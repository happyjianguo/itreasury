package com.iss.itreasury.ebank.obsystem.dataentity;

import java.io.Serializable;
import java.sql.*;

public class OBAbstractSettingInfo implements Serializable{
	
	private long  id = -1;      
	private long  nofficeid = -1;      
	private String  scode = null;       
	private String  sdesc = null;      
	private long  nstatusid = -1;
	private long  nclientid = -1;
	private Timestamp inputtime = null;
	private Timestamp modifytime = null;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSdesc() {
		return sdesc;
	}
	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNclientid() {
		return nclientid;
	}
	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}
	public Timestamp getInputtime() {
		return inputtime;
	}
	public void setInputtime(Timestamp inputtime) {
		this.inputtime = inputtime;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	
	  
}
