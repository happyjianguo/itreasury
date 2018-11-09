package com.iss.itreasury.settlement.transloan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.DataFormat;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-18
 */
public class ContractFormInfo implements Serializable {
	//合同信息
	//ID:合同标识
	private long ContractID;

	//ID:合同标识
	private long LoanID;

	//nCurrencyID:币种标识
	private long CurrencyID;

	//nOfficeID:办事处标识
	private long OfficeID;

	//sContractCode:合同编号
	private String ContractCode;

	//nLoanTypeID:贷款种类
	private long LoanTypeID;
	private String LoanTypeName;

	//nBorrowClientID:贷款单位ID
	private long BorrowClientID;

	//sName:贷款单位名称
	private String BorrowClientName;

	//sName:贷款单位编号
	private String BorrowClientCode;

	//nClientID 委托单位ID
	private long ClientID;

	//sName:委托单位名称
	private String ClientName;

	//委托单位借款单位账户
	private String ClientAccount;

	//借款单位账户
	private String LoanAccount;

	//mLoanAmount 金额
	private double LoanAmount;

	//ExamineAmount 批准金额
	private double ExamineAmount;

	//汇总贴现核定金额
	private double CheckAmount;

	//贴现利息
	private double DiscountInterest;

	//AssureAmount 担保金额
	private double AssureAmount;

	//AssureRate 担保金额之和占审批金额的比例
	private double AssureRate;

	//CreditRate信用贷款金额占审批金额的比例
	private double CreditRate;

	//信用贷款总额
	private double CreditAmount;

	//已发放金额
	private double AmountDone;

	//fInterestRate:利率
	private double InterestRate;

	//fInterestRate:银行利率ID
	private long BankInterestID;

	//nIntervalNum:贷款期限
	private long IntervalNum;

	//nInputUserID:录入人标识 亦是合同管理人
	private long lInputUserID;

	//sInputUserName 录入人姓名
	private String InputUserName;
	private Timestamp InputDate; //合同录入日期

	//DiscountRate 贴现利率
	private double DiscountRate;

	//nStatusID:合同状态
	private long StatusID;
	private String Status;

	//分页显示总页数
	private long PageCount;

	// 申请编号
	private String ApplyCode;

	//总纪录数,仅在贷款申请查询时用到
	private long AllRecord;

	//总金额
	private double AllAmount;

	//贷款余额
	private double Balance;

	//dtLoanStart借款起始日期
	private Timestamp LoanStart;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;

	//借款用途
	private String LoanPurpose;

	//借款原因
	private String LoanReason;

	//放款日期
	private Timestamp OutDate;

	//借款单位邮编
	private String LoanZipCode;

	//借款单位电话
	private String LoanPhone;

	//借款单位地址
	private String LoanAddress;

	//合同执行计划是否可以修改
	private long IsPlanModifying = -1;

	//合同执行计划版本号ID
	private long PlanVersionID = -1;

	//是否有计划在展期之中
	private long IsOverDueModifying = -1;

	//利率值
	private double Rate = -1;

	//浮动利率正负标识（正1负2）
	private long AddOrDecrease = 1;

	//浮动利率
	private double AdjustRate = 0;

	//审核人ID
	private long CheckUserID = -1;

	//审核人名称
	private String CheckUserName = "";

	//按地区分类
	private long AreaType = -1;

	//按行业分类1
	private long IndustryType1 = -1;

	//按行业分类2
	private long IndustryType2 = -1;

	//保证信息
	private Collection cAssure = null;

	//合同文本信息
	private Collection cContractContent = null;

	//银团信息
	private Collection cYT = null;

	//银团信息--在结算中暂时不使用 by Huang Ye
	//private YTFormatInfo YTInfo = null;

	//合同计划修改日期
	private Timestamp PlanModifyDate = null;

	//还款来源
	private String sOther = "";

	//手续费率
	private double ChargeRate = 0;

	//手续费率类型
	private long ChargeRateType = -1;
	
	//转贴现类型
	private long DiscountTypeID = -1;
	
	//转贴现买入卖出类型
	private long ReDiscountInOrOut = -1;

	/**
	 * function 得到合同标识
	 * return ContractID
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @param lID
	 * function 设置合同标识
	 * return void
	 */
	public void setContractID(long lID)
	{
		this.ContractID = lID;
	}

	/**
	 * function 得到合同编号
	 * return ContractCode
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @param ContractCode
	 * function 设置合同编号
	 * return void
	 */
	public void setContractCode(String ContractCode)
	{
		this.ContractCode = ContractCode;
	}

	/**
	 * function 得到贷款单位名称
	 * return BorrowClientName
	 */
	public String getBorrowClientName()
	{
		return BorrowClientName;
	}

	/**
	 * @param BorrowClientName
	 * function 设置贷款单位名称
	 * return void
	 */
	public void setBorrowClientName(String BorrowClientName)
	{
		this.BorrowClientName = BorrowClientName;
	}

	/**
	 * function 得到贷款单位编号
	 * return BorrowClientCode
	 */
	public String getBorrowClientCode()
	{
		return BorrowClientCode;
	}

	/**
	 * @param BorrowClientCode
	 * function 设置贷款单位编号
	 * return void
	 */
	public void setBorrowClientCode(String BorrowClientCode)
	{
		this.BorrowClientCode = BorrowClientCode;
	}

	/**
	 * function 得到委托单位名称
	 * return ClientName
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param ClientName
	 * function 设置委托单位名称
	 * return ClientName
	 */
	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	/**
	 * function 得到贷款金额
	 * return lLoanAmount
	 */
	public double getLoanAmount()
	{
		return LoanAmount;
	}

	/**
	* function 得到格式化后的贷款金额
	* return lLoanAmount
	*/
	public String getFormatLoanAmount()
	{
		return DataFormat.formatDisabledAmount(LoanAmount);
	}

	/**
	 * @param dLoanAmount
	 * function 设置贷款金额
	 * return void
	 */
	public void setLoanAmount(double dLoanAmount)
	{
		this.LoanAmount = dLoanAmount;
	}

	/**
	 * function 得到利率
	 * return dInterestRate
	 */
	public double getInterestRate()
	{
		return InterestRate;
	}

	/**
	* function 得到利率
	* return dInterestRate
	*/
	public String getFormatInterestRate()
	{
		return DataFormat.formatRate(InterestRate);
	}

	/**
	 * @param dInterestRate
	 * function 设置利率
	 * return void
	 */
	public void setInterestRate(double dInterestRate)
	{
		this.InterestRate = dInterestRate;
	}

	/**
	 * function 得到贷款期限
	 * return lIntervalNum
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}

	/**
	 * @param IntervalNum
	 * function 设置贷款期限
	 * return void
	 */
	public void setIntervalNum(long IntervalNum)
	{
		this.IntervalNum = IntervalNum;
	}

	/**
	 * function 得到合同状态
	 * return lStatusID
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param lStatusID
	 * function 设置合同状态
	 * return void
	 */
	public void setStatusID(long lStatusID)
	{
		this.StatusID = lStatusID;
	}

	/**
	 * function 得到合同管理人
	 * return InputUserName
	 */
	public String getInputUserName()
	{
		if (InputUserName != null)
		{
			return InputUserName;
		}
		else
		{
			return "";
		}
	}

	/**
	 * @param InputUserName
	 * function 设置合同管理人
	 * return void
	 */
	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
	}

	/**
	 * function 得到贷款类型
	 * return lTypeID
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param lTypeID
	 * function 设置贷款类型
	 * return void
	 */
	public void setLoanTypeID(long lTypeID)
	{
		this.LoanTypeID = lTypeID;
	}

	/**
	 * function 得到贷款单位ID
	 * return lBorrowClientID
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}

	/**
	 * @param lBorrowClientID
	 * function 设置贷款单位ID
	 * return void
	 */
	public void setBorrowClientID(long lBorrowClientID)
	{
		this.BorrowClientID = lBorrowClientID;
	}

	/**
	 * function 得到委托单位ID
	 * return lClientID
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param lClientID
	 * function 设置委托单位ID
	 * return void
	 */
	public void setClientID(long lClientID)
	{
		this.ClientID = lClientID;
	}

	/**
	 * function 得到状态描述
	 * return Status
	 */
	public String getStatus()
	{
		return Status;
	}

	/**
	 * @param Status
	 * function 设置状态描述
	 * return void
	 */
	public void setStatus(String Status)
	{
		this.Status = Status;
	}

	/**
	 * function 得到贷款类型描述
	 * return LoanTypeName
	 */
	public String getLoanTypeName()
	{
		return LoanTypeName;
	}

	/**
	 * @param LoanTypeName
	 * function 设置贷款类型描述
	 * return void
	 */
	public void setLoanTypeName(String LoanTypeName)
	{
		this.LoanTypeName = LoanTypeName;
	}

	/**
	 * function 得到总金额
	 * return double
	 */
	public double getAllAmount()
	{
		return AllAmount;
	}

	/**
	 * @param dAllAmount
	 * function 设置总金额
	 * return void
	 */
	public void setAllAmount(double AllAmount)
	{
		this.AllAmount = AllAmount;
	}

	/**
	 * function 得到申请书编号
	 * return ApplyCode
	 */
	public String getApplyCode()
	{
		return ApplyCode;
	}

	/**
	 * @param ApplyCode
	 * function 设置申请书编号
	 * return void
	 */
	public void setApplyCode(String ApplyCode)
	{
		this.ApplyCode = ApplyCode;
	}

	public double getBalance()
	{
		return Balance;
	}

	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public Timestamp getLoanStart()
	{
		return LoanStart;
	}

	public String getFormatLoanStart()
	{
		return DataFormat.getDateString(LoanStart);
	}

	public void setLoanStart(Timestamp LoanStart)
	{
		this.LoanStart = LoanStart;
	}

	public Timestamp getLoanEnd()
	{
		return LoanEnd;
	}

	public String getFormatLoanEnd()
	{
		return DataFormat.getDateString(LoanEnd);
	}

	public void setLoanEnd(Timestamp LoanEnd)
	{
		this.LoanEnd = LoanEnd;
	}

	/**
	 * function 得到分页显示总页数
	 * return PageCount
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param lPageCount
	 * function 设置分页显示总页数
	 * return void
	 */
	public void setPageCount(long lPageCount)
	{
		this.PageCount = lPageCount;
	}

	/**
	 * function 得到总纪录数
	 * return AllRecord
	 */
	public long getAllRecord()
	{
		return AllRecord;
	}

	/**
	 * @param lAllRecord
	 * function 设置总纪录数
	 * return void
	 */
	public void setAllRecord(long lAllRecord)
	{
		this.AllRecord = lAllRecord;
	}

	public double getAmountDone()
	{
		return AmountDone;
	}

	public void setAmountDone(double AmountDone)
	{
		this.AmountDone = AmountDone;
	}

	public String getLoanPurpose()
	{
		return LoanPurpose;
	}

	public void setLoanPurpose(String LoanPurpose)
	{
		this.LoanPurpose = LoanPurpose;
	}

	public Timestamp getOutDate()
	{
		return OutDate;
	}

	public void setOutDate(Timestamp OutDate)
	{
		this.OutDate = OutDate;
	}

	public String getLoanAccount()
	{
		return LoanAccount;
	}

	public void setLoanAccount(String LoanAccount)
	{
		this.LoanAccount = LoanAccount;
	}

	public String getLoanAddress()
	{
		return LoanAddress;
	}

	public void setLoanAddress(String LoanAddress)
	{
		this.LoanAddress = LoanAddress;
	}

	public String getLoanPhone()
	{
		return LoanPhone;
	}

	public void setLoanPhone(String LoanPhone)
	{
		this.LoanPhone = LoanPhone;
	}

	public String getLoanZipCode()
	{
		return LoanZipCode;
	}

	public void setLoanZipCode(String LoanZipCode)
	{
		this.LoanZipCode = LoanZipCode;
	}

	public String getClientAccount()
	{
		return ClientAccount;
	}

	public void setClientAccount(String ClientAccount)
	{
		this.ClientAccount = ClientAccount;
	}

	/**
	 * function 得到合同执行计划是否在修改
	 * return IsPlanModifying
	 */
	public long getIsPlanModifying()
	{
		return IsPlanModifying;
	}

	/**
	 * @param IsPlanModifying
	 * function设置合同执行计划是否在修改
	 * return void
	 */
	public void setIsPlanModifying(long lIsPlanModifying)
	{
		this.IsPlanModifying = lIsPlanModifying;
	}

	/**
	 * function 得到合同执行计划版本号ID
	 * return PlanVersionID
	 */
	public long getPlanVersionID()
	{
		return PlanVersionID;
	}

	/**
	 * @param lPlanVersionID
	 * function 设置合同执行计划版本号ID
	 * return void
	 */
	public void setPlanVersionID(long lPlanVersionID)
	{
		this.PlanVersionID = lPlanVersionID;
	}

	/**
	 * function 得到是否有计划在展期之中
	 * return IsOverDueModifying
	 */
	public long getIsOverDueModifying()
	{
		return IsOverDueModifying;
	}

	/**
	 * @param lIsOverDueModifying
	 * function 设置是否有计划在展期之中
	 * return void
	 */
	public void setIsOverDueModifying(long lIsOverDueModifying)
	{
		this.IsOverDueModifying = lIsOverDueModifying;
	}

	public double getRate()
	{
		return Rate;
	}

	public void setRate(double Rate)
	{
		this.Rate = Rate;
	}

	public double getAdjustRate()
	{
		return AdjustRate;
	}

	public void setAdjustRate(double AdjustRate)
	{
		this.AdjustRate = AdjustRate;
	}

	public long getAddOrDecrease()
	{
		return AddOrDecrease;
	}

	public void setAddOrDecrease(long AddOrDecrease)
	{
		this.AddOrDecrease = AddOrDecrease;
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
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.getDateString(InputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
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
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		LoanID = l;
	}

	/**
	 * @return
	 */
	public double getExamineAmount()
	{
		return ExamineAmount;
	}

	/**
	* @return
	*/
	public String getFormatExamineAmount()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount);
	}

	/**
	 * @param d
	 */
	public void setExamineAmount(double d)
	{
		ExamineAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureAmount()
	{
		return AssureAmount;
	}

	/**
	* @return
	*/
	public String getFormatAssureAmount()
	{
		return DataFormat.formatDisabledAmount(AssureAmount);
	}

	/**
	 * @param d
	 */
	public void setAssureAmount(double d)
	{
		AssureAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureRate()
	{
		return AssureRate;
	}

	/**
	* @return
	*/
	public String getFormatAssureRate()
	{
		return DataFormat.formatDisabledAmount(AssureRate * 100);
	}

	/**
	 * @param d
	 */
	public void setAssureRate(double d)
	{
		AssureRate = d;
	}

	/**
	 * @return
	 */
	public double getCreditAmount()
	{
		return CreditAmount;
	}

	/**
	* @return
	*/
	public String getFormatCreditAmount()
	{
		return DataFormat.formatDisabledAmount(CreditAmount);
	}

	/**
	 * @param d
	 */
	public void setCreditAmount(double d)
	{
		CreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getCreditRate()
	{
		return CreditRate;
	}

	/**
	* @return
	*/
	public String getFormatCreditRate()
	{
		return DataFormat.formatDisabledAmount(CreditRate * 100);
	}

	/**
	 * @param d
	 */
	public void setCreditRate(double d)
	{
		CreditRate = d;
	}

	/**
	 * @return
	 */
	public long getAreaType()
	{
		return AreaType;
	}

	/**
	 * @return
	 */
	public long getIndustryType1()
	{
		return IndustryType1;
	}

	/**
	 * @return
	 */
	public long getIndustryType2()
	{
		return IndustryType2;
	}

	/**
	 * @param l
	 */
	public void setAreaType(long l)
	{
		AreaType = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType1(long l)
	{
		IndustryType1 = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType2(long l)
	{
		IndustryType2 = l;
	}

	/**
	 * @return
	 */
	public Collection getAssure()
	{
		return cAssure;
	}

	/**
	 * @param collection
	 */
	public void setAssure(Collection collection)
	{
		cAssure = collection;
	}

	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		if (CheckUserName != null)
		{
			return CheckUserName;
		}
		else
		{
			return "";
		}

	}

	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
	}

	/**
//	 * @return
//	 */
//	public YTFormatInfo getYTInfo()
//	{
//		return YTInfo;
//	}
//
//	/**
//	 * @param info
//	 */
//	public void setYTInfo(YTFormatInfo info)
//	{
//		YTInfo = info;
//	}

	/**
	 * @return
	 */
	public Collection getYT()
	{
		return cYT;
	}

	/**
	 * @param collection
	 */
	public void setYT(Collection collection)
	{
		cYT = collection;
	}

	/**
	 * Returns the cAssure.
	 * @return Collection
	 */
	public Collection getCAssure()
	{
		return cAssure;
	}

	/**
	 * Returns the cYT.
	 * @return Collection
	 */
	public Collection getCYT()
	{
		return cYT;
	}

	/**
	 * Sets the cAssure.
	 * @param cAssure The cAssure to set
	 */
	public void setCAssure(Collection cAssure)
	{
		this.cAssure = cAssure;
	}

	/**
	 * Sets the cYT.
	 * @param cYT The cYT to set
	 */
	public void setCYT(Collection cYT)
	{
		this.cYT = cYT;
	}

	/**
	 * Returns the planModifyDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanModifyDate()
	{
		return PlanModifyDate;
	}

	/**
	 * Sets the planModifyDate.
	 * @param planModifyDate The planModifyDate to set
	 */
	public void setPlanModifyDate(Timestamp planModifyDate)
	{
		PlanModifyDate = planModifyDate;
	}

	/**
	 * @return
	 */
	public String getLoanReason()
	{
		return LoanReason;
	}

	/**
	 * @param string
	 */
	public void setLoanReason(String string)
	{
		LoanReason = string;
	}

	/**
	 * @return
	 */
	public String getOther()
	{
		return sOther;
	}

	/**
	 * @param string
	 */
	public void setOther(String string)
	{
		sOther = string;
	}

	/**
	 * @return
	 */
	public Collection getContractContent()
	{
		return cContractContent;
	}

	/**
	 * @param collection
	 */
	public void setContractContent(Collection collection)
	{
		cContractContent = collection;
	}

	/**
	 * Returns the chargeRate.
	 * @return double
	 */
	public double getChargeRate()
	{
		return ChargeRate;
	}

	/**
	 * Sets the chargeRate.
	 * @param chargeRate The chargeRate to set
	 */
	public void setChargeRate(double chargeRate)
	{
		ChargeRate = chargeRate;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @return
	 */
	public long getChargeRateType()
	{
		return ChargeRateType;
	}

	/**
	 * @param l
	 */
	public void setChargeRateType(long l)
	{
		ChargeRateType = l;
	}

	/**
	 * @return
	 */
	public long getBankInterestID()
	{
		return BankInterestID;
	}

	/**
	 * @param l
	 */
	public void setBankInterestID(long l)
	{
		BankInterestID = l;
	}

	/**
	 * @return
	 */
	public double getCheckAmount()
	{
		return CheckAmount;
	}

	/**
	* @return
	*/
	public String getFormatCheckAmount()
	{
		return DataFormat.formatDisabledAmount(CheckAmount);
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		CheckAmount = d;
	}

	/**
	 * @return
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}

	/**
	* @return
	*/
	public String getFormatDiscountRate()
	{
		return DataFormat.formatRate(DiscountRate);
	}

	/**
	 * @param d
	 */
	public void setDiscountRate(double d)
	{
		DiscountRate = d;
	}

	/**
	 * @return
	 */
	public double getDiscountInterest()
	{
		return DiscountInterest;
	}

	/**
	* @return
	*/
	public String getFormatDiscountInterest()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount-CheckAmount);
	}

	/**
	 * @param d
	 */
	public void setDiscountInterest(double d)
	{
		DiscountInterest = d;
	}
	/**
	 * Returns the discountTypeID.
	 * @return long
	 */
	public long getDiscountTypeID()
	{
		return DiscountTypeID;
	}

	/**
	 * Sets the discountTypeID.
	 * @param discountTypeID The discountTypeID to set
	 */
	public void setDiscountTypeID(long discountTypeID)
	{
		DiscountTypeID = discountTypeID;
	}

	/**
	 * Returns the reDiscountInOrOut.
	 * @return long
	 */
	public long getReDiscountInOrOut()
	{
		return ReDiscountInOrOut;
	}

	/**
	 * Sets the reDiscountInOrOut.
	 * @param reDiscountInOrOut The reDiscountInOrOut to set
	 */
	public void setReDiscountInOrOut(long reDiscountInOrOut)
	{
		ReDiscountInOrOut = reDiscountInOrOut;
	}

}
