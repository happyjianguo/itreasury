/*
 * Created on 2004-7-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.dao ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.usermanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class UserDAO extends ITreasuryDAO
{
	/**
	 *  
	 */
	public UserDAO ( )
	{
		super ( "Sys_user" ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 */
	public UserDAO ( String tableName )
	{
		super ( tableName ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param conn
	 */
	public UserDAO ( Connection conn )
	{
		super ( conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public UserDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param isNeedPrefix
	 */
	public UserDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public UserDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public UserDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
		// TODO Auto-generated constructor stub
	}
	public Collection getPrivilegeOfUser ( long lUserID , long lModuleID ) throws ITreasuryDAOException
	{
		Vector v = new Vector ( ) ;
		try
		{
			initDAO ( ) ;
			//
			StringBuffer sb = new StringBuffer ( ) ;
			sb.append ( " select distinct p.id privilegeid,p.officeID officeID,p.name privilegename,p.moduleid moduleid,p.levle level,menuURL" ) ;
			sb.append ( " from Sys_user user u,Sys_group_of_user gou,Sys_Group g,Sys_privilege_of_group pog,Sys_privilege p" ) ;
			sb.append ( " where u.id=gou.userid and gou.groupid=g.id and g.id=pog.groupID and pog.privilegeID=p.id " ) ;
			sb.append ( "  and p.statusid=" + Constant.RecordStatus.VALID + " and p.moduleID=" + lModuleID + " and u.id=" + lUserID ) ;
			sb.append ( " order by p.menuURL" ) ;
			Log.print ( sb.toString ( ) ) ;
			PreparedStatement ps = prepareStatement ( sb.toString ( ) ) ;
			ResultSet rs = ps.executeQuery ( ) ;
			while (rs.next ( ))
			{
				PrivilegeInfo pi = new PrivilegeInfo ( ) ;
				pi.setId ( rs.getLong ( "privilegeid" ) ) ;
				pi.setName ( rs.getString ( "privilegename" ) ) ;
				pi.setLevel ( rs.getLong ( "level" ) ) ;
				pi.setMenuURL ( rs.getString ( "menuURL" ) ) ;
				pi.setModuleID ( rs.getLong ( "moduleid" ) ) ;
				pi.setOfficeID ( rs.getLong ( "officeID" ) ) ;
				v.add ( pi ) ;
			}
			finalizeDAO ( ) ;
		} catch (Exception e)
		{
		    finalizeDAO ( ) ;
			e.printStackTrace ( ) ;
		}
		finally
		{
			finalizeDAO ( ) ; 
		}
		return v.size ( ) > 0 ? v : null ;
	}
}