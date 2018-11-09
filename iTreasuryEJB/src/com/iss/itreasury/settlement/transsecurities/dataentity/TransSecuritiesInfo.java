/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transsecurities.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author xrli
 *	证券交易实体类：
 *	1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *	2、包含变量、类型、默认值、说明
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransSecuritiesInfo implements Serializable
{
	//主信息
	private long ID = -1; //唯一标识
	private String TransNo = ""; //交易编号
	private long TransactionTypeID = -1; //交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	private long OfficeID = -1; //办事处标识
	private long CurrencyID = -1; //币种标识
	private long SecuritiesNoticeID = -1; //证券业务通知单标识
	private String SecuritiesNoticeNo = ""; //证券业务通知单号
	private String SecuritiesTransaction = ""; //证券交易类型
	private String ExtAccountNo = ""; //	非财务公司账户号	  
	private String ExtClientName = ""; //非财务公司客户名称	  
	private String RemitInProvince = ""; //非财务公司汇入地(省)：	  
	private String RemitInCity = ""; //非财务公司汇入地(市)：	  
	private String RemitInBank = ""; //非财务公司汇入银行名称
	private long BankID = -1; //	收/付款银行账户ID
	private String FormNo = ""; //报单号
	private String BankChequeNo = ""; //银行支票号
	private double ReceiveAmount = 0.0; //应收金额 
	private double Amount = 0.0; //交易金额/实收金额	
	private Timestamp Date = null; //应付日期
	private Timestamp SettlementDate = null; //结算开机日	
	private Timestamp ExecuteDate = null; //执行日
	private Timestamp InputDate = null; //录入日	
	private Timestamp ModifyDate = null; //修改时间：时分秒	
	private long AbstractID = -1; //摘要ID
	private String Abstract = ""; //摘要	
	private long InputUserID = -1; //录入人ID
	private String InputUserName = ""; //录入人名称
	private long CheckUserID = -1; //复核人ID
	private String CheckUserName = ""; //复核人名称
	private String CheckAbstract = ""; //复核/取消复核摘要
	private long StatusID = -1; //交易状态
	private long CurrentAccountID = -1; //收/付活期账户
	private String CounterPartName = "";//交易对手名称
	
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
	public long getAbstractID()
	{
		return AbstractID;
	}
	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * @return
	 */
	public String getBankChequeNo()
	{
		return BankChequeNo;
	}
	/**
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}
	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
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
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}
	/**
	 * @return
	 */
	public String getFormNo()
	{
		return FormNo;
	}
	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}
	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
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
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @return
	 */
	public double getReceiveAmount()
	{
		return ReceiveAmount;
	}
	/**
	 * @return
	 */
	public long getSecuritiesNoticeID()
	{
		return SecuritiesNoticeID;
	}
	/**
	 * @return
	 */
	public String getSecuritiesTransaction()
	{
		return SecuritiesTransaction;
	}
	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}
	/**
	 * @param l
	 */
	public void setAbstractID(long l)
	{
		AbstractID = l;
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}
	/**
	 * @param string
	 */
	public void setBankChequeNo(String string)
	{
		BankChequeNo = string;
	}
	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}
	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}
	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}
	/**
	 * @param string
	 */
	public void setFormNo(String string)
	{
		FormNo = string;
	}
	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}
	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}
	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}
	/**
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		ModifyDate = timestamp;
	}
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}
	/**
	 * @param d
	 */
	public void setReceiveAmount(double d)
	{
		ReceiveAmount = d;
	}
	/**
	 * @param l
	 */
	public void setSecuritiesNoticeID(long l)
	{
		SecuritiesNoticeID = l;
	}
	/**
	 * @param string
	 */
	public void setSecuritiesTransaction(String string)
	{
		SecuritiesTransaction = string;
	}
	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}
	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}
	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}
	/**
	 * @return
	 */
	public long getBankID()
	{
		return BankID;
	}
	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}
	/**
	 * @return
	 */
	public String getExtAccountNo()
	{
		return ExtAccountNo;
	}
	/**
	 * @return
	 */
	public String getExtClientName()
	{
		return ExtClientName;
	}
	/**
	 * @return
	 */
	public String getRemitInBank()
	{
		return RemitInBank;
	}
	/**
	 * @return
	 */
	public String getRemitInCity()
	{
		return RemitInCity;
	}
	/**
	 * @return
	 */
	public String getRemitInProvince()
	{
		return RemitInProvince;
	}
	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}
	/**
	 * @param string
	 */
	public void setExtAccountNo(String string)
	{
		ExtAccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setExtClientName(String string)
	{
		ExtClientName = string;
	}
	/**
	 * @param string
	 */
	public void setRemitInBank(String string)
	{
		RemitInBank = string;
	}
	/**
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		RemitInCity = string;
	}
	/**
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		RemitInProvince = string;
	}
	/**
	 * @return
	 */
	public Timestamp getSettlementDate()
	{
		return SettlementDate;
	}
	/**
	 * @param timestamp
	 */
	public void setSettlementDate(Timestamp timestamp)
	{
		SettlementDate = timestamp;
	}
	/**
	 * @return
	 */
	public String getSecuritiesNoticeNo()
	{
		return SecuritiesNoticeNo;
	}
	/**
	 * @param string
	 */
	public void setSecuritiesNoticeNo(String string)
	{
		SecuritiesNoticeNo = string;
	}
	/**
	 * @return Returns the currentAccountID.
	 */
	public long getCurrentAccountID()
	{
		return CurrentAccountID;
	}
	/**
	 * @param currentAccountID The currentAccountID to set.
	 */
	public void setCurrentAccountID(long currentAccountID)
	{
		CurrentAccountID = currentAccountID;
	}
	/**
	 * @return Returns the counterPartName.
	 */
	public String getCounterPartName()
	{
		return CounterPartName;
	}
	/**
	 * @param counterPartName The counterPartName to set.
	 */
	public void setCounterPartName(String counterPartName)
	{
		CounterPartName = counterPartName;
	}
}
