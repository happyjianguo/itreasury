/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.loanapply.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;
import java.sql.Timestamp;
public class LoanPlanDetailInfo implements Serializable
{
    /*
    1   ID  NUMBER  Y    
    2   "nPlanID"   NUMBER  Y   贷款计划版本ID(FK_reference_loan_LoanFormPlan_ID)
    3   "dtPlanDate"    DATE        原始计划日期
    4   "nPayTypeID"    NUMBER  Y   放/还款
    5   "mAmount"   NUMBER(21,6)        金额
    6   "sType" VARCHAR2(100)       类型
    7   "dtModifyDate"  DATE        更改日期
    8   "nLastExtendID" NUMBER      对应的展期ID
    9   "nLastOverdueID"    NUMBER      对应逾期ID
    10  "nlastVersionPlanID"    NUMBER      对应的上一版本的计划明细ID
    */
    private long        ID=-1;
    private long        planID=-1;
    private Timestamp   planDate=null;
    private long        payTypeID=-1;
    private double      amount=0;
    private String      type="";
    private Timestamp   modifyDate=null;
    private long        lastExtendID=-1;
    private long        lastOverdueID=-1;
    private long        lastVersionPlanID=-1;
    private double 		interestAmount = 0.0;
    private double 		recognizanceAmount = 0.0;
    
    /**
     * @return
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public long getLastExtendID()
    {
        return lastExtendID;
    }

    /**
     * @return
     */
    public long getLastOverdueID()
    {
        return lastOverdueID;
    }

    /**
     * @return
     */
    public long getLastVersionPlanID()
    {
        return lastVersionPlanID;
    }

    /**
     * @return
     */
    public Timestamp getModifyDate()
    {
        return modifyDate;
    }

    /**
     * @return
     */
    public long getPayTypeID()
    {
        return payTypeID;
    }

    /**
     * @return
     */
    public Timestamp getPlanDate()
    {
        return planDate;
    }

    /**
     * @return
     */
    public long getPlanID()
    {
        return planID;
    }

    /**
     * @return
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        amount = d;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setLastExtendID(long l)
    {
        lastExtendID = l;
    }

    /**
     * @param l
     */
    public void setLastOverdueID(long l)
    {
        lastOverdueID = l;
    }

    /**
     * @param l
     */
    public void setLastVersionPlanID(long l)
    {
        lastVersionPlanID = l;
    }

    /**
     * @param timestamp
     */
    public void setModifyDate(Timestamp timestamp)
    {
        modifyDate = timestamp;
    }

    /**
     * @param l
     */
    public void setPayTypeID(long l)
    {
        payTypeID = l;
    }

    /**
     * @param timestamp
     */
    public void setPlanDate(Timestamp timestamp)
    {
        planDate = timestamp;
    }

    /**
     * @param l
     */
    public void setPlanID(long l)
    {
        planID = l;
    }

    /**
     * @param string
     */
    public void setType(String string)
    {
        type = string;
    }

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}

	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}
    

}