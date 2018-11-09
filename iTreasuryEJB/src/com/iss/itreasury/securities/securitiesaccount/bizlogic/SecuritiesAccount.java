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
	 * �տ��/�ݴ�ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param
	* @return
	* @throws
	 */
	public void receive(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;


	/**
	 * �տ�ɾ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param��
	* @return
	* @throws
	 */
	public void cancelReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	
	/**
	 * �տ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param��
	* @return
	* @throws
	 */
	public void deliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * ȡ���տ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param��
	* @return
	* @throws
	 */
	public void cancelDeliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * �����/�ݴ�ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param
	* @return
	* @throws
	 */
	public void pay(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * ����ɾ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param
	* @return
	* @throws
	 */
	public void cancelPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	/**
	 * �����ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param
	* @return
	* @throws
	 */
	public void deliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * ȡ�������ʱ�ʽ��˻��Ĳ���
	* @param accountParam �ʽ��˻���Ϣ
	* @param
	* @return
	* @throws
	 */
	public void cancelDeliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException;
	
	/**
	 * ���һ���ʽ��ʻ��������յ�ֹ�գ������ʽ��ʻ��ս�
	 * @remark �ʽ���սᣬ��ͬһ������˳����أ�ֻ������������
	 * */
	public void caculateSingleAccountDailyStock(long accountID, Timestamp sDate,Timestamp eDate)throws RemoteException,SecuritiesException;	


	/**
	 * ��鵱ǰ�ʽ��˻��Ŀ�������Ƿ��㡡��ʾ���ʽ��ʻ�[?]��������
	 * ��ǰ��� + �������տ���ʽ�� - ���������ʽ�� < ���׽���͸֧
	* @param accInfo
	* @param transAmount ���׽�� 
	* @return
	* @throws��AccounStatusException
	 */	
	public void checkIsOverDraft(AccountInfo accInfo, double transAmount)throws AccountOverDraftException,RemoteException;	
}
