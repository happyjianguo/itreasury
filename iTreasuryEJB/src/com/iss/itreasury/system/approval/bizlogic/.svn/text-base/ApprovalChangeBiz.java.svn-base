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
package com.iss.itreasury.system.approval.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.system.approval.dao.ApprovalChangeDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalChangeInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalChangeBiz extends Object implements java.io.Serializable
{	

	/**
	 * 查询该用户在这些相应模块，操作，业务下的审批级别和权限转移情况
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID             业务类型
	 * @param       lActionID               操作ID
	 * @param       lUserID                 用户ID
	 * @param long           lPageLineCount        每页页行数条件
	 * @param long           lPageNo               第几页条件
	 * @param long           lOrderParam           排序条件，根据此参数决定结果集排序条件
	 * @param long           lDesc                 升序或降序
	 * @return      Collection              返回审批意见信息(ApprovalChangeInfo)
	 */
	public Collection findApprovalItemForChange(long lApprovalID,long lOfficeID, long lCurrencyID,
										  long lUserID,
										  long lPageLineCount,
										  long lPageNo,
										  long lOrderParam,
										  long lDesc
										  ) throws Exception
	{
		Connection con = null;
		ApprovalChangeDao changedao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		//ApprovalChangeInfo ACInfo = null;
		Collection c = null;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//查询审批意见信息
			c = changedao.findApprovalItemForChange(lApprovalID,lOfficeID,lCurrencyID,lUserID,lPageLineCount,lPageNo,lOrderParam,lDesc);
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
			Log.print(
				"ApprovalChangeBiz.findApprovalItemForChange() failed.  Exception is " +
				e.toString());
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

	/**
	 * 查询该用户在该审批操作下的权限转移情况
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lApprovalID             审批设置ID
	 * @param       lUserID                 用户ID
	 * @return      Collection              返回审批信息(ApprovalChangeInfo)
	 */
	public ApprovalChangeInfo findChangeInfoByID(long lApprovalID,long lUserID) throws Exception
	{
		Connection con = null;
		ApprovalChangeDao changedao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		//ApprovalChangeInfo ACInfo = null;
		Collection c = null;
		ApprovalChangeInfo info = new ApprovalChangeInfo();

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//查询审批意见信息
			info = changedao.findChangeInfoByID(lApprovalID,lUserID);
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
			Log.print(
				"ApprovalChangeBiz.findChangeInfoByID() failed.  Exception is " +
				e.toString());
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

		return (info);
	}

	/**
	 * 审批权限转移
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       
	 * @param       lUserID                 用户ID
	 * @param long  lNewUserID              转移的用户ID
	 * @return      long                    返回结果标识  1、成功；-1、失败
	 */
	public long moveCheckRight(long lApprovalID, long lOfficeID, long lCurrencyID, long lUserID, long lNewUserID,Timestamp endDate) throws Exception
	{
		Connection con = null;
		ApprovalChangeDao changedao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		//ApprovalChangeInfo ACInfo = null;
		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//权限转移
			lReturn = changedao.moveCheckRight(lApprovalID,lOfficeID,lCurrencyID,lUserID,lNewUserID,endDate);
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
			Log.print(
				"ApprovalChangeBiz.moveCheckRight() failed.  Exception is " +
				e.toString());
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

		return (lReturn);
	}

	/**
	 * 审批权限取消
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID             业务类型
	 * @param       lActionID               操作ID
	 * @param       lUserID                 用户ID
	 * @return      long                    返回结果标识  1、成功；-1、失败
	 */
	public long returnCheckRight(long lApprovalID, long lOfficeID, long lCurrencyID, long lUserID) throws Exception
	{
		Connection con = null;
		ApprovalChangeDao changedao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		//ApprovalChangeInfo ACInfo = null;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//权限取消
			lReturn = changedao.returnCheckRight(lApprovalID,lOfficeID,lCurrencyID,lUserID);
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
			Log.print(
				"ApprovalChangeBiz.moveCheckRight() failed.  Exception is " +
				e.toString());
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

		return (lReturn);
	}	
}
