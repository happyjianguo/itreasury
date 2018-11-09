<%
/**
 * 页面名称 ：q032-c.jsp
 * 页面功能 : 贷款合同查找控制
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
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
			com.iss.system.dao.PageLoader,
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
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
		long pageNo=GetNumParam(request,"txtPageNo",1);
		long lDesc = GetNumParam(request,"lDesc",1);
		long lOrderParam = GetNumParam(request,"lOrderParam",1);
		double amountBegin=GetDoubleParam(request,"amountBeginStr",0);
		double amountEnd=GetDoubleParam(request,"amountEndStr",0);
		String loanDateBegin=GetParam(request,"loanDateBegin","");
		String loanDateEnd=GetParam(request,"loanDateEnd","");
		String purpose=GetParam(request,"purpose","");					/*区别是来自贷款合同查询还是贴现合同查询 */
		String nextPage="";
%>
<jsp:setProperty name="termInfo" property="*" />
<%		
		termInfo.setAmountBegin(amountBegin);
		termInfo.setAmountEnd(amountEnd);
		termInfo.setClientID(sessionMng.m_lClientID);
		termInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		termInfo.setLoanDateBegin(loanDateBegin);
		termInfo.setLoanDateEnd(loanDateEnd);
		termInfo.setTxtPageNo(pageNo);
		termInfo.setLOrderParam(lOrderParam);
		termInfo.setLDesc(lDesc);

		if ( purpose.equals("TX") )
		{
			termInfo.setQueryPurpose(OBQueryTermInfo.TX);
			nextPage="/loan/query/q052-v.jsp";
		}
		else
		{
			termInfo.setQueryPurpose(OBQueryTermInfo.CONTRACT);
			nextPage="/loan/query/q033-v.jsp";
		}	
			
		/*开始查询动作*/
		OBContractQuery operation=new OBContractQuery();
		Collection c=operation.queryContract(termInfo);
		QuerySumInfo sumInfo=operation.queryContractSum(termInfo);	

		request.setAttribute("queryResult",c);
		request.setAttribute("SumInfo",sumInfo);
		String strURL = nextPage;
			
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
     }
     
%>
 

