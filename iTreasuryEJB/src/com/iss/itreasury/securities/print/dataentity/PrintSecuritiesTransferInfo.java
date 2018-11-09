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
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PrintSecuritiesTransferInfo extends SECBaseDataEntity	implements Serializable {

	//交易编号
	private String code          = "";
	//成交日期
	private Timestamp transactionDate   = null;
	
	//转入资料--业务单位id
	private long inClientID          = -1;
	//转入资料--开户营业部id
	private long inCounterPartId = -1; 	
	//转入资料--资金帐号id
	private long inAccountId    = -1;
	//转入资料--证券id
	private long inSecuritiesId        = -1;

	
	//转出资料--业务单位id
	private long outClientID          = -1;
	//转出资料--开户营业部id
	private long outCounterPartId = -1; 	
	//转出资料--资金帐号id
	private long outAccountId    = -1;
	//转出资料--证券id
	private long outSecuritiesId        = -1;

	
	//划转数量
	private double quantity     = 0.0;	
	//划转证券成本
	private double price  = 0.0;
	//状态
	private long statusID     = -1;
	//录入人
	private long userId     = -1;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
	
	}
	

	/**
	 * @return
	 */
	public String getCode() {
		return code;
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
	public long getStatusID() {
		return statusID;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
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
	public void setStatusID(long l) {
		statusID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionDate(Timestamp timestamp) {
		transactionDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getInAccountId() {
		return inAccountId;
	}

	/**
	 * @return
	 */
	public long getInClientID() {
		return inClientID;
	}

	/**
	 * @return
	 */
	public long getInCounterPartId() {
		return inCounterPartId;
	}

	/**
	 * @return
	 */
	public long getInSecuritiesId() {
		return inSecuritiesId;
	}

	/**
	 * @return
	 */
	public long getOutAccountId() {
		return outAccountId;
	}

	/**
	 * @return
	 */
	public long getOutClientID() {
		return outClientID;
	}

	/**
	 * @return
	 */
	public long getOutCounterPartId() {
		return outCounterPartId;
	}

	/**
	 * @return
	 */
	public long getOutSecuritiesId() {
		return outSecuritiesId;
	}

	/**
	 * @param l
	 */
	public void setInAccountId(long l) {
		inAccountId = l;
	}

	/**
	 * @param l
	 */
	public void setInClientID(long l) {
		inClientID = l;
	}

	/**
	 * @param l
	 */
	public void setInCounterPartId(long l) {
		inCounterPartId = l;
	}

	/**
	 * @param l
	 */
	public void setInSecuritiesId(long l) {
		inSecuritiesId = l;
	}

	/**
	 * @param l
	 */
	public void setOutAccountId(long l) {
		outAccountId = l;
	}

	/**
	 * @param l
	 */
	public void setOutClientID(long l) {
		outClientID = l;
	}

	/**
	 * @param l
	 */
	public void setOutCounterPartId(long l) {
		outCounterPartId = l;
	}

	/**
	 * @param l
	 */
	public void setOutSecuritiesId(long l) {
		outSecuritiesId = l;
	}

	/**
	 * @return
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param l
	 */
	public void setUserId(long l) {
		userId = l;
	}

}
