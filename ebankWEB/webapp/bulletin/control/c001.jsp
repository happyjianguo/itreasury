<%--
 页面名称 ：c001.jsp
 页面功能 : 公告功能控制页面！
 作    者 ：ruiwang
 日    期 ：2006-11-11
 转入页面 ：v001.jsp
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.system.bulletin.bizlogic.*,
				com.iss.itreasury.system.bulletin.dataentity.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.ebank.obsystem.bizlogic.OBAcctPrvgBiz,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
				
				
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>  
<% 
try {
	 /** 权限检查 **/
   Log.print("=================进入页面budget/constitute/control/c001.jsp=========");
	String strTitle = "预算编制"; 
   //请求检测
    //用户登录检测 
       if (sessionMng.isLogin() == false)
       {
       	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
       	out.flush();
       	return;
       }

       // 判断用户是否有权限 
       if (sessionMng.hasRight(request) == false)
       {
           out.println(sessionMng.hasRight(request));
       	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
       	out.flush();
       	return;
       }
%>

<%	
//////////////////////分页///////////////////////

 
String	strSuccessPageURL = "/bulletin/view/v001.jsp"	 ;
String	strFailPageURL = "bulletin/view/v001.jsp";
String strNextPageURL="../view/v001.jsp";
String searchAction="search";			
BulletinInfo info=new BulletinInfo();
String clientID=""+sessionMng.m_lClientID;
Log.print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&clientID"+clientID);
info.setClients(clientID);
info.setOfficeId(sessionMng.m_lOfficeID);
/*开始查询动作*/
PageLoader pageLoader = null;
	
String strPageLoaderKey = null;
if(request.getAttribute("_pageLoaderKey")!=null)
{
	 strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");		 
}

BulletinBiz bulletinBiz=new BulletinBiz();

if( searchAction.equals("search"))
{ 
	
	 pageLoader = bulletinBiz.findWithPage(info);
	
	 Log.print("_pageLoaderKey~~~~~~~~~~~~~~"+strPageLoaderKey);
	 if( strPageLoaderKey==null)
		 
	 {
		 Log.print("111111111111111111111111111111111111111111");
		 strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
		 Log.print("_pageLoaderKey~~~~~~~~~~~~~~"+strPageLoaderKey);
	 }		
	 Log.print("222222222222222222222222222222222222222");
	 sessionMng.setQueryCondition(strPageLoaderKey,info);
	 Log.print("3");
	}
	else
	{		
		pageLoader = sessionMng.getPageLoader(strPageLoaderKey);			
	}	
	request.setAttribute("_pageLoaderKey",strPageLoaderKey);
	strNextPageURL = "/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
	//ServletContext sc = getServletContext();
    
	/* 设置返回地址 */
	RequestDispatcher rd=null;
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	
	/* forward到结果页面 */
    rd.forward(request, response);
%>

<%
	//Log.print("Next Page URL:" + strNextPageURL);	
	//RequestDispatcher rd=null;

	//rd.forward( request,response );
	
%>
<%
}catch( Exception exp ){


	
	
	exp.printStackTrace();
	
	
}

%>