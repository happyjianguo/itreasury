package com.iss.itreasury.craftbrother.apply.bizlogic;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.SessionContext;

import com.iss.itreasury.craftbrother.apply.dao.CraLoanContentDao;
import com.iss.itreasury.craftbrother.apply.dao.CreditCheckDAO;
import com.iss.itreasury.craftbrother.apply.dataentity.CraLoanContentInfo;
import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.query.bizlogic.QueryCapitalLandingFormBean;
import com.iss.itreasury.securities.query.dataentity.QueryCapitalLandingFormParam;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartBankAccountDAO;
import com.iss.itreasury.securities.setting.dao.SEC_CounterPartDAO;
import com.iss.itreasury.securities.setting.dataentity.CounterPartBankAccountInfo;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.*;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.*;
import com.iss.system.dao.PageLoader;
public class ApplyBiz 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	
	/**
	 *������ı������
	 * @throws Exception 
	*/
	public long save(ApplyInfo info) throws Exception
	{
		Connection conn = null;

		//DeliveryOrderServiceOperation dso = new DeliveryOrderServiceOperation();
		//UsableCreditLineOfSecuritiesInfo creditInfo1 = new UsableCreditLineOfSecuritiesInfo();
		//UsableCreditLineOfSecuritiesInfo creditInfo2 = new UsableCreditLineOfSecuritiesInfo();
		long lID = -1;
		long ret = -1;
		String strError = "";
		String applyCode = "";
        try
        {
			//�������������ݲ������ֶ��ύConnection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			SEC_ApplyDAO dao = new SEC_ApplyDAO(conn);
			CreditSettingInfo creditInfo =null;
			CreditSettingDAO cdao = new CreditSettingDAO(conn);
			
			if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
			{
				//�ʽ����ȼ��
				log4j.print("�ʽ����ȼ��");
				strError = dao.checkCapitalLandingCreditExtension(info.getCounterpartId(), info.getTransactionTypeId(), info.getPledgeSecuritiesAmount(), info.getId());
				if (strError != null && strError.length() > 0)
				{
					throw new SecuritiesException("Sec_E180", strError, null);
				}
			}
			else if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
					|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK)
	        {
				System.out.println("�ʲ�ת�ú�ͬ���루�ع������ż��");
	        	CreditCheckDAO checkDao = new CreditCheckDAO();
	        	
	        	//modify by xwhe
	        	CreditSettingDAO creditDao =new CreditSettingDAO();
				
				String counterpartName = NameRef.getCounterpartNameByID(info.getCounterpartId());
				
				creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),info.getCounterpartId(),CRANameRef.getPartenerInfo(info.getOfficeId()).getClientID(),1,info.getTransactionTypeId(),counterpartName,"");
	        	strError = checkDao.checkAttormCredit(info);
	        	System.out.println("�쳣��Ϣ"+strError);
	        	if (strError != null && strError.length() > 0)
				{
					throw new SecuritiesException("Sec_E180", strError, null);
				}
	        }
			// ��ͬ�������ع�������ͬ��������ϣ�
			else if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY 
					|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
			{
				System.out.println("�ʲ�ת�ú�ͬ�������ع�,��ϣ����ż��");
				CreditSettingDAO creditDao =new CreditSettingDAO();
				//CreditSettingInfo creditInfo =null;
				
				String counterpartName = NameRef.getCounterpartNameByID(info.getCounterpartId());
				
				creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),info.getCounterpartId(),CRANameRef.getPartenerInfo(info.getOfficeId()).getClientID(),2,info.getTransactionTypeId(),counterpartName,"");
				if(creditInfo != null)
				{
					if(info.getAmount() > 10000*creditInfo.getAmount())
					{
						strError = "���ʲ��ع�����Ļع����"+DataFormat.formatDisabledAmount(info.getAmount())+"Ԫ�������˽��׶���\""
							+ counterpartName + "\"�Բ���˾����"+SECConstant.BusinessType.CAPITAL_REPURCHASE.getName(info.getTransactionTypeId())
							+"�����������ŵ�"+DataFormat.formatDisabledAmount(10000*creditInfo.getAmount(),2)+"Ԫ�������Ŷ�ȣ����޸ĺ����ύ";
						throw new SecuritiesException("Sec_E180", strError, null);
					}
				}
			}
			if (info.getId() < 0)
			{
				try
				{
					
					HashMap map = new HashMap();
					map.put("officeID",String.valueOf(info.getOfficeId())); 
					map.put("currencyID",String.valueOf(info.getCurrencyId()));
					map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
					map.put("transTypeID",String.valueOf(info.getTypeId()));
					map.put("actionID",String.valueOf(info.getActionTypeId()));
					map.put("subActionID",String.valueOf(info.getTransactionTypeId()));
					applyCode = CreateCodeManager.createCode(map);
					info.setCode(applyCode);
					//����һ������������Ϊ1
					//info.setNextCheckLevel(1);
					info.setCreditId(creditInfo.getID());
					lID = dao.add(info);
					//����ԭ�����ͬ��Ϣ
					CraLoanContentDao craDao = new CraLoanContentDao(conn);
					if(info.getCraColl() != null)
					{
						Iterator it = info.getCraColl().iterator();
						while(it.hasNext())
						{
							CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
							craInfo.setApplyId(lID);
							craDao.setUseMaxID();
							craDao.add(craInfo);
						}
					}
					//����ԭ�����ͬ��Ϣ����
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
					if(info.getCraColl() != null)
					{
						//���º�ͬ��Ϣ(��ɾ��ԭ��Ϣ)
						Collection craColl = null;
						CraLoanContentDao craDao = new CraLoanContentDao(conn);
						craColl = findByApplyID(info.getId());
						Iterator it = craColl.iterator();
						while(it.hasNext()){
							CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
							craDao.deletePhysically(craInfo.getId());
						}
						//���º�ͬ��Ϣ����
						//����ԭ�����ͬ��Ϣ
						CraLoanContentDao craDaoS = new CraLoanContentDao(conn);
						Iterator itSave = info.getCraColl().iterator();
						while(itSave.hasNext())
						{
							CraLoanContentInfo craInfoSave = (CraLoanContentInfo)itSave.next();
							craInfoSave.setApplyId(info.getId());
							craDaoS.setUseMaxID();
							craDaoS.add(craInfoSave);
						}
						//����ԭ�����ͬ��Ϣ����
					}
				}
				catch (ITreasuryDAOException e)
				{
					throw new SecuritiesDAOException(e);
				}
				lID = info.getId();
			}
		
        }catch(Exception ex){
				ex.printStackTrace();
				throw ex;   
		}	
       finally
       {
			conn.commit();
			if(conn != null) {
				conn.close();
				conn = null;
			}	
       }
        
		return lID;
	}
	/**
	 * �����ͬ��Ϣ�ı������
	 */
	public void saveContract(CraLoanContentInfo cinfo) throws Exception
	{
		CraLoanContentDao dao = new CraLoanContentDao();
		try
		{
			dao.setUseMaxID();
			dao.add(cinfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * �����ͬ��Ϣɾ������
	 */
	public void deleteContract(long lID) throws Exception
	{
		CraLoanContentDao dao = new CraLoanContentDao();
		try
		{
			dao.updateStatus(lID, SECConstant.NoticeFormStatus.CANCELED);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
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
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, SecuritiesException, Exception
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
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException,Exception
	{
		Connection conn = null;
		try
		{
			//�������������ݲ������ֶ��ύConnection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			SEC_ApplyDAO dao = new SEC_ApplyDAO(conn);
			//ɾ�������ͬ��Ϣ
			Collection craColl = null;
			CraLoanContentDao craDao = new CraLoanContentDao(conn);
			craColl = findByApplyID(lID);
			Iterator it = craColl.iterator();
			while(it.hasNext()){
				CraLoanContentInfo craInfo = (CraLoanContentInfo)it.next();
				craDao.delete(craInfo.getId());
			}
			
			dao.delete(lID);
			conn.commit();
			if(conn != null) {
				conn.close();
				conn = null;
			}				
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
	/**
	 *����������ͬ��Ϣ��ѯ
	 */
	public Collection findByApplyID(long lID) throws SecuritiesException
	{	
		CraLoanContentInfo craInfo = new CraLoanContentInfo();
		CraLoanContentDao craDao = new CraLoanContentDao();
		Collection coll = null;
		
		try
		{
			coll = craDao.findByApplyID(lID, craInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			throw new SecuritiesDAOException(e);
		}
		return coll;
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
	 * ͨ��ID���ؽ��׶������� 
	 * @param counterPartID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartInfo findCounterPartById(long counterPartId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID start!");
		log4j.debug("--lhj dubug info ���׶���ID == " + counterPartId);
		CounterPartInfo counterPartInfo = new CounterPartInfo();
		SEC_CounterPartDAO counterPartDAO = new SEC_CounterPartDAO();
		try
		{
			log4j.debug("----lhj dubug info  1111111111");
			counterPartInfo = (CounterPartInfo) counterPartDAO.findByID(counterPartId, counterPartInfo.getClass());
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		log4j.debug("lhj debug info CounterPartBean :: findCounterPartByID end!");
		return counterPartInfo;

	}
	
	/**
	 * ͨ��ID���ؽ��׶��󿪻������� 
	 * @param counterPartBankAccountID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CounterPartBankAccountInfo findCounterPartBankAccountById(long counterPartBankAccountId) throws SecuritiesDAOException
	{
		log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById start!");
		CounterPartBankAccountInfo counterPartBankAccountInfo = new CounterPartBankAccountInfo();
		SEC_CounterPartBankAccountDAO counterPartBankAccountDAO = new SEC_CounterPartBankAccountDAO();

		try
		{
			counterPartBankAccountInfo = (CounterPartBankAccountInfo) counterPartBankAccountDAO.findByID(counterPartBankAccountId, counterPartBankAccountInfo.getClass());
			log4j.debug("lhj debug info CounterPartBean :: findCounterPartBankAccountById end!");
		}
		catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}

		return counterPartBankAccountInfo;

	}
	
	/**
	 * ���뵥��ѯ,����һ��PageLoader
	 * @author ����
	 * @param QueryCapitalLandingFormParam ҵ��֪ͨ����ѯҳ������
	 * @throws SecuritiesException
	 */
	public PageLoader queryCapitalLandingForm(QueryCapitalLandingFormParam queryParam)
		throws SecuritiesException {
		PageLoader queryPageLoader = null;
		QueryCapitalLandingFormBean queryBean = new QueryCapitalLandingFormBean();

		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm start");
		queryPageLoader =
		queryBean.queryCapitalLandingFormInfo(queryParam);
		log4j.debug("queryDelegation debug info ::::queryCapitalLandingForm end");
		return queryPageLoader;
	}
	  /**
     * added by xwhe 2007/06/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(ApplyInfo info)
			throws java.rmi.RemoteException, IRollbackException {
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
			dao.update(lReturnId, info.getInputUserId(),SECConstant.ApplyFormStatus.APPROVALING);
			
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
    public long examinePass(ApplyInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long lTransDiscountContractID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				SEC_ApplyDAO dao = new SEC_ApplyDAO();
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
   							SECConstant.ApplyFormStatus.CHECKED);
   					
   					//�������Ժ�,���ɺ�ͬ	
   					if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
   							|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK){
   					dao.doAfterCheckOver(lTransDiscountContractID);
   				}
   				}
   				//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.ApplyFormStatus.SUBMITED);
   				}	
   		 
   	 }
   	 catch(Exception e){
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
    public long updateApplyAndApprovalInit(ApplyInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			lReturnId = save(info);
			SEC_ApplyDAO dao = new SEC_ApplyDAO();
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(info.getId()));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
			// �ύ����
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// ����״̬��"������"
			dao.update(info.getId(), info.getInputUserId(),SECConstant.ApplyFormStatus.APPROVALING);
			
		} catch (Exception e) {
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
	public long cancelApproval(ApplyInfo info)throws java.rmi.RemoteException, SecuritiesException
	{
		long lReturn = -1;
		long ret = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		AttornmentApplyDao adao = new AttornmentApplyDao();
		
		boolean ifUsed=false;
		if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY
				|| info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK){
		try{
			ifUsed=dao.checkStatuID(info.getId());
		}catch (SecuritiesDAOException e) {
			throw new SecuritiesException();
		}
		if(ifUsed){
			throw new SecuritiesException("����ȡ����ͬ,��ȡ������!",null);
		}
		}
		if(info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY||
		    info.getTransactionTypeId()==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
		{
		try{
				ifUsed=dao.checkAttornStatuID(info.getId());
			}catch (SecuritiesDAOException e) {
				throw new SecuritiesException();
			}
			if(ifUsed){
				throw new SecuritiesException("����ȡ�������ʲ�ת������,��ȡ������!",null);
			}
		}
		
		try
		{
			
			dao.cancelContract(info.getId());//ȡ����ͬ�ı���״̬
		    dao.updateAttornByID(info.getId());//ȡ������ת������ı���״̬
			
			//ȡ������
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.ApplyFormStatus.SUBMITED);
			
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

}
