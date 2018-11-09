<!--说明：该文件只做结构显示，不做执行效果显示-->

<!--Header start-->
<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*,               
                 java.util.*,
				 java.sql.*"
%>
<%
 response.setHeader("Cache-Control","no-stored");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
%>
<!--Header end-->
<%
    //固定变量
    String strTitle = "资金划拨";
String strCol1 ="";
		String strCol2 ="";
		String strCol3 = "";
		String strCol4 = "";
		String strCol5 ="";

    try{
        /**
         * isLogin start
         */
        //登录检测
          if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E003");
		out.flush();
		return;
	}

        // ReturnInfo rf = null;
        // rf = request.getAttribute("return");
       //  String strCol1 = rf.strCol1;
       //  String strCol2 = rf.strCol2;
       //  String strCol3 = rf.strCol3;
       //  String strCol4 = rf.strCol4;
       //  String strCol5 = rf.strCol5;
		

    
        //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
    }
    catch (Exception e)
    {
       
    }
%>

<script language="JavaScript" src="/webob/OBZoom.js"></script>
<script language="JavaScript" src="/webob/OBCheck.js"></script>

      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="/webob/blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">活期账户</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">活期账户名称：</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" name="txtLiveAccoName" size="30" value="中国天然气有限公司" disabled>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">活期账号：</td>
          <td width="430" height="25" class="box">
            <%=strCol1%>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="/webob/blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">定期账户</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>

      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
               <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25"> <span class="graytext">定期账号：</span></td>
          <td width="430" height="25" class="graytext">
            <input type="text" name="txtLimitAccoNo" size="30" value="01-09-0001-1" disabled>
          </td>
          <td width="1" height="25"></td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25">
            <p><span class="graytext">定期存款期限：</span></p>
          </td>
          <td width="430" height="25">
            <%=strCol2%>
          </td>
          <td width="1" height="25"></td>
        </tr>
      </table>
      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="/webob/blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="3"><font size="3" color="#FFFFFF" class="whitetext">划款资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" bgcolor="#C8D7EC" class="graytext">金额：</td>
          <td width="20" height="25" bgcolor="#C8D7EC" class="graytext">
          </td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">

          <%=strCol3%>

          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext" colspan="2">执行日：</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <%=strCol4%>
            <img src="/webob/calendar.gif" width="15" height="18"> </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="box">
            <%=strCol5%>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="445">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right"></div>
          </td>
          <td width="62">
            <div align="right"></div>
          </td>
        </tr>
      </table>
<!--presentation end-->
<script language="javascript">
firstFocus(frm.txtContract1Ctrl);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script>
<%
        //显示文件尾
        OBHtml.showOBHomeEnd(out);
%>
