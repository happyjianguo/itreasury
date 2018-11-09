package com.iss.itreasury.itreasuryinfo.branch.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 用于存放银行账户（开户行）利率信息，包含数据库所有字段的存取操作
 * @author gmqiu
 *
 */
public class BranchRateInfo extends ITreasuryBaseDataEntity {
	
	private static final long serialVersionUID = -2789852779483184851L;
	
	private long id = -1;	                /** ID，主键 */
	private long nOfficeId = -1; 	        /** 办事处编号ID(默认-1) */
	private long nCurrencyId = -1;	        /** 币种ID(默认-1) */
	private long branchId = -1; 	        /** 银行账户（开户行）ID，来自sett_branch 表的ID(默认-1) */
	private Timestamp dtValidate = null;	/** 利率生效日期 */
	private double mRate = 0.0;		        /** 利率值（年利率）*/
	private long nStatusId = -1;	        /** 状态(默认-1) */
	private long nInputUserId = -1;	        /** 录入人ID(默认-1) */
	private Timestamp dtInputDate = null;	/** 录入日期 */
	private long nCheckUserId = -1;			/** 复核人ID */
	private Timestamp dtCheckDate = null;   /** 复核日期 */
	
	private String sName = "";
	private String sBankAccountCode = "";
	private long nBankType = -1;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getnOfficeId() {
		return nOfficeId;
	}
	public void setnOfficeId(long nOfficeId) {
		this.nOfficeId = nOfficeId;
	}
	public long getnCurrencyId() {
		return nCurrencyId;
	}
	public void setnCurrencyId(long nCurrencyId) {
		this.nCurrencyId = nCurrencyId;
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	public Timestamp getDtValidate() {
		return dtValidate;
	}
	public void setDtValidate(Timestamp dtValidate) {
		this.dtValidate = dtValidate;
	}
	public double getmRate() {
		return mRate;
	}
	public void setmRate(double mRate) {
		this.mRate = mRate;
	}
	public long getnStatusId() {
		return nStatusId;
	}
	public void setnStatusId(long nStatusId) {
		this.nStatusId = nStatusId;
	}
	public long getnInputUserId() {
		return nInputUserId;
	}
	public void setnInputUserId(long nInputUserId) {
		this.nInputUserId = nInputUserId;
	}
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
	}
	public long getnCheckUserId() {
		return nCheckUserId;
	}
	public void setnCheckUserId(long nCheckUserId) {
		this.nCheckUserId = nCheckUserId;
	}
	public Timestamp getDtCheckDate() {
		return dtCheckDate;
	}
	public void setDtCheckDate(Timestamp dtCheckDate) {
		this.dtCheckDate = dtCheckDate;
	}
	
	
	private String branchName = "";          /** 开户行名称  */
	private String nInputUserName = "";     /** 录入人名称  */
	private String nCheckUserName = "";     /** 复核人名称  */

	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getnInputUserName() {
		return nInputUserName;
	}
	public void setnInputUserName(String nInputUserName) {
		this.nInputUserName = nInputUserName;
	}
	public String getnCheckUserName() {
		return nCheckUserName;
	}
	public void setnCheckUserName(String nCheckUserName) {
		this.nCheckUserName = nCheckUserName;
	}
	
	private Timestamp dtValidateStart = null;   /** 利率生效开始日期 */
	private Timestamp dtValidateEnd = null;   /** 利率生效结束日期 */

	public Timestamp getDtValidateStart() {
		return dtValidateStart;
	}
	public void setDtValidateStart(Timestamp dtValidateStart) {
		this.dtValidateStart = dtValidateStart;
	}
	public Timestamp getDtValidateEnd() {
		return dtValidateEnd;
	}
	public void setDtValidateEnd(Timestamp dtValidateEnd) {
		this.dtValidateEnd = dtValidateEnd;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsBankAccountCode() {
		return sBankAccountCode;
	}
	public void setsBankAccountCode(String sBankAccountCode) {
		this.sBankAccountCode = sBankAccountCode;
	}
	public long getnBankType() {
		return nBankType;
	}
	public void setnBankType(long nBankType) {
		this.nBankType = nBankType;
	}
    	
}
