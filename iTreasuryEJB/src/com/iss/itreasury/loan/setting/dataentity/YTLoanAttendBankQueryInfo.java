
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
    private long currencyID = -1;				//币种
    private long officeID = -1;					//办事处
    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setId(long l)
    {
        id = l;
        putUsedField("id", id);
    }
    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBankId()
    {
        return bankId;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNameId()
    {
        return nameId;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getOtherGLSubjectCode()
    {
        return otherGLSubjectCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOtherGLSubjectId()
    {
        return otherGLSubjectId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBank(String string)
    {
        bank = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBankId(long l)
    {
        bankId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setName(String string)
    {
        name = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setNameId(long l)
    {
        nameId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOtherGLSubjectCode(String string)
    {
        otherGLSubjectCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOtherGLSubjectId(long l)
    {
        otherGLSubjectId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOrderParam()
    {
        return orderParam;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPageNo()
    {
        return pageNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDesc(long l)
    {
        desc = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOrderParam(long l)
    {
        orderParam = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOrderParamString(String string)
    {
        orderParamString = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPageLineCount(long l)
    {
        pageLineCount = l;
    }

    /**
     * @param 
     * function 得到/设置
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