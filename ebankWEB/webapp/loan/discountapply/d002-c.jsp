<%--
 页面名称 ：d002-c.jsp
 页面功能 : 新增贴现申请-保存贴现申请 查询借款单位详细信息控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d002-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
		String strTemp = "";
		long lClientID = sessionMng.m_lClientID;
		//Log.print("=============客户ID："+lClientID);
		String strBackPage = "";
		
		strTemp = (String)request.getAttribute("strBackPage");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strBackPage = DataFormat.toChinese(strTemp.trim());
		}
		
		OBSystemHome  obSystemHome = null;
        OBSystem      obSystem = null;
        obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
        obSystem = obSystemHome.create();
        ClientInfo clientInfo = null;
		
		clientInfo = obSystem.findClientByID(lClientID);
		
		if(clientInfo != null)
		{
		  request.setAttribute("resultInfo",clientInfo);
		  
		  
		  /* 获取上下文环境 */
	      ServletContext sc = getServletContext();
		  /* 设置返回地址 */
		  RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d003-v.jsp")));
	      /* forward到结果页面 */
	      rd.forward(request, response); 
		  
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


