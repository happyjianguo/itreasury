/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.audit.settlementaudit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zcwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SettlementAuditCondition implements Serializable
{

	private long officeID = -1; // 办事处
	private long currencyID = -1; // 币种
	private String AccountTypeSet = ""; // 账户类型组 
	private String amountTypes = ""; //业务范围（金额类型）
	private String amountShowTypes =""; //查询选项(金额显示列)
	private String startClientCode = ""; // 客户号-从
	private String endClientCode = ""; // 客户号-到
	private String startAccountNo = ""; // 账户号-从
	private String endAccountNo = ""; // 账户号=到
	private String startContractCode = "";
	private String endContractCode = "";
	private Timestamp selectDateFrom = null; //核对区间开始
	private Timestamp selectDateTo = null; //核对区间结束
	public String getAccountTypeSet() {
		return AccountTypeSet;
	}
	public void setAccountTypeSet(String accountTypeSet) {
		AccountTypeSet = accountTypeSet;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public String getEndAccountNo() {
		return endAccountNo;
	}
	public void setEndAccountNo(String endAccountNo) {
		this.endAccountNo = endAccountNo;
	}
	public String getEndClientCode() {
		return endClientCode;
	}
	public void setEndClientCode(String endClientCode) {
		this.endClientCode = endClientCode;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public Timestamp getSelectDateFrom() {
		return selectDateFrom;
	}
	public void setSelectDateFrom(Timestamp selectDateFrom) {
		this.selectDateFrom = selectDateFrom;
	}
	public Timestamp getSelectDateTo() {
		return selectDateTo;
	}
	public void setSelectDateTo(Timestamp selectDateTo) {
		this.selectDateTo = selectDateTo;
	}
	public String getStartAccountNo() {
		return startAccountNo;
	}
	public void setStartAccountNo(String startAccountNo) {
		this.startAccountNo = startAccountNo;
	}
	public String getStartClientCode() {
		return startClientCode;
	}
	public void setStartClientCode(String startClientCode) {
		this.startClientCode = startClientCode;
	}
	public String getEndContractCode() {
		return endContractCode;
	}
	public void setEndContractCode(String endContractCode) {
		this.endContractCode = endContractCode;
	}
	public String getStartContractCode() {
		return startContractCode;
	}
	public void setStartContractCode(String startContractCode) {
		this.startContractCode = startContractCode;
	}
	public String getAmountShowTypes() {
		return amountShowTypes;
	}
	public void setAmountShowTypes(String amountShowTypes) {
		this.amountShowTypes = amountShowTypes;
	}
	public String getAmountTypes() {
		return amountTypes;
	}
	public void setAmountTypes(String amountTypes) {
		this.amountTypes = amountTypes;
	}
	
	
}
