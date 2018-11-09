package com.iss.itreasury.loan.creditext.dataentity;

import java.sql.Date;

import com.iss.itreasury.util.DataFormat;

public class BankCreditExtResultInfo {
	private long 	no = -1;			// ���
	private String	contractNo = "";			//���ź�ͬ��	
	private String	year = "";					//�������	
	private String 	bankName = "";				//��������	
	private String 	company = "";				//��������	
	private long 	variety = -1; 				// ����Ʒ��  1-���ڴ���2-�г��ڴ���3-����֤4-����5-�Ŵ�֤��6-�жһ�Ʊ
	private long 	currencyType = -1; 			// ���� 
	private double 	amount = 0.0; 				// ��� 
	private String	startDate = "";				//��ʼ����	
	private String	endDate = "";				//��������	
	private String	operationDate = "";			//��������	
	private long		status	= -1;				//����״̬		1-ִ����2-�ѽ���3-��ȡ��
	
	
	public void  formatString()
	{
		startDate = DataFormat.formatString(startDate);
		endDate = DataFormat.formatString(endDate);
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public long getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getVariety() {
		return variety;
	}
	public void setVariety(long variety) {
		this.variety = variety;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate.toString();
	}
    
	public void setEndDate(Date endDate) {
		this.endDate = endDate.toString();
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate.toString();
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
}
