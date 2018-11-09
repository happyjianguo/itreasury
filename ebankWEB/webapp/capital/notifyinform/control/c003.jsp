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
String strNextURL = "../control/c002.jsp";

long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "通知存款支取指令提交";
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
	
	String [] checki =  request.getParameterValues("ccheck");
	long [] id=new long[checki.length] ;
	for( int i=0; i<checki.length; i++)
	{
		id[i] = Long.valueOf(checki[i]).longValue();
	}
	
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	long lret = -1;
	lret = biz.cancel(id);
	if(lret>0)
	{
		sessionMng.getActionMessages().addMessage("撤销支取指令成功。");
	}
	
	 
	
	
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
RequestDispatcher rd = null;
response.sendRedirect(strNextURL);

%>
