<%
/**
 * 页面名称 ：S613.jsp
 * 页面功能 : 贷款展期申请处理-新增
 * 作    者 ：方远明
 * 日    期 ：2003-10-23
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S614.jsp			  
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
	com.iss.itreasury.ebank.obrepayplan.bizlogic.*,
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
		String strcontrol = "";//控制动作
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		long lLoanType = -1;
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

		long lInterestType = -1;
		long lLiborRateID = -1;
		String sLiborName = "";
		boolean libor = false;
		double dRateAdjust = 0;
		String sAdjust1 = "";
		String sAdjust2 = "";
		long lBankRateTypeID = -1;

		long nLength = 0;
		long lExtendListID = -1;
		long lPlanID = -1;

		long lStatusID = -1;

		String strLiborName = "";
		float fLiborAdjustRate = 0;
		long lLiborID = 0;
        
		OBSecurityInfo secinfo = new OBSecurityInfo();
		
		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
		{
			strcontrol = (String)request.getAttribute("control");
		}
		
		if( (String)request.getAttribute("first") != null)
		{
			strFirst = (String)request.getAttribute("first");
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) 
		{
        	sAction = strTmp;
		}

		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply e_ejb = extendApplyHome.create();

		// 判断(展期:1转期:2－新增:1审核:2修改:3)--------------------begin (lEType-lAType)
		lInterestType = LOANConstant.InterestRateType.BANK;
		
		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) 
		{
			lAType = Long.valueOf(strTmp).longValue();
		}
		if (lAType == 1) 
		{
				strTmp = (String)request.getAttribute("lContractID");
				if ( strTmp != null && strTmp.length() > 0 ) 
				{
					lContractID = Long.valueOf(strTmp).longValue();
				} 
				else 
				{
					out.println("出错：没传合同标识.");
					strcontrol = "";
				}
		}
		if (lAType == 2 || lAType == 3) 
		{
				strTmp = (String)request.getAttribute("lExtendID");
				if ( strTmp != null && strTmp.length() > 0 ) 
				{
					lExtendID = Long.valueOf(strTmp).longValue();
				} 
				else 
				{
					out.println("出错：没传展期标识.");
					strcontrol = "";
				}
		}
		
				String[] slID = request.getParameterValues("checkbox");
				if (slID != null || slID.length > 0) 
				{
					nLength = slID.length;
				}

				double dbalance = 0;  // 用来判断是否为计划余额
				strTmp = (String)request.getAttribute("balance"); 
				if (strTmp != null && strTmp.length() >0) 
				{
					dbalance = Double.valueOf(strTmp).doubleValue();
				}

				// 2
				strTmp = ((String)request.getAttribute("extype")).trim();
				if (strTmp != null && strTmp.length() > 0) 
				{
					lEType = Long.valueOf(strTmp).longValue();
				}  
				else 
				{
					out.println("出错：没有指定是展期或转期");
					strcontrol = "";
				}
				
				ContractInfo c_info = e_ejb.findExtendByID(-1,lContractID,secinfo).getC_Info();
				System.out.println("hehrhehrehrhehrhehrhehrherhehrhh========="+c_info);
				
				//复杂的一块!!!
	            // String[] -> long[]
                long l = -1;
				long[] lID = new long[slID.length];
				for (int i=0; i<slID.length; i++) 
				{
					l = Long.valueOf(slID[i]).longValue();
					lID[i] = l;
				}
               	OBRepayPlanHome repayPlanHome = (OBRepayPlanHome)EJBObject.getEJBHome("OBRepayPlanHome");
               	OBRepayPlan r_ejb = repayPlanHome.create();
               	OBRepayPlanInfo r_info[] = new OBRepayPlanInfo[(int)nLength];  //  页面显示用
				for(int i =0;i<nLength;i++)
				{
					r_info[i] = new OBRepayPlanInfo();
				}
				secinfo.setContractID(lContractID);
				secinfo.setOfficeID(sessionMng.m_lOfficeID);
				for (int i=0; i<nLength; i++) 
				{
                	System.out.println("jsp-----------------------lID[i]: " + lID[i]);
                	r_info[i] = r_ejb.findPlanDetailByID(lID[i],secinfo);
				}
				
		request.setAttribute("r_info",r_info);
		request.setAttribute("c_info",c_info);
		//
		request.setAttribute("control",strcontrol);
		request.setAttribute("first",strFirst);
		request.setAttribute("txtAction",sAction);
		request.setAttribute("attribtype",lAType+"");
		request.setAttribute("lContractID",lContractID+"");
		request.setAttribute("lExtendID",lExtendID+"");
		request.setAttribute("checkbox",slID);
		request.setAttribute("balance",dbalance+"");
		request.setAttribute("extype",lEType+"");
		//request.setAttribute("nlength",
		
		/* 获取上下文环境*/
		ServletContext sc = getServletContext();
		/* 设置返回地址 */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e007-v.jsp")));
		/* forward到结果页面 */
		rd.forward(request, response);
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"展期查找(计划)",1);
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>