<%
/**
 * 页面名称 ：l028-c.jsp
 * 页面功能 : 提交贷款申请，并查询相关信息
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
	com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
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
	long        loanID=-1;          //贷款申请流水号
	long		userID=-1;
	
	String tmp="";
    long pageNo=1;
    long orderParam=99;
    long lDesc=Constant.PageControl.CODE_ASCORDESC_DESC;

	
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
		
		String action="";
		tmp=(String)request.getAttribute("txtAction");
		if (tmp!=null)
			action=tmp;
			
		loanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();
		userID=sessionMng.m_lUserID;
		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		if ( (tmp!=null)&&(tmp.length()>0) )
			pageNo=Long.valueOf(tmp).longValue();
					
		String strURL="/loan/loanapply/l037-v.jsp?lLoanID="+loanID
			+"&nOrderParam="+orderParam
			+"&nDesc="+lDesc
			+"&nPageNo="+pageNo;

		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
				
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		
		OBSystemHome home2 = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home2.create();		
		
		
		/*查询申请信息*/
		OBLoanApplyInfo laInfo=lla.findByID(loanID,sInfo);	
		
		OBPageInfo pageInfo=new OBPageInfo();
		pageInfo.setPageNo(pageNo);
		pageInfo.setOrderParam(orderParam);
		pageInfo.setDesc(lDesc);
			
		//查询计划信息
		Collection c=lla.findPlanByLoanID(loanID,pageInfo,sInfo);
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