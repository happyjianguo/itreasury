<%--
 页面名称 ：consignReceiveStart.jsp
 页面功能 : 委托收款发起控制页面
 作    者 ：xlchang
 日    期 ：2010-11-29
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.bizlogic.ConsignReceiveBiz" %>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo" %>

<%@ page import="com.iss.system.dao.PageLoader"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%    
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	String strTitle = "[委托收款确认]";
	String strContext = request.getContextPath();

    try
	{
	
        //请求检测
		// 用户登录检测
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
			out.flush();
			return;
		} 
				
		//定义变量	
		ConsignReceiveInfo info = null;
		ConsignReceiveBiz biz = new ConsignReceiveBiz();
		
		long nOfficeID = sessionMng.m_lOfficeID;
		long nCurrencyID = sessionMng.m_lCurrencyID;
		long nClientID = sessionMng.m_lClientID;
		long nUserID = sessionMng.m_lUserID;

        //读取数据		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
				       
       ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
       tempInfo.convertRequestToDataEntity(request);
       
       tempInfo.setNOfficeID(nOfficeID);
       tempInfo.setNCurrencyID(nCurrencyID);
       
       String strStatus =  OBConstant.SettInstrStatus.SUBMIT + "," + OBConstant.SettInstrStatus.CONFIRM + ","
	    			+ OBConstant.SettInstrStatus.REFUSE;
       
       
        //查询条件
       	String strTemp = null;	
       	String q_payeeClientCodeStart = ""; //查询条件-收款方 由
		String q_payeeClientCodeEnd = ""; //查询条件-收款方 至
		strTemp = (String)request.getAttribute("q_payeeClientIDStartCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    q_payeeClientCodeStart = strTemp;
		}
		strTemp = (String)request.getAttribute("q_payeeClientIDEndCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    q_payeeClientCodeEnd = strTemp;
		}
		
       //缺省查询对象
		ConsignReceiveInfo defQueryInfo = new ConsignReceiveInfo();
		defQueryInfo.setNOfficeID(nOfficeID);
		defQueryInfo.setNCurrencyID(nCurrencyID);
		defQueryInfo.setNPayerClientID(nClientID);  
		defQueryInfo.setStrStatus(strStatus);  
		if (tempInfo.getQ_NStatus() > 0) {	
    		defQueryInfo.setNStatus(tempInfo.getQ_NStatus());
    	}     	
    	defQueryInfo.setQ_inputStart(tempInfo.getQ_inputStart());
    	defQueryInfo.setQ_inputEnd(tempInfo.getQ_inputEnd());
    	defQueryInfo.setQ_payeeClientIDStart(tempInfo.getQ_payeeClientIDStart());
    	defQueryInfo.setQ_payeeClientIDEnd(tempInfo.getQ_payeeClientIDEnd());
    	defQueryInfo.setQ_payeeClientCodeStart(q_payeeClientCodeStart);
    	defQueryInfo.setQ_payeeClientCodeEnd(q_payeeClientCodeEnd);    	     	

        //根据请求操作，完成业务处理的调用
         //根据请求操作，完成业务处理的调用
       	
        if(String.valueOf(OBConstant.Actions.MATCHSEARCH).equals(strAction)){ 
               
			//Collection c = biz.confirmDefFindByCondition(tempInfo,nClientID);  
        	//request.setAttribute("results",c);
        	
        	//strActionResult = Constant.ActionResult.SUCCESS;  
        	//strSuccessPageURL = strContext+"/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp";  
        	
        	
        	PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);						
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
        	
        	strActionResult = Constant.ActionResult.SUCCESS;        
        	        	
        } else if(String.valueOf(OBConstant.Actions.TODETAIL).equals(strAction)){    
            
        	ConsignReceiveInfo result = biz.findByID(tempInfo.getId());
        	request.setAttribute("result",result);
        	
        	strActionResult = Constant.ActionResult.SUCCESS;
        	
        	
        } else if(String.valueOf(OBConstant.Actions.CREATESAVE).equals(strAction)){
        
        	info = new ConsignReceiveInfo();
        	tempInfo.setNOfficeID(nOfficeID);
        	tempInfo.setNCurrencyID(nCurrencyID);
        	tempInfo.setNConfirmUserID(nUserID);
        	FinanceInfo fInfo = biz.createFinanceInfo(tempInfo);
        	        	
        	strSuccessPageURL = "/capital/financeinstr/view/fi_v001.jsp";
        	request.setAttribute("financeInfo",fInfo);
        	request.setAttribute("isConsignReceive", "true");
        	strActionResult = Constant.ActionResult.SUCCESS;        	
        	
        } else if(String.valueOf(OBConstant.Actions.DELETE).equals(strAction)){
        
        	info = new ConsignReceiveInfo();
        	info.setId(tempInfo.getId());
        	info.setNStatus(OBConstant.SettInstrStatus.REFUSE);
        	info.setNConfirmUserID(nUserID);
        	info.setDTConfirm(Env.getSystemDate());
        	info.setSMemo(tempInfo.getSMemo());
        	biz.update(info);
        	
        	//成功后返回查询页面         	
        	//Collection c = biz.confirmDefFindByCondition(tempInfo,nClientID);  
        	      	
        	//request.setAttribute("results",c);
        	strActionResult = Constant.ActionResult.SUCCESS;
        	
        	//成功后返回查询页面 
        	PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);						
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
        	
        }
        if (strNextPageURL.equals("")) {
        	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        } 
        
        RequestDispatcher rd = null;
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPageURL);
		//分发
		rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));		
		    
		/* forward到结果页面 */
	    rd.forward(request, response);
	
	} catch(IException ie){
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}	
	
	
%>