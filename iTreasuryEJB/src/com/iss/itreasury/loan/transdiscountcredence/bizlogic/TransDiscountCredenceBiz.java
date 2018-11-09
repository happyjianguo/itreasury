/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcredence.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.SelectedTransDiscountBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountCredenceBiz implements SessionBean
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
	public TransDiscountCredenceBiz()
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
	 *凭证的保存操作
	*/
	public long save(TransDiscountCredenceInfo info) throws java.rmi.RemoteException, LoanException
	{
		long lReturn = -1;
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			/*更新凭证表*/
			if (info.getId() <= 0)
			{
				/*更新凭证表*/
				lReturn = dao.saveTransDiscountCredence(info);
				/*更新票据表*/
				//dao.saveTransDiscountCredenceBill(lReturn,info.getAllBillID());			
				/*更新票据凭证关系表*/
				dao.saveRCredenceAndBill(lReturn, info.getAllBillID());
			}
			else if (info.getId() > 0)
			{
				/*更新凭证表*/
				lReturn = dao.updateTransDiscountCredence(info);
			}
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
		return lReturn;
	}

	/**
	 *凭证的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());
	}

	/**
	 *凭证的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		long lCount = 0;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		long lApprovalID = -1;
		long[] lApprovalContentIDList;

		//定义相应操作常量
		//模块类型
		long lModuleID = LOANConstant.ModuleType.CRAFTBROTHER;
		info.setModuleID(lModuleID);
		//业务类型
		//long lLoanTypeID = LOANConstant.ApprovalLoanType.ZTX;
		long lLoanTypeID = info.getLoanTypeID();
		info.setLoanTypeID(lLoanTypeID);
		//操作类型
		long lActionID = info.getActionID();
		info.setActionID(lActionID);
		lApprovalID=info.getApprovalID();
		ApprovalDelegation appbiz = new ApprovalDelegation();

		lApprovalContentIDList = info.getApprovalContentIDList();

		if (lApprovalContentIDList.length > 0)
		{
			try
			{
				//获得ApprovalID
//				lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID, lActionID,info.getOfficeID(),info.getCurrencyID());
				info.setApprovalID(lApprovalID);
			}
			catch (Exception e1)
			{
				log4j.error("getApprovalID fail");
				e1.printStackTrace();
			}

			//处理审批意见
			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
			{
				//审批意见状态
				lStatusID = Constant.RecordStatus.VALID;
				//审批操作类型
				lResultID = Constant.ApprovalDecision.REFUSE;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.PASS;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
			{
				lStatusID = Constant.RecordStatus.VALID;
				lResultID = Constant.ApprovalDecision.FINISH;
				//审批完成后需要做的操作
			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
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
				//审核申请书
				dao.checkCredence(info);

				//log4j.debug("saveApprovalTracing begin");
				try
				{
					appbiz.saveApprovalTracing(info);
				}
				catch (Exception e)
				{
					log4j.error("saveApprovalTracing fail");
					e.printStackTrace();
				}
				//log4j.debug("saveApprovalTracing end");
			}
		}
	}

	/**
		 * lazhang add method
		 * @param  ApprovalTracingInfo info
		 * @throws java.rmi.RemoteException
		 * @throws LoanException
		 */
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceDAO dao = null;
		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		long lStatusID = -1; //审核记录状态：有效、无效
		long lResultID = -1; //ApprovalDecision 审核决策
		long[] lApprovalContentIDList; //批量审核内容标识数组
		long lCount = 0;

		if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
		{
			lStatusID = Constant.RecordStatus.INVALID;
			lResultID = Constant.ApprovalDecision.REFUSE;
		}
		if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.PASS;
		}
		if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.FINISH;
		}
		if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
		{
			lStatusID = Constant.RecordStatus.VALID;
			lResultID = Constant.ApprovalDecision.RETURN;
		}

		//info设置
		info.setResultID(lResultID);
		info.setStatusID(lStatusID);

		lApprovalContentIDList = info.getApprovalContentIDList();
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
			//审核凭证
			dao.cpfCheckCredence(info);
			log4j.debug("saveReviewOpinion begin");
			try
			{
				dao.addReviewOpinion(info);
			}
			catch (Exception e)
			{
				log4j.error("saveReviewOpinion fail");
				e.printStackTrace();
			}
			log4j.debug("saveReviewOpinion end");
		}
	}

	/**
	 *凭证的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			dao.cancelCredenceByID(lID);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
	}

	/**
	 *凭证的单笔查询操作
	*/
	public TransDiscountCredenceInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceInfo returnInfo = new TransDiscountCredenceInfo();
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			returnInfo = dao.findCredenceInfoByID(lID);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return returnInfo;
	}

	/**
	 *凭证的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountCredenceQueryInfo qInfo,long lActoinID) throws java.rmi.RemoteException, LoanException
	{
		Collection c_Return = null;
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{	
			if(qInfo.getActionID()==1)
				c_Return = dao.findCredenceByMultiOption(qInfo);
			else if(qInfo.getActionID()==2)
				c_Return = dao.findCredenceByMultiOption1(qInfo,lActoinID);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return c_Return;
	}
	
	/**
	 *中油凭证的多笔查询操作
	*/
		public Collection cpfFindByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
		{
			Collection c_Return = null;
			TransDiscountCredenceDAO dao = null;

			dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

			try
			{
				c_Return = dao.cpfFindCredenceByMultiOption(qInfo);
			}
			catch (Exception e)
			{
				throw new LoanException("Gen_E001", e);
			}

			return c_Return;
		}

	/**
	 *凭证下的票据查询操作
	*/
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID) throws java.rmi.RemoteException, LoanException
	{
		Collection c_Return = null;
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			c_Return = dao.findDraftForAmortization(lTransDiscountCredenceID);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return c_Return;
	}

	/**
	 *票据查询操作
	*/
	public Collection findTransDiscountCredenceBill(long lContractID, long lCredenceID, long lBillSourceTypeID) throws java.rmi.RemoteException, LoanException
	{
		Collection c_Return = null;
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			Log.print("----------------------begin----------------");
			c_Return = dao.findTransDiscountCredenceBill(lContractID, lCredenceID, lBillSourceTypeID);
			Log.print("----------------------end----------------");
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return c_Return;
	}

	/**
	 *票据保存修改操作
	*/
	public void saveTransDiscountCredenceBill(long lTransDiscountCredenceID, long[] lIDList) throws java.rmi.RemoteException, LoanException
	{
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			/*更新票据表*/
			dao.saveTransDiscountCredenceBill(lTransDiscountCredenceID, lIDList);
			/*更新票据凭证表*/
			dao.saveRCredenceAndBill(lTransDiscountCredenceID, lIDList);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
	}

	/**
	 * 选定的票据信息，操作Loan_DiscountContractBill表
	 * @param SelectedTransDiscountBillInfo 选定的票据信息
	 * @return SelectedTransDiscountBillInfo
	 */
	public SelectedTransDiscountBillInfo findBillInterestByBillID(SelectedTransDiscountBillInfo info) throws java.rmi.RemoteException, LoanException
	{
		SelectedTransDiscountBillInfo returnInfo = new SelectedTransDiscountBillInfo();
		TransDiscountCredenceDAO dao = null;

		dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());

		try
		{
			returnInfo = dao.findBillInterestByBillID(info);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return returnInfo;
	}

	/**
	 * 根据不同项目获取凭证表表名
	 * @return strTableName
	 * @author yanliu
	 * */
	private String getTransDiscountCredenceTableName()
	{
		String strTableName = "";

		/*  TOCONFIG—TODELETE  */
		/*
		 * 产品化不再区分项目 
		 * ninh 
		 * 2005-03-24
		 */

//		if (Env.getProjectName().equals(Constant.ProjectName.CNMEF))
//		{
//			strTableName = "LOAN_DISCOUNTCREDENCE";
//		}
//		else if (Env.getProjectName().equals(Constant.ProjectName.CPF)) //getClientName
//		{
//			strTableName = "DISCOUNTCREDENCE";
//		}
//		else
//		{
//			strTableName = "LOAN_DISCOUNTCREDENCE";
//		}

		strTableName = "LOAN_DISCOUNTCREDENCE";

		/*  TOCONFIG—END  */

		return strTableName;
	}
	
 	/**
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalInit(TransDiscountCredenceInfo info)
			throws RemoteException, IRollbackException {
		String strContractCode = "";
		long lReturnId = -1;
		try {
			
			lReturnId = save(info);
			TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(lReturnId));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturnId);
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			lReturnId=dao.update(lReturnId, info.getInputUserID(),LOANConstant.LoanStatus.APPROVALING);
			
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
    public long examinePass(TransDiscountCredenceInfo info)
	throws RemoteException, IRollbackException{
   	 long lReturnId = -1;
		 long credenceID=info.getId();
		 long lUserID=-1;
   			try
   			{
   				TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());
   				//TransDiscountApplyInfo	appInfo=dao.findByID(info.getId());
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
   							credenceID,
   							lUserID,
   							LOANConstant.LoanStatus.CHECK);
				
   					
   					//将贷款申请信息复制到合同表				  		
   					//dao.doAfterApprovalOver(credenceID);
   				}
   				//如果是最后一级,且为审批拒绝,更新状态为已保存
   				else if(returnInfo.isRefuse())
   				{
   					dao.update(
   							credenceID,
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
     * added by xwhe 2007/09/14
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long updateApplyAndApprovalInit(TransDiscountCredenceInfo info)
			throws RemoteException, IRollbackException {
		long lReturnId = -1;
		try {
	
			TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			inutParameterInfo.setTransID(String.valueOf(info.getId()));
			inutParameterInfo.setUrl(inutParameterInfo.getUrl()+info.getId());
			// 提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			
			// 更新状态到"审批中"
			dao.update(info.getId(), info.getInputUserID(),LOANConstant.LoanStatus.APPROVALING);
			
		} catch (Exception e) {
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
	public long cancelApproval(TransDiscountCredenceInfo info)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.getTransDiscountCredenceTableName());
		
		try
		{
			//取消审批
			lReturn = dao.update(info.getId(), info.getInputUserID(), LOANConstant.LoanStatus.SAVE);
			
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
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}

}
