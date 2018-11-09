/*
 * Created on 2004-3-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author hjliu
 * creation of date 2003-03-10
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CounterPartBankAccountInfo extends SECBaseDataEntity implements Serializable
{

	private long id                = -1; //ID
	private long officeId          = -1; //办事处
	private long currencyId        = -1; //币种
	private long counterpartId     = -1; //交易对手ID
	private long counterpartType   = -1; //交易对手类型
	                                     //1:基金管理公司
	                                     //2:银行间交易对手
	                                     //3:债券分销商
	                                     //4:券商
	                                     //5:被投资企业
	                                     //6:受让方
	                                     //7:发行方
	                                     //8:委托方	                                     //
	                                     //9:投保人
	private long statusId          = -1; //状态 //0=已删除	//	2=已保存//	3=已复核
	private String bankProvince    = ""; //省
	private String bankCity        = ""; //市
	private String bankName        = ""; //开户行行名
	private String bankAccountName = ""; //开户行户名
	private String bankAccountCode = ""; //开户行帐号
	private String remark          = ""; //备注
	
	/**
	 * @return
	 */
	public String getBankAccountCode()
	{
		return bankAccountCode;
	}

	/**
	 * @return
	 */
	public String getBankAccountName()
	{
		return bankAccountName;
	}

	/**
	 * @return
	 */
	public String getBankCity()
	{
		return bankCity;
	}

	/**
	 * @return
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @return
	 */
	public String getBankProvince()
	{
		return bankProvince;
	}

	/**
	 * @return
	 */
	public long getCounterpartId()
	{
		return counterpartId;
	}

	/**
	 * @return
	 */
	public long getCurrencyId()
	{
		return currencyId;
	}

	/**
	 * @return
	 */
	public long getOfficeId()
	{
		return officeId;
	}

	/**
	 * @return
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @param string
	 */
	public void setBankAccountCode(String string)
	{
		bankAccountCode = string;
		putUsedField("bankAccountCode", string);
	}

	/**
	 * @param string
	 */
	public void setBankAccountName(String string)
	{
		bankAccountName = string;
		putUsedField("bankAccountName", string);
	}

	/**
	 * @param string
	 */
	public void setBankCity(String string)
	{
		bankCity = string;
		putUsedField("bankCity", string);
	}

	/**
	 * @param string
	 */
	public void setBankName(String string)
	{
		bankName = string;
		putUsedField("bankName", string);
	}

	/**
	 * @param string
	 */
	public void setBankProvince(String string)
	{
		bankProvince = string;
		putUsedField("bankProvince", string);
	}

	/**
	 * @param l
	 */
	public void setCounterpartId(long l)
	{
		counterpartId = l;
		putUsedField("counterpartID", l);
	}

	/**
	 * @param l
	 */
	public void setCurrencyId(long l)
	{
		currencyId = l;
		putUsedField("currencyID", l);
	}

	/**
	 * @param l
	 */
	public void setOfficeId(long l)
	{
		officeId = l;
		putUsedField("officeID", l);
	}

	/**
	 * @param string
	 */
	public void setRemark(String string)
	{
		remark = string;
		putUsedField("remark", string);
		
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", l);
	}
	

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusID", l);
		
	}

	/**
	 * @return
	 */
	public long getCounterpartType()
	{
		return counterpartType;
	}

	/**
	 * @param l
	 */
	public void setCounterpartType(long l)
	{
		counterpartType = l;
		putUsedField("counterpartType", l);
	}

}
