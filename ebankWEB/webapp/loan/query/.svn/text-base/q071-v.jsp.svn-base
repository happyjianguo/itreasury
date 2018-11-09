<%
/**
 * 页面名称 ：q071-v.jsp
 * 页面功能 : 查询——贷款展期申请查询——高级查询的页面
 * 作    者 ：qqgd
 * 日    期 ：2003-11-18
 * 特殊说明 ：
 *			
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<%
	String strOfficeName = sessionMng.m_strOfficeName;
	String strUserName = sessionMng.m_strUserName;
	String strTableTitle = "贷款展期申请查询";
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
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
		//定义变量，获取请求参数
		long lStatusID=-1;
		long lLoanType=-1;
		long recordCount=-1;
		
		termInfo.reset();
		
		OBHtml.showOBHomeHead(out,sessionMng,strTableTitle,Constant.YesOrNo.YES);
%>		
<form name="frm">
<input type=hidden name=txtAction value=query>
<table width="80%" border="0" class="top">
	<TR class="tableHeader">
		<TD class=FormTitle height=29><B><%=strTableTitle%></B></TD>
	</TR>
	<TR>
		<TD height=80>
		<TABLE border=0 height=80 width="730">
			<TBODY>
				<TR>
					<TD>&nbsp;</TD>
					<TD>贷款种类：</TD>
					<TD>
						<SELECT class=box name=typeID onfocus="nextfield='txtContractCodeBeg'"  onchange="Javascript:getLoanType();">
		<%
						long[] lLoanTypeTmp = LOANConstant.LoanType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);//得到项目的贷款种类
						if (lLoanType==-1) 
						{
		%>					<OPTION value="99" SELECTED></OPTION>
		<%				}
						else
						{
		%>					<OPTION value="99"></OPTION>
		<%				}
						for(int i=0;i<lLoanTypeTmp.length;i++)
						{
							if (lLoanType==lLoanTypeTmp[i]) 
							{
			%>					<OPTION value="<%=lLoanTypeTmp[i]%>" SELECTED>
									<%=LOANConstant.LoanType.getName(lLoanTypeTmp[i])%>
								</OPTION>
			<%				}
							else
							{
			%>					<OPTION value="<%=lLoanTypeTmp[i]%>">
									<%=LOANConstant.LoanType.getName(lLoanTypeTmp[i])%>
								</OPTION>
			<%				}
						}
			%>
						</SELECT> 
					</TD>
				</TR>
				<TR>
					<TD><font color='#FF0000'>&nbsp;</font></TD>
<%
				//合同编号放大镜
				String strMagnifierNameContract = URLEncoder.encode("合同编号");
				String strFormNameContract = "frm";
				String strMainPropertyContract = "";
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
				String strMatchValueContract = "ContractCode";
				String strNextControlsContract = "txtContractCodeEnd";
				String strTitleContract = "合同编号  由";
				String strFirstTDContract="";
				String strSecondTDContract="";
				Magnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);


%>
					<TD><font color='#FF0000'>&nbsp;</font></TD>
<%
				//合同编号放大镜
				String strMagnifierNameContract2 = URLEncoder.encode("合同编号");
				String strFormNameContract2 = "frm";
				String strMainPropertyContract2 = "";
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
				String strNextControlsContract2 = "statusID";
				String strTitleContract2 = "到";
				String strFirstTDContract2="";
				String strSecondTDContract2="";
				Magnifier.showZoomCtrl(out,strMagnifierNameContract2,strFormNameContract2,strPrefixContract2,strMainNamesContract2,strMainFieldsContract2,strReturnNamesContract2,strReturnFieldsContract2,strReturnInitValuesContract2,strReturnValuesContract2,strDisplayNamesContract2,strDisplayFieldsContract2,nIndexContract2,strMainPropertyContract2,strSQLContract2,strMatchValueContract2,strNextControlsContract2,strTitleContract2,strFirstTDContract2,strSecondTDContract2);
%>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
					<TD>申请状态：</TD>
					<TD>
						<SELECT class=box name=statusID size=1 onfocus="nextfield='amountBegin'" > 
							<OPTION value="99" selected></OPTION> 
			<%
							long[] StatusTmp = LOANConstant.ExtendStatus.getAllCode();
							for(int i=0;i<StatusTmp.length;i++)
							{
								if (lStatusID==StatusTmp[i]) 
								{
				%>					<OPTION value="<%=StatusTmp[i]%>" SELECTED>
										<%=LOANConstant.ExtendStatus.getName(StatusTmp[i])%>
									</OPTION>
				<%				}
								else
								{
				%>					<OPTION value="<%=StatusTmp[i]%>">
										<%=LOANConstant.ExtendStatus.getName(StatusTmp[i])%>
									</OPTION>
				<%				}
							}
				%>
						</SELECT>
					</TD>
					<TD>&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD colSpan=12>
						<hr>
					</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
					<TD>已到期贷款余额  由：</TD>
					<TD> <%=sessionMng.m_strCurrencySymbol%>
						<script language="javascript">
							createAmountCtrl("frm","amountBeginStr","<%=DataFormat.formatAmount(0)%>","amountEndStr","");
						</script>
					<TD>&nbsp;</TD>
					<TD>到：</TD>
					<TD><%=sessionMng.m_strCurrencySymbol%>
						<script language="javascript">
							createAmountCtrl("frm","amountEndStr","<%=DataFormat.formatAmount(0)%>","amountBegin2Str","");
						</script>
					</TD>
					<TD colSpan=5>&nbsp;</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
					<TD>贷款展期金额    由：</TD>
					<TD> <%=sessionMng.m_strCurrencySymbol%>
						<script language="javascript">
							createAmountCtrl("frm","amountBegin2Str","<%=DataFormat.formatAmount(0)%>","amountEnd2Str","");
						</script>
					<TD>&nbsp;</TD>
					<TD>到：</TD>
					<TD><%=sessionMng.m_strCurrencySymbol%>
						<script language="javascript">
							createAmountCtrl("frm","amountEnd2Str","<%=DataFormat.formatAmount(0)%>","loanDateBegin","");
						</script>
					</TD>
					<TD colSpan=5>&nbsp;</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
					<TD>贷款日期      由：</TD>
					<TD>
						<fs_c:calendar 
			         	    name="loanDateBegin"
				          	value="" 
				          	properties="nextfield ='loanDateEnd'" 
				          	size="21"/>
					</TD>
					<TD>&nbsp;</TD>
					<TD>到：</TD>
					<TD>
					<fs_c:calendar 
			         	    name="loanDateEnd"
				          	value="" 
				          	properties="nextfield ='Search'" 
				          	size="21"/>
					</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
					<TD colSpan="8" height=20>
						<DIV align="right">
							<INPUT onFocus="nextfield=''" class=button name="Search" onclick="frmSubmit(frm)"  type=button value=" 查找 ">
							<INPUT onFocus="nextfield=''" class=button name="Back" onclick="frmSubmit2(frm)"  type=button value=" 返回 ">
						</DIV>
					</TD>
				</TR>
				<TR>
					<TD>&nbsp;</TD>
				</TR>
			</TBODY>
		</TABLE>
		</TD>
	</TR>
</TABLE>
	<input type=hidden name="LoanType" value="">
	<input type=hidden name="actType" value="query">
	<input type="hidden" name="queryLevel" value="high">
</form>
<br>

<!---------------------------------------------body----------------------------------------------->


<script language="javascript">
	firstFocus(frm.typeID);
	//setSubmitFunction("frmSubmit(frm)");
	setFormName("frm");
</script>

<script language="javascript">
function frmSubmit(frm)
{
	if(!check(frm)) return false;
	frm.action="q072-c.jsp";
	showSending();
	frm.submit();
	return true;
}
</script>

<script language="javascript">
function frmSubmit2(frm)
{
	frm.action="q070-v.jsp";
	showSending();
	frm.submit();
	return true;
}
</script>
<script language="javascript">
function getLoanType()
{
	frm.LoanType.value = frm.typeID.value;
}
</script>

<script language="javascript">
function check(frm)
{
	if(!checkAmount(document.frm.amountBeginStr,0,"已到期贷款余额")) 	return (false);
	if(!checkAmount(document.frm.amountEndStr,0,"已到期贷款余额")) 	return (false);
	if (parseFloat(reverseFormatAmount1(frm.amountBeginStr.value)) > parseFloat(reverseFormatAmount1(frm.amountEndStr.value)))
	{
		alert("已到期贷款余额不能由大到小！");
		return(false);
	}
	if(!checkAmount(document.frm.amountBegin2Str,0,"贷款展期金额")) 	return (false);
	if(!checkAmount(document.frm.amountEnd2Str,0,"贷款展期金额")) 	return (false);
	if (parseFloat(reverseFormatAmount1(frm.amountBegin2Str.value)) > parseFloat(reverseFormatAmount1(frm.amountEnd2Str.value)))
	{
		alert("贷款展期金额不能由大到小！");
		return(false);
	}	
	if(!checkDate(document.frm.loanDateBegin,0,"贷款日期")) 	return (false);
	if(!checkDate(document.frm.loanDateEnd,0,"贷款日期")) 	return (false);
	if (!CodeCompare(frm.loanDateBegin,frm.loanDateEnd,"贷款日期 "))  return(false);
	else	return true
}

	//编号比较
	function CodeCompare(d_input1,d_input2,d_str)
	{
		if (d_input1.value.length>0 && d_input2.value.length>0)
		{
			if (d_input1.value>d_input2.value)
			{
				alert(d_str+"不能由大至小。");
				d_input1.focus();
				return (false);
			}
		}
		return true;
	}

	function  getContractSQL()
	{
		var sql="select id, sContractCode as ContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%> and NBORROWCLIENTID=<%=sessionMng.m_lClientID%>  and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
</Script>

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