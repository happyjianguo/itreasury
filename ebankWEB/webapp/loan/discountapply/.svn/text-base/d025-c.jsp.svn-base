<%--
 页面名称 ：d025-c.jsp
 页面功能 : 查询贴现票据明细 控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 java.util.Vector,
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
	Log.print("*******进入页面--ebank/loan/discountapply/d025-c.jsp*******");
	
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
		long lID = -1;//贴现申请Id
		long lContractID = -1;
	    long lPageLineCount = 1000;
	    long lPageNo = 1;
		long lPageCount = 0;
	    long lOrderParam = 1;
	    long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	    double dRate = 0.0;
	    Timestamp tsDate = null;//贴现日
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
	    OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		
		DiscountInfo discountInfo = null;
		
		discountInfo = obDiscountApply.findDiscountByID(lID);
		
		if(discountInfo != null)
		{
			lID = discountInfo.getID();
			//lContractID = -1;
		    //lPageLineCount= -1;
		    //lPageNo = -1;
		    //lOrderParam = -1;
		    //lDesc = -1;
		    dRate = discountInfo.getDiscountRate();
		    tsDate = discountInfo.getDiscountStartDate();
			
			request.setAttribute("resultDiscountInfo",discountInfo);
		}
		
		DiscountBillQueryInfo discountBillQueryInfo = new DiscountBillQueryInfo();
		 
		discountBillQueryInfo.setDiscountID(lID);
	    discountBillQueryInfo.setContractID(lContractID);
	    discountBillQueryInfo.setPageLineCount(lPageLineCount);
	    discountBillQueryInfo.setPageNo(lPageNo);
	    discountBillQueryInfo.setOrderParam(lOrderParam);
	    discountBillQueryInfo.setDesc(lDesc);
	    discountBillQueryInfo.setRate(dRate);
	    discountBillQueryInfo.setDate(tsDate);
        
		Vector vctDiscountBillInfo = null;
		
		vctDiscountBillInfo = obDiscountApply.findDiscountBillByDiscountID(discountBillQueryInfo);

		if(vctDiscountBillInfo != null)
		{
		   request.setAttribute("resultInfo",vctDiscountBillInfo);
		  /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d026-v.jsp")));
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
