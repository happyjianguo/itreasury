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
	/**�ʽ����������--����*/
	private final static int OPERATION_ADD 	    = 1;
	/**�ʽ����������--����*/	
	private final static int OPERATION_SUBTRACT = 2;	
	
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
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
	 * �տ��/�ݴ�ʱ�ʽ��˻��Ĳ���
	* @param accountParam 
	* @param
	* @return
	* @throws
	 */
	public void receive(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::�տ�-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		//���´�������˽��/���
		log.debug("-------��ʼ���´�������˽��/���----------");
		log.debug("---�����գ�"+accountParam.getDeliveryDate());
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);
		if(!accountParam.getDeliveryDate().after(today))//�������
			addBalance(accountParam.getId(), accountParam.getAmount());
		else																		//�����տ���˽��
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		log.debug("------�����ʽ��˻�����::�տ�-----------");		
	}
	


	/**
	 * �տ�ɾ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam 
	* @param��
	* @return
	* @throws
	 */
	public void cancelReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::ȡ���տ�-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬���������տ�������ո�����ʵ���Ǹ�
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		//����Ƿ�͸֧
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());
		//���´�������˽��/���
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);		
		if(!accountParam.getDeliveryDate().after(today))//�������
			subtractBalance(accountParam.getId(), accountParam.getAmount());
		else																		//�����տ���˽��
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		log.debug("------�����ʽ��˻�����::ȡ���տ�-----------");		
	}
	
	
	/**
	 * �տ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param��
	* @return
	* @throws
	 */
	public void deliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::�տ��-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);				
		if(!accountParam.getDeliveryDate().after(today)){
			addBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		}
		log.debug("------�����ʽ��˻�����::�տ��-----------");		
	}
	
	/**
	 * ȡ���տ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param��
	* @return
	* @throws
	 */
	public void cancelDeliverReceipt(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::ȡ���տ��-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)
			checkIsOverDraft(accInfo, accountParam.getAmount());
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);				
		if(!accountParam.getDeliveryDate().after(today)){
			subtractBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.RECEIVE);
		}
		log.debug("------�����ʽ��˻�����::ȡ���տ��-----------");				
		
	}	
	
	/**
	 * �����/�ݴ�ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void pay(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::����-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		log.debug("-------��ʼ͸֧���-------");
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());		
		
		//���´�������˽��/���
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);				
		if(!accountParam.getDeliveryDate().after(today))//�������
			subtractBalance(accountParam.getId(), accountParam.getAmount());
		else																		//�����տ���˽��
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);
		log.debug("------�����ʽ��˻�����::����-----------");		
		
	}
	

	/**
	 * ����ɾ��ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void cancelPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::ȡ������-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);		
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);		
		//���´�������˽��/���
		if(!accountParam.getDeliveryDate().after(today))
			addBalance(accountParam.getId(), accountParam.getAmount());
		else																		
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);
		log.debug("------�����ʽ��˻�����::ȡ������-----------");		
	}
	
	/**
	 * �����ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void deliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::�����-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.PAY);
		
		if(accountParam.getIsNeedCheckOverDraft() == SECConstant.TRUE)		
			checkIsOverDraft(accInfo, accountParam.getAmount());		
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);				
		//���´�������˽��/���
		if(!accountParam.getDeliveryDate().after(today)){
			subtractBalance(accountParam.getId(), accountParam.getAmount());
			subtractSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);			
		}
		log.debug("------�����ʽ��˻�����::�����-----------");				
	}
	
	/**
	 * ȡ�������ʱ�ʽ��˻��Ĳ���
	* @param accountParam
	* @param
	* @return
	* @throws
	 */
	public void cancelDeliverPayment(AccountParam accountParam) throws RemoteException,AccounStatusException,AccountOverDraftException,SecuritiesException{
		log.debug("------��ʼ�ʽ��˻�����::ȡ�������-----------");		
		AccountInfo accInfo = findAccountInfoByID(accountParam.getId());		
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		Timestamp today = Env.getSecuritiesSystemDate(accountParam.getOfficeID(), accountParam.getCurrencyID());
		log.debug("---�����ǣ�"+today);				
		//���´�������˽��/���
		if(!accountParam.getDeliveryDate().after(today)){
			addBalance(accountParam.getId(), accountParam.getAmount());
			addSuspendingAmount(accountParam.getId(), accountParam.getAmount(),SECConstant.Direction.PAY);			
		}
		log.debug("------�����ʽ��˻�����::ȡ�������-----------");						
	}	
	
	/**
	 * �����˻�״̬״̬�����Ƿ�����쳣��������쳣��ʾ
	* @param accInfo
	* @param statusID
	* @param payOrReceive 
	* @return
	* @throws��AccounStatusException
	 */
	private void checkAccountStatus(AccountInfo accInfo, long direction) throws AccounStatusException,SecuritiesException{

		if(accInfo == null || accInfo.getId() < 0){//�ʽ��ʻ�������
			throw new AccounStatusException("Sec_E102");
		}		
		//check status ����˻�״̬������(�������ᣬ��棬�廧)���ҷ�(״̬�Ƕ��Ტ�����տ����(�������տ������ȡ������))������쳣
		if((accInfo.getAccountStatusId() != SECConstant.AccountStatusID.NORMAL)&& !(direction == SECConstant.Direction.RECEIVE && accInfo.getAccountStatusId() == SECConstant.AccountStatusID.FREEZE)){
			throw new AccounStatusException(accInfo.getAccountCode(),accInfo.getAccountStatusId());
		}
	}
	

	/**
	 * ��鵱ǰ�ʽ��˻��Ŀ�������Ƿ��㡡��ʾ���ʽ��ʻ�[?]��������
	 * ��ǰ��� + �������տ���ʽ�� - ���������ʽ�� < ���׽���͸֧
	* @param accInfo
	* @param transAmount ���׽�� 
	* @return
	* @throws��AccounStatusException
	 */	
	public void checkIsOverDraft(AccountInfo accInfo, double transAmount)throws AccountOverDraftException,RemoteException{
		//������� = ��ǰ��� + �������տ���ʽ�� - ���������ʽ��
//		������� = ��ǰ��� - ���������ʽ��
		//log.debug("������� = ��ǰ��� + �������տ���ʽ�� - ���������ʽ��");
		log.debug("������� = ��ǰ��� - ���������ʽ��");
		log.debug("��ǰ���: "+accInfo.getBalance());
		//log.debug("�������տ���ʽ��: "+accInfo.getSuspenseReceive());
		log.debug("���������ʽ��: "+accInfo.getSuspensePay());
		//if((SECUtil.Arith.round(accInfo.getBalance(),2)+SECUtil.Arith.round(accInfo.getSuspenseReceive(),2)-SECUtil.Arith.round(accInfo.getSuspensePay(),2)) < transAmount){
		if((SECUtil.Arith.round(accInfo.getBalance(),2)-SECUtil.Arith.round(accInfo.getSuspensePay(),2)) < transAmount){
			throw new AccountOverDraftException(accInfo.getAccountCode());
		}
	}

	
	
	/**
	 * ���ӹ��˽��
	* @param  
	* @param
	* @return
	* @throws
	 */	
	private void addSuspendingAmount(long accountID, double amount, long direction) throws SecuritiesException{
		updateSuspendingAmount(accountID, amount, direction);	
	}
	
	/**
	 * ���ٹ��˽��
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
		* @param ���¹��˽�� 
		* @param
		* @return
		* @throws
		 */
	private void updateSuspendingAmount(long accountID, double amount, long direction) throws SecuritiesDAOException {
		log.debug("------��ʼ���¹��˽��-----------");
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
		log.debug("------�������¹��˽��-----------");		
	}
	
	/**
	 * �����˻���� 
	* @param 
	* @param
	* @return
	* @throws
	 */
	private void addBalance(long accountID, double amount) throws SecuritiesException{
		log.debug("------��ʼ�������-----------");
		log.debug("accountID:"+accountID);
		log.debug("amount:"+amount);		
		updateBalance(accountID, amount);
		log.debug("-----�����������-----------");		
	}
	
	/**
	 * �����˻���� 
	* @param 
	* @param
	* @return
	* @throws
	 */	
	private void subtractBalance(long accountID, double amount) throws SecuritiesException{
		log.debug("------��ʼ�������-----------");		
		amount = (-1)*amount;
		log.debug("accountID:"+accountID);
		log.debug("amount:"+amount);				
		updateBalance(accountID, amount);		
		log.debug("------�����������-----------");		
	}		
	
	/**
	* @param �����˻���� 
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
		log.debug("��������ʽ��˻�Ϊ: "+accInfo);		
		return accInfo;
	}
	
	/**
	 * ���һ���ʽ��ʻ��������յ�ֹ�գ������ʽ��ʻ��ս�
	 * @remark �ʽ���սᣬ��ͬһ������˳����أ�ֻ������������
	 * */
	public void caculateSingleAccountDailyStock(long accountID, Timestamp sDate,Timestamp eDate)throws RemoteException,SecuritiesException{
		AccountInfo accInfo = findAccountInfoByID(accountID);	
		//����˻�״̬
		checkAccountStatus(accInfo,SECConstant.Direction.RECEIVE);
		
		Timestamp yesterdayOfStart = DataFormat.getNextDate(sDate, -1); 
		
		
		SEC_DailyAccountDAO dailyAccountDAO = new SEC_DailyAccountDAO();
		DailyAccountInfo dailyAccountInfo = dailyAccountDAO.findByAccountIDAndDate(accountID, sDate);

		//���ʻ����½��ʻ�.����һ��������ս��¼
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
			log.debug("--------���ڴ�������:"+sDate);
			double[] todayPay = vAccountDetailDAO.sumAccountDetails(accountID,sDate, SECConstant.Direction.PAY);
			long todayPayNumber = (long)todayPay[0];
			double todayNetPayIncome = todayPay[1];
			double[] todayReceive = vAccountDetailDAO.sumAccountDetails(accountID,sDate, SECConstant.Direction.RECEIVE);
			long todayReceiveNumber = (long)todayReceive[0];
			double todayNetReceiveIncome = todayReceive[1];
			//��ĩ��
			Timestamp yesterday = DataFormat.getNextDate(sDate,-1);
			log.debug("-----------������:"+yesterday);
			DailyAccountInfo lastDailyAccountInfo = dailyAccountDAO.findByAccountIDAndDate(accountID, yesterday);
			log.debug("-----------������ʻ��ս���Ϣ��:"+lastDailyAccountInfo);
			double initBalance = 0.0;
			if(lastDailyAccountInfo != null){
				initBalance = lastDailyAccountInfo.getBalance();				
			}
			log.debug("-----------initBalance:"+initBalance);
			log.debug("-----------todayNetReceiveIncome:"+todayNetReceiveIncome);
			log.debug("-----------todayNetPayIncome:"+todayNetPayIncome);
			
			double endBalace = initBalance + todayNetReceiveIncome - todayNetPayIncome;
			//������ʻ��ս�
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
					log.debug("�����ʻ��ս���ϢΪ:"+dailyAccountInfo);
					dailyAccountDAO.add(dailyAccountInfo);					
				}else{
					log.debug("�����µ��ʻ��ս���ϢΪ:"+dailyAccountInfo);					
					dailyAccountDAO.update(dailyAccountInfo);
				}
			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}
			initBalance = endBalace;
			//��������һ��
			sDate = DataFormat.getNextDate(sDate, 1); 			
		}//end of while
	}	
	

}
