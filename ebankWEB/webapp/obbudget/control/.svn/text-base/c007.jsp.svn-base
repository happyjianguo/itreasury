<%
/**页面功能说明
 * 页面名称 ：c007.jsp
 * 页面功能 : 
 * 作    者 ：barneyliu	
 * 日    期 ：2005-10-30
 */
%>

<%@ page contentType = "text/html;charset=gbk"%>
<!--类导入部分开始-->
 
<%@ page import="com.iss.itreasury.util.Constant.PageControl"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!--类导入部分结束-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>

<%
		

		PageCtrlInfo pageCtrl = new PageCtrlInfo();
		PageLoaderInfo pageLoaderInfo = null;
		//PageControl pageInfo = new PageControl();
     
		String strSuccessPageURL =  "";
		String strFailPageURL    =  "";
    try
	{

		 System.out.println("----------------- 进入页面 c007.jsp -----------------");
		
         // 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
			//获取页面控制信息
			pageCtrl.convertRequestToDataEntity(request);
			strSuccessPageURL = pageCtrl.getStrSuccessPageURL();
   			strFailPageURL = pageCtrl.getStrFailPageURL();
	//		Log.print("pagectrl-----------"+pageCtrl);
		 
		//分页工具pageLoader
		PageLoader pageLoader = null;
		
		//从request 中获得隐含业务数据
		String strPageLoaderKey = (String)request.getAttribute("pageLoaderKey");
		if( request.getAttribute("pageLoaderKey") == null )
		{
			strPageLoaderKey = (String)request.getParameter("pageLoaderKey");
		}
		
		//统计对象
		String strStatKey = "";
		
		//定义对象
		OBBudgetInfo paramInfo        = new OBBudgetInfo();
		OBBudgetInfo paramInfo_2      = new OBBudgetInfo();
	 	OBBudgetBiz biz               = new OBBudgetBiz();
 

 		/** 获取sessionMng数据 **/
		paramInfo.setClientID(sessionMng.m_lClientID);           //客户
		 

		/** 获取表单数据，设置AcctTransParam属性 **/		
		paramInfo.convertRequestToDataEntity(request);  
		 
		Log.print("paramInfo"+paramInfo);

 
		  
        if(pageCtrl.getStrAction().equals("findFirst"))
		{
            System.out.println("PageLoader 第一次查找");
		   
		   //列表信息
		   pageLoader = biz.query(paramInfo);
		 		 
		}
		else //if( pageCtrl.getStrAction().equals("find") )
		{
			 System.out.println("PageLoader 不是第一次查找");
			 
			paramInfo_2 = (OBBudgetInfo)sessionMng.getQueryCondition("queryConditions");
			
			//paramInfo_2.setDesc( paramInfo.getDesc() );
			//paramInfo_2.setOrderField( paramInfo.getOrderField() );
			
			//列表信息
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			
			pageLoaderInfo = (pageLoader == null)?null:pageLoader.getPageLoaderInfo();
			
			String strTemp = (String)request.getParameter("pageLoaderInfo_rowPerPage");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setRowPerPage(Integer.valueOf(strTemp).intValue());
			}
			strTemp = (String)request.getParameter("pageLoaderInfo_pageNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setPageNo(Integer.valueOf(strTemp).intValue());
			}
			
			strTemp = (String)request.getParameter("strOrderBy");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoader.setOrderBy( strTemp.trim() );
			}
			
			 
			
		}
		
		
		//生成pageLoader Key值
		strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
		
		
	    System.out.println("pageLoader 已经生成！！！！");
		
		//根据strPageLoaderKey，保存此查询条件param
		if( pageCtrl.getStrAction().equals("findFirst") )
		{
			sessionMng.setQueryCondition("queryConditions",paramInfo);
		}
		else if( pageCtrl.getStrAction().equals("find") )
		{
			sessionMng.setQueryCondition("queryConditions",paramInfo_2);
		}
		
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		request.setAttribute("strSuccessPageURL",pageCtrl.getStrSuccessPageURL());
		request.setAttribute("strFailPageURL",pageCtrl.getStrFailPageURL());
		
		pageCtrl.success();
		System.out.println("@@@@@@@@@@@@@@@@@comein cccccccccccccccc1111111111111");
		/**业务逻辑结束*/
	}
	catch( Exception exp )
	{
		 
		exp.printStackTrace();
		Log.print(exp.getMessage());
		pageCtrl.fail();
		OBHtml.showCommonMessage(out,sessionMng,"","",OBConstant.ShowMenu.NO,"Gen_E001");
	}
	
	
	String nextURL = strContext + "/pagecontrol.jsp";
	Log.print("进入下一页面："+nextURL);	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	System.out.println("@@@@@@@@@@@@@@@@@comein cccccccccccccccc 222222222222222222");
	rd.forward( request,response );
	
%>
