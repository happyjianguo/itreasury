/*
 * Created on 2003-11-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author ruixie
 * 功能说明：逾期贷款催收通知书info类
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowOverLoanNoticeInfo
{
	public String SerialYear = ""; //编号（年）
	public String SerialNo = ""; //编号（号）
	public String FormNum = "";//催收次数
	public String DebitName = ""; //借款人
	public String AssureName = ""; //担保人
	public String DateStart = ""; //贷款起始日
	public String LoanContractCode = ""; //贷款合同号
	public String AssureContractCode = ""; //担保合同号
	public String LetOutRequisition = ""; //放款通知单号
	public String LoanAmount = ""; //贷款金额(万元)
	public String DateEnd = ""; //贷款到期日
	public String NextTwoWeekDateEnd = ""; //贷款到期日
	public String Deadline = ""; //截至日期
	public String OwnAmount = ""; //未还贷款本金
	public String OwnInterest = ""; //未还贷款利息
	public String OwnComissionFee = ""; //未还贷款手续费
	public String RepaymentInterval = ""; //偿还贷款期限
	public String YearSeal = ""; //盖章日期（年）
	public String MonthSeal = ""; //盖章日期（月）
	public String DaySeal = ""; //盖章日期（日）
	
	public String getNextTwoWeekDateEnd() {
		return NextTwoWeekDateEnd;
	}
	public void setNextTwoWeekDateEnd(String nextTwoWeekDateEnd) {
		NextTwoWeekDateEnd = nextTwoWeekDateEnd;
	}
	/**
	 * @return
	 */
	public String getAssureContractCode()
	{
		return AssureContractCode;
	}
	/**
	 * @return
	 */
	public String getAssureName()
	{
		return AssureName;
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
	public String getDaySeal()
	{
		return DaySeal;
	}
	/**
	 * @return
	 */
	public String getDeadline()
	{
		return Deadline;
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
	public String getLoanContractCode()
	{
		return LoanContractCode;
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
	public String getOwnAmount()
	{
		return OwnAmount;
	}
	/**
	 * @return
	 */
	public String getOwnComissionFee()
	{
		return OwnComissionFee;
	}
	/**
	 * @return
	 */
	public String getOwnInterest()
	{
		return OwnInterest;
	}
	/**
	 * @return
	 */
	public String getRepaymentInterval()
	{
		return RepaymentInterval;
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
	public void setAssureContractCode(String string)
	{
		AssureContractCode = string;
	}
	/**
	 * @param string
	 */
	public void setAssureName(String string)
	{
		AssureName = string;
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
	public void setDaySeal(String string)
	{
		DaySeal = string;
	}
	/**
	 * @param string
	 */
	public void setDeadline(String string)
	{
		Deadline = string;
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
	public void setLoanContractCode(String string)
	{
		LoanContractCode = string;
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
	public void setOwnAmount(String string)
	{
		OwnAmount = string;
	}
	/**
	 * @param string
	 */
	public void setOwnComissionFee(String string)
	{
		OwnComissionFee = string;
	}
	/**
	 * @param string
	 */
	public void setOwnInterest(String string)
	{
		OwnInterest = string;
	}
	/**
	 * @param string
	 */
	public void setRepaymentInterval(String string)
	{
		RepaymentInterval = string;
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
	 * Returns the formNum.
	 * @return String
	 */
	public String getFormNum()
	{
		return FormNum;
	}

	/**
	 * Sets the formNum.
	 * @param formNum The formNum to set
	 */
	public void setFormNum(String formNum)
	{
		FormNum = formNum;
	}

}
