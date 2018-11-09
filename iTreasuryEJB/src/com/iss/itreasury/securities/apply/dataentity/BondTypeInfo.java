package com.iss.itreasury.securities.apply.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BondTypeInfo extends SECBaseDataEntity {

	private long id;
	private long applyFormId;
	private String name;
	private long term;
	private double amount;
	private String rate;
	private long statusId;

    /**
     * @return Returns the amount.
     */
    public double getAmount()
    {
        return amount;
    }
    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
        putUsedField("amount", amount);
    }
    /**
     * @return Returns the applyFormId.
     */
    public long getApplyFormId()
    {
        return applyFormId;
    }
    /**
     * @param applyFormId The applyFormId to set.
     */
    public void setApplyFormId(long applyFormId)
    {
        this.applyFormId = applyFormId;
        putUsedField("applyFormId", applyFormId);
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
        putUsedField("id", id);
    }
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
        putUsedField("name", name);
    }
    /**
     * @return Returns the rate.
     */
    public String getRate()
    {
        return rate;
    }
    /**
     * @param rate The rate to set.
     */
    public void setRate(String rate)
    {
        this.rate = rate;
        putUsedField("rate", rate);
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId()
    {
        return statusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId)
    {
        this.statusId = statusId;
        putUsedField("statusId", statusId);
    }
    /**
     * @return Returns the term.
     */
    public long getTerm()
    {
        return term;
    }
    /**
     * @param term The term to set.
     */
    public void setTerm(long term)
    {
        this.term = term;
        putUsedField("term", term);
    }
}
