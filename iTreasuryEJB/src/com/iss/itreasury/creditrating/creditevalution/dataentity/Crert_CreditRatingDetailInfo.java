package com.iss.itreasury.creditrating.creditevalution.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Crert_CreditRatingDetailInfo implements Serializable {

	private long      ID                = -1;   //����
	private long      creditRatingID   = -1;	//������������ID
	private String    ratingCode        = "";   //�����������
	private long      clientID          = -1;   //�ͻ�ID
	private String    clientCode        = "";
	private String    clientName        = "";
	private long      ratingType        = -1;   //��������
	private long      ratingProjectID   = -1;   //������������ID���ڲ���
	private String    ratingProjectName = "";   //������������
	private Timestamp stateDate         = null;	//������ʼʱ��
	private Timestamp endDate           = null; //��������ʱ��
	private double      ratingNumeric     = -1;   //��������
	private String    ratingResult      = "";   //�������
	private String    reMark            = "";   //��ע
	private Timestamp ratingDate        = null; //����ʱ��
	private long      officeID          = -1;   //���´�ID
	private long      currencyID        = -1;   //����ID
	private long      inputUserID       = -1;   //¼����ID
	private Timestamp inputDate         = null;	//¼��ʱ��
	private long      state             = -1;	//״̬
	private List creditRatingRevaluedList = new ArrayList();
	
	public List getCreditRatingRevaluedList() {
		return creditRatingRevaluedList;
	}
	public void setCreditRatingRevaluedList(List creditRatingRevaluedList) {
		this.creditRatingRevaluedList = creditRatingRevaluedList;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
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
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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
	public Timestamp getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(Timestamp ratingDate) {
		this.ratingDate = ratingDate;
	}
	public double getRatingNumeric() {
		return ratingNumeric;
	}
	public void setRatingNumeric(double ratingNumeric) {
		this.ratingNumeric = ratingNumeric;
	}
	public long getRatingProjectID() {
		return ratingProjectID;
	}
	public void setRatingProjectID(long ratingProjectID) {
		this.ratingProjectID = ratingProjectID;
	}
	public String getRatingProjectName() {
		return ratingProjectName;
	}
	public void setRatingProjectName(String ratingProjectName) {
		this.ratingProjectName = ratingProjectName;
	}
	public String getRatingResult() {
		return ratingResult;
	}
	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
	}
	public long getRatingType() {
		return ratingType;
	}
	public void setRatingType(long ratingType) {
		this.ratingType = ratingType;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public Timestamp getStateDate() {
		return stateDate;
	}
	public void setStateDate(Timestamp stateDate) {
		this.stateDate = stateDate;
	}
}
