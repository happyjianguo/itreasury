<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
	com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.util.SessionMng"></jsp:useBean>
<%

	try
	{
		/**
		* isLogin start
		*/
		//登录检测
		
    	if (!sessionMng.isLogin())
    	{
			LOANHTML.showMessage(out,sessionMng,request,response,"客户管理",Constant.RecordStatus.INVALID,"Gen_E002");
			LOANHTML.showHomeEnd(out);
        	out.flush();
        	return;
    	}

    	//检测权限
    	if (sessionMng.hasRight(request) == false)
    	{
			LOANHTML.showMessage(out,sessionMng,request,response,"客户管理",Constant.RecordStatus.INVALID,"Gen_E003");
        	LOANHTML.showHomeEnd(out);
        	out.flush();
        	return;
    	}
		
		long lClientID = -1;
		long lLoanType = -1;
		
		//取参数变量
		String strTemp = "";
		strTemp = request.getParameter("lClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lClientID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lClientID = -1;
			}
		}
		
		strTemp = request.getParameter("lLoanType");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lLoanType = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lLoanType = -1;
			}
		}
		
		LoanCommonSettingHome home = (LoanCommonSettingHome)EJBObject.getEJBHome("LoanCommonSettingHome");
		LoanCommonSetting lcs = home.create();
		
		ClientInfo cInfo=null;
		cInfo=lcs.findClientByID(lClientID);
		request.setAttribute("ClientInfo",cInfo);	
		
		String strURL = "/loan/l004-v.jsp?txtAction=check&lLoanType="+lLoanType;

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		LOANHTML.showExceptionMessage(out, sessionMng, ie, request, response, "客户管理", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			