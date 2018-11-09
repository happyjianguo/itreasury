<%--
 页面名称 ：c011.jsp
 页面功能 : 审批流设置查询--控制页面
 作    者 ：gyzhao
 日    期 ：2005年05月09日
 特殊说明 ：实现操作说明：
				1、查找
				2、返回
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>	
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.Log" %>

<%@ page import="com.iss.itreasury.ebank.util.*" %>	
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.ApprovalSettingBiz" %>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.*" %>	

<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<%	
	//页面控制类
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	try
	{
		String strTitle = "审批流设置";
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
		
		//从request中获得页面控制信息
		pageInfo.convertRequestToDataEntity(request);
		
		ApprovalQueryInfo queryInfo = new ApprovalQueryInfo();
		queryInfo.convertRequestToDataEntity(request);
        
		ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
		Collection c = null;
		ApprovalSettingInfo relustInfo = null;
		
		long lResult = -1;
		long lApprovalID = -1;
		String strTmp = (String)request.getAttribute("lApprovalID");
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
		
		if(pageInfo.getStrAction().equalsIgnoreCase("toModify"))
		{	
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toBack"))
		{
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toDelete"))
		{
			AppBiz.deleteApprovalSetting(lApprovalID);
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toValid"))
		{
			AppBiz.changeApprovalSetting(lApprovalID);
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toUpdate"))
		{
			//long lApprovalID = -1;	//审批设置标示
    		long lTotalLevel = 0;	//审批总级别
			long lLowLevel = 0;		//最小审批级别
   			long lIsPass = 0;		//是否允许越级
			String strApproveName = "";	//审批流名称
    		String strApproveDesc = "";	//审批流描述
    	
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

			// 获取lTotalLevel
			strTmp = (String)request.getAttribute("lTotalLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lTotalLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lTotalLevel = -1;
				}
			}
		
			// 获取lLowLevel
			strTmp = (String)request.getAttribute("lLowLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lLowLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lLowLevel = -1;
				}
			}

			// 获取lIsPass
			strTmp = (String)request.getAttribute("lIsPass");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lIsPass = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lIsPass = -1;
				}
			}
			// 获取approveName
			strTmp = (String)request.getAttribute("strApproveName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveName = strTmp;
				}
				catch(Exception e)
				{
					strApproveName = "";
				}
			}

			// 获取approveDesc
			strTmp = (String)request.getAttribute("strApproveDesc");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveDesc = strTmp;
				}
				catch(Exception e)
				{
					strApproveDesc = "";
				}
			}
			ApprovalSettingInfo info = new ApprovalSettingInfo();
			info.setID(lApprovalID);
			info.setName(strApproveName);
			info.setDesc(strApproveDesc);
			info.setTotalLevel(lTotalLevel);
			info.setLowLevel(lLowLevel);
			info.setIsPass(lIsPass);

		String strErrorMessage ="";
		lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lResult == 1)
		{
			System.out.println("======1=111===========ret="+lResult);
			//获得错误信息
			strErrorMessage = "审批设置有未完成审批的业务，不允许保存！";
			request.setAttribute("Error", strErrorMessage);
			System.out.println("=======1===========strErrorMessage="+strErrorMessage);
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
%>
					<script language="JavaScript">
						alert("<%=strErrorMessage%>");
						eval("history.back(-1)");
					</script>
<%
			pageInfo.fail();
			
		}
		else
		{
			System.out.println("======2============ret="+lResult);
			System.out.println("======2============strErrorMessage="+strErrorMessage);
			long lTemp = AppBiz.saveApprovalSetting(info);
			if(lTemp == -2)
				sessionMng.getActionMessages().addMessage("存在相同名称的审批流,不能保存!");
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
			pageInfo.success();

		}
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toDispatch"))
		{
			//查询是否有未审批完成的业务
			lResult = AppBiz.checkApprovalTracing(lApprovalID);		
			if(lResult==1)          //如果是转到后一页，并且有未审批完的业务，则传标识isOver为2
			{
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("isOver",new String("2"));				
			}
			else
			{
				request.setAttribute("isOver",new String("1"));
			}
			request.setAttribute("fromPage","c011");
			pageInfo.setStrSuccessPageURL("../control/c002.jsp?control=view");
			pageInfo.success();
		}
		else
		{	
			sessionMng.setQueryCondition("queryInfo",queryInfo);
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//出现异常,操作结果置为失败	
		pageInfo.fail();
	}	
	if(pageInfo.getStrActionResult().equals(Constant.ActionResult.SUCCESS))
	{
		request.setAttribute("strActionResult",pageInfo.getStrActionResult());	
		Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
				//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURLs(pageControllerInfo));
		rd.forward( request,response );
	}
	else
	{
%>
					<script language="JavaScript">
						alert("审批设置有未完成审批的业务，不允许保存！");
						eval("history.back(-1)");
					</script>
<%
	}
%>