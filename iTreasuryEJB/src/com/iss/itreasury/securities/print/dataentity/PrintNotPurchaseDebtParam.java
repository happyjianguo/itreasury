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
 * @author wangyi
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintNotPurchaseDebtParam extends SECBaseDataEntity	implements Serializable {
	
    //查找日期开始日
	private Timestamp noticeInputDateStart   = null;
	//查找日期结束日
	private Timestamp noticeInputDateEnd     = null;

	// 业务单位ID
	private long clientId               = -1;
	
	// 股东帐户名称
	private String[] stockHolderAccountIds		= null;

	// 证券类别
	private long securitiesType           = -1;
	
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
	public long getSecuritiesType() {
		return securitiesType;
	}

	/**
	 * @return
	 */
	public String[] getStockHolderAccountIds() {
		return stockHolderAccountIds;
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
	 * @param l
	 */
	public void setSecuritiesType(long l) {
		securitiesType = l;
	}

	/**
	 * @param strings
	 */
	public void setStockHolderAccountIds(String[] strings) {
		stockHolderAccountIds = strings;
	}

}
