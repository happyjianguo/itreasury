<%
/**
 * 页面名称 ：l016-c.jsp
 * 页面功能 : 从保证人选择页面返回到保证资料主维护页面
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
	com.iss.itreasury.ebank.obloanapply.dao.OBLoanApplyDao,
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
	
		String tmp="";
		long loanID=-1;
		
		tmp=(String)request.getAttribute("lLoanID");		//流水号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
		
		
		
		//OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApplyDao lla = new OBLoanApplyDao();
		
		long lOrderParam=-1;
		long lDesc=-1;
		
		tmp=(String)request.getAttribute("lOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lOrderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();
			
		OBPageInfo obPInfo = new OBPageInfo();
		obPInfo.setDesc(lDesc);
		obPInfo.setOrderParam(lOrderParam);	
		request.setAttribute("OBPageInfo",obPInfo);
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);			
		
		OBLoanApplyInfo laInfo=null;
		laInfo=	lla.findByID(loanID,obPInfo);	
		request.setAttribute("LoanApplyInfo",laInfo);
		System.out.println(loanID+"%%%%%%%%%%%%%%%%%%%%%%"+laInfo.getSubTypeId()+"isPledge==="+laInfo.getIsPledge());
		String strURL="/loan/loanapply/l010-v.jsp";
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