<%--
 ҳ������ ��fi_c001.jsp
 ҳ�湦�� : �������� - ��ʸ��� 
 ��    �� ��leiyang
 ��    �� ��2008-12-01
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "��ʸ���";
	String strTemp = "";
	
	long lInstructionID = -1;
	String strTempAction = "";

try {
	//��¼���
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	out.flush();
       	return;
   	}
   	//���Ȩ��
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
       	out.flush();
       	return;
   	}
 
 	//��request�еĲ���ת����Bean��
	pageInfo.convertRequestToDataEntity(request);
	
	String lreport=null;
	String report="";
	lreport = request.getParameter("report");
	if(lreport!=null&&lreport.trim().length()>0)
	{
	      report=lreport;
	}
 	
	strTemp = request.getParameter("lInstructionID");
	if(strTemp != null && !strTemp.equals("")){
		lInstructionID = Long.parseLong(strTemp);
		financeInfo.setID(lInstructionID);
	}
	
	strTemp = request.getParameter("sbatchNO");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setSBatchNo(strTemp);
	}
	
	strTemp = request.getParameter("nPayerAccountID");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayerAcctID(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("sPayerAccountNoZoomCtrl");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayerAcctNo(strTemp);
	}
	
	strTemp = request.getParameter("dPayerCurrBalance");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setCurrentBalance(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
	}
	
	strTemp = request.getParameter("dPayerUsableBalance");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setUsableBalance(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
	}
	
	strTemp = request.getParameter("nRemitType");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setRemitType(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("nPayeeAccountID");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeAcctID(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("sPayeeBankNoZoomCtrl");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeAcctNo(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeAcctNoZoomCtrl");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeAcctNo(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeName");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeName(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeProv");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeProv(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeCity");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeCity(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeBankName");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeBankName(strTemp);
	}
	
	strTemp = request.getParameter("dAmount");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
	}
	
	strTemp = request.getParameter("tsExecute");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setExecuteDate(DataFormat.getDateTime(strTemp));
	}
	
	strTemp = request.getParameter("sNote");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setNote(strTemp);
	}
	
	strTemp = request.getParameter("strTempAction");
	if(strTemp != null && !strTemp.equals("")){
		strTempAction = strTemp;
	}

	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	if(lInstructionID != -1){
		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
	}		
	request.setAttribute("financeInfo",financeInfo);
	if(report.equals("report"))
		{
		request.setAttribute("modify","modify");
		}
	if(pageInfo.getStrAction().equals("doApproval")){
		if(!strTempAction.equals("")){
			request.setAttribute("strAction",strTempAction);
		}
		else {
			request.setAttribute("strAction",pageInfo.getStrAction());
		}
	}
	pageInfo.success();
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request,response);
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
