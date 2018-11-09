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
if(key == null) key = "";//赋为“”为了在页面中不显示为null

PageLoader pageLoader = sessionMng.getPageLoader(key); 
%>

<form name="listForm" method="post" action="pagecontrol.jsp">
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
	<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
	<input name="_pageLoaderKey"  type="hidden" value="<%=key%>">
	<input type="hidden" name="pageLoaderInfo.pageCount" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageCount()%>">
	<input type="hidden" name="pageLoaderInfo.rowCount" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowCount()%>">

<a href="javascript:doPrevious()">上页</a> 
| 第<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageNo()%>
/<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getPageCount()%>页 
|<a href="javascript:doNext()">下页</a> 
| 共<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowCount()%>行 
| 每页 <input type="text" size="4" maxlength="3" name="pageLoaderInfo.rowPerPage" value="<%=(pageLoader==null)?0:pageLoader.getPageLoaderInfo().getRowPerPage()%>" onKeyDown="beforeDoRow()"> 行
<input type="button" class="button" value="go" onclick="doRow()"/> 
| 转到第 
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
</select> 页
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
		alert("已经是第一页了");
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
		alert("已经是最后一页了");
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
		alert("请输入数字");
		listForm.elements["pageLoaderInfo.rowPerPage"].focus();
		return;
	}
	else
	{
		if(parseInt(listForm.elements["pageLoaderInfo.rowPerPage"].value) <= 0)
		{
			alert("数字必须大于零");
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
		alert("没有记录");
		return true;
	}
	return false;
}
</script>