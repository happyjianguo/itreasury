<%--
 ҳ������ ��e017-v.jsp
 ҳ�湦�� : ����չ������-ָ��ȷ��
 ��    �� ���
 ��    �� ��2004��2��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obextendapply.bizlogic.*,
                 com.iss.itreasury.ebank.obextendapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* ����̶����� */
	String strTitle = "[չ������]";
%>	
<%
	  try
	  {
           /**
            * isLogin start
           */
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
		String strCode = "";
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strCode");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strCode = DataFormat.toChinese(strTemp.trim());
		}
		
		//��ָ���ѯ�����Ĳ���
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");

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
<table width="740" border="0" cellspacing="0" cellpadding="0">

  <tr bgcolor="#456795" class="tableHeader">

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

      <b>ָ�����Ϊ��<%=strCode%></b></font><br>

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
  printIt('../extendapply/e012-c.jsp?lExtendApplyID=<%=lID%>');
}
function doReturn()
{
     if(confirm("�Ƿ񷵻أ�"))
	 {
        //frm.action="e008-c.jsp?lExtendID=<%=lID%>&lIsFromSubmitPage=1";
		<%if(txtSearch.equals("modify"))
		{%>
			frm.action="../query/q003-c.jsp";
		<%}else{%>
        	frm.action="../extendapply/e001-v.jsp";
		<%}%>
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