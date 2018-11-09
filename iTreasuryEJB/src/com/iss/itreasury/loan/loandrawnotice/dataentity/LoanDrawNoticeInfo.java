/*
 * LoanDrawNoticeInfo.java
 *
 * Created on 2002��4��15��
 */

package com.iss.itreasury.loan.loandrawnotice.dataentity;

import java.rmi.RemoteException;
import java.beans.*;
import java.sql.*;
import java.util.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 *
 * @author
 * @version
 */
public class LoanDrawNoticeInfo implements java.io.Serializable
{
	/** Creates new LoanDrawNoticeInfo */
	public LoanDrawNoticeInfo()
	{
		super();
	}

	private long ID = -1;                    //��������ʶ
	private String Code = "";                //���룬���֪ͨ�����
	private double DrawAmount = 0;			 //�����������
    private double DrawAgentAmount = 0;      //���ڴ�����ܼ�
	private long ContractID = -1;            //��ͬID	
    private String ContractCode = "";        //��ͬ��� 
    private String ClientName = "";          //���λ
    private double ContractAmount = 0;       //��ͬ���
    private double AgentRate = 0;            //��������
	private long StatusID = -1;              //���֪ͨ��״̬
	private long InputUserID = -1;           //¼���û�ID
    private String InputUserName = "";       //¼����
	private Timestamp InputDate = null;      //¼�����ڣ��ύ����
	private long NextCheckUserID = -1;       //��һ�������ID
    private String CheckUserName = "";       //�����
    private long NextCheckLevel = -1;		 //��һ����˼���

	private long Count = -1; // ��¼��
	private long PageCount = -1; // ��¼��ҳ��
	
	private Collection YTDrawInfo = null;//��������ϸ��Ϣ
	

	private InutParameterInfo inutParameterInfo=null;
	

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

    
    /**
     * function �õ�/������������ʶ
     * return long
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param l
     * function �õ�/������������ʶ
     * return void
     */
    public void setID(long l)
    {
        this.ID = l;
    }

    /**
     * function �õ�/�������֪ͨ�����
     * return String
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param s
     * function �õ�/�������֪ͨ�����
     * return void
     */
    public void setCode(String s)
    {
        this.Code = s;
    }

    /**
     * function �õ�/���ñ����������
     * return double
     */
    public double getDrawAmount()
    {
        return DrawAmount;
    }

    /**
     * @param d
     * function �õ�/���ñ����������
     * return void
     */
    public void setDrawAmount(double d)
    {
        this.DrawAmount = d;
    }

    /**
     * function �õ�/���ñ��ڴ�����ܼ�
     * return double
     */
    public double getDrawAgentAmount()
    {
        return DrawAgentAmount;
    }

    /**
     * @param d
     * function �õ�/���ñ��ڴ�����ܼ�
     * return void
     */
    public void setDrawAgentAmount(double d)
    {
        this.DrawAgentAmount = d;
    }

    /**
     * function �õ�/���ú�ͬ��� 
     * return String
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param s
     * function �õ�/���ú�ͬ��� 
     * return void
     */
    public void setContractCode(String s)
    {
        this.ContractCode = s;
    }

    /**
     * function �õ�/���ô��λ
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param s
     * function �õ�/���ô��λ
     * return void
     */
    public void setClientName(String s)
    {
        this.ClientName = s;
    }

    /**
     * function �õ�/���ú�ͬ���
     * return double
     */
    public double getContractAmount()
    {
        return ContractAmount;
    }

    /**
     * @param d
     * function �õ�/���ú�ͬ���
     * return void
     */
    public void setContractAmount(double d)
    {
        this.ContractAmount = d;
    }

    /**
     * function �õ�/�������֪ͨ��״̬
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param l
     * function �õ�/�������֪ͨ��״̬
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function �õ�/����¼�����ڣ��ύ����
     * return Timestamp InputDate
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }

    /**
     * @param ts
     * function �õ�/����¼�����ڣ��ύ����
     * return void
     */
    public void setInputDate(Timestamp ts)
    {
        this.InputDate = ts;
    }

    /**
     * function �õ�/����¼���û�ID
     * return long
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @param l
     * function �õ�/����¼���û�ID
     * return void
     */
    public void setInputUserID(long l)
    {
        this.InputUserID = l;
    }

    /**
     * function �õ�/����¼����
     * return String
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param s
     * function �õ�/����¼����
     * return void
     */
    public void setInputUserName(String s)
    {
        this.InputUserName = s;
    }

    /**
     * function �õ�/������һ�������ID
     * return long
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }

    /**
     * @param l
     * function �õ�/������һ�������ID
     * return void
     */
    public void setNextCheckUserID(long l)
    {
        this.NextCheckUserID = l;
    }

    /**
     * function �õ�/���������
     * return String
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @param s
     * function �õ�/���������
     * return void
     */
    public void setCheckUserName(String s)
    {
        this.CheckUserName = s;
    }

    /**
     * function �õ�/���ü�¼��
     * return long
     */
    public long getCount()
    {
        return Count;
    }

    /**
     * @param l
     * function �õ�/���ü�¼��
     * return void
     */
    public void setCount(long l)
    {
        this.Count = l;
    }

    /**
     * function �õ�/���ü�¼��ҳ��
     * return long
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param l
     * function �õ�/���ü�¼��ҳ��
     * return void
     */
    public void setPageCount(long l)
    {
        this.PageCount = l;
    }

    /**
     * function �õ�/���ú�ͬID   
     * return long
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param l
     * function �õ�/���ú�ͬID   
     * return void
     */
    public void setContractID(long l)
    {
        this.ContractID = l;
    }

    /**
     * function �õ�/������������
     * return double
     */
    public double getAgentRate()
    {
        return AgentRate;
    }

    /**
     * @param d
     * function �õ�/������������
     * return void
     */
    public void setAgentRate(double d)
    {
        this.AgentRate = d;
    }

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return NextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        NextCheckLevel = nextCheckLevel;
    }
	/**
	 * @return
	 */
	public Collection getYTDrawInfo()
	{
		return YTDrawInfo;
	}

	/**
	 * @param collection
	 */
	public void setYTDrawInfo(Collection collection)
	{
		YTDrawInfo = collection;
	}

}