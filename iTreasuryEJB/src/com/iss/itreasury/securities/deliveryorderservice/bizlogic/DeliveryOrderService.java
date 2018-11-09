/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.deliveryorderservice.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.deliveryorderservice.dataentity.CarryCostParam;
import com.iss.itreasury.securities.exception.SecuritiesException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface DeliveryOrderService extends javax.ejb.EJBObject
{
//	/**
//	 * 交割单保存操作
//	 * @return 登记薄ID
//	 * */
//	public long save(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
//	
//	/**
//	 * 交割单删除操作
//	 * */
//	public void delete(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
//	
//	/**
//	 * 交割单复核操作
//	 * */
//	public void check(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
//	
//	/**
//	 * 交割单取消复核操作
//	 * */
//	public void cancelCheck(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	/**
	 * 交割单保存操作
	 * @return 登记薄ID
	 * */
	public long saveDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException;
	/**
	 * 交割单删除操作
	 * */
	public void deleteDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException;
	/**
	 * 交割单复核操作
	 * */
	public void checkDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException;
	/**
	 * 交割单取消复核操作
	 * */
	public void cancelCheckDeliveryOrder(DeliveryOrderInfo doInfo) throws  RemoteException,SecuritiesException;
	
	/**
	 * 针对一个业务单位下一个资金帐户下的一个证券代码，从起日到止日，对其所有出库交易进行成本结转。
	 * */
	public void carryCost(CarryCostParam param) throws  RemoteException,SecuritiesException;
	
	/**
	 * 本系统支持T+N交易，即交割单的交易日（或称成交日）可以和交割日（或称结算日、记帐日）不同。如果已达成了成交协议，
	 * 不论是否当时交割，都可以即时对交割单进行复核。也就是说，交割单可以在交易日录入并复核，如果录入时交割日晚于交割日，
	 * 则此笔业务所涉及的资金或库存不会即时发生变化，而是要到系统运行到交割日当天时，才会发生变化
	 * */
	public void deliverAutomatically(long officeID,long currencyID) throws RemoteException,SecuritiesException;	
}
