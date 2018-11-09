/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */
package com.iss.itreasury.system.approval.dataentity;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.SessionMng;

public class InutParameterInfo implements Serializable 
{
	private long id = -1;          			
	private long officeID = -1;				//办事处ID
    private long currencyID = -1;			//币种ID	
    private long moduleID = -1;				//模块标示 
	private long transTypeID = -1;			//业务类型标识 
	private long actionID = -1;				//操作标识	
	private long approvalID = -1;           //审批设置标识
	private long userID = -1;				//用户id,一般用于初始化审批流接口和取消审批中的操作人
	private String transID = "";				//业务标识
	private long approvalEntryID = -1;		//审批实例id，审批流引擎返回
	
	private SessionMng sessionMng = null;		//内部系统(结算\信贷等)session	
	private Map requestMap = null; 				//request对象,主要用于向审批流引擎传递业务数据map
	private String url = "";					//返回url
	private boolean isLastLevel = false;		//是否最后一级审批通过
	private boolean isRefuse = false;			//是否拒绝（系统内拒绝就是返回修改，可由然后由录入人决定删除还是重新提交审批）
	
	private String advise="";                   //建议 add by keivn(刘连凯)2011-05-19
	private double amount=0.0;					//金额 add by keivn(刘连凯)2011-05-25
	
	private Object dataEntity=null; 			//数据实体 added by mzh_fu 2007/04/23
	
	public InutParameterInfo()
	{
		super();
	}
	
	public InutParameterInfo(long officeID,long currencyID,long moduleID,long transTypeID,long actionID)
	{
		this.officeID = officeID;
		this.currencyID = currencyID;
		this.moduleID = moduleID;
		this.transTypeID = transTypeID;
		this.actionID = actionID;
	}
	public InutParameterInfo(long officeID,long currencyID,long moduleID,long transTypeID,long actionID,double amount)
	{
		this.officeID = officeID;
		this.currencyID = currencyID;
		this.moduleID = moduleID;
		this.transTypeID = transTypeID;
		this.actionID = actionID;
		this.amount=amount;
	}
		
	public void setSessionMng(SessionMng sessionMng) 
	{
		this.sessionMng = sessionMng;
		this.officeID = sessionMng.m_lOfficeID;
		this.currencyID = sessionMng.m_lCurrencyID;
		this.moduleID = sessionMng.m_lModuleID;
		this.userID = sessionMng.m_lUserID;
	}
	public void setRequest(HttpServletRequest request) 
	{
		this.requestMap = FSWorkflowManager.getRequestAttribute(request);
	}
	
	public SessionMng getSessionMng() 
	{
		return sessionMng;
	}
	public boolean isRefuse() 
	{
		return isRefuse;
	}
	public void setRefuse(boolean isRefuse) 
	{
		this.isRefuse = isRefuse;
	}
	public boolean isLastLevel() 
	{
		return isLastLevel;
	}
	public void setLastLevel(boolean isLastLevel) 
	{
		this.isLastLevel = isLastLevel;
	}
	public Map getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map requestMap) {
		this.requestMap = requestMap;
	}

	public String getUrl() 
	{
		return url;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
	}
	public long getActionID() 
	{
		return actionID;
	}
	public void setActionID(long actionID) 
	{
		this.actionID = actionID;
	}
	public long getApprovalID() {
		return approvalID;
	}
	public void setApprovalID(long approvalID) 
	{
		this.approvalID = approvalID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) 
	{
		this.currencyID = currencyID;
	}
	public long getModuleID() 
	{
		return moduleID;
	}
	public void setModuleID(long moduleID) 
	{
		this.moduleID = moduleID;
	}
	public long getOfficeID() 
	{
		return officeID;
	}
	public void setOfficeID(long officeID) 
	{
		this.officeID = officeID;
	}
	
	public long getUserID() 
	{
		return userID;
	}
	public void setUserID(long userID) 
	{
		this.userID = userID;
	}
	public long getTransTypeID() 
	{
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) 
	{
		this.transTypeID = transTypeID;
	}

	public Object getDataEntity() {
		return dataEntity;
	}

	public void setDataEntity(Object dataEntity) {
		this.dataEntity = dataEntity;
	}

	public long getApprovalEntryID() 
	{
		return approvalEntryID;
	}

	public void setApprovalEntryID(long approvalEntryID) 
	{
		this.approvalEntryID = approvalEntryID;
	}

	public String getTransID() 
	{
		return transID;
	}

	public void setTransID(String transID) 
	{
		this.transID = transID;
	}

	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


}
