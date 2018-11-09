<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>

<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% 
	String strTitle = null;
    try {
    
    	//分页信息
    	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
    	
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
        
        String strCurrency = sessionMng.m_strCurrencySymbol;
        String systemDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
        
        String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
        
        String ordertype = (String)request.getAttribute("ordertype");
		JSPLogger.info("****************进入页面--/ebank/webapp/capital/check/view/check_v005.jsp");
		
		boolean blnNotBeFalsified;
        long lClientID = sessionMng.m_lClientID;    //客户ID
        long lTransType = -1;           //交易类型
        long NSTATUS = -1;              //状态
        String sStartSubmit = "";       //提交日期-从
        String sEndSubmit = "";         //提交日期-到
        double dMinAmount = 0.0;        //交易金额-最小值
        double dMaxAmount = 0.0;        //交易金额-最大值
        String sStartExe = "";          //执行日期-从
        String sEndExe = "";            //执行日期-到
        sStartSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-从
        sEndSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-到
      	sStartExe = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);//执行日期-从
        sEndExe = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);//执行日期-从
 		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);		

        String[] nameArray = null;
		String[] valueArray = null;
        //从请求中获取查询结果信息
		Query_FinanceInfo  info = null;
		Query_FinanceInfo[] queryResults = (Query_FinanceInfo[])request.getAttribute(Constant.PageControl.SearchResults);
		Query_FinanceInfo queryCondition = (Query_FinanceInfo)sessionMng.getQueryCondition(strPageLoaderKey);
		Log.print("queryResults :  " + queryResults);
		
		 if(request.getParameter("dMinAmount") != null && request.getParameter("dMinAmount").trim().length() > 0) {
       dMinAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("dMinAmount")));
       }
        if(request.getParameter("dMaxAmount") != null && request.getParameter("dMaxAmount").trim().length() > 0) {
       dMaxAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("dMaxAmount")));
       }
       if(request.getParameter("sStartExe") != null ) {
       sStartExe= request.getParameter("sStartExe");
       }
       if(request.getParameter("sEndExe") != null ) {
       sEndExe= request.getParameter("sEndExe");
       }
       if(request.getParameter("sStartSubmit") != null) {
       sStartSubmit= request.getParameter("sStartSubmit");
       }
       if(request.getParameter("sEndSubmit") != null ) {
       sEndSubmit= request.getParameter("sEndSubmit");
       }
  
        if(request.getParameter("NSTATUS") != null && request.getParameter("NSTATUS").trim().length() > 0) {
            NSTATUS = Long.parseLong(request.getParameter("NSTATUS")); // 交易指令状态
            Log.print("交易指令状态=" + NSTATUS);
        }
        if(request.getParameter("lTransType") != null && request.getParameter("lTransType").trim().length() > 0) {
            lTransType = Long.parseLong(request.getParameter("lTransType")); // 交易指令状态
            Log.print("交易类型=" + lTransType);
        }
          
        //查询用参数-start
        String sign ="";
        String strTemp = "";
        //页面控制参数
         strTemp = (String) request.getParameter("sign");
        if(strTemp != null && strTemp.trim().length() > 0) {
            sign = strTemp; 
        }
        //查询用参数-end
        
      	if(queryCondition!=null)
		{
			lTransType = queryCondition.getNtranstype();       //网上银行交易类型
			NSTATUS = queryCondition.getNSTATUS();             //交易指令状态
			sStartSubmit = queryCondition.getStartSubmit();   //提交日期-从
			sEndSubmit = queryCondition.getEndSubmit();       //提交日期-到
			dMinAmount = queryCondition.getMinAmount();       //交易金额-最小值      
            dMaxAmount = queryCondition.getMaxAmount();       //交易金额-最大值
            sStartExe = queryCondition.getStartExe();         //执行日期-从       
            sEndExe = queryCondition.getEndExe();             //执行日期-到   
		}
          
		String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		String strFailPageURL = (String)request.getAttribute("strFailPageURL");
        OBHtml.showOBHomeHead(out, sessionMng, "", 1);
        String strContext = request.getContextPath();
        // 系统时间
        Timestamp dtSysDate = Env.getSystemDateTime();
        String strNote = "";
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<table width="98%" cellpadding="0" cellspacing="0" border="0"><tr><td>
<form name="frmjysqcx" method="post" action="../view/check_v005.jsp">
	<input type="hidden" name="lClientID" value="<%=lClientID%>">
	<input type="hidden" name="ActionID" value="<%=new java.util.Date().getTime()%>">
	<input type="hidden" name="p_Action" value="findFirst">
	<input type="hidden" name="strSuccessPageURL" value="<%=strContext %>/capital/check/view/check_v005.jsp">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey %>">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="ordertype" value="<%=ordertype==null?"asc":ordertype%>">
	<input type="hidden" name="orderfield" value="">
	<input type="hidden" name="systemDate" value="<%=Env.getSystemDateString()%>">
	<input type="hidden" name="isCheck" value="1"/>
	<input type="hidden" name="isDirectLink" value="1"/>		
	<input type="hidden" name="accountStatus" value="1"/>
	<input type="hidden" name="ActionID" value="<%=new java.util.Date().getTime()%>">
	<input type="hidden" name="sign" value="<%=sign %>">
	<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>">
	<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
    <input type="hidden" name="nUserID" value="<%=sessionMng.m_lUserID %>">
	
<table width=100% cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">批量复核</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
         <tr>
          <td colspan="6" height="5"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext" colspan="2">交易类型：</td>
          <td height="25" class="graytext" colspan="3">
         <%
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		OBConstant.SettInstrType.showList(out,"lTransType",1,lTransType,true,false,"   onfocus=\"nextfield ='NSTATUS';\" ",null,false);

            //业务
            
%>
           </td>
          <td width="8" height="25"></td>
        </tr>
		<tr >
          <td colspan="6" height="1"></td>
        </tr>
		<tr id="commonStatus">
			<td width="5" height="25"></td>
			<td height="25" class="graytext" colspan="2">状态：</td>
			<td height="25" class="graytext" colspan="3">
<%
			//状态
		OBHtmlCom.showCheckStatusListControl(
										out,
										"NSTATUS",
										NSTATUS,
										" onfocus=\"nextfield ='sStartSubmit';\" "
									);
%>
			</td>
			<td width="8" height="25"></td>
		</tr>

        <tr  id="commonStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="submitDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext" >提交日期：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext">
          	<fs_c:calendar 
	          	    name="sStartSubmit"
		          	value="" 
		          	properties="nextfield ='sEndSubmit'" 
		          	size="18"/>
		      	  <script>
	          		$('#sStartSubmit').val('<%=sStartSubmit%>');
	          	</script>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
            </td>
          <td width="330" height="25" class="graytext">
            <fs_c:calendar 
	          	    name="sEndSubmit"
		          	value="" 
		          	properties="nextfield ='dMinAmount'" 
		          	size="18"/>
		     <script>
	          		$('#sEndSubmit').val('<%=sEndSubmit%>');
	          	</script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="submitDateLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">金额：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">最小值</div>
          </td>
          <td width="188" height="25" class="graytext" colspan="1">
            <fs:amount 
	       		form="frmjysqcx"
       			name="dMinAmount"
       			value="<%=dMinAmount%>"
       			
       			nextFocus="dMaxAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
            </td>
            <td width="98" height="25" class="graytext" colspan="1" align="right">
            <span class="graytext">最大值</span>
            </td>
            <td width="330" height="25" class="graytext" colspan="1">
            	 <fs:amount 
	       		form="frmjysqcx"
       			name="dMaxAmount"
       			value="<%=dMaxAmount%>"
       			
       			nextFocus="sStartExe"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="executeDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">执行日期：</td>
          <td width="67" height="25" class="graytext">
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext" >
    	    <fs_c:calendar 
	          	    name="sStartExe"
		          	value="" 
		          	properties="nextfield ='sEndExe'" 
		          	size="18"/>
		        <script>
	          		$('#sStartExe').val('<%=sStartExe%>');
	          	</script>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
          </td>
          <td width="330" height="25" class="graytext">
            	<fs_c:calendar 
	          	    name="sEndExe"
		          	value="" 
		          	properties="nextfield ='butSearch'" 
		          	size="18"/>
		        <script>
	          		$('#sEndExe').val('<%=sEndExe%>');
	          	</script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="executeDateLine">
          <td colspan="6" height="1"></td>
        </tr>
      <tr>
          <td  colspan="6">
            <div align="right">
			  <input  class=button1 type="button" name="butSearch" value=" 查 找 " onClick="doSearch();" onfocus="nextfield='';">
				&nbsp;&nbsp;</div>
          </td>
      </tr>
        <tr>
          <td colspan="6" height="5"></td>
        </tr>
      </table>
    <br>
    <table class="top" width="100%">
    <tbody>
     <tr>
        <td width="1%">&nbsp;</td>
        <td width="*%">
		   <br><TABLE width="100%" id="flexlist"></TABLE><br>
		</td>
		<td width="1%">&nbsp;</td>
		</tr>
	</tbody>
	</table>
  <br/>
	<table width=100% border="0" align="" cellspacing="0" cellpadding="0">
	<tr>
		<td width="450">
			<div align="left">
				<span class="graytext">金额合计：<%= strCurrency /*金额最小值*/%></span>
				<span id=amountsum>0.00</span>
			</div>
		</td>
		<td></td>
	</tr>
	<tr>
		<td width="450">
			<div align="left">
				<span class="graytext">查询时间：<%=DataFormat.getDateString(dtSysDate)%></span>
			</div>
		</td>
		<td align="right">
			<input class="button1" name=add type=button value=" 复 核 " onClick="Javascript:doCheck();" onKeyDown="Javascript:doCheck();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</table>			
</table>
</form>
</td>
</tr>
<form name="listReport" method="post" >
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
</form>
</table>
<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="search" value="">
   
</form>
<script language="javascript">
firstFocus(frmjysqcx.lTransType);
setFormName("frmjysqcx");

$(document).ready(function() {
	
    $("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'ccheck', name : 'id', width : 50, sortable : true, align: 'center'},
			{display: '指令序号', name: 'id', elType : 'link', elName : 'username', methodName : 'toDetail("?","?","?")', width: 150, sortable: true, align: 'center'},
			{display: '交易类型',  name : 'ntranstype', width : 120, sortable : true, align: 'center'},
			{display: '账户号',  name : 'saccountno', width : 120, sortable : true, align: 'center'}	,
			{display: '借贷方向',   name: 'direction', width: 120, sortable: false, align: 'center'},
			{display: '金额',   name: 'mAmount', width: 120, sortable: true, align: 'center'},
			{display: '对方账户',   name: 'spayeeacctno', width: 120, sortable: true, align: 'center'},
			{display: '对方账号名称',   name: 'spayeename', width: 120, sortable: true, align: 'center'},
			{display: '对方汇入行',   name: 'spayeebankname', width: 120, sortable: true, align: 'center'},
			{display: '状态',   name: 'NSTATUS', width: 120, sortable: true, align: 'center'},
			{display: '汇款用途',   name: 'SNOTE', width: 120, sortable: true, align: 'center'},
			{display: '执行日期',   name: 'dtexecute', width: 120, sortable: true, align: 'center'},
			{display: '提交日期',   name: 'DTCONFIRM', width: 120, sortable: true, align: 'center'}
		],//列参数
		title:'批量复核',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryCheckInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "DTCONFIRM" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		callback : 'addOnClickFun()'
	});
});

function getFormData() 
{
	return $.addFormData("frmjysqcx","flexlist");
}

function doSearch()
{	
	if(validate())
		$.gridReload("flexlist");
}
	 
function toDetail(id,name,blnNotBeFalsified) {
	form3.txtTransType.value = id;
	form3.txtID.value = name;
  	form3.action = "<%=strContext%>/capital/check/control/check_c007.jsp?menu=hidden&blnNotBeFalsified="+blnNotBeFalsified;
    window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
    form3.target = "_formwin"; 
    //showSending();   
    form3.submit(); 
}

var flexlist = "flexlist";
function addOnClickFun(){
	//全选
	var lisCheckBox = $("input[type=checkbox][name='ccheck_all']");
	$(lisCheckBox).each(function () {
		$(this).attr("onclick", "");
  	});
   	$(lisCheckBox).each(function () {
  		$(this).click(function () {
  			dosum();
    	});
	});
	showAlarm();
	//单选框	
	var lisCheckBox = $("input[type=checkbox][name='ccheck']");
	$(lisCheckBox).each(function () {
		$(this).attr("onclick", "");
  	});
   	$(lisCheckBox).each(function () {
  		$(this).click(function () {
  			dosum();
    	});
	});

}

/*金额汇总函数*/
function dosum(){

	var tmp = 0;
	var aa = "0.00";
	
	$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
		if(n.checked){
			tmp += Number(n.value.split('####')[1]);
		} 
	});
	
	tmp = tmp + "";
	
	if(tmp==0)
	{
		document.getElementById("amountsum").innerText =aa;
	}
	else{
		document.getElementById("amountsum").innerText = formatAmount(tmp);
	}

}

function isCheckedAll()
{
	var isCheck = true;
	
	$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
		if(n.checked == false){
			isCheck = false;
		} 
	});
	/*
	for(var i=0;i<document.frmjysqcx.lID1.length;i++)
	{
		if(document.frmjysqcx.lID1[i].checked == false)
			isCheck = false;
	}
	*/
	if(isCheck)
		document.frmjysqcx.lID.checked = true;
	else
		document.frmjysqcx.lID.checked = false;
		
	if(document.frmjysqcx.lID1.length == undefined){
		document.frmjysqcx.lID.checked = document.frmjysqcx.lID1.checked;
	}		
}

function doCheck()/*批量复核*/
{
	var isFalsified = false;
	var msg;
	var isCheck = false;
	$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
		if(n.checked){
			isCheck = true;
		} 
	});
	if (!isCheck)
	{
		alert("请选择记录");
		return false;
	}
	  
	<%
	if(isUseCertification)
	{
	%>
		$.each($("#" + flexlist + " input[type='checkbox'][name='ccheck']"),function(i,n){
			if(n.checked&&n.isFalsified){
				isFalsified = true;
			} 
		});
	<%
	}
	%>
	if(isFalsified)
	{
	<%
		if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
		{
	%>
			msg = "交易中含有非法修改的数据，是否继续复核？";
	    	if (!confirm(msg))
			{
				return false;
			}  	   			
				
	<%
		}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
		{
	%>
			msg = "交易中含有非法修改的数据，无法复核!"
			alert(msg);
			return false; 	   
	<%
		}
	%>
	    
	}
	else
	{
		msg = "是否复核？";
	   	if (!confirm(msg))
		{
			return false;
		} 	   	   
	}       
	
    frmjysqcx.action = "<%=strContext%>/capital/check/control/check_c006.jsp";
    showSending(); 
	frmjysqcx.submit();
	   
}

/* 校验函数 */
function validate() {
	var starSubmit = frmjysqcx.sStartSubmit.value;
	var endSubmit = frmjysqcx.sEndSubmit.value;
	if (starSubmit != "") {
	    if(chkdate(starSubmit) == 0) {
	        alert("请输入正确的提交开始日期");
	        frmjysqcx.sStartSubmit.focus();
	        return false;
	    }
	}
	if (endSubmit != "") {
	    if(chkdate(endSubmit) == 0) {
	        alert("请输入正确的提交结束日期");
	        frmjysqcx.sEndSubmit.focus();
	        return false;
	    }
	}
	if ((starSubmit != "") && (endSubmit != "")) {
	    if (!CompareDate(frmjysqcx.sStartSubmit, frmjysqcx.sEndSubmit, 
	        "提交日期：起始日期不能大于结束日期")) {
	        return false;
	    }
	}
	if(!checkAmount(frmjysqcx.dMinAmount,"0","最小金额")){
		return false;
	}
	if(!checkAmount(frmjysqcx.dMaxAmount,"0","最大金额")){
		return false;
	}

    if ((parseFloat(reverseFormatAmount(frmjysqcx.dMinAmount.value))) > (parseFloat(reverseFormatAmount(frmjysqcx.dMaxAmount.value)))) {
        alert("最小金额不能大于最大金额");
        return false;
    }

    /* 执行日期校验 */
    var startExe = frmjysqcx.sStartExe.value;
    var endExe = frmjysqcx.sEndExe.value;
    if (startExe != "") {
        if(chkdate(startExe) == 0) {
            alert("请输入正确的执行开始日期");
            frmjysqcx.sStartExe.focus();
            return false;
        }
    }
    if (endExe != "") {
        if(chkdate(endExe) == 0)
        {
            alert("请输入正确的执行结束日期");
            frmjysqcx.sEndExe.focus();
            return false;
        }
    }
    if ((startExe != "") && (endExe != "")) {
        if (!CompareDate(frmjysqcx.sStartExe, frmjysqcx.sEndExe, 
            "执行日期：起始日期不能大于结束日期")) {
            return false;
        }
    }
    return true;
}




</script>

<%
        /* 显示文件尾 */
    }    
   catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	
	OBHtml.showOBHomeEnd(out);
	
%>
