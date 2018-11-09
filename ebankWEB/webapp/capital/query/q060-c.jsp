<%--
/*
 * 程序名称：q000-c.jsp
 * 功能说明：交易申请查询控制页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-11
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //标题变量
    String strTitle = "［交易申请查询］";
    try {
       /* 定义对表单的相应变量 */
        long lFixTransferID = -1;       //定期支取
        long lNotifyTransferID = -1;    //通知支取

        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
        lFixTransferID = obFinanceInstrDao.getLoanAccountID(
            sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
        lNotifyTransferID = obFinanceInstrDao.getLoanAccountID(
            sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
        /* 在请求中保存结果对象 */
        request.setAttribute("lFixTransferID", String.valueOf(lFixTransferID));
        request.setAttribute("lNotifyTransferID", String.valueOf(lNotifyTransferID));

        /* 获取上下文环境 */
        //ServletContext sc = getServletContext();
		
		/* 设置返回地址 */
		
		
		
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/query/q061-v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		
        /* forward到结果页面 */
        rd.forward(request, response);
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>