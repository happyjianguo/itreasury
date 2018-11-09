package com.iss.itreasury.securities.securitiescontract.bizlogic;

/**
 * Created 2004-3-15 15:01:53
 * Code generated by the Sun ONE Studio EJB Builder
 * @author cpf
 */

import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiescontract.dao.*;
import com.iss.itreasury.securities.securitiescontractplan.dao.*;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.loan.attornmentapply.dao.*;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.util.Constant;

public class SecuritiesContractEJB implements javax.ejb.SessionBean
{
	private javax.ejb.SessionContext context;

	/**
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(javax.ejb.SessionContext aContext)
	{
		context = aContext;
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
	 * See section 7.10.3 of the EJB 2.0 specification
	 */
	public void ejbCreate() throws javax.ejb.CreateException
	{

	}

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

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
			ContractBalanceInfo info = dao.getContractBalance(lID);
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
		SEC_ApplyDAO appdao = new SEC_ApplyDAO();
		SecuritiesContractInfo aInfo = new SecuritiesContractInfo();

		try
		{
			aInfo = (SecuritiesContractInfo) dao.findByID(lID, aInfo.getClass());
			appdao.delete(aInfo.getApplyId());
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
			c = dao.findByMultiOption(qInfo);
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

	public static void main(String[] args)
	{
		try
		{
			long[] lID = { 641,642 };
			long lUserID = 7;
			SecuritiesContractEJB ejb = new SecuritiesContractEJB();
			System.out.println("begin");
			ejb.transferContractRight(lID, lUserID);
			System.out.println("end");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}