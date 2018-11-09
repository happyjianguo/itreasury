package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class QueryPayformOverdue extends ITreasuryBaseDataEntity implements Serializable {
	
	private long id  = -1;//id
	private long ncontractid =-1;//合同ID
	private String scode=null;//放款通知单编号
	private long ndrawnoticeid=-1;//放款通知单ID
	private String  contractCode="";	//合同编号
	private long ntypeid=-1;//贷款类型
	private String ntypename="";//贷款类型名称
	private long nborrowclientid=-1; //借款单位ID
	private String nborrowclientname="";//借款单位名称
	private double mamount  =  0.00;//放款单金额
	private double mcompoundinterest=0.00;//复利
	private Timestamp dtoutdate=null;//放贷日期
	private Timestamp dtend=null;//还款日期
	private double minterestrate=0.0;//放款利率
	private double mbalance=0.00;//放款单余额
	private double MOVERDUEINTEREST=0.00;//逾期金额
	private long nstatusid=-1;//是否转表外
	private long nofficeid =-1;
	private long ncurrencyid=-1; 
	private long  ninputuserid =-1;//录入人ID
	private Timestamp dtinputdate =null;//录入时间
	private long statusid=-1;//是否已结清
	public long getId() {
		return id;
	}
	public void setId(long id) {
		putUsedField("id",id);
		this.id = id;
	}
	public Timestamp getDtoutdate() {
		return dtoutdate;
	}
	public String getFormatDtoutdate()
	{
		return DataFormat.getDateString(dtoutdate);
	}
	public void setDtoutdate(Timestamp dtoutdate) {
		putUsedField("dtoutdate",dtoutdate);
		this.dtoutdate = dtoutdate;
	}
	public Timestamp getDtend() {
		return dtend;
	}
	public String getFormatDtend() {
		return DataFormat.getDateString(dtend);
	}
	public void setDtend(Timestamp dtend) {
		putUsedField("dtend",dtend);
		this.dtend = dtend;
	}


	public double getMamount() {
		return mamount;
	}
	public void setMamount(double mamount) {
		putUsedField("mamount",mamount);
		this.mamount = mamount;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		putUsedField("contractCode",contractCode);
		this.contractCode = contractCode;
	}
	public long getNcontractid() {
		return ncontractid;
	}
	public void setNcontractid(long ncontractid) {
		putUsedField("ncontractid",ncontractid);
		this.ncontractid = ncontractid;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		putUsedField("nstatusid",nstatusid);
		this.nstatusid = nstatusid;
	}

	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		putUsedField("scode",scode);
		this.scode = scode;
	}
	public double getMinterestrate() {
		return minterestrate;
	}
	public void setMinterestrate(double minterestrate) {
		putUsedField("minterestrate",minterestrate);
		this.minterestrate = minterestrate;
	}
	public double getMcompoundinterest() {
		return mcompoundinterest;
	}
	public void setMcompoundinterest(double mcompoundinterest) {
		putUsedField("mcompoundinterest",mcompoundinterest);
		this.mcompoundinterest = mcompoundinterest;
	}
	public long getNborrowclientid() {
		return nborrowclientid;
	}
	public void setNborrowclientid(long nborrowclientid) {
		putUsedField("nborrowclientid",nborrowclientid);
		this.nborrowclientid = nborrowclientid;
	}
	public String getNborrowclientname() {
		return nborrowclientname;
	}
	public void setNborrowclientname(String nborrowclientname) {
		putUsedField("nborrowclientname",nborrowclientname);
		this.nborrowclientname = nborrowclientname;
	}
	public long getNtypeid() {
		return ntypeid;
	}
	public void setNtypeid(long ntypeid) {
		putUsedField("ntypeid",ntypeid);
		this.ntypeid = ntypeid;
	}
	public long getNdrawnoticeid() {
		return ndrawnoticeid;
	}
	public void setNdrawnoticeid(long ndrawnoticeid) {
		putUsedField("ndrawnoticeid",ndrawnoticeid);
		this.ndrawnoticeid = ndrawnoticeid;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		putUsedField("nofficeid",nofficeid);
		this.nofficeid = nofficeid;
	}
	public long getNcurrencyid() {
		return ncurrencyid;
	}
	public void setNcurrencyid(long ncurrencyid) {
		putUsedField("ncurrencyid",ncurrencyid);
		this.ncurrencyid = ncurrencyid;
	}
	public long getNinputuserid() {
		return ninputuserid;
	}
	public void setNinputuserid(long ninputuserid) {
		putUsedField("ninputuserid",ninputuserid);
		this.ninputuserid = ninputuserid;
	}
	public Timestamp getDtinputdate() {
		return dtinputdate;
	}
	public void setDtinputdate(Timestamp dtinputdate) {
		putUsedField("dtinputdate",dtinputdate);
		this.dtinputdate = dtinputdate;
	}
	public String getNtypename() {
		return ntypename;
	}
	public void setNtypename(String ntypename) {
		putUsedField("ntypename",ntypename);
		this.ntypename = ntypename;
	}
	public double getMbalance() {
		return mbalance;
	}
	public void setMbalance(double mbalance) {
		putUsedField("mbalance",mbalance);
		this.mbalance = mbalance;
	}
	public double getMOVERDUEINTEREST() {
		return MOVERDUEINTEREST;
	}
	public void setMOVERDUEINTEREST(double moverdueinterest) {
		putUsedField("moverdueinterest",moverdueinterest);
		MOVERDUEINTEREST = moverdueinterest;
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		putUsedField("statusid",statusid);
		this.statusid = statusid;
	}

}
