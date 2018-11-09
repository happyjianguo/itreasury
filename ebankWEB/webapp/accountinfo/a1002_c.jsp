<%--
 ҳ������ ��c002.jsp
 ҳ�湦�� : ���ڿ����������Ӳ���ҳ��Ŀ�����ҳ��
 ��    �� ��xrli
 ��    �� ��2003-09-27
 ����˵�� ��ʵ�ֲ���˵����
				1�����Ӳ���
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.bankportal.integration.info.ReqGetGeneralBankAcctInfo"%>
<%@ page import="com.iss.itreasury.bankportal.integration.info.RespGetGeneralBankAcctInfo"%>
<%@ page import="com.iss.itreasury.bankportal.integration.client.BPClientAgent"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	RespGetGeneralBankAcctInfo generalbankacctinfo = new RespGetGeneralBankAcctInfo();
	ReqGetGeneralBankAcctInfo instruction=new ReqGetGeneralBankAcctInfo();
    try
	{
			/* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }

        //�������		


		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		//��ȡ����
		String lAccountID ="";
		String strTemp = null;		
		strTemp = (String)request.getAttribute("lAccountIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountID = strTemp;
		}
		instruction.setSystemId(1);
		instruction.setReferenceCode(lAccountID);
		generalbankacctinfo = BPClientAgent.getGeneralBankAcctInfo(instruction);//��û����˻����ϼ������˺ŵ�λ
		strActionResult = Constant.ActionResult.SUCCESS;
		strSuccessPageURL = strSuccessPageURL + "?isTrue=true";
		request.setAttribute("generalbankacctinfo",generalbankacctinfo);
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;	
	//ת����һҳ��
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response);
%>