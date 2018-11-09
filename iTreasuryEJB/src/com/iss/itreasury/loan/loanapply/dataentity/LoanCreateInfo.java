package com.iss.itreasury.loan.loanapply.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>Title: iTreasury </p> 
 * <p>Description: �������Ϣ</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: gump
 * @version 1.0
 * @Date: 2003-09-25
 * �������ֶα���Ϊ�Ǵ����������Ϣ
 * loanID           long        �����������ˮ��
 * typeID           long        ������������
 * currencyID       long        ����Ļ����������
 * officeID         long        ���´�����
 * applyCode        String      �����
 * consignClientID  long        ί�е�λ����
 * borrowClientID   long        ��λ����(�ͻ����룩
 * userID          long         ¼���˱�ʾ
 * sDate           Timestamp    ¼��ʱ��
 * strPrivLevel     String      ¼����Ȩ��
 */
public class LoanCreateInfo implements Serializable
{
    private long        loanID=-1;             //��ˮ��
    private long        typeID=-1;             //��������
    private long        subTypeId= -1;         //��������
    private long        currencyID=-1;         //���Ҵ���
    private long        officeID=-1;           //���´�����
    private String      applyCode="";          //���������
    private long        consignClientID=-1;    //ί�е�λ����
    private long        borrowClientID=-1;     //��λ
    private long        inputUserID=-1;        //¼���˴���
    private Timestamp   inputDate=null;        //¼��ʱ��   
	private long        sellClientID=-1;       //��Ӧ��
	private long        isBuyInto = -1;        //�Ƿ������ʲ�
 
    public long getIsBuyInto() {
		return isBuyInto;
	}
	public void setIsBuyInto(long isBuyInto) {
		this.isBuyInto = isBuyInto;
	}
	/**
     * ����������Ϣ��
     */
    public LoanCreateInfo()
    {
        super();
    }
    /**
     * ���ô����������ˮ��
     * @param loanID long,�����������ˮ��
     */
    public void setLoanID(long loanID)
    {
         this.loanID=loanID;
    }
    /**
     * ��ȡ�����������ˮ��
     * @return long �����������ˮ��
     */
    public long getLoanID()
    {
        return loanID;
    }
    /**
     * ���ô�������ͺ�
     * @param typeID long ��������ʹ���
     */
    public void setTypeID(long typeID)
    {
        this.typeID=typeID;
    }
    /**
     * ��ȡ��������ʹ���
     * @return long ��������ʹ���
     */
    public long getTypeID()
    {
        return typeID;
    }
    
    /**
     * ���û��Ҵ���
     * @param long currencyID ����Ļ��Ҵ���
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID=currencyID;
    }
    /**
     * ��ô���Ļ��Ҵ���
     * @return long ����Ļ��Ҵ���
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    
    /**
     * ���ð��´�����
     * @param long officeID ���´�����
     */
    public void setOfficeID(long officeID)
    {
        this.officeID=officeID;
    }
    /**
     * ��ð��´�����
     * @return long ���´�����
     */
    public long getOfficeID()
    {
        return officeID;
    }
    
    /**
     * ���ô��������
     * @param String applyCode
     */
    public void setApplyCode(String applyCode)
    {
        this.applyCode=applyCode;
    }
    /**
     * ��ô���������
     * @return String ����������
     */
    public String getApplyCode()
    {
        return this.applyCode;
    }
    
    /**
     * ����ί�е�λ
     * @param long consignClientID ί�е�λ����
     */
    public void setConsignClientID(long consignClientID)
    {
        this.consignClientID=consignClientID;
    }
    /**
     * ��ȡί�е�λ
     * @return long ί�е�λ����
     */
    public long getConsignClientID()
    {
        return this.consignClientID ;
    }
    
    /**
     * ���ý�λ����
     * @param long borrowClientID ��λ����
     */
    public void setBorrowClientID(long borrowClientID)
    {
        this.borrowClientID=borrowClientID;
    }
    /**
     * ���ؽ�λ����
     * @return long ��λ����
     */
    public long getBorrowClientID()
    {
        return this.borrowClientID;
    }
    /**
     * ����¼���˴���
     * @param inputUserID long
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID=inputUserID;
    }
    
    /**
     * ��ȡ¼���˴���
     * @return long
     */
    public long getInputUserID()
    {
        return this.inputUserID;
    }
    
    /**
     * ����¼������
     * @param inputDate Timestamp
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate=inputDate;
    }
    
    /**
     * ��ȡ¼������
     * @return Timestamp
     */
    public Timestamp getInputDate()
    {
        return this.inputDate;
    }
    
	public static void main(String[] args)
	{
	}
	/**
	 * @return
	 */
	public long getSellClientID() {
		return sellClientID;
	}

	/**
	 * @param l
	 */
	public void setSellClientID(long l) {
		sellClientID = l;
	}

    /**
     * @return Returns the subTypeId.
     */
    public long getSubTypeId() {
        return subTypeId;
    }
    /**
     * @param subTypeId The subTypeId to set.
     */
    public void setSubTypeId(long subTypeId) {
        this.subTypeId = subTypeId;
    }
}
