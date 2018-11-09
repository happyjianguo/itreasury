/*
 * Created on 2004-11-18
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
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.ebank.approval.dataentity.ApprovalQueryInfo;
import com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalQueryDao extends Object implements java.io.Serializable
{

	private Connection m_Conn = null;

	public ApprovalQueryDao()
	{
	    try
        {
            m_Conn = Database.getConnection();
        } 
	    catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	public ApprovalQueryDao(Connection con)
	{
		m_Conn = con;
	}

	/**
	 * ��ѯ����������Ϣ
	 * ��ѯ����Ϊ�������ñ�ʾ��Ҳ������Ϊ����ģ�顢�������͡�������ʾ��ѯ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID    			       �������ñ�ʾ����ѯ������
	 * @return      ApprovalSettingInfo    ��������������Ϣ
	 */
	public ApprovalSettingInfo findApprovalSetting() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
		String strSQL = "";
		try
		{
			strSQL = " select * from ob_ApprovalSetting order by ID ";
			ps = m_Conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				ASInfo.setID(rs.getLong("ID"));
				ASInfo.setName(rs.getString("sName"));
				ASInfo.setTotalLevel(rs.getLong("nTotalLevel"));
				ASInfo.setIsPass(rs.getLong("nIsPass"));
				ASInfo.setLowLevel(rs.getLong("nLowLevel"));
				ASInfo.setStatusID(rs.getLong("nStatusID"));
			}

		}
		catch (Exception e)
		{
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

		return (ASInfo);
	}
	
	/**
	 * �°汾2004.5.16
	 * ��ѯ�����û�Ȩ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lID                     �������ñ�ʾ
	 * @param       lLevel                  ��������
	 * @param       lUserID                 �ų����û�
	 * @param       lIsReduplicateAssign	�Ƿ�����������ظ�������Ա
	 * @return      ApprovalSettingInfo     ���������û�Ȩ����Ϣ����ѡ��Ϳɹ�ѡ����û��б�ȣ�
	 */
	public Collection findApprovalItem() throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApprovalQueryInfo AQInfo = new ApprovalQueryInfo();
		String strSQL = "";
		Collection c = null;
		Vector vTmp = new Vector();
		
		try
		{
			strSQL = " select a.nLevel,a.nUserID,b.sName,c.ID,c.sName ApprovalName "
			    + " from ob_ApprovalItem a,ob_user b,ob_ApprovalSetting c " 
			    + " where a.nUserID = b.ID and a.nApprovalID = c.ID and b.nStatus != 0 "
			    + " order by c.ID,a.nLevel ";
			ps = m_Conn.prepareStatement(strSQL);			
			rs = ps.executeQuery();
			while (rs.next())
			{
			    ApprovalQueryInfo approvalQueryInfo = new ApprovalQueryInfo();
			    approvalQueryInfo.setApprovalUserID(rs.getLong("nUserID"));
			    approvalQueryInfo.setApprovalUserName(rs.getString("sName"));
			    approvalQueryInfo.setApprovalLevel(rs.getLong("nLevel"));
			    vTmp.add(approvalQueryInfo);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
		}
		catch (Exception e)
		{
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

		return (vTmp);
	}


	public static void main(String[] args)
	{
		try
		{
		    ApprovalQueryDao appQuery = new ApprovalQueryDao();
		    Collection c = null;
			ApprovalSettingInfo info = new ApprovalSettingInfo();
			info = appQuery.findApprovalSetting();
			c = appQuery.findApprovalItem();
			if( (c != null) && (c.size() > 0) )
            {
				Iterator it = c.iterator();
                while (it.hasNext() )
				{
                    ApprovalQueryInfo qinfo = ( ApprovalQueryInfo ) it.next();                     
                    System.out.print(Constant.ModuleType.getName(qinfo.getModuleID()));
                    System.out.print("	");
                    System.out.print(Constant.ApprovalLoanType.getName(qinfo.getLoanTypeID()));
                    System.out.print("	");
                    System.out.print(Constant.ApprovalAction.getName(qinfo.getActionID()));
                    System.out.print("	");
                    System.out.print(qinfo.getActionName());
                    //System.out.print("	");
                    //System.out.print(qinfo.getApprovalUserID());
                    System.out.print("	");
                    System.out.print(qinfo.getApprovalUserName());
				}
            }
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
	}

}
