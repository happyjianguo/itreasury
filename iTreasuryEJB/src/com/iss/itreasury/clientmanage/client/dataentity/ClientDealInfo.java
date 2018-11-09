package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;

public class ClientDealInfo extends CimsBaseDataEntity{
	
	private long officeID=-1;//所属办事处id
	private String code="";//客户编号 
	private String name="";//姓名 
	private long clientid=-1;//客户id
	private String dealScope="";//经营范围
	private String capitalScope="";//资产规模
    private String netCapital="";//净资产
    private String productScope="";//生产规模
    private String products="";//主要产品
    private String operations="";//主营业事务及销售市场
    private Timestamp buildDate;//成立日期
    private long employeeNumber=0;//员工总数
	
	
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
