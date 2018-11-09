<%--
/*
 * 程序名称：c001.jsp
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
	Log.print("---------进入 c001.jsp 保存审批设置控制页面---------");
	
	
	/* 标题固定变量 */
	String strTitle = "审批设置"; 
%>

<%	
    //从Session中取得必需的参数
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//设置参数
	ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
	String strTmp = "";
    
	long lApprovalID = -1;	//审批设置标示
    long lTotalLevel = 0;	//审批总级别
	long lLowLevel = 0;		//最小审批级别
    long lIsPass = 0;		//是否允许越级
	String strApproveName = "";	//审批流名称
    String strApproveDesc = "";	//审批流描述
    
	long lStatusID = 1;		//当前审批状态：1、设置中；2、设置完成
	long lResult = -1;		//调用方法返回结果
	long lType = 0;         //操作标识
	String strErrorMessage = "";
	
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
		
		// 获取lStatusID
		strTmp = (String)request.getAttribute("lStatusID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lStatusID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lStatusID = -1;
			}
		}

		// 获取lType
		strTmp = (String)request.getAttribute("lType");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lType = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lType = -1;
			}
		}
        
        //根据获取的参数，组织分配
        String strURL = "";
		//查询是否有未审批完成的业务
		//lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lType == 1)
		{
			if(lResult==1)          //如果是转到后一页，并且有未审批完的业务，则传标识isOver为2
			{
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("isOver",new String("2"));
				strURL = "../control/c002.jsp?control=view";
			}
			else
			{
				request.setAttribute("isOver",new String("1"));
				strURL = "../control/c002.jsp?control=view";
			}
		}
		else
		{
			System.out.println("lApprovalID="+lApprovalID);
			//设置ApprovalSettingInfo()
			ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
			ASInfo.setID(lApprovalID);
			ASInfo.setName(strApproveName);
			ASInfo.setDesc(strApproveDesc);
			ASInfo.setTotalLevel(lTotalLevel);
			ASInfo.setLowLevel(lLowLevel);
			ASInfo.setIsPass(lIsPass);
			//将装态置为设置中
			ASInfo.setStatusID(Constant.ApprovalStatus.SAVE);
			ASInfo.setOfficeID(sessionMng.m_lOfficeID);
			ASInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			ASInfo.setInputUserID(sessionMng.m_lUserID);
			ASInfo.setInputDate(Env.getSystemDate());
			//保存更改信息
			lResult = AppBiz.saveApprovalSetting(ASInfo);
			Log.print("#################lResult = " + lResult);
			if(lResult == -2)
			{	
				sessionMng.getActionMessages().addMessage("存在相同名称的审批流,不能保存!");
			}			
			else if (lResult == -1)
			{
				//获得错误信息
				sessionMng.getActionMessages().addMessage("设置保存不成功!");
				//返回保存以前的值（通过lApprovalID查询出的值）
				//ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
				ASInfo = AppBiz.findApprovalSetting(lApprovalID);
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("strApproveName",strApproveName);
				request.setAttribute("strApproveDesc",strApproveDesc);				
				request.setAttribute("lTotalLevel",Long.toString(lTotalLevel));
				request.setAttribute("lLowLevel",Long.toString(lLowLevel));
				request.setAttribute("lIsPass",Long.toString(ASInfo.getIsPass()));
				request.setAttribute("lStatusID",Long.toString(ASInfo.getStatusID()));
				
				request.setAttribute("strStatusName", Constant.ApprovalStatus.getName(ASInfo.getStatusID()));
			}
			else
			{
				sessionMng.getActionMessages().addMessage("保存成功!");
				//返回现在的值（也就是页面的值）
				request.setAttribute("lApprovalID",Long.toString(lResult));
				request.setAttribute("strApproveName",strApproveName);
				request.setAttribute("strApproveDesc",strApproveDesc);		
				request.setAttribute("lTotalLevel",Long.toString(lTotalLevel));
				request.setAttribute("lLowLevel",Long.toString(lLowLevel));
				request.setAttribute("lIsPass",Long.toString(lIsPass));				
				request.setAttribute("lStatusID",Long.toString(lStatusID));				
				request.setAttribute("strStatusName",Constant.ApprovalStatus.getName(lStatusID));
			}
			strURL = "../view/v014.jsp?control=view";
		}
		
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    rd.forward(request, response);
		return;
		
    } catch (Exception e) {
		Log.print(e.toString());
        //CPFHTML.dealException(out,request,response,e.getMessage(),sessionMng,strTitle,1,0);
		return;
    }
%>