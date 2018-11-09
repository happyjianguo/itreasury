<%
/**
 * ҳ������ ��q060-v.jsp
 * ҳ�湦�� : ��ѯ������ִͬ�����
 * ��    �� ��qqgd
 * ��    �� ��2003-11-18
 * ����˵�� ��
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%
	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	String strTableTitle = "����ʵ��ִ�������ѯ";
//////////////////////////////////////////////////////////////////////////////////////////////

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

		OBHtml.showOBHomeHead(out,sessionMng,strTableTitle,Constant.YesOrNo.YES);
%>
<form name="frm">
<table width="80%" border="0" class="top">
	<TR class="tableHeader">
		<TD class=FormTitle height=29><B><%=strTableTitle%></B></TD>
	</TR>
	<TR>
		<TD align=center height=80>
		<TABLE align=center border=0 height=80 width=730>
			<TBODY>
			<TR>
				<TD width="1" height=20><font color='#FF0000'>*</font></TD>
<%
				//��ͬ��ŷŴ�
				String strMagnifierNameContract = URLEncoder.encode("��ͬ���");
				String strFormNameContract = "frm";
				String strMainPropertyContract = "";
				String strPrefixContract = "";
				String[] strMainNamesContract = {"txtContractCode"};
				String[] strMainFieldsContract = {"sContractCode"};
				String strReturnInitValuesContract="";
				String[] strReturnNamesContract = {"lContractID"};
				String[] strReturnFieldsContract = {"id"};
				String[] strReturnValuesContract = {"-1"};
				String[] strDisplayNamesContract = {URLEncoder.encode("��ͬ���")};
				String[] strDisplayFieldsContract = {"sContractCode"};
				int nIndexContract = 0;
				String strSQLContract = "getContractCode()";
				String strMatchValueContract = "sContractCode";
				String strNextControlsContract = "continus";
				String strTitleContract = "��ͬ���";
				String strFirstTDContract="";
				String strSecondTDContract="";
				Magnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);
%>
				<TD width="350" height=20>&nbsp;</TD>
			</TR>
			<TR>
				<TD width="1" height=20>&nbsp;</TD>
				<TD colSpan="5" height=20>
					<DIV align="right">
						<INPUT onFocus="nextfield=''" class=button name="continus" onclick="frmSubmit(frm)"  type=button value=" ���� ">
					</DIV>
				</TD>
			</TR>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
</TABLE>
	<input type=hidden name="control" value="view">
	<input type=hidden name="actType" value="query">
</form>
<br>

<script language="javascript">
	firstFocus(frm.txtContractCode);
	//setSubmitFunction("frmSubmit(frm)");
	setFormName("frm");
</script>

<script language="javascript">
function frmSubmit(frm)
{
	if(!checkMagnifier("frm","lContractID","txtContractCode","��ͬ���")) return (false);
	frm.action="q061-c.jsp";
	showSending();
	frm.submit();
	return true;
}

function  getContractCode()
	{
		var sql="select id, sContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%> and nBorrowClientID=<%=sessionMng.m_lClientID%>  and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
</script>

<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
		out.flush();
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>