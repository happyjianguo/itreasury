<%@ page language="java" contentType="text/html; charset=GBK"%>

<!--类导入部分开始-->
<%@ page import="java.util.*"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.securities.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.fcinterface.bankportal.bankcode.bizlogic.*"%>
<%@ page import="com.iss.itreasury.fcinterface.bankportal.bankcode.BranchNOIdentify"%>
<%@ page import="com.iss.itreasury.fcinterface.bankportal.bankcode.dataentity.BankCodeParamInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	PageLoader pageLoader = null;
	AreaCodeBiz areaCodeBiz = new AreaCodeBiz();
	BankCodeBiz bankCodeBiz = new BankCodeBiz();
	String city="";
	String strTemp="";
	String keyWord="";
	String province="";
	String bankType="";
	String bankName=""; 
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strContext = request.getContextPath();
	try 
	{
		//获取页面控制信息
		pageInfo.convertRequestToDataEntity(request);
		String strPageLoaderKey = null;
		if (strPageLoaderKey == null) 
		{
			strPageLoaderKey = (String) request.getParameter("_pageLoaderKey");
		}
		
		strTemp = (String)request.getParameter("strSuccessPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					//成功页面
		{				
			strSuccessPageURL = strTemp.trim();
		}
		
		strTemp = (String)request.getParameter("strFailPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					 //失败页面
		{				
			strFailPageURL = strTemp.trim();
		}
		String strAction = (String)request.getParameter("strAction");
		if(strAction!=null && strAction.length()>0){
			pageInfo.setStrAction(strAction);
		}
		String bankNam1111e = (String)request.getParameter("bankName");
		System.out.println("-"+bankNam1111e+"-");
		
		if (strPageLoaderKey == null 
				|| strPageLoaderKey.equals("null")
				|| pageInfo.getStrAction().equals("find"))//第一次查询
		{
			BankCodeParamInfo condition = new BankCodeParamInfo();
		    bankName = request.getParameter("bankName");
		    if(bankName!=null&&bankName.length()>0)
		    {
	   		    condition.setLbankName(bankName);
    	    }
			bankType = java.net.URLDecoder.decode(request.getParameter("bank"),"utf-8");
			if(bankType!=null&&bankType.length()>0)
			{
				condition.setBankTypeName(bankType);
			}
			String recBankCode = java.net.URLDecoder.decode(request.getParameter("recBankCode"),"utf-8");
			if(recBankCode!=null&&recBankCode.length()>0)
			{
				condition.setBankCode(recBankCode);
				request.setAttribute("recBankCode", recBankCode);
			}
			if(request.getParameter("province")!=null&&request.getParameter("province").length()>0)
			{
			 
			 province =java.net.URLDecoder.decode(request.getParameter("province"),"utf-8");
			}
			if(province!=null&&province.length()>0)
			{
				condition.setProvinceName(province);
			}
			if(request.getParameter("city")!=null&&request.getParameter("city").trim().length()>0)
			{
			 city =java.net.URLDecoder.decode(request.getParameter("city"),"utf-8");
			}
			if(city!=null&&city.length()>0)
			{
				condition.setCityName(city);
			}
			keyWord = request.getParameter("keyWord");
			if(keyWord!=null&&keyWord.length()>0)
			{
				condition.setBankName(keyWord);
			}
			
			String oldReceiveBranchName = java.net.URLDecoder.decode(request.getParameter("oldReceiveBranchName"),"utf-8");
			if(oldReceiveBranchName!=null)
			{
				condition.setOldReceiveBranchName(oldReceiveBranchName);
			}
			if (request.getParameter("bankNames") == null) 
			{
				ArrayList bankNames = BranchNOIdentify.getBankNames();
				request.setAttribute("bankNames", bankNames);
			}
			String[] provinceCol = areaCodeBiz.findAreaCodeProvince();
			sessionMng.setQueryCondition("provinceCol", provinceCol);
			sessionMng.setQueryCondition("queryCondition", condition);
			pageLoader = bankCodeBiz.findBankNOByCondition(condition);
			//生成PageLoader Key值
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
		}
		request.setAttribute("_pageLoaderKey", strPageLoaderKey);
		strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
		pageInfo.setStrNextPageURL(strNextPageURL);
	} catch (Exception exp) 
	{
		sessionMng.getActionMessages().addMessage(exp.getMessage());
		exp.printStackTrace();
		pageInfo.fail();

	}
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>
