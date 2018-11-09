<%--
/*
 * 程序名称：C412.jsp
 * 功能说明：业务复核查询控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月23日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>

<%
	/* 用户登录检测与权限校验 */
	
	String strStartDate = null;//上一个页面传来的开始日期
	String strEndDate = null;//上个页面传来的结束日期
	String sign = "";
	
	try 
	{
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
%>

<%
		//定义接收Form信息的变量
		//定义初始化查询信息对象
    	QueryCapForm qcf=new QueryCapForm();
	
		request.setAttribute("billstatusid",request.getParameter("SelectType"));
    	qcf.setTransType(GetNumParam(request,"SelectType") ); // 网上银行交易查询类型
		Log.print("SelectType:   "+qcf.getTransType());
	     
		qcf.setStartSubmit ( GetParam(request,"txtConfirmA") ); // 提交日期-从
		Log.print("txtStartSubmit:   "+qcf.getStartSubmit());
	      
		qcf.setEndSubmit ( GetParam(request,"txtConfirmB") ); // 提交日期-到
		Log.print("txtEndSubmit:   "+qcf.getEndSubmit());
		
		strStartDate = (String)request.getParameter("txtConfirmA");
		strEndDate = (String)request.getParameter("txtConfirmB");
		
		qcf.setStatus ( GetNumParam(request,"SelectStatus") );// 交易指令状态
		Log.print("SelectStatus:   "+qcf.getStatus());
		
		if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase("")))
		{
			qcf.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMinAmount").trim())) );// 交易金额-最小值
			Log.print("MinAmount:   "+qcf.getMinAmount());
		}	

		if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase("")))
		{
			qcf.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMaxAmount").trim())) );// 交易金额-最大值
			Log.print("MaxAmount:   "+qcf.getMaxAmount());
		}	
		
		qcf.setStartExe ( GetParam(request,"txtExecuteA") ); // 执行日期-从
		Log.print("StartExe :   "+qcf.getStartExe ());
	
		qcf.setEndExe ( GetParam(request,"txtExecuteB") ); // 执行日期-到
		Log.print("EndExe :   "+qcf.getEndExe ());      
		
        if (request.getParameter("sign") != null && request.getParameter("sign").trim().length() > 0) {
        	sign = (String)request.getParameter("sign");
        	qcf.setSign(sign);
        }  
	   
		qcf.setClientID ( sessionMng.m_lClientID );
		qcf.setCurrencyID ( sessionMng.m_lCurrencyID );
		qcf.setOperationTypeID ( OBConstant.QueryOperationType.CHECK );
		qcf.setUserID ( sessionMng.m_lUserID );
	
		//定义流水号
		/* 初始化信息查询类 */
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();

		/* 调用类方法 */
		Collection rs = obFinanceInstrDao.query_Uncheck(qcf);
		Iterator iterator = null;
		if (rs != null)
		iterator = rs.iterator();
		
		//在请求中保存结果对象
		request.setAttribute("return",iterator);
		request.setAttribute("FormValue",qcf);
		request.setAttribute("strStartDate", strStartDate);
		request.setAttribute("strEndDate", strEndDate);
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
		
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	if(sign.equals("current")){
	pageControllerInfo.setUrl(strContext + "/capital/check/V418.jsp");
	}else if(sign.equals("fixd")){
	pageControllerInfo.setUrl(strContext + "/capital/check/V419.jsp");
	}else{
	pageControllerInfo.setUrl(strContext + "/capital/check/V413.jsp");
	}
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		//forward到结果页面
		rd.forward(request, response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>

