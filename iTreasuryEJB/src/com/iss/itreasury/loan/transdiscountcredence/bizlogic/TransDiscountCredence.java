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
	 *凭证的保存操作
	*/
	public long save(TransDiscountCredenceInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *凭证的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *凭证的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *中油凭证的审核操作
	*/
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *凭证的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *凭证的单笔查询操作
	*/
	public TransDiscountCredenceInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *凭证的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *中油凭证的多笔查询操作
	*/
	public Collection cpfFindByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *凭证下的票据查询操作
	*/
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID) throws java.rmi.RemoteException, LoanException;

	/**
	 *票据查询操作
	*/
	public Collection findTransDiscountCredenceBill(long lContractID, long lCredenceID, long lBillSourceTypeID) throws java.rmi.RemoteException, LoanException;

	/**
	 *票据选择操作
	*/
	public void saveTransDiscountCredenceBill(long lTransDiscountCredenceID,long[] lIDList) throws java.rmi.RemoteException, LoanException;

	/**
	 * 选定的票据信息，操作Loan_DiscountContractBill表
	 * @param SelectedTransDiscountBillInfo 选定的票据信息
	 * @return SelectedTransDiscountBillInfo
	 */
	public SelectedTransDiscountBillInfo findBillInterestByBillID(SelectedTransDiscountBillInfo info) throws java.rmi.RemoteException, LoanException;
	
}