<%
/**
 * 页面名称 ：q061-c.jsp
 * 页面功能 : 查询—合同执行情况查询控制类
 * 作    者 ：qqgd
 * 日    期 ：2003-11-18
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
				com.iss.itreasury.loan.query.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
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
		QueryOperation operation = new QueryOperation();
		ContractInfo contractinfo = new ContractInfo();

		long lContractID = -1;
		String strContractCode="";
		Collection c=null;
		String strURL="";
		QuerySumInfo sumInfo=null;
		long orderParam=GetNumParam(request,"lOrderParam",99);
		long lDesc=GetNumParam(request,"lDesc",1);
	
		lContractID=GetNumParam(request,"lContractID",-1);	
		if(lContractID > 0)
		{
			contractinfo = contractejb.findByID(lContractID);
			strContractCode=DataFormat.formatString(contractinfo.getContractCode());//合同编号
			c=operation.queryPerform(lContractID,orderParam,lDesc);
			sumInfo=operation.queryPerformSum(lContractID);
		}
		request.setAttribute("ContractCode",strContractCode);
		request.setAttribute("queryResult",c);
		request.setAttribute("SumInfo",sumInfo);
		strURL = "/loan/query/q062-v.jsp";

	
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