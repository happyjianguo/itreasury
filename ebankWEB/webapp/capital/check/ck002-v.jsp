<%--
/*
 * 程序名称：ck002-v.jsp
 * 功能说明：资金划拨复核匹配输入页面
 * 作　　者：刘琰
 * 完成日期：2004年02月06日
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
	/* 标题固定变量 */
	String strTitle = null;
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	long lTransType = -1;
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
		System.out.println("***************");
        //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }

		/* 从请求中获取信息 */
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}

        /* 显示文件头 */
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
			       <td class=title><span class="txt_til2">逐笔付款复核</span></td>
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
		//下属单位放大镜
		String strMagnifierNameClientName = "下属单位";
		String strFormNameClientName = "frmzjhb";
		String strMainPropertyClientName = "";
		String strPrefixClientName = "";
		String[] strMainNamesClientName = {"txtClientCode"};
		String[] strMainFieldsClientName = {"ClientCode"};
		String strReturnInitValuesClientName=financeInfo.getChildClientNo();
		String[] strReturnNamesClientName = {"lClientID"};
		String[] strReturnFieldsClientName = {"id"};
		String[] strReturnValuesClientName = {String.valueOf(financeInfo.getChildClientID())};
		String[] strDisplayNamesClientName = {"客户编号","客户名称"};
		String[] strDisplayFieldsClientName = {"ClientCode","ClientName"};
		int nIndexClientName = 0;
		String strSQLClientName = " getClient()";
		//String[] strNextControlsClientName = {"txtContractCode"};
		String strMatchValueClientName = "ClientCode";
		String strNextControlsClientName = "sPayerAccountNoZoomCtrl";
		String strTitleClientName = "下属单位客户编号";
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
      <td width="130" height="25" align="left"><font color='#FF0000'>* </font>付款方账号：</td>
	  <td>
	  	<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>" >
	  	<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>" >
	  	<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>" >
		<fs_c:dic id="sPayerAccountNoZoomCtrl" size="22" form="frmzjhb" title="付款方账号" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams='frmzjhb.sPayerAccountNoZoomCtrl.value,frmzjhb.lUserID.value,frmzjhb.lClientID.value,frmzjhb.lCurrencyID.value,frmzjhb.lInstructionID.value' value="<%=financeInfo.getPayerAcctNo()%>" nextFocus="nRemitType" width="500">
			<fs_c:columns> 
				<fs_c:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				<fs_c:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
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
		//付款方账号放大镜
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
											"<font color='#FF0000'>* </font>付款方账号",
											" width=\"300\"  height=\"25\" class=\"MsoNormal\"",
											" width=\"300\" height=\"25\" ",
											new String[]{"nRemitType"});	*/
%>
      <td width="5" class="MsoNormal"></td>
    </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="220" height="25" class="MsoNormal">
            <p><span class="MsoNormal"><font color="#FF0000">* </font>汇款方式：</span></p>
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
		//收款方账号放大镜（原本转，现内部转账）
		OBMagnifier.createPayeeBankNOCtrl(out,
				sessionMng.m_lCurrencyID,
				sessionMng.m_lClientID,
				"nPayeeAccountID",
				"sPayeeNameBankNoCtrl",
				"frmzjhb",
				financeInfo.getPayeeAcctNo(),
				"sPayeeBankNoZoom",
				"   &nbsp;&nbsp;收款方账号",
				" width=\"300\" height=\"25\" class=\"MsoNormal\"",
				" width=\"300\" height=\"25\" ",
				false);
%>	 		

          <td class="MsoNormal" height="25" width="5"></td>
        </tr>
		<tr id="payeeNameZoomBank" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
		  <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;收款方名称：</td>
		  <td height="25" class="MsoNormal">
		  	<input type="hidden"  class="box" name="sPayeeNameBankNoCtrl" value="<%= financeInfo.getPayeeName() %>" maxlength="50"  size="32" readonly>
		  </td>
          <td  height="25" width="5" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNameRead" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;汇入行名称：</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="box" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="32" readonly>
          </td>
		<td height="25" width="5" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal" id="payeeBankNoZoomInternal">
          <td height="25" width="5" class="MsoNormal"></td>
<%
		//收款方账号放大镜（原内部转账，现在的本转）
		//OBMagnifier.createPayerAccountNoCtrlForZj(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","&nbsp;&nbsp;收款方账号"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ");	
	//	OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ");
	/*    OBMagnifier.createPayeeBankNOCtrl1(
	    		out,
	    		sessionMng.m_lCurrencyID,
	    		-1,
	    		"nPayeeAccountID",
	    		"sPayeeName",
	    		"frmzjhb",
	    		financeInfo.getPayeeAcctNo(),
	    		"sPayeeAccountInternal",
	    		"<font color='#FF0000'>* </font>收款方账号",
	    		" width=\"300\" height=\"25\" class=\"MsoNormal\"",
	    		" width=\"300\" height=\"25\" ");*/
%>			
		  <td width="130" height="25" align="left"><font color='#FF0000'>* </font>收款方账号：</td>
		  <td>
			<fs_c:dic id="sPayeeAccountInternalCtrl" size="22" form="frmzjhb" title="收款方账号" sqlFunction="getPayeeBankNOSQL1"  sqlParams='false,-1,frmzjhb.lCurrencyID.value,frmzjhb.sPayeeAccountInternalCtrl.value,frmzjhb.sPayeeName.value' value="<%=financeInfo.getPayeeAcctNo()%>" nextFocus="dAmount" width="500">
				<fs_c:columns> 
					<fs_c:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
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
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;收款方名称：</td>
          <td height="25" class="MsoNormal">
            <input type="text" class="box" name="sPayeeName" value="" size="32">
              </td>
          <td  height="25" width="5" class="MsoNormal"></td>
        </tr>
		 <tr class="MsoNormal" id="payeeAcctNoZoom">          
          <td height="25" width="5" class="MsoNormal"></td>
          <td width="130" height="25" align="left">&nbsp;&nbsp;收款方账号：</td>
		  <td>
			<fs_c:dic id="sPayeeAcctNoZoomCtrl" size="22" form="frmzjhb" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frmzjhb.lClientID.value,frmzjhb.lCurrencyID.value,frmzjhb.sPayeeAcctNoZoomCtrl.value,frmzjhb.sPayeeNameAcctNOCtrl.value' value="<%=financeInfo.getPayerAcctNo()%>" nextFocus="" width="900">
				<fs_c:columns> 
					<fs_c:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
					<fs_c:column display="汇入地（省）" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="汇入地（市）" name="spayeecity" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="汇入行名称" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
					<fs_c:column display="联行号" name="spayeebankexchangeno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="CNAPS号" name="spayeebankcnapsno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
					<fs_c:column display="机构号" name="spayeebankorgno" type="<%=PagerTypeConstant.STRING %>" width="100" sort="true" align="center"/>
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
		//收款方账号放大镜（汇）
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
        		"&nbsp;&nbsp;收款方账号",
        		" width=\"300\" height=\"25\" class=\"MsoNormal\"",
        		" width=\"300\" height=\"25\" ",
        		sNextControlsEbank,false);	*/
	//	OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeNameAcctNOCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","&nbsp;&nbsp;收款方账号"," width=\"300\" height=\"25\" class=\"MsoNormal\""," width=\"300\" height=\"25\" ",false);	
%>	
          <td class="MsoNormal" height="25" width="5"></td>
        </tr>
		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25" width="5" class="MsoNormal"></td>
		  <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;收款方名称：</td>
		  <td height="25"  class="MsoNormal">
			 <input type="text" name="sPayeeNameAcctNOCtrl" class="box" cols="30"  readonly rows="2" ><%= financeInfo.getPayeeName() %></input>
		  </td>
          
          <td height="25" width="5" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankName">
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="220" class="MsoNormal">&nbsp;&nbsp;汇入行名称：</td>
          <td height="25" class="MsoNormal">
            <input type="text" class="box" name="sPayeeBankName" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50" readonly>			
          </td>
        <td  height="25" width="1" class="MsoNormal"></td>
        </tr>
       	<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
          	<td width="220" height="25" class="MsoNormal"><font color="#FF0000">* </font>金额：</td>
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
          <td width="220" height="25" class="MsoNormal" ><font color="#FF0000">* </font>执行日：</td>
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
				<input type="button" name="Submitv00204" value=" 匹 配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
				<input type="button" name="Submitv00205" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
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
	//张雷  修改，等页面加载完成后再进行 初始化	
	document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法.
	function subSomething()
	{
		if(document.readyState == "complete"){ //当页面加载状态为完全结束时进入
			/* 汇款方式 */
			jump();
		}
	} 
	

	/* 实现功能：动态显示根据汇款方式确定的收款方资料录入表单
	 * 实现方法：通过对TR中的ID属性控制实现
	 */

	/* 收款方名称 */
	function jump()
	{	
		iRemitType = frmzjhb.nRemitType.value;
		
		/* 汇款方式本转 */
		payeeBankNoZoom.style.display = "none";

		payeeNameZoomBank.style.display = "none";

		payeeBankNameRead.style.display = "none";

		/* 汇款方式内部转账 */
		payeeBankNoZoomInternal.style.display = "none";

		payeeName.style.display = "none";

		/* 汇款方式银行汇款 */
		payeeAcctNoZoom.style.display = "none";

		payeeNameZoomAcct.style.display = "none";

		payeeBankName.style.display = "none";

		
		/* 根据汇款方式确定所显示的TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // 汇款方式本转
		{
			/* 带放大镜的收款方银行账号 */
			payeeBankNoZoom.style.display = "";

			payeeNameZoomBank.style.display = "";

			payeeBankNameRead.style.display = "";
				
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>||iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)   // 银行付款
			
		{
			/* 带放大镜的收款方账号 */
			payeeAcctNoZoom.style.display = "";

			payeeNameZoomAcct.style.display = "";

			payeeBankName.style.display = "";

		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			/* 带放大镜的收款方银行账号 */
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
			  {//没有选择
			  	  alert("请选择汇款方式");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frmzjhb.sPayeeAccountInternalCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//本转
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//其他
                  frmzjhb.dAmount.focus();
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* 修改提交处理函数 */
    function matchme()
    {
        
		frmzjhb.action = "ck007-c.jsp?child=<%=lChild%>";
		if (validate() == true)
        {
			/* 确认提交 */
<%--			if (!confirm("是否匹配？"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
			showSending();
            frmzjhb.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (frmzjhb.lInstructionID.value == -1)
		{	
			if (confirm("确定取消吗？"))
			{
				frmzjhb.action="";
				frmzjhb.submit();
			}
		}
		else
		{
			if (confirm("确定取消吗？"))
			{
        		history.go(-1);
			}
		}
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		
		if (frmzjhb.lClientID != null)
		{
			if (frmzjhb.lClientID.value <= 0)
			{
				alert("下属单位客户编号不能为空");
				frmzjhb.txtClientCode.focus();
				return false;
			}
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		
		if (iRemitType <= 0)
		{
			alert("请选择汇款方式！");
			frmzjhb.nRemitType.focus();
			return false;
		}
		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>)// 汇款方式本转
		{			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>||iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)   // 汇款方式银行付款
		{							
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
	      if(frmzjhb.nPayeeAccountID.value==-1){
		    alert("收款方账户必须从放大镜里面选择");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{			
			if (frmzjhb.sPayeeAccountInternalCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		if(frmzjhb.nPayeeAccountID.value==-1){ 
		    alert("收款方账户必须从放大镜里面选择");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}

		}

		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.dAmount, 1, "交易金额"))
		{
			return false;
		}
		
		/* 执行日校验 */
		var tsExecute = frmzjhb.tsExecute.value;
		if(tsExecute=="")
		{
			alert("执行日不能为空，请录入");
			frmzjhb.tsExecute.focus();
			return false;
		}
	
	
	
		
		if(!CompareDateString(frmzjhb.hiddenOpendate.value,frmzjhb.tsExecute.value))
	{
		alert("执行日不能小于系统开机日！");
		form.tsExecute.focus();
		return false;
	}
	
	
	
		if(chkdate(tsExecute) == 0)
		{
			alert("执行日格式不正确，请重新录入");
			frmzjhb.tsExecute.focus();
			return false;
		}
    	return true;
    }
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
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
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);	
    }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>