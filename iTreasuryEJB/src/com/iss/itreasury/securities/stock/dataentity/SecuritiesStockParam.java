/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-8
 */
package com.iss.itreasury.securities.stock.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.stock.bizlogic.StockOperation;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesStockParam extends SECBaseDataEntity {

	private long transactionTypeID = -1;			//��������ID
	private long deliveryOrderCodeID = -1;			//���ID
	private long accountID = -1;                     //�����ʽ��ʻ�ID
	private long client = -1;						//ҵ��λID
	private long securitiesID = -1;					//֤ȯID
	private double amount = 0.0;					//������ʵ���ո���
	private double netPriceAmount = 0.0;            //���۽����ľ���ʵ���ո���
	private double quantity = 0.0;					//����
	private Timestamp transactionDate = null;       //��������/�ɽ�����
	private Timestamp deliveryDate = null;          //��������/��������
	private double unitCost = 0.0;                  //��λ�ɱ���ԭ���ⵥλ�ɱ���
	private double unitNetCost = 0.0;				//��λ���۳ɱ���ԭ���ⵥλ���۳ɱ���
	private double unitProfitLoss = 0.0;            //��λʵ��ӯ����ԭ���ⵥλʵ��ӯ����
	private double unitNetProfitLoss = 0.0;         //��λ����ʵ��ӯ��(ԭ���ⵥλ����ʵ��ӯ��)
	
	private long isNeedCheckOverDraft = SECConstant.FALSE; 		//�Ƿ���͸��
	
	
	
	
	private long officeID = -1;
	private long currencyID = -1;

	public SecuritiesStockParam(DeliveryOrderInfo doInfo,int stockOperationType) throws SecuritiesException{
		transactionTypeID = doInfo.getTransactionTypeId();
		deliveryOrderCodeID = doInfo.getId();
		//�ʽ��ʻ�=����ϵĽ����ʺ�ID������ý������Ͷ�Ӧ��ҵ������Ϊ���м�ҵ����ΪNULL
		//accountID = NameRef.getAccountIDFromDeliveryOrder(doInfo, false);
		accountID = doInfo.getAccountId();
		client = doInfo.getClientId();
		amount = doInfo.getAmount();
		isNeedCheckOverDraft = doInfo.getIsCheckOverStock();
		
		if(stockOperationType == StockOperation.STOCK_OPERATION_FREEZE
		|| stockOperationType == StockOperation.STOCK_OPERATION_CANCELFREEZE){
			quantity = doInfo.getPledgeSecuritiesQuantity();
			securitiesID = doInfo.getPledgeSecuritiesId();
		}else{	
			quantity = doInfo.getQuantity();
			securitiesID = doInfo.getSecuritiesId();
		}
		deliveryDate = doInfo.getDeliveryDate();
		netPriceAmount = doInfo.getNetPriceAmount();
		officeID = doInfo.getOfficeId();
		currencyID = doInfo.getCurrencyId();
		unitCost = doInfo.getUnitCost();
		unitNetCost = doInfo.getUnitNetCost();
		unitProfitLoss = doInfo.getUnitProfitLoss();
		unitNetProfitLoss = doInfo.getUnitNetProfitLoss();
	}
	
	public SecuritiesStockParam(){
	}
	
//	public SecuritiesStockInfo getSecuritiesStockInfo(int stockOperationType){
//		SecuritiesStockInfo resInfo = new SecuritiesStockInfo();
//		switch(stockOperationType){
//			case StockOperation.STOCK_OPERATION_ENTER:{ 
//				resInfo.setAccountID(accountID);
//				resInfo.setClient(client);
//				//resInfo.
//			}
//			break;
//			case StockOperation.STOCK_OPERATION_CANCELENTER:{
//			}
//			break;
//			case StockOperation.STOCK_OPERATION_EXIT:{
//			}
//			break;
//			case StockOperation.STOCK_OPERATION_CANCELEXIT:{
//			}
//			break;
//			case StockOperation.STOCK_OPERATION_FREEZE:{
//			}
//			break;
//			case StockOperation.STOCK_OPERATION_CANCELFREEZE:{
//			}
//			break;
//		}
//		return null;
//	}
	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
	}
	/**
	 * @return Returns the acountID.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param acountID The acountID to set.
	 */
	public void setAccountID(long acountID) {
		putUsedField("accountID", acountID);
		this.accountID = acountID;
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
	 * @return Returns the client.
	 */
	public long getClient() {
		return client;
	}
	/**
	 * @param client The client to set.
	 */
	public void setClient(long client) {
		putUsedField("client", client);
		this.client = client;
	}
	/**
	 * @return Returns the deliveryOrderCodeID.
	 */
	public long getDeliveryOrderCodeID() {
		return deliveryOrderCodeID;
	}
	/**
	 * @param deliveryOrderCodeID The deliveryOrderCodeID to set.
	 */
	public void setDeliveryOrderCodeID(long deliveryOrderCodeID) {
		putUsedField("deliveryOrderCodeID", deliveryOrderCodeID);
		this.deliveryOrderCodeID = deliveryOrderCodeID;
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
	public void setQuantity(double quantity) {
		putUsedField("quantity", quantity);
		this.quantity = quantity;
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
	 * @return Returns the netPriceAmount.
	 */
	public double getNetPriceAmount() {
		return netPriceAmount;
	}
	/**
	 * @param netPriceAmount The netPriceAmount to set.
	 */
	public void setNetPriceAmount(double netPriceAmount) {
		putUsedField("netPriceAmount", netPriceAmount);
		this.netPriceAmount = netPriceAmount;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
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
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the unitCost.
	 */
	public double getUnitCost() {
		return unitCost;
	}
	/**
	 * @param unitCost The unitCost to set.
	 */
	public void setUnitCost(double unitCost) {
		putUsedField("unitCost", unitCost);
		this.unitCost = unitCost;
	}
	/**
	 * @return Returns the unitNetCost.
	 */
	public double getUnitNetCost() {
		return unitNetCost;
	}
	/**
	 * @param unitNetCost The unitNetCost to set.
	 */
	public void setUnitNetCost(double unitNetCost) {
		putUsedField("unitNetCost", unitNetCost);
		this.unitNetCost = unitNetCost;
	}
	/**
	 * @return Returns the unitNetProfitLoss.
	 */
	public double getUnitNetProfitLoss() {
		return unitNetProfitLoss;
	}
	/**
	 * @param unitNetProfitLoss The unitNetProfitLoss to set.
	 */
	public void setUnitNetProfitLoss(double unitNetProfitLoss) {
		putUsedField("unitNetProfitLoss", unitNetProfitLoss);
		this.unitNetProfitLoss = unitNetProfitLoss;
	}
	/**
	 * @return Returns the unitProfitLoss.
	 */
	public double getUnitProfitLoss() {
		return unitProfitLoss;
	}
	/**
	 * @param unitProfitLoss The unitProfitLoss to set.
	 */
	public void setUnitProfitLoss(double unitProfitLoss) {
		putUsedField("unitProfitLoss", unitProfitLoss);
		this.unitProfitLoss = unitProfitLoss;
	}
	/**
	 * @return Returns the isNeedCheckOverDraft.
	 */
	public long getIsNeedCheckOverDraft() {
		return isNeedCheckOverDraft;
	}
	/**
	 * @param isNeedCheckOverDraft The isNeedCheckOverDraft to set.
	 */
	public void setIsNeedCheckOverDraft(long isNeedCheckOverDraft) {
		putUsedField("isNeedCheckOverDraft", isNeedCheckOverDraft);
		this.isNeedCheckOverDraft = isNeedCheckOverDraft;
	}
}
