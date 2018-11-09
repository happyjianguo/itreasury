package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-8-23
 */
public class ExternalAccountInfo implements Serializable {
	//ID
	private long id = -1;
	//
	private long officeID = -1;
	private long ncurrencyID = -1; //区分币种
	//非财务公司账户号
	private String extAcctNo = "";
	//非财务公司账户名称
	private String extAcctName = "";
	//非财务公司银行名称
	private String bankName = "";
	//省
	private String province = "";
	//市
	private String city = "";
	//外部客户号（用于报表查询）				
	private long clientID = -1;	
	//联行号
	private String spayeebankexchangeno="";
	//CNAPS号
	private String spayeebankcnapsno ="";
	//机构号
	private String spayeebankorgno ="";
	
	
	/**
	 * Returns the bankName.
	 * @return String
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Returns the city.
	 * @return String
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * Returns the extAcctName.
	 * @return String
	 */
	public String getExtAcctName() {
		return extAcctName;
	}

	/**
	 * Returns the extAcctNo.
	 * @return String
	 */
	public String getExtAcctNo() {
		return extAcctNo;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return officeID;
	}

	/**
	 * Returns the province.
	 * @return String
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Sets the bankName.
	 * @param bankName The bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Sets the city.
	 * @param city The city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	/**
	 * Sets the extAcctName.
	 * @param extAcctName The extAcctName to set
	 */
	public void setExtAcctName(String extAcctName) {
		this.extAcctName = extAcctName;
	}

	/**
	 * Sets the extAcctNo.
	 * @param extAcctNo The extAcctNo to set
	 */
	public void setExtAcctNo(String extAcctNo) {
		this.extAcctNo = extAcctNo;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	/**
	 * Sets the province.
	 * @param province The province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	public long getNcurrencyID() {
		return ncurrencyID;
	}

	public void setNcurrencyID(long ncurrencyID) {
		this.ncurrencyID = ncurrencyID;
	}

	public String getSpayeebankexchangeno() {
		return spayeebankexchangeno;
	}

	public void setSpayeebankexchangeno(String spayeebankexchangeno) {
		this.spayeebankexchangeno = spayeebankexchangeno;
	}

	public String getSpayeebankcnapsno() {
		return spayeebankcnapsno;
	}

	public void setSpayeebankcnapsno(String spayeebankcnapsno) {
		this.spayeebankcnapsno = spayeebankcnapsno;
	}

	public String getSpayeebankorgno() {
		return spayeebankorgno;
	}

	public void setSpayeebankorgno(String spayeebankorgno) {
		this.spayeebankorgno = spayeebankorgno;
	}

}
