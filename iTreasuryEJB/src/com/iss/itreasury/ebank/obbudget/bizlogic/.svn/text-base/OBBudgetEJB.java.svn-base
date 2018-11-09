package com.iss.itreasury.ebank.obbudget.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.ebank.approval.dao.InutApprovalRelationDao;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obbudget.dao.OBBudgetDao;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.OBFSWorkflowManager;

public class OBBudgetEJB implements SessionBean{
	private javax.ejb.SessionContext mySessionCtx = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2592022384328785353L;

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		mySessionCtx = arg0;
	}
	
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext(){
		return mySessionCtx;
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException{
	}
	
//	******用户自加入代码*******//
	/**
	 * 新增所有用款计划
	 */
	public long saveAll(OBBudgetInfo info,List listDates,List listAmounts) throws IRollbackException,RemoteException{
		long parentBudgetId = -1;
		info.setStartdate(DataFormat.getDateTime((String)listDates.get(0)));
		info.setEnddate(DataFormat.getDateTime((String)listDates.get(listDates.size()-1)));
		info.setParentBudgetId(parentBudgetId);
		info.setModifyDate(Env.getSystemDateTime());
		try{
			OBBudgetBiz biz = new OBBudgetBiz();
			parentBudgetId = biz.save(info);
		    //增加审批流， Added by zwsun，2007/7/18
	    	//begin
			OBBudgetDao dao = new OBBudgetDao();
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+parentBudgetId);
				tempInfo.setTransID(String.valueOf(parentBudgetId));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//更新主记录为审批中状态
				dao.updateStatus(parentBudgetId,OBConstant.OBBudgetStatus.CHECK, -1, "authing");		    	
		    }
		    //end
			info.setParentBudgetId(parentBudgetId);			
			for(int i = 0 ; i < listDates.size() ; i ++){	
				info.setStartdate(DataFormat.getDateTime((String)listDates.get(i)));
				info.setEnddate(DataFormat.getDateTime((String)listDates.get(i)));
				info.setAmount(DataFormat.parseNumber((String)listAmounts.get(i)));
				info.setId(-1);
				long lSingleRecord=biz.saveSingleDay(info);
				//Added by zwsun, 2007/7/18, 审批流，更新子记录为审批中状态
				if(info.getInutParameterInfo()!=null && lSingleRecord>0){
					dao.updateStatus(lSingleRecord, OBConstant.OBBudgetStatus.CHECK, -1, "authing");					
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return parentBudgetId;
	}
	
	/**
	 * 用款调整新增
	 */
	public long saveAdjust(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException{
		long id = -1;
		OBBudgetDao dao = new OBBudgetDao();
		Timestamp currentTime = Env.getCurrentSystemDate();
		try{
			info.setModifyDate(Env.getSystemDateTime());
			info.setStatus(OBConstant.OBBudgetStatus.BEADJUSTED);		//更新原用款计划状态为被调整
			dao.update(info);
			info.setId(id);												//新增用款调整计划
			info.setStatus(OBConstant.OBBudgetStatus.SAVE);
			info.setAdjustId(info.getId());
			id = dao.add(info);
		    //增加审批流， Added by zwsun，2007/7/18
	    	//begin
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+id);
				tempInfo.setTransID(String.valueOf(id));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//更新主记录为审批中状态
				dao.updateStatus(id,OBConstant.OBBudgetStatus.CHECK, -1, "authing");		    	
		    }
		    //end			
			for(int i=0;i<list.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
				double amount = DataFormat.parseNumber((String)listAmounts.get(i));
				if( (subInfo.getStartdate().equals(currentTime) && (subInfo.getStatus() == OBConstant.OBBudgetStatus.NOTDEAL || subInfo.getStatus() == OBConstant.OBBudgetStatus.FAILEDDEAL)) || subInfo.getStartdate().after(currentTime)){
					subInfo.setStatus(OBConstant.OBBudgetStatus.BEADJUSTED);		//更新原子记录状态为被调整
					dao.update(subInfo);
					subInfo.setStatus(OBConstant.OBBudgetStatus.SAVE);				//生成用款调整子记录
					subInfo.setAmount(amount);					
				}			
				subInfo.setParentBudgetId(id);
				subInfo.setId(-1);
				long lSingleRecord = dao.add(subInfo);
				//Added by zwsun, 2007/7/18, 审批流，更新子记录为审批中状态
				if(info.getInutParameterInfo()!=null && lSingleRecord>0){
					dao.updateStatus(lSingleRecord, OBConstant.OBBudgetStatus.CHECK, -1, "authing");					
				}				
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return id;
	}
	/**
	 * 查询时更新状态为已保存的记录信息
	 */
	public long updateBudget(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException{
		long id = -1;
		Timestamp currentTime = Env.getCurrentSystemDate();
		OBBudgetDao dao = new OBBudgetDao();
		info.setModifyDate(currentTime);
		try{
			dao.update(info);
			//增加审批流， Added by zwsun，2007/8/8
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+info.getId());
				tempInfo.setTransID(String.valueOf(info.getId()));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//更新主记录为审批中状态
				dao.updateStatus(info.getId(),OBConstant.OBBudgetStatus.CHECK, -1, "authing");		    	
		    }			
			for(int i=0;i<list.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
				double amount = DataFormat.parseNumber((String)listAmounts.get(i));				
				subInfo.setAmount(amount);		//更新原子记录用款金额
				dao.update(subInfo);			    								
				//Added by zwsun, 2007/7/18, 审批流，更新子记录为审批中状态
				if(info.getInutParameterInfo()!=null && subInfo.getId()>0){
					dao.updateStatus(subInfo.getId(), OBConstant.OBBudgetStatus.CHECK, -1, "authing");					
				}			
			}			
			id = Constant.SUCCESSFUL;
		}catch(Exception e){
			id = Constant.FAIL;
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);			
		}
		return id;
	}
	/**
	 * 审批, Added by zwsun, 2007/7/19
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(OBBudgetInfo info)throws RemoteException, IRollbackException
	{
		long depositId = info.getId();
		InutParameterInfo returnInfo = new InutParameterInfo();
		OBBudgetDao dao = new OBBudgetDao();		
		InutApprovalRelationDao inputDao = new InutApprovalRelationDao();		
		
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		if(inutParameterInfo != null){
			sessionMng = inutParameterInfo.getSessionMng();
		}
		try
		{
			//提交审批
			OBBudgetInfo financeinfo = new OBBudgetInfo();
			financeinfo = (OBBudgetInfo)dao.findByID(info.getId(), OBBudgetInfo.class);
			inutParameterInfo.setDataEntity(financeinfo);
			
			try
			{
				returnInfo = OBFSWorkflowManager.doApproval(inutParameterInfo);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				sessionMng.getActionMessages().addMessage("该业务已被其他人审批完成");
				throw new Exception("该业务已被其他人审批完成",e);
			}
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				
				
				long parentId = inputDao.getParentId(info.getClientID());
				
				InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
				
				//构造参数类
				qInfo.setTransTypeID(inutParameterInfo.getTransTypeID());
				qInfo.setClientID(parentId);
				qInfo.setIslowerunit(1);
				
				long approvalID = inputDao.findApprovalID(qInfo);
				
				//inutParameterInfo.setApprovalEntryID(approvalID);
				
				if(parentId != -1 && approvalID != -1){
					
					//OBFSWorkflowManager.updateApprovalRecord(inutParameterInfo);
					InutApprovalRecordInfo recordInfo = new InutApprovalRecordInfo();
					long wfInstanceId = Long.parseLong((String)inutParameterInfo.getRequestMap().get(com.iss.inut.workflow.constants.Constants.WF_INSTANCEID));
					recordInfo.setApprovalEntryID(wfInstanceId);
					recordInfo.setStatusID(Constant.RecordStatus.STASIS);
					inputDao.updateApprovalRecord(recordInfo);
					inutParameterInfo.getSessionMng().m_lClientID=parentId;
					inutParameterInfo.setIslowerunit(1);
					inutParameterInfo.setTransID(String.valueOf(financeinfo.getId()));
					OBFSWorkflowManager.initApproval(inutParameterInfo);
	
				}else{
					
					dao.updateStatus(info.getId(),OBConstant.OBBudgetStatus.APPROVE,sessionMng.m_lUserID,"check");
					//更新子记录状态
					List subList=dao.findAllSubRecords(info.getId());
					for(int i=0;i<subList.size();i++){
						OBBudgetInfo subInfo = (OBBudgetInfo)subList.get(i);
						dao.updateStatus(subInfo.getId(),OBConstant.OBBudgetStatus.NOTDEAL,sessionMng.m_lUserID,"check");
					}
					
					//如果是自动复核
					/*封掉原来的审核功能
					if(OBFSWorkflowManager.isAutoCheck())
					{
						//构造check参数
						//FinanceInfo financeInfo = new FinanceInfo();
						//financeInfo = this.findByID(info.getID(),info.getUserID(),info.getCurrencyID());
						//financeinfo.setAbstract("机核");
						info.setUserID(sessionMng.m_lUserID);	
						info.setCurrencyID(sessionMng.m_lCurrencyID);
						//financeInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
										
						
						//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
						
						//调用openCheck方法
						this.check(info.getID(),info.getUserID(),info.getCurrencyID());	
	
					}
					*/
				
				}
			}
			//如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getId(),
						OBConstant.OBBudgetStatus.SAVE,-1,"authing");
				//更新子记录状态
				List subList=dao.findAllSubRecords(info.getId());
				for(int i=0;i<subList.size();i++){
					OBBudgetInfo subInfo = (OBBudgetInfo)subList.get(i);
					dao.updateStatus(subInfo.getId(),OBConstant.OBBudgetStatus.SAVE,-1,"authing");
				}				
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
	}
    /**
	 * 取消审批方法。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @author mingfang
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(OBBudgetInfo financeInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		 OBBudgetDao obFinanceInstrDao = new OBBudgetDao();	
		 InutParameterInfo inutParameterInfo = financeInfo.getInutParameterInfo();			
		 com.iss.itreasury.ebank.util.SessionOB sessionMng = null;
		if(inutParameterInfo != null){
			sessionMng = inutParameterInfo.getSessionMng();
		}		 
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			/*
			if(OBFSWorkflowManager.isAutoCheck() && financeInfo.getStatus()==OBConstant.SettInstrStatus.CHECK)
			{
				//取消复核
				this.cancelCheck(financeInfo);
				//取消审批
				lReturn = obFinanceInstrDao.updateStatusAndActionStatus(financeInfo.getID(), OBConstant.SettInstrStatus.SAVE,OBConstant.SettActionStatus.CANCELAPPROVALED);
			}
			else if( !OBFSWorkflowManager.isAutoCheck() && financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED)
			{
				//取消审批
				lReturn = obFinanceInstrDao.updateStatusAndActionStatus(financeInfo.getID(), OBConstant.SettInstrStatus.SAVE,OBConstant.SettActionStatus.CANCELAPPROVALED);
			}
			*/
			lReturn = obFinanceInstrDao.updateStatus(financeInfo.getId(), OBConstant.OBBudgetStatus.SAVE, sessionMng.m_lUserID, "cancelcheck");
			//更新子记录状态
			List subList=obFinanceInstrDao.findAllSubRecords(financeInfo.getId());
			for(int i=0;i<subList.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)subList.get(i);
				obFinanceInstrDao.updateStatus(subInfo.getId(),OBConstant.OBBudgetStatus.SAVE, sessionMng.m_lUserID, "cancelcheck");
			}			
			//查询映射表
			InutApprovalRecordInfo inutApprovalRecordInfo = OBFSWorkflowManager.findByTransID(String.valueOf(financeInfo.getId()),OBConstant.SettInstrType.BUDGETNEW,Constant.RecordStatus.VALID);
			
			
			//如果存在映射信息,说明此业务关联了审批流
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutApprovalRecordInfo != null && inutApprovalRecordInfo.getApprovalEntryID() > 0)
			{
				inutParameterInfo.setApprovalEntryID(inutApprovalRecordInfo.getApprovalEntryID());
				inutParameterInfo.setSessionMng(sessionMng);
				OBFSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}

}
