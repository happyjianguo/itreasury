<%--
/*
 * 程序名称：S845-Ctr-Fix.jsp
 * 功能说明：签认金额设置提交控制页面（新奥--定期）
 * 作　　者：周翔
 * 完成日期：2011年4月15日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "定期签认金额设置";
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
	
%>

<!--Get Data start-->
<%
   		/* 定义对表单的相应变量 */
		double dAmount1 = 0.00; // 金额一
    	double dAmount2 = 0.00; // 金额二
    	double dAmount3 = 0.00; // 金额三
    	long lSignUserID1 = -1; // 指定签认人一
    	long lSignUserID2 = -1; // 指定签认人二
    	long lSignUserID3 = -1; // 指定签认人三

		/* 从FORM表单中获取相应数据 */
		/* 金额一 */
		if((request.getParameter("sAmount1") != null) && !((String)request.getParameter("sAmount1")).equals(""))
		{
			dAmount1 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount1"))).doubleValue(); // 金额一
			Log.print("金额一=" + dAmount1);
		}
		/* 金额二 */
		if((request.getParameter("sAmount2") != null) && !((String)request.getParameter("sAmount2")).equals(""))
		{
			dAmount2 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount2"))).doubleValue(); // 金额二
			Log.print("金额二=" + dAmount2);
		}
		/* 金额三 */
		if((request.getParameter("sAmount3") != null) && !((String)request.getParameter("sAmount3")).equals(""))
		{
			dAmount3 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount3"))).doubleValue(); // 金额三
			Log.print("金额三=" + dAmount3);
		}
		/* 指定签认人一 */
		if((request.getParameter("nSignUserID1") != null) && !((String)request.getParameter("nSignUserID1")).equals(""))
		{
			lSignUserID1 = Long.parseLong((String)request.getParameter("nSignUserID1")); // 指定签认人一
			Log.print("指定签认人一=" + lSignUserID1);
		}
		/* 指定签认人二 */
		if((request.getParameter("nSignUserID2") != null) && !((String)request.getParameter("nSignUserID2")).equals(""))
		{
			lSignUserID2 = Long.parseLong((String)request.getParameter("nSignUserID2")); // 指定签认人二
			Log.print("指定签认人二=" + lSignUserID2);
		}
		/* 指定签认人三 */
		if((request.getParameter("nSignUserID3") != null) && !((String)request.getParameter("nSignUserID3")).equals(""))
		{
			lSignUserID3 = Long.parseLong((String)request.getParameter("nSignUserID3")); // 指定签认人三
			Log.print("指定签认人三=" + lSignUserID3);
		}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set SignAmountInfo Attribute start-->
<%
		/* 初始化信息类 */
		SignAmountInfo signAmountInfo = new SignAmountInfo();

		/* 从FORM表单中获取的相应数据 */
		signAmountInfo.setAmount1( dAmount1 ); // 金额一
		signAmountInfo.setAmount2( dAmount2 ); // 金额二
		signAmountInfo.setAmount3( dAmount3 ); // 金额三
   		signAmountInfo.setSignUserID1( lSignUserID1 ); // 指定签认人一
		signAmountInfo.setSignUserID2( lSignUserID2 ); // 指定签认人二
		signAmountInfo.setSignUserID3( lSignUserID3 ); // 指定签认人三

		/* 从session中获取相应数据 */
		signAmountInfo.setClientID( sessionMng.m_lClientID );
		signAmountInfo.setCurrencyID( sessionMng.m_lCurrencyID ); 
		signAmountInfo.setInputUserID( sessionMng.m_lUserID );
%>
<!--Set SignAmountInfo Attribute end-->

<!--Access DB start-->
<%
		/* 提交返回结果 */
		long lAddResult = -1;
		
		/* 初始化EJB */
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();
		
		/* 调用EJB方法提交信息 */
		lAddResult = obSystem.addSignAmountForFix(signAmountInfo);
	
%>
<!--Access DB end>

<!--Forward start-->
<%
		if (lAddResult >=  0)
		{
		    /* 获取上下文环境 */
		    //ServletContext sc = getServletContext();
		    /* 设置返回地址 */
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/S858-Opt-Fix.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    /* forward到结果页面 */
		    rd.forward(request, response);
		} 
		else 
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
			return;
		}
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}	
%>
<!--Forward end-->