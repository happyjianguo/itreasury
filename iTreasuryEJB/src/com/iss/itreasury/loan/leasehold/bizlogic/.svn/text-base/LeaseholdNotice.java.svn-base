package com.iss.itreasury.loan.leasehold.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdInterestAdjustInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

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

public interface LeaseholdNotice extends EJBObject {

    /**  融资租赁收款通知单 */
    /**
     * 融资租赁收款通知单的保存操作
     *
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return long
     * @throws RemoteException
     */
    public long savePayNotice(LeaseholdPayNoticeInfo paynoticeinfo) throws
            RemoteException, IRollbackException;
    /**
     * 融资租赁收款通知单的取消操作
     * @param paynoticeid long
     * @throws RemoteException
     */
    public void cancelPayNotice(long paynoticeid) throws RemoteException, IRollbackException, IRollbackException;
    /**
     * 融资租赁收款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkPayNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException, IRollbackException;
    /**
     * 融资租赁收款通知单的单笔查询操作
     * @param paynoticeid long
     * @return LeaseholdPayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid) throws
            RemoteException;
    /**
     * 融资租赁收款通知单的多笔查询操作
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findPayNoticeByMultiOption(LeaseholdPayNoticeInfo
                                                 paynoticeinfo) throws
            RemoteException;

    /**  融资租赁还款通知单 */
    /**
     * 融资租赁还款通知单的保存操作
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return long
     * @throws RemoteException
     */
    public long saveRepayNotice(LeaseholdRepayNoticeInfo repaynoticeInfo) throws
            RemoteException, IRollbackException;
    
    /**added by xiong fei 2010-07-15
     * 判断该合同下之前是否有未还款项
     * @param contractId,officeID,currencyID
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long isAheadRepay(long contractId,long officeID,long currencyID) throws
    RemoteException, IRollbackException;
    
    /**
     * 融资租赁还款通知单的取消操作
     * @param repaynoticeid long
     * @throws RemoteException
     */
    public void cancelRepayNotice(long repaynoticeid) throws RemoteException, IRollbackException;
    /**
     * 融资租赁还款通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkRepayNotice(ApprovalTracingInfo info) throws
            RemoteException, IRollbackException;
    /**
     * 融资租赁还款通知单的单笔查询操作
     * @param repaynoticeid long
     * @return LeaseholdRepayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid) throws
            RemoteException;
    /**
     * 融资租赁还款通知单的多笔查询操作
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findRepayNoticeByMultiOption(LeaseholdRepayNoticeInfo
            repaynoticeInfo) throws RemoteException;
    
    /**
     * 融资租赁利率调整通知单的保存操作
     *
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return long
     * @throws RemoteException
     */
    public long saveAdjustNotice(LeaseholdInterestAdjustInfo noticeinfo) throws RemoteException, IRollbackException; 
    
    /**
     * 融资租利率调整通知单的取消操作
     * @param noticeid long
     * @throws RemoteException
     */
    public void cancelAdjustNotice(long noticeid) throws RemoteException, IRollbackException , IRollbackException;
    
    /**
     * 融资租赁利率调整通知单的审核操作
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkAdjustNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException;
    
    /**
     * 融资租赁利率调整通知单的单笔查询操作
     * @param noticeid long
     * @return LeaseholdInterestAdjustInfo
     * @throws RemoteException
     */
    public LeaseholdInterestAdjustInfo findAdjustNoticeByID(long noticeid) throws RemoteException;
    
    /**
     * 融资租赁利率调整通知单的多笔查询操作
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findAdjustNoticeByMultiOption(LeaseholdInterestAdjustInfo noticeinfo) throws RemoteException; 
    
	public long doApprovalLeaseholdPayNotice(LeaseholdPayNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException;

	public long doApprovalLeaseholdRePayNotice(LeaseholdRepayNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException;
	
	public long cancelApproval(LeaseholdPayNoticeInfo nInfo)throws RemoteException, IRollbackException;
	
	public long cancelApproval(LeaseholdRepayNoticeInfo nInfo)throws RemoteException, IRollbackException;
	
    /**
     * 查询未收款金额(合同未做收款通知单金额)
     * @param lContractID long
     * @return unPayAmount
     * @throws RemoteException
     */
    public double findUnPayAmountByID(long lContractID,long payID) throws RemoteException;
    
    /**
	 * @param lContractID 合同ID
	 * @param rePayID 还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long lContractID,long rePayID) throws RemoteException, IException;
	
	/**
	 * @author yunchang
	 * @date 2010-05-25 
	 * @function 查找出记录担保收费通知单信息(LOAN_ASSURECHARGEFORM)中相同合同号的收取租金账户,为多笔收款通知单做校验,相同合同的收取租金账户、收保证金账户必须相同
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException;

	/**
	 * @author yunchang
	 * @date 2010-06-01 19:24
	 * @function 查找出融资租赁新增(LOAN_LOANFORM)中的新增手续费,为多笔收款通知单做校验,多比手续费总和不能超过新增手续费
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException;

	/**
	 * @author yunchang
	 * @date 2010-06-28 17:37
	 * @function 收款通知单保证金余额
	 * @param 合同ID
	 * @throws RemoteException
	 * @throws IException
	 */
	public double getSurplusRecognizanceAmount(long constractID) throws RemoteException,IException;	
}
