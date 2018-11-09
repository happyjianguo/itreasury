<%--
 页面名称 ：v002.jsp
 页面功能 : 通知存款支取指令提交
 作    者 ：YuanHuang
 日    期 ：2006-10-31
 特殊说明 ：通知存款支取指令显示
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.ebank.util.OBHtml,
				com.iss.itreasury.ebank.util.OBConstant,
				com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>

<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<% 
  
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
  
	long lShowMenu = OBConstant.ShowMenu.YES;
  	String strTitle = "通知存款支取指令显示";
    try 
	{
        //用户登录检测 
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
        /* 显示文件头 */
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
    System.out.println("------------通知存款支取指令提交/v002.jsp-----------------------------");
 
 %>

<form name="frmV002" method="post" action="">
	<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title ><span class="txt_til2">通知存款支取查找</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE> 
	   
	<br/>
	 <TABLE border="0" width="100%" class="top">
		<TBODY>
			<tr>
			    <TD width="1%">&nbsp;</TD>
				<TD width="*%">
					<br><TABLE width="100%" id="flexlist"></TABLE><br>
				</TD>
				 <TD width="1%">&nbsp;</TD>
			</tr>
		</TBODY>
	</TABLE>
	
    <table align="" width=100% border="0" cellspacing="0" cellpadding="0" >
		<TR> 			 
		  <TD colspan="8"></TD>
			<td align="right">
			 <INPUT type="button" class="button1" name="butCal" onclick="javascript:doSettlement();" value=" 撤 消 " >&nbsp;&nbsp;
			<INPUT type="button" class="button1" name="butBack" onclick="javascript:doBack();" value=" 返 回 ">&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
		</TR>					 
	</TABLE>
 </td>
	  </tr>
	</table>
</form>   

<form name="fmodify">
	<input type="hidden" name="id">
</form>

<script>
$(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'ccheck', name : 'id', width : 50, sortable : true, align: 'center'},
			{display: '账户编号', name: 'saccountno', elType : 'link', elName : 'strAccountno', methodName : 'doModify("?")', width: 120, sortable: true, align: 'center'},
			{display: '客户名称',  name : 'clientName', width : 120, sortable : true, align: 'center'},
			{display: '存款单据号',  name : 'depositNo', width : 120, sortable : true, align: 'center'},	
			{display: '通知存款本金',  name : 'mAmount', width : 120, sortable : true, align: 'center'},
			{display: '支取金额',  name : 'amount', width : 120, sortable : true, align: 'center'},	
			{display: '通知人',  name : 'userName', width : 120, sortable : true, align: 'center'},	
			{display: '通知日期',  name : 'notifyDate1', width : 100, sortable : true, align: 'center'},
			{display: '状态',  name : 'statusID', width : 100, sortable : true, align: 'center'}
		],//列参数
		classMethod : 'com.iss.itreasury.settlement.transfixeddeposit.action.NotifyDepositAction.queryNotifyDeposit4ebank',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "saccountno" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		callback: 'toDisabled()'
	});
});

function getFormData() 
{
	return $.addFormData("frmV002","flexlist");
}

function doSearch()
{	
	$.gridReload("flexlist");
}

var flexlist = "flexlist";
function toDisabled(){
	$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
		if((n.value == "")||(n.value == 'undefined')){
			n.disabled = true;
		}
	});
}

function doModify( id )
{
	if( id==" "){
		return;
	}
	fmodify.id.value = id;
	fmodify.action = "<%=strContext%>/capital/notifyinform/control/c004.jsp";				
	showSending();
	fmodify.submit();
}

function doBack()
{
	location.href="<%=strContext%>/capital/notifyinform/view/v001.jsp";
}

function isChecked()
{	
	var isChecked = 0; 
	$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
		if(n.checked){
			isChecked = isChecked + 1;
		} 
	});
	return isChecked;
}

function doSettlement()
{
	var _isChecked = isChecked();
  	if(_isChecked == 0){
		alert("请选择要撤销的内容。");
		return false;
  	}
  	if(confirm("是否撤销？"))
  	{
		frmV002.action ="<%=strContext%>/capital/notifyinform/control/c003.jsp?";
		showSending();
		frmV002.submit();
   	}
}
function selectAll()
{	
	for (var i = 0; i < document.frmV002.elements.length; i++) 
	{
		if(document.frmV002.elements[i].name =="ccheck") 
		{
			document.frmV002.elements[i].checked = document.frmV002.cSelectAll.checked;
		}
	}
	return true;
}
</script>

 
 
 
 
 <%    
        
	OBHtml.showOBHomeEnd(out);	
}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
  
%>
<safety:resources />
<jsp:include page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/SignValidate.inc" %>