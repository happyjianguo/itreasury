<%--
/*
 * 程序名称：V820.jsp
 * 功能说明：系统管理-收款方资料新增输入页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//固定变量
	String strTitle = null;
%>

<%
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        // 用户登录检测 
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
	
		//从请求中获取信息
		ClientCapInfo rf = (ClientCapInfo)request.getAttribute("clientInfo");
		//接收是否中油用户的参数据
		String strISCPF = request.getParameter("txtIsCPF");
			
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		String strContext = request.getContextPath();//http://xxxx/../cpob
%>

<%-- 中油财务内部账户--%>
<form name="form1" method="post">
<input type="hidden" value="<%=Math.random()%>" >
<input type="text" name="txtIsCPF" style="display:none" value="true" class="box">

<table cellpadding="0" cellspacing="0" class="title_top" style="display:none">
	<tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">往来账户维护</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

			<table width=100% border="0" cellspacing="0" cellpadding="0" align="" style="display:none">
			    <tr>
					<td width="1"><a class=lab_title1></td>
					<td width="150" class="lab_title2"> <%=Env.getClientName()%>账号</td>
					<td width="600"><a class=lab_title3></td>
				</tr>
			</table>   
	        
			<table width=100%  cellspacing="1" class=normal id="table1" align="" style="display:none">
				<tr>
					<td  height="25" width="1" ></td>
			    	<td height="25" width="5" ></td>
			    	<td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>账号：</td>
			      	<td height="25" colspan="2" class="graytext" >
			  		<input class="box" type="text" name="txtPayeeBankNO" size="20" maxlength="20"
						value="<% 
						if ( strISCPF != null )
						{
							if ( strISCPF.equals("true") )
							{
								if ( rf != null )
								{
									if ( rf.getPayeeBankNO() != null )
									{
										out.println( rf.getPayeeBankNO() ) ;
									}
								}
							}
						}				
						%>" 
					  onkeydown="keydownform1()">
	                </td>
	                <td  height="25" width="1"></td>
	              </tr>
	              <tr>
	                <td  height="1" colspan="6"></td>
                	</tr>
	              <tr>
	                <td  height="25" width="1"></td>
	                <td height="25" width="5" ></td>
	                <td height="25" width="128" class="graytext" align="left">&nbsp;&nbsp;汇入行名称：</td>
	                <td height="25" colspan="2" class="graytext" >
	                  <input class="box" type="text" name="textfield2222222" size="32" value="<%=Env.getClientName()%>" disabled>
	                </td>
	                <td  height="25" width="1"></td>
	              </tr>
	              <tr>
	                <td  height="1" colspan="6"></td>
	              </tr>
	    	</table>
						
	     	<table width=80% border="0" cellspacing="0" cellpadding="0" align="" style="display:none">
	              <tr>
	                <td  colspan="3" nowrap>
	                  <div align="right">
					  <!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSelectForm1();"-->
					 <input type="button" id="button1" name="Submitv00214" value=" 提 交 " class="button1" onClick="javascript:doSubmitForm1();" onkeydown="keydownform1sub();">&nbsp;&nbsp;
					  <input type="button"  name="Submitv00204" value=" 查 找 " class="button1" onClick="javascript:doSelectForm1();">&nbsp;&nbsp;
					 
					  </div>
	                </td>
	              </tr>
	     	</table>
  		</td>
  	</tr>
</table>
</form>
<%-- 非中油财务客户/外部银行账户--%>
<form name="form2" method="post">
<input class="box" type="text" name="txtIsCPF" style="display:none" value="false">		
<table cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="22">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">往来账户维护</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
			<br>
	<table width=100%  cellspacing="1" class=normal id="table1" align="" >
 		<tr>
	  		<td  height="25" width="1"></td>
	      	<td height="25" width="5" ></td>
      		<td height="25" width="128" class="graytext" align="left"><font color="#FF0000">* </font>收款方账号：</td>
    		<td height="25" width="220" class="graytext" >
                <!--modify by xwhe 2008-12-15 调整收款方账号能输入40个数字 -->
                  <input class="box" type="text" name="txtPayeeAccoutNO" size="32"  maxlength="40"
				  value=
				  "<% 
				  	if ( strISCPF != null  )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getPayeeAccoutNO() != null )
								{
									out.println( rf.getPayeeAccoutNO() ) ;
								}
							}
						}
					}				
					%>"
				 onkeydown="javascript:instandTab();">
        	</td>
         	<td  height="25" width="100"></td>
    	</tr>
		<tr>
         	<td  height="1" colspan="5"></td>
       	</tr>
		<tr>
          	<td  height="25" width="1"></td>
	  		<td height="25" width="5" ></td>
      		<td height="25" width="128" class="graytext" align="left"><font color="#FF0000">* </font>收款方名称：</td>
         	<td height="25"  width="220" class="graytext" >
                  <input class="box" type="text" name="txtPayeeName" size="32"  maxlength="50"
				  value=
				  "<% 
				  	if ( strISCPF != null  )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getPayeeName() != null )
								{
									out.println( rf.getPayeeName() ) ;
								}
							}
						}
					}				
					%>"
				  onkeydown="javascript:instandTab();">
        	</td>
      		<td  height="25" width="100"></td>
   		</tr>
		<tr>
         	<td  height="1" colspan="5"></td>
     	</tr>
   		<tr>
         	<td  height="25" width="1"></td>
         	<td height="25" width="5" ></td>
      		<td height="25" width="128" class="graytext" align="left"><font color="#FF0000">* </font>汇入地：</td>
       		<td height="25" width="220" class="graytext" nowrap>
          		<input class="box" type="text" name="txtPayeeProv" size="10"  maxlength="15"
				  value=
				  "<%
				  	if ( strISCPF != null  )
					{ 
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getPayeeProv() != null )
								{
									out.println( rf.getPayeeProv() ) ;
								}
							}
						}
					}				
					%>"
				  onkeydown="javascript:instandTab();">
                  省
                  <input class="box" type="text" name="txtCity" size="10"  maxlength="15"
				  value=
				  "<% 
				  	if ( strISCPF != null  )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getCity() != null )
								{
									out.println( rf.getCity() ) ;
								}
							}
						}
					}				
					%>"
				  onkeydown="javascript:instandTab();">
        	市（县）
        	</td>
          	<td  height="25" width="100"></td>
 		</tr>
		<tr>
         	<td  height="1" colspan="5"></td>
      	</tr>
   		<tr>
            <td  height="25" width="1"> </td>
            <td height="25" width="5" ></td>
            <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>汇入行名称：</td>
            <td height="25" width="220" class="graytext" >
         		<input class="box" type="text" name="txtPayeeBankName" size="32"  maxlength="50"
				  value=
				  "<% 
				  	if ( strISCPF != null )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getPayeeBankName() != null )
								{
									out.println( rf.getPayeeBankName() ) ;
								}
							}
						}
					}				
					%>" onkeydown="javascript:keydownform2();">
          	</td>
         	<td  height="25" width="100"></td>
     	</tr>
     	<tr>
       		<td  height="1" colspan="5"></td>
    	</tr>
		<tr>
            <td  height="25" width="1"> </td>
            <td height="25" width="5" ></td>
            <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;汇入行CNAPS号：</td>
            <td height="25" width="220" class="graytext" >
       		<input class="box" type="text" name="txtPayeeBankCNAPSNO" size="32"  maxlength="50"
			  value=
			  "<% 
			  	if ( strISCPF != null )
				{
					if ( strISCPF.equals("false") )
					{
						if ( rf != null )
						{
							if ( rf.getSPayeeBankCNAPSNO() != null )
							{
								out.println( rf.getSPayeeBankCNAPSNO() ) ;
							}
						}
					}
				}				
				%>" onkeydown="javascript:keydownform2();">
				<a href="javascript:doLink()"><font color="red">CNAPS号检索</font></a>
         	</td>
     		<td  height="25" width="100"></td>
		</tr>
		<tr>
       		<td  height="1" colspan="5"></td>
        </tr>
        <tr>
			<td  height="25" width="1"> </td>
	     	<td height="25" width="5" ></td>
	      	<td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;汇入行机构号：</td>
	    	<td height="25" width="220" class="graytext" >
	 		<input class="box" type="text" name="txtPayeeBankOrgNO" size="32"  maxlength="50"
				  value=
				  "<% 
				  	if ( strISCPF != null )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getSPayeeBankOrgNO() != null )
								{
									out.println( rf.getSPayeeBankOrgNO() ) ;
								}
							}
						}
					}				
					%>" onkeydown="javascript:keydownform2();">
        	</td>
           	<td  height="25" width="100"></td>
   		</tr>
   		<tr>
         	<td  height="1" colspan="5"></td>
     	</tr>
 		<tr>
        	<td  height="25" width="1"> </td>
        	<td height="25" width="5" ></td>
        	<td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;汇入行联行号：</td>
        	<td height="25" width="220" class="graytext" >
        	<input class="box" type="text" name="txtPayeeBankExchangeNO" size="32"  maxlength="50"
				  value=
				  "<% 
				  	if ( strISCPF != null )
					{
						if ( strISCPF.equals("false") )
						{
							if ( rf != null )
							{
								if ( rf.getSPayeeBankExchangeNO() != null )
								{
									out.println( rf.getSPayeeBankExchangeNO() ) ;
								}
							}
						}
					}				
					%>" onkeydown="javascript:keydownform2();">
        	</td>
        	<td  height="25" width="100"></td>
    	</tr>
      	<tr width="500" align="center" >
	      	<td  colspan="4"></td>
	     	<td align="right" nowrap>
			<!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doSelectForm1();"-->
		  	<input type="button" name="form2Submitv00204" value=" 保 存 " class="button1" onClick="javascript:doSubmitForm2();" onkeydown="keydownform2sub();">&nbsp;&nbsp;
			<input type="button"  name="Submitv00204" value=" 返 回 " class="button1" onClick="javascript:doBack();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
	  	</tr>
       	<tr >
  			<td height="5"></td>
      	</tr>
	</table>
	</td>
  </tr>
</table>
</form>

<script language="JavaScript">
function doBack(){
	window.location.href="<%= strContext %>/system/V834.jsp";
}
function doLink()
{    
	if(form2.txtPayeeProv.value.length <= 0)
	{
		alert("请输入省份！");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+form2.txtPayeeBankName.value+'&province='+form2.txtPayeeProv.value+'&city='+form2.txtCity.value+'&recBankCode='+form2.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+form2.txtPayeeBankName.value+'&bankName=txtPayeeBankName', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}

function isSpecialChar (d_string) { 
var i = 0; 
var specialChar = "%&*_|<>'"; 
var allValid = false; 
for (i = 0; i < d_string.length; i++) { 
var c = d_string.charAt(i);
 if (specialChar.indexOf(c) >= 0) 
	allValid = true; 
} 
	return allValid; 
}

function doCheckForm1()
{
	if (form1.txtPayeeBankNO.value == "")
	{
		alert("账号不能为空，请录入");
		form1.txtPayeeBankNO.focus();
		return false;
	}
	if(isSpecialChar(form1.txtPayeeBankNO.value))
	{
		alert("账号不能录入特殊字符，请重新录入");
		form1.txtPayeeBankNO.focus();
		return false;
	}
	return true;
}


function doCheckForm2()
{	
	var txtPayeeNameValue = form2.txtPayeeName.value;
	var txtPayeeProvValue = form2.txtPayeeProv.value;
	var txtCityValue = form2.txtCity.value;
	var txtPayeeBankNameValue = form2.txtPayeeBankName.value;
	var re = /'/;
	
	if (form2.txtPayeeAccoutNO.value.Trim() == "")
	{
		alert("收款方账号不能为空，请录入");
		form2.txtPayeeAccoutNO.focus();
		return false;
	}
	if(isSpecialChar(form2.txtPayeeAccoutNO.value))
	{
		alert("账号不能录入特殊字符，请重新录入");
		form2.txtPayeeAccoutNO.focus();
		return false;
	}
	var payNameLength = form2.txtPayeeName.value.replace(/[^\x00-\xff]/g,'**').length;
	if(payNameLength>80)
	{
		alert("收款方名称长度不能大于40个汉字(80个字节)!");
		form2.txtPayeeName.focus();
		return false;
	}
	if (form2.txtPayeeName.value.Trim() == "")
	{
		alert("收款方名称不能为空，请录入");
		form2.txtPayeeName.focus();
		return false;
	}
	if(isSpecialChar(form2.txtPayeeName.value))
	{
		alert("收款方名称不能录入特殊字符，请重新录入");
		form2.txtPayeeAccoutNO.focus();
		return false;
	}

	if (txtPayeeNameValue.match(re) != null )
	{
		alert("收款方名称不能录入特殊字符，请重新录入 ");
		form2.txtPayeeName.focus();
		return false;
	}

	if (form2.txtPayeeProv.value.Trim() == "")
	{
		alert("汇入地 省不能为空，请录入");
		form2.txtPayeeProv.focus();
		return false;
	}
	
	if (txtPayeeProvValue.match(re) != null )
	{
		alert(" 汇入地 省名称不能录入特殊字符，请重新录入 ");
		form2.txtPayeeProv.focus();
		return false;
	}
	
	if (form2.txtCity.value.Trim() == "")
	{
		alert("汇入地 市（县）不能为空，请录入");
		form2.txtCity.focus();
		return false;
	}

	if (txtCityValue.match(re) != null )
	{
		alert(" 汇入地 市（县）名称不能录入特殊字符，请重新录入 ");
		form2.txtCity.focus();
		return false;
	}
	if (form2.txtPayeeBankName.value.Trim() == "" )
	{
		alert(" 汇入行名称不能为空，请重新录入 ");
		form2.txtPayeeBankName.focus();
		return false;
	}
	if (txtPayeeBankNameValue.match(re) != null )
	{
		alert(" 汇入行名称不能录入特殊字符，请重新录入 ");
		form2.txtPayeeBankName.focus();
		return false;
	}
	if(!InputValid(form2.txtPayeeBankCNAPSNO, 0, "string", 1, 0, 20,"汇入行CNAPS号")){
		form2.txtPayeeBankCNAPSNO.focus();
		return false;
	}
		
	if(!InputValid(form2.txtPayeeBankOrgNO, 0, "string", 1, 0, 20,"汇入行机构号")){
		form2.txtPayeeBankOrgNO.focus();
		return false;
	}
		
	if(!InputValid(form2.txtPayeeBankExchangeNO, 0, "string", 1, 0, 20,"汇入行联行号")){
		form2.txtPayeeBankExchangeNO.focus();
		return false;
	}
	
	return true;
}
function doSelectForm1()
{
	form1.action = "<%= strContext %>/system/C823.jsp";
	showSending(); form1.submit();
}

function doSubmitForm1()
{
	if (doCheckForm1())
	{
		form1.action = "<%= strContext %>/system/C821.jsp?sAddFlag=1";
		showSending(); form1.submit();
	}
}

function doCencelForm1()
{
	if (confirm("是否取消？"))
	{
	form1.action = "";
	showSending(); form1.submit();
	}
}

function doSelectForm2()
{
	form2.action = "<%= strContext %>/system/C823.jsp";
	showSending();
	form2.submit();
}
function doSubmitForm2()
{
	if (doCheckForm2())
	{
		form2.action = "<%= strContext %>/system/C821.jsp?sAddFlag=1";
		showSending();
		form2.submit();
	}
}
function doCencelForm2()
{
	if (confirm("是否取消？"))
	{
	form2.action = "";
	showSending();
	form2.submit();
	}
}

//用回车代替TAB
function instandTab(){
  if(event.keyCode==13)
     event.keyCode=9;
}

//form2获得焦点
function keydownform2(){
	if (event.keyCode == 13)
    {
        event.returnValue=false;
        event.cancel = true;
        document.form2.form2Submitv00204.focus();
    }
}

//form2提交
function keydownform2sub(){
	if (event.keyCode == 13)
    {
        doSubmitForm2();
    }
}

//form1获得焦点
function keydownform1(){
	if (event.keyCode == 13)
    {
        document.form1.Submitv00214.focus();
    }
}
//form1提交
function keydownform1sub(){
	if (event.keyCode == 13)
    {
        doSubmitForm1();
    }
}


/* firstFocus(form1.txtPayeeBankNO);第一个获焦点*/
/* setSubmitFunction("doSelectForm2()");*/
/* setFormName("form2");*/
 
 
 /*
 * create by 何小文
 * 日期：2007-03-23
 * 去除前后空格
 */
String.prototype.Trim = function()  
{  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
} 

</script>
<%
		// 如果资料已存在，应该提示“收款方资料已存在”
		String sDuplicate = request.getParameter("sDuplicate") ;
		if ( sDuplicate != null && sDuplicate.equals("1") )
		{
%>
			<script language="JavaScript">
			alert("收款方资料已存在!") ;
			</script>		
<%
		}
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);
	}
	catch (IException ie)
	{
		 OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		 return;
	}
	
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>
