package com.iss.itreasury.securities.apply.bizlogic;

import java.rmi.RemoteException;
import java.util.*;
import java.sql.Timestamp;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.system.approval.dataentity.*;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Apply extends javax.ejb.EJBObject
{
	/**
	 *申请书的保存操作
	*/
	public long save(ApplyInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书的单笔查询操作
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书的多笔查询操作
	*/
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书下的投标区间保存操作
	*/
	public long saveBidRange(BidRangeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书下的投标区间查询操作
	*/
	public Collection findBidRangeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书下的投标区间删除操作
	*/
	public void deleteBidRange(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;
	/**
	 *申请书的审批操作
	*/
	public long doApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException;
	/**
	 *申请书的取消审批操作
	*/
	public long cancelApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException;
	
	/**
	 *申请书下的债券种类保存操作
	*/
	public long saveBondType(BondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书下的债券种类查询操作
	*/
	public Collection findBondTypeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *申请书下的债券种类删除操作
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;

	/**
		* 转移申请书管理人权限，支持批量转移
		* @param lID
		* @param lUserID
		* @throws java.rmi.RemoteException
		* @throws SecuritiesException
		*/
	public void transferApplyRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException;

}
