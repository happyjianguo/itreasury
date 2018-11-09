/*
 * Created on 2004-3-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.consignvoucher.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author ruixie
 *  委托付款凭证DataEntity类
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccountTrustVoucherInfo extends ITreasuryBaseDataEntity {
	long Id = -1;
	long AccountId = -1;//账户ID
	String VoucherNo = "";//委托付款凭证号
	long StatusId = -1;//状态
	String PassWord = "";//密码
	Timestamp InputDate = null;//录入日期
	String TransactionNo = "";//交易号
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return AccountId;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		putUsedField("accountID", accountID);
		AccountId = accountID;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		Id = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		InputDate = inputDate;
	}
	/**
	 * @return Returns the passWord.
	 */
	public String getPassWord() {
		return PassWord;
	}
	/**
	 * @param passWord The passWord to set.
	 */
	public void setPassWord(String passWord) {
		putUsedField("passWord", passWord);
		PassWord = passWord;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusId;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		StatusId = statusID;
	}
	/**
	 * @return Returns the transactionNo.
	 */
	public String getTransactionNo() {
		return TransactionNo;
	}
	/**
	 * @param transactionNo The transactionNo to set.
	 */
	public void setTransactionNo(String transactionNo) {
		putUsedField("transactionNo", transactionNo);
		TransactionNo = transactionNo;
	}
	/**
	 * @return Returns the voucherNo.
	 */
	public String getVoucherNo() {
		return VoucherNo;
	}
	/**
	 * @param voucherNo The voucherNo to set.
	 */
	public void setVoucherNo(String voucherNo) {
		putUsedField("voucherNo", voucherNo);
		VoucherNo = voucherNo;
	}
}
