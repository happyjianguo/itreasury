<%--
/*
 * 程序名称：C825.jsp
 * 功能说明：系统管理-收款方资料删除控制页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"

%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%
	//固定变量
	String strTitle = "[收款方资料维护--新增收款方资料]";
%>

<%
	/* 用户登录检测与权限校验 */
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
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>


<%
	try
	{
      	String strID[] = request.getParameterValues("txtIDCheckbox");
      	
		//下面可以调用ejb的方法了
		OBSystemHome obsystemhome = null;//定义本地接口
		OBSystem obsystem = null;//定义远程接口
		obsystemhome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obsystem = obsystemhome.create();
	  	int iCount = strID.length;
	  	for (int i=0;i<iCount;i++)
	  	{
			long lTmp = Long.parseLong(strID[i]);
			Log.print("strID:  "+lTmp);
			obsystem.deletePayee(lTmp);
	  	}

      	//下面传IsCPF表示是中油用户,用于控制S823-Ctr.jsp转到不同的页面
		request.setAttribute("IsCPF",request.getParameter("txtIsCPF").trim());
      	//获取上下文环境
      	//ServletContext sc = getServletContext();
      	//设置返回地址
      	String flag = (String)request.getParameter("flag");
      	if(flag!=null&&flag.equals("deleted")){
      		request.setAttribute("delete","delete");
      	}
      	
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
      	//forward到结果页面
      	rd.forward(request, response);
   	}
   	catch(IException ie)
   	{
 	 	OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
   	}

%>

