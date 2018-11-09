<%--
 ҳ������ ��fi_c001.jsp
 ҳ�湦�� : �������� - ��ʸ��� ����\���沢�ύ����
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
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.facade.factory.*"%>
<%@ page import="com.iss.itreasury.safety.facade.imp.*"%>
<%@ page import="com.iss.itreasury.safety.facade.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.bizlogic.*"%>
<%@page import="com.iss.itreasury.fcinterface.dataentity.ExtSystemLogInfo"%>
<%@page import="com.iss.itreasury.fcinterface.util.EPConstant"%>
<%@page import="com.iss.itreasury.fcinterface.dao.FcinterfaceDao"%>
<%@ page import="com.iss.itreasury.bs.util.BranchIdentify" %>
<%@page import="com.iss.itreasury.bs.log.SystemOutImpl"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "��ʸ���";
	String strTemp = "";
	String sTemp = "";
	long sourceType = -1;  //SAP��Դ
	long AbstractID = -1;//�����;ID
	String AbstractCode = "";//�����;���	
	String strNote = "";// �����;
	long remitArea = -1;	//�������
	long remitSpeed = -1;	//����ٶ�
	long strAction = -1;
	long lInstructionID = -1;
	String sbatchNO = "";
	long lRemitType = -1;
	Timestamp tsExecute = null;// ִ����
	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	boolean isCommitTimes = false;
	String signatureValue = "";
	String signatureOriginalValue = "";
	Timestamp dtmodify=null;
	TransInfo transinfo = new TransInfo();
	OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
    OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();	
	Timestamp now = new Timestamp(System.currentTimeMillis());//��ȡϵͳ��ǰʱ��    
	boolean isNeedApproval = false;
	String nextPageURL ="";
	String strFailPageURL = "";
	String submitAgain = "";
	String strPayeeBankName = "";
	String bankName = ""; //������ȫ��
	long timestamp = -1;
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
		financeInfo.setID(lInstructionID);
	}
	
	strTemp = request.getParameter("sbatchNO");
	if(strTemp != null && !strTemp.equals("")){
		sbatchNO = strTemp;
		financeInfo.setSBatchNo(sbatchNO);
	}
	
	strTemp = request.getParameter("payerAcctID");
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
		lRemitType = Long.parseLong(strTemp);
		financeInfo.setRemitType(lRemitType);
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
		strPayeeBankName = strTemp.trim();
		financeInfo.setPayeeBankName(strPayeeBankName);

	}
	if(lRemitType==OBConstant.SettRemitType.BANKPAY)//���л��
	{
		bankName = BranchIdentify.getStandardBankName(strPayeeBankName);
		if(bankName==null||bankName.trim().length()==0)
		{
			bankName = "��������";
		}
		financeInfo.setBankName(bankName);	
	}
	strTemp = request.getParameter("txtPayeeBankCNAPSNO");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setSPayeeBankCNAPSNO(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeBankOrgNO");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setSPayeeBankOrgNO(strTemp);
	}
	
	strTemp = request.getParameter("sPayeeBankExchangeNO");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setSPayeeBankExchangeNO(strTemp);
	}
	
	strTemp = request.getParameter("sApplyCodeSource");
	if(strTemp != null && !strTemp.equals("")){
		sourceType = Long.parseLong(strTemp);
		financeInfo.setSource(sourceType);
	}
	//SAP��ԴΪ����ʱ����������SAPָ�����к�
	if(sourceType!=SETTConstant.ExtSystemSource.EBANK)
	{
		strTemp = request.getParameter("sApplyCode");
		if(strTemp != null && !strTemp.equals("")){
			financeInfo.setApplyCode(strTemp);
		}
	}

	strTemp = request.getParameter("amount");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
	}
	
	strTemp = request.getParameter("tsExecute");
	if(strTemp != null && !strTemp.equals("")){
		tsExecute = DataFormat.getDateTime(strTemp);
		financeInfo.setExecuteDate(tsExecute);
	}
	
	strTemp = request.getParameter("sNoteCtrl");
	if(strTemp != null && !strTemp.equals("")){
		strNote = strTemp;
		financeInfo.setNote(strTemp);
	}
	strTemp = request.getParameter("dtmodify");
	if(strTemp != null && !strTemp.equals("")){
		dtmodify =DataFormat.getDateTime(strTemp);
		financeInfo.setDtModify(dtmodify);
	}
	strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE.getName());
	if(strTemp != null && !strTemp.equals("")){
		signatureValue = strTemp;
	}
	
	strTemp = request.getParameter(SignatureConstant.ORIGINALVALUE.getName());
	if(strTemp != null && !strTemp.equals("")){
		signatureOriginalValue = strTemp;
	}	
	
	//Boxu Add 2010-12-01 ����"����/���"��"�Ƿ�Ӽ�"
	strTemp = request.getParameter("remitArea");
	if(strTemp != null && !strTemp.equals("")){
		remitArea = Long.parseLong(strTemp);
		financeInfo.setRemitArea(remitArea);
	}
	strTemp = request.getParameter("remitSpeed");
	if(strTemp != null && !strTemp.equals("")){
		remitSpeed = Long.parseLong(strTemp);
		financeInfo.setRemitSpeed(remitSpeed);
	}
	
	strTemp = request.getParameter("strAction");
	if(strTemp != null && !strTemp.equals("")){
		strAction = Long.parseLong(strTemp);
		
	}
	strTemp = request.getParameter("strFailPageURL");
	if(strTemp != null && !strTemp.equals("")){
		strFailPageURL = strTemp;
		
	}
	
	strTemp = request.getParameter("submitAgain");
	if(strTemp != null && !strTemp.equals("")){
		submitAgain = strTemp;
		
	}
	
	strTemp = request.getParameter("timestamp");
	if(strTemp != null && !strTemp.equals("")){
		timestamp = Long.valueOf(strTemp).longValue();
		
	}	
	
	financeInfo.setOfficeID(sessionMng.m_lOfficeID);

	/* ȷ�����Ͻ������ͺͻ�ʽ */
	switch ((int) lRemitType) {
		case (int) OBConstant.SettRemitType.BANKPAY:
			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
			break;
		case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
			break;
	}
	
	
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	boolean isSameApplyCode = false;
	
	//��Դ��������ʱ���ж�SAP�Ƿ��ظ�
	if(sourceType!=SETTConstant.ExtSystemSource.EBANK)
	{
		isSameApplyCode=obFinanceInstrBiz.isSameApplyCode(financeInfo.getID(),financeInfo.getApplyCode(),sourceType);
		if(isSameApplyCode==true)
		{
			request.setAttribute("financeInfo",financeInfo);
			throw new Exception("SAPָ�����к��ظ������������룡");
		}
	}
	

	//�ж��Ƿ����������
	InutParameterInfo inutParameterInfo = new InutParameterInfo();
	inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
	inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
	inutParameterInfo.setClientID(sessionMng.m_lClientID);
	inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	inutParameterInfo.setTransTypeID(financeInfo.getTransType());
	//�����к�ȡ�����������ֱ���λ������������λ����
	if (OBConstant.SettInstrStatus.DOAPPRVOAL != strAction && OBConstant.SettInstrStatus.CANCELAPPRVOAL != strAction) {
		inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
	}
	isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
	
	
	
	
	/* ��ʼ��EJB */
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();
	

	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVE))
	|| pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL))){
		
		//���������ύʱ��Ч��
		OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
		isCommitTimes = commitTime.validateOBCommitTime(tsExecute,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		if(isCommitTimes == false){
			tsExecute.setDate(tsExecute.getDate() + 1);
		}
		
		//��֤ǩ����ʼ
		SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
		SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
		boolean validate = facade.validateSignFromClient(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY,request);
		if(!validate)
		{
			throw new Exception("��֤�ͻ���ǩ����Ϣʧ��!");
		}
		
		
		//��֤ǩ������
		financeInfo.setTimestamp(timestamp);
		financeInfo.setSignatureOriginalValue(signatureOriginalValue);
		financeInfo.setSignatureValue(signatureValue);
		financeInfo.setOfficeID(sessionMng.m_lOfficeID);
		financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		financeInfo.setConfirmUserID(sessionMng.m_lUserID);
		financeInfo.setClientID(sessionMng.m_lClientID);
		financeInfo.setConfirmDate(tsExecute);
		financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		
		if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL))){
			StringBuffer strURL1 = new StringBuffer();
			strURL1.append(strContext + "/capital/financeinstr/control/fi_c001.jsp");
			strURL1.append("?strSuccessPageURL=/capital/financeinstr/view/fi_v004.jsp");
			//strURL1.append("&strFailPageURL=/capital/financeinstr/view/fi_v001.jsp");
			strURL1.append("&strAction=doApproval");
			strURL1.append("&lInstructionID=");
			//���������
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(strURL1.toString());
			pInfo.setTransTypeID(OBFSWorkflowManager.getReflectOperation(OBConstant.QueryInstrType.CAPTRANSFER, financeInfo.getRemitType()));
			financeInfo.setInutParameterInfo(pInfo);
		}
		long transID = financeInfo.getID();
		try
		{
			//����
			if(submitAgain.equals("submitAgain"))
			{
				financeInfo.setID(-1);
			}
			lInstructionID = obFinanceInstr.addCapitalTrans(financeInfo);
			
			transinfo.setStatus(Constant.SUCCESSFUL);
			if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)))
			{
				transinfo.setActionType(Constant.TransLogActionType.initApproval);
			}
			else if(transID>0)
			{
				transinfo.setActionType(Constant.TransLogActionType.modify);
			}
			else
			{
				transinfo.setActionType(Constant.TransLogActionType.insert);
			}
		}catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)))
				{
					transinfo.setActionType(Constant.TransLogActionType.initApproval);
				}
				else if(transID>0)
				{
					transinfo.setActionType(Constant.TransLogActionType.modify);
				}
				else
				{
					transinfo.setActionType(Constant.TransLogActionType.insert);
				}
					ex.printStackTrace();
					throw new IException(ex.getMessage());
			}
		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		
		request.setAttribute("financeInfo",financeInfo);
		if(isCommitTimes == true){
			sessionMng.getActionMessages().addMessage("�����ɹ�");
		}
		else{
			sessionMng.getActionMessages().addMessage("�����ɹ����ύʱ���ѳ���������ٽ���ʱ��, ִ����˳��һ��!");
		}
		pageInfo.success();
	}
	
				/*��������;ժҪ��Ϣ*/
				
				String sNote = "";
				sTemp = (String)request.getAttribute("sNote");
				if(sTemp!=null&&sTemp.trim().length()>0)
				{
					sNote = sTemp;
					AbstractID = Long.parseLong(sNote);//�����;ID
				}
				
				
				sTemp = (String)request.getAttribute("sCode");//�����;���
				if(sTemp!=null&&sTemp.trim().length()>0)
				{
					AbstractCode = sTemp;
					
				}
				
				if( AbstractID < 0)
				{
					long lMaxCode = OBAbstractSetting.getMaxCode(sessionMng.m_lOfficeID,sessionMng.m_lUserID);
					OBinfo.setScode(DataFormat.formatInt(lMaxCode,5,true,0));
				}else if(AbstractID > 0){
					OBinfo.setScode(AbstractCode);
				}
				
				OBinfo.setId(AbstractID);
				OBinfo.setSdesc(strNote);
				OBinfo.setNofficeid(sessionMng.m_lOfficeID);
				OBinfo.setNclientid(sessionMng.m_lUserID);
				OBinfo.setInputtime(now);
				OBinfo.setModifytime(now);
				OBAbstractSetting.saveStandardAbstract(OBinfo);	
				
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				nextPageURL = pageInfo.getStrNextPageURL()+"?isNeedApproval="+isNeedApproval;

}
catch (Exception ie){	
	ie.printStackTrace();
    //OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    sessionMng.getActionMessages().addMessage("����ʧ�ܣ�"+ie.getMessage());
    nextPageURL = strFailPageURL;
    request.setAttribute("financeInfo",financeInfo);
	return;
}

finally
	{
		
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(nextPageURL);
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	// minzhao 20090520 start add translogger

		if(transinfo.getStatus()!=-1)
		{
			TranslogBiz translofbiz= new TranslogBiz();
			transinfo.setHostip(request.getRemoteAddr());
			transinfo.setHostname(request.getRemoteHost());
			long iTransType = -1;
			switch ((int) lRemitType) {
			case (int) OBConstant.SettRemitType.BANKPAY:
				iTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
				break;
			case (int) OBConstant.SettRemitType.INTERNALVIREMENT:
				iTransType = OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT;
				break;
			}
			transinfo.setTransType(iTransType);
			translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			//����ⲿָ�������־	ADD xiangzhou 2011-4-2
			if(financeInfo.getSource()!=SETTConstant.ExtSystemSource.EBANK){
				FcinterfaceDao dao = new FcinterfaceDao();
				ExtSystemLogInfo info = new ExtSystemLogInfo();
				info.setApplycode(financeInfo.getApplyCode());
				info.setSource(financeInfo.getSource());
				info.setNstatus(EPConstant.EPInstructionStatus.SENDSUCCESS);
				dao.addLog(info);
			}
		
		}
		
	//minzhao 20090520 end translogger 
	}
%>