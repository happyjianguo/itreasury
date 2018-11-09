<%--
 ҳ������ ��query_c002.jsp
 ҳ�湦�� : ��Ϣ��ѯ �� ����ָ�����Ϣ��ѯ 
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
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	QueryCapForm queryCapForm = new QueryCapForm();
	FinanceInfo financeInfo = null;
	String strTitle = "����ָ�����Ϣ��ѯ ";
	String strTemp = "";
	
	long lTransType = -1;
	long lInstructionID = -1;

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
 	
	strTemp = request.getParameter("lTransType");
	if(strTemp != null && strTemp.length()>0){
		lTransType = Long.parseLong(strTemp);
		queryCapForm.setTransType(lTransType);
    }
	
	strTemp = request.getParameter("lDepositID");
	if(strTemp != null && strTemp.length()>0){
		queryCapForm.setDepositID(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("strDepositNo");
	if(strTemp != null && strTemp.length()>0){
		queryCapForm.setDepositNo(strTemp);
	}
	
    strTemp = request.getParameter("lStatus");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setStatus(Long.parseLong(strTemp));
    }
    
    strTemp = request.getParameter("sStartExe");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setStartExe(strTemp);
    }
    
    strTemp = request.getParameter("sEndExe");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setEndExe(strTemp);
    }
    
    strTemp = request.getParameter("dMinAmount");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setMinAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
    }
    
    strTemp = request.getParameter("dMaxAmount");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setMaxAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
    }
    
    strTemp = request.getParameter("sStartSubmit");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setStartSubmit(strTemp);
    }
    
    strTemp = request.getParameter("sEndSubmit");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setEndSubmit(strTemp);
    }
	
	strTemp = request.getParameter("lInstructionID");
	if(strTemp != null && !strTemp.equals("")){
		lInstructionID = Long.parseLong(strTemp);
	}
    		
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	
	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.QueryOperationType.QUERY))){
		if(lInstructionID != -1){
			financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		}
		request.setAttribute("financeInfo",financeInfo);
		request.setAttribute("queryCapForm",queryCapForm);
		//���ݶ��ڡ�֪ͨҵ�����ҳ��
		request.setAttribute("search","1");
	
		pageInfo.success();
	}
	
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
