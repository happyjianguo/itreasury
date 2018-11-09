/*
 * Created on 2003-12-10
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;

/**
 * @author yfsu
 * @author kewen hu 2003-12-16
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryDailyCapitalInfo implements Serializable
{
	private long ID = -1;					// ���
	private String Name="";				// ��λ����
	private String SimpleName="";				// ��λ����(���)	
	private double DailyHandIn=0.0; 		// ���ձ䶯-�Ͻ�	
	private double DailyCost=0.0; 		// ���ձ䶯-֧��
	private double MonthHandIn=0.0; 		// �����ۼ�-�Ͻ�
	private double MonthCost=0.0; 		// �����ۼ�-֧��
	private double MonthBudgetHandIn=0.0;	// ����Ԥ��-�Ͻ�
	private double MonthBudgetCost=0.0; 	// ����Ԥ��-֧��
	private double MarginHandIn=0.0; 		// ��Ԥ����-�Ͻ�
	private double MarginCost=0.0; 		// ��Ԥ����-֧��
	private double MonthBalance=0.0; 		// �³����
	private double TodayBalance=0.0; 		// �������
	private String Abstract=""; 			// ��ע
	private long ClientTypeID = -1;		// �ͻ�����ID
	private String ClientTypeName = "";	// �ͻ���������
	/**
	 * Returns the abstract
	 * @return String
	 */
	public String getAbstract() {
		return Abstract;
	}

	/**
	 * Returns the dailyCost.
	 * @return double
	 */
	public double getDailyCost() {
		return DailyCost;
	}

	/**
	 * Returns the dailyHandIn.
	 * @return double
	 */
	public double getDailyHandIn() {
		return DailyHandIn;
	}

	/**
	 * Returns the marginCost.
	 * @return double
	 */
	public double getMarginCost() {
		return MarginCost;
	}

	/**
	 * Returns the marginHandIn.
	 * @return double
	 */
	public double getMarginHandIn() {
		return MarginHandIn;
	}

	/**
	 * Returns the monthBalance.
	 * @return double
	 */
	public double getMonthBalance() {
		return MonthBalance;
	}

	/**
	 * Returns the monthBudgetCost.
	 * @return double
	 */
	public double getMonthBudgetCost() {
		return MonthBudgetCost;
	}

	/**
	 * Returns the monthBudgetHandIn.
	 * @return double
	 */
	public double getMonthBudgetHandIn() {
		return MonthBudgetHandIn;
	}

	/**
	 * Returns the monthCost.
	 * @return double
	 */
	public double getMonthCost() {
		return MonthCost;
	}

	/**
	 * Returns the monthHandIn.
	 * @return double
	 */
	public double getMonthHandIn() {
		return MonthHandIn;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Returns the todayBalance.
	 * @return double
	 */
	public double getTodayBalance() {
		return TodayBalance;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a) {
		Abstract = a;
	}

	/**
	 * Sets the dailyCost.
	 * @param dailyCost The dailyCost to set
	 */
	public void setDailyCost(double dailyCost) {
		DailyCost = dailyCost;
	}

	/**
	 * Sets the dailyHandIn.
	 * @param dailyHandIn The dailyHandIn to set
	 */
	public void setDailyHandIn(double dailyHandIn) {
		DailyHandIn = dailyHandIn;
	}

	/**
	 * Sets the marginCost.
	 * @param marginCost The marginCost to set
	 */
	public void setMarginCost(double marginCost) {
		MarginCost = marginCost;
	}

	/**
	 * Sets the marginHandIn.
	 * @param marginHandIn The marginHandIn to set
	 */
	public void setMarginHandIn(double marginHandIn) {
		MarginHandIn = marginHandIn;
	}

	/**
	 * Sets the monthBalance.
	 * @param monthBalance The monthBalance to set
	 */
	public void setMonthBalance(double monthBalance) {
		MonthBalance = monthBalance;
	}

	/**
	 * Sets the monthBudgetCost.
	 * @param monthBudgetCost The monthBudgetCost to set
	 */
	public void setMonthBudgetCost(double monthBudgetCost) {
		MonthBudgetCost = monthBudgetCost;
	}

	/**
	 * Sets the monthBudgetHandIn.
	 * @param monthBudgetHandIn The monthBudgetHandIn to set
	 */
	public void setMonthBudgetHandIn(double monthBudgetHandIn) {
		MonthBudgetHandIn = monthBudgetHandIn;
	}

	/**
	 * Sets the monthCost.
	 * @param monthCost The monthCost to set
	 */
	public void setMonthCost(double monthCost) {
		MonthCost = monthCost;
	}

	/**
	 * Sets the monthHandIn.
	 * @param monthHandIn The monthHandIn to set
	 */
	public void setMonthHandIn(double monthHandIn) {
		MonthHandIn = monthHandIn;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * Sets the todayBalance.
	 * @param todayBalance The todayBalance to set
	 */
	public void setTodayBalance(double todayBalance) {
		TodayBalance = todayBalance;
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

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * @return Returns the simpleName.
	 */
	public String getSimpleName() {
		return SimpleName;
	}
	/**
	 * @param simpleName The simpleName to set.
	 */
	public void setSimpleName(String simpleName) {
		SimpleName = simpleName;
	}
}
