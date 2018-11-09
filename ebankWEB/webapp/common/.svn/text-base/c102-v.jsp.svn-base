<%@page contentType="text/html;charset=gbk"%>
<%/*
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
    <%@page import="java.util.*,
	                com.iss.itreasury.util.*,
	                com.iss.itreasury.common.attach.bizlogic.*,
	                com.iss.itreasury.common.attach.dataentity.*,
	                com.iss.itreasury.ebank.util.*" 
	%>
		<% String strContext = request.getContextPath();%>
<%
	try
	{
 %>
<fs:header showMenu="false" />	
 <%
		Collection c = null;
		c = (Collection)request.getAttribute("AttachInfo");
		long ParentID = Long.parseLong(String.valueOf(request.getAttribute("ParentID")));
		long ParentIDType = Long.parseLong(String.valueOf(request.getAttribute("ParentIDType")));
		long ModuleID = Long.parseLong(String.valueOf(request.getAttribute("ModuleID")));
		long TransTypeID = Long.parseLong(String.valueOf(request.getAttribute("TransTypeID")));
		long TransSubTypeID = Long.parseLong(String.valueOf(request.getAttribute("TransSubTypeID")));
		long CurrencyID = Long.parseLong(String.valueOf(request.getAttribute("CurrencyID")));
		long OfficeID = Long.parseLong(String.valueOf(request.getAttribute("OfficeID")));
		long clientID = Long.parseLong(String.valueOf(request.getAttribute("ClientID")));
		String transCode = (String)request.getParameter("transCode");
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<SCRIPT language=JavaScript src="/webob/js/date-picker.js"></SCRIPT>
<script lang="JavaScript" src="/webob/js/Check.js" type="text/javascript"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<FORM METHOD="post" name="form_1"
 ACTION="<%=strContext%>/UpLoadServlet?DocType=<%=Constant.DocType.EBANKUPLOAD%>&DestJsp=/common/c106-c.jsp&UserID=<%=sessionMng.m_lUserID%>" 
 ENCTYPE="multipart/form-data">
<input type="hidden" name="SUBMIT" >
<input type="hidden" name="ParentID" value="<%=ParentID%>" >
<input type="hidden" name="ParentIDType" value="<%=ParentIDType%>" >
<input type="hidden" name="ModuleID" value="<%=ModuleID%>" >
<input type="hidden" name="TransTypeID" value="<%=TransTypeID%>" >
<input type="hidden" name="TransSubTypeID" value="<%=TransSubTypeID%>" >
<input type="hidden" name="CurrencyID" value="<%=CurrencyID%>" >
<input type="hidden" name="OfficeID" value="<%=OfficeID%>" >
<input type="hidden" name="ClientID" value="<%=clientID%>" >
<input type="hidden" name="transCode" value="<%=transCode%>" >



<table  cellpadding="0" cellspacing="0" class="title_top">
	<tr>
		    <td height="22">
			    <table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
				       <td class=title><span class="txt_til2">链接文件</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
		  	
	<br/>
      <TABLE align= border=0 height=120 width="100%" class=normal>
        <TBODY>
        <tr><td></td></tr>
        <TR >
          <TD height=15 width=1>&nbsp;</TD>
          <TD colSpan=3 height=15 nowrap width="9%"><font color='#FF0000'>*&nbsp;</font><FONT size=2>选择文档：</FONT></TD>
         
          <TD height=15 width=189><FONT size=2>
          <INPUT class=box name="file1" type="file"   onFocus="nextfield ='ClientName';"> 
            </FONT></TD>
          <TD height=15 width=288><FONT size=2>&nbsp;</FONT></TD>
          <TD height=15 vAlign=bottom width=302><FONT size=2>&nbsp;</FONT></TD>
          <TD height=15 width=1>&nbsp;</TD></TR>
        <tr><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td></tr><TR borderColor=#ffffff>
          <TD height=29 width=1>&nbsp;</TD>
          <TD colSpan=3 height=29 nowrap>
            <DIV align=center></DIV><font color='#FF0000'>*&nbsp;</font><FONT size=2>文档描述： </FONT></TD>
          
          <TD height=29 width=189><FONT size=2>
          <INPUT class=box type="text" name="ClientName"  onFocus="nextfield ='submitfunction';"> 
          </FONT></TD>
          <TD height=29 width=288><FONT size=2>&nbsp;</FONT></TD>
          <TD height=29 width=302 align=right>
          
          </TD>
          <TD height=29 width=1>&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=29 width=1>&nbsp;</TD>
          <TD colSpan=6 height=29>
            <HR>
          </TD>
          <TD height=29 width=1>&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=29 width=1>&nbsp;</TD>
          <TD colSpan=3 height=29 nowrap><FONT size=2>已链接的文档：</FONT></TD>
          <TD colSpan=2 height=29><FONT size=2>&nbsp;</FONT></TD>
          <TD height=29 width=302>&nbsp;</TD>
          <TD height=29 width=1>&nbsp;</TD>
		  </TR>
		  <%
		  DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
		  	if ( c != null )
			{
				Iterator it = c.iterator();
				while ( it.hasNext() )
				{
					AttachInfo info = new AttachInfo();
					info = (AttachInfo)it.next();
		  %>
         <TR borderColor=#ffffff>
          <TD height=29 width=1>&nbsp;</TD>
          <TD height=29 width=122>
            <DIV align=right></DIV></TD>
          <TD height=29>&nbsp;</TD>
          <TD height=29>&nbsp; </TD>
          <TD colSpan=2 height=29>
		  <FONT size=2>
          <A href="<%=strContext%>/DownLoadServlet?FileID=<%=en.getEncString(String.valueOf(info.getFileID()))%>" target="_blank">
		  <%=info.getShowName()%>
		  </A>－<%=info.getRealName()%>
		  </FONT>
		  </TD>
          <TD height=29 width=6%>
          <a href="Javascript:validate(2,<%=info.getID()%>)">删除</a>
          </TD>
          <TD height=29 width=1>&nbsp;
		  </TD>
		  </TR>
		  <%
		  	}
		  }
		  %>
         <TR borderColor=#ffffff>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=6 height=2>
            <HR>
          </TD>
          <TD height=2 width=1>&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD colSpan=5 height=2></TD>
          
           <TD width="6%">
	          
					<INPUT class=button1 name="upload"  type=button value=" 链 接 " onclick="validate(1,0);" >
			   
		   </TD>
		   <TD width="6%">
			    		 
				&nbsp;&nbsp;<INPUT class=button1 name=Submit43 onclick="Javascript:closeme();" type=button value=" 关 闭 "> 
	           
           </TD>
           <td></td>
        </TR>
        <tr><td></td></tr>
      </TBODY>
   </TABLE>
   </td>
</tr>
</table> 
</form>
<script language="javascript">
function validate(iType,iNo)
{
  var sTmp;
  var form1;
  var strDesc;
  sTmp = "";
  form1 = document.form_1;
  if (iType == 1)
  {

    if (!checkString(form1.file1,"选择文档"))
    {
      return false;
    }
    if (!checkString(form1.ClientName,"文档描述"))
    {
      return false;
    }
	else
	{
		//strDesc = encodeURIComponent(form1.ClientName.value);
		strDesc = form1.ClientName.value;
	}
	if(confirm("确定要链接吗？") )
	{
	    form1.SUBMIT.value="upload";
		
		form1.action = "<%=strContext%>/UpLoadServlet?transCode=<%=transCode%>&ParentID=<%=ParentID%>&DocType=<%=Constant.DocType.SETTLEMENTUPLOAD%>&ModuleID=<%=ModuleID%>&TransTypeID=<%=TransTypeID%>&TransSubTypeID=<%=TransSubTypeID%>&CurrencyID=<%=CurrencyID%>&OfficeID=<%=OfficeID%>&lClientID=<%=clientID%>&ParentID=<%=ParentID%>&ParentIDType=<%=ParentIDType%>&ClientName=" + strDesc + "&DestJsp=/common/c106-c.jsp&UserID=<%=sessionMng.m_lUserID%>";
	}
	else
	{
		return;
	} 
  }
  else
  {
  	if ( confirm("确定删除吗？") )
	{
    	form1.action = "<%=strContext%>/common/c103-c.jsp?transCode=<%=transCode%>&ParentID=<%=ParentID%>&ModuleID=<%=ModuleID%>&TransTypeID=<%=TransTypeID%>&TransSubTypeID=<%=TransSubTypeID%>&CurrencyID=<%=CurrencyID%>&OfficeID=<%=OfficeID%>&ClientID=<%=clientID%>&ParentIDType=<%=ParentIDType%>&fileID="+iNo;
    	form1.SUBMIT.value="delete";
	}
	else
	{
		return ;
	}
  }
  form_1.submit();
  return true;
}

function closeme()
{
  parent.opener.document.forms[0].transCode.value = '<%=transCode%>';
  parent.opener.document.forms[0].action = "/NASApp/iTreasury-ebank/common/c107-c.jsp";
  parent.opener.document.forms[0].submit();
  window.close();
}

</script>

<script language="javascript">
	firstFocus(document.form_1.file1);
	//setSubmitFunction("validate(1,0)");
    setFormName("form_1");
</script>

<%
	}
	catch (Exception ie)
	{ 
		//OBHtml.showExceptionMessage(out, sessionMng, (IException)ie,"文件上传","",1);
		ie.printStackTrace();
		out.flush();
		return;
	}
%>
