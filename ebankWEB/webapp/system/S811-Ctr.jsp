<%--
/**
 * 程序名称：S811-Ctr.jsp
 * 功能说明：提交票据查询控制页面
 * 作　　者：
 * 完成日期：
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
                 com.iss.itreasury.ebank.util.*"
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
	String strTitle = "[票据查询]";
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

        //判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>

<!--Get Data start-->
<%
   	/* 定义对表单的相应变量 */
	String strPayerBankNo = ""; // 银行账号
	long lType = -1; // 票据类型
	String strStartSubmit = null; // 申请日期-从
	String strEndSubmit = null; // 申请日期-到
	String lStartVoucherNo = ""; // 票据号-从
	String lEndVoucherNo = ""; // 票据号-到

	try 
	{
		/* 从FORM表单中获取相应数据 */
		/* 客户资料资料 */
		if(request.getParameter("lType") != null)
		{
			lType= Long.parseLong((String)request.getParameter("lType"));// 票据类型
			Log.print("账号=" + lType);	
		}
			Log.print("begin");
		if(!((String)request.getParameter("sStartSubmit")).equals(""))
		{
			strStartSubmit = (String)request.getParameter("sStartSubmit");// 申请日期-从
			Log.print("申请日期-从=" + strStartSubmit);
		}
		if(!((String)request.getParameter("sEndSubmit")).equals(""))
		{
			strEndSubmit = (String)request.getParameter("sEndSubmit");// 申请日期-到
			Log.print("申请日期-从=" + strEndSubmit);
		}
		if(!((String)request.getParameter("sStartVoucherNo")).equals(""))
		{
			lStartVoucherNo = (String)request.getParameter("sStartVoucherNo");// 票据号-从
			Log.print("票据号-从=" + lStartVoucherNo);
		}
		if(!((String)request.getParameter("sEndVoucherNo")).equals(""))
		{
			lEndVoucherNo = (String)request.getParameter("sEndVoucherNo");// 票据号-到
			Log.print("票据号-到=" + lEndVoucherNo);
		}
	} 
	catch(Exception e) 
	{
		OBHtml.showCommonMessage(out,   sessionMng, strTitle, "", 1, "Gen_E001");
		return;
	}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set QueryVoucherForm Attribute start-->
<%
	/* 初始化信息类 */
	QueryVoucherInfo queryVoucherInfo = new QueryVoucherInfo();

	/* 从FORM表单中获取的相应数据 */
	/* 客户资料资料 */
	queryVoucherInfo.setTypeID( lType ); // 票据类型
	queryVoucherInfo.setStartDate( strStartSubmit ); // 申请日期-从
	queryVoucherInfo.setEndDate( strEndSubmit ); // 申请日期-到
	queryVoucherInfo.setStartVoucherNo(lStartVoucherNo ); // 票据号-从
	queryVoucherInfo.setEndVoucherNo( lEndVoucherNo ); // 票据号-到
	
	//从session中获取相应数据 
	queryVoucherInfo.setClientID( sessionMng.m_lClientID );
	queryVoucherInfo.setCurrencyID( sessionMng.m_lCurrencyID );
%>
<!--Set QueryVoucherForm Attribute end-->

<!--Access DB start-->
<%
	/* 初始化查询结果集 */
	Collection cltQvf = null;
	/*初始化查询类*/
	OBSystemDao obSystemDao = new OBSystemDao();
	/* 调用方法获得查询结果 */
	cltQvf = (Collection)obSystemDao.queryVoucher(queryVoucherInfo);
%>
<!--Access DB end>

<!--Forward start-->
<%
	try
	{
		/* 在请求中保存结果对象 */
	    request.setAttribute("cltQvf", cltQvf);
		//request.setAttribute("sPayerBankNo", strPayerBankNo);
		request.setAttribute("queryVoucherInfo", queryVoucherInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S810-Ipt.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward到结果页面 */
	    rd.forward(request, response);
	} 
	catch(Exception e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->