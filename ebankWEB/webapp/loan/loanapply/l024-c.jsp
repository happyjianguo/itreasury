<%
/**
 * 页面名称 ：l024-c.jsp
 * 页面功能 : 保存计划明细，包括新增和修改
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
	long        loanID=-1;          //贷款申请流水号
	long		rsCount=-1;
	long 		payType=-1;
	long 		planVersion=-1;
	Timestamp	payDate=null;
	double 		payAmount=0;
	String 		type="本金";
	String tmp="";
	Vector		v=new Vector();
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

	
		tmp=(String)request.getAttribute("lRsCount");		//上页的纪录数
		if ( (tmp!=null)&&(tmp.length()>0) )
			rsCount=Long.valueOf(tmp).longValue();	
		
		tmp=(String)request.getAttribute("lPlanVersion");		//计划版本号
		if ( (tmp!=null)&&(tmp.length()>0) )
			planVersion=Long.valueOf(tmp).longValue();	
	
		for ( int i=0;i<rsCount;i++)
		{
			tmp=(String)request.getAttribute("dAmount"+i);		//金额
			if ( (tmp!=null)&&(tmp.length()>0) )
			{
				tmp=DataFormat.reverseFormatAmount(tmp);	
				payAmount=Double.valueOf(tmp).doubleValue();
			}	
			if ( payAmount<=0 )
				continue;
			tmp=(String)request.getAttribute("txtdtInputDate"+i);		//日期
			if ( (tmp!=null)&&(tmp.length()>0) )
				payDate=DataFormat.getDateTime(tmp);
				
			tmp=(String)request.getAttribute("txtlPayRepayType"+i);		//放还款
			if ( (tmp!=null)&&(tmp.length()>0) )
				payType=Long.valueOf(tmp).longValue();	
			
			OBLoanPlanDetailInfo planDetail=new OBLoanPlanDetailInfo();
			tmp=(String)request.getAttribute("lDetailID"+i);		//放还款
			if ( (tmp!=null)&&(tmp.length()>0) )
				planDetail.setID(Long.valueOf(tmp).longValue());
			else
				planDetail.setID(-1);
			planDetail.setPlanID(planVersion);
			planDetail.setPlanDate(payDate);
			planDetail.setPayTypeID(payType);
			planDetail.setAmount(payAmount);
			planDetail.setType(type);
			payAmount=0;
			
			v.add(planDetail);    

		}
					
		String strURL="/loan/loanapply/l020-c.jsp";
		
		OBLoanApplyHome home = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home.create();
		
		lla.savePlan(v);
		

	    //request.setAttribute("LoanApplyInfo",laInfo);
		
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