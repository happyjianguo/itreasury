package com.iss.itreasury.loan.integrationcredit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CreditLimitDetailInfo  extends ITreasuryBaseDataEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id = -1;
	private String applyCode = ""; //ҵ����
	private long creditLimitID = -1; //���Ŷ��ID
	private long changeTypeID = -1;  //������ͣ�����/���٣�
	private double changeAmount = 0;  //������
	private long inputUserID = -1;  //¼����
	private Timestamp inputDate = null;  //¼������
	private long statusID = -1;   
	private long activeUserID = -1;   //������
	private Timestamp activeDate = null; //��������
	private long activeStatusID = -1; //����״̬
	private long officeID = -1; //���´�ID
	private long currencyID = -1; //����ID
	private long clientID = -1; //�ͻ�ID
	private long creditModeID = -1;//���ŷ�ʽID
	private long creditTypeID = -1;//���Ų�Ʒ����ID
	private Timestamp startDate = null; //������ʼ���� 
	private Timestamp endDate =  null; //���Ž�������  
	private InutParameterInfo inutParameterInfo =null ;
//	�����ֶ�
	private long orderParam=-1;
	
	//desc
	private long desc=-1;	
	
	public long getDesc() {
		return desc;
	}
	public void setDesc(long desc) {
		this.desc = desc;
	}
	public long getOrderParam() {
		return orderParam;
	}
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public double getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
		putUsedField("changeAmount", changeAmount);
	}
	public long getChangeTypeID() {
		return changeTypeID;
	}
	public void setChangeTypeID(long changeTypeID) {
		this.changeTypeID = changeTypeID;
		putUsedField("changeTypeID", changeTypeID);
	}

	public long getCreditLimitID() {
		return creditLimitID;
	}
	public void setCreditLimitID(long creditLimitID) {
		this.creditLimitID = creditLimitID;
		putUsedField("creditLimitID", creditLimitID);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	
	public long getActiveUserID() {
		return activeUserID;
	}
	public void setActiveUserID(long activeUserID) {
		this.activeUserID = activeUserID;
		putUsedField("activeUserID", activeUserID);
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
		putUsedField("applyCode", applyCode);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public long getCreditModeID() {
		return creditModeID;
	}
	public void setCreditModeID(long creditModeID) {
		this.creditModeID = creditModeID;
		putUsedField("creditModeID", creditModeID);
	}
	public long getCreditTypeID() {
		return creditTypeID;
	}
	public void setCreditTypeID(long creditTypeID) {
		this.creditTypeID = creditTypeID;
		putUsedField("creditTypeID", creditTypeID);
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		putUsedField("endDate", endDate);
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}
	public Timestamp getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
		putUsedField("activeDate", activeDate);
	}
	public long getActiveStatusID() {
		return activeStatusID;
	}
	public void setActiveStatusID(long activeStatusID) {
		this.activeStatusID = activeStatusID;
		putUsedField("activeStatusID", activeStatusID);
	}
	
	
	

}
