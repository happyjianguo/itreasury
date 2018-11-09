/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.ebankPrint.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.evoucher.setting.dataentity.PRINTBaseDataEntity;

/**
 * @author liangwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintEbankApply extends PRINTBaseDataEntity
{
    /**
	 * 
	 */

	
	private static final long serialVersionUID = 3372554852609559470L;
	private long id = -1;                  
    private long nofficeid = -1;           
    private long ncurrency = -1;           
    private long nprintcontentid = -1;    
    private String nprintcontentno = "";      
    private long ndeptid = -1;             
    private long nbillid = -1;              
    private long ntempid = -1;              
    private long nstatusid = -1;            
    private long ischapter = -1;           
    private long nclientid = -1;           
    private long ninputuserid = -1;        
    private Timestamp ninputdate;           
    private long ntypeid = -1;             
    private long isebank = -1;              
    private long nprintid = -1;
    private String stempname = "";              
    private long nmoduleid = -1;
    //add by dwj 20110929 接收人(网银提交的补打审批由谁接收的)
    private long nreceiveuserid = -1;
    //end add by dwj 20110929
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIschapter() {
		return ischapter;
	}
	public void setIschapter(long ischapter) {
		this.ischapter = ischapter;
	}
	public long getIsebank() {
		return isebank;
	}
	public void setIsebank(long isebank) {
		this.isebank = isebank;
	}
	public long getNbillid() {
		return nbillid;
	}
	public void setNbillid(long nbillid) {
		this.nbillid = nbillid;
	}
	public long getNclientid() {
		return nclientid;
	}
	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}
	public long getNcurrency() {
		return ncurrency;
	}
	public void setNcurrency(long ncurrency) {
		this.ncurrency = ncurrency;
	}
	public long getNdeptid() {
		return ndeptid;
	}
	public void setNdeptid(long ndeptid) {
		this.ndeptid = ndeptid;
	}
	public Timestamp getNinputdate() {
		return ninputdate;
	}
	public void setNinputdate(Timestamp ninputdate) {
		this.ninputdate = ninputdate;
	}
	public long getNinputuserid() {
		return ninputuserid;
	}
	public void setNinputuserid(long ninputuserid) {
		this.ninputuserid = ninputuserid;
	}
	public long getNofficeid() {
		return nofficeid;
	}
	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
	}
	public long getNprintcontentid() {
		return nprintcontentid;
	}
	public void setNprintcontentid(long nprintcontentid) {
		this.nprintcontentid = nprintcontentid;
	}
	public String getNprintcontentno() {
		return nprintcontentno;
	}
	public void setNprintcontentno(String nprintcontentno) {
		this.nprintcontentno = nprintcontentno;
	}
	public long getNprintid() {
		return nprintid;
	}
	public void setNprintid(long nprintid) {
		this.nprintid = nprintid;
	}
	public long getNstatusid() {
		return nstatusid;
	}
	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	public long getNtempid() {
		return ntempid;
	}
	public void setNtempid(long ntempid) {
		this.ntempid = ntempid;
	}
	public long getNtypeid() {
		return ntypeid;
	}
	public void setNtypeid(long ntypeid) {
		this.ntypeid = ntypeid;
	}
	public long getNmoduleid() {
		return nmoduleid;
	}
	public void setNmoduleid(long nmoduleid) {
		this.nmoduleid = nmoduleid;
	}
	public String getStempname() {
		return stempname;
	}
	public void setStempname(String stempname) {
		this.stempname = stempname;
	}
	public long getNreceiveuserid() {
		return nreceiveuserid;
	}
	public void setNreceiveuserid(long nreceiveuserid) {
		this.nreceiveuserid = nreceiveuserid;
	}
	
	


}
