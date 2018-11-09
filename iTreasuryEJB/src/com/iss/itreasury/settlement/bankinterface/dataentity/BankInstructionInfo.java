package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        		iTreasury
 * Description:         ����ת��ָ��������
��* Copyright:             Copyright (c) 2003 Company: iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-1-14
 */
public class BankInstructionInfo implements Serializable,Cloneable 
{
	private long ID = -1;                                             
	private String payAccountNo = null;//�����˻���(���������)
	private String payBankAccountNO = null;//���������˻���
	private String payAcctBankCode = null; //�����˻����б��
	private String payAccountName = null;//�����˻�����             
	private String payDepartmentName = null;//�����˵�λȫ��           
	private String payBranchName = null;//�����˿���������         
	private double amount = 0.0;//���                     
	private String receiveAccountNo = null;//�տ��˻���               
	private String receiveAccountName = null;//�տ��˻�����             
	private String receiveDepartmentName = null;//�տ��˵�λȫ��           
	private String receiveBranchName = null;//�տ��˿���������        
    private String receiveBranchAreaNameOfProvince = null;//�տ��˿��������ڵ�ʡ���� 
	private String receiveBranchAreaNameOfCity = null;//�տ��˿��������ڵس�������       
	private String comment = null;//��ע                     
	private String strAbstract = null;//ժҪ                     
	private String senderNo = null;//ָ�����no             
	private String cancellerNo = null;//ָ�����no 
	private Timestamp createTime = null;//ָ���ʱ��            
	private Timestamp sendTime = null;//ָ���ʱ��             
	private Timestamp cancelTime = null;//ָ���ʱ��             
	private Timestamp modifyTime = null;//ָ���޸�ʱ��             
	private long statusID = -1;//ָ��״̬                 
	private String transactionNo = null;//ָ���Ӧҵ��ؽ��׺�     
	private long transType = -1;//ָ���Ӧҵ�������       
	private long receiveBank = -1;//ָ���������             
	private String IDOfBankSeg1 = null;//ָ�������еض�Ӧ��ʶ     
	private String IDOfBankSeg2 = null;//ͬ��                     
	private String IDOfBankSeg3 = null;//ͬ��                     
	private String IDOfBankSeg4 = null;//ͬ��                     
	private Timestamp signTime = null;//ָ��ǩ��ʱ��             
	private Timestamp transTimeOfBank = null;//���в�ؽ���ʱ��         
	private String statusIDOfBank = null;//���в��ת��ָ��״̬     
	private String statusDescOfBank = null;//���в��ת��ָ��״̬���� 
	private String remindOfBank = null;//���еط�����Ϣ
         
	//�����ֶ�
	private String payAreaNameOfProvince = null;//�����˿��������ڵ�ʡ����
	private String payAreaNameOfCity = null;//�����˿��������ڵس�������
	private long currencyID = -1;//���ױ���
	private long isSameBank = -1;//ͬ�б�־��true��false��
	private long remitPriority = -1;//����ٶ�
	private long chargesBorneType = -1;//���óе���
	
	//����������������кţ������ţ��������˺�
	private String payBankExchangeCode = "";//������к� 
	private String payBranchCodeOfBank = "";//���������	 
	private String receiveBankExchangeCode = "";//�տ���к� 
	private String receiveBranchCodeOfBank = "";//�տ������	 
	private String payChargeAccountNo = "";//�������˺� 
	private String payChargeBankExchangeCode = "";//�������˻����к�
	private String payChargeBranchCodeOfBank = "";//�������˻�������
	private String receiveReferenceCode="";//�շ����ڲ�������
	private String recNetStationName = ""; //�տ��������
	
	//������������Ϣ
	private String agentAcctNoOfPay = "";//�������˺�
	private String agentBankNameOfPay = "";//�������˻�����
	
	//����ӿ�����
	private String recBankCode = "";  //CNAPSָ���
	
	//�������´���Ϣ
	private long officeId=-1;
	
	
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return Returns the agentAcctNoOfPay.
	 */
	public String getAgentAcctNoOfPay() {
		return agentAcctNoOfPay;
	}
	/**
	 * @param agentAcctNoOfPay The agentAcctNoOfPay to set.
	 */
	public void setAgentAcctNoOfPay(String agentAcctNoOfPay) {
		this.agentAcctNoOfPay = agentAcctNoOfPay;
	}
	/**
	 * @return Returns the agentBankNameOfPay.
	 */
	public String getAgentBankNameOfPay() {
		return agentBankNameOfPay;
	}
	/**
	 * @param agentBankNameOfPay The agentBankNameOfPay to set.
	 */
	public void setAgentBankNameOfPay(String agentBankNameOfPay) {
		this.agentBankNameOfPay = agentBankNameOfPay;
	}
	/**
	 * @return Returns the recVetStationName.
	 */
	public String getRecNetStationName() {
		return recNetStationName;
	}
	/**
	 * @param recVetStationName The recVetStationName to set.
	 */
	public void setRecNetStationName(String recNetStationName) {
		this.recNetStationName = recNetStationName;
	}
	/**
	 * @return Returns the chargesBorneType.
	 */
	public long getChargesBorneType() {
		return chargesBorneType;
	}
	/**
	 * @param chargesBorneType The chargesBorneType to set.
	 */
	public void setChargesBorneType(long chargesBorneType) {
		this.chargesBorneType = chargesBorneType;
	}
	/**
	 * @return Returns the payBankAccountNO.
	 */
	public String getPayBankAccountNO() {
		return payBankAccountNO;
	}
	/**
	 * @param payBankAccountNO The payBankAccountNO to set.
	 */
	public void setPayBankAccountNO(String payBankAccountNO) {
		this.payBankAccountNO = payBankAccountNO;
	}
	/**
	 * @return Returns the payAcctBankCode.
	 */
	public String getPayAcctBankCode() {
		return payAcctBankCode;
	}
	/**
	 * @param payAcctBankCode The payAcctBankCode to set.
	 */
	public void setPayAcctBankCode(String payAcctBankCode) {
		this.payAcctBankCode = payAcctBankCode;
	}
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Returns the cancellerNo.
	 * @return String
	 */
	public String getCancellerNo()
	{
		return cancellerNo;
	}

	/**
	 * Returns the cancelTime.
	 * @return Timestamp
	 */
	public Timestamp getCancelTime()
	{
		return cancelTime;
	}

	/**
	 * Returns the comment.
	 * @return String
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the iDOfBankSeg1.
	 * @return String
	 */
	public String getIDOfBankSeg1()
	{
		return IDOfBankSeg1;
	}

	/**
	 * Returns the iDOfBankSeg2.
	 * @return String
	 */
	public String getIDOfBankSeg2()
	{
		return IDOfBankSeg2;
	}

	/**
	 * Returns the iDOfBankSeg3.
	 * @return String
	 */
	public String getIDOfBankSeg3()
	{
		return IDOfBankSeg3;
	}

	/**
	 * Returns the iDOfBankSeg4.
	 * @return String
	 */
	public String getIDOfBankSeg4()
	{
		return IDOfBankSeg4;
	}

	/**
	 * Returns the modifyTime.
	 * @return Timestamp
	 */
	public Timestamp getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * Returns the payAccountName.
	 * @return String
	 */
	public String getPayAccountName()
	{
		return payAccountName;
	}

	/**
	 * Returns the payAccountNo.
	 * @return String
	 */
	public String getPayAccountNo()
	{
		return payAccountNo;
	}

	/**
	 * Returns the payBranchName.
	 * @return String
	 */
	public String getPayBranchName()
	{
		return payBranchName;
	}

	/**
	 * Returns the payDepartmentName.
	 * @return String
	 */
	public String getPayDepartmentName()
	{
		return payDepartmentName;
	}

	/**
	 * Returns the receiveAccountName.
	 * @return String
	 */
	public String getReceiveAccountName()
	{
		return receiveAccountName;
	}

	/**
	 * Returns the receiveAccountNo.
	 * @return String
	 */
	public String getReceiveAccountNo()
	{
		return receiveAccountNo;
	}

	/**
	 * Returns the receiveBank.
	 * @return long
	 */
	public long getReceiveBank()
	{
		return receiveBank;
	}

	/**
	 * Returns the receiveBranchName.
	 * @return String
	 */
	public String getReceiveBranchName()
	{
		return receiveBranchName;
	}

	/**
	 * Returns the receiveDepartmentName.
	 * @return String
	 */
	public String getReceiveDepartmentName()
	{
		return receiveDepartmentName;
	}

	/**
	 * Returns the remindOfBank.
	 * @return String
	 */
	public String getRemindOfBank()
	{
		return remindOfBank;
	}

	/**
	 * Returns the senderNo.
	 * @return String
	 */
	public String getSenderNo()
	{
		return senderNo;
	}

	/**
	 * Returns the sendTime.
	 * @return Timestamp
	 */
	public Timestamp getSendTime()
	{
		return sendTime;
	}

	/**
	 * Returns the signTime.
	 * @return Timestamp
	 */
	public Timestamp getSignTime()
	{
		return signTime;
	}

	/**
	 * Returns the statusDescOfBank.
	 * @return String
	 */
	public String getStatusDescOfBank()
	{
		return statusDescOfBank;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * Returns the statusIDOfBank.
	 * @return String
	 */
	public String getStatusIDOfBank()
	{
		return statusIDOfBank;
	}

	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract()
	{
		return strAbstract;
	}

	/**
	 * Returns the transactionNo.
	 * @return String
	 */
	public String getTransactionNo()
	{
		return transactionNo;
	}

	/**
	 * Returns the transTimeOfBank.
	 * @return Timestamp
	 */
	public Timestamp getTransTimeOfBank()
	{
		return transTimeOfBank;
	}

	/**
	 * Returns the transType.
	 * @return long
	 */
	public long getTransType()
	{
		return transType;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Sets the cancellerNo.
	 * @param cancellerNo The cancellerNo to set
	 */
	public void setCancellerNo(String cancellerNo)
	{
		this.cancellerNo = cancellerNo;
	}

	/**
	 * Sets the cancelTime.
	 * @param cancelTime The cancelTime to set
	 */
	public void setCancelTime(Timestamp cancelTime)
	{
		this.cancelTime = cancelTime;
	}

	/**
	 * Sets the comment.
	 * @param comment The comment to set
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the iDOfBankSeg1.
	 * @param iDOfBankSeg1 The iDOfBankSeg1 to set
	 */
	public void setIDOfBankSeg1(String iDOfBankSeg1)
	{
		IDOfBankSeg1 = iDOfBankSeg1;
	}

	/**
	 * Sets the iDOfBankSeg2.
	 * @param iDOfBankSeg2 The iDOfBankSeg2 to set
	 */
	public void setIDOfBankSeg2(String iDOfBankSeg2)
	{
		IDOfBankSeg2 = iDOfBankSeg2;
	}

	/**
	 * Sets the iDOfBankSeg3.
	 * @param iDOfBankSeg3 The iDOfBankSeg3 to set
	 */
	public void setIDOfBankSeg3(String iDOfBankSeg3)
	{
		IDOfBankSeg3 = iDOfBankSeg3;
	}

	/**
	 * Sets the iDOfBankSeg4.
	 * @param iDOfBankSeg4 The iDOfBankSeg4 to set
	 */
	public void setIDOfBankSeg4(String iDOfBankSeg4)
	{
		IDOfBankSeg4 = iDOfBankSeg4;
	}

	/**
	 * Sets the modifyTime.
	 * @param modifyTime The modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * Sets the payAccountName.
	 * @param payAccountName The payAccountName to set
	 */
	public void setPayAccountName(String payAccountName)
	{
		this.payAccountName = payAccountName;
	}

	/**
	 * Sets the payAccountNo.
	 * @param payAccountNo The payAccountNo to set
	 */
	public void setPayAccountNo(String payAccountNo)
	{
		this.payAccountNo = payAccountNo;
	}

	/**
	 * Sets the payBranchName.
	 * @param payBranchName The payBranchName to set
	 */
	public void setPayBranchName(String payBranchName)
	{
		this.payBranchName = payBranchName;
	}

	/**
	 * Sets the payDepartmentName.
	 * @param payDepartmentName The payDepartmentName to set
	 */
	public void setPayDepartmentName(String payDepartmentName)
	{
		this.payDepartmentName = payDepartmentName;
	}

	/**
	 * Sets the receiveAccountName.
	 * @param receiveAccountName The receiveAccountName to set
	 */
	public void setReceiveAccountName(String receiveAccountName)
	{
		this.receiveAccountName = receiveAccountName;
	}

	/**
	 * Sets the receiveAccountNo.
	 * @param receiveAccountNo The receiveAccountNo to set
	 */
	public void setReceiveAccountNo(String receiveAccountNo)
	{
		this.receiveAccountNo = receiveAccountNo;
	}

	/**
	 * Sets the receiveBank.
	 * @param receiveBank The receiveBank to set
	 */
	public void setReceiveBank(long receiveBank)
	{
		this.receiveBank = receiveBank;
	}

	/**
	 * Sets the receiveBranchName.
	 * @param receiveBranchName The receiveBranchName to set
	 */
	public void setReceiveBranchName(String receiveBranchName)
	{
		this.receiveBranchName = receiveBranchName;
	}

	/**
	 * Sets the receiveDepartmentName.
	 * @param receiveDepartmentName The receiveDepartmentName to set
	 */
	public void setReceiveDepartmentName(String receiveDepartmentName)
	{
		this.receiveDepartmentName = receiveDepartmentName;
	}

	/**
	 * Sets the remindOfBank.
	 * @param remindOfBank The remindOfBank to set
	 */
	public void setRemindOfBank(String remindOfBank)
	{
		this.remindOfBank = remindOfBank;
	}

	/**
	 * Sets the senderNo.
	 * @param senderNo The senderNo to set
	 */
	public void setSenderNo(String senderNo)
	{
		this.senderNo = senderNo;
	}

	/**
	 * Sets the sendTime.
	 * @param sendTime The sendTime to set
	 */
	public void setSendTime(Timestamp sendTime)
	{
		this.sendTime = sendTime;
	}

	/**
	 * Sets the signTime.
	 * @param signTime The signTime to set
	 */
	public void setSignTime(Timestamp signTime)
	{
		this.signTime = signTime;
	}

	/**
	 * Sets the statusDescOfBank.
	 * @param statusDescOfBank The statusDescOfBank to set
	 */
	public void setStatusDescOfBank(String statusDescOfBank)
	{
		this.statusDescOfBank = statusDescOfBank;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusID = statusID;
	}

	/**
	 * Sets the statusIDOfBank.
	 * @param statusIDOfBank The statusIDOfBank to set
	 */
	public void setStatusIDOfBank(String statusIDOfBank)
	{
		this.statusIDOfBank = statusIDOfBank;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a)
	{
		strAbstract = a;
	}

	/**
	 * Sets the transactionNo.
	 * @param transactionNo The transactionNo to set
	 */
	public void setTransactionNo(String transactionNo)
	{
		this.transactionNo = transactionNo;
	}

	/**
	 * Sets the transTimeOfBank.
	 * @param transTimeOfBank The transTimeOfBank to set
	 */
	public void setTransTimeOfBank(Timestamp transTimeOfBank)
	{
		this.transTimeOfBank = transTimeOfBank;
	}

	/**
	 * Sets the transType.
	 * @param transType The transType to set
	 */
	public void setTransType(long transType)
	{
		this.transType = transType;
	}

	/**
	 * Returns the createTime.
	 * @return Timestamp
	 */
	public Timestamp getCreateTime()
	{
		return createTime;
	}

	/**
	 * Sets the createTime.
	 * @param createTime The createTime to set
	 */
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}

    /**
     * Returns the receiveBranchAreaNameOfCity.
     * @return String
     */
    public String getReceiveBranchAreaNameOfCity()
    {
        return receiveBranchAreaNameOfCity;
    }

    /**
     * Returns the receiveBranchAreaNameOfProvince.
     * @return String
     */
    public String getReceiveBranchAreaNameOfProvince()
    {
        return receiveBranchAreaNameOfProvince;
    }

    /**
     * Sets the receiveBranchAreaNameOfCity.
     * @param receiveBranchAreaNameOfCity The receiveBranchAreaNameOfCity to set
     */
    public void setReceiveBranchAreaNameOfCity(String receiveBranchAreaNameOfCity)
    {
        this.receiveBranchAreaNameOfCity = receiveBranchAreaNameOfCity;
    }

    /**
     * Sets the receiveBranchAreaNameOfProvince.
     * @param receiveBranchAreaNameOfProvince The receiveBranchAreaNameOfProvince to set
     */
    public void setReceiveBranchAreaNameOfProvince(String receiveBranchAreaNameOfProvince)
    {
        this.receiveBranchAreaNameOfProvince = receiveBranchAreaNameOfProvince;
    }

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the isSameBank.
	 */
	public long getIsSameBank()
	{
		return isSameBank;
	}
	/**
	 * @param isSameBank The isSameBank to set.
	 */
	public void setIsSameBank(long isSameBank)
	{
		this.isSameBank = isSameBank;
	}
	/**
	 * @return Returns the payAreaNameOfCity.
	 */
	public String getPayAreaNameOfCity()
	{
		return payAreaNameOfCity;
	}
	/**
	 * @param payAreaNameOfCity The payAreaNameOfCity to set.
	 */
	public void setPayAreaNameOfCity(String payAreaNameOfCity)
	{
		this.payAreaNameOfCity = payAreaNameOfCity;
	}
	/**
	 * @return Returns the payAreaNameOfProvince.
	 */
	public String getPayAreaNameOfProvince()
	{
		return payAreaNameOfProvince;
	}
	/**
	 * @param payAreaNameOfProvince The payAreaNameOfProvince to set.
	 */
	public void setPayAreaNameOfProvince(String payAreaNameOfProvince)
	{
		this.payAreaNameOfProvince = payAreaNameOfProvince;
	}
	/**
	 * @return Returns the remitPriority.
	 */
	public long getRemitPriority()
	{
		return remitPriority;
	}
	/**
	 * @param remitPriority The remitPriority to set.
	 */
	public void setRemitPriority(long remitPriority)
	{
		this.remitPriority = remitPriority;
	}
	/**
	 * @return Returns the strAbstract.
	 */
	public String getStrAbstract()
	{
		return strAbstract;
	}
	/**
	 * @param strAbstract The strAbstract to set.
	 */
	public void setStrAbstract(String strAbstract)
	{
		this.strAbstract = strAbstract;
	}

	/**
	 * @return Returns the payBankExchangeCode.
	 */
	public String getPayBankExchangeCode() {
		return payBankExchangeCode;
	}

	/**
	 * @param payBankExchangeCode The payBankExchangeCode to set.
	 */
	public void setPayBankExchangeCode(String payBankExchangeCode) {
		this.payBankExchangeCode = payBankExchangeCode;
	}

	/**
	 * @return Returns the payBranchCodeOfBank.
	 */
	public String getPayBranchCodeOfBank() {
		return payBranchCodeOfBank;
	}

	/**
	 * @param payBranchCodeOfBank The payBranchCodeOfBank to set.
	 */
	public void setPayBranchCodeOfBank(String payBranchCodeOfBank) {
		this.payBranchCodeOfBank = payBranchCodeOfBank;
	}

	/**
	 * @return Returns the payChargeAccountNo.
	 */
	public String getPayChargeAccountNo() {
		return payChargeAccountNo;
	}

	/**
	 * @param payChargeAccountNo The payChargeAccountNo to set.
	 */
	public void setPayChargeAccountNo(String payChargeAccountNo) {
		this.payChargeAccountNo = payChargeAccountNo;
	}

	/**
	 * @return Returns the payChargeBankExchangeCode.
	 */
	public String getPayChargeBankExchangeCode() {
		return payChargeBankExchangeCode;
	}

	/**
	 * @param payChargeBankExchangeCode The payChargeBankExchangeCode to set.
	 */
	public void setPayChargeBankExchangeCode(String payChargeBankExchangeCode) {
		this.payChargeBankExchangeCode = payChargeBankExchangeCode;
	}

	/**
	 * @return Returns the payChargeBranchCodeOfBank.
	 */
	public String getPayChargeBranchCodeOfBank() {
		return payChargeBranchCodeOfBank;
	}

	/**
	 * @param payChargeBranchCodeOfBank The payChargeBranchCodeOfBank to set.
	 */
	public void setPayChargeBranchCodeOfBank(String payChargeBranchCodeOfBank) {
		this.payChargeBranchCodeOfBank = payChargeBranchCodeOfBank;
	}

	/**
	 * @return Returns the receiveBankExchangeCode.
	 */
	public String getReceiveBankExchangeCode() {
		return receiveBankExchangeCode;
	}

	/**
	 * @param receiveBankExchangeCode The receiveBankExchangeCode to set.
	 */
	public void setReceiveBankExchangeCode(String receiveBankExchangeCode) {
		this.receiveBankExchangeCode = receiveBankExchangeCode;
	}

	/**
	 * @return Returns the receiveBranchCodeOfBank.
	 */
	public String getReceiveBranchCodeOfBank() {
		return receiveBranchCodeOfBank;
	}

	/**
	 * @param receiveBranchCodeOfBank The receiveBranchCodeOfBank to set.
	 */
	public void setReceiveBranchCodeOfBank(String receiveBranchCodeOfBank) {
		this.receiveBranchCodeOfBank = receiveBranchCodeOfBank;
	}

	public String getReceiveReferenceCode() {
		return receiveReferenceCode;
	}

	public void setReceiveReferenceCode(String payReferenceCode) {
		this.receiveReferenceCode = payReferenceCode;
	}
	public String getRecBankCode() {
		return recBankCode;
	}
	public void setRecBankCode(String recBankCode) {
		this.recBankCode = recBankCode;
	}
	
	public Object clone()
	{
		Object bankInstructionInfo = null;
		try {
			bankInstructionInfo =  super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bankInstructionInfo;
	}
}
