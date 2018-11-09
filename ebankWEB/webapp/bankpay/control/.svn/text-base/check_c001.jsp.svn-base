<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBBankPayInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:directive.page import="com.iss.itreasury.util.PageCtrlInfo"/>
<%
    PageCtrlInfo pageInfo = new PageCtrlInfo();
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;  	
    //动作
    String strAction			="";
	//排序参数
	String orderfield			="";
	String ordertype			="";
    String strContext = request.getContextPath() ;

		long lTransType = -1;       // 交易类型
		long NSTATUS = -1;          // 交易指令状态
        long lContractID = -1;      // 合同ID
        String sStartSubmit = ""; // 提交日期-从
        String sEndSubmit = "";   // 提交日期-到 
        double dMinAmount = 0.00;   // 交易金额-最小值
        double dMaxAmount = 0.00;   // 交易金额-最大值
 
	try {
		
		String strTitle = null;
	  	
	  	//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }	
		String strTemp = "";
		
		//页面控制参数
		strTemp = (String)request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			strAction = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("orderfield");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			orderfield = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("ordertype");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			ordertype = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strSuccessPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					//成功页面
		{				
			strSuccessPageURL = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strFailPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					 //失败页面
		{				
			strFailPageURL = strTemp.trim();
		}
		

		//查询条件获取
        strTemp = (String) request.getParameter("lTransType");
        if(strTemp != null && strTemp.trim().length() > 0) {
            lTransType = Long.parseLong(strTemp); // 交易类型
            Log.print("交易类型=" + lTransType);
        }
        strTemp = (String) request.getParameter("NSTATUS");
        if(strTemp != null && strTemp.trim().length() > 0) {
            NSTATUS = Long.parseLong(strTemp); // 交易指令状态
            Log.print("交易指令状态=" + NSTATUS);
        }
        strTemp = (String) request.getParameter("sStartSubmit");
        if(strTemp != null && strTemp.trim().length() > 0) {
            sStartSubmit = strTemp; // 提交日期-从
            Log.print("提交日期-从=" + sStartSubmit);
        }
        strTemp = (String) request.getParameter("sEndSubmit");
        if(strTemp != null && strTemp.trim().length() > 0) {
            sEndSubmit = strTemp; // 提交日期-到
            Log.print("提交日期-到=" + sEndSubmit);
        }
        strTemp = (String) request.getParameter("dMinAmount");
        if(strTemp != null && strTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最小值
            Log.print("金额最小值=" + dMinAmount);
        }
        strTemp = (String) request.getParameter("dMaxAmount");
        if(strTemp != null && strTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最大值
            Log.print("金额最大值=" + dMaxAmount);
        }
		 /* 初始化查询信息类 */
		pageInfo.convertRequestToDataEntity(request);	
		OBFinanceInstrBiz obfinanceBiz = new OBFinanceInstrBiz();
    	OBBankPayInfo info = new OBBankPayInfo();
    	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		
        info.setNtranstype(lTransType);          // 网上银行交易类型
        info.setNstatus(NSTATUS);                // 交易指令状态
        info.setSStartSubmit(sStartSubmit);    // 提交日期-从
        info.setSEndSubmit(sEndSubmit);        // 提交日期-到
        info.setDMinAmount(dMinAmount);          // 金额最小值
        info.setDMaxAmount(dMaxAmount);          // 金额最大值   
		 /* 从session中获取相应数据 */
        info.setNclientid(sessionMng.m_lClientID);
        info.setNcurrencyid(sessionMng.m_lCurrencyID);
        info.setNconfirmuserid(sessionMng.m_lUserID);
        info.setLOfficeID(sessionMng.m_lOfficeID);
			
	  	PageLoader pageLoader = null;
	  	if(strAction.equals("search")||strPageLoaderKey == null||strPageLoaderKey.equals("null"))
		{
			pageLoader =obfinanceBiz.queryCheckInfo_bankpay(info);
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			sessionMng.setQueryCondition(strPageLoaderKey,info);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			//成功完成设置
		}
		else
		{
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			pageLoader.setOrderBy(" order by "+orderfield+" "+ordertype);
			ordertype=ordertype.equals("asc")?"desc":"asc";
			request.setAttribute("ordertype",ordertype);
			sessionMng.setQueryCondition(strPageLoaderKey,info);
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			//成功完成设置
		}
		
		strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
		pageInfo.setStrNextPageURL(strNextPageURL);
	}
	catch( Exception exp ) 
	{
		exp.printStackTrace();
		strActionResult = Constant.ActionResult.FAIL;
		sessionMng.getActionMessages().addMessage("操作失败！"+exp.getMessage());
	}
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
  	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>