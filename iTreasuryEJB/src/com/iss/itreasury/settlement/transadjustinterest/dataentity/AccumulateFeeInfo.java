package com.iss.itreasury.settlement.transadjustinterest.dataentity;
import java.sql.Timestamp;

/**
 * �˻����������Ϣ��
 * �������ڣ�(2002-5-9 14:16:30)
 * @author��linsg
 */

public class AccumulateFeeInfo implements java.io.Serializable 
{
	private long lID = -1;							//���˻�ID
	
	private long lAccountID = -1;					//�˻�ID
	private long lContractID = -1;					//��ͬID
	private long lDueBillID = -1;					//�ſ�֪ͨ��ID
    private String strAccountNo = "";				//�˻����
    private String strContractNo = "";				//��ͬ��
    private String strLetoutRequisitionNo="";		//�ſ�֪ͨ����
    //
    private double dInterest = 0;					//�ۼ���Ϣ
    private double dCommission = 0;					//�ۼ�������
    private double dSuretyFee = 0;					//�ۼƵ�����
    private double dInterestTax = 0;				//�ۼ���Ϣ˰��
    private String strInterestRatePlanName = "";	//��Ϣ�ƻ�
    //
    private Timestamp tsOpen = null;				//��������
    //
    private long lDepositTypeID=-1;					//�������
    
    /*	����һЩ����
    public long lID = -1;
    public long lFixedDepositID = -1;
    public long lTypeID = -1;
    public String strFixedDepositNo = "";
    public String strClientName = "";//
    public String strTransactionNo = "";//
    public String strAbstract = "";//
    public long lAddOrReduceID=1;//���ӻ��߼��٣�ȱʡ1����
    public String strAdjustReason = "";
    */
    
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
	 * @return Returns the strContractNo.
	 */
	public String getSContractNo() {
		return strContractNo;
	}
	/**
	 * @param id The strContractNo to set.
	 */
	public void setSContractNo(String strContractNo) {
		this.strContractNo = strContractNo;
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
	 * @return Returns the strInterestRatePlanName.
	 */
	public String getSInterestRatePlanName() {
		return strInterestRatePlanName;
	}
	/**
	 * @param id The strInterestRatePlanName to set.
	 */
	public void setSInterestRatePlanName(String strInterestRatePlanName) {
		this.strInterestRatePlanName = strInterestRatePlanName;
	}
	
	/**
	 * @return Returns the tsOpen.
	 */
	public Timestamp getTsOpen()
	{
		return tsOpen;
	}
	/**
	 * @param modifyDate The tsOpen to set.
	 */
	public void setTsOpen(Timestamp tsOpen)
	{
		this.tsOpen = tsOpen;
		//putUsedField("modifyDate", modifyDate);
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
}