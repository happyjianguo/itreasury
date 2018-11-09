<%
/**
 * 页面名称 ：q006-c.jsp
 * 页面功能 : 查询客户得基本信息，如果是委托，也查询委托客户信息
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.ebank.util.*,			
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,		
			com.iss.itreasury.loan.loanapply.dataentity.*,
			com.iss.itreasury.loan.loanapply.bizlogic.*,	
			com.iss.itreasury.ebank.obquery.bizlogic.*,
			java.rmi.RemoteException,
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
		String type=(String)request.getAttribute("lLoanType");			//从本页开始type 变成lLoanType
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=LOANConstant.LoanType.getName(loanType);
		boolean	backward=false;
    	boolean iswt=false;
    	boolean isyt=false;
    	String action=(String)request.getAttribute("txtAction");
    	String statusID=(String)request.getAttribute("statusID");
    	String mType=(String)request.getAttribute("mType");
		String strURL="";
		ClientInfo cInfo=null;
		LoanApplyInfo appInfo=null;
		long loanID=-1;		
		Collection planInfo=null;
				String tmp="";
		
		if ( mType.equals("client") )
		{
			long cID=-1;
	
		
			OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
			OBSystem lcs = home.create();
		
			tmp=(String)request.getAttribute("lClientID");
			if ( (tmp!=null)&&(tmp.length()>0) )		
				cID=Long.valueOf(tmp).longValue();
			
			cInfo=lcs.findClientByID(cID);
			strURL="/loan/query/q007-v.jsp";
	
			request.setAttribute("ClientInfo",cInfo);	
		}
		
		else if (mType.equals("loanBasic"))	
		{
			
			OBLoanQuery lla = new OBLoanQuery();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/query/q008-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;

		}
		else if ( mType.equals("loanProperty") )
		{
			OBLoanQuery lla = new OBLoanQuery();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/query/q009-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;
		
		}
		
		else if ( mType.equals("assure"))
		{
			OBLoanQuery lla = new OBLoanQuery();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/query/q010-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;		
		}
		else if (mType.equals("plan"))
		{
			OBLoanQuery lla = new OBLoanQuery();

			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID);
			request.setAttribute("LoanApplyInfo",appInfo);		
			long pageNo=1;
			long orderParam=99;
			long lDesc=1;
			planInfo=lla.findPlanByLoanID(loanID,pageNo,Constant.PageControl.CODE_PAGELINECOUNT,orderParam,lDesc);
			request.setAttribute("Collection",planInfo);		
			
			strURL="/loan/query/q013-c.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;		
		}		
				
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