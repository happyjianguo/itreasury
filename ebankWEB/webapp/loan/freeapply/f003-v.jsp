<%
	/*
	*页面名称：f003-v.jsp
	*功能：免还申请-查找
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[免还明细]";
%>					
<%
  try
  {
	
	   /* 用户登录检测 */
	   
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
		
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		%>


<%

		String strOfficeName = sessionMng.m_strOfficeName;
		long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
		long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
		long lUserID = sessionMng.m_lUserID;
		String strUserName = sessionMng.m_strUserName;
		
	
			
		//定义变量，获取请求参数

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		String strDisabled = "";
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
		String strClientName="";	//借款单位名称
		String strConsignClientName="";	//委托单位名称
		String strConsignClientAccount = "";//委托账户

		long lPlanID = -1;

		long lLoanPayID=-1;//放款通知单ID
		String strLoanPayCode=null;	//放款通知单编号
		double dPayAmount = 0;//放款金额
		double dPayBalance = 0;//放款余额
		double mPayInterest = 0;//放款利息
		Timestamp dtEndDate = null;//到期日

		String strFreeCode = "";
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
		FreeApplyInfo freeinfo = null;
///////control/////////////////////////////////////////////
		//log4j.info("---321-----------1------------");
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
		
		strTmp = (String)request.getAttribute("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		
		freeinfo = (FreeApplyInfo)request.getAttribute("freeinfo");
	
///////////////////////////////////////view////////////////////////////////////////////////
				strFreeCode=freeinfo.getFreeCode();
				mFreeAmount=freeinfo.getFreeAmount();						//免还金额
				mFreeRate=freeinfo.getFreeRate();							//免还利息
				dtFreeDate=freeinfo.getFreeDate();							//免还日
				strFreeReason=freeinfo.getFreeReason();						//免还原因
				strConsignClientAccount=freeinfo.getConsignClientAccount();	//委托账户
				lStatusID=freeinfo.getStatusID();							//免还状态
				
				
				lInputUserID=freeinfo.getInputUserID();//录入人ID
				strInputUserName=freeinfo.getInputUserName();//录入人名称
				if(lInputUserID<=0)
				{
					lInputUserID = lUserID;
					strInputUserName = strUserName;
				}
				

				
				
				dtInputDate=freeinfo.getInputDate();//录入日期
				//--------------------------------------------------------------------------//
				strContractCode=freeinfo.getContractCode();			//合同编号
				dAmount =freeinfo.getLoanAmount();				//贷款金额
				nIntervalNum =freeinfo.getIntervalNum();			//期限
				dLoanRate=freeinfo.getInterestRate();				//贷款执行利率
				dBalance =freeinfo.getBalance();					//贷款余额，未还金额
				strLoanPurpose=freeinfo.getLoanPurpose();			//借款用途
				strClientName=freeinfo.getClientName();				//借款单位名称
				dtEndDate = freeinfo.getEndDate();					//到期日
				//lPlanID = freeinfo.getPlanVersionID();
				//---------------------------------------------------------------------------//
				strConsignClientName=freeinfo.getConsignClientName();//委托单位名称
				strLoanPayCode=freeinfo.getLoanPayCode();			//放款通知单编号
				dPayAmount=freeinfo.getLoanPayAmount();				//放款金额
				dPayBalance=freeinfo.getLoanPayBalance();			//放款余额
				mPayInterest=freeinfo.getLoanPayInterest();					//放款利息
				
				
			if ((dBalance <= 0) || (dPayBalance <= 0))
			{
		%>
				<SCRIPT language="JavaScript">
					if (confirm("此放款余额为零，无法申请免还！是否返回新增？"))
					{
						eval("history.back(-1)");
					}
				</SCRIPT>
		<%

		}
///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

%>
<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=60 width="730">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=35><B><B><%=strTitle%></B></B></TD></TR>
	<TR>
		<TD vAlign=top>
		<TABLE align=center border=0 height=60 width=700>
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
						<TD colspan=3>
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
				<TD colSpan=8 height=30> <hr></TD>
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
								<TD height=32 width=400></TD>
							</TR>
							<TR>
								<TD colSpan=2>借款单位：</TD>
								<TD colSpan=1></TD>
								<TD colspan=4>
									<INPUT class=box size=68 disabled name="strBorrowClientName" value="<%=DataFormat.formatString(strClientName)%>">
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
									<INPUT class=box size=68 disabled name="strConsignClientName" value="<%=DataFormat.formatString(strConsignClientName)%>">
								</TD>
							</TR>
							<TR>
								<TD colSpan=2>委托账号：</TD>
								<TD colSpan=1></TD>
								<TD>
									<INPUT class=box onfocus="nextfield='mFreeAmount'" name="strConsignClientAccount" size=18 value="<%=DataFormat.formatString(strConsignClientAccount)%>">
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
									<INPUT class=tar disabled name="nIntervalNum" size=10 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> 月
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
								<TD colSpan=2 align=right><font color="#000000">放款通知单余额：</font></TD>
								<TD><%=sessionMng.m_strCurrencySymbol%>
									<INPUT class=tar disabled name="dPayBalance" size=18 value="<%=DataFormat.formatListAmount(dPayBalance)%>"> 
								</TD>
							</TR>
							<TR>
								<TD colSpan=2><font color='#FF0000'>&nbsp;</font>免还金额：</TD>
								<TD colSpan=1>
									<DIV  align="left"><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
								<TD>
									<script language="javascript">
									<%if(mFreeAmount<0)mFreeAmount=0;%>
										createAmountCtrl("frm", "mFreeAmount", "<%=DataFormat.formatListAmount(mFreeAmount)%>", "dtFreeDate", null);
									</script>
								</TD>
								<TD colSpan=2 align=right><font color='#FF0000'>*</font>&nbsp;免还日：</TD>
								<TD>&nbsp;&nbsp;
									<fs_c:calendar 
						          	    name="dtFreeDate"
							          	value="" 
							          	properties="nextfield ='mFreeRate'" 
							          	size="18"/>
							       			          	  <script>
	          		$('#dtFreeDate').val('<%if ((dtToday != null)&&(dtFreeDate !=null)) {out.println(DataFormat.getDateString(dtFreeDate));} else {out.println(DataFormat.getDateString(dtToday));}%>');
	          	</script>
							 	</TD>
							</TR>
							<TR>
								<TD colSpan=2>免还利息：</TD>
								<TD colSpan=1><%=sessionMng.m_strCurrencySymbol%></TD>
								<TD>
									<script language="javascript">
									<%if(mFreeRate<0)mFreeRate=0;%>
										createAmountCtrl("frm", "mFreeRate", "<%=DataFormat.formatAmount(mFreeRate)%>", "strFreeReason", null);
									</script>
								</TD>
								<TD colSpan=2 align=right>免还编号：</TD>
								<TD>&nbsp;&nbsp;
									<INPUT class=box  name="strFreeCode" size=18 value="<%=DataFormat.formatString(strFreeCode)%>" disabled>
								</TD>
							</TR>
							<TR>
								<TD colSpan=3><font color='#FF0000'>*</font>&nbsp;免还原因：</TD>
								<TD colSpan=4>
										<%
										//log4j.info("reason :"+strFreeReason+"1");
										%>
									<TEXTAREA cols=60 name="strFreeReason" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onFocus="nextfield='submitfunction';" ><%=DataFormat.formatString(strFreeReason)%></TEXTAREA> 
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
				<TD height=2 width=1>&nbsp;</TD>
				<TD height=2 width=562><U>执行计划详细</U></TD>
				<TD colSpan=7 height=2></TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<tr> 
				<td width="1" height="2">&nbsp;</td>
				<td width="562" height="2">
					<input type="button" name="Submit4224" onclick="window.open('../query/q094-v.jsp?control=view&isYU=2&nTmpID=<%=freeinfo.getPlanVersionID()%>&isSM=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes')" value="执行计划" class="button">
				</td>
				<td colspan="7" height="2"> 
					<div align="right"></div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<tr>
				<td width="1" height="2">&nbsp;</td>
				<td colspan="8" height="2"> 
					<div align="right">
			<%
				if((lStatusID==OBConstant.LoanInstrStatus.SUBMIT||lStatusID==OBConstant.LoanInstrStatus.SAVE||lStatusID==0)
				&&(freeinfo.getInputUserID()== sessionMng.m_lUserID||freeinfo.getInputUserID()<=0))
				{
			%>
						<INPUT  class=button name="FreeSubmit" onclick="confirmSave(frm)" type="button" value="保存免还申请">
			<%	}

			%>
						<INPUT  class=button name="back"  type="button" value=" 返回 " onclick="backto()"> 
					</div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<%	if (lFreeApplyID > 0) 
	{
%>
			<tr >
				<td width="1">&nbsp;</td>
				<td  >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				录入人：<%=DataFormat.formatString(strInputUserName)%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				录入时间：<%if(dtInputDate != null){out.println(DataFormat.getDateString(dtInputDate));} else {out.println("");}%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				状态：
					<%if(lStatusID > 0){out.println(OBConstant.LoanInstrStatus.getName(lStatusID));} else {out.println("");}%>
				</td>
			</tr>
<%	}
		else
		{
%>
			<tr>
				<td width="1">&nbsp;</td>
				<td >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				录入人：<%=DataFormat.formatString(strUserName)%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				录入时间：<%out.println(DataFormat.getDateString(dtToday));%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	状态：
					<%out.println(OBConstant.LoanInstrStatus.getName(1));%>
				</td>
			</tr>

<%}%>
			<tr>
				<td colspan="10" height="2">&nbsp;</td>
			</tr>

		</table>
		</td>
	</tr>
</table>

<input type=hidden name="strAction" value="save">

<input type=hidden name="lContractID" value="<%=lContractID%>">
<input type=hidden name="lLoanPayID" value="<%=lLoanPayID%>">
<input type=hidden name="lFreeApplyID" value="<%=lFreeApplyID%>">



<P><BR></P>
</form>


<script language="javascript">
function printIt(url)
{
	window.open(url,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
</script>
<script language="javascript">
function backto()
{
	var act = "<%=strAction%>";	
		if(act=="edit")
                {
				eval("self.location='<%=Env.EBANK_URL%>/loan/query/q003-c.jsp'");	
				}
				else
				{
				eval("self.location='<%=Env.EBANK_URL%>/loan/freeapply/f001-v.jsp'");
				}
}

</script>

<script language="javascript">
function confirmSave(frm)
{
	//alert("1");
	if (<%=dPayBalance%> <= 0)
	{
		alert("此放款余额为零，无法申请免还！请返回新增！")
		backto();
		return true;
	}
	var strMsg = "";

	if (!InputValid(frm.strConsignClientAccount, 0, "int", 0, 0, 0,"委托账号")) 
	{
		return false;
	}
/*
	if (!checkAmount(frm.mFreeAmount,1,"免还金额")) 
	{
		return false;
	}
//*/
	if ((reverseFormatAmount1(frm.mFreeAmount.value)) > <%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(dPayBalance))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(dPayBalance))%>)
	{
		alert("免还金额应该小于等于放款余额！");
		frm.mFreeAmount.focus();
		return false;
	}
	if (!InputValid(frm.mFreeRate,0, "float",0,0,<%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))%>,"免还利息")) 
	{
		return false;
	}
	if ((reverseFormatAmount1(frm.mFreeRate.value)) > <%=DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))==""?"0":DataFormat.reverseFormatAmount(DataFormat.formatAmount(mPayInterest))%>)
	{
		alert("免还利息应该小于等于放款的利息！");
		frm.mFreeRate.focus();
		return false;
	}

	if (((reverseFormatAmount1(frm.mFreeRate.value)) <= 0 )&&((reverseFormatAmount1(frm.mFreeAmount.value)) <= 0))
	{
		alert("免还金额、免还利息至少免还其中一个，但不能都为 0 ！");
		frm.mFreeAmount.focus();
		return false;
	}


	if (!checkDate(frm.dtFreeDate,1,"免还日")) 
	{
		return false;
	}
	if (!InputValid(frm.strFreeReason,1, "string",1,1,100,"免还原因")) 
	{
		return false;
	}

	if (<%=lFreeApplyID%> >0)
	{	strMsg = "确认修改申请单？";
		if(confirm(strMsg))
		{
		frm.action="<%=Env.EBANK_URL%>/loan/freeapply/f004-c.jsp";
		frm.strAction.value="modify";
		showSending();
		frm.submit();
		return true;
		}
	}else{
	
		if (<%=freeinfo.getStatusID()%> == <%=LOANConstant.FreeApplyStatus.CHECK%>)
		strMsg = "此申请书已审核，是否确认再次提交并重新审核？";
		else strMsg = "是否继续保存？";
		if(confirm(strMsg))
		{
			frm.action="<%=Env.EBANK_URL%>/loan/freeapply/f004-c.jsp";
			showSending();
			frm.submit();
			return true;
		}
	
	}
}

</script>
<script language="javascript">
<%
	//如果状态是已审核，且登录人是录入人，才可以再次提交申请，否则不可以修改
	if((lStatusID==LOANConstant.FreeApplyStatus.CHECK && lInputUserID == lUserID)||(lStatusID <=0)||(lStatusID==LOANConstant.FreeApplyStatus.SUBMIT && lInputUserID == lUserID  && lCheckUserID == lUserID))
	{
%>
		firstFocus(frm.strConsignClientAccount);
		//setSubmitFunction("confirmSave(frm)");
<%
	}
	else
	{
%>
		frm.strConsignClientAccount.disabled=true;
		frm.strFreeReason.disabled=true;
		frm.mFreeRate.disabled=true;
		frm.mFreeAmount.disabled=true;
		frm.dtFreeDate.disabled=true;
		firstFocus(frm.back);
		//setSubmitFunction("backto()");
<%
	}
%>
setFormName("frm");

</script>
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
<%@ include file="/common/SignValidate.inc" %>
