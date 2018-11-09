<%--
 页面名称 ：c001.jsp
 页面功能 : 账户授权控制页面
 作    者 ：ruiwang
 日    期 ：2006-11-8
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
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.ebank.obsystem.bizlogic.OBAcctPrvgBiz,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
				
				
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
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
	String strNextPageURL="../view/v001.jsp";
	Collection con;
	long clientId=sessionMng.m_lClientID;
	Log.print("********************登录人ID："+clientId);
	
	OBAcctPrvgBiz obap=new OBAcctPrvgBiz();
	con=obap.getAcctPrvgs(clientId,sessionMng.m_lCurrencyID );
	Log.print("@@@@@@@@@@@@@@@@@@@@@@"+clientId);
	if(con==null){
		
		Log.print("********************con为空");
	}
	else{
		Log.print("************con不为空");
	}
	request.setAttribute("clientInfo",con);
%>

<%
	Log.print("Next Page URL:" + strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
	
%>
<%
}catch( Exception exp ){


	
	
	exp.printStackTrace();
	
	
}

%>