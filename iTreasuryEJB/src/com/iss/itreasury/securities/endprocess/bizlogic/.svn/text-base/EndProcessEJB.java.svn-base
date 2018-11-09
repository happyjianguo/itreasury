/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.endprocess.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.ejb.SessionBean;

import com.iss.itreasury.closesystem.GLDealBean;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.DeliveryOrderServiceOperation;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostParam;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesaccount.bizlogic.SecuritiesAccountOperation;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_DailyAccountDAO;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountInfo;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.stock.dao.SEC_DailyStockDAO;
import com.iss.itreasury.securities.stock.dao.SEC_SecuritiesStockDAO;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockInfo;
import com.iss.itreasury.securities.stock.dataentity.SingleAccountDailyStockParam;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SECUtil;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EndProcessEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 320609679760846163L;
	
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * 资金库存日结
	 * */
	public void endProcess(long officeID,long currencyID)  throws RemoteException,SecuritiesException{
		try{
		log.debug("----------开始资金库存日结-----------");
		
		DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation();
		
		deliveryOrderServiceOperation.deliverAutomatically(officeID, currencyID);
		
		
		DeliveryOrderServiceOperation doService = new DeliveryOrderServiceOperation();
		
		SecuritiesAccountOperation accountOperation = new SecuritiesAccountOperation(true);
		StockOperation stockOperation = new StockOperation();
		
		boolean isSystemFirstRunDate = false;		
		
		Timestamp today = Env.getSecuritiesSystemDate(officeID, currencyID);
		log.debug("----------今天即日结结束日是:"+today);
		SEC_AccountDAO accountDAO = new SEC_AccountDAO();
		ArrayList accountIDs = (ArrayList) accountDAO.getAllIDs();	
		
		log.debug("----------开始资金日结-----------");
		for(int i=0;i<accountIDs.size();i++){
			long accountID = ((Long)accountIDs.get(i)).longValue();
			log.debug("----------资金帐户ID为:"+accountID);
			SEC_DailyAccountDAO dailyAccountDAO = new SEC_DailyAccountDAO();
			Timestamp lastEndOfProcessDate = dailyAccountDAO.findLastEndProcessTime(accountID);
			log.debug("----------帐户日结表记录的上次资金日结的时间为:"+lastEndOfProcessDate);

			if(lastEndOfProcessDate == null){
				isSystemFirstRunDate = true;
				//String initTime = "2004-03-23";
				//lastEndOfProcessDate = DataFormat.getDateTime(initTime);		
				
				//zpli modify 2005-09-20
				lastEndOfProcessDate = SECUtil.getSEC_FIRST_RUN_DATE();
				//lastEndOfProcessDate = DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE);

				log.debug("----------帐户为新生成，无上次日结时间，取系统上线时间为:"+lastEndOfProcessDate);
			}
			//
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			//获取“已做过日结，但后来又被修改的交割单”的交割日期
			Timestamp deliveryDateThatHadSettledAndModified = deliveryOrderDAO.getDeliveryDateThatHadSettledAndModified(accountID, -1,lastEndOfProcessDate);
			log.debug("----------已做过日结，但后来又被修改的交割单的交割日期为:"+deliveryDateThatHadSettledAndModified);			
			//获取上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期
			Timestamp date3 = deliveryOrderDAO.getDeliveryDateThatInputAfterSettlementButDeliverBeforeSettlement(accountID, -1,-1,lastEndOfProcessDate);
			log.debug("----------上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期为:"+date3);			
			//获取上次日结的下一天
			//lastEndOfProcessDate = DataFormat.getNextDate(lastEndOfProcessDate, 1);
			log.debug("----------帐户表记录的上次资金日结的时间为:"+lastEndOfProcessDate);
			//获取以上三个日期种最早的时间作为本次日结开始日
			//Timestamp dailySettlementDate = getStartDateOfEndProcess(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,date3,isSystemFirstRunDate);
			//d1:上次资金日结日期
			//d2:已做过日结，但后来又被修改的交割单
			//d3:上次日结日期之后录入的，但交割日是上次日结日期之前的交割单
			//d4:资金帐户开户日期
			//d5:上线日期
			AccountInfo accountInfo = null; 
			try {
				accountInfo = (AccountInfo) accountDAO.	findByID(accountID, AccountInfo.class);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
			Timestamp accountOpenDate = accountInfo.getOpenDate();
			log.debug("----------该帐户的开户日期为:"+accountOpenDate);				
			
			//zpli modify 2005-09-20
			Timestamp dailySettlementDate = this.getStartDate(
						lastEndOfProcessDate,
						deliveryDateThatHadSettledAndModified, date3,
						accountInfo.getOpenDate(), SECUtil.getSEC_FIRST_RUN_DATE());
				//Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,date3,accountInfo.getOpenDate(),DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE));
			
			log.debug("----------本次日结开始日为:"+dailySettlementDate);
			//针对一个资金帐户，从起日到止日，进行资金帐户日结
			accountOperation.caculateSingleAccountDailyStock(accountID, dailySettlementDate, today);
		}//end of for1
		log.debug("----------结束资金日结-----------");
		
		SEC_SecuritiesStockDAO securitiesStockDAO = new SEC_SecuritiesStockDAO();
		ArrayList stockList = (ArrayList) securitiesStockDAO.findAll();
		log.debug("----------开始库存日结-----------");
		for(int j=0;j<stockList.size();j++){
			SecuritiesStockInfo stockInfo = (SecuritiesStockInfo) stockList.get(j);
			log.debug("----------被处理的库存记录为:"+stockInfo);
			//确定日结起日
			SEC_DailyStockDAO dailyStockDAO = new SEC_DailyStockDAO();  
			Timestamp lastEndOfProcessDate = dailyStockDAO.findLastEndProcessTime(stockInfo.getAccountID(), stockInfo.getClientID(), stockInfo.getSecuritiesID());
			isSystemFirstRunDate = false;
			if(lastEndOfProcessDate == null){
				isSystemFirstRunDate = true;
//				String initTime = "2004-03-23";
//				lastEndOfProcessDate = DataFormat.getDateTime(initTime);	
				
				//zpli modify 2005-09-20				
				lastEndOfProcessDate = SECUtil.getSEC_FIRST_RUN_DATE();	
				//lastEndOfProcessDate = DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE);
				
				log.debug("----------库存为新生成，无上次日结时间，取系统上线时间为:"+lastEndOfProcessDate);
			}			
			log.debug("----------lastEndOfProcessDate:"+lastEndOfProcessDate);
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			//获取“已做过日结，但后来又被修改的交割单”的交割日期
			Timestamp deliveryDateThatHadSettledAndModified = deliveryOrderDAO.getDeliveryDateThatHadSettledAndModified(stockInfo.getAccountID(), stockInfo.getClientID(),lastEndOfProcessDate);
			log.debug("----------已做过日结，但后来又被修改的交割单的交割日期为:"+deliveryDateThatHadSettledAndModified);			
			//获取上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期
			Timestamp deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement = deliveryOrderDAO.getDeliveryDateThatInputAfterSettlementButDeliverBeforeSettlement(stockInfo.getAccountID(), stockInfo.getClientID(),stockInfo.getSecuritiesID(),lastEndOfProcessDate);
			log.debug("----------上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期为:"+deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement);			
			//获取上次日结的下一天 
			//lastEndOfProcessDate = DataFormat.getNextDate(lastEndOfProcessDate, 1);
			log.debug("----------帐户表记录的上次资金日结的时间为:"+lastEndOfProcessDate);
			//获取以上三个日期种最早的时间作为本次日结开始日
			//Timestamp dailySettlementDate = getStartDateOfEndProcess(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,isSystemFirstRunDate);
			//d1:上次资金日结日期
			//d2:已做过日结，但后来又被修改的交割单
			//d3:上次日结日期之后录入的，但交割日是上次日结日期之前的交割单
			//d4:资金帐户开户日期
			//d5:上线日期			
			
			
			//zpli modify 2005-09-20
			Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,null,SECUtil.getSEC_FIRST_RUN_DATE());					
			//Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,null,DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE));
			
			log.debug("----------本次日结开始日为:"+dailySettlementDate);			
			SingleAccountDailyStockParam singleAccountDailyStockParam = new SingleAccountDailyStockParam(); 
			singleAccountDailyStockParam.setAccountID(stockInfo.getAccountID());
			singleAccountDailyStockParam.setClientID(stockInfo.getClientID());
			singleAccountDailyStockParam.setSecuritiesID(stockInfo.getSecuritiesID());
			singleAccountDailyStockParam.setStartDate(dailySettlementDate);
			singleAccountDailyStockParam.setEndDate(today);
			stockOperation.caculateSingleAccountDailyStock(singleAccountDailyStockParam);
			
		}
		log.debug("----------结束库存日结-----------");
		log.debug("----------完成资金库存日结-----------");
		
		CarryCostParam param = new CarryCostParam();
		doService.carryCost(param);
		}catch(SecuritiesException secExp){
			mySessionCtx.setRollbackOnly();
			throw secExp;
		}
		
	}
	
	/**
	 * 向Oracle-Finance过帐
	 * */
	public void postGLVoucher(long lOfficeID, long lCurrencyID, Timestamp tsStart, Timestamp tsEnd) throws RemoteException,SecuritiesException
	{
        try
        {
        	/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
        	{
        		//Notes:国机项目，系统关机过账修改，gqfang,2005-02-03
        		System.out.println("Log =======================项目名称：CNMEF");
        		for (Timestamp tsTempDate = DataFormat.getDateTime(DataFormat.getDateString(tsStart)); DataFormat.getDateString(tsTempDate).compareTo(DataFormat.getDateString(tsEnd)) <= 0; tsTempDate = DataFormat.getDateTime(DataFormat.getDateString(DataFormat.getNextDate(tsTempDate))) )
        		{
        			GLDealBean.postGLVoucher(lOfficeID, lCurrencyID, Constant.ModuleType.SECURITIES, DataFormat.getDateTime(DataFormat.getDateString(tsTempDate)), DataFormat.getDateTime(DataFormat.getDateString(tsTempDate)));
        		}
        	}
        	else
        	{*/
        		GLDealBean.postGLVoucher(lOfficeID, lCurrencyID, Constant.ModuleType.SECURITIES, tsStart, tsEnd);
        	//}
        	
        }
        catch(Exception e)
        {
            throw new SecuritiesException();
        }
	}
	
	
	/**
	 * 获取系统日结的时间中应该作为起始日的时间
	 * @param date1 上次日结时间 不能为空
	 * @param date2 已做过日结，但后来又被修改的交割单的交割日期 可以为空
	 * @param date3 获取上次日结日期之后录入的，但交割日是上次日结日期之前的交割日期 可以为空
	 * date2 和 date3中获取最早的时间->date4
	 * date4和date1比较获取最晚的
	 * @return 日结的起始时间
	 * */
	private Timestamp getStartDateOfEndProcess(Timestamp date1,Timestamp date2,Timestamp date3,boolean isSystemFirstRunDate){
		Timestamp earliestDate = null;
		Timestamp endDate = null;
		if(date2 != null && date3 != null){
			if(date2.before(date3))
				earliestDate = date2;
			else
				earliestDate = date3;
		}else{
			if(date2 != null)
				earliestDate = date2;
			else if(date3 != null)
				earliestDate = date3;
		}
		
		if(isSystemFirstRunDate){
			if(earliestDate != null){
				if(earliestDate.after(date1))
					endDate = earliestDate;
				else
					endDate = date1;
			}else
				endDate = date1;
		}else{
			if(earliestDate != null){
				if(earliestDate.before(date1))
					endDate = earliestDate;
				else
					endDate = date1;
			}else
				endDate = date1;			
		}
		

		
		return endDate;
	}
	
	//日结起日= max (min(a,b,c),d,e)
	private Timestamp getStartDate(Timestamp d1,Timestamp d2,Timestamp d3,Timestamp d4,Timestamp d5){
		Timestamp earliestDate = getEarliestDate(d1,d2,d3);
		Timestamp latestDate = getlatestDate(earliestDate,d4,d5);
		return latestDate;
	}
	
	private Timestamp getEarliestDate(Timestamp d1,Timestamp d2,Timestamp d3){
		Timestamp earliestDate = null;
		
		earliestDate = getEarliestDate(d1,d2);
		earliestDate = getEarliestDate(earliestDate,d3);
		return earliestDate;		
	}
	
	private Timestamp getEarliestDate(Timestamp d1,Timestamp d2){
		Timestamp earliestDate = null;
		if(d1 != null && d2 != null){
			if(d1.before(d2))
				earliestDate = d1;
			else
				earliestDate = d2;
		}else if(d1 == null && d2 != null){
			earliestDate = d2;
		}else if(d1 != null && d2 == null){
			earliestDate = d1;
		}
		return earliestDate;
	}
	
	
	private Timestamp getlatestDate(Timestamp d1,Timestamp d2,Timestamp d3){
		Timestamp earliestDate = null;
		
		earliestDate = getlatestDate(d1,d2);
		earliestDate = getlatestDate(earliestDate,d3);
		return earliestDate;		
	}
	
	private Timestamp getlatestDate(Timestamp d1,Timestamp d2){
		Timestamp earliestDate = null;
		if(d1 != null && d2 != null){
			if(d1.after(d2))
				earliestDate = d1;
			else
				earliestDate = d2;
		}else if(d1 == null && d2 != null){
			earliestDate = d2;
		}else if(d1 != null && d2 == null){
			earliestDate = d1;
		}
		return earliestDate;
	}	
	

}
