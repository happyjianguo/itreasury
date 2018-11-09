package com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ReconcliationInfo extends ITreasuryBaseDataEntity {
	private long id;      //主键
	private long year;     //年
	private long month;     //月
	private double recover; //回收率
	private double depositsTotal;    //存款合计
	private double loansTotal;       //贷款合计
	private long inputUserID;        //输入人ID;
	private Timestamp inputDate;      //输入日期
	private long sumNumber;          //做过交易账户总数
	private long checkNumber;        //做过反馈账户数
	public long getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(long sumNumber) {
		this.sumNumber = sumNumber;
		putUsedField("sumNumber", sumNumber);
	}

	public long getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(long checkNumber) {
		this.checkNumber = checkNumber;
		putUsedField("checkNumber", checkNumber);
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
		putUsedField("year", year);
	}

	public long getMonth() {
		return month;
	}

	public void setMonth(long month) {
		this.month = month;
		putUsedField("month", month);
	}

	public double getRecover() {
		return recover;
	}

	public void setRecover(double recover) {
		this.recover = recover;
		putUsedField("recover", recover);
	}

	public double getDepositsTotal() {
		return depositsTotal;
	}

	public void setDepositsTotal(double depositsTotal) {
		this.depositsTotal = depositsTotal;
		putUsedField("depositsTotal", depositsTotal);
	}

	public double getLoansTotal() {
		return loansTotal;
	}

	public void setLoansTotal(double loansTotal) {
		this.loansTotal = loansTotal;
		putUsedField("loansTotal", loansTotal);
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
		putUsedField("id", id);
	}

}
