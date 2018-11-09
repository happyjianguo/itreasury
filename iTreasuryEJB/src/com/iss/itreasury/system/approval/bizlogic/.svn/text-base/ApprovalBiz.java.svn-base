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
	 * 查询审批设置信息
	 * 查询条件为审批设置标示，也可重载为根据模块、业务类型、操作标示查询，担不推荐使用
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   审批设置标示（查询条件）
	 * @return      ApprovalSettingInfo    返回审批设置信息
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

			//查询审批设置信息
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
	 * 保存审批意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审批意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
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
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalDao(con);

            //得到最新的approvaltracing表的id
			ATInfo.setID(approvaldao.findApprovalTracingMaxID());
			//得到SerialID并且设置
			ATInfo.setSerialID(approvaldao.findApprovalTracingSerialID(ATInfo.getModuleID(),ATInfo.getLoanTypeID(),ATInfo.getActionID(),ATInfo.getOfficeID(),ATInfo.getCurrencyID(),ATInfo.getApprovalContentID()));
			
			//保存审批意见信息
			lReturn = approvaldao.saveApprovalTracing(ATInfo);
			
//			返回修改，将此业务以前保存的审批意见的审批级别置成-1
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
	 * 查询审核意见信息
	 * 根据需要排序，推荐使用
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID
	 * @param       lActionID               操作ID
	 * @param       lApprovalContentID      审核内容标示
	 * @return      Collection              返回审核意见信息(ApprovalTracingInfo)
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
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//查询审核意见信息
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
	 * 查询审核意见信息
	 * 根据需要排序，推荐使用
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID
	 * @param       lActionID               操作ID
	 * @param       lApprovalContentID      审核内容标示
	 * @return      Collection              返回审核意见信息(ApprovalTracingInfo)
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
									"年"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
									"月"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"日");
							cReturn.add(ATInfos[temp]);
						}else{
							ATInfos[temp].setOpinion(ATInfos[temp].getOpinion()+"<br>"+ATInfo.getOpinion());
							ATInfos[temp].setUserName(ATInfos[temp].getUserName()+"<br>"+ATInfo.getUserName()+
									"<br>"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(0,4)+
									"年"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
									"月"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"日");
						}
						
					}else if(temp == -1){
						ATInfo.setUserName(ATInfo.getUserName()+"<br>"+
								DataFormat.getDateString(ATInfo.getApprovalDate()).substring(0,4)+
								"年"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(5,7)+
								"月"+DataFormat.getDateString(ATInfo.getApprovalDate()).substring(8,10)+"日");
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
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//查询审核意见信息
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
	 * 获得最后一个审核人姓名
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             审核设置标示
	 * @param       lApprovalContentID      审核内容标示
	 * @return      Collection              返回审核意见信息(ApprovalTracingInfo)
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

			//查询审核意见信息
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
	 * 删除审核意见信息
	 * 原则上应该都使用逻辑删除
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID             审核设置标示
	 * @param       lApprovalContentID      审核内容标示
	 * @param       lActionID               标识：1、物理删除，2、逻辑删除
	 * @return      long                    成功，返回值=1；失败，返回值=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws	Exception
	{
		Connection con = null;
		ApprovalDao approvaldao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		try
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			//con.setAutoCommit(false);
			approvaldao = new ApprovalDao(con);

			//删除审核意见信息
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
	 * 显示下一级审核人列表
	 * 支持重复分配人员、越级审核以及额度控制
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     输出
	 * @param       strFieldName            域的名称
	 * @param       strNextFieldName        下一个域的名称
	 * @param       lModuleID               模块标示
	 * @param       lLoanTypeID             业务类型标识
	 * @param       lActionID               操作标识
	 * @param       lUserID                 审核用户标示
	 * @param       lLevel                  当前审核级别
	 * @param       bIsLowLevel             是否走最少审批级别
	 * @return      long                    最后一级审核返回1；其他级别审核返回0；错误返回-1
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
		Vector vNextUser = new Vector(); //下一级审核人集合

		try 
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);

//			System.out.println("====查询审核设置信息====start==");
			//查询审核设置信息
			ASInfo = approvalsettingdao.findApprovalSetting(approvaldao.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID));

//			System.out.println("====查询审核设置信息====ok==");
			
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
			//如果该设置的状态不是已激活，则返回-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if (lLevel <= 0)
                {
//					System.out.println("===========level <= 0==========");
                    //当前审核人不在审批设置中，返回-2                    
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
					//最后一级审核
									
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
//					System.out.println("===========lLevel == 1 =====");
				    //当前审核人不在审批设置第一级中，返回-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//查询审核人员信息
					ASInfo = null;
					if (lIsPass == 1) //允许越级审核
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
						//没有设置下一级审核人员
						lReturn = -1;
					}
					else
					{
//						System.out.println("===========审核人员下拉列表显示====="); 
						//审核人员下拉列表显示控件
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				System.out.println("======设置的状态不是已激活=====lReturn = -1 ====="); 
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
	 * 查询用户审核级别
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            审核设置标示
	 * @param       lUserID                用户标识
	 * @return      long                   返回用户审核级别
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
	 * 审核人员下拉列表显示控件
	 * @param out                输出
	 * @param strFieldName       域的名称
	 * @param strFieldName       下一个域的名称
	 * @param NextUser           用户列表
	 * @param lDisplayFinish     是否显示“审核完成”：1 是；2 否；3 只显示“审核完成”
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
							out.println("<option value=\"-2\">审核完成</option>");
						}
				}
			}
			else
			{
				out.println("<option value=\"-2\">审核完成</option>");
			}
			out.println("</select>");
		}
		catch (Exception ex)
		{
		}
	}

	/**
	 * 审核人员下拉列表显示控件(审批设置未完成～）
	 * @param out                输出
	 * @param strFieldName       域的名称
	 * @param strFieldName       下一个域的名称
	 * @param nErrorNo       	 错误信息编号
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
				    strError = "审批设置未完成或没有激活！请检查！";
				    break;
				case 2:
				    strError = "当前审核人不在该审批设置中！请检查！";
				    break;
				case 3:
				    strError = "当前审核人不在该审批设置第一级中！请检查！";
				    break;
				case 4:
				    strError = "下一级审核人为空！请检查！";
				    break;
				default :
				    strError = "审批设置不正确！请检查！";
			}
		    out.println("<script language='javascript'>");
			out.println("alert('" + strError + "');");
			//Added by zwsun, 2007/10/12 不成功的时候后退
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
	 * lModuleID,lLoanTypeID,lActionID确定lApprovalID
	 * 支持通过相关设置增加的审批设置信息，如资金计划模块的部门设置
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lActionID
	 * @return      lApprovalID            返回审批设置信息
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

			//查询审批设置信息
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
	 * 查询可以做该审批的实际用户
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lLoanActionID
	 * @param       lUserID                用户标识
	 * @param       con                	   数据库连接
	 * @return      long                   返回用户审核级别
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
	 * 查询可以做该审批的实际用户
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID
	 * @param       lLoanTypeID
	 * @param       lLoanActionID
	 * @param       lUserID                用户标识
	 * @return      long                   返回用户审核级别
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
						 uinfo.setUserName("郝宾宾");
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
						 tinfo.setOpinion("呵呵");
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
	 * 查询审核意见信息
	 * 根据需要排序，推荐使用
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型
	 * @param       lLoanTypeID
	 * @param       lActionID               操作ID
	 * @param       lApprovalContentID      审核内容标示
	 * @return      Collection              返回审核意见信息(ApprovalTracingInfo)
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
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);

			//查询审核意见信息
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
	 * 显示下一级审核人列表 //: new function //:~
	 * 支持重复分配人员、越级审核以及额度控制
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     输出
	 * @param       strFieldName            域的名称
	 * @param       strNextFieldName        下一个域的名称
	 * @param       lModuleID               模块标示
	 * @param       lLoanTypeID             业务类型标识
	 * @param       lActionID               操作标识
	 * @param       lUserID                 审核用户标示
	 * @param       lLevel                  当前审核级别
	 * @param       bIsLowLevel             是否走最少审批级别
	 * @return      long                    最后一级审核返回1；其他级别审核返回0；错误返回-1
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
		Vector vNextUser = new Vector(); //下一级审核人集合

		try 
		{
			//connection 不能嵌套

			//con = Database.get_jdbc_connection();
			con = Database.getConnection();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);

//			System.out.println("====查询审核设置信息====start==");
			//查询审核设置信息
			ASInfo = approvalsettingdao.findApprovalSetting(approvaldao.getApprovalID1(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,amount));

//			System.out.println("====查询审核设置信息====ok==");
			
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
			//如果该设置的状态不是已激活，则返回-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				if (lLevel <= 0)
                {
//					System.out.println("===========level <= 0==========");
                    //当前审核人不在审批设置中，返回-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{
//					System.out.println("===========lLevel == lTotalLevel=====");
					//最后一级审核
					showUserList(out, strFieldName, strNextFieldName, null, 3);
					lReturn = 1;
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
//					System.out.println("===========lLevel == 1 =====");
				    //当前审核人不在审批设置第一级中，返回-2                    
	                showWrongUserList(out,strFieldName,strNextFieldName,3);
	                lReturn = -2;
				}
				else
				{
					//查询审核人员信息
					ASInfo = null;
					if (lIsPass == 1) //允许越级审核
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
						//没有设置下一级审核人员
						lReturn = -1;
					}
					else
					{
//						System.out.println("===========审核人员下拉列表显示====="); 
						//审核人员下拉列表显示控件
						showUserList(out, strFieldName, strNextFieldName,
									 vNextUser,
									 lIsPass);
						lReturn = 0;
					}
				}
			}
			else
			{
				System.out.println("======设置的状态不是已激活=====lReturn = -1 ====="); 
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
