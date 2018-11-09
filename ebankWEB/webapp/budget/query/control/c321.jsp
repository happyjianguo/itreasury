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
<%@ page import="java.util.Vector" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.loan.util.*" %>
<%@ page import="com.iss.itreasury.bill.util.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.query.queryobj.QBudget" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.bizdelegation.ConstituteDelegation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strAction = "";

try
{	
	
	/** 权限检查 **/
	/** 权限检查 **/
	if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
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
	strAction = (String)request.getAttribute("strAction");
	QueryBudgetInfo qInfo = new QueryBudgetInfo();
	
	if(String.valueOf(Constant.Actions.NEXTSTEP).equals(strAction)){
		qInfo.convertRequestToDataEntity(request);
	}else{
		qInfo=(QueryBudgetInfo)session.getAttribute("controlBudgetInfo");
	}
	//点返回的时候要保留原来的查询数据
	session.setAttribute("controlBudgetInfo",qInfo);
	
	/**
	 * 声明Delegation
	 */
	QBudget delegation = new QBudget();
	/**
	 * 根据操作代码进行操作
	 */

	BudgetPlanInfo planinfo=new BudgetPlanInfo();
	ConstituteDelegation delega= new ConstituteDelegation();
	
	Collection excuteColl= new ArrayList();
	//if(String.valueOf(Constant.Actions.NEXTSTEP).equals(strAction)){
			qInfo.setShowColumn(new long[]{
			BUDGETConstant.BudgetColumnList.ORIGINALAMOUNT,
			BUDGETConstant.BudgetColumnList.ADJUSTBALANCE,
			BUDGETConstant.BudgetColumnList.TOTALAMOUNT,
			BUDGETConstant.BudgetColumnList.CURRENTEXECUTE,
			BUDGETConstant.BudgetColumnList.EXCUTESCALE,
			BUDGETConstant.BudgetColumnList.SUB
			});
		planinfo.setShowColumn(qInfo.getShowColumn());
        planinfo.setBudgetFlag(BUDGETConstant.BudgetFlag.ADJUST);
		request.setAttribute("BudgetPlanInfo",planinfo);
		long level = delega.getItemMaxLevel(qInfo.getClientID(),qInfo.getBudgetSystemID());
		
		request.setAttribute("versionNo",qInfo.getVersionNo());
		//查询所有符合条件的记录（单位条件除外）
		excuteColl=delegation.queryBudgetExecute(qInfo);
		request.setAttribute("maxLevel",String.valueOf(level));
		request.setAttribute("excuteColl",excuteColl);
		pageInfo.setStrSuccessPageURL("../view/v322.jsp");
	//}
	/**
	 * 操作成功
	 */
	pageInfo.success();

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
	request.setAttribute("strActionResult", pageInfo.getStrActionResult());

	/**
	 * 将打印信息置入request中
	 */
	if (!pageInfo.getStrPrintMsg().equals(""))request.setAttribute("strPrintMsg",pageInfo.getStrPrintMsg());

	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:" + pageInfo.getStrNextPageURL());	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>