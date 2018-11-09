<%
/**
 * 页面名称 ：q003-c.jsp
 * 页面功能 : 指令查询控制类
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.query.dataentity.*,
			com.iss.itreasury.ebank.util.*,	
			com.iss.itreasury.ebank.obdataentity.*,			
			com.iss.itreasury.ebank.obquery.dataentity.*,
			com.iss.itreasury.ebank.obquery.bizlogic.*,
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

		String action=GetParam(request,"txtAction","");
		long pageNo=GetNumParam(request,"txtPageNo",1);
		termInfo.setTxtPageNo(pageNo);
		String loanDateBegin=GetParam(request,"inputDateBegin","");
		String loanDateEnd=GetParam(request,"inputDateEnd","");
		String scodeBegin=GetParam(request,"scodeBegin","");
		String scodeEnd=GetParam(request,"scodeEnd","");
		String sinstrNoBegin=GetParam(request,"sinstrNoBegin","");
		String sinstrNoEnd=GetParam(request,"sinstrNoEnd","");
		String userName=GetParam(request,"inputUserName","");
		termInfo.setClientID(sessionMng.m_lClientID);
		if (action.equals("query"))
		{
%>
<jsp:setProperty name="termInfo" property="*" />
<%
			termInfo.setClientID(sessionMng.m_lClientID);
			termInfo.setInputDateBegin(loanDateBegin);
			termInfo.setInputDateEnd(loanDateEnd);
			termInfo.setCodeBegin(scodeBegin);
			termInfo.setCodeEnd(scodeEnd);
			termInfo.setInstrNoBegin(sinstrNoBegin);
			termInfo.setInstrNoEnd(sinstrNoEnd);
			termInfo.setInputUserName(userName);
			//termInfo.setTxtPageNo(pageNo);
		}
		OBInstrQuery operation=new OBInstrQuery();
		QuerySumInfo sumInfo=new QuerySumInfo();
		Collection c=operation.queryInstr(sumInfo,termInfo);
		request.setAttribute("Result",c);
		request.setAttribute("SumInfo",sumInfo);
		
		String strURL="/loan/query/q002-v.jsp";
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));

	    	rd.forward(request, response);
	
		return;
	
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户管理","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>