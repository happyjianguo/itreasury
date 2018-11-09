/*
 * Created on 2005-9-15
 *
 */
package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.*;

import java.io.Serializable;
/**
 *
 *  ��ҵ����ƾ֤
 *  @author qijiang
 *
 */
public class BankAccountMappingInfo implements Serializable
{
    long ID = -1;
    
    String accountNo = "";
	String SubjectCode = "";//��Ŀ����
	long TransDirection = -1;//���׷���
	double Amount = 0.0;//���׷�����
	Timestamp transDate = null;//���׷�������
	String Abstract = "";//ժҪ
	long StatusID = -1;//����״̬
	long MappingID = -1; //ƥ���
	
	
	//��������
	String OfficeName = "";//��������
	String CurrencyName = "";//��������
	String TransDirectionName = "";//���׷�������
	String StatusName = "";//״̬����	
	
	

    public BankAccountMappingInfo() {
		super();
	}

	/**
     * @return
     */
    public String getAbstract()
    {
        return Abstract;
    }

    /**
     * @return
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @return
     */
    public String getSubjectCode()
    {
        return SubjectCode;
    }

    /**
     * @return
     */
    public long getTransDirection()
    {
        return TransDirection;
    }

    /**
     * @param string
     */
    public void setAbstract(String string)
    {
        Abstract = string;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        Amount = d;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setStatusID(long lStatusID)
    {
        StatusID = lStatusID;
        this.StatusName=SETTConstant.CheckAccountBookStatus.getName(lStatusID);
    }

    /**
     * @param l
     */
    public void setSubjectCode(String str)
    {
        SubjectCode = str;
    }

    /**
     * @param l
     */
    public void setTransDirection(long l)
    {
        TransDirection = l;
        this.TransDirectionName = SETTConstant.DebitOrCredit.getName(l);
    }

    /**
     * @return
     */
    public String getCurrencyName()
    {
        return CurrencyName;
    }

    /**
     * @return
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @return
     */
    public String getStatusName()
    {
        return StatusName;
    }

    /**
     * @return
     */
    public String getTransDirectionName()
    {
        return TransDirectionName;
    }

	/**
	 * @return Returns the mappingID.
	 */
	public long getMappingID() {
		return MappingID;
	}

	/**
	 * @param mappingID The mappingID to set.
	 */
	public void setMappingID(long mappingID) {
		MappingID = mappingID;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the transDate.
	 */
	public Timestamp getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate The transDate to set.
	 */
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}

}