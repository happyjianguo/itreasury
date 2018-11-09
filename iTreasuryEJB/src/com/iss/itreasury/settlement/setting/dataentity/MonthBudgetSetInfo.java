/*
 * Created on 2004-04-23
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

/**
 * @author kewen hu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonthBudgetSetInfo implements Serializable {
	/** ���ֶ� */
    //ID				Y	PK
	private long ID = -1;
    //nOfficeID	���´�ID	Number			
	private long OfficeID = -1;
	//nCurrencyID	����ID	Number			
	private long CurrencyID = -1;
	//sYearMonth	Ԥ������	Varchar(20)			��������ʾ������200404��
	private String YearMonth = "";
	//nSuperClientID1	�ϼ���λ1	Number			
	private long SuperClientID1 = -1;
	//nSuperClientID2	�ϼ���λ2	Number			
	private long SuperClientID2 = -1;
	//nClientID	������λ	Number			
	private long ClientID = -1;
	//mDepositAmount	Ԥ�������	Money			
	private double DepositAmount = 0.0;
	//mWithdrawAmount	Ԥ��֧ȡ���	Money			
	private double WithdrawAmount = 0.0;
	//nStatusID	״̬	Number	Not nullDefault -1		1-����0-��Ч
	private long StatusID = -1;

	/** ҳ���������� */
	private String Code = "";		//�ͻ����
	private String Name = "";		//�ͻ�����
	private long ReportType = -1;	//��������

	/** ����ҵ�������ṹ */
	private long TotalType = -1;					//�ϼ�����
	private String DepositAmountControlName = "";	//Ԥ�������Ŀؼ���
	private String WithdrawAmountControlName = "";	//Ԥ��֧ȡ���Ŀؼ���

	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return ClientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		ClientID = clientID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the depositAmount.
	 */
	public double getDepositAmount() {
		return DepositAmount;
	}
	/**
	 * @param depositAmount The depositAmount to set.
	 */
	public void setDepositAmount(double depositAmount) {
		DepositAmount = depositAmount;
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
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	/**
	 * @return Returns the superClientID1.
	 */
	public long getSuperClientID1() {
		return SuperClientID1;
	}
	/**
	 * @param superClientID1 The superClientID1 to set.
	 */
	public void setSuperClientID1(long superClientID1) {
		SuperClientID1 = superClientID1;
	}
	/**
	 * @return Returns the superClientID2.
	 */
	public long getSuperClientID2() {
		return SuperClientID2;
	}
	/**
	 * @param superClientID2 The superClientID2 to set.
	 */
	public void setSuperClientID2(long superClientID2) {
		SuperClientID2 = superClientID2;
	}
	/**
	 * @return Returns the withdrawAmount.
	 */
	public double getWithdrawAmount() {
		return WithdrawAmount;
	}
	/**
	 * @param withdrawAmount The withdrawAmount to set.
	 */
	public void setWithdrawAmount(double withdrawAmount) {
		WithdrawAmount = withdrawAmount;
	}
	/**
	 * @return Returns the yearMonth.
	 */
	public String getYearMonth() {
		return YearMonth;
	}
	/**
	 * @param yearMonth The yearMonth to set.
	 */
	public void setYearMonth(String yearMonth) {
		YearMonth = yearMonth;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return Code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		Code = code;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return Returns the reportType.
	 */
	public long getReportType() {
		return ReportType;
	}
	/**
	 * @param reportType The reportType to set.
	 */
	public void setReportType(long reportType) {
		ReportType = reportType;
	}
	/**
	 * @return Returns the depositAmountControlName.
	 */
	public String getDepositAmountControlName() {
		return DepositAmountControlName;
	}
	/**
	 * @param depositAmountControlName The depositAmountControlName to set.
	 */
	public void setDepositAmountControlName(String depositAmountControlName) {
		DepositAmountControlName = depositAmountControlName;
	}
	/**
	 * @return Returns the totalType.
	 */
	public long getTotalType() {
		return TotalType;
	}
	/**
	 * @param totalType The totalType to set.
	 */
	public void setTotalType(long totalType) {
		TotalType = totalType;
	}
	/**
	 * @return Returns the withdrawAmountControlName.
	 */
	public String getWithdrawAmountControlName() {
		return WithdrawAmountControlName;
	}
	/**
	 * @param withdrawAmountControlName The withdrawAmountControlName to set.
	 */
	public void setWithdrawAmountControlName(String withdrawAmountControlName) {
		WithdrawAmountControlName = withdrawAmountControlName;
	}
}