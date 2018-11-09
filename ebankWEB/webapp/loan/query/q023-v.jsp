<%
/**
 * 页面名称 ：q023-v.jsp
 * 页面功能 : 贷款申请查询结果
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 转入页面 : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loaninterestsetting.dataentity.*,
	com.iss.itreasury.loan.query.dataentity.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp" %>  
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
   try{
    	
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
		
   		Timestamp tsToday = Env.getSystemDate();	
   		
   		ArrayList queryResults = null;
		queryResults = (ArrayList)request.getAttribute("Result");
		QuerySumInfo sumInfo = (QuerySumInfo)request.getAttribute("SumInfo");

        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"贷款申请查询",Constant.YesOrNo.YES);
        boolean isdq=false;
        String queryLevel=GetParam(request,"queryLevel","low");
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<TABLE border=0 class=top width="98%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=29><B>贷款申请查询</B></TD></TR>
  <TR>
    <TD colSpan=2 >

      <TABLE border=0 borderColor=#999999 class=ItemList width=730>
        <TBODY>
        <TR align="center" class="tableHeader">
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'1');">贷款类型</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'2');">申请书编号</A></FONT></TD>
          <% if (!isdq) { %> 
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'3');">借款单位</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'4');">委托单位</A></FONT></TD>
          <%}else{%>
          <TD class=ItemTitle height=17  colspan=2><FONT size=2><A href="javascript:go(document.turnPageFrm,'3');">借款单位</A></FONT></TD>
          <%}%>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'5');">贷款金额</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'6');">计划执行利率</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'8');">贷款日期</A></FONT></TD>		  
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'7');">期限</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'9');">申请提交日期</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'10');">申请状态</A></FONT></TD>
          <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'11');">业务负责人</A></FONT></TD>
		  <TD class=ItemTitle height=17 ><FONT size=2><A href="javascript:go(document.turnPageFrm,'12');">下一级审核人</A></FONT></TD>
		  </TR>
 <%  
    if(queryResults != null && queryResults.size()>0)
	{
		String strType="";
		String applyCode="";
		String borrowClientName="";
		String consignClientName="";
		String strAmount="";
		String strRate="";
		String strDate="";
		String strInterval="";
		String strInputDate="";
		String strStatus="";
		String inputUserName="";
		String strNextCheckUser="";
		double adjustRate=0;
		long bankInterestID=-1;
		double interestRate=0;
		double baseRate=0;
		long lID=-1;
		long lTypeID=-1;
		LoanApplyInfo appInfo=null;
		// add by fxzhang 2007-1-3
		double staidAdjustRate=0;

		int count=queryResults.size();
		for( int i=0; queryResults != null && i<count; i++ )
		{ 
			appInfo = (LoanApplyInfo )queryResults.get(i);
			lID=appInfo.getID();
			lTypeID=appInfo.getTypeID();
			strType = LOANConstant.LoanType.getName(appInfo.getTypeID());
			applyCode=appInfo.getApplyCode();
			borrowClientName=appInfo.getBorrowClientName();
			consignClientName=appInfo.getConsignClientName();
			strAmount=DataFormat.formatListAmount(appInfo.getLoanAmount());
			interestRate=appInfo.getInterestRate();
			bankInterestID=appInfo.getBankInterestID();
			adjustRate=appInfo.getAdjustRate();
			// add by fxzhang 2007-1-3
			staidAdjustRate=appInfo.getStaidAdjustRate();

			
			if ( bankInterestID>0)
			{
    			OBLoanQuery interestSet=new OBLoanQuery();
    			InterestRateInfo rateInfo=interestSet.findInterestRateByID(bankInterestID);
    			baseRate=rateInfo.getInterestRate();
    			
    			// modified by fxzhang 2007-1-3
    			
    			//interestRate=baseRate * (1 + adjustRate/100);	    		
    			interestRate=baseRate * (1 + adjustRate/100)+staidAdjustRate;	//执行利率		
			}
			strRate=DataFormat.formatRate(interestRate,6);
			strDate=DataFormat.getDateString(appInfo.getStartDate())+"-"+DataFormat.getDateString(appInfo.getEndDate());
			if ( appInfo.getIntervalNum()<=0 )
				strInterval="";
			else	
				strInterval=String.valueOf(appInfo.getIntervalNum());
			strInputDate=DataFormat.getDateString(appInfo.getInputDate());
			strStatus=LOANConstant.LoanStatus.getName(appInfo.getStatusID());
			inputUserName=appInfo.getInputUserName();
			strNextCheckUser=appInfo.getLastCheckUserName();
			if (strNextCheckUser==null)
				strNextCheckUser="";
			
 %>		         
        <TR align="center"  borderColor=#999999>
          <TD height=17 class=ItemBody width=107><FONT size=2><%=strType%></FONT></TD>
          <% if ( appInfo.getTypeID()==LOANConstant.LoanType.TX ) { %>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <A href="javascript:confirmTX(<%=lTypeID%>,<%=lID%>);"><%=applyCode%></A> </FONT></TD>
          <% } else { %>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <A href="javascript:confirmLoan(<%=lTypeID%>,<%=lID%>);"><%=applyCode%></A> </FONT></TD>
          <% } %>
          <% if (!isdq) { %> 
          <TD height=17 align="center"  class=ItemBody ><FONT size=2> <%=(borrowClientName==null)?"":borrowClientName%></FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <%=(consignClientName==null)?"":consignClientName%></FONT></TD>
          <%}else{%>
          <TD height=17 align="center"  class=ItemBody colspan=2><FONT size=2> <%=(borrowClientName==null)?"":borrowClientName%></FONT></TD>
          <%}%>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <%=strAmount%></FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <%=strRate%>% </FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> <%=strDate%> </FONT></TD>		  
          <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=strInterval%> </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=strInputDate%> </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=strStatus%> </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=inputUserName%></FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=strNextCheckUser%></FONT></TD>
		  </TR>
<%
		}
	}
	else
	{
%>
        <TR align="center"  borderColor=#999999>
          <TD height=17 class=ItemBody width=107><FONT size=2></FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2>  </FONT></TD>

          <% if (!isdq) { %> 
          <TD height=17 align="center"  class=ItemBody ><FONT size=2>   </FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> </FONT></TD>
          <%}else{%>
          <TD height=17 align="center"  class=ItemBody colspan=2><FONT size=2>   </FONT></TD>
          <%}%>
          <TD height=17 align="center"  class=ItemBody><FONT size=2>  </FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2> </FONT></TD>
          <TD height=17 align="center"  class=ItemBody><FONT size=2>  </FONT></TD>		  
          <TD height=17 align="center" class=ItemBody ><FONT size=2>  </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2>   </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2>  </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> </FONT></TD>
		  </TR>
<%
	}
%>		
		
  		 <tr>
				<td colspan=13>
 <%
	//输出分页控件
	OBHtml.showTurnPageControl(out,"turnPageFrm","q022-c.jsp?queryLevel="+queryLevel,sumInfo.getAllRecord(),(termInfo==null?1:termInfo.getTxtPageNo()),Constant.PageControl.CODE_PAGELINECOUNT,(termInfo==null?99:termInfo.getLOrderParam()),(termInfo==null?1:termInfo.getLDesc()));
%>
	 	</TD>
		</TR>
  		       
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=12 >
		     <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar width="100%">
              <TBODY>
              <TR>
                <TD>
                  <B>总申请</B><FONT size=2>贷款金额</FONT>:<B><FONT size=2> 
                  <INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalApplyAmount())%>" disabled> 
				  </FONT></B>
				</TD>
                <TD >&nbsp;</TD></TR>
              <TR>
                <TD ></TD>
                <TD ></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
          </TD>
		</TR>
  <TR>
    <TD height=36><FONT size=2><BR>查询数据时间为: <%=DataFormat.getDateString(tsToday)%></FONT> 
      <DIV align=right></DIV></TD>
    <TD height=36>
      <DIV align=right><B>
	  
      </B><INPUT class=button name=Submit2 onclick="backto()" type="button" value=" 返 回 " > 
      </DIV></TD></TR></TBODY></TABLE>

<script language="JavaScript">

function confirmLoan(lType,lID)
{
	window.open('q004-c.jsp?type='+lType+'&lLoanID='+lID+'&txtAction=query', '查看贷款资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function confirmTX(lType,lID)
{
	window.open('q024-v.jsp?control=view&lDiscountID='+lID, '查看贴现基本资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function backto()
{
<%
	if ( queryLevel.equals("low") ){
%>	
	window.location.href="../query/q020-v.jsp";
<%
	}else{
%>	
	window.location.href="../query/q021-v.jsp";
<%
	}
%>	
}
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贷款申请查询结果", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %> 