<%--
/**
 * ҳ������ ��v002_P.jsp
 * ҳ�湦�� : ���׼�¼��ѯ���������ʾ��ҳ��
 * ��    �� ��Boxu
 * ��    �� ��2006-12-12
 * ����˵�� ��
 *			
 * �޸���ʷ ��
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "";
%>
 
<%
try
{
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	OBHtml.validateRequest(out,request,response);

	/* ��ʾ�ļ�ͷ */
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
    
%>
	<jsp:include page="/ShowMessage.jsp"/>
<%
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");

	String strTemp = null;
	QueryTransactionInfo[] resultInfos = (QueryTransactionInfo[])request.getAttribute(Constant.PageControl.SearchResults);

	long lStatusID = -1;
	long lOfficeID = -1;
	String strTransactionType = "";
	String sExecuteStart = "";
	String sExecuteEnd = "";
	String StatusID = ""; // ���׼�¼״̬: save δ���ˣ�check �Ѹ���

	strTemp = (String)request.getAttribute("lTransactionStatusID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{	
		session.setAttribute("lTransactionStatusID",strTemp);
		lStatusID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("officeList");
	if(strTemp !=null && strTemp.trim().length()>0)
	{
		lOfficeID = Long.parseLong(strTemp);
	}
	strTemp = (String)request.getAttribute("strTransactionType");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    strTransactionType = strTemp;
	}
	strTemp = (String)request.getAttribute("tsExecuteStartDate");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    sExecuteStart = strTemp;
	}
	strTemp = (String)request.getAttribute("tsExecuteEndDate");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    sExecuteEnd = strTemp;
	}
	QueryTransactionSumInfo sumInfo = (QueryTransactionSumInfo)sessionMng.getSumResult(strPageLoaderKey);
%>

<form name="frmV012" method="post" action="/NASApp/iTreasury-ebank/print/control/c007_p.jsp">

<input type="hidden" name="StatusID" value="<%=StatusID%>">
<input name="strSuccessPageURL" type="hidden" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strTransNos">
<input type="hidden" name="path">
<input type="hidden" name="_pageLoaderKey" value="<%= strPageLoaderKey %>">
<input type="hidden" name="lTransactionStatusID" value="<%=lStatusID %>">
<input type="hidden" name="officeList" value="<%=lOfficeID %>">
<input type="hidden" name="strTransactionType" value="<%=strTransactionType %>">
<input type="hidden" name="tsExecuteStartDate" value="<%=sExecuteStart %>">
<input type="hidden" name="tsExecuteEndDate" value="<%=sExecuteEnd %>">
<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>">
<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">������ϸ</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		<br/>
	<TABLE border="0" width="100%" class="top">
		<TBODY>
			<tr>
			    <td width="1%">&nbsp;</td>
				<TD width="*%">
					<br><TABLE width="100%" id="flexlist"></TABLE><br>
				</TD>
			    <td width="1%">&nbsp;</td>
			</tr>
		</TBODY>
	</TABLE>	

	<br/>
	<table width=98% border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="605">
				<div align="right"></div>
			</td>
			<td nowrap>
			<div align="right">
				<input class="button1" type=button name="Submit22" value=" �� ӡ " onClick="doPrint(frmV012);">&nbsp;&nbsp;&nbsp;&nbsp;<input class="button1" type=button name="Submit23" value=" �� �� "  onClick="doBack();">
			</div>
			</td>
		</tr>
	</table> 
	</td>
</tr>
</table>
</form>
<form name="listReport" method="post" >
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
	<input name="lTransactionStatusID"  type="hidden" value="<%= lStatusID %>">
</form>

<script language='javascript'>
var flexlist = "flexlist";
$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
        	{elType : 'checkbox', elName : 'printCheck', name : 'ID', width : 40, sortable : true, align: 'center'},
        	{display: '������',  name : 'Execute', width : 120, sortable : true, align: 'center'},
			{display: '���׺�', name: 'TransNo', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?","?","?")', width: 120, sortable: true, align: 'center'},
        	{display: 'ҵ������',  name : 'TransactionTypeID', width : 120, sortable : true, align: 'center'},
        	{display: 'ժҪ',  name : 'Abstract', width : 120, sortable : true, align: 'center'},
        	{display: '�Է��˺�',  name : 'CPF_SREJECT', width : 120, sortable : false, align: 'center'},
        	{display: '�Է��˻�����',  name : 'CPF_SREJECT', width : 120, sortable : false, align: 'center'},
        	{display: '������',  name : 'CPF_SREJECT', width : 120, sortable : false, align: 'center'},
        	{display: '�տ���',  name : 'CPF_SREJECT', width : 120, sortable : false, align: 'center'}
		],//�в���
		title:'������ϸ',
		classMethod : 'com.iss.itreasury.settlement.query.Action.QTransactionAction.queryTransactionInfoForPrint',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData,
		printbutton : false,
		exportbutton : false
	});
	
});
function getFormData() 
{
	return $.addFormData("frmV012","flexlist");
}
function doBack()
{
	window.location.href = '<%=strContext%>'+"/print/view/v001_P.jsp";
}
function doLink(id,transactionTypeID,operationTypeID,transNo,serialNo){
	var url = "<%=strContext%>/print/view/v003_P.jsp?lID="+id+"&TransactionTypeID="+transactionTypeID+"&operationTypeID="+operationTypeID+"&TransNo="+transNo+"&SerialNo="+serialNo+"&strFailPageURL=<%=strContext%>/print/view/v002_P.jsp";
	window.open(url,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
function selectAll()
{	
	if (isNaN(frmV012.printCheck.length))
	{
		if ( frmV012.allCheck.checked == true )
		{
			frmV012.printCheck.checked = true;
		}
		else {
			frmV012.printCheck.checked = false;
		}
	}
	else {
		if(frmV012.allCheck.checked == true)
		{
			for(var i = 0;i < frmV012.printCheck.length;i++)
			{
				frmV012.printCheck[i].checked = true;
			}
		}
		else
		{
			for(var i = 0;i < frmV012.printCheck.length;i++)
			{
				frmV012.printCheck[i].checked = false;
			}
		}
	}
	
}
	
function doPrint(frmV002)
{
	var count = 0;
	var c;
	
	
	$.each($("#" + flexlist + " input[type='checkbox'][name='printCheck']"),function(i,n){
		if(n.checked == true){
			count++; 
		} 
	});
	
	/*
	if (isNaN(frmV002.printCheck.length) == true)
	{
		if(frmV002.printCheck.checked) 
		{
			count++; 
		}
	}
	else
	{
		for(c=0; c < frmV002.printCheck.length; c++) 
		{
			if(frmV002.printCheck[c].checked) 
			{
				count++; 
			}
		}
	}
	*/
	
	if(count == 0)
	{
		alert("������ѡ��һ��ҵ��");
		return false;
	}
	else
	{
	    var strTransNos111 = "";
	    
		$.each($("#" + flexlist + " input[type='checkbox'][name='printCheck']"),function(i,n){
			if(n.checked == true){
				var transno = n.value.split('####')[1];
				strTransNos111 += "," + transno;
			} 
		});
	
		/*
		if (isNaN(frmV002.printCheck.length) == true)
		{
			if (frmV002.printCheck.checked == true)
			{
				strTransNos111 = "," + frmV002.transno.value;
			}
		}
		else
		{
			for (var i=0; i<frmV002.printCheck.length; i++)
			{
				if (frmV002.printCheck[i].checked == true)
				{
					strTransNos111 += "," + frmV002.transno[i].value;
				}
			}
		}
		*/
		
		if (confirm("�Ƿ�������ӡ��"))
		{
			//ֱ�Ӵ�ӡ
			//frmV002.path.value = fcpubdata.Path;
			frmV002.strTransNos.value = strTransNos111;
			frmV002.strSuccessPageURL.value="../view/v003_p.jsp";
			frmV002.strFailPageURL.value="../control/c001_p.jsp";
			frmV002.target="_blank";
			frmV002.submit();
		}
	}
}
</script>
<%
OBHtml.showOBHomeEnd(out);
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />