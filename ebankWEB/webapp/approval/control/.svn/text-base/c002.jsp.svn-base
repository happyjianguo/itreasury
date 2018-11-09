<%--
/*
 * 程序名称：c002.jsp
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

<%@ page import="java.util.Vector" %>
<%   
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
	Log.print("---------进入 c002.jsp 审批用户权限设置控制页面---------");
	
	
	/* 标题固定变量 */
	String strTitle = "审批设置"; 
%>

<%	
    //从Session中取得必需的参数
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//设置参数
	ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
	String strTmp = "";
	String strErrorMessage = "";
	String strType = "";      //获得当前的操作类型
	String strUserID = "";  //获得选中的人物列表
    
	long lApprovalID = -1;	//审批设置标示
    long lTotalLevel = 0;	//审批总级别
    long lIsPass = 0;		//是否允许越级
	
	long lStatusID = 1;		//当前审批状态：1、设置中；2、设置完成
	long lLevel = -1;		//当前审批级别
	long lIsOver = 1;       //判断是否全部都审批完1：是。2：否
	
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
		System.out.println("lApprovalID====="+lApprovalID);
		// 获取lLevel
		strTmp = (String)request.getAttribute("lLevel");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lLevel = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lLevel = -1;
			}
		}
		else
		{
			lLevel = 1;
		}

		// 获取Error
		strTmp = (String)request.getAttribute("Error");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				strErrorMessage = strTmp.trim();
			}
			catch(Exception e)
			{
				strErrorMessage = "";
			}
		}
		
		// 获取操作类型strType
		strTmp = (String)request.getAttribute("sType");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				strType = strTmp.trim();
			}
			catch(Exception e)
			{
				strErrorMessage = "";
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
		
		//判断当前操作是不是保存！
		if(strType.equals("save"))
		{
		long lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lResult == 1)
		{
			//获得错误信息
			strErrorMessage = "审批设置有未完成审批的业务，不允许激活！";
			request.setAttribute("Error", strErrorMessage);
			
		}
		else
		{
			// 获取选中的人物
			strTmp = (String)request.getAttribute("strUserID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strUserID = strTmp.trim();
					ApprovalSettingInfo infoForSave = new ApprovalSettingInfo();
					infoForSave.setID(lApprovalID);
					infoForSave.setLevel(lLevel);
					//获得选中人物id的数组
					Vector vTmp = DataFormat.changeStringGroup(strUserID);        //待建
					Vector vSelect = new Vector();
					for(int i=0;i<vTmp.size();i++)
					{
						ApprovalUserInfo userInfo = new ApprovalUserInfo();
						userInfo.setUserID(((Long)vTmp.elementAt(i)).longValue());
						vSelect.add(userInfo);
					}
					//将所得存入infoForSave
					infoForSave.setSelectUser(vSelect);
					//进行存储操作
					AppBiz.saveApprovalItem(infoForSave);
				}
				catch(Exception e)
				{
					strErrorMessage = "save出错";
				}
			}
			else//选择为空
			{
				try
				{
					ApprovalSettingInfo infoForSave = new ApprovalSettingInfo();
					infoForSave.setID(lApprovalID);
					infoForSave.setLevel(lLevel);
					infoForSave.setSelectUser(null);
					AppBiz.saveApprovalItem(infoForSave);
				}
				catch(Exception e)
				{
					strErrorMessage = "save出错";
				}
			}
		}
		}
		else if(lLevel < 1)
		{
			lLevel = 1;
		}

        //根据获取的参数，组织分配
        String strURL = "";
		ApprovalSettingInfo ASInfo = null;
		//获得审批设置信息
		ASInfo = AppBiz.findApprovalSetting(lApprovalID);
		ASInfo.setLevel(lLevel);
		ApprovalSettingInfo ASItemInfo = null;
		//获得审批设置人员信息s
		ASItemInfo = AppBiz.findApprovalItem(lApprovalID,lLevel,-1,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID);
		
		//审批设置信息
		request.setAttribute("ASInfo",ASInfo);
		//审批用户权限信息
		request.setAttribute("ASItemInfo",ASItemInfo);
		//出错信息
		request.setAttribute("Error", strErrorMessage);
		//设置是否有未完成的审批
		request.setAttribute("isOver",Long.toString(lIsOver));
		
		if(request.getAttribute("fromPage") != null)
		{
			request.setAttribute("fromPage",(String)request.getAttribute("fromPage"));
		}
		
		strURL = "../view/v002.jsp?control=view";
        
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