/* Generated by Together */

package com.iss.itreasury.bill.blankvoucher.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.bill.util.BillDataEntity;

public class BlankVoucherQueryInfo extends BillDataEntity {
	private long QueryBillTypeID = -1;//票据类型
	private String QueryBillCodeStart = "";//起始编号
	private String QueryBillCodeEnd = "";//票据止号
	private long QueryBillStatusID = -1;//票据状态
	private long QueryUseClientID = -1;//领用客户
	private long QueryUseAccountID = -1;//领用帐户
	private long QueryInputUserID = -1;//录入人
	private Timestamp QueryInputDate = null;//录入日期
	private long QueryOfficeID =-1;//
	private long QueryCurrencyID = -1;//
	private Timestamp searchDate ;
	private long billTypeID = -1;
	
	private String OrderParamString ="";//排序字段
	private long Desc = -1;//
	private long PageLineCount = -1;//
	private long PageNo = -1;//
	private long PageCount =-1;//
	/**
	 * @return Returns the billTypeID.
	 */
	public long getBillTypeID()
	{
		return billTypeID;
	}
	/**
	 * @param billTypeID The billTypeID to set.
	 */
	public void setBillTypeID(long billTypeID)
	{
		this.billTypeID = billTypeID;
	}
	/**
	 * @return Returns the searchDate.
	 */
	public Timestamp getSearchDate()
	{
		return searchDate;
	}
	/**
	 * @param searchDate The searchDate to set.
	 */
	public void setSearchDate(Timestamp searchDate)
	{
		this.searchDate = searchDate;
	}
	/**
	 * @return Returns the queryBillCodeEnd.
	 */
	public String getQueryBillCodeEnd()
	{
		return QueryBillCodeEnd;
	}
	/**
	 * @param queryBillCodeEnd The queryBillCodeEnd to set.
	 */
	public void setQueryBillCodeEnd(String queryBillCodeEnd)
	{
		QueryBillCodeEnd = queryBillCodeEnd;
	}
	/**
	 * @return Returns the queryBillCodeStart.
	 */
	public String getQueryBillCodeStart()
	{
		return QueryBillCodeStart;
	}
	/**
	 * @param queryBillCodeStart The queryBillCodeStart to set.
	 */
	public void setQueryBillCodeStart(String queryBillCodeStart)
	{
		QueryBillCodeStart = queryBillCodeStart;
	}
	/**
	 * @return Returns the queryBillStatusID.
	 */
	public long getQueryBillStatusID()
	{
		return QueryBillStatusID;
	}
	/**
	 * @param queryBillStatusID The queryBillStatusID to set.
	 */
	public void setQueryBillStatusID(long queryBillStatusID)
	{
		QueryBillStatusID = queryBillStatusID;
	}
	/**
	 * @return Returns the queryBillTypeID.
	 */
	public long getQueryBillTypeID()
	{
		return QueryBillTypeID;
	}
	/**
	 * @param queryBillTypeID The queryBillTypeID to set.
	 */
	public void setQueryBillTypeID(long queryBillTypeID)
	{
		QueryBillTypeID = queryBillTypeID;
	}
	/**
	 * @return Returns the queryCurrencyID.
	 */
	public long getQueryCurrencyID()
	{
		return QueryCurrencyID;
	}
	/**
	 * @param queryCurrencyID The queryCurrencyID to set.
	 */
	public void setQueryCurrencyID(long queryCurrencyID)
	{
		QueryCurrencyID = queryCurrencyID;
	}
	/**
	 * @return Returns the queryInputDate.
	 */
	public Timestamp getQueryInputDate()
	{
		return QueryInputDate;
	}
	/**
	 * @param queryInputDate The queryInputDate to set.
	 */
	public void setQueryInputDate(Timestamp queryInputDate)
	{
		QueryInputDate = queryInputDate;
	}
	/**
	 * @return Returns the queryInputUserID.
	 */
	public long getQueryInputUserID()
	{
		return QueryInputUserID;
	}
	/**
	 * @param queryInputUserID The queryInputUserID to set.
	 */
	public void setQueryInputUserID(long queryInputUserID)
	{
		QueryInputUserID = queryInputUserID;
	}
	/**
	 * @return Returns the queryOfficeID.
	 */
	public long getQueryOfficeID()
	{
		return QueryOfficeID;
	}
	/**
	 * @param queryOfficeID The queryOfficeID to set.
	 */
	public void setQueryOfficeID(long queryOfficeID)
	{
		QueryOfficeID = queryOfficeID;
	}
	/**
	 * @return Returns the queryUseAccountID.
	 */
	public long getQueryUseAccountID()
	{
		return QueryUseAccountID;
	}
	/**
	 * @param queryUseAccountID The queryUseAccountID to set.
	 */
	public void setQueryUseAccountID(long queryUseAccountID)
	{
		QueryUseAccountID = queryUseAccountID;
	}
	/**
	 * @return Returns the queryUseClientID.
	 */
	public long getQueryUseClientID()
	{
		return QueryUseClientID;
	}
	/**
	 * @param queryUseClientID The queryUseClientID to set.
	 */
	public void setQueryUseClientID(long queryUseClientID)
	{
		QueryUseClientID = queryUseClientID;
	}
	/**
	 * @return Returns the desc.
	 */
	public long getDesc()
	{
		return Desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(long desc)
	{
		Desc = desc;
	}
	/**
	 * @return Returns the orderParamString.
	 */
	public String getOrderParamString()
	{
		return OrderParamString;
	}
	/**
	 * @param orderParamString The orderParamString to set.
	 */
	public void setOrderParamString(String orderParamString)
	{
		OrderParamString = orderParamString;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount()
	{
		return PageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}
	/**
	 * @return Returns the pageLineCount.
	 */
	public long getPageLineCount()
	{
		return PageLineCount;
	}
	/**
	 * @param pageLineCount The pageLineCount to set.
	 */
	public void setPageLineCount(long pageLineCount)
	{
		PageLineCount = pageLineCount;
	}
	/**
	 * @return Returns the pageNo.
	 */
	public long getPageNo()
	{
		return PageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(long pageNo)
	{
		PageNo = pageNo;
	}
}
