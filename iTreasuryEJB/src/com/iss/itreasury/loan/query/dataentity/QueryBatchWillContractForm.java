package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class QueryBatchWillContractForm {
	private long id;					//主键
	private long nrateid;				//利率值id
	private String ratenum;				//利率编号
	private double nrate;				//利率值
	private double nfloatvalue;			//浮动利率
	private double nfixrate;			//固定利率
	private double nexcuterate;			//调整后执行利率
	private Timestamp dtvalidate;		//生效日
	private String reason;				//利率调整原因
	private long ifCheck=0;				//是否勾选"使用原利率",1为使用
	private String inputUserName = "";	//录入人用户名称
	private Timestamp inputDate = null;	//录入时间
	private long bStatus = 0;			//批次表状态
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getBStatus() {
		return bStatus;
	}
	public void setBStatus(long status) {
		bStatus = status;
	}
	public long getIfCheck() {
		return ifCheck;
	}
	public void setIfCheck(long ifCheck) {
		this.ifCheck = ifCheck;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNrateid() {
		return nrateid;
	}
	public void setNrateid(long nrateid) {
		this.nrateid = nrateid;
	}
	public double getNfloatvalue() {
		return nfloatvalue;
	}
	public void setNfloatvalue(double nfloatvalue) {
		this.nfloatvalue = nfloatvalue;
	}
	public double getNfixrate() {
		return nfixrate;
	}
	public void setNfixrate(double nfixrate) {
		this.nfixrate = nfixrate;
	}
	public double getNexcuterate() {
		return nexcuterate;
	}
	public void setNexcuterate(double nexcuterate) {
		this.nexcuterate = nexcuterate;
	}
	public Timestamp getDtvalidate() {
		return dtvalidate;
	}
	public void setDtvalidate(Timestamp dtvalidate) {
		this.dtvalidate = dtvalidate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public double getNrate() {
		return nrate;
	}
	public void setNrate(double nrate) {
		this.nrate = nrate;
	}
	public String getRatenum() {
		return ratenum;
	}
	public void setRatenum(String ratenum) {
		this.ratenum = ratenum;
	}
	

}
