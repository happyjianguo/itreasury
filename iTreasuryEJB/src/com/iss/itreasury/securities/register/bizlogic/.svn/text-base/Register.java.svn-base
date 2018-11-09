/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.register.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dataentity.IRegisterParam;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Register extends javax.ejb.EJBObject
{
	/**
	 * 登记薄登记
	 * @param regiesterInfo 登记薄信息
	 * */
	public long register(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * 登记薄取消登记
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * 登记薄登记到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void maturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * 登记薄登记取消到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException;
	
	/**
	 * 登记薄申购确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	public void confirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	/**
	 * 登记薄申购取消确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelConfirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	
	/**
	 * 申购卖出
	 * 一级卖出时，修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */
	public void sellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	
	/**
	 * 申购取消卖出
	 * 修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelSellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException;
	

}
