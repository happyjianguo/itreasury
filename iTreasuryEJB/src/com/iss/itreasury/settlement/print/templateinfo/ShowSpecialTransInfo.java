/*
 * Created on 2003-11-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author Jason Fang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ShowSpecialTransInfo implements Serializable
{
	public ShowSpecialTransInfo()
	{
		super();
	}
	public String Year = "";//��
	public String Month = "";//��
	public String Day = "";//��
	public String PayAccountName = "";//������ȫ��
	public String PayAccountNo = "";//����˺�
	public String PayBankName = "";//�������������
	public String ReceiveBankCode = "";
	public String PayBankCode = "";
	public String ReceiveAccountName = "";//�տ���ȫ��
	public String ReceiveAccountNo = "";//�տ���˺�
	public String ReceiveBankName = "";//�տ����������
	public String Amount = "";//������
	public String CheckUserName = "";//������
	public String InputUserName = "";//¼����
	public String CurrencyName = "";//����
	public String TransNo = "";//���׺�
	public String ChineseAmount = "";//�������д��
	public String Abstract = "";//ժҪ
	
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
	public String getMonth()
	{
		return Month;
	}

	/**
	 * @return
	 */
	public String getPayAccountName()
	{
		return PayAccountName;
	}

	/**
	 * @return
	 */
	public String getPayAccountNo()
	{
		return PayAccountNo;
	}

	/**
	 * @return
	 */
	public String getPayBankCode()
	{
		return PayBankCode;
	}

	/**
	 * @return
	 */
	public String getPayBankName()
	{
		return PayBankName;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountName()
	{
		return ReceiveAccountName;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountNo()
	{
		return ReceiveAccountNo;
	}

	/**
	 * @return
	 */
	public String getReceiveBankCode()
	{
		return ReceiveBankCode;
	}

	/**
	 * @return
	 */
	public String getReceiveBankName()
	{
		return ReceiveBankName;
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
	public void setMonth(String string)
	{
		Month = string;
	}

	/**
	 * @param string
	 */
	public void setPayAccountName(String string)
	{
		PayAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setPayAccountNo(String string)
	{
		PayAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setPayBankCode(String string)
	{
		PayBankCode = string;
	}

	/**
	 * @param string
	 */
	public void setPayBankName(String string)
	{
		PayBankName = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountName(String string)
	{
		ReceiveAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountNo(String string)
	{
		ReceiveAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveBankCode(String string)
	{
		ReceiveBankCode = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveBankName(String string)
	{
		ReceiveBankName = string;
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

}