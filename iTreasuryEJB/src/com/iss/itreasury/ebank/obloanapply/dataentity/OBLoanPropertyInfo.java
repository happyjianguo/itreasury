/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;
import com.iss.itreasury.ebank.obdataentity.*;
import java.io.Serializable;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanPropertyInfo implements Serializable
{
	private long        loanID=-1;          //流水号
	private long        loanType=-1;        //贷款种类
	private long        isCircle=0;         //是否循环
	private long        isSaleBuy=-1;       //是否卖方贷款
	private long        isTechnical=0;      //是否技改贷款
	private long        isCredit=0;         //是否信用保证
	private long        isAssure=0;         //是否担保
	private long        isImpawn=0;         //是否质押
	private long        isPledge=0;         //是否抵押
	private long        isRecognizance=0;         //是否保证金
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
	public void setLoanType(long loanType)
	{   
		this.loanType=loanType;
	}
	public long getLoanType()
	{
		return this.loanType;
	}
	/**
	 * 设置是否循环
	 * @param isCircle long 
	 */
	public void setIsCircle(long isCircle)
	{
		this.isCircle=isCircle;   
	}
    
	/**
	 * 获取是否循环
	 * @return long
	 */
	public long getIsCircle()
	{
		return this.isCircle;
	}
    
	/**
	 * 设置是否卖方贷款
	 * @param isSaleBuy long 
	 */
	public void setIsSaleBuy(long isSaleBuy)
	{
		this.isSaleBuy=isSaleBuy;
	}
    
	/**
	 * 获取是否卖方贷款
	 * @return long
	 */
	public long getIsSaleBuy()
	{
		return this.isSaleBuy;
	}
    
	/**
	 * 设置是否技改贷款
	 * @param isTechnical long
	 */
	public void setIsTechnical(long isTechnical)
	{
		this.isTechnical=isTechnical;
	}
    
	/**
	 * 获取是否技改贷款
	 * @return long
	 */
	public long getIsTechnical()
	{
		return this.isTechnical;
	}
    

	/**
	 * 设置是否信用保证
	 * @param isCredit long
	 */
	public void setIsCredit(long isCredit)
	{
		this.isCredit=isCredit;
	}
    
	/**
	 * 获取是否信用保证
	 * @return long
	 */
	public long getIsCredit()
	{
		return this.isCredit;
	}
    
	/**
	 * 设置是否担保
	 * @param isAssure long
	 */
	public void setIsAssure(long isAssure)
	{
		this.isAssure=isAssure;
	}
    
	/**
	 * 获取是否担保
	 * @return long
	 */
	public long getIsAssure()
	{
		return this.isAssure;
	}
    
	/**
	 * 设置是否质押
	 * @param isImpawn long
	 */
	public void setIsImpawn(long isImpawn)
	{
		this.isImpawn=isImpawn;
	}
    
	/**
	 * 获取是否质押
	 * @return long
	 */
	public long getIsImpawn()
	{
		return this.isImpawn;
	}
    
	/**
	 * 设置是否抵押
	 * @param isPledge long
	 */
	public void setIsPledge(long isPledge)
	{
		this.isPledge=isPledge;
	}
    
	/**
	 * 获取是否抵押
	 * @return long
	 */
	public long getIsPledge()
	{
		return this.isPledge;
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
	public long getIsRecognizance() {
		return isRecognizance;
	}
	public void setIsRecognizance(long isRecognizance) {
		this.isRecognizance = isRecognizance;
	}

}
