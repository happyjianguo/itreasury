/* Generated by Together */
package com.iss.itreasury.bill.venture.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.bill.util.BillDataEntity;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BlackBillInfo extends BillDataEntity
{
	private long BillTypeID = -1;
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private String BillCode = "";
	private long StatusID = -1;
	private long InputUserID = -1;
	private Timestamp InputDate = null;
	private Timestamp CreateDate = null;
	private Timestamp MaturityDate = null;
	private String Acceptor = "";
	private String AcceptorAccount = "";
	private String AcceptorBank = "";
	private double BillAmount = 0.0;
	private String Remark = "";
	private long QueryPageCount = -1;//��ҳ��
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
		putUsedField("Acceptor", acceptor);
	}
	/**
	 * @return Returns the acceptorAccount.
	 */
	public String getAcceptorAccount()
	{
		return AcceptorAccount;
	}
	/**
	 * @param acceptorAccount The acceptorAccount to set.
	 */
	public void setAcceptorAccount(String acceptorAccount)
	{
		AcceptorAccount = acceptorAccount;
		putUsedField("AcceptorAccount", acceptorAccount);
	}
	/**
	 * @return Returns the acceptorBank.
	 */
	public String getAcceptorBank()
	{
		return AcceptorBank;
	}
	/**
	 * @param acceptorBank The acceptorBank to set.
	 */
	public void setAcceptorBank(String acceptorBank)
	{
		AcceptorBank = acceptorBank;
		putUsedField("AcceptorBank", acceptorBank);
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
		putUsedField("BillAmount", billAmount);
	}
	/**
	 * @return Returns the billCode.
	 */
	public String getBillCode()
	{
		return BillCode;
	}
	/**
	 * @param billCode The billCode to set.
	 */
	public void setBillCode(String billCode)
	{
		BillCode = billCode;
		putUsedField("BillCode", billCode);
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
		putUsedField("BillTypeID", billTypeID);
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
		putUsedField("CreateDate", createDate);
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
		putUsedField("InputDate", inputDate);
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
		putUsedField("InputUserID", inputUserID);
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
		putUsedField("MaturityDate", maturityDate);
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark()
	{
		return Remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark)
	{
		Remark = remark;
		putUsedField("Remark", remark);
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
		putUsedField("StatusID", statusID);
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
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
		putUsedField("CurrencyID", currencyID);
	}
	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("OfficeID", officeID);
	}
	/**
	 * Returns the queryPageCount.
	 * @return long
	 */
	public long getQueryPageCount()
	{
		return QueryPageCount;
	}

	/**
	 * Sets the queryPageCount.
	 * @param queryPageCount The queryPageCount to set
	 */
	public void setQueryPageCount(long queryPageCount)
	{
		QueryPageCount = queryPageCount;
	}

}
