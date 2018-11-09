<%
/**
 * 页面名称 ：q052-v.jsp
 * 页面功能 : 贴现查询-查询结果
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
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
        OBHtml.showOBHomeHead(out,sessionMng,"贴现合同查询结果",Constant.YesOrNo.YES);
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<TABLE border=0 class=top width="98%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=29><B>贴现合同查询</B></TD></TR>
  <TR>
    <TD colSpan=2 >
      <TABLE border=0 borderColor=#999999 class=ItemList width=730>
        <TBODY>
        <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
          <TD class=ItemTitle width=10.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'2');">贴现合同编号</A></FONT></TD>
          <TD class=ItemTitle width=10.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'3');">申请单位</A></FONT></TD>
          <TD class=ItemTitle width=10.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'11');">贴现申请书编号</A></FONT></TD>
          <TD class=ItemTitle width=10.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'12');">贴现批准金额</A></FONT></TD>
		  <TD class=ItemTitle width=10.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'13');">贴现实付金额</a></FONT></TD>          
          <TD class=ItemTitle width=7% height=17><FONT size=2>贴现利息</FONT></TD>
		  <TD class=ItemTitle width=7% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'14');">执行利率</A></FONT></TD>
          <TD class=ItemTitle width=5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'8');">开始日期</A></FONT></TD>
          <TD class=ItemTitle width=5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'15');">结束日期</A></FONT></TD>
          <TD class=ItemTitle width=5.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'9');">状态</A></FONT></TD>
          <TD class=ItemTitle width=5.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'10');">业务负责人</A></FONT></TD>
		  <TD class=ItemTitle width=5.5% height=17><FONT size=2><A href="javascript:go(document.turnPageFrm,'20');">下一级审核人</A></FONT></TD>
		  </TR>
 <%  
    if(queryResults != null && queryResults.size()>0)
	{
		String contractCode="";
		String applyCode="";
		String borrowClientName="";
		String strExamineAmount="";
		String strCheckAmount="";
		String strDiscountRate="";
		String strFee="";
		String strStartDate="";
		String strEndDate="";
		String strStatus="";
		String inputUserName="";
		String strNextCheckUser="";
		long lID=-1;
		ContractInfo appInfo=null;
		ContractOperation operation=new ContractOperation();
		for( int i=0; queryResults != null && i<queryResults.size(); i++ )
		{ 
			appInfo = (ContractInfo )queryResults.get(i);
			lID=appInfo.getContractID();
	
			contractCode=appInfo.getContractCode();
			applyCode=appInfo.getApplyCode();
			borrowClientName=appInfo.getBorrowClientName();
			strDiscountRate=DataFormat.formatRate(appInfo.getDiscountRate());
			strCheckAmount=DataFormat.formatListAmount(appInfo.getCheckAmount());
			strExamineAmount=DataFormat.formatListAmount(appInfo.getExamineAmount());
			strFee=DataFormat.formatListAmount(appInfo.getExamineAmount()-appInfo.getCheckAmount());

			
			strStartDate=DataFormat.getDateString(appInfo.getLoanStart());
			strEndDate=DataFormat.getDateString(appInfo.getLoanEnd());
			strStatus=LOANConstant.ContractStatus.getName(appInfo.getStatusID());
			inputUserName=appInfo.getInputUserName();
			strNextCheckUser=appInfo.getCheckUserName();
 %>		         
        <TR align="center"  borderColor=#999999>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2><a href="javascript:confirmContract(<%=lID%>);"> <%=contractCode%> </a> </FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody ><FONT size=2> <%=(borrowClientName==null)?"":borrowClientName%> </FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2> <%=(applyCode==null)?"":applyCode%> </FONT></TD>
		  <TD width=10.5% align="center"  class=ItemBody><FONT size=2> <%=strExamineAmount%> </FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2>  <%=strCheckAmount%>  </FONT></TD>
          <TD width=7% align="center"  class=ItemBody><FONT size=2>  <%=strFee%></FONT></TD>
          <TD width=7% align="center"  class=ItemBody><FONT size=2>  <%=strDiscountRate%>%  </FONT></TD>
		  <TD width=5% align="center" class=ItemBody ><FONT size=2>  <%=strStartDate%>  </FONT></TD>
          <TD width=5% align="center" class=ItemBody ><FONT size=2>  <%=strEndDate%> </FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2> <%=strStatus%> </FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2> <%=inputUserName%> </FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2> <%=strNextCheckUser%> </FONT></TD>
		  </TR>
 <%
		}
	}
	else
	{
%>
        <TR align="center"  borderColor=#999999>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2>&nbsp;</FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody ><FONT size=2>&nbsp;</FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2>&nbsp; </FONT></TD>
		  <TD width=10.5% align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>
          <TD width=10.5% align="center"  class=ItemBody><FONT size=2>&nbsp;   </FONT></TD>
          <TD width=7% align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>
          <TD width=7% align="center"  class=ItemBody><FONT size=2>&nbsp;  </FONT></TD>          
		  <TD width=5% align="center" class=ItemBody ><FONT size=2>&nbsp;  </FONT></TD>
          <TD width=5% align="center" class=ItemBody ><FONT size=2>&nbsp;   </FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2>&nbsp;</FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2>&nbsp; </FONT></TD>
		  <TD width=5.5% align="center" class=ItemBody ><FONT size=2>&nbsp; </FONT></TD>
		  </TR>
<%
	}
%>       
        <tr  align="center" > 
		  <td width="99%" valign="bottom" colspan=12>
         	<TABLE border="0" cellPadding=1 height=20 width="100%" class="ItemList">
         	<TBODY>
             <TR>
                <TD height=20 width=99% class="ItemTitle">
<%
	//输出分页控件
	OBHtml.showTurnPageControl(out,"turnPageFrm","q032-c.jsp?purpose=TX&queryLevel="+queryLevel,recordCount,(termInfo==null?1:termInfo.getTxtPageNo()),Constant.PageControl.CODE_PAGELINECOUNT,(termInfo==null?99:termInfo.getLOrderParam()),(termInfo==null?1:termInfo.getLDesc()));
%>

				</TD>
			  </TR>
		  	</TBODY>
		  	</TABLE>
	 	</TD>
		</TR>
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=12 height=2>
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
	window.open('<%=Env.EBANK_URL%>loan/query/q036-c.jsp?contractID='+lID+'&action=3&type=<%=LOANConstant.LoanType.TX%>', '查看合同资料', 'toolbar=yes,menubar=yes,resizable=yes,location=yes,width=800,height=600,scrollbars=yes', false)
}
function backto()
{
	window.location.href="<%=Env.EBANK_URL%>loan/query/q050-v.jsp";
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

