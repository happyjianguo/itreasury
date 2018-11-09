/*
 * Created on 2004-3-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.consignvoucher.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccountTrustVoucherConditionInfo  extends ITreasuryBaseDataEntity {
	long OfficeID = -1;
	long CurrencyID = -1;
	long ID = -1;
	long AccountID = -1;
	long StatusID = -1;//用于重置时的判断
	Timestamp InputDate = null;
	String VoucherStart = "";
	String VoucherEnd = "";
	int VoucherNum = -1;//凭证位数
	int PasswordNum = -1;//密码位数
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return AccountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		AccountID = accountID;
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
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	/**
	 * @return Returns the voucherEnd.
	 */
	public String getVoucherEnd() {
		return VoucherEnd;
	}
	/**
	 * @param voucherEnd The voucherEnd to set.
	 */
	public void setVoucherEnd(String voucherEnd) {
		VoucherEnd = voucherEnd;
	}
	/**
	 * @return Returns the voucherStart.
	 */
	public String getVoucherStart() {
		return VoucherStart;
	}
	/**
	 * @param voucherStart The voucherStart to set.
	 */
	public void setVoucherStart(String voucherStart) {
		VoucherStart = voucherStart;
	}
	/**
	 * @return Returns the passwordNum.
	 */
	public int getPasswordNum() {
		return PasswordNum;
	}
	/**
	 * @param passwordNum The passwordNum to set.
	 */
	public void setPasswordNum(int passwordNum) {
		PasswordNum = passwordNum;
	}
	/**
	 * @return Returns the voucherNum.
	 */
	public int getVoucherNum() {
		return VoucherNum;
	}
	/**
	 * @param voucherNum The voucherNum to set.
	 */
	public void setVoucherNum(int voucherNum) {
		VoucherNum = voucherNum;
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
        
        public long getId() {
            return 0;
        }
        
        public void setId(long id) {

        }
        
}
