<%--
 页面名称 ：l004-v.jsp
 页面功能 : [贷款还款]--自营贷款
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
	String strTitle = "[贷款还款]--自营贷款";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l004-v.jsp*******");
	
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
		 
		 String isYT = "";
		 strTemp = (String)request.getAttribute("isYT");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			isYT = strTemp.trim();
			if (isYT.equals("1"))
			{
				strTitle = "[贷款还款]--银团贷款";
			}
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
	    double dTotal = 0.0; //应付利息和费用之和
		//新添字段
		double dInterestReceiveable = 0.0;//计提利息
		double dInterestIncome = 0.0;//本次利息
		
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
		
		     strTemp = (String)request.getAttribute("sPayeeName");//收款方名称
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayeeName = strTemp.trim();
			 }
				
			 strTemp = (String)request.getAttribute("sPayeeAcctNo");//收款方账号（即贷款账号） 
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				sPayeeAcctNo = strTemp.trim();
			 }
			 //Log.print("====贷款账号："+sPayeeAcctNo);
			 
			  strTemp = (String)request.getAttribute("lPayeeAcctID");//收款方账户ID（即贷款账户id）
			 if (strTemp != null && strTemp.trim().length() > 0)
			 {
				lPayeeAcctID = Long.valueOf(strTemp).longValue();
			 }
			 
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
			  Log.print("====放款日期："+tsPayDate);
	         
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
		 //获取查询结果
		 SubLoanAccountDetailInfo subLoanAccountDetailInfo = null;
		 subLoanAccountDetailInfo = (SubLoanAccountDetailInfo)request.getAttribute("resultInfo");
		 if(subLoanAccountDetailInfo != null)
		 {
		   lSubAccountID = subLoanAccountDetailInfo.getSubAccountID();
		   Log.print("＝＝＝＝＝＝＝＝＝＝＝子账户:"+lSubAccountID);
		   dInterest = subLoanAccountDetailInfo.getInterest();
		   Log.print("======贷款利息："+dInterest);
		   dCompoundInterest = subLoanAccountDetailInfo.getCompoundInterest();
		   dOverDueInterest = subLoanAccountDetailInfo.getOverDueInterest();
		   dSuretyFee = subLoanAccountDetailInfo.getSuretyFee();
		   dInterestReceiveable = subLoanAccountDetailInfo.getInterestReceiveable();
		   Log.print("======计提利息"+dInterestReceiveable);
		   dInterestIncome = subLoanAccountDetailInfo.getInterestIncome();
		   Log.print("======本次利息"+dInterestIncome);		   
		   dTotal = subLoanAccountDetailInfo.getTotal();
		   Log.print("＝＝＝＝＝＝＝＝＝＝＝应付总额:"+dTotal);
		   
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
		 
		 /* 实现菜单控制 */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
			lShowMenu = OBConstant.ShowMenu.NO;
		}
		
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<FORM name=frm method=post>
<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
<!--以下hidden控件用于返回时页面一信息的回显以及保存!--->
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="sPayerName" value="<%=sPayerName%>">
<input type="Hidden" name="sPayerAcctNoCtrl" value="<%=sPayerAcctNo%>">
<input type="Hidden" name="lPayerAcctID" value="<%=lPayerAcctID%>">
<input type="Hidden" name="dCurrentBalance" value="<%=dCurrentBalance%>">
<input type="Hidden" name="dUsableBalance" value="<%=dUsableBalance%>">
<input type="Hidden" name="sPayeeName" value="<%=sPayeeName%>">
<input type="Hidden" name="sPayeeAcctNo" value="<%=sPayeeAcctNo%>">
<input type="Hidden" name="lPayeeAcctID" value="<%=lPayeeAcctID%>">
<input type="Hidden" name="lContractID" value="<%=lContractID%>">
<input type="Hidden" name="lContractIDCtrl" value="<%=sLoanContractNo%>">
<input type="Hidden" name="lLoanNoteIDCtrl" value="<%=tsPayDate%>">
<input type="Hidden" name="lLoanNoteID" value="<%=lLoanNoteID%>">
<input type="Hidden" name="dBalance" value="<%=dBalance%>">
<input type="Hidden" name="dAmount" value="<%=dAmount%>">
<input type="Hidden" name="tsExecuteDate" value="<%=tsExecuteDate%>">
<input type="Hidden" name="sNote" value="<%=sNote%>">
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
          <table width="730" height="172" border="0" cellpadding="0" cellspacing="1" class="top">
            <tr class="tableHeader"> 
              <td colSpan=2 class="FormTitle" height=25>&nbsp;利息费用资料</td>
            </tr>
            <tr> 			
              <td colspan="2"  >&nbsp;付息账号：
			  <INPUT class=rebox  readOnly size=30 name=sPayInterestAccountNO value="<%=sPayerAcctNo%>"> </td>
            </tr>
            <tr> 
              <td width="29%" class=graytext >&nbsp;</td>
              <td width="71%" class=graytext >应付金额：</td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;贷款利息：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterest value="<%=dInterest > 0 ?DataFormat.formatDisabledAmount(dInterest):"0.00"%>"></td>
            </tr>
			<tr>
			<td colspan="2">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr> 
              <td class=graytext  width="29%">&nbsp;&nbsp;其中&nbsp;&nbsp;计提利息：&nbsp;&nbsp;</td>
              <td class=graytext  width="71%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterestReceiveable value="<%=dInterestReceiveable > 0 ?DataFormat.formatDisabledAmount(dInterestReceiveable):"0.00"%>"></td>
            </tr>
			<tr> 
              <td class=graytext  width="29%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次利息：</td>
              <td class=graytext  width="71%"><%=sessionMng.m_strCurrencySymbol%> 
              <INPUT class=amountbox  readOnly size=30 name=dInterestIncome value="<%=dInterestIncome > 0 ?DataFormat.formatDisabledAmount(dInterestIncome):"0.00"%>"></td>
            </tr>
			</table>
			</td>
			</tr>
            <tr> 
              <td class=graytext >&nbsp;复利：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dCompoundInterest value="<%=dCompoundInterest > 0 ? DataFormat.formatDisabledAmount(dCompoundInterest):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;逾期罚息：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dOverDueInterest value="<%=dOverDueInterest > 0 ? DataFormat.formatDisabledAmount(dOverDueInterest):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;担保费：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
               <INPUT class=amountbox  readOnly size=30 name=dSuretyFee value="<%=dSuretyFee > 0 ? DataFormat.formatDisabledAmount(dSuretyFee):"0.00"%>"></td>
            </tr>
            <tr> 
              <td class=graytext >&nbsp;利息费用合计：</td>
              <td class=graytext ><%=sessionMng.m_strCurrencySymbol%>  
              <INPUT class=amountbox  readOnly size=30 name=dTotal value="<%=dTotal > 0 ? DataFormat.formatDisabledAmount(dTotal):"0.00"%>"></td>
            </tr>
          </table>
          <br>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><TABLE cellSpacing=0 cellPadding=0 width=700 border=0>
                  <TR> 
                    <TD width=454> <DIV align=right></DIV><input type="hidden" name="isYT" value="<%=isYT%>"></TD>
                    <TD width=70> <DIV align=right><input type="button" name="doSubmit" value=" 提 交 " class = button onfocus="nextfield='submitfunction';" onClick="doSubmitFun();"></DIV></TD>
                    <TD width=59> <DIV align=right><input type="button" name="doCalcel" value=" 返 回 " class = button  onClick="doCancelFun();"></DIV></TD>
                  </TR>
                </TABLE></td>
            </tr>
          </table>
          <p>&nbsp;</p></form>
<script language="javascript">
function validate()
{
  var dBalance = parseFloat(reverseFormatAmount(frm.dUsableBalance.value)) 
  			    -(parseFloat(reverseFormatAmount(frm.dAmount.value)) 
				+ parseFloat(reverseFormatAmount(frm.dTotal.value))) ;
	
  if(dBalance < 0 )
  {
     alert("账户可用余额小于本金与利息费用之和！");
	 return false;
  }			
  return true;				   

}

function doSubmitFun()
{
  if(!validate())
  {
    return;
  }
  if(confirm("是否提交？"))
  {
        frm.action="l005-c.jsp"
		showSending();
		frm.submit();
  }
}

function doCancelFun()
{
  if(confirm("是否返回？"))
  {
    frm.strAction.value="toCancel";
	frm.action="l002-v.jsp"
	showSending();
	frm.submit();
  }
}

firstFocus(frm.doSubmit);
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