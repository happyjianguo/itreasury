<%
/**
 * 页面名称 ：l025-c.jsp
 * 页面功能 : 删除计划信息
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 *			  
 * 传入参数 ：type			贷款类型  
 *			  
 *
 * 传出参数 ：type			贷款类型
 * 			  LoanApplyInfo 贷款基本申请信息
 * 转入页面 : ../view/v1008.jsp			  
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
	String action="";
		
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
		long rsCount=-1;
		long todel[]=null;
		
		tmp=(String)request.getAttribute("lRsCount");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			rsCount=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lLoanID");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
			
		tmp=(String)request.getAttribute("txtAction");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			action=tmp;	
			
		todel=new long[(int)rsCount];
			
		for ( int i=0;i<rsCount;i++ )
		{
			tmp=(String)request.getAttribute("txtlID"+i);		//流水号
			if ( (tmp!=null)&&(tmp.length()>0) )
				todel[i]=Long.valueOf(tmp).longValue();
			else
				todel[i]=-1;
			System.out.println("todel"+i+"=="+todel[i]);		
		}
			
		String strURL="/loan/loanapply/l020-c.jsp";
		
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();

		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
				
		lla.deletePlan(todel,sInfo);
		
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