<%
/**
 * 页面名称 ：l033-c.jsp
 * 页面功能 : 取消贷款申请
 * 作    者 ：gump
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.ebank.obloanapply.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
	long        loanID=-1;          //流水号
	long		loanType=-1;
	
		
	/* 用户登录检测与权限校验及文件头显示 */
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
	
		String tmp="";
		long 		userID=sessionMng.m_lUserID;
		
		tmp=(String)request.getAttribute("lLoanID");
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lLoanType");
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanType=Long.valueOf(tmp).longValue();	
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);		
		
		long ret=lla.cancel(loanID,sInfo);
		String strURL="";
		tmp=(String)request.getAttribute("txtAction");
	
		if ( (tmp!=null)&&(tmp.length()>0) )		//返回查询页面
		{
			strURL="/loan/query/q003-c.jsp";		
		}
		else
		{
			if ( loanType==OBConstant.LoanInstrType.LOAN_WT )
			{
				strURL="/loan/loanapply/l002-c.jsp";
			}
			else
			{
				strURL="/loan/loanapply/l002-c.jsp";
			}
		}
		request.setAttribute("type",String.valueOf(loanType));
		request.setAttribute("txtAction","");
		
		
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