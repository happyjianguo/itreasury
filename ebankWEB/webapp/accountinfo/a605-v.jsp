<%--
 ҳ������ ��v019.jsp
 ҳ�湦�� : ���ŷ���ҵ���ӡѡ�����
 ��    �� ��gqzhang	
 ��    �� ��2004-5-25
 ����˵�� ����ʵ��˵����
 						//���ŷ���
 						���˵�
						���Ŵ����ƾ֤
						���Ŵ���������ȡƾ֤
						���е�㡢�Ż�ƾ֤����Ʊί����
						
						
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>		
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%
	try
	{
			long lID = -1;
			
			//ҳ�渨������
			String strAction = null;
			String strSuccessPageURL = "";
			String strFailPageURL = "";
			String strActionResult = Constant.ActionResult.FAIL;
			long lTransactionTypeID = -1;
			String strTransNo = "";
			
			String strTemp = "";
			strTemp = (String)request.getParameter("lID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)request.getParameter("lTransactionTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)request.getParameter("strTransNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strTransNo = strTemp;
			}
			
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
			strFailPageURL = (String)request.getAttribute("strFailPageURL");

			OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();

			long lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
				sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			Log.print("=================lReturn="+lReturn);
%>	
<safety:resources />

<html>
<head>
<Script Language="JavaScript">
self.resizeTo(180,370);
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
	if (num == 0)
	{ 
		alert("��ѡ����Ҫ��ӡ�����ϣ�");
	}
	else 
	{
		if (document.frmv001.jzd.checked == true)
		{
			strfrmList = strfrmList + "1";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv001.ffpz.checked == true)
		{
			strfrmList = strfrmList + "2";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		window.open('a606-c.jsp?lID=<%=lID%>&strPrintPage='+strfrmList);
		window.close();
	}
}

function check(box)
{
  if (box.checked == false) 
  {
  	num = num + 1;
  }
  else
  {
  	num = num - 1;
  }
}
</Script>
<body bgcolor="#d6d3ce"  text="#000000">
	<form name="frmv001" method="post" action="../control/c001.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
  <table width="150" class="table" align="center">
  <%
  	if ( lReturn==OBConstant.CheckSettType.RecAndPayID)
	{
  %>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="jzd" value="checkbox" onClick="check(this)">
          ���˵�
      </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        <input type="checkbox" name="ffpz" value="checkbox" onClick="check(this)">
        ���Ŵ����ƾ֤</td>
    </tr>
	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" ȷ �� " onClick="sendback1()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
    </tr>
	<%}
	else{
	%>
	<tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        û�пɴ�ĵ���</td>
    </tr>
	<%}%>
  </table>

</form>
</body>
</html>
<%
    }
	catch( Exception exp )
	{
		exp.printStackTrace();
		Log.print(exp.getMessage());
	}
%>

<%@ include file="/common/SignValidate.inc" %>