/*
 * Created on 2004-4-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author huanwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryBusinessTypeInfo extends SECBaseDataEntity implements
        Serializable {

    private long businessTypeID = -1;//业务类型ID

    private long businessAttributeID = -1;//业务性质ID

    private String code = "";//业务类型编号

    private String tname = "";//业务类型名称

    private String aname = "";//业务性质名称

    private long statusID = -1;//状态

    private long inputUserID = -1;//录入人

    private Timestamp inputDate = null; //录入时间

    private long updateUserID = -1;//修改人

    private Timestamp updateDate = null; //修改时间

    private long checkUserID = -1;//复核人

    private Timestamp checkDate = null; //复核时间

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
     * @return Returns the checkDate.
     */
    public Timestamp getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate
     *            The checkDate to set.
     */
    public void setCheckDate(Timestamp checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @return Returns the checkUserID.
     */
    public long getCheckUserID() {
        return checkUserID;
    }

    /**
     * @param checkUserID
     *            The checkUserID to set.
     */
    public void setCheckUserID(long checkUserID) {
        this.checkUserID = checkUserID;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate
     *            The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID() {
        return inputUserID;
    }

    /**
     * @param inputUserID
     *            The inputUserID to set.
     */
    public void setInputUserID(long inputUserID) {
        this.inputUserID = inputUserID;
    }

    /**
     * @return Returns the statusID.
     */
    public long getStatusID() {
        return statusID;
    }

    /**
     * @param statusID
     *            The statusID to set.
     */
    public void setStatusID(long statusID) {
        this.statusID = statusID;
    }

    /**
     * @return Returns the updateDate.
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     *            The updateDate to set.
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return Returns the updateUserID.
     */
    public long getUpdateUserID() {
        return updateUserID;
    }

    /**
     * @param updateUserID
     *            The updateUserID to set.
     */
    public void setUpdateUserID(long updateUserID) {
        this.updateUserID = updateUserID;
    }

    /**
     * @return Returns the aname.
     */
    public String getAname() {
        return aname;
    }

    /**
     * @param aname
     *            The aname to set.
     */
    public void setAname(String aname) {
        this.aname = aname;
    }

    /**
     * @return Returns the tname.
     */
    public String getTname() {
        return tname;
    }

    /**
     * @param tname
     *            The tname to set.
     */
    public void setTname(String tname) {
        this.tname = tname;
    }

    /**
     * @return Returns the businessTypeID.
     */
    public long getBusinessTypeID() {
        return businessTypeID;
    }

    /**
     * @param businessTypeID
     *            The businessTypeID to set.
     */
    public void setBusinessTypeID(long businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    /**
     * @return Returns the businessAttributeID.
     */
    public long getBusinessAttributeID() {
        return businessAttributeID;
    }

    /**
     * @param businessAttributeID
     *            The businessAttributeID to set.
     */
    public void setBusinessAttributeID(long businessAttributeID) {
        this.businessAttributeID = businessAttributeID;
    }
}
