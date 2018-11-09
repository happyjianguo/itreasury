package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.deliveryorder.bizlogic.DeliveryOrder;
import com.iss.itreasury.securities.deliveryorder.bizlogic.DeliveryOrderHome;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderConditionInfo;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dataentity.NoticeInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class DeliveryOrderDelegation {
	
	private DeliveryOrder deliverOrderFacade = null;
	
	
	/**
	 * 排序字段
	 */
	public static final String ORDER_FIELD_ID					= "id";					//交割单编号
	
	public static final String ORDER_FIELD_TRANSACTIONTYPEID 	= "transactionTypeId";	//交易类型
	
	public static final String ORDER_FIELD_AMOUNT 				= "amount";				//拆借金额
	
	public static final String ORDER_FIELD_RATE					= "rate";				//利率
	
	public static final String ORDER_FIELD_TERM					= "term";				//拆借期限
	
	public static final String ORDER_FIELD_PRICE				= "price";				//成交价格

	public static final String ORDER_FIELD_QUANTITY				= "quantity";			//成交数量
	
	public static final String ORDER_FIELD_OPPOSITEQUANTITY		= "OPPOSITEQUANTITY";	//股票数量
	
	public DeliveryOrderDelegation() throws RemoteException{
		try
		{
			DeliveryOrderHome home;
			try {
				home =
					(DeliveryOrderHome) EJBHomeFactory.getFactory().lookUpHome(
						DeliveryOrderHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			deliverOrderFacade = (DeliveryOrder) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
	/**
	 *资金划转的提交操作
	*/
	public long submit(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return submit(deliveryOrderInfo,null);
	}
	/**
	 *资金划转的提交操作
	*/
	public long submit(DeliveryOrderInfo deliveryOrderInfo, NoticeInfo noticeInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.submit(deliveryOrderInfo,noticeInfo);
	}

	/**
	 *交割单的保存操作
	*/
	public void save(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.save(deliveryOrderInfo);
	}
	
	/**
	 *交割单的删除操作
	*/
	public void delete(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.delete(deliveryOrderInfo);
	}	
	
	/**
	 *交割单的暂存操作
	*/
	public void tmpSave(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.tmpSave(deliveryOrderInfo);
	}
	
	/**
	 *交割单的复核操作
	*/
	public void check(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.check(deliveryOrderInfo);
	}
	/**
	 *交割单的批量复核操作
	*/
	public void massCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.massCheck(deliveryOrderInfoCollection);
	}
	
	/**
	 *交割单的取消复核操作
	*/
	public void cancelCheck(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.cancelCheck(deliveryOrderInfo);
	}	
	/**
	 *交割单的批量取消复核操作
	*/
	public void massCancelCheck(Collection deliveryOrderInfoCollection) throws java.rmi.RemoteException,SecuritiesException
	{
		deliverOrderFacade.massCancelCheck(deliveryOrderInfoCollection);
	}
	
	/**
	 *交割单的单笔查询操作
	*/
	public DeliveryOrderInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.findByID(lID);
	}
	/**
	 * 链接查找方法
	 * @param deliveryOrderConditionInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public Collection findByCondition(DeliveryOrderConditionInfo deliveryOrderConditionInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.findByCondition(deliveryOrderConditionInfo);
	}
	/**
	 * 查找所有未返款交割单
	 * @param conditionInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public Collection getAllNotRepayDeliveryOrder(DeliveryOrderConditionInfo conditionInfo) throws java.rmi.RemoteException,SecuritiesException{
		return deliverOrderFacade.getAllNotRepayDeliveryOrder(conditionInfo);
	}
	
	/**
	*交割单的多参数查询操作
	*/
	public Collection findByCondition(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.findByCondition(deliveryOrderInfo);
	}
	/**
	 * 查找某个申请书生成的交割单集
	 * 因为一个申请书可以生成多个交割单。
	 * 
	 */
	public Collection findByApplyFormID(long applyFormID) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.findByApplyFormID(applyFormID);
	} 

	/**
	 *映射申请书到交割单
	*/
	public DeliveryOrderInfo mappingApplyInfoToDeliveryOrderInfo(long applyFormID) throws java.rmi.RemoteException,SecuritiesException
	{
		Log.print("in delegation");
		return deliverOrderFacade.mappingApplyInfoToDeliveryOrderInfo(applyFormID);
	}
	/**
	 *取已到期的交割单
	 *
	*/
	public Collection getMaturityDeliveryOrder(DeliveryOrderInfo deliveryOrderInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return deliverOrderFacade.getMaturityDeliveryOrder(deliveryOrderInfo);
	}



}
