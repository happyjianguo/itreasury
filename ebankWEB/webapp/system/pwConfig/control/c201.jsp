<%--
 ҳ������ ��c201.jsp
 ҳ�湦�� : �����������
 ��    �� ��liangpan
 ��    �� ��2007-6-25
 ����˵�� ��
 �޸���ʷ ��
 �޸�˵�� :
--%>
<%@ page contentType="text/html;charset=gbk" %>
<jsp:directive.page import="java.sql.Timestamp"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*,
				 com.iss.itreasury.ebank.pwconfig.bizlogic.*,
				 com.iss.itreasury.ebank.pwconfig.dao.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 java.util.*,
				 javax.servlet.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<%	
	Log.print("�û���������----iTreasury-ebank/pwconfig/control/c201.jsp");

	//ҳ�������
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strTitle = "";
	try{
	//��¼���
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//�ж��Ƿ���Ȩ��
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}


	//��request�л�ȡҳ����Ʋ���
	pageInfo.convertRequestToDataEntity(request);

	/**
	* ����ҵ��dataentity
	*/
	PasswordInfo passwordInfo = new PasswordInfo();
	
	PWConfigBean biz = new PWConfigBean();

	/**
	* ��request��ȡ��ҳ����صı���
	*/
	passwordInfo = biz.getPasswordConfigInfo(sessionMng.m_lOfficeID);
	String strTemp = null;
	strTemp = String.valueOf(request.getAttribute("isCancelConfig"));
	if(strTemp.equals("1")){
		passwordInfo.setStatus(0);
		passwordInfo.setModifyDate(Env.getSystemDateTime());
		biz.updatePasswordInfo(passwordInfo);
	}else{	
		strTemp = null;	
		passwordInfo.setMinPassword(Long.parseLong(String.valueOf(request.getAttribute("passwordLength"))));
		long id = passwordInfo.getId();
		
		strTemp = String.valueOf(request.getAttribute("termDate"));
		if(strTemp.equals("")){
			passwordInfo.setTermDate(-1);
		}else{
			passwordInfo.setTermDate(Long.parseLong(strTemp));
		}
		strTemp = String.valueOf(request.getAttribute("remindDate"));
		if(strTemp.equals("")){
			passwordInfo.setRemindDate(-1);
		}else{
			passwordInfo.setRemindDate(Long.parseLong(strTemp));
		}
		passwordInfo.setCompriseCapital(Long.parseLong(String.valueOf(request.getAttribute("compriseCapital"))));
		passwordInfo.setCompriseLowercase(Long.parseLong(String.valueOf(request.getAttribute("compriseLowercase"))));
		passwordInfo.setCompriseNumber(Long.parseLong(String.valueOf(request.getAttribute("compriseNumber"))));
		passwordInfo.setCompriseSpecial(Long.parseLong(String.valueOf(request.getAttribute("compriseSpecial"))));
		passwordInfo.setCompriseTerm(Long.parseLong(String.valueOf(request.getAttribute("compriseTerm"))));
		passwordInfo.setForcePerfect(Long.parseLong(String.valueOf(request.getAttribute("compriseForce"))));
		passwordInfo.setInputuserId(sessionMng.m_lUserID);
		passwordInfo.setOfficeId(sessionMng.m_lOfficeID);
		passwordInfo.setClientId(sessionMng.m_lClientID);
		passwordInfo.setType(1);
		if(id == -1){
			passwordInfo.setInputDate(Env.getSystemDate());
			passwordInfo.setStatus(1);
			biz.addPasswordInfo(passwordInfo);
		}else{			
			passwordInfo.setModifyDate(Env.getSystemDate());
			biz.updatePasswordInfo(passwordInfo);
		}
	}		
	//�ɹ��������
	pageInfo.setStrActionResult(Constant.ActionResult.SUCCESS);
	sessionMng.getActionMessages().addMessage("���óɹ�");
	}catch(IException e){
		e.printStackTrace();
		/**
		* �����쳣,��ӱ�����Ϣ
		*/
		sessionMng.getActionMessages().addMessage(e.getMessage()); 
		pageInfo.setStrActionResult(Constant.ActionResult.FAIL);
	}
	/**
	 * �������������request��
	 */
	request.setAttribute("strActionResult", pageInfo.getStrActionResult());

	/**
	 * ����ӡ��Ϣ����request��
	 */
	if (!pageInfo.getStrPrintMsg().equals(""))request.setAttribute("strPrintMsg",pageInfo.getStrPrintMsg());
	
	/**
	 * ���������request��
	 */
	

	/** 
	 * ת����һҳ��
	 */
	 
	String nextURL = "../view/v201.jsp";
	pageInfo.setStrNextPageURL(nextURL);
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>