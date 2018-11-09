<%
/**
 * 页面名称 ：l023-v.jsp
 * 页面功能 : 新增计划明细
 * 作    者 ：gump
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 修改历史 ：
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
	com.iss.itreasury.ebank.obloanapply.bizlogic.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	boolean iswt=false;
    boolean isyt=false;
    String action="";
    String tmp="";
    
    long loanID=-1;
    long clientID=-1;
    String applyCode="";
    double loanAmount=0;
    
    String strDate=DataFormat.getDateString(Env.getSystemDate(1,1));
    long planVersion=-1;
    long rsCount=10;

        
   try
   {
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
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
    	
		tmp=(String)request.getAttribute("lLoanID");				//申请号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lClientID");				//申请号
		if ( (tmp!=null)&&(tmp.length()>0) )
			clientID=Long.valueOf(tmp).longValue();


		tmp=(String)request.getAttribute("lPlanVersion");				//申请号
		if ( (tmp!=null)&&(tmp.length()>0) )
			planVersion=Long.valueOf(tmp).longValue();
					
		tmp=(String)request.getAttribute("sApplyCode");				//申请号
		if ( (tmp!=null)&&(tmp.length()>0) )
			applyCode=tmp;	
		
		tmp=(String)request.getAttribute("loanAmount");				//申请号
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanAmount=Double.valueOf(tmp).doubleValue();
		
		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			action=tmp;	
			
		
        //显示文件头
        if ( action.equals("modify") )
        	OBHtml.showOBHomeHead(out,sessionMng,"[贷款计划维护]",Constant.YesOrNo.NO);
        else
            OBHtml.showOBHomeHead(out,sessionMng,"[贷款计划维护]",Constant.YesOrNo.YES);
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<FORM name=form1>
<TABLE border=0 class=top height=127 >
  <TR class="tableHeader">
    <TD class=FormTitle height=2><B>原始计划明细新增</B></TD></TR>
  <TR>
    <TD height=120>
      <TABLE align=left border=0 height=144 width=730>
        <TR borderColor=#ffffff>
          <TD height=23 >&nbsp;</TD>
          <TD colSpan=2 height=23>申请指令编号:</TD>
          <TD colSpan=4 height=23><%=applyCode%></TD>
          <TD height=23 >&nbsp;</TD>
        </TR>
        <%
        	for ( int i=0;i<rsCount;i++ )
        	{
        %>
        <TR borderColor=#ffffff>
          <TD colSpan=8 height=23>
            <HR>
          </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=23 >&nbsp;</TD>
          <TD colSpan=2 height=23><FONT color=#ff0000>* </FONT>原始计划日期:</TD>
          <TD colSpan=4 height=23>
           <fs_c:calendar 
         	    name="txtdtInputDate<%=i%>"
	          	value="<%=strDate%>" 
	          	properties="nextfield ='txtlPayRepayType<%=i%>'" 
	          	size="20"/>
          </TD>
          <TD height=23 >&nbsp;</TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=17 >&nbsp;</TD>
          <TD colSpan=2 height=17>放款/还款:</TD>
          <TD colSpan=4 height=17>
          	<SELECT class=box name=txtlPayRepayType<%=i%> onfocus="nextfield='dAmount<%=i%>'"> 
          		<OPTION selected value=1>放款</OPTION> 
          		<OPTION value=2>还款</OPTION>
          	</SELECT> 
          </TD>
          <TD height=17 >&nbsp;</TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=7 >&nbsp;</TD>
          <TD height=7 ><FONT color=#ff0000>* </FONT>金额:</TD>
          <TD align=right height=7 >￥</TD>
          <TD height=7 >
            <SCRIPT language=javascript>
            	<% if (i==(rsCount-1)) {%>
            		createAmountCtrl("form1","dAmount<%=i%>","","Submit41");
            	<% }else{ %>	
        			createAmountCtrl("form1","dAmount<%=i%>","","txtdtInputDate<%=i+1%>");
        		<% } %>	
          	</SCRIPT>
          </TD>
          <TD colSpan=-1 height=7 >类型:</TD>
          <TD height=7 ><INPUT class=box name=txtsType readOnly value=本金> </TD>
          <TD height=7 >&nbsp;</TD>
        </TR>
        <%
        	}
        %>
        <TR borderColor=#ffffff>
          <TD height=31 >&nbsp;</TD>
          <TD colSpan=2 height=31>&nbsp;</TD>
          <TD height=31> </TD>
          <TD height=31>&nbsp;</TD>
          <TD height=31>
            <DIV align=right>
            <INPUT class=button name=Submit41 onclick="confirmFinish(form1,'是否确认完成?')" onkeydown=key_down(form1) type=button value=" 完 成 "> 
			<INPUT class=button name=Submit42 onclick="goBack(form1)" type=button value=" 返 回 "> 
            </DIV>
          </TD>
          <TD height=31>&nbsp;</TD>
          <TD height=31 >&nbsp;</TD>
        </TR>
        
      </TABLE>
      </TD>
      </TR>
   </TABLE>

<INPUT name=nVersionID type=hidden value=10488> 
<INPUT name=sApplyCode type=hidden value=<%=applyCode%> > 
<INPUT name=isSM type=hidden value=1> 
<INPUT name=sURL type=hidden value=S8.jsp> 
<INPUT name=sFile type=hidden value=S20> 
<INPUT name=lLoanID type=hidden value=<%=loanID%> > 
<INPUT name=lLoanType type=hidden value=<%=loanType%> > 
<INPUT name=lRsCount type=hidden value=<%=rsCount%> > 
<INPUT name=lPlanVersion type=hidden value=<%=planVersion%> > 
<input type="hidden" name="txtAction" value="<%=action%>">       


</FORM>

<SCRIPT language=javascript>
 firstFocus(form1.Submit41);
 //setSubmitFunction("confirmFinish(form1,'是否确认完成?');");
 setFormName("form1");        

 function key_down(form)
 {
  	if (window.event.keyCode==13)
   	{	
		confirmFinish(form1,'是否确认完成?');
   	}
}

function goBack(form)
{
	form.action="l020-c.jsp";
	showSending();
	form.submit();
}


</SCRIPT>
<SCRIPT language=JavaScript>
<!--
function checkAmountMAX(d_input,d_notnull,d_str)
{
		if ( !isFloat(reverseFormatAmount1(d_input.value)))
    {
		alert( d_str+"只能是数值" );
		d_input.focus();
		return (false);
    }
    if  ( ((d_notnull==1) || (d_input.value!=""))  && reverseFormatAmount1(d_input.value) < 0.01 )
		{
			alert(d_str+ "的值不能小于0.01。" );
			d_input.focus();
			return (false);
		}
	  if  ( ((d_notnull==1) || (d_input.value!="")) && reverseFormatAmount1(d_input.value) > <%=loanAmount%> )
		{
			alert(d_str+ "的值不能大于申请金额: ￥ <%=loanAmount%>" );
			d_input.focus();
			return (false);
		}
		return true;
}
//-->
</SCRIPT>

<SCRIPT language=JavaScript>
<!--
function confirmFinish(form,msg)
{
     var nNum = 0;
	for (var i = 0 ; i < <%=rsCount%>; i++)
	{
       if (eval("form1.dAmount"+i+".value.length") > 0) 
	{
				if (!checkDate(eval("form.txtdtInputDate"+i),1,"计划日期"))
					return(false);
			//	if (!checkAmount(form.dAmount,1,"批准金额"))
			//		return(false);
				if (!checkAmountMAX(eval("form.dAmount"+i),1,"录入金额 "))
						return(false);
				nNum++;
				}
	}
		if (nNum == 0)
	{
		alert("请新增一条计划明细！");
		return false;
	}
	if (confirm(msg)) {
		form.action="l024-c.jsp";
			
		showSending();
		form.submit();
	}			
}


//-->
</SCRIPT>

<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( Exception ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>