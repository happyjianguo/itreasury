<%@ page contentType="text/html;charset=gbk" %>
<%@ page import=" java.util.*,java.rmi.*,java.net.URLEncoder"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.loan.util.*,
    			java.sql.*,
                java.rmi.RemoteException,
                java.net.URLEncoder,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>			
<%
//////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
           
		//定义变量，获取请求参数

		//String strTitle = "";
		String strTmp = "";
		String strControl = "";
		long lCurrencyID = -1;
		
		long lContractID = -1;
		String txtContractCode = "";
		//long lClientID = -1;
		String txtClientCode = "";

		long lClientID = sessionMng.m_lClientID;   //客户ID

///////control////////////////////////////////////////////////////////////////////////
	    /*strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp;
		}

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("lClientID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lClientID = Long.parseLong(strTmp.trim());
		}	 

		if (strControl.equals("save"))
		{
			response.sendRedirect("S125.jsp?control=view&lContractID="+lContractID);
            return;
		}*/
				
//////////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out,sessionMng,"贴现凭证",Constant.ShowMenu.YES);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<form name="frm">

<TABLE border=0 class=top height=181 width=740>
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现凭证——新增</B></TD></TR>
  <TR>
    <TD align=center height=130>
      <TABLE align=center border=0 height=130 width=691>
        <TBODY>
        <TR>
		  <td width="1" height="2">&nbsp;</td>

<%
				/*String strMagnifierNameClient = "客户编号";
				String strFormNameClient = "frm";
				String strMainPropertyClient = "";
				String strPrefixClient = "";
				String[] strMainNamesClient = {"txtClientName"};
				String[] strMainFieldsClient = {"ClientName"};
				String strReturnInitValuesClient = "";
				String[] strReturnNamesClient = {"lClientID"};
				String[] strReturnFieldsClient = {"ID"};
				String[] strReturnValuesClient = {""+lClientID+""};
				String[] strDisplayNamesClient = {"客户编号","客户名称"};
				String[] strDisplayFieldsClient = {"ClientCode","ClientName"};
				int nIndexClient = 0;
				String strSQLClient = "getClientSQL(-1,-1,-1)";
				String strMatchValueClient = "ClientCode";
				String strNextControlsClient = "txtContractCode";
				String strTitleClient = "申请单位";
				String strFirstTDClient = "";
				String strSecondTDClient = "borderColor=#999999 height=32 vAlign=center";
				
				Magnifier.showZoomCtrl(out,strMagnifierNameClient,strFormNameClient,strPrefixClient,strMainNamesClient,strMainFieldsClient,strReturnNamesClient,strReturnFieldsClient,strReturnInitValuesClient,strReturnValuesClient,strDisplayNamesClient,strDisplayFieldsClient,nIndexClient,strMainPropertyClient,strSQLClient,strMatchValueClient,strNextControlsClient,strTitleClient,strFirstTDClient,strSecondTDClient);*/
%>

<%				//合同编号放大镜
				String strMagnifierNameContract = URLEncoder.encode("合同编号");
				String strFormNameContract = "frm";
				String strPrefixContract = "";
				String[] strMainNamesContract = {"txtContractCode"};
				String[] strMainFieldsContract = {"ContractCode"};
				String[] strReturnNamesContract = {"lContractID"};
				String[] strReturnFieldsContract = {"ID"};
				String strReturnInitValuesContract = "";
				String[] strReturnValuesContract = {""+lContractID+""};
				String[] strDisplayNamesContract = {URLEncoder.encode("合同编号")};
				String[] strDisplayFieldsContract = {"ContractCode"};
				int nIndexContract=0;
				String strMainPropertyContract = "";
				String strSQLContract = "getContractSQL(4,-1,"+lClientID+","+sessionMng.m_lUserID+","+sessionMng.m_lCurrencyID+")";
				String strMatchValueContract = "";
				String strNextControlsContract = "xzmhtzhd";
				String strTitleContract = "<font color='#FF0000'>* </font>贴现合同编号";
				String strFirstTDContract = "";
				String strSecondTDContract = "borderColor=#999999 height=32 vAlign=center";
				Magnifier.showZoomCtrl(out,strMagnifierNameContract,strFormNameContract,strPrefixContract,strMainNamesContract,strMainFieldsContract,strReturnNamesContract,strReturnFieldsContract,strReturnInitValuesContract,strReturnValuesContract,strDisplayNamesContract,strDisplayFieldsContract,nIndexContract,strMainPropertyContract,strSQLContract,strMatchValueContract,strNextControlsContract,strTitleContract,strFirstTDContract,strSecondTDContract);

%>

		<td colspan="3" height="2">&nbsp;</td>
		</TR>
        <TR> 
          <td width="1" height="2">&nbsp;</td>
          <td colspan="5" height="2"> 
            <hr>
          </td>
        </TR>
       	<TR>
          <TD width="1" height=2>&nbsp;</TD>
          <TD colSpan="5" height=2>
          <DIV align="right">
		
		<INPUT class=button name="xzmhtzhd" onFocus="nextfield='submitfunction';" onclick="confirmSave(frm)" type=button value=" 新增贴现凭证 "> 

</DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type=hidden name="control" value="view">

<P><BR></P>
</form>

<script language="javascript">
function confirmSave(frm)
{
	if (!checkMagnifier("frm","txtContractCode","txtContractCode","贴现合同编号"))
	{
		return false;
	}
	if (frm.lContractID.value < 0)
	{
	        alert("贴现合同编号的值请从放大镜里带出！");
		return false;
	}
		
	frm.action="d031-c.jsp";
	frm.control.value="save";
	showSending();
	frm.submit();
	return true;		
}

/**==========合同放大镜SQL语句===========
* lType  查找类型  1：委托免还；  2：逾期  3：放款通知单
* lLoanType 贷款种类
* lClientID 客户标识
* lInputUserID 用户标识
*=====================================*/
function getContractSQL(lType,lLoanType,lClientID,lInputUserID,lCurrencyID)
{
	var strSQL = " select a.sContractCode as ContractCode, a.id as ID ";
		strSQL +=" ,b.ID as ClientID,b.sName as ClientName ";
		strSQL += " ,-1 as NullID,'' as NullName ";//NullID NullName 是用来清空由合同ID决定的放款单编号等等
		strSQL +=" from LOAN_ContractForm a,Client b where 1=1 ";
		strSQL +=" and a.NBORROWCLIENTID=b.ID(<%=URLEncoder.encode("+")%>) ";
		
	if(lCurrencyID > 0)
	{
		strSQL += " and a.NCURRENCYID = " + lCurrencyID;
	}
		
	if(lType==1)//查找委托免还的合同
	{
		strSQL += " and a.nTypeID in (<%=LOANConstant.LoanType.WT%>,<%=LOANConstant.LoanType.WT%>)";//委托免还
	}
	else if(lType==2)//查找逾期的合同
	{
		strSQL += " and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";//逾期没有 “贴现“ 贷款类型
	}
	else if(lType==3)//查找放款通知单的合同，没有 “贴现“ 贷款类型
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID != <%=LOANConstant.LoanType.TX%> ";
	}
	else if(lType==4)//查找贴现凭证的合同
	{
		strSQL += " and a.nStatusID in (<%=LOANConstant.ContractStatus.ACTIVE%>,<%=LOANConstant.ContractStatus.NOTACTIVE%>,<%=LOANConstant.ContractStatus.EXTEND%>) and a.nTypeID = <%=LOANConstant.LoanType.TX%>";
	}
	else if(lType==5)//查找贷款合同风险状态变更合同
	{
		strSQL += " and a.nStatusID = <%=LOANConstant.ContractStatus.DELAYDEBT%> ";
	}
	//如果有其他的查找类型，可以用lType来区分
	///////////////////////////////////////
	if(lLoanType > 0)//查找该贷款类型下的合同
	{
		strSQL += " and a.nTypeID = " + lLoanType;
	}
	if(lClientID > 0)//查找该客户下的合同
	{
		strSQL += " and a.nBorrowClientID = " + lClientID;
	}
	/*if(lInputUserID > 0)//查出录入人为该用户的合同
	{
		strSQL += " and a.nInputUserID = " + lInputUserID;
	}*/
		strSQL += " order by a.sContractCode ";//按合同编号排序
	//System.out.println("sql==="+strSQL);
	return strSQL;
}

firstFocus(frm.txtContractCode);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script>

<//jsp:include page="../../magnifier/MagnifierSQL.jsp"/>

<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>
