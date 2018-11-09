/**
 * 包含权限管理模块的公共常量
 * @author jinchen
 */
package com.iss.itreasury.system.privilege.util;

import java.util.Collection;

import com.iss.itreasury.system.util.SYSHTML;


public class PrivilegeConstant
{
	 
	public static class UserOrderCondition
	{
		public static final long	USERNAME		= 1 ; 	//用户名
		public static final long	LOGINNAME		= 2 ; 	//登录名
		public static final long	OFFICE			= 3 ; 	//办事处
		public static final long	CURRENCY		= 4 ; 	//外汇
		public static final long	INPUTUSER		= 5 ; 	//录入人
		public static final long	INPUTDATE		= 6 ; 	//录入日期
		
	}
	public static class GroupOrderCondition
	{
		public static final long	GROUPNAME		= 1 ; 	//用户组名
		public static final long	MODULEID		= 2 ; 	//使用模块		
		public static final long	INPUTUSER		= 3 ; 	//录入人
		public static final long	INPUTDATE		= 4 ; 	//录入日期
		
	}
	
	public PrivilegeConstant()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 功能：通过办事处ID查询办事处名称
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static String getOfficeNameByID(long lOfficeID)
	{
		String strReturn = "";
		try
		{
			String strSQL = "select sName from Office where ID=" + lOfficeID;
			Collection c = SYSHTML.getCommonSelectList(strSQL, "sName");
			if (c != null)
			{
				strReturn = (String) c.iterator().next();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return strReturn;
	}
}
