/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-5
 */
package com.iss.itreasury.securities.register.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;


/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RepurchaseRegisterParam extends SECBaseDataEntity
		implements
			IRegisterParam {
	private long id = -1;
	private long transactionTypeID = -1;
	private String systemTransactionCode = null;
	private long accountID = -1;
	private long clientID = -1;
	private long counterpartID = -1;
	private long pledgeSecuritiesID = -1;
	private double pledgeAmount = 0.0;
	private long pledgeQuantity = -1;
	private double pledgeRate = 0.0;
	private double amount = 0.0;
	private Timestamp valueDate = null;
	private Timestamp maturityDate = null;
	private long term = -1;
	private long termTypeID = -1;
	private double interestRate = 0.0;
	private long deliveryOrderID = -1;
	
	/**��ȡ��Ӧ�ĵǼǱ���Ϣ*/
	public SECBaseDataEntity gainRegisterInfo(int operationType){
		RepurchaseRegisterInfo resInfo = new RepurchaseRegisterInfo();
		resInfo.setAccountID(accountID);
		resInfo.setAmount(amount);
		//TBD????????
		/**
		 * ����/���/����/��ȯʱ�����=���׽�����ʱ�����-=����׽����>0ʱ��ʾδ����/δȫ�����
		 * ���=0ʱ��ʾȫ��������ɣ�����ҵ����������ڽ�����
		 * */
		resInfo.setBalance(amount);
		resInfo.setClientID(clientID);
		resInfo.setCounterpartID(counterpartID);
		//�ڵ���ҵ���е�LastDeliveryOrderIDҲͨ�����ֶν��д��ݣ����������²���ʱȡ���ֶθ���LastDeliveryOrderID
		resInfo.setFirstDeliveryOrderID(deliveryOrderID);
		resInfo.setId(id);
		resInfo.setInterestRate(interestRate);
		//resInfo.setLastDeliveryOrderID()
		resInfo.setMaturityDate(maturityDate);
		resInfo.setPledgeAmount(pledgeAmount);
		resInfo.setPledgeQuantity(pledgeQuantity);
		resInfo.setPledgeRate(pledgeRate);
		resInfo.setPledgeSecuritiesID(pledgeSecuritiesID);
		resInfo.setStatusID(SECConstant.BusinessAttributeStatus.CHECKED);
		resInfo.setSystemTransactionCode(systemTransactionCode);
		resInfo.setTerm(term);
		resInfo.setTermTypeID(termTypeID);
		resInfo.setTransactionTypeID(transactionTypeID);
		resInfo.setValueDate(valueDate);
		return resInfo;
	}
	
	
	public RepurchaseRegisterParam(DeliveryOrderInfo doInfo) throws SecuritiesException{
		id = doInfo.getRegisterId();
		transactionTypeID = doInfo.getTransactionTypeId();
		systemTransactionCode = doInfo.getSystemTransactionCode();
		//accountID = NameRef.getAccountIDFromDeliveryOrder(doInfo,false);
		accountID = doInfo.getAccountId();
		clientID = doInfo.getClientId();
		counterpartID = doInfo.getCounterpartId();
		pledgeSecuritiesID = doInfo.getSecuritiesId();
		pledgeAmount = doInfo.getPledgeSecuritiesAmount();
		pledgeRate = doInfo.getPledgeRate();
		amount = doInfo.getAmount();		
		valueDate = doInfo.getDeliveryDate();
		maturityDate = doInfo.getMaturityDate();
		term = doInfo.getTerm();
		termTypeID = doInfo.getTermTypeId();
		interestRate = doInfo.getRate();
		deliveryOrderID = doInfo.getId();		
	}
	
	


	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
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
}
