<%
/*
���Ʊ���
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*;"				
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<%


	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	long CurrencyID = sessionMng.m_lCurrencyID;
	String strTitle = "���Ŵ������֪ͨ�����ύ";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//�ж��Ƿ��¼//CODE_COMMONMESSAGE_LOGIN=1
	        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//�������
		
		//��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		//�����������ȡ�������

		String strTmp = null;
		long lLoanType = -1;
		long txtContract = -1;
		String txtContractCode = null;
		
		strTmp = (String)request.getAttribute("type");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanType =Long.parseLong(strTmp.trim());
		}
		Log.print("loantype:"+lLoanType);
///////////////////////////////////////////////////////////////////////////////////////////////////


%>



<form name="frm">

<TABLE border=0 class=top height=30 width="71%">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=30><B><%=DataFormat.formatString(strTitle)%></B></TD>
	</TR>
	<TR>
		<TD align=center height=50>
		<TABLE align=center border=0 height=30 width=691>
			<TBODY>
			<TR>
				<TD width="1" height=20>&nbsp;</TD>
				<TD width="1" height=20><font color='#FF0000'>*</font></TD>
<%

				//��ͬ��ŷŴ�
				String strMagnifierNameContract = URLEncoder.encode("��ͬ���");
				String strFormNameContract = "frm";
				String strMainPropertyContract = "";
				String strPrefixContract = "";
				String[] strMainNamesContract = {"txtContractCode"};
				String[] strMainFieldsContract = {"ContractCode"};
				String strReturnInitValuesContract="";
				String[] strReturnNamesContract = {"lContractID"};
				String[] strReturnFieldsContract = {"id"};
				String[] strReturnValuesContract = {"-1"};
				String[] strDisplayNamesContract = {URLEncoder.encode("��ͬ���")};
				String[] strDisplayFieldsContract = {"ContractCode"};
				int nIndexContract = 0;
				System.out.println("lClientID::::::::::::::::::::::::::::::"+lClientID);
				String strSQLContract = "getContractSQL2("+lClientID+","+CurrencyID+")";
				String strMatchValueContract = "";
				String strNextControlsContract = "continus";
				String strTitleContract = "��ͬ���";
				String strFirstTDContract="";
				String strSecondTDContract="";	
				OBMagnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);

%>

				<TD width=300>&nbsp;</TD>
			</TR>
			<tr>
				<td colspan=6>
					<hr>
				</td>
			</tr>
			<TR>
				<TD width="1">&nbsp;</TD>
				<TD colSpan="5">
					<DIV align="right">
						<INPUT onFocus="nextfield='';" class=button name="continus" onclick="frmSubmit(frm)"  type=button value=" ���� ">
											</DIV>
				</TD>
			</TR>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
	</TBODY>
</TABLE>

<input type=hidden name="strAction" value="addnew">
<input type=hidden name="type" value="<%=lLoanType%>">

<P><BR></P>
</form>

<script language="javascript">
function frmSubmit(frm)
{
	if(!checkMagnifier("frm","lContractID","txtContractCode","��ͬ���")) return (false);
	frm.action="d006-c.jsp"
	showSending();
	frm.submit();
	return true;
}
</script>

<script language="JavaScript">
	firstFocus(frm.txtContractCode);
	//setSubmitFunction("frmSubmit(frm)");
	setFormName("frm");
</script>



<%
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
