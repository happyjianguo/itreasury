package com.iss.itreasury.settlement.query.resultinfo;
/**
 * �˴���������˵����
 * �������ڣ�(2002-1-15 17:20:10)
 * @author��
 */
public class DailyGatherInfo implements java.io.Serializable
{
	private long ID=-1;//��ʶ
	private String Name="";//����
	private double GetAmount=0;//�տ���
	private double PutAmount=0;//������
	private long Number=0;//������Ŀ
    
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
