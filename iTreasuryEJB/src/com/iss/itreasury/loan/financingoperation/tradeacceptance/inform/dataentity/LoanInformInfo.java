package com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.loan.financingoperation.tradeacceptance.protocol.dataentity.LoanProtocolInfo;

public class LoanInformInfo implements Serializable{
	
	 private long id = -1;//ID	NUMBER primary key,
	 private long ncontractId = -1;//nContractID Number,--票据承兑协议ID
	 private long nnoteTypeId = -1;//nNoteTypeID Number,--业务通知单类型(1收取手续费 2到期承兑 3垫付本息收回)
	 private String scode = "";//sCode Varchar2(10),--业务通知单编号(例：001)
	 private Timestamp dtExecuteDate = null;//dtExecuteDate Date,--执行日期
	 private Timestamp dtOutdate = null;//dtOutdate Date,--出票日期
	 private Timestamp dtTodate = null;//dtTodate Date,--到日期
	 private double namount = -1;//nAmount Number(21,6),--交易金额(1 手续费 2 承兑金额 3 本息总额)
	 private long npayAccountID = -1;//nPayAccountID number,--付款内部账户ID
	 private String spayBankAccountNO = "";//sPayBankAccountNO Varchar2(30),--付款银行账号
	 private String spayBankAccountName = "";//sPayBankAccountName Varchar2(100),--付款银行账户名称
	 private String spayBankName = "";//sPayBankName Varchar2(100),--付款账户开户行
	 private long nrecAccountID = -1;//nRecAccountID number,--收款内部账户ID
	 private String srecBankAccountNO = "";//sRecBankAccountNO Varchar2(30),--收款银行账号
	 private String srecBankAccountName = "";//sRecBankAccountName Varchar2(100),--收款银行账户名称
	 private String srecBankName = "";//sRecBankName Varchar2(100),--收款银行名称
	 private String srecBankProvince = "";//sRecBankProvince Varchar2(50),--收款开户行所在省
	 private String srecBankCity = "";//sRecBankCity Varchar2(50),--收款开户行所在市
	 private double ncapitalAmount = -1;// Number(21,6),--本金 //2008-07-23
	 private long noverdueDay = -1;// Number,--逾期天数
	 private double noverDueRate = -1;// Number(15,12),--逾期利率 //2008-07-23
	 private double ninterestAmount = -1;// Number(21,6),--本次收取利息 //2008-07-23
	 private long nextcheckuserid = -1;// NUMBER,--下一个审核人
	 private long nextchecklevel = -1;// NUMBER,--下一个审批级别
	 private long islowlevel = -1;// NUMBER,--审批流程
	 private long officeid = -1;//NUMBER,--办事处
	 private long currencyid =-1;// NUMBER,--币种
	 private long status = -1;// NUMBER,--状态
	 private long inputuserid = -1;// NUMBER,--录入人
	 private Timestamp inputdate = null;// DATE--录入时间
	 
	 private LoanProtocolInfo loanProtocolInfo = null;
	 
	 //显示用
	 private double poundage = 0.0;//承兑手续费	Poundage	Number(21,6)
	 private double npoundage = 0.0;//未收手续费	Npoundage	Number(21,6)
	 
	 private double noverdueratesum =0.0;//利息和
	 private double ninterestamountsum = 0.0;//已收回利息
	 private double nninterestamountsum = 0.0;//未收回利息
	 
//	 add dwj 20080726 
	 private String fsaccountno = "";//内部帐户编号(付款方)
	 private String fsaccountnoname = "";//内部帐户名称(付款方)
//	 end dwj 20080726
	 
	 
//	 add dwj 20080726 //审批流使用
    private String heckOpinion="";//审批建议
    private long userID=-1;//当前登陆的用户
//	 end dwj 20080726
	 
    //add dwj 20081029
    private double nncapitalamount = 0.0;//未收本金
    //end add dwj 20081029
    
//	 add dwj 20081114
	 private String fsaccountno1 = "";//内部帐户编号(收款款方)
	 private String fsaccountnoname1 = "";//内部帐户名称(收款款方)
//	 end dwj 20081114
	 
	 //add dwj 20081114
	 private long noverdueid = -1;
	 //end add dwj 20081114
    
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}
	public Timestamp getDtExecuteDate() {
		return dtExecuteDate;
	}
	public void setDtExecuteDate(Timestamp dtExecuteDate) {
		this.dtExecuteDate = dtExecuteDate;
	}
	public Timestamp getDtOutdate() {
		return dtOutdate;
	}
	public void setDtOutdate(Timestamp dtOutdate) {
		this.dtOutdate = dtOutdate;
	}
	public Timestamp getDtTodate() {
		return dtTodate;
	}
	public void setDtTodate(Timestamp dtTodate) {
		this.dtTodate = dtTodate;	
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
	public long getIslowlevel() {
		return islowlevel;
	}
	public void setIslowlevel(long islowlevel) {
		this.islowlevel = islowlevel;
	}
	public double getNamount() {
		return namount;
	}
	public void setNamount(double namount) {
		this.namount = namount;
	}
	public double getNcapitalAmount() {
		return ncapitalAmount;
	}
	public void setNcapitalAmount(double ncapitalAmount) {
		this.ncapitalAmount = ncapitalAmount;
	}
	public long getNcontractId() {
		return ncontractId;
	}
	public void setNcontractId(long ncontractId) {
		this.ncontractId = ncontractId;
	}
	public long getNextchecklevel() {
		return nextchecklevel;
	}
	public void setNextchecklevel(long nextchecklevel) {
		this.nextchecklevel = nextchecklevel;
	}
	public long getNextcheckuserid() {
		return nextcheckuserid;
	}
	public void setNextcheckuserid(long nextcheckuserid) {
		this.nextcheckuserid = nextcheckuserid;
	}
	public double getNinterestAmount() {
		return ninterestAmount;
	}
	public void setNinterestAmount(double ninterestAmount) {
		this.ninterestAmount = ninterestAmount;
	}
	public long getNnoteTypeId() {
		return nnoteTypeId;
	}
	public void setNnoteTypeId(long nnoteTypeId) {
		this.nnoteTypeId = nnoteTypeId;
	}
	public long getNoverdueDay() {
		return noverdueDay;
	}
	public void setNoverdueDay(long noverdueDay) {
		this.noverdueDay = noverdueDay;
	}
	public double getNoverDueRate() {
		return noverDueRate;
	}
	public void setNoverDueRate(double noverDueRate) {
		this.noverDueRate = noverDueRate;
	}
	public long getNpayAccountID() {
		return npayAccountID;
	}
	public void setNpayAccountID(long npayAccountID) {
		this.npayAccountID = npayAccountID;
	}
	public long getNrecAccountID() {
		return nrecAccountID;
	}
	public void setNrecAccountID(long nrecAccountID) {
		this.nrecAccountID = nrecAccountID;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSpayBankAccountName() {
		return spayBankAccountName;
	}
	public void setSpayBankAccountName(String spayBankAccountName) {
		this.spayBankAccountName = spayBankAccountName;
	}
	public String getSpayBankAccountNO() {
		return spayBankAccountNO;
	}
	public void setSpayBankAccountNO(String spayBankAccountNO) {
		this.spayBankAccountNO = spayBankAccountNO;
	}
	public String getSpayBankName() {
		return spayBankName;
	}
	public void setSpayBankName(String spayBankName) {
		this.spayBankName = spayBankName;
	}
	public String getSrecBankAccountName() {
		return srecBankAccountName;
	}
	public void setSrecBankAccountName(String srecBankAccountName) {
		this.srecBankAccountName = srecBankAccountName;
	}
	public String getSrecBankAccountNO() {
		return srecBankAccountNO;
	}
	public void setSrecBankAccountNO(String srecBankAccountNO) {
		this.srecBankAccountNO = srecBankAccountNO;
	}
	public String getSrecBankCity() {
		return srecBankCity;
	}
	public void setSrecBankCity(String srecBankCity) {
		this.srecBankCity = srecBankCity;
	}
	public String getSrecBankName() {
		return srecBankName;
	}
	public void setSrecBankName(String srecBankName) {
		this.srecBankName = srecBankName;
	}
	public String getSrecBankProvince() {
		return srecBankProvince;
	}
	public void setSrecBankProvince(String srecBankProvince) {
		this.srecBankProvince = srecBankProvince;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public LoanProtocolInfo getLoanProtocolInfo() {
		return loanProtocolInfo;
	}
	public void setLoanProtocolInfo(LoanProtocolInfo loanProtocolInfo) {
		this.loanProtocolInfo = loanProtocolInfo;
	}
	public double getNpoundage() {
		return npoundage;
	}
	public void setNpoundage(double npoundage) {
		this.npoundage = npoundage;
	}
	public double getPoundage() {
		return poundage;
	}
	public void setPoundage(double poundage) {
		this.poundage = poundage;
	}
	public String getFsaccountno() {
		return fsaccountno;
	}
	public void setFsaccountno(String fsaccountno) {
		this.fsaccountno = fsaccountno;
	}
	public String getFsaccountnoname() {
		return fsaccountnoname;
	}
	public void setFsaccountnoname(String fsaccountnoname) {
		this.fsaccountnoname = fsaccountnoname;
	}
	public String getHeckOpinion() {
		return heckOpinion;
	}
	public void setHeckOpinion(String heckOpinion) {
		this.heckOpinion = heckOpinion;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public double getNinterestamountsum() {
		return ninterestamountsum;
	}
	public void setNinterestamountsum(double ninterestamountsum) {
		this.ninterestamountsum = ninterestamountsum;
	}
	public double getNninterestamountsum() {
		return nninterestamountsum;
	}
	public void setNninterestamountsum(double nninterestamountsum) {
		this.nninterestamountsum = nninterestamountsum;
	}
	public double getNoverdueratesum() {
		return noverdueratesum;
	}
	public void setNoverdueratesum(double noverdueratesum) {
		this.noverdueratesum = noverdueratesum;
	}
	public double getNncapitalamount() {
		return nncapitalamount;
	}
	public void setNncapitalamount(double nncapitalamount) {
		this.nncapitalamount = nncapitalamount;
	}
	public String getFsaccountno1() {
		return fsaccountno1;
	}
	public void setFsaccountno1(String fsaccountno1) {
		this.fsaccountno1 = fsaccountno1;
	}
	public String getFsaccountnoname1() {
		return fsaccountnoname1;
	}
	public void setFsaccountnoname1(String fsaccountnoname1) {
		this.fsaccountnoname1 = fsaccountnoname1;
	}
	public long getNoverdueid() {
		return noverdueid;
	}
	public void setNoverdueid(long noverdueid) {
		this.noverdueid = noverdueid;
	}

}
