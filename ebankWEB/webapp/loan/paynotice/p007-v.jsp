<%--
 ҳ������ ��d027-v.jsp
 ҳ�湦�� : �����������-ָ��ȷ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
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
	/* ����̶����� */
	String strTitle = "[�������]";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/loan/discountapply/d014-v.jsp*******");
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//�������
		String strTemp = "";
		long lID = -1;
		String code = "";
		long lContractID =-1;
		strTemp = (String)request.getAttribute("lPayID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("code");
		if( strTemp != null && strTemp.length() > 0 )
		{
			code = DataFormat.toChinese(strTemp.trim());
		}
				
		strTemp = (String)request.getAttribute("lContractID");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lContractID = Long.parseLong(strTemp.trim());
		}
		//��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		%>	
		
<script language="JavaScript" src="/webob/OBZoom.js"></script>
<script language="JavaScript" src="/webob/OBCheck.js"></script>
<script language="JavaScript" src="/webob/date-picker.js"></script>
<script language="JavaScript" src="/webob/MagnifierSQL.js"></script>
<safety:resources />

<form name="frm" method="post">
<input type="Hidden" name="lID" value="<%=lID%>">
<table width="730" border="0" cellspacing="0" cellpadding="0">

  <tr class="tableHeader">

    <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img

      src="/webob/blue_top_left.gif" width="3" height="3"></td>

    <td width="560" height="25" bgcolor="#456795"><font size="3"

      color="#FFFFFF" class="whitetext">ָ��ȷ��</font></td>

    <td width="5" valign="top" align="right" height="25">

      <div align="right">

      </div>

    </td>

  </tr>

  <tr bgcolor="#FFFFFF">

    <td colspan="3" height="1"></td>

  </tr>

  <tr bgcolor="#C8D7EC">

    <td width="5" height="25"></td>

    <td height="25" bgcolor="#C8D7EC">

      <p><font size="3">ָ�����ύ��<%=Env.getClientName()%>��</font><font

      size="3"><br>

      <br>

      <b>ָ�����Ϊ��<%=code%></b></font><br>

      <br>

      </p>

    </td>

    <td width="5" height="25"></td>

  </tr>

  <tr bgcolor="#FFFFFF">

    <td colspan="3" height="1"></td>

  </tr>

</table>

<br>

<table width="570" border="0" cellspacing="0" cellpadding="0">

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

	  <input class="button" name="back" onclick="doReturn();" type="button" value=" ���� ">

      </div>

    </td>

  </tr>

</table>

</form>
<script language="JavaScript">
if(confirm("�Ƿ��ӡ��"))
{
  printIt();
}
function doReturn()
{
     if(confirm("�Ƿ񷵻أ�"))
	 {
        frm.action="p001-v.jsp";
		showSending();
		frm.submit();
	 }	

}
function printIt()
{
window.open('p008-v.jsp?lContractID=<%=lContractID%>&lPayID=<%=lID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');

}
</script>


<%	
   //��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	%>
	<%@ include file="/common/SignValidate.inc" %>