<%
/**
 * 页面名称 ：S614.jsp
 * 页面功能 : 贷款展期申请处理-新增
 * 作    者 ：方远明
 * 日    期 ：2003-10-23
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S611.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>

<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>		

<%
	try
	{
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		long lModuleID = Constant.ModuleType.LOAN;
		//模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER ;
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		
    	// 定义变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		String strTmp = "";
		String sTitle = "一般贷款";
		String sAction = "";
		String sEType = "展期";

        String sAmount = "";
		double dAmount = 0;
		double dBalance = 0;
		Timestamp dtExtendStartDate = null;
		Timestamp dtInputDate = null;
		double dRate = 0;
		String sReason = "",sSource = "",sOther = "";
		long nNum = -1;
		String sLiborName = "";
		boolean libor = false;
		long nLength = 0;
		long lBankRateTypeID = 0;

		OBSecurityInfo secinfo = new OBSecurityInfo();
		
		long lInterestType = -1;
		long lLiborRateID  = -1;
		double dRateAdjust = 0.0;
		
//----------------------------------------------do add------------------------------------------------------------
			
		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply e_ejb = extendApplyHome.create();
		

		OBRepayPlanInfo rp_info = new OBRepayPlanInfo();  // 新增提交后保存展期用 or action = save
		ArrayList alist = new ArrayList();

//--------------------------------------------do add end----------------------------------------------------------
		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) 
		{
			lExtendID = Long.valueOf(strTmp).longValue();
		} 
		
		//从指令查询过来的参数
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");

		//cancel操作
		long lResult = e_ejb.updateStatus(lExtendID,OBConstant.LoanInstrStatus.SUBMIT,secinfo);
		
		OBExtendApplyInfo e_info = e_ejb.findExtendByID(lExtendID,-1,secinfo);

		response.sendRedirect("../extendapply/e017-v.jsp?control=view&txtSearch="+txtSearch+"&lID="+lExtendID+"&strCode="+e_info.getInstructionNo());

		//lLoanTypeID = e_info.getLoanTypeID();


		//RepayPlanInfo r_info = new RepayPlanInfo();  //  页面显示用
		
		//request.setAttribute("c_info",c_info);
		//request.setAttribute("e_info",e_info);
		//request.setAttribute("attribtype",lAType+"");
		//request.setAttribute("txtAction",sAction);
		//request.setAttribute("control",strcontrol);
		//request.setAttribute("first",strFirst);
		//request.setAttribute("lExtendID",lExtendID+"");
		//request.setAttribute("lContractID",e_info.getContractID()+"");
		
		/* 获取上下文环境*/
		//ServletContext sc = getServletContext();
		/* 设置返回地址 */
		//RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e017-v.jsp")));
		/* forward到结果页面 */
		//rd.forward(request, response);
		
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>