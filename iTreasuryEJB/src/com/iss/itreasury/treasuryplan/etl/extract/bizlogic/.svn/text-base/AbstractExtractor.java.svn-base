/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-7
 */
package com.iss.itreasury.treasuryplan.etl.extract.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.loan.LoanContractPlanInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.treasuryplan.util.TPUtil;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 *
 * 资金计划数据抽取抽象类
 */
public abstract class AbstractExtractor {
	

	private Connection tpConn = null; 
	
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	
	public void startExtractData(String[] args) throws Exception{

		Timestamp currentDate = null;
		Timestamp transDate = null;
		log.debug("-----------今天是: "+ currentDate);		
		log.debug("---------------开始抽取数据-----------");
		Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();
		Collection c = extractorUtilDAO.findAllOfficeTime();
		Iterator it = c.iterator();
		//Timestamp today = Timestamp.valueOf("2004-05-28 00:00:00.000000000");
		//extractActualAmount(1,1,today,today);
		while(it.hasNext()){
			OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
			log.debug("---------正在处理的OfficeTimeInfo: "+ officeTimeInfo);
			if(args != null && args.length > 0 && args[0] != null && args[1] != null){
				currentDate = Timestamp.valueOf(args[0]+" 00:00:00.000000000");
				if(officeTimeInfo.getNCURRENCYID() == 1)//只针对人民币
					transDate = Timestamp.valueOf(args[1]+" 00:00:00.000000000");
				else{
					transDate = officeTimeInfo.getDTOPENDATE();
					//交易时间更改为取系统时间
					//transDate = DataFormat.getTreasuryPlanExecuteDate();
				}
			}else{
				currentDate = DataFormat.getTreasuryPlanExecuteDate();
				transDate = officeTimeInfo.getDTOPENDATE();
				//交易时间更改为取系统时间
				//transDate = DataFormat.getTreasuryPlanExecuteDate();				
			}									
			
			//如果系统状态为“关机”才运行资金计划
			Timestamp tempTransDate = transDate;
			long lStatusID = Constant.SystemStatus.getID(officeTimeInfo.getSSYSTEMSTATUSDESC());
			if (lStatusID==Constant.SystemStatus.CLOSE)
			{
				//如果关机日之前的日期有未执行资金计划的，则自动补上这些日期的实际数据
				long maxDayCount = TPConstant.DayCount.getInstance().getMaxDayCount();//
				long interval = TPUtil.getInterval(transDate);
				long n=interval-1;
				if (n >= 0)
				{
					for (int m=0;m<=n && maxDayCount>0;m++,maxDayCount--)
					{
						transDate = DataFormat.getNextDate(tempTransDate, -m);
						System.out.println("transDate="+transDate);
						extractActualAmount(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,transDate);
						System.out.println("transDate="+transDate);
						extractActualBalance(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,transDate);
					}					
				}
				
				transDate = DataFormat.getNextDate(tempTransDate, 1);
				for(int i= 0;i<365;i++){
					extractForcastAmount(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,transDate);
					extractForcastBalance(officeTimeInfo.getNOFFICEID(),officeTimeInfo.getNCURRENCYID(),currentDate,transDate);
					transDate = DataFormat.getNextDate(transDate, 1);
				}
			}
		}
		
		extractorUtilDAO.closeModuleConn();
	}
	
	abstract public void extractActualBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception ;

	abstract public void extractForcastBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception ;
	
	abstract public void extractActualAmount(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception;
	
	abstract public void extractForcastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forcastDate) throws Exception;
	
	
	protected double caculateInterest(Timestamp transactionDate,Timestamp dtInit,Timestamp dtInterestStart,Timestamp dtInterestEnd,double rate,long interestRateDaysFlag,ArrayList infoList,double initBalance){
		log.debug("---------开始利息匡算--------------");
		double interest = 0.0;
		InterestCalculation iCal = new InterestCalculation();
		if(dtInterestStart.before(dtInit) || infoList.size() == 0)
			return -1.0;

		double balance = 0.0;
		//获取日期:
		//1.早于起息日的最晚一个日期
		int I = -1;
		//2.早于止息日的最晚一个日期
		int J = -1;
		//如果获取早于起息日的最晚一个日期失败，但是起息日后存在发生额，从起息日开始计算利息
		int M = -1;
		//如果获取早于结息日的最晚一个日期失败，但是结息日后存在发生额，利息计算到结息日
		int N = -1;		
		boolean isEndAddBalance = false;
		//上次计息日期
		Timestamp lastInterestSettDay = dtInterestStart;
		
		boolean bCaculate3MonthInterest = false;
		for(int i=0;i<infoList.size();i++){
			LoanContractPlanInfo info = (LoanContractPlanInfo) infoList.get(i);
			log.debug("---------LoanContractPlanInfo"+info);			
			Timestamp tmpDate = info.getPlanDate();
			Timestamp conditoin = Timestamp.valueOf("1999-06-22 00:00:00.000000000");
			if(tmpDate.equals(conditoin)){
				int z = 0;
			}
			if(tmpDate.after(dtInterestStart) && !isEndAddBalance){//第一个晚于起息日的交易发生日期(因为数组是按照时间排序)
				if(tmpDate.before(dtInterestEnd)){//发生额在起息日和结息日之间
					if(i-1 >= 0){
						I = i-1;
					}else{//虽然没有获得合法的"早于起息日的最晚一个日期",但是起息日后的发生额存在，从起息日开始分段计算利息
						M=i;
					}
				}else{//在起息日和结息日之间没有任何发生额，利息取起息日到结息日之间的利息
					bCaculate3MonthInterest = true;
				}

				isEndAddBalance = true;
			}
			
			if(!isEndAddBalance){
				balance += info.getAmount();
			}

			if(tmpDate.after(dtInterestEnd) && tmpDate.before(dtInterestStart)){//第一个晚于止息日的交易发生日期(因为数组是按照时间排序)
				if(i-1 >= 0){
					J = i-1;
				}else{
					N = i;
				}	
				break;
				
			}			
		}
		if(I < 0 && !bCaculate3MonthInterest)//没有得到合法的I,因此余额计算也不正确
			balance = 0.0;
		
		int intervalDays = 0;
		


		if(I<0 && J > 0){//发生日期全部在起息日与结息日之间
		}else if( I > 0 && J < 0){
			J = I;
		}else if( I < 0 && J > 0){
			I = M;
			int intervalDays1 = 0;
			LoanContractPlanInfo info = (LoanContractPlanInfo) infoList.get(J);
			try {
				intervalDays1 = (int) iCal.getIntervalDays(dtInterestStart,info.getPlanDate(),1);
			} catch (IException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//计算从起息日到起息日后第一个发生日的利息
			interest = interest +  balance *intervalDays1*rate/100.0/interestRateDaysFlag;			
		}else if(I > 0 && J > 0){
			I = I+1;
		}else{
			if(infoList.size() >=2){
				LoanContractPlanInfo info1 = (LoanContractPlanInfo) infoList.get(0);
				LoanContractPlanInfo info2 = (LoanContractPlanInfo) infoList.get(infoList.size()-1);
				if(!info1.getPlanDate().after(dtInterestStart) && !info2.getPlanDate().before(dtInterestEnd)){
					int intervalDaysOf3Months=0;
					try {
						intervalDaysOf3Months = (int) iCal.getIntervalDays(dtInterestStart,dtInterestEnd,1);
					} catch (IException e1) {
						e1.printStackTrace();
					}
					interest = balance *intervalDaysOf3Months*rate/100.0/interestRateDaysFlag;
					return interest;
				}
			}
			return 0.0;
		}
		
		if(I < infoList.size()){
			for(int k=I;k<J;k++){
				LoanContractPlanInfo info = (LoanContractPlanInfo) infoList.get(k);
				Timestamp tmpDate = info.getPlanDate();
				try {
					intervalDays = (int) iCal.getIntervalDays(lastInterestSettDay,tmpDate,1);
				} catch (IException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				interest = interest +  balance *intervalDays*rate/100.0/interestRateDaysFlag;
				lastInterestSettDay = tmpDate;
			}
		}
		int intervalDays2=0;
		try {
			intervalDays2 = (int) iCal.getIntervalDays(lastInterestSettDay,dtInterestEnd,1);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		利息=利息+余额*（止息日-上次计息日期）*利率/100/年基数
		interest = interest +  balance *intervalDays2*rate/100.0/interestRateDaysFlag;
		return interest;
	}
	


	
}
