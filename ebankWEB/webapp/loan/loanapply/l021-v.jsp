<%
/**
 * ҳ������ ��l021-v.jsp
 * ҳ�湦�� : ��������ִ�мƻ���ά������
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
		 
		OBLoanApplyInfo appInfo=(OBLoanApplyInfo)request.getAttribute("LoanApplyInfo");		//����������Ϣ
		Vector planInfo=(Vector)request.getAttribute("Collection");						//����ƻ���Ϣ
	
    	long loanType=appInfo.getTypeID();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
    	String action="";
		boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	long statusID=-1;
    
    	long loanID=appInfo.getID();
    	long clientID=appInfo.getBorrowClientID();
    	long wtClientID=appInfo.getConsignClientID();
    	//String applyCode=appInfo.getApplyCode();
    	String applyCode=appInfo.getInstructionNo();
    
    	double interestRate=appInfo.getInterestRate();						//����ִ������
    	double adjustRate=appInfo.getAdjustRate();							//��������
    	System.out.println("adjust:"+adjustRate);
    	interestRate=interestRate*(1+adjustRate/100);								//ִ������
    	
    	Timestamp applyDate=appInfo.getInputDate();							//��������ʱ��
    	long intervalNum=appInfo.getIntervalNum();							//����
    	Timestamp applyStartDate=appInfo.getStartDate();					//�������뿪ʼʱ��
    	double loanAmount=appInfo.getLoanAmount();
    	long planVersion=appInfo.getPlanVersion();
    
    	long isCredit=appInfo.getIsCredit();
    	long isAssure=appInfo.getIsAssure();
    	long isPledge=appInfo.getIsPledge();
    	long isImpawn=appInfo.getIsImpawn();
    
    	long		payType=-1;			//�ſʽ
		long 		repayType=-1;		//���ʽ
		Timestamp	payStart=null;
		Timestamp	payEnd=null;
		Timestamp	repayStart=null;
		Timestamp	repayEnd=null;
    
    	Timestamp nowTime=DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)));
    
    	String strPayDate="";
    	String strRepayDate="";
    	
    	if ( applyStartDate==null )
    	{
    		strPayDate=DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));
    	}
    	else
    		strPayDate=DataFormat.getDateString(applyStartDate);
    	if ( applyStartDate==null )
    		strRepayDate=DataFormat.getDateString(DataFormat.getNextMonth(nowTime,(int)intervalNum));
    	else
    		strRepayDate=DataFormat.getDateString(DataFormat.getNextMonth(applyStartDate,(int)intervalNum));	
    
    	double		payAmount=0;			//�ſ�ϼ�
    	double		repayAmount=0;			//����ϼ� 
    
    	long  pageLine=Constant.PageControl.CODE_PAGELINECOUNT;
    	long  planCount=appInfo.getPlanDetailCount();
    	long  pageCount=planCount / pageLine;
    	if ( (planCount%pageLine)!=0 )
    		pageCount++;
     	
    	long pageNo=0;
    	long orderParam=-1;
    	long lDesc=Constant.PageControl.CODE_ASCORDESC_ASC;
    	String tmp="";
		tmp=(String)request.getAttribute("nOrderParam");
		if ( (tmp!=null)&&(tmp.length()>0) )
			orderParam=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("nDesc");
		if ( (tmp!=null)&&(tmp.length()>0) )
			lDesc=Long.valueOf(tmp).longValue();

		tmp=(String)request.getAttribute("nPageNo");
		
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			pageNo=Long.valueOf(tmp).longValue();
		}
		else		
		{
			pageNo=1;
		}
		if ( pageCount<=0 ) pageCount=1;
		
		tmp=(String)request.getAttribute("txtlPayType");		//�ſʽ
		if ( (tmp!=null)&&(tmp.length()>0) )
			payType=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("txtlRepayType");		//���ʽ
		if ( (tmp!=null)&&(tmp.length()>0) )
			repayType=Long.valueOf(tmp).longValue();
		
		tmp=(String)request.getAttribute("txtdtInputDate11");		//��ʼ�ſ�����
		if ( (tmp!=null)&&(tmp.length()>0) )	
			payStart=DataFormat.getDateTime(tmp);
		
			
		tmp=(String)request.getAttribute("txtdtInputDate12");		//��ֹ�ſ�����
		if ( (tmp!=null)&&(tmp.length()>0) )	
			payEnd=DataFormat.getDateTime(tmp);
			
		tmp=(String)request.getAttribute("txtdtInputDate21");		//��ֹ��������
		if ( (tmp!=null)&&(tmp.length()>0) )	
			repayStart=DataFormat.getDateTime(tmp);
			
		tmp=(String)request.getAttribute("txtdtInputDate22");		//��ֹ��������
		if ( (tmp!=null)&&(tmp.length()>0) )	
			repayEnd=DataFormat.getDateTime(tmp);
		
		
		payAmount=appInfo.getPlanPayAmount();
		repayAmount=appInfo.getPlanRepayAmount();
		
		tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		statusID=appInfo.getStatusID();     
			
        //��ʾ�ļ�ͷ
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[����ƻ�ά��]",Constant.YesOrNo.NO);
        else	
        	OBHtml.showOBHomeHead(out,sessionMng,"[����ƻ�ά��]",Constant.YesOrNo.YES);
        if ( action.equals("check") )
       	isCheck=true;	 
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<FORM name="form1">
<TABLE border=0 class=top height=420 width="86%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=19><B><%=loanTypeName%>����ԭʼ�ƻ�����</B></TD>
  </TR>
  <TR>
    <TD height=60>
      <TABLE align=center border=0 height=94 width=730>
        <TBODY>
        <TR borderColor=#ffffff>
          <TD height=28 colspan=3>����ָ����:</TD>
          <TD height=28 colspan=2 align="left"><%=applyCode%> </TD>
		  <TD height=28  nowrap align="right">�����������ƣ�<%LOANConstant.SubLoanType.showList(out,"subLoanType",appInfo.getSubTypeId(),false,true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%></TD>
	  <TD height=46 width=80>&nbsp; </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=28 >&nbsp;</TD>
          <TD colSpan=6 height=28> <HR> </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=28 >&nbsp;</TD>
          <TD height=28 ><U>�ſ�ƻ�</U></TD>
          <TD height=28 ></TD>
          <TD height=28 ></TD>
          <TD height=28 ></TD>
          <TD height=28 >&nbsp;</TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 noWrap >�ſʽ:</TD>
          <TD height=29 >
          	<SELECT  class=box name=txtlPayType size=1 <%if (isCheck) {%>disabled<%}%> > 
          		<%
                	long ptypes[] = LOANConstant.PayType.getAllCode();
                	String ptypename="";
                	for ( int i=0;i<ptypes.length;i++ )
                	{
                		ptypename=LOANConstant.PayType.getName(ptypes[i]);
                		if ( payType!=-1 )
                		{
                %>		
                		<option value="<%=ptypes[i]%>" <% if (ptypes[i]==payType) {%> selected <% } %>><%=ptypename%></option>
                <%		}else{	%>
                		
                		<option value="<%=ptypes[i]%>" <% if (ptypes[i]==LOANConstant.PayType.ONETIME  ) {%> selected <% } %>><%=ptypename%></option>
				<%
						}
					}                
                %>
              
            </SELECT> 
          </TD>
          <TD height=29 noWrap >&nbsp;&nbsp;��ʼ�ſ�����:</TD>
          <TD height=29 ><!-- 
          <%// if ( payStart!=null ) { %>
          	<INPUT class=box name=txtdtInputDate11 onfocus="nextfield ='txtdtInputDate12';" value=<%=DataFormat.getDateString(payStart)%> <%if (isCheck) {%>disabled<%}%> > 
          <%// }else { %>	
          	<INPUT class=box name=txtdtInputDate11 onfocus="nextfield ='txtdtInputDate12';" value=<%=strPayDate%> <%if (isCheck) {%>disabled<%}%> > 
          <%// } %>-->
          <% if (!isCheck){ %>	 
         	<fs_c:calendar 
          	    name="txtdtInputDate11"
	          	value="" 
	          	properties="nextfield ='txtdtInputDate12'" 
	          	size="20"/>
	          	<script>
	          		$('#txtdtInputDate11').val('<%=if(payStart!=null )return DataFormat.getDateString(payStart);else return strPayDate;%>');
	          	</script>
							          	<!-- 
          	<A href="javascript:show_calendar('form1.txtdtInputDate11');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
          		<IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> 
          		 -->
          <% }else{ %>
             <INPUT class=box name=txtdtInputDate11 onfocus="nextfield ='txtdtInputDate12';" value=<%=if(payStart!=null )return DataFormat.getDateString(payStart);else return strPayDate;%> disabled > 
          <%} %>		
          </TD>
          <TD height=29 noWrap >&nbsp;&nbsp;��ֹ�ſ�����:</TD>
          <TD height=29 noWrap >
          
          <!-- 
          <%// if (payEnd!=null) { %>
          	<INPUT class=box name=txtdtInputDate12 onfocus="nextfield ='txtdtInputDate21';" value=<%=DataFormat.getDateString(payEnd)%> <%if (isCheck) {%>disabled<%}%>  >
          <%// } else { %>	 
          	<INPUT class=box name=txtdtInputDate12 onfocus="nextfield ='txtdtInputDate21';" value=<%=strPayDate%> <%if (isCheck) {%>disabled<%}%> > 
          <%// } %> -->
          <% if (!isCheck){ %>	
          <fs_c:calendar 
          	    name="txtdtInputDate12"
	          	value="" 
	          	properties="nextfield ='txtdtInputDate21'" 
	          	size="20"/>
	          	<script>
	          		$('#txtdtInputDate12').val('<%=if (payEnd!=null)return DataFormat.getDateString(payEnd) ;else return strPayDate;%>');
	          	</script>
	          	<!-- 
          	<A href="javascript:show_calendar('form1.txtdtInputDate12');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
          	<IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> 
          	 -->
         <% } else{%>
                   	<INPUT class=box name=txtdtInputDate12 onfocus="nextfield ='txtdtInputDate21';" value=<%=if (payEnd!=null)return DataFormat.getDateString(payEnd) ;else return strPayDate;%> disabled  >
         
         <%} %> 	
          </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 ><U>����ƻ�</U></TD>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 >&nbsp;</TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=29 >&nbsp;</TD>
          <TD height=29 noWrap >���ʽ:</TD>
          <TD height=29 >
          
          <SELECT class=box name=txtlRepayType size=1 <%if (isCheck) {%>disabled<%}%> > 
				<%
                	long types[] = LOANConstant.RepayType.getAllCode();
                	String typename="";
                	for ( int i=0;i<types.length;i++ )
                	{
                		typename=LOANConstant.RepayType.getName(types[i]);
                		if ( repayType!=-1 )
                		{
                %>		
                		<option value="<%=types[i]%>" <% if (types[i]==repayType ) {%> selected <% } %>><%=typename%></option>
                <%		}else{		%>		
                		<option value="<%=types[i]%>" <% if (types[i]==LOANConstant.RepayType.ONETIME ) {%> selected <% } %>><%=typename%></option>
				<%
						}
					}                
                %>
            
          </SELECT> 
          </TD>
          <TD height=29 noWrap >&nbsp;&nbsp;��ʼ��������:</TD>
          <TD height=29  noWrap>
          <!-- 
          <%// if ( repayStart!=null ){ %>
          	<INPUT class=box name=txtdtInputDate21 onfocus="nextfield ='txtdtInputDate22';" value=<%=DataFormat.getDateString(repayStart)%> <%if (isCheck) {%>disabled<%}%> >
          <%// }else{ %>	 
          	<INPUT class=box name=txtdtInputDate21 onfocus="nextfield ='txtdtInputDate22';" value=<%=strRepayDate%> <%if (isCheck) {%>disabled<%}%> > 
          <%// } %>	
           -->
           <% if (!isCheck){ %>
           <fs_c:calendar 
          	    name="txtdtInputDate21"
	          	value="" 
	          	properties="nextfield ='txtdtInputDate22'" 
	          	size="20"/>
	          				          	  <script>
	          		$('#txtdtInputDate21').val('<%= if ( repayStart!=null )return DataFormat.getDateString(repayStart);else return strRepayDate;%>');
	          	</script>
	          	<!-- 
          	<A href="javascript:show_calendar('form1.txtdtInputDate21');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
          	<IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> 
          	 -->
           <%}else {%>
               <INPUT class=box name=txtdtInputDate21 onfocus="nextfield ='txtdtInputDate22';" value=<%= if ( repayStart!=null )return DataFormat.getDateString(repayStart);else return strRepayDate;%> disabled >
           <%} %>	
          </TD>
          <TD height=29 noWrap >&nbsp;&nbsp;��ֹ��������:</TD>
          <TD height=29 noWrap >
          <!-- 
          <%// if ( repayEnd!=null) { %>
          	<INPUT class=box name=txtdtInputDate22 onfocus="nextfield ='submitfunction';" value=<%=DataFormat.getDateString(repayEnd)%> <%if (isCheck) {%>disabled<%}%> >
          <%// }else{ %>	 
          	<INPUT class=box name=txtdtInputDate22 onfocus="nextfield ='submitfunction';" value=<%=strRepayDate%> <%if (isCheck) {%>disabled<%}%> > 
          <%// } %>	
           -->
           <% if (!isCheck){ %>
            <fs_c:calendar 
          	    name="txtdtInputDate22"
	          	value="" 
	          	properties="nextfield ='submitfunction'" 
	          	size="20"/>
	          				          	  <script>
	          		$('#txtdtInputDate22').val('<%= if ( repayEnd!=null)  return DataFormat.getDateString(repayEnd); else return strRepayDate;%>');
	          	</script>
	          	<!-- 
          	<A href="javascript:show_calendar('form1.txtdtInputDate22');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
          	<IMG border=0 height=16 src="/webob/graphics/calendar.gif" ></A> 
          	 -->
          <% }else{ %>
            <INPUT class=box name=txtdtInputDate22 onfocus="nextfield ='submitfunction';" value=<%= if ( repayEnd!=null)  return DataFormat.getDateString(repayEnd); else return strRepayDate;%> disabled >
          <%} %>	
          </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=36 >&nbsp;</TD>
          <TD height=36 >&nbsp;</TD>
          <TD colSpan=3 >&nbsp;</TD>
          <TD height=36 >&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=36 >&nbsp;</TD>
          <TD height=36 >&nbsp;</TD>
          <TD height=36 >&nbsp;</TD>
          <TD colSpan=4 >
            <DIV align=right>
            	<% if ( !((action.equals("modify"))||(isCheck)) ) { %>
            	<INPUT class=button name=Submit31 onclick="confirmBack(form1)" type=button value="  ��һ��  "> 
            	<% } %>
				<INPUT class=button name=Submit12 onclick="confirmAuto('ȷ���Զ����żƻ�?')" type=button value=�Զ����żƻ� <%if (isCheck) {%>disabled<%}%> > 
				<INPUT class=button name=Submit13 onclick="addPlan()" type=button value=����ԭʼ�ƻ���ϸ <%if (isCheck) {%>disabled<%}%> > 
            </DIV>
          </TD>
        </TR>
        <TR borderColor=#ffffff>
          <TD height=36 >&nbsp;</TD>
          <TD height=36 >&nbsp;</TD>
          <TD colSpan=4 height=36>&nbsp;</TD>
        </TR>
        </TBODY>
        </TABLE>
   </TD>
  </TR>
  <TR>
    <TD height=171 vAlign=top>
      <DIV align=right>
      <HR>

      <TABLE align=center border=0 class=ItemList height=51 width="100%">
        <TBODY>
        <TR class="tableHeader">
          <TD align=center class=ItemTitle height=14 width="5%">&nbsp;</TD>
          <TD align=center class=ItemTitle height=14 width="20%"><A 
            href="javascript:order(form1,1)">ԭʼ�ƻ�����</A></TD>
          <TD align=center class=ItemTitle height=14 width="10%"><A 
            href="javascript:order(form1,2)">�ſ�/����</A></TD>
          <TD align=center class=ItemTitle height=14 width="20%"><A 
            href="javascript:order(form1,3)">���</A></TD>
          <TD align=center class=ItemTitle height=14 width="10%"><A 
            href="javascript:order(form1,4)">����</A></TD>
          <TD align=center class=ItemTitle height=14 width="15%"><A 
            href="javascript:order(form1,5)">�ƻ�ִ������</A></TD>
          <TD align=center class=ItemTitle height=14 width="20%"><A 
            href="javascript:order(form1,6)">�޸�����</A></TD></TR>
       <% 
       		if ( (planInfo!=null)&&(planInfo.size()>0 ) )
       		{
       			long        ID=-1;
    			long        planID=-1;
    			Timestamp   planDate=null;
    			long        payTypeID=-1;
   			 	double      amount=0;
    			String      planType="";
    			Timestamp   modifyDate=null;
    			long        lastExtendID=-1;
    			long        lastOverdueID=-1;
    			long        lastVersionPlanID=-1;

         
         		int count=planInfo.size();
				for ( int i=0;i<count;i++ )
				{
					OBLoanPlanDetailInfo lpInfo=(OBLoanPlanDetailInfo)planInfo.get(i);
					ID=lpInfo.getID();
					planID=lpInfo.getPlanID();
					planDate=lpInfo.getPlanDate();
					payTypeID=lpInfo.getPayTypeID();
					amount=lpInfo.getAmount();
					planType=lpInfo.getType();
					modifyDate=lpInfo.getModifyDate();
		%>					            
        <TR align=center>
          <TD class=ItemBody height=23 width="5%"><INPUT name=txtlID<%=i%> type=checkbox value=<%=ID%>> </TD>
          <TD class=ItemBody height=23 width="20%">
          	<% if (isCheck) { %>
          	<%=DataFormat.formatDate(planDate)%>
          	<% }else{ %>
          	<A href="javascript:confirmModify(form1,<%=ID%>);" ><%=DataFormat.formatDate(planDate)%></A>
          	<% } %>
          	</TD>
          <TD class=ItemBody height=23 width="10%"><%=LOANConstant.PlanType.getName(payTypeID)%></TD>
          <TD class=ItemBody height=23 width="20%"><%=DataFormat.formatNumber(amount,2)%></TD>
          <TD class=ItemBody height=23 width="10%"><%=planType%></TD>
          <% if ( interestRate==0 ) { %>
          <TD class=ItemBody height=23 width="15%">0.00 %</TD>
          <% }else{ %>
          <TD class=ItemBody height=23 width="15%"><%=DataFormat.formatRate(interestRate)%> %</TD>
          <% } %>
          <TD class=ItemBody height=23 width="20%"><%=DataFormat.formatDate(modifyDate)%></TD>
        </TR>
        <%
        				
        		}//end for
        	} else {
        %>		
        <TR align=center>
          <TD class=ItemBody height=23 width="5%"></TD>
          <TD class=ItemBody height=23 width="20%">         	</TD>
          <TD class=ItemBody height=23 width="10%"></TD>
          <TD class=ItemBody height=23 width="20%"></TD>
          <TD class=ItemBody height=23 width="10%"></TD>
          <TD class=ItemBody height=23 width="15%"></TD>
          <TD class=ItemBody height=23 width="20%"></TD>
        </TR>
        
        <% } %>
        <TR>
          <TD colSpan=7 height=1>
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar height=37 width="100%">
              <TBODY>
              <TR>
                <TD height=2 width="50%"><DIV align=left>�ſ�ϼ�: <%=DataFormat.formatNumber(payAmount,2)%> ����ϼ�: <%=DataFormat.formatNumber(repayAmount,2)%> </DIV></TD>
                <TD height=2 width=0%>&nbsp;</TD>
                <TD height=2 width="50%">
                  <DIV align=right>
                        <P><B>�� 
                          <INPUT class=SearchBar_Page name=nPageNo onfocus="nextfield ='submitfunction';" size=3 value=<%=pageNo%>>
                          ҳ / �� <%=pageCount%> ҳ 
                          <INPUT class=SearchBar_Btn name=Submit822 onclick=frmSubmit(form1); type=button value=go>
                          
                          <INPUT name=hdnPageCount type=hidden value=<%=pageCount%> >
                          
                          <% if (pageNo>1) { %>
                          <input type="button" name="Submit4222" value="|&lt;" class="SearchBar_Btn" onclick="go(1);" >
                          <input type="button" name="Submit5222" value="&lt;" class="SearchBar_Btn" onclick="go(<%=(pageNo -1)%>);" >
                          <% }
                          	 if ( pageNo<pageCount ) {
                          %>	 
						  <input type="button" name="Submit6222" value="&gt;" class="SearchBar_Btn" onclick="go(<%=(pageNo+1)%>);" >
                          <input type="button" name="Submit7222" value="&gt;|" class="SearchBar_Btn" onclick="go(<%=pageCount%>);" >
                          <% } %>
                          </B></P>
                      </DIV>
               </TD>
               </TR>
               </TBODY>
            </TABLE>
         </TD>
        </TR>
      </TBODY>
     </TABLE>
     
      <DIV align=left><BR><B>&nbsp; <INPUT class=button name=Submit43 onclick="confirmDel(form1,'ɾ��ѡ��Ļ���ƻ�')" type=button value=" ɾ�� " <%if (isCheck) {%>disabled<%}%> > 
      </B><BR>
      <DIV align=right>
      <TABLE border=0 width="100%">
        <TBODY>
        <TR>
          <TD>
            <HR>
          </TD></TR></TBODY></TABLE>
            <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" �� �� "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" �޸Ĳ��ύ "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.ACCEPT) {    %>
					<INPUT class=button name="subnext"  onclick="finish()" type="button" value=" �޸Ĳ��ύ "> 									
			<%      }         %>				
			<% }else if ( action.equals("check") ){ %>
				<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� ">
			<% }else { %>
				<INPUT class=button name=Submit31 onclick="confirmSumit(form1,'ȷ��Ҫ������?')" type=button value="  �� ��  "> 
            <% } %>
          
      </DIV></DIV></DIV></TD></TR></TBODY></TABLE>
      
<INPUT name=nVersionID type=hidden value=-1> 
<INPUT name=nOrderParam type=hidden value=<%=orderParam%>> 
<INPUT name=nDesc type=hidden value=<%=lDesc%>> 
<INPUT name=sURL  type=hidden value=S15-1.jsp> 
<INPUT name=isSM type=hidden value=1> 
<input type=hidden name="lLoanID" value="<%=loanID%>">
<input type=hidden name="lClientID" value="<%=clientID%>">
<input type=hidden name="lLoanType" value="<%=loanType%>">
<input type=hidden name="sApplyCode" value="<%=applyCode%>">
<input type=hidden name="loanAmount" value="<%=loanAmount%>">
<INPUT name=lPlanVersion type=hidden value=<%=planVersion%> >
<INPUT name=lPlanDetailID type=hidden value="-1" > 
<INPUT name=lRsCount type=hidden value=<%=(planInfo==null)?0:planInfo.size()%> > 
<input type="hidden" name="txtAction" value="<%=action%>">        
</FORM>       

       
<SCRIPT language=javascript>
<%if (action.equals("modify") ) {%>
 firstFocus(form1.Submit13);
<%}%>
 //setSubmitFunction("confirmAuto('ȷ���Զ����żƻ�?')");
 setFormName("form1");
</SCRIPT>

<SCRIPT language=JavaScript>
function confirmClose()
{
	window.close();
}
function finish()
{
<% 	if ( payAmount!=loanAmount )
	{
		if ( payAmount!=repayAmount )
		{
%>		
			alert("��ʾ: �ſ�ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>�� �ſ��뻹��ϼ�������ȣ�");
<%		}else{   %>
			alert("��ʾ: �ſ�ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>�� ");
<% 		}
	}else if (payAmount!=repayAmount){
%>				
		alert("��ʾ: ����ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>���ſ��뻹��ϼ�������ȣ�");	
<%  }  %>		
	if (confirm("ȷʵҪ�޸Ĳ��ύô��"))
	{
		parent.opener.location.reload();
		window.close();
	}
	
}
function confirmAuto(msg)
{

	if (form1.txtlPayType.value != 8)
	{
			if (!checkDate(form1.txtdtInputDate11,1,"��ʼ�ſ����� "))
				return(false);
			if (!checkDate(form1.txtdtInputDate12,1,"��ֹ�ſ����� "))
				return(false);
			if (!CodeCompare(form1.txtdtInputDate11,form1.txtdtInputDate12,"��ʼ�ſ����ں���ֹ�ſ�����"))
				return(false);
	}
	
	if (form1.txtlRepayType.value != 8)
	{
			if (!checkDate(form1.txtdtInputDate21,1,"��ʼ�������� "))
				return(false);
			if (!checkDate(form1.txtdtInputDate22,1,"��ֹ�������� "))
				return(false);
			if (!CodeCompare(form1.txtdtInputDate21,form1.txtdtInputDate22,"��ʼ�������ں���ֹ��������"))
				return(false);
	}
	if (form1.txtlPayType.value != 8  &&  form1.txtlRepayType.value != 8)
	{
			if (!CodeCompare(form1.txtdtInputDate11,form1.txtdtInputDate21,"��ʼ�ſ����ں���ʼ��������"))
				return(false);
			if (!CodeCompare(form1.txtdtInputDate12,form1.txtdtInputDate22,"��ֹ�ſ����ں���ֹ��������"))
				return(false);
	}

	if (confirm(msg))
	{
		form1.action="l022-c.jsp";
		showSending();
		form1.submit();
	}
}
function addPlan()
{
	form1.action="l023-v.jsp";
	showSending();
	form1.submit();
}
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if ( !CompareDateString(d_input1.value,d_input2.value))
		{
			alert(d_str+"�����ɴ���С��");
			form1.Submit12.focus();
			return (false);
		}
	}
	return true;
}

function confirmDel(form,msg)
{

	var j=0;
	var i=0;
	var count=<%=(planInfo==null)?0:planInfo.size()%>;
  	for ( i=0;i<count;i++ )
  	{
		if(eval("form.txtlID"+i+".checked"))
     	    j=j+1;
    }
     	    
	if (j==0)
  	{
  		alert("��ѡ��ɾ������");
  		return false;
  	}


	if (confirm(msg))
	{
		form.action="l025-c.jsp";
		showSending();
		form.submit();
	}
	
}

function confirmBack(form)
{
<% if ( (isAssure==1)||(isPledge==1)||(isImpawn==1) ) { %>
	form.action="l016-c.jsp";
<% }else{ %>
	form.action="l012-c.jsp";
<%  } %>
	showSending();
	form.submit();
}

function confirmOK(form,msg)
{
	if (form.nVersionID.value<2)
	{
		alert("�Բ����㻹û������ԭʼ�ƻ���");
		return(false);
	}

	if (confirm(msg))
	{
		form.txtAction.value = "finish";
		showSending();
		form.submit();
	}
}
function confirmSumit(form,msg)
{
<% 	if ( payAmount!=loanAmount )
	{
		if ( payAmount!=repayAmount )
		{
%>		
			alert("��ʾ: �ſ�ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>�� �ſ��뻹��ϼ�������ȣ�");
<%		}else{   %>
			alert("��ʾ: �ſ�ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>�� ");
<% 		}
	}else if (payAmount!=repayAmount){
%>				
		alert("��ʾ: ����ϼ���������������:��<%=DataFormat.formatAmount(loanAmount)%>���ſ��뻹��ϼ�������ȣ�");	
<%  }  %>		
	if (confirm(msg))
	{
	
		form.action="l028-c.jsp";
		showSending();
		form.submit();
	}
}
function frmSubmit(form)
{   
    var lMax;
	lMax = parseInt(form.hdnPageCount.value);
	if (InputValid(form.nPageNo, 1, "int", 1, 1, lMax,"ҳ��")) 
	{
		showSending();
		form.action="l020-c.jsp";
		form.submit();
	}
}

function go(i)
{
	var j=0;
	if ( i<1 )
	{
		j=1;
	}else if ( i><%=pageCount%> ){
		j=<%=pageCount%>
	}else{
		j=i;
	}
		
	form1.nPageNo.value=j;
	frmSubmit(form1);
}

function order(form,i)
{
    form.nOrderParam.value=i;
    if ( form.nDesc.value=="1" )
    	form.nDesc.value="2";
    else
    	form.nDesc.value=1;
    	
    form.action="l020-c.jsp";
	showSending();
	form.submit();
}

function confirmModify(form,id)
{
	form.lPlanDetailID.value=id;
	form.action="l027-c.jsp";
	showSending();
	form.submit();
}
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