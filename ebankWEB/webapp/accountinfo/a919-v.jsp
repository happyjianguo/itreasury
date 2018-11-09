<%--
 ҳ������ ��a919-v.jsp
 ҳ�湦�� : �����ջ�ҵ���ӡѡ�����
 ��    �� ��qqgd
 ��    �� ��2003-11-17
 ����˵�� ����ʵ��˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<%
	try
	{
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

			
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
			
			strTemp = (String)request.getParameter("lTransactionTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			
			long lOBReturn=-1;
			strTemp=(String)request.getAttribute("lReturn");
			if ( (strTemp!=null)&&(strTemp.length()>0) )
			{
				lOBReturn=Long.valueOf(strTemp).longValue();
			}

%>	
<safety:resources />

<html>
<head>
<Script Language="JavaScript">
self.resizeTo(200,300);
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
<% 		if ( (lOBReturn==OBConstant.CheckSettType.RecAndPayID)||(lOBReturn==OBConstant.CheckSettType.PayID) )
	  	{  
%> 
			if (document.frmv005.ckzqpz.checked == true)//���֧ȡƾ֤
			{
				strfrmList = strfrmList + "3";
			}
			else
			{
				strfrmList = strfrmList + "a";
			}
			if (document.frmv005.lxtzd.checked == true)//��Ϣ֪ͨ��
			{
				strfrmList = strfrmList + "0";
			}
			else
			{
				strfrmList = strfrmList + "a";
			}
<%
			if (lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{			
%>
				if (document.frmv005.wtdkshpz.checked == true)//ί�д����ջ�ƾ֤
				{
					strfrmList = strfrmList + "p";
				}
				else
				{
					strfrmList = strfrmList + "a";
				}
<%
			}
			else if(lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			{
%>
				if (document.frmv005.zydkshpz.checked == true)//��Ӫ�����ջ�ƾ֤
				{
					strfrmList = strfrmList + "q";
				}
				else
				{
					strfrmList = strfrmList + "a";
				}
<%
			} 
		} 
 		else if ((lOBReturn==OBConstant.CheckSettType.RecAndPayID)||(lOBReturn==OBConstant.CheckSettType.RecID)  )
		{     		
			if (lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{
%>	
				
				if (document.frmv005.tzzz.checked == true)//����ת��
				{
					strfrmList = strfrmList + "2";
				}
<%	
			}	
			else
			{
%>
				strfrmList = strfrmList + "a";
<%
			}
 		} 
%>
		window.open('../common/p001-c.jsp?lID=<%=lID%>&printPage=../accountinfo/a919-p.jsp&lTransactionTypeID=<%=lTransactionTypeID%>&strPrintPage='+strfrmList);
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
function closeThis()
{
	window.close();
}
</Script>
<body bgcolor="#d6d3ce"  text="#000000">
	<form name="frmv005" method="post" action="../accountinfo/a919-p.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
		<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
  <table width="300" class="table" align="center">
  <% if ( (lOBReturn==OBConstant.CheckSettType.RecAndPayID)||(lOBReturn==OBConstant.CheckSettType.PayID) ){  %> 
 	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="ckzqpz" value="checkbox" onClick="check(this)">
          ���֧ȡƾ֤
      </td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="lxtzd" value="checkbox" onClick="check(this)">
          ��Ϣ֪ͨ��
      </td>
    </tr>
	
<%
		if (lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
%>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="wtdkshpz" value="checkbox" onClick="check(this)">
          �����ջ�ƾ֤
      </td>
    </tr>
<%
		}
		else if (lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
%>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="zydkshpz" value="checkbox" onClick="check(this)">
          �����ջ�ƾ֤
      </td>
    </tr>
<%
		}
%>
	 <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30" align="center"><input type="button" name="Submit" value=" ȷ �� " onClick="sendback1()" class="button"></td>
    </tr>

<% } else if ( lOBReturn==OBConstant.CheckSettType.RecID ){     %>
	<%
	if (lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
	{
	%>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="tzzz" value="checkbox" onClick="check(this)">
          ����ת�˴�����Ʊ
      </td>
    </tr>
     <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30" align="center"><input type="button" name="Submit" value=" ȷ �� " onClick="sendback1()" class="button"></td>
    </tr>
   <% } else { %>
	<tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  �޿ɴ�ӡƱ�� </td>
    </tr>
  	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" ȷ �� " onClick="closeThis()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
    </tr>
<% } %>          
    
   <% } else { %>
	<tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  �޿ɴ�ӡƱ�� </td>
    </tr>
  	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" ȷ �� " onClick="closeThis()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
    </tr>
<% } %>          
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
