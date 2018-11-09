/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-7
 */
package com.iss.itreasury.securities.register.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.register.dao.SEC_PurchaseRegisterDAO;
import com.iss.itreasury.securities.register.dao.SEC_RepurchaseRegisterDAO;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RegisterOperation {
	
	private Register registerFacade = null;	
	

	
	
	public RegisterOperation(boolean isNeedInitEJB) throws RemoteException{
		if(isNeedInitEJB){
			try
			{
				RegisterHome home;
				try {
					home =
						(RegisterHome) EJBHomeFactory.getFactory().lookUpHome(
								RegisterHome.class);
				} catch (Exception e) {
					throw new RemoteException("EJBHomeFactory连接错误",e);
				}
				registerFacade = (Register) home.create();
			}
	
			catch (CreateException ce)
			{
				throw new RemoteException("发生CreateException",ce);
			}				
		}
	}
	

	
	
	
	/**
	 * 登记薄登记
	 * @param regiesterInfo 登记薄信息
	 * */
	public long register(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		return registerFacade.register(doInfo);
	}
	/**
	 * 登记薄取消登记
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelRegister(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		registerFacade.cancelRegister(doInfo);
	}
	/**
	 * 登记薄登记到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void maturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		registerFacade.maturate(doInfo);
	}
	/**
	 * 登记薄登记取消到期
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelMaturate(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		registerFacade.cancelMaturate(doInfo);
	}
	
	/**
	 * 登记薄申购确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	public void confirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		registerFacade.confirmApplication(doInfo);		
	}
	/**
	 * 登记薄申购取消确认，仅仅针对申购相关业务
	 * @param regiesterInfo 登记薄信息
	 * */
	public void cancelConfirmApplication(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		registerFacade.cancelConfirmApplication(doInfo);		
	}
	
	/**
	 * 申购卖出
	 * 一级卖出时，修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */
	public void sellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		registerFacade.sellOut(doInfo);		
	}
	
	/**
	 * 申购取消卖出
	 * 修改证券申购登记簿
	 * @param regiesterInfo 登记薄信息
	 * */	
	public void cancelSellOut(DeliveryOrderInfo doInfo)throws RemoteException,SecuritiesException{
		registerFacade.cancelSellOut(doInfo);
	}	
	
	/**
	 * 判断关联交易的交割单是否可以被取消复核
	 * */
	public void isAllowCancelCheck(DeliveryOrderInfo doInfo) throws RemoteException,SecuritiesException{
		if(doInfo.getRegisterId() < 0)
			return;
		RegisterBean registerBean = RegisterBean.newInstance(doInfo.getTransactionTypeInfo());
		registerBean.isAllowCancelCheck(doInfo);
	}	
	
	
	/**
	 *从回购买卖登记薄中获得所有未返款的交割单ID 
	 * */
	public Collection getAllNotRepayDeliverOrderID(long transactionTypeID) throws SecuritiesException{
		SEC_RepurchaseRegisterDAO dao = new SEC_RepurchaseRegisterDAO();
		if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY){
			transactionTypeID = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN;
		}else if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY){
			transactionTypeID = SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT;
		}else if(transactionTypeID == SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY){
			transactionTypeID = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE;
		}else if(transactionTypeID == SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY){
			transactionTypeID = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE;
		}else if(transactionTypeID == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPAY){
			transactionTypeID = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE;
		}else if(transactionTypeID == SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPAY){
			transactionTypeID = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE;
		}
		
		
		
		return dao.getAllNotRepayDeliverOrderID(transactionTypeID);	
	}
	
	/**
	 * 根据交易类型获取所有申请了且未中签,未返款的申购交割单ID
	 * */
	public Collection getDeliverOrderIDsForConfirm(long transactionTypeID) throws SecuritiesException{
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		long transTypeID = -1;
		if(transactionTypeID == SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_WIN){
			transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_WIN){
			transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_QUOTA_CONFIRM){
			transTypeID = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_QUOTA_CONFIRM){
			transTypeID = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE_CONFIRM){
			transTypeID = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;
		}else if(transactionTypeID == SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID_CONFIRM){
			transTypeID = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_QUOTA_CONFIRM){
			transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_WIN){
			transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_QUOTA_CONFIRM){
			transTypeID = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_WIN){
			transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_QUOTA_CONFIRM){
			transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;
		}
		
		return dao.getDeliverOrderIDsForConfirm(transTypeID);
	}	
	/**
	 * 根据交易类型获取所有中签了且未返款的中签交割单ID及未中签，未返款的交割单ID
	 * */
	public Collection getDeliverOrderIDsForDrawBack(long transactionTypeID) throws SecuritiesException{
		SEC_PurchaseRegisterDAO dao = new SEC_PurchaseRegisterDAO();
		long transTypeID = -1;
		if(transactionTypeID == SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_REPAY){
			transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_REPAY){
			transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY
				|| transactionTypeID == SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY){
			transTypeID = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY
				||transactionTypeID == SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY){
			transTypeID = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;		
		}else if(transactionTypeID == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY
				||transactionTypeID == SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY){
			transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;		
		}else if(transactionTypeID == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY
				||transactionTypeID == SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY){
			transTypeID = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID;		
		}else if(transactionTypeID == SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_REPAY){
			transTypeID = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_REPAY){
			transTypeID = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;
		}else if(transactionTypeID == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY
				||transactionTypeID == SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY){
			transTypeID = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;		
		}
		
		long repayOrSupply = SECConstant.BusinessType.isRepayOrSupply(transactionTypeID);
		return dao.getDeliverOrderIDsForDrawBack(transTypeID,repayOrSupply);
	}
	
	public long isDeliveryOrderRepaid(long deliveryOrderId)throws SecuritiesException{
		SEC_RepurchaseRegisterDAO dao = new SEC_RepurchaseRegisterDAO();
		return dao.isDeliveryOrderRepaid(deliveryOrderId);
	}
	

	
//	public long[] getDeliveryOrderIDsForOfflineConfirm(long transactionTypeID) throws SecuritiesException{
//		if(transactionTypeID == SECConstant.BusinessType)
//	}
	
}
