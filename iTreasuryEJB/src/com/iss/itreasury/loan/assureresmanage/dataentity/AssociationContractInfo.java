package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;

public class AssociationContractInfo implements Serializable {

	private String clientName = "";
	
	private String enterprisesCode = "";
	
	private String contractCode = "";
	
	private long   loanType = -1;
	
	private double loanAmount = 0;
	
	private long   contractStatus = -1;
	
	private long   assureModule = -1;
	
	private double assureAmount = 0;

	public double getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(double assureAmount) {
		this.assureAmount = assureAmount;
	}

	public long getAssureModule() {
		return assureModule;
	}

	public void setAssureModule(long assureModule) {
		this.assureModule = assureModule;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public long getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(long contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getEnterprisesCode() {
		return enterprisesCode;
	}

	public void setEnterprisesCode(String enterprisesCode) {
		this.enterprisesCode = enterprisesCode;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public long getLoanType() {
		return loanType;
	}

	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}
}
