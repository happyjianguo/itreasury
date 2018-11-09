<%--
 页面名称 ：c001.jsp
 页面功能 : 预算编制控制层
 作    者 ：weilu
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
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
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

	/** 
	 * [定义业务变量]
	 */
	 long showColumn = -1;
	 String versionNo = info.getVersionNo();
	 String strTmp = (String)request.getAttribute("showcolumn");
	 if (strTmp != null && strTmp.length()>0 )
	 {
		showColumn = Long.parseLong(strTmp);
	 }
	
	/**
	* 初始化业务逻辑层
	*/
	ConstituteDelegation delegation = new ConstituteDelegation();
	
	/**
	 * 根据操作代码进行业务操作
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//创建保存
	{
		info.setClientID(sessionMng.m_lClientID);
		Collection c = delegation.findByCondition(info);
		
		request.setAttribute("searchResult",c);
		/**
		* 操作结果置为成功
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.NEXTSTEP)))	//下一步
	{
		//设置显示的列
		if (showColumn > 0)
			info.setShowColumn(new long[]{showColumn,BUDGETConstant.BudgetColumnList.CURRENTAMOUNT});
		else
			info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.CURRENTAMOUNT});
		
		Collection c = delegation.findNewConstituteTemplate(info);
		if (versionNo == null || versionNo.length() == 0)
			versionNo = delegation.getNewVersionNo(info);

		long level = delegation.getItemMaxLevel(info.getClientID(),info.getBudgetSystemID());

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		request.setAttribute("versionNo",versionNo);
		/**
		* 操作结果置为成功
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.LINKSEARCH)))	//下一步
	{
		//设置显示的列
		if (showColumn > 0)
			info.setShowColumn(new long[]{showColumn,BUDGETConstant.BudgetColumnList.CURRENTAMOUNT});
		else
			info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.CURRENTAMOUNT});
		
		Collection c = delegation.findAll(info);
		if (versionNo == null || versionNo.length() == 0)
			versionNo = delegation.getNewVersionNo(info);

		long level = delegation.getItemMaxLevel(info.getClientID(),info.getBudgetSystemID());
		
		if (info.getId()>0)
		{
			BudgetPlanInfo ThePlanInfo = delegation.findByPlanID(info.getId());
			ThePlanInfo.setShowColumn(info.getShowColumn());
			info = ThePlanInfo;
		}

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		request.setAttribute("versionNo",versionNo);
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