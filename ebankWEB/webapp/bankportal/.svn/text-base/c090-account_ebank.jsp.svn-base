
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dao.BillPrintBiz"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/><%
/**页面功能说明
 * 页面名称 ：c090-gd.jsp
 * 页面功能 : 对账单打印
 * 作    者 ：zcwang
 * 日    期 ：2008-4-29
 * 简单实现说明：
 *				1、查找符合条件的账户信息
 * 特殊说明 ：
 * 修改历史 ：
 */
%>
<%@ page contentType = "text/html;charset=GBK" %>

<!--类导入部分开始-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<!--类导入部分结束-->

<%
	
	PageControllerInfo pageCtrl = new PageControllerInfo();
	PageLoaderInfo pageLoaderInfo = null;
    try
	{
		//emoduleid等于6代表网银模块
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        //		out.flush();
		//		return;
		//}
		String strTitle = null;
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
		/**页面校验开始（用户登录校验、用户权限校验、重复请求校验）*/
		JSPLogger.info("*******进入页面--bankportal\\c090-account_ebank.jsp*******");
		//获取页面控制信息
		pageCtrl.convertRequestToDataEntity(request);
		//分页工具pageLoader
		PageLoader pageLoader = null;		
		//从request 中获得隐含业务数据
		String strPageLoaderKey = (String)request.getAttribute("pageLoaderKey");
		if( request.getAttribute("pageLoaderKey") == null )
		{
			strPageLoaderKey = (String)request.getParameter("pageLoaderKey");
		}		
		//定义对象
		QueryBillPrintParam paramInfo    = new QueryBillPrintParam();
		BillPrintBiz biz 				 = new BillPrintBiz();
		paramInfo.setOfficeID(sessionMng.m_lOfficeID);		
		paramInfo.setClientIdFrom(sessionMng.m_lClientID);
		paramInfo.setClientIdTo(sessionMng.m_lClientID);
		long currencyID=sessionMng.m_lCurrencyID;
		paramInfo.setCurrencyType(currencyID);
		paramInfo.setClientId(sessionMng.m_lCurrencyID);
		paramInfo.convertRequestToDataEntity(request);	
		if(null != paramInfo.getBankType()){
			if("-1".equals(paramInfo.getBankType())){
				paramInfo.setAllbankId("all");
			}else{
			   String bankid=NameRef.getBankIdByRefCode(paramInfo.getBankType());
				paramInfo.setAllbankId(bankid);
			}
		}	
		
        if(pageCtrl.getP_Action().equals("findFirst"))
		{
           JSPLogger.info("PageLoader 第一次查找--返回账户信息");
		   //列表信息
		   pageLoader = biz.findBillByCondition(paramInfo,sessionMng.m_lUserID);		   
		}
		else if( pageCtrl.getP_Action().equals("find") )
		{
			JSPLogger.info("PageLoader 不是第一次查找");
					
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
		// System.out.println("pageLoader 已经生成！！！！");
		//根据strPageLoaderKey，保存此查询条件param
		if( pageCtrl.getP_Action().equals("findFirst") )
		{
			request.setAttribute("queryInfo", paramInfo);
		}
		else if( pageCtrl.getP_Action().equals("find") )
		{
			request.setAttribute("queryInfo", paramInfo);
		}
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		request.setAttribute("strSuccessPageURL",pageCtrl.getP_SuccessPageURL());
		request.setAttribute("strFailPageURL",pageCtrl.getP_FailPageURL());
		//clientId放到session中
		sessionMng.setQueryCondition("clientId",new Long(paramInfo.getClientIdFrom()));
		pageCtrl.success();
		/**业务逻辑结束*/
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.info(exp.getMessage());
		pageCtrl.fail();
	}	
	String nextURL = "/pagecontrol.jsp";
	JSPLogger.info("进入下一页面："+nextURL);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);	
	rd.forward( request,response );	
%>