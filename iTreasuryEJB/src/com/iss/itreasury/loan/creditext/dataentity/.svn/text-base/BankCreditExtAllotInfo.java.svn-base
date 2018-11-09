package com.iss.itreasury.loan.creditext.dataentity;

import java.sql.Date;
import java.util.HashMap;

/**
* 银行授信分配与调整信息
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotInfo {
	private long id;				//id
	private String contractNo;		//授信合同号
	private String bankName;		//授信银行
	private String year;			//授信年度
	private String startDate;		//起始日期
	private String endDate;			//结束日期
	private String officeCode;		//结算中心代码
	private long officeid ;         //结算中心id
	private String officeName;		//结算中心名称
	private long variety;			//授信品种
	private double balance;			//授信余额
	private long currencyType;		//币种
	private double amount;			//金额
	private long isValid;			//做逻辑删除的标志位
	private long lastModifier;	    //最后修改人id
	private String lastModifyDate;	//最后修改日期）
	private String remark;			//备注
	private HashMap balances = new HashMap(); //所有品种的余额集合
	
	public HashMap getBalances(){
		return this.balances;
	}
	public void setBalances(HashMap balances){
		this.balances = balances;
	}
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getOfficeCode() {
		return this.officeCode;
	}
	
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	public String getOfficeName() {
		return this.officeName;
	}
	
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	public long getVariety() {
		return this.variety;
	}
	
	public void setVariety(long variety) {
		this.variety = variety;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public long getCurrencyType() {
		return this.currencyType;
	}
	
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIsValid() {
		return isValid;
	}

	public void setIsValid(long isValid) {
		this.isValid = isValid;
	}

	public long getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(long lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
}
