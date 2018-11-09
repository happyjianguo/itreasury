/*
 * Created on 2005-8-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.sql.Timestamp;

/**
 * @author hkzhou TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountDailyTransGatherConditionInfo
{
    private String    startAccountNo = "";

    private String    endAccountNo   = "";

    private String    AccountNo      = "";

    private Timestamp startDate      = null;

    private Timestamp endDate        = null;
        
    //add by xwhe 2008-04-01
    private long   officeId          = -1; //办事处ID
    
    private long   currencyId        = -1; //币种ID
    /**
     * @return Returns the accountNo.
     */
    public String getAccountNo()
    {
        return AccountNo;
    }

    /**
     * @param accountNo The accountNo to set.
     */
    public void setAccountNo(String accountNo)
    {
        AccountNo = accountNo;
    }

    /**
     * @return Returns the endAccountNo.
     */
    public String getEndAccountNo()
    {
        return endAccountNo;
    }

    /**
     * @param endAccountNo The endAccountNo to set.
     */
    public void setEndAccountNo(String endAccountNo)
    {
        this.endAccountNo = endAccountNo;
    }

    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return Returns the startAccountNo.
     */
    public String getStartAccountNo()
    {
        return startAccountNo;
    }

    /**
     * @param startAccountNo The startAccountNo to set.
     */
    public void setStartAccountNo(String startAccountNo)
    {
        this.startAccountNo = startAccountNo;
    }

    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }

    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
    }

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

}
