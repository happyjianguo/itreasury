package com.iss.itreasury.loan.loanapply.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 贷款创建信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: gump
 * @version 1.0
 * @Date: 2003-09-25
 * 有下列字段被认为是创建贷款的信息
 * loanID           long        贷款的申请流水号
 * typeID           long        贷款的种类代码
 * currencyID       long        贷款的货币种类代码
 * officeID         long        办事处代码
 * applyCode        String      申请号
 * consignClientID  long        委托单位代码
 * borrowClientID   long        借款单位代码(客户代码）
 * userID          long         录入人标示
 * sDate           Timestamp    录入时间
 * strPrivLevel     String      录入人权限
 */
public class LoanCreateInfo implements Serializable
{
    private long        loanID=-1;             //流水号
    private long        typeID=-1;             //贷款种类
    private long        subTypeId= -1;         //贷款子类
    private long        currencyID=-1;         //货币代码
    private long        officeID=-1;           //办事处代码
    private String      applyCode="";          //贷款申请号
    private long        consignClientID=-1;    //委托单位代码
    private long        borrowClientID=-1;     //借款单位
    private long        inputUserID=-1;        //录入人代码
    private Timestamp   inputDate=null;        //录入时间   
	private long        sellClientID=-1;       //供应商
	private long        isBuyInto = -1;        //是否买入资产
 
    public long getIsBuyInto() {
		return isBuyInto;
	}
	public void setIsBuyInto(long isBuyInto) {
		this.isBuyInto = isBuyInto;
	}
	/**
     * 构造贷款创建信息。
     */
    public LoanCreateInfo()
    {
        super();
    }
    /**
     * 设置贷款申请的流水号
     * @param loanID long,贷款申请的流水号
     */
    public void setLoanID(long loanID)
    {
         this.loanID=loanID;
    }
    /**
     * 获取贷款申请的流水号
     * @return long 贷款申请的流水号
     */
    public long getLoanID()
    {
        return loanID;
    }
    /**
     * 设置贷款的类型号
     * @param typeID long 贷款的类型代码
     */
    public void setTypeID(long typeID)
    {
        this.typeID=typeID;
    }
    /**
     * 获取贷款的类型代码
     * @return long 贷款的类型代码
     */
    public long getTypeID()
    {
        return typeID;
    }
    
    /**
     * 设置货币代码
     * @param long currencyID 贷款的货币代码
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID=currencyID;
    }
    /**
     * 获得贷款的货币代码
     * @return long 贷款的货币代码
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    
    /**
     * 设置办事处代码
     * @param long officeID 办事处代码
     */
    public void setOfficeID(long officeID)
    {
        this.officeID=officeID;
    }
    /**
     * 获得办事处代码
     * @return long 办事处代码
     */
    public long getOfficeID()
    {
        return officeID;
    }
    
    /**
     * 设置贷款申请号
     * @param String applyCode
     */
    public void setApplyCode(String applyCode)
    {
        this.applyCode=applyCode;
    }
    /**
     * 获得贷款的申请号
     * @return String 贷款的申请号
     */
    public String getApplyCode()
    {
        return this.applyCode;
    }
    
    /**
     * 设置委托单位
     * @param long consignClientID 委托单位代码
     */
    public void setConsignClientID(long consignClientID)
    {
        this.consignClientID=consignClientID;
    }
    /**
     * 获取委托单位
     * @return long 委托单位代码
     */
    public long getConsignClientID()
    {
        return this.consignClientID ;
    }
    
    /**
     * 设置借款单位代码
     * @param long borrowClientID 借款单位代码
     */
    public void setBorrowClientID(long borrowClientID)
    {
        this.borrowClientID=borrowClientID;
    }
    /**
     * 返回借款单位代码
     * @return long 借款单位代码
     */
    public long getBorrowClientID()
    {
        return this.borrowClientID;
    }
    /**
     * 设置录入人代码
     * @param inputUserID long
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID=inputUserID;
    }
    
    /**
     * 获取录入人代码
     * @return long
     */
    public long getInputUserID()
    {
        return this.inputUserID;
    }
    
    /**
     * 设置录入日期
     * @param inputDate Timestamp
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate=inputDate;
    }
    
    /**
     * 获取录入日期
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
