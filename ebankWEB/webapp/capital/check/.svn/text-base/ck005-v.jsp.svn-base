<%--
 页面名称 ：l012-v.jsp
 页面功能 :  [贷款还款]--委托贷款,视图页面一
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
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
	String strTitle = "业务复核";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>					
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
	String strTableTitle = "";
	long lTransType = -1;

  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l012-v.jsp*******");
	
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
		
		//初始化贷款账号
		if(request.getAttribute("payerOrPayeeInfo") != null)
		{
			payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo");
		}
		
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
		switch((int) lTransType)
		{
			case (int) OBConstant.QueryInstrType.TRUSTLOANRECEIVE :
				strTableTitle = "自营贷款清还";
				break;
			case (int) OBConstant.QueryInstrType.CONSIGNLOANRECEIVE :
				strTableTitle = "委托贷款清还";
				break;
			case (int) OBConstant.QueryInstrType.INTERESTFEEPAYMENT :
				strTableTitle = "利息费用清还";
				break;
			case (int) OBConstant.QueryInstrType.YTLOANRECEIVE :
				strTableTitle = "银团贷款清还";
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
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

<FORM name=frm method=post>
<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">

        <TABLE cellSpacing=0 cellPadding=0  border=0 width="730" class="top">
          <TR  class="FormTitle">
          <!--TD vAlign=top align=left width=5  height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
            <TD  colSpan=8 height=25><FONT 
             size=3><%=strTableTitle%></FONT></TD>
          <!--TD vAlign=top align=right width=1 height=25>
            <DIV align=right></DIV></TD--></TR>    
        <TR >
          <TD colSpan=4 height=1></TD></TR>
        <tr > 
      <td width="5" height="25"></td>
<%
		//付款方账号放大镜
		String[] nextfocus = null;
		nextfocus = new String[]{"lContractIDCtrl"};
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","付款方账号"," width=\"130\" height=\"25\" class=\"graytext\" colSpan=2  "," width=\"200\" height=\"25\" class=\"graytext\" colSpan=3",nextfocus);	
%>
      <td width="5"></td>
    </tr>                
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="Hidden" name="strClientCtrl" value="<%=sessionMng.m_lClientID%>"></TD>
			
	        <%
			String[] strNextControls = {"lLoanNoteIDCtrl"};
			if(lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE)
			{//CONSIGNLOANRECEIVE
		    	Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","合同号",sessionMng.m_lClientID,financeInfo.getContractID(),financeInfo.getLoanContractNo(),SETTConstant.TransactionType.CONSIGNLOANRECEIVE,2,"strClientCtrl"," colSpan=2  "," colSpan=2  ",strNextControls);
			}
			if(lTransType == OBConstant.QueryInstrType.TRUSTLOANRECEIVE)
			{
				Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","合同号",sessionMng.m_lClientID,financeInfo.getContractID(),financeInfo.getLoanContractNo(),SETTConstant.TransactionType.TRUSTLOANRECEIVE,2,"strClientCtrl"," colSpan=2  "," colSpan=2  ",strNextControls);
			}
			if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
			{
				Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","合同号",sessionMng.m_lClientID,financeInfo.getContractID(),financeInfo.getLoanContractNo(),-1,2,"strClientCtrl"," colSpan=2  "," colSpan=2 ",strNextControls);
			}
			if (lTransType == OBConstant.QueryInstrType.YTLOANRECEIVE)
			{
				Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","合同号",sessionMng.m_lClientID,financeInfo.getContractID(),financeInfo.getLoanContractNo(),SETTConstant.TransactionType.BANKGROUPLOANRECEIVE,2,"strClientCtrl"," colSpan=2  "," colSpan=2 ",strNextControls);
			}
			%>
            <TD width="5" ></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="hidden" name="strRtnContractIDCtrl"></TD>
          <%
          String[] strNextControls1 = {"dAmount"};
		 	if(lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE)
		 	{//CONSIGNLOANRECEIVE
		 		Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","放款日期",financeInfo.getContractID(),financeInfo.getLoanNoteID(),payeeInfo.getAccountID(),financeInfo.getPayDate(),financeInfo.getLetOutCode(),2,3,"lContractID"," colSpan=2 ","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
		  	}
			if(lTransType == OBConstant.QueryInstrType.TRUSTLOANRECEIVE )
			{
				Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","放款日期",financeInfo.getContractID(),financeInfo.getLoanNoteID(),payeeInfo.getAccountID(),financeInfo.getPayDate(),financeInfo.getLetOutCode(),1,3,"lContractID"," colSpan=2 ","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
			}
			if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
			{
				Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","放款日期",financeInfo.getContractID(),financeInfo.getLoanNoteID(),payeeInfo.getAccountID(),financeInfo.getPayDate(),financeInfo.getLetOutCode(),3,3,"lContractID"," colSpan=2  ","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
			}
			if( lTransType == OBConstant.QueryInstrType.YTLOANRECEIVE)
			{
				Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","放款日期",financeInfo.getContractID(),financeInfo.getLoanNoteID(),payeeInfo.getAccountID(),financeInfo.getPayDate(),financeInfo.getLetOutCode(),4,5,"lContractID"," colSpan=2  ","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
			}
		  %>
            <TD width="5" ></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
        
<%
	if (lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE || lTransType == OBConstant.QueryInstrType.TRUSTLOANRECEIVE||lTransType == OBConstant.QueryInstrType.YTLOANRECEIVE)
	{
%>  
          <TR > 
            <TD width=5 height=25></TD>           
      		<TD class=graytext width=110  height=25>本金支取金额：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
			<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dAmount','<%=DataFormat.formatDisabledAmount(financeInfo.getAmount())%>','tsExecute','',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
<%
	}
	if (lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE || lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
	{
%>
		  <TR > 
            <TD width=5 height=25></TD>           
      		<TD class=graytext width=110  height=25>实付贷款利息：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
			  <SCRIPT language=JavaScript>
				createAmountCtrl('frm','dRealInterest','<%=DataFormat.formatDisabledAmount(financeInfo.getRealInterest())%>','dRealCompoundInterest','',<%=sessionMng.m_lCurrencyID%>);
			 </SCRIPT>
			</TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>

 	  	<TR > 
            <TD width=5 height=25></TD>              		
     		<TD class=graytext width=110  height=25>实付复利：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
				<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dRealCompoundInterest','<%=DataFormat.formatDisabledAmount(financeInfo.getRealCompoundInterest())%>','dRealOverDueInterest','',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT>
				
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
		  
		  <TR > 
            <TD width=5 height=25></TD>           
	      	<TD class=graytext width=110  height=25>实付罚息：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
				<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dRealOverDueInterest','<%=DataFormat.formatDisabledAmount(financeInfo.getRealOverdueInterest())%>','dRealCommission','',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT>

			</TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>

		  <TR > 
            <TD width=5 height=25></TD>           	
      		<TD class=graytext width=110  height=25>实付手续费：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
				<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dRealCommission','<%=DataFormat.formatDisabledAmount(financeInfo.getRealCommission())%>','tsExecute','',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT>
			</TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>		  		  

<%
		if(lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
		{
%>
		  <TR > 
            <TD width=5 height=25></TD>           	
      		<TD class=graytext width=110  height=25>实付担保费：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext width=430  colSpan=2 height=25> 
				<SCRIPT language=JavaScript>
				createAmountCtrl('frm','dRealSuretyFee','<%=DataFormat.formatDisabledAmount(financeInfo.getRealSuretyFee())%>','tsClearInterest','',<%=sessionMng.m_lCurrencyID%>);
			</SCRIPT>
				
			</TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>

		  <TR > 
            <TD width=5 height=25></TD>           	   		
      		<TD class=graytext width=110  height=25>结息日：</TD>
            <TD class=graytext width=20  height=25> <DIV align=right></DIV></TD>
            
      <TD class=graytext width=430  colSpan=2 height=25> 
      	<fs_c:calendar 
			          	    name="tsClearInterest"
				          	value="" 
				          	properties="nextfield ='tsExecute'" 
				          	size="20"/>
		  	  <script>
	          		$('#tsClearInterest').val('<%=(financeInfo.getID() == -1) ? DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)):DataFormat.getDateString(financeInfo.getClearInterest())%>');
	          	</script>
      </TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>		  		 		  		  		  
<%
		}
	}
%>
          <TR > 
            <TD width=5 height=25></TD>
            <TD class=graytext width=130  colSpan=2 
          height=25>执行日：</TD>
            <TD class=graytext width=430  height=25>
           		 <fs_c:calendar 
		          	    name="tsExecute"
			          	value="<%=(financeInfo.getID() == -1) ? DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)):DataFormat.getDateString(financeInfo.getExecuteDate())%>" 
			          	properties="nextfield =''" 
			          	size="18"/>
			      <script>
	          		$('#tsExecute').val('<%=(financeInfo.getID() == -1) ? DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)):DataFormat.getDateString(financeInfo.getExecuteDate())%>');
	          	</script>
			</TD>
            <TD width=5 height=25></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>               
        </TABLE>
        <br>
              <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            
          </td>
          <td width="62">
            <div align="right">			
			<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
			<input type="button" name="Submitv00204" value=" 匹  配 " class="button" onClick="Javascript:matchme();">
			</div>
          </td>
        </tr>
      </table>

	  <input type="hidden" name="nPayerAccountID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">    
	  <input type="hidden" name="SelectType" value="<%= lTransType %>"> 
	  <input type="Hidden" name="lPayeeAcctID" value="<%=(financeInfo.getID() == -1) ? payeeInfo.getAccountID() : financeInfo.getPayeeAcctID() %>"> 
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">	  
</form>
<script language="javascript">
function validate()
{
   //付款方账号
   if(frm.sPayerAccountNoZoomCtrl.value=="")
   {
     alert("请选择付款方账号！");
	 frm.sPayerAccountNoZoomCtrl.focus();
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
   if(frm.lLoanNoteIDCtrl.value=="" || frm.lLoanNoteIDCtrl.value=="-1")
   {
      alert("请选择放款日期！");
	  frm.lLoanNoteIDCtrl.focus();
	  return false;
   }
   
   /* 结息日校验 */
  	if(frm.SelectType.value == <%=OBConstant.QueryInstrType.INTERESTFEEPAYMENT%>)
	{
		var tsClearInterest = frm.tsClearInterest.value;
		if(chkdate(tsClearInterest) == 0)
		{
			alert("结息日格式不正确，请重新录入");
			frm.tsClearInterest.focus();
			return false;
		}
	}
	/* 执行日校验 */
	var tsExecute = frm.tsExecute.value;
	if(tsExecute=="")
		{
			alert("执行日不能为空，请录入");
			frm.tsExecute.focus();
			return false;
		}
	if(chkdate(tsExecute) == 0)
	{
		alert("执行日格式不正确，请重新录入");
		frm.tsExecute.focus();
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

function matchme()
{     
	 if(!validate())
	 {
	   return;
	 }
	
<%--	 if(confirm("是否匹配？"))--%>
<%--	 {--%>
	  frm.action="ck007-c.jsp"
	  showSending();
	  frm.submit();
<%--	  }--%>

}

firstFocus(frm.sPayerAccountNoZoomCtrl);
//setSubmitFunction("matchme()");
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