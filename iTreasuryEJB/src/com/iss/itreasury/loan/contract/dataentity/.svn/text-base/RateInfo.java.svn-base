/*
 * Created on 2003-11-18
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contract.dataentity;

import com.iss.itreasury.util.*;
import java.sql.Timestamp;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RateInfo implements java.io.Serializable
{
	private long BankInterestID = -1; //未调整的基准利率ID
	private long LateBankInterestID = -1; //调整后的基准利率ID
	private double Rate = 0; //未调整的利率
	private double LateRate = 0; //调整后的利率
	private double BasicRate = 0; //	未调整的基准利率
	private double LateBasicRate = 0; //调整后的基准利率
	private Timestamp AdjustDate = null; //调整日期
    
    //======ninh 2004-07-14 需求变更 增加固定浮动利率======//
    private double AdjustRate = 0; //未调整的调整比例
    private double LateAdjustRate = 0; //调整后的利率
    private double StaidAdjustRate = 0; //未调整的浮动固定利率
    private double LateStaidAdjustRate = 0; //调整后的浮动固定利率
    
    private long LiborRateID = -1;	//Libor利率ID
    private String LiborName = "";	//Libor利率名称
    private long LiborIntervalNum = 0;	//Libor利率期限
    
    private String LateRateString = "";	//执行利率字符串（银行利率/Libor利率）
    
	/**
	 * @return
	 */
	public long getBankInterestID()
	{
		return BankInterestID;
	}

	/**
	 * @return
	 */
	public long getLateBankInterestID()
	{
		return LateBankInterestID;
	}

	/**
	 * @return
	 */
	public double getLateRate()
	{
		return LateRate;
	}

	/**
	* @return
	*/
	public String getFormatLateRate()
	{
		return DataFormat.formatRate(LateRate);
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	* @return
	*/
	public String getFormatRate()
	{
		return DataFormat.formatRate(Rate);
	}

	/**
	 * @param l
	 */
	public void setBankInterestID(long l)
	{
		BankInterestID = l;
	}

	/**
	 * @param l
	 */
	public void setLateBankInterestID(long l)
	{
		LateBankInterestID = l;
	}

	/**
	 * @param d
	 */
	public void setLateRate(double d)
	{
		LateRate = d;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * @return
	 */
	public double getBasicRate()
	{
		return BasicRate;
	}

	/**
	* @return
	*/
	public String getFormatBasicRate()
	{
		return DataFormat.formatRate(BasicRate);
	}

	/**
	 * @return
	 */
	public double getLateBasicRate()
	{
		return LateBasicRate;
	}

	/**
	* @return
	*/
	public String getFormatLateBasicRate()
	{
		return DataFormat.formatRate(LateBasicRate);
	}

	/**
	 * @param d
	 */
	public void setBasicRate(double d)
	{
		BasicRate = d;
	}

	/**
	 * @param d
	 */
	public void setLateBasicRate(double d)
	{
		LateBasicRate = d;
	}

	/**
		 * @return
		 */
	public String getFormatAdjustDate()
	{
		return DataFormat.getDateString(AdjustDate);
	}

	/**
	 * @return
	 */
	public Timestamp getAdjustDate()
	{
		return AdjustDate;
	}

	/**
	 * @param timestamp
	 */
	public void setAdjustDate(Timestamp timestamp)
	{
		AdjustDate = timestamp;
	}

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getAdjustRate()
    {
        return AdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getLateAdjustRate()
    {
        return LateAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getLateStaidAdjustRate()
    {
        return LateStaidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return StaidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAdjustRate(double d)
    {
        AdjustRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLateAdjustRate(double d)
    {
        LateAdjustRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLateStaidAdjustRate(double d)
    {
        LateStaidAdjustRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        StaidAdjustRate = d;
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
}
