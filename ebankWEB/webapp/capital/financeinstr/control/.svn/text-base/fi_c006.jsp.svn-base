<%--
/*
 * 程序名称：fi_c006.jsp
 * 功能说明：资金划拨修改控制页面
 * 作　　者：niweinan
 * 完成日期：2011年03月23日
 */
--%>
 
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*
			     " 
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header start-->

<%!
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";
	String strTemp = "";
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
		strTemp = request.getParameter("lInstructionID");
		if(strTemp != null && strTemp.length() > 0){
			lInstructionID = Long.parseLong(strTemp);
		}
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		//FinanceInfo tempFinanceInfo = null;
		//if(session.getAttribute("financeInfo") != null)
		//	tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		//session.setAttribute("financeInfo", null);
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
		if(financeInfo.getSBatchNo()==null)
		{
			financeInfo.setSBatchNo("");
		}

		System.out.println("查询出来3"+financeInfo.getSBatchNo());

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
		String 	strNextPageURL = "../view/fi_v001.jsp";
	
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPageURL);
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

