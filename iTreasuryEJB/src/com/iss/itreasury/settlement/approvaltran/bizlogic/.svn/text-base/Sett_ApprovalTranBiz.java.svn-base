package com.iss.itreasury.settlement.approvaltran.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.settlement.approvaltran.dao.Sett_ApprovalTranDao;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dao.ApprovalDao;
import com.iss.itreasury.system.approval.dao.ApprovalSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalUserInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author yangliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_ApprovalTranBiz
{
	Sett_ApprovalTranDao dao = null;
    public Sett_ApprovalTranBiz(){
        dao = new Sett_ApprovalTranDao();
    }
    /**
	 *  根据条件查询需要登录人审批的记录(银行付款、内部转帐、委托存款)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
   public Collection findByAppBankConditions(TransCurrentDepositInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppBankConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
   }
   /**
	 *  根据条件查询需要登录人审批的记录(银行付款、内部转帐、委托存款)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
  public Collection findByAppGenerallConditions(TransGeneralLedgerInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppGenerallConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
  }
  /**
	 *  根据条件查询需要登录人审批的记录(特殊交易)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
public Collection findByAppCorpsConditions(TransSpecialOperationInfo info,long typeID,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByAppCorpsConditions(info,typeID,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
}
   /**
	 * 根据模块ID，业务类型ID，金额查询所属的审批流ID--add by 刘洋
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
   public long getApprovalId(long moduleid,long typeId,long currencyID,long officeID,double amount){
	   long lretu=-1;
	   try{
		   lretu=dao.getApprovalId(moduleid,typeId,currencyID,officeID,amount);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
   }
   /**
	 * 根据审批流ID，用户ID查出此用户的审批级别--add by 刘洋
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
   public long getLevelId(long approvalID,long userID){
	   long lretu=-1;
	   try{
		   lretu=dao.getLevelId(approvalID,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
   }
   /**
	 * 根据审批结果来更新结算业务记录的状态,同时处理审批记录表记录--add by 刘洋
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
  public long updateDataStatusID(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
	   long lretu=-1;
	   try{
		   lretu=dao.updateDataStatusID(info,lResultID,tranTypeId,settID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return lretu;
  }
  /**
   *  根据模块ID,业务类型ID，审批内容id等相关信息查找审批信息
   * @author chuanliu
   *
   * TODO To change the template for this generated type comment go to
   * Window - Preferences - Java - Code Style - Code Templates
   */
  public Collection findByCondition(ApprovalTracingInfo info) throws Exception
  {
      Collection Condition = null;
      try
      {
          Condition = dao.findByCondition(info);
      }
      catch(Exception e){e.printStackTrace();}
   return Condition;
  }
  /**
   * 根据条件查询需要登录人审批的记录(贴现贷款)
   * @author chuanliu
   *TransGrantDiscountInfo
   * TODO To change the template for this generated type comment go to
   * Window - Preferences - Java - Code Style - Code Templates
   */
  public Collection findByDISCOUNTGRANTConditions(TransGrantDiscountInfo info,long userID) throws Exception
  {
      Collection Condition = null;
      try
      {
          Condition = dao.findByDISCOUNTGRANTConditions(info,userID);
      }
      catch(Exception e){e.printStackTrace();}
   return Condition;
  }
  /**
	 *  根据条件查询需要登录人审批的记录(自营贷款、委托贷款)--add by 刘川
	 * @param TransGrantLoanInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
  public Collection findByTrustConditions(TransGrantLoanInfo info,long userID){
	   Collection Cretu=null;
	   try{
		   Cretu=dao.findByTrustConditions(info,userID);		   
	   }catch(Exception e){e.printStackTrace();}
	   return Cretu;
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
	 * @param       amount               	金额
	 * @param       lUserID                 审核用户标示
	 * @param       dataStatusID            当前状态
	 * @return      long                    最后一级审核返回1；其他级别审核返回0；错误返回-1
	 */
	public long showApprovalUserList(
	        JspWriter out, 
	        String strFieldName,
	        String strNextFieldName, 
	        long lModuleID,
			long lLoanTypeID, 
			double amount,
			long lOfficeID, 
			long lCurrencyID,
			long lUserID,
			long dataStatusID
		) throws Exception
	{

		Connection con = null;
		ApprovalDao approvaldao = null;
		Sett_ApprovalTranDao settdao = null;
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
			//connection 不能嵌套
			con = Database.getConnection();
			settdao = new Sett_ApprovalTranDao();
			approvaldao = new ApprovalDao(con);
			approvalsettingdao = new ApprovalSettingDao(con);
			//查询审核设置信息
			lApprovalID = settdao.getApprovalId(lModuleID,lLoanTypeID,lOfficeID,lCurrencyID,amount);
			lLevel = settdao.getLevelId(lApprovalID,lUserID);
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
				if(dataStatusID==SETTConstant.TransactionStatus.APPROVALING && lLevel!=1)
				{
					 //当前审核人不在审批设置中，返回-2                    
                    showWrongUserList(out,strFieldName,strNextFieldName,5);
                    lReturn = -2;
				}else if (lLevel <= 0)
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
		}
	}
}