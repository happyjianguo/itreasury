
<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>
<%  
	/* 用户登录检测与权限校验 */
	try 
	{     
	
		/* 指令序号 */
		long lInstructionID = -1;
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("------------------------------lInstructionID="+lInstructionID);
		/* 实例化信息类 */
		FinanceInfo financeInfo = null;

		/* 初始化查询类 */
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
	
		/* 调用方法获取信息对象 */	
		financeInfo = obFinanceInstrDao.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
	
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		
	    /* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		
	    /* 设置返回地址 */
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/finance/V16.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	    
		/* forward到结果页面 */
	    rd.forward(request, response);
	} 
	catch(IException ie)
	{
		Log.print(ie.getMessage());
	}
%>

