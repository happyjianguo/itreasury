<%--
/*
 * 程序名称：exp_add.jsp
 * 功能说明：资金划拨--批量导入页面
 * 作　　者：郑凯
 * 完成日期：2007年04月16日
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*" %>
<%@ page import = "com.iss.itreasury.dataentity.*" %>
<%@ page import = "com.iss.itreasury.util.*" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>


<%
	


	
	/* 标题固定变量 */
	String strTitle = "批量导入";

	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<% OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu); %>

<%
	 //用户登录检测 
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
    
    String strInfo = "";
    long lRow = -1;
    long lCol = -1;
    


%>

<html>
<body onload="javascript:document.exp_frm.xlsfile.focus();">
<form name="exp_frm" enctype ="multipart/form-data"  action="" method="post">
	<input type="hidden" name="SuccessPage" value="exp_add.jsp">
	<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">批量付款</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>  

<br/>
<table width=100% border="0"  cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr >
          <td width="*%" colspan="9" height="25" class="MsoNormal">&nbsp;</td>
        </tr>
		<tr >
		  <td width="4" height="25" ></td>
          <td width="100" height="25" ><font color="red">*&nbsp;</font>上传文件：</td>
          <td width="4" height="25" ></td>
          <td width="457" height="25" >
			<input type="file" class="box" name="xlsfile" size="18" onfocus="nextfield ='dAmount';">
          </td>
          <td width="20" height="25" class="MsoNormal"></td>
          <td width="*%" colspan="4" height="25" class="MsoNormal">&nbsp;</td>
          
          
        </tr>
        
		<tr >
		<td width="4" height="25" ></td>
		  <td width="100" height="25" ><font color="red">*&nbsp;</font>总金额：</td>
          <td width="5" height="25" ><div align="right" ></div></td>
          <td width="457" height="29" width="29">
          	<fs:amount 
	       		form="exp_frm"
       			name="dAmount"
       			chineseCtrlName="sChineseAmount"
       			nextFocus="lCount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
          <td width="*%" height="25" class="MsoNormal">&nbsp;</td>
          <td width="*%" height="25" class="MsoNormal">&nbsp;</td>
          <td width="*%" height="25" class="MsoNormal">&nbsp;</td>
          <td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        </tr>
        <tr>
            <td width="5" height="25" class="MsoNormal"></td>
            <!--modify by xwhe 2008-11-10-->
            <td width="250" height="25" class="MsoNormal" nowrap colspan="2" ><font color="red">*&nbsp;</font>大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
            <td height="25" class="MsoNormal">
		    <input type="text" class="box" name="sChineseAmount" size="29" value="" readonly></td> 	
		  <td width="5" height="25" class="MsoNormal"></td>
		  <td width="*%" colspan="4" height="25" class="MsoNormal">&nbsp;</td>
        </tr>
		<tr >
		  <td width="4" height="25" ></td>
          <td width="100" height="25" ><font color="red">*&nbsp;</font>总笔数：</td>
          <td width="4" height="25" ></td>
          <td width="457" height="25" >
			<input type="text" class="box" name="lCount" style="text-align: right;" size="29"  onfocus="nextfield ='add1';" >
          </td>
          <td width="129" height="25" colspan="" class="MsoNormal"></td>
          <td width="*%" colspan="4" height="25" class="MsoNormal">&nbsp;</td>
        </tr>
        <tr><td width="*%" height="25" colspan="9" class="MsoNormal">&nbsp;</td></tr>
        <tr>
			
			<td colspan="5"></td>
            <td  width="50" align="right" >
				<input class="button1" name=add1 type=button value=" 导 入 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction';">&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td  width="50" align="right" >
				<input class="button1" name=download type=button value=" 模板下载 " onClick="Javascript:downloadme();" >&nbsp;&nbsp;&nbsp;&nbsp;
 			</td>
 			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
 			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        </tr>
  		<tr height="5"><td width="*%" colspan="9" height="25" class="MsoNormal">&nbsp;</td></tr>
</table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
<script>
<%
	UpLoanReturnInfo upLoanReturnInfo = null;
	/* 从请求中获取信息 */
	upLoanReturnInfo = (UpLoanReturnInfo)request.getAttribute("upLoanReturnInfo");
	
	if(upLoanReturnInfo != null)
	{
		strInfo = upLoanReturnInfo.getReason();
		lRow = upLoanReturnInfo.getPositionRow();
		lCol = upLoanReturnInfo.getPositionCol();
		%>
		checkReturn();
	<%}
	
%>

function addme()
{
	var vfile = document.all("xlsfile").value;
	var vamount = document.all("dAmount").value;
	var vallcount = document.all("lCount").value;
	var breturn = true;
	
	
	
	if(vfile=="")
	{
		alert("上传文件不能为空，请选择");
		breturn = false;
		document.exp_frm.xlsfile.focus();
		return;
	}
	
	if(!vfile=="")
	{	
	    Ary=vfile.split('.'); 
	    filetype=Ary[Ary.length-1]; 
      if (filetype!='xls') 
        { 
          alert ('请把上传文件转换成.xls格式'); 
          breturn = false;
		  document.exp_frm.xlsfile.focus();
		   return;
        } 
	   	 		
	}
	
	if(vamount==""||vamount=="0.00")
	{
		alert("总金额不能为空，请录入");
		breturn = false;
		document.all("dAmount").focus();
		return;
	}
	if(vallcount=="")
	{
		alert("总笔数不能为空，请录入");
		document.all("lCount").focus();
		breturn = false;
		return;
	}	
		if(!vallcount=="" && vallcount.length>0)
	{
		for(i  =  0;  i  <  vallcount.length;  i++)  {   
     if(vallcount.charAt(i)  <  "0"  ||  vallcount.charAt(i)  >  "9")      
        {  alert("请输入数字类型的总笔数！");
           document.all("lCount").value="";
		  document.all("lCount").focus();
		  breturn = false;
		return;
		}
     } 

	}	
	
	
	exp_frm.action = "exp_c.jsp?dAmount=" + exp_frm.dAmount.value + "&lCount=" + exp_frm.lCount.value;
	showSending();
	exp_frm.submit();
}

function checkReturn()
{
	<%
	if(strInfo != null && !strInfo.equals(""))
	{
		if(lRow > 0)
		{%>
			alert("<%=strInfo%>" + ",在文件第" + <%=lRow+1%> + "行,第" + <%=lCol+1%> + "列");
		<%}
		else
		{%>
			alert("<%=strInfo%>");
		<%}
	}
	%>
}


function downloadme(){
	exp_frm.action = "/NASApp/iTreasury-ebank/DownLoadModelServlet";
	exp_frm.submit();
	}

</script>

<script language="javascript">
   // window.name = "Check_Window";  	
    firstFocus(exp_frm.xlsfile);/*第一个获焦点**/
    //setSubmitFunction("addme(exp_frm)");
    setFormName("exp_frm");
</script>
<% OBHtml.showOBHomeEnd(out); %>
<safety:resources />
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc" %>