/*
 * Created on 2004-5-12
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author jsxie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PrintStockRationParam extends SECBaseDataEntity implements Serializable
{
	/* (non-Javadoc)
		 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
		 */
		public long getId() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
		 */
		public void setId(long id) {
			// TODO Auto-generated method stub
		
		}
//	    �ɽ����ڿ�ʼ��
	    private Timestamp transactionDateStart   = null;
//      �ɽ����ڽ�����
	    private Timestamp transactionDateEnd     = null;
//	    ��������ID
	    private String[] transactionTypeIds      = null;
//		ҵ��λID
		private long clientId =-1;
//      �ɶ��ʻ�ID
        private String[] stockHolderAccountIds=null;
//	    ����Ӫҵ��ID
	    private long counterpartId = -1;
//		�ʽ��˺�
		private String[] accountIds = null;
//      ֤ȯ(��Ʊ)ID
        private String[] securitiesIds=null;
        
    	private Timestamp deliveryOrderInputDateStart   = null;
    	private Timestamp deliveryOrderInputDateEnd   = null;




/**
 * @return
 */
public String[] getAccountIds() {
	return accountIds;
}

/**
 * @return
 */
public long getClientId() {
	return clientId;
}

/**
 * @return
 */
public long getCounterpartId() {
	return counterpartId;
}

/**
 * @return
 */
public String[] getSecuritiesIds() {
	return securitiesIds;
}

/**
 * @return
 */
public String[] getStockHolderAccountIds() {
	return stockHolderAccountIds;
}

/**
 * @return
 */
public Timestamp getTransactionDateEnd() {
	return transactionDateEnd;
}

/**
 * @return
 */
public Timestamp getTransactionDateStart() {
	return transactionDateStart;
}

/**
 * @return
 */
public String[] getTransactionTypeIds() {
	return transactionTypeIds;
}

/**
 * @param strings
 */
public void setAccountIds(String[] strings) {
	accountIds = strings;
}

/**
 * @param l
 */
public void setClientId(long l) {
	clientId = l;
}

/**
 * @param l
 */
public void setCounterpartId(long l) {
	counterpartId = l;
}

/**
 * @param strings
 */
public void setSecuritiesIds(String[] strings) {
	securitiesIds = strings;
}

/**
 * @param strings
 */
public void setStockHolderAccountIds(String[] strings) {
	stockHolderAccountIds = strings;
}

/**
 * @param timestamp
 */
public void setTransactionDateEnd(Timestamp timestamp) {
	transactionDateEnd = timestamp;
}

/**
 * @param timestamp
 */
public void setTransactionDateStart(Timestamp timestamp) {
	transactionDateStart = timestamp;
}

/**
 * @param strings
 */
public void setTransactionTypeIds(String[] strings) {
	transactionTypeIds = strings;
}

		/**
		 * @return Returns the deliveryOrderInputDateEnd.
		 */
		public Timestamp getDeliveryOrderInputDateEnd() {
			return deliveryOrderInputDateEnd;
		}
		/**
		 * @param deliveryOrderInputDateEnd The deliveryOrderInputDateEnd to set.
		 */
		public void setDeliveryOrderInputDateEnd(
				Timestamp deliveryOrderInputDateEnd) {
			this.deliveryOrderInputDateEnd = deliveryOrderInputDateEnd;
		}
		/**
		 * @return Returns the deliveryOrderInputDateStart.
		 */
		public Timestamp getDeliveryOrderInputDateStart() {
			return deliveryOrderInputDateStart;
		}
		/**
		 * @param deliveryOrderInputDateStart The deliveryOrderInputDateStart to set.
		 */
		public void setDeliveryOrderInputDateStart(
				Timestamp deliveryOrderInputDateStart) {
			this.deliveryOrderInputDateStart = deliveryOrderInputDateStart;
		}
}//class PrintStockRationParam
