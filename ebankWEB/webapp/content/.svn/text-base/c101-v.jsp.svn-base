<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obattach.bizlogic.*,
	com.iss.itreasury.ebank.obcontent.dao.*,
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
   	   String[] arrContent=new String[64];
   	   if (strContent.length() > 0)
	   {
			int nIndex; //","的索引位置
			int nTmp = 0;
			nIndex = strContent.indexOf(OBContentDao.CONTENT_SEPERATOR);
			while (nIndex >= 0)
			{
				arrContent[nTmp] = strContent.substring(0, nIndex);
				strContent = strContent.substring(nIndex + 4);
				nIndex = strContent.indexOf(OBContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = strContent;
		}

  	   int iCount=0;
  	   long lContentID=-1;
  	   String pageName="/content/c102-v.jsp";
  	   long pageNo=1;
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

<form name="form_1" method="post" action="../content/c002-c.jsp">
<input type="hidden" name="lContentID" value="<%=lContentID%>">
<input type="hidden" name="PageName" value="<%=pageName%>">
<input type="hidden" name="PageNo" value="<%=pageNo%>">
<input type="hidden" name="prePageName" value="">
<input type="hidden" name="SUBMIT" value="">
<input type="hidden" name="control" value="<%=control%>">

<table width="80%" border="0" class="top" height="153">
  <tr> 
    
<td class="FormTitle" height="29"><b>贷款调查表</b></td>
  </tr>
  <tr>
  <td >
    <table width="100%"> <tr>
    <td width="70%">&nbsp;</td>
    <td width="30%">
    <input  class=button type=button name="export" value="导出并打印" onclick="exportme();">
    </td>
    </tr></table>
  </td>
  </tr>
  <tr> 
    <td> 
      <table width="100%" border="0" align="center">
        <tr> 
          <td colspan="6" height="31">&nbsp;</td>
        </tr>
        
<tr bgcolor="#FFFFFF" align="center"> 
          
<td colspan="6" height="31">
<br>
<br>
<b>贷款调查表</b>
<table width="99%" border="0" align="center" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
<td bgcolor="#FFFFFF" align="center" colspan="4"><font size="2">一、企业基本情况</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">企业名称</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">

<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">

</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">负责人</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">地址</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">电话</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">主管部门</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">企业性质</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">开户银行</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">账号</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">职工人数</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">高中级职称</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">固定资产原值</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">固定资产净值</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">资产总计</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">负债总计</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">注册资金</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">所有者权益</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">应收账款</font></td>
<td bgcolor="#FFFFFF" width="47%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">应付账款</font></td>
<td bgcolor="#FFFFFF" width="29%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" colspan="4" align="center">
主要产品及生产规模
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" colspan="4">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" colspan="4">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" colspan="4">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
</table>
<table width="99%" border="0" align="center" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
<td bgcolor="#FFFFFF" align="center" colspan="7"><font size="2">二、
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
年至
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
年的经济效益情况</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" rowspan="2" width="11%" background="../library/line.gif" style="background-repeat:no-repeat"><font size="2">　　项目<br>
年份
</font></td>
<td bgcolor="#FFFFFF" rowspan="2" width="12%" align="center"><font size="2">销售收入</font></td>
<td bgcolor="#FFFFFF" rowspan="2" width="12%" align="center"><font size="2">换汇</font></td>
<td bgcolor="#FFFFFF" colspan="3" align="center"><font size="2">实现利税</font></td>
<td bgcolor="#FFFFFF" rowspan="2" width="17%" align="center"><font size="2">上交利税</font></td>
</tr>
<tr>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">合计</font></td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">利润</font></td>
<td bgcolor="#FFFFFF" width="12%" align="center"><font size="2">税金</font></td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%" align="center">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
</tr>
<tr align="center">
<td bgcolor="#FFFFFF" width="11%"><font size="2">合计</font></td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="12%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='textfield[<%= iCount %>]';">
</td>
<td bgcolor="#FFFFFF" width="17%">
<input type="text" name="textfield" size="20" <% if(isRead){ %> readonly <%}%> value="<%=DataFormat.formatString(arrContent[iCount++])%>" maxlength="50"  onFocus="nextfield ='submitfunction';">
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
function exportme()
{
	window.open('../content/c106-c.jsp?lContentID=<%=lContentID%>', "", "width=800,height=600,status=yes,toolbar=yes,menubar=yes,location=no,resizable=yes,scrollbars=yes");
}
function validate(nType)
{
  var bSubmit = false;
  
  if(nType == "3")			//关闭
  {
  	if(confirm("是否关闭当前窗口？"))
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