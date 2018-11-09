/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,该功能实现查询我的工作  
 *      
 */
package com.iss.itreasury.settlement.mywork.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;


import com.iss.itreasury.settlement.mywork.dao.SettInutWorkDao;

import com.iss.itreasury.settlement.mywork.dataentity.SettInutWorkInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;


public class SettInutWorkBiz implements java.io.Serializable
{
	/**
     * 查询待办业务
     */
    public Collection queryCurrentWork(SettInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYCURRENTWORK);
        	c_Return = dao.queryMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    /**
     * 查询累计费用待办业务
     */
    public Collection queryAdjustCurrentWork(SettInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYCURRENTWORK);
        	c_Return = dao.queryAdjustMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    /**
     * 查询账户待办业务
     */
    public Collection queryAccountCurrentWork(SettInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYCURRENTWORK);
        	c_Return = dao.queryAccountMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    /**
     * 查询已办业务
     */
    public Collection queryHistoryWork(SettInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYHISTORYWORK);
        	c_Return = dao.queryMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    /**
     * 查询办结业务
     */
    /*
    public Collection queryFinishedWork(SettInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	qInfo.setQueryPurpose(dao.QUERYFINISHEDWORK);
        	c_Return = dao.queryMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    */
    /**
	 * 查询办结业务
	 * @param SettInutWorkInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryFinishedWork(SettInutWorkInfo conditionworkInfo) throws Exception
	{   
		PageLoader pageLoader=null;
   	 	try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	pageLoader=dao.queryFinishedWork(conditionworkInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
   		return pageLoader;
	}
    /**
	 * 查询账户办结业务
	 * @author haoliang
	 * @param SettInutWorkInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountFinishedWork(SettInutWorkInfo conditionworkInfo) throws Exception
	{   
		PageLoader pageLoader=null;
   	 	try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	pageLoader=dao.queryAccountFinishedWork(conditionworkInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
   		return pageLoader;
	}
	
    //显示结算所有关联审批流的业务类型
    public static void showAllRelatedTransTypeList(JspWriter out, String strControlName, long officeID,long currencyID, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank)
    {
    	try
        {
    		InutApprovalRelationBiz biz = new InutApprovalRelationBiz();
    		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
    		qInfo.setCurrencyID(currencyID);
    		qInfo.setOfficeID(officeID);
    		qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
    		
    		Collection c = biz.queryMyworkByCondition(qInfo);
    		
    		out.println("<select name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (lSelectValue == -1)
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
            if(c!=null && c.size()>0)
            {
            	Iterator it = c.iterator();
            	while(it.hasNext())
            	{
            		InutApprovalRelationInfo rInfo = (InutApprovalRelationInfo)it.next();
            		if(rInfo.getTransTypeID()==lSelectValue)
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "' selected >" + SETTConstant.TransactionType.getName(rInfo.getTransTypeID()) + "</option>");
            		}
            		else
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "'>" + SETTConstant.TransactionType.getName(rInfo.getTransTypeID()) + "</option>");
            		}	
            	}	
            }	
            if (isNeedAll == true)
            {
                if (lSelectValue == 0)
                {
                    out.println("<option value='0' selected>全部</option>");
                }
                else
                {
                    out.println("<option value='0'>全部</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }
    
    
    //显示结算所有关联审批流的账户业务类型
    public static void showAllAccountRelatedTransTypeList(JspWriter out, String strControlName, long officeID,long currencyID, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank)
    {
    	try
        {
    		InutApprovalRelationBiz biz = new InutApprovalRelationBiz();
    		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
    		qInfo.setCurrencyID(currencyID);
    		qInfo.setOfficeID(officeID);
    		qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
    		
    		Collection c = biz.queryByAccountCondition(qInfo);
    		
    		out.println("<select name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (lSelectValue == -1)
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
            if(c!=null && c.size()>0)
            {
            	Iterator it = c.iterator();
            	while(it.hasNext())
            	{
            		InutApprovalRelationInfo rInfo = (InutApprovalRelationInfo)it.next();
            		if(rInfo.getTransTypeID()==lSelectValue)
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "' selected >" + SETTConstant.TransactionType.getName(rInfo.getTransTypeID()) + "</option>");
            		}
            		else
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "'>" + SETTConstant.TransactionType.getName(rInfo.getTransTypeID()) + "</option>");
            		}	
            	}	
            }	
            if (isNeedAll == true)
            {
                if (lSelectValue == 0)
                {
                    out.println("<option value='0' selected>全部</option>");
                }
                else
                {
                    out.println("<option value='0'>全部</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }
    
    
    
    /**
     * 查询拒绝账户业务
     * @throws Exception 
     */
    public PageLoader queryAccountRefuseWork(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
    	PageLoader pageLoader=null;
    	 try
         {
         	SettInutWorkDao dao = new SettInutWorkDao();
         	pageLoader=dao.queryAccountRefuseWork(conditionworkInfo,ldesc,lOrderField);
         } 
         catch (Exception e)
         {
             throw new Exception("Gen_E001", e);
         }
       
    		return pageLoader;
    }
    /**
     * 查询拒绝业务
     * @throws Exception 
     */
    public PageLoader queryRefuseWork(SettInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
    	PageLoader pageLoader=null;
    	 try
         {
         	SettInutWorkDao dao = new SettInutWorkDao();
         	pageLoader=dao.queryRefuseWork(conditionworkInfo,ldesc,lOrderField);
         } 
         catch (Exception e)
         {
             throw new Exception("Gen_E001", e);
         }
       
    		return pageLoader;
    }
    
    /**
	 * 取消审批查询（如果自动复核，则查询登录人复核的当日已复核交易，如果是手动复核，则查询登录人为最后一级审批人的当日已审批交易）
	 * @param SettInutWorkInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryCancelApproval(SettInutWorkInfo conditionworkInfo) throws Exception
	{   
		PageLoader pageLoader=null;
   	 	try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	pageLoader=dao.queryCancelApproval(conditionworkInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
   		return pageLoader;
	}
	
	/**
	 * 取消账户审批查询
	 * @param SettInutWorkInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountCancelApproval(SettInutWorkInfo conditionworkInfo) throws Exception
	{   
		PageLoader pageLoader=null;
   	 	try
        {
        	SettInutWorkDao dao = new SettInutWorkDao();
        	pageLoader=dao.queryAccountCancelApproval(conditionworkInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
   		return pageLoader;
	}
}
