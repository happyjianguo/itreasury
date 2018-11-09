<%
/**
 * 页面名称 ：l018-c.jsp
 * 页面功能 : 修改保证资料时根据保证编号查询保证信息
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 转入页面 : l004-v.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.ebank.util.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.bizlogic.*,
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.ebank.obdataentity.*,
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
    	
		boolean iswt=false;
    	boolean isyt=false;
    
		ClientInfo cInfo=null;
		OBAssureInfo assInfo=null;
	
		long cID=-1;
		boolean isnew=false;
		String tmp="";
		long assureID=-1;
		long assClientID=-1;
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();

		OBLoanApplyHome home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home2.create();
			
		tmp=(String)request.getAttribute("lAssureID");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			assureID=Long.valueOf(tmp).longValue();
		
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
		
				
		assInfo=lla.findAssureByID(assureID,sInfo);
		assClientID=assInfo.getClientID();
		cInfo=lcs.findClientByID(assClientID);	
		
		String strURL="/loan/loanapply/l015-v.jsp";
						
		request.setAttribute("ClientInfo",cInfo);
		request.setAttribute("AssureInfo",assInfo);	
		request.setAttribute("lAssureTypeID",String.valueOf(assInfo.getAssureTypeID()));
		
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