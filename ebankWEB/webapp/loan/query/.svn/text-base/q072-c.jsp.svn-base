<%
/**
 * 页面名称 ：q072-c.jsp
 * 页面功能 : 查询—贷款展期申请查询-控制类页面
 * 作    者 ：qqgd
 * 日    期 ：2003-11-18
 * 特殊说明 ：简单实现说明：
 *				1、根据条件查询。
 *				2、结果列表。
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.query.dataentity.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

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

		long pageNo=GetNumParam(request,"txtPageNo",1);
		double amountBegin=GetDoubleParam(request,"amountBeginStr",0);
		double amountEnd=GetDoubleParam(request,"amountEndStr",0);
		double amountBegin2=GetDoubleParam(request,"amountBegin2Str",0);
		double amountEnd2=GetDoubleParam(request,"amountEnd2Str",0);
		String loanDateBegin=GetParam(request,"loanDateBegin","");
		String loanDateEnd=GetParam(request,"loanDateEnd","");
%>
<jsp:setProperty name="termInfo" property="*" />
<%
		termInfo.setTxtPageNo(pageNo);
		termInfo.setAmountBegin(amountBegin);
		termInfo.setAmountEnd(amountEnd);
		termInfo.setAmountBegin2(amountBegin2);
		termInfo.setAmountEnd2(amountEnd2);
		termInfo.setClientID(sessionMng.m_lClientID);
		termInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		termInfo.setLoanDateBegin(loanDateBegin);
		termInfo.setLoanDateEnd(loanDateEnd);
		
		OBExtendQuery operation=new OBExtendQuery();
		Collection c=operation.queryExtend(termInfo);
		QuerySumInfo sumInfo=operation.QueryExtendSum(termInfo);
		
		request.setAttribute("Result",c);
		request.setAttribute("SumInfo",sumInfo);
		
		String strURL="/loan/query/q073-v.jsp";

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