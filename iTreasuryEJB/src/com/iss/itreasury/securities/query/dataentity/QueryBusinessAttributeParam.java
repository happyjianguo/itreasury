/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
//import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author huanwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryBusinessAttributeParam extends SECBaseDataEntity implements
        Serializable {

    private String codeStart = "";//业务性质编号(起始)
    
    private String codeEnd = "";//业务性质编号(截止)

    private String[] names = null;//业务性质名称

    private long desc = -1;//排序方式

    private long orderField = -1;//排序字段

    /*
     * (non-Javadoc)
     * 
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
     */
    public long getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long id) {
        // TODO Auto-generated method stub

    }

    /**
     * @return Returns the desc.
     */
    public long getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            The desc to set.
     */
    public void setDesc(long desc) {
        this.desc = desc;
    }

    /**
     * @return Returns the orderField.
     */
    public long getOrderField() {
        return orderField;
    }

    /**
     * @param orderField
     *            The orderField to set.
     */
    public void setOrderField(long orderField) {
        this.orderField = orderField;
    }

    

    /**
     * @return Returns the names.
     */
    public String[] getNames() {
        return names;
    }

    /**
     * @param names
     *            The names to set.
     */
    public void setNames(String[] names) {
        this.names = names;
    }
    
    /**
     * @return Returns the codeEnd.
     */
    public String getCodeEnd() {
        return codeEnd;
    }
    /**
     * @param codeEnd The codeEnd to set.
     */
    public void setCodeEnd(String codeEnd) {
        this.codeEnd = codeEnd;
    }
    /**
     * @return Returns the codeStart.
     */
    public String getCodeStart() {
        return codeStart;
    }
    /**
     * @param codeStart The codeStart to set.
     */
    public void setCodeStart(String codeStart) {
        this.codeStart = codeStart;
    }
}
