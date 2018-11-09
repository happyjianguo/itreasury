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
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{

	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
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
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	// private method write following block
	/**
	 * 方法说明：判断新增的客户编号是否已经存在。
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
	 * 方法说明：判断新增的账户编号是否已经存在。
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
	 * 方法说明： 新增客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回新增的客户ID
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
	 * 方法说明：取得客户编号
	 * 
	 * @param lOfficeID :
	 *            long @return: String - 新增的客户编号
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
	 * 方法说明：根据客户ID，查询客户信息
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
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
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
	 * 方法说明：修改客户信息
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - 客户ID
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
	 * 方法说明： 删除客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回删除的客户ID
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
				//modify by Forest,20040518,加入判断：如果该客户已被贷款模块使用，则不能被删除。
				//原已存在判断：如果该客户已经有账户了，则不能删除。
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
	 * 方法说明：新增活期账户
	 * 
	 * @param ai
	 *            @return: long - 新增的账户ID
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
				//modify by xwhe 2008-10-15 如果没有选收息账户，默认为当前的活期账户
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

			    //如果Info中的InutParameterInfo不为空,则需要提交审批 add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------提交审批--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+lAccountID);
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//提交审批
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					//更新状态到审批中
					objAccountDAO.updateCheckStatus(lAccountID,SETTConstant.Actions.SAVEANDINITAPPROVAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------提交审批成功--------");
				}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lAccountID;
	}
	/**
	 * 方法说明：新增定期和贷款账户
	 * 
	 * @param ai
	 *            @return: long - 新增的账户ID
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
			//如果Info中的InutParameterInfo不为空,则需要提交审批 add by haoliang
		    if(ai.getInutParameterInfo()!=null)
			{
					log.debug("------提交审批--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//提交审批
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					//更新状态到审批中
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------提交审批成功--------");
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * 方法说明：根据账户ID，查询账户信息
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
	 * 方法说明：根据子账户ID，查询子账户信息
	 * 
	 * @param lSubAccountID
	 *            子账户ID @return:SubAccountAssemblerInfo ai 子账户Assemble
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
	 * 方法说明：根据开户行ID，查询开户行信息
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
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
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
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
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
	 * 方法说明：修改活期账户的信息
	 * 
	 * @param ai
	 * @return :返回账户的ID
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
			//modify by xwhe 2008-10-24 不修改计提利息和计提日期
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
			else //如果没有选择开户行 则删除 以前设置 账户下面的开户行
				{
				objAccountBankDAO.deleteByAccountID(ai.getAccountID());
			}
			    //如果Info中的InutParameterInfo不为空,则需要提交审批 add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------提交审批--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//提交审批
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					if(ai.getCheckStatusID()==SETTConstant.AccountCheckStatus.NEWSAVE)
					{
						lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING;
					}else{
						lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALING;
					}
					//更新状态到审批中
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,lCheckStatus,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------提交审批成功--------");
				}

		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * 方法说明：修改定期和贷款账户的信息
	 * 
	 * @param ai
	 * @return :返回账户的ID
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
			
			    //如果Info中的InutParameterInfo不为空,则需要提交审批 add by haoliang
				if(ai.getInutParameterInfo()!=null)
				{
					log.debug("------提交审批--------");
					InutParameterInfo tempInfo = ai.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+ai.getAccountID());
					tempInfo.setTransID(ai.getAccountNo());
					tempInfo.setDataEntity(ai);
					//提交审批
					FSWorkflowManager.initApproval(ai.getInutParameterInfo());
					if(ai.getCheckStatusID()==SETTConstant.AccountCheckStatus.NEWSAVE)
					{
						lCheckStatus = SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALING;
					}else{
						lCheckStatus = SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALING;
					}
					//更新状态到审批中
					objAccountDAO.updateCheckStatus(ai.getAccountID(),SETTConstant.Actions.SAVEANDINITAPPROVAL,lCheckStatus,ai.getCheckUserID(),ai.getCheckDate());
					log.debug("------提交审批成功--------");
				}
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * 方法说明：得到新的账户号
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return ：返回账户编号
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
	 * 方法说明：得到新的账户号（备付金账户，准备金账户，拆借账户 适用）
	 * 
	 * @param lOfficeID 当前操作机构
	 * @param lOfficeID 客户所属机构
	 * @param lAccountGroupTypeID 账户组类型
	 * @param lAccountTypeID 账户类型
	 * @param lCurrencyID
	 * @return ：返回账户编号
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
	 * 方法说明：判断是否透支
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 透支返回 true; 不透支返回false
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
		//		log.debug("--------开始透支检查-----------");
		//		log.debug("sfasdfa;sdjk;3223231111-开始透支检查");
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
		//			//			log.debug("----------账户信息为：---------");
		//			//			log.debug(UtilOperation.dataentityToString(assembler.getSubAccountCurrenctInfo()));
		//			//			log.debug(UtilOperation.dataentityToString(assembler.getSubAccountFixedInfo()));
		//			if (ai != null){
		//				assembler = saDao.findByID(subAccountID);
		//				if(assembler == null)
		//					throw new IRollbackException(ctx, "无法找到子账户"+subAccountID+"所对应的信息，账户余额透支检查错误,交易失败");
		//			}else{
		//				throw new IRollbackException(ctx, "无法找到账户"+lAccountID+"所对应的信息，账户余额透支检查错误,交易失败");
		//			}
		//
		//		}
		//		catch (Exception e1)
		//		{
		//			// TODO Auto-generated catch block
		//			e1.printStackTrace();
		//			throw new IRollbackException(ctx, e1.getMessage(), e1);
		//		}
		//		log.debug("---------判断账户类型------------");
		//		if (SETTConstant.AccountType.isCurrentAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("判断活期账户是否透支,支取金额:" + dPayAmount);
		//			
		//			
		//				SubAccountCurrentInfo saci = assembler.getSubAccountCurrenctInfo();
		//				if (saci != null)
		//				{
		//					//lSubAccountID = saci.getID();
		//					//log.debug("---------获得活期支取子账户ID" + lSubAccountID + "--------------");
		//					log.info("单笔最高金额限制=" + ai.getMaxSinglePayAmount());
		//					// 1、支取金额 > 单笔最高金额限制，提示“付款金额大于单笔最高金额限制”
		//					if (dPayAmount > ai.getMaxSinglePayAmount() && ai.getMaxSinglePayAmount() > 0)
		//					{
		//						throw new IRollbackException(ctx, "Sett_E115", String.valueOf(ai.getMaxSinglePayAmount()));
		//					}
		//					//Sett_AccountDAO accDAO = new Sett_AccountDAO(); 
		//					//double sumMonthAmout = accDAO.getMonthSumAmount(ai, ai.getOfficeID(), ai.getCurrencyID());
		//					log.debug("月度累计发生额限制=" + saci.getMonthLimitAmount());
		//					log.debug("月度累计发生额=" + ai.getMonthSumAmount());
		//					if(saci.getMonthLimitAmount() != 0 && ai.getMonthSumAmount() < saci.getMonthLimitAmount()){
		//						throw new IRollbackException(ctx, "活期存款账户的月度累计发生额超过限制,交易失败");
		//					}
		//					log.info(
		//						"当前余额 - 累计未复核金额 - 支取金额=" + (saci.getBalance() - saci.getDailyUncheckAmount() - dPayAmount));
		//					log.info("最低余额=" + saci.getCapitalLimitAmount());
		//					// 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
		//					log.debug("当前余额："+saci.getBalance());
		//					log.debug("累计未复核金额："+saci.getDailyUncheckAmount());
		//					log.debug("支取金额："+dPayAmount);					
		//					if (UtilOperation.Arith.round(saci.getBalance(), 2)
		//						- UtilOperation.Arith.round(saci.getDailyUncheckAmount(), 2)
		//						- UtilOperation.Arith.round(dPayAmount, 2)
		//						< saci.getCapitalLimitAmount())
		//					{
		//						throw new IRollbackException(ctx, "Sett_E116", String.valueOf(saci.getCapitalLimitAmount()));
		//					}
		//					// 透支检查未完.......
		//					bReturn = false;
		//				}
		//			
		//		}
		//		else if (SETTConstant.AccountType.isFixAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("判断定期账户是否透支,支取金额:" + dPayAmount);
		//			SubAccountFixedInfo safi = assembler.getSubAccountFixedInfo();
		//			if (safi != null)
		//			{
		//
		//				
		//				// 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
		//				log.debug("当前余额："+safi.getBalance());
		//				log.debug("累计未复核金额："+safi.getDailyUncheckAmount());
		//				log.debug("支取金额："+dPayAmount);
		//				log.info(
		//						"当前余额 - 累计未复核金额 - 支取金额=" + (safi.getBalance() - safi.getDailyUncheckAmount() - dPayAmount));				
		//				if (UtilOperation.Arith.round(safi.getBalance(), 2)
		//					- UtilOperation.Arith.round(safi.getDailyUncheckAmount(), 2)
		//					- UtilOperation.Arith.round(dPayAmount, 2)
		//					< 0.0)
		//				{
		//					throw new IRollbackException(ctx, "Sett_E116", String.valueOf(0));
		//				}				
		//
		//				log.debug("---------未超过单笔最低金额限制-----------");
		//				bReturn = false;
		//			}
		//		}
		//		else if (SETTConstant.AccountType.isLoanAccountType(ai.getAccountTypeID()))
		//		{
		//			log.info("判断贷款账户是否透支,支取金额:" + dPayAmount);
		//			SubAccountLoanInfo sali = assembler.getSubAccountLoanInfo();
		//			log.debug("11111111111111111111111111111111111111111");
		//			if (sali != null)
		//			{
		//				
		//				// 2、当前余额 - 累计未复核金额 - 支取金额 < 最低余额，提示“账户余额低于最低余额”
		//				log.info(
		//						"当前余额 - 累计未复核金额 - 支取金额=" + (sali.getBalance() - sali.getDailyUncheckAmount() - dPayAmount));								
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
		//			log.debug("-----------" + "账户" + lAccountID + "没有透支---------------");
		//		else
		//			throw new IRollbackException(ctx, "账户余额透支检查错误,交易失败");		
		//		return bReturn;
	}
	
	/**
	 * 方法说明：判断 账户余额支付累计未复核交易金额后是否还有余额支付此交易金额（实际余额-累计未复核金额-交易金额<0,不考虑账户是否允许透支）
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 *            @return: 不足 返回true; 足 返回false
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
		//		log.debug("----------validateAccountStatus开始--------------");
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
		//		log.debug("---------该账户信息为:------------"+ai);
		//		log.debug(UtilOperation.dataentityToString(ai));
		//		if (ai == null)
		//		{
		//			// 提示“付款账户ＸＸ不存在”
		//			throw new IRollbackException(ctx, "Sett_E117", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.FREEZE)
		//		{
		//			// 提示“付款账户ＸＸ已销户”
		//			throw new IRollbackException(ctx, "Sett_E118", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.SEALUP)
		//		{
		//			// 提示“付款账户ＸＸ已封存”
		//			throw new IRollbackException(ctx, "Sett_E120", ai.getAccountNo());
		//		}
		//		if (ai.getStatusID() == SETTConstant.AccountStatus.CLOSE)
		//		{
		//			// 提示“付款账户ＸＸ已清户”
		//			throw new IRollbackException(ctx, "Sett_E119", ai.getAccountNo());
		//		}
		//		log.debug("----------validateAccountStatus结束--------------");
		//		return lReturn;
	}
	/**
	 * 方法说明 ： 开立活期存款子账户
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
	 * 方法说明 ： 开立定期子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openFixSubAccount(SubAccountFixedInfo safi) throws RemoteException, IRollbackException
	{
		long lSubAccountID = -1;
		log.debug("---------开始openFixSubAccount-----------");
		AccountBean accBean = new AccountBean();

		if (validateAccountStatus(safi.getAccountID()) == SETTConstant.AccountStatus.NORMAL
			/*&& isOverDraft(safi.getAccountID(), 0.0) == false*/
			)
		{
			log.debug("---------定期子账户没有透支-----------");
			Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
			try
			{
				log.debug("---------Sett_SubAccountDAO增加定期账户-----------");
				lSubAccountID = objSubAccountDAO.addSubAccountFix(safi);

			}
			catch (Exception e)
			{
				throw new IRollbackException(ctx, e.getMessage());
			}
		}

		log.debug("---------结束openFixSubAccount-----------");
		return lSubAccountID;
	}
	/**
	 * 方法说明 ： 开立贷款存款子账户
	 * 
	 * @param safi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long openLoanSubAccount(SubAccountLoanInfo sali) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		log.debug("--------开始开立贷款账户---------");
		//判断是否一次性收取收取手续费
		Loan_DAO loanDAO = new Loan_DAO();
		ContractFormInfo cfInfo = null;
		try
		{
			cfInfo = loanDAO.getContractInfoByLoanNoteID(sali.getLoanNoteID());
			if (cfInfo.getChargeRateType() == LOANConstant.ChargeRatePayType.ONETIME || cfInfo.getChargeRateType() == LOANConstant.ChargeRatePayType.YEAR)
			{
				log.debug("--------一次行收取手续费或按年收取（生成子账户时计算手续费）---------");
				LoanPayFormDetailInfo lpfInfo = loanDAO.getLoanPayFormDetailInfoByID(sali.getLoanNoteID());
				
				//Modify by leiyang 2008/07/01 修改一次性收取手续费的算法
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
				log.debug("--------手续费金额:" + commission + "---------");

				sali.setCommission(commission);
				//银团贷款在子账户中不生成手续费记录，为了在利息结算处理时不能查询到银团贷款手续费 modify zcwang 2007-6-21
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
		log.debug("--------结束开立贷款账户，子账户ID是:" + lReturn + " ---------");
		return lReturn;
	}
	/**
	 * 方法说明：增加账户余额。
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
		//					log.debug("更新子账户"+lSubAccountID+"状态从清户到正常");
		//					subAccountDAO.updateFinishDateAndStatus(lSubAccountID, SETTConstant.SubAccountStatus.NORMAL, null);
		//				}
		//			}
		//			log.debug("更新子账户"+lSubAccountID+"余额到"+dPayAmount);
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
	 * 方法说明：减少账户余额。
	 * 
	 * @param lAccountID
	 * @param dPayAmount
	 * @return 子账户ID
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
		//		log.debug("-------开始扣除子账户"+lSubAccountID+"的余额"+dPayAmount);
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
		//				log.debug("-----******余额"+balance);
		//				if (balance - dPayAmount <= 0){
		//					log.debug("------定期或贷款子账户余额为0，账户清户--------");
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
		//		log.debug("----结束扣除账户余额操作------");		
		//		return lReturn;
	}

	/**
	 * 方法说明：增加活期子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
	 * 方法说明：增加定期子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
     * 融资租赁 保证金保后处理  更新 子账户中 累计未复核付款金额 字段。保存时，将 该字段 的值直接赋为 当前账户余额 这个字段的值。
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
	 * 方法说明：增加贷款子账户累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
	 * 方法说明：增加累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数dPaymount为负数
	 * 
	 * @param lAccountID
	 * @param lOppAccountID 如果是-1表明不用考虑对方账户
	 * @param dPayAmount
	 * @return @throws
	 *         RemoteException
	 * @isCancelOperation 如果是取消复核操作调用增加未复核金额则不检查透支
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
	 * 方法说明：扣除活期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
	 * 方法说明：扣除定期累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
	 * 融资租赁 保证金 保后处理  更新 子账户 中 累计未复核金额，更新为0.0
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
	 * 方法说明：扣除贷款累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数
	 * dPaymount为负数
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
	 * 方法说明：扣除累计未复核付款金额。 保存时：未复核累计付款金额增加，参数dPaymount为正数；复核时：未复核累计付款金额减少，参数dPaymount为负数
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

		//		log.debug("-------开始扣除子账户 " + lSubAccountID + " 的累计未复核金额---------");
		//		log.debug("-------金额是 " + dPayAmount + "---------");
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
		//		log.debug("-------结束扣除累计未复核金额---------");
	}
	
	
	/**
	 * 方法说明：活期支取。
	 * 
	 * @param tadi
	 * @return 子账户ID
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
	 * 定期支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------开始定期支取withdrawFix------------");
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
					"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
			}

			accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------结束定期支取withdrawFix------------");
		return lSubAccountID;
	}
	
	public Collection withdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException
	{
		log.debug("--------开始支取withdrawFix4Recog------------");
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
						"无法获得主账户ID是:" + tadi.getTransAccountID() + " 的子账户，交易失败");
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
						"无法获得主账户ID是:" + tadi.getTransAccountID() + " 的子账户，交易失败");
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
		log.debug("--------结束支取withdrawFix4Recog------------");
		return c;
	}
	
	/**
	 * 融资租赁还款支取
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long withdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------开始融资租赁还款withdrawFinance------------");
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
					"无法获得主账户ID是:" + tadi.getTransAccountID() + " 收款通知单号是: " + tadi.getFixedDepositNo() + " 的融资租赁子账户，交易失败");
			}

			accBean.withdraw(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------结束融资租赁还款withdrawFinance------------");
		return lSubAccountID;
	}

	/**
	 * 方法说明：取消活期支取。
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
		//		//		// 检查账户状态是否正常，账户余额是否透支
		//		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		//		{
		//		//			// 增加累计未复核金额
		//		//			addUncheckAmount(tadi.getTransAccountID(),tadi.getTransSubAccountID(), tadi.getAmount());
		//		//			// 增加账户余额
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
	 * 取消融资租赁还款存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFinance(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("--------开始取消融资租赁还款存入cancelWithdrawFinance------------");
		//long lSubAccountID =
		//	this.getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		//modify by zwxiao 2010-08-08 由于取得子账户的时候，如果子账户的状态为已结清的时候会出现找不到子账户的情况，所以重写该方法
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
					"无法获得主账户ID是:" + tadi.getTransAccountID() + " 收款通知单号是: " + tadi.getFixedDepositNo() + " 的融资租赁子账户，交易失败");
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
						"无法获得主账户ID是:" + tadi.getTransAccountID() + " 收款通知单号是: " + tadi.getFixedDepositNo() + " 的融资租赁子账户，交易失败");
			}
		}
		tadi.setTransSubAccountID(lSubAccountID);
		if (lSubAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 收款通知单号是: " + tadi.getFixedDepositNo() + " 的融资租赁子账户，交易失败");
		}		
		cancelWithdraw(tadi);
		log.debug("--------结束取消融资租赁还款存入cancelWithdrawFinance------------");
	}
	
	/**
	 * 取消定期存入
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */

	public void cancelWithdrawFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		if (tadi.getTransSubAccountID() == -1)
		{
			throw new IRollbackException(ctx, "无法获得定期子账户，取消定期支取交易失败");
		}
		//		long lSubAccountID =
		//			this.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
		//		tadi.setTransSubAccountID(lSubAccountID);
		//		if (lSubAccountID == -1)
		//		{
		//			throw new IRollbackException(
		//				ctx,
		//				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
		//		}		
		cancelWithdraw(tadi);
	}
	
	public void cancelWithdrawFix4Recog(TransAccountDetailInfo tadi,long nContractID) throws RemoteException, IRollbackException
	{
		
		log.debug("--------开始支取withdrawFix4Recog------------");
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
						"无法获得主账户ID是:" + tadi.getTransAccountID() + " 的子账户，交易失败");
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
							"无法获得主账户ID是:" + tadi.getTransAccountID() + " 的子账户，交易失败");
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
		log.debug("--------结束支取withdrawFix4Recog------------");
	}

	/**private method for cancel withdraw*/
	private void cancelWithdraw(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//
		//		//注意：先增加账户余额再增加未复核金额
		//		// 检查账户状态是否正常，账户余额是否透支
		//
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		{
		//			// 增加账户余额
		//			long lSubAccountID = tadi.getTransSubAccountID();
		//			if (lSubAccountID == -1)
		//			{
		//				throw new IRollbackException(ctx, "交易的子账户不存在，交易失败");
		//			}
		//			addAccountBalance(lSubAccountID, tadi.getAmount());
		//			// 增加累计未复核金额
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
		//		// 检查账户状态是否正常，账户余额是否透支
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//			&& isOverDraft(tadi.getTransAccountID(),tadi.getTransSubAccountID(),  0.0) == false)
		//		{
		//			log.debug("--------账户状态正常，账户余额不透支------------");
		//			log.debug("--------交易类型ID是: " + tadi.getTransactionTypeID() + "----------");
		//			if (SETTConstant.TransactionType.isCurrentTransaction(tadi.getTransactionTypeID())
		//				|| SETTConstant.TransactionType.isFixedTransaction(tadi.getTransactionTypeID()))
		//			{
		//				// 扣除累计未复核金额
		//				log.debug("--------扣除子账户　"+tadi.getTransSubAccountID()+"　的累计未复核金额------------");
		//				subtractUncheckAmount(tadi.getTransSubAccountID(), tadi.getAmount());
		//			}
		//			// 减少账户余额
		//			log.debug("--------减少账户余额: " + tadi.getAmount() + "----------");
		//			lSubAccountID = tadi.getTransSubAccountID();
		//			if (lSubAccountID == -1)
		//			{
		//				throw new IRollbackException(ctx, "没有获得可用的子账户，交易失败");
		//				
		//			}
		//			subtractAccountBalance(lSubAccountID, tadi.getAmount());
		//			// 生成交易明细账
		//			sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO();
		//			log.debug("--------生成交易明细账------------");
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
	 * 方法说明：删除定期子账户。注意，该方法目前只做为一个返回子账户的方法，实际的删除将在定期或贷款减少账户余额到0的时候自动删除该账户
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
			log.debug("------删除定期子账户" + subAccountID + "--------");
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
	 * 方法说明：删除贷款子账户。(本方法不删除账户，删除操作在扣除账户余额时进行)
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
			log.debug("------删除贷款子账户" + subAccountID + "--------");
			dao.updateStatus(subAccountID, Constant.RecordStatus.INVALID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		//return lSubAccount;
	}

	/**
	 * 方法说明：活期存入。
	 * 
	 * @param tadi
	 * @return 子账户ID
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
	 * 方法说明：活期存入。
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long depositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//		// 检查账户状态是否正常，账户余额是否透支
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL)
		//		{
		//			lSubAccountID = tadi.getTransSubAccountID();
		//			
		//			// 增加账户余额
		//			addAccountBalance(lSubAccountID, tadi.getAmount());
		//			// 生成交易明细账
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
		log.debug("----------开始定期存入--------------");
		AccountBean accBean = new AccountBean();
		try
		{
			if (tadi.getTransSubAccountID() < 0)
			{
				long lSubAccountID =
					getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(tadi.getTransAccountID(), tadi.getFixedDepositNo());
				tadi.setTransSubAccountID(lSubAccountID);
			}

			log.debug("账户ID是:" + tadi.getTransSubAccountID());
			if (tadi.getTransSubAccountID() == -1)
			{
				throw new IRollbackException(
					ctx,
					"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
			}

			accBean.depoist(tadi);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("----------结束定期存入--------------");
		return tadi.getTransSubAccountID();
	}

	/**定期活期存入*/
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
		//		// 检查账户状态是否正常，账户余额是否透支
		//		try {
		//			long lSubAccountID = -1;
		//			log.debug("--------开始存入----------");
		//			if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& validateMinSinglePayAmount(tadi.getTransAccountID(), tadi.getAmount()))
		//			{
		//				log.debug("--------账户状态正常------------");
		//				lSubAccountID = tadi.getTransSubAccountID();
		//				if (lSubAccountID == -1)
		//				{
		//					throw new IRollbackException(ctx, "交易的子账户不存在，交易失败");
		//				}
		//				// 增加账户余额
		//				log.debug("--------增加账户余额: " + tadi.getAmount() + "----------");
		//				addAccountBalance(lSubAccountID, tadi.getAmount());
		//				// 生成交易明细账
		//				tadi.setTransDirection(SETTConstant.ControlDirection.CREDIT);
		//				tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
		//				log.debug("--------生成交易明细账------------");
		//				sett_TransAccountDetailDAO taddao = new sett_TransAccountDetailDAO();
		//				taddao.add(tadi);
		//				log.debug("--------结束存入----------");
		//			}
		//		} catch (SQLException e) {
		//			throw new IRollbackException(ctx, e.getMessage());
		//		}

	}

	/**存入时验证存入金额是否超过最小起存金额*/
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
	 * 删除账户交易明细表的交易明细
	 * 
	 * @param strTransNo
	 *            交易编号
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
	 * 方法说明：取消活期存入。
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public void cancelDepositCurrent(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{

		//		// 检查账户状态是否正常，账户余额是否透支
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& isOverDraft(tadi.getTransAccountID(), tadi.getAmount()) == false)
		//		{
		//			// 减少账户余额
		//			subtractAccountBalance(tadi.getTransAccountID(), tadi.getAmount());
		//		}
		log.debug("AccoutEJB::cancelDepositCurrent start");
		long lSubAccountID = getCurrentSubAccoutIDByAccoutID(tadi.getTransAccountID());
		log.debug("AccoutEJB::getCurrentSubAccoutIDByAccoutID end---------------");
		tadi.setTransSubAccountID(lSubAccountID);
		if (lSubAccountID == -1)
		{
			throw new IRollbackException(ctx, "无法获得主账户ID是:" + tadi.getTransAccountID() + " 的定期子账户，交易失败");
		}
		log.debug("AccoutEJB::cancelDeposit start");
		cancelDeposit(tadi);
		log.debug("AccoutEJB::cancelDeposit end");
	}

	/**
	 * 方法说明：取消定期存入。
	 * 
	 * @param tadi
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long cancelDepositFix(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug(UtilOperation.dataentityToString(tadi));
		//		// 检查账户状态是否正常，账户余额是否透支
		//		if (validateAccountStatus(tadi.getTransAccountID()) == SETTConstant.AccountStatus.NORMAL
		//				&& isOverDraft(tadi.getTransAccountID(), tadi.getAmount()) == false)
		//		{
		//			// 减少账户余额
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
				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
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
	 * 方法说明：贷款发放
	 * 
	 * @param tadi
	 * @return 子账户ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		//		log.debug("-------开始贷款放款---------");
		//		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		//		if (subAccountID == -1)
		//		{
		//			throw new IRollbackException(
		//				ctx,
		//				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
		//		}
		//子账户ID在AccountBook中已经获得
		//tadi.setTransSubAccountID(tadi.getTransSubAccountID());

		depoist(tadi);

		//		Sett_TransGrantLoanDAO grantLoanDAO = new Sett_TransGrantLoanDAO();
		//		try {
		//			grantLoanDAO.updateLoanPayFormStatus(tadi.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.USED);
		//		} catch (SQLException e) {
		//			throw new IRollbackException(ctx,"发生数据库错误",e);
		//		} 
		log.debug("-------结束贷款放款---------");
		return tadi.getTransSubAccountID();
	}

	/**
	 * 方法说明：取消贷款发放
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelGrantLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------开始取消贷款放款---------");
		long subAccountID = tadi.getTransSubAccountID();
		log.print("------子账户id:"+subAccountID);
		if(subAccountID <0)
		{
		    log.debug("-------开始取消贷款放款---------");
		    subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		}
		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
		}
		tadi.setTransSubAccountID(subAccountID);
		cancelDeposit(tadi);
		log.debug("-------结束取消贷款放款---------");
		return subAccountID;
	}

	/**
	 * 贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------开始贷款归还---------");

		long subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
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
		log.debug("-------结束贷款归还---------");
		return subAccountID;
	}
	/**
	 * 取消贷款归还
	 * 
	 * @param tadi
	 * 
	 * @throws RemoteException
	 * @throws IRollbackException
	 * 
	 */
	public long cancelRepayLoan(TransAccountDetailInfo tadi) throws RemoteException, IRollbackException
	{
		log.debug("-------开始取消贷款归还---------");
		long subAccountID = tadi.getTransSubAccountID();
		if (subAccountID < 0)
		{
			subAccountID = getLoanSubAccountIDByAccountIDAndLoanNoteID(tadi.getTransAccountID(), tadi.getLoanNoteID());
		
		}

		if (subAccountID == -1)
		{
			throw new IRollbackException(
				ctx,
				"无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
		}
		//added by qhzhou 2008-03-14
		tadi.setTransSubAccountID(subAccountID);
		
		cancelWithdraw(tadi);
		log.debug("-------结束取消贷款归还---------");
		return subAccountID;
	}

	/**
	 * 方法说明：保存外部账户
	 * 
	 * @param ExternalAccountInfo
	 * @return 保存记录ID
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
		//		log.debug("--------开始getSubjectBySubAccountID,子账户ID是:" + lSubAccountID + "-----------");
		//		String strSubject = null;
		//		//根据子账户ID，从账户子表sett_SubAccount中查找主账户
		//		long accountTypeID = -1;
		//		try
		//		{
		//			Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
		//			SubAccountAssemblerInfo subAccountAssemblerInfo = subAccountDAO.findByID(lSubAccountID);
		//			long accoontID = subAccountAssemblerInfo.getSubAccountCurrenctInfo().getAccountID();
		//			log.debug("-------------对应的主账户ID是: " + accoontID + "---------------");
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
		//			{ //账户中没有保存科目信息，从账户类型编码设置附加表查找科目
		//				log.debug("---------账户中没有保存科目信息 或者是取利息/计提利息科目，开始从账户类型编码设置附加表查找科目------------");
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
		//				{ //无下级分类
		//					log.debug("---------取账户科目号，无下级分类-------------");
		//					Sett_CurrencySubjectDAO currencySubjectDAO = new Sett_CurrencySubjectDAO();
		//					strSubject =
		//						currencySubjectDAO.findSubjectCodeByTableNameAndAccoutTypeIDAndCurrencyIDAndOfficeID("Sett_accounttype", accountTypeID, accountInfo.getCurrencyID(), accountInfo.getOfficeID());
		//					log.debug("-------------1.科目号是: " + strSubject + "---------------");
		//
		//				}
		//				else
		//					if (isExistSubClass == 1)
		//					{
		//						if (accountGroupType == SETTConstant.AccountGroupType.FIXED)
		//						{
		//							log.debug("---------定期下级分类-------------");
		//							Sett_SubAccounTtype_FixedDAO subAccounTtype_FixedDAO = new Sett_SubAccounTtype_FixedDAO();
		//							if (depositTerm == -1 && noticeDay == -1)
		//								throw new IRollbackException(ctx, "账户信息(定期存款期限或通知存款天数)不正确，交易失败");
		//
		//							//if (depositTerm == -1 && noticeDay != -1) //是通知存款
		//							//	
		//							if (noticeDay > 0)
		//								depositTerm = noticeDay;
		//
		//							switch (subjectType)
		//							{
		//								case AccountOperation.SUBJECT_TYPE_ACCOUNT :
		//									{
		//										log.debug("---------取账户对应的科目-------------");
		//										strSubject = subAccounTtype_FixedDAO.findAccountSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//									break;
		//								case AccountOperation.SUBJECT_TYPE_INTEREST :
		//									{
		//										log.debug("---------取利息对应的科目-------------");
		//										strSubject = subAccounTtype_FixedDAO.findInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//									break;
		//								case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST :
		//									{
		//										log.debug("---------取计提利息对应的科目-------------");
		//										strSubject = subAccounTtype_FixedDAO.findPredrawInterestSubjectCodeByAccountTypeIDAndFixDepositMonthID(accountTypeID, depositTerm);
		//									}
		//
		//							}
		//
		//							log.debug("-------------2.科目号是: " + strSubject + "---------------");
		//						}
		//						else
		//							if (accountGroupType == SETTConstant.AccountGroupType.LOAN)
		//							{
		//								log.debug("---------贷款下级分类-------------");
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
		//										log.debug("---------账户类型是贴现账户-------------");
		//										Loan_DAO lDAO = new Loan_DAO();
		//										draftType = lDAO.getAcceptPoTypeIDByDiscountCredenceID(loanNoteID);
		//										//贴现账户不匹配以下条件
		//										accountTypeInfo.setIsLoanType(-1);
		//										accountTypeInfo.setIsLoanMonth(-1);
		//										accountTypeInfo.setIsLoanYear(-1);
		//										accountTypeInfo.setIsCosign(-1);
		//
		//									}
		//									else
		//										throw new IRollbackException(ctx, "无法找到放款通知单号为" + loanNoteID + "的放款通知单所对应的合同信息,交易失败");
		//
		//								//loanDAO. 
		//								switch (subjectType)
		//								{
		//									case AccountOperation.SUBJECT_TYPE_ACCOUNT :
		//										{
		//											log.debug("---------取账户对应的科目-------------");
		//											strSubject = subAccounTtype_LoanDAO.findAccountSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//										}
		//										break;
		//									case AccountOperation.SUBJECT_TYPE_INTEREST :
		//										{
		//											log.debug("---------取利息对应的科目-------------");
		//											strSubject = subAccounTtype_LoanDAO.findInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//										}
		//										break;
		//									case AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST :
		//										{
		//											log.debug("---------取计提利息对应的科目-------------");
		//											strSubject = subAccounTtype_LoanDAO.findPredrawInterestSubjectCode(accountTypeInfo, loanTypeID, consignClientID, intervalNum, draftType, year);
		//
		//										}
		//
		//								}
		//
		//								log.debug("-------------2.科目号是: " + strSubject + "---------------");
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
		//		log.debug("-----------开始校验科目号合法性----------------");
		//		if (strSubject == null || strSubject.compareToIgnoreCase("") == 0)
		//		{
		//			throw new IRollbackException(ctx, "账户类型" + accountTypeID + "所属科目号为空");
		//		}
		//
		//		GeneralLedgerOperation glOperation = new GeneralLedgerOperation();
		//		//GLSubjectDefinitionInfo glSubjectDefInfo1 = glOperation.findBySubjectOldCode(strSubject);
		//		//GLSubjectDefinitionInfo glSubjectDefInfo2 = glOperation.findBySubjectCode(strSubject);
		//		if (!glOperation.isExistSubeject(strSubject))
		//			throw new IRollbackException(ctx, "账户类型" + accountTypeID + "所属科目:" + strSubject + " 不存在或已删除"); //else
		//
		//		log.debug("--------结束getSubjectBySubAccountID,科目号是: " + strSubject + "-----------");
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

	/**根据交易费用类型ID查询科目号*/
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
	 * 查找活期子账户
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
	 * 方法说明：批量修改账户状态和利率计划
	 * 
	 * @param ai
	 * @return :返回成功标识
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
			lReturn = objSubAccountDAO.batchUpdate(qaci, saci); //先修改子账户的数据，这样不会影响主账户的状态
			lReturn = objAccountDAO.batchUpdate(qaci, ai); //返回Update后的结果
		}
		catch (Exception e)
		{
			throw new IRollbackException(ctx, e.getMessage());
		}
		return lReturn;
	}
	/**
	 * 方法说明：根据账户ID修改账户状态
	 * @param qaci
	 * @return long - 返回账户ID
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
		//			throw new IRollbackException(ctx, "无法查询到账户: " + accoutID + " 对应的子账户，交易失败!!");
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
		//			throw new IRollbackException(ctx, "无法查询到账户: " + accoutID + " 对应的活期子账户，交易失败!!");
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
		//		log.debug("----开始getFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
		//		try
		//		{
		//			//ai = aDao.findByID(accoutID);
		//			assembler = saDao.findByFixedDepositNo(accoutID, strFixedDepositNo);
		//		}
		//		catch (SQLException e)
		//		{
		//			e.printStackTrace();
		//			throw new IRollbackException(ctx, "无法获得主账户ID是:" + accoutID + " 定期存单号是: " + strFixedDepositNo + "的定期子账户，交易失败",e);
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
		//			throw new IRollbackException(ctx, "无法获得主账户ID是:" + accoutID + " 定期存单号是: " + strFixedDepositNo + " 的定期子账户，交易失败!");
		//		}
		//		log.debug("----结束getFixedSubAccoutIDByAccoutIDAndFixedDepositNo------");
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
		//			throw new IRollbackException(ctx, "无法获得主账户ID是:" + accoutID + " 放款通知单号是: " + loanNoteID + " 的贷款子账户，交易失败",e);			
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
		//			throw new IRollbackException(ctx, "无法获得主账户ID是:" + accoutID + " 放款通知单号是: " + loanNoteID + " 的贷款子账户，交易失败");
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
	
	 //账户开户审批方法/
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
			
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				if(transTypeID == SETTConstant.TransactionType.ACCOUNTOPEN){
					
					AccountId = this.UpdateCheckStatus(accountInfo.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.NEWSAVE_APPROVALED,-1,null);
					
				}else{
					AccountId = this.UpdateCheckStatus(info.getAccountID(),SETTConstant.Actions.DOAPPRVOAL,SETTConstant.AccountCheckStatus.OLDSAVE_APPROVALED, -1,null);
						
				}
				
				//如果是自动复核
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
	 * 取消审批方法（特殊）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
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
		System.out.println("<<<<--------------------进入cancelApproval()方法--------------------->>>>");
		try
		{
			
			
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
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
				 //取消审批
				lReturn = dao.updateCheckStatus(info.getAccountID(),SETTConstant.Actions.CANCELAPPROVAL,lCheckStatus, -1,null);
			}
           
			//将审批记录表内的该交易的审批记录状态置为无效
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
	 * 到期续存处理计提利息和计提日期
	 * @param tadi
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long ContinueFixedPreDrawInterest(TransAccountDetailInfo tadi, String strCheckType) throws RemoteException, IRollbackException
	{
		log.debug("--------开始到期续存处理计提利息和计提日期------------");
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
				throw new IRollbackException(ctx, "无法获得主账户ID是:" + tadi.getTransAccountID() + " 定期存单号是: " + tadi.getFixedDepositNo() + " 的定期子账户，交易失败");
			}
			
			accBean.ContinueFixedPreDrawInterest(tadi, strCheckType);
		}
		catch (IException e)
		{
			throw new IRollbackException(ctx, e);
		}
		log.debug("--------结束到期续存处理计提利息和计提日期------------");
		
		return lSubAccountID;
	}
	
	//获取客户
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
	
	//获取账户类型
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
     * 方法说明：取消复核时更新子账户手续费金额
	 * Boxu Add 2008年4月30日
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
        		throw new IException("找不到子账户信息");
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