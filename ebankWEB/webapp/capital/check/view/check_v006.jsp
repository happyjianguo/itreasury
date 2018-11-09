<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<!--Header end-->

<%
	//标题变量
	String strTitle = null;
%>

<%
	/* 用户登录检测与权限校验 */
	try{ 
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
           
        /* 显示文件头 */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		Vector rf = null;//显示信息
		rf = (Vector)request.getAttribute("return");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!"+rf.size());
%>
           
<%          long lTransType=-1;             //交易类型
			long NSTATUS=-1;                //状态
			double dMinAmount = 0.0;        //交易金额-最小值   
            double dMaxAmount = 0.0;        //交易金额-最大值
        	String sStartExe = "";    // 执行日期-从
     		String sEndExe = "";      // 执行日期-到   
        	String sStartSubmit = ""; // 提交日期-从
       		String sEndSubmit = "";   // 提交日期-到  
			String strTemp = "";             //临时接收变量
			String sign = "";
			
			 strTemp = (String) request.getParameter("lTransType");
       		 if(strTemp != null && strTemp.trim().length() > 0) {
            lTransType = Long.parseLong(strTemp); 									// 网上银行交易类型
            Log.print("交易类型=" + lTransType);
       	 }
        	 strTemp = (String) request.getParameter("NSTATUS");
        	if(strTemp != null && strTemp.trim().length() > 0) {
            NSTATUS = Long.parseLong(strTemp); 										// 交易指令状态
            Log.print("交易指令状态=" + NSTATUS);
         }
 			strTemp = (String) request.getParameter("dMinAmount");
       		if(strTemp != null && strTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最小值
            Log.print("金额最小值=" + dMinAmount);
        } 
        	 strTemp = (String) request.getParameter("dMaxAmount");
        	if(strTemp != null && strTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // 金额最大值
            Log.print("金额最大值=" + dMaxAmount);
        }
      		 strTemp = (String) request.getParameter("sStartExe");
        	if(strTemp != null ) {
            sStartExe = strTemp; 													// 执行日期-从
            Log.print("执行日期-从=" + sStartExe);
        }
        
        	strTemp = (String) request.getParameter("sEndExe");
        	if(strTemp != null ) {
            sEndExe = strTemp; 													// 执行日期-到
            Log.print("执行日期-到=" + sEndExe);
        }
         strTemp = (String) request.getParameter("sStartSubmit");
        	if(strTemp != null ) {
            sStartSubmit = strTemp; 													// 提交日期-从
            Log.print("提交日期-从=" + sStartSubmit);
        }
        
        	strTemp = (String) request.getParameter("sEndSubmit");
        	if(strTemp != null ) {
            sEndSubmit = strTemp; 													// 提交日期-到
            Log.print("提交日期-到=" +sEndSubmit);
        }
        strTemp = (String) request.getParameter("sign");                            
        	if(strTemp != null ) {
            sign = strTemp; 													
        }       
 %>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />

 <form method="post" name="form1">
    <table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <br/>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width=98% align="center">
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	    <br/>
	<table width=98% border="0" cellspacing="0" cellpadding="0" align="center" class = normal>
		<tr class="MsoNormal">
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
	  	<tr class="MsoNormal">
	     	<td width="5" height="25" class="MsoNormal"></td>
	   		<td height="" class="MsoNormal">
<%
		int iCount = rf.size();//数量
		for (int i=0;i<iCount;i++)//显示
		{
			String strTmp = (String)rf.elementAt(i);
			out.print("<br>"+strTmp);
		}
%>
			</td>
	   		<td width="1" height="25"></td>
		</tr>
		<tr><td height="10"></td><td colspan=2></td></tr>
        <tr class="MsoNormal">
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">
            <div align="right">
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="javascript:doExit();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
        <tr><td height="5" colspan=3></td></tr>
	</table>
      <input type="hidden" name="dMinAmount" value="<%=dMinAmount %>">
      <input type="hidden" name="dMaxAmount" value="<%=dMaxAmount %>">
      <input type="hidden" name="sStartExe" value="<%=sStartExe %>">
      <input type="hidden" name="sEndExe" value="<%= sEndExe%>">
      <input type="hidden" name="sStartSubmit" value="<%=sStartSubmit%>">
      <input type="hidden" name="sEndSubmit" value="<%=sEndSubmit%>">
      <input type="hidden" name="NSTATUS" value="<%=NSTATUS%>">
      <input type="hidden" name="lTransType" value="<%=lTransType%>">
      <input type="hidden" name="strAction" value="search">
      <input name="strSuccessPageURL"  type="hidden" >
	  <input name="strFailPageURL"  type="hidden" >
      <br/>
	</td>
  </tr>
</table>
</form>

	  <script language="JavaScript">
	  function doExit()
	  {
			
			form1.action = "<%=strContext%>/capital/check/control/check_c005.jsp";
			<%if(sign.equals("current"))
			{%>
			form1.strSuccessPageURL.value="<%=strContext%>/capital/check/view/check_v014.jsp";
			form1.strFailPageURL.value="<%=strContext%>/capital/check/view/check_v014.jsp";
			<%}else if(sign.equals("fixd")){ %>
			form1.strSuccessPageURL.value="<%=strContext%>/capital/check/view/check_v015.jsp";
			form1.strFailPageURL.value="<%=strContext%>/capital/check/view/check_v015.jsp";
			<%}else{%>
			form1.strSuccessPageURL.value="<%=strContext%>/capital/check/view/check_v005.jsp";
			form1.strFailPageURL.value="<%=strContext%>/capital/check/view/check_v005.jsp";
			<%}%>
			showSending();
			document.form1.submit();
	  }
	  </script>
<%
   }
   catch(IException ie)
   {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
%>

<%
		OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>