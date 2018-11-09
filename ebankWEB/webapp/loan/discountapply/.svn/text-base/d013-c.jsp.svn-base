<%--
 页面名称 ：d013-c.jsp
 页面功能 : 新增贴现申请-查询一条贴现票据信息 控制页面
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
				 com.iss.itreasury.ebank.obdiscountapply.dao.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo,
				 java.util.Vector"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d013-c.jsp*******");
	
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
		String strTmp ="";
		long lDiscountBillID = -1;
		String subtypeid="";
		
		 strTmp = (String)request.getAttribute("lDiscountBillID");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 lDiscountBillID = Long.parseLong(strTmp.trim());
			 Log.print("\n＝＝＝＝＝票据id:"+lDiscountBillID);
		 }
		 strTmp = (String)request.getAttribute("subtypeid");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 subtypeid = strTmp;
		 }
		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		//modify by wjliu 2007/3/20 修改贴现申请-新增页面中点击序列号的进入详细页面的方法
		DiscountBillInfo discountBillInfo = null;
		//discountBillInfo = obDiscountApply.findDiscountBillByID(lDiscountBillID);
		//OBDiscountApplyDao obDiscountApplyDao = new OBDiscountApplyDao();//d
		//discountBillInfo = obDiscountApplyDao.findDiscountBillByID(lDiscountBillID);//d
		
		DiscountBillQueryInfo discountBillQueryInfo = new DiscountBillQueryInfo();
		discountBillQueryInfo.setDiscountID(lDiscountBillID);
		Vector v = null;
		v = obDiscountApply.findByID(discountBillQueryInfo);
		
		if(null != v && v.size() > 0)
		{
			discountBillInfo = (DiscountBillInfo)v.get(0);
		}
		
	
		if(discountBillInfo != null)
		{
		   Log.print("=====discountBillInfo:"+discountBillInfo.getID());
		   request.setAttribute("resultInfo",discountBillInfo);
		   request.setAttribute("subtypeid",subtypeid);
		 
		   /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d014-v.jsp")));
	       /* forward到结果页面 */
	       rd.forward(request, response);
		}
		else
		{
		   OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		   return;
		}

%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

