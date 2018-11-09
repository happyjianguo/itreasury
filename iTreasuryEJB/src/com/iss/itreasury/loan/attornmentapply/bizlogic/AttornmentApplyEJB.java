/*
 * Created on 2004-5-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attornmentapply.bizlogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentContractDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.UtilOperation;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttornmentApplyEJB implements SessionBean
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this); //
	/* Methods required by SessionBean Interface. EJB 1.1 section 6.5.1. */
	/**
	 * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	/**
	 * No argument constructor required by container.
	 */
	public AttornmentApplyEJB()
	{
	}
	/**
	 * Create method specified in EJB 1.1 section 6.10.3
	 */
	public void ejbCreate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove()
	{
	}

	/**
	 * �������ת�����룬�����������޸�
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long save(AttornmentApplyInfo info) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		SEC_ApplyDAO applyDao = new SEC_ApplyDAO();
		long ret=-1;
		long repurchaseApplyId=-1;
		
		try
		{
			if (info.getId()<0)
			{
				dao.setUseMaxID();
				info.setCode(dao.getApplyCode());
				repurchaseApplyId = info.getRepurchaseApplyId();
				ApplyInfo applyInfo = (ApplyInfo)applyDao.findByID(repurchaseApplyId,ApplyInfo.class );
				info.setRepurchaseAmount( applyInfo.getAmount() );
				info.setRepurchaseStartDate( applyInfo.getTransactionStartDate() );
				info.setRepurchaseEndDate( applyInfo.getTransactionEndDate() );
				info.setNextCheckLevel(1);
				
				ret=dao.add(info);
			}
			else
			{
				info.setNextCheckLevel(1);
				dao.update(info);
				ret=info.getId();
			}
			
			if(info.getInutParameterInfo()!=null)
			{
				log4j.debug("------�ύ����--------");
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + ret);
				String transNo="";
				try{
					transNo = UtilOperation.getNewTransactionNo(tempInfo,ret);
				}catch (IException e) {
					throw new RemoteException();
				}
				tempInfo.setTransID(transNo);
				tempInfo.setDataEntity(info);
				
				try{
					//�ύ����
					FSWorkflowManager.initApproval(tempInfo);
					//����״̬��������
					dao.updateStatus(ret, LOANConstant.AttornmentStatus.APPROVALING);
					log4j.debug("------�ύ�����ɹ�--------");
				}catch (Exception e) {
					// TODO: handle exception
					throw new RemoteException();
				}
			}
			
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			//modified by mzh_fu 2007/08/06
			//throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return ret;    
	}
	
	/**
	 * �������ת�õ������ͬ
	 * @param aInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long saveAttornmentContract(AttornmentContractInfo[] aInfo) throws java.rmi.RemoteException,IException
	{
		long ret=-1;
		AttornmentContractDao dao=new AttornmentContractDao();
		AttornmentApplyDao applyDao = new AttornmentApplyDao();
		ContractDao conDao = new ContractDao();
		LoanPayNoticeDao loanPayNoticeDao = new LoanPayNoticeDao();
		ArrayList conList = new ArrayList();
		try
		{
			for (int i=0;i<aInfo.length ;i++)
			{
				if (aInfo[i].getId()>0)
				{
					AttornmentContractInfo info=(AttornmentContractInfo)dao.findByID( aInfo[i].getId(),AttornmentContractInfo.class );
					if (info!=null) 
					{
						aInfo[i].setContractId( info.getContractId() );
						conList.add(info);
					}	
				}
			}
			conDao.setLastAttornment( conList,2);
			conList.clear() ;
			
			for (int i=0;i<aInfo.length;i++)
			{
				conList.add(aInfo[i]);
			}
			conDao.setLastAttornment( conList,1);
			Log.print("conList.size"+conList.size() );
				
			for (int i=0;i<aInfo.length ;i++)
			{
				if (aInfo[i].getId()>0)
				{
					AttornmentContractInfo info=(AttornmentContractInfo)dao.findByID( aInfo[i].getId(),AttornmentContractInfo.class );
					ContractInfo conInfo = conDao.findByID( info.getContractId() );
					
					//LoanPayNoticeInfo payInfo = loanPayNoticeDao.findLoanPayNoticeByID(info.getPayId());
					
					//aInfo[i].setLastAttornmentAmount( conInfo.getLastAttornmentAmount());
					
					dao.update(aInfo[i]);
				}
				else
				{
					dao.setUseMaxID();
					ContractInfo conInfo = conDao.findByID( aInfo[i].getContractId() );
					
					LoanPayNoticeInfo payInfo = loanPayNoticeDao.findLoanPayNoticeByID(aInfo[i].getPayId());
					
					if (conInfo==null)
					{
						log4j.print( "���Һ�ͬʧ�ܣ�����ͬIDΪ"+aInfo[i].getContractId());
						throw new IException("");
					}
					aInfo[i].setContractCode( conInfo.getContractCode() );
					aInfo[i].setClientId( conInfo.getBorrowClientID() );
					aInfo[i].setLoanTypeId( conInfo.getLoanTypeID() );
					
					//aInfo[i].setLoanStartDate( conInfo.getLoanStart() );
					//aInfo[i].setLoanEndDate( conInfo.getLoanEnd() );
					//aInfo[i].setLoanAmount( conInfo.getExamineAmount() );
					aInfo[i].setLoanStartDate( payInfo.getStart() );
					aInfo[i].setLoanEndDate( payInfo.getEnd() );
					aInfo[i].setLoanAmount( payInfo.getAmount() );
					
					//aInfo[i].setLastAttornmentAmount( conInfo.getLastAttornmentAmount());
					
					dao.add(aInfo[i]);
				}
			}
			ret = 1;
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			//modified by mzh_fu 2007/08/06
			//throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//modified by mzh_fu 2007/08/06
			//throw new IException("",ex);
			throw new IRollbackException(context, ex.getMessage(), ex);
		}
		return ret;
	}
	
	/**
	 * ȡ������ת������
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		ContractDao conDao = new ContractDao();
		Collection conList = null;
		AttornmentContractDao attrConDao = new AttornmentContractDao();
		try
		{
			conList=dao.findAttornmentContractByApplyId( lID );
			conDao.setLastAttornment( conList,2 );
			dao.updateStatus(lID,LOANConstant.AttornmentStatus.CANCEL );
			attrConDao.cancleByApplyId(lID);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			// modified by mzh_fu 2007/08/06
			// throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			// modified by mzh_fu 2007/08/06
			// throw new IException("",ex);
			throw new IRollbackException(context, ex.getMessage(), ex);
		}
		
		return -1;
	}
	/**
	 * ���º�ͬδת��ծȨ���
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void setLeftoversAttornment(long lContractID, double leftoversAttornment) throws java.rmi.RemoteException,IException
	{
		ContractDao dao = new ContractDao();
	    
		try {
			dao.setLeftoversAttornment(lContractID, leftoversAttornment);
			
		} catch (ITreasuryDAOException e) {
			throw new IException("",e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * ���Ҵ���ת��������Ϣ
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public AttornmentApplyInfo findById(long lID) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		AttornmentApplyInfo aInfo = new AttornmentApplyInfo();
	    
		try {
			aInfo = (AttornmentApplyInfo)dao.findByID(lID,aInfo.getClass());
			
		} catch (ITreasuryDAOException e) {
			throw new IException("",e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return aInfo;

	}
	
	/**
	 * �޸Ĳ��Ҵ���ת��������Ϣ
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findByMultioption(AttornmentQueryInfo qInfo) throws java.rmi.RemoteException,IException
	{
		java.util.Collection c=null;
		AttornmentApplyDao dao = new AttornmentApplyDao();
		
		try
		{
			c=dao.findByMultiOption( qInfo );
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;

	}
	
	/**
	 * ��������ID����ת�õĺ�ͬ�б�
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findAttornmentContractByApplyId(long lID) throws java.rmi.RemoteException,IException
	{
		java.util.Collection c=null;
		AttornmentApplyDao dao = new AttornmentApplyDao();
		
		try
		{
			c=dao.findAttornmentContractByApplyId( lID );
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;

	}
	/**
	 *��������
	*/
	public long doApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException{
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		AttornmentApplyDao dao = new AttornmentApplyDao();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getId();
		try
		{
			AttornmentApplyInfo loanInfo = new AttornmentApplyInfo();
			loanInfo= (AttornmentApplyInfo)dao.findByID(info.getId(),info.getClass());
			inutParameterInfo.setDataEntity(loanInfo);
			//�ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if(returnInfo.isLastLevel())
			{	
				dao.updateStatus(loanId, LOANConstant.AttornmentStatus.CHECK);
				//���ɺ�ͬ
				new SEC_ApplyDAO().doAfterCheckOver(loanInfo.getRepurchaseApplyId());
				//������Զ�����
				/*if(FSWorkflowManager.isAutoCheck())
				{
//					approvalInfo.setCheckActionID(SECConstant.Actions.CHECKOVER);			
//					check(approvalInfo);
					info.setStatusId(SECConstant.ApplyFormStatus.CHECKED);
					dao.updateStatusID(info);				
				}*/	
			}
			//	��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(loanId, LOANConstant.AttornmentStatus.SUBMIT);
//				approvalInfo.setCheckActionID(SECConstant.Actions.REJECT);
//				check(approvalInfo);
			}
		}
		catch (Exception e)
		{
			throw new SecuritiesException();
		}
		return loanId;		
	}
	/**
	 *ȡ����������
	*/
	public long cancelApproval(AttornmentApplyInfo info) throws java.rmi.RemoteException, IException{
		long lReturn = -1;
		AttornmentApplyDao loanDao = new AttornmentApplyDao();		
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();	
		boolean ifUsed=false;
		try
		{
			info=(AttornmentApplyInfo)loanDao.findByID(info.getId(),info.getClass());
			if(info.getStatusId()==LOANConstant.AttornmentStatus.CHECK)
			{
				
				try{
					ifUsed=loanDao.checkStatuID(info.getId());
				}catch (SecuritiesDAOException e) {
					throw new SecuritiesException();
				}
				if(ifUsed){
					throw new SecuritiesException("����ȡ����ͬ,��ȡ������!",null);
				}
				
				loanDao.cancelContract(info.getRepurchaseApplyId());//ȡ����ͬ�ı���״̬
				//ȡ������
				loanDao.updateStatus(info.getId(), LOANConstant.AttornmentStatus.SUBMIT);
				lReturn = 1;
			}			
			/*
			//���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if(FSWorkflowManager.isAutoCheck() && info.getStatusId()==SECConstant.ApplyFormStatus.CHECKED)
			{
				//ȡ������
//				approvalInfo.setStatusID(SECConstant.Actions.RETURN);
//				this.check(approvalInfo);				
				//ȡ������
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				lReturn = loanDao.updateStatusID(info);
			}
			else if( !FSWorkflowManager.isAutoCheck() && info.getStatusId()==SECConstant.ApplyFormStatus.APPROVALED)
			{
				//ȡ������
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				lReturn = loanDao.updateStatusID(info);
			}
			*/
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch(SecuritiesException e1){
			this.context.setRollbackOnly();
			throw e1;
		}
		catch (Exception e)
		{
			throw new SecuritiesException();
		}
		return lReturn;		
	}
	
	
	/**
	 * ��˴���ת������
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		Collection conList = null;
		ContractDao conDao = new ContractDao();

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long[] lApprovalContentIDList;



		//ģ������
		long lModuleID = atInfo.getModuleID();
		//ҵ������
		long lLoanTypeID = atInfo.getLoanTypeID();
		//��������
		long lActionID = atInfo.getActionID();
		
		long lApprovalContentID = atInfo.getApprovalContentID();
		long lNextUserID = atInfo.getNextUserID();
		long lApprovalID = atInfo.getApprovalID();
		long lUserID = atInfo.getInputUserID();
		String sOpinion = atInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalDelegation appbiz = new ApprovalDelegation();
		
		lApprovalContentIDList = atInfo.getApprovalContentIDList();
		
		if (lApprovalContentIDList.length > 0)
		{
			try {
				//���ApprovalID
				if (lApprovalID<0){
					lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,atInfo.getOfficeID(),atInfo.getCurrencyID());
				}
			} catch (Exception e1) {
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}
	        
			//�����������
			if (atInfo.getCheckActionID() == Constant.ApprovalDecision.REFUSE) //�ܾ�
			{				
				//�������״̬
				lStatusID = Constant.RecordStatus.VALID;
				//������������
				lResultID = Constant.ApprovalDecision.REFUSE;			
				
				try
				{
					conList = dao.findAttornmentContractByApplyId( lApprovalContentID );
					conDao.setLastAttornment( conList,2 );	
				} catch (SecuritiesDAOException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			if (atInfo.getCheckActionID() == Constant.ApprovalDecision.PASS ) //����
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;				
			}	
			if (atInfo.getCheckActionID() == Constant.ApprovalDecision.FINISH ) //����&&���
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;				
				//������ɺ���Ҫ���Ĳ���
			}
			if (atInfo.getCheckActionID() == Constant.ApprovalDecision.RETURN ) //�޸�
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;				
			}
			atInfo.setApprovalID(lApprovalID);
			atInfo.setResultID(lResultID);
			atInfo.setStatusID(lStatusID);
	        
			lCount = lApprovalContentIDList.length;
			for(int i=0; i<lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					atInfo.setApprovalContentID(lApprovalContentIDList[i]);
					Log.print("atInfo.getApprovalContentID()="+atInfo.getApprovalContentID());
				}
				else
				{
					break;
				}
				//���������
				dao.check(atInfo);				
				
				log4j.debug("saveApprovalTracing begin");
				try {
					appbiz.saveApprovalTracing(atInfo);
				} catch (Exception e) {
					log4j.error("saveApprovalTracing fail");
					e.printStackTrace();
					//added by mzh_fu 2007/08/06
					throw new IRollbackException(context, e.getMessage(), e);
				}
				log4j.debug("saveApprovalTracing end");
			}
		}
		
		
	}
	
	/*************	�Ŵ�����֮�ʲ�ת�ÿ�ʼ	********************/
	/**
	 * �����Ŵ�����֮�ʲ�ת�������ͬ
	 * @param aInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long saveAttormentForLoan(AttornmentApplyInfo info) throws java.rmi.RemoteException,IException
	{
		
		long lReturn = -1;
		lReturn = this.save(info);
		AttornmentContractInfo contractInfo = info.getContractInfo();
		contractInfo.setAttornmentApplyId(lReturn);
		try{
			this.saveAttornmentContract(new AttornmentContractInfo[]{contractInfo} );
		}catch(Exception e){
			//added by mzh_fu 2007/08/06
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}
	
	/**
	 * ȡ���Ŵ�����֮�ʲ�ת��
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public long cancelAttormentForLoan(long oid) throws java.rmi.RemoteException,IException
	{
		long lReturn = 1;
		AttornmentContractDao contractDao = new AttornmentContractDao();
		try
		{
			AttornmentApplyInfo info = this.findAttormentForLoanById(oid);
			this.cancel(oid);
			AttornmentContractInfo contractInfo = new AttornmentContractInfo();
			contractDao.updateStatus(info.getContractInfo().getId(),LOANConstant.AttornmentStatus.CANCEL );
		}
		catch(IException ie)
		{
			//modified by mzh_fu 2007/08/06
			//throw ie;
			throw new IRollbackException(context, ie.getMessage(), ie);
		}
		catch(Exception e)
		{
			//modified by mzh_fu 2007/08/06
			//throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
		}
			
		return lReturn;
	}
	
	/**
	 * ���ұ����Ŵ�����֮�ʲ�ת��������Ϣ
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public AttornmentApplyInfo findAttormentForLoanById(long oid) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyInfo returnInfo = null;
		AttornmentContractDao dao = new AttornmentContractDao();
		try
		{
			returnInfo = dao.findApplyInfo(oid);
		}
		catch(IException ie)
		{
			throw ie;		
		}
		catch(Exception e)
		{
			throw new IException("",e);
		}
		
		return returnInfo;
	}
	
	/**
	 * �޸Ĳ��ұ����Ŵ�����֮�ʲ�ת�������ͬ������Ϣ
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public Collection findAttormentForLoanByMultioption(AttornmentQueryInfo qinfo) throws java.rmi.RemoteException,IException
	{
		Collection c_Return = null;
		AttornmentContractDao dao = new AttornmentContractDao();
		try
		{
			c_Return = dao.findAttormentForLoanByMultioption(qinfo);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			throw new IException("",e);
		}
		return c_Return;
	}	
	
	/**
	 * ���˱����Ŵ�����֮�ʲ�ת������
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void checkAttormentForLoan(AttornmentApplyInfo applyInfo) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao applyDao = new AttornmentApplyDao();
		AttornmentContractDao contractDao = new AttornmentContractDao();
		try
		{
			AttornmentApplyInfo info = new AttornmentApplyInfo() ;
			info.setId(applyInfo.getId());
			info.setNextCheckUserId(applyInfo.getNextCheckUserId());
			info.setStatusId(LOANConstant.AttornmentStatus.CHECK);
			info.setUpdateDate(Env.getSystemDate());
			applyDao.update(info);
			
			AttornmentContractInfo cInfo = new AttornmentContractInfo();
			cInfo.setId(applyInfo.getContractInfo().getId());
			cInfo.setStatusId(LOANConstant.AttornmentStatus.CHECK);		
			contractDao.update(cInfo );
		}
		catch(IException ie)
		{
			//modified by mzh_fu 2007/08/06
			//throw ie;
			throw new IRollbackException(context, ie.getMessage(), ie);
		}
		catch(Exception e)
		{
			//modified by mzh_fu 2007/08/06
		    //throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
		}
	}
	
	/**
	 * ȡ�����˱����Ŵ�����֮�ʲ�ת������
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws IException
	 */
	public void cancelCheckAttormentForLoan(AttornmentApplyInfo applyInfo) throws java.rmi.RemoteException,IException
	{
		AttornmentApplyDao applyDao = new AttornmentApplyDao();
		AttornmentContractDao contractDao = new AttornmentContractDao();
		try
		{
			AttornmentApplyInfo info = new AttornmentApplyInfo() ;
			info.setId(applyInfo.getId());
			info.setNextCheckUserId(-1);
			info.setStatusId(LOANConstant.AttornmentStatus.SUBMIT);
			//info.setUpdateDate(Env.getSystemDate());
			applyDao.update(info);
			
			AttornmentContractInfo cInfo = new AttornmentContractInfo();
			cInfo.setId(applyInfo.getContractInfo().getId());
			cInfo.setStatusId(LOANConstant.AttornmentStatus.SUBMIT);		
			contractDao.update(cInfo );
		}
		catch(IException ie)
		{
			//modified by mzh_fu 2007/08/06
			//throw ie;
			throw new IRollbackException(context, ie.getMessage(), ie);
		}
		catch(Exception e)
		{
			//modified by mzh_fu 2007/08/06
			//throw new IException("",e);
			throw new IRollbackException(context, e.getMessage(), e);
		}
	}
	
	/*************	�Ŵ�����֮�ʲ�ת�ý���	********************/
}
