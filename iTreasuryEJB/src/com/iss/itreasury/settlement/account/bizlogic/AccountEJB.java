/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.loan.setting.bizlogic.LoanTypeRelationBiz;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountBankDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_ClientDAO;
import com.iss.itreasury.settlement.account.dao.Sett_ExternalAccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountBankInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TransactionFeeTypeDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dao.PayAmountForRecogDao;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.PayAmountForRecogInfo;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transloan.dao.Loan_DAO;
import com.iss.itreasury.settlement.transloan.dataentity.ContractFormInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.query.queryobj.QAccount;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccountEJB implements SessionBean
{
	private javax.ejb.SessionContext ctx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	final static int WITHDAWORCANCELDEPOSIT_WITHDRAW = 1;
	final static int WITHDAWORCANCELDEPOSIT_CANCELDEPOSIT = 2;
	//private long lSubAccountID = -1;
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{

	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return ctx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	// private method write following block
	/**
	 * ����˵�����ж������Ŀͻ�����Ƿ��Ѿ����ڡ�
	 */
	private boolean isDuplicateClientCode(long lOfficeID, String strClientCode) throws Exception
	{
		boolean bReturn = false;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		QueryClientConditionInfo qcci = new QueryClientConditionInfo();
		Collection coll = null;
		try
		{
			qcci.setOfficeID(lOfficeID);
			qcci.setStartClientCode(strClientCode);
			qcci.setEndClientCode(strClientCode);
			coll = objClientDAO.findByCondition(qcci);
			if (coll != null && coll.size() > 0)
			{
				bReturn = true;
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		return bReturn;
	}
	/**
	 * ����˵�����ж��������˻�����Ƿ��Ѿ����ڡ�
	 */
	private boolean isDuplicateAccountCode(long lCurrencyID, String strAccountCode) throws Exception
	{
		boolean bReturn = false;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		QueryAccountConditionInfo qaci = new QueryAccountConditionInfo();
		Collection coll = null;
		try
		{
			qaci.setCurrencyID(lCurrencyID);
			qaci.setStartAccountCode(strAccountCode);
			qaci.setEndAccountCode(strAccountCode);
			coll = objAccountDAO.findByConditions(qaci);
			if (coll != null && coll.size() > 0)
			{
				bReturn = true;
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		return bReturn;
	}
	// public methods write following block
	/**
	 * ����˵���� �����ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ���������Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		boolean bDublicate = false;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			bDublicate = isDuplicateClientCode(ci.getOfficeID(), ci.getClientCode());
			if (!bDublicate)
			{
				lReturn = objClientDAO.add(ci);
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵����ȡ�ÿͻ����
	 * 
	 * @param lOfficeID :
	 *            long @return: String - �����Ŀͻ����
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException
	{
		String strReturn = "";
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			strReturn = objClientDAO.getNewClientCode(lOfficeID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return strReturn;
	}
	/**
	 * ����˵�������ݿͻ�ID����ѯ�ͻ���Ϣ
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException
	{
		ClientInfo ci = new ClientInfo();
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			ci = objClientDAO.findByID(lClientID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return ci;
	}
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException
	{
		Vector v = new Vector();
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			v = (Vector) objClientDAO.findByCondition(qcci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * ����˵�����޸Ŀͻ���Ϣ
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - �ͻ�ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		try
		{
			lReturn = objClientDAO.update(ci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵���� ɾ���ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ����ɾ���Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_ClientDAO objClientDAO = new Sett_ClientDAO();
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		ClientInfo ci = new ClientInfo();
		Collection coll = null;
		QueryAccountConditionInfo qaci = new QueryAccountConditionInfo();
		try
		{
			ci = objClientDAO.findByID(lClientID);
			if (ci != null)
			{
				//modify by Forest,20040518,�����жϣ�����ÿͻ��ѱ�����ģ��ʹ�ã����ܱ�ɾ����
				//ԭ�Ѵ����жϣ�����ÿͻ��Ѿ����˻��ˣ�����ɾ����
				qaci.setStartClientCode(ci.getClientCode());
				qaci.setEndClientCode(ci.getClientCode());
				coll = objAccountDAO.findByConditions(qaci);
				if ((coll != null && coll.size() > 0) || objClientDAO.checkIsUsedByLoan(lClientID) == true)
				{
					lReturn = -1;
				}
				else
				{
					ci.setStatusID(SETTConstant.RecordStatus.INVALID);
					lReturn = objClientDAO.update(ci);
				}
			}
		}
		catch (IRollbackException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵�������������˻�
	 * 
	 * @param ai
	 *            @return: long - �������˻�ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException
	{
		long lAccountID = -1;
		boolean bDublicate = false;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		Sett_AccountBankDAO objAccountBankDAO = new Sett_AccountBankDAO();
		try
		{
			//bDublicate = isDuplicateAccountCode(ai.getCurrencyID(), ai.getAccountCode());
			if (!bDublicate)
			{
				lAccountID = objAccountDAO.add(ai);
				saci.setAccountID(lAccountID);
				//modify by xwhe 2008-10-15 ���û��ѡ��Ϣ�˻���Ĭ��Ϊ��ǰ�Ļ����˻�
				if(saci.getIsInterest()>0 && saci.getInterestAccountID()<0)
				{
					saci.setInterestAccountID(lAccountID);
				}
				objSubAccountDAO.addSubAccountCurrent(saci);
			}
			if (vAccountBank != null && vAccountBank.size() > 0)
			{
				for (int i = 0; i < vAccountBank.size(); i++)
				{
					AccountBankInfo abi = (AccountBankInfo) vAccountBank.elementAt(i);
					abi.setAccountID(lAccountID);
					try
					{
						objAccountBankDAO.add(abi);
					}
					catch (Exception e)
					{
						;
					}

				}
			}

			    //���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------�ύ����--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+lAccountID);
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//�ύ����
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					//����״̬��������
					objAccountDAO.updateCheckStatus(lAccountID,SETTConstant.Actions.SAVEANDINITAPPROVAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------�ύ�����ɹ�--------");
				}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lAccountID;
	}
	/**
	 * ����˵�����������ںʹ����˻�
	 * 
	 * @param ai
	 *            @return: long - �������˻�ID
	 * @throws RemoteException,IRollbackException
	 */
	public long addAccount(AccountInfo ai) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		boolean bDublicate = false;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			//bDublicate = isDuplicateAccountCode(ai.getCurrencyID(), ai.getAccountCode());
			if (!bDublicate)
			{
				lReturn = objAccountDAO.add(ai);
			}
			//���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by haoliang
		    if(ai.getInutParameterInfo()!=null)
			{
					log.debug("------�ύ����--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//�ύ����
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					//����״̬��������
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------�ύ�����ɹ�--------");
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵���������˻�ID����ѯ�˻���Ϣ
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public AccountInfo findAccountByID(long lAccountID) throws RemoteException, IRollbackException
	{
		AccountInfo ai = null;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			ai = objAccountDAO.findByID(lAccountID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return ai;
	}
	/**
	 * ����˵�����������˻�ID����ѯ���˻���Ϣ
	 * 
	 * @param lSubAccountID
	 *            ���˻�ID @return:SubAccountAssemblerInfo ai ���˻�Assemble
	 * @throws RemoteException,IRollbackException
	 */
	public SubAccountAssemblerInfo findSubAccountByID(long lSubAccountID) throws RemoteException, IRollbackException
	{
		SubAccountAssemblerInfo saai = null;
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		try
		{
			saai = objSubAccountDAO.findByID(lSubAccountID);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return saai;
	}
	/**
	 * ����˵�������ݿ�����ID����ѯ��������Ϣ
	 * 
	 * @param lAccountID
	 *            @return:AccountInfo ai
	 * @throws RemoteException,IRollbackException
	 */
	public BranchInfo findBranchByID(long lAccountID) throws RemoteException, IRollbackException
	{
		BranchInfo bi = null;
		Sett_BranchDAO objBranchDAO = new Sett_BranchDAO();
		try
		{
			bi = objBranchDAO.findByID(lAccountID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return bi;
	}
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findAccountByCondition(QueryAccountConditionInfo qaci) throws RemoteException, IRollbackException
	{
		Vector v = new Vector();
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			v = (Vector) objAccountDAO.findByConditions(qaci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return v;
	}
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param QueryAccountConditionInfo
	 *            qcai @return:Collection
	 * @throws RemoteException,IRollbackException
	 */
	public Collection findReserveAccountByCondition(QueryAccountConditionInfo qaci) throws RemoteException, IRollbackException
	{
		Vector v = new Vector();
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			v = (Vector) objAccountDAO.findReserveAccountByCondition(qaci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return v;
	}
	/**
	 * ����˵�����޸Ļ����˻�����Ϣ
	 * 
	 * @param ai
	 * @return :�����˻���ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateCurrentAccount(AccountInfo ai, SubAccountCurrentInfo saci, Vector vAccountBank)
		throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long lCheckStatus = -1;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		Sett_AccountBankDAO objAccountBankDAO = new Sett_AccountBankDAO();
		try
		{
			lReturn = objAccountDAO.update(ai);
			//objSubAccountDAO.updateSubAccountCurrent(saci);
			//modify by xwhe 2008-10-24 ���޸ļ�����Ϣ�ͼ�������
			objSubAccountDAO.updateSubAccountCurrentInfo(saci);
			if (vAccountBank != null && vAccountBank.size() > 0)
			{
				objAccountBankDAO.deleteByAccountID(ai.getAccountID());
				for (int i = 0; i < vAccountBank.size(); i++)
				{
					AccountBankInfo abi = (AccountBankInfo) vAccountBank.elementAt(i);
					abi.setAccountID(ai.getAccountID());
					try
					{
						objAccountBankDAO.add(abi);
					}
					catch (Exception e)
					{
						;
					}
				}
			}
			else //���û��ѡ�񿪻��� ��ɾ�� ��ǰ���� �˻�����Ŀ�����
				{
				objAccountBankDAO.deleteByAccountID(ai.getAccountID());
			}
			    //���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------�ύ����--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//�ύ����
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					if(ai.getCheckStatusID()==SETTConstant.AccountCheckStatus.NEWSAVE)
					{
						lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING;
					}else{
						lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALING;
					}
					//����״̬��������
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,lCheckStatus,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------�ύ�����ɹ�--------");
				}

		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵�����޸Ķ��ںʹ����˻�����Ϣ
	 * 
	 * @param ai
	 * @return :�����˻���ID
	 * @throws RemoteException,IRollbackException
	 */
	public long updateAccount(AccountInfo ai) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long lCheckStatus = -1;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			    lReturn = objAccountDAO.update(ai);
			
			    //���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------�ύ����--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//�ύ����
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					if(ai.getCheckStatusID()==SETTConstant.AccountCheckStatus.NEWSAVE)
					{
						lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING;
					}else{
						lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALING;
					}
					//����״̬��������
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,lCheckStatus,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------�ύ�����ɹ�--------");
				}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵�����õ��µ��˻���
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return �������˻����
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID)
		throws RemoteException, IRollbackException
	{
		String strReturn = "";
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
				strReturn = objAccountDAO.getNewAccountNo(lOfficeID, lCurrencyID, lAccountTypeID, lClientID);
			
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return strReturn;
	}

	/**
	 * ����˵�����õ��µ��˻��ţ��������˻���׼�����˻�������˻� ���ã�
	 * 
	 * @param lOfficeID ��ǰ��������
	 * @param lOfficeID �ͻ���������
	 * @param lAccountGroupTypeID �˻�������
	 * @param lAccountTypeID �˻�����
	 * @param lCurrencyID
	 * @return �������˻����
	 * @throws RemoteException,IRollbackException
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID, long lAccountTypeID, long lClientID,long lAccountGroupTypeID,long belongOfficeID)
		throws RemoteException, IRollbackException
	{
		String strReturn = "";
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
				strReturn = objAccountDAO.getNewAccountNo(lOfficeID, lCurrencyID, lAccountTypeID, lClientID,lAccountGroupTypeID,belongOfficeID);
			
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return strReturn;
	}
	/**
	 * ����˵�����ж��Ƿ�͸֧
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: ͸֧���� true; ��͸֧����false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isOverDraft(long lAccountID, long subAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.isOverDraft(lAccountID, subAccountID, dPayAmount, true);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		log.debug("--------��ʼ͸֧���-----------");
		//		log.debug("sfasdfa;sdjk;3223231111-��ʼ͸֧���");
		//		boolean bReturn = true;
		//		Sett_AccountDAO aDao = new Sett_AccountDAO();
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		AccountInfo ai = null;
		//		SubAccountAssemblerInfo assembler = null;
		//		//SubAccountCurrentInfo saci = null;
		//		try
		//		{
		//			ai = aDao.findByID(lAccountID);
		//			
		//			
		//			
		//			//			log.debug("----------�˻���ϢΪ��---------");
		//			//			log.debug(UtilOperation.dataentityToString(assembler.getSubAccountCurrenctInfo()));
		//			//			log.debug(UtilOperation.dataentityToString(assembler.getSubAccountFixedInfo()));
		//			if (ai != null){
		//				assembler = saDao.findByID(subAccountID);
		//				if(assembler == null)
		//					throw new IRollbackException(ctx, "�޷��ҵ����˻�"+subAccountID+"����Ӧ����Ϣ���˻����͸֧������,����ʧ��");
		//			}else{
		//				throw new IRollbackException(ctx, "�޷��ҵ��˻�"+lAccountID+"����Ӧ����Ϣ���˻����͸֧������,����ʧ��");
		//			}
		//
		//		}
		//		catch (Exception e1)
		//		{
		//			// TODO Auto-generated catch block
		//			e1.printStackTrace();
		//			throw new IRollbackException(ctx, e1.getMessage(), e1);
		//		}
		//		log.debug("---------�ж��˻�����------------");
		//		if (SETTConstant.AccountType.isCurrentAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("�жϻ����˻��Ƿ�͸֧,֧ȡ���:" + dPayAmount);
		//			
		//			
		//				SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
		//				if (saci != null)
		//				{
		//					//lSubAccountID = saci.getID();
		//					//log.debug("---------��û���֧ȡ���˻�ID" + lSubAccountID + "--------------");
		//					log.info("������߽������=" + ai.getMaxSinglePayAmount());
		//					// 1��֧ȡ��� > ������߽�����ƣ���ʾ����������ڵ�����߽�����ơ�
		//					if (dPayAmount > ai.getMaxSinglePayAmount() && ai.getMaxSinglePayAmount() > 0)
		//					{
		//						throw new IRollbackException(ctx, "Sett_E115", String.valueOf(ai.getMaxSinglePayAmount()));
		//					}
		//					//Sett_AccountDAO accDAO = new Sett_AccountDAO(); 
		//					//double sumMonthAmout = accDAO.getMonthSumAmount(ai, ai.getOfficeID(), ai.getCurrencyID());
		//					log.debug("�¶��ۼƷ���������=" + saci.getMonthLimitAmount());
		//					log.debug("�¶��ۼƷ�����=" + ai.getMonthSumAmount());
		//					if(saci.getMonthLimitAmount() != 0 && ai.getMonthSumAmount() < saci.getMonthLimitAmount()){
		//						throw new IRollbackException(ctx, "���ڴ���˻����¶��ۼƷ����������,����ʧ��");
		//					}
		//					log.info(
		//						"��ǰ��� - �ۼ�δ���˽�� - ֧ȡ���=" + (saci.getBalance() - saci.getDailyUncheckAmount() - dPayAmount));
		//					log.info("������=" + saci.getCapitalLimitAmount());
		//					// 2����ǰ��� - �ۼ�δ���˽�� - ֧ȡ��� < �������ʾ���˻������������
		//					log.debug("��ǰ��"+saci.getBalance());
		//					log.debug("�ۼ�δ���˽�"+saci.getDailyUncheckAmount());
		//					log.debug("֧ȡ��"+dPayAmount);					
		//					if (UtilOperation.Arith.round(saci.getBalance(), 2)
		//						- UtilOperation.Arith.round(saci.getDailyUncheckAmount(), 2)
		//						- UtilOperation.Arith.round(dPayAmount, 2)
		//						< saci.getCapitalLimitAmount())
		//					{
		//						throw new IRollbackException(ctx, "Sett_E116", String.valueOf(saci.getCapitalLimitAmount()));
		//					}
		//					// ͸֧���δ��.......
		//					bReturn = false;
		//				}
		//			
		//		}
		//		else if (SETTConstant.AccountType.isFixAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("�ж϶����˻��Ƿ�͸֧,֧ȡ���:" + dPayAmount);
		//			SubAccountFixedInfo safi = assembler.getSubAccountFixedInfo();
		//			if (safi != null)
		//			{
		//
		//				
		//				// 2����ǰ��� - �ۼ�δ���˽�� - ֧ȡ��� < �������ʾ���˻������������
		//				log.debug("��ǰ��"+safi.getBalance());
		//				log.debug("�ۼ�δ���˽�"+safi.getDailyUncheckAmount());
		//				log.debug("֧ȡ��"+dPayAmount);
		//				log.info(
		//						"��ǰ��� - �ۼ�δ���˽�� - ֧ȡ���=" + (safi.getBalance() - safi.getDailyUncheckAmount() - dPayAmount));				
		//				if (UtilOperation.Arith.round(safi.getBalance(), 2)
		//					- UtilOperation.Arith.round(safi.getDailyUncheckAmount(), 2)
		//					- UtilOperation.Arith.round(dPayAmount, 2)
		//					< 0.0)
		//				{
		//					throw new IRollbackException(ctx, "Sett_E116", String.valueOf(0));
		//				}				
		//
		//				log.debug("---------δ����������ͽ������-----------");
		//				bReturn = false;
		//			}
		//		}
		//		else if (SETTConstant.AccountType.isLoanAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("�жϴ����˻��Ƿ�͸֧,֧ȡ���:" + dPayAmount);
		//			SubAccountLoanInfo sali = assembler.getSubAccountLoanInfo();
		//			log.debug("11111111111111111111111111111111111111111");
		//			if (sali != null)
		//			{
		//				
		//				// 2����ǰ��� - �ۼ�δ���˽�� - ֧ȡ��� < �������ʾ���˻������������
		//				log.info(
		//						"��ǰ��� - �ۼ�δ���˽�� - ֧ȡ���=" + (sali.getBalance() - sali.getDailyUncheckAmount() - dPayAmount));								
		//				if (UtilOperation.Arith.round(sali.getBalance(), 2)
		//					- UtilOperation.Arith.round(sali.getDailyUncheckAmount(), 2)
		//					- UtilOperation.Arith.round(dPayAmount, 2)
		//					< 0.0)
		//				{
		//					throw new IRollbackException(ctx, "Sett_E116", String.valueOf(0));
		//				}				
		//				bReturn = false;
		//			}
		//		}
		//		if(!bReturn)
		//			log.debug("-----------" + "�˻�" + lAccountID + "û��͸֧---------------");
		//		else
		//			throw new IRollbackException(ctx, "�˻����͸֧������,����ʧ��");		
		//		return bReturn;
	}
	
	/**
	 * ����˵�����ж� �˻����֧���ۼ�δ���˽��׽����Ƿ������֧���˽��׽�ʵ�����-�ۼ�δ���˽��-���׽��<0,�������˻��Ƿ�����͸֧��
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: ���� ����true; �� ����false
	 * @throws RemoteException,IRollbackException
	 * @throws IRollbackException
	 */
	public boolean isLackBalanceToDraft(long lAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		long subAccountID=-1;		
		try
		{
			subAccountID = getCurrentSubAccoutIDByAccoutID(lAccountID);
			return accBean.isLackBalanceToDraft(lAccountID, subAccountID, dPayAmount, true);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}

	}
	public long validateAccountStatus(long lAccountID) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.validateAccountStatus(lAccountID, AccountBean.TRANSTYPE_IRRELATIVE);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		log.debug("----------validateAccountStatus��ʼ--------------");
		//		long lReturn = SETTConstant.AccountStatus.NORMAL;
		//		Sett_AccountDAO adao = new Sett_AccountDAO();
		//		SubAccountAssemblerInfo assembler = null;
		//		SubAccountCurrentInfo saci = null;
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		AccountInfo ai;
		//		try
		//		{
		//			ai = adao.findByID(lAccountID);
		//		}
		//		catch (Exception e)
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//			throw new IRollbackException(ctx, e.getMessage(), e);
		//		}
		//		log.debug("---------���˻���ϢΪ:------------"+ai);
		//		log.debug(UtilOperation.dataentityToString(ai));
		//		if (ai == null)
		//		{
		//			// ��ʾ�������˻��أز����ڡ�
		//			throw new IRollbackException(ctx, "Sett_E117", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.FREEZE)
		//		{
		//			// ��ʾ�������˻��أ���������
		//			throw new IRollbackException(ctx, "Sett_E118", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.SEALUP)
		//		{
		//			// ��ʾ�������˻��أ��ѷ�桱
		//			throw new IRollbackException(ctx, "Sett_E120", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.CLOSE)
		//		{
		//			// ��ʾ�������˻��أ����廧��
		//			throw new IRollbackException(ctx, "Sett_E119", ai.getAccountNo());
		//		}
		//		log.debug("----------validateAccountStatus����--------------");
		//		return lReturn;
	}
	/**
	 * ����˵�� �� �������ڴ�����˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openCurrentSubAccount(SubAccountCurrentInfo saci) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		try
		{
			lReturn = objSubAccountDAO.addSubAccountCurrent(saci);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵�� �� �����������˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openFixSubAccount(SubAccountFixedInfo safi) throws RemoteException, IRollbackException
	{
		long lSubAccountID = -1;
		log.debug("---------��ʼopenFixSubAccount-----------");
		AccountBean accBean = new AccountBean();

		if (validateAccountStatus(safi.getAccountID()) == SETTConstant.AccountStatus.NORMAL
			/*&& isOverDraft(safi.getAccountID(), 0.0) == false*/
			)
		{
			log.debug("---------�������˻�û��͸֧-----------");
			Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
			try
			{
				log.debug("---------Sett_SubAccountDAO���Ӷ����˻�-----------");
				lSubAccountID = objSubAccountDAO.addSubAccountFix(safi);

			}
			catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage());
			}
		}

		log.debug("---------����openFixSubAccount-----------");
		return lSubAccountID;
	}
	/**
	 * ����˵�� �� �������������˻�
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openLoanSubAccount(SubAccountLoanInfo sali) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		log.debug("--------��ʼ���������˻�---------");
		//�ж��Ƿ�һ������ȡ��ȡ������
		Loan_DAO loanDAO = new Loan_DAO();
		ContractFormInfo cfInfo = null;
		try
		{
			cfInfo = loanDAO.getContractInfoByLoanNoteID(sali.getLoanNoteID());
			if (cfInfo.getChargeRateType() == LOANConstant.ChargeRatePayType.ONETIME || cfInfo.getChargeRateType() == LOANConstant.ChargeRatePayType.YEAR)
			{
				log.debug("--------һ������ȡ�����ѻ�����ȡ���������˻�ʱ���������ѣ�---------");
				LoanPayFormDetailInfo lpfInfo = loanDAO.getLoanPayFormDetailInfoByID(sali.getLoanNoteID());
				
				//Modify by leiyang 2008/07/01 �޸�һ������ȡ�����ѵ��㷨
				//Sett_AccountDAO accDAO = new Sett_AccountDAO();
				//AccountInfo accInfo = accDAO.findByID(sali.getAccountID());
				//InterestCalculation ic = new InterestCalculation();
				//log.debug("accInfo.getCurrencyID():" + accInfo.getCurrencyID());
				//log.debug("lpfInfo.getCommissionRate()" + lpfInfo.getCommissionRate());
				//log.debug("sali.getOpenAmount()" + sali.getOpenAmount());
				//log.debug("lpfInfo.getStart()" + lpfInfo.getStart());
				//log.debug(" lpfInfo.getOutDate()" + lpfInfo.getOutDate());
				/*double commission =
					ic.calculateLoanAccountInterest(
						accInfo.getCurrencyID(),
						lpfInfo.getCommissionRate(),
						1,
						sali.getOpenAmount(),
						lpfInfo.getOutDate(),
						lpfInfo.getEnd());*/
				double commission = UtilOperation.Arith.mul(sali.getOpenAmount(), UtilOperation.Arith.div(lpfInfo.getCommissionRate(), 100));
				log.debug("--------�����ѽ��:" + commission + "---------");

				sali.setCommission(commission);
				//���Ŵ��������˻��в����������Ѽ�¼��Ϊ������Ϣ���㴦��ʱ���ܲ�ѯ�����Ŵ��������� modify zcwang 2007-6-21
				LoanTypeRelationBiz loanbiz = new LoanTypeRelationBiz();
				if(loanbiz.getLoanTypeBySubLoan(cfInfo.getLoanTypeID())==LOANConstant.LoanType.YT)
				{
					sali.setCommission(0);
				}
				//
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		try
		{
			lReturn = objSubAccountDAO.addSubAccountLoan(sali);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		log.debug("--------�������������˻������˻�ID��:" + lReturn + " ---------");
		return lReturn;
	}
	/**
	 * ����˵���������˻���
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long addAccountBalance(long lSubAccountID, double dPayAmount) throws RemoteException, IRollbackException
	{
		//		long lReturn = -1;
		//		try
		//		{
		//			Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
		//			//			When cancel fix withdraw transaction add account balance, if account status is close, then the status of account will be changed to normal
		//			long accountType = getAccountTypeBySubAccountID(lSubAccountID); 
		//			if (!SETTConstant.AccountType.isCurrentAccountType(accountType))
		//			{
		//				SubAccountFixedInfo fixSubAccountInfo = subAccountDAO.findByID(lSubAccountID).getSubAccountFixedInfo();
		//				if (fixSubAccountInfo.getStatusID() == SETTConstant.SubAccountStatus.FINISH){
		//					log.debug("�������˻�"+lSubAccountID+"״̬���廧������");
		//					subAccountDAO.updateFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.NORMAL, null);
		//				}
		//			}
		//			log.debug("�������˻�"+lSubAccountID+"��"+dPayAmount);
		//			lReturn = subAccountDAO.updateAccountBalance(lSubAccountID, dPayAmount);
		//		}
		//		catch (Exception e)
		//		{
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}
		//                return lReturn;
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.addAccountBalance(lSubAccountID, dPayAmount);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
	}
	/**
	 * ����˵���������˻���
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long subtractAccountBalance(long lSubAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.subtractAccountBalance(lSubAccountID, dPayAmount);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		log.debug("-------��ʼ�۳����˻�"+lSubAccountID+"�����"+dPayAmount);
		//		long lReturn = -1;
		//		Sett_AccountDAO aDao = new Sett_AccountDAO();
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		try
		//		{
		//			Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
		//			//			When fix withdraw transaction subtract account balance, if currentbalance = 0, the account will be colsed
		//			long accountType = getAccountTypeBySubAccountID(lSubAccountID);
		//			if (!SETTConstant.AccountType.isCurrentAccountType(accountType))
		//			{
		//				double balance = 0.0;
		//				if(SETTConstant.AccountType.isFixAccountType(accountType)){
		//					SubAccountFixedInfo fixSubAccountInfo = saDao.findByID(lSubAccountID).getSubAccountFixedInfo();
		//					balance = fixSubAccountInfo.getBalance();				
		//				}else{
		//					SubAccountLoanInfo loanSubAccountInfo = saDao.findByID(lSubAccountID).getSubAccountLoanInfo();
		//					balance = loanSubAccountInfo.getBalance();									
		//				}
		//				log.debug("-----******���"+balance);
		//				if (balance - dPayAmount <= 0){
		//					log.debug("------���ڻ�������˻����Ϊ0���˻��廧--------");
		//					saDao.updateFinishDateAndStatus(
		//						lSubAccountID,
		//						SETTConstant.SubAccountStatus.FINISH,
		//						Env.getSystemDate());
		//				}
		//			}
		//			subAccountDAO.updateAccountBalance(lSubAccountID, -dPayAmount);
		//		}
		//		catch (Exception e)
		//		{
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}
		//		log.debug("----�����۳��˻�������------");		
		//		return lReturn;
	}

	/**
	 * ����˵�������ӻ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addCurrentUncheckAmount(long lAccountID, long lOppAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{

		long subAccountID = getCurrentSubAccoutIDByAccoutID(lAccountID);

		addUncheckAmount(lAccountID, lOppAccountID, subAccountID, dPayAmount);
		return subAccountID;
	}

	/**
	 * ����˵�������Ӷ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		long subAccountID = getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(lAccountID, strFixedDepositNo);

		addUncheckAmount(lAccountID, -1, subAccountID, dPayAmount);
		return subAccountID;
	}
	
    /**
     * �������� ��֤�𱣺���  ���� ���˻��� �ۼ�δ���˸����� �ֶΡ�����ʱ���� ���ֶ� ��ֱֵ�Ӹ�Ϊ ��ǰ�˻���� ����ֶε�ֵ��
     * @param lAccountID
     * @param contractid
     * @throws RemoteException
     * @throws IRollbackException
     */
	public void addFixedUncheckAmount4Recog(long lAccountID,long contractid)
	throws RemoteException, IRollbackException
	{
		Sett_SubAccountDAO aDao = new Sett_SubAccountDAO();
		Collection c = null ;
		try {
			c = aDao.findByConditions4Recog(lAccountID,contractid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c == null || c.size() == 0)
			return;
		
		for(Iterator it=c.iterator();it.hasNext();){
			SubAccountCurrentInfo ai = (SubAccountCurrentInfo)it.next();
		    addUncheckAmount4Recog(lAccountID,ai.getID(), ai.getBalance());
		}
	}

	/**
	 * ����˵�������Ӵ������˻��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */

	public long addLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(lAccountID, loanNoteID);

		addUncheckAmount(lAccountID, -1, subAccountID, dPayAmount);
		return subAccountID;
	}
	


	/**
	 * ����˵���������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����dPaymountΪ����
	 * 
	 * @param lAccountID
	 * @param lOppAccountID �����-1�������ÿ��ǶԷ��˻�
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @isCancelOperation �����ȡ�����˲�����������δ���˽���򲻼��͸֧
	 * @throws IRollbackException
	 */
	private void addUncheckAmount(long lAccountID, long lOppAccountID, long subAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			accBean.addUncheckAmount(lAccountID, lOppAccountID, subAccountID, dPayAmount,-1);
		}
		catch (IException e)
		{
			//throw new IRollbackException(ctx, e.getMessage(), e);
			throw new IRollbackException(ctx,e);
			
		}
	} 
	
	private void addUncheckAmount4Recog(long lAccountID, long subAccountID, double dPayAmount)
	throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			accBean.addUncheckAmount4Recog(lAccountID, subAccountID, dPayAmount);
		}
		catch (IException e)
		{
			//throw new IRollbackException(ctx, e.getMessage(), e);
			throw new IRollbackException(ctx,e);
			
		}
	} 

	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractCurrentUncheckAmount(long lAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		log.debug("AccountEJB::subtractCurrentUncheckAmount3414123412342");
		long subAccountID = getCurrentSubAccoutIDByAccoutID(lAccountID);
		subtractUncheckAmount(subAccountID, dPayAmount);

		return subAccountID;
	}

	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractFixedUncheckAmount(long lAccountID, String strFixedDepositNo, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		long subAccountID = getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(lAccountID, strFixedDepositNo);
		subtractUncheckAmount(subAccountID, dPayAmount);

		return subAccountID;
	}
	/**
	 * �������� ��֤�� ������  ���� ���˻� �� �ۼ�δ���˽�����Ϊ0.0
	 * @param lAccountID
	 * @param contractid
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void subtractUncheckAmount4Recog(long lAccountID,long contractid)
		throws RemoteException, IRollbackException
	{
		log.debug("AccountEJB::subtractUncheckAmount4Recog");
		
		
		Sett_SubAccountDAO aDao = new Sett_SubAccountDAO();
		Collection c = null ;
		try {
			c = aDao.findByConditions4Recog(lAccountID,contractid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c == null || c.size() == 0)
			return;
		
		for(Iterator it=c.iterator();it.hasNext();){
			SubAccountCurrentInfo ai = (SubAccountCurrentInfo)it.next();
			subtractUncheckAmount(ai.getID(), ai.getBalance());
		}
	}
	/**
	 * ����˵�����۳������ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����
	 * dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long subtractLoanUncheckAmount(long lAccountID, long loanNoteID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(lAccountID, loanNoteID);
		subtractUncheckAmount(subAccountID, dPayAmount);

		return subAccountID;
	}

	/**
	 * ����˵�����۳��ۼ�δ���˸���� ����ʱ��δ�����ۼƸ��������ӣ�����dPaymountΪ����������ʱ��δ�����ۼƸ�������٣�����dPaymountΪ����
	 * 
	 * @param lSubAccountID
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	private void subtractUncheckAmount(long lSubAccountID, double dPayAmount)
		throws RemoteException, IRollbackException
	{
		log.debug("AccountEJB::subtractUncheckAmount");
		AccountBean accBean = new AccountBean();
		try
		{

			accBean.subtractUncheckAmount(lSubAccountID, dPayAmount);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}

		//		log.debug("-------��ʼ�۳����˻� " + lSubAccountID + " ���ۼ�δ���˽��---------");
		//		log.debug("-------����� " + dPayAmount + "---------");
		//		long lReturn = -1;
		//		Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
		//		try
		//		{
		//			subAccountDAO.updateUncheckPaymentAmount(lSubAccountID, -dPayAmount);
		//		}
		//		catch (SQLException e)
		//		{
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}
		//		log.debug("-------�����۳��ۼ�δ���˽��---------");
	}
	
	
	/**
	 * ����˵��������֧ȡ��
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.withdrawCurrent(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
	}

	/**
	 * ����֧ȡ
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------��ʼ����֧ȡwithdrawFix------------");
		long lSubAccountID = -1;
		try
		{
			AccountBean accBean = new AccountBean();
			lSubAccountID =
				getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
			
			tadi.setTransSubAccountID(lSubAccountID);
			if (lSubAccountID == -1)
			{
				throw new IRollbackException(
					ctx,
					"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
			}

			accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------��������֧ȡwithdrawFix------------");
		return lSubAccountID;
	}
	
	public Collection withdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException
	{
		log.debug("--------��ʼ֧ȡwithdrawFix4Recog------------");
		Collection c = null ;
		try
		{
			AccountBean accBean = new AccountBean();
			c = getFixedSubAccoutIDAndAmount(tadi.getTransAccountID(),nContractID);
			
			
			PayAmountForRecogDao pDao = new PayAmountForRecogDao();
			PayAmountForRecogInfo pInfo = new PayAmountForRecogInfo();
			
			if(c == null || c.size() == 0)
			{
				throw new IRollbackException(
						ctx,
						"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �����˻�������ʧ��");
			}
			for(Iterator it=c.iterator();it.hasNext();)
			{
				SubAccountCurrentInfo ai = (SubAccountCurrentInfo)it.next();
				tadi.setTransSubAccountID(ai.getID());
				tadi.setAmount(ai.getBalance());
				if (ai.getID() < 0)
				{
					throw new IRollbackException(
						ctx,
						"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �����˻�������ʧ��");
				}
				accBean.withdraw4Recog(tadi);
			    
				pInfo.setNAccountID(tadi.getTransAccountID());
			    pInfo.setNSubAccountID(ai.getID());
			    pInfo.setNContractID(nContractID);
			    pInfo.setPayAmount(ai.getBalance());
			    pInfo.setNStatusID(1);
			    pDao.setUseMaxID();
			    pDao.add(pInfo);
			}
			    
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------����֧ȡwithdrawFix4Recog------------");
		return c;
	}
	
	/**
	 * �������޻���֧ȡ
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------��ʼ�������޻���withdrawFinance------------");
		long lSubAccountID = -1;
		try
		{
			AccountBean accBean = new AccountBean();
			lSubAccountID =
				getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
			tadi.setTransSubAccountID(lSubAccountID);
			if (lSubAccountID == -1)
			{
				throw new IRollbackException(
					ctx,
					"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �տ�֪ͨ������: " + tadi.getFixedDepositNo() + " �������������˻�������ʧ��");
			}

			accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------�����������޻���withdrawFinance------------");
		return lSubAccountID;
	}

	/**
	 * ����˵����ȡ������֧ȡ��
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelWithdrawCurrent(TransAccountDetailInfo tadi) throws IRollbackException
	{
		//		//		long subAccountID = this.getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
		//		//		
		//		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		//		{
		//		//			// �����ۼ�δ���˽��
		//		//			addUncheckAmount(tadi.getTransAccountID(),tadi.getTransSubAccountID(), tadi.getAmount());
		//		//			// �����˻����
		//		//			addAccountBalance(subAccountID, tadi.getAmount());
		//		//		}
		//
		//		long subAccountID = getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
		//		tadi.setTransSubAccountID(subAccountID);
		//
		//		cancelWithdraw(tadi);
		AccountBean accBean = new AccountBean();
		try
		{
			accBean.cancelWithdrawCurrent(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}

	/**
	 * ȡ���������޻������
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------��ʼȡ���������޻������cancelWithdrawFinance------------");
		//long lSubAccountID =
		//	this.getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		//modify by zwxiao 2010-08-08 ����ȡ�����˻���ʱ��������˻���״̬Ϊ�ѽ����ʱ�������Ҳ������˻��������������д�÷���
		Sett_SubAccountDAO accountDAO = new Sett_SubAccountDAO();
		long lSubAccountID = -1;
		long lStatusID = -1;
		SubAccountAssemblerInfo assembler = null;
		try {
			assembler = accountDAO.findByLoanNoteIDForCNMEF(tadi.getTransAccountID(), tadi.getLoanNoteID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new IRollbackException(
					ctx,
					"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �տ�֪ͨ������: " + tadi.getFixedDepositNo() + " �������������˻�������ʧ��");
		}
		if (assembler != null && assembler.getSubAccountFixedInfo() != null && assembler.getSubAccountFixedInfo().getID() != -1)
        {
			lSubAccountID = assembler.getSubAccountFixedInfo().getID();
			lStatusID = assembler.getSubAccountFixedInfo().getStatusID();
        }
		if(lStatusID == SETTConstant.SubAccountStatus.FINISH){
			try {
				accountDAO.updateNoFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.NORMAL);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new IRollbackException(
						ctx,
						"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �տ�֪ͨ������: " + tadi.getFixedDepositNo() + " �������������˻�������ʧ��");
			}
		}
		tadi.setTransSubAccountID(lSubAccountID);
		if (lSubAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �տ�֪ͨ������: " + tadi.getFixedDepositNo() + " �������������˻�������ʧ��");
		}		
		cancelWithdraw(tadi);
		log.debug("--------����ȡ���������޻������cancelWithdrawFinance------------");
	}
	
	/**
	 * ȡ�����ڴ���
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		if (tadi.getTransSubAccountID() == -1)
		{
			throw new IRollbackException(ctx, "�޷���ö������˻���ȡ������֧ȡ����ʧ��");
		}
		//		long lSubAccountID =
		//			this.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
		//		tadi.setTransSubAccountID(lSubAccountID);
		//		if (lSubAccountID == -1)
		//		{
		//			throw new IRollbackException(
		//				ctx,
		//				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		//		}		
		cancelWithdraw(tadi);
	}
	
	public void cancelWithdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException
	{
		
		log.debug("--------��ʼ֧ȡwithdrawFix4Recog------------");
		Collection c = null ;
		try
		{
			AccountBean accBean = new AccountBean();
			c = getFixedSubAccoutIDAndAmount4CancelCheck(tadi.getTransAccountID(),nContractID);
			
			PayAmountForRecogDao pDao = new PayAmountForRecogDao();
			PayAmountForRecogInfo pInfo = new PayAmountForRecogInfo();
			
			if(c == null || c.size() == 0)
			{
				throw new IRollbackException(
						ctx,
						"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �����˻�������ʧ��");
			}
			for(Iterator it=c.iterator();it.hasNext();)
			{
				try
				{
					SubAccountCurrentInfo ai = (SubAccountCurrentInfo)it.next();
					tadi.setTransSubAccountID(ai.getID());
					
					pInfo = pDao.findBySubAccountID(ai.getID());
					
					tadi.setAmount(pInfo.getPayAmount());
					if (ai.getID() < 0)
					{
						throw new IRollbackException(
							ctx,
							"�޷�������˻�ID��:" + tadi.getTransAccountID() + " �����˻�������ʧ��");
					}
					
						accBean.cancelWithdraw(tadi);
						pInfo.setNStatusID(0);
						pDao.update(pInfo);
					}
				catch (Exception e)
				{
					throw new IRollbackException(ctx, e.getMessage(), e);
				}
			 }
			    
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------����֧ȡwithdrawFix4Recog------------");
	}

	/**private method for cancel withdraw*/
	private void cancelWithdraw(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//
		//		//ע�⣺�������˻����������δ���˽��
		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		{
		//			// �����˻����
		//			long lSubAccountID = tadi.getTransSubAccountID();
		//			if (lSubAccountID == -1)
		//			{
		//				throw new IRollbackException(ctx, "���׵����˻������ڣ�����ʧ��");
		//			}
		//			addAccountBalance(lSubAccountID, tadi.getAmount());
		//			// �����ۼ�δ���˽��
		//			addUncheckAmount(tadi.getTransAccountID(), tadi.getTransSubAccountID(), tadi.getAmount());
		//
		//		}
		AccountBean accBean = new AccountBean();
		try
		{
			accBean.cancelWithdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}

	}

	/**withdraw from account for fixed and current transaction*/
	private long withdraw(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		long lSubAccountID = -1;
		//
		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//			&& isOverDraft(tadi.getTransAccountID(),tadi.getTransSubAccountID(),  0.0) == false)
		//		{
		//			log.debug("--------�˻�״̬�������˻���͸֧------------");
		//			log.debug("--------��������ID��: " + tadi.getTransactionTypeID() + "----------");
		//			if (SETTConstant.TransactionType.isCurrentTransaction(tadi.getTransactionTypeID())
		//				|| SETTConstant.TransactionType.isFixedTransaction(tadi.getTransactionTypeID()))
		//			{
		//				// �۳��ۼ�δ���˽��
		//				log.debug("--------�۳����˻���"+tadi.getTransSubAccountID()+"�����ۼ�δ���˽��------------");
		//				subtractUncheckAmount(tadi.getTransSubAccountID(), tadi.getAmount());
		//			}
		//			// �����˻����
		//			log.debug("--------�����˻����: " + tadi.getAmount() + "----------");
		//			lSubAccountID = tadi.getTransSubAccountID();
		//			if (lSubAccountID == -1)
		//			{
		//				throw new IRollbackException(ctx, "û�л�ÿ��õ����˻�������ʧ��");
		//				
		//			}
		//			subtractAccountBalance(lSubAccountID, tadi.getAmount());
		//			// ���ɽ�����ϸ��
		//			sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO();
		//			log.debug("--------���ɽ�����ϸ��------------");
		//			//tadi.setTransSubAccountID(lSubAccountID);
		//			try
		//			{
		//				log.debug(UtilOperation.dataentityToString(tadi));
		//				tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);
		//				tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);						
		//				taddao.add(tadi);
		//			}
		//			catch (Exception e1)
		//			{
		//				throw new IRollbackException(ctx, e1.getMessage());
		//
		//			}
		//		}
		//		return lSubAccountID;
	}

	/**
	 * ����˵����ɾ���������˻���ע�⣬�÷���Ŀǰֻ��Ϊһ���������˻��ķ�����ʵ�ʵ�ɾ�����ڶ��ڻ��������˻���0��ʱ���Զ�ɾ�����˻�
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteFixSubAccount(long subAccountID) throws RemoteException, IRollbackException
	{
		//		long lSubAccount = -1;
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		try
		{
			//			lSubAccount = this.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(lAccountID, strFixedDepositNo);
			log.debug("------ɾ���������˻�" + subAccountID + "--------");
			dao.updateStatus(subAccountID, Constant.RecordStatus.INVALID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage());
		}
		//return lSubAccount;
	}

	/**
	 * ����˵����ɾ���������˻���(��������ɾ���˻���ɾ�������ڿ۳��˻����ʱ����)
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void deleteLoanSubAccount(long subAccountID) throws RemoteException, IRollbackException
	{
		//long lSubAccount = -1;
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		try
		{
			//	lSubAccount = this.getLoanSubAccountIDByAccountIDAndLoanNoteID(lAccountID, loanNoteID);
			log.debug("------ɾ���������˻�" + subAccountID + "--------");
			dao.updateStatus(subAccountID, Constant.RecordStatus.INVALID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		//return lSubAccount;
	}

	/**
	 * ����˵�������ڴ��롣
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.depositCurrent(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
	}
	/**
	 * ����˵�������ڴ��롣
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		{
		//			lSubAccountID = tadi.getTransSubAccountID();
		//			
		//			// �����˻����
		//			addAccountBalance(lSubAccountID, tadi.getAmount());
		//			// ���ɽ�����ϸ��
		//			sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO();
		//			try
		//			{
		//				taddao.add(tadi);
		//			}
		//			catch (Exception e)
		//			{
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//				throw new IRollbackException(ctx, e.getMessage());
		//			}
		//		}
		//		return lSubAccountID;
		log.debug("----------��ʼ���ڴ���--------------");
		AccountBean accBean = new AccountBean();
		try
		{
			if (tadi.getTransSubAccountID() < 0)
			{
				long lSubAccountID =
					getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
				tadi.setTransSubAccountID(lSubAccountID);
			}

			log.debug("�˻�ID��:" + tadi.getTransSubAccountID());
			if (tadi.getTransSubAccountID() == -1)
			{
				throw new IRollbackException(
					ctx,
					"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
			}

			accBean.depoist(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("----------�������ڴ���--------------");
		return tadi.getTransSubAccountID();
	}

	/**���ڻ��ڴ���*/
	private void depoist(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{

		AccountBean accBean = new AccountBean();
		try
		{
			accBean.depoist(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		try {
		//			long lSubAccountID = -1;
		//			log.debug("--------��ʼ����----------");
		//			if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& validateMinSinglePayAmount(tadi.getTransAccountID(), tadi.getAmount()))
		//			{
		//				log.debug("--------�˻�״̬����------------");
		//				lSubAccountID = tadi.getTransSubAccountID();
		//				if (lSubAccountID == -1)
		//				{
		//					throw new IRollbackException(ctx, "���׵����˻������ڣ�����ʧ��");
		//				}
		//				// �����˻����
		//				log.debug("--------�����˻����: " + tadi.getAmount() + "----------");
		//				addAccountBalance(lSubAccountID, tadi.getAmount());
		//				// ���ɽ�����ϸ��
		//				tadi.setTransDirection(SETTConstant.ControlDirection.CREDIT);
		//				tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
		//				log.debug("--------���ɽ�����ϸ��------------");
		//				sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO();
		//				taddao.add(tadi);
		//				log.debug("--------��������----------");
		//			}
		//		} catch (SQLException e) {
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}

	}

	/**����ʱ��֤�������Ƿ񳬹���С�����*/
	private boolean validateMinSinglePayAmount(long accountID, double depositAmount) throws SQLException
	{
		Sett_AccountDAO sDAO = new Sett_AccountDAO();
		try
		{
			AccountInfo saInfo = sDAO.findByID(accountID);
			if (saInfo.getMinSinglePayAmount() == 0 || depositAmount >= saInfo.getMinSinglePayAmount())
				return true;
		}
		catch (Exception e)
		{
			throw ((SQLException) e);
		}
		return false;
	}

	/**
	 * ɾ���˻�������ϸ��Ľ�����ϸ
	 * 
	 * @param strTransNo
	 *            ���ױ��
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.deleteTransAccountDetail(strTransNo);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.toString(), e);
		}
	}
	/**
	 * ����˵����ȡ�����ڴ��롣
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{

		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& isOverDraft(tadi.getTransAccountID(), tadi.getAmount()) == false)
		//		{
		//			// �����˻����
		//			subtractAccountBalance(tadi.getTransAccountID(), tadi.getAmount());
		//		}
		log.debug("AccoutEJB::cancelDepositCurrent start");
		long lSubAccountID = getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
		log.debug("AccoutEJB::getCurrentSubAccoutIDByAccoutID end---------------");
		tadi.setTransSubAccountID(lSubAccountID);
		if (lSubAccountID == -1)
		{
			throw new IRollbackException(ctx, "�޷�������˻�ID��:" + tadi.getTransAccountID() + " �Ķ������˻�������ʧ��");
		}
		log.debug("AccoutEJB::cancelDeposit start");
		cancelDeposit(tadi);
		log.debug("AccoutEJB::cancelDeposit end");
	}

	/**
	 * ����˵����ȡ�����ڴ��롣
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long cancelDepositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug(UtilOperation.dataentityToString(tadi));
		//		// ����˻�״̬�Ƿ��������˻�����Ƿ�͸֧
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& isOverDraft(tadi.getTransAccountID(), tadi.getAmount()) == false)
		//		{
		//			// �����˻����
		//			subtractAccountBalance(tadi.getTransAccountID(), tadi.getAmount());
		//		}

		long lSubAccountID = -1;
		if (tadi.getTransSubAccountID() < 0)
		{

			lSubAccountID =
				getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());

			tadi.setTransSubAccountID(lSubAccountID);
		}
		if (tadi.getTransSubAccountID() == -1)
		{
			throw new IRollbackException(
				ctx,
				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		}

		cancelDeposit(tadi);
		return lSubAccountID;
	}

	public double getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType)
		throws RemoteException, IRollbackException
	{
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		double interest;
		try
		{
			interest = dao.getPredrawInterestBySubAccountIDAndAccountType(subAccountID, accountType);
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return interest;
	}

	public void cancelDeposit(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			accBean.cancelDeposit(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	/**
	 * ����˵���������
	 * 
	 * @param tadi
	 * @return ���˻�ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//		log.debug("-------��ʼ����ſ�---------");
		//		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		//		if (subAccountID == -1)
		//		{
		//			throw new IRollbackException(
		//				ctx,
		//				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		//		}
		//���˻�ID��AccountBook���Ѿ����
		//tadi.setTransSubAccountID(tadi.getTransSubAccountID());

		depoist(tadi);

		//		Sett_TransGrantLoanDAO grantLoanDAO = new Sett_TransGrantLoanDAO();
		//		try {
		//			grantLoanDAO.updateLoanPayFormStatus(tadi.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.USED);
		//		} catch (SQLException e) {
		//			throw new IRollbackException(ctx,"�������ݿ����",e);
		//		} 
		log.debug("-------��������ſ�---------");
		return tadi.getTransSubAccountID();
	}

	/**
	 * ����˵����ȡ�������
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelGrantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------��ʼȡ������ſ�---------");
		long subAccountID = tadi.getTransSubAccountID();
		log.print("------���˻�id:"+subAccountID);
		if(subAccountID <0)
		{
		    log.debug("-------��ʼȡ������ſ�---------");
		    subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		}
		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		}
		tadi.setTransSubAccountID(subAccountID);
		cancelDeposit(tadi);
		log.debug("-------����ȡ������ſ�---------");
		return subAccountID;
	}

	/**
	 * ����黹
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------��ʼ����黹---------");

		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		}
		tadi.setTransSubAccountID(subAccountID);

		AccountBean accBean = new AccountBean();
		try
		{
			accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("-------��������黹---------");
		return subAccountID;
	}
	/**
	 * ȡ������黹
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 * 
	 */
	public long cancelRepayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------��ʼȡ������黹---------");
		long subAccountID = tadi.getTransSubAccountID();
		if (subAccountID < 0)
		{
			subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		
		}

		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
		}
		//added by qhzhou 2008-03-14
		tadi.setTransSubAccountID(subAccountID);
		
		cancelWithdraw(tadi);
		log.debug("-------����ȡ������黹---------");
		return subAccountID;
	}

	/**
	 * ����˵���������ⲿ�˻�
	 * 
	 * @param ExternalAccountInfo
	 * @return �����¼ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long saveExternalAccount(ExternalAccountInfo info) throws RemoteException, IRollbackException
	{
		long res = -1;
		if(info==null || info.getExtAcctNo()==null || info.getExtAcctNo().length()<=0 || info.getOfficeID()<=0)
		    return res;
		if ("".equals(info.getExtAcctNo()) || "".equals(info.getExtAcctName()) || info.getOfficeID() == -1)
			return res;
		try
		{
			Sett_ExternalAccountDAO dao = new Sett_ExternalAccountDAO();
			if (!dao.isRepetitiveRecord(info))
				res = dao.add(info);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage());
		}
		return res;
	}

	/**
	 * @param lSubAccountID
	 * @return @throws
	 *         IRollbackException
	 */
	public String getSubjectBySubAccountID(long lSubAccountID, int subjectType)
		throws RemoteException, IRollbackException
	{
		//		log.debug("--------��ʼgetSubjectBySubAccountID,���˻�ID��:" + lSubAccountID + "-----------");
		//		String strSubject = null;
		//		//�������˻�ID�����˻��ӱ�sett_SubAccount�в������˻�
		//		long accountTypeID = -1;
		//		try
		//		{
		//			Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
		//			SubAccountAssemblerInfo subAccountAssemblerInfo = subAccountDAO.findByID(lSubAccountID);
		//			long accoontID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getAccountID();
		//			log.debug("-------------��Ӧ�����˻�ID��: " + accoontID + "---------------");
		//
		//			long depositTerm = subAccountAssemblerInfo.getSubAccountFixedInfo().getDepositTerm();
		//			long noticeDay = subAccountAssemblerInfo.getSubAccountFixedInfo().getNoticeDay();
		//			long loanNoteID = subAccountAssemblerInfo.getSubAccountLoanInfo().getLoanNoteID();
		//
		//			accountTypeID = -1;
		//			Sett_AccountDAO accountDAO = new Sett_AccountDAO();
		//			AccountInfo accountInfo = accountDAO.findByID(accoontID);
		//			strSubject = accountInfo.getSubject();
		//			accountTypeID = accountInfo.getAccountTypeID();
		//			log.debug("-------------AccountTypeID: " + accountTypeID + "---------------");
		//			if ((subjectType == AccountOperation.SUBJECT_TYPE_ACCOUNT && strSubject == null)
		//				|| (subjectType == AccountOperation.SUBJECT_TYPE_INTEREST || subjectType == AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST))
		//			{ //�˻���û�б����Ŀ��Ϣ�����˻����ͱ������ø��ӱ���ҿ�Ŀ
		//				log.debug("---------�˻���û�б����Ŀ��Ϣ ������ȡ��Ϣ/������Ϣ��Ŀ����ʼ���˻����ͱ������ø��ӱ���ҿ�Ŀ------------");
		//
		//				long accountGroupType = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(accountTypeID);
		//				log.debug("-------------AccountGroupType: " + accountGroupType + "---------------");
		//				Sett_AccountTypeDAO accountTypeDAO = new Sett_AccountTypeDAO();
		//				AccountTypeInfo accountTypeInfo = accountTypeDAO.findByID(accountTypeID);
		//				log.debug("-------------AccountTypeInfo:---------------");
		//				log.debug(UtilOperation.dataentityToString(accountTypeInfo));
		//				long isExistSubClass = accountTypeInfo.getIsExistSubClass();
		//				log.debug("subjectType: " + subjectType);
		//				log.debug("isExistSubClass: " + isExistSubClass);
		//				if (subjectType == AccountOperation.SUBJECT_TYPE_ACCOUNT && isExistSubClass != 1)
		//				{ //���¼�����
		//					log.debug("---------ȡ�˻���Ŀ�ţ����¼�����-------------");
		//					Sett_CurrencySubjectDAO currencySubjectDAO = new Sett_CurrencySubjectDAO();
		//					strSubject =
		//						currencySubjectDAO.findSubjectCodeByTableNameAndAccoutTypeIDAndCurrencyIDAndOfficeID("Sett_accounttype", accountTypeID, accountInfo.getCurrencyID(), accountInfo.getOfficeID());
		//					log.debug("-------------1.��Ŀ����: " + strSubject + "---------------");
		//
		//				}
		//				else
		//					if (isExistSubClass == 1)
		//					{
		//						if (accountGroupType == SETTConstant.AccountGroupType.FIXED)
		//						{
		//							log.debug("---------�����¼�����-------------");
		//							Sett_SubAccounTtype_FixedDAO subAccounTtype_FixedDAO = new Sett_SubAccounTtype_FixedDAO();
		//							if (depositTerm == -1 && noticeDay == -1)
		//								throw new IRollbackException(ctx, "�˻���Ϣ(���ڴ�����޻�֪ͨ�������)����ȷ������ʧ��");
		//
		//							//if (depositTerm == -1 && noticeDay != -1) //��֪ͨ���
		//							//	
		//							if (noticeDay > 0)
		//								depositTerm = noticeDay;
		//
		//							switch (subjectType)
		//							{
		//								case AccountOperation.SUBJECT_TYPE_ACCOUNT :
		//									{
		//										log.debug("---------ȡ�˻���Ӧ�Ŀ�Ŀ-------------");
		//										strSubject = subAccounTtype_FixedDAO.findAccountSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//									break;
		//								case AccountOperation.SUBJECT_TYPE_INTEREST :
		//									{
		//										log.debug("---------ȡ��Ϣ��Ӧ�Ŀ�Ŀ-------------");
		//										strSubject = subAccounTtype_FixedDAO.findInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//									break;
		//								case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST :
		//									{
		//										log.debug("---------ȡ������Ϣ��Ӧ�Ŀ�Ŀ-------------");
		//										strSubject = subAccounTtype_FixedDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//
		//							}
		//
		//							log.debug("-------------2.��Ŀ����: " + strSubject + "---------------");
		//						}
		//						else
		//							if (accountGroupType == SETTConstant.AccountGroupType.LOAN)
		//							{
		//								log.debug("---------�����¼�����-------------");
		//								Sett_SubAccountType_LoanDAO subAccounTtype_LoanDAO = new Sett_SubAccountType_LoanDAO();
		//
		//								Loan_DAO loanDAO = new Loan_DAO();
		//
		//								long year = -1;
		//								long loanTypeID = -1;
		//								long intervalNum = -1;
		//								long consignClientID = -1;
		//								long draftType = -1;
		//								if (accountTypeID == SETTConstant.AccountType.TRUSTLOAN
		//									|| accountTypeID == SETTConstant.AccountType.CONSIGNLOAN
		//									|| accountTypeID == SETTConstant.AccountType.CYCLOAN
		//									|| accountTypeID == SETTConstant.AccountType.SHORTLOAN
		//									|| accountTypeID == SETTConstant.AccountType.FOREIGN_TRUSTLOAN
		//									|| accountTypeID == SETTConstant.AccountType.FOREIGN_CONSIGNLOAN)
		//								{
		//									ContractFormInfo contractFormInfo = loanDAO.getContractInfoByLoanNoteID(loanNoteID);
		//									GregorianCalendar sCalendar = new GregorianCalendar();
		//									sCalendar.setTime(contractFormInfo.getLoanStart());
		//									year = sCalendar.get(Calendar.YEAR);
		//									loanTypeID = contractFormInfo.getLoanTypeID();
		//									intervalNum = contractFormInfo.getIntervalNum();
		//									consignClientID = contractFormInfo.getClientID();
		//									//accountTypeInfo.setIsDraftType(-1);
		//								}
		//								else
		//									if (accountTypeID == SETTConstant.AccountType.DISCOUNT)
		//									{
		//										log.debug("---------�˻������������˻�-------------");
		//										Loan_DAO lDAO = new Loan_DAO();
		//										draftType = lDAO.getAcceptPoTypeIDByDiscountCredenceID(loanNoteID);
		//										//�����˻���ƥ����������
		//										accountTypeInfo.setIsLoanType(-1);
		//										accountTypeInfo.setIsLoanMonth(-1);
		//										accountTypeInfo.setIsLoanYear(-1);
		//										accountTypeInfo.setIsCosign(-1);
		//
		//									}
		//									else
		//										throw new IRollbackException(ctx, "�޷��ҵ��ſ�֪ͨ����Ϊ" + loanNoteID + "�ķſ�֪ͨ������Ӧ�ĺ�ͬ��Ϣ,����ʧ��");
		//
		//								//loanDAO. 
		//								switch (subjectType)
		//								{
		//									case AccountOperation.SUBJECT_TYPE_ACCOUNT :
		//										{
		//											log.debug("---------ȡ�˻���Ӧ�Ŀ�Ŀ-------------");
		//											strSubject = subAccounTtype_LoanDAO.findAccountSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//										}
		//										break;
		//									case AccountOperation.SUBJECT_TYPE_INTEREST :
		//										{
		//											log.debug("---------ȡ��Ϣ��Ӧ�Ŀ�Ŀ-------------");
		//											strSubject = subAccounTtype_LoanDAO.findInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//										}
		//										break;
		//									case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST :
		//										{
		//											log.debug("---------ȡ������Ϣ��Ӧ�Ŀ�Ŀ-------------");
		//											strSubject = subAccounTtype_LoanDAO.findPredrawInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//
		//										}
		//
		//								}
		//
		//								log.debug("-------------2.��Ŀ����: " + strSubject + "---------------");
		//							}
		//
		//					}
		//
		//			}
		//		}
		//		catch (SQLException e)
		//		{
		//			throw new IRollbackException(ctx, e.getMessage());
		//
		//		}
		//		catch (Exception e)
		//		{
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}
		//
		//		log.debug("-----------��ʼУ���Ŀ�źϷ���----------------");
		//		if (strSubject == null || strSubject.compareToIgnoreCase("") == 0)
		//		{
		//			throw new IRollbackException(ctx, "�˻�����" + accountTypeID + "������Ŀ��Ϊ��");
		//		}
		//
		//		GeneralLedgerOperation glOperation = new GeneralLedgerOperation();
		//		//GLSubjectDefinitionInfo glSubjectDefInfo1 = glOperation.findBySubjectOldCode(strSubject);
		//		//GLSubjectDefinitionInfo glSubjectDefInfo2 = glOperation.findBySubjectCode(strSubject);
		//		if (!glOperation.isExistSubeject(strSubject))
		//			throw new IRollbackException(ctx, "�˻�����" + accountTypeID + "������Ŀ:" + strSubject + " �����ڻ���ɾ��"); //else
		//
		//		log.debug("--------����getSubjectBySubAccountID,��Ŀ����: " + strSubject + "-----------");
		//		return strSubject;

		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getSubjectBySubAccountID(lSubAccountID, subjectType);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}
	/**
	 * @param lCracontractID
	 * @return @throws
	 *         IRollbackException
	 */
	public String getSubjectByOther(long lCracontractID, int subjectType)
		throws RemoteException, IRollbackException
	{
		
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getSubjectByOther(lCracontractID, subjectType);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
	}

	/**���ݽ��׷�������ID��ѯ��Ŀ��*/
	public String getSubjectByTransFeeTypeID(long transFeeTypeID) throws RemoteException, IRollbackException
	{
		String res = "";
		Sett_TransactionFeeTypeDAO dao = new Sett_TransactionFeeTypeDAO();
		TransFeeTypeSetInfo info;
		try
		{
			info = dao.findByID(transFeeTypeID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		if (info != null && info.getID() > 0)
		{
			res = info.getSubjectCode();
		}
		return res;

	}
	/**
	 * ���һ������˻�
	 * @param lAccountID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubAccountAssemblerInfo findSubCurrentAccountByAccountID(long lAccountID)
		throws RemoteException, IRollbackException
	{
		SubAccountAssemblerInfo o = null;
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		try
		{
			o = dao.findByAccountID(lAccountID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage());
		}
		return o;
	}
	/**
	 * ����˵���������޸��˻�״̬�����ʼƻ�
	 * 
	 * @param ai
	 * @return :���سɹ���ʶ
	 * @throws RemoteException,IRollbackException
	 */
	public long BatchUpdateAccount(QueryAccountConditionInfo qaci, AccountInfo ai, SubAccountCurrentInfo saci)
		throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
		try
		{
			lReturn = objSubAccountDAO.batchUpdate(qaci, saci); //���޸����˻������ݣ���������Ӱ�����˻���״̬
			lReturn = objAccountDAO.batchUpdate(qaci, ai); //����Update��Ľ��
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * ����˵���������˻�ID�޸��˻�״̬
	 * @param qaci
	 * @return long - �����˻�ID
	 * @throws Exception
	 */
	public long UpdateCheckStatus(long lAccountID,long lActionID,long lCheckStatusID, long lCheckUserID, Timestamp tsCheckDate)
		throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
		try
		{
			lReturn = objAccountDAO.updateCheckStatus(lAccountID,lActionID,lCheckStatusID, lCheckUserID, tsCheckDate);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}

	public long getCurrentSubAccoutIDByAccoutID(long accoutID) throws RemoteException, IRollbackException
	{

		AccountBean accBean = new AccountBean();
		try
		{
			log.debug("" + accBean);
			log.debug("accBean.getCurrentSubAccoutIDByAccoutID");
			return accBean.getCurrentSubAccoutIDByAccoutID(accoutID);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		long subAccountID = -1;
		//		SubAccountAssemblerInfo assembler = null;
		//		SubAccountCurrentInfo saci = null;
		//		Sett_AccountDAO aDao = new Sett_AccountDAO();
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		AccountInfo ai = null;
		//		long accountType = -1;
		//
		//		try
		//		{
		//			ai = aDao.findByID(accoutID);
		//			accountType = ai.getAccountTypeID();
		//			assembler = saDao.findByAccountID(accoutID);
		//		}
		//		catch (Exception e)
		//		{
		//			throw new IRollbackException(ctx, "�޷���ѯ���˻�: " + accoutID + " ��Ӧ�����˻�������ʧ��!!");
		//		}
		//		if (assembler != null
		//			&& assembler.getSubAccountCurrenctInfo() != null
		//			&& assembler.getSubAccountCurrenctInfo().getID() != -1)
		//		{
		//			subAccountID = assembler.getSubAccountCurrenctInfo().getID();
		//
		//		}
		//		else
		//		{
		//			throw new IRollbackException(ctx, "�޷���ѯ���˻�: " + accoutID + " ��Ӧ�Ļ������˻�������ʧ��!!");
		//		}
		//
		//		return subAccountID;

	}

	private long getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(long accoutID, String strFixedDepositNo)
		throws IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(accoutID, strFixedDepositNo);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		long subAccountID = -1;
		//		SubAccountAssemblerInfo assembler = null;
		//		SubAccountCurrentInfo saci = null;
		//		//Sett_AccountDAO aDao = new Sett_AccountDAO();
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		AccountInfo ai = null;
		//		long accountType = -1;
		//
		//		log.debug("----��ʼgetFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
		//		try
		//		{
		//			//ai = aDao.findByID(accoutID);
		//			assembler = saDao.findByFixedDepositNo(accoutID, strFixedDepositNo);
		//		}
		//		catch (SQLException e)
		//		{
		//			e.printStackTrace();
		//			throw new IRollbackException(ctx, "�޷�������˻�ID��:" + accoutID + " ���ڴ浥����: " + strFixedDepositNo + "�Ķ������˻�������ʧ��",e);
		//		}
		//		
		//		log.debug(""+assembler);
		//		log.debug(""+assembler.getSubAccountFixedInfo());
		//		log.debug(UtilOperation.dataentityToString(assembler.getSubAccountFixedInfo()));
		//		if (assembler != null
		//			&& assembler.getSubAccountFixedInfo() != null
		//			&& assembler.getSubAccountFixedInfo().getID() != -1)
		//		{			
		//			subAccountID = assembler.getSubAccountFixedInfo().getID();
		//
		//		}
		//		else
		//		{
		//			throw new IRollbackException(ctx, "�޷�������˻�ID��:" + accoutID + " ���ڴ浥����: " + strFixedDepositNo + " �Ķ������˻�������ʧ��!");
		//		}
		//		log.debug("----����getFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
		//		return subAccountID;

	}
	
	private Collection getFixedSubAccoutIDAndAmount(long accoutID,long nContractID)
	    throws IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getFixedSubAccoutIDAndAmount(accoutID, nContractID);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
	
	}
	
	private Collection getFixedSubAccoutIDAndAmount4CancelCheck(long accoutID,long nContractID) throws IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getFixedSubAccoutIDAndAmoun4CancelCheck(accoutID, nContractID);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
	}

	public long getLoanSubAccountIDByAccountIDAndLoanNoteID(long accoutID, long loanNoteID)
		throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getLoanSubAccountIDByAccountIDAndLoanNoteID(accoutID, loanNoteID);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		//		long subAccountID = -1;
		//		SubAccountAssemblerInfo assembler = null;
		//		SubAccountCurrentInfo saci = null;
		//		//Sett_AccountDAO aDao = new Sett_AccountDAO();
		//		Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
		//		AccountInfo ai = null;
		//		long accountType = -1;
		//
		//		try
		//		{
		//			//ai = aDao.findByID(accoutID);
		//			assembler = saDao.findByLoanNoteID(accoutID, loanNoteID);
		//		}
		//		catch (SQLException e)
		//		{
		//			throw new IRollbackException(ctx, "�޷�������˻�ID��:" + accoutID + " �ſ�֪ͨ������: " + loanNoteID + " �Ĵ������˻�������ʧ��",e);			
		//		}
		//		if (assembler != null
		//			&& assembler.getSubAccountFixedInfo() != null
		//			&& assembler.getSubAccountFixedInfo().getID() != -1)
		//		{
		//			subAccountID = assembler.getSubAccountFixedInfo().getID();
		//
		//		}
		//		else
		//		{
		//			throw new IRollbackException(ctx, "�޷�������˻�ID��:" + accoutID + " �ſ�֪ͨ������: " + loanNoteID + " �Ĵ������˻�������ʧ��");
		//		}
		//
		//		return subAccountID;

	}
	
	/**
	 * added by mzh_fu 2007/08/08
	 * @param accoutID
	 * @param loanNoteID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(
			long accoutID, long loanNoteID,long lStatus) throws RemoteException,
			IRollbackException {
		AccountBean accBean = new AccountBean();
		try {
			return accBean.getLoanSubAccountIDByAccountIDAndLoanNoteIDAndStatus(
					accoutID, loanNoteID,lStatus);
		} catch (IException e) {
			throw new IRollbackException(ctx, e);
		}
	}
	
	public Double queryAccountBalance(QueryAccountWhereInfo qawi) throws RemoteException, IRollbackException{
		try{
		QAccount qobj = new QAccount();
		String str=qobj.queryAccountBalanceStr(qawi);
		System.out.println(str);
		log.info("wzp "+str);
		Sett_AccountDAO dao=new Sett_AccountDAO();
		Double dl=dao.queryAccountBalance(str);
		return dl;
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(ctx,"",e);
		}
		
	}
	public long getAccountTypeBySubAccountID(long subAccountID) throws RemoteException, IRollbackException
	{
		AccountBean accBean = new AccountBean();
		try
		{
			return accBean.getAccountTypeBySubAccountID(subAccountID);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}

	}
	
	 //�˻�������������/
	public long doApproval(AccountInfo info)throws RemoteException, IRollbackException
	{
		
		long AccountId = -1;
		long transTypeID = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();

		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		transTypeID = inutParameterInfo.getTransTypeID();
		SessionMng sessionMng = inutParameterInfo.getSessionMng();
		
		
		
		try
		{	
			Sett_AccountDAO dao = new Sett_AccountDAO();
			AccountInfo accountInfo = new AccountInfo();
			accountInfo = this.findAccountByID(info.getAccountID());
			inutParameterInfo.setDataEntity(accountInfo);
			
			//�ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if(returnInfo.isLastLevel())
			{	
				if(transTypeID == SETTConstant.TransactionType.ACCOUNTOPEN){
					
					AccountId = this.UpdateCheckStatus(accountInfo.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED,-1,null);
					
				}else{
					AccountId = this.UpdateCheckStatus(info.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED, -1,null);
						
				}
				
				//������Զ�����
				if(FSWorkflowManager.isAutoCheck())
				{	
					AccountId = this.UpdateCheckStatus(info.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.CHECK, sessionMng.m_lUserID,Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
				}	
			}
			else if(returnInfo.isRefuse())
			{
				if(transTypeID == SETTConstant.TransactionType.ACCOUNTOPEN){
					
					AccountId = this.UpdateCheckStatus(info.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.NEWSAVE, -1,null);
				}
				else
				{
					AccountId = this.UpdateCheckStatus(info.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.OLDSAVE, -1,null);
				}
			}
			else
			{
				AccountId = accountInfo.getAccountID();
			}
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return AccountId;
	}
	/**
	 * ȡ���������������⣩��������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷���
	 * ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(AccountInfo info)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long lCheckStatus = -1;
		Sett_AccountDAO dao = new Sett_AccountDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		SessionMng sessionMng  = inutParameterInfo.getSessionMng();
		System.out.println("<<<<--------------------����cancelApproval()����--------------------->>>>");
		try
		{
			
			
			//���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if(FSWorkflowManager.isAutoCheck() && dao.findByID(info.getAccountID()).getCheckStatusID()==SETTConstant.AccountCheckStatus.CHECK)
			{
				if(inutParameterInfo.getTransTypeID()==SETTConstant.TransactionType.ACCOUNTOPEN){
					lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE;
				}else if(inutParameterInfo.getTransTypeID()==SETTConstant.TransactionType.ACCOUNTMODIFY){
					lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE;
				}
				lReturn = dao.updateCheckStatus(info.getAccountID(),SETTConstant.Actions.CANCELAPPROVAL,lCheckStatus,-1,null);
			}
			else
			{
				if(inutParameterInfo.getTransTypeID()==SETTConstant.TransactionType.ACCOUNTOPEN){
					lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE;
				}else if(inutParameterInfo.getTransTypeID()==SETTConstant.TransactionType.ACCOUNTMODIFY){
					lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE;
				}
				 //ȡ������
				lReturn = dao.updateCheckStatus(info.getAccountID(),SETTConstant.Actions.CANCELAPPROVAL,lCheckStatus, -1,null);
			}
           
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return lReturn;
	}

	/**
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long haveTrans(AccountInfo info)throws RemoteException, IRollbackException
	{
		Sett_AccountDAO dao = new Sett_AccountDAO();
		long lReturn;
		try
		{
			lReturn = dao.haveTrans(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		return lReturn;
	}
	
	/**
	 * �������洦�������Ϣ�ͼ�������
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws RemoteException, IRollbackException
	{
		log.debug("--------��ʼ�������洦�������Ϣ�ͼ�������------------");
		long lSubAccountID = -1;
		try
		{
			AccountBean accBean = new AccountBean();
			
			if(strCheckType.equals("check"))
			{
				lSubAccountID = getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
				tadi.setTransSubAccountID(lSubAccountID);
			}
			else
			{
				lSubAccountID = tadi.getTransSubAccountID();
			}
				
			if (lSubAccountID == -1)
			{
				throw new IRollbackException(ctx, "�޷�������˻�ID��:" + tadi.getTransAccountID() + " ���ڴ浥����: " + tadi.getFixedDepositNo() + " �Ķ������˻�������ʧ��");
			}
			
			accBean.ContinueFixedPreDrawInterest(tadi, strCheckType);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------�����������洦�������Ϣ�ͼ�������------------");
		
		return lSubAccountID;
	}
	
	//��ȡ�ͻ�
	public String findClientCodeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException
	{
		String clientCode = "";
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		try
		{
			clientCode = dao.findClientCodeBySubAccountID(lSubAccountID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage());
		}
		
		return clientCode;
	}
	
	//��ȡ�˻�����
	public long findAccountTypeBySubAccountID(long lSubAccountID) throws RemoteException, IRollbackException
	{
		long accountType = -1;
		Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
		try
		{
			accountType = dao.findAccountTypeBySubAccountID(lSubAccountID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(ctx, e.getMessage());
		}
		
		return accountType;
	}
	
	  /**
     * ����˵����ȡ������ʱ�������˻������ѽ��
	 * Boxu Add 2008��4��30��
     * @param lSubAccountID
     * @return String
     * @throws IRollbackException
     */
    public long updateCommission(TransAccountDetailInfo info) throws RemoteException, IRollbackException
    {
        long accountType = -1;
        try
        {
            Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
        	if(info.getTransSubAccountID() < 0)
        	{
        		throw new IException("�Ҳ������˻���Ϣ");
        	}
        	accountType = subAccountDAO.updateCommission(info);
        }
    	catch (Exception e)
        {
            e.printStackTrace();
        }                   
        return accountType;
    }
}