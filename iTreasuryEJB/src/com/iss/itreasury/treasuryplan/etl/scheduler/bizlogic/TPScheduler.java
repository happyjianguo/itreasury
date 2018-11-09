/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-9
 */

package com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.extract.bizlogic.GeneralLedgerExtractor;
import com.iss.itreasury.treasuryplan.etl.extract.bizlogic.LoanExtractor;
import com.iss.itreasury.treasuryplan.etl.extract.bizlogic.SecuritiesExtractor;
import com.iss.itreasury.treasuryplan.etl.extract.bizlogic.SettlementExtractor;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.dao.SchedulerDAO;
import com.iss.itreasury.treasuryplan.etl.scheduler.dataentity.SchedulerInfo;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.ActualDataTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AfterTransformation;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.ForecastDataTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.dataentity.ClientType;
import com.iss.itreasury.treasuryplan.util.dataentity.Prefix;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TPScheduler
{

	protected Log4j				log						= new Log4j(Constant.ModuleType.PLAN, this);

	//
	final static private int	MANUAL_UPDATE_DURATION	= 4;


	static public void main(String[] args)
	{

		TPScheduler tpScheduler = new TPScheduler();
		try 
		{
			// tpScheduler.constructTPTemplate( Env.getSystemDate(1,1) );
			Timestamp startdate = Timestamp.valueOf("2006-05-04 00:00:00.000000000");
			Timestamp enddate = Timestamp.valueOf("2006-05-07 00:00:00.000000000");

			try 
			{
				SchedulerInfo schedulerInfo = new SchedulerInfo();
				schedulerInfo.setForecastStartDate(startdate);
				schedulerInfo.setForecastEndDate(enddate);
				schedulerInfo.setForecastUpdate(true);
				System.out.println("START Forecast EXTATCT ...................................");
				tpScheduler.startExtractAndTransform(schedulerInfo,1);
				System.out.println("END Forecast EXTATCT ...................................");
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 获取设置中是否进行资金计划数据更新以及更新的开始结束时间
	 */
	private Timestamp[] getForecastSDateAndEDate() throws Exception
	{

		Timestamp res[] = new Timestamp[2];
		Trea_SystemParameterDAO systemParameterDAO = new Trea_SystemParameterDAO();
		SystemParameterInfo info = new SystemParameterInfo();
		info.setStatusID(1);
		info.setParameterName("TPRunSwitch");
		SystemParameterInfo resInfo = systemParameterDAO.find(info);
		double paramValue = resInfo.getParameterValue();
		if (paramValue == 1) 
		{// 对于资金计划执行开关：0=关
			info = new SystemParameterInfo();
			info.setStatusID(1);
			info.setParameterName("TPStartDate");
			resInfo = systemParameterDAO.find(info);
			double paramValue1 = resInfo.getParameterValue();
			res[0] = new Timestamp((long) paramValue1);
			info = new SystemParameterInfo();
			info.setStatusID(1);
			info.setParameterName("TPEndDate");
			resInfo = systemParameterDAO.find(info);
			double paramValue2 = resInfo.getParameterValue();
			res[1] = new Timestamp((long) paramValue2);
			return res;
		}
		else 
		{
			return null;
		}
	}


	/**
	 * @description:业务数据即时更新 描述：(1)
	 *                       条件：1.sDate等于eDate,2.且都等于结算系统的开机日，3.且开机日结算系统的状态为"Opened"（未关机）；
	 *                       a.如果同时满足上述3个条件，则不执行更新操作，直接return;
	 *                       b.如果满足条件1、2，不满足条件3（说明结算系统已经关机），则执行更新操作。 (2)
	 *                       条件：1.sDate小于结算系统的开机日，eDate
	 *                       等于结算系统的开机日；2.开机日结算系统的状态为"Opened"（未关机）。
	 *                       c.如果条件1、2均成立，则eDate = eDate - 1,执行更新操作；
	 *                       d.如果条件1成立，2不成立（系统已经关机），则执行正常的更新操作。 (3) e.如果sDate
	 *                       和eDate 均小于结算系统的开机日则执行正常的更新操作，不管当前系统是否已关机。 void
	 * @param sDate：数据更新区间
	 *            由
	 * @param eDate：数据更新区间
	 *            至
	 * @throws Exception
	 * @by Jason
	 */
	public void startExtractAndTransform(SchedulerInfo schedulerInfo,long officeid) throws Exception
	{

		log = new Log4j(Constant.ModuleType.PLAN, this);

		System.out.println("111111111111111111111111111111111111");
		System.out.println("-----------Manual update  ActualStartDate  : " + schedulerInfo.getActualStartDate());
		System.out.println("-----------Manual update  ActualEndDate    : " + schedulerInfo.getActualEndDate());
		System.out.println("-----------Manual update  ForecastStartDate: " + schedulerInfo.getForecastStartDate());
		System.out.println("-----------Manual update  ForecastEndDate  : " + schedulerInfo.getForecastEndDate());

		SettlementExtractor settlementExtractor = new SettlementExtractor();
		GeneralLedgerExtractor generalLedgerExtractor = new GeneralLedgerExtractor();
		SecuritiesExtractor securitiesExtractor = new SecuritiesExtractor();
		LoanExtractor loanExtractor = new LoanExtractor();
		
		
		System.out.println("---------------开始抽取数据................");
		Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();

		Collection c = extractorUtilDAO.findAllOfficeTimeByID(Constant.CurrencyType.RMB,officeid);
		Iterator it = null;
		if (c != null) 
		{
			it = c.iterator();
		}

		
		while (it != null && it.hasNext()) 
		{
			OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
			System.out.println("---------正在处理的OfficeTimeInfo: " + officeTimeInfo);
			// Timestamp transDate = officeTimeInfo.getDTOPENDATE();

			// 如果不是人民币或者结算系统的状态为null,则跳出本次循环，进行下一次循环
			if (officeTimeInfo.getNCURRENCYID() != Constant.CurrencyType.RMB || officeTimeInfo.getSSYSTEMSTATUSDESC() == null) 
			{
				System.out.println(" 币种为: " + officeTimeInfo.getNCURRENCYID() + " ； 结算系统的状态为: " + officeTimeInfo.getSSYSTEMSTATUSDESC() + "  跳出本次循环，进行下一次循环................... ");
				continue;
			}

			if (schedulerInfo.isActualUpdate())// 更新实际数据
			{
				if (officeTimeInfo.getNCURRENCYID() == Constant.CurrencyType.RMB) 
				{
					if (officeTimeInfo.getSSYSTEMSTATUSDESC() != null) 
					{
						if (officeTimeInfo.getSSYSTEMSTATUSDESC().compareToIgnoreCase("Closed") != 0 && schedulerInfo.getActualStartDate().equals(schedulerInfo.getActualEndDate()) && schedulerInfo.getActualStartDate().equals(officeTimeInfo.getDTOPENDATE()))// 如果符合描述中a的情况
						{
							System.out.println("1.sDate等于eDate,2.且都等于结算系统的开机日，3.且开机日结算系统的状态为未关机。不执行数据更新，跳出本次循环，进行下一次循环................... ");
							continue;
						}
						else if (officeTimeInfo.getSSYSTEMSTATUSDESC().compareToIgnoreCase("Closed") != 0 && schedulerInfo.getActualStartDate().before(officeTimeInfo.getDTOPENDATE()) && schedulerInfo.getActualEndDate().equals(officeTimeInfo.getDTOPENDATE()))// 如果符合描述中c的情况
						{
							schedulerInfo.setActualEndDate(DataFormat.getPreviousDate(schedulerInfo.getActualEndDate()));
							System.out.println("---------eDate = eDate -1 :  " + schedulerInfo.getActualEndDate());
						}
					}
				}
			}

			// 先删除重复数据,必须关机后才能操作
			// beforeExtract.beforeExtract(sDate,eDate,officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID());

			// 对第一天的实际数据进行处理

			if (schedulerInfo.isActualUpdate()) 
			{
				System.out.println("Actual---------开始对当天的实际数据进行手动更新的抽取....................... ");

				System.out.println("Actual--------- start settlementExtractor.extractActualAmount....................... ");
				settlementExtractor.extractActualAmount(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getActualStartDate(), schedulerInfo.getActualEndDate());
				System.out.println("Actual--------- end settlementExtractor.extractActualAmount....................... ");

				System.out.println("Actual--------- start settlementExtractor.extractActualBalance....................... ");
				settlementExtractor.extractActualBalance(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getActualStartDate(), schedulerInfo.getActualEndDate());
				System.out.println("Actual--------- end settlementExtractor.extractActualBalance....................... ");

				System.out.println("Actual--------- start generalLedgerExtractor.extractActualBalance....................... ");
				generalLedgerExtractor.extractActualBalance(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getActualStartDate(), schedulerInfo.getActualEndDate());
				System.out.println("Actual--------- end generalLedgerExtractor.extractActualBalance....................... ");

				System.out.println("Actual--------- start securitiesExtractor.extractActualAmount....................... ");
				securitiesExtractor.extractActualAmount(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getActualStartDate(), schedulerInfo.getActualEndDate());
				System.out.println("Actual--------- end securitiesExtractor.extractActualAmount....................... ");

				System.out.println("Actual--------- 结束对当天的实际数据进行手动更新的抽取....................... ");
			}

			// transDate = DataFormat.getNextDate(transDate, 1);
			// 预测数据的抽取从当前日期后的第一天开始
			if (schedulerInfo.isForecastUpdate()) 
			{
				System.out.println("Forecast--------- 开始对未来数据进行手动更新的抽取....................... ");
				
				System.out.println("Forecast--------- start settlementExtractor.extractForcastAmount ....................... ");
				settlementExtractor.extractForcastAmount(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate());
				System.out.println("Forecast--------- end settlementExtractor.extractForcastAmount....................... ");

				System.out.println("Forecast--------- start settlementExtractor.extractForcastBalance....................... ");
				settlementExtractor.extractForcastBalance(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate());
				System.out.println("Forecast--------- end settlementExtractor.extractForcastBalance ....................... ");

				System.out.println("Forecast--------- start securitiesExtractor.extractForcastAmount ....................... ");
				securitiesExtractor.extractForcastAmount(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate());
				System.out.println("Forecast--------- end securitiesExtractor.extractForcastAmount....................... ");

				System.out.println("Forecast--------- start loanExtractor.extractForcastAmount....................... ");
				loanExtractor.extractForcastAmount(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate());
				System.out.println("Forecast--------- end loanExtractor.extractForcastAmoun t....................... ");

					
				System.out.println("Forecast--------- 结束对未来数据进行手动更新的抽取 －－－－－－－－－ ");
			}

		}

		extractorUtilDAO.closeModuleConn();

		Connection tpConn = Database.getConnection();
		ActualDataTransformer actualDataTransformer = new ActualDataTransformer();
		ForecastDataTransformer forecastDataTransformer = new ForecastDataTransformer();
		it = c.iterator();
		int i=0;
		while (it.hasNext()) 
		{
		    System.out.println("wzpaaaaaaaaaaaaaaa"+(c.size())+"       index:"+(i++));
		    
			OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
			System.out.println("---------正在处理的OfficeTimeInfo: " + officeTimeInfo);

			if (schedulerInfo.isActualUpdate()) 
			{
				System.out.println("---------开始转换实际数据.......................");
				actualDataTransformer.transform(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getActualStartDate(), schedulerInfo.getActualEndDate(), tpConn);
				System.out.println("---------结束转换转换实际数据.......................");
			}

			if (schedulerInfo.isForecastUpdate()) 
			{
				// Timestamp tmpDate =
				// DataFormat.getNextDate(schedulerInfo.getForecastStartDate(),
				// 1);
				// System.out.println("---------开始转换预测数据，预测首日为:"+tmpDate);
				// while(!tmpDate.after(schedulerInfo.getForecastEndDate()))
				// {
				System.out.println("---------正在转换预测数据.......................");
				forecastDataTransformer.transform(officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate(), tpConn);
				// tmpDate = DataFormat.getNextDate(tmpDate, 1);
				// }

				System.out.println("---------结束转换预测数据---------");
			}
		}

		tpConn.close();

		AfterTransformation afterTransformation = new AfterTransformation();
		String[] args = new String[1];
		System.out.println("#########---------结束转换预测数据    schedulerInfo.getForecastStartDate() = " + schedulerInfo.getForecastStartDate());
		System.out.println("%%%%%%%%%---------结束转换预测数据    schedulerInfo.getForecastEndDate()" + schedulerInfo.getForecastEndDate());

		if (schedulerInfo.isForecastUpdate()) 
		{
			long intervalDays = DataFormat.getIntervalDays(schedulerInfo.getForecastStartDate(), schedulerInfo.getForecastEndDate());
			// args[0] = String.valueOf(MANUAL_UPDATE_DURATION);
			args[0] = String.valueOf(intervalDays);

			System.out.println("---------开始数据更新后的最后处理，更新天数为：" + args[0]);
			afterTransformation.afterTransformation(args);
			System.out.println("---------结束数据更新后的最后处理---------");
		}

	}


	/**
	 * 根据用户设定的参数决定是否执行所有操作 在每个操作前必须调用此函数
	 * 
	 * @throws Exception
	 */
	static public boolean isNeedExecute(Connection tpConn) throws Exception
	{

		boolean res = false;
		if (tpConn == null)
		{
			tpConn = Database.getConnection();
		}
		Trea_SystemParameterDAO systemParameterDAO = new Trea_SystemParameterDAO();
		SystemParameterInfo info = new SystemParameterInfo();
		info.setStatusID(1);
		info.setParameterName("TPRunSwitch");
		SystemParameterInfo resInfo = systemParameterDAO.find(info);
		double paramValue = resInfo.getParameterValue();
		if (paramValue == 1) 
		{// 对于资金计划执行开关：0=关
			res = true;
		}
		else
		{
			res = false;
		}

		if (tpConn != null)
		{
			tpConn.close();
		}
		return res;
	}


	public void beforeExtract()
	{

	}


	/**
	 * @description: 自动构造资金计划模版 void
	 * @param openDate
	 * @throws Exception
	 */
	public void constructTPTemplate(Timestamp openDate) throws Exception
	{

		Connection tpConn = Database.getConnection();
		tpConn.setAutoCommit(false);
		Trea_TPTemplateItemDAO templateItemDAO = new Trea_TPTemplateItemDAO(tpConn);
		templateItemDAO.setUseMaxID();
		Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(tpConn);
		templateDAO.setUseMaxID();
		SchedulerDAO schedulerDAO = new SchedulerDAO(tpConn);
		templateItemDAO.setUseMaxID();
		// 1. 新增开户银行
		Collection c = templateItemDAO.getNewBranchSubject();
		Iterator it = c.iterator();
		while (it.hasNext()) 
		{
			String subjectCode = (String) it.next();
			// 增加模版子项中“期初余额”的定义项
			TPTemplateItemInfo initTemplateItemInfo = new TPTemplateItemInfo();
			initTemplateItemInfo.setLineID(1);
			initTemplateItemInfo.setModuleID(Constant.ModuleType.GENERALLEDGER);
			initTemplateItemInfo.setGlSubjectCode(subjectCode);
			initTemplateItemInfo.setAmountFlag(2);
			templateItemDAO.add(initTemplateItemInfo);
			// 增加模版子项中“期未余额”的定义项
			TPTemplateItemInfo endTemplateItemInfo = new TPTemplateItemInfo();
			endTemplateItemInfo.setLineID(3662);
			endTemplateItemInfo.setModuleID(Constant.ModuleType.GENERALLEDGER);
			endTemplateItemInfo.setGlSubjectCode(subjectCode);
			endTemplateItemInfo.setAmountFlag(2);
			templateItemDAO.add(endTemplateItemInfo);
		}

		c = schedulerDAO.getNewClient(openDate);
		it = c.iterator();

		while (it.hasNext())
		{
			ClientInfo clientInfo = (ClientInfo) it.next();

			ClientType clientType = TPConstant.LineNoPrefix.getInstance().getClientType(clientInfo.getClientTypeID());

			Hashtable allPrefixes = clientType.getAllPrefixes();
			Enumeration e = allPrefixes.keys();
			while (e.hasMoreElements())
			{
				Prefix p = (Prefix) allPrefixes.get(e.nextElement());
				addNewTemplateByAccountTypeID(templateItemDAO, templateDAO, schedulerDAO, p, clientInfo, openDate);
			}

		}

		// 检查新增客户是否已经开户，如果开户，使得对应行项目激活
		// 3. 活期存款账户
		c = schedulerDAO.getClientOfNewAccount(openDate, 1);
		it = c.iterator();
		while (it.hasNext())
		{
			ClientInfo clientInfo = (ClientInfo) it.next();
			templateDAO.updateStatusIDByAccountType(clientInfo.getClientCode(), 1);
		}

		// 4. 定期存款账户

		c = schedulerDAO.getClientOfNewAccount(openDate, 2);
		it = c.iterator();
		while (it.hasNext()) 
		{
			ClientInfo clientInfo = (ClientInfo) it.next();
			templateDAO.updateStatusIDByAccountType(clientInfo.getClientCode(), 2);
		}

		// 5. 通知存款账户
		c = schedulerDAO.getClientOfNewAccount(openDate, 3);
		it = c.iterator();
		while (it.hasNext())
		{
			ClientInfo clientInfo = (ClientInfo) it.next();
			templateDAO.updateStatusIDByAccountType(clientInfo.getClientCode(), 3);
		}

		// 5. 委托存款
		c = schedulerDAO.getClientOfNewAccount(openDate, 4);
		it = c.iterator();
		while (it.hasNext()) 
		{
			ClientInfo clientInfo = (ClientInfo) it.next();
			templateDAO.updateStatusIDByAccountType(clientInfo.getClientCode(), 3);
		}

		// 检查新增客户是否已经有申请书，如果有，使得对应行项目激活
		// 6. 自营贷款申请
		c = schedulerDAO.getNewLoanApply(openDate, 1);
		it = c.iterator();
		while (it.hasNext()) 
		{
			templateDAO.updateStatusIDByAccountType((String) it.next(), 4);
		}

		// 7. 委托贷款申请
		c = schedulerDAO.getNewLoanApply(openDate, 2);
		it = c.iterator();
		while (it.hasNext()) 
		{
			templateDAO.updateStatusIDByAccountType((String) it.next(), 5);
		}

		// 8. 贴现贷款申请
		c = schedulerDAO.getNewLoanApply(openDate, 3);
		it = c.iterator();
		while (it.hasNext()) 
		{
			templateDAO.updateStatusIDByAccountType((String) it.next(), 6);
		}

		tpConn.commit();
		tpConn.close();
	}


	/**
	 * @param templateItemDAO
	 * @param templateDAO
	 * @param accInfo
	 * @throws Exception
	 * @throws ITreasuryDAOException
	 */
	private void addNewTemplateForResourceAndUsingForNewAccount(Trea_TPTemplateItemDAO templateItemDAO, Trea_TPTemplateDAO templateDAO, ClientInfo clientInfo) throws Exception, ITreasuryDAOException
	{

		// 增加模版中的资金来源行项目
		// 增加行项目
		TPTemplateInfo templateInfo2 = new TPTemplateInfo();
		templateInfo2.setOfficeID(1);
		templateInfo2.setCurrencyID(1);
		templateInfo2.setLineNo("002_002_0" + templateDAO.getLineNoByPrefix("002_002"));
		templateInfo2.setLineName(clientInfo.getClientName());
		templateInfo2.setLineLevel(3);

		long parentLineID2 = templateDAO.getIDByLineNo("002_002");
		templateInfo2.setParentLineID(parentLineID2);
		templateInfo2.setStatusID(1);
		templateInfo2.setIsLeaf(1);
		templateInfo2.setAuthorizedDepartment("<10003>");
		templateInfo2.setInputDate(DataFormat.getTreasuryPlanExecuteDate());

		long templateID = templateDAO.add(templateInfo2);

		// 增加行项目属性设置
		TPTemplateItemInfo templateItemInfo21 = new TPTemplateItemInfo();
		templateItemInfo21.setLineID(templateID);
		templateItemInfo21.setClientCode(clientInfo.getClientCode());
		templateItemInfo21.setAccountTypeId(4);
		templateItemInfo21.setAmountDirection(1);
		templateItemInfo21.setAmountFlag(1);
		templateItemDAO.add(templateItemInfo21);

		// 增加模版中的资金运用行项目
		// 增加行项目
		TPTemplateInfo templateInfo3 = new TPTemplateInfo();
		templateInfo3.setOfficeID(1);
		templateInfo3.setCurrencyID(1);
		templateInfo3.setLineNo("003_002_0" + templateDAO.getLineNoByPrefix("003_002"));
		templateInfo3.setLineName(clientInfo.getClientName());
		templateInfo3.setLineLevel(3);

		long parentLineID3 = templateDAO.getIDByLineNo("003_002");
		templateInfo3.setParentLineID(parentLineID3);
		templateInfo3.setStatusID(1);
		templateInfo3.setIsLeaf(1);
		templateInfo3.setAuthorizedDepartment("<10003>");
		templateInfo3.setInputDate(DataFormat.getTreasuryPlanExecuteDate());

		long templateID2 = templateDAO.add(templateInfo3);

		// 增加行项目属性设置
		TPTemplateItemInfo templateItemInfo31 = new TPTemplateItemInfo();
		templateItemInfo31.setLineID(templateID2);
		templateItemInfo31.setClientCode(clientInfo.getClientCode());
		templateItemInfo31.setAccountTypeId(4);
		templateItemInfo31.setAmountDirection(2);
		templateItemInfo31.setAmountFlag(1);
		templateItemDAO.add(templateItemInfo21);
	}


	/**
	 * @param unitLineLevel
	 * @param lineNo
	 * @return
	 */
	private String getUnitLineNo(String strSubLineNum)
	{

		long subLineNum = Long.parseLong(strSubLineNum);
		String res = "";
		if (subLineNum >= 1 && subLineNum <= 9) 
		{
			res += "_00" + subLineNum;
		}
		else if (subLineNum >= 10 && subLineNum <= 99) 
		{
			res += "_0" + subLineNum;
		}
		else if (subLineNum >= 100 && subLineNum <= 999) 
		{
			res += "_" + subLineNum;
		}
		return res;
	}


	private void addNewTemplateByAccountTypeID(Trea_TPTemplateItemDAO templateItemDAO, Trea_TPTemplateDAO templateDAO, SchedulerDAO schedulerDAO, Prefix prefix, ClientInfo clientInfo, Timestamp openDate) throws Exception, ITreasuryDAOException
	{

		long sourceOrUse = prefix.getFromOrTo();
		String maxLineCode = templateDAO.getMaxLineNo(prefix.getPrefixString());
		String lineNo = prefix.getPrefixString() + getUnitLineNo(maxLineCode);
		TPTemplateInfo templateInfo = new TPTemplateInfo();

		templateInfo.setOfficeID(1);
		templateInfo.setCurrencyID(1);
		templateInfo.setLineNo(lineNo);
		templateInfo.setLineName(clientInfo.getClientName());
		templateInfo.setLineLevel((lineNo.length() + 1) / 4);

		long parentLineID = templateDAO.getIDByLineNo(prefix.getPrefixString());
		templateInfo.setParentLineID(parentLineID);
		templateInfo.setIsLeaf(0);
		templateInfo.setAuthorizedDepartment("<6>");
		templateInfo.setIsNeedSum(1);
		templateInfo.setInputDate(DataFormat.getTreasuryPlanExecuteDate());
		System.out.println("----------templateInfo:" + templateInfo);
		long clientTemplateID = templateDAO.add(templateInfo);

		for (int i = 0; i < prefix.getAccountTypes().size(); i++) 
		{
			long debitDirection = -1;

			long accountTypeID = Long.parseLong(((String) prefix.getAccountTypes().get(i)).trim());
			if ((sourceOrUse == 1 && (accountTypeID == SETTConstant.AccountGroupType.CURRENT || accountTypeID == SETTConstant.AccountGroupType.FIXED || accountTypeID == SETTConstant.AccountGroupType.NOTIFY || accountTypeID == SETTConstant.AccountGroupType.CONSIGN))
					|| (sourceOrUse == 2 && (accountTypeID == SETTConstant.AccountGroupType.TRUST || accountTypeID == SETTConstant.AccountGroupType.CONSIGN || accountTypeID == SETTConstant.AccountGroupType.DISCOUNT)))
			{
				debitDirection = 1;
			}
			else 
			{
				debitDirection = 2;
			}

			Collection c = schedulerDAO.getClientOfNewAccount(openDate, 2);

			if (accountTypeID == SETTConstant.AccountGroupType.CONSIGN || accountTypeID == SETTConstant.AccountGroupType.DISCOUNT) 
			{
				Collection c1 = schedulerDAO.getClientOfNewAccount(openDate, accountTypeID);
				if (c1.size() > 0) 
				{
					templateDAO.updateStatus(clientTemplateID, 1);
				}
				// 子项目
				TPTemplateItemInfo templateItemInfo21 = new TPTemplateItemInfo();
				templateItemInfo21.setLineID(clientTemplateID);
				templateItemInfo21.setClientCode(clientInfo.getClientCode());
				templateItemInfo21.setAccountTypeId(accountTypeID);
				templateItemInfo21.setAmountDirection(debitDirection);
				templateItemInfo21.setAmountFlag(1);
				System.out.println("----------templateItemInfo21:" + templateItemInfo21);
				templateItemDAO.add(templateItemInfo21);
				break;
			}

			// --行项目

			TPTemplateInfo templateInfo2 = new TPTemplateInfo();
			templateInfo2.setOfficeID(1);
			templateInfo2.setCurrencyID(1);
			String suffix = getUnitLineNo(templateDAO.getNextAccountTypeLineNoSuffix(lineNo));
			templateInfo2.setLineNo(lineNo + suffix);
			templateInfo2.setLineName(getLineNameByAccountTypeID(accountTypeID));
			templateInfo2.setLineLevel(templateInfo.getLineLevel() + 1);
			templateInfo2.setIsNeedSum(1);
			Collection c1 = schedulerDAO.getClientOfNewAccount(openDate, accountTypeID);
			if (c1.size() > 0)
			{
				templateDAO.updateStatus(clientTemplateID, 1);
				templateInfo2.setStatusID(1);
			}
			/*
			 * Iterator it = c.iterator(); while(it.hasNext()){ ClientInfo
			 * tmpClientInfo = (ClientInfo) it.next();
			 * templateDAO.updateStatusIDByAccountType(tmpClientInfo.getClientCode(),accountTypeID); }
			 */

			// long parentLineID2 = templateDAO.getIDByLineNo(lineNoPrefix1);
			templateInfo2.setParentLineID(clientTemplateID);
			templateInfo2.setIsLeaf(1);
			templateInfo2.setAuthorizedDepartment("<6>");
			templateInfo2.setInputDate(DataFormat.getTreasuryPlanExecuteDate());

			long currentTemplateID = templateDAO.add(templateInfo2);

			// 子项目
			TPTemplateItemInfo templateItemInfo21 = new TPTemplateItemInfo();
			templateItemInfo21.setLineID(currentTemplateID);
			templateItemInfo21.setClientCode(clientInfo.getClientCode());
			templateItemInfo21.setAccountTypeId(accountTypeID);
			templateItemInfo21.setAmountDirection(debitDirection);
			templateItemInfo21.setAmountFlag(1);
			System.out.println("----------templateItemInfo21:" + templateItemInfo21);
			templateItemDAO.add(templateItemInfo21);
		}
	}


	private static String getLineNameByAccountTypeID(long accountTypeID)
	{

		String accTypeName = "";
		if (accountTypeID == SETTConstant.AccountGroupType.CURRENT)
			accTypeName = "活期";
		else if (accountTypeID == SETTConstant.AccountGroupType.FIXED)
			accTypeName = "定期";
		else if (accountTypeID == SETTConstant.AccountGroupType.NOTIFY)
			accTypeName = "通知";
		else if (accountTypeID == SETTConstant.AccountGroupType.TRUST)
			accTypeName = "自营";
		else if (accountTypeID == SETTConstant.AccountGroupType.CONSIGN)
			accTypeName = "委贷";
		else if (accountTypeID == SETTConstant.AccountGroupType.DISCOUNT)
			accTypeName = "贴现";
		else if (accountTypeID == SETTConstant.AccountGroupType.CONSIGN)
			accTypeName = "委存";
		return accTypeName;
	}

	private long[]	accountTypes	= {SETTConstant.AccountGroupType.CURRENT, 
									   SETTConstant.AccountGroupType.FIXED, 
									   SETTConstant.AccountGroupType.NOTIFY, 
									   SETTConstant.AccountGroupType.TRUST, 
									   SETTConstant.AccountGroupType.CONSIGN, 
									   SETTConstant.AccountGroupType.DISCOUNT};

}
