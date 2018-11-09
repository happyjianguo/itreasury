<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    try
	{
		//请求检测
		//if(!SETTHTML.validateRequest(out, request,response)) return;
		
        //定义变量
		String key = null;
		PageLoader pageLoader = null; 
		PageLoaderInfo pageLoaderInfo = null;
		Object[] searchResults = null;

        //读取数据		
        strAction = (String)request.getParameter("strAction");
		strSuccessPageURL = (String)request.getParameter("strSuccessPageURL");
		strFailPageURL = (String)request.getParameter("strFailPageURL");
		key = (String)request.getAttribute("_pageLoaderKey");

		pageLoader = sessionMng.getPageLoader(key); 
		pageLoaderInfo = (pageLoader == null)?null:pageLoader.getPageLoaderInfo();
		
		Log.print(" PageControl - pageLoader : " + pageLoader);
		Log.print(" PageControl - strSuccessPageURL : " + strSuccessPageURL);
		Log.print(" PageControl - strFailPageURL : " + strFailPageURL);
		Log.print(" PageControl - strAction : " + strAction);
		Log.print(" PageControl - Key : " + key);

		if(pageLoaderInfo != null)
		{
			String strTemp = (String)request.getAttribute("pageLoaderInfo.rowPerPage");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setRowPerPage(Integer.valueOf(strTemp).intValue());
			}
			strTemp = (String)request.getAttribute("pageLoaderInfo.pageNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setPageNo(Integer.valueOf(strTemp).intValue());
			}
		}

        //根据请求操作，完成业务处理的调用
        if(String.valueOf(Constant.PageControl.FIRSTPAGE).equals(strAction))
        {			
			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回第一页结果
				searchResults = pageLoader.firstPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.LASTPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回最后一页结果
				searchResults = pageLoader.lastPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.NEXTPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回下一页结果
				searchResults = pageLoader.nextPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.PREVIOUSPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回上一页结果
				searchResults = pageLoader.previousPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.GOTOPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回指定页结果
				searchResults = pageLoader.gotoPage(pageLoaderInfo.getPageNo());

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.LISTALL).equals(strAction))
        {
			if (pageLoader != null)
			{
				//取回所有结果
				searchResults = pageLoader.listAll();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
        else
        {
            //未指定分页操作时的默认执行方法，执行查询操作
			//业务处理逻辑...
			//查询方法.
			//pageLoader = dao.search();

			//取回第一页结果,并且保留在FORM中,目的在页面中显示使用.
			//this.setSearchResult(pageLoader.firstPage());


			//将pageLoader缓存入SESSION中.
			//request.getSession().setAttribute(SessionConstants.PAGELOADER_KEY, pageLoader);

			//if (pageLoader.getPageLoaderInfo().getRowCount() <= 0)
			//{
			//	sessionMng.getActionMessages().addMessage("无查询结果");
			//}

			//strActionResult = Constant.ActionResult.SUCCESS;

			if (pageLoader != null)
			{
				//将FORM中改变的页面信息更新到PAGELOADER中
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//取回第一页结果
				searchResults = pageLoader.firstPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
        }

		request.setAttribute(Constant.PageControl.SearchResults,searchResults);
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;	
	}
	
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//转向下一页面
	Log.print("Next Page URL:"+strNextPageURL);	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	if(rd != null)
	{
		rd.forward( request,response );
	}
	else
	{
		String strMessage = "\""+strNextPageURL+"\"可能不正确，无法完成页面跳转。\r\n（注：跳转目标路径必须是绝对路径）";
		out.print(strMessage);
		System.out.println(strMessage);
	}
%>