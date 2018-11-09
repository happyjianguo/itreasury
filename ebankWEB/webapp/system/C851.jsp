<%--
/**
 * �������ƣ�C851.jsp
 * ����˵����ϵͳ����-�û������
 * �������ߣ�����
 * ������ڣ�2003��9��2��
 */
--%>

<!--Header start-->
<%@ page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="getData"
	class="com.iss.itreasury.system.privilege.util.GetData" scope="page" />
<%@ page
	import="java.util.*,com.iss.itreasury.util.*,com.iss.itreasury.system.bizlogic.EBankbean,com.iss.itreasury.ebank.privilege.dataentity.*,com.iss.itreasury.ebank.privilege.bizlogic.*,com.iss.itreasury.ebank.privilege.dao.*,com.iss.itreasury.ebank.privilege.util.*,com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%
	//response.setHeader("Cache-Control", "no-stored");
	//response.setHeader("Pragma", "no-cache");
	//response.setDateHeader("Expires", 0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!/* ����̶����� */
	String strTitle = "";%>

<%
	/* �û���¼�����Ȩ��У�� */
	try {
		/* �û���¼��� */
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

		String strTemp = null;
		String strMethod = "";
		//
		strTemp = request.getParameter("method");
		Log.print("Method is : " + strTemp);
		if (strTemp != null && strTemp.length() > 0) {
			strMethod = strTemp;
		}

		System.out
		.println("###################enter c851.jsp###############");
		//������
		HttpServletRequest req = getData.setValue(request);
		System.out
		.print("******************************strMethod**************"
				+ strMethod + "****");
		if (strMethod.equals("toAdd")) {
			Vector v = new Vector();
			EBankbean userAdmin = new EBankbean();
			Collection c = userAdmin.getAllPrivilege();
			for (int i = 1; i <= 9; i++) {
		v.add(userAdmin.getPrivilegesByTopMenu(c, i));
			}
			/* �������б��������� */
			request.setAttribute("PrivilegesOfTopMenu", v);
			/* ��ȡ�����Ļ��� */
			//ServletContext sc = getServletContext();
			/* ���÷��ص�ַ */
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V852.jsp");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			/* forward�����ҳ�� */
			rd.forward(request, response);
		} else if (strMethod.equals("Add")) {
			OB_GroupInfo groupinfo = new OB_GroupInfo();
			OB_Privilege_Of_GroupInfo[] pgCondition = null;
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO();
			String strGroupName = "";
			String strLevel = "";

			strTemp = (String) req.getAttribute("GroupName");
			Log.print("Group Name " + strTemp);
			if (strTemp != null && strTemp.length() > 0) {
		strGroupName = strTemp;
			}
			strTemp = (String) req.getAttribute("Level");
			Log.print("Group Name " + strTemp);
			if (strTemp != null && strTemp.length() > 0) {
		strLevel = strTemp;
			}
			strTemp = (String) req.getAttribute("vsize");
			long vsize=-1;
			if (strTemp != null && strTemp.length() > 0) {
			vsize=Long.valueOf(strTemp).longValue();
			}
			//
			ArrayList checkbox = new ArrayList();
			ArrayList strPvgNoTmp = new ArrayList();
			for(int i=1;i<=vsize;i++)
			{
				String[] strPvgNo = request
				.getParameterValues("checkbox"+i);
				checkbox.add(strPvgNo);
			}

/*
			String[] strPvgNo1 = request
			.getParameterValues("checkbox1");
			String[] strPvgNo2 = request
			.getParameterValues("checkbox2");
			String[] strPvgNo3 = request
			.getParameterValues("checkbox3");
			String[] strPvgNo4 = request
			.getParameterValues("checkbox4");
			String[] strPvgNo5 = request.getParameterValues("checkbox");

			checkbox.add(strPvgNo1);
			checkbox.add(strPvgNo2);
			checkbox.add(strPvgNo3);
			checkbox.add(strPvgNo4);
			checkbox.add(strPvgNo5);
*/			
			Iterator checkboxList = checkbox.iterator();
			String strPNTmp[];
			while (checkboxList != null && checkboxList.hasNext()) {
		Object tmpcheckbox = checkboxList.next();
		if (tmpcheckbox == null)
			continue;
		strPNTmp = (String[]) tmpcheckbox;
			for (int i = 0; i < strPNTmp.length; i++) {
				strPvgNoTmp.add(strPNTmp[i]);
				}
			}

			String[] strPvgNo = new String[strPvgNoTmp.size()];
			Iterator str = strPvgNoTmp.iterator();
			int num = 0;
			while (str.hasNext()) {
		strPvgNo[num] = (String) str.next();
		num++;
			}
			//
			if (strPvgNo != null) {
		String[] arrTmp = null;
		Vector v = new Vector();
		String tmp = null;
		String tmp1 = null;
		String tmp2 = null;
		for (int i = 0; i < strPvgNo.length; i++) {
			tmp = strPvgNo[i];
			if (tmp.length() >= 4)
				tmp1 = tmp.substring(0, (tmp.length() - 4));
			if (tmp.length() >= 8)
				tmp2 = tmp.substring(0, (tmp.length() - 8));
			if (tmp1 != null && !v.contains(tmp1)) {
				v.add(tmp1);
			}
			if (tmp2 != null && !v.contains(tmp2)) {
				v.add(tmp2);
			}
		}
		if (v.size() > 0) {

			arrTmp = new String[strPvgNo.length + v.size()];
			for (int j = 0; j < strPvgNo.length; j++) {
				arrTmp[j] = strPvgNo[j];
			}
			for (int k = 0; k < v.size(); k++) {
				arrTmp[strPvgNo.length + k] = (String) v.get(k);
			}

		}

		strPvgNo = arrTmp;
		Log.print("Check box size : " + strPvgNo.length);
			}
			EBankbean userAdmin = new EBankbean();
			groupinfo.setClientId(sessionMng.m_lClientID);
			groupinfo.setName(strGroupName);
			System.out
			.print("******************************3**************");
			if (strPvgNo != null) {
		int strlen = strPvgNo.length;
		pgCondition = new OB_Privilege_Of_GroupInfo[strlen];
		for (int i = 0; i < strPvgNo.length; i++) {
			OB_Privilege_Of_GroupInfo ob_Privilege_Of_GroupInfo = new OB_Privilege_Of_GroupInfo();
			ob_Privilege_Of_GroupInfo
			.setPrivilegeID(ob_privilegeDao
					.findPrivilegeIdByNo(strPvgNo[i]));
			pgCondition[i] = ob_Privilege_Of_GroupInfo;
		}
			}

			long lGroupID = userAdmin.addGroup(groupinfo, pgCondition);

			// ��ȡ�����Ļ��� 
			//ServletContext sc = getServletContext();
			// ���÷��ص�ַ 
			request.setAttribute("aadd","aadd");
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C851.jsp?method=view");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			// forward�����ҳ�� 
			rd.forward(request, response);
		} else if (strMethod.equals("Modify")) {
			long lGroupID = -1;
			String strGroupName = "";
			String strLevel = "";

			OB_GroupInfo groupinfo = new OB_GroupInfo();
			OB_Privilege_Of_GroupInfo[] pgCondition = null;
			OB_PrivilegeDAO ob_privilegeDao = new OB_PrivilegeDAO();
			//
			strTemp = (String) req.getAttribute("GroupID");
			if (strTemp != null && strTemp.length() > 0) {
		lGroupID = Long.parseLong(strTemp);
			}
			//
			strTemp = (String) req.getAttribute("GroupName");
			if (strTemp != null && strTemp.length() > 0) {
		strGroupName = strTemp;
			}
			//
			strTemp = (String) req.getAttribute("Level");
			if (strTemp != null && strTemp.length() > 0) {
		strLevel = strTemp;
			}
			strTemp = (String) req.getAttribute("vsize");
			long vsize=-1;
			if (strTemp != null && strTemp.length() > 0) {
			vsize=Long.valueOf(strTemp).longValue();
			}

			ArrayList checkbox = new ArrayList();
			ArrayList strPvgNoTmp = new ArrayList();
			for(int i=1;i<=vsize;i++)
			{
			String[] strPvgNo = request
			.getParameterValues("checkbox"+i);
			checkbox.add(strPvgNo);
			}
			/*
			String[] strPvgNo1 = request
			.getParameterValues("checkbox1");
			String[] strPvgNo2 = request
			.getParameterValues("checkbox2");
			String[] strPvgNo3 = request
			.getParameterValues("checkbox3");
			String[] strPvgNo4 = request
			.getParameterValues("checkbox4");
			String[] strPvgNo5 = request.getParameterValues("checkbox");

			checkbox.add(strPvgNo1);
			checkbox.add(strPvgNo2);
			checkbox.add(strPvgNo3);
			checkbox.add(strPvgNo4);
			checkbox.add(strPvgNo5);
			*/
			Iterator checkboxList = checkbox.iterator();
			String strPNTmp[];
			while (checkboxList != null && checkboxList.hasNext()) {
		Object tmpcheckbox = checkboxList.next();
		if (tmpcheckbox == null)
			continue;
		strPNTmp = (String[]) tmpcheckbox;
		for (int i = 0; i < strPNTmp.length; i++) {
			strPvgNoTmp.add(strPNTmp[i]);
		}
			}

			String[] strPvgNo = new String[strPvgNoTmp.size()];
			Iterator str = strPvgNoTmp.iterator();
			int num = 0;
			while (str.hasNext()) {
		strPvgNo[num] = (String) str.next();
		num++;
			}
			//
			if (strPvgNo != null) {
		String[] arrTmp = null;
		Vector v = new Vector();
		String tmp = null;
		String tmp1 = null;
		String tmp2 = null;
		for (int i = 0; i < strPvgNo.length; i++) {
			tmp = strPvgNo[i];
			if (tmp.length() >= 4)
				tmp1 = tmp.substring(0, (tmp.length() - 4));
			if (tmp.length() >= 8)
				tmp2 = tmp.substring(0, (tmp.length() - 8));
			if (tmp1 != null && !v.contains(tmp1)) {
				v.add(tmp1);
			}
			if (tmp2 != null && !v.contains(tmp2)) {
				v.add(tmp2);
			}
		}
		if (v.size() > 0) {

			arrTmp = new String[strPvgNo.length + v.size()];
			for (int j = 0; j < strPvgNo.length; j++) {
				arrTmp[j] = strPvgNo[j];
			}
			for (int k = 0; k < v.size(); k++) {
				arrTmp[strPvgNo.length + k] = (String) v.get(k);
			}

		}

		strPvgNo = arrTmp;
		Log.print("Check box size : " + strPvgNo.length);
			}
			Log.print("GroupID is " + lGroupID);
			EBankbean userAdmin = new EBankbean();
			groupinfo.setId(lGroupID);
			groupinfo.setClientId(sessionMng.m_lClientID);
			groupinfo.setName(strGroupName);
			int strlen = strPvgNo.length;
			pgCondition = new OB_Privilege_Of_GroupInfo[strlen];
			for (int i = 0; i < strPvgNo.length; i++) {
		OB_Privilege_Of_GroupInfo ob_Privilege_Of_GroupInfo = new OB_Privilege_Of_GroupInfo();
		ob_Privilege_Of_GroupInfo
				.setPrivilegeID(ob_privilegeDao
				.findPrivilegeIdByNo(strPvgNo[i]));
		pgCondition[i] = ob_Privilege_Of_GroupInfo;
			}

			lGroupID = userAdmin.updateGroup(groupinfo, pgCondition);

			// ��ȡ�����Ļ��� 
			//ServletContext sc = getServletContext();
			// ���÷��ص�ַ 
			
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C851.jsp?method=view");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			// forward�����ҳ�� 
			rd.forward(request, response);
		} else if (strMethod.equals("Delete")) {
			long lGroupID = -1;
			String strGroupName = "";
			String strLevel = "";
			String[] strPvgNo = null;
			//
			strTemp = (String) req.getAttribute("GroupID");
			if (strTemp != null && strTemp.length() > 0) {
		lGroupID = Long.parseLong(strTemp);
			}
			EBankbean userAdmin = new EBankbean();
			lGroupID = userAdmin.delGroup(lGroupID);

			if (lGroupID == -1) {
%>
<script language="JavaScript">
alert("���û��������û�ʹ�ã�����ɾ��");
</script>
<%
		/* ��ȡ�����Ļ��� */
		//ServletContext sc = getServletContext();
		/* ���÷��ص�ַ */
				
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C851.jsp?method=view");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		/* forward�����ҳ�� */
		rd.forward(request, response);

			} else {
		// ��ȡ�����Ļ��� 
		//ServletContext sc = getServletContext();
		// ���÷��ص�ַ 
		request.setAttribute("deletee","deletee");
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C851.jsp?method=view");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		// forward�����ҳ�� 
		rd.forward(request, response);
			}
		} else if (strMethod.equals("view")) {
			EBankbean userAdmin = new EBankbean();
			Collection c = userAdmin
			.findGroupsByClient(sessionMng.m_lClientID);
			String deletee = (String)request.getAttribute("deletee");
			String aadd = (String)request.getAttribute("aadd");
			if(deletee != null && !deletee.equals("")){
			request.setAttribute("deletee","ɾ���ɹ�");
			}
			if(aadd != null && !aadd.equals("")){
			request.setAttribute("aadd","����ɹ�");
			}
			/* �������б��������� */
			request.setAttribute("GroupsOfClient", c);
			// ��ȡ�����Ļ��� 
			//ServletContext sc = getServletContext();
			// ���÷��ص�ַ 
			
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V851.jsp");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			// forward�����ҳ�� 
			rd.forward(request, response);
		} else if (strMethod.equals("toModify")) {
			long lGroupID = -1;
			strTemp = (String) req.getAttribute("GroupID");
			Log.print("Group Name " + strTemp);
			if (strTemp != null && strTemp.length() > 0) {
		lGroupID = Long.parseLong(strTemp);
			}
			EBankbean userAdmin = new EBankbean();
			Vector cPrivilege = new Vector();
			cPrivilege = (Vector) userAdmin
			.findPrivilegesByGroupId(lGroupID);
			OB_GroupInfo gi = userAdmin.findGroupInfoByID(lGroupID);
			//
			Collection c = userAdmin.getAllPrivilege();
			Vector v = new Vector();

			for (int i = 1; i <= 9; i++) {
		v.add(userAdmin.getPrivilegesByTopMenu(c, i));
			}
			/* �������б��������� */
			request.setAttribute("PrivilegesOfTopMenu", v);

			/* �������б��������� */
			request.setAttribute("GroupInfo", gi);
			request.setAttribute("cPrivilege", cPrivilege);
			// ��ȡ�����Ļ��� 
			//ServletContext sc = getServletContext();
			// ���÷��ص�ַ 
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V852.jsp");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			// forward�����ҳ�� 
			rd.forward(request, response);
		}
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
<!--Forward end-->
