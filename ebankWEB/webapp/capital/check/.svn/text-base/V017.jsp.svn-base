<%--
/*
 * 程序名称：V417.jsp
 * 功能说明：交易指令单条复核或取消复核输出页面
 * 作　　者：刘琰
 * 完成日期：2003年09月25日
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
	String strTitle = "[业务复核]";
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
%>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<safety:resources />

	<table width="730" border="0" align="center" cellpadding="0" cellspacing="0" class="">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2"></span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
 <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><table width="150" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="130" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> <%if (strIsCheck.equalsIgnoreCase("1")) {out.print("业务复核确认");}else{out.print("业务取消复核确认");}%></td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal" >
            <font size="3"><br>
<%
		int iCount = rf.size();//显示数据的个数
		for (int i=0;i<iCount;i++)
		{
			String strTmp = (String)rf.elementAt(i);
			out.print("<br>"+strTmp);
		}
%>
              </font>
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>      
     
        <tr class="MsoNormal">
          
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">
<% 
		if ( lShowMenu == OBConstant.ShowMenu.NO) 
		{
%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="button" name="Submitv00204" value=" 关  闭 " class="button" onClick="Javascript:window.close();">
<%
		}
		else
		{
%>
            <div align="right"><!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit()"-->
			<input type="button" name="Submitv00204" value=" 返  回 " class="button" onClick="javascript:doExit();"></div>
<%
		}
%>
		  </td>
        </tr>
      </table>
	  <form name=form1>
	  <input type="hidden" name="isCheck" size="16" value="<%= strIsCheck %>" >
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
      </form>
	  <script language="JavaScript">
	  function doExit()
	  {             	       
		  if (form1.isCheck.value == 1)
		  {
		 	form1.action="ck012-v.jsp";
			showSending();
		  	form1.submit();
		  }
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