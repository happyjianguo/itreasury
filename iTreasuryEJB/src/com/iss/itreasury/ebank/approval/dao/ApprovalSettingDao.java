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
package com.iss.itreasury.ebank.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.ebank.approval.bizlogic.ApprovalSettingBiz;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalQueryInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalSettingDao extends Object implements java.io.Serializable
{

	private Connection m_Conn = null;

	public ApprovalSettingDao()
	{
	}

	public ApprovalSettingDao(Connection con)
	{
		m_Conn = con;
	}

	/**
	 * 新增审批设置信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     审批设置信息
	 * @return      long         成功，返回审批设置标示；失败，返回值=-1
	 */
	public long insertApprovalSetting(ApprovalSettingInfo ASInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long lReturn = -1;
		try
		{
			strSQL = " insert into ob_ApprovalSetting " + 
			" (ID,sName,sDesc,nTotalLevel,nLowLevel,nIsPass,nStatusID,nOfficeID,nCurrencyID,nInputUserID,dtInputDate) " + 
			" values (?,?,?,?,?,?,?,?,?,?,?) ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, ASInfo.getID());
			ps.setString(2,ASInfo.getName());
			ps.setString(3,ASInfo.getDesc());
			ps.setLong(4, ASInfo.getTotalLevel());
			ps.setLong(5, ASInfo.getLowLevel());
			ps.setLong(6, ASInfo.getIsPass());			
			ps.setLong(7, ASInfo.getStatusID());			
			ps.setLong(8, ASInfo.getOfficeID());
			ps.setLong(9, ASInfo.getCurrencyID());
			ps.setLong(10,ASInfo.getInputUserID());
			ps.setTimestamp(11,ASInfo.getInputDate());
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
			Log.print("ApprovalSettingDao.saveApprovalSetting() failed.  Exception is " + e.toString());
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
	 * 保存审批设置信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     审批设置信息
	 * @return      long         成功，返回审批设置标示；失败，返回值=-1
	 */
	public long saveApprovalSetting(ApprovalSettingInfo ASInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long lReturn = -1;
		try
		{
			if (ASInfo.getID() > 0)
			{
			    strSQL = " update ob_ApprovalSetting set ";
			    strSQL += " ID = " + ASInfo.getID();
				if (ASInfo.getTotalLevel() > 0)
				{
				    strSQL += " , nTotalLevel = " + ASInfo.getTotalLevel();
				}
				if (ASInfo.getIsPass() > 0)
				{
				    strSQL += " , nIsPass = " + ASInfo.getIsPass();
				}				
				if (ASInfo.getLowLevel() > 0)
				{
				    strSQL += " , nLowLevel = " + ASInfo.getLowLevel();
				}
				if (ASInfo.getStatusID() > 0)
				{
				    strSQL += " , nStatusID = " + ASInfo.getStatusID();
				}	
				//
				if(ASInfo.getName()!=null && ASInfo.getName().length()>0)
				{
				    strSQL += ", sName = '" + ASInfo.getName() + "' ";
				}
				if(ASInfo.getDesc()!=null && ASInfo.getDesc().length()>0)
				{
				    strSQL += ", sDesc = '" + ASInfo.getDesc() + "' ";
				}				
				//
				strSQL += " where ID = " + ASInfo.getID();
				System.out.println("saveApprovalSetting strSQL=" + strSQL);
				ps = m_Conn.prepareStatement(strSQL);				
				lReturn = ps.executeUpdate();
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
			Log.print("ApprovalSettingDao.saveApprovalSetting() failed.  Exception is " + e.toString());
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
	 * 删除审批设置信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     审批设置信息
	 * @return      long         成功，返回审批设置标示；失败，返回值=-1
	 */
	public long deleteApprovalSetting(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long lReturn = -1;
		try
		{
			if (lApprovalID > 0)
			{
			    strSQL = " update ob_ApprovalSetting set nStatusID = " + Constant.RecordStatus.INVALID + 
						 " where id = " + lApprovalID;
			
				ps = m_Conn.prepareStatement(strSQL);			
				lReturn = ps.executeUpdate();
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
			Log.print("ApprovalSettingDao.deleteApprovalSetting() failed.  Exception is " + e.toString());
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
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID    			       审批设置标示（查询条件）
	 * @return      ApprovalSettingInfo    返回审批设置信息
	 */
	public ApprovalSettingInfo findApprovalSetting(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
		String strSQL = "";
		try
		{
			strSQL = " select * from ob_ApprovalSetting where id = " + lID;
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				ASInfo.setID(rs.getLong("id"));
				ASInfo.setName(rs.getString("sName"));
				ASInfo.setDesc(rs.getString("sDesc"));				
				ASInfo.setTotalLevel(rs.getLong("nTotalLevel"));
				ASInfo.setIsPass(rs.getLong("nIsPass"));				
				ASInfo.setLowLevel(rs.getLong("nLowLevel"));
				ASInfo.setStatusID(rs.getLong("nStatusID"));				
				ASInfo.setOfficeID(rs.getLong("nOfficeID"));
				ASInfo.setCurrencyID(rs.getLong("nCurrencyID"));
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
			Log.print("ApprovalSettingDao.findApprovalSetting() failed.  Exception is " + e.toString());
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
	 * 检查是否存在相同名称的审批流	 
	 * @param       approvalName       审批设置名称（查询条件）
	 * @return      long    存在则返回1; 不存在则返回-1
	 */
	public long checkByApprovalName(long lApprovalID,String approvalName) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String strSQL = "";
		long lResult = -1;
		try
		{
			strSQL = " select * from ob_ApprovalSetting where nStatusID != " + Constant.ApprovalStatus.INVALID +
					" and sName = '" + approvalName + "'";
			if(lApprovalID > 0)
			{
				strSQL += " and id != " + lApprovalID;
			}
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = 1;
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
			Log.print("ApprovalSettingDao.checkByApprovalName() failed.  Exception is " + e.toString());
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

		return lResult;
	}
	//
	public Collection findApprovalSetting(ApprovalQueryInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList c = new ArrayList();
		String strSQL = "";
		try
		{
			strSQL = " select * from ob_ApprovalSetting where 1=1 ";
			if(info.getInputUserID() > 0)
			    strSQL += " and nInputUserId = " + info.getInputUserID();
			if(info.getStartDate() != null)
			    strSQL += " and dtInputDate >= to_date('" + DataFormat.getDateString(info.getStartDate()) + "','yyyy-mm-dd') ";
			if(info.getEndDate() != null)
			    strSQL += " and dtInputDate <= to_date('" + DataFormat.getDateString(info.getEndDate()) + "','yyyy-mm-dd') ";
			if(info.getStatusID() >= 0)
			    strSQL += " and nStatusID = " + info.getStatusID();
			else
				strSQL += " and nStatusID != " + Constant.ApprovalStatus.INVALID;
			strSQL += " order by ID asc ";
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while(rs.next())
			{
			    ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
				ASInfo.setID(rs.getLong("id"));
				ASInfo.setName(rs.getString("sName"));
				ASInfo.setDesc(rs.getString("sDesc"));
				ASInfo.setInputDate(rs.getTimestamp("dtInputDate"));
				ASInfo.setInputUserID(rs.getLong("nInputUserID"));				
				ASInfo.setTotalLevel(rs.getLong("nTotalLevel"));
				ASInfo.setIsPass(rs.getLong("nIsPass"));				
				ASInfo.setLowLevel(rs.getLong("nLowLevel"));
				ASInfo.setStatusID(rs.getLong("nStatusID"));				
				ASInfo.setOfficeID(rs.getLong("nOfficeID"));
				ASInfo.setCurrencyID(rs.getLong("nCurrencyID"));
				
				c.add(ASInfo);
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
			Log.print("ApprovalSettingDao.findApprovalSetting(ApprovalQueryInfo info) failed.  Exception is " + e.toString());
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

		return c;
	}	
	/**
	 * 保存审批用户权限信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo      审批用户权限信息（选择的用户列表等）
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long saveApprovalItem(ApprovalSettingInfo ASInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		ApprovalUserInfo approvalUserInfo = new ApprovalUserInfo();
		try
		{
			//判断当前层次选择了多少个user，并将他们写入数据库
			if (ASInfo.getSelectUser() != null)
			{
				for (int i = 0; i < ASInfo.getSelectUser().size(); i++)
				{
					System.out.println("selectuser is not null!");
					strSQL = " insert into ob_ApprovalItem(napprovalID,nUserID,nLevel) " + " values(?,?,?) ";
					ps = m_Conn.prepareStatement(strSQL);
					ps.setLong(1, ASInfo.getID());
					//ps.setLong(2,Long.parseLong((ASInfo.getSelectUser().elementAt(i)).toString()));
					approvalUserInfo = (ApprovalUserInfo) ASInfo.getSelectUser().elementAt(i);
					ps.setLong(2, approvalUserInfo.getUserID());
					ps.setLong(3, ASInfo.getLevel());
					lReturn = ps.executeUpdate();
					ps.close();
					ps = null;
				}
			}
			else
			{
				lReturn = 1;
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
			Log.print("ApprovalSettingDao.saveApprovalItem() failed.  Exception is " + e.toString());
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
	public ApprovalSettingInfo findApprovalItem(long nApprovalID, long nLevel, long lUserID, long lCurrencyID,long lOfficeID,long lclientID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
		String strSQL = "";

		Vector vTmp = new Vector();
		try
		{
			strSQL = " select a.nUserID,b.sName from ob_ApprovalItem a,ob_user b  where nApprovalID = "+nApprovalID+" and nLevel = "+nLevel+"  and a.nUserID = b.ID  and b.nStatus != 0 and  b.nclientid="+lclientID+" and b.NOFFICEID="+lOfficeID+"";
			ps = m_Conn.prepareStatement(strSQL);
			//ps.setLong(1, nApprovalID);
			//ps.setLong(2, nLevel);
			Log.print(strSQL.toString()+"^^^^^^^^");
			Log.print("findApprovalItem 1---" + strSQL + " lID = " + nApprovalID + " lLevel = " + nLevel);
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
			//将已经选择的人员加入到ASInfo
			ASInfo.setSelectUser(vTmp);

			//请空临时的vector(下级审核人里不包括虚拟用户！！！）
			vTmp = new Vector();
			strSQL =
				" select a.nUserID,b.sName from ob_ApprovalItem a,ob_user b "
					+ " where nApprovalID = ? and nLevel = ? "
					+ " and a.nUserID = b.ID "
					+ " and b.nStatus != 0 "
					+ " and b.NOFFICEID="+lOfficeID+"  and  b.nclientid="+lclientID+""
					+ " and b.nIsVirtualUser != "
					+ Constant.YesOrNo.YES;
			if (lUserID > 0)
			{
			    strSQL += " and a.nUserID != " + lUserID;
			}
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, nApprovalID);
			ps.setLong(2, nLevel);
			Log.print("findApprovalItem 2---" + strSQL + " nApprovalID = " + nApprovalID + " nLevel = " + nLevel);
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

			//清空临时的vector
			vTmp = new Vector();
			//现在查询未选择的人物
			strSQL  = " select id,sname from ob_user where id not in ( "; 
			strSQL += " select nuserid from ob_ApprovalItem where nApprovalID = ? "; 
			//允许各级别重复分配人员，只过滤掉当前级别的已分配的人员
			//否则，过滤掉所有级别的已分配的人员
			/*
			if (lIsReduplicateAssign == 1)//是
			{
			    strSQL += " and nLevel = " + lLevel;
			}
			*/
			strSQL += " ) and nstatus != 0  and NOFFICEID="+lOfficeID+"  and  nclientid="+lclientID+"";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, nApprovalID);
			Log.print("findApprovalItem 3---" + strSQL + " lID = " + nApprovalID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ApprovalUserInfo approvalUserInfo = new ApprovalUserInfo();
				approvalUserInfo.setUserID(rs.getLong("id"));
				approvalUserInfo.setUserName(rs.getString("sname"));
				vTmp.add(approvalUserInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//将未选择的人员加到ASInfo
			ASInfo.setNotSelectUser(vTmp);

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
			Log.print("ApprovalSettingDao.findApprovalItem() failed.  Exception is " + e.toString());
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
	 * 删除审批用户权限
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID       审批设置标示
	 * @param       lTotalLevel       审批级别
	 * @return      long              成功，返回值=1；失败，返回值=-1
	 */
	public long deleteApprovalItem(long lApprovalID, long lTotalLevel) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = 1;
		String strSQL = "";
		try
		{
			strSQL = " delete from ob_ApprovalItem where nApprovalID = ? and nLevel = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
			ps.setLong(2, lTotalLevel);
			ps.execute();
			ps.close();
			ps = null;

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
			Log.print("ApprovalSettingDao.deleteApprovalItem() failed.  Exception is " + e.toString());
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
	 * 删除审批用户权限(该level以上的)
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID       审批设置标示
	 * @param       lTotalLevel       审批级别
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long deleteApprovalItemAboveLevel(long lApprovalID, long lTotalLevel) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = 1;
		String strSQL = "";
		try
		{
			strSQL = " delete from ob_ApprovalItem where nApprovalID = ? and nLevel > ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
			ps.setLong(2, lTotalLevel);
			ps.execute();
			ps.close();
			ps = null;

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
			Log.print("ApprovalSettingDao.deleteApprovalItem() failed.  Exception is " + e.toString());
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
	 * 查询出approvalsetting表的最大id＋1
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审批意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long findApprovalSettingMaxID() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		try
		{
			strSQL = " select nvl(max(id),0)+1 as id from ob_ApprovalSetting ";
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
			Log.print("ApprovalSettingDao.findApprovalSettingMaxID() failed.  Exception is " + e.toString());
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
	 * 检查审批设置是否全部设置完成，如果没有则不允许对此审批设置进行激活
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @return      long                 1：是；2：否
	 */
	public long checkApprovalIntegrality(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = Constant.YesOrNo.NO;
		String strSQL = "";
		long lTmp = 0;

		try
		{
			//先求出该设置里被分配了人员的等级一共有多少个，存入临时变量lTmp
			strSQL = " select count(*) as count from (select distinct(nlevel) from ob_ApprovalItem " + " where nApprovalID = " + lApprovalID + ")";
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lTmp = rs.getLong("count");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			//再求出ApprovalSetting表里的nlevel为多少
			strSQL = " select nTotalLevel from ob_ApprovalSetting where id = " + lApprovalID;
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				if (lTmp == rs.getLong("nTotalLevel"))
				{
					lReturn = Constant.YesOrNo.YES;
				}
				else
				{
					lReturn = Constant.YesOrNo.NO;
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
			Log.print("ApprovalSettingDao.checkApprovalIntegrality() failed.  Exception is " + e.toString());
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
	 * 检查是否正确分配人员，如果没有则不允许对此审批设置进行激活
	 * 检查是否不允许各级别重复分配人员，但存在重复分配的情况，如果有则不允许对此审批设置进行修改保存
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @return      long                 1：是；2：否
	 */
	public long checkApprovalItem(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = Constant.YesOrNo.YES;
		String strSQL = "";
		long lTmp = 0;
		try
		{		    
			//检查，存入临时变量lTmp
				strSQL = " select max(count(nUserID)) from ob_ApprovalItem where nApprovalID = " + lApprovalID + " group by nUserID ";
				ps = m_Conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lTmp = rs.getLong(1);
					if (lTmp > 1)
					{
						lReturn = Constant.YesOrNo.NO;
					}
					else
					{
						lReturn = Constant.YesOrNo.YES;
					}
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			
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
			Log.print("ApprovalSettingDao.checkApprovalIntegrality() failed.  Exception is " + e.toString());
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
	 * 激活审批设置
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示
	 * @return      long                 成功，返回值=1；失败，返回值=-1
	 */
	public long activeApprovalSetting(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";

		try
		{
			strSQL = " update ob_ApprovalSetting set nstatusid = 2 where id = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
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
			Log.print("ApprovalSettingDao.activeApprovalSetting() failed.  Exception is " + e.toString());
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
	 * 审批设置设为设置中
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示
	 * @return      long                 成功，返回值=1；失败，返回值=-1
	 */
	public long changeApprovalSetting(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";

		try
		{
			strSQL = " update ob_ApprovalSetting set nstatusid = 1 where id = ? ";
			ps = m_Conn.prepareStatement(strSQL);
			ps.setLong(1, lApprovalID);
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
			Log.print("ApprovalSettingDao.activeApprovalSetting() failed.  Exception is " + e.toString());
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
	 *  查询是否有未审批完成的业务
	 *    ApprovalSetting,  ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          ?ó?ú?è??±ê???¨?é????????
	 * @return      long                 1??????2??????
	 */
	public long checkApprovalTracing(long lApprovalID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		long lReturn = 2;
		String strSQL = "";
		String strSQL1 = "";

		try
		{ /*
			strSQL =
				" select * from loan_approvaltracing where id in( "
					+ " select max(id) from loan_approvaltracing where nstatusid = "
					+ Constant.RecordStatus.VALID
					+ " group by napprovalid,napprovalcontentid ) "
					+ " and nresultid = "
					+ Constant.ApprovalDecision.PASS
					+ " and napprovalid = ? ";
					//*/
					
			strSQL = " select * from ob_ApprovalRelation where 1 = 1 and ApprovalID = " + lApprovalID;
			System.out.println("======sql= "+strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				//lReturn = 1;
				ApprovalRelationInfo info = new ApprovalRelationInfo();
				info.setApprovalID(rs.getLong("ApprovalID"));
				info.setModuleID(rs.getLong("ModuleID"));
				info.setLoanTypeID(rs.getLong("LoanTypeID")); //贷款类型大类
				info.setSubLoanTypeID(rs.getLong("SubLoanTypeID")); //贷款类型子类ID
				info.setActionID(rs.getLong("ActionID"));
				info.setOfficeID(rs.getLong("OfficeID")); //办事处
				info.setCurrencyID(rs.getLong("CurrencyID")); //币种
				

				strSQL1 = " select * from ob_approvaltracing " +
					" where id in (  " +
					" select max(id) from ob_approvaltracing " +
					" where nstatusid = " + Constant.RecordStatus.VALID +
					" and nModuleID = " + rs.getLong("ModuleID") +
					" and nLoanTypeID = " + rs.getLong("SubLoanTypeID") +
					" and nActionID = " + rs.getLong("ActionID") +
					" and nCurrencyID = " + rs.getLong("CurrencyID") +
					" and nOfficeID = " + rs.getLong("OfficeID") +
					" group by nModuleID,nLoanTypeID ) " +
					" and nresultid = " +Constant.ApprovalDecision.PASS +
					" ";
				System.out.println("======sql 1 = "+strSQL1);
				ps1 = m_Conn.prepareStatement(strSQL1);
				rs1 = ps1.executeQuery();
				while (rs1.next())
				{
					lReturn = 1;
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
			Log.print("ApprovalDao.checkApprovalTracing() failed.  Exception is " + e.toString());
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
	/**检查审批流是否被关联
	 * 
	 * @author weihuang
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public long checkApprovalRelation(long lApprovalID,long officeid)throws Exception
	{   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";
		
	    try
	    {  
	      strSQL="select APPROVALID from ob_approvalrelation where 1=1 and OFFICEID= "+officeid+" and APPROVALID= "+lApprovalID;    
	      System.out.println("======sql = "+strSQL); 
	      ps=m_Conn.prepareStatement(strSQL);
	     
	      rs=ps.executeQuery();
	      
	      
	      
	      while(rs.next()){
	          lReturn=1;
	           
	      }
	    }
	    catch(Exception e){
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
	    }finally{
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
	    
	    return lReturn;
	 }
	public static void main(String[] args){
	    long l=-1;
	    ApprovalSettingInfo info=new ApprovalSettingInfo();
	    ApprovalSettingBiz c=new ApprovalSettingBiz();
	    try {
	    	info=c.findApprovalItem(7,1,1,1,1,1);
            System.out.println("jieguo="+info);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	 }
	
}
