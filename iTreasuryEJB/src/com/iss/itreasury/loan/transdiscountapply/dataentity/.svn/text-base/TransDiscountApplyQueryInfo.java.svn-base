/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.transdiscountapply.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyQueryInfo extends LoanBaseDataEntity 
{

    private long id = -1;
    //
    private long currencyId = -1;//
    private long officeId = -1;//
    private long userId = -1;//
    private String strUser = "";//
    
    private long queryPurpose = -1;//查找类型
    private long pageLineCount = LOANConstant.PageControl.CODE_PAGELINECOUNT;//每页记录数目
    private long pageCount = 0;//总页数
    private long pageNo = 1;//当前页码
    private long orderParam = -1;//排序值
    private String orderParamString = "";//排序字符
    private long desc = -1;//正反排序
    private long firstSearch = -1;//是否显示查找结果

    private long startTransDiscountApplyId = -1;//
    private String startTransDiscountApplyCode = "";//
    private long endTransDiscountApplyId = -1;//
    private String endTransDiscountApplyCode = "";//
    private long startDiscountContractId = -1;//
    private String startDiscountContractCode = "";//
    private long endDiscountContractId = -1;//
    private String endDiscountContractCode = "";//
    private long startTransDiscountContractId = -1;//
    private String startTransDiscountContractCode = "";//
    private long endTransDiscountContractId = -1;//
    private String endTransDiscountContractCode = "";//

    private long billId = -1;//
    private String code = "";//
    private double startAmount = 0;//
    private double endAmount = 0;//

    private long clientId = -1;//
    private String clientCode = "";//
    private String clientName = "";//
    private long queryStatusId = -1;//
    private Timestamp queryStartDate = null;//
    private Timestamp queryEndDate = null;//
    private Timestamp repurchaseDate = null;//
    
    //计算票据利息
    private long transDiscountApplyId = -1;//贴现申请id
    private double transDiscountRate = 0;//贴现利息
    private Timestamp countDiscountDate = null;//贴现日
    private long transDiscountCountType = -1;//计息方式
    private long inOrOut = -1;//
    private long discountTypeId = -1;
    private long readOnly = -1;
    private long billStatus = -1;
    // dandelion 于2007－01－29日为给转贴现申请选择票据查询添加"汇票类型"查询条件
    private long acceptPOTypeID=-1;//汇票类型
    public long getAcceptPOTypeID() {
		return acceptPOTypeID;
	}
	public void setAcceptPOTypeID(long acceptPOTypeID) {
		this.acceptPOTypeID = acceptPOTypeID;
	}
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }
    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getClientId()
    {
        return clientId;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getCode()
    {
        return code;
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
     * return double
     */
    public double getEndAmount()
    {
        return endAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getQueryEndDate()
    {
        return queryEndDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getEndDiscountContractId()
    {
        return endDiscountContractId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getEndTransDiscountContractId()
    {
        return endTransDiscountContractId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getFirstSearch()
    {
        return firstSearch;
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
    public long getPageCount()
    {
        return pageCount;
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
     * return long
     */
    public long getQueryPurpose()
    {
        return queryPurpose;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStartAmount()
    {
        return startAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getQueryStartDate()
    {
        return queryStartDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStartDiscountContractId()
    {
        return startDiscountContractId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStartTransDiscountContractId()
    {
        return startTransDiscountContractId;
    }


    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientId(long l)
    {
        clientId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCode(String string)
    {
        code = string;
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
    public void setEndAmount(double d)
    {
        endAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setQueryEndDate(Timestamp timestamp)
    {
        queryEndDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndDiscountContractId(long l)
    {
        endDiscountContractId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndTransDiscountContractId(long l)
    {
        endTransDiscountContractId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setFirstSearch(long l)
    {
        firstSearch = l;
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
    public void setPageCount(long l)
    {
        pageCount = l;
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

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setQueryPurpose(long l)
    {
        queryPurpose = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartAmount(double d)
    {
        startAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setQueryStartDate(Timestamp timestamp)
    {
        queryStartDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartDiscountContractId(long l)
    {
        startDiscountContractId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartTransDiscountContractId(long l)
    {
        startTransDiscountContractId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyId()
    {
        return currencyId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeId()
    {
        return officeId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyId(long l)
    {
        currencyId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeId(long l)
    {
        officeId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUserId(long l)
    {
        userId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getUser()
    {
        return strUser;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUser(String string)
    {
        strUser = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getEndTransDiscountApplyId()
    {
        return endTransDiscountApplyId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStartTransDiscountApplyId()
    {
        return startTransDiscountApplyId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndTransDiscountApplyId(long l)
    {
        endTransDiscountApplyId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartTransDiscountApplyId(long l)
    {
        startTransDiscountApplyId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBillId()
    {
        return billId;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getEndDiscountContractCode()
    {
        return endDiscountContractCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getEndTransDiscountContractCode()
    {
        return endTransDiscountContractCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getStartDiscountContractCode()
    {
        return startDiscountContractCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getStartTransDiscountApplyCode()
    {
        return startTransDiscountApplyCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getStartTransDiscountContractCode()
    {
        return startTransDiscountContractCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBillId(long l)
    {
        billId = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientName(String string)
    {
        clientName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndDiscountContractCode(String string)
    {
        endDiscountContractCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndTransDiscountContractCode(String string)
    {
        endTransDiscountContractCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartDiscountContractCode(String string)
    {
        startDiscountContractCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartTransDiscountApplyCode(String string)
    {
        startTransDiscountApplyCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartTransDiscountContractCode(String string)
    {
        startTransDiscountContractCode = string;
    }

    /**
     * @param 
     * return String
     */
    public String getClientCode()
    {
        return clientCode;
    }

    /**
     * @param 
     * return String
     */
    public String getEndTransDiscountApplyCode()
    {
        return endTransDiscountApplyCode;
    }

    /**
     * @param 
     * return void
     */
    public void setClientCode(String string)
    {
        clientCode = string;
    }

    /**
     * @param 
     * return void
     */
    public void setEndTransDiscountApplyCode(String string)
    {
        endTransDiscountApplyCode = string;
    }

    /**
     * @param 
     * return Timestamp
     */
    public Timestamp getCountDiscountDate()
    {
        return countDiscountDate;
    }

    /**
     * @param 
     * return long
     */
    public long getTransDiscountApplyId()
    {
        return transDiscountApplyId;
    }

    /**
     * @param 
     * return double
     */
    public double getTransDiscountRate()
    {
        return transDiscountRate;
    }

    /**
     * @param 
     * return void
     */
    public void setCountDiscountDate(Timestamp timestamp)
    {
        countDiscountDate = timestamp;
    }

    /**
     * @param 
     * return void
     */
    public void setTransDiscountApplyId(long l)
    {
        transDiscountApplyId = l;
    }

    /**
     * @param 
     * return void
     */
    public void setTransDiscountRate(double d)
    {
        transDiscountRate = d;
    }

    /**
     * @param 
     * return long
     */
    public long getTransDiscountCountType()
    {
        return transDiscountCountType;
    }

    /**
     * @param 
     * return void
     */
    public void setTransDiscountCountType(long l)
    {
        transDiscountCountType = l;
    }

    /**
     * @param 
     * return long
     */
    public long getDiscountTypeId()
    {
        return discountTypeId;
    }

    /**
     * @param 
     * return long
     */
    public long getInOrOut()
    {
        return inOrOut;
    }

    /**
     * @param 
     * return void
     */
    public void setDiscountTypeId(long l)
    {
        discountTypeId = l;
    }

    /**
     * @param 
     * return void
     */
    public void setInOrOut(long l)
    {
        inOrOut = l;
    }

    /**
     * @param 
     * return long
     */
    public long getQueryStatusId()
    {
        return queryStatusId;
    }

    /**
     * @param 
     * return void
     */
    public void setQueryStatusId(long l)
    {
        queryStatusId = l;
    }

    /**
     * @param 
     * return long
     */
    public long getReadOnly()
    {
        return readOnly;
    }

    /**
     * @param 
     * return void
     */
    public void setReadOnly(long l)
    {
        readOnly = l;
    }
	/**
	 * @return the billStatus
	 */
	public long getBillStatus() {
		return billStatus;
	}
	/**
	 * @param billStatus the billStatus to set
	 */
	public void setBillStatus(long billStatus) {
		this.billStatus = billStatus;
	}
	public Timestamp getRepurchaseDate() {
		return repurchaseDate;
	}
	public void setRepurchaseDate(Timestamp repurchaseDate) {
		this.repurchaseDate = repurchaseDate;
	}

}
