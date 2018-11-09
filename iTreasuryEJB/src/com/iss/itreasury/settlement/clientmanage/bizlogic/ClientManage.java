package com.iss.itreasury.settlement.clientmanage.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.clientmanage.dataentity.ClientInfo;
import com.iss.itreasury.settlement.clientmanage.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.util.IRollbackException;

public interface  ClientManage extends javax.ejb.EJBObject{
	
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����������Ŀͻ�
	 * 
	 * @param qcci:
	 *            QueryClientConditionInfo @return: Collection
	 * @throws Exception
	 */
	public Collection findClientByCondition(QueryClientConditionInfo qcci) throws RemoteException, IRollbackException;
	/**
	 * ����˵�������ݿͻ�ID����ѯ�ͻ���Ϣ
	 * 
	 * @param lClientID :
	 *            long @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findClientByID(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * ����˵���� ɾ���ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ����ɾ���Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long deleteClient(long lClientID) throws RemoteException, IRollbackException;
	/**
	 * ����˵����ȡ�ÿͻ����
	 * 
	 * @param lOfficeID :
	 *            long @return: String - �����Ŀͻ����
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws RemoteException, IRollbackException;
	/**
	 * ����˵���� �����ͻ�
	 * 
	 * @param ci:
	 *            ClientInfo
	 * @return : long - ���������Ŀͻ�ID
	 * @throws Exception
	 *  
	 */
	public long addClient(ClientInfo ci) throws RemoteException, IRollbackException;
	/**
	 * ����˵�����޸Ŀͻ���Ϣ
	 * 
	 * @param ci:
	 *            ClientInfo @return: long - �ͻ�ID
	 * @throws Exception
	 */
	public long updateClient(ClientInfo ci) throws RemoteException, IRollbackException;

}
