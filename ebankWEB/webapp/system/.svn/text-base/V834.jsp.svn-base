<%--
/*
 * 程序名称：V834.jsp
 * 功能说明：系统管理-收款方资料查询输出页面（外部账户）
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//固定变量
	String strTitle = null;
%>

<%
     /* 用户登录检测与权限校验及文件头显示 */
    try 
	{
    	//分页信息
    	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
    	
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
        Collection collection = (Collection)request.getAttribute("collection");
		Iterator iterator = null;
		if (collection != null)
		{
            iterator = collection.iterator();
		}
%>

<form name="form1" method="post" >
<input type="hidden" name="txtIsCPF" value="false">
<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">  
    <table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">往来账户列表</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>


      <table width="774" border="0" cellspacing="0" cellpadding="0" align="">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
      </table>
	<TABLE border="0" width="100%" class="top">
				<TBODY>
					<tr>
					    <td width="1%">&nbsp;</td>
						<TD width="*%">
							<br><TABLE width="100%" id="flexlist"></TABLE><br>
						</TD>
					    <td width="1%">&nbsp;</td>
					</tr>
				</TBODY>
			</TABLE>	
	  
      <br>
      <table width=100% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="664" height="14">
            <div align="right"><input type="button" name="Submitv002041" value=" 新 增 " class="button1" onClick="javascript:doAdd();" >&nbsp;</div>
          </td>
          <td width="63" height="14"  valign="top">
            <div align="right">
			<input type="button" name="Submitv00204" value=" 删 除 " class="button1"  
			style="cursor:hand" onClick="javascript:doDelet();"
			>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </td>
          <!-- 
          <td width="63" height="14" valign="top">
            <div align="right" >
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1" onclick="javascript:doGoback();">&nbsp;
			</div>
          </td>
           -->
        </tr>
      </table>
       </td>
  </tr>
</table>
</form>

<%--用于提交--%>
<form name="form2" method="post" action="<%= strContext %>/system/C826.jsp">
	<input type="hidden" name="txtID" value="">
	<input type="hidden" name="txtIsCPF" value="false">
</form>

<script language="JavaScript">
var flexlist = "flexlist";
$(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
        	{elType : 'checkbox', elName : 'txtIDCheckbox', name : 'id', width : 40, sortable : true, align: 'center'},
        	{display: '收款方名称',  name : 'spayeename', width : 110, sortable : true, align: 'center'},
        	{display: '收款方账号', name: 'spayeeacctno', elType : 'link', elName : 'batchno', methodName : 'doLink("?")', width: 110, sortable: true, align: 'center'},
        	{display: '汇入行名称',  name : 'spayeebankname', width : 220, sortable : true, align: 'center'},
        	{display: '汇入省',  name : 'spayeeprov', width : 90, sortable : true, align: 'center'},
        	{display: '汇入市',  name : 'spayeecity', width : 90, sortable : true, align: 'center'},
        	{display: '汇入行联行号',  name : 'SPAYEEBANKEXCHANGENO', width : 110, sortable : true, align: 'center'},
        	{display: '汇入行机构号',  name : 'SPAYEEBANKORGNO', width : 110, sortable : true, align: 'center'},
        	{display: '汇入行CNAPS号',  name : 'SPAYEEBANKCNAPSNO', width : 110, sortable : true, align: 'center'}
		],//列参数
		title:'往来账户列表',
		classMethod : 'com.iss.itreasury.ebank.obsystem.action.OBAbstractSettingAction.queryClientCapInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		usepager : false,
		userFunc : getFormData,
		height : 270
	});
	
});
function getFormData() 
{
	return $.addFormData("form1","flexlist");
}
 function doAdd(){
 	window.location.href="<%= strContext %>/system/V820.jsp";
 }
 function doLink(id){
 	form2.txtID.value = id;
 	form2.submit();
 }
 function doCheck()
 {
  	var isCheck = false;
	$.each($("#" + flexlist + " input[type='checkbox'][name='txtIDCheckbox']"),function(i,n){
		if(n.checked == true){
			isCheck = true;
		} 
	});
	if (!isCheck)
	{
		alert("请选择要删除的记录");
		return false;
	}
	return true;
 }
 
 function subcheck(obj){
 	    var subcheckbox = document.getElementsByName("txtIDCheckbox");
 	    var j=0;
 	    for(var i=0;i<subcheckbox.length;i++){
 	    	if(obj == subcheckbox[i]){
 	    		subcheckbox[i].checked = obj.checked;
 	    	}
 	    }
 	    for(var i=0;i<subcheckbox.length;i++){
 	   		if(subcheckbox[i].checked == false){
 	   		document.form1.checkAll.checked=false;
 	   		}else{
 	   			j++;
 	   		}
 	    }
 	    if(j == subcheckbox.length){
 	    	document.form1.checkAll.checked=true;
 	    }
 }
 
 
 
 function doDelet()
 {
 	if (doCheck())
	{
		if(confirm("是否删除？"))
	    {
		   form1.action = "<%= strContext %>/system/C825.jsp?flag=deleted";
		   showSending(); form1.submit();
		}
	}
 }
 
 function doGoback(){
 	form1.action="<%= strContext %>/system/V820.jsp";
	showSending(); form1.submit();
 }
 
 function checkall()
 {
 	for( var c = 0;c < document.form1.elements.length; c++)
	{
	    if(form1.elements[c].name == "txtIDCheckbox" )
	    {
	       form1.elements[c].checked = document.form1.checkAll.checked;
	    }
	}
 }
 </script>
<%
		//显示文件尾
		OBHtml.showOBHomeEnd(out);

		//显示保存操作成功
		String str = (String)request.getAttribute("BCCG");
		if(str!=null&&str.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('保存成功')");
			out.println("</script>");
		}
		
		//显示删除操作成功
		String strdel = (String)request.getAttribute("SCCG");
		if(strdel!=null&&strdel.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('删除成功')");
			out.println("</script>");
		}
		
		//显示提交操作成功
		String stradd = (String)request.getAttribute("TJCG");
		if(stradd!=null&&stradd.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('提交成功')");
			out.println("</script>");
		}
	
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
	
%>

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
