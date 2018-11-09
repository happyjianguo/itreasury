<%--
/*
 * �������ƣ�batchck001-v.jsp
 * ����˵�����������˲�ѯҳ��
 * �������ߣ�����ξ
 * ������ڣ�2007��04��20��
 */   
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo,
				com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao,
				java.util.*,
				com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%!/* ����̶����� */
	String strTitle = "[��������]";%>
<%
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	/* ʵ������Ϣ�� */
	//ʵ��
	
	String strStartDate = null;//��һ��ҳ�洫���Ŀ�ʼ����
	
	String strEndDate = null;//�ϸ�ҳ�洫���Ľ�������
	

	
	FinanceInfo info = new FinanceInfo();
	List infoList = null;
	//���ӽ���ҳ��
	String strURL = null;
	//�ɹ�����ҳ��
	String strSuccessURL = "batchck004-v.jsp";
	//ʧ�ܷ���ҳ��
	String strFaileURL = "batchck004-v.jsp";
	//��ѯ��
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try {
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		
		if(request.getAttribute("strStartDate")!=null)
		{
			
			strStartDate =(String)request.getAttribute("strStartDate");
			System.out.println("strStartDate============="+strStartDate);
		}
		
		
		if(request.getAttribute("strEndDate")!=null)
		{
			
			strEndDate = (String)request.getAttribute("strEndDate");			
		}
		
		
		if(request.getAttribute("infoList")!=null)
		{
			//�õ��������ʾ�������
			infoList = (List)request.getAttribute("infoList");
		}
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<html>
<head>
</head>

<safety:resources />

<body>
<form method="post" name="frm">
<!--�ж��Ǹ��˻���ȡ�������ֶ�,�˴�Ϊδ���� -->
<input type="hidden" name="sStatus" value="1"/>
<input type="hidden" name="userID" value="<%=sessionMng.m_lUserID %>"/>
<input type="hidden" name="clientID" value="<%=sessionMng.m_lClientID %>"/>
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">���������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

<br/>

<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
	<tr><td></td></tr>
	<tr>
		
		<td class="graytext" width="120" align="right">��ʼ���ڣ�</td>
		<td>
		<%  Timestamp ts=Env.getSystemDateTime();
		System.out.println("_______________ts____________________"+ts);		
		 %>
		<fs_c:calendar 
			          	    name="strStartDate"
				          	value="" 
				          	properties="nextfield ='strEndDate'" 
				          	size="20"/>
		<script>
	          		$('#strStartDate').val('<%=(request.getAttribute("strStartDate")!=null&&!request.getAttribute("strStartDate").equals(""))?request.getAttribute("strStartDate"):ts.toString().substring(0,10)%>');
	    </script>
		</td>
	</tr>
	<tr>
		
		<td class="graytext" width="120" align="right">�������ڣ�</td>
		<td>
			<fs_c:calendar 
          	    name="strEndDate"
	          	value="" 
	          	properties="nextfield ='submit1'" 
	          	size="20"/>
	          	<script>
	          		$('#strEndDate').val('<%=(request.getAttribute("strEndDate")!=null&&!request.getAttribute("strEndDate").equals(""))?request.getAttribute("strEndDate"):ts.toString().substring(0,10)%>');
	    </script>
		</td>
		
	</tr>
	<tr>
		<td colspan="7"></td>
		<td width="15">
		<input class="button1" name="submit1" type="button" value=" �� ѯ "   onclick="Javascript:query();" onKeyDown="Javascript:query();"> 
		</td>
		<td></td>
	</tr>
	<tr><td></td></tr>
</table>
<br/>

	
<p>&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
	
<table class="top" width="100%">
    <tbody>
     <tr>
        <td width="1%">&nbsp;</td>
        <td width="*%">
		   <br><TABLE width="100%" id="flexlist"></TABLE><br>
		</td>
		<td width="1%">&nbsp;</td>
		</tr>
	</tbody>
	</table>

</td></tr>
</table>
</form>


<script language="javascript">
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '���κ�', name: 'sbatchno', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?")', width: 200, sortable: true, align: 'center'},
			{display: '�ύ��',  name : 'sname', width : 200, sortable : true, align: 'center'},
			{display: '�ύʱ��',  name : 'dtconfirm', width : 200, sortable : true, align: 'center'},
			{display: '�ܽ��',  name : 'mamount', width : 200, sortable : true, align: 'center'},
			{display: '�ܱ���',  name : 'ncount', width : 200, sortable : true, align: 'center'}
		],//�в���
		title:'����������',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryBatchCheckInfo',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sbatchno" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData
	});
	
});

function getFormData() 
{
	return $.addFormData("frm","flexlist");
}

function query()
{	
	if(validate(frm)){
		$.gridReload("flexlist");
	}
}
function validate()
{
	var ret = true;
	if(document.all("strStartDate").value == '')
	{
		alert("��ʼ���ڲ���Ϊ�գ���¼��");
		document.frm.strStartDate.focus();
		return false;
	}
	
	if(document.all("strEndDate").value == '')
	{
		alert("�������ڲ���Ϊ�գ���¼��");
		document.frm.strEndDate.focus();
		return false;
	}
	if(document.all("strStartDate").value>document.all("strEndDate").value)
	{
		alert("��ʼ���ڲ��ܴ��ڽ�������");
		document.frm.strStartDate.focus();
		return false;
	}
	return ret;
}
function doLink(startDate,endDate,batchNo){
	var url = "batchck004-v.jsp?sStatus=1&strStartDate="+startDate+"&strEndDate="+endDate+"&sbatchno=" + batchNo
					+ "&strSuccessURL=batchck004-v.jsp&strFaileURL=batchck004-v.jsp";
	window.location.href = url;
}
</script>
<script language="javascript">
  /* ҳ�潹�㼰�س����� */
    firstFocus(frm.strStartDate);
    //setSubmitFunction("addme()");
    setFormName("frm");
</script>
<% 
	/*if(request.getAttribute("infoList")!=null)
		{
			//�õ��������ʾ�������
			out.print("<script language = 'javascript'>");
			out.print("document.all('result').style.display='block';");
			out.print("</script>");
		}*/
%>
</body>
</html>

<%
		/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/SignValidate.inc" %>