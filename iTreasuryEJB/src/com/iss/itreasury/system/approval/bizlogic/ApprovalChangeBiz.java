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
	 * ��ѯ���û�����Щ��Ӧģ�飬������ҵ���µ����������Ȩ��ת�����
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               ģ������
	 * @param       lLoanTypeID             ҵ������
	 * @param       lActionID               ����ID
	 * @param       lUserID                 �û�ID
	 * @param long           lPageLineCount        ÿҳҳ��������
	 * @param long           lPageNo               �ڼ�ҳ����
	 * @param long           lOrderParam           �������������ݴ˲��������������������
	 * @param long           lDesc                 �������
	 * @return      Collection              �������������Ϣ(ApprovalChangeInfo)
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
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//��ѯ���������Ϣ
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
	 * ��ѯ���û��ڸ����������µ�Ȩ��ת�����
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lApprovalID             ��������ID
	 * @param       lUserID                 �û�ID
	 * @return      Collection              ����������Ϣ(ApprovalChangeInfo)
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
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//��ѯ���������Ϣ
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
	 * ����Ȩ��ת��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       
	 * @param       lUserID                 �û�ID
	 * @param long  lNewUserID              ת�Ƶ��û�ID
	 * @return      long                    ���ؽ����ʶ  1���ɹ���-1��ʧ��
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
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//Ȩ��ת��
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
	 * ����Ȩ��ȡ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing,ApprovalChangeItem
	 * @param       lModuleID               ģ������
	 * @param       lLoanTypeID             ҵ������
	 * @param       lActionID               ����ID
	 * @param       lUserID                 �û�ID
	 * @return      long                    ���ؽ����ʶ  1���ɹ���-1��ʧ��
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
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			changedao = new ApprovalChangeDao(con);

			//Ȩ��ȡ��
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
