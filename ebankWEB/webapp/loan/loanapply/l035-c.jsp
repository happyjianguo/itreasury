<%
/**
 * 页面名称 ：l035-c.jsp
 * 页面功能 : 委托贷款中选择借款单位后,生成借款申请信息
	  
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
		
		String tmp="";
		String action="";
		long loanID=-1;
		tmp=(String)request.getAttribute("lLoanID");		//贷款申请ID
		if ( (tmp!=null)&&(tmp.length())>0 )
			loanID=Long.valueOf(tmp).longValue();;
		
	    tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
    		
  		
    				
		OBLoanCreateInfo createInfo=new OBLoanCreateInfo();
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		long clientID=Long.valueOf((String)request.getAttribute("wtClientID")).longValue();
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
	
		createInfo.setLoanID(loanID);
		createInfo.setSecurityInfo(sInfo);
		createInfo.setBorrowClientID(clientID);
		
		OBLoanApplyHome home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home2.create();
		long lID=lla.add(createInfo);
		
		if ( (action.equals("modify"))||(action.equals("check")) )
		{
%>
<html>
<body>
	<script language="javascript">
		parent.opener.location.reload();
		window.close();
	</script>
</body>
</html>	
<%			
		}
		else
		{				
			OBLoanApplyInfo lInfo=lla.findByID(loanID,sInfo);	
			request.setAttribute("LoanApplyInfo",lInfo);
		
			String strURL="/loan/loanapply/l006-v.jsp";
		
			/* 获取上下文环境 */
			ServletContext sc = getServletContext();
	    
			/* 设置返回地址 */
			RequestDispatcher rd=null;
			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
			/* forward到结果页面 */
	    	rd.forward(request, response);
			return;
		}
		
     }catch (IException ie) {
     	//System.out.println("nullll");
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
		return; 
	  
     }
%>