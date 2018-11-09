/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public interface Account extends javax.ejb.EJBObject
{
	// public methods write following block
	/**
	 * ����˵���� �����ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ���������Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * ����˵����ȡ�ÿͻ����
	 * 
	 * @param lOfficeID :
	 *            long @return: String - �����Ŀͻ����
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݿͻ�ID����ѯ�ͻ���Ϣ
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException;
	/**
	 * ����˵�����޸Ŀͻ���Ϣ
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - �ͻ�ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * ����˵���� ɾ���ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ����ɾ���Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������������˻�
	 * 
	 * @param ai
	 *            @return: long - �������˻�ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵�����������ںʹ����˻�
	 * 
	 * @param ai
	 *            @return: long - �������˻�ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addAccount(AccountInfo ai) throws RemoteException, IRollbackException;
	/**
	 * ����˵���������˻�ID����ѯ�˻���Ϣ
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public AccountInfo findAccountByID(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�����������˻�ID����ѯ���˻���Ϣ
	 * 
	 * @param lSubAccountID
	 *            ���˻�ID @return:SubAccountAssemblerInfo ai ���˻�Assemble
	 * @throws RemoteException,IRollbackException
	 */
	public SubAccountAssemblerInfo findSubAccountByID(long lSubAccountID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݿ�����ID����ѯ��������Ϣ
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public BranchInfo findBranchByID(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findAccountByCondition(QueryAccountConditionInfo qaci)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findReserveAccountByCondition(QueryAccountConditionInfo qaci)
		throws RemoteException, IRollbackException;
	
	/**
	 * ����˵�����޸Ļ����˻�����Ϣ
	 * 
	 * @param ai
	 * @return :�����˻���ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵�����޸Ķ��ںʹ����˻�����Ϣ
	 * 
	 * @param ai
	 * @return :�����˻���ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateAccount(AccountInfo ai) throws RemoteException, IRollbackException;
	/**
	 * ����˵�����õ��µ��˻���
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return �������˻����
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵�����õ��µ��˻��ţ��������˻���׼�����˻�������˻� ���ã�
	 * 
	 * @param lOfficeID ��ǰ��������
	 * @param lOfficeID �ͻ���������
	 * @param lAccountGroupTypeID �˻�������
	 * @param lAccountTypeID �˻�����
	 * @param lCurrencyID
	 * @return �������˻����
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID,long lAccountGroupTypeID,long belongOfficeID)
		throws RemoteException, IRollbackException;

	/**
	 * ����˵�����ж��Ƿ�͸֧
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: ͸֧���� true; ��͸֧����false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isOverDraft(long lAccountID, long subAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;
	
	/**
	 * ����˵�����ж� �˻����֧���ۼ�δ���˽��׽����Ƿ������֧���˽��׽�ʵ�����-�ۼ�δ���˽��-���׽��<0,�������˻��Ƿ�����͸֧��
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: ���� ����true; �� ����false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isLackBalanceToDraft(long lAccountID, double dPayAmount)throws RemoteException, IRollbackException;
	
	
	public long validateAccountStatus(long lAccountID) throws RemoteException, IRollbackException;
	/**
	 * ����˵�� �� �������ڴ�����˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openCurrentSubAccount(SubAccountCurrentInfo saci) throws RemoteException, IRollbackException;
	/**
	 * ����˵�� �� �����������˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openFixSubAccount(SubAccountFixedInfo safi) throws RemoteException, IRollbackException;
	/**
	 * ����˵�� �� �������������˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openLoanSubAccount(SubAccountLoanInfo sali) throws RemoteException, IRollbackException;
	/**
	 * ����˵���������˻���
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long addAccountBalance(long lSubAccountID, double dPayAmount) throws RemoteException, IRollbackException;
	/**
	 * ����˵���������˻���
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long subtractAccountBalance(long lSubAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;
	// ��ѯ-�˻�����ѯ
	public Double queryAccountBalance(QueryAccountWhereInfo qawi) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ӻ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addCurrentUncheckAmount(long lAccountID, long lOppAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
	 * ����˵�������Ӷ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException;
	
	/**
	 * ���� �������� ��֤�� ������  ���˻����ۼ�δ���˸����
	 * @param lAccountID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void addFixedUncheckAmount4Recog(long lAccountID,long contractid)
	throws RemoteException, IRollbackException;
	
	/**
	 * ����˵�������Ӵ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractCurrentUncheckAmount(long lAccountID, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException;

	/**
     * �������� ��֤�� ������  ���� ���˻� �� �ۼ�δ���˽�����Ϊ0.0
     * @param lAccountID
     * @param contractid
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void subtractUncheckAmount4Recog(long lAccountID,long contractid)
	throws RemoteException, IRollbackException;
	/**
	 * ����˵��������֧ȡ��
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException;

	public long withdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����֧ȡ
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	
	public Collection withdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException;
	
	/**
	 * �������޻���֧ȡ
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����˵����ȡ������֧ȡ��
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ȡ�����ڴ���
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ȡ�����ڴ���
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	
	public void cancelWithdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException;

	/**
	 * ����˵����ɾ���������˻���ע�⣬�÷���Ŀǰֻ��Ϊһ���������˻��ķ�����ʵ�ʵ�ɾ�����ڶ��ڻ��������˻���0��ʱ���Զ�ɾ�����˻�
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteFixSubAccount(long subAccountID) throws RemoteException, IRollbackException;

	/**
	 * ����˵����ɾ���������˻���(��������ɾ���˻���ɾ�������ڿ۳��˻����ʱ����)
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteLoanSubAccount(long subAccountID) throws RemoteException, IRollbackException;

	/**
	 * ����˵�������ڴ��롣
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ڴ��롣
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ɾ���˻�������ϸ��Ľ�����ϸ
	 * 
	 * @param strTransNo
	 *            ���ױ��
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo) throws RemoteException, IRollbackException;
	/**
	 * ����˵����ȡ�����ڴ��롣
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����˵����ȡ�����ڴ��롣
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long cancelDepositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	public double getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType)
		throws RemoteException, IRollbackException;

	/**
	 * ����˵���������
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����˵����ȡ�������
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelGrantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����黹
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;
	/**
	 * ȡ������黹
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 * 
	 */
	public long cancelRepayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException;

	/**
	 * ����˵���������ⲿ�˻�
	 * 
	 * @param ExternalAccountInfo
	 * @return �����¼ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveExternalAccount(ExternalAccountInfo info) throws RemoteException, IRollbackException;

	/**
	 * @param lSubAccountID
	 * @return @throws
	 *         IRollbackException
	 */
	public String getSubjectBySubAccountID(long lSubAccountID, int subjectType)
		throws RemoteException, IRollbackException;
	
	public String getSubjectByOther(long lCracontractID, int subjectType)
	throws RemoteException, IRollbackException;
	/**
	 * ���һ������˻�
	 * @param lAccountID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubAccountAssemblerInfo findSubCurrentAccountByAccountID(long lAccountID)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵���������޸��˻�״̬�����ʼƻ�
	 * 
	 * @param ai
	 * @return :���سɹ���ʶ
	 * @throws RemoteException,IRollbackException
	 */
	public long BatchUpdateAccount(QueryAccountConditionInfo qaci, AccountInfo ai, SubAccountCurrentInfo saci)
		throws RemoteException, IRollbackException;
	/**
	 * ����˵���������˻�ID�޸��˻�״̬
	 * @param qaci
	 * @return long - �����˻�ID
	 * @throws Exception
	 */
	public long UpdateCheckStatus(long lAccountID,long lActionID,long lCheckStatusID, long lCheckUserID, Timestamp tsCheckDate)
		throws RemoteException, IRollbackException;

	public long getCurrentSubAccoutIDByAccoutID(long accoutID) throws RemoteException, IRollbackException;

	public long getLoanSubAccountIDByAccountIDAndLoanNoteID(long accoutID, long loanNoteID)
		throws RemoteException, IRollbackException;
	
	/**
	 * added by mzh_fu 2007/08/08
	 * @param accoutID
	 * @param loanNoteID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(
			long accoutID, long loanNoteID,long lStatus) throws RemoteException,
			IRollbackException ;

	public long getAccountTypeBySubAccountID(long subAccountID) throws RemoteException, IRollbackException;

	/**���ݽ��׷�������ID��ѯ��Ŀ��*/
	public String getSubjectByTransFeeTypeID(long transFeeTypeID) throws RemoteException, IRollbackException;

    //�˻���������/
	public long doApproval(AccountInfo info)throws RemoteException, IRollbackException;
//	�˻�ȡ����������/
	public long cancelApproval(AccountInfo info)throws RemoteException, IRollbackException;
	//�½��˻��Ƿ�����ҵ��
	public long haveTrans(AccountInfo info)throws RemoteException, IRollbackException;
	
	/**
	 * �������洦�������Ϣ�ͼ�������
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws RemoteException, IRollbackException;
	
	//��ȡ�ͻ�
	public String findClientCodeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException;
	
	//��ȡ�˻�����
	public long findAccountTypeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException;
	public long updateCommission(TransAccountDetailInfo info) throws RemoteException, IRollbackException;
}