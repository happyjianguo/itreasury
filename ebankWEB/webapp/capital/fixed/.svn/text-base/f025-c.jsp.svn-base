<%--
 页面名称 ：c050.jsp
 页面功能 : 找出定期开立己复核并且换开定期存单的状态为空或者为己复核的定期开立数据
 作    者 ：feiye
 日    期 ：2006-03-23
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/* 标题固定变量 */
	String strTitle = "[换开定期存单]";

	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    try
	{
		OBHtml.validateRequest(out, request, response);

		//定义变量
		long lID=-1;		//定期开立数据的ID号			

		//读取数据		
        strAction = (String)request.getAttribute("strAction");

        String SDEPOSITBILLNO = (String)request.getParameter("SDEPOSITBILLNO");
        String Abstract = (String)request.getAttribute("lAbstractID");
		String strTemp = null;
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lID = Long.valueOf(strTemp).longValue();
		}

		strTemp = request.getParameter("nOrderByCode");
		String OrderByCode = "";
		if(strTemp != null)
		{		
			OrderByCode = strTemp;
		}
		/* 实例化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();		

		/* 调用方法获取信息对象 */	
	    if(strAction.equals(new Integer(SETTConstant.Actions.MODIFYTEMPSAVE).toString())){
			//暂存
			financeInfo.setID(lID);
			financeInfo.setNDepositBillStatusId(OBConstant.SettInstrStatus.DEAL);
			financeInfo.setSDepositBillNo(SDEPOSITBILLNO);
			financeInfo.setNDepositBillInputuserId(sessionMng.m_lUserID);
			financeInfo.setDtDepositBillInputdate(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));
			financeInfo.setSDepositBillAbstract(Abstract);
			//执行业务操作
	    	lID = financeInstr.TransOpenFixdDePositUpdtae(financeInfo);
			if(lID>0)
				sessionMng.getActionMessages().addMessage("暂存成功！");
			else 
				sessionMng.getActionMessages().addMessage("暂存失败！");
	    	strNextPageURL = "f021-c.jsp?type=tempsave";
		}else if(strAction.equals(new Integer(SETTConstant.Actions.DELETE).toString())){
			//删除
			financeInfo.setID(lID);
			financeInfo.setNDepositBillStatusId(OBConstant.SettInstrStatus.DELETE);
			//执行业务操作
	    	lID = financeInstr.TransOpenFixdDePositUpdtae(financeInfo);
			if(lID>0)
				sessionMng.getActionMessages().addMessage("删除成功！");
			else 
				sessionMng.getActionMessages().addMessage("删除失败！");
			
			financeInfo.setClientID(sessionMng.m_lClientID);
			financeInfo.setOrderByCode(OrderByCode);
			financeInfo.setNDepositBillStatusId(OBConstant.SettInstrStatus.SAVE);
			Collection searchResults = financeInstr.getTransOpenFixdDePosit(financeInfo);
			request.setAttribute("searchResults",searchResults);
	    	strNextPageURL = "f022-v.jsp?type=linkSearch&lID=" + lID;
		}else if(strAction.equals(new Integer(SETTConstant.Actions.MODIFYSAVE).toString())){
			//保存
			financeInfo.setID(lID);
			financeInfo.setClientID(sessionMng.m_lClientID);
			financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			financeInfo.setNDepositBillStatusId(OBConstant.SettInstrStatus.SAVE);
			financeInfo.setSDepositBillNo(SDEPOSITBILLNO);
			financeInfo.setNDepositBillInputuserId(sessionMng.m_lUserID);
			financeInfo.setDtDepositBillInputdate(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));
			financeInfo.setSDepositBillAbstract(Abstract);
			//执行业务操作
	    	lID = financeInstr.TransOpenFixdDePositUpdtae(financeInfo);
			if(lID>0)
				sessionMng.getActionMessages().addMessage("保存成功！");
			else 
				sessionMng.getActionMessages().addMessage("保存失败！");
	    	strNextPageURL = "f021-c.jsp?type=save";
		}else if(strAction.equals("linkSearch"))
		{
			financeInfo.setClientID(sessionMng.m_lClientID);
			financeInfo.setOrderByCode(OrderByCode);
			financeInfo.setNDepositBillStatusId(OBConstant.SettInstrStatus.SAVE);
			Collection searchResults = financeInstr.getTransOpenFixdDePosit(financeInfo);
			request.setAttribute("searchResults",searchResults);
	    	strNextPageURL = "f022-v.jsp?type=linkSearch&lID=" + lID;
		}
		else{
			Log.print("没有此操作");
		}
		request.setAttribute("financeInfo",financeInfo);
		//转向下一页面
		Log.print("Next Page URL:"+strNextPageURL);	
		
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
	
		rd.forward( request,response );
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}
	
%>