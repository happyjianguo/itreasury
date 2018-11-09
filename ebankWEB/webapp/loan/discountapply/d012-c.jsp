<%--
 页面名称 ：d012-c.jsp
 页面功能 : 新增贴现申请-贴现票据明细删除 控制页面
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
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d012-c.jsp*******");
	
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
		
		//根据d009-v的处理情况获取lID
		String[] strIDS = (String[])request.getParameterValues("lIDs");
		
		if(strIDS != null)
		{
			
			int tempInt = strIDS.length;
			
			long[] lIDs = new long[tempInt];
			
		   if(strIDS != null)
		   	
			for(int i=0;i< strIDS.length;i++)
			{
			   lIDs[i] = Long.valueOf(strIDS[i]).longValue();
			   Log.print("===========删除票据id:"+lIDs[i]);
			}
			
			OBDiscountApplyHome  obDiscountApplyHome = null;
	        OBDiscountApply      obDiscountApply = null;
	        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
	        obDiscountApply = obDiscountApplyHome.create();
			
			// OBDiscountApplyDao obDiscountApplyDAO = new OBDiscountApplyDao();//d
			// if(obDiscountApplyDAO.deleteDiscountBill(lIDs) > 0)//d
			if(obDiscountApply.deleteDiscountBill(lIDs) > 0)
			{
			    /* 获取上下文环境 */
		       ServletContext sc = getServletContext();
		       /* 设置返回地址 */
		       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d008-c.jsp")));
		       /* forward到结果页面 */
		       rd.forward(request, response);
			}
			else
			{
			   OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
			    return;
			}
		}
		else
		{
		    /* 获取上下文环境 */
		       ServletContext sc = getServletContext();
		       /* 设置返回地址 */
		       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d008-c.jsp")));
		       /* forward到结果页面 */
		       rd.forward(request, response);
		}	

    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


