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
	public String Year = ""; //��
	public String Month = ""; //��
	public String Day = ""; //��
	public String Amount = ""; //������
	public String CurrencyName = ""; //����
	public String TransNo = ""; //���׺�
	public String ChineseAmount = ""; //�������д��
	public String InputUserName = ""; //¼����
	public String CheckUserName = ""; //������
	public String Abstract = ""; //ժҪ
	public String Note = ""; //��ע
	public String ContractRate = ""; //��ͬ����
	public String ChargeRate = ""; //��������
	public String LoanType = ""; //��������
	public String RepaymentUnitName = ""; //���λ����
	public String RepaymentBankName = ""; //������������
	public String RepaymentAccountNo = ""; //�����˺�
	public String ContractCode = ""; //��ͬ���
	public String BillCode = ""; //�ſ��
	public String ConsignUnit = ""; //ί�е�λ
	public String LoanInterval = ""; //��������
	public String TotalRepayAmount = ""; //�ۼƻ���
	public String Balance = ""; //���
	public String LoanRate = ""; //��������
	public String LoanInterest = ""; //������Ϣ
	public String OverDueRate = ""; //���ڴ�������
	public String OverDueInterest = ""; //���ڴ�����Ϣ
	public String TotalInterest = ""; //�ϼ���Ϣ
	public String DateStart = ""; //��ʼ����
	public String DateEnd = ""; //��ֹ����
	//����
	//�д�����
	private String SumLoanRate = "";
	//�տ���
	private String SumReciveAmount = "";
	//���Ŵ����ջ���ϸ��
	private  Vector vctSynLoanRepayDetail = null;
	//�ڼ���
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
