<%--
/**
 ҳ������ ��a409-v.jsp
 ҳ�湦�� : ����ҵ���ӡѡ�����
 ��    �� ��kewen hu
 ��    �� ��2004-02-25
 ����˵�� ����ʵ��˵����
 �޸���ʷ ��
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******����ҳ��--ebank/capital/accountinfo/a409-v.jsp******\n");
    //�������
    String strTitle = "[�˻���ʷ��ϸ]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* ��ʾ�ļ�ͷ */
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

			//ҳ�渨������
			String strAction = null;
			String strSuccessPageURL = "";
			String strFailPageURL = "";
			String strActionResult = Constant.ActionResult.FAIL;
			
			//ҵ�����
			long lID = -1;
			long lTransactionTypeID = -1;
			String strTemp = "";
			
			strTemp = (String)request.getParameter("strSuccessPageURL");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strSuccessPageURL = strTemp;
			}
			
			strTemp = (String)request.getParameter("strFailPageURL");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strFailPageURL = strTemp;
			}
			
			strTemp = (String)request.getParameter("lID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)request.getParameter("lTransTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getParameter("lReturn");
			long lReturn = -1;
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lReturn = Long.valueOf(strTemp).longValue();
			}
			Log.print("=============lReturn="+lReturn);
%>	
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(320,550);
</SCRIPT>
<title>��ӡ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
Table {  font-family: ����,Arial,Helvetica,Helv; font-size: 12px;}
BODY {
	BORDER-RIGHT: 0px; PADDING-RIGHT: 0px; BORDER-TOP: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: -8px 0px 0px 4px; BORDER-LEFT: 0px; WIDTH: auto; PADDING-TOP: 0px; BORDER-BOTTOM: 0px; FONT-FAMILY: Arial, Helvetica, sans-serif
}
.button {  border: thin outset; background-color: #d6d3ce; font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10px; font-style: normal; color: #000000}
-->
</style>
</head>
<Script Language="JavaScript">
var num = 0;

function sendback1()
{
 	var strfrmList = "";
<%
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
		if (document.frmv009.jzd.checked == true)//���˵�
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.ckzqpz.checked == true)//���֧ȡƾ֤
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzjfpz.checked == true)//����ת�ˣ��裩ƾ֤
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzdfpz.checked == true)//����ת�ˣ�����ƾ֤
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.jzd2.checked == true)//���˵�
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.ckzqpz2.checked == true)//���֧ȡƾ֤
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzjfpz2.checked == true)//����ת�ˣ��裩ƾ֤
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzdfpz2.checked == true)//����ת�ˣ�����ƾ֤
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
		if (document.frmv009.ckzqpz.checked == true)//���֧ȡƾ֤
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzjfpz.checked == true)//����ת�ˣ��裩ƾ֤
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.ckzqpz2.checked == true)//���֧ȡƾ֤
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv009.tzzzjfpz2.checked == true)//����ת�ˣ��裩ƾ֤
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
		if (document.frmv009.jzd.checked == true)//���˵�
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.tzzzdfpz.checked == true)//����ת�ˣ�����ƾ֤
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.jzd2.checked == true)//���˵�
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.tzzzdfpz2.checked == true)//����ת�ˣ�����ƾ֤
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.NoRecAndPayID:
	default :
%>
	num = -1;
<%
	break;
}
%>
/*
		
		if (document.frmv009.PrintTemplate1.checked == true)
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.PrintTemplate2.checked == true)
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv009.PrintTemplate3.checked == true)
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}				
		if (document.frmv009.PrintTemplate4.checked == true)
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate5.checked == true)
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate6.checked == true)
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate7.checked == true)
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate8.checked == true)
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate9.checked == true)
		{
			strfrmList = strfrmList + "j";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate10.checked == true)
		{
			strfrmList = strfrmList + "k";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate11.checked == true)
		{
			strfrmList = strfrmList + "l";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate12.checked == true)
		{
			strfrmList = strfrmList + "m";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv009.PrintTemplate13.checked == true)
		{
			strfrmList = strfrmList + "n";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
*/

	if (num == 0)
	{
		alert("��ѡ����Ҫ��ӡ�����ϣ�");
	}
	else if (num == -1)
	{
		window.close();
	}
	else 
	{
		window.open("<%=strContext%>/common/p003-c.jsp?lID=<%=lID%>&printPage=/accountinfo/a409-c.jsp&strPrintPage="+strfrmList);
		window.close();
	}
}

function check(box)
{
  if (box.checked == false) 
  {
  	num = num - 1;
  }
  else
  {
  	num = num + 1;
  }
}
</Script>
<body bgcolor="#d6d3ce"  text="#000000">
	<form name="frmv009" method="post" action="">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
  <table width="320" class="table" align="center">
<%
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td width="124" align="center">���һ</td>
      <td width="124" height="30" align="center">����</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="jzd" value="checkbox" onClick="check(this)">
        ���˵� </td>
      <td height="30"> <input type="checkbox" name="jzd2" value="checkbox" onClick="check(this)">
        ���˵� </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="ckzqpz" value="checkbox" onClick="check(this)">
        ���֧ȡƾ֤</td>
      <td height="30"> <input type="checkbox" name="ckzqpz2" value="checkbox" onClick="check(this)">
        ���֧ȡƾ֤</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="tzzzjfpz" value="checkbox" onClick="check(this)">
        ����ת�˽跽ƾ֤</td>
      <td height="30"> <input type="checkbox" name="tzzzjfpz2" value="checkbox" onClick="check(this)">
        ����ת�˽跽ƾ֤</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="tzzzdfpz" value="checkbox" onClick="check(this)">
        ����ת�˴���ƾ֤</td>
      <td height="30"> <input type="checkbox" name="tzzzdfpz2" value="checkbox" onClick="check(this)">
        ����ת�˴���ƾ֤</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td width="124" align="center">���һ</td>
      <td width="124" height="30" align="center">����</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="ckzqpz" value="checkbox" onClick="check(this)">
        ���֧ȡƾ֤</td>
      <td height="30"> <input type="checkbox" name="ckzqpz2" value="checkbox" onClick="check(this)">
        ���֧ȡƾ֤</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="tzzzjfpz" value="checkbox" onClick="check(this)">
        ����ת�˽跽ƾ֤</td>
      <td height="30"> <input type="checkbox" name="tzzzjfpz2" value="checkbox" onClick="check(this)">
        ����ת�˽跽ƾ֤</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td width="124" align="center">���һ</td>
      <td width="124" height="30" align="center">����</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="jzd" value="checkbox" onClick="check(this)">
        ���˵� </td>
      <td height="30"> <input type="checkbox" name="jzd2" value="checkbox" onClick="check(this)">
        ���˵� </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="tzzzdfpz" value="checkbox" onClick="check(this)">
        ����ת�˴���ƾ֤</td>
      <td height="30"> <input type="checkbox" name="tzzzdfpz2" value="checkbox" onClick="check(this)">
        ����ת�˴���ƾ֤</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.NoRecAndPayID:
	default :
%>
    <tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        û�пɴ�ĵ���</td>
    </tr>
<%
	break;
}
%>

    <!--tr bordercolor="#d6d3ce">
      <td> <input type="checkbox" name="PrintTemplate1" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(1)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate1" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(1)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate2" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(2)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate2" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(2)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate3" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(3)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate3" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(3)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate4" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(4)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate4" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(4)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate5" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(5)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate5" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(5)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate6" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(6)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate6" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(6)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate7" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(7)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate7" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(7)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate8" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(8)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate8" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(8)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate9" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(9)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate9" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(9)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate10" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(10)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate10" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(10)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate11" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(11)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate11" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(11)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="PrintTemplate12" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(12)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate12" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(12)%></td>
    </tr>
    <tr bordercolor="#d6d3ce">
      <td> <input type="checkbox" name="PrintTemplate13" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(13)%></td>
      <td height="30"> <input type="checkbox" name="PrintTemplate13" value="checkbox" onClick="check(this)"> 
        <%=IPrintTemplate.getPrintTemplateNameByID(13)%></td-->

    <tr bordercolor="#d6d3ce"> 
      <td height="30" colspan="2"> <div align="center"> 
          <input type="button" name="Submit" value=" ȷ �� " onClick="sendback1()" class="button">
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

<%@ include file="/common/SignValidate.inc" %>
