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
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApprovalSettingBiz extends Object implements java.io.Serializable
{

	/**
	 * ��������������Ϣ
	 * ����/�޸�����������Ϣ��
	 * * �Ŵ���֤ȯģ�������������Ϣ��ϵͳ����ʱ��ʼ������ģ����ߺ�ֻ���޸���Ϣ��
	 * * �ʽ�ƻ�ģ�������������Ϣ���沿��������Ϣ������޸Ķ���̬�仯�ģ�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	����������Ϣ
	 * @return      long    �ɹ��������������ñ�ʾ��ʧ�ܣ�����ֵ=-1
	 */
	public long saveApprovalSetting(ApprovalSettingInfo ASInfo) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{			
			con = Database.getConnection();			
			approvaldao = new ApprovalSettingDao(con);
			if(approvaldao.checkByApprovalName(ASInfo.getID(),ASInfo.getName(),ASInfo.getOfficeID())>0)
			{
				lReturn = -2;//������ͬ���Ƶ������������ܱ���
			}
			else
			{
				long lMaxID = approvaldao.findApprovalSettingMaxID();
				if (ASInfo.getID() > 0)
				{
					lReturn = approvaldao.saveApprovalSetting(ASInfo);
				}
				else
				{
					ASInfo.setID(lMaxID);
					// ����Ĭ��ֵ
					/*
					 * ASInfo.setTotalLevel(3); ASInfo.setLowLevel(3);
					 * ASInfo.setIsPass(Constant.YesOrNo.NO);
					 */
					ASInfo.setStatusID(Constant.ApprovalStatus.SAVE);
					lReturn = approvaldao.insertApprovalSetting(ASInfo);
				}

				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
				if (bSucceed)
				{
					// ɾ����ǰ�������û�Ȩ����Ϣ(ֻɾ��������TotalLevel�����)
					// ���������С�ˣ�ԭ���ڴ��������������Ѿ��������Ա����Ҫ����Ա�������ɾ����
					lReturn = approvaldao.deleteApprovalItemAboveLevel(ASInfo
							.getID(), ASInfo.getTotalLevel());
					if (lReturn > 0)
					{
						bSucceed = true;
					}
					else
					{
						bSucceed = false;
					}
				}
				if (bSucceed)
				{
					// con.commit();
					// lReturn = 1;
					lReturn = lMaxID;// �����ɹ����򷵻���ID
				}
				else
				{
					// con.rollback();
					lReturn = -1;
				}

				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			System.out.println(
				"ApprovalBiz.saveApprovalSetting() failed.  Exception is " +
				e.toString());
			//con.rollback();
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
	 * ɾ������������Ϣ
	 * * �Ŵ���֤ȯģ�������������Ϣ��ϵͳ����ʱ��ʼ������ģ����ߺ�ֻ���޸���Ϣ��
	 * * �ʽ�ƻ�ģ�������������Ϣ���沿��������Ϣ������޸Ķ���̬�仯�ģ�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo	����������Ϣ
	 * @return      long    �ɹ��������������ñ�ʾ��ʧ�ܣ�����ֵ=-1
	 */
	public long deleteApprovalSetting(long lApprovalID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalSettingDao(con);
			
			if (lApprovalID > 0)
			{
			    lReturn = approvaldao.deleteApprovalSetting(lApprovalID);
			}
						
			if (lReturn > 0)
			{
				bSucceed = true;
			}
			else
			{
				bSucceed = false;
			}			
			if (bSucceed)
			{
				//con.commit();
				lReturn = 1;
			}
			else
			{
				//con.rollback();
				lReturn = -1;
			}

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
			System.out.println(
				"ApprovalBiz.saveApprovalSetting() failed.  Exception is " +
				e.toString());
			//con.rollback();
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
	 * ��ѯ����������Ϣ
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   �������ñ�ʾ����ѯ������
	 * @return      ApprovalSettingInfo    ��������������Ϣ
	 */
	public ApprovalSettingInfo findApprovalSetting(long lApprovalID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//��ѯ����������Ϣ
			ASInfo = approvaldao.findApprovalSetting(lApprovalID);

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
			System.out.println("ApprovalBiz.findApprovalSetting() failed.  Exception is " +	e.toString());
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
	//add by ygzhao 05-05-09
	public Collection findApprovalSetting(ApprovalQueryInfo info) throws
	Exception
	{
	    Connection con = null;
	    ApprovalSettingDao approvaldao = null;
	    boolean bSucceed = false;
	    long lReturn = -1;
	    Collection c = null;
	    try
	    {			
	        con = Database.getConnection();
	        approvaldao = new ApprovalSettingDao(con);
	        //��ѯ����������Ϣ
	        c = approvaldao.findApprovalSetting(info);
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
	        System.out.println("ApprovalBiz.findApprovalSetting(ApprovalQueryInfo info) failed.  Exception is " +	e.toString());
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

	    return c;
	}	
	//

	/**
	 * ���������û�Ȩ����Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ASInfo    �����û�Ȩ����Ϣ��ѡ����û��б�ȣ�
	 * @return      long      �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long saveApprovalItem(ApprovalSettingInfo ASInfo) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalSettingDao(con);

			//ɾ���ɵ������û�Ȩ����Ϣ
			lReturn = approvaldao.deleteApprovalItem(ASInfo.getID(),
				ASInfo.getLevel());
			if (lReturn > 0)
			{
				bSucceed = true;
			}
			else
			{
				bSucceed = false;
			}
			if (bSucceed)
			{
				//�����µ������û�Ȩ����Ϣ
				lReturn = approvaldao.saveApprovalItem(ASInfo);
				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
			}
			if (bSucceed)
			{
				//��������װ̬Ϊ������
				approvaldao.changeApprovalSetting(ASInfo.getID());
				if (lReturn > 0)
				{
					bSucceed = true;
				}
				else
				{
					bSucceed = false;
				}
			}

			if (bSucceed)
			{
				//con.commit();
				lReturn = 1;
			}
			else
			{
				//con.rollback();
				lReturn = -1;
			}
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
			System.out.println(
				"ApprovalBiz.saveApprovalItem() failed.  Exception is " +
				e.toString());
			//con.rollback();
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
	 * ��ѯ�����û�Ȩ��
	 * ͨ����ǿ���Ƽ�ʹ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID         �������ñ�ʾ
	 * @param       lLevel              ��������
	 * @param       lUserID                 �ų����û�
	 * @param       lIsReduplicateAssign	�Ƿ�����������ظ�������Ա
	 * @return      ApprovalSettingInfo ���������û�Ȩ����Ϣ����ѡ��Ϳɹ�ѡ����û��б�ȣ�
	 */
	public ApprovalSettingInfo findApprovalItem(long lApprovalID, long lLevel, long lUserID, long lCurrencyID,long lOfficeID) throws
		Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//��ѯ�����û�Ȩ����Ϣ
			ASInfo = approvaldao.findApprovalItem(lApprovalID,lLevel,lUserID,lCurrencyID,lOfficeID);

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
			System.out.println(
				"ApprovalBiz.findApprovalItem() failed.  Exception is " +
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
		return (ASInfo);
	}
	/**
	 * ������������Ƿ�ȫ��������ɣ����û��������Դ��������ý��м���
	 * ��ѯ����Ϊ�������ñ�ʾ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ����ѯ������
	 * @return      long                 1���ǣ�2��������Ϣ��������3��������Ա��Ϣ����
	 */
	public long checkApprovalValidity(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);

			//��ѯ����������Ϣ
			lReturn = approvaldao.checkApprovalIntegrality(lApprovalID);
			if (lReturn == Constant.YesOrNo.YES)
			{
			    //��ѯ������Ա��Ϣ
			    if (approvaldao.checkApprovalItem(lApprovalID) == 2)
			    {
			        lReturn = 3;
			    }			    
			}
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
				"ApprovalBiz.checkApprovalIntegrality() failed.  Exception is " +
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
	 * ������������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ
	 * @return      long                 �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long activeApprovalSetting(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);


			//��ѯ����������Ϣ
			lReturn = approvaldao.activeApprovalSetting(lApprovalID);

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
				"ApprovalBiz.activeApprovalSetting() failed.  Exception is " +
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
	 * ����������Ϊ������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          �������ñ�ʾ
	 * @return      long                 �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long changeApprovalSetting(long lApprovalID) throws Exception
	{
	    Connection con = null;
		ApprovalSettingDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);			
			lReturn = approvaldao.changeApprovalSetting(lApprovalID);

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
				"ApprovalBiz.changeApprovalSetting() failed.  Exception is " +
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
		return lReturn;
	}	


	/**
	 *  ��ѯ�Ƿ���δ������ɵ�ҵ��
	 *    ApprovalSetting,  ApprovalItem,ApprovalTracing
	 * @param       lApprovalID          ?��?��?��??����???��?��????????
	 * @return      long                 1??????2??????
	 */
	public long checkApprovalTracing(long lApprovalID) throws Exception
	{
		Connection con = null;
		ApprovalSettingDao approvaldao = null;
		long lReturn = -1;
		try 
		{
			
			con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);			
			lReturn = approvaldao.checkApprovalTracing(lApprovalID);
			
			con.close();
			con = null;
		} 
		catch (SQLException e) {		} 
		catch (Exception e) {		}

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
		
		return lReturn;
	}
	/**����������Ƿ񱻹���
	 * 
	 * @author weihuang
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public long checkApprovalRelation(long lApprovalID,long officeid) throws Exception{
        long lReturn=-1;
        Connection con = null;
		ApprovalSettingDao approvaldao = null;
	    try{
	        con = Database.getConnection();
			approvaldao = new ApprovalSettingDao(con);
			lReturn=approvaldao.checkApprovalRelation(lApprovalID,officeid);
			con.close();
			con = null;
	    }catch(Exception ex){
	        
	    }finally
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
	    
	    return lReturn;
	    
	}
	public static void main(String[] args){
	    long l=-1;
	    ApprovalSettingBiz c=new ApprovalSettingBiz();
	    try {
            l=c.checkApprovalRelation(1,1);
            System.out.println("jieguo="+l);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	 }
}
