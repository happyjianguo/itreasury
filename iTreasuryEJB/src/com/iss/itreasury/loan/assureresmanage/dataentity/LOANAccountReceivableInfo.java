package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOANAccountReceivableInfo implements Serializable {

	private    long        id                               = -1;  
    
    private    long        gageid                           = -1;     //担保品信息ID
    
    private    long        debtor                           = -1;     //债务人
    
    private    String      debtorName                       = "";     //债务人名称
    
    private    String      contractorinvoicenumber          = "";     //应收账款合同号/发票号
    
    private    double      accountreceivableamount          = 0;      //应收款金额
    
    private    double      accountreceivablebalance         = 0;      //应收款余额
    
    private    Timestamp   returntime                       = null;   //还款期限
    
    private    String      accountname                      = "";     //应收款回款专户户名
    
    private    String      accountnumber                    = "";     //应收款回款专户账号
    
    private    String      operbank                         = "";     //开户行
    
    private    long        transfernotice                   = -1;     //债权转让通知书回函
    
    private    long        accountreceivablepromises        = -1;     //应收款承诺书回函
    
    private    long        officeid                         = -1;     //办事处
    
    private    long        currency                         = -1;     //币种
    
    private    long        inputuserid                      = -1;     //录入人
    
    private    Timestamp   inputdate                        = null;   //录入时间
    
    private    long        status                           = -1;     //状态

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public double getAccountreceivableamount() {
		return accountreceivableamount;
	}

	public void setAccountreceivableamount(double accountreceivableamount) {
		this.accountreceivableamount = accountreceivableamount;
	}

	public double getAccountreceivablebalance() {
		return accountreceivablebalance;
	}

	public void setAccountreceivablebalance(double accountreceivablebalance) {
		this.accountreceivablebalance = accountreceivablebalance;
	}

	public long getAccountreceivablepromises() {
		return accountreceivablepromises;
	}

	public void setAccountreceivablepromises(long accountreceivablepromises) {
		this.accountreceivablepromises = accountreceivablepromises;
	}

	public String getContractorinvoicenumber() {
		return contractorinvoicenumber;
	}

	public void setContractorinvoicenumber(String contractorinvoicenumber) {
		this.contractorinvoicenumber = contractorinvoicenumber;
	}

	public long getCurrency() {
		return currency;
	}

	public void setCurrency(long currency) {
		this.currency = currency;
	}

	public long getDebtor() {
		return debtor;
	}

	public void setDebtor(long debtor) {
		this.debtor = debtor;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	public long getGageid() {
		return gageid;
	}

	public void setGageid(long gageid) {
		this.gageid = gageid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getInputdate() {
		return inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
	}

	public long getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}

	public String getOperbank() {
		return operbank;
	}

	public void setOperbank(String operbank) {
		this.operbank = operbank;
	}

	public Timestamp getReturntime() {
		return returntime;
	}

	public void setReturntime(Timestamp returntime) {
		this.returntime = returntime;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getTransfernotice() {
		return transfernotice;
	}

	public void setTransfernotice(long transfernotice) {
		this.transfernotice = transfernotice;
	}
}
