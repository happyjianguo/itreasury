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
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException �쳣˵����
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
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	/**
	 * �ʽ����ս�
	 * */
	public void endProcess(long officeID,long currencyID)  throws RemoteException,SecuritiesException{
		try{
		log.debug("----------��ʼ�ʽ����ս�-----------");
		
		DeliveryOrderServiceOperation deliveryOrderServiceOperation = new DeliveryOrderServiceOperation();
		
		deliveryOrderServiceOperation.deliverAutomatically(officeID, currencyID);
		
		
		DeliveryOrderServiceOperation doService = new DeliveryOrderServiceOperation();
		
		SecuritiesAccountOperation accountOperation = new SecuritiesAccountOperation(true);
		StockOperation stockOperation = new StockOperation();
		
		boolean isSystemFirstRunDate = false;		
		
		Timestamp today = Env.getSecuritiesSystemDate(officeID, currencyID);
		log.debug("----------���켴�ս��������:"+today);
		SEC_AccountDAO accountDAO = new SEC_AccountDAO();
		ArrayList accountIDs = (ArrayList) accountDAO.getAllIDs();	
		
		log.debug("----------��ʼ�ʽ��ս�-----------");
		for(int i=0;i<accountIDs.size();i++){
			long accountID = ((Long)accountIDs.get(i)).longValue();
			log.debug("----------�ʽ��ʻ�IDΪ:"+accountID);
			SEC_DailyAccountDAO dailyAccountDAO = new SEC_DailyAccountDAO();
			Timestamp lastEndOfProcessDate = dailyAccountDAO.findLastEndProcessTime(accountID);
			log.debug("----------�ʻ��ս���¼���ϴ��ʽ��ս��ʱ��Ϊ:"+lastEndOfProcessDate);

			if(lastEndOfProcessDate == null){
				isSystemFirstRunDate = true;
				//String initTime = "2004-03-23";
				//lastEndOfProcessDate = DataFormat.getDateTime(initTime);		
				
				//zpli modify 2005-09-20
				lastEndOfProcessDate = SECUtil.getSEC_FIRST_RUN_DATE();
				//lastEndOfProcessDate = DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE);

				log.debug("----------�ʻ�Ϊ�����ɣ����ϴ��ս�ʱ�䣬ȡϵͳ����ʱ��Ϊ:"+lastEndOfProcessDate);
			}
			//
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			//��ȡ���������սᣬ�������ֱ��޸ĵĽ�����Ľ�������
			Timestamp deliveryDateThatHadSettledAndModified = deliveryOrderDAO.getDeliveryDateThatHadSettledAndModified(accountID, -1,lastEndOfProcessDate);
			log.debug("----------�������սᣬ�������ֱ��޸ĵĽ���Ľ�������Ϊ:"+deliveryDateThatHadSettledAndModified);			
			//��ȡ�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ�������
			Timestamp date3 = deliveryOrderDAO.getDeliveryDateThatInputAfterSettlementButDeliverBeforeSettlement(accountID, -1,-1,lastEndOfProcessDate);
			log.debug("----------�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ�������Ϊ:"+date3);			
			//��ȡ�ϴ��ս����һ��
			//lastEndOfProcessDate = DataFormat.getNextDate(lastEndOfProcessDate, 1);
			log.debug("----------�ʻ����¼���ϴ��ʽ��ս��ʱ��Ϊ:"+lastEndOfProcessDate);
			//��ȡ�������������������ʱ����Ϊ�����սῪʼ��
			//Timestamp dailySettlementDate = getStartDateOfEndProcess(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,date3,isSystemFirstRunDate);
			//d1:�ϴ��ʽ��ս�����
			//d2:�������սᣬ�������ֱ��޸ĵĽ��
			//d3:�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ��
			//d4:�ʽ��ʻ���������
			//d5:��������
			AccountInfo accountInfo = null; 
			try {
				accountInfo = (AccountInfo) accountDAO.	findByID(accountID, AccountInfo.class);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
			Timestamp accountOpenDate = accountInfo.getOpenDate();
			log.debug("----------���ʻ��Ŀ�������Ϊ:"+accountOpenDate);				
			
			//zpli modify 2005-09-20
			Timestamp dailySettlementDate = this.getStartDate(
						lastEndOfProcessDate,
						deliveryDateThatHadSettledAndModified, date3,
						accountInfo.getOpenDate(), SECUtil.getSEC_FIRST_RUN_DATE());
				//Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,date3,accountInfo.getOpenDate(),DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE));
			
			log.debug("----------�����սῪʼ��Ϊ:"+dailySettlementDate);
			//���һ���ʽ��ʻ��������յ�ֹ�գ������ʽ��ʻ��ս�
			accountOperation.caculateSingleAccountDailyStock(accountID, dailySettlementDate, today);
		}//end of for1
		log.debug("----------�����ʽ��ս�-----------");
		
		SEC_SecuritiesStockDAO securitiesStockDAO = new SEC_SecuritiesStockDAO();
		ArrayList stockList = (ArrayList) securitiesStockDAO.findAll();
		log.debug("----------��ʼ����ս�-----------");
		for(int j=0;j<stockList.size();j++){
			SecuritiesStockInfo stockInfo = (SecuritiesStockInfo) stockList.get(j);
			log.debug("----------������Ŀ���¼Ϊ:"+stockInfo);
			//ȷ���ս�����
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
				
				log.debug("----------���Ϊ�����ɣ����ϴ��ս�ʱ�䣬ȡϵͳ����ʱ��Ϊ:"+lastEndOfProcessDate);
			}			
			log.debug("----------lastEndOfProcessDate:"+lastEndOfProcessDate);
			SEC_DeliveryOrderDAO deliveryOrderDAO = new SEC_DeliveryOrderDAO();
			//��ȡ���������սᣬ�������ֱ��޸ĵĽ�����Ľ�������
			Timestamp deliveryDateThatHadSettledAndModified = deliveryOrderDAO.getDeliveryDateThatHadSettledAndModified(stockInfo.getAccountID(), stockInfo.getClientID(),lastEndOfProcessDate);
			log.debug("----------�������սᣬ�������ֱ��޸ĵĽ���Ľ�������Ϊ:"+deliveryDateThatHadSettledAndModified);			
			//��ȡ�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ�������
			Timestamp deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement = deliveryOrderDAO.getDeliveryDateThatInputAfterSettlementButDeliverBeforeSettlement(stockInfo.getAccountID(), stockInfo.getClientID(),stockInfo.getSecuritiesID(),lastEndOfProcessDate);
			log.debug("----------�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ�������Ϊ:"+deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement);			
			//��ȡ�ϴ��ս����һ�� 
			//lastEndOfProcessDate = DataFormat.getNextDate(lastEndOfProcessDate, 1);
			log.debug("----------�ʻ����¼���ϴ��ʽ��ս��ʱ��Ϊ:"+lastEndOfProcessDate);
			//��ȡ�������������������ʱ����Ϊ�����սῪʼ��
			//Timestamp dailySettlementDate = getStartDateOfEndProcess(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,isSystemFirstRunDate);
			//d1:�ϴ��ʽ��ս�����
			//d2:�������սᣬ�������ֱ��޸ĵĽ��
			//d3:�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ��
			//d4:�ʽ��ʻ���������
			//d5:��������			
			
			
			//zpli modify 2005-09-20
			Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,null,SECUtil.getSEC_FIRST_RUN_DATE());					
			//Timestamp dailySettlementDate = this.getStartDate(lastEndOfProcessDate,deliveryDateThatHadSettledAndModified,deliveryDateThatInputAfterSettlementButDeliverBeforeSettlement,null,DataFormat.getDateTime(Env.SEC_FIRST_RUN_DATE));
			
			log.debug("----------�����սῪʼ��Ϊ:"+dailySettlementDate);			
			SingleAccountDailyStockParam singleAccountDailyStockParam = new SingleAccountDailyStockParam(); 
			singleAccountDailyStockParam.setAccountID(stockInfo.getAccountID());
			singleAccountDailyStockParam.setClientID(stockInfo.getClientID());
			singleAccountDailyStockParam.setSecuritiesID(stockInfo.getSecuritiesID());
			singleAccountDailyStockParam.setStartDate(dailySettlementDate);
			singleAccountDailyStockParam.setEndDate(today);
			stockOperation.caculateSingleAccountDailyStock(singleAccountDailyStockParam);
			
		}
		log.debug("----------��������ս�-----------");
		log.debug("----------����ʽ����ս�-----------");
		
		CarryCostParam param = new CarryCostParam();
		doService.carryCost(param);
		}catch(SecuritiesException secExp){
			mySessionCtx.setRollbackOnly();
			throw secExp;
		}
		
	}
	
	/**
	 * ��Oracle-Finance����
	 * */
	public void postGLVoucher(long lOfficeID, long lCurrencyID, Timestamp tsStart, Timestamp tsEnd) throws RemoteException,SecuritiesException
	{
        try
        {
        	/*if (Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
        	{
        		//Notes:������Ŀ��ϵͳ�ػ������޸ģ�gqfang,2005-02-03
        		System.out.println("Log =======================��Ŀ���ƣ�CNMEF");
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
	 * ��ȡϵͳ�ս��ʱ����Ӧ����Ϊ��ʼ�յ�ʱ��
	 * @param date1 �ϴ��ս�ʱ�� ����Ϊ��
	 * @param date2 �������սᣬ�������ֱ��޸ĵĽ���Ľ������� ����Ϊ��
	 * @param date3 ��ȡ�ϴ��ս�����֮��¼��ģ������������ϴ��ս�����֮ǰ�Ľ������� ����Ϊ��
	 * date2 �� date3�л�ȡ�����ʱ��->date4
	 * date4��date1�Ƚϻ�ȡ�����
	 * @return �ս����ʼʱ��
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
	
	//�ս�����= max (min(a,b,c),d,e)
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
