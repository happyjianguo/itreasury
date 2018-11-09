<%--
 页面名称 ：c161.jsp
 页面功能 : 银行滞留比例分析控制页面
 作    者 ：liuyang
 日    期 ：
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.loan.util.*" %>
<%@ page import="com.iss.itreasury.bill.util.*" %>
<%@ page import="com.iss.itreasury.budget.query.queryobj.QBudget" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.bizdelegation.ConstituteDelegation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	QueryBudgetInfo qInfo = null;
	String strTitle = "预算结构分析"; 

try
{	
	
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
	

	/**
	 * 从request中获得页面控制信息
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	 * 定义业务dataentity
	 */
	qInfo = new QueryBudgetInfo();
	qInfo.convertRequestToDataEntity(request);
	
	/**
	 * 声明Delegation
	 */
	ConstituteDelegation delegation = new ConstituteDelegation();

	/**
	 * 根据操作代码进行操作
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//
	{
			qInfo.setShowColumn(new long[]{
			BUDGETConstant.BudgetColumnList.CURRENTBUDGET,
			BUDGETConstant.BudgetColumnList.BUDGETSTRUCTURE,
			BUDGETConstant.BudgetColumnList.CURRENTEXECUTE,
			BUDGETConstant.BudgetColumnList.EXECUTESTRUCTURE
			});
		Collection c = delegation.findAll(qInfo);
		long level = delegation.getItemMaxLevel(qInfo.getClientID(),qInfo.getBudgetSystemID());

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		/**
		* 操作结果置为成功
		*/
		pageInfo.success();
	}


}
catch( Exception exp )
{
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	sessionMng.getActionMessages().addMessage(exp); 
	/**
	 * 出现异常,操作结果置为失败
	 */
	pageInfo.fail();
}
	
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	request.setAttribute("QueryBudgetInfo",qInfo);
	
	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>