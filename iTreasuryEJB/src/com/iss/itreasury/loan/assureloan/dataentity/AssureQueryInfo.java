package com.iss.itreasury.loan.assureloan.dataentity;


public class AssureQueryInfo {
	private String code;
	private String contractNo;
	private int assureKind = -1;
	private int assureMode = -1;
	private String startClient;
	private String endClient;
	private String startDate1;
	private String startDate2;
	private String endDate1;
	private String endDate2;
	
	public int getAssureKind() {
		return assureKind;
	}
	public void setAssureKind(int assureKind) {
		this.assureKind = assureKind;
	}
	public int getAssureMode() {
		return assureMode;
	}
	public void setAssureMode(int assureMode) {
		this.assureMode = assureMode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getEndClient() {
		return endClient;
	}
	public void setEndClient(String endClient) {
		this.endClient = endClient;
	}
	public String getEndDate1() {
		return endDate1;
	}
	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}
	public String getEndDate2() {
		return endDate2;
	}
	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}
	public String getStartClient() {
		return startClient;
	}
	public void setStartClient(String startClient) {
		this.startClient = startClient;
	}
	public String getStartDate1() {
		return startDate1;
	}
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	public String getStartDate2() {
		return startDate2;
	}
	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
}
