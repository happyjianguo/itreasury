<%--
 页面名称 ：c003.jsp
 页面功能 : 单据打印--控制页面
 作    者 ：Boxu
 日    期 ：2007-7-17
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz"%>
<%@ page import="com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity"%>
<%@page import="java.util.HashMap"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
/* 标题固定变量 */
	String strTitle = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strNextPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	
	long transID = -1;
	String strTransNo = "";
	long transTypeID = -1;
	//特殊交易类型
	long operationTypeID = -1;
	String strAction = "";
	sessionMng.setServletInfo(request.getServerPort(),request.getServerName(),request.getContextPath());	
	try
	{
		/* 用户登录检测 */
	    if (sessionMng.isLogin() == false)
	    {
	        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E002");
	    	out.flush();
	    	return;
	    }
	
	    /* 判断用户是否有权限 */
	    if (sessionMng.hasRight(request) == false)
	    {
	    	out.println(sessionMng.hasRight(request));
	    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E003");
	    	out.flush();
	    	return;
	    }
    
		boolean blPrint = false;
		
		//打印XML的名称
		String[] billName = null;
		HashMap printCountMap = new HashMap();//打印次数

		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = "";
		strTemp = (String)request.getAttribute("transIDs");
		if(strTemp!=null && strTemp.length()>0)
		{
			transID = Long.parseLong(strTemp);
		}

		strTemp = (String)request.getAttribute("strTransNos");
		if(strTemp!=null && strTemp.length()>0)
		{
			strTransNo = strTemp;
		}

		if(request.getParameter("billName")!=null)
		{
			billName = request.getParameterValues("billName");
		}
		else
		{
			billName = new String[1];
			strTemp = (String)request.getAttribute("printXMLName");
			if(strTemp!=null && strTemp.length()>0)
			{
				billName[0] = strTemp;
			}
		}

		strTemp = (String)request.getAttribute("lTransTypeID");
		if(strTemp!=null && strTemp.length()>0)
		{
			transTypeID = Long.valueOf(strTemp).longValue();
		}
		
		//特殊交易类型
		strTemp = (String)request.getAttribute("operationTypeID");
		if(strTemp!=null && strTemp.length()>0)
		{
			operationTypeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strAction");
		if(strTemp!=null && strTemp.length()>0)
		{
			strAction = strTemp;
		}

		if(strAction.equals("preview"))
		{
			QueryPrintBiz biz = new QueryPrintBiz();
			strActionResult = Constant.ActionResult.SUCCESS;
			PrintXMLTimeInfo p = new PrintXMLTimeInfo();
			p.setId(transID);
			p.setTransNo(strTransNo);
			p.setBillName(billName);
			p.setDeptID(VOUConstant.PrintSection.EBANKCUSTOMER);
			p.setOfficeID(sessionMng.m_lOfficeID);
			p.setCurrencyID(sessionMng.m_lCurrencyID);
			p.setModule(Constant.ModuleType.SETTLEMENT);
			
			//针对于特殊交易修改
			p.setTransTypeID(transTypeID);
			p.setOpretionType(operationTypeID);
			
			p.setPrintUser(sessionMng.m_lUserID);//jzw 2010-05-25 针对电子签章添加打印用户信息
			p.setClientID(sessionMng.m_lClientID);
			p.setIsPreview(1);
			String printCheck = biz.valadatePrintTHREE(p);
			String printCount = printCheck.split("_")[0];
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("printCount",printCount+"");//张雷 添加  打印次数
		}
		else
		{
			QueryPrintBiz biz = new QueryPrintBiz();
			PrintXMLTimeInfo printXMLInfo = new PrintXMLTimeInfo();
			printXMLInfo.setId(transID);
			printXMLInfo.setTransNo(strTransNo);
			printXMLInfo.setBillName(billName);
			printXMLInfo.setDeptID(VOUConstant.PrintSection.EBANKCUSTOMER);
			printXMLInfo.setOfficeID(sessionMng.m_lOfficeID);
			printXMLInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			printXMLInfo.setModule(Constant.ModuleType.SETTLEMENT);
			
			//针对于特殊交易修改
			printXMLInfo.setTransTypeID(transTypeID);
			printXMLInfo.setOpretionType(operationTypeID);
			
			printXMLInfo.setPrintUser(sessionMng.m_lUserID);//jzw 2010-05-25 针对电子签章添加打印用户信息
			printXMLInfo.setClientID(sessionMng.m_lClientID);
			
			//blPrint = biz.valadatePrintTWO(printXMLInfo);
			String printCheck = biz.valadatePrintTHREE(printXMLInfo);
			String canPrint = printCheck.split("_")[1];
			String printCount = printCheck.split("_")[0];
			if("can".equals(canPrint))
			{
				strActionResult = Constant.ActionResult.SUCCESS;
				printCountMap = biz.getPrintCountMap(printXMLInfo);
				request.setAttribute("printCountMap",printCountMap);//xiang 添加  打印次数集合
			}
			else
			{
				strActionResult = Constant.ActionResult.FAIL;
				sessionMng.getActionMessages().addMessage("该交易对应单据模版已达到最大打印次数");
				request.setAttribute("printCount",printCount+"");//张雷 添加  打印次数
				request.setAttribute("lID", String.valueOf(transID));
				request.setAttribute("TransactionTypeID", String.valueOf(transTypeID));
				request.setAttribute("TransNo", strTransNo);
				request.setAttribute("strSuccessPageURL", "../view/v003_p.jsp");
				request.setAttribute("strFailPageURL", "../view/v003_p.jsp");
			}
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		//出现异常,操作结果置为失败
		strActionResult = Constant.ActionResult.FAIL;
		
		request.setAttribute("lID", String.valueOf(transID));
		request.setAttribute("TransactionTypeID", String.valueOf(transTypeID));
		request.setAttribute("TransNo", strTransNo);
		request.setAttribute("strSuccessPageURL", "../view/v003_p.jsp");
		request.setAttribute("strFailPageURL", "../view/v003_p.jsp");
	}

	// 页面跳转

	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL:strFailPageURL;
	//request.setAttribute("strSuccessPageURL", strSuccessPageURL);

	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>