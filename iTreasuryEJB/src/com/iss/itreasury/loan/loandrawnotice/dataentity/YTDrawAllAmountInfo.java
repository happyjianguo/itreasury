/*
 * Created on 2004-8-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.loandrawnotice.dataentity;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author zcwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class YTDrawAllAmountInfo implements java.io.Serializable
{
	private long attendBankID = -1;//银行名称在参与行设置的ID
	private long contractID = -1; //合同ID
	private long isHead = -1; //是否牵头行
	private double drawAllAmount = 0; //提款金额
	private double chargeAllAmount = 0; //应付代理费

	private String bankName = ""; //银行名称
	private double loanRate = 0.0; //承贷比例
	public long getAttendBankID() {
		return attendBankID;
	}
	public void setAttendBankID(long attendBankID) {
		this.attendBankID = attendBankID;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public double getChargeAllAmount() {
		return chargeAllAmount;
	}
	public void setChargeAllAmount(double chargeAllAmount) {
		this.chargeAllAmount = chargeAllAmount;
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public double getDrawAllAmount() {
		return drawAllAmount;
	}
	public void setDrawAllAmount(double drawAllAmount) {
		this.drawAllAmount = drawAllAmount;
	}
	public long getIsHead() {
		return isHead;
	}
	public void setIsHead(long isHead) {
		this.isHead = isHead;
	}
	public double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}

	
}
