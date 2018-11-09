<%--
/*
 * 程序名称：batchck001-v.jsp
 * 功能说明：批量复核查询页面
 * 作　　者：菅中尉
 * 完成日期：2007年04月20日
 */   
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo,
				com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao,
				java.util.*,
				com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%!/* 标题固定变量 */
	String strTitle = "[批量复核]";%>
<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	/* 实例化信息类 */
	//实体
	
	String strStartDate = null;//上一个页面传来的开始日期
	
	String strEndDate = null;//上个页面传来的结束日期
	

	
	FinanceInfo info = new FinanceInfo();
	List infoList = null;
	//链接进入页面
	String strURL = null;
	//成功返回页面
	String strSuccessURL = "batchck004-v.jsp";
	//失败返回页面
	String strFaileURL = "batchck004-v.jsp";
	//查询类
	/* 用户登录检测与权限校验及文件头显示 */
	try {
		//用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		
		if(request.getAttribute("strStartDate")!=null)
		{
			
			strStartDate =(String)request.getAttribute("strStartDate");
			System.out.println("strStartDate============="+strStartDate);
		}
		
		
		if(request.getAttribute("strEndDate")!=null)
		{
			
			strEndDate = (String)request.getAttribute("strEndDate");			
		}
		
		
		if(request.getAttribute("infoList")!=null)
		{
			//得到结果并显示结果集合
			infoList = (List)request.getAttribute("infoList");
		}
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<html>
<head>
</head>

<safety:resources />

<body>
<form method="post" name="frm">
<!--判断是复核还是取消复核字段,此处为未复核 -->
<input type="hidden" name="sStatus" value="1"/>
<input type="hidden" name="userID" value="<%=sessionMng.m_lUserID %>"/>
<input type="hidden" name="clientID" value="<%=sessionMng.m_lClientID %>"/>
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">批量付款复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

<br/>

<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
	<tr><td></td></tr>
	<tr>
		
		<td class="graytext" width="120" align="right">起始日期：</td>
		<td>
		<%  Timestamp ts=Env.getSystemDateTime();
		System.out.println("_______________ts____________________"+ts);		
		 %>
		<fs_c:calendar 
			          	    name="strStartDate"
				          	value="" 
				          	properties="nextfield ='strEndDate'" 
				          	size="20"/>
		<script>
	          		$('#strStartDate').val('<%=(request.getAttribute("strStartDate")!=null&&!request.getAttribute("strStartDate").equals(""))?request.getAttribute("strStartDate"):ts.toString().substring(0,10)%>');
	    </script>
		</td>
	</tr>
	<tr>
		
		<td class="graytext" width="120" align="right">结束日期：</td>
		<td>
			<fs_c:calendar 
          	    name="strEndDate"
	          	value="" 
	          	properties="nextfield ='submit1'" 
	          	size="20"/>
	          	<script>
	          		$('#strEndDate').val('<%=(request.getAttribute("strEndDate")!=null&&!request.getAttribute("strEndDate").equals(""))?request.getAttribute("strEndDate"):ts.toString().substring(0,10)%>');
	    </script>
		</td>
		
	</tr>
	<tr>
		<td colspan="7"></td>
		<td width="15">
		<input class="button1" name="submit1" type="button" value=" 查 询 "   onclick="Javascript:query();" onKeyDown="Javascript:query();"> 
		</td>
		<td></td>
	</tr>
	<tr><td></td></tr>
</table>
<br/>

	
<p>&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
	
<table class="top" width="100%">
    <tbody>
     <tr>
        <td width="1%">&nbsp;</td>
        <td width="*%">
		   <br><TABLE width="100%" id="flexlist"></TABLE><br>
		</td>
		<td width="1%">&nbsp;</td>
		</tr>
	</tbody>
	</table>

</td></tr>
</table>
</form>


<script language="javascript">
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '批次号', name: 'sbatchno', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?")', width: 200, sortable: true, align: 'center'},
			{display: '提交人',  name : 'sname', width : 200, sortable : true, align: 'center'},
			{display: '提交时间',  name : 'dtconfirm', width : 200, sortable : true, align: 'center'},
			{display: '总金额',  name : 'mamount', width : 200, sortable : true, align: 'center'},
			{display: '总笔数',  name : 'ncount', width : 200, sortable : true, align: 'center'}
		],//列参数
		title:'待复核批次',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryBatchCheckInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sbatchno" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData
	});
	
});

function getFormData() 
{
	return $.addFormData("frm","flexlist");
}

function query()
{	
	if(validate(frm)){
		$.gridReload("flexlist");
	}
}
function validate()
{
	var ret = true;
	if(document.all("strStartDate").value == '')
	{
		alert("起始日期不能为空，请录入");
		document.frm.strStartDate.focus();
		return false;
	}
	
	if(document.all("strEndDate").value == '')
	{
		alert("结束日期不能为空，请录入");
		document.frm.strEndDate.focus();
		return false;
	}
	if(document.all("strStartDate").value>document.all("strEndDate").value)
	{
		alert("起始日期不能大于结束日期");
		document.frm.strStartDate.focus();
		return false;
	}
	return ret;
}
function doLink(startDate,endDate,batchNo){
	var url = "batchck004-v.jsp?sStatus=1&strStartDate="+startDate+"&strEndDate="+endDate+"&sbatchno=" + batchNo
					+ "&strSuccessURL=batchck004-v.jsp&strFaileURL=batchck004-v.jsp";
	window.location.href = url;
}
</script>
<script language="javascript">
  /* 页面焦点及回车控制 */
    firstFocus(frm.strStartDate);
    //setSubmitFunction("addme()");
    setFormName("frm");
</script>
<% 
	/*if(request.getAttribute("infoList")!=null)
		{
			//得到结果并显示结果集合
			out.print("<script language = 'javascript'>");
			out.print("document.all('result').style.display='block';");
			out.print("</script>");
		}*/
%>
</body>
</html>

<%
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/SignValidate.inc" %>