<%--
 页面名称 ：c031.jsp
 页面功能 : 审批流关联设置--控制页面
 作    者 ：gyzhao
 日    期 ：2005年05月26日
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>
	<%@ page contentType="text/html;charset=gbk" %>	
	<%@ page import="java.util.Collection" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
	<%@ page import="com.iss.itreasury.util.Log" %>	
	<%@ page import="com.iss.itreasury.util.Constant" %>		
	<%@ page import="com.iss.itreasury.ebank.util.*" %>	
	<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.ApprovalRelationBiz" %>	
	<%@ page import="com.iss.itreasury.ebank.approval.dataentity.ApprovalRelationInfo" %>	
	<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
	<%@page import="com.iss.itreasury.util.PageController"%>
	<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<%	
	//页面控制类
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	try
	{
        String strTitle = "审批流关联设置";
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
		ApprovalRelationBiz appRelationBiz = new ApprovalRelationBiz();
		ApprovalRelationInfo qInfo = null;
		if(String.valueOf(Constant.Actions.NEXTSTEP).equals(pageInfo.getStrAction()))
		{
			qInfo = new ApprovalRelationInfo();
			qInfo.setOfficeID(sessionMng.m_lOfficeID);
			qInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			qInfo.setModuleID(Constant.ModuleType.BUDGET);
			qInfo.setSubLoanTypeID(sessionMng.m_lClientID);
			sessionMng.setQueryCondition("qInfo",qInfo);
		}
		else if(String.valueOf(Constant.Actions.MODIFYSAVE).equals(pageInfo.getStrAction()))
		{
			qInfo = (ApprovalRelationInfo)sessionMng.getQueryCondition("qInfo");
			ArrayList infos = new ArrayList();
			//贷款模块所有的操作类型
			long[] approvalAction = Constant.ApprovalAction.getAllCode(Constant.ModuleType.BUDGET);
			
			for(int i=0;i<approvalAction.length;i++)
			{
				ApprovalRelationInfo info = new ApprovalRelationInfo();
				long actionID = approvalAction[i];
				long approvalID = Long.valueOf(request.getParameter("actionID" + String.valueOf(actionID))).longValue();
				if(approvalID > 0)//对该操作类型设置了审批流
				{
					if(request.getParameter("approvalID" + String.valueOf(actionID)) != null)
					{
						long lOrignalApprovalID = Long.valueOf(request.getParameter("approvalID" + String.valueOf(actionID))).longValue();
						info.setId(lOrignalApprovalID);//现关联审批流ID	
						info.setApprovalID(approvalID);//更新后关联审批流ID
						
						Log.print("现关联审批流lOrignalApprovalID=" + lOrignalApprovalID);
						Log.print("更新后关联审批流approvalID=" + approvalID);
					}
					else //新增
					{
						info.setApprovalID(approvalID);
					}
					info.setActionID(actionID);
					info.setCurrencyID(qInfo.getCurrencyID());
					info.setLoanTypeID(qInfo.getLoanTypeID());
					info.setModuleID(Constant.ModuleType.BUDGET);
					info.setOfficeID(qInfo.getOfficeID());
					info.setSubLoanTypeID(qInfo.getSubLoanTypeID());
					
					infos.add(info);
				}
			}
			appRelationBiz.save(infos);
		}
		Collection resultColl = appRelationBiz.findByMultiOption(qInfo);
		request.setAttribute("resultColl",resultColl);
		
		pageInfo.setStrSuccessPageURL("../view/v021.jsp");
		pageInfo.success();
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		pageInfo.setStrFailPageURL("../view/v021.jsp");
		//出现异常,操作结果置为失败	
		pageInfo.fail();
	}	
	//将操作结果置入request中 
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());

	//转向下一页面 
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
			//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>