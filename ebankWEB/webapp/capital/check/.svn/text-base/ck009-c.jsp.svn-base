<%--
/*
 * 程序名称：c009-c.jsp
 * 功能说明：复核匹配控制页面
 * 作　　者：苗佳
 * 完成日期：2006年04月10日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "java.sql.*,
				   java.util.*,
                   javax.servlet.*,
				   com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obquery.bizlogic.*,
				   com.iss.itreasury.ebank.obquery.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
%>

<%
	/* 定义对表单的相应变量 */
	long lTransType = -1; //交易类型
	String lPayerAcctID = "";//付款方账户ID
	long lRemitType = 0;// 汇款方式
	long lPayeeAcctID = -1;//收款方账户ID
	double dAmount = 0.0;// 金额
	String tsExecute = null;// 执行日
	String tsClearInterest = null; //结息日
	long lFixedDepositTime = -1; // 定期存款期限（月）
	long lNoticeDay = -1; //通知存款品种（天）
	long lInterestPayeeAcctID = -1;//利息收款方账户ID
	long lInterestRemitType = 0;// 利息汇款方式
	String strDepositNo = "";//定期（通知）存款单据号
	long lSubAccountID = -1; //子账户ID
	long lContractID = -1; //合同ID
	long lLoanNoteID = -1; //放款通知单ID
	double dRealInterest = 0.0; //实付贷款利息
	double dRealCompoundInterest = 0.0; //实付复利
	double dRealOverDueInterest = 0.0; //实付逾期罚息
	double dRealCommission = 0.0; //实付手续费
	double dRealSuretyFee = 0.0; //实付担保费dRealSuretyfee
	String sdePostBillNO = "";//定期存单号

	/* 用户登录检测与权限校验 */
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
%>

<!--Get Data start-->
<%
		/* 从FORM表单中获取相应数据 */
		lTransType = GetNumParam(request,"SelectType"); //交易类型
		
		lPayerAcctID = request.getParameter("nPayerAccountID");//付款方账户ID
		Log.print("付款方账户ID=" + lPayerAcctID);
		
		
		if(request.getParameter("dAmount") != null && request.getParameter("dAmount").length()>0)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dAmount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute =request.getParameter("tsExecute");// 执行日
			Log.print("执行日=" + tsExecute);
		}
		
		
		lFixedDepositTime = GetNumParam(request,"nFixedDepositTime");//定期存款期限
		Log.print("定期存款期限=" + lFixedDepositTime);
		
		//if(request.getParameter("SDEPOSITBILLNO") != null && request.getParameter("SDEPOSITBILLNO").length()>0)
		//{
		//	sdePostBillNO = request.getParameter("SDEPOSITBILLNO");//定期存单号
		//	Log.print("定期存单号=" + sdePostBillNO);
		//}
		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set obQueryInfo Attribute start-->
<%		
		/* 实例化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%		
		
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		financeInfo.setClientID(sessionMng.m_lClientID);
		financeInfo.setUserID(sessionMng.m_lUserID);
		financeInfo.setPayerAcctID(new Long(lPayerAcctID).longValue());
		financeInfo.setAmount(dAmount);
		financeInfo.setExecuteDate(DataFormat.getDateTime(tsExecute));
		financeInfo.setFixedDepositTime(lFixedDepositTime);
		//financeInfo.setSDepositBillNo(sdePostBillNO);
		/*业务调用*/
		ArrayList al = (ArrayList)financeInstr.isTransOpenFixdDePosit(financeInfo);
		Iterator iterator = null;
		if (al != null && !al.isEmpty())
		{
			iterator = al.iterator();
			financeInfo = (FinanceInfo)iterator.next();
		}
%>
<!--Access DB end-->

<!--Forward start-->
<%	
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		/* 设置返回地址 */
	    RequestDispatcher rd = null;
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		if((financeInfo != null) && (financeInfo.getID() >0))
		{	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/capital/fixed/f004-v.jsp?checktype=1");
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			/* forward到结果页面 */
	    	rd.forward(request, response);
		}
		else
		{
%>
			<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%			
		}
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->
