<%--
/*
 * 程序名称：c001-c.jsp
 * 功能说明：资金划拨修改控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>
 
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
			     javax.servlet.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->

<%!
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
%>

<%  
	/* 用户登录检测与权限校验 */
	try 
	{
	
        //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
	
%>

<!--Get Data start-->
<%
		/* 指令序号 */
		long lInstructionID = -1;
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		FinanceInfo tempFinanceInfo = null;
		if(session.getAttribute("financeInfo") != null)
			tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		session.setAttribute("financeInfo", null);
		
		
		
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* 实例化信息类 */
		FinanceInfo financeInfo = null;
		
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		/* 调用方法获取信息对象 */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		
		if(	lInstructionID < 0 && tempFinanceInfo!=null && tempFinanceInfo.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY) 
			financeInfo = tempFinanceInfo;
		
		
			ClientAccountInfo accountInfo=null;
			long accountID=-1;
			OBFinanceInstrDao dao = new OBFinanceInstrDao();
			//if (financeInfo!=null)	accountID=financeInfo.getPayerAcctID();
			accountInfo=dao.findAccountInfoByClient(sessionMng.m_lUserID, sessionMng.m_lClientID, accountID, 1);
			
			request.setAttribute("accountInfo",accountInfo);

%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		
	    /* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		
		RequestDispatcher rd = null;
	    /* 设置返回地址 */

	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/captransfer/c012-v.jsp");
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    
		/* forward到结果页面 */
	    rd.forward(request, response);
	} 
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->

