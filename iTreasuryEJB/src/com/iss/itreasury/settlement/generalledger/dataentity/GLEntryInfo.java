/*
 * Created on 2003-9-12
 *
 */
package com.iss.itreasury.settlement.generalledger.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.*;
import java.io.Serializable;
/**
 *
 *  ��Ʒ�¼
 *  @author yqwu
 *
 */
public class GLEntryInfo implements Serializable
{
    long ID = -1;
    
	long OfficeID = -1;//����ID
	long CurrencyID = -1;//����ID
	String SubjectCode = "";//��Ŀ����
	String TransNo = "";//���ױ��
	long TransactionTypeID = -1;//��������
	long TransDirection = -1;//���׷���
	double Amount = 0.0;//���׷�����
	Timestamp Execute = null;//ִ����
	Timestamp InterestStart = null;//��Ϣ��
	String Abstract = "";//ժҪ
	String MultiCode = "";//��ά��
	long InputUserID = -1;//¼����
	long CheckUserID = -1;//������
	long StatusID = -1;//״̬
	
	//����
	long Group = -1;
	long Type = -1;
	long PostStatusID = -1;
	
	//��������
	String OfficeName = "";//��������
	String CurrencyName = "";//��������
	String TransDirectionName = "";//���׷�������
	String InputUserName = "";//¼����
	String CheckUserName = "";//������
	String StatusName = "";//״̬����
	
	//����������Ϣ
	String assitantName = ""; //������������
	String assitantValue = ""; //��������ֵ��ȡ�ͻ����"-"��Ĵ� �磺01-000001 ȡ��000001����

	//�°����ӿͻ���Ϣ
	String clientCode = "";//�ͻ����
	String clientName = "";//�ͻ�����
	String clientShortName = "";//�ͻ����

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
    public long getCheckUserID()
    {
        return CheckUserID;
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
    public long getGroup()
    {
        return Group;
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
    public long getInputUserID()
    {
        return InputUserID;
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
    public String getMultiCode()
    {
        return MultiCode;
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
     * @return
     */
    public long getType()
    {
        return Type;
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
    public void setCheckUserID(long l)
    {
        CheckUserID = l;
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
    public void setGroup(long l)
    {
        Group = l;
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
    public void setInputUserID(long l)
    {
        InputUserID = l;
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
    public void setMultiCode(String str)
    {
        MultiCode = str;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
        
		//��NameRef��Ӧ����ȡ��Exception��ȡ��try block
//		try
//		{
//			NameRef.getOfficeNameByID(l);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}        
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
        this.StatusName=SETTConstant.EntryStatus.getName(l);
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
        this.TransDirectionName=SETTConstant.DebitOrCredit.getName(l);
    }

    /**
     * @param l
     */
    public void setTransDirection(long l)
    {
        TransDirection = l;
    }

    /**
     * @param l
     */
    public void setTransNo(String str)
    {
        TransNo = str;
    }

    /**
     * @param l
     */
    public void setType(long l)
    {
        Type = l;
    }

    /**
     * @return
     */
    public String getCheckUserName()
    {
        return CheckUserName;
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
    public String getInputUserName()
    {
        return InputUserName;
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
	public long getPostStatusID()
	{
		return PostStatusID;
	}

	/**
	 * @param l
	 */
	public void setPostStatusID(long l)
	{
		PostStatusID = l;
	}

	public String getAssitantName()
	{
		return assitantName;
	}

	public void setAssitantName(String assitantName)
	{
		this.assitantName = assitantName;
	}

	public String getAssitantValue()
	{
		return assitantValue;
	}

	public void setAssitantValue(String assitantValue)
	{
		this.assitantValue = assitantValue;
	}
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientShortName() {
		return clientShortName;
	}

	public void setClientShortName(String clientShortName) {
		this.clientShortName = clientShortName;
	}

	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}

	
}