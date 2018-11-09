<%--
 页面名称 ：fi_v001.jsp
 页面功能 : 网上银行 - 逐笔付款 
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.iss.itreasury.safety.util.*"%>
<%@page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao" %> 
<%@ page import="com.iss.itreasury.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
String strTitle = "逐笔付款";
long lAbstractID = -1;
String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);

Timestamp systemDateTime = Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
String strContext = request.getContextPath();

try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	String sPayerCurrentBalance="";
   	String dPayerUsableBalance="";
	FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
	if(financeInfo!=null)
	{
		if(financeInfo.getSource()==SETTConstant.ExtSystemSource.EBANK)
		{
			financeInfo.setApplyCode("");
		}
	}


	
	String temp = "";
	String newOpen = "";
	temp = (String)request.getAttribute("newOpen");
	if(temp!=null&&temp.trim().length()>0)
	{
		newOpen = temp;
	}
	
	String submitAgain = "";
	temp = (String)request.getAttribute("submitAgain");
	if(temp!=null&&temp.trim().length()>0)
	{
		submitAgain = temp;
	}
	

	



	if(financeInfo == null){
		financeInfo = new FinanceInfo();
		financeInfo.setID(-1);
		financeInfo.setSBatchNo("");
		
		financeInfo.setPayerAcctID(-1);
		financeInfo.setPayerAcctNo("");
		financeInfo.setCurrentBalance(0.0);
		financeInfo.setUsableBalance(0.0);
		
		financeInfo.setRemitType(OBConstant.SettRemitType.BANKPAY);
		financeInfo.setPayeeAcctID(-1);
		financeInfo.setPayeeAcctNo("");
		financeInfo.setPayeeName("");
		financeInfo.setPayeeProv("");
		financeInfo.setPayeeCity("");
		financeInfo.setPayeeBankName("");
		
		financeInfo.setAmount(0.0);
		financeInfo.setExecuteDate(systemDateTime);
		financeInfo.setNote("");
		
		financeInfo.setRemitArea(-1);
		financeInfo.setRemitSpeed(-1);
		financeInfo.setSource(SETTConstant.ExtSystemSource.EBANK);
		financeInfo.setOfficeID(sessionMng.m_lOfficeID);
		
	}
	
	    sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
	    dPayerUsableBalance = financeInfo.getFormatUsableBalance();
	    System.out.print(dPayerUsableBalance);
        if (dPayerUsableBalance==null || dPayerUsableBalance.trim().length()==0) 
		{	
			dPayerUsableBalance="0.00";
		}
	
		String strPayeeAcctNo="";
		String strBankPayeeAcctNo="";
		//解决BUG #14291::（78:9080站点）网银—申请指令查询（银行汇款），修改“收款方账号”后，保存的信息还是修改前信息。 qushuang 2012-08-23
		if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
		{
			//银行汇款
			strBankPayeeAcctNo=financeInfo.getPayeeAcctNo();
		}
		else{
			//内部转账
			strPayeeAcctNo=financeInfo.getPayeeAcctNo();
		}
		//解决BUG #14291 end
%>
<% 

		
         String saccountno_zhubi="";
        long payerAcctID=-1;
        String modify = "";
        String lmodify="";
		lmodify=(String)request.getAttribute("modify");
		if(lmodify!=null&&lmodify.trim().length()>0)
		{modify=lmodify;}     
        FinanceInfo info=new  FinanceInfo();
        OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	 	info.setClientID(sessionMng.m_lClientID);
        info.setCurrencyID(sessionMng.m_lCurrencyID);
        info.setNUserID(sessionMng.m_lUserID);
        info.setOfficeID(sessionMng.m_lOfficeID);
	 	info =obFinanceInstrBiz.query_OBMagnifier1(info);
	 	payerAcctID=financeInfo.getPayerAcctID();
	 	saccountno_zhubi=financeInfo.getPayerAcctNo();
 		  if(info!=null)
 		  {
	 		payerAcctID=info.getNAccountID();
	 		saccountno_zhubi=info.getSaccountno_zhubi();
	 		 if(!modify.equals("modify"))
	        {
	 			dPayerUsableBalance=info.getCurrentbalance_zhubi();
	 			System.out.print(dPayerUsableBalance);
	 		}
	 		sPayerCurrentBalance=info.getN_balance_zhubi();
 		  }
 		  
 		  String lupdate = null;
		  String update = "";
		  lupdate = request.getParameter("update");
		  if(lupdate!=null&&lupdate.trim().length()>0)
			{
				update = lupdate;
			}
	
	
		//查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
		String strTemp = "";
		String isConsignReceive = "false";
		strTemp = (String)request.getAttribute("isConsignReceive");
		if(strTemp != null && !strTemp.equals("")){
		isConsignReceive = strTemp;
	}
	
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>


<script language="JavaScript">
<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) ) { %>
	<!--
	//控制不同付款方式的显示
	
	function disRemitType(form){
		var remitType = form.nRemitType.value;
		var oTrBankpay = document.getElementById("trBankpay");
		var oTrInternalvirement = document.getElementById("trInternalvirement");
		var oTrPayeeProv = document.getElementById("trPayeeProv");
		var oTrPayeeBankName = document.getElementById("trPayeeBankName");
		var oTrSaveBankpay = document.getElementById("trSaveBankpay");
		var oTrSaveInternalvirement = document.getElementById("trSaveInternalvirement");
		var oTrBankpayim = document.getElementById("trBankpayim");
		var oTrPayeeBankCNAPSNO = document.getElementById("trPayeeBankCNAPSNO");
		var oTrPayeeBankOrgNO = document.getElementById("trPayeeBankOrgNO");
		var oTrPayeeBankExchangeNO = document.getElementById("trPayeeBankExchangeNO");
		var illustration = document.getElementById("illustration");

		if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
			oTrBankpay.style.display = "block";
			oTrBankpayim.style.display = "block";
			oTrInternalvirement.style.display = "none";
			oTrPayeeProv.style.display = "block";
			oTrPayeeBankName.style.display = "block";
			oTrPayeeBankCNAPSNO.style.display = "block";
			oTrPayeeBankOrgNO.style.display = "block";
			oTrPayeeBankExchangeNO.style.display = "block";
			oTrSaveBankpay.style.display = "block";
			oTrSaveInternalvirement.style.display = "none";
			illustration.style.display = "";
			form.sPayeeName.readOnly = "";
		}
		
		if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
			oTrBankpay.style.display = "none";
			oTrBankpayim.style.display = "none";
			oTrInternalvirement.style.display = "block";
			oTrPayeeProv.style.display = "none";
			oTrPayeeBankName.style.display = "none";
			oTrPayeeBankCNAPSNO.style.display = "none";
			oTrPayeeBankOrgNO.style.display = "none";
			oTrPayeeBankExchangeNO.style.display = "none";
			oTrSaveBankpay.style.display = "none";
			oTrSaveInternalvirement.style.display = "block";
			illustration.style.display = "none";
			form.sPayeeName.readOnly = "readOnly";
		}
	}
	
	//控制不同付款方式的文本显示
	function changeRemitType(form){
		disRemitType(form);
		var remitType = form.nRemitType.value;
		if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
			form.sPayeeBankNoZoomCtrl.value = "";
			form.sPayeeName.value = "";
			form.sPayeeProv.value = "";
			form.sPayeeCity.value = "";
			form.sPayeeBankName.value = "";
			form.txtPayeeBankCNAPSNO.value = "";
			form.sPayeeBankOrgNO.value = "";
			form.sPayeeBankExchangeNO.value = "";
			form.sApplyCode.value = "";
			illustration.style.display = "";
			form.remitArea[0].checked = true;
			form.remitSpeed.checked = false;
		}
		
		if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
			form.sPayeeAcctNoZoomCtrl.value = "";
			form.sPayeeName.value = "";
			form.sPayeeProv.value = "";
			form.sPayeeCity.value = "";
			form.sPayeeBankName.value = "";
			form.txtPayeeBankCNAPSNO.value = "";
			form.sPayeeBankOrgNO.value = "";
			form.sPayeeBankExchangeNO.value = "";
			form.sApplyCode.value = "";
			illustration.style.display = "none";
			form.remitArea[0].checked = true;
			form.remitSpeed.checked = false;
		}
	}

	//-->
<% } else { %>
	<!--
	//控制不同付款方式的显示
	
	function disRemitType(form){
		var remitType = form.nRemitType.value;
		var oTrBankpay = document.getElementById("trBankpay");
		var oTrInternalvirement = document.getElementById("trInternalvirement");
		var oTrPayeeProv = document.getElementById("trPayeeProv");
		var oTrPayeeBankName = document.getElementById("trPayeeBankName");
		var oTrSaveBankpay = document.getElementById("trSaveBankpay");
		var oTrSaveInternalvirement = document.getElementById("trSaveInternalvirement");
		var oTrPayeeBankCNAPSNO = document.getElementById("trPayeeBankCNAPSNO");
		var oTrPayeeBankOrgNO = document.getElementById("trPayeeBankOrgNO");
		var oTrPayeeBankExchangeNO = document.getElementById("trPayeeBankExchangeNO");
		var oTrApplyCode = document.getElementById("trApplyCode");
		var illustration = document.getElementById("illustration");
		if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
			oTrBankpay.style.display = "block";
			oTrInternalvirement.style.display = "none";
			oTrPayeeProv.style.display = "block";
			oTrPayeeBankName.style.display = "block";
			oTrPayeeBankCNAPSNO.style.display = "block";
			oTrPayeeBankOrgNO.style.display = "block";
			oTrPayeeBankExchangeNO.style.display = "block";
			oTrSaveBankpay.style.display = "block";
			oTrSaveInternalvirement.style.display = "none";
			illustration.style.display = "";
			form.sPayeeName.readOnly = "";
		}
		
		if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
			oTrBankpay.style.display = "none";
			oTrInternalvirement.style.display = "block";
			oTrPayeeProv.style.display = "none";
			oTrPayeeBankName.style.display = "none";
			oTrPayeeBankCNAPSNO.style.display = "none";
			oTrPayeeBankOrgNO.style.display = "none";
			oTrPayeeBankExchangeNO.style.display = "none";
			oTrSaveBankpay.style.display = "none";
			oTrSaveInternalvirement.style.display = "block";
			illustration.style.display = "none";
			form.sPayeeName.readOnly = "readOnly";
		}
	}
	
	//控制不同付款方式的文本显示
	function changeRemitType(form){
		disRemitType(form);
		var remitType = form.nRemitType.value;
		if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
			form.sPayeeBankNoZoomCtrl.value = "";
			form.sPayeeName.value = "";
			form.sPayeeProv.value = "";
			form.sPayeeCity.value = "";
			form.sPayeeBankName.value = "";
			form.txtPayeeBankCNAPSNO.value = "";
			form.sPayeeBankOrgNO.value = "";
			form.sPayeeBankExchangeNO.value = "";
			form.sApplyCode.value = "";
		}
		
		if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
			form.sPayeeAcctNoZoomCtrl.value = "";
			form.sPayeeName.value = "";
			form.sPayeeProv.value = "";
			form.sPayeeCity.value = "";
			form.sPayeeBankName.value = "";
			form.txtPayeeBankCNAPSNO.value = "";
			form.sPayeeBankOrgNO.value = "";
			form.sPayeeBankExchangeNO.value = "";
			form.sApplyCode.value = "";
		}
	}
	
	
	//-->
<% } %>
		
	function displayApplyCode(form)
	{
		var oTrApplyCode = document.getElementById("trApplyCode");
		var sourceType = form.sApplyCodeSource.value;
		if(sourceType=="<%=SETTConstant.ExtSystemSource.EBANK%>")
		{
			oTrApplyCode.style.display = "none";
		}else
		{
			oTrApplyCode.style.display = "block";
		}
		
	}
	
	function changeApplyCode(form)
	{
		displayApplyCode(form);
		var sourceType = form.sApplyCodeSource.value;
		if(sourceType=="<%=SETTConstant.ExtSystemSource.EBANK%>")
		{
			form.sApplyCode.value="";
		}
		
	}

</script>
<safety:security/>
<form name="form1" method="post" action="../control/fi_c002.jsp">
<safety:certInformation/>
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v003.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.SettInstrStatus.SAVE%>">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo()%>">
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="confirmUserID" value="<%=sessionMng.m_lUserID%>">
<input type="hidden" name="systemDateTime" value="<%=DataFormat.getDateString(systemDateTime)%>">
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
<input type="hidden" name="flag" value="">	
<input type="hidden" name="submitAgain" value="<%=submitAgain %>">
<input type="hidden" name="lOfficeId" value="<%=sessionMng.m_lOfficeID %>">
<input type="hidden" name="lCurrencyId" value="<%=sessionMng.m_lCurrencyID %>">
<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>">
<input type="hidden" name="newOpen" value="<%=newOpen%>">
<input type="hidden" name="timestamp" value="<%=System.nanoTime() %>">


<!--start  指纹认证相关html -->
<input name="Ver" id="Ver" type="hidden" value="">
<!--end  指纹认证相关html -->
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
		</td>
	</tr>

	<tr>
		<td valign="top">
			<table  border="0" cellspacing="0" cellpadding="0" align="">
			
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" nowrap> 付款方资料</td>
				<td width="683"><a class=lab_title3></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 付款方名称：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sPayerName" size="32" value="<%=NameRef.getClientNameByID(sessionMng.m_lClientID)%>" readonly>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td width="4" height="25">&nbsp;</td>
					<td width="130" height="25" align="left" nowrap>&nbsp;&nbsp;付款方账号：</td>
					<td>
 						<fs:dic id="sPayerAccountNoZoomCtrl" size="22" form="form1" title="付款方账号" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams='form1.sPayerAccountNoZoomCtrl.value,form1.confirmUserID.value,form1.lClientID.value,form1.lCurrencyId.value,form1.lInstructionID.value' value="<%=saccountno_zhubi %>" nextFocus="nRemitType" width="500">
							<fs:columns> 
								<fs:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
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
						</td>
					<%/*     
						//付款方账号放大镜
						OBMagnifier.createPayerAccountNoCtrlByDate(out,
															sessionMng.m_lUserID,
															sessionMng.m_lCurrencyID,
															financeInfo.getOfficeID(),
															financeInfo.getID(),
															sessionMng.m_lClientID,
															"payerAcctID",
															"dPayerCurrBalance",
															"dPayerUsableBalance",
															"tsExecute",
															"form1",
															saccountno_zhubi,
															"sPayerAccountNoZoom",
															"<font color='#FF0000'>* </font>付款方账号",
															" width=\"130\" height=\"25\" align=\"left\"",
															" width=\"500\" height=\"25\"",
															new String[]{"nRemitType"});	
					*/%>		
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
					<%
						OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
						long lOffice = -1;
						if(payerAcctID>0)
						{
							lOffice = obFinanceInstrDao.findOfficeByAccountId(payerAcctID);
						}
						else
						{
							lOffice = sessionMng.m_lOfficeID;
						}
						Timestamp opendate = Env.getSystemDate(lOffice,sessionMng.m_lCurrencyID);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String strOpenDate = sdf.format(opendate);
					 %>
				
		 		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;	<input type="hidden" name='hiddenOpendate' value="<%=request.getParameter("oldOpenDate")==null?strOpenDate:request.getParameter("oldOpenDate") %>"></td>
          			<td width="130" height="25"align="left" nowrap>&nbsp;&nbsp;当前余额：</td>
          			<td width="500" height="25">
						<fs:amount 
				       		form="form1"
			       			name="dPayerCurrBalance"
			       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(sPayerCurrentBalance))%>"
			       			readonly="true"
			       			nextFocus="dPayerUsableBalance"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left" nowrap>&nbsp;&nbsp;可用余额：</td>
          			<td width="500" height="25">
		  			    <fs:amount 
				       		form="form1"
			       			name="dPayerUsableBalance"
			       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(dPayerUsableBalance))%>"
			       			readonly="true"
			       			nextFocus="nRemitType"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		       			<input type="hidden" name="payerAcctID" size="20" value="<%=payerAcctID %>" >
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table  border="0" cellspacing="0" cellpadding="0" align="">
			
			    <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" nowrap> 收款方资料</td>
				<td width="683"><a class=lab_title3></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 汇款方式：</td>
		          	<td width="500" height="25">
				  	<%
				  		//汇款方式
				  		OBConstant.SettRemitType.showList(out,
				  										  "nRemitType",
				  										  1,
				  										  financeInfo.getRemitType(),
				  										  false,
				  										  false,
				  										  "onchange='changeRemitType(form)'",
				  										  sessionMng.m_lOfficeID,
			  											  sessionMng.m_lCurrencyID);
				  	%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
			  	<tr id="trBankpay">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 收款方账户：</td>
		          	<td width="500" height="25">
						<fs:dic id="sPayeeBankNoZoomCtrl" size="22" form="form1" title="收款方账户" sqlFunction="getPayeeAccountNOSQL"  sqlParams="false,form1.lClientID.value,form1.lCurrencyId.value,form1.sPayeeBankNoZoomCtrl.value,form1.sPayeeName.value" nextFocus="amount" width="900" value="<%=strBankPayeeAcctNo%>" width="850">
							<fs:columns>
								<fs:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
								<fs:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="汇入地（省）" name="sPayeeProv" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="汇入地（市）" name="spayeecity" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="汇入行名称" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="联行号" name="spayeebankexchangeno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="CNAPS号" name="spayeebankcnapsno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
								<fs:column display="机构号" name="spayeebankorgno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="nPayeeAccountID" name="id" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sPayeeBankNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeProv" name="sPayeeProv" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeCity" name="sPayeeCity" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankExchangeNO" name="spayeebankexchangeno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="txtPayeeBankCNAPSNO" name="spayeebankcnapsno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankOrgNO" name="spayeebankorgno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								
							</fs:pageElements>							
						</fs:dic> 
					</td>
						<%/*
						//收款方账号放大镜（银行汇款）
						String[] sNextControlsEbank = {"amount"};
						OBMagnifier.createPayeeAccountNOCtrl2(out,
															sessionMng.m_lCurrencyID,
															sessionMng.m_lClientID,
															"nPayeeAccountID",
															"sPayeeName",
															"sPayeeProv",
															"sPayeeCity",
															"sPayeeBankName",
															"txtPayeeBankCNAPSNO",
															"sPayeeBankOrgNO",
															"sPayeeBankExchangeNO",
															"form1",
															financeInfo.getPayeeAcctNo(),
															"sPayeeBankNoZoom",
															"<font color='#FF0000'>* </font> 收款方账号",
															" width=\"130\" height=\"25\" align=\"left\"",
															" width=\"500\" height=\"25\"",
															sNextControlsEbank,
															false);*/
					%>	
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
 		
			  	<tr id="trInternalvirement">
					<td width="4" height="25">&nbsp;</td>
					<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 收款方账户：</td>
		          	<td width="500" height="25">
						<fs:dic id="sPayeeAcctNoZoomCtrl" size="22" form="form1" title="收款方账户" sqlFunction="getPayeeBankNOSQL1"  sqlParams="false,-1,form1.lCurrencyId.value,form1.sPayeeAcctNoZoomCtrl.value,form1.sPayeeName.value" nextFocus="" width="450" value="<%=strPayeeAcctNo%>" width="500" >
							<fs:columns>
								<fs:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nPayeeAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sPayeeName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="hidsPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="hidsPayeeName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
					<%/*
						//收款方账号放大镜（内部转账）
						OBMagnifier.createPayeeBankNOCtrl1(out,
														   sessionMng.m_lCurrencyID,
														   //update by dwj 20111104 BUG #9447
														   //sessionMng.m_lOfficeID,
														   -1,
														   //end update by dwj 20111104
														   "nPayeeAccountID",
														   "sPayeeName",
														   "form1",
														   financeInfo.getPayeeAcctNo(),
														   "sPayeeAcctNoZoom",
														   "<font color='#FF0000'>* </font>收款方账号",
														   " width=\"130\" height=\"25\" align=\"left\"",
														   " width=\"500\" height=\"25\"");	
					*/
					%>	
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 收款方名称：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sPayeeName" value="<%=financeInfo.getPayeeName()%>" size="32">
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeProv">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 汇入地：</td>
		          	<td width="500" height="25" nowrap>
			            <input type="text" class="box" name="sPayeeProv" onblur="dropSpace(this)" value="<%=financeInfo.getPayeeProv()%>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15"> 省
			            <input type="text" class="box" name="sPayeeCity" onblur="dropSpace(this)" value="<%=financeInfo.getPayeeCity()%>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15"> 市（县）
		          		<span id="illustration"><a href="#" onclick="doIllustration()"><font color="red">各银行收款方名称信息详细说明</font></a></span>
	          		</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeBankName">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap><font color="#FF0000">*</font> 汇入行名称：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" id="sPayeeBankName" name="sPayeeBankName" value="<%=financeInfo.getPayeeBankName()%>" size="32" >
		          	
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        
		         <tr id="trPayeeBankCNAPSNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap>&nbsp; 汇入行CNAPS号：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" id="txtPayeeBankCNAPSNO" name="txtPayeeBankCNAPSNO" value="<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>" size="32"  maxlength="50">
		          		&nbsp;&nbsp;
		          		<a href="javascript:doLink()"><font color="red">CNAPS号检索</font></a>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        
		         <tr id="trPayeeBankOrgNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap>&nbsp; 汇入行机构号：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" name="sPayeeBankOrgNO" value="<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>" size="32"  maxlength="50">
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        
		         <tr id="trPayeeBankExchangeNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" nowrap>&nbsp; 汇入行联行号：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" name="sPayeeBankExchangeNO" value="<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>" size="32"  maxlength="50">
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table  border="0" cellspacing="0" cellpadding="0" align="">
			   <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" nowrap> 划款资料</td>
				<td width="683"><a class=lab_title3></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 金额：</td>
		          	<td width="500" height="25">
		          		<fs:amount 
				       		form="form1"
			       			name="amount"
			       			value="<%=financeInfo.getAmount()%>"
			       			chineseCtrlName="sChineseAmount"
			       			nextFocus="sApplyCodeSource"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">&nbsp;&nbsp;大写金额：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">&nbsp;&nbsp;数据来源：</td>
		          	<td width="500" height="25">
		            	 <%
		            	 	SETTConstant.ExtSystemSource.ShowList(out,"sApplyCodeSource",financeInfo.getSource(),false,false,"onchange='changeApplyCode(form)'","onfocus=\"nextfield='tsExecute'\"");
		            	  %>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		         <tr id="trApplyCode">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 外部系统指令序号：</td>
		          	<td width="500" height="25">
		            	 <input class="box" type="text" name="sApplyCode" value="<%=financeInfo.getApplyCode()==null?"": financeInfo.getApplyCode()%>" size="32"  maxlength="50">

		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        
		        <!-- Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"，使用手续费的配置文件 -->
		        <%
		        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) )
		        	{
		        %>
		        	<tr id="trBankpayim">
						<td width="4" height="25">&nbsp;</td>
			          	<td width="130" height="25" nowrap>&nbsp;&nbsp;汇款区域/速度：</td>
			          	<td width="500" height="25" nowrap>
			            	<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.NATIVE %>" 
							<%if(financeInfo.getRemitArea() == -1){out.println("checked");}else{ if(financeInfo.getRemitArea() == Constant.remitAreaType.NATIVE){out.println("checked");} }%>> 本地
							<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.DEVIATIONISM %>" 
							<%if(financeInfo.getRemitArea() == Constant.remitAreaType.DEVIATIONISM){out.println("checked");}%>> 异地&nbsp;&nbsp;&nbsp;&nbsp;
			            	
			            	<input type="checkbox" name="remitSpeed" <% if(financeInfo.getRemitSpeed() == Constant.remitSpeedType.RAPID) { out.print("checked"); } %> 
			            	onfocus="nextfield ='tsExecute';">是否加急
			          	</td>
			          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
			          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        	</tr>
		        <%
		        	}
		        %>
 				<tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 执行日：</td>
		          	<td width="500" height="25">
			          	<fs:calendar 
			          	    name="tsExecute"
				          	value="<%=financeInfo.getFormatExecuteDate()%>" 
				          	properties="nextfield ='sNoteCtrl'" 
				          	size="20"/>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="4" height="25">&nbsp;</td>
		        	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇款用途：</td>	
		        	<td width="500" height="25">
						<fs:dic id="sNoteCtrl" form="form1" title="汇款用途" sqlFunction="getAbstractSettingSQL" sqlParams="form1.lOfficeId.value,form1.lUserID.value,form1.sNoteCtrl.value" nextFocus="butBankpaySave" width="500" value="<%=financeInfo.getNote() %>" type="textarea" row="2" col="55" needRemind="true" maxlength="30" properties="" position="top">
							<fs:columns>
								<fs:column display="摘要代号" name="AbstractCode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="摘要描述" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sNote" name="AbstractID" type="<%=PagerTypeConstant.STRING %>" elType="hidden"/>
								<fs:pageElement elName="sNoteCtrl" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" elType="text"/>
							</fs:pageElements>
						</fs:dic>
		        	</td>	    
		        	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>    
		        	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>	
		        </tr>


<%
/*		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "form1";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " <font color=\"#FF0000\">* </font>汇款用途";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = financeInfo.getNote();
		String strFirstTDAbstract = " style=\"width:14%\" ";
		String strSecondTDAbstract = "";
		long maxLength = 30;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"butBankpaySave"};
		
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

				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr id="trSaveBankpay">
					<td width="*%" align="right">
						<input type="button" value=" 保 存 " class="button1" name="butBankpaySave" onclick="toSave(form1)"/>&nbsp;
					  	<fs:obApprovalinitbutton controlName="butBankpayApprovalInit"
					 										 value="保存并提交审批"
															 classType="button1"
															 onClickMethod="doSaveAndApprovalInit(form1);"
															 officeID="<%=sessionMng.m_lOfficeID%>"
															 clientID="<%=sessionMng.m_lClientID%>"
															 currencyID="<%=sessionMng.m_lCurrencyID%>"
															 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
															 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" />&nbsp;
						<input type="button" value=" 重 置 " class="button1" name="butBankpayReset" onclick="toReset(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr id="trSaveInternalvirement">
					<td width="*%" align="right">
						<input type="button" value=" 保 存 " class="button1" name="butInternalvirementSave" onclick="toSave(form1)"/>&nbsp;
					  	<fs:obApprovalinitbutton controlName="butInternalvirementApprovalInit"
					 										 value="保存并提交审批"
															 classType="button1"
															 onClickMethod="doSaveAndApprovalInit(form1);"
															 officeID="<%=sessionMng.m_lOfficeID%>"
															 clientID="<%=sessionMng.m_lClientID%>"
															 currencyID="<%=sessionMng.m_lCurrencyID%>"
															 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
															 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>" />&nbsp;
						<input type="button" value=" 重 置 " class="button1" name="butInternalvirementReset" onclick="toReset(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</tbody>
</table>
</form>
<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  指纹控件-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width="140" height="180" border="1"></OBJECT>
<% } %>		
<script language="javascript">
/* 页面焦点及回车控制 */

firstFocus(form1.sPayerName);
setFormName("form1");
disRemitType(form1);
displayApplyCode(form1);
$(
	function(){
		var strBatchNo = "<%=financeInfo.getSBatchNo() %>"
		if(strBatchNo!="")
		{
			$("input[name='butBankpayApprovalInit']").css("display","none");
			$("input[name='butInternalvirementApprovalInit']").css("display","none");
		}
	}
);
function toSave(form)
{
	form.action = "../control/fi_c002.jsp";
	form.strSuccessPageURL.value = "../view/fi_v003.jsp?oldOpenDate="+form.hiddenOpendate.value;
	form.strFailPageURL.value = "../view/fi_v001.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.SAVE%>";
	form.flag.value = "save";
	if (!validate(form)) return;
 	
	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) ) { %>
 	if(form.remitSpeed.checked == true)
 	{
 		form.remitSpeed.value = "<%=Constant.remitSpeedType.RAPID %>";
 	}
 	else
 	{
 		form.remitSpeed.value = "<%=Constant.remitSpeedType.GENERAL %>";
 	}
 	<% } %>

    //网银数字签名 
    if(security.isSign())
    {
    	var format = new CaptransferOperator();
    	sign(format,form);
    }
    
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
		  		getFingerPrint(form,1);
				if($("#Ver").val()!=""){
			  	    toSave(form);// 再次提交
				}
				fpFlag = false;
			 }
			 else if(result=="fpiswrong"){
		  		alert("指纹认证错误，请重新采集");	
				$("#Ver").val("");
			  	getFingerPrint(form,1);//加载控件
				if($("#Ver").val()!=""){
			  	    toSave(form);// 再次提交
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
	
    if(confirm("是否保存？"))
	{	
		if ("<%=isConsignReceive%>"=="true") {
			form.action = "<%=strContext%>/project/wisgfc/special/control/consignReceiveConfirm.jsp";
			form.strSuccessPageURL.value = "<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp";
			form.strFailPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
			form.strAction.value = "<%=OBConstant.Actions.MATCHSEARCH%>";
		}
		
		showSending();
	    form.submit();
	}else{
		$("#Ver").val("");
	}
}

function doSaveAndApprovalInit(form)
{
	form.action = "../control/fi_c002.jsp";
	form.strSuccessPageURL.value = "../view/fi_v003.jsp";
	form.strFailPageURL.value = "../view/fi_v001.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
	form.flag.value = "saveAndApproval";
	if (!validate(form)) return;
 	
	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) ) { %>
 	if(form.remitSpeed.checked == true)
 	{
 		form.remitSpeed.value = "<%=Constant.remitSpeedType.RAPID %>";
 	}
 	else
 	{
 		form.remitSpeed.value = "<%=Constant.remitSpeedType.GENERAL %>";
 	}
 	<% } %>

    //网银数字签名 
    if(security.isSign())
    {
    	var format = new CaptransferOperator();
    	sign(format,form);
    }

	
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
		  		getFingerPrint(form,1);
				if($("#Ver").val()!=""){
			  	    doSaveAndApprovalInit(form);// 再次提交
				}
				fpFlag = false;
			 }
			 else if(result=="fpiswrong"){
		  		alert("指纹认证错误，请重新采集");	
				$("#Ver").val("");
			  	getFingerPrint(form,1);//加载控件
				if($("#Ver").val()!=""){
			  	    doSaveAndApprovalInit(form);// 再次提交
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
	
	if(confirm("是否保存并提交审批？"))
	{
		if ("<%=isConsignReceive%>"=="true") {
			form.action = "<%=strContext%>/project/wisgfc/special/control/consignReceiveConfirm.jsp";
			form.strSuccessPageURL.value = "<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp";
			form.strFailPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
			form.strAction.value = "<%=OBConstant.Actions.MATCHSEARCH%>";
		}
		
		showSending();
	    form.submit();
	}else{
		$("#Ver").val("");
	}
}

function toReset(form){
	form.reset();
	disRemitType(form);
	
}

//Added by ylguo(郭英亮)去掉汇入地的两边空格
function dropSpace(obj){
	var s = obj.value;
	var ss = "";
	ss = Trim(s);
	obj.value = ss;
}
//Added by ylguo at 2009-01-06,对文本框内容判断是否为全角
/**
 *obj ：一个页面元素对象
 *textTitle ：字符串，文本框标题
 *return : 文本框内容有全角字符，返回false，否则返回值true
*/
function quanjiao(obj,textTitle)
{
    if(obj != null && obj.value != "")
       {	
       		var sss1 = obj.value;
       		var ss2 = new String(textTitle);
       		ss2 = ss2+' 不能输入全角字符，请输入半角字符';
       		if (sss1.length>0)
		    {
		       for (var i = 0; i < sss1.length; i++)
		        {
		            unicode=sss1.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert(ss2);
					    obj.select();
						return false;
						break;
					}
				}
				//return true;
			}
       	}
      return true;
}



function validate(form){

	if(!InputValid(form.sPayerAccountNoZoomCtrl, 1, "string", 1, 1, 50,"付款方账号")){
		return false;
	}

	if(form.payerAcctID.value == "-1"){
		alert("付款方账号请用放大镜选择！");
		form.sPayerAccountNoZoomCtrl.focus();
		return false;
	}
	
	var remitType = form.nRemitType.value;

	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){

		if(!InputValid(form.sPayeeBankNoZoomCtrl, 1, "string", 1, 1, 50,"收款方账号")){
			return false;
		}

		/////特殊校验收款方名称 ----- start -----
		
		//if(!InputValid(form.sPayeeName, 1, "string", 1, 1, 15, "收款方名称")){
		//	return false;
		//}
	   	if(form.sPayeeName.value.length==0) 
	    {
	       alert("请输入收款方名称");
	       form.sPayeeName.focus();
	       return false;
	    }
		if(form.sPayeeName.value.indexOf("'")>0)
		{
		   alert("收款方名称中不能包含非法字符！");
	       form.sPayeeName.focus();
	       return false;
		}
		//if(!(1<=form.sPayeeName.value.length && form.sPayeeName.value.length<=15))
		//{
		//   form.sPayeeName.value = form.sPayeeName.value.substring(0,15);
		//   alert(form.sPayeeName.value);
		//}
		// Added by ylguo(郭英亮) at 2009-02-17解决收款方名称的问题

		// Modifying ended by ylguo at 2009-02-17
		
		/////特殊校验收款方名称 ----- end -----
		
		var payNameLength = form.sPayeeName.value.replace(/[^\x00-\xff]/g,'**').length;
		if(payNameLength>80)
		{
			alert("收款方名称长度不能大于40个汉字(80个字节)!");
			form.sPayeeName.focus();
			return false;
		}
	    
		
		if(!InputValid(form.sPayeeProv, 1, "string", 1, 1, 10,"汇入地省份")){
			return false;
		}
		
		if(!InputValid(form.sPayeeCity, 1, "string", 1, 1, 10,"汇入地城市")){
			return false;
		}
		// Modified by ylguo(郭英亮)at 2009-02-17,汇入行名称不能超过15个汉字，将原来的50个汉字改为15
		if(!InputValid(form.sPayeeBankName, 1, "string", 1, 1, 40,"汇入行名称")){
			return false;
		}
		
		if(!InputValid(form.txtPayeeBankCNAPSNO, 0, "string", 1, 0, 20,"汇入行CNAPS号")){
			return false;
		}
		
		if(!InputValid(form.sPayeeBankOrgNO, 0, "string", 1, 0, 20,"汇入行机构号")){
			return false;
		}
		
		if(!InputValid(form.sPayeeBankExchangeNO, 0, "string", 1, 0, 20,"汇入行联行号")){
			return false;
		}
		
	
	}
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
	
		if(!InputValid(form.sPayeeAcctNoZoomCtrl, 1, "string", 1, 1, 50,"收款方账号")){
			return false;
		}
	
		if(form.nPayeeAccountID.value == "-1"){
			alert("收款方账号请用放大镜选择！");
			form.sPayeeAcctNoZoomCtrl.focus();
			return false;
		}
	}
	var sourceType = form.sApplyCodeSource.value;
	if(sourceType!="<%=SETTConstant.ExtSystemSource.EBANK%>")
	{
			
		if(!InputValid(form.sApplyCode, 1, "string", 1, 0, 20,"外部系统指令序号")){
			return false;
		}
	}

	if(form.amount.value=="0.00")
	{
		alert("交易金额不能小于0.01");
		form.amount.focus();
		return false;
	}
	if(Trim(form.amount.value)=="")
	{
		alert("交易金额不能为空");
		form.amount.focus();
		return false;
	}	
	if(!checkAmount(form.amount, 1, "交易金额")){
		return false;
	}

	if(!checkDate(form.tsExecute, 1 ,"执行日")){
		return false;
	}
	if(!CompareDateString(form.hiddenOpendate.value,form.tsExecute.value))
	{
		alert("执行日不能小于系统开机日！");
		form.tsExecute.focus();
		return false;
	}
	// Modified by ylguo(郭英亮)at 2009-02-17,汇入行名称不能超过6个汉字，将原来的100个汉字改为6
	/*if(!InputValid(form.sNoteCtrl, 1, "string", 1, 1, 6,"汇款用途")){
		return false;
	}*/
	
	// 基本业务校验
	var dBalance = parseFloat(reverseFormatAmount(form.dPayerUsableBalance.value)) - parseFloat(reverseFormatAmount(form.amount.value));
	// 可用余额 - 交易金额不能小于0
	if(dBalance < 0){
		alert("可用余额不足，请重新输入金额");
		form.amount.focus();
		return false;
	}
	
	// 付款方和收款方不能相同
	if((form.sPayerAccountNoZoomCtrl.value == form.sPayeeAcctNoZoomCtrl.value ) && (remitType == <%=OBConstant.SettRemitType.INTERNALVIREMENT%>)){
		alert("付款方和收款方不能相同");
		form.sPayeeAcctNoZoomCtrl.focus();
		return false;
	}
	/* 汇款用途校验 */
		// 校验汇款用途必填项	
		if(Trim(form.sNoteCtrl.value) == "")
		{
		   alert("汇款用途不能为空");
		   form.sNoteCtrl.focus();
		   return false;
		
		}
	// Added by ylguo 处理账号为全角的情况的ＢＵＧ
	if(!quanjiao(form.sPayerAccountNoZoomCtrl,'付款方账号'))//最后看账户号有没有全角的字符
    	return false;
	if(!quanjiao(form.sPayeeBankNoZoomCtrl,'收款方账号'))//最后看账户号有没有全角的字符
    	return false;
    if(!quanjiao(form.sPayeeAcctNoZoomCtrl,'收款方账号'))//最后看账户号有没有全角的字符
    	return false;
    return true;
}

//查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
initCheck(form1);
function initCheck(frm) {
	if ("<%=isConsignReceive%>"=="true") {
		frm.sPayerName.disabled = true;
		frm.sPayerAccountNoZoomCtrl.disabled = true;
		frm.dPayerCurrBalance.disabled = true;
		frm.dPayerUsableBalance.disabled = true;
		frm.nRemitType.disabled = true;
		frm.sPayeeAcctNoZoomCtrl.disabled = true;
		frm.sPayeeName.disabled = true;
		frm.amount.disabled = true;
		frm.sChineseAmount.disabled = true;
		//frm.tsExecute.disabled = true;
		frm.sNoteCtrl.disabled = true;
	}
}

function doLink()
{    
	if(form1.sPayeeProv.value.length <= 0)
	{
		alert("请输入省份！");
		form1.sPayeeProv.focus();
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strAction=find&strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+encodeURIComponent(encodeURIComponent(form1.sPayeeBankName.value))
	+'&province='+encodeURIComponent(encodeURIComponent(form1.sPayeeProv.value))
	+'&city='+encodeURIComponent(encodeURIComponent(form1.sPayeeCity.value))
	+'&recBankCode='+encodeURIComponent(encodeURIComponent(form1.txtPayeeBankCNAPSNO.value))
	+'&oldReceiveBranchName='+encodeURIComponent(encodeURIComponent(form1.sPayeeBankName.value))
	+'&bankName=sPayeeBankName', '', 'height=480, width=700, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}

function doIllustration()
{
	window.open('<%=strContext%>/illustation/illustration.jsp','','height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');
}


</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}
OBHtml.showOBHomeEnd(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
