/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-17
 */
package com.iss.itreasury.securities.register.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.NameRef;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PurchaseRegisterParam extends SECBaseDataEntity
		implements
			IRegisterParam {
	
	private long id = -1;
	private long transactionTypeID = -1;          //交易类型ID
	private String systemTransactionCode = null;  //交易系统成交编号
	private long acountID = -1;                   //开户资金帐户ID
	private long securitiesID = -1;               //证券ID
	private long clientID = -1;                   //业务单位ID
	private long counterpartID = -1;              //交易对手ID
	private Timestamp deliveryDate = null;        //交割日
	private long deliveryOrderID = -1;            //交割单ID
	private double quantity = 0.0;                   //成交数量
	private double price = 0.0;                   //成交价 
	private double amount = 0.0;                  //成交金额  
	private double netIncome = 0.0;               //实际收付
	private double marginRate = 0.0;              //预付定金比例%  

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public PurchaseRegisterParam(DeliveryOrderInfo doInfo) throws SecuritiesException{
		id = doInfo.getRegisterId();
		transactionTypeID = doInfo.getTransactionTypeId();
		//acountID = NameRef.getAccountIDFromDeliveryOrder(doInfo,true);
		acountID = doInfo.getAccountId();
		securitiesID = doInfo.getSecuritiesId();
		clientID = doInfo.getClientId();
		counterpartID = doInfo.getCounterpartId();
		deliveryOrderID = doInfo.getId();
		deliveryDate = doInfo.getDeliveryDate();
		quantity = doInfo.getQuantity();
		price = doInfo.getPrice();

		netIncome = doInfo.getNetIncome();
		//marginRate = doInfo.getm
	}

//	public SECBaseDataEntity gainRegisterInfo(int operationType) {
//		PurchaseRegisterInfo resInfo = new PurchaseRegisterInfo();
//
//		resInfo.setId(id);	
//		resInfo.setAccountID(acountID);
//		resInfo.setClient(clientID);
//		resInfo.setSecuritiesID(securitiesID);
//		resInfo.setTransactionTypeID(transactionTypeID);
		
//		switch(operationType){
//			case REGISTER_OPERATION_TYPE_REGISTER:
//			case REGISTER_OPERATION_TYPE_CANCELREGISTER:{
//				resInfo.setApplyDeliveryOrderID(deliveryOrderID);
//				resInfo.setApplyDate(deliveryDate);
//				resInfo.setApplyQuantity(quantity);
//				resInfo.setApplyPrice(price);
//				resInfo.setApplyAmount(amount);
//				if(marginRate == 0.0)
//					marginRate = 100.0;
//				resInfo.setMarginRate(marginRate);
//				resInfo.setMarginAmount(netIncome);
//				resInfo.setStatusID(SECConstant.RemarkStatus.VALID);
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_MATURATE:
//			{
//			//	resInfo.setDrawbackAmount(amount);
//				resInfo.setDrawbackDate(deliveryDate);
//				resInfo.setDrawbackDeliveryOrderID(deliveryOrderID);				
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_CANCELMATURATE:{
//				resInfo.setDrawbackAmount(0.0);
//				resInfo.setDrawbackDate(SECBaseDataEntity.getNullTimeStamp());
//				resInfo.setDrawbackDeliveryOrderID(-1);				
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_APPLYCONFIRM:{
//				resInfo.setConfirmAmount(amount);
//				resInfo.setConfirmDate(deliveryDate);
//				resInfo.setConfirmDeliveryOrderID(deliveryOrderID);
//				resInfo.setConfirmPrice(price);
//				resInfo.setConfirmQuantity(quantity);
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_CANCELAPPLYCONFIRM:{
//				resInfo.setConfirmAmount(0.0);
//				resInfo.setConfirmDate(SECBaseDataEntity.getNullTimeStamp());
//				resInfo.setConfirmDeliveryOrderID(-1);
//				resInfo.setConfirmPrice(0.0);
//				resInfo.setConfirmQuantity(0);				
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_APPLYSELL:{
//				resInfo.setSellingAmount(amount);
//				resInfo.setSellingDate(deliveryDate);
//				resInfo.setSellingDeliveryOrderID(deliveryOrderID);
//				resInfo.setSellingPrice(price);
//				resInfo.setSellingQuantity(quantity);
//			}
//			break;
//			case REGISTER_OPERATION_TYPE_CANCELAPPLYSELL:{
//				resInfo.setSellingAmount(0.0);
//				resInfo.setSellingDate(SECBaseDataEntity.getNullTimeStamp());
//				resInfo.setSellingDeliveryOrderID(-1);
//				resInfo.setSellingPrice(0.0);
//				resInfo.setSellingQuantity(0);				
//			}
//			break;
//		}
//		return resInfo;
//	}
	/**
	 * @return Returns the acountID.
	 */
	public long getAcountID() {
		return acountID;
	}
	/**
	 * @param acountID The acountID to set.
	 */
	public void setAcountID(long acountID) {
		putUsedField("acountID", acountID);
		this.acountID = acountID;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		putUsedField("clientID", clientID);
		this.clientID = clientID;
	}
	/**
	 * @return Returns the counterpartID.
	 */
	public long getCounterpartID() {
		return counterpartID;
	}
	/**
	 * @param counterpartID The counterpartID to set.
	 */
	public void setCounterpartID(long counterpartID) {
		putUsedField("counterpartID", counterpartID);
		this.counterpartID = counterpartID;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		putUsedField("deliveryDate", deliveryDate);
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the deliveryOrderID.
	 */
	public long getDeliveryOrderID() {
		return deliveryOrderID;
	}
	/**
	 * @param deliveryOrderID The deliveryOrderID to set.
	 */
	public void setDeliveryOrderID(long deliveryOrderID) {
		putUsedField("deliveryOrderID", deliveryOrderID);
		this.deliveryOrderID = deliveryOrderID;
	}
	/**
	 * @return Returns the marginRate.
	 */
	public double getMarginRate() {
		return marginRate;
	}
	/**
	 * @param marginRate The marginRate to set.
	 */
	public void setMarginRate(double marginRate) {
		putUsedField("marginRate", marginRate);
		this.marginRate = marginRate;
	}
	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome() {
		return netIncome;
	}
	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome) {
		putUsedField("netIncome", netIncome);
		this.netIncome = netIncome;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		putUsedField("price", price);
		this.price = price;
	}
	/**
	 * @return Returns the quantity.
	 */
	public double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(long quantity) {
		putUsedField("quantity", quantity);
		this.quantity = quantity;
	}
	/**
	 * @return Returns the systemTransactionCode.
	 */
	public String getSystemTransactionCode() {
		return systemTransactionCode;
	}
	/**
	 * @param systemTransactionCode The systemTransactionCode to set.
	 */
	public void setSystemTransactionCode(String systemTransactionCode) {
		putUsedField("systemTransactionCode", systemTransactionCode);
		this.systemTransactionCode = systemTransactionCode;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		putUsedField("transactionTypeID", transactionTypeID);
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * @return Returns the securitiesID.
	 */
	public long getSecuritiesID() {
		return securitiesID;
	}
	/**
	 * @param securitiesID The securitiesID to set.
	 */
	public void setSecuritiesID(long securitiesID) {
		putUsedField("securitiesID", securitiesID);
		this.securitiesID = securitiesID;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.securities.register.dataentity.IRegisterParam#gainRegisterInfo(int)
	 */
	public SECBaseDataEntity gainRegisterInfo(int operationType) {
		// TODO Auto-generated method stub
		return null;
	}
}
