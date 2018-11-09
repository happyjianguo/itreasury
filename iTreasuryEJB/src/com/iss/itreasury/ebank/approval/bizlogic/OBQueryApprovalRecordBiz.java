package com.iss.itreasury.ebank.approval.bizlogic;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;


import com.iss.itreasury.ebank.approval.dao.OBQueryApprovalRecordDao;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.OBInutWorkInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;


public class OBQueryApprovalRecordBiz implements java.io.Serializable
{
	/**
     * ��ѯ����ҵ��
     */
    public Collection queryCurrentWork(OBInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
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
     * ��ѯ�Ѱ�ҵ��
     */
    public Collection queryHistoryWork(OBInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
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
     * ��ѯ���ҵ��
     */
    public Collection queryFinishedWork(OBInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
        	qInfo.setQueryPurpose(dao.QUERYFINISHEDWORK);
        	c_Return = dao.queryMyWork(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    
    /**
     * ��ѯ�ܾ�ҵ��
     * @throws Exception
     */
    public PageLoader getHistoryList(OBInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
    	PageLoader pageLoader=null;
    	 try
         {
    		 OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
         	pageLoader=dao.getHistoryList(conditionworkInfo,ldesc,lOrderField);
         } 
         catch (Exception e)
         {
             throw new Exception("Gen_E001", e);
         }
       
    		return pageLoader;
    }
    /**
     * Added by zwsun, 2007/7/26
     * ��ѯԤ��ܾ�ҵ��
     * @throws Exception
     */
    public PageLoader getHistoryListBudget(OBInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
    	PageLoader pageLoader=null;
    	 try
         {
    		 OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
         	pageLoader=dao.getHistoryListBudget(conditionworkInfo,ldesc,lOrderField);
         } 
         catch (Exception e)
         {
             throw new Exception("Gen_E001", e);
         }
       
    		return pageLoader;
    }   
    
    /**
     * Added by liangwang, 2007/7/26
     * ��ѯԤ��ܾ�ҵ��
     * @throws Exception
     */
    public PageLoader getHistoryListBankPay(OBInutWorkInfo conditionworkInfo,long ldesc,long lOrderField) throws Exception
    {
    	PageLoader pageLoader=null;
    	 try
         {
    		 OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
         	pageLoader=dao.getHistoryListBankPay(conditionworkInfo,ldesc,lOrderField);
         } 
         catch (Exception e)
         {
             throw new Exception("Gen_E001", e);
         }
       
    		return pageLoader;
    } 
    
    /**
     * ȡ������  ��Ϣ�б�
     * @throws Exception
     */
    public Collection queryWorkForCancelApprove(OBInutWorkInfo qInfo) throws Exception
    {
        Collection c_Return = null;
        
        try
        {
        	OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
        	c_Return = dao.queryWorkForCancelApprove(qInfo);		        			
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }
      
        return c_Return;
    }
    
    
	public String getPayAccountNOByIDForJSP(long accountID,long fixedInterestToAccountID,long transType) throws Exception
	{
		OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
		 return dao.getPayAccountNOByIDForJSP(accountID,fixedInterestToAccountID,transType);
	}
	public String getPayAccountNOByIDForJSP_BankPay(long accountID) throws Exception
	{
		OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
		 return dao.getPayAccountNOByIDForJSP_BankPay(accountID);
	}
     
	// ���������տ��Ϣ����ȡ�տ��˺�
	public String getRecAccountNOByIDForJSP(long accountID,long fixedInterestToAccountID,long transType) throws Exception
	{
		OBQueryApprovalRecordDao dao = new OBQueryApprovalRecordDao();
		 return dao.getRecAccountNOByIDForJSP(accountID,fixedInterestToAccountID, transType);
	}
    
    //��ʾ�������й�����������ҵ������
    public static void showAllRelatedTransTypeList(JspWriter out, String strControlName, long clientID,long currencyID, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank)
    {
    	try
        {
    		InutApprovalRelationBiz biz = new InutApprovalRelationBiz();
    		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
    		qInfo.setCurrencyID(currencyID);
    		qInfo.setClientID(clientID);
    		qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
    		Vector v = new Vector();
    		Collection c = biz.queryByCondition(qInfo);
    		
    		out.println("<select style='background-color:#ffffff;height:20px;' name=\"" + strControlName + "\" " + strProperty + ">");
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
            		// 	�������ͬ��ҵ������ ����ʾһ��
            		if(v.contains(String.valueOf(rInfo.getTransTypeID())))
            		{
            			continue;
            		}
            		if(rInfo.getTransTypeID()==lSelectValue)
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "' selected >" + OBConstant.SettInstrType.getName(rInfo.getTransTypeID())  + "</option>");
            		}
            		else
            		{
            			out.println("<option value='" + rInfo.getTransTypeID() + "'>" + OBConstant.SettInstrType.getName(rInfo.getTransTypeID()) + "</option>");
            		}
            		v.add(String.valueOf(rInfo.getTransTypeID()));
            	}	
            }	
            if (isNeedAll == true)
            {
                if (lSelectValue == 0)
                {
                    out.println("<option value='0' selected>ȫ��</option>");
                }
                else
                {
                    out.println("<option value='0'>ȫ��</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("��ʾ�����б�����쳣��" + ex.toString());
        }
    }
}
