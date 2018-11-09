/*
 * 创建日期 2003-10-6
 */
package com.iss.itreasury.settlement.transloan.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * 信托贷款和委托贷款收回的通用info类
 * @author yqwu
 */
public class TransRepaymentLoanInfo extends BaseDataEntity implements Serializable
{
	private long ID							= -1;
	private long OfficeID 					= -1;					
	private long CurrencyID 				= -1;
	private String TransNo 					= ""; 		//交易号
	private long TransactionTypeID 			= -1;
	private long FreeFormID 				= -1;		//提前还款通知单
	private long IsCancelLoan 				= -1;
	private long ClientID 					= -1;		//客户ID
	private long DepositAccountID 			= -1;		//付款方活期存款账户号
	private String ConsignPayVoucherNo 		= "";
	private String ConsignPayPWD 			= "";
	private String BillNo 					= "";		//票据号
	private long BillTypeID 				= -1;		//票据类型
	private long BillBankID 				= -1;		//发票银行
	private long BankID 					= -1;		//收款银行
	private String ExtBankNo 				= "";		//提出行
	private long CashFlowID 				= -1;
	private long LoanAccountID 				= -1;		//主账户ID
	private long LoanContractID 			= -1;		//合同号
	private long LoanNoteID 				= -1;		//放款通知单
	private long PreFormID 					= -1;		//提前还款通知单
	private double Amount 					= 0.0;
	private Timestamp InterestStart 		= null;
	private Timestamp Execute 				= null;
	private long AbstractID 				= -1;
	private String Abstract 				= "";
	private String CheckAbstract 			= "";
	private long ConsignAccountID 			= -1;		//委托存款账户号
	private long ConsignDepositAccountID 	= -1;		//委托方活期存款账户号
	private long PayInterestAccountID 		= -1;		//付息账户号
	private long InterestBankID 			= -1;		//付息银行
	private long InterestCashFlowID 		= -1;
	private long ReceiveInterestAccountID 	= -1;		//收息账户号
	private long PaySuretyAccountID 		= -1;		//负担保费账户号
	private long SuretyBankID 				= -1;		//负担保费银行
	private long SuretyCashFlowID 			= -1;
	private long ReceiveSuretyAccountID 	= -1;		//收担保费账户号
	private long CommissionBankID 			= -1;		//负担保费银行
	private long CommissionCashFlowID 		= -1;
	private long CommissionAccountID 		= -1;		//负担保费账户号
	private double Interest 				= 0.0;     //利息
	private double InterestReceiveAble 		= 0.0;
	private double InterestIncome 			= 0.0;
	private double InterestTax 				= 0.0;
	private double CompoundInterest 		= 0.0;
	private double OverDueInterest 			= 0.0;
	private double SuretyFee 				= 0.0;
	private double Commission 				= 0.0;
	private String AdjustInterestReason 	= "";		//调整原因
	private double AdjustInterest 			= 0;
	private double AheadRepayInterest 		= 0;
	private long IsRemitInterest 			= -1;
	private long IsRemitCompoundInterest 	= -1;
	private long IsRemitOverDueInterest 	= -1;
	private long IsRemitSuretyFee 			= -1;
	private long IsRemitCommission 			= -1;
	private long CapitalAndInterstDealway 	= -1;
	private double RealInterest 			= 0.0;
	private double RealInterestReceiveAble 	= 0.0;
	private double RealInterestIncome 		= 0.0;
	private double RealInterestTax 			= 0.0;
	private double RealCompoundInterest 	= 0.0;
	private double RealOverDueInterest 		= 0.0;
	private double RealSuretyFee 			= 0.0;
	private double RealCommission 			= 0.0;
	private Timestamp LatestInterestClear 	= null;		//上次结息日
	private Timestamp InterestClear 		= null;
	private long SubAccountID 				= -1;		//子账户ID
	private double CurrentBalance 			= 0.0;		//累计还款金额
	private Timestamp Input 				= null;
	private long InputUserID 				= -1;
	private long CheckUserID 				= -1; 
//	交易状态 0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	private long[] StatusID 				= null;
	private long AscOrDesc 					= -1;
	private long OrderByType 				= -1;
	/**
	* 修改时间
	*/
    private Timestamp Modify 				= null;
    
	/**多笔贷款收回　交易方向*/
	private long TransDirectionID 			= -1;
	/**多笔贷款收回　报单号*/
	private String DeclarationNo 			= null;
	/**多笔贷款收回　临时交易号*/
	private String TempTransNO 				= null;     
	

	
	/**
	 * 为打印添加的利息信息
	 */
	   
	private Timestamp CompoundInterestStart = null;		//复利起息日
	private double CompoundAmount 			= 0.0;		//复利本金
	private double CompoundRate 			= 0.0;		//复利利率
	
	private Timestamp OverDueStart 			= null;		//逾期罚息起息日
	private double OverDueAmount 			= 0.0;		//逾期罚息本金
	private double OverDueRate 				= 0.0;		//逾期罚息利率
	
	/**
	 * 为打印新添加信息2
	 */
	double LoanRepaymentRate 				= 0.0;		//还款利率
	Timestamp SuretyFeeStart 				= null;		//担保费起息日
	double SuretyFeeRate 					= 0.0;		//担保费率
	Timestamp CommissionStart 				= null;		//手续费起息日
	double CommissionRate 					= 0.0;		//手续费率
	
	/**
	 * 多笔贷款收回新添加参数
	 */
	long SerialNo 							= -1;		//序列号
	
	/**
	 * 网银加上的参数
	 */
	String InstructionNo					= "";		//
	
	//银团的成员行利息信息
	ArrayList SyndicationLoanInterest =null;
	
	 public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the subAccountID.
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param subAccountID The subAccountID to set.
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
	}

	/**
	 * @return Returns the interestClear.
	 */
	public Timestamp getInterestClear()
	{
		return InterestClear;
	}

	/**
	 * @param interestClear The interestClear to set.
	 */
	public void setInterestClear(Timestamp interestClear)
	{
		InterestClear = interestClear;
	}

	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * @return
	 */
	public long getAbstractID()
	{
		return AbstractID;
	}

	/**
	 * @return
	 */
	public String getAdjustInterestReason()
	{
		return AdjustInterestReason;
	}

	/**
	 * @return
	 */
	public double getAheadRepayInterest()
	{
		return AheadRepayInterest;
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
	public long getBillBankID()
	{
		return BillBankID;
	}

	/**
	 * @return
	 */
	public long getBillTypeID()
	{
		return BillTypeID;
	}

	/**
	 * @return
	 */
	public long getCapitalAndInterstDealway()
	{
		return CapitalAndInterstDealway;
	}

	/**
	 * @return
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}

	/**
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public long getCommissionAccountID()
	{
		return CommissionAccountID;
	}

	/**
	 * @return
	 */
	public long getCommissionBankID()
	{
		return CommissionBankID;
	}

	/**
	 * @return
	 */
	public long getCommissionCashFlowID()
	{
		return CommissionCashFlowID;
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
	public long getConsignAccountID()
	{
		return ConsignAccountID;
	}

	/**
	 * @return
	 */
	public long getConsignDepositAccountID()
	{
		return ConsignDepositAccountID;
	}

	/**
	 * @return
	 */
	public String getConsignPayPWD()
	{
		return ConsignPayPWD;
	}

	/**
	 * @return
	 */
	public String getConsignPayVoucherNo()
	{
		return ConsignPayVoucherNo;
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
	public long getDepositAccountID()
	{
		return DepositAccountID;
	}

	/**
	 * @return
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}

	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return Execute;
	}

	/**
	 * @return
	 */
	public long getFreeFormID()
	{
		return FreeFormID;
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
	public Timestamp getInput()
	{
		return Input;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
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
	public long getInterestBankID()
	{
		return InterestBankID;
	}

	/**
	 * @return
	 */
	public long getInterestCashFlowID()
	{
		return InterestCashFlowID;
	}

	/**
	 * @return
	 */
	public double getInterestIncome()
	{
		return InterestIncome;
	}

	/**
	 * @return
	 */
	public double getInterestReceiveAble()
	{
		return InterestReceiveAble;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStart()
	{
		return InterestStart;
	}

	/**
	 * @return
	 */
	public double getInterestTax()
	{
		return InterestTax;
	}

	/**
	 * @return
	 */
	public long getIsCancelLoan()
	{
		return IsCancelLoan;
	}

	/**
	 * @return
	 */
	public long getIsRemitCommission()
	{
		return IsRemitCommission;
	}

	/**
	 * @return
	 */
	public long getIsRemitCompoundInterest()
	{
		return IsRemitCompoundInterest;
	}

	/**
	 * @return
	 */
	public long getIsRemitInterest()
	{
		return IsRemitInterest;
	}

	/**
	 * @return
	 */
	public long getIsRemitOverDueInterest()
	{
		return IsRemitOverDueInterest;
	}

	/**
	 * @return
	 */
	public long getIsRemitSuretyFee()
	{
		return IsRemitSuretyFee;
	}

	/**
	 * @return
	 */
	public long getLoanAccountID()
	{
		return LoanAccountID;
	}

	/**
	 * @return
	 */
	public long getLoanContractID()
	{
		return LoanContractID;
	}

	/**
	 * @return
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
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
	public double getOverDueInterest()
	{
		return OverDueInterest;
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
	public long getPaySuretyAccountID()
	{
		return PaySuretyAccountID;
	}

	/**
	 * @return
	 */
	public double getRealCommission()
	{
		return RealCommission;
	}

	/**
	 * @return
	 */
	public double getRealCompoundInterest()
	{
		return RealCompoundInterest;
	}

	/**
	 * @return
	 */
	public double getRealInterest()
	{
		return RealInterest;
	}

	/**
	 * @return
	 */
	public double getRealInterestIncome()
	{
		return RealInterestIncome;
	}

	/**
	 * @return
	 */
	public double getRealInterestReceiveAble()
	{
		return RealInterestReceiveAble;
	}

	/**
	 * @return
	 */
	public double getRealInterestTax()
	{
		return RealInterestTax;
	}

	/**
	 * @return
	 */
	public double getRealOverDueInterest()
	{
		return RealOverDueInterest;
	}

	/**
	 * @return
	 */
	public double getRealSuretyFee()
	{
		return RealSuretyFee;
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}

	/**
		 * Returns the statusID.
		 * @return long
		 */
	public long getStatusID()
	{
		long lResult = -1;
		if (this.StatusID != null && this.StatusID.length > 0)
		{
			lResult = this.StatusID[0];
		}
		return lResult;
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return this.StatusID;
	}

	 

	/**
	 * @return
	 */
	public long getSuretyBankID()
	{
		return SuretyBankID;
	}

	/**
	 * @return
	 */
	public long getSuretyCashFlowID()
	{
		return SuretyCashFlowID;
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
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}

	/**
	 * @param l
	 */
	public void setAbstractID(long l)
	{
		AbstractID = l;
	}

	/**
	 * @param string
	 */
	public void setAdjustInterestReason(String string)
	{
		AdjustInterestReason = string;
	}

	/**
	 * @param d
	 */
	public void setAheadRepayInterest(double d)
	{
		AheadRepayInterest = d;
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
	public void setBillBankID(long l)
	{
		BillBankID = l;
	}

	/**
	 * @param l
	 */
	public void setBillTypeID(long l)
	{
		BillTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setCapitalAndInterstDealway(long l)
	{
		CapitalAndInterstDealway = l;
	}

	/**
	 * @param l
	 */
	public void setCashFlowID(long l)
	{
		CashFlowID = l;
	}

	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCommissionAccountID(long l)
	{
		CommissionAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setCommissionBankID(long l)
	{
		CommissionBankID = l;
	}

	/**
	 * @param l
	 */
	public void setCommissionCashFlowID(long l)
	{
		CommissionCashFlowID = l;
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
	 * @param l
	 */
	public void setConsignAccountID(long l)
	{
		ConsignAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setConsignDepositAccountID(long l)
	{
		ConsignDepositAccountID = l;
	}

	/**
	 * @param string
	 */
	public void setConsignPayPWD(String string)
	{
		ConsignPayPWD = string;
	}

	/**
	 * @param string
	 */
	public void setConsignPayVoucherNo(String string)
	{
		ConsignPayVoucherNo = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDepositAccountID(long l)
	{
		DepositAccountID = l;
	}

	/**
	 * @param string
	 */
	public void setExtBankNo(String string)
	{
		ExtBankNo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setExecute(Timestamp timestamp)
	{
		Execute = timestamp;
	}

	/**
	 * @param l
	 */
	public void setFreeFormID(long l)
	{
		FreeFormID = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInput(Timestamp timestamp)
	{
		Input = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * @param l
	 */
	public void setInterestBankID(long l)
	{
		InterestBankID = l;
	}

	/**
	 * @param l
	 */
	public void setInterestCashFlowID(long l)
	{
		InterestCashFlowID = l;
	}

	/**
	 * @param d
	 */
	public void setInterestIncome(double d)
	{
		InterestIncome = d;
	}

	/**
	 * @param d
	 */
	public void setInterestReceiveAble(double d)
	{
		InterestReceiveAble = d;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp)
	{
		InterestStart = timestamp;
	}

	/**
	 * @param d
	 */
	public void setInterestTax(double d)
	{
		InterestTax = d;
	}

	/**
	 * @param l
	 */
	public void setIsCancelLoan(long l)
	{
		IsCancelLoan = l;
	}

	/**
	 * @param l
	 */
	public void setIsRemitCommission(long l)
	{
		IsRemitCommission = l;
	}

	/**
	 * @param l
	 */
	public void setIsRemitCompoundInterest(long l)
	{
		IsRemitCompoundInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsRemitInterest(long l)
	{
		IsRemitInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsRemitOverDueInterest(long l)
	{
		IsRemitOverDueInterest = l;
	}

	/**
	 * @param l
	 */
	public void setIsRemitSuretyFee(long l)
	{
		IsRemitSuretyFee = l;
	}

	/**
	 * @param l
	 */
	public void setLoanAccountID(long l)
	{
		LoanAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanContractID(long l)
	{
		LoanContractID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanNoteID(long l)
	{
		LoanNoteID = l;
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
	public void setOverDueInterest(double d)
	{
		OverDueInterest = d;
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
	public void setPaySuretyAccountID(long l)
	{
		PaySuretyAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setRealCommission(double d)
	{
		RealCommission = d;
	}

	/**
	 * @param d
	 */
	public void setRealCompoundInterest(double d)
	{
		RealCompoundInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealInterest(double d)
	{
		RealInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealInterestIncome(double d)
	{
		RealInterestIncome = d;
	}

	/**
	 * @param d
	 */
	public void setRealInterestReceiveAble(double d)
	{
		RealInterestReceiveAble = d;
	}

	/**
	 * @param d
	 */
	public void setRealInterestTax(double d)
	{
		RealInterestTax = d;
	}

	/**
	 * @param d
	 */
	public void setRealOverDueInterest(double d)
	{
		RealOverDueInterest = d;
	}

	/**
	 * @param d
	 */
	public void setRealSuretyFee(double d)
	{
		RealSuretyFee = d;
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{
		ReceiveInterestAccountID = l;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.StatusID = new long[] { statusID };
	}
	/**
	 * 用于当前Entity作为查询条件时
	 * @param statusIDs
	 */
	public void setStatusID(long[] statusIDs)
	{
		this.StatusID = statusIDs;
	}

	 

	/**
	 * @param l
	 */
	public void setSuretyBankID(long l)
	{
		SuretyBankID = l;
	}

	/**
	 * @param l
	 */
	public void setSuretyCashFlowID(long l)
	{
		SuretyCashFlowID = l;
	}

	/**
	 * @param d
	 */
	public void setSuretyFee(double d)
	{
		SuretyFee = d;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	/**
	 * @return
	 */
	public Timestamp getModify()
	{
		return Modify;
	}

	/**
	 * @param timestamp
	 */
	public void setModify(Timestamp timestamp)
	{
		Modify = timestamp;
	}

	/**
	 * @return
	 */
	public long getAscOrDesc()
	{
		return AscOrDesc;
	}

	/**
	 * @return
	 */
	public long getOrderByType()
	{
		return OrderByType;
	}

	/**
	 * @param l
	 */
	public void setAscOrDesc(long l)
	{
		AscOrDesc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderByType(long l)
	{
		OrderByType = l;
	}

	/**
	 * @return
	 */
	public String getBillNo()
	{
		return BillNo;
	}

	/**
	 * @return
	 */
	public long getPreFormID()
	{
		return PreFormID;
	}

	/**
	 * @param string
	 */
	public void setBillNo(String string)
	{
		BillNo = string;
	}

	/**
	 * @param l
	 */
	public void setPreFormID(long l)
	{
		PreFormID = l;
	}

	/**
	 * @return
	 */
	public long getBankID()
	{
		return BankID;
	}

	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}

	/**
	 * @return
	 */
	public long getReceiveSuretyAccountID()
	{
		return ReceiveSuretyAccountID;
	}

	/**
	 * @param l
	 */
	public void setReceiveSuretyAccountID(long l)
	{
		ReceiveSuretyAccountID = l;
	}

	/**
	 * @return
	 */
	public double getAdjustInterest()
	{
		return AdjustInterest;
	}

	/**
	 * @param d
	 */
	public void setAdjustInterest(double d)
	{
		AdjustInterest = d;
	}

	/**
	 * @return Returns the currentBalance.
	 */
	public double getCurrentBalance()
	{
		return CurrentBalance;
	}

	/**
	 * @param currentBalance The currentBalance to set.
	 */
	public void setCurrentBalance(double currentBalance)
	{
		CurrentBalance = currentBalance;
	}

	/**
	 * @return Returns the latestInterestClear.
	 */
	public Timestamp getLatestInterestClear()
	{
		return LatestInterestClear;
	}

	/**
	 * @param latestInterestClear The latestInterestClear to set.
	 */
	public void setLatestInterestClear(Timestamp latestInterestClear)
	{
		LatestInterestClear = latestInterestClear;
	}

	/**
	 * Returns the declarationNo.
	 * @return String
	 */
	public String getDeclarationNo() {
		return DeclarationNo;
	}

	/**
	 * Returns the tempTransNO.
	 * @return String
	 */
	public String getTempTransNO() {
		return TempTransNO;
	}

	/**
	 * Returns the transDirectionID.
	 * @return long
	 */
	public long getTransDirectionID() {
		return TransDirectionID;
	}

	/**
	 * Sets the declarationNo.
	 * @param declarationNo The declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo) {
		DeclarationNo = declarationNo;
	}

	/**
	 * Sets the tempTransNO.
	 * @param tempTransNO The tempTransNO to set
	 */
	public void setTempTransNO(String tempTransNO) {
		TempTransNO = tempTransNO;
	}

	/**
	 * Sets the transDirectionID.
	 * @param transDirectionID The transDirectionID to set
	 */
	public void setTransDirectionID(long transDirectionID) {
		TransDirectionID = transDirectionID;
	}

	/**
	 * @return Returns the compoundAmount.
	 */
	public double getCompoundAmount()
	{
		return CompoundAmount;
	}

	/**
	 * @param compoundAmount The compoundAmount to set.
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		CompoundAmount = compoundAmount;
	}

	/**
	 * @return Returns the compoundInterestStart.
	 */
	public Timestamp getCompoundInterestStart()
	{
		return CompoundInterestStart;
	}

	/**
	 * @param compoundInterestStart The compoundInterestStart to set.
	 */
	public void setCompoundInterestStart(Timestamp compoundInterestStart)
	{
		CompoundInterestStart = compoundInterestStart;
	}

	/**
	 * @return Returns the compoundRate.
	 */
	public double getCompoundRate()
	{
		return CompoundRate;
	}

	/**
	 * @param compoundRate The compoundRate to set.
	 */
	public void setCompoundRate(double compoundRate)
	{
		CompoundRate = compoundRate;
	}

	/**
	 * @return Returns the overDueAmount.
	 */
	public double getOverDueAmount()
	{
		return OverDueAmount;
	}

	/**
	 * @param overDueAmount The overDueAmount to set.
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		OverDueAmount = overDueAmount;
	}

	/**
	 * @return Returns the overDueRate.
	 */
	public double getOverDueRate()
	{
		return OverDueRate;
	}

	/**
	 * @param overDueRate The overDueRate to set.
	 */
	public void setOverDueRate(double overDueRate)
	{
		OverDueRate = overDueRate;
	}

	/**
	 * @return Returns the overDueStart.
	 */
	public Timestamp getOverDueStart()
	{
		return OverDueStart;
	}

	/**
	 * @param overDueStart The overDueStart to set.
	 */
	public void setOverDueStart(Timestamp overDueStart)
	{
		OverDueStart = overDueStart;
	}

	/**
	 * @return Returns the commissionRate.
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}

	/**
	 * @param commissionRate The commissionRate to set.
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}

	/**
	 * @return Returns the commissionStart.
	 */
	public Timestamp getCommissionStart()
	{
		return CommissionStart;
	}

	/**
	 * @param commissionStart The commissionStart to set.
	 */
	public void setCommissionStart(Timestamp commissionStart)
	{
		CommissionStart = commissionStart;
	}

	/**
	 * @return Returns the loanRepaymentRate.
	 */
	public double getLoanRepaymentRate()
	{
		return LoanRepaymentRate;
	}

	/**
	 * @param loanRepaymentRate The loanRepaymentRate to set.
	 */
	public void setLoanRepaymentRate(double loanRepaymentRate)
	{
		LoanRepaymentRate = loanRepaymentRate;
	}

	/**
	 * @return Returns the suretyFeeRate.
	 */
	public double getSuretyFeeRate()
	{
		return SuretyFeeRate;
	}

	/**
	 * @param suretyFeeRate The suretyFeeRate to set.
	 */
	public void setSuretyFeeRate(double suretyFeeRate)
	{
		SuretyFeeRate = suretyFeeRate;
	}

	/**
	 * @return Returns the suretyFeeStart.
	 */
	public Timestamp getSuretyFeeStart()
	{
		return SuretyFeeStart;
	}

	/**
	 * @param suretyFeeStart The suretyFeeStart to set.
	 */
	public void setSuretyFeeStart(Timestamp suretyFeeStart)
	{
		SuretyFeeStart = suretyFeeStart;
	}

	/**
	 * @return Returns the serialNo.
	 */
	public long getSerialNo()
	{
		return SerialNo;
	}

	/**
	 * @param serialNo The serialNo to set.
	 */
	public void setSerialNo(long serialNo)
	{
		SerialNo = serialNo;
	}

	/**
	 * @return Returns the instructionNo.
	 */
	public String getInstructionNo()
	{
		return InstructionNo;
	}

	/**
	 * @param instructionNo The instructionNo to set.
	 */
	public void setInstructionNo(String instructionNo)
	{
		InstructionNo = instructionNo;
	}

	/**
	 * @return
	 */
	public ArrayList getSyndicationLoanInterest() {
		return SyndicationLoanInterest;
	}

	/**
	 * @param list
	 */
	public void setSyndicationLoanInterest(ArrayList list) {
		SyndicationLoanInterest = list;
	}


}
