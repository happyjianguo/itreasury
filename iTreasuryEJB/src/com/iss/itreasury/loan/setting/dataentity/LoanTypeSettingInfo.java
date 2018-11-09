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

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeSettingInfo extends LoanBaseDataEntity
{
	//Loan_LoanTypeSetting表中有的字段
    private long Id = -1;						//ID
    private String Code = "";					//贷款类型分类编号
    private String Name = "";					//贷款类型分类名称
    private long LoanTypeID = -1;				//贷款类型大类ID
    private long SubLoanTypeID = -1;			//贷款类型子类ID
    private long StatusID = -1;					//状态
    private long OfficeID=-1;					//办事处ID 2007.6.10 qhzhou
    private long CurrencyID=-1;					//币种ID   2007.6.10 qhzhou

	//Loan_LoanTypeSetting表中没有的字段
    private long queryPurpose = -1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";
    
    private long bankAcceptPo = -1; //银行承兑汇票（张数）
    private long bizAcceptPo = -1; //商业承兑汇票（张数）

    public long getSubLoanTypeID() {
		return SubLoanTypeID;
	}
	public void setSubLoanTypeID(long subLoanTypeID) {
		SubLoanTypeID = subLoanTypeID;
	}
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
     * @return Returns the loanTypeID.
     */
    public long getLoanTypeID()
    {
        return LoanTypeID;
    }
    /**
     * @param loanTypeID The loanTypeID to set.
     */
    public void setLoanTypeID(long loanTypeID)
    {
        LoanTypeID = loanTypeID;
        putUsedField("LoanTypeID", LoanTypeID);
    }
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return Name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        Name = name;
        putUsedField("Name", Name);
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
     * @return
     */
    public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}
	/**
	 * @param officeID
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
		putUsedField("officeID", officeID);
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
	public long getBankAcceptPo() {
		return bankAcceptPo;
	}
	public void setBankAcceptPo(long bankAcceptPo) {
		this.bankAcceptPo = bankAcceptPo;
	}
	public long getBizAcceptPo() {
		return bizAcceptPo;
	}
	public void setBizAcceptPo(long bizAcceptPo) {
		this.bizAcceptPo = bizAcceptPo;
	}
}
