package com.iss.itreasury.loan.bankloan.dataentity;

import java.util.Date;

import com.iss.itreasury.util.DataFormat;

/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BankLoanInfo implements java.io.Serializable{
	
	private long id = -1; 
	private long officeId = -1;
	private String code = "";//内部流水号
	private String loanContractNo = "";//贷款合同号
	private long conferContractNo = 0;//授信合同id
	private String loanClient ="";//贷款单位编号
	private String loanBank = "";//贷款银行
	private long loanType = -1; //贷款类型
	private double amount = 0.0; //贷款金额
	private long currencyID = -1; //贷款金额币种
	private double exchangeRate = 0.0; //与人民币汇率
	private double convertRMB = 0.0; //折合人民币
	private String startDate = ""; //起始日期
	private String endDate = ""; //到期日期
	private long statusID = -1; //状态标志
	private String remark = ""; //备注
	
	private String contractNo = "";//授信合同号
	private String loanClientName = "";//贷款单位名称
	
	private double countPay = 0.0;//放款总额
	private double countRepay = 0.0;//还款总额
	
    //为了查询而设的属性
	String fromStartDate ="";//起始日期由
	String endStartDate ="";//起始日期到
	
	String fromEndDate ="";//到期日期由
	String endEndDate ="";//到期日期到
	
	
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLoanClientName() {
		return loanClientName;
	}
	public void setLoanClientName(String loanClientName) {
		this.loanClientName = loanClientName;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public double getCountPay() {
		return countPay;
	}
	public void setCountPay(double countPay) {
		this.countPay = countPay;
	}
	public double getCountRepay() {
		return countRepay;
	}
	public void setCountRepay(double countRepay) {
		this.countRepay = countRepay;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getConferContractNo() {
		return conferContractNo;
	}
	public void setConferContractNo(long conferContractNo) {
		this.conferContractNo = conferContractNo;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndEndDate() {
		return endEndDate;
	}
	public void setEndEndDate(String endEndDate) {
		this.endEndDate = endEndDate;
	}

	public String getFromStartDate() {
		return fromStartDate;
	}
	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLoanBank() {
		return loanBank;
	}
	public void setLoanBank(String loanBank) {
		this.loanBank = loanBank;
	}
	public String getLoanClient() {
		return loanClient;
	}
	public void setLoanClient(String loanClient) {
		this.loanClient = loanClient;
	}
	public String getLoanContractNo() {
		return loanContractNo;
	}
	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getConvertRMB() {
		return convertRMB;
	}
	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public String getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getFromEndDate() {
		return fromEndDate;
	}
	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}
	public long getLoanType() {
		return loanType;
	}
	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public void setStartDate(Date startDate) {
		this.startDate = DataFormat.formatDate(startDate,1);
	}
	public void setEndDate(Date endDate) {
		this.endDate = DataFormat.formatDate(endDate,1);
	}

}
