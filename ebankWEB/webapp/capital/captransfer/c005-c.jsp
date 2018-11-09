<%--
/*
 * 程序名称：c005-c.jsp
 * 功能说明：资金划拨删除控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 javax.servlet.*"
%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

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
	TransInfo transinfo = new TransInfo();
	FinanceInfo financeinfo = null;
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
        //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
	 } 
	 catch (Exception e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>

<!--Get Data start-->
<%
	/* 指令序号 */
	long lInstructionID = -1;
	/* 登录人ID */
	long lUserID = sessionMng.m_lUserID;
	
	Timestamp dtmodify = null;
	try {
		lInstructionID = GetNumParam(request,"lInstructionID");
		dtmodify = DataFormat.getDateTime((String)request.getParameter("dtmodify"));
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
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		/* 调用EJB方法删除信息对象 */
		try
		{
		financeinfo = new FinanceInfo();
		financeinfo.setID(lInstructionID);
		financeinfo.setDeleteUserID(lUserID);
		financeinfo.setDtModify(dtmodify);
		lDeleteResult = financeInstr.deleteCapitalTrans(financeinfo);
		transinfo.setStatus(Constant.SUCCESSFUL);
		transinfo.setActionType(Constant.TransLogActionType.delete);
		financeinfo = financeInstr.findByID(lInstructionID,lUserID,sessionMng.m_lCurrencyID);
		}catch(Exception ex)
		{
			transinfo.setStatus(Constant.FAIL);
			transinfo.setActionType(Constant.TransLogActionType.delete);
			ex.printStackTrace();
			throw new IException(ex.getMessage());
		}
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
		if (lShowMenu == OBConstant.ShowMenu.NO)
		{
		    out.write("<script>window.close();window.opener.queryme();</script>");
			return;
		}
		request.setAttribute("lInstructionID", "-1");
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    if(request.getParameter("flag")!=null&&request.getParameter("flag").equals("delete")){
	    	request.setAttribute("flag",request.getParameter("flag"));
	    }
	   
	  	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/captransfer/c002-v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));  

	    /* forward到结果页面 */
	    rd.forward(request, response);
	} 
	}catch(Exception e) {
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		
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
				transinfo.setTransType(financeinfo.getTransType());
				translofbiz.saveTransLogInfo(sessionMng,financeinfo,transinfo);
			
			}
			

		//minzhao 20090520 end translogger 
		}
	
%>
<!--Forward end-->