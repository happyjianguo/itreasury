package com.iss.itreasury.loan.loanapply.dataentity;

import java.io.Serializable;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
import java.sql.Timestamp;

public class LoanQueryInfo implements Serializable 
{
    private int loanType=-1;
    private long consignClientID=-1;
    private long borrowClientID=-1;
    private long startID=-1;
    private long endID=-1;
    private String startApplyCode = "";  //申请书编号从
    private String endApplyCode = "";//申请书编号到
    private long intervalNum=-1;
    private double startAmount=0;
    private double endAmount=0;
    private Timestamp startDate=null;
    private Timestamp endDate=null;
    private int statusID=-1;
    private long pageLineCount=-1;
    private long pageNo=-1;
    private long orderParam=-1;
    private long desc=-1;
    private long inputUserID=-1;
    private long nextCheckUserID=-1;
    private long queryPurpose=-1;	// 1:for modify 2:for examine
    private long officeID = -1; //办事处ID
    private long currencyID = -1; //币种ID
    
    public static void main(String[] args)
	{
	}
 

	/**
	 * @return
	 */
	public long getBorrowClientID()
	{
		return borrowClientID;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return consignClientID;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public double getEndAmount()
	{
		return endAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @return
	 */
	public long getEndID()
	{
		return endID;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return intervalNum;
	}

	/**
	 * @return
	 */
	public int getLoanType()
	{
		return loanType;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return pageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return pageNo;
	}

	/**
	 * @return
	 */
	public double getStartAmount()
	{
		return startAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @return
	 */
	public long getStartID()
	{
		return startID;
	}

	/**
	 * @return
	 */
	public int getStatusID()
	{
		return statusID;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		borrowClientID = l;
	}

	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		consignClientID = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}

	/**
	 * @param d
	 */
	public void setEndAmount(double d)
	{
		endAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setEndID(long l)
	{
		endID = l;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		intervalNum = l;
	}

	/**
	 * @param i
	 */
	public void setLoanType(int i)
	{
		loanType = i;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		pageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		pageNo = l;
	}

	/**
	 * @param d
	 */
	public void setStartAmount(double d)
	{
		startAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStartID(long l)
	{
		startID = l;
	}

	/**
	 * @param i
	 */
	public void setStatusID(int i)
	{
		statusID = i;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		inputUserID = l;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID()
	{
		return nextCheckUserID;
	}

	/**
	 * @return
	 */
	public long getQueryPurpose()
	{
		return queryPurpose;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		nextCheckUserID = l;
	}

	/**
	 * @param l
	 */
	public void setQueryPurpose(long l)
	{
		queryPurpose = l;
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}


	public String getEndApplyCode() {
		return endApplyCode;
	}


	public void setEndApplyCode(String endApplyCode) {
		this.endApplyCode = endApplyCode;
	}


	public String getStartApplyCode() {
		return startApplyCode;
	}


	public void setStartApplyCode(String startApplyCode) {
		this.startApplyCode = startApplyCode;
	}
}
