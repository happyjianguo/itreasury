package com.iss.itreasury.project.wisgfc.settlement.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SpecialContractInfo extends ITreasuryBaseDataEntity{
	private static final long serialVersionUID = -7071561044294425781L;

	private long id;
	private long NOfficeID;    //办事处id
	private long NCurrencyID;  //币种id
	private String SCode;      //合同号
	private long NPayClientID;      //付款单位id
	private long NPayAccountID;      //付款账号id
	private Timestamp DTEndDate;     //结束日期
	private String SAbstract;        //用途
	private String SMemo;           //摘要
	private long NStatusID;    //状态
	
	String q_contractCode = "";      //查询条件-合同号
	long q_payClientID = -1;    //查询条件-付款单位id
	long q_payAccountID = -1;     //查询条件-账户号id
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public long getNOfficeID() {
		return NOfficeID;
	}
	public void setNOfficeID(long officeID) {
		NOfficeID = officeID;
		putUsedField("NOfficeID",officeID);
	}
	public long getNCurrencyID() {
		return NCurrencyID;
	}
	public void setNCurrencyID(long currencyID) {
		NCurrencyID = currencyID;
		putUsedField("NCurrencyID",NCurrencyID);
	}
	public String getSCode() {
		return SCode;
	}
	public void setSCode(String code) {
		SCode = code;
		putUsedField("SCode",SCode);
	}
	public long getNPayClientID() {
		return NPayClientID;
	}
	public void setNPayClientID(long payClientID) {
		NPayClientID = payClientID;
		putUsedField("NPayClientID",NPayClientID);
	}
	public long getNPayAccountID() {
		return NPayAccountID;
	}
	public void setNPayAccountID(long payAccountID) {
		NPayAccountID = payAccountID;
		putUsedField("NPayAccountID",NPayAccountID);
	}
	public Timestamp getDTEndDate() {
		return DTEndDate;
	}
	public void setDTEndDate(Timestamp endDate) {
		DTEndDate = endDate;
		putUsedField("DTEndDate",DTEndDate);
	}
	public String getSAbstract() {
		return SAbstract;
	}
	public void setSAbstract(String abstract1) {
		SAbstract = abstract1;
		putUsedField("SAbstract",SAbstract);
	}
	public String getSMemo() {
		return SMemo;
	}
	public void setSMemo(String memo) {
		SMemo = memo;
		putUsedField("SMemo",SMemo);
	}
	public long getNStatusID() {
		return NStatusID;
	}
	public void setNStatusID(long statusID) {
		NStatusID = statusID;
		putUsedField("NStatusID",NStatusID);
	}
	/*
	public String getQ_contractCode() {
		return Q_contractCode;
	}
	public void setQ_contractCode(String code) {
		Q_contractCode = code;
		putUsedField("SCode",Q_contractCode);
	}
	public long getQ_payClientID() {
		return Q_payClientID;
	}
	public void setQ_payClientID(long clientID) {
		Q_payClientID = clientID;
		putUsedField("NPayClientID",Q_payClientID);
	}
	public long getQ_payAccountID() {
		return Q_payAccountID;
	}
	public void setQ_payAccountID(long accountID) {
		Q_payAccountID = accountID;
		putUsedField("NPayAccountID",Q_payAccountID);
	}
	*/
	public String getQ_contractCode() {
		return q_contractCode;
	}
	public void setQ_contractCode(String code) {
		q_contractCode = code;
		putUsedField("SCode",q_contractCode);
	}
	public long getQ_payClientID() {
		return q_payClientID;
	}
	public void setQ_payClientID(long clientID) {
		q_payClientID = clientID;
		putUsedField("NPayClientID",q_payClientID);
	}
	public long getQ_payAccountID() {
		return q_payAccountID;
	}
	public void setQ_payAccountID(long accountID) {
		q_payAccountID = accountID;
		putUsedField("NPayAccountID",q_payAccountID);
	}
	
	

}
