/*
 * Created on 2007-4-16
 *
 * Title:				iTreasury
 * @author             	���� 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,��������ӿ�        
 */

package com.iss.itreasury.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.iss.inut.workflow.constants.Constants;
import com.iss.inut.workflow.constants.StateEnum;
import com.iss.inut.workflow.entity.po.OsCurrentstep;
import com.iss.inut.workflow.entity.po.OsHistorystep;
import com.iss.inut.workflow.entity.po.OsWfdefine;
import com.iss.inut.workflow.ws.WorkflowManager;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRecordBiz;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.history.bizlogic.HistoryBiz;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;

public class FSWorkflowManager 
{
	/*
	 * �ύ��������
	 * ����:��ϵͳ��ҵ���ύ�ĵ�������
	 * ����:1.���ݲ������Ҹ�ҵ�������������  2.��������������ĳ�ʼ���ӿ�,��ҵ���ύ����������
	 * @param       transtypeid      	������ҵ���ҵ������
	 * 				actionid			������ҵ��Ĳ�������(һ��ֻ�д���\֤ȯ��ģ����Ҫ��������������ҵ��Ĳ�ͬ����ʱ���õ�,����������Դ�"-1")
	 * 				String strUrl		������ϵͳ�е�"����ҵ���ѯ"��,�����ѯ���Ĵ���ҵ��ʱ������(��ҵ������¼��ҳ��,����ҵ����ϸ����������ı�ǩ)
	 * 				request				��Ҫ���������������洫��ҵ������(request���и�ҵ����������,ת��Ϊmap,���ݸ�����������)
	 * 				sessionMng			ϵͳsession,������¼�û�,���´�,����,ģ�����Ϣ
	 * @return      long        		��������ʵ��id
	 */
	public static long initApproval(InutParameterInfo pInfo)throws IException
	{
		long lReturn = -1;
		com.iss.itreasury.util.SessionMng sessionMng = null;		
		WorkflowManager wfManager = null; 
		String strUserId="-1";
		
		try
		{		
			if(pInfo != null)
			{
				sessionMng = pInfo.getSessionMng();
				strUserId=String.valueOf(sessionMng.m_lUserID);
			}	
		
			InutApprovalRelationBiz arbiz=new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo=new InutApprovalRelationInfo();
			
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setTransTypeID(pInfo.getTransTypeID());
			qInfo.setActionID(pInfo.getActionID());					
		
			//���Ҹ�ҵ�������������
			long lApprovalID=arbiz.findApprovalID(qInfo);
					
			if(lApprovalID < 0)
			{
				throw new IRuntimeException("��ҵ��δ����������,����!");
			}
				
			//����ҵ������map,���ݸ����������棬pInfo������ҵ��info��
			HashMap hm=new HashMap();			
			switch ((int)(pInfo.getModuleID()))
			{
				case (int)Constant.ModuleType.SETTLEMENT:
					hm.putAll( FSWorkflowEntityConverter.entity2Map(pInfo));
					break;
				case (int)Constant.ModuleType.LOAN:
					//modified by kevin (������)2011-05-25
					//hm.putAll( FSWorkflowEntityConverter.entity2LoanMap(pInfo));
					hm.putAll( FSWorkflowEntityConverter.LoanMap(pInfo));
					break;
			}
			
			//��ʼ������������
			wfManager=WorkflowManager.instance(String.valueOf(pInfo.getUserID())); 
			//��ʼ����
			//wfManager.beginTransaction();
			//��ʼ������
			lReturn = wfManager.initialize(lApprovalID,0,pInfo.getUrl(),hm);			
			//�����ύ������¼
			if(lReturn>0)
			{
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				
				recordInfo.setOfficeID(pInfo.getOfficeID());
				recordInfo.setCurrencyID(pInfo.getCurrencyID());
				recordInfo.setModuleID(pInfo.getModuleID());				
				recordInfo.setTransTypeID(pInfo.getTransTypeID());
				recordInfo.setActionID(pInfo.getActionID());
				recordInfo.setTransID(pInfo.getTransID());
				recordInfo.setApprovalEntryID(lReturn);
				recordInfo.setLinkUrl(pInfo.getUrl());
				recordInfo.setStatusID(Constant.RecordStatus.VALID);
				recordBiz.save(recordInfo);	
				//����ύ��¼
				//add by keivn(������)2011-05-19
			 	HistoryBiz historyBiz=new HistoryBiz();
			 	HistoryAdviseInfo historyAdviseInfo=new HistoryAdviseInfo();
			 	historyAdviseInfo.setOperator(strUserId);
			 	historyAdviseInfo.setOpTime(new Timestamp(new Date().getTime()));
			 	historyAdviseInfo.setActionKey(String.valueOf(Constant.Actions.COMMIT));
			 	historyAdviseInfo.setAction(Constant.Actions.getName(Constant.Actions.COMMIT));	
			 	historyAdviseInfo.setEntryID(lReturn);
			 	historyAdviseInfo.setAdvise(pInfo.getAdvise());
			 	historyAdviseInfo.setStatusID(Constant.RecordStatus.VALID);
			 	long tempid=historyBiz.save(historyAdviseInfo);
			 	if(tempid<0){
			 		 Log.print("--�����ύ��¼ʧ��--");
			 		 throw new IRuntimeException("�ύ����ʧ��,����!");
			 	}
				
			 	
			}
			else
			{
				throw new IRuntimeException("�ύ����ʧ��,����!");
			}
			//�ύ
			//wfManager.commit();
			
			return lReturn;
		}
		catch(IRuntimeException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRuntimeException("Gen_E001",e);
		}
		finally
		{
			try
			{
				if(wfManager!=null)
				{
					wfManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
		}
	}
	
	/*
	 * ��������
	 * ����:����ϵͳ��ҵ����Ϣ
	 * ����:��������������������ӿ�,��������ҵ��
	 * @param       strUrl      	��һ����ҳ��(������������ɺ󷵻ص�ҳ��)
	 * 				request			request,���������������Ϣ
	 * 				sessionMng		ϵͳsession,���������Ϣ
	 * @return      long        	�ɹ�������������IDֵ��ʧ�ܣ�����ֵ=-1
	 */
	public static InutParameterInfo doApproval(InutParameterInfo pInfo)throws IException
	{
		InutParameterInfo returnInfo = pInfo;
		WorkflowManager workflowManager = null;
		
		try
		{			
			//����ʵ��id,��request�л�ȡ(�������ҵ������ʱ,request���Ѿ��и���Ϣ)
			long wfInstanceId = Long.parseLong((String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_INSTANCEID));
			//�������,��request�л�ȡ(�������ҵ������ʱ,request���Ѿ��и���Ϣ)
			int wfActionId = Integer.parseInt((String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ACTIONID));	
			//�������,��request�л�ȡ
			String osAdvise = (String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ADVISE);
			//�Ƿ�������ͨ��
			String ispass = (String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ISPASS);
			
			//����ҵ������map,���ݸ����������棬pInfo������ҵ��info��
			HashMap hm=new HashMap();
			hm.put(com.iss.inut.workflow.constants.Constants.WF_TASKID,(String)pInfo.getRequestMap().get(Constants.WF_TASKID));	
			hm.put(com.iss.inut.workflow.constants.Constants.WF_ADVISE,osAdvise);
			
			switch ((int)(pInfo.getModuleID()))
			{
				case (int)Constant.ModuleType.SETTLEMENT:
					hm.putAll( FSWorkflowEntityConverter.entity2Map(pInfo));
					break;
				case (int)Constant.ModuleType.LOAN:
					//modified by kevin (������)2011-05-25
					//hm.putAll( FSWorkflowEntityConverter.entity2LoanMap(pInfo));
				    hm.putAll( FSWorkflowEntityConverter.LoanMap(pInfo));
					break;
			}
			//��ʼ������������
		    workflowManager=WorkflowManager.instance(String.valueOf(pInfo.getUserID()));
		    //����ʼ
		    //workflowManager.beginTransaction();
		    //��������
			StateEnum se=workflowManager.doAction(wfInstanceId,wfActionId,pInfo.getUrl(),hm);
			
			/*
			 * �ж��Ƿ����һ������ͨ�������һ�������ܾ�
			 * se.equals(se.COMPLETED)�ж��Ƿ����һ����ispass.equals("0")�ж�������ͨ�����������ܾ�			  
			 */			
			if(se.equals(se.COMPLETED) && ispass.equals("0"))		//���һ������ͨ��
			{
				returnInfo.setLastLevel(true);				
				//��¼���һ��������
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				recordInfo.setApprovalEntryID(wfInstanceId);
				recordInfo.setLastAppUserID(pInfo.getUserID());							
				recordBiz.updateByApprovalEntryID(recordInfo);								
			}			
			else if(se.equals(se.COMPLETED) && !ispass.equals("0"))	//���һ�������ܾ�
			{
				returnInfo.setRefuse(true);
				//ɾ��������¼���߼�ɾ����
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				recordInfo.setApprovalEntryID(wfInstanceId);
				recordInfo.setStatusID(Constant.RecordStatus.INVALID);//״̬��Ч							
				recordBiz.updateByApprovalEntryID(recordInfo);
			}	
			
			//�����ύ
			//workflowManager.commit();
			
			return returnInfo;
		}
		catch(IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//modified by wjliu 2007-8-9
			//throw new IException("Gen_E001",e);
			
			throw new IException("����ʧ��");
		}
		finally
		{
			try
			{
				if(workflowManager!=null)
				{
					workflowManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
		}
	}
	
	/*
	 * �жϸ�ҵ�������Ƿ���Ҫ����
	 * ����:����ҵ�����ݺ��������������жϸ�ҵ���Ƿ���Ҫ��������
	 * ����:����ҵ�����ݲ��������������ñ�,�����ҵ�����������������,���ʾ��ҵ����Ҫ��������,��֮,����Ҫ
	 * @param       strUrl      	��һ����ҳ��(������������ɺ󷵻ص�ҳ��)
	 * 				request			request,���������������Ϣ
	 * 				sessionMng		ϵͳsession,���������Ϣ
	 * @return      boolean        	��Ҫ������true������Ҫ������false
	 */
	public static boolean isNeedApproval(InutParameterInfo pInfo) throws IException
	{
		boolean bReturn = true;
		long lApprovalID = -1;
		try
		{
			//��ʼ����ѯ��Ͳ�����
			InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
			//���������
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setTransTypeID(pInfo.getTransTypeID());
			qInfo.setActionID(pInfo.getActionID());
			//����������ѯ��ҵ���Ƿ���������
			lApprovalID = iaBiz.findApprovalID(qInfo);
			
			if(lApprovalID>0)
			{
				bReturn = true;		//������������true
			}
			else
			{
				bReturn = false;	//û������������false
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return bReturn;
	}

	/*
	 * ��ȡҵ�񸴺�ʱ,��Ҫƥ���ҵ��״̬,��ȡ�����˺�,��Ҫ�ָ���ҵ��״̬
	 * ����:����ҵ������,ȡ��ƥ��ʱӦƥ���ҵ��״̬��ȡ�����˺�Ӧ�ûָ���ҵ��״̬
	 * ����:����ҵ�����ݲ��������������ñ�,�����ҵ�����������������,���ʾ��ҵ����Ҫ��������,ƥ��ʱ��Ҫƥ��"������"״̬,ȡ��������ָ�"������"״̬
	 * 	   �����ҵ��û�й���������,���ҵ��û����������,ƥ��ʱ��Ҫƥ��"�ѱ���"״̬,ȡ��������ָ�"�ѱ���"״̬	
	 * @param       strUrl      	��һ����ҳ��(������������ɺ󷵻ص�ҳ��)
	 * 				request			request,���������������Ϣ
	 * 				sessionMng		ϵͳsession,���������Ϣ
	 * @return      long        	���������ķ���"������",��֮�򷵻�"�ѱ���"
	 */
	public static long getSettCheckStatus(InutParameterInfo pInfo) throws Exception
	{
		long lStatus = -1;
		long lApprovalID = -1;
		try
		{
			//��ʼ����ѯ��Ͳ�����
			InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
			//���������
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setTransTypeID(pInfo.getTransTypeID());
			qInfo.setActionID(pInfo.getActionID());
			//����������ѯ��ҵ���Ƿ���������
			lApprovalID = iaBiz.findApprovalID(qInfo);
			
			if(lApprovalID>0)
			{				
				if(qInfo.getTransTypeID() == SETTConstant.TransactionType.ACCOUNTOPEN)//�ʻ�����
				{
					lStatus = SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED;
				}
				else if(qInfo.getTransTypeID() == SETTConstant.TransactionType.ACCOUNTMODIFY)//�ʻ��޸�
				{
					lStatus = SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED;
				}
				else//��̨ҵ��
				{	
					lStatus = SETTConstant.TransactionStatus.APPROVALED;	//������������"������"״̬
				}	
			}
			else
			{
				if(qInfo.getTransTypeID() == SETTConstant.TransactionType.ACCOUNTOPEN)//�ʻ�����
				{
					lStatus = SETTConstant.AccountCheckStatus.NEWSAVE;
				}
				else if(qInfo.getTransTypeID() == SETTConstant.TransactionType.ACCOUNTMODIFY)//�ʻ��޸�
				{
					lStatus = SETTConstant.AccountCheckStatus.OLDSAVE;
				}
				else//��̨ҵ��
				{	
					lStatus = SETTConstant.TransactionStatus.SAVE;		//������������"�ѱ���"״̬
				}				
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lStatus;
	}	
	
	/*
	 * �����ж��Ƿ��Զ�����
	 * ����:����������,�ж��Ƿ������һ������ʱ�Զ�����
	 * ����:������ο�settlement.xml:sett_transapproval_autocheck
	 * @param       
	 * @return      boolean        �Զ����ˣ�true,���Զ�����:false
	 */
	public static boolean isAutoCheck()
	{
		boolean bReturn = false;
		
		bReturn = Config.getBoolean(ConfigConstant.SETT_TRANSAPPROVAL_AUTOCHECK,false);
		
		return bReturn;
	}
	
	/*
	 * ��ȡ�����������������Ч��������
	 * ����:��ȡ�����������������Ч��������
	 * ����:
	 * @param       officeID		���´�id
	 * 				userID			�û�id
	 * @return      HashMap        	key:������id,value:����������,����������������
	 */
	public static HashMap getAllApproval(long userID,long officeID) throws Exception
	{
		HashMap m_Result = new HashMap();
		
		WorkflowManager workflowManager=WorkflowManager.instance(String.valueOf(-1));
		
		//Modify by leiyang 20081209
		//��ϵͳ�н��������������������������и�������������ͬ��Ϊ���������
		//FSWorkflowManager�ڵ��ô˷���ʱ��ʹ����Ioc(���Ʒ�ת)��ʹ�䲻�������������ֿ���ͨ�������ĸ���������
		List list = null;
		Method method = workflowManager.getClass().getMethod("getListById", new Class[]{String.class, int.class, int.class});
		if(method != null){
			list = (List)method.invoke(workflowManager, new Object[]{String.valueOf(officeID), new Integer(1), new Integer(10000000)});
		}
		//List list = workflowManager.getListById(String.valueOf(officeID),1,10000000);
		
		if(list!=null && list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				OsWfdefine info = (OsWfdefine)list.get(i);
				m_Result.put(String.valueOf(info.getId()), info.getName());
			}	
		}	

		workflowManager.closeSession();

		//m_Result.get(key)
		return m_Result;
	}
	
	/*
	 * ��ȡ��ǰ�û��Ĵ���ҵ��
	 * ����:��������������ӿ�,��ѯ��ǰ�û��Ĵ���ҵ��(������ģ��,ҵ������)
	 * ����:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			keyΪ����ʵ��id,valueΪInutApprovalWorkInfo
	 */
	public static HashMap getCurrentList (InutParameterInfo pInfo) throws Exception
	{
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		try
		{
			workflowManager=WorkflowManager.instance(String.valueOf(pInfo.getUserID()));			
			List list = workflowManager.getAvailableSteps(1, 100000);
			
			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsCurrentstep info = (OsCurrentstep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//����id
					workInfo.setEntryID(info.getOsWfentry().getId());		//����ʵ��id
					workInfo.setActionID(info.getActionId());				//��������id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//��������id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//����ʵ������
					workInfo.setStepName(info.getStepName());				//������������
					workInfo.setDueDate(info.getDueDate());					//�������ʱ��
					workInfo.setStartDate(info.getStartDate());				//������ʼʱ��					
					workInfo.setStatus(info.getStatus());					//����״̬
					workInfo.setOwner(info.getOwner());						//�Ͱ���
					workInfo.setWfDefineName(info.getOsWfentry().getOsWfdefine().getName());
					hm.put(String.valueOf(info.getOsWfentry().getId()), workInfo);
				}	
			}					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(workflowManager!=null)
				{
					workflowManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
		}
		return hm;
	}
	
	/*
	 * ��ȡ��ǰ�û����Ѱ�ҵ��
	 * ����:��������������ӿ�,��ѯ��ǰ�û����Ѱ�ҵ��(������ģ��,ҵ������)
	 * ����:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			keyΪ����ʵ��id,valueΪInutApprovalWorkInfo
	 */
	public static HashMap getHistoryList (InutParameterInfo pInfo) throws Exception
	{
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		try
		{
			workflowManager=WorkflowManager.instance(String.valueOf(pInfo.getUserID()));
			List list = workflowManager.getHistoryList(1, 100000);
			
			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsHistorystep info = (OsHistorystep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//����id
					workInfo.setEntryID(info.getOsWfentry().getId());		//����ʵ��id
					workInfo.setActionID(info.getActionId());				//��������id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//��������id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//����ʵ������
					workInfo.setStepName(info.getStepName());				//������������
					workInfo.setDueDate(info.getDueDate());					//�������ʱ��
					workInfo.setStartDate(info.getStartDate());				//������ʼʱ��					
					workInfo.setStatus(info.getStatus());					//����״̬
					workInfo.setOwner(info.getOwner());						//�Ͱ���
					workInfo.setWfDefineName(info.getOsWfentry().getOsWfdefine().getName());
										
					hm.put(String.valueOf(info.getOsWfentry().getId()), workInfo);
				}	
			}					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(workflowManager!=null)
				{
					workflowManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
		}
		return hm;
	}

	/*
	 * ��ȡ��ǰ�û��İ��ҵ��
	 * ����:��������������ӿ�,��ѯ��ǰ�û��İ��ҵ��(������ģ��,ҵ������)
	 * ����:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			keyΪ����ʵ��id,valueΪInutApprovalWorkInfo
	 */
	public static HashMap getFinishedList (InutParameterInfo pInfo) throws Exception
	{
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		try
		{
			workflowManager=WorkflowManager.instance(String.valueOf(pInfo.getUserID()));
			List list = workflowManager.getFinishedList(1, 100000);

			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsHistorystep info = (OsHistorystep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//����id
					workInfo.setEntryID(info.getOsWfentry().getId());		//����ʵ��id
					workInfo.setActionID(info.getActionId());				//��������id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//��������id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//����ʵ������
					workInfo.setStepName(info.getStepName());				//������������
					workInfo.setDueDate(info.getDueDate());					//�������ʱ��
					workInfo.setStartDate(info.getStartDate());				//������ʼʱ��					
					workInfo.setStatus(info.getStatus());					//����״̬
					workInfo.setOwner(info.getOwner());						//�Ͱ���
					workInfo.setWfDefineName(info.getOsWfentry().getOsWfdefine().getName());
										
					hm.put(String.valueOf(info.getOsWfentry().getId()), workInfo);
				}	
			}					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(workflowManager!=null)
				{
					workflowManager.closeSession();
				}
			}
			catch(Exception we)
			{
				we.printStackTrace();
			}
		}
		return hm;
	}
	
	/*
	 * ȡ����������
	 * ����:��ȡ�������������ܾ�ʱ,���ø÷���,��������¼�����ҵ��ĵ�ǰ��¼��Ϊ��Ч
	 * ����:	
	 * @param       InutParameterInfo      	
	 * @return      long        			
	 */
	public static long cancelApprovalRecord (InutParameterInfo pInfo) throws Exception
	{
		long lReturn = -1;
		String strUserId="-1";
		com.iss.itreasury.util.SessionMng sessionMng = null;
		try
		{
			if(pInfo != null&&pInfo.getSessionMng()!=null)
			{
				sessionMng = pInfo.getSessionMng();				
				strUserId=String.valueOf(sessionMng.m_lUserID);
			}
			InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
			InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
			recordInfo.setApprovalEntryID(pInfo.getApprovalEntryID());
			recordInfo.setStatusID(Constant.RecordStatus.INVALID);							
			recordBiz.updateByApprovalEntryID(recordInfo);	
			//���ȡ��������¼
			//add by keivn(������)2011-05-19
		 	HistoryBiz historyBiz=new HistoryBiz();
		 	HistoryAdviseInfo historyAdviseInfo=new HistoryAdviseInfo();
		 	historyAdviseInfo.setOperator(strUserId);
		 	historyAdviseInfo.setOpTime(new Timestamp(new Date().getTime()));
		 	historyAdviseInfo.setActionKey(String.valueOf(Constant.Actions.CANCELAPPROVED));
		 	historyAdviseInfo.setAction(Constant.Actions.getName(Constant.Actions.CANCELAPPROVED));	
		 	historyAdviseInfo.setEntryID(pInfo.getApprovalEntryID());
		 	historyAdviseInfo.setAdvise(pInfo.getAdvise());
		 	historyAdviseInfo.setStatusID(Constant.RecordStatus.VALID);
		 	long tempid=historyBiz.save(historyAdviseInfo);
		 	if(tempid<0){
		 		 Log.print("--����ȡ��������¼ʧ��--");
		 		 throw new IRuntimeException("ȡ������ʧ��,����!");
		 	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lReturn;
	}
	
    public static Map getRequestAttribute(HttpServletRequest req)
    {
        Map map = null;
        String strParamName = null;
        Object obj = null;
        if(req != null)
        {
            map = new HashMap();
            for(Enumeration EnumNames = req.getAttributeNames(); EnumNames.hasMoreElements(); map.put(strParamName, obj))
            {
                strParamName = (String)EnumNames.nextElement();
                obj = req.getAttribute(strParamName);
                if(obj == null)
                {
                    obj = req.getAttribute(strParamName);
                }
            }
        }
        return map;
    }

}
