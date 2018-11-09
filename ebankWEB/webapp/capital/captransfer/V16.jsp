<%--
/*
 * �������ƣ�V16.jsp
 * ����˵�����ʽ𻮲��޸�����ҳ��
 * �������ߣ�����
 * ������ڣ�2003��09��17��
 */
--%>

<!--Header start-->


<%@ page contentType = "text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%@ include file="/magnifier/MagnifierSQL.jsp" %>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ʽ𻮲�]";
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
	
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";

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
        /* ��ʾ�ļ�ͷ */
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/Control.js"></script>
<script language="JavaScript" src="/webob/Check.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">
     <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          <td width="720"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">�������</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
		  <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">������ƣ�</td>
          <td width="590" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" class="rebox" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? sessionMng.m_strClientName : financeInfo.getPayerName() %>" readonly>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>

		<!--a try for glass start-->
		<tr bgcolor="#C8D7EC">
		<td width="5" height="25"></td>
<%
		//����˺ŷŴ�
		OBHtmlCom.createBankAccountCodeCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"dPayerCurrBalance","dPayerUsableBalance","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","����˺�"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>		
		<td width="5"></td>
		</tr>
		<tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
        </tr>
		<!--a try for glass end-->
		<!--
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">����˺ţ�</td>
          <td width="430" height="25" class="box">
		<input type="text" class="rebox" name="sPayerAcctNo" size="16" value="<%= financeInfo.getPayerAcctNo() %>" readonly>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
		-->
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>

          <td width="130" height="25" class="graytext">��ǰ��</td>
          <td width="590" height="25" class="box">
		<input type="text" class="amountbox" name="dPayerCurrBalance" size="16" value="<%= sPayerCurrentBalance %>" readonly>
		<font class="graytext" >
		������
		</font>
		<input type="text" class="amountbox" name="dPayerUsableBalance" size="16" value="<%= sPayerUsableBalance %>" readonly>
		  </td>
          <td width="5" colspan="4"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="720"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">�տ����</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25">
            <p><span class="graytext">��ʽ��</span></p>
          </td>
          <td width="590" height="25">
           <input type="hidden" name="nRemitTypeHidden" value="<%= financeInfo.getRemitType() %>">
<%
			OBHtmlCom.showRemitTypeListControl(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
		  </td>
          <td width="1" height="25"></td>
        </tr>
      </table>
		   
	  <!--��ʽ��̬��ʾ�տ����-->

	  <table width="730" border="0" cellspacing="0" cellpadding="0">

		 <tr id="payeeName">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">�տ���ƣ�</td>
          <td height="25" width="590" colspan="2" class="graytext">
            <input type="text" class="rebox" name="sPayeeName" value="<%=(financeInfo.getID() == -1)?sessionMng.m_strClientName : financeInfo.getPayeeName() %>" size="24" readonly>
              </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeBankNoZoom">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//�տ�˺ŷŴ󾵣���ת��
		OBHtmlCom.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"sPayeeNameZoomBankCtrl","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","�տ�˺�"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");
%>	 
		 		 																																												
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNoZoomLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeNameZoomBank">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
		  <td height="25" width="130" class="graytext">�տ���ƣ�</td>
		  <td height="25" colspan="2" class="graytext">
		  	<input type="Text" name="sPayeeNameZoomBankCtrl" value="<%= financeInfo.getPayeeName() %>" >
		  </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameZoomBankLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<tr id="payeeBankNoZoomInternal">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//�տ�˺ŷŴ󾵣��ڲ�ת�ˣ�
		OBHtmlCom.createBankAccountCodeCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","�տ�˺�"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>			 
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNoZoomInternalLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		<!--
        <tr id="payeeAcctNo">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="128" class="graytext">�տ�˺ţ�</td>
          <td height="25" colspan="2" class="graytext">
            <input type="text" class="rebox" name="sPayeeAcctNo" value="<%= financeInfo.getPayeeAcctNo() %>" size="30" readonly>
          </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeAcctNoLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		-->
		 <tr id="payeeAcctNoZoom">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//�տ�˺ŷŴ󾵣��㣩
		OBHtmlCom.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"sPayeeNameZoomAcctCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","�տ�˺�"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>	

          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeNameZoomAcct">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
		  <td height="25" width="130" class="graytext">�տ���ƣ�</td>
		  <td height="25" colspan="2" class="graytext">
		  	<input type="text" name="sPayeeNameZoomAcctCtrl" value="<%= financeInfo.getPayeeName() %>" onfocus="nextfield ='sPayeeProv';">
		  </td>
          
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<tr id="payeePlace">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">����أ�</td>
          <td height="25"  class="graytext">
            <input type="text" name="sPayeeProv" value="<%=  financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCity';">
            ʡ
            <input type="text" name="sPayeeCity" value="<%=  financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankName';">
            �� </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeePlaceLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeBankNameRead">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">���������ƣ�</td>
          <td height="25" colspan="2" class="graytext">
			<input type="text" class="rebox" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="30" readonly>
          </td>
		<td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeBankName">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">���������ƣ�</td>
          <td height="25" colspan="2" class="graytext">
            <input type="text" name="sPayeeBankName" value="<%=  financeInfo.getPayeeBankName() %>" size="30" onfocus="nextfield ='dAmount';">
          </td>
        <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNameLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<!--
		<tr id="payeeBalance">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="128" class="graytext">��ǰ��</td>
          <td height="25"  class="graytext">
            
          </td>
          <td height="25"  class="graytext">
		������
            
          </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeBalanceLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		-->
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="720 "height="25" bgcolor="#456795" colspan="3"><font size="3" color="#FFFFFF" class="whitetext">��������</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" bgcolor="#C8D7EC" class="graytext">��</td>
          <td width="20" height="25" bgcolor="#C8D7EC" class="graytext">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td height="25" width="590" bgcolor="#C8D7EC" class="box">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%= financeInfo.getFormatAmount() %>","tsExecute","",1);
			</script>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext" colspan="2">ִ���գ�</td>
          <td height="25" bgcolor="#C8D7EC" class="box">
           		<fs_c:calendar 
	          	    name="tsExecute"
		          	value="" 
		          	properties="nextfield ='sNote'" 
		          	size="12"/>
		        <script>
	          		$('#txtExecuteA').val('<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(1,1)) :financeInfo.getFormatExecuteDate() %>');
	          	</script>
		  </td>

          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">�����;��</td>
          <td height="25" class="box" nowrap>
		  <textarea name="sNote" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onFocus="nextfield ='';"><%= financeInfo.getNote() %></textarea>
			<font color="red">(������50����)</font>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right"><img src="\webob\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"></div>
          </td>
          <td width="62">
            <div align="right"><img src="\webob\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"></div>
          </td>
        </tr>
      </table>

	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	  
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * ����У�顢����ʾ���Ƽ�FORM�ύ
	 * javascript
	 */

	/* ��ʽ */
	var iRemitType = frmzjhb.nRemitType.value;
	jump();

	/* ʵ�ֹ��ܣ���̬��ʾ���ݻ�ʽȷ�����տ����¼���
	 * ʵ�ַ�����ͨ����TR�е�ID���Կ���ʵ��
	 * �������򣺲����Ŵ󾵵İ�һ����������(xxx)
	 * 			 ͨ���Ŵ�ʵ�ֵ���Zoom����(xxxZoom)
	 *			 ͬһ�Ŵ󾵶Բ�ͬ����Ӱ��ʱͨ������Ӧ��׺����(xxxZoomxxx)
	 */

	/* �տ���� */
	function jump()
	{	
		iRemitType = frmzjhb.nRemitType.value;
		
		payeeName.style.display = "none";
		payeeNameLine.style.display = "none";
		payeeNameZoomBank.style.display = "none";
		payeeNameZoomBankLine.style.display = "none";
		payeeNameZoomAcct.style.display = "none";
		payeeNameZoomAcctLine.style.display = "none";
		/* �տ�����˺� */
		payeeBankNoZoom.style.display = "none";
		payeeBankNoZoomLine.style.display = "none";
		payeeBankNoZoomInternal.style.display = "none";
		payeeBankNoZoomInternalLine.style.display = "none";
		/* �տ�˺� */
		//payeeAcctNo.style.display = "none";
		//payeeAcctNoLine.style.display = "none";
		payeeAcctNoZoom.style.display = "none";
		payeeAcctNoZoomLine.style.display = "none";
		/*  ���������� */
		payeeBankName.style.display = "none";
		payeeBankNameRead.style.display = "none";
		payeeBankNameLine.style.display = "none";
		/* ����� */
		payeePlace.style.display = "none";
		payeePlaceLine.style.display = "none";
		/* ��ǰ��� */
		//payeeBalance.style.display = "none";
		//payeeBalanceLine.style.display = "none";
		/*�����;*/
		frmzjhb.sNote.value = "";
		
		if( frmzjhb.lInstructionID.value > 0)
		{
			frmzjhb.sNote.value = "<%=financeInfo.getNote()%>";
		}
		else
		{
			frmzjhb.sNote.value = "<%=sessionMng.m_strClientShortName%>";
		}

		/* ���ݻ�ʽȷ������ʾ��TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // ��ʽ��ת
		{
			/* ���Ŵ󾵡����տ�����˺��й������տ���� */
			payeeNameZoomBank.style.display = "";
			payeeNameZoomBankLine.style.display = "";
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoom.style.display = "";
			payeeBankNoZoomLine.style.display = "";
			/* �����Ŵ󾵵��տ�˺� */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/*  ���������� */
			payeeBankNameRead.style.display = "";
			payeeBankNameLine.style.display = "";
			
			/* ���ݻ�ʽ�ı�����; */
			//frmzjhb.sNote.value = "��ת";
		}
		if((iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  || // ��ʽ���
			(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>) || // ��ʽ�Ż�
			(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)) // ��ʽƱ��
		{
			/* ���Ŵ󾵡����տ�˺��й������տ���� */
			payeeNameZoomAcct.style.display = "";
			payeeNameZoomAcctLine.style.display = "";
			/* ���Ŵ󾵵��տ�˺� */
			payeeAcctNoZoom.style.display = "";
			payeeAcctNoZoomLine.style.display = "";
			/* ����� */
			payeePlace.style.display = "";
			payeePlaceLine.style.display = "";
			/*  ���������� */
			payeeBankName.style.display = "";
			payeeBankNameLine.style.display = "";
			
			/* ���ݻ�ʽ�ı�����; */
			if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)
			{
				// frmzjhb.sNote.value="���";
			}
			if(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>)
			{
				//frmzjhb.sNote.value="�Ż�";
			}
			if(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)
			{
				//frmzjhb.sNote.value="Ʊ��";
			}
		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // ��ʽ�ڲ�ת��
		{
			/* �����Ŵ󾵵��տ���� */
			payeeName.style.display = "";
			payeeNameLine.style.display = "";
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoomInternal.style.display = "";
			payeeBankNoZoomInternalLine.style.display = "";
			 
			/* �����Ŵ󾵵��տ�˺� */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/* ��ǰ��� */
			//payeeBalance.style.display = "";
			//payeeBalanceLine.style.display = "";
			
			/* ���ݻ�ʽ�ı�����; */
			//frmzjhb.sNote.value="�ڲ�ת��";
		}
	}
	
	function getNextField()
	{
              //>>>>add by shiny 20030403
      	      var iRemitType = frmzjhb.nRemitType.value;
			  if (iRemitType == -1)
			  {//û��ѡ��
			  	  alert("��ѡ���ʽ");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//�ڲ�ת��
                  frmzjhb.sPayeeAccountInternalCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//��ת
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* �޸��ύ������ */
    function addme()
    {
        
		frmzjhb.action = "C11.jsp";
		if (validate() == true)
        {
			/* ȷ���ύ */
<%--			if (!confirm("�Ƿ��ύ��"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
            frmzjhb.submit();
        }
    }
    /* ȡ�������� */
    function cancelme()
    {
		if (frmzjhb.lInstructionID.value == -1)
		{	
			if (confirm("ȷ��ȡ����"))
			{
				frmzjhb.action="";
				frmzjhb.submit();
			}
		}
		else
		{
			if (confirm("ȷ��ȡ����"))
			{
        		history.go(-1);
			}
		}
    }

    /* У�麯�� */
    function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        */

		/* ������Ϸǿ�У�� */
		if (frmzjhb.sPayerName.value == "")
		{
			alert("������Ʋ���Ϊ��");
			frmzjhb.sPayerName.focus();
			return false;
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("��ѡ�񸶿�˺�");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		

		/* ���ݻ�ʽ���տ���Ͻ��зǿ�У�� */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>)// ��ʽ��ת
		{
			if (frmzjhb.sPayeeNameZoomBankCtrl.value == "")
			{
				alert("��ѡ���տ���ƻ��˺�");
				frmzjhb.sPayeeNameZoomBankCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			
	

		}
		if((iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  || // ��ʽ���
			(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>) || // ��ʽ�Ż�
			(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>))// ��ʽƱ��
		{
			
			if (frmzjhb.sPayeeNameZoomAcctCtrl.value == "")
			{
				alert("��ѡ���տ���ƻ��˺�");
				frmzjhb.sPayeeNameZoomAcctCtrl.focus();
				return false;
			}
		
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// ��ʽ�ڲ�ת��
		{
			if (frmzjhb.sPayeeName.value == "")
			{
				alert("�տ���Ʋ���Ϊ��");
				frmzjhb.sPayeeName.focus();
				return false;
			}
			if (frmzjhb.sPayeeAccountInternalCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		}

		/* �������Ϸǿ�У�� */
		/* ���У�� */
		if(!checkAmount(frmzjhb.dAmount, 1, "���׽��"))
		{
			return false;
		}

		/* ִ����У�� */
		if (!checkInterestExecuteDate(frmzjhb.tsExecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		}

		/* ҵ��У�� */
		/* ���������׽�� */
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		//add by sun for test
		/*comment by shiny 2003-03-11
		//alert("�����ǵ�����Ϣ");
		//alert("͸֧�޶�=" + frmzjhb.sPayerBankNoZoom_OVAmount.value);
		//alert("���������׽�͸֧�޶�=" + dBalance)
		
				alert("�������=" + frmzjhb.dPayerUsableBalance.value);
				alert("���׽��=" + frmzjhb.dAmount.value);
				alert("͸֧�޶�=" + frmzjhb.sPayerBankNoZoom_OVAmount.value);
				alert("���ɷ��Ž��=" + frmzjhb.sPayerBankNoZoom_MaxAmount.value);
				*/
				//alert("���������׽�͸֧�޶���ɷ��Ž��=" + dFinalBalance);
		//add end
		
		
		
		//���������׽�0������ʾ���������㣬���������뻮����
		if (dBalance < 0) 
		{
			alert("�������㣬���������뻮�����");
			frmzjhb.dAmount.focus();
			return false;
		}

    	return true;
    }
</script>

<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	//setSubmitFunction("addme(frmzjhb)");
	setFormName("frmzjhb");
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