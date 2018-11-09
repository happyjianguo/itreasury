<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo" %>
<%@ page import="com.iss.system.dao.PageLoader"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:directive.page import="com.iss.itreasury.util.PageCtrlInfo"/>
<%
    PageCtrlInfo pageInfo = new PageCtrlInfo();
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
    //动作
    String strAction			="";
	//排序参数
	String orderfield			="";
	String ordertype			="";
    String strContext = request.getContextPath() ;
		long lTransType = -1;       // 网上银行交易类型
		long NSTATUS = -1;          // 交易指令状态
        String sStartSubmit = ""; // 提交日期-从
        String sEndSubmit = "";   // 提交日期-到 
        double dMinAmount = 0.00;   // 交易金额-最小值
        double dMaxAmount = 0.00;   // 交易金额-最大值
        String sStartExe = "";    // 执行日期-从
        String sEndExe = "";      // 执行日期-到
        String sign ="";
 
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
            lTransType = Long.parseLong(strTemp); // 网上银行交易类型
        }
        strTemp = (String) request.getParameter("NSTATUS");
        if(strTemp != null && strTemp.trim().length() > 0) {
            NSTATUS = Long.parseLong(strTemp); // 交易指令状态
        }
        strTemp = (String) request.getParameter("sStartSubmit");
        if(strTemp != null) {
            sStartSubmit = strTemp; // 提交日期-从
        }
        strTemp = (String) request.getParameter("sEndSubmit");
        if(strTemp != null ) {
            sEndSubmit = strTemp; // 提交日期-到
        }
        strTemp = (String) request.getParameter("sStartExe");
        if(strTemp != null) {
            sStartExe = strTemp; // 执行日期-从
        }
        strTemp = (String) request.getParameter("sEndExe");
        if(strTemp != null ) {
            sEndExe = strTemp; // 执行日期-到
        }
        strTemp = (String) request.getParameter("dMinAmount");
        if(strTemp != null && strTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最小值
        }
        strTemp = (String) request.getParameter("dMaxAmount");
        if(strTemp != null && strTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最大值
        }
        
         strTemp = (String) request.getParameter("sign");
        if(strTemp != null && strTemp.trim().length() > 0) {
            sign = strTemp; 
        }
        
        
        
        
		 /* 初始化查询信息类 */
		pageInfo.convertRequestToDataEntity(request);	
		OBFinanceInstrBiz obfinanceBiz = new OBFinanceInstrBiz();
    	Query_FinanceInfo info = new Query_FinanceInfo();
    	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		
        info.setNtranstype(lTransType);          // 网上银行交易类型
        info.setNSTATUS(NSTATUS);                // 交易指令状态
        info.setStartSubmit(sStartSubmit);    // 提交日期-从
        info.setEndSubmit(sEndSubmit);        // 提交日期-到
        info.setStartExe(sStartExe);          // 执行日期-从
        info.setEndExe(sEndExe);              // 执行日期-到
        info.setMinAmount(dMinAmount);          // 金额最小值
        info.setMaxAmount(dMaxAmount);          // 金额最大值    
        info.setSign(sign);                     //标示活期或定期业务（新奥项目新增）
		 /* 从session中获取相应数据 */
        info.setClientID(sessionMng.m_lClientID);
        info.setCurrencyID(sessionMng.m_lCurrencyID);
        info.setNUserID(sessionMng.m_lUserID);
        info.setOfficeID(sessionMng.m_lOfficeID);
			


	  	PageLoader pageLoader = null;
	  	if(strAction.equals("search")||strPageLoaderKey == null||strPageLoaderKey.equals("null"))
		{
			pageLoader =obfinanceBiz.queryCheckInfo(info);
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
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
		sessionMng.getActionMessages().addMessage("操作失败！"+exp.getMessage());
	}
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
  	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>