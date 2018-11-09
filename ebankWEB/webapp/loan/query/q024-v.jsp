<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.system.approval.bizlogic.*,
				com.iss.itreasury.system.approval.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	try{    
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
           
		//定义变量，获取请求参数

		String strTmp = "";
		String strControl = "";
		String isFirst = "";
		String backpage = "";
		long lAction = 0;
		long lReturn = -1;
						
		long lClientID = -1;		//客户标示
		long lDiscountID = -1;		//贴现标示
			
		long nApplyDiscountPO = 0;
		long nBankAccepPO = 0;
		long nBizAcceptPO = 0;
		double dApplyDiscountAmount = 0;
									
		String sDiscountPurpose = "";		
		String sDiscountReason = "";
		
		double mExamineAmount=0;
        double fDiscountRate=0;
        double mCheckAmount=0;
	    Timestamp tsDiscountDate = null;			//贴现日
		long   lNextCheckUserID = -1;
		String sOpinion = "";

		//模块类型
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		
		Collection temp = null;
		
		//贴现EJB
		DiscountLoanInfo  dli = new DiscountLoanInfo ();
		OBLoanQuery Discount=new OBLoanQuery();
		
		//客户EJB
        ClientInfo			   ci = new ClientInfo ();
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem loanCommonSetting = home.create();
        
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
        				
///////control/////////////////////////////////////////////////////////////////	
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lDiscountID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lDiscountID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("backpage");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 backpage = strTmp.trim();
		}
		
////////view/////////////////////////////////////////////////////////////////////
		if (strControl.equals("view"))
		{
			dli = Discount.findDiscountByID(lDiscountID);
			lClientID = dli.getApplyClientID();
			ci = loanCommonSetting.findClientByID(lClientID);
       	}

///////////////////////////////////////////////////////////////////////////////////////////////////

		OBHtml.showOBHomeHead(out,sessionMng,"贴现申请",Constant.YesOrNo.NO);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">

<TABLE border=0 class=top width="83%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B>贴现申请——申请书审核</B></TD></TR>
  <TR>
    <TD height=616 vAlign=top>
      <TABLE align=center border=0 width=730>
        <TBODY>        
        <TR>
          <TD colSpan=11 height=207>
            <TABLE cellPadding=0 width=730>
              <TBODY>
              <TR>
                <TD height=24 width=200>贴现申请编号：<%if ((dli!=null)&&(dli.getDiscountCode()!=null)) {out.println(dli.getDiscountCode());} else {out.println("");}%></TD>
                <TD colSpan=5 height=24 align="center">贴现合同编号：<%if ((dli!=null)&&(dli.getContractCode()!=null)) {out.println(dli.getContractCode());} else {out.println("");}%></TD></TR>
              <TR>
                <TD colSpan=6 height=24>
                  <DIV align=center>
                  <HR align=center SIZE=2 width="100%">
                  </DIV>
                </TD></TR>
              <TR>
                <TD height=19 width=129> <P>单位名称：</P></TD>
                <TD height=19 width=251 colSpan=5>
                  <P><INPUT class=box disabled name=tf_dw3 size="80" value="<%=DataFormat.formatString(ci.getName())%>"></P></TD>
			  </TR>
			  <TR>
                <TD height=19 width=126>
				<%
				String sOfficeName = "";
				%>
				<%=sOfficeName%>
				</TD>
                <TD colSpan=5 height=19><INPUT class=box disabled 
                  name=tf_dw33 value="<%=DataFormat.formatString(Env.getClientName())%>"></TD></TR>
              <TR>
                <TD height=2 width=129>
                  <P>客户编号：</P></TD>
                <TD height=2 width=251>
                  <P><INPUT class=box disabled name=tf_dw32 
                  value="<%=DataFormat.formatString(ci.getCode())%>"></P></TD>
                <TD height=2 width=126>
                  <P>营业执照号码：</P></TD>
                <TD height=2 width=101><INPUT 
                  class=box disabled name=textfield223 value="<%=DataFormat.formatString(ci.getLicenceCode())%>"> 
                  </TD>
                <TD colSpan=2 height=2>&nbsp;</TD></TR>
              <TR>
                <TD height=8 width=129>
				<%
				String strMagnifierNameAccount = "";
				%>
				<%=strMagnifierNameAccount%>
				</TD>
                <TD height=8 width=251><INPUT 
                  class=box disabled value="<%=DataFormat.formatString(ci.getAccount())%>"></TD>
                <TD height=8 width=126>&nbsp;</TD>
                <TD height=8 width=101>&nbsp;</TD>
                <TD colSpan=2 height=8>&nbsp;</TD></TR>
              <TR>
                <TD height=30 width=129>
                  <P>开户银行：</P></TD>
                <TD height=30 width=251>
                  <P><INPUT class=box disabled value="<%=DataFormat.formatString(ci.getBank1())%>"></P></TD>
                <TD height=30 width=126>
                  <P>开户银行账号：</P></TD>
                <TD colSpan=3 height=30>
				  <INPUT class=box disabled 
                  value="<%=DataFormat.formatString(ci.getBankAccount1())%>"></TD></TR>
          
              <TR>
                <TD colSpan=2 height=2>
				<INPUT class=button name=Submit32 onclick="MM_goToURL('self','q025-v.jsp?lClientID=<%=lClientID%>&lDiscountID=<%=lDiscountID%>&strDiscountCode=<%=dli.getDiscountCode()%>&control=view&backpage=q024-v');" type=button value="贴现单位资料">
				</TD>
                <TD colSpan=3 height=2>
                  <P><BR></P></TD>
                <TD height=2 width=174>&nbsp;</TD></TR></TBODY></TABLE>
            <HR>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=104>
            <TABLE width="104%">
              <TBODY>
              <TR vAlign=center>
                <TD height=29 width="14%">申请贴现汇票：</TD>
                <TD height=29 width="13%">共 <INPUT class=box disabled 
                  name=textfield282242 size=3 value="<%if ((dli!=null)&&(dli.getApplyDiscountPO()>0)) {out.println(dli.getApplyDiscountPO());} else {out.println("0");}%>"> 张</TD>
                <TD height=29 width="8%">其中：</TD>
                <TD height=29 width="15%">银行承兑汇票：</TD>
                <TD height=29 width="11%"><INPUT class=box disabled 
                  name=textfield2822322 size=3 value="<%if ((dli!=null)&&(dli.getBankAccepPO()>0)) {out.println(dli.getBankAccepPO());} else {out.println("0");}%>"> 张</TD>
                <TD height=29 width="15%">商业承兑汇票：</TD>
                <TD height=29 vAlign=center width="24%"><INPUT class=box disabled 
                  name=textfield28222242 size=3 value="<%if ((dli!=null)&&(dli.getBizAcceptPO()>0)) {out.println(dli.getBizAcceptPO());} else {out.println("0");}%>"> 
            张</TD></TR></TBODY></TABLE>
            <TABLE border=0 width="100%">
             <TBODY>
              <TR>
                <TD height=41 width="14%">申请贴现金额：</TD>
                <TD height=41 width="2%">
                  <DIV align=right>￥</DIV></TD>
                <TD height=41 width="24%"><INPUT class=tar 
                  disabled name=textfield2822232 size=18 value="<%if ((dli!=null)&&(dli.getApplyDiscountAmount()>0)) {out.println(DataFormat.formatDisabledAmount(dli.getApplyDiscountAmount()));} else {out.println("0.00");}%>"> </TD>
                <TD height=41 width="17%">&nbsp;</TD>
                <TD height=41 width="3%">&nbsp;</TD>
                <TD height=41 width="40%">&nbsp;</TD>
			  </TR>
			  <TR>
                <TD height=41 width="14%">贴现开始日期：</TD>
                <TD height=41 width="2%">&nbsp;</TD>
				<TD height=41 width="24%"><INPUT class=box 
                  disabled name=textfield2822232 size=18 value="<%if ((dli!=null)&&(dli.getDiscountStartDate()!=null)) {out.println(DataFormat.getDateString(dli.getDiscountStartDate()));} else {out.println("");}%>"></TD>
                <TD height=41 width="17%">贴现到期日期：</TD>
                <TD height=41 colspan="2"><INPUT class=box 
                  disabled name=textfield28222321 size=18 value="<%if ((dli!=null)&&(dli.getDiscountEndDate()!=null)) {out.println(DataFormat.getDateString(dli.getDiscountEndDate()));} else {out.println("");}%>"> </TD>
			  </TR>
			 </TBODY></TABLE></TD></TR>
        <TR>
          <TD colSpan=11 height=14>&nbsp;贴现原因：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<TEXTAREA class=box cols=65 disabled name=textarea><%if ((dli!=null)&&(dli.getDiscountReason()!=null)) {out.println(dli.getDiscountReason());} else {out.println("");}%></TEXTAREA> 
            </TD></TR>
        <TR>
          <TD colSpan=11 height=14>&nbsp;贴现用途：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<TEXTAREA class=box cols=65 disabled name=textarea2><%if ((dli!=null)&&(dli.getDiscountPurpose()!=null)) {out.println(dli.getDiscountPurpose());} else {out.println("");}%></TEXTAREA> 
            </TD></TR>
        <TR>
          <TD colSpan=11 height=14>&nbsp;</TD></TR>
        <TR>
          <TD colSpan=11 height=14>
            <HR>
          </TD></TR>
		<TR>
          <TD colSpan=11 height=2><U>贴现票据明细表</U></TD></TR>
        <TR>
          <TD colSpan=11 height=2>
		  	<INPUT class=button name=Submit2 onclick="MM_goToURL('self','q026-v.jsp?lDiscountID=<%=lDiscountID%>&sDiscountCode=<%=dli.getDiscountCode()%>&control=view&backurl=q024-v');" type=button value="贴现票据明细表">
		  	<INPUT class=button name=Submit42242 onclick="MM_goToURL('self','q027-v.jsp?lDiscountID=<%=lDiscountID%>&sDiscountCode=<%=dli.getDiscountCode()%>&control=view&backurl=q024-v');" type=button value="贴现票据计息明细表">
          </TD></TR>
        <TR>
          <TD colSpan=11 height=2>
            <HR>
          </TD></TR>
<TR>
          <TD colSpan=11 height=2>
<%//--------------------------------------------------------------------------------------------%>

            <table width="100%" border="1" bordercolor="#999999" cellpadding="0" cellspacing="0">
              <tr> 
                <td>
<TABLE border=0 width="100%">
  <TR>
    <TD colSpan=3 height=28><U>本公司审批意见</U></TD>
    <TD height=28 width=156>&nbsp;</TD>
    <TD height=28 width=218>&nbsp;</TD></TR>
  <TR>
    <TD height=36><font color='#FF0000'>* </font>票面金额：￥</TD>
	<TD height=36>

	<script language="javascript">
			createAmountCtrl("frm","mExamineAmount","<%=DataFormat.formatAmount(dli.getExamineAmount())%>","","daxie");
            frm.mExamineAmount.disabled = true; 
	</script>

	</TD>
    <TD height=36 width=300>（大写） 

	<INPUT class=box disabled name="daxie" size=28 value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(dli.getExamineAmount()))%>">
	
	</TD>
    <TD colSpan=2 height=36><font color='#FF0000'>* </font>贴现利率： 
	<INPUT class=tar disabled name="fDiscountRate" size=10 value="<%=DataFormat.formatRate(dli.getDiscountRate())%>" onfocus="clearRate('fDiscountRate');" onchange="clearAmount();"> 
      %</TD></TR>
  <TR>
    <TD colSpan=2 height=31>
	
	</TD>
	<TD colSpan=2 height=31><font color='#FF0000'>* </font>实付贴现额： ￥ 
	<INPUT class=tar name="mCheckAmount" size=18 readonly value="<%=DataFormat.formatDisabledAmount(dli.getCheckAmount())%>">
	</TD>    
	<TD height=31 width=118>&nbsp;</TD></TR>
  <TR>
    <TD colSpan=5 height=14>
      <HR>
    </TD></TR>
  <TR>
    <TD height=80 width=192>历史审核意见：</TD>
    <TD colSpan=4 height=80>
      <TABLE align=left border=0 class=ItemList height=70 width=532>        
        <TR class="tableHeader">
          <TD class=ItemTitle height=20 width="12%">
            <DIV align=center>序列号</DIV></TD>
          <TD class=ItemTitle height=20 width="21%">
            <DIV align=center>意见内容</DIV></TD>
          <TD class=ItemTitle height=20 width="21%">
            <DIV align=center>审核人</DIV></TD>
          <TD class=ItemTitle height=20 width="20%">
            <DIV align=center>审核决定</DIV></TD>
          <TD class=ItemTitle height=20 width="26%">
            <DIV align=center>日期和时间</DIV></TD>
		</TR>
<% /************************* Information Display********************************/ %>	
	<%	
				temp = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lDiscountID,-1);

				if( (temp != null) && (temp.size() > 0) )
                {
					Iterator it = temp.iterator();
                    while (it.hasNext() )
					{
						tracinginfo = (ApprovalTracingInfo) it.next();
	%>	
	<TR>
          <TD class=ItemBody height=18 width="12%">
            <DIV align=center>
			<%=tracinginfo.getSerialID()%>
			</DIV></TD>
          <TD class=ItemBody height=18 width="21%">
            <DIV align=center>
			<%=DataFormat.formatString(tracinginfo.getOpinion())%>
			</DIV></TD>
          <TD class=ItemBody height=18 width="21%">
            <DIV align=center>
			<%=DataFormat.formatString(tracinginfo.getUserName())%>
			</DIV></TD>
          <TD class=ItemBody height=18 width="20%">
            <DIV align=center>
			<%=Constant.ApprovalDecision.getName(tracinginfo.getResultID())%>
			</DIV></TD>
          <TD class=ItemBody height=18 width="26%">
            <DIV align=center>
			<%=DataFormat.getDateTimeString(tracinginfo.getApprovalDate())%>
			</DIV></TD></TR>
	<% /*************************The If and while 's End ********************************/ %>
        <%
		           }
	            }
			   else{
			   %>
        <TR>
          <TD class=ItemBody height=18 width="12%">
            <DIV align=center></DIV></TD>
          <TD class=ItemBody height=18 width="21%">
            <DIV align=center></DIV></TD>
          <TD class=ItemBody height=18 width="21%">
            <DIV align=center></DIV></TD>
          <TD class=ItemBody height=18 width="20%">
            <DIV align=center></DIV></TD>
          <TD class=ItemBody height=18 width="26%">
            <DIV align=center></DIV></TD></TR>
			   <%			   
			   }
        %>
				  
</TABLE></TD></TR>

 
  </TABLE>
                </td>
              </tr>
            </table>
<%//--------------------------------------------------------------------------------------------%>
          </TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
		  <TD align=right height=2 colSpan=10>		  
            <input type="button" name="SubmitPrint" class="button" onclick="printIt('q028-p.jsp?control=view&lDiscountID=<%=lDiscountID%>&lClientID=<%=lClientID%>&isSM=<%=Constant.YesOrNo.NO%>');" value=" 打 印 ">
			<% if (backpage != null && backpage.length() > 0) { %>
            <input type="button" name="SubmitBack" value=" 返 回 " class="button" onclick="window.location.href='<%=backpage%>.jsp?control=view'">
			<% } else { %>
			<input type="button" name="SubmitClose" value=" 关 闭 " class="button" onclick="window.close()">
			<% } %>
		  </TD>          
          </TR>		  
		<TR>
          <TD height=2 colspan=11>&nbsp;</TD>
        </TR>
		<TR>
          <TD height=2 width=1>&nbsp;</TD>
		  <TD align=center height=2 colspan=10>
		  录入人：<%=DataFormat.formatString(dli.getInputUserName())%>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  录入时间：<%if ((dli!=null)&&(dli.getInputDate()!=null)) {out.println(DataFormat.getDateString(dli.getInputDate()));} else {out.println("");}%>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
　　　　　状态：<%=LOANConstant.LoanStatus.getName(dli.getStatusID())%>			
		  </TD>          
        </TR>
			  </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>

<input type="hidden" name="control" value="view">

	
<script language="JavaScript">

function printIt(url)
{
	window.open(url,"popup","width=800,height=600,scrollbars=0,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");
}

</script>

<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贴现申请", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>