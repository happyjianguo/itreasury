<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.system.approval.bizlogic.*,
				com.iss.itreasury.system.approval.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>	
<%!
	public static String getAmountByOrder(String strAmount, int iOrderID) throws Exception
	{
		String strReturn = "&nbsp;";
		int nAmountLength = 11;
		try
		{
			if (iOrderID < strAmount.length())
			{
				if (iOrderID <= 2)
				{
					return (strAmount.substring(strAmount.length() - iOrderID, strAmount.length() - iOrderID + 1));
				}
				else
				{
					return (strAmount.substring(strAmount.length() - iOrderID - 1, strAmount.length() - iOrderID));
				}
			}
			if((iOrderID == strAmount.length()) && (iOrderID <= nAmountLength))
			{
					return "￥";//sessionMng.m_strCurrencySymbol;
			}
		}
		catch (Exception e)
		{
		}
		return strReturn;
	}
%>
<%
/////////////////////////////////////////////////////////////////////////////////
	System.out.println("hrere!");
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
		
		String strTmp = "";
		String strControl = "";
		String strBackURL = "S120-1";
		String strDisabled = " disabled";
		String strAction = "";
			
		long txtContract = -1;			//贴现标示
		String txtContractCode = "";	//贴现申请编号
		long txtClient = -1;			//单位标示
		String txtClientCtrl = "";		//单位名称

		long lContractID = -1;			//贴现标示
		long lCredenceID = -1;			//贴现凭证标示
		Timestamp tsFillDate = null;
		long lDraftTypeID = -1;
		String strDraftTypeName = "";
		String strDraftCode = "";
		Timestamp tsPublicDate = null;
		Timestamp tsAtTerm = null;
		String strApplyClient = "";
		String strApplyAccount = "";
		String strApplyBank = "";
		String strAcceptClient = "";
		String strAcceptAccount = "";
		String strAcceptBank = "";
		double dAmount = 0;
		double dRate = 0;
		double dInterest = 0;
		double dCheckAmount = 0;
		double dBillAmount = 0;
		double dBillInterest = 0;
		double dBillCheckAmount = 0;
		double[] dResult = new double[3];
		
		//////////////////////////////
		String strContractCode = "";
		String strCredenceCode = "";
		long lInputUserID = -1;
		String strInputUserName = "";
		Timestamp tsInputDate = null;
		long lStatusID = -1;
		long lCount = 0;
		//////////////////////////////
		int nTmp = 0;
		int TRACINGNUM = 4;
		String[] strApprovalTracing = new String[TRACINGNUM];
		String[] strApprovalDate = new String[TRACINGNUM];
		String[] strApprovalUserName = new String[TRACINGNUM];
		for (int i=0; i<TRACINGNUM; i++)
		{
			strApprovalUserName[i] = "";
			strApprovalTracing[i] = "";
			strApprovalDate[i] = DataFormat.getDateString(Env.getSystemDate());
		}
		//模块类型
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = Constant.ApprovalAction.DISCOUNT_CREDENCE;
		
		Collection temp = null;
		//////////////////////////////
		
		//贴现EJB
        
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();

///////control///////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
	
		strTmp = (String)request.getAttribute("backurl");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strBackURL = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("txtContract");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContract = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("txtClient");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClient = Long.parseLong(strTmp.trim());
		}	 

		strTmp = (String)request.getAttribute("txtClientCtrl");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClientCtrl = DataFormat.toChinese(strTmp.trim());
		}
		
//adding
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}

////////view/////////////////////////////////////////////////////////////////////
			if (lCredenceID > 0)
			{
				//dci = Discount.findDiscountCredenceByID (lCredenceID);

				if (info.getCode()!=null && info.getCode().length()>0)
				{
					strCredenceCode = info.getCode();
				}
				else
				{
					strCredenceCode = "&nbsp;";
				}
				lContractID = info.getDiscountContractID();
				if (info.getDiscountContractCode()!=null && info.getDiscountContractCode().length()>0)
				{
					strContractCode = info.getDiscountContractCode();
				}
				else
				{
					strContractCode = "&nbsp;";
				}
				lStatusID = info.getStatusID();
				tsFillDate = info.getFillDate();
				tsInputDate = info.getInputDate();
				lInputUserID = info.getInputUserID();
				if (info.getInputUserName()!=null && info.getInputUserName().length()>0)
				{
					strInputUserName = info.getInputUserName();
				}
				else
				{
					strInputUserName = "&nbsp;";
				}
				lDraftTypeID = info.getDraftTypeID();
				if (info.getDraftCode()!=null && info.getDraftCode().length()>0)
				{
					strDraftCode = info.getDraftCode();
				}
				else
				{
					strDraftCode = "&nbsp;";
				}
				tsPublicDate = info.getPublicDate();
				tsAtTerm = info.getAtTerm();

				if (info.getApplyClientName()!=null && info.getApplyClientName().length()>0)
				{
					strApplyClient = info.getApplyClientName();
				}
				else
				{
					strApplyClient = "&nbsp;";
				}
				if (info.getApplyAccount()!=null && info.getApplyAccount().length()>0)
				{
					strApplyAccount = info.getApplyAccount();
				}
				else
				{
					strApplyAccount = "&nbsp;";
				}
				if (info.getApplyBank()!=null && info.getApplyBank().length()>0)
				{
					strApplyBank = info.getApplyBank();
				}
				else
				{
					strApplyBank = "&nbsp;";
				}

				strAcceptClient = info.getAcceptClientName();
				strAcceptAccount = info.getAcceptAccount();
				strAcceptBank = info.getAcceptBank();
				if (info.getAcceptClientName()!=null && info.getAcceptClientName().length()>0)
				{
					strAcceptClient = info.getAcceptClientName();
				}
				else
				{
					strAcceptClient = "&nbsp;";
				}
				if (info.getAcceptAccount()!=null && info.getAcceptAccount().length()>0)
				{
					strAcceptAccount = info.getAcceptAccount();
				}
				else
				{
					strAcceptAccount = "&nbsp;";
				}
				if (info.getAcceptBank()!=null && info.getAcceptBank().length()>0)
				{
					strAcceptBank = info.getAcceptBank();
				}
				else
				{
					strAcceptBank = "&nbsp;";
				}

				dAmount = info.getExamineAmount();
				dRate = info.getDiscountRate();
				dCheckAmount = info.getCheckAmount();

				dBillAmount = info.getBillAmount();
				dBillInterest = info.getBillInterest();
				dBillCheckAmount = info.getBillCheckAmount();

				temp = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lCredenceID,Constant.PageControl.CODE_ASCORDESC_DESC);

				if( (temp != null) && (temp.size() > 0) )
				{
					nTmp = TRACINGNUM-1;
					Iterator it = temp.iterator();
					while (it.hasNext() )
					{
						tracinginfo = (ApprovalTracingInfo) it.next();
						strApprovalUserName[nTmp] = DataFormat.formatString(tracinginfo.getUserName());
						strApprovalTracing[nTmp] = DataFormat.formatString(tracinginfo.getOpinion());
						if (tracinginfo.getApprovalDate() != null)
						{
							strApprovalDate[nTmp] = DataFormat.getDateString(tracinginfo.getApprovalDate());
						} 
						else 
						{
							strApprovalDate[nTmp] = DataFormat.getDateString(Env.getSystemDate());
						}
						Log.print(nTmp);
						Log.print(strApprovalTracing[nTmp]);
						Log.print(strApprovalDate[nTmp]);
						nTmp--;
						if (nTmp < 0)
						{
							break;
						}
					}
				}
			}

/////////////////////////////////////////////////////////////////////////////////

%>

<html>
<head>
<title>贴现凭证</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
body {
	font-family: "宋体";
	font-size: 14px;
}
td {
	font-family: "楷体_GB2312";
	font-size: 12px;
}
.f16 {
	font-family: "宋体";
	font-size: 16px;
}
.f12 {
	font-family: "楷体_GB2312";
	font-size: 12px;
}
-->
</style>
</head>

<body text="#000000" bgcolor="#FFFFFF">

<% 
	for (int i = 0; i < 5; i++) 
	{
%>

<table width="650" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="100">&nbsp;</td>
    <td width="450" height="50" align="center"><b><span class="f16"><%=Env.getClientName()%><br><u>贴现凭证（一式五联）</u></span></b></td>
    <td width="100">&nbsp;</td>
  </tr>
</table>
<table width="650" border="0" align="center">
<tr> 
    <td width="100">&nbsp;</td>
    <td width="450" height="30" align="center"><% if (tsFillDate != null) { %><%=DataFormat.getDateString(tsFillDate).substring(0,4)%>年&nbsp;<%=DataFormat.getDateString(tsFillDate).substring(5,7)%>月&nbsp;<%=DataFormat.getDateString(tsFillDate).substring(8,10)%>日<% } else { out.print("年&nbsp;&nbsp;月&nbsp;&nbsp;日");}%></td>
    <td width="100">&nbsp;</td>
</tr>
<tr>
	<td height="30" align="left" colspan=3>贴现合同号：&nbsp;<%=strContractCode%></td>
</tr>
</table>
<table width="650" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td width="630">
	  <table width="630" border="0" cellpadding="0" cellspacing="0" class="table1">
		<tr> 
          <td rowspan="3" align="center" class="td-rightbottom">贴<br>
         	现<br>
         	汇<br>
         	票</td>
          <td align="center" class="td-rightbottom" height="25">种　类</td>
          <td align="center" colspan="2" class="td-rightbottom"><%if(lDraftTypeID>0) out.print(OBConstant.DraftType.getName(lDraftTypeID)); else out.print("详见明细表");%></td>
          <td align="center" class="td-rightbottom">号码</td>
          <td align="center" colspan="4" class="td-rightbottom"><%=DataFormat.formatString(strDraftCode)%></td>
          <td rowspan="3" align="center" class="td-rightbottom">持<br>
         	票<br>
         	人</td>
          <td align="center" class="td-rightbottom">名　称</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyClient)%></td>
		</tr>
        <tr> 
          <td align="center" class="td-rightbottom" height="25">出票日</td>
          <td align="center" colspan="7" class="td-rightbottom"><% if (tsPublicDate != null) { %><%=DataFormat.getDateString(tsPublicDate).substring(0,4)%>年&nbsp;<%=DataFormat.getDateString(tsPublicDate).substring(5,7)%>月&nbsp;<%=DataFormat.getDateString(tsPublicDate).substring(8,10) %>日<% } else { out.print("年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日");}%></td>
          <td align="center" class="td-rightbottom">账　号</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyAccount)%></td>
        </tr>
        <tr> 
          <td align="center" class="td-rightbottom" height="25">到期日</td>
          <td align="center" colspan="7" class="td-rightbottom"><% if (tsAtTerm != null) { %><%=DataFormat.getDateString(tsAtTerm).substring(0,4)%>年&nbsp;<%=DataFormat.getDateString(tsAtTerm).substring(5,7)%>月&nbsp;<%=DataFormat.getDateString(tsAtTerm).substring(8,10)%>日<% } else { out.print("年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日");}%></td>
          <td align="center" class="td-rightbottom">开户银行</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyBank)%></td>
        </tr>
        <tr> 
          <td align="center" colspan="2" class="td-rightbottom" height="25">汇票出票人</td>
          <td align="center" class="td-rightbottom">名称</td>
          <td align="center" colspan="5" width="80" class="td-rightbottom"><%=DataFormat.formatString(strAcceptClient)%></td>
          <td align="center" class="td-rightbottom">账号</td>
          <td align="center" colspan="2" class="td-rightbottom"><%=DataFormat.formatString(strAcceptAccount)%></td>
          <td align="center" class="td-rightbottom">开户银行</td>
          <td align="center" colspan="2" class="td-bottom"><%=DataFormat.formatString(strAcceptBank)%></td>
        </tr>
        <tr> 
          <td align="center" colspan="2" class="td-rightbottom" height="40">汇票金额</td>
          <td colspan="10" class="td-rightbottom">&nbsp;人民币<br>
         	（大写）<%=ChineseCurrency.showChinese( DataFormat.formatAmount(dBillAmount))%></td>
          <td colspan="2" class="td-bottom">
		    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	  <tr>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">亿</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">千</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">百</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">十</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">万</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">千</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">百</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">十</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">元</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">角</span></td>
          		<td width="8" align="center" class="td-bottom"><span class="f12">分</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),1)%></span></td>
       		</tr>
          	</table></td>
        </tr>
        <tr> 
          <td align="center" nowrap class="td-rightbottom" height="40">贴现率</td>
          <td align="right" class="td-rightbottom"><%=dRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dRate))+"%"%>&nbsp;</td>
<%
		if(i==1)
		{
%>
			<td colspan="3" align="center" class="td-topleftright2bottom2">贴现利息</td>
			<td colspan="5" class="td-bottom">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">亿</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">千</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">百</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">十</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">万</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">千</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">百</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">十</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">元</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">角</span></td>
					<td align="center" width="8" class="td-topright2bottom"><span class="f12">分</span></td>
				</tr>
				<tr>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),11)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),10)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),9)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),8)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),7)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),6)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),5)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),4)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),3)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),2)%></span></td>
					<td align="center" class="td-right2bottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),1)%></span></td>
				</tr>
			</table>
		</td>
<%
		}
		else
		{
%>
          <td colspan="3" align="center" class="td-rightbottom">贴现利息</td>
          <td colspan="5" class="td-rightbottom">
		    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	<tr>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">亿</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">千</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">百</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">十</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">万</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">千</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">百</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">十</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">元</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">角</span></td>
          		<td align="center" width="8" class="td-bottom"><span class="f12">分</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),1)%></span></td>
       		</tr>
          	</table></td>
<%
		}
%>
<%
		if(i==2)
		{
%>
			<td colspan="2" align="center" class="td-topleftright2bottom2">实付贴现金额</td>
			<td colspan="2" class="td-bottom">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">亿</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">千</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">百</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">十</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">万</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">千</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">百</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">十</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">元</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">角</span></td>
					<td align="center" width="8" class="td-topbottom"><span class="f12">分</span></td>
				</tr>
				<tr>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),11)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),10)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),9)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),8)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),7)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),6)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),5)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),4)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),3)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),2)%></span></td>
					<td align="center" class="td-bottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),1)%></span></td>
				</tr>
			</table>
			</td>
<%
		}
		else
		{
%>
          <td colspan="2" align="center" class="td-rightbottom">实付贴现金额</td>
          <td colspan="2" class="td-bottom">
		  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	<tr>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">亿</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">千</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">百</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">十</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">万</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">千</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">百</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">十</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">元</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">角</span></td>
          		<td align="center" width="8" class="td-bottom"><span class="f12">分</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),1)%></span></td>
       		</tr>
          	</table></td>
<%
		}
%>
        </tr>
        <tr valign="top">
        	<td height="50" colspan="14" valign="middle">&nbsp;摘要：</td>
   		</tr>
  </table></td>
  <td width="20" align="center">第<%	
									switch (i)
									{
										case 0 :
											out.print("一");
											break;
										case 1 :
											out.print("二");
											break;
										case 2 :
											out.print("三");
											break;
										case 3 :
											out.print("四");
											break;
										case 4 :
											out.print("五");
											break;
									}
								  %>联<br>记账附件</td>
  </tr>
</table>
<table width="650" border="0" align="center">
	<tr valign="bottom">
		<td width="25%">主管经理：<%=strApprovalUserName[0]%></td>
		<td width="25%">部门经理：<%=strApprovalUserName[1]%></td>
		<td width="25%">复核：<%=strApprovalUserName[2]%></td>
		<td width="25%">经办：<%=strApprovalUserName[3]%></td>
	</tr>
</table>
<% if (i < 4) { %>
<br clear=all style='page-break-before:always'>
<% } %>
<% } %>

<SCRIPT language="JavaScript">
	window.print();
</SCRIPT>

</body>
</html>

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