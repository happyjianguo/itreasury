<%
/**
 * 页面名称 ：q084-v.jsp
 * 页面功能 : 免还申请查询详细信息
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 */	
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.system.approval.bizlogic.*,
				com.iss.itreasury.system.approval.dataentity.*,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.loan.freeapply.dataentity.*,
				com.iss.itreasury.loan.freeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%


	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;

	String strTableTitle = "免还情况查询—免还通知单明细";
	try
	{
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

		OBFreeQuery ejb = new OBFreeQuery();
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
		
		OBContractQuery contractejb = new OBContractQuery();
		ContractInfo contractinfo = new ContractInfo();
		LoanPayNoticeInfo payinfo = new LoanPayNoticeInfo();

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		String strURL = "";
		long lIsSM = Constant.YesOrNo.YES;
		int backflag = 0;
		long lFreeApplyID = -1;
		
		long lTypeID=-1;
		long lContractID=-1;		//合同标示
		String strContractCode="";	//合同编号
		long lClientID=-1;			//借款单位标示
		double dAmount = 0;//贷款金额
		long nIntervalNum = -1;//贷款期限
		double dBalance = 0;//贷款余额
		double dLoanRate=0;//贷款利率
		String strLoanPurpose="";//借款用途
		String strBorrowClientName="";	//借款单位名称
		String strConsignClientName="";	//委托单位名称
		String strConsignClientAccount = "";//委托账户

		long lLoanPayID=-1;//放款通知单ID
		String strLoanPayCode=null;	//放款通知单编号
		double dPayAmount = 0;//放款金额
		double dPayBalance = 0;//放款余额
		Timestamp dtEndDate = null;//到期日

		String strFreeCode = "";//免还编号
		double mFreeAmount = 0;//免还金额
		double mFreeRate = 0;//免还利息
		Timestamp dtFreeDate = null;//免还日
		String strFreeReason = "";//免还原因
		long lFreeApplyStatusID = -1;//免还状态
		long lInputUserID = -1;//录入人
		String strInputUserName="";//录入人名称
		long lCheckUserID=-1;//审核人
		String strCheckUserName="";//审核人名称
		Timestamp dtInputDate=null;//录入日期
		Timestamp dtToday = Env.getSystemDate();//新增日期
		long lAction=0;//审核操作
		String strOptions="";//审核意见

///////control/////////////////////////////////////////////
		strTmp = (String)request.getAttribute("type");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lTypeID =Long.parseLong(strTmp.trim());
		}
		//log4j.info("loantype:"+lTypeID);
		//log4j.info("---325-----------1------------");
		strTmp = (String)request.getAttribute("isSM");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lIsSM = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lContractID");//合同ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lLoanPayID");//放款单ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanPayID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lFreeApplyID");//免还ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lFreeApplyID = Long.parseLong(strTmp.trim());
		}
		//log4j.info("---325-----------2------------");
		///////////////////////////////////////////////////
		strTmp = (String)request.getAttribute("control");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strControl = strTmp.trim();
		}
		strTmp = (String)request.getAttribute("backurl");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strBackURL = strTmp.trim();
			//log4j.info("backurl:"+strBackURL);
		}//*
		if(strBackURL.equals("S324-1"))
		{
			strTmp = (String)request.getAttribute("strURL");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strURL = strTmp.trim();
				//log4j.info("参数:"+strURL);
			}
		}
		
///////////////view//////////////////////////////////////////////////
		if (strControl.equals("view"))
		{
			freeinfo=ejb.findFreeApplyByID(lFreeApplyID);
			//-------------------------------------------------------------------------//
			strFreeCode=freeinfo.getFreeCode();
			mFreeAmount=freeinfo.getFreeAmount();						//免还金额
			mFreeRate=freeinfo.getFreeRate();							//免还利息
			dtFreeDate=freeinfo.getFreeDate();							//免还日
			strFreeReason=freeinfo.getFreeReason();						//免还原因
			strConsignClientAccount=freeinfo.getConsignClientAccount();	//委托账户
			lFreeApplyStatusID=freeinfo.getStatusID();					//免还状态
			lInputUserID=freeinfo.getInputUserID();//录入人ID
			lCheckUserID=freeinfo.getCheckUserID();//审核人ID
			strInputUserName=freeinfo.getInputUserName();//录入人名称
			strCheckUserName=freeinfo.getCheckUserName();//审核人名称
			dtInputDate=freeinfo.getInputDate();//录入日期
			//--------------------------------------------------------------------------//
			lContractID=freeinfo.getContractID();
			contractinfo = contractejb.findByID(lContractID);
			//---------------------------//
			strContractCode=contractinfo.getContractCode();			//合同编号
			dAmount =contractinfo.getLoanAmount();					//贷款金额
			nIntervalNum =contractinfo.getIntervalNum();			//期限
			dLoanRate=contractinfo.getInterestRate();				//贷款执行利率
			dBalance =contractinfo.getBalance();					//贷款余额，未还金额
			strLoanPurpose=contractinfo.getLoanPurpose();			//借款用途
			strBorrowClientName=contractinfo.getBorrowClientName();	//借款单位名称
			dtEndDate = contractinfo.getLoanEnd();					//到期日
			//---------------------------------------------------------------------------//
			lLoanPayID=freeinfo.getLoanPayID();
			payinfo = contractejb.findLoanPayNoticeByID(lLoanPayID);
			//---------------------------//
			strConsignClientName=payinfo.getConsignClientName();//委托单位名称
			strLoanPayCode=payinfo.getCode();					//放款通知单编号
			dPayAmount=payinfo.getAmount();						//放款金额
			dPayBalance=payinfo.getBalance();					//放款余额
		}
///////////////////////////////////////////////////////////////////////////////////////////////////

		//定义变量，获取请求参数
		// 定义相应操作常量
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
		//贷款
		long lModuleID = Constant.ModuleType.LOAN;
		//模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.WT;
		lLoanTypeID=lTypeID;
		long lActionID = Constant.ApprovalAction.FREE_APPLY;
		//历史意见
		Collection ColappBiz = null;
		ColappBiz = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lFreeApplyID,-1);

		OBHtml.showOBHomeHead(out,sessionMng,strTableTitle,Constant.YesOrNo.NO);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<form name="frm">
<TABLE border=0 class=top height=60 width="50%">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=35><B><B><%=strTableTitle%></B></B></TD></TR>
	<TR>
		<TD vAlign=top>
		<TABLE align=center border=0 height=60 width=730>
			<TBODY>
			<TR>
				<TD height=23 width=1>&nbsp;</TD>
				<TD colSpan=2 height=23>合同编号：<%=DataFormat.formatString(strContractCode)%></TD>
				<TD colSpan=6 height=23></TD>
			</TR>
			<TR>
				<TD height=24 width=1>&nbsp;</TD>
				<TD colSpan=8 height=24>
					<HR>
				</TD>
				<TD align=right height=24 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30><U>合同基本资料</U></TD>
				<TD align=right height=30 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=100></TD>
						<TD width=100></TD>
						<TD width=400></TD>
					</TR>
					<TR>
						<TD>贷款单位：</TD>
						<TD colspan=5>
							<INPUT class=box size=80 disabled name="strLoanClientName1" value="<%=DataFormat.formatString(strLoanClientName)%>">
						</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>贷款金额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%>
							<INPUT class=tar disabled name="dAmount1" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD  align=right>贷款期限：</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=10 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> 月
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>贷款余额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%>
							<INPUT class=tar disabled name="dBalance1" size=18 value="<%=DataFormat.formatListAmount(dBalance)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>贷款利率：</TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=10 value="<%=DataFormat.formatRate(dLoanRate)%>"> %
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>借款用途:</TD>
						<TD  colspan=3>
							<INPUT class=box disabled name="strLoanPurpose" size=45 value="<%=DataFormat.formatString(strLoanPurpose)%>"> 
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
					<HR>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
					<DIV align=center><B>委托贷款免还通知单</B></DIV>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=225 width=1>&nbsp;</TD>
				<TD colSpan=8 height=225>
				<TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height=224 width="100%">
					<TBODY>
					<TR>
						<TD>
						<TABLE border=0 height=136 width="98%">
							<TBODY>
							<TR>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=30></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=100></TD>
								<TD height=32 width=380></TD>
							</TR>
							<TR>
								<TD colSpan=2>借款单位：</TD>
								<TD colSpan=1></TD>
								<TD colspan=4>
									<INPUT class=box size=70 disabled name="strBorrowClientName" size=16 value="<%=DataFormat.formatString(strBorrowClientName)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>借款合同编号：</TD>
								<TD colSpan=1></TD>
								<TD>
									<INPUT class=box disabled name="strContractCode" size=18 value="<%=DataFormat.formatString(strContractCode)%>">
								</TD>
								<TD colSpan=2 align=right>放款通知单编号：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box disabled name="strLoanPayCode" size=18 value="<%=DataFormat.formatString(strLoanPayCode)%>"></TD>
							</TR>
							<TR>
								<TD colSpan=2>委托单位：</TD>
								<TD colSpan=1></TD>
								<TD colspan=4>
									<INPUT class=box size=70 disabled name="strConsignClientName" size=16 value="<%=DataFormat.formatString(strConsignClientName)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>委托账号：</TD>
								<TD colSpan=1></TD>
								<TD>
									<INPUT class=box onfocus="nextfield='mFreeAmount'" name="strConsignClientAccount" size=18 disabled value="<%=DataFormat.formatString(strConsignClientAccount)%>">
								</TD>
								<TD colSpan=2></TD>
								<TD height=27></TD>
							</TR>
							<TR>
								<TD colSpan=2>原借款：</TD>
								<TD colSpan=1>
									<DIV align=left><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<INPUT class=tar disabled name="dAmount" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>"> 
								</TD>
								<TD colSpan=2 align=right>期限：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=tar disabled name="nIntervalNum" size=6 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> 月
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>贷款余额：</TD>
								<TD colSpan=1><%=sessionMng.m_strCurrencySymbol%></TD>
								<TD>
									<INPUT class=tar disabled name="dBalance" size=18 value="<%=DataFormat.formatListAmount(dBalance)%>"> 
								</TD>
								<TD colSpan=2 align=right>到期日：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box disabled name="dtEndDate" size=18 value="<%=DataFormat.getDateString(dtEndDate)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>放款通知单金额：</TD>
								<TD colSpan=1>
									<DIV align=left><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<INPUT class=tar disabled name="dPayAmount" size=18 value="<%=DataFormat.formatListAmount(dPayAmount)%>"> 
								</TD>
								<TD colSpan=2 align=right>放款通知单余额：</TD>
								<TD><%=sessionMng.m_strCurrencySymbol%>
									<INPUT class=tar disabled name="dPayBalance" size=18 value="<%=DataFormat.formatListAmount(dPayBalance)%>"> 
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>免还金额：</TD>
								<TD colSpan=1>
									<DIV align=left><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<INPUT class=tar onfocus="nextfield='mFreeRate'" name="mFreeAmount" size=18 value="<%=DataFormat.formatListAmount(mFreeAmount)%>" disabled>
									
								</TD>
								<TD colSpan=2 align=right>免还日：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box onfocus="nextfield='mFreeRate'" name="dtFreeDate" size=18 value="<%=DataFormat.getDateString(dtFreeDate)%>" disabled>
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>免还利息：</TD>
								<TD colSpan=1><%=sessionMng.m_strCurrencySymbol%></TD>
								<TD>
									<INPUT class=tar onfocus="nextfield='strFreeReason'" name="mFreeRate" size=18 value="<%=DataFormat.formatListAmount(mFreeRate)%>" disabled>
								</TD>
								<TD colSpan=2 align=right>免还编号：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box onfocus="nextfield='mFreeRate'" name="dtFreeDate" size=18 value="<%=DataFormat.formatString(strFreeCode)%>" disabled>
								</TD>
							</TR>
							<TR>
								<TD colSpan=3>免还原因：</TD>
								<TD colSpan=4>
									<TEXTAREA cols=60 name="strFreeReason"  onfocus="nextfield='print'" disabled><%=DataFormat.formatString(strFreeReason)%></TEXTAREA> 
								</TD>
							</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=225 width=1>&nbsp;</TD></TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=2 width=1>
				
	<table height=30 width="98%" align=center>
		<tbody> 
			<tr> 
				<td valign=top width="47%" height=30> 
					<table bordercolor=#999999 height="100%" width="100%" align=left border=1>
						<tr bordercolor=#d7dfe5> 
							<td valign=middle colspan=2 height=20> 
							<table width="100%" border="0" cellpadding="0">
								<tr> 
									<td width="19%" nowrap><u>本公司审批意见</u></td>
									<td width="81%" nowrap>&nbsp; </td>
								</tr>
								<tr> 
									<td width="19%" nowrap>历史审核意见：</td>
									<td width="81%" nowrap> 
									<table class=ItemList width="99%" align=left border=0 cellspacing="2" cellpadding="2">
										<tr class="tableHeader"> 
											<td class=ItemTitle width="15%" height=20 nowrap> 
												<div align=center>序列号</div>
											</td>
											<td class=ItemTitle width="15%" height=20 nowrap> 
												<div align=center>意见内容</div>
											</td>
											<td class=ItemTitle width="15%" height=20 nowrap> 
												<div align=center> 审核人</div>
											</td>
											<td class=ItemTitle width="20%" height=20 nowrap> 
												<div align=center> 审核决定</div>
											</td>
											<td class=ItemTitle width="15%" height=20 nowrap> 
												<div align=center> 时间和日期</div>
											</td>
										</tr>
<%
	if (ColappBiz != null) 
	{
		Iterator iter = ColappBiz.iterator();
		while (iter.hasNext())
		{
			tracinginfo = (ApprovalTracingInfo)iter.next();
			
		%>
			<tr> 
				<td class="ItemBody" width="12%" height="24">&nbsp;<%= tracinginfo.getSerialID()%></td>
				<td class="ItemBody" width="21%" height="24">&nbsp;<%= (tracinginfo.getOpinion()==null?"":tracinginfo.getOpinion()) %></td>
				<td class="ItemBody" width="21%" height="24">&nbsp;<%= tracinginfo.getUserName() %></td>
				<td class="ItemBody" width="20%" height="24">&nbsp;<%= Constant.ApprovalDecision.getName(tracinginfo.getResultID())%></td>
				<td class="ItemBody" width="26%" height="24">&nbsp;<%= DataFormat.getDateTimeString(tracinginfo.getApprovalDate()) %></td>
			</tr>
<%		}
	}
	else
	{
%>
			<tr>
				<td class="ItemBody" width="12%" height="24">&nbsp;</td>
				<td class="ItemBody" width="21%" height="24">&nbsp;</td>
				<td class="ItemBody" width="21%" height="24">&nbsp;</td>
				<td class="ItemBody" width="20%" height="24">&nbsp;</td>
				<td class="ItemBody" width="26%" height="24">&nbsp;</td>
			</tr>
<%	}
%>
		</table>
		</td>
	</tr>
</table>
</td>
</tr>
</table>
</tr>
</tbody> 
</table>
	<input type=hidden name="lAction" value="-1">

	<table width="98%" border="0" cellpadding="0">
		<tr>
			<td>
				<div align="right">
					<INPUT class=button name=print onclick="window.open('q095-p.jsp?control=view&nTmpID=<%=lFreeApplyID%>&isSM=<%=Constant.YesOrNo.NO%>&type=<%=lTypeID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" type="button" value="打 印" >
						<INPUT class=button name=Return onclick="javascript:window.close();" type="button" value="关 闭" onKeydown="javascript:window.close();">
				</div>
			</td>
		</tr>
	</table>
	<table>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD height=2 width=379><U>执行计划详细</U></TD>
				<TD colSpan=7 height=2></TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<tr> 
				<td width="1" height="2">&nbsp;</td>
				<td width="379" height="2">
					<input type="button" name="Submit4224" onclick="window.open('q094-v.jsp?control=view&isYU=2&nTmpID=<%=contractinfo.getPlanVersionID()%>&isSM=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" value="执行计划" class="button">
				</td>
				<td colspan="7" height="2"> 
					<div align="right"></div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<tr>
				<td width="1">&nbsp;</td>
				<td colspan=1>录入人：<%=DataFormat.formatString(strInputUserName)%></td>
				<td colspan=1>录入时间：
					<%if(dtInputDate != null){out.println(DataFormat.getDateString(dtInputDate));} else {out.println("");}%>
				</td>
				<td colspan=1 width="100">状态：
					<%if(lFreeApplyStatusID > 0){out.println(LOANConstant.FreeApplyStatus.getName(lFreeApplyStatusID));} else {out.println("");}%>
				</td>
				<td height="2" width="1">&nbsp;</td>
			</tr>
		</table>
	</TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type=hidden name="control" value="view">
<input type=hidden name="lFreeAction">

<input type=hidden name="lContractID" value="<%=lContractID%>">
<input type=hidden name="lLoanPayID" value="<%=lLoanPayID%>">
<input type=hidden name="lFreeApplyID" value="<%=lFreeApplyID%>">
<input type=hidden name="type" value="<%=lTypeID%>">


<P><BR></P>
</form>

<script language="javascript">
function printIt(url)
{

	//window.open(url,"popup", "width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");
	window.open('../free/'+url+'&control=view&nTmpID=<%=lFreeApplyID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}

</script>

<script language="javascript">
	firstFocus(frm.Return);
	//setSubmitFunction("window.close()");
	setFormName("frm");

</script>

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
