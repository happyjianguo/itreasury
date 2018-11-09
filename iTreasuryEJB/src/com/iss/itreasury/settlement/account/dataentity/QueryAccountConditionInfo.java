/*
 * Created on 2003-9-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;
import java.io.Serializable;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryAccountConditionInfo extends BaseDataEntity implements Serializable
{

	private long OfficeID = -1; // 办事处ID
	private long CurrencyID = -1; //币种
	private long queryClientID = -1; // 查询由客户ID
	private String StartClientCode = ""; // 查询由客户编号
	private String EndClientCode = ""; // 查询至客户编号
	private String StartAccountCode = ""; // 查询由账户编号
	private String EndAccountCode = ""; // 查询至账户编号
	private long AccountTypeID = -1; //账户类型
	private long AccountGroupTypeID = -1; //账户组类型
	private long CheckStatusID = -1; //复核状态
	private long StatusID = -1; //账户状态
	private long BatchUpdateID = -1;//批量修改序号
	private long InputUserID = -1;//链接查找查出自己做的业务
	private long CheckUserID = -1;//复核时不能与录入人相同
	
	private long Order = 0;
	private long Desc =-1;
	private long PageCurrent = 1;
    private String strQuery = "";
    private String strAction = "";  //控制变量,在修改账户查询后使用

	public String getStrAction() 
	{
		return strAction;
	}

	public void setStrAction(String strAction) 
	{
		this.strAction = strAction;
	}

	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * Returns the checkStatusID.
	 * @return long
	 */
	public long getCheckStatusID()
	{
		return CheckStatusID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the endAccountCode.
	 * @return String
	 */
	public String getEndAccountCode()
	{
		return EndAccountCode;
	}

	/**
	 * Returns the endClientCode.
	 * @return String
	 */
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the startAccountCode.
	 * @return String
	 */
	public String getStartAccountCode()
	{
		return StartAccountCode;
	}

	/**
	 * Returns the startClientCode.
	 * @return String
	 */
	public String getStartClientCode()
	{
		return StartClientCode;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}

	/**
	 * Sets the checkStatusID.
	 * @param checkStatusID The checkStatusID to set
	 */
	public void setCheckStatusID(long checkStatusID)
	{
		CheckStatusID = checkStatusID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * Sets the endAccountCode.
	 * @param endAccountCode The endAccountCode to set
	 */
	public void setEndAccountCode(String endAccountCode)
	{
		EndAccountCode = endAccountCode;
	}

	/**
	 * Sets the endClientCode.
	 * @param endClientCode The endClientCode to set
	 */
	public void setEndClientCode(String endClientCode)
	{
		EndClientCode = endClientCode;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * Sets the startAccountCode.
	 * @param startAccountCode The startAccountCode to set
	 */
	public void setStartAccountCode(String startAccountCode)
	{
		StartAccountCode = startAccountCode;
	}

	/**
	 * Sets the startClientCode.
	 * @param startClientCode The startClientCode to set
	 */
	public void setStartClientCode(String startClientCode)
	{
		StartClientCode = startClientCode;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @return
	 */
	public long getOrder()
	{
		return Order;
	}

	/**
	 * @return
	 */
	public long getPageCurrent()
	{
		return PageCurrent;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrder(long l)
	{
		Order = l;
	}

	/**
	 * @param l
	 */
	public void setPageCurrent(long l)
	{
		PageCurrent = l;
	}

	/**
	 * @return
	 */
	public long getBatchUpdateID()
	{
		return BatchUpdateID;
	}

	/**
	 * @param l
	 */
	public void setBatchUpdateID(long l)
	{
		BatchUpdateID = l;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	public String getStrQuery() {
		return strQuery;
	}

	public void setStrQuery(String strQuery) {
		this.strQuery = strQuery;
	}

	/**
	 * @return the accountGroupTypeID
	 */
	public long getAccountGroupTypeID() {
		return AccountGroupTypeID;
	}

	/**
	 * @param accountGroupTypeID the accountGroupTypeID to set
	 */
	public void setAccountGroupTypeID(long accountGroupTypeID) {
		this.AccountGroupTypeID = accountGroupTypeID;
	}

	/**
	 * @return the queryClientID
	 */
	public long getQueryClientID() {
		return queryClientID;
	}

	/**
	 * @param queryClientID the queryClientID to set
	 */
	public void setQueryClientID(long queryClientID) {
		this.queryClientID = queryClientID;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}


}
