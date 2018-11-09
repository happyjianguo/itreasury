<%--
 页面名称 ：l002-v.jsp
 页面功能 :  [贷款还款]--自营贷款,视图页面一
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>

<%@ page
	import="java.sql.*,javax.servlet.*,com.iss.itreasury.util.*,com.iss.itreasury.settlement.util.NameRef,com.iss.itreasury.loan.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,com.iss.itreasury.ebank.obsystem.bizlogic.*,com.iss.itreasury.ebank.obsystem.dataentity.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%/* 标题固定变量 */
			String strTitle = "[贷款还款]--自营贷款";

			%>
<%try {
				Log
						.print("*******进入页面--ebank/capital/loanrepayment/l002-v.jsp*******");

				/* 用户登录检测 */
				if (sessionMng.isLogin() == false) {
					OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
							"Gen_E002");
					out.flush();
					return;
				}

				/* 判断用户是否有权限 */
				if (sessionMng.hasRight(request) == false) {
					out.println(sessionMng.hasRight(request));
					OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
							"Gen_E003");
					out.flush();
					return;
				}

				//定义变量
				String strTemp = "";
				String strAction = "";
				String strReturn = "";
				//获取信息用于返回

				strTemp = (String) request.getAttribute("strAction");
				if (strTemp != null && strTemp.trim().length() > 0) {
					strAction = strTemp.trim();
				}

				strTemp = (String) request.getAttribute("strReturn");
				if (strTemp != null && strTemp.trim().length() > 0) {
					strReturn = strTemp.trim();
				}
				String isYT = "";
				strTemp = (String) request.getAttribute("isYT");
				if (strTemp != null && strTemp.trim().length() > 0) {
					isYT = strTemp.trim();
					if (isYT.equals("1")) {
						strTitle = "[贷款还款]--银团贷款";
					}
				}

				long lID = -1; // 指令序号
				String sPayerName = ""; // 付款方名称
				String sPayerAcctNo = ""; // 付款方账号 
				long lPayerAcctID = -1;//付款方账户ID
				double dCurrentBalance = 0.00; // 当前金额
				double dUsableBalance = 0.00; // 可用金额
				String sPayeeName = ""; // 收款方名称
				String sPayeeAcctNo = ""; // 收款方账号（即贷款账号）
				long lPayeeAcctID = -1; // 收款方账户ID（即贷款账户id）
				long lContractID = -1;//贷款合同id
				String sLoanContractNo = ""; // 贷款合同号
				Timestamp tsPayDate = null; // 贷款放款日期
				long lLoanNoteID = -1;
				String sLetOutCode = ""; // 放款通知单号
				double dBalance = 0.00;//贷款余额
				double dRate = 0.00;//放款通知单利率
				double dAmount = 0.00; // 本金金额
				Timestamp tsExecuteDate = null; // 执行日
				String sNote = "自营贷款清还"; // 汇款用途/摘要
				long lSubAccountID = -1;//子账户id

				String strOpenSystem = Env.getSystemDateString(
						sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);

				//如果从第二页面返回
				if (strAction.equals("toCancel")) {
					//获取页面一的信息,关于放大镜的信息获取需要进行修改
					strTemp = (String) request.getAttribute("lID");// 指令序号
					if (strTemp != null && strTemp.trim().length() > 0) {
						lID = Long.valueOf(strTemp).longValue();
					}

					strTemp = (String) request.getAttribute("sPayerName");// 付款方名称
					if (strTemp != null && strTemp.trim().length() > 0) {
						sPayerName = strTemp.trim();
					}

					strTemp = (String) request.getAttribute("sPayerAcctNoCtrl");// 付款方账号 
					if (strTemp != null && strTemp.trim().length() > 0) {
						sPayerAcctNo = strTemp.trim();
					}

					strTemp = (String) request.getAttribute("lPayerAcctID");// 付款方账户id
					if (strTemp != null && strTemp.trim().length() > 0) {
						lPayerAcctID = Long.valueOf(strTemp).longValue();
					}

					strTemp = (String) request.getAttribute("dCurrentBalance");//当前金额
					if (strTemp != null && strTemp.trim().length() > 0) {
						dCurrentBalance = DataFormat.parseNumber(strTemp);
					}

					strTemp = (String) request.getAttribute("dUsableBalance");//可用金额
					if (strTemp != null && strTemp.trim().length() > 0) {
						dUsableBalance = DataFormat.parseNumber(strTemp);
					}

					strTemp = (String) request.getAttribute("sPayeeName");//收款方名称
					if (strTemp != null && strTemp.trim().length() > 0) {
						sPayeeName = strTemp.trim();
					}

					strTemp = (String) request.getAttribute("sPayeeAcctNo");//收款方账号（即贷款账号） 
					if (strTemp != null && strTemp.trim().length() > 0) {
						sPayeeAcctNo = strTemp.trim();
					}

					strTemp = (String) request.getAttribute("lPayeeAcctID");//收款方账户ID（即贷款账户id）
					if (strTemp != null && strTemp.trim().length() > 0) {
						lPayeeAcctID = Long.valueOf(strTemp).longValue();
					}

					strTemp = (String) request.getAttribute("lContractID");//贷款合同id
					if (strTemp != null && strTemp.trim().length() > 0) {
						lContractID = Long.valueOf(strTemp).longValue();
						sLoanContractNo = NameRef
								.getContractNoByID(lContractID);
					}

					strTemp = (String) request.getAttribute("lContractIDCtrl");//贷款合同编号
					if (strTemp != null && strTemp.trim().length() > 0) {
						sLoanContractNo = strTemp.trim();
					}

					strTemp = (String) request.getAttribute("lLoanNoteIDCtrl");//贷款放款日期
					if (strTemp != null && strTemp.trim().length() > 0) {
						tsPayDate = DataFormat.getDateTime(strTemp);
					}

					strTemp = (String) request.getAttribute("lLoanNoteID");// 放款通知单id
					if (strTemp != null && strTemp.trim().length() > 0) {
						lLoanNoteID = Long.valueOf(strTemp).longValue();
						sLetOutCode = NameRef.getPayFormNoByID(lLoanNoteID);
					}

					strTemp = (String) request.getAttribute("dBalance");//贷款余额
					if (strTemp != null && strTemp.trim().length() > 0) {
						dBalance = DataFormat.parseNumber(strTemp);
					}

					strTemp = (String) request.getAttribute("dAmount");//本金金额
					if (strTemp != null && strTemp.trim().length() > 0) {
						dAmount = DataFormat.parseNumber(strTemp);
					}

					strTemp = (String) request.getAttribute("tsExecuteDate");//执行日
					if (strTemp != null && strTemp.trim().length() > 0) {
						tsExecuteDate = DataFormat.getDateTime(strTemp);
					}

					strTemp = (String) request.getAttribute("sNote");// 汇款用途/摘要
					if (strTemp != null && strTemp.trim().length() > 0) {
						sNote = strTemp.trim();
					}
				} else {
					Log.print("=======获取查询结果");
					FinanceInfo financeInfo = null;
					financeInfo = (FinanceInfo) request
							.getAttribute("resultInfo");

					if (financeInfo != null) {
						Log.print("=======not null");
						lID = financeInfo.getID();
						lPayerAcctID = financeInfo.getPayerAcctID();//付款方账户id
						sPayerName = financeInfo.getPayerName(); // 付款方名称
						sPayerAcctNo = financeInfo.getPayerAcctNo(); // 付款方账号 
						dCurrentBalance = financeInfo.getCurrentBalance(); // 当前金额
						dUsableBalance = financeInfo.getUsableBalance(); // 可用金额
						sPayeeName = financeInfo.getPayeeName(); // 收款方名称
						sPayeeAcctNo = financeInfo.getPayeeAcctNo(); // 收款方账号（即贷款账号）
						lPayeeAcctID = financeInfo.getPayeeAcctID(); // 收款方账户ID（即贷款账户id）
						lContractID = financeInfo.getContractID();//贷款合同ID
						sLoanContractNo = financeInfo.getLoanContractNo(); // 贷款合同编号
						tsPayDate = financeInfo.getPayDate(); // 贷款放款日期
						lLoanNoteID = financeInfo.getLoanNoteID();//放款通知单id
						sLetOutCode = financeInfo.getLetOutCode(); // 放款通知单号
						dBalance = financeInfo.getBalance();//贷款余额
						dRate = financeInfo.getRate();//放款通知单利率
						dAmount = financeInfo.getAmount(); // 本金金额
						tsExecuteDate = financeInfo.getExecuteDate(); // 执行日
						sNote = financeInfo.getNote(); // 汇款用途/摘要

					}
				}

				//初始化贷款账号
				PayerOrPayeeInfo payerOrPayeeInfo = (PayerOrPayeeInfo) request
						.getAttribute("payerOrPayeeInfo");
				if (payerOrPayeeInfo != null) {
					lPayeeAcctID = payerOrPayeeInfo.getAccountID();
					sPayeeAcctNo = payerOrPayeeInfo.getAccountNo();
					sPayeeName = payerOrPayeeInfo.getAccountName();
				}

				/* 实现菜单控制 */
				long lShowMenu = OBConstant.ShowMenu.YES;
				String strMenu = (String) request.getAttribute("menu");
				if ((strMenu != null) && (strMenu.equals("hidden"))) {
					lShowMenu = OBConstant.ShowMenu.NO;
				}

				//显示文件头
				OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);

				%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="webob/js/DropDownData.js"></script>

<safety:resources />

<FORM name=frm method=post><input type="Hidden" name="lID"
	value="<%=lID%>"> <!--以下用于查询返回!--> <input type="hidden"
	name="strAction" value="<%=strAction%>"> <input type="hidden"
	name="strReturn" value="<%=strReturn%>"> <input type="hidden"
	name="menu"
	value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>"> <!--以上用于查询返回!-->

<TABLE cellSpacing=0 cellPadding=0 border=0 width="730" class="top">
	<TR class="tableHeader">
		<!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
		<TD colSpan=9 height=25 class="FormTitle"><FONT size=3>付款方资料</FONT></TD>
		<!--TD vAlign=top align=right width=1 height=25>
            <DIV align=right></DIV></TD-->
	</TR>

	<TR class="MsoNormal">
		<TD colSpan=8 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal width=130 height=25>付款账户名称：</TD>
		<TD class=MsoNormal width=430 colSpan=7 height=25><INPUT class=rebox
			readOnly size=30 name=sPayerName value="<%=sPayeeName%>"></TD>
		<TD width=1 height=25></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=8 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25><input type="Hidden" name="lPayerAcctID"
			value="<%=lPayerAcctID%>"></TD>

		<%//付款方账号放大镜
				Magnifier.createPayerAccountNoCtrl(out, sessionMng.m_lUserID,
						sessionMng.m_lCurrencyID, lID, sessionMng.m_lClientID,
						"lPayerAcctID", "dCurrentBalance", "dUsableBalance",
						"frm", sPayerAcctNo, "sPayerAcctNo", "付款方账号",
						" width=\"130\" height=\"25\" class=\"MsoNormal\"",
						" width=\"590\" height=\"25\" colSpan=\"4 \"");

				%>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=4 height=25></TD>
		<TD width=146 height=25>当前余额：</TD>
		<TD class=MsoNormal height=25><%=sessionMng.m_strCurrencySymbol%><INPUT
			class=amountbox readOnly size=16 name=dCurrentBalance
			value="<%=DataFormat.formatDisabledAmount(dCurrentBalance)%>"></TD>
		<TD class=leftwhiteline><font class=MsoNormal>可用余额： </font> <%=sessionMng.m_strCurrencySymbol%><input
			class=amountbox readonly size=16 name=dUsableBalance
			value="<%=DataFormat.formatDisabledAmount(dUsableBalance)%>"></TD>
		<TD width=15 colSpan=4></TD>
	</TR>
</table>
<br>
<br>


<TABLE cellSpacing=0 cellPadding=0 width=730 border=0 class="top">
	<TR class="tableHeader">
		<!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
		<TD colSpan=4 class="FormTitle">
		<DIV align=left><FONT size=3>贷款资料</FONT></DIV>
		</TD>
		<!--TD width=15 colSpan=4></TD-->
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal width=131 height=25>贷款账号：</TD>
		<TD class=MsoNormal width=549 height=25><input type="Hidden"
			name="lPayeeAcctID" value="<%=lPayeeAcctID%>"> <input type="Hidden"
			name="sPayeeName" value="<%=sPayeeName%>"> <INPUT class=rebox
			readOnly size=22 name=sPayeeAcctNo value="<%=sPayeeAcctNo%>"></TD>
		<TD width=1 height=25></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25><input type="Hidden" name="strClientCtrl"
			value="<%=sessionMng.m_lClientID%>"></TD>

		<%String[] strNextControls = { "lLoanNoteIDCtrl" };
				long lTempType = SETTConstant.TransactionType.TRUSTLOANRECEIVE;
				if (isYT.equals("1")) {
					lTempType = SETTConstant.TransactionType.BANKGROUPLOANRECEIVE;
				}
				Magnifier.createContractCtrl(out, sessionMng.m_lOfficeID,
						sessionMng.m_lCurrencyID, "frm", "lContractID", "合同号",
						sessionMng.m_lClientID, lContractID, sLoanContractNo,
						lTempType, 2, "strClientCtrl", "", "", strNextControls);

				%>
		<TD width=131></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25><input type="hidden" name="strRtnContractIDCtrl"></TD>
		<%String[] strNextControls1 = { "dAmount" };
				lTempType = 1;
				long lType = 3;

				if (isYT.equals("1")) {
					lTempType = 4;
					lType = 5;
				}
				// Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lLoanNoteID","放款日期",lContractID,lLoanNoteID,tsPayDate,sLetOutCode,1,3,"lContractID","","",strNextControls1,"","","","","","dBalance");

				Magnifier.createPayFormCtrl(out, sessionMng.m_lOfficeID,
						sessionMng.m_lCurrencyID, sessionMng.m_lClientID,
						"frm", "lLoanNoteID", "放款日期", lContractID, lLoanNoteID,
						lPayeeAcctID, tsPayDate, sLetOutCode, lTempType, lType,
						"lContractID", "", "", strNextControls1, "",
						"strRtnContractIDCtrl", "", "", "", "dBalance");

				%>
		<TD></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<tr class="MsoNormal">
		<td></td>
		<td>利率：</td>
		<td colspan="3"><input size="8" type="text"
			value="<%=DataFormat.formatRate(dRate)%>" name="lLoanNoteIDrate"
			disabled>%</td>
	</tr>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal width=131 height=25>贷款余额：</TD>
		<TD class=MsoNormal width=431 height=25>
			<!--  <INPUT type="hidden" size=16 name=dBalance value="<%=DataFormat.formatDisabledAmount(dBalance)%>"
				onchange="document.all.dSeeBalance.value=formatAmount(this.value)"> -->
				 <%=sessionMng.m_strCurrencySymbol%>
				 <INPUT class=amountbox readOnly size=16 name=dBalance value="<%=DataFormat.formatDisabledAmount(dBalance)%>"></TD>
		<TD width=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=4 height=1></TD>
	</TR>
</TABLE>
<br>
<br>
<TABLE cellSpacing=0 cellPadding=0 width=730 border=0 class="top">

	<TR class="tableHeader">
		<!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
		<TD height=25 colSpan=5 class="FormTitle"><FONT size=3>划款资料</FONT></TD>
		<!--TD vAlign=top align=right width=27 height=25> <DIV align=right></DIV></TD>
			<TD vAlign=top align=right width=601 height=25> <DIV align=right></DIV></TD>
			<TD vAlign=top align=right width=5 height=25> <DIV align=right></DIV></TD-->
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=5 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal height=25>本金金额：</TD>
		<TD class=MsoNormal width=27 height=25>
		<DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV>
		</TD>
		<TD class="MsoNormal" height=25>
			<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dAmount',"<%=dAmount%>",'tsExecuteDate','sChineseAmount',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT></TD>
		<TD vAlign=top align=right width=5 height=25></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=5 height=1></TD>
	</TR>

	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal colSpan=2 height=25>大写金额(<%=OBConstant.CurrencyType
								.getName(sessionMng.m_lCurrencyID)%>)：</TD>
		<TD class=MsoNormal width=601 height=25><input type="text"
			class="rebox" name="sChineseAmount" size="30"
			value="<%=ChineseCurrency.showChinese(String.valueOf(dAmount),sessionMng.m_lCurrencyID)%>"
			readonly></TD>
		<TD width=5 height=25></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=5 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal colSpan=2 height=25>执行日：</TD>
		<TD class=MsoNormal width=601 height=25>
		 <fs_c:calendar 
			          	    name="tsExecuteDate"
				          	value="" 
				          	properties="nextfield ='sNote'" 
				          	size="18"/>
				     <script>
	          		$('#tsExecuteDate').val('<%=tsExecuteDate == null ? DataFormat.getDateString(Env.getSystemDate(1,1)):DataFormat.getDateString(tsExecuteDate)%>');
	          	</script>
		</TD>
		<TD width=5 height=25></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=5 height=1></TD>
	</TR>
	<TR class="MsoNormal">
		<TD width=5 height=25></TD>
		<TD class=MsoNormal colSpan=2 height=25>汇款用途：</TD>
		<TD class=MsoNormal noWrap width=601 height=25><textarea name="sNote"
			cols="65" rows="2"
			onkeypress="if (this.value.length>50) event.keyCode=0;"
			onchange="if(this.value.length>50) this.value=this.value.substr(0,50)"
			onfocus="nextfield = 'submitfunction'"><%=sNote == "" || sNote == null ? sessionMng.m_strClientShortName
								: DataFormat.formatString(sNote)%></textarea></TD>
		<TD width=5></TD>
	</TR>
	<TR class="MsoNormal">
		<TD colSpan=5 height=1></TD>
	</TR>
</TABLE>

<br>
<TABLE cellSpacing=0 cellPadding=0 width=730 border=0>
	<TR>
		<TD width=445>
		<DIV align=right></DIV>
		</TD>
		<TD width=63>
		<DIV align=right></DIV>
		</TD>
		<TD width=62>
		<div align="right"><input type="hidden" name="isYT" value="<%=isYT%>">
		<input type="button" name="GoNext" value=" 继 续 " class=button
			onfocus="nextfield='submitfunction';" onClick="goNext();"></div>
		</TD>
	</TR>
</TABLE>
<p>&nbsp;</p>
<p>&nbsp;</p>
</form>
<script language="javascript">
function changeFormat(txt)
{
   //alert(1);
   var num = txt.value;
   txt.value = formatAmount(num);
}



function validate()
{  
   //付款方账号
   if(frm.sPayerAcctNoCtrl.value=="")
   {
     alert("请选择付款方账号！");
	 frm.sPayerAcctNoCtrl.focus();
	 return false;
   }
   
   //合同号
   if(frm.lContractID.value=="" || frm.lContractID.value == "-1")
   {
     alert("请选择合同编号！");
	 frm.lContractIDCtrl.focus();
	 return false;
   
   }

   //放款通知单
  if(frm.lLoanNoteIDCtrl.value=="" || frm.lLoanNoteID.value=="-1")
   {
      alert("请选择放款日期！");
	  frm.lLoanNoteIDCtrl.focus();
	  return false;
   }
  
   //本金金额
   if(!checkAmount(frm.dAmount, 1, "本金金额"))
   {
	  return false;
   }
   
   //执行日
    if (!checkInterestExecuteDate(frm.tsExecuteDate,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
	{
		return false;
	}
	
	if (frm.tsExecuteDate.value < "<%=strOpenSystem%>")
	{
		alert("执行日不能晚于开机日！");
		return false;
	}
		/* 汇款用途 */
		if (!InputValid(frm.sNote, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}		
	
	//业务校验
	//校验合同号于放款通知单合同号是否一致
	if(frm.lContractID.value != frm.strRtnContractIDCtrl.value)
	{
	   alert("放款通知单与合同号不匹配！");
	   frm.lLoanNoteIDCtrl.focus();
	   return false;
	}
	
	
	
	
	var dBalance1 = parseFloat(reverseFormatAmount(frm.dAmount.value)) -
				   parseFloat(reverseFormatAmount(frm.dUsableBalance.value)) ;
	
	 if(dBalance1 > 0)
	{
		alert("本金金额不能超过可用余额！");
	   frm.dAmount.focus();	   
	   return false;
	}
	
	
	var dBalance2 = parseFloat(reverseFormatAmount(frm.dAmount.value)) -
				   parseFloat(reverseFormatAmount(frm.dBalance.value)) ;
				   
	if(dBalance2 > 0)
	{
	   alert("本金金额不能超过贷款余额！");
	   frm.dAmount.focus();
	   return false;
	}			   
   
    
   
      return true;

}

function goNext()
{     

	 if(!validate())
	 {
	   return;
	 }
	
	
    
	 if(confirm("是否继续？"))
	 {
	  frm.action="l003-c.jsp"
	  showSending();
	  frm.submit();
	  }

}

firstFocus(frm.sPayerAcctNoCtrl);
//setSubmitFunction("goNext()");
setFormName("frm");	 
</script>
<%//显示文件尾
				OBHtml.showOBHomeEnd(out);
			} catch (IException ie) {
				OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
						1);
				return;
			}

		%>
<%@ include file="/common/SignValidate.inc" %>