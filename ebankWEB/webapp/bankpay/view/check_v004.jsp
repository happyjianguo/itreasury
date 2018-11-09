<%--
/*
 * 程序名称：
 * 功能说明：业务复核完毕显示页面
 * 作　　者：
 * 日期：2010年10月16日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->
<%
	//标题变量
	String strTitle = "[银行汇款]";
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
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
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%out.print("业务复核确认");%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	  <br/>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal" >
            <br>
<%
			OBBankPayInfo info=(OBBankPayInfo)request.getAttribute("info");
			System.out.println("********info in check_v004.jsp************"+info);
			
%>
				
				<%
				if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) out.print("这笔业务现为<b>已复核状态</b>,");	
				if(info.getNiscanaccept()==3 ) out.print("需要签认后才能发送银行指令");
				else if(info.getNiscanaccept()==2 ) out.print("需要审核后才能发送银行指令");
					else out.print("已经发送银行指令"); 
				%>
				<br><br>
				指令序列号为<b><%=info.getId()%></b>
              
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>      
     
        <tr class="MsoNormal">
          
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">

            <div align="right"><!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit()"-->
			<input type="button" name="Submitv00204" value=" 关 闭 " class="button1" onClick="javascript:window.close();"></div>&nbsp;&nbsp;&nbsp;&nbsp;

		  </td>
		  <td>&nbsp;</td>
        </tr>
        <tr><td height=5>&nbsp;</td></tr>
      </table>
</td>
</tr>
</table>
	 
	  <script language="JavaScript">
	  
	  function window.onunload()
	  {   
	      window.opener.document.frmjysqcx.ActionID.value="<%=new java.util.Date().getTime()%>";
          window.opener.doSearch();
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
	/* 显示文件尾*/
	OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>