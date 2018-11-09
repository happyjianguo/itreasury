<%--
/*
 * 程序名称：V827.jsp
 * 功能说明：系统管理-收款方资料修改输入页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%
	//固定变量
	String strTitle = null;
%>

<%
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        // 用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//显示文件头
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String strContext = request.getContextPath();//http://xxxx/../cpob
		//接收数据
		ClientCapInfo rf = (ClientCapInfo)request.getAttribute("ClientCapInfo");
		String strBankNo = com.iss.itreasury.settlement.util.NameRef.getBankAccountNOByCurrenctAccountID(rf.getPayeeAccoutNO());
			rf.setPayeeBankNO("");
		
%>

<safety:resources />
<form name="form1" method="post">
<%-- C823.jsp需要数据 --%>
<input type="hidden" name="txtIsCPF"  value="true">
<%-- C825.jsp需要数据 --%>
<input type="hidden" name="txtIDCheckbox" value="<%if (rf != null) out.print(rf.getID()); %>">

    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">收款方资料－修改</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  
<br/>
 
           
            <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
<tr>
              <td  height="25" width="1"></td>
                
      <td height="25" width="4" ></td>
                
      <td height="25" width="129" class="graytext"  align="left"><font color="#FF0000">* </font>账号：</td>
                <td height="25" colspan="2" class="graytext" >
				<%-- C821.jsp需要数据 --%>
                  <input class="box" type="text" name="txtPayeeBankNO" size="20" value="<%
                  if (rf != null && rf.getPayeeAccoutNO() != null){
                  NameRef nameref = new NameRef();
                  out.print(nameref.getNoLineAccountNo(rf.getPayeeAccoutNO())+rf.getPayeeBankNO());
                  } 
                  %>" >
                </td>
              <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
<tr>
                <td  height="25" width="1"></td>
                
      <td height="25" width="4" ></td>
                
      <td height="25" width="129" class="graytext"  align="left">&nbsp;&nbsp;汇入行名称：</td>
                <td height="25" colspan="2" class="graytext" >
                  <input class="box" type="text" name="textfield2222222" size="32" value="<%=Env.getClientName()%>"  disabled>
                </td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
            </table>
            <br>       
            <table width=100% border="0" cellspacing="0" cellpadding="0" align="">	  
			  <tr>
                <td  colspan="3">
                  <div align="right">
				  <!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSelectForm1();"-->
				 <input type="button" name="Savev00204" value=" 保 存 " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doSubmitForm1();" <% }%>>&nbsp;&nbsp;
				 <input type="button" name="Submitv00204" value=" 删 除 " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doDeletForm1();" <% }%>>&nbsp;&nbsp;
				 <input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="doBackForm1();">&nbsp;&nbsp;
				  </div>
                </td>
              </tr>
            </table>
  </td>
  </tr>
</table>
</form>

<script language="JavaScript" src="/webob/js/Control.js"></script>

<script language="JavaScript">
function doCheckForm1()
{
	if (form1.txtPayeeBankNO.value == "")
	{
		alert("账号不能为空，请录入");
		form1.txtPayeeBankNO.focus();
		return false;
	}
	return true;
}

function doSubmitForm1()
{
	if (doCheckForm1())
	{
		form1.action = "<%= strContext %>/system/C821.jsp?flag=saved";
		showSending(); 
		form1.submit();
	}
}
function doDeletForm1()
{
	if(confirm("是否删除？"))
	{
	  form1.action = "<%= strContext %>/system/C825.jsp?flag=deleted";
	  showSending(); form1.submit();
	}
}
function doBackForm1()
{
	form1.action = "<%= strContext %>/system/C823.jsp";
	showSending(); form1.submit();
}
 firstFocus(form1.txtPayeeBankNO);/*第一个获焦点*/
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch(Exception e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
	
%>

<%@ include file="/common/SignValidate.inc" %>
