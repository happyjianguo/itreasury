<!--

author: baihuili
2006.9.15
功能:网银－资金拨划 银行汇款
-->
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
					java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                   com.iss.itreasury.budget.util.*,
				   com.iss.itreasury.budget.setting.dataentity.*,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*"
				   
%>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>


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
	long lSourceType = 0;
	String strSource = request.getParameter("src");
	if ((strSource != null) && (strSource.length() > 0)) {
		lSourceType = Long.parseLong(strSource);
	}
	
		String sTemp = null;
	 	long lID = 0;           //指令序号
    	sTemp = (String) request.getParameter("lID");
    	if (sTemp != null && sTemp.trim().length() > 0) {
       		 lID = Long.parseLong(sTemp);
   		 }
	String Temp = null;
	long nEbankStatus = -1; //银行指令状态  为0时是撤销状态
	Temp =(String) request.getParameter("nEbankStatus");
	if(Temp!=null&&Temp.trim().length()>0)
	{
		 nEbankStatus = Long.parseLong(Temp);
	}

	
%>
<%

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
     if(!OBHtml.validateRequest(out, request,response)) return;
    	
		

   		 String npayeracctno = null;
   		 String spayeeacctno = null;
   		 String bankconnectnumber = null;
   		 String departmentnumber = null;
   		 String bankCNAPSNo = null;
   		 String mamount = null;
   		 Timestamp dtexecute = null;
   		 String a=Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
   	     String strContext = request.getContextPath();
   		  String snote = null;
   		  double mamountchina = 0.00;
   		  String spayeeacctname = null;
   		  String spayeebankname = null;
   		  String spayeeprov = null;
   		  String spayeecity = null;
   		  String dtexecute1 = null;
   		  String name = null;
   		  String branchname = null;
   		  String balance = null;
   		  long npayeracctid =-2;
   		  long npayeeacctid =-2;
   		  long nEbankStatusID = -1;
   		  long nstatus = -1;
   		
		  OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	 	  OBBankPayInfo oBBankPayInfo = null;
	 	
	 	  oBBankPayInfo = new OBBankPayInfo();
	 	  oBBankPayInfo.setNclientid(sessionMng.m_lClientID);
          oBBankPayInfo.setNcurrencyid(sessionMng.m_lCurrencyID);
          oBBankPayInfo.setNuserid(sessionMng.m_lUserID);
          oBBankPayInfo.setLOfficeID(sessionMng.m_lOfficeID);
	 	  oBBankPayInfo = obFinanceInstrBiz.query_OBMagnifier(oBBankPayInfo);
 		  if(oBBankPayInfo!=null)
 		  {
	 		 npayeracctid = oBBankPayInfo.getId1();       	//付款方id
	 		 name = oBBankPayInfo.getName(); 			 	//付款方名称
	 		 npayeracctno = oBBankPayInfo.getAccountno();  	//付款方帐号
	 		 branchname = oBBankPayInfo.getBankname();  		//付款方银行名称
	 	 	 balance = oBBankPayInfo.getCurrentbalance();	//付款方账户余额
 		  }

   		 if(lID>0)
   		 {
   		 	oBBankPayInfo = new OBBankPayInfo();
   		 	oBBankPayInfo = obFinanceInstrBiz.findEbankById(lID);
   		 	npayeracctno = com.iss.itreasury.ebank.util.NameRef.getBankPayAcctNameByAcctID(oBBankPayInfo.getNpayeracctid());  //付款方帐号
   		 	spayeeacctno = oBBankPayInfo.getSpayeeacctno();  //收款方帐号
   		 	departmentnumber = oBBankPayInfo.getDepartmentnumber(); //机构号
   		 	mamount = oBBankPayInfo.getFormatAmount(); //小写金额
   		 	mamountchina = oBBankPayInfo.getMamount(); //大写金额
   		 	dtexecute = oBBankPayInfo.getDtexecute(); //执行日
   		 	snote = oBBankPayInfo.getSnote();  //汇款用途
   		 	spayeeacctname = oBBankPayInfo.getSpayeeacctname();  //收款方名称
   		 	spayeebankname = oBBankPayInfo.getSpayeebankname();  //汇入行名称
   		 	spayeeprov = oBBankPayInfo.getSpayeeprov(); //汇入省
   		 	spayeecity = oBBankPayInfo.getSpayeecity(); //汇入市
   		 	bankCNAPSNo = oBBankPayInfo.getBankCNAPSNo();  //CNAPS号
   		 	bankconnectnumber = oBBankPayInfo.getBankconnectnumber(); //银行联行号
   		 	dtexecute1 = oBBankPayInfo.getFormatExecuteDate(); //执行日期
   		 	name = oBBankPayInfo.getName();  //付款方名称
   		 	branchname = oBBankPayInfo.getBranchname();  //付款方银行名称
   		 	balance = oBBankPayInfo.getFormatBalance();  //付款方账户余额
   		 	npayeracctid = oBBankPayInfo.getNpayeracctid(); //付款方账户id
   		 	npayeeacctid = oBBankPayInfo.getNpayeeacctid(); //收款方账户id
   		 	nEbankStatusID = oBBankPayInfo.getNEbankStatus();//银行指令查询状态id
   		 	nstatus = oBBankPayInfo.getNstatus();
   		 }
         
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<safety:resources />

<form method="post" name="frmzjhb">
<input type="hidden" name="strAction" value="">
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

  	    </td>
  </tr>
  
</table>

<br/>
<table border="0" cellspacing="0" cellpadding="0" class="">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 付款方资料</td>
	<td width="650"><a class=lab_title3></td>
</tr>
</table>
<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr  class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="MsoNormal">
		  <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"  align="left"><font color="#FF0000">* </font>付款方名称：</td>
          <td width="430" height="25" class="MsoNormal">
            <input type="text" class="box" name="name" size="30" value="<%=name==null?"":name%>" readonly >
            <INPUT type="hidden" name="npayeracctid" value="<%=npayeracctid==-2?-2:npayeracctid %>">
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
       <tr  class="MsoNormal">
		<td width="5" height="25" class="MsoNormal"></td>
	<%OBMagnifier.createPayerBankAccount(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,
	sessionMng.m_lClientID,sessionMng.m_lClientID,
	"npayeracctid","dPayerCurrBalance","dPayerUsableBalance","name","frmzjhb",
	NameRef.getNoLineAccountNo(npayeracctno==null?"":npayeracctno),
	"sPayerAccountNoZoom","<font color='#FF0000'>* </font>付款方账号"," nowrap width=\"130\" height=\"25\" class=\"MsoNormal\"",
	" width=\"430\" height=\"25\" ");
		%>
		<td width="160" class="MsoNormal" ></td>
		<td width="5" class="MsoNormal" ></td>
		 </tr>
	
        <tr  class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

          <td width="130" height="25" class="MsoNormal">&nbsp;&nbsp;当前余额：</td>
          <td width="480" height="25" nowrap class="MsoNormal">
		<input type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%=balance==null?"":balance%>" readonly>
		&nbsp;&nbsp;&nbsp;&nbsp;付款方银行名称：
		<input class="box" type="text"   name="bankname" size="20" value="<%=branchname==null?"":branchname%>" readonly> 
		<input type="hidden" name="nPayerAccountID" size="16" value="" >
		  </td>
          <td width="5" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table border="0" cellspacing="0" cellpadding="0" class="">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2"> 收款方资料</td>
				<td width="683"><a class=lab_title3></td>
			</tr>
        </table>
        <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
           <p>&nbsp;&nbsp;汇款方式：</p>
          </td>
          <td width="430" height="25" class="MsoNormal">
           &nbsp;&nbsp;
			银行汇款
			<INPUT type="hidden" name="ntranstype" value="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>">
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>


		
		 <tr id="payeeAcctNoZoom" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <input type="hidden" name="npayeeacctid" value="<%=npayeeacctid==-2?-2:npayeeacctid%>"/>
          <input type="hidden" name="spayeeacctno"/>

		<%
						//收款方账号放大镜（银行汇款）
						String[] sNextControlsEbank = {"mamount"};
						OBMagnifier.createPayeeAccountNOCtrl2(out,
															sessionMng.m_lCurrencyID,
															sessionMng.m_lClientID,
															"npayeeacctid",
															"spayeeacctname",
															"spayeeprov",
															"spayeecity",
															"spayeebankname",
															"txtPayeeBankCNAPSNO",
															"departmentnumber",
															"bankconnectnumber",
															"frmzjhb",
															NameRef.getNoLineAccountNo(spayeeacctno==null?"":spayeeacctno),
															"sPayeeAcctNoZoom",
															"<font color='#FF0000'>* </font>收款方账号",
															" nowrap width=\"130\" height=\"25\" class=\"MsoNormal\" ",
															" height=\"25\" ",
															sNextControlsEbank,
															false);
					%>	

          <td height="25" width="140" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25"  class="MsoNormal"></td>
		  <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<input type="text" class="box" name="spayeeacctname" cols="50"  onfocus="nextfield ='spayeeprov';" rows="2" ><%=spayeeacctname==null?"":spayeeacctname%></textarea>
		  	&nbsp;&nbsp;&nbsp;
		  	<a href="#" onclick="doIllustration()"><font color="red">各银行收款方名称信息详细说明</font></a>
		  </td>
          
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeePlace" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入地：</td>
          <td height="25"  class="MsoNormal">
            <input class="box" type="text" name="spayeeprov"  size="10" onfocus="nextfield ='spayeecity';" maxlength="15" value="<%=spayeeprov==null?"":spayeeprov%>">
            省
            <input class="box" type="text" name="spayeecity"  size="10" onfocus="nextfield ='spayeebankname';" maxlength="15" value="<%=spayeecity==null?"":spayeecity%>">
        市（县）</td>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
           <td height="25" width="130" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input class="box" type="text" class="rebox" name="spayeebankname"  size="30"  value="<%=spayeebankname==null?"":spayeebankname%>">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankCNAPSNO">
			  <td width="4" height="25">&nbsp;</td>
		      <td width="130" height="25" align="left">&nbsp; 汇入行CNAPS号：</td>
		      <td width="500" height="25">
			  	<input class="box" type="text" name="txtPayeeBankCNAPSNO" value="<%=bankCNAPSNo==null?"":bankCNAPSNo %>" size="30"  maxlength="25">
			  	&nbsp;&nbsp;&nbsp;
			  	<a href="javascript:doLink()"><font color="red">CNAPS号检索</font></a>
		      </td>
		          	
		</tr>
        <tr class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;银行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input class="box" type="text" class="rebox" name="bankconnectnumber"  size="30" maxlength="25" value="<%=bankconnectnumber==null?"":bankconnectnumber%>">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">&nbsp;&nbsp;机构号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input class="box" type="text" class="rebox" name="departmentnumber" value="<%=departmentnumber==null?"":departmentnumber%>" size="30" maxlength="25">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table  border="0" cellspacing="0" cellpadding="0" class="">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2"> 划款资料</td>
				<td width="683"><a class=lab_title3></td>
			</tr>
        </table>
        <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
       <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>金额：&nbsp;&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td height="25" width="430" class="MsoNormal">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","mamount","<%=mamount==null?"0.0":mamount%>","sNoteCtrl","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
			</script>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" nowrap class="MsoNormal" align="right">大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td width="430" height="25" class="MsoNormal">
			<input class="box" type="text" class="rebox" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(mamountchina),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal" >&nbsp;&nbsp;提交日期：</td>
          <td width="430" height="25" class="MsoNormal">
         	<%=DataFormat.getStringDateTime().substring(0,10)%>
			<input class="box" type="hidden" name="dtexecute" value="<%=DataFormat.getStringDateTime().substring(0,10)%>" onfocus="nextfield ='sNoteCtrl';" size="12">
			<!--<a href="javascript:show_calendar('frmzjhb.dtexecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">-->
				
			<!--</a>-->
		  </td>
		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          
<%
		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "frmzjhb";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " <font color=\"#FF0000\">* </font>汇款用途";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = (snote==null)?"":snote;
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 12;
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
		strNextControlsAbstract);
%>           
          <td width="230" class="MsoNormal"></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
  
	  <br>
      <table width=80% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td  align="right" >
            
          
            
			
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->

			
          	
			<%
			
			if(lSourceType==1)
			{
			 %>
			 <input class="button1" name=add type=button value=" 保 存 "  onClick="Javascript:addme_modify();"  onfocus="nextfield='submitfunction'"> 
			 <INPUT type="hidden" name="act">
						<fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit_modify();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>"/>	
          	
			 <%
			 }
			 else
			 	 if(nEbankStatus==0)
			 {
			  %>
			   <input class="button1" name=add type=button value=" 保 存 " onClick="Javascript:addme_cancel();" onfocus="nextfield='submitfunction'" > 
			 <INPUT type="hidden" name="act">
						<fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit_cancel();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>"/>	
			  <%
			  }
			  else
			  {
			  %>
			  <input class="button1" name=add type=button value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'"> 
			<INPUT type="hidden" name="act">
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
			  %>
			   
			
          
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			&nbsp;<input class="button1" name=addreset type=button value=" 重 置 " onClick="Javascript:cancelme();" > 
          &nbsp;
          </td>
          </tr>
          <td width="9" height="17"></td>
       
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
    	
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
        frmzjhb.act.value="add";	
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
     function addme_modify()
    {
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
        frmzjhb.act.value="modifyquery";	
		frmzjhb.action = "../control/c001.jsp?src=<%=lSourceType%>&lID=<%=lID%>&flag=save";
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
					  	    addme_modify();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    addme_modify();// 再次提交
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
    function addme_cancel()
    {
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
        frmzjhb.act.value="modify";	
		frmzjhb.action = "../control/c001.jsp?nEbankStatus=<%=nEbankStatus%>&flag=save";
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
					  	    addme_cancel();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    addme_cancel();// 再次提交
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
    
    

	    /* 修改提交处理函数 */
    function doSaveAndApprovalInit()
    {
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
        frmzjhb.act.value="SaveAndApproval";	
		frmzjhb.action = "../control/c001.jsp?flag=submit";
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
     function doSaveAndApprovalInit_modify()
    {
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
        frmzjhb.act.value="SaveAndApprovalmodify";	
		frmzjhb.action = "../control/c001.jsp?src=<%=lSourceType%>&lID=<%=lID%>&flag=submit";
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
					  	    doSaveAndApprovalInit_modify();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit_modify();// 再次提交
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
    function doSaveAndApprovalInit_cancel()
    {
    	document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
        frmzjhb.act.value="SaveAndApproval";	
		frmzjhb.action = "../control/c001.jsp?nEbankStatus=<%=nEbankStatus%>&flag=submit";
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
					  	    doSaveAndApprovalInit_cancel();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmzjhb,1);//加载控件
						if($("#Ver").val()!=""){
					  	    doSaveAndApprovalInit_cancel();// 再次提交
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
    /* 取消处理函数 */
    function cancelme()
    {
		if (confirm("确定重置吗？"))
			{
				frmzjhb.reset();
			}
		
    }
 function dateformat(){
		var date=frmzjhb.dtexecute.value.split("-");
		for(i=0;i<=2;i++)
		if(date[i]!=null&&date[i].length==1)
		date[i]="0"+date[i];
		date=date.join("-");
		frmzjhb.dtexecute.value = date ;
    }
    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		

		/* 付款方资料非空校验 */		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		if (frmzjhb.npayeracctid.value < 0)
		{
			alert("付款方账号请从放大镜里取出！");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}	
		
		   if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("收款方账号不能为空，请输入");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.spayeeacctname.value == "")
			{
				alert("收款方名称不能为空");
				frmzjhb.spayeeacctname.focus();
				return false;
			}	
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			
			if (!checkInterestExecuteDate(frmzjhb.dtexecute,"<%=DataFormat.getStringDateTime().substring(0,10)%>"))
			{
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
		
		if(!IsAccountN0Int(frmzjhb.txtPayeeBankCNAPSNO.value)){
				alert("汇入行CNAPS号只能是数字");
				frmzjhb.txtPayeeBankCNAPSNO.focus();
				return false;
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
		
		
      
	

		/* 执行日校验 
		if (!checkInterestExecuteDate(frmzjhb.dtexecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		}
		*/
		/* 汇款用途 */
		//if (!InputValid(frmzjhb.sNoteCtrl, 0, "textarea", 1, 0, 100,"汇款用途"))
		//{
		//	return false;
		//}
		/* 业务校验 */
		/* 可用余额－交易金额 */
		
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerCurrBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.mamount.value)) ;
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("当前余额不足，请重新输入划拨金额");
			frmzjhb.mamount.focus();
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
			
			if (!InputValid(frmzjhb.spayeeprov, 1, "string", 1, 1, 15,"汇款地 省"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.spayeecity, 1, "string",1, 1, 15,"汇入地 市(县)"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.spayeebankname, 1, "string",1, 1, 40,"汇入行名称"))
			{
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
			//暂时不控制预算必选
			/*
			if(frmzjhb.budgetSystemID.value==-1)
			{
				alert("预算体系请从放大镜中选择!");
				return false;
			}
			if(frmzjhb.budgetItemID.value==-1)
			{
				alert("预算项目请从放大镜中选择!");
				return false;
			}
			*/
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
 
 function doLink()
{    
	if(frmzjhb.spayeeprov.value.length <= 0)
	{
		alert("请输入省份！");
		return;
	}
	window.open('<%=strContext%>/bankcode/control/c001.jsp?strSuccessPageURL=<%=strContext%>/bankcode/view/v001.jsp&strFailPageURL=<%=strContext%>/bankcode/view/v001.jsp&bank='+frmzjhb.spayeebankname.value+'&province='+frmzjhb.spayeeprov.value+'&city='+frmzjhb.spayeecity.value+'&recBankCode='+frmzjhb.txtPayeeBankCNAPSNO.value+'&oldReceiveBranchName='+frmzjhb.spayeebankname.value+'&bankName=spayeebankname', '', 'height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no')
}
function doIllustration()
{
	window.open('<%=strContext%>/illustation/illustration.jsp','','height=480, width=640, top=0, left=100, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=no');
}

 
</script>
<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	setFormName("frmzjhb");
	<%
	if(lSourceType==1)
	{
	%>
	//setSubmitFunction("addme_modify(frmzjhb)");
	<%
	}
	else if(nEbankStatus==0)
	{
	%>
	//setSubmitFunction("addme_cancel(frmzjhb)");
	<%
	}
	else
	{
	%>
	
	//setSubmitFunction("addme(frmzjhb)");
	<%
	}
	%>
</script>

<%
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie){
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>