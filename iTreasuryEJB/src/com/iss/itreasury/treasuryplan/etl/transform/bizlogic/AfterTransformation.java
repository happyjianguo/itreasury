/*
 * Created on 2004-8-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.transform.bizlogic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.AbstractBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPActualDataDAO;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPForecastDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

/**
 * @author yehuang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AfterTransformation {

	/*
    3662	 	期末可用余额
	3661	     差额调节
	3660	 	资金头寸
	3659	 	必要备付金
	1830	 	资金运用
	2	 	    资金来源
	1	 	    期初可用余额
	
    	 	期末余额
	 	    期初余额	
	
	
 */
private long beginUsefulBalanceRowID = TPConstant.RowID.getInstance().getBeginBalanceRowID();//期初可用余额
private long beginBalanceRowID = TPConstant.RowID.getInstance().getBeginBalanceRowID1();//期初余额
private long endUsefulBalanceRowID = TPConstant.RowID.getInstance().getEndBalanceRowID();//期末可用余额
private long endBalanceRowID = TPConstant.RowID.getInstance().getEndBalanceRowID1();//期末余额
private long fromRowID = TPConstant.RowID.getInstance().getFormRowID();//2
private long toRowID = TPConstant.RowID.getInstance().getToRowID();//1830
private long needMoneyRowID = TPConstant.RowID.getInstance().getNeedMoneyRowID();//3659
private long cashRowID = TPConstant.RowID.getInstance().getCashRowID();//3660
private long adjustCashRowID = TPConstant.RowID.getInstance().getAdjustCashRowID();//3660
private long balanceRegulateRowID = TPConstant.RowID.getInstance().getBalanceRegulateRowID();//3661
private long enterpriseDeposit = TPConstant.RowID.getInstance().getEnterpriseDeposit();//
private long enterpriseWithdraw = TPConstant.RowID.getInstance().getEnterpriseWithdraw();//




/**
 * 如果没有参数传入，则取系统中的数据 否则取传入数据 arg0 执行日 arg1 关机日（只针对人民币，其他币种仍然取数据库中的关机日）
 */
public static void main(String[] args) {
	AfterTransformation afterTransformation = new AfterTransformation();
	try {
		if (!TPScheduler.isNeedExecute(null))
			return;
		afterTransformation.afterTransformation(args);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void afterTransformation(String[] args) throws Exception {

	System.out.println("afterTransformation----Last Modified at 2004-11-17");
	Connection tpConn = Database.getConnection();
	//Timestamp currentDate = DataFormat.getTreasuryPlanExecuteDate();
	//Timestamp currentDate = Timestamp.valueOf("2004-08-22
	// 00:00:00.000000000");
	//Timestamp currentDate = Timestamp.valueOf("2004-08-20
	// 00:00:00.000000000");
	Timestamp currentDate = null;
	Timestamp openDate = null;
	ForecastDataTransformer transformer = new ForecastDataTransformer();
	Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();
	Collection c = extractorUtilDAO.findAllOfficeTime();
    
    Iterator it = null;
    if( c != null && c.size() > 0 )
    {
        it = c.iterator();
    }
	
	ForecastDataTransformer transfomer = new ForecastDataTransformer();
	Trea_TPTemplateDAO TPTemplateDAO = new Trea_TPTemplateDAO(tpConn);
	Trea_TPForecastDataDAO destinationDataDAO = new Trea_TPForecastDataDAO(
			tpConn);
	Trea_TPActualDataDAO actualDataDAO = new Trea_TPActualDataDAO(tpConn);
	AbstractAmountDAO amountDAO = AbstractAmountDAO.getAmountDAOInstance(
			ForecastDataTransformer.Extracting_Type_Forecast, tpConn);
	Trea_SystemParameterDAO systemParameterDAO = new Trea_SystemParameterDAO(tpConn);
	Trea_TPTemplateDAO templateDAO = new Trea_TPTemplateDAO(tpConn);
	AbstractBalanceDAO balanceDAO = AbstractBalanceDAO
			.getBalanceDAOInstance(
					ForecastDataTransformer.Extracting_Type_Forecast,
					tpConn);
	
	double todayPlanReverse = 0.0;
	
	//进行更新的时间长度，默认为一年，如果是手工数据更新，为参数传入的天数
	int duration = 0;

	while (it != null && it.hasNext()) 
    {
		OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
		System.out.println("---------正在处理的OfficeTimeInfo: " + officeTimeInfo);
		
		//如果系统状态为“关机”才运行资金计划
		long lStatusID = Constant.SystemStatus.getID(officeTimeInfo.getSSYSTEMSTATUSDESC());
		if (lStatusID!=Constant.SystemStatus.CLOSE)
		{
			break;
		}
		
		System.out.println("-----------今天是: " + currentDate);
		System.out.println("---------------开始转换后的更新-----------" + args);
		if (args != null && args.length == 2  && args[0] != null
				&& args[1] != null) {
			currentDate = Timestamp
					.valueOf(args[0] + " 00:00:00.000000000");
			if (officeTimeInfo.getNCURRENCYID() == 1)//只针对人民币
				openDate = Timestamp.valueOf(args[1]
						+ " 00:00:00.000000000");
			else {
				// = officeTimeInfo.getDTOPENDATE();
				//					//交易时间更改为取系统时间
				//openDate = DataFormat.getTreasuryPlanExecuteDate();
				openDate = officeTimeInfo.getDTOPENDATE();
			}

			
		}else {
			
			//第1个参数是需要更新的时间天数
			if(args.length == 1 && args[0] != null){
				duration = Integer.parseInt(args[0]);
				System.out.println("--------------开始手动数据更新后的处理，处理天数为：" + duration);					
			}
			
			currentDate = DataFormat.getTreasuryPlanExecuteDate();
			
			openDate = officeTimeInfo.getDTOPENDATE();
			//				//交易时间更改为取系统时间
			//openDate = DataFormat.getTreasuryPlanExecuteDate();
			 
		}

		System.out.println("--------------currentDate" + currentDate);
		System.out.println("--------------openDate" + openDate);

		//currentDate = Timestamp.valueOf("2004-08-26 00:00:00.000000000");
		//openDate = Timestamp.valueOf("2004-08-26 00:00:00.000000000");

		
		
		Timestamp startDate = openDate;
		
		startDate = DataFormat.getNextDate(startDate, 1);
		Timestamp endDate = null;
		if(duration > 0){
			endDate = DataFormat.getNextDate(startDate, duration);
		}else
			endDate = DataFormat.getNextYear(startDate, 1);
		
		//必要备付金率
		SystemParameterInfo reserveParameterInfo = new SystemParameterInfo();
		reserveParameterInfo.setStatusID(1);
		reserveParameterInfo.setParameterName("Paymentrate");
		reserveParameterInfo = systemParameterDAO.find(reserveParameterInfo);
		double rate = reserveParameterInfo.getParameterValue() / 100.0;
		
		int i = 0;
		while (!endDate.before(startDate)) {
			System.out.println("date=" + startDate.toString());

			if (i == 0) {
				
				//预测首日期初可用余额=当日实际期末可用余额
				double initUsefulForecastBalance = actualDataDAO
						.getAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo
								.getNCURRENCYID(), endUsefulBalanceRowID, openDate);
				
				//预测首日期初余额=当日实际期末余额
				double initForecastBalance = actualDataDAO
						.getAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo
								.getNCURRENCYID(), endBalanceRowID, openDate);				

				destinationDataDAO.updateAmountByLineIDAndTransactionDate(
						officeTimeInfo.getNOFFICEID(), officeTimeInfo
								.getNCURRENCYID(), initUsefulForecastBalance, beginUsefulBalanceRowID,
						startDate);
				
				destinationDataDAO.updateAmountByLineIDAndTransactionDate(
						officeTimeInfo.getNOFFICEID(), officeTimeInfo
								.getNCURRENCYID(), initForecastBalance, beginBalanceRowID,
						startDate);				
				
			} 
			else 
			{
				Collection c1 = templateDAO.getTemplatesDependingOtherLine(
				officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID());
				Iterator it1 = c1.iterator();
				while (it1.hasNext()) {
			
					TPTemplateInfo tpTemplateInfo = (TPTemplateInfo) it1.next();
					System.out.println("---------正在处理的tpTemplateInfo: " + tpTemplateInfo);
					transformer.sumTransformationData(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), currentDate, startDate,
							tpConn, destinationDataDAO, amountDAO,
							systemParameterDAO, balanceDAO, tpTemplateInfo,
							true, 
							true,
							false  //InsertOrUpdate  the false is update 
							);

				}
				

			} //end else

			double forcastAmount1 = destinationDataDAO
					.getAmountByLineIDAndTransactionDate(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), beginBalanceRowID, startDate);
			double forcastUsefulAmount1 = destinationDataDAO
			.getAmountByLineIDAndTransactionDate(officeTimeInfo
					.getNOFFICEID(), officeTimeInfo
					.getNCURRENCYID(), beginUsefulBalanceRowID, startDate);			
			
			double forcastAmount2 = destinationDataDAO
					.getAmountByLineIDAndTransactionDate(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), fromRowID, startDate);
			double forcastAmount1830 = destinationDataDAO
					.getAmountByLineIDAndTransactionDate(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), toRowID, startDate);
			
			//不参与公式计算的资金来源预测数
			double unSumForecastFromAmount = destinationDataDAO.getUnSumAmountByLineIDAndTransactionDate(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), fromRowID, startDate);
			//不参与公式计算的资金运用预测数
			double unSumForecastToAmount = destinationDataDAO.getUnSumAmountByLineIDAndTransactionDate(officeTimeInfo
							.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), 6, startDate);
			
			//资金来源预测数去掉不参与公式计算的金额
			forcastAmount2= forcastAmount2 - unSumForecastFromAmount;
			//资金运用预测数去掉不参与公式计算的金额
			forcastAmount1830 = forcastAmount1830 - unSumForecastToAmount;
			
			//更新资金来源预测数
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), forcastAmount2, fromRowID, startDate);
			//更新资金运用预测数
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), forcastAmount1830, toRowID, startDate);
			
			//期末可用余额
			double endBalance = DataFormat.cutNumberAfterDecimal(forcastAmount1) + DataFormat.cutNumberAfterDecimal(forcastAmount2) - DataFormat.cutNumberAfterDecimal(forcastAmount1830);
			//期末可用可用余额
			double endUsefulBalance = DataFormat.cutNumberAfterDecimal(forcastUsefulAmount1) + DataFormat.cutNumberAfterDecimal(forcastAmount2) - DataFormat.cutNumberAfterDecimal(forcastAmount1830);			
			//更新期末余额
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), endBalance, endBalanceRowID, startDate);
			//更新期末可用余额
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), endUsefulBalance, endUsefulBalanceRowID, startDate);	
			
			//必要备付金
			double payment=0;
			if(i==0)
			{
				payment = actualDataDAO.getAmountByLineIDAndTransactionDate(
						officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), needMoneyRowID, openDate);
			}
			else
			{
				payment = destinationDataDAO.getAmountByLineIDAndTransactionDate(
						officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), 
						needMoneyRowID, DataFormat.getPreviousDate(startDate));
			}
			//更新 必要备付金=（前一天“必要备付金”/必要备付金率+资金来源-资金运用）*必要备付金率 
			payment=(payment/rate+forcastAmount2-forcastAmount1830)*rate;
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), 
					payment, needMoneyRowID, startDate);	
			
			//资金头寸=期末可用余额-必要备付金
			//资金头寸
			double forecasttc = endUsefulBalance-payment;
			//更新预测数的资金头寸
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
					forecasttc, cashRowID, startDate);
			//更新预测数的调节后的资金头寸
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
					forecasttc, adjustCashRowID, startDate);
			
			//更新第二天的期初余额
			destinationDataDAO.updateInitBalanceByLastDateEndBalance(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), DataFormat.getNextDate(
							startDate, 1));
			//更新预测数的差额调节
			destinationDataDAO.updateAmountByLineIDAndTransactionDate(
					officeTimeInfo.getNOFFICEID(), officeTimeInfo
							.getNCURRENCYID(), 0, balanceRegulateRowID, startDate);

			startDate = DataFormat.getNextDate(startDate, 1);
			i++;
		}//end while
		destinationDataDAO.updatePlanAmountAfterTransform(officeTimeInfo
				.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
				currentDate);
		//
		/*
		 * //if(DataFormat.getWeekDay(DataFormat.getTreasuryPlanExecuteDate()) // ==
		 * Calendar.FRIDAY)//如果是周五的数据操作，则更新周六的期初计划数据与周六期初应计数据相等 if
		 * (DataFormat.getWeekDay(DataFormat.getTreasuryPlanExecuteDate()) ==
		 * Calendar.MONDAY)//如果是周五的数据操作，则更新周六的期初计划数据与周六期初应计数据相等 System.out
		 * .println("-----------updatePlanAmountAsForecastAmount");
		 * destinationDataDAO.updatePlanAmountAsForecastAmount(officeTimeInfo
		 * .getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), 1,
		 * DataFormat.getNextDate(DataFormat .getTreasuryPlanExecuteDate(),
		 * 1));
		 */
	}

	tpConn.close();
}

/**
 * @param currentDate
 * @param destinationDataDAO 如果预测首日计划准备金，则传入Trea_TPActualDataDAO,否则传入Trea_TPForecastDataDAO
 * @param systemParameterDAO
 * @param officeTimeInfo
 * @param startDate
 * @throws Exception
 */
private double updatePlanRevseAmount(Timestamp currentDate, AbstractDestinationDataDAO destinationDataDAO, Trea_TPForecastDataDAO forecastDataDAO,Trea_SystemParameterDAO systemParameterDAO, OfficeTimeInfo officeTimeInfo, Timestamp startDate) throws Exception {
	//更新计划数据 准备金
	
	if(destinationDataDAO instanceof Trea_TPForecastDataDAO)
		destinationDataDAO.setAmountFieldNameAsPlanAmount();
	
	double lastDayReverse = destinationDataDAO
			.getAmountByLineIDAndTransactionDate(officeTimeInfo
					.getNOFFICEID(), officeTimeInfo
					.getNCURRENCYID(), needMoneyRowID, DataFormat
					.getNextDate(startDate, -1));//3659
	destinationDataDAO.resetAmountFieldName();
	
	forecastDataDAO.setAmountFieldNameAsPlanAmount();
	
	double todayEnterpriseDeposit = forecastDataDAO
			.getAmountByLineIDAndTransactionDate(officeTimeInfo
					.getNOFFICEID(), officeTimeInfo
					.getNCURRENCYID(), enterpriseDeposit, startDate);

	double todayEnterpriseWithdraw = forecastDataDAO
			.getAmountByLineIDAndTransactionDate(officeTimeInfo
					.getNOFFICEID(), officeTimeInfo
					.getNCURRENCYID(), enterpriseWithdraw, startDate);

	SystemParameterInfo reserveParameterInfo1 = new SystemParameterInfo();
	reserveParameterInfo1.setEffectiveDate(startDate);
	reserveParameterInfo1.setStatusID(1);
	reserveParameterInfo1.setParameterName("Paymentrate");
	reserveParameterInfo1 = systemParameterDAO
			.findLastEffectiveDate(reserveParameterInfo1);
	double lastDayReveseRate = reserveParameterInfo1
			.getParameterValue() / 100.0;

	SystemParameterInfo reserveParameterInfo2 = new SystemParameterInfo();
	reserveParameterInfo2.setEffectiveDate(startDate);
	reserveParameterInfo2.setStatusID(1);
	reserveParameterInfo2.setParameterName("Paymentrate");
	reserveParameterInfo2 = systemParameterDAO
			.findLastEffectiveDate(reserveParameterInfo1);
	double todayReveseRate = (reserveParameterInfo1
			.getParameterValue()) / 100.0;

	//本日必要备付金=上日必要备付金/备付金率 + 本日行项目“企业存款” - 本日行项目“企业支款” * 备付金率

	double todayReverse = (lastDayReverse / lastDayReveseRate + todayEnterpriseDeposit - todayEnterpriseWithdraw)
			* todayReveseRate;
	todayReverse = DataFormat.formatDouble(todayReverse);

	System.out.println("---------lastDayReverse:"
			+ lastDayReverse);
	System.out.println("---------lastDayReveseRate:"
			+ lastDayReveseRate);
	System.out.println("---------todayEnterpriseDeposit:"
			+ todayEnterpriseDeposit);
	System.out.println("---------todayEnterpriseWithdraw:"
			+ todayEnterpriseWithdraw);
	System.out.println("---------todayReveseRate:"
			+ todayReveseRate);
	System.out.println("---------todayReverse:" + todayReverse);

	forecastDataDAO.updateAmountByTransactionDateAndLineID(
			officeTimeInfo.getNOFFICEID(), officeTimeInfo
					.getNCURRENCYID(), startDate, needMoneyRowID,
			todayReverse, currentDate);

	////
	/*
	 * //计划期初可用余额 double planAmount1 =
	 * destinationDataDAO.getAmountByLineIDAndTransactionDate(officeTimeInfo.getNOFFICEID(),
	 * officeTimeInfo.getNCURRENCYID(), 1, startDate); double
	 * planAmount2 =
	 * destinationDataDAO.getAmountByLineIDAndTransactionDate(officeTimeInfo.getNOFFICEID(),
	 * officeTimeInfo.getNCURRENCYID(), 2, startDate); double
	 * planAmount1830 =
	 * destinationDataDAO.getAmountByLineIDAndTransactionDate(officeTimeInfo.getNOFFICEID(),
	 * officeTimeInfo.getNCURRENCYID(), 1830, startDate);
	 * 
	 * //计划期末可用余额 double planEndBalance = planAmount1 +
	 * planAmount2- planAmount1830; /////
	 * 
	 * destinationDataDAO.updateAmountByLineIDAndTransactionDate(
	 * officeTimeInfo.getNOFFICEID(), officeTimeInfo
	 * .getNCURRENCYID(), endBalance - payment, 3660,
	 * startDate);
	 */


	forecastDataDAO.resetAmountFieldName();
	return todayReverse;
}
}
