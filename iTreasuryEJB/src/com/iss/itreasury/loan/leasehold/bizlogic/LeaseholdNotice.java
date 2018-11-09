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
 * <p>Title:��������(�տ�/����)֪ͨ�� </p>
 *
 * <p>Description: �Ŵ�������������</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: iSoftStone</p>
 *
 * @author liuxz
 * @version 1.0
 */

public interface LeaseholdNotice extends EJBObject {

    /**  ���������տ�֪ͨ�� */
    /**
     * ���������տ�֪ͨ���ı������
     *
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return long
     * @throws RemoteException
     */
    public long savePayNotice(LeaseholdPayNoticeInfo paynoticeinfo) throws
            RemoteException, IRollbackException;
    /**
     * ���������տ�֪ͨ����ȡ������
     * @param paynoticeid long
     * @throws RemoteException
     */
    public void cancelPayNotice(long paynoticeid) throws RemoteException, IRollbackException, IRollbackException;
    /**
     * ���������տ�֪ͨ������˲���
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkPayNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException, IRollbackException;
    /**
     * ���������տ�֪ͨ���ĵ��ʲ�ѯ����
     * @param paynoticeid long
     * @return LeaseholdPayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid) throws
            RemoteException;
    /**
     * ���������տ�֪ͨ���Ķ�ʲ�ѯ����
     * @param paynoticeinfo LeaseholdPayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findPayNoticeByMultiOption(LeaseholdPayNoticeInfo
                                                 paynoticeinfo) throws
            RemoteException;

    /**  �������޻���֪ͨ�� */
    /**
     * �������޻���֪ͨ���ı������
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return long
     * @throws RemoteException
     */
    public long saveRepayNotice(LeaseholdRepayNoticeInfo repaynoticeInfo) throws
            RemoteException, IRollbackException;
    
    /**added by xiong fei 2010-07-15
     * �жϸú�ͬ��֮ǰ�Ƿ���δ������
     * @param contractId,officeID,currencyID
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long isAheadRepay(long contractId,long officeID,long currencyID) throws
    RemoteException, IRollbackException;
    
    /**
     * �������޻���֪ͨ����ȡ������
     * @param repaynoticeid long
     * @throws RemoteException
     */
    public void cancelRepayNotice(long repaynoticeid) throws RemoteException, IRollbackException;
    /**
     * �������޻���֪ͨ������˲���
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkRepayNotice(ApprovalTracingInfo info) throws
            RemoteException, IRollbackException;
    /**
     * �������޻���֪ͨ���ĵ��ʲ�ѯ����
     * @param repaynoticeid long
     * @return LeaseholdRepayNoticeInfo
     * @throws RemoteException
     */
    public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid) throws
            RemoteException;
    /**
     * �������޻���֪ͨ���Ķ�ʲ�ѯ����
     * @param repaynoticeInfo LeaseholdRepayNoticeInfo
     * @return Collection
     * @throws RemoteException
     */
    public Collection findRepayNoticeByMultiOption(LeaseholdRepayNoticeInfo
            repaynoticeInfo) throws RemoteException;
    
    /**
     * �����������ʵ���֪ͨ���ı������
     *
     * @param noticeinfo LeaseholdInterestAdjustInfo
     * @return long
     * @throws RemoteException
     */
    public long saveAdjustNotice(LeaseholdInterestAdjustInfo noticeinfo) throws RemoteException, IRollbackException; 
    
    /**
     * ���������ʵ���֪ͨ����ȡ������
     * @param noticeid long
     * @throws RemoteException
     */
    public void cancelAdjustNotice(long noticeid) throws RemoteException, IRollbackException , IRollbackException;
    
    /**
     * �����������ʵ���֪ͨ������˲���
     * @param info ApprovalTracingInfo
     * @throws RemoteException
     */
    public void checkAdjustNotice(ApprovalTracingInfo info) throws RemoteException, IRollbackException;
    
    /**
     * �����������ʵ���֪ͨ���ĵ��ʲ�ѯ����
     * @param noticeid long
     * @return LeaseholdInterestAdjustInfo
     * @throws RemoteException
     */
    public LeaseholdInterestAdjustInfo findAdjustNoticeByID(long noticeid) throws RemoteException;
    
    /**
     * �����������ʵ���֪ͨ���Ķ�ʲ�ѯ����
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
     * ��ѯδ�տ���(��ͬδ���տ�֪ͨ�����)
     * @param lContractID long
     * @return unPayAmount
     * @throws RemoteException
     */
    public double findUnPayAmountByID(long lContractID,long payID) throws RemoteException;
    
    /**
	 * @param lContractID ��ͬID
	 * @param rePayID ����֪ͨ��ID
	 * @return ��ѯδ������
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long lContractID,long rePayID) throws RemoteException, IException;
	
	/**
	 * @author yunchang
	 * @date 2010-05-25 
	 * @function ���ҳ���¼�����շ�֪ͨ����Ϣ(LOAN_ASSURECHARGEFORM)����ͬ��ͬ�ŵ���ȡ����˻�,Ϊ����տ�֪ͨ����У��,��ͬ��ͬ����ȡ����˻����ձ�֤���˻�������ͬ
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException;

	/**
	 * @author yunchang
	 * @date 2010-06-01 19:24
	 * @function ���ҳ�������������(LOAN_LOANFORM)�е�����������,Ϊ����տ�֪ͨ����У��,����������ܺͲ��ܳ�������������
	 * @param LeaseholdPayNoticeInfo
	 * @throws RemoteException,IException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws RemoteException,IException;

	/**
	 * @author yunchang
	 * @date 2010-06-28 17:37
	 * @function �տ�֪ͨ����֤�����
	 * @param ��ͬID
	 * @throws RemoteException
	 * @throws IException
	 */
	public double getSurplusRecognizanceAmount(long constractID) throws RemoteException,IException;	
}
