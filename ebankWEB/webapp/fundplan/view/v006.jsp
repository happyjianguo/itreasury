<%--
/*
 * �������ƣ�v006.jsp
 * ����˵�����ʽ�ƻ��걨�Ĳ�ѯ�����ʾҳ��   c005.jsp ��������> ��ǰҳ ��������> v007.jsp
 * �������ߣ�ylguo
 * ������ڣ�2008-10-21
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="java.util.Iterator,com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier,java.text.DateFormat,java.text.SimpleDateFormat"%>



<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

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
		OBHtml.showOBHomeHead(out, sessionMng, strTitle,
		OBConstant.ShowMenu.YES);
%>
<form name="frm" method="post" action="">
	
	<input type="hidden" name="sNextSuccessPage" value="">
<table width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�ʽ�ƻ���ѯ���</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
  
  <tr> 
    <td width="100%" valign="bottom"> <br>
		<form name="frmV202" method="post" action="">
		
        <table width="100%" border="1" align="" class=normal>
               <thead>
                 <tr> 
                    <TD width="20%" height="25"  noWrap><DIV align=center>�ʽ�ƻ����</DIV></TD>
                    <td noWrap><div align="center">��ʼ����</div></td>
                    <td noWrap><div align="center">��������</div></td>
                    <td noWrap><div align="center">¼������</div></td>
                    <td noWrap><div align="center">¼����</div></td>
                    <td noWrap><div align="center">��������</div></td>
                    <td noWrap><div align="center">������</div></td>
                    
                    <td noWrap><div align="center">״̬</div></td>
                  </tr>
              </thead>
       <%
       		Collection collection = (Collection)request.getAttribute("capitalResults");
       		if(collection.isEmpty()){
       	%>
				  <tr> 
                    <TD height=25 colspan="8" noWrap align="center">
                     �Բ���û���ҵ����ϲ�ѯ�������ʽ�ƻ��걨��
                    </TD>
                  </tr>	
	   <%
	   		}else{
	   			 Iterator it = collection.iterator();
	   			 while(it.hasNext()){
	   			 	CapitalPlanInfo info = new CapitalPlanInfo();
	   			 	info = (CapitalPlanInfo)it.next();
	   			     
	   %>		
	   		<tr> 
                    <TD height=25 noWrap align="center">
                    <a href="../view/v007.jsp?&id=<%=info.getId()%>&modelId=<%=info.getModelId()%>&statusId=<%=info.getStatusId()%>&clientName=<%=info.getClientName()%>&startDate=<%=DataFormat.getDateString(info.getStartdate())%>&endDate=<%=DataFormat.getDateString(info.getEnddate())%>&cpCode=<%=info.getCpCode()%>&inputerId=<%=info.getInputuserId() %>&isCheckedP=<%=info.getModifyuserId() %>"><%=info.getCpCode() %></a></TD>
                    <TD noWrap align="center" ><%=DataFormat.getDateString(info.getStartdate()) %></TD>
                    <TD noWrap align="center" ><%=DataFormat.getDateString(info.getEnddate()) %></TD>
                    <td noWrap><div align="center"><%=DataFormat.getDateString(info.getInputdate()) %></div></td>
                    <td noWrap><div align="center"><%=info.getInputerName() %></div></td>
                    <td noWrap><div align="center"><%=DataFormat.getDateString(info.getModifydate()) %></div></td>
                    <td noWrap><div align="center"><%=info.getModifyerName() %></div></td>
                    <TD noWrap align="center" ><%=OBConstant.SettInstrStatus.getName(info.getStatusId()) %></TD>
                  </tr>	
	   		
	   		   
	   		
	   	<%
	   		}
	   	} %>	
	   		
	   
	 </table>
	  </form>
    </td>
  </tr>

 
</table>
	<table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td width="476">
				<%="&nbsp;"%>
			</td>
			
			<td width="60">
				<div align="right">
					<input type="Button" class="button1" value=" �� �� " width="46" height="18" onclick="javascript:toBack()">
				</div>
			</td>
			
		</tr>
	</table>
	<br>
</table>

<script language="javascript">
    //����
	function toBack(){
		window.location = "../view/v005.jsp";
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
