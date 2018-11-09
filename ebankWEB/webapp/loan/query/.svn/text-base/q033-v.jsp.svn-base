<%
/**
 * 页面名称 ：q033-v.jsp
 * 页面功能 : 贷款合同查询结果
 * 作    者 ：qqgd
 * 日    期 ：
 * 特殊说明 ：
 *			  
 * 转入页面 : l001-c.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.loan.query.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
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
   		String queryLevel=GetParam(request,"queryLevel","");
   		String action=GetParam(request,"txtAction","");
   		
   		
   		ArrayList queryResults = null;
		queryResults = (ArrayList)request.getAttribute("queryResult");
		QuerySumInfo sumInfo = (QuerySumInfo)request.getAttribute("SumInfo");;	
		
		String strSumLoanAmount=DataFormat.formatListAmount(sumInfo.getTotalApplyAmount() );
		long recordCount=sumInfo.getAllRecord();
		
        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"贷款合同查询结果",Constant.YesOrNo.YES);
        
		boolean isdq=false;
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<TABLE border=0 class=top width="98%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=29><B>贷款合同查询</B></TD></TR>
  <TR>
    <TD colSpan=2 >
      <TABLE border=0 borderColor=#999999 class=ItemList width=730>
        <TBODY>
        <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'1');">贷款类型</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'2');">合同编号</A></FONT></TD>
          <% if (!isdq) { %> 
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'3');">借款单位</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'4');">委托单位</A></FONT></TD>
          <%}else{%>
          <TD class=ItemTitle colspan=2 height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'3');">借款单位</A></FONT></TD>
          <%}%>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'5');">贷款金额</A></FONT></TD>
		  <TD class=ItemTitle height=17><FONT size=2>贷款余额</FONT></TD>          
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'6');">合同利率</A></FONT></TD>
		  <TD class=ItemTitle height=17><FONT size=2>执行利率</FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'8');">贷款日期</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'7');">期限</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'9');">合同状态</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'10');">业务负责人</A></FONT></TD>
		  <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'20');">下一级审核人</A></FONT></TD>
		  </TR>
 <%  
    if(queryResults != null && queryResults.size()>0)
	{
		String strType="";
		String applyCode="";
		String borrowClientName="";
		String consignClientName="";
		String strAmount="";
		String strBalance="";
		String strRate="";
		String strBasicRate="";
		String strDate="";
		String strInterval="";
		String strInputDate="";
		String strStatus="";
		String inputUserName="";
		String strNextCheckUser="";
		long lID=-1;
		long lTypeID=-1;
		ContractInfo appInfo=null;
		ContractOperation operation=new ContractOperation();
		RateInfo rateInfo=null;
		ContractAmountInfo amountInfo=null;
		for( int i=0; queryResults != null && i<queryResults.size(); i++ )
		{ 
			appInfo = (ContractInfo )queryResults.get(i);
			lID=appInfo.getContractID();
			rateInfo=operation.getLatelyRate(lID);
			amountInfo=operation.getLateAmount(lID);
			lTypeID=appInfo.getLoanTypeID();
			strType = LOANConstant.LoanType.getName(lTypeID);
			applyCode=appInfo.getContractCode();
			borrowClientName=appInfo.getBorrowClientName();
			consignClientName=appInfo.getClientName();
			strAmount=DataFormat.formatListAmount(appInfo.getExamineAmount());
			strBalance=DataFormat.formatListAmount(amountInfo.getBalanceAmount());
			System.out.println(""+amountInfo.getBalanceAmount());
			strRate=DataFormat.formatRate(rateInfo.getLateRate());
			strBasicRate=DataFormat.formatRate(rateInfo.getRate());
			strDate=DataFormat.getDateString(appInfo.getLoanStart())+"-"+DataFormat.getDateString(appInfo.getLoanEnd());
			if ( appInfo.getIntervalNum()<=0 )
				strInterval="";
			else	
				strInterval=String.valueOf(appInfo.getIntervalNum());
			strStatus=LOANConstant.ContractStatus.getName(appInfo.getStatusID());
			inputUserName=appInfo.getInputUserName();
			strNextCheckUser=appInfo.getCheckUserName();
 %>		         
        <TR align="center"  borderColor=#999999>
          <TD class=ItemBody height=17><FONT size=2> <%=strType%> </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2><a href="javascript:confirmContract(<%=lID%>);""> <%=applyCode%> </a> </FONT></TD>
          <% if (!isdq) { %> 
          <TD align="center"  class=ItemBody ><FONT size=2> <%=(borrowClientName==null)?"":borrowClientName%> </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2> <%=(consignClientName==null)?"":consignClientName%> </FONT></TD>
          <%}else{%>
          <TD colspan=2 align="center"  class=ItemBody ><FONT size=2> <%=(borrowClientName==null)?"":borrowClientName%> </FONT></TD>
          <%}%>
		  <TD align="center"  class=ItemBody><FONT size=2> <%=strAmount%> </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>  <%=strBalance%>  </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>  <%=strBasicRate%>%  </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>  <%=strRate%>%  </FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2>  <%=strDate%>  </FONT></TD>
          <TD align="center" class=ItemBody ><FONT size=2>  <%=strInterval%> </FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2> <%=strStatus%> </FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2> <%=inputUserName%> </FONT></TD>
		  <TD height=17 align="center" class=ItemBody ><FONT size=2> <%=strNextCheckUser%></FONT></TD>
		  </TR>
 <%
		}
	}
	else
	{
%>
        <TR align="center"  borderColor=#999999>
          <TD class=ItemBody height=17><FONT size=2>&nbsp;  </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>&nbsp;</FONT></TD>
          <% if (!isdq) { %>
          <TD align="center"  class=ItemBody ><FONT size=2>&nbsp;</FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>&nbsp; </FONT></TD>
          <%}else{%>
          <TD colspan=2 align="center"  class=ItemBody ><FONT size=2>&nbsp;</FONT></TD>
          <%}%>
		  <TD align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>&nbsp;   </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>
          <TD align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>          
		  <TD align="center" class=ItemBody ><FONT size=2>&nbsp;  </FONT></TD>
          <TD align="center" class=ItemBody ><FONT size=2>&nbsp;   </FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2>&nbsp;</FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2>&nbsp; </FONT></TD>
		  <TD align="center" class=ItemBody ><FONT size=2>&nbsp; </FONT></TD>
		  </TR>
<%
	}
%>       
         <tr>
				<td colspan=13>
<%
	//输出分页控件
	OBHtml.showTurnPageControl(out,"turnPageFrm","q032-c.jsp?queryLevel="+queryLevel,recordCount,(termInfo==null?1:termInfo.getTxtPageNo()),Constant.PageControl.CODE_PAGELINECOUNT,(termInfo==null?99:termInfo.getLOrderParam()),(termInfo==null?1:termInfo.getLDesc()));
%>
				</TD>
			  </TR>
		  	
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=13 height=2>
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar
            height=50 width="100%">
              <TBODY>
              <TR>
              <td> <FONT size=2><B>贷款金额:
				  <INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=25 value="<%=strSumLoanAmount%>" disabled>
                  </B></FONT>
                </td>  
                <TD height=2 width=354>&nbsp;</TD>
			  </TR>
              <TR>
                <TD height=14 width=662>    </TD>
                <TD height=14 width=354>	</TD>
              </TR>
              </TBODY>
              </TABLE>
           </TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height=36><FONT size=2><BR>查询数据时间为:<%=DataFormat.getDateString(tsToday)%></FONT>
      <DIV align=right></DIV></TD>
    <TD height=36>
      <DIV align=right><B>
	  	  <INPUT class=button name=Submit22 onclick="window.print()" type=button value=" 打 印 ">
      </B><INPUT class=button name=Submit2 onclick="backto()" type="button" value=" 返 回 " >
      </DIV></TD></TR></TBODY></TABLE>
 
<script language="JavaScript"> 

function confirmContract(lID)
{
	window.open('../query/q034-c.jsp?contractID='+lID, '查看合同资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function backto()
{
<%
	if ( queryLevel.equals("low") ){
%>	
	window.location.href="../query/q030-v.jsp";
<%
	}else{
%>	
	window.location.href="../query/q031-v.jsp";
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
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"委托客户选择", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 <%@ include file="/common/SignValidate.inc" %>

