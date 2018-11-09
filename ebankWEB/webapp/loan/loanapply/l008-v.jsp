<!--  
/**
 * ҳ������ :l008-v.jsp
 * ҳ�湦�� : ������������
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 * ����˵�� ��
 *			  
 * �޸���ʷ �� 2007-03-15 mzh_fu ������":"�ĳ�"��"
 */
-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.bizlogic.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obloanapply.dataentity.*,
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
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};

    	boolean iswt=false;
    	boolean isyt=false;
    	boolean isCheck=false;
    	String action="";
    	long statusID=-1;
    	String tmp="";
		String detailStr="�������ʽ";
    
		OBLoanApplyInfo lInfo= (OBLoanApplyInfo) request.getAttribute("LoanApplyInfo");
		Vector assVector=(Vector)lInfo.getAssureInfo();

		long wtClientID=lInfo.getConsignClientID();			//ί�пͻ�
		String loanCode=lInfo.getApplyCode();				//�����
		long loanID=lInfo.getID();							//����ID
		long clientID=lInfo.getBorrowClientID();			//�ͻ���
		//String applyCode=lInfo.getApplyCode();
		String applyCode=lInfo.getInstructionNo();
		long isCircle=lInfo.getIsCircle();					//�Ƿ�ѭ��
		long isSaleBuy=lInfo.getIsSaleBuy();				//�Ƿ���������
		long isTechnical=lInfo.getIsTechnical();			//�Ƿ񼼸Ĵ���
		long isCredit=lInfo.getIsCredit();					//�Ƿ����ñ�֤
		long isAssure=lInfo.getIsAssure();					 //�Ƿ񵣱�
		long isImpawn=lInfo.getIsImpawn();					//�Ƿ���Ѻ
		long isPledge=lInfo.getIsPledge();					//�Ƿ��Ѻ
		long isRecognizance = lInfo.getIsRecognizance();	//�Ƿ�֤��
	
		boolean hasAssure=false;
		boolean hasPledge=false;
		boolean hasImpawn=false;
		boolean hasRecognizance=false;
      
	    tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		action=tmp;
		tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
    		statusID=Long.valueOf(tmp).longValue(); 
    	
    	if ( (assVector!=null )	&& (assVector.size()>0) )
    	{
    		int count=assVector.size();
    		long assTypeID=-1;
    		for ( int i=0;i<count;i++ )
    		{
    			OBAssureInfo assInfo=(OBAssureInfo)assVector.get(i);
    			assTypeID=assInfo.getAssureTypeID();
    			if ( assTypeID==LOANConstant.AssureType.ASSURE) hasAssure=true;
          		if ( assTypeID==LOANConstant.AssureType.PLEDGE) hasPledge=true;
          		if ( assTypeID==LOANConstant.AssureType.IMPAWN) hasImpawn=true;
				if ( assTypeID==LOANConstant.AssureType.RECOGNIZANCE) hasRecognizance=true;
    		}
    	}
        //��ʾ�ļ�ͷ
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[���������������]",Constant.YesOrNo.NO);
        else
        	OBHtml.showOBHomeHead(out,sessionMng,"[���������������]",Constant.YesOrNo.YES);
        if ( action.equals("check") )
       	isCheck=true;		
        
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=330 width="49%">
  <TBODY>
  <TR class="tableHeader">
      <TD class=FormTitle height=29><B><%=loanTypeName%>��������</B></TD>
    </TR>
  <TR>
    <TD>
      <TABLE cellPadding=0 height=50 width=730>
      <TR style="HEIGHT: 21pt">
	  <TD height=11 width=8>&nbsp;</TD>
            <TD height=56 colspan=2 nowrap ><P class=MsoNormal>����ָ���ţ�<%=applyCode%></P> </TD>
      <TD height=28  nowrap align="right">�����������ƣ�<%LOANConstant.SubLoanType.showList(out,"subLoanType",lInfo.getSubTypeId(),false,true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%></TD>
	  <TD height=46 width=80>&nbsp; </TD>
		    
	</tr>
	<%if(loanType!=OBConstant.LoanInstrType.ASSURE){%>
        <TR >
            <TD height=11 width=8>&nbsp;</TD>
            <TD colSpan=8 vAlign=top><HR align=center SIZE=2 width="100%"> <P><font color='#FF0000'>* </font><U>��������</U></P></TD>
        </TR>
        <TR style="HEIGHT: 21.75pt">
            <TD height=46 width=8>&nbsp; </TD>
            <TD height=46 colspan=8>
            	<table cellPadding=0 height=50 width=100% >
            	<tr>
            	<td width=16%>ѭ�����</TD>
            	<TD height=46 width=16%>
              		<select class=box name="iscircle" onfocus="nextfield='issalebuy';" <%if (isCheck) {%>disabled<%}%> >
					<option value="-1" ></option>
					<option value="1" <% if (isCircle==1) {%> selected <% } %> >��</option>
					<option value="2" <% if (isCircle!=1) {%> selected <% } %> >��</option>
			  		</select>
	            </TD>
            	<TD height=46 width=16%>���������</TD>
            	<TD height=46 width=16%>
              		<select class=box name="issalebuy"  onfocus="nextfield='istechnical';" <%if (isCheck) {%>disabled<%}%>>
					<option value="-1" ></option>
					<option value="1" <% if (isSaleBuy==1) {%> selected <% } %> >��</option>
					<option value="2" <% if (isSaleBuy==2) {%> selected <% } %> >��</option>
					<option value="3" <% if ( (isSaleBuy!=1)&&(isSaleBuy!=2)) {%> selected <% } %> >������</option>
					</select>
		  		</TD>
            	<TD height=46 width=16%>���Ĵ��</TD>
            	<TD height=46 width=20%>
            		<select class=box name="istechnical"  onfocus="nextfield='iscredit';" <%if (isCheck) {%>disabled<%}%>>
                	<option value="-1" > </option>
                	<option value="1" <% if (isTechnical==1) {%> selected <% } %> >��</option>
                	<option value="2" <% if (isTechnical!=1) {%> selected <% } %> >��</option>
              		</select>
              	</TD>
              	</tr>
              	</table>
            </TD>
          </TR>
		  <%}%>
        <TR >
		<%
		String sss="isassure";
		if(loanType==OBConstant.LoanInstrType.ASSURE){
				 sss="isRecognizance";
				 detailStr="��������ʽ";
		}
		%>
            <TD height=100 width=8> 
              <P class=MsoNormal>&nbsp;</P></TD>
          <TD colSpan=8 height=100 vAlign=top align=center>
            <HR align=center SIZE=2 width="100%">
            <P align=left><font color='#FF0000'>* </font><U><%=detailStr%></U></P>
            <TABLE border=0 width="100%">
              <TR>
                <TD height=17 width="6%" align=center> <INPUT name="iscredit" type=checkbox  onfocus="nextfield='<%=sss%>';" value="1" <% if (isCredit>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> > </TD>
                <TD height=17 width="15%">����</TD>
				<%if(loanType==OBConstant.LoanInstrType.ASSURE){%>
                  <TD height=17 width="5%" align=center> 
                    <INPUT name="isRecognizance" type=checkbox onfocus="nextfield='isassure';" value="1"  <% if (isRecognizance>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> >
                  </TD>
				  <TD height=17 width="15%">��֤��</TD>
				  <%}%>
                <TD height=17 width="6%" align=center> <INPUT name="isassure" type=checkbox onfocus="nextfield='isimpawn';" value="1"  <% if (isAssure>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
				<TD height=17 width="15%">��֤</TD>
                <TD height=17 width="6%"> <INPUT name="isimpawn" type=checkbox  onfocus="nextfield='ispledge';" value="1"  <% if (isImpawn>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
                <TD height=17 width="15%">��Ѻ</TD>
                <TD height=17 width="6%"> <INPUT name="ispledge" type=checkbox onfocus="nextfield='xyb';" value="1"  <% if (isPledge>0) {%> checked <% } %> <%if (isCheck) {%>disabled<%}%> ></TD>
                <TD height=17 width="15%">��Ѻ</TD>
              </TR>
            </TABLE>
            <HR align=center SIZE=2 width="100%">
            </TD>
          </TR>
			
        <TR> 
            <TD align=left height="39"></td>     
            <TD height=39 vAlign=middle>����������</TD>
            <TD align=left colspan=7 height="39"></td>
		</tr>
		<tr>
			<TD align=left height="39"></td>     
			<td colspan=8>
				<iframe src="../../attach/AttachFrame.jsp?lID=<%=loanID%>&lTypeID=<%=LOANConstant.AttachParentType.LOAN%>&sCaption=<%=URLEncoder.encode("���Ӹ���")%>&showOnly=<%=isCheck%>" width=723 height="100" scrolling="Auto" frameborder="0" name="iFrame" >
				</iframe>
			</TD>
		</TR>
        <TR >
            <TD height=37 width=8>&nbsp;</TD>
          <TD colSpan=8 height=37 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD>
        </TR>
        <TR style="HEIGHT: 31.5pt">
          <TD height=37 width=8> <P class=MsoNormal>&nbsp;</P></TD>
          <TD colSpan=8 height=37 vAlign=top>
            <P align=right class=MsoNormal >
            <% if (action.equals("modify")) {%>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �� �� "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SAVE) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" �޸Ĳ��ύ "> 				
			<%      }         %>
			<% }else if ( action.equals("check") ){ %>
					<INPUT class=button name="xybClose" onclick="confirmClose()" type="button" value=" �� �� "> 				
			<% }else { %>
            <INPUT class=button name=Submit42322222 onclick="backto()" type="button" value=" ��һ�� "> 
            <INPUT class=button name="xyb"  onclick="confirmSave(frm)"  onfocus="nextfield='submitfunction';"  type=button value=" ��һ�� "> 
            <% } %>
			
            </P>
          </TD>
        </TR>
      </TABLE>
     </TD>
    </TR>
   </TABLE>
<P><FONT size=5></FONT></P>
<input type="hidden" name="sApplyCode" value="<%=applyCode%>">
<input type="hidden" name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="lClientID" value="<%=clientID%>">
<input type=hidden name="lLoanType" value="<%=type%>">
<input type="hidden" name="wtClientID" value="<%=wtClientID%>">
<input type="hidden" name="txtAction" value="<%=action%>">

</form>
<script language="JavaScript">
function confirmClose()
{
	window.close();
}
function backto()
{
   frm.action="l011-c.jsp";
   showSending();
frm.submit();


}
function  confirmSave(frm)
	{
<%if(loanType!=OBConstant.LoanInstrType.ASSURE){%>
	if(frm.iscircle.value==-1)
	{
	  alert("��ѡ���Ƿ�Ϊѭ�����ʽ");
	  frm.iscircle.focus();
	  return false;
	}
	
	if(frm.issalebuy.value==-1)
	{
	  alert("��ѡ�����������ʽ");
	  frm.issalebuy.focus();
	  return false;
	}
	
	if(frm.istechnical.value==-1)
	{
	  alert("��ѡ�񼼸Ĵ��ʽ");
	  frm.istechnical.focus();
	  return false;
	}
	if ((!frm.iscredit.checked)&&(!frm.ispledge.checked)&&(!frm.isimpawn.checked)&&(!frm.isassure.checked))
	  {
        alert("��ѡ����֤��ʽ��"); 
		frm.iscredit.focus();
		return false;
	  }
	<%}%>
<%if(loanType==OBConstant.LoanInstrType.ASSURE){%>
	if ((!frm.isRecognizance.checked)&&(!frm.iscredit.checked)&&(!frm.ispledge.checked)&&(!frm.isimpawn.checked)&&(!frm.isassure.checked)){
			alert("��ѡ����֤��ʽ��"); 
			frm.iscredit.focus();
			return false;
		  }
<%  if ( (isRecognizance==1)&&(hasRecognizance) ){ %>
	if ( !frm.isRecognizance.checked )
	{
		alert("�Ѿ��е������¼��Ϣ������ȡ����");	
		return false;
	}
<%  }	
}%>

    
	
<%  if ( (isAssure==1)&&(hasAssure) ){ %>
	if ( !frm.isassure.checked )
	{
		alert("�Ѿ��е�����¼��Ϣ������ȡ����");	
		return false;
	}
<%  }
	if ( (isImpawn==1)&&(hasImpawn) ){ 
%>
	if ( !frm.isimpawn.checked )
	{
		alert("�Ѿ�����Ѻ������¼��Ϣ������ȡ����");	
		return false;
	}
<%  }
	if ( (isPledge==1)&&(hasPledge) ){
%>
	if ( !frm.ispledge.checked )
	{
		alert("�Ѿ��е�Ѻ������¼��Ϣ������ȡ����");	
		return false;
	}
<% } %>		
        frm.action="l009-c.jsp";
		showSending();
		frm.submit();
	}
<%if (action.equals("modify") ) {%>
firstFocus(frm.iscircle);
<%}%>
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