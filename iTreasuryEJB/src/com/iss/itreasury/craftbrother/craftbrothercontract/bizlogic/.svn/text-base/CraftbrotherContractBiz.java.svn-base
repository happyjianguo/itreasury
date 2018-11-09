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
	 * 查找单条合同信息
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
			//info没有的字段
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //业务单位名称
			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //交易对手名称
			aInfo.setCounterpartCode(NameRef.getCounterpartCodeByID(aInfo.getCounterpartId())); //交易对手编号
			aInfo.setInputUserName(NameRef.getUserNameCodeByID(aInfo.getInputUserId())); //录入人姓名
			aInfo.setNextCheckUserName(NameRef.getUserNameCodeByID(aInfo.getNextCheckUserId())); //下一级审核人姓名

			ApprovalBiz appbiz = new ApprovalBiz();
			//zpli modify 2005-09-14
			long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3,aInfo.getOfficeId(),aInfo.getCurrencyId());
			//long approvalID = NameRef.getApprovalIDByTransactionTypeID(aInfo.getTransactionTypeId(), 3);
			
			//zpli modify 2005-09-16 lModuleID,lLoanTypeID,lActionID, lOfficeID, lCurrencyID, lApprovalContentID
			//aInfo.setLastCheckUserName(appbiz.getLastCheckPerson(approvalID, lID));
			aInfo.setLastCheckUserName(appbiz.getLastCheckPerson(Constant.ModuleType.SECURITIES,SECUtil.getBusinessIDByTransactionID(aInfo.getTransactionTypeId()) ,aInfo.getTransactionTypeId(),aInfo.getOfficeId(),aInfo.getCurrencyId(),lID));
			
			
			aInfo.setUserCheckLevel(appbiz.findApprovalUserLevel(approvalID, aInfo.getNextCheckUserId()));

			
			
			//给合同余额赋初值，为了通知单测试，以后再改，by fanyang
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
	 * 保存合同信息，新增或修改合同
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
						//转让日期与回购日期之间间隔天数
						interestDays = DataFormat.getIntervalDays(info
								.getTransactionStartDate(), info.getTransactionEndDate());
						//转让日期到回购日期之间的利息
						interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(
								UtilOperation.Arith
										.mul(info.getPrice(), info.getIncomeRate()),
								interestDays), 36000);
					}
					info.setTransferBalance(info.getPrice());  //转让款项余额
					info.setAvailableTransferBalance(info.getPrice());  //转让款项可用余额
					info.setRepurchaseBalance(info.getPrice());  //待回购余额
					info.setAvailableRepurchaseBalance(info.getPrice());  //待回购可用余额
					info.setInterestBalance(interest);//转让日期到回购日期之间的利息
					info.setReceivedInterest(0);  //已收利息（已收款）
					info.setWaitReceivedInterest(0);  //已收利息（待收款）
					info.setPaidInterest(0);  //已付利息（已支付）
					info.setWaitPaidInterest(0);  //已付利息（待支付）
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
	 * 取消合同，同时取消申请书
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
	 * 查找符合条件的合同信息，用于修改查找和审核查找
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
	 * 审批合同
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
				//获得ApprovalID
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

			//处理审批意见
			if (atInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (atInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
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
			if (atInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
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
	 * 激活合同，支持批量激活
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
	 * 激活合同，支持批量激活
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
		 * 转移合同管理人权限，支持批量转移
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
					//修改合同管理人信息
					SecuritiesContractInfo info = new SecuritiesContractInfo();
					info.setId(lID[i]);
					info.setInputUserId(lUserID);
					cDao.update(info);
					
					//修改申请管理人信息
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
	 * 手动结束合同，支持批量结束
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
				e.printStackTrace() ;
				throw new java.rmi.RemoteException();
			}
		}
		/**TODO 手动结束合同是否要做何检测？结束合同是否影响其它模块？**/
	}

	/**
	 *合同下的债券种类保存操作
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
	 *合同下的债券种类查询操作
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
	 *合同下的债券种类删除操作
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
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
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
   				

   				//---- added by xwhe 2007/09/12 审批流 begin
   				
   				InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
   				InutParameterInfo returnInfo = new InutParameterInfo();
   				
   				//将业务记录置入pinfo,转换成标准map传递到审批流引擎
   				inutParameterInfo.setDataEntity(info);
   				
   				//提交审批
   				returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
   				
   				//如果是最后一级,且为审批通过,更新状态为已审批
   				if(returnInfo.isLastLevel())
   				{	
   					dao.update(
   							lTransDiscountContractID,
   							lUserID,
   							SECConstant.ContractStatus.CHECK);
   					
   					//审核完成以后，把该合同下的买入的票据置为可卖状态				  		
   					//dao.doAfterCheckOver(lTransDiscountContractID);
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
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
	 * 审批流：取消审批方法
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
			throw new SecuritiesException("请先取消通知单,再取消合同!",null);
		}
		try
		{
			dao.cancelNotice(info.getId());
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserId(), SECConstant.ContractStatus.SAVE);
			
			if(lReturn > 0){
				//将审批记录表内的该交易的审批记录状态置为无效
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
	 * 更新资产转让合同转让款项余额
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
	 * 更新资产转让合同转让款项可用余额
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
	 * 更新资产转让合同待回购余额
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
	 * 更新资产转让合同待回购可用余额
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
	 * 更新资产转让合同已收利息（已收款）
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
	 * 更新资产转让合同已收利息（待收款）
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
	 * 更新资产转让合同已付利息（已支付）
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
	 * 更新资产转让合同已付利息（待支付）
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
	 * 根据资产转让通知单的类型，保存或保存并提交通知单时更新资产转让合同的各种余额信息
	 * （转让款项余额、转让款项可用余额、待购回余额、待购回可用余额、已收利息（已收款）、已收利息（待收款）、已付利息（已支付）、已付利息（待支付））
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
	 * 根据资产转让通知单的类型，取消通知单时更新资产转让合同的各种余额信息
	 * （转让款项余额、转让款项可用余额、待购回余额、待购回可用余额、已收利息（已收款）、已收利息（待收款）、已付利息（已支付）、已付利息（待支付））
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
