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
/*///////////////////////////get parameter/////////////////////////
		lContractID = GetNumParam(request,"lContractID");
		lLoanPayID = GetNumParam(request,"lLoanPayID");
		lFreeApplyID = GetNumParam(request,"lFreeApplyID");
		strAction = GetParam(request,"strAction");
//////////////////////////get data///////////////////////////*/
		strTmp = (String)request.getAttribute("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		strTmp = (String)request.getAttribute("lContractID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID=Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lLoanPayID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanPayID=Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lFreeApplyID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lFreeApplyID=Long.parseLong(strTmp.trim());
		}
		Log.print(lContractID);
		Log.print(lLoanPayID);
		Log.print(lFreeApplyID);
		Log.print(strAction);
		queryInfo.setLContractID(lContractID);
		queryInfo.setLLoanPayID(lLoanPayID);
		queryInfo.setLFreeApplyID(lFreeApplyID);
		freeinfo = ejb.findFreeApply(queryInfo);
							/* �������б��������� */
	    request.setAttribute("freeinfo", freeinfo);
		request.setAttribute("strAction", strAction);
		Log.print("set freeinfo into attribute!");
///////business logic/////////////////////////////////////////////

		if(strAction.equals("addnew"))
		{
		
		Log.print("add new freeApply!!!");
	    ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f003-v.jsp")));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
		}else if(strAction.equals("edit"))
		{
			Log.print("edit freeApply!!!");
	    	ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    	RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f003-v.jsp")));
	    /* forward�����ҳ�� */
	    	rd.forward(request, response);
		}
		else if (strAction.equals("view")){
		
		Log.print("view freeApply!!!");
	    ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/freeapply/f005-v.jsp")));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
		}

///////business logic/////////////////////////////////////////////

}	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
%>