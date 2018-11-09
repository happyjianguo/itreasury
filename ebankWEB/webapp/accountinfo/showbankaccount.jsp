<%--
 页面名称 ：showbankaccount.jsp
 页面功能 : 查询银行账户余额
 作    者 ：zcwang
 日    期 ：2008-4-24
 特殊说明 ：实现操作说明：
				1、链接查找
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	
    try
	{
			/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	String strNextPageURL = "";
	String strTemp = null;	
	String strAction = "";											//获得参数中转变量
	strTemp = (String)request.getAttribute("action");						//记录的ID
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		strAction = strTemp;
	}
	String indexUrl = request.getContextPath();
	if(strAction.equals("bankacount"))
	{
		//strNextPageURL =indexUrl+ "/bankportal/v090-account_ebank.jsp?moduleid="+sessionMng.m_lModuleID+"&clientid="+sessionMng.m_lClientID+"&officeid="+sessionMng.m_lOfficeID;
		strNextPageURL =indexUrl+ "/bankportal/v090-account_ebank.jsp";
	}
	else if(strAction.equals("subbankacount"))
	{
		//strNextPageURL =indexUrl+ "/bankportal/v090-subaccount_ebank.jsp?moduleid="+sessionMng.m_lModuleID+"&clientid="+sessionMng.m_lClientID+"&officeid="+sessionMng.m_lOfficeID;
		strNextPageURL =indexUrl+ "/bankportal/v090-subaccount_ebank.jsp";
	}
	Log.print("Next Page URL:"+strNextPageURL);	
	response.sendRedirect(strNextPageURL);
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
	}	
%>