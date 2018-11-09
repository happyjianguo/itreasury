/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.sql.Timestamp;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractQueryInfo extends SECBaseDataEntity{

	private long id = -1;
	private long startContractId=-1;
	private long transactionTypeId=-1;
	private long endContractId=-1;
	private long counterpartId = -1;	
	private double startAmount = 0;
	private double endAmount = 0;
	private Timestamp startBeginDate=null;
	private Timestamp endBeginDate=null;
	private Timestamp startEndDate=null;
	private Timestamp endEndDate=null;
	private long statusId=-1;
	private long intervalNum=-1;
	private long settlementTypeId=-1;
	private long interestTypeId=-1;				
	private double commissionChargeRate=0;		//手续费率
	private double beginBoldScale=0;			//债券总规模
	private double endBoldScale=0;

	private Timestamp startInputDate=null;
	private Timestamp endInputDate=null;
	private Timestamp startTransactionDate=null;
	private Timestamp endTransactionDate=null;
	private long pageLineCount = -1;
	private long pageNo = -1;
	private long orderParam = -1;
	private long desc = -1;
	private long userId = -1;
	private long inputUserId = -1;
	private long nextCheckUserId = -1;
	private long queryPurpose = -1;	// 1:for modify 2:for examine
	private String orderParamString = "";
	
	private long officeId = -1;
	private long currencyId = -1;
	private long clientId=-1;
	private long planStatusId = -1;
	
	/*boxu add 2006-09-19*/
	private double startcostAmount = 0;  //回购成本开始
	private double endcostAmount = 0;  //回购成本结束
	private long startterm = -1;  //开始期限
	private long endterm = -1;  //结束期限
	private double startincomeRate = 0;  //开始回购收益率
	private double endincomeRate = 0;  //结束回购收益率
	
	/*boxu add 2007-1-9*/
	private String contractCodeFrom = "";  //合同编号开始
	private String contractCodeTo = "";  //合同编号结束
	
	/**
	 * @return Returns the contractCodeFrom.
	 */
	public String getContractCodeFrom() {
		return contractCodeFrom;
	}
	/**
	 * @param contractCodeFrom The contractCodeFrom to set.
	 */
	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}
	/**
	 * @return Returns the contractCodeTo.
	 */
	public String getContractCodeTo() {
		return contractCodeTo;
	}
	/**
	 * @param contractCodeTo The contractCodeTo to set.
	 */
	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}
	/**
	 * @return Returns the endcostAmount.
	 */
	public double getEndcostAmount() {
		return endcostAmount;
	}
	/**
	 * @param endcostAmount The endcostAmount to set.
	 */
	public void setEndcostAmount(double endcostAmount) {
		this.endcostAmount = endcostAmount;
	}
	/**
	 * @return Returns the endincomeRate.
	 */
	public double getEndincomeRate() {
		return endincomeRate;
	}
	/**
	 * @param endincomeRate The endincomeRate to set.
	 */
	public void setEndincomeRate(double endincomeRate) {
		this.endincomeRate = endincomeRate;
	}
	/**
	 * @return Returns the endterm.
	 */
	public long getEndterm() {
		return endterm;
	}
	/**
	 * @param endterm The endterm to set.
	 */
	public void setEndterm(long endterm) {
		this.endterm = endterm;
	}
	/**
	 * @return Returns the startcostAmount.
	 */
	public double getStartcostAmount() {
		return startcostAmount;
	}
	/**
	 * @param startcostAmount The startcostAmount to set.
	 */
	public void setStartcostAmount(double startcostAmount) {
		this.startcostAmount = startcostAmount;
	}
	/**
	 * @return Returns the startincomeRate.
	 */
	public double getStartincomeRate() {
		return startincomeRate;
	}
	/**
	 * @param startincomeRate The startincomeRate to set.
	 */
	public void setStartincomeRate(double startincomeRate) {
		this.startincomeRate = startincomeRate;
	}
	/**
	 * @return Returns the startterm.
	 */
	public long getStartterm() {
		return startterm;
	}
	/**
	 * @param startterm The startterm to set.
	 */
	public void setStartterm(long startterm) {
		this.startterm = startterm;
	}
	/**
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l) {
		id = l;
	}

	/**
	 * @return
	 */
	public double getCommissionChargeRate()
	{
		return commissionChargeRate;
	}

	/**
	 * @return
	 */
	public long getCounterpartId()
	{
		return counterpartId;
	}

	/**
	 * @return
	 */
	public long getCurrencyId()
	{
		return currencyId;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public double getEndAmount()
	{
		return endAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getEndBeginDate()
	{
		return endBeginDate;
	}

	/**
	 * @return
	 */
	public long getEndContractId()
	{
		return endContractId;
	}

	/**
	 * @return
	 */
	public Timestamp getEndEndDate()
	{
		return endEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getEndInputDate()
	{
		return endInputDate;
	}

	/**
	 * @return
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}

	/**
	 * @return
	 */
	public long getInterestTypeId()
	{
		return interestTypeId;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return intervalNum;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserId()
	{
		return nextCheckUserId;
	}

	/**
	 * @return
	 */
	public long getOfficeId()
	{
		return officeId;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}

	/**
	 * @return
	 */
	public String getOrderParamString()
	{
		return orderParamString;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return pageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return pageNo;
	}

	/**
	 * @return
	 */
	public long getQueryPurpose()
	{
		return queryPurpose;
	}

	/**
	 * @return
	 */
	public long getSettlementTypeId()
	{
		return settlementTypeId;
	}

	/**
	 * @return
	 */
	public double getStartAmount()
	{
		return startAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getStartBeginDate()
	{
		return startBeginDate;
	}

	/**
	 * @return
	 */
	public long getStartContractId()
	{
		return startContractId;
	}

	/**
	 * @return
	 */
	public Timestamp getStartEndDate()
	{
		return startEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartInputDate()
	{
		return startInputDate;
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @return
	 */
	public long getUserId()
	{
		return userId;
	}

	/**
	 * @param d
	 */
	public void setCommissionChargeRate(double d)
	{
		commissionChargeRate = d;
	}

	/**
	 * @param l
	 */
	public void setCounterpartId(long l)
	{
		counterpartId = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyId(long l)
	{
		currencyId = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}

	/**
	 * @param d
	 */
	public void setEndAmount(double d)
	{
		endAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setEndBeginDate(Timestamp timestamp)
	{
		endBeginDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setEndContractId(long l)
	{
		endContractId = l;
	}

	/**
	 * @param timestamp
	 */
	public void setEndEndDate(Timestamp timestamp)
	{
		endEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setEndInputDate(Timestamp timestamp)
	{
		endInputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserId(long l)
	{
		inputUserId = l;
	}

	/**
	 * @param l
	 */
	public void setInterestTypeId(long l)
	{
		interestTypeId = l;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		intervalNum = l;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserId(long l)
	{
		nextCheckUserId = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeId(long l)
	{
		officeId = l;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @param string
	 */
	public void setOrderParamString(String string)
	{
		orderParamString = string;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		pageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		pageNo = l;
	}

	/**
	 * @param l
	 */
	public void setQueryPurpose(long l)
	{
		queryPurpose = l;
	}

	/**
	 * @param l
	 */
	public void setSettlementTypeId(long l)
	{
		settlementTypeId = l;
	}

	/**
	 * @param d
	 */
	public void setStartAmount(double d)
	{
		startAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setStartBeginDate(Timestamp timestamp)
	{
		startBeginDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStartContractId(long l)
	{
		startContractId = l;
	}

	/**
	 * @param timestamp
	 */
	public void setStartEndDate(Timestamp timestamp)
	{
		startEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setStartInputDate(Timestamp timestamp)
	{
		startInputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
	}

	/**
	 * @param l
	 */
	public void setUserId(long l)
	{
		userId = l;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l)
	{
		transactionTypeId = l;
	}

	/**
	 * @return
	 */
	public long getClientId()
	{
		return clientId;
	}

	/**
	 * @param l
	 */
	public void setClientId(long l)
	{
		clientId = l;
	}

	/**
	 * @return
	 */
	public double getEndBoldScale()
	{
		return endBoldScale;
	}

	/**
	 * @return
	 */
	public double getBeginBoldScale()
	{
		return beginBoldScale;
	}

	/**
	 * @param d
	 */
	public void setEndBoldScale(double d)
	{
		endBoldScale = d;
	}

	/**
	 * @param d
	 */
	public void setBeginBoldScale(double d)
	{
		beginBoldScale = d;
	}

	/**
	 * @return
	 */
	public Timestamp getEndTransactionDate()
	{
		return endTransactionDate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartTransactionDate()
	{
		return startTransactionDate;
	}

	/**
	 * @param timestamp
	 */
	public void setEndTransactionDate(Timestamp timestamp)
	{
		endTransactionDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setStartTransactionDate(Timestamp timestamp)
	{
		startTransactionDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getPlanStatusId()
	{
		return planStatusId;
	}

	/**
	 * @param l
	 */
	public void setPlanStatusId(long l)
	{
		planStatusId = l;
	}

}
