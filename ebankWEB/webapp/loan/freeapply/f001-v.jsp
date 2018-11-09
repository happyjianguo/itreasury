<%
/*
���Ʊ���
f001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
				
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	long lClientID = sessionMng.m_lClientID;
	
	String strTitle = "ί�д����⻹���롪����";
	long lLoanType=-1;
	try
	{
	//Log4j log4j = new Log4j(Constant.ModuleType.LOAN);


	
//////////////////////////////////////////////////////////////////////////////////////////////

		Log.print("�û�����  "+lUserID);
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
				
		long txtContract = -1;
		String txtContractCode = null;
		long txtClient = -1;
		String txtClientName = null;

		//log4j.info("loantype:"+lLoanType);
///////////////////////////////////////////////////////////////////////////////////////////////////

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm">

<TABLE border=0 class=top height=181 width="800">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=29><B><%=strTitle%>	</B></TD>
	</TR>
	<TR>
		<TD align=center height=139>
		<TABLE align=center border=0 height=151 width=691>
			<TBODY>
			<TR>
				<TD width="1" height=2>&nbsp;</TD>
				<TD width="1" height=2>&nbsp;</TD>
<%
				//��λ�Ŵ�
				String strMagnifierNameClientName = URLEncoder.encode("�ͻ�");
				String strFormNameClientName = "frm";
				String strMainPropertyClientName = "";
				String strPrefixClientName = "";
				String[] strMainNamesClientName = {"txtClientName","txtContractCode","txtLoanPayCode","lContractID","lLoanPayID"};
				String[] strMainFieldsClientName = {"ClientName","NullName","NullName","NullID","NullID"};
				String strReturnInitValuesClientName="";
				String[] strReturnNamesClientName = {"lClientID"};
				String[] strReturnFieldsClientName = {"id"};
				String[] strReturnValuesClientName = {"-1"};
				String[] strDisplayNamesClientName = {URLEncoder.encode("�ͻ����"),URLEncoder.encode("�ͻ�����")};
				String[] strDisplayFieldsClientName = {"ClientCode","ClientName"};
				int nIndexClientName = 0;
				String strSQLClientName = "getClientSQL()";
				//String[] strNextControlsClientName = {"txtContractCode"};
				String strMatchValueClientName = "ClientCode";
				String strNextControlsClientName = "txtContractCode";
				String strTitleClientName = "��λ";
				String strFirstTDClientName="";
				String strSecondTDClientName="";	
				OBMagnifier.showZoomCtrl(out,strMagnifierNameClientName,strFormNameClientName,strPrefixClientName,strMainNamesClientName,strMainFieldsClientName,strReturnNamesClientName,strReturnFieldsClientName,strReturnInitValuesClientName,strReturnValuesClientName,strDisplayNamesClientName,strDisplayFieldsClientName,nIndexClientName,strMainPropertyClientName,strSQLClientName,strMatchValueClientName,strNextControlsClientName,strTitleClientName,strFirstTDClientName,strSecondTDClientName);

				//Magnifier.showZoomCtrl(out,strMagnifierNameClientName,strFormNameClientName,strPrefixClientName,strMainNamesClientName,strMainFieldsClientName,strReturnNamesClientName,strReturnFieldsClientName,strReturnValuesClientName,strDisplayNamesClientName,strDisplayFieldsClientName,nIndexClientName,strMainPropertyClientName,strSQLClientName,strNextControlsClientName);
%>

				<TD width="350" height=2>&nbsp;</TD>
			</TR>
			<TR>
				<TD width="1" height=2>&nbsp;</TD>
				<TD width="1" height=2><font color='#FF0000'>*</font></TD>
<%
				//��ͬ��ŷŴ�
				String strMagnifierNameContract = URLEncoder.encode("��ͬ���");
				String strFormNameContract = "frm";
				String strMainPropertyContract = "";
				String strPrefixContract = "";
				String[] strMainNamesContract = {"txtContractCode","lClientID","txtClientName","txtLoanPayCode","lLoanPayID"};
				String[] strMainFieldsContract = {"ContractCode","ClientID","ClientName","NullName","NullID"};
				String strReturnInitValuesContract="";
				String[] strReturnNamesContract = {"lContractID"};
				String[] strReturnFieldsContract = {"id"};
				String[] strReturnValuesContract = {"-1"};
				String[] strDisplayNamesContract = {URLEncoder.encode("��ͬ���")};
				String[] strDisplayFieldsContract = {"ContractCode"};
				int nIndexContract = 0;
				String strSQLContract = "getContractSQL1("+lClientID+","+sessionMng.m_lCurrencyID+")";
				//String[] strNextControlsContract = {"txtLoanPayCode"};
				String strMatchValueContract = "";
				String strNextControlsContract = "txtLoanPayCode";
				String strTitleContract = "��ͬ���";
				String strFirstTDContract="";
				String strSecondTDContract="";	
				OBMagnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);

				//Magnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strNextControlsContract);
%>

				<TD width="350" height=2>&nbsp;</TD>
			</TR>
			<TR>
				<TD width="1" height=2>&nbsp;</TD>
				<TD width="1" height=2><font color='#FF0000'>*</font></TD>
<%
				//�ſ�֪ͨ����ŷŴ�
				String strMagnifierNameLoanPay = URLEncoder.encode("�ſ�֪ͨ�����");
				String strFormNameLoanPay = "frm";
				String strMainPropertyLoanPay = "";
				String strPrefixLoanPay = "";
				String[] strMainNamesLoanPay = {"txtLoanPayCode","lContractID","txtContractCode","lClientID","txtClientName"};
				String[] strMainFieldsLoanPay = {"LoanPayCode","ContractID","ContractCode","ClientID","ClientName"};
				String strReturnInitValuesLoanPay="";
				String[] strReturnNamesLoanPay = {"lLoanPayID"};
				String[] strReturnFieldsLoanPay = {"id"};
				String[] strReturnValuesLoanPay = {"-1"};
				String[] strDisplayNamesLoanPay = {URLEncoder.encode("��ͬ���"),URLEncoder.encode("�ſ�֪ͨ�����")};
				String[] strDisplayFieldsLoanPay = {"ContractCode","LoanPayCode"};
				int nIndexLoanPay = 1;
				String strSQLLoanPay = "getLoanPaySQL(frm.lContractID.value"+","+sessionMng.m_lCurrencyID+","+lClientID+")";
				//String[] strNextControlsLoanPay = {"continus"};
				String strMatchValueLoanPay = "";
				String strNextControlsLoanPay = "continus";
				String strTitleLoanPay = "�ſ�֪ͨ�����";
				String strFirstTDLoanPay="";
				String strSecondTDLoanPay="";	
				OBMagnifier.showZoomCtrl(out,strMagnifierNameLoanPay,strFormNameLoanPay,strPrefixLoanPay,strMainNamesLoanPay,strMainFieldsLoanPay,strReturnNamesLoanPay,strReturnFieldsLoanPay,strReturnInitValuesLoanPay,strReturnValuesLoanPay,strDisplayNamesLoanPay,strDisplayFieldsLoanPay,nIndexLoanPay,strMainPropertyLoanPay,strSQLLoanPay,strMatchValueLoanPay,strNextControlsLoanPay,strTitleLoanPay,strFirstTDLoanPay,strSecondTDLoanPay);

				//Magnifier.showZoomCtrl(out,strMagnifierNameLoanPay,strFormNameLoanPay,strPrefixLoanPay,strMainNamesLoanPay,strMainFieldsLoanPay,strReturnNamesLoanPay,strReturnFieldsLoanPay,strReturnValuesLoanPay,strDisplayNamesLoanPay,strDisplayFieldsLoanPay,nIndexLoanPay,strMainPropertyLoanPay,strSQLLoanPay,strNextControlsLoanPay);
%>

				<TD width="350" height=2>&nbsp;</TD>
			</TR>
			<TR>
				<TD width="1" height=2>&nbsp;</TD>
				<TD colSpan="5" height=2>
					<DIV align="right"><FONT size=2>
						<INPUT onFocus="nextfield='';" class=button name="continus" onclick="frmSubmit(frm)"  type=button value=" �� �� ">

					</FONT></DIV>
				</TD>
			</TR>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
	</TBODY>
</TABLE>

<input type=hidden name="strAction" value="addnew">
<input type=hidden name="lFreeApplyID" value="-1">

<P><BR></P>
</form>

<script language="javascript">

function frmSubmit(frm)
{
	if(!checkMagnifier("frm","lContractID","txtContractCode","����ͬ���")) return (false);
	if(!checkMagnifier("frm","lLoanPayID","txtLoanPayCode","�ſ�֪ͨ�����")) return (false);
	frm.action="f002-c.jsp";
	showSending();
frm.submit();


	return true;
}
function  go(frm)
{
	frm.action="S323-1.jsp?control=view"
	showSending();
	
frm.submit();


	return true;
}
</script>
<script language="JavaScript">
	firstFocus(frm.txtClientName);
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
