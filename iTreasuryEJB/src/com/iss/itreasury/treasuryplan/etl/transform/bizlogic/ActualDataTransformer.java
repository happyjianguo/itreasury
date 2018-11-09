/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-14
 */
package com.iss.itreasury.treasuryplan.etl.transform.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPActualDataDAO;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.SystemParameterInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TPUtil;


/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ActualDataTransformer extends AbstractTransformer {
	
	long beginUsefulBalanceRowID = TPConstant.RowID.getInstance().getBeginBalanceRowID();//期初可用余额
	long beginBalanceRowID = TPConstant.RowID.getInstance().getBeginBalanceRowID1();//期初余额
	long endUsefulBalanceRowID = TPConstant.RowID.getInstance().getEndBalanceRowID();//期末可用余额
	long endBalanceRowID = TPConstant.RowID.getInstance().getEndBalanceRowID1();//期末余额
	long fromRowID = TPConstant.RowID.getInstance().getFormRowID();//2
	long toRowID = TPConstant.RowID.getInstance().getToRowID();//1830
	long needMoneyRowID = TPConstant.RowID.getInstance().getNeedMoneyRowID();//3659
	long cashRowID = TPConstant.RowID.getInstance().getCashRowID();//3660
	long adjustCashRowID = TPConstant.RowID.getInstance().getAdjustCashRowID();//3660
	long balanceRegulateRowID = TPConstant.RowID.getInstance().getBalanceRegulateRowID();//3661
	long enterpriseDeposit = TPConstant.RowID.getInstance().getEnterpriseDeposit();//
	long enterpriseWithdraw = TPConstant.RowID.getInstance().getEnterpriseWithdraw();//
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer#transform(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	
	public ActualDataTransformer(){
		extractingType = Extracting_Type_Actual;
	}
	
	public void transform(String args[])throws Exception 
	{		
		Connection tpConn = null;
		try {
			Timestamp currentDate = null;
			Timestamp openDate = null;
			if(!TPScheduler.isNeedExecute(null))
				return;		
	
			
			//Timestamp currentDate = Timestamp.valueOf("2004-08-03 00:00:00.000000000");		
			
			Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();
			Collection c = extractorUtilDAO.findAllOfficeTime();
			Iterator it = c.iterator();			
			ActualDataTransformer transfomer = new ActualDataTransformer();
			tpConn = Database.getConnection();
			tpConn.setAutoCommit(false);
			Trea_TPActualDataDAO actualDataDAO = new Trea_TPActualDataDAO(tpConn);
			Trea_SystemParameterDAO systemParameterDAO = new Trea_SystemParameterDAO(tpConn);
			while(it.hasNext()){
				OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
				System.out.println("---------正在处理的OfficeTimeInfo: "+ officeTimeInfo);
				
				//如果系统状态为“关机”才运行资金计划
				long lStatusID = Constant.SystemStatus.getID(officeTimeInfo.getSSYSTEMSTATUSDESC());
				if (lStatusID!=Constant.SystemStatus.CLOSE) 
				{
					break;
				}
				
				if(args != null && args.length == 2 && args[0] != null && args[1] != null){
					currentDate = Timestamp.valueOf(args[0]+" 00:00:00.000000000");
					if(officeTimeInfo.getNCURRENCYID() == 1)//只针对人民币
						openDate = Timestamp.valueOf(args[1]+" 00:00:00.000000000");
					else{
						openDate = officeTimeInfo.getDTOPENDATE();
						//交易时间更改为取系统时间
						//openDate = DataFormat.getTreasuryPlanExecuteDate();						
					}
				}else{
					currentDate = DataFormat.getTreasuryPlanExecuteDate();
					openDate = officeTimeInfo.getDTOPENDATE();
					//交易时间更改为取系统时间
					//openDate = DataFormat.getTreasuryPlanExecuteDate();											
				}				
				System.out.println("-----------今天是: "+ currentDate);
				System.out.println("-----------OpenDate是: "+ openDate);
				System.out.println("---------------开始抽取数据-----------");
				
				long interval = TPUtil.getInterval(openDate);
				long n=interval-1;
				Timestamp tempTransDate = openDate;
				if (n >= 0)
				{
					//如果关机日之前的日期有未执行资金计划的，则自动补上这些日期的实际数据
					long maxDayCount = TPConstant.DayCount.getInstance().getMaxDayCount();
					for (int m=0;m<=n && maxDayCount>0;m++,maxDayCount--) 
					{
						openDate = DataFormat.getNextDate(tempTransDate, -m);
						transfomer.transform(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,openDate,tpConn);

						//期末可用余额
						double actual3662 = actualDataDAO
								.getAmountByLineIDAndTransactionDate(officeTimeInfo
										.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
										endUsefulBalanceRowID, openDate); //3662
						//期末余额
						double actual1287 = actualDataDAO
								.getAmountByLineIDAndTransactionDate(officeTimeInfo
										.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
										endBalanceRowID, openDate); //3662
						//期初可用余额
						double actual4 = actualDataDAO.getAmountByLineIDAndTransactionDate(
								officeTimeInfo.getNOFFICEID(), officeTimeInfo
										.getNCURRENCYID(), beginUsefulBalanceRowID, openDate);
						//期初余额
						double actual1 = actualDataDAO.getAmountByLineIDAndTransactionDate(
								officeTimeInfo.getNOFFICEID(), officeTimeInfo
										.getNCURRENCYID(), beginBalanceRowID, openDate);
						
						//来源
						double actual2 = actualDataDAO.getAmountByLineIDAndTransactionDate(
								officeTimeInfo.getNOFFICEID(), officeTimeInfo
										.getNCURRENCYID(), fromRowID, openDate);//2
						//运用
						double actual1830 = actualDataDAO
								.getAmountByLineIDAndTransactionDate(officeTimeInfo
										.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
										toRowID, openDate); //1830		
						
						//差额调节
						double actual3661 = DataFormat.cutNumberAfterDecimal(actual3662) - (DataFormat.cutNumberAfterDecimal(actual1) + DataFormat.cutNumberAfterDecimal(actual2) - DataFormat.cutNumberAfterDecimal(actual1830));
						actual3661=0;
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual3661, balanceRegulateRowID, openDate); 
						
						//更新实际数据的 资金来源和资金运用 
						//去掉不参与公式计算的行项目的值
						
						//不参与公式计算的资金来源实际数
						double unSumActualFromAmount = actualDataDAO.getUnSumAmountByLineIDAndTransactionDate(officeTimeInfo
										.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), fromRowID, openDate);
						//不参与公式计算的资金运用实际数
						double unSumActualToAmount = actualDataDAO.getUnSumAmountByLineIDAndTransactionDate(officeTimeInfo
										.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(), 6, openDate);
						
						
						actual2 = actual2 - unSumActualFromAmount;
						actual1830 = actual1830 - unSumActualToAmount;
						
						//修改资金来源(实际数)
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual2, fromRowID, openDate);
						
						//修改资金运用(实际数)
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual1830, toRowID, openDate);
						
						//更新实际数据的 期末余额=　期初+资金来源-资金运用 
						//			  期末可用余额= 期初可用余额+资金来源-资金运用
						//去掉不参与公式计算的行项目
						actual3662 = actual4 + actual2 - actual1830;
						actual1287 = actual1 + actual2 - actual1830;
						
						//修改期末可用余额(实际数)
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual3662, endUsefulBalanceRowID, openDate);
						
						//修改期末余额(实际数)
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual1287, endBalanceRowID, openDate);
						
						//必要备付金率
						SystemParameterInfo reserveParameterInfo = new SystemParameterInfo();
						reserveParameterInfo.setStatusID(1);
						reserveParameterInfo.setParameterName("Paymentrate");
						reserveParameterInfo = systemParameterDAO.find(reserveParameterInfo);
						double rate = reserveParameterInfo.getParameterValue() / 100.0;
						
						//必要备付金=企业存款*必要备付金率
						//企业存款
						double actual6 = actualDataDAO.getAmountByLineIDAndTransactionDate(
								officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),6, openDate); //6
						actual6=actual6*rate;
						//更新实际数的必要备付金
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actual6, needMoneyRowID, openDate);
						
						//资金头寸=期末可用余额-必要备付金
						//资金头寸
						double actualtc = actual3662-actual6;
						//更新实际数的资金头寸
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actualtc, cashRowID, openDate);
						//更新实际数的调节后的资金头寸
						actualDataDAO.updateAmountByLineIDAndTransactionDate(officeTimeInfo
								.getNOFFICEID(), officeTimeInfo.getNCURRENCYID(),
								actualtc, adjustCashRowID, openDate);
					}					
				}
			}
			tpConn.commit();
			tpConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				TPConstant.DayCount.setRunDayCount("-1");
				
				if(tpConn!=null)
				  tpConn.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	
	}
	
	/**
	 * 如果没有参数传入，则取系统中的数据
	 * 否则取传入数据
	 * arg0 执行日
	 * arg1 关机日（只针对人民币，其他币种仍然取数据库中的关机日）
	 * */
	public static void main(String args[])
	{
		ActualDataTransformer actualTransformer = new ActualDataTransformer();
		try 
		{
			actualTransformer.transform(args);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
