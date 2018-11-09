<%@page contentType="text/html;charset=gbk"%>
<!--Header start-->
<%
//response.setHeader("Pragma", "no-cache");
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", -1); 
%>
<!--Header end-->
<%@page import="
java.util.*,
com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo,
com.iss.itreasury.system.approval.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[提前还款通知单]";
%>	
<%
	long lID = -1; 
	String sAction = "";
	long lAction = -1;
	
	try
	{
		/**
		* isLogin start
		*/
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

		//取参数变量
		String strTemp = "";
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("actionControl");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lAction = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lAction = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("action");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sAction = strTemp;
			}
			catch (Exception e)
			{
				sAction = "";
			}
		}	
			
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		
		OBAheadRepayNoticeHome aheadRepayNoticeHome = (OBAheadRepayNoticeHome)EJBObject.getEJBHome("OBAheadRepayNoticeHome");
		OBAheadRepayNotice aheadRepayNotice = aheadRepayNoticeHome.create();
		//Search
		info = aheadRepayNotice.findAheadRepayNotice(-1,-1,lID);
		
		request.setAttribute("info", info);  
		request.setAttribute("actionControl", String.valueOf(lAction));  
		//网银不需要审批意见
		/*long lModuleID = Constant.ModuleType.LOAN; //模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;  //贷款类型
		long lActionID = Constant.ApprovalAction.AHEADREPAY_NOTICE; //合同审核
		ApprovalBiz appbiz = new ApprovalBiz();*/

		//取得审批意见
		/*Collection cApproval = appbiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lID);
		request.setAttribute("cApproval", cApproval);		*/

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		RequestDispatcher rd = null;
		
		String sURL = "../aheadrepaynotice/a008-p.jsp";
		
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(sURL);
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

			