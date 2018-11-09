<%--
/**
 * �������ƣ�V855.jsp
 * ����˵����ϵͳ����-�˻�����
 * �������ߣ�����
 * ������ڣ�2003��9��5��
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
//�̶�����
String strTitle = "[�˻�����]";
String strContext = request.getContextPath();
try
{
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	/**
	* isLogin start
	*/
	//��¼���
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//�ж��Ƿ���Ȩ��
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
	OB_UserInfo info = null;
    ArrayList list = (ArrayList) request.getAttribute("userList");
%>
<form name="form1" method=post >
<input type="hidden" name="UserID" value="">
  <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
  <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�˻�����</span></td>
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
	     </td>
     </tr>
    </table>
</form>
 <script language="JavaScript">
 $(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
        	{display: '�û�����',  name : 'sLoginNo', width : 210, sortable : true, align: 'center'},
        	{display: '��¼����',  name : 'ncurrencyId', width : 210, sortable : true, align: 'center'},
        	{display: '����',  name : 'inputusername', width : 210, sortable : true, align: 'center'},
        	{display: '�����ͻ�',  name : 'dtInput', width : 210, sortable : true, align: 'center'},
        	{display: '�˻���Ȩ', elType : 'button', elName : 'changePW', methodName : 'accountAuthorize("?")', name : 'id', width : 200, sortable : false, align: 'center'}
		],//�в���
		title:'�˻�����',
		classMethod : 'com.iss.itreasury.ebank.privilege.action.OB_UserAction.queryUser4Authorized',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
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
function accountAuthorize(UserId)
{
	form1.action ="<%= strContext %>/system/sdc/C853.jsp?method=AccountAuthorize";
	form1.UserID.value = UserId;
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