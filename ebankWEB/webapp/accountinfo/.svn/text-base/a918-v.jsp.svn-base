<%--
 页面名称 ：a918-v.jsp
 页面功能 : 委托收回——复核/取消复核显示页面
 作    者 ：qqgd
 日    期 ：2003年11月12日
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
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
	 java.sql.Timestamp tsInput = null;
	 long lInputUserID = -1;
	 long lCheckUserID = -1; 
	 long lStatusID = -1;
	 java.sql.Timestamp tsModify = null;
	 java.sql.Timestamp tsExecute = null;
	 long lOrderByCode = Sett_TransRepaymentLoanDAO.ORDER_TRANS_NO;
	 boolean isDesc = true;
		
	 long lFreeFormID = -1;
	 long lClientID = -1;
	 long lDepositAccountID = -1;
	 long lBankID = -1;
	
	 long lContractID = -1;
	 long lLoanAccountID = -1;
	 long lLoanNoteID = -1 ;//放款通知单LoanNote
	 String strDateStart = "";
	 long lPreFormID = -1;//提前还款通知单
	
	 double dAmount = 0.0;
	 java.sql.Timestamp tsInterestStart = null;
	 long lAbstractID = -1;
	 String strAbstract = "";
	 
	long lConsignAccountID = -1;//委托存款账号，委托专用
	long lConsignDepositAccountID = -1;//委托放活期存款账号，委托专用
	long lPayInterestAccountID = -1;
	long lPayInterestBankID = -1;
	long lReceiveInterestAccountID = -1;//利息收入账户ID，委托专用
	long lCommissionAccountID = -1;//手续费付出账号，委托专用
	long lCommissionBankID = -1;//手续费付出银行，委托专用
	
	
	//应当支付 
	 double dInterest = 0.0;    
	 double dInterestTax = 0.0;
	 double dCompoundInterest = 0.0;
	 double dOverDueInterest = 0.0;
	 double dCommission = 0.0;
	 
	//实际支付利息
	 double dRealInterest = 0.0;
	 double dRealInterestTax = 0.0;
	 double dRealCompoundInterest = 0.0;
	 double dRealOverDueInterest = 0.0;
	 double dRealCommission = 0.0;
	
	//是否免还利息
	long lIsRemitInterest = -1;
	long lIsRemitCompoundInterest = -1;
	long lIsRemitOverDueInterest = -1;
	long lIsRemitCommission = -1;
	
	//免还原因
	String strAdjustInterestReason = "";
	
	//本金利息处理方法
	long lCapitalAndInterstDealway = -1;
	
	//取消复核摘要
	String strCheckAbstract = "";
	 
	 if (request.getAttribute("strActionResult") != null)
	{
		  strActionResult = (String)request.getAttribute("strActionResult");
	}
	if (request.getAttribute("strAction") != null)
	{
		  strAction = (String)request.getAttribute("strAction");
	}
		
	String strTemp = null;
	//返回链接查找
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
	//网银财务指令接收
		String strOBInstr = (String)request.getAttribute("OBInstr");
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	//业务信息
	
	TransRepaymentLoanInfo transRepaymentLoanInfo = null;
	transRepaymentLoanInfo = (TransRepaymentLoanInfo)request.getAttribute("searchResults");
	
	if(transRepaymentLoanInfo != null)
	{
	  lID = transRepaymentLoanInfo.getID();
	  lOfficeID = transRepaymentLoanInfo.getOfficeID();
	  lCurrencyID = transRepaymentLoanInfo.getCurrencyID();
	 
	  lTransactionTypeID = transRepaymentLoanInfo.getTransactionTypeID();
	  tsInput = transRepaymentLoanInfo.getInput();
	  lInputUserID = transRepaymentLoanInfo.getInputUserID();
	  lCheckUserID = transRepaymentLoanInfo.getCheckUserID();
	  lStatusID = transRepaymentLoanInfo.getStatusID();
	  tsModify = transRepaymentLoanInfo.getModify();
	  tsExecute = transRepaymentLoanInfo.getExecute();
	
      lFreeFormID = transRepaymentLoanInfo.getFreeFormID();
	  lClientID = transRepaymentLoanInfo.getClientID();
	  lDepositAccountID = transRepaymentLoanInfo.getDepositAccountID();
	  lBankID = transRepaymentLoanInfo.getBankID();
	  lLoanAccountID = transRepaymentLoanInfo.getLoanAccountID();
	  lContractID = transRepaymentLoanInfo.getLoanContractID();
	  lLoanNoteID = transRepaymentLoanInfo.getLoanNoteID();
	  lPreFormID = transRepaymentLoanInfo.getPreFormID();
	  dAmount = transRepaymentLoanInfo.getAmount();
	  tsInterestStart = transRepaymentLoanInfo.getInterestStart();
	  
	  lConsignAccountID = transRepaymentLoanInfo.getConsignAccountID();
	  
	  lConsignDepositAccountID = transRepaymentLoanInfo.getConsignDepositAccountID();
	
	  lPayInterestAccountID = transRepaymentLoanInfo.getPayInterestAccountID();
	  
	  lPayInterestBankID = transRepaymentLoanInfo.getInterestBankID();
		
	  lReceiveInterestAccountID = transRepaymentLoanInfo.getReceiveInterestAccountID();
	  
	  lCommissionAccountID = transRepaymentLoanInfo.getCommissionAccountID();
	  
	  lCommissionBankID = transRepaymentLoanInfo.getCommissionBankID();
	 
	 
	  
	 
	  dInterest = transRepaymentLoanInfo.getInterest();
	  dInterestTax = transRepaymentLoanInfo.getInterestTax();
	  dCompoundInterest = transRepaymentLoanInfo.getCompoundInterest();
	  dOverDueInterest = transRepaymentLoanInfo.getOverDueInterest();
	  dCommission = transRepaymentLoanInfo.getCommission();
	  strAdjustInterestReason = transRepaymentLoanInfo.getAdjustInterestReason();
	  
	  dRealInterest = transRepaymentLoanInfo.getRealInterest();
	  dRealInterestTax = transRepaymentLoanInfo.getRealInterestTax();
	  dRealCompoundInterest = transRepaymentLoanInfo.getRealCompoundInterest();
	  dRealOverDueInterest = transRepaymentLoanInfo.getRealOverDueInterest();
	  dRealCommission = transRepaymentLoanInfo.getRealCommission();
	
	  lIsRemitInterest = transRepaymentLoanInfo.getIsRemitInterest();
	  lIsRemitCompoundInterest = transRepaymentLoanInfo.getIsRemitCompoundInterest();
	  lIsRemitOverDueInterest = transRepaymentLoanInfo.getIsRemitOverDueInterest();
	  lIsRemitCommission = transRepaymentLoanInfo.getIsRemitCommission();
	  
	  lCapitalAndInterstDealway = transRepaymentLoanInfo.getCapitalAndInterstDealway();
	  
	  strAbstract = transRepaymentLoanInfo.getAbstract();
	  
	  strTransNo = transRepaymentLoanInfo.getTransNo();
	  
	  strCheckAbstract = transRepaymentLoanInfo.getCheckAbstract();
	
   }
	 
		
	   //根据放款通知单查询起始日期
	 /*   LoanPayFormDetailInfo tempLoanPayFormDetailInfo = new LoanPayFormDetailInfo();
		Sett_TransGrantLoanDAO tempSett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
		
		tempLoanPayFormDetailInfo.setLoadNoteID(lLoanNoteID);
		java.sql.Timestamp tsTemp = null;
		tsTemp = (tempSett_TransGrantLoanDAO.findPayFormDetailByCondition(tempLoanPayFormDetailInfo)).getStart();
		strDateStart = DataFormat.formatDate(tsTemp);
		*/
		
		
%>
 <form name="frmV038" action="../control/c014.jsp" method="post">
<TABLE border=0 class=top height=290 width="99%">
  <TBODY> 
<TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
  <input name="strAction"  type="hidden">
   <input name="strSuccessPageURL"  type="hidden" value="<%
			if(lStatusID == SETTConstant.TransactionStatus.SAVE)//如果未复核,复核成功之后进入匹配页面
			{out.print("../view/v036.jsp");
			}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)//如若已经复核，取消复核成功之后进入复核链接查找页面
			{out.print("../view/v037.jsp");}%>">
	<input name="strFailPageURL"  type="hidden" value="../view/v038.jsp">
	<input name="lID"  type="hidden" value="<%=lID%>">
	<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
	<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
	<input name="tsModify"  type="hidden" value="<%=tsModify%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
	<input name="isDesc" type="Hidden" value="<%=isDesc%>"></TD>
  </TR>
	
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>委托贷款收回</B></TD>
  </TR>

  <TR>
    <TD height=61 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY> 
        <TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=30 nowrap><U>归还贷款账户详细资料</U> </TD>
          <TD height=30 vAlign=middle width="29%" nowrap>&nbsp; 
           </TD>
          <TD height=30 width="16%" nowrap>&nbsp;</TD>
          <TD height=30 width="34%" nowrap>&nbsp;</TD>
        </TR>
		 <TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=30 nowrap><input type="Checkbox" name="lIsCancelLoan" value="1" disabled>
		  <script language="JavaScript">
		  if ('<%=lFreeFormID%>' > 0)
		  {
		   document.frmV038.lIsCancelLoan.checked = true;
		  }
		 
		  </script>
		  免还通知单:</TD>
		  <TD height=30 nowrap><input class=box type="Text" name="strFreeFormNo"  value="<%=NameRef.getFreeFormNoByID(lFreeFormID)%>" disabled> </TD>
       	  <TD height=30 nowrap colspan="2">&nbsp;</td>	
	    </TR>
        <TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=20 nowrap>还款客户编号：</TD>
          <TD height=20 width="29%" nowrap> 
            <INPUT class=box  name="strClientNo" value="<%=NameRef.getClientCodeByID(lClientID)%>" disabled>
          </TD>
          <TD height=20 width="16%" nowrap>还款客户名称 ：</TD>
          <TD height=20 width="34%" nowrap> 
            <textarea class=box  name="strClientName" rows=2 cols=30 disabled><%=NameRef.getClientNameByID(lClientID)%></textarea>
		  </TD>
        </TR>
        <TR borderColor=#E8E8E8> 
          <TD height=20 width="3%" nowrap> 
            <INPUT type=radio name="Rb1"  value="1" disabled>
		  </TD>		
<script language=javascript>			  
		showDisableAccountCtrl("strDepositAccountNo","<%=NameRef.getAccountNoByID(lDepositAccountID)%>","活期存款账号","width=21%","width=23%")
</script>
          <TD height=20 vAlign=middle width="16%" nowrap>&nbsp;</TD>
          <TD height=20 vAlign=middle width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
          <TD height=20 width="3%" nowrap> 
             <INPUT type=radio name="Rb1"  value="2" disabled>
		<script language="JavaScript">
		  if ('<%=lDepositAccountID%>' > 0)
		  {
		  	 document.frmV038.Rb1[0].checked = true;
		  }
		  else if('<%=lBankID%>'  > 0)
		  {
		  	document.frmV038.Rb1[1].checked = true;
		  }
		</script>
        
          </TD>
          <TD height=20 vAlign=middle width="18%" nowrap>收款银行名称：</TD>
          <TD height=20 vAlign=middle width="29%" nowrap> 
            <textarea class=box disabled name="txtBankNameCtrl" rows=2 cols=30><%=NameRef.getBankNameByID(lBankID)%></textarea>
          </TD>
          <TD height=20 vAlign=middle width="16%" nowrap>&nbsp;</TD>
          <TD height=20 vAlign=middle width="34%" nowrap>&nbsp;</TD>
        </TR>
       </TBODY>
      </TABLE>
    </TD>
  </TR>
  <TR>
    <TD height=149 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY> 
        <TR borderColor=#E8E8E8> 
          <TD height=18 vAlign=middle width="17%" nowrap><U>委托贷款详细资料</U> </TD>
          <TD height=18 vAlign=top width="33%" nowrap>&nbsp;</TD>
          <TD height=18 width="16%" nowrap>&nbsp;</TD>
          <TD height=18 width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 	
<script language=javascript>			  
		showDisableAccountCtrl("strLoanAccountNo","<%=NameRef.getAccountNoByID(lLoanAccountID)%>","委托贷款账号","width=17%","width=33%")
</script>
          <TD height=20 width="16%" nowrap>委托贷款客户名称 ： </TD>
          <TD height=20 width="34%" nowrap> 
            <textarea class=box disabled name="strLoanClientName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
		  </TD>
        </TR>
        <TR borderColor=#E8E8E8> 
          <TD height=20 vAlign=middle width="17%" nowrap>合同号：</TD>
          <TD height=20 vAlign=top width="33%" nowrap> 
            <INPUT class=box disabled name="strLoanContract" value="<%=NameRef.getContractNoByID(lContractID)%>">
          </TD>
          <TD height=20 width="16%" nowrap>&nbsp;</TD>
          <TD height=20 width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
          <TD height=20 vAlign=middle width="17%" nowrap>放款通知单据号：</TD>
          <TD height=20 vAlign=top width="33%" nowrap> 
            <INPUT class=box disabled name="strLoanNoteNo" value="<%=NameRef.getPayFormNoByID(lLoanNoteID)%>"> 
          </TD>
          <TD height=20 width="16%" nowrap>&nbsp;</TD>
          <TD height=20 width="34%" nowrap>&nbsp;</TD>
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY> 
        <TR borderColor=#E8E8E8> 
          <TD colSpan=4 height=16 vAlign=middle nowrap><U>委托方详细资料</U></TD>
        </TR>
        <TR borderColor=#E8E8E8> 
<script language=javascript>			  
		showDisableAccountCtrl("strConsignAccountNo","<%=NameRef.getAccountNoByID(lConsignAccountID)%>","委托存款账号","width=17%","width=33%")
</script>	
          <TD height=20 width="15%" nowrap>委托存款客户名称 :</TD>
          <TD height=20 width="35%" nowrap> 
            <textarea class=box disabled name="strConsignName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lConsignAccountID)%></textarea>
		  </TD>
        </TR>
        <TR borderColor=#E8E8E8> 
<script language=javascript>			  
		showDisableAccountCtrl("strDepositAccountNo ","<%=NameRef.getAccountNoByID(lConsignDepositAccountID)%>","委托方活期存款账号","width=17%","width=33%")
</script>	
          <TD height=20 width="15%" nowrap>活期客户名称 :</TD>
          <TD height=20 width="35%" nowrap> 
            <textarea class=box disabled name="strDepositAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lConsignDepositAccountID)%></textarea>
		  </TD>
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY> 
      
		<TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=30 nowrap><U>利息还款详细资料</U> </TD>
          <TD height=30 vAlign=middle width="29%" nowrap>&nbsp; 
           </TD>
          <TD height=30 width="16%" nowrap>&nbsp;</TD>
          <TD height=30 width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
		 <TD height=20 width="3%" nowrap> 
            <INPUT type=radio name="Rb2"  value="1" disabled checked>
		  </TD>		
<script language=javascript>	
//利息付出账号		  
	showDisableAccountCtrl("strPayInterstAccountNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","利息付出账号","width=20% nowrap","width=30% nowrap")
</script>				  
          <TD height=20 vAlign=middle width="15%" nowrap>利息付出客户名称: </TD>
          <TD height=20 vAlign=top width="35%" nowrap> 
            <textarea class=box disabled name="strPayInterstClientName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPayInterestAccountID)%></textarea>
		  </TD>
     	   </TR>
		   <TR borderColor=#E8E8E8> 
		 <TD height=20 width="3%" nowrap> 
            <INPUT type=radio name="Rb2"  value="2" disabled>
		 <script language="JavaScript">
		  if ('<%=lPayInterestAccountID%>' > 0)
		  {
		  	 document.frmV038.Rb2[0].checked = true;
		  }
		  else if('<%=lPayInterestBankID%>'  > 0)
		  {
		  	document.frmV038.Rb2[1].checked = true;
		  }
		</script>
          </TD>
          <TD height=20 vAlign=middle width="18%" nowrap>开户行：</TD>
          <TD height=20 vAlign=middle width="29%" nowrap> 
            <textarea class=box disabled name="strPayInterestBankName" rows=2 cols=30><%=NameRef.getBankNameByID(lPayInterestBankID)%></textarea>
          </TD>
          <TD height=20 vAlign=middle width="16%" nowrap>&nbsp;</TD>
          <TD height=20 vAlign=middle width="34%" nowrap>&nbsp;</TD>
        </TR>
		 <TR borderColor=#E8E8E8> 
                <TD colSpan=5 height=20> <HR> </TD>
         </TR>
      <TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=30 nowrap><U>利息收入详细资料</U> </TD>
          <TD height=30 vAlign=middle width="29%" nowrap>&nbsp; 
           </TD>
          <TD height=30 width="16%" nowrap>&nbsp;</TD>
          <TD height=30 width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
		<TD height=20 width="3%" nowrap>&nbsp; 
		  </TD>		
<script language=javascript>	
//利息收入账号		  
	showDisableAccountCtrl("strReceiveInterestAccountNo","<%=NameRef.getAccountNoByID(lReceiveInterestAccountID)%>","利息收入账号","width=20% nowrap","width=30% nowrap")
</script>	
          <TD height=20 vAlign=middle width="15%" nowrap>利息收入客户名称：</TD>
          <TD height=20 vAlign=top width="35%" nowrap> 
            <textarea class=box disabled name="strReceiveInterestAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lReceiveInterestAccountID)%></textarea>
		  </TD>
        </TR>
	    </TBODY>
      </TABLE>
    </TD>
  </TR>
   <TR>
    <TD height=20 vAlign=top width="100%">
 <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY> 
 <TR borderColor=#E8E8E8> 
          <TD colSpan=2 height=30 nowrap><U>手续费收回详细资料</U> </TD>
          <TD height=30 vAlign=middle width="29%" nowrap>&nbsp; 
           </TD>
          <TD height=30 width="16%" nowrap>&nbsp;</TD>
          <TD height=30 width="34%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
		 <TD height=20 width="3%" nowrap> 
            <INPUT type=radio name="Rb3"  value="1" disabled checked>
		  </TD>		
<script language=javascript>	
//手续费付出账号		  
	showDisableAccountCtrl("strCommissionAccountNo","<%=NameRef.getAccountNoByID(lCommissionAccountID)%>","手续费付出账号","width=20% nowrap","width=30% nowrap")
</script>				  
          <TD height=20 vAlign=middle width="15%" nowrap>手续费付出客户名称: </TD>
          <TD height=20 vAlign=top width="35%" nowrap> 
            <textarea class=box disabled name="strCommissionName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lCommissionAccountID)%></textarea>
		  </TD>
     	   </TR>
		   <TR borderColor=#E8E8E8> 
		 <TD height=20 width="3%" nowrap> 
            <INPUT type=radio name="Rb3"  value="2" disabled>
		 <script language="JavaScript">
		  if ('<%=lCommissionAccountID%>' > 0)
		  {
		  	 document.frmV038.Rb3[0].checked = true;
		  }
		  else if('<%=lCommissionBankID%>'  > 0)
		  {
		  	document.frmV038.Rb3[1].checked = true;
		  }
		</script>
        
          </TD>
          <TD height=20 vAlign=middle width="18%" nowrap>开户行：</TD>
          <TD height=20 vAlign=middle width="29%" nowrap> 
            <textarea class=box disabled name="strPayInterestBankName" rows=2 cols=30><%=NameRef.getBankNameByID(lCommissionBankID)%></textarea>
          </TD>
          <TD height=20 vAlign=middle width="16%" nowrap>&nbsp;</TD>
          <TD height=20 vAlign=middle width="34%" nowrap>&nbsp;</TD>
        </TR>
		  <TR borderColor=#E8E8E8> 
                <TD colSpan=5 height=20> <HR> </TD>
           </TR>
		    <TR borderColor=#E8E8E8> 
                <TD colSpan=5 height=20><table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr> 
                    <td>&nbsp;</td>
                    <td >应当支付</td>
                    <td>实际支付</td>
                    <td>&nbsp; </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr> 
                    <td>贷款利息：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%>  <input dir="rtl" type="text"  class="box" name="dInterest" disabled  value="<%=dInterest==0.0?"0.0":DataFormat.formatListAmount(dInterest)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealInterest" type="text" disabled  class="box" value="<%=dRealInterest==0.0?"0.0":DataFormat.formatListAmount(dRealInterest)%>"></td>
                    <td><input type="checkbox" name="CbIsRemitInterest" value="1" class="box" disabled>
					<script language="JavaScript">
					if ('<%=lIsRemitOverDueInterest%>' == '<%=Constant.RecordStatus.VALID%>')
					{
						document.frmV038.CbIsRemitInterest.checked = true;
					}
					</script>
					</td>
                    <td>免还剩余贷款利息
                      </td>
                  </tr>
                  <tr> 
                    <td>复利：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dCompoundInterest" type="text" disabled  class="box" value="<%=dCompoundInterest==0.0?"0.0":DataFormat.formatListAmount(dCompoundInterest)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealCompoundInterest" type="text" disabled  class="box" value="<%=dRealCompoundInterest==0.0?"0.0":DataFormat.formatListAmount(dRealCompoundInterest)%>"></td>
                    <td><input type="checkbox" name="CbIsRemitCompoundInterest" value="1" class="box" disabled>
					<script language="JavaScript">
					if ('<%=lIsRemitCompoundInterest%>' == '<%=Constant.RecordStatus.VALID%>')
					{
						document.frmV038.CbIsRemitCompoundInterest.checked = true;
					}
					</script>
					</td>
                    <td>免还剩余复利</td>
                  </tr>
                  <tr> 
                    <td>逾期罚息：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dOverDueInterest" type="text" disabled  class="box" value="<%=dOverDueInterest==0.0?"0.0":DataFormat.formatListAmount(dOverDueInterest)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealOverDueInterest" type="text" disabled  class="box" value="<%=dRealOverDueInterest==0.0?"0.0":DataFormat.formatListAmount(dRealOverDueInterest)%>"></td>
                    <td><input type="checkbox" name="CbIsRemitOverDueInterest" value="1" class="box" disabled>
					<script language="JavaScript">
					if ('<%=lIsRemitOverDueInterest%>' == '<%=Constant.RecordStatus.VALID%>')
					{
						document.frmV038.CbIsRemitOverDueInterest.checked = true;
					}
					</script>
					</td>
                    <td>免还剩余逾期罚息</td>
                  </tr>
                  <tr> 
                    <td>手续费：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dCommission" type="text" disabled  class="box" value="<%=dCommission==0.0?"0.0":DataFormat.formatListAmount(dCommission)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealCommission" type="text" disabled  class="box" value="<%=dRealCommission==0.0?"0.0":DataFormat.formatListAmount(dRealCommission)%>"></td>
                    <td><input type="checkbox" name="CbIsRemitCommission" value="1" class="box" disabled>
						<script language="JavaScript">
					if ('<%=lIsRemitCommission%>' == '<%=Constant.RecordStatus.VALID%>')
					{
						document.frmV038.CbIsRemitCommission.checked = true;
					}
					</script>
					</td>
                    <td>免还剩余手续费</td>
                  </tr>
                                    <tr> 
                    <td>利息税费：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dInterestTax" type="text" disabled  class="box" value="<%=dInterestTax==0.0?"0.0":DataFormat.formatListAmount(dInterestTax)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealInterestTax" type="text" disabled  class="box" value="<%=dRealInterestTax==0.0?"0.0":DataFormat.formatListAmount(dRealInterestTax)%>"></td>
                    <td>&nbsp;
					</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr> 
                    <td>利息费用合计：</td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dSumFee" type="text" disabled  class="box" value="<%=(dInterest+dCompoundInterest+dOverDueInterest+dCommission)==0.0?"0.0":DataFormat.formatListAmount(dInterest+dCompoundInterest+dOverDueInterest+dCommission)%>"></td>
                    <td><%=sessionMng.m_strCurrencySymbol%> <input dir="rtl" name="dRealSumFee" type="text" disabled  class="box" value="<%=(dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealCommission)==0.0?"0.0":DataFormat.formatListAmount(dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealCommission)%>"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr> 
                    <td>免还原因：</td>
                    <td>
<textarea class=box disabled name="strAdjustInterestReason" rows=2 cols=30><%=DataFormat.formatString(strAdjustInterestReason)%></textarea></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table> </TD>
           </TR>
		</TBODY>
      </TABLE>
    </TD>
  </TR>
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
					<input type="radio" name="lCapitalAndInterstDealway" disabled>
					  汇总处理 </TD>
                    <TD vAlign="middle">&nbsp;</TD>
                    <TD height="20" vAlign="middle" width="250"><input type="radio" name="lCapitalAndInterstDealway"  disabled>
					<script language="JavaScript">
					if ('<%=lCapitalAndInterstDealway%>' == 1)
					{
					  document.frmV038.lCapitalAndInterstDealway[0].checked = true;
					}
					if ('<%=lCapitalAndInterstDealway%>' == 2)
					{
					  document.frmV038.lCapitalAndInterstDealway[1].checked = true;
					}
					</script>
					
					分笔处理</TD>
                    <TD height="20" vAlign="top">&nbsp; </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>

  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center height=159 width="97%">
          <TBODY>
            <TR vAlign=middle>
              <TD borderColor=#E8E8E8 height=20 nowrap><input type="checkbox" name="Cb" value="1"  disabled>
			  <script language="JavaScript">
			  if ('<%=lPreFormID%>' > 0)
			  {
			  	document.frmV038.Cb.checked = true;
			  }
			  </script>
                提前还款通知单：</TD>
              <TD borderColor=#E8E8E8 height=20 nowrap><INPUT class=box disabled name="strPreFormNo" value="<%=NameRef.getAheadPayFormNoByID(lPreFormID)%>"></TD>
              <TD borderColor=#E8E8E8 height=20 nowrap>&nbsp;</TD>
              <TD height=32 nowrap>&nbsp;</TD>
              <TD height=32 nowrap>&nbsp;</TD>
              <TD height=32 nowrap>&nbsp;</TD>
            </TR>
            <TR vAlign=middle> 
              <TD borderColor=#E8E8E8 height=20 width="21%" nowrap>还款金额：</TD>
              <TD borderColor=#E8E8E8 height=20 width="18%" nowrap> <%=sessionMng.m_strCurrencySymbol%> 
                <INPUT class=box disabled dir="rtl" name="dAmount" size=17 value="<%=dAmount==0.0?"0.0":DataFormat.formatListAmount(dAmount)%>"> </TD>
              <TD borderColor=#E8E8E8 height=20 width="12%" nowrap>&nbsp;</TD>
              <TD height=32 width="20%" nowrap>&nbsp;</TD>
              <TD height=32 width="8%" nowrap>&nbsp;</TD>
              <TD height=32 width="21%" nowrap>&nbsp;</TD>
            </TR>
            <TR vAlign=middle> 
              <TD height=32 width="12%" nowrap>起息日：</TD>
              <TD height=32 width="20%" nowrap> <INPUT class=box disabled name="tsInterestStart" value="<%=DataFormat.formatDate(tsInterestStart)%>"> 
              </TD>
              <TD height=32 width="12%" nowrap>&nbsp;</TD>
              <TD height=32 width="20%" nowrap>&nbsp;</TD>
              <TD height=32 width="8%" nowrap>&nbsp;</TD>
              <TD height=32 width="21%" nowrap>&nbsp;</TD>
            </TR>
            <TR> 
              <TD align=left height=29 vAlign=middle width="21%" nowrap>交易号： </TD>
              <TD align=left height=29 vAlign=middle width="18%" nowrap> <INPUT class=box disabled name="strTransNo" value="<%=DataFormat.formatString(strTransNo)%>"> 
              </TD>
              <TD align=left height=29 vAlign=middle width="12%" nowrap>摘 要：</TD>
              <TD align=left height=29 vAlign=middle width="20%" nowrap> <INPUT class=box disabled maxLength=100 name="strAbstract" size=25 value="<%=DataFormat.formatString(strAbstract)%>"> 
              </TD>
              <TD align=left height=29 vAlign=middle width="8%" nowrap>&nbsp;</TD>
              <TD align=left height=29 vAlign=middle width="21%" nowrap>&nbsp;</TD>
            </TR>
            <TR> 
              <TD align=left colSpan=6 height=20 vAlign=top nowrap> <DIV align=right></DIV>
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
			  </DIV></TD>
            </TR>
          </TBODY>
        </TABLE>
    </TD></TR>
  <TR>
    <TD height=8 vAlign=top width="100%">
      <HR>

      <TABLE align=center border=0 height=22 width="100%">
        <TBODY> 
        <TR vAlign=middle> 
          <TD height=25 width="8%" nowrap><%=(lStatusID == SETTConstant.TransactionStatus.SAVE)?"复核备注":"取消复核备注："%></TD>
          <TD height=25 vAlign=top width="18%" nowrap> 
            <INPUT class=box name="strCheckAbstract" value="<%=DataFormat.formatString(strCheckAbstract)%>" onfocus="nextfield='submitfunction';" maxlength="100"> 
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
              <TD height=25 width="7%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%> </TD>
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
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v036.jsp";
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
		document.frmV038.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.frmV038.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if(!validateFields(frmV038)) return;
	
	if (confirm("是否取消复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.frmV038.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.frmV038.submit();
	}
}

function doPrint()
{
<%	
	long lOBReturn=-1;
	String strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>	
	window.open( "../accountinfo/a919-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=SETTConstant.TransactionType.CONSIGNLOANRECEIVE%>&strSuccessPageURL='../accountinfo/a918-v.jsp'&strFailPageURL='../accountinfo/a918-v.jsp'&lReturn=<%=lOBReturn%>");
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
		document.location.href="<%=strContext%>/settlement/tran/loan/control/c013.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.CONSIGNLOANRECEIVE%>&strSuccessPageURL=../view/v037.jsp&strFailPageURL=../view/v037.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";//复核成功或者失败均返回链接查找页面
	<%
	}else 
	{%>
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v036.jsp";//匹配页面
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
firstFocus(document.frmV038.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV038");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV038.strCheckAbstract);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV038");     
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