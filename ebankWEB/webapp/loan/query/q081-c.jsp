<%
/**
 * 页面名称 ：q081-c.jsp
 * 页面功能 : 免还申请查询页面
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 转入页面 : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.query.dataentity.*,
			com.iss.itreasury.loan.query.bizlogic.*,
			com.iss.itreasury.ebank.util.*,			
			com.iss.itreasury.ebank.obdataentity.*,	
			com.iss.itreasury.ebank.obquery.bizlogic.*,
			com.iss.itreasury.ebank.obquery.dataentity.*,
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
    try{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		String action=GetParam(request,"actType","");
		long pageNo=GetNumParam(request,"txtPageNo",1);
		termInfo.setTxtPageNo(pageNo);
		termInfo.setClientID(sessionMng.m_lClientID);
		String sCodeBegin = "";
		String sCodeEnd = "";
		if( request.getParameter("codeBegin")!=null )
			sCodeBegin=request.getParameter("codeBegin");
		if( request.getParameter("codeEnd")!=null )
			sCodeEnd=request.getParameter("codeEnd");
		
		if (action.equals("contract") || action.equals("query"))
		{
%>
<jsp:setProperty name="termInfo" property="*" />
<%
			termInfo.setCodeEnd(sCodeBegin);
			termInfo.setCodeBegin(sCodeBegin);
			termInfo.setTxtPageNo(pageNo);
			termInfo.setClientID(sessionMng.m_lClientID);
			termInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		}
			
		OBFreeQuery operation=new OBFreeQuery();
		Collection c=operation.questFree(termInfo);
		
		request.setAttribute("Result",c);
		
		String strURL="/loan/query/q082-v.jsp";
	
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
     	OBHtml.showExceptionMessage(out,sessionMng,ie,"免还申请查询","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
		return; 
	  
     }
     
%>