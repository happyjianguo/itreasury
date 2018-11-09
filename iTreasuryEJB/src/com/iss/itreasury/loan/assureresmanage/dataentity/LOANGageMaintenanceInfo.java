package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOANGageMaintenanceInfo implements Serializable {

    private     long        id         = -1; 
    
    private     long        gageid     = -1;    //担保品信息ID
    
    private     long        muserid    = -1;    //维护人
    
    private     long        maction    = -1;    //维护动作
    
    private     Timestamp   mdate      = null;  //维护日期
    
    private     long        mstatus    = -1;    //状态
    
    private     long        officeid   = -1;    //办事处
    
    private     long        currencyid = -1;    //币种
    
    private     String      musername = "";     //维护人名称

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}

	public long getGageid() {
		return gageid;
	}

	public void setGageid(long gageid) {
		this.gageid = gageid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMaction() {
		return maction;
	}

	public void setMaction(long maction) {
		this.maction = maction;
	}

	public Timestamp getMdate() {
		return mdate;
	}

	public void setMdate(Timestamp mdate) {
		this.mdate = mdate;
	}

	public long getMstatus() {
		return mstatus;
	}

	public void setMstatus(long mstatus) {
		this.mstatus = mstatus;
	}

	public long getMuserid() {
		return muserid;
	}

	public void setMuserid(long muserid) {
		this.muserid = muserid;
	}

	public String getMusername() {
		return musername;
	}

	public void setMusername(String musername) {
		this.musername = musername;
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
}
