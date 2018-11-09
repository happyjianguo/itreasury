package com.iss.itreasury.ebank.obdiscountapply.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *贴现票据统计信息 To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class DiscountBillStatInfo implements java.io.Serializable
{
	/**
	 * @see java.lang.Object#Object()
	 */
	public DiscountBillStatInfo()
	{
		super();
	}
	private long lID = -1; //贴现标识
	private long    subTypeId=-1;           //贷款子类
	//private long lApplyDiscountPO = -1;// 申请汇票贴现张数
	private long lBankAcceptPO = -1; //银行承兑汇票张数
	private long lBizAcceptPO = -1; //商业承兑汇票张数
	private double dAmount = 0.0; //申请贴现金额
	private String strReason = ""; //贴现原因
	private String strPurpose ="";//贴现目的
	private long lInputUserID = -1; //录入人标识
    private long isPurchaserInterest = 2;	//是否买方付息
    private long discountClientID = -1;		//出票人
    private String discountClientName = "";	//出票人名称
	private Timestamp tsDate = null; //录入日期
	private Timestamp tsDiscountStartDate = null; // 贴现开始日期 
	
	
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return dAmount;
	}
	/**
	 * Returns the bankAcceptPO.
	 * @return long
	 */
	public long getBankAcceptPO()
	{
		return lBankAcceptPO;
	}
	/**
	 * Returns the bizAcceptPO.
	 * @return long
	 */
	public long getBizAcceptPO()
	{
		return lBizAcceptPO;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return lID;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}
	/**
	 * Returns the reason.
	 * @return String
	 */
	public String getReason()
	{
		return strReason;
	}
	/**
	 * Returns the date.
	 * @return Timestamp
	 */
	public Timestamp getDate()
	{
		return tsDate;
	}
	/**
	 * Returns the discountStartDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountStartDate()
	{
		return tsDiscountStartDate;
	}
	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		dAmount = amount;
	}
	/**
	 * Sets the bankAcceptPO.
	 * @param bankAcceptPO The bankAcceptPO to set
	 */
	public void setBankAcceptPO(long bankAcceptPO)
	{
		lBankAcceptPO = bankAcceptPO;
	}
	/**
	 * Sets the bizAcceptPO.
	 * @param bizAcceptPO The bizAcceptPO to set
	 */
	public void setBizAcceptPO(long bizAcceptPO)
	{
		lBizAcceptPO = bizAcceptPO;
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		lID = iD;
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}
	/**
	 * Sets the reason.
	 * @param reason The reason to set
	 */
	public void setReason(String reason)
	{
		strReason = reason;
	}
	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Timestamp date)
	{
		tsDate = date;
	}
	/**
	 * Sets the discountStartDate.
	 * @param discountStartDate The discountStartDate to set
	 */
	public void setDiscountStartDate(Timestamp discountStartDate)
	{
		tsDiscountStartDate = discountStartDate;
	}
	/**
	 * Returns the purpose.
	 * @return String
	 */
	public String getPurpose()
	{
		return strPurpose;
	}

	/**
	 * Sets the purpose.
	 * @param purpose The purpose to set
	 */
	public void setPurpose(String purpose)
	{
		strPurpose = purpose;
	}
	public long getIsPurchaserInterest()
    {
        return isPurchaserInterest;
    }
    /**
     * @param isPurchaserInterest The isPurchaserInterest to set.
     */
    public void setIsPurchaserInterest(long isPurchaserInterest)
    {
        this.isPurchaserInterest = isPurchaserInterest;
    }
    public long getDiscountClientID()
    {
        return discountClientID;
    }
    /**
     * @param discountClientID The discountClientID to set.
     */
    public void setDiscountClientID(long discountClientID)
    {
        this.discountClientID = discountClientID;
    }
    public String getDiscountClientName()
    {
        return discountClientName;
    }
    /**
     * @param discountClientName The discountClientName to set.
     */
    public void setDiscountClientName(String discountClientName)
    {
        this.discountClientName = discountClientName;
    }
    public long getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(long subTypeId) {
		this.subTypeId = subTypeId;
	}

}