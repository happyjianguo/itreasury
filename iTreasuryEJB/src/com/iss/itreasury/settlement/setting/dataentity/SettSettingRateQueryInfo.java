/*
 * Created on 2004-9-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.setting.dataentity;


import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author jinchen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettSettingRateQueryInfo extends SettlementBaseDataEntity
{
    
	private long lTypeID;
	private long lCurrencyID;
	private long lPageLineCount;
	private long lPageNo;
	private String sOrderParam = "";
	private long lDesc;
    /**
     * @return Returns the lCurrencyID.
     */
    public long getLCurrencyID()
    {
        return lCurrencyID;
    }
    /**
     * @param currencyID The lCurrencyID to set.
     */
    public void setLCurrencyID(long currencyID)
    {
        lCurrencyID = currencyID;
    }
    /**
     * @return Returns the lDesc.
     */
    public long getLDesc()
    {
        return lDesc;
    }
    /**
     * @param desc The lDesc to set.
     */
    public void setLDesc(long desc)
    {
        lDesc = desc;
    }
   
    /**
     * @return Returns the sOrderParam.
     */
    public String getSOrderParam()
    {
        return sOrderParam;
    }
    /**
     * @param orderParam The sOrderParam to set.
     */
    public void setSOrderParam(String orderParam)
    {
        sOrderParam = orderParam;
    }
    /**
     * @return Returns the lPageLineCount.
     */
    public long getLPageLineCount()
    {
        return lPageLineCount;
    }
    /**
     * @param pageLineCount The lPageLineCount to set.
     */
    public void setLPageLineCount(long pageLineCount)
    {
        lPageLineCount = pageLineCount;
    }
    /**
     * @return Returns the lPageNo.
     */
    public long getLPageNo()
    {
        return lPageNo;
    }
    /**
     * @param pageNo The lPageNo to set.
     */
    public void setLPageNo(long pageNo)
    {
        lPageNo = pageNo;
    }
    /**
     * @return Returns the lTypeID.
     */
    public long getLTypeID()
    {
        return lTypeID;
    }
    /**
     * @param typeID The lTypeID to set.
     */
    public void setLTypeID(long typeID)
    {
        lTypeID = typeID;
    }
    public static void main(String[] args)
    {
    }
}
