<%--
/*
 * �������ƣ�ck002-v.jsp
 * ����˵�����ʽ𻮲�����ƥ������ҳ��
 * �������ߣ�����
 * ������ڣ�2004��02��06��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%!
	/* ����̶����� */
	String strTitle = null;
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
	FinanceInfo financeInfo = new FinanceInfo();
	long lTransType = -1;
	
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
		System.out.println("***************");
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
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}

        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22" colspan="10">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">��ʸ����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br>
<table class=normal width="100%">
	<tr><td height="15">&nbsp;</td></tr>
	<%
		long lChild = GetNumParam(request,"child");
		if (lChild == 1)
		{
	%>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
<%
		//������λ�Ŵ�
		String strMagnifierNameClientName = "������λ";
		String strFormNameClientName = "frmzjhb";
		String strMainPropertyClientName = "";
		String strPrefixClientName = "";
		String[] strMainNamesClientName = {"txtClientCode"};
		String[] strMainFieldsClientName = {"ClientCode"};
		String strReturnInitValuesClientName=financeInfo.getChildClientNo();
		String[] strReturnNamesClientName = {"lClientID"};
		String[] strReturnFieldsClientName = {"id"};
		String[] strReturnValuesClientName = {String.valueOf(financeInfo.getChildClientID())};
		String[] strDisplayNamesClientName = {"�ͻ����","�ͻ�����"};
		String[] strDisplayFieldsClientName = {"ClientCode","ClientName"};
		int nIndexClientName = 0;
		String strSQLClientName = " getClient()";
		//String[] strNextControlsClientName = {"txtContractCode"};
		String strMatchValueClientName = "ClientCode";
		String strNextControlsClientName = "sPayerAccountNoZoomCtrl";
		String strTitleClientName = "������λ�ͻ����";
		String strFirstTDClientName=" height=\"25\" class=\"MsoNormal\" colspan=2 ";
		String strSecondTDClientName= " width=\"500\" height=\"25\" ";	
		OBMagnifier.showZoomCtrl(out,
				strMagnifierNameClientName,
				strFormNameClientName,
				strPrefixClientName,
				strMainNamesClientName,
				strMainFieldsClientName,
				strReturnNamesClientName,
				strReturnFieldsClientName,
				strReturnInitValuesClientName,
				strReturnValuesClientName,
				strDisplayNamesClientName,
				strDisplayFieldsClientName,
				nIndexClientName,
				strMainPropertyClientName,
				strSQLClientName,
				strMatchValueClientName,
				strNextControlsClientName,
				strTitleClientName,
				strFirstTDClientName,
				strSecondTDClientName);
%>
      <td width="5" class="MsoNormal"></td>
    </tr>
	
	<%			
		}
	%>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" align="left"><font color='#FF0000'>* </font>����˺ţ�</td>
	  <td>
	  	<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>" >
	  	<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>" >
	  	<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>" >
		<fs_c:dic id="sPayerAccountNoZoomCtrl" size="22" form="frmzjhb" title="����˺�" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams='frmzjhb.sPayerAccountNoZoomCtrl.value,frmzjhb.lUserID.value,frmzjhb.lClientID.value,frmzjhb.lCurrencyID.value,frmzjhb.lInstructionID.value' value="<%=financeInfo.getPayerAcctNo()%>" nextFocus="nRemitType" width="500">
			<fs_c:columns> 
				<fs_c:column display="�˺�" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				<fs_c:column display="�˻�����" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
			</fs_c:columns>
			<fs_c:pageElements>
				<fs_c:pageElement elName="sPayerAccountNoZoomCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
				<fs_c:pageElement elName="nPayerAccountID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
				<fs_c:pageElement elName="dPayerCurrBalance" name="dPayerCurrBalance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
				<fs_c:pageElement elName="dPayerUsableBalance" name="dPayerUsableBalance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
				<fs_c:pageElement elName="tsExecute" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
				<fs_c:pageElement elName="hiddenOpendate" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				<fs_c:pageElement elName="sName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
			</fs_c:pageElements>							
		</fs_c:dic> 
		<%Timestamp opendate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strOpenDate = sdf.format(opendate);%>
		<input type="hidden" name="hiddenOpendate" value="<%=strOpenDate %>">
	  </td>		
<%
		//����˺ŷŴ�
		/*OBMagnifier.createPayerAccountNoCtrlByDate(out,
											sessionMng.m_lUserID,
											sessionMng.m_lCurrencyID,
											sessionMng.m_lOfficeID,
											financeInfo.getID(),
											sessionMng.m_lClientID,
											"nPayerAccountID",
											"dPayerCurrBalance",
											"dPayerUsableBalance",
											"tsExecute",
											"frmzjhb",
											financeInfo.getPayerAcctNo(),
											"sPayerAccountNoZoom",
											"<font color='#FF0000'>* </font>����˺�",
											" width=\"300\"  height=\"25\" class=\"MsoNormal\"",
											" width=\"300\" height=\"25\" ",
											new String[]{"nRemitType"});	*/
%>
      <td width="5" class="MsoNormal"></td>
    </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="220" height="25" class="MsoNormal">
            <p><span class="MsoNormal"><font color="#FF0000">* </font>��ʽ��</span></p>
          </td>
          <td width="500" height="25" class="MsoNormal">
           <input type="hidden" name="nRemitTypeHidden" value="<%= financeInfo.getRemitType() %>">
<%		if (lChild==1)
			{
				OBHtmlCom.showRemitTypeListControlZj2(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly");
			}
			else
			{
				OBHtmlCom.showRemitTypeListControlZj(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			}
%>
		  </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal"id="payeeBankNoZoom">
          <td height="25" width="5"></td>
<%
		//�տ�˺ŷŴ󾵣�ԭ��ת�����ڲ�ת�ˣ�
		OBMagnifier.createPayeeBankNOCtrl(out,
				sessionMng.m_lCurrencyID,
				sessionMng.m_lClientID,
				"nPayeeAccountID",
				"sPayeeNameBankNoCtrl",
				"frmzjhb",
				financeInfo.getPayeeAcctNo(),
				"sPayeeBankNoZoom",
				"   &nbsp;&nbsp;�տ�˺�",
				" width=\"300\" height=\"25\" class=\"MsoNormal\"",
				" width=\"300\" height=\"25\" ",
				false);
%>	 		

          <td class="MsoNormal" height="25" width="5"></td>
        </tr>
		<tr id="payeeNameZoomBank" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
		  <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;�տ���ƣ�</td>
		  <td height="25" class="MsoNormal">
		  	<input type="hidden"  class="box" name="sPayeeNameBankNoCtrl" value="<%= financeInfo.getPayeeName() %>" maxlength="50"  size="32" readonly>
		  </td>
          <td  height="25" width="5" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNameRead" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;���������ƣ�</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="box" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="32" readonly>
          </td>
		<td height="25" width="5" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal" id="payeeBankNoZoomInternal">
          <td height="25" width="5" class="MsoNormal"></td>
<%
		//�տ�˺ŷŴ󾵣�ԭ�ڲ�ת�ˣ����ڵı�ת��
		//OBMagnifier.createPayerAccountNoCtrlForZj(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","&nbsp;&nbsp;�տ�˺�"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ");	
	//	OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>�տ�˺�"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ");
	/*    OBMagnifier.createPayeeBankNOCtrl1(
	    		out,
	    		sessionMng.m_lCurrencyID,
	    		-1,
	    		"nPayeeAccountID",
	    		"sPayeeName",
	    		"frmzjhb",
	    		financeInfo.getPayeeAcctNo(),
	    		"sPayeeAccountInternal",
	    		"<font color='#FF0000'>* </font>�տ�˺�",
	    		" width=\"300\" height=\"25\" class=\"MsoNormal\"",
	    		" width=\"300\" height=\"25\" ");*/
%>			
		  <td width="130" height="25" align="left"><font color='#FF0000'>* </font>�տ�˺ţ�</td>
		  <td>
			<fs_c:dic id="sPayeeAccountInternalCtrl" size="22" form="frmzjhb" title="�տ�˺�" sqlFunction="getPayeeBankNOSQL1"  sqlParams='false,-1,frmzjhb.lCurrencyID.value,frmzjhb.sPayeeAccountInternalCtrl.value,frmzjhb.sPayeeName.value' value="<%=financeInfo.getPayeeAcctNo()%>" nextFocus="dAmount" width="500">
				<fs_c:columns> 
					<fs_c:column display="�տ�˺�" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="�˻�����" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sPayeeAccountInternalCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="nPayeeAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="hidsPayeeAccountInternalCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="hidsPayeeName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
		  </td>		
          <td class="MsoNormal" height="25" width="5"></td>
        </tr>
		 <tr id="payeeName" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;�տ���ƣ�</td>
          <td height="25" class="MsoNormal">
            <input type="text" class="box" name="sPayeeName" value="" size="32">
              </td>
          <td  height="25" width="5" class="MsoNormal"></td>
        </tr>
		 <tr class="MsoNormal" id="payeeAcctNoZoom">          
          <td height="25" width="5" class="MsoNormal"></td>
          <td width="130" height="25" align="left">&nbsp;&nbsp;�տ�˺ţ�</td>
		  <td>
			<fs_c:dic id="sPayeeAcctNoZoomCtrl" size="22" form="frmzjhb" title="�տ�˺�" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frmzjhb.lClientID.value,frmzjhb.lCurrencyID.value,frmzjhb.sPayeeAcctNoZoomCtrl.value,frmzjhb.sPayeeNameAcctNOCtrl.value' value="<%=financeInfo.getPayerAcctNo()%>" nextFocus="" width="900">
				<fs_c:columns> 
					<fs_c:column display="�տ�˺�" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="�տ����" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
					<fs_c:column display="����أ�ʡ��" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="����أ��У�" name="spayeecity" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="����������" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
					<fs_c:column display="���к�" name="spayeebankexchangeno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="CNAPS��" name="spayeebankcnapsno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="������" name="spayeebankorgno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="nPayeeAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeNameAcctNOCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="txtPayeeBankCNAPSNO" name="spayeebankcnapsno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="bankconnectnumber" name="spayeebankorgno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="departmentnumber" name="spayeebankexchangeno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="sPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
		  </td>		
<%
		//�տ�˺ŷŴ󾵣��㣩
		String[] sNextControlsEbank = {}; 
     /*   OBMagnifier.createPayeeAccountNOCtrl2(
        		out,
        		sessionMng.m_lCurrencyID,
        		-1,
        		"nPayeeAccountID",
        		"sPayeeNameAcctNOCtrl",
        		"sPayeeProv",
        		"sPayeeCity",
        		"sPayeeBankName",
        		"txtPayeeBankCNAPSNO",
        		"bankconnectnumber",
        		"departmentnumber",
        		"frmzjhb",
        		financeInfo.getPayeeAcctNo(),
        		"sPayeeAcctNoZoom",
        		"&nbsp;&nbsp;�տ�˺�",
        		" width=\"300\" height=\"25\" class=\"MsoNormal\"",
        		" width=\"300\" height=\"25\" ",
        		sNextControlsEbank,false);	*/
	//	OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeNameAcctNOCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","&nbsp;&nbsp;�տ�˺�"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ",false);	
%>	
          <td class="MsoNormal" height="25" width="5"></td>
        </tr>
		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
		  <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;�տ���ƣ�</td>
		  <td height="25"  class="MsoNormal">
			 <input type="text" name="sPayeeNameAcctNOCtrl" class="box" cols="30"  readonly rows="2" ><%= financeInfo.getPayeeName() %></input>
		  </td>
          
          <td height="25" width="5" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankName">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;���������ƣ�</td>
          <td height="25" class="MsoNormal">
            <input type="text" class="box" name="sPayeeBankName" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50" readonly>			
          </td>
        <td  height="25" width="1" class="MsoNormal"></td>
        </tr>
       	<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
          	<td width="220" height="25" class="MsoNormal"><font color="#FF0000">* </font>��</td>
          	<td height="25" width="500" class="MsoNormal">
            	<fs:amount 
		       		form="frmzjhb"
	       			name="dAmount"
	       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount()))%>"
	       			nextFocus="tsExecute"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          	</td>
          	<td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="220" height="25" class="MsoNormal" ><font color="#FF0000">* </font>ִ���գ�</td>
          <td height="25" class="MsoNormal">
	          	<fs_c:calendar 
	          	    name="tsExecute"
		          	value="" 
		          	properties="nextfield =''" 
		          	size="20"/>
		        <script>
	          		$('#tsExecute').val('<%=(financeInfo.getExecuteDate() == null)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>');
	          	</script>
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr>
       		<td colspan="2">
       		</td>          
       		<td align="right">
				<input type="button" name="Submitv00204" value=" ƥ �� " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
				<input type="button" name="Submitv00205" value=" �� �� " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
			</td>
		</tr>
		<tr><td height="15">&nbsp;</td></tr>
   </table>
      <input type="hidden" name="nPayerAccountID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
   	  <input type="hidden" name="sPayeeNameZoomAcctCtrl" value="">
   	  <input type="hidden" name="sPayeeNameZoomBankCtrl" value="" >
	  <input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="SelectType" value="<%= lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
</form>

<script language="Javascript">
var iRemitType;
	//����  �޸ģ���ҳ�������ɺ��ٽ��� ��ʼ��	
	document.onreadystatechange = subSomething;//��ҳ�����״̬�ı��ʱ��ִ���������.
	function subSomething()
	{
		if(document.readyState == "complete"){ //��ҳ�����״̬Ϊ��ȫ����ʱ����
			/* ��ʽ */
			jump();
		}
	} 
	

	/* ʵ�ֹ��ܣ���̬��ʾ���ݻ�ʽȷ�����տ����¼���
	 * ʵ�ַ�����ͨ����TR�е�ID���Կ���ʵ��
	 */

	/* �տ���� */
	function jump()
	{	
		iRemitType = frmzjhb.nRemitType.value;
		
		/* ��ʽ��ת */
		payeeBankNoZoom.style.display = "none";

		payeeNameZoomBank.style.display = "none";

		payeeBankNameRead.style.display = "none";

		/* ��ʽ�ڲ�ת�� */
		payeeBankNoZoomInternal.style.display = "none";

		payeeName.style.display = "none";

		/* ��ʽ���л�� */
		payeeAcctNoZoom.style.display = "none";

		payeeNameZoomAcct.style.display = "none";

		payeeBankName.style.display = "none";

		
		/* ���ݻ�ʽȷ������ʾ��TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // ��ʽ��ת
		{
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoom.style.display = "";

			payeeNameZoomBank.style.display = "";

			payeeBankNameRead.style.display = "";
				
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>||iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)   // ���и���
			
		{
			/* ���Ŵ󾵵��տ�˺� */
			payeeAcctNoZoom.style.display = "";

			payeeNameZoomAcct.style.display = "";

			payeeBankName.style.display = "";

		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // ��ʽ�ڲ�ת��
		{
			/* ���Ŵ󾵵��տ�����˺� */
			payeeBankNoZoomInternal.style.display = "";

			payeeName.style.display = "";

		}
	}
	
	function goback(){
		location.href='ck001-v.jsp';
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
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//����
                  frmzjhb.dAmount.focus();
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* �޸��ύ������ */
    function matchme()
    {
        
		frmzjhb.action = "ck007-c.jsp?child=<%=lChild%>";
		if (validate() == true)
        {
			/* ȷ���ύ */
<%--			if (!confirm("�Ƿ�ƥ�䣿"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
			showSending();
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
		
		if (frmzjhb.lClientID != null)
		{
			if (frmzjhb.lClientID.value <= 0)
			{
				alert("������λ�ͻ���Ų���Ϊ��");
				frmzjhb.txtClientCode.focus();
				return false;
			}
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("��ѡ�񸶿�˺�");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		
		if (iRemitType <= 0)
		{
			alert("��ѡ���ʽ��");
			frmzjhb.nRemitType.focus();
			return false;
		}
		/* ���ݻ�ʽ���տ���Ͻ��зǿ�У�� */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>)// ��ʽ��ת
		{			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>||iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)   // ��ʽ���и���
		{							
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
	      if(frmzjhb.nPayeeAccountID.value==-1){
		    alert("�տ�˻�����ӷŴ�����ѡ��");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// ��ʽ�ڲ�ת��
		{			
			if (frmzjhb.sPayeeAccountInternalCtrl.value == "")
			{
				alert("��ѡ���տ�˺�");
				frmzjhb.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		if(frmzjhb.nPayeeAccountID.value==-1){ 
		    alert("�տ�˻�����ӷŴ�����ѡ��");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
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
		var tsExecute = frmzjhb.tsExecute.value;
		if(tsExecute=="")
		{
			alert("ִ���ղ���Ϊ�գ���¼��");
			frmzjhb.tsExecute.focus();
			return false;
		}
	
	
	
		
		if(!CompareDateString(frmzjhb.hiddenOpendate.value,frmzjhb.tsExecute.value))
	{
		alert("ִ���ղ���С��ϵͳ�����գ�");
		form.tsExecute.focus();
		return false;
	}
	
	
	
		if(chkdate(tsExecute) == 0)
		{
			alert("ִ���ո�ʽ����ȷ��������¼��");
			frmzjhb.tsExecute.focus();
			return false;
		}
    	return true;
    }
</script>

<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	<% 
	if (lChild==1)
	{%>
	firstFocus(frmzjhb.txtClientCode);
	<%}else
	{%>
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	<%}%>
	//setSubmitFunction("matchme(frmzjhb)");
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