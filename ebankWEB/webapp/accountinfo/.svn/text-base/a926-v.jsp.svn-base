<%--
 页面名称 ：v026.jsp
 页面功能 : 委托贷款发放——复核/取消复核显示页面
 作    者 ：Barry
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo" %>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
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
	//定义变量
	TransGrantLoanInfo info = null;
	LoanPayFormDetailInfo patforminfo = null;
	
	info=new TransGrantLoanInfo();
	patforminfo=new LoanPayFormDetailInfo();
	patforminfo = (LoanPayFormDetailInfo)request.getAttribute("LoanPayFormDetailInfo");
	info = (TransGrantLoanInfo)request.getAttribute("TransGrantLoanInfo");
	
	int nOrderByCode=-1;
	long lDesc  = -1 ;
	String strTemp=null;
	long lStatusID = -1;
	String strAction 				= null;								//操作代码
	strAction = (String)request.getAttribute("strAction");	
	
	strTemp = (String)request.getAttribute("nOrderByCode");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		nOrderByCode = Integer.valueOf(strTemp).intValue();
	}
	strTemp = (String)request.getAttribute("lDesc");
	if(strTemp != null && strTemp.length()>0)
	{
		lDesc = Long.valueOf(strTemp).longValue();
	}
	strTemp=(String)request.getAttribute("lStatusID");
	if (strTemp!=null && strTemp.length()>0){
		lStatusID=Long.valueOf(strTemp).longValue();
	}
	else{
		lStatusID=info.getStatusID();
	}
	
%>

<form name="frmV026"  method="post"> 
<input type="hidden" name="lID" value="<%=info.getID()%>">
<input type="hidden" name="tsInputDate" value="<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>">
<input type="hidden" name="tsModify" value="<%=info.getModify().toString()%>">
<input type="hidden" name="strTransNo" value="<%=info.getTransNo()%>">

<!--<input type="hidden" name="lLoanClientID" value="<%=NameRef.getClientIDByAccountID(info.getLoanAccountID())%>">
<input type="hidden" name="lLoanAccountID" value="<%=info.getLoanAccountID()%>">
<input type="hidden" name="lLoanContractID" value="<%=info.getLoanContractID()%>">
<input type="hidden" name="lLoanPayFormID" value="<%=info.getLoanNoteID()%>">

<input type="hidden" name="lPayInterestAccountID" value="<%=info.getPayInterestAccountID()%>">
<input type="hidden" name="lReceiveInterestAccountID" value="<%=info.getReceiveInterestAccountID()%>">
<input type="hidden" name="lCommissionAccountID" value="<%=info.getPayCommisionAccountID()%>">
<input type="hidden" name="lInterestRateID" value="<%=info.getInterestTaxRate()%>">
<input type="hidden" name="lDepositAccountID" value="<%=info.getDepositAccountID()%>">
<input type="hidden" name="lRemitOutBranchID" value="<%=info.getBankID()%>">
<input type="hidden" name="lPayTypeID" value="<%=info.getPayTypeID()%>">
<input type="hidden" name="lConsignDepositClientAccountID" value="<%=info.getConsignDepositAccountID()%>"> -->



<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="strAction"  type="hidden">
<input name="strSuccessPageURL"  type="hidden" value="../view/v025.jsp">
<input name="strFailPageURL"  type="hidden" value="../view/v026.jsp">

<input name="strContinueSave" type="hidden" value="false">
<input type="hidden" name="lTransactionTypeID" value="<%=SETTConstant.TransactionType.CONSIGNLOANGRANT%>">
<input type="hidden" name="lCheckUserID" value="<%=sessionMng.m_lUserID%>">

<input type="hidden" name="lStatusID" value="<%=lStatusID%>"> 
<%Log.print("v026状态:"+lStatusID);%>
<input type="hidden" name="nOrderByCode" value="<%=info.getOrderByType()>0?(""+info.getOrderByType()):""%>">
<input type="hidden" name="lDesc" value="<%=info.getAscOrDesc()>0?(""+info.getAscOrDesc()):""%>">

<TABLE border=0 class=top height=665 width="99%">
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>委托贷款发放 
      </B></TD>
  </TR>
  <TR>
    <TD height=9 vAlign=bottom width="65%">
      <TABLE align=center borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">委托贷款客户编号：</TD>
          <TD height=20 width="33%"><INPUT class=box maxLength=6 
            name="txtTrustLoanClientNoCtrl" size=10 value="<%=NameRef.getClientCodeByAccountID(info.getLoanAccountID())%>" disabled> </TD>
          <TD height=20 width="16%">委托贷款客户名称 ：</TD>
		  <TD height=20 width="34%">
                <textarea name="txtTrustLoanClientNameCtrl"  class="box" disabled bgcolor="#FF00"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getLoanAccountID())%></textarea>
            </TD>
		</TR>
        <TR borderColor=#E8E8E8>		
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtTrustLoanAccountNoCtrl","<%=NameRef.getAccountNoByID(info.getLoanAccountID())%>","委托贷款账号","width=17%","width=33%")
</script>				  
<!--*********************************************************************************-->			
          <TD height=20 width="16%">&nbsp;</TD>
          <TD height=20 width="34%"></TD>
		</TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">合同号：</TD>
          <TD height=20 width="33%"><INPUT class=box 
            name="txtContractNoCtrl" value="<%=NameRef.getContractNoByID(info.getLoanContractID())%>" disabled> </TD>
          <TD height=20 width="16%">放款通知单：</TD>
		    <TD height=20 width="34%"><INPUT class=box 
            name="txtLetoutRequisitionNoCtrl" value="<%=NameRef.getPayFormNoByID(info.getLoanNoteID())%>" disabled></TD>
		</TR>
		<TR borderColor=#E8E8E8>
			<TD height=20>
				是否后补记账处理:
			</TD>
			<TD  height=20>
				<input type='checkbox' <%=info.isKeepAccount()?"checked":""%> name='blnIsKeepAccount' disabled>
			</TD>
		</TR>
	  </TABLE>
	</TD>
  </TR>
  <TR>
    <TD height=20 vAlign=bottom width="100%">
      <TABLE border=0 width="100%">
        <TR class=top>
          <TD height=20 rowSpan=2 vAlign=bottom width="35%">
            <TABLE align=right border=1 borderColor=#999999 height="100%" 
            width="96%">
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=23 vAlign=middle><U>委托贷款详细资料 
                  </U></TD>
                <TD borderColor=#E8E8E8 height=23 vAlign=top 
                width="60%">&nbsp;</TD>
			  </TR>	
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=20 vAlign=middle>起始日期：</TD>
                <TD height=20 vAlign=top width="60%"><INPUT 
                  class=box disabled name="txtDateStart" value="<%=DataFormat.formatDate(patforminfo.getStart())%>"> 
                  </TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=27 vAlign=middle>到期日期：</TD>
                <TD height=27 vAlign=top width="60%"><INPUT 
                  class=box disabled name="txtDateEnd" value="<%=DataFormat.formatDate(patforminfo.getEnd())%>"> 
                  </TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=27 vAlign=middle>类型:</TD>
                <TD height=27 vAlign=top width="60%">
				<%
				long[] lLoanTypeArray = LOANConstant.LoanType.getAllCode();
				%>
				<select class=box name="slcLoanType" disabled>
					<option value="-1" <%=(patforminfo.getLoanType() == -1 ? "selected" : "")%>></option>
					<%
					for (int i=0; i < lLoanTypeArray.length; i++)
					{
					%>
					<option value="<%=lLoanTypeArray[i]%>" <%=(patforminfo.getLoanType() == lLoanTypeArray[i] ? "selected" : "")%>><%=LOANConstant.LoanType.getName(lLoanTypeArray[i])%></option>
					<%
					}
					%>
				</select>
                 </TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=20 vAlign=middle>委托方：</TD>
                <TD height=20 vAlign=top width="60%">
				<input type='text' name='txtConsigner' value="<%=DataFormat.formatString(patforminfo.getClientCode())%>" disabled>
				</TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD colSpan=2 height=20 vAlign=middle>&nbsp;</TD>
                <TD height=20 vAlign=top width="60%">&nbsp;</TD>
			  </TR>
		  </TABLE></TD>
          <TD height=9 vAlign=bottom width="65%">
            <TABLE align=left border=1 borderColor=#999999 height="100%" 
            width="98%">
              <TR borderColor=#E8E8E8>
                <TD height=23 width="25%" colspan='2'><U>利息详细资料</U></TD>
                <TD borderColor=#E8E8E8 height=23 width="36%">&nbsp;</TD>
                <TD borderColor=#E8E8E8 height=23 width="14%">&nbsp;</TD>
                <TD borderColor=#E8E8E8 height=23 width="25%">&nbsp;</TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 width="25%" colspan='2'>利率：</TD>
				<input type="hidden" name="txtRate" value="<%=patforminfo.getInterestRate()%>">
                <TD height=20 width="36%">
				  <INPUT class=tar name="txtRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getInterestRate(),'0',6)%>" disabled>% 
				</TD>
                <TD height=20 width="14%">&nbsp;</TD>
                <TD height=20 width="25%">&nbsp;</TD>
			  </TR>
              <TR borderColor=#E8E8E8>
              <td>
              	<input type='checkbox' disabled name='chkPayInterestAccount' <%if (info.getPayInterestAccountID()>0) out.print("checked");%> value='1' width='10%' onFocus=nextfield='lPayInterestAccountIDCtrlCtrl3';>
              </td>
              
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("lPayInterestAccountID","<%=NameRef.getAccountNoByID(info.getPayInterestAccountID())%>","贷款方付息账户","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->	
                   <TD height=20 width="14%">客户名称 :</TD>
                <TD height=20 width="25%">
                <textarea name="txtPayInterestAccountName"  class="box" disabled bgcolor="#FF00"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getPayInterestAccountID())%></textarea>
                  
                  </TD>
			  </TR>
			  <tr borderColor=#E8E8E8>
	              <td>
              	<input type='checkbox' disabled value='1' name='chkReceiveInterestAccount' <%if(info.getReceiveInterestAccountID()>0)out.print("checked");%> width='10%' onFocus=nextfield='lReceiveInterestAccountIDCtrlCtrl3';>
              </td>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("lReceiveInterestAccountID","<%=NameRef.getAccountNoByID(info.getReceiveInterestAccountID())%>","委托方收息账户","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->	
                   <TD height=20 width="14%">客户名称 :</TD>
                <TD height=20 width="25%">
                <textarea name="txtReceiveInterestAccountName"  class="box" disabled   rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getReceiveInterestAccountID())%></textarea>
                  
                  </TD>
			  </tr>
		  </TABLE>
		  </TD>
		</TR>
        <TR class=top>
          <TD height=9 vAlign=bottom width="65%">
            <TABLE align=left border=1 borderColor=#999999 height="100%" 
            width="98%">
              <TR borderColor=#E8E8E8>
                <TD colSpan=5 height=23 vAlign=middle><U>费用详细资料</U></TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">
				  <INPUT name="ckbCommissionRateCtrl" <%=(patforminfo.getPoundage() > 0 ? "checked" : "" )%> type="checkbox" disabled> 
				</TD>
                <TD borderColor=#E8E8E8 height=20 vAlign=middle 
                  width="21%">手续费率:</TD>
                <TD height=20 vAlign=top width="35%">
				 <input type="hidden" name="txtSuretyRate" value="<%=patforminfo.getPoundage()%>"> 
				<INPUT class=box  dir="rtl" name="txtSuretyRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getPoundage(),'0',6)%>" disabled >%</TD>
                <TD height=20 vAlign=top width="14%">手续费收取方式</TD>
                <TD height=20 vAlign=top width="25%">
                	<input type='text'  class='box' disabled value='<%=LOANConstant.ChargeRatePayType.getName(patforminfo.getChargeRateTypeID())%>'>
                </TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD borderColor=#E8E8E8 height=20 vAlign=middle 
                width="5%"><input type='checkbox' disabled value='1' <%if (info.getPayCommisionAccountID()>0)out.print("checked");%> name='chkCommissionAccountID' onfocus=nextfield='lCommissionAccountIDCtrlCtrl3';></TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("lCommissionAccountID","<%=NameRef.getAccountNoByID(info.getPayCommisionAccountID())%>","支付手续费账号","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->	

         <TD height=25 width="14%">客户名称 :</TD>
                <TD height=25 width="25%">
                <textarea name="txtCommissionAccountName" class="box" disabled  rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getPayCommisionAccountID())%></textarea>
                  
                  </TD>
			  </TR>
              <TR borderColor=#E8E8E8>
                <TD borderColor=#E8E8E8 height=20 vAlign=middle 
                width="5%"><input type='checkbox' disabled value='1' <%if (info.getInterestTaxRate()>0)out.print("checked");%> name='chkInterestRateID' onfocus=nextfield='lInterestRateIDCtrl';></TD>
                <td>
                	利息税费率:
                </td>
                <td>
                	<input type='text' name='txtInterestTaxRate' dir="rtl" class="box" disabled value='<%=info.getInterestTaxRate()>0?DataFormat.formatTax(info.getInterestTaxRate(),'0',6):""%>' >%
                </td>
                <!--<TD height=25 width="14%">生效日期 :</TD>
                <TD height=25 width="25%">
				<input type='text' name='tsValidDate' disabled value='<%if (info.getInterestTaxRateVauleDate()!=null) out.print(info.getInterestTaxRateVauleDate().toString().substring(0,10));%>' maxlength='16' class="box" onFocus=nextfield='radio1[0]';>
                 </TD>-->
			  </TR>
			</TABLE>
		  </TD>
		</TR>
	  </TABLE>
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>委托贷款放款账户详细资料</U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
		    <INPUT name="radio1" disabled type=radio value="1" <%=(info.getDepositAccountID()>0)?"checked":""%> onfocus="nextfield='lDepositAccountIDCtrlCtrl3'"> 
		</TD>
		
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("lDepositAccountID","<%=NameRef.getAccountNoByID(info.getDepositAccountID())%>","贷款发放至活期账号","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->	

          <TD height=20 width="14%">活期客户名称:</TD>
          <TD height=20 width="40%">
                <textarea class="box" name="txtGrantCurrentAccountName" disabled bgcolor="#FF00"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getDepositAccountID())%></textarea>        
            </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
		    <INPUT name="radio1" disabled type=radio value="2" <%=(info.getPayTypeID()>0)?"checked":""%> onfocus="nextfield='lPayTypeID';"> </TD>
          <TD borderColor=#E8E8E8 height=20 width="20%">放款方式：</TD>
          <TD height=20 width="23%">
		  <%
		  	SETTConstant.PayType.showList(out,"lPayTypeID",0,-1,false,true," disabled onfocus=\"nextfield='lRemitOutBranchIDCtrl';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>
		  </TD>
          <TD height=20 width="14%">&nbsp;</TD>
          <TD height=20 width="40%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
		   <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
		   <TD height=20 width="18%">汇出行名称：</TD>
<td>
		   <textarea class=box disabled 
            name="txtRemitOutBankIDCtrl" rows=2 cols=30><%=NameRef.getBankNameByID(info.getBankID())%></textarea> </td>

          <TD height=20 width="14%">&nbsp;</TD>
          <TD height=20 width="40%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"></TD>
          
          <td height="18" bordercolor="#E8E8E8" width="18%">汇入收款单位账号：</td>
		  <td height="18" width="28%"> 
            <input type="text" name="txtRemitInAccountNoCtrl" value="<%=(info.getExtAcctName()!=null && info.getExtAcctName().length()>0)?info.getExtAcctName():""%>" disabled size="18" class="box">
            </td>
          <TD height=20 width="14%">汇入收款单位户名:</TD>
          
          <TD height=18 width="36%"><textarea class=box disabled 
            name="txtRemitInAccountNameCtrl" rows=2 cols=30><%=(info.getExtAcctName()!=null && info.getExtAcctName().length()>0)?info.getExtAcctName():""%></textarea> </TD>
        </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="20%">汇入地(省)：</TD>
          
          <TD height=20 width="28%"><INPUT class=box disabled 
            name="txtInProvinceCtrl" value="<%=(info.getProvince()!=null && info.getProvince().length()>0)?info.getProvince():""%>" size=24> </TD>

          <TD height=20 width="14%">汇入地(市)：</TD>
          <TD height=20 width="40%"><INPUT class=box disabled
            name="strCity" value="<%=(info.getCity()!=null && info.getCity().length()>0)?info.getCity():""%>" size=24  onfocus="nextfield='lRemitInBankIDCtrl';"  maxlength="100"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入行名称：</TD>
          <TD height=20 width="28%"><textarea class=box disabled rows=2 cols=30
            name="txtRemitInBankCtrl"><%=(info.getBankName()!=null && info.getBankName().length()>0)?info.getBankName():""%></textarea> </TD>
          <TD height=20 width="14%">&nbsp;</TD>
          <TD height=20 width="40%">&nbsp;</TD></TR>
        <tr borderColor=#E8E8E8>
        <td>&nbsp;</td>
        
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("lConsignDepositClientAccountID","<%=NameRef.getAccountNoByID(info.getConsignDepositAccountID())%>","委托存款账号","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->	

<TD height=20 width="14%">委托存款客户名称:</TD>
          <TD height=20 width="40%">
                <textarea class="box" name="txtConsignDepositClientName" disabled   rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getConsignDepositAccountID())%></textarea>        
            </TD>
        </tr>
</TABLE></TD></TR>
  <TR>
    <TD height=142 vAlign=top width="100%">
      <TABLE align=center height=15 width="97%">
        
        <TR vAlign=middle>
          <TD height=20 width="10%">金额：</TD>
		  <input type="hidden" name="dAmount" value="<%=patforminfo.getAmount()%>">
          <TD height=20 width="20%"><%=sessionMng.m_strCurrencySymbol%>
		    <INPUT class=tar name="txtMoneyCtrl" value="<%=DataFormat.formatDisabledAmount(patforminfo.getAmount())%>" disabled >
		  </TD>
          <TD height=20 width="10%">起息日：</TD>
          <input type='hidden' name='tsInterestStart' value="<%=patforminfo.getInterestStart()!=null ?patforminfo.getInterestStart().toString().substring(0,10):""%>">
          <TD height=20 width="25%"><INPUT class=box disabled 
            name="tsInterestStartCtrl" value="<%=patforminfo.getInterestStart()!=null ?patforminfo.getInterestStart().toString().substring(0,10):""%>" maxlength="100"> </TD>
          <TD height=20 width="10%">执行日：</TD>
          <TD height=20 width="25%"><INPUT type="text" class="box" name="tsExecute" value="<%=DataFormat.getDateString(info.getExecute())%>" onfocus="nextfield ='txtNote';" > </TD></TR>
        <TR vAlign=middle>
          <TD height=30 width="10%">交易号：</TD>
          <TD height=30 width="20%"><INPUT class=box name="txtTransactionNo" value="<%=info.getTransNo()%>" disabled> </TD>
		  <TD>
		  摘要:
		  </TD>
		  <TD>
		  <input type='text' class='box' disabled name='abstract' value='<%=(info.getAbstract()!=null && info.getAbstract().length()>0)?info.getAbstract():""%>'>
		  </TD>
						 
          <TD height=20 width="10%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD>
          <TD height=20 width="10%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD></TR>
        <TR vAlign=middle>
          <TD borderColor=#E8E8E8 height=30>&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=30>&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=30></TD>
          <TD borderColor=#E8E8E8 height=30></TD>
          <TD height=20 width="10%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD></TR>
        <TR>
          <TD align=left colSpan=6 height=20 vAlign=top>
            <DIV align=right>
<%
	if("Query".equalsIgnoreCase(strAction))
	{
%>
	            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
<%
	}
	else
	{
%>
			<%
			if (lStatusID == SETTConstant.TransactionStatus.SAVE)
			{
			%>
			<INPUT class=button name=Submit3 onclick="doCheck(frmV026);" type=button value=" 复 核 "> 
			<%
			}
			else if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV026);" type=button value=" 取消复核 "> 			
			<%
			}
			%>
            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
			<INPUT class=button name=Submit322 onclick="doBack(frmV026);" type=button value=" 返 回 "> 
<%
	}
%>
			</DIV></TD></TR></TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=26 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TR vAlign=middle>
          <TD height=25 width="8%">复核备注:</TD>
          <%
          String strAbstract= info.getCheckAbstract();		//取得复核备注
          %>
          <TD height=25 vAlign=top width="19%"><INPUT class=box 
            name="strCheckAbstract" size="40" value="<%=(strAbstract!=null && strAbstract.length()>0)?strAbstract:""%>" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">录入人:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(info.getInputUserID())%></TD>
          <TD height=25 width="8%">录入日期:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(info.getInputDate())%></TD>
          <TD height=25 width="6%">复核人:</TD>
          <TD height=25 width="7%"><%=NameRef.getUserNameByID(info.getCheckUserID())%></TD>
          <TD height=25 width="8%">复核日期:</TD>
          <TD height=25 width="9%"><%=(info.getCheckUserID() > 0 ? DataFormat.formatDate(info.getExecute()) : "&nbsp;")%></TD>
          <TD height=25 width="5%">状态:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR>
  </TABLE>
</form>	
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<script language="JavaScript">
firstFocus(document.frmV026.strCheckAbstract);
//setSubmitFunction("<%=(lStatusID == SETTConstant.TransactionStatus.SAVE ? "doCheck" : "doCancelCheck")%>(frmV026);");
setFormName("frmV026"); 
</script>
<%
	}
%>
<script language="JavaScript">
function doBack(form)
{
	if (confirm("是否返回？"))
	{
		<%
		if (lStatusID == SETTConstant.TransactionStatus.SAVE)
		{
		%>
		form.action = "../view/v025.jsp";
		<%
		}
		else
		{
		%>
		form.action = "../control/c002.jsp";
		form.strSuccessPageURL.value = '../view/v027.jsp';
		form.strFailPageURL.value = '../view/v026.jsp';
		<%
		}
		%>
		showSending();
		form.submit();
	}	
}
function print(){
<%	
	long lOBReturn=-1;
	String strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>	
	if (confirm("是否打印?")){
		window.open( "../accountinfo/a915-v.jsp?lID=<%=info.getID()%>&lTransactionTypeID=<%=SETTConstant.TransactionType.CONSIGNLOANGRANT%>&strSuccessPageURL='../accountinfo/a926-v.jsp'&strFailPageURL='../accountinfo/a926-v.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v025.jsp';
	form.strFailPageURL.value = '../view/v026.jsp';
	if (confirm("是否复核？"))
	{
		form.action = "../control/c004.jsp";
		showSending();
		form.submit();
	}
}
function doCancelCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CANCELCHECK%>';	
	form.strSuccessPageURL.value = '../view/v027.jsp';
	form.strFailPageURL.value = '../view/v026.jsp';
	
	if (!validateFields(form))
	{
		return false;
	}
	if (confirm("是否取消复核？"))
	{
		form.action = "../control/c004.jsp";
		showSending();
		form.submit();
	}
}
function allFields()
{
	this.aa = new Array("strCheckAbstract","复核备注","string",1);
}
</script>
<%
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>