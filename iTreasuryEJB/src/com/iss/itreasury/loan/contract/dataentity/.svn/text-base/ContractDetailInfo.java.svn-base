package com.iss.itreasury.loan.contract.dataentity;

import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;

public class ContractDetailInfo implements java.io.Serializable {


 


	/**
	 * ContractInfo 构造子注解。
	 */
	public ContractDetailInfo()
	{
		super();
	}

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
	private long SubTypeID;

	//nBorrowClientID:贷款单位ID
	private long BorrowClientID;

	//niscircle:是否循环贷款
	private long isCircle;
	//风险等级
	private long riskLevel;

	//sName:贷款单位名称
	private String BorrowClientName;

	//nClientID 委托单位ID
	private long ClientID;

	//sName:委托单位名称
	private String ClientName;


	//mLoanAmount 金额
	private double LoanAmount;

	//ExamineAmount 批准金额
	private double ExamineAmount;
	
	
	

	//fInterestRate:利率
	private double InterestRate;

	//fInterestRate:基准利率
	private double BasicInterestRate;

	//fInterestRate:银行利率ID
	private long BankInterestID = -1;
	
	//InterestTypeID:利率类型
	private long InterestTypeID = 1;
	
	//LiborRateID:Libor利率ID
	private long LiborRateID = -1;

	//nIntervalNum:贷款期限
	private long IntervalNum;

	//sInputUserName 录入人姓名
	private String InputUserName;
	private Timestamp InputDate; //合同录入日期

	//DiscountRate 贴现利率
	private double DiscountRate;

	//nStatusID:合同状态
	private long StatusID;
	
	
	//nDraftTypeID:贴现汇票种类
	private long tsDiscountTypeID;
	private String tsDiscountType;
	
	//手续费率
	private double ChargeRate = 0;

	

	//分页显示总页数
	private long PageCount;

	// 申请编号
	private String ApplyCode;

	//合同当前余额
	private double Balance = 0;

	//指定余额日期的合同余额
	private double dailyBalance = 0;
	
	//dtLoanStart借款起始日期
	private Timestamp LoanStart;
	
	//dtDiscountDate贴现日期
	private Timestamp discountDate;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;

	//初始利率值
	private double Rate = -1;

	//浮动利率正负标识（正1负2）
	//private long AddOrDecrease = 1;

	//浮动利率
	private double AdjustRate = 0;

	

	//按地区分类
	private long AreaType = -1;

	//按行业分类1
	private long IndustryType1 = -1;

	//按行业分类2
	private long IndustryType2 = -1;
	
	//按行业分类3
	private long IndustryType3 = -1;


	//信用
	private long IsCredit = -1;

	//保证
	private long IsAssure = -1;

	//抵押
	private long IsImpawn = -1;

	//质押
	private long IsPledge = -1;
	
	//保证金
    private long IsRecognizance = -1;
    
 
    //固定浮动利率
    private double staidAdjustRate=0;

	private String subTypeName = "";//贷款子类型 mzh_fu 2007/06/12
	

	
	
	
	private String dailyDate;
	
	
	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	
	

	
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
	 * function 得到合同风险等级
	 * return lStatusID
	 */
	public long getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @param lStatusID
	 * function 设置合同风险等级
	 * return void
	 */
	public void setRiskLevel(long lRiskLevel)
	{
		this.riskLevel = lRiskLevel;
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
	 * Returns the chargeRate.
	 * @return double
	 */
	public double getChargeRate()
	{
		return ChargeRate;
	}

	public String getFormatChargeRate()
	{
		return DataFormat.formatRate(ChargeRate);
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
	public double getBasicInterestRate()
	{
		return BasicInterestRate;
	}

	/**
	 * @param d
	 */
	public void setBasicInterestRate(double d)
	{
		BasicInterestRate = d;
	}



	
	/**
	 * Returns the isAssure.
	 * @return long
	 */
	public long getIsAssure()
	{
		return IsAssure;
	}

	/**
	 * Returns the isCredit.
	 * @return long
	 */
	public long getIsCredit()
	{
		return IsCredit;
	}

	/**
	 * Returns the isImpawn.
	 * @return long
	 */
	public long getIsImpawn()
	{
		return IsImpawn;
	}

	/**
	 * Returns the isPledge.
	 * @return long
	 */
	public long getIsPledge()
	{
		return IsPledge;
	}

	/**
	 * Sets the isAssure.
	 * @param isAssure The isAssure to set
	 */
	public void setIsAssure(long isAssure)
	{
		IsAssure = isAssure;
	}

	/**
	 * Sets the isCredit.
	 * @param isCredit The isCredit to set
	 */
	public void setIsCredit(long isCredit)
	{
		IsCredit = isCredit;
	}

	/**
	 * Sets the isImpawn.
	 * @param isImpawn The isImpawn to set
	 */
	public void setIsImpawn(long isImpawn)
	{
		IsImpawn = isImpawn;
	}

	/**
	 * Sets the isPledge.
	 * @param isPledge The isPledge to set
	 */
	public void setIsPledge(long isPledge)
	{
		IsPledge = isPledge;
	}



	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		Balance = d;
	}



    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return staidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        staidAdjustRate = d;
    }



	

   
    /**
     * @return Returns the isRecognizance.
     */
    public long getIsRecognizance()
    {
        return IsRecognizance;
    }
    /**
     * @param isRecognizance The isRecognizance to set.
     */
    public void setIsRecognizance(long isRecognizance)
    {
        IsRecognizance = isRecognizance;
    }
	
    /**
     * @return Returns the industryType3.
     */
    public long getIndustryType3()
    {
        return IndustryType3;
    }
    /**
     * @param industryType3 The industryType3 to set.
     */
    public void setIndustryType3(long industryType3)
    {
        IndustryType3 = industryType3;
    }




    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return InterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        InterestTypeID = interestTypeID;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return LiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        LiborRateID = liborRateID;
    }
   
	
	
	/**
	 * Returns the subTypeID.
	 * @return long
	 */
	public long getSubTypeID() {
		return SubTypeID;
	}

	/**
	 * Sets the subTypeID.
	 * @param subTypeID The subTypeID to set
	 */
	public void setSubTypeID(long subTypeID) {
		SubTypeID = subTypeID;
	}
 
	


	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public Timestamp getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Timestamp discountDate) {
		this.discountDate = discountDate;
	}

	public long getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(long isCircle) {
		this.isCircle = isCircle;
	}

	public String getTsDiscountType() {
		return tsDiscountType;
	}

	public void setTsDiscountType(String tsDiscountType) {
		this.tsDiscountType = tsDiscountType;
	}

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}

	
	

	public double getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(double dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	
}
