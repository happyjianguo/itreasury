<%@ page contentType="text/html; charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% 
String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
if(strSuccessPageURL == null) strSuccessPageURL = "";
String strFailPageURL = (String)request.getAttribute("strFailPageURL");
if(strFailPageURL == null) strFailPageURL = "";
String key = (String)request.getAttribute("_pageLoaderKey");
if(key == null) key = "";//��Ϊ����Ϊ����ҳ���в���ʾΪnull

PageLoader pageLoader = sessionMng.getPageLoader(key); 
%>

<form name="listForm" method="post" action="pagecontrol.jsp">
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
	<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
	<input name="_pageLoaderKey"  type="hidden" value="<%=key%>">
	<input type="hidden" name="pageLoaderInfo.pageCount" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageCount()%>">
	<input type="hidden" name="pageLoaderInfo.rowCount" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowCount()%>">

<a href="javascript:doPrevious()">��ҳ</a> 
| ��<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageNo()%>
/<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageCount()%>ҳ 
|<a href="javascript:doNext()">��ҳ</a> 
| ��<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowCount()%>�� 
| ÿҳ <input type="text" size="4" maxlength="3" name="pageLoaderInfo.rowPerPage" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowPerPage()%>" onKeyDown="beforeDoRow()"> ��
<input type="button" class="button" value="go" onclick="doRow()"/> 
| ת���� 
<select name="pageLoaderInfo.pageNo" class='box' onchange="doJump()">
<% 
if (pageLoader != null) 
{ 
	for (int nPageIndex=1;nPageIndex<=pageLoader.getPageLoaderInfo().getPageCount();nPageIndex++) 
	{
%>
	<option value="<%=String.valueOf(nPageIndex)%>" <%=(nPageIndex==pageLoader.getPageLoaderInfo().getPageNo())?"selected":""%>>
		<%=String.valueOf(nPageIndex)%></option>
<%   
	} 
} 
%>
</select> ҳ
</form>
<script language="javascript">	
function beforeDoRow()
{
	var keycode = event.keyCode;
	//alert (keycode);
	if (keycode == 13)
	{
		doRow();
	}
}

function doPrevious()
{	if(isEmpty()) return;
	if (listForm.elements["pageLoaderInfo.pageNo"].value == 1)
	{
		alert("�Ѿ��ǵ�һҳ��");
		return;
	}
	listForm.strAction.value="<%=Constant.PageControl.PREVIOUSPAGE%>";
	listForm.submit();
}

function doNext()
{
	//alert (listForm.strSuccessPageURL.value);
	if(isEmpty()) return;
	if (listForm.elements["pageLoaderInfo.pageNo"].value == listForm.elements["pageLoaderInfo.pageCount"].value)
	{
		alert("�Ѿ������һҳ��");
		return;
	}
	listForm.strAction.value="<%=Constant.PageControl.NEXTPAGE%>";
	listForm.submit();
}

function doRow()
{
	if(isEmpty()) return;
	if (isNaN(listForm.elements["pageLoaderInfo.rowPerPage"].value))
	{
		alert("����������");
		listForm.elements["pageLoaderInfo.rowPerPage"].focus();
		return;
	}
	else
	{
		if(parseInt(listForm.elements["pageLoaderInfo.rowPerPage"].value) <= 0)
		{
			alert("���ֱ��������");
			listForm.elements["pageLoaderInfo.rowPerPage"].focus();
			return;
		}
	}
	listForm.strAction.value="<%=Constant.PageControl.GOTOPAGE%>";
	listForm.submit();
}

function doJump()
{		
	if(isEmpty()) return;
	listForm.strAction.value="<%=Constant.PageControl.GOTOPAGE%>";
	listForm.submit();
}

function isEmpty()
{
	if (listForm.elements["pageLoaderInfo.pageCount"].value == 0)
	{
		alert("û�м�¼");
		return true;
	}
	return false;
}
</script>