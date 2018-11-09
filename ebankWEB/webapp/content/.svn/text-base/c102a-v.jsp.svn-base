<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obattach.bizlogic.*,
	com.iss.itreasury.loan.contractcontent.dao.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.ebank.obattach.dataentity.*,
	com.iss.itreasury.util.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	try
	{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	  
  	   String strContent=(String)request.getAttribute("sContent");
  	   System.out.println(strContent);
   	   String[] arrContent=new String[48];
   	   if (strContent.length() > 0)
	   {
			int nIndex; //","的索引位置
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
  	   String pageName="/content/c103a-v.jsp";
  	   long pageNo=2;
  	   String control="";
  	   
  	   		//取参数变量
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
<script language="JavaScript" src="/webloan/js/Control.js"></script>
<script language="JavaScript" src="/webloan/js/Check.js"></script>

<safety:resources />

<form name="form_1" method="post" action="../content/c002a-c.jsp">
<input type="hidden" name="lContentID" value="<%=lContentID%>">
<input type="hidden" name="PageName" value="<%=pageName%>">
<input type="hidden" name="PageNo" value="<%=pageNo%>">
<input type="hidden" name="prePageName" value="/content/c101a-v.jsp">
<input type="hidden" name="SUBMIT" value="">
<input type="hidden" name="control" value="<%=control%>">

<table width="85%" border="0" class="top" height="153">
  <tr> 
    
<td class="FormTitle" height="29"><b>贷款调查表</b></td>
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
<td bgcolor="#FFFFFF" align="center" colspan="8"><font size="2">三、主要产品的产、销、库存情况</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" rowspan="2" width="11%" style="background-repeat:no-repeat"><font size="2">产品名称</font></td>
<td bgcolor="#FFFFFF" colspan="2" align="center"><font size="2">产量</font></td>
<td bgcolor="#FFFFFF" rowspan="2" width="14%" align="center"><font size="2">销量<br>
(上月期末数)</font></td>
<td bgcolor="#FFFFFF" rowspan="2" align="center" width="14%"><font size="2">销售收入<br>
(上月期末数)
</font></td>
<td bgcolor="#FFFFFF" colspan="3" align="center"><font size="2">库存量(上月期末数)</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="11%" align="center"><font size="2">上年度</font></td>
<td bgcolor="#FFFFFF" width="13%" align="center"><font size="2">到上月累计</font></td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">正常库存</font></td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">积压库存</font></td>
<td bgcolor="#FFFFFF" width="13%" align="center"><font size="2">占用资金</font></td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="11%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="11%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="11%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="11%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%"><font size="2">合计</font></td>
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="14%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="13%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%"><font size="2">备注</font></td>
<td bgcolor="#FFFFFF" colspan="7">
<textarea name="textfield" cols="60" rows="5" onFocus="nextfield ='submitfunction';" <% if(isRead){ %> readonly <%}%> ><%=DataFormat.formatString(arrContent[iCount++])%></textarea>
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
              <input type="button" name="Submit22" value=" 上一步 " class="button" onClick="return validate(2);">
              <input type="button" name="Submit2" value=" 下一步 " class="button" onClick="return validate(1);">
              <input type="button" name="Submit22" value=" 关 闭 " class="button" onClick="return validate(3);">
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
    if (confirm("是否保存该步的合同内容？"))
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
    	if (confirm("是否保存该步的合同内容？"))
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
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>