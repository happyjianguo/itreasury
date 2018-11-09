package com.iss.itreasury.settlement.account.action;

import java.util.Map;

import com.iss.itreasury.settlement.account.bizlogic.AccountBiz;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;



public class AccountAction {
	
	AccountBiz biz = new AccountBiz();
	/*
	 * �������������Ӳ���
	*/
	public PagerInfo findByConditions(Map map) throws Exception{
		
		PagerInfo pagerInfo = null;
		try 
		{
			QueryAccountConditionInfo qaci_1 = new QueryAccountConditionInfo();
			qaci_1.convertMapToDataEntity(map);
			
			String strAction = (String) map.get("straction");
			//��õ�¼��
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
	 * �޸Ĵ���������
	*/
   public PagerInfo findByConditionsModify(Map map) throws Exception{
	   String strNextPageURL = "";
		String strSuccessPageURL = "";
		String strFailPageURL = "";
		String strAction = "check";
		String strAction2 = "";
		String strControl = "";  //updateCheck�޸ĸ���
		String strQueryAction = "";  //���Ʊ���,���޸��˻���ѯ��ʹ��
		
		//�������
		long lOfficeID = -1; // ���´�ID
		long lCurrencyID = -1; //����
		long lInputUserID = -1;//�˻��ĸ�����Ӧ�������˻�¼������ͬ
		long lModuleID=-1;
		String strStartClientCode = ""; // ��ѯ�ɿͻ����
		String strEndClientCode = ""; // ��ѯ���ͻ����
		String strStartAccountCode = ""; // ��ѯ���˻����
		String strEndAccountCode = ""; // ��ѯ���˻����
		long lAccountTypeID = -1; //�˻�����
		long lCheckStatusID = -1; //����״̬
		long lStatusID = -1; //�˻�״̬
		long lBatchUpdateID = -1;//�����������
		
	   
		PagerInfo pagerInfo = null;
		try 
		{
			//��ȡ����
			String strTemp = null;
			strTemp = (String)map.get("strAction".toLowerCase());
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strAction = strTemp;
			}
			
			//updateCheck�޸ĸ���
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
			
			
			
			//���Ʊ���,���޸��˻���ѯ��ʹ��
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
			qaci.setStrAction(strQueryAction);  //���Ʊ���,���޸��˻���ѯ��ʹ��
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
