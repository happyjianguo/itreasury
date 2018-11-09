<%--
/*
 * 程序名称：v004.jsp
 * 功能说明：网银用款调整页
 * 作　　者：liangpan
 * 完成日期：2007-07-18
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%String strContext = request.getContextPath();%>
<%
/**
 * 页面控制类
 */
     String strTitle = "用款新增"; 
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
OBBudgetInfo info = null;
OBBudgetInfo originalInfo = null;

try {
	 /** 权限检查 **/
    System.out.println("=================进入页面../control/c004.jsp=========");

     // 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

	 
	pageInfo.convertRequestToDataEntity(request);
	//System.out.println("barney@@@@@@"+pageInfo);

	 
	info = new OBBudgetInfo();
	 
	info.convertRequestToDataEntity(request);	 
 	
	OBBudgetBiz biz = new OBBudgetBiz();

	
	String strTemp = "";
	int lAction = -1;
	strTemp = request.getParameter("strAction");	
	if(strTemp!=null && !strTemp.equals("")){
		lAction = Integer.parseInt(strTemp);
	}
	String tempTransCode = "";			
	
	List list=null ;
	long lId=-1;
	lId=info.getId();
	if(lId<0){	
		strTemp = request.getParameter("lID");	
		if(strTemp!=null && !strTemp.equals("")){
			lId = Long.parseLong(strTemp);
		}else{
			strTemp = request.getParameter("id");	
			if(strTemp!=null && !strTemp.equals("")){
				lId = Long.parseLong(strTemp);
			}			
		}
	}		
	if(lId>0){
		
		originalInfo = biz.findByID(lId);
		originalInfo.setAdjustNote(info.getAdjustNote());
		originalInfo.setAdjustdate(Env.getCurrentSystemDate());
		list = biz.findAllSubRecords(lId);
		request.setAttribute("info", originalInfo);
		request.setAttribute("subInfoList", list);		
	}	

if(lAction==OBConstant.SettInstrStatus.SAVE || lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL){
	List listAmounts = new ArrayList();
	
	for(int i=0;i<list.size();i++){
		strTemp = (String)request.getAttribute("amount"+i);
		if(strTemp != null && !strTemp.equals("")){	
			listAmounts.add(strTemp);
		}	
	}	
	
	OBBudgetDelegation delegation = new OBBudgetDelegation();
	//Added by zwsun, 2007/7/18, 审批流
	if(lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL){
		String surl = strContext + "/obbudget/control/c004.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp010.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID=";			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETADJUST);
		originalInfo.setInutParameterInfo(pInfo);
	}		 
	long isModified = biz.checkModifyDate(info);
	if(isModified == OBConstant.TRUE){
		sessionMng.getActionMessages().addMessage("该记录已被修改，操作无效");
		pageInfo.setStrNextPageURL("../view/v004.jsp");
	}else{		 
		long retlong = delegation.saveAdjust(originalInfo,list,listAmounts);
		if(retlong > 0){
			pageInfo.success();
			pageInfo.setStrNextPageURL("../view/v004.jsp?ID="+retlong);
		}else{
			pageInfo.fail();
		}
	}
}
//审批
else if(lAction == OBConstant.SettInstrStatus.DOAPPRVOAL){
		String surl = strContext + "/obbudget/control/c004.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp010.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID="+lId;			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETADJUST);
		info.setInutParameterInfo(pInfo);
		OBBudgetDelegation obBudgetDelegation = new  OBBudgetDelegation();
		if(obBudgetDelegation.doApproval(info)>0){
			pageInfo.success();
			sessionMng.getActionMessages().addMessage("审批成功");
		}else{
			pageInfo.fail();
			sessionMng.getActionMessages().addMessage("审批失败");
		}	
}
//取消审批
else if(lAction == OBConstant.SettInstrStatus.CANCELAPPRVOAL){
		String surl = strContext + "/obbudget/control/c004.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp010.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID="+lId;			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETADJUST);
		info.setInutParameterInfo(pInfo);
		OBBudgetDelegation obBudgetDelegation = new  OBBudgetDelegation();
		if(obBudgetDelegation.cancelApproval(info)>0){
			pageInfo.success();
			sessionMng.getActionMessages().addMessage("取消审批成功");
		}else{
			pageInfo.fail();
			sessionMng.getActionMessages().addMessage("取消审批失败");
		}		
}	
else{
//默认进入显示页面
	if(lId > 0){
		if(info!=null){
			pageInfo.success();
		}else{
			pageInfo.fail();
		}
	}else{
		pageInfo.fail();
	}
} 
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	
	 
	System.out.println("Next Page URL:"+pageInfo.getStrNextPageURL());	

	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

	rd.forward( request,response );
	
	 }
	catch (IException ie)
	{	
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
%>
