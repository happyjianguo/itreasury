<%--
/*
 * �������ƣ�batchck002-c.jsp
 * ����˵�����������˲�ѯ����
 * �������ߣ�����ξ
 * ������ڣ�2007��04��21��
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>

<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%String strContext = request.getContextPath();%>
<%!/* ����̶����� */
	String strTitle = "[��������]";%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	/* ʵ������Ϣ�� */
	//ʵ��
	FinanceInfo info = new FinanceInfo();
	OBFinanceInstrDao obstr = new OBFinanceInstrDao();//��ѯ����
	List infoList = null;
	String strStartDate = null;//��һ��ҳ�洫���Ŀ�ʼ����
	String strEndDate = null;//�ϸ�ҳ�洫���Ľ�������
	String status = null;//�洢״̬
	//��ѯ��

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try {
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<%
		//�����һҳ���ϵ���Ϣ����װ��ʵ����
		strStartDate = (String)request.getParameter("strStartDate");
		strEndDate = (String)request.getParameter("strEndDate");
		status = (String)request.getParameter("sStatus");
		info.setDtDepositBillCheckdate(DataFormat.getDateTime(strStartDate));//�������濪ʼ����
		info.setDtDepositBillInputdate(DataFormat.getDateTime(strEndDate));//���������������
		info.setNUserID(sessionMng.m_lUserID);
		info.setSStatus((String)request.getParameter("sStatus"));
		
		info.setClientID( sessionMng.m_lClientID );//����ָ�������ͻ�ID
		//������
		infoList = obstr.findBatchInfoByDatehl(info);
		request.setAttribute("infoList", infoList);
		request.setAttribute("strStartDate", strStartDate);
		request.setAttribute("strEndDate", strEndDate);
		
		//�����ת		
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/capital/check/batchck001-v.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("---------"+ie.toString());
		return;
	}	
		
 %>
