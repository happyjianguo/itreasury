/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settcontract.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractQueryInfo;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface SettContract extends javax.ejb.EJBObject
{
	/**
	 *合同的保存操作
	*/
	public long save(SettContractInfo info) throws java.rmi.RemoteException, SettlementException;

	/**
	 *合同的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SettlementException;

	/**
	 *合同的审核操作
	*/
	//public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SettlementException;

	/**
	 *合同的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SettlementException;

	/**
	 *合同的单笔查询操作
	*/
	public SettContractInfo findByID(long lID) throws java.rmi.RemoteException, SettlementException;

	/**
	 *合同的多笔查询操作
	*/
	public Collection findByMultiOption(SettContractQueryInfo qInfo) throws java.rmi.RemoteException, SettlementException;
	/**
	 *合同的分页
	*/
	public PageLoader getMultiOptin(SettContractQueryInfo conditionInfo)throws java.rmi.RemoteException, SettlementException;

}
