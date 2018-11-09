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
	 *合同的保存操作
	*/
	public long save(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同的单笔查询操作
	*/
	public TransDiscountContractInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *合同下的票据查询操作
	*/
	public Collection findBillByTransDiscountContractID(long lTransDiscountContractID) throws java.rmi.RemoteException, LoanException;
  
    /**
     *申请书下的票据保存操作
    */
    public long saveBill(TransDiscountContractBillInfo info) throws java.rmi.RemoteException, LoanException;

    /**
     * 申请书的提交操作
     */
    public void submit(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException;
	
    /**
     * 中油合同复核操作
     * @param ATInfo
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public void cpfCheck(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException, LoanException;

    /**
     * 中油的多条件查询操作
     * @param qInfo
     * @return
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public Collection cpfFindByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
    
    /**
     * 审批流操作操作
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