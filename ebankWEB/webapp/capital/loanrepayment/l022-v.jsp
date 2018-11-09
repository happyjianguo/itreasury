<%--
 页面名称 ：l022-v.jsp
 页面功能 :  [贷款还款]--利息费用清还,视图页面一
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*"
                 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款]--利息费用清还";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l022-v.jsp*******");
	
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
		String strOpenSystem = Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		//获取信息用于返回
		
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
	    String  sLetOutCode = ""; // 放款通知单号
		double dBalance = 0.00;//贷款余额
		double dRate = 0.00;//放款通知单利率
	//	double dAmount = 0.00; // 本金金额
		Timestamp tsClearInterest = null; // 结息日
	//	String sNote = ""; // 汇款用途/摘要
		long lSubAccountID = -1;//子账户id
		
		Timestamp tsExecuteDate = null; // 执行日
		String sNote = ""; // 汇款用途/摘要
		double dRealInterest = 0.0; //实付贷款利息
	    double dRealCompoundInterest = 0.0; //实付复利
	    double dRealOverDueInterest = 0.0; //实付逾期罚息
	    double dRealCommission = 0.0; //应付手续费
	    double dRealTotal = 0.0; //实付利息和费用之和
		
		
		 //如果从第二页面返回
		 if(strAction.equals("toCancel")|| strAction.equals("toCancelModify"))
		 {
				  //获取页面一的信息,关于放大镜的信息获取需要进行修改
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
				 
				/* strTemp = (String)request.getAttribute("dAmount");//本金金额
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					dAmount = DataFormat.parseNumber(strTemp);
				 }
				 */
				
				 
				  strTemp = (String)request.getAttribute("tsClearInterest");//结息日
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					tsClearInterest =  DataFormat.getDateTime(strTemp);
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
				 
				 if(!strAction.equals("toCancel"))
				 {
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
				  } 
                
		 }
		 else if (strAction.equals("toModify"))
		 {		
		        Log.print("=======获取查询结果");
				FinanceInfo financeInfo = null;
				financeInfo = (FinanceInfo)request.getAttribute("resultInfo");
				
				if(financeInfo != null)
				{
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
				// dAmount = financeInfo.getAmount(); // 本金金额
				 tsClearInterest = financeInfo.getClearInterest(); // 结息日
				 tsExecuteDate = financeInfo.getExecuteDate(); // 执行日
				 sNote = financeInfo.getNote(); // 汇款用途/摘要
				 dRealInterest = financeInfo.getRealInterest();//实付贷款利息
	   			 dRealCompoundInterest = financeInfo.getRealCompoundInterest(); //实付复利
	   			 dRealOverDueInterest = financeInfo.getRealOverdueInterest(); //实付逾期罚息
	   			 dRealCommission = financeInfo.getRealCommission(); //应付手续费
	   			 dRealTotal = DataFormat.formatDouble(DataFormat.formatDouble(dRealInterest)
				             + DataFormat.formatDouble(dRealCompoundInterest)
							 + DataFormat.formatDouble(dRealOverDueInterest)
							 + DataFormat.formatDouble(dRealCommission));//实付利息和费用之和
				 
				}
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
<input type="Hidden" name="lID" value="<%=lID%>">
<!--以下用于查询返回!-->
<input type="hidden" name="strAction" value="<%=strAction%>">
<input type="hidden" name="strReturn" value="<%=strReturn%>">
<!--以上用于查询返回!-->
<!--以下用于修改!-->
<input type="Hidden" name="tsExecuteDate" value="<%=tsExecuteDate%>">
<input type="Hidden" name="sNote" value="<%=sNote%>">
<input type="hidden" name="dRealInterest" value="<%=dRealInterest%>">
<input type="hidden" name="dRealCompoundInterest" value="<%=dRealCompoundInterest%>">
<input type="hidden" name="dRealOverDueInterest" value="<%=dRealOverDueInterest%>">
<input type="hidden" name="dRealCommission" value="<%=dRealCommission%>">
<input type="hidden" name="dRealTotal" value="<%=dRealTotal%>">
<!--以上用于修改!-->	
        <TABLE cellSpacing=0 cellPadding=0  border=0 width="730" class=top>
          <TR class="FormTitle">
          <!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="" width=3></TD-->
            <TD  colSpan=8 height=25><FONT 
             size=3>活期账户</FONT></TD>
          <!--TD vAlign=top align=right width=1 height=25>
            <DIV align=right></DIV></TD-->
			</TR>
        <TR >
          <TD colSpan=4 height=1></TD></TR>
        <TR >
          <TD width=5 height=25></TD>
            <TD class=graytext width=130  height=25>活期账户名称：</TD>
          <TD class=graytext width=430  height=25 colSpan=2 ><INPUT class=rebox 
            readOnly size=30 name=sPayerName value="<%=sessionMng.m_strClientName%>"></TD>
          <TD width=1 height=25></TD></TR>
        <TR >
          <TD colSpan=4 height=1></TD></TR>
        <TR >
          <TD width=5 height=25><input type="Hidden" name="lPayerAcctID" value="<%=lPayerAcctID%>"></TD>
		  
<%
		//付款方账号放大镜
		Magnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,lID,sessionMng.m_lClientID,"lPayerAcctID","dCurrentBalance","dUsableBalance","frm",sPayerAcctNo,"sPayerAcctNo","付款方账号"," width=\"130\" height=\"25\" class=\"graytext\""," colSpan=2 width=\"590\" height=\"25\" class=\"graytext\"");	
%>		 
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD width=4 height=25></TD>
            <TD width=146 height=25>当前余额：</TD>
            <TD class=graytext height=25>
             <%=sessionMng.m_strCurrencySymbol%><INPUT class=amountbox readOnly size=16 name=dCurrentBalance value="<%=DataFormat.formatDisabledAmount(dCurrentBalance)%>">
            </TD>
            <TD class=leftwhiteline><font class=graytext>可用余额： </font> 
               <%=sessionMng.m_strCurrencySymbol%><input class=amountbox readonly size=16 name=dUsableBalance value="<%=DataFormat.formatDisabledAmount(dUsableBalance)%>"></TD>
            <TD width=15 colSpan=4></TD>
          </TR>
        </table>
		<br><br>
        <TABLE cellSpacing=0 cellPadding=0 width=730 border=0 class=top>
          
          <TR class="FormTitle"> 
            <!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
            <TD colSpan=7 height=25> <DIV align=left><FONT  
            size=3 >贷款资料</FONT></DIV></TD>
            <!--TD width=15 colSpan=4></TD-->
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="Hidden" name="strClientCtrl" value="<%=sessionMng.m_lClientID%>"></TD>
			
	        <%
			String[] strNextControls = {"lLoanNoteIDCtrl"};
		    Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","合同号",sessionMng.m_lClientID,lContractID,sLoanContractNo,SETTConstant.TransactionType.INTERESTFEEPAYMENT,2,"strClientCtrl","","",strNextControls);
			%>
            <TD width=131></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="hidden" name="strRtnContractIDCtrl"></TD>
          <%
          String[] strNextControls1 = {"tsClearInterest"};
		 //Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lLoanNoteID","放款日期",lContractID,lLoanNoteID,tsPayDate,sLetOutCode,-1,3,"lContractID","","",strNextControls1,"","","","","","dBalance");
		  Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","放款日期",lContractID,lLoanNoteID,lPayeeAcctID,tsPayDate,sLetOutCode,3,3,"lContractID","","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
		  %>
		  
            <TD width=131><INPUT type="Hidden" class=amountbox readOnly size=16 name=dBalance value="<%=DataFormat.formatDisabledAmount(dBalance)%>"></TD>
          </TR>
		  <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
		  <tr >
		  <td></td>
		  <td>利率：</td>
		  <td colspan="3"><input  size="8" type="text"  value="<%=DataFormat.formatRate(dRate)%>" name="lLoanNoteIDrate" disabled>%</td>
		  </tr>
          <TR > 
            <TD colSpan=4 height=1></TD>
		   </TR>  
		   
		  <TR > 
            <TD width=0 height=25></TD>
            <TD class=graytext width=30% 
          height=25>&nbsp;结息日：</TD>
            <TD class=graytext   height=25 align="left">
			<INPUT class=tar size=18 onfocus="nextfield='submitfunction'" 
            name="tsClearInterest" value="<%=tsClearInterest == null ? DataFormat.getDateString(Env.getSystemDate(1,1)):DataFormat.getDateString(tsClearInterest)
			%>"><!--A href="javascript:show_calendar('frm.tsClearInterest');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;"-->
           <IMG border=0 height=16 src="/webob/graphics/calendar.gif" width=17>
			</TD>
            <TD width=5 height=25></TD>
          </TR>
		  
		   <TR > 
            <TD colSpan=4 height=1></TD>
		   </TR>
		  </table>
        
        <br>
        <TABLE cellSpacing=0 cellPadding=0 width=730 border=0>
          <TR> 
            <TD width=445> <DIV align=right></DIV></TD>
            <TD width=63> <DIV align=right> </DIV></TD>
            <TD width=62>
			<div align="right">	
				<input type="button" name="GoNext" value=" 继 续 " class = button onfocus="nextfield='submitfunction';" onClick="goNext();">
			</div>
			</TD>
          </TR>
        </TABLE>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
      </form>
<script language="javascript">
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
   if(frm.lContractID.value=="" || frm.lContractID.value== "-1")
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
 
   //结息日
   	if (!checkDate(frm.tsClearInterest,1,"结息日 "))
	{
		return false;
	}
    
	if (frm.tsClearInterest.value >"<%=strOpenSystem%>")
	{
		alert("结息日不能晚于开机日！");
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
	   <%
	 if(strAction.equals("toCancelModify"))
	 {
	 %>
	 frm.strAction.value="toModify";
	 <%
	 }
	 %>
	  frm.action="l023-c.jsp"
	  showSending();
	  frm.submit();
	  }

}

firstFocus(frm.sPayerAcctNoCtrl);
//setSubmitFunction("goNext()");
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