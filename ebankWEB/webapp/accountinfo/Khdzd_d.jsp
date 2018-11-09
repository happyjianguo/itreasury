<%--
 程序名称 ：khdzd_d.jsp
 功能说明 : 历史明细查询结果下载页面
 作    者 ：刘琰
 日    期 ：2003年11月26日
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.query.queryobj.*,
				 com.iss.itreasury.settlement.query.paraminfo.*,
				 com.iss.itreasury.settlement.query.resultinfo.*,
				 com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao,
				 java.rmi.RemoteException,
				 java.sql.*,
				 java.util.*"  
%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%response.setContentType("application/msexcel;charset=gbk");
response.setHeader("Content-Disposition",";filename=name.xls");%>
<META http-equiv=Content-Type content="application/msexcel; charset=gbk">


<%!
	/* 标题固定变量 */
	String strTitle = "[下载查找结果]";
%>

<safety:resources />

<%!
	public String getAccountnoByID(long lAccountID) throws RemoteException
	{
		String strAccountNo="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			conn = Database.getConnection();
			strSQL =
				"select distinct sAccountNO from sett_account "
					+ " where id=?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if(rs.next())
			{
				strAccountNo = rs.getString("sAccountNO");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return strAccountNo;			
	}
%>

<%
  	int nPageLine=1000000;//定义每页行数
    try
	{	       
		 // 用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");;
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//定义变量		
		Collection coll = null;
		Iterator it = null;
		String strClientCode = "";//客户号
		String strAccountNo = "";//账号
		String strAccountName = "";//帐户名称
		long lAccountID = -1;//账户ID
		long lAccountTypeID = -1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID
		String strContractCode = "";//合同号
		long lContractID = -1;//合同ID
		String strLoanNoteNo = "";//放款单号
		long lLoanNoteID = -1;//放款单号ID
		String strFixedDepositNo = "";//定期存款单据号
		long lSubAccountID = -1;//定期存款单据对应的子账户ID
		Timestamp tsStartDate = null;//开始日期
		String strStartDate = "";
		String strEndDate = "";
		Timestamp tsEndDate = null;//结束日期
		double dBalance = 0.0;//期初余额
		long lOrder = -1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态
		//读取数据
		if(request.getParameter("lAccountID")!=null)
		{
			lAccountID = Long.parseLong(request.getParameter("lAccountID")); //账户ID		
			strAccountNo = getAccountnoByID(lAccountID); //账号
			strAccountName = NameRef.getAccountNameByID(lAccountID);//帐户名称
		}
		if(request.getParameter("lAccountTypeID")!=null)
		{
			lAccountTypeID = Long.parseLong(request.getParameter("lAccountTypeID")); //账户类型ID
		}
		if(request.getParameter("lAccountGroupID")!=null)
		{
			lAccountGroupID = Long.parseLong(request.getParameter("lAccountGroupID")); //账户组ID
		}
		if(request.getParameter("tsStart")!=null)
		{
			strStartDate = request.getParameter("tsStart");
			tsStartDate = DataFormat.getDateTime(strStartDate);  //起始日期
		}
		if(request.getParameter("tsEnd")!=null)
		{
			strEndDate = request.getParameter("tsEnd");
			tsEndDate = DataFormat.getDateTime(strEndDate); //终止日期
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strContractCodeCtrl")!=null) )
		{
			strContractCode = request.getParameter("strContractCodeCtrl"); //合同号
			Log.print("strContractCode="+strContractCode);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strContractCode")!=null) )
		{
			//lContractID = GetNumParam(request,"strContractCode"); //合同ID
			if(request.getParameter("strContractCode") != null){
				lContractID = Long.parseLong(request.getParameter("strContractCode")); //合同ID
			}
			Log.print("lContractID="+lContractID);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strLoanNoteNoCtrl")!=null) )
		{
			strLoanNoteNo = request.getParameter("strLoanNoteNoCtrl"); //放款通知单号
			Log.print("strLoanNoteNo="+strLoanNoteNo);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.CONSIGN
			||lAccountGroupID==SETTConstant.AccountGroupType.DISCOUNT
			||lAccountGroupID==SETTConstant.AccountGroupType.TRUST
			||lAccountGroupID==SETTConstant.AccountGroupType.OTHERLOAN) && (request.getParameter("strLoanNoteNo")!=null) )
		{
			//lLoanNoteID = GetNumParam(request,"strLoanNoteNo");  //放款通知单ID
			if(request.getParameter("strLoanNoteNo") != null){
				lLoanNoteID = Long.parseLong(request.getParameter("strLoanNoteNo"));  //放款通知单ID
			}
			Log.print("lLoanNoteID="+lLoanNoteID);
		}
		if((lAccountGroupID==SETTConstant.AccountGroupType.FIXED
			||lAccountGroupID==SETTConstant.AccountGroupType.NOTIFY) && (request.getParameter("strFixedDepositNoCtrl")!=null) )
		{
			if(SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
			{
				strFixedDepositNo = request.getParameter("strFixedDepositNoCtrl"); //定期存款单据号
				Log.print("strFixedDepositNo="+strFixedDepositNo);
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				strFixedDepositNo = request.getParameter("strNotifyDepositNoCtrl"); //通知存款单据号
				Log.print("strFixedDepositNo="+strFixedDepositNo);
			}
		}
		//lSubAccountID =  GetNumParam(request,"sFixedDepositNo"); //子账户ID
		if(request.getParameter("sFixedDepositNo") != null){
			lSubAccountID =  Long.parseLong(request.getParameter("sFixedDepositNo")); //子账户ID
		}
		Log.print("子账户=" + lSubAccountID);
		
		//初始化并设置查询条件类
		QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
        QueryTransAccountDetailWhereInfo qtai = new QueryTransAccountDetailWhereInfo();
		QTransAccountDetail qobj = new QTransAccountDetail();
				
        qtai.setOfficeID(sessionMng.m_lOfficeID);
		qtai.setCurrencyID(sessionMng.m_lCurrencyID);
		qtai.setClientCode(sessionMng.m_strClientCode);//客户号
		qtai.setAccountNo(strAccountNo);//账号
		qtai.setAccountID(lAccountID);//账户ID
		qtai.setAccountTypeID(lAccountTypeID);//账户类型ID
		qtai.setContractCode(strContractCode);//合同号
		qtai.setContractID(lContractID);//合同ID
		qtai.setLoanNoteNo(strLoanNoteNo);//放款单号
		qtai.setLoanNoteID(lLoanNoteID);//放款单ID
		qtai.setFixedDepositNo(strFixedDepositNo);//定期存款单据号
		qtai.setSubAccountID(lSubAccountID);//定期存款号对应的子账户ID
		qtai.setStartDate(tsStartDate);
		qtai.setEndDate(tsEndDate);
		//qtai.setDesc(1);
		qtai.setOrderField(1);
		
        //根据请求操作，完成业务处理的调用				
        coll = qobj.queryTransAccountDetail(qtai);
		dBalance = qobj.queryTransAccountBalance(qtai);//账户的期初余额
		OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
		if (lContractID > 0)
		{
			dBalance = bqDao.getLoanHisBalanceByContractID(qtai);//合同的期初余额
		}
		if (lLoanNoteID > 0)
		{
			dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qtai);//放款单的期初余额
		}
%>
<html>
<head>
<title></title>
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
</head>

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>
<script language="javascript">
function window.onload()
{
	factory.printing.header = "<%=IPrint.PRINT_REPORT_HEADER%>"
	factory.printing.footer = "<%=IPrint.PRINT_REPORT_FOOTER%>"
	factory.printing.leftMargin = 0.9
	factory.printing.topMargin = 0.8
	factory.printing.rightMargin = 0.2
	factory.printing.bottomMargin = 0.5
}

function document.onkeydown(DnEvents)
{
	k =  window.event.keyCode;
	if(k==13)
	{
		if (confirm("是否打印？"))
		{
			//factory.printing.printer="";可以写打印机的名称
			factory.printing.Print(true);
		}
	}
	if(k==32)
	{
		if (confirm("是否预览？"))
		{
			//factory.printing.printer="";可以写打印机的名称
			factory.printing.Preview();
		}
	}
}	
</script>
<body>
<table width="630" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="15%" colspan="2">&nbsp;</td>
		<td width="70%" align="center" colspan="3"><b><font style="font-size:22px"><%=Env.getClientName()%>客户对账单</font></b></td>
		<td width="15%">&nbsp;</td>
	</tr>
</table>
<br>
<table width="630" border="0">
    <tr>
    <!-- <td colspan="5" height="19">开户机构：<%=sessionMng.m_strOfficeName%> 　 　 </td> -->
    <td colspan="5" height="19">开户机构：<%=NameRef.getOpenOrganizationNameByAccountID(lAccountID)%> 　 　 </td>
	<td width="25%" colspan="2">账号：  <%=NameRef.getNoLineAccountNo(strAccountNo)%></td>
    </tr>
	<tr>
		<td width="75%" colspan="5">户名：<%=strAccountName%></td>
		<td width="25%" colspan="2">币种：<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
<%
	if(coll !=null && coll.size()<2)
	{
%>	  
	<table width="630" border="0"  cellpadding="3" cellspacing="0" height="60" class="table1">
     <tr align="center">
          <td width="10%" height="20" class="td-rightbottom">日期</td>
          <td width="15%" height="20" class="td-rightbottom">交易编号</td>
          <td width="10%" height="20" class="td-rightbottom">摘要</td>
          <td width="15%" height="20" class="td-rightbottom">对方账号</td>
		  <td width="15%" height="20" class="td-rightbottom">对方账户名称</td>
          <td width="15%" height="20" class="td-rightbottom">借方金额</td>
          <td width="15%" height="20" class="td-rightbottom">贷方金额</td>
          <td width="15%" height="20" class="td-bottom">余额</td>
     </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
		<td width="10%" height="23" class="td-rightbottom"><%=DataFormat.formatDate(tsStartDate)%></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="10%" height="23" class="td-rightbottom">期初余额</td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-bottom" align="right"><b><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></b></td>
	 </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
	 <td width="10%" height="23" class="td-rightbottom"></td>
		<td width="10%" height="23" class="td-rightbottom"></td>
		<td width="10%" height="23" class="td-rightbottom">本月合计</td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom" align="right">0.00</td>
		<td width="15%" height="23" class="td-rightbottom" align="right">0.00</td>
		<td width="15%" height="23" class="td-bottom" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
	 </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
	 <td width="10%" height="23" class="td-rightbottom"></td>
		<td width="10%" height="23" class="td-rightbottom"></td>
		<td width="10%" height="23" class="td-rightbottom">本年合计</td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom"></td>
		<td width="15%" height="23" class="td-rightbottom" align="right"> 0.00</td>
		<td width="15%" height="23" class="td-rightbottom" align="right"> 0.00</td>
		<td width="15%" height="23" class="td-bottom" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
	 </tr>
	</table>
	<br>
<%
	}
	else if(coll!=null && coll.size()>1)
	{
%>	  
<table width="630" border="0"  cellpadding="3" cellspacing="0" height="60" class="table1">
        <tr align="center" >
          <td width="10%" height="20" class="td-rightbottom">日期</td>
          <td width="15%" height="20" class="td-rightbottom">交易编号</td>
          <td width="10%" height="20" class="td-rightbottom">摘要</td>
          <td width="10%" height="20" class="td-rightbottom">对方账号</td>
		  <td width="15%" height="20" class="td-rightbottom">对方账户名称</td>
          <td width="15%" height="20" class="td-rightbottom">借方金额</td>
          <td width="15%" height="20" class="td-rightbottom">贷方金额</td>
          <td width="15%" height="20" class="td-bottom">余额</td>
        </tr>
		<tr bordercolor="#ffffff"  align="center">
			<td width="10%" height="23" class="td-rightbottom"><%=DataFormat.formatDate(tsStartDate)%></td>
			<td width="15%" height="23" class="td-rightbottom"></td>
			<td width="10%" height="23" class="td-rightbottom">期初 余额</td>
			<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
			<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
			<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
			<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
			<td width="15%" height="23" align="right" class="td-bottom" ><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
		</tr>
<%
		Timestamp tsTempDate = null;
		Timestamp tsYearLoopDate = tsStartDate;
		Timestamp tsLastDate = null;//为天合计用的
		Log.print("tsStartDate="+tsStartDate);
		Log.print("tsEndDate="+tsEndDate);
		Log.print("tsEndDate.before(tsStartDate)="+tsEndDate.before(tsStartDate));

	for(tsYearLoopDate = tsStartDate ; (tsYearLoopDate.before(tsEndDate)||tsYearLoopDate.equals(tsEndDate)) ;  )//tsTempDate = DataFormat.getNextMonth(tsTempDate,12)
	{
		Log.print("in year loop");
		Log.print("-----------date="+tsYearLoopDate.toString());
		dYearPayBalance = 0.0;
		dYearReceiveBalance = 0.0;
		int nYears = Integer.valueOf(DataFormat.getYearString(tsYearLoopDate)).intValue();
		Log.print("-----------year="+nYears);
		for(tsTempDate = tsYearLoopDate ;Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == nYears && ((DataFormat.getMonthString(tsTempDate).equals(DataFormat.getMonthString(tsEndDate))||tsTempDate.before(tsEndDate)||DataFormat.formatDate(tsTempDate).equals(DataFormat.formatDate(tsEndDate)))) ; tsTempDate = DataFormat.getNextMonth(tsTempDate,1) )
		{//将月份从开始日期开始循环 以取得没有交易的月的合计
			Log.print("in month loop");
			dMonthPayBalance = 0.0;
			dMonthReceiveBalance = 0.0;
			//Collection coll = (Collection)request.getAttribute("searchResults");
			//Iterator it = null;
				if(coll != null)
				{
					it = coll.iterator();
				}
				if(it != null && it.hasNext())
				{
					while(it.hasNext())
					{
						qtri = (QueryTransAccountDetailResultInfo)it.next();
					
						if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue()+1 != qtri.getExecuteMonth())
						{//如果不是本月的则不显示在本月范围之内
							continue;
						}
						if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != qtri.getExecuteYear())
						{//如果不是本年的则不显示在本年范围之内
							continue;
						}
						Log.print("开始显示当前月的交易");
						if(qtri.getTransTypeID() > -1000)//如果不是日合计的 金额
						{
							dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
							dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
							dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
							dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
						}
				    
%>
		          <tr align="center"  bordercolor="#999999" class="ItemBody">
		          	<td width="10%" height="23" class="td-rightbottom"><%= qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;"%></td>
				  
				<%
				  	if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID()))
				  	{
				%>
			        <td width="15%" height="23" class="td-rightbottom" align="left"><%= qtri.getTransNo() != null ? qtri.getTransNo() + "&nbsp;" : "&nbsp;"%></td>
				<%
					}else
					{
				%>
      				<td width="15%" height="23" class="td-rightbottom"><%=qtri.getTransNo() != null ? qtri.getTransNo() + "&nbsp;" : "&nbsp;"%></td>
				<%
					}
				%>
				  	<td width="10%" height="23" class="td-rightbottom" align="<%=(qtri.getAbstract() != null&&qtri.getAbstract().equals("<b>本日合计</b>"))?"center":"left" %>"><%=qtri.getAbstract() != null? (qtri.getAbstract().equals("<b>本日合计</b>")?"本日合计":qtri.getAbstract()+ "&nbsp;"): "&nbsp;"%></td>   
		        	<td width="15%"  height="23" class="td-rightbottom"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNoByID(qtri.getOppAccountID()): (qtri.getOppAccountNo()!=null? qtri.getOppAccountNo() + "&nbsp;" :"&nbsp;")%></td> 
<%
				if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
				{
%>      		    
				  <td width="15%"  height="23" class="td-rightbottom"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNameByID(qtri.getOppAccountID()) : (qtri.getOppAccountName()!=null?qtri.getOppAccountName():"&nbsp;")%></td> 
<%
				}
				else{
%>
				 <td width="15%"  height="23" class="td-rightbottom">&nbsp;</td>
<%
				}
				if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
				    {
				   %>
			          <td width="15%" height="23" align="right" class="td-rightbottom"><%= qtri.getPayAmount() !=0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00"%></td>
			          <td width="15%" height="23" align="right" class="td-rightbottom"><%= qtri.getReceiveAmount() !=0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00"%></td>
				  	  <td width="15%" height="23" align="right" class="td-bottom"><%= qtri.getBalance() != 0 ?  DataFormat.formatDisabledAmount(qtri.getBalance()) : "0.00"%></td>
				  <%
				  	}
				  	else
				  	{
				  %>
			          <td width="15%" height="23" align="right" class="td-rightbottom"><%= qtri.getPayAmount() !=0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00"%></td>
			          <td width="15%" height="23" align="right" class="td-rightbottom"><%= qtri.getReceiveAmount() !=0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00"%></td>
				  	  <td width="15%" height="23" align="right" class="td-bottom"><%= qtri.getBalance() != 0 ?  DataFormat.formatDisabledAmount(qtri.getBalance()) : "0.00"%></td></td>
				  <%
				  		 
					}
						dBalance = qtri.getBalance();
					}
				}
%>
				</tr>
				<tr bordercolor="#999999" align="center" class="ItemBody">
					<td width="10%" height="23" class="td-rightbottom"><%=DataFormat.getLastDateString(tsTempDate)%></td>
					<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
					<td width="10%" height="23" class="td-rightbottom">本月合计</td>
					<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
					<td width="15%" height="23" class="td-rightbottom">&nbsp;</td>
					<td width="15%" height="23" class="td-rightbottom" align="right"><%= dMonthPayBalance >0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00"%></td>
					<td width="15%" height="23" class="td-rightbottom" align="right"><%= dMonthReceiveBalance >0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00"%></td>
					<td width="15%" height="23" align="right" class="td-bottom"><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>
				</tr>
<%
			}//end month
%>
				<tr bordercolor="#999999" align="center" class="ItemBody">
					<td width="10%" height="23" class="td-rightbottom"><%=DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1))%></td>
					<td width="15%" height="23" class="td-rightbottom"></td>
					<td width="10%" height="23" class="td-rightbottom">本年合计</td>
					<td width="15%" height="23" class="td-rightbottom"></td>
					<td width="15%" height="23" class="td-rightbottom"></td>
					<td width="15%" height="23" class="td-rightbottom" align="right"><%= dYearPayBalance >0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00"%></td>
					<td width="15%" height="23" class="td-rightbottom" align="right"><%= dYearReceiveBalance >0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00"%></td>
					<td width="15%" height="23" align="right" class="td-bottom"><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>
				</TR>
<%
			tsYearLoopDate = DataFormat.getDateTime(nYears+1,1,1,0,0,0);
		}//end year
%>
      </table>    
      <table width="630" border="0" cellspacing="4" cellpadding="0">
				<tr>
					
    <td width="20%">&nbsp;</td>
					
    <td width="30%">操作人：<%=sessionMng.m_strUserName%></td>
					
    <td width="50%" align="right">打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></td>
				</tr>
	 </table>
<%
	}
%>
</body>
</html>


<%
	}
	catch (Exception e)
	{
		//out.println(e.toString());
		//OBHtml.dealException(out,response,e.getMessage(),sessionMng,"打印报表",1);
		e.printStackTrace();
	}
%>		
