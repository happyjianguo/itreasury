/*
 * CustomerTypeInfo.java
 *
 * Created on 2002��2��19��, ����5:53
 */

package com.iss.itreasury.loan.loancommonsetting.dataentity;

//import java.beans.*;
import java.sql.Timestamp;

/**
 *
 * @author  yzhang
 * @version
 */
public class ClientTypeInfo implements java.io.Serializable {

    /**
     * CustomerTypeInfo ������ע�⡣
     * use the DB Table  :   SETT_ClientType
     */
    public ClientTypeInfo()
    {
        super();
    }

    //ID:��ʶ
    private long ClientTypeID;

    //sCode:����
    private String Code;

    //sName:����
    private String Name;

    //nInputUserID:¼���˱�ʶ
    private long InputUserID;

    //¼��������
    private String InputUserName;

    //dtInput:¼������
    private Timestamp dtInput;
    
    //nUpdateUserID:�����˱�ʶ
    private long UpdateUserID;

    //����������
    private String UpdateUserName;

    //dtUpdate:��������
    private Timestamp dtUpdate;

    //nStatusID:״̬���Ƿ����
    private long StatusID;

    //��ҳ��ʾ��ҳ����¼
    private long PageCount;

    /**
     * function �õ���ʶ
     * return lID
     */
    public long getClientTypeID()
    {
        return ClientTypeID;
    }

    /**
     * @param lID
     * function ���ñ�ʶ
     * return void
     */
    public void setClientTypeID(long lID)
    {
        this.ClientTypeID = lID;
    }

    /**
     * function �õ�����
     * return Code
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param strCode
     * function ���ñ���
     * return void
     */
    public void setCode(String strCode)
    {
        this.Code = strCode;
    }

    /**
     * function �õ�����
     * return Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @param strName
     * function �õ�/����
     * return void
     */
    public void setName(String strName)
    {
        this.Name = strName;
    }

    /**
     * function �õ�¼��������
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strInputUserName
     * function �õ�/����¼��������
     * return void
     */
    public void setInputUserName(String strInputUserName)
    {
        this.InputUserName = strInputUserName;
    }

    /**
     * function �õ�¼��ʱ��
     * return dtInput
     */
    public Timestamp getDtInput()
    {
        return dtInput;
    }

    /**
     * @param dtInput
     * function ����¼��ʱ��
     * return void
     */
    public void setDtInput(Timestamp dtInput)
    {
        this.dtInput = dtInput;
    }

    /**
     * function �õ�ҳ��
     * return lPageCount
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param lPageCount
     * function����ҳ��
     * return void
     */
    public void setPageCount(long lPageCount)
    {
        this.PageCount = lPageCount;
    }

}
