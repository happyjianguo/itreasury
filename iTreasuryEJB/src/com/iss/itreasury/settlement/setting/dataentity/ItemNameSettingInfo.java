/*
 * Created on 2005-1-10
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author ygzhao
 *  
 */
public class ItemNameSettingInfo extends SettlementBaseDataEntity
{
    private long ID	= -1;//Number	ID	主健
    private String itemName = "";//VARCHAR2 (100)	项目名称	
    private long officeID = -1;//Number	办事处	
    private long currencyID	= -1;//Number	币种	
    private long itemType = -1;//Number	项目类型	1、	资产负债及表外项目设置2、	损益表项目设置
    private Timestamp inputDate = null;//DATE	录入日期	
    private long inputUserID = -1;//Number	录入人	
    private Timestamp modifyDate = null;//DATE	修改日期	
    private long modifyUserID = -1;//Number	修改人	
    private long statusid = -1;//Number	状态	0—已删除1—正常
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID = currencyID;
    }
    /**
     * @return Returns the iD.
     */
    public long getID()
    {
        return ID;
    }
    /**
     * @param id The iD to set.
     */
    public void setID(long id)
    {
        ID = id;
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return inputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
    }
    /**
     * @return Returns the itemName.
     */
    public String getItemName()
    {
        return itemName;
    }
    /**
     * @param itemName The itemName to set.
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    /**
     * @return Returns the itemType.
     */
    public long getItemType()
    {
        return itemType;
    }
    /**
     * @param itemType The itemType to set.
     */
    public void setItemType(long itemType)
    {
        this.itemType = itemType;
    }
    /**
     * @return Returns the modifyDate.
     */
    public Timestamp getModifyDate()
    {
        return modifyDate;
    }
    /**
     * @param modifyDate The modifyDate to set.
     */
    public void setModifyDate(Timestamp modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    /**
     * @return Returns the modifyUserID.
     */
    public long getModifyUserID()
    {
        return modifyUserID;
    }
    /**
     * @param modifyUserID The modifyUserID to set.
     */
    public void setModifyUserID(long modifyUserID)
    {
        this.modifyUserID = modifyUserID;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return officeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
    }
    /**
     * @return Returns the statusid.
     */
    public long getStatusid()
    {
        return statusid;
    }
    /**
     * @param statusid The statusid to set.
     */
    public void setStatusid(long statusid)
    {
        this.statusid = statusid;
    }
}
