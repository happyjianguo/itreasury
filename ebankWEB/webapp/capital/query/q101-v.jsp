
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>	
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QuerySubFixedInfo" %>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QSubAccountDao" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<% String strContext = request.getContextPath();%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<safety:resources />

    <%
	Log.print("\n\n*******进入页面--settlement/query/view/v031.jsp*******\n\n");
	String strTitle = "定期存单信息";
	
    try
    {
		Log.print("\n\n*******进入页面--capital/query/q101-v.jsp*******\n\n");
		
		/* 实现菜单控制 */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
		    lShowMenu = OBConstant.ShowMenu.NO;
		}
		

        
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
	%>

    <%
		
		//页面控制变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;

		//定义变量
		long ID=0;  //定期账号
		String AF_sDepositNo=""; //定期存款单据号
		Timestamp AF_dtStart=null;//起始日期
		long AF_nDepositTerm=0;//定期存款期限
		Timestamp AF_dtEnd=null;//到期日期
		double AF_mRate=0.0;//利率
		double mOpenAmount=0.0;//存单金额
		double mBalance=0.0;//当前余额
		long nStatusID=0;//定期存款状态
		Timestamp dtOpen=null;//开户日期
		Timestamp dtFinish=null;//清户日期
		double AF_mPreDrawInterest=0.0;	//已计提利息

		//取得传过来的对象
		QuerySubFixedInfo info = (QuerySubFixedInfo)request.getAttribute("searchResults");
		if(info!=null)
		{			
            ID=info.getID();
			
			AF_sDepositNo=info.getAF_sDepositNo();
			AF_dtStart=info.getAF_dtStart();
			AF_nDepositTerm=info.getAF_nDepositTerm();
			AF_dtEnd=info.getAF_dtEnd();
			AF_mRate=info.getAF_mRate();
			mOpenAmount=info.getMOpenAmount();
			mBalance=info.getMBalance();
			nStatusID=info.getNStatusID();
			dtOpen=info.getDtOpen();
			dtFinish=info.getDtFinish();
			AF_mPreDrawInterest=info.getAF_mPreDrawInterest();
		}
    %>

<form name=fm >	
<!--利用隐藏控件，来控制页面-->
<input type="hidden" name="strSuccessPageURL" value="../view/v005.jsp">
<input type="hidden" name="strFailPageURL" value="../view/v008.jsp">	
<input name="strAction" type="hidden" value="">
<TABLE border=0 class="title_top" height=300 width="99%">
  <TBODY>
  <TR>
    <TD  width="100%">
	    <table  border="0" cellspacing="0" cellpadding="0" class=title_Top1>
	      <tr>
	        <td width="124" background="/webob/graphics/new_til_bg.gif" class=title>　<span class="txt_til2"><%=strTitle %></span></td>
	      </tr>
	    </table>
    <BR>
      <TABLE align=center border=0 width="100%" class="normal">
        <TBODY>
        <TR >
          <TD height=20 width="16%" nowrap>定期客户编号：</TD>
		  <td width=30%>
				<input class=box name="txtFixedClientID" value="<%=NameRef.getClientCodeByAccountID(info.getAccountID())%>"  disabled>
		  </td>
          <TD height=20 width="16%" nowrap>定期客户名称：</TD>
          <TD height=20 width="39%">
			<INPUT class=box  name=txtFixedClientName size=30 value="<%=NameRef.getClientNameByAccountID(info.getAccountID())%>" disabled> 
	      </TD>
	    </TR>
     
	    <TR >
          <TD height=20 nowrap>定期账号：</TD>
		  <td >
			<input class=box name="txtFixedAccountID" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>"  disabled>
		  </td>
		  <td height="20" bordercolor="#E8E8E8">&nbsp;</td>
          <td height="20" bordercolor="#E8E8E8">&nbsp;</td>
        <TR >
          <TD height=20 nowrap>定期存款单据号： </TD>
          <TD height=20 ><INPUT class=box name="txtFixedDepositBillNo" value="<%=(AF_sDepositNo==null || AF_sDepositNo.equals(""))?"&nbsp;":AF_sDepositNo%>" maxlength=50 onfocus="nextfield ='txtDateFixedStart';" disabled>  </TD>
          <TD height=20 ></TD>
          <TD height=20 ></TD>
		</TR>
        <TR >
          <TD height=20 nowrap>起始日期：</TD>
          <TD height=20 >
			<INPUT class=box name="txtDateFixedStart" size=18 onBlur="dateChange(fm)" value="<%=DataFormat.formatDate(AF_dtStart)%>" maxlength=20 onfocus="nextfield ='slcFixedInterval';"  disabled>  
		  </TD>
          <TD height=20 >&nbsp;</TD>
          <TD height=20 >&nbsp;</TD>
		</TR>
        <TR >
          <TD height=20 nowrap>定期存款期限：</TD>
          <TD height=20 >
			 <%				
				SETTHTML.showFixedDepositMonthListCtrl(out,"slcFixedInterval",SETTConstant.TransQueryType.FIXED,AF_nDepositTerm,true,"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			 %>
			 <script language="JavaScript">
				 document.fm.slcFixedInterval.disabled=true;
			 </script>
			
		  </TD>
          <TD height=20 nowrap>到期日期：</TD>
          <TD height=20 >
			<INPUT class=box disabled 
            name=txtDateFixedEndCtrl size=18 value="<%=DataFormat.formatDate(AF_dtEnd)%>"> <input type="hidden" name="txtDateFixedEnd">
		  </TD>
		</TR>
		<TR >	
		  <TD height=29 nowrap>利率：</TD>
		  <TD height=29 >
			<input class=box name="txtInterestRate" value="<%=(AF_mRate>=0.00?DataFormat.formatRate(AF_mRate):"0.00")%>" onfocus="nextfield='rdoSource(0)'"  disabled>%
		  </TD>
		  <TD height=29 ></TD>
		  <TD height=29 >&nbsp;</TD>
		</TR>
					<TR vAlign=center>
						<TD height=20 width="16%">存单金额：<%=sessionMng.m_strCurrencySymbol%></TD>
					  	<TD height=20 width="30%"> 
							<input class=box name="txtMoney" value="<%=DataFormat.formatDisabledAmount(mOpenAmount)%>" onfocus="nextfield='rdoSource(0)'"  disabled>
					  	</TD>
					  	<TD height=20 width="16%" nowrap>当前余额：<%=sessionMng.m_strCurrencySymbol%></TD>
					  	<TD height=20 width="38%"> 					  
					    	<input class=box name="txtMoney" value="<%=DataFormat.formatDisabledAmount(mBalance)%>" onfocus="nextfield='rdoSource(0)'"  disabled>
					  	</TD>
				   	</TR>
				   	<TR vAlign=center>
					  	<TD height=20 nowrap>定期存款状态：</TD>
					  	<TD height=20>
							<INPUT class=box disabled name="txtDateInterestStartCtrl" value="<%=SETTConstant.SubAccountStatus.getName(nStatusID)%>"> 
					  	</TD>
					  	<TD align=left height=20 vAlign=center >已计提利息：<%=sessionMng.m_strCurrencySymbol%> </TD>
           				<TD align=left height=20 vAlign=center >
							<INPUT class=box disabled name=textfield235 value="<%=DataFormat.formatDisabledAmount(AF_mPreDrawInterest)%>"> 
		   				</TD>
					</TR>
         			<TR vAlign=center>
         				<TD height=20>开户日期：</TD>					  
			  			<TD height=20>
			   				<INPUT class=box disabled name="txtDateInterestStartCtrl" value="<%=DataFormat.formatDate(dtOpen)%>"> </TD>
			  			<TD height=20>清户日期： </TD>
			  			<TD height=20><INPUT class=box name=txtDateInterestExecute value="<%=DataFormat.formatDate(dtFinish)%>" maxlength=20 onfocus="nextfield ='txtNote';"  disabled>
			  			</TD>
					</TR>
			        <TR vAlign=center>
			          	<TD colSpan=6 height=2>
		            		<DIV align=right>
						<%--   <INPUT class=button name=Submit222 onClick="viewTransDetail();" type=button value=" 交易历史明细 " >  --%>
						  	<INPUT class=button name=Submit43 onclick="Javascript:window.close();" type=button value=" 关 闭 "> 
			            	</DIV>
					  	</TD>
					</TR>
</TBODY></TABLE>
</TD></TR>
</TBODY></TABLE>
</form>	

<form name="frmToTransDetail" method="post" target="_blank">
<input type="hidden" name="strSuccessPageURL" value="/settlement/query/view/v031-1.jsp">
<input type="hidden" name="strFailPageURL" value="/settlement/query/view/v031.jsp">

<input type="hidden" name="lPayAccountIDEndCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lPayAccountIDStartCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lReceiveAccountIDEndCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lReceiveAccountIDStartCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="strDepositNo" value="<%=AF_sDepositNo%>">
</form>
<script language="JavaScript">
function viewTransDetail()
{
	frmToTransDetail.action = "../control/c032.jsp";
	//showSending();
	frmToTransDetail.submit();
}
</script>

<%
OBHtml.showOBHomeEnd(out);
}
catch( Exception exp ){
    //OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
<%@ include file="/common/SignValidate.inc" %>