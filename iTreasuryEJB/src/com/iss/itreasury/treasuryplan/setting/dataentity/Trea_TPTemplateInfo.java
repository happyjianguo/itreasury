package com.iss.itreasury.treasuryplan.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * @name: Trea_TPTemplateInfo
 * @description:�ʽ�ƻ�ģ��
 * @author: jason
 * @create: 2005-11-15
 */
public class Trea_TPTemplateInfo extends TreasuryPlanBaseDataEntity implements Serializable
{
    private long      id                   = -1;  // ΨһԼ��

    private long      officeId             = -1;  // ���´���ʶ

    private long      currencyId           = -1;  // ���ֱ�ʶ

    private String    lineNo               = null; // ����Ŀ���

    private String    lineName             = null; // ����Ŀ����

    private long      lineLevel            = -1;  // ����Ŀ����

    private long      parentLineId         = -1;  // �ϼ�����ĿId

    private long      isLeaf               = -1;  // �Ƿ�Ҷ�� 0=��1=��

    private long      isReadonly           = -1;  // �Ƿ�ֻ����Ŀ

    private long      isNeedSum            = -1;   // �Ƿ���빫ʽ����

    private String    authorizedDepartment = null; // ��������

    private String    authorizedUser       = null; // �����û�

    private long      maintenanceFlag      = -1;  // ά����־

    private long      statusId             = -1;  // ״̬

    private long      inputUserId          = -1;  // ¼����

    private Timestamp inputDate            = null; // ¼������

    private long      updateUserId         = -1;  // �޸���

    private Timestamp updateDate           = null; // �޸�ʱ��

    // ���ݿ�û�е��ֶ�
    private long      maxLevel             = -1;  // ģ����Ŀ����󼶱�

    public long getId()
    {
        // TODO Auto-generated method stub
        return id;
    }

    public void setId(long id)
    {
        // TODO Auto-generated method stub
        this.id = id;
        putUsedField("id", id);
    }

    /**
     * @return Returns the authorizedDepartment.
     */
    public String getAuthorizedDepartment()
    {
        return authorizedDepartment;
    }

    /**
     * @param authorizedDepartment The authorizedDepartment to set.
     */
    public void setAuthorizedDepartment(String authorizedDepartment)
    {
        this.authorizedDepartment = authorizedDepartment;
        putUsedField("authorizedDepartment", authorizedDepartment);
    }

    /**
     * @return Returns the authorizedUser.
     */
    public String getAuthorizedUser()
    {
        return authorizedUser;
    }

    /**
     * @param authorizedUser The authorizedUser to set.
     */
    public void setAuthorizedUser(String authorizedUser)
    {
        this.authorizedUser = authorizedUser;
        putUsedField("authorizedUser", authorizedUser);
    }

    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId()
    {
        return currencyId;
    }

    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId)
    {
        this.currencyId = currencyId;
        putUsedField("currencyId", currencyId);
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
        putUsedField("inputDate", inputDate);
    }

    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId()
    {
        return inputUserId;
    }

    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId)
    {
        this.inputUserId = inputUserId;
        putUsedField("inputUserId", inputUserId);
    }

    /**
     * @return Returns the isNeedSum.
     */
    public long getIsNeedSum()
    {
        return isNeedSum;
    }

    /**
     * @param isNeedSum The isNeedSum to set.
     */
    public void setIsNeedSum(long isNeedSum)
    {
        this.isNeedSum = isNeedSum;
        putUsedField("isNeedSum", isNeedSum);
    }

    /**
     * @return Returns the isLeaf.
     */
    public long getIsLeaf()
    {
        return isLeaf;
    }

    /**
     * @param isLeaf The isLeaf to set.
     */
    public void setIsLeaf(long isLeaf)
    {
        this.isLeaf = isLeaf;
        putUsedField("isLeaf", isLeaf);
    }

    /**
     * @return Returns the isReadonly.
     */
    public long getIsReadonly()
    {
        return isReadonly;
    }

    /**
     * @param isReadonly The isReadonly to set.
     */
    public void setIsReadonly(long isReadonly)
    {
        this.isReadonly = isReadonly;
        putUsedField("isReadonly", isReadonly);
    }

    /**
     * @return Returns the lineLevel.
     */
    public long getLineLevel()
    {
        return lineLevel;
    }

    /**
     * @param lineLevel The lineLeval to set.
     */
    public void setLineLevel(long lineLevel)
    {
        this.lineLevel = lineLevel;
        putUsedField("lineLevel", lineLevel);
    }

    /**
     * @return Returns the lineName.
     */
    public String getLineName()
    {
        return lineName;
    }

    /**
     * @param lineName The lineName to set.
     */
    public void setLineName(String lineName)
    {
        this.lineName = lineName;
        putUsedField("lineName", lineName);
    }

    /**
     * @return Returns the lineNo.
     */
    public String getLineNo()
    {
        return lineNo;
    }

    /**
     * @param lineNo The lineNo to set.
     */
    public void setLineNo(String lineNo)
    {
        this.lineNo = lineNo;
        putUsedField("lineNo", lineNo);
    }

    /**
     * @return Returns the maintenanceFlag.
     */
    public long getMaintenanceFlag()
    {
        return maintenanceFlag;
    }

    /**
     * @param maintenanceFlag The maintenanceFlag to set.
     */
    public void setMaintenanceFlag(long maintenanceFlag)
    {
        this.maintenanceFlag = maintenanceFlag;
        putUsedField("maintenanceFlag", maintenanceFlag);
    }

    /**
     * @return Returns the officeId.
     */
    public long getOfficeId()
    {
        return officeId;
    }

    /**
     * @param officeId The officeId to set.
     */
    public void setOfficeId(long officeId)
    {
        this.officeId = officeId;
        putUsedField("officeId", officeId);
    }

    /**
     * @return Returns the parentLineId.
     */
    public long getParentLineId()
    {
        return parentLineId;
    }

    /**
     * @param parentLineId The parentLineId to set.
     */
    public void setParentLineId(long parentLineId)
    {
        this.parentLineId = parentLineId;
        putUsedField("parentLineId", parentLineId);
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
     * @return Returns the updateDate.
     */
    public Timestamp getUpdateDate()
    {
        return updateDate;
    }

    /**
     * @param updateDate The updateDate to set.
     */
    public void setUpdateDate(Timestamp updateDate)
    {
        this.updateDate = updateDate;
        putUsedField("updateDate", updateDate);
    }

    /**
     * @return Returns the updateUserId.
     */
    public long getUpdateUserId()
    {
        return updateUserId;
    }

    /**
     * @param updateUserId The updateUserId to set.
     */
    public void setUpdateUserId(long updateUserId)
    {
        this.updateUserId = updateUserId;
        putUsedField("updateUserId", updateUserId);
    }

    /**
     * @return Returns the maxLevel.
     */
    public long getMaxLevel()
    {
        return maxLevel;
    }

    /**
     * @param maxLevel The maxLevel to set.
     */
    public void setMaxLevel(long maxLevel)
    {
        this.maxLevel = maxLevel;
    }
}
