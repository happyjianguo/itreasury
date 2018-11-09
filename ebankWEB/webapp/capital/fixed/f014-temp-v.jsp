<%--
/*
 * 程序名称：f014-v.jsp
 * 功能说明：定期支取提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%--
	session.setMaxInactiveInterval(600); // 如果登录人在10分钟内不做任何操作，则退出登录。
	response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
--%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%
/* 标题固定变量 */
String strTitle = "定期支取";

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
	//add by mingfang 2007-05-24 
	/*if(blnUseITrusCert){
		String[] nameArray = OBSignatureConstant.FixedToCurrentTransfer.getSignNameArray();
		String[] valueArray = OBSignatureConstant.FixedToCurrentTransfer.getSignValueArrayFromInfo(financeInfo);
			
		if(OBSignatureUtil.isIdHaveNotRealValue(financeInfo,blnIsNeedApproval)){
			//特殊处理			
		 	valueArray[7] = "-1";
		}
		//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		
		blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
	}*/

%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} %>

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
        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">定期支取</span></td>
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
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 定期账户资料</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" class =normal align="center">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
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
          
		<td width="130" height="25" class="MsoNormal">定期存款单据号：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getDepositNo() %></td>
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
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 收款方资料</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr class="MsoNormal">
          <td width="387">
		  	
      <table width="387" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">本金：</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇款方式：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatRemitType() %></td>
        </tr>
        <input type="hidden" id="aaa" value="<%= financeInfo.getFormatRemitType() %>">
        <tr id="payeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNo" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo().toString()) %></td>
        </tr>
        <tr id="payeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNoName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方名称：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getPayeeName() %></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tbody id="aa" style="">
        <tr id="payeePlace" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入地：</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getPayeeProv() == null) ? "　" : financeInfo.getPayeeProv()) + "省" + ((financeInfo.getPayeeCity() == null) ? "　" : financeInfo.getPayeeCity()) + "市（县）" %> 
          </td>
        </tr>
        <tr id="payeeBankNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入行名称：</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getPayeeBankName() == null) ? "" : financeInfo.getPayeeBankName() %> 
          </td>
        </tr>
        </tbody>
        <tbody id="cc" style="">
        <tr id="payeePlace" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25">
          </td>
        </tr>
        <tr id="payeeBankNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        </tbody>
        <tr id="line1" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        
      </table>  
		  </td>
		  <td width="1" class="MsoNormal">
		  </td>
		  <td width="386">
		  	
      <table width="387" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="graytext">利息：</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇款方式：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatInterestRemitType() %></td>
        </tr>
        <input type="hidden" id="bbb" value="<%= financeInfo.getFormatInterestRemitType() %>">
        <tr id="InterestPayeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNO" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= NameRef.getNoLineAccountNo(financeInfo.getInterestPayeeAcctNo().toString()) %></td>
        </tr>
        <tr id="InterestPayeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNoName" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方名称：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getInterestPayeeName() %></td>
        </tr>
        <tr id="InterestPayeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tbody id="bb">
        <tr id="InterestPayeePlace" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入地：</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getInterestPayeeProv() == null) ? "　" : financeInfo.getInterestPayeeProv()) + "省" + ((financeInfo.getInterestPayeeCity() == null) ? "　" : financeInfo.getInterestPayeeCity()) + "市（县）" %> 
          </td>
        </tr>
        <tr id="InterestPayeeBankLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBank" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="graytext">汇入行名称：</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getInterestPayeeBankName() == null) ? "" : financeInfo.getInterestPayeeBankName() %>	
          </td>
        </tr>
        </tbody>
         <tbody id="dd">
        <tr id="InterestPayeePlace" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25">  
          </td>
        </tr>
        <tr id="InterestPayeeBankLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBank" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="graytext"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        </tbody>
      </table>  
		  </td>
        </tr>
      </table>
	   <script type="text/javascript">
	    document.getElementById("cc").style.display= "none";
	   document.getElementById("dd").style.display= "none";
		var aaa=document.getElementById("aaa").value;
		var bbb=document.getElementById("bbb").value;
		if(aaa == "内部转账"){
			document.getElementById("aa").style.display= "none";
			document.getElementById("cc").style.display= "";
		}
		if(bbb == "内部转账"){
			document.getElementById("bb").style.display= "none";
			document.getElementById("dd").style.display= "";
		}
		</script>
	  <br>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 划款资料</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal" width="110">金额：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">执行日：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getNote()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝      		   
		{ 
%>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="130" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="110" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 交易申请处理详情</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal align="center">
        <thead>
        <tr>
          <td width="63"  height="19" >
            <p align="center">序列号</p>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle"   width="190">
            <div align="center">用户</div>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle"   width="190">
            <div align="center">工作描述</div>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle"   width="277">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"     height="25">
            <div align="center">1</div>
          </td>
          
          <td     width="190" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td     width="190" height="25">
            <div align="center">录入</div>
          </td>
          
          <td     width="277" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
       
<% 
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
        <tr valign="middle">
          <td width="63" align="left"     height="25">
            <div align="center" class="graytext">2</div>
          </td>
          
          <td     width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName()==null?"机核":financeInfo.getCheckUserName() %></div>
          </td>
          
          <td     width="190" height="25">
            <div align="center">复核</div>
          </td>
          
          <td     width="277" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        
<% 
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"     height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td     width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td     width="190" height="25">
            <div align="center">签认</div>
          </td>
          
          <td     width="277" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				} 
%>
<%
 			} 
%>
 </table>
 <br>
<%
 		} 
%>
    <%--  <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
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
		        	transTypeID = '<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
     <%--      </td>
        </tr>
      </table>
--%>
	  <br>
      <form method="post" name="frm">
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
      
	  <table border="0" width="774" cellspacing="0" cellpadding="0" align="center">
	  <!-- 历史审批意见 -->
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>'
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

<!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->

           <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">


            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
			 <input class=button1 name=add type=button value=" 修改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">

			<input class=button1 name=add type=button value=" 删除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">

		<% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

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
		  
		    			<input class=button1 name=add type=button value=" 返回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
		    			
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
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" 关闭 " width="46" height="18"   onclick="window.opener.location.reload();window.close();">			
        	<%}%>
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
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	  
	  	   <!-- 签名用字段 -->
	  <input type="hidden" name="sFixedDepositNoCtrl" value="<%=financeInfo.getDepositNo() %>">
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=financeInfo.getInterestPayeeAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">	  
	  <input type="hidden" name="nRemitTypeHidden" value="<%=financeInfo.getRemitType() %>">	  
	  <input type="hidden" name="nInterestRemitTypeHidden" value="<%=financeInfo.getInterestRemitType() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  <!-- 供c415.jsp判断业务类型用-->
	  <input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">
	  </form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("<%=strContext%>/capital/common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}


	/* 菜单控制处理函数 */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frm.menu.value="hidden";
		<%  }   %>
	}
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

	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/C13.jsp";
		frm.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/f011-c.jsp";
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
		frm.action="<%=strContext%>/capital/fixed/f015-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.FixedToCurrentTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.FixedToCurrentTransfer.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}%>
		
		
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("是否取消复核？"))
		{
			//showMenu();
			
			<%
				if(blnUseITrusCert){
					OBSignatureConstant.FixedToCurrentTransfer.outSignNameArrayToView(out);
					OBSignatureConstant.FixedToCurrentTransfer.outSignValueArrayToView(out);
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
		if (confirm("是否取消签认？"))
		{
			//showMenu();
			frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frm.action="<%=strContext%>/capital/fixed/S00-Ipt.jsp";
		frm.target="new_window";
		frm.submit();
		frm.target="";
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
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e, strTitle, "", 1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>