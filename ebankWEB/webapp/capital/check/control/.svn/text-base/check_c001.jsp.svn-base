<%--
 页面名称 ：check_c001.jsp
 页面功能 : 申请指令复核查询
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
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.ebank.util.OBSignatureUtil"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "申请指令复核查询";
	String strTemp = "";
	
	long lRemitType = -1;
	boolean isNeedApproval = false;
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
	
	strTemp = request.getParameter("nPayerAccountID");
	if(strTemp != null && strTemp.length()>0){
		financeInfo.setPayerAcctID(Long.parseLong(strTemp));
	}
 	
	strTemp = request.getParameter("nRemitType");
	if(strTemp != null && strTemp.length()>0){
		lRemitType = Long.parseLong(strTemp);
		financeInfo.setRemitType(lRemitType);
    }
	
	strTemp = request.getParameter("nPayeeAccountID");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setPayeeAcctID(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("dAmount");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
	}
	
	strTemp = request.getParameter("tsExecute");
	if(strTemp != null && !strTemp.equals("")){
		financeInfo.setExecuteDate(DataFormat.getDateTime(strTemp));
	}
	
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
	
	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.QueryOperationType.CHECK))){
		/* 从session中获取相应数据 */
	    financeInfo.setClientID(sessionMng.m_lClientID);
	    financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
	    financeInfo.setUserID(sessionMng.m_lUserID);
	    financeInfo.setConfirmUserID(sessionMng.m_lUserID);
	    
	    isNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng, financeInfo);
		if(isNeedApproval == true){
			 financeInfo.setStatus(OBConstant.SettInstrStatus.APPROVALED);
		}
		else {
			 financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}
		Collection coll = obFinanceInstrBiz.queryCollectionByCheck(financeInfo);
		if(coll != null){
			request.setAttribute("financeInfo",coll.toArray()[0]);
			request.setAttribute("isNeedApproval",String.valueOf(isNeedApproval));
			pageInfo.success();
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request,response);
		}
	    else {
%>	    	<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%	    }
	}
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
