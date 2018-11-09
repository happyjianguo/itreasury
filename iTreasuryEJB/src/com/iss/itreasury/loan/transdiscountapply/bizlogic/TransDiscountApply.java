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
package com.iss.itreasury.loan.transdiscountapply.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.discount.dataentity.DiscountLoanInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanExaminePassInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransDiscountApply extends javax.ejb.EJBObject
{
	/**
	 *������ı������
	*/
	public long save(TransDiscountApplyInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *�������ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *���������˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *�������������˲���
	 */
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *�������ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *������ĵ��ʲ�ѯ����
	*/
	public TransDiscountApplyInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *������Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *�������µ�Ʊ�ݱ������
	*/
	public long saveBill(TransDiscountApplyBillInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *�������µ�Ʊ�ݲ�ѯ����
	*/
	public Collection findBillByTransDiscountApplyID(long lTransDiscountApplyID,long lInOrOut) throws java.rmi.RemoteException, LoanException;

	/**
	 *�������µ�Ʊ��ɾ������
	*/
	public void deleteBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *Ʊ�ݲ�ѯ����
	*/
	public Collection findTransDiscountApplyBill(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *Ʊ��ѡ�����
	*/
	public void saveTransDiscountApplyBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException;

    /**
     * ����ת���������µ�����Ʊ����Ϣ��
     * ����Loan_DiscountFormBill��
     * @param lDiscountApplyID ���ֱ�ʶ
     * @param  dRate        ��������
     * @param  tsDate       ������
     * @return ��������Ʊ�ݵ��б�
     */
     public Collection calculateTransDiscountBillInterest(TransDiscountApplyQueryInfo qInfo)
     throws java.rmi.RemoteException, LoanException;

    /**
     *������ĵ��ʲ�ѯ����
    */
    public TransDiscountApplyBillInfo findBillByID(long lID) throws java.rmi.RemoteException, LoanException;
    
    /**
     * ���͵��������������ѯ����
     * @author zntan
     *
     * TODO To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Style - Code Templates
     */
    public Collection cpfFindByMultiOption(TransDiscountApplyQueryInfo qInfo)
	throws java.rmi.RemoteException, LoanException;
    
	public long submitApplyAndApprovalInit(TransDiscountApplyInfo info)
	throws RemoteException, IRollbackException;
	

	public long examinePass(TransDiscountApplyInfo info)
	throws RemoteException, IRollbackException;
	
	public long submitApplyAndApprovalBillInit(TransDiscountApplyInfo info)
		throws RemoteException, IRollbackException;
	
    public long doApprovalApplyBillInit(TransDiscountApplyInfo nInfo) 
    	throws RemoteException,IRollbackException,IException , BillException;
    
    public long doCancleApprovalApplyBillInit(TransDiscountApplyInfo nInfo) 
    	throws  RemoteException,IRollbackException,IException;
    
}