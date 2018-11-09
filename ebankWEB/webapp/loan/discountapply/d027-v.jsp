<%--
 页面名称 ：d027-v.jsp
 页面功能 : 新增贴现申请-指令确认
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d014-v.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//定义变量
		String strTemp = "";
	    String strAction = (String)request.getAttribute("txtAction");
		long lID = -1;
		String strDiscountCode = "";
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strDiscountCode");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strDiscountCode = DataFormat.toChinese(strTemp.trim());
		}
		
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		%>	
		
<script language="JavaScript" src="/webob/OBZoom.js"></script>
<script language="JavaScript" src="/webob/OBCheck.js"></script>
<script language="JavaScript" src="/webob/date-picker.js"></script>
<script language="JavaScript" src="/webob/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post">
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<table width="730" border="0" cellspacing="0" cellpadding="0" class="top">

  <tr  class="FormTitle">

    <td width="5" valign="top" align="left" height="25" ></td>

    <td width="560" height="25" ><font size="3">指令确认</font></td>

    <td width="5" valign="top" align="right" height="25">

      <div align="right">

      </div>

    </td>

  </tr>

  <tr >

    <td colspan="3" height="1"></td>

  </tr>

  <tr >

    <td width="5" height="25"></td>

    <td height="25" >

      <p><font size="3">指令已提交到<%=Env.getClientName()%>！</font><font

      size="3"><br>

      <br>

      <b>指令序号为：<%=strDiscountCode%></b></font><br>

      <br>

      </p>

    </td>

    <td width="5" height="25"></td>

  </tr>

  <tr >

    <td colspan="3" height="1"></td>

  </tr>

</table>

<br>

<table width="700" border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td>

      <div align="right">

      </div>

      <div align="right">

      </div>

      <div align="right">

      </div>

    </td>

    <td>

      <div align="right">

	  <input class="button" name="back" onclick="doReturn();" type="button" value=" 返回 ">

      </div>

    </td>

  </tr>

</table>

</form>
<script language="JavaScript">
if(confirm("是否打印？"))
{
  printIt('d022-v.jsp?lID=<%=lID%>');
}
function doReturn()
{
     if(confirm("是否返回？"))
	 {
        frm.action="d017-c.jsp";
		showSending();
		frm.submit();
	 }	

}
function printIt(url)
{
	 window.open(url,"popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=300,top=180");
}
</script>


<%	
   //显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>