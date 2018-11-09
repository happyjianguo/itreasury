/*
 * Created on 2004-7-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.dao;

import java.sql.Connection;
import com.iss.itreasury.dao.ITreasuryDAO;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrivilegeOfGroupDAO extends ITreasuryDAO
{
	/**
	 * 
	 */
	public PrivilegeOfGroupDAO ( )
	{
		super ( "Sys_privilege_of_group") ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 */
	public PrivilegeOfGroupDAO ( String tableName )
	{
		super ( tableName ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param conn
	 */
	public PrivilegeOfGroupDAO ( Connection conn )
	{
		super ( conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public PrivilegeOfGroupDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param isNeedPrefix
	 */
	public PrivilegeOfGroupDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public PrivilegeOfGroupDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public PrivilegeOfGroupDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
		// TODO Auto-generated constructor stub
	}
}
