/*
 * Created on 2006-12-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.approvaltran.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.evoucher.approvaltran.dao.Evo_ApprovalTranDao;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Evo_ApprovalTranBiz 
{
	/**
	 *打印申请的多笔查询操作
	*/
	public Collection findByMultiOption(long typeid,long officeid,long currencyid,long userid) throws VoucherException
	{
		Collection coll = null;
		Evo_ApprovalTranDao dao = new Evo_ApprovalTranDao();
		
		try
		{
			coll = dao.findSettConditions(typeid,officeid,currencyid,userid);
		}
		catch (Exception e)
		{
			throw new VoucherException();
		}
		return coll;
	}
	
	/**
	 * 电子回单柜申请审批使用  boxu add 2006-12-22
	 * 显示下一级审核人列表
	 * 支持重复分配人员、越级审核以及额度控制
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     输出
	 * @param       strFieldName            域的名称
	 * @param       strNextFieldName        下一个域的名称
	 * @param       lModuleID               模块标示
	 * @param       lLoanTypeID             业务类型标识
	 * @param       amount               	金额
	 * @param       lUserID                 审核用户标示
	 * @return      long                    最后一级审核返回1；其他级别审核返回0；错误返回-1
	 */
	public long showApprovalUserList(
	       JspWriter out, 
	       String strFieldName,
	       String strNextFieldName, 
	       long lModuleID,
		   long lOfficeID, 
		   long lCurrencyID,
		   long lUserID,
		   long ActionID,
		   long subtypeId
		) throws Exception
	{

		Connection con = null;
		ApprovalDao approvaldao = null;
		Evo_ApprovalTranDao dao = null;
		ApprovalSettingDao approvalsettingdao = null;
		boolean bSucceed = false;
		long lReturn = -1;

		ApprovalTracingInfo ATInfo = null;
		ApprovalSettingInfo ASInfo = null;
		long lApprovalID = -1;
		long lTotalLevel = -1;
		long lLevel = -1;
		long lIsPass = -1;		
		long lStatusID = -1;
		Vector vNextUser = new Vector(); //下一级审核人集合

		try 
		{
			con = Database.getConnection();
			dao = new Evo_ApprovalTranDao();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);
			
			//查询审核设置信息
			lApprovalID = dao.getApprovalId(lModuleID,lOfficeID,lCurrencyID,ActionID,subtypeId);
			
			lLevel = dao.getLevelId(lApprovalID,lUserID);
			ASInfo = approvalsettingdao.findApprovalSetting(lApprovalID);
		
			if (ASInfo != null)
			{
				lApprovalID = ASInfo.getID();
				lTotalLevel = ASInfo.getTotalLevel();
				lIsPass = ASInfo.getIsPass();
				lStatusID = ASInfo.getStatusID();
			}
			//如果该设置的状态不是已激活，则返回-1
			if(lStatusID == Constant.ApprovalStatus.SUBMIT)
			{
				//if(lLevel!=1)
				//{
					//当前审核人不在审批设置中，返回-2                    
                //    showWrongUserList(out,strFieldName,strNextFieldName,5);
                //    lReturn = -2;
				//}
				//else 
				if (lLevel <= 0)
                {
                    //当前审核人不在审批设置中，返回-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,2);
                    lReturn = -2;
                }
				else if (lLevel == lTotalLevel)
				{
					//最后一级审核
					showUserList(out, strFieldName, strNextFieldName, null, 3);
					lReturn = 1;
				}
				else if (lLevel == 1 && !approvaldao.checkApprovalUserLevel(lApprovalID, lUserID, lLevel))
				{
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
					        ASInfo = approvaldao.findApprovalItemAboveLevel(
									lApprovalID,
									lLevel,
									lUserID,									
									lTotalLevel,lCurrencyID,lOfficeID);					    
					}
					else
					{					     
					        ASInfo = approvaldao.findApprovalItem(lApprovalID,
									lLevel+1,
									lUserID,
									lCurrencyID,lOfficeID);					    
					}
					if (ASInfo != null)
					{ 
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
				case 5:
				    strError = "您不为第一级审批人！请检查！";
				    break;
				default :
				    strError = "审批设置不正确！请检查！";
			}
		    out.println("<script language='javascript'>");
			out.println("alert('" + strError + "');");
			out.println("</script>");
			out.println("<select class=\"box\" name=\"" + strFieldName +
						"\" onfocus=\"nextfield='" + strNextFieldName + "';\">");
			out.println("<option value=\"-1\"></option>");
			out.println("</select>");
		}
		catch (Exception ex)
		{
			throw new Exception();
		}
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
			throw new Exception();
		}
	}
	
	/**
	 * 根据审批结果来更新证卷业务记录的状态,同时处理审批记录表记录--add by Boxu
	 * 申请业务操作
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long updateDataStatusID(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		   long lretu=-1;
		   Evo_ApprovalTranDao dao = new Evo_ApprovalTranDao();
		   try
		   {
			   lretu = dao.updateDataStatusID(info,lResultID,tranTypeId,settID);
		   }
		   catch(Exception e)
		   {
		   		e.printStackTrace();
		   }
		   return lretu;
	}
	
}
