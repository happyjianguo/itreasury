/*
 * Created on 2003-11-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;

import java.sql.Timestamp;

/**
 * @author ruixie
 * ����˵�������ڴ���֤ʵ��
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowFixedDepositOpenInfo
{
	public String DepositBillNo = ""; //���ݺ�
	public String TransNo = "";//���ױ��
	public String DateOpenAccount = ""; //������
	public String ChineseOpenAccountDate = "";//���Ŀ�������
	public String CurrencyName = ""; //����
	public String AccountName = ""; //����
	public String AccountNo = ""; //�˺�
	public String ChineseAmount = ""; //��д��� 
	public String Amount = ""; //Сд���
	public String Interval = ""; //����
	public String StartInterestDate = ""; //��Ϣ��
	public String InputUserName = ""; //¼����
	public String CheckUserName = ""; //������
	public String Rate = "";//����
	private String EndDate = "";//������
	private String Abstract = ""; //ժҪ
	
	//added by mzh_fu 2007/04/11
	private String startDate = "";//��ʼ����
	
	/**
	 * @return
	 */
	public String getAccountName()
	{
		return AccountName;
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
	public String getAmount()
	{
		return Amount;
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
	public String getCurrencyName()
	{
		return CurrencyName;
	}
	/**
	 * @return
	 */
	public String getDateOpenAccount()
	{
		return DateOpenAccount;
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
	public String getStartInterestDate()
	{
		return StartInterestDate;
	}
	/**
	 * @param string
	 */
	public void setAccountName(String string)
	{
		AccountName = string;
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
	public void setAmount(String string)
	{
		Amount = string;
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
	public void setCurrencyName(String string)
	{
		CurrencyName = string;
	}
	/**
	 * @param string
	 */
	public void setDateOpenAccount(String string)
	{
		DateOpenAccount = string;
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
	public void setStartInterestDate(String string)
	{
		StartInterestDate = string;
	}
	/**
	 * @return
	 */
	public String getInterval()
	{
		return Interval;
	}

	/**
	 * @return
	 */
	public String getRate()
	{
		return Rate;
	}

	/**
	 * @param string
	 */
	public void setInterval(String string)
	{
		Interval = string;
	}

	/**
	 * @param string
	 */
	public void setRate(String string)
	{
		Rate = string;
	}

	/**
	 * @return
	 */
	public String getChineseOpenAccountDate()
	{
		return ChineseOpenAccountDate;
	}

	/**
	 * @param string
	 */
	public void setChineseOpenAccountDate(String string)
	{
		ChineseOpenAccountDate = string;
	}

	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
	}

	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract()
	{
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String abstract1)
	{
		Abstract = abstract1;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate()
	{
		return EndDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate)
	{
		EndDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
