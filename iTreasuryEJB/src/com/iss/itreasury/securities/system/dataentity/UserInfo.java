/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.system.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserInfo extends SECBaseDataEntity 
{
	long id 				= -1;		//ID
	String sName 			= "";		//用户名称
	String sLoginNo 		= "";		//登录帐户
	String sPassword 		= "";		//密码
	long nOfficeId 			= -1;		//办事处ID
	long nUserGroupId 		= -1;		//用户组ID
	long nCurrencyId 		= -1;		//币种
	long nInputUserId 		=-1;		//录入人
	Timestamp dtInput 		= null;		//录入时间
	long nStatusId 			= -1;		//状态
	String sPrivLevel 		= "";		//权限级别
	long nCloseSystem 		= -1;		//能否关机
	long nIsAdminUser 		= -1;		//是否管理员
	long nIsVirtualUser 	= -1;		//是否虚拟用户

	/**
	 * @return Returns the dtInput.
	 */
	public Timestamp getDtInput()
	{
		return dtInput;
	}

	/**
	 * @param dtInput The dtInput to set.
	 */
	public void setDtInput(Timestamp dtInput)
	{
		this.dtInput = dtInput;
		putUsedField("dtInput", dtInput);
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
		putUsedField("id", id);
	}

	/**
	 * @return Returns the nCloseSystem.
	 */
	public long getNCloseSystem()
	{
		return nCloseSystem;
	}

	/**
	 * @param closeSystem The nCloseSystem to set.
	 */
	public void setNCloseSystem(long closeSystem)
	{
		nCloseSystem = closeSystem;
		putUsedField("closeSystem", closeSystem);
	}

	/**
	 * @return Returns the nCurrencyId.
	 */
	public long getNCurrencyId()
	{
		return nCurrencyId;
	}

	/**
	 * @param currencyId The nCurrencyId to set.
	 */
	public void setNCurrencyId(long currencyId)
	{
		nCurrencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	/**
	 * @return Returns the nInputUserId.
	 */
	public long getNInputUserId()
	{
		return nInputUserId;
	}

	/**
	 * @param inputUserId The nInputUserId to set.
	 */
	public void setNInputUserId(long inputUserId)
	{
		nInputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}

	/**
	 * @return Returns the nIsAdminUser.
	 */
	public long getNIsAdminUser()
	{
		return nIsAdminUser;
	}

	/**
	 * @param isAdminUser The nIsAdminUser to set.
	 */
	public void setNIsAdminUser(long isAdminUser)
	{
		nIsAdminUser = isAdminUser;
		putUsedField("isAdminUser", isAdminUser);
	}

	/**
	 * @return Returns the nIsVirtualUser.
	 */
	public long getNIsVirtualUser()
	{
		return nIsVirtualUser;
	}

	/**
	 * @param isVirtualUser The nIsVirtualUser to set.
	 */
	public void setNIsVirtualUser(long isVirtualUser)
	{
		nIsVirtualUser = isVirtualUser;
		putUsedField("isVirtualUser", isVirtualUser);
	}

	/**
	 * @return Returns the nOfficeId.
	 */
	public long getNOfficeId()
	{
		return nOfficeId;
	}

	/**
	 * @param officeId The nOfficeId to set.
	 */
	public void setNOfficeId(long officeId)
	{
		nOfficeId = officeId;
		putUsedField("officeId", officeId);
	}

	/**
	 * @return Returns the nStatusId.
	 */
	public long getNStatusId()
	{
		return nStatusId;
	}

	/**
	 * @param statusId The nStatusId to set.
	 */
	public void setNStatusId(long statusId)
	{
		nStatusId = statusId;
		putUsedField("statusId", statusId);
	}

	/**
	 * @return Returns the nUserGroupId.
	 */
	public long getNUserGroupId()
	{
		return nUserGroupId;
	}

	/**
	 * @param userGroupId The nUserGroupId to set.
	 */
	public void setNUserGroupId(long userGroupId)
	{
		nUserGroupId = userGroupId;
		putUsedField("userGroupId", userGroupId);
	}

	/**
	 * @return Returns the sLoginNo.
	 */
	public String getSLoginNo()
	{
		return sLoginNo;
	}

	/**
	 * @param loginNo The sLoginNo to set.
	 */
	public void setSLoginNo(String loginNo)
	{
		sLoginNo = loginNo;
		putUsedField("loginNo", loginNo);
	}

	/**
	 * @return Returns the sName.
	 */
	public String getSName()
	{
		return sName;
	}

	/**
	 * @param name The sName to set.
	 */
	public void setSName(String name)
	{
		sName = name;
		putUsedField("name", name);
	}

	/**
	 * @return Returns the sPassword.
	 */
	public String getSPassword()
	{
		return sPassword;
	}

	/**
	 * @param password The sPassword to set.
	 */
	public void setSPassword(String password)
	{
		sPassword = password;
		putUsedField("password", password);
	}

	/**
	 * @return Returns the sPrivLevel.
	 */
	public String getSPrivLevel()
	{
		return sPrivLevel;
	}

	/**
	 * @param privLevel The sPrivLevel to set.
	 */
	public void setSPrivLevel(String privLevel)
	{
		sPrivLevel = privLevel;
		putUsedField("privLevel", privLevel);
	}

}
