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
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*
			     " 
%>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.bizlogic.ConsignReceiveBiz" %>
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
	
	String lsign = null;
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}

	String lreport=null;
	String report="";
	lreport = request.getParameter("report");
	if(lreport!=null&&lreport.trim().length()>0)
	{
	      report=lreport;
	}

	
	String lupdate = null;
	String update = "";
	lupdate = request.getParameter("update");
	if(lupdate!=null&&lupdate.trim().length()>0)
	{
		update = lupdate;
	}
	

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
	//	tempFinanceInfo.setSBatchNo(financeInfo.getSBatchNo());
		System.out.println("查询出来3"+financeInfo.getSBatchNo());
		//if(	lInstructionID < 0 && tempFinanceInfo!=null && tempFinanceInfo.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY) 
		//	financeInfo = tempFinanceInfo;
		
		 //查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
        ConsignReceiveBiz crBiz = new ConsignReceiveBiz();
        if ( crBiz.isConsignReceive(lInstructionID)) {
        	request.setAttribute("isConsignReceive", "true");
        }
        
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
		String flag = "";
		flag=(String)request.getAttribute("flag");
		if(report.equals("report"))
		{
		request.setAttribute("modify","modify");
		}
		if(flag!=null&&flag.equals("delete")){
			request.setAttribute("delete","delete");
		}
		String strNextPageURL="";
		if(sign.equals("again"))
		{
			strNextPageURL="/capital/captransfer/c002-v.jsp?sign="+sign;
		}
		else
		{
			strNextPageURL="/capital/captransfer/c002-v.jsp";
		}
		
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

