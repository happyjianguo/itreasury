/*
 * Created on 2005-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.clientmanage.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class CimsBaseDataEntity extends ITreasuryBaseDataEntity{
    private long id = -1;
    private Timestamp modifyTime = null;       //Ê±¼ä´Á
    
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
}
