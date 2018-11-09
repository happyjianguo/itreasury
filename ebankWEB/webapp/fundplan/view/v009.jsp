<%--
/*
 * �������ƣ�v009.jsp
 * ����˵�����ʽ�ƻ���ѯ�����ʾҳ��   c008.jsp ��������> v009.jsp��������> v010.jsp
 * �������ߣ�ylguo
 * ������ڣ�2008-12-04
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,com.iss.itreasury.util.Constant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<%
String strContext = request.getContextPath();
%>
<%
	//�������
	String strTitle = null;
	try {
		/* �û���¼��� */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		/* �ж��û��Ƿ���Ȩ�� */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		//��ʾ�ļ�ͷ
		OBHtml.showOBHomeHead(out, sessionMng, strTitle,OBConstant.ShowMenu.YES);
		
		Object[] queryResults = null;	
		queryResults = (CapitalPlanInfo[])request.getAttribute(Constant.PageControl.SearchResults);
		
%>
<form name="frm" method="post" action="">
<input name="strAction" type="hidden" value="">
<input name="p_SuccessPageURL" type="hidden" value="">
<input name="p_FailPageURL" type="hidden" value="">
<input name="strContextRoad" type="hidden" value="<%=strContext %>">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�ʽ�ƻ���ѯ</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
 		<br/>
        <table width="100%" border="1" class=normal>
               <thead>
                  <tr> 
                    <TD width="20%" height="25"  noWrap><DIV align=center>�ʽ�ƻ����</DIV></TD>
                    <td noWrap><div align="center">��ʼ����</div></td>
                    <td noWrap><div align="center">��������</div></td>
                    <td noWrap><div align="center">¼������</div></td>
                    <td noWrap><div align="center">¼����</div></td>
                    <td noWrap><div align="center">��������</div></td>
                    <td noWrap><div align="center">������</div></td>
                    <td noWrap><div align="center">��������</div></td>
                    <td noWrap><div align="center">������</div></td>
                    <td noWrap><div align="center">״̬</div></td>
                  </tr>
              </thead>
       <%
     		
			if(queryResults == null || queryResults.length <= 0){
       	%>
				  <tr> 
                    <TD height=25 colspan="10" noWrap align="center">
                     �Բ���û���ҵ����ϲ�ѯ�������ʽ�ƻ��걨��
                    </TD>
                  </tr>	
	   <%
	   		}
			if(queryResults != null && queryResults.length>0)
			{
	   			 for( int i=0; queryResults != null && i<queryResults.length; i++ )
				 {
	   			 	CapitalPlanInfo info = (CapitalPlanInfo)queryResults[i];
	   %>		
	   		<tr> 
                    <TD height=25 noWrap align="center">
                    <a href="#" onclick="showDetail(<%=info.getId()%>)"><%=info.getCpCode() %></a></TD>
                    <TD noWrap align="center" ><%=DataFormat.getDateString(info.getStartdate()) %></TD>
                    <TD noWrap align="center" ><%=DataFormat.getDateString(info.getEnddate()) %></TD>
                    <td noWrap><div align="center"><%=DataFormat.getDateString(info.getInputdate()) %></div></td>
                    <td noWrap><div align="center"><%=info.getInputerName() %></div></td>
                    <td noWrap><div align="center"><%=DataFormat.getDateString(info.getModifydate()) %></div></td>
                    <td noWrap><div align="center"><%=info.getModifyerName() %></div></td>
                    <td noWrap><div align="center"><%=DataFormat.getDateString(info.getAuditingdate()) %></div></td>
                    <td noWrap><div align="center"><%=info.getAuditerName() %></div></td>
                    <TD noWrap align="center" ><%=OBConstant.SettInstrStatus.getName(info.getStatusId()) %></TD>
                  </tr>	
	   	<%
	   		}
	   	} %>	
	 </table>
	</td>
</tr>
</table>
</form>
	<table width="80%">
	<!-- ��ҳ�ؼ� -->
		<TR>
			<td width="100%" align="right">
				<table width=100% cellspacing="3" cellpadding="0" class="SearchBar" height="19" >
					  <TR>
				           <TD height=20 width=100% align="right">
				             <DIV align=right>
							    <jsp:include page="/pagenavigator.jsp"  />
							 </DIV>
						   </TD>
					  </TR>
			     </table> 
		     </td>
	     </TR> 
	</table>
	<table width=80% border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td>&nbsp;</td>
			<td width="60" >
				<div align="right">
					<input type="Button" class="button1" name = "back" value=" ���� " width="46" height="18" onclick="javascript:toBack()">
				</div>
			</td>
		</tr>
	</table>


<script language="javascript">
    //����
	function toBack()
	{	var sss = frm.strContextRoad.value;
		window.location = sss+"/fundplan/view/v008.jsp";
	}
	
	function showDetail(capitalPlanId)
	{
		var sss = frm.strContextRoad.value;
		// alert(sss);
		frm.action = sss+"/fundplan/control/c009.jsp?capitalPlanId=" + capitalPlanId;
		frm.strAction.value = "showdetail";
		frm.p_SuccessPageURL.value = "../view/v010.jsp";
		frm.p_FailPageURL.value = "../view/v008.jsp";
		showSending();
		frm.submit();
	}
    
</script>

	<%
			/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		} catch (IException ie) {
			OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
			1);
		}
	%>
