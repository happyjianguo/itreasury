<%
/**
 * ҳ������ ��q008-v.jsp
 * ҳ�湦�� : ���������������
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
    	
		long wtClientID=lInfo.getConsignClientID();			//ί�пͻ�
		String loanCode=lInfo.getApplyCode();				//�����
		long loanID=lInfo.getID();							//����ID
		long clientID=lInfo.getBorrowClientID();			//�ͻ���
	
		double loanAmount=lInfo.getLoanAmount();			//������
		String loanReason=lInfo.getLoanReason();			//���ԭ��
		String loanPurpose=lInfo.getLoanPurpose();			//���Ŀ��
		String other=lInfo.getOther();					//����
		double chargeRate=lInfo.getChargeRate();			//��������
		long   intervalNum=lInfo.getIntervalNum();			//����
		Timestamp startDate=lInfo.getStartDate();			//��ʼ����
		Timestamp endDate=lInfo.getEndDate();				//��������
		double selfAmount=lInfo.getSelfAmount();			//����˾�ɴ������
		//double selfScale = lInfo.getSelfScale();			//����˾�д�����
		double selfScale=0;//UNDO
		double interestRate=lInfo.getInterestRate();		//��Ϣ��
		String clientInfo=lInfo.getClientInfo();
	
		/*�Կ�ֵ���д���*/
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
 
 	/*�����������ݺͽ�����ݽ������⴦��*/
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
        //��ʾ�ļ�ͷ
       	OBHtml.showOBHomeHead(out,sessionMng,"[�����������]",Constant.YesOrNo.NO);
        
       	isCheck=true;		
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="frm" method="post">
<TABLE border=0 class=top height=254 width=730>
<TR class="tableHeader">
    <TD class=FormTitle height=29><B><%=loanTypeName%>���鿴</B></TD>
</TR>
<TR>
    <TD>
    <% if (iswt){ %>
      <TABLE align=center border=0 width=730>
      <TR>
          <TD height=28 >&nbsp;</TD>
          <TD colSpan=3 height=28>����ָ����:<%=loanCode%></TD>
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
          <TD colSpan=5 height=29><U>�����������</U></TD>
          <TD align=right colSpan=5 height=29 >&nbsp;</TD></TR>
        <TR>
          <TD height=26 >&nbsp;</TD>
          <TD height=26 ><font color='#FF0000'>* </font>�����:</TD>
          <TD height=26 align=right>��</TD>
          <TD height=26 >
          <% if ( isCheck ) { %>
          <input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled>
          <input name="daxie" size="25" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(loanAmount),1)%>" class=box disabled>
          <% }else{ %>
  		  	<script language="javascript">
        		createAmountCtrl("frm","loanamount","<%=sloanAmount%>","bankinterest","daxie");
          	</script>	
          	����д��<input type="text" name="daxie" size="25" value="" class=box disabled>
          <% } %>			
		  </TD>
          <TD height=26 ><font color='#FF0000'>* </font>�������:</TD>
          <TD colSpan=6 height=26> 
          	<% if ( interestRate==0 ) {%>
          	  <input class=box name="bankinterest" size=8 value="" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
          	<% }else{ %>
              <input class=box name="bankinterest" size=8 value="<%=DataFormat.formatRate(interestRate)%>" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
            <% } %>  
              �� ������ 
             <% if ( chargeRate==0 ){ %>
             <input class=box name="chargerate" size=8  value="" onfocus="nextfield='startdate'" <%if (isCheck) {%>disabled<%}%> >
              ��
             <%  }else{ %>
             <input class=box name="chargerate" size=8  value="<%=DataFormat.formatRate(chargeRate)%>" onfocus="nextfield='startdate'" <%if (isCheck) {%>disabled<%}%> >
              ����
             <% } %>
              
          </TD>
		</TR>
		  
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P><font color='#FF0000'>* </font>�������:<BR><BR><font color='#FF0000'>* </font>����:</P>
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
              �� 
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
              ��
          </TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33><font color='#FF0000'>* </font>�����;:</TD>
          <TD height=33 ><TEXTAREA  class=box cols=30 name="loanpurpose"  onfocus="nextfield='wjh'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P>��Ŀ��׼���ؼ��ĺ�:</P></TD>
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
				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
          </TD>
        </TR>
    </TABLE>
    <% }else{ %>
    <TABLE cellPadding=0 height=17 width=100%>
          <TR >
            <TD height=36 >&nbsp; </TD>
            <TD height=36 > ����ָ����:<%=loanCode%></TD>
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
                  <P><U>�����������</U></P>
            </TD>
          </TR>
          <TR> 
            <TD ><font color='#FF0000'>* </font>
	<%
			if ( isyt )
			{
	%>
				������:
	<%
			}
			else
			{
	%>
				�����:
	<%
			}
	%>
            </TD>
            <TD >��</TD>
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
				<TD ><font color='#FF0000'>*</font> �������:</TD>
				<TD >
<%
			if (isyt)
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='sCale'" <%if (isCheck) {%>disabled<%}%> >��	
<%
			}
			else
			{
%>
					<INPUT class=box name="intervalnum" size=6 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanreason'" <%if (isCheck) {%>disabled<%}%> >��	
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
					<TD ><font color='#FF0000'>&nbsp;</font> ����˾�д����:</TD>
					<TD >��</TD>
					<TD >
						<input name="saleamount" size="18" value="<%=sselfAmount%>" class=tar readonly>
					</TD>
					<TD ><font color='#FF0000'>*</font> �д�����:</TD>
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
            <TD > <font color='#FF0000'>* </font>���ԭ��:</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA  class=box cols=65 name="loanreason"  onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanReason%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD ><font color='#FF0000'>* </font>�����;:</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA  class=box cols=65 name="loanpurpose"  onfocus="nextfield='hkly'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD height=50 > <font color='#FF0000'>* </font>������Դ����ʩ:</TD>
            <TD height=50 >&nbsp;            </TD>
            <TD colSpan=5 height=50 > <TEXTAREA  class=box cols=65 name="hkly"  onfocus="nextfield='clientinfo'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA></TD>
          </TR>
          <TR > 
            <td height=50 colspan=2> <font color='#FF0000'>* </font>��λ������:</td>
            
            <td colspan=5 height=50 > <textarea  class=box cols=65 name="clientinfo" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=clientInfo%></textarea></td>
          </TR>
          <TR > 
            <TD colSpan=7 height=40 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
          <TR > 
            <TD colSpan=7 align="right"> 
 				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
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
		//OBHtml.showExceptionMessage(out,sessionMng,ie,"�ͻ�ѡ��","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>