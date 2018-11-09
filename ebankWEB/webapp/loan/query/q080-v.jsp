<%
/**
 * 页面名称 ：q080-v.jsp
 * 页面功能 : 免还申请查询-普通查询条件页面
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 转入页面 : 
 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		termInfo.reset();
   		
        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"免还申请查询",Constant.YesOrNo.YES);
        long officeID=sessionMng.m_lOfficeID;
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="87%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>免还申请查询</B></TD></TR>
  <TR>
    <TD>
	<TABLE align=center border=0 width=730>
	<TBODY>
		<tr>
			<TD height=28 >贷款种类：</TD>
			<TD height=28 >
			<SELECT class=box name=typeID onfocus="nextfield='txtContractCodeBeg'" >
					<OPTION value="-1" SELECTED></OPTION>
					<OPTION value="<%=LOANConstant.LoanType.WT%>">
						<%=LOANConstant.LoanType.getName(LOANConstant.LoanType.WT)%>
					</OPTION>
			</SELECT> 
			</TD>
		</tr>
		<TR>

<%				//合同编号放大镜
				String strMagnifierNameContract = URLEncoder.encode("合同编号");
				String strFormNameContract = "frm";
				String strMainPropertyContract = "size='22'";
				String strPrefixContract = "";
				String[] strMainNamesContract = {"txtContractCodeBeg"};
				String[] strMainFieldsContract = {"ContractCode"};
				String strReturnInitValuesContract="";
				String[] strReturnNamesContract = {"codeBegin"};
				String[] strReturnFieldsContract = {"ContractCode"};
				String[] strReturnValuesContract = {""};
				String[] strDisplayNamesContract = {URLEncoder.encode("合同编号")};
				String[] strDisplayFieldsContract = {"ContractCode"};
				int nIndexContract = 0;
				String strSQLContract = "getContractSQL()";
				//String[] strNextControlsContract = {"txtContractCodeEnd"};
				String strMatchValueContract = "ContractCode";
				String strNextControlsContract = "txtContractCodeEnd";
				String strTitleContract = "合同编号 由";
				String strFirstTDContract="";
				String strSecondTDContract="";	
				Magnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);
%>
<%				//合同编号放大镜
				String strMagnifierNameContract2 = URLEncoder.encode("合同编号");
				String strFormNameContract2 = "frm";
				String strMainPropertyContract2 = "size='22'";
				String strPrefixContract2 = "";
				String[] strMainNamesContract2 = {"txtContractCodeEnd"};
				String[] strMainFieldsContract2 = {"ContractCode"};
				String strReturnInitValuesContract2="";
				String[] strReturnNamesContract2 = {"codeEnd"};
				String[] strReturnFieldsContract2 = {"ContractCode"};
				String[] strReturnValuesContract2 = {""};
				String[] strDisplayNamesContract2 = {URLEncoder.encode("合同编号")};
				String[] strDisplayFieldsContract2 = {"ContractCode"};
				int nIndexContract2 = 0;
				String strSQLContract2 = "getContractSQL()";
				String strMatchValueContract2 = "ContractCode";
				String strNextControlsContract2 = "Search";
				String strTitleContract2 = "到";
				String strFirstTDContract2="";
				String strSecondTDContract2="";	
				Magnifier.showZoomCtrl(out,strMagnifierNameContract2,strFormNameContract2,strPrefixContract2,strMainNamesContract2,strMainFieldsContract2,strReturnNamesContract2,strReturnFieldsContract2,strReturnInitValuesContract2,strReturnValuesContract2,strDisplayNamesContract2,strDisplayFieldsContract2,nIndexContract2,strMainPropertyContract2,strSQLContract2,strMatchValueContract2,strNextControlsContract2,strTitleContract2,strFirstTDContract2,strSecondTDContract2);
%>
			<TD height=28 >&nbsp;</TD>
			<TD colSpan=5 height=28>&nbsp;</TD>
		</TR>
		<TR>
			<TD colSpan=5>&nbsp;</TD>
			<TD align=right>&nbsp;</TD>
			<TD align=right colSpan=6 vAlign=top>
				<INPUT class=button name=Search  type=button value=" 查 找 " onclick="frmSubmitFine(frm)" onKeydown="if(event.keyCode==13) frmSubmitFine(frm);"> 
			</TD>
			<TD align=right>&nbsp;</TD>
		</TR>
		</TBODY>
	</TABLE>
		  
	</TD></TR></TBODY></TABLE>
		  
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="low">
<input type="hidden" name="actType" value="query">

</form>
<script language="JavaScript">
function frmSubmitFine(frm)
{
	frm.action="q081-c.jsp";
	frm.submit();
}

firstFocus(frm.typeID);
//setSubmitFunction("frmSubmitFine()");
setFormName("frm");
</script> 
<script language="javascript">
	function  getContractSQL()
	{
		var sql="select id, sContractCode as ContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%> and NCONSIGNCLIENTID=<%=sessionMng.m_lClientID%>  and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
</script>		 
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"免还申请查询","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %> 

