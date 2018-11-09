package com.iss.itreasury.ebank.obdiscountapply.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *����Ʊ��ͳ����Ϣ To change this generated comment edit the template variable
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
	private long lID = -1; //���ֱ�ʶ
	private long    subTypeId=-1;           //��������
	//private long lApplyDiscountPO = -1;// �����Ʊ��������
	private long lBankAcceptPO = -1; //���гжһ�Ʊ����
	private long lBizAcceptPO = -1; //��ҵ�жһ�Ʊ����
	private double dAmount = 0.0; //�������ֽ��
	private String strReason = ""; //����ԭ��
	private String strPurpose ="";//����Ŀ��
	private long lInputUserID = -1; //¼���˱�ʶ
    private long isPurchaserInterest = 2;	//�Ƿ��򷽸�Ϣ
    private long discountClientID = -1;		//��Ʊ��
    private String discountClientName = "";	//��Ʊ������
	private Timestamp tsDate = null; //¼������
	private Timestamp tsDiscountStartDate = null; // ���ֿ�ʼ���� 
	
	
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