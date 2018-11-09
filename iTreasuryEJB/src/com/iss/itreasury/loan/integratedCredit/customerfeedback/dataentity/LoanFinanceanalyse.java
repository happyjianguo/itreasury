package com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity;

// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * LoanFinanceanalyse generated by MyEclipse - Hibernate Tools
 */

public class LoanFinanceanalyse  implements java.io.Serializable {


    // Fields    

     private long id;//ID
     private long creditid;//授信评分表ID
     private long clientid;//客户ID
     private String cycleyear;//财务周期年
     private String cyclemonth;//财务周期月
     private long isaudit;//是否审计
     private long repoetnameid;//报表名称ID
     private String repoetname;//报表名称
     private long reporttype;//报表类型
     private long status;//状态
     private long inputuserid;//录入人
     private Timestamp inputdate;//录入时间
     private String explain;//说明
     private long officeid;//办事处
     private long currencyid;//币种
     
     // add by lcliu @ 08-11-18
     private String clientcode;//客户编号
     private String clientname;//客户名
     private String inputusername;//录入人姓名

    // Constructors

    /** default constructor */
    public LoanFinanceanalyse() {
    }

	/** minimal constructor */
    public LoanFinanceanalyse(long id) {
        this.id = id;
    }
    
    /** full constructor */
    public LoanFinanceanalyse(long id, long creditid, long clientid, String cycleyear, String cyclemonth, long isaudit, long repoetnameid, String repoetname, long reporttype, long status, long inputuserid, Timestamp inputdate, String explain, long officeid, long currencyid) {
        this.id = id;
        this.creditid = creditid;
        this.clientid = clientid;
        this.cycleyear = cycleyear;
        this.cyclemonth = cyclemonth;
        this.isaudit = isaudit;
        this.repoetnameid = repoetnameid;
        this.repoetname = repoetname;
        this.reporttype = reporttype;
        this.status = status;
        this.inputuserid = inputuserid;
        this.inputdate = inputdate;
        this.explain = explain;
        this.officeid = officeid;
        this.currencyid = currencyid;
    }

   
    // Property accessors

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getCreditid() {
        return this.creditid;
    }
    
    public void setCreditid(long creditid) {
        this.creditid = creditid;
    }

    public long getClientid() {
        return this.clientid;
    }
    
    public void setClientid(long clientid) {
        this.clientid = clientid;
    }

    public String getCycleyear() {
        return this.cycleyear;
    }
    
    public void setCycleyear(String cycleyear) {
        this.cycleyear = cycleyear;
    }

    public String getCyclemonth() {
        return this.cyclemonth;
    }
    
    public void setCyclemonth(String cyclemonth) {
        this.cyclemonth = cyclemonth;
    }

    public long getIsaudit() {
        return this.isaudit;
    }
    
    public void setIsaudit(long isaudit) {
        this.isaudit = isaudit;
    }

    public long getRepoetnameid() {
        return this.repoetnameid;
    }
    
    public void setRepoetnameid(long repoetnameid) {
        this.repoetnameid = repoetnameid;
    }

    public String getRepoetname() {
        return this.repoetname;
    }
    
    public void setRepoetname(String repoetname) {
        this.repoetname = repoetname;
    }

    public long getReporttype() {
        return this.reporttype;
    }
    
    public void setReporttype(long reporttype) {
        this.reporttype = reporttype;
    }

    public long getStatus() {
        return this.status;
    }
    
    public void setStatus(long status) {
        this.status = status;
    }

    public long getInputuserid() {
        return this.inputuserid;
    }
    
    public void setInputuserid(long inputuserid) {
        this.inputuserid = inputuserid;
    }

    public Timestamp getInputdate() {
        return this.inputdate;
    }
    
    public void setInputdate(Timestamp inputdate) {
        this.inputdate = inputdate;
    }

    public String getExplain() {
        return this.explain;
    }
    
    public void setExplain(String explain) {
        this.explain = explain;
    }

    public long getOfficeid() {
        return this.officeid;
    }
    
    public void setOfficeid(long officeid) {
        this.officeid = officeid;
    }

    public long getCurrencyid() {
        return this.currencyid;
    }
    
    public void setCurrencyid(long currencyid) {
        this.currencyid = currencyid;
    }

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getInputusername() {
		return inputusername;
	}

	public void setInputusername(String inputusername) {
		this.inputusername = inputusername;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
}