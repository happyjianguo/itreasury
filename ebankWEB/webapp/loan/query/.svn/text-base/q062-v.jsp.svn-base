<%
/**
 * 页面名称 ：q062-v.jsp
 * 页面功能 : 合同执行情况
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
	com.iss.itreasury.loan.query.dataentity.*,
	com.iss.itreasury.loan.query.bizlogic.*,	
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
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

        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"贷款实际情况查询",Constant.YesOrNo.YES);
        
        Vector v=(Vector)request.getAttribute("queryResult");
        QuerySumInfo sumInfo=(QuerySumInfo)request.getAttribute("SumInfo");
        
        String tmp="";
		String contractCode="";
		String search="";
		long lOrderParam=99;
		long lDesc=1;
		long contractID=-1;
		
		String actType=GetParam(request,"actType","");
		tmp=(String)request.getAttribute("lContractID");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			contractID=Long.valueOf(tmp).longValue();
		}
		
		tmp=(String)request.getAttribute("lOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			lOrderParam=Long.valueOf(tmp).longValue();
		}

		tmp=(String)request.getAttribute("lDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			lDesc=Long.valueOf(tmp).longValue();
		}
				
		tmp=(String)request.getAttribute("ContractCode");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			contractCode=tmp;
		}
		
		tmp=(String)request.getAttribute("search");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			search=tmp;
		}
		Timestamp tsToday = Env.getSystemDate();	
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="98%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=29><B>贷款实际执行情况查询</B></TD></TR>
    <tr><td colspan=2> 合同号：<%=contractCode%></td></tr>
	<TR>
    <TD colSpan=2>
      <TABLE border=0 borderColor=#999999 class=ItemList width=730>
        <TBODY>
        <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go('3');">放款/还款日期</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go('5');">放款/还款</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go('1');">金额</A></FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go('2');">已付利息</A></FONT></TD>	
          <TD class=ItemTitle height=17><FONT size=2>执行利率</FONT></TD>
          <TD class=ItemTitle height=17><FONT size=2><A href="javascript:go('4');">修改日期</A></FONT></TD>		  		  	  
<% 
	if ( (v!=null)&&(v.size()>0) )
	{
		int count=v.size();
		String strPayDate="";
		String strPayType="";
		String strAmount="";
		String strInterest="";
		String strInterestRate="";
		String strModifyDate="";
		
		for (int i=0;i<count;i++)
		{
			QueryPerformInfo info=(QueryPerformInfo)v.get(i);
			strPayDate=DataFormat.getDateString(info.getPerformDate());
			if ( info.getPayType()==1 )
				strPayType="放款";
			else
				strPayType="还款";
			strAmount=DataFormat.formatListAmount(info.getPerformAmount());
			strInterest=DataFormat.formatListAmount(info.getInterest());
			strInterestRate=DataFormat.formatRate(info.getPerformRate());
			strModifyDate=DataFormat.getDateString(info.getModifyDate());		
%>	
        <TR align=center borderColor=#999999>
          <TD height=17 class=ItemBody><%=strPayDate%></TD>
          <TD height=17 class=ItemBody><%=strPayType%></TD>
          <TD height=17 class=ItemBody><%=strAmount%></TD>
          <TD height=17 class=ItemBody><%=strInterest%></TD>
          <TD height=17 class=ItemBody><%=strInterestRate%></TD>
          <TD height=17 class=ItemBody><%=strModifyDate%></TD>		  
        </TR>
<%
		}
	}else{
%>	
        <TR align=center borderColor=#999999>
          <TD height=17 class=ItemBody>&nbsp;</TD>
          <TD height=17 class=ItemBody>&nbsp;</TD>
          <TD height=17 class=ItemBody>&nbsp;</TD>
          <TD height=17 class=ItemBody>&nbsp;</TD>
          <TD height=17 class=ItemBody>&nbsp;</TD>
          <TD height=17 class=ItemBody>&nbsp;</TD>		  
        </TR>
<%
	}
%>				   
</TBODY></TABLE></TD></TR>
  <TR>
    <TD><FONT size=2>放款合计: </FONT> 
    	<INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalPayAmount())%>" disabled> 
    </TD>
    <TD></TD>
  </TR>
  <TR>
    <TD><FONT size=2>还款合计: </FONT> 
		<INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalRepayAmount())%>" disabled>     
    </TD>
    <TD></TD>
  </TR>
  <TR>
    <TD><FONT size=2>利息合计: </FONT>
 <INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalInterestAmount())%>" disabled>    
     </TD>
    <TD></TD>
  </TR>
  <TR>
    <TD><FONT size=2>手续费合计: </FONT> 
 <INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalChargeAmount())%>" disabled>        
    </TD>
    <TD></TD>
  </TR>
  <TR>
    <TD><FONT size=2>担保费合计: </FONT> 
<INPUT class=SearchBar_Page maxLength=25 name=textfield32222 size=18 value="<%=DataFormat.formatListAmount(sumInfo.getTotalCreditAmount())%>" disabled>        
    </TD>
    <TD></TD>
  </TR>
  <TR>
    <TD height=36><FONT size=2><BR>查询数据时间为: <%=DataFormat.getDateString(tsToday)%></FONT> 
      <DIV align=right></DIV></TD>
    <TD height=36>
      <DIV align=right><B>
	  	  	  <INPUT class=button name=Submit22 onclick="window.print()" type=button value=" 打 印 "> 
<% if (actType.equals("query")){ %>
	  	  	  	  <INPUT class=button name=Submit2 onclick="backto()" type="button" value=" 返 回 " > 
<% }else{ %> 
					<input type="button" name="butReturn" value=" 关 闭 " class="button" onClick="closePage()">
<% } %>					


      </B>
      </DIV></TD></TR></TBODY></TABLE>
<input type=hidden name="lOrderParam" value="<%=lOrderParam%>">
<input type=hidden name="lDesc" value="<%=lDesc%>">	 
<input type="hidden" name="control" > 
<input type="hidden" name="lContractID" value=<%=contractID%>> 
<input type="hidden" name="contractCode" value=<%=contractCode%>> 
<input type="hidden" name="search" value=<%=search%>> 
<input type="hidden" name="actType" value=<%=actType%>> 
</form>	  
<script language="JavaScript">
function backto()
{
window.location.href="q060-v.jsp?control=view"
}
function go(para)
{
  frm.control.value="view";
  if (frm.lOrderParam.value==para)
     {
	   if (frm.lDesc.value=="1")
	       frm.lDesc.value="2";
	   else
	   	  frm.lDesc.value="1"; 
	 }
  frm.lOrderParam.value=para;
  confirmSave(frm); 
}
function  confirmSave(frm)
{
	frm.action="q061-c.jsp";
    frm.control.value="view";
    frm.submit();		
}
function closePage()
{
	window.close();
}
</script> 
<%
		OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贷款实际情况查询", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %> 

