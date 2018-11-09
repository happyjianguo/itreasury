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
	 * ��������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     ����������Ϣ
	 * @return      long         �ɹ��������������ñ�ʾ��ʧ�ܣ�����ֵ=-1
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
	 * ��������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     ����������Ϣ
	 * @return      long         �ɹ��������������ñ�ʾ��ʧ�ܣ�����ֵ=-1
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
	 * ɾ������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	     ����������Ϣ
	 * @return      long         �ɹ��������������ñ�ʾ��ʧ�ܣ�����ֵ=-1
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
	 * ��ѯ����������Ϣ
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID    			       �������ñ�ʾ����ѯ������
	 * @return      ApprovalSettingInfo    ��������������Ϣ
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
	 * ����Ƿ������ͬ���Ƶ�������	 
	 * @param       approvalName       �����������ƣ���ѯ������
	 * @return      long    �����򷵻�1; �������򷵻�-1
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
	 * ���������û�Ȩ����Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo      �����û�Ȩ����Ϣ��ѡ����û��б�ȣ�
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
			//�жϵ�ǰ���ѡ���˶��ٸ�user����������д�����ݿ�
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
	 * �°汾2004.5.16
	 * ��ѯ�����û�Ȩ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     �������ñ�ʾ
	 * @param       lLevel                  ��������
	 * @param       lUserID                 �ų����û�	 
	 * @return      ApprovalSettingInfo     ���������û�Ȩ����Ϣ����ѡ��Ϳɹ�ѡ����û��б�ȣ�
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
			//���Ѿ�ѡ�����Ա���뵽ASInfo
			ASInfo.setSelectUser(vTmp);

			//�����ʱ��vector(�¼�������ﲻ���������û���������
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

			//�����ʱ��vector
			vTmp = new Vector();
			//���ڲ�ѯδѡ�������
			strSQL  = " select id,sname from ob_user where id not in ( "; 
			strSQL += " select nuserid from ob_ApprovalItem where nApprovalID = ? "; 
			//����������ظ�������Ա��ֻ���˵���ǰ������ѷ������Ա
			//���򣬹��˵����м�����ѷ������Ա
			/*
			if (lIsReduplicateAssign == 1)//��
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
			//��δѡ�����Ա�ӵ�ASInfo
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
	 * ɾ�������û�Ȩ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID       �������ñ�ʾ
	 * @param       lTotalLevel       ��������
	 * @return      long              �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ɾ�������û�Ȩ��(��level���ϵ�)
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID       �������ñ�ʾ
	 * @param       lTotalLevel       ��������
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ��ѯ��approvalsetting������id��1
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ���������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ������������Ƿ�ȫ��������ɣ����û��������Դ��������ý��м���
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ����ѯ������
	 * @return      long                 1���ǣ�2����
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
			//������������ﱻ��������Ա�ĵȼ�һ���ж��ٸ���������ʱ����lTmp
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

			//�����ApprovalSetting�����nlevelΪ����
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
	 * ����Ƿ���ȷ������Ա�����û��������Դ��������ý��м���
	 * ����Ƿ�����������ظ�������Ա���������ظ����������������������Դ��������ý����޸ı���
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ����ѯ������
	 * @return      long                 1���ǣ�2����
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
			//��飬������ʱ����lTmp
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
	 * ������������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ
	 * @return      long                 �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ����������Ϊ������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ
	 * @return      long                 �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 *  ��ѯ�Ƿ���δ������ɵ�ҵ��
	 *    ApprovalSetting,  ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          ?��?��?��??����???��?��????????
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
				info.setLoanTypeID(rs.getLong("LoanTypeID")); //�������ʹ���
				info.setSubLoanTypeID(rs.getLong("SubLoanTypeID")); //������������ID
				info.setActionID(rs.getLong("ActionID"));
				info.setOfficeID(rs.getLong("OfficeID")); //���´�
				info.setCurrencyID(rs.getLong("CurrencyID")); //����
				

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
	/**����������Ƿ񱻹���
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
