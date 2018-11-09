<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,com.iss.itreasury.ebank.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%String strContext = request.getContextPath();%>

<%
	boolean bLogout = false;
	String strTmp = request.getParameter("logout");
	Log.print("Logout status : " + strTmp);
	
	//added by mzh_fu 2007/12/21
	String strConfigTroy = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME);
		
	if( strTmp != null && strTmp.equals("true") )
	{	
		sessionMng.m_lUserID = -1;
		request.getSession().invalidate();
		bLogout = true;
	}
	else if(strTmp != null && strTmp.equals("false"))
	{
		response.sendRedirect(strContext + "/Home.jsp");
	}
%>
<%
	OBHtml.showOBHomeHead(out, sessionMng, "", Constant.ShowMenu.YES);
%>
<safety:resources />
<form  name="form1" method="post" action="<%=strContext%>/Logout.jsp">
<input type="Hidden" name="logout" value="false" >
<% 	if( bLogout == false && sessionMng.isLogin() == true)
	{
 %>
<TABLE align=center border=0 class=top height=217 width="27%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B><%=Env.getClientName()%></B></TD></TR>
  <TR>
    <TD height=40 vAlign=bottom width="100%">
      <TABLE align=center height=187 width="97%">
        <TBODY>
        <TR>
          <TD height=40 vAlign=top width="96%">
            <TABLE align=center border=1 borderColor=#999999 height=177 
            width="99%">
              <TBODY>
              <TR borderColor=#E8E8E8 vAlign=center>          
                <TD height=30 vAlign=center width="100%">
                  <DIV align=center>是否注销？</DIV></TD>
			  </TR>
			</TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height=11 vAlign=top width="100%">
      <TABLE align=center height=17 width="97%">
        <TBODY>
        <TR vAlign=center>
          <TD colSpan=6 height=40>
            <DIV align=center>
			 <INPUT class="button1" name="Submit"  type="submit" value=" 确认 " onClick="submitform(form1)">&nbsp;&nbsp; 
		       <input type="button" name="cancel" value=" 放弃 " class="button1" onClick="Javascript:Cancel(form1);">
            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<% 
	}
	else
	{
		//added by mzh_fu 2007/12/21
		if(strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
%>
			<script type="text/javascript">
			<!--
				if(confirm("您已退出登录，为保证您证书的使用安全，请及时将您的USB KEY从计算机上拔出，并且关闭该窗口！是否关闭该窗口？")){
					parent.window.close();
				}
			//-->
			</script>
<%		} %>			

<TABLE align=center border=0 class=top height=217 width="27%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B><%=com.iss.itreasury.util.Env.getClientName()%></B></TD></TR>
  <TR>
    <TD height=40 vAlign=bottom width="100%">
      <TABLE align=center height=187 width="97%">
        <TBODY>
        <TR>
          <TD height=40 vAlign=top width="96%">
            <TABLE align=center border=1 borderColor=#999999 height=177 
            width="99%">
              <TBODY>
              <TR borderColor=#E8E8E8 vAlign=center>        
                <TD height=30 vAlign=center width="100%">
                  <DIV align=center>您已注销</DIV>
			<br><br>
			
				 </TD>
			  </TR>
			</TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height=11 vAlign=top width="100%">
      <TABLE align=center height=17 width="97%">
        <TBODY>
        <TR vAlign=center>
          <TD colSpan=6 height=40>
            <DIV align=center>
				   <input type="button" name ="login" value=" 重新登录 " class="button1" onClick ="Javascript:ReLogin();">&nbsp;&nbsp;	
			       <input type="button" name="cancel" value=" 关闭系统 " class="button1" onClick="Javascript:Close(form1);">
				</DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
				
<%	
	}
%>
	  </form>
<%	
	//显示文件尾
	OBHtml.showOBHomeEnd(out);

%>
<SCRIPT language=JavaScript>
function submitform(form1)
{
	//clearSingleCertSession();
	form1.logout.value = true;
	return true;
}
function Cancel(form1)
{
	form1.logout.value = false;
	form1.submit();
}
function Return(form1)
{
	form1.action = "/";
	form1.submit();
}
function Close(form1)
{
	window.close();
	window.parent.close();
}
function ReLogin()
{
    //window.open("<%=strContext%>/Index.jsp" ,'_parent');
    window.parent.location = "<%=strContext%>/Index.jsp";
}

</SCRIPT>
