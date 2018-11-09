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
		// TODO �Զ����ɷ������
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO �Զ����ɷ������
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO �Զ����ɷ������
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO �Զ����ɷ������
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
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException{
	}
	
//	******�û��Լ������*******//
	/**
	 * ���������ÿ�ƻ�
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
		    //������������ Added by zwsun��2007/7/18
	    	//begin
			OBBudgetDao dao = new OBBudgetDao();
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+parentBudgetId);
				tempInfo.setTransID(String.valueOf(parentBudgetId));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//��������¼Ϊ������״̬
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
				//Added by zwsun, 2007/7/18, �������������Ӽ�¼Ϊ������״̬
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
	 * �ÿ��������
	 */
	public long saveAdjust(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException{
		long id = -1;
		OBBudgetDao dao = new OBBudgetDao();
		Timestamp currentTime = Env.getCurrentSystemDate();
		try{
			info.setModifyDate(Env.getSystemDateTime());
			info.setStatus(OBConstant.OBBudgetStatus.BEADJUSTED);		//����ԭ�ÿ�ƻ�״̬Ϊ������
			dao.update(info);
			info.setId(id);												//�����ÿ�����ƻ�
			info.setStatus(OBConstant.OBBudgetStatus.SAVE);
			info.setAdjustId(info.getId());
			id = dao.add(info);
		    //������������ Added by zwsun��2007/7/18
	    	//begin
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+id);
				tempInfo.setTransID(String.valueOf(id));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//��������¼Ϊ������״̬
				dao.updateStatus(id,OBConstant.OBBudgetStatus.CHECK, -1, "authing");		    	
		    }
		    //end			
			for(int i=0;i<list.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
				double amount = DataFormat.parseNumber((String)listAmounts.get(i));
				if( (subInfo.getStartdate().equals(currentTime) && (subInfo.getStatus() == OBConstant.OBBudgetStatus.NOTDEAL || subInfo.getStatus() == OBConstant.OBBudgetStatus.FAILEDDEAL)) || subInfo.getStartdate().after(currentTime)){
					subInfo.setStatus(OBConstant.OBBudgetStatus.BEADJUSTED);		//����ԭ�Ӽ�¼״̬Ϊ������
					dao.update(subInfo);
					subInfo.setStatus(OBConstant.OBBudgetStatus.SAVE);				//�����ÿ�����Ӽ�¼
					subInfo.setAmount(amount);					
				}			
				subInfo.setParentBudgetId(id);
				subInfo.setId(-1);
				long lSingleRecord = dao.add(subInfo);
				//Added by zwsun, 2007/7/18, �������������Ӽ�¼Ϊ������״̬
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
	 * ��ѯʱ����״̬Ϊ�ѱ���ļ�¼��Ϣ
	 */
	public long updateBudget(OBBudgetInfo info,List list,List listAmounts) throws IRollbackException,RemoteException{
		long id = -1;
		Timestamp currentTime = Env.getCurrentSystemDate();
		OBBudgetDao dao = new OBBudgetDao();
		info.setModifyDate(currentTime);
		try{
			dao.update(info);
			//������������ Added by zwsun��2007/8/8
		    if(info.getInutParameterInfo()!=null){
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+info.getId());
				tempInfo.setTransID(String.valueOf(info.getId()));
				tempInfo.setDataEntity(info);
				OBFSWorkflowManager.initApproval(tempInfo);
				//��������¼Ϊ������״̬
				dao.updateStatus(info.getId(),OBConstant.OBBudgetStatus.CHECK, -1, "authing");		    	
		    }			
			for(int i=0;i<list.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
				double amount = DataFormat.parseNumber((String)listAmounts.get(i));				
				subInfo.setAmount(amount);		//����ԭ�Ӽ�¼�ÿ���
				dao.update(subInfo);			    								
				//Added by zwsun, 2007/7/18, �������������Ӽ�¼Ϊ������״̬
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
	 * ����, Added by zwsun, 2007/7/19
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
			//�ύ����
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
				sessionMng.getActionMessages().addMessage("��ҵ���ѱ��������������");
				throw new Exception("��ҵ���ѱ��������������",e);
			}
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if(returnInfo.isLastLevel())
			{	
				
				
				long parentId = inputDao.getParentId(info.getClientID());
				
				InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
				
				//���������
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
					//�����Ӽ�¼״̬
					List subList=dao.findAllSubRecords(info.getId());
					for(int i=0;i<subList.size();i++){
						OBBudgetInfo subInfo = (OBBudgetInfo)subList.get(i);
						dao.updateStatus(subInfo.getId(),OBConstant.OBBudgetStatus.NOTDEAL,sessionMng.m_lUserID,"check");
					}
					
					//������Զ�����
					/*���ԭ������˹���
					if(OBFSWorkflowManager.isAutoCheck())
					{
						//����check����
						//FinanceInfo financeInfo = new FinanceInfo();
						//financeInfo = this.findByID(info.getID(),info.getUserID(),info.getCurrencyID());
						//financeinfo.setAbstract("����");
						info.setUserID(sessionMng.m_lUserID);	
						info.setCurrencyID(sessionMng.m_lCurrencyID);
						//financeInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
										
						
						//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
						
						//����openCheck����
						this.check(info.getID(),info.getUserID(),info.getCurrencyID());	
	
					}
					*/
				
				}
			}
			//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getId(),
						OBConstant.OBBudgetStatus.SAVE,-1,"authing");
				//�����Ӽ�¼״̬
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
	 * ȡ������������������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷���
	 * ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
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
			//���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			/*
			if(OBFSWorkflowManager.isAutoCheck() && financeInfo.getStatus()==OBConstant.SettInstrStatus.CHECK)
			{
				//ȡ������
				this.cancelCheck(financeInfo);
				//ȡ������
				lReturn = obFinanceInstrDao.updateStatusAndActionStatus(financeInfo.getID(), OBConstant.SettInstrStatus.SAVE,OBConstant.SettActionStatus.CANCELAPPROVALED);
			}
			else if( !OBFSWorkflowManager.isAutoCheck() && financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED)
			{
				//ȡ������
				lReturn = obFinanceInstrDao.updateStatusAndActionStatus(financeInfo.getID(), OBConstant.SettInstrStatus.SAVE,OBConstant.SettActionStatus.CANCELAPPROVALED);
			}
			*/
			lReturn = obFinanceInstrDao.updateStatus(financeInfo.getId(), OBConstant.OBBudgetStatus.SAVE, sessionMng.m_lUserID, "cancelcheck");
			//�����Ӽ�¼״̬
			List subList=obFinanceInstrDao.findAllSubRecords(financeInfo.getId());
			for(int i=0;i<subList.size();i++){
				OBBudgetInfo subInfo = (OBBudgetInfo)subList.get(i);
				obFinanceInstrDao.updateStatus(subInfo.getId(),OBConstant.OBBudgetStatus.SAVE, sessionMng.m_lUserID, "cancelcheck");
			}			
			//��ѯӳ���
			InutApprovalRecordInfo inutApprovalRecordInfo = OBFSWorkflowManager.findByTransID(String.valueOf(financeInfo.getId()),OBConstant.SettInstrType.BUDGETNEW,Constant.RecordStatus.VALID);
			
			
			//�������ӳ����Ϣ,˵����ҵ�������������
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
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
