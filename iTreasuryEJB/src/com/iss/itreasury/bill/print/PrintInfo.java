/*
 * Created on 2005-3-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.bill.print;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zntan
 * details for printing
 * 
 */
public class PrintInfo implements Serializable
{
	public PrintInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
//	共用信息
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private long TypeID = -1;//类型（票据类型、凭证类型……）
	private String Code = "";//号码
	private Timestamp ExecuteDate = null; //执行日
	private Timestamp InterestStartDate = null; //起息日
	private String TransNo = ""; //交易号
	private long PayClientID = -1; //付款方客户 ID
	private long PayAccountID = -1; //付款方帐户编号ID
	private long PayBankID = -1; //付款方开户行ID
	private String PayAccount = ""; //付款方帐户名称
	private String PayBank = "";
	private String PayClient = "";
	private long ReceiveClientID = -1; //收款方客户
	private long ReceiveAccountID = -1; //收款方帐户
	private long ReceiveBankID = -1;//
	private String ReceiveAccount = ""; //收款方帐户
	private String ReceiveBank = ""; //收款方开户行
	private String ReceiveClient = "";//
	private double Amount = 0.0; //金额
	private String Abstract = ""; //摘要
	private long InputUserID = -1; //录入人
	private long CheckUserID = -1; //复核人
	private Timestamp StartDate = null; //开始日期
	private Timestamp EndDate = null; //结束日期
	private double Rate = 0.0; //利率
	private double Interest = 0.0;//利息
	private boolean DebitOrCredit = true;//可用于打印类似模板的两张凭证时的选项
	
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
	 * @return Returns the amount.
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
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
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate)
	{
		EndDate = endDate;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		ExecuteDate = executeDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the interestStartDate.
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}
	/**
	 * @param interestStartDate The interestStartDate to set.
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		InterestStartDate = interestStartDate;
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
	 * @return Returns the rate.
	 */
	public double getRate()
	{
		return Rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate)
	{
		Rate = rate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate)
	{
		StartDate = startDate;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
	}
	/**
	 * @return Returns the debitOrCredit.
	 */
	public boolean isDebitOrCredit()
	{
		return DebitOrCredit;
	}
	/**
	 * @param debitOrCredit The debitOrCredit to set.
	 */
	public void setDebitOrCredit(boolean debitOrCredit)
	{
		DebitOrCredit = debitOrCredit;
	}
	/**
	 * @return Returns the payBankID.
	 */
	public long getPayBankID()
	{
		return PayBankID;
	}
	/**
	 * @param payBankID The payBankID to set.
	 */
	public void setPayBankID(long payBankID)
	{
		PayBankID = payBankID;
	}
	/**
	 * @return Returns the payClientID.
	 */
	public long getPayClientID()
	{
		return PayClientID;
	}
	/**
	 * @param payClientID The payClientID to set.
	 */
	public void setPayClientID(long payClientID)
	{
		PayClientID = payClientID;
	}
	/**
	 * @return Returns the receiveAccountID.
	 */
	public long getReceiveAccountID()
	{
		return ReceiveAccountID;
	}
	/**
	 * @param receiveAccountID The receiveAccountID to set.
	 */
	public void setReceiveAccountID(long receiveAccountID)
	{
		ReceiveAccountID = receiveAccountID;
	}
	/**
	 * @return Returns the receiveClientID.
	 */
	public long getReceiveClientID()
	{
		return ReceiveClientID;
	}
	/**
	 * @param receiveClientID The receiveClientID to set.
	 */
	public void setReceiveClientID(long receiveClientID)
	{
		ReceiveClientID = receiveClientID;
	}
	/**
	 * @return Returns the payAccount.
	 */
	public String getPayAccount()
	{
		return PayAccount;
	}
	/**
	 * @param payAccount The payAccount to set.
	 */
	public void setPayAccount(String payAccount)
	{
		PayAccount = payAccount;
	}
	/**
	 * @return Returns the payBank.
	 */
	public String getPayBank()
	{
		return PayBank;
	}
	/**
	 * @param payBank The payBank to set.
	 */
	public void setPayBank(String payBank)
	{
		PayBank = payBank;
	}
	/**
	 * @return Returns the payClient.
	 */
	public String getPayClient()
	{
		return PayClient;
	}
	/**
	 * @param payClient The payClient to set.
	 */
	public void setPayClient(String payClient)
	{
		PayClient = payClient;
	}
	/**
	 * @return Returns the receiveAccount.
	 */
	public String getReceiveAccount()
	{
		return ReceiveAccount;
	}
	/**
	 * @param receiveAccount The receiveAccount to set.
	 */
	public void setReceiveAccount(String receiveAccount)
	{
		ReceiveAccount = receiveAccount;
	}
	/**
	 * @return Returns the receiveBank.
	 */
	public String getReceiveBank()
	{
		return ReceiveBank;
	}
	/**
	 * @param receiveBank The receiveBank to set.
	 */
	public void setReceiveBank(String receiveBank)
	{
		ReceiveBank = receiveBank;
	}
	/**
	 * @return Returns the receiveClient.
	 */
	public String getReceiveClient()
	{
		return ReceiveClient;
	}
	/**
	 * @param receiveClient The receiveClient to set.
	 */
	public void setReceiveClient(String receiveClient)
	{
		ReceiveClient = receiveClient;
	}
	/**
	 * @return Returns the payAccountID.
	 */
	public long getPayAccountID()
	{
		return PayAccountID;
	}
	/**
	 * @param payAccountID The payAccountID to set.
	 */
	public void setPayAccountID(long payAccountID)
	{
		PayAccountID = payAccountID;
	}
	/**
	 * @return Returns the receiveBankID.
	 */
	public long getReceiveBankID()
	{
		return ReceiveBankID;
	}
	/**
	 * @param receiveBankID The receiveBankID to set.
	 */
	public void setReceiveBankID(long receiveBankID)
	{
		ReceiveBankID = receiveBankID;
	}
	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID()
	{
		return TypeID;
	}
	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID)
	{
		TypeID = typeID;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode()
	{
		return Code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code)
	{
		Code = code;
	}
	/**
	 * @return Returns the interest.
	 */
	public double getInterest()
	{
		return Interest;
	}
	/**
	 * @param interest The interest to set.
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}
}
