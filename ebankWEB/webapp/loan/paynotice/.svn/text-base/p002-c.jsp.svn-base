<%
	/*
	*页面名称：p002-c.jsp
	*功能：提款申请
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.ebank.obpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obpaynotice.bizlogic.*"
%>
<%@ include file="/common/common.jsp" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[提款申请]";
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
		long lPayID = -1;
		String paytype="";
		String txtAction = "";
///////control/////////////////////////////////////////////

		strTmp = (String)request.getAttribute("txtAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			txtAction = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("lPayID");
        if( strTmp != null && strTmp.length() > 0 )
        {
             lPayID = Long.parseLong(strTmp.trim());
        }
		
		
		strTmp = (String)request.getAttribute("paytype");
        if( strTmp != null && strTmp.length() > 0 )
        {
             paytype = strTmp.trim();
        }

        else
        {
	        // lPayID = -1;
        }

		/*
		strTmp = (String)request.getAttribute("lLoanDrawNoticeID");//合同ID
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
		*/
		
///////////////////////////////////////////////////////////////////////////////////

           OBPayNoticeHome PayNoticeHome = (OBPayNoticeHome)EJBObject.getEJBHome("OBPayNoticeHome");
		   OBPayNotice PayNotice = PayNoticeHome.create();
		   PayNoticeInfo info = new PayNoticeInfo();

////////////////////////////////////////////////////////////////////////////////////////////
		info = PayNotice.findPayNoticeByID(lPayID,lContractID);
		
		request.setAttribute("info",info);		
				
		if(strAction.equals("addnew"))
		{
			ServletContext sc = getServletContext();
			/* 设置返回地址 */
			RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p003-v.jsp?txtAction="+txtAction)));
			/* forward到结果页面 */
			rd.forward(request, response);
		}
		else if(strAction.equals("view"))
		{
			ServletContext sc = getServletContext();
			/* 设置返回地址 */
			RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p005-v.jsp?paytype="+paytype+"&txtAction="+txtAction)));
			/* forward到结果页面 */
			rd.forward(request, response);
		}
		else if(strAction.equals("modify"))
		{
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd  = null;
			/* 设置返回地址 */
	
			if (info.getInputUserID()==sessionMng.m_lUserID
			&&
			(info.getStatusID()==OBConstant.LoanInstrStatus.SAVE
			||info.getStatusID()==OBConstant.LoanInstrStatus.SUBMIT)
			)
			{
				rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p003-v.jsp?strAction=modify"+"&txtAction="+txtAction)));
			}
			else
			{
				request.setAttribute("txtAction","modify");
				rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/paynotice/p005-v.jsp?paytype="+paytype)));
			}
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

