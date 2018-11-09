<!--
 页面名称 ：c034.jsp
 页面功能 : 网银-已办任务查询显示页面 - 控制页面
 作    者 ：mingfang
 日    期 ：2007-05-18
 特殊说明 ：
 修改历史 ：
-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.OBInutWorkInfo"%>
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.OBQueryApprovalRecordBiz"%>
<%@ page import="com.iss.system.dao.PageLoader" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%

	String strTitle = "已办任务";
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	String strContinueSave = "";

    try
	{
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		strAction = (String)request.getAttribute("strAction");
        //定义变量
		long lOrderField = -1;
		long transactionTypeID = -1;
		Timestamp dtExecuteFrom = null;
		Timestamp dtExecuteTo = null;
		long lDesc=-1;
		String strTemp = null;
		strTemp = (String)request.getAttribute("lOrderField");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lOrderField = Long.valueOf(strTemp).longValue();
		}
		//业务类型
		strTemp = (String)request.getAttribute("transactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			transactionTypeID = Long.valueOf(strTemp).longValue();
		}
		//执行日由
		strTemp = (String)request.getAttribute("dtExecuteFrom");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dtExecuteFrom = DataFormat.getDateTime(strTemp);
		}
		//执行日到
		strTemp = (String)request.getAttribute("dtExecuteTo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dtExecuteTo = DataFormat.getDateTime(strTemp);
		}
		
		strTemp = (String)request.getAttribute("lDesc");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDesc = Long.valueOf(strTemp).longValue();
		}
        OBInutWorkInfo conditionworkInfo = null;
		PageLoader pageLoader = null;
		String strPageLoaderKey = "";
		OBQueryApprovalRecordBiz biz=new OBQueryApprovalRecordBiz();
	    
			conditionworkInfo = new OBInutWorkInfo();
			//设值
			conditionworkInfo.setOfficeID(sessionMng.m_lOfficeID);
			conditionworkInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			conditionworkInfo.setClientID(sessionMng.m_lClientID);
			conditionworkInfo.setUserID(sessionMng.m_lUserID);
			
			//页面查询条件
			conditionworkInfo.setTransactionTypeID(transactionTypeID);
			conditionworkInfo.setDtExecuteFrom(dtExecuteFrom);
			conditionworkInfo.setDtExecuteTo(dtExecuteTo);
			
			//conditionworkInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
			 
			pageLoader = biz.getHistoryList(conditionworkInfo,lDesc,lOrderField);
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			//
			sessionMng.setQueryCondition(strPageLoaderKey,conditionworkInfo);	
		
		
		//回写v页面
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		request.setAttribute("transactionTypeID",String.valueOf(transactionTypeID));
		request.setAttribute("dtExecuteFrom",DataFormat.formatDate(dtExecuteFrom));
		request.setAttribute("dtExecuteTo",DataFormat.formatDate(dtExecuteTo));
		request.setAttribute("lDesc",String.valueOf(lDesc));
		request.setAttribute("strSuccessPageURL","/approval/view/v034.jsp");
		request.setAttribute("strFailPageURL","/approval/view/v034.jsp");
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = strContext + "/pagecontrol.jsp";
	Log.print("strNextPageURL :  " + strNextPageURL);


	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
	rd.forward( request,response );
%>