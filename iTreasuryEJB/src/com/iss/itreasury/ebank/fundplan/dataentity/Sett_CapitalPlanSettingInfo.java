package com.iss.itreasury.ebank.fundplan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Sett_CapitalPlanSettingInfo implements Serializable {

	private long   ID            = -1;			
	
	private long   clientID      = -1;       //�ͻ�ID
	
	private String clientCode    = "";       //�ͻ����
	
	private String clientName    = "";       //�ͻ�����
	
	private String clientTypeName1 = "";     //�ͻ�����1����
	
	private String clientTypeName2 = "";     //�ͻ�����2����
	
	private String clientTypeName3 = "";     //�ͻ�����3����
	
	private long   isCheckPlan   = -1;       //�����ͻ��Ƿ񸴺˼ƻ����ݣ�1-�ǡ�0-��
	
	private long   nOfficeID     = -1;
	
	private long   nCurrencyID   = -1;
	
	
	private String clientCodeFromCtrl = "";
	
	private String clientCodeToCtrl = "";
	
	private long desc = -1;

	private long orderParam = -1;

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientCodeFromCtrl() {
		return clientCodeFromCtrl;
	}

	public void setClientCodeFromCtrl(String clientCodeFromCtrl) {
		this.clientCodeFromCtrl = clientCodeFromCtrl;
	}

	public String getClientCodeToCtrl() {
		return clientCodeToCtrl;
	}

	public void setClientCodeToCtrl(String clientCodeToCtrl) {
		this.clientCodeToCtrl = clientCodeToCtrl;
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

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public long getIsCheckPlan() {
		return isCheckPlan;
	}

	public void setIsCheckPlan(long isCheckPlan) {
		this.isCheckPlan = isCheckPlan;
	}

	public long getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	public long getNOfficeID() {
		return nOfficeID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}

	public String getClientTypeName1() {
		return clientTypeName1;
	}

	public void setClientTypeName1(String clientTypeName1) {
		this.clientTypeName1 = clientTypeName1;
	}

	public String getClientTypeName2() {
		return clientTypeName2;
	}

	public void setClientTypeName2(String clientTypeName2) {
		this.clientTypeName2 = clientTypeName2;
	}

	public String getClientTypeName3() {
		return clientTypeName3;
	}

	public void setClientTypeName3(String clientTypeName3) {
		this.clientTypeName3 = clientTypeName3;
	}
	
	
	
	
}
