/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.ebank.obdataentity.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanCreateInfo implements Serializable
{

	private long        loanID=-1;             //流水号
	private long        typeID=-1;             //贷款种类
	private String      applyCode="";          //贷款申请号
	private long        consignClientID=-1;    //委托单位代码
	private long        borrowClientID=-1;     //借款单位
	private Timestamp   inputDate=null;        //录入时间
	private OBSecurityInfo securityInfo=null;			//安全信息   
 

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


	/**
	 * @return
	 */
	public OBSecurityInfo getSecurityInfo()
	{
		return securityInfo;
	}

	/**
	 * @param info
	 */
	public void setSecurityInfo(OBSecurityInfo info)
	{
		securityInfo = info;
	}

}
