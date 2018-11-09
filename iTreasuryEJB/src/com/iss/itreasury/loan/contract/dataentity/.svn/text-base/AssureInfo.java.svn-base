/*
 * Created on 2003-10-17
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contract.dataentity;

import com.iss.itreasury.util.DataFormat;
import java.util.Vector;
/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssureInfo implements java.io.Serializable
{
	private String sClientCode = ""; //客户编号
	private String sClientName = ""; //单位名称
	private String sAssureTypeName = ""; //担保方式
	private long lAssureType = -1; //担保方式
	private String sContact = ""; //联系人
	private String sPhone = ""; //电话
	private double dAmount = 0; //担保金额
	private double dExamineAmount = 0; //审批金额
	private double dRate = 0; //担保比例(%)
	private long isTopAssure = 2;	//是否为最高额担保

	/**
	 * @return
	 */
	public double getAmount()
	{
		return dAmount;
	}

	/**
	* @return
	*/
	public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(dAmount);
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return dRate;
	}

	/**
	* @return
	*/
	public String getFormatRate()
	{
		return DataFormat.formatDisabledAmount(dRate*100);
	}

	/**
	 * @return
	 */
	public String getClientCode()
	{
		return sClientCode;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return sClientName;
	}

	/**
	 * @return
	 */
	public String getContact()
	{
		return sContact;
	}

	/**
	 * @return
	 */
	public String getPhone()
	{
		return sPhone;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		dAmount = d;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		dRate = d;
	}

	/**
	 * @param l
	 */
	public void setAssureType(long l)
	{
		lAssureType = l;
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		sClientCode = string;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		sClientName = string;
	}

	/**
	 * @param string
	 */
	public void setContact(String string)
	{
		sContact = string;
	}

	/**
	 * @param string
	 */
	public void setPhone(String string)
	{
		sPhone = string;
	}

	/**
	 * @return
	 */
	public String getAssureTypeName()
	{
		return sAssureTypeName;
	}

	/**
	 * @param string
	 */
	public void setAssureTypeName(String string)
	{
		sAssureTypeName = string;
	}

	/**
	 * @return
	 */
	public long getAssureType()
	{
		return lAssureType;
	}

	/**
	 * @return
	 */
	public double getExamineAmount()
	{
		return dExamineAmount;
	}

	/**
	 * @param d
	 */
	public void setExamineAmount(double d)
	{
		dExamineAmount = d;
	}

    /**
     * @return Returns the isTopAssure.
     */
    public long getIsTopAssure()
    {
        return isTopAssure;
    }
    /**
     * @param isTopAssure The isTopAssure to set.
     */
    public void setIsTopAssure(long isTopAssure)
    {
        this.isTopAssure = isTopAssure;
    }
}
