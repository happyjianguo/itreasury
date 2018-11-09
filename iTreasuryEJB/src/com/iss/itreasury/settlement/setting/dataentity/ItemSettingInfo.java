/*
 * Created on 2005-1-7
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author ygzhao
 *  
 */
public class ItemSettingInfo extends SettlementBaseDataEntity
{
    private long ID	= -1; //主健
    private long itemID = -1;//项目ID
    private long officeID = -1; //办事处	
    private long currencyID = -1; //币种	    
    private long itemType = -1; //项目类型	1、	资产负债及表外项目设置2、	损益表项目设置
    private long accountType = -1; //账户类型	列出常量的所有账户类型
    private long contractType = -1; //合同类型	当账户类型为8、自营贷款9、委托贷款10、贴现合同类型有效。
    private long interestFeeType = -1; //损益类型	1、	利息2、	手续费3、	担保费4、	复利5、	罚息6、	计提利息7、	利息税费
    private long relateClient = -1; //有关客户	1、	申请人2、	委托人3、	卖方4、	出票人
    private Timestamp inputDate	= null; //录入日期	
    private long inputUserID = -1; //录入人	
    private Timestamp modifyDate = null;//修改日期	
    private long modifyUserID = -1; //修改人	
    private long statusID = -1; //状态	0—已删除1—正常   
    /**
     * @return Returns the accountType.
     */
    public long getAccountType()
    {
        return accountType;
    }
    /**
     * @param accountType The accountType to set.
     */
    public void setAccountType(long accountType)
    {
        this.accountType = accountType;
    }
    /**
     * @return Returns the contractType.
     */
    public long getContractType()
    {
        return contractType;
    }
    /**
     * @param contractType The contractType to set.
     */
    public void setContractType(long contractType)
    {
        this.contractType = contractType;
    }
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
     * @return Returns the interestFeeType.
     */
    public long getInterestFeeType()
    {
        return interestFeeType;
    }
    /**
     * @param interestFeeType The interestFeeType to set.
     */
    public void setInterestFeeType(long interestFeeType)
    {
        this.interestFeeType = interestFeeType;
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
     * @return Returns the relateClient.
     */
    public long getRelateClient()
    {
        return relateClient;
    }
    /**
     * @param relateClient The relateClient to set.
     */
    public void setRelateClient(long relateClient)
    {
        this.relateClient = relateClient;
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return statusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        this.statusID = statusID;
    }
}
