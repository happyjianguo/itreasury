/*
 * Created on 2003-12-17
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.SQLException;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * @author kewen hu 2003-12-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QCurrencyDepositInfo extends QueryAccountResultInfo {
	private long No=0;					// 序号
	private String DepositCorp="";			// 存款单位
	private double PreWeeklyBalance=0.0;	// 上周存款余额
	private double WeeklyHandIn=0.0;		// 本周-存入
	private double WeeklyCost=0.0;		// 本周-支取
	private double CurrencyBalance=0.0;	// 本周存款余额-活期存款
	private double FixedBalance=0.0;		// 本周存款余额-定期存款
	private double NotifyBalance=0.0;		// 本周存款余额-通知存款
	private double SumBalance=0.0;		// 合计
	private long ClientTypeID = -1;		// 客户分类ID
	private String ClientTypeName = "";	// 客户分类名称
	/**
	 * 构造函数
	 * @param nothing
	 * @return nothing
	 * @see java.lang.Object#Object()
	 * @exception nothing
	 */
	public QCurrencyDepositInfo() {
		super();
	}
	/**
	 * Returns the currencyBalance.
	 * @return double
	 */
	public double getCurrencyBalance() {
		return CurrencyBalance;
	}

	/**
	 * Returns the depositCorp.
	 * @return String
	 */
	public String getDepositCorp() {
		return DepositCorp;
	}

	/**
	 * Returns the fixedBalance.
	 * @return double
	 */
	public double getFixedBalance() {
		return FixedBalance;
	}

	/**
	 * Returns the no.
	 * @return long
	 */
	public long getNo() {
		return No;
	}

	/**
	 * Returns the notifyBalance.
	 * @return double
	 */
	public double getNotifyBalance() {
		return NotifyBalance;
	}

	/**
	 * Returns the preWeeklyBalance.
	 * @return double
	 */
	public double getPreWeeklyBalance() {
		return PreWeeklyBalance;
	}

	/**
	 * Returns the sumBalance.
	 * @return double
	 */
	public double getSumBalance() {
		return SumBalance;
	}

	/**
	 * Returns the weeklyCost.
	 * @return double
	 */
	public double getWeeklyCost() {
		return WeeklyCost;
	}

	/**
	 * Returns the weeklyHandIn.
	 * @return double
	 */
	public double getWeeklyHandIn() {
		return WeeklyHandIn;
	}

	/**
	 * Sets the currencyBalance.
	 * @param currencyBalance The currencyBalance to set
	 */
	public void setCurrencyBalance(double currencyBalance) {
		CurrencyBalance = currencyBalance;
	}

	/**
	 * Sets the depositCorp.
	 * @param depositCorp The depositCorp to set
	 */
	public void setDepositCorp(String depositCorp) {
		DepositCorp = depositCorp;
	}

	/**
	 * Sets the fixedBalance.
	 * @param fixedBalance The fixedBalance to set
	 */
	public void setFixedBalance(double fixedBalance) {
		FixedBalance = fixedBalance;
	}

	/**
	 * Sets the no.
	 * @param no The no to set
	 */
	public void setNo(long no) {
		No = no;
	}

	/**
	 * Sets the notifyBalance.
	 * @param notifyBalance The notifyBalance to set
	 */
	public void setNotifyBalance(double notifyBalance) {
		NotifyBalance = notifyBalance;
	}

	/**
	 * Sets the preWeeklyBalance.
	 * @param preWeeklyBalance The preWeeklyBalance to set
	 */
	public void setPreWeeklyBalance(double preWeeklyBalance) {
		PreWeeklyBalance = preWeeklyBalance;
	}

	/**
	 * Sets the sumBalance.
	 * @param sumBalance The sumBalance to set
	 */
	public void setSumBalance(double sumBalance) {
		SumBalance = sumBalance;
	}

	/**
	 * Sets the weeklyCost.
	 * @param weeklyCost The weeklyCost to set
	 */
	public void setWeeklyCost(double weeklyCost) {
		WeeklyCost = weeklyCost;
	}

	/**
	 * Sets the weeklyHandIn.
	 * @param weeklyHandIn The weeklyHandIn to set
	 */
	public void setWeeklyHandIn(double weeklyHandIn) {
		WeeklyHandIn = weeklyHandIn;
	}
	/**
	 * Returns the clientTypeID.
	 * @return long
	 */
	public long getClientTypeID() {
		return ClientTypeID;
	}

	/**
	 * Returns the clientTypeName.
	 * @return String
	 */
	public String getClientTypeName() {
		return ClientTypeName;
	}

	/**
	 * Sets the clientTypeID.
	 * @param clientTypeID The clientTypeID to set
	 */
	public void setClientTypeID(long clientTypeID) {
		ClientTypeID = clientTypeID;
	}

	/**
	 * Sets the clientTypeName.
	 * @param clientTypeName The clientTypeName to set
	 */
	public void setClientTypeName(String clientTypeName) {
		ClientTypeName = clientTypeName;
	}

}