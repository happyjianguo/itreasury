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
package com.iss.itreasury.loan.transdiscountcontract.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransDiscountContract extends javax.ejb.EJBObject
{
	/**
	 *��ͬ�ı������
	*/
	public long save(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ��ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ����˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ��ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ�ĵ��ʲ�ѯ����
	*/
	public TransDiscountContractInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ�Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *��ͬ�µ�Ʊ�ݲ�ѯ����
	*/
	public Collection findBillByTransDiscountContractID(long lTransDiscountContractID) throws java.rmi.RemoteException, LoanException;
  
    /**
     *�������µ�Ʊ�ݱ������
    */
    public long saveBill(TransDiscountContractBillInfo info) throws java.rmi.RemoteException, LoanException;

    /**
     * ��������ύ����
     */
    public void submit(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException;
	
    /**
     * ���ͺ�ͬ���˲���
     * @param ATInfo
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public void cpfCheck(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, LoanException;

    /**
     * ���͵Ķ�������ѯ����
     * @param qInfo
     * @return
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public Collection cpfFindByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
    
    /**
     * ��������������
     * @param qInfo
     * @return
     * @throws java.rmi.RemoteException
     * @throws IRollbackException
     */
    
	public long submitApplyAndApprovalInit(TransDiscountContractInfo info)
	throws RemoteException, IRollbackException;
	

	public long examinePass(TransDiscountContractInfo info)
	throws RemoteException, IRollbackException;
}