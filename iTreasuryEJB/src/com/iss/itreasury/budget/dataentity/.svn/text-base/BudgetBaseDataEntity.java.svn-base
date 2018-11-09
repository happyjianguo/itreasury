package com.iss.itreasury.budget.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * Title:        	iTreasury
 * Description:       所有预算模块实体类的父类
 *  Copyright         (c) 2003 Company: iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-29 
 */



public abstract class BudgetBaseDataEntity extends ITreasuryBaseDataEntity {
    
    private long id = -1;
    private Timestamp modifyTime = null;       //时间戳
    
    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
     */
    public long getId() {
        // TODO Auto-generated method stub
        return id;
    }
    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long id) {
        // TODO Auto-generated method stub
    	this.id = id;
    	putUsedField("ID", id);
    	
    }
    /**
     * @return Returns the modifyTime.
     */
    public Timestamp getModifyTime() {
        return modifyTime;
    }
    /**
     * @param modifyTime The modifyTime to set.
     */
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
        putUsedField("modifyTime", modifyTime);
    }
}
