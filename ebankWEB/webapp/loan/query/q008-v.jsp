<%
/**
 * 页面名称 ：q008-v.jsp
 * 页面功能 : 贷款基本性质资料
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
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
		
		String type=(String)request.getAttribute("lLoanType");
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=LOANConstant.LoanType.getName(loanType);
	
    	boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
    
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue();
    
    	LoanApplyInfo lInfo= (LoanApplyInfo) request.getAttribute("LoanApplyInfo");
    	
		long wtClientID=lInfo.getConsignClientID();			//委托客户
		String loanCode=lInfo.getApplyCode();				//申请号
		long loanID=lInfo.getID();							//申请ID
		long clientID=lInfo.getBorrowClientID();			//客户号
	
		double loanAmount=lInfo.getLoanAmount();			//申请金额
		String loanReason=lInfo.getLoanReason();			//借款原因
		String loanPurpose=lInfo.getLoanPurpose();			//借款目的
		String other=lInfo.getOther();					//其它
		double chargeRate=lInfo.getChargeRate();			//手续费率
		long   intervalNum=lInfo.getIntervalNum();			//期限
		Timestamp startDate=lInfo.getStartDate();			//开始日期
		Timestamp endDate=lInfo.getEndDate();				//结束日期
		double selfAmount=lInfo.getSelfAmount();			//财务公司成带劲金额
		//double selfScale = lInfo.getSelfScale();			//财务公司承贷比例
		double selfScale=0;//UNDO
		double interestRate=lInfo.getInterestRate();		//利息率
		String clientInfo=lInfo.getClientInfo();
	
		/*对空值进行处理*/
		if ( clientInfo==null )
			clientInfo="";
		if ( loanReason==null )
			loanReason="";
		if ( loanPurpose==null )
			loanPurpose="";
		if ( other==null )
			other="";
		if ( intervalNum<1)
		{
        	
            intervalNum=12;
            
		}	
 
 	/*对日期形数据和金额数据进行特殊处理*/
 	String sstartDate="";
 	String sendDate="";
 	String sloanAmount="";
 	String sselfAmount="";
	String sScale = "";
 	
 	if ( startDate!=null )
 		sstartDate=DataFormat.getDateString(startDate);
 	if ( endDate!=null )
 		sendDate=DataFormat.getDateString(endDate);
 	sloanAmount=DataFormat.formatNumber(loanAmount,2);
 	sselfAmount=DataFormat.formatNumber(selfAmount,2);
	sScale =DataFormat.formatNumber(selfScale,6);
	
    if ( loanType==OBConstant.LoanInstrType.LOAN_WT)
    {
    	iswt=true;
	}
	/*else if( loanType==OBConstant.LoanInstrType.LOAN_YT )
	{
		isyt=true;
	}*/
        //显示文件头
       	OBHtml.showOBHomeHead(out,sessionMng,"[贷款基本资料]",Constant.YesOrNo.NO);
        
       	isCheck=true;		
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="frm" method="post">
<TABLE border=0 class=top height=254 width=730>
<TR class="tableHeader">
    <TD class=FormTitle height=29><B><%=loanTypeName%>－查看</B></TD>
</TR>
<TR>
    <TD>
    <% if (iswt){ %>
      <TABLE align=center border=0 width=730>
      <TR>
          <TD height=28 >&nbsp;</TD>
          <TD colSpan=3 height=28>申请指令编号:<%=loanCode%></TD>
          <TD colSpan=2 height=28></TD>
          <TD colSpan=5 height=28 ></TD>
      </TR>
        <TR>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=5 height=29>
            <HR>
          </TD>
          <TD align=right colSpan=5 height=29 >&nbsp;</TD>
        </TR>
        <TR>
          <TD height=29 >&nbsp;</TD>
          <TD colSpan=5 height=29><U>借款申请详情</U></TD>
          <TD align=right colSpan=5 height=29 >&nbsp;</TD></TR>
        <TR>
          <TD height=26 >&nbsp;</TD>
          <TD height=26 ><font color='#FF0000'>* </font>借款金额:</TD>
          <TD height=26 align=right>￥</TD>
          <TD height=26 >
          <% if ( isCheck ) { %>
          <input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
          <input name="daxie" size="25" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(loanAmount),1)%>" class=box disabled>
          <% }else{ %>
  		  	<script language="javascript">
        		createAmountCtrl("frm","loanamount","<%=sloanAmount%>","bankinterest","daxie");
          	</script>	
          	（大写）<input type="text" name="daxie" size="25" value="" class=box disabled>
          <% } %>			
		  </TD>
          <TD height=26 ><font color='#FF0000'>* </font>借款利率:</TD>
          <TD colSpan=6 height=26> 
          	<% if ( interestRate==0 ) {%>
          	  <input class=box name="bankinterest" size=8 value="" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
          	<% }else{ %>
              <input class=box name="bankinterest" size=8 value="<%=DataFormat.formatRate(interestRate)%>" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
            <% } %>  
              ％ 手续费 
             <% if ( chargeRate==0 ){ %>
             <input class=box name="chargerate" size=8  value="" onfocus="nextfield='startdate'" <%if (isCheck) {%>disabled<%}%> >
              ％
             <%  }else{ %>
             <input class=box name="chargerate" size=8  value="<%=DataFormat.formatRate(chargeRate)%>" onfocus="nextfield='startdate'" <%if (isCheck) {%>disabled<%}%> >
              ％）
             <% } %>
              
          </TD>
		</TR>
		  
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P><font color='#FF0000'>* </font>借款日期:<BR><BR><font color='#FF0000'>* </font>期限:</P>
          </TD>
          <TD colSpan=3 height=33>
          <fs_c:calendar 
			         	    name="startdate"
				          	value="" 
				          	properties="nextfield ='enddate'" 
				          	size="20"/>
						          	  <script>
	          		$('#startdate').val('<%=sstartDate%>');
	          	</script>
				<script type="text/javascript">
					$('#startdate').blur(function(){
						writed();
					});
				</script>
				<%if (isCheck) {%>
				<script>
	          		$('#startdate').attr('disabled','true');
	          	</script>
	          	<%} %>
          <!--  
              <input class=box onBlur="writed()" name="startdate" value="<%=sstartDate%>" <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='enddate'">
              <A href="javascript:show_calendar('frm.startdate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;"> 
              <IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> -->
              到 
              <fs_c:calendar 
			         	    name="enddate"
				          	value="" 
				          	properties="nextfield ='intervalnum'" 
				          	size="20"/>
				          				          	  <script>
	          		$('#enddate').val('<%=sendDate%>');
	          	</script>
				          	<%if (isCheck) {%>
								<script>
					          		$('#enddate').attr('disabled','true');
					          	</script>				          	
				          	<%}%>
               <!--  
              <INPUT class=box name="enddate" value="<%//=sendDate%>" <%//if (isCheck) {%>disabled<%}%> onfocus="nextfield='intervalnum'">
              <A href="javascript:show_calendar('frm.enddate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;"> 
              <IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> -->
              <br>
              <INPUT class=box onblur="writed()" name="intervalnum" size=3 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> >
              月
          </TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33><font color='#FF0000'>* </font>借款用途:</TD>
          <TD height=33 ><TEXTAREA  class=box cols=30 name="loanpurpose"  onfocus="nextfield='wjh'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P>项目批准机关及文号:</P></TD>
          <TD height=33 ><TEXTAREA  class=box cols=30 name="wjh" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=17 >&nbsp;</TD>
          <TD colSpan=5 height=17>
            <HR>
          </TD>
          <TD align=right colSpan=5 height=17 >&nbsp;</TD>
        </TR>
        <TR>
          <TD height=41 >&nbsp;</TD>
          <TD colSpan=3 height=41></TD>
          <TD height=41 ></TD>
          <TD align=right colSpan=6 height=41>
				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
          </TD>
        </TR>
    </TABLE>
    <% }else{ %>
    <TABLE cellPadding=0 height=17 width=100%>
          <TR >
            <TD height=36 >&nbsp; </TD>
            <TD height=36 > 申请指令编号:<%=loanCode%></TD>
            <TD colSpan=2 height=36></TD>
            <TD height=36 > </TD>
            <TD height=36 ></TD>
            <TD height=36 ></TD>
          </TR>
       </TABLE>
       <TABLE cellPadding=0 height=50 >
          <TR > 
            <TD colSpan=7 height=66 vAlign=bottom> 
                <HR align=center SIZE=2 width="100%">
                  <P><U>借款申请详情</U></P>
            </TD>
          </TR>
          <TR> 
            <TD ><font color='#FF0000'>* </font>
	<%
			if ( isyt )
			{
	%>
				贷款额度:
	<%
			}
			else
			{
	%>
				借款金额:
	<%
			}
	%>
            </TD>
            <TD >￥</TD>
            <TD > 
<%
			if (isyt)
			{
%>
<%
				if ( isCheck )
				{
%>
					<input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
<%
				}
				else
				{
%>
					<script language="javascript">
						createAmountCtrl("frm","loanamount","<%=sloanAmount%>","intervalnum","");
					</script>
<%
				}
			}
			else
			{
				if (isCheck)
				{
%>
					<input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
<%
				}
				else
				{
%>
					<script language="javascript">
						createAmountCtrl("frm","loanamount","<%=sloanAmount%>","intervalnum","");
					</script>
<%
				}
			}
%>
				</TD>
				<TD ><font color='#FF0000'>*</font> 借款期限:</TD>
				<TD >
<%
			if (isyt)
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='sCale'" <%if (isCheck) {%>disabled<%}%> >月	
<%
			}
			else
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanreason'" <%if (isCheck) {%>disabled<%}%> >月	
<%
			}
%>
					</TD>
				<TD ></TD>
				<TD ></TD>
          </TR>
<%
			if (isyt)
			{
%>
				<tr>
					<TD ><font color='#FF0000'>&nbsp;</font> 财务公司承贷额度:</TD>
					<TD >￥</TD>
					<TD >
						<input name="saleamount" size="18" value="<%=sselfAmount%>" class=tar readonly>
					</TD>
					<TD ><font color='#FF0000'>*</font> 承贷比例:</TD>
					<TD >
	<%
				String strReadonly = "";
				if (isCheck)
				{
					strReadonly = "readonly";
				}
	%>
						<INPUT class=box name="sCale" size=6 maxlength="8" value="<%=sScale%>" onblur="changeScale(frm)" onfocus="nextfield='loanreason'" <%=strReadonly%>> % 
					</TD>
				</tr>
<%
			}
%>
          <TR> 
            <TD > <font color='#FF0000'>* </font>借款原因:</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA  class=box cols=65 name="loanreason"  onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanReason%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD ><font color='#FF0000'>* </font>借款用途:</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA  class=box cols=65 name="loanpurpose"  onfocus="nextfield='hkly'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD height=50 > <font color='#FF0000'>* </font>还款来源及措施:</TD>
            <TD height=50 >&nbsp;            </TD>
            <TD colSpan=5 height=50 > <TEXTAREA  class=box cols=65 name="hkly"  onfocus="nextfield='clientinfo'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA></TD>
          </TR>
          <TR > 
            <td height=50 colspan=2> <font color='#FF0000'>* </font>借款单位情况简介:</td>
            
            <td colspan=5 height=50 > <textarea  class=box cols=65 name="clientinfo" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=clientInfo%></textarea></td>
          </TR>
          <TR > 
            <TD colSpan=7 height=40 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
          <TR > 
            <TD colSpan=7 align="right"> 
 				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" 关 闭 "> 
           </TD>
          </TR>
        </TABLE>
	<%}%>
    
    </TD>
</TR>
</TABLE>
<input type="hidden" name="sApplyCode" value="<%=loanCode%>">
<input type=hidden name="lLoanType" value="<%=type%>">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lClientID" value="<%=clientID%>">
<input type="hidden" name="wtClientID" value="<%=wtClientID%>">
<input type="hidden" name="txtAction" value="<%=action%>">
</form>


<script language="javascript">
function confirmClose()
{
	window.close();
}

firstFocus(frm.xybClose);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 	 
</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>