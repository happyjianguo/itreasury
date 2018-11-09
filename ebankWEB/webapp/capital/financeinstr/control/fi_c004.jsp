<%--
 页面名称 ：fi_c001.jsp
 页面功能 : 网上银行 - 逐笔付款 删除
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
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@page import="java.sql.Timestamp"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	FinanceInfo financeInfo = new FinanceInfo();
	String strTitle = "逐笔付款";
	String strTemp = "";
	TransInfo transinfo = new TransInfo();
	long lInstructionID = -1;
	Timestamp dtmodify = null;
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
	strTemp = request.getParameter("dtmodify");
	if(strTemp != null && !strTemp.equals("dtmodify")){
	dtmodify = DataFormat.getDateTime(strTemp);
	}
	/* 初始化EJB */
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
		sessionMng.getActionMessages().addMessage("删除成功");
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
