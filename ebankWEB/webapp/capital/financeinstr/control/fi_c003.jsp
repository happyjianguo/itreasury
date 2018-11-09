<%--
 页面名称 ：fi_c003.jsp
 页面功能 : 网上银行 - 逐笔付款 审批\取消审批
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
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
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = null;
	String strTitle = "逐笔付款";
	String strTemp = "";

	
	long lInstructionID = -1;
	long  osentryId = -1;
	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	String signatureValue = "";
	String signatureOriginalValue = "";
	TransInfo transinfo = new TransInfo();

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
	}
	
	strTemp = (String)request.getAttribute("osentryId");
	if (strTemp != null && strTemp.trim().length() > 0){
		osentryId = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE);
	if(strTemp != null && !strTemp.equals("")){
		signatureValue = strTemp;
	}
	
	/* 初始化EJB */
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();

	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.DOAPPRVOAL))
		|| pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.CANCELAPPRVOAL))){
		if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
			String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
			String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromReq(request);
			
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
		
			try {	
				System.out.println("signatureValue：" + signatureValue);
				System.out.println("signatureOriginalValue：" + signatureOriginalValue.toString());		
				signatureInfo.setOriginalData(signatureOriginalValue);
				signatureInfo.setSignatureValue(signatureValue);
				SignatureAuthentication.validateFromReq(signatureInfo);
			} catch (Exception e) {
					System.out.println(e.getMessage());			
					throw new IException(e.getMessage());
			}
		}

		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		//将签名值与原始数据保存
		financeInfo.setDtModify(financeInfo.getDtModify());
		financeInfo.setSignatureValue(signatureValue);
		financeInfo.setSignatureOriginalValue(signatureOriginalValue);

		StringBuffer strURL1 = new StringBuffer();
		strURL1.append(strContext + "/capital/financeinstr/control/fi_c001.jsp");
		strURL1.append("?strSuccessPageURL=/capital/financeinstr/view/fi_v004.jsp&lInstructionID="+financeInfo.getID());
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
	
		if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.SettInstrStatus.DOAPPRVOAL))){
		try {
			obFinanceInstr.doApproval(financeInfo);
			sessionMng.getActionMessages().addMessage("操作成功");
			transinfo.setStatus(Constant.SUCCESSFUL);
			transinfo.setActionType(Constant.TransLogActionType.approval);
			} catch (Exception e) {
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.approval);
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
		}
		else{
			try
			{
				obFinanceInstr.cancelApproval(financeInfo);
				sessionMng.getActionMessages().addMessage("取消审批成功");
				transinfo.setStatus(Constant.SUCCESSFUL);
				transinfo.setActionType(Constant.TransLogActionType.cancelApproval);
			} catch (Exception e) {
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.cancelApproval);
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			
		}
		financeInfo = obFinanceInstrBiz.findByInstructionID(lInstructionID, sessionMng.m_lCurrencyID);
		request.setAttribute("financeInfo",financeInfo);
		pageInfo.success();
	}
	
	//构建页面分发时需要用到的实体
	//PageControllerInfo pageControllerInfo = new PageControllerInfo();
	//pageControllerInfo.setSessionMng(sessionMng);
	//pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	//RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	//rd.forward(request,response);
	response.sendRedirect(pageInfo.getStrNextPageURL());
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
