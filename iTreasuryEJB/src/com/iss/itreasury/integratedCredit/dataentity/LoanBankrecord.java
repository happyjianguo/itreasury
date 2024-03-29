package com.iss.itreasury.integratedCredit.dataentity;

// default package



/**
 * wxsu 银行信贷登记系统记录表属性清单
 * LoanBankrecord generated by MyEclipse - Hibernate Tools
 */

public class LoanBankrecord  implements java.io.Serializable {


    // Fields    

     private long id;
     private long creditgradeid;
     private String record;


    // Constructors

    /** default constructor */
    public LoanBankrecord() {
    }

	/** minimal constructor */
    public LoanBankrecord(long id) {
        this.id = id;
    }
    
    /** full constructor */
    public LoanBankrecord(long id, long creditgradeid, String record) {
        this.id = id;
        this.creditgradeid = creditgradeid;
        this.record = record;
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

    public String getRecord() {
        return this.record;
    }
    
    public void setRecord(String record) {
        this.record = record;
    }
   








}