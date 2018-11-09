/*
 * Created on 2004-11-29
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.assuremanagementnotice.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.loan.aheadrepaynotice.dao.AheadRepayNoticeDao;
import com.iss.itreasury.loan.aheadrepaynotice.dataentity.AheadRepayNoticeInfo;
import com.iss.itreasury.loan.assurechargenotice.dao.AssureChargeNoticeDao;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dao.AssureManagementNoticeDao;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementQueryInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssureManagementNoticeEJB implements SessionBean
{
	/* Methods required by SessionBean Interface. EJB 1.1 section 6.5.1. */
	/**
	 * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	private static Log4j log4j = null;
	/**
	 * No argument constructor required by container.
	 */
	public AssureManagementNoticeEJB()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
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
	 * ֪ͨ���ı������
	*/
	public long save(AssureManagementNoticeInfo info) throws java.rmi.RemoteException, LoanException
	{
		long lReturn = -1;
		AssureManagementNoticeDao dao = null;

		dao = new AssureManagementNoticeDao();

		try
		{
			/*����֪ͨ����*/
			if (info.getId() <= 0)
			{
				/*����֪ͨ����*/
				dao.setUseMaxID();
				lReturn = dao.add(info);
				//dao.updateAssureManagementNoticeStatus(lReturn, LOANConstant.AssureManagementNoticeStatus.SUBMIT);
			}
			else if (info.getId() > 0)
			{
				/*����֪ͨ����*/
				dao.update(info);
				lReturn = info.getId();
				
			}
            InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			
			if(inutParameterInfo != null){
				inutParameterInfo.setTransID(String.valueOf(lReturn));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturn);
				inutParameterInfo.setDataEntity(info);
				
				// �ύ����
				FSWorkflowManager.initApproval(inutParameterInfo);
				
			    dao.updateAssureManagementNoticeStatus(lReturn, LOANConstant.AssureChargeNoticeStatus.APPROVALING);
			}
		}
		catch (Exception e)
		{
			//modified by mzh_fu 2007/08/06
			//throw new LoanException("Gen_E001", e);
			throw new LoanException(e.getMessage(), e,context);
		}
		return lReturn;
	}

	/**
	 * ֪ͨ������˲���
	*/
	public void check(ApprovalTracingInfo info) 
	throws java.rmi.RemoteException, LoanException
	{
		AssureManagementNoticeDao dao = null;

		dao = new AssureManagementNoticeDao();

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long lApprovalID = -1;
		long[] lApprovalContentIDList;

		//������Ӧ��������
		//ģ������
		long lModuleID = LOANConstant.ModuleType.LOAN;
		info.setModuleID(lModuleID);
		//ҵ������
//		long lLoanTypeID = LOANConstant.ApprovalLoanType.DB;
//		info.setLoanTypeID(lLoanTypeID);
		//��������
		long lActionID = LOANConstant.ApprovalAction.ASSURE_MANAGEMENT_NOTICE;
		info.setActionID(lActionID);
        
		String sContractID = LOANNameRef.getNameByID("contractid","LOAN_ASSUREMANAGEMENTFORM","id",String.valueOf(info.getApprovalContentIDList()[0]),null);
		long conID = -1;
		if( sContractID != null && sContractID.length() > 0 )
		{
			conID = Long.valueOf(sContractID).longValue();
		}
		
		long lLoanTypeID = -1;
		String sSubType = LOANNameRef.getSubTypeByContractID(conID);
		if( sSubType != null && sSubType.length() > 0 )
		{
			lLoanTypeID = Long.valueOf(sSubType).longValue();
		}
		info.setLoanTypeID(lLoanTypeID);

		ApprovalDelegation appbiz = new ApprovalDelegation();

		lApprovalContentIDList = info.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//���ApprovalID
				lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,info.getOfficeID(),info.getCurrencyID());
				info.setApprovalID(lApprovalID);
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}

			//�����������
			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //�ܾ�
			{
				//�������״̬
				lStatusID = Constant.RecordStatus.VALID;
				//������������
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //����
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //����&&���
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//������ɺ���Ҫ���Ĳ���
			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //�޸�
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}
			info.setApprovalID(lApprovalID);
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);

			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					info.setApprovalContentID(lApprovalContentIDList[i]);
					//Log.print("ATInfo.getApprovalContentID()="+info.getApprovalContentID());
				}
				else
				{
					break;
				}
				//���֪ͨ��
				dao.check(info);

				//log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(info);
				}
				catch (Exception e)
				{
					log4j.error("saveApprovalTracing fail");
					//modified by mzh_fu 2007/08/06
					//e.printStackTrace();
					throw new LoanException(e.getMessage(), e,context);
				}
				//log4j.debug("saveApprovalTracing end");
			}
		}
	}

	/**
	 * ֪ͨ����ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
		AssureManagementNoticeDao dao = null;
		dao = new AssureManagementNoticeDao();
		AssureManagementNoticeInfo info = new AssureManagementNoticeInfo();

		try
		{			
		    info.setId(lID);
		    info.setStatusID(LOANConstant.AssureManagementNoticeStatus.CANCEL);
		    dao.update(info);
		    dao.doAfterCancel(lID);
		}
		catch (Exception e)
		{
			//modified by mzh_fu 2007/08/06
			//throw new LoanException("Gen_E001", e);
			throw new LoanException(e.getMessage(), e,context);
			
		}
	}

	/**
	 * ֪ͨ���ĵ��ʲ�ѯ����
	*/
	public AssureManagementNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
		AssureManagementNoticeInfo returnInfo = new AssureManagementNoticeInfo();
		AssureManagementNoticeDao dao = null;

		dao = new AssureManagementNoticeDao();

		try
		{
		    returnInfo = (AssureManagementNoticeInfo) dao.findByID(lID,returnInfo.getClass());
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);			
		}

		return returnInfo;
	}

	/**
	 * ֪ͨ���Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(AssureManagementQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
		Collection c = null;
		AssureManagementNoticeDao dao = null;

		dao = new AssureManagementNoticeDao();

		long lApprovalID = -1;
		String strUser = "";

		//������Ӧ��������
		//ģ������
		long lModuleID = LOANConstant.ModuleType.LOAN;
		//ҵ������
		long lLoanTypeID = LOANConstant.ApprovalLoanType.DB;
		//��������
		long lActionID = LOANConstant.ApprovalAction.ASSURE_MANAGEMENT_NOTICE;

		ApprovalDelegation appBiz = new ApprovalDelegation();

		try
		{
		    lApprovalID = appBiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID());;
		}
		catch (Exception e1)
		{
			log4j.error("getApprovalID fail");
			e1.printStackTrace();
		}
		try
		{
			//������������������¼���ˣ���ʵ�򴫸���������ˣ���
//			strUser = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(), qInfo.getUserID());
			LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
			String strUserID = " ( 0 ) ";		
			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
					qInfo.getOfficeID(),qInfo.getCurrencyID(),
					new long[] {  LOANConstant.LoanType.DB });
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				strUserID = "( ";
				for (int i = 0; i < a_SubLoanType.length; i++) {
					strUserID = strUserID
							+ " (a.NEXTCHECKUSERID in "
							+ appBiz.findTheVeryUser(lModuleID,
									a_SubLoanType[i], lActionID ,qInfo.getOfficeID(),qInfo.getCurrencyID(), qInfo.getUserID())
							+ " and b.NSUBTYPEID = " + a_SubLoanType[i] + ")";
					if (i < a_SubLoanType.length - 1)
						strUserID += " or ";
					else
						strUserID += " ) ";
				}
			}else{
				return null;
			}
			
			qInfo.setStrUser(strUserID);
		}
		catch (Exception e2)
		{
			log4j.error("findTheVeryUser fail");
			e2.printStackTrace();
		}
		
		try
		{
		    c = dao.findByMultiOption(qInfo);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
		
		return c;
	}
	/**
	 * ��֤��������ͨ�����߾ܾ�
	 * @param nInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws IException
	 */
	public long doApprovalAssureManagementNotice4Decognizance(AssureManagementNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			
			AssureManagementNoticeDao assureManagementNoticeDao = new AssureManagementNoticeDao();
			AssureManagementNoticeInfo nInfo1 = new AssureManagementNoticeInfo();
			nInfo1 = assureManagementNoticeDao.findByID(nInfo.getId());
		
			
			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
		
			// ��ҵ���¼����nInfo1,ת���ɱ�׼map���ݵ�����������
			inutParameterInfo.setDataEntity(nInfo1);
		
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
				long id=nInfo.getId();
		
				//����֤����Ϊ"0"��ʱ������ͨ���޸�״̬Ϊ"��ʹ��"
				long statusID = -1;
				if ( nInfo.getRecognizanceAmount() == 0 && nInfo.getAssureChargeAmount() == 0 )
				{
					statusID = LOANConstant.AssureManagementNoticeStatus.USED;
				}
				else
				{
					statusID = LOANConstant.AssureManagementNoticeStatus.CHECK;
				}
				
				lResult = assureManagementNoticeDao.updateAssureManagementNoticeStatus(id, statusID);		
				//������ɺ���Ҫ���Ĳ���
			}
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) {
				lResult = assureManagementNoticeDao.updateAssureManagementNoticeStatus(nInfo.getId(),LOANConstant.AssureManagementNoticeStatus.SUBMIT);
				// ���ܾ�ʱӦ������Ĺ���
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IRollbackException(context, e.getMessage(), e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	
	public long doApprovalAssureManagementNotice(AssureManagementNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			
			AssureManagementNoticeDao assureManagementNoticeDao = new AssureManagementNoticeDao();
			AssureManagementNoticeInfo nInfo1 = new AssureManagementNoticeInfo();
			nInfo1 = assureManagementNoticeDao.findByID(nInfo.getId());
		
			
			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
		
			// ��ҵ���¼����nInfo1,ת���ɱ�׼map���ݵ�����������
			inutParameterInfo.setDataEntity(nInfo1);
		
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
				long lll=nInfo.getId();
		
				//Modify Boxu 2007-12-7 ����֤������������ѽ�Ϊ"0"��ʱ������ͨ���޸�״̬Ϊ"��ʹ��"
				long statusID = -1;
				if ( nInfo.getRecognizanceAmount() == 0 && nInfo.getAssureChargeAmount() == 0 )
				{
					statusID = LOANConstant.AssureManagementNoticeStatus.USED;
				}
				else
				{
					statusID = LOANConstant.AssureManagementNoticeStatus.CHECK;
				}
				
				lResult = assureManagementNoticeDao.updateAssureManagementNoticeStatus(lll, statusID);		
				//������ɺ���Ҫ���Ĳ���
			}
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) {
				
				lResult = assureManagementNoticeDao.updateAssureManagementNoticeStatus(nInfo.getId(),LOANConstant.AssureManagementNoticeStatus.SUBMIT);
				
				// ���ܾ�ʱӦ������Ĺ���

			}
		
		
		
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IRollbackException(context, e.getMessage(), e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * ��������ȡ���������������� . ������
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(AssureManagementNoticeInfo nInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
		AssureManagementNoticeDao assureManagementNoticeDao = new AssureManagementNoticeDao();
		
		try
		{
			//ȡ������
			lReturn = assureManagementNoticeDao.updateStatusAndCheckStatus(nInfo.getId(),LOANConstant.AssureManagementNoticeStatus.SUBMIT);
			
			if(lReturn > 0){
				//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}				
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}
}
