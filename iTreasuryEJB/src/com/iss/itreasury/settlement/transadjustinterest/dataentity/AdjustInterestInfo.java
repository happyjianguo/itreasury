package com.iss.itreasury.settlement.transadjustinterest.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * �ۼƷ��ü���Ϣ����ֵ��ϸ��¼��
 * �������ڣ�(2005-9-6 18:29:50)
 * @author��feiye
 */
public class AdjustInterestInfo extends SettlementBaseDataEntity
{
	private long lID = -1;						//��¼ID
    private long lOfficeID=-1;					//���´�ID
    private long lCurrencyID=-1;				//����ID		
    //
    private long lAccountID = -1;				//�˻�ID
    private String strAccountNo = "";			//�˻���
    private long lContractID = -1;				//��ͬID
    private String strContractCode = "";		//��ͬ��
    private long lDueBillID = -1;				//�ſ�֪ͨ��ID
    private String strLetoutRequisitionNo="";	//�ſ�֪ͨ����
    //
    private long lAddOrReduce = -1;				//�ӻ�� 1:�� 0:��
    private Timestamp tsExecute = null;			//ִ����
    private double dInterest = 0;				//�ۼ���Ϣ������
    private double dCommission = 0;				//�ۼ������ѵ�����
    private double dSuretyFee = 0;				//�ۼƵ����ѵ�����
    private double dInterestTax = 0;			//�ۼ���Ϣ˰�ѵ�����
    //
    private long lInputUserID=-1;				//������ID
    private String strInputUser = "";			//����������	
    private Timestamp tsInput = null;			//��������
    
    private long lCheckUserID=-1;				//������ID
    private String strCheckUser = "";			//����������	
    private Timestamp tsCheck = null;			//��������
    //
    private long lStatusID = -1;				//��¼״̬
    private String strAdjustReason = ""; 		//��ע
    
    private long lDepositTypeID = -1;			//��������ID
    //
    private long lSubAccountID = -1;			//���˻�ID
    
    private InutParameterInfo inutParameterInfo = null;
    
    //-------------�д����Ƿ���Ҫ
    //public long m_lLoanTypeID = -1;
    //public long m_lLoanID = -1;
    //public long m_lFixedDepositID = -1;
    //public String m_strFixedDepositFormNo = "";
    /**
	 * @return Returns the lID.
	 */
	public long getLID() {
		return lID;
	}
	/**
	 * @param id The lID to set.
	 */
	public void setLID(long lID) {
		this.lID = lID;
	}
    /**
	 * @return Returns the lOfficeID.
	 */
	public long getLOfficeID() {
		return lOfficeID;
	}
	/**
	 * @param id The lOfficeID to set.
	 */
	public void setLOfficeID(long lOfficeID) {
		this.lOfficeID = lOfficeID;
	}
	/**
	 * @return Returns the lCurrencyID.
	 */
	public long getLCurrencyID() {
		return lCurrencyID;
	}
	/**
	 * @param id The lCurrencyID to set.
	 */
	public void setLCurrencyID(long lCurrencyID) {
		this.lCurrencyID = lCurrencyID;
	}
	/**
	 * @return Returns the lAccountID.
	 */
	public long getLAccountID() {
		return lAccountID;
	}
	/**
	 * @param id The lAccountID to set.
	 */
	public void setLAccountID(long lAccountID) {
		this.lAccountID = lAccountID;
	}
	
	/**
	 * @return Returns the strAccountNo.
	 */
	public String getSAccountNo() {
		return strAccountNo;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSAccountNo(String strAccountNo) {
		this.strAccountNo = strAccountNo;
	}

	/**
	 * @return Returns the lContractID.
	 */
	public long getLContractID() {
		return lContractID;
	}
	/**
	 * @param id The lContractID to set.
	 */
	public void setLContractID(long lContractID) {
		this.lContractID = lContractID;
	}
	
	/**
	 * @return Returns the strContractCode.
	 */
	public String getSContractCode() {
		return strContractCode;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSContractCode(String strContractCode) {
		this.strContractCode = strContractCode;
	}

	/**
	 * @return Returns the lDueBillID.
	 */
	public long getLDueBillID() {
		return lDueBillID;
	}
	/**
	 * @param id The lDueBillID to set.
	 */
	public void setLDueBillID(long lDueBillID) {
		this.lDueBillID = lDueBillID;
	}
	
	/**
	 * @return Returns the strLetoutRequisitionNo.
	 */
	public String getSLetoutRequisitionNo() {
		return strLetoutRequisitionNo;
	}
	/**
	 * @param id The strAccountNo to set.
	 */
	public void setSLetoutRequisitionNo(String strLetoutRequisitionNo) {
		this.strLetoutRequisitionNo = strLetoutRequisitionNo;
	}
	
	 /**
	 * @return Returns the lAddOrReduce.
	 */
	public long getLAddOrReduce() {
		return lAddOrReduce;
	}
	/**
	 * @param id The lAddOrReduce to set.
	 */
	public void setLAddOrReduce(long lAddOrReduce) {
		this.lAddOrReduce = lAddOrReduce;
	}
	
	/**
	 * @return Returns the tsExecute.
	 */
	public Timestamp getTsExecute()
	{
		return tsExecute;
	}
	/**
	 * @param modifyDate The tsExecute to set.
	 */
	public void setTsExecute(Timestamp tsExecute)
	{
		this.tsExecute = tsExecute;
		//putUsedField("modifyDate", modifyDate);
	}
	
	 /**
	 * @return Returns the dInterest.
	 */
	public double getDInterest() {
		return dInterest;
	}
	/**
	 * @param id The dInterest to set.
	 */
	public void setDInterest(double dInterest) {
		this.dInterest = dInterest;
	}
	
	 /**
	 * @return Returns the dCommission.
	 */
	public double getDCommission() {
		return dCommission;
	}
	/**
	 * @param id The dCommission to set.
	 */
	public void setDCommission(double dCommission) {
		this.dCommission = dCommission;
	}
	
	 /**
	 * @return Returns the dSuretyFee.
	 */
	public double getDSuretyFee() {
		return dSuretyFee;
	}
	/**
	 * @param id The dSuretyFee to set.
	 */
	public void setDSuretyFee(double dSuretyFee) {
		this.dSuretyFee = dSuretyFee;
	}
	
	/**
	 * @return Returns the dInterestTax.
	 */
	public double getDInterestTax() {
		return dInterestTax;
	}
	/**
	 * @param id The dInterestTax to set.
	 */
	public void setDInterestTax(double dInterestTax) {
		this.dInterestTax = dInterestTax;
	}
	
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID() {
		return lInputUserID;
	}
	/**
	 * @param id The lInputUserID to set.
	 */
	public void setLInputUserID(long lInputUserID) {
		this.lInputUserID = lInputUserID;
	}
	
	/**
	 * @return Returns the strInputUser.
	 */
	public String getSInputUser() {
		return strInputUser;
	}
	/**
	 * @param id The strInputUser to set.
	 */
	public void setSInputUser(String strInputUser) {
		this.strInputUser = strInputUser;
	}
	
	/**
	 * @return Returns the tsInput.
	 */
	public Timestamp getTsInput()
	{
		return tsInput;
	}
	/**
	 * @param modifyDate The tsInput to set.
	 */
	public void setTsInput(Timestamp tsInput)
	{
		this.tsInput = tsInput;
		//putUsedField("modifyDate", modifyDate);
	}
	
	
	
	/**
	 * @return Returns the lCheckUserID.
	 */
	public long getLCheckUserID() {
		return lCheckUserID;
	}
	/**
	 * @param id The lCheckUserID to set.
	 */
	public void setLCheckUserID(long lCheckUserID) {
		this.lCheckUserID = lCheckUserID;
	}
	
	/**
	 * @return Returns the strCheckUser.
	 */
	public String getSCheckUser() {
		return strCheckUser;
	}
	/**
	 * @param id The strCheckUser to set.
	 */
	public void setSCheckUser(String strCheckUser) {
		this.strCheckUser = strCheckUser;
	}
	
	/**
	 * @return Returns the tsCheck.
	 */
	public Timestamp getTsCheck()
	{
		return tsCheck;
	}
	/**
	 * @param modifyDate The tsCheck to set.
	 */
	public void setTsCheck(Timestamp tsCheck)
	{
		this.tsCheck = tsCheck;
		//putUsedField("modifyDate", modifyDate);
	}
	
	
	/**
	 * @return Returns the lStatusID.
	 */
	public long getLStatusID() {
		return lStatusID;
	}
	/**
	 * @param id The lStatusID to set.
	 */
	public void setLStatusID(long lStatusID) {
		this.lStatusID = lStatusID;
	}
	
	/**
	 * @return Returns the strAdjustReason.
	 */
	public String getSAdjustReason() {
		return strAdjustReason;
	}
	/**
	 * @param id The strAdjustReason to set.
	 */
	public void setSAdjustReason(String strAdjustReason) {
		this.strAdjustReason = strAdjustReason;
	}
	
	
	/**
	 * @return Returns the lDepositTypeID.
	 */
	public long getLDepositTypeID() {
		return lDepositTypeID;
	}
	/**
	 * @param id The lStatusID to set.
	 */
	public void setLDepositTypeID(long lDepositTypeID) {
		this.lDepositTypeID = lDepositTypeID;
	}
	
	/**
	 * @return Returns the lSubAccountID.
	 */
	public long getLSubAccountID() {
		return lSubAccountID;
	}
	/**
	 * @param id The lSubAccountID to set.
	 */
	public void setLSubAccountID(long lSubAccountID) {
		this.lSubAccountID = lSubAccountID;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}
