<%
/**
 * 页面名称 ：l034-c.jsp
 * 页面功能 : 进入修改贷款申请各部分资料（客户资料、基本资料、贷款性质、保证资料、）的修改页面
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 转入页面 : l004-v.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,
			com.iss.itreasury.ebank.util.*,
			com.iss.itreasury.ebank.obloanapply.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.bizlogic.*,
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.ebank.obdataentity.*,
			com.iss.itreasury.loan.util.*,
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	String tmp;
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
    	
		boolean	backward=false;
    	boolean iswt=false;
    	boolean isyt=false;
    	String action=(String)request.getAttribute("txtAction");
    	String statusID=(String)request.getAttribute("statusID");
    	String mType=(String)request.getAttribute("mType");
		String strURL="";
		ClientInfo cInfo=null;
		OBLoanApplyInfo appInfo=null;
		long loanID=-1;		
		OBLoanApplyHome home2 = null;
		OBLoanApply lla = null;
		Collection planInfo=null;

	    long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
				
		if ( mType.equals("client") )
		{
			long cID=-1;
	
			tmp=(String)request.getAttribute("lClientID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				cID=Long.valueOf(tmp).longValue();
			OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
			OBSystem lcs = home.create();
			cInfo=lcs.findClientByID(cID);
			strURL="/loan/loanapply/l004-v.jsp?type="+type+"&wtClientID=-1&txtAction="+action+"&nStatusID="+statusID;
			request.setAttribute("ClientInfo",cInfo);	
		}
		else if ( mType.equals("wtclient") )
		{
			long cID=-1;
			String clientName="";
			tmp=(String)request.getAttribute("lClientID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				cID=Long.valueOf(tmp).longValue();
			OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
			OBSystem lcs = home.create();
			cInfo=lcs.findClientByID(cID);
			clientName=cInfo.getName();
			//strURL="/loan/loanapply/l001-v.jsp?type="+type+"&wtClientID="+cID+"&wtClientName="+clientName+"&txtAction="+action+"&nStatusID="+statusID;
			request.setAttribute("wtClientName",clientName);
			strURL="/loan/loanapply/l001-v.jsp?type="+type+"&wtClientID="+cID+"&txtAction="+action+"&nStatusID="+statusID;
		}

		else if (mType.equals("loanBasic"))	
		{
			home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
			lla = home2.create();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID,sInfo);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/loanapply/l006-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;

		}
		else if ( mType.equals("loanProperty") )
		{
			home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
			lla = home2.create();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID,sInfo);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/loanapply/l008-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;
		
		}
		else if ( mType.equals("assure"))
		{
			home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
			lla = home2.create();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID,sInfo);
			request.setAttribute("LoanApplyInfo",appInfo);		
			strURL="/loan/loanapply/l010-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;		
		}
		else if (mType.equals("plan"))
		{
			home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
			lla = home2.create();
			tmp=(String)request.getAttribute("lLoanID");
			if ( (tmp!=null)&&(tmp.length()>0) )
				loanID=Long.valueOf(tmp).longValue();
			
			appInfo=lla.findByID(loanID,sInfo);
			request.setAttribute("LoanApplyInfo",appInfo);		
			long pageNo=1;
			long orderParam=99;
			long lDesc=1;
		
			OBPageInfo pageInfo=new OBPageInfo();
			pageInfo.setPageNo(pageNo);
			pageInfo.setOrderParam(orderParam);
			pageInfo.setDesc(lDesc);
		
			planInfo=lla.findPlanByLoanID(loanID,pageInfo,sInfo);
			request.setAttribute("Collection",planInfo);		
			
			strURL="/loan/loanapply/l021-v.jsp?lLoanType="+type+"&txtAction="+action+"&nStatusID="+statusID;		
		}
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));

	    rd.forward(request, response);
	
		return;
		
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>