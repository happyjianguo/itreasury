/* Generated by Together */

package com.iss.itreasury.bill.blankvoucher.dataentity;

import java.sql.Timestamp;

public class BlankReturnInfo extends BlankTransDetailInfo {
	private long BillTypeID = -1;
	private long BillID = -1;
	private double BillAmount = 0.0;
	private String BillPassword = "";
	private String ReturnReason = "";
	private String ReturnReasonDocNo = "";
	
	//�����ݿ��ֶ�
	private long OfficeID = -1;//���´�
	private long CurrencyID = -1;//����
	private long UserID = -1;//��ǰ��½��
	private Timestamp InputDate = null;//¼������
	private String Summary = "";//ժҪ
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
		putUsedField("billAmount",billAmount);
		
	}
	/**
	 * @return Returns the billID.
	 */
	public long getBillID()
	{
		return BillID;
	}
	/**
	 * @param billID The billID to set.
	 */
	public void setBillID(long billID)
	{
		BillID = billID;
		putUsedField("billID",billID);
	}
	/**
	 * @return Returns the billPassword.
	 */
	public String getBillPassword()
	{
		return BillPassword;
	}
	/**
	 * @param billPassword The billPassword to set.
	 */
	public void setBillPassword(String billPassword)
	{
		BillPassword = billPassword;
		putUsedField("billPassword",billPassword);
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
		putUsedField("billTypeID",billTypeID);
	}
	/**
	 * @return Returns the returnReason.
	 */
	public String getReturnReason()
	{
		return ReturnReason;
	}
	/**
	 * @param returnReason The returnReason to set.
	 */
	public void setReturnReason(String returnReason)
	{
		ReturnReason = returnReason;
		putUsedField("returnReason",returnReason);
	}
	/**
	 * @return Returns the returnReasonDocNo.
	 */
	public String getReturnReasonDocNo()
	{
		return ReturnReasonDocNo;
	}
	/**
	 * @param returnReasonDocNo The returnReasonDocNo to set.
	 */
	public void setReturnReasonDocNo(String returnReasonDocNo)
	{
		ReturnReasonDocNo = returnReasonDocNo;
		putUsedField("returnReasonDocNo",returnReasonDocNo);
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
	 * @return Returns the summary.
	 */
	public String getSummary()
	{
		return Summary;
	}
	/**
	 * @param summary The summary to set.
	 */
	public void setSummary(String summary)
	{
		Summary = summary;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID()
	{
		return UserID;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(long userID)
	{
		UserID = userID;
	}
}