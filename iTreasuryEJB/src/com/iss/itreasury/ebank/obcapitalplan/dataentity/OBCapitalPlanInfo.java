package com.iss.itreasury.ebank.obcapitalplan.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OBCapitalPlanInfo extends ITreasuryBaseDataEntity
{

	private long id = -1;//主健ID
	private long clientID = -1;//客户ID
	private long accountID = -1; //账户ID
	private long periodID=-1;//计划周期ID
	private long period=-1;//计划周期
	private double payAmount = 0.0;//支出金额
	private double receivAmount = 0.0;//收入金额
	private long currencyID = -1;//币种ID
	private long officeID = -1;//办事处ID
	private long checkUserID = -1;//复核人ID
	private Timestamp PeriodStartDate = null;//周期开始日期
	private Timestamp startDate = null;//开始日期
	private Timestamp endDate = null;//结束日期
	private Timestamp checkDate = null;//复核日期
	private long modifyUserID = -1;//修改人
	private Timestamp modifyDate = null;//录入时间
	private long inputUserID = -1;//录入人
	private Timestamp inputDate = null;//录入时间
	private long statusID = -1;//状态ID
	
	//非数据库字段，用来查询（暂定）
	
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
		putUsedField("accountID", accountID);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
		putUsedField("payAmount", payAmount);
	}
	public double getReceivAmount() {
		return receivAmount;
	}
	public void setReceivAmount(double receivAmount) {
		this.receivAmount = receivAmount;
		putUsedField("receivAmount", receivAmount);
	}
	
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	public long getPeriodID() {
		return periodID;
	}
	public void setPeriodID(long periodID) {
		this.periodID = periodID;
		putUsedField("periodID", periodID);
		
	}
	public long getCheckUserID()
	{
		return checkUserID;
	}
	public void setCheckUserID(long checkUserID)
	{
		this.checkUserID = checkUserID;
		putUsedField("checkUserID", checkUserID);
	}
	public Timestamp getCheckDate()
	{
		return checkDate;
	}
	public void setCheckUserDate(Timestamp checkDate)
	{
		this.checkDate = checkDate;
		putUsedField("checkDate", checkDate);
	}
	public long getCurrencyID()
	{
		return currencyID;
	}
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public Timestamp getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
		putUsedField("endDate", endDate);
	}
	public Timestamp getInputDate()
	{
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}
	public void setModifyDate(Timestamp modifyDate)
	{
		this.modifyDate = modifyDate;
		putUsedField("modifyDate", modifyDate);
	}
	public long getModifyUserID()
	{
		return modifyUserID;
	}
	public void setModifyUserID(long modifyUserID)
	{
		this.modifyUserID = modifyUserID;
		putUsedField("modifyUserID", modifyUserID);
	}
	public long getOfficeID()
	{
		return officeID;
	}
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	public Timestamp getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Timestamp startDate)
	{
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}
	public long getPeriod()
	{
		return period;
	}
	public void setPeriod(long period)
	{
		this.period = period;
	}
	public Timestamp getPeriodStartDate()
	{
		return PeriodStartDate;
	}
	public void setPeriodStartDate(Timestamp periodStartDate)
	{
		PeriodStartDate = periodStartDate;
	}
	
}
