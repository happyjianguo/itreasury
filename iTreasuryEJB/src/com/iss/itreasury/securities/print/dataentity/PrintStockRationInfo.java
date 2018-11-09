/*
 * Created on 2004-5-13
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
public class PrintStockRationInfo extends SECBaseDataEntity	implements Serializable 
{

		/* (non-Javadoc)
		 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
		 */
		public long getId() 
		{
			// TODO Auto-generated method stub
			return 0;
		}
	
		/* (non-Javadoc)
		 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
		 */
		public void setId(long id) 
		{
			// TODO Auto-generated method stub
			
		}
		
	//	֤ȯ(��Ʊ)ID
		private long securitiesId = -1;
    //	ҵ��λID
		private long clientID = -1 ; 
	//  �ɶ��ʻ�ID
	    private long stockHolderAccountId = -1;
		private long stockHolderAccountId1 = -1;
    //	��������ID
		private long transactiontypeID = -1 ;
    //	����Ӫҵ��ID
		private long counterpartID = -1;
    //	�ʽ��ʺ�ID
		private long accountID = -1;
   //	�ɽ�����
		private Timestamp transactionDate  = null;
   //   ����ǰ���(���׺��棽����ǰ��棫�ɽ�����)******************
        private double stockQuantity=0.0;
   //   ��ɼ۸�(�ɽ��۸�)		
	    private double price = 0.0;
   //   �ɽ�����
        private double quantity= 0.0;
   //   �ɽ����
        private double amount= 0.0;
   //   ˰��
        private double tax =0.0;
   //   ʵ���ո�
        private double netIncome =0.0;
	    
	/**
	 * @return
	 */
	public long getAccountID() {
		return accountID;
	}

/**
 * @return
 */
public double getAmount() {
	return amount;
}

	/**
	 * @return
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * @return
	 */
	public long getCounterpartID() {
		return counterpartID;
	}

/**
 * @return
 */
public double getNetIncome() {
	return netIncome;
}

/**
 * @return
 */
public double getPrice() {
	return price;
}

/**
 * @return
 */
public double getQuantity() {
	return quantity;
}

	/**
	 * @return
	 */
	public long getSecuritiesId() {
		return securitiesId;
	}

	/**
	 * @return
	 */
	public long getStockHolderAccountId() {
		return stockHolderAccountId;
	}

/**
 * @return
 */
public double getStockQuantity() {
	return stockQuantity;
}

/**
 * @return
 */
public double getTax() {
	return tax;
}

/**
 * @return
 */
public Timestamp getTransactionDate() {
	return transactionDate;
}

	/**
	 * @return
	 */
	public long getTransactiontypeID() {
		return transactiontypeID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		accountID = l;
	}

/**
 * @param d
 */
public void setAmount(double d) {
	amount = d;
}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		clientID = l;
	}

	/**
	 * @param l
	 */
	public void setCounterpartID(long l) {
		counterpartID = l;
	}

/**
 * @param d
 */
public void setNetIncome(double d) {
	netIncome = d;
}

/**
 * @param d
 */
public void setPrice(double d) {
	price = d;
}

/**
 * @param d
 */
public void setQuantity(double d) {
	quantity = d;
}

	/**
	 * @param l
	 */
	public void setSecuritiesId(long l) {
		securitiesId = l;
	}

	/**
	 * @param l
	 */
	public void setStockHolderAccountId(long l) {
		stockHolderAccountId = l;
	}

/**
 * @param d
 */
public void setStockQuantity(double d) {
	stockQuantity = d;
}

/**
 * @param d
 */
public void setTax(double d) {
	tax = d;
}

/**
 * @param timestamp
 */
public void setTransactionDate(Timestamp timestamp) {
	transactionDate = timestamp;
}

	/**
	 * @param l
	 */
	public void setTransactiontypeID(long l) {
		transactiontypeID = l;
	}

		/**
		 * @return
		 */
		public long getStockHolderAccountId1()
		{
			return stockHolderAccountId1;
		}

		/**
		 * @param l
		 */
		public void setStockHolderAccountId1(long l)
		{
			stockHolderAccountId1 = l;
		}

}
