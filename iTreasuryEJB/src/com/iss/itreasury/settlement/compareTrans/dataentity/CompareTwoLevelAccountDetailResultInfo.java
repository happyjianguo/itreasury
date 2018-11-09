package com.iss.itreasury.settlement.compareTrans.dataentity;

import java.util.Date;

import com.iss.itreasury.settlement.comparisontrans.bizlogic.ComparisonTransBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.SessionMng;



public class CompareTwoLevelAccountDetailResultInfo {
	//显示信息
	private String settTransNo = "";//交易号
	private long settDirectType = -1; //借贷方向
	private double settAmount =Double.NaN;//交易金额
	private String settSabstract = "";
	private String settOppAccountNO = "";
	private String settOppAccountNAME = "";
	private Date dtDate = null;
	
	private String bankTransNo = "";//交易号
	private long bankDirectType = -1; //借贷方向
	private double bankAmount =Double.NaN;//交易金额
	private String bankSabstract = "";
	private String bankOppAccountNO = "";
	private String bankOppAccountNAME = "";
	public double getBankAmount() {
		return bankAmount;
	}
	public void setBankAmount(double bankAmount) {
		this.bankAmount = bankAmount;
	}
	public long getBankDirectType() {
		return bankDirectType;
	}
	public void setBankDirectType(long bankDirectType) {
		this.bankDirectType = bankDirectType;
	}
	public String getBankTransNo() {
		return bankTransNo;
	}
	public void setBankTransNo(String bankTransNo) {
		this.bankTransNo = bankTransNo;
	}
	public Date getDtDate() {
		return dtDate;
	}
	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}
	public double getSettAmount() {
		return settAmount;
	}
	public void setSettAmount(double settAmount) {
		this.settAmount = settAmount;
	}
	public long getSettDirectType() {
		return settDirectType;
	}
	public void setSettDirectType(long settDirectType) {
		this.settDirectType = settDirectType;
	}
	public String getSettTransNo() {
		return settTransNo;
	}
	public void setSettTransNo(String settTransNo) {
		this.settTransNo = settTransNo;
	}
	public String getBankOppAccountNAME() {
		return bankOppAccountNAME;
	}
	public void setBankOppAccountNAME(String bankOppAccountNAME) {
		this.bankOppAccountNAME = bankOppAccountNAME;
	}
	public String getBankOppAccountNO() {
		return bankOppAccountNO;
	}
	public void setBankOppAccountNO(String bankOppAccountNO) {
		this.bankOppAccountNO = bankOppAccountNO;
	}
	public String getBankSabstract() {
		return bankSabstract;
	}
	public void setBankSabstract(String bankSabstract) {
		this.bankSabstract = bankSabstract;
	}
	public String getSettOppAccountNAME() {
		return settOppAccountNAME;
	}
	public void setSettOppAccountNAME(String settOppAccountNAME) {
		this.settOppAccountNAME = settOppAccountNAME;
	}
	public String getSettOppAccountNO() {
		return settOppAccountNO;
	}
	public void setSettOppAccountNO(String settOppAccountNO) {
		this.settOppAccountNO = settOppAccountNO;
	}
	public String getSettSabstract() {
		return settSabstract;
	}
	public void setSettSabstract(String settSabstract) {
		this.settSabstract = settSabstract;
	}


}
