<%--
/*
 * �������ƣ�
 * ����˵�������л�����Ϣ�޸�
 * �������ߣ�baihuili
 * ���ڣ�2006��09��15��
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
					java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                   com.iss.itreasury.budget.util.*,
				   com.iss.itreasury.budget.setting.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[���л��]";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	long lAbstractID = -1;
	long lSourceType = 0;//ͷ��Ϣ��ʾ
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
%>
<%


	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
     OBHtml.validateRequest(out,request,response);
		String strContext = request.getContextPath();
		OBBankPayInfo info=(OBBankPayInfo)request.getAttribute("info");
		long lID = info.getId();
		System.out.println("##################"+info);
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />

<form method="post" name="frmzjhb">
<!--start  ָ����֤���html -->
<input name="Ver" id="Ver" type="hidden" value="">
<!--end  ָ����֤���html -->
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ҵ����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2"> �������</td>
					<td width="800"><a class=lab_title3></td>
				</tr>
			</table>
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr  class="">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="">
		  <td width="4" height="25" class=""></td>
          <td width="130" height="25"  align="left"><font color="#FF0000">* </font>������ƣ�</td>
          <td width="457" height="25" class="">
            <input type="text" class="box" name="name" size="30" value="<%=info.getName()%>" readonly >
            <INPUT type="hidden" name="npayeracctid" value="<%=info.getNpayeracctid()%>">
            <input type="hidden" name="id" value="<%=info.getId()%>">
          </td>
          <td width="129" height="25" class=""></td>
        </tr>
       <tr  class="">
		<td width="4" height="25" class="MsoNormal"></td>
	<%OBMagnifier.createPayerBankAccountNoCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID,"npayeracctid","dPayerCurrBalance","dPayerUsableBalance","name","frmzjhb","","sPayerAccountNoZoom","<font color='#FF0000'>* </font>����˺�"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
		%>
		<script>
          document.frmzjhb.sPayerAccountNoZoomCtrl.value="<%=com.iss.itreasury.ebank.util.NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %>";
          </script>
		<td width="130" class="" ></td>
		 </tr>
	
        <tr  class="">
          <td width="4" height="25" class=""></td>

          <td width="160" height="25" class="">&nbsp;&nbsp;��ǰ��</td>
          <td width="457" height="25" class="">
		<input  type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%=DataFormat.formatDisabledAmount(OBOperation.GetCurBalance(info.getNpayeracctid(), sessionMng.m_lCurrencyID),2)%>" readonly>
		&nbsp;&nbsp;&nbsp;&nbsp;����������ƣ�
		</font>
		<input class=box type="text"   name="bankname" size="20" value="<%= com.iss.itreasury.ebank.util.NameRef.getBankNameByAcctID(info.getNpayeracctid()) %>" readonly> 
		<input type="hidden" name="nPayerAccountID" size="16" value="" >
		  </td>
          <td width="129" colspan="4" class=""></td>
        </tr>
        <tr class="">
          <td colspan="4" height="1" class=""></td>
        </tr>
      </table>
	  <br>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> �տ����</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	  </table>
	   <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="">
          <td colspan="4" height="1" class=""></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class=""></td>
          <td width="130" height="25" class="">
            <p><span class="">&nbsp;&nbsp;&nbsp;��ʽ��</span></p>
          </td>
          <td width="590" height="25" class="">
           &nbsp;&nbsp;
			���л��
			<INPUT type="hidden" name="ntranstype" value="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>">
		  </td>
          <td width="1" height="25" class=""></td>
        </tr>
      </table>
	   <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>

		
		 <tr id="payeeAcctNoZoom" class="">
          <td height="25" width="1" class=""></td>
          <td height="25" width="5" class=""></td>
<!--         <input type="hidden" name="npayeeacctid" value="<%=info.getNpayeeacctid()%>">
          <input type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
 -->             <input type="hidden" name="npayeeacctid" value="">
          <input type="hidden" name="spayeeacctno" value="">
          
		<%
		//�տ�˺ŷŴ󾵣��㣩
		String[] sNextControlsEbank = {"mamount"};
		OBMagnifier.createPayeeAccountNOCtrl2(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"npayeeacctid","spayeeacctname","spayeeprov","spayeecity","spayeebankname","txtPayeeBankCNAPSNO","departmentnumber","bankconnectnumber","frmzjhb",info.getSpayeeacctno(),"sPayeeAcctNoZoom","<font color='#FF0000'>* </font>�տ�˺�"," nowrap width=\"130\" height=\"25\" class=\"MsoNormal\" "," height=\"25\" ",sNextControlsEbank,false);	
%>
		
          <td height="25" width="1" class=""></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="">
          <td height="1" colspan="6" class=""></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="">
          <td height="25" width="2" class=""></td>
          <td height="25" width="5" class=""></td>
		   <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>�տ���ƣ�</td>
		  <td height="25" colspan="3" class="">
		  	<textarea class=box name="spayeeacctname" value="<%=info.getSpayeeacctname()%>" cols="50"  onfocus="nextfield ='spayeeprov';" rows="2" ></textarea>
		 	&nbsp;&nbsp;&nbsp;
		 	<a href="#" onclick="doIllustration()"><font color="red">�������տ������Ϣ��ϸ˵��</font></a>
		  </td>
		  
		  <script>
          document.frmzjhb.spayeeacctname.value="<%=info.getSpayeeacctname()%>";
          </script>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeePlace" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>����أ�</td>
          <td height="25"  class="MsoNormal">
            <input class=box type="text" name="spayeeprov" value="<%=info.getSpayeeprov()%>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15">
            ʡ
            <input class=box type="text" name="spayeecity" value="<%=info.getSpayeecity()%>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15">
        �У��أ�</td>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>���������ƣ�</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="spayeebankname" value="<%=info.getSpayeebankname()%>" size="30">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">&nbsp; ������CNAPS�ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="txtPayeeBankCNAPSNO" value="<%=info.getBankCNAPSNo()==null?"":info.getBankCNAPSNo() %>" size="30">
			&nbsp;&nbsp;&nbsp;
			<a href="javascript:doLink()"><font color="red">CNAPS�ż���</font></a>
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;�������кţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="bankconnectnumber"  size="30" value="<%=info.getBankconnectnumber()==null?"":info.getBankconnectnumber()%>" maxlength="25">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;�����ţ�</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="departmentnumber"  size="30" value="<%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%>" maxlength="25">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> ��������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	  </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
       <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>��&nbsp;&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td height="25" width="590" class="MsoNormal">
            <script  language="JavaScript">
				var a = <%=info.getMamount()%>+"";
				createAmountCtrl("frmzjhb","mamount","<%=info.getMamount()%>","sNoteCtrl","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
				document.frmzjhb.mamount.value=formatAmount1(a);
			</script>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" align="right">��д���(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)��</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="box" name="sChineseAmount" size="30" 
			value="<%=ChineseCurrency.showChinese(String.valueOf(info.getMamount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal">&nbsp;&nbsp;�ύ���ڣ�</td>
          <td height="25" class="MsoNormal">
          <%=info.getDtexecute().toString().substring(0,10)%>
			<input class=box type="hidden" name="dtexecute" value="<%=info.getDtexecute().toString().substring(0,10)%>" onfocus="nextfield ='sNoteCtrl';" size="12">
			<!--<a class=calendar href="javascript:show_calendar('frmzjhb.dtexecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">-->
				<!--  -->
			<!--</a>-->
		  </td>
		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
			<script language="javascript">
				//frmzjhb.sNoteCtrl.value="<%=info.getSnote()%>";
			</script>          
<%
		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "frmzjhb";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " <font color=\"#FF0000\">* </font>�����;";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = info.getSnote();
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 12;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"add1"};
		
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
		strNextControlsAbstract);
%>          
          
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<%
				if(lSourceType==0)  //����
				{
			 %>
			<input class=button1 name=add1 type=button value=" �� �� " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'"> 
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="���沢�ύ����" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>"/>	
			<%
				}
				else  //����ָ���ѯ
				{
			 %>
			 <input class=button1 name=add1 type=button value=" �� �� " onClick="Javascript:addme1();"  onfocus="nextfield='submitfunction'"> 
			 <fs:obApprovalinitbutton controlName="approvalInit" 
				value="���沢�ύ����" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit1();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>"/>	
			 <%
			 	}
			  %>
			<INPUT type="hidden" name="act">
			
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			&nbsp;<input class=button1 name=add type=button value=" �� �� " onClick="Javascript:doreset();" >&nbsp; 
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
      
</form>
<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  ָ�ƿؼ�-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
<% } %>	
<script language="Javascript">
	
	function getNextField()
	{
	    frmzjhb.sPayeeAcctNoZoomCtrl.focus();
	}   

    /* �޸ı��洦���� */
    function addme()
    {
        frmzjhb.act.value="modify";	
		frmzjhb.action = "../control/c001.jsp?flag=save";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------���ָ����֤---��ʼ----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //ָ����֤
			$.ajax(
			{
				  type:'post',
				  url:"<%=strContext%>/fingerprintControl.jsp",
				  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
				  async:false,
				  success:function(returnValue){
				  	 var result = $(returnValue).filter("div#returnValue").text();
					 if(result=='success'){
						  fpFlag = true;
					 }
					 else if(result=="needFPCert"){
				  		getFingerPrint(frmzjhb,1);
						if($("#Ver").val()!=""){
					  	    addme();// �ٴ��ύ
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("ָ����֤���������²ɼ�");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//���ؿؼ�
						if($("#Ver").val()!=""){
					  	    addme();// �ٴ��ύ
						}
						fpFlag = false;
					}
					else{
						if(result != null && result != "null" && result != "" ){
							alert(result);	
							$("#Ver").val("");
							fpFlag = false;
						}else{
							fpFlag = true;
						}
					}
				  }
			}
			);
			if(!fpFlag){return;}
			<%}%>
			//-------------------���ָ����֤---����----------------	
			/* ȷ�ϱ��� */
			if (!confirm("�Ƿ񱣴棿"))
			{
				$("#Ver").val("");
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
     function addme1()
    {
        frmzjhb.act.value="modify";	
		frmzjhb.action = "../control/c001.jsp?src=<%=lSourceType%>&flag=save";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------���ָ����֤---��ʼ----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //ָ����֤
			$.ajax(
			{
				  type:'post',
				  url:"<%=strContext%>/fingerprintControl.jsp",
				  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
				  async:false,
				  success:function(returnValue){
				  	 var result = $(returnValue).filter("div#returnValue").text();
					 if(result=='success'){
						  fpFlag = true;
					 }
					 else if(result=="needFPCert"){
				  		getFingerPrint(frmzjhb,1);
						if($("#Ver").val()!=""){
					  	    addme1();// �ٴ��ύ
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("ָ����֤���������²ɼ�");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//���ؿؼ�
						if($("#Ver").val()!=""){
					  	    addme1();// �ٴ��ύ
						}
						fpFlag = false;
					}
					else{
						if(result != null && result != "null" && result != "" ){
							alert(result);	
							$("#Ver").val("");
							fpFlag = false;
						}else{
							fpFlag = true;
						}
					}
				  }
			}
			);
			if(!fpFlag){return;}
			<%}%>
			//-------------------���ָ����֤---����----------------	
			/* ȷ�ϱ��� */
			if (!confirm("�Ƿ񱣴棿"))
			{
				$("#Ver").val("");
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
    /* ȡ�������� */
    function cancelme()
    {
		if (confirm("ȷ��������"))
			{
				frmzjhb.reset();
			}
		
    }
    
        /* �޸��ύ������ */
    function doSaveAndApprovalInit()
    {
        frmzjhb.act.value="SaveAndApprovalmodify";	
		frmzjhb.action = "../control/c001.jsp?lID=<%=lID%>&flag=submit";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------���ָ����֤---��ʼ----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //ָ����֤
			$.ajax(
			{
				  type:'post',
				  url:"<%=strContext%>/fingerprintControl.jsp",
				  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
				  async:false,
				  success:function(returnValue){
				  	 var result = $(returnValue).filter("div#returnValue").text();
					 if(result=='success'){
						  fpFlag = true;
					 }
					 else if(result=="needFPCert"){
				  		getFingerPrint(frmzjhb,1);
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit();// �ٴ��ύ
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("ָ����֤���������²ɼ�");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//���ؿؼ�
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit();// �ٴ��ύ
						}
						fpFlag = false;
					}
					else{
						if(result != null && result != "null" && result != "" ){
							alert(result);	
							$("#Ver").val("");
							fpFlag = false;
						}else{
							fpFlag = true;
						}
					}
				  }
			}
			);
			if(!fpFlag){return;}
			<%}%>
			//-------------------���ָ����֤---����----------------	
			/* ȷ���ύ */
			if (!confirm("�Ƿ񱣴沢�ύ������"))
			{
				$("#Ver").val("");
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
    
     function doSaveAndApprovalInit1()
    {
        frmzjhb.act.value="SaveAndApprovalmodify";	
		frmzjhb.action = "../control/c001.jsp?lID=<%=lID%>&flag=submit&src=<%=lSourceType%>";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------���ָ����֤---��ʼ----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //ָ����֤
			$.ajax(
			{
				  type:'post',
				  url:"<%=strContext%>/fingerprintControl.jsp",
				  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
				  async:false,
				  success:function(returnValue){
				  	 var result = $(returnValue).filter("div#returnValue").text();
					 if(result=='success'){
						  fpFlag = true;
					 }
					 else if(result=="needFPCert"){
				  		getFingerPrint(frmzjhb,1);
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit1();// �ٴ��ύ
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("ָ����֤���������²ɼ�");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//���ؿؼ�
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit1();// �ٴ��ύ
						}
						fpFlag = false;
					}
					else{
						if(result != null && result != "null" && result != "" ){
							alert(result);	
							$("#Ver").val("");
							fpFlag = false;
						}else{
							fpFlag = true;
						}
					}
				  }
			}
			);
			if(!fpFlag){return;}
			<%}%>
			//-------------------���ָ����֤---����----------------	
			/* ȷ���ύ */
			if (!confirm("�Ƿ񱣴沢�ύ������"))
			{
				$("#Ver").val("");
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
    
    function doreset()
    {
          if(confirm("ȷ��������")){
          frmzjhb.reset();
          document.frmzjhb.spayeeacctname.value="<%=info.getSpayeeacctname()%>";
          document.frmzjhb.sPayerAccountNoZoomCtrl.value="<%=com.iss.itreasury.ebank.util.NameRef.getBankAcctNameByAcctID(info.getNpayeracctid())%>";
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
		if (frmzjhb.name.value == "")
		{
			alert("������Ʋ���Ϊ��");
			frmzjhb.name.focus();
			return false;
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("��ѡ�񸶿�˺�");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		if (frmzjhb.name.value < 0)
		{
			alert("����˺���ӷŴ���ȡ����");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
					
			if (frmzjhb.spayeeacctname.value == "")
			{
				alert("��ѡ���տ���ƻ��˺�");
				frmzjhb.spayeeacctname.focus();
				return false;
			}
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("��ѡ���տ���ƻ��˺�");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			//if (!IsAccountN0Int(frmzjhb.sPayeeAcctNoZoomCtrl.value))
			//{
			//	alert("�տ�ʻ����ֻ��������");
			//	frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			//	return false;
			//}
			if(frmzjhb.spayeecity.value != "")
			{
				var str = frmzjhb.spayeecity.value;
				str = str.substring(str.length-1,str.length);
				if(str=="��")
				{
					alert("��ȥ��������к�� '��' ");
					frmzjhb.spayeecity.focus();
					return false;
				}
			}
			
		
		if(!IsAccountN0Int(frmzjhb.bankconnectnumber.value)){
			alert("�������к�ֻ��������");
			frmzjhb.bankconnectnumber.focus();
			return false;
		}
		
		if(!IsAccountN0Int(frmzjhb.departmentnumber.value)){
			alert("������ֻ��������");
			frmzjhb.departmentnumber.focus();
			return false;
		}
		
        if(Trim(frmzjhb.spayeebankname.value) == "")
			{
			   alert("���������Ʋ���Ϊ��");
			   frmzjhb.spayeebankname.focus();
			   return false;
			
			}
      
		/* �������Ϸǿ�У�� */
		/* ���У�� */
		if(!checkAmount(frmzjhb.mamount, 1, "���׽��"))
		{
			return false;
		}
		if(Trim(frmzjhb.sNoteCtrl.value) == "")
			{
			   alert("�����;����Ϊ��");
			   frmzjhb.sNoteCtrl.focus();
			   return false;
			
			}

		/* ִ����У��
		if (!checkInterestExecuteDate(frmzjhb.dtexecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		} */
		/* �����; */
		if (!InputValid(frmzjhb.sNoteCtrl, 0, "textarea", 1, 0, 100,"�����;"))
		{
			return false;
		}
		/* ҵ��У�� */
		/* ���������׽�� */
		
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerCurrBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.mamount.value)) ;
		
		//���������׽�0������ʾ���������㣬���������뻮����
		if (dBalance < 0) 
		{
			alert("��ǰ���㣬���������뻮�����");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		
		if(frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeAcctNoZoomCtrl.value)
		{
			alert("������տ������ͬ");
			frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			return false;
		}

			var payNameLength = frmzjhb.spayeeacctname.value.replace(/[^\x00-\xff]/g,'**').length;
			if(payNameLength>80)
			{
				alert("�տ���Ƴ��Ȳ��ܴ���40������(80���ֽ�)!");
				frmzjhb.spayeeacctname.focus();
				return false;
			}
	
			if (!InputValid(frmzjhb.spayeeprov, 1, "string", 0, 0, 0,"���� ʡ"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.spayeecity, 1, "string",0, 0, 0,"����� ��(��)"))
			{
				return false;
			}
		

    	return true;
		
    }
	
function IsAccountN0Int( d_int)
{
		var checkOK = "0123456789";
		var checkStr = d_int;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		if (ch != ",")
			allNum += ch;
		}
		return (allValid)
 }	
 
function doIllustration()
{
	window.open('<%=strContext%>/illustation/illustration.jsp','','height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');
}
 
function doLink()
{    
	if(frmzjhb.spayeeprov.value.length <= 0)
	{
		alert("������ʡ�ݣ�");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+frmzjhb.spayeebankname.value+'&province='+frmzjhb.spayeeprov.value+'&city='+frmzjhb.spayeecity.value+'&recBankCode='+frmzjhb.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+frmzjhb.spayeebankname.value+'&bankName=spayeebankname', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}
 
</script>
<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	setFormName("frmzjhb");
	<%
	if(lSourceType==0)
	{
	%>
	//setSubmitFunction("addme(frmzjhb)");
	<%
	}
	else
	{
	%>
	//setSubmitFunction("addme1(frmzjhb)");
	<%
	}
	%>
</script>

<%
	}
	catch(IException ie){
		 OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
	}
	OBHtml.showOBHomeEnd(out);
%>
     <%@ include file="/common/SignValidate.inc" %>