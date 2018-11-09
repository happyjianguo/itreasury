
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>	
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QuerySubFixedInfo" %>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QSubAccountDao" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<% String strContext = request.getContextPath();%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<safety:resources />

    <%
	Log.print("\n\n*******����ҳ��--settlement/query/view/v031.jsp*******\n\n");
	String strTitle = "���ڴ浥��Ϣ";
	
    try
    {
		Log.print("\n\n*******����ҳ��--capital/query/q101-v.jsp*******\n\n");
		
		/* ʵ�ֲ˵����� */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
		    lShowMenu = OBConstant.ShowMenu.NO;
		}
		

        
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
	%>

    <%
		
		//ҳ����Ʊ���
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;

		//�������
		long ID=0;  //�����˺�
		String AF_sDepositNo=""; //���ڴ��ݺ�
		Timestamp AF_dtStart=null;//��ʼ����
		long AF_nDepositTerm=0;//���ڴ������
		Timestamp AF_dtEnd=null;//��������
		double AF_mRate=0.0;//����
		double mOpenAmount=0.0;//�浥���
		double mBalance=0.0;//��ǰ���
		long nStatusID=0;//���ڴ��״̬
		Timestamp dtOpen=null;//��������
		Timestamp dtFinish=null;//�廧����
		double AF_mPreDrawInterest=0.0;	//�Ѽ�����Ϣ

		//ȡ�ô������Ķ���
		QuerySubFixedInfo info = (QuerySubFixedInfo)request.getAttribute("searchResults");
		if(info!=null)
		{			
            ID=info.getID();
			
			AF_sDepositNo=info.getAF_sDepositNo();
			AF_dtStart=info.getAF_dtStart();
			AF_nDepositTerm=info.getAF_nDepositTerm();
			AF_dtEnd=info.getAF_dtEnd();
			AF_mRate=info.getAF_mRate();
			mOpenAmount=info.getMOpenAmount();
			mBalance=info.getMBalance();
			nStatusID=info.getNStatusID();
			dtOpen=info.getDtOpen();
			dtFinish=info.getDtFinish();
			AF_mPreDrawInterest=info.getAF_mPreDrawInterest();
		}
    %>

<form name=fm >	
<!--�������ؿؼ���������ҳ��-->
<input type="hidden" name="strSuccessPageURL" value="../view/v005.jsp">
<input type="hidden" name="strFailPageURL" value="../view/v008.jsp">	
<input name="strAction" type="hidden" value="">
<TABLE border=0 class="title_top" height=300 width="99%">
  <TBODY>
  <TR>
    <TD  width="100%">
	    <table  border="0" cellspacing="0" cellpadding="0" class=title_Top1>
	      <tr>
	        <td width="124" background="/webob/graphics/new_til_bg.gif" class=title>��<span class="txt_til2"><%=strTitle %></span></td>
	      </tr>
	    </table>
    <BR>
      <TABLE align=center border=0 width="100%" class="normal">
        <TBODY>
        <TR >
          <TD height=20 width="16%" nowrap>���ڿͻ���ţ�</TD>
		  <td width=30%>
				<input class=box name="txtFixedClientID" value="<%=NameRef.getClientCodeByAccountID(info.getAccountID())%>"  disabled>
		  </td>
          <TD height=20 width="16%" nowrap>���ڿͻ����ƣ�</TD>
          <TD height=20 width="39%">
			<INPUT class=box  name=txtFixedClientName size=30 value="<%=NameRef.getClientNameByAccountID(info.getAccountID())%>" disabled> 
	      </TD>
	    </TR>
     
	    <TR >
          <TD height=20 nowrap>�����˺ţ�</TD>
		  <td >
			<input class=box name="txtFixedAccountID" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>"  disabled>
		  </td>
		  <td height="20" bordercolor="#E8E8E8">&nbsp;</td>
          <td height="20" bordercolor="#E8E8E8">&nbsp;</td>
        <TR >
          <TD height=20 nowrap>���ڴ��ݺţ� </TD>
          <TD height=20 ><INPUT class=box name="txtFixedDepositBillNo" value="<%=(AF_sDepositNo==null || AF_sDepositNo.equals(""))?"&nbsp;":AF_sDepositNo%>" maxlength=50 onfocus="nextfield ='txtDateFixedStart';" disabled>  </TD>
          <TD height=20 ></TD>
          <TD height=20 ></TD>
		</TR>
        <TR >
          <TD height=20 nowrap>��ʼ���ڣ�</TD>
          <TD height=20 >
			<INPUT class=box name="txtDateFixedStart" size=18 onBlur="dateChange(fm)" value="<%=DataFormat.formatDate(AF_dtStart)%>" maxlength=20 onfocus="nextfield ='slcFixedInterval';"  disabled>  
		  </TD>
          <TD height=20 >&nbsp;</TD>
          <TD height=20 >&nbsp;</TD>
		</TR>
        <TR >
          <TD height=20 nowrap>���ڴ�����ޣ�</TD>
          <TD height=20 >
			 <%				
				SETTHTML.showFixedDepositMonthListCtrl(out,"slcFixedInterval",SETTConstant.TransQueryType.FIXED,AF_nDepositTerm,true,"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			 %>
			 <script language="JavaScript">
				 document.fm.slcFixedInterval.disabled=true;
			 </script>
			
		  </TD>
          <TD height=20 nowrap>�������ڣ�</TD>
          <TD height=20 >
			<INPUT class=box disabled 
            name=txtDateFixedEndCtrl size=18 value="<%=DataFormat.formatDate(AF_dtEnd)%>"> <input type="hidden" name="txtDateFixedEnd">
		  </TD>
		</TR>
		<TR >	
		  <TD height=29 nowrap>���ʣ�</TD>
		  <TD height=29 >
			<input class=box name="txtInterestRate" value="<%=(AF_mRate>=0.00?DataFormat.formatRate(AF_mRate):"0.00")%>" onfocus="nextfield='rdoSource(0)'"  disabled>%
		  </TD>
		  <TD height=29 ></TD>
		  <TD height=29 >&nbsp;</TD>
		</TR>
					<TR vAlign=center>
						<TD height=20 width="16%">�浥��<%=sessionMng.m_strCurrencySymbol%></TD>
					  	<TD height=20 width="30%"> 
							<input class=box name="txtMoney" value="<%=DataFormat.formatDisabledAmount(mOpenAmount)%>" onfocus="nextfield='rdoSource(0)'"  disabled>
					  	</TD>
					  	<TD height=20 width="16%" nowrap>��ǰ��<%=sessionMng.m_strCurrencySymbol%></TD>
					  	<TD height=20 width="38%"> 					  
					    	<input class=box name="txtMoney" value="<%=DataFormat.formatDisabledAmount(mBalance)%>" onfocus="nextfield='rdoSource(0)'"  disabled>
					  	</TD>
				   	</TR>
				   	<TR vAlign=center>
					  	<TD height=20 nowrap>���ڴ��״̬��</TD>
					  	<TD height=20>
							<INPUT class=box disabled name="txtDateInterestStartCtrl" value="<%=SETTConstant.SubAccountStatus.getName(nStatusID)%>"> 
					  	</TD>
					  	<TD align=left height=20 vAlign=center >�Ѽ�����Ϣ��<%=sessionMng.m_strCurrencySymbol%> </TD>
           				<TD align=left height=20 vAlign=center >
							<INPUT class=box disabled name=textfield235 value="<%=DataFormat.formatDisabledAmount(AF_mPreDrawInterest)%>"> 
		   				</TD>
					</TR>
         			<TR vAlign=center>
         				<TD height=20>�������ڣ�</TD>					  
			  			<TD height=20>
			   				<INPUT class=box disabled name="txtDateInterestStartCtrl" value="<%=DataFormat.formatDate(dtOpen)%>"> </TD>
			  			<TD height=20>�廧���ڣ� </TD>
			  			<TD height=20><INPUT class=box name=txtDateInterestExecute value="<%=DataFormat.formatDate(dtFinish)%>" maxlength=20 onfocus="nextfield ='txtNote';"  disabled>
			  			</TD>
					</TR>
			        <TR vAlign=center>
			          	<TD colSpan=6 height=2>
		            		<DIV align=right>
						<%--   <INPUT class=button name=Submit222 onClick="viewTransDetail();" type=button value=" ������ʷ��ϸ " >  --%>
						  	<INPUT class=button name=Submit43 onclick="Javascript:window.close();" type=button value=" �� �� "> 
			            	</DIV>
					  	</TD>
					</TR>
</TBODY></TABLE>
</TD></TR>
</TBODY></TABLE>
</form>	

<form name="frmToTransDetail" method="post" target="_blank">
<input type="hidden" name="strSuccessPageURL" value="/settlement/query/view/v031-1.jsp">
<input type="hidden" name="strFailPageURL" value="/settlement/query/view/v031.jsp">

<input type="hidden" name="lPayAccountIDEndCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lPayAccountIDStartCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lReceiveAccountIDEndCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="lReceiveAccountIDStartCtrl" value="<%=NameRef.getAccountNoByID(info.getAccountID())%>">
<input type="hidden" name="strDepositNo" value="<%=AF_sDepositNo%>">
</form>
<script language="JavaScript">
function viewTransDetail()
{
	frmToTransDetail.action = "../control/c032.jsp";
	//showSending();
	frmToTransDetail.submit();
}
</script>

<%
OBHtml.showOBHomeEnd(out);
}
catch( Exception exp ){
    //OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
<%@ include file="/common/SignValidate.inc" %>