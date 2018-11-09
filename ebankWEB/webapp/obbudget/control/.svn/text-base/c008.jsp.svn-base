<%--
/*
 * 程序名称：c008.jsp
 * 功能说明：查询
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetAdjustBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.OBBudgetDelegation"%>
<jsp:directive.page import="com.iss.itreasury.ebank.util.OBConstant"/>
<jsp:directive.page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"/>
<jsp:directive.page import="com.iss.itreasury.util.Constant"/>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
 
     String strTitle = "预算查询"; 
     String action = "";
 	int lAction = -1;
	String strTemp = request.getParameter("strAction");	
	if(strTemp!=null && !strTemp.equals("")){
		lAction = Integer.parseInt(strTemp);
	}    
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
OBBudgetInfo info = null;
OBBudgetAdjustInfo adjustinfo = null;

try {
	 /** 权限检查 **/
    System.out.println("=================进入页面../control/c008.jsp=========");


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
	if(request.getParameter("action")!=null)
	{
		 action = (String)request.getParameter("action");
		 System.out.println("barney@@@@@@@@@@@"+action);
	}
	
	 System.out.println("@@!!!!111111111111111111");
	OBBudgetBiz biz = new OBBudgetBiz();
	 System.out.println("@@!!!!111111111111111111");
	OBBudgetAdjustBiz adjustbiz = new OBBudgetAdjustBiz();
	 System.out.println("@@!!!!111111111111111111");
	 
	 if(action.equals("budgetfind"))
	 {
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}

		if(retlong > 0)
		{
			System.out.println("==========budgetfind=========budgetfind=========budgetfind");		
			OBBudgetInfo rInfo = new OBBudgetInfo();
			rInfo = biz.findByID(retlong);
			System.out.println("barney"+rInfo);
			request.setAttribute("rInfo",rInfo);
			pageInfo.success();
	    	pageInfo.setStrNextPageURL("../view/v003.jsp");
		}	 
		System.out.println("Next Page URL:"+pageInfo.getStrNextPageURL());
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		rd.forward( request,response );
	 }
	 else if(action.equals("adjustfind"))
	 { 	
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}

		if(retlong > 0)
		{
			System.out.println("==========adjustfind=========adjustfind=========adjustfind");		
			OBBudgetAdjustInfo rInfo = new OBBudgetAdjustInfo();
			OBBudgetInfo rBudgetInfo = new OBBudgetInfo();
			rInfo = adjustbiz.findByID(retlong);
			rBudgetInfo = biz.findByID(rInfo.getBudgetID());
			System.out.println("barney"+rInfo);
			request.setAttribute("rInfo",rInfo);
			request.setAttribute("rBudgetInfo",rBudgetInfo);
			pageInfo.success();
			pageInfo.setStrNextPageURL("../view/v006.jsp");
		}	
		System.out.println("Next Page URL:"+pageInfo.getStrNextPageURL());	
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward( request,response ); 
	 }
	 else if(action.equals("budgetmodify"))
	 {
	 	strTemp = "";
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}
	 	List list = biz.findAllSubRecords(retlong);
	 	List listAmounts = new ArrayList();
	 	for(int i=0;i<list.size();i++){
			strTemp = (String)request.getAttribute("amount"+i);
			if(strTemp != null && !strTemp.equals("")){	
				listAmounts.add(strTemp);
			}	
		} 	
	 	info = new OBBudgetInfo();
		info.convertRequestToDataEntity(request);

	 	OBBudgetInfo _info = new OBBudgetInfo();
	 	_info = biz.findByID(info.getId());
	 	_info.setSname(info.getSname());
	 	_info.setAccountID(info.getAccountID());
	 	_info.setNote(info.getNote());
		_info.setModifyuser(info.getModifyuser());
		OBBudgetDelegation delegation = new OBBudgetDelegation();

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
		_info.setInutParameterInfo(pInfo);
	}			
		retlong= delegation.updateBudget(_info,list,listAmounts);
		if(retlong>0)
		{
			%><form name="form_c">
				</form>
				 <script type="text/javascript">
				 	if(<%=lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>){
				 		alert("修改并提交审批成功");
				 	}else{
				 		alert("保存成功！");
				 	}
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	if(<%=lAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>){
				 		alert("修改并提交审批成功");
				 	}else{				 
				 		alert("保存失败！");
				 	}
				 	window.close();
				</script>
			<%
		}
		  
	 }
	 else if(action.equals("budgetdelete"))
	 { 	
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}
	 	
		System.out.println("==========budgetdelete=========budgetdelete=========budgetdelete");		 
		retlong = biz.delete(retlong,sessionMng.m_lUserID);
 		if(retlong>0)
		{
			%>
				 <script type="text/javascript">
				 	alert("删除成功！");
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	alert("删除失败！");
				 	window.close();
				</script>
			<%
		}
		 	 
	 }
	 else if(action.equals("budgetcancle"))
	 { 	
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}
		System.out.println("==========budgetcancle=========budgetcancle=========budgetcancle");		 
		retlong = biz.canclecheck(retlong,sessionMng.m_lUserID);
 		if(retlong>0)
		{
			%>
				 <script type="text/javascript">
				 	alert("取消复核成功！");
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	alert("取消复核失败！");
				 	window.close();
				</script>
			<%
		}
  
	 }
	  else if(action.equals("adjustmodify"))
	 { 		
	 	adjustinfo = new OBBudgetAdjustInfo();
		adjustinfo.convertRequestToDataEntity(request);
		System.out.println(adjustinfo);
	 	long retlong = -1;
	 	OBBudgetAdjustInfo _info = new OBBudgetAdjustInfo();
	 	_info.setAmount(adjustinfo.getAmount());
	 	_info.setId(adjustinfo.getId());
	 	_info.setModifydate(Timestamp.valueOf(DataFormat.getStringDateTime()));
	 	_info.setModifyuser(sessionMng.m_lUserID);
	 	_info.setNote(adjustinfo.getNote());
	 	_info.setInputuser(sessionMng.m_lUserID);
		System.out.println("==========adjustmodify=========adjustmodify=========adjustmodify");		
		retlong=adjustbiz.save(_info);
		if(retlong>0)
		{
			%>
				 <script type="text/javascript">
				 	alert("保存成功！");
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	alert("保存失败！");
				 	window.close();
				</script>
			<%
		}
		  
	 }
	 else if(action.equals("adjustdelete"))
	 { 	
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}
	 	
		System.out.println("==========adjustdelete=========adjustdelete=========adjustdelete");		 
		retlong = adjustbiz.delete(retlong,sessionMng.m_lUserID);
 		if(retlong>0)
		{
			%>
				 <script type="text/javascript">
				 	alert("删除成功！");
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	alert("删除失败！");
				 	window.close();
				</script>
			<%
		}
		 	 
	 }
	 else if(action.equals("adjustcancle"))
	 { 	
	 	long retlong = -1;
	 	if(request.getParameter("id")!=null)
	 	{
	 		retlong = Long.parseLong((String)request.getParameter("id"));
	 	}
		System.out.println("==========adjustcancle=========adjustcancle=========adjustcancle");		 
		retlong = adjustbiz.canclecheck(retlong,sessionMng.m_lUserID);
 		if(retlong>0)
		{
			%>
				 <script type="text/javascript">
				 	alert("取消复核成功！");
				 	window.opener.location.reload();
				 	window.close();
				</script>
			<%
		}else
		{
			%>
				 <script type="text/javascript">
				 	alert("取消复核失败！");
				 	window.close();
				</script>
			<%
		}
  
	 }
 
 
	
	
	 }
	catch (IException ie)
	{	
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
%>
