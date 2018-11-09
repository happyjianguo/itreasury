<%--
 页面名称 ：l001-c.jsp
 页面功能 : [贷款还款]--自营贷款
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
            根据财务交易指令ID，查询指令信息
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款]--自营贷款";
%>					
<%
  try
  {
	   Log.print("*******进入页面--ebank/capital/loanrepayment/l001-c.jsp*******");
	
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
		long lInstructionID = -1;//财务指令id
		long lUserID = sessionMng.m_lUserID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		
		 strTemp = (String)request.getAttribute("lID");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lInstructionID = Long.valueOf(strTemp).longValue();
		 }
		
		 OBFinanceInstrHome  obFinanceInstrHome = null;
         OBFinanceInstr      obFinanceInstr = null;
         obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
         obFinanceInstr = obFinanceInstrHome.create();
		
		 FinanceInfo financeInfo = null;
		 financeInfo = obFinanceInstr.findByID(lInstructionID,lUserID,lCurrencyID);
		 
		 if(financeInfo != null)
		 {
		   request.setAttribute("resultInfo",financeInfo);
		   
		 }
		 
		 //获取贷款账号
		PayerOrPayeeInfo payerOrPayeeInfo = obFinanceInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.TRUST);
		if(payerOrPayeeInfo != null)
		{
		  request.setAttribute("payerOrPayeeInfo",payerOrPayeeInfo);
		}
           /* 获取上下文环境 */
	       //ServletContext sc = getServletContext();
	       /* 设置返回地址 */
		  
		   
		   
		   //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/loanrepayment/l002-v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		   /* forward到结果页面 */
		   rd.forward(request, response);
%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
