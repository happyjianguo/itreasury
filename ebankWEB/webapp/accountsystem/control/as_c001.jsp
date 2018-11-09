<%--
 页面名称 ：as_c001.jsp
 页面功能 : 查询账户关系体系
 作    者 ：jeff
 日    期 ：2008-02-29
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.bizlogic.AccountSystem"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
	AccountSystem accountSystem = new AccountSystem();
	String strTitle = "查询账户关系体系";
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
		
		strTemp = request.getParameter("nUpAccountId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setNUpAccountId(Long.parseLong(strTemp));
		}
		
		strTemp = request.getParameter("nId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setId(Long.parseLong(strTemp));
		}
		
		assInfo.setNOfficeId(sessionMng.m_lOfficeID);
		assInfo.setNCurrencyId(sessionMng.m_lCurrencyID);
		assInfo.setNClientId(sessionMng.m_lClientID);
		
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.QUERY))){
			Collection coll = accountSystem.findAccountSystem(assInfo);
			
			pageInfo.success();
			request.setAttribute("findResultColl",coll);
			assInfo.convertDataEntityToRequest(request);
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.DETAILS))){
			AccountSystemSettingInfo tempInfo = accountSystem.findAccountSystem(assInfo.getId());
			
			pageInfo.success();
			request.setAttribute("findResultInfo",tempInfo);
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
