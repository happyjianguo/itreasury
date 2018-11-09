<%--
/*
 * �������ƣ�batchck003-c.jsp
 * ����˵�����������˲�ѯ����
 * �������ߣ�����ξ
 * ������ڣ�2007��04��23��
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
	String sbatchno = null;//���κ�
	String strSuccessURL = null;//�ɹ�ҳ��
	String strFaileURL = null;//ʧ��ҳ��
	//��ѯ��
	
		sbatchno = (String)request.getParameter("sbatchno");
		System.out.println("��������κţ�"+sbatchno);
		strSuccessURL = (String)request.getParameter("strSuccessURL");
		System.out.println("����ĳɹ���ַ��"+strSuccessURL);
		strFaileURL = (String)request.getParameter("strFaileURL");

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
		info.setSBatchNo(sbatchno.trim());//�洢��
		//������
		System.out.println("aaa����ĳɹ���ַ��" );
		infoList = obstr.findFinanceInfoByBatchno(info);
		System.out.println("bbbs����ĳɹ���ַ��" );
		System.out.println("�ϸ�ҳ���ѯ�Ľ����"+infoList);
		request.setAttribute("infoList", infoList);
		
		//�����ת
	
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/capital/check/batchck004-v.jsp?sbatchno=" + sbatchno);
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
		
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("batchck003-c�쳣��Ϣ��"+ie.toString());
		return;
	}	
		
 %>
