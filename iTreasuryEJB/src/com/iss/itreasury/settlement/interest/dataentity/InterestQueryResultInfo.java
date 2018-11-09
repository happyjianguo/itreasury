/*
 * Created on 2003-10-28
 *
 * InterestQueryResultInfo.java
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;
/**
 * @author Allan Liu
 *
 * 利息费用结算页面查询结果信息实体。
 * 该类主要用来保存页面的查询结果，在页面上显示。
 * 该实体必须遵守JavaBean的规范，以便实现从JSP页面到实体的自动赋值的功能，
 * 简化应用程序的开发过程。
 * 该实体中的一些信息直接来自数据库，另外一些信息来自算息模块的计算。
 */
public class InterestQueryResultInfo implements Serializable{
	
    private String strAccountNo = "";                       //账户号
    private long AccountTypeID = -1;                       //账户类型 ID
    private long AccountID = -1;                           //账户 ID
	private long SubAccountID = -1;                        //子账户 ID
	private double LoanPreDrawInterest = 0.00;             //贷款计提利息    
    private String ContractNo = "";                      //合同号
    private String PayFormNo = "";                       //放款通知单号
    private long PayFormID = -1;                       //放款通知单号ID 
    private String FixedDepositNo = "";                  //定期单据号
    private Timestamp StartDate = null;                   //开始日期
    private Timestamp EndDate = null;                     //结束日期
    private long Days = -1;                                //天数
    private double Balance = 0.00;                         //余额
    private double InterestRate = 0.00;                    //利率
    private double Interest = 0.00;                        //利息
    private double CompoundInterest = 0.00;                //复利
    private double ForfeitInterest = 0.00;                 //罚息  
    private double DrawingInterest = 0.00;                 //计提利息
    private double HandlingCharge = 0.00;                  //手续费
    private double AssuranceCharge = 0.00;                 //担保费
    private double InterestTaxCharge = 0.00;               //利息税费
	private double InterestTaxRate = 0.00;               //利息税费利率
	private long   InterestTaxRatePlan = -1;               //利息税费利率计划
	private double NegotiateInterest = 0.00;               //协定利息
	private double NegotiateInterestRate = 0.00;               //协定利率
	private double NegotiateBalance = 0.00;               //协定存款计息金额
	private double NegotiatePecisionInterest = 0.0;        //精确的协定利息值  
	private double PecisionInterest = 0.0;                 //精确的利息值      
    private String Creator = "";                         //生成人
    private Timestamp CreateDate = null;                  //生成日期
    private String ExchangeNo = "";                      //交易号
    private boolean IsCanSelfLoanDrawing = false;            //自营贷款是否可以计提：对于定期存款账户无效
    private boolean IsDrawingResult = false;                 //是否计提成功标志：对定期存款账户无效（一定成功）
	private long InterestType = -1;                          //利息类型
	private long InterestID = -1;                          //结息表ID
	private double InterestBalance = 0.00;                 //计息金额
	private long PayInterestAccountID = -1;                        //付息账户号
	private long RecieveInterestAccountID = -1;                    //收息账户号  
	private double realInterest = 0;//实收利息自营贷款在增加部分结息后需要江实收利息与应收利息都保存下来贷款部分收息可使用
    	  
    private long contractID = -1;  //合同ID
    private long discreID = -1;  //贴现凭证ID
    
    private boolean isSuccess = false;
    private String  strPromptMessage="";//提示信息 add by kevin(刘连凯)2011-10-20
    
    
    public long getContractID() 
    {
		return contractID;
	}
    
	public void setContractID(long contractID) 
	{
		this.contractID = contractID;
	}
	
	public long getDiscreID() 
	{
		return discreID;
	}
	
	public void setDiscreID(long discreID) 
	{
		this.discreID = discreID;
	}
	/**
     * @return a string representing the current object using reflect api.
     */
    public String toString(){
        
        Field[] allFields = getClass().getDeclaredFields();
        StringBuffer buffer = new StringBuffer();
        
        try{
            AccessibleObject.setAccessible(allFields, true);
            buffer.append("InterestQueryResultInfo[");
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
    public String getAccountNo() {
        return this.strAccountNo;
    }

    /**
     * @param accountNo
     */
    public void setAccountNo(String accountNo) {
        this.strAccountNo = accountNo;
    }

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @return
	 */
	public String getStrAccountNo()
	{
		return strAccountNo;
	}	

	/**
	 * @param string
	 */
	public void setStrAccountNo(String string)
	{
		strAccountNo = string;
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
	public long getAccountTypeID()
	{
		return AccountTypeID;
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
	public double getBalance()
	{
		return Balance;
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
	public String getContractNo()
	{
		return ContractNo;
	}

	/**
	 * @return
	 */
	public Timestamp getCreateDate()
	{
		return CreateDate;
	}

	/**
	 * @return
	 */
	public String getCreator()
	{
		return Creator;
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
	public double getDrawingInterest()
	{
		return DrawingInterest;
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
	public String getExchangeNo()
	{
		return ExchangeNo;
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
	public double getForfeitInterest()
	{
		return ForfeitInterest;
	}

	/**
	 * @return
	 */
	public double getHandlingCharge()
	{
		return HandlingCharge;
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
	public double getInterestRate()
	{
		return InterestRate;
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
	public String getPayFormNo()
	{
		return PayFormNo;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
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
	public void setBalance(double d)
	{
		Balance = d;
	}

	/**
	 * @param d
	 */
	public void setCompoundInterest(double d)
	{
		CompoundInterest = d;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string)
	{
		ContractNo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setCreateDate(Timestamp timestamp)
	{
		CreateDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setCreator(String string)
	{
		Creator = string;
	}

	

	/**
	 * @param d
	 */
	public void setDrawingInterest(double d)
	{
		DrawingInterest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setExchangeNo(String string)
	{
		ExchangeNo = string;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		FixedDepositNo = string;
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
	public void setHandlingCharge(double d)
	{
		HandlingCharge = d;
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
	public void setInterestRate(double d)
	{
		InterestRate = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTaxCharge(double d)
	{
		InterestTaxCharge = d;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string)
	{
		PayFormNo = string;
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
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setDays(long l)
	{
		Days = l;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterest()
	{
		return NegotiateInterest;
	}

	/**
	 * @param d
	 */
	public void setNegotiateInterest(double d)
	{
		NegotiateInterest = d;
	}

	/**
	 * @return
	 */
	public double getNegotiateBalance()
	{
		return NegotiateBalance;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterestRate()
	{
		return NegotiateInterestRate;
	}

	/**
	 * @param d
	 */
	public void setNegotiateBalance(double d)
	{
		NegotiateBalance = d;
	}

	/**
	 * @param d
	 */
	public void setNegotiateInterestRate(double d)
	{
		NegotiateInterestRate = d;
	}

	/**
	 * @return
	 */
	public double getNegotiatePecisionInterest()
	{
		return NegotiatePecisionInterest;
	}

	/**
	 * @return
	 */
	public double getPecisionInterest()
	{
		return PecisionInterest;
	}

	/**
	 * @param d
	 */
	public void setNegotiatePecisionInterest(double d)
	{
		NegotiatePecisionInterest = d;
	}

	/**
	 * @param d
	 */
	public void setPecisionInterest(double d)
	{
		PecisionInterest = d;
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
	public void setInterestTaxRate(double d)
	{
		InterestTaxRate = d;
	}

	
	public long getInterestTaxRatePlan()
	{
	
		return InterestTaxRatePlan;
	}
	
	public void setInterestTaxRatePlan(long interestTaxRatePlan)
	{
	
		InterestTaxRatePlan = interestTaxRatePlan;
	}
	/**
	 * @return
	 */
	public boolean isDrawingResult()
	{
		return IsDrawingResult;
	}

	/**
	 * @param b
	 */
	public void setDrawingResult(boolean b)
	{
		IsDrawingResult = b;
	}

	/**
	 * @return
	 */
	public long getInterestID()
	{
		return InterestID;
	}

	/**
	 * @param l
	 */
	public void setInterestID(long l)
	{
		InterestID = l;
	}

	

	/**
	 * @return
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getRecieveInterestAccountID()
	{
		return RecieveInterestAccountID;
	}

	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l)
	{
		PayInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setRecieveInterestAccountID(long l)
	{
		RecieveInterestAccountID = l;
	}

	/**
	 * @return
	 */
	public double getLoanPreDrawInterest()
	{
		return LoanPreDrawInterest;
	}

	/**
	 * @param d
	 */
	public void setLoanPreDrawInterest(double d)
	{
		LoanPreDrawInterest = d;
	}

	/**
	 * @return
	 */
	public double getInterestBalance()
	{
		return InterestBalance;
	}

	/**
	 * @return
	 */
	public boolean isCanSelfLoanDrawing()
	{
		return IsCanSelfLoanDrawing;
	}

	/**
	 * @param d
	 */
	public void setInterestBalance(double d)
	{
		InterestBalance = d;
	}

	/**
	 * @param b
	 */
	public void setCanSelfLoanDrawing(boolean b)
	{
		IsCanSelfLoanDrawing = b;
	}

	public long getPayFormID() {
		return PayFormID;
	}

	public void setPayFormID(long payFormID) {
		PayFormID = payFormID;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public double getRealInterest() {
		return realInterest;
	}

	public void setRealInterest(double realInterest) {
		this.realInterest = realInterest;
	}

	public String getStrPromptMessage() {
		return strPromptMessage;
	}

	public void setStrPromptMessage(String strPromptMessage) {
		this.strPromptMessage = strPromptMessage;
	}

}
