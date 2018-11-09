/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ���幦��˵��������Ȩ������
 *
 * ʹ��ע�����
 * 1
 * 2
 *
 * ���ߣ�����
 *
 * ������Ա��
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
	 * ��ѯ�û���������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            �������ñ�ʾ
	 * @param       lUserID                �û���ʶ
	 * @return      long                   �����û���������
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
	 * �°汾2004.5.16
	 * ��ѯ�����û�Ȩ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     �������ñ�ʾ
	 * @param       lLevel                  ��������
	 * @param       lUserID                 �ų����û�	 
	 * @return      ApprovalSettingInfo     ���������û�Ȩ����Ϣ����ѡ��Ϳɹ�ѡ����û��б�ȣ�
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
			//�����ʱ��vector(�¼�������ﲻ���������û���������
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
	 * �°汾2004.5.16
	 * ��ѯ�����û�Ȩ��(����Խ��)
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     �������ñ�ʾ
	 * @param       lLevel                  ��������
	 * @param       lUserID                 �ų����û�
	 * @param       lIsReduplicateAssign	�Ƿ�����������ظ�������Ա
	 * @param       lTopLevel               ����������𣨸��ݡ��Ƿ�����С���������жϣ�
	 * @return      ApprovalSettingInfo     ���������û�Ȩ����Ϣ����ѡ��Ϳɹ�ѡ����û��б�ȣ�
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
			//�����ʱ��vector(�¼�������ﲻ���������û���������
			vTmp = new Vector();
			strSQL = " select distinct a.nUserID,b.sName from ob_ApprovalItem a,ob_user b "
					+ " where nApprovalID = ? and nLevel >= ? "
					+ " and a.nUserID = b.ID "
					+ " and b.nOfficeID = " + lOfficeID  //������´�
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
	 * �������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ���������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ��ѯ��approvaltracing������id��1
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ���������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ��ѯ��approvaltracing������serialid��1
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ���������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
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
	 * ��ѯ���������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             �������ñ�ʾ
	 * @param       lApprovalContentID      �������ݱ�ʾ
	 * @param       lDesc                   ˳���ǵ���
	 * @return      Collection              �������������Ϣ(ApprovalTracingInfo)
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
	 * ɾ�����������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             �������ñ�ʾ
	 * @param       lApprovalContentID      �������ݱ�ʾ
	 * @param       lType               	��ʶ��1������ɾ����2���߼�ɾ��
	 * @return      long                    �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = 1;
		String strSQL = "";

		try
		{
			if (lTypeID == 1) //����ɾ��
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
			else //�߼�ɾ��
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
	 * �������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             �������ñ�ʾ
	 * @param       lApprovalContentID      �������ݱ�ʾ
	 * @param       lActionID               ��ʶ��1�������޸ģ����������ó�-1
	 * @return      long                    �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long updateApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		String strSQL = "";

		try
		{
			if (lTypeID == 1) //�����޸ģ����������ó�-1
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
	 * ��ѯ����������Ϣ
	 * ��ѯ��������Ϊ����ģ�顢�������͡�������ʾ��ѯ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              ģ���ʾ
	 * @param       lLoanTypeID            �������ͱ�ʶ
	 * @param       lActionID              ������ʶ
	 * @return      ApprovalSettingInfo    ��������������Ϣ
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
	 * ��ѯ��������������ʵ���û�
	 * �������ݿ��
	 * @param       lApprovalID
	 * @param       lUserID                �û���ʶ
	 * @return      long                   �����û���˼���
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
			//�����жϸ��û�����ʵ�û����������
			UserBiz ubiz = new UserBiz();
			PeopleInfo pinfo = new PeopleInfo();
			pinfo = ubiz.getUserByID(lUserID);
			//�������ʵ�û�
			if (pinfo.lIsVirtualUser == Constant.YesOrNo.NO)
			{
				strSQL = " select * from ob_Approvalchangeitem " + " where nUserID = " + lUserID + " and nApprovalID = " + lApprovalID + " and nStatusID = " + Constant.RecordStatus.VALID;
				ps = m_Conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					strReturn = " (0) "; //�����ת����Ȩ�ޣ��򿴲������������ļ�¼
				}
				else //����б����˴�������Ȩ��
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
						//lReturn = 1;//��־����ʾ������
						strTmp += rs.getString("nUserID") + ",";
					}
					strReturn = " ( " + strTmp + lUserID + " ) "; //��Ҫ�����Լ�
				}
			}
			else //����������û�
			{
				strSQL = " select nUserID from ob_Approvalchangeitem " + " where nNewUserID = " + lUserID + " and nApprovalID = " + lApprovalID + " and nStatusID = " + Constant.RecordStatus.VALID;
				ps = m_Conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs.next())
				{
					//lReturn = 1;//��־����ʾ������
					strTmp += rs.getString("nUserID") + ",";
				}
				strReturn = " ( " + strTmp + lUserID + " ) "; //��Ҫ�����Լ�
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
	 * ��ѯ�û��Ƿ���ָ������������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            �������ñ�ʾ
	 * @param       lUserID                �û���ʶ
	 * @param       lLevel                 �û���ʶ
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
