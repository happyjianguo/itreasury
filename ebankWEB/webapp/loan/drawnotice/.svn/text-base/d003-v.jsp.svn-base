<%
/*
控制变量
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.ebank.obdrawnotice.dataentity.*,
				com.iss.itreasury.ebank.obdrawnotice.bizlogic.*"
				
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%

	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	long CurrencyID = sessionMng.m_lCurrencyID;
	String strTitle = "银团贷款提款通知单－提交";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
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
		//定义变量，获取请求参数

		DrawNoticeInfo LDNinfo = null;
		YTFormatInfo YTinfo = null;
////////////////////////////////////////////////////////////////////*/
////////////////////////////////////////////////////////////////////*/
		//定义变量，获取请求参数

		String strLoanClientName="";//贷款方名称 华能/大桥
		strLoanClientName=Env.getClientName();
		String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		int nback=0;
		String strDisabled = "";
		long lLoanType = -1;

		long lContractID=-1;		//合同标示
		String strContractCode="";	//合同编号
		Timestamp dtStartDate=null;//合同开始日期
		Timestamp dtEndDate=null;//结束日期
		double dAmount = 0;//贷款金额
		long nIntervalNum = -1;//贷款期限
		double dBalance = 0;//贷款余额
		double dUNPayAmount = 0;
		double dLoanRate=0;//贷款利率
		double dChargeRate=0;//手续费率
		String strLoanPurpose="";//借款用途
		String strBorrowClientName="";	//借款单位名称
		String strBorrowClientCode="";
		String strConsignClientName="";	//委托单位名称
		String strConsignClientAccount = "";//委托账户

		long lLoanDrawNoticeID = -1;//银团放款通知单ID
		String strLoanPayCode=null;	//放款通知单编号
		String[] strBankName = {"","","","","","",""};//
		//*
		double[] mBankLoanAmount = {0,0,0,0,0,0};//
		double[] mBankLoanRate = {0,0,0,0,0,0};//
		double[] mBankConsignAmount = {0,0,0,0,0,0};//
		double[] mBankTrustAmount = {0,0,0,0,0,0};//*/
		//*
		String[] strBankLoanAmount = {"","","","","","",""};//
		String[] strBankLoanRate = {"","","","","","",""};//
		String[] strBankConsignAmount = {"","","","","","",""};//
		String[] strBankTrustAmount = {"","","","","","",""};//*/

		double mThisDrawAmount = 0;//
		double mThisDrawTipAmount = 0;//
		double mThisTotalAmount = 0;//
		double[] mThisPayAmount = {0,0,0,0,0,0};//
		double[] mThisTipAmount = {0,0,0,0,0,0};//

		long lStatusID = -1;//状态
		long lInputUserID = -1;//录入人
		String strInputUserName="";//录入人名称
		long lCheckUserID=-1;//审核人
		String strCheckUserName="";//审核人名称
		Timestamp dtInputDate=null;//录入日期
		Timestamp dtToday = Env.getSystemDate();//新增日期

///////control/////////////////////////////////////////////
/*
		strTmp = request.getParameter("type");//放款单ID
		if( strTmp != null && strTmp.length() > 0 )
		{
			lLoanType = Long.parseLong(strTmp.trim());
		}
*/
		strTmp = (String)request.getAttribute("mThisDrawAmount");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				mThisDrawAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
			}
			catch(Exception e)
			{
				;
			}
		}



//////////////////////////////////////////////////////////////////////
		strTmp = request.getParameter("strAction");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strAction = strTmp.trim();
		}
		LDNinfo = (DrawNoticeInfo)request.getAttribute("LDNinfo");
		


///////////////view//////////////////////////////////////////////////

			lLoanDrawNoticeID = LDNinfo.getID();
			lContractID = LDNinfo.getContractID();

			
			if (strAction.equals("count") && (mThisDrawAmount <=0))
				{
					mThisDrawAmount = LDNinfo.getDrawAmount();
				}
			lInputUserID = LDNinfo.getInputUserID();//录入人
			strInputUserName = LDNinfo.getInputUserName();//录入人
			
			if(lInputUserID<=0)
			{
				lInputUserID = lUserID;
				strInputUserName = strUserName;
			}
			
			lStatusID = LDNinfo.getStatusID();//
			
			if (lContractID > 0)
			{
				Log.print("Conrtact------------");
				
				strContractCode=DataFormat.formatString(LDNinfo.getContractCode());			//合同编号
				
				dAmount =LDNinfo.getExamineAmount();					//贷款金额,批准金额
				nIntervalNum =LDNinfo.getIntervalNum();			//期限
				dLoanRate=LDNinfo.getInterestRate();				//贷款执行利率
				dChargeRate=LDNinfo.getAgentRate();//手续费率
				strBorrowClientName=DataFormat.formatString(LDNinfo.getClientName());	//借款单位名称
				strBorrowClientCode=DataFormat.formatString(LDNinfo.getClientCode());
				dtStartDate=LDNinfo.getLoanStart();//合同开始日期
				dtEndDate=LDNinfo.getLoanEnd();//结束日期
				dUNPayAmount = LDNinfo.getUnPayAmount();
				
				Log.print("未发放金额:"+DataFormat.formatAmount(dUNPayAmount));
				YTinfo = new YTFormatInfo();
				YTinfo = LDNinfo.getYTInfo();
				if(YTinfo != null)
				{
					strBankName=YTinfo.getBankName();
					strBankLoanAmount=YTinfo.getLoanAmount();
					strBankLoanRate=YTinfo.getLoanRate();
					strBankConsignAmount=YTinfo.getAssureAmount();
					strBankTrustAmount=YTinfo.getCreditAmount();
					for(int i=0;i<6;i++)
					{
						Log.print("---");
						if(strBankLoanAmount[i].length() > 0)
						{
							mBankLoanAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankLoanAmount[i]));
						}
						if(strBankLoanRate[i].length() > 0)
						{
							mBankLoanRate[i]=Double.parseDouble(strBankLoanRate[i]);
						}
						if(strBankConsignAmount[i].length() > 0)
						{
							mBankConsignAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankConsignAmount[i]));
						}
						if(strBankTrustAmount[i].length() > 0)
						{
							mBankTrustAmount[i]=Double.parseDouble(DataFormat.reverseFormatAmount(strBankTrustAmount[i]));
						}
					}
				}
			}
///////////////////////////////////////////////////////////////////////////////////////

			if (strAction.equals("count"))
			{
				//mThisDrawTipAmount = (dChargeRate*mThisDrawAmount)/100;
				//Log.print("本次提款总金额："+mThisTotalAmount);
				for(int k=0;k<6;k++)
				{
					mThisPayAmount[k] = (mThisDrawAmount*mBankLoanRate[k])/100;//
					if(k > 0)
					{
						mThisTipAmount[k] = (dChargeRate*mThisPayAmount[k])/1000;
						mThisDrawTipAmount += mThisTipAmount[k];
					}
				}
				mThisTotalAmount = mThisDrawAmount + mThisDrawTipAmount;
			}//*/
			Log.print("--------------Conrtact");
		if (dUNPayAmount <= 0)
		{
%>
			<SCRIPT language="JavaScript">
				if (confirm("未发放金额为零，无法再发放贷款！是否返回新增页面？"))
				{
					eval("self.location='d001-v.jsp'");
				}
			</SCRIPT>
<%
		}

///////////////////////////////////////////////////////////////////////////////////////////////////

%>


<form name="frm">
<TABLE border=0 class=top height=60 width="54%">
	<TBODY>
	<TR class="tableHeader">
		<TD class=FormTitle height=35><B><B><%=DataFormat.formatString(strTitle)%></B></B></TD></TR>
	<TR>
		<TD vAlign=top>
		<TABLE align=center border=0 height=60 width=819>
			<TBODY>
			<TR>
				<TD height=23 width=1>&nbsp;</TD>
				<TD colSpan=2 height=23>合同编号：<U><font color=blue><%=DataFormat.formatString(strContractCode)%></font></U></TD>
				<TD colSpan=6 height=23></TD>
			</TR>
			<TR>
				<TD height=24 width=1>&nbsp;</TD>
				<TD colSpan=8 height=24>
					<HR>
				</TD>
				<TD align=right height=24 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30><U>合同资料</U></TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=1></TD>
						<TD width=200></TD>
						<TD width=30></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=200></TD>
						<TD width=30></TD>
						<TD width=200></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>贷款单位：</TD>
						<TD></TD>
						<TD colspan=2><U><font color=blue><%=DataFormat.formatString(strBorrowClientName)%></font></U></TD>
						<TD align=right>客户编号：</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=18 value="<%=DataFormat.formatString(strBorrowClientCode)%>">
						</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>合同金额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar disabled name="dAmount1" size=18 value="<%=DataFormat.formatListAmount(dAmount)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD  align=right>期限：</TD>
						<TD>
							<INPUT class=tar disabled name="nIntervalNum" size=18 value="<%if(nIntervalNum>0){out.print(""+nIntervalNum);}else{out.print("");}%>"> 月
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>执行利率：</TD>
						<TD></TD>
						<TD>
							<INPUT class=tar disabled name="dLoanRate" size=18 value="<%=DataFormat.formatRate(dLoanRate)%>">&nbsp;%
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>代理费率：</TD>
						<TD>
							<INPUT class=tar disabled name="dChargeRate" size=18 value="<%=DataFormat.formatRate(dChargeRate)%>">&nbsp;‰
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>合同起始日期：</TD>
						<TD></TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=18 value="<%=DataFormat.getDateString(dtStartDate)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>合同结束日期：</TD>
						<TD>
							<INPUT class=tar disabled name="fInterestRate" size=18 value="<%=DataFormat.getDateString(dtEndDate)%>">
						</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
<%
		for(int i=0;i<6;i++)
		{
%>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
					<HR>
				</TD>
				<TD align=right height=30 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=30 width=1>&nbsp;</TD>
				<TD colSpan=8 height=30>
				<TABLE cellPadding=0 cellSpacing=0 width="100%">
					<TBODY>
					<TR>
						<TD width=1></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=20></TD>
						<TD width=200></TD>
						<TD width=300></TD>
					</TR>
<%
			if(i==0)
			{
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=8><U>贷款人资料</U></TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=2>牵头行/代理行：</TD>
						<TD align=right>&nbsp;</TD>
					</TR>
<%
			}
			else
			{
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD colSpan=2>参加行<%=i%>：</TD>
						<TD align=right height=30 width=1>&nbsp;</TD>
					</TR>
<%
			}
%>
					<TR>
						<TD>&nbsp;</TD>
						<TD>银行名称：</TD>
						<TD>&nbsp;</TD>
						<TD>
							<INPUT class=tar  name="strNameBank<%=i%>" size=18 value="<%=DataFormat.formatString(strBankName[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>承贷金额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankLoanAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankLoanAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD>承贷比例：</TD>
						<TD>
							<INPUT class=tar  name="mBankLoanRate<%=i%>" size=18 value="<%=mBankLoanRate[i] <=0?"":DataFormat.formatRate(mBankLoanRate[i])%>" readonly> %
						</TD>
						<TD>&nbsp;</TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
						<TD>担保金额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankConsignAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankConsignAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD align=right>信用金额：</TD>
						<TD><%=sessionMng.m_strCurrencySymbol%></TD>
						<TD>
							<INPUT class=tar  name="mBankTrustAmount<%=i%>" size=18 value="<%=DataFormat.formatListAmount(mBankTrustAmount[i])%>" readonly>
						</TD>
						<TD>&nbsp;</TD>
						<TD></TD>
						<TD></TD>
						<TD>&nbsp;</TD>
					</TR>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
<%
	}
%>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD width=1></TD>
				<TD colSpan=1><font color='#FF0000'>*</font>&nbsp;本次总提款金额:
				<%=sessionMng.m_strCurrencySymbol%>
					<script language="javascript">
						createAmountCtrl("frm", "mThisDrawAmount", "<%=DataFormat.formatListAmount(mThisDrawAmount)%>","",null);
					</script>
					<INPUT onfocus="nextfield=''" class=button type="button"  name="CountAmount" value=" 计 算 " onclick="confirmfrm(frm,1)">
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
		<TR>
			<TD align=center height=16 width="1%">
			<TD colSpan=8 align=center height=16 width="99%">
			<TABLE align=center border=0 class=ItemList height=20 width="99%">
				<TBODY>
				<TR align=center bgColor=#cccccc borderColor=#999999>
					<TD class=ItemTitle height=20 width="20%" colSpan=3>
						<DIV align=center>贷款人资料</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%" rowSpan=2>
						<DIV align=center><U><font color=blue>本次提款金额</font></U></DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%" rowSpan=2>
						<DIV align=center><U><font color=blue>本次应付代理费</font></U></DIV>
					</TD>
				</TR>
				<TR align=center bgColor=#cccccc borderColor=#999999>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>银行名称</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>承贷金额</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="20%">
						<DIV align=center>承贷比例(%)</DIV>
					</TD>
				</TR>
<%
	if(mThisDrawAmount > 0)
	{
		for(int j=0;j<6;j++)
		{
			if(mBankLoanRate[j] > 0)
			{
%>
				<TR align=center borderColor=#999999>
					<TD class=ItemBody><%=DataFormat.formatString(strBankName[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mBankLoanAmount[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatRate(mBankLoanRate[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mThisPayAmount[j])%></TD>
					<TD class=ItemBody><%=DataFormat.formatDisabledAmount(mThisTipAmount[j])%></TD>
				</TR>
<%
			}
		}
	}
	else
	{
%>
				<TR align=center borderColor=#999999>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
					<TD class=ItemBody>&nbsp;</TD>
				</TR>
<%
	}
%>
				<TR borderColor=#999999>
					<TD class=SearchBar colSpan=10 height=46>
					<TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar width="200%">
						<TBODY>
						<TR>
							<TD height=14 colSpan=5>
								<B><B>本次提款金额总计:</B></B> <%=sessionMng.m_strCurrencySymbol%>
								<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatDisabledAmount(mThisTotalAmount,0)%>" disabled >
							</TD>
						</TR>
						<TR>
							<TD height=14 colSpan=5>
							<B><B>本次应付代理费总计:</B></B> <%=sessionMng.m_strCurrencySymbol%>
							<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatDisabledAmount(mThisDrawTipAmount,0)%>" disabled >
							</TD>
						</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
				</TBODY>
			</TABLE>
				<HR>
			</TD>
		</TR>
		<TR borderColor=#999999>
			<TD colSpan=10 height=100 width=800>
			<TABLE border=0 cellPadding=0 cellSpacing=3>
				<TBODY>
				<TR>
					<TD colSpan=1></TD>
					<TD colSpan=1></TD>
					<TD colSpan=1></TD>
					<TD colspan=7 width=700 align=right></TD>
				</TR>
				<TR>
					<TD colSpan=1>&nbsp;</TD>
					<TD colSpan=1>其他附件：</TD>
					<TD colSpan=1>&nbsp;</TD>
					<TD colspan=7 align=right>
						<iframe src="../../attach/AttachFrame.jsp?lID=<%=lContractID%>&lTypeID=<%//=OBConstant.AttachParentType.LOAN%>&sCaption=链接附件&showOnly=<%=Constant.YesOrNo.NO%>" width=700 height="80" scrolling="Auto" frameborder="0" name="iFrame" >
						</iframe>
					</TD>
				</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<tr>
				<td width="1" height="2">&nbsp;</td>
				<td colspan="8" height="2"> 
					<div align="right">
					<%
						if(lLoanDrawNoticeID<0)
					%>
						<INPUT onfocus="nextfield=''" class=button name="SaveSubmit" onclick="confirmfrm(frm,2)" type="button" value=" 提交 " >
                    <%
						else{
						%>
						<INPUT onfocus="nextfield=''" class=button name="SaveSubmit" onclick="confirmfrm(frm,3)" type="button" value=" 提交 " >
						<%
						}
					%>
					<INPUT class=button name="back" onclick="backto(frm)" type="button" value=" 返回 " onKeydown="if(event.keyCode==13) backto(frm);"> 
					</div>
				</td>
				<td align="right" height="2" width="1">&nbsp;</td>
			</tr>
			<TR>
				<TD height=2 width=1>&nbsp;</TD>
				<TD colSpan=8 height=2>
					<HR>
				</TD>
				<TD align=right height=2 width=1>&nbsp;</TD>
			</TR>
			<TR>
				<TD colSpan=3>&nbsp;</TD>
				<TD colSpan=2>&nbsp;</TD>
				<TD colSpan=2>&nbsp;</TD>
				<TD colSpan=3>&nbsp;</TD>
			</TR>
			<tr>
				<td colspan="10" height="2">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<input type=hidden name="lContractID" value="<%=lContractID%>">

<INPUT type="hidden" name="lLoanDrawNoticeID" value="<%=lLoanDrawNoticeID%>">
<input type=hidden name="strAction" value="count">


<P><BR></P>
</form>


<script language="javascript">
function printIt(url)
{
	window.open(url,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}

</script>
<script language="javascript">
/*function backto(frm,nback)
{

	if(nback==1)
	{
		eval("history.back(-1)");
	}
	else if(nback==2)
	{
		eval("history.back(-1)");
	}
	else
	{
		eval("self.location='S001.jsp?control=view&type=<%=lLoanType%>'");
	}
}
*/
function backto(frm)
{
		eval("self.location='d001-v.jsp'");
}

</script>
<script language="javascript">
function check(frm)
{
	if(<%=dUNPayAmount%> == 0)
	{
		alert("该合同已放款完毕，不能再放款，请返回新增页面");
		frm.mThisDrawAmount.focus();
		return false;
	}
	if(reverseFormatAmount1(frm.mThisDrawAmount.value) > <%=dUNPayAmount%> )
	{
		alert("放款金额应该小于等于未发放金额:");//+<%=DataFormat.formatDisabledAmount(dUNPayAmount)%>);
		frm.mThisDrawAmount.focus();
		return false;
	}
	else
	{
		return true;
	}
}
</script>

<script language="javascript">
function confirmfrm(frm,nAction)
{
	if(nAction == 1)
	{
		if(!checkAmount(document.frm.mThisDrawAmount,1,"提款金额")) 	return (false);
		frm.action="d006-c.jsp";
		showSending();
		frm.submit();
		return true;
	}//计算
	if(nAction == 2)
	{
		if(!check(frm)) return false;
		if(!checkAmount(document.frm.mThisDrawAmount,1,"提款金额")) 	return (false);
		if(confirm("是否提交提款申请？"))
		{
			frm.action="d004-c.jsp";
			showSending();
			frm.submit();
			return true;
		}
	}//提交
	if(nAction == 3)
	{
		if(!check(frm)) return false;
		if(!checkAmount(document.frm.mThisDrawAmount,1,"提款金额")) 	return (false);
		if(confirm("是否更改提款申请？"))
		{
			frm.action="d009-c.jsp";
			showSending();
			frm.submit();
			return true;
		}
	}
	//修改
	else
	{
		return false;
	}
}

</script>
<script language="javascript">
		firstFocus(frm.mThisDrawAmount);
		//setSubmitFunction("confirmfrm(frm,2)");
		setFormName("frm");

</script>


<%
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
