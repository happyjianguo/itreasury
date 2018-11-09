<%--
 页面名称 ：d001-c.jsp
 页面功能 : 新增贴现申请-保存贴现申请 控制页面
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
	   Log.print("*******进入页面--ebank/loan/discountapply/d001-c.jsp*******");
	
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
		 long lID = -1; //   贴现标识
		 long lClientID = -1; // 客户标识
		 long lCurrencyID = -1; // 币种
		 long lUserID = -1; //录入人标识
		 Timestamp tsDate = null; //录入时间
		 long lOfficeID = -1; // 办事处标识
		 long lStatusID = -1;// 状态
		
		DiscountMainInfo discountMainInfo = new DiscountMainInfo();
		
		lClientID = sessionMng.m_lClientID;
		lCurrencyID = sessionMng.m_lCurrencyID;
		lUserID = sessionMng.m_lUserID;
		tsDate = DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDate(1,1)));
		lOfficeID = sessionMng.m_lOfficeID;
		lStatusID = OBConstant.LoanInstrStatus.SAVE;

        discountMainInfo.setClientID(lClientID);
		discountMainInfo.setCurrencyID(lCurrencyID);
		discountMainInfo.setUserID(lUserID);
		discountMainInfo.setDate(tsDate);
		Log.print("============录入日期："+discountMainInfo.getDate());
		discountMainInfo.setOfficeID(lOfficeID);
		discountMainInfo.setStatusID(lStatusID);
		
		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		
		lID = obDiscountApply.saveDiscount1(discountMainInfo);
		
		Log.print("\n=========lID:"+lID);
		
		if(lID > 0)
		{
		   request.setAttribute("lID",lID+"");
		  /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d005-c.jsp")));
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


