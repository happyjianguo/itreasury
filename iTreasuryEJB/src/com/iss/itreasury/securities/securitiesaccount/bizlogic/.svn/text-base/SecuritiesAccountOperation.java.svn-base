package com.iss.itreasury.securities.securitiesaccount.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_AccountTypeDAO;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountInfo;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.setting.dao.SEC_TransactionTypeDAO;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Log4j;




public class SecuritiesAccountOperation {
	
	private SecuritiesAccount securitiesAccountFacade = null;
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	
	public SecuritiesAccountOperation(boolean isNeedInitEJB)throws RemoteException{
		if(isNeedInitEJB){
			try
			{
				SecuritiesAccountHome home;
				try {
					home =
						(SecuritiesAccountHome) EJBHomeFactory.getFactory().lookUpHome(
						SecuritiesAccountHome.class);
				} catch (Exception e) {
					throw new RemoteException("EJBHomeFactory连接错误",e);
				}
				securitiesAccountFacade = (SecuritiesAccount) home.create();
			}
	
			catch (CreateException ce)
			{
				throw new RemoteException("发生CreateException",ce);
			}		
		}
	
	}
	/**
	 * 收款保存/暂存时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void receive(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.receive(accountParam);	
	}


	/**
	 * 收款删除时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void cancelReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.cancelReceipt(accountParam);		
	}
	
	
	/**
	 * 收款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void deliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.deliverReceipt(accountParam);		
	}
	
	/**
	 * 取消收款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void cancelDeliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.cancelDeliverReceipt(accountParam);		
	}	
	
	/**
	 * 付款保存/暂存时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void pay(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.pay(accountParam);		
	}
	
	/**
	 * 付款删除时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void cancelPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.cancelPayment(accountParam);		
	}
	
	/**
	 * 付款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void deliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.deliverPayment(accountParam);		
	}
	
	/**
	 * 取消付款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void cancelDeliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		securitiesAccountFacade.cancelDeliverPayment(accountParam);		
	}	
	
	
	/**
	 * 根据查询条件获取帐务类型信息
	 * */	
	public Collection getSecuritiesSubjectCode(long officeID,
			long currencyID,
			long accountType,
			long termTypeID,
			long term,
			long counterpartID,
			long clientID,
			long exchangeCenterCode,
			String seatCode,
			long accountID,
			long securitiesID,
			long subjectType) throws SecuritiesException{
		SEC_AccountTypeDAO dao = new SEC_AccountTypeDAO();
		try {
			return dao.getSecuritiesSubjectCode(officeID, currencyID, accountType, termTypeID, term, counterpartID, clientID, exchangeCenterCode, seatCode, accountID, securitiesID, subjectType);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 * 根据资金账户ID获取对应的科目号
	 * */
	public String getAccountSubjectCode(long accountID) throws SecuritiesException{
		SEC_AccountDAO accDAO = new SEC_AccountDAO();
		AccountInfo accInfo = null;
		try {
			accInfo = (AccountInfo) accDAO.findByID(accountID, AccountInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(accInfo == null || accInfo.getId() == -1){
			throw new AccounStatusException("Sec_E102");
		}
		
		if(accInfo.getAccountSubject() == null || accInfo.getAccountSubject().compareToIgnoreCase("") == 0){
			throw new SecuritiesException("Sec_E107",accInfo.getAccountCode(),null);
		}
		
		return accInfo.getAccountSubject();
	}
	
	/**
	 * 针对一个资金帐户，从起日到止日，进行资金帐户日结
	 * @remark 资金的日结，在同一日内与顺序与关，只是余额的增减。
	 * */
	public void caculateSingleAccountDailyStock(long accountID, Timestamp sDate,Timestamp eDate)throws RemoteException,SecuritiesException{
		securitiesAccountFacade.caculateSingleAccountDailyStock(accountID, sDate,eDate);
	}
	
	/**
	 * 检查当前资金账户的可用余额是否不足　提示　资金帐户[?]可用余额不足
	 * 当前余额 + 待交割收款挂帐金额 - 待交割付款挂帐金额 < 交易金额　则透支
	* @param accountID 资金帐户ID
	* @param transactionTypeID 交易类型ID
	* @param transAmount 交易金额 
	* @return
	* @throws　AccounStatusException
	 */	
	public void checkIsOverDraft(long accountID, long transactionTypeID, double transAmount)throws SecuritiesException,RemoteException{
		SEC_TransactionTypeDAO transationTypeDAO = new SEC_TransactionTypeDAO();
		
		SEC_AccountDAO accountDAO = new SEC_AccountDAO(); 
		AccountInfo accInfo = null;
		try{
			TransactionTypeInfo transactionTypeInfo = (TransactionTypeInfo) transationTypeDAO.findByID(transactionTypeID, TransactionTypeInfo.class);
			if(transactionTypeInfo.getCapitalDirection() != SECConstant.Direction.PAY
			 && transactionTypeInfo.getCapitalDirection() != SECConstant.Direction.PAY_AND_FINANCE_RECEIVE){//不是付款操作不需要透支检查
			 	log.debug("资金方向不是 资金帐户付 或 资金帐户付，银行收，不进行透支检查");	
				return;
			 }
			
			accInfo = (AccountInfo) accountDAO.findByID(accountID,AccountInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("被处理的资金账户为: "+accInfo);				
		
		securitiesAccountFacade.checkIsOverDraft(accInfo, transAmount);
	}
	
}
