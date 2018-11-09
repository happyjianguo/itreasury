/*
 * Created on 2003-10-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loanapply.dataentity;

import java.io.Serializable;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoanExaminePassInfo implements Serializable
{
    private long loanID=-1;
    private double amount=0;
    private double selfAmount=0;
    private double selfScale=0;//承贷比例
    private long loanType=-1;//贷款类型
    private long period=-1;
    private long rateID=-1;
    private double adjustRate=0;
    private double interestRate=0;
    private double agentRate=0;
    private long repayType=-1;
    private String option="";
    private long userID=-1;
    private long inputUserID=-1;
    private long nextUserID=-1;
    private long statusID=-1;
    private long checkLevelID=-1;	//当前的审批级别：1: 最后一级 0其它
    private double staidAdjustRate=0;//固定浮动利率
    private long interestTypeID = -1; //利率类型
    private long liborRateID = -1 ; //Libor利率ID
    
    private InutParameterInfo inutParameterInfo=null;
    
	long OfficeID=-1;
	long CurrencyID=-1;
	
	double mInterestRate=0; //贷款利率实际值
	
	private int adjustRateTerm = 0;

	private long isRemitCompoundInterest 	= -1; //是否计算复利
	private long isRemitOverDueInterest 	= -1; //是否计算罚息
    private double overDueAdjustRate = 0.0 ; //比例浮动
    private double overDueStaidAdjustRate = 0.0 ; //固定浮动点
    private double overDueInterestRate = 0.0 ; //利率
    
    
	
    public long getIsRemitCompoundInterest() {
		return isRemitCompoundInterest;
	}
	public void setIsRemitCompoundInterest(long isRemitCompoundInterest) {
		this.isRemitCompoundInterest = isRemitCompoundInterest;
	}
	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}
	public double getOverDueAdjustRate() {
		return overDueAdjustRate;
	}
	public void setOverDueAdjustRate(double overDueAdjustRate) {
		this.overDueAdjustRate = overDueAdjustRate;
	}
	public double getOverDueStaidAdjustRate() {
		return overDueStaidAdjustRate;
	}
	public void setOverDueStaidAdjustRate(double overDueStaidAdjustRate) {
		this.overDueStaidAdjustRate = overDueStaidAdjustRate;
	}
	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}
	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}
	
	public int getAdjustRateTerm() {
		return adjustRateTerm;
	}
	public void setAdjustRateTerm(int adjustRateTerm) {
		this.adjustRateTerm = adjustRateTerm;
	}
	
	/**
     * @return 返回 interestTypeID。
     */
    public long getInterestTypeID()
    {
        return interestTypeID;
    }
    /** 
     * @param interestTypeID 要设置的 interestTypeID。
     */
    public void setInterestTypeID(long interestTypeID)
    {
        this.interestTypeID = interestTypeID;
    }
    private double liborInterestRate = 0;
    private double liborAdjustRate=0;
    private double liborStaidAdjustRate=0;
    
	/**
	 * @return
	 */
	public double getAdjustRate()
	{
		return adjustRate;
	}

	/**
	 * @return
	 */
	public double getAgentRate()
	{
		return agentRate;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return loanID;
	}

	/**
	 * @return
	 */
	public long getNextUserID()
	{
		return nextUserID;
	}

	/**
	 * @return
	 */
	public String getOption()
	{
		return option;
	}

	/**
	 * @return
	 */
	public long getPeriod()
	{
		return period;
	}

	/**
	 * @return
	 */
	public long getRateID()
	{
		return rateID;
	}

	/**
	 * @return
	 */
	public long getRepayType()
	{
		return repayType;
	}

	/**
	 * @return
	 */
	public double getSelfAmount()
	{
		return selfAmount;
	}

	/**
	 * @return
	 */
	public long getUserID()
	{
		return userID;
	}

	/**
	 * @param d
	 */
	public void setAdjustRate(double d)
	{
		adjustRate = d;
	}

	/**
	 * @param d
	 */
	public void setAgentRate(double d)
	{
		agentRate = d;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		loanID = l;
	}

	/**
	 * @param l
	 */
	public void setNextUserID(long l)
	{
		nextUserID = l;
	}

	/**
	 * @param string
	 */
	public void setOption(String string)
	{
		option = string;
	}

	/**
	 * @param l
	 */
	public void setPeriod(long l)
	{
		period = l;
	}

	/**
	 * @param l
	 */
	public void setRateID(long l)
	{
		rateID = l;
	}

	/**
	 * @param l
	 */
	public void setRepayType(long l)
	{
		repayType = l;
	}

	/**
	 * @param d
	 */
	public void setSelfAmount(double d)
	{
		selfAmount = d;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		userID = l;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @return
	 */
	public long getCheckLevelID()
	{
		return checkLevelID;
	}

	/**
	 * @param l
	 */
	public void setCheckLevelID(long l)
	{
		checkLevelID = l;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		inputUserID = l;
	}

	/**
	 * @return
	 */
	public double getInterestRate()
	{
		return interestRate;
	}

	/**
	 * @param d
	 */
	public void setInterestRate(double d)
	{
		interestRate = d;
	}

    /**
     * function 得到/设置承贷比例
     * return double
     */
    public double getSelfScale()
    {
        return selfScale;
    }

    /**
     * @param d
     * function 得到/设置承贷比例
     * return void
     */
    public void setSelfScale(double d)
    {
        this.selfScale = d;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getLoanType()
    {
        return loanType;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setLoanType(long l)
    {
        this.loanType = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return staidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        staidAdjustRate = d;
    }

    /**
     * @return 返回 liborAdjustRate。
     */
    public double getLiborAdjustRate()
    {
        return liborAdjustRate;
    }
    /**
     * @param liborAdjustRate 要设置的 liborAdjustRate。
     */
    public void setLiborAdjustRate(double liborAdjustRate)
    {
        this.liborAdjustRate = liborAdjustRate;
    }
    /**
     * @return 返回 liborInterestRate。
     */
    public double getLiborInterestRate()
    {
        return liborInterestRate;
    }
    /**
     * @param liborInterestRate 要设置的 liborInterestRate。
     */
    public void setLiborInterestRate(double liborInterestRate)
    {
        this.liborInterestRate = liborInterestRate;
    }
    /**
     * @return 返回 liborStaidAdjustRate。
     */
    public double getLiborStaidAdjustRate()
    {
        return liborStaidAdjustRate;
    }
    /**
     * @param liborStaidAdjustRate 要设置的 liborStaidAdjustRate。
     */
    public void setLiborStaidAdjustRate(double liborStaidAdjustRate)
    {
        this.liborStaidAdjustRate = liborStaidAdjustRate;
    }
    /**
     * @return 返回 liborRateID。
     */
    public long getLiborRateID()
    {
        return liborRateID;
    }
    /**
     * @param liborRateID 要设置的 liborRateID。
     */
    public void setLiborRateID(long liborRateID)
    {
        this.liborRateID = liborRateID;
    }
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public double getMInterestRate() {
		return mInterestRate;
	}
	public void setMInterestRate(double interestRate) {
		mInterestRate = interestRate;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

}
