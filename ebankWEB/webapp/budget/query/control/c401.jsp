<%--
 页面名称 ：c401.jsp
 页面功能 : 预算调整查询控制层
 作    者 ：xrli
 日    期 ：2005-7-14
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
				com.iss.itreasury.budget.query.queryobj.QBudget,
				com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
BudgetPlanInfo info = null;

try {
	 /** 权限检查 **/
    Log.print("=================进入页面budget/constitute/control/c011.jsp=========");
    //请求检测
   if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
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
	* 初始化业务实体类 
	*/
	info = new BudgetPlanInfo();

	/**
	* 获得上页参数
	*/
	info.convertRequestToDataEntity(request);
	
	QueryBudgetInfo qInfo = new QueryBudgetInfo();
	qInfo.convertRequestToDataEntity(request);

	info.setBudgetFlag(BUDGETConstant.BudgetFlag.ADJUST);

	
	/**
	* 初始化业务逻辑层
	*/
	ConstituteDelegation delegation = new ConstituteDelegation();
	QBudget qBudget = new QBudget();
	/**
	 * 根据操作代码进行业务操作
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//查找
	{
		Collection c = delegation.findByCondition(info);
		
		request.setAttribute("searchResult",c);
		
		request.setAttribute("clientid",String.valueOf(info.getClientID()));
		/**
		* 操作结果置为成功
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.NEXTSTEP)))	//链接查找
	{
		
		System.out.println("******************************"+qInfo.getPlanID());
		Collection c = qBudget.queryBudgetAdjust(qInfo);
		
		System.out.println("**************"+c.size());
		request.setAttribute("searchResult",c);
		
		long level = delegation.getItemMaxLevel(qInfo.getClientID(),qInfo.getBudgetSystemID());
		System.out.println("**************"+level);
		//查询所有符合条件的记录（单位条件除外）
		
		long adjustNum =qBudget.getAdjustNum(qInfo);
		System.out.println("**************"+adjustNum);
		
		request.setAttribute("adjustNum",String.valueOf(adjustNum));
		request.setAttribute("maxLevel",String.valueOf(level));

		/**
		* 操作结果置为成功
		*/
		pageInfo.success();
	}

} 
/**
* 异常处理
*/
catch ( Exception exp ) {
	/**
	* 用户提交信息置入request中
	*/
	
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
	request.setAttribute("BudgetPlanInfo",info);
	
	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
				//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>