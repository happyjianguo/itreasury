/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiesaccount.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountInfo;
import com.iss.itreasury.securities.securitiesaccount.dataentity.AccountParam;
import com.iss.itreasury.securities.securitiesaccount.exception.AccounStatusException;
import com.iss.itreasury.securities.securitiesaccount.exception.AccountOverDraftException;
import com.iss.itreasury.securities.exception.*;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface SecuritiesAccount extends javax.ejb.EJBObject
{
	/**
	 * 收款保存/暂存时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void receive(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;


	/**
	 * 收款删除时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void cancelReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	
	/**
	 * 收款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void deliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * 取消收款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param　
	* @return
	* @throws
	 */
	public void cancelDeliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * 付款保存/暂存时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void pay(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * 付款删除时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void cancelPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	/**
	 * 付款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void deliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * 取消付款交割时资金账户的操作
	* @param accountParam 资金账户信息
	* @param
	* @return
	* @throws
	 */
	public void cancelDeliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * 针对一个资金帐户，从起日到止日，进行资金帐户日结
	 * @remark 资金的日结，在同一日内与顺序与关，只是余额的增减。
	 * */
	public void caculateSingleAccountDailyStock(long accountID, Timestamp sDate,Timestamp eDate)throws RemoteException,SecuritiesException;	


	/**
	 * 检查当前资金账户的可用余额是否不足　提示　资金帐户[?]可用余额不足
	 * 当前余额 + 待交割收款挂帐金额 - 待交割付款挂帐金额 < 交易金额　则透支
	* @param accInfo
	* @param transAmount 交易金额 
	* @return
	* @throws　AccounStatusException
	 */	
	public void checkIsOverDraft(AccountInfo accInfo, double transAmount)throws AccountOverDraftException,RemoteException;	
}
