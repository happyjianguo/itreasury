/*
 * Created on 2003-11-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
/**
 * @author ruixie
 * ����˵����Ӧ��������Ϣ֪ͨ��info��
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowTrustLoanInterestInfo
{
	public String SerialYear = ""; //��ţ��꣩
	public String SerialNo = ""; //��ţ��ţ�
	public String DebitName = ""; //�����
	public String DateSign = ""; //ί�д����ͬǩ����
	public String LoanContractCode = ""; //�����ͬ��
	public String LetOutRequisition = ""; //�ſ�֪ͨ��
	public String DateDuePay = ""; //Ӧ֧������
	public String Quarter = ""; //����
	public String InterestAmount = ""; //��Ϣ
	public String LoanAmount = ""; //�����
	public String LoanBalance = ""; //�������
	public String ContractRate = ""; //��ͬ����
	public String ExcecuteRate = ""; //ִ������
	public String DateRateModify = ""; //�������ʵ�������
	public String RateModify = ""; //��������Ϣ
	public String LoanIntervalMonth = ""; //�������ޣ��£�
	public String LoanIntervalStart = ""; //��������(��ʼ��)
	public String LoanIntervalEnd = ""; //�������ޣ������գ�
	public String DuePayLoanInterest = ""; //Ӧ��������Ϣ
	public String DuePayCompundInterest = ""; //Ӧ������
	public String DuePayToalInterest = ""; //Ӧ����Ϣ���úϼ�
	public String YearSeal = ""; //�������ڣ��꣩
	public String MonthSeal = ""; //�������ڣ��£�
	public String DaySeal = ""; //�������ڣ��գ�
	public String ClientSeal = "";//���ű�ʶ
	public String InterestStartDate = "";//��Ϣ��
	public String IntervalDays = "";//��Ϣ����
	//modify by xwhe 2008-12-03
	public String ChiLoanBalance = ""; //��������д
	
	
	public String preInterestDate = "";//��Ϣ�յ�ǰһ�졣modified by ylguo
	
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