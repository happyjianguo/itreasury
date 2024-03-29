/* Generated by Together */
package com.iss.itreasury.bill.venture.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.bill.util.BillDataEntity;
/**
 * @author gqzhang
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BlackConditionInfo extends BillDataEntity
{
	/**
	 *票据类型
	 */
	private long BillTypeID = -1;
	/**
	 *办事处
	 */
	private long OfficeID = -1;
	/**
	 *币种
	 */
	private long CurrencyID = -1;
	/**
	 *票据号字符串
	 */
	private String BillCodeString = "";
	/**
	 *票据号数字部分的长度
	 */
	private long BillNumberLength = -1;
	/**
	 *票据号数字部分起号
	 */
	private long BillCodeStart = -1;
	/**
	 *票据号数字部分止号
	 */
	private long BillCodeEnd = -1;
	/**
	 *出票日期
	 */
	private Timestamp CreateDate = null;
	/**
	 *到期日期
	 */
	private Timestamp MaturityDate = null;
	/**
	 *承兑人
	 */
	private String Acceptor = "";
	/**
	 *承兑人帐号
	 */
	private String AcceptorAccount = "";
	/**
	 *承兑银行
	 */
	private String AcceptorBank = "";
	/**
	 *票面金额
	 */
	private double BillAmount = 0.0;
	/**
	 *备注
	 */
	private String Remark = "";
	/*
	 * 录入人
	 */
	private long InputUserID = -1;
	/*
	 * 录入日期
	 */
	private Timestamp InputDate = null;
	/*
	 * 状态
	 */
	private long StatusID = -1;
	/**
	 * @return Returns the acceptor.
	 */
	public String getAcceptor()
	{
		return Acceptor;
	}
	/**
	 * @param acceptor The acceptor to set.
	 */
	public void setAcceptor(String acceptor)
	{
		Acceptor = acceptor;
	}
	/**
	 * @return Returns the billAmount.
	 */
	public double getBillAmount()
	{
		return BillAmount;
	}
	/**
	 * @param billAmount The billAmount to set.
	 */
	public void setBillAmount(double billAmount)
	{
		BillAmount = billAmount;
	}
	/**
	 * @return Returns the billCodeEnd.
	 */
	public long getBillCodeEnd()
	{
		return BillCodeEnd;
	}
	/**
	 * @param billCodeEnd The billCodeEnd to set.
	 */
	public void setBillCodeEnd(long billCodeEnd)
	{
		BillCodeEnd = billCodeEnd;
	}
	/**
	 * @return Returns the billCodeStart.
	 */
	public long getBillCodeStart()
	{
		return BillCodeStart;
	}
	/**
	 * @param billCodeStart The billCodeStart to set.
	 */
	public void setBillCodeStart(long billCodeStart)
	{
		BillCodeStart = billCodeStart;
	}
	/**
	 * @return Returns the billCodeString.
	 */
	public String getBillCodeString()
	{
		return BillCodeString;
	}
	/**
	 * @param billCodeString The billCodeString to set.
	 */
	public void setBillCodeString(String billCodeString)
	{
		BillCodeString = billCodeString;
	}
	/**
	 * @param billNumberLength The billNumberLength to set.
	 */
	public void setBillNumberLength(int billNumberLength)
	{
		BillNumberLength = billNumberLength;
	}
	/**
	 * @return Returns the billTypeID.
	 */
	public long getBillTypeID()
	{
		return BillTypeID;
	}
	/**
	 * @param billTypeID The billTypeID to set.
	 */
	public void setBillTypeID(long billTypeID)
	{
		BillTypeID = billTypeID;
	}
	/**
	 * @return Returns the createDate.
	 */
	public Timestamp getCreateDate()
	{
		return CreateDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Timestamp createDate)
	{
		CreateDate = createDate;
	}
	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate()
	{
		return MaturityDate;
	}
	/**
	 * @param maturityDate The maturityDate to set.
	 */
	public void setMaturityDate(Timestamp maturityDate)
	{
		MaturityDate = maturityDate;
	}
	/**
	 * Returns the acceptorAccount.
	 * @return String
	 */
	public String getAcceptorAccount()
	{
		return AcceptorAccount;
	}
	/**
	 * Returns the acceptorBank.
	 * @return String
	 */
	public String getAcceptorBank()
	{
		return AcceptorBank;
	}
	/**
	 * Returns the billNumberLength.
	 * @return long
	 */
	public long getBillNumberLength()
	{
		return BillNumberLength;
	}
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * Returns the remark.
	 * @return String
	 */
	public String getRemark()
	{
		return Remark;
	}
	/**
	 * Sets the acceptorAccount.
	 * @param acceptorAccount The acceptorAccount to set
	 */
	public void setAcceptorAccount(String acceptorAccount)
	{
		AcceptorAccount = acceptorAccount;
	}
	/**
	 * Sets the acceptorBank.
	 * @param acceptorBank The acceptorBank to set
	 */
	public void setAcceptorBank(String acceptorBank)
	{
		AcceptorBank = acceptorBank;
	}
	/**
	 * Sets the billNumberLength.
	 * @param billNumberLength The billNumberLength to set
	 */
	public void setBillNumberLength(long billNumberLength)
	{
		BillNumberLength = billNumberLength;
	}
	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
	/**
	 * Sets the remark.
	 * @param remark The remark to set
	 */
	public void setRemark(String remark)
	{
		Remark = remark;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}
	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}

}
