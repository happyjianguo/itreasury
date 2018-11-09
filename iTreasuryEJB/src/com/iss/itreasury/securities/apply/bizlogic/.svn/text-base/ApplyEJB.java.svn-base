package com.iss.itreasury.securities.apply.bizlogic;

import javax.ejb.SessionBean;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.system.approval.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.securities.deliveryorderservice.bizlogic.*;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.*;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApplyEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
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
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	/**
	 *申请书的保存操作
	*/
	public long save(ApplyInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		DeliveryOrderServiceOperation dso = new DeliveryOrderServiceOperation();
		UsableCreditLineOfSecuritiesInfo creditInfo1 = new UsableCreditLineOfSecuritiesInfo();
		UsableCreditLineOfSecuritiesInfo creditInfo2 = new UsableCreditLineOfSecuritiesInfo();
		long lID = -1;
		String strError = "";

		if (info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN || info.getTransactionTypeId() == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
		{
			//资金拆借额度检查
			log4j.print("资金拆借额度检查");
			strError = dao.checkCapitalLandingCreditExtension(info.getCounterpartId(), info.getTransactionTypeId(), info.getPledgeSecuritiesAmount(), info.getId());
			if (strError != null && strError.length() > 0)
			{
				throw new SecuritiesException("Sec_E180", strError, null);
			}
		}

		if (info.getId() < 0)
		{
			try
			{
				info.setCode(dao.getApplyCode(info.getTransactionTypeId()));
				//将下一个审批级别设为1
				info.setNextCheckLevel(1);
				lID = dao.add(info);
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
		if(info.getInutParameterInfo()!=null)
		{
			log4j.debug("------提交审批--------");
			//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
			InutParameterInfo tempInfo = info.getInutParameterInfo();
			tempInfo.setUrl(tempInfo.getUrl()+lID);
			String transNo="";
			try{
				transNo=UtilOperation.getNewTransactionNo(tempInfo,lID);
			}catch (IException e) {
				throw new RemoteException();
			}
			tempInfo.setTransID(transNo);
			tempInfo.setDataEntity(info.getInutParameterInfo());
			
			try{
				//提交审批
				FSWorkflowManager.initApproval(tempInfo);
				//更新状态到审批中
				info.setId(lID);
				info.setStatusId(SECConstant.ApplyFormStatus.APPROVALING);	//需新增业务状态		
				dao.updateStatusID(info);
				log4j.debug("------提交审批成功--------");
			}catch (Exception e) {
				// TODO: handle exception
				throw new RemoteException();
			}
		}
				
		return lID;
	}
	/**
	 *申请书的审批操作
	*/
	public long doApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException{
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getId();
		try
		{
			ApplyInfo loanInfo = new ApplyInfo();
			loanInfo=findByID(info.getId());
			inutParameterInfo.setDataEntity(loanInfo);
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				info.setStatusId(SECConstant.ApplyFormStatus.CHECKED);
				dao.updateStatusID(info);
				//如果是自动复核
				/*if(FSWorkflowManager.isAutoCheck())
				{
//					approvalInfo.setCheckActionID(SECConstant.Actions.CHECKOVER);			
//					check(approvalInfo);
					info.setStatusId(SECConstant.ApplyFormStatus.CHECKED);
					dao.updateStatusID(info);				
				}*/	
			}
			//	如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				dao.updateStatusID(info);				
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
	 *申请书的取消审批操作
	*/
	public long cancelApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException{
		long lReturn = -1;
		SEC_ApplyDAO loanDao = new SEC_ApplyDAO();		
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();	
		boolean ifUsed=false;
		try{
			ifUsed=loanDao.checkUsed(info.getId());
		}catch (SecuritiesDAOException e) {
			throw new SecuritiesException();
		}
		if(ifUsed){
			throw new SecuritiesException("该申请已被使用，不能取消审批",null);
		}
		try
		{
			info=(ApplyInfo)loanDao.findByID(info.getId(),info.getClass());
			if(info.getStatusId()==SECConstant.ApplyFormStatus.CHECKED)
			{
				//取消审批
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				lReturn = loanDao.updateStatusID(info);
			}			
			/*
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && info.getStatusId()==SECConstant.ApplyFormStatus.CHECKED)
			{
				//取消复核
//				approvalInfo.setStatusID(SECConstant.Actions.RETURN);
//				this.check(approvalInfo);				
				//取消审批
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				lReturn = loanDao.updateStatusID(info);
			}
			else if( !FSWorkflowManager.isAutoCheck() && info.getStatusId()==SECConstant.ApplyFormStatus.APPROVALED)
			{
				//取消审批
				info.setStatusId(SECConstant.ApplyFormStatus.SUBMITED);
				lReturn = loanDao.updateStatusID(info);
			}
			*/
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new SecuritiesException();
		}
		return lReturn;		
	}

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		boolean ifUsed=false;
		try{
			ifUsed=dao.checkUsed(lID);
		}catch (SecuritiesDAOException e) {
			throw new SecuritiesException();
		}
		if(ifUsed){
			throw new SecuritiesException("该申请已被使用，不能删除",null);
		}		
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
	 *申请书的审核操作
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

		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
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
				//获得ApprovalID
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

			//处理审批意见
			if (ATInfo.getCheckActionID() == SECConstant.Actions.REJECT) //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECK) //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//审批完成后需要做的操作
			}
			if (ATInfo.getCheckActionID() == SECConstant.Actions.RETURN) //修改
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
				//审核申请书
				try {
					dao.check(ATInfo);
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new SecuritiesException("审核申请书错误",e1);
				}

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
	 *申请书的取消操作
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
	 *申请书的单笔查询操作
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_ApplyDAO dao = new SEC_ApplyDAO();
		ApplyInfo aInfo = new ApplyInfo();

		try
		{
			aInfo = (ApplyInfo) dao.findByID(lID, aInfo.getClass());
			//SEC_ApplyForm表中没有的字段
			aInfo.setClientName(NameRef.getClientNameByID(aInfo.getClientId())); //业务单位名称
			aInfo.setCounterpartName(NameRef.getCounterpartNameByID(aInfo.getCounterpartId())); //交易对手名称
			aInfo.setSecuritiesName(NameRef.getSecuritiesNameByID(aInfo.getSecuritiesId())); //证券名称
			aInfo.setAccountNo(NameRef.getAccountNobyAccountID(aInfo.getAccountId())); //资金账户代码
			aInfo.setAccountName(NameRef.getAccountNameById(aInfo.getAccountId())); //资金账户名称
			aInfo.setStockHolderAccountCode(NameRef.getStockHolderAccountCodeByAccountId(aInfo.getAccountId())); //股东账户代码
			aInfo.setStockName(NameRef.getSecuritiesNameByID(aInfo.getStockId())); //转成股票名称
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
	 *申请书的多笔查询操作
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
				//获得ApprovalID
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
				//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
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
	 *申请书下的投标区间保存操作
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
	 *申请书下的投标区间查询操作
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
	 *申请书下的投标区间删除操作
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
	 *申请书下的债券种类保存操作
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
	 *申请书下的债券种类查询操作
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
	 *申请书下的债券种类删除操作
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
	* 转移申请书管理人权限，支持批量转移
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

}
