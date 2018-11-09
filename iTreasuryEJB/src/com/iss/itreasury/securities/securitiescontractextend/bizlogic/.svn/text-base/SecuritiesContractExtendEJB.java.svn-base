package com.iss.itreasury.securities.securitiescontractextend.bizlogic;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.securities.apply.dao.SEC_ApplyDAO;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiescontract.dao.ContractBondTypeDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBalanceInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBondTypeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.securitiescontractextend.dao.Sec_ExtendFormDao;
import com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo;
import com.iss.itreasury.securities.securitiescontractplan.dao.SecuritiesContractPlanDao;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @Name: SecuritiesContractExtendEJB.java @Description: @ @Description:
 * 委托理财合同展期 @Author: gqfang @Create Date: 2005-4-19 To change the template for
 * this generated type comment go to Window - Preferences - Java - Code
 * Generation - Code and Comments
 */
public class SecuritiesContractExtendEJB implements SessionBean
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
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
	/**
	 * 查找单条合同信息
	 * 
	 * @param lID
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractExtendInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();

		SecuritiesContractExtendInfo aInfo = new SecuritiesContractExtendInfo();

		SecuritiesContractPlanDao pDao = new SecuritiesContractPlanDao();

		try
		{
			aInfo = (SecuritiesContractExtendInfo) dao.findByID(lID, aInfo.getClass());

			//info没有的字段
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //业务单位名称

			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //交易对手名称

			aInfo.setCounterpartCode(NameRef.getCounterpartCodeByID(aInfo.getCounterpartId())); //交易对手编号

			aInfo.setInputUserName(NameRef.getUserNameCodeByID(aInfo.getInputUserId())); //录入人姓名

			aInfo.setNextCheckUserName(NameRef.getUserNameCodeByID(aInfo.getNextCheckUserId())); //下一级审核人姓名

			ApprovalBiz appbiz = new ApprovalBiz();
			
			//zpli mofify 2005-09-14
			long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3,aInfo.getOfficeId(),aInfo.getCurrencyId());
			//long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3);
			//zpli modify 2005-09-20
			//TODO:证券 待定
			//Sec_ExtendForm已经不存在，因此此函数作废,下句直接注释掉
			//aInfo.setLastCheckUserName(appbiz.getLastCheckPerson(approvalID, lID));

			aInfo.setUserCheckLevel(appbiz.findApprovalUserLevel(approvalID, aInfo.getNextCheckUserId()));

			//给合同余额赋初值，为了通知单测试，以后再改，by fanyang
			ContractBalanceInfo info = dao.getContractBalance(lID);
			aInfo.setBalance(info.getBalance());
			//aInfo.setReceivedAmount(info.getTotalReceivedAmount());
			//aInfo.setBuyBackAmount(info.getTotalPaiedAmount());
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
	 * 保存合同信息，新增或修改合同
	 * 
	 * @param info
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractExtendInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
		String contractCode = "";
		long ret = -1;
		try
		{
			if (info.getId() < 0)
			{
				dao.setUseMaxID();

				//展期序列号
				info.setSerialNo(Long.parseLong(dao.getSerialNo(info.getApplyContractId())));

				//原合同号
				contractCode = NameRef.getContractCodeByID(info.getApplyContractId());

				//展期合同号
				if (Long.parseLong(dao.getSerialNo(info.getApplyContractId())) < 10)
				{
					info.setCode(contractCode + "-0" + info.getSerialNo());
				}
				else
				{
					info.setCode(contractCode + "-" + info.getSerialNo());
				}

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
	 * 取消展期合同
	 * 
	 * @param lID
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
		SecuritiesContractExtendInfo aInfo = new SecuritiesContractExtendInfo();

		try
		{
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
	 * 查找符合条件的合同信息，用于修改查找和审核查找
	 * 
	 * @param qInfo
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		java.util.Collection c = null;
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
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
	 * 展期合同--审核
	 * 
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long[] lApprovalContentIDList;
		//模块类型
		long lModuleID = atInfo.getModuleID();
		//业务类型
		long lLoanTypeID = atInfo.getLoanTypeID();
		//操作类型
		long lActionID = atInfo.getActionID();
		long lApprovalContentID = atInfo.getApprovalContentID();
		long lNextUserID = atInfo.getNextUserID();
		long lApprovalID = atInfo.getApprovalID();
		long lUserID = atInfo.getInputUserID();
		
		//zpli add 2005-09-14
		long lOfficeID=atInfo.getOfficeID();
		long lCurrencyID=atInfo.getCurrencyID();
		////
		
		String sOpinion = atInfo.getOpinion();
		ApprovalTracingInfo info = new ApprovalTracingInfo();
		ApprovalBiz appbiz = new ApprovalBiz();
		lApprovalContentIDList = atInfo.getApprovalContentIDList();
		System.out.println("In EJB ******************** Log 1     lApprovalContentIDList = " + lApprovalContentIDList);
		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//获得ApprovalID
				if (lApprovalID < 0)
				{
					//zpli modify 2005-09-14
					lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID);
					//lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID);
				}
				System.out.println("In EJB ******************** Log 2     lApprovalID = " + lApprovalID);
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}
			//处理审批意见
			System.out.println("In EJB ******************** Log 3     处理审批意见 = " + atInfo.getCheckActionID());
			if (atInfo.getCheckActionID() == SECConstant.Actions.REJECT)    //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.CHECK)     //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//审批完成后需要做的操作
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.RETURN)    //返回修改
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
				//审核申请书
				System.out.println("In EJB ********************Log 4     开始审核展期合同 ");
				try
				{
					dao.check(atInfo);
				}
				catch (SecuritiesDAOException e2)
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				catch (Exception e2)
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
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
	 * 激活合同，支持批量激活
	 * 
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
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
	 * 转移合同管理人权限，支持批量转移
	 * 
	 * @param lID
	 * @param lUserID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void transferContractRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		try
		{
			Sec_ExtendFormDao cDao = new Sec_ExtendFormDao();
			SEC_ApplyDAO aDao = new SEC_ApplyDAO();
			long applyID = -1;
			for (int i = 0; i < lID.length; i++)
			{
				if (lID[i] > 0 && lUserID > 0)
				{
					//修改合同管理人信息
					SecuritiesContractExtendInfo info = new SecuritiesContractExtendInfo();
					info.setId(lID[i]);
					info.setInputUserId(lUserID);
					cDao.update(info);
					//修改申请管理人信息
					info = findByID(lID[i]);
					//applyID = info.getApplyId();
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
	 * 手动结束合同，支持批量结束
	 * 
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		Sec_ExtendFormDao dao = new Sec_ExtendFormDao();
		AttornmentApplyDao attDao = new AttornmentApplyDao();
		SecuritiesContractInfo info = null;
		for (int i = 0; i < lID.length; i++)
		{
			try
			{
				if (lID[i] <= 0)
					continue;
				info = (SecuritiesContractInfo) dao.findByID(lID[i], SecuritiesContractInfo.class);
				if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_REPURCHASE.CAPITAL_REPURCHASE)
				{
					attDao.endForRepurchase(info.getAccountId());
				}
				dao.updateStatus(lID[i], SECConstant.ContractStatus.FINISH);
				//更新库存
				dao.saveStockByContractId(lID[i]);
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
				throw new SecuritiesException("", e);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new java.rmi.RemoteException();
			}
		}
		/** TODO 手动结束合同是否要做何检测？结束合同是否影响其它模块？* */
	}
	/**
	 * 合同下的债券种类保存操作
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
	 * 合同下的债券种类查询操作
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
	 * 合同下的债券种类删除操作
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
			long[] lID = {641, 642};
			long lUserID = 7;
			SecuritiesContractExtendEJB ejb = new SecuritiesContractExtendEJB();
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