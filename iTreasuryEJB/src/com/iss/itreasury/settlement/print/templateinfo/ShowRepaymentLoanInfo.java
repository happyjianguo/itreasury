/*
 * Created on 2003-11-21
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;

import java.util.Vector;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowRepaymentLoanInfo
{
	public ShowRepaymentLoanInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public String Year = ""; //年
	public String Month = ""; //月
	public String Day = ""; //日
	public String Amount = ""; //本金金额
	public String CurrencyName = ""; //币种
	public String TransNo = ""; //交易号
	public String ChineseAmount = ""; //本金金额（大写）
	public String InputUserName = ""; //录入人
	public String CheckUserName = ""; //复核人
	public String Abstract = ""; //摘要
	public String Note = ""; //备注
	public String ContractRate = ""; //合同利率
	public String ChargeRate = ""; //手续费率
	public String LoanType = ""; //贷款种类
	public String RepaymentUnitName = ""; //还款单位名称
	public String RepaymentBankName = ""; //付款银行名称
	public String RepaymentAccountNo = ""; //付款账号
	public String ContractCode = ""; //合同编号
	public String BillCode = ""; //放款单号
	public String ConsignUnit = ""; //委托单位
	public String LoanInterval = ""; //贷款期限
	public String TotalRepayAmount = ""; //累计还款
	public String Balance = ""; //余额
	public String LoanRate = ""; //贷款利率
	public String LoanInterest = ""; //贷款利息
	public String OverDueRate = ""; //逾期贷款利率
	public String OverDueInterest = ""; //逾期贷款利息
	public String TotalInterest = ""; //合计利息
	public String DateStart = ""; //起始日期
	public String DateEnd = ""; //终止日期
	//银团
	//承贷比例
	private String SumLoanRate = "";
	//收款金额
	private String SumReciveAmount = "";
	//银团贷款收回明细表
	private  Vector vctSynLoanRepayDetail = null;
	//第几联
	private String Num = "";
	
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
	public String getAmount()
	{
		return Amount;
	}
	/**
	 * @return
	 */
	public String getBalance()
	{
		return Balance;
	}
	/**
	 * @return
	 */
	public String getBillCode()
	{
		return BillCode;
	}
	/**
	 * @return
	 */
	public String getChargeRate()
	{
		return ChargeRate;
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
	public String getChineseAmount()
	{
		return ChineseAmount;
	}
	/**
	 * @return
	 */
	public String getConsignUnit()
	{
		return ConsignUnit;
	}
	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
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
	public String getCurrencyName()
	{
		return CurrencyName;
	}
	/**
	 * @return
	 */
	public String getDateEnd()
	{
		return DateEnd;
	}
	/**
	 * @return
	 */
	public String getDateStart()
	{
		return DateStart;
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
	public String getInputUserName()
	{
		return InputUserName;
	}
	/**
	 * @return
	 */
	public String getLoanInterest()
	{
		return LoanInterest;
	}
	/**
	 * @return
	 */
	public String getLoanInterval()
	{
		return LoanInterval;
	}
	/**
	 * @return
	 */
	public String getLoanRate()
	{
		return LoanRate;
	}
	/**
	 * @return
	 */
	public String getLoanType()
	{
		return LoanType;
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
	public String getNote()
	{
		return Note;
	}
	/**
	 * @return
	 */
	public String getOverDueInterest()
	{
		return OverDueInterest;
	}
	/**
	 * @return
	 */
	public String getOverDueRate()
	{
		return OverDueRate;
	}
	/**
	 * @return
	 */
	public String getRepaymentAccountNo()
	{
		return RepaymentAccountNo;
	}
	/**
	 * @return
	 */
	public String getRepaymentBankName()
	{
		return RepaymentBankName;
	}
	/**
	 * @return
	 */
	public String getRepaymentUnitName()
	{
		return RepaymentUnitName;
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
	public String getTotalRepayAmount()
	{
		return TotalRepayAmount;
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
	public void setAbstract(String string)
	{
		Abstract = string;
	}
	/**
	 * @param string
	 */
	public void setAmount(String string)
	{
		Amount = string;
	}
	/**
	 * @param string
	 */
	public void setBalance(String string)
	{
		Balance = string;
	}
	/**
	 * @param string
	 */
	public void setBillCode(String string)
	{
		BillCode = string;
	}
	/**
	 * @param string
	 */
	public void setChargeRate(String string)
	{
		ChargeRate = string;
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
	public void setChineseAmount(String string)
	{
		ChineseAmount = string;
	}
	/**
	 * @param string
	 */
	public void setConsignUnit(String string)
	{
		ConsignUnit = string;
	}
	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		ContractCode = string;
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
	public void setCurrencyName(String string)
	{
		CurrencyName = string;
	}
	/**
	 * @param string
	 */
	public void setDateEnd(String string)
	{
		DateEnd = string;
	}
	/**
	 * @param string
	 */
	public void setDateStart(String string)
	{
		DateStart = string;
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
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}
	/**
	 * @param string
	 */
	public void setLoanInterest(String string)
	{
		LoanInterest = string;
	}
	/**
	 * @param string
	 */
	public void setLoanInterval(String string)
	{
		LoanInterval = string;
	}
	/**
	 * @param string
	 */
	public void setLoanRate(String string)
	{
		LoanRate = string;
	}
	/**
	 * @param string
	 */
	public void setLoanType(String string)
	{
		LoanType = string;
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
	public void setNote(String string)
	{
		Note = string;
	}
	/**
	 * @param string
	 */
	public void setOverDueInterest(String string)
	{
		OverDueInterest = string;
	}
	/**
	 * @param string
	 */
	public void setOverDueRate(String string)
	{
		OverDueRate = string;
	}
	/**
	 * @param string
	 */
	public void setRepaymentAccountNo(String string)
	{
		RepaymentAccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setRepaymentBankName(String string)
	{
		RepaymentBankName = string;
	}
	/**
	 * @param string
	 */
	public void setRepaymentUnitName(String string)
	{
		RepaymentUnitName = string;
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
	public void setTotalRepayAmount(String string)
	{
		TotalRepayAmount = string;
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
	 * Returns the sumLoanRate.
	 * @return String
	 */
	public String getSumLoanRate()
	{
		return SumLoanRate;
	}

	/**
	 * Returns the sumReciveAmount.
	 * @return String
	 */
	public String getSumReciveAmount()
	{
		return SumReciveAmount;
	}

	/**
	 * Sets the sumLoanRate.
	 * @param sumLoanRate The sumLoanRate to set
	 */
	public void setSumLoanRate(String sumLoanRate)
	{
		SumLoanRate = sumLoanRate;
	}

	/**
	 * Sets the sumReciveAmount.
	 * @param sumReciveAmount The sumReciveAmount to set
	 */
	public void setSumReciveAmount(String sumReciveAmount)
	{
		SumReciveAmount = sumReciveAmount;
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

}
