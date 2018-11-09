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
////////////////////////////get parameter/////////////////////////
		lContractID = GetNumParam(request,"lContractID");
		lLoanPayID = GetNumParam(request,"lLoanPayID");
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lContractID);
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lLoanPayID);
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lFreeApplyID);
//////////////////////////get data///////////////////////////
		queryInfo.setLContractID(lContractID);
		queryInfo.setLLoanPayID(lLoanPayID);
		queryInfo.setLFreeApplyID(lFreeApplyID);
		freeinfo = ejb.findFreeApply(queryInfo);
							/* 在请求中保存结果对象 */
	    request.setAttribute("freeinfo", freeinfo);
		Log.print("set freeinfo into attribute!");
///////business logic/////////////////////////////////////////////

		if(freeinfo!=null)
		{
		
		Log.print("print");
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f007-v.jsp")));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}
		else {
%>
					<script language="JavaScript">
						alert("无法取得参数！");
						eval("history.back(-1)");
					</script>
<%
		}

///////business logic/////////////////////////////////////////////

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>