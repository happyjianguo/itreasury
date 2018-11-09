package com.iss.itreasury.creditrating.creditevalution.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class Crert_CreditRatingRevaluedInfo  implements Serializable{

	private long      ID	= -1;          //主键
	
	private String    ratingCode = "";     //信用评级重估编号
	
	private long      creditRatingID = -1; //信用评级结果ID
	
	private String    revalueDresult = ""; //重估结果
	
	private Timestamp revaluedDate = null; //重估时间
	
	private String    revaluedMark = "";   //重估原因
	
	private String    reMark = "";         //备注
	
	private long      officeID = -1;       //办事处ID
	
	private long      currencyID = -1;     //币种ID
	
	private long      inputUserID = -1;    //录入人ID
	
	private Timestamp inputDate = null;    //录入时间
	
	private long      state = -1;          //状态
	
	private long      clientID = -1;
	
	private String      ratingOldResult = "";
	
	private InutParameterInfo inutParameterInfo = null;
    
	private Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo = null;
	
	public Crert_CreditRatingDetailInfo getCrert_CreditRatingDetailInfo()
	{
		if(crert_CreditRatingDetailInfo == null)crert_CreditRatingDetailInfo = new Crert_CreditRatingDetailInfo();
		
		return crert_CreditRatingDetailInfo;
	}

	public void setCrert_CreditRatingDetailInfo(Crert_CreditRatingDetailInfo crert_CreditRatingDetailInfo)
	{
		this.crert_CreditRatingDetailInfo = crert_CreditRatingDetailInfo;
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public long getCreditRatingID() {
		return creditRatingID;
	}

	public void setCreditRatingID(long creditRatingID) {
		this.creditRatingID = creditRatingID;
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

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public Timestamp getRevaluedDate() {
		return revaluedDate;
	}

	public void setRevaluedDate(Timestamp revaluedDate) {
		this.revaluedDate = revaluedDate;
	}

	public String getRevaluedMark() {
		return revaluedMark;
	}

	public void setRevaluedMark(String revaluedMark) {
		this.revaluedMark = revaluedMark;
	}

	public String getRevalueDresult() {
		return revalueDresult;
	}

	public void setRevalueDresult(String revalueDresult) {
		this.revalueDresult = revalueDresult;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public String getRatingOldResult() {
		return ratingOldResult;
	}

	public void setRatingOldResult(String ratingOldResult) {
		this.ratingOldResult = ratingOldResult;
	}


}
