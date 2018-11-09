package com.iss.itreasury.itreasuryinfo.subject.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 * 用于存放资金项目余额信息，包含数据库所有字段的存取操作
 * @author gmqiu
 *
 */
public class SubjectBalanceInfo  extends ITreasuryBaseDataEntity {

	private static final long serialVersionUID = 6243401635147596935L;
	
	private long id = -1;                       /** ID，主键 */
	private long nOfficeId = -1;	            /** 办事处编号ID */
	private long nCurrencyId = -1;	            /** 币种ID */
	private long nSubjectID = -1;			    /** 资金项目ID(1 自有资金,2 信贷资产转让,3 准备金,4 基金、债券等) */
	private Timestamp dtBalanceDate = null;		/** 余额日期 */
	private double mBalance = 0.0;		        /** 当日余额 */
	private long nStatusId = -1;	            /** 记录状态 */
	private long nInputUserId = -1;	            /** 录入人ID */
	private Timestamp dtInputDate = null;		/** 录入日期 */
	private long nCheckUserId = -1;			    /** 复核人ID */
	private Timestamp dtCheckDate = null;		/** 复核日期 */
	
	private long nKind = -1;                    /** 项目性质 (1 资金来源 2 资金占用)*/
	private String sSubjectName = "";           /** 项目名称 (自有资金,信贷资产转让,准备金,基金、债券等)*/
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getnOfficeId() {
		return nOfficeId;
	}
	public void setnOfficeId(long nOfficeId) {
		this.nOfficeId = nOfficeId;
		putUsedField("id", id);
	}
	public long getnCurrencyId() {
		return nCurrencyId;
	}
	public void setnCurrencyId(long nCurrencyId) {
		this.nCurrencyId = nCurrencyId;
		putUsedField("id", id);
	}
	public long getnSubjectID() {
		return nSubjectID;
	}
	public void setnSubjectID(long nSubjectID) {
		this.nSubjectID = nSubjectID;
		putUsedField("nSubjectID", nSubjectID);
	}
	public Timestamp getDtBalanceDate() {
		return dtBalanceDate;
	}
	public void setDtBalanceDate(Timestamp dtBalanceDate) {
		this.dtBalanceDate = dtBalanceDate;
		putUsedField("dtBalanceDate", dtBalanceDate);
	}
	public double getmBalance() {
		return mBalance;
	}
	public void setmBalance(double mBalance) {
		this.mBalance = mBalance;
		putUsedField("mBalance", mBalance);
	}
	public long getnStatusId() {
		return nStatusId;
	}
	public void setnStatusId(long nStatusId) {
		this.nStatusId = nStatusId;
		putUsedField("nStatusId", nStatusId);
	}
	public long getnInputUserId() {
		return nInputUserId;
	}
	public void setnInputUserId(long nInputUserId) {
		this.nInputUserId = nInputUserId;
		putUsedField("nInputUserId", nInputUserId);
	}
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
		putUsedField("dtInputDate", dtInputDate);
	}
	public long getnCheckUserId() {
		return nCheckUserId;
	}
	public void setnCheckUserId(long nCheckUserId) {
		this.nCheckUserId = nCheckUserId;
		putUsedField("nCheckUserId", nCheckUserId);
	}
	public Timestamp getDtCheckDate() {
		return dtCheckDate;
	}
	public void setDtCheckDate(Timestamp dtCheckDate) {
		this.dtCheckDate = dtCheckDate;
		putUsedField("dtCheckDate", dtCheckDate);
	}
	
	public long getnKind() {
		return nKind;
	}
	public void setnKind(long nKind) {
		this.nKind = nKind;
	}
	public String getsSubjectName() {
		return sSubjectName;
	}
	public void setsSubjectName(String sSubjectName) {
		this.sSubjectName = sSubjectName;
	}
	
}
