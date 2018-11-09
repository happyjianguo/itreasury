<%--
 ҳ������ ��fi_c001.jsp
 ҳ�湦�� : �������� - ��ʸ��� ɾ��
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
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@page import="java.sql.Timestamp"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "��ʸ���";
	String strTemp = "";
	TransInfo transinfo = new TransInfo();
	long lInstructionID = -1;
	Timestamp dtmodify = null;
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
 	
	strTemp = request.getParameter("lInstructionID");
	if(strTemp != null && !strTemp.equals("")){
		lInstructionID = Long.parseLong(strTemp);
	}
	strTemp = request.getParameter("dtmodify");
	if(strTemp != null && !strTemp.equals("dtmodify")){
	dtmodify = DataFormat.getDateTime(strTemp);
	}
	/* ��ʼ��EJB */
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();

	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.DELETE))){
		if(lInstructionID != -1){
			try
			{
				financeInfo.setID(lInstructionID);
		        financeInfo.setDeleteUserID(sessionMng.m_lUserID);
		        financeInfo.setDtModify(dtmodify);
				obFinanceInstr.deleteCapitalTrans(financeInfo);
				transinfo.setStatus(Constant.SUCCESSFUL);
				transinfo.setActionType(Constant.TransLogActionType.delete);
				financeInfo = obFinanceInstr.findByID(lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);

			}catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.delete);
				ex.printStackTrace();
				throw new IException(ex.getMessage());
			}
		}
		sessionMng.getActionMessages().addMessage("ɾ���ɹ�");
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
finally
{
// minzhao 20090520 start add translogger

	if(transinfo.getStatus()!=-1)
	{
		TranslogBiz translofbiz= new TranslogBiz();
		transinfo.setHostip(request.getRemoteAddr());
		transinfo.setHostname(request.getRemoteHost());
		transinfo.setTransType(financeInfo.getTransType());
		translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
	
	}
	

//minzhao 20090520 end translogger 
}
%>
