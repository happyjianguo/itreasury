package com.iss.itreasury.securities.apply.bizlogic;

import javax.ejb.SessionBean;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.craftbrother.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dao.SEC_BidRangeDAO;
import com.iss.itreasury.securities.apply.dao.SEC_BondTypeDAO;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.*;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.*;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApplyBiz implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	/**
	 *������ı������
	 * @throws Exception 
	*/
	public long save(ApplyInfo info) throws Exception
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		CreditSettingDAO cdao = new CreditSettingDAO();
		//DeliveryOrderServiceOperation dso = new DeliveryOrderServiceOperation();
		//UsableCreditLineOfSecuritiesInfo creditInfo1 = new UsableCreditLineOfSecuritiesInfo();
		//UsableCreditLineOfSecuritiesInfo creditInfo2 = new UsableCreditLineOfSecuritiesInfo();
		long lID = -1;
		String strError = "";
		long ret =-1;

		if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
		{
			//�ʽ����ȼ��
			log4j.print("�ʽ����ȼ��");
			strError = dao.checkCapitalLandingCreditExtension(info.getClientId(),info.getCounterpartId(), info.getTransactionTypeId(), info.getPledgeSecuritiesAmount(), info.getId(),info.getTransactionStartDate(),info.getTransactionEndDate(),info.getOfficeId());
			if (strError != null && strError.length() > 0)
			{
				throw new SecuritiesException("Sec_E180", strError, null);
			}
		}

		if (info.getId() < 0)
		{
			try
			{
				//��ȡ��ˮ��
				HashMap map = new HashMap();
				map.put("officeID",String.valueOf(info.getOfficeId()));
				map.put("currencyID",String.valueOf(info.getCurrencyId()));
				map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
				map.put("transTypeID",String.valueOf(info.getTypeId()));
				map.put("actionID",String.valueOf(info.getActionTypeId()));
				map.put("subActionID", String.valueOf(info.getTransactionTypeId()));
				String applyCode=CreateCodeManager.createCode(map);

				info.setCode(applyCode);
				//����һ������������Ϊ1
				info.setNextCheckLevel(1);
				lID = dao.add(info);
				 //modify by xwhe 2007-11-5
                if(info.getCreditId() >0 ){              
                	ret = cdao.updateStatus(info.getCreditId(),CRAconstant.TransactionStatus.USED); 
                	System.out.print("�޸ĵ�����ID��:"+ret);
                          }
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
		}
		else
		{
			try
			{
				dao.update(info);
			}
			catch (ITreasuryDAOException e)
			{
				throw new SecuritiesDAOException(e);
			}
			lID = info.getId();
		}
		return lID;
	}

	/**
	 *�������ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		try
		{
			dao.delete(lID);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *���������˲���
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long[] lApprovalContentIDList;
		String strSQL = "";

		//������Ӧ��������
		//ģ������
		long lModuleID = ATInfo.getModuleID();
		//ҵ������
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//��������
		long lActionID = ATInfo.getActionID();

		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		
		//zpli add 2005-09-14
		long lOfficeID=ATInfo.getOfficeID();
		long lCurrencyID=ATInfo.getCurrencyID();
		////
		String sOpinion = ATInfo.getOpinion();

		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();

		lApprovalContentIDList = ATInfo.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//���ApprovalID
				if (lApprovalID <= 0)
				{
					//zpli modify 2005-09-14
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
			if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //�ܾ�
			{
				//�������״̬
				lStatusID = Constant.RecordStatus.VALID;
				//������������
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //����
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //����&&���
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//������ɺ���Ҫ���Ĳ���
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //�޸�
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.RETURN;
			}
			ATInfo.setApprovalID(lApprovalID);
			ATInfo.setResultID(lResultID);
			ATInfo.setStatusID(lStatusID);

			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lApprovalContentIDList[i] > 0)
				{
					/*
					if (ATInfo.getNextLevel() <= 0)
					{
					    ApplyInfo aInfo = new ApplyInfo();
					    try
					    {
					        aInfo = (ApplyInfo)dao.findByID(lApprovalContentIDList[i],aInfo.getClass());
					    } 
					    catch (ITreasuryDAOException e2)
					    {
					        e2.printStackTrace();
					    }
					    ATInfo.setNextLevel(aInfo.getNextCheckLevel());
					}
					*/
					ATInfo.setApprovalContentID(lApprovalContentIDList[i]);
					Log.print("ATInfo.getApprovalContentID()=" + ATInfo.getApprovalContentID());
				}
				else
				{
					break;
				}
				//���������
				dao.check(ATInfo);

				log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(ATInfo);
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
	 *�������ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		try
		{
			dao.delete(lID);
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *������ĵ��ʲ�ѯ����
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();

		try
		{
			aInfo = (ApplyInfo) dao.findByID(lID, aInfo.getClass());
			//SEC_ApplyForm����û�е��ֶ�
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //ҵ��λ����
			aInfo.setCounterpartCode(NameRef.getCounterpartCodeByID(aInfo.getCounterpartId()));//���׶��ֱ��
			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //���׶�������
			aInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(aInfo.getSecuritiesId())); //֤ȯ����
			aInfo.setAccountNo(NameRef.getAccountNobyAccountID(aInfo.getAccountId())); //�ʽ��˻�����
			aInfo.setAccountName(NameRef.getAccountNameById(aInfo.getAccountId())); //�ʽ��˻�����
			aInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(aInfo.getAccountId())); //�ɶ��˻�����
			aInfo.setStockName(NameRef.getSecuritiesNameByID(aInfo.getStockId())); //ת�ɹ�Ʊ����
			if (aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || aInfo.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				aInfo = dao.findCounterpartCreditInfo(aInfo);
			}
			if (dao.checkTransactionTypeID(aInfo.getTransactionTypeId()))
			{
				aInfo.setCapitalLandingCreditExtensionMessage(
					dao.checkApplyCreditExtension(aInfo.getSecuritiesId(), Long.valueOf(NameRef.getSecuritiesTypeIDBySecuritiesID(aInfo.getSecuritiesId())).longValue(), aInfo.getAmount()));
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return aInfo;
	}
	 /*������ĵ��ʲ�ѯ����
	    */
	    public ApplyInfo findByCreditId(long lID) throws java.rmi.RemoteException, SecuritiesException, LoanDAOException
	    {
	    	ApplyInfo info = new ApplyInfo();
	    	SEC_ApplyDAO dao = new SEC_ApplyDAO();
			try
			{
				info = (ApplyInfo) dao.findByCreditID(lID);
				
			}
			catch (Exception e)
			{
				throw new SecuritiesDAOException("",e);
			}
	            
	       
	        return info;
	    }
	    
	/**
	 *������Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApprovalBiz appBiz = new ApprovalBiz();
		String strUser = "";
		long lModuleID = -1;
		long lLoanTypeID = -1;
		long lActionID = -1;
		long lApprovalID = -1;
		
		//zpli add 2005-09-14		
		lModuleID=qInfo.getModuleId();
		lLoanTypeID=qInfo.getLoanTypeId();
		lActionID=qInfo.getActionId();
		long lOfficeID=qInfo.getOfficeId();
		long lCurrencyID=qInfo.getCurrencyId();
		////
		
		try
		{
			try
			{
				lApprovalID = qInfo.getApprovalId();
				//���ApprovalID
				if (lApprovalID <= 0)
				{
					//zpli modify 2005-09-14
					lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1,qInfo.getOfficeId(),qInfo.getCurrencyId());
					//lApprovalID = NameRef.getApprovalIDByTransactionTypeID(qInfo.getTransactionTypeId(), 1);
				}
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}
			try
			{
				//������������������¼���ˣ���ʵ�򴫸���������ˣ���
				//zpli modify 2005-09-14
				strUser = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,qInfo.getUserId());
				//strUser = appBiz.findTheVeryUser(lApprovalID, qInfo.getUserId());
			}
			catch (Exception e2)
			{
				log4j.error("findTheVeryUser fail");
				e2.printStackTrace();
			}
			qInfo.setStrUser(strUser);
			c = dao.findByMultiOption(qInfo);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *�������µ�Ͷ�����䱣�����
	*/
	public long saveBidRange(BidRangeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		SEC_ApplyDAO applydao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();
		long lID = -1;

		try
		{
			System.out.println("�ҵ�����");
			dao.setUseMaxID();
			lID = dao.add(info);

			/*
			aInfo.setId(info.getApplyFormId());
			//aInfo.setAmount(dao.findMaxAmount(info.getApplyFormId()));
			if (aInfo.getAmount() > 0)
			{
				applydao.update(aInfo);
			}
			*/
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return lID;
	}

	/**
	 *�������µ�Ͷ�������ѯ����
	*/
	public Collection findBidRangeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		try
		{
			c = dao.findByApplyID(lApplyID);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *�������µ�Ͷ������ɾ������
	*/
	public void deleteBidRange(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		long lCount = 0;
		SEC_BidRangeDAO dao = new SEC_BidRangeDAO();
		SEC_ApplyDAO applydao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();
		BidRangeInfo bInfo = new BidRangeInfo();
		try
		{
			if (lIDList[0] > 0)
			{
				bInfo = (BidRangeInfo) dao.findByID(lIDList[0], bInfo.getClass());
			}
			lCount = lIDList.length;
			for (int i = 0; i < lCount; i++)
			{
				if (lIDList[i] > 0)
				{
					dao.delete(lIDList[i]);
				}
			}
			/*
			if (bInfo.getApplyFormId() > 0)
			{
				aInfo.setId(bInfo.getApplyFormId());
				//aInfo.setAmount(dao.findMaxAmount(bInfo.getApplyFormId()));
				if (aInfo.getAmount() > 0)
				{
					applydao.update(aInfo);
				}
			}
			*/
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
	}

	/**
	 *�������µ�ծȯ���ౣ�����
	*/
	public long saveBondType(BondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		long lID = -1;

		try
		{
			if (info.getId() < 0)
			{
				dao.setUseMaxID();
				info.setStatusId(1);
				lID = dao.add(info);
			}
			else
			{
				info.setStatusId(1);
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
	 *�������µ�ծȯ�����ѯ����
	*/
	public Collection findBondTypeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		Collection c = null;
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		try
		{
			c = dao.findByApplyID(lApplyID);
		}
		catch (SecuritiesDAOException e)
		{
			throw new SecuritiesException("", e);
		}
		return c;
	}

	/**
	 *�������µ�ծȯ����ɾ������
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		long lCount = 0;
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
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

	/**
	* ת�������������Ȩ�ޣ�֧������ת��
	* @param lID
	* @param lUserID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferApplyRight(long[] lID, long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				if (lID[i] > 0 && lUserID > 0)
				{
					ApplyInfo info = new ApplyInfo();
					info.setId(lID[i]);
					info.setInputUserId(lUserID);
					dao.update(info);
				}
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
		}
	}
	/**
	 * ����ʽ������Ŷ��
	 * @param counterpartID	���׶���
	 * @param transactionTypeID ��������
	 * @param newPledgeSecuritiesAmount �����
	 * @param lApplyID �������ʾ
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public boolean checkTransactionTypeID(long lTransactionTypeID) throws java.rmi.RemoteException, SecuritiesException
	{
		boolean bResult = false;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		try
		{
			bResult = dao.checkTransactionTypeID(lTransactionTypeID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}

		return bResult;
	}
	/**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(ApplyInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			
			lReturnId = save(info);
			SEC_ApplyDAO dao = new SEC_ApplyDAO();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(lReturnId));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// �ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// ����״̬��"������"
			dao.updateStatusID(lReturnId,SECConstant.ApplyFormStatus.APPROVALING);
			
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
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
    public long examinePass(ApplyInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransApplyID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SEC_ApplyDAO dao = new SEC_ApplyDAO();
   				//TransDiscountApplyInfo	appInfo=dao.findByID(info.getId());
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
   					dao.updateStatusID(
   							lTransApplyID,
   							SECConstant.ApplyFormStatus.CHECKED);
   					
   					//������������Ϣ���Ƶ���ͬ��				  		
   					dao.doCheckOver(lTransApplyID);
   				}
   				//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
   				else if(returnInfo.isRefuse())
   				{
   					dao.updateStatusID(
   							lTransApplyID,
   							SECConstant.ApplyFormStatus.SUBMITED);
   				}	
   		 
   	 }
   	 catch(Exception e){
   		 throw new IRollbackException(mySessionCtx, e.getMessage(), e); 
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
    public long updateApplyAndApprovalInit(ApplyInfo info)
			throws RemoteException, IRollbackException {
		long lReturnId = -1;
		try {
	
			SEC_ApplyDAO dao = new SEC_ApplyDAO();
			lReturnId = save(info);
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(info.getId()));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
			// �ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// ����״̬��"������"
			dao.updateStatusID(info.getId(),SECConstant.ApplyFormStatus.APPROVALING);
			
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
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
	public long cancelApproval(ApplyInfo info)throws RemoteException, LoanException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		boolean ifUsed=false;
		try{
			ifUsed=dao.checkStatuID(info.getId());
		}catch (LoanDAOException e) {
			throw new LoanException();
		}
		if(ifUsed){
			throw new LoanException("����ȡ�����,��ȡ������!",null);
		}
		try
		{
			dao.canclediliver(info.getId());
			//ȡ������
			lReturn = dao.updateStatusID(info.getId(),SECConstant.ApplyFormStatus.SUBMITED);
			
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
			throw new LoanException();
		}
		return lReturn;
	}

	/**
	 *������ĵ��ʲ�ѯ����
	*/
	public ApplyInfo findByCode(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();

		try
		{
			aInfo = (ApplyInfo) dao.findByID(lID, aInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return aInfo;
	}

}
