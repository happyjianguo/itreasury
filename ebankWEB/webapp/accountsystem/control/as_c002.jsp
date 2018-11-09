<%--
 页面名称 ：as_c002.jsp
 页面功能 : 保存账户关系体系
 作    者 ：jeff
 日    期 ：2008-02-28
 特殊说明 ：
 实现操作说明：
 修改历史 ：
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
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath() + "/iTreasury-ebank";%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
	AccountSystem accountSystem = new AccountSystem();
	String strTitle = "保存账户关系体系";
	String strTemp = "";

try {

    
		//登录检测
    	if (!sessionMng.isLogin()){
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
    	}
    	//检测权限
    	if (!sessionMng.hasRight(request)){
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
    	}
	 
	 	//将request中的参数转换到Bean中
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
			
			////校验
			Collection coll = accountSystem.findAccountSystem(assInfo);
			if(coll != null){
				sessionMng.getActionMessages().addMessage("保存失败，此帐户已被纳入帐户体系!");
				pageInfo.fail();
			}
			else {
				long nId = accountSystem.addAccountSystem(assInfo);
				assInfo.setId(nId);
				sessionMng.getActionMessages().addMessage("保存成功");
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
			sessionMng.getActionMessages().addMessage("保存成功");
			pageInfo.success();
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.DELETE))){
			Collection coll = null;
			AccountSystemSettingInfo assInfoTemp = new AccountSystemSettingInfo();
			assInfoTemp.setId(assInfo.getId());
			assInfoTemp.setSystemName(assInfo.getSystemName());
			assInfoTemp.setDtModifyTime(Env.getSystemDateTime());
			assInfoTemp.setNModifyUserId(sessionMng.m_lUserID);
			
			//检查交易记录
			coll = accountSystem.findTranscurrentDepositColl(assInfo.getId());
			if(coll != null){
				sessionMng.getActionMessages().addMessage("删除失败，此帐户体系存在上划、下拨的交易记录!");
				pageInfo.fail();
			}
			else {
				//删除时修改父状态
				if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.SAVE){
					assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETED);
				}else{
					assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETING);
				}
				
				//删除时修改子状态
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
				sessionMng.getActionMessages().addMessage("删除成功");
				assInfo.convertDataEntityToRequest(request);
				pageInfo.success();
			}
		}
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
