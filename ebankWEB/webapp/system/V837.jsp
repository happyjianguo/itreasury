<%--
/*
 * 程序名称：V827.jsp
 * 功能说明：系统管理-收款方资料修改输入页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
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
		
		//显示文件头
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String strContext = request.getContextPath();//http://xxxx/../cpob
			 
		//接收数据
		ClientCapInfo rf = (ClientCapInfo)request.getAttribute("ClientCapInfo");
%>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<safety:resources />

<form name="form1" method="post">
<%-- C823.jsp需要数据 --%>
<input type="hidden" name="txtIsCPF"  value="false">
<%-- C825.jsp需要数据 --%>
<input type="hidden" name="txtIDCheckbox" value="<%if (rf != null) out.print(rf.getID()); %>">

    
    
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">收款方资料－修改</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>

            <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
                            <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                
      <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>收款方账号：</td>
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
                
      <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>收款方名称：</td>

      <td height="25" width="220" class="graytext" > <input type="text" class="box" name="txtPayeeName" onfocus="nextfield ='txtPayeeAccoutNO';" size="32" maxlength="50" value="<%if (rf != null && rf.getPayeeName()!=null) out.print(rf.getPayeeName()); %>" ></td>
                <td  height="25" width="100"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext"  align="left"><font color="#FF0000">* </font>汇入地：</td>
      			<td height="25" width="220" class="graytext" nowrap> 
      				<input type="text" class="box" name="txtPayeeProv" onfocus="nextfield ='txtCity';" size="10"  maxlength="15" value="<%if (rf != null && rf.getPayeeProv()!=null) out.print(rf.getPayeeProv()); %>" >
			        	省
			        <input type="text" class="box" name="txtCity" size="10" onfocus="nextfield ='txtPayeeBankName';" maxlength="15" value="<%if (rf != null && rf.getCity()!=null) out.print(rf.getCity()); %>"   >
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
                <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;汇入行CNAPS号：</td>
                <td height="25" width="220" class="graytext" >
            		<input type="text" class="box" name="txtPayeeBankCNAPSNO" onfocus="nextfield ='txtPayeeBankOrgNO';" size="32"  maxlength="50" value="<%if (rf != null && rf.getSPayeeBankCNAPSNO()!=null) out.print(rf.getSPayeeBankCNAPSNO()); %>" >
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
                <td height="25" width="128" class="graytext"  align="left">&nbsp;&nbsp;汇入行联行号：</td>
                <td height="25" width="220" class="graytext" >
                  <input type="text" class="box" name="txtPayeeBankExchangeNO" onfocus="nextfield ='';" size="32"  maxlength="50" value="<%if (rf != null && rf.getSPayeeBankExchangeNO()!=null) out.print(rf.getSPayeeBankExchangeNO()); %>" >
                </td>
                <td  height="25" width="100"></td>
              </tr>
              
              <tr>
                <td  colspan="4" ></td>
                  <td align="right" nowrap>
					 <input type="button" name="Savev00204" value=" 保 存 " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doSubmitForm1();" <% }%>>&nbsp;&nbsp;
					 <input type="button" name="Submitv00204" value=" 删 除 " class="button1" <% if (rf != null) {%> style="cursor:hand" onClick="javascript:doDeletForm1();" <% }%>>&nbsp;&nbsp;
					 <input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="doBackForm1();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
		alert("请输入省份！");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+form1.txtPayeeBankName.value+'&province='+form1.txtPayeeProv.value+'&city='+form1.txtCity.value+'&recBankCode='+form1.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+form1.txtPayeeBankName.value+'&bankName=txtPayeeBankName', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}

function doSubmitForm1()
{ 
	if (form1.txtPayeeAccoutNO.value.Trim() == "")
	{
		alert("收款账号不能为空，请录入");
		form1.txtPayeeAccoutNO.focus();
	  return false;
	}
	if (form1.txtPayeeName.value.Trim() == "")
	{
		alert("收款方名称不能为空，请录入");
		form1.txtPayeeName.focus();
		return false;
	}
	if (form1.txtPayeeProv.value.Trim() == "")
	{
		alert("汇入地 省名称不能为空，请录入！");
		form1.txtPayeeProv.focus();
		return false;
	}
	var payeeNameLength = form1.txtPayeeName.value.replace(/[^\x00-\xff]/g,'**').length;
	if(payeeNameLength>80)
	{
		alert("收款方名称长度不能大于40个汉字(80个字节)!");
		form1.txtPayeeProv.focus();
		return false;
	}
	if (form1.txtCity.value.Trim() == "")
	{
		alert("汇入地 市（县）名称不能为空，请录入");
		form1.txtCity.focus();
		return false;
	}
	if (form1.txtPayeeBankName.value.Trim() == "")
	{
		alert("汇入行名称不能为空，请录入");
		form1.txtCity.focus();
		return false;
	}
	form1.action = "<%= strContext %>/system/C821.jsp?flag=saved";
	showSending(); form1.submit();
}
function doDeletForm1()
{
	if(confirm("是否删除？"))
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
 firstFocus(form1.txtPayeeName);/*第一个获焦点*/
 setSubmitFunction("doSubmitForm1()");
 setFormName("form1");

/*
 * 去除前后空格
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
