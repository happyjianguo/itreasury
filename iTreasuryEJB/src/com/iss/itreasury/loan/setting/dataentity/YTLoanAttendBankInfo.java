/*
 * YTLoanAttendBankInfo.java
 *
 * Created on 2004��5��31��, ����
 */

package com.iss.itreasury.loan.setting.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 *
 * @author  ninh
 * @version
 */

public class YTLoanAttendBankInfo extends ITreasuryBaseDataEntity 
{
    private long id = -1;  
    //NAME             VARCHAR2(100),
    private String name = "";//
    //BANK             VARCHAR2(100),   
    private String bank = "";//
    //BANKACCOUNTNO    VARCHAR2(100),
    private String bankAccountNo = "";//
    //OTHERGLSUBJECTCODE    VARCHAR2(100),
    private String otherGLSubjectCode = "";//
    //STATUSID     NUMBER,
    private long isLeaderBank = -1;//
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
    //modify by xwhe 2007-08-06
    private long currencyID = -1;				//����
    private long officeID = -1;					//���´�
    
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
     * return String
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getBankAccountNo()
    {
        return bankAccountNo;
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
    public long getIsLeaderBank()
    {
        return isLeaderBank;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public String getOtherGLSubjectCode()
    {
        return otherGLSubjectCode;
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
    public void setBank(String string)
    {
        bank = string;
        putUsedField("bank",bank);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBankAccountNo(String string)
    {
        bankAccountNo = string;
        putUsedField("bankAccountNo",bankAccountNo);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        inputDate = timestamp;
        putUsedField("inputDate",inputDate);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputUserId(long l)
    {
        inputUserId = l;
        putUsedField("inputUserId",inputUserId);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setIsLeaderBank(long l)
    {
        isLeaderBank = l;
        putUsedField("isLeaderBank",isLeaderBank);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setName(String string)
    {
        name = string;
        putUsedField("name",name);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOtherGLSubjectCode(String s)
    {
        otherGLSubjectCode = s;
        putUsedField("otherGLSubjectCode",otherGLSubjectCode);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStatusId(long l)
    {
        statusId = l;
        putUsedField("statusId",statusId);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setUpdateDate(Timestamp timestamp)
    {
        updateDate = timestamp;
        putUsedField("updateDate",updateDate);
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
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

}