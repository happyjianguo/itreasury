package com.iss.itreasury.settlement.account.action;

import java.util.Map;

import com.iss.itreasury.settlement.account.bizlogic.AccountBiz;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;



public class AccountAction {
	
	AccountBiz biz = new AccountBiz();
	/*
	 * 开户处理——连接查找
	*/
	public PagerInfo findByConditions(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		try 
		{
			QueryAccountConditionInfo qaci_1 = new QueryAccountConditionInfo();
			qaci_1.convertMapToDataEntity(map);
			
			String strAction = (String) map.get("straction");
			//获得登录人
			long lUserID = Long.parseLong(map.get("luserid").toString());
			
			long lCheckStatusID = Long.parseLong(map.get("lCheckStatusID".toLowerCase()).toString());
			
			long lAccountTypeID = Long.parseLong(map.get("lAccountTypeID".toLowerCase()).toString());
					
			
			if(strAction != null && ( strAction.equals("check") || strAction.equals("CancelCheck") ))
			{
				qaci_1.setCheckUserID(lUserID);
			}
			else if(strAction != null && (strAction.equals("LinkQuery")|| strAction.equals("delete")))
			{
				qaci_1.setInputUserID(lUserID);
			}
			else if(strAction != null && strAction.equals("MODIFYSAVE"))
			{
				qaci_1.setInputUserID(lUserID);
				
			    if(lCheckStatusID==4)
			    {
			    	qaci_1.setStrQuery(strAction);
				 }
			}
			qaci_1.setCheckStatusID(lCheckStatusID);
			qaci_1.setAccountTypeID(lAccountTypeID);
			
			pagerInfo = biz.findByConditions(qaci_1);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	/*
	 * 修改处理——查找
	*/
   public PagerInfo findByConditionsModify(Map map) throws Exception{
	   String strNextPageURL = "";
		String strSuccessPageURL = "";
		String strFailPageURL = "";
		String strAction = "check";
		String strAction2 = "";
		String strControl = "";  //updateCheck修改复核
		String strQueryAction = "";  //控制变量,在修改账户查询后使用
		
		//定义变量
		long lOfficeID = -1; // 办事处ID
		long lCurrencyID = -1; //币种
		long lInputUserID = -1;//账户的复核人应不能与账户录入人相同
		long lModuleID=-1;
		String strStartClientCode = ""; // 查询由客户编号
		String strEndClientCode = ""; // 查询至客户编号
		String strStartAccountCode = ""; // 查询由账户编号
		String strEndAccountCode = ""; // 查询至账户编号
		long lAccountTypeID = -1; //账户类型
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态
		long lBatchUpdateID = -1;//批量复核序号
		
	   
		PagerInfo pagerInfo = null;
		try 
		{
			//读取数据
			String strTemp = null;
			strTemp = (String)map.get("strAction".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAction = strTemp;
			}
			
			//updateCheck修改复核
			strTemp = (String)map.get("strControl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strControl = strTemp;
			}
			
			strTemp = (String)map.get("lOfficeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lOfficeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lModuleID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lModuleID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lCurrencyID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCurrencyID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lClientIDStartCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strStartClientCode = strTemp;
			}
			strTemp = (String)map.get("lClientIDEndCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strEndClientCode = strTemp;
			}
			strTemp = (String)map.get("lAccountIDStartCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				if(strTemp.trim().length()>8)
				{
					strStartAccountCode = strTemp;
				}
			}
			strTemp = (String)map.get("lAccountIDEndCtrl".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				if(strTemp.trim().length()>8)
				{
					strEndAccountCode = strTemp;
				}
			}
			strTemp = (String)map.get("lAccountTypeID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lAccountTypeID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)map.get("lCheckStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCheckStatusID = Long.valueOf(strTemp).longValue();
				/*
				if(lCheckStatusID == SETTConstant.AccountCheckStatus.OLDSAVE && strAction.equals("check"))
				{
				  InutParameterInfo pInfo = new InutParameterInfo();
	              pInfo.setModuleID(lModuleID);
				  pInfo.setOfficeID(lOfficeID);
				  pInfo.setCurrencyID(lCurrencyID);
				  pInfo.setTransTypeID(SETTConstant.TransactionType.ACCOUNTMODIFY);
				  lCheckStatusID = FSWorkflowManager.getSettCheckStatus(pInfo);
				}*/
			}
			/*else
			{
			    InutParameterInfo pInfo = new InutParameterInfo();
	            pInfo.setModuleID(lModuleID);
				pInfo.setOfficeID(lOfficeID);
				pInfo.setCurrencyID(lCurrencyID);
				pInfo.setTransTypeID(SETTConstant.TransactionType.ACCOUNTOPEN);
				lCheckStatusID = FSWorkflowManager.getSettCheckStatus(pInfo);
			}*/
			
			System.out.println("-------------c023.jsp---------lCheckStatusID------:"+lCheckStatusID);
			
			strTemp = (String)map.get("lStatusID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lStatusID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)map.get("lBatchUpdateID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lBatchUpdateID = Long.valueOf(strTemp).longValue();
			}
			
			
			
			//控制变量,在修改账户查询后使用
			strTemp = (String)map.get("strQueryAction".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strQueryAction = strTemp;
			}
			strTemp = (String)map.get("lInputUserID".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lInputUserID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)map.get("strAction2".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAction2 = strTemp;
			}
			
	        QueryAccountConditionInfo qaci = new QueryAccountConditionInfo();
			
	        qaci.setOfficeID(lOfficeID);
			qaci.setCurrencyID(lCurrencyID);
			
			if(strAction != null && ( strAction.equals("check") || strAction.equals("CancelCheck") ))
			{
				qaci.setCheckUserID(lInputUserID);
			}
			else if(strAction != null && (strAction.equals("LinkQuery")|| strAction.equals("delete")))
			{
				qaci.setInputUserID(lInputUserID);
			}
			else if(strAction != null && strAction.equals("MODIFYSAVE"))
			{
			    qaci.setInputUserID(lInputUserID);
			    if(lCheckStatusID==4)
			    {
			  		 qaci.setStrQuery(strAction);
				 }
			}
			
			qaci.setEndAccountCode(strEndAccountCode);
			qaci.setEndClientCode(strEndClientCode);
			qaci.setStartAccountCode(strStartAccountCode);
			qaci.setStartClientCode(strStartClientCode);
			qaci.setAccountTypeID(lAccountTypeID);
			qaci.setCheckStatusID(lCheckStatusID);
			qaci.setStatusID(lStatusID);
			qaci.setBatchUpdateID(lBatchUpdateID);
			qaci.setStrAction(strQueryAction);  //控制变量,在修改账户查询后使用
			System.out.println("------------lCheckStatusID--------------------"+lCheckStatusID);
			

			pagerInfo = biz.findByConditionsModify(qaci);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
	
	

}
