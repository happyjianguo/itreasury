/*
 * Created on 2005-6-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QBudgetResultInfo implements Serializable{
	
	
	private long clientID = -1;				//单位ID
	private String clientNo="";				//单位编号
	private String clientName="";			//单位名称
    private long	itemID = -1;			//预算项目ID
    private String itemNo = "";				//项目编号
    private String itemName = "";			//项目名称
    private double originalAmount = 0.0;	//原始金额
    private double[] adjustAmountArray = null;	//调整金额数组
    private double adjustAmount = 0.0;			//调整金额
    private double budgetAmount = 0.0;			//实际预算金额
    private double executeAmout = 0.0;			//实际执行金额
    private double executePercent = 0.0;		//执行比例
    private double adjustPercent = 0.0;		//调整比例
    private double budgetStructure = 0.0;		//预算结构比例
    private double executeStructure = 0.0; 		//执行结构比例
    
    //预算单位对比分析字段
    private double bBudgetAmount = 0.0;			//实际预算金额
    private double bExecuteAmout = 0.0;			//实际执行金额
    private double diffBudgetAmount = 0.0;		//预算差额
    private double diffExecuteAmount = 0.0; 	//执行差额
    private double diffBudgetAmountPercent = 0.0;	//预算差额比例
    private double diffExecuteAmountPercent = 0.0;	//执行差额比例
    
    //预算滞留比例分析
    private double inAmount = 0.0;					//内转外金额
    private double outAmount = 0.0;					//对外支付金额
    private double capitalPercent = 0.0;			//资金集中比例
    private double bankResortPercent = 0.0;			//银行滞留比例 
    private double budgetResortPercent = 0.0;		//预算滞留比例
    private double budgetOutPercent = 0.0;			//预算支出比例
    //预算执行情况分析
    private Timestamp executeDate = null;		//执行日期
    private String transNo = null;		//交易号
    private String contractCode = null;		//票据号码
    private String remark = null;		//摘要

    
    
    
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public double getAdjustAmount() {
		return adjustAmount;
	}
	public void setAdjustAmount(double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	public double[] getAdjustAmountArray() {
		return adjustAmountArray;
	}
	public void setAdjustAmountArray(double[] adjustAmountArray) {
		this.adjustAmountArray = adjustAmountArray;
	}
	public double getAdjustPercent() {
		return adjustPercent;
	}
	public void setAdjustPercent(double adjustPercent) {
		this.adjustPercent = adjustPercent;
	}
	public double getBankResortPercent() {
		return bankResortPercent;
	}
	public void setBankResortPercent(double bankResortPercent) {
		this.bankResortPercent = bankResortPercent;
	}
	public double getBBudgetAmount() {
		return bBudgetAmount;
	}
	public void setBBudgetAmount(double budgetAmount) {
		bBudgetAmount = budgetAmount;
	}
	public double getBExecuteAmout() {
		return bExecuteAmout;
	}
	public void setBExecuteAmout(double executeAmout) {
		bExecuteAmout = executeAmout;
	}
	public double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public double getBudgetOutPercent() {
		return budgetOutPercent;
	}
	public void setBudgetOutPercent(double budgetOutPercent) {
		this.budgetOutPercent = budgetOutPercent;
	}
	public double getBudgetResortPercent() {
		return budgetResortPercent;
	}
	public void setBudgetResortPercent(double budgetResortPercent) {
		this.budgetResortPercent = budgetResortPercent;
	}
	public double getBudgetStructure() {
		return budgetStructure;
	}
	public void setBudgetStructure(double budgetStructure) {
		this.budgetStructure = budgetStructure;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public double getDiffBudgetAmount() {
		return diffBudgetAmount;
	}
	public void setDiffBudgetAmount(double diffBudgetAmount) {
		this.diffBudgetAmount = diffBudgetAmount;
	}
	public double getDiffBudgetAmountPercent() {
		return diffBudgetAmountPercent;
	}
	public void setDiffBudgetAmountPercent(double diffBudgetAmountPercent) {
		this.diffBudgetAmountPercent = diffBudgetAmountPercent;
	}
	public double getDiffExecuteAmount() {
		return diffExecuteAmount;
	}
	public void setDiffExecuteAmount(double diffExecuteAmount) {
		this.diffExecuteAmount = diffExecuteAmount;
	}
	public double getDiffExecuteAmountPercent() {
		return diffExecuteAmountPercent;
	}
	public void setDiffExecuteAmountPercent(double diffExecuteAmountPercent) {
		this.diffExecuteAmountPercent = diffExecuteAmountPercent;
	}
	public double getExecuteAmout() {
		return executeAmout;
	}
	public void setExecuteAmout(double executeAmout) {
		this.executeAmout = executeAmout;
	}
	public double getExecutePercent() {
		return executePercent;
	}
	public void setExecutePercent(double executePercent) {
		this.executePercent = executePercent;
	}
	public double getExecuteStructure() {
		return executeStructure;
	}
	public void setExecuteStructure(double executeStructure) {
		this.executeStructure = executeStructure;
	}
	public double getInAmount() {
		return inAmount;
	}
	public void setInAmount(double inAmount) {
		this.inAmount = inAmount;
	}
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public double getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}
	public double getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(double outAmount) {
		this.outAmount = outAmount;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	/**
	 * @return Returns the capitalPercent.
	 */
	public double getCapitalPercent() {
		return capitalPercent;
	}
	/**
	 * @param capitalPercent The capitalPercent to set.
	 */
	public void setCapitalPercent(double capitalPercent) {
		this.capitalPercent = capitalPercent;
	}
}
