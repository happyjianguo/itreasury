package com.iss.itreasury.bill.query.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;

public class QuerySumInfo implements Serializable
{
    //���������ܽ��
    private double TotalApplyAmount=0;
    
    //�ſ�ϼ�
    private double TotalPayAmount=0;
    
    //����ϼ�
    private double TotalRepayAmount=0;
    
    //��Ϣ�ϼ�
    private double TotalInterestAmount=0;
    
    //�����Ѻϼ�
    private double TotalChargeAmount=0;
    
    //�������ϼ�
    private double TotalBalance=0;
    
    //�����Ѻϼ�
    private double TotalCreditAmount=0;

	//�������
	private double sumOverdueAmount=0.0;

	//������Ϣ
	private double sumPunishInterest=0.0;
	
	//���ֽ���˸���Ϣ
	private double sumDiscountInterest=0.0;
	
	//�����򷽸���Ϣ
	private double sumDiscountPurchaserInterest=0.0;

    private long AllRecord = 0;
    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getTotalApplyAmount()
    {
        return TotalApplyAmount;
    }

    /**
     * @param d
     * function �õ�/����
     * return void
     */
    public void setTotalApplyAmount(double d)
    {
        this.TotalApplyAmount = d;
    }

    /**
     * function �õ�/����
     * return long
     */
    public long getAllRecord()
    {
        return AllRecord;
    }

    /**
     * @param l
     * function �õ�/����
     * return void
     */
    public void setAllRecord(long l)
    {
        this.AllRecord = l;
    }

	/**
	 * @return
	 */
	public double getTotalChargeAmount()
	{
		return TotalChargeAmount;
	}

	/**
	 * @return
	 */
	public double getTotalCreditAmount()
	{
		return TotalCreditAmount;
	}

	/**
	 * @return
	 */
	public double getTotalInterestAmount()
	{
		return TotalInterestAmount;
	}

	/**
	 * @return
	 */
	public double getTotalPayAmount()
	{
		return TotalPayAmount;
	}

	/**
	 * @return
	 */
	public double getTotalRepayAmount()
	{
		return TotalRepayAmount;
	}

	/**
	 * @param d
	 */
	public void setTotalChargeAmount(double d)
	{
		TotalChargeAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalCreditAmount(double d)
	{
		TotalCreditAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalInterestAmount(double d)
	{
		TotalInterestAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalPayAmount(double d)
	{
		TotalPayAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalRepayAmount(double d)
	{
		TotalRepayAmount = d;
	}

	/**
	 * Returns the sumOverdueAmount.
	 * @return double
	 */
	public double getSumOverdueAmount() {
		return sumOverdueAmount;
	}

	/**
	 * Returns the sumPunishInterest.
	 * @return double
	 */
	public double getSumPunishInterest() {
		return sumPunishInterest;
	}

	/**
	 * Sets the sumOverdueAmount.
	 * @param sumOverdueAmount The sumOverdueAmount to set
	 */
	public void setSumOverdueAmount(double sumOverdueAmount) {
		this.sumOverdueAmount = sumOverdueAmount;
	}

	/**
	 * Sets the sumPunishInterest.
	 * @param sumPunishInterest The sumPunishInterest to set
	 */
	public void setSumPunishInterest(double sumPunishInterest) {
		this.sumPunishInterest = sumPunishInterest;
	}

	/**
	 * @return
	 */
	public double getTotalBalance() {
		return TotalBalance;
	}

	/**
	 * @param d
	 */
	public void setTotalBalance(double d) {
		TotalBalance = d;
	}

    /**
     * @return Returns the sumDiscountInterest.
     */
    public double getSumDiscountInterest()
    {
        return sumDiscountInterest;
    }
    /**
     * @param sumDiscountInterest The sumDiscountInterest to set.
     */
    public void setSumDiscountInterest(double sumDiscountInterest)
    {
        this.sumDiscountInterest = sumDiscountInterest;
    }
    /**
     * @return Returns the sumDiscountPurchaserInterest.
     */
    public double getSumDiscountPurchaserInterest()
    {
        return sumDiscountPurchaserInterest;
    }
    /**
     * @param sumDiscountPurchaserInterest The sumDiscountPurchaserInterest to set.
     */
    public void setSumDiscountPurchaserInterest(double sumDiscountPurchaserInterest)
    {
        this.sumDiscountPurchaserInterest = sumDiscountPurchaserInterest;
    }
}