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

<%@ page import="com.iss.system.dao.PageLoader"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%    
	//页面控制变量	
	String strAction = null;
    String strNextPageURL = "";   
    String strContext = request.getContextPath();    
	String strSuccessPageURL = strContext + "/project/wisgfc/special/view/consignReceiveStartQuery.jsp";
	String strFailPageURL = strContext +  "/project/wisgfc/special/view/consignReceiveStartQuery.jsp";	
	String strActionResult = Constant.ActionResult.FAIL;	
	String strTitle = "[委托收款发起]";
	
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
		
		String strTemp = "";
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

		//读取数据		
		strAction = (String)request.getAttribute("strAction");
		     
		ConsignReceiveInfo tempInfo = new ConsignReceiveInfo();
		tempInfo.convertRequestToDataEntity(request);
		 
		tempInfo.setNOfficeID(nOfficeID);
		tempInfo.setNCurrencyID(nCurrencyID);
		
		//缺省查询对象
		ConsignReceiveInfo defQueryInfo = new ConsignReceiveInfo();
		defQueryInfo.setNOfficeID(nOfficeID);
		defQueryInfo.setNCurrencyID(nCurrencyID);
		defQueryInfo.setNPayeeClientID(nClientID);  
		if (tempInfo.getQ_NStatus() > 0) {	
    		defQueryInfo.setNStatus(tempInfo.getQ_NStatus());
    	}             	
		  
		//根据请求操作，完成业务处理的调用       	
		if (String.valueOf(OBConstant.Actions.CREATESAVE).equals(strAction)){

			info = new ConsignReceiveInfo();	
			info.setNOfficeID(nOfficeID);
			info.setNCurrencyID(nCurrencyID);
			info.setNTransType(OBConstant.SettInstrType.BANK_CONSIGNRECEIVESTART);
			info.setNPayerClientID(tempInfo.getNPayerClientID());
			info.setNPayerAcctID(tempInfo.getNPayerAcctID());
			info.setNPayeeClientID(tempInfo.getNPayeeClientID());
			info.setNPayeeAcctID(tempInfo.getNPayeeAcctID());
			info.setMAmount(tempInfo.getMAmount());
			info.setNAbstractID(tempInfo.getNAbstractID());
			info.setSContractCode(tempInfo.getSContractCode());			
			info.setNStatus(OBConstant.SettInstrStatus.SAVE);	
			info.setNInputUserID(sessionMng.m_lUserID);
			info.setDTInput(Env.getSystemDate());
			info.setSMemo(tempInfo.getSMemo());
			request.setAttribute("result",info);          //用于出错时返回原录入信息
			biz.add(info);
			
			strActionResult = Constant.ActionResult.SUCCESS;
			sessionMng.getActionMessages().addMessage("保存成功");	
						
			//成功后返回查询页面	
			PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;	    
			
        } else if(String.valueOf(OBConstant.Actions.MODIFYSAVE).equals(strAction)){
        
			info = new ConsignReceiveInfo(); 
			info.setId(tempInfo.getId());       	
			info.setNPayerClientID(tempInfo.getNPayerClientID());
			info.setNPayerAcctID(tempInfo.getNPayerAcctID());
			info.setNPayeeClientID(tempInfo.getNPayeeClientID());
			info.setNPayeeAcctID(tempInfo.getNPayeeAcctID());
			info.setMAmount(tempInfo.getMAmount());
			info.setNAbstractID(tempInfo.getNAbstractID());
			info.setSContractCode(tempInfo.getSContractCode());
       		info.setDTModify(Env.getSystemDate());
       		info.setSMemo(tempInfo.getSMemo());
       		request.setAttribute("result",info);          //用于出错时返回原录入信息		
       		biz.update(info);		
			
			strActionResult = Constant.ActionResult.SUCCESS;
			sessionMng.getActionMessages().addMessage("保存成功");	
			
			//成功后返回查询页面	
			PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);						
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;				
			
		} else if(String.valueOf(OBConstant.Actions.SAVEANDINITAPPROVAL).equals(strAction)){
		
        	info = new ConsignReceiveInfo();
        	 //公共信息（可修改的信息）      	
			info.setNPayerClientID(tempInfo.getNPayerClientID());
			info.setNPayerAcctID(tempInfo.getNPayerAcctID());
			info.setNPayeeClientID(tempInfo.getNPayeeClientID());
			info.setNPayeeAcctID(tempInfo.getNPayeeAcctID());
			info.setMAmount(tempInfo.getMAmount());
			info.setNAbstractID(tempInfo.getNAbstractID());
			info.setSContractCode(tempInfo.getSContractCode());	
			info.setSMemo(tempInfo.getSMemo());
			info.setNStatus(OBConstant.SettInstrStatus.SUBMIT);	
			info.setDTInput(Env.getSystemDate());			
			request.setAttribute("result",info);          //用于出错时返回原录入信息
			
        	if (tempInfo.getId() > 0) {
        		info.setId(tempInfo.getId());        	
        		info.setDTModify(Env.getSystemDate());
        		biz.update(info);
        	} else {   
        		info.setNOfficeID(nOfficeID);
				info.setNCurrencyID(nCurrencyID);
				info.setNTransType(OBConstant.SettInstrType.BANK_CONSIGNRECEIVESTART);     	
				info.setNInputUserID(sessionMng.m_lUserID);					
				biz.add(info);			
        	}    
        	
        	strActionResult = Constant.ActionResult.SUCCESS;			    	
       		sessionMng.getActionMessages().addMessage("提交成功");	
        	
        	//成功后返回查询页面
			PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);	
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;       	    
       		       	
        } else if(String.valueOf(OBConstant.Actions.DELETE).equals(strAction)){
        
        	info = new ConsignReceiveInfo();
        	info.setId(tempInfo.getId());
        	info.setNStatus(OBConstant.SettInstrStatus.DELETE);
        	biz.update(info);
        	
        	strActionResult = Constant.ActionResult.SUCCESS;
        	
        	//成功后返回查询页面 
        	PageLoader pageLoader = biz.queryConsignReceiveInfo(defQueryInfo);
        	String strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
        	sessionMng.setQueryCondition(strPageLoaderKey,tempInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);						
			strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
			        	
        } else if(String.valueOf(OBConstant.Actions.LINKSEARCH).equals(strAction)){
        
        	strActionResult = Constant.ActionResult.SUCCESS;
        	
        } else if(String.valueOf(OBConstant.Actions.MATCHSEARCH).equals(strAction)){   
           
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
        	
        } else if("toAdd".equals(strAction)){		
			strActionResult = Constant.ActionResult.SUCCESS;
		}
        
        if (strNextPageURL.equals("")) {
        	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        }        
               
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPageURL);		
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    
		/* forward到结果页面 */
	    rd.forward(request, response);
	    
	} catch(IException ie){
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}		
%>