/*
 * Created on 2006-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transfixeddeposit.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotifyDepositInformInfo extends BaseDataEntity{
	private long ID=-1;
	private long moduleID=-1;//模块标识
	private long officeID=-1;//办事处标识
	private long currencyID=-1;//币种标识
	private String DepositNo="";//通知存款单据号
	private String notifyDate="";//通知日期
	private String strTakeDate="";//取款日期
	private Timestamp notifyDate1=null;
	private double amount=0;//支取金额
	private long statusID=-1;//状态标识
	private long userID = -1;//通知人ID
	private String stransno = "";//交易号 
	
	//为接收查询后结果设置
	private String saccountno = "" ;// 账户编号
	private String clientName = "" ;//客户名称
	private double mAmount = 0;//本金
	private String userName = "";//通知人
	
	//为接收要修改的信息设置
	private String clientCode = ""; //客户编号
	private double balance = 0;//余额
	private String startdate = "";//起始日
	private String sApplyCode = "";  //业务申请编号
	private long lSource = -1;  //数据来源
	
	//支取指令删除或保存 0为保存,1为删除
	private long isDele = -1;

	public long getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		ID = id;
		
	}
	
	public double getmAmount() {
		return mAmount;
	}
	public void setmAmount(double mAmount) {
		this.mAmount = mAmount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the depositNo.
	 */
	public String getDepositNo() {
		return DepositNo;
	}
	/**
	 * @param depositNo The depositNo to set.
	 */
	public void setDepositNo(String depositNo) {
		DepositNo = depositNo;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		
		ID = id;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}
	/**
	 * @return Returns the notifyDate.
	 */
	public String getNotifyDate() {
		return notifyDate;
	}
	/**
	 * @param notifyDate The notifyDate to set.
	 */
	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}	

	/**
	 * @param notifyDate The notifyDate to set.
	 */
	public void setNotifyDate( Date notifyDate) {
		this.notifyDate = notifyDate.toString();
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
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSaccountno() {
		return saccountno;
	}
	public void setSaccountno(String saccountno) {
		this.saccountno = saccountno;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public long getIsDele() {
		return isDele;
	}
	public void setIsDele(long isDele) {
		this.isDele = isDele;
	}
	public String getStransno() {
		return stransno;
	}
	public void setStransno(String stransno) {
		this.stransno = stransno;
	}
	public Timestamp getNotifyDate1() {
		return notifyDate1;
	}
	public void setNotifyDate1(Timestamp notifyDate1) {
		this.notifyDate1 = notifyDate1;
	}
	public String getStrTakeDate() {
		return strTakeDate;
	}
	public void setStrTakeDate(String strTakeDate) {
		this.strTakeDate = strTakeDate;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public String getSApplyCode() {
		return sApplyCode;
	}
	public void setSApplyCode(String applyCode) {
		sApplyCode = applyCode;
	}	
	public long getLSource() {
		return lSource;
	}
	public void setLSource(long source) {
		lSource = source;
	}
}
