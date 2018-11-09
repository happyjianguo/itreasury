<%--
 页面名称 ：c032.jsp
 页面功能 : 审批流关联设置保存关联、删除关联操作
 作    者 ：ypxu
 日    期 ：2007-4-16
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Log" %>	
<%@ page import="com.iss.itreasury.util.Constant" %>		
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz" %>	
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo" %>
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>	
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	String strTitle = "审批流关联设置－明细";
	//页面控制类
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	InutApprovalRelationInfo commonInfo = new InutApprovalRelationInfo();
	InutApprovalRelationBiz appRelationBiz = new InutApprovalRelationBiz();
	try
	{
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
		
		//从request中获得页面控制信息
		pageInfo.convertRequestToDataEntity(request);
		
		//初始化查询类\参数类\结果类
		
		

		//从request中获得查询条件信息
		commonInfo.convertRequestToDataEntity(request);	


System.out.println("---------------------------->" + request.getParameter("isLowerUnit"));
		long islowerunit = Long.parseLong(request.getParameter("isLowerUnit")) ;  

		
		commonInfo.setIslowerunit(islowerunit);




				
		//qInfo = (ApprovalRelationInfo)sessionMng.getQueryCondition("qInfo");
		Vector v_Infos = new Vector();
				

			//获取所有的业务类型
			String[] strTransTypes = request.getParameterValues("transTypeID");		
			//获取所有的复选框的ID值
			String[] txtIDCheckbox = request.getParameterValues("txtIDCheckbox");			
			//获取ID
			String[] strID = request.getParameterValues("id");
			//获取动作的操作类型
			String operation = request.getParameter("operation");
			//交易类型
			long transType = -1;
			//审批流ID
			long approvalID = -1;
			//主键ID
			long lID = -1;
			
			
			//保存操作
		if(operation.equals("save"))
		{
			for(int i = 0;i < strTransTypes.length; i++)
			{
				//获取审批流ID
				String strApprovalID = request.getParameter("approvalID" + i);
				
				//如果对该业务类型设置了审批流
				if(strApprovalID != null && !"".equals(strApprovalID) && !"-1".equals(strApprovalID))
				{
					transType = Long.parseLong(strTransTypes[i]);				
					approvalID = Long.parseLong(strApprovalID);
					lID = Long.parseLong(strID[i]);
					InutApprovalRelationInfo info = new InutApprovalRelationInfo();
					info.setOfficeID(sessionMng.m_lOfficeID);
					info.setCurrencyID(sessionMng.m_lCurrencyID);
					info.setClientID(sessionMng.m_lClientID);
					info.setModuleID(commonInfo.getModuleID());
					info.setTransTypeID(transType);
					info.setApprovalID(approvalID);
					info.setId(lID);
					info.setIslowerunit(islowerunit);
					//是否提交上级审批
					long lIsSendToUpClient = -1;
					String strIsSendToUpClient = request.getParameter("isSendToUpClientCheckbox" + i);
					if(islowerunit==OBConstant.IsLowerun.ISNO && strIsSendToUpClient!=null)
					{
						lIsSendToUpClient = Long.valueOf(strIsSendToUpClient).longValue();
					}
					//
					//是否提交上级审批
					if(islowerunit==OBConstant.IsLowerun.ISNO)
					{
						info.setIssendtoupclient(lIsSendToUpClient);
					}
					//
					v_Infos.add(info);
				}	
			}
			
			appRelationBiz.batchSave(v_Infos);
			sessionMng.getActionMessages().addMessage("设置关联成功");	
		}
		if(operation.equals("del"))
		{			
				if(txtIDCheckbox != null && txtIDCheckbox.length > 0)
				{
					for(int i = 0; i < txtIDCheckbox.length; i++)
					{
						int j = Integer.parseInt(txtIDCheckbox[i]);
						lID = Long.parseLong(strID[j]);
						appRelationBiz.delete(lID);					
					}
					sessionMng.getActionMessages().addMessage("取消关联成功");	
				}
				else
				{
					sessionMng.getActionMessages().addMessage("请选择要取消关联的记录！");				
				}							
		}
		
    	pageInfo.success();
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//出现异常,操作结果置为失败	
		pageInfo.fail();
	}
	finally
	{
		Collection c_Result = null;
		c_Result = appRelationBiz.queryByConditions(commonInfo);
		request.setAttribute("c_Result",c_Result);
		//
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