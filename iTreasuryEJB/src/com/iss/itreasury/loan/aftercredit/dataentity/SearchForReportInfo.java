package com.iss.itreasury.loan.aftercredit.dataentity;
import java.sql.Timestamp;
public class SearchForReportInfo {
	
	
	private String clientFrom = "";
	private String clientTo = "";
	private String DateFrom = null; 
	private String DateTo = null; 
	private String CodeFrom = "";
	private String CodeTo = "";
	private String CreditType = "";
	private long RiskType = -1;
	private long nofficeID = -1;
	private long ncurrencyID = -1;
	//贷后检查区间
	private String CheckType = "";
	private String CheckYear = "";
	private String Checkqm = "";
	private String CheckTemp = "";
	//add by llliu 2008-9-17 
	private long inputuserid = -1;
	private long nextcheckuserid = -1;
	
	private long nuserid = -1;
	
	private long status = -1;
	
	//add by lll 2008-12-20
	private long ActionID = -1;//操作类型 
	
	
	public long getActionID() {
		return ActionID;
	}
	public void setActionID(long actionID) {
		ActionID = actionID;
	}
	public String getClientFrom() {
		return clientFrom;
	}
	public void setClientFrom(String clientFrom) {
		this.clientFrom = clientFrom;
	}
	public String getClientTo() {
		return clientTo;
	}
	public void setClientTo(String clientTo) {
		this.clientTo = clientTo;
	}
	public String getCodeFrom() {
		return CodeFrom;
	}
	public void setCodeFrom(String codeFrom) {
		CodeFrom = codeFrom;
	}
	public String getCodeTo() {
		return CodeTo;
	}
	public void setCodeTo(String codeTo) {
		CodeTo = codeTo;
	}
	public String getCreditType() {
		return CreditType;
	}
	public void setCreditType(String creditType) {
		CreditType = creditType;
	}

	
	public long getRiskType() {
		return RiskType;
	}
	public void setRiskType(long riskType) {
		RiskType = riskType;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getDateFrom() {
		return DateFrom;
	}
	public void setDateFrom(String dateFrom) {
		DateFrom = dateFrom;
	}
	public String getDateTo() {
		return DateTo;
	}
	public void setDateTo(String dateTo) {
		DateTo = dateTo;
	}
	public String getCheckqm() {
		return Checkqm;
	}
	public void setCheckqm(String checkqm) {
		Checkqm = checkqm;
	}
	public String getCheckTemp() {
		return CheckTemp;
	}
	public void setCheckTemp(String checkTemp) {
		CheckTemp = checkTemp;
	}
	public String getCheckType() {
		return CheckType;
	}
	public void setCheckType(String checkType) {
		CheckType = checkType;
	}
	public String getCheckYear() {
		return CheckYear;
	}
	public void setCheckYear(String checkYear) {
		CheckYear = checkYear;
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
	}
	public long getNextcheckuserid() {
		return nextcheckuserid;
	}
	public void setNextcheckuserid(long nextcheckuserid) {
		this.nextcheckuserid = nextcheckuserid;
	}
	public long getNuserid() {
		return nuserid;
	}
	public void setNuserid(long nuserid) {
		this.nuserid = nuserid;
	}
	public long getNofficeID() {
		return nofficeID;
	}
	public void setNofficeID(long nofficeID) {
		this.nofficeID = nofficeID;
	}
	public long getNcurrencyID() {
		return ncurrencyID;
	}
	public void setNcurrencyID(long ncurrencyID) {
		this.ncurrencyID = ncurrencyID;
	}
}
