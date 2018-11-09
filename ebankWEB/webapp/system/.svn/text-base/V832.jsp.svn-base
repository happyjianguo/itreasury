<%--
/*
 * 程序名称：V820.jsp
 * 功能说明：系统管理-收款方资料新增输入页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
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
		
		
		String str = (String)request.getAttribute("success");
		if(str!=null&&str.equals("success")){
			out.println("<script language='javascript'>");
			out.println("alert('提交成功')");
			out.println("</script>");
		}
		
		
		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		//从请求中获取信息
		ClientCapInfo rf = (ClientCapInfo)request.getAttribute("clientInfo");
		String strContext = request.getContextPath();//http://xxxx/../cpob
%>

<safety:resources />
<form name="form1" method="post">
<input type="text" class="box" name="txtIsCPF" style="display:none" value="false">
</form>
<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="200" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="174" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">收款人资料－新增预览</span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="10"></td>
  </tr>
</table>


			<%
			if (rf != null)
			{
			 %>
            <table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
          
                            <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                
    <td height="25" width="128" class="graytext" align="left">收款方账号：</td>
                <td height="25" class="graytext" >
                  <%
                    NameRef nameref = new  NameRef();
                  if (rf.getPayeeAccoutNO()!=null) out.print(nameref.getNoLineAccountNo(rf.getPayeeAccoutNO())); %>
                </td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext" align="left">收款方名称：</td>
                <td height="25" class="graytext" >
                  <%
                
                  if (rf.getPayeeName()!=null) out.print(rf.getPayeeName()); %>
                </td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"></td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext" align="left">汇入地：</td>
                
    <td height="25" class="graytext" > 
      <%if (rf.getPayeeProv()!=null) out.print(rf.getPayeeProv()); %>
      省 
      <%if (rf.getCity()!=null) out.print(rf.getCity());%>
      市（县）</td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
              <tr>
                <td  height="25" width="1"> </td>
                <td height="25" width="5" ></td>
                <td height="25" width="128" class="graytext" align="left">汇入行名称：</td>
                <td height="25" class="graytext" >
                  <%if (rf.getPayeeBankName()!=null) out.print(rf.getPayeeBankName()); %>
                </td>
                <td  height="25" width="1"></td>
              </tr>
              <tr>
                <td  height="1" colspan="5"></td>
              </tr>
            </table>
			<%} %>
      <br>
      <table width="774" border="0" cellspacing="0" cellpadding="0" height="24">
        <tr>
          <td width="555">
            <div align="right"></div>
          </td>
          <td width="64">
            <div align="right"></div>
          </td>
          <td width="56">
<%--                  <div align="right">--%>
<%--				  <!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doFind();"-->--%>
<%--				  <input type="button" name="Submitv00204" value=" 查找 " class="button1" onClick="javascript:doFind();">&nbsp;&nbsp;--%>
<%--				  </div>--%>
          </td>
          <td width="57">
            <div align="right">
			<!--img src="/webob/graphics/button_xinzeng.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doAdd();"-->
			<input type="button" name="Submitv00204" value=" 返回 " class="button1" onClick="javascript:doAdd();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
      </table>
	  <a href="" id="link1"></a>
  <script language="JavaScript">
	function doAdd()
	{
		link1.href = "<%= strContext %>/system/V820.jsp";
		showSending();
		link1.click();
	}
	function doFind()
	{
		form1.action = "<%= strContext %>/system/C823.jsp";
		showSending(); form1.submit();
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
