<%
	/*
	*ҳ�����ƣ�D006-c.jsp
	*���ܣ��������֪ͨ��
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
	/* ����̶����� */
	String strTitle = "[���Ŵ������֪ͨ��]";
%>				
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//���ұ�ʶ
	long lOfficeID=sessionMng.m_lOfficeID;//���´���ʶ
	long lUserID = sessionMng.m_lUserID;
	long lContractID = -1;
	long lLoanDrawNoticeID = -1;
	long lStatusID = -1;
	String strTmp = "";
	double mThisDrawAmount = 0;
	
	DrawNoticeInfo LDNinfo = null;
	//Log.print("*******����ҳ��--ebank/loan/discountapply/d011-c.jsp*******");
	
	   /* �û���¼��� */
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

		strTmp = request.getParameter("lContractID");//��ͬID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = request.getParameter("lLoanDrawNoticeID");//��ͬID
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
						alert("ȡ���ɹ���");
					</script>
<%
					response.sendRedirect("d001-v.jsp");
					return;
				}
				else
				{
%>
					<script language="JavaScript">
						alert("ȡ��ʧ�ܣ�");
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
