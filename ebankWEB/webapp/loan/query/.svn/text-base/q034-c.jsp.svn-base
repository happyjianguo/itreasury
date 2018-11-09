<%
/**
 * 页面名称 ：q034-c.jsp
 * 页面功能 : 查询贷款明细信息
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 转入页面 : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.contract.bizlogic.*,
			com.iss.itreasury.loan.contract.dataentity.*,
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
<%@ include file="/common/common.jsp" %>
<%	
    try 
    {
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

		long contractID=GetNumParam(request,"contractID",-1);
		
		OBContractQuery lcs = new OBContractQuery();
		
		ContractInfo conInfo=lcs.findByID(contractID);
		
		request.setAttribute("ContractInfo",conInfo);
		
		String strURL = "/loan/query/q035-v.jsp";

	
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"委托客户选择", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
		return; 
	  
     }
     
%>