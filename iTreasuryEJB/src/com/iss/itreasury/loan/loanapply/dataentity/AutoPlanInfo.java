/*
 * Created on 2003-10-14
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
import java.sql.Timestamp;
import java.io.Serializable;

public class AutoPlanInfo implements Serializable
{
    private long lLoanID=-1;
    private long nPayType=-1;
    private Timestamp tsPayStart=null;
    private Timestamp tsPayEnd=null;
    private long nRepayType=-1;
    private Timestamp tsRepayStart=null;
    private Timestamp tsRepayEnd=null;
    private long nSourceType;
    
    
    /**
     * @return
     */
    public long getLLoanID()
    {
        return lLoanID;
    }

    /**
     * @return
     */
    public long getNPayType()
    {
        return nPayType;
    }

    /**
     * @return
     */
    public long getNRepayType()
    {
        return nRepayType;
    }

    /**
     * @return
     */
    public long getNSourceType()
    {
        return nSourceType;
    }

    /**
     * @return
     */
    public Timestamp getTsPayEnd()
    {
        return tsPayEnd;
    }

    /**
     * @return
     */
    public Timestamp getTsPayStart()
    {
        return tsPayStart;
    }

    /**
     * @return
     */
    public Timestamp getTsRepayEnd()
    {
        return tsRepayEnd;
    }

    /**
     * @return
     */
    public Timestamp getTsRepayStart()
    {
        return tsRepayStart;
    }

    /**
     * @param l
     */
    public void setLLoanID(long l)
    {
        lLoanID = l;
    }

    /**
     * @param l
     */
    public void setNPayType(long l)
    {
        nPayType = l;
    }

    /**
     * @param l
     */
    public void setNRepayType(long l)
    {
        nRepayType = l;
    }

    /**
     * @param l
     */
    public void setNSourceType(long l)
    {
        nSourceType = l;
    }

    /**
     * @param timestamp
     */
    public void setTsPayEnd(Timestamp timestamp)
    {
        tsPayEnd = timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTsPayStart(Timestamp timestamp)
    {
        tsPayStart = timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTsRepayEnd(Timestamp timestamp)
    {
        tsRepayEnd = timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTsRepayStart(Timestamp timestamp)
    {
        tsRepayStart = timestamp;
    }

}
