package com.iss.itreasury.settlement.accountsystem.dataentity;

import java.io.Serializable;

public class CapitalTransferStatResult implements Serializable {
	private long subAccountId; // 下级账户ID
	private String subAccountCode; // 下级账户号
	private String subAccountName; // 下级账户名称
	private double sumCapitalUpAmount; // 上划金额合计
	private long countCapitalUp; // 上划笔数合计
	private double sumCapitalDownAmount; // 下拨金额合计
	private long countCapitalDown; // 下拨笔数合计
	private double endingBalance; // 下级账户期末余额

	public long getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(long subAccountId) {
		this.subAccountId = subAccountId;
	}

	public String getSubAccountCode() {
		return subAccountCode;
	}

	public void setSubAccountCode(String subAccountCode) {
		this.subAccountCode = subAccountCode;
	}

	public String getSubAccountName() {
		return subAccountName;
	}

	public void setSubAccountName(String subAccountName) {
		this.subAccountName = subAccountName;
	}

	public double getSumCapitalUpAmount() {
		return sumCapitalUpAmount;
	}

	public void setSumCapitalUpAmount(double sumCapitalUpAmount) {
		this.sumCapitalUpAmount = sumCapitalUpAmount;
	}

	public long getCountCapitalUp() {
		return countCapitalUp;
	}

	public void setCountCapitalUp(long countCapitalUp) {
		this.countCapitalUp = countCapitalUp;
	}

	public double getSumCapitalDownAmount() {
		return sumCapitalDownAmount;
	}

	public void setSumCapitalDownAmount(double sumCapitalDownAmount) {
		this.sumCapitalDownAmount = sumCapitalDownAmount;
	}

	public long getCountCapitalDown() {
		return countCapitalDown;
	}

	public void setCountCapitalDown(long countCapitalDown) {
		this.countCapitalDown = countCapitalDown;
	}

	public double getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(double endingBalance) {
		this.endingBalance = endingBalance;
	}
}
