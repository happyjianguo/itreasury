<%--
/*
 * 程序名称：C17.jsp
 * 功能说明：资金划拨删除控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月21日
 */
--%>

<!--Header start-->
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 javax.servlet.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";
%>

<%
	/* 指令序号 */
	long lInstructionID = -1;
	/* 登录人ID */
	long lUserID = sessionMng.m_lUserID;

	try 
	{
		lInstructionID = GetNumParam(request,"lInstructionID");
	} 
	catch(Exception e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
	/* 确定返回结果 */
	long lDeleteResult = -1;
try{
	if (lInstructionID > 0)
	{
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("FinanceInstrHome");
		financeInstr = financeInstrHome.create();
		/* 调用EJB方法删除信息对象 */
		lDeleteResult = financeInstr.deleteCapitalTrans(lInstructionID, lUserID);
		Log.print(lDeleteResult);
	} 
	else 
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		return;
	}
%>
<!--Access DB end-->

<!--Forward start-->
<%
	if (lDeleteResult > 0)
	{
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/finance/C15.jsp?lInstructionID=-1");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
	    /* forward到结果页面 */
	    rd.forward(request, response);
	} 
	}catch(Exception e) {
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		
		return;
	}
%>
<!--Forward end-->