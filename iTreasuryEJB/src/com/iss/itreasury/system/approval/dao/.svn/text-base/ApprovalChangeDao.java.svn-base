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
package com.iss.itreasury.system.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.system.approval.dataentity.ApprovalChangeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalChangeDao extends Object implements java.io.Serializable
{

	private Connection m_Conn = null;

	public ApprovalChangeDao()
	{
	}

	public ApprovalChangeDao(Connection con)
	{
		m_Conn = con;
	}

	/**
	 * 查询该用户在这些相应模块，操作，业务下的审批级别和权限转移情况
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID             业务类型
	 * @param       lActionID               操作ID
	 * @param       lUserID                 用户ID （必输）
	 * @param long           lPageLineCount        每页页行数条件
	 * @param long           lPageNo               第几页条件
	 * @param long           lOrderParam           排序条件，根据此参数决定结果集排序条件
	 * @param long           lDesc                 升序或降序
	 * @return      Collection              返回审批信息(ApprovalChangeInfo)
	 */

	public Collection findApprovalItemForChange(long lApprovalID, long lOfficeID, long lCurrencyID, long lUserID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c = null;
		Vector vTmp = new Vector();
		String strSQL = "";
		String strSelect = "";
		String strOrder = "";
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long lSerialID = 1; //序号

		try
		{
			//strSQL = " select a.* ,c.nnewuserid "
			strSelect = "select count(*) ";
			strSQL = " from loan_ApprovalItem aa, loan_ApprovalSetting bb, loan_Approvalchangeitem cc, userInfo dd, userInfo ee where aa.nApprovalID = bb.ID ";
			strSQL += " and bb.nOfficeID =" + lOfficeID;
			strSQL += " and bb.nCurrencyID =" + lCurrencyID;
			if (lUserID > 0)
			{
				strSQL += " and aa.nUserID =" + lUserID;
				strSQL += " and cc.nUserID(+) = " + lUserID;
			}
			if (lApprovalID > 0)
			{
				strSQL += " and bb.ID= " + lApprovalID;
			}			
			strSQL += " and bb.nStatusID= "
				+ Constant.ApprovalStatus.SUBMIT
				+ " and cc.nApprovalID(+)= bb.ID "
				+ " and cc.nStatusID(+) = "
				+ Constant.RecordStatus.VALID
				+ " and aa.nUserID = dd.ID "
				+ " and cc.nNewUserID = ee.ID(+) "
				+ " and aa.nLevel > 1 ";				
			ps = m_Conn.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
			{
				case 1 :
					strSQL += " order by bb.ID ";
					break;
				case 2 :
					//strSQL += " order by bb.nLoanTypeID ";
					break;
				case 3 :
					//strSQL += " order by bb.nActionID ";
					break;
				case 4 :
					strSQL += " order by aa.nLevel ";
					break;
				case 5 :
					strSQL += " order by aa.nUserID ";
					break;
				case 6 :
					strSQL += " order by cc.nNewUserID ";
					break;
				case 7 :
					strSQL += " order by cc.dtInputDate ";
					break;
				default :
					strSQL += " order by cc.dtInputDate ";
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strSQL = " select aa.*,bb.ID,bb.sName,cc.nNewUserID,cc.endDate,cc.dtInputDate,dd.sname as sUserName,ee.sname as sNewUserName  " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			System.out.println("sql2=" + strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{

				ApprovalChangeInfo ACInfo = new ApprovalChangeInfo();
				ACInfo.setName(rs.getString("sName"));
				//ACInfo.setDesc(rs.getString("sDesc"));	
				ACInfo.setNewUserName(rs.getString("sNewUserName"));
				ACInfo.setUserName(rs.getString("sUserName"));
				ACInfo.setDate(rs.getTimestamp("dtInputDate"));
				ACInfo.setLevel(rs.getLong("nLevel"));
				ACInfo.setEndDate(rs.getTimestamp("endDate"));
				ACInfo.setApprovalID(rs.getLong("nApprovalID"));
				ACInfo.setRecordCount(lRecordCount); //总条数
				ACInfo.setSerialID(lRowNumStart++); //序号
				vTmp.add(ACInfo);

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
			Log.print("ApprovalDao.findApprovalItemForChange() failed.  Exception is " + e.toString());
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
	 * 查询该用户在这些相应模块，操作，业务下的审批级别和权限转移情况
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lApprovalID             审批设置ID
	 * @param       lUserID                 用户ID
	 * @return      Collection              返回审批信息(ApprovalChangeInfo)
	 */

	public ApprovalChangeInfo findChangeInfoByID(long lApprovalID, long lUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c = null;
		Vector vTmp = new Vector();
		String strSQL = "";
		String strSelect = "";
		ApprovalChangeInfo ACInfo = new ApprovalChangeInfo();

		try
		{
			strSQL =
				" select aa.nLevel,bb.*,cc.nUserID,cc.endDate,cc.nNewUserID,cc.dtInputDate,dd.sname as sUserName,ee.sname as sNewUserName "
					+ " from loan_approvalitem aa ,loan_approvalsetting bb,loan_Approvalchangeitem cc ,userinfo dd,userinfo ee "
					+ " where aa.napprovalid = bb.id ";
			if (lUserID > 0)
			{
				strSQL += " and aa.nuserid = " + lUserID + " and cc.nUserid(+) = " + lUserID;
			}
			if (lApprovalID > 0)
			{
				strSQL += " and bb.id = " + lApprovalID;
			}
			strSQL += " and bb.nstatusid= "
				+ Constant.ApprovalStatus.SUBMIT
				+ " and cc.napprovalid(+)= bb.id "
				+ " and cc.nstatusid(+) = "
				+ Constant.RecordStatus.VALID
				+ " and aa.nuserid = dd.id "
				+ " and cc.nNewUserID = ee.id(+) ";

			System.out.println("strSQL=" + strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{				
				ACInfo.setNewUserName(rs.getString("sNewUserName"));
				ACInfo.setUserName(rs.getString("sUserName"));
				ACInfo.setName(rs.getString("sName"));
				ACInfo.setUserID(rs.getLong("nUserID"));
				ACInfo.setNewUserID(rs.getLong("nNewUserID"));
				ACInfo.setDate(rs.getTimestamp("dtInputDate"));
				ACInfo.setLevel(rs.getLong("nLevel"));
				ACInfo.setIsPass(rs.getLong("nIsPass"));
				ACInfo.setEndDate(rs.getTimestamp("endDate"));
				ACInfo.setStatusID(rs.getLong("nStatusID"));
				ACInfo.setTotalLevel(rs.getLong("nTotalLevel"));
				ACInfo.setApprovalID(rs.getLong("ID"));
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
			Log.print("ApprovalDao.findApprovalItemForChange() failed.  Exception is " + e.toString());
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
		return ACInfo;
	}

	/**
	 * 审批权限转移
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID             业务类型
	 * @param       lActionID               操作ID
	 * @param       lUserID                 用户ID
	 * @param long  lNewUserID              转移的用户ID
	 * @return      long                    返回结果标识  1、成功；-1、失败
	 */

	public long moveCheckRight(long lApprovalID, long lOfficeID, long lCurrencyID, long lUserID, long lNewUserID,Timestamp endDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c = null;
		Vector vTmp = new Vector();
		String strSQL = "";
		String strSelect = "";
		long lReturn = 1;
		long lLevel = 0; //审批级别
		long lTempApprovalID = -1; //审批设置id

		try
		{
			//查出该用户在审批设置里参与了哪些level>1的设置，以及相应的level
			strSQL = " select aa.* from loan_approvalitem aa,loan_approvalsetting bb "
					+ " where aa.napprovalid = bb.id "
					+ " and bb.nstatusid = "
					+ Constant.ApprovalStatus.SUBMIT
					+ " and aa.nuserid = "
					+ lUserID
					+ " and aa.nLevel > 1 ";
			strSQL += " and bb.nOfficeID = " + lOfficeID;
			strSQL += " and bb.nCurrencyID = " + lCurrencyID;
			if (lApprovalID > 0)
			{
				strSQL += " and bb.ID= " + lApprovalID;
			}
			System.out.println("strSQL=" + strSQL);
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			System.out.println("<><><><>1111进入判断1111<><><>");
			while (rs != null && rs.next())
			{
				System.out.println("<><><><>2222进入判断2222<><><>");
				lLevel = rs.getLong("nLevel");
				lTempApprovalID = rs.getLong("nApprovalID");
				System.out.println(checkNewUserInThisLevel(lTempApprovalID, lLevel, lNewUserID) == Constant.YesOrNo.YES);
				//然后判断新要转入审批权限的User在不在这个审批设置的这个level里并且要求lUserID没有被转移过权限，lNewUserID没有转出过权限
				if (checkNewUserInThisLevel(lTempApprovalID, lLevel, lNewUserID) == Constant.YesOrNo.YES && IsRightMoved(lTempApprovalID, lUserID, lNewUserID) == Constant.YesOrNo.NO)
				{
					System.out.println("<><><><>3333进入判断3333<><><>");
					if (moveRight(lTempApprovalID, lUserID, lNewUserID,endDate) < 0)
					{
						System.out.println("()()==<><>OK!!!!!<><>==()()");
						lReturn = -1;
						throw new Exception("转移错误！");
					}
					else
					{
						lReturn = 1;
					}
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
			Log.print("ApprovalSettingDao.findApprovalItemForChange() failed.  Exception is " + e.toString());
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

		return lReturn;
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c = null;
		Vector vTmp = new Vector();
		String strSQL = "";
		long lReturn = -1;

		try
		{
			//审批取消（将状态置为无效）
			strSQL = " update loan_approvalchangeitem set nstatusid = "
					+ Constant.RecordStatus.INVALID
					+ " where nuserid = "
					+ lUserID
					+ " and napprovalid in "
					+ " (select id from loan_approvalsetting "
					+ " where 1=1 ";
			strSQL += " and nOfficeID = " + lOfficeID;
			strSQL += " and nCurrencyID = " + lCurrencyID;
			if (lApprovalID > 0)
			{
				strSQL += " and id = " + lApprovalID;
			}
			strSQL += " and nstatusid = " + Constant.ApprovalStatus.SUBMIT + ") ";

			ps = m_Conn.prepareStatement(strSQL);
			Log.print("strSQL=" + strSQL);
			lReturn = ps.executeUpdate();

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
			Log.print("ApprovalDao.findApprovalItemForChange() failed.  Exception is " + e.toString());
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

		return lReturn;
	}

	/**
	 * 检查审批设置里该用户是不是这个级别的审核人，如果是，才允许进行权限转移
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @param       lLevel               审批级别
	 * @param       lNewUserID           用户ID
	 * @return      long                 1：是；2：否
	 */
	private long checkNewUserInThisLevel(long lApprovalID, long lLevel, long lNewUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = Constant.YesOrNo.NO;
		String strSQL = "";

		try
		{
			//先求出该设置里被分配了人员的等级一共有多少个，存入lReturn
		    //检查审批设置里该用户是不是这个级别的审核人，如果是，才允许进行权限转移
			strSQL = " select * from loan_approvalitem aa,Loan_Approvalsetting bb "
					+ " where aa.napprovalid = bb.id "
					+ " and aa.nuserid = "
					+ lNewUserID
					+ " and bb.nstatusid = "
					+ Constant.ApprovalStatus.SUBMIT
					+ " and aa.nlevel = "
					+ lLevel
					+ " and aa.napprovalid = "
					+ lApprovalID;

			ps = m_Conn.prepareStatement(strSQL);
			System.out.println("(1)第一次判断SQL:"+strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = Constant.YesOrNo.YES;
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
			Log.print("ApprovalDao.checkNewUserInThisLevel() failed.  Exception is " + e.toString());
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
	 * 判断lUserID、和lNewUserID的权限是不是没有分别被转移和转移出去
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @param       lLevel               审批级别
	 * @param       lNewUserID           用户ID
	 * @return      long                 1：是；2：否
	 */
	private long IsRightMoved(long lApprovalID, long lUserID, long lNewUserID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = Constant.YesOrNo.NO;
		String strSQL = "";

		try
		{
			//先求出该设置里被分配了人员的等级一共有多少个，存入lReturn
			/*strSQL = " select * from loan_approvalitem aa,Loan_Approvalsetting bb "
				   + " where aa.napprovalid = bb.id "
				   + " and aa.nuserid = " + lNewUserID
				   + " and bb.nstatusid = " + Constant.ApprovalStatus.SUBMIT
				   + " and aa.nlevel = " + lLevel
				   + " and aa.napprovalid = " + lApprovalID;*/
			//先看有没有转给lUserID的权限记录,以及被lNewUserID转给的用户
			strSQL = " select * from loan_approvalchangeitem where "
					+ " nApprovalID = "
					+ lApprovalID
					+ " and nStatusID = "
					+ Constant.RecordStatus.VALID
					+ " and nNewUserID = "
					+ lUserID
					+ " union "
					+ " select * from loan_approvalchangeitem where "
					+ " nApprovalID = "
					+ lApprovalID
					+ " and nStatusID = "
					+ Constant.RecordStatus.VALID
					+ " and nUserID = "
					+ lNewUserID;
			ps = m_Conn.prepareStatement(strSQL);
			System.out.println("(2)第一次判断SQL:"+strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = Constant.YesOrNo.YES;
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
			Log.print("ApprovalDao.IsRightMoved() failed.  Exception is " + e.toString());
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
	 * 进行权限转移
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          审批设置标示（查询条件）
	 * @param       lUserID              原来审批用户
	 * @param       lNewUserID           审批转移到的用户id
	 * @return      long
	 */
	private long moveRight(long lApprovalID, long lUserID, long lNewUserID,Timestamp endDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = Constant.YesOrNo.NO;
		String strSQL = "";
		long lTmp = 0; //操作标识，1、新增；2、更新

		try
		{
			//先看权限转移表里有没有该用户在该审批设置的记录
			strSQL = " select * from loan_approvalchangeitem " + " where napprovalid = " + lApprovalID + " and nUserID = " + lUserID + " and nstatusID = " + Constant.RecordStatus.VALID;

			ps = m_Conn.prepareStatement(strSQL);
			System.out.println("第二次判断SQL:"+strSQL +"-------:"+ endDate);
			rs = ps.executeQuery();
			if (rs.next()) //如果有，则进行更新操作
			{
				lTmp = 2;
			}
			else
			{
				lTmp = 1;
			}
			System.out.println("============"+lTmp);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (lTmp == 1) //新增操作
			{
				System.out.println("----------------2--------------:"+endDate);
				strSQL = " insert into loan_approvalchangeitem(napprovalid," + " nuserid,nnewuserid,dtinputdate,nstatusid,endDate) " + " values(?,?,?,sysdate,?,?) ";
				ps = m_Conn.prepareStatement(strSQL);
				ps.setLong(1, lApprovalID);
				ps.setLong(2, lUserID);
				ps.setLong(3, lNewUserID);
				ps.setLong(4, Constant.RecordStatus.VALID);
				ps.setTimestamp(5,endDate);
				System.out.println("                    "+strSQL);
				lReturn = ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else //进行更新操作
			{
				strSQL = " update loan_approvalchangeitem set nnewuserid = ?, "
						+ " dtinputdate=sysdate "
						+ " where napprovalid = "
						+ lApprovalID
						+ " and nuserid = "
						+ lUserID
						+ " and nstatusid = "
						+ Constant.RecordStatus.VALID
						+ " and endDate = "
						+ endDate;
				ps = m_Conn.prepareStatement(strSQL);
				System.out.println("                    "+strSQL);
				ps.setLong(1, lNewUserID);
				lReturn = ps.executeUpdate();
				ps.close();
				ps = null;
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
			Log.print("ApprovalDao.checkNewUserInThisLevel() failed.  Exception is " + e.toString());
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
}
