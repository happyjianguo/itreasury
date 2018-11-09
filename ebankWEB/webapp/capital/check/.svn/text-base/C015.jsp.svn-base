<%--
/*
 * 程序名称：C415.jsp
 * 功能说明：交易指令批量复核控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月24日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 com.iss.itreasury.settlement.util.NameRef,
                 java.sql.*,
                 com.iss.itreasury.settlement.util.NameRef,
                 java.util.*,
                 com.iss.itreasury.ebank.bizdelegation.*,
                 com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>

<%
	//标题变量
	String strTitle = "[业务复核]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
%>
<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
%>

<%
		//查询条件信息，用于V416.jsp,返回时，给form写值
		QueryCapForm qcf=new QueryCapForm();
 	
		// 网上银行交易类型,资金划拨由专门的类型
    	qcf.setTransType( GetNumParam(request,"SelectType") ); // 网上银行交易类型,资金划拨由专门的类型
		Log.print("SelectType:   "+qcf.getTransType());
	
    	qcf.setStartSubmit ( GetParam(request,"txtConfirmA") ); // 提交日期-从
		Log.print("txtStartSubmit:   "+qcf.getStartSubmit());
    
    	qcf.setEndSubmit ( GetParam(request,"txtConfirmB") ); // 提交日期-到
		Log.print("txtEndSubmit:   "+qcf.getEndSubmit());
    
    	qcf.setStatus ( GetNumParam(request,"SelectStatus") );// 交易指令状态
		Log.print("SelectStatus:   "+qcf.getStatus());
		
		if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase("")))
    	{
			qcf.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(request.getParameter("txtMinAmount").trim())) );// 交易金额-最小值
			Log.print("MinAmount:   "+qcf.getMinAmount());
		}
		
		if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase("")))
    	{
			qcf.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(request.getParameter("txtMaxAmount").trim())) );// 交易金额-最大值
			Log.print("MaxAmount:   "+qcf.getMaxAmount());
      	}
		
		qcf.setStartExe ( GetParam(request,"txtExecuteA") ); // 执行日期-从
		Log.print("StartExe :   "+qcf.getStartExe ());
	
    	qcf.setEndExe ( GetParam(request,"txtExecuteB") ); // 执行日期-到
		Log.print("EndExe :   "+qcf.getEndExe ());   

		//定义接收Form信息的变量
		long lIsCheck = -1;//是否是复核
		if (request.getParameter("txtisCheck") !=null && (!request.getParameter("txtisCheck").trim().equalsIgnoreCase("")))
		{
			lIsCheck = Long.parseLong(request.getParameter("txtisCheck"));      //如果,strCheckbox为null说明是从V12等页面过来的
    	}
		String strCheckbox[] = request.getParameterValues("txtCheckbox");
		String strID[] = null;
		if (request.getParameter("txtID") !=null && (!request.getParameter("txtID").trim().equalsIgnoreCase("")))
    	{
			strID = request.getParameterValues("txtID");
   		}
		Log.print("是否是复核取消："+lIsCheck);
%>

<%		
		//定义流水号
		//初始化EJB
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		
		FinanceInfo info = null;

		//调用ejb方法
		int iCount = -1;//数据提交的个个数
		int j = 0 ; //循环变量
		if (strCheckbox != null)
		{
			iCount = strCheckbox.length;//数据提交的个个数
			Log.print("C415.jsp:checkbox array:"+strCheckbox.length);
		}
		else
		{
			iCount = 1;//为空，表示只有一条数据
			Log.print("C415.jsp:single checkbox");
		}
		Log.print("iCount:   "+iCount);
		String strTemp = new String();//返回信息
		Vector vcResult = new Vector(1);//返回信息
		
		/* 初始状态是未复核，进行复核操作 */
		if (lIsCheck == 1) 
		{
			//复核，循环对每一行处理
			Log.print("复核...");
			for(int i=0;i<iCount;i++)
			{
				int iTag;//数组下标
				long lID;//指令序号
				long lRet= -1 ;//调用check()的反回值
				// 如果，strCheckbox为null说明是从V12等页面过来的
				if (strCheckbox != null)
				{
					Log.print("复核:"+i+"     :"+strCheckbox[i]);
					iTag = Integer.parseInt(strCheckbox[i]);
					Log.print("复核:"+i+"     :"+strID[iTag-1]);
					lID = Long.parseLong(strID[iTag-1]);										
				}
				else
				{					
					lID = Long.parseLong(strID[0]);
					Log.print("--------lID="+lID);										
				}
				
				/* 调用ejb方法 */
				FinanceInstructDelegation financeInstructDelegation = new FinanceInstructDelegation();
				financeInstructDelegation.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				
				/* 返回信息处理 */
				if( obFinanceInstrDao.getSignUserID(lID) > 0 )
				{
					strTemp = OBConstant.SettInstrType.getName( OBConstant.getTransTypeByQueryType(qcf.getTransType()) )+" 这笔业务现为已复核状态，需要签认后才能提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
					vcResult.add(strTemp);
				}
				else
				{
					strTemp = OBConstant.SettInstrType.getName( OBConstant.getTransTypeByQueryType(qcf.getTransType()) )+" 这笔业务现为已复核状态，已经提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
					vcResult.add(strTemp);				
				}
				strTemp = "<b>指令序号为： "+lID+" </b><br>";
				vcResult.add(strTemp);								
			}
		}
		
		/* 初始状态为已复核，取消复核 */
		else  
		{	
			Log.print("取消复核...");
			for(int i=0;i<iCount;i++)
			{
				int iTag;//数组下标
				long lID;//指令序号
				long lRet;//调用cancelcheck()的反回值
				// 如果，strCheckbox为null说明是从S12等页面过来的
				if (strCheckbox != null)
				{				
					Log.print("取消复核:"+i+"     :"+strCheckbox[i]);
					iTag = Integer.parseInt(strCheckbox[i]);
					Log.print("取消复核:"+i+"     :"+strID[iTag-1]);
					lID = Long.parseLong(strID[iTag-1]);
					Log.print("取消复核:"+i+"     :"+lID);					
				}
				else
				{					
					lID = Long.parseLong(strID[0]);					
				}
				
				/* 调用ejb方法 */
				lRet = financeInstr.cancelCheck(lID, sessionMng.m_lUserID);
				Log.print("**********第"+i+"次循环*****************");
				
				/* 返回信息处理 */
				strTemp = OBConstant.SettInstrType.getName( OBConstant.getTransTypeByQueryType(qcf.getTransType()) )+" 这笔业务现为未复核状态，需要复核后才能提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
				vcResult.add(strTemp);
				strTemp = "<b>指令序号为： "+lID+" </b><br>";
				vcResult.add(strTemp);
			}				
		}

		//在请求中保存结果对象
		request.setAttribute("return",vcResult);
		request.setAttribute("FormValue",qcf);
		request.setAttribute("isCheck",request.getParameter("txtisCheck"));
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/capital/check/V017.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		//forward到结果页面
		rd.forward(request, response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	}
%>

