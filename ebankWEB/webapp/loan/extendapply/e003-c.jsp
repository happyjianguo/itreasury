<%
/**
 * 页面名称 ：e001-v.jsp
 * 页面功能 : 网上银行展期申请处理-新增
 * 作    者 ：杨帆
 * 日    期 ：2004-01-11
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : e003-c.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>			

<%
	try
	{
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
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

		String sApplyCode1 = "";
		String sApplyCode2 = "";
		String sBorrowClientName = "";
		long lBorrowClientID = -1;
		long lLoanApplyID1 = -1;
		long lLoanApplyID2 = -1;
		long lLoanIntervalNum = -1;
		long lLoanTypeID = -1;
		String strTmp = "";
		String sTitle = "网银";

		// ---------------------------------------------------------------------------------end
//adding by 杨帆!
		OBQueryContractInfo qinfo = new OBQueryContractInfo();
		//第一次进行查询，取得查询条件
		if (GetNumParam(request,"nPageNo",-1) == -1 )
		{
			qinfo.setUserID(sessionMng.m_lUserID);
			qinfo.setCurrencyID(sessionMng.m_lCurrencyID);
			qinfo.setOfficeID(sessionMng.m_lOfficeID);
			qinfo.setClientID(sessionMng.m_lClientID);

			qinfo.setTypeID(GetNumParam(request,"txtlLoanTypeID",0));
			qinfo.setContractIDFrom(GetNumParam(request,"txtIDStart",0));
			qinfo.setContractIDTo(GetNumParam(request,"txtIDEnd",0));
			//qinfo.setClientID(GetNumParam(request,"txtsBorrowClientName",0));
			qinfo.setPageLineCount(Constant.PageControl.CODE_PAGELINECOUNT);
			qinfo.setPageNo(GetNumParam(request,"txtPageNo",0));
			qinfo.setOrderParam(GetNumParam(request,"lOrderParam",0));
			qinfo.setDesc(GetNumParam(request,"lDesc",0));
			
			qinfo.setContractNameFrom(GetParam(request,"txtIDStartCtrl",""));
			qinfo.setContractNameTo(GetParam(request,"txtIDEndCtrl",""));
			qinfo.setClientName(GetParam(request,"txtsBorrowClientNameCtrl",""));
			
			
			/*qInfo.setStartAmount(GetDoubleParam(request,"txtAmountFrom",0));
			qInfo.setEndAmount(GetDoubleParam(request,"txtAmountTo",0));
			qInfo.setStartDate(GetTSParam(request, "txtLoanStart", ""));
			qInfo.setEndDate(GetTSParam(request, "txtLoanEnd", ""));
			qInfo.setIntervalNum(GetNumParam(request,"txtIntervalNum",0));
			qInfo.setStatusID(GetIntParam(request,"selStatusID",0));*/
			session.setAttribute("qinfo",qinfo);
		}
		else//如不是第一次查询，从session中取得查询条件
		{
			qinfo = (OBQueryContractInfo)session.getAttribute("qinfo");
			qinfo.setPageLineCount(Constant.PageControl.CODE_PAGELINECOUNT);
			qinfo.setPageNo(GetNumParam(request,"txtPageNo",1));
			qinfo.setOrderParam(GetNumParam(request,"lOrderParam",1));
			qinfo.setDesc(GetNumParam(request,"lDesc",Constant.PageControl.CODE_ASCORDESC_ASC));
		}
		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

// ------------------------------------------------- lLoanTypeID

     	// 初始化EJB
		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply ejb = extendApplyHome.create();

		Collection c = null;
		c = ejb.findContractByMultiOption(qinfo);
		System.out.println("descdescdescdescdescdescdescdescdesc isisisisisisiisisisisi=="+qinfo.getDesc());
		request.setAttribute("info",c);
		request.setAttribute("qinfo",qinfo);      //保存查询条件
		request.setAttribute("firstFlag","false");//设置第一次查询标志
		
		
		/* 获取上下文环境*/
		ServletContext sc = getServletContext();
		/* 设置返回地址 */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e001-v.jsp")));
		/* forward到结果页面 */
		rd.forward(request, response);

	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>