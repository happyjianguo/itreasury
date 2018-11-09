package com.iss.itreasury.loan.creditext.dataentity;

import java.sql.Date;

import com.iss.itreasury.util.DataFormat;

public class BankCreditExtResultInfo {
	private long 	no = -1;			// 序号
	private String	contractNo = "";			//授信合同号	
	private String	year = "";					//授信年度	
	private String 	bankName = "";				//授信银行	
	private String 	company = "";				//授信主体	
	private long 	variety = -1; 				// 授信品种  1-短期贷款2-中长期贷款3-信用证4-保函5-信贷证明6-承兑汇票
	private long 	currencyType = -1; 			// 币种 
	private double 	amount = 0.0; 				// 金额 
	private String	startDate = "";				//起始日期	
	private String	endDate = "";				//结束日期	
	private String	operationDate = "";			//办理日期	
	private long		status	= -1;				//授信状态		1-执行中2-已结束3-已取消
	
	
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
