<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%--
/*
 * �������ƣ�n002-v.jsp
 * ����˵����֪ͨ�����޸�����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��10��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.*,
				    com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				   com.iss.itreasury.settlement.util.SETTConstant"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.TransTypeSet" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrStatus" %>
<%@page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[֪ͨ����]";
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
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = null;
	PayerOrPayeeInfo payeeInfo = null;
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
		String lIsSoft = "";
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();
	long lAbstractID = -1;

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

		/* �������л�ȡ��Ϣ */
		financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payeeInfo");
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		
		/* ����Ϣ���л�ȡ��ʽ���ĵ�ǰ���Ϳ��ý�� */
        sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
        sPayerUsableBalance = financeInfo.getFormatUsableBalance();
        if (sPayerUsableBalance==null || sPayerUsableBalance.trim().length()==0) 
		{	
			sPayerUsableBalance="0.00";
		}
		/*��ȡ�˻��������Ϣ*/
		accountID = (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID();
		//accinfo = accdel.findAccountByID(accountID);
		accinfo = dao.findByID(accountID);
		dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
		

					if(accinfo.getAccountExtendInfo() == null) {
						lIsSoft = "0";	
					}
					else {
						if(accinfo.getAccountExtendInfo().getIsSoft() > 0) {
							lIsSoft=String.valueOf(accinfo.getAccountExtendInfo().getIsSoft());	
						}
					}
		
		System.out.println("lIsSoft in n002-v-------------->" + lIsSoft);
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        String str = (String)request.getAttribute("flag");
		if(str!=null&&str.equals("delete")){
		out.println("<script language='javascript'>");
		out.println("alert('ɾ���ɹ�')");
		out.println("</script>");
	}
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:security/>
<form method="post" name="frm">
<safety:certInformation/>
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">֪ͨ����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	 
	<br/>
	<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> �����˻�</td>
	<td width="800"><a class=lab_title3></td>
</tr>	   
    </table> 
    <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
       
        <tr class="MsoNormal">
		  <td width="5" height="25"></td>
          <td width="17%" height="25" class="graytext">�����˻����ƣ�</td>
          <td width="430" height="25" class="graytext">
            <input type="text" class="box" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? NameRef.getClientNameByID(sessionMng.m_lClientID) : financeInfo.getPayerName() %>" readonly>
          </td>
          <td width="5" height="25"></td>
          <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
        </tr>

		<!--a try for glass start-->
		<tr class="graytext">
		<td height="25"><font color="#FF0000">* </font>
		<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID%>"/>
        <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>"/>
        <input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>"/>
        <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>"/>
        </td>
	
					<td width="130" height="25" align="left">����˺ţ�</td>
					<td>
 						<fs:dic id="sPayerAccountNoZoomCtrl" size="20" form="frm" title="����˺�" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams="frm.sPayerAccountNoZoomCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value" value="<%=financeInfo.getPayerAcctNo()%>" nextFocus="noticeDay" width="500">
							<fs:columns> 
								<fs:column display="�˺�" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="�˻�����" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sPayerAccountNoZoomCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="payerAcctID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="dPayerCurrBalance" name="dPayerCurrBalance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
								<fs:pageElement elName="dPayerUsableBalance" name="dPayerUsableBalance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
								<fs:pageElement elName="tsExecute" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="hiddenOpendate" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
						<% 
							OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
							long lOffice = -1;
							if(financeInfo.getPayerAcctID()>0)
							{
								lOffice = obFinanceInstrDao.findOfficeByAccountId(financeInfo.getPayerAcctID());
							}else
							{
								lOffice = sessionMng.m_lOfficeID;
							}
							Timestamp opendate = Env.getSystemDate(lOffice,sessionMng.m_lCurrencyID);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String strOpenDate = sdf.format(opendate);

						%>
			<input type="hidden" name='hiddenOpendate' value="<%= request.getParameter("oldOpenDate")==null||request.getParameter("oldOpenDate").equals("")?strOpenDate:request.getParameter("oldOpenDate")%>">
					</td>
        <td ></td>
		<td ></td>
		<td height="25" colspan="2"  class="MsoNormal"></td>
		</tr>
		<tr>         
			<td height="25"></td>
          	<td height="25" class="graytext">��ǰ��</td>
          	<td height="25" class="graytext">
          		<fs:amount 
			       		form="frm"
		       			name="dPayerCurrBalance"
		       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(sPayerCurrentBalance)) %>"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
	     	</td>
     	</tr>
     	<tr>
     		<td height="25"></td>
			<td><font class="graytext" >������</font></td>
	     	<td>
		     	<fs:amount 
			       		form="frm"
		       			name="dPayerUsableBalance"
		       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(sPayerUsableBalance)) %>"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		     	<input type="hidden" name="payerAcctID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
		  	</td>
          	<td colspan="4"></td>
        </tr>
      </table>
      <br>
      <table align="" width="140" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> ֪ͨ����˻�</td>
			<td width="17"><a class=lab_title3></td>
		</tr>	   
      </table> 
      <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
       
	  <tr class="graytext">
          <td width="5" height="25"></td>
          
        <td width="17%" height="25"> <span class="graytext">֪ͨ����˺ţ�</span></td>

      	<td width="590" height="25" class="graytext">
      	<!--
			<input type="text" class="box" name="sPayeeAccountNo" size="24" value="<%= (financeInfo.getID() == -1) ? NameRef.getNoLineAccountNo(payeeInfo.getAccountNo()):NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo()) %>" readonly  class=rebox>
	  		<input type="hidden" name="payeeAcctID"  value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID() %>" >
	  	-->
	  	<%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.NOTIFY,financeInfo.getPayeeAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
      	<input type="hidden" name="sPayeeAccountNo" size="20" value="<%=financeInfo!=null?financeInfo.getPayeeAcctNo():"" %>" class="box"> 
        <input type="hidden" name="payeeAcctID"  value="<%=financeInfo!=null?financeInfo.getPayeeAcctID():-1 %>" > 
	  	
	  	</td>
          <td height="25"></td>
        </tr>
      
        <tr class="graytext">
          <td height="25"><font color="#FF0000">* </font></td>
          <td height="25">
            
        <p><span class="graytext">֪ͨ���Ʒ�֣�</span></p>
          </td>
          <td height="25" class="graytext">
		  <%SETTHTML.showFixedDepositMonthListCtrl(out,"noticeDay",SETTConstant.TransQueryType.NOTIFY,financeInfo.getNoticeDay(),false,"onfocus=\"nextfield='amount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
          </td>
          <td height="25"></td>
        </tr>
      </table>
	  <br>
	  <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" >
	
<tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> ��������</td>
	<td width="800"><a class=lab_title3></td>
</tr>	   
      </table> 
      <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        
        <tr class="graytext">
          <td width="5" height="25"><font color="#FF0000">* </font></td>
          <td width="17%" height="25" class="graytext">��</td>
          <td height="25" width="590" class="graytext">
           	<fs:amount 
	       		form="frm"
       			name="amount"
       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount())) %>"
       			nextFocus="tsExecute"
       			chineseCtrlName="sChineseAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td height="25"></td>
        </tr>
        <tr class="graytext">
          <td height="25"></td>
          <td height="25" class="graytext" >��д���(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)��</td>
          <td height="25" class="graytext">
			<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td height="25"></td>
        </tr>
        <tr class="graytext">
          <td height="25"><font color="#FF0000">* </font></td>
          <td height="25" class="graytext" >ִ���գ�</td>
          <td height="25" class="graytext">
          <fs_c:calendar 
         	    name="tsExecute"
	          	value="<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>" 
	          	properties="nextfield ='sNoteCtrl'" 
	          	size="20"/>
		  </td>

          <td height="25"></td>
        </tr>
        
        <tr class="graytext">
        	<td height="25"><font color="#FF0000">*</font></td>
        	<td height="25" align="left">�����;��</td>	
        	<td height="25">
				<fs:dic id="sNoteCtrl" form="frm" title="�����;" sqlFunction="getAbstractSettingSQL" sqlParams="frm.lOfficeID.value,frm.lUserID.value,frm.sNoteCtrl.value" nextFocus="add" width="500" value="<%=financeInfo.getNote() %>" type="textarea" row="2" col="55" needRemind="true" maxlength="30" properties="" position="top">
					<fs:columns>
						<fs:column display="ժҪ����" name="AbstractCode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="ժҪ����" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="sNote" name="AbstractID" type="<%=PagerTypeConstant.STRING %>" elType="hidden"/>
						<fs:pageElement elName="sNoteCtrl" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" elType="text"/>
					</fs:pageElements>
				</fs:dic>
        	</td>	    
        	<td height="25" class="MsoNormal">&nbsp;</td>    	
        </tr>        

      
<%
/*		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "frm";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " �����;";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = (financeInfo.getNote()==null)?"":financeInfo.getNote();
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 100;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"add"};
		
	OBMagnifier.createAbstractSettingCtrl(
		out,
		lOfficeIDAbstract,
		lClientIDAbstract,
		lCurrencyIDAbstract,
		strFormNameAbstract,
		strCtrlNameAbstract,
		strTitleAbstract,
		lAbstractIDAbstract,
		strAbstractDescAbstract,
		strFirstTDAbstract,
		strSecondTDAbstract,
		maxLength,
		strNextControlsAbstract);*/
%>      

      </table>
      <br>
    <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " �� �� " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
          <td width="100%" align="right">
			<input class=button1 name=add type=button value=" �� �� " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'">
			<fs:obApprovalinitbutton controlName="approvalInit"
		 										 value="���沢�ύ����"
												 classType="button1"
												 onClickMethod="doSaveAndApprovalInit();"
												 officeID="<%=sessionMng.m_lOfficeID%>"
												 clientID="<%=sessionMng.m_lClientID%>"
												 currencyID="<%=sessionMng.m_lCurrencyID%>"
												 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
												 transTypeID="<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>" />
			<%--<input class=button1 name=add type=button value=" ���� " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">--%>
			<input class=button1 name=addreset type=reset value=" �� �� " >&nbsp;&nbsp;

          </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
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
		if(sign.equals("again"))
		{
			financeInfo.setID(-1);
		}
%>
		<input type="hidden" name="clickCount" value="<%=strClickCount%>">
	    <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
		<input type="hidden" name="dMinSinglePayAmount" value="<%=dMinSinglePayAmount%>">
		<input name="strAction" type="hidden">
	  <input type="hidden" name="confirmUserID" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="timestamp" value="<%=System.nanoTime() %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>">
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <input type="hidden" name="lIsSoft" value="<%=lIsSoft%>">

</form>



<!--presentation end-->

<script language="Javascript">
    /* �޸ı��洦���� */
    function addme()
    {
   
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		var signatureValue;
		if (validate() == true)
        {
        
		    //��������ǩ�� 
		    if(security.isSign())
		    {
		    	var format = new OpenNotifyAccountOperator();
		    	sign(format,frm);
		    }
			//ȷ�ϱ��� 
			if (!confirm("�Ƿ񱣴棿"))
			{
				return false;
			}
			showSending();

			frm.action = "n003-c.jsp?sign=<%=sign%>"+"&oldOpenDate="+frm.hiddenOpendate.value;
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
    }
    function doSaveAndApprovalInit()
    {
		frm.action = "n003-c.jsp?sign=<%=sign%>"+"&oldOpenDate="+frm.hiddenOpendate.value;
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		var signatureValue;
		if (validate() == true){

		    //��������ǩ�� 
		    if(security.isSign())
		    {
		    	var format = new OpenNotifyAccountOperator();
		    	sign(format,frm);
		    }	
		    				
			//ȷ�ϱ��沢�ύ����
			if (!confirm("�Ƿ񱣴沢�ύ������"))
			{
				return false;
			}	
			showSending();	

			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
			document.frm.submit();
		}
    }
    /* ȡ�������� */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			//if (confirm("ȷ��������")) 
			//{
				frm.action="";
				showSending();	
				frm.submit();
			//}
		}
		else
		{
			//if (confirm("ȷ��������"))
			//{	
        		history.go(-1);
			//}
		}
    }
    
    //add by dwj 20111107
    function saccountnochange()
    {
    	var a = frm.saccountno1.options[frm.saccountno1.selectedIndex].text;
    	frm.sPayeeAccountNo.value = a;
    	frm.payeeAcctID.value = frm.saccountno1.value;
    }
    //end add by dwj

    /* У�麯�� */
    function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */

		/* ������Ϸǿ�У�� */
		if (frm.sPayerName.value == "")
		{
			alert("������Ʋ���Ϊ��");
			frm.sPayerName.focus();
			return false;
		}
		
		if (frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("��ѡ�񸶿�˺�");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		if (frm.sName.value == -1)
		{
			alert("����˺�,��ӷŴ�ѡ��");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		/*֪ͨ���Ʒ��У��*/
		
		if (frm.noticeDay.value == "" || frm.noticeDay.value<0)
		{
			alert("��ѡ��֪ͨ���Ʒ��");
			frm.noticeDay.focus();
			return false;
		}
		
		/* �տ���Ϸǿ�У�� */
		if (frm.sPayeeAccountNo.value == "")
       {
             alert("֪ͨ�˺Ų����ڣ�����������ף�");
             return false;
       }
		//if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.amount.value))
		//{
			//alert("���С�ڸ��˻�֪ͨ�����������������룡");
			//document.frm.amount.focus();
			//return false;
	//	}
		/* �������Ϸǿ�У�� */
		/* ���У�� */
		if(!checkAmount(frm.amount, 1, "���׽��"))
		{
			return false;
		}

		/* ִ����У�� */
		if(document.all("tsExecute").value=="")
		{
			alert("ִ���ղ���Ϊ�գ���¼��");
			document.all("tsExecute").focus();
			return false;
		}
		
		if(!CompareDateString(frm.hiddenOpendate.value,frm.tsExecute.value))
		{
			alert("ִ���ղ���С��ϵͳ�����գ�");
			frm.tsExecute.focus();
			return false;
		}
		/* �����; */
		if (!InputValid(frm.sNoteCtrl, 0, "textarea", 1, 0, 100,"�����;"))
		{
			return false;
		}	
		
			if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.amount.value))
			{
				alert("�����С�ڴ�����������Ϊ��<%=sessionMng.m_strCurrencySymbol%>" + formatAmount(document.frm.dMinSinglePayAmount.value));
				if(parseInt(<%=lIsSoft%>) == 1) {
					return false;
				}
			}
		/* ҵ��У�� */
		/* ���������׽�� */
		var dBalance = parseFloat(reverseFormatAmount(frm.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frm.amount.value)) ;
		if (dBalance < 0) 
		{
			alert("�������㣬���������뻮�����");
			frm.amount.focus();
			return false;
		}
		if(Trim(frm.amount.value)=="")
		{
			alert("֧ȡ����Ϊ��");
			frm.amount.focus();
			return false;
		}
		if(parseFloat(reverseFormatAmount(frm.amount.value))<0.01){
			alert("֧ȡ����С��0.01");
			frm.amount.focus();
			return false;
		}
		/*��� BUG #13770::�����ˣ����ڿ���������֧ȡ���������桢֪ͨ������֪֧ͨȡ��û�л����;ʱ�����汨��ҳ��qushuang 2012-08-23*/
		if (frm.sNoteCtrl.value == ""||frm.sNoteCtrl.value<0)
		{
			alert("�����;����Ϊ�գ���ӷŴ���ѡ��");
			frm.sNoteCtrl.focus();
			return false;
		}
		/* BUG #13770 END */
    	return true;
    }
	firstFocus(frm.sPayerAccountNoZoomCtrl);
//	//setSubmitFunction("addme()");
	setFormName("frm");
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

<jsp:include page="/ShowMessage.jsp"/>