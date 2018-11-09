<%--
 页面名称 ：c001.jsp
 页面功能 : 通知支取指令 提交控制页面
 作    者 ：YuanHuang
 日    期 ：2006-11-03
 特殊说明 ：
--%>
<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="com.iss.itreasury.settlement.util.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.util.DataFormat,
					com.iss.itreasury.ebank.util.OBHtml,				
					com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
					com.iss.itreasury.ebank.obnotifydepositinform.bizlogic.*,
					com.iss.itreasury.ebank.util.OBConstant,
					com.iss.itreasury.settlement.util.SETTConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
 <%@ include file="/common/common.jsp" %>


<%
String strNextURL = "../view/v003.jsp";
//Added by zwsun, 2007/6/26, 通知存款通知显示
if(request.getParameter("control")!=null && request.getParameter("control").equals("display")){
	strNextURL="../view/v004.jsp";
	request.setAttribute("control", request.getParameter("control"));
}
long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "通知存款支取通知";
  try 
	{
      //用户登录检测 
      if (sessionMng.isLogin() == false)
      {
      	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
      	out.flush();
      	return;
      }

      // 判断用户是否有权限 
      if (sessionMng.hasRight(request) == false)
      {
          out.println(sessionMng.hasRight(request));
      	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
      	out.flush();
      	return;
      }
	System.out.println("-------------------------c003.jsp---------------------------");
	
	
	long id = Long.parseLong((String)(request.getAttribute("id")));	
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	info = biz.findByID(id);
	
	request.setAttribute("info",info);
	
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}


//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

rd.forward( request,response ); 
%>
