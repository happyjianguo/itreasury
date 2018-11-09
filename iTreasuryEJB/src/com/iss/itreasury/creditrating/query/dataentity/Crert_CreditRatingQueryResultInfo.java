package com.iss.itreasury.creditrating.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Crert_CreditRatingQueryResultInfo implements Serializable {

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
	
	private String    ratingResult      = "";   //�������
	
	private Timestamp ratingDate         = null;	//¼��ʱ��
	
	private long      state             = -1;	//״̬

	private Timestamp revalueddate = null; //�����ع�����
	
	public Timestamp getRevalueddate() {
		return revalueddate;
	}

	public void setRevalueddate(Timestamp revalueddate) {
		this.revalueddate = revalueddate;
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

	public Timestamp getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Timestamp ratingDate) {
		this.ratingDate = ratingDate;
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
