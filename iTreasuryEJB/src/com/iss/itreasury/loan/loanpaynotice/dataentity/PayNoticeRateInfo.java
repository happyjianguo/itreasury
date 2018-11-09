package com.iss.itreasury.loan.loanpaynotice.dataentity;

import java.sql.Timestamp;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PayNoticeRateInfo implements java.io.Serializable
{

	public static void main(String[] args)
	{
	}
 
    //利率
	private double InterestRate = 0;
	//利率类型（银行利率、手续费率等）
	private long RateType = 0;
	//计息方式（年息、月息等）
	private long RateStyle = 0;
	
	private long BankInterestID = -1; //未调整的基准利率ID
	private long LateBankInterestID = -1; //调整后的基准利率ID
	private double Rate = 0; //未调整的利率
	private double LateRate = 0; //调整后的利率
	private double BasicRate = 0; //	未调整的基准利率
	private double LateBasicRate = 0; //调整后的基准利率
	private Timestamp AdjustDate = null; //调整日期
    
    private double AdjustRate = 0; //未调整的调整比例
    private double LateAdjustRate = 0; //调整后的利率
    private double StaidAdjustRate = 0; //未调整的浮动固定利率
    private double LateStaidAdjustRate = 0; //调整后的浮动固定利率
    
    private long LiborRateID = -1;	//Libor利率ID
    private String LiborName = "";	//Libor利率名称
    private long LiborIntervalNum = 0;	//Libor利率期限
    
    private String LateRateString = "";	//执行利率字符串（银行利率/Libor利率）
    
    private double overDueAdjustRate = 0; //调整比例
    private double overDueStaidAdjustRate = 0; //浮动固定利率
    private long isRemitOverDueInterest = 0; //是否罚息
	private double overDueInterestRate = 0; //罚息利率
	
	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate()
	{
		return InterestRate;
	}

	/**
	 * Returns the rateStyle.
	 * @return long
	 */
	public long getRateStyle()
	{
		return RateStyle;
	}

	/**
	 * Returns the rateType.
	 * @return long
	 */
	public long getRateType()
	{
		return RateType;
	}

	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate)
	{
		InterestRate = interestRate;
	}

	/**
	 * Sets the rateStyle.
	 * @param rateStyle The rateStyle to set
	 */
	public void setRateStyle(long rateStyle)
	{
		RateStyle = rateStyle;
	}

	/**
	 * Sets the rateType.
	 * @param rateType The rateType to set
	 */
	public void setRateType(long rateType)
	{
		RateType = rateType;
	}

    /**
     * @return Returns the adjustDate.
     */
    public Timestamp getAdjustDate()
    {
        return AdjustDate;
    }
    /**
     * @param adjustDate The adjustDate to set.
     */
    public void setAdjustDate(Timestamp adjustDate)
    {
        AdjustDate = adjustDate;
    }
    /**
     * @return Returns the adjustRate.
     */
    public double getAdjustRate()
    {
        return AdjustRate;
    }
    /**
     * @param adjustRate The adjustRate to set.
     */
    public void setAdjustRate(double adjustRate)
    {
        AdjustRate = adjustRate;
    }
    /**
     * @return Returns the bankInterestID.
     */
    public long getBankInterestID()
    {
        return BankInterestID;
    }
    /**
     * @param bankInterestID The bankInterestID to set.
     */
    public void setBankInterestID(long bankInterestID)
    {
        BankInterestID = bankInterestID;
    }
    /**
     * @return Returns the basicRate.
     */
    public double getBasicRate()
    {
        return BasicRate;
    }
    /**
     * @param basicRate The basicRate to set.
     */
    public void setBasicRate(double basicRate)
    {
        BasicRate = basicRate;
    }
    /**
     * @return Returns the lateAdjustRate.
     */
    public double getLateAdjustRate()
    {
        return LateAdjustRate;
    }
    /**
     * @param lateAdjustRate The lateAdjustRate to set.
     */
    public void setLateAdjustRate(double lateAdjustRate)
    {
        LateAdjustRate = lateAdjustRate;
    }
    /**
     * @return Returns the lateBankInterestID.
     */
    public long getLateBankInterestID()
    {
        return LateBankInterestID;
    }
    /**
     * @param lateBankInterestID The lateBankInterestID to set.
     */
    public void setLateBankInterestID(long lateBankInterestID)
    {
        LateBankInterestID = lateBankInterestID;
    }
    /**
     * @return Returns the lateBasicRate.
     */
    public double getLateBasicRate()
    {
        return LateBasicRate;
    }
    /**
     * @param lateBasicRate The lateBasicRate to set.
     */
    public void setLateBasicRate(double lateBasicRate)
    {
        LateBasicRate = lateBasicRate;
    }
    /**
     * @return Returns the lateRate.
     */
    public double getLateRate()
    {
        return LateRate;
    }
    /**
     * @param lateRate The lateRate to set.
     */
    public void setLateRate(double lateRate)
    {
        LateRate = lateRate;
    }
    /**
     * @return Returns the lateRateString.
     */
    public String getLateRateString()
    {
        return LateRateString;
    }
    /**
     * @param lateRateString The lateRateString to set.
     */
    public void setLateRateString(String lateRateString)
    {
        LateRateString = lateRateString;
    }
    /**
     * @return Returns the lateStaidAdjustRate.
     */
    public double getLateStaidAdjustRate()
    {
        return LateStaidAdjustRate;
    }
    /**
     * @param lateStaidAdjustRate The lateStaidAdjustRate to set.
     */
    public void setLateStaidAdjustRate(double lateStaidAdjustRate)
    {
        LateStaidAdjustRate = lateStaidAdjustRate;
    }
    /**
     * @return Returns the liborIntervalNum.
     */
    public long getLiborIntervalNum()
    {
        return LiborIntervalNum;
    }
    /**
     * @param liborIntervalNum The liborIntervalNum to set.
     */
    public void setLiborIntervalNum(long liborIntervalNum)
    {
        LiborIntervalNum = liborIntervalNum;
    }
    /**
     * @return Returns the liborName.
     */
    public String getLiborName()
    {
        return LiborName;
    }
    /**
     * @param liborName The liborName to set.
     */
    public void setLiborName(String liborName)
    {
        LiborName = liborName;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return LiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        LiborRateID = liborRateID;
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
     * @return Returns the staidAdjustRate.
     */
    public double getStaidAdjustRate()
    {
        return StaidAdjustRate;
    }
    /**
     * @param staidAdjustRate The staidAdjustRate to set.
     */
    public void setStaidAdjustRate(double staidAdjustRate)
    {
        StaidAdjustRate = staidAdjustRate;
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

	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}

	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}

	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}

	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}
    
    
}
