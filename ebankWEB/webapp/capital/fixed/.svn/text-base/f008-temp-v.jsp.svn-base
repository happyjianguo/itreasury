<!--
/*
 * 程序名称：f008-v.jsp
 * 功能说明：定期续存提交控制页面
 * 作　　者：蔡俊
 * 完成日期：2007年04月18日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="java.text.DateFormat"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%
String strTitle = "到期续存";

try {
	
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

	com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();

	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
    String type = "";
    String _type = request.getParameter("type");
    if ((type != null) && (type.length()>0))
	{
    	type = _type;
	}
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
       	lTransType = (String)request.getParameter("txtTransType");

      	
	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";

	FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
	if(financeInfo == null){
		response.sendRedirect(strContext + "/capital/financeinstr/view/fi_v001.jsp");
	}
	else{
		if(financeInfo.getSBatchNo() == null){
			financeInfo.setSBatchNo("");
		}
	}
	

	boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
 	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
	if(queryCapForm == null){
		queryCapForm = new QueryCapForm();
		queryCapForm.setStartSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
		queryCapForm.setEndSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
	}

		
	String strTransNo = financeInfo.getID() + "";
	
	boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
	boolean blnNotBeFalsified = true;
/*	if(blnUseITrusCert){
		String[] nameArray = OBSignatureConstant.DriveFixdePosit.getSignNameArray();
		String[] valueArray = OBSignatureConstant.DriveFixdePosit.getSignValueArrayFromInfo(financeInfo);
			
		if(OBSignatureUtil.isIdHaveNotRealValue(financeInfo,blnIsNeedApproval)){
			//特殊处理			
		 	valueArray[OBSignatureConstant.DriveFixdePosit.iArrayLength - 1] = "-1";
		}
		//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		
		blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);			
	}*/
%>


<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} %>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <form name="frm" method="post">

<input type="hidden" name="strSuccessPageURL" value="../view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.QueryOperationType.QUERY%>">  <!--操作代码-->
<!-- 查询申请指令的隐藏域 -->
<input type="hidden" name="lTransType" value="<%=queryCapForm.getTransType()%>">
<input type="hidden" name="lDepositID" value="<%=queryCapForm.getDepositID()%>">
<input type="hidden" name="strDepositNo" value="<%=queryCapForm.getDepositNo()%>">
<input type="hidden" name="lStatus" value="<%=queryCapForm.getStatus()%>">
<input type="hidden" name="sStartExe" value="<%=queryCapForm.getStartExe()%>">
<input type="hidden" name="sEndExe" value="<%=queryCapForm.getEndExe()%>">
<input type="hidden" name="dMinAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMinAmount())%>">
<input type="hidden" name="dMaxAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>">
<input type="hidden" name="sStartSubmit" value="<%=queryCapForm.getStartSubmit()%>">
<input type="hidden" name="sEndSubmit" value="<%=queryCapForm.getEndSubmit()%>">

<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="" align="center">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">到期续存</span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>

	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 到期存款账户</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
      <tr class="MsoNormal">
          <td width="5" height="25"></td>  
    <td width="130" height="25" class="MsoNormal">定期账户名称：</td>
          <td width="430" height="25"class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">定期账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">定期存单号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getSDepositBillNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 到期续存</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>

      
  <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>金额：</p></td>
      <td width="547" height="25" class="MsoNormal">
       <%=sessionMng.m_strCurrencySymbol%> <%= financeInfo.getFormatAmount() %>
      </td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>定期存款期限：</p></td>
      <td width="547" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod()>10000?financeInfo.getSDepositBillPeriod()-10000:financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"天":"个月"%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>新定期存款起始日：</p></td>
      <td width="547" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;新定期存款到期日：<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
  </table>

      <br>
      	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 利息处理 </td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%if(financeInfo.getSDepositInterestDeal() == 1 ) {%>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">本利续存</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%}else if(financeInfo.getSDepositInterestDeal() == 2 ){ %>
           <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="130">利息转至活期账号：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;</td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%} %>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
		{ 
%>
		 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="160" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 交易申请处理详情 </td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal >
       <thead>
        <tr >
          <td width="63" height="19">
            <p align="center">序列号</p>
          </td>
          
          <td  height="19" valign="middle"  width="190">
            <div align="center">用户</div>
          </td>
         
          <td  height="19" valign="middle"  width="198">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td   width="198" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% }
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
        <tr valign="middle">
          <td width="63" align="left"   height="25">
            <div align="center">2</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">复核</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <% }

				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">签认</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				}
%>
 </table>
 <%--
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
	            <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	            <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "上传" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       <%--    </td>
        </tr>
      </table>
      --%>
      <br>
	  <table border="0" width="774" cellspacing="0" cellpadding="0" align="center">
	  <!-- 历史审批意见 -->
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
		  </TD>
	  </TR>
	  <!-- 历史审批意见 -->
        <tr>
		<td width="774" align="right">

<%
	/* 从指令查询页面过来的 */
String search="";
if(request.getAttribute("search")!=null){
search=(String)request.getAttribute("search");
}
String isSign="";
if(request.getParameter("sign")!=null){
isSign=request.getParameter("sign");
}
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

            <input class=button1 name=add type=button value=" 修改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" 删除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			

		<% }
		
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) &&!search.equals("1")) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE|| financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) &&!search.equals("1")) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
			<% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&&!search.equals("1") && !isSign.equals("1")) {// 已复核、登录人＝复核人 %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">

		<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&&!search.equals("1") && isSign.equals("1")){// 已复核、需要被登录人签认=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&&!search.equals("1") && isSign.equals("1")){// 已签认、登录人＝签认人 %>

            	<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		  
<input class=button1 name=add type=button value=" 返回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;


		   <%
						///add by liuguang 2007-10-19  设置业务是否需要打印的提示
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
		   <input type="Button" class="button1" value=" 打印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%if(a!=null && !a.contains(strStatus))
               { 
			%>
			   disabled
			<%
				}
			%>
		   >
		   <%
				}
				%>
		 </td>
        </tr>
      </table>

	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">

<!-- add by mingfang 2007-05-24 -->
	  <!-- 签名用字段 -->
	  
   <input type="hidden" name="nPayeeAccountID"         value="<%=financeInfo.getPayeeAcctID()%>">     
   <input type="hidden" name="sPayerAccountNoZoomCtrl" value="<%=financeInfo.getSDepositBillNo()%>">
   <input type="hidden" name="dAmount"                 value="<%=financeInfo.getAmount()%>">
   <input type="hidden" name="nFixedDepositTime1"      value="<%=financeInfo.getFixedDepositTime()%>">
   <input type="hidden" name="tsExecute"               value="<%=financeInfo.getExecuteDate()%>">
   <input type="hidden" name="dPayerStartDate"         value="<%=financeInfo.getSDepositBillStartDate()%>">
   <input type="hidden" name="interesttype"            value="<%=financeInfo.getSDepositInterestDeal()%>">
   <input type="hidden" name="lInterestToAccountID"    value="<%=financeInfo.getSDepositInterestToAccountID()%>">
   <input type="hidden" name="submitUserId"            value="<%=financeInfo.getConfirmUserID()%>">

	<!-- 供c415.jsp判断业务类型用-->
	<input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">  
	  </form>
<!--presentation end-->

<script language="javascript">

	/*返回处理函数 */
	function returnme()
	{
		frm.action = "<%=strContext%>/capital/query/control/query_c001.jsp";
		frm.strSuccessPageURL.value = "<%=strContext%>/capital/query/view/query_v002.jsp";
		frm.strFailPageURL.value = "<%=strContext%>/capital/query/view/query_v001.jsp";
		frm.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
		showSending();
	    frm.submit();
	}

	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消复核吗？"))
		{
			//showMenu();
			
								//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			frm.action="<%=strContext%>/capital/check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}

	function updateme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/f006-c.jsp";
		frm.submit();
	}
	
		/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/f009-1-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	
/* 复核处理函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
		function checkme()
	{
		//showMenu();
		
							//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	
		/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>