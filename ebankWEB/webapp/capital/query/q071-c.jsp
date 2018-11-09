<%--
 程序名称 ：queryLiShi.jsp
 功能说明 : 历史明细查询控制页面
 作    者 ：刘琰
 日    期 ：2003年11月25日
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection,com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.OBInterestWhereInfo,
				 com.iss.itreasury.ebank.obquery.dao.OBInterestQueryDao"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[历史明细]";
%>

<%
    try
	{	
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        //判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
		
		//定义变量
		String strClientCode = "";//客户号
		String strAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		long lAccountTypeID = -1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID

		String strEndDate = "";
		
		//读取数据
		if(request.getParameter("lAccountID")!=null)
		{
			lAccountID = Long.parseLong(request.getParameter("lAccountID")); //账户ID
		}
		if(request.getParameter("lAccountTypeID")!=null)
		{
			lAccountTypeID = Long.parseLong(request.getParameter("lAccountTypeID")); //账户类型ID
		}
		if(request.getParameter("lAccountGroupID")!=null)
		{
			lAccountGroupID = Long.parseLong(request.getParameter("lAccountGroupID")); //账户组ID
		}
		if(request.getParameter("tsEnd")!=null)
		{
			strEndDate = (String)request.getParameter("tsEnd");
		}
		
		//初始化并设置查询条件类
        OBInterestWhereInfo qtai = new OBInterestWhereInfo();
		OBInterestQueryDao qobj = new OBInterestQueryDao();
				
        
		qtai.setAccctNo(strAccountNo);//账号
		qtai.setAcctId(lAccountID);//账户ID
		qtai.setEndDate(strEndDate);
		
        //根据请求操作，完成业务处理的调用
		Collection coll = null;
		
        coll = qobj.queryInterest(qtai);
			
		/* 在请求中保存结果对象 */
		request.setAttribute("whereInfo",qtai);
		request.setAttribute("coll_resultInfo",coll);
	
		/* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	  
	   
	   //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/query/q070-v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	   
	    /* forward到结果页面 */
	    rd.forward(request, response);
	}
	catch( Exception e )
	{
		OBHtml.showExceptionMessage(out,sessionMng,(IException)e, strTitle,"",1);
		return;
	}
%>