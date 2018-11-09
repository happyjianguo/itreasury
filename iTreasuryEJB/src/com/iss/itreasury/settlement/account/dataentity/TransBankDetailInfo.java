package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;
import java.util.Date;
/**
 * Title:        		iTreasury
 * Description:　　　　　银行支取存入操作的数据类
 * Copyright (c) 2003 Company: iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-1-14
 */
public class TransBankDetailInfo implements Serializable
{
	private long officeID = -1;//	办事处编号ID	
	private long currencyID = -1;//	交易的币种
	private long createUserID = -1;//指令创建人id
	
	private long payBranchID = -1; //付款方开户行设置记录的id         
	private double amount = 0.0; //金额                     
	private String receiveAccountNo = null; //收款账户号               
	private String receiveAccountName = null; //收款账户名称             
	private String receiveDepartmentName = null; //收款人单位全称           
	private String receiveBranchName = null; //收款人开户行名称 
    private String receiveBranchAreaNameOfProvince = null;//收款人开户行所在地省名称 
    private String receiveBranchAreaNameOfCity = null;//收款人开户行所在地城市名称                      
	private String strAbstract = null; //摘要    
	private String transactionNo = null; //指令对应业务地交易号     
	private long transType = -1; //指令对应业务地类型
	
	//新增中行所需的联行号，机构号，手续费账号
	private String receiveBankExchangeCode = "";//收款方联行号 
	private String receiveBranchCodeOfBank = "";//收款方机构号	 
	private String payChargeAccountNo = "";//手续费账号
	private String payChargeBankExchangeCode = "";//手续费账户联行号
	private String payChargeBranchCodeOfBank = "";//手续费账户机构号
	private long BankInstructionType=-1; //指令类型
	private long RemitPriority=-1;
	private String CreateUserName="";
	private Date CreateDate=null;
	private String RemarkInfo="";
	private String PayReferenceCode="";//关联号
	private long PayAccountID=-1;
	private String receiveAccountReferenceCode="";
	public String getPayReferenceCode() {
		return PayReferenceCode;
	}

	public void setPayReferenceCode(String payReferenceCode) {
		PayReferenceCode = payReferenceCode;
	}

	public String getStrAbstract() {
		return strAbstract;
	}

	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}

	/**
	 * Constructor for TransBankDetailInfo.
	 */
	public TransBankDetailInfo()
	{
		super();
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
	 * Returns the payBranchID.
	 * @return long
	 */
	public long getPayBranchID()
	{
		return payBranchID;
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
	 * Sets the payBranchID.
	 * @param payBranchID The payBranchID to set
	 */
	public void setPayBranchID(long payBranchID)
	{
		this.payBranchID = payBranchID;
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
	 * Sets the transType.
	 * @param transType The transType to set
	 */
	public void setTransType(long transType)
	{
		this.transType = transType;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}

	/**
	 * Returns the createUserID.
	 * @return long
	 */
	public long getCreateUserID()
	{
		return createUserID;
	}

	/**
	 * Sets the createUserID.
	 * @param createUserID The createUserID to set
	 */
	public void setCreateUserID(long createUserID)
	{
		this.createUserID = createUserID;
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

	public long getBankInstructionType() {
		return BankInstructionType;
	}

	public void setBankInstructionType(long setBankInstructionType) {
		this.BankInstructionType = setBankInstructionType;
	}

	public long getRemitPriority() {
		return RemitPriority;
	}

	public void setRemitPriority(long remitPriority) {
		RemitPriority = remitPriority;
	}

	public String getCreateUserName() {
		return CreateUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.CreateUserName = createUserName;
	}

	public Date getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}

	public String getRemarkInfo() {
		return RemarkInfo;
	}

	public void setRemarkInfo(String remarkInfo) {
		RemarkInfo = remarkInfo;
	}

	public long getPayAccountID() {
		return PayAccountID;
	}

	public void setPayAccountID(long payAccountID) {
		PayAccountID = payAccountID;
	}

	public String getReceiveAccountReferenceCode() {
		return receiveAccountReferenceCode;
	}

	public void setReceiveAccountReferenceCode(String receiveAccountReferenceCode) {
		this.receiveAccountReferenceCode = receiveAccountReferenceCode;
	}

}
