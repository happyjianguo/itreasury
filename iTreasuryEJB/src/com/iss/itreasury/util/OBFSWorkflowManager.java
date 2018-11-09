/*
 * Created on 2007-4-21
 *
 * Title:				iTreasury
 * @author             	ypxu
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增处理接口        
 */

package com.iss.itreasury.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iss.inut.workflow.constants.Constants;
import com.iss.inut.workflow.constants.StateEnum;
import com.iss.inut.workflow.entity.po.OsCurrentstep;
import com.iss.inut.workflow.entity.po.OsHistorystep;
import com.iss.inut.workflow.ws.WorkflowManager;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.ebank.obhistory.bizlogic.HistoryBiz;
import com.iss.itreasury.ebank.obhistory.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRecordBiz;
import com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.util.OBConstant;

public class OBFSWorkflowManager 
{
	/*
	 * 提交审批方法
	 * 作用:将系统内业务提交的到审批流
	 * 描述:1.根据参数查找该业务关联的审批流  2.调用审批流引擎的初始化接口,将业务提交到审批流中
	 * @param       transtypeid      	待审批业务的业务类型
	 * 				actionid			待审批业务的操作类型(一般只有贷款\证券等模块需要将审批流关联到业务的不同操作时才用到,其它情况可以传"-1")
	 * 				String strUrl		在审批系统中的"待办业务查询"中,点击查询出的待办业务时的链接(即业务审批录入页面,包括业务明细和相关审批的标签)
	 * 				request				主要用于向审批流引擎传递业务数据(request中有该业务所有数据,转换为map,传递给审批流引擎)
	 * 				sessionMng			系统session,包含登录用户,办事处,币种,模块等信息
	 * @return      long        		成功，返回审批流ID值；失败，返回值=-1
	 */
	public static long initApproval(InutParameterInfo pInfo)throws IException
	{
		long lReturn = -1;
		long trantypeid = -1;
//		long actionid = -1;
		String url = "";
		HttpServletRequest request = null;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		WorkflowManager wfManager = null;
		try
		{		
			if(pInfo != null)
			{
				trantypeid = pInfo.getTransTypeID();
//				actionid = pInfo.getActionID();
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}	
		
			InutApprovalRelationBiz arbiz=new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo=new InutApprovalRelationInfo();
			qInfo.setModuleID(pInfo.getModuleID());
//			qInfo.setActionID(actionid);
			qInfo.setTransTypeID(trantypeid);
			qInfo.setOfficeID(sessionMng.m_lOfficeID);
			qInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			//当是集团审批时存入的是上级客户ID
			if(pInfo.getIslowerunit()==OBConstant.IsLowerun.ISYES)
			{
				qInfo.setClientID(pInfo.getClientID());
			}
			else
			{
				qInfo.setClientID(sessionMng.m_lClientID);
			}
			qInfo.setIslowerunit(pInfo.getIslowerunit());
			
		
			//查找该业务关联的审批流
			long lApprovalID=arbiz.findApprovalID(qInfo);
					
			if(lApprovalID < 0)
			{
				throw new IException("该业务未关联审批流,请检查!");
			}
				
			//构造业务数据map,传递给审批流引擎
			HashMap hm=new HashMap();
			switch ((int)(pInfo.getModuleID()))
			{
				case (int)Constant.ModuleType.SETTLEMENT:
					hm.putAll( OBFSWorkflowEntityConverter.entity2Map(pInfo));
					break;
			}
			
			//初始化审批流引擎
			wfManager=WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));
			//开始事务
			//wfManager.beginTransaction();
			//初始化操作
			lReturn = wfManager.initialize(lApprovalID,0,url,hm);
			
//			保存提交审批记录
			if(lReturn>0)
			{
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				
				recordInfo.setOfficeID(pInfo.getOfficeID());
				recordInfo.setCurrencyID(pInfo.getCurrencyID());
				recordInfo.setModuleID(pInfo.getModuleID());				
				recordInfo.setTransTypeID(pInfo.getTransTypeID());
				//recordInfo.setActionID(pInfo.getActionID());
				recordInfo.setTransID(pInfo.getTransID());
				recordInfo.setApprovalEntryID(lReturn);
				recordInfo.setLinkUrl(pInfo.getUrl());
				recordInfo.setClientID(pInfo.getClientID());
				recordInfo.setStatusID(Constant.RecordStatus.VALID);
				if(pInfo.getNextLevel()>1)
				{
					recordInfo.setNextLevel(pInfo.getNextLevel());
				}
				else
				{
					recordInfo.setNextLevel(1);//提交审批，所以下一级审批级别必然为1
				}
				
				recordBiz.save(recordInfo);	
				//添加提交记录
				//add by keivn(刘连凯)2011-05-20
			 	HistoryBiz historyBiz=new HistoryBiz();
			 	HistoryAdviseInfo historyAdviseInfo=new HistoryAdviseInfo();
			 	historyAdviseInfo.setOperator(String.valueOf(sessionMng.m_lUserID));
			 	historyAdviseInfo.setOpTime(new Timestamp(new Date().getTime()));
			 	historyAdviseInfo.setActionKey(String.valueOf(OBConstant.Actions.SAVEANDINITAPPROVAL));
			 	historyAdviseInfo.setAction(OBConstant.Actions.getName(OBConstant.Actions.SAVEANDINITAPPROVAL));	
			 	historyAdviseInfo.setEntryID(lReturn);
			 	historyAdviseInfo.setAdvise(pInfo.getAdvise());
			 	historyAdviseInfo.setStatusID(OBConstant.RecordStatus.VALID);
			 	long tempid=historyBiz.save(historyAdviseInfo);
			 	if(tempid<0){
			 		 Log.print("--生成提交记录失败--");
			 		 throw new IRuntimeException("提交审批失败,请检查!");
			 	}
			}
			else
			{
				throw new IRuntimeException("提交审批失败,请检查!");
			}
			//提交
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
	 * 审批方法
	 * 作用:审批系统内业务信息
	 * 描述:调用审批流引擎的审批接口,审批待办业务
	 * @param       strUrl      	下一环节页面(即审批动作完成后返回的页面)
	 * 				request			request,包含审批流相关信息
	 * 				sessionMng		系统session,包含相关信息
	 * @return      long        	成功，返回审批流ID值；失败，返回值=-1
	 */
	public static InutParameterInfo doApproval(InutParameterInfo pInfo)throws IException
	{
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		InutParameterInfo returnInfo = pInfo;
		String url = "";
		WorkflowManager workflowManager = null;
		try
		{
			if(pInfo != null)
			{
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}
			
			//审批流id,从request中获取(点击待办业务链接时,request中已经有该信息)
			long wfInstanceId = Long.parseLong((String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_INSTANCEID));
			//动作编号,从request中获取(点击待办业务链接时,request中已经有该信息)
			int wfActionId = Integer.parseInt((String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ACTIONID));
			//审批意见,从request中获取
			String osAdvise = (String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ADVISE);
			//是否是审批通过
			String ispass = (String)pInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_ISPASS);
			
			//构造业务数据map,传递给审批流引擎，pInfo中须有业务info类
			HashMap hm=new HashMap();
			hm.put(com.iss.inut.workflow.constants.Constants.WF_TASKID,(String)pInfo.getRequestMap().get(Constants.WF_TASKID));	
			hm.put(com.iss.inut.workflow.constants.Constants.WF_ADVISE,osAdvise);
			
			switch ((int)(pInfo.getModuleID()))
			{
				case (int)Constant.ModuleType.SETTLEMENT:
					hm.putAll( OBFSWorkflowEntityConverter.entity2Map(pInfo));
					break;
			}
			
			//初始化审批流引擎
			workflowManager = WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));
			//事务开始
		   // workflowManager.beginTransaction();
		    //审批操作
			StateEnum se = workflowManager.doAction(wfInstanceId,wfActionId,url,hm);
			
			//判断是否最后一级审批通过或最后一级审批拒绝
			//se.equals(se.COMPLETED)判断是否最后一级，ispass.equals("0")判断是审批通过还是审批拒绝
			if(se.equals(se.COMPLETED) && ispass.equals("0"))
			{
				returnInfo.setLastLevel(true);
				//记录最后一级审批人
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				recordInfo.setApprovalEntryID(wfInstanceId);
				recordInfo.setLastAppUserID(pInfo.getUserID());	
				recordInfo.setNextLevel(1);//只要大于1即可，update操作是自增操作（即update nextlevel=nextlevel+1）
				recordBiz.updateByApprovalEntryID(recordInfo);	
			}
			else if(se.equals(se.COMPLETED) && !ispass.equals("0"))
			{
			    returnInfo.setRefuse(true);
			    //删除审批记录（逻辑删除）
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				recordInfo.setApprovalEntryID(wfInstanceId);
				recordInfo.setStatusID(Constant.RecordStatus.INVALID);//状态无效	
				//recordInfo.setNextLevel(1);//只要大于1即可，update操作是自增操作（即update nextlevel=nextlevel+1）
				recordBiz.updateByApprovalEntryID(recordInfo);
			}
			else
			{
				//update下一级审批级别
				InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
				recordInfo.setApprovalEntryID(wfInstanceId);
				recordInfo.setNextLevel(1);//只要大于1即可，update操作是自增操作（即update nextlevel=nextlevel+1）						
				recordBiz.updateByApprovalEntryID(recordInfo);
			}	
				
//			事务提交
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
			throw new IException("Gen_E001",e);
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
	 * 判断该业务流程是否需要审批
	 * 作用:根据业务数据和审批关联设置判断该业务是否需要审批流程
	 * 描述:根据业务数据查找审批关联设置表,如果该业务有相关联的审批流,则表示该业务需要审批流程,反之,则不需要
	 * @param       strUrl      	下一环节页面(即审批动作完成后返回的页面)
	 * 				request			request,包含审批流相关信息
	 * 				sessionMng		系统session,包含相关信息
	 * @return      boolean        	需要，返回true；不需要，返回false
	 */
	public static boolean isNeedApproval(InutParameterInfo pInfo) throws IException
	{
		boolean bReturn = true;
		long lApprovalID = -1;
		try
		{
			//初始化查询类和参数类
			InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
			//构造参数类
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setTransTypeID(pInfo.getTransTypeID());
			qInfo.setClientID(pInfo.getClientID());
//			qInfo.setActionID(pInfo.getActionID());
			// 是否是否设置关联下级单位
			qInfo.setIslowerunit(pInfo.getIslowerunit()); 
			//根据条件查询给业务是否有审批流
			lApprovalID = iaBiz.findApprovalID(qInfo);
			
			if(lApprovalID>0)
			{
				bReturn = true;		//有审批流返回true
			}
			else
			{
				bReturn = false;	//没有审批流返回false
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return bReturn;
	}

	/*
	 * 获取业务复核时,需要匹配的业务状态,和取消复核后,需要恢复的业务状态
	 * 作用:根据业务数据,取得匹配时应匹配的业务状态和取消复核后应该恢复的业务状态
	 * 描述:根据业务数据查找审批关联设置表,如果该业务有相关联的审批流,则表示该业务需要审批流程,匹配时需要匹配"已审批"状态,取消复核需恢复"已审批"状态
	 * 	   如果该业务没有关联审批流,则该业务没有审批流程,匹配时需要匹配"已保存"状态,取消复核需恢复"已保存"状态	
	 * @param       strUrl      	下一环节页面(即审批动作完成后返回的页面)
	 * 				request			request,包含审批流相关信息
	 * 				sessionMng		系统session,包含相关信息
	 * @return      long        	有审批流的返回"已审批",反之则返回"已保存"
	 */
	public static long getOBCheckStatus(InutParameterInfo pInfo) throws Exception
	{
		long lStatus = -1;
		long lApprovalID = -1;
		try
		{
			//初始化查询类和参数类
			InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
			//构造参数类
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setClientID(pInfo.getClientID());
			qInfo.setTransTypeID(getReflectOperation(pInfo.getTransTypeID(),pInfo.getRemitType()));
//			qInfo.setActionID(pInfo.getActionID());
			//复核匹配只能匹配到本单位的审批流
			qInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
			//根据条件查询给业务是否有审批流
			lApprovalID = iaBiz.findApprovalID(qInfo);
			
			if(lApprovalID>0)
			{				
				lStatus = OBConstant.SettInstrStatus.APPROVALED;	//有审批流返回"已审批"状态
			}
			else
			{
				lStatus = OBConstant.SettInstrStatus.SAVE;		//有审批流返回"已保存"状态
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lStatus;
	}
	//取消复核时,判断状态
	public static long getOBCancelCheckStatus(InutParameterInfo pInfo) throws Exception
	{
		long lStatus = -1;
		long lApprovalID = -1;
		try
		{
			//初始化查询类和参数类
			InutApprovalRelationBiz iaBiz = new InutApprovalRelationBiz();
			InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
			//构造参数类
			qInfo.setModuleID(pInfo.getModuleID());
			qInfo.setOfficeID(pInfo.getOfficeID());
			qInfo.setCurrencyID(pInfo.getCurrencyID());
			qInfo.setClientID(pInfo.getClientID());
			qInfo.setTransTypeID(pInfo.getTransTypeID());
//			qInfo.setActionID(pInfo.getActionID());
//			复核匹配只能匹配到本单位的审批流
			qInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
			//根据条件查询给业务是否有审批流
			lApprovalID = iaBiz.findApprovalID(qInfo);
			
			if(lApprovalID>0)
			{				
				lStatus = OBConstant.SettInstrStatus.APPROVALED;	//有审批流返回"已审批"状态
			}
			else
			{
				lStatus = OBConstant.SettInstrStatus.SAVE;		//有审批流返回"已保存"状态
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lStatus;
	}
	/*
	 * 结算判断是否自动复核
	 * 作用:根据配置项,判断是否在最后一级审批时自动复核
	 * 描述:配置项参考settlement.xml:sett_transapproval_autocheck
	 * @param       
	 * @return      boolean        自动复核：true,不自动复核:false
	 */
	public static boolean isAutoCheck() throws Exception
	{
		boolean bReturn = false;
		
		bReturn = Config.getBoolean(ConfigConstant.EBANK_TRANSAPPROVAL_AUTOCHECK,false);
		
		return bReturn;
	}
	//得到交易类型的映射
	public static final long getReflectOperation(long lCode,long remitType)
	{
		long lReturn = -1; //初始化返回值
		switch ((int) lCode)
		{
//		资金划拨
		case (int)OBConstant.QueryInstrType.CAPTRANSFER:
			if(remitType==OBConstant.SettRemitType.INTERNALVIREMENT)
			{
				lReturn=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
			}
			else if(remitType==OBConstant.SettRemitType.BANKPAY)
			{
				lReturn=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
			}
			//lReturn = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
			break;
//		定期开立
		case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
			lReturn = OBConstant.SettInstrType.OPENFIXDEPOSIT;
		break;
//		定期支取	
		case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
			lReturn = OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER;
		break;
//		通知开立
		case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
			lReturn = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
		break;
//		通知支取	
		case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
			lReturn = OBConstant.SettInstrType.NOTIFYDEPOSITDRAW;
		break;
//		自营贷款清还
		case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
			lReturn = OBConstant.SettInstrType.TRUSTLOANRECEIVE;
		break;
//		委托贷款清还
		case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
			lReturn = OBConstant.SettInstrType.CONSIGNLOANRECEIVE;
		break;
//		利息费用清还
		case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
			lReturn = OBConstant.SettInstrType.INTERESTFEEPAYMENT;
		break;
//		下属单位资金划拨
		case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
			lReturn = OBConstant.SettInstrType.CHILDCAPTRANSFER;
		break;
//		银团贷款清还
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
			lReturn = OBConstant.SettInstrType.YTLOANRECEIVE;
		break;
//		资金申领
		case (int)OBConstant.QueryInstrType.APPLYCAPITAL:
			lReturn = OBConstant.SettInstrType.APPLYCAPITAL;
		break;
//		换开定期存单
		case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
			lReturn = OBConstant.SettInstrType.BILLOPENFIXDEPOSIT;
		break;
//		定期续存
		case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT:
			lReturn = OBConstant.SettInstrType.DRIVEFIXDEPOSIT;
		break;
//		定期转存	
		case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT:
			lReturn = OBConstant.SettInstrType.CHANGEFIXDEPOSIT;
		break;
		}
		return lReturn;
	}
	
	/*
	 * 获取当前用户的代办业务
	 * 作用:调用审批流引擎接口,查询当前用户的代办业务(不区分模块,业务类型)
	 * 描述:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			key为审批实例id,value为InutApprovalWorkInfo
	 */
	public static HashMap getCurrentList (InutParameterInfo pInfo) throws Exception
	{
		long trantypeid = -1;
		String url = "";
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		try
		{
			if(pInfo != null)
			{
				trantypeid = pInfo.getTransTypeID();
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}
			
			workflowManager=WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));		
			
			List list = workflowManager.getAvailableSteps(1, 100000);
			
			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsCurrentstep info = (OsCurrentstep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//任务id
					workInfo.setEntryID(info.getOsWfentry().getId());		//审批实例id
					workInfo.setActionID(info.getActionId());				//审批动作id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//审批环节id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//审批实例名称
					workInfo.setStepName(info.getStepName());				//审批环节名称
					workInfo.setDueDate(info.getDueDate());					//最迟审批时间
					workInfo.setStartDate(info.getStartDate());				//审批开始时间					
					workInfo.setStatus(info.getStatus());					//任务状态
					workInfo.setOwner(info.getOwner());						//送办人
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
	 * 获取当前用户的已办业务
	 * 作用:调用审批流引擎接口,查询当前用户的已办业务(不区分模块,业务类型)
	 * 描述:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			key为审批实例id,value为InutApprovalWorkInfo
	 */
	public static HashMap getHistoryList (InutParameterInfo pInfo) throws Exception
	{
		long trantypeid = -1;
		String url = "";
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		
		try
		{
			
			if(pInfo != null)
			{
				trantypeid = pInfo.getTransTypeID();
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}
			
			workflowManager=WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));		
			
			List list = workflowManager.getHistoryList(1, 100000);
			
			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsHistorystep info = (OsHistorystep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//任务id
					workInfo.setEntryID(info.getOsWfentry().getId());		//审批实例id
					workInfo.setActionID(info.getActionId());				//审批动作id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//审批环节id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//审批实例名称
					workInfo.setStepName(info.getStepName());				//审批环节名称
					workInfo.setDueDate(info.getDueDate());					//最迟审批时间
					workInfo.setStartDate(info.getStartDate());				//审批开始时间					
					workInfo.setStatus(info.getStatus());					//任务状态
					workInfo.setOwner(info.getOwner());						//送办人
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
	 * 获取当前用户的办结业务
	 * 作用:调用审批流引擎接口,查询当前用户的办结业务(不区分模块,业务类型)
	 * 描述:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			key为审批实例id,value为InutApprovalWorkInfo
	 */
	public static HashMap getFinishedList (InutParameterInfo pInfo) throws Exception
	{
		long trantypeid = -1;
		String url = "";
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		try
		{
			if(pInfo != null)
			{
				trantypeid = pInfo.getTransTypeID();
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}
			
			workflowManager=WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));	
			
			List list = workflowManager.getFinishedList(1, 100000);

			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsHistorystep info = (OsHistorystep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//任务id
					workInfo.setEntryID(info.getOsWfentry().getId());		//审批实例id
					workInfo.setActionID(info.getActionId());				//审批动作id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//审批环节id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//审批实例名称
					workInfo.setStepName(info.getStepName());				//审批环节名称
					workInfo.setDueDate(info.getDueDate());					//最迟审批时间
					workInfo.setStartDate(info.getStartDate());				//审批开始时间					
					workInfo.setStatus(info.getStatus());					//任务状态
					workInfo.setOwner(info.getOwner());						//送办人
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
	 * 获取当前用户的可以办理取消审批的业务列表
	 * 作用:调用审批流引擎接口,查询当前用户的办结业务(不区分模块,业务类型)
	 * 描述:	
	 * @param       InutParameterInfo      	
	 * @return      HashMap        			key为审批实例id,value为InutApprovalWorkInfo
	 */
	public static HashMap getWorkForCancelApproveList (InutParameterInfo pInfo) throws Exception
	{
		long trantypeid = -1;
		String url = "";
		HashMap hm = new HashMap();
		WorkflowManager workflowManager = null;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		
		try
		{
			
			if(pInfo != null)
			{
				trantypeid = pInfo.getTransTypeID();
				url = pInfo.getUrl();
				sessionMng = pInfo.getSessionMng();
			}
			
			workflowManager=WorkflowManager.instance(String.valueOf(sessionMng.m_lUserID));		
			
			List list = workflowManager.getHistoryList(1, 100000);
			
			if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					OsHistorystep info = (OsHistorystep)list.get(i);
					InutApprovalWorkInfo workInfo = new InutApprovalWorkInfo();
					
					workInfo.setTaskID(info.getId());						//任务id
					workInfo.setEntryID(info.getOsWfentry().getId());		//审批实例id
					workInfo.setActionID(info.getActionId());				//审批动作id
					workInfo.setActionCode(info.getActionCode());
					workInfo.setStepID(info.getStepId());					//审批环节id
					workInfo.setStepCode(info.getStepCode());
					workInfo.setEntryName(info.getOsWfentry().getName());	//审批实例名称
					workInfo.setStepName(info.getStepName());				//审批环节名称
					workInfo.setDueDate(info.getDueDate());					//最迟审批时间
					workInfo.setStartDate(info.getStartDate());				//审批开始时间					
					workInfo.setStatus(info.getStatus());					//任务状态
					workInfo.setOwner(info.getOwner());						//送办人
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
	 * 取消审批方法
	 */
	public static long cancelApprovalRecord (InutParameterInfo pInfo) throws Exception
	{
		long lReturn = -1;
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		String strUserId="-1";
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
			recordInfo.setStatusID(OBConstant.RecordStatus.INVALID);							
			recordBiz.updateByApprovalEntryID(recordInfo);	
			//添加取消审批记录
			//add by keivn(刘连凯)2011-05-19
		 	HistoryBiz historyBiz=new HistoryBiz();
		 	HistoryAdviseInfo historyAdviseInfo=new HistoryAdviseInfo();
		 	historyAdviseInfo.setOperator(strUserId);
		 	historyAdviseInfo.setOpTime(new Timestamp(new Date().getTime()));
		 	historyAdviseInfo.setActionKey(String.valueOf(OBConstant.Actions.CANCELAPPROVAL));
		 	historyAdviseInfo.setAction(OBConstant.Actions.getName(OBConstant.Actions.CANCELAPPROVAL));	
		 	historyAdviseInfo.setEntryID(pInfo.getApprovalEntryID());
		 	historyAdviseInfo.setAdvise(pInfo.getAdvise());
		 	historyAdviseInfo.setStatusID(OBConstant.RecordStatus.VALID);
		 	long tempid=historyBiz.save(historyAdviseInfo);
		 	if(tempid<0){
		 		 Log.print("--生成取消审批记录失败--");
		 		 throw new IRuntimeException("取消审批失败,请检查!");
		 	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lReturn;
	}
	

	public static void updateApprovalRecord (InutParameterInfo pInfo) throws Exception
	{

		try
		{
			InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
			InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
			recordInfo.setApprovalEntryID(pInfo.getApprovalEntryID());
			recordInfo.setStatusID(Constant.RecordStatus.STASIS);							
			recordBiz.updateByApprovalEntryID(recordInfo);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
    /**
     * 根据业务id和业务类型查找业务详细信息
     */
    public static InutApprovalRecordInfo findByTransID(String lTransID,long lTransType,long status) throws Exception
    {
    	InutApprovalRecordBiz recordBiz = new InutApprovalRecordBiz();
		InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
		recordInfo = recordBiz.findByTransID( lTransID,lTransType,status);
		return recordInfo;
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
	            }
	        }
	        return map;
	    }

}
