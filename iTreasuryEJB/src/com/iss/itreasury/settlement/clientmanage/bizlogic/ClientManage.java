package com.iss.itreasury.settlement.clientmanage.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.settlement.clientmanage.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.util.IRollbackException;

public interface  ClientManage extends javax.ejb.EJBObject{
	
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：根据客户ID，查询客户信息
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明： 删除客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回删除的客户ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：取得客户编号
	 * 
	 * @param lOfficeID :
	 *            long @return: String - 新增的客户编号
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException;
	/**
	 * 方法说明： 新增客户
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - 返回新增的客户ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * 方法说明：修改客户信息
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - 客户ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException;

}
