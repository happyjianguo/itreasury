<%@page contentType="text/html;charset=gbk"%>
<%/*
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
    <%@page import="java.util.*,
	com.iss.itreasury.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.attach.bizlogic.*,
	com.iss.itreasury.loan.attach.dataentity.*
	" %>
<%
	try
	{
 		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		Collection c = null;
		c = (Collection)request.getAttribute("AttachInfo");
		long ParentID = Long.parseLong(String.valueOf(request.getAttribute("ParentID")));
		long ParentIDType = Long.parseLong(String.valueOf(request.getAttribute("ParentIDType")));
			
		//显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"链接文件",Constant.YesOrNo.NO);
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<SCRIPT language=JavaScript src="/webob/js/date-picker.js"></SCRIPT>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>

<safety:resources />

<FORM METHOD="post" name="form_1"
 ACTION="<%=Env.EBANK_URL%>/UpLoadServlet?DocType=<%=Constant.DocType.EBANKUPLOAD%>&DestJsp=<%=Env.EBANK_URL%>attach/a106a-c.jsp&UserID=<%=sessionMng.m_lUserID%>" 
 ENCTYPE="multipart/form-data">
<input type="hidden" name="SUBMIT" >
<input type="hidden" name="ParentID" value="<%=ParentID%>" >
<input type="hidden" name="ParentIDType" value="<%=ParentIDType%>" >
<TABLE border=0 class=top height=231 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=19><FONT size=5><B><FONT 
      size=3><B>链接文件</B></FONT></B></FONT></TD></TR>
  <TR>
    <TD height=101>
      <TABLE align=center border=0 height=120 width=100%>
        <TBODY>
        <TR borderColor=#ffffff>
          <TD height=15 >&nbsp;</TD>
          <TD colSpan=3 height=15><font color='#FF0000'>*</font><FONT size=2>选择文档：</FONT></TD>
         
          <TD height=15 ><FONT size=2>
          <INPUT class=box name="file1" type="file"   onFocus="nextfield ='ClientName';"> 
            </FONT></TD>
          <TD height=15 ><FONT size=2>&nbsp;</FONT></TD>
          <TD height=15 vAlign=bottom ><FONT size=2>&nbsp;</FONT></TD>
          <TD height=15 >&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=3 height=29>
            <DIV align=center></DIV><font color='#FF0000'>*</font><FONT size=2>文档描述： </FONT></TD>
          
          <TD height=29 ><FONT size=2>
          <INPUT class=box type="text" name="ClientName"  onFocus="nextfield ='submitfunction';"> 
          </FONT></TD>
          <TD height=29 ><FONT size=2>&nbsp;</FONT></TD>
          <TD height=29 >
          <INPUT class=button name="upload"  type=button value=" 链 接 " onclick="validate(1,0);" > 
          </TD>
          <TD height=29 >&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=6 height=29>
            <HR>
          </TD>
          <TD height=29 >&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=3 height=29><FONT size=2>已链接的文档：</FONT></TD>
          <TD colSpan=2 height=29><FONT size=2>&nbsp;</FONT></TD>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 >&nbsp;</TD>
		  </TR>
		  <%
		  	if ( c != null )
			{
				Iterator it = c.iterator();
				while ( it.hasNext() )
				{
					AttachInfo info = new AttachInfo();
					info = (AttachInfo)it.next();
		  %>
         <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 >
            <DIV align=right></DIV></TD>
          <TD height=29>&nbsp;</TD>
          <TD height=29>&nbsp; </TD>
          <TD colSpan=2 height=29>
		  <FONT size=2>
          <A href="<%=Env.EBANK_URL%>DownLoadServlet?FileID=<%=info.getFileID()%>" target="_blank">
		  <%=info.getShowName()%>
		  </A>－<%=info.getRealName()%>
		  </FONT>
		  </TD>
          <TD height=29 >
          <a href="Javascript:validate(2,<%=info.getID()%>)">删除</a>
          </TD>
          <TD height=29 >&nbsp;
		  </TD>
		  </TR>
		  <%
		  	}
		  }
		  %>
        <TR borderColor=#ffffff>
          <TD height=2 >&nbsp;</TD>
          <TD colSpan=6 height=2>
            <HR>
          </TD>
          <TD height=2 >&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=2 >&nbsp;</TD>
          <TD colSpan=2 height=2>&nbsp;</TD>
          <TD colSpan=4 height=2>
            <DIV align=right>
<INPUT class=button name=Submit43 onclick="Javascript:closeme();" type=button value=" 关 闭 "> 
            </DIV></TD>
          <TD height=2 >&nbsp;</TD>
        </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
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
		strDesc = form1.ClientName.value;
	}
    form1.SUBMIT.value="upload";
	form1.action = "<%=Env.EBANK_URL%>UpLoadServlet?DocType=<%=Constant.DocType.LOANCONTRACTCONTENT%>&ParentID=<%=ParentID%>&ParentIDType=<%=ParentIDType%>&ClientName=" + strDesc + "&DestJsp=/attach/a106a-c.jsp&UserID=<%=sessionMng.m_lUserID%>";
	//alert("<%=Env.EBANK_URL%>UpLoadServlet?DocType=<%=Constant.DocType.LOANCONTRACTCONTENT%>&ParentID=<%=ParentID%>&ParentIDType=<%=ParentIDType%>&ClientName=" + strDesc + "&DestJsp=<%=Env.EBANK_URL%>attach/a106a-c.jsp&UserID=<%=sessionMng.m_lUserID%>");
  }
  else
  {
  	if ( confirm("确定删除吗？") )
	{
    	form1.action = "<%=Env.EBANK_URL%>attach/a103a-c.jsp?fileID=" + iNo ;
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
  parent.opener.location.reload();
  window.close();
}

</script>

<script language="javascript">
	firstFocus(document.form_1.file1);
	//setSubmitFunction("validate(1,0)");
    setFormName("form_1");
</script>

<%
	OBHtml.showOBHomeEnd(out);
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>