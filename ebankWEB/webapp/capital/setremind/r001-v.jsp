<%--
/*
 * �������ƣ�r001-v.jsp
 * ����˵������������--�ÿ�ƻ����� 
 * �������ߣ�����
 * ������ڣ�2007/06/17
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ page import="com.iss.itreasury.ebank.obsetremind.dataentity.OB_OperationReminderInfo"%>
<%@ page import="com.iss.itreasury.ebank.obsetremind.bizlogic.OB_OperationReminderBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%!
	/* ����̶����� */
	String strTitle = "ҵ����������";
%>

<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
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
 %>

<%
    /* ҵ���߼� */
    long lOfficeId = sessionMng.m_lOfficeID;
    long lCurrencyId = sessionMng.m_lCurrencyID;
    long lClientId = sessionMng.m_lClientID;
    long lIdOpenFixDeposit = OBConstant.OperationReminderType.OPENFIXDEPOSIT;
	long lFateOpenFixDeposit = -1;
	long lIdNotifyDepositDraw = OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW;
	long lFateNotifyDepositDraw = -1;
	long lIdCapTransfer = OBConstant.OperationReminderType.CAPTRANSFER;
	long lFateCapTransfer = -1;
	long lIdUnOpenFixDeposit = OBConstant.OperationReminderType.UNOPENFIXDEPOSIT;
	long lFateUnOpenFixDeposit = -1;
	long lIdUnNotifyDepositDraw = OBConstant.OperationReminderType.UNNOTIFYDEPOSITDRAW;
	long lFateUnNotifyDepositDraw = -1;
	
	OB_OperationReminderBiz biz = new OB_OperationReminderBiz();
	OB_OperationReminderInfo queryInfo = null;
	OB_OperationReminderInfo info = null;
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdOpenFixDeposit);
	info = biz.findOperationReminder(queryInfo);
	if(info != null) {
		lFateOpenFixDeposit = info.getOperationFate();
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdNotifyDepositDraw);
	info = biz.findOperationReminder(queryInfo);
	if(info != null) {
		lFateNotifyDepositDraw = info.getOperationFate();
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdCapTransfer);
	info = biz.findOperationReminder(queryInfo);
	if(info != null) {
		lFateCapTransfer = info.getOperationFate();
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdUnOpenFixDeposit);
	info = biz.findOperationReminder(queryInfo);
	if(info != null) {
		lFateUnOpenFixDeposit = info.getOperationFate();
	}
	
	queryInfo = new OB_OperationReminderInfo();
	queryInfo.setOfficeId(lOfficeId);
	queryInfo.setCurrencyId(lCurrencyId);
	queryInfo.setClientId(lClientId);
	queryInfo.setOperationId(lIdUnNotifyDepositDraw);
	info = biz.findOperationReminder(queryInfo);
	if(info != null) {
		lFateUnNotifyDepositDraw = info.getOperationFate();
	}
%>

<form method="post" name="frmOperationReminder" action="r001-c.jsp">
<input type="hidden" name="strAction"> 
	    
	    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%=strTitle %></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	    
    
<br/>
<table width="150" border="0" cellspacing="0" cellpadding="0" align="">
  <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> ����ҵ������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	 
	 <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
		
	    <tr  class="MsoNormal">
          <td colspan="4" height="25"  class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal" >
		  <td width="45%" height="25" class="MsoNormal" align="right">
		  	<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.OPENFIXDEPOSIT)%>������ǰ
		  </td>
		  <td width="3%" height="25"  class="MsoNormal">&nbsp;</td>
          <td width="12%" height="25"  class="MsoNormal" align="left">
          	<input type="hidden" name="Id_OpenFixDeposit" value="<%=OBConstant.OperationReminderType.OPENFIXDEPOSIT%>" />
            <input type="text" class="box" name="Fate_OpenFixDeposit" maxlength="4" id="Fate_OpenFixDeposit" value="<%if(lFateOpenFixDeposit != -1) {out.print(lFateOpenFixDeposit);}%>" size="8" />
          </td>
          <td width="40%" height="25"  class="MsoNormal" align="left">������</td>
        </tr>
        
        <tr class="MsoNormal" >
		  <td width="45%" height="25" class="MsoNormal" align="right">
			<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW)%>������ǰ
	      </td>
	      <td width="3%" height="25"  class="MsoNormal">&nbsp;</td>
          <td width="12%" height="25"  class="MsoNormal" align="left">
          	<input type="hidden" name="Id_NotifyDepositDraw" value="<%=OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW%>" />
            <input type="text" class="box" name="Fate_NotifyDepositDraw" maxlength="4" id="Fate_NotifyDepositDraw" value="<%if(lFateNotifyDepositDraw != -1) {out.print(lFateNotifyDepositDraw);}%>" size="8" />
          </td>
          <td width="40%" height="25"  class="MsoNormal" align="left">������</td>
        </tr>

        
		<!--a try for glass start-->
		<tr  class="MsoNormal">
			<td width="5" height="25"  class="MsoNormal" colspan="4"></td>
		</tr>
      </table>
<br/>
<table width="150" border="0" cellspacing="0" cellpadding="0" align="">
  <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">����ҵ������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	 
	 <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
		
	    <tr  class="MsoNormal">
          <td colspan="4" height="25"  class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal" >
		  <td width="45%" height="25" class="MsoNormal" align="right">
		  	<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.UNOPENFIXDEPOSIT)%>��
		  </td>
		  <td width="3%" height="25"  class="MsoNormal">&nbsp;</td>
          <td width="12%" height="25"  class="MsoNormal" align="left">
          	<input type="hidden" name="Id_UnOpenFixDeposit" value="<%=OBConstant.OperationReminderType.UNOPENFIXDEPOSIT%>" />
            <input type="text" class="box" name="Fate_UnOpenFixDeposit" maxlength="4" id="Fate_UnOpenFixDeposit" value="<%if(lFateUnOpenFixDeposit != -1) {out.print(lFateUnOpenFixDeposit);}%>" size="8" />
          </td>
          <td width="40%" height="25"  class="MsoNormal" align="left">������</td>
        </tr>
        
        <tr class="MsoNormal" >
		  <td width="45%" height="25" class="MsoNormal" align="right">
			<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.UNNOTIFYDEPOSITDRAW)%>��
	      </td>
	      <td width="3%" height="25"  class="MsoNormal">&nbsp;</td>
          <td width="12%" height="25"  class="MsoNormal" align="left">
          	<input type="hidden" name="Id_UnNotifyDepositDraw" value="<%=OBConstant.OperationReminderType.UNNOTIFYDEPOSITDRAW%>" />
            <input type="text" class="box" name="Fate_UnNotifyDepositDraw"  maxlength="4" id="Fate_UnNotifyDepositDraw" value="<%if(lFateUnNotifyDepositDraw != -1) {out.print(lFateUnNotifyDepositDraw);}%>" size="8" />
          </td>
          <td width="40%" height="25"  class="MsoNormal" align="left">������</td>
        </tr>
        
		<!--a try for glass start-->
		<tr  class="MsoNormal">
			<td width="5" height="25"  class="MsoNormal" colspan="4"></td>
		</tr>
      </table>
      
      <br>
      <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
          <td align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 id="submitt" type=button value=" �� �� " onClick="return save()" onKeyDown="return save()">&nbsp;&nbsp;
								
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 type="reset" value=" �� �� ">&nbsp;&nbsp; &nbsp;&nbsp;

          </td>
        </tr>
      </table>
<%
		String strClickCount = (String) session.getAttribute("clickCount");
		Log.print("strClickCount=" + strClickCount);
		if (strClickCount == null) 
		{
			strClickCount = "0";
		}
%>
	  
	  </td>
  </tr>
</table>
</form>
<!--presentation end-->
<script language="javascript">
	function save() {
		var fateOpenFixDeposit = document.getElementById("Fate_OpenFixDeposit");
		var fateNotifyDepositDraw = document.getElementById("Fate_NotifyDepositDraw");
		var fateUnOpenFixDeposit = document.getElementById("Fate_UnOpenFixDeposit");
		var fateUnNotifyDepositDraw = document.getElementById("Fate_UnNotifyDepositDraw");
		//var Fate_CapTransfer = document.getElementById("Fate_CapTransfer");
		var bReturn = false;
		var aReturn = false;
		if(fateOpenFixDeposit.value.length != 0)
		{
			aReturn = true;
		}
		else if(fateNotifyDepositDraw.value.length != 0)
		{
			aReturn = true;
		}
		//else if(Fate_CapTransfer.checked ==true)
		//{
		//	aReturn = true;
		//}
		else if(fateUnOpenFixDeposit.value.length != 0)
		{
			aReturn = true;
		}
		else if(fateUnNotifyDepositDraw.value.length != 0)
		{
			aReturn = true;
		}
		if(aReturn==false)
		{
			alert("����д������Ϣ");
			return false;
		}
		var regx = /^\d+$/;
		if(fateOpenFixDeposit.value.length == 0) {
			bReturn = true;
		}
		else if (regx.test(fateOpenFixDeposit.value) == false) {
			alert("<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.OPENFIXDEPOSIT)%>��������, ������һ���Ǹ�����������");
			bReturn = false;
			fateOpenFixDeposit.value = "";
			fateOpenFixDeposit.focus();
		}
		else {
			bReturn = true;
		}
		
		if(bReturn == true) {
			if(fateNotifyDepositDraw.value.length == 0) {
				bReturn = true;
			}
			else if(regx.test(fateNotifyDepositDraw.value) == false) {
				alert("<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.NOTIFYDEPOSITDRAW)%>��������, ����������������");
				bReturn = false;
				fateNotifyDepositDraw.value = "";
				fateNotifyDepositDraw.focus();
			}
			else {
				bReturn = true;
			}
		}
		
		var regx = /^[0-9]*[1-9][0-9]*$/;
		if(bReturn == true) {
			if(fateUnOpenFixDeposit.value.length == 0) {
				bReturn = true;
			}
			else if(regx.test(fateUnOpenFixDeposit.value) == false) {
				alert("<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.UNOPENFIXDEPOSIT)%>������, ������һ������������");
				bReturn = false;
				fateUnOpenFixDeposit.value = "";
				fateUnOpenFixDeposit.focus();
			}
			else {
				bReturn = true;
			}
		}
		
		if(bReturn == true) {
			if(fateUnNotifyDepositDraw.value.length == 0) {
				bReturn = true;
			}
			else if(regx.test(fateUnNotifyDepositDraw.value) == false) {
				alert("<%=OBConstant.OperationReminderType.getName(OBConstant.OperationReminderType.UNNOTIFYDEPOSITDRAW)%>������, ������һ���Ǹ���������");
				bReturn = false;
				fateUnNotifyDepositDraw.value = "";
				fateUnNotifyDepositDraw.focus();
			}
			else {
				bReturn = true;
			}
		}
		
		if(bReturn == true) {
			if(confirm("�Ƿ񱣴�?")) {
				frmOperationReminder.strAction.value = "<%=OBConstant.SettInstrStatus.SAVE%>";
				frmOperationReminder.submit();
			}
			bReturn = false;
		}
		
		return bReturn
	}
</script>
<%
		/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);	
    }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />