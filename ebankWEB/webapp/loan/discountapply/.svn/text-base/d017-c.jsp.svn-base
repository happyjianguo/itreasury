<%--
 页面名称 ：d017-c.jsp
 页面功能 : 新增贴现申请-查询贴现申请详细信息 控制页面
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
                 com.iss.itreasury.ebank.obdiscountapply.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>
<%@ include file="/common/common.jsp" %>					
<%
  try
  {
	   Log.print("*******进入页面--ebank/loan/discountapply/d017-c.jsp*******");
	
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
		long lID = -1;
		
		String strTemp = "";
		String frompage="";
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("frompage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			frompage = strTemp;
		}
		
				
		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		
		DiscountInfo discountInfo = new DiscountInfo();
		discountInfo = obDiscountApply.findDiscountByID(lID);
		
		if(discountInfo != null)
		{
		   request.setAttribute("resultInfo",discountInfo);
		  /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d018-v.jsp?frompage="+frompage+"")));
	       /* forward到结果页面 */
	       rd.forward(request, response);
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }   
    

		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
%>


