<%
	/*
	*页面名称：c001.jsp
	*功能：银行汇款信息修改
	*作者：libaihui
	*time:2006-09-11
	*/
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* 标题固定变量 */
	String strTitle="[银行汇款]";
%>
<%
try{
	/* 实现菜单控制 */
	 OBHtml.validateRequest(out,request,response);
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	long lSourceType = 0;//头信息显示
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
%>
<%	
		
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		OBBankPayInfo info=new OBBankPayInfo();
		info.convertRequestToDataEntity(request);
		info=financeInstr.findByID(info.getId());
		
		request.setAttribute("info",info);
		
		String doact=request.getParameter("doact");
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		
		//分发
		RequestDispatcher rd = null;
	    if(doact!=null && doact.equals("querymodify"))
	    {
	    	pageControllerInfo.setUrl("../view/v304.jsp");
	    	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
	    }
	    else {
	    	if(lSourceType==0)
	    	{
	    		pageControllerInfo.setUrl("../view/v003.jsp");
	    	}
	    	else
	    	{
	    		pageControllerInfo.setUrl("../view/v003.jsp?src="+lSourceType);
	    	}
	    	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	

		}
	   /* forward到结果页面 */
		rd.forward(request, response);	

	} 
	catch(IException ie) 
	{
		Log.print("v001.jsp:EJB调用异常或者跳转有错");
		//session.setAttribute("financeInfo", financeInfo);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }

%>