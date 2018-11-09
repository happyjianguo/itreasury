/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.accountbook.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransDiscountDetailInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AccountBook extends javax.ejb.EJBObject
{
	public void saveSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void deleteSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void checkSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	public void cancelCheckSecuritiesDetails(TransSecuritiesInfo transInfo) throws RemoteException, IRollbackException;
	
	/**
	 * ���ڲ����ױ���(���ڽ��ױ���ʱ�Ĳ���������)
	 * �߼�������
	 * 1.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0��Ϊ�ڲ�ת�ˣ������ۼ�δ���˽������˻��������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����Ʊ�ݺ�>0����Ʊ��ʹ�ã�Ʊ�ݷ������У�Ʊ�����ͣ�Ʊ�ݺţ�����ͻ�ID��ִ���գ�¼���ˣ�
	 * @param transInfo Sett_TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void saveCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;
    
    /**
     * ��������ȡ�����ױ���(�����ѽ��ױ���ʱ�Ĳ���������)
     * @param transInfo Sett_TransCurrentDepositInfo ���ڽ���ʵ����
     * @throws java.rmi.RemoteException
     * @throws Exception
     * @throws IRollbackException
     */
    public void saveCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ���ڲ�����ɾ��(���ڽ���ɾ��ʱ�Ĳ���������)
	 * �߼�������
	 * 1.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0���ڲ�ת�ˣ����򣺿۳�δ���˽�����˻������׽�
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����Ʊ�ݺ�>0����Ʊ��ȡ��ʹ�ã�Ʊ�ݷ������У�Ʊ�����ͣ�Ʊ�ݺţ�ִ���գ�¼���ˣ�
	 * @param transInfo Sett_TransCurrentDepositInfo
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void deleteCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ���ڲ����׸���(���ڽ��׸���ʱ�Ĳ���������)
	 * �߼�������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ����˻�ID>0���ڲ�ת�ˣ����򣺻���֧ȡ������˻���������Է��˻�=�տ��
	 * 3.����������Ϊ�����տ�ֽ��տ�ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ����տ��˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻��ڴ��루�տ�˻���������,�Է��˻�=�����
	 * 4.ͨ��ͨ�Ҵ���AccountBookBizlogic.InterbranchSettlement()
	 * 5.������Ʒ�¼GeneralLedgerBizlogic.GenerateGLEntry(��)�������/���������˻�ID>0���򱾽�����lPrincipalType =2���У����򱾽�����lPrincipalType =1�ڲ�ת�ˣ���¼����lEntryType =0�޹أ�lAccountID1=�տ�˻���lAccountID2=����˻���dAmount1=����������ԡ�
	 * @param transInfo Sett_TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void checkCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;

	/**
     * ��������ȡ�����׸���(�����ѽ��׸���ʱ�Ĳ���������)
     * ������Ʒ�¼GeneralLedgerBizlogic.GenerateGLEntry(��)�������/���������˻�ID>0���򱾽�����lPrincipalType =2���У����򱾽�����lPrincipalType =1�ڲ�ת�ˣ���¼����lEntryType =0�޹أ�lAccountID1=�տ�˻���lAccountID2=����˻���dAmount1=����������ԡ�
     * @param transInfo Sett_TransCurrentDepositInfo ���ڽ���ʵ����
     * @throws java.rmi.RemoteException
     * @throws Exception
     * @throws IRollbackException
     */
    public void checkCommissionAccountDetails(TransCommissionInfo transInfo) throws RemoteException, IRollbackException;
    
	/**
	 * ���ڲ�����ȡ������(���ڽ���ȡ������ʱ�Ĳ���������)
	 * �߼�������
	 * 2.����������Ϊ���и��֧Ʊ����ֽ𸶿Ʊ�㸶���������ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ��Ҹ���˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻���֧ȡ�����ף�����˻���������Է��˻�=�տ��
	 * 3.����������Ϊ�����տ�ֽ��տ�ڲ�ת�ˡ�ί���տί�д���֤�����������Ϊһ�����գ����տ�˻�ID>0��Ϊ�ڲ�ת�ˣ����򣺻��ڴ��뷴���ף��տ�˻���������Է��˻�=�����
	 * 4.ɾ����ϸ��AccountBookBizlogic.DeleteAccountDetail
	 * 5.ͨ��ͨ�ҷ����״���AccountBookBizlogic.InterbranchSettlementReverse()
	 * 6.ɾ����Ʒ�¼GeneralLedgerBizlogic.DeleteGLEntry(��)
	 * @param transInfo Sett_TransCurrentDepositInfo ���ڽ���ʵ����
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 * @throws IRollbackException
	 */
	public void unCheckCurrentAccountDetails(TransCurrentDepositInfo transInfo) throws RemoteException, IRollbackException;

	/*
	 * ���ڿ�����֪ͨ��������
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */

	public void saveOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;

	/*
	 * ���ڿ�����֪ͨ����ɾ��
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * ���ڿ�����֪ͨ�������׸���
	 */
	public void checkOpenFixedDeposit(TransFixedOpenInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ���ڿ�����֪ͨ��������ȡ��
	*/
	public void cancelCheckOpenFixedDeposit(TransFixedOpenInfo info) throws RemoteException, IRollbackException;
	/**
	 * ����֧ȡ��֪ͨ���֧ȡ���ױ���
	    */
	public void saveWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**
	 * ����֧ȡ��֪ͨ���֧ȡ����ɾ��
	*/
	public void deleteWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * ����֧ȡ��֪ͨ���֧ȡ���׸���*/
	public void checkWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**����֧ȡ��֪ͨ���֧ȡ���ף���ȡ������*
	 */
	public void cancelCheckWithdrawFixedDeposit(TransFixedDrawInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��֤���������
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void saveOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��֤����ɾ��
	 * @param TransFixedOpenInfo
	 * @Exception RemoteException, IRollbackException
	 */
	public void deleteOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��֤��������׸���
	 */
	public void checkOpenMarginDeposit(TransMarginOpenInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ��֤���������ȡ��
	*/
	public void cancelCheckOpenMarginDeposit(TransMarginOpenInfo info) throws RemoteException, IRollbackException;
	/**
	 * ��֤����֧ȡ���ױ���
	 */
	public void saveWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * �������ޱ�֤�𱣺��� ���֧ȡ���ױ���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
    /**
     * �������ޱ�֤�𱣺��� �޸ı���ʱ�� ɾ��
     * @param info
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void deleteWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
	/**
	 * �������ޱ�֤�𱣺��� ����
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;
	/**
	 * ��֤����֧ȡ����ɾ��
	*/
	public void deleteWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * ��֤����֧ȡ���׸���*/
	public void checkWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;

	/**��֤����֧ȡ���ף���ȡ������*
	 */
	public void cancelCheckWithdrawMarginDeposit(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	/**
	 * �������ޱ�֤�𱣺��� ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckWithFinancialMargin(TransFinancialMarginInfo info) throws RemoteException, IRollbackException;

	
	/**��������ת�汣��*/
	public void saveContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	public void deleteContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**��������ת�渴��*/
	public void checkContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**��������ת��ȡ������*/
	public void cancelCheckContinueFixedDeposit(TransFixedContinueInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ����š�ί�д���ŵȽ��ױ���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ����š�ί�д���ŵȽ���ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ����š�ί�д���ŵȽ��׸���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ����š�ί�д���ŵ�ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckGrantLoan(TransGrantLoanInfo info) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ�����ջء�ί�д����ջصȽ��ױ���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void saveRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ�����ջء�ί�д����ջصȽ���ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ��Ӫ�����ջء�ί�д����ջصȽ��׸���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void checkRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ��Ӫ�����ջء�ί�д����ջصȽ���ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentLoan(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *���ַ��Ž��ױ���
	*/
	public void saveGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *���ַ��Ž���ɾ��
	*/
	public void deleteGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *���ַ��Ž��׸���
	*/
	public void checkGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 *���ַ��Ž���ȡ������
	*/
	public void cancelCheckGrantDiscount(TransGrantDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**�����ջر���*/
	public void saveRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**�����ջ�ɾ��*/
	public void deleteRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**�����ջظ���*/
	public void checkRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * �����ջز�����ȡ������(�����ջؽ���ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransRepaymentDiscountInfo �����ջؽ���ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckRepaymentDiscount(TransRepaymentDiscountInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ����ҵ������ױ���(����ҵ�񱣴�ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ��ʵ����
	 * @throws IRollbackException
	 */
	public void saveSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ����ҵ�������ɾ��(����ҵ����ɾ��ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void deleteSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ����ҵ������׸���(����ҵ���׸���ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void checkSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ����ҵ�������ȡ������(����ҵ����ȡ������ʱ�Ĳ�����)
	 * �߼�������
	 * @param transInfo TransSpecialOperationInfo ����ҵ����ʵ����
	 * @throws IRollbackException
	 */
	public void cancelCheckSpecialOperation(TransSpecialOperationInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս��ױ���
	 *  �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս���ɾ��
	 *  �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void deleteOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս��׸���
	 *   ��  ��������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void checkOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս���ȡ��
	 *   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void cancelCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս��׹���
	 *   ��  ��������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void squareOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * һ�����ս���ȡ������
	 *   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void cancelsquareCheckOnePayMultiReceive(TransOnePayMultiReceiveInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * �����ౣ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */
	public void saveGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
		
	/**
	 * ������ɾ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */	
	public void deleteGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	
	/**
	 * �����ิ��   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */		
	public void checkGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	/**
	 * ������ȡ������   �߼�������
	 * @param transInfo 
	 * @throws IRollbackException
	 */			
	public void cancelCheckGeneralLedgerOperation(TransGeneralLedgerInfo transInfo)  throws RemoteException, IRollbackException;
	
	/**
	 *��ʴ����ջر���
	*/
	public void saveMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *��ʴ����ջ�ɾ��
	*/
	public void deleteMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *��ʴ����ջظ���
	*/
	public void checkMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *��ʴ����ջ�ȡ������
	*/
	public void cancelCheckMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *��ʴ����ջع���
	*/	
	public void squareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 *��ʴ����ջ�ȡ������
	*/
	public void cancelSquareMultiLoanReceive(TransRepaymentLoanInfo transInfo) throws RemoteException, IRollbackException;
	
	/**���׷��ô����ױ���*/
	public void saveTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**���׷��ô�����ɾ��*/
	public void deleteTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**���׷��ô����׸���*/
	public void checkTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	
	/**���׷��ô�����ȡ������*/
	public void cancelCheckTransFee(TransFeeInfo transInfo)throws RemoteException, IRollbackException;
	

	public void interbranchSettlementReverse()  throws RemoteException, IRollbackException;;
	
	
	/**
	 * ���������տ�ױ���
	 */
	public void saveReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * ���������տ��ɾ��
	 */
	public void deleteReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * ���������տ�׸���
	 */
	public void checkReceiveFinance(TransReceiveFinanceInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * ���������տ��ȡ������
	*/
	public void cancelCheckReceiveFinance(TransReceiveFinanceInfo info) throws RemoteException, IRollbackException;
	
	
	/**
	 * �������޻���ױ���
	 */
	public void saveReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * �������޻����ɾ��
	 */
	public void deleteReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;

	/**
	 * �������޻���׸���
	 */
	public void checkReturnFinance(TransReturnFinanceInfo transInfo) throws RemoteException, IRollbackException;

	/**
	 * �������޻����ȡ������
	*/
	public void cancelCheckReturnFinance(TransReturnFinanceInfo info) throws RemoteException, IRollbackException;
	/**
	 *ת���ַ��Ž��׸���
	*/
	public void checkTransDiscount(TransDiscountDetailInfo transInfo) throws RemoteException, IRollbackException;
	
	public boolean isCreateInstruction(long bankID) throws Exception,RemoteException;
	/**
	 * �ʲ�ת�ø����
	*/
	public void checkTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * �ʲ�ת�ø���ȡ������
	*/
	public void cancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * �ʲ�ת���տ��
	*/
	public void repaycheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * �ʲ�ת���տ�ȡ������
	*/
	public void repaycancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * �ʲ�ת���տ��(����)
	*/
	public void repayClientcheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * �ʲ�ת���տ�ȡ�����ˣ����գ�
	*/
	public void repayClientcancelCheckTransferContract(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(������)2011-07-15
	 * �ڲ����-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(������)2011-07-15
	 * �ڲ����-���˲���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by keivn(������)
	 * 2011-07-15
	 * �ڲ����-ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLend(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * �ڲ�����ջ�-����
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * �ڲ�����ջ�-ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by kevin(������)2011-07-19
	 * �ڲ�����ջ�-���˲���
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * add by keivn(������)
	 * 2011-07-19
	 * �ڲ�����ջ�-ȡ������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckInternalLendRepayment(TransInternalLendInfo info) throws RemoteException, IRollbackException;

	/**
	 * add by ���� 2011-07-19
	 * ����������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-19
	 * ����������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-19
	 * ����������-ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-19
	 * ����������-ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteBakReserveAccountDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	
    public void checkBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
	
    public void checkBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void unCheckBakReserveDetailsUpReceive(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    
    
	public void unCheckBakReserveDetailsDownReturn(TransBakReserveInfo transInfo) throws RemoteException, IRollbackException;
    

	/**
	 * add by ���� 2011-07-20
	 * ׼��������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-20
	 * ׼��������-�������
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-20
	 * ׼��������-ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * add by ���� 2011-07-20
	 * ׼��������-ɾ��
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteReserveAccountDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void checkReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
    
	public void checkReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	    
	public void unCheckReserveDetailsUpReceive(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	    
    
	public void unCheckReserveDetailsDownReturn(TransReserveInfo transInfo) throws RemoteException, IRollbackException;
	
	/**��ҵƱ�ݳж�-���ڳжҽ��ױ���*/
	public void saveAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;

	/**��ҵƱ�ݳж�-���ڳжҽ���ɾ��*/
	public void deleteAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**��ҵƱ�ݳж�-���ڳжҽ��׸���*/
	public void checkAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**��ҵƱ�ݳж�-���ڳжҽ���ȡ������*/
	public void cancelCheckAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo transInfo)throws RemoteException, IRollbackException;
	
	/**
	 * ������Ŀ : added by qhzhou 2011-03-28
	 * ͬҵ�������� ,�˻��˲�����
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * ͬҵҵ����㴦��ȡ������
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckTransCraftbrother(TransCraftbrotherInfo transInfo) throws RemoteException, IRollbackException;

	
}
