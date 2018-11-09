<%--
/*
 *程序名称：S858-Opt-Curr.jsp
 *功能说明：签认金额设置提交完成页面（新奥--活期）
 *作    者: 周翔
 *完成日期: 2011年4月15日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*"
                 %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<%
 	response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%
    //固定变量
      String strTitle = "活期签认金额设置";

    try{
           /**
            * isLogin start
           */
          //登录检测
          if( sessionMng.isLogin()==false)
         {
               OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
               out.flush();
               return;
         }

        //判断是否有权限
        if(sessionMng.hasRight(request)==false)
        {
               out.println(sessionMng.hasRight(request));
               OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
               out.flush();
               return;
        }
        /**
         * isLogin end
         */
		 		
        /**
         * presentation start
         */
       //显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<form name="form1" action="<%=strContext%>/system/S844-Ctr-Curr.jsp" method="post">
  
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">活期签认金额设置</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    
    </td>
  </tr>
 
</table>
<br/>
	 <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>&nbsp;</td>
      </tr>
     
     
    </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal align="">
        <tr bgcolor="">
          <td colspan="3" height="1"></td>
        </tr> 
        <tr bgcolor="">
          <td width="5" height="25"></td> 
          <td height="25">设置已生效</td>
          <td width="1" height="25"></td>
        </tr>
      </table>
      <br>
      <table width=80% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right"></div>
          </td>
          <td width="62">
            <div align="right">
			<!--a href="#" onClick="form1.submit() ;"><img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0"></a-->
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="form1.submit() ;">			
			</div>
          </td>
        </tr>
      </table>
	  </form>
<!--presentation end-->
<%
      }
      catch (IException ie)
      {
           OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
      }

      OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
