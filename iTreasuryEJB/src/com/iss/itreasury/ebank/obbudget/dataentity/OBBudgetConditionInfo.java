/*
 * Created on 2006-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

import java.sql.Timestamp;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetConditionInfo extends ITreasuryBaseDataEntity
{
	private long id = -1;//id
	private long accountID = -1;//账户id
	private Timestamp startdate = null;//查询开始日期
	private Timestamp enddate = null;//查询结束日期
	private double minamount = 0.00;//金额最小值
	private double maxamount = 0.00;// 金额最大值啊　
	private long clientID = -1;//客户ID
	
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	/**
	 * @return Returns the enddate.
	 */
	public Timestamp getEnddate() {
		return enddate;
	}
	/**
	 * @param enddate The enddate to set.
	 */
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the maxamount.
	 */
	public double getMaxamount() {
		return maxamount;
	}
	/**
	 * @param maxamount The maxamount to set.
	 */
	public void setMaxamount(double maxamount) {
		this.maxamount = maxamount;
	}
	/**
	 * @return Returns the minamount.
	 */
	public double getMinamount() {
		return minamount;
	}
	/**
	 * @param minamount The minamount to set.
	 */
	public void setMinamount(double minamount) {
		this.minamount = minamount;
	}
	/**
	 * @return Returns the startdate.
	 */
	public Timestamp getStartdate() {
		return startdate;
	}
	/**
	 * @param startdate The startdate to set.
	 */
	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}
	
    /**
     * @return Returns the clientID.
     */
    public long getClientID() {
        return clientID;
    }
    /**
     * @param clientID The clientID to set.
     */
    public void setClientID(long clientID) {
        this.clientID = clientID;
    }
}
