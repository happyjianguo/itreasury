/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transnoteacceptance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @author feiye ��ҵƱ�ݳжҽ��׵�--�渶��Ϣ�ջ�--ʵ���ࣺ
 *         1�����б�����ΪPrivate,����ֻ����setXXX�������õ�ֻ����getXXX������ 2���������������͡�Ĭ��ֵ��˵�� To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class TransAdvancedReceviceNoteAcceptanceInfo implements Serializable
{
	//���õ���Ϣ
	private long ID = -1; //Ψһ��ʶ
	private String TransNo = ""; //���ױ��
	private long TransactionTypeID = -1; //�������ͣ����ڿ���������֧ȡ����������ת�桢֪ͨ������֪֧ͨȡ��
	private long OfficeID = -1; //���´���ʶ
	private long CurrencyID = -1; //���ֱ�ʶ

	private long StatusID = -1; //����״̬	
	private Timestamp ModifyDate = null; //�޸�ʱ�䣺ʱ����
	private long InputUserID = -1; //¼����ID
	private String InputUserName = ""; //¼��������
	private long CheckUserID = -1; //������ID
	private String CheckUserName = ""; //����������
	private long AbstractID = -1; //ժҪID
	private String Abstract = ""; //ժҪ
	private String CheckAbstract = ""; //����/ȡ������ժҪ
	
	private Timestamp InputDate = null; //¼����
	private Timestamp InterestStartDate = null; //��Ϣ��	
	private Timestamp ExecuteDate = null; //ִ����

	//�������ҵƱ�ݳж�ҵ��(���ڳж�)
	private long IsAdvanced = -1; //�Ƿ��ڵ渶
	private long AcceptancePayAccountID = -1; //�жҸ����ʻ�ID	�����ڲ��ʻ�
	private long AcceptancePayBankID = -1; //�жҸ�������ID
	private long AcceptanceReceiveAccountID = -1; //�ж��տ��ʻ�ID
	private long AcceptanceReceiveBankID = -1; //�ж��տ�����ID
	private double AcceptanceAmount = 0.0; 		//�жҽ��
	
	/**
	 * @return Returns the contractID.
	 */
	public long getContractID() {
		return ContractID;
	}
	/**
	 * @param contractID The contractID to set.
	 */
	public void setContractID(long contractID) {
		ContractID = contractID;
	}
	/**
	 * @return Returns the receiveFormID.
	 */
	public long getReceiveFormID() {
		return ReceiveFormID;
	}
	/**
	 * @param receiveFormID The receiveFormID to set.
	 */
	public void setReceiveFormID(long receiveFormID) {
		ReceiveFormID = receiveFormID;
	}
	private long ContractID = -1; //��ͬID��loan_contractForm��
	private long ReceiveFormID = -1; //�տ�֪ͨ��ID(loan_AssureChargeForm��)
	
	/*
	private long ContractID = -1; //��ͬID��loan_contractForm��
	private long ReceiveFormID = -1; //�տ�֪ͨ��ID(loan_AssureChargeForm��)
	private long ReceviceBailAccountID = -1; //�ձ�֤���ʻ�ID	(�п��ܴӴ����������û�������������Ӧ��ֵ)
	private long PayBailAccountID = -1; //����֤���ʻ�ID
	private long PayBailBankID = -1; //����֤������ID
	private double BailAmount = 0.0; //��ȡ��֤����

	//private long IsBailInterest = -1; //�Ƿ��Ϣ(��֤���ʻ�)		----ֵ��ע��һ��
	//private long PayPoundageAccountID = -1; //���������ʻ�ID
	//private long PayPoundageBankID = -1; //������������ID
	//private double PoundageAmount = 0.0; //��ȡ�����ѽ��
	//private long InterestAccountID=-1;//��Ϣ�ʻ�ID
	
	//��չ����
	private long CashFlowID = -1; //�ֽ�����ID
	private String CashFlowDesc = ""; //�ֽ���������
	private String InstructionNo = ""; //��ʶ�ǽ���ϵͳ��������ˮ��
	private double Rate = 0.0; //����(��֤��)
	
	//Ϊҳ�������һЩ����(��ͬ��Ϣ���տ�֪ͨ����Ϣ)
		//��ͬ�ĸ�����Ϣ
	private String ContractCode = ""; //��ͬ���
	private long ContractFinanceClientID = -1; //���ⵥλ�ͻ�ID
	private String ContractFinanceClientCode = ""; //���ⵥλ�ͻ����	
	private String ContractFinanceClientName = ""; //���ⵥλ�ͻ�����
	
	private Timestamp ContractFinanceStartDate = null; //���޿�ʼ����
	private Timestamp ContractFinanceEndDate = null; //���޽�������
	private long ContractFinanceTerm = -1;			//��ƾ����
	private double ContractFinanceRate = 0.0;		//��������
	private double ContractBailAmount = 0.0; 		//��ͬ��֤����
	
	private double ContractBailAmountForYS = 0.0; //���ձ�֤����	ֻ��	��ʾ�˺�ͬ������ȡ�ı�֤����
	private double ContractBailAmountForWS = 0.0; 		//δ�ձ�֤����	ֻ��	��֤����-���ձ�֤����
	
	//�տ�֪ͨ��������Ϣ
	private Timestamp ReceiveFormDate = null; //�տ�����	ֻ��	�տ�֪ͨ����Ϣ
	private String ReceiveFormCode = ""; //�տ�֪ͨ�����	ֻ��	�տ�֪ͨ����Ϣ
	*/
		
	
	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	/**
	 * @return Returns the abstractID.
	 */
	public long getAbstractID() {
		return AbstractID;
	}
	/**
	 * @param abstractID The abstractID to set.
	 */
	public void setAbstractID(long abstractID) {
		AbstractID = abstractID;
	}
	/**
	 * @return Returns the acceptanceAmount.
	 */
	public double getAcceptanceAmount() {
		return AcceptanceAmount;
	}
	/**
	 * @param acceptanceAmount The acceptanceAmount to set.
	 */
	public void setAcceptanceAmount(double acceptanceAmount) {
		AcceptanceAmount = acceptanceAmount;
	}
	/**
	 * @return Returns the acceptancePayAccountID.
	 */
	public long getAcceptancePayAccountID() {
		return AcceptancePayAccountID;
	}
	/**
	 * @param acceptancePayAccountID The acceptancePayAccountID to set.
	 */
	public void setAcceptancePayAccountID(long acceptancePayAccountID) {
		AcceptancePayAccountID = acceptancePayAccountID;
	}
	/**
	 * @return Returns the acceptancePayBankID.
	 */
	public long getAcceptancePayBankID() {
		return AcceptancePayBankID;
	}
	/**
	 * @param acceptancePayBankID The acceptancePayBankID to set.
	 */
	public void setAcceptancePayBankID(long acceptancePayBankID) {
		AcceptancePayBankID = acceptancePayBankID;
	}
	/**
	 * @return Returns the acceptanceReceiveAccountID.
	 */
	public long getAcceptanceReceiveAccountID() {
		return AcceptanceReceiveAccountID;
	}
	/**
	 * @param acceptanceReceiveAccountID The acceptanceReceiveAccountID to set.
	 */
	public void setAcceptanceReceiveAccountID(long acceptanceReceiveAccountID) {
		AcceptanceReceiveAccountID = acceptanceReceiveAccountID;
	}
	/**
	 * @return Returns the acceptanceReceiveBankID.
	 */
	public long getAcceptanceReceiveBankID() {
		return AcceptanceReceiveBankID;
	}
	/**
	 * @param acceptanceReceiveBankID The acceptanceReceiveBankID to set.
	 */
	public void setAcceptanceReceiveBankID(long acceptanceReceiveBankID) {
		AcceptanceReceiveBankID = acceptanceReceiveBankID;
	}
	/**
	 * @return Returns the checkAbstract.
	 */
	public String getCheckAbstract() {
		return CheckAbstract;
	}
	/**
	 * @param checkAbstract The checkAbstract to set.
	 */
	public void setCheckAbstract(String checkAbstract) {
		CheckAbstract = checkAbstract;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return CheckUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		CheckUserID = checkUserID;
	}
	/**
	 * @return Returns the checkUserName.
	 */
	public String getCheckUserName() {
		return CheckUserName;
	}
	/**
	 * @param checkUserName The checkUserName to set.
	 */
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		ExecuteDate = executeDate;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName() {
		return InputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}
	/**
	 * @return Returns the interestStartDate.
	 */
	public Timestamp getInterestStartDate() {
		return InterestStartDate;
	}
	/**
	 * @param interestStartDate The interestStartDate to set.
	 */
	public void setInterestStartDate(Timestamp interestStartDate) {
		InterestStartDate = interestStartDate;
	}
	/**
	 * @return Returns the isAdvanced.
	 */
	public long getIsAdvanced() {
		return IsAdvanced;
	}
	/**
	 * @param isAdvanced The isAdvanced to set.
	 */
	public void setIsAdvanced(long isAdvanced) {
		IsAdvanced = isAdvanced;
	}
	/**
	 * @return Returns the modifyDate.
	 */
	public Timestamp getModifyDate() {
		return ModifyDate;
	}
	/**
	 * @param modifyDate The modifyDate to set.
	 */
	public void setModifyDate(Timestamp modifyDate) {
		ModifyDate = modifyDate;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo() {
		return TransNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
}