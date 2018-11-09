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
		long lPlanID = -1;
		long lExtendListID = -1;
		
//----------------------------------------------do add------------------------------------------------------------
			
			OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
			OBExtendApply e_ejb = extendApplyHome.create();
			
			strTmp = ((String)request.getAttribute("nlength")).trim();
			if (strTmp != null && strTmp.length() > 0) 
			{
				nLength = Long.valueOf(strTmp).longValue();
			} 
		
			strTmp = (String)request.getAttribute("txtExtReason");
			if ( strTmp != null && strTmp.length() >0) 
			{
				sReason = strTmp;
			}
			
			strTmp = (String)request.getAttribute("txtExtSource");
			if ( strTmp != null && strTmp.length() >0) 
			{
				sSource = strTmp;
			}
					
			strTmp = (String)request.getAttribute("txtExtOther");
			if ( strTmp != null && strTmp.length() >0) 
			{
				sOther = strTmp;
			}


			strTmp = (String)request.getAttribute("txtlRateValue");
			if ( strTmp != null && strTmp.length() >0) 
			{
					dRate = Double.valueOf(DataFormat.reverseFormatAmount(strTmp)).doubleValue();
			}
			
			strTmp = (String)request.getAttribute("txtlRate");
			if ( strTmp != null && strTmp.length() >0) 
			{
				lBankRateTypeID = Long.valueOf(strTmp).longValue();
			}

  // action = modi
			ArrayList alist = new ArrayList();
			
			for (int j=0; j<nLength; j++) 
			{
				OBRepayPlanInfo rp_info = new OBRepayPlanInfo();
System.out.println("!!!!!!!=");
				strTmp = (String)request.getAttribute("txtdBalance"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					dBalance = Double.valueOf(DataFormat.reverseFormatAmount(strTmp)).doubleValue();
				}
System.out.println("!!!!!!!="+dBalance);
				strTmp = (String)request.getAttribute("dAmount"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					dAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTmp)).doubleValue();
					sAmount = DataFormat.formatListAmount(dAmount);
				}
System.out.println("!!!!!!!="+dAmount);
				strTmp = (String)request.getAttribute("txtExtendStartDate"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					dtExtendStartDate = DataFormat.getDateTime(strTmp);
				}
System.out.println("!!!!!!!="+dtExtendStartDate);
				strTmp = (String)request.getAttribute("txtdtInputDate"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					dtInputDate = DataFormat.getDateTime(strTmp);
				}
System.out.println("!!!!!!!="+dtInputDate);
				strTmp = (String)request.getAttribute("txtNum"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					nNum = Long.valueOf(strTmp).longValue();
				}
				System.out.println("jsp----------save: " + dBalance + "--" + dAmount + "--" + dtExtendStartDate + "--" + dtInputDate + "--" + nNum);
System.out.println("!!!!!!!="+nNum );
				strTmp = (String)request.getAttribute("planid"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					lPlanID = Long.valueOf(strTmp).longValue();
				}
System.out.println("!!!!!!!="+lPlanID);
				strTmp = (String)request.getAttribute("listid"+j);
				if ( strTmp != null && strTmp.length() >0) 
				{
					lExtendListID = Long.valueOf(strTmp).longValue();
				}
System.out.println("!!!!!!!="+lExtendListID);
				//rp_info = new RepayPlanInfo();
				rp_info.setPlanDate(dtInputDate);
				rp_info.setExecuteInterestRate(dRate);
				rp_info.setAmount(dAmount);
				rp_info.setPlanBalance(dBalance);
				rp_info.setExtendStartDate(dtExtendStartDate);
				rp_info.setExtendEndDate(dtInputDate);
				rp_info.setExtendPeriod(nNum);
				rp_info.setID(lPlanID);
				rp_info.setExtendListID(lExtendListID);
				alist.add(rp_info);
			}
			
			strTmp = (String)request.getAttribute("lContractID");
			if ( strTmp != null && strTmp.length() > 0 ) 
			{
				lContractID = Long.valueOf(strTmp).longValue();
			} 
			strTmp = (String)request.getAttribute("lExtendID");
			if ( strTmp != null && strTmp.length() > 0 ) 
			{
				lExtendID = Long.valueOf(strTmp).longValue();
			} 

			OBExtendApplyInfo qinfo = new OBExtendApplyInfo();
			qinfo.setID(lExtendID);
			qinfo.setContractID(lContractID);
			qinfo.setPlanVersionID(0);
			qinfo.setExtendList(alist);
			qinfo.setExtendTypeID(lEType);
			qinfo.setInterestTypeID(lInterestType);
			qinfo.setBankRateTypeID(lBankRateTypeID);
			qinfo.setExtendRate(dRate);
			qinfo.setLiborRateID(lLiborRateID);
			qinfo.setAdjustValue(dRateAdjust);
			qinfo.setExtendReason(sReason);
			qinfo.setReturnPostPend(sSource);
			qinfo.setOtherContent(sOther);
			qinfo.setUserID(sessionMng.m_lUserID);
			
			long lResult = e_ejb.saveExtendApply(qinfo);

//--------------------------------------------do add end----------------------------------------------------------
		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		//因为合并到一个页面,所以这里可以省略
		//lExtendID = lResult;
		/*strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lExtendID = Long.valueOf(strTmp).longValue();
		} else {
			out.println("出错：没传展期标识.");
			strcontrol = "";
		}*/

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

		//RepayPlanInfo r_info = new RepayPlanInfo();  //  页面显示用
		
		request.setAttribute("c_info",c_info);
		request.setAttribute("e_info",e_info);
		request.setAttribute("attribtype",lAType+"");
		request.setAttribute("txtAction",sAction);
		request.setAttribute("control",strcontrol);
		request.setAttribute("first",strFirst);
		request.setAttribute("lExtendID",lExtendID+"");
		request.setAttribute("lContractID",e_info.getContractID()+"");
		
		/* 获取上下文环境*/
		ServletContext sc = getServletContext();
		/* 设置返回地址 */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e009-v.jsp")));
		/* forward到结果页面 */
		rd.forward(request, response);
		
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>