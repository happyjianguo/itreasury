<%--
/*
 * 程序名称：f009-1-c.jsp
 * 功能说明：到期续存删除控制页面
 * 作　　者：蔡俊
 * 完成日期：2007年04月25日
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
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

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
	/* 初始化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
    TransInfo transinfo = new TransInfo();
    Timestamp dtmodify=null;//上一次修改时间  add by zhanglei date 2010.06.01
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
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		if(request.getAttribute("dtmodify")!=null){
			dtmodify = DataFormat.getDateTime((String) request.getParameter("dtmodify"));// 上次修改时间  add by zhanglei  date 2010.06.01
		}
		/* 调用EJB方法删除信息对象 */
		//modify by xwhe 2009-05-25 网银日志的添加
		try
		    {
		         financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		         financeInfo.setDtModify(dtmodify);
		         financeInfo.setDeleteUserID(sessionMng.m_lUserID);
		         lDeleteResult = financeInstr.deleteCapitalTrans(financeInfo);
                 transinfo.setStatus(Constant.SUCCESSFUL);
		    }
	   catch(Exception ex)
			{
			     
			     transinfo.setStatus(Constant.FAIL);
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
	request.setAttribute("delete","delete");
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
	   
	   //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/fixed/f006-c.jsp?lInstructionID=-1");
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
			if(transinfo.getStatus()!=-1)
			{
				TranslogBiz translofbiz= new TranslogBiz();
				transinfo.setHostip(request.getRemoteAddr());
				transinfo.setHostname(request.getRemoteHost());
				transinfo.setTransType(OBConstant.SettInstrType.DRIVEFIXDEPOSIT);
				transinfo.setActionType(Constant.TransLogActionType.delete);				
				translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			}
		}
%>
<!--Forward end-->