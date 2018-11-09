<%--
 页面名称 ：l024-v.jsp
 页面功能 : [贷款还款]--利息费用清还
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款]--利息费用清还";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l024-v.jsp*******");
	
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
		//定义变量
		String strTemp = "";
		String strAction = "";
        String strReturn = "";
		
		 strTemp = (String)request.getAttribute("strAction");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strAction = strTemp.trim();
		 }
		 
		  strTemp = (String)request.getAttribute("strReturn");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strReturn = strTemp.trim();
		 }
		
		//页面一信息
		long lID = -1; // 指令序号
	    String sPayerName = ""; // 付款方名称
		String sPayerAcctNo = ""; // 付款方账号 
		long lPayerAcctID = -1;//付款方账户ID
		double dCurrentBalance = 0.0; // 当前金额
	    double dUsableBalance = 0.0; // 可用金额
		String sPayeeName = ""; // 收款方名称
	    String sPayeeAcctNo = ""; // 收款方账号（即贷款账号）
	    long lPayeeAcctID = -1; // 收款方账户ID（即贷款账户id）
		long lContractID = -1;//贷款合同id
		String sLoanContractNo = ""; // 贷款合同号
		Timestamp tsPayDate = null; // 贷款放款日期
		long lLoanNoteID = -1;
	    String  sLetOutCode = ""; // 放款通知单号
		Timestamp tsClearInterest = null;//结息日
		double dBalance = 0.0;//贷款余额
		double dAmount = 0.0; // 本金金额
		Timestamp tsExecuteDate = null; // 执行日
		String sNote = ""; // 汇款用途/摘要
		
		
		//子账户信息
	    //long lLoanNoteID = -1; //放款通知单id
		long lSubAccountID = -1;//子账户id
	    double dInterest = 0.0; //应付贷款利息
	    double dCompoundInterest = 0.0; //应付复利
	    double dOverDueInterest = 0.0; //应付逾期罚息
		double dSuretyFee = 0.0; //应付担保费
	    double dCommission = 0.0; //应付手续费
		double dInterestTax = 0.0;//应付利息税费
	    double dTotal = 0.0; //应付利息和费用之和
		
		double dRealInterest = 0.0; //实付贷款利息
	    double dRealCompoundInterest = 0.0; //实付复利
	    double dRealOverDueInterest = 0.0; //实付逾期罚息
		double dRealSuretyFee = 0.0; //实付担保费
	    double dRealCommission = 0.0; //应付手续费
		double dRealInterestTax = 0.0;//实付利息税费
	    double dRealTotal = 0.0; //实付利息和费用之和
		double dInterestTaxRate = 0.0;//利息税费率
        
		double dInterestReceiveable = 0.0;//计提利息
		double dInterestIncome = 0.0;//本次利息
		double dRealInterestReceiveable = 0.0;//实付计提利息
		double dRealInterestIncome = 0.0;//实付本次利息
		
		//为打印所加字段
	    Timestamp tsInterestStart = null; //贷款利息起息日
	    double dInterestRate = 0.00; //贷款利息利率	
	 	Timestamp tsCompInterestStart = null; //复利起息日
	 	double dCompRate = 0.00; //复利利率
	 	double dCompoundAmount = 0.00; //复利本金
	 	Timestamp tsOverDueStart = null; //罚息起息日
	 	double dOverDueRate = 0.00; //罚息利率
	 	double dOverDueAmount = 0.00; //应付逾期本金
	 	Timestamp tsSuretyStart = null; //担保费起息日
	 	double dSuretyRate = 0.00; //担保费率
	 	Timestamp tsCommissionStart = null; //手续费起息日
	 	double dCommissionRate = 0.00; //手续费率
		
		long lLoanAcctID = -1;
	    strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");//收款方账户ID（即贷款账户id）
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lLoanAcctID = Long.valueOf(strTemp).longValue();
		 }
		
		//获取页面一的信息
	         strTemp = (String)request.getAttribute("lID");// 指令序号
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lID = Long.valueOf(strTemp).longValue();
			 }
			
			 strTemp = (String)request.getAttribute("sPayerName");// 付款方名称
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayerName = strTemp.trim();
			 }
			 
			 strTemp = (String)request.getAttribute("sPayerAcctNoCtrl");// 付款方账号 
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayerAcctNo = strTemp.trim();
			 }
			 
			 strTemp = (String)request.getAttribute("lPayerAcctID");// 付款方账户id
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lPayerAcctID = Long.valueOf(strTemp).longValue();
			 }
			
			 strTemp = (String)request.getAttribute("dCurrentBalance");//当前金额
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dCurrentBalance = DataFormat.parseNumber(strTemp);
			 }
			
			 strTemp = (String)request.getAttribute("dUsableBalance");//可用金额
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dUsableBalance = DataFormat.parseNumber(strTemp);
			 }
			 
			  strTemp = (String)request.getAttribute("lLoanNoteIDAccountID");//收款方账户ID（即贷款账户id）
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lPayeeAcctID = Long.valueOf(strTemp).longValue();
			 }
			 Log.print("===============收款方账户ID:"+lPayeeAcctID);
			 
			 strTemp = (String)request.getAttribute("lContractID");//贷款合同id
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lContractID = Long.valueOf(strTemp).longValue();
				sLoanContractNo = NameRef.getContractNoByID(lContractID);
			 }
			 
			 strTemp = (String)request.getAttribute("lContractIDCtrl");//贷款合同编号
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sLoanContractNo = strTemp.trim();
			 }
			 
		 	 strTemp = (String)request.getAttribute("lLoanNoteIDCtrl");//贷款放款日期
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				tsPayDate =  DataFormat.getDateTime(strTemp);
			 }
			  
			  strTemp = (String)request.getAttribute("tsClearInterest");//结息日
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				tsClearInterest =  DataFormat.getDateTime(strTemp);
			 }
			
			  strTemp = (String)request.getAttribute("lLoanNoteID");// 放款通知单id
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lLoanNoteID = Long.valueOf(strTemp).longValue();
				sLetOutCode = NameRef.getPayFormNoByID(lLoanNoteID);
			 }
			 
			 strTemp = (String)request.getAttribute("dBalance");//贷款余额
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dBalance = DataFormat.parseNumber(strTemp);
			 }
			 
			 strTemp = (String)request.getAttribute("dAmount");//本金金额
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				dAmount = DataFormat.parseNumber(strTemp);
			 }
			 
			 strTemp = (String)request.getAttribute("tsExecuteDate");//执行日
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				tsExecuteDate =  DataFormat.getDateTime(strTemp);
			 }
			 
			 strTemp = (String)request.getAttribute("sNote");// 汇款用途/摘要
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sNote = strTemp.trim();
			 }
			 
			  strTemp = (String)request.getAttribute("dRealInterest");
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				 dRealInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			 }
			 
			 strTemp = (String)request.getAttribute("dRealCompoundInterest");
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				 dRealCompoundInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			 }
			 
			  strTemp = (String)request.getAttribute("dRealOverDueInterest");
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				 dRealOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			 }
			 
			 strTemp = (String)request.getAttribute("dRealCommission");
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				 dRealCommission = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
			 } 
			 
			 strTemp = (String)request.getAttribute("dRealTotal");
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				 dRealTotal = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();				 
			 } 
			 
		 //获取查询结果
		 SubLoanAccountDetailInfo subLoanAccountDetailInfo = null;
		 subLoanAccountDetailInfo = (SubLoanAccountDetailInfo)request.getAttribute("resultInfo");
		 if(subLoanAccountDetailInfo != null)
		 {
		   lSubAccountID = subLoanAccountDetailInfo.getSubAccountID();
		   dInterest = subLoanAccountDetailInfo.getInterest();
		   dInterestReceiveable = subLoanAccountDetailInfo.getInterestReceiveable();
		   dInterestIncome = subLoanAccountDetailInfo.getInterestIncome();
		   dCompoundInterest = subLoanAccountDetailInfo.getCompoundInterest();
		   dOverDueInterest = subLoanAccountDetailInfo.getOverDueInterest();
		   dSuretyFee = subLoanAccountDetailInfo.getSuretyFee();
		   dCommission = subLoanAccountDetailInfo.getCommission();
		   dInterestTax = subLoanAccountDetailInfo.getInterestTax();
		   dInterestTaxRate = subLoanAccountDetailInfo.getInterestTaxRate();
		   dTotal = subLoanAccountDetailInfo.getTotal();
		    if(!strAction.equals("toModify"))
		   {					   
			   if(dRealTotal <= 0)
			   {			   
			   dRealInterest = dInterest;
			   dRealInterestReceiveable = dInterestReceiveable;
			   dRealInterestIncome = dInterestIncome;
			   dRealCompoundInterest = dCompoundInterest;
			   dRealOverDueInterest = dOverDueInterest;
			   dRealInterestTax = dInterestTax;
			   dRealSuretyFee = dSuretyFee;
			   dRealCommission = dCommission;
			   dRealTotal = dTotal;
			   }
		   }   
			   
		   //为打印所加字段
		   tsInterestStart = subLoanAccountDetailInfo.getInterestStart(); //贷款利息起息日
	       dInterestRate = subLoanAccountDetailInfo.getInterestRate(); //贷款利息利率	
	 	   tsCompInterestStart = subLoanAccountDetailInfo.getCompInterestStart(); //复利起息日
	 	   dCompRate = subLoanAccountDetailInfo.getCompRate(); //复利利率
	 	   dCompoundAmount = subLoanAccountDetailInfo.getCompoundAmount(); //复利本金
	 	   tsOverDueStart = subLoanAccountDetailInfo.getOverDueStart(); //罚息起息日
	 	   dOverDueRate = subLoanAccountDetailInfo.getOverDueRate(); //罚息利率
	 	   dOverDueAmount = subLoanAccountDetailInfo.getOverDueAmount(); //应付逾期本金
	 	   tsSuretyStart = subLoanAccountDetailInfo.getSuretyStart(); //担保费起息日
	 	   dSuretyRate = subLoanAccountDetailInfo.getSuretyRate(); //担保费率
	 	   tsCommissionStart = subLoanAccountDetailInfo.getCommissionStart(); //手续费起息日
	 	   dCommissionRate = subLoanAccountDetailInfo.getCommissionRate(); //手续费率
	     }
		 
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<FORM name=frm method=post>
<!--以下hidden控件用于返回时页面一信息的回显以及保存!--->
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="sPayerName" value="<%=sPayerName%>">
<input type="Hidden" name="sPayerAcctNoCtrl" value="<%=sPayerAcctNo%>">
<input type="Hidden" name="lPayerAcctID" value="<%=lPayerAcctID%>">
<input type="Hidden" name="dCurrentBalance" value="<%=dCurrentBalance%>">
<input type="Hidden" name="dUsableBalance" value="<%=dUsableBalance%>">
<input type="Hidden" name="lLoanNoteIDAccountID" value="<%=lPayeeAcctID%>">
<input type="Hidden" name="lContractID" value="<%=lContractID%>">
<input type="Hidden" name="lContractIDCtrl" value="<%=sLoanContractNo%>">
<input type="Hidden" name="lLoanNoteIDCtrl" value="<%=tsPayDate%>">
<input type="Hidden" name="lLoanNoteID" value="<%=lLoanNoteID%>">
<input type="Hidden" name="tsClearInterest" value="<%=tsClearInterest%>">
<input type="Hidden" name="dBalance" value="<%=dBalance%>">
<input type="Hidden" name="dAmount" value="<%=dAmount%>">
<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<!--以上hidden控件用于返回时页面一信息的回显以及保存!--->
<!--以下hidden控件用于打印所需信息!--->
<input type="Hidden" name="tsInterestStart" value="<%=tsInterestStart%>">
<input type="Hidden" name="dInterestRate" value="<%=dInterestRate%>">
<input type="Hidden" name="tsCompInterestStart" value="<%=tsCompInterestStart%>">
<input type="Hidden" name="dCompRate" value="<%=dCompRate%>">
<input type="Hidden" name="dCompoundAmount" value="<%=dCompoundAmount%>">
<input type="Hidden" name="tsOverDueStart" value="<%=tsOverDueStart%>">
<input type="Hidden" name="dOverDueRate" value="<%=dOverDueRate%>">
<input type="Hidden" name="dOverDueAmount" value="<%=dOverDueAmount%>">
<input type="Hidden" name="tsSuretyStart" value="<%=tsSuretyStart%>">
<input type="Hidden" name="dSuretyRate" value="<%=dSuretyRate%>">
<input type="Hidden" name="tsCommissionStart" value="<%=tsCommissionStart%>">
<input type="Hidden" name="dCommissionRate" value="<%=dCommissionRate%>">
<!--以上hidden控件用于打印所需信息!--->
<!--以下用于查询返回!-->
<input type="hidden" name="strAction" value="<%=strAction%>">
<input type="hidden" name="strReturn" value="<%=strReturn%>">
<!--以上用于查询返回!-->
<input type="hidden" name="lLoanAcctID" value="<%=lLoanNoteID%>">
<input type="hidden" name="dInterestTaxRate" value="<%=dInterestTaxRate%>">
          
  <table width="730" height="172" border="0" cellpadding="0" cellspacing="1" class=top>
    <tr class="FormTitle"> 
              <td colspan="3" class=graytext  height=25>&nbsp;利息费用资料</td>
            </tr>
            <tr> 
			
              <td colspan="3" class=graytext >&nbsp;付息账号：
			  <INPUT class=rebox  readOnly size=30 name=sPayInterestAccountNO value="<%=sPayerAcctNo%>"> </td>
            </tr>
            <tr> 
              <td width="30%" class=graytext >&nbsp;</td>
              <td width="35%" class=graytext >应付金额：</td>
			  <td width="35%" class=graytext >实付金额：</td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;贷款利息：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterest value="<%=dInterest > 0 ?DataFormat.formatDisabledAmount(dInterest):"0.00"%>"></td>
			  <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealInterest onfocus="nextfield='dRealCompoundInterest'" value="<%=dRealInterest > 0 ?DataFormat.formatDisabledAmount(dRealInterest):"0.00"%>" onChange="disassemble(),changeRealSum(this),changeRealInterestTax()" readOnly></td>
            </tr>
				<tr>
			<td colspan="3">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr> 
              <td class=graytext  width="30%">&nbsp;&nbsp;其中&nbsp;&nbsp;计提利息：&nbsp;&nbsp;</td>
              <td class=graytext  width="35%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox readOnly  size=30 name=dInterestReceiveable value="<%=dInterestReceiveable > 0 ?DataFormat.formatDisabledAmount(dInterestReceiveable):"0.00"%>" ></td>
			  <td class=graytext  width="35%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealInterestReceiveable value="<%=dRealInterestReceiveable > 0 ?DataFormat.formatDisabledAmount(dRealInterestReceiveable):"0.00"%>" onChange="disassemble(),changeRealSum(this),changeRealInterestTax()"></td>
            </tr>
			<tr> 
              <td class=graytext  width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次利息：</td>
              <td class=graytext  width="35%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterestIncome value="<%=dInterestIncome > 0 ?DataFormat.formatDisabledAmount(dInterestIncome):"0.00"%>"></td>
			   <td class=graytext  width="35%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealInterestIncome value="<%=dRealInterestIncome > 0 ?DataFormat.formatDisabledAmount(dRealInterestIncome):"0.00"%>" onChange="disassemble(),changeRealSum(this),changeRealInterestTax()"></td>
            </tr>
			</table>
			</td>
			</tr>
            <tr> 
              <td class=graytext >&nbsp;复利：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dCompoundInterest value="<%=dCompoundInterest > 0 ? DataFormat.formatDisabledAmount(dCompoundInterest):"0.00"%>"></td>
			    <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealCompoundInterest  onfocus="nextfield='dRealOverDueInterest'" value="<%=dRealCompoundInterest > 0 ?DataFormat.formatDisabledAmount(dRealCompoundInterest):"0.00"%>" onChange="changeRealSum(this),changeRealInterestTax()"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;逾期罚息：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dOverDueInterest value="<%=dOverDueInterest > 0 ? DataFormat.formatDisabledAmount(dOverDueInterest):"0.00"%>"></td>
			    <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealOverDueInterest onfocus="nextfield='dRealSuretyFee'" value="<%=dRealOverDueInterest > 0 ?DataFormat.formatDisabledAmount(dRealOverDueInterest):"0.00"%>" onChange="changeRealSum(this),changeRealInterestTax()"></td>
            </tr>
			<tr> 
              <td class=graytext >&nbsp;利息税费：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dInterestTax value="<%=dInterestTax > 0 ? DataFormat.formatDisabledAmount(dInterestTax):"0.00"%>"></td>
			    <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar  readOnly size=30 name=dRealInterestTax  value="<%=dRealInterestTax > 0 ?DataFormat.formatDisabledAmount(dRealInterestTax):"0.00"%>" onChange="changeRealSum(this);"></td>
            </tr>
			  <tr> 
              <td class=graytext >&nbsp;担保费：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dSuretyFee value="<%=dSuretyFee > 0 ? DataFormat.formatDisabledAmount(dSuretyFee):"0.00"%>"></td>
			      <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealSuretyFee onfocus="nextfield='dRealCommission'" value="<%=dRealSuretyFee > 0 ?DataFormat.formatDisabledAmount(dRealSuretyFee):"0.00"%>" onChange="changeRealSum(this);"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;手续费：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dCommission value="<%=dCommission > 0 ? DataFormat.formatDisabledAmount(dCommission):"0.00"%>"></td>
			      <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=tar   size=30 name=dRealCommission onfocus="nextfield='tsExecuteDate'" value="<%=dRealCommission > 0 ?DataFormat.formatDisabledAmount(dRealCommission):"0.00"%>" onChange="changeRealSum(this);"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;利息费用合计：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
              <INPUT class=amountbox  readOnly size=30 name=dTotal value="<%=dTotal > 0 ? DataFormat.formatDisabledAmount((dInterest + dCompoundInterest + dOverDueInterest +dSuretyFee +dCommission)):"0.00"%>"></td>
			  <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
              <INPUT class=tar  readOnly size=30 name=dRealTotal value="<%=dRealTotal > 0 ? DataFormat.formatDisabledAmount((dRealInterest + dRealCompoundInterest + dRealOverDueInterest +dRealSuretyFee +dRealCommission)):"0.00"%>" ></td>
            </tr>
			<tr>
			<td colspan="3">
			<table width="100%" cellpadding="0" cellspacing="0">
			<TR > 
            <TD  width=125  colSpan=2 height=25>&nbsp;执行日：</TD>
            <TD height=25>
			<INPUT class=tar size=18 onfocus="nextfield='sNote'" 
            name="tsExecuteDate" value="<%=tsExecuteDate == null ? DataFormat.getDateString(Env.getSystemDate(1,1)):DataFormat.getDateString(tsExecuteDate)%>"><!--<A href="javascript:show_calendar('frm.tsExecuteDate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;"-->
           <IMG border=0 height=16 src="/webob/graphics/calendar.gif" width=17>
			</TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD  width=125 colSpan=2 height=25>&nbsp;汇款用途：</TD>
            <TD  noWrap  height=25>
			<textarea name="sNote" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield = 'submitfunction'" ><%=sNote == "" || sNote == null ? sessionMng.m_strClientShortName:DataFormat.formatString(sNote)%></textarea>
            </TD>
          </TR>
          <TR bgColor=#ffffff> 
            <TD colSpan=5 height=1></TD>
          </TR>
		  </table>
		  </td>
		  </tr>
          </table>
          <br>
		  
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><TABLE cellSpacing=0 cellPadding=0 width=584 border=0>
                  <TR> 
                    <TD width=454> <DIV align=right></DIV></TD>
                    <TD width=70> <DIV align=right><input type="button" name="doSubmit" value=" 提 交 " class = button onfocus="nextfield='submitfunction';" onClick="doSubmitFun();"></DIV></TD>
                    <TD width=59> <DIV align=right><input type="button" name="doCalcel" value=" 返 回 " class = button  onClick="doCancelFun();"></DIV></TD>
                  </TR>
                </TABLE></td>
            </tr>
          </table>
          <p>&nbsp;</p></form>
<script language="javascript">
function doSubmitFun()
{
 /* if(!validate())
  {
    return;
  }
  */
  var abTemp = frm.sNote.value;
    if(abTemp.length>50)
	{
	  alert("汇款用途过长: 汇款用途字符数应在50以内");
	  return;
	}
<%--  if(confirm("是否提交？"))--%>
<%--  {--%>
        frm.action="l025-c.jsp"
		showSending();
		frm.submit();
<%--  }--%>

}

//输入实际支付值时进行统计
function changeRealSum(txt)
{
   var num = txt.value;
   txt.value = formatAmount(num);
   if(!validate())
   {
    return;
   }
   ChangeSum();
}

function ChangeSum()
{

var cSum = 0;    
	cSum = (
   parseFloat(reverseFormatAmount(frm.dRealInterest.value)) + 
   parseFloat(reverseFormatAmount(frm.dRealCompoundInterest.value))+
   parseFloat(reverseFormatAmount(frm.dRealOverDueInterest.value))+
   parseFloat(reverseFormatAmount(frm.dRealSuretyFee.value))+
   parseFloat(reverseFormatAmount(frm.dRealCommission.value)));
   frm.dRealTotal.value = cSum;
}

function validate()
{
  if(!checkAmount(frm.dRealInterest, 2, "实际支付的贷款利息"))
   {
	  return false;
   }
   
   if(!checkAmount(frm.dRealCompoundInterest, 2, "实际支付的复利"))
   {
	  return false;
   }
   
    if(!checkAmount(frm.dRealOverDueInterest, 2, "实际支付的逾期罚息"))
   {
	  return false;
   }
   
     if(!checkAmount(frm.dRealSuretyFee, 2, "实际支付的担保费"))
   {
	  return false;
   }
   
    if(!checkAmount(frm.dRealCommission, 2, "实际支付的手续费"))
   {
	  return false;
   }
   
   //执行日
   if (!checkInterestExecuteDate(frm.tsExecuteDate,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
	{
		return false;
	}
		/* 汇款用途 */
		if (!InputValid(frm.sNote, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}	
   
   //比较实际支付与应付之间关系
   var dBalance;
   
  dBalance = parseFloat(reverseFormatAmount(frm.dRealInterest.value)) -
				   parseFloat(reverseFormatAmount(frm.dInterest.value));
				   
  if(dBalance > 0)
  {
    alert("实际支付的贷款利息不能大于应付贷款利息！");
	frm.dRealInterest.value = "0.00";
	ChangeSum();
	frm.dRealInterest.focus();
	return false;
  }
  
   dBalance = parseFloat(reverseFormatAmount(frm.dRealOverDueInterest.value)) -
				   parseFloat(reverseFormatAmount(frm.dOverDueInterest.value));
				   
  if(dBalance > 0)
  {
    alert("实际支付的逾期罚息不能大于应付逾期罚息！");
	frm.dRealOverDueInterest.value = "0.00";
	ChangeSum();
	frm.dRealOverDueInterest.focus();
	return false;
  }
  
   dBalance = parseFloat(reverseFormatAmount(frm.dRealSuretyFee.value)) -
				   parseFloat(reverseFormatAmount(frm.dSuretyFee.value));
				   
  if(dBalance > 0)
  {
    alert("实际支付的担保费不能大于应付担保费！");
	frm.dRealSuretyFee.value = "0.00";
	ChangeSum();
	frm.dRealSuretyFee.focus();
	return false;
  }
   
   dBalance = parseFloat(reverseFormatAmount(frm.dRealCompoundInterest.value)) -
				   parseFloat(reverseFormatAmount(frm.dCompoundInterest.value));
				   
  if(dBalance > 0)
  {
    alert("实际支付的复利不能大于应付复利！");
	frm.dRealCompoundInterest.value = "0.00";
	ChangeSum();
	frm.dRealCompoundInterest.focus();
	return false;
  }
   
  dBalance = parseFloat(reverseFormatAmount(frm.dRealCommission.value)) -
				   parseFloat(reverseFormatAmount(frm.dCommission.value));
				   
  if(dBalance > 0)
  {
    alert("实际支付的手续费不能大于应付手续费！");
	frm.dRealCommission.value = "0.00";
	ChangeSum();
	frm.dRealCommission.focus();
	return false;
  }
 
  
	//业务校验
  dBalance = parseFloat(reverseFormatAmount(frm.dUsableBalance.value)) 
  			    -(parseFloat(reverseFormatAmount(frm.dAmount.value)) 
				+ parseFloat(reverseFormatAmount(frm.dRealTotal.value))) ;
	
  if(dBalance < 0 )
  {
     alert("账户可用余额小于本金与利息费用之和！");
	 return false;
  }		
	
	  
   return true;
}

function changeRealInterestTax()
{
	
	var tempTaxRate = document.frm.dInterestTaxRate.value;
	var tempRealInterest = reverseFormatAmount(document.frm.dRealInterest.value);
	var tempRealCompoundInterest = reverseFormatAmount(document.frm.dRealCompoundInterest.value);
	var tempRealOverDueInterest = reverseFormatAmount(document.frm.dRealOverDueInterest.value);
	var tempRealCommission = reverseFormatAmount(document.frm.dRealCommission.value);
	if (tempRealInterest == "")
	{
		tempRealInterest = 0.0;
	}
	
	if (tempRealCompoundInterest == "")
	{
		tempRealCompoundInterest = 0.0;
	}
	
	if (tempRealOverDueInterest == "")
	{
		tempRealOverDueInterest = 0.0;
	}
	
	if (tempRealCommission == "")
	{
		tempRealCommission = 0.0;
	}
	
	if (!isFloat(tempRealInterest))
	{
		alert("请输入正确的数值");
		document.frm.dRealInterest.value = "";
		document.frm.dRealInterest.focus();
		return false;
	}
		
	if (!isFloat(tempRealCompoundInterest))
	{
		alert("请输入正确的数值");
		document.frm.dRealCompoundInterest.value = "";
		document.frm.dRealCompoundInterest.focus();
		return false;
	}
	
	if (!isFloat(tempRealOverDueInterest))
	{
		alert("请输入正确的数值");
		document.frm.dRealOverDueInterest.value = "";
		document.frm.dRealOverDueInterest.focus();
		return false;
	}
	
	if (!isFloat(tempRealCommission))
	{
		alert("请输入正确的数值");
		document.frm.dRealCommission.value = "";
		document.frm.dRealCommission.focus();
		return false;
	}
	<%
	if(subLoanAccountDetailInfo.getContractType()==LOANConstant.LoanType.WT)	
	{
			//委托贷款
	%>
	var tempValue =(parseFloat(tempRealInterest)
	               + parseFloat(tempRealCompoundInterest) 
				   + parseFloat(tempRealOverDueInterest))
				   *parseFloat(tempTaxRate)/100;
	frm.dRealInterestTax.value = formatAmount(tempValue);			   
	<%
		}
	%>			
	
	
}

function doCancelFun()
{
  if(confirm("是否返回？"))
  {
    <%if(strAction.equals("toModify"))
	{
	%>
	frm.strAction.value="toCancelModify";
	<%
	}
	else
	{
	%>
	frm.strAction.value="toCancel";
	<%
	}
	%>
	
	frm.action="l022-v.jsp"
	showSending();
	frm.submit();
  }
}

/**
 * 拆分利息到计提利息和本次利息
 */
function disassemble(){

	with (document.all){
		var realInterest 			= isNaN(parseFloat(reverseFormatAmount(dRealInterest.value)))?0:parseFloat(reverseFormatAmount(dRealInterest.value));				//实际支付利息
		var interestReceiveable 		= isNaN(parseFloat(reverseFormatAmount(dInterestReceiveable.value)))?0:parseFloat(reverseFormatAmount(dInterestReceiveable.value));	//计提利息
		var interestIncome 			= isNaN(parseFloat(reverseFormatAmount(dInterestIncome.value)))?0:parseFloat(reverseFormatAmount(dInterestIncome.value));			//本次利息
		var realInterestReceiveable	= 0;																			//实际支付计提利息
		var realInterestIncome		= 0;																			//实际支付本次利息	
		var realBalance				= 0;																			//补偿计提利息后剩余的金额
		
		if (realInterest>interestReceiveable)
		{//如果实际支付利息大于应当支付计提利息
			realInterestReceiveable 	= interestReceiveable;
			realBalance				= realInterest - interestReceiveable;		//计算余额
		}
		else
		{//如果实际支付利息小于等于应当支付计提利息
			realInterestReceiveable	= realInterest;
		}
		if (realBalance>interestIncome)
		{//如果余额大于应当支付本次利息
			realInterestIncome 		= interestIncome;
		}
		else{
		//如果余额小于等于应当支付本次利息
			realInterestIncome		= realBalance;
		}
		
		dRealInterestReceiveable.value=formatAmount(getMoneyNum(realInterestReceiveable));
		dRealInterestIncome.value	= formatAmount(getMoneyNum(realInterestIncome));
	}//end with
}//end function

/**
 *获得数值的小数点后两位四舍五入值
 */
function getMoneyNum(number){
	var returnValue=""+number;
	var index = returnValue.indexOf(".");
	if (index != -1 && index+3<returnValue.length){
		var nextVal = returnValue.charAt(index+3);
		returnValue = returnValue.substring(0,index+3);
		if (parseInt(nextVal)>=5) returnValue = (parseFloat(returnValue)*100+1)/100;
	}
	return returnValue;
}
firstFocus(frm.dRealInterest);
//setSubmitFunction("doSubmitFun()");
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