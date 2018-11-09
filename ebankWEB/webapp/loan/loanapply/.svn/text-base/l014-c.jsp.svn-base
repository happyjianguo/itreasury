<%
/**
 * 页面名称 ：l014-c.jsp
 * 页面功能 : 查询一个客户的基本信息
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
	
		ClientInfo cInfo=null;
		long cID=-1;
		boolean isnew=false;
		String tmp="";
		
		if ( ((String)request.getAttribute("isnew")).equals("new") )
		{
			isnew=true;
		}
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();
		
		/*如果是新增客户，就增加一条客户的基本信息*/
		/*如果是新增客户，就增加一条客户的基本信息*/		
		if ( isnew )
		{
			String clientName="";
			String licenceCode="";
			long officeID=sessionMng.m_lOfficeID;
		
			tmp=(String)request.getAttribute("nclientname");
			if ( (tmp!=null)&&(tmp.length()>0) )
				clientName=tmp;
						
			tmp=(String)request.getAttribute("nlicencecode");
			if ( (tmp!=null)&&(tmp.length()>0) )
				licenceCode=tmp;
							
			tmp=(String)request.getAttribute("nofficeid");
			
			if ( (tmp!=null)&&(tmp.length()>0) )
				officeID=Long.valueOf(tmp).longValue();
											
			cInfo=new ClientInfo();
			cInfo.setClientID(0);
			cInfo.setName(clientName);
			cInfo.setLicenceCode(licenceCode);
			cInfo.setOfficeID(officeID);
			cInfo.setInputUserID(sessionMng.m_lUserID);
			
			cID=lcs.saveClientInfo(cInfo);
		}
		else										//老客户，只获得客户ID
		{
			tmp=(String)request.getAttribute("oclientid");
			if ( (tmp!=null)&&(tmp.length()>0) )
				cID=Long.valueOf(tmp).longValue();
		}
		
		cInfo=lcs.findClientByID(cID);
	
		String strURL="/loan/loanapply/l015-v.jsp";
		
		request.setAttribute("ClientInfo",cInfo);	
		
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