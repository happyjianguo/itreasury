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
	 *申请书的保存操作
	*/
	public long save(TransDiscountApplyInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 *中油申请书的审核操作
	 */
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书的单笔查询操作
	*/
	public TransDiscountApplyInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书下的票据保存操作
	*/
	public long saveBill(TransDiscountApplyBillInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书下的票据查询操作
	*/
	public Collection findBillByTransDiscountApplyID(long lTransDiscountApplyID,long lInOrOut) throws java.rmi.RemoteException, LoanException;

	/**
	 *申请书下的票据删除操作
	*/
	public void deleteBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 *票据查询操作
	*/
	public Collection findTransDiscountApplyBill(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;

	/**
	 *票据选择操作
	*/
	public void saveTransDiscountApplyBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException;

    /**
     * 计算转贴现申请下的贴现票据利息，
     * 操作Loan_DiscountFormBill表
     * @param lDiscountApplyID 贴现标识
     * @param  dRate        贴现利率
     * @param  tsDate       贴现日
     * @return 返回贴现票据的列表
     */
     public Collection calculateTransDiscountBillInterest(TransDiscountApplyQueryInfo qInfo)
     throws java.rmi.RemoteException, LoanException;

    /**
     *申请书的单笔查询操作
    */
    public TransDiscountApplyBillInfo findBillByID(long lID) throws java.rmi.RemoteException, LoanException;
    
    /**
     * 中油的申请书多条件查询操作
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