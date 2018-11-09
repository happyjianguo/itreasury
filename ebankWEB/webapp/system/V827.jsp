<%--
/*
 * �������ƣ�V827.jsp
 * ����˵����ϵͳ����-�տ�����޸�����ҳ��
 * �������ߣ�����
 * ������ڣ�2003��10��20��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%
	//�̶�����
	String strTitle = null;
%>

<%
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
        // �û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//��ʾ�ļ�ͷ
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String strContext = request.getContextPath();//http://xxxx/../cpob
		//��������
		ClientCapInfo rf = (ClientCapInfo)request.getAttribute("ClientCapInfo");
		String strBankNo = com.iss.itreasury.settlement.util.NameRef.getBankAccountNOByCurrenctAccountID(rf.getPayeeAccoutNO());
			rf.setPayeeBankNO("");
		
%>

<safety:resources />
<form name="form1" method="post">
<%-- C823.jsp��Ҫ���� --%>
<input type="hidden" name="txtIsCPF"  value="true">
<%-- C825.jsp��Ҫ���� --%>
<input type="hidden" name="txtIDCheckbox" value="<%if (rf != null) out.print(rf.getID()); %>">

    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�տ���ϣ��޸�</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  
<br/>
 
           
            <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
<tr>
              <td  height="25" width="1"></td>
                
      <td height="25" width="4" ></td>
                
      <td height="25" width="129" class="graytext"  align="left"><font color="#FF0000">* </font>�˺ţ�</td>
                <td height="25" colspan="2" class="graytext" >
				<%-- C821.jsp��Ҫ���� --%>
                  <input class="box" type="text" name="txtPayeeBankNO" size="20" value="<%
                  if (rf != null && rf.getPayeeAccoutNO() != null){
                  NameRef nameref = new NameRef();
                  out.print(nameref.getNoLineAccountNo(rf.getPayeeAccoutNO())+rf.getPayeeBankNO());
                  } 
                  %>" >
                </td>
              <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
<tr>
                <td  height="25" width="1"></td>
                
      <td height="25" width="4" ></td>
                
      <td height="25" width="129" class="graytext"  align="left">&nbsp;&nbsp;���������ƣ�</td>
                <td height="25" colspan="2" class="graytext" >
                  <input class="box" type="text" name="textfield2222222" size="32" value="<%=Env.getClientName()%>"  disabled>
                </td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="6"></td>
              </tr>
            </table>
            <br>       
            <table width=100% border="0" cellspacing="0" cellpadding="0" align="">	  
			  <tr>
                <td  colspan="3">
                  <div align="right">
				  <!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSelectForm1();"-->
				 <input type="button" name="Savev00204" value=" �� �� " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doSubmitForm1();" <% }%>>&nbsp;&nbsp;
				 <input type="button" name="Submitv00204" value=" ɾ �� " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doDeletForm1();" <% }%>>&nbsp;&nbsp;
				 <input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="doBackForm1();">&nbsp;&nbsp;
				  </div>
                </td>
              </tr>
            </table>
  </td>
  </tr>
</table>
</form>

<script language="JavaScript" src="/webob/js/Control.js"></script>

<script language="JavaScript">
function doCheckForm1()
{
	if (form1.txtPayeeBankNO.value == "")
	{
		alert("�˺Ų���Ϊ�գ���¼��");
		form1.txtPayeeBankNO.focus();
		return false;
	}
	return true;
}

function doSubmitForm1()
{
	if (doCheckForm1())
	{
		form1.action = "<%= strContext %>/system/C821.jsp?flag=saved";
		showSending(); 
		form1.submit();
	}
}
function doDeletForm1()
{
	if(confirm("�Ƿ�ɾ����"))
	{
	  form1.action = "<%= strContext %>/system/C825.jsp?flag=deleted";
	  showSending(); form1.submit();
	}
}
function doBackForm1()
{
	form1.action = "<%= strContext %>/system/C823.jsp";
	showSending(); form1.submit();
}
 firstFocus(form1.txtPayeeBankNO);/*��һ���񽹵�*/
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch(Exception e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
	
%>

<%@ include file="/common/SignValidate.inc" %>
