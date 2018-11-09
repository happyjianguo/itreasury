<%--
 页面名称 ：l023-c.jsp
 页面功能 : [贷款还款]--利息费用清还
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
	String strTitle = "[贷款还款]--利息费用清还";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l023-c.jsp*******");
	
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
		long lPayeeAcctID = -1;
		long lLoanNoteID = -1;//放款通知单id
		Timestamp tsClearInterest = null;//结息日
		long lLoanAcctID = -1;
		strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");//收款方账户ID（即贷款账户id）
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanAcctID = Long.valueOf(strTemp).longValue();
		 }
		
	     strTemp = (String)request.getAttribute("lPayeeAcctID");//收款方账户ID（即贷款账户id）
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lPayeeAcctID = Long.valueOf(strTemp).longValue();
		 }
		 
		 strTemp = (String)request.getAttribute("lLoanNoteID");// 放款通知单id
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanNoteID = Long.valueOf(strTemp).longValue();
		 }
		 
		 strTemp = (String)request.getAttribute("tsClearInterest");//结息日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsClearInterest =  DataFormat.getDateTime(strTemp);
		 }
		 	
		  SubLoanAccountDetailInfo subLoanAccountDetailInfo = null;
		
		  subLoanAccountDetailInfo = new SubLoanAccountDetailInfo();
		  //设置查询条件
		  subLoanAccountDetailInfo.setOfficeID(sessionMng.m_lOfficeID);
		  subLoanAccountDetailInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		  subLoanAccountDetailInfo.setLoanNoteID(lLoanNoteID);
		  subLoanAccountDetailInfo.setInterestStart(tsClearInterest);
		  subLoanAccountDetailInfo.setLoanAccountID(lLoanAcctID);
		  
	      OBFinanceInstrHome  obFinanceInstrHome = null;
          OBFinanceInstr      obFinanceInstr = null;
          obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
          obFinanceInstr = obFinanceInstrHome.create();
		  subLoanAccountDetailInfo = obFinanceInstr.findSubLoanAccountDetailByCondition(subLoanAccountDetailInfo);
		 
		 
		if(subLoanAccountDetailInfo != null)
		{
		   request.setAttribute("resultInfo",subLoanAccountDetailInfo);
		}
		
		/* 获取上下文环境 */
        //ServletContext sc = getServletContext();
        /* 设置返回地址 */
	   
	    
	    
	    //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/loanrepayment/l024-v.jsp");
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
