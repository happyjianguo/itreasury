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
 * ����˵�������ڴ������֪ͨ��info��
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowOverLoanNoticeInfo
{
	public String SerialYear = ""; //��ţ��꣩
	public String SerialNo = ""; //��ţ��ţ�
	public String FormNum = "";//���մ���
	public String DebitName = ""; //�����
	public String AssureName = ""; //������
	public String DateStart = ""; //������ʼ��
	public String LoanContractCode = ""; //�����ͬ��
	public String AssureContractCode = ""; //������ͬ��
	public String LetOutRequisition = ""; //�ſ�֪ͨ����
	public String LoanAmount = ""; //������(��Ԫ)
	public String DateEnd = ""; //�������
	public String NextTwoWeekDateEnd = ""; //�������
	public String Deadline = ""; //��������
	public String OwnAmount = ""; //δ�������
	public String OwnInterest = ""; //δ��������Ϣ
	public String OwnComissionFee = ""; //δ������������
	public String RepaymentInterval = ""; //������������
	public String YearSeal = ""; //�������ڣ��꣩
	public String MonthSeal = ""; //�������ڣ��£�
	public String DaySeal = ""; //�������ڣ��գ�
	
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
