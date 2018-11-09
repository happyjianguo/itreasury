<%--
/*
 * �������ƣ�C17.jsp
 * ����˵�����ʽ𻮲�ɾ������ҳ��
 * �������ߣ�����
 * ������ڣ�2003��09��21��
 */
--%>

<!--Header start-->
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 javax.servlet.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ʽ𻮲�]";
%>

<%
	/* ָ����� */
	long lInstructionID = -1;
	/* ��¼��ID */
	long lUserID = sessionMng.m_lUserID;

	try 
	{
		lInstructionID = GetNumParam(request,"lInstructionID");
	} 
	catch(Exception e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
	/* ȷ�����ؽ�� */
	long lDeleteResult = -1;
try{
	if (lInstructionID > 0)
	{
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("FinanceInstrHome");
		financeInstr = financeInstrHome.create();
		/* ����EJB����ɾ����Ϣ���� */
		lDeleteResult = financeInstr.deleteCapitalTrans(lInstructionID, lUserID);
		Log.print(lDeleteResult);
	} 
	else 
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		return;
	}
%>
<!--Access DB end-->

<!--Forward start-->
<%
	if (lDeleteResult > 0)
	{
		/* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/finance/C15.jsp?lInstructionID=-1");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
	} 
	}catch(Exception e) {
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		
		return;
	}
%>
<!--Forward end-->