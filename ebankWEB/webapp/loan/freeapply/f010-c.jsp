<%
	/*
	*页面名称：f002-c.jsp
	*功能：免还申请
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[免还申请提交]";
%>					
<%
  try
  {
  
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;
	String strTmp = "";

	long lFreeApplyID = -1;
	long lstatusID = -1;
	
	//Log.print("*******进入页面--ebank/loan/discountapply/d011-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home  = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
	////////////////////////////////////////////////////////////////////
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
		String code = GetParam(request,"code");
		
		lstatusID = OBConstant.LoanInstrStatus.SUBMIT;
	////////////////////////////////////////////////////////////////////
					
		long lResult=ejb.updateStatus(lFreeApplyID,lstatusID);
	/////////////////////////////////////////////////////////////////////////////		
				//log4j.info("提交申请免还");
				
				if(lResult>0)
				{
%>
					<script language="JavaScript">
						alert("提交免还成功！");
					</script>
<%
					response.sendRedirect("f009-v.jsp?code="+code+"&lFreeApplyID="+lFreeApplyID);
					return;
				}
				else
				{
					//log4j.info("提交免还失败");
%>
					<script language="JavaScript">
						alert("提交免还失败！");
						eval("history.back(-1)");
					</script>
<%
				}
	///////////////////////////////////////////////////////////////////////////////

	}catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
	%>
























