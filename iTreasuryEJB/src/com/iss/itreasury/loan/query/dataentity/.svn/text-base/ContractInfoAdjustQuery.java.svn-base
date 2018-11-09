/**
 * ContractInfoAdjustQuery.java
 * Created on 2008年6月19日
 */
package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

/**
 * 合同利率调整查询
 * @author 马现福
 * 2008-6-19
 * @version 
 */
public class ContractInfoAdjustQuery extends Object implements java.io.Serializable {

	/** Creates new ContractInfoAdjustQuery */
	public static final long TX=1;//贴现
	public static final long LOAN=2;//信贷
	public static final long ZTX=3;//自营贴现
	public static final long DB=4;//担保
	
	public ContractInfoAdjustQuery() {
		super();
	}
	/**
	 * 查询条件 合同编号、放款通知单开始日期由、放款通知单开始日期到
	 */
	//合同编号
	private String ContractCode="";    	
	//放款通知单开始日期由
	private String minStartDate=null;	
	//放款通知单开始日期到
	private String maxStartDate=null;
	//办事处
	private long officeID  = -1;
	//币种
	private long currencyID = -1;	
	//是否循环
	private long isCircle=-1;	
	//查询级别
	private String queryLevel="";	
	//查询目的		
	public long queryPurpose=LOAN;
	//排序字段
	private long orderParam=-1;	
	//desc
	private long desc=-1;
	private long ID = -1 ; //放款通知单标识ID
	private long ContractID = -1 ; //合同ID
	private long DrawNoticeID = -1 ; //银团提款通知单标示
	
	/**
	 * 查询结果
	 */
	private Timestamp DtoutDate;//放款开始日期
	private String Code;//客户编号
	private String Name;//客户名称
	private String SContractCode;//合同编号
	private String SCode;//放款通知单编号
	private long NIsCircle;//是否循环贷款
	private String strIsCircle;//是否循环贷款 1:是 2：否
	private double MAmount;//放款金额
	private double MInterestRate;//执行利率
	private double NIntervalNoticeNum;//放款期限
	
	/**
	 * getter and setter
	 * @return
	 */		
	public String getContractCode() {
		return ContractCode;
	}

	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(long isCircle) {
		this.isCircle = isCircle;
	}

	public String getMaxStartDate() {
		return maxStartDate;
	}

	public void setMaxStartDate(String maxStartDate) {
		this.maxStartDate = maxStartDate;
	}

	public String getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(String minStartDate) {
		this.minStartDate = minStartDate;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public String getQueryLevel() {
		return queryLevel;
	}

	public void setQueryLevel(String queryLevel) {
		this.queryLevel = queryLevel;
	}

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public long getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public Timestamp getDtoutDate() {
		return DtoutDate;
	}

	public void setDtoutDate(Timestamp dtoutDate) {
		DtoutDate = dtoutDate;
	}

	public double getMAmount() {
		return MAmount;
	}

	public void setMAmount(double amount) {
		MAmount = amount;
	}

	public double getMInterestRate() {
		return MInterestRate;
	}

	public void setMInterestRate(double interestRate) {
		MInterestRate = interestRate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getNIntervalNoticeNum() {
		return NIntervalNoticeNum;
	}

	public void setNIntervalNoticeNum(double intervalNoticeNum) {
		NIntervalNoticeNum = intervalNoticeNum;
	}

	public long getNIsCircle() {
		return NIsCircle;
	}

	public void setNIsCircle(long isCircle) {
		NIsCircle = isCircle;
	}

	public String getSCode() {
		return SCode;
	}

	public void setSCode(String code) {
		SCode = code;
	}

	public String getSContractCode() {
		return SContractCode;
	}

	public void setSContractCode(String contractCode) {
		SContractCode = contractCode;
	}

	public String getStrIsCircle() {
		return strIsCircle;
	}

	public void setStrIsCircle(String strIsCircle) {
		this.strIsCircle = strIsCircle;
	}

	public long getContractID() {
		return ContractID;
	}

	public void setContractID(long contractID) {
		ContractID = contractID;
	}

	public long getDrawNoticeID() {
		return DrawNoticeID;
	}

	public void setDrawNoticeID(long drawNoticeID) {
		DrawNoticeID = drawNoticeID;
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}
	
		
}