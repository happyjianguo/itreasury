<!-- 
/**
 * ҳ������ ��l006-v.jsp
 * ҳ�湦�� : ���������������
 * �޸ļ�¼��
 * 2007-03-09 mzh_fu ����������б��س�������ת������
 * 2007-03-15 mzh_fu 1.�����ҳ�����һ��ҳ��㡰��һ����ʱ���ֵ�JS����2.������":"�ĳ�"��"��3.����˽�����ʴ����벻��������
 */
 -->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
	com.iss.itreasury.util.*,
	com.iss.itreasury.loan.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>

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
		System.out.println("^^^^^^^^^^^loanType=======^^^^^^^^^^^^^"+loanType);
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
    	boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
		String panduan="0";
		String strProperty = "";
    
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue();
    
    	OBLoanApplyInfo lInfo= (OBLoanApplyInfo) request.getAttribute("LoanApplyInfo");
    	
		long wtClientID=lInfo.getConsignClientID();			//ί�пͻ�
		//String loanCode=lInfo.getApplyCode();				//�����
		String loanCode=lInfo.getInstructionNo();
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
		long chargeRateTypeID  = lInfo.getChargeRateTypeID();//��ȡ��ʽ
		double assureChargeRate = lInfo.getAssureChargeRate();		//��������
		long assureChargeTypeID = lInfo.getAssureChargeTypeID();	//��������ȡ��ʽ
		long assureTypeID1 = lInfo.getAssureTypeID1();				//��������1
		long assureTypeID2 = lInfo.getAssureTypeID2();				//��������2
		String beneficiary = lInfo.getBeneficiary();				//������
	
		/*�Կ�ֵ���д���*/
		if ( clientInfo==null )
			clientInfo="";
		if ( loanReason==null )
			loanReason="";
		if ( loanPurpose==null )
			loanPurpose="";
		if ( other==null )
			other="";
		if ( beneficiary == null )
			beneficiary="";
		if ( intervalNum<1){
            	intervalNum=12;
           
		}	
 
 	/*�����������ݺͽ�����ݽ������⴦��*/
 	String sstartDate="";
 	String sendDate="";
 	String sloanAmount="";
 	String sselfAmount="";
	String sScale = "";
	String str1="���";
	String str2="������Դ����ʩ��";

 	
 	if ( startDate!=null )
 		sstartDate=DataFormat.getDateString(startDate);
 	if ( endDate!=null )
 		sendDate=DataFormat.getDateString(endDate);
 	sloanAmount=DataFormat.formatNumber(loanAmount,2);
 	sselfAmount=DataFormat.formatNumber(selfAmount,2);
	sScale =DataFormat.formatNumber(selfScale,6);
	
    if ( loanType==OBConstant.LoanInstrType.LOAN_WT){
    	iswt=true;
		panduan="1";
	}else if(loanType==OBConstant.LoanInstrType.ASSURE){
		str1="����";
		str2="��ע��";
		panduan="2";
	}
	isyt=false;
        //��ʾ�ļ�ͷ
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[�����������]",Constant.YesOrNo.NO);
        else	
        	OBHtml.showOBHomeHead(out,sessionMng,"[�����������]",Constant.YesOrNo.YES);
        
        if ( action.equals("check") ){
       		isCheck=true;	
			strProperty = " disabled ";
		}
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>
<safety:resources />
<form name="frm" method="post"><input type=hidden name=pand value=<%=panduan%>>
<TABLE border=0 class=top height=254 width=730>
<TR class="tableHeader">
    <TD class=FormTitle height=29><B><%=loanTypeName%>������</B></TD>
</TR>
<TR>
    <TD>
    <% if (iswt){%>

      <TABLE align=center border=0 width=730>
      <TR>
          <TD height=28 >&nbsp;</TD>
          <TD colSpan=3 height=28>����ָ���ţ�<%=loanCode%></TD>
          <TD colSpan=3 height=28>�����������ƣ�
	<%LOANConstant.SubLoanType.showList(out,"subLoanType",-1,false,false," onfocus=\"nextfield='loanamount'\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
			
			</TD>
         
	    </TR>
		<tr>
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
          <TD height=26 ><font color='#FF0000'>* </font>����</TD>
          <TD height=26 align=right>��</TD>
          <TD height=26 >
          <% if ( isCheck ) { %>
          <input name="loanamount" size="25" value="<%=sloanAmount%>" class=box disabled><br>
          <input name="daxie" size="25" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(loanAmount),1)%>" class=box disabled>
          <% }else{ %>
  		  	<script language="javascript">
        		createAmountCtrl("frm","loanamount","<%=sloanAmount%>","bankinterest","daxie");
          	</script>	<br>
          	<input type="text" name="daxie" size="20" value="" class=box disabled>����д��
          <% } %>			
		  </TD>
          <TD height=26 valign="baseline"  align="right"><font color='#FF0000'>* </font>������ʣ�</TD>
          <TD colSpan=6 height=26> 
          	<% if ( interestRate==0 ) {%>
          	  <input class=box name="bankinterest" size=8 value="" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
          	<% }else{ %>
              <input class=box name="bankinterest" size=8 value="<%=DataFormat.formatRate(interestRate)%>" onfocus="nextfield='chargerate'" <%if (isCheck) {%>disabled<%}%> >
            <% } %>  
              �� �����ѣ�
             <% if ( chargeRate==0 ){ %>
             <input class=box name="chargerate" size=8  value="" onfocus="nextfield='checkPayType'" <%if (isCheck) {%>disabled<%}%> >
              ��
             <%  }else{ %>
             <input class=box name="chargerate" size=8  value="<%=DataFormat.formatRate(chargeRate)%>" onfocus="nextfield='checkPayType'" <%if (isCheck) {%>disabled<%}%> >
              ��
             <% } %>
              <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              ��������ȡ��ʽ�� 
              <% if (!isCheck){ %>
                      <select class='box' name="checkPayType"  onfocus="nextfield='startdate'" >
                        <option value=-1>��</option>
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              %> 
                <option value="<%=typeVal[i]%>" <% if (typeVal[i]==chargeRateTypeID ) {%> selected <% } %>><%=typeName%></option>
              <%	
              	}
              %> 
                      </select>
                   <%}else{%>
                  <select class='box' name="checkPayType"  onfocus="nextfield='startdate'" >
			 <%
              	long typeVal[]=LOANConstant.ChargeRatePayType.getAllCode();
              	String typeName="";
              	for ( int i=0;i<typeVal.length;i++ )
              	{
              		typeName=LOANConstant.ChargeRatePayType.getName(typeVal[i]);
              		if (typeVal[i]==chargeRateTypeID)
              		{
              %> 
                <option value="<%=typeVal[i]%>" selected ><%=typeName%></option>
              <%	
              		}
              	}
              %> 
                      </select>
                   <%}%>
              
          </TD>
		</TR>
		  
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P><font color='#FF0000'>* </font>������ڣ�<BR><BR><font color='#FF0000'>* </font>���ޣ�</P>
          </TD>
          <TD colSpan=3 height=33>
	          <fs_c:calendar 
	          	    name="startdate"
		          	value="" 
		          	properties="nextfield='enddate';" 
		          	size="20"/>
		          				          	  <script>
	          		$('#startdate').val('<%=sstartDate%>');
	          	</script>
		          	<script>
		          		$('#startDate').blur(function (){
		          			writed();
		          		});
		          	</script>
		          	 <%if (isCheck) {%>
		          	 	<script>
		          	 		$('#startDate').attr('readonly','true');
		          	 	</script>
		          	 <%}%>
		          	 <!-- 
              <input class=box onBlur="writed()" name="startdate" value="<%=sstartDate%>" <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='enddate'">
              <A href="javascript:show_calendar('frm.startdate');" onMouseOut="window.status='';return true;" onMouseOver="window.status='Date Picker';return true;"><IMG border="0" height="16" src="/websett/image/calendar.gif" width="17"></A>  -->
              �� 
              <INPUT class=box name="enddate" value="<%=sendDate%>" <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='intervalnum'" readonly>
              <!--A href="javascript:show_calendar('frm.enddate');" onMouseOut="window.status='';return true;" onMouseOver="window.status='Date Picker';return true;"--><IMG border="0" height="16" src="/websett/image/calendar.gif" width="17">
              <br>
              <INPUT class=box onblur="writed()" name="intervalnum" size=3 maxlength="3" value="<%=intervalNum%>" onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> >
              ��
          </TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33><font color='#FF0000'>* </font>�����;��</TD>
          <TD height=33 ><TEXTAREA   cols=30 name="loanpurpose"  onfocus="nextfield='wjh'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA> </TD>
          <TD height=33 >&nbsp;</TD>
          <TD height=33 >&nbsp;</TD>
          <TD align=right colSpan=5 height=24 >&nbsp;</TD></TR>
        <TR>
          <TD height=24 >&nbsp;</TD>
          <TD colSpan=2 height=33>
            <P>��Ŀ��׼���ؼ��ĺţ�</P></TD>
          <TD height=33 ><TEXTAREA  cols=30 name="wjh" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA> </TD>
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
             <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �� �� " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ " onKeyDown="if(event.keyCode==13) confirmSave(frm);"> 		
			<%      }         %>
			<% }else if (action.equals("check") ) { %>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 		
			<% }else { %>
            <INPUT class=button name="subnext" onclick="confirmSave(frm)" type="button" value=" ��һ�� " onKeyDown="if(event.keyCode==13) confirmSave(frm);">
            <% } %>
          </TD>
        </TR>
    </TABLE>
    <% }else{ %>
    <TABLE cellPadding=0 height=17 width=100%>
          <TR >
            <TD height=36 >&nbsp; </TD>
            <TD height=36 > ����ָ���ţ�<%=loanCode%></TD>
           <TD  height=28>�����������ƣ�  			<%LOANConstant.SubLoanType.showList(out,"subLoanType",-1,false,false,"onfocus=\"nextfield='loanamount'\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
			</TD>
          </TR>
       </TABLE>
       <TABLE cellPadding=0 height=50 >
          <TR > 
            <TD colSpan=7 height=66 vAlign=bottom> 
                <HR align=center SIZE=2 width="100%">
                  <P><U><%=str1%>��������</U></P>
            </TD>
          </TR>
          <TR> 
            <TD ><font color='#FF0000'>* </font><%=str1%>��
            </TD>
            <TD align="right">��</TD>
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
				<TD ><font color='#FF0000'>*</font> <%=str1%>���ޣ�</TD>
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
					<TD ><font color='#FF0000'>&nbsp;</font> ����˾�д���ȣ�</TD>
					<TD >��</TD>
					<TD >
						<input name="saleamount" size="18" value="<%=sselfAmount%>" class=tar readonly>
					</TD>
					<TD ><font color='#FF0000'>*</font> �д�������</TD>
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

 <%if(loanType==OBConstant.LoanInstrType.ASSURE){%>
			<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>�������ʣ�</FONT></TD>
					<TD width="82"></TD>
					<TD width="180">
					<input  class=tar  name="assureChargeRate" size=8 value="<%=DataFormat.formatRate(assureChargeRate)%>" onblur="formatChargeRate('frm','assureChargeRate');" onfocus="nextfield='assureChargeTypeID'" <%if (isCheck) {%>disabled<%}%> >
              % </TD>
					
            <TD width="100">&nbsp;</TD>
					
            <TD width="150">&nbsp; </TD>
				</tr>

						<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>��������ȡ��ʽ��</FONT></TD>
					
            <TD width="82">&nbsp;</TD>
					<TD width="180">
						<%
							 String strPaytype = strProperty + " onfocus=nextfield='beneficiary'; ";
							LOANConstant.ChargeRatePayType.showList(out,"assureChargeTypeID",0,assureChargeTypeID,false,false,strPaytype,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
						%>
					</TD>
					
            <TD width="100"><FONT size=2>�����ˣ�</FONT></TD>
					<TD width="150">
						<INPUT class=box name="beneficiary" size=15 maxlength="25" value="<%=beneficiary%>"  <%if (isCheck) {%>disabled<%}%> onfocus="nextfield='assureTypeID1'"> 
					</TD>
				</tr>
				<tr>
					
            <TD width="170"><font color='#FF0000'>* </font> <FONT size=2>��������һ��</FONT></TD>
					<TD width="82"></TD>
					<TD width="180">
						<%
							String strProperty1 = strProperty + "onfocus=nextfield='assureTypeID2'; onchange='selectChange(this, frm.assureTypeID2, arrItems1, arrItemsGrp1,"+assureTypeID2+");' ";
							LOANConstant.AssureType1.showList(out,"assureTypeID1",0,assureTypeID1,false,true,strProperty1,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
						%>					
            </TD>
					
            <TD width="100"><font color='#FF0000'>*</font> <FONT size=2>�������Ͷ���</FONT></TD>
					
            <TD width="150">
				<% if( assureTypeID2<0 ){ %>
					<SELECT class='box' id=assureTypeID2 name=assureTypeID2 onfocus="nextfield='loanreason'"></SELECT>
				<%}else {
							String strProperty2 = strProperty + "onfocus=nextfield='loanreason'; ";
							LOANConstant.AssureType2.showList(out,"assureTypeID2",(int)assureTypeID1,assureTypeID2,false,true,strProperty2,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);								
				}%>
			</TD>
				</tr>

<%}%>

          <TR> 
            <TD > <font color='#FF0000'>* </font><%=str1%>ԭ��</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA   cols=65 name="loanreason"  onfocus="nextfield='loanpurpose'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanReason%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD ><font color='#FF0000'>* </font><%=str1%>��;��</TD>
            <TD >&nbsp;</TD>
            <TD colSpan=5 height=50> <TEXTAREA   cols=65 name="loanpurpose"  onfocus="nextfield='hkly'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=loanPurpose%></TEXTAREA></TD>
          </TR>
          <TR > 
            <TD height=50 ><%if(loanType!=OBConstant.LoanInstrType.ASSURE){%> <font color='#FF0000'>* </font><%}%><%=str2%></TD>
            <TD height=50 >&nbsp;            </TD>
            <TD colSpan=5 height=50 > <TEXTAREA  cols=65 name="hkly"  onfocus="nextfield='clientinfo'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=other%></TEXTAREA></TD>
          </TR>
		  <%if(loanType!=OBConstant.LoanInstrType.ASSURE){%>
          <TR > 
            <td height=50 colspan=2> <font color='#FF0000'>* </font>��λ�����飺</td>
            
            <td colspan=5 height=50 > <textarea   cols=65 name="clientinfo" onfocus="nextfield='subnext'" <%if (isCheck) {%>disabled<%}%> onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" ><%=clientInfo%></textarea></td>
          </TR>
		  <%}%>
          <TR > 
            <TD colSpan=7 height=40 > 
              <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
          <TR > 
            <TD colSpan=7 align="right"> 
             <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �� �� "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ "> 		
			<%      }         %>	
			<% }else if (action.equals("check") ) { %>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 	
			<% }else { %>
            <INPUT class=button name="subnext" onclick="confirmSave(frm)" type="button" value=" ��һ�� " onKeyDown="if(event.keyCode==13) confirmSave(frm);">
            <% } %>
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
	var arrItems1 = new Array();
	var arrItemsGrp1 = new Array();

	arrItems1[0] = "";
	arrItemsGrp1[0] = -1;
	arrItems1[1] = "�����";
	arrItemsGrp1[1] = 1;
	arrItems1[2] = "ó�����µĹ��ڵ���";
	arrItemsGrp1[2] = 1;
	arrItems1[3] = "ó�����µĹ��ⵣ��";
	arrItemsGrp1[3] = 1;	
	arrItems1[4] = "��Ͷ�굣��";
	arrItemsGrp1[4] = 2;
	arrItems1[5] = "��Լ����";
	arrItemsGrp1[5] = 2;
	arrItems1[6] = "�ʱ�";
	arrItemsGrp1[6] = 2;
	arrItems1[7] = "Ԥ�����";
	arrItemsGrp1[7] = 2;
	arrItems1[8] = "����";
	arrItemsGrp1[8] = 0;

function selectChange(control, controlToPopulate,ItemArray, GroupArray, selectValue)
{
  var myEle ;
  var x ;
  
  //mzh_fu 2007-03-15
  if(controlToPopulate!=null){
	  // Empty the second drop down box of any choices
	  for (var q=controlToPopulate.options.length;q>=0;q--) controlToPopulate.options[q]=null;
	  // ADD Default Choice - in case there are no values
	  //myEle = document.createElement("option") ;
	  //myEle.value = 0 ;
	  //myEle.text = "[SELECT]" ;
	  //controlToPopulate.add(myEle) ;
	  for ( x = 0 ; x < ItemArray.length  ; x++ )
	    {
	      if ( GroupArray[x] == control.value || GroupArray[x] == 0)
	        {
	          myEle = document.createElement("option") ;
	          myEle.value = x ;
	          myEle.text = ItemArray[x] ;
			  myEle.selected=false;
			  
			  if(x==selectValue)
			  {		 	
				myEle.selected=true;
			  }	
	          controlToPopulate.add(myEle) ;
	        }
	    }
    }
}


function writed()
{	
	if (frm.startdate.value!="" && frm.intervalnum.value!="")
    {
    	if(!checkDate(frm.startdate,1,"�����ʼ����"))			
		{
			return false;
		}
		if (!InputValid(frm.intervalnum, 1, "int", 1, 1, 999,"�������")) 
		{
			return false;
		}		
   		frm.enddate.value=addDate(frm.startdate.value,eval(frm.intervalnum.value));
   	}
}
function confirmClose()
{
	window.close();
}

function changeScale(frm)
{
	if (!InputValid(frm.sCale,1, "float",1,0.01,100,"�д�����")) 
	//if(!checkAmount(frm.sCale,1,"�д�����"))
	{
	    return false;
	}
	frm.saleamount.value = formatAmount(eval(reverseFormatAmount1(frm.loanamount.value))*eval(frm.sCale.value)/100);
}

function addDate(strInputDate,lMonth)
{
 	var temp,s;
    temp=new String(strInputDate);
    s=new String("");
    for(var i=0;i<=temp.length-1;i++)
    {
    	if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
    	{
      		s=s+"/";
  		} 
    	else
    	{
    		if(isNaN(Number(temp.charAt(i))))
	      	{
    	   		alert("��������ȷ�Ŀ�ʼ����");
       			form.txtDateFixedStart.focus();
       			return false;
   			}    
    		else
   			{
    			s=s+temp.charAt(i);
   			}
  		} 
   	}
   	var dtDate;
 	dtDate = new Date(s);
 	var strDate;
 	var yy,mm,temp;
 
 	var dtDay = dtDate.getDate();
 	temp = parseInt(lMonth) + dtDate.getMonth()+1 ;
 	var dtMonth = temp % 12;
 	var dtYear = dtDate.getYear() + parseInt(temp / 12);
 
 	if(parseInt(dtMonth)==0)
 	{
  		dtMonth='12';
  		dtYear=parseInt(dtYear)-1;
 	}
 	strDate=dtYear + "-" +dtMonth + "-" + dtDay;
 	return strDate;
}
	

function  confirmSave(frm)
{
//ȥ���ո�
trimFormValue();
frm.loanpurpose.value = Trim(frm.loanpurpose.value);

 if(!checkList(frm.subLoanType,"��������"))
    return false;
<% if (isyt) {%>
	//����� loanamount
	if(!checkAmount(frm.loanamount,1,"������"))
	{
	    return false;
	}

	//����˾�д�����
	if (!InputValid(frm.sCale,1, "float",1,0.01,100,"�д�����")) 
	{
		return false;
	}
	if((frm.sCale.value <= 0) || (frm.sCale.value > 100 ))
	{
		
		alert("����˾�д���������С��0.01�����100%!");
		return false;
	}
	changeScale(frm);
	//����˾�д���� saleamount
	if(!checkAmount(frm.saleamount,1,"����˾�д����"))
	{
	    return false;
	}
	
	if ( parseFloat(reverseFormatAmount1(frm.saleamount.value))>parseFloat(reverseFormatAmount1(frm.loanamount.value)) )
	{
		alert("����˾�д���Ȳ��ܴ��ڴ�����!");
		return false;
	}
<%}else{%>
	//����� loanamount
	if(!checkAmount(frm.loanamount,1,"�����"))
	{	
		frm.loanamount.focus();
	    return false;
	}
<%}%>	
<% if (panduan.equals("1") ) { %>
	//��ʼ���� startdate
	if(!checkDate(frm.startdate,1,"�����ʼ����"))
	{frm.startdate.focus();
		return false;
	}
	//�������� enddate
	if(!checkDate(frm.enddate,1,"�����ʼ����"))
	{frm.enddate.focus();
		return false;
	}
	
	startExe1 = formatedate(document.frm.startdate.value) ;
	endExe1 = formatedate(document.frm.enddate.value) ;
	
	if( startExe1.length != 0 && endExe1.length != 0 &&  startExe1 > endExe1){
		alert("��ʼ���ڲ��ܴ��ڽ�������");
		document.frm.enddate.focus();
		return false;
	} 
	/*	
	//������� interestrate
	if (!InputValid(frm.interestrate, 1, "float", 0, 0, 0,"�������")) 
	{
		return false;
	}
	if (!InputValid(frm.chargerate, 1, "float", 0, 0, 0,"������")) 
	{
		return false;
	}
	*/
	
	if(!checkRate(frm.bankinterest,1,"�������"))
	{frm.bankinterest.focus();
		  return false;
	}
	//�������� chargerate
	if(!checkRate(frm.chargerate,1,"������"))
	{frm.chargerate.focus();
	  return false;
	}
	
	//����������ȡ��ʽ checkPayType
	if (frm.checkPayType.value==-1)
	{
		alert("����������ȡ��ʽ����Ϊ��");
		frm.checkPayType.focus();
		return false;
	}
		if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"�����;")) 
	{frm.loanpurpose.focus();
		return false;
	}
	
	
	//��Ŀ��׼���ؼ��ʺ� other
<%} else if(panduan.equals("2")) {%>

	if (!InputValid(frm.assureChargeRate,1, "float",1,0.01,100,"��������")) 
	{
		frm.assureChargeRate.focus();
	    return false;
	}
	if (frm.assureTypeID1.value==-1)
	{
		alert("��������һ����Ϊ��");
		frm.assureTypeID1.focus();
		return false;
	}
	//���ԭ�� loanreason
	if (!InputValid(frm.loanreason, 1, "string", 0, 0, 100,"����ԭ��")) 
	{
		frm.loanreason.focus();
		return false;
	}
	//�����; loanpurpose
	if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"������;")) 
	{
		frm.loanpurpose.focus();
		return false;
	}
	
<%}else{%>	
	//���ԭ�� loanreason
	if (!InputValid(frm.loanreason, 1, "string", 0, 0, 100,"���ԭ��")) 
	{
		frm.loanreason.focus();
		return false;
	}
	//�����; loanpurpose
	if (!InputValid(frm.loanpurpose, 1, "string", 0, 0, 100,"�����;")) 
	{
		frm.loanpurpose.focus();
		return false;
	}
	//������Դ�ʹ�ʩother
	
	if (!InputValid(frm.hkly, 1, "string", 0, 0, 100,"������Դ����ʩ") )
	{
		frm.hkly.focus();
		return false;
	}
	
	//��λ��� clientinfo
	if (!InputValid(frm.clientinfo, 1, "string", 0, 0, 100,"��λ����")) 
	{
		frm.clientinfo.focus();
		return false;
	}
<%}%>
	if (!InputValid(frm.intervalnum, 1, "int", 1, 1, 999,"����")) 
	{
		frm.intervalnum.focus();
		return false;
	}
	
		
	showSending();
	
	frm.action="l007-c.jsp";
	frm.submit();
	return true;
 }
 function formatChargeRate(fromName,rateCntlName)
 {
  var strData=eval(fromName + '.' + rateCntlName + '.value');
	if(isInnerFloat(strData))
 	{
 		
		if(strData!="")
 		{
			var i,strTemp;

			//��С����ǰ�ͺ�����ݷֱ�ȡ����
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//С����ǰ�������λ����0
			if (strFront.length==0)
			{
				strFront = "0";
			}
			//�����߽�С��������ֵ��������λ
	 		if(strEnd.length>6)
	 		{
	 			strEnd=strEnd.substring(0,6);
	 		}
	 		else
	 		{
	 			if(strEnd.length==1)
	 			{
	 				strEnd=strEnd+ "00000";
	 			}
	 			else
				if (strEnd.length==2)
				{
					strEnd=strEnd+ "0000";
				}
				else
				if (strEnd.length==3)
				{
					strEnd=strEnd+ "000";
				}
				else
				if (strEnd.length==4)
				{
					strEnd=strEnd+ "00";
				}
				else
				if (strEnd.length==5)
				{
					strEnd=strEnd+ "0";
				}
				else
	 				if(strEnd.length==0)
	 				{
	 					strEnd="000000";
	 				}
	 		}
	 		strData=strFront+"." + strEnd;
 		}
		else
		{
			strData = "0.000000";
		}
	}
	eval(fromName + "." + rateCntlName + ".value='"+strData+"'");
 }
<%if (action.equals("modify") ) {%>
firstFocus(frm.loanamount);
<%}%>
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
<%if(assureTypeID1<0){%>	
selectChange(frm.assureTypeID1, frm.assureTypeID2, arrItems1, arrItemsGrp1,"<%=assureTypeID2%>");
<%}%>
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