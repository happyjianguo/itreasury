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
public class ReleaseAmountlimitSettingInfo extends SettlementBaseDataEntity
{
    private long ID	= -1; //����
    private long officeID = -1; //���´�	
    private long currencyID	= -1; //����	
    private long projectID	= -1;//��ĿID    
    private long clientID = -1; //�ͻ�ID	
    private long clientType	= -1; //�ͻ�����	
    private long clientSettingFlag = -1; //�ͻ����ñ��	1��	�ͻ��������2��	�ͻ���������
    private Timestamp efficientDate	= null; //��Ч����	
    private double releaseAmountLimit = 0.0; //������(Ԫ)	
    private double awokeRate = 0.0; //���ѱ���%	
    private Timestamp inputDate	= null; //¼������	
    private long inputUserID = -1; //¼����	
    private Timestamp modifyDate = null; //�޸�����	
    private long modifyUserID = -1; //�޸���	
    private long statusID = -1; //״̬	0����ɾ��1������
    
    /**
     * @return Returns the awokeRate.
     */
    public double getAwokeRate()
    {
        return awokeRate;
    }
    /**
     * @param awokeRate The awokeRate to set.
     */
    public void setAwokeRate(double awokeRate)
    {
        putUsedField("awokeRate", awokeRate);
        this.awokeRate = awokeRate;
    }
    /**
     * @return Returns the clientID.
     */
    public long getClientID()
    {
        return clientID;
    }
    /**
     * @param clientID The clientID to set.
     */
    public void setClientID(long clientID)
    {
        putUsedField("clientID", clientID);
        this.clientID = clientID;
    }
    /**
     * @return Returns the clientSettingFlag.
     */
    public long getClientSettingFlag()
    {
        return clientSettingFlag;
    }
    /**
     * @param clientSettingFlag The clientSettingFlag to set.
     */
    public void setClientSettingFlag(long clientSettingFlag)
    {
        putUsedField("clientSettingFlag", clientSettingFlag);
        this.clientSettingFlag = clientSettingFlag;
    }
    /**
     * @return Returns the clientType.
     */
    public long getClientType()
    {
        return clientType;
    }
    /**
     * @param clientType The clientType to set.
     */
    public void setClientType(long clientType)
    {
        putUsedField("clientType", clientType);
        this.clientType = clientType;
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
        putUsedField("currencyID", currencyID);
        this.currencyID = currencyID;
    }
    /**
     * @return Returns the efficientDate.
     */
    public Timestamp getEfficientDate()
    {
        return efficientDate;
    }
    /**
     * @param efficientDate The efficientDate to set.
     */
    public void setEfficientDate(Timestamp efficientDate)
    {
        putUsedField("efficientDate", efficientDate);
        this.efficientDate = efficientDate;
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
        putUsedField("id", id);
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
        putUsedField("inputDate", inputDate);
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
        putUsedField("inputUserID", inputUserID);
        this.inputUserID = inputUserID;
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
        putUsedField("modifyDate", modifyDate);
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
        putUsedField("modifyUserID", modifyUserID);
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
        putUsedField("officeID", officeID);
        this.officeID = officeID;
    }
    /**
     * @return Returns the projectID.
     */
    public long getProjectID()
    {
        return projectID;
    }
    /**
     * @param projectID The projectID to set.
     */
    public void setProjectID(long projectID)
    {
        putUsedField("projectID", projectID);
        this.projectID = projectID;
    }
    /**
     * @return Returns the releaseAmountLimit.
     */
    public double getReleaseAmountLimit()
    {
        return releaseAmountLimit;
    }
    /**
     * @param releaseAmountLimit The releaseAmountLimit to set.
     */
    public void setReleaseAmountLimit(double releaseAmountLimit)
    {
        putUsedField("releaseAmountLimit", releaseAmountLimit);
        this.releaseAmountLimit = releaseAmountLimit;
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
        putUsedField("statusID", statusID);
        this.statusID = statusID;
    }
}
