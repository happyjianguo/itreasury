/*
 * Created on 2003-11-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
import java.util.Vector;
/**
 * @author ruixie
 * 功能说明:利息通知单info类
 * 支取
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowPayInterestInfo
{
	public String Year = ""; //年
	public String Month = ""; // 月
	public String Day = ""; //日
	public String ClientName = ""; // 单位名称
	public String ConsignClientName = ""; //委托单位名称
	public String AccountType = ""; //账号类型
	public String TransNo = ""; //交易编号
	public String AccountNo = ""; //账号
	public String AccountName = ""; //账户名称
	//活期利息
	public String CurrentInterest = ""; //活期利息
	public String CurrentInterestDateStart = ""; //活期利息起息日期
	public String CurrentInterestDateEnd = ""; //活期利息终息日期
	public String CurrentInterestDay = ""; //活期利息天数
	public String CurrentInterestAmount = ""; //活期利息本金
	public String CurrentIntegalAmount = ""; //活期利息积数
	public String CurrentInterestRate = ""; //活期利息利率
	//协定利息
	public String AccordInterest = ""; //协定利息
	public String AccordInterestDateStart = ""; //协定利息起息日期
	public String AccordInterestDateEnd = ""; //协定利息终息日期
	public String AccordInterestDay = ""; //协定利息天数
	public String AccordInterestAmount = ""; //协定利息本金
	public String AccordIntegalAmount = ""; //协定利息积数
	public String AccordInterestRate = ""; //协定利息利率
	//正常利息
	public String NormalInterestDateStart = ""; //正常利息起息日期
	public String NormalInterestDateEnd = ""; //正常利息终息日期
	public String NormalInterestDay = ""; //正常利息天数
	public String NormalInterestAmount = ""; //正常利息本金
	public String NormalInterestRate = ""; //正常利息利率
	public String NormalInterest = ""; //正常利息
	//免还利息
	public String RemitInterest = ""; //免还利息
	//复利
	public String CompoundInterestDateStart = ""; //复利起息日期
	public String CompoundInterestDateEnd = ""; //复利终息日期
	public String CompoundInterestDay = ""; //复利天数
	public String CompoundInterestAmount = ""; //复利本金
	public String CompoundInterestRate = ""; //复利利率
	public String CompoundInterest = ""; //复利利息
	//逾期罚息
	public String OverInterestDateStart = ""; //逾期罚息起息日期
	public String OverInterestDateEnd = ""; //逾期罚息终息日期
	public String OverInterestDay = ""; //逾期罚息天数
	public String OverInterestAmount = ""; //逾期罚息本金
	public String OverInterestRate = ""; //逾期罚息利率
	public String OverInterest = ""; //逾期罚息利息
	//手续费
	public String CommissionFeeDateStart = ""; //手续费起息日期
	public String CommissionFeeDateEnd = ""; //手续费终息日期
	public String CommissionFeeDay = ""; //手续费天数
	public String CommissionFeeAmount = ""; //手续费本金
	public String CommissionFeeRate = ""; //手续费利率
	public String CommissionFee = ""; //手续费利息
	//担保费
	public String AssureFeeDateStart = ""; //担保费起息日期
	public String AssureFeeDateEnd = ""; //担保费终息日期
	public String AssureFeeDay = ""; //担保费天数
	public String AssureFeeAmount = ""; //担保费本金
	public String AssureFeeRate = ""; //担保费利率
	public String AssureFee = ""; //担保费利息
	public String TotalInterest = ""; //利息总额
	public String TotalInterestChinese = ""; //中文利息总额
	public String InterestAccountNo = ""; //利息账户号
	public String CurrentAccountNo = ""; //对应的活期账户号
	public String ContractNo = ""; //对应的合同号
	public String LoanBillNo = ""; //对应借据号
	public String DepositBillNo = ""; //对应存单号
	public String TransAccountDate = ""; //转账日
	public String InputUserName = ""; //录入人
	public String CheckUserName = ""; //复核人
	public String CurrencyName = "";
	public String SumInterest = "";
	public String ReceiveAccountNo = "";//收利息账户号
	//银团
	//银团贷款收回明细表
	private Vector vctSynLoanRepayDetail = null;
	//第几联
	private String Num = "";
//	办事处
	private long OfficeID = -1;
//	币种
	private long CurrencyID = -1;
	
	/**
	 * @return Returns the receiveAccountNo.
	 */
	public String getReceiveAccountNo() {
		return ReceiveAccountNo;
	}
	/**
	 * @param receiveAccountNo The receiveAccountNo to set.
	 */
	public void setReceiveAccountNo(String receiveAccountNo) {
		ReceiveAccountNo = receiveAccountNo;
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
	public String getAccountType()
	{
		return AccountType;
	}
	/**
	 * @return
	 */
	public String getAssureFeeAmount()
	{
		return AssureFeeAmount;
	}
	/**
	 * @return
	 */
	public String getAssureFeeDateEnd()
	{
		return AssureFeeDateEnd;
	}
	/**
	 * @return
	 */
	public String getAssureFeeDateStart()
	{
		return AssureFeeDateStart;
	}
	/**
	 * @return
	 */
	public String getAssureFeeRate()
	{
		return AssureFeeRate;
	}
	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		return CheckUserName;
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
	public String getCommissionFeeAmount()
	{
		return CommissionFeeAmount;
	}
	/**
	 * @return
	 */
	public String getCommissionFeeDateEnd()
	{
		return CommissionFeeDateEnd;
	}
	/**
	 * @return
	 */
	public String getCommissionFeeDateStart()
	{
		return CommissionFeeDateStart;
	}
	/**
	 * @return
	 */
	public String getCommissionFeeDay()
	{
		return CommissionFeeDay;
	}
	/**
	 * @return
	 */
	public String getCommissionFeeRate()
	{
		return CommissionFeeRate;
	}
	/**
	 * @return
	 */
	public String getCompoundInterestAmount()
	{
		return CompoundInterestAmount;
	}
	/**
	 * @return
	 */
	public String getCompoundInterestDateEnd()
	{
		return CompoundInterestDateEnd;
	}
	/**
	 * @return
	 */
	public String getCompoundInterestDateStart()
	{
		return CompoundInterestDateStart;
	}
	/**
	 * @return
	 */
	public String getCompoundInterestDay()
	{
		return CompoundInterestDay;
	}
	/**
	 * @return
	 */
	public String getCompoundInterestRate()
	{
		return CompoundInterestRate;
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
	public String getCurrentAccountNo()
	{
		return CurrentAccountNo;
	}
	/**
	 * @return
	 */
	public String getDay()
	{
		return Day;
	}
	/**
	 * @return
	 */
	public String getDepositBillNo()
	{
		return DepositBillNo;
	}
	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}
	/**
	 * @return
	 */
	public String getInterestAccountNo()
	{
		return InterestAccountNo;
	}
	/**
	 * @return
	 */
	public String getLoanBillNo()
	{
		return LoanBillNo;
	}
	/**
	 * @return
	 */
	public String getMonth()
	{
		return Month;
	}
	/**
	 * @return
	 */
	public String getNormalInterestAmount()
	{
		return NormalInterestAmount;
	}
	/**
	 * @return
	 */
	public String getNormalInterestDateEnd()
	{
		return NormalInterestDateEnd;
	}
	/**
	 * @return
	 */
	public String getNormalInterestDateStart()
	{
		return NormalInterestDateStart;
	}
	/**
	 * @return
	 */
	public String getNormalInterestDay()
	{
		return NormalInterestDay;
	}
	/**
	 * @return
	 */
	public String getNormalInterestRate()
	{
		return NormalInterestRate;
	}
	/**
	 * @return
	 */
	public String getOverInterestAmount()
	{
		return OverInterestAmount;
	}
	/**
	 * @return
	 */
	public String getOverInterestDateEnd()
	{
		return OverInterestDateEnd;
	}
	/**
	 * @return
	 */
	public String getOverInterestDateStart()
	{
		return OverInterestDateStart;
	}
	/**
	 * @return
	 */
	public String getOverInterestDay()
	{
		return OverInterestDay;
	}
	/**
	 * @return
	 */
	public String getOverInterestRate()
	{
		return OverInterestRate;
	}
	/**
	 * @return
	 */
	public String getTotalInterest()
	{
		return TotalInterest;
	}
	/**
	 * @return
	 */
	public String getTransAccountDate()
	{
		return TransAccountDate;
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
	public String getYear()
	{
		return Year;
	}
	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setAccountType(String string)
	{
		AccountType = string;
	}
	/**
	 * @param string
	 */
	public void setAssureFeeAmount(String string)
	{
		AssureFeeAmount = string;
	}
	/**
	 * @param string
	 */
	public void setAssureFeeDateEnd(String string)
	{
		AssureFeeDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setAssureFeeDateStart(String string)
	{
		AssureFeeDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setAssureFeeRate(String string)
	{
		AssureFeeRate = string;
	}
	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
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
	public void setCommissionFeeAmount(String string)
	{
		CommissionFeeAmount = string;
	}
	/**
	 * @param string
	 */
	public void setCommissionFeeDateEnd(String string)
	{
		CommissionFeeDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setCommissionFeeDateStart(String string)
	{
		CommissionFeeDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setCommissionFeeDay(String string)
	{
		CommissionFeeDay = string;
	}
	/**
	 * @param string
	 */
	public void setCommissionFeeRate(String string)
	{
		CommissionFeeRate = string;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterestAmount(String string)
	{
		CompoundInterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterestDateEnd(String string)
	{
		CompoundInterestDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterestDateStart(String string)
	{
		CompoundInterestDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterestDay(String string)
	{
		CompoundInterestDay = string;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterestRate(String string)
	{
		CompoundInterestRate = string;
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
	public void setCurrentAccountNo(String string)
	{
		CurrentAccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setDay(String string)
	{
		Day = string;
	}
	/**
	 * @param string
	 */
	public void setDepositBillNo(String string)
	{
		DepositBillNo = string;
	}
	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}
	/**
	 * @param string
	 */
	public void setInterestAccountNo(String string)
	{
		InterestAccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setLoanBillNo(String string)
	{
		LoanBillNo = string;
	}
	/**
	 * @param string
	 */
	public void setMonth(String string)
	{
		Month = string;
	}
	/**
	 * @param string
	 */
	public void setNormalInterestAmount(String string)
	{
		NormalInterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setNormalInterestDateEnd(String string)
	{
		NormalInterestDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setNormalInterestDateStart(String string)
	{
		NormalInterestDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setNormalInterestDay(String string)
	{
		NormalInterestDay = string;
	}
	/**
	 * @param string
	 */
	public void setNormalInterestRate(String string)
	{
		NormalInterestRate = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterestAmount(String string)
	{
		OverInterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterestDateEnd(String string)
	{
		OverInterestDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterestDateStart(String string)
	{
		OverInterestDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterestDay(String string)
	{
		OverInterestDay = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterestRate(String string)
	{
		OverInterestRate = string;
	}
	/**
	 * @param string
	 */
	public void setTotalInterest(String string)
	{
		TotalInterest = string;
	}
	/**
	 * @param string
	 */
	public void setTransAccountDate(String string)
	{
		TransAccountDate = string;
	}
	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}
	/**
	 * @param string
	 */
	public void setYear(String string)
	{
		Year = string;
	}
	/**
	 * @return
	 */
	public String getNormalInterest()
	{
		return NormalInterest;
	}
	/**
	 * @param string
	 */
	public void setNormalInterest(String string)
	{
		NormalInterest = string;
	}
	/**
	 * @return
	 */
	public String getCompoundInterest()
	{
		return CompoundInterest;
	}
	/**
	 * @return
	 */
	public String getOverInterest()
	{
		return OverInterest;
	}
	/**
	 * @param string
	 */
	public void setCompoundInterest(String string)
	{
		CompoundInterest = string;
	}
	/**
	 * @param string
	 */
	public void setOverInterest(String string)
	{
		OverInterest = string;
	}
	/**
	 * @return
	 */
	public String getAssureFee()
	{
		return AssureFee;
	}
	/**
	 * @return
	 */
	public String getCommissionFee()
	{
		return CommissionFee;
	}
	/**
	 * @param string
	 */
	public void setAssureFee(String string)
	{
		AssureFee = string;
	}
	/**
	 * @param string
	 */
	public void setCommissionFee(String string)
	{
		CommissionFee = string;
	}
	/**
	 * @return
	 */
	public String getConsignClientName()
	{
		return ConsignClientName;
	}
	/**
	 * @param string
	 */
	public void setConsignClientName(String string)
	{
		ConsignClientName = string;
	}
	/**
	 * @return
	 */
	public String getAssureFeeDay()
	{
		return AssureFeeDay;
	}
	/**
	 * @param string
	 */
	public void setAssureFeeDay(String string)
	{
		AssureFeeDay = string;
	}
	/**
	 * Returns the totalInterestChinese.
	 * @return String
	 */
	public String getTotalInterestChinese()
	{
		return TotalInterestChinese;
	}
	/**
	 * Sets the totalInterestChinese.
	 * @param totalInterestChinese The totalInterestChinese to set
	 */
	public void setTotalInterestChinese(String totalInterestChinese)
	{
		TotalInterestChinese = totalInterestChinese;
	}
	/**
	 * @return
	 */
	public String getAccordInterest()
	{
		return AccordInterest;
	}
	/**
	 * @return
	 */
	public String getCurrentInterest()
	{
		return CurrentInterest;
	}
	/**
	 * @param string
	 */
	public void setAccordInterest(String string)
	{
		AccordInterest = string;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterest(String string)
	{
		CurrentInterest = string;
	}
	/**
	 * @return
	 */
	public String getCurrentInterestAmount()
	{
		return CurrentInterestAmount;
	}
	/**
	 * @return
	 */
	public String getCurrentInterestDateEnd()
	{
		return CurrentInterestDateEnd;
	}
	/**
	 * @return
	 */
	public String getCurrentInterestRate()
	{
		return CurrentInterestRate;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterestAmount(String string)
	{
		CurrentInterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterestDateEnd(String string)
	{
		CurrentInterestDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterestDateStart(String string)
	{
		CurrentInterestDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterestDay(String string)
	{
		CurrentInterestDay = string;
	}
	/**
	 * @param string
	 */
	public void setCurrentInterestRate(String string)
	{
		CurrentInterestRate = string;
	}
	/**
	 * @return
	 */
	public String getAccordInterestAmount()
	{
		return AccordInterestAmount;
	}
	/**
	 * @return
	 */
	public String getAccordInterestDateEnd()
	{
		return AccordInterestDateEnd;
	}
	/**
	 * @return
	 */
	public String getAccordInterestDateStart()
	{
		return AccordInterestDateStart;
	}
	/**
	 * @return
	 */
	public String getAccordInterestDay()
	{
		return AccordInterestDay;
	}
	/**
	 * @return
	 */
	public String getAccordInterestRate()
	{
		return AccordInterestRate;
	}
	/**
	 * @param string
	 */
	public void setAccordInterestAmount(String string)
	{
		AccordInterestAmount = string;
	}
	/**
	 * @param string
	 */
	public void setAccordInterestDateEnd(String string)
	{
		AccordInterestDateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setAccordInterestDateStart(String string)
	{
		AccordInterestDateStart = string;
	}
	/**
	 * @param string
	 */
	public void setAccordInterestDay(String string)
	{
		AccordInterestDay = string;
	}
	/**
	 * @param string
	 */
	public void setAccordInterestRate(String string)
	{
		AccordInterestRate = string;
	}
	/**
	 * Returns the accordIntegalAmount.
	 * @return String
	 */
	public String getAccordIntegalAmount()
	{
		return AccordIntegalAmount;
	}
	/**
	 * Returns the currentIntegalAmount.
	 * @return String
	 */
	public String getCurrentIntegalAmount()
	{
		return CurrentIntegalAmount;
	}
	/**
	 * Sets the accordIntegalAmount.
	 * @param accordIntegalAmount The accordIntegalAmount to set
	 */
	public void setAccordIntegalAmount(String accordIntegalAmount)
	{
		AccordIntegalAmount = accordIntegalAmount;
	}
	/**
	 * Sets the currentIntegalAmount.
	 * @param currentIntegalAmount The currentIntegalAmount to set
	 */
	public void setCurrentIntegalAmount(String currentIntegalAmount)
	{
		CurrentIntegalAmount = currentIntegalAmount;
	}
	/**
	 * Returns the currentInterestDateStart.
	 * @return String
	 */
	public String getCurrentInterestDateStart()
	{
		return CurrentInterestDateStart;
	}
	/**
	 * Returns the currentInterestDay.
	 * @return String
	 */
	public String getCurrentInterestDay()
	{
		return CurrentInterestDay;
	}
	/**
	 * Returns the accountName.
	 * @return String
	 */
	public String getAccountName()
	{
		return AccountName;
	}
	/**
	 * Sets the accountName.
	 * @param accountName The accountName to set
	 */
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}
	/**
	 * Returns the synLoanRepayDetail.
	 * @return Vector
	 */
	public Vector getSynLoanRepayDetail()
	{
		return vctSynLoanRepayDetail;
	}
	/**
	 * Sets the synLoanRepayDetail.
	 * @param synLoanRepayDetail The synLoanRepayDetail to set
	 */
	public void setSynLoanRepayDetail(Vector synLoanRepayDetail)
	{
		vctSynLoanRepayDetail = synLoanRepayDetail;
	}
	/**
	 * Returns the num.
	 * @return String
	 */
	public String getNum()
	{
		return Num;
	}
	/**
	 * Sets the num.
	 * @param num The num to set
	 */
	public void setNum(String num)
	{
		Num = num;
	}
	/**
	 * Returns the currencyName.
	 * @return String
	 */
	public String getCurrencyName()
	{
		return CurrencyName;
	}
	/**
	 * Sets the currencyName.
	 * @param currencyName The currencyName to set
	 */
	public void setCurrencyName(String currencyName)
	{
		CurrencyName = currencyName;
	}
	/**
	 * Returns the sumInterest.
	 * @return String
	 */
	public String getSumInterest()
	{
		return SumInterest;
	}

	/**
	 * Sets the sumInterest.
	 * @param sumInterest The sumInterest to set
	 */
	public void setSumInterest(String sumInterest)
	{
		SumInterest = sumInterest;
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
	/**
	 * @return Returns the vctSynLoanRepayDetail.
	 */
	public Vector getVctSynLoanRepayDetail()
	{
		return vctSynLoanRepayDetail;
	}
	/**
	 * @param vctSynLoanRepayDetail The vctSynLoanRepayDetail to set.
	 */
	public void setVctSynLoanRepayDetail(Vector vctSynLoanRepayDetail)
	{
		this.vctSynLoanRepayDetail = vctSynLoanRepayDetail;
	}
	public String getRemitInterest() {
		return RemitInterest;
	}
	public void setRemitInterest(String remitInterest) {
		RemitInterest = remitInterest;
	}
}
