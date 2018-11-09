<%
/**
 * 页面名称 ：q013-c.jsp
 * 页面功能 : 查询贷款申请信息和计划信息，转到执行计划主维护界面
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
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
		
		long  loanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();          //流水号
    	LoanApplyInfo applyInfo=null;	
    	String tmp="";
    	long pageNo=1;
    	long orderParam=99;
    	long lDesc=Constant.PageControl.CODE_ASCORDESC_ASC;
    	String action="";
    

		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			pageNo=Long.valueOf(tmp).longValue();
						
		OBLoanQuery lla = new OBLoanQuery(); 
				
		/*查询申请信息*/
		LoanApplyInfo laInfo=lla.findByID(loanID);		
		
		long  pageLine=Constant.PageControl.CODE_PAGELINECOUNT;
    	long  planCount=laInfo.getPlanDetailCount();
    	long  pageCount=planCount / pageLine;
    	if ( (planCount%pageLine)!=0 )	pageCount++;
    	
		/*查询计划信息*/
		if ( pageNo<=0 ) pageNo=1;
		if ( orderParam<=0 ) orderParam=99;
		if ( lDesc<=0 ) lDesc=Constant.PageControl.CODE_ASCORDESC_ASC;
		if ( pageNo>pageCount) pageNo=1;
		
		Vector c=(Vector)lla.findPlanByLoanID(loanID,pageNo,Constant.PageControl.CODE_PAGELINECOUNT,orderParam,lDesc);
		
		request.setAttribute("LoanApplyInfo",laInfo);
		request.setAttribute("Collection",c);
		request.setAttribute("nOrderParam",String.valueOf(orderParam));
		request.setAttribute("nDesc",String.valueOf(lDesc));
		request.setAttribute("nPageNo",String.valueOf(pageNo));
	
		String strURL="/loan/query/q014-v.jsp";
		
		
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>