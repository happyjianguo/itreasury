/*
 * Created on 2003-11-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
/**
 * @author ruixie
 * 功能说明：应付贷款利息通知书info类
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowTrustLoanInterestInfo
{
	public String SerialYear = ""; //编号（年）
	public String SerialNo = ""; //编号（号）
	public String DebitName = ""; //借款人
	public String DateSign = ""; //委托贷款合同签订日
	public String LoanContractCode = ""; //贷款合同号
	public String LetOutRequisition = ""; //放款通知单
	public String DateDuePay = ""; //应支付日期
	public String Quarter = ""; //季度
	public String InterestAmount = ""; //利息
	public String LoanAmount = ""; //贷款本金
	public String LoanBalance = ""; //贷款余额
	public String ContractRate = ""; //合同利率
	public String ExcecuteRate = ""; //执行利率
	public String DateRateModify = ""; //贷款利率调整日期
	public String RateModify = ""; //调整后年息
	public String LoanIntervalMonth = ""; //贷款期限（月）
	public String LoanIntervalStart = ""; //贷款期限(起始日)
	public String LoanIntervalEnd = ""; //贷款期限（到期日）
	public String DuePayLoanInterest = ""; //应付贷款利息
	public String DuePayCompundInterest = ""; //应付复利
	public String DuePayToalInterest = ""; //应付利息费用合计
	public String YearSeal = ""; //盖章日期（年）
	public String MonthSeal = ""; //盖章日期（月）
	public String DaySeal = ""; //盖章日期（日）
	public String ClientSeal = "";//部门标识
	public String InterestStartDate = "";//起息日
	public String IntervalDays = "";//计息天数
	//modify by xwhe 2008-12-03
	public String ChiLoanBalance = ""; //贷款余额大写
	
	
	public String preInterestDate = "";//结息日的前一天。modified by ylguo
	
	public String getPreInterestDate() {
		return preInterestDate;
	}
	public void setPreInterestDate(String preInterestDate) {
		this.preInterestDate = preInterestDate;
	}
	/**
	 * @return
	 */
	public String getContractRate()
	{
		return ContractRate;
	}
	/**
	 * @return
	 */
	public String getDateDuePay()
	{
		return DateDuePay;
	}
	/**
	 * @return
	 */
	public String getDateRateModify()
	{
		return DateRateModify;
	}
	/**
	 * @return
	 */
	public String getDateSign()
	{
		return DateSign;
	}
	/**
	 * @return
	 */
	public String getDaySeal()
	{
		return DaySeal;
	}
	/**
	 * @return
	 */
	public String getDebitName()
	{
		return DebitName;
	}
	/**
	 * @return
	 */
	public String getDuePayCompundInterest()
	{
		return DuePayCompundInterest;
	}
	/**
	 * @return
	 */
	public String getDuePayLoanInterest()
	{
		return DuePayLoanInterest;
	}
	/**
	 * @return
	 */
	public String getDuePayToalInterest()
	{
		return DuePayToalInterest;
	}
	/**
	 * @return
	 */
	public String getExcecuteRate()
	{
		return ExcecuteRate;
	}
	/**
	 * @return
	 */
	public String getInterestAmount()
	{
		return InterestAmount;
	}
	/**
	 * @return
	 */
	public String getLetOutRequisition()
	{
		return LetOutRequisition;
	}
	/**
	 * @return
	 */
	public String getLoanAmount()
	{
		return LoanAmount;
	}
	/**
	 * @return
	 */
	public String getLoanBalance()
	{
		return LoanBalance;
	}
	/**
	 * @return
	 */
	public String getLoanContractCode()
	{
		return LoanContractCode;
	}
	/**
	 * @return
	 */
	public String getLoanIntervalEnd()
	{
		return LoanIntervalEnd;
	}
	/**
	 * @return
	 */
	public String getLoanIntervalMonth()
	{
		return LoanIntervalMonth;
	}
	/**
	 * @return
	 */
	public String getLoanIntervalStart()
	{
		return LoanIntervalStart;
	}
	/**
	 * @return
	 */
	public String getMonthSeal()
	{
		return MonthSeal;
	}
	/**
	 * @return
	 */
	public String getQuarter()
	{
		return Quarter;
	}
	/**
	 * @return
	 */
	public String getRateModify()
	{
		return RateModify;
	}
	/**
	 * @return
	 */
	public String getSerialNo()
	{
		return SerialNo;
	}
	/**
	 * @return
	 */
	public String getSerialYear()
	{
		return SerialYear;
	}
	/**
	 * @return
	 */
	public String getYearSeal()
	{
		return YearSeal;
	}
	/**
	 * @param string
	 */
	public void setContractRate(String string)
	{
		ContractRate = string;
	}
	/**
	 * @param string
	 */
	public void setDateDuePay(String string)
	{
		DateDuePay = string;
	}
	/**
	 * @param string
	 */
	public void setDateRateModify(String string)
	{
		DateRateModify = string;
	}
	/**
	 * @param string
	 */
	public void setDateSign(String string)
	{
		DateSign = string;
	}
	/**
	 * @param string
	 */
	public void setDaySeal(String string)
	{
		DaySeal = string;
	}
	/**
	 * @param string
	 */
	public void setDebitName(String string)
	{
		DebitName = string;
	}
	/**
	 * @param string
	 */
	public void setDuePayCompundInterest(String string)
	{
		DuePayCompundInterest = string;
	}
	/**
	 * @param string
	 */
	public void setDuePayLoanInterest(String string)
	{
		DuePayLoanInterest = string;
	}
	/**
	 * @param string
	 */
	public void setDuePayToalInterest(String string)
	{
		DuePayToalInterest = string;
	}
	/**
	 * @param string
	 */
	public void setExcecuteRate(String string)
	{
		ExcecuteRate = string;
	}
	/**
	 * @param string
	 */
	public void setInterestAmount(String string)
	{
		InterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setLetOutRequisition(String string)
	{
		LetOutRequisition = string;
	}
	/**
	 * @param string
	 */
	public void setLoanAmount(String string)
	{
		LoanAmount = string;
	}
	/**
	 * @param string
	 */
	public void setLoanBalance(String string)
	{
		LoanBalance = string;
	}
	/**
	 * @param string
	 */
	public void setLoanContractCode(String string)
	{
		LoanContractCode = string;
	}
	/**
	 * @param string
	 */
	public void setLoanIntervalEnd(String string)
	{
		LoanIntervalEnd = string;
	}
	/**
	 * @param string
	 */
	public void setLoanIntervalMonth(String string)
	{
		LoanIntervalMonth = string;
	}
	/**
	 * @param string
	 */
	public void setLoanIntervalStart(String string)
	{
		LoanIntervalStart = string;
	}
	/**
	 * @param string
	 */
	public void setMonthSeal(String string)
	{
		MonthSeal = string;
	}
	/**
	 * @param string
	 */
	public void setQuarter(String string)
	{
		Quarter = string;
	}
	/**
	 * @param string
	 */
	public void setRateModify(String string)
	{
		RateModify = string;
	}
	/**
	 * @param string
	 */
	public void setSerialNo(String string)
	{
		SerialNo = string;
	}
	/**
	 * @param string
	 */
	public void setSerialYear(String string)
	{
		SerialYear = string;
	}
	/**
	 * @param string
	 */
	public void setYearSeal(String string)
	{
		YearSeal = string;
	}
	/**
	 * @return
	 */
	public String getClientSeal()
	{
		return ClientSeal;
	}

	/**
	 * @param string
	 */
	public void setClientSeal(String string)
	{
		ClientSeal = string;
	}
	
	public String getInterestStartDate()
	{
	
		return InterestStartDate;
	}
	
	public void setInterestStartDate(String interestStartDate)
	{
	
		InterestStartDate = interestStartDate;
	}
	
	public String getIntervalDays()
	{
	
		return IntervalDays;
	}
	
	public void setIntervalDays(String intervalDays)
	{
	
		IntervalDays = intervalDays;
	}
	public String getChiLoanBalance() {
		return ChiLoanBalance;
	}
	public void setChiLoanBalance(String chiLoanBalance) {
		ChiLoanBalance = chiLoanBalance;
	}

}
