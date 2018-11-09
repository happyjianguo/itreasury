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
	String strTitle = "[�⻹����]";
%>					
<%
  try
  {
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//���ұ�ʶ
	long lOfficeID=sessionMng.m_lOfficeID;//���´���ʶ
	long lUserID = sessionMng.m_lUserID;
	
	
	
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
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
		FreeApplyQueryInfo queryInfo = new FreeApplyQueryInfo();

////////////////////////////////////////////////////////////////////*

		//�����������ȡ�������

		String strLoanClientName="";//������� ����/����
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		
		long lFreeApplyID = -1;
		long lTypeID=-1;
		long lContractID=-1;		//��ͬ��ʾ
		long lLoanPayID=-1;//�ſ�֪ͨ��ID
////////////////////////////get parameter/////////////////////////
		lContractID = GetNumParam(request,"lContractID");
		lLoanPayID = GetNumParam(request,"lLoanPayID");
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lContractID);
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lLoanPayID);
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@="+lFreeApplyID);
//////////////////////////get data///////////////////////////
		queryInfo.setLContractID(lContractID);
		queryInfo.setLLoanPayID(lLoanPayID);
		queryInfo.setLFreeApplyID(lFreeApplyID);
		freeinfo = ejb.findFreeApply(queryInfo);
							/* �������б��������� */
	    request.setAttribute("freeinfo", freeinfo);
		Log.print("set freeinfo into attribute!");
///////business logic/////////////////////////////////////////////

		if(freeinfo!=null)
		{
		
		Log.print("print");
	    ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f007-v.jsp")));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
		}
		else {
%>
					<script language="JavaScript">
						alert("�޷�ȡ�ò�����");
						eval("history.back(-1)");
					</script>
<%
		}

///////business logic/////////////////////////////////////////////

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>