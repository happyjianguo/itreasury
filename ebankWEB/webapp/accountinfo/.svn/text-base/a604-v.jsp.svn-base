<%--
 页面名称 ：v076.jsp
 页面功能 : 银团贷款收回——复核/取消复核显示页面
 作    者 ：xrli	
 日    期 ：2004年05月23日
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
	
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<safety:resources />
<%
 try
 {
	Log.print("*******进入页面--settlement/tran/loan/view/v076.jsp*******");
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

		OBHtml.showOBHomeHead(out,sessionMng,Env.getClientName(),Constant.YesOrNo.NO);

//页面辅助变量
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	String strPreSaveResult = null;

//定义业务变量
	 long lID = -1;
	 long lOfficeID = -1;
	 long lCurrencyID = -1;
	 String strTransNo = ""; 
	 long lTransactionTypeID = -1;
	 long lClientID = -1;
	 long lDepositAccountID = -1;
	 long lBankID = -1;
	 long lLoanAccountID = -1;
	 long lLoanContractID = -1;
	 long lLoanNoteID = -1;
	  String strStartDate = "";//起始日期
	 long lPreFormID = -1;
	 double dAmount = 0.0;
	 java.sql.Timestamp tsInterestStart = null;
	 java.sql.Timestamp tsExecute = null;
	 long lAbstractID = -1;
	 String strAbstract = "";
	 String strCheckAbstract = "";
	 long lPayInterestAccountID = -1;
	 long lInterestBankID = -1;
	 long lReceiveInterestAccountID = -1;
	 long lPaySuretyAccountID = -1;
	 long lSuretyBankID = -1;
	 long lReceiveSuretyAccountID = -1;
	 double dInterest = 0.0;
	 double dInterestReceiveAble = 0.0;
	 double dInterestIncome = 0.0;
	 double dInterestTax = 0.0;
	 double dCompoundInterest = 0.0;
	 double dOverDueInterest = 0.0;
	 double dSuretyFee = 0.0;
	 String strAdjustInterestReason = ""; 
	 double dAdjustInterest = 0;
	 double dAheadRepayInterest = 0;
	 long lIsRemitInterest = -1;
	 long lIsRemitCompoundInterest = -1;
	 long lIsRemitOverDueInterest = -1;
	 long lIsRemitSuretyFee = -1;
	 long lCapitalAndInterstDealway = -1;
	 double dRealInterest = 0.0;
	 double dRealInterestReceiveAble = 0.0;
	 double dRealInterestIncome = 0.0;
	 double dRealInterestTax = 0.0;
	 double dRealCompoundInterest = 0.0;
	 double dRealOverDueInterest = 0.0;
	 double dRealSuretyFee = 0.0;
	 java.sql.Timestamp tsInput = null;
	 long lInputUserID = -1;
	 long lCheckUserID = -1; 
	 long lStatusID = -1;
	 java.sql.Timestamp tsModify = null;
	long lOrderByCode = Sett_TransRepaymentLoanDAO.ORDER_TRANS_NO;
	boolean isDesc = true;

	 
	if (request.getAttribute("strActionResult") != null)
	{
		  strActionResult = (String)request.getAttribute("strActionResult");
	}
	if (request.getAttribute("strAction") != null)
	{
		  strAction = (String)request.getAttribute("strAction");
	}
	
	
	//网银财务指令接收
		String strOBInstr = (String)request.getAttribute("OBInstr");
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	//从Request中获得参数
	TransRepaymentLoanInfo transRepaymentLoanInfo = null;
	transRepaymentLoanInfo = (TransRepaymentLoanInfo)request.getAttribute("searchResults");
	
	if(transRepaymentLoanInfo != null)
	{
	  lID = transRepaymentLoanInfo.getID();
	  Log.print("lID ："+lID);
	  lOfficeID = transRepaymentLoanInfo.getOfficeID();
	  Log.print("lOfficeID ："+lOfficeID);
	  lCurrencyID = transRepaymentLoanInfo.getCurrencyID();
	  Log.print("lCurrencyID ："+lCurrencyID);
	  strTransNo = transRepaymentLoanInfo.getTransNo();
	  Log.print("strTransNo ："+strTransNo);
	  lTransactionTypeID = transRepaymentLoanInfo.getTransactionTypeID();
	  Log.print("lTransactionTypeID ："+lTransactionTypeID);
	  lClientID = transRepaymentLoanInfo.getClientID();
	  Log.print("lClientID ："+lClientID);
	  lDepositAccountID = transRepaymentLoanInfo.getDepositAccountID();
	  Log.print("lDepositAccountID ："+lDepositAccountID);
	  lBankID = transRepaymentLoanInfo.getBankID();
	  Log.print("lBankID ："+lBankID);
	  lLoanAccountID = transRepaymentLoanInfo.getLoanAccountID();
	  Log.print("lLoanAccountID ："+lLoanAccountID);
	  lLoanContractID = transRepaymentLoanInfo.getLoanContractID();
	   Log.print("lLoanContractID ："+lLoanContractID);
	  lLoanNoteID = transRepaymentLoanInfo.getLoanNoteID();
	  Log.print("lLoanNoteID ："+lLoanNoteID);
	  lPreFormID = transRepaymentLoanInfo.getPreFormID();
	  Log.print("lPreFormID ："+lPreFormID);
	  dAmount = transRepaymentLoanInfo.getAmount();
	  Log.print("dAmount ："+dAmount);
	  tsInterestStart = transRepaymentLoanInfo.getInterestStart();
	  Log.print("tsInterestStart ："+tsInterestStart);
	  tsExecute = transRepaymentLoanInfo.getExecute();
	  Log.print("tsExecute ："+tsExecute);
	  lAbstractID = transRepaymentLoanInfo.getAbstractID();
	  Log.print("lAbstractID ："+lAbstractID);
	  strAbstract = transRepaymentLoanInfo.getAbstract();
	  Log.print("strAbstract ："+strAbstract);
	  strCheckAbstract = transRepaymentLoanInfo.getCheckAbstract();
	  Log.print("strCheckAbstract ："+strCheckAbstract);
	  lPayInterestAccountID = transRepaymentLoanInfo.getPayInterestAccountID();
	  Log.print("lPayInterestAccountID ："+lPayInterestAccountID);
	  lInterestBankID = transRepaymentLoanInfo.getInterestBankID();
	  Log.print("lInterestBankID ："+lInterestBankID);
	  lReceiveInterestAccountID = transRepaymentLoanInfo.getReceiveInterestAccountID();
	  Log.print("lReceiveInterestAccountID ："+lReceiveInterestAccountID);
	  lPaySuretyAccountID = transRepaymentLoanInfo.getPaySuretyAccountID();
	  Log.print("lPaySuretyAccountID ："+lPaySuretyAccountID);
	  lSuretyBankID = transRepaymentLoanInfo.getSuretyBankID();
	  Log.print("lSuretyBankID ："+lSuretyBankID);
	  lReceiveSuretyAccountID = transRepaymentLoanInfo.getReceiveSuretyAccountID();
	  Log.print("lReceiveSuretyAccountID ："+lReceiveSuretyAccountID);
	  dInterest = transRepaymentLoanInfo.getInterest();
	  Log.print("dInterest ："+dInterest);
	  dInterestReceiveAble = transRepaymentLoanInfo.getInterestReceiveAble();
	  Log.print("dInterestReceiveAble ："+dInterestReceiveAble);
	  
	  dInterestIncome = transRepaymentLoanInfo.getInterestIncome();
	  Log.print("dInterestIncome ："+dInterestIncome);
	  dInterestTax = transRepaymentLoanInfo.getInterestTax();
	  Log.print("dInterestTax ："+dInterestTax);
	  dCompoundInterest = transRepaymentLoanInfo.getCompoundInterest();
	   Log.print("dCompoundInterest ："+dCompoundInterest);
	  dOverDueInterest = transRepaymentLoanInfo.getOverDueInterest();
	  Log.print("dOverDueInterest ："+dOverDueInterest);
	  dSuretyFee = transRepaymentLoanInfo.getSuretyFee();
	  Log.print("dSuretyFee ："+dSuretyFee);
	  strAdjustInterestReason = transRepaymentLoanInfo.getAdjustInterestReason();
	  Log.print("strAdjustInterestReason ："+strAdjustInterestReason);
	  dAdjustInterest = transRepaymentLoanInfo.getAdjustInterest();
	  Log.print("dAdjustInterest ："+dAdjustInterest);
	  dAheadRepayInterest = transRepaymentLoanInfo.getAheadRepayInterest();
	  Log.print("dAheadRepayInterest ："+dAheadRepayInterest);
	  lIsRemitInterest = transRepaymentLoanInfo.getIsRemitInterest();
	  Log.print("lIsRemitInterest ："+lIsRemitInterest);
	  lIsRemitCompoundInterest = transRepaymentLoanInfo.getIsRemitCompoundInterest();
	  Log.print("lIsRemitCompoundInterest ："+lIsRemitCompoundInterest);
	  lIsRemitOverDueInterest = transRepaymentLoanInfo.getIsRemitOverDueInterest();
	  Log.print("lIsRemitOverDueInterest ："+lIsRemitOverDueInterest);
	  lIsRemitSuretyFee = transRepaymentLoanInfo.getIsRemitSuretyFee();
	  Log.print("lIsRemitSuretyFee ："+lIsRemitSuretyFee);
	  lCapitalAndInterstDealway = transRepaymentLoanInfo.getCapitalAndInterstDealway();
	  Log.print("lCapitalAndInterstDealway ："+lCapitalAndInterstDealway);
	  dRealInterestReceiveAble = transRepaymentLoanInfo.getRealInterestReceiveAble();
	   Log.print("RealInterestReceiveAble ："+dRealInterestReceiveAble);
	  dRealInterestIncome = transRepaymentLoanInfo.getRealInterestIncome();
	  Log.print("dRealInterestIncome ："+dRealInterestIncome);
	  dRealInterest = transRepaymentLoanInfo.getRealInterest();
	  Log.print("dRealInterest ："+dRealInterest);
	  dRealInterestTax = transRepaymentLoanInfo.getRealInterestTax();
	  Log.print("dRealInterestTax ："+dRealInterestTax);
	  dRealCompoundInterest = transRepaymentLoanInfo.getRealCompoundInterest();
	  Log.print("dRealCompoundInterest ："+dRealCompoundInterest);
	  dRealOverDueInterest = transRepaymentLoanInfo.getRealOverDueInterest();
	  Log.print("dRealOverDueInterest ："+dRealOverDueInterest);
	  dRealSuretyFee = transRepaymentLoanInfo.getRealSuretyFee();
	  Log.print("dRealSuretyFee ："+dRealSuretyFee);
	  tsInput = transRepaymentLoanInfo.getInput();
	  Log.print("tsInput ："+tsInput);
	  lInputUserID = transRepaymentLoanInfo.getInputUserID();
	  Log.print("lInputUserID ："+lInputUserID);
	  lCheckUserID = transRepaymentLoanInfo.getCheckUserID();
	  Log.print("lCheckUserID ："+lCheckUserID);
	  lStatusID = transRepaymentLoanInfo.getStatusID();
	  Log.print("lStatusID ："+lStatusID);
	  tsModify = transRepaymentLoanInfo.getModify();
	  Log.print("tsModify ："+tsModify);
	 }
	 
		
	   //根据放款通知单查询起始日期
	    LoanPayFormDetailInfo tempLoanPayFormDetailInfo = new LoanPayFormDetailInfo();
		Sett_TransGrantLoanDAO tempSett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
		
		tempLoanPayFormDetailInfo.setLoadNoteID(lLoanNoteID);
		java.sql.Timestamp tsTemp = null;
		tsTemp = (tempSett_TransGrantLoanDAO.findPayFormDetailByCondition(tempLoanPayFormDetailInfo)).getStart();
		strStartDate = DataFormat.formatDate(tsTemp);
		
		
  	//返回链接查找
	
			
			String strTemp = null;
			strTemp = (String)request.getAttribute("lOrderByCode");
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lOrderByCode = Long.valueOf(strTemp).longValue();
			}
			
	
			strTemp = (String)request.getAttribute("isDesc");
			if(strTemp != null && strTemp.length()>0)
			{
				isDesc = Boolean.valueOf(strTemp).booleanValue();
			}			
%>
 <form name="frmv076" action="../control/c014.jsp" method="post">
<TABLE border=0 class=top height=290 width="99%">
  <TBODY> 
    <TR class="tableHeader"> 
      <TD class=FormTitle height=2 width="100%">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
  <input name="strAction"  type="hidden">
   <input name="strSuccessPageURL"  type="hidden" value="<%
			if(lStatusID == SETTConstant.TransactionStatus.SAVE)
			{out.print("../view/v075.jsp");
			}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
			{out.print("../view/v077.jsp");}%>">
	<input name="strFailPageURL"  type="hidden" value="../view/v076.jsp">
	<input name="lID"  type="hidden" value="<%=lID%>">
	<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
	<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
	<input name="tsModify"  type="hidden" value="<%=tsModify%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
	<input name="isDesc" type="Hidden" value="<%=isDesc%>"><B>银团贷款收回</B></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD colSpan=2 height=20><U>贷款方还款详细资料</U> </TD>
              <TD height=20 width="23%">&nbsp;</TD>
              <TD height=20 width="16%">&nbsp;</TD>
              <TD height=20 width="37%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <TD colSpan=2 height=20>还款客户编号：</TD>
              <TD height=20 width="23%"><INPUT class=box  
            maxLength=8 name="lClientNo" size=10 value="<%=NameRef.getClientCodeByID(lClientID)%>" disabled> </TD>
              <TD height=20 width="16%">还款客户名称 ：</TD>
              <TD height=20 width="37%"><textarea class=box  
            name="lClientName" rows=2 cols=30 disabled><%=NameRef.getClientNameByID(lClientID)%></textarea> 
              </TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <TD height=20 width="3%">
			  <%
			  if (lDepositAccountID < 0)
			  {
			  %>
			 <INPUT  name="Rb1" 
            type=radio value="1"  disabled> 
			 <%}
			 else
			{
			%>
			<INPUT  name="Rb1" 
            type=radio value="1" checked disabled> 
			<%}%>
			</TD>
<script language="javascript">			  
		showDisableAccountCtrl("strDepositAccountNo","<%=NameRef.getAccountNoByID(lDepositAccountID)%>","活期存款账号","width=21%","width=23%")
</script>
            
              <TD height=32 vAlign=middle width="16%">&nbsp;</TD>
              <TD height=32 vAlign=middle width="37%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <TD height=20 width="3%"> 
			 <%
			  if (lBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb1" 
            type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb1" 
            type=radio value="2" checked disabled> 
			<%}%> </TD>
              <TD height=20 vAlign=middle width="21%">收款银行名称：</TD>
           
              <TD height=20 vAlign=middle width="23%"><textarea 
            class=box  name="strBankName" rows=2 cols=30 disabled><%=NameRef.getBankNameByID(lBankID)%></textarea> 
              
              </TD>
              <TD height=20 vAlign=middle width="16%">&nbsp;</TD>
              <TD height=20 vAlign=middle width="37%">&nbsp;</TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD height=20 width="17%"><U>银团贷款详细资料</U> </TD>
              <TD height=20 width="33%">&nbsp;</TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
            
 <script language=javascript>			  
		showDisableAccountCtrl("strLoanAccountNo","<%=NameRef.getAccountNoByID(lLoanAccountID)%>","银团贷款账号","width=17%","width=33%")
</script>
       
              <TD height=20 width="15%">贷款客户名称 ：</TD>
              <TD height=20 width="35%"><textarea class=box disabled 
            name="strLoanClientName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea> 
              </TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <TD height=20 width="17%">合同号：</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="strLoanContract" value="<%=NameRef.getContractNoByID(lLoanContractID)%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%"></TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <TD height=20 width="17%">放款通知单号：</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="lLoanNoteNo" value="<%=NameRef.getPayFormNoByID(lLoanNoteID)%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%">&nbsp;</TD>
            </TR>
			  <TR borderColor=#E8E8E8> 
              <TD height=20 width="17%">起始日期：</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="strStartDate" value="<%=strStartDate%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%"></TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD height=20 vAlign=middle nowrap width="15%"><U>利息收回详细资料</U> </TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="35%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> 
				<%
			  if (lPayInterestAccountID < 0)
			  {
			  %>
			  <INPUT  name="Rb2" 
            	type=radio value="1"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb2" 
            type=radio value="1" checked disabled> 
			<%}%> </td>
                    <script language=javascript>			  
	showDisableAccountCtrl("lPayInterestAccountNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","付息活期存款账号","width=20%","")
</script>
                   <TD width="100">&nbsp;</TD>
                    <TD height=20 vAlign=middle>付息客户名称：</TD>
                    <TD height=20 vAlign=top ><textarea name="strPayInterestClientName"  class="box"  bgcolor="#FF00"  rows=2 cols=30 disabled><%=NameRef.getClientNameByAccountID(lPayInterestAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">
				<%
			  if (lInterestBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb2" 
            	type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb2" 
            type=radio value="2" checked disabled> 
			<%}%></td>
					<td>开户行：</td>
					<td width="100">&nbsp;</td>
					<td><textarea name="strInterestBankName" class="box"  rows="2" cols="30" disabled><%=NameRef.getBankNameByID(lInterestBankID)%></textarea></td>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>担保费付出详细资料</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">
			<%
			  if (lPaySuretyAccountID < 0)
			  {
			  %>
			  <INPUT  name="Rb3" 
            	type=radio value="1"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb3" 
            type=radio value="1" checked disabled> 
			<%}%> </td>
                    <script language=javascript>showDisableAccountCtrl("strPaySuretyAccountNo","<%=NameRef.getAccountNoByID(lPaySuretyAccountID)%>","担保费付出存款账号","width=20%","")
</script>
                    <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">付款客户名称： </TD>
                    <TD height="20" vAlign="top"> <textarea name="strPaySuretyClientName" class="box"   rows="2" cols="30" disabled><%=NameRef.getClientNameByAccountID(lPaySuretyAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> 
			<%
			  if (lSuretyBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb3" 
            	type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb3" 
            type=radio value="2" checked disabled> 
			<%}%> </td>
                    <td>开户行：</td>
					<td width="100">&nbsp;</td>
					<td><textarea name="strSuretyBankName" class="box"   rows="2" cols="30" disabled><%=NameRef.getBankNameByID(lSuretyBankID)%></textarea></td>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>担保费收入详细资料</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">&nbsp; </td>
                    <script language=javascript>showDisableAccountCtrl("sreReceiveSuretyAccountNo","<%=NameRef.getAccountNoByID(lReceiveSuretyAccountID)%>","担保费收入账号","width=20%","")
</script>           <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">收款客户名称： </TD>
                    <TD height="20" vAlign="top"> <textarea name="strReceiveSuretyAccountName" class="box"  bgcolor="#FF00" rows="2" cols="30" disabled><%=NameRef.getClientNameByAccountID(lReceiveSuretyAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR>
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <td width="80%"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD  height="21"  valign="top"width="250">应当付出 </TD>
                    <TD height="21" vAlign="top" width="250">实际付出</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
				  <tr> 
                    <td>贷款利息：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dInterest"  value="<%=dInterest==0.0?"0.0":DataFormat.formatListAmount(dInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealInterest"  value="<%=dRealInterest==0.0?"0.0":DataFormat.formatListAmount(dRealInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitInterest"  disabled>
					<%
					}
					%>
                      免还剩余利息</TD>
                  </tr>
                  <tr> 
                    <td>其中 计提利息：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dInterestReceiveAble" value="<%=dInterestReceiveAble==0.0?"0.0":DataFormat.formatListAmount(dInterestReceiveAble)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealInterestReceiveAble" value="<%=dRealInterestReceiveAble==0.0?"0.0":DataFormat.formatListAmount(dRealInterestReceiveAble)%>" disabled></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
				  <!--
                  <tr> 
                    <td>&nbsp; &nbsp;&nbsp;&nbsp;本次利息：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dInterestIncome"  value="<%=dInterestIncome==0.0?"0.0":DataFormat.formatListAmount(dInterestIncome)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealInterestIncome"  value="<%=dRealInterestIncome==0.0?"0.0":DataFormat.formatListAmount(dRealInterestIncome)%>" disabled></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
				  -->
				 <tr> 
                    <td>复利：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dCompoundInterest"  value="<%=dCompoundInterest==0.0?"0.0":DataFormat.formatListAmount(dCompoundInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealCompoundInterest"  value="<%=dRealCompoundInterest==0.0?"0.0":DataFormat.formatListAmount(dRealCompoundInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitCompoundInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitCompoundInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitCompoundInterest"  disabled>
					<%
					}
					%>
                      免还剩余复息</TD>
                  </tr>
                  <tr> 
                    <td>逾期罚息：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dOverDueInterest"   value="<%=dOverDueInterest==0.0?"0.0":DataFormat.formatListAmount(dOverDueInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealOverDueInterest" value="<%=dRealOverDueInterest==0.0?"0.0":DataFormat.formatListAmount(dRealOverDueInterest)%>" disabled></TD>
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitOverDueInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitOverDueInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitOverDueInterest"  disabled>
					<%
					}
					%>
                      免还剩余罚息</TD>
                  </tr>
                  <tr> 
                    <td>担保费：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dSuretyFee" value="<%=dSuretyFee==0.0?"0.0":DataFormat.formatListAmount(dSuretyFee)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealSuretyFee" value="<%=dRealSuretyFee==0.0?"0.0":DataFormat.formatListAmount(dRealSuretyFee)%>" disabled></TD>
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitSuretyFee == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitSuretyFee" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitSuretyFee"  disabled>
					<%
					}
					%>
                      免还剩余担保费</TD>
                  </tr>
				  <tr> 
                    <td>利息费合计：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dSum" value="<%=(dInterest+dCompoundInterest+dOverDueInterest+dSuretyFee)==0.0?"0.0":DataFormat.formatListAmount(dInterest+dCompoundInterest+dOverDueInterest+dSuretyFee)%>" disabled></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <input type="text"  class="box" name="dRealSum" value="<%=(dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealSuretyFee)==0.0?"0.0":DataFormat.formatListAmount(dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealSuretyFee)%>" disabled></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>免还原因：</td>
                    <TD  height="20"  valign="top"><textarea name="strAdjustInterestReason" class="box"   rows="2" cols="30" disabled><%=DataFormat.formatString(strAdjustInterestReason)%></textarea></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
	<tr>
   	<TD colSpan=2 height=62 vAlign=top width="100%">
	 <TABLE align=center border=0 borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>各成员行利息实际收入明细表</U></TD>
		</TR>
		 <TR borderColor=#E8E8E8>  
		 	<TD colSpan=5 height=30>
		 	 	<table width="100%" border="1" align="center"  class="ItemList">
		  				 <tr> 	
		  					<td>			  					
							    <tr class="tableHeader"> 
							         <td class="ItemTitle" nowrap height="20"> 
							            <div align="center">&nbsp;</div>
							          </td>							          
							          <td class="ItemTitle" nowrap height="20"> 
							            <div align="center">贷款利息</div>
							          </td>
									  <td class="ItemTitle" nowrap height="20"> 
							            <div align="center">复利</div>
							          </td>
							          <td class="ItemTitle" nowrap height="20"> 
							            <div align="center">罚息</div>
							          </td>
							          <td class="ItemTitle" nowrap height="20"> 
							            <div align="center">合计</div>
							          </td>
								 </tr>	
				<%
	/*		Collection resultColl = (Collection)request.getAttribute("searchBankLoanMemberResults");

			Iterator itResult = null;
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			int i = 0;
		if(itResult != null && itResult.hasNext())
		{
			while(itResult.hasNext())
		*/
			ArrayList resultColl = (ArrayList)session.getAttribute("searchBankLoanMemberResults");		
		if(resultColl!=null && resultColl.size()!=0)
		{	
			for(int i = 0 ;i< resultColl.size();i++)
			{		
				SyndicationLoanInterestInfo obj = new SyndicationLoanInterestInfo();		
				obj = (SyndicationLoanInterestInfo)resultColl.get(i);
		%>
				<tr> 
	          <td class="ItemBody" nowrap height="21"> 
			  <div align="center"><%=DataFormat.formatEmptyString(obj.getBankName())%>
					</div>					
	          </td>
		<td class="ItemBody" nowrap height="21">	
			 <div align="right"><%=DataFormat.formatAmountUseZero(obj.getInterest())%>
					</div>			 	          
	          </td>
			  
	          <td class="ItemBody" nowrap height="21">
			  <div align="right"><%=DataFormat.formatAmountUseZero(obj.getCompoundInterest())%>
					</div>		 			  
	          </td>
	
			    <td class="ItemBody" nowrap height="21"> 
				<div align="right"><%=DataFormat.formatAmountUseZero(obj.getForpeitInterest())%>				
					</div>		 			  	           
	          </td>  
			   <td class="ItemBody" nowrap height="21"> 
			   <div align="right"><%=DataFormat.formatAmountUseZero((dRealInterest+dRealCompoundInterest+dRealOverDueInterest)*obj.getRate()/100)%>
					</div>
	          </td>  

				<%
				}
			}	
			else
			{
			%>
						<tr>
							<td class="ItemBody" nowrap height="20">
								<div align="center">&nbsp;
								</div>
							</td>
							<td class="ItemBody" nowrap height="20">
								<div align="center">&nbsp;
								</div>
							</td>
							<td class="ItemBody" nowrap height="20">
								<div align="center">&nbsp;
								</div>
							</td>
							<td class="ItemBody" nowrap height="20">
								<div align="center">&nbsp;
								</div>
							</td>						
							<td class="ItemBody" nowrap height="20">
								<div align="center">&nbsp;
								</div>
							</td>						
						</tr>
	
			<%
			}
			%>
			<tr> 
	          <td class="ItemBody" nowrap height="21"> 
	           <div align="center">合计：
					</div>	  
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
			  <div align="right"><%=DataFormat.formatAmountUseZero(dRealInterest)%>
					</div>			
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
			  <div align="right"><%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>
					</div>		                
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
			  <div align="right"><%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>
					</div>			 
	          </td>  
	          <td class="ItemBody" nowrap height="21"> 
			   <div align="right"><%=DataFormat.formatAmountUseZero(dRealInterest+dRealCompoundInterest+dRealOverDueInterest)%>
					</div>			
	          </td>  
	        </tr>
			
			</table>
			</TD>
		  </TR>
		</TABLE>
	 </td> 
  </tr>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>本金/利息处理办法</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD width="250">
					<%
					if (lCapitalAndInterstDealway == 1)
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  checked disabled>
					<%
					}
					else
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  disabled>
					<%
					}
					%>
                      汇总处理 </TD>
                    <TD vAlign="middle">&nbsp;</TD>
                    <TD height="20" vAlign="middle" width="250"><%
					if (lCapitalAndInterstDealway == 2)
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  checked disabled>
					<%
					}
					else
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  disabled>
					<%
					}
					%>
                      分笔处理</TD>
                    <TD height="20" vAlign="top">&nbsp; </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center height=116 width="97%">
          <TBODY>
            <TR vAlign=middle> 
              <TD height=32 colspan="6" valign="top"><table width="100%" border="0" >
                  <tr> 
                    <td width="10">
					<%
					if (lPreFormID < 0)
					{
					%>
					<input type="checkbox" name="lLoanNoteID"  disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox" name="lLoanNoteID" checked  disabled>
					<%
					}
					%>
					</td>
                    <td width="100">提前还款通知单</td>
                    <td>
					<input type="text" name="strLoanNoteNo"  class="box" value="<%=NameRef.getAheadPayFormNoByID(lPreFormID)%>" disabled></td>
                  </tr>
                </table></TD>
            </TR>
            <TR vAlign=middle> 
              <TD height=20 width="10%">还款金额：</TD>
			  <TD height=20 width="23%"><%=sessionMng.m_strCurrencySymbol%>  
                <INPUT class=box   name="dAmount" value="<%=dAmount==0.0?"0.0":DataFormat.formatListAmount(dAmount)%>" disabled> </TD>
              <TD height=32 width="14%">起息日：</TD>
              <TD height=32 width="14%"><INPUT class=box name="tsInterestStart" value="<%=DataFormat.formatDate(tsInterestStart)%>" disabled> </TD>
              <TD height=32 width="8%">&nbsp;</TD>
              <TD height=32 width="31%"></TD>
            </TR>
            <TR> 
              <TD align=left height=20 vAlign=middle width="10%">交易号：</TD>
              <TD align=left height=20 vAlign=middle width="23%">
			  <INPUT class=box  name="strTransNo" value="<%=DataFormat.formatString(strTransNo)%>" disabled> </TD>
              <TD align=left height=20 vAlign=middle width="14%">摘 要：</TD>
			  <TD align=left height=20 vAlign=middle width="14%">
			  <INPUT class=box  maxLength=200 name="strAbstract"  size=25 value="<%=DataFormat.formatString(strAbstract)%>" disabled> </TD>
              <TD align=left height=20 vAlign=middle width="8%">&nbsp;</TD>
              <TD align=left height=20 vAlign=middle width="31%">&nbsp;</TD>
            </TR>
            <TR> 
              <TD align=left colSpan=6 height=20 vAlign=top> 
                <DIV align=right> 
<%
	if("Query".equalsIgnoreCase(strAction))
	{
		if(strOBInstr!=null && strOBInstr.equals("obinstr"))
		{
%>
			<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
			<input class="button" name="Submit32" type="button" value=" 返 回 " onClick="doReturn();">	
<%	
		}
		else
		{
%>
				<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
			
<%
		}
	}
	else
	{
%>
          		<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
				%>
					<input type="button" name="check" value=" 复 核 " class="button" onClick="doCheck();">
				<%
				}
				else
				{
				%>
					<input type="button" name="check" value=" 取消复核 " class="button" onClick="doUndoCheck();">
				<%
				}
				%>
					<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
					<input type="button" name="return" value=" 返 回 " class="button" onClick="doReturnFun();"> 
<%
	}
%>
			  </DIV>
			 </TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <HR> <TABLE align=center border=0 height=22 width="97%">
          <TBODY>
            <TR vAlign=middle> 
              <TD height=25 width="10%">	<%=(lStatusID == SETTConstant.TransactionStatus.SAVE)?"复核备注":"取消复核备注："%></TD>
              <TD height=25 vAlign=top width="16%"><INPUT class=box name="strCheckAbstract" size="40" value="<%=DataFormat.formatString(strCheckAbstract)%>" onfocus="nextfield='submitfunction';" maxlength="100"> 
              </TD>
              <TD height=25 vAlign=middle width="7%">录入人：</TD>
              <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(lInputUserID)%></TD>
              <TD height=25 width="8%">录入日期：</TD>
              <TD height=25 width="8%"><%=DataFormat.formatDate(tsInput)%></TD>
			  <%
			 if(lStatusID != SETTConstant.TransactionStatus.SAVE)
			 {
			  %>
              <TD height=25 width="7%">复核人：</TD>
              <TD height=25 width="8%"><%=NameRef.getUserNameByID(lCheckUserID)%></TD>
              <TD height=25 width="8%">复核日期：</TD>
              <TD height=25 width="8%"><%=DataFormat.formatDate(tsExecute)%></TD>
			  <%
			  }
			  else
			  {
			  %>
			  <TD height=25 width="7%">复核人：</TD>
              <TD height=25 width="8%">&nbsp;</TD>
              <TD height=25 width="8%">复核日期：</TD>
              <TD height=25 width="8%">&nbsp;</TD>
			  <%
			  }
			  %>
              <TD height=25 width="6%">状态：</TD>
              <TD height=25 width="6%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%> </TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
   </TBODY>
</TABLE></form>
<script language="JavaScript">
//标识是否已提交请求
var isSubmited = false;

function checkSuccess()
{
    if (confirm("复核成功，是否打印?"))
    {
        //code
    }
	else
	{
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v075.jsp";
	}
}

function doCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if (confirm("是否复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.frmv076.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.frmv076.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if(!validateFields(frmv076)) return;
	
	if (confirm("是否取消复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.frmv076.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.frmv076.submit();
	}
}

function doPrint()
{
	//code
	window.open( "a607-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=SETTConstant.TransactionType.BANKGROUPLOANRECEIVE%>&strTransNo=<%=strTransNo%>");
}
function doReturn()
		{
			document.location.href="<%=strContext%>/settlement/obinstruction/control/c001.jsp?strSuccessPageURL=/settlement/obinstruction/view/v002.jsp&strFailPageURL=/settlement/obinstruction/view/v002.jsp&_pageLoaderKey=<%=strPageLoaderKey%>";
		}	
function doReturnFun()
{
	if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }
	
	if (confirm("是否返回?")) 
	{
		isSubmited = true;
		//alert(1);
		showSending();
		//alert(2);
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/loan/control/c013.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.BANKGROUPLOANRECEIVE%>&strSuccessPageURL=../view/v077.jsp&strFailPageURL=../view/v077.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";//复核成功或者失败均返回链接查找页面
	<%
	}else 
	{%>
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v075.jsp";//匹配页面
	<%}%>
	
	}
	
}

function allFields()
{
	
	this.aa = new Array("strCheckAbstract","取消复核备注","string",1);
	
} 
</script>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
<script language="JavaScript">
firstFocus(document.frmv076.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmv076");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmv076.strCheckAbstract);
//setSubmitFunction("doUndoCheck()");
setFormName("frmv076");     
</script>
<%
	}
%>
<%
	}
%>

<%	
		if(Constant.ActionResult.SUCCESS.equals(strActionResult) && String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
		{
			%>
				<script language="JavaScript">
					checkSuccess();
				</script>
			<%
		}

OBHtml.showOBHomeEnd(out);
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>