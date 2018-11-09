package com.iss.itreasury.creditrating.becominginvalid.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class Crert_CreditRatingCancelInfo implements Serializable {

	private long      ID	          = -1;  //����
	
	private String    ratingCode      = "";  //�����������ϱ��
	
	private long      ratingProjectID = -1;  //�����������ID
	
	private String    cancelMark      = "";  //����ԭ��
	
	private long      officeID        = -1;  //���´�ID
	
	private long      currencyID      = -1;  //����ID
	
	private long      inputUserID     = -1;  //¼����ID
	
	private Timestamp inputDate       = null;//¼��ʱ��
	
	private long      state           = -1;  //״̬
	
	private Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo = null;
	
	private InutParameterInfo inutParameterInfo = null;

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public String getCancelMark() {
		return cancelMark;
	}

	public void setCancelMark(String cancelMark) {
		this.cancelMark = cancelMark;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public String getRatingCode() {
		return ratingCode;
	}

	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}

	public long getRatingProjectID() {
		return ratingProjectID;
	}

	public void setRatingProjectID(long ratingProjectID) {
		this.ratingProjectID = ratingProjectID;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public Crert_CreditRatingDetailInfo getCrert_CreditRatingDetailInfo() {
		if(crert_CreditRatingDetailInfo == null)crert_CreditRatingDetailInfo = new Crert_CreditRatingDetailInfo();
		return crert_CreditRatingDetailInfo;
	}

	public void setCrert_CreditRatingDetailInfo(Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo) {
		this.crert_CreditRatingDetailInfo = crert_CreditRatingDetailInfo;
	}
}
