/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 总体功能说明：审批权限设置
 *
 * 使用注意事项：
 * 1
 * 2
 *
 * 作者：樊羿
 *
 * 更改人员：
 *
 */

package com.iss.itreasury.ebank.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dataentity.PeopleInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.system.bizlogic.UserBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 *
 * @author  yfan
 * @update
 */
public class ApprovalDao extends Object implements java.io.Serializable
{

	private Connection m_Conn = null;

	public ApprovalDao()
	{
	}

	public ApprovalDao(Connection con)
	{
		m_Conn = con;
	}

	/**
	 * 查询用户审批级别
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            审批设置标示
	 * @param       lUserID                用户标识
	 * @return      long                   返回用户审批级别
	 */
	public long findApprovalUserLevel(long lApprovalID, long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";

		try
		{
			strSQL = " select nLevel from ob_ApprovalItem where nApprovalID = ? and nUserID = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
			ps.setLong(2, lUserID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalSetting() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 新版本2004.5.16
	 * 查询审批用户权限
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     审批设置标示
	 * @param       lLevel                  审批级别
	 * @param       lUserID                 排除的用户	 
	 * @return      ApprovalSettingInfo     返回审批用户权限信息（已选择和可供选择的用户列表等）
	 */
	public ApprovalSettingInfo findApprovalItem(long lID, long lLevel, long lUserID, long lCurrencyID, long lOfficeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
		String strSQL = "";

		Vector vTmp = new Vector();
		try
		{
			//请空临时的vector(下级审核人里不包括虚拟用户！！！）
			vTmp = new Vector();
			strSQL =
				" select a.nUserID,b.sName from ob_ApprovalItem a,ob_user b "
					+ " where nApprovalID = ? and nLevel = ? "
					+ " and a.nUserID = b.ID "
					+ " and b.nStatus != 0 "
					+ ",%' and b.nOfficeID = " + lOfficeID
					+ " and b.nIsVirtualUser != "
					+ Constant.YesOrNo.YES;
			if (lUserID > 0)
			{
			    strSQL += " and a.nUserID != " + lUserID;
			}
			System.out.println(strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lID);
			ps.setLong(2, lLevel);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ApprovalUserInfo approvalUserInfo = new ApprovalUserInfo();
				approvalUserInfo.setUserID(rs.getLong("nUserID"));
				approvalUserInfo.setUserName(rs.getString("sName"));
				vTmp.add(approvalUserInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			ASInfo.setNextUser(vTmp);
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalItem() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 新版本2004.5.16
	 * 查询审批用户权限(允许越级)
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     审批设置标示
	 * @param       lLevel                  审批级别
	 * @param       lUserID                 排除的用户
	 * @param       lIsReduplicateAssign	是否允许各级别重复分配人员
	 * @param       lTopLevel               最高审批级别（根据“是否走最小审批级别”判断）
	 * @return      ApprovalSettingInfo     返回审批用户权限信息（已选择和可供选择的用户列表等）
	 */
	public ApprovalSettingInfo findApprovalItemAboveLevel(long lID, long lLevel, long lUserID, long lTopLevel, long lCurrencyID, long lOfficeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
		String strSQL = "";

		Vector vTmp = new Vector();
		try
		{
			//请空临时的vector(下级审核人里不包括虚拟用户！！！）
			vTmp = new Vector();
			strSQL = " select distinct a.nUserID,b.sName from ob_ApprovalItem a,ob_user b "
					+ " where nApprovalID = ? and nLevel >= ? "
					+ " and a.nUserID = b.ID "
					+ " and b.nOfficeID = " + lOfficeID  //加入办事处
					+ " and b.nStatus != 0 "
					+ " and b.nIsVirtualUser != "
					+ Constant.YesOrNo.YES
					+ " and nLevel <= " + lTopLevel;
			if (lUserID > 0)
			{
			    strSQL += " and a.nUserID != " + lUserID;
			}
			System.out.println(strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lID);
			ps.setLong(2, lLevel);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ApprovalUserInfo approvalUserInfo = new ApprovalUserInfo();
				approvalUserInfo.setUserID(rs.getLong("nUserID"));
				approvalUserInfo.setUserName(rs.getString("sName"));
				vTmp.add(approvalUserInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			ASInfo.setNextUser(vTmp);
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalItemAboveLevel() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 保存审批意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审批意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long saveApprovalTracing(ApprovalTracingInfo ATInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		long lLevel = -1;
		try
		{
		    lLevel = findApprovalUserLevel(ATInfo.getApprovalID(), ATInfo.getUserID());
		    strSQL =
				" insert into ob_approvaltracing(id,nApprovalContentID,"//nApprovalID
					+ " nSerialID,nUserID,nNextUserID,sOpinion,dtDate,nResultID,"
					+ " nStatusID,nLevel,nModuleID,nLoanTypeID,nActionID,nOfficeID,nCurrencyID) "
					+ " values(?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?) ";
			ps = m_Conn.prepareStatement(strSQL);
			int nIndex =1;
			ps.setLong(nIndex++, ATInfo.getID());
//			ps.setLong(nIndex++, ATInfo.getApprovalID());
			ps.setLong(nIndex++, ATInfo.getApprovalContentID());
			ps.setLong(nIndex++, ATInfo.getSerialID());
			ps.setLong(nIndex++, ATInfo.getUserID());
			ps.setLong(nIndex++, ATInfo.getNextUserID());
			ps.setString(nIndex++, ATInfo.getOpinion());
			//ps.setTimestamp(8, ATInfo.getApprovalDate());
			ps.setLong(nIndex++, ATInfo.getResultID());
			ps.setLong(nIndex++, ATInfo.getStatusID());
			if (ATInfo.getLevel() > 0)
			{
			    ps.setLong(nIndex++, ATInfo.getLevel());
			}
			else
			{
			    ps.setLong(nIndex++, lLevel);
			}
			ps.setLong(nIndex++, ATInfo.getModuleID());
			ps.setLong(nIndex++, ATInfo.getLoanTypeID());
			ps.setLong(nIndex++, ATInfo.getActionID());
			ps.setLong(nIndex++, ATInfo.getOfficeID());
			ps.setLong(nIndex++, ATInfo.getCurrencyID());
			
			lReturn = ps.executeUpdate();
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.saveApprovalTracing() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 查询出approvaltracing表的最大id＋1
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审批意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long findApprovalTracingMaxID() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		try
		{
			strSQL = " select nvl(max(id),0)+1 as id from ob_approvaltracing ";
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = (rs.getLong("id"));
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalTracingMaxID() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 查询出approvaltracing表的最大serialid＋1
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审批意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long findApprovalTracingSerialID(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		try
		{
			strSQL = "select nvl(max(nSerialID)+1,1) as nSerialID from ob_approvaltracing where nModuleID = ? and nLoanTypeID = ? and nActionID = ? and nOfficeID = ? and nCurrencyID = ? and nApprovalContentID = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lModuleID);
			ps.setLong(2, lLoanTypeID);
			ps.setLong(3, lActionID);
			ps.setLong(4, lOfficeID);
			ps.setLong(5, lCurrencyID);
			ps.setLong(6, lApprovalContentID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = (rs.getLong("nSerialID"));
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalTracingSerialID() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 查询审批意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             审批设置标示
	 * @param       lApprovalContentID      审批内容标示
	 * @param       lDesc                   顺序还是倒序
	 * @return      Collection              返回审批意见信息(ApprovalTracingInfo)
	 */
	public Collection findApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lLevel, long lDesc) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c = null;
		Vector vTmp = new Vector();
		String strSQL = "";

		try
		{
			strSQL = " select a.*,b.sName from ob_approvaltracing a,ob_user b where a.nModuleID = ? and a.nLoanTypeID = ? and a.nActionID = ? " 
			       + " and a.nApprovalContentID = ? and a.nOfficeID = ? and a.nCurrencyID = ? and a.nUserID = b.ID ";
			if (lLevel > 0)
			{
				strSQL += " and a.nLevel = " + lLevel;
			}
			strSQL += " order by dtDate";
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_ASC)
			{
				strSQL += " asc ";
			}
			else
			{
				strSQL += " desc ";
			}
//			Log.print("sql="+strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lModuleID);
			ps.setLong(2, lLoanTypeID);
			ps.setLong(3, lActionID);
			ps.setLong(4, lApprovalContentID);
			ps.setLong(5, lOfficeID);
			ps.setLong(6, lCurrencyID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
				ATInfo.setID(rs.getLong("ID"));
				ATInfo.setOfficeID(rs.getLong("nOfficeID"));
				ATInfo.setCurrencyID(rs.getLong("nCurrencyID"));
				ATInfo.setModuleID(rs.getLong("nModuleID"));
				ATInfo.setLoanTypeID(rs.getLong("nLoanTypeID"));
				ATInfo.setActionID(rs.getLong("nActionID"));
				ATInfo.setApprovalContentID(rs.getLong("nApprovalContentID"));
				ATInfo.setSerialID(rs.getLong("nSerialID"));
				ATInfo.setUserID(rs.getLong("nUserID"));
				ATInfo.setUserName(rs.getString("sName"));
				ATInfo.setNextUserID(rs.getLong("nNextUserID"));
				ATInfo.setOpinion(rs.getString("sOpinion"));
				ATInfo.setApprovalDate(rs.getTimestamp("dtDate"));
				ATInfo.setResultID(rs.getLong("nResultID"));
				ATInfo.setStatusID(rs.getLong("nStatusID"));
				ATInfo.setLevel(rs.getLong("nLevel"));
				vTmp.add(ATInfo);
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalTracing() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return vTmp.size() > 0 ? vTmp : null;
	}

	/**
	 * 删除审批意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             审批设置标示
	 * @param       lApprovalContentID      审批内容标示
	 * @param       lType               	标识：1、物理删除，2、逻辑删除
	 * @return      long                    成功，返回值=1；失败，返回值=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = 1;
		String strSQL = "";

		try
		{
			if (lTypeID == 1) //物理删除
			{
				strSQL = " delete from ob_approvaltracing where nModuleID = ? and nLoanTypeID = ? and nActionID = ? and nOfficeID = ? and nCurrencyID = ? and nApprovalContentID = ? ";
				ps = m_Conn.prepareStatement(strSQL);
				ps.setLong(1, lModuleID);
				ps.setLong(2, lLoanTypeID);
				ps.setLong(3, lActionID);
				ps.setLong(4, lOfficeID);
				ps.setLong(5, lCurrencyID);
				ps.setLong(6, lApprovalContentID);
				ps.execute();
			}
			else //逻辑删除
			{
				strSQL = "update ob_approvaltracing set nStatusID = ? where nModuleID = ? and nLoanTypeID = ? and nActionID = ? and nOfficeID = ? and nCurrencyID = ? and nApprovalContentID = ? ";
				ps = m_Conn.prepareStatement(strSQL);
				ps.setLong(1, Constant.RecordStatus.INVALID);
				ps.setLong(2, lModuleID);
				ps.setLong(3, lLoanTypeID);
				ps.setLong(4, lActionID);
				ps.setLong(5, lOfficeID);
				ps.setLong(6, lCurrencyID);
				ps.setLong(7, lApprovalContentID);
				ps.execute();
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.deleteApprovalTracing() failed.  Exception is " + e.toString());
			return (-1);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 更新审批意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             审批设置标示
	 * @param       lApprovalContentID      审批内容标示
	 * @param       lActionID               标识：1、返回修改，审批级别置成-1
	 * @return      long                    成功，返回值=1；失败，返回值=-1
	 */
	public long updateApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";

		try
		{
			if (lTypeID == 1) //返回修改，审批级别置成-1
			{
			    strSQL = "update ob_approvaltracing set nLevel = ? where nModuleID = ? and nLoanTypeID = ? and nActionID = ? and nOfficeID = ? and nCurrencyID = ? and nApprovalContentID = ? ";
				ps = m_Conn.prepareStatement(strSQL);
				ps.setLong(1, -1);
				ps.setLong(2, lModuleID);
				ps.setLong(3, lLoanTypeID);
				ps.setLong(4, lActionID);
				ps.setLong(5, lOfficeID);
				ps.setLong(6, lCurrencyID);
				ps.setLong(7, lApprovalContentID);
				ps.execute();
			}
			else //
			{
			    //
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.updateApprovalTracing() failed.  Exception is " + e.toString());
			return (lReturn);
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
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
	 * 查询审批设置信息
	 * 查询条件重载为根据模块、贷款类型、操作标示查询
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              模块标示
	 * @param       lLoanTypeID            贷款类型标识
	 * @param       lActionID              操作标识
	 * @return      ApprovalSettingInfo    返回审批设置信息
	 */
	public long getApprovalID(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lApprovalID = -1;
		String strSQL = "";
		ApprovalRelationDao approvalRelationDao = new ApprovalRelationDao();
		ApprovalRelationInfo approvalRelationInfo = new ApprovalRelationInfo();
		try
		{
		    approvalRelationInfo.setModuleID(lModuleID);
		    //approvalRelationInfo.setLoanTypeID(lLoanTypeID);
			approvalRelationInfo.setSubLoanTypeID(lLoanTypeID);
		    approvalRelationInfo.setActionID(lActionID);
		    approvalRelationInfo.setOfficeID(lOfficeID);
		    approvalRelationInfo.setCurrencyID(lCurrencyID);
		    lApprovalID = approvalRelationDao.findApprovalID(approvalRelationInfo);
			System.out.println("==================lApprovalID="+lApprovalID);
		}
		
		
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.getApprovalID() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return lApprovalID;
	}

	/**
	 * 查询可以做该审批的实际用户
	 * 操作数据库表
	 * @param       lApprovalID
	 * @param       lUserID                用户标识
	 * @return      long                   返回用户审核级别
	 */
	public String findApprovalChangeUser(long lApprovalID, long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strReturn = "";
		String strTmp = "";
		String strSQL = "";

		try
		{
			//首先判断该用户是真实用户还是虚拟的
			UserBiz ubiz = new UserBiz();
			PeopleInfo pinfo = new PeopleInfo();
			pinfo = ubiz.getUserByID(lUserID);
			//如果是真实用户
			if (pinfo.lIsVirtualUser == Constant.YesOrNo.NO)
			{
				strSQL = " select * from ob_Approvalchangeitem " + " where nUserID = " + lUserID + " and nApprovalID = " + lApprovalID + " and nStatusID = " + Constant.RecordStatus.VALID;
				ps = m_Conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					strReturn = " (0) "; //如果他转移了权限，则看不到该他审批的记录
				}
				else //如果有被别人传过来的权限
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					strSQL = " select nUserID from ob_Approvalchangeitem " + " where nNewUserID = " + lUserID + " and nApprovalID = " + lApprovalID + " and nStatusID = " + Constant.RecordStatus.VALID;
					ps = m_Conn.prepareStatement(strSQL);
					rs = ps.executeQuery();
					while (rs.next())
					{
						//lReturn = 1;//标志，表示被传过
						strTmp += rs.getString("nUserID") + ",";
					}
					strReturn = " ( " + strTmp + lUserID + " ) "; //还要加上自己
				}
			}
			else //如果是虚拟用户
			{
				strSQL = " select nUserID from ob_Approvalchangeitem " + " where nNewUserID = " + lUserID + " and nApprovalID = " + lApprovalID + " and nStatusID = " + Constant.RecordStatus.VALID;
				ps = m_Conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs.next())
				{
					//lReturn = 1;//标志，表示被传过
					strTmp += rs.getString("nUserID") + ",";
				}
				strReturn = " ( " + strTmp + lUserID + " ) "; //还要加上自己
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalChangeUser() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (strReturn);
	}
	
	/**
	 * 查询用户是否在指定审批级别中
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            审批设置标示
	 * @param       lUserID                用户标识
	 * @param       lLevel                 用户标识
	 * @return      boolean                
	 */
	public boolean checkApprovalUserLevel(long lApprovalID, long lUserID, long lLevel) throws Exception
	{
	    Connection conn = null;
	    if (m_Conn == null)
	        conn = Database.getConnection();
	    else
	        conn = m_Conn;
	    
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		String strSQL = "";

		try
		{
			strSQL = " select count(*) from ob_ApprovalItem where nApprovalID = ? and nUserID = ? and nLevel = ? ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
			ps.setLong(2, lUserID);
			ps.setLong(3, lLevel);
			rs = ps.executeQuery();
			if (rs.next())
			{
				if (rs.getLong(1) > 0)
				{
				    bReturn = true;
				}
			}
		}
		catch (Exception e)
		{
		    if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			Log.print("ApprovalDao.findApprovalSetting() failed.  Exception is " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (m_Conn == null)
				{
				    conn.close();
				    conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (bReturn);
	}

	public static void main(String[] args)
	{
		try
		{
			ApprovalDao dao = new ApprovalDao();
			long l = dao.getApprovalID(2,7,1,1,1);
			System.out.println("appid"+l);
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
	}

}
