/*
 * 创建日期 2004-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/** 
 * @author 王怡
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesApplyParam extends SECBaseDataEntity	implements Serializable {
    //业务通知单录入日期开始日
	private Timestamp noticeInputDateStart   = null;
	//业务通知单录入日期结束日
	private Timestamp noticeInputDateEnd     = null;
	//业务通知单收付款日期
	private Timestamp noticeExecuteDate      = null;
	
	private Timestamp deliveryOrderInputDateStart   = null;
	private Timestamp deliveryOrderInputDateEnd   = null;
	
	// 业务类型ID
	private long businessTypeId              = -1;
		
	// 业务单位ID
	private long clientIds               = -1;
	
	// 股东帐户名称
	private String[] stockHolderAccountIds		= null;

	// 交易类型ID
	private long transactionTypeIds     = -1;

    // 开户营业部ID
	private long bankOfDepositIds       = -1;
	// 资金账号
	private String[] accountIds            = null;
	
	// 证券名称
	private String[] securitiesIds           = null;
	
	//排序方式
	private long desc                       = -1;
	//排序字段
	private long orderField                 = -1;
	//业务状态ID
	private long businessAttributeId        = -1;
	
	
	
	
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
	



	/**
	 * @return
	 */
	public String[] getAccountIds() {
		return accountIds;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositIds() {
		return bankOfDepositIds;
	}

	/**
	 * @return
	 */
	public long getBusinessAttributeId() {
		return businessAttributeId;
	}

	/**
	 * @return
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @return
	 */
	public long getClientIds() {
		return clientIds;
	}

	/**
	 * @return
	 */
	public long getDesc() {
		return desc;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeExecuteDate() {
		return noticeExecuteDate;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeInputDateEnd() {
		return noticeInputDateEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeInputDateStart() {
		return noticeInputDateStart;
	}

	/**
	 * @return
	 */
	public long getOrderField() {
		return orderField;
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
	 * @param strings
	 */
	public void setAccountIds(String[] strings) {
		accountIds = strings;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositIds(long l) {
		bankOfDepositIds = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessAttributeId(long l) {
		businessAttributeId = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessTypeId(long l) {
		businessTypeId = l;
	}

	/**
	 * @param l
	 */
	public void setClientIds(long l) {
		clientIds = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		desc = l;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeExecuteDate(Timestamp timestamp) {
		noticeExecuteDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeInputDateEnd(Timestamp timestamp) {
		noticeInputDateEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeInputDateStart(Timestamp timestamp) {
		noticeInputDateStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		orderField = l;
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
	 * @return
	 */
	public long getTransactionTypeIds() {
		return transactionTypeIds;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeIds(long l) {
		transactionTypeIds = l;
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
	public void setDeliveryOrderInputDateEnd(Timestamp deliveryOrderInputDateEnd) {
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
}
