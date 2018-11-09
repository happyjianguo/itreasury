<%--
 页面名称 ：l013-c.jsp
 页面功能 : [贷款还款]--委托贷款
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
	String strTitle = "[贷款还款]--委托贷款";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l013-c.jsp*******");
	
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
		double dAmount = 0.0;//本金金额
		double dBalance = 0.0;//贷款余额
		Timestamp tsExecuteDate = null;//执行日
		
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
		 
		  strTemp = (String)request.getAttribute("tsExecuteDate");//执行日
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			tsExecuteDate =  DataFormat.getDateTime(strTemp);
		 }
		 
	 	strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		 
		strTemp = (String)request.getAttribute("dBalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dBalance = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		} 
		
		SubLoanAccountDetailInfo subLoanAccountDetailInfo = null;
		
		  subLoanAccountDetailInfo = new SubLoanAccountDetailInfo();
		  //设置查询条件
		   subLoanAccountDetailInfo.setOfficeID(sessionMng.m_lOfficeID);
		   Log.print("办事处id:"+sessionMng.m_lOfficeID);
		   subLoanAccountDetailInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		   subLoanAccountDetailInfo.setLoanNoteID(lLoanNoteID);
		   subLoanAccountDetailInfo.setInterestStart(tsExecuteDate);//在自营贷款清还中，设计为(结息日 = 执行日)
		   subLoanAccountDetailInfo.setLoanAccountID(lLoanAcctID);//贷款账号
		   Log.print("贷款账号:"+lLoanAcctID);
		  
	      OBFinanceInstrHome  obFinanceInstrHome = null;
          OBFinanceInstr      obFinanceInstr = null;
          obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
          obFinanceInstr = obFinanceInstrHome.create();
		  subLoanAccountDetailInfo = obFinanceInstr.findSubLoanAccountDetailByCondition(subLoanAccountDetailInfo);
		 
		  if(dAmount < dBalance)
		{
		  subLoanAccountDetailInfo.setInterest(0.0);
		  subLoanAccountDetailInfo.setCompoundInterest(0.0);
		  subLoanAccountDetailInfo.setOverDueInterest(0.0);
		  subLoanAccountDetailInfo.setSuretyFee(0.0);
		  subLoanAccountDetailInfo.setCommission(0.0);
		  subLoanAccountDetailInfo.setTotal(0.0);
		  
		  //lxr后增加
		  subLoanAccountDetailInfo.setInterestTax(0.0);		
		}
		 
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
	pageControllerInfo.setUrl("/capital/loanrepayment/l014-v.jsp");
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
