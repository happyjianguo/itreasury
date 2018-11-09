<%
/**
 * 页面名称 ：q004-c.jsp
 * 页面功能 : 察看贷款申请信息控制类
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.bizlogic.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%	
	long        loanID=-1;          //贷款申请流水号
	long		userID=-1;
	
    long pageNo=1;
    long orderParam=99;
    long lDesc=Constant.PageControl.CODE_ASCORDESC_DESC;

	/* 用户登录检测与权限校验及文件头显示 */
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
		loanID=GetNumParam(request,"lLoanID",-1);
		userID=sessionMng.m_lUserID;
		orderParam=GetNumParam(request,"nOrderParam",99);
		lDesc=GetNumParam(request,"nDesc",Constant.PageControl.CODE_ASCORDESC_DESC);
		pageNo=GetNumParam(request,"nPageNo",1);
		
		String strURL="/loan/query/q005-v.jsp?lLoanID="+loanID
			+"&nOrderParam="+orderParam
			+"&nDesc="+lDesc
			+"&nPageNo="+pageNo;
		
		OBLoanQuery lla=new OBLoanQuery();
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();
		
		/*查询申请信息*/
		LoanApplyInfo laInfo=lla.findByID(loanID);		
		
		//查询计划信息
		Collection c=lla.findPlanByLoanID(loanID,pageNo,Constant.PageControl.CODE_PAGELINECOUNT,orderParam,lDesc);
		long clientID=laInfo.getBorrowClientID();
		long wtClientID=laInfo.getConsignClientID();
		
		ClientInfo clientInfo=lcs.findClientByID(clientID);
		ClientInfo wtClientInfo=lcs.findClientByID(wtClientID);
		
		request.setAttribute("LoanApplyInfo",laInfo);
		request.setAttribute("wtClientInfo",wtClientInfo);
		request.setAttribute("Collection",c);
		request.setAttribute("ClientInfo",clientInfo);
		request.setAttribute("nOrderParam",String.valueOf(orderParam));
		request.setAttribute("nDesc",String.valueOf(lDesc));
		request.setAttribute("nPageNo",String.valueOf(pageNo));
		
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (Exception ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>