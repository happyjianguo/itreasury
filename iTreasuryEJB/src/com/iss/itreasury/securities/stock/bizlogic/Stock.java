/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.stock.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockReturn;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockParam;
import com.iss.itreasury.securities.stock.dataentity.SingleAccountDailyStockParam;
import com.iss.itreasury.securities.stock.exception.DuplicatedStockException;
import com.iss.itreasury.securities.stock.exception.OutOfStockException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Stock extends javax.ejb.EJBObject
{
	/**
	 * ���
	 * */
	public void enterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * ����(���ɾ��)
	 * */	
	public void deleteEnterStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * ��⽻��
	 * */	
	public void deliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * ���ȡ������
	 * */		
	public void cancelDeliverStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * ����
	 * */	
	public SecuritiesStockReturn exitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * �˿�(����ɾ��)
	 * */		
	public void cancelExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * ���⽻��
	 * */		
	public SecuritiesStockReturn deliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	/**
	 * ����ȡ������
	 * */			
	public void cancelDeliverExitStock(SecuritiesStockParam stockParam) throws RemoteException,SecuritiesException;
	
	/**
	 * ��涳�᣺���ı��棬ֻ�������˿�涳������
	 * @param stockParam
	 * @return
	 * */
	public void freezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,OutOfStockException,SecuritiesException;
	
	/**
	 * ���ı��棬ֻ�Ǽ����˿�涳������
	 * @param stockParam
	 * @return
	 * */	
	public void cancelFreezeStock(SecuritiesStockParam stockParam) throws RemoteException,DuplicatedStockException,SecuritiesException;

	/**
	 * 	��������ս�
	 * ���һ��ҵ��λ��һ���ʽ��ʻ��µ�һ��֤ȯ���룬�����յ�ֹ�գ����п���ս�
	 * */
	public void caculateSingleAccountDailyStock(SingleAccountDailyStockParam param)throws RemoteException,SecuritiesException;
}
