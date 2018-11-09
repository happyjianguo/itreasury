<%--
 页面名称 ：c002.jsp
 页面功能 : 定期开立处理链接查找页面的控制类页面
 作    者 ：xrli
 日    期 ：2003-09-27
 特殊说明 ：实现操作说明：
				1、链接查找
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	if(request.getParameter("next")!=null){
	request.setAttribute("next",request.getParameter("next"));
	}
	if(request.getParameter("accountType")!=null){
	request.setAttribute("accountType",request.getParameter("accountType"));
	}
    try
	{
			/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }

        //定义变量		


		long lAccountID =-1;
		long lSubAccountID =-1;
		long lContractID =-1;
		strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		//读取数据
		String strTemp = null;		
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSubAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSubAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lContractID = Long.valueOf(strTemp).longValue();
		}
		
        OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
		OBAccountQueryInfo info = new OBAccountQueryInfo();
		OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
		info.setAccountID(lAccountID);
		info.setSubAccountID(lSubAccountID);
		info.setContractID(lContractID);
		info.setOfficeID(sessionMng.m_lOfficeID);
		info.setCurrencyID(sessionMng.m_lCurrencyID);
		//获取账户对应的开户机构
		tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
		info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
        //根据请求操作，完成业务处理的调用
        Collection resultColl = null;
        double [] MorY = null;
        
		if(strAction.equals("current"))
		{
		MorY = dao.findByCurrentAccountInfo(info);
        resultColl = dao.findByCurrentAccountID(info);		
		strActionResult = Constant.ActionResult.SUCCESS;
	
		}
		else if(strAction.equals("fixed"))
		{
								
	        resultColl = dao.findByFixedAccountID(info);			
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		else if(strAction.equals("fixedDzd"))
		{
								
	        resultColl = dao.findByFixedAccountIDDzd(info);			
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		else if(strAction.equals("notice"))
		{
									
	        //resultColl = dao.findByNoticeAccountID(info);
			resultColl = dao.findByFixedAccountID(info);
			strActionResult = Constant.ActionResult.SUCCESS;
		   
			
		}
		else if(strAction.equals("loan"))
		{
								
	        resultColl = dao.findByLoanAccountID(info);
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		request.setAttribute("MorY",MorY);
		request.setAttribute("searchResults",resultColl);
		request.setAttribute("tempinfo",tempinfo);
	}
	
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	request.setAttribute("strActionResult",strActionResult);
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;	
	//转向下一页面
	//构建页面分发时需要用到的实体
	String strNextURL = strNextPageURL;
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response);
%>