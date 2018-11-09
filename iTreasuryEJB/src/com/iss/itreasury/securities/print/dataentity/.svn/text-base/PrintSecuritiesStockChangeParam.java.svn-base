/*
 * Created on 2004-5-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author wangyi
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesStockChangeParam extends SECBaseDataEntity implements Serializable
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
	
	//查找日期开始日
	private Timestamp noticeInputDateStart   = null;
	//查找日期结束日
	private Timestamp noticeInputDateEnd     = null;
	
	private long securitiesTypeId = -1;//证券类别ID
	private String[] securitiesIds = null;// 证券名称
	private long clientId = -1;    //业务单位ID
	private String[] stockHolderAccountIds = null; //股东帐户
	
	private long bourseCounterPartId = -1;  //开发营业部ID
	private String[] accountIds = null;//	资金账号

	private String[] fundCounterPartIds = null;  //基金管理公司
	
	//排序方式
	private long desc                       = -1;
	//排序字段
	private long orderField                 = -1;
	//业务状态ID
	private long businessAttributeId        = -1;

	

	/**
	 * @return
	 */
	public String[] getAccountIds() {
		return accountIds;
	}

	/**
	 * @return
	 */
	public long getBourseCounterPartId() {
		return bourseCounterPartId;
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
	public long getClientId() {
		return clientId;
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
	public String[] getFundCounterPartIds() {
		return fundCounterPartIds;
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
	public long getSecuritiesTypeId() {
		return securitiesTypeId;
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
	public void setBourseCounterPartId(long l) {
		bourseCounterPartId = l;
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
	public void setClientId(long l) {
		clientId = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		desc = l;
	}

	/**
	 * @param strings
	 */
	public void setFundCounterPartIds(String[] strings) {
		fundCounterPartIds = strings;
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
	 * @param l
	 */
	public void setSecuritiesTypeId(long l) {
		securitiesTypeId = l;
	}

	/**
	 * @param strings
	 */
	public void setStockHolderAccountIds(String[] strings) {
		stockHolderAccountIds = strings;
	}

}
