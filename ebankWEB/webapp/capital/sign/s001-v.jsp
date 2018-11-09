<%--
/*
 * 程序名称：s001-v.jsp
 * 功能说明：签认查询显示页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs" %>

<%
    Log.print("\n111*******进入页面--ebank/capital/sign/s001-v.jsp******\n");
    //标题变量
    String strTitle = "";
    try {
    	//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
    	/* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        Iterator rs = (Iterator)request.getAttribute("return");
        //查询信息对象
        QueryCapForm rsForm = new QueryCapForm();
        if(request.getAttribute("FormValue") != null) {
            rsForm = (QueryCapForm)request.getAttribute("FormValue");
        }
        
        if(request.getAttribute("SelectType")!=null&&request.getParameter("SelectType").trim().length() > 0)
        {
        	String type =(String)request.getAttribute("SelectType");
        	long lTransType = Long.parseLong(type);
        	rsForm.setTransType(lTransType);
        }
      	
      	if(request.getAttribute("txtConfirmA")!=null&&request.getParameter("txtConfirmA").trim().length() > 0)
      	{
      		String startConfirm =(String) request.getAttribute("txtConfirmA");
      		rsForm.setStartSubmit(startConfirm);
      	}
      	
      	if(request.getAttribute("txtConfirmB")!=null&&request.getParameter("txtConfirmB").trim().length() > 0)
      	{
      		String endConfirm =(String) request.getAttribute("txtConfirmB");
      		rsForm.setEndSubmit(endConfirm);
      	}
      	
      	if(request.getAttribute("SelectStatus")!=null&&request.getParameter("SelectStatus").trim().length() > 0)
      	{
      		String status = (String)request.getAttribute("SelectStatus");
      		long lStatus = Long.parseLong(status);
      		rsForm.setStatus(lStatus);
      	}
      	double minAmount = 0.0;
      	if(request.getAttribute("txtMinAmount")!=null&&request.getParameter("txtMinAmount").trim().length() > 0)
      	{
      		String min = (String)request.getAttribute("txtMinAmount");
      		minAmount = Double.parseDouble(DataFormat.reverseFormatAmount(min));
      		rsForm.setMinAmount(minAmount);
      		
      	}
      	double maxAmount = 0.0;
      	if(request.getAttribute("txtMaxAmount")!=null&&request.getParameter("txtMaxAmount").trim().length() > 0)
      	{
      		String max = (String)request.getAttribute("txtMaxAmount");
      		maxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(max));
      		rsForm.setMaxAmount(maxAmount);
      	}
      	
      	if(request.getAttribute("txtExecuteA")!=null&&request.getParameter("txtExecuteA").trim().length() > 0)
      	{
      		String start = (String)request.getAttribute("txtExecuteA");
      		rsForm.setStartExe(start);
      	}
      	
      	if(request.getAttribute("txtExecuteB")!=null&&request.getParameter("txtExecuteB").trim().length() > 0)
      	{
      		String end = (String)request.getAttribute("txtExecuteB");
      		rsForm.setEndExe(end);
      	}
      
        // 系统时间

        Timestamp dtSysDate = Env.getSystemDateTime();
      //  boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);  //是否使用CFCA证书验签
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);	
        int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
        boolean blnNotBeFalsified = true;
        String[] nameArray = null;
		String[] valueArray = null;    
		
		   /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<%--下是查询结果--%>
<form name="form1" method="post">
<!--start  指纹认证相关html -->
<input name="Ver" id="Ver" type="hidden" value="">
<input name="lClientId" id="lClientId" type="hidden" value="<%=sessionMng.m_lClientID %>">
<input name="lCurrencyID" id="lCurrencyID" type="hidden" value="<%=sessionMng.m_lCurrencyID %>">
<input name="lUserID" id="lCurrencyID" type="hidden" value="<%=sessionMng.m_lUserID %>">
<!--end  指纹认证相关html -->
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">

<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务签认</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>

      <table width=100% border=0 class="normal" cellpadding="0" cellspacing="0"  id="table1" >
      	<tr><td></td></tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr height="29">
          <td width="6" ></td>
          
      <td colspan=2> 
        <p><span class="graytext" >&nbsp;&nbsp;交易类型：</span></p>
          </td>
          
          <td height="25">
<%
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		long lIsCtrl = dao.getIsControlChild(sessionMng.m_strClientCode);
		if (lIsCtrl == 1)
		{
			OBHtmlCom.showQueryTypeListControl1(out,"SelectType",(rsForm == null)?-1:
            rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" colspan=\"4\" ", true);
		}
		else
		{		
			OBHtmlCom.showQueryTypeListControl(out,"SelectType",(rsForm == null)?-1:
            rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" colspan=\"4\"  ", true);
		}
%>
          </td>
          <td width="88" height="25"></td
        ></tr>
      
        <tr >
          <td colspan="5" height="3"></td>
        </tr>
        <tr height="29">
          <td width="6" height="25"></td>
          
      <td width="84" height="25" nowrap> 
        <p><span class="graytext">&nbsp;&nbsp;提交日期：</span></p>
          </td>
          <td width="74" height="25">
            <div align="right" class="graytext">由：</div>
          </td>
          <td  height="25" >
		<%  Timestamp ts=Env.getSystemDateTime(); %>
				<fs_c:calendar 
	          	    name="txtConfirmA"
		          	value="" 
		          	properties="nextfield ='txtConfirmB'" 
		          	size="20"/>
		        <script>
	          		$('#txtConfirmA').val('<%=request.getAttribute("txtConfirmA")!=null?rsForm.getStartSubmit():ts.toString().substring(0,10)%>');
	          	</script>
	          	</td>
	            <td align="right" width="20%">至：
	            </td>
	            	<td>
            	<fs_c:calendar 
	          	    name="txtConfirmB"
		          	value="" 
		          	properties="nextfield ='SelectStatus'" 
		          	size="20"/>
		          	<script>
	          		$('#txtConfirmB').val('<%=request.getAttribute("txtConfirmB")!=null?rsForm.getEndSubmit():ts.toString().substring(0,10)%>');
	          	</script>
            </td>
          <td width="1" height="25"></td>
        </tr>
      
        <tr >
          <td colspan="4" height="3"></td>
        </tr>
        <tr height="29">
          <td width="6" height="25"></td>
          
      <td height="25" colspan=2> 
        <p><span class="graytext">&nbsp;&nbsp;状态：</span></p>
          </td>
      <td height="25">
<%
        OBHtmlCom.showSignTransStatusListControl(out,"SelectStatus",(rsForm==null || rsForm.getStatus()<0)?2:
            rsForm.getStatus()," onFocus=\"nextfield ='txtMinAmount';\" colspan=\"4\"");
%>
        </td>
          <td width="88" height="25"></td>
        </tr>
      
        <tr >
          <td colspan="5" height="3"></td>
        </tr>
        <tr height="29">
          <td width="6" height="25"></td>
          
      <td width="84" height="25"> 
        <p><span class="graytext">&nbsp;&nbsp;金额：</span></p>
          </td>
          <td width="74" height="25">
            <div align="right" class="graytext">由：</div>
          </td>
          <td width="93" height="25">
            <fs:amount 
	       		form="form1"
       			name="txtMinAmount"
       			value="<%=minAmount %>"
       			
       			nextFocus="txtMaxAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          
      <td width="5%" height="25" align="left"> <div align="right" class="graytext">至：</div>
          </td>
          <td width="423" height="25">
            <fs:amount 
	       		form="form1"
       			name="txtMaxAmount"
       			value="<%=maxAmount %>"
       			
       			nextFocus="txtExecuteA"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="1" height="25"></td>
        </tr>
      
        <tr >
          <td colspan="5" height="3"></td>
        </tr>
        <tr height="29">
          <td width="6" height="25"></td>
          
      <td width="84" height="25" nowrap> 
        <p><span class="graytext">&nbsp;&nbsp;执行日期：</span></p>
          </td>
          <td width="74" height="25">
            <div align="right" class="graytext">由：</div>
          </td>
          <td height="25" >
          	<%  Timestamp tss=Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); %>
           <fs_c:calendar 
						          	    name="txtExecuteA"
							          	value="" 
							          	properties="nextfield =''" 
							          	size="20"/>
							          	  <script>
	          		$('#txtExecuteA').val('<%=request.getAttribute("txtExecuteA")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	          	</script></td>
             <td align="right">至：
	            </td><td>
             <fs_c:calendar 
						          	    name="txtExecuteB"
							          	value="" 
							          	properties="nextfield =''" 
							          	size="20"/>
							          	   <script>
	          		$('#txtExecuteB').val('<%=request.getAttribute("txtExecuteB")!=null?rsForm.getEndExe():tss.toString().substring(0,10)%>');
	          	</script>
            </td>
          <td width="5" height="25"></td>
        </tr>
         <tr>
         <td>&nbsp;</td>
          <td width="120">  
          </td>
          <td >&nbsp;</td><td>&nbsp;</td><td colspan="2">&nbsp;</td>
          <td width="60" align=right colspan="3">
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Query" value=" 查 找 " class="button1" onClick="javascript:doQuery();">
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr><td></td></tr>
      </table>
      <br>
      <br>
      
      <TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
				    <td width="1%">&nbsp;</td>
					<TD width="*%">
						<br><TABLE width="100%" id="flexlist"></TABLE><br>
					</TD>
					<td width="1%">&nbsp;</td>
				</tr>
			</TBODY>
		</TABLE>
      
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="25" valign="top" align="left"  >
            <div align="center">
             
            </div>
          </td>
          <td width="25" valign="top" align="left"  >
            <div align="right"></div>
          </td>
          <td width="326">
            <div align="right"></div>
          </td>
          <td width="134">
          </td>
          <td width="60">
            <div align="right">
            <input type=hidden name="txtisCheck"  value="0"><!--表示要做取消复核的操作　1表示签认 0 表示取消签认-->
            <!--img src="" name="Check1" border="0" style="cursor:hand" onClick="javascript:doSign();"-->
			 <input type="button" name="Check1" class="button1" onClick="doSign();">
			</div>
          <script language="javascript">
          	<%if(rsForm!=null && rsForm.getStatus()>0){%>
          		form1.SelectStatus.value="<%=rsForm.getStatus()%>";
          	<%}%>
            
            if (form1.SelectStatus.value == "<%=OBConstant.SettInstrStatus.CHECK%>") {
                /*签认*/
               /* form1.Check1.src = "/webob/graphics/button_qianrentijiao.gif";*/
				form1.Check1.value = " 签 认 ";
                form1.txtisCheck.value= "1";
            } else {
                /*取消签认*/
                form1.Check1.value = " 取消签认 ";
                form1.Check1.onclick = function(){doCancelSign();};
                form1.txtisCheck.value = "0";
            }
          </script>
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

<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
   <input type="hidden" name="txtisCheck" value="0"><!--表示要做取消复核的操作　1表示复核 0 表示取消复核-->
</form>

<script language="javascript">
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'txtID', name : 'txtID', width : 50, sortable : true, align: 'center'},
			{display: '指令序号', name: 'ID', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?")', width: 120, sortable: true, align: 'center'},
			{display: '汇款用途',  name : 'SNOTE', width : 120, sortable : true, align: 'center'},
			{display: '账号',  name : 'payeracctno', width : 120, sortable : false, align: 'center'},
			{display: '借/贷',  name : 'payername', width : 100, sortable : false, align: 'center'},
			{display: '金额',  name : 'MAMOUNT', width : 100, sortable : true, align: 'center'},
			{display: '对方资料名称',  name : 'mamount', width : 100, sortable : false, align: 'center'},
			{display: '对方资料账号',  name : 'payeeacctno', width : 100, sortable : false, align: 'center'},
			{display: '执行日期',  name : 'DTEXECUTE', width : 100, sortable : true, align: 'center'}
		],//列参数
		title:'业务签认',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.querySignInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		callback: 'toDisabled()'
	});
	
});

function getFormData() 
{
	return $.addFormData("form1","flexlist");
}
var flexlist = 'flexlist';
function toDisabled(){
	$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
		if(n.value == '0'){
			n.disabled = true;
		}
	});
	showAlarm();
}
function doQuery()
{	
	if (doCheckForm()) {
		$.gridReload("flexlist");
 	}
   	if (form1.SelectStatus.value == "<%=OBConstant.SettInstrStatus.CHECK%>") {
		/*签认*/
		/* form1.Check1.src = "/webob/graphics/button_qianrentijiao.gif";*/
		form1.Check1.value = " 签 认 ";
    	form1.txtisCheck.value= "1";
	}else {
  		/*取消签认*/
    	form1.Check1.value = " 取消签认 ";
    	form1.Check1.onclick = function(){doCancelSign();};
      	form1.txtisCheck.value = "0";
	}
}

 //form1.txtMinAmount.value = "<%=rsForm.getFormatMinAmount()%>";
 //rform1.txtMaxAmount.value = "<%=rsForm.getFormatMaxAmount()%>";
 
 /*
 window.onload = function(){
	var trArray = document.getElementsByTagName("tr");
 	for(var i=0;i<trArray.length;i++)
 	{
 		var tr = trArray[i];
 		if(tr.className=="notFalsified")
 		{
 			var tdArray = tr.childNodes;
 			for(var j=0;j<tdArray.length;j++)
 			{
 				var td = tdArray[j];
 				if(!td.onmouseover)
 				{
 					td.id = tr.id + j;
 					td.attachEvent("onmouseover",mouseover);
 					td.attachEvent("onmouseout",mouseout);
 				}
 			}
 		}
 	}	
 }
*/
    function doCheckForm() {
       var fTop,fLov;
        /* 提交日期校验 */
        var starSubmit = form1.txtConfirmA.value;
        var endSubmit = form1.txtConfirmB.value;
        if (starSubmit != "") {
            if(chkdate(starSubmit) == 0) {
                alert("请输入正确的申请开始日期");
                form1.txtConfirmA.focus();
                return false;
            }
        }
        if (endSubmit != "") {
            if(chkdate(endSubmit) == 0) {
                alert("请输入正确的申请结束日期");
                form1.txtConfirmB.focus();
                return false;
            }
        }
        if ((starSubmit != "") && (endSubmit != "")) {
            if (!CompareDate(form1.txtConfirmA, form1.txtConfirmB, "提交日期：起始日期不能大于结束日期")) {
                return false;
            }
        }
        /* 执行日期校验 */
        var startExe = form1.txtExecuteA.value;
        var endExe = form1.txtExecuteB.value;
        if (startExe != "") {
            if(chkdate(startExe) == 0) {
                alert("请输入正确的执行开始日期");
                form1.txtExecuteA.focus();
                return false;
            }
        }
        if (endExe != "") {
            if(chkdate(endExe) == 0) {
                alert("请输入正确的执行结束日期");
                form1.txtExecuteB.focus();
                return false;
            }
        }
        if ((startExe != "") && (endExe != "")) {
            if (!CompareDate(form1.txtExecuteA, form1.txtExecuteB, "执行日期：起始日期不能大于结束日期")) {
                return false;
            }
        }

       fLov = parseFloat(reverseFormatAmount1(form1.txtMinAmount.value));
       fTop = parseFloat(reverseFormatAmount1(form1.txtMaxAmount.value));
       if (fLov > fTop) {
            alert("金额 最小值不能大于最大值");
            return false;
        }
        return true;
    }
    
    //签认功能
    function doSign() {/*选择*/
        var isCheck = false;
        var isFalsified = false;
        $.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
			if(n.checked){
				isCheck = true;
			} 
		});
        if (!isCheck) {
            alert("请选择记录");
            return false;
        }
        
 	   <%
 	   		if(isUseCertification)
 	   		{
 	   %>
 	   			$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
					if(n.checked&&n.isFalsified){
						isFalsified = true;
					} 
				});
 	   <%
 	   		}
 	   %>        


        
        //-------------------添加指纹认证---开始----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //指纹验证
		$.ajax(
		{
			  type:'post',
			  url:"<%=request.getContextPath()%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(form1,1);
					if($("#Ver").val()!=""){
				  	    doSign();// 再次提交
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("指纹认证错误，请重新采集");	
					$("#Ver").val("");
				  	getFingerPrint(form1,1);//加载控件
					if($("#Ver").val()!=""){
				  	    doSign();// 再次提交
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
		
        if(isFalsified)
        {
        	<%
        		if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
        		{
        	%>
        			var btName = "交易中含有非法修改的数据，是否继续签认？"
        			if(!confirm(btName)){
	        			return false;
	        		}
	        
        	<%
        		}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
        		{
        	%>
        			var btName = "交易中含有非法修改的数据，无法签认！"
        			alert(btName);
        			return false;
        	<%
        		}
        	%>
        	
        	
        }else
        {
        	var btName = document.form1.Check1.value;
        	btName = "是否"+IgnoreSpaces(btName)+"?";
	        if(!confirm(btName)){
	        	$("#Ver").val("");
	        	return false;
	        }        	
        }
        
        form1.action = "s004-c.jsp?";
		//form1.target="NewWindow_S";
        //showSending();
        form1.submit();
    }
    
    //取消签认功能
    function doCancelSign() {/*取消选择*/
        var isCheck = false;
        $.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
			if(n.checked){
				isCheck = true;
			} 
		});
        if (!isCheck) {
            alert("请选择记录");
            return false;
        }
        
        var btName = document.form1.Check1.value;
        btName = IgnoreSpaces(btName);
        
        if(!confirm("是否"+btName+"？")){
        	return false;
        }
        form1.action = "s004-c.jsp?";
        //form1.target="NewWindow_S";
        //showSending();

        form1.submit();
    }
    
    function doCheckAll() {
        for (var i = 0; i < document.form1.elements.length; i++) {
            if(document.form1.elements[i].type=="checkbox") {
                document.form1.elements[i].checked = document.form1.txtCheckAll.checked;
            }
        }
        return true;
    }
	function changeCheckAll()
	{
		for (var i = 0; i < document.form1.elements.length; i++) {
            if(document.form1.elements[i].type=="checkbox"  && document.form1.elements[i].name!="txtCheckAll" &&document.form1.elements[i].checked==false) 
			{
                document.form1.txtCheckAll.checked = false;
            }
        }
        return true;
	}
    function doLink(blnNotBeFalsified,id,name) {
   	 	form3.txtID.value = name;
        form3.txtTransType.value = id;
        form3.action = "s003-c.jsp?menu=hidden&blnNotBeFalsified="+blnNotBeFalsified;
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");	
        form3.target = "_formwin";
        form3.submit();
        form3.target = "";
    }
    function queryme() {
       if (doCheckForm()) {
            form1.action="s002-c.jsp";
            form1.target="";
            showSending();
            form1.submit();
       }
    }
    function IgnoreSpaces(Str)
    { 
	    var ResultStr = ""; 
	    Temp=Str.split(" "); //双引号之间是个空格； 
	    for(i = 0; i < Temp.length; i++) 
	    ResultStr +=Temp[i]; 
	    return ResultStr; 
	}
</script>

<script language="javascript">
   // window.name = "Check_Window";  
    firstFocus(form1.SelectType);/*第一个获焦点**/
    //setSubmitFunction("doQuery()");
    setFormName("form1");
</script>

<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>