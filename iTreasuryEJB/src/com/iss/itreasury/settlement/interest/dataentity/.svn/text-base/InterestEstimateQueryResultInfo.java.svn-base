/*
 * Created on 2003-10-28
 *
 * InterestEstimateQueryResultInfo.java
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;
import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;
/**
 * @author xiren li
 *
 * 利息匡算页面查询结果信息实体。
 * 该类主要用来保存页面的查询结果，在页面上显示。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 该实体中的一些信息直接来自数据库，另外一些信息来自算息模块的计算。
 */
public class InterestEstimateQueryResultInfo implements Serializable{
	
    
	private String AccountNo = "";                         // 账户号
	private long  AccountID = -1;                         // 账户ID
	private long  AccountTypeID = -1;                     // 账户类型ID
	private long LoanTypeID = -1;                         //贷款类型
	private long nsubTypeID = -1;							//贷款子类型ID
	private String nsubTypeName = "";						//贷款子类型名称
	private long  SubAccountID = -1;                      // 子账户ID	
    private String ClientName = "";                       // 客户名称
    private String ClientNo = "";                          // 客户号
	private long   ClientID = -1;                         // 客户ID 
    private double CompoundInterest = 0.00;                //复利
    private double SelfLoanInterest = 0.00;                //自营贷款利息  
    private double ConsignLoanInterest = 0.00;             //委托贷款利息
    private double ForfeitInterest = 0.00;                 //罚息  
    private double TotalInterest = 0.00;                   //利息合计
    private double Commission = 0.00;                      //手续费
    private double AssuranceCharge = 0.00;                 //担保费 
    private Timestamp ClearInterestDate = null;           //结息日期
    private double Total = 0.00;                           //合计
    private double Balance = 0.00;                         //余额
    private double BackInterest = 0.00;   //欠息
    private int flag = 1;//
    //后加
	private double Interest = 0.00;                       //利息   
    private double InterestTaxCharge = 0.00;               //利息税费
    private double InterestTaxRate = 0.00;                 //利息税费利率
    private long   InterestTaxRatePlan = -1;               //利息税费利率计划
    private double Rate = 0.00;                            //利率
	private double Amount = 0.00;                          //本金
	private Timestamp StartDate = null;                   //开始日期
	private Timestamp EndDate = null;                     //结束日期
	private long Days = -1;                                //天数
	private String ContractNo = "";                      //合同号
	private String PayFormNo = "";                       //放款通知单号
	private long PayFormID = -1;                       //放款通知单ID
	private String FixedDepositNo = "";                  //定期单据号
	private long InterestType = -1;                          //利息类型
	private double InterestBalance = 0.00;                          //记息金额
	private String LoanAccountNo = "";                  //贷款账户号
	private long ContractTerm = -1;                     //合同期限
	private double LoanAmount = 0.00;                   //贷款本金
	private Vector PayInterestAccountID=null;           //付款账户
	private long npayInterestAccountID = -1;//付息账户ID
	private String npayInterestAccountNO = "";//付息账号
	private double npayInterestAccountBalance = 0.00;//付息账户余额
	private String strStartDate="";
	private String strEndDate=""; 
	private String strDays="";
	private String strRate="";
	private String strInterest="";
	
	//Added by ylguo(郭英亮) at 2009-02-09解决委托方名称显示错误的情况
	private String ConsignClientName="";//委托方名称
	private String ConsignClientCode="";//委托方编号 
	private long ConsignClientID= -1;//委托方ID 
      
    
    public long getConsignClientID() {
		return ConsignClientID;
	}


	public void setConsignClientID(long consignClientID) {
		ConsignClientID = consignClientID;
	}


	public String getStrEndDate() {
		return strEndDate;
	}


	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}


	public String getStrStartDate() {
		return strStartDate;
	}


	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}


	/**
     * @return a string representing the current object using reflect api.
     */
    public String toString(){
        
        Field[] allFields = getClass().getDeclaredFields();
        StringBuffer buffer = new StringBuffer();
        
        try{
            AccessibleObject.setAccessible(allFields, true);
            buffer.append("InterestEstimateQueryResultInfo[");
            for(int i = 0; i < allFields.length; i++){
                buffer.append(allFields[i].getName());
                buffer.append("=");
                buffer.append(allFields[i].get(this));
                buffer.append("\n");
            }
        }catch(Exception e){
            return e.getMessage();
        }
        buffer.append("]");
        return buffer.toString();
    }  
  

	/**
	 * @return
	 */
	public double getAssuranceCharge()
	{
		return AssuranceCharge;
	}

	/**
	 * @return
	 */
	public double getBackInterest()
	{
		return BackInterest;
	}

	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @return
	 */
	public String getClientNo()
	{
		return ClientNo;
	}

	/**
	 * @return
	 */
	public double getCommission()
	{
		return Commission;
	}

	/**
	 * @return
	 */
	public double getCompoundInterest()
	{
		return CompoundInterest;
	}

	/**
	 * @return
	 */
	public double getConsignLoanInterest()
	{
		return ConsignLoanInterest;
	}

	/**
	 * @return
	 */
	public double getForfeitInterest()
	{
		return ForfeitInterest;
	}

	/**
	 * @return
	 */
	public double getSelfLoanInterest()
	{
		return SelfLoanInterest;
	}

	/**
	 * @return
	 */
	public double getTotal()
	{
		return Total;
	}

	/**
	 * @return
	 */
	public double getTotalInterest()
	{
		return TotalInterest;
	}

	/**
	 * @param d
	 */
	public void setAssuranceCharge(double d)
	{
		AssuranceCharge = d;
	}

	/**
	 * @param d
	 */
	public void setBackInterest(double d)
	{
		BackInterest = d;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		Balance = d;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setClientNo(String string)
	{
		ClientNo = string;
	}

	/**
	 * @param d
	 */
	public void setCommission(double d)
	{
		Commission = d;
	}

	/**
	 * @param d
	 */
	public void setCompoundInterest(double d)
	{
		CompoundInterest = d;
	}

	/**
	 * @param d
	 */
	public void setConsignLoanInterest(double d)
	{
		ConsignLoanInterest = d;
	}

	/**
	 * @param d
	 */
	public void setForfeitInterest(double d)
	{
		ForfeitInterest = d;
	}

	/**
	 * @param d
	 */
	public void setSelfLoanInterest(double d)
	{
		SelfLoanInterest = d;
	}

	/**
	 * @param d
	 */
	public void setTotal(double d)
	{
		Total = d;
	}

	/**
	 * @param d
	 */
	public void setTotalInterest(double d)
	{
		TotalInterest = d;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @return
	 */
	public double getInterestTaxCharge()
	{
		return InterestTaxCharge;
	}

	/**
	 * @return
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTaxCharge(double d)
	{
		InterestTaxCharge = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTaxRate(double d)
	{
		InterestTaxRate = d;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * @return
	 */
	public long getDays()
	{
		return Days;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param l
	 */
	public void setDays(long l)
	{
		Days = l;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}

	/**
	 * @return
	 */
	public String getContractNo()
	{
		return ContractNo;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNo()
	{
		return FixedDepositNo;
	}

	/**
	 * @return
	 */
	public String getPayFormNo()
	{
		return PayFormNo;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string)
	{
		ContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		FixedDepositNo = string;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string)
	{
		PayFormNo = string;
	}

	/**
	 * @return
	 */
	public long getInterestType()
	{
		return InterestType;
	}

	/**
	 * @param l
	 */
	public void setInterestType(long l)
	{
		InterestType = l;
	}

	/**
	 * @return
	 */
	public double getInterestBalance()
	{
		return InterestBalance;
	}

	/**
	 * @param d
	 */
	public void setInterestBalance(double d)
	{
		InterestBalance = d;
	}

	/**
	 * @return
	 */
	public long getContractTerm()
	{
		return ContractTerm;
	}

	/**
	 * @return
	 */
	public String getLoanAccountNo()
	{
		return LoanAccountNo;
	}

	/**
	 * @return
	 */
	public double getLoanAmount()
	{
		return LoanAmount;
	}

	/**
	 * @param l
	 */
	public void setContractTerm(long l)
	{
		ContractTerm = l;
	}

	/**
	 * @param string
	 */
	public void setLoanAccountNo(String string)
	{
		LoanAccountNo = string;
	}

	/**
	 * @param d
	 */
	public void setLoanAmount(double d)
	{
		LoanAmount = d;
	}

	/**
	 * @return
	 */
	public Vector getPayInterestAccountID() {
		return PayInterestAccountID;
	}

	/**
	 * @param vector
	 */
	public void setPayInterestAccountID(Vector vector) {
		PayInterestAccountID = vector;
	}

	/**
	 * @return Returns the loanTypeID.
	 */
	public long getLoanTypeID() {
		return LoanTypeID;
	}
	/**
	 * @param loanTypeID The loanTypeID to set.
	 */
	public void setLoanTypeID(long loanTypeID) {
		LoanTypeID = loanTypeID;
	}
	
	/**
	 * @return Returns the payFormID.
	 */
	public long getPayFormID() {
		return PayFormID;
	}
	/**
	 * @param payFormID The payFormID to set.
	 */
	public void setPayFormID(long payFormID) {
		PayFormID = payFormID;
	}


	
	public long getInterestTaxRatePlan()
	{
	
		return InterestTaxRatePlan;
	}


	
	public void setInterestTaxRatePlan(long interestTaxRatePlan)
	{
	
		InterestTaxRatePlan = interestTaxRatePlan;
	}


	public String getStrDays() {
		return strDays;
	}


	public void setStrDays(String strDays) {
		this.strDays = strDays;
	}


	public String getStrInterest() {
		return strInterest;
	}


	public void setStrInterest(String strInterest) {
		this.strInterest = strInterest;
	}


	public String getStrRate() {
		return strRate;
	}


	public void setStrRate(String strRate) {
		this.strRate = strRate;
	}


	public String getConsignClientCode() {
		return ConsignClientCode;
	}


	public void setConsignClientCode(String consignClientCode) {
		ConsignClientCode = consignClientCode;
	}


	public String getConsignClientName() {
		return ConsignClientName;
	}


	public void setConsignClientName(String consignClientName) {
		ConsignClientName = consignClientName;
	}


	public long getNpayInterestAccountID() {
		return npayInterestAccountID;
	}


	public void setNpayInterestAccountID(long npayInterestAccountID) {
		this.npayInterestAccountID = npayInterestAccountID;
	}


	public long getNsubTypeID() {
		return nsubTypeID;
	}


	public void setNsubTypeID(long nsubTypeID) {
		this.nsubTypeID = nsubTypeID;
	}


	public String getNpayInterestAccountNO() {
		return npayInterestAccountNO;
	}


	public void setNpayInterestAccountNO(String npayInterestAccountNO) {
		this.npayInterestAccountNO = npayInterestAccountNO;
	}


	public double getNpayInterestAccountBalance() {
		return npayInterestAccountBalance;
	}


	public void setNpayInterestAccountBalance(double npayInterestAccountBalance) {
		this.npayInterestAccountBalance = npayInterestAccountBalance;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}


	public String getNsubTypeName() {
		return nsubTypeName;
	}


	public void setNsubTypeName(String nsubTypeName) {
		this.nsubTypeName = nsubTypeName;
	}
}
