<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>

<%

		double txtMinAmount = 0.00;   // 交易金额-最小值
        double txtMaxAmount = 0.00;   // 交易金额-最大值
        String txtExecuteA = "";    // 执行日期-从
        String txtExecuteB="";      //执行日期到
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
        //定义接收Form信息的变量
		//定义初始化查询信息对象
    	QueryCapForm qcf=new QueryCapForm();
    	
	     String strTemp = "";
		strTemp = (String) request.getParameter("txtMinAmount");
		 if(strTemp != null && strTemp.trim().length() > 0) {
            txtMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最小值
           
        }
        strTemp = (String) request.getParameter("txtMaxAmount");
		 if(strTemp != null && strTemp.trim().length() > 0) {
            txtMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最小值
           
        }
        
         strTemp = (String) request.getParameter("txtExecuteA");
        if(strTemp != null && strTemp.trim().length() > 0) {
            txtExecuteA = strTemp; // 执行日期-从
        }
        strTemp = (String) request.getParameter("txtExecuteB");
        if(strTemp != null && strTemp.trim().length() > 0) {
            txtExecuteB = strTemp; // 执行日期-到
           
        }
		
		if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase("")))
		{
			
			qcf.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMinAmount").trim())) );// 交易金额-最小值
			Log.print("MinAmount:   "+qcf.getMinAmount());
		}	
		System.out.print("*************************begin1");
		if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase("")))
		{
			qcf.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMaxAmount").trim())) );// 交易金额-最大值
			//Log.print("MaxAmount:   "+qcf.getMaxAmount());
		}	
		String tmp=GetParam(request,"txtExecuteA");
		if(tmp!=null)
		{
			qcf.setStartExe (tmp); // 执行日期-从
			//Log.print("StartExe :   "+qcf.getStartExe ());
		}
		tmp= GetParam(request,"txtExecuteB");
		if(tmp!=null)
		{
			qcf.setEndExe ( tmp); // 执行日期-到
		//Log.print("EndExe :   "+qcf.getEndExe ());      
		}
		qcf.setClientID ( sessionMng.m_lClientID );
		qcf.setCurrencyID ( sessionMng.m_lCurrencyID );
		qcf.setStatus(OBConstant.SettInstrStatus.CHECK);
		qcf.setOperationTypeID ( OBConstant.QueryOperationType.CHECK );
		qcf.setUserID ( sessionMng.m_lUserID );
		OBFinanceInstrBiz obiz=new OBFinanceInstrBiz();
		Collection result=obiz.query_uncheck(qcf);
		System.out.println("*****************获取查询结果集结束");
		request.setAttribute("rcoll",result);

	    if(result!=null)
		{
			System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssss");
		}
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v104.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		 /* forward到结果页面 */
		rd.forward(request, response);	
		
%>

<%
}
catch(IException ie) 
	{
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }%>