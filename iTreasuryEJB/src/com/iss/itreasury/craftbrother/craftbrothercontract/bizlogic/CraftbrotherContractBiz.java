package com.iss.itreasury.craftbrother.craftbrothercontract.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection; 

import javax.ejb.SessionContext;

import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;

import com.iss.itreasury.dao.ITreasuryDAOException;
//import com.iss.itreasury.securities.securitiescontract.bizlogic.SecuritiesContractEJB;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.securities.securitiescontract.dao.*;
import com.iss.itreasury.securities.securitiescontractplan.dao.*;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.loan.attornmentapply.dao.*;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.UtilOperation;

public class CraftbrotherContractBiz 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;

	/**
	 * ���ҵ�����ͬ��Ϣ
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		SecuritiesContractInfo aInfo = new SecuritiesContractInfo();
		SecuritiesContractPlanDao pDao = new SecuritiesContractPlanDao();

		try
		{
			aInfo = (SecuritiesContractInfo) dao.findByID(lID, aInfo.getClass());
			//infoû�е��ֶ�
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //ҵ��λ����
			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //���׶�������
			aInfo.setCounterpartCode(NameRef.getCounterpartCodeByID(aInfo.getCounterpartId())); //���׶��ֱ��
			aInfo.setInputUserName(NameRef.getUserNameCodeByID(aInfo.getInputUserId())); //¼��������
			aInfo.setNextCheckUserName(NameRef.getUserNameCodeByID(aInfo.getNextCheckUserId())); //��һ�����������

			ApprovalBiz appbiz = new ApprovalBiz();
			//zpli modify 2005-09-14
			long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3,aInfo.getOfficeId(),aInfo.getCurrencyId());
			//long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3);
			
			//zpli modify 2005-09-16 lModuleID,lLoanTypeID,lActionID, lOfficeID, lCurrencyID, lApprovalContentID
			//aInfo.setLastCheckUserName(appbiz.getLastCheckPerson(approvalID, lID));
			aInfo.setLastCheckUserName(appbiz.getLastCheckPerson(Constant.ModuleType.SECURITIES,SECUtil.getBusinessIDByTransactionID(aInfo.getTransactionTypeId()) ,aInfo.getTransactionTypeId(),aInfo.getOfficeId(),aInfo.getCurrencyId(),lID));
			
			
			aInfo.setUserCheckLevel(appbiz.findApprovalUserLevel(approvalID, aInfo.getNextCheckUserId()));

			
			
			//����ͬ����ֵ��Ϊ��֪ͨ�����ԣ��Ժ��ٸģ�by fanyang
		//	ContractBalanceInfo info = dao.getContractBalance(lID);
		//	modify by xwhe 2007-12-4
			ContractBalanceInfo info = new ContractBalanceInfo();
			if(aInfo.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY){
				info = dao.getContractAmount(lID);
			}
			else if(aInfo.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY || aInfo.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.PAYBACK_NOTIFY){
				info = dao.getContractBalanceInfo(lID, true);
			}
			else{
				info = dao.getContractBalance(lID);
			}
			aInfo.setBalance(info.getBalance());
			aInfo.setReceivedAmount(info.getTotalReceivedAmount());
			aInfo.setBuyBackAmount(info.getTotalPaiedAmount());

			java.util.Collection planCol = pDao.findPlanDetailByContract(lID);
			if (planCol != null)
				aInfo.setPlanDetailCount(planCol.size());

		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return aInfo;

	}
    
	public AttornmentApplyInfo findAttornmentInfoByApplyID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		AttornmentApplyInfo c = null;

		try
		{
			c = dao.findAttornmentByRepurchaseID(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return c;

	}
	
	public Collection findAttornmentByApplyID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		Collection c = null;

		try
		{
			c = dao.findContractByRepurchaseID(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return c;

	}
	
	/**
	 * �����ͬ��Ϣ���������޸ĺ�ͬ
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		long ret = -1;

		try
		{
			if (info.getId() < 0)
			{
				dao.setUseMaxID();
				info.setCode(dao.getContractCode(info.getTransactionTypeId()));
				info.setNextCheckLevel(1);
				ret = dao.add(info);
			}
			else
			{
				if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY
						|| info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
				{
					int interestDays = 0;
					double interest = 0;
					if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY)
					{
						//ת��������ع�����֮��������
						interestDays = DataFormat.getIntervalDays(info
								.getTransactionStartDate(), info.getTransactionEndDate());
						//ת�����ڵ��ع�����֮�����Ϣ
						interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(
								UtilOperation.Arith
										.mul(info.getPrice(), info.getIncomeRate()),
								interestDays), 36000);
					}
					info.setTransferBalance(info.getPrice());  //ת�ÿ������
					info.setAvailableTransferBalance(info.getPrice());  //ת�ÿ���������
					info.setRepurchaseBalance(info.getPrice());  //���ع����
					info.setAvailableRepurchaseBalance(info.getPrice());  //���ع��������
					info.setInterestBalance(interest);//ת�����ڵ��ع�����֮�����Ϣ
					info.setReceivedInterest(0);  //������Ϣ�����տ
					info.setWaitReceivedInterest(0);  //������Ϣ�����տ
					info.setPaidInterest(0);  //�Ѹ���Ϣ����֧����
					info.setWaitPaidInterest(0);  //�Ѹ���Ϣ����֧����
				}
				
				dao.update(info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException("", e);
		}
		return ret;
	}

	/**
	 * ȡ����ͬ��ͬʱȡ��������
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		//SEC_ApplyDAO appdao = new SEC_ApplyDAO();
		//SecuritiesContractInfo aInfo = new SecuritiesContractInfo();

		try
		{
			//aInfo = (SecuritiesContractInfo) dao.findByID(lID, aInfo.getClass());
			//appdao.delete(aInfo.getApplyId());
			dao.updateStatus(lID, SECConstant.ContractStatus.CANCEL);

		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException("", e);
		}

		return -1;
	}

	/**
	 * ���ҷ��������ĺ�ͬ��Ϣ�������޸Ĳ��Һ���˲���
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		java.util.Collection c = null;
		SecuritiesContractDao dao = new SecuritiesContractDao();

		try
		{
			c = dao.findByMultiOption2(qInfo);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
		}

		return c;
	}

	/**
	 * ������ͬ
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();

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
		
		//zpli add 2004-09-14
		long lOfficeID=atInfo.getOfficeID();
		long lCurrencyID=atInfo.getCurrencyID();
		////
		
		String sOpinion = atInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();

		lApprovalContentIDList = atInfo.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//���ApprovalID
				if (lApprovalID < 0)
				{
					//zpli modify 2004-09-14
					lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID);
					//lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID);

				}
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}

			//�����������
			if (atInfo.getCheckActionID() == SECConstant.Actions.REJECT) //�ܾ�
			{
				//�������״̬
				lStatusID = Constant.RecordStatus.VALID;
				//������������
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.CHECK) //����
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //����&&���
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//������ɺ���Ҫ���Ĳ���
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.RETURN) //�޸�
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}
			atInfo.setApprovalID(lApprovalID);
			atInfo.setResultID(lResultID);
			atInfo.setStatusID(lStatusID);

			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					atInfo.setApprovalContentID(lApprovalContentIDList[i]);
					Log.print("atInfo.getApprovalContentID()=" + atInfo.getApprovalContentID());
				}
				else
				{
					break;
				}
				//���������
				dao.check(atInfo);

				log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(atInfo);
				}
				catch (Exception e)
				{
					log4j.error("saveApprovalTracing fail");
					e.printStackTrace();
				}
				log4j.debug("saveApprovalTracing end");
			}
		}

	}

	/**
	 * �����ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				dao.updateStatus(lID[i], SECConstant.ContractStatus.NOTACTIVE);
				if (lID[i] <= 0)
					break;

			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
		}
	}
	
	/**
	 * �����ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[], long lTransactionTypeId) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
					dao.updateStatus(lID[i], SECConstant.ContractStatus.NOTACTIVE);	
				if (lID[i] <= 0)
					break;

			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
		}
	}

	/**
		 * ת�ƺ�ͬ������Ȩ�ޣ�֧������ת��
		 * @param lID
		 * @param lUserID
		 * @throws java.rmi.RemoteException
		 * @throws SecuritiesException
		 */
	public void transferContractRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		try
		{
			SecuritiesContractDao cDao = new SecuritiesContractDao();
			SEC_ApplyDAO aDao = new SEC_ApplyDAO();
			long applyID = -1;

			for (int i = 0; i < lID.length; i++)
			{

				if (lID[i] > 0 && lUserID > 0)
				{
					//�޸ĺ�ͬ��������Ϣ
					SecuritiesContractInfo info = new SecuritiesContractInfo();
					info.setId(lID[i]);
					info.setInputUserId(lUserID);
					cDao.update(info);
					
					//�޸������������Ϣ
					info = findByID(lID[i]);
					applyID = info.getApplyId();
					ApplyInfo aInfo = new ApplyInfo();
					aInfo.setId(applyID);
					aInfo.setInputUserId(lUserID);
					aDao.update(aInfo);
				}

			}
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException("", e);
		}
	}

	/**
	 * �ֶ�������ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		AttornmentApplyDao attDao = new AttornmentApplyDao();
		SecuritiesContractInfo info = null;

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				if (lID[i]<=0) continue;
				info=(SecuritiesContractInfo)dao.findByID(lID[i],SecuritiesContractInfo.class );
				if (info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE)
				{
					attDao.endForRepurchase( info.getAccountId() );
				}
				dao.updateStatus(lID[i], SECConstant.ContractStatus.FINISH);
				//���¿��
				dao.saveStockByContractId(lID[i]);
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
			catch (Exception e)
			{
				e.printStackTrace() ;
				throw new java.rmi.RemoteException();
			}
		}
		/**TODO �ֶ�������ͬ�Ƿ�Ҫ���μ�⣿������ͬ�Ƿ�Ӱ������ģ�飿**/
	}

	/**
	 *��ͬ�µ�ծȯ���ౣ�����
	*/
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		ContractBondTypeDao dao = new ContractBondTypeDao();
		long lID = -1;

		try
		{
			if (info.getId() < 0)
			{
				dao.setUseMaxID();
				lID = dao.add(info);
			}
			else
			{
				dao.update(info);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return lID;
	}

	/**
	 *��ͬ�µ�ծȯ�����ѯ����
	*/
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		java.util.Collection c = null;
		ContractBondTypeDao dao = new ContractBondTypeDao();
		try
		{
			c = dao.findByContractID(lApplyID);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *��ͬ�µ�ծȯ����ɾ������
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		long lCount = 0;
		ContractBondTypeDao dao = new ContractBondTypeDao();
		try
		{
			lCount = lIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lIDList[i] > 0)
				{
					dao.delete(lIDList[i]);
				}
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}
	
	public long countSecuritiesContractInterest(SecuritiesContractInterestInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
	    long lResult = -1;
	    SecuritiesContractInterestDao dao = new SecuritiesContractInterestDao();
	    
		try
		{
		    lResult = dao.countSecuritiesContractInterest(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return lResult;

	}
	/**
     * added by xwhe 2007/06/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(SecuritiesContractInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			
			lReturnId = save(info);
			SecuritiesContractDao dao = new SecuritiesContractDao();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		    //inutParameterInfo.setTransID(String.valueOf(lReturnId));
		    //inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// �ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// ����״̬��"������"
			dao.update(info.getId(), info.getInputUserId(),SECConstant.ContractStatus.APPROVALING);
			
		} catch (Exception e) {
			throw new IRollbackException(context, e.getMessage(), e);
		}

		return lReturnId;
	} 
    
    /**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long examinePass(SecuritiesContractInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransDiscountContractID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SecuritiesContractDao dao = new SecuritiesContractDao();
   				//TransDiscountContractInfo	appInfo=dao.findByID(info.getId());
                //lLoanTypeID=appInfo.getTypeID();
   				//lLoanTypeID=appInfo.getSubTypeId();
   				//long status=appInfo.getStatusId() ;
   				

   				//---- added by xwhe 2007/09/12 ������ begin
   				
   				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
   				InutParameterInfo returnInfo = new InutParameterInfo();
   				
   				//��ҵ���¼����pinfo,ת���ɱ�׼map���ݵ�����������
   				inutParameterInfo.setDataEntity(info);
   				
   				//�ύ����
   				returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
   				
   				//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
   				if(returnInfo.isLastLevel())
   				{	
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.ContractStatus.CHECK);
   					
   					//�������Ժ󣬰Ѹú�ͬ�µ������Ʊ����Ϊ����״̬				  		
   					//dao.doAfterCheckOver(lTransDiscountContractID);
   				}
   				//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							LOANConstant.LoanStatus.SAVE);
   				}	
   		 
   	 }
   	 catch(Exception e){
   		 throw new IRollbackException(context, e.getMessage(), e); 
   	 }
   	 return lReturnId;
    }
    /**
	 * Modify by xwge date 2007/09/10
	 * ��������ȡ����������
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(SecuritiesContractInfo info)throws java.rmi.RemoteException, SecuritiesException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SecuritiesContractDao dao = new SecuritiesContractDao();
		boolean ifUsed=false;
		try{
			ifUsed=dao.checkStatuID(info.getId());
		}catch (SecuritiesDAOException e) {
			throw new SecuritiesException();
		}
		if(ifUsed){
			throw new SecuritiesException("����ȡ��֪ͨ��,��ȡ����ͬ!",null);
		}
		try
		{
			dao.cancelNotice(info.getId());
			//ȡ������
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.ContractStatus.SAVE);
			
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
			throw new SecuritiesException();
		}
		return lReturn;
	}   
	
	/**
	 * �����ʲ�ת�ú�ͬת�ÿ������
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateTransferBalance(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateTransferBalance(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬת�ÿ���������
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateAvailableTransferBalance(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateAvailableTransferBalance(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ���ع����
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateRepurchaseBalance(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateRepurchaseBalance(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ���ع��������
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateAvailableRepurchaseBalance(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateAvailableRepurchaseBalance(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ������Ϣ�����տ
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateReceivedInterest(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateReceivedInterest(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ������Ϣ�����տ
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateWaitReceivedInterest(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateWaitReceivedInterest(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ�Ѹ���Ϣ����֧����
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updatePaidInterest(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updatePaidInterest(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת�ú�ͬ�Ѹ���Ϣ����֧����
	 * @param lContractID
	 * @param dAmount
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SecuritiesException
	 */
	public long updateWaitPaidInterest(long lContractID, double dAmount) throws RemoteException, IRollbackException, SecuritiesException
	{
		long lReturn = -1;
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			lReturn = dao.updateWaitPaidInterest(lContractID, dAmount);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
		return lReturn;
	}
	
	/**
	 * �����ʲ�ת��֪ͨ�������ͣ�����򱣴沢�ύ֪ͨ��ʱ�����ʲ�ת�ú�ͬ�ĸ��������Ϣ
	 * ��ת�ÿ�����ת�ÿ���������������������ؿ�����������Ϣ�����տ��������Ϣ�����տ���Ѹ���Ϣ����֧�������Ѹ���Ϣ����֧������
	 * @param lNoticeTypeId
	 */
	public void submitBalanceAndInterestOfContract(NoticeInfo nInfo) throws RemoteException, IRollbackException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			dao.submitBalanceAndInterestOfContract(nInfo);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
	}
	
	/**
	 * �����ʲ�ת��֪ͨ�������ͣ�ȡ��֪ͨ��ʱ�����ʲ�ת�ú�ͬ�ĸ��������Ϣ
	 * ��ת�ÿ�����ת�ÿ���������������������ؿ�����������Ϣ�����տ��������Ϣ�����տ���Ѹ���Ϣ����֧�������Ѹ���Ϣ����֧������
	 * @param lNoticeTypeId
	 */
	public void cancelBalanceAndInterestOfContract(NoticeInfo nInfo)  throws RemoteException, IRollbackException, SecuritiesException
	{
		SecuritiesContractDao dao = new SecuritiesContractDao();
		try
		{
			dao.cancelBalanceAndInterestOfContract(nInfo);
		}
		catch (SecuritiesDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}catch (Exception e)
		{
			e.printStackTrace() ;
			throw new java.rmi.RemoteException();
		}
	}
}
