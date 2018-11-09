/*
 * Created on 2007-4-16
 *
 * Title:				iTreasury
 * @author             	ypxu 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */
package com.iss.itreasury.ebank.approval.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class InutApprovalRelationInfo extends ITreasuryBaseDataEntity 
{
	private long id = -1;          			//主键
	private long officeID = -1;				//办事处ID
    private long currencyID = -1;			//币种ID	
    private long clientID = -1;				//客户ID
    private long moduleID = -1;				//模块标示 
	private long transTypeID = -1;			//业务类型标识 
	private long actionID = -1;				//操作标识	
	private long approvalID = -1;           //审批设置标识
	private long islowerunit = -1;         //是否设置关联下级单位审批
	private long issendtoupclient = -1;		//是否送上级审批，条件：ISLOWERUNIT=2
	
	public long getIslowerunit() {
		return islowerunit;
	}
	public void setIslowerunit(long islowerunit) {
		this.islowerunit = islowerunit;
		putUsedField("islowerunit", islowerunit);
	}
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id", id);
	}
	public long getActionID() 
	{
		return actionID;
	}
	public void setActionID(long actionID) 
	{
		this.actionID = actionID;
		putUsedField("actionID", actionID);
	}
	public long getApprovalID() {
		return approvalID;
	}
	public void setApprovalID(long approvalID) 
	{
		this.approvalID = approvalID;
		putUsedField("approvalID", approvalID);
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) 
	{
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public long getModuleID() 
	{
		return moduleID;
	}
	public void setModuleID(long moduleID) 
	{
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
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
	public long getTransTypeID() 
	{
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) 
	{
		this.transTypeID = transTypeID;
		putUsedField("transTypeID", transTypeID);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public long getIssendtoupclient() {
		return issendtoupclient;
	}
	public void setIssendtoupclient(long issendtoupclient) {
		this.issendtoupclient = issendtoupclient;
		putUsedField("issendtoupclient", issendtoupclient);
	}
}
