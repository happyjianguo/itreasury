package com.iss.itreasury.loan.aftercredit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AfterCreditReportInfo extends ITreasuryBaseDataEntity implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long id =-1;
    private String checkreportcode ="";
    private long clientid =-1;
    private long loancontractid =-1;
    private long checkmode =-1;
    private String checkyear ="";
    private long checkqm =-1;
    private Timestamp checktemp = null;
    private long checktype =-1;
    private String changecircs ="";
    private String creditnote ="";
    private String propertyrightclass ="";
    private String personnelclass ="";
    private String investclass ="";
    private String lawclass ="";
    private String otherclass ="";
    private long advice =-1;
    private String conclusion ="";
    private long status =-1;
    private long inputuserid =-1;
    private Timestamp inputdate = null;
    private long officeid =-1;
    private long currencyid =-1;
    private long nextcheckuserid =-1;
    private long nextchecklevel =-1;
    private long islowlevel =-1;
    private long contractID = -1;//合同ID
    private String ids = "";//已上传财务报表ID值
//  add by  wxsu  审批流使用
    private String heckOpinion="";
    private long UserID = -1;
    private long lAction = -1;
    private long nsubTypeID = -1;
    //add 2008-9-2
    private String clientcode = "";
    private String clientname = "";
    private String contractcode = "";
    private Timestamp effectDate = null;//五级分类生效日期
    private String department = "";
    
// added by mk 2010-01-12
    private long checkuserid =-1;//审核人
    private Timestamp checkdate = null;//审核时间
    
    GageAnalyseInfo gageanalyse = null;
    
    //add by dwj 20081125
    private String lastCheckUser = "";
    //end by dwj 20081125
    public InutParameterInfo inutParameterInfo = null;
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
		putUsedField("department", department);
	}
	public String getHeckOpinion() {
		return heckOpinion;
	}
	public void setHeckOpinion(String heckOpinion) {
		this.heckOpinion = heckOpinion;
		putUsedField("heckOpinion", heckOpinion);		
	}
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
		putUsedField("UserID", UserID);
	}
	public long getAdvice() {
		return advice;
	}
	public void setAdvice(long advice) {
		this.advice = advice;
		putUsedField("advice", advice);
	}
	public String getChangecircs() {
		return changecircs;
	}
	public void setChangecircs(String changecircs) {
		this.changecircs = changecircs;
		putUsedField("changecircs", changecircs);
	}
	public long getCheckmode() {
		return checkmode;
	}
	public void setCheckmode(long checkmode) {
		this.checkmode = checkmode;
		putUsedField("checkmode", checkmode);
	}
	public long getCheckqm() {
		return checkqm;
	}
	public void setCheckqm(long checkqm) {
		this.checkqm = checkqm;
		putUsedField("checkqm", checkqm);
	}
	public String getCheckreportcode() {
		return checkreportcode;
	}
	public void setCheckreportcode(String checkreportcode) {
		this.checkreportcode = checkreportcode;
		putUsedField("checkreportcode", checkreportcode);
	}
	public Timestamp getChecktemp() {
		return checktemp;
	}
	public void setChecktemp(Timestamp checktemp) {
		this.checktemp = checktemp;
		putUsedField("checktemp", checktemp);
	}
	public long getChecktype() {
		return checktype;
	}
	public void setChecktype(long checktype) {
		this.checktype = checktype;
		putUsedField("checktype", checktype);
	}
	public String getCheckyear() {
		return checkyear;
	}
	public void setCheckyear(String checkyear) {
		this.checkyear = checkyear;
		putUsedField("checkyear", checkyear);
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
		putUsedField("clientid", clientid);
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
		putUsedField("conclusion", conclusion);
	}
	public String getCreditnote() {
		return creditnote;
	}
	public void setCreditnote(String creditnote) {
		this.creditnote = creditnote;
		putUsedField("creditnote", creditnote);
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid", currencyid);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate", inputdate);
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid", inputuserid);
	}
	public String getInvestclass() {
		return investclass;
	}
	public void setInvestclass(String investclass) {
		this.investclass = investclass;
		putUsedField("investclass", investclass);
	}
	public long getIslowlevel() {
		return islowlevel;
	}
	public void setIslowlevel(long islowlevel) {
		this.islowlevel = islowlevel;
		putUsedField("islowlevel", islowlevel);
	}
	public String getLawclass() {
		return lawclass;
	}
	public void setLawclass(String lawclass) {
		this.lawclass = lawclass;
		putUsedField("lawclass", lawclass);
	}
	public long getLoancontractid() {
		return loancontractid;
	}
	public void setLoancontractid(long loancontractid) {
		this.loancontractid = loancontractid;
		putUsedField("loancontractid", loancontractid);
	}
	public long getNextchecklevel() {
		return nextchecklevel;
	}
	public void setNextchecklevel(long nextchecklevel) {
		this.nextchecklevel = nextchecklevel;
		putUsedField("nextchecklevel", nextchecklevel);
	}
	public long getNextcheckuserid() {
		return nextcheckuserid;
	}
	public void setNextcheckuserid(long nextcheckuserid) {
		this.nextcheckuserid = nextcheckuserid;
		putUsedField("nextcheckuserid", nextcheckuserid);
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid", officeid);
	}
	public String getOtherclass() {
		return otherclass;
	}
	public void setOtherclass(String otherclass) {
		this.otherclass = otherclass;
		putUsedField("otherclass", otherclass);
	}
	public String getPersonnelclass() {
		return personnelclass;
	}
	public void setPersonnelclass(String personnelclass) {
		this.personnelclass = personnelclass;
		putUsedField("personnelclass", personnelclass);
	}
	public String getPropertyrightclass() {
		return propertyrightclass;
	}
	public void setPropertyrightclass(String propertyrightclass) {
		this.propertyrightclass = propertyrightclass;
		putUsedField("propertyrightclass", propertyrightclass);
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	public long getLAction() {
		return lAction;
	}
	public void setLAction(long action) {
		lAction = action;
		putUsedField("lAction", lAction);
	}
	
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
		putUsedField("clientcode", clientcode);
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
		putUsedField("clientname", clientname);
	}
	public String getContractcode() {
		return contractcode;
	}
	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
		putUsedField("contractcode", contractcode);
	}
	public GageAnalyseInfo getGageanalyse() {
		return gageanalyse;
	}
	public void setGageanalyse(GageAnalyseInfo gageanalyse) {
		this.gageanalyse = gageanalyse;
		putUsedField("gageanalyse", gageanalyse);
	}
	public String getLastCheckUser() {
		return lastCheckUser;
	}
	public void setLastCheckUser(String lastCheckUser) {
		this.lastCheckUser = lastCheckUser;
		putUsedField("lastCheckUser", lastCheckUser);
	}
	public long getCheckuserid() {
		return checkuserid;
	}
	public void setCheckuserid(long checkuserid) {
		this.checkuserid = checkuserid;
		putUsedField("checkuserid", checkuserid);
	}
	public Timestamp getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
		putUsedField("checkdate", checkdate);
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public Timestamp getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}
	public long getNsubTypeID() {
		return nsubTypeID;
	}
	public void setNsubTypeID(long nsubTypeID) {
		this.nsubTypeID = nsubTypeID;
	}
	
}
