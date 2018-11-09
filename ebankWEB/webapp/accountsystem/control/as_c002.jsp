<%--
 ҳ������ ��as_c002.jsp
 ҳ�湦�� : �����˻���ϵ��ϵ
 ��    �� ��jeff
 ��    �� ��2008-02-28
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingViewInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.bizlogic.AccountSystem"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath() + "/iTreasury-ebank";%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
	AccountSystem accountSystem = new AccountSystem();
	String strTitle = "�����˻���ϵ��ϵ";
	String strTemp = "";

try {

    
		//��¼���
    	if (!sessionMng.isLogin()){
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
    	}
    	//���Ȩ��
    	if (!sessionMng.hasRight(request)){
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
    	}
	 
	 	//��request�еĲ���ת����Bean��
		pageInfo.convertRequestToDataEntity(request);
		assInfo.convertRequestToDataEntity(request);
		
		strTemp = request.getParameter("nUpAccountId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setNUpAccountId(Long.parseLong(strTemp));
		}
		strTemp = request.getParameter("nId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setId(Long.parseLong(strTemp));
		}
		strTemp = request.getParameter("nStatusId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setNStatusId(Long.parseLong(strTemp));
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.SAVE))){
			assInfo.setNOfficeId(sessionMng.m_lOfficeID);
			assInfo.setNCurrencyId(sessionMng.m_lCurrencyID);
			assInfo.setNClientId(sessionMng.m_lClientID);
			assInfo.setNStatusId(AccountSystemConstant.SettingStatus.SAVE);
			assInfo.setDtInputTime(Env.getSystemDateTime());
			assInfo.setNInputUserId(sessionMng.m_lUserID);
			
			////У��
			Collection coll = accountSystem.findAccountSystem(assInfo);
			if(coll != null){
				sessionMng.getActionMessages().addMessage("����ʧ�ܣ����ʻ��ѱ������ʻ���ϵ!");
				pageInfo.fail();
			}
			else {
				long nId = accountSystem.addAccountSystem(assInfo);
				assInfo.setId(nId);
				sessionMng.getActionMessages().addMessage("����ɹ�");
				pageInfo.success();
			}
			
			AccountSystemSettingInfo assInfoTemp = accountSystem.findAccountSystem(assInfo.getId());
			request.setAttribute("findResultInfo",assInfoTemp);
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.MODIFY))){
			AccountSystemSettingInfo assInfoTemp = new AccountSystemSettingInfo();
			assInfoTemp.setId(assInfo.getId());
			assInfoTemp.setSystemName(assInfo.getSystemName());
			assInfoTemp.setDtModifyTime(Env.getSystemDateTime());
			assInfoTemp.setNModifyUserId(sessionMng.m_lUserID);
			if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.SAVE){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.SAVE);
			}else if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.DELETING){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETING);
			}else{
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.MODIFING);
			}
			
			accountSystem.updateAccountSystem(assInfoTemp);
			
			assInfoTemp = accountSystem.findAccountSystem(assInfo.getId());
			request.setAttribute("findResultInfo",assInfoTemp);
			sessionMng.getActionMessages().addMessage("����ɹ�");
			pageInfo.success();
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.DELETE))){
			Collection coll = null;
			AccountSystemSettingInfo assInfoTemp = new AccountSystemSettingInfo();
			assInfoTemp.setId(assInfo.getId());
			assInfoTemp.setSystemName(assInfo.getSystemName());
			assInfoTemp.setDtModifyTime(Env.getSystemDateTime());
			assInfoTemp.setNModifyUserId(sessionMng.m_lUserID);
			
			//��齻�׼�¼
			coll = accountSystem.findTranscurrentDepositColl(assInfo.getId());
			if(coll != null){
				sessionMng.getActionMessages().addMessage("ɾ��ʧ�ܣ����ʻ���ϵ�����ϻ����²��Ľ��׼�¼!");
				pageInfo.fail();
			}
			else {
				//ɾ��ʱ�޸ĸ�״̬
				if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.SAVE){
					assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETED);
				}else{
					assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETING);
				}
				
				//ɾ��ʱ�޸���״̬
				coll = accountSystem.findCollByAccountSystemId(assInfo.getId());
				if(coll != null){
					Iterator iter = coll.iterator();
					AccountRelationSettingInfo arsInfo = null;
					AccountRelationSettingViewInfo arsViewInfo = null;
	    			while(iter.hasNext()){
	    				arsViewInfo = (AccountRelationSettingViewInfo)iter.next();
	    				arsInfo = new AccountRelationSettingInfo();
	    				
	    				arsInfo.setId(arsViewInfo.getId());
	    				if(assInfoTemp.getNStatusId() == AccountSystemConstant.SettingStatus.DELETED){
							arsInfo.setNStatusId(AccountSystemConstant.RelationStatus.DELETED);
						}
						else {
							arsInfo.setNStatusId(AccountSystemConstant.RelationStatus.DELETING);
						}
						accountSystem.updateAccountRelation(arsInfo);
	    			}
	   			}
				
				accountSystem.updateAccountSystem(assInfoTemp);
				sessionMng.getActionMessages().addMessage("ɾ���ɹ�");
				assInfo.convertDataEntityToRequest(request);
				pageInfo.success();
			}
		}
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
