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
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>

<jsp:directive.page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.util.OBConstant"/>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%
/**
 * 页面控制类
 */
     String strTitle = "预算新增"; 
	 PageCtrlInfo pageInfo = new PageCtrlInfo();

	/** 定义业务实体类 */
	OBBankPayInfo info = new OBBankPayInfo();
	OBFinanceInstrHome financeInstrHome = null;
	OBFinanceInstr financeInstr = null;
	financeInstrHome = (OBFinanceInstrHome) EJBObject.getEJBHome("OBFinanceInstrHome");
	financeInstr = financeInstrHome.create();
			

try {
	 /** 权限检查 **/
    System.out.println("=================进入页面../control/c001.jsp=========");
    
    OBHtml.validateRequest(out,request,response);
	 
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
	//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
	if(lId>0){
		info=financeInstr.findByID(lId);
		info.setNconfirmuserid(sessionMng.m_lUserID);
		info.setNclientid(sessionMng.m_lClientID);
		info.setNcurrencyid(sessionMng.m_lCurrencyID);
		info.setLOfficeID(sessionMng.m_lOfficeID);

	}

	String strFailPageURL1 = request.getParameter("strFailPageURL");

	//审批
	if(lAction == OBConstant.SettInstrStatus.DOAPPRVOAL){
				
		String surl= strContext + "/bankpay/view/c004_v.jsp?strFailPageURL="+strFailPageURL1+"&&txtID="+lId;	
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.ONLINEBANK_BANKPAY);
		info.setInutParameterInfo(pInfo);
		//OBBudgetDelegation obBudgetDelegation = new  OBBudgetDelegation();
		if(financeInstr.doApproval(info)>0){
			pageInfo.success();
			sessionMng.getActionMessages().addMessage("审批成功");
		}else{
			pageInfo.fail();
			sessionMng.getActionMessages().addMessage("审批失败");
		}	
	}
	//取消审批

	else if(lAction == OBConstant.SettInstrStatus.CANCELAPPRVOAL){
	String surl= strContext + "/bankpay/view/c004_v.jsp?strFailPageURL="+strFailPageURL1+"&&txtID="+lId;			
		InutParameterInfo pInfo = new InutParameterInfo();
		pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		pInfo.setRequest(request);
		pInfo.setSessionMng(sessionMng);
		pInfo.setUrl(surl);
		pInfo.setTransTypeID(OBConstant.SettInstrType.ONLINEBANK_BANKPAY);
		info.setInutParameterInfo(pInfo);
		//OBBudgetDelegation obBudgetDelegation = new  OBBudgetDelegation();
		if(financeInstr.cancelApproval(info)>0){
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
