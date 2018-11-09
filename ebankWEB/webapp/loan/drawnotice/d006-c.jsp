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
				com.iss.itreasury.ebank.obdrawnotice.dataentity.*,
				com.iss.itreasury.ebank.obdrawnotice.bizlogic.*"
				
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[银团贷款提款通知单]";
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
		OBDrawNoticeHome home = (OBDrawNoticeHome) EJBObject.getEJBHome("OBDrawNoticeHome");
		OBDrawNotice ejb = home.create();
		DrawNoticeInfo LDNinfo = new DrawNoticeInfo();
////////////////////////////////////////////////////////////////////*/
		//定义变量，获取请求参数

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		long lLoanType = -1;
		double mThisDrawAmount=0;
		long lContractID=-1;		//合同标示
		long lLoanDrawNoticeID = -1;//银团放款通知单ID
		String strLoanPayCode=null;	//放款通知单编号
///////control/////////////////////////////////////////////
/*
		strTmp = request.getParameter("type");//放款单ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanType = Long.parseLong(strTmp.trim());
		}
		*/
		strTmp = request.getParameter("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}

		strTmp = request.getParameter("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = request.getParameter("lLoanDrawNoticeID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanDrawNoticeID = Long.parseLong(strTmp.trim());
		}
		
				strTmp = (String)request.getAttribute("mThisDrawAmount");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				mThisDrawAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
			}
			catch(Exception e)
			{
				;
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////
		LDNinfo = new DrawNoticeInfo();
		LDNinfo = ejb.findDrawNoticeByID(lLoanDrawNoticeID,lContractID);

		request.setAttribute("LDNinfo",LDNinfo);
		

////////////////////////////////////////////////////////////////////////////////////////////
		if(strAction.equals("addnew"))
		{
		Log.print("add new DeawNotice");
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/drawnotice/d003-v.jsp")));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}
		else if(strAction.equals("count"))
		{
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/drawnotice/d003-v.jsp?strAction=count&mThisDrawAmount="+mThisDrawAmount)));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}
		else if(strAction.equals("print"))
		{
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/drawnotice/d007-v.jsp?strAction=count&mThisDrawAmount="+mThisDrawAmount)));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}
		else
		{
		Log.print("view DeawNotice");
	    ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/drawnotice/d005-v.jsp")));
	    /* forward到结果页面 */
	    rd.forward(request, response);
		}
		
///////////////////////////////////////////////////////////////////////////////////////////////////

%>

<%

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>

