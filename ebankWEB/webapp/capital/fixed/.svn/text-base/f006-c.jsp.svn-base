<!--
/*
 * 程序名称：f006-c.jsp
 * 功能说明：到期续存修改控制页面
 * 作　　者：葛亮
 * 完成日期：2007年04月13日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.SETTConstant,
			     javax.servlet.*,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->

<%!
	/* 标题固定变量 */
	String strTitle = "[到期续存]";
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

		String dPayerEndDate="";
		if(request.getAttribute("dPayerEndDate")!=null)
		{
			dPayerEndDate =(String) request.getAttribute("dPayerEndDate");   //定期存款结束日
		}
		
		String lEndDate="";
		if(request.getAttribute("endDate")!=null)
		{
			lEndDate =(String) request.getAttribute("endDate");   //定期存款结束日
		}
	
	
		/* 指令序号 */
		long lInstructionID = -1;
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("------------------------------lInstructionID="+lInstructionID);
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* 实例化信息类 */
		FinanceInfo financeInfo = null;
		PayerOrPayeeInfo payeeInfo = null;
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		

		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
	
		/* 调用方法获取信息对象 */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//交易信息
			if(financeInfo.getSubAccountID() > 0)
		{
		request.setAttribute("lSubAccount",String.valueOf(financeInfo.getSubAccountID()));
		}
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		payeeInfo = biz.getLoanAccountSelectInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED,sessionMng.m_lUserID);//收款方信息
		if(payeeInfo == null)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "../../LoginMain.html",1, "OB_E002");
			return;
		}
	    
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		request.setAttribute("payeeInfo", payeeInfo);
		
	    /* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
	    
	    
	    //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/fixed/f006-v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    
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

