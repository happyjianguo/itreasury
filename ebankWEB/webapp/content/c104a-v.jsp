<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obattach.bizlogic.*,
	com.iss.itreasury.loan.contractcontent.dao.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.ebank.obattach.dataentity.*,
	com.iss.itreasury.util.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	try
	{
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	  
  	   String strContent=(String)request.getAttribute("sContent");
  	   System.out.println(strContent);
   	   String[] arrContent=new String[16];
   	   if (strContent.length() > 0)
	   {
			int nIndex; //","������λ��
			int nTmp = 0;
			nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}

  	   int iCount=0;
  	   long lContentID=-1;
  	   String pageName="";
  	   long pageNo=4;
  	   String control="";
  	     	   
  	   		//ȡ��������
		String strTemp = "";
		strTemp = (String)request.getAttribute("lContentID");
		
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lContentID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContentID = -1;
			}
		}
  	    strTemp = (String)request.getAttribute("control");
		if (strTemp != null && !strTemp.equals(""))
		{
			control=strTemp;
		}  	   
		boolean isRead=false;
		if ( control.equals("view") )
			isRead=true;

%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<form name="form_1" method="post" action="../content/c002a-c.jsp">
<input type="hidden" name="lContentID" value="<%=lContentID%>">
<input type="hidden" name="PageName" value="<%=pageName%>">
<input type="hidden" name="PageNo" value="<%=pageNo%>">
<input type="hidden" name="prePageName" value="/content/c103a-v.jsp">
<input type="hidden" name="SUBMIT" value="">
<input type="hidden" name="control" value="<%=control%>">


<table width="80%" border="0" class="top" height="153">
  <tr> 
    
<td class="FormTitle" height="29"><b>��������</b></td>
  </tr>
  <tr> 
    <td> 
      <table width="100%" border="0" align="center">
        <tr> 
          <td colspan="6" height="31">&nbsp;</td>
        </tr>
        
<tr bgcolor="#FFFFFF" align="center"> 
          
<td colspan="6" height="31">
<font size="2"><br>
</font>
<table width="99%" border="0" align="center" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
<td bgcolor="#FFFFFF" align="center" colspan="3"><font size="2">�塢��Ҫ����ָ�����</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center" style="background-repeat:no-repeat"><font size="2">��Ŀ</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center"><font size="2">������ĩ��</font></td>
<td bgcolor="#FFFFFF" align="center" width="43%"><font size="2">��������ĩ��</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" align="center" width="19%"><font size="2">��ֵ</font></td>
<td bgcolor="#FFFFFF" align="center" width="38%">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">��������</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">����</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">˰��</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">�����ʲ���ծ��</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">��������</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">�ٶ�����</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="19%" align="center"><font size="2">�ʲ���ծ��</font></td>
<td bgcolor="#FFFFFF" width="38%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="43%" align="center">
<input type="text" name="textfield" size="30" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='submitfunction';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" colspan="3">
<blockquote>
<p><font size="2"><br>
��ע�������ʲ���ծ�� �� ������ծ�������ʲ�<br>
�������������� �� �����ʲ���������ծ<br>
�������ٶ����� �� �����ʲ��������������ծ<br>
�������ʲ���ծ�� �� �ܸ�ծ�����ʲ�
</font></p>
</blockquote>
</td>
</tr>













</table>
<table width="99%" border="0">
<tr>
<td>����
����ҵ��֤��������Ϊ��ʵ�ɿ��ģ����в�ʵ������ҵ�е��ɴ������һ�����Ρ�������б仯����������֪ͨ��˾��
<p align="right">�����ҵ���£�������������&nbsp;</p>
<p align="right">���˴���ǩ�֣�������������&nbsp;</p>
<p align="right">�������ꡡ���¡�����&nbsp;</p>
</td>
</tr>
</table>
<br>
</td>
        </tr>
        <tr> 
          <td width="1%" height="40">&nbsp;</td>
          <td colspan="5" height="40"> 
            <div align="right"> 
              <input type="button" name="Submit22" value=" ��һ�� " class="button" onClick="return validate(2);">
              <input type="button" name="Submit22" value=" �� �� " class="button" onClick="return validate(3);">
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table></form>
<p><br>
</p>
<p>&nbsp; </p>
<script language="JavaScript">
<!--
function validate(nType)
{
  var bSubmit = false;
  
  if(nType == "2")
  {
  <% if (isRead) { %>
      bSubmit=true;
      form_1.SUBMIT.value="pre";
  <% }else{ %>
    if (confirm("�Ƿ񱣴�ò��ĺ�ͬ���ݣ�"))
    {
      bSubmit=true;
      form_1.SUBMIT.value="presave";
    }
    else
    {
      bSubmit=true;
      form_1.SUBMIT.value="pre";
    }
    <% } %>
    
  }
  else if(nType == "3")
  {
       <% if (isRead){ %>
       window.close();
       <% } else { %>
    	if (confirm("�Ƿ񱣴�ò��ĺ�ͬ���ݣ�"))
    	{
      		bSubmit=true;
      		form_1.SUBMIT.value="close";
     	}
    	else
    	{
	  		window.close();
    	}
    	<% } %>
  }
  else
  {
    bSubmit = true;
    form_1.SUBMIT.value="save";
  }
  if (bSubmit)
  {
    form_1.submit();
    return true;
  }
  else
  {
    return false;
  }
}
//-->
</script>

<script language="javascript">
	firstFocus(form_1.textfield[0]);
	//setSubmitFunction("validate(1)");
    setFormName("form_1");
</script>

<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>