<%--
 ҳ������ ��fi_c001.jsp
 ҳ�湦�� : �������� - ��ʸ��� ����\ȡ������
 ��    �� ��leiyang
 ��    �� ��2008-12-01
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.info.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strTitle = "��ʸ���";
	String strTemp = "";
	
	FinanceInfo financeInfo = null;
	long lInstructionID = -1;
	String sbatchNO = "";
	long lRemitType = -1;
	Timestamp tsExecute = null;// ִ����
	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	boolean isCommitTimes = false;
	String signatureValue = "";
	String signatureOriginalValue = "";
	TransInfo transinfo = new TransInfo();
	String strContext = request.getContextPath();
	String strMessage1 = "";//ҵ�񸴺���ʾ��Ϣ
	String strMessage2 = "";
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
	
	strTemp = request.getParameter("sbatchNO");
	if(strTemp != null && !strTemp.equals("")){
		sbatchNO = strTemp;
	}
	
	strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE);
	if(strTemp != null && !strTemp.equals("")){
		signatureValue = strTemp;
	}
	
	/* ��ʼ��EJB */
	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();

	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.CHECK))){
		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		if(financeInfo == null){
			sessionMng.getActionMessages().addMessage("û�в��ҵ����������ݣ�");
			pageInfo.fail();
		}
		else {
			tsExecute = financeInfo.getExecuteDate();
			
			//���������ύʱ��Ч��
			OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
			isCommitTimes = commitTime.validateOBCommitTime(tsExecute,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			if(isCommitTimes == false){
				//tsExecute.setDate(tsExecute.getDate() + 1);
				sessionMng.getActionMessages().addMessage("����ʱ���ѳ���������ٽ���ʱ�䣡");
				pageInfo.fail();
			}
			else {
				if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
					String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
					String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromReq(request);
		
					SignatureInfo signatureInfo = new SignatureInfo();
					signatureInfo.setNameArray(nameArray);
					signatureInfo.setValueArray(valueArray);
					signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
				
					try {	
						System.out.println("signatureValue��" + signatureValue);
						System.out.println("signatureOriginalValue��" + signatureOriginalValue.toString());		
						signatureInfo.setOriginalData(signatureOriginalValue);
						signatureInfo.setSignatureValue(signatureValue);
						SignatureAuthentication.validateFromReq(signatureInfo);	
						//��ǩ��ֵ��ԭʼ���ݱ���
						financeInfo.setSignatureValue(signatureValue);
						financeInfo.setSignatureOriginalValue(signatureOriginalValue);
					} catch (Exception e) {
							System.out.println(e.getMessage());			
							throw new IException(e.getMessage());
					}
				}
				
				/* ������Ϣ���� */  //ҵ�񸴺�
				if( obFinanceInstrDao.getSignUserID(lInstructionID) > 0 )
				{
					strMessage1 =" ���ҵ����Ϊ�Ѹ���״̬����Ҫǩ�Ϻ�����ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";
					
				}else{
					strMessage1 = " ���ҵ����Ϊ�Ѹ���״̬���Ѿ��ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";
								
				}
				strMessage2 = "<b>ָ�����Ϊ�� "+lInstructionID+" </b><br>";
				
				request.setAttribute("strMessage1",strMessage1);
				request.setAttribute("strMessage2",strMessage2);
				financeInfo.setCheckUserID(sessionMng.m_lUserID);
				try
				{
					obFinanceInstr.check(financeInfo);
					sessionMng.getActionMessages().addMessage("�����ɹ�");
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo.setActionType(Constant.TransLogActionType.check);
				} catch (Exception e) {
					transinfo.setStatus(Constant.FAIL);
					transinfo.setActionType(Constant.TransLogActionType.check);
					e.printStackTrace();
					throw new IException(e.getMessage());
				}
				
				pageInfo.success();
			}
		}
	}
	else if(pageInfo.getStrAction().equals("UNCHECK")){
		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		if(financeInfo == null){
			sessionMng.getActionMessages().addMessage("û�в��ҵ����������ݣ�");
			pageInfo.fail();
		}
		else {
			if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
				String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
				String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromReq(request);
	
				SignatureInfo signatureInfo = new SignatureInfo();
				signatureInfo.setNameArray(nameArray);
				signatureInfo.setValueArray(valueArray);
				signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
			
				try {	
					System.out.println("signatureValue��" + signatureValue);
					System.out.println("signatureOriginalValue��" + signatureOriginalValue.toString());		
					signatureInfo.setOriginalData(signatureOriginalValue);
					signatureInfo.setSignatureValue(signatureValue);
					SignatureAuthentication.validateFromReq(signatureInfo);	
					//��ǩ��ֵ��ԭʼ���ݱ���
					financeInfo.setSignatureValue(signatureValue);
					financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				} catch (Exception e) {
						System.out.println(e.getMessage());			
						throw new IException(e.getMessage());
				}
			}
			
			financeInfo.setCheckUserID(sessionMng.m_lUserID);
		
			
			try
			{
				obFinanceInstr.cancelCheck(financeInfo);
				sessionMng.getActionMessages().addMessage("�����ɹ�");
				transinfo.setStatus(Constant.SUCCESSFUL);
				transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
			}catch (Exception e) {
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			pageInfo.success();
		}
	}
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	 RequestDispatcher rd = null;
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
	
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