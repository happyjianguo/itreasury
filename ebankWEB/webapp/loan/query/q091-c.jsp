<%
/**
 * 页面名称 ：q091-c.jsp
 * 页面功能 : 查询—合同计划查询-控制类页面
 * 作    者 ：qqgd
 * 日    期 ：2004-2-20
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.query.bizlogic.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<%
	try
	{
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

		OBContractQuery contractejb = new OBContractQuery();
		ContractInfo contractinfo = new ContractInfo();
		long lContractID=GetNumParam(request,"lContractID",-1);

		contractinfo = contractejb.findByID(lContractID);
		String strContractCode=contractinfo.getContractCode();//合同编号
				
		OBQueryTermInfo qInfo=new OBQueryTermInfo();
		qInfo.setContractID(lContractID);
		qInfo.setClientID(sessionMng.m_lClientID);
		Collection c=contractejb.queryContractPlan(qInfo);

		request.setAttribute("ContractCode",strContractCode);
		request.setAttribute("queryResult",c);
		String strURL = "/loan/query/q092-v.jsp";
	
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
	}
	catch(Exception exp)
	{
		exp.printStackTrace();
		out.flush();
		return;
	}
%>