/*
 * Created on 2007-04-21
 *
 * Title:				iTreasury
 * @author             	ypxu 
 * Company:             iSoftStone
 * @version				iTreasury3.2����
 * Description:         ��Ʒ��3.2�ڽ���,��������������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���
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
	private long officeID = -1;				//���´�ID
    private long currencyID = -1;			//����ID	
    private long clientID = -1;				//�ͻ�ID
    private long moduleID = -1;				//ģ���ʾ 
	private long transTypeID = -1;			//ҵ�����ͱ�ʶ 
//	private long actionID = -1;				//������ʶ	
	private long approvalID = -1;           //�������ñ�ʶ
	private long userID = -1;				//�û�id,һ�����ڳ�ʼ���������ӿ�
	private String transID = "";				//ҵ���ʶ
	private long approvalEntryID = -1;		//����ʵ��id�����������淵��
	
	private SessionOB sessionMng = null;		//�ڲ�ϵͳ(����\�Ŵ���)session	
	private Map requestMap = null; 	 	//request����,��Ҫ���������������洫��ҵ������map
	private String url = "";					//����url
	private boolean isLastLevel = false;		//�Ƿ����һ������ͨ��
	private boolean isRefuse = false;			//�Ƿ�ܾ���ϵͳ�ھܾ����Ƿ����޸ģ�����Ȼ����¼���˾���ɾ�����������ύ������
	private long remitType = -1;       //��ʽ
	private Object dataEntity=null; 			//����ʵ�� added by mzh_fu 2007/04/23
	private long islowerunit = -1;         //�Ƿ����ù����¼���λ����
	private long nextLevel = -1;          //��������
	
	private String advise="";                   //���� add by keivn(������)2011-05-20
	
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
