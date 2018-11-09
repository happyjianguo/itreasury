<%--
 程序名称 ：khdzd.jsp
 功能说明 : 历史明细查询结果打印页面
 作    者 ：刘琰
 日    期 ：2003年11月26日
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.query.queryobj.*,
				 com.iss.itreasury.settlement.query.paraminfo.*,
				 com.iss.itreasury.settlement.query.resultinfo.*,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.settlement.print.IPrintTemplate,
				 com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao,
				 java.rmi.RemoteException,
				 java.sql.*,
				 java.util.*"  
%>
<%@ page import="com.iss.itreasury.ebank.print.bizlogic.EbankPrintApplyBiz" %>
<%@ page import="java.lang.Math" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<safety:resources />
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
		return NameRef.getNoLineAccountNo(strAccountNo);			
	}
%>

<%!
	/* 标题固定变量 */
	String strTitle = "[打印]";
	
	
%>

<%
		//签章相关设置//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//服务器端请求路径
		String nowDate = Env.getSystemDateTimeString();//当前日期
		String officeName = Env.getClientName();//印章名称
		//查看是否有签章权限--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		int pageCount=1;
		//String nbilltypeId = EBankDocRiht.ebankDocType[2][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.ZHLSMX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//查看是否有签章权限--end----
		double px=300;//横坐标
		double py = 200;//纵坐标
		//////////////////////////////////////////////////////////////

  
  	long signatureTimes = 0;	//add by xiangzhou 20110603 当打印多页时，保证每页都有签章
    try
	{	       
		 // 用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
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
		long lPageLine = 34;	//每页打印多少行
		double lLine = 1.00;
		boolean bIsFirstPage = false;
				
		Collection coll = null;
		Iterator it = null;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lClientID = -1;//客户ID
		String strClientCode = "";//客户号
		String strAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		String strAccountName = "";//账户名称
		long lAccountTypeID = -1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID
		String strContractCode = "";//合同号
		long lContractID = -1;//合同ID
		String strLoanNoteNo = "";//放款单号
		long lLoanNoteID = -1;//放款单号ID
		String strFixedDepositNo = "";//定期存款单据号
		long lFixedDepositID = -1;//定期存款单据号ID
		long lSubAccountID = -1;//定期存款单据对应的子账户ID
		Timestamp tsStartDate = null;//开始日期
		String strStartDate = "";
		String strEndDate = "";
		Timestamp tsEndDate = null;//结束日期
		double dBalance = 0.0;//期初余额
		long abstractRowNumber = 1; //计算摘要折行数
		long payAccountNameNumber = 1;//计算收款方名称折行数
		String strAbstract = "";
		String payAccountName = "";
		long lOrder = -1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		long height = 26;  //单行表格高度
		long tempheight = height;
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态
		EbankPrintApplyBiz ebankPrintApplyBiz = new EbankPrintApplyBiz();
		
		//读取数据
		if(request.getParameter("lAccountID")!=null)
		{
			lAccountID = Long.parseLong(request.getParameter("lAccountID")); //账户ID
			strAccountNo = getAccountnoByID(lAccountID);//账号
			strAccountName = NameRef.getAccountNameByID(lAccountID);
			lClientID = NameRef.getClientIDByAccountID(lAccountID);
			strClientCode = NameRef.getClientCodeByID(lClientID);
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
			lContractID = GetNumParam(request,"strContractCode"); //合同ID
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
			lLoanNoteID = GetNumParam(request,"strLoanNoteNo");  //放款通知单ID
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
		lSubAccountID =  GetNumParam(request,"sFixedDepositNo"); //子账户ID
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
<%
		//OBHtml.showCPFPrintHead(out,true,"A4","",-1);
		//IPrintTemplate.showPrintHead(out,false,"A4","",1,lOfficeID);
%>
<%
	eBankPrint.showPrintReport(out,sessionMng,"A4",1,true);
%>


<div align="center">
  <table width="700" border="0" cellspacing="0" cellpadding="0" name="table1">
    <tr> 
      <td width="25%">&nbsp;</td>
      <td align="center" width="46%"><b><font style="font-size:21px"><%=Env.getClientName()%>客户对账单</font></b></td>
      <td rowspan="2" width="25%" align="center"><!--<img src="\webob\graphics\dayin_logo.gif"  height="46">--></td>
    </tr>
    <tr> 
      <td width="25%">&nbsp;</td>
      <td width="50%" align="center"></td>
    </tr>
  </TABLE>
<table width="700" border="0">
	<tr>
		<!-- <td width="20%">开户机构:<%=sessionMng.m_strOfficeName%></td> -->
		<td width="20%">开户机构:<%=NameRef.getOpenOrganizationNameByAccountID(lAccountID)%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=strAccountNo%></td>
	</tr>
	<tr>
		<td width="50%">户名：<%=strAccountName%></td>
		<td width="20%"></td>
		<td width="30%">币种：<%=Constant.CurrencyType.getName(lCurrencyID)%></td>

</table>
<table width="700" border="0" class="table1" cellspacing="0" cellpadding="0" style="word-break:break-all">
        <tr align="center">
          <td width="9%" height="15" class="td-right">日期</td>
          <td width="15%" height="15" class="td-right">交易编号</td>
          <td width="8%" height="15" class="td-right">摘要</td>
          <td width="15%" height="15" class="td-right">对方账号</td>
		  <td width="12%" height="15" class="td-right">对方账户名称</td>
          <td width="13%" height="15" class="td-right">借方金额</td>
          <td width="13%" height="15" class="td-right">贷方金额</td>
          <td width="15%" height="15" class="td-right">余额</td>
        </tr>
		<tr align="center">
			<td width="9%" height=<%=tempheight%> class="td-topright"><%=DataFormat.formatDate(tsStartDate)%></td>
			<td width="15%" height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td width="8%" height=<%=tempheight%> class="td-topright">期初余额</td>
			<td width="15%" height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td width="12%" height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td width="13%" height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td width="13%" height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td width="15%" height=<%=tempheight%> align="right" class="td-top"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
		</tr>
<%
Timestamp tsTempDate = null;
Timestamp tsYearLoopDate = tsStartDate;
Log.print("tsStartDate="+tsStartDate);
Log.print("tsEndDate="+tsEndDate);
Log.print("tsEndDate.before(tsStartDate)="+tsEndDate.before(tsStartDate));

	for(tsYearLoopDate = tsStartDate ; (tsYearLoopDate.before(tsEndDate)||tsYearLoopDate.equals(tsEndDate)) ;  )//tsTempDate = DataFormat.getNextMonth(tsTempDate,12)
	{
		Log.print("in year loop");
		dYearPayBalance = 0.0;
		dYearReceiveBalance = 0.0;
		int nYears = Integer.valueOf(DataFormat.getYearString(tsYearLoopDate)).intValue();
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
					if(qtri.getExecuteDate()==null&&tsTempDate!=null){
					   continue;
					}
					if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue() != Long.valueOf(DataFormat.getMonthString(qtri.getExecuteDate())).longValue())
					{//如果不是本月的则不显示在本月范围之内
						continue;
					}
					if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != qtri.getExecuteYear())
					{//如果不是本年的则不显示在本年范围之内
						continue;
					}
					
					//计算摘要和帐户名称折行数
					if(qtri.getAbstract()!=null)
					{
						strAbstract = qtri.getAbstract().trim();
					}
					else
					{
						strAbstract = "";
					}

					if(qtri.getOppAccountName()!=null)
					{
						payAccountName =qtri.getOppAccountName().trim();
					}
					else
					{
						payAccountName = "";
					}
					abstractRowNumber = ebankPrintApplyBiz.countRowNumber(strAbstract,8);
					payAccountNameNumber = ebankPrintApplyBiz.countRowNumber(payAccountName,12);
					//分页
					if(Math.max(abstractRowNumber,payAccountNameNumber)<=1)
					{
						++lLine;
						tempheight = height;
					}
					else
					{
						lLine = lLine + 1 +(Math.max(abstractRowNumber,payAccountNameNumber)-1)*0.5;
						tempheight = new Double(height * (1 +(Math.max(abstractRowNumber,payAccountNameNumber)-1)*0.5)).longValue();
					}
					if(lLine > lPageLine)
					{
						pageCount++;
						signatureTimes++;
					%>
</table>
  	<table width="700" border="0">
		<TR>
			<TD align="left" >操作人：<%=sessionMng.m_strUserName%></TD>
			<TD align="right" id="printDate<%=signatureTimes %>" ><div id="Signature<%=signatureTimes %>"></div>打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
		</TR>
	</TABLE>
<br clear=all style='page-break-before:always'>
  <table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td width="25%">&nbsp;</td>
      <td align="center" width="46%"><b><font style="font-size:21px"><%=Env.getClientName()%>客户对账单</font></b></td>
      <td rowspan="2" width="25%" align="center"><!--<img src="\webob\graphics\dayin_logo.gif"  height="46">--></td>
    </tr>
    <tr> 
      <td width="25%">&nbsp;</td>
      <td width="50%" align="center"><b><font style="font-size:22px"></font></b></td>
    </tr>
  </TABLE>
<table width="700" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20%" >开户机构:<%=sessionMng.m_strOfficeName%></td>
		<td width="25%"></td>
		<td width="25%">账号：<%=strAccountNo%></td>
	</tr>
	<tr>
		<td width="50%" >户名：<%=strAccountName%></td>
		<td width="20%"></td>
		<td width="30%">币种：<%=Constant.CurrencyType.getName(lCurrencyID)%></td>
	</tr>
</table>
<table width="700" border="0" height="60" class="table1" cellspacing="0" cellpadding="0" style="word-break:break-all">
        <tr align="center">
          <td width="9%" height="15" class="td-right">日期</td>
          <td width="15%" height="15" class="td-right">交易编号</td>
          <td width="8%" height="15" class="td-right">摘要</td>
          <td width="15%" height="15" class="td-right">对方账号</td>
		  <td width="12%" height="15" class="td-right">对方账户名称</td>
          <td width="13%" height="15" class="td-right">借方金额</td>
          <td width="13%" height="15" class="td-right">贷方金额</td>
          <td width="15%" height="15" class="td-right">余额</td>
        </tr>
					<%
						lLine = 0.00;
					}
					//分页End
					
					if(qtri.getTransTypeID() != -1000)//如果不是日合计的 金额
					{
						dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
						dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
						dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
						dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
					}
%>

				  <tr bordercolor="#999999" align="center">
			          <td  height=<%=tempheight%> class="td-topright"><%= qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;"%></td>
			      <%
			      	if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID())){
			      %>
					  <td  height=<%=tempheight%> class="td-topright" align="left"><%= qtri.getTransNo() != null ? qtri.getTransNo() + "&nbsp;" : "&nbsp;"%></td>
				  <%
				  	}else {
				  %>
					  <td  height=<%=tempheight%> class="td-topright"><%= qtri.getTransNo() != null ? qtri.getTransNo() + "&nbsp;" : "&nbsp;"%></td>
				  <%
				  	}
				  %>
			          <td  height=<%=tempheight%> class="td-topright" align="<%=(qtri.getAbstract() != null&&qtri.getAbstract().equals("<b>本日合计</b>"))?"center":"left" %>"><%=qtri.getAbstract() != null? (qtri.getAbstract().equals("<b>本日合计</b>")?"本日合计":qtri.getAbstract()): "&nbsp;"%></td>   
			          <td  height=<%=tempheight%> class="td-topright"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNoByID(qtri.getOppAccountID()): (qtri.getOppAccountNo()!=null? qtri.getOppAccountNo() + "&nbsp;":"&nbsp;")%></td>  
				<%
					if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
					{
				%>      		    
				  		<td height=<%=tempheight%> class="td-topright"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNameByID(qtri.getOppAccountID()) : (qtri.getOppAccountName()!=null?qtri.getOppAccountName() + "&nbsp;":"&nbsp;")%></td> 
				<%
					}
					else{
				%>
				 		<td height=<%=tempheight%> class="td-topright">&nbsp;</td>
				<%
					}
					if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
				    {
				%>
			          <td  height=<%=tempheight%> align="right" class="td-topright"><%= qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00"%></td>
			          <td  height=<%=tempheight%> align="right" class="td-topright"><%= qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00"%></td>
			          <td  height=<%=tempheight%> align="right" class="td-top"><%= DataFormat.formatDisabledAmount(qtri.getBalance())%></td>
			    <%
				    }else{
			    %>
			          <td  height=<%=tempheight%> align="right" class="td-topright"><%= qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "&nbsp;"%></td>
			          <td  height=<%=tempheight%> align="right" class="td-topright"><%= qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "&nbsp;"%></td>
			          <td  height=<%=tempheight%> align="right" class="td-top"><%= DataFormat.formatDisabledAmount(qtri.getBalance())%></td>
			    <%
				    }
			    %>
			        </tr>
<%
					dBalance = qtri.getBalance();
				}
			}
%>
			<tr bordercolor="#999999" align="center">
				<%
					++lLine;
					tempheight = height;
				 %>
				<td  height=<%=tempheight%> class="td-topright"><%=DataFormat.getLastDateString(tsTempDate)%></td>
				<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
				<td  height=<%=tempheight%> class="td-topright">本月合计</td>
				<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
				<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
				<td  height=<%=tempheight%> class="td-topright" align="right"><%= dMonthPayBalance != 0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00"%></td>
				<td  height=<%=tempheight%> class="td-topright" align="right"><%= dMonthReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00"%></td>
				<td  height=<%=tempheight%> align="right" class="td-top"><%= dBalance >0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>
			</tr>
<%
		}//end month
%>
	</tr>
			<tr bordercolor="#999999" align="center">
			<%
				++lLine;
			 %>
			<td  height=<%=tempheight%> class="td-topright"><%=DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1))%></td>
			<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td  height=<%=tempheight%> class="td-topright">本年合计</td>
			<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td  height=<%=tempheight%> class="td-topright">&nbsp;</td>
			<td  height=<%=tempheight%> class="td-topright" align="right"><%= dYearPayBalance != 0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00"%></td>
			<td  height=<%=tempheight%> class="td-topright" align="right"><%= dYearReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00"%></td>
			<td  height=<%=tempheight%> align="right" class="td-top"><%= dBalance >0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>
		  </tr>
<%
	tsYearLoopDate = DataFormat.getDateTime(nYears+1,1,1,0,0,0);
	}//end year
%>

<%
	signatureTimes++;
	}
	catch (Exception e)
	{
		//out.println(e.toString());
		//OBHtml.dealException(out,response,e.getMessage(),sessionMng,"打印报表",1);
		e.printStackTrace();
	}
%>		
   </table>
   <table width="700" border="0">
   <TR>
			<TD align="left" >操作人：<%=sessionMng.m_strUserName%></TD>
			<TD align="right" id="printDate<%=signatureTimes %>" ><div id="Signature<%=signatureTimes %>"></div>打印时间：<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></TD>
	</TR>
	</TABLE>	
</div>


<%@ include file="/common/SignValidate.inc" %>

<%

	if(hasRight){

 %>
<BODY language="javascript" onResize="setAfterPrintPosition();" style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl"  codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
<param name="ServiceUrl" value="<%=basePath%>/NASApp/SignatureServlet">                       <!--读去数据库相关信息-->
<param name="WebAutoSign" value="0">                     <!--是否自动数字签名(0:不启用，1:启用)-->
<param name="PrintControlType" value=2>                  <!--打印控制方式（0:不控制  1：签章服务器控制  2：开发商控制）-->
<param name="MenuDocVerify" value=false>                 <!--菜单文档验证文档-->
<param name="MenuServerVerify" value=false>              <!--菜单在线验证,网络版本专用-->
<param name="MenuDigitalCert" value=false>               <!--菜单数字证书-->
<param name="MenuDocLocked" value=false>                 <!--菜单文档锁定-->
<param name="MenuDeleteSign" value=false>                <!--菜单撤消签章-->
<param name="MenuMoveSetting" value=true>                <!--菜单禁止移动-->
<param name="PrintWater" value=false>                    <!--是否打印水印-->
</OBJECT>
</BODY>
<script language="javascript">
	var oldScrollTop=0;
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			var pagecount="<%=pageCount%>";
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80+(parseInt(pagecount)-1)*200;
			sx = 901;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
	function setAfterPrintPosition(){
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
			document.body.scrollTop=oldScrollTop;
	}
	window.onafterprint=setAfterPrintPosition;
	window.onbeforeprint=setPirntPosition;
	
	var j = <%=signatureTimes%>;
	for(var i=1; i<=j; i++){	//add by xiangzhou 20110603 当打印多页时，保证每页都有签章
		try{
			var   x,y;   
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate"+i).getBoundingClientRect();   
			var width = document.getElementById("printDate"+i).clientWidth;
			x=oRect.left
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
		    document.all.SignatureControl.EnableMove = "false";          //设置签章是否可以移动
		    document.all.SignatureControl.PassWord = "123456";           //签章密码,系统默认为"",当设置改参数后点签章后弹出的选择签章窗体中的密码将默认为该密码      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //读取、设置选择签章的窗口是否可见
		    document.all.SignatureControl.FieldsList = "strTransNos=组合业务编号";          //读取、设置签章保护信息列表
		    document.all.SignatureControl.SaveHistory = "false";         //读取、设置是否保存历史记录true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //读取、设置签章的用户名称
		    //document.all.SignatureControl.DivId = oPageSet.showSignatureName;          //读取、设置签章所在的层
		 	document.all.SignatureControl.PositionByTagType = 0;
		    document.all.SignatureControl.Position(sx-300,sy);
		    document.all.SignatureControl.ValidateCertTime = false;      //设置是否验证证书过期或未到期
		    document.all.SignatureControl.ExtParam = "11111111|11";//transNo
		    document.all.SignatureControl.ExtParam1 = "<%=nowDate%>";          //设置签章附加信息
		    //document.all.SignatureControl.SetWaterInfo("网络专用","隶书",0X0000FF,0);//设置签章数字水印信息
		    document.all.SignatureControl.WebSetFontOther(true,"","0","宋体",7,"$0000FF",false);//设置签章图案附属信息(日期时间、签章人员、意见等)显示模式
		    document.all.SignatureControl.DefaultSignTimeFormat = 8;    //设置签章附加时间默认格式
		    document.all.SignatureControl.SetSignTextOffset(0,30);      //设置盖章是的附加信息(包括时间)的偏移量
			document.all.SignatureControl.DivId="Signature"+i;
		  }catch(e){
		    alert(e);
		  }
	    try{
	    	document.all.SignatureControl.RunSignature();               //执行签章  
	    }catch(e){
	    	alert("添加签章错误，请联系开发人员");
	    }
	}
</script>
<%
	}
%>
</html>