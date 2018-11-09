/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 特殊交易的按状态查询条件实体类：
 * 1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 * 2、包含变量、类型、默认值、说明
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryConditionInfo implements Serializable
{
	private long OfficeID = -1; //办事处ID
	private long CurrencyID = -1; //币种ID
	private long UserID = -1; //用户ID
	private long TypeID = -1; // 查询类型：0，（处理的查找）；1，（复核的查找）
	private long StatusID = -1; //交易状态
	private Timestamp Date = null; //查询日期
	private long TransactionTypeID = -1; //  业务类型
	private long OrderByType = -1;//排序条件
	private boolean isDesc = false;//正反序
	private boolean isPayForm = false;//正反序

	public boolean isPayForm() {
		return isPayForm;
	}
	public void setPayForm(boolean isPayForm) {
		this.isPayForm = isPayForm;
	}
	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * @return
	 */
	public long getTypeID()
	{
		return TypeID;
	}
	/**
	 * @return
	 */
	public long getUserID()
	{
		return UserID;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}
	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}
	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		TypeID = l;
	}
	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		UserID = l;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return this.TransactionTypeID;
	}
	/**
	 * @param transactionTypeID
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.TransactionTypeID = transactionTypeID;
	}
	/**
	 * @return
	 */
	public boolean getDesc()
	{
		return this.isDesc;
	}

	/**
	 * @return
	 */
	public long getOrderByType()
	{
		return OrderByType;
	}

	/**
	 * @param l
	 */
	public void setDesc(boolean b)
	{
		this.isDesc = b;
	}

	/**
	 * @param l
	 */
	public void setOrderByType(long l)
	{
		OrderByType = l;
	}

}
