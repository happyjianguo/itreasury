package com.iss.itreasury.settlement.craftbrother.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransCraInterestPreDrawInfo extends ITreasuryBaseDataEntity
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6683332035645245487L;
	private long id = -1;
	private long nofficeid  = -1;
	private long ncurrencyid = -1;
	private long ntransactiontypeid = -1;
	private long ncrabusinesstypeid = -1;
	private long nsubtransactiontypeid = -1;
	private long ncounterpartid = -1;
	
	private String stransno = null; 
	
	private long nnoticeid = -1;
	
	private double mpredrawinterest = 0.00;
	private double minterestrate = 0.00;
	private Timestamp dtintereststart = null;
	private Timestamp dtinterestend = null;
	private long ndays = -1;
	private long nstatusid = -1;
	private Timestamp dtmake = null;
	private long nmakeuserid = -1;
	private String sabstract = "";
	//辅助信息
	private long DESC = -1;//排序方式
	private long OrderIndex = -1;//排序索引
	
	private long ncontractid = -1;
	private String scontractcode = null;
	private String snoticecode = null;
	private String scounterpartname = null;
	
	private List draftList = null;
	
	private String queryDate = "";
	
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
		putUsedField("nofficeid",nofficeid );
	}
	public long getNcurrencyid() {
		return ncurrencyid;
	}
	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
		putUsedField("ncurrencyid", ncurrencyid);
	}
	public String getStransno() {
		return stransno;
	}
	public void setStransno(String stransno) {
		this.stransno = stransno;
		putUsedField("stransno", stransno);
	}
	public long getNtransactiontypeid() {
		return ntransactiontypeid;
	}
	public void setNtransactiontypeid(long ntransactiontypeid) {
		this.ntransactiontypeid = ntransactiontypeid;
		putUsedField("ntransactiontypeid", ntransactiontypeid);
	}
	public long getNcrabusinesstypeid() {
		return ncrabusinesstypeid;
	}
	public void setNcrabusinesstypeid(long ncrabusinesstypeid) {
		this.ncrabusinesstypeid = ncrabusinesstypeid;
		putUsedField("ncrabusinesstypeid", ncrabusinesstypeid);
	}
	public long getNsubtransactiontypeid() {
		return nsubtransactiontypeid;
	}
	public void setNsubtransactiontypeid(long nsubtransactiontypeid) {
		this.nsubtransactiontypeid = nsubtransactiontypeid;
		putUsedField("nsubtransactiontypeid", nsubtransactiontypeid);
	}
	
	public long getNcounterpartid() {
		return ncounterpartid;
	}
	public void setNcounterpartid(long ncounterpartid) {
		this.ncounterpartid = ncounterpartid;
		putUsedField("ncounterpartid", ncounterpartid);
	}

	public long getNnoticeid() {
		return nnoticeid;
	}
	public void setNnoticeid(long nnoticeid) {
		this.nnoticeid = nnoticeid;
		putUsedField("nnoticeid", nnoticeid);
	}
	public double getMpredrawinterest() {
		return mpredrawinterest;
	}
	public void setMpredrawinterest(double mpredrawinterest) {
		this.mpredrawinterest = mpredrawinterest;
		putUsedField("mpredrawinterest", mpredrawinterest);
	}
	public double getMinterestrate() {
		return minterestrate;
	}
	public void setMinterestrate(double minterestrate) {
		this.minterestrate = minterestrate;
		putUsedField("minterestrate", minterestrate);
	}
	public Timestamp getDtintereststart() {
		return dtintereststart;
	}
	public void setDtintereststart(Timestamp dtintereststart) {
		this.dtintereststart = dtintereststart;
		putUsedField("dtintereststart", dtintereststart);
	}
	public Timestamp getDtinterestend() {
		return dtinterestend;
	}
	public void setDtinterestend(Timestamp dtinterestend) {
		this.dtinterestend = dtinterestend;
		putUsedField("dtinterestend", dtinterestend);
	}
	public long getNdays() {
		return ndays;
	}
	public void setNdays(long ndays) {
		this.ndays = ndays;
		putUsedField("ndays", ndays);
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
		putUsedField("nstatusid", nstatusid);
	}
	public Timestamp getDtmake() {
		return dtmake;
	}
	public void setDtmake(Timestamp dtmake) {
		this.dtmake = dtmake;
		putUsedField("dtmake", dtmake);
	}
	public long getNmakeuserid() {
		return nmakeuserid;
	}
	public void setNmakeuserid(long nmakeuserid) {
		this.nmakeuserid = nmakeuserid;
		putUsedField("nmakeuserid", nmakeuserid);
	}
	public String getSabstract() {
		return sabstract;
	}
	public void setSabstract(String sabstract) {
		this.sabstract = sabstract;
		putUsedField("sabstract", sabstract);
	}
	public long getDESC() {
		return DESC;
	}
	public void setDESC(long dESC) {
		DESC = dESC;
	}
	public long getOrderIndex() {
		return OrderIndex;
	}
	public void setOrderIndex(long orderIndex) {
		OrderIndex = orderIndex;
	}
	public long getNcontractid() {
		return ncontractid;
	}
	public void setNcontractid(long ncontractid) {
		this.ncontractid = ncontractid;
	}
	public String getScontractcode() {
		return scontractcode;
	}
	public void setScontractcode(String scontractcode) {
		this.scontractcode = scontractcode;
	}
	public String getSnoticecode() {
		return snoticecode;
	}
	public void setSnoticecode(String snoticecode) {
		this.snoticecode = snoticecode;
	}
	public String getScounterpartname() {
		return scounterpartname;
	}
	public void setScounterpartname(String scounterpartname) {
		this.scounterpartname = scounterpartname;
	}
	public List getDraftList() {
		return draftList;
	}
	public void setDraftList(List draftList) {
		this.draftList = draftList;
	}
}
