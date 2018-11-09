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

import java.util.Collection;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.SelectedTransDiscountBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransDiscountCredence extends javax.ejb.EJBObject
{
	/**
	 *ƾ֤�ı������
	*/
	public long save(TransDiscountCredenceInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *ƾ֤��ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *ƾ֤����˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *����ƾ֤����˲���
	*/
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *ƾ֤��ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *ƾ֤�ĵ��ʲ�ѯ����
	*/
	public TransDiscountCredenceInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *ƾ֤�Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *����ƾ֤�Ķ�ʲ�ѯ����
	*/
	public Collection cpfFindByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *ƾ֤�µ�Ʊ�ݲ�ѯ����
	*/
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID) throws java.rmi.RemoteException, LoanException;

	/**
	 *Ʊ�ݲ�ѯ����
	*/
	public Collection findTransDiscountCredenceBill(long lContractID, long lCredenceID, long lBillSourceTypeID) throws java.rmi.RemoteException, LoanException;

	/**
	 *Ʊ��ѡ�����
	*/
	public void saveTransDiscountCredenceBill(long lTransDiscountCredenceID,long[] lIDList) throws java.rmi.RemoteException, LoanException;

	/**
	 * ѡ����Ʊ����Ϣ������Loan_DiscountContractBill��
	 * @param SelectedTransDiscountBillInfo ѡ����Ʊ����Ϣ
	 * @return SelectedTransDiscountBillInfo
	 */
	public SelectedTransDiscountBillInfo findBillInterestByBillID(SelectedTransDiscountBillInfo info) throws java.rmi.RemoteException, LoanException;
	
}