/*
 * atTermAwakeInfo.java
 *
 * Created on 2004��4��21��, ����
 */

package com.iss.itreasury.loan.setting.dataentity;

import java.beans.*;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 *
 * @author  ninh
 * @version
 */

public class ApprovalOpinionInfo extends ITreasuryBaseDataEntity
{
    //ID           NUMBER not null,
    private long id = -1;//���������ʾ
    //CODE         VARCHAR2(5),
    private String code = "";//����������
    //DESCRIPTION  VARCHAR2(500),
    private String description = "";//�����������
    //STATUSID     NUMBER,
    private long statusId = -1;//״̬ �Ƿ���Ч
    //INPUTUSERID  NUMBER,
    private long inputUserId = -1;//¼����
    //INPUTDATE    DATE,
    private Timestamp inputDate = null;//¼��ʱ��
    //UPDATEUSERID NUMBER,
    private long updateUserId = -1;//�޸���
    //UPDATEDATE   DATE
    private Timestamp updateDate = null;//�޸�ʱ��
    
	private long		nOfficeID				= -1 ;     //���´�
	private long 		nCurrencyID			    = -1 ;     //����
    
	/**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getNOfficeID()
    {
        return nOfficeID;
    }
    
	/**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getNCurrencyID()
    {
        return nCurrencyID;
    }

	/**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param 
     * function �õ�/����
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getInputUserId()
    {
        return inputUserId;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getStatusId()
    {
        return statusId;
    }

    /**
     * @param 
     * function �õ�/����
     * return Timestamp
     */
    public Timestamp getUpdateDate()
    {
        return updateDate;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getUpdateUserId()
    {
        return updateUserId;
    }

   
    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNOfficeID(long l)
    {
        nOfficeID = l;
        putUsedField("nOfficeID", nOfficeID);
    }
    
    
    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNCurrencyID(long l)
    {
        nCurrencyID = l;
        putUsedField("nCurrencyID", nCurrencyID);
    }
    
    
    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCode(String string)
    {
        code = string;
        putUsedField("code", code);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setDescription(String string)
    {
        description = string;
        putUsedField("description", description);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setId(long l)
    {
        id = l;
        putUsedField("id", id);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        inputDate = timestamp;
        putUsedField("inputDate", inputDate);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputUserId(long l)
    {
        inputUserId = l;
        putUsedField("inputUserId", inputUserId);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStatusId(long l)
    {
        statusId = l;
        putUsedField("statusId", statusId);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setUpdateDate(Timestamp timestamp)
    {
        updateDate = timestamp;
        putUsedField("updateDate", updateDate);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setUpdateUserId(long l)
    {
        updateUserId = l;
        putUsedField("updateUserId", updateUserId);
    }

}