package com.iss.itreasury.integratedCredit.dataentity;

// default package



/**
 * wxsu  被担保/承兑企业简况属性清单
 * LoanAssureoracceptsynopsis generated by MyEclipse - Hibernate Tools
 */

public class LoanAssureoracceptsynopsis  implements java.io.Serializable {


    // Fields    

     private long id;
     private String synopsis;
     private long status;
     private long creditgradeid;


    // Constructors

    /** default constructor */
    public LoanAssureoracceptsynopsis() {
    }

	/** minimal constructor */
    public LoanAssureoracceptsynopsis(long id) {
        this.id = id;
    }
    
    /** full constructor */
    public LoanAssureoracceptsynopsis(long id, String synopsis, long status, long creditgradeid) {
        this.id = id;
        this.synopsis = synopsis;
        this.status = status;
        this.creditgradeid = creditgradeid;
    }

   
    // Property accessors

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getSynopsis() {
        return this.synopsis;
    }
    
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public long getStatus() {
        return this.status;
    }
    
    public void setStatus(long status) {
        this.status = status;
    }

    public long getCreditgradeid() {
        return this.creditgradeid;
    }
    
    public void setCreditgradeid(long creditgradeid) {
        this.creditgradeid = creditgradeid;
    }
   








}