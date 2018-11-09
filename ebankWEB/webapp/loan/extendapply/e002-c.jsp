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
<%String strContext = request.getContextPath();%>
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

		OBSecurityInfo secinfo = new OBSecurityInfo();

		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lExtendID = Long.valueOf(strTmp).longValue();
		} else {
			out.println("出错：没传展期标识.");
			strcontrol = "";
		}

		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply e_ejb = extendApplyHome.create();
		OBExtendApplyInfo e_info = e_ejb.findExtendByID(lExtendID,-1,secinfo);
		
		lLoanTypeID = e_info.getLoanTypeID();
		
		//ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
		//Contract c_ejb = contractHome.create();
		
        //ContractInfo c_info = c_ejb.findByID(e_info.m_lContractID);
		ContractInfo c_info = e_ejb.findExtendByID(-1,e_info.getContractID(),secinfo).getC_Info();

		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) {
			lAType = Long.valueOf(strTmp).longValue();
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  sAction = strTmp;
		}

		//从指令查询过来的参数
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");
			
		//配合指令查询过来的参数,如果为"yes",则表示查询后并修改过
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

		//RepayPlanInfo r_info = new RepayPlanInfo();  //  页面显示用
		
		request.setAttribute("c_info",c_info);
		request.setAttribute("e_info",e_info);
		request.setAttribute("attribtype",lAType+"");
		request.setAttribute("txtAction",sAction);
		request.setAttribute("control",strcontrol);
		request.setAttribute("first",strFirst);
		request.setAttribute("lExtendID",lExtendID+"");
		request.setAttribute("lContractID",e_info.getContractID()+"");
		request.setAttribute("txtSearch",txtSearch);
		
		/* 获取上下文环境*/
		ServletContext sc = getServletContext();
		/* 设置返回地址 */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e013-v.jsp")));
//新增,复杂

			//如果是指令提交过来的页面,并且登录人不是撰写人,则只能看
			if(txtSearch.equals("modify"))
			{
				if(e_info.getInputUserID()==sessionMng.m_lUserID&&!txtChanged.equals("yes")&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.ACCEPT&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.REFUSE&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.CANCEL&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.APPROVE)
				{
					System.out.println("inininiininini="+txtChanged);
					
					//构建页面分发时需要用到的实体
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo.setUrl(strContext + "/loan/extendapply/e013-v.jsp");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					//request.setAttribute("lID", lID+"");
					request.setAttribute("txtChanged","yes");
				}else
				{
					//保存结束
					//构建页面分发时需要用到的实体
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo.setUrl(strContext + "/loan/extendapply/e009-v.jsp");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				}
			}

		/* forward到结果页面 */
		rd.forward(request, response);
		
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>