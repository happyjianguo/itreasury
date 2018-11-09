<%
	/*
	*页面名称：D006-c.jsp
	*功能：银团提款通知单
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
	long lStatusID = -1;
	String strTmp = "";
	double mThisDrawAmount = 0;
	
	DrawNoticeInfo LDNinfo = null;
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
		strTmp = (String)request.getAttribute("mThisDrawAmount");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				mThisDrawAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
			}
			catch(Exception e)
			{
				;
			}
		}
		
		
		OBDrawNoticeHome home = (OBDrawNoticeHome) EJBObject.getEJBHome("OBDrawNoticeHome");
		OBDrawNotice ejb = home.create();
/////////////////////////////////////////get param////////////////////////////////////

		strTmp = request.getParameter("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = request.getParameter("lLoanDrawNoticeID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanDrawNoticeID = Long.parseLong(strTmp.trim());
		}
		
		lStatusID = OBConstant.LoanInstrStatus.CANCEL;
///////////////////////////////////////////////////////////////////////////////////////////

				Log.print("--------------cancel------------");
				//strControl="view";
				long lResult=ejb.updateStatus(lLoanDrawNoticeID,lStatusID);
				if(lResult>0)
				{
%>
					<script language="JavaScript">
						alert("取消成功！");
					</script>
<%
					response.sendRedirect("d001-v.jsp");
					return;
				}
				else
				{
%>
					<script language="JavaScript">
						alert("取消失败！");
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
