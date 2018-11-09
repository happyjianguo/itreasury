<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：网银预算新增页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
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
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.OBBudgetDelegation"%>
<%@ page import="com.iss.itreasury.common.attach.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
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
     String strTitle = "预算新增"; 
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
OBBudgetInfo info = new OBBudgetInfo();

try {
	 /** 权限检查 **/
    System.out.println("=================进入页面../control/c001.jsp=========");

    
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

	long period = -1;
	String strTemp = "";
	String startDate = "";
	strTemp = (String)request.getAttribute("period");
	if(strTemp != null && !strTemp.equals("")){
		period = Long.parseLong(strTemp);
	}
	
	strTemp = (String)request.getParameter("startDate");
	if(strTemp != null && !strTemp.equals("")){
		startDate = strTemp;
	}
	
	//Added by zwsun, 2007/7/18, 审批流
	//lAction 默认为-1时进入显示页面
	int lAction = -1;
	strTemp = request.getParameter("strAction");	
	if(strTemp!=null && !strTemp.equals("")){
		lAction = Integer.parseInt(strTemp);
	}
	long lId=-1;
	String tempTransCode = "";	
	strTemp = request.getParameter("lID");	
	if(strTemp!=null && !strTemp.equals("")){
		lId = Long.parseLong(strTemp);
	}else{
		strTemp = request.getParameter("id");	
		if(strTemp!=null && !strTemp.equals("")){
			lId = Long.parseLong(strTemp);
		}			
	}
	strTemp = (String)request.getAttribute("strTransTypeNo");	 	
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		   tempTransCode = strTemp;
	}		
	//Timestamp endDate = nul;
	//endDate.setTime(DataFormat.getDateTime(startDate).getTime()+period*24*3600*1000-1);
	OBBudgetBiz biz = new OBBudgetBiz();
	if(lId>0){
		info = biz.findByID(lId);
		List list = biz.findAllSubRecords(lId);
		request.setAttribute("info", info);
		request.setAttribute("subInfoList", list);		
	}
if(lAction==OBConstant.SettInstrStatus.SAVE || lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL){
	List listDates = new ArrayList();
	List listAmounts = new ArrayList();
	for(int i=0;i<period;i++){
		listDates.add(startDate);
		Timestamp date = DataFormat.getDateTime(startDate);
		date.setTime(date.getTime()+24*3600*1000);
		startDate = DataFormat.getDateString(date);
		strTemp = (String)request.getAttribute("amount"+i);
		if(strTemp != null && !strTemp.equals("")){	
			listAmounts.add(strTemp);
		}
		System.out.println(listDates.get(i)+">>>>>>>>>>>"+listAmounts.get(i));		
	}	 
	
	info.convertRequestToDataEntity(request);
	OBBudgetDelegation obBudgetDelegation = new  OBBudgetDelegation();
	long retlong = -1; 
	
	//Added by zwsun, 2007/7/18, 审批流
	if(lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL){
		String surl = strContext + "/obbudget/control/c001.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp009.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID=";			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETNEW);
		info.setInutParameterInfo(pInfo);
	}	
	retlong = obBudgetDelegation.saveAll(info,listDates,listAmounts);
	if(retlong > 0){
		if(tempTransCode != null && !tempTransCode.equals(""))
		{
			//数据访问对象
			AttachOperation attachOperation = new AttachOperation();
			attachOperation.updateOBTransCode(tempTransCode,tempTransCode);
		}			
		//sessionMng.getActionMessages().addMessage("保存成功!");
		 //pageInfo.setStrNextPageURL("../view/v001.jsp");
		System.out.println("============================22");
		pageInfo.success();
		pageInfo.setStrNextPageURL("../view/v001.jsp?ID="+retlong);
	}else{
		pageInfo.fail();
	}
}
//审批
else if(lAction == OBConstant.SettInstrStatus.DOAPPRVOAL){
		String surl = strContext + "/obbudget/control/c001.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp009.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID="+lId;			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETNEW);
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
		String surl = strContext + "/obbudget/control/c001.jsp?"
						+"strSuccessPageURL="+ strContext +"/obbudget/view/vApp009.jsp"
						+"&strFailPageURL="+ strContext +"/approval/view/v033.jsp"
						+"&lID="+lId;			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.BUDGETNEW);
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
		/*
		List list = biz.findAllSubRecords(lId);
		request.setAttribute("info", info);
		request.setAttribute("subInfoList", list);
		*/
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
