package com.iss.itreasury.securities.securitiescontractextend.bizlogic;

import javax.ejb.EJBObject;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBondTypeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;

/**
 *
 * @Name:        SecuritiesContractExtend.java
 * @Description: 委托理财合同展期 
 * @Author:      gqfang 
 * @Create Date: 2005-4-19 
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface SecuritiesContractExtend extends EJBObject
{


	/**
	 * 查找单条合同信息
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractExtendInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 保存合同信息，新增或修改合同
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractExtendInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 取消合同
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 查找符合条件的合同信息，用于修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 审批合同
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 激活合同，支持批量激活
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException;

	/**
	* 转移合同管理人权限，支持批量转移
	* @param lID
	* @param lUserID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferContractRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * 手动结束合同，支持批量结束
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *合同下的债券种类保存操作
	*/
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *合同下的债券种类查询操作
	*/
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *合同下的债券种类删除操作
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;

}