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
public class CorpAccountMappingInfo implements Serializable
{
    long ID = -1;
    
	long OfficeID = -1;//����ID
	long CurrencyID = -1;//����ID
	String SubjectCode = "";//��Ŀ����
	long WithinAccountID = -1; //�ڲ��˻�ID
	String TransNo = "";//���ױ��
	long TransactionTypeID = -1;//��������
	long TransDirection = -1;//���׷���
	double Amount = 0.0;//���׷�����
	Timestamp Execute = null;//ִ����
	Timestamp InterestStart = null;//��Ϣ��
	String Abstract = "";//ժҪ
	long StatusID = -1;//����״̬
	long MappingID = -1; //ƥ���
	
	
	//��������
	String OfficeName = "";//��������
	String CurrencyName = "";//��������
	String TransactionTypeName = ""; //������������
	String TransDirectionName = "";//���׷�������
	String StatusName = "";//״̬����	
    
    //�������������Ƿ��Ѿ�����
    long GlentryID = -1;//��Ʒ�¼��¼ID
    long TransDetailID = -1;//������ϸ��¼ID
	
    public CorpAccountMappingInfo() {
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
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @return
     */
    public Timestamp getExecute()
    {
        return Execute;
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
    public Timestamp getInterestStart()
    {
        return InterestStart;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return OfficeID;
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
    public long getTransactionTypeID()
    {
        return TransactionTypeID;
    }

    /**
     * @return
     */
    public long getTransDirection()
    {
        return TransDirection;
    }

    /**
     * @return
     */
    public String getTransNo()
    {
        return TransNo;
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
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
        
		this.CurrencyName=SETTConstant.CurrencyType.getName(l);
    }

    /**
     * @param Timestamp
     */
    public void setExecute(Timestamp date)
    {
        Execute = date;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param Timestamp
     */
    public void setInterestStart(Timestamp date)
    {
        InterestStart = date;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
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
    public void setTransactionTypeID(long l)
    {
        TransactionTypeID = l;
        this.TransactionTypeName = SETTConstant.TransactionType.getName(l);
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
     * @param l
     */
    public void setTransNo(String str)
    {
        TransNo = str;
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
     * @return
     */
    public String getTransactionTypeName()
    {
        return TransactionTypeName;
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
	 * @return Returns the withInAccountID.
	 */
	public long getWithinAccountID() {
		return WithinAccountID;
	}

	/**
	 * @param withInAccountID The withInAccountID to set.
	 */
	public void setWithinAccountID(long withinAccountID) {
		WithinAccountID = withinAccountID;
	}

	/**
	 * @return Returns the detailID.
	 */
	public long getTransDetailID() {
		return TransDetailID;
	}

	/**
	 * @param detailID The detailID to set.
	 */
	public void setTransDetailID(long transDetailID) {
		TransDetailID = transDetailID;
	}

	/**
	 * @return Returns the glentyID.
	 */
	public long getGlentryID() {
		return GlentryID;
	}

	/**
	 * @param glentyID The glentyID to set.
	 */
	public void setGlentryID(long glentryID) {
		GlentryID = glentryID;
	}

}