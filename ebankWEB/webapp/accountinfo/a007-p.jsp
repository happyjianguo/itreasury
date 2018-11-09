

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.table2 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-leftright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px}
.td-leftbottomright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 1px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
body {
	font-family: "宋体";
	font-size: 14px;
}
td {
	font-family: "宋体";
	font-size: 12px;
}
.f16 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.f14 {
	font-family: "宋体";
	font-size: 14px;
}
.f10 {
	font-family: "宋体";
	font-size: 10px;
	line-height:10px;
}
.f12 {
	font-family: "宋体";
	font-size: 12px;
}

.f22
{
  font-family:"黑体";
  font-size:22px;
}
.f15 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.tnt {Writing-mode:tb-rl;Text-align:center;font-size:12px}
-->
</style>

<%
    //标题变量
    String strTitle = "［客户对账单］";
    try 
	{
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

        
        //从请求中获取查询结果信息
        Collection resultColl = (Collection)request.getAttribute("searchResults");
		Iterator itResult = null;
		if(resultColl != null)
		{
			itResult = resultColl.iterator();
		}
        // 系统时间
        
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<%
	long lPageLine = 20;
	long lLine = 1;
	boolean bIsFirstPage = false;
		
    String strAccountNo = "";
	String strDepositNo="";
	long lAccountID =-1;	
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
	strAccountNo = DataFormat.formatString(NameRef.getNoLineAccountNoByID(lAccountID));
	
	String strEarlyBanlance = ""; //期初余额
	double mEarlyBanlance = 0.0;
	double mEveryBanlance = 0.0;//每笔以后的余额
	String strExecuteDate = "";    //执行日
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
	mEarlyBanlance = dao.findEarlyBalance(lAccountID,UtilOperation.getNextNDay(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID),-1));
	mEveryBanlance = mEarlyBanlance;
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
   		
    IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
%>
<safety:resources />

<table width="99%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%>客户 对账单</font></b></td>
	</tr>
	<tr>
		<td width="15%">&nbsp;</td>
		<td width="70%" align="center"></td>
		<td width="15%">&nbsp;</td>
	</tr>
</TABLE>
<table width="99%" border="0">
	<tr>
		<td width="20%">开户机构：<%=sessionMng.m_strOfficeName%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=strAccountNo%></td>
	</tr>
	<tr>
		<td width="50%">户名：<%=DataFormat.formatString(NameRef.getAccountNameByID(lAccountID))%></td>
		<td width="20%"></td>
		<td width="30%">币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
	
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
       <tr>
         <td align="center" class="td-rightbottom" nowrap>日期</td>        
         <td align="center"  class="td-rightbottom" nowrap>摘要</td><%--
         <td align="center"  class="td-rightbottom" nowrap>单据号</td>
         <td align="center"  class="td-rightbottom" nowrap>支票号</td>
         --%><td align="center"  class="td-rightbottom" nowrap>借方金额</td>
         <td align="center"  class="td-rightbottom" nowrap>贷方金额</td>        
         <td align="center"  class="td-rightbottom" nowrap>余额</td>
       </tr>  
	   <tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center"><%=strExecuteDate%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">期初余额
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>						
				<td class="td-rightbottom"nowrap height="20">
					<div align="right">
					<%=strEarlyBanlance%>
					</div>
				</td>				
			</tr>     
	<%
	if(itResult != null && itResult.hasNext())
	{		
		double dbtAmountSum=0.0;   //借方合计
		double cdtAmountSum=0.0;   //贷方合计
		double banlanceSum=0.0;    //余额合计
		while(itResult.hasNext())
		{
			OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
			
			String strTransNo = "";   //交易号
			String strTransactionType = "";  //交易类型	
			strDepositNo = "";	       //单据号
			String strAmount = "" ;         //金额
			strExecuteDate = "";    //交易日
			
			String strBillNo = "";    //凭证号/支票号

			String strDebitAmount = "" ;         //借方金额
			String strCreditAmount = "" ;         //贷方金额
			
			String strBanlance = "";  //余额
			
			strBillNo = DataFormat.formatString(info.getBillNo());	
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.DEBIT)
			{
				strDebitAmount=DataFormat.formatListAmount(info.getAmount());
				dbtAmountSum = dbtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance+info.getAmount();
			}
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.CREDIT)
			{
				strCreditAmount=DataFormat.formatListAmount(info.getAmount());
				cdtAmountSum = cdtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance-info.getAmount();
			}
			strBanlance = DataFormat.formatListAmount(mEveryBanlance);			
			banlanceSum=info.getBalance();
			
			strTransNo = DataFormat.formatEmptyString(info.getTransNo());
			strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
			
			strDepositNo = DataFormat.formatString(info.getDepositNo());
			strAmount=DataFormat.formatListAmount(info.getAmount());
			strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));		

			//分页
			lLine ++;		
			if(lLine > lPageLine)
			{
			
	%>
	</table>
	<table width="920" border="0">
		<TR>
			<TD align="left">操作人：<%=sessionMng.m_strUserName%></TD>
			<TD align="right">打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
		</TR>
	</TABLE>
	<br clear=all style='page-break-before:always'>
<table width="920" border="0" cellspacing="0" cellpadding="0">
<tr>
<td colspan="3" align="center"><b><font style="font-size:21px"><%=Env.getClientName()%>客户对账单</font></b></td>
</tr>
<tr>
<td width="15%">&nbsp;</td>
<td width="70%" align="center"></td>
<td width="15%">&nbsp;</td>
</tr>
</table>
<table width="920" border="0">
	<tr>
		<td width="20%">开户机构：<%=sessionMng.m_strOfficeName%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=strAccountNo%></td>
	</tr>
	<tr>
		<td width="50%">户名：<%=DataFormat.formatString(NameRef.getAccountNameByID(lAccountID))%></td>
		<td width="20%"></td>
		<td width="30%">币种：<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
<table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
	<tr>
         <td align="center" class="td-rightbottom" nowrap>日期</td>        
         <td align="center"  class="td-rightbottom" nowrap>摘要</td>
         <%--<td align="center"  class="td-rightbottom" nowrap>单据号</td>
         <td align="center"  class="td-rightbottom" nowrap>支票号</td>
         --%><td align="center"  class="td-rightbottom" nowrap>借方金额</td>
         <td align="center"  class="td-rightbottom" nowrap>贷方金额</td>        
         <td align="center"  class="td-rightbottom" nowrap>余额</td>
       </tr>
<%
	    		lLine = 1;
				}	
%>	   
	   
		<tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">		
					<%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate%>		
					</div>
				</td>
				<td class="td-rightbottom"  nowrap height="20">
					<div align="left">
					<%=strTransactionType.equals("") ? "&nbsp;" : strTransactionType%>
					</div>
				</td>
				<%--<td class="td-rightbottom"  nowrap height="20">
					<div align="center">
					    <%=strDepositNo.equals("") ? "&nbsp;" : strDepositNo%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">
					    <%=strBillNo.equals("") ? "&nbsp;" : strBillNo%>
					</div>
				</td>
				--%><td class="td-rightbottom" nowrap height="20">
					<div align="right">
					    <%=strDebitAmount.equals("") ? "&nbsp;" : strDebitAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"  nowrap height="20">
					<div align="right">
					    <%=strCreditAmount.equals("") ? "&nbsp;" : strCreditAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"  nowrap height="20">
					<div align="right">
					    <%=strBanlance.equals("") ? "&nbsp;" : strBanlance%>
					</div>
				</td>				
			</tr>
		<%
		}
	%>	
		
			<tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">本日合计
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(dbtAmountSum)%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(cdtAmountSum)%>
					</div>
				</td>						
				<td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=DataFormat.formatListAmount(banlanceSum)%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本月合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=DataFormat.formatListAmount(banlanceSum)%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本年合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=DataFormat.formatListAmount(banlanceSum)%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%			
	}
	else
	{		
		%>		
			<tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">本日合计
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>						
				<td class="td-rightbottom"nowrap height="20">
					<div align="right"><%=strEarlyBanlance%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本月合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本年合计--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%
	}
%>		
 </table>		
    <table width="99%" border="0" cellspacing="0" cellpadding="3">
      <tr>
	    <td align="left" >操作人：<%=sessionMng.m_strUserName%></td>
        <td align="right" >打印 时间：<%=strExecuteDate%></td>
      </tr>
    </table>
<script language= "javascript">
	//factory.printing.Print(true);
</script>
<%
    }
	catch (IException ie) 
	{
        Log.print(ie.getMessage());
    }
%>

<%@ include file="/common/SignValidate.inc" %>