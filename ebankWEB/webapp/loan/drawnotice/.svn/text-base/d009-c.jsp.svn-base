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
				com.iss.itreasury.ebank.obdrawnotice.dataentity.*,
				com.iss.itreasury.loan.loandrawnotice.dataentity.*,
				com.iss.itreasury.loan.loandrawnotice.bizlogic.*,
				com.iss.itreasury.ebank.obdrawnotice.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[银团贷款提款通知单]";
%>					
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;
	long lContractID = -1;
	long lLoanDrawNoticeID = -1;
	double mThisDrawAmount=0.0;
	String strTmp = "";
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
		

/////////////////////////////////////////page head/////////////////////////////////////		

/////////////////////////////////////////get param////////////////////////////////////

		strTmp = request.getParameter("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = request.getParameter("lLoanDrawNoticeID");//放款单ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanDrawNoticeID = Long.parseLong(strTmp.trim());
		}
///////////////////////////////////////////////////////////////////////////////////////////
				Log.print("--------------save------------");
				LoanDrawNoticeHome home = (LoanDrawNoticeHome) EJBObject.getEJBHome("LoanDrawNoticeHome");
				LoanDrawNotice ejb = home.create();
				LoanDrawNoticeInfo LDNinfo = null;
				//strControl="view";
				//log4j.info("放款提交");
				LDNinfo = new LoanDrawNoticeInfo();
				LDNinfo.setID(lLoanDrawNoticeID);
				LDNinfo.setContractID(lContractID);
				LDNinfo.setDrawAmount(mThisDrawAmount);
				LDNinfo.setInputUserID(lUserID);
				LDNinfo.setNextCheckUserID(lUserID);
				long ireturn = ejb.saveLoanDrawNotice(LDNinfo);
				if(ireturn > 0)
				{
%>
					<script language="JavaScript">
						alert("放款提交成功！");
					</script>
<%
					response.sendRedirect("d006-c.jsp?strAction=view&lLoanDrawNoticeID="+lLoanDrawNoticeID);
					return;
				}
				else
				{
%>
					<script language="JavaScript">
						alert("放款提交失败！");
						eval("history.back(-1)");
					</script>
<%
				}
%>		
		
<%
////////////////////////////////////page end////////////////////////////////////////

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>
