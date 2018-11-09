package com.iss.itreasury.loan.aftercredit.dataentity;

public class AfterCreditAccountInfo {
	private String scode;
	private String sname;
	private String scapital;
	private String creditlevel;
	private double creditamount;//授信额度
	private double camount;//信用额度
	private long creditgradeid = -1;//信用等级id
	private long clientTypeid1 = -1;
	private long clientTypeid2 = -1;
	private long extendAttribute1 = -1;
	private long extendAttribute2 = -1;
	public double getCreditamount() {
		return creditamount;
	}
	public void setCreditamount(double creditamount) {
		this.creditamount = creditamount;
	}
	public String getCreditlevel() {
		return creditlevel;
	}
	public void setCreditlevel(String creditlevel) {
		this.creditlevel = creditlevel;
	}
	public String getScapital() {
		return scapital;
	}
	public void setScapital(String scapital) {
		this.scapital = scapital;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public double getCamount() {
		return camount;
	}
	public void setCamount(double camount) {
		this.camount = camount;
	}
	public long getCreditgradeid() {
		return creditgradeid;
	}
	public void setCreditgradeid(long creditgradeid) {
		this.creditgradeid = creditgradeid;
	}
	public long getClientTypeid1() {
		return clientTypeid1;
	}
	public void setClientTypeid1(long clientTypeid1) {
		this.clientTypeid1 = clientTypeid1;
	}
	public long getClientTypeid2() {
		return clientTypeid2;
	}
	public void setClientTypeid2(long clientTypeid2) {
		this.clientTypeid2 = clientTypeid2;
	}
	public long getExtendAttribute1() {
		return extendAttribute1;
	}
	public void setExtendAttribute1(long extendAttribute1) {
		this.extendAttribute1 = extendAttribute1;
	}
	public long getExtendAttribute2() {
		return extendAttribute2;
	}
	public void setExtendAttribute2(long extendAttribute2) {
		this.extendAttribute2 = extendAttribute2;
	}
	
}
