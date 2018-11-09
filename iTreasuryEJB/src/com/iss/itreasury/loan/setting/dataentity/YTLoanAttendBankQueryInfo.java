
package com.iss.itreasury.loan.setting.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 *
 * @author  ninh
 * @version
 */

public class YTLoanAttendBankQueryInfo extends ITreasuryBaseDataEntity
{
    private long id = -1;
    //NAME             VARCHAR2(100),
    private long nameId = -1;//
    private String name = "";//
    //BANK             VARCHAR2(100),
    private long bankId = -1;//
    private String bank = "";//
    //OTHERGLSUBJECTID NUMBER,
    private long otherGLSubjectId = -1;//
    private String otherGLSubjectCode = "";//

    private long pageLineCount = -1;
    private long pageNo = -1;
    private long orderParam = -1;
    private long desc = -1;
    private String orderParamString = "";
//  modify by xwhe 2007-08-06
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
     * return long
     */
    public long getBankId()
    {
        return bankId;
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
    public long getNameId()
    {
        return nameId;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
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
    public long getOtherGLSubjectId()
    {
        return otherGLSubjectId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBank(String string)
    {
        bank = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBankId(long l)
    {
        bankId = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setName(String string)
    {
        name = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNameId(long l)
    {
        nameId = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOtherGLSubjectCode(String string)
    {
        otherGLSubjectCode = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOtherGLSubjectId(long l)
    {
        otherGLSubjectId = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOrderParam()
    {
        return orderParam;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getPageNo()
    {
        return pageNo;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setDesc(long l)
    {
        desc = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOrderParam(long l)
    {
        orderParam = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOrderParamString(String string)
    {
        orderParamString = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setPageLineCount(long l)
    {
        pageLineCount = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setPageNo(long l)
    {
        pageNo = l;
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