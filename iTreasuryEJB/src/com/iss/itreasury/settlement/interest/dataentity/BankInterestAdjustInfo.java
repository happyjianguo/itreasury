package com.iss.itreasury.settlement.interest.dataentity;

import java.sql.Timestamp;

public class BankInterestAdjustInfo
{

	private long accountID = -1;    //���˻�ID
	private long subAccountID = -1; //���˻�ID
	private Timestamp backDate = null; //��������
	private long loanRateAdjustPayDetailID = -1; //��LOAN_RATEADJUSTPAYDETAIL��ID

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return accountID;
	}

	/**
	 * @return
	 */
	public Timestamp getBackDate()
	{
		return backDate;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return subAccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		accountID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setBackDate(Timestamp timestamp)
	{
		backDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		subAccountID = l;
	}

	/**
	 * @return Returns the loanRateAdjustPayDetailID.
	 */
	public long getLoanRateAdjustPayDetailID() {
		return loanRateAdjustPayDetailID;
	}

	/**
	 * @param loanRateAdjustPayDetailID The loanRateAdjustPayDetailID to set.
	 */
	public void setLoanRateAdjustPayDetailID(long loanRateAdjustPayDetailID) {
		this.loanRateAdjustPayDetailID = loanRateAdjustPayDetailID;
	}

}