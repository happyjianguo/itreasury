/*
 * Created on 2004-7-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.user.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserInfo extends ITreasuryBaseDataEntity
{
	private long Id = -1;
	private String sName = "";
	private String sLoginno = "";
	private String sPassword = "";
	private long nOfficeID = -1;
	private long nCurrencyID = -1;
	private long nUserGroupID = -1;
	private long nInputUserID = -1;
	private Timestamp dtInput = null;
	private long nStatusID = -1;
	private String sPrivLevel = "";
	private long nCloseSystem = -1;
	private long nIsAdminUser = -1;
	private long nIsVirtualUser = -1;
	private long nDepartmentID = -1;

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId ( )
	{
		// TODO Auto-generated method stub
		return Id ;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId ( long id )
	{
		// TODO Auto-generated method stub
		this.Id = id;
		putUsedField ( "Id" , id ) ;		
	}
	/**
	 * @return Returns the input.
	 */
	public Timestamp getInput ( )
	{
		return dtInput ;
	}
	/**
	 * @param input The input to set.
	 */
	public void setInput ( Timestamp input )
	{
		dtInput = input ;
		putUsedField ( "dtinput" , input ) ;
	}
	/**
	 * @return Returns the closeSystem.
	 */
	public long getCloseSystem ( )
	{
		return nCloseSystem ;
	}
	/**
	 * @param closeSystem The closeSystem to set.
	 */
	public void setCloseSystem ( long closeSystem )
	{
		nCloseSystem = closeSystem ;
		putUsedField ( "ncloseSystem" , closeSystem ) ;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID ( )
	{
		return nCurrencyID ;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID ( long currencyID )
	{
		nCurrencyID = currencyID ;
		putUsedField ( "ncurrencyID" , currencyID ) ;
	}
	/**
	 * @return Returns the departmentID.
	 */
	public long getDepartmentID ( )
	{
		return nDepartmentID ;
	}
	/**
	 * @param departmentID The departmentID to set.
	 */
	public void setDepartmentID ( long departmentID )
	{
		nDepartmentID = departmentID ;
		putUsedField ( "ndepartmentID" , departmentID ) ;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID ( )
	{
		return nInputUserID ;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID ( long inputUserID )
	{
		nInputUserID = inputUserID ;
		putUsedField ( "ninputUserID" , inputUserID ) ;
	}
	/**
	 * @return Returns the isAdminUser.
	 */
	public long getIsAdminUser ( )
	{
		return nIsAdminUser ;
	}
	/**
	 * @param isAdminUser The isAdminUser to set.
	 */
	public void setIsAdminUser ( long isAdminUser )
	{
		nIsAdminUser = isAdminUser ;
		putUsedField ( "nisAdminUser" , isAdminUser ) ;
	}
	/**
	 * @return Returns the isVirtualUser.
	 */
	public long getIsVirtualUser ( )
	{
		return nIsVirtualUser ;
	}
	/**
	 * @param isVirtualUser The isVirtualUser to set.
	 */
	public void setIsVirtualUser ( long isVirtualUser )
	{
		nIsVirtualUser = isVirtualUser ;
		putUsedField ( "nisVirtualUser" , isVirtualUser ) ;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID ( )
	{
		return nOfficeID ;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID ( long officeID )
	{
		nOfficeID = officeID ;
		putUsedField ( "nofficeID" , officeID ) ;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID ( )
	{
		return nStatusID ;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID ( long statusID )
	{
		nStatusID = statusID ;
		putUsedField ( "nstatusID" , statusID ) ;
	}
	/**
	 * @return Returns the userGroupID.
	 */
	public long getUserGroupID ( )
	{
		return nUserGroupID ;
	}
	/**
	 * @param userGroupID The userGroupID to set.
	 */
	public void setUserGroupID ( long userGroupID )
	{
		nUserGroupID = userGroupID ;
		putUsedField ( "nuserGroupID" , userGroupID ) ;
	}
	/**
	 * @return Returns the loginno.
	 */
	public String getLoginno ( )
	{
		return sLoginno ;
	}
	/**
	 * @param loginno The loginno to set.
	 */
	public void setLoginno ( String loginno )
	{
		sLoginno = loginno ;
		putUsedField ( "sloginno" , loginno ) ;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName ( )
	{
		return sName ;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName ( String name )
	{
		sName = name ;
		putUsedField ( "sname" , name ) ;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword ( )
	{
		return sPassword ;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword ( String password )
	{
		sPassword = password ;
		putUsedField ( "spassword" , password ) ;
	}
	/**
	 * @return Returns the privLevel.
	 */
	public String getPrivLevel ( )
	{
		return sPrivLevel ;
	}
	/**
	 * @param privLevel The privLevel to set.
	 */
	public void setPrivLevel ( String privLevel )
	{
		sPrivLevel = privLevel ;
		putUsedField ( "sprivLevel" , privLevel ) ;
	}
}
