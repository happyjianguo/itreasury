<%
/**页面功能说明
 * 页面名称 ：c001.jsp
 * 页面功能 : 当日余额查询   控制层
 * 作    者 ：barneyliu	
 * 日    期 ：2005-11-15
 * 简单实现说明：
 *				1、历史交易查询
 * 特殊说明 ：
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk"%>
<!--类导入部分开始-->
 
<%@ page import="com.iss.itreasury.util.Log"%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBTodayBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
 
<!--类导入部分结束-->

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
		

		PageCtrlInfo pageCtrl = new PageCtrlInfo();
 		String strSuccessPageURL =  "";
		String strFailPageURL    =  "";
    try
	{

		 System.out.println("----------------- 进入页面 c001.jsp -----------------");
		/* 用户登录检测 */
        /*if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }*/
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		
			//获取页面控制信息
			pageCtrl.convertRequestToDataEntity(request);
			strSuccessPageURL = pageCtrl.getStrSuccessPageURL();
   			strFailPageURL = pageCtrl.getStrFailPageURL();
	//		Log.print("pagectrl-----------"+pageCtrl);
		 
	 
		//定义对象
		OBQueryAccInfo paramInfo        = new OBQueryAccInfo();
	  	OBTodayBalanceBiz biz             = new OBTodayBalanceBiz();
		OBTodayBalanceResultInfo[] OBtodaybalanceresults = null; 
 
		
		/** 获取sessionMng数据 **/
		paramInfo.setClientid(sessionMng.m_lClientID);           //客户
		paramInfo.setUserid(sessionMng.m_lUserID);				 //用户
		  
		
		/** 获取表单数据，设置AcctTransParam属性 **/		
		paramInfo.convertRequestToDataEntity(request);
		Log.print("paramInfo"+paramInfo);
	
	    System.out.println("进入查找");
		   
		   //列表信息
		   OBtodaybalanceresults = biz.QueryTodayBalance(paramInfo);
		 		 
	 
	    System.out.println("已查完！！！！");
		
		request.setAttribute("OBtodaybalanceresults",OBtodaybalanceresults);
		
		request.setAttribute("strSuccessPageURL",pageCtrl.getStrSuccessPageURL());
		request.setAttribute("strFailPageURL",pageCtrl.getStrFailPageURL());
		
		pageCtrl.success();
		
		/**业务逻辑结束*/
	}
	catch( Exception exp )
	{
		 
		exp.printStackTrace();
		Log.print(exp.getMessage());
		pageCtrl.fail();
		OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
	}
	
	
	String nextURL = pageCtrl.getStrNextPageURL();
	Log.print("进入下一页面："+nextURL);	
//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	
	rd.forward( request,response );
	
%>