<%--
/*
 * �������ƣ�v001.jsp
 * ����˵��������Ԥ������ҳ
 * �������ߣ�banreyliu
 * ������ڣ�2006-09-04
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<!-- ������Ҫ����,��������.* -->
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.OBBudgetDelegation"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	 /**
	 * ҳ�������
	 */
	String strTitle = "Ԥ������";
	PageCtrlInfo pageInfo = new PageCtrlInfo();

	/** ����ҵ��ʵ���� */
	OBBudgetInfo info = new OBBudgetInfo();

	try {
		/** Ȩ�޼�� **/
		System.out
		.println("=================����ҳ��../control/c001.jsp=========");

		// �û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

		pageInfo.convertRequestToDataEntity(request);
		//System.out.println("barney@@@@@@"+pageInfo);

		long period = -1;
		String strTemp = "";
		String startDate = "";
		strTemp = (String) request.getAttribute("period");
		if (strTemp != null && !strTemp.equals("")) {
			period = Long.parseLong(strTemp);
		}

		strTemp = (String) request.getParameter("startDate");
		if (strTemp != null && !strTemp.equals("")) {
			startDate = strTemp;
		}

		info.convertRequestToDataEntity(request);

		request.setAttribute("period", String.valueOf(period));
		request.setAttribute("startdate", startDate);

		OBBudgetBiz biz = new OBBudgetBiz();
		long retlong = -1;
		retlong = biz.check(info);
		if (retlong == Constant.FALSE) {
			//sessionMng.getActionMessages().addMessage("����ɹ�!");
			//pageInfo.setStrNextPageURL("../view/v001.jsp");
			pageInfo.success();
			pageInfo
			.setStrNextPageURL("../view/v001.jsp?ID=" + retlong);
		} else {
			pageInfo.setStrNextPageURL(pageInfo.getStrSuccessPageURL());
		}

		request.setAttribute("strActionResult", pageInfo
		.getStrActionResult());

		System.out.println("Next Page URL:"
		+ pageInfo.getStrNextPageURL());
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
		//�ַ�
		RequestDispatcher rd = request
		.getRequestDispatcher(PageController
				.getDispatcherURL(pageControllerInfo));
		rd.forward(request, response);

	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
<input type="hidden" name="transCode" value="">
<input type="hidden" name="sname" value="<%=info.getSname()%>">
<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
<input type="hidden" name="note" value="<%=info.getNote()%>">
