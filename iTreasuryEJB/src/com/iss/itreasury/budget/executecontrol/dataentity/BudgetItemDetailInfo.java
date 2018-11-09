/*
 * Created on 2005-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.executecontrol.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BudgetItemDetailInfo  extends BudgetBaseDataEntity {

    private long itemID = -1;					//项目ID
    private String itemNo = "";					//项目编号
    private String itemName = "";				//项目名称
    private String transNo = "";				//交易号
    private String contractCode = "";			//合同号/存单号
    private String payFormCode = "";			//放款通知单号
    private long clientID = -1;					//客户/业务单位编号
    private long accountID = -1;				//账户号/资金账户
    private long accountTypeID = -1;			//账户类型
    private double budgetAmount = 0.0;			//预算额
    private double excuteAmount = 0.0;			//执行数
    private Timestamp executeDate = null;		//执行日期
    private long dataSource = -1;				//数据来源
    private long statusID;                      //状态ID
    private String remark="";					//摘要
    private int rowNum=-1;						//行号
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark", remark);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
		putUsedField("accountID", accountID);
	}
	public long getAccountTypeID() {
		return accountTypeID;
	}
	public void setAccountTypeID(long accountTypeID) {
		
		this.accountTypeID = accountTypeID;
		putUsedField("accountTypeID", accountTypeID);
	}
	public double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
		putUsedField("budgetAmount", budgetAmount);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
		putUsedField("contractCode", contractCode);
	}
	public long getDataSource() {
		return dataSource;
	}
	public void setDataSource(long dataSource) {
		this.dataSource = dataSource;
		putUsedField("dataSource", dataSource);
	}
	public double getExcuteAmount() {
		return excuteAmount;
	}
	public void setExcuteAmount(double excuteAmount) {
		this.excuteAmount = excuteAmount;
		putUsedField("excuteAmount", excuteAmount);
	}
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
		putUsedField("executeDate", executeDate);
	}
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
		putUsedField("itemID", itemID);
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
		putUsedField("itemName", itemName);
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
		putUsedField("itemNo", itemNo);
	}
	public String getPayFormCode() {
		return payFormCode;
	}
	public void setPayFormCode(String payFormCode) {
		this.payFormCode = payFormCode;
		putUsedField("payFormCode", payFormCode);
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
		putUsedField("transNo", transNo);
	}
}
