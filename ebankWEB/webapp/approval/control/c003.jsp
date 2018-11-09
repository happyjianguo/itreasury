<%--
/*
 * 程序名称：c003.jsp
 * 功能说明：保存审批设置控制页面
 */
--%>

<!--Header start-->
<%
/* 设置页面属性、session、引入的程序包 */
%>
<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.approval.bizlogic.*,
				com.iss.itreasury.ebank.approval.dataentity.*,
                java.util.Collection,
				java.sql.Timestamp"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%   
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
	Log.print("---------进入 c003.jsp 审批用户权限设置控制页面---------");
	
	
	/* 标题固定变量 */
	String strTitle = "审批设置"; 
%>

<%	
    //从Session中取得必需的参数
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//设置参数
	ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
	String strTmp = "";
    long lModuleID = -1;	//模块标示
	long lLoanTypeID = -1;	//类型标示
	long lActionID = -1;	//操作标示
	long lApprovalID = -1;	//审批设置标示
    long lTotalLevel = 0;	//审批总级别
    long lIsPass = 0;		//是否允许越级
	long lIsReduplicateAssign = 0;	//是否允许各级别重复分配人员
	long lIsReduplicateCheck = 0;	//是否允许同一人员重复审批
	long lStatusID = 1;		//当前审批状态：1、设置中；2、设置完成
	long lLevel = -1;		//当前审批级别
	long lResult = -1;
	long lIsOver = 1;       //判断是否全部都审批完1：是。2：否
	String strErrorMessage ="";
	/* 用户登录检测与权限校验及文件头显示 */
    try {
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
        
		//获取表单数据，设置currentTransInfo属性
		Log.print("获取查询数据");

		// 获取lApprovalID
		strTmp = (String)request.getAttribute("lApprovalID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lApprovalID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lApprovalID = -1;
			}
		}

		// 获取lType
		strTmp = (String)request.getAttribute("isOver");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lIsOver = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lIsOver = -1;
			}
		}
        
        //根据获取的参数，组织分配
        String strURL = "";
		ApprovalSettingInfo ASInfo = null;
		lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lResult == 1)
		{
			//获得错误信息
			strErrorMessage = "审批设置有未完成审批的业务，不允许激活！";
			request.setAttribute("Error", strErrorMessage);
			
		}
		else
		{
			lResult = AppBiz.checkApprovalValidity(lApprovalID);
			if (lResult == 2)
			{
				//获得错误信息
				strErrorMessage = "审批设置没有全部设置完成，不允许激活！";
				request.setAttribute("Error", strErrorMessage);
				
			}
			else if (lResult == 3)
			{
				//获得错误信息
				strErrorMessage = "不允许各级别重复分配人员，请检查！";
				request.setAttribute("Error", strErrorMessage);
				
			}
			else
			{
				lResult = AppBiz.activeApprovalSetting(lApprovalID);
				if (lResult < 0)
				{
					//获得错误信息
					strErrorMessage = "设置激活不成功！";
					request.setAttribute("Error", strErrorMessage);
				}
			}
		}
		//审批设置标示
		request.setAttribute("lApprovalID",Long.toString(lApprovalID));
		//设置是否有未完成的审批
		request.setAttribute("isOver",Long.toString(lIsOver));
		
		if(request.getAttribute("fromPage") != null)
		{
			request.setAttribute("fromPage",(String)request.getAttribute("fromPage"));
		}
		
        strURL = "c002.jsp?control=view";
        
		/* 获取上下文环境 */
		Log.print("获取上下文环境");
	    //ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		Log.print("设置返回地址");
		
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		/* forward到结果页面 */
		Log.print("forward到结果页面");
	    rd.forward(request, response);
		return;
		
    } catch (Exception e) {
		Log.print(e.toString());
    }
%>