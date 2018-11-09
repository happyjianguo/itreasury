<%--
/*
 * 程序名称：c005.jsp
 * 功能说明：审批权限转移列表页面
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
<%@ page import="java.util.Vector" %>
<%   
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
	Log.print("---------进入 c004.jsp 审批权限转移列表页面---------");
	
	
	/* 标题固定变量 */
	String strTitle = "审批设置"; 
%>

<%	
    //从Session中取得必需的参数
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//设置参数
	ApprovalChangeBiz AppChangeBiz = new ApprovalChangeBiz();
	String strTmp = "";
    
	long lUserID = -1;      //用户标识
	String strUserName = "";//用户名称
	long lResult = -1;		//调用方法返回结果
	long lType = 0;         //操作标识
	long lApprovalID = 0;   //审批设置标识
	//获得翻页参数
	long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;
 	long lPageNo = 1;
	long lOrderParam = 1;
	long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
	Vector vResult = new Vector();
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

		// 获取lUserID
		strTmp = (String)request.getAttribute("lUserID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lUserID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lUserID = -1;
			}
		}

		// 获取strUserName
		strTmp = (String)request.getAttribute("sUserName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				strUserName = strTmp;
			}
			catch(Exception e)
			{
				strUserName= "";
			}
		}

	
			// 获取lUserID
			strTmp = (String)request.getAttribute("lPageLineCount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lPageLineCount = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lPageLineCount = -1;
				}
			}
	
			// 获取lUserID
			strTmp = (String)request.getAttribute("lPageNo");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lPageNo = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lPageNo = -1;
				}
			}
			
			// 获取lUserID
			strTmp = (String)request.getAttribute("lOrderParam");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lOrderParam = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lOrderParam = -1;
				}
			}
			
			// 获取lUserID
			strTmp = (String)request.getAttribute("lDesc");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lDesc = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lDesc = -1;
				}
			}
        
        //根据获取的参数，组织分配
        String strURL = "";
		ApprovalChangeInfo info = new ApprovalChangeInfo();
		//查询该审批设置的详细信息
		info = AppChangeBiz.findChangeInfoByID(lApprovalID,lUserID);
		if (info == null)
		{		
			//如果没有记录，则直接返回
			strErrorMessage = "没有相应的审批设置记录，或者只存在第一级的审批设置记录，请检查！";
			request.setAttribute("Error", strErrorMessage);
			strURL = "../view/v003.jsp?control=view";
		}
		else
		{
			request.setAttribute("lApprovalID",Long.toString(lApprovalID));
			request.setAttribute("lUserID",Long.toString(lUserID));
			request.setAttribute("lPageLineCount",Long.toString(lPageLineCount));
			request.setAttribute("lPageNo",Long.toString(lPageNo));
			request.setAttribute("lOrderParam",Long.toString(lOrderParam));
			request.setAttribute("lDesc",Long.toString(lDesc));
			request.setAttribute("info",info);
			//strURL = "/iTreasury-system/control/v004.jsp?control=view";
			strURL = "../view/v005.jsp?control=view";
		}

		/* 获取上下文环境 */
		Log.print("获取上下文环境");
	   // ServletContext sc = getServletContext();
	    
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
        //CPFHTML.dealException(out,request,response,e.getMessage(),sessionMng,strTitle,1,0);
		return;
    }
%>