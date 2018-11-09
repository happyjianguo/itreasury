/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author wlming
 * 	定期/通知存款交易的按状态查询条件实体类：
 *	1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *	2、包含变量、类型、默认值、说明
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryByStatusConditionInfo extends BaseDataEntity implements Serializable 
{
	private long OfficeID = -1;//办事处ID
	private long CurrencyID = -1;//币种ID
	private long UserID = -1;//用户ID
	private long TypeID = -1;// 查询类型：0，（处理的查找）；1，（复核的查找）
	private long StatusID = -1;//交易状态
	private long TransactionTypeID = -1; //交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	private Timestamp Date = null; //查询日期
	private long[]  Status =null;  //交易状态
	int OrderByType = -1 ;  //排序类型
	boolean isDesc =false;  //升序或降序
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 获得 -- 币种ID
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 *  获得 -- 办事处ID
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 *  获得 -- 状态
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 *  获得 -- 查询类型：0，（处理的查找）；1，（复核的查找）
	 * @return
	 */
	public long getTypeID()
	{
		return TypeID;
	}

	/**
	 *  获得 -- 用户ID
	 * @return
	 */
	public long getUserID()
	{
		return UserID;
	}

	/**
	 *  设置 -- 币种ID
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * 设置 -- 办事处ID
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * 设置 -- 状态
	 * @param ls
	 */
	public void setStatusID(long ls)
	{
		StatusID = ls;
	}

	/**
	 * 设置 -- 查询类型：0，（处理的查找）；1，（复核的查找）
	 * @param l
	 */
	public void setTypeID(long l)
	{
		TypeID = l;
	}

	/**
	 * 设置 -- 用户ID
	 * @param l
	 */
	public void setUserID(long l)
	{
		UserID = l;
	}

	/**
	 * 得到日期 
	 * Returns the date.
	 * @return Timestamp
	 */
	public Timestamp getDate() 
	{
		return Date;
	}

	/**
	 * 得到交易类型ID 
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID() 
	{
		return TransactionTypeID;
	}

	/**
	 * 设置日期 
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Timestamp date) 
	{
		Date = date;
	}

	/**
	 * 设置交易类型ID 
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID) 
	{
		TransactionTypeID = transactionTypeID;
	}

	/**
	 * @return
	 */
	public long[] getStatus() {
		return Status;
	}

	/**
	 * @param ls
	 */
	public void setStatus(long[] ls) {
		Status = ls;
	}

	/**
	 * @return
	 */
	public boolean isDesc() {
		return isDesc;
	}

	/**
	 * @return
	 */
	public int getOrderByType() {
		return OrderByType;
	}

	/**
	 * @param b
	 */
	public void setDesc(boolean b) {
		isDesc = b;
	}

	/**
	 * @param i
	 */
	public void setOrderByType(int i) {
		OrderByType = i;
	}

}
