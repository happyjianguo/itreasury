<%
/**
 * ҳ������ ��l023-v.jsp
 * ҳ�湦�� : �����ƻ���ϸ
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * �޸���ʷ ��
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
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}


		String type=(String)request.getAttribute("lLoanType");
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
    	
		tmp=(String)request.getAttribute("lLoanID");				//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanID=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("lClientID");				//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
			clientID=Long.valueOf(tmp).longValue();


		tmp=(String)request.getAttribute("lPlanVersion");				//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
			planVersion=Long.valueOf(tmp).longValue();
					
		tmp=(String)request.getAttribute("sApplyCode");				//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
			applyCode=tmp;	
		
		tmp=(String)request.getAttribute("loanAmount");				//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
			loanAmount=Double.valueOf(tmp).doubleValue();
		
		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			action=tmp;	
			
		
        //��ʾ�ļ�ͷ
        if ( action.equals("modify") )
        	OBHtml.showOBHomeHead(out,sessionMng,"[����ƻ�ά��]",Constant.YesOrNo.NO);
        else
            OBHtml.showOBHomeHead(out,sessionMng,"[����ƻ�ά��]",Constant.YesOrNo.YES);
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<FORM name=form1>
<TABLE border=0 class=top height=127 >
  <TR class="tableHeader">
    <TD class=FormTitle height=2><B>ԭʼ�ƻ���ϸ����</B></TD></TR>
  <TR>
    <TD height=120>
      <TABLE align=left border=0 height=144 width=730>
        <TR borderColor=#ffffff>
          <TD height=23 >&nbsp;</TD>
          <TD colSpan=2 height=23>����ָ����:</TD>
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
          <TD colSpan=2 height=23><FONT color=#ff0000>* </FONT>ԭʼ�ƻ�����:</TD>
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
          <TD colSpan=2 height=17>�ſ�/����:</TD>
          <TD colSpan=4 height=17>
          	<SELECT class=box name=txtlPayRepayType<%=i%> onfocus="nextfield='dAmount<%=i%>'"> 
          		<OPTION selected value=1>�ſ�</OPTION> 
          		<OPTION value=2>����</OPTION>
          	</SELECT> 
          </TD>
          <TD height=17 >&nbsp;</TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=7 >&nbsp;</TD>
          <TD height=7 ><FONT color=#ff0000>* </FONT>���:</TD>
          <TD align=right height=7 >��</TD>
          <TD height=7 >
            <SCRIPT language=javascript>
            	<% if (i==(rsCount-1)) {%>
            		createAmountCtrl("form1","dAmount<%=i%>","","Submit41");
            	<% }else{ %>	
        			createAmountCtrl("form1","dAmount<%=i%>","","txtdtInputDate<%=i+1%>");
        		<% } %>	
          	</SCRIPT>
          </TD>
          <TD colSpan=-1 height=7 >����:</TD>
          <TD height=7 ><INPUT class=box name=txtsType readOnly value=����> </TD>
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
            <INPUT class=button name=Submit41 onclick="confirmFinish(form1,'�Ƿ�ȷ�����?')" onkeydown=key_down(form1) type=button value=" �� �� "> 
			<INPUT class=button name=Submit42 onclick="goBack(form1)" type=button value=" �� �� "> 
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
 //setSubmitFunction("confirmFinish(form1,'�Ƿ�ȷ�����?');");
 setFormName("form1");        

 function key_down(form)
 {
  	if (window.event.keyCode==13)
   	{	
		confirmFinish(form1,'�Ƿ�ȷ�����?');
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
		alert( d_str+"ֻ������ֵ" );
		d_input.focus();
		return (false);
    }
    if  ( ((d_notnull==1) || (d_input.value!=""))  && reverseFormatAmount1(d_input.value) < 0.01 )
		{
			alert(d_str+ "��ֵ����С��0.01��" );
			d_input.focus();
			return (false);
		}
	  if  ( ((d_notnull==1) || (d_input.value!="")) && reverseFormatAmount1(d_input.value) > <%=loanAmount%> )
		{
			alert(d_str+ "��ֵ���ܴ���������: �� <%=loanAmount%>" );
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
				if (!checkDate(eval("form.txtdtInputDate"+i),1,"�ƻ�����"))
					return(false);
			//	if (!checkAmount(form.dAmount,1,"��׼���"))
			//		return(false);
				if (!checkAmountMAX(eval("form.dAmount"+i),1,"¼���� "))
						return(false);
				nNum++;
				}
	}
		if (nNum == 0)
	{
		alert("������һ���ƻ���ϸ��");
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
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>