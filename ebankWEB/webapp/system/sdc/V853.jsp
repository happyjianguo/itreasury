<%--
/**
 * 程序名称：V853.jsp
 * 功能说明：系统管理-用户管理
 * 作　　者：刘琰
 * 完成日期：2003年9月5日
 */
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="getData" class="com.iss.itreasury.system.privilege.util.GetData" scope="page"/>
<%@ page import="java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.system.bizlogic.EBankbean,
				com.iss.itreasury.ebank.privilege.dataentity.*,
				com.iss.itreasury.ebank.privilege.bizlogic.*,
				com.iss.itreasury.ebank.privilege.dao.*,
				com.iss.itreasury.ebank.privilege.util.*,
                 com.iss.itreasury.ebank.util.*,
                 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
response.setHeader("Cache-Control","no-stored");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%
//固定变量
String strTitle = "[用户管理]";
String strContext = request.getContextPath();
try
{
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	/**
	* isLogin start
	*/
	//登录检测
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}
	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    Collection c = (Collection) request.getAttribute("UsersOfClient");
%>
<form name="form1" method=post >
	<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
  <table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用户管理</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>  
			<br/>
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
	          <td width="376">
	            <div align="right"></div>
	          </td>
	          <td width="305">
	            <div align="right"></div>
	          </td>
	          <td >
	            <div align="right" width="500">
				<input type="button" name="Query" value=" 新 增 " class="button1" onClick="javascript:add();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
	          </td>
	        </tr>
	      </table>
	     </td>
     </tr>
    </table>
</form>
 <script language="JavaScript">
 $(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
        	{display: '用户名称', name: 'sName', elType : 'link', elName : 'batchno', methodName : 'view("?")', width: 180, sortable: true, align: 'center'},
        	{display: '登录名称',  name : 'sLoginNo', width : 180, sortable : true, align: 'center'},
        	{display: '币种',  name : 'ncurrencyId', width : 180, sortable : true, align: 'center'},
        	{display: '录入人',  name : 'inputusername', width : 180, sortable : true, align: 'center'},
        	{display: '录入日期',  name : 'dtInput', width : 180, sortable : true, align: 'center'},
        	{display: '更改密码', elType : 'button', elName : 'changePW', methodName : 'doChangePW("?")', name : 'id', width : 150, sortable : false, align: 'center'}
		],//列参数
		title:'用户管理',
		classMethod : 'com.iss.itreasury.ebank.privilege.action.OB_UserAction.queryUser4Mng',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});
function getFormData() 
{
	return $.addFormData("form1","flexlist");
}
function doChangePW(id){
	window.open('../V855.jsp?UserID='+id,'_blank','height=210,width=330');
}
function add()
{
	form1.action = "<%= strContext %>/system/sdc/V856.jsp";
	form1.submit();
}
function view(UserID)
{
	form1.action = "<%= strContext %>/system/sdc/C853.jsp?method=toModify&UserID="+UserID;
	form1.submit();
}
</script>
<%
}
catch (Exception e)
{
	OBHtml.showExceptionMessage(out,sessionMng, (IException) e, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
 %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc" %>