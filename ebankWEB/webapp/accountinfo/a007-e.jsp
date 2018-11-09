<%--
/*
 * 程序名称：客户对账单.jsp
 * 功能说明：账户余额查询页面下载
 * 作　　者：xiren li
 * 完成日期：2004-03-02
 */
 modify by wjliu --2007/3/19  修改下载出来的数据:期初余额重复,没有输出本日,本月,本年的合计值
--%>

<%@ page contentType="application/msexcel;charset=gbk"%>

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
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    response.setHeader("Content-Disposition",";filename=\treport.xls");
%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

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
<HTML>
<HEAD><TITLE></TITLE>
<style type="text/css">
<!--
.table1 {  border: 1px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
-->
</style>
</HEAD>
<BODY text="#000000">
<%
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
	strAccountNo = NameRef.getNoLineAccountNoByID(lAccountID);
	
	String strEarlyBanlance = ""; //期初余额
	double mEarlyBanlance = 0.0;
	double mEveryBanlance = 0.0;//每笔以后的余额
	String strExecuteDate = "";    //执行日
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
	mEarlyBanlance = dao.findEarlyBalance(lAccountID,UtilOperation.getNextNDay(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID),-1));
	mEveryBanlance = mEarlyBanlance;
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
   		
      
%>
<table width="630" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="15%" colspan="1">&nbsp;</td>
		<td width="70%" align="center" colspan="3"><b><font style="font-size:22px"><%=Env.getClientName()%>客户对账单</font></b></td>
		<td width="15%">&nbsp;</td>
	</tr>
</table>

	<table width="630" border="0">
    <tr>
	<br>	
    <td colspan="5" height="19">户名：　<%=sessionMng.m_strClientName%> 　 　 </td>
    </tr>
	<tr>
		<td width="75%" colspan="3">账号：<%=strAccountNo%></td>
		<td width="25%" colspan="2">币种：<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
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
<%--	   		<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center"><%=strExecuteDate%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">期初余额--%>
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
<%--					<div align="right">--%>
<%--					<%=strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>      --%>
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
			
			
	%>
		<tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">		
					<%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate%>		
					</div>
				</td>
				<td class="td-rightbottom" class="td-rightbottom"  nowrap height="20">
					<div align="center">
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
					<div align="right"><%=strEarlyBanlance%>
					</div>
				</td>				
			</tr>
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
					<div align="center">
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
<%--					<div align="center">&nbsp;--%>
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
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr-->--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">本日合计--%>
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
    <table width="99%" border="0" cellspacing="0" cellpadding="8">
      <tr>
	    <td align="left" >操作人：<%=sessionMng.m_strUserName%></td>
	       <td>&nbsp
	    </td><%--
	     <td>&nbsp
	    </td>
	      <td>&nbsp
	    </td>
	      --%><td>&nbsp
	    </td>
	   
	   
	    
        <td align="right" colspan="2">打印时间：<%=dtSysDate.toString().substring(0,10)%></td>
      </tr>
    </table>
</BODY>
</HTML>
<%
    }
	catch (IException ie) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>