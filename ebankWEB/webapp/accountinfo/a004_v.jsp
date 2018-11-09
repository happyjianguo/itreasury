<%--
 ҳ������ ��a004_v.jsp
 ҳ�湦�� : �����˻�������ϸ����
 ��    �� ��xirenli
 ��    �� ��2004-02-24
 ����˵�� ����ʵ��˵����
				1��
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<% String strContext = request.getContextPath();%>
<%
try
{
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	/* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
	Collection resultColl = (Collection)request.getAttribute("searchResults");
	OBAccountQueryInfo tempinfo = (OBAccountQueryInfo)request.getAttribute("tempinfo");
	Iterator itResult = null;
	
	String strCurrentAccountNo = "";
	long lAccountID =-1;
	String strDepositNo="";
	long lSubAccountID =-1;
	long lContractID =-1;
	String strTemp = null;
	strTemp = (String)request.getAttribute("lAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("strDepositNo");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    strDepositNo = strTemp;
	}
	strTemp = (String)request.getAttribute("lSubAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lSubAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("lContractID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lContractID = Long.valueOf(strTemp).longValue();
	}
	//strCurrentAccountNo = NameRef.getNoLineAccountNoByID(lAccountID);
	strCurrentAccountNo = NameRef.getAccountNoByID(lAccountID);
	
	if(resultColl != null)
	{
		itResult = resultColl.iterator();
	}
	
	
	String strEarlyBanlance = ""; //�ڳ����
	double mEarlyBanlance = 0.0;
	double mEveryBanlance = 0.0;//ÿ���Ժ�����
	String strExecuteDate = "";    //ִ����
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
	mEarlyBanlance = dao.findEarlyBalance(lAccountID,com.iss.itreasury.settlement.util.UtilOperation.getNextNDay(Env.getSystemDate(tempinfo.getOfficeID(),tempinfo.getCurrencyID()),-1));
	mEveryBanlance = mEarlyBanlance;
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
	//��ʾ�ļ�ͷ
    OBHtml.showOBHomeHead(out, sessionMng, "", OBConstant.ShowMenu.YES);
    //��ת��
   String next="";
   if(request.getAttribute("next")!=null){
      next=(String)request.getAttribute("next");
   }
    String accountType="";
   if(request.getAttribute("accountType")!=null){
      accountType=(String)request.getAttribute("accountType");
   }
%>
<safety:resources />


	<form name="frmV002"  action="../a004_c.jsp">		
		<input type="hidden" name="strSuccessPageURL" value="../a004_v.jsp">
		<input type="hidden" name="strFailPageURL" value="../a004_v.jsp">
		<input type="hidden" name="strAction" value="current">
		<input type="hidden" name="strDepositNo" value="<%=strDepositNo%>">
		<input type="hidden" name="lAccountID" value="<%=lAccountID%>">
		<input type="hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
		<input type="hidden" name="lContractID" value="<%=lContractID%>">
		<input type="hidden" name="lCurrencyID" value="<%=tempinfo.getCurrencyID() %>">
		<input type="hidden" name="lOfficeID" value="<%=tempinfo.getOfficeID() %>">

 <table cellpadding="0" cellspacing="0" class="title_top" width="98%">
  <tr>
    <td height="22">
	    <table cellspacing="0" cellpadding="0" class=title_Top1 >
			<TR>
		       <td class=title><span class="txt_til2">�˻���ϸ</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
		<TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
				     <TD width="1%">&nbsp;</TD>
					<TD width="*%">
						<br><TABLE width="100%" id="flexlist"></TABLE><br>
					</TD>
					 <TD width="1%">&nbsp;</TD>
				</tr>
			</TBODY>
		</TABLE>
	
	<br/>
	<table width=100% border="0" cellspacing="0" cellpadding="0">
		<tr> 
		    <td  colspan="3" align="left" valign="top" height="16" class="graytext"><br>
	<%
				Timestamp ts=Env.getSystemDateTime(); 
	%>
		      <span class="graytext">��ѯ���ڣ�<%=ts.toString().substring(0,10)%> ��ѯʱ�䣺<%=ts.toString().substring(10,19)%></span><br>
		    </td>
		   </tr>
    </table>
    <br/>
	<table width="100%" border="0" cellspacing="2" cellpadding="2" height="15" >
		<tr>
			<td height="19" nowrap>
				<div align="right">
					<input type="button" name="Submit23" value=" ���ز�ѯ���" class="button1"onClick="doDownLoad();">&nbsp;&nbsp;
					<input type="button" name="Submit24" value=" �� ӡ " class="button1" onClick="doReport();">&nbsp;&nbsp;
					<input type="button" name="Submit25" value=" �� �� " class="button1" onClick="goBack('<%=next%>','<%=accountType%>');">
				</div>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
	</form>
<script language="JavaScript">
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '���ױ��',  name : 'TransNo', width : 120, sortable : false, align: 'center'},
			{display: '��������',  name : 'TransactionTypeID', width : 100, sortable : false, align: 'center'},
			{display: 'ժҪ',  name : 'Abstract', width : 120, sortable : false, align: 'center'},
			{display: '�Է��˺�',  name : 'OppAccountID', width : 120, sortable : false, align: 'center'},
			{display: '�Է��˻�����',  name : 'OppAccountID', width : 100, sortable : false, align: 'center'},
			{display: 'ƾ֤��/֧Ʊ��',  name : 'BankChequeNo', width : 100, sortable : false, align: 'center'},
			{display: '�跽���',  name : 'Amount', width : 100, sortable : false, align: 'center'},
			{display: '�������',  name : 'Amount', width : 100, sortable : false, align: 'center'},
			{display: '���',  name : 'Banlance', width : 120, sortable : false, align: 'center'}
		],//�в���
		title:'���ڴ�<%=strCurrentAccountNo%>',
		classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryCurrentBalaceDetail',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmV002","flexlist");
}

function goBack(a,b){
if(a==8){
location.href='a008-v.jsp?accountType='+b;
}else{
 location.href='a002_v.jsp';
}
}
function doReport() {
<%--    document.frmV002.target='blank_';--%>
<%--    document.frmV002.action = "<%=strContext%>/accountinfo/a004_c.jsp";--%>
<%--    document.frmV002.strSuccessPageURL.value = "/accountinfo/a005-p.jsp";--%>
<%--    document.frmV002.strFailPageURL.value = "/accountinfo/a005-p.jsp";--%>
<%--    document.frmV002.submit();--%>
	window.open('<%=strContext%>/accountinfo/a004_c.jsp?strSuccessPageURL=/accountinfo/a005-p.jsp&strFailPageURL=/accountinfo/a005-p.jsp&strAction=current&lAccountID=<%=lAccountID%>&lContractID=<%=lContractID%>&strDepositNo=<%=strDepositNo%>&lSubAccountID=<%=lSubAccountID%>&strStartClientCode=<%=NameRef.getClientCodeByID(sessionMng.m_lClientID)%>&strEndClientCode=<%=NameRef.getClientCodeByID(sessionMng.m_lClientID)%>&lAccountStatusID=1&lIsFiltrate=-1&UrlType=ebank&e_lOfficeID=<%=sessionMng.m_lOfficeID%>&e_lCurrencyID=<%=sessionMng.m_lCurrencyID%>',"NewWin",'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;'); 	
}

function doDownLoad()
{
    document.frmV002.target='blank_';
    document.frmV002.action = "<%=strContext%>/accountinfo/a004_c.jsp";
    document.frmV002.strSuccessPageURL.value = "/accountinfo/a005-e.jsp";
    document.frmV002.strFailPageURL.value = "/accountinfo/a005-e.jsp";
    document.frmV002.submit();
}
</script>
	<%	    
	OBHtml.showOBHomeEnd(out);
%>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>