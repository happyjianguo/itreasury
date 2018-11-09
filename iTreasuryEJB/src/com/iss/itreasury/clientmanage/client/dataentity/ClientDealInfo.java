package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;

public class ClientDealInfo extends CimsBaseDataEntity{
	
	private long officeID=-1;//�������´�id
	private String code="";//�ͻ���� 
	private String name="";//���� 
	private long clientid=-1;//�ͻ�id
	private String dealScope="";//��Ӫ��Χ
	private String capitalScope="";//�ʲ���ģ
    private String netCapital="";//���ʲ�
    private String productScope="";//������ģ
    private String products="";//��Ҫ��Ʒ
    private String operations="";//��Ӫҵ���������г�
    private Timestamp buildDate;//��������
    private long employeeNumber=0;//Ա������
	
	
    public long getOfficeID() {
		return officeID;
	}
	
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		putUsedField("code", code);
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		putUsedField("name", name);
		this.name = name;
	}
	
	public String getDealScope() {
		return dealScope;
	}

	public void setDealScope(String dealScope) {
		putUsedField("dealScope", dealScope);
		this.dealScope = dealScope;
	}
	
	public String getCapitalScope() {
		return capitalScope;
	}
	
	public void setCapitalScope(String capitalScope) {
		putUsedField("capitalScope", capitalScope);
		this.capitalScope = capitalScope;
	}
	
	public String getNetCapital() {
		return netCapital;
	}
	
	public void setNetCapital(String netCapital) {
		putUsedField("netCapital", netCapital);
		this.netCapital = netCapital;
	}
	
	public String getProductScope() {
		return productScope;
	}

	public void setProductScope(String productScope) {
		putUsedField("productScope", productScope);
		this.productScope = productScope;
	}
	
	public String getProducts() {
		return products;
	}
	
	public void setProducts(String products) {
		putUsedField("products", products);
		this.products = products;
	}
	
	public String getOperations() {
		return operations;
	}
	
	public void setOperations(String operations) {
		putUsedField("operations", operations);
		this.operations = operations;
	}
	
	public Timestamp getBuildDate() {
		return buildDate;
	}
	
	public void setBuildDate(Timestamp buildDate) {
		putUsedField("buildDate", buildDate);
		this.buildDate = buildDate;
	}
	
	public long getEmployeeNumber() {
		return employeeNumber;
	}
	
	public void setEmployeeNumber(long employeeNumber) {
		putUsedField("employeeNumber", employeeNumber);
		this.employeeNumber = employeeNumber;
	}
	
	public long getClientid() {
		return clientid;
	}
	
	public void setClientid(long clientid) {
		putUsedField("clientid", clientid);
		this.clientid = clientid;
	}
}
