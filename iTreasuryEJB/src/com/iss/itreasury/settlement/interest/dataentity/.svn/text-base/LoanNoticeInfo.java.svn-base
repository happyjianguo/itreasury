/*                                                                    
 * Created on 2003-10-23                                              
 *                                                                    
 * To change the template for this generated file go to               
 * Window - Preferences - Java - Code Generation - Code and Comments  
 */                                                                   
package com.iss.itreasury.settlement.interest.dataentity;             
                                                                      
import java.io.Serializable;                                          
import java.sql.Timestamp;                                            
                                                                      
/**                                                                   
 * @author xrli                                                    
 *                                                                    
 * To change the template for this generated type comment go to       
 * Window - Preferences - Java - Code Generation - Code and Comments  
 */                                                                   
public class LoanNoticeInfo implements Serializable    
{
	private long ID = -1;                                  //主键              
	private long OfficeID = -1;                            //机构ID            
	private long CurrencyID = -1;                          //币种ID            
	//private String TransNo = "";                         //交易号            
	//private long TransactionTypeID = -1;                 //交易类型          
	private long AccountID = -1;                           //主账户ID 
	private long AccountTypeID = -1;                       //账户类型
	private long SubAccountID = -1;                        //子账户ID 
	private String AccountNo = "";                         //账户号 
	private String ContractNo = "";                        //借款合同号
	private long ContractID = -1;                          //借款合同ID
	private String AssureContractNo = "";                  //担保合同号
	private long AssureContractID = -1;                    //担保合同ID
	private long LoanNoteID = -1;//放款通知单id
	private String PayFormNo = "";                         //放款通知单号
	private double PayFormAmount = 0.0;                    //放款通知单金额  
	private double PayFormBanlance = 0.0;                  //放款通知单余额
	private String BorrowClientName ="";                   //借款人名称
	private long BorrowClientID =-1;                       //借款人ID
	
	private String AssureClientName ="";                   //担保人名称        
	private long FormTypeID = -1;                          //通知书类型         
	private String FormYear = "";                          //通知书年度
	private String FormNo = "";                            //通知单号
	private long FormNum = -1;                           //催收次数
	private Timestamp ExecuteDate = null;	               //执行日
	private Timestamp ClearInterestDate = null;	           //结息日
	private double LoanAmount = 0.0;                       //贷款金额
	private Timestamp LoanStartDate = null;	               //贷款起起始日            
	private Timestamp LoanEndDate = null;	               //贷款结束日
	private long LoanTerm =-1;                             //贷款期限	
	private double  OriginalContractRate  = 0.0;           //原合同利率
	private double LoanBalance = 0.0;                      //贷款余额
	private double  ExecuteRate  = 0.0;                    //执行利率
	private double  NewExecuteRate  = 0.0;                 //新执行利率
	private Timestamp ExecuteRateValidDate = null;	       //执行利率调整日期
	private double Interest = 0.0;                         //利息值
	private double SuretyFee = 0.0;                        //担保费
	private double CompoundInterest = 0.0;                 //复利
	private double CommissionRate = 0.0;                   //手续费率
	private double Commission = 0.0;                       //手续费
	private double OverDueInterestRate = 0.0;              //逾期罚息率
	private double OverDueInterest = 0.0;                  //逾期罚息
	private double AllInterest = 0.0;                      //利息合计
	private double TotalInterest = 0.0;                    //本息合计
	private long StatusID = -1;                              //状态
	
	//后增加
	private Timestamp InterestStartDate = null;	               //起息日
	private Timestamp CommissionStartDate = null;	           //手续费起息日
	private Timestamp OverDueInterestStartDate = null;	       //罚息起息日
	private Timestamp SuretyFeeStartDate = null;	           //担保费起息日
	private Timestamp CompoundInterestStartDate = null;	       //复利起息日
	private long LoanTypeID =-1;                             //贷款类型	

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
	public double getAllInterest()
	{
		return AllInterest;
	}

	/**
	 * @return
	 */
	public String getAssureClientName()
	{
		return AssureClientName;
	}

	/**
	 * @return
	 */
	public String getAssureContractNo()
	{
		return AssureContractNo;
	}

	/**
	 * @return
	 */
	public String getBorrowClientName()
	{
		return BorrowClientName;
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
	public double getCommissionRate()
	{
		return CommissionRate;
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
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public double getExecuteRate()
	{
		return ExecuteRate;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteRateValidDate()
	{
		return ExecuteRateValidDate;
	}

	/**
	 * @return
	 */
	public String getFormNo()
	{
		return FormNo;
	}

	

	/**
	 * @return
	 */
	public long getFormTypeID()
	{
		return FormTypeID;
	}

	

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
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
	public double getLoanAmount()
	{
		return LoanAmount;
	}

	/**
	 * @return
	 */
	public double getLoanBalance()
	{
		return LoanBalance;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanEndDate()
	{
		return LoanEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanStartDate()
	{
		return LoanStartDate;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}

	/**
	 * @return
	 */
	public double getNewExecuteRate()
	{
		return NewExecuteRate;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public double getOriginalContractRate()
	{
		return OriginalContractRate;
	}

	/**
	 * @return
	 */
	public double getOverDueInterest()
	{
		return OverDueInterest;
	}

	/**
	 * @return
	 */
	public double getOverDueInterestRate()
	{
		return OverDueInterestRate;
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
	public long getStatusID()
	{
		return StatusID;
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
	public double getSuretyFee()
	{
		return SuretyFee;
	}

	/**
	 * @return
	 */
	public double getTotalInterest()
	{
		return TotalInterest;
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
	 * @param d
	 */
	public void setAllInterest(double d)
	{
		AllInterest = d;
	}

	/**
	 * @param string
	 */
	public void setAssureClientName(String string)
	{
		AssureClientName = string;
	}

	/**
	 * @param string
	 */
	public void setAssureContractNo(String string)
	{
		AssureContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientName(String string)
	{
		BorrowClientName = string;
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
	public void setCommissionRate(double d)
	{
		CommissionRate = d;
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
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setExecuteRate(double d)
	{
		ExecuteRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteRateValidDate(Timestamp timestamp)
	{
		ExecuteRateValidDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFormNo(String string)
	{
		FormNo = string;
	}

	

	/**
	 * @param l
	 */
	public void setFormTypeID(long l)
	{
		FormTypeID = l;
	}	

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
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
	public void setLoanAmount(double d)
	{
		LoanAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLoanBalance(double d)
	{
		LoanBalance = d;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanEndDate(Timestamp timestamp)
	{
		LoanEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanStartDate(Timestamp timestamp)
	{
		LoanStartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setLoanTerm(long l)
	{
		LoanTerm = l;
	}

	/**
	 * @param d
	 */
	public void setNewExecuteRate(double d)
	{
		NewExecuteRate = d;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param d
	 */
	public void setOriginalContractRate(double d)
	{
		OriginalContractRate = d;
	}

	/**
	 * @param d
	 */
	public void setOverDueInterest(double d)
	{
		OverDueInterest = d;
	}

	/**
	 * @param d
	 */
	public void setOverDueInterestRate(double d)
	{
		OverDueInterestRate = d;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string)
	{
		PayFormNo = string;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setSuretyFee(double d)
	{
		SuretyFee = d;
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
	public String getFormYear()
	{
		return FormYear;
	}

	/**
	 * @param string
	 */
	public void setFormYear(String string)
	{
		FormYear = string;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getAssureContractID()
	{
		return AssureContractID;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @param l
	 */
	public void setAssureContractID(long l)
	{
		AssureContractID = l;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @return
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		BorrowClientID = l;
	}

	/**
	 * @return
	 */
	public long getFormNum()
	{
		return FormNum;
	}

	/**
	 * @param l
	 */
	public void setFormNum(long l)
	{
		FormNum = l;
	}

	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
	}

	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID)
	{
		LoanNoteID = loanNoteID;
	}

	/**
	 * @return
	 */
	public Timestamp getCommissionStartDate() {
		return CommissionStartDate;
	}

	/**
	 * @return
	 */
	public Timestamp getCompoundInterestStartDate() {
		return CompoundInterestStartDate;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStartDate() {
		return InterestStartDate;
	}

	/**
	 * @return
	 */
	public Timestamp getOverDueInterestStartDate() {
		return OverDueInterestStartDate;
	}

	/**
	 * @return
	 */
	public Timestamp getSuretyFeeStartDate() {
		return SuretyFeeStartDate;
	}

	/**
	 * @param timestamp
	 */
	public void setCommissionStartDate(Timestamp timestamp) {
		CommissionStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setCompoundInterestStartDate(Timestamp timestamp) {
		CompoundInterestStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp) {
		InterestStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setOverDueInterestStartDate(Timestamp timestamp) {
		OverDueInterestStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setSuretyFeeStartDate(Timestamp timestamp) {
		SuretyFeeStartDate = timestamp;
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
	 * @return Returns the payFormAmount.
	 */
	public double getPayFormAmount() {
		return PayFormAmount;
	}
	/**
	 * @param payFormAmount The payFormAmount to set.
	 */
	public void setPayFormAmount(double payFormAmount) {
		PayFormAmount = payFormAmount;
	}
	/**
	 * @return Returns the payFormBanlance.
	 */
	public double getPayFormBanlance() {
		return PayFormBanlance;
	}
	/**
	 * @param payFormBanlance The payFormBanlance to set.
	 */
	public void setPayFormBanlance(double payFormBanlance) {
		PayFormBanlance = payFormBanlance;
	}
}                                           