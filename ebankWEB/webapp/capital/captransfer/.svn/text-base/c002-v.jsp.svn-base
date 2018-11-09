<%--
/*
 * 程序名称：c002-v.jsp
 * 功能说明：资金划拨修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.budget.util.*"%>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.TransTypeSet" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrStatus" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<% 
	String strContext = request.getContextPath();
	/* 标题固定变量 */
	String strTitle = null;
	String strTemp = null;
	long lAbstractID = -1;
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	String lsign = null;  //判断是否为重新提交
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}
	
	
	String sPayerCurrentBalance="";
	String dPayerUsableBalance="";
	Timestamp systemDateTime = Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
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
    	
		/* 指令序号 */
		//long lInstructionID = -1;
		
		/* 初始化EJB */
		//OBFinanceInstrHome financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		//OBFinanceInstr financeInstr = financeInstrHome.create();
		//FinanceInfo financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		
		if(financeInfo == null){
			financeInfo = new FinanceInfo();
		}
		System.out.println("批次号：" + financeInfo.getSBatchNo());
		
		//初始化审批流信息
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		//此页面只有本单位审批才能进入，用于区分本单位审批和下属单位审批
		inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		//银行付款
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
		boolean bankPayIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//内部转账
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
		boolean internalVirementIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//本转
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_SELF);
		boolean selfIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		//其他
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CAPTRANSFER_OTHER);
		boolean otherIsNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);		
		boolean isNeedApproval = true;
		/* 从信息类中获取格式化的当前金额和可用金额 */
        sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
        dPayerUsableBalance = financeInfo.getFormatUsableBalance();
        if (dPayerUsableBalance==null || dPayerUsableBalance.trim().length()==0) 
		{	
			dPayerUsableBalance="0.00";
		}
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        
       %>
       
        <%
        String saccountno_zhubi="";
        long nPayerAccountID=-1;
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
	 	nPayerAccountID=financeInfo.getPayerAcctID();
 		  if(info!=null)
 		  {
 		nPayerAccountID=info.getNAccountID();
 		saccountno_zhubi=info.getSaccountno_zhubi();
 		 if(!modify.equals("modify"))
        {
 		dPayerUsableBalance=info.getCurrentbalance_zhubi();
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
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<%if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){%>
	<safety:resources/>
<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){%>
	<safety:resources cabName="NetSign20.cab" cabVersion="2,0,53,1" cabClassId="B3B938C4-4190-4F37-8CF0-A92B0A91CC77"/>
<%}%>

<form method="post" name="frmzjhb">
<!--start  指纹认证相关html -->
<input name="Ver" id="Ver" type="hidden" value="">
<!--end  指纹认证相关html -->
<input name="dtmodify" value="<%=financeInfo!=null?financeInfo.getDtModify()+"":"" %>" type="hidden"/>
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">逐笔付款</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

  	    </td>
  </tr>
  
</table>

<br/>


<table  border="0" cellspacing="0" cellpadding="0" align="">

  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 付款方资料</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>

     <table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="183" height="25"  align="left"><font color="#FF0000">* </font>付款方名称：</td>
          <td width="458" height="25" >
            <input type="text" class="box" name="sPayerName" size="32" value="<%=sessionMng.m_strClientName%>" readonly>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr  class="MsoNormal" id="payerAccountNoZoom">
			<td width="4" height="25" class="MsoNormal"></td>
			
<%     
  	 String[] ss = {"itemIDCtrl"};
		//付款方账号放大镜
		if(sign.equals("again")||update.equals("update"))
		{
			OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","<font color='#FF0000'>* </font>付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss);	
		}
		
		else
		{
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frmzjhb",saccountno_zhubi,"sPayerAccountNoZoom","<font color='#FF0000'>* </font>付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss);	
		}
	%>		
		<td width="220" class="MsoNormal" ></td>
		 </tr>
		 <!--  中交付款方放大镜 -->
		 	<tr  class="MsoNormal" id="payerAccountNoZoomDown">
		<td width="4" height="25" class="MsoNormal"></td>
			
<%     
  	 String[] ss1 = {"itemIDCtrl"};
		//付款方账号放大镜
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountIDDown","dPayerCurrBalanceDown","dPayerUsableBalanceDown","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoDownZoom","<font color='#FF0000'>* </font>付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",ss1);	
	%>		
		<td width="220" class="MsoNormal" ></td>
		 </tr>

		<tr class="MsoNormal">
        <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		
        <tr  class="MsoNormal" id="Balance">
          <td width="4" height="25" class="MsoNormal"></td>
          
          <td width="183" height="25" class="MsoNormal" align="left">&nbsp;&nbsp;当前余额：</td>
          <td width="458" height="25" class="MsoNormal">
		<input type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%= sPayerCurrentBalance %>" readonly align="right">
		  </td>
		</tr>
		<tr> 
          <td width="4" height="25" class="MsoNormal"></td>		 
		  <td width="183" height="25" class="MsoNormal" align="left">		
		<font class="MsoNormal" >
		&nbsp;&nbsp;可用余额：
		</font></td>
          <td width="458" height="25" class="MsoNormal">		
		<input type="text" class="tar" name="dPayerUsableBalance" size="20" value="<%= dPayerUsableBalance %>" readonly align="right">
		<input type="hidden" name="nPayerAccountID" size="20" value="<%=nPayerAccountID %>" >
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal" id="BalanceDown">
          <td width="4" height="25" class="MsoNormal"></td>

          <td width="183" height="25" class="MsoNormal" align="left">&nbsp;&nbsp;当前余额：</td>
          <td width="458" height="25" class="MsoNormal" >
		<input type="text" class="tar" name="dPayerCurrBalanceDown" size="20" value="<%= sPayerCurrentBalance %>" readonly align="right">
		<font class="MsoNormal" >
		可用余额：
		</font>
		<input type="text" class="tar" name="dPayerUsableBalanceDown" size="20" value="<%= dPayerUsableBalance %>" readonly align="right">
		<input type="hidden" name="nPayerAccountIDDown" size="20" value="<%= financeInfo.getPayerAcctID() %>" >
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0" align="">

    <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 收款方资料</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width=80% border="0" cellspacing="0" align="" cellpadding="0" class=normal id="table1">
        <tr >
          <td colspan="4" height="1" ></td>
        </tr>
        <tr >
          <td width="5" height="25" ></td>
          <td width="19%" height="25" align="left">
            <p><span ><font color="#FF0000">* </font>汇款方式：</span></p>
          </td>
          <td height="25"  align="left">
           <input type="hidden" name="nRemitTypeHidden" value="<%= financeInfo.getRemitType() %>">
		  <%
		  		OBHtmlCom.showRemitTypeListControlZj(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\"  ":"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
      </table>
		   
	  <!--汇款方式动态显示收款方资料-->

	  <table width=80% class=normal align="">
	  
	  	<tr id="payeeNameOther" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input type="text" class="box" name="sPayeeNameOther" value="<%=Env.getClientName()%>" size="32" readonly>
              </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameOtherLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNoZoom" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
<%
		//收款方账号放大镜（本转）
		
		OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");		
%>	 
					
          <td  height="25" width="177" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNoZoomLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomBank" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25" colspan="2" class="MsoNormal">
		  	<input type="Text" class="box" name="sPayeeNameZoomBankCtrl" value="<%= financeInfo.getPayeeName() %>" maxlength="50" size="32" readonly>
		  </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomBankLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeeBankNoZoomInternal" class="MsoNormal">
          <td height="25" width="6" class="MsoNormal"></td>
<%
		//收款方账号放大镜（内部转账）
		//OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","<font color='#FF0000'>* </font>收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
        OBMagnifier.createPayeeBankNOCtrl1(out,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,"nPayeeAccountID","sPayeeName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","<font color='#FF0000'>* </font>收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
%>		
<script>
	document.frmzjhb.hidsPayeeBankNoZoomCtrl.value="<%=financeInfo.getPayeeAcctNo()%>";
	document.frmzjhb.hidsPayeeName.value="<%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%>";
</script> 
          <td  height="25" width="183" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNoZoomInternalLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		 <tr id="payeeName" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
          <td height="25" colspan="3"class="MsoNormal">
            <input type="text" class="box" name="sPayeeName" value="<%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%>" size="32" >
              </td>
          <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		
		 <tr id="payeeAcctNoZoom" class="MsoNormal">
          <td height="25" width="6" class="MsoNormal"></td>
<%
		//收款方账号放大镜（汇）
		OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountID","sPayeeNameZoomAcctCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","<font color='#FF0000'>* </font>收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"");	
	
%>	
          <td height="25" width="183" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<textarea name="sPayeeNameZoomAcctCtrl" class="box" cols="30" onfocus="nextfield ='sPayeeProv';" rows="2" size="32" ><%= financeInfo.getPayeeName()%></textarea>
		  </td>
          <td height="25" width="2" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeePlace" class="MsoNormal">
          <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入地：</td>
          <td width="546" height="25"  class="MsoNormal">
            <input type="text" class="box" name="sPayeeProv" onblur="dropSpace(this)" value="<%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15">
            省
            <input type="text" class="box" name="sPayeeCity" onblur="dropSpace(this)" value="<%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15">
        市（县）</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="box" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="32" readonly>
          </td>
		<td height="25" width="7" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankName">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankName" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='sPayeeBankCNAPSNO';" maxlength="50">
			<input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankNameLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankCNAPSNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行CNAPS号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankCNAPSNO" value="<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>" size="32" onfocus="nextfield ='sPayeeBankOrgNO';" maxlength="50">
		
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankCNAPSNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankOrgNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行机构号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankOrgNO" value="<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>" size="32" onfocus="nextfield ='sPayeeBankExchangeNO';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankOrgNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankExchangeNO">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankExchangeNO" value="<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankExchangeNOLine" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <!-- 中交，下划成员单位显示的收款方资料 -->
        <tr id="payeeAcctNoZoomDown" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
<%
		//收款方账号放大镜（下划）
		OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nPayeeAccountIDDown","sPayeeNameZoomAcctDownCtrl","sPayeeProvDown","sPayeeCityDown","sPayeeBankNameDown","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoDownZoom","<font color='#FF0000'>* </font>收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"");	
	
%>	

          <td height="25" width="177" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
        <tr id="payeeNameZoomAcctDown" class="MsoNormal">
          <td height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
		  <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>收款方名称：</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<textarea name="sPayeeNameZoomAcctDownCtrl" cols="30" class="box"  onfocus="nextfield ='sPayeeProvDown';" rows="2" ><%= (financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName()%></textarea>
		  </td>
          
          <td height="25" width="2" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceDown" class="MsoNormal">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left"><font color="#FF0000">* </font>汇入地：</td>
          <td height="25"  class="MsoNormal">
            <input type="text" class="box" name="sPayeeProvDown" onblur="dropSpace(this)" value="<%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCityDown';" maxlength="15">
            省
            <input type="text" class="box" name="sPayeeCityDown" onblur="dropSpace(this)" value="<%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankNameDown';" maxlength="15">
        市（县）</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLineDown" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankNameDown">
          <td  height="25" width="0" class="MsoNormal"></td>
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="177"  align="left">&nbsp;&nbsp;汇入行名称：</td>
          <td height="25" colspan="2">
            <input type="text" class="box" name="sPayeeBankNameDown" value="<%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>" size="32" onfocus="nextfield ='sPayeeBankCNAPSNODown';" maxlength="50">
			<input type="hidden" name="nPayeeAccountIDDown" value="<%=  financeInfo.getPayeeAcctID() %>" >
          </td>
        <td  height="25" width="7"></td>
        </tr>
		<tr id="payeeBankNameLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
        
        <tr id="payeeBankCNAPSNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行CNAPS号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankCNAPSNODown" value="<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>" size="32" onfocus="nextfield ='sPayeeBankOrgNODown';" maxlength="50">
		
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankCNAPSNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankOrgNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行机构号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankOrgNODown" value="<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>" size="32" onfocus="nextfield ='sPayeeBankExchangeNODown';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankOrgNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
          <tr id="payeeBankExchangeNODown">
           <td height="25" width="4" class="MsoNormal"></td>
          <td height="25" width="177" class="MsoNormal" align="left">&nbsp;&nbsp;汇入行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
            <input class="box" type="text" name="sPayeeBankExchangeNODown" value="<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>" size="32" onfocus="nextfield ='dAmount';" maxlength="50">
			
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
		<tr id="payeeBankExchangeNOLineDown" class="MsoNormal">
          <td  height="1" colspan="6" class="MsoNormal"></td>
        </tr>
        
      <!-- 中交下划添加完毕 --> 
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0" align="">
   <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width=80% border="0" align="" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="19%" height="25"class="MsoNormal" align="left"><font color="#FF0000">* </font>金额：&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td height="25" width="590" class="MsoNormal">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%=financeInfo.getFormatAmount()%>","tsExecute","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
			</script>
			
			</td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal"  align="right">大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25" class="MsoNormal">
            <!--modify by xwhe 2008-05-08-->
			<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal"  align="left">&nbsp;&nbsp;外部系统指令序号：</td>
          <td height="25" class="MsoNormal">
         
			<input type="text" class="box" name="sApplyCode" size="32" value="<%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %>">		
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        
      	<!-- Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"，使用手续费的配置文件 -->
        <%
        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) 
        	&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
        	{
        %>
        	<tr id="trBankpayim">
				<td width="4" height="25">&nbsp;</td>
	          	<td width="130" height="25">&nbsp;&nbsp;汇款区域/速度：</td>
	          	<td width="500" height="25">
	            	<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.NATIVE %>" 
					<%if(financeInfo.getRemitArea() == -1){out.println("checked");}else{ if(financeInfo.getRemitArea() == Constant.remitAreaType.NATIVE){out.println("checked");} }%>> 本地
					<input type="radio" name="remitArea" value="<%=Constant.remitAreaType.DEVIATIONISM %>" 
					<%if(financeInfo.getRemitArea() == Constant.remitAreaType.DEVIATIONISM){out.println("checked");}%>> 异地&nbsp;&nbsp;&nbsp;&nbsp;
	            	
			        <input type="checkbox" name="remitSpeed" <% if(financeInfo.getRemitSpeed() == Constant.remitSpeedType.RAPID) { out.print("checked"); } %> 
			        onfocus="nextfield ='tsExecute';">是否加急
	          	</td>
	          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        	</tr>
        <%
        	}
        %>
        
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal"  align="left"><font color="#FF0000">* </font>执行日：</td>
          <td height="25" class="MsoNormal">
           <fs_c:calendar 
          	    name="tsExecute"
	          	value="" 
	          	properties="nextfield ='sNoteCtrl'" 
	          	size="20"/>
	          	<script>
	          		$('#tsExecute').val('<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>');
	          	</script>
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
		String strAbstractDescAbstract = financeInfo.getNote();
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 12;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"butAdd"};
		
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
		  <td width="600" class="MsoNormal"></td> 
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      
<%---------
       <br>
           <div id="bankpay">
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "上传" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		   	    	clientID = '<%=sessionMng.m_lClientID%>'
		   	    	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
		   	    	</div>
            <div id="internalvirement">
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "上传" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
		        	</div>
      <br>
-----------%>
	  <br/>
      <table width=80% border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
			<input class="button1" name="butAdd" type="button" value=" 保 存 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
		  	<fs:obApprovalinitbutton controlName="approvalInit"
		 										 value="保存并提交审批"
												 classType="button1"
												 onClickMethod="doSaveAndApprovalInit();"
												 officeID="<%=sessionMng.m_lOfficeID%>"
												 clientID="<%=sessionMng.m_lClientID%>"
												 currencyID="<%=sessionMng.m_lCurrencyID%>"
												 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
												 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" />
			 <fs:obApprovalinitbutton controlName="approvalInit1"
		 										 value="保存并提交审批"
												 classType="button1"
												 onClickMethod="doSaveAndApprovalInit();"
												 officeID="<%=sessionMng.m_lOfficeID%>"
												 clientID="<%=sessionMng.m_lClientID%>"
												 currencyID="<%=sessionMng.m_lCurrencyID%>"
												 moduleID="<%=Constant.ModuleType.SETTLEMENT%>"
												 transTypeID="<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>" />									  
			<input class="button1" name="butReset" type="reset" value=" 重 置 "  >&nbsp;&nbsp;
          </td>
        </tr>
      </table>
      <br/>
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
	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
		<input type="hidden" name="clickCount" value="<%=strClickCount%>">
	  	<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	    <input type="hidden" name="strAction" value="">
	    
	  
	  <input type="hidden" name="submitUserId" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>"> 
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <!--add by xwhe 2008-11-30-->
	 <input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo() %>">
	 <input type="hidden" name="systemDateTime" value="<%=DataFormat.getDateString(systemDateTime)%>">
	  
	  <safety:certFilterHidden />

</form>
<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  指纹控件-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
<% } %>
<script language="Javascript">
	
	/* 汇款方式 */
	var iRemitType = frmzjhb.nRemitType.value;
	var isNeedApproval2= null;
	jump();

	/* 实现功能：动态显示根据汇款方式确定的收款方资料录入表单
	 * 实现方法：通过对TR中的ID属性控制实现
	 */

	/* 收款方名称 */
	function jump()
	{	
		//document.getElementById("bankpay").style.display="none";
		//document.getElementById("internalvirement").style.display="none";

		frmzjhb.approvalInit.style.display ="none";
		frmzjhb.approvalInit1.style.display ="none";
		iRemitType = frmzjhb.nRemitType.value;
		
		/*下划的付款方放大镜*/
		payerAccountNoZoomDown.style.display = "none";
		BalanceDown.style.display = "none";
					
		payeeName.style.display = "none";
		payeeNameLine.style.display = "none";
		payeeNameZoomBank.style.display = "none";
		payeeNameZoomBankLine.style.display = "none";
		payeeNameZoomAcct.style.display = "none";
		payeeNameZoomAcctLine.style.display = "none";
			//下划－收款方名称
			payeeNameZoomAcctDown.style.display="none";
			payeeNameZoomAcctLineDown.style.display="none";
		
		
		/* 收款方银行账号 */
		payeeBankNoZoom.style.display = "none";
		payeeBankNoZoomLine.style.display = "none";
		payeeBankNoZoomInternal.style.display = "none";
		payeeBankNoZoomInternalLine.style.display = "none";
			//下划－收款方银行账号
			payeeAcctNoZoomDown.style.display="none";
			payeeAcctNoZoomLineDown.style.display="none";
		/* 收款方账号 */
		//payeeAcctNo.style.display = "none";
		//payeeAcctNoLine.style.display = "none";
		payeeAcctNoZoom.style.display = "none";
		payeeAcctNoZoomLine.style.display = "none";
		/*  汇入行名称 */
		payeeBankName.style.display = "none";
		payeeBankNameRead.style.display = "none";
		payeeBankNameLine.style.display = "none";
		/* 汇入地 */
		payeePlace.style.display = "none";
		payeePlaceLine.style.display = "none";
		//财企接口新增
		payeeBankCNAPSNO.style.display = "none";
		payeeBankCNAPSNOLine.style.display = "none";
		payeeBankOrgNO.style.display = "none";
		payeeBankOrgNOLine.style.display = "none";
		payeeBankExchangeNO.style.display = "none";
		payeeBankExchangeNOLine.style.display = "none";
			/*下划汇入地*/
			payeePlaceDown.style.display = "none";
			payeePlaceLineDown.style.display = "none";
			/*下划－汇入行*/
			payeeBankNameDown.style.display = "none";
			payeeBankNameLineDown.style.display = "none";
			
			payeeBankCNAPSNODown.style.display = "none";
			payeeBankCNAPSNOLineDown.style.display = "none";
			payeeBankOrgNODown.style.display = "none";
			payeeBankOrgNOLineDown.style.display = "none";
			payeeBankExchangeNODown.style.display = "none";
			payeeBankExchangeNOLineDown.style.display = "none";
		/* 当前余额 */
		//payeeBalance.style.display = "none";
		//payeeBalanceLine.style.display = "none";
		/*其他*/
		payeeNameOther.style.display = "none";
		payeeNameOtherLine.style.display = "none";
	
		/* 根据汇款方式确定所显示的TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // 汇款方式本转
		{
		    isNeedApproval2  = <%=selfIsNeedApproval%>;
		    <%isNeedApproval=selfIsNeedApproval;System.out.println("汇款方式本转");%>
		    frmzjhb.isNeedApproval.value = <%=selfIsNeedApproval%>
		   // frmzjhb.approvalInit.style.display = <%=selfIsNeedApproval==true?"":"\"none\""%>
			/*付款方放大镜*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			
			/* 带放大镜、和收款方银行账号有关联的收款方名称 */
			payeeNameZoomBank.style.display = "";
			payeeNameZoomBankLine.style.display = "";
			/* 带放大镜的收款方银行账号 */
			payeeBankNoZoom.style.display = "";
			payeeBankNoZoomLine.style.display = "";
			/* 不带放大镜的收款方账号 */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/*  汇入行名称 */
			payeeBankNameRead.style.display = "";
			payeeBankNameLine.style.display = "";
					
		}
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 银行付款	
		{		
		isNeedApproval2  = <%=bankPayIsNeedApproval%>;
		 <%isNeedApproval=bankPayIsNeedApproval;System.out.println("银行付款");%>
		 frmzjhb.isNeedApproval.value = <%=bankPayIsNeedApproval%>
		 <%-- modify by xwhe 2008-10-24 判断是批量付款的话就不走审批流--%> 	
	    <%if(financeInfo.getSBatchNo()==null || financeInfo.getSBatchNo().trim().length()==0 ) {%>
		   frmzjhb.approvalInit.style.display = <%=bankPayIsNeedApproval==true?"":"\"none\""%>
		<%}
		  else
		  {
		%>
		   frmzjhb.approvalInit.style.display = "none";
		 <%
		 }
		 %>
			/*付款方放大镜*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			/* 带放大镜、和收款方账号有关联的收款方名称 */
			payeeNameZoomAcct.style.display = "";
			payeeNameZoomAcctLine.style.display = "";
			/* 带放大镜的收款方账号 */
			payeeAcctNoZoom.style.display = "";
			payeeAcctNoZoomLine.style.display = "";
			/* 汇入地 */
			payeePlace.style.display = "";
			payeePlaceLine.style.display = "";
			/*  汇入行名称 */
			payeeBankName.style.display = "";
			payeeBankNameLine.style.display = "";
			
			payeeBankCNAPSNO.style.display = "";
			payeeBankCNAPSNOLine.style.display = "";
			payeeBankOrgNO.style.display = "";
			payeeBankOrgNOLine.style.display = "";
			payeeBankExchangeNO.style.display = "";
			payeeBankExchangeNOLine.style.display = "";
			
			/* 根据汇款方式改变汇款用途 */
			//frmzjhb.sNote.value="银行付款";
			
		}
		/*
		**中交加入，下划成员单位
		*/
		if(iRemitType==<%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)//下划成员单位
		{

			/*付款方放大镜*/
			payerAccountNoZoom.style.display="none";
			Balance.style.display = "none";
			
			payerAccountNoZoomDown.style.display="";
			BalanceDown.style.display="";
			
			
				/* 带放大镜、和收款方账号有关联的收款方名称 */
			payeeNameZoomAcctDown.style.display = "";
			payeeNameZoomAcctLineDown.style.display = "";
			/* 带放大镜的收款方账号 */
			payeeAcctNoZoomDown.style.display = "";
		    payeeAcctNoZoomLineDown.style.display = "";
		    
			/* 汇入地 */
			payeePlaceDown.style.display = "";
			payeePlaceLineDown.style.display = "";
			/*  汇入行名称 */
			payeeBankNameDown.style.display = "";
			payeeBankNameLineDown.style.display = "";
			
			payeeBankCNAPSNODown.style.display = "";
			payeeBankCNAPSNOLineDown.style.display = "";
			payeeBankOrgNODown.style.display = "";
			payeeBankOrgNOLineDown.style.display = "";
			payeeBankExchangeNODown.style.display = "";
			payeeBankExchangeNOLineDown.style.display = "";
		}
		

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
		isNeedApproval2  = <%=internalVirementIsNeedApproval%>;
		<%isNeedApproval=internalVirementIsNeedApproval;System.out.println("汇款方式内部转账");%>
		 frmzjhb.isNeedApproval.value = <%=internalVirementIsNeedApproval%>
		 <%-- modify by xwhe 2008-10-24 判断是批量付款的话就不走审批流--%> 	
	    <%if(financeInfo.getSBatchNo()==null || financeInfo.getSBatchNo().trim().length()==0 ) {%>
		 frmzjhb.approvalInit1.style.display = <%=internalVirementIsNeedApproval==true?"":"\"none\""%>
		 <%}
		  else
		  {
		 %>
		   frmzjhb.approvalInit1.style.display = "none";
		 <%
		 }
		 %>
		    /*付款方放大镜*/
			payerAccountNoZoom.style.display="";
			Balance.style.display = "";
			/* 不带放大镜的收款方名称 */
			payeeName.style.display = "";
			payeeNameLine.style.display = "";
			/* 带放大镜的收款方银行账号 */
			payeeBankNoZoomInternal.style.display = "";
			payeeBankNoZoomInternalLine.style.display = "";
			 		
		}
		if(iRemitType == <%= OBConstant.SettRemitType.OTHER %>) // 汇款方式其他
		{
		isNeedApproval2  = <%=otherIsNeedApproval%>;
		<%isNeedApproval=otherIsNeedApproval;System.out.println("汇款方式其他");%>
		frmzjhb.isNeedApproval.value = <%=otherIsNeedApproval%>
		//frmzjhb.approvalInit.style.display = <%=otherIsNeedApproval==true?"":"\"none\""%>
		
			/* 不带放大镜的收款方名称 */
			payeeNameOther.style.display = "";
			payeeNameOtherLine.style.display = "";
			//frmzjhb.sNote.value="其他";	
		}
	}
	
	function getNextField()
	{
				
              //>>>>add by shiny 20030403
      	      var iRemitType = frmzjhb.nRemitType.value;
			  if (iRemitType == -1)
			  {//没有选择
			  	  alert("汇款方式不能为空，请选择");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//本转
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//其他
                  frmzjhb.dAmount.focus();
              }
              else if (iRemitType == <%= OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER%>)
              {//中交,下划成员单位
              	  trimXH();
              	  frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();             	                	  
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* 修改保存处理函数 */
    function addme()
    {
    	var varRemitSpeed = -1;
    	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false) 
    	&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY) { %>
    	if(frmzjhb.remitSpeed.checked == true)
	 	{
	 		varRemitSpeed = "<%=Constant.remitSpeedType.RAPID %>";
	 	}
	 	else
	 	{
	 		varRemitSpeed = "<%=Constant.remitSpeedType.GENERAL %>";
	 	}
	 	<% } %>
    	
		document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		var signatureValue;
		trimXH();
		if (validate() == true)
        {
        	//签名  add by mingfang 2007-05-23
			<%
			if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		           
	            if(financeInfo.getSBatchNo()!=null && financeInfo.getSBatchNo().length()>0)
	            {
	            %>
	             valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
	            <% 
	           }	            
	            else
	           {
	           %>  
				if(isNeedApproval2){	
				<%				
					if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
					&& !financeInfo.isRefused()){
			%>				
					//特殊处理
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
			<%
				}%>			
			}	
			else{
				<%if(financeInfo.getStatus() !=OBConstant.SettInstrStatus.CHECK 
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED){
			%>
					//特殊处理
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
			<%
				}%>
			}
	     <%   }%>
					
				signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				//签名不成功，不允许保存表单
				if(signatureValue == ""){
					alert("签名不成功，无法进行保存！");
					return false;
				}
			<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>	
				
				try{
					signatureValue = DoSignNetSign(frmzjhb,nameArray,valueArray);
				}catch(e){
					alert("frmzjhb"+e.description);
				}		
				//签名不成功，不允许保存表单
				if(signatureValue == ""){
					alert("签名不成功，无法进行保存！");
					return false;
				}
			<%}%>
			if(validate() == true){
				
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
				
				//确认保存
				if (!confirm("是否保存？"))
				{
					$("#Ver").val("");
					return false;
				}
			}
			
			//查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
			invocationControl(frmzjhb);
			
			frmzjhb.<%=SignatureConstant.SIGNATUREVALUE%>.value = signatureValue;
			frmzjhb.action = "c003-c.jsp?flag=commit&sign=<%=sign%>&remitSpeed="+varRemitSpeed;
			
			var parameter = "";
			parameter += "PayerAcctNo="+document.getElementById("sPayerAccountNoZoomCtrl").value;
			parameter += "&nRemitType="+document.getElementById("nRemitType").value;
			parameter += "&dAmount="+document.getElementById("dAmount").value;
			parameter += "&tsExecute="+document.getElementById("tsExecute").value;
			parameter += "&nRemitTypeHidden="+document.getElementById("nRemitTypeHidden").value;
			parameter += "&lInstructionID="+document.getElementById("lInstructionID").value;
			parameter += "&PayeeAcctNo="+document.getElementById("sPayeeBankNoZoomCtrl").value;
			parameter += "&sPayeeProv="+document.getElementById("sPayeeProv").value;
			parameter += "&sPayeeCity="+document.getElementById("sPayeeCity").value;
			parameter += "&sPayeeBankName="+document.getElementById("sPayeeBankName").value;
			parameter += "&sPayeeAcctNoZoom="+document.getElementById("sPayeeAcctNoZoomCtrl").value;
			parameter += "&sPayeeNameZoomAcct="+document.getElementById("sPayeeNameZoomAcctCtrl").value;
			send_request("post","c003-c-1.jsp?"+parameter,parameter,"XML",populateList);
        }
    }
    function populateList() {
	    if (http_request.readyState != 4) // 判断对象状态
			return;
			
		if (http_request.status != 200)  // 信息已经成功返回，开始处理信息
		{
			alert("您所请求的页面有异常。");
			return;
		}
    
    	var flag = http_request.responseText;
    	if(flag>0){
    		if(!confirm("已有类似的记录，是否继续保存？")){
    			return false;
    		}else{
    			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
				for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    			showSending();
    			frmzjhb.submit();
    		}
    	}else{
    		frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
			for(var i=0;i<document.frames.length;i++)
			{
				if(document.frames[i].document.getElementById("transCode").value != "")
				{
					document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
				}
			}
    		showSending();			
            frmzjhb.submit();
    	}
    		
    }	
    
    function doSaveAndApprovalInit()
    {
    	
    	var varRemitSpeed = -1;
    	
    	<% if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)&& financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY ) { %>
    		
	    	if(frmzjhb.remitSpeed.checked == true)
		 	{
		 		varRemitSpeed = "<%=Constant.remitSpeedType.RAPID %>";
		 	}
		 	else
		 	{
		 		varRemitSpeed = "<%=Constant.remitSpeedType.GENERAL %>";
		 	}
	 	<% } %>
    	
		var signatureValue;
		document.frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		
		if(validate() == true){
		    //签名
			<%
			if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
				
				if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
					&& !financeInfo.isRefused()){		
			%>
					//特殊处理
					//modified by mzh_fu 2007/12/20				
					//valueArray[5] = '-1';
					valueArray[<%=OBSignatureConstant.CapTransfer.iArrayLength-1%>] = '-1';
				<%}%>		
				signatureValue = DoSign(frmzjhb,nameArray,valueArray);					
				//签名不成功，不允许保存表单
				if(signatureValue == ""){
					alert("签名不成功，无法进行保存！");
					return false;
				}	
			<%}else if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign)){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>	
				
				try{
					signatureValue = DoSignNetSign(frmzjhb,nameArray,valueArray);
				}catch(e){
					alert(e.description);
				}		
				
				//签名不成功，不允许保存表单
				if(signatureValue == ""){
					alert("签名不成功，无法进行保存！");
					return false;
				}
			<%}%>
		
		if(validate() == true){
		
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
		
			//确认保存并提交审批
			if (!confirm("是否保存并提交审批？"))
			{
				$("#Ver").val("");
				return false;
			}
		}
		
		//查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
		invocationControl(frmzjhb);
		
		frmzjhb.<%=SignatureConstant.SIGNATUREVALUE%>.value = signatureValue;
		frmzjhb.action = "c003-c.jsp?flag=commit&sign=<%=sign%>&remitSpeed="+varRemitSpeed;
		
			var parameter = "";
			parameter += "PayerAcctNo="+document.getElementById("sPayerAccountNoZoomCtrl").value;
			parameter += "&nRemitType="+document.getElementById("nRemitType").value;
			parameter += "&dAmount="+document.getElementById("dAmount").value;
			parameter += "&tsExecute="+document.getElementById("tsExecute").value;
			parameter += "&nRemitTypeHidden="+document.getElementById("nRemitTypeHidden").value;
			parameter += "&lInstructionID="+document.getElementById("lInstructionID").value;
			parameter += "&PayeeAcctNo="+document.getElementById("sPayeeBankNoZoomCtrl").value;
			parameter += "&sPayeeProv="+document.getElementById("sPayeeProv").value;
			parameter += "&sPayeeCity="+document.getElementById("sPayeeCity").value;
			parameter += "&sPayeeBankName="+document.getElementById("sPayeeBankName").value;
			parameter += "&sPayeeAcctNoZoom="+document.getElementById("sPayeeAcctNoZoomCtrl").value;
			parameter += "&sPayeeNameZoomAcct="+document.getElementById("sPayeeNameZoomAcctCtrl").value;
			send_request("post","c003-c-1.jsp?"+parameter,parameter,"XML",populateSave);
			
			
		}
    }
    
    function populateSave() {
	    if (http_request.readyState != 4) // 判断对象状态
			return;
			
		if (http_request.status != 200)  // 信息已经成功返回，开始处理信息
		{
			alert("您所请求的页面有异常。");
			return;
		}
    
    	var flag = http_request.responseText;
    	if(flag>0){
    		if(!confirm("已有类似的记录，是否继续保存？")){
    			return false;
    		}else{
    			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
				for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    			showSending();
    			frmzjhb.submit();
    		}
    	}else{
    		frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
			for(var i=0;i<document.frames.length;i++)
				{
					if(document.frames[i].document.getElementById("transCode").value != "")
					{
						document.getElementById("strTransTypeNo").value=document.frames[i].document.getElementById("transCode").value;
					}
				}
    		showSending();			
            frmzjhb.submit();
    	}
    		
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (frmzjhb.lInstructionID.value == -1)
		{	
			frmzjhb.action="c001-c.jsp";
			frmzjhb.submit();
		}
		else
		{
			
        		history.go(-1);
			
		}
    }
	
//Added by ylguo(郭英亮)去掉空格
function dropSpace(obj){
	var s = obj.value;
	var ss = "";
	ss = Trim(s);
	obj.value = ss;
}

//Added by ylguo at 2009-01-06,判断账号是否为全角
function quanjiao()
{
    if(frmzjhb.sPayerAccountNoZoomCtrl != null && frmzjhb.sPayerAccountNoZoomCtrl.value != "")
       {	
       		var sss1 = frmzjhb.sPayerAccountNoZoomCtrl.value;
       		if (sss1.length>0)
		    {
		       for (var i = 0; i < sss1.length; i++)
		        {
		            unicode=sss1.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					    frmzjhb.sPayerAccountNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       	}
       if(frmzjhb.sPayerAccountNoDownZoomCtrl != null && frmzjhb.sPayerAccountNoDownZoomCtrl.value != "")
       {
       		var sss2 = frmzjhb.sPayerAccountNoDownZoomCtrl.value;
       		if (sss2.length>0)
		    {
		       for (var i = 0; i < sss2.length; i++)
		        {
		            unicode=sss2.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					    frmzjhb.sPayerAccountNoDownZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeAccountInternalCtrl != null && frmzjhb.sPayeeAccountInternalCtrl.value != "")
       {
       		var sss3 = frmzjhb.sPayeeAccountInternalCtrl.value;
       		if (sss3.length>0)
		    {
		       for (var i = 0; i < sss3.length; i++)
		        {
		            unicode=sss3.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					    frmzjhb.sPayeeAccountInternalCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeBankNoZoomCtrl != null && frmzjhb.sPayeeBankNoZoomCtrl.value != "")
       {
       		var sss4 = frmzjhb.sPayeeBankNoZoomCtrl.value;
       		if (sss4.length>0)
		    {
		       for (var i = 0; i < sss4.length; i++)
		        {
		            unicode=sss4.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					    frmzjhb.sPayeeBankNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       }
       if(frmzjhb.sPayeeAcctNoZoomCtrl != null && frmzjhb.sPayeeAcctNoZoomCtrl.value != "")
       {
       		var sss5 = frmzjhb.sPayeeAcctNoZoomCtrl.value;
       		if (sss5.length>0)
		    {
		       for (var i = 0; i < sss5.length; i++)
		        {
		            unicode=sss5.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					   frmzjhb.sPayeeAcctNoZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			}
       	}
       if(frmzjhb.sPayeeAcctNoDownZoomCtrl != null && frmzjhb.sPayeeAcctNoDownZoomCtrl.value != "")
       {
       		var sss6 = frmzjhb.sPayeeAcctNoDownZoomCtrl.value;
       		if (sss6.length>0)
		    {
		       for (var i = 0; i < sss6.length; i++)
		        {
		            unicode=sss6.charCodeAt(i);
		            if (unicode>65280 && unicode<65375)
		            {
		                alert("账号不能输入全角字符，请输入半角字符");
					    frmzjhb.sPayeeAcctNoDownZoomCtrl.select();
						return false;
						break;
					}
				}
				//return true;
			} 
       }
      return true;
}
//判断账户编号,只能是数字和-
function checkAccountNO(accountNoCtrl)
{
	var patrn=/^([0-9]|[\\-])*$/; 

	if(accountNoCtrl!=null && accountNoCtrl.value!='')
	{
		var tempaccountNoCtrl=Trim(accountNoCtrl.value);
		if(!patrn.exec(tempaccountNoCtrl)) 
		{
			return false;
		}
	}
	return true ;
}
 /**
 * 如果所有条件符合，return ture
 * 如果校验出错，return false
 */
    function validate()
    {
 /*付款方资料校验*/
		
		// 中交添加，下划的付款方资料检验
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>) //银行付款－－下划成员单位
		{
			if (frmzjhb.sPayerName.value == "")
			{
				alert("付款方名称不能为空");
				frmzjhb.sPayerName.focus();
				return false;
			}
			
			if (frmzjhb.sPayerAccountNoDownZoomCtrl.value == "")
			{
				alert("付款方账号，请从放大镜中选择");
				frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sName.value < 0)
			{
				alert("付款方账号，请从放大镜中选择");
				frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
				return false;
			}
		}
		else                                                                 //其他
		{
			// 付款方资料非空校验 
			if (frmzjhb.sPayerName.value == "")
			{
				alert("付款方名称不能为空，请选择");
				frmzjhb.sPayerName.focus();
				return false;
			}
		
			if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
			{
				alert("付款方账号不能为空，请选择");
				frmzjhb.sPayerAccountNoZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sName.value < 0)
			{
				alert("付款方账号，请从放大镜中选择");
				frmzjhb.sPayerAccountNoZoomCtrl.focus();
				return false;
			}
		}

/*收款方资料校验*/

		// 汇款方式是否为空
		if (iRemitType <= 0)
		{
			alert("汇款方式不能为空，请选择");
			frmzjhb.nRemitType.focus();
			return false;
		}
		
		// 根据汇款方式（本转）对收款方资料进行非空校验 
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // 汇款方式本转
		{
			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankAccountNO.value < 0)
			{
				alert("收款方账号请从放大镜里取出");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeNameZoomBankCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeNameZoomBankCtrl.focus();
				return false;
			}

		}
		// 银行汇款，银行付款---财司代付，银行付款---拨子账户 三中汇款方式的账号和收款方名称的校验 
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %> ||iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %> || iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)   // 汇款方式银行付款
		{			
					
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("收款方账号不能为空，请输入");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeNameZoomAcctCtrl.value == "")
			{
				alert("收款方名称不能为空，请填写收款方名称或账号");
				frmzjhb.sPayeeNameZoomAcctCtrl.focus();
				return false;
			}
			
			if (!InputValid(frmzjhb.sPayeeNameZoomAcctCtrl, 1, "string", 1, 1,15,"收款方名称"))
			{
				return false;
			}
			
			if (frmzjhb.itemID != null)
			{
				if (frmzjhb.itemID.value <= 0)
				{
					alert("预算项目不能为空");
					frmzjhb.itemIDCtrl.focus();
					return false;
				}
			}
		}
		
		// 根据汇款方式（内部转账）对收款方资料进行非空校验 
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式：内部转账
		{
			
			if (frmzjhb.sPayeeBankNoZoomCtrl.value =="")
			{
				alert("收款方账号不能为空，请选择");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.nPayeeAccountID.value <0)
			{
				alert("收款方账号，请从放大镜中选择");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankNoZoomCtrl.value !=frmzjhb.hidsPayeeBankNoZoomCtrl.value)
			{
				alert("收款方账号，请从放大镜中选择");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
						
			if (frmzjhb.sPayeeName.value == "")
			{
				alert("收款方名称不能为空，请输入");
				frmzjhb.sPayeeName.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeName.value !=frmzjhb.hidsPayeeName.value)
			{
				alert("收款方名称，请从放大镜中选择带出");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			// 内部转帐的收款方名称是通过文本框的长度控制了
		}
		
		//中交下划成员单位收款方资料检验数据
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>) //中交：银行付款－－下划成员单位
		{
			if (frmzjhb.sPayeeAcctNoDownZoomCtrl.value == "")
			{
				alert("收款方账号不能为空，请选择");
				frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();
				return false;
			}
			
			if (frmzjhb.sPayeeNameZoomAcctDownCtrl.value == "")
			{
				alert("请选择收款方名称");
				frmzjhb.sPayeeNameZoomAcctDownCtrl.focus();
				return false;
			}		
			
			if (frmzjhb.itemID != null)
			{
				if (frmzjhb.itemID.value <= 0)
				{
					alert("预算项目不能为空，请输入");
					frmzjhb.itemIDCtrl.focus();
					return false;
				}
			}
		}
		
/* 付款方和收款方的综合校验*/	
        if((frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeBankNoZoomCtrl.value ) && (iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) )
		{
			alert("付款方和收款方不能相同");
			frmzjhb.sPayeeBankNoZoomCtrl.focus();
			return false;
		}
		if((frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeBankNoZoomCtrl.value) && (iRemitType == <%= OBConstant.SettRemitType.SELF %>))
		{
			alert("付款方和收款方不能相同");
			frmzjhb.sPayeeBankNoZoomCtrl.focus();
			return false;
		}
		
/* 汇入地的校验:银行汇款，银行付款---财司代付，银行付款---拨子账户 三中汇款方式 */
       if(frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.BANKPAY %> || frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.FINCOMPANYPAY %> || frmzjhb.nRemitType.value == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)   // 银行付款
		{
			if (!InputValid(frmzjhb.sPayeeProv, 1, "string", 1, 1, 15,"汇入地(省)"))
			{
				return false;
			}
			
			if (!InputValid(frmzjhb.sPayeeCity, 1, "string",1, 1, 15,"汇入地(市)"))
			{
				return false;
			}
			
			if(frmzjhb.sPayeeCity.value != "")
			{
				var str = frmzjhb.sPayeeCity.value;
				str = str.substring(str.length-1,str.length);
				if(str=="市")
				{
					alert("请去掉汇入城市中的 '市' ");
					frmzjhb.sPayeeCity.focus();
					return false;
				}
			}
			
			//modify by xwhe 2008-10-13 
			if (!InputValid(frmzjhb.sPayeeBankName, 1, "string",1, 1, 15,"汇入行名称"))
			{
				return false;
			}
		}
		
/* 中交，银行付款－－下划成员单位 */
		if(frmzjhb.nRemitType.value == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER%>)
		{
			if (!InputValid(frmzjhb.sPayeeProvDown, 1, "string", 1, 1, 15,"汇入地(省)"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.sPayeeCityDown, 1, "string",1, 1, 15,"汇入地(市)"))
			{
				return false;
			}
			if(frmzjhb.sPayeeCityDown.value != "")
			{
				var str = frmzjhb.sPayeeCityDown.value;
				str = str.substring(str.length-1,str.length);
				if(str=="市")
				{
					alert("请去掉汇入城市中的 '市' ");
					frmzjhb.sPayeeCityDown.focus();
					return false;
				}
			}
		}
      
/* 金额校验 */
		if(!isFloat(frmzjhb.dAmount.value))
		{
			alert("交易金额只能是数值");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		if(frmzjhb.dAmount.value<0)
		{
			alert("交易金额不能为负");
			frmzjhb.dAmount.focus();
			return false;
		}
		
		if(!checkAmount(frmzjhb.dAmount, 1, "交易金额"))
		{
			return false;
		}
		
		// 可用余额－交易金额 
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		// 中交加入，如果汇款方式是下划，判断可用余额－交易金额
		if(iRemitType == <%=OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER %>)
		{
			dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalanceDown.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		}
		// 可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入划款金额");
			frmzjhb.dAmount.focus();
			return false;
		}
		
/* 执行日校验 */
		if(document.all("tsExecute").value=="")
		{
			alert("执行日不能为空，请录入");
			document.all("tsExecute").focus();
			return false;
		}
		if(!CompareDateString(frmzjhb.systemDateTime.value,frmzjhb.tsExecute.value))
		{
			alert("执行日不能小于系统开机日！");
			frmzjhb.tsExecute.focus();
			return false;
		}
		
		
/* 汇款用途校验 */
		// 校验汇款用途必填项	
		if(Trim(frmzjhb.sNoteCtrl.value) == "")
		{
		   alert("汇款用途不能为空");
		   frmzjhb.sNoteCtrl.focus();
		   return false;
		
		}
		// 汇款用途 
		//if (!InputValid(frmzjhb.sNoteCtrl, 1, "string", 1, 1,6,"汇款用途"))
		//{
		//	return false;
		//}
		
		// Added by ylguo 处理账号为全角的情况的ＢＵＧ
		//if(!quanjiao())//最后看账户号有没有全角的字符
    	//	return false;
    	 if(frmzjhb.sPayerAccountNoZoomCtrl != null && frmzjhb.sPayerAccountNoZoomCtrl.value != "")
       {
	    	if(!checkAccountNO(frmzjhb.sPayerAccountNoZoomCtrl))
	    	{
	    	  alert("付款方账户号不合法请重新录入");
	    	  frmzjhb.sPayerAccountNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayerAccountNoDownZoomCtrl != null && frmzjhb.sPayerAccountNoDownZoomCtrl.value != "")
       {
       		if(!checkAccountNO(frmzjhb.sPayerAccountNoDownZoomCtrl))
	    	{
	    	  alert("付款方账户号不合法请重新录入");
	    	  frmzjhb.sPayerAccountNoDownZoomCtrl.focus();
	    	  return false;
	    	}
       }	
    	 if(frmzjhb.sPayeeAccountInternalCtrl != null && frmzjhb.sPayeeAccountInternalCtrl.value != "")
       {
       		if(!checkAccountNO(frmzjhb.sPayeeAccountInternalCtrl))
	    	{
	    	  alert("收款方账号不合法请重新录入");
	    	  frmzjhb.sPayeeAccountInternalCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeBankNoZoomCtrl != null && frmzjhb.sPayeeBankNoZoomCtrl.value != "")
       {
      		 if(!checkAccountNO(frmzjhb.sPayeeBankNoZoomCtrl))
	    	{
	    	  alert("收款方账号不合法请重新录入");
	    	  frmzjhb.sPayeeBankNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeAcctNoZoomCtrl != null && frmzjhb.sPayeeAcctNoZoomCtrl.value != "")
       {
       		 if(!checkAccountNO(frmzjhb.sPayeeAcctNoZoomCtrl))
	    	{
	    	  alert("收款方账号不合法请重新录入");
	    	  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
	    	  return false;
	    	}
       }
       if(frmzjhb.sPayeeAcctNoDownZoomCtrl != null && frmzjhb.sPayeeAcctNoDownZoomCtrl.value != "")
       {
       	 if(!checkAccountNO(frmzjhb.sPayeeAcctNoDownZoomCtrl))
	    	{
	    	  alert("收款方账号不合法请重新录入");
	    	  frmzjhb.sPayeeAcctNoDownZoomCtrl.focus();
	    	  return false;
	    	}
       }	
    return true;
}
 
//中交下划,置空
function trimXH()
{
	if(frmzjhb.sPayeeAcctNoDownZoomCtrl.value==' ')
	{
		frmzjhb.sPayeeAcctNoDownZoomCtrl.value='';
	}
	if(frmzjhb.sPayeeNameZoomAcctDownCtrl.value==' ')
	{
		frmzjhb.sPayeeNameZoomAcctDownCtrl.value='';
	}
	if(frmzjhb.sPayeeProvDown.value==' ')
	{
		frmzjhb.sPayeeProvDown.value='';
	}
	if(frmzjhb.sPayeeCityDown.value==' ')
	{
		frmzjhb.sPayeeCityDown.value='';
	}
	if(frmzjhb.sPayeeBankNameDown.value==' ')
	{
		frmzjhb.sPayeeBankNameDown.value='';
	}

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
 
function repeatTrans()
{
	
	
	
	if (confirm(document.frmzjhb.strErrMessage.value))
    {
	
		frmzjhb.action = "c003-c.jsp";
		document.frmzjhb.BudgetPass.value="true";


		if (validate() == true)
        {
			showSending();
			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
            frmzjhb.submit();
		}

    }
	else
	{
		document.frmzjhb.BudgetPass.value="";
		document.frmzjhb.strErrMessage.value="";
		frmzjhb.action = "c001-c.jsp";
		showSending();
			
        frmzjhb.submit();
		//window.location="c001-c.jsp";
	}
	

}  
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	<%if(financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER){%>
	firstFocus(frmzjhb.sPayerAccountNoDownZoomCtrl);
	<%}else{%>
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	<%}%>
	//setSubmitFunction("addme(frmzjhb)");
	setFormName("frmzjhb");		
	
	//查询是否是由委托收款生成的内转 add by xlchang 2010-12-06 武钢需求
	initCheck(frmzjhb);
	function initCheck(frm) {
		if ("<%=isConsignReceive%>"=="true") {
			frm.sPayerName.disabled = true;
			frm.sPayerAccountNoZoomCtrl.disabled = true;
			frm.dPayerCurrBalance.disabled = true;
			frm.dPayerUsableBalance.disabled = true;
			frm.nRemitType.disabled = true;
			frm.sPayeeBankNoZoomCtrl.disabled = true;
			frm.sPayeeName.disabled = true;
			frm.dAmount.disabled = true;
			frm.sChineseAmount.disabled = true;
			frm.sNoteCtrl.disabled = true;
			firstFocus(frmzjhb.tsExecute);
			frm.tsExecute.onfocus="nextfield ='butAdd';"
			frm.butReset.disabled = true;
		}
	}
	//启用控件
	function invocationControl(frm) {
		frm.sPayerName.disabled = false;
		frm.sPayerAccountNoZoomCtrl.disabled = false;
		frm.dPayerCurrBalance.disabled = false;
		frm.dPayerUsableBalance.disabled = false;
		frm.nRemitType.disabled = false;
		frm.sPayeeBankNoZoomCtrl.disabled = false;
		frm.sPayeeName.disabled = false;
		frm.dAmount.disabled = false;
		frm.sChineseAmount.disabled = false;
		frm.sNoteCtrl.disabled = false;
	}
</script>
<%	
}
catch (IException ie)
{
       OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
	return;
}
/* 显示文件尾 */
OBHtml.showOBHomeEnd(out);	
%>
<%@ include file="/common/SignValidate.inc" %>