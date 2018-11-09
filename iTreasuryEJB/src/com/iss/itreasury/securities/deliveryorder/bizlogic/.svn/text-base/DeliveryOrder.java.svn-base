/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.deliveryorder.bizlogic;

import java.util.Collection;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderConditionInfo;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;


/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface DeliveryOrder extends javax.ejb.EJBObject
{
	/**
	 *资金划转的提交操作
	*/
	public long submit(DeliveryOrderInfo deliveryOrderInfo, NoticeInfo noticeInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的保存操作
	*/
	public void save(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的删除操作
	*/
	public void delete(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的暂存操作
	*/
	public void tmpSave(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的复核操作
	*/
	public void check(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的批量复核操作
	*/
	public void massCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的取消复核操作
	*/
	public void cancelCheck(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *交割单的批量取消复核操作
	*/
	public void massCancelCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException;

	/**
	 *交割单的单笔查询操作
	*/
	public DeliveryOrderInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException;
	/**
	*交割单的多参数查询操作
	*/
	public Collection findByCondition(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 * 链接查找方法
	 * @param deliveryOrderConditionInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public Collection findByCondition(DeliveryOrderConditionInfo deliveryOrderConditionInfo) throws java.rmi.RemoteException,SecuritiesException; 
	
	/**
	 * 查找所有未返款交割单
	 * @param conditionInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public Collection getAllNotRepayDeliveryOrder(DeliveryOrderConditionInfo conditionInfo) throws java.rmi.RemoteException,SecuritiesException;
	/*
	 * 查找某个申请书生成的交割单集
	 * 因为一个申请书可以生成多个交割单。
	 * 
	 */
	public Collection findByApplyFormID(long applyFormID) throws java.rmi.RemoteException,SecuritiesException; 

	/**
	 *映射申请书到交割单
	*/
	public DeliveryOrderInfo mappingApplyInfoToDeliveryOrderInfo(long applyFormID) throws java.rmi.RemoteException,SecuritiesException;
	/**
	 *取已到期的交割单
	 *
	*/
	public Collection getMaturityDeliveryOrder(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException;

}
