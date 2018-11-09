package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-1
 */
public class DailyAccountBalanceInfo implements Serializable {
    /**
     * @param subAccountStatusid The subAccountStatusid to set.
     */
    public void setSubAccountStatusid(long subAccountStatusid)
    {
        this.subAccountStatusid = subAccountStatusid;
    }
	private long accountID = -1;
	private long subAccountID = -1;
	private Timestamp date = null;
	private long accountStatusID = -1;
	private double balance = 0.0;
	private double interestBalance = 0.0;	
	private double interestRate = 0.0;
	private double dailyInterest = 0.0;
	private double interest = 0.0;	
	private double ac_mNegotiateBalance = 0.0;
	private double ac_mNegotiateRate = 0.0;
	private double ac_mDailyNegotiateInterest = 0.0;
	private double ac_mNegotiateInterest = 0.0;
	private double al_mArrearageInterest = 0.0;
	/*
	 * 新增字段，逾期金额，贷款专用
	 *　刘惠军　2004-1-12
	 * 本日的逾期金额，用于计算逾期罚息，逾期金额　＝　贷款余额　＋ 逾期欠息　
	 */
	private double al_mOverDueAmount = 0.0; 
	private long subAccountStatusid = -1;
	private double freezeAmount = 0.0;
	
	private double mforfeitdailyinterset = 0.0;		//每日罚息
	private double mforfeitinterest = 0.0;			//罚息
	private double mcompounddailyinterset = 0.0;	//每日复利
	private double mcompoundinterest = 0.0;			//复利
	//added by xwhe 2008-12-12 积数
	private double mCurrensInterestbalance = 0.0;
	private double mAc_mnegoInterestbalance = 0.0;
	/*
	 * 新增字段
	 */
	private  Timestamp startDate = null;
	
	private double mForfeitInterestRate = 0.0; //罚息利率
	
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the ac_mDailyNegotiateInterest.
	 * @return double
	 */
	public double getAc_mDailyNegotiateInterest() {
		return ac_mDailyNegotiateInterest;
	}

	/**
	 * Returns the ac_mNegotiateBalance.
	 * @return double
	 */
	public double getAc_mNegotiateBalance() {
		return ac_mNegotiateBalance;
	}

	/**
	 * Returns the ac_mNegotiateInterest.
	 * @return double
	 */
	public double getAc_mNegotiateInterest() {
		return ac_mNegotiateInterest;
	}

	/**
	 * Returns the ac_mNegotiateRate.
	 * @return double
	 */
	public double getAc_mNegotiateRate() {
		return ac_mNegotiateRate;
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Returns the accountStatusID.
	 * @return long
	 */
	public long getAccountStatusID() {
		return accountStatusID;
	}

	/**
	 * Returns the al_mArrearageInterest.
	 * @return double
	 */
	public double getAl_mArrearageInterest() {
		return al_mArrearageInterest;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Returns the dailyInterest.
	 * @return double
	 */
	public double getDailyInterest() {
		return dailyInterest;
	}

	/**
	 * Returns the date.
	 * @return Timestamp
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest() {
		return interest;
	}

	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID() {
		return subAccountID;
	}

	/**
	 * Sets the ac_mDailyNegotiateInterest.
	 * @param ac_mDailyNegotiateInterest The ac_mDailyNegotiateInterest to set
	 */
	public void setAc_mDailyNegotiateInterest(double ac_mDailyNegotiateInterest) {
		this.ac_mDailyNegotiateInterest = ac_mDailyNegotiateInterest;
	}

	/**
	 * Sets the ac_mNegotiateBalance.
	 * @param ac_mNegotiateBalance The ac_mNegotiateBalance to set
	 */
	public void setAc_mNegotiateBalance(double ac_mNegotiateBalance) {
		this.ac_mNegotiateBalance = ac_mNegotiateBalance;
	}

	/**
	 * Sets the ac_mNegotiateInterest.
	 * @param ac_mNegotiateInterest The ac_mNegotiateInterest to set
	 */
	public void setAc_mNegotiateInterest(double ac_mNegotiateInterest) {
		this.ac_mNegotiateInterest = ac_mNegotiateInterest;
	}

	/**
	 * Sets the ac_mNegotiateRate.
	 * @param ac_mNegotiateRate The ac_mNegotiateRate to set
	 */
	public void setAc_mNegotiateRate(double ac_mNegotiateRate) {
		this.ac_mNegotiateRate = ac_mNegotiateRate;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	/**
	 * Sets the accountStatusID.
	 * @param accountStatusID The accountStatusID to set
	 */
	public void setAccountStatusID(long accountStatusID) {
		this.accountStatusID = accountStatusID;
	}

	/**
	 * Sets the al_mArrearageInterest.
	 * @param al_mArrearageInterest The al_mArrearageInterest to set
	 */
	public void setAl_mArrearageInterest(double al_mArrearageInterest) {
		this.al_mArrearageInterest = al_mArrearageInterest;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Sets the dailyInterest.
	 * @param dailyInterest The dailyInterest to set
	 */
	public void setDailyInterest(double dailyInterest) {
		this.dailyInterest = dailyInterest;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest) {
		this.interest = interest;
	}

	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID) {
		this.subAccountID = subAccountID;
	}

	/**
	 * Returns the interestBalance.
	 * @return double
	 */
	public double getInterestBalance() {
		return interestBalance;
	}

	/**
	 * Sets the interestBalance.
	 * @param interestBalance The interestBalance to set
	 */
	public void setInterestBalance(double interestBalance) {
		this.interestBalance = interestBalance;
	}

	/**
	 * @return 逾期金额
	 */
	public double getAl_mOverDueAmount()
	{
		return al_mOverDueAmount;
	}

	/**
	 * 逾期金额
	 * @param d
	 */
	public void setAl_mOverDueAmount(double d)
	{
		al_mOverDueAmount = d;
	}

    /**
     * @return Returns the freezeAmount.
     */
    public double getFreezeAmount()
    {
        return freezeAmount;
    }
    /**
     * @param freezeAmount The freezeAmount to set.
     */
    public void setFreezeAmount(double freezeAmount)
    {
        this.freezeAmount = freezeAmount;
    }
    /**
     * @return Returns the subAccountStatusid.
     */
    public long getSubAccountStatusid()
    {
        return subAccountStatusid;
    }

	public double getMcompounddailyinterset() {
		return mcompounddailyinterset;
	}

	public void setMcompounddailyinterset(double mcompounddailyinterset) {
		this.mcompounddailyinterset = mcompounddailyinterset;
	}

	public double getMcompoundinterest() {
		return mcompoundinterest;
	}

	public void setMcompoundinterest(double mcompoundinterest) {
		this.mcompoundinterest = mcompoundinterest;
	}

	public double getMforfeitdailyinterset() {
		return mforfeitdailyinterset;
	}

	public void setMforfeitdailyinterset(double mforfeitdailyinterset) {
		this.mforfeitdailyinterset = mforfeitdailyinterset;
	}

	public double getMforfeitinterest() {
		return mforfeitinterest;
	}

	public void setMforfeitinterest(double mforfeitinterest) {
		this.mforfeitinterest = mforfeitinterest;
	}

	public double getMAc_mnegoInterestbalance() {
		return mAc_mnegoInterestbalance;
	}

	public void setMAc_mnegoInterestbalance(double ac_mnegoInterestbalance) {
		mAc_mnegoInterestbalance = ac_mnegoInterestbalance;
	}

	public double getMCurrensInterestbalance() {
		return mCurrensInterestbalance;
	}

	public void setMCurrensInterestbalance(double currensInterestbalance) {
		mCurrensInterestbalance = currensInterestbalance;
	}

	public double getMForfeitInterestRate() {
		return mForfeitInterestRate;
	}

	public void setMForfeitInterestRate(double mforfeitInterestRate) {
		this.mForfeitInterestRate = mforfeitInterestRate;
	}
	
	
}
