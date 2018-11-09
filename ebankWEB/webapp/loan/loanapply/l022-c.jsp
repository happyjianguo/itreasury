<%
/**
 * 页面名称 ：l022-c.jsp
 * 页面功能 : 自动生成计划
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 *			  
 * 转入页面 : 1020-c.jsp
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

		long        loanID=-1;          //贷款申请流水号
		long		payType=-1;			//放款方式
		long 		repayType=-1;		//还款方式
		Timestamp	payStart=null;
		Timestamp	payEnd=null;
		Timestamp	repayStart=null;
		Timestamp	repayEnd=null;
		long		sourceType=-1;
		String tmp="";
		long nOrderParam=-1;
		long nDesc=-1;
		String action="";
	
		
		tmp=(String)request.getAttribute("lLoanID");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nOrderParam");		//排序字段
		if ( (tmp!=null)&&(tmp.length()>0) )
			nOrderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");		//排序方式
		if ( (tmp!=null)&&(tmp.length()>0) )
			nDesc=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("txtlPayType");		//放款方式
		if ( (tmp!=null)&&(tmp.length()>0) )
			payType=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("txtlRepayType");		//还款方式
		if ( (tmp!=null)&&(tmp.length()>0) )
			repayType=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("txtdtInputDate11");		//起始放款日期
		if ( (tmp!=null)&&(tmp.length()>0) )	
			payStart=DataFormat.getDateTime(tmp);
			
		tmp=(String)request.getAttribute("txtdtInputDate12");		//终止放款日期
		if ( (tmp!=null)&&(tmp.length()>0) )	
			payEnd=DataFormat.getDateTime(tmp);
			
		tmp=(String)request.getAttribute("txtdtInputDate21");		//终止还款日期
		if ( (tmp!=null)&&(tmp.length()>0) )	
			repayStart=DataFormat.getDateTime(tmp);
			
		tmp=(String)request.getAttribute("txtdtInputDate22");		//终止还款日期
		if ( (tmp!=null)&&(tmp.length()>0) )	
			repayEnd=DataFormat.getDateTime(tmp);
		
		sourceType=LOANConstant.WhoChangePlan.LOANAPPLY	;	//动作来源	
					
		String strURL="/loan/loanapply/l020-c.jsp";
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		OBAutoPlanInfo apInfo=new OBAutoPlanInfo();
		apInfo.setLLoanID(loanID);
		apInfo.setNPayType(payType);
		apInfo.setNRepayType(repayType);
		apInfo.setTsPayStart(payStart);
		apInfo.setTsPayEnd(payEnd);
		apInfo.setTsRepayStart(repayStart);
		apInfo.setTsRepayEnd(repayEnd);
		apInfo.setNSourceType(sourceType);
		
		long ret=lla.autoAddPlan(apInfo);
		
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