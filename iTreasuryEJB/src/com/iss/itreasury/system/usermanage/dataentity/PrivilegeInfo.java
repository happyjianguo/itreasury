/*
 * Created on 2004-7-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.dataentity;

import java.io.Serializable;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrivilegeInfo extends ITreasuryBaseDataEntity implements Serializable
{
	private long Id = -1;	
	private String name = "";
	private long officeID = -1;
	private long moduleID = -1;
	private long level = -1;
	private String menuURL = "";
	private long statusID = -1;
	/**
	 * @return Returns the id.
	 */
	public long getId ( )
	{
		return Id ;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId ( long id )
	{
		Id = id ;
		putUsedField ( "id" , id ) ;
	}
	/**
	 * @return Returns the level.
	 */
	public long getLevel ( )
	{
		return level ;
	}
	/**
	 * @param level The level to set.
	 */
	public void setLevel ( long level )
	{
		this.level = level ;
		putUsedField ( "level" , level ) ;
	}
	/**
	 * @return Returns the menuURL.
	 */
	public String getMenuURL ( )
	{
		return menuURL ;
	}
	/**
	 * @param menuURL The menuURL to set.
	 */
	public void setMenuURL ( String menuURL )
	{
		this.menuURL = menuURL ;
		putUsedField ( "menuURL" , menuURL ) ;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID ( )
	{
		return moduleID ;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID ( long moduleID )
	{
		this.moduleID = moduleID ;
		putUsedField ( "moduleID" , moduleID ) ;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName ( )
	{
		return name ;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName ( String name )
	{
		this.name = name ;
		putUsedField ( "name" , name ) ;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID ( )
	{
		return officeID ;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID ( long officeID )
	{
		this.officeID = officeID ;
		putUsedField ( "officeID" , officeID ) ;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID ( )
	{
		return statusID ;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID ( long statusID )
	{
		this.statusID = statusID ;
		putUsedField ( "statusID" , statusID ) ;
	}
}
