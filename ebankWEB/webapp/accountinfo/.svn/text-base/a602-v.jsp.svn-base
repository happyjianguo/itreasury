
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
try
{
	Log.print("*******进入页面--a602-v.jsp*******");
	//判断是否登录
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
	
	patforminfo = (LoanPayFormDetailInfo)request.getAttribute("LoanPayFormDetailInfo");
	info = (TransGrantLoanInfo)request.getAttribute("TransGrantLoanInfo");
	//patforminfo = new LoanPayFormDetailInfo();
	//info = new TransGrantLoanInfo();
	
	//定义变量
	long lStatusID = -1;
	int  nOrderByCode = -1;
	long lDesc = -1;
	String strAction 				= null;								//操作代码
	strAction = (String)request.getAttribute("strAction");	
	
	//定义变量
	long lPayInterestAccountID = -1;//信托-结息账户
	long lPaySuretyFeeAccountID = -1;//信托-贷款方账号
	long lReceiveSuretyFeeAccountID = -1;//信托-担保方账号
	
	long lDepositAccountID = -1;//贷款发放至活期账户
	
	long lPayTypeID = -1;//付款方式
	long lBankID = -1;//汇出行
	String strExtAcctNo = "";//汇入收款单位账号
	String strExtAcctName = "";//汇入收款单位户名
	String strProvince = "";//汇入地(省)
	String strCity = "";//汇入地(市)
	String strBankName = "";//汇入行
	String strExtBankNo = ""; //提入行
	
	long lAbstractID = -1;//摘要ID
	String strAbstract = "";//摘要描述
	String strCheckAbstract = "";//复核摘要
	
	double dAmount = 0.0;//金额
	java.sql.Timestamp tsExecute = null;//执行日
	java.sql.Timestamp tsInterestStart = null;//起息日
	java.sql.Timestamp tsModify = null;//访问日期
	String strTransNo = "";
	
	//从查询后的info中得到值
	lPayInterestAccountID = info.getPayInterestAccountID();//信托-结息账户
	lPaySuretyFeeAccountID = info.getPaySuretyFeeAccountID();//信托-贷款方账号
	lReceiveSuretyFeeAccountID = info.getReceiveSuretyFeeAccountID();//信托-担保方账号
		
	lDepositAccountID = info.getDepositAccountID();//贷款发放至活期账户
		
	lPayTypeID = info.getPayTypeID();//付款方式
	lBankID = info.getBankID();//汇出行
	tsModify = info.getModify();
	
	if(info.getExtAcctNo() != null)
	{
		strExtAcctNo = info.getExtAcctNo();//汇入收款单位账号
	}
	if(info.getExtAcctName() != null)
	{
		strExtAcctName = info.getExtAcctName();//汇入收款单位户名
	}
	if(info.getProvince() != null)
	{
		strProvince = info.getProvince();//汇入地(省)
	}
	if(info.getCity() != null)
	{
		strCity = info.getCity();//汇入地(市)
	}
	if(info.getBankName() != null)
	{
		strBankName = info.getBankName();//汇入行
	}
	if(info.getExtBankNo() != null)
	{
		strExtBankNo = info.getExtBankNo();//提入行
	}
	
	if(info.getTransNo() != null)
	{
		strTransNo = info.getTransNo();//交易号
	}
	lAbstractID = info.getAbstractID();//摘要ID
	if(info.getAbstract() != null)
	{
		strAbstract = info.getAbstract();//摘要描述
	}
	if(info.getCheckAbstract() != null)
	{
		strCheckAbstract = info.getCheckAbstract();//复核摘要描述
	}	
	
	dAmount = info.getAmount();//金额
	tsExecute = info.getExecute();//执行日
	tsInterestStart = info.getInterestStart();//起息日
	tsModify = info.getModify();//访问日期
	
	//读取数据
	String strTemp = null;
	strTemp = (String)request.getAttribute("lStatusID");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		lStatusID = Long.valueOf(strTemp).longValue();
	}
	else{
		lStatusID=info.getStatusID();
	}
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
%>
<form name="frmv066" method="post">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="strAction"  type="hidden">
<input name="strSuccessPageURL"  type="hidden">
<input name="strFailPageURL"  type="hidden">

<input type="hidden" name="lTransactionTypeID" value="<%=SETTConstant.TransactionType.BANKGROUPLOANGRANT%>">
<input type="hidden" name="lID" value="<%=info.getID()%>">
<input type="hidden" name="tsModify" value="<%=tsModify.toString()%>">
<input type="hidden" name="lStatusID" value="<%=lStatusID%>"> 

<input type="hidden" name="nOrderByCode" value="<%=nOrderByCode%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="lCheckUserID" value="<%=sessionMng.m_lUserID%>">
<TABLE border=0 class=top height=290 width="99%">
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=2 width="100%"><B>银团贷款发放</B></TD></TR>
  <TR>
    <TD colSpan=2 height=20 vAlign=top width="100%">
      <TABLE align=center borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%">银团贷款客户编号：</TD>
          <TD height=20 vAlign=top width="33%"><INPUT class=box 
            disabled maxLength=10 name="txtTrustLoanClientNoCtrl" size=10 value="<%=NameRef.getClientCodeByAccountID(info.getLoanAccountID())%>"> 
            </TD>
          <TD height=20 width="16%">银团贷款客户名称 ：</TD>
          <TD height=20 width="34%"><textarea class=box disabled 
            name="txtTrustLoanClientNameCtrl" rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getLoanAccountID())%></textarea> </TD></TR>
        <TR borderColor=#E8E8E8>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtTrustLoanAccountNoCtrl","<%=NameRef.getAccountNoByID(info.getLoanAccountID())%>","银团贷款账号","width=17%","width=33%")
</script>				  
<!--*********************************************************************************-->			
          <TD height=20 width="16%">&nbsp;</TD>
          <TD height=20 width="34%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">合同号：</TD>
          <TD height=20 width="33%"><INPUT class=box disabled 
            name="txtContractNoCtrl" value="<%=NameRef.getContractNoByID(info.getLoanContractID())%>"> </TD>
          <TD height=20 width="16%">放款通知单：</TD>
          <TD height=20 width="34%"><INPUT class=box 
            name="txtLetoutRequisitionNoCtrl" value="<%=NameRef.getPayFormNoByID(info.getLoanNoteID())%>" disabled></TD></TR></TABLE></TD></TR>
  <TR>
    <TD height=20 rowSpan=2 vAlign=bottom width="35%">
      <TABLE align=right border=1 borderColor=#999999 height="100%" 
        width="96%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=23 vAlign=middle><U>银团贷款详细资料 
            </U></TD>
          <TD borderColor=#E8E8E8 height=23 vAlign=top width="60%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>起始日期：</TD>
          <TD height=20 vAlign=top width="60%"><INPUT class=box 
            disabled name="txtDateStartCtrl" value="<%=DataFormat.formatDate(patforminfo.getStart())%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=27 vAlign=middle>到期日期：</TD>
          <TD height=27 vAlign=top width="60%"><INPUT class=box 
            disabled name="txtDateEndCtrl" value="<%=DataFormat.formatDate(patforminfo.getEnd())%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=27 vAlign=middle>贷款种类:</TD>
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
 		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>贷款期限：</TD>
          <TD height=20 vAlign=top width="60%">
		  <%
					SETTHTML.showLoanTermListCtrl(out,"slcLoanInterval",patforminfo.getLoanTerm(),true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
				%>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>&nbsp;</TD>
          <TD height=20 vAlign=top width="60%">&nbsp;</TD></TR></TABLE></TD>
    <TD height=9 vAlign=bottom width="65%">
      <TABLE align=left border=1 borderColor=#999999 height="100%" 
        width="98%">
        <TR borderColor=#E8E8E8>
          <TD height=23 width="20%"><U>利息详细资料 </U></TD>
          <TD borderColor=#E8E8E8 height=23 width="39%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=23 width="13%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=23 width="28%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=25 width="20%">利率：</TD>
          <TD height=25 width="39%"><INPUT class=tar disabled 
            name="txtRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getInterestRate(),'0',6)%>%"> </TD>
          <TD height=25 width="13%">&nbsp;</TD>
          <TD height=25 width="28%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtInterestAccountIDNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","结息账户","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->			
          <TD height=25 width="13%">客户名称 :</TD>
		  <TD height=25 width="28%"><textarea class=box disabled 
            name="txtInterestAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPayInterestAccountID )%></textarea> 
			<!--<img src="/graphics/but-all.GIF"  border=0 align=absmiddle  alt="华能财务">-->
      </TD></TR></TABLE></TD></TR>
  <TR>
    <TD height=9 vAlign=bottom width="65%">
      <TABLE align=left border=1 borderColor=#999999 height="100%" 
        width="98%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=23 vAlign=middle><U>费用详细资料</U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">
		  <INPUT  disabled name=checkbox type=checkbox <%=(patforminfo.getAssureRate() > 0 ? "checked" : "" )%> value=checkbox> </TD>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="20%">担保费率:</TD>
          <TD height=20 vAlign=top width="34%">
		    <INPUT class=tar disabled name="txtSuretyRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getAssureRate(),'0',6)%>%"> </TD>
          <TD height=20 vAlign=top width="13%">&nbsp;</TD>
          <TD height=20 vAlign=top width="28%">&nbsp;</TD>
		  </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">&nbsp;</TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtDebtorAccountIDCtrl","<%=NameRef.getAccountNoByID(lPaySuretyFeeAccountID)%>","贷款方账号","width=20%","width=34%")
</script>				  
<!--*********************************************************************************-->			  
          <TD height=25 width="13%">客户名称 :</TD>
          <TD height=25 width="28%">
		    <textarea class=box disabled name="txtDebtorAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPaySuretyFeeAccountID )%></textarea>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">&nbsp;</TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtSuretyAccountIDCtrl","<%=NameRef.getAccountNoByID(lReceiveSuretyFeeAccountID)%>","担保方账号","width=20%","width=34%")
</script>				  
<!--*********************************************************************************-->			  
          <TD height=25 width="13%">客户名称 :</TD>
          <TD height=25 width="28%">
		    <textarea class=box disabled name="txtSuretyAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lReceiveSuretyFeeAccountID)%></textarea>
      </TD></TR>
	  <TR borderColor=#E8E8E8>
			  <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">&nbsp;
						</TD>
				  <TD borderColor=#E8E8E8 height=20 vAlign=middle 
	                  width="21%">代理费率:</TD>
			  	<TD height=20 vAlign=top width="35%">
				 <input type="hidden" name="txtSuretyRate" value="<%=patforminfo.getCommissionRate()%>"> 
				<INPUT class=tar name="txtSuretyRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getCommissionRate(),'0',6)%>" disabled >‰</TD>
                <TD height=20 vAlign=top width="14%">&nbsp;</TD>
		 </TR>
	  </TABLE></TD></TR>
  <tr>
   	<TD colSpan=2 height=62 vAlign=top width="100%">
	 <TABLE align=center border=0 borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>提款通知单明细表</U></TD>
		</TR>
		 <TR borderColor=#E8E8E8>  
		 	<TD colSpan=5 height=30>
		 	 	<table width="100%" border="1" align="center"  class="ItemList">
		  				 <tr > 	
		  					<td>
			  					<tr class="tableHeader"> 
									<td class="ItemTitle" nowrap colspan="3" height="22"> 
							            <div align="center">贷款人资料</div>
							          </td>
									 <td class="ItemTitle" nowrap rowspan="2" height="22"> 
						            <div align="center">本次提款金额</div>
						          </td>
						          <td class="ItemTitle" nowrap rowspan="2" height="22"> 
						            <div align="center">本次应付代理费</div>
						          </td>	 
								</tr> 
							    <tr class="tableHeader"> 
							         <td class="ItemTitle" nowrap height="22"> 
							            <div align="center">银行名称</div>
							          </td>
							          <td class="ItemTitle" nowrap height="22"> 
							            <div align="center">承贷金额</div>
							          </td>
							          <td class="ItemTitle" nowrap height="22"> 
							            <div align="center">承贷比例（%）</div>
							          </td>
								 </tr>						 						
				<%
			Collection resultColl = (Collection)request.getAttribute("searchBankLoanDrawResults");

			Iterator itResult = null;
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			String strBankName1="";                 //银行名称
			String strLoanAmount="";               //承贷金额 
			String strLoanRate ="";                //承贷比例 
			String strDrawAmount="";               //本次提款金额
			String strCommission="";              //本次应付代理费
			double dCommissionSum=0.0;            //本次代理费合计
			double dDrawAmountSum=0.0;            //本次提款金额合计
			
		if(itResult != null && itResult.hasNext())
		{
			while(itResult.hasNext())
			{		
			
				BankLoanDrawInfo obj = new BankLoanDrawInfo();		
				obj = (BankLoanDrawInfo)itResult.next();
				strBankName1 = 	 DataFormat.formatEmptyString(obj.getBankName());
				strLoanAmount = DataFormat.formatListAmount(obj.getLoanAmount());
				strLoanRate = 	DataFormat.formatDisabledAmount(obj.getRate(),2) + "%";
				strDrawAmount = DataFormat.formatListAmount(obj.getDrawAmount());
				if(obj.getIsHead()!=1)
				{
					strCommission = DataFormat.formatListAmount(obj.getCommission());
					dCommissionSum = dCommissionSum+obj.getCommission();
				}
				dDrawAmountSum = dDrawAmountSum+obj.getDrawAmount();
				
				
		%>
				<tr> 
	          <td class="ItemBody" nowrap height="21"> 
	            <div align="center"><%=strBankName1%></div>
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
	            <div align="center"><%=strLoanAmount%></div>
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
	              <div align="center"> <%=strLoanRate%></div>
	          </td>
	          <td class="ItemBody" nowrap height="21"> 
	            <div align="center"> <%=strDrawAmount%></div> 
	          </td>  
	          <td class="ItemBody" nowrap height="21"> 
	            <div align="center"> <%=strCommission%></div> 
	          </td>  
	        </tr>
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
			<TR borderColor=#999999>
					<TD class=SearchBar colSpan=10 height=46>
					<TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar width="200%">
						<TBODY>
						<TR>
							<TD height=14 colSpan=5>
								<B><B>本次提款金额总计:</B></B> <%=sessionMng.m_strCurrencySymbol%>
								<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatListAmount(dDrawAmountSum+dCommissionSum)%>" disabled >
							</TD>
						</TR>
						<TR>
							<TD height=14 colSpan=5>
							<B><B>本次应付代理费总计:</B></B> <%=sessionMng.m_strCurrencySymbol%>
							<INPUT type="text" class=tar name=dAllAmount size=20 value="<%=DataFormat.formatListAmount(dCommissionSum)%>" disabled >
							</TD>
						</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</table>
			</TD>
		  </TR>
		</TABLE>
	 </td> 
  </tr>
  <TR>
    <TD colSpan=2 height=62 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>银团贷款放款账户详细资料 
        </U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
		    <INPUT disabled name="radio1" type=radio value="1" <%=((lDepositAccountID > 0 || lPayTypeID < 0) ? "checked" : "")%>> 
            </TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtGrantCurrentAccountIDCtrl","<%=NameRef.getAccountNoByID(lDepositAccountID)%>","贷款发放至活期账号","width=18%","width=28%")
</script>				  
<!--*********************************************************************************-->				
          <TD height=20 width="15%">活期客户名称:</TD>
          <TD height=20 width="36%"><textarea class=box disabled 
            name="txtGrantCurrentAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lDepositAccountID)%></textarea>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"><INPUT 
            disabled name="radio1" <%=(lPayTypeID > 0 ? "checked" : "")%> type=radio value="2" > </TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">放款方式：</TD>
          <TD height=20 width="28%">
		  <%
		  	SETTConstant.PayType.showList(out,"lPayTypeID",1,-1,false,true,"disabled onfocus=\"nextfield='lRemitOutBranchIDCtrl';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>

		  </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">汇出行名称：</TD>
          <TD height=20 width="28%"><textarea class=box disabled 
            name="txtRemitOutBankIDCtrl" rows=2 cols=30><%=NameRef.getBankNameByID(lBankID)%></textarea> 
			<!--<img src="/graphics/but-all.GIF"  border=0 align=absmiddle  alt=""> --></TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=18 width="3%"></TD>
		  <td height="18" bordercolor="#E8E8E8" width="18%">汇入收款单位账号：</td>
		  <td height="18" width="28%"> 
            <input type="text" name="txtRemitInAccountNoCtrl" value="<%=strExtAcctNo%>" disabled size="18" class="box">
            </td>
          <TD height=18 width="15%">汇入收款单位户名:</TD>
          <TD height=18 width="36%"><textarea class=box disabled 
            name="txtRemitInAccountNameCtrl" rows=2 cols=30><%=strExtAcctName%></textarea> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">汇入地(省)：</TD>
          <TD height=20 width="28%"><INPUT class=box disabled 
            name="txtInProvinceCtrl" value="<%=strProvince%>" size=24> </TD>
          <TD height=20 width="15%">汇入地(市)：</TD>
          <TD height=20 width="36%"><INPUT class=box disabled 
            name="txtInCityCtrl" value="<%=strCity%>"  size=24> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入行名称：</TD>
          <TD height=20 width="28%"><textarea class=box disabled rows=2 cols=30
            name="txtRemitInBankCtrl"><%=strBankName%></textarea> </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD>
</TR>
         </TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=66 vAlign=top width="100%">
      <TABLE align=center height=2 width="97%">
        <TR vAlign=middle>
          <TD height=32 width="10%">金 额：</TD>
          <TD height=32 width="20%"><%=sessionMng.m_strCurrencySymbol%> <INPUT class=tar disabled 
            name="txtMoneyCtrl" size=17 value="<%=DataFormat.formatDisabledAmount(dAmount)%>"> </TD>
          <TD height=32 width="10%">起息日：</TD>
          <TD height=32 width="25%"><INPUT class=box disabled 
            name="txtDateInterestStartCtrl" value="<%=DataFormat.formatDate(tsInterestStart)%>"> </TD>
          <TD height=32 width="10%">执行日：</TD>
          <TD height=32 width="25%"><INPUT type="text" disabled class="box"
            name="txtDateExecuteCtrl" value="<%=DataFormat.formatDate(tsExecute)%>"> </TD></TR>
        <TR>
          <TD align=left height=20 vAlign=middle width="10%">交易号： </TD>
          <TD align=left height=20 vAlign=middle width="20%"><INPUT class=box disabled name="txtTransactionNoCtrl" 
            value="<%=strTransNo%>"> </TD>
          <TD align=left height=20 vAlign=middle width="10%">摘 要：</TD>
          <TD align=left height=20 vAlign=middle width="25%"><INPUT class=box disabled maxLength=2000 name="txtNoteCtrl" 
            size=25 value="<%=strAbstract%>"> </TD>
          <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="25%">&nbsp;</TD></TR>
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
			<INPUT class=button name=Submit3 onclick="doCheck(frmv066);" type=button value=" 复 核 "> 
			<%
			}
			else if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmv066);" type=button value=" 取消复核 "> 			
			<%
			}
			%>
            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
			<INPUT class=button name=Submit322 onclick="doBack(frmv066);" type=button value=" 返 回 "> 
<%
	}
%>
            </DIV></TD></TR></TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=20 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TR vAlign=middle>
          <TD height=25 width="8%">复核备注:</TD>
          <TD height=25 vAlign=top width="19%"><INPUT class=box 
            name="strCheckAbstract" value="<%=strCheckAbstract%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
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
firstFocus(document.frmv066.strCheckAbstract);
//setSubmitFunction("<%=(lStatusID == SETTConstant.TransactionStatus.SAVE ? "doCheck" : "doCancelCheck")%>(frmv066);");
setFormName("frmv066"); 
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
		form.action = "../view/v065.jsp";
		<%
		}
		else
		{
		%>
		form.action = "../control/c022.jsp";
		form.strSuccessPageURL.value = '../view/v067.jsp';
		form.strFailPageURL.value = '../view/v066.jsp';
		<%
		}
		%>
		showSending();
		form.submit();
	}	
}
function print(){
	if (confirm("是否打印?")){
		window.open( "a605-v.jsp?lID=<%=info.getID()%>&lTransactionTypeID=<%=SETTConstant.TransactionType.BANKGROUPLOANGRANT%>&strTransNo=<%=strTransNo%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v065.jsp';
	form.strFailPageURL.value = '../view/v066.jsp';
	
	if (confirm("是否复核？"))
	{
		form.action = "../control/c024.jsp";
		showSending();
		form.submit();
	}
}
function doCancelCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CANCELCHECK%>';	
	form.strSuccessPageURL.value = '../view/v067.jsp';
	form.strFailPageURL.value = '../view/v066.jsp';
	
	if (!validateFields(form))
	{
		return false;
	}
	if (confirm("是否取消复核？"))
	{
		form.action = "../control/c024.jsp";
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
OBHtml.showOBHomeEnd(out);
}catch( Exception exp ){
	exp.printStackTrace();
	Log.print(exp.getMessage());
}

%>

<%@ include file="/common/SignValidate.inc" %>