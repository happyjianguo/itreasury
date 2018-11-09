<%--
 页面名称 ：fi_c001.jsp
 页面功能 : 网上银行 - 逐笔付款 保存\保存并提交审批
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
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
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "逐笔付款";
	String strTemp = "";
	String sTemp = "";
	long sourceType = -1;  //SAP来源
	long AbstractID = -1;//汇款用途ID
	String AbstractCode = "";//汇款用途编号	
	String strNote = "";// 汇款用途
	long remitArea = -1;	//汇款区域
	long remitSpeed = -1;	//汇款速度
	long strAction = -1;
	long lInstructionID = -1;
	String sbatchNO = "";
	long lRemitType = -1;
	Timestamp tsExecute = null;// 执行日
	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	boolean isCommitTimes = false;
	String signatureValue = "";
	String signatureOriginalValue = "";
	Timestamp dtmodify=null;
	TransInfo transinfo = new TransInfo();
	OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
    OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();	
	Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间    
	boolean isNeedApproval = false;
	String nextPageURL ="";
	String strFailPageURL = "";
	String submitAgain = "";
	String strPayeeBankName = "";
	String bankName = ""; //汇入行全称
	long timestamp = -1;
try {
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
       	out.flush();
       	return;
   	}
 
 	//将request中的参数转换到Bean中
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
	if(lRemitType==OBConstant.SettRemitType.BANKPAY)//银行汇款
	{
		bankName = BranchIdentify.getStandardBankName(strPayeeBankName);
		if(bankName==null||bankName.trim().length()==0)
		{
			bankName = "其他银行";
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
	//SAP来源为网银时，不需输入SAP指令序列号
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
	
	//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
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

	/* 确定网上交易类型和汇款方式 */
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
	
	//来源不是网银时，判断SAP是否重复
	if(sourceType!=SETTConstant.ExtSystemSource.EBANK)
	{
		isSameApplyCode=obFinanceInstrBiz.isSameApplyCode(financeInfo.getID(),financeInfo.getApplyCode(),sourceType);
		if(isSameApplyCode==true)
		{
			request.setAttribute("financeInfo",financeInfo);
			throw new Exception("SAP指令序列号重复！请重新输入！");
		}
	}
	

	//判断是否存在审批流
	InutParameterInfo inutParameterInfo = new InutParameterInfo();
	inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
	inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
	inutParameterInfo.setClientID(sessionMng.m_lClientID);
	inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	inutParameterInfo.setTransTypeID(financeInfo.getTransType());
	//审批中和取消审批不区分本单位审批和下属单位审批
	if (OBConstant.SettInstrStatus.DOAPPRVOAL != strAction && OBConstant.SettInstrStatus.CANCELAPPRVOAL != strAction) {
		inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
	}
	isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
	
	
	
	
	/* 初始化EJB */
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();
	

	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVE))
	|| pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL))){
		
		//网银最晚提交时间效验
		OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
		isCommitTimes = commitTime.validateOBCommitTime(tsExecute,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		if(isCommitTimes == false){
			tsExecute.setDate(tsExecute.getDate() + 1);
		}
		
		//验证签名开始
		SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
		SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
		boolean validate = facade.validateSignFromClient(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY,request);
		if(!validate)
		{
			throw new Exception("验证客户端签名信息失败!");
		}
		
		
		//验证签名结束
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
			//构造参数类
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
			//新增
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
			sessionMng.getActionMessages().addMessage("操作成功");
		}
		else{
			sessionMng.getActionMessages().addMessage("操作成功，提交时间已超过结算最迟接收时间, 执行日顺延一日!");
		}
		pageInfo.success();
	}
	
				/*保存汇款用途摘要信息*/
				
				String sNote = "";
				sTemp = (String)request.getAttribute("sNote");
				if(sTemp!=null&&sTemp.trim().length()>0)
				{
					sNote = sTemp;
					AbstractID = Long.parseLong(sNote);//汇款用途ID
				}
				
				
				sTemp = (String)request.getAttribute("sCode");//汇款用途编号
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
				
				//构建页面分发时需要用到的实体
				nextPageURL = pageInfo.getStrNextPageURL()+"?isNeedApproval="+isNeedApproval;

}
catch (Exception ie){	
	ie.printStackTrace();
    //OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    sessionMng.getActionMessages().addMessage("操作失败！"+ie.getMessage());
    nextPageURL = strFailPageURL;
    request.setAttribute("financeInfo",financeInfo);
	return;
}

finally
	{
		
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(nextPageURL);
		//分发
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
			//添加外部指令接收日志	ADD xiangzhou 2011-4-2
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