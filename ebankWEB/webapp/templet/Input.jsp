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
<%String strContext = request.getContextPath();%>
<%
    //�̶�����
    String strTitle = "�ʽ𻮲�";

    try{
          if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//�ж��Ƿ���Ȩ��
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E003");
		out.flush();
		return;
	}
        /**
         * isLogin end
         */

        /**
         * presentation start
         */
        //��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
    }
    catch (Exception e)
    {
       
    }
%>

<script language="JavaScript" src="/webob/OBZoom.js"></script>
<script language="JavaScript" src="/webob/OBCheck.js"></script>

<form name="frm" method="post">
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="/webob/blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">�����˻�</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">�����˻����ƣ�</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" name="txtLiveAccoName" size="30" value="�й���Ȼ�����޹�˾" disabled>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">�����˺ţ�</td>
          <td width="430" height="25" class="box">
            <select class='box' name="selLiveAccoNo" onfocus="nextfield ='selLimitPeriod';">
              <option value="-1"> </option>
              <option value=1>���ڴ�� 01-01-0001-1(��ǰ��100,000.00����������0,000.00)</option>
              <option value=2>���ڴ�� 01-01-0001-2(��ǰ����00,000.00����������0,000.00)</option>
            </select>
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
          <td width="560"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">�����˻�</font></td>
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
          <td width="130" height="25"> <span class="graytext">�����˺ţ�</span></td>
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
            <p><span class="graytext">���ڴ�����ޣ�</span></p>
          </td>
          <td width="430" height="25">
            <select name="selLimitPeriod"  onfocus="nextfield ='txtAmountCtrl';">
              <option value="3">3����</option>
              <option value="6">6����</option>
              <option value="12">12����</option>
              <option value="36">36����</option>
              <option value="60">60����</option>
              <option value="1001">1��</option>
            </select>
          </td>
          <td width="1" height="25"></td>
        </tr>
      </table>
      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="/webob/blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="3"><font size="3" color="#FFFFFF" class="whitetext">��������</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" bgcolor="#C8D7EC" class="graytext">��</td>
          <td width="20" height="25" bgcolor="#C8D7EC" class="graytext">
          </td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">

<script language="javascript">
    createAmountCtrl("frm","txtAmount",0,"txtDate","null",<%=sessionMng.m_lCurrencyID%>);
</script>

          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext" colspan="2">ִ���գ�</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" name="txtDate" size="12" value="2001-11-21" onfocus="nextfield ='txtUsage';">
            <img src="/webob/calendar.gif" width="15" height="18"> </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">�����;��</td>
          <td width="430" height="25" class="box">
            <input type="text" name="txtUsage" size="30" onfocus="nextfield ='submitfunction';">
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
            <div align="right"><img src="/webob/button_tijiao.gif" width="46" height="18" onclick="Javascript:submitme();"></div>
          </td>
          <td width="62">
            <div align="right"><img src="/webob/button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"></div>
          </td>
        </tr>
      </table>
</form>
<!--presentation end-->
<!--check start-->
<script language="JavaScript">
    //�ύ������
    function submitme()
    {
	    var form1 = document.frm;
		frm.action="<%=strContext%>/Control.jsp";
        if (validate() == ture)
        {
            frm.submit();
        }
    }
    //ȡ��������
    function cancelme()
    {
        frm.reset();
    }
    У�麯��
    function validate()
    {
       var form_1 = document.frm;

       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */

    	return true;
    }
</script>
<!--check end-->
<script language="javascript">
firstFocus(frm.selLiveAccoNo);
//setSubmitFunction("submitme()");
setFormName("frm");
</script>
<%
        //��ʾ�ļ�β
        OBHtml.showOBHomeEnd(out);
%>

