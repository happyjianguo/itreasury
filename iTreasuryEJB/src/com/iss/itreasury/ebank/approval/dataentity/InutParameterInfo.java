/*
 * Created on 2007-04-21
 *
 * Title:				iTreasury
 * @author             	ypxu 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */
package com.iss.itreasury.ebank.approval.dataentity;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.iss.itreasury.util.OBFSWorkflowManager;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.ebank.util.SessionOB;

public class InutParameterInfo extends ITreasuryBaseDataEntity 
{
	private long id = -1;          			
	private long officeID = -1;				//办事处ID
    private long currencyID = -1;			//币种ID	
    private long clientID = -1;				//客户ID
    private long moduleID = -1;				//模块标示 
	private long transTypeID = -1;			//业务类型标识 
//	private long actionID = -1;				//操作标识	
	private long approvalID = -1;           //审批设置标识
	private long userID = -1;				//用户id,一般用于初始化审批流接口
	private String transID = "";				//业务标识
	private long approvalEntryID = -1;		//审批实例id，审批流引擎返回
	
	private SessionOB sessionMng = null;		//内部系统(结算\信贷等)session	
	private Map requestMap = null; 	 	//request对象,主要用于向审批流引擎传递业务数据map
	private String url = "";					//返回url
	private boolean isLastLevel = false;		//是否最后一级审批通过
	private boolean isRefuse = false;			//是否拒绝（系统内拒绝就是返回修改，可由然后由录入人决定删除还是重新提交审批）
	private long remitType = -1;       //汇款方式
	private Object dataEntity=null; 			//数据实体 added by mzh_fu 2007/04/23
	private long islowerunit = -1;         //是否设置关联下级单位审批
	private long nextLevel = -1;          //审批级别
	
	private String advise="";                   //建议 add by keivn(刘连凯)2011-05-20
	
	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise;
	}

	public long getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(long nextLevel) {
		this.nextLevel = nextLevel;
	}

	public long getIslowerunit() {
		return islowerunit;
	}

	public void setIslowerunit(long islowerunit) {
		this.islowerunit = islowerunit;
	}

	public InutParameterInfo()
	{
		super();
	}
	
	public InutParameterInfo(long officeID,long currencyID,long clientID,long moduleID,long transTypeID,long remitType)
	{
		this.officeID = officeID;
		this.currencyID = currencyID;
		this.clientID = clientID;
		this.moduleID = moduleID;
		this.transTypeID = transTypeID;
		this.remitType = remitType;
//		this.actionID = actionID;
	}
	
	


	public void setRequest(HttpServletRequest request) 
	{
		this.requestMap = OBFSWorkflowManager.getRequestAttribute(request);
	}
	public boolean isLastLevel() 
	{
		return isLastLevel;
	}
	public void setLastLevel(boolean isLastLevel) 
	{
		this.isLastLevel = isLastLevel;
	}
	
	public SessionOB getSessionMng() 
	{
		return sessionMng;
	}
	public void setSessionMng(SessionOB sessionMng) 
	{
		this.sessionMng = sessionMng;
		this.officeID = sessionMng.m_lOfficeID;
		this.currencyID = sessionMng.m_lCurrencyID;
		this.clientID = sessionMng.m_lClientID;
		//this.moduleID = sessionMng.m_lModuleID;
		this.userID = sessionMng.m_lUserID;
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
//	public long getActionID() 
//	{
//		return actionID;
//	}
//	public void setActionID(long actionID) 
//	{
//		this.actionID = actionID;
//	}
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
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) 
	{
		this.clientID = clientID;
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
	public long getTransTypeID() 
	{
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) 
	{
		this.transTypeID = transTypeID;
	}

	public Map getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map requestMap) {
		this.requestMap = requestMap;
	}

	public Object getDataEntity() {
		return dataEntity;
	}

	public void setDataEntity(Object dataEntity) {
		this.dataEntity = dataEntity;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public boolean isRefuse() {
		return isRefuse;
	}

	public void setRefuse(boolean isRefuse) {
		this.isRefuse = isRefuse;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}
	
	public long getApprovalEntryID()
	{
	
		return approvalEntryID;
	}

	
	public void setApprovalEntryID(long approvalEntryID)
	{
	
		this.approvalEntryID = approvalEntryID;
	}

	public long getRemitType() {
		return remitType;
	}

	public void setRemitType(long remitType) {
		this.remitType = remitType;
	}
}
