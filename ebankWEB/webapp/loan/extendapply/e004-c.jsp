<%
/**
 * 页面名称 ：e004-c.jsp
 * 页面功能 : 网银展期申请处理-新增-计划查找
 * 作    者 ：杨帆
 * 日    期 ：2003-04-12
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : e005-v.jsp			  
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

    	// 定义变量
		long lPageCount = 1;
		long lPageNo = 1;
		long lOrderParam = 1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		long lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;//临时传输变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";

		long lContractID = -1;
		long lLoanTypeID = -1;
		String strTmp = "";
		String sTitle = "贷款";
		String sDisabled = "";
		
		OBSecurityInfo secinfo = new OBSecurityInfo();

		// ---------------------------------------------------------------------------------end

		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		strTmp = (String)request.getAttribute("lContractID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lContractID = Long.valueOf((String)request.getAttribute("lContractID")).longValue();
		} else {
			out.println("没传合同标识");
			strcontrol = "";
		}

		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply ejb = extendApplyHome.create();

//  -------------------------------------------------------------  检查条件1
        String sMessage = "";
System.out.println("lCOntractID====="+lContractID);
		Collection c = null;
        if ( lContractID > 0 ) 
		{
			  System.out.println("here");
			  c = ejb.findPlanByContract(lContractID,secinfo);
	    }
		if (c == null) 
		{
			System.out.println("该笔合同执行计划正在修改当中，无法进行操作！");
			sMessage = "该笔合同执行计划正在修改当中，无法进行操作！";
		}

//      是否在展期	  
		long lStatusID = -1;
		strTmp = (String)request.getAttribute("lStatusID");
		if ( strTmp != null && strTmp.length() > 0 ) 
		{
              lStatusID = Long.valueOf(strTmp).longValue();
		}
		if (lStatusID == 2) 
		{
			 System.out.println("该笔合同执行计划正在修改当中，无法进行操作！");
			 sMessage = "该笔合同正在展期处理中，无法进行操作！";
		}

		if (!sMessage.equals("")) 
		{
			out.println("<script language=\"JavaScript\">\n" ); 
			out.println("<!--\n" ); 
			out.println("alert(\"" + sMessage + "\");\n" ); 
			out.println("self.location='../extendapply/e001-v.jsp?control=view'" ); 
			out.println("//-->\n" ); 
			out.println("</script>\n" ); 
		}else{
//  -------------------------------------------------------------  检查条件2	

     	// 初始化EJB

		//ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
		//Contract c_ejb = contractHome.create();
		//ContractInfo c_info = c_ejb.findByID(lContractID);
		ContractInfo c_info = new ContractInfo();
		OBExtendApplyInfo o_info = new OBExtendApplyInfo();
		System.out.println("lContractID========8============="+lContractID);
		o_info = ejb.findExtendByID(-1,lContractID,secinfo);
		System.out.println("lContractID========8============="+lContractID);
		c_info = o_info.getC_Info();
		//c_info = ejb.findExtendByID(-1,lContractID,secinfo).getC_Info();
		System.out.println("jehrhehrhehrh==="+c_info);
		
		
		request.setAttribute("c_info",c_info);
		request.setAttribute("c",c);
		request.setAttribute("lContractID",lContractID+"");
		//request.setAttribute("qinfo",qinfo);      //保存查询条件
		//request.setAttribute("firstFlag","false");//设置第一次查询标志
		
		/* 获取上下文环境*/
		ServletContext sc = getServletContext();
		/* 设置返回地址 */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e005-v.jsp")));
		/* forward到结果页面 */
		rd.forward(request, response);
		}
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>
