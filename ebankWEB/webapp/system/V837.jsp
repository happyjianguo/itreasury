<%--
/*
 * �������ƣ�V827.jsp
 * ����˵����ϵͳ����-�տ�����޸�����ҳ��
 * �������ߣ�����
 * ������ڣ�2003��10��20��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.settlement.util.NameRef,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"

%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

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
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<safety:resources />

<form name="form1" method="post">
<%-- C823.jsp��Ҫ���� --%>
<input type="hidden" name="txtIsCPF"  value="false">
<%-- C825.jsp��Ҫ���� --%>
<input type="hidden" name="txtIDCheckbox" value="<%if (rf != null) out.print(rf.getID()); %>">

    
    
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�տ���ϣ��޸�</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>

            <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
                            <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                
      <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>�տ�˺ţ�</td>
                <td height="25" width="220" align="left" class="graytext" >
                  <input class="box" type="text" name="txtPayeeAccoutNO" size="20" maxlength="20" onfocus="nextfield ='txtPayeeProv';" value="<%if (rf != null && rf.getPayeeAccoutNO()!=null) out.print(NameRef.getNoLineAccountNo(rf.getPayeeAccoutNO())); %>" >
                </td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
			  <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                
      <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>

      <td height="25" width="220" class="graytext" > <input type="text" class="box" name="txtPayeeName" onfocus="nextfield ='txtPayeeAccoutNO';" size="32" maxlength="50" value="<%if (rf != null && rf.getPayeeName()!=null) out.print(rf.getPayeeName()); %>" ></td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>����أ�</td>
      			<td height="25" width="220" class="graytext" nowrap> 
      				<input type="text" class="box" name="txtPayeeProv" onfocus="nextfield ='txtCity';" size="10"  maxlength="15" value="<%if (rf != null && rf.getPayeeProv()!=null) out.print(rf.getPayeeProv()); %>" >
			        	ʡ
			        <input type="text" class="box" name="txtCity" size="10" onfocus="nextfield ='txtPayeeBankName';" maxlength="15" value="<%if (rf != null && rf.getCity()!=null) out.print(rf.getCity()); %>"   >
			        	�У��أ�
		        </td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"> </td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>���������ƣ�</td>
                <td height="25" width="220" class="graytext" >
                  <input type="text" class="box" name="txtPayeeBankName" onfocus="nextfield ='txtPayeeBankCNAPSNO';" size="32"  maxlength="50" value="<%if (rf != null && rf.getPayeeBankName()!=null) out.print(rf.getPayeeBankName()); %>" >
                </td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"> </td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;������CNAPS�ţ�</td>
                <td height="25" width="220" class="graytext" >
            		<input type="text" class="box" name="txtPayeeBankCNAPSNO" onfocus="nextfield ='txtPayeeBankOrgNO';" size="32"  maxlength="50" value="<%if (rf != null && rf.getSPayeeBankCNAPSNO()!=null) out.print(rf.getSPayeeBankCNAPSNO()); %>" >
                	<a href="javascript:doLink()"><font color="red">CNAPS�ż���</font></a>
                </td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"> </td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;�����л����ţ�</td>
                <td height="25" width="220" class="graytext" >
                  <input type="text" class="box" name="txtPayeeBankOrgNO" onfocus="nextfield ='txtPayeeBankExchangeNO';" size="32"  maxlength="50" value="<%if (rf != null && rf.getSPayeeBankOrgNO()!=null) out.print(rf.getSPayeeBankOrgNO()); %>" >
                </td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"> </td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;���������кţ�</td>
                <td height="25" width="220" class="graytext" >
                  <input type="text" class="box" name="txtPayeeBankExchangeNO" onfocus="nextfield ='';" size="32"  maxlength="50" value="<%if (rf != null && rf.getSPayeeBankExchangeNO()!=null) out.print(rf.getSPayeeBankExchangeNO()); %>" >
                </td>
                <td  height="25" width="100"></td>
              </tr>
              
              <tr>
                <td  colspan="4" ></td>
                  <td align="right" nowrap>
					 <input type="button" name="Savev00204" value=" �� �� " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doSubmitForm1();" <% }%>>&nbsp;&nbsp;
					 <input type="button" name="Submitv00204" value=" ɾ �� " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doDeletForm1();" <% }%>>&nbsp;&nbsp;
					 <input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="doBackForm1();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  </td>
              </tr>
              <tr><td height="5"></td></tr>
            </table>
            <br>

       
 </td>
  </tr>
 
</table>
</form>

<script language="JavaScript">

function doLink()
{    
	if(form1.txtPayeeProv.value.length <= 0)
	{
		alert("������ʡ�ݣ�");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+form1.txtPayeeBankName.value+'&province='+form1.txtPayeeProv.value+'&city='+form1.txtCity.value+'&recBankCode='+form1.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+form1.txtPayeeBankName.value+'&bankName=txtPayeeBankName', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}

function doSubmitForm1()
{ 
	if (form1.txtPayeeAccoutNO.value.Trim() == "")
	{
		alert("�տ��˺Ų���Ϊ�գ���¼��");
		form1.txtPayeeAccoutNO.focus();
	  return false;
	}
	if (form1.txtPayeeName.value.Trim() == "")
	{
		alert("�տ���Ʋ���Ϊ�գ���¼��");
		form1.txtPayeeName.focus();
		return false;
	}
	if (form1.txtPayeeProv.value.Trim() == "")
	{
		alert("����� ʡ���Ʋ���Ϊ�գ���¼�룡");
		form1.txtPayeeProv.focus();
		return false;
	}
	var payeeNameLength = form1.txtPayeeName.value.replace(/[^\x00-\xff]/g,'**').length;
	if(payeeNameLength>80)
	{
		alert("�տ���Ƴ��Ȳ��ܴ���40������(80���ֽ�)!");
		form1.txtPayeeProv.focus();
		return false;
	}
	if (form1.txtCity.value.Trim() == "")
	{
		alert("����� �У��أ����Ʋ���Ϊ�գ���¼��");
		form1.txtCity.focus();
		return false;
	}
	if (form1.txtPayeeBankName.value.Trim() == "")
	{
		alert("���������Ʋ���Ϊ�գ���¼��");
		form1.txtCity.focus();
		return false;
	}
	form1.action = "<%= strContext %>/system/C821.jsp?flag=saved";
	showSending(); form1.submit();
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
 firstFocus(form1.txtPayeeName);/*��һ���񽹵�*/
 setSubmitFunction("doSubmitForm1()");
 setFormName("form1");

/*
 * ȥ��ǰ��ո�
 */
String.prototype.Trim = function()  
{  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}

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

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
