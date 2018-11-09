<%--
/*
 * 程序名称：
 * 功能说明：银行汇款－－信息修改
 * 作　　者：baihuili
 * 日期：2006年09月15日
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
	/* 标题固定变量 */
	String strTitle = "[银行汇款]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	long lAbstractID = -1;
	long lSourceType = 0;//头信息显示
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
%>
<%


	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
     OBHtml.validateRequest(out,request,response);
		String strContext = request.getContextPath();
		OBBankPayInfo info=(OBBankPayInfo)request.getAttribute("info");
		long lID = info.getId();
		System.out.println("##################"+info);
        /* 显示文件头 */
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
<!--start  指纹认证相关html -->
<input name="Ver" id="Ver" type="hidden" value="">
<!--end  指纹认证相关html -->
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务处理</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2"> 付款方资料</td>
					<td width="800"><a class=lab_title3></td>
				</tr>
			</table>
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr  class="">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="">
		  <td width="4" height="25" class=""></td>
          <td width="130" height="25"  align="left"><font color="#FF0000">* </font>付款方名称：</td>
          <td width="457" height="25" class="">
            <input type="text" class="box" name="name" size="30" value="<%=info.getName()%>" readonly >
            <INPUT type="hidden" name="npayeracctid" value="<%=info.getNpayeracctid()%>">
            <input type="hidden" name="id" value="<%=info.getId()%>">
          </td>
          <td width="129" height="25" class=""></td>
        </tr>
       <tr  class="">
		<td width="4" height="25" class="MsoNormal"></td>
	<%OBMagnifier.createPayerBankAccountNoCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID,"npayeracctid","dPayerCurrBalance","dPayerUsableBalance","name","frmzjhb","","sPayerAccountNoZoom","<font color='#FF0000'>* </font>付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
		%>
		<script>
          document.frmzjhb.sPayerAccountNoZoomCtrl.value="<%=com.iss.itreasury.ebank.util.NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %>";
          </script>
		<td width="130" class="" ></td>
		 </tr>
	
        <tr  class="">
          <td width="4" height="25" class=""></td>

          <td width="160" height="25" class="">&nbsp;&nbsp;当前余额：</td>
          <td width="457" height="25" class="">
		<input  type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%=DataFormat.formatDisabledAmount(OBOperation.GetCurBalance(info.getNpayeracctid(), sessionMng.m_lCurrencyID),2)%>" readonly>
		&nbsp;&nbsp;&nbsp;&nbsp;付款方银行名称：
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
			<td class="lab_title2"> 收款方资料</td>
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
            <p><span class="">&nbsp;&nbsp;&nbsp;汇款方式：</span></p>
          </td>
          <td width="590" height="25" class="">
           &nbsp;&nbsp;
			银行汇款
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
		//收款方账号放大镜（汇）
		String[] sNextControlsEbank = {"mamount"};
		OBMagnifier.createPayeeAccountNOCtrl2(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"npayeeacctid","spayeeacctname","spayeeprov","spayeecity","spayeebankname","txtPayeeBankCNAPSNO","departmentnumber","bankconnectnumber","frmzjhb",info.getSpayeeacctno(),"sPayeeAcctNoZoom","<font color='#FF0000'>* </font>收款方账号"," nowrap width=\"130\" height=\"25\" class=\"MsoNormal\" "," height=\"25\" ",sNextControlsEbank,false);	
%>
		
          <td height="25" width="1" class=""></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="">
          <td height="1" colspan="6" class=""></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="">
          <td height="25" width="2" class=""></td>
          <td height="25" width="5" class=""></td>
		   <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25" colspan="3" class="">
		  	<textarea class=box name="spayeeacctname" value="<%=info.getSpayeeacctname()%>" cols="50"  onfocus="nextfield ='spayeeprov';" rows="2" ></textarea>
		 	&nbsp;&nbsp;&nbsp;
		 	<a href="#" onclick="doIllustration()"><font color="red">各银行收款方名称信息详细说明</font></a>
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
          <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入地：</td>
          <td height="25"  class="MsoNormal">
            <input class=box type="text" name="spayeeprov" value="<%=info.getSpayeeprov()%>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15">
            省
            <input class=box type="text" name="spayeecity" value="<%=info.getSpayeecity()%>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15">
        市（县）</td>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="spayeebankname" value="<%=info.getSpayeebankname()%>" size="30">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">&nbsp; 汇入行CNAPS号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="txtPayeeBankCNAPSNO" value="<%=info.getBankCNAPSNo()==null?"":info.getBankCNAPSNo() %>" size="30">
			&nbsp;&nbsp;&nbsp;
			<a href="javascript:doLink()"><font color="red">CNAPS号检索</font></a>
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;银行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="bankconnectnumber"  size="30" value="<%=info.getBankconnectnumber()==null?"":info.getBankconnectnumber()%>" maxlength="25">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;机构号：</td>
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
			<td class="lab_title2"> 划款资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	  </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
       <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>金额：&nbsp;&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
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
          <td width="130" height="25" class="MsoNormal" align="right">大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
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
          <td width="130" height="25"class="MsoNormal">&nbsp;&nbsp;提交日期：</td>
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
		String strTitleAbstract = " <font color=\"#FF0000\">* </font>汇款用途";
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
				if(lSourceType==0)  //新增
				{
			 %>
			<input class=button1 name=add1 type=button value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'"> 
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>"/>	
			<%
				}
				else  //申请指令查询
				{
			 %>
			 <input class=button1 name=add1 type=button value=" 保 存 " onClick="Javascript:addme1();"  onfocus="nextfield='submitfunction'"> 
			 <fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
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
			&nbsp;<input class=button1 name=add type=button value=" 重 置 " onClick="Javascript:doreset();" >&nbsp; 
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
      
</form>
<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  指纹控件-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
<% } %>	
<script language="Javascript">
	
	function getNextField()
	{
	    frmzjhb.sPayeeAcctNoZoomCtrl.focus();
	}   

    /* 修改保存处理函数 */
    function addme()
    {
        frmzjhb.act.value="modify";	
		frmzjhb.action = "../control/c001.jsp?flag=save";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------添加指纹认证---开始----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //指纹验证
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
					  	    addme();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    addme();// 再次提交
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
			//-------------------添加指纹认证---结束----------------	
			/* 确认保存 */
			if (!confirm("是否保存？"))
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
        	//-------------------添加指纹认证---开始----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //指纹验证
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
					  	    addme1();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    addme1();// 再次提交
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
			//-------------------添加指纹认证---结束----------------	
			/* 确认保存 */
			if (!confirm("是否保存？"))
			{
				$("#Ver").val("");
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (confirm("确定重置吗？"))
			{
				frmzjhb.reset();
			}
		
    }
    
        /* 修改提交处理函数 */
    function doSaveAndApprovalInit()
    {
        frmzjhb.act.value="SaveAndApprovalmodify";	
		frmzjhb.action = "../control/c001.jsp?lID=<%=lID%>&flag=submit";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        	//-------------------添加指纹认证---开始----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //指纹验证
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
					  	    doSaveAndApprovalInit();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit();// 再次提交
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
			//-------------------添加指纹认证---结束----------------	
			/* 确认提交 */
			if (!confirm("是否保存并提交审批？"))
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
        	//-------------------添加指纹认证---开始----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //指纹验证
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
					  	    doSaveAndApprovalInit1();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit1();// 再次提交
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
			//-------------------添加指纹认证---结束----------------	
			/* 确认提交 */
			if (!confirm("是否保存并提交审批？"))
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
          if(confirm("确定重置吗？")){
          frmzjhb.reset();
          document.frmzjhb.spayeeacctname.value="<%=info.getSpayeeacctname()%>";
          document.frmzjhb.sPayerAccountNoZoomCtrl.value="<%=com.iss.itreasury.ebank.util.NameRef.getBankAcctNameByAcctID(info.getNpayeracctid())%>";
          }
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */


		/* 付款方资料非空校验 */
		if (frmzjhb.name.value == "")
		{
			alert("付款方名称不能为空");
			frmzjhb.name.focus();
			return false;
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		if (frmzjhb.name.value < 0)
		{
			alert("付款方账号请从放大镜里取出！");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
					
			if (frmzjhb.spayeeacctname.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.spayeeacctname.focus();
				return false;
			}
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			//if (!IsAccountN0Int(frmzjhb.sPayeeAcctNoZoomCtrl.value))
			//{
			//	alert("收款方帐户编号只能是数字");
			//	frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			//	return false;
			//}
			if(frmzjhb.spayeecity.value != "")
			{
				var str = frmzjhb.spayeecity.value;
				str = str.substring(str.length-1,str.length);
				if(str=="市")
				{
					alert("请去掉汇入城市后的 '市' ");
					frmzjhb.spayeecity.focus();
					return false;
				}
			}
			
		
		if(!IsAccountN0Int(frmzjhb.bankconnectnumber.value)){
			alert("银行联行号只能是数字");
			frmzjhb.bankconnectnumber.focus();
			return false;
		}
		
		if(!IsAccountN0Int(frmzjhb.departmentnumber.value)){
			alert("机构号只能是数字");
			frmzjhb.departmentnumber.focus();
			return false;
		}
		
        if(Trim(frmzjhb.spayeebankname.value) == "")
			{
			   alert("汇入行名称不能为空");
			   frmzjhb.spayeebankname.focus();
			   return false;
			
			}
      
		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.mamount, 1, "交易金额"))
		{
			return false;
		}
		if(Trim(frmzjhb.sNoteCtrl.value) == "")
			{
			   alert("汇款用途不能为空");
			   frmzjhb.sNoteCtrl.focus();
			   return false;
			
			}

		/* 执行日校验
		if (!checkInterestExecuteDate(frmzjhb.dtexecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		} */
		/* 汇款用途 */
		if (!InputValid(frmzjhb.sNoteCtrl, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}
		/* 业务校验 */
		/* 可用余额－交易金额 */
		
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerCurrBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.mamount.value)) ;
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("当前余额不足，请重新输入划拨金额");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		
		if(frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeAcctNoZoomCtrl.value)
		{
			alert("付款方和收款方不能相同");
			frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			return false;
		}

			var payNameLength = frmzjhb.spayeeacctname.value.replace(/[^\x00-\xff]/g,'**').length;
			if(payNameLength>80)
			{
				alert("收款方名称长度不能大于40个汉字(80个字节)!");
				frmzjhb.spayeeacctname.focus();
				return false;
			}
	
			if (!InputValid(frmzjhb.spayeeprov, 1, "string", 0, 0, 0,"汇款地 省"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.spayeecity, 1, "string",0, 0, 0,"汇入地 市(县)"))
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
		alert("请输入省份！");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+frmzjhb.spayeebankname.value+'&province='+frmzjhb.spayeeprov.value+'&city='+frmzjhb.spayeecity.value+'&recBankCode='+frmzjhb.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+frmzjhb.spayeebankname.value+'&bankName=spayeebankname', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}
 
</script>
<script language="javascript">
	/* 页面焦点及回车控制 */
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