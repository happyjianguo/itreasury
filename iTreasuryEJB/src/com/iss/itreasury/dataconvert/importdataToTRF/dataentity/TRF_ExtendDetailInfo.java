/*
 * Created on 2006-4-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TRF_ExtendDetailInfo {
    
    //id
    private long id=-1;
	//展期对应合同编号
    private String loanContractCode="";
	//展期合同编号
    private String extendContractCode="";
	//展期金额
    private double amount=0.0;
	//展期执行利率%
    private double executeInterestRate=0.0;
	//展期起始日期
    private Timestamp startDate=null;
	//展期结束日期
    private Timestamp endDate=null;
	//展期明细中的展期月数
    private long extendTerm=-1;


    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    public double getExecuteInterestRate() {
        return executeInterestRate;
    }
    public void setExecuteInterestRate(double executeInterestRate) {
        this.executeInterestRate = executeInterestRate;
    }
    public String getExtendContractCode() {
        return extendContractCode;
    }
    public void setExtendContractCode(String extendContractCode) {
        this.extendContractCode = extendContractCode;
    }
    public long getExtendTerm() {
        return extendTerm;
    }
    public void setExtendTerm(long extendTerm) {
        this.extendTerm = extendTerm;
    }
    public String getLoanContractCode() {
        return loanContractCode;
    }
    public void setLoanContractCode(String loanContractCode) {
        this.loanContractCode = loanContractCode;
    }
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
