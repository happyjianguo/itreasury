 <%/**
			 * ҳ������ ��v003.jsp
			 * ҳ�湦�� : ֪ͨ���֧ȡ֪ͨ�޸�
			 * ��    �� ��YuanHuang
			 * ��    �� ��2006-10-31
			 * ����˵�� ��֪ͨ���֧ȡ֪ͨ��ʾ
			 *			  
			 * �޸���ʷ ��
			 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.ebank.util.OBHtml,
				com.iss.itreasury.ebank.util.OBConstant
				"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
  <% 
  long lShowMenu = OBConstant.ShowMenu.YES;
  String strTitle = "֪ͨ���֧ȡ֪ͨ�޸�";
    try 
	{
        //�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
        /* ��ʾ�ļ�ͷ */
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
    System.out.println("------------֪ͨ���֧ȡ֪ͨ�޸�/v003.jsp-----------------------------");
 

 
 		//�������	
			long id = -1;// idֵ
			String clientCode = ""; //�ͻ����
			String clientName = "" ;//�ͻ�����
			String saccountno = "" ;// �˺�
			String DepositNo="";//֪ͨ���ݺ�
			String startdate = "";//��ʼ��
			double mAmount = 0;//����
			double balance = 0;//���	
			double amount=0;//֧ȡ���
			String notifyDate="";//֪ͨ����
			
			NotifyDepositInformInfo info = (NotifyDepositInformInfo) request.getAttribute("info");
			if(info!=null)
			{
				String strTemp = null;
				double douTemp = 0.00;
				id= info.getID();
				strTemp = info.getClientCode();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					clientCode = strTemp;
				}
				strTemp = info.getClientName();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					clientName = strTemp;
				}
				strTemp = info.getSaccountno();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					saccountno = strTemp;
				}
				strTemp = info.getDepositNo();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					DepositNo = strTemp;
				}
				strTemp = info.getStartdate();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					startdate = strTemp;
				}
				douTemp = info.getmAmount();
				if( douTemp >0)
				{
					mAmount = douTemp;
				}
				douTemp = info.getBalance();
				if( douTemp >0)
				{
					balance = douTemp;
				}
				douTemp = info.getAmount();
				if( douTemp >0)
				{
					amount = douTemp;
				}
				strTemp = info.getNotifyDate();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					notifyDate = strTemp;
				}
			}						
			%>
<form name="frmV003">
<input type="hidden" value="<%=id %>" name="id">

<table width="98%" cellpadding="0" cellspacing="0" class="title_top" >
	  <tr>
	    <td height="22" >
		    <table width="100%" cellspacing="0" cellpadding="0" class=title_Top1 >
				<TR>
			       <td class=title><span class="txt_til2" style="width:150px">֪ͨ���֧ȡ֪ͨ�޸�</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
		</table>	
	    
	<br/>
	<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
 		<tr><td></td></tr>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
       		<TD>֪ͨ���ݺţ�</TD>
      		<TD align="left">
       			<INPUT class="box" type='text' name='DepositNo' disabled value="<%= DepositNo %>">
       		</TD>
			<input type="hidden" name= "DepositNo1" value="<%= DepositNo %>">  			
     		<TD>֪ͨ�����ʼ�գ�</TD>
       		<TD>
        		<INPUT class=box type="text" disabled name="strStartDate" value="<%=startdate%>">
			</TD>
		</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD>֪ͨ����</TD>
			<TD align="left">
				<fs:amount 
		       		form="frmV003"
	       			name="dAmount"
	       			readonly="true"
	       			value="<%=mAmount %>"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
			</TD>
			<TD>֪ͨ�����</TD>
			<TD>
				<fs:amount 
		       		form="frmV003"
	       			name="dDepositBalance"
	       			readonly="true"
	       			value="<%=balance %>"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
   			</TD>			
 		</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD>֧ȡ��</TD>			
			<TD>
				<fs:amount 
		       		form="frmV003"
	       			name="dDrawAmount"
	       			value="<%=amount %>"
	       			nextFocus="notifyDate"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
			</TD>
			<!-- Added by zwsun 2007-6-19 ����֪ͨ���� -->
			<!-- begin -->
			<td>
				֪ͨ���ڣ�
			</td>
			<td>
				<fs:calendar 
	          	    name="notifyDate"
		          	value="" 
		          	properties=""
		          	size="20"/>
		        <script>
	          		$('#notifyDate').val('<%= notifyDate %>');
	          	</script>
			</td>
			<!-- end -->						
		</TR>
		<tr><td>&nbsp;</td></tr>
		<TR vAlign=center>
			<TD colSpan=6 height=2>
			<DIV align=right></DIV>
			<DIV align=right>						
			 <input class="button1" name="next" type="button" value=" �� �� " onClick="doNext();"  onKeydown="if(event.keyCode==13) {if(confirm('�Ƿ񱣴棿')) doNext();}"> 
			 <input class="button1" name="Submit32" type="button" value=" �� �� " onClick="window.location.href='../control/c002.jsp';"></DIV>
			</TD>
			<td>&nbsp;&nbsp;</td>
		</TR>
		<tr><td>&nbsp;</td></tr>
	</TABLE>
	
	</td>
	</tr>
	</table>
</form>
<script>
setFormName("frmV003");
firstFocus(frmV003.dDrawAmount);

function doNext()
{
	if(!check()) return false;
	if(confirm('�Ƿ񱣴棿')) 
	{
		frmV003.action = "../control/c005.jsp";
		showSending();
		frmV003.submit();
	}
}

function check()
{
	if(!validate()) return false;
	//֧ȡ������
	if(Trim(frmV003.dDrawAmount.value)=="")
	{
		alert("֧ȡ������Ϊ�ա�");
		document.frmV003.dDrawAmount.focus();
		return false;
	}	
	if(checkAmount(frmV003.dDrawAmount,1,"֧ȡ���") == false)
	{
		return false;
	}
	//Added by zwsun , 2007-06-18, ��������У��
	if(checkDate(frmV003.notifyDate,1,"֪ͨ����") == false){
		return false;
	}
	return true;
}

	function validate()
	{			
		if(parseFloat(reverseFormatAmount(document.frmV003.dDepositBalance.value))-parseFloat(reverseFormatAmount(document.frmV003.dDrawAmount.value))<0)
		{	
			alert("֧ȡ���ܴ��ڴ浥�����������룡");
			document.frmV003.dDrawAmount.focus();
			bResult = false;
			return false;
		}
	
		return true;
	}
</script>
 
 
 
 <%    
        
	OBHtml.showOBHomeEnd(out);	
}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
  
  %>
<safety:resources />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/SignValidate.inc"%>