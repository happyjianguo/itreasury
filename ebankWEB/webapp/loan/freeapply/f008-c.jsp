<%
	/*
	*ҳ�����ƣ�f002-c.jsp
	*���ܣ��⻹����
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
	/* ����̶����� */
	String strTitle = "[�⻹�����ύ]";
%>					
<%
  try
  {
  
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//���ұ�ʶ
	long lOfficeID=sessionMng.m_lOfficeID;//���´���ʶ
	long lUserID = sessionMng.m_lUserID;
	String strTmp = "";

	long lFreeApplyID = -1;
	long lstatusID = -1;
	
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
///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home  = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
	////////////////////////////////////////////////////////////////////
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
		lstatusID = OBConstant.LoanInstrStatus.CANCEL;
	////////////////////////////////////////////////////////////////////
					
		long lResult=ejb.updateStatus(lFreeApplyID,lstatusID);
	/////////////////////////////////////////////////////////////////////////////		
				//log4j.info("ȡ�������⻹");
				
				if(lResult>0)
				{
%>
					<script language="JavaScript">
						alert("ȡ���⻹�ɹ���");
					</script>
<%
					response.sendRedirect("f001-v.jsp");
					return;
				}
				else
				{
					//log4j.info("ȡ���⻹ʧ��");
%>
					<script language="JavaScript">
						alert("ȡ���⻹ʧ�ܣ�");
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
























