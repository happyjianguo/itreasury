<%--
 页面名称 ：c001.jsp
 页面功能 : 资金计划申报新增前判断是否可以新增
 作    者 ：ylguo
 日    期 ：2008-10-23
 特殊说明 ：
 修改历史 ：
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				java.sql.Timestamp,
				com.iss.itreasury.ebank.fundplan.bizlogic.*,
				com.iss.itreasury.ebank.fundplan.bizlogic.Sett_CapitalPlanSettingBiz"%>
				
<%@ page import="com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo"%>
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
		JSPLogger.info("*******进入页面--\\iTreasury-ebank\\fundplan\\control\\c001.jsp*******");
				
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
        
        //主表信息
		Timestamp startDateTime = DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime());
		Timestamp endDateTime = DataFormat.getNextDate(startDateTime, 4);
		String strStartDate = DataFormat.formatDate(startDateTime, DataFormat.FMT_DATE_YYYYMMDD);
		String strEndDate = DataFormat.formatDate(endDateTime, DataFormat.FMT_DATE_YYYYMMDD);
        
       	AllCapitalPlanBiz planBiz = new AllCapitalPlanBiz();
       	
       	//判断下一周该客户是否已存在资金计划
		if(planBiz.isExistCapitalPlan(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,startDateTime,endDateTime))
		{
			sessionMng.getActionMessages().addMessage(strStartDate + " 至 " + strEndDate + " 已存在资金计划");
			pageCtrlInfo.fail();
			
		}
		else
		{
			CapitalPlanInfo planInfo = new CapitalPlanInfo();
			
			planInfo.setId(-1);
			planInfo.setClientId(sessionMng.m_lClientID);
			planInfo.setClientCode(sessionMng.m_strClientCode);
			planInfo.setClientName(sessionMng.m_strClientName);
			planInfo.setOfficeId(sessionMng.m_lOfficeID);
			planInfo.setCurrencyId(sessionMng.m_lCurrencyID);			
			planInfo.setStartdate(startDateTime);
			planInfo.setEnddate(endDateTime);
			planInfo.setCpCode(planBiz.createCapitalPlanCode(sessionMng.m_strClientCode, startDateTime));
			
			//获得当前申报正在使用的模板编号
			long modelId = planBiz.getPlanModelIdInUse(OBConstant.CapitalPlanModelType.PLAN_DECLARE,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
				
			planInfo.setModelId(modelId);
			
			request.setAttribute("capitalPlanInfo", planInfo);	
			request.setAttribute("isAutoCheck",new Boolean(new Sett_CapitalPlanSettingBiz().isAutoCheck(sessionMng.m_lClientID)));	
				
			pageCtrlInfo.success();					
		}					
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