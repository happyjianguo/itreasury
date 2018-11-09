<%--
 页面名称 ：check_c002.jsp
 页面功能 : 信息查询 － 批量取消复核
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
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.info.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	QueryCapForm queryCapForm = new QueryCapForm();
	String strTitle = "批量取消复核查询";
	String strTemp = "";
	String lnstructionList[] = null;
	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	String signatureValue = "";
	String signatureOriginalValue = "";
	long lTransType = -1;
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
    
    //获取指令集
    lnstructionList = request.getParameterValues("lnstructionList");
    
	/* 初始化EJB */
	OBFinanceInstrHome obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	OBFinanceInstr obFinanceInstr = obFinanceInstrHome.create();
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	FinanceInfo financeInfo = null;
	
	if(pageInfo.getStrAction().equals("BATCHUNCHECK")){
		for(int i=0; i<lnstructionList.length; i++){
			financeInfo = obFinanceInstrBiz.findByInstructionID(Long.parseLong(lnstructionList[i]), sessionMng.m_lCurrencyID);
			if(financeInfo == null){
				sessionMng.getActionMessages().addMessage("没有查找到被复核数据！");
				pageInfo.fail();
			}
			else {
				if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
					String[] nameArray = null;
					//String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromReq(request);
					String[] valueArray = null;
					
					if(financeInfo.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY ||
					financeInfo.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT) {
						//资金划拨
						nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
						valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromInfo(financeInfo);
					}
					else if(financeInfo.getTransType() == OBConstant.SettInstrType.OPENFIXDEPOSIT) {
						//定期开立
						nameArray = OBSignatureConstant.OpenFixDeposit.getSignNameArray();
						valueArray = OBSignatureConstant.OpenFixDeposit.getSignValueArrayFromInfo(financeInfo);
					}
					else if(financeInfo.getTransType() == OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER) {
						//定期支取
						nameArray = OBSignatureConstant.FixedToCurrentTransfer.getSignNameArray();
						valueArray = OBSignatureConstant.FixedToCurrentTransfer.getSignValueArrayFromInfo(financeInfo);
					}
					else if(financeInfo.getTransType() == OBConstant.SettInstrType.OPENNOTIFYACCOUNT) {
						//通知开立
						nameArray = OBSignatureConstant.OpenNotifyAccount.getSignNameArray();
						valueArray = OBSignatureConstant.OpenNotifyAccount.getSignValueArrayFromInfo(financeInfo);
					}
					else if(financeInfo.getTransType() == OBConstant.SettInstrType.NOTIFYDEPOSITDRAW) {
						//通知支取
						nameArray = OBSignatureConstant.NotifyDepositDraw.getSignNameArray();
						valueArray = OBSignatureConstant.NotifyDepositDraw.getSignValueArrayFromInfo(financeInfo);
					}
					else if(financeInfo.getTransType() == OBConstant.SettInstrType.DRIVEFIXDEPOSIT) {
						//定期续存
						nameArray = OBSignatureConstant.DriveFixdePosit.getSignNameArray();
						valueArray = OBSignatureConstant.DriveFixdePosit.getSignValueArrayFromInfo(financeInfo);
					}
		
					SignatureInfo signatureInfo = new SignatureInfo();
					signatureInfo.setNameArray(nameArray);
					signatureInfo.setValueArray(valueArray);
					signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
					
					try {
						signatureValue = OBSignatureUtil.doSignatureUseKeyStore(signatureOriginalValue);
					
						System.out.println("signatureValue：" + signatureValue);
						System.out.println("signatureOriginalValue：" + signatureOriginalValue.toString());		
	
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
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
				}catch(Exception ex)
				{
					transinfo.setStatus(Constant.FAIL);
					transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}
				finally
				{
					if(transinfo.getStatus()!=-1)
					{
						TranslogBiz translofbiz= new TranslogBiz();
						transinfo.setHostip(request.getRemoteAddr());
						transinfo.setHostname(request.getRemoteHost());
						transinfo.setTransType(financeInfo.getTransType());
						translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
					
					}
				}
			}
		}
		sessionMng.getActionMessages().addMessage("操作成功");
		
		/* 从session中获取相应数据 */
		queryCapForm.setStatus(OBConstant.SettInstrStatus.CHECK);
	    queryCapForm.setClientID(sessionMng.m_lClientID);
	    queryCapForm.setCurrencyID(sessionMng.m_lCurrencyID);
	    queryCapForm.setUserID(sessionMng.m_lUserID);
	    /* 根据页面菜单确定查询类型 */
	    queryCapForm.setOperationTypeID(OBConstant.QueryOperationType.QUERY);
	    queryCapForm.setOrderBy(true);
	    Collection coll = obFinanceInstrBiz.queryCollectionByQuery(queryCapForm);
	    
		request.setAttribute("queryCapForm",queryCapForm);
		request.setAttribute("queryCapColl",coll);
		pageInfo.success();
	}
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request,response);
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
