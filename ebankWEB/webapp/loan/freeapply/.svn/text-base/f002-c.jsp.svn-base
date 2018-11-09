<%
	/*
	*页面名称：f002-c.jsp
	*功能：免还申请
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[免还申请]";
%>					
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;
	
	
	//Log.print("*******进入页面--ebank/loan/discountapply/d011-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home  = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
		FreeApplyQueryInfo queryInfo = new FreeApplyQueryInfo();

////////////////////////////////////////////////////////////////////*

		//定义变量，获取请求参数

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		
		long lFreeApplyID = -1;
		long lTypeID=-1;
		long lContractID=-1;		//合同标示
		long lLoanPayID=-1;//放款通知单ID
/*///////////////////////////get parameter/////////////////////////
		lContractID = GetNumParam(request,"lContractID");
		lLoanPayID = GetNumParam(request,"lLoanPayID");
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
		strAction = GetParam(request,"strAction");
//////////////////////////get data///////////////////////////*/
		strTmp = (String)request.getAttribute("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		strTmp = (String)request.getAttribute("lContractID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID=Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lLoanPayID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanPayID=Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lFreeApplyID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lFreeApplyID=Long.parseLong(strTmp.trim());
		}
		Log.print(lContractID);
		Log.print(lLoanPayID);
		Log.print(lFreeApplyID);
		Log.print(strAction);
		queryInfo.setLContractID(lContractID);
		queryInfo.setLLoanPayID(lLoanPayID);
		queryInfo.setLFreeApplyID(lFreeApplyID);
		freeinfo = ejb.findFreeApply(queryInfo);
							/* 在请求中保存结果对象 */
	    request.setAttribute("freeinfo", freeinfo);
		request.setAttribute("strAction", strAction);
		Log.print("set freeinfo into attribute!");
///////business logic/////////////////////////////////////////////

		if(strAction.equals("addnew"))
		{
		
		Log.print("add new freeApply!!!");
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f003-v.jsp")));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}else if(strAction.equals("edit"))
		{
			Log.print("edit freeApply!!!");
	    	ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    	RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f003-v.jsp")));
	    /* forward到结果页面 */
	    	rd.forward(request, response);
		}
		else if (strAction.equals("view")){
		
		Log.print("view freeApply!!!");
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f005-v.jsp")));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}

///////business logic/////////////////////////////////////////////

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>