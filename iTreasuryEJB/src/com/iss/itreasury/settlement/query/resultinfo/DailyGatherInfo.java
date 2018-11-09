package com.iss.itreasury.settlement.query.resultinfo;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 17:20:10)
 * @author：
 */
public class DailyGatherInfo implements java.io.Serializable
{
	private long ID=-1;//标识
	private String Name="";//名称
	private double GetAmount=0;//收款金额
	private double PutAmount=0;//付款金额
	private long Number=0;//交易数目
    
	/**
	 * @return
	 */
	public double getGetAmount()
	{
		return GetAmount;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return Name;
	}

	/**
	 * @return
	 */
	public long getNumber()
	{
		return Number;
	}

	/**
	 * @return
	 */
	public double getPutAmount()
	{
		return PutAmount;
	}

	/**
	 * @param d
	 */
	public void setGetAmount(double d)
	{
		GetAmount = d;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		Name = string;
	}

	/**
	 * @param l
	 */
	public void setNumber(long l)
	{
		Number = l;
	}

	/**
	 * @param d
	 */
	public void setPutAmount(double d)
	{
		PutAmount = d;
	}

}
