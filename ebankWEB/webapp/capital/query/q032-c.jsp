<%--
 页面名称 ：c001.jsp
 页面功能 :  挂失业务处理控制页面
 作    者 ：jinchen
 日    期 ：2004-11-23
 特殊说明 ：
 实现操作说明：
 修改历史 ：
           
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@page import="
				java.util.*,
				java.sql.*,
				com.iss.itreasury.util.*,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.*,
				com.iss.itreasury.settlement.reportlossorfreeze.dao.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/**
	 * 页面控制变量
	 */
	String strSourcePage 		= "";		//来源页面
    String strNextPageURL 		= "";		//下一个跳转的页面
	String strSuccessPageURL 	= "";		//操作成功跳转到的页面
	String strFailPageURL 		= "";		//操作失败跳转到的页面
	String strAction 			= "";		//操作代码
	String strActionResult		= "";		//操作结果
	String strParentPageURL		= "";		//父页面

	
	
try
{	

	/* 判断用户是否有权限 */
	if (sessionMng.hasRight(request) == false) {
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
		out.flush();
		return;
	}
	
	/**
	 * 从request中获取页面控制参数
	 */
	String strTemp = "";
	
	strTemp = (String)request.getAttribute("strSourcePage");
	if (strTemp != null && strTemp.trim().length()>0){					//来源页面
		strSourcePage = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strSuccessPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//成功页面
		strSuccessPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strFailPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//失败页面
		strFailPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strAction");
	if (strTemp != null && strTemp.trim().length() > 0){				//操作代码
		strAction = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strParentPageURL");
	if (strTemp != null && strTemp.trim().length()>0){					//父页面
		strParentPageURL = strTemp.trim();
	}
		
	 //定义变量
		
	String strClientCode = ""; // 查询由客户编号
	String strExecuteStart = ""; //执行日期--开始
	String strExecuteEnd = ""; //执行日期--结束
	long lClientId = -1;	//客户ID
	long lOrder = -1;	
	long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	long lTransActionType = -1;

	/**
	 * 获得提交变量
	 */
	
	strTemp = (String)request.getAttribute("hdnTransActionType");
	if( strTemp != null && strTemp.length() > 0 )
	{
		lTransActionType = Long.parseLong(strTemp.trim());
	}
	strTemp = (String)request.getAttribute("Order");
	if( strTemp != null && strTemp.length() > 0 )
	{
		lOrder = Long.parseLong(strTemp.trim());
	}
	strTemp = (String)request.getAttribute("Desc");
	if( strTemp != null && strTemp.length() > 0 )
	{
		lDesc = Long.parseLong(strTemp.trim());
	}
	

	strTemp = (String)request.getAttribute("lClientID");
	if( strTemp != null && strTemp.length() > 0 )
	{
		lClientId = Long.parseLong(strTemp.trim());
		//qInfo.setClientId(Long.parseLong(strTemp.trim()));
	}
	strTemp = (String)request.getAttribute("startdate");
	if( strTemp != null && strTemp.length() > 0 )
	{
		strExecuteStart = strTemp.trim();
		//qInfo.setStartDate(DataFormat.getDateTime(strTemp.trim()));
	}
	strTemp = (String)request.getAttribute("enddate");
	if( strTemp != null && strTemp.length() > 0 )
	{
		strExecuteEnd = strTemp.trim();
		//qInfo.setEndDate(DataFormat.getDateTime(strTemp.trim()));
	}

	
	
	 
	 
	
	 
	
	
    
	
	

	
	/**
	 * 定义业务dataentity
	 */

	ReportLossOrFreezeQueryInfo qInfo = new ReportLossOrFreezeQueryInfo();
	

	/**
	 * 声明Delegation
	 */
	ReportLossOrFreezeBean delegation = new ReportLossOrFreezeBean();


	

	/**
	 * 根据操作代码进行操作
	 */
	 //if (strAction.equals(String.valueOf(SETTConstant.Actions.MATCHSEARCH)))	// 匹配查找
		
		
		
		
		
		PageLoader pageLoader = null;
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		Log.print("strPageLoaderKey : " + strPageLoaderKey);
		if( lOrder < 0 )
		{
			
			qInfo.setDesc(lDesc);
			qInfo.setOrderField(lOrder);
			qInfo.setTransActionType(lTransActionType);
			qInfo.setStatus(3);
			if(lClientId>0)
			{
				qInfo.setClientId(lClientId);
			}
			if( strExecuteStart != null && strExecuteStart.length() > 0 )
			{
				qInfo.setStartDate(DataFormat.getDateTime((strExecuteStart)));
			}
			if( strExecuteEnd != null && strExecuteEnd.length() > 0 )
			{
				qInfo.setEndDate(DataFormat.getDateTime((strExecuteEnd)));
			}
			
			pageLoader = delegation.queryReportLossOrFreezeInfo(qInfo);
			
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			sessionMng.setQueryCondition(strPageLoaderKey,qInfo);
		}
		else
		{
			qInfo = (ReportLossOrFreezeQueryInfo)sessionMng.getQueryCondition(strPageLoaderKey);
			
			qInfo.setDesc(lDesc);
			qInfo.setOrderField(lOrder);
			delegation.getReportLossOrFreezeInfoSQL(qInfo);
			//
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			pageLoader.setOrderBy(delegation.getOrderBy().toString());
		}
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		strActionResult = Constant.ActionResult.SUCCESS;
	
	





	
	
}catch( Exception exp ){

	Log.print("新增利率-------------失败-------------------------------------------------------");
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	//sessionMng.getActionMessages().addMessage(exp); 
	/**
	 * 出现异常,操作结果置为失败
	 */
	strActionResult = Constant.ActionResult.FAIL;
}
	
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",strActionResult);

	/**
	 * 根据处理结果设置下一步跳转的目标页面
	 */
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL : strFailPageURL;

	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:" + strNextPageURL);	
	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/pagecontrol.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	
	rd.forward( request,response );
%>