<%--
/*
 * 程序名称：consignReceiveConfirmUpdate.jsp
 * 功能说明：委托收款确认修改页面
 * 作　　者：xlchang
 * 完成日期：2010-12-01
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.iss.itreasury.util.ChineseCurrency" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.IException" %>
<%@page import="com.iss.itreasury.ebank.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% 
	//放大镜公用变量
	String strFormName = "form";
	String strCtrlName = "";
	String strMagnifierName = "";
	String strFirstTD = " width = \"150\" ";	
	String strSecondTD = "";	
	String strMainProperty = "";
	String strPrefix = "";
	String strSQL = "";
	int nIndex = 0;	
	String[] sNextControls = null;	
	String[] strMainNames = null;
	String[] strMainFields = null;
	String[] strReturnNames = null;
	String[] strReturnFields = null;
	String[] strReturnValues = null;
	String[] strDisplayNames = null;
	String[] strDisplayFields = null;
   	
   	long id = -1;
	long NPayerClientID = -1;      //付款单位id
	String NPayerClientName = "";   //付款单位名称
	long NPayerAcctID = -1;      //付款账号id
	String NPayerAcctNo = "";     //付款账号
	long NPayeeClientID = -1;     //收款单位id
	String NPayeeClientName = "";  //收款单位名称
	long NPayeeAcctID = -1;      //收款账号id
	String NPayeeAcctNo = "";     //收款账号
	double MAmount = 0.00;         //金额
	String SExecute = "";         //执行日期
	long NAbstractID = -1;      //汇款用途id;
	String NAbstractName = "";  //汇款用途
	String SContractCode = "";           //合同号	
	long NStatus = -1;    //状态
	String SMemo = "";                   //备注
	
	String strTitle = "委托收款确认－修改";
	String strContext = request.getContextPath();

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{		
		/* 实现菜单控制 */
		long lShowMenu = OBConstant.ShowMenu.YES;
		String strMenu = (String)request.getAttribute("menu");
		if ((strMenu != null) && (strMenu.equals("hidden")))
		{
		    lShowMenu = OBConstant.ShowMenu.NO;
		}
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
    	
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        
        long nOfficeID = sessionMng.m_lOfficeID;
		long nCurrencyID = sessionMng.m_lCurrencyID;	
		long nClientID = sessionMng.m_lClientID;
		NPayeeClientID = nClientID;
       
       
       NameRef nameRef = new NameRef();
       ConsignReceiveInfo info = (ConsignReceiveInfo)request.getAttribute("result");			
		if (info != null) {
			id = info.getId();
			NPayerClientID = info.getNPayerClientID();
			NPayerClientName = NameRef.getClientNameByID(NPayerClientID);
			NPayerAcctID = info.getNPayerAcctID();
			NPayerAcctNo = nameRef.getAccountNOByIDFromSett(NPayerAcctID);
			NPayeeClientID = info.getNPayeeClientID();
			NPayeeClientName = NameRef.getClientNameByID(NPayeeClientID);
			NPayeeAcctID = info.getNPayeeAcctID();
			NPayeeAcctNo = nameRef.getAccountNOByIDFromSett(NPayeeAcctID);
			MAmount = info.getMAmount();
			SExecute = DataFormat.getDateString(info.getDTExecute());
			if (SExecute == "") {
				 SExecute = DataFormat.getDateString(Env.getSystemDateTime(nOfficeID,nCurrencyID));
			}
			NAbstractID = info.getNAbstractID();
			NAbstractName = NameRef.getAbstractNameByID(NAbstractID);
			SContractCode = DataFormat.formatString(info.getSContractCode());
			NStatus = info.getNStatus();
			SMemo = DataFormat.formatString(info.getSMemo());
		}
                
       %>
       
       
     
<jsp:include flush="true" page="/ShowMessage.jsp" />

<form name="form" method="post" action="<%=strContext%>/project/wisgfc/special/control/consignReceiveConfirm.jsp">
<input type="hidden" name="strAction" >
<input type="hidden" name="id" value="<%=id%>">
<input name="strSuccessPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmQuery.jsp">
<input name="strFailPageURL"  type="hidden" value="<%=strContext%>/project/wisgfc/special/view/consignReceiveConfirmUpdate.jsp">
<table cellpadding="0" cellspacing="0" class="title_top">
	<tr>
  		<td height="22">
   		<table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title nowrap><span class="txt_til2"><%=strTitle %></span></td>
			   <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
   		</td>
	</tr>  
</table>
<br/>

<table  border="0" cellspacing="0" cellpadding="0" >
	<tr>
		<td width="1"><a class=lab_title1></td>
		<td class="lab_title2"> 付款方资料</td>
		<td width="683"><a class=lab_title3></td>
	</tr>

</table>

<table width="650" border="0" cellspacing="0" cellpadding="0" class=normal id="table1" >	
	<tr height="25" class="MsoNormal">
		<td width="20" align="right" ><font color=#FF0000>* </font></td>
       			
		<%   										
			strMagnifierName = URLEncoder.encode("付款方名称");
			strCtrlName = "NPayerClientID";
			strTitle = "付款方名称";		
			strFirstTD = " width = \"150\" ";				
			sNextControls = new String[]{"NPayerAcctIDCtrl"};
			strMainNames = new String[]{ strCtrlName + "Ctrl"};
			strMainFields = new String[]{ "name" };
			strReturnNames = new String[]{ "NPayerClientID" };
			strReturnFields = new String[]{ "id" };
			strReturnValues = new String[]{ String.valueOf(NPayerAcctID)};
			strDisplayNames = new String[]{ URLEncoder.encode("单位编号"), URLEncoder.encode("单位名称")};
			strDisplayFields = new String[]{ "code", "name" };
			strMainProperty = " maxlength='40' value='" + NPayerClientName + "'";
			strSQL = "getConsignReceiveClientSQL("+nOfficeID+","+ strCtrlName + "Ctrl.value," + nClientID +")";	
									
			OBMagnifier.showZoomCtrl1(out,strMagnifierName,strFormName,strPrefix,
				strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnValues,
				strDisplayNames,strDisplayFields,nIndex,strMainProperty,strSQL,sNextControls,
				strTitle,strFirstTD,strSecondTD,false,false);
																	
		%>	          
        </tr>
		<tr height="25" class="MsoNormal" id="payerAccountNoZoom">
			<td width="20" align="right" ><font color=#FF0000>* </font></td>
			
		<%   									
			strMagnifierName = URLEncoder.encode("付款方账号");
			strCtrlName = "NPayerAcctID";
			strTitle = "付款方账号";		
			strFirstTD = " width = \"150\" ";		
			sNextControls = new String[]{"NPayeeAcctIDCtrl"};
			strMainNames = new String[]{ strCtrlName + "Ctrl", "NPayerClientID","NPayerClientIDCtrl"};
			strMainFields = new String[]{ "sno","cid","cname" };
			strReturnNames = new String[]{ "NPayerAcctID" };
			strReturnFields = new String[]{ "sid" };
			strReturnValues = new String[]{ String.valueOf(NPayerAcctID)};
			strDisplayNames = new String[]{ URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
			strDisplayFields = new String[]{ "sno", "sname" };
			strMainProperty = " maxlength='40' value='" + NPayerAcctNo + "'";
			strSQL = "getConsignReceiveAccountSQL(" + nOfficeID+"," + nCurrencyID + "," + strCtrlName + "Ctrl.value,"
						+ "NPayerClientID.value," + NPayeeClientID + ")";
				
			OBMagnifier.showZoomCtrl1(out,strMagnifierName,strFormName,strPrefix,
				strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnValues,
				strDisplayNames,strDisplayFields,nIndex,strMainProperty,strSQL,sNextControls,
				strTitle,strFirstTD,strSecondTD,false,false);												
		%>	
		 </tr>	        
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0" >
    <tr>
		<td width="1"><a class=lab_title1></td>
		<td class="lab_title2"> 收款方资料</td>
		<td width="683"><a class=lab_title3></td>
	</tr>
</table>
    
		   
<!--汇款方式动态显示收款方资料-->

<table width="650" class=normal border="0" cellspacing="0" cellpadding="0"  >
	<tr height="25" class="MsoNormal">
		<td width="20" align="right" ><font color=#FF0000>* </font></td>
		<%   										
		strMagnifierName = URLEncoder.encode("收款方账号");
		strCtrlName = "NPayeeAcctID";
		strTitle = "收款方账号";			
		strFirstTD = " width = \"150\" ";			
		sNextControls = new String[]{"MAmount"};
		strMainNames = new String[]{ strCtrlName + "Ctrl","NPayeeClientName"};
		strMainFields = new String[]{ "sno","cname" };
		strReturnNames = new String[]{ "NPayeeAcctID","NPayeeClientID" };
		strReturnFields = new String[]{ "sid","cid" };
		strReturnValues = new String[]{ String.valueOf(NPayeeAcctID),String.valueOf(NPayeeClientID)};
		strDisplayNames = new String[]{ URLEncoder.encode("账户编号"), URLEncoder.encode("账户名称")};
		strDisplayFields = new String[]{ "sno", "sname" };
		strMainProperty = " maxlength='40' value='" + NPayeeAcctNo + "'";
		strSQL = "getConsignReceiveAccountSQL("+nOfficeID+","+nCurrencyID+","+ strCtrlName + "Ctrl.value," + NPayeeClientID + ",-1)";
		
		OBMagnifier.showZoomCtrl1(out,strMagnifierName,strFormName,strPrefix,
			strMainNames,strMainFields,strReturnNames,strReturnFields,strReturnValues,
			strDisplayNames,strDisplayFields,nIndex,strMainProperty,strSQL,sNextControls,
			strTitle,strFirstTD,strSecondTD,false,false);		
																
		%>	
	</tr>  
	<tr id="payeeName" class="MsoNormal">
		<td width="20" align="right" ><font color=#FF0000>* </font></td>
		<td align="left">收款方名称：</td>
		<td>
			<input type="text" class="box" name="NPayeeClientName" value="<%=NPayeeClientName%>" size="20" readonly >
		</td>		
	</tr>   
</table>
<br>

<table  border="0" cellspacing="0" cellpadding="0" >
   <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width="650" border="0"  cellspacing="0" cellpadding="0" class=normal id="table1">        
        <tr class="MsoNormal" height="25" >
          <td width="20" align="right" ><font color=#FF0000>* </font></td>
          <td width="150"  align="left">金额：&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
          <td >
            <script  language="JavaScript">
				createAmountCtrl("form","MAmount","<%=MAmount%>","SContractCode","sChineseAmount","<%=nCurrencyID%>");
			</script>
			
			</td>
        </tr>	
        <tr class="MsoNormal" height="25">
          <td width="20"></td>
          <td width="150" align="left" nowrap>大写金额(<%=OBConstant.CurrencyType.getName(nCurrencyID)%>)：</td>
          <td>           
			<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(MAmount),nCurrencyID)%>" readonly>		
		  </td>	
        </tr>  
        <tr class="MsoNormal">
          <td width="20"  ></td>
          <td width="150" align="left">合同号：</td>
          <td>
			<input type="text" class="box" name="SContractCode" value="<%=SContractCode%>" maxlength="100" onfocus="nextfield ='DTExecute';" size="32">
		  </td>		
        </tr>          
        <tr class="MsoNormal" height="25">
          <td width="20" align="right" ><font color=#FF0000>* </font></td>
          <td width="150" align="left">执行日：</td>
          <td>
           <fs_c:calendar 
         	    name="DTExecute"
	          	value="" 
	          	properties="nextfield ='butConfirm'" 
	          	size="20"/>
	          	 <script>
	          		$('#DTExecute').val('<%=SExecute %>');
	          	</script>
		   </td>	
        </tr>       
        <tr class="MsoNormal"  height="25">
          <td width="20" align="right" ><font color=#FF0000>* </font></td>

		<%
			long lOfficeIDAbstract = nOfficeID;
			long lClientIDAbstract = sessionMng.m_lUserID;
			long lCurrencyIDAbstract = nCurrencyID;
			String strFormNameAbstract = "form";
			String strCtrlNameAbstract = "NAbstractID";
			String strTitleAbstract = "摘要";
			long lAbstractIDAbstract = NAbstractID;
			String strAbstractDescAbstract = NAbstractName;
			String strFirstTDAbstract = "";
			String strSecondTDAbstract = "";	
			long maxLength = 12;
			String[] strNextControlsAbstract = null;
			strNextControlsAbstract = new String[]{"SMemo"};
			
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
        </tr>
        <tr class="MsoNormal" height="25">
        <td width="20" align="right" ></td>
        <%com.iss.itreasury.util.Constant.CommonTextarea.show(out,"备注：","height=12 width='20%'","SMemo","",true,3,65,SMemo,100,"butConfirm","" ); %>
        </tr>          
      </table>
	  <br/>
	<table width="650" border="0"  cellspacing="0" cellpadding="0">
		<tr>
			<td align="right">	
				<%if (NStatus == OBConstant.SettInstrStatus.SUBMIT) { %>		
				<input class="button1" name="butConfirm" type="button" value=" 确 认 " onClick="doConfirm();" onKeyDown="doConfirm();">&nbsp;&nbsp;
				<input class="button1" name="butRefuse" type="button" value=" 拒 绝 " onClick="doRefuse();" onKeyDown="doRefuse();">&nbsp;&nbsp;
				<%} %>
				<input class="button1" name="butBack" type="button" value=" 返 回 " onClick="doBack();" onKeyDown="doBack();">&nbsp;&nbsp;			
			</td>
		</tr>
	</table>
<br/>


</form>
<script language="javascript">
	/* 页面焦点及回车控制 	*/
	setFormName("form");
	firstFocus(form.DTExecute);	
	//setSubmitFunction("doConfirm()");
	
</script>
<script language="javascript">
String.prototype.Trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

//校验
function allFields(){
	this.af = new Array("DTExecute","执行日期","text",1);
}

//确认
function doConfirm() {
	wipeOffSpace(form);
	if(!validateFields(form)) return false;	
	if (confirm("是否确认?")) {
		form.strAction.value="<%=OBConstant.Actions.CREATESAVE%>";
		showSending();
		form.submit();
	}
}

//返回
function doBack() {
	if (confirm("是否返回?")) {
		form.strAction.value="<%=OBConstant.Actions.MATCHSEARCH%>";
		showSending();
		form.submit();
	}
}

//拒绝
function doRefuse(){
	wipeOffSpace(form);	
	if(!validate(form)) return false;	
	if (confirm("是否拒绝?")) {		
		form.strAction.value="<%=OBConstant.Actions.DELETE%>";
		showSending();
		form.submit();
	}
}

//去除空格
function wipeOffSpace(frm) {
	frm.SMemo.value = frm.SMemo.value.Trim();	
}

//校验
function validate(frm) {
	var bResult = true;	
	if (frm.SMemo.value.length ==0) {
		alert("备注不能为空!");
		frm.SMemo.focus();
		bResult = false;
	}
	if (frm.SMemo.value.length > 1000) {
		alert("备注长度不能大于1000个中文字符!");
		frm.SMemo.focus();
		bResult = false;
	}
	return bResult;
}


initCheck(form);
function initCheck(frm) {	
	frm.NPayerClientIDCtrl.disabled = true;
	frm.NPayerAcctIDCtrl.disabled = true;
	frm.NPayeeAcctIDCtrl.disabled = true;
	frm.NPayeeAcctIDCtrl.disabled = true;
	frm.NPayeeClientName.disabled = true;
	frm.MAmount.disabled = true;
	frm.sChineseAmount.disabled = true;
	frm.SContractCode.disabled = true;
	//frm.DTExecute.disabled = true;
	frm.NAbstractIDCtrl.disabled = true;
}


</script>

<%	
OBHtml.showOBHomeEnd(out);
}
catch (IException ie)
{
       OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
	return;
}
%>