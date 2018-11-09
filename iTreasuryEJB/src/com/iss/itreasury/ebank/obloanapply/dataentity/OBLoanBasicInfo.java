/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;

import java.io.Serializable;
import com.iss.itreasury.ebank.obdataentity.*;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanBasicInfo implements java.io.Serializable
{

	private long    loanID=-1;             //流水号
	private long    loanType=-1;           //贷款种类
	private long    subTypeId=-1;           //贷款子类
	private double  loanAmount=0;          //借款金额
	private String  loanReason="";         //借款原因
	private String  loanPurpose="";        //借款目的
	private String  other="";              //其他，还款来源及措施,        
	private long    bankInterestID=-1;     //银行利率
	private double  chargeRate=0;          //手续费率
	private long    checkPayType =-1;      //手续费收取方式
	private long    intervalNum=-1;        //期限
	private Timestamp startDate;           //贷款开始日期
	private Timestamp endDate;             //贷款结束日期
	private String  clientInfo="";         //借款单位介绍
	private double  saleAmount=0;          //财务公司承贷额度
	private double  interestRate=0;         //委托用贷款利率
	
	private OBSecurityInfo securityInfo=null;			//安全信息   
	
	//	担保
	private double assureChargeRate = 0; //担保费率
	private long assureChargeTypeID = -1;//担保费收取方式
	private String beneficiary = "";	 //受益人
	private long assureTypeID1 = -1;	 //担保类型1
	private long assureTypeID2 = -1;	 //担保类型2
	/**
	 * 设置贷款申请的流水号
	 * @param loanID long,贷款申请的流水号
	 */
	public void setLoanID(long loanID)
	{
		this.loanID=loanID;
	}
	/**
	 * 获取贷款申请的流水号
	 * @return long 贷款申请的流水号
	 */
	public long getLoanID()
	{
		return loanID;
	}
	public void setLoanType(long loanType)
	{
		this.loanType=loanType;
	}
	public long getLoanType()
	{
		return this.loanType;
	}
	/**
	 * 设置贷款金额
	 * @param double loanAmount 贷款金额
	 */
	public void setLoanAmount(double loanAmount)
	{
		this.loanAmount=loanAmount;
	}
	/**
	 * 获取贷款金额
	 * @return double 贷款金额
	 */
	public double getLoanAmount()
	{
		return this.loanAmount;
	}
    
	/**
	 * 设置借款原因
	 * @param String loanReason 借款原因
	 */
	public void setLoanReason(String loanReason)
	{
		this.loanReason=loanReason;
	}
	/**
	 * 获取借款原因
	 * @return String 借款原因
	 */
	public String getLoanReason()
	{
		return this.loanReason;
	}
    
	/**
	 * 设置借款目的
	 * @param String loanPurpose 借款目的
	 */
	public void setLoanPurpose(String loanPurpose)
	{
		this.loanPurpose=loanPurpose;
	}
	/**
	 * 获取贷款目的
	 * @return String 借款目的
	 */
	public String getLoanPurpose()
	{
		return this.loanPurpose;
	}
    
	/**
	 * 设置环款来源和措施
	 * @param String other 还款来源和措施
	 */
	public void setOther(String other)
	{
		this.other=other;
	}
	/**
	 * 获得还款来源和措施
	 * @return String 还款来源和措施
	 */
	public String getOther()
	{
		return this.other;
	}
	public void setBankInterestID(long interestID)
	{
		this.bankInterestID=interestID; 
	}
	public long getBankInterestID()
	{
		return this.bankInterestID;
	}
    
	public void setChargeRate(double cRate)
	{
		this.chargeRate=cRate;
	}
	public double getChargeRate()
	{
		return this.chargeRate;
	}
	public void setIntervalNum(long iNum)
	{
		this.intervalNum=iNum;
	}
	public long getIntervalNum()
	{
		return this.intervalNum;
	}
	public void setStartDate(Timestamp sDate)
	{
		this.startDate=sDate;
	}
	public Timestamp getStartDate()
	{
		return this.startDate;
	}
	public void setEndDate(Timestamp eDate)
	{
		this.endDate=eDate;
	}
	public Timestamp getEndDate()
	{
		return this.endDate;
	}
	public void setClientInfo(String cInfo)
	{
		this.clientInfo=cInfo;
	}
	public String getClientInfo()
	{
		return this.clientInfo;
	}
	public void setSaleAmount(double saleAmount)
	{
		this.saleAmount=saleAmount;
	}
	public double getSaleAmount()
	{
		return this.saleAmount;
	}
	public void setInterestRate(double irate)
	{
		this.interestRate=irate;
	}
	public double getInterestRate()
	{
		return this.interestRate;
	}


	/**
	 * @return
	 */
	public OBSecurityInfo getSecurityInfo()
	{
		return securityInfo;
	}

	/**
	 * @param info
	 */
	public void setSecurityInfo(OBSecurityInfo info)
	{
		securityInfo = info;
	}

	/**
	 * @return Returns the checkPayType.
	 */
	public long getCheckPayType() 
	{
		return checkPayType;
	}
	/**
	 * @param checkPayType The checkPayType to set.
	 */
	public void setCheckPayType(long checkPayType) 
	{
		this.checkPayType = checkPayType;
	}
	public long getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(long subTypeId) {
		this.subTypeId = subTypeId;
	}
	public double getAssureChargeRate() {
		return assureChargeRate;
	}
	public void setAssureChargeRate(double assureChargeRate) {
		this.assureChargeRate = assureChargeRate;
	}
	public long getAssureChargeTypeID() {
		return assureChargeTypeID;
	}
	public void setAssureChargeTypeID(long assureChargeTypeID) {
		this.assureChargeTypeID = assureChargeTypeID;
	}
	public long getAssureTypeID1() {
		return assureTypeID1;
	}
	public void setAssureTypeID1(long assureTypeID1) {
		this.assureTypeID1 = assureTypeID1;
	}
	public long getAssureTypeID2() {
		return assureTypeID2;
	}
	public void setAssureTypeID2(long assureTypeID2) {
		this.assureTypeID2 = assureTypeID2;
	}
	public String getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
}
