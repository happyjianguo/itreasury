<%--
/*
 * 程序名称：check_v006_1.jsp
 * 功能说明：交易指令复核输出页面
 * 作　　者：
 * 完成日期：2010年10月12日
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
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>

<!--Header end-->
<%
	//标题变量
	String strTitle = null;
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	Vector rf = null;//显示
	rf = (Vector)request.getAttribute("return");
	String strIsCheck = (String)request.getAttribute("isCheck");//1 是复核

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
        if (strIsCheck.equalsIgnoreCase("1"))
		{
        	/* 显示文件头 */
			OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		}
		else
		{
			OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		}	
		/*	
				String str = (String)request.getAttribute("flag");
		if(str!=null&&str.equals("checked")){
			out.println("<script language='javascript'>");
			out.println("alert('复核成功')");
			out.println("</script>");
		}
		*/
%>     

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
	
</head>

<safety:resources />
<form name=form1>
    <table  class="title_top" width="98%" align="center">
	  <tr>
	    <td height="24">
		    <br/>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
 
<br/>

      <table width=98% border="0" cellspacing="0" cellpadding="0" align="center" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal" >

<%
		int iCount = rf.size();//显示数据的个数
		for (int i=0;i<iCount;i++)
		{
			String strTmp = (String)rf.elementAt(i);
			out.print("<br>"+strTmp);
		}
%>
             
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr> 
      
      	       <tr >
          
          <td colspan="2"></td>
          <td><div align=right>

            <div align="right"><!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit()"-->
			<input type="button" name="Submitv00204" value=" 关 闭 " class="button1" onClick="javascript:window.close();"></div>&nbsp;&nbsp;&nbsp;&nbsp;

		  </div></td>
        </tr>  
        <tr><td height="5"></td></tr>  
      </table>
    	<br/>
       
	  
	  <input type="hidden" name="isCheck" size="16" value="<%= strIsCheck %>" >
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	    </td>
  </tr>
</table>
      </form>
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