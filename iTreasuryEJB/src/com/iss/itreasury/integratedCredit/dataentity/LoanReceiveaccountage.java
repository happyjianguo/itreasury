package com.iss.itreasury.integratedCredit.dataentity;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * @author wxsu 2008-06-17
 * LoanReceiveaccountage generated by MyEclipse - Hibernate Tools
 */

public class LoanReceiveaccountage   extends ITreasuryBaseDataEntity implements java.io.Serializable {

	

    // Fields    

     private long id;
     private long creditgradeid;
     private long accountage;
     private long accountcount;
     private double amount;
     private double scale;
     private String explain;
     private Timestamp  inputdate;
     private long inputuserid;
     private long status;
     private long officeid;
     private long currencyid;
     private Collection OthersStockHolder = null;//Ӧ���ʿ����伯��

    // Constructors

    /** default constructor */
    public LoanReceiveaccountage() {
    }

	/** minimal constructor */
    public LoanReceiveaccountage(long id) {
        this.id = id;
    }
    
    /** full constructor */
    public LoanReceiveaccountage(long id, long creditgradeid, long accountage, long accountcount, double amount, double scale, String explain, Timestamp inputdate, long inputuserid, long status, long officeid, long currencyid) {
        this.id = id;
        this.creditgradeid = creditgradeid;
        this.accountage = accountage;
        this.accountcount = accountcount;
        this.amount = amount;
        this.scale = scale;
        this.explain = explain;
        this.inputdate = inputdate;
        this.inputuserid = inputuserid;
        this.status = status;
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

    public long getCreditgradeid() {
        return this.creditgradeid;
    }
    
    public void setCreditgradeid(long creditgradeid) {
        this.creditgradeid = creditgradeid;
    }

    public long getAccountage() {
        return this.accountage;
    }
    
    public void setAccountage(long accountage) {
        this.accountage = accountage;
    }

    public long getAccountcount() {
        return this.accountcount;
    }
    
    public void setAccountcount(long accountcount) {
        this.accountcount = accountcount;
    }

    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getScale() {
        return this.scale;
    }
    
    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getExplain() {
        return this.explain;
    }
    
    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Timestamp getInputdate() {
        return this.inputdate;
    }
    
    public void setInputdate(Timestamp inputdate) {
        this.inputdate = inputdate;
    }

    public long getInputuserid() {
        return this.inputuserid;
    }
    
    public void setInputuserid(long inputuserid) {
        this.inputuserid = inputuserid;
    }

    public long getStatus() {
        return this.status;
    }
    
    public void setStatus(long status) {
        this.status = status;
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

	public Collection getOthersStockHolder() {
		return OthersStockHolder;
	}

	public void setOthersStockHolder(Collection othersStockHolder) {
		OthersStockHolder = othersStockHolder;
	}
   








}