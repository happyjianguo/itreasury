<%--
/*
 * 程序名称：V824.jsp
 * 功能说明：系统管理-收款方资料查询输出页面（内部账户）
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

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
        
        //显示保存操作成功
        String str = (String)request.getAttribute("BCCG");
		if(str!=null&&str.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('保存成功')");
			out.println("</script>");
		}
        
        //显示删除操作成功
        String strdel = (String)request.getAttribute("SCCG");
		if(strdel!=null&&strdel.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('删除成功')");
			out.println("</script>");
		}
        
        
        
        //显示文件头   
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		String strContext = request.getContextPath();//http://xxxx/../cpob
        Collection collection = (Collection)request.getAttribute("collection");
		Iterator iterator = null;
		if (collection != null)
		{
            iterator = collection.iterator();
		}
%>

<safety:resources />
<form name="form1" method="post" >
<input type="hidden" name="txtIsCPF" value="true">
<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="txt_black">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="316" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="300" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">已设置<%=Env.getClientName()%>内部收款方资料－查询</span></td>
        <td width="15" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
		<br/>


      <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
      </table>
	  
	  
	  
      <table width="774" border="1" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
        <thead>
    <tr>
	<td width="5%" align="center">全选<input type="checkbox" name="checkAll" onclick="javascript:checkall();"/></td>
      <td width="65%">收款方名称</td>
      <td width="15%">收款方账号</td>
      <td width="20%">汇入行名称</td>
    </tr>
  </thead>

	<%
		NameRef nameref = null;
		 int iCount = 0;
		 while ((iterator != null) && iterator.hasNext())
		 {
			iCount++;
			ClientCapInfo  rf =(ClientCapInfo)iterator.next();
			String strBankNo = com.iss.itreasury.settlement.util.NameRef.getBankAccountNOByCurrenctAccountID(rf.getPayeeAccoutNO());
				rf.setPayeeBankNO("");
	 %>
        <tr bgcolor="<%=iCount%2==0?"#EBEBEB":"FFFFFF"%>">
          <td  height="14" align="center">
            <input type="checkbox" name="txtIDCheckbox" value="<%= rf.getID() %>">
          </td>
          <td  height="14">
            <div align="left"><%if (rf.getPayeeName()!=null) out.print(rf.getPayeeName()); %></div>
          </td>
          <td   height="14"  align="center">
            <div><u style="cursor:hand" onClick="javascript:form2.txtID.value = this.name;form2.submit();" name="<%= rf.getID() %>">
            <%
            if(rf.getPayeeAccoutNO()!=null){
            out.print(nameref.getNoLineAccountNo(rf.getPayeeAccoutNO())+rf.getPayeeBankNO());
            }
            %>  
            </u></div>
			</td>
<%
		if(rf.getIsCPFAcct()==OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES)
		{
%>
		<td  height="14">
            <div align="left"><%if (true) out.print(""+Env.getClientName());%></div>
          </td>
<%
		}
		else
		{
%>
		<td   height="14">
            <div align="center"><%if (rf.getPayeeBankName()!=null) out.print(rf.getPayeeBankName()); %></div>
          </td>
<%
		}
%>
		  <!--
	  <td class="ItemBody" width="133" height="14">
            <div align="center"><%if (rf.getPayeeBankNO()!=null) out.print(rf.getPayeeBankNO()); %></div>
          </td>
		  -->
        </tr>
		<%-- 循环项 --%>
	<%
		}
		if (iCount == 0)/*表示没记录，显示一空行*/
		{
	 %>
        <tr >
          <td  width="26" height="14">
          </td>
          <td  width="146" height="14">
          </td>
          <td  width="102" height="14" >
			</td>
		<td width="137" height="14">
          </td>
		  <!--
	  <td class="ItemBody" width="133" height="14">
          </td>
		  -->
        </tr>
	 <%
	 }
	  %>

      </table>
      <br>
      <table width="774" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="664" height="14">
            <div align="right"></div>
          </td>
          <td width="63" height="14" valign="top">
            <div align="right" >
			<!--img src="/webob/graphics/button_shanchu.gif" width="46" height="18" border="0" <% if (iterator!=null) { %>style="cursor:hand" onClick="javascript:doDelet();"<% } %>-->
			<input type="button" name="Submitv00204" value=" 删除 " class="button1"  <% if (iterator!=null) { %>style="cursor:hand" onClick="javascript:doDelet();"<% }else{ %>disabled<% }%>>&nbsp;&nbsp;
            </div>
          </td>
          <td width="63" height="14" valign="top">
            <div align="right" ><!--a  href="<%= strContext %>/system/V820.jsp">
			<img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" ></a-->
			<input type="button" name="Submitv00204" value=" 返回 " class="button1" onclick="javascript:doGobank();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
      </table>
</form>
<%--用于提交--%>
<form name="form2" method="post" action="<%= strContext %>/system/C826.jsp">
<input type="hidden" name="txtID" value="">
<input type="hidden" name="txtIsCPF" value="true">
</form>
 <script language="JavaScript">
 function doCheck()
 {
 	  	var isCheck = false;
		for(i=0; i<document.form1.elements.length; i++)
   		{
     		if(document.form1.elements[i].type=="checkbox")
			{
	   			if (document.form1.elements[i].checked == true)
				{
					isCheck = true;
					break;
				}
			}
		}
		if (!isCheck)
		{
			alert("请选择要删除的记录");
			return false;
		}
		return true;
 }
 function doDelet()
 {
 	if (doCheck())
	{
		if(confirm("是否删除？"))
	    {
			form1.action = "<%= strContext %>/system/C825.jsp?flag=deleted";
			showSending(); form1.submit();
	    }
	}
 }
 function doGobank(){
 	form1.action="<%= strContext %>/system/V820.jsp";
	showSending(); form1.submit();
	 }
 function checkall()
 {
 	for( var c = 0;c < document.form1.elements.length; c++)
	{
	    if(form1.elements[c].name == "txtIDCheckbox" )
	    {
	       form1.elements[c].checked = document.form1.checkAll.checked;
	    }
	}
 }
 </script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		 return;
	}
	
%>

<%@ include file="/common/SignValidate.inc" %>
