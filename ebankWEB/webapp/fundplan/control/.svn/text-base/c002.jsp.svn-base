<%--
 页面名称 ：c002.jsp
 页面功能 : 资金计划申报查询 查询一条申报页面   v001.jsp ————>c002.jsp ————>v004.jsp
 作    者 ：ylguo
 日    期 ：2008-10-23
 特殊说明 ：
 修改历史 ：
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,
com.iss.itreasury.ebank.fundplan.dao.*,
com.iss.itreasury.ebank.fundplan.bizlogic.Sett_CapitalPlanSettingBiz"%>

<%@ page import="com.iss.itreasury.ebank.system.util.JSPLogger"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[资金计划]";
%>
<% String strContext = request.getContextPath(); %>
<%
	PageControllerInfo pageCtrlInfo = new PageControllerInfo();
	
	try
	{
		JSPLogger.info("*******进入页面--\\iTreasury-ebank\\fundplan\\control\\c002.jsp*******");	
	    /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		
        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
        
        pageCtrlInfo.convertRequestToDataEntity(request);
        
        String strTemp = null;
	    //子表中的字段CapitalplanId
	    long capitalplanId = -1;
	    strTemp = request.getParameter("capitalPlanId");
	    if(strTemp != null && strTemp.length()>0)
	    {
			capitalplanId = Long.parseLong(strTemp);
	    }
	     		
 		if(capitalplanId>0)
 		{
	 		CapitalPlanInfo capitalplanInfo = null; 		
	        //主表信息
			CapitalPlanDao planDAO = new CapitalPlanDao();
			capitalplanInfo 
				= (CapitalPlanInfo)planDAO.findByID(capitalplanId, CapitalPlanInfo.class);
				
		request.setAttribute("capitalPlanInfo", capitalplanInfo);	
		request.setAttribute("isAutoCheck",new Boolean(new Sett_CapitalPlanSettingBiz().isAutoCheck(sessionMng.m_lClientID)));	
				
		}else{
			throw new IException("资金计划申报无效");
		}
				
		pageCtrlInfo.success();
	}
	catch(IException exp)
	{
    	sessionMng.getActionMessages().addMessage(exp.getMessage());
    	exp.printStackTrace();
 		pageCtrlInfo.fail();
    }
    
   /* 设置返回地址 */
	String nextURL = pageCtrlInfo.getP_NextPageURL();
	JSPLogger.info("进入下一页面："+nextURL);		
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward到结果页面 */
	rd.forward(request, response);	      
%>