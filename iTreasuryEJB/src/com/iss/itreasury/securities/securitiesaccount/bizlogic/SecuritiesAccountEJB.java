/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiesaccount.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_AccountDAO;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_DailyAccountDAO;
import com.iss.itreasury.securities.securitiesaccount.dao.SEC_VAccountDetailDAO;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SECUtil;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountInfo;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.dataentity.DailyAccountInfo;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesAccountEJB implements SessionBean
{
	/**资金金额操作类型--增加*/
	private final static int OPERATION_ADD 	    = 1;
	/**资金金额操作类型--减少*/	
	private final static int OPERATION_SUBTRACT = 2;	
	
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
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
	 * 收款保存/暂存时资金账户的操作
	* @param accountParam 
	* @param
	* @return
	* @throws
	 */
	public void receive(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::收款-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		//更新待交割挂账金额/余额
		log.debug("-------开始更新待交割挂账金额/余额----------");
		log.debug("---交割日："+accountParam.getDeliveryDate());
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);
		if(!accountParam.getDeliveryDate().after(today))//更新余额
			addBalance(accountParam.getId(), accountParam.getAmount());
		else																		//增加收款挂账金额
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		log.debug("------结束资金账户处理::收款-----------");		
	}
	


	/**
	 * 收款删除时资金账户的操作
	* @param accountParam 
	* @param　
	* @return
	* @throws
	 */
	public void cancelReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::取消收款-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态，由于是收款反操作，收付方向实际是付
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		//检查是否透支
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());
		//更新待交割挂账金额/余额
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);		
		if(!accountParam.getDeliveryDate().after(today))//更新余额
			subtractBalance(accountParam.getId(), accountParam.getAmount());
		else																		//更新收款挂账金额
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		log.debug("------结束资金账户处理::取消收款-----------");		
	}
	
	
	/**
	 * 收款交割时资金账户的操作
	* @param accountParam
	* @param　
	* @return
	* @throws
	 */
	public void deliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::收款交割-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);				
		if(!accountParam.getDeliveryDate().after(today)){
			addBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		}
		log.debug("------结束资金账户处理::收款交割-----------");		
	}
	
	/**
	 * 取消收款交割时资金账户的操作
	* @param accountParam
	* @param　
	* @return
	* @throws
	 */
	public void cancelDeliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::取消收款交割-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
			checkIsOverDraft(accInfo, accountParam.getAmount());
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);				
		if(!accountParam.getDeliveryDate().after(today)){
			subtractBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		}
		log.debug("------结束资金账户处理::取消收款交割-----------");				
		
	}	
	
	/**
	 * 付款保存/暂存时资金账户的操作
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void pay(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::付款-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		log.debug("-------开始透支检查-------");
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());		
		
		//更新待交割挂账金额/余额
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);				
		if(!accountParam.getDeliveryDate().after(today))//更新余额
			subtractBalance(accountParam.getId(), accountParam.getAmount());
		else																		//增加收款挂账金额
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);
		log.debug("------结束资金账户处理::付款-----------");		
		
	}
	

	/**
	 * 付款删除时资金账户的操作
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void cancelPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::取消付款-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);		
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);		
		//更新待交割挂账金额/余额
		if(!accountParam.getDeliveryDate().after(today))
			addBalance(accountParam.getId(), accountParam.getAmount());
		else																		
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);
		log.debug("------结束资金账户处理::取消付款-----------");		
	}
	
	/**
	 * 付款交割时资金账户的操作
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void deliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::付款交割-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());		
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);				
		//更新待交割挂账金额/余额
		if(!accountParam.getDeliveryDate().after(today)){
			subtractBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);			
		}
		log.debug("------结束资金账户处理::付款交割-----------");				
	}
	
	/**
	 * 取消付款交割时资金账户的操作
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void cancelDeliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------开始资金账户处理::取消付款交割-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---今天是："+today);				
		//更新待交割挂账金额/余额
		if(!accountParam.getDeliveryDate().after(today)){
			addBalance(accountParam.getId(), accountParam.getAmount());
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);			
		}
		log.debug("------结束资金账户处理::取消付款交割-----------");						
	}	
	
	/**
	 * 根据账户状态状态决定是否产生异常及具体的异常提示
	* @param accInfo
	* @param statusID
	* @param payOrReceive 
	* @return
	* @throws　AccounStatusException
	 */
	private void checkAccountStatus(AccountInfo accInfo, long direction) throws AccounStatusException,SecuritiesException{

		if(accInfo == null || accInfo.getId() < 0){//资金帐户不存在
			throw new AccounStatusException("Sec_E102");
		}		
		//check status 如果账户状态非正常(包括冻结，封存，清户)并且非(状态是冻结并且是收款操作(或者是收款操作的取消操作))则产生异常
		if((accInfo.getAccountStatusId() != SECConstant.AccountStatusID.NORMAL)&& !(direction == SECConstant.Direction.RECEIVE && accInfo.getAccountStatusId() == SECConstant.AccountStatusID.FREEZE)){
			throw new AccounStatusException(accInfo.getAccountCode(),accInfo.getAccountStatusId());
		}
	}
	

	/**
	 * 检查当前资金账户的可用余额是否不足　提示　资金帐户[?]可用余额不足
	 * 当前余额 + 待交割收款挂帐金额 - 待交割付款挂帐金额 < 交易金额　则透支
	* @param accInfo
	* @param transAmount 交易金额 
	* @return
	* @throws　AccounStatusException
	 */	
	public void checkIsOverDraft(AccountInfo accInfo, double transAmount)throws AccountOverDraftException,RemoteException{
		//可用余额 = 当前余额 + 待交割收款挂帐金额 - 待交割付款挂帐金额
//		可用余额 = 当前余额 - 待交割付款挂帐金额
		//log.debug("可用余额 = 当前余额 + 待交割收款挂帐金额 - 待交割付款挂帐金额");
		log.debug("可用余额 = 当前余额 - 待交割付款挂帐金额");
		log.debug("当前余额: "+accInfo.getBalance());
		//log.debug("待交割收款挂帐金额: "+accInfo.getSuspenseReceive());
		log.debug("待交割付款挂帐金额: "+accInfo.getSuspensePay());
		//if((SECUtil.Arith.round(accInfo.getBalance(),2)+SECUtil.Arith.round(accInfo.getSuspenseReceive(),2)-SECUtil.Arith.round(accInfo.getSuspensePay(),2)) < transAmount){
		if((SECUtil.Arith.round(accInfo.getBalance(),2)-SECUtil.Arith.round(accInfo.getSuspensePay(),2)) < transAmount){
			throw new AccountOverDraftException(accInfo.getAccountCode());
		}
	}

	
	
	/**
	 * 增加挂账金额
	* @param  
	* @param
	* @return
	* @throws
	 */	
	private void addSuspendingAmount(long accountID, double amount, long direction) throws SecuritiesException{
		updateSuspendingAmount(accountID, amount, direction);	
	}
	
	/**
	 * 减少挂账金额
	* @param  
	* @param
	* @return
	* @throws
	 */		
	private void subtractSuspendingAmount(long accountID, double amount, long direction) throws SecuritiesException{
		amount = (-1)*amount;
		updateSuspendingAmount(accountID, amount, direction);		
	}
	
		/**
		* @param 更新挂账金额 
		* @param
		* @return
		* @throws
		 */
	private void updateSuspendingAmount(long accountID, double amount, long direction) throws SecuritiesDAOException {
		log.debug("------开始更新挂账金额-----------");
		log.debug("accountID:"+accountID);
		log.debug("amount:"+amount);
		log.debug("direction:"+direction);		
		SEC_AccountDAO accDAO = new SEC_AccountDAO();		
		if(direction == SECConstant.Direction.PAY){
			try {
				accDAO.updateAmount(accountID, amount, SEC_AccountDAO.Account_AmountType_SuspensePay);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
		}else{
			try {
				accDAO.updateAmount(accountID, amount, SEC_AccountDAO.Account_AmountType_SuspenseReceive);
			} catch (ITreasuryDAOException e1) {
				throw new SecuritiesDAOException(e1);
			}
		}
		log.debug("------结束更新挂账金额-----------");		
	}
	
	/**
	 * 增加账户金额 
	* @param 
	* @param
	* @return
	* @throws
	 */
	private void addBalance(long accountID, double amount) throws SecuritiesException{
		log.debug("------开始增加余额-----------");
		log.debug("accountID:"+accountID);
		log.debug("amount:"+amount);		
		updateBalance(accountID, amount);
		log.debug("-----结束增加余额-----------");		
	}
	
	/**
	 * 减少账户金额 
	* @param 
	* @param
	* @return
	* @throws
	 */	
	private void subtractBalance(long accountID, double amount) throws SecuritiesException{
		log.debug("------开始减少余额-----------");		
		amount = (-1)*amount;
		log.debug("accountID:"+accountID);
		log.debug("amount:"+amount);				
		updateBalance(accountID, amount);		
		log.debug("------结束减少余额-----------");		
	}		
	
	/**
	* @param 更新账户余额 
	* @param
	* @return
	* @throws
	 */
	private void updateBalance(long accountID, double amount) throws SecuritiesDAOException {
		SEC_AccountDAO accDAO = new SEC_AccountDAO();		
		try {
			accDAO.updateAmount(accountID, amount, SEC_AccountDAO.Account_AmountType_Balance);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	* @param accountParam 
	* @param
	* @return
	* @throws
	 */
	private AccountInfo findAccountInfoByID(long accountID) throws SecuritiesDAOException {
		SEC_AccountDAO accountDAO = new SEC_AccountDAO(); 
		AccountInfo accInfo = null;
		try {
			accInfo = (AccountInfo) accountDAO.findByID(accountID,AccountInfo.class);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		log.debug("被处理的资金账户为: "+accInfo);		
		return accInfo;
	}
	
	/**
	 * 针对一个资金帐户，从起日到止日，进行资金帐户日结
	 * @remark 资金的日结，在同一日内与顺序与关，只是余额的增减。
	 * */
	public void caculateSingleAccountDailyStock(long accountID, Timestamp sDate,Timestamp eDate)throws RemoteException,SecuritiesException{
		AccountInfo accInfo = findAccountInfoByID(accountID);	
		//检查账户状态
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		
		Timestamp yesterdayOfStart = DataFormat.getNextDate(sDate, -1); 
		
		
		SEC_DailyAccountDAO dailyAccountDAO = new SEC_DailyAccountDAO();
		DailyAccountInfo dailyAccountInfo = dailyAccountDAO.findByAccountIDAndDate(accountID, sDate);

		//该帐户是新建帐户.增加一条昨天的日结记录
		if(dailyAccountInfo == null && !sDate.after(eDate)){
			dailyAccountInfo = new DailyAccountInfo();
			dailyAccountInfo.setValuesToDefault();
			dailyAccountInfo.setAccountID(accountID);
			dailyAccountInfo.setAccountDate(yesterdayOfStart);
			AccountInfo accountInfo = findAccountInfoByID(accountID);
			dailyAccountInfo.setBalance(accountInfo.getInitBalance());
			try {
				dailyAccountDAO.add(dailyAccountInfo);
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);	
			}
		}
		SEC_VAccountDetailDAO vAccountDetailDAO = new SEC_VAccountDetailDAO();
		
		while(!sDate.after(eDate)){
			log.debug("--------正在处理日是:"+sDate);
			double[] todayPay = vAccountDetailDAO.sumAccountDetails(accountID,sDate, SECConstant.Direction.PAY);
			long todayPayNumber = (long)todayPay[0];
			double todayNetPayIncome = todayPay[1];
			double[] todayReceive = vAccountDetailDAO.sumAccountDetails(accountID,sDate, SECConstant.Direction.RECEIVE);
			long todayReceiveNumber = (long)todayReceive[0];
			double todayNetReceiveIncome = todayReceive[1];
			//期末数
			Timestamp yesterday = DataFormat.getNextDate(sDate,-1);
			log.debug("-----------昨天是:"+yesterday);
			DailyAccountInfo lastDailyAccountInfo = dailyAccountDAO.findByAccountIDAndDate(accountID, yesterday);
			log.debug("-----------昨天的帐户日结信息是:"+lastDailyAccountInfo);
			double initBalance = 0.0;
			if(lastDailyAccountInfo != null){
				initBalance = lastDailyAccountInfo.getBalance();				
			}
			log.debug("-----------initBalance:"+initBalance);
			log.debug("-----------todayNetReceiveIncome:"+todayNetReceiveIncome);
			log.debug("-----------todayNetPayIncome:"+todayNetPayIncome);
			
			double endBalace = initBalance + todayNetReceiveIncome - todayNetPayIncome;
			//今天的帐户日结
			dailyAccountInfo =  dailyAccountDAO.findByAccountIDAndDate(accountID, sDate);
			boolean isNew = false;
			if(dailyAccountInfo == null){
				dailyAccountInfo = new DailyAccountInfo();
				dailyAccountInfo.setAccountID(accountID);
				dailyAccountInfo.setAccountDate(sDate);
				isNew = true;
			}

			dailyAccountInfo.setBalance(endBalace);
			dailyAccountInfo.setPayAmount(todayNetPayIncome);
			dailyAccountInfo.setPayNumber(todayPayNumber);
			dailyAccountInfo.setReceiveAmount(todayNetReceiveIncome);
			dailyAccountInfo.setReceiveNumber(todayReceiveNumber);
			
			try {
				if(isNew){
					log.debug("新增帐户日结信息为:"+dailyAccountInfo);
					dailyAccountDAO.add(dailyAccountInfo);					
				}else{
					log.debug("被更新的帐户日结信息为:"+dailyAccountInfo);					
					dailyAccountDAO.update(dailyAccountInfo);
				}
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
			initBalance = endBalace;
			//日期增加一天
			sDate = DataFormat.getNextDate(sDate, 1); 			
		}//end of while
	}	
	

}
