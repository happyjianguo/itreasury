<%--
/*
 * �������ƣ�C821.jsp
 * ����˵����ϵͳ����-�տ������������ҳ��
 * �������ߣ�����
 * ������ڣ�2003��10��20��
 */
--%>

<!--Header start-->
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.obsystem.bizlogic.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.NameRef,java.rmi.*,java.lang.*,com.iss.itreasury.util.*,java.sql.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%
	String strTitle = "[�տ����ά��--�����տ����]";
	/* �û���¼�����Ȩ��У�� */
	try {
		/* �û���¼��� */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		//�ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
	} catch (Exception e) {
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e,
		strTitle, "", 1);
		return;
	}
%>


<!--Get Data start-->
<%
	String sNextPage = "";
	if (request.getParameter("sAddFlag") != null
			&& request.getParameter("sAddFlag").equals("1")) {
		sNextPage = strContext + "/system/V820.jsp";
	}

	try {
		ClientCapInfo clientCapInfo = new ClientCapInfo();

		//�����Ƿ������û��Ĳ�����
		String strISCPF = GetParam(request, "txtIsCPF").trim();

		//�������л�ȡ����
		if (strISCPF.equalsIgnoreCase("true")) {

			clientCapInfo.setPayeeAccoutNO(NameRef.setAccontLine(GetParam(request, "txtPayeeBankNO").trim()));//�տ�˺�
			clientCapInfo.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//�����Ͳ����ڲ��˻���
			clientCapInfo.setPayeeBankName(Env.getClientName());//���������ƣ��̶�

		} else {
			clientCapInfo
			.setPayeeName(GetParam(request, "txtPayeeName")
			.trim());
			clientCapInfo
			.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//�������Ͳ����ڲ��˻�
			clientCapInfo.setPayeeAccoutNO(GetParam(request,
			"txtPayeeAccoutNO").trim());
			clientCapInfo.setPayeeBankName(GetParam(request,
			"txtPayeeBankName"));//����������
			clientCapInfo
			.setPayeeProv(GetParam(request, "txtPayeeProv")
			.trim());//txtPayeeProv
			clientCapInfo.setCity(GetParam(request, "txtCity").trim());//txtCity
			clientCapInfo.setSPayeeBankCNAPSNO(GetParam(request, "txtPayeeBankCNAPSNO").trim());
			clientCapInfo.setSPayeeBankExchangeNO(GetParam(request, "txtPayeeBankExchangeNO").trim());
			clientCapInfo.setSPayeeBankOrgNO(GetParam(request, "txtPayeeBankOrgNO").trim());
		}
		//��session��ȡֵ		
		clientCapInfo.setClientID(sessionMng.m_lClientID);//����ͻ�ID ��session �ͻ�ID
		clientCapInfo.setCurrencyID(sessionMng.m_lCurrencyID);//���֣�session ����
		clientCapInfo.setInputUserID(sessionMng.m_lUserID);//¼���ˣ�session��ȡֵ
		clientCapInfo.setLofficeid(sessionMng.m_lOfficeID); //���´�
		//��ʼ��ejb
		OBSystemHome obsystemhome = null;//���屾�ؽӿ�
		OBSystem obsystem = null;//����Զ�̽ӿ�
		obsystemhome = (OBSystemHome) EJBObject
		.getEJBHome("OBSystemHome");
		obsystem = obsystemhome.create();

		//�������������޸�
		int state = 1;

		//������ˮ��
		long lID = -1;
		String strID = (String) request.getParameter("txtIDCheckbox");

		if ((strID != null) && (strID.length() > 0)) {
			clientCapInfo.setID(Long.parseLong(strID));
			Log.print("��ˮ��:" + clientCapInfo.getID());
		}

		if (clientCapInfo.getID() < 0) {
			//����տ���ƣ��˺ź����ݿ�ļ�¼���ظ��Ļ������޸�
			lID = obsystem.addPayee(clientCapInfo);
			Log.print("�տ����Add");
			clientCapInfo.setID(lID);

			if (strISCPF.equalsIgnoreCase("true")) {
		ClientCapInfo info1 = null;
		info1 = obsystem.findAccount(sessionMng.m_lClientID,
				sessionMng.m_lCurrencyID, clientCapInfo
				.getPayeeAccoutNO(),
				sessionMng.m_lOfficeID);
		clientCapInfo.setPayeeName(info1.getPayeeName());
			}
		} else {
			state = 2;
			lID = obsystem.updatePayee(clientCapInfo);
		  //  lID = obsystem.addPayee(clientCapInfo);
			Log.print("�տ����Update");
			if (strISCPF.equalsIgnoreCase("true")) {
		ClientCapInfo info1 = null;
		info1 = obsystem.findAccount(sessionMng.m_lClientID,
				sessionMng.m_lCurrencyID, clientCapInfo
				.getPayeeAccoutNO(),
				sessionMng.m_lOfficeID);
		clientCapInfo.setPayeeName(info1.getPayeeName());
			}
		}

		//�������б���������
		if (clientCapInfo != null) {
			request.setAttribute("clientInfo", clientCapInfo);
		}
		//��ȡ�����Ļ���
		//ServletContext sc = getServletContext();
		//���÷��ص�ַ
		RequestDispatcher rd = null;
		if (strISCPF.equalsIgnoreCase("true")) {
			if (state == 2) {/*****�ҵ�2�δ�ֵ������ֱ�Ӳ��Һͱ��浽����ҳ��**********/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("saved")) {
					request.setAttribute("BCCG", "success");
				}
							/***************/
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/C823.jsp");
				//�ַ�
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
			else {
				Log.print("��V824.jsp��");
				request.setAttribute("success", "success");
				
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/C823.jsp");
				//�ַ�
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
		} else {
			if (state == 2) {/*****�ҵ�2�δ�ֵ������ֱ�Ӳ��Һͱ��浽����ҳ��**********/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("saved")) {
					request.setAttribute("BCCG", "success");
				}
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
				//�ַ�
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			} 
			else {
				Log.print("��V834.jsp��");
				request.setAttribute("success", "success");
	
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
				//�ַ�
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
		}
		
		 /*
		 //add by hyzeng 2003-4-11 ��������Ѵ��ڣ�Ӧ����ʾ���տ�����Ѵ��ڡ�
		 if  ( clientCapInfo.getID() == -2 )
		 {
		 Log.print("��V820.jsp��");
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V820.jsp?sDuplicate=1&txtIsCPF="+strISCPF);
			//�ַ�
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		 }
		 */
		 
		//forward�����ҳ��
		rd.forward(request, response);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
