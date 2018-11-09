/*
 * Created on 2003-9-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubAccountFixedInfo implements Serializable
{
	private long ID = -1; //子账户ID
	private long AccountID = -1; //主账户ID
	private double Interest = 0.0; //当前利息
	private double Balance = 0.0; //账户当前余额
	private double OpenAmount = 0.0; //开户金额
	private Timestamp OpenDate = null; //开户日期
	private Timestamp FinishDate = null; //销户日期
	private long IsInterest = -1; //是否计息
	private Timestamp ClearInterestDate = null; //结息日期
	private long StatusID = -1; //状态

	private String DepositNo = ""; //存单号
	private double Rate = 0.0; //利率
	private Timestamp StartDate = null; //开始日期
	private Timestamp EndDate = null; //结束日期
	private double PreDrawInterest = 0.0; //计提利息
	private Timestamp PreDrawDate = null; //计提日期
	private long DepositTerm = -1; //定期存款期限（月）
	private long InterestPlanID = -1; //利率计划
	private long NoticeDay = -1; //通知存款支取日期（天）
	private long InterestAccountID = -1; //收利息账户ID
	private String SealNo = ""; //印鉴卡号
	private long SealBankID = -1; //印鉴卡发放银行ID
	private double DailyUncheckAmount = 0.0; // 当天累计未复核付款金额
	private long IsAutoContinue = -1;
	private long AutoContinueType = -1;
	
	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * Returns the depositNo.
	 * @return String
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}

	/**
	 * Returns the depositTerm.
	 * @return long
	 */
	public long getDepositTerm()
	{
		return DepositTerm;
	}

	/**
	 * Returns the endDate.
	 * @return Timestamp
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * Returns the interestAccountID.
	 * @return long
	 */
	public long getInterestAccountID()
	{
		return InterestAccountID;
	}

	/**
	 * Returns the interestPlanID.
	 * @return long
	 */
	public long getInterestPlanID()
	{
		return InterestPlanID;
	}

	/**
	 * Returns the isInterest.
	 * @return long
	 */
	public long getIsInterest()
	{
		return IsInterest;
	}

	/**
	 * Returns the noticeDay.
	 * @return long
	 */
	public long getNoticeDay()
	{
		return NoticeDay;
	}

	/**
	 * Returns the preDrawDate.
	 * @return Timestamp
	 */
	public Timestamp getPreDrawDate()
	{
		return PreDrawDate;
	}

	/**
	 * Returns the preDrawInterest.
	 * @return double
	 */
	public double getPreDrawInterest()
	{
		return PreDrawInterest;
	}

	/**
	 * Returns the rate.
	 * @return double
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * Returns the sealBankID.
	 * @return long
	 */
	public long getSealBankID()
	{
		return SealBankID;
	}

	/**
	 * Returns the sealNo.
	 * @return String
	 */
	public String getSealNo()
	{
		return SealNo;
	}

	/**
	 * Returns the startDate.
	 * @return Timestamp
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
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
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}

	/**
	 * Sets the depositNo.
	 * @param depositNo The depositNo to set
	 */
	public void setDepositNo(String depositNo)
	{
		DepositNo = depositNo;
	}

	/**
	 * Sets the depositTerm.
	 * @param depositTerm The depositTerm to set
	 */
	public void setDepositTerm(long depositTerm)
	{
		DepositTerm = depositTerm;
	}

	/**
	 * Sets the endDate.
	 * @param endDate The endDate to set
	 */
	public void setEndDate(Timestamp endDate)
	{
		EndDate = endDate;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}

	/**
	 * Sets the interestAccountID.
	 * @param interestAccountID The interestAccountID to set
	 */
	public void setInterestAccountID(long interestAccountID)
	{
		InterestAccountID = interestAccountID;
	}

	/**
	 * Sets the interestPlanID.
	 * @param interestPlanID The interestPlanID to set
	 */
	public void setInterestPlanID(long interestPlanID)
	{
		InterestPlanID = interestPlanID;
	}

	/**
	 * Sets the isInterest.
	 * @param isInterest The isInterest to set
	 */
	public void setIsInterest(long isInterest)
	{
		IsInterest = isInterest;
	}

	/**
	 * Sets the noticeDay.
	 * @param noticeDay The noticeDay to set
	 */
	public void setNoticeDay(long noticeDay)
	{
		NoticeDay = noticeDay;
	}

	/**
	 * Sets the preDrawDate.
	 * @param preDrawDate The preDrawDate to set
	 */
	public void setPreDrawDate(Timestamp preDrawDate)
	{
		PreDrawDate = preDrawDate;
	}

	/**
	 * Sets the preDrawInterest.
	 * @param preDrawInterest The preDrawInterest to set
	 */
	public void setPreDrawInterest(double preDrawInterest)
	{
		PreDrawInterest = preDrawInterest;
	}

	/**
	 * Sets the rate.
	 * @param rate The rate to set
	 */
	public void setRate(double rate)
	{
		Rate = rate;
	}

	/**
	 * Sets the sealBankID.
	 * @param sealBankID The sealBankID to set
	 */
	public void setSealBankID(long sealBankID)
	{
		SealBankID = sealBankID;
	}

	/**
	 * Sets the sealNo.
	 * @param sealNo The sealNo to set
	 */
	public void setSealNo(String sealNo)
	{
		SealNo = sealNo;
	}

	/**
	 * Sets the startDate.
	 * @param startDate The startDate to set
	 */
	public void setStartDate(Timestamp startDate)
	{
		StartDate = startDate;
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
	 * @return
	 */
	public double getDailyUncheckAmount()
	{
		return this.DailyUncheckAmount;
	}

	/**
	 * @param dailyUncheckAmount
	 */
	public void setDailyUncheckAmount(double dailyUncheckAmount)
	{
		this.DailyUncheckAmount = dailyUncheckAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @return
	 */
	public double getOpenAmount()
	{
		return OpenAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		FinishDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setOpenAmount(double d)
	{
		OpenAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp)
	{
		OpenDate = timestamp;
	}

	public long getIsAutoContinue() {
		return IsAutoContinue;
	}

	public void setIsAutoContinue(long isAutoContinue) {
		IsAutoContinue = isAutoContinue;
	}

	public long getAutoContinueType() {
		return AutoContinueType;
	}

	public void setAutoContinueType(long autoContinueType) {
		AutoContinueType = autoContinueType;
	}

}
