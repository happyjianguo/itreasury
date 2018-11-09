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

package com.iss.itreasury.system.approval.bizlogic;

import java.sql.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.dataentity.*;
import com.iss.itreasury.system.approval.dao.*;
import com.iss.itreasury.system.approval.dataentity.*;

/**
 *
 * @author  yfan
 * @update
 */
public class ApprovalBiz extends Object implements java.io.Serializable
{

	/**
	 * ��ѯ����������Ϣ
	 * ��ѯ����Ϊ�������ñ�ʾ��Ҳ������Ϊ����ģ�顢ҵ�����͡�������ʾ��ѯ�������Ƽ�ʹ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   �������ñ�ʾ����ѯ������
	 * @return      ApprovalSettingInfo    ��������������Ϣ
	 */
	public ApprovalSettingInfo findApprovalSetting(long lApprovalID) throws	Exception
	{
		Connection con = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvalsettingdao = new ApprovalSettingDao(con);

			//��ѯ����������Ϣ
			ASInfo = approvalsettingdao.findApprovalSetting(lApprovalID);

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
	/**
	 * �������������Ϣ
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      ���������Ϣ
	 * @return      long        �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long saveApprovalTracing(ApprovalTracingInfo ATInfo) throws
		Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalSettingInfo ASInfo = null;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalDao(con);

            //�õ����µ�approvaltracing���id
			ATInfo.setID(approvaldao.findApprovalTracingMaxID());
			//�õ�SerialID��������
			ATInfo.setSerialID(approvaldao.findApprovalTracingSerialID(ATInfo.getModuleID(),ATInfo.getLoanTypeID(),ATInfo.getActionID(),ATInfo.getOfficeID(),ATInfo.getCurrencyID(),ATInfo.getApprovalContentID()));
			
			//�������������Ϣ
			lReturn = approvaldao.saveApprovalTracing(ATInfo);
			
//			�����޸ģ�����ҵ����ǰ�����������������������ó�-1
			if (ATInfo.getResultID() == Constant.ApprovalDecision.RETURN)
			{
			    approvaldao.updateApprovalTracing(ATInfo.getModuleID(),ATInfo.getLoanTypeID(),ATInfo.getActionID(),ATInfo.getOfficeID(),ATInfo.getCurrencyID(),ATInfo.getApprovalContentID(),1);
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
				"ApprovalBiz.saveApprovalTracing() failed.  Exception is " +
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
	 * ��ѯ��������Ϣ
	 * ������Ҫ�����Ƽ�ʹ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������
	 * @param       lLoanTypeID
	 * @param       lActionID               ����ID
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @return      Collection              ������������Ϣ(ApprovalTracingInfo)
	 */
	public ApprovalTracingInfo findApprovalTracingInfo(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lLevel) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		Collection c = null;
		ApprovalTracingInfo ATInfo = null;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//��ѯ��������Ϣ
			c = approvaldao.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lLevel,Constant.PageControl.CODE_ASCORDESC_DESC);
			if ( c != null )
			{
				Iterator it = c.iterator();
	            if( it.hasNext() )
	            {
	                ApprovalTracingInfo info = (ApprovalTracingInfo)it.next();
	                ATInfo = info;
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
				"ApprovalBiz.findApprovalTracing() failed.  Exception is " +
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

		return (ATInfo);
	}

	/**
	 * ��ѯ��������Ϣ
	 * ������Ҫ�����Ƽ�ʹ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������
	 * @param       lLoanTypeID
	 * @param       lActionID               ����ID
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @return      Collection              ������������Ϣ(ApprovalTracingInfo)
	 */
	public Collection findLinkedApproval(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID,long subLoanTypeID, long lDesc) throws Exception{
		Connection con = null;
		ApprovalDao approvaldao = null;
		Collection cReturn = new ArrayList();
		Collection c = null;
		ApprovalTracingInfo[] ATInfos = null;
		try{
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			c = findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lDesc);
			if(c != null && c.size() > 0){
				ATInfos = new ApprovalTracingInfo[8];
				Iterator it = c.iterator();
				while(it.hasNext()){
					ApprovalTracingInfo ATInfo = (ApprovalTracingInfo)it.next();
					int temp = (int)approvaldao.getApprovalLevel(ATInfo.getUserID(),lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,subLoanTypeID);
					if(temp > 0){
						if(ATInfos[temp] == null)
						{
							ATInfos[temp] = new ApprovalTracingInfo();
							ATInfos[temp].setOpinion(ATInfo.getOpinion());
							ATInfos[temp].setUserName(ATInfo.getUserName()+"<br>"+
									DataFormat.getDateString(ATInfo.getApprovalDate()).substring(0,4)+
									"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
									"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"��");
							cReturn.add(ATInfos[temp]);
						}else{
							ATInfos[temp].setOpinion(ATInfos[temp].getOpinion()+"<br>"+ATInfo.getOpinion());
							ATInfos[temp].setUserName(ATInfos[temp].getUserName()+"<br>"+ATInfo.getUserName()+
									"<br>"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(0,4)+
									"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
									"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"��");
						}
						
					}else if(temp == -1){
						ATInfo.setUserName(ATInfo.getUserName()+"<br>"+
								DataFormat.getDateString(ATInfo.getApprovalDate()).substring(0,4)+
								"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
								"��"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"��");
						cReturn.add(ATInfo);
					}
				}
			}
			
		}catch(Exception e){
			if (con != null)
			{
				con.close();
				con = null;
			}
			e.printStackTrace();
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
				ex.printStackTrace();
			}
		}
		return cReturn;
	}
	
	public Collection findApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lDesc) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalTracingInfo ATInfo = null;
		Collection c = null;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//��ѯ��������Ϣ
			c = approvaldao.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,-1,lDesc);

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
				"ApprovalBiz.findApprovalTracing() failed.  Exception is " +
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
	 * ������һ�����������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             ������ñ�ʾ
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @return      Collection              ������������Ϣ(ApprovalTracingInfo)
	 */
	public String getLastCheckPerson(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		String strLastCheckPerson = "";
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalTracingInfo ATInfo = null;
		Collection c = null;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//��ѯ��������Ϣ
			c = approvaldao.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,-1,Constant.PageControl.CODE_ASCORDESC_DESC);
			if ( c != null )
			{
				Iterator it = c.iterator();
	            if( it.hasNext() )
	            {
	                ApprovalTracingInfo info = (ApprovalTracingInfo)it.next();
	                ATInfo = info;
	            }
			}
			strLastCheckPerson = ATInfo.getUserName();

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
				"ApprovalBiz.findApprovalTracing() failed.  Exception is " +
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

		return (strLastCheckPerson);
	}

	/**
	 * ɾ����������Ϣ
	 * ԭ����Ӧ�ö�ʹ���߼�ɾ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             ������ñ�ʾ
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @param       lActionID               ��ʶ��1������ɾ����2���߼�ɾ��
	 * @return      long                    �ɹ�������ֵ=1��ʧ�ܣ�����ֵ=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws	Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalDao(con);

			//ɾ����������Ϣ
			lReturn = approvaldao.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lActionID);
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
			Log.print(
				"ApprovalBiz.deleteApprovalTracing() failed.  Exception is " +
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
	 * ��ʾ��һ��������б�
	 * ֧���ظ�������Ա��Խ������Լ���ȿ���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     ���
	 * @param       strFieldName            �������
	 * @param       strNextFieldName        ��һ���������
	 * @param       lModuleID               ģ���ʾ
	 * @param       lLoanTypeID             ҵ�����ͱ�ʶ
	 * @param       lActionID               ������ʶ
	 * @param       lUserID                 ����û���ʾ
	 * @param       lLevel                  ��ǰ��˼���
	 * @param       bIsLowLevel             �Ƿ���������������
	 * @return      long                    ���һ����˷���1������������˷���0�����󷵻�-1
	 */
	public long showApprovalUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, long lModuleID,
									 long lLoanTypeID, long lActionID,
									 long lOfficeID, long lCurrencyID,
									 long lUserID, long lLevel, 
									 boolean bIsLowLevel) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		ApprovalTracingInfo ATInfo = null;
		ApprovalSettingInfo ASInfo = null;
		long lApprovalID = -1;
		long lTotalLevel = -1;
		long lLowLevel = -1;
		long lIsPass = -1;		
		long lStatusID = -1;
		Vector vNextUser = new Vector(); //��һ������˼���

		try 
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);

//			System.out.println("====��ѯ���������Ϣ====start==");
			//��ѯ���������Ϣ
			ASInfo = approvalsettingdao.findApprovalSetting(approvaldao.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID));

//			System.out.println("====��ѯ���������Ϣ====ok==");
			
			if (ASInfo != null)
			{
				lApprovalID = ASInfo.getID();
				lTotalLevel = ASInfo.getTotalLevel();
				lLowLevel = ASInfo.getLowLevel();
				lIsPass = ASInfo.getIsPass();
				lStatusID = ASInfo.getStatusID();
				if (bIsLowLevel)
				{
				    lTotalLevel = lLowLevel;
				}
//				System.out.println("========showApprovalUserList != null ===");
//				System.out.println("==================lApprovalID="+lApprovalID);
			}
			//��������õ�״̬�����Ѽ���򷵻�-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if (lLevel <= 0)
                {
//					System.out.println("===========level <= 0==========");
                    //��ǰ����˲������������У�����-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{				
					if(lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel)){
						 showWrongUserList(out,strFieldName,strNextFieldName,3);
			             lReturn = -2;
					}else{
						showUserList(out, strFieldName, strNextFieldName, null, 3);
						lReturn = 1;
					}
//					System.out.println("===========lLevel == lTotalLevel=====");
					//���һ�����
									
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
//					System.out.println("===========lLevel == 1 =====");
				    //��ǰ����˲����������õ�һ���У�����-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//��ѯ�����Ա��Ϣ
					ASInfo = null;
					if (lIsPass == 1) //����Խ�����
					{					   
//						System.out.println("===========lIsPass == 1====="); 
					        ASInfo = approvaldao.findApprovalItemAboveLevel(
									lApprovalID,
									lLevel,
									lUserID,									
									lTotalLevel,lCurrencyID,lOfficeID);					    
					}
					else
					{					    
//						System.out.println("===========lIsPass != 1====="); 
					        ASInfo = approvaldao.findApprovalItem(lApprovalID,
									lLevel+1,
									lUserID,
									lCurrencyID,lOfficeID);					    
					}
					if (ASInfo != null)
					{
//						System.out.println("===========getNextUser====="); 
						vNextUser = ASInfo.getNextUser();
					}
					if (vNextUser == null || vNextUser.size() == 0)
					{
						showWrongUserList(out,strFieldName,strNextFieldName,4);
						//û��������һ�������Ա
						lReturn = -1;
					}
					else
					{
//						System.out.println("===========�����Ա�����б���ʾ====="); 
						//�����Ա�����б���ʾ�ؼ�
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				System.out.println("======���õ�״̬�����Ѽ���=====lReturn = -1 ====="); 
				showWrongUserList(out,strFieldName,strNextFieldName,1);
				lReturn = -1;
			}

			con.close();
			con = null;
		}
        catch (IException ie)
        {
            if (con != null)
			{
				con.close();
				con = null;
			}
            throw ie;
        }
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.showApprovalUserList() failed.  Exception is " +
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
	 * ��ѯ�û���˼���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            ������ñ�ʾ
	 * @param       lUserID                �û���ʶ
	 * @return      long                   �����û���˼���
	 */
	public long findApprovalUserLevel(long lApprovalID, long lUserID) throws
		Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		//boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			lReturn = approvaldao.findApprovalUserLevel(lApprovalID,lUserID);
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.findApprovalUserLevel() failed.  Exception is " +
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
	 * �����Ա�����б���ʾ�ؼ�
	 * @param out                ���
	 * @param strFieldName       �������
	 * @param strFieldName       ��һ���������
	 * @param NextUser           �û��б�
	 * @param lDisplayFinish     �Ƿ���ʾ�������ɡ���1 �ǣ�2 ��3 ֻ��ʾ�������ɡ�
	 *
	 */
	private static void showUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, Vector vNextUser,
									 long lDisplayFinish) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();

		try
		{
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			if (lDisplayFinish != 3)
			{
				if (vNextUser != null && vNextUser.size() > 0)
				{
					while (i < vNextUser.size())
					{
						AUInfo = (ApprovalUserInfo) vNextUser.get(i);
						out.println("<option value=\"" + AUInfo.getUserID() +
									"\">" + AUInfo.getUserName() + "</option>");
						i++;
					}
						if (lDisplayFinish == 1)
						{
							out.println("<option value=\"-2\">������</option>");
						}
				}
			}
			else
			{
				out.println("<option value=\"-2\">������</option>");
			}
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}

	/**
	 * �����Ա�����б���ʾ�ؼ�(��������δ��ɡ���
	 * @param out                ���
	 * @param strFieldName       �������
	 * @param strFieldName       ��һ���������
	 * @param nErrorNo       	 ������Ϣ���
	 *
	 */
	private static void showWrongUserList(JspWriter out, String strFieldName,String strNextFieldName,int nErrorNo) throws Exception
	{
		int i = 0;
		ApprovalUserInfo AUInfo = new ApprovalUserInfo();
		String strError = "";

		try
		{
			switch (nErrorNo)
			{
				case 1:
				    strError = "��������δ��ɻ�û�м�����飡";
				    break;
				case 2:
				    strError = "��ǰ����˲��ڸ����������У����飡";
				    break;
				case 3:
				    strError = "��ǰ����˲��ڸ��������õ�һ���У����飡";
				    break;
				case 4:
				    strError = "��һ�������Ϊ�գ����飡";
				    break;
				default :
				    strError = "�������ò���ȷ�����飡";
			}
		    out.println("<script language='javascript'>");
			out.println("alert('" + strError + "');");
			//Added by zwsun, 2007/10/12 ���ɹ���ʱ�����
			out.println("history.back(-1);");
			out.println("</script>");
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}

	/**
	 * lModuleID,lLoanTypeID,lActionIDȷ��lApprovalID
	 * ֧��ͨ������������ӵ�����������Ϣ�����ʽ�ƻ�ģ��Ĳ�������
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lActionID
	 * @return      lApprovalID            ��������������Ϣ
	 */
	public long getApprovalID(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID) throws
		Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		long lApprovalID = -1;

		try
		{
			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//��ѯ����������Ϣ
			lApprovalID = approvaldao.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);

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
				"ApprovalBiz.getApprovalID() failed.  Exception is " +
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

		return lApprovalID;
	}

	/**
	 * ��ѯ��������������ʵ���û�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lLoanActionID
	 * @param       lUserID                �û���ʶ
	 * @param       con                	   ���ݿ�����
	 * @return      long                   �����û���˼���
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID,Connection con) throws
		Exception
	{
		//Connection con = null;
		ApprovalDao approvaldao = null;
		//boolean bSucceed = false;
		long lApprovalID = -1;
		String strReturn = "";

		try
		{
			//con = Database.getConnection();
		    approvaldao = new ApprovalDao(con);
			lApprovalID = approvaldao.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			strReturn= approvaldao.findApprovalChangeUser(lApprovalID,lUserID);
		}
		catch (Exception e)
		{
		    /*
		    if (con != null)
			{
				con.close();
				con = null;
			}
			*/
			Log.print(
				"ApprovalBiz.findTheVeryUser() failed.  Exception is " +
				e.toString());
		}
		finally
		{
			try
			{
				/*
			    if (con != null)
				{
					con.close();
					con = null;
				}
				*/
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}

		return (strReturn);
	}
	
	/**
	 * ��ѯ��������������ʵ���û�
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lLoanActionID
	 * @param       lUserID                �û���ʶ
	 * @return      long                   �����û���˼���
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID) throws
		Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		//boolean bSucceed = false;
		long lApprovalID = -1;
		String strReturn = "";

		try
		{
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			lApprovalID = approvaldao.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
			strReturn= approvaldao.findApprovalChangeUser(lApprovalID,lUserID);
		}
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.findTheVeryUser() failed.  Exception is " +
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

		return (strReturn);
	}
	
	public static void main(String[] args)
	{
		try
		{
			ApprovalBiz app = new ApprovalBiz();
			ApprovalDao appDao = new ApprovalDao();
			Vector vTmp = new Vector();
			ApprovalSettingInfo info = new ApprovalSettingInfo();
			ApprovalUserInfo uinfo = new ApprovalUserInfo();
			ApprovalUserInfo uinfo2 = new ApprovalUserInfo();
			ApprovalTracingInfo tinfo = new ApprovalTracingInfo();
			//info.setID(1);
			/**info.setModuleID(2);
						 info.setLoanTypeID(2);
						 info.setActionID(1);
						 info.setTotalLevel(4);
						 info.setIsPass(1);*/
			//app.saveApprovalSetting(info);
			//info = app.findApprovalSetting(7);
			/*uinfo.setUserID(65);
						 uinfo.setUserName("�±���");
						 vTmp.add(uinfo);
						 uinfo2.setUserID(66);
						 vTmp.add(uinfo2);
						 info.setSelectUser(vTmp);
						 info.setID(7);
						 info.setLevel(2);
						 app.saveApprovalItem(info);*/
			/**info = app.findApprovalItem(7,2);
						 for(int i=0;i<info.getSelectUser().size();i++)
						 {
			  uinfo = (ApprovalUserInfo)info.getSelectUser().elementAt(i);
			  Log.print("id="+uinfo.getUserID());
			  Log.print("name="+uinfo.getUserName());
						 }
						 for(int i=0;i<info.getNotSelectUser().size();i++)
						 {
			  uinfo = (ApprovalUserInfo)info.getNotSelectUser().elementAt(i);
			  Log.print("2id="+uinfo.getUserID());
			  Log.print("2name="+uinfo.getUserName());
						 }*/
			/**tinfo.setModuleID(2);
						 tinfo.setLoanTypeID(2);
						 tinfo.setActionID(1);
						 tinfo.setApprovalContentID(101);
						 tinfo.setSerialID(1);
						 tinfo.setUserID(65);
						 tinfo.setNextUserID(66);
						 tinfo.setOpinion("�Ǻ�");
				 tinfo.setApprovalDate(new Timestamp(103,05,02,12,00,00,00));
						 tinfo.setResultID(1);
						 tinfo.setIsFinish(0);
						 tinfo.setStatusID(1);
						 app.saveApprovalTracing(tinfo);*/
			/**vTmp = (Vector)app.findApprovalTracing(5,100);
						 Log.print("hrere!"+vTmp);
						 if(vTmp!=null)
						 {
			  for (int i = 0; i < vTmp.size(); i++) {
				tinfo = (ApprovalTracingInfo) (vTmp.elementAt(i));
				Log.print("id=" + tinfo.getID());
			  }
						 }*/
			//Log.print("result="+app.deleteApprovalTracing(5,101));
			//Log.print("result="+app.checkApprovalTracing(5));
			//Log.print("result="+app.checkApprovalIntegrality(7));
			//app.activeApprovalSetting(7);

			//Log.print("resule="+app.checkApprovalIntegrality(6));

			/**info = appDao.findApprovalSetting(2,2,1);
						 Log.print("id="+info.getID());
						 Log.print("nModuleID="+info.getModuleID());
						 Log.print("nLoanTypeID="+info.getLoanTypeID());
						 Log.print("nActionID="+info.getActionID());
						 Log.print("nTotalLevel="+info.getTotalLevel());
						 Log.print("nIsPass="+info.getIsPass());
						 Log.print("nstatusID="+info.getStatusID());*/
			//Log.print(app.activeApprovalSetting(1));
//			Collection c1 = app.findApprovalTracing(2,7,1,1,1,103,1);
//			if(c1 != null)
//			{
//				System.out.println("size===="+c1.size());
//			}
//			else
//			{
//				System.out.println("size====0");
//			}
			tinfo = app.findApprovalTracingInfo(2,1,1,1,1,1,1);
			ApprovalTracingInfo tinfo1 = new ApprovalTracingInfo();
			
			tinfo1.setModuleID(1);
			tinfo1.setLoanTypeID(1);
			tinfo1.setActionID(1);
			tinfo1.setApprovalContentID(1);
			tinfo1.setUserID(1);
			tinfo1.setNextUserID(2);
			tinfo1.setOpinion("123");
			tinfo1.setResultID(1);
			tinfo1.setStatusID(1);
			tinfo1.setOfficeID(1);
			tinfo1.setCurrencyID(1);
			app.saveApprovalTracing(tinfo1);
			
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
	}
	
	/**
	 * ��ѯ��������Ϣ
	 * ������Ҫ�����Ƽ�ʹ��
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               ģ������
	 * @param       lLoanTypeID
	 * @param       lActionID               ����ID
	 * @param       lApprovalContentID      ������ݱ�ʾ
	 * @return      Collection              ������������Ϣ(ApprovalTracingInfo)
	 */
	public Collection findValidateApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lDesc) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;
		ApprovalTracingInfo ATInfo = null;
		Collection c = null;

		try
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//��ѯ��������Ϣ
			c = approvaldao.findValidateApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,-1,lDesc);

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
				"ApprovalBiz.findApprovalTracing() failed.  Exception is " +
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
	 * ��ʾ��һ��������б� //: new function //:~
	 * ֧���ظ�������Ա��Խ������Լ���ȿ���
	 * �������ݿ��ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     ���
	 * @param       strFieldName            �������
	 * @param       strNextFieldName        ��һ���������
	 * @param       lModuleID               ģ���ʾ
	 * @param       lLoanTypeID             ҵ�����ͱ�ʶ
	 * @param       lActionID               ������ʶ
	 * @param       lUserID                 ����û���ʾ
	 * @param       lLevel                  ��ǰ��˼���
	 * @param       bIsLowLevel             �Ƿ���������������
	 * @return      long                    ���һ����˷���1������������˷���0�����󷵻�-1
	 */
	public long showApprovalUserList1(JspWriter out, String strFieldName,
									 String strNextFieldName, long lModuleID,
									 long lLoanTypeID, long lActionID,
									 long lOfficeID, long lCurrencyID,
									 long lUserID, long lLevel, double amount,
									 boolean bIsLowLevel) throws Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		ApprovalTracingInfo ATInfo = null;
		ApprovalSettingInfo ASInfo = null;
		long lApprovalID = -1;
		long lTotalLevel = -1;
		long lLowLevel = -1;
		long lIsPass = -1;		
		long lStatusID = -1;
		Vector vNextUser = new Vector(); //��һ������˼���

		try 
		{
			//connection ����Ƕ��

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);

//			System.out.println("====��ѯ���������Ϣ====start==");
			//��ѯ���������Ϣ
			ASInfo = approvalsettingdao.findApprovalSetting(approvaldao.getApprovalID1(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,amount));

//			System.out.println("====��ѯ���������Ϣ====ok==");
			
			if (ASInfo != null)
			{
				lApprovalID = ASInfo.getID();
				lTotalLevel = ASInfo.getTotalLevel();
				lLowLevel = ASInfo.getLowLevel();
				lIsPass = ASInfo.getIsPass();
				lStatusID = ASInfo.getStatusID();
				if (bIsLowLevel)
				{
				    lTotalLevel = lLowLevel;
				}
//				System.out.println("========showApprovalUserList != null ===");
//				System.out.println("==================lApprovalID="+lApprovalID);
			}
			//��������õ�״̬�����Ѽ���򷵻�-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if (lLevel <= 0)
                {
//					System.out.println("===========level <= 0==========");
                    //��ǰ����˲������������У�����-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{
//					System.out.println("===========lLevel == lTotalLevel=====");
					//���һ�����
					showUserList(out, strFieldName, strNextFieldName, null, 3);
					lReturn = 1;
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
//					System.out.println("===========lLevel == 1 =====");
				    //��ǰ����˲����������õ�һ���У�����-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//��ѯ�����Ա��Ϣ
					ASInfo = null;
					if (lIsPass == 1) //����Խ�����
					{					   
//						System.out.println("===========lIsPass == 1====="); 
					        ASInfo = approvaldao.findApprovalItemAboveLevel(
									lApprovalID,
									lLevel,
									lUserID,									
									lTotalLevel,lCurrencyID,lOfficeID);					    
					}
					else
					{					    
//						System.out.println("===========lIsPass != 1====="); 
					        ASInfo = approvaldao.findApprovalItem(lApprovalID,
									lLevel+1,
									lUserID,
									lCurrencyID,lOfficeID);					    
					}
					if (ASInfo != null)
					{
//						System.out.println("===========getNextUser====="); 
						vNextUser = ASInfo.getNextUser();
					}
					if (vNextUser == null || vNextUser.size() == 0)
					{
						showWrongUserList(out,strFieldName,strNextFieldName,4);
						//û��������һ�������Ա
						lReturn = -1;
					}
					else
					{
//						System.out.println("===========�����Ա�����б���ʾ====="); 
						//�����Ա�����б���ʾ�ؼ�
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				System.out.println("======���õ�״̬�����Ѽ���=====lReturn = -1 ====="); 
				showWrongUserList(out,strFieldName,strNextFieldName,1);
				lReturn = -1;
			}

			con.close();
			con = null;
		}
        catch (IException ie)
        {
            if (con != null)
			{
				con.close();
				con = null;
			}
            throw ie;
        }
		catch (Exception e)
		{
		    if (con != null)
			{
				con.close();
				con = null;
			}
			Log.print(
				"ApprovalBiz.showApprovalUserList() failed.  Exception is " +
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
