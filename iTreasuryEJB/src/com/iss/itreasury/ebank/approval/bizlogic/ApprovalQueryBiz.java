/*
 * Created on 2005-5-12
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.ebank.approval.bizlogic;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.ebank.approval.dao.ApprovalQueryDao;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalQueryBiz extends Object implements java.io.Serializable
{
	/**
	 * 查询审批设置信息
	 * 查询条件为审批设置标示，也可重载为根据模块、贷款类型、操作标示查询
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID    			       审批设置标示（查询条件）
	 * @return      ApprovalSettingInfo    返回审批设置信息
	 */
	public ApprovalSettingInfo findApprovalSetting() throws	Exception
	{
		Connection con = null;
		ApprovalQueryDao approvalquerydao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvalquerydao = new ApprovalQueryDao(con);

			//查询审批设置信息
			ASInfo = approvalquerydao.findApprovalSetting();

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println("ApprovalQueryBiz.findApprovalSetting() failed.  Exception is " +	e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (ASInfo);
	}
	
	/**
	 * 查询审批用户权限
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     审批设置标示
	 * @param       lLevel                  审批级别
	 * @param       lUserID                 排除的用户
	 * @param       lIsReduplicateAssign	是否允许各级别重复分配人员
	 * @return      ApprovalSettingInfo     返回审批用户权限信息（已选择和可供选择的用户列表等）
	 */
	public Collection findApprovalItem() throws	Exception
	{
		Connection con = null;
		ApprovalQueryDao approvalquerydao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		Collection c = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvalquerydao = new ApprovalQueryDao(con);

			//查询审批设置信息
			c = approvalquerydao.findApprovalItem();

			con.close();
			con = null;
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println("ApprovalQueryBiz.findApprovalItem() failed.  Exception is " +	e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (c);
	}
    
	public static void main(String[] args)
	{
		try
		{
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
	}
}
