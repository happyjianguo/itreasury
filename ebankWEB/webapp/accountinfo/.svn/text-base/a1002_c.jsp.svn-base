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
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.bankportal.integration.info.ReqGetGeneralBankAcctInfo"%>
<%@ page import="com.iss.itreasury.bankportal.integration.info.RespGetGeneralBankAcctInfo"%>
<%@ page import="com.iss.itreasury.bankportal.integration.client.BPClientAgent"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	RespGetGeneralBankAcctInfo generalbankacctinfo = new RespGetGeneralBankAcctInfo();
	ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
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


		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		//读取数据
		String lAccountID ="";
		String strTemp = null;		
		strTemp = (String)request.getAttribute("lAccountIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountID = strTemp;
		}
		instruction.setSystemId(1);
		instruction.setReferenceCode(lAccountID);
		generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//获得活期账户的上级银行账号单位
		strActionResult = Constant.ActionResult.SUCCESS;
		strSuccessPageURL = strSuccessPageURL + "?isTrue=true";
		request.setAttribute("generalbankacctinfo",generalbankacctinfo);
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;	
	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response);
%>