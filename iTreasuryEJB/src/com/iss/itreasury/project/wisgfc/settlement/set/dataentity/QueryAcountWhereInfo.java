package com.iss.itreasury.project.wisgfc.settlement.set.dataentity;

public class QueryAcountWhereInfo {
	private long clientId;           //�ͻ�iD
	private long currenyId;          //����
	private long officeId;           //���´�
	private String accountNO;          //�˻���
	private String accountType;       //�˻�����
	private long status;              //��ѯ���÷�
	private long Desc = 1;             //����ʽ
	private long OrderField = 1;       //�����ֶ�
	public long getDesc() {
		return Desc;
	}
	public void setDesc(long desc) {
		Desc = desc;
	}
	public long getOrderField() {
		return OrderField;
	}
	public void setOrderField(long orderField) {
		OrderField = orderField;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getCurrenyId() {
		return currenyId;
	}
	public void setCurrenyId(long currenyId) {
		this.currenyId = currenyId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public String getAccountNO() {
		return accountNO;
	}
	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
}
