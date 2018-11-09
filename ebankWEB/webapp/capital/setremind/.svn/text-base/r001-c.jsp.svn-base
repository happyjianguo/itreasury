<%--
/*
 * 程序名称：r001-c.jsp
 * 功能说明：提醒设置--用款计划提醒操作页面
 * 作　　者：杨垒 (leiyang)
 * 完成日期：2007/06/21
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obsetremind.dataentity.OB_OperationReminderInfo"%>
<%@ page import="com.iss.itreasury.ebank.obsetremind.bizlogic.OB_OperationReminderBiz"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
long lOfficeId = -1;
long lCurrencyId = -1;
long lClientId = -1;

long lIdOpenFixDeposit = -1;
long lFateOpenFixDeposit = -1;
long lIdNotifyDepositDraw = -1;
long lFateNotifyDepositDraw = -1;
long lIdCapTransfer = -1;
long lFateCapTransfer = -1;
long lIdUnOpenFixDeposit = -1;
long lFateUnOpenFixDeposit = -1;
long lIdUnNotifyDepositDraw = -1;
long lFateUnNotifyDepositDraw = -1;

OB_OperationReminderInfo info = null;
OB_OperationReminderInfo queryInfo = null;

try {

	
	 /** 权限检查 **/
    Log.print("=================进入页面/capital/setremind/r001-c.jsp=========");
    String strTitle = "用款计划提醒"; 

    /*
     * 校验客户端请求的有效性
     */
    OBHtml.validateRequest(out,request,response);
    
	String strTemp = null;
	/*strTemp = request.getParameter("strAction");
	if(strTemp != null && strTemp.length()>0) {
		strAction = strTemp;
	}*/
	
	strTemp = request.getParameter("Id_OpenFixDeposit");
	if(strTemp != null && strTemp.length()>0) {
		lIdOpenFixDeposit = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Fate_OpenFixDeposit");
	if(strTemp != null && strTemp.length()>0) {
		lFateOpenFixDeposit = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Id_NotifyDepositDraw");
	if(strTemp != null && strTemp.length()>0) {
		lIdNotifyDepositDraw = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Fate_NotifyDepositDraw");
	if(strTemp != null && strTemp.length()>0) {
		lFateNotifyDepositDraw = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Id_CapTransfer");
	if(strTemp != null && strTemp.length()>0) {
		lIdCapTransfer = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Fate_CapTransfer");
	if(strTemp != null && strTemp.length()>0) {
		lFateCapTransfer = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Id_UnOpenFixDeposit");
	if(strTemp != null && strTemp.length()>0) {
		lIdUnOpenFixDeposit = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Fate_UnOpenFixDeposit");
	if(strTemp != null && strTemp.length()>0) {
		lFateUnOpenFixDeposit = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Id_UnNotifyDepositDraw");
	if(strTemp != null && strTemp.length()>0) {
		lIdUnNotifyDepositDraw = Long.parseLong(strTemp);
	}
	
	strTemp = request.getParameter("Fate_UnNotifyDepositDraw");
	if(strTemp != null && strTemp.length()>0) {
		lFateUnNotifyDepositDraw = Long.parseLong(strTemp);
	}
	
	/**
	* 实例化逻辑类
	*/
	OB_OperationReminderBiz biz = new OB_OperationReminderBiz();

	/**
	* 为业务实体类装数据
	*/
	lOfficeId = sessionMng.m_lOfficeID;
    lCurrencyId = sessionMng.m_lCurrencyID;
    lClientId = sessionMng.m_lClientID;
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdOpenFixDeposit);
	info = biz.findOperationReminder(queryInfo);
	if(info == null) {
		info = new OB_OperationReminderInfo();
		info.setOfficeId(lOfficeId);
		info.setCurrencyId(lCurrencyId);
		info.setClientId(lClientId);
		info.setOperationId(lIdOpenFixDeposit);
		info.setOperationFate(lFateOpenFixDeposit);
		info.setInputUserId(sessionMng.m_lUserID);
		biz.addOperationReminder(info);
	}
	else {
		info.setOperationFate(lFateOpenFixDeposit);
		biz.update(info);
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdNotifyDepositDraw);
	info = biz.findOperationReminder(queryInfo);
	if(info == null) {
		info = new OB_OperationReminderInfo();
		info.setOfficeId(lOfficeId);
		info.setCurrencyId(lCurrencyId);
		info.setClientId(lClientId);
		info.setOperationId(lIdNotifyDepositDraw);
		info.setOperationFate(lFateNotifyDepositDraw);
		info.setInputUserId(sessionMng.m_lUserID);
		biz.addOperationReminder(info);
	}
	else {
		info.setOperationFate(lFateNotifyDepositDraw);
		biz.update(info);
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdCapTransfer);
	info = biz.findOperationReminder(queryInfo);
	if(info == null) {
		info = new OB_OperationReminderInfo();
		info.setOfficeId(lOfficeId);
		info.setCurrencyId(lCurrencyId);
		info.setClientId(lClientId);
		info.setOperationId(lIdCapTransfer);
		info.setOperationFate(lFateCapTransfer);
		info.setInputUserId(sessionMng.m_lUserID);
		biz.addOperationReminder(info);
	}
	else {
		info.setOperationFate(lFateCapTransfer);
		biz.update(info);
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdUnOpenFixDeposit);
	info = biz.findOperationReminder(queryInfo);
	if(info == null) {
		info = new OB_OperationReminderInfo();
		info.setOfficeId(lOfficeId);
		info.setCurrencyId(lCurrencyId);
		info.setClientId(lClientId);
		info.setOperationId(lIdUnOpenFixDeposit);
		info.setOperationFate(lFateUnOpenFixDeposit);
		info.setInputUserId(sessionMng.m_lUserID);
		biz.addOperationReminder(info);
	}
	else {
		info.setOperationFate(lFateUnOpenFixDeposit);
		biz.update(info);
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdUnNotifyDepositDraw);
	info = biz.findOperationReminder(queryInfo);
	if(info == null) {
		info = new OB_OperationReminderInfo();
		info.setOfficeId(lOfficeId);
		info.setCurrencyId(lCurrencyId);
		info.setClientId(lClientId);
		info.setOperationId(lIdUnNotifyDepositDraw);
		info.setOperationFate(lFateUnNotifyDepositDraw);
		info.setInputUserId(sessionMng.m_lUserID);
		biz.addOperationReminder(info);
	}
	else {
		info.setOperationFate(lFateUnNotifyDepositDraw);
		biz.update(info);
	}
	
	sessionMng.getActionMessages().addMessage("保存成功");
	pageInfo.setStrNextPageURL("r001-v.jsp");
	
	
		/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	
	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:" + pageInfo.getStrNextPageURL());	
	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	
	
	rd.forward( request,response );
}
/**
* 异常处理
*/
catch ( Exception exp ) {
	/**
	* 用户提交信息置入request中
	*/
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	sessionMng.getActionMessages().addMessage(exp);
	/**
	 * 出现异常,操作结果置为失败
	 */
	pageInfo.fail();
}
%>