package com.iss.itreasury.loan.leasehold.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.aheadrepaynotice.dao.AheadRepayNoticeDao;
import com.iss.itreasury.loan.assuremanagementnotice.dao.AssureManagementNoticeDao;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.leasehold.dao.LoanAssureChargeFormDao;
import com.iss.itreasury.loan.leasehold.dao.LoanLeaseholdRepayFormDao;
import com.iss.itreasury.loan.leasehold.dao.Loan_AdjustPayConditionDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdInterestAdjustInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 *
 * <p>Title:融资租赁(收款/还款)通知单 </p>
 *
 * <p>Description: 信贷管理－融资租赁</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: iSoftStone</p>
 *
 * @author liuxz
 * @version 1.0
 */
public class LeaseholdNoticeEJB implements SessionBean {
	private static Log4j log4j = null;
    SessionContext sessionContext;

    LoanAssureChargeFormDao dao = new LoanAssureChargeFormDao();
    LoanLeaseholdRepayFormDao repaydao = new LoanLeaseholdRepayFormDao();
    Loan_AdjustPayConditionDao adjustDao = new Loan_AdjustPayConditionDao();
    
    public LeaseholdNoticeEJB()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
    public void ejbCreate() throws CreateException {
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    /**  融资租赁收款通知单 */
    
    /**added by xiong fei 2010-07-15
     * 判断该合同下之前是否有未付款项
     * @param contractId,officeID,currencyID
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long isAheadRepay(long contractId,long officeID,long currencyID) throws
    RemoteException, IRollbackException {
    	try {
    		return dao.isAheadRepay(contractId,officeID,currencyID);
    	} catch (ITreasuryDAOException ex) {
    		throw new IRollbackException(sessionContext, ex.getMessage(), ex);
    	}
    }
    
    /**
     * 融资租赁收款通知单的保存操作
     *
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public long savePayNotice(LeaseholdPayNoticeInfo paynoticeinfo) throws
            RemoteException, IRollbackException {
        try {
            return dao.savePayNotice(paynoticeinfo);
        } catch (LoanException ex) {
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁收款通知单的取消操作
     * @param paynoticeid long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void cancelPayNotice(long paynoticeid) throws RemoteException, IRollbackException {
        try {
            dao.cancelPayNotice(paynoticeid);
        } catch (LoanException ex) {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁收款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void checkPayNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException {
        try {
            dao.checkPayNotice(info);
        } catch (LoanException ex) {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁收款通知单的单笔查询操作
     * @param paynoticeid long
     * @return LeaseholdPayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid) throws
            RemoteException {
        try {
            return dao.findPayNoticeByID(paynoticeid);
        } catch (LoanException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    /**
     * 融资租赁收款通知单的多笔查询操作
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findPayNoticeByMultiOption(LeaseholdPayNoticeInfo
                                                 paynoticeinfo) throws
            RemoteException {
        try {
            return dao.findPayNoticeByMultiOption(paynoticeinfo);
        } catch (LoanException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    /**  融资租赁还款通知单 */

    /**
     * 融资租赁还款通知单的保存操作
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public long saveRepayNotice(LeaseholdRepayNoticeInfo repaynoticeInfo) throws
            RemoteException, IRollbackException {
        try {
            return repaydao.saveRepayNotice(repaynoticeInfo);
        } catch (LoanException ex) {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁还款通知单的取消操作
     * @param repaynoticeid long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void cancelRepayNotice(long repaynoticeid) throws RemoteException, IRollbackException {
        try {
            repaydao.cancelRepayNotice(repaynoticeid);
        } catch (LoanException ex) {
        	//modified by mzh_fu 2007/08/07
           // throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁还款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void checkRepayNotice(ApprovalTracingInfo info) throws
            RemoteException, IRollbackException {
        try {
            repaydao.checkRepayNotice(info);
        } catch (LoanDAOException ex) {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }

    /**
     * 融资租赁还款通知单的单笔查询操作
     * @param repaynoticeid long
     * @return LeaseholdRepayNoticeInfo
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid) throws
            RemoteException {
        try {
            return repaydao.findRepayNoticeByID(repaynoticeid);
        } catch (LoanException ex) {
            throw new RemoteException(ex.getMessage());
        }

    }

    /**
     * 融资租赁还款通知单的多笔查询操作
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findRepayNoticeByMultiOption(LeaseholdRepayNoticeInfo
            repaynoticeInfo) throws RemoteException {
        try {
            return repaydao.findRepayNoticeByMultiOption(repaynoticeInfo);
        } catch (LoanException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }
    
    /**
     * 融资租赁利率调整通知单的保存操作
     *
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public long saveAdjustNotice(LeaseholdInterestAdjustInfo noticeinfo) throws RemoteException, IRollbackException 
    {
        try 
        {
            return adjustDao.saveAdjustNotice(noticeinfo);
        } 
        catch (LoanException ex) 
        {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }
    
    /**
     * 融资租利率调整通知单的取消操作
     * @param noticeid long
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void cancelAdjustNotice(long noticeid) throws RemoteException, IRollbackException 
    {
        try 
        {
        	adjustDao.cancelAdjustNotice(noticeid);
        } 
        catch (LoanException ex) 
        {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }
    
    /**
     * 融资租赁利率调整通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     * @throws IRollbackException 
     */
    public void checkAdjustNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException 
    {
        try 
        {
        	adjustDao.checkAdjustNotice(info);
        } 
        catch (LoanException ex) 
        {
        	//modified by mzh_fu 2007/08/07
            //throw new RemoteException(ex.getMessage());
        	throw new IRollbackException(sessionContext, ex.getMessage(), ex);
        }
    }
    
    /**
     * 融资租赁利率调整通知单的单笔查询操作
     * @param noticeid long
     * @return LeaseholdInterestAdjustInfo
     * @throws RemoteException
     */
    public LeaseholdInterestAdjustInfo findAdjustNoticeByID(long noticeid) throws RemoteException 
    {
    	LeaseholdInterestAdjustInfo returnInfo = new LeaseholdInterestAdjustInfo();
    	try 
        {
    		return (LeaseholdInterestAdjustInfo)adjustDao.findByID(noticeid,returnInfo.getClass());
        } 
        catch (Exception ex) 
        {
            throw new RemoteException(ex.getMessage());
        }
    }
    
    /**
     * 融资租赁利率调整通知单的多笔查询操作
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findAdjustNoticeByMultiOption(LeaseholdInterestAdjustInfo noticeinfo) throws RemoteException 
    {
        try 
        {
            return adjustDao.findAdjustNoticeByMultiOption(noticeinfo);
        } 
        catch (LoanException ex) 
        {
            throw new RemoteException(ex.getMessage());
        }
    }
    //added by qhzhou 2007.07.09
	public long doApprovalLeaseholdPayNotice(LeaseholdPayNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			
			LoanAssureChargeFormDao LeaseholdPayNoticeDao = new LoanAssureChargeFormDao();
			LeaseholdPayNoticeInfo nInfo1 = new LeaseholdPayNoticeInfo();
			nInfo1 = findPayNoticeByID(nInfo.getId());
		
			
			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
		
			// 将业务记录置入nInfo1,转换成标准map传递到审批流引擎
			inutParameterInfo.setDataEntity(nInfo1);
		
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				long lll=nInfo.getId();
		
				lResult = LeaseholdPayNoticeDao.updateLeaseholdNoticeStatus(lll,LOANConstant.LoanPayNoticeStatus.CHECK);		
				//审批完成后需要做的操作
			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) {
				
				lResult = LeaseholdPayNoticeDao.updateLeaseholdNoticeStatus(nInfo.getId(),LOANConstant.LoanPayNoticeStatus.SUBMIT);
				
				// 被拒绝时应该清除的关联

			}
		
		
		
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IRollbackException(sessionContext, e.getMessage(), e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				//modified by mzh_fu 2007/08/07
				//throw new IException("Gen_E001");
				throw new IRollbackException(sessionContext, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	//added by qhzhou 2007.07.09
	public long doApprovalLeaseholdRePayNotice(LeaseholdRepayNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			
			LoanAssureChargeFormDao LeaseholdPayNoticeDao = new LoanAssureChargeFormDao();
			LeaseholdRepayNoticeInfo nInfo1 = new LeaseholdRepayNoticeInfo();
			nInfo1 = findRepayNoticeByID(nInfo.getId());
		
			
			InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
		
			// 将业务记录置入nInfo1,转换成标准map传递到审批流引擎
			inutParameterInfo.setDataEntity(nInfo1);
		
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				
				lResult = LeaseholdPayNoticeDao.updateLeaseholdRepayNoticeStatus(nInfo.getId(),LOANConstant.LoanPayNoticeStatus.CHECK);		
				//审批完成后需要做的操作
			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) {
				
				lResult = LeaseholdPayNoticeDao.updateLeaseholdRepayNoticeStatus(nInfo.getId(),LOANConstant.LoanPayNoticeStatus.SUBMIT);
				
				// 被拒绝时应该清除的关联

			}
		
		
		
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IRollbackException(sessionContext, e.getMessage(), e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				//modified by mzh_fu 2007/08/07
				//throw new IException("Gen_E001");
				throw new IRollbackException(sessionContext, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * 审批流：取消审批方法（融资租赁 . 收款通知单）
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(LeaseholdPayNoticeInfo nInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
		LoanAssureChargeFormDao LeaseholdPayNoticeDao = new LoanAssureChargeFormDao();
		
		try
		{
			//取消审批
			lReturn = LeaseholdPayNoticeDao.updateNoticeStatusAndCheckStatus(nInfo.getId(),LOANConstant.LoanPayNoticeStatus.SUBMIT);
			
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
			throw new IRollbackException(sessionContext, e.getMessage(), e);
		}
		return lReturn;
	}
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * 审批流：取消审批方法（融资租赁 . 收款通知单）
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(LeaseholdRepayNoticeInfo nInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = nInfo.getInutParameterInfo();
		LoanAssureChargeFormDao LeaseholdPayNoticeDao = new LoanAssureChargeFormDao();
		
		try
		{
			//取消审批
			lReturn = LeaseholdPayNoticeDao.updateRepayNoticeStatusAndCheckStatus(nInfo.getId(),LOANConstant.LoanPayNoticeStatus.SUBMIT);
			
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
			throw new IRollbackException(sessionContext, e.getMessage(), e);
		}
		return lReturn;
	}
	
    /**
     * 查询未收款金额(合同未做收款通知单金额)
     * @param lContractID long
     * @return unPayAmount
     * @throws RemoteException
     */
    public double findUnPayAmountByID(long lContractID,long payID) throws RemoteException 
    {
        try 
        {
            return dao.findUnPayAmountByID(lContractID,payID);
        } 
        catch (LoanException e)
        {
        	throw new RemoteException(e.getMessage());
        }
    }
    
    /**
	 * @param lContractID 合同ID
	 * @param rePayID 还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long lContractID,long rePayID) throws RemoteException, IException
	{
		double unRePayAmount = 0.0;
		try
		{
			unRePayAmount = repaydao.findUnRePayAmountByID(lContractID,rePayID);
		}
		catch (Exception e)
		{	
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return unRePayAmount;
	}
	
	/**
	 * @author yunchang
	 * @date 2010-05-25 
	 * @function 查找出记录担保收费通知单信息(LOAN_ASSURECHARGEFORM)中相同合同号的收取租金账户,为多笔收款通知单做校验,相同合同的收取租金账户、收保证金账户必须相同
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException
	{
		LeaseholdPayNoticeInfo leaseholdPayNoticeInfoTemp = new LeaseholdPayNoticeInfo();
		try
		{
			leaseholdPayNoticeInfoTemp = dao.getCollectionRentAccountID(leaseholdPayNoticeInfo);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return leaseholdPayNoticeInfoTemp;
	}
	
	/**
	 * @author yunchang
	 * @date 2010-06-01 19:24
	 * @function 查找出融资租赁新增(LOAN_LOANFORM)中的新增手续费,为多笔收款通知单做校验,多比手续费总和不能超过新增手续费
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException
	{
		LeaseholdPayNoticeInfo leaseholdPayNoticeInfoTemp = new LeaseholdPayNoticeInfo();
		try
		{
			leaseholdPayNoticeInfoTemp = dao.getMchargeAmount(leaseholdPayNoticeInfo);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return leaseholdPayNoticeInfoTemp;
	}	
	
	/**
	 * @author yunchang
	 * @date 2010-06-28 17:37
	 * @function 收款通知单保证金余额
	 * @param 合同ID
	 * @throws RemoteException
	 * @throws IException
	 */
	public double getSurplusRecognizanceAmount(long constractID) throws RemoteException,IException
	{
		double tempSurplusRecognizanceAmount = 0d;
		try
		{
			tempSurplusRecognizanceAmount = dao.getSurplusRecognizanceAmount(constractID);
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return tempSurplusRecognizanceAmount;
	}
}
