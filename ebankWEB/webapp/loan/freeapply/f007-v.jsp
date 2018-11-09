<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,	
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.system.bizlogic.*,
				com.iss.itreasury.system.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.ebank.obrepayplan.bizlogic.*,
				com.iss.itreasury.ebank.obrepayplan.dataentity.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,	
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.repayplan.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.repayplan.bizlogic.*,
				com.iss.itreasury.ebank.approval.bizlogic.*,
				com.iss.itreasury.ebank.approval.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>

<%
		//log4j //log4j = new //log4j(Constant.ModuleType.LOAN);
		String strTitle  = "委托贷款免还申请—免还通知单明细";
		String strOfficeName = sessionMng.m_strOfficeName;
		String strUserName = sessionMng.m_strUserName;
		long lUserID = sessionMng.m_lUserID;
		long lClientID = sessionMng.m_lClientID;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
try
{

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

		//定义变量
		

		String strTmp = "";
		String strFreeCode = "";
		String strClientName ="";
		double mPayInterest  = 0;
		long lID = -1;
		String[] strOpinion = {"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] strCheckName = {"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] sDate = {"&nbsp;&nbsp;年　月　日","&nbsp;&nbsp;年　月　日","&nbsp;&nbsp;年　月　日","&nbsp;&nbsp;年　月　日"};

		//获取必需参数

///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
////////////////////////////////////////////////////////////////////*/
		//OBRepayPlanHome payplanhome = (OBRepayPlanHome) EJBObject.getEJBHome("OBRepayPlanHome");
		//OBRepayPlan payplanejb = payplanhome.create();
		OBContractQuery repayplan = new OBContractQuery();
		//RepayPlanInfo payplaninfo = new RepayPlanInfo();
////////////////////////////////////////////////////////////////////*/
		//定义变量，获取请求参数

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		//String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		int backflag = 0;
		long lFreeApplyID = -1;
		
		long lTypeID=-1;
		long lContractID=-1;		//合同标示
		String strContractCode="";	//合同编号
		double dAmount = 0;//贷款金额
		long nIntervalNum = -1;//贷款期限
		double dBalance = 0;//贷款余额
		double dLoanRate=0;//贷款利率
		double dChargeRate = 0;//手续费率
		String strLoanPurpose="";//借款用途
		String strBorrowClientName="";	//借款单位名称
		String strConsignClientName="";	//委托单位名称
		String strConsignClientAccount = "";//委托账户

		long lLoanPayID=-1;//放款通知单ID
		String strLoanPayCode=null;	//放款通知单编号
		double dPayAmount = 0;//放款金额
		double dPayBalance = 0;//放款余额
		Timestamp dtEndDate = null;//到期日
		Timestamp dtStartDate = null;//发放日

		double mFreeAmount = 0;//免还金额
		double mFreeRate = 0;//免还利息
		Timestamp dtFreeDate = null;//免还日
		String strFreeReason = "";//免还原因
		long lStatusID = -1;//免还状态
		long lInputUserID = -1;//录入人
		String strInputUserName="";//录入人名称
		long lCheckUserID=-1;//审核人
		String strCheckUserName="";//审核人名称
		Timestamp dtInputDate=null;//录入日期
		Timestamp dtToday = Env.getSystemDate();//新增日期

///////control/////////////////////////////////////////////

		freeinfo = (FreeApplyInfo)request.getAttribute("freeinfo");
		//System.out.println("stop here!!!");
///////////////////////////////////////view////////////////////////////////////////////////
				strFreeCode=freeinfo.getFreeCode();
				mFreeAmount=freeinfo.getFreeAmount();						//免还金额
				mFreeRate=freeinfo.getFreeRate();							//免还利息
				dtFreeDate=freeinfo.getFreeDate();							//免还日
				strFreeReason=freeinfo.getFreeReason();						//免还原因
				strConsignClientAccount=freeinfo.getConsignClientAccount();	//委托账户
				lStatusID=freeinfo.getStatusID();							//免还状态
				lInputUserID=freeinfo.getInputUserID();//录入人ID
				lCheckUserID=freeinfo.getCheckUserID();//审核人ID
				strInputUserName=freeinfo.getInputUserName();//录入人名称
				strCheckUserName=freeinfo.getCheckUserName();//审核人名称
				dtInputDate=freeinfo.getInputDate();//录入日期
				//--------------------------------------------------------------------------//
				strContractCode=freeinfo.getContractCode();			//合同编号
				dAmount =freeinfo.getLoanAmount();				//贷款金额
				nIntervalNum =freeinfo.getIntervalNum();			//期限
				dLoanRate=freeinfo.getInterestRate();				//贷款执行利率
				dBalance =freeinfo.getBalance();					//贷款余额，未还金额
				strLoanPurpose=freeinfo.getLoanPurpose();			//借款用途
				strClientName=freeinfo.getClientName();				//借款单位名称
				//dtEndDate = freeinfo.getEndDate();					//到期日
				//lPlanID = freeinfo.getPlanVersionID();
				//---------------------------------------------------------------------------//
				strConsignClientName=freeinfo.getConsignClientName();//委托单位名称
				strLoanPayCode=freeinfo.getLoanPayCode();			//放款通知单编号
				dPayAmount=freeinfo.getLoanPayAmount();				//放款金额
				dPayBalance=freeinfo.getLoanPayBalance();			//放款余额
				mPayInterest=freeinfo.getLoanPayInterest();					//放款利息
				dtEndDate = freeinfo.getPayLoanEndDate();						//到期日
				dtStartDate=freeinfo.getPayLoanStartDate();						//发放日
				dChargeRate=freeinfo.getChargeRate();//手续费率
				
				strBorrowClientName=freeinfo.getClientName();
				
	
		// 定义相应操作常量
//*****************************************************************************************
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
		//贷款
		long lModuleID = Constant.ModuleType.LOAN;
		//模块类型
		long lLoanTypeID = lTypeID;
		long lActionID = Constant.ApprovalAction.FREE_APPLY;
		//历史意见
		Collection ColappBiz = null;
		long lDesc1 = Constant.PageControl.CODE_ASCORDESC_DESC;//排序变量

		ColappBiz = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lFreeApplyID,lDesc1);
		if (ColappBiz != null) 
		{
			if(ColappBiz.size() >=4)
			{
				Iterator iter = ColappBiz.iterator();
				int nOpeinion = 3;
				while (iter.hasNext() && nOpeinion >= 0)
				{
					//log4j.info(">=4");
					tracinginfo = (ApprovalTracingInfo)iter.next();
					strOpinion[nOpeinion] = DataFormat.formatString(tracinginfo.getOpinion());
					strCheckName[nOpeinion] = DataFormat.formatString(tracinginfo.getUserName());
					sDate[nOpeinion] = DataFormat.getChineseDateString(tracinginfo.getApprovalDate());
					nOpeinion--;
				}
			}
			else
			{
				Iterator iter = ColappBiz.iterator();
				int nOpeinion = ColappBiz.size()-1;
				while (iter.hasNext() && nOpeinion >= 0)
				{
					//log4j.info("<4");
					tracinginfo = (ApprovalTracingInfo)iter.next();
					strOpinion[nOpeinion] = DataFormat.formatString(tracinginfo.getOpinion());
					strCheckName[nOpeinion] = DataFormat.formatString(tracinginfo.getUserName());
					sDate[nOpeinion] = DataFormat.getChineseDateString(tracinginfo.getApprovalDate());
					nOpeinion--;
				}
			}
		}
	//*****************************************************************************************
		//payplanejb
		Collection ColpayPlan = null;
		long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;//每页显示记录数
		long lPageNo=1;
		long lOrderParam = 1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;//
		
		OBPageInfo pageinfo = new OBPageInfo();
		OBSecurityInfo sinfo = new OBSecurityInfo();
		
		pageinfo.setPageLineCount(lPageLineCount);
		pageinfo.setPageNo(lPageNo);
		pageinfo.setOrderParam(lOrderParam);
		pageinfo.setDesc(lDesc);
			
		sinfo.setOfficeID(lOfficeID);
		sinfo.setCurrencyID(lCurrencyID);
		sinfo.setUserID(lUserID);
		sinfo.setClientID(lClientID);
		
		//ColpayPlan = payplanejb.findPlanByContract(lContractID,pageinfo,sinfo);
		Collection c = null;
		//c=repayplan.findPlanByVer(freeinfo.getPlanVersionID(),Constant.PageControl.CODE_PAGELINECOUNT,0,lOrderParam,lDesc,sessionMng.m_lUserID,sessionMng.m_lOfficeID);
		c=repayplan.findPlanByContract(freeinfo.getContractID(),1000,1,1,1);
		//c=repayplan.findPlanDetailByContractID(freeinfo.getContractID());
		int nPlan=0;
		RepayPlanInfo rp_info = new RepayPlanInfo();
		
		if (c != null)
		{
			nPlan = c.size();

			//log4j.info("nPlan:"+nPlan);
		}
		String[] strPayTime=new String[nPlan];
		String[] strRepayTime=new String[nPlan];
		String[] strPayAmount=new String[nPlan];
		String[] strRepayAmount=new String[nPlan];
		double dTotalPay = 0;
		double dTotalRepay = 0;
		int iPay=0;
		int iRepay=0;
		if (c != null)
		{
			Iterator iterPlan = c.iterator();
			while(iterPlan!=null && iterPlan.hasNext())
			{
				rp_info = (RepayPlanInfo)iterPlan.next();
				if(rp_info.nLoanOrRepay==LOANConstant.PlanType.PAY)//用款（放款）
				{
					if(rp_info.tsPlanDate!=null)
					{
						strPayTime[iPay]=DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strPayTime[iPay]="&nbsp;";
					}
					if(rp_info.dAmount>=0)
					{
						strPayAmount[iPay]=DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strPayAmount[iPay]="&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				}
				else//还款
				{
					if(rp_info.tsPlanDate!=null)
					{
						strRepayTime[iRepay]=DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strRepayTime[iRepay]="&nbsp;";
					}
					if(rp_info.dAmount>=0)
					{
						strRepayAmount[iRepay]=DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strRepayAmount[iRepay]="&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}//end while(iter.hasNext())
		}
		nPlan=iRepay>iPay?iRepay:iPay;


//*****************************************************************************************
%>
<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gbk">
<title>委托贷款免还通知单</title>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<style type="text/css">
<!--
.table1 {  border: 2px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
-->
</style>
</head>
	

<body bgcolor="#FFFFFF">
<table width="630" border="0" cellspacing="0" cellpadding="0" height="960">
<tr>
<td valign="top" align="center">
<table width="100%" border="0">

<tr>
<td align="center" width="25%"><font size="5"></font></td>
<td align="center" width="50%"><strong><font size="5"><b>委托贷款免还通知单</b></font></strong></td>
<td align="center" width="25%">&nbsp;</td>
</tr>
<tr>
<td align="right" width="25%">&nbsp;</td>
<td align="right">&nbsp;</td>
<td align="right" width="25%">&nbsp;</td>
</tr>
</table>
<font size="4"></font>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="table1" height="850">
<tr>
<td class="td-rightbottom" align="center" height="36">委托单位</td>
<td class="td-rightbottom" align="center" height="36" width="30%"><%=strConsignClientName==null?"&nbsp;":strConsignClientName%>&nbsp;</td>
<td class="td-rightbottom" width="20%" align="center">委托存款账号</td>
<td class="td-bottom" width="30%"><%=strConsignClientAccount==null?"&nbsp;":strConsignClientAccount%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36" width="12%">借款单位</td>
<td class="td-rightbottom" align="center" height="36" colspan="1"><%=strBorrowClientName==null?"&nbsp;":strBorrowClientName%>&nbsp;</td>
<td class="td-rightbottom" align="center" height="36" width="12%">合同号</td>
<td class="td-bottom"><%=strContractCode==null?"&nbsp;":strContractCode%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">借款金额</td>
<%String Amount = DataFormat.formatAmount(dAmount);%>
<td class="td-bottom" height="36"  colspan=3>人民币(大写)
					<%=ChineseCurrency.showChinese(Amount)%>(<%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(dAmount,2)%>)</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">利　　率</td>
<td class="td-rightbottom" align="center" height="36"><%=dLoanRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dLoanRate))+"%"%>&nbsp;</td>
<td class="td-rightbottom" align="center">手续费率</td>
<td class="td-bottom" align="center"><%=dChargeRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dChargeRate))+"%"%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">期　　限</td>
<td class="td-rightbottom" align="center" colspan=1><%if ((freeinfo!=null)&&(nIntervalNum>0)) {out.println(nIntervalNum);} else {out.println("&nbsp;");}%>个月</td>
<td class="td-rightbottom" align="center">发放日期</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtStartDate)%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">贷款余额</td>
<td class="td-rightbottom" align="center" height="36"><%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(dBalance,2)%></td>
<td class="td-rightbottom" align="center">到期日</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtEndDate)%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36"> 免还金额</td>
<td class="td-rightbottom" align="center" height="36"><%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(mFreeAmount,2)%></td>
<td class="td-rightbottom" align="center">免还日</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtFreeDate)%>&nbsp;</td>
</tr>





<tr>
<td class="td-bottom" colspan="4" height="150">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">

<tr valign="top">
<td colspan="2" height="30">免还原因：（委托单位填写）</td>
</tr>
<tr>
<td colspan="2" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<%=DataFormat.formatString(strFreeReason)%></td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%">委托单位公章：</td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%">负责人：</td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%" align="center" height="40"><br>
年　月　日
</td>
</tr>
</table>
</td>
</tr>
<tr align="center">
<td class="td-bottom" colspan="4" height="30">中　国　华　能　财　务　有　限　责　任　公　司</td>
</tr>
	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">经办人意见：</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[0]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">签章</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>经办人签名:<%=strCheckName[0]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[0]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">复核人意见：</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[1]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">签章</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>复核人签名:<%=strCheckName[1]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[1]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">经理意见：</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[2]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">签章</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>经理签名:<%=strCheckName[2]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[2]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>


</table>

<table width="100%" border="0">
<tr>
<td>此通知单由委托单位填写，一式四份，由委托单位签字盖章后报送<%=Env.getClientName()%>。</td>
</tr>
</table>
</td>
</tr>
</table>


<!-- ------------------------------------------------------------------------------------------------Page2 -->
<p>&nbsp;</p>
<font size="4"><b>附件： </b></font>
<table width="630" border="0" cellspacing="0" cellpadding="0" height="800">
<tr>
<td valign="top" align="center">
<font size="4"><b>用　款、还　款　计　划</b></font>
<table width="100%" border="1" cellspacing="0" class="table1" >
<tr>
<td colspan='2' ALIGN="center" width="50%"  class="td-rightbottom">用款计划</td>
<td colspan='2' ALIGN="center" width="50%" class="td-rightbottom">还款计划</td>
</tr>
<tr align="center">
<td width="25%"  class="td-rightbottom">计划时间</td>
<td width="25%"  class="td-rightbottom">金额</td>
<td width="25%"  class="td-rightbottom">计划时间</td>
<td width="25%"  class="td-rightbottom">金额</td>
</tr>
<%
	for(int i=0;i<nPlan;i++)
	{

%>
<TR>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strPayTime[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strPayAmount[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strRepayTime[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strRepayAmount[i])%></TD>
</TR>
<%
	}
%>
<TR>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
</TR>
<tr align="center">
<td width="25%"  class="td-rightbottom">总计:</td>
<td width="25%"  class="td-rightbottom">&nbsp;<%=DataFormat.formatListAmount(dTotalPay)%></td>
<td width="25%"  class="td-rightbottom">总计:</td>
<td width="25%"  class="td-rightbottom">&nbsp;<%=DataFormat.formatListAmount(dTotalRepay)%></td>
</tr>
</table>
<p>&nbsp;</p>
</td>
</tr>
</table>
<!-- ------------------------------------------------------------------------------------------------Page2 -->
</body>

</html>
<script language="JavaScript">
<!--
window.print();
//-->
</script>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<%	
   //显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>