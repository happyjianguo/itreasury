package com.iss.itreasury.integratedCredit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;

public class CreditGradeQueryInfo {
	private long creditgradeid = -1;
	private String creditcode = "";
	private String clientcode = "";
	private String clientname = "";
	private String creditlevel = "";
	private double creditlevelvalue;//信用等级评估分值
	private String inputuser = "";
	private Timestamp gradedate = null;
	private long status = -1;
	private long ndepartmentid;//部门
	
	public long getNdepartmentid() {
		return ndepartmentid;
	}
	public void setNdepartmentid(long ndepartmentid) {
		this.ndepartmentid = ndepartmentid;
	}
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getCreditcode() {
		return creditcode;
	}
	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}
	public long getCreditgradeid() {
		return creditgradeid;
	}
	public void setCreditgradeid(long creditgradeid) {
		this.creditgradeid = creditgradeid;
	}
	public String getCreditlevel() {
		return creditlevel;
	}
	public void setCreditlevel(String creditlevel) {
		this.creditlevel = creditlevel;
	}
	public String getGradedate() {
		return (gradedate==null?"":DataFormat.formatDate(gradedate));
	}
	public void setGradedate(Timestamp gradedate) {
		this.gradedate = gradedate;
	}
	public String getStatus() {
		return LOANConstant.LoanCreditgrade.getName(status);
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getInputuser() {
		return inputuser;
	}
	public void setInputuser(String inputuser) {
		this.inputuser = inputuser;
	}
	public double getCreditlevelvalue() {
		return creditlevelvalue;
	}
	public void setCreditlevelvalue(double creditlevelvalue) {
		this.creditlevelvalue = creditlevelvalue;
	}
}
