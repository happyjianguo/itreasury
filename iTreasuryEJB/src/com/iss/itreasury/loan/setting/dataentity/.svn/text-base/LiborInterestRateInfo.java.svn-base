/*
 * Created on 2005-01-20
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.setting.dataentity;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;

import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LiborInterestRateInfo extends LoanBaseDataEntity
{
	//Loan_LiborInterestRate表中有的字段
    private long Id = -1;						//ID
    private long CurrencyID = -1;				//币种
    private long OfficeID = -1;					//办事处
    private String Code = "";					//Libor编号
    private String LiborName = "";				//Libor名称
    private long IntervalNum = -1;				//Libor期限	单位：月
    private long InputUserID = -1;				//录入人
    private Timestamp InputDate = null;			//录入时间
    private long StatusID = -1;					//状态

	//Loan_LiborInterestRate表中没有的字段
    private String InputUserName = "";			//录入人名称
    
    private long queryPurpose = 1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";

    /**
     * @return Returns the code.
     */
    public String getCode()
    {
        return Code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code)
    {
        Code = code;
        putUsedField("Code", Code);
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        CurrencyID = currencyID;
        putUsedField("CurrencyID", CurrencyID);
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        Id = id;
        putUsedField("Id", Id);
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        InputDate = inputDate;
        putUsedField("InputDate", InputDate);
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return InputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        InputUserID = inputUserID;
        putUsedField("InputUserID", InputUserID);
    }
    /**
     * @return Returns the inputUserName.
     */
    public String getInputUserName()
    {
        return InputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName)
    {
        InputUserName = inputUserName;
    }
    /**
     * @return Returns the intervalNum.
     */
    public long getIntervalNum()
    {
        return IntervalNum;
    }
    /**
     * @param intervalNum The intervalNum to set.
     */
    public void setIntervalNum(long intervalNum)
    {
        IntervalNum = intervalNum;
        putUsedField("IntervalNum", IntervalNum);
    }
    /**
     * @return Returns the liborName.
     */
    public String getLiborName()
    {
        return LiborName;
    }
    /**
     * @param liborName The liborName to set.
     */
    public void setLiborName(String liborName)
    {
        LiborName = liborName;
        putUsedField("LiborName", LiborName);
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return OfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        OfficeID = officeID;
        putUsedField("OfficeID", OfficeID);
    }
    /**
     * @return Returns the pageCount.
     */
    public long getPageCount()
    {
        return pageCount;
    }
    /**
     * @param pageCount The pageCount to set.
     */
    public void setPageCount(long pageCount)
    {
        this.pageCount = pageCount;
    }
    /**
     * @return Returns the recordCount.
     */
    public long getRecordCount()
    {
        return recordCount;
    }
    /**
     * @param recordCount The recordCount to set.
     */
    public void setRecordCount(long recordCount)
    {
        this.recordCount = recordCount;
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return StatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        StatusID = statusID;
        putUsedField("StatusID", StatusID);
    }
    /**
     * @return Returns the desc.
     */
    public long getDesc()
    {
        return desc;
    }
    /**
     * @param desc The desc to set.
     */
    public void setDesc(long desc)
    {
        this.desc = desc;
    }
    /**
     * @return Returns the orderParam.
     */
    public long getOrderParam()
    {
        return orderParam;
    }
    /**
     * @param orderParam The orderParam to set.
     */
    public void setOrderParam(long orderParam)
    {
        this.orderParam = orderParam;
    }
    /**
     * @return Returns the orderParamString.
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }
    /**
     * @param orderParamString The orderParamString to set.
     */
    public void setOrderParamString(String orderParamString)
    {
        this.orderParamString = orderParamString;
    }
    /**
     * @return Returns the pageLineCount.
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }
    /**
     * @param pageLineCount The pageLineCount to set.
     */
    public void setPageLineCount(long pageLineCount)
    {
        this.pageLineCount = pageLineCount;
    }
    /**
     * @return Returns the pageNo.
     */
    public long getPageNo()
    {
        return pageNo;
    }
    /**
     * @param pageNo The pageNo to set.
     */
    public void setPageNo(long pageNo)
    {
        this.pageNo = pageNo;
    }
    /**
     * @return Returns the queryPurpose.
     */
    public long getQueryPurpose()
    {
        return queryPurpose;
    }
    /**
     * @param queryPurpose The queryPurpose to set.
     */
    public void setQueryPurpose(long queryPurpose)
    {
        this.queryPurpose = queryPurpose;
    }
    /**
     * @return Returns the rowNumEnd.
     */
    public long getRowNumEnd()
    {
        return rowNumEnd;
    }
    /**
     * @param rowNumEnd The rowNumEnd to set.
     */
    public void setRowNumEnd(long rowNumEnd)
    {
        this.rowNumEnd = rowNumEnd;
    }
    /**
     * @return Returns the rowNumStart.
     */
    public long getRowNumStart()
    {
        return rowNumStart;
    }
    /**
     * @param rowNumStart The rowNumStart to set.
     */
    public void setRowNumStart(long rowNumStart)
    {
        this.rowNumStart = rowNumStart;
    }
}
