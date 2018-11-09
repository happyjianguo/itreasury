<%--
/*
 * 程序名称：V413.jsp
 * 功能说明：业务复核查询显示页面
 * 作　　者：刘琰
 * 完成日期：2003年09月23日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.safety.util.*,
                 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%
	//标题变量
	String strTitle = null;
%>

<%	  
	/* 用户登录检测与权限校验 */
	
	String strStartDate = null;//上一个页面传来的开始日期
	
	String strEndDate = null;//上个页面传来的结束日期
	
	double txtMinAmount = 0.0;
	double txtMaxAmount = 0.0;
	
	try{ 
		
		//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
		/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
           
        /* 显示文件头 */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
   		
		Iterator rs = (Iterator)request.getAttribute("return");
		
		//查询信息对象
		QueryCapForm rsForm = new QueryCapForm();
		if(request.getAttribute("FormValue") != null)
		{
       		rsForm = (QueryCapForm)request.getAttribute("FormValue");
        }
		long lTransType = -1;
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}

        	if(request.getAttribute("txtConfirmA")!=null)
		{
			
			strStartDate =(String)request.getAttribute("txtConfirmA");
			System.out.println("txtConfirmA============="+strStartDate);
		}
		
		
		if(request.getAttribute("txtConfirmB")!=null)
		{
			
			strEndDate = (String)request.getAttribute("txtConfirmB");
			System.out.println("txtConfirmB============="+strEndDate);
		}
		
		if(request.getParameter("txtMinAmount") != null && request.getParameter("txtMinAmount").trim().length() > 0) {
			txtMinAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMinAmount")));
       	}
		if(request.getParameter("txtMaxAmount") != null && request.getParameter("txtMaxAmount").trim().length() > 0) {
			txtMaxAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMaxAmount")));
      	}
		 
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
 		boolean blnNotBeFalsified = true;
		String[] nameArray = null;
		String[] valueArray = null;
		
%>
<script language="javascript" src="/webob/js/glass.js"></script>

<safety:resources />

<%--下是查询结果--%>
<form name="form1" method="post">
<input type="hidden" name="lClientID" value="<%= sessionMng.m_lClientID %>">
<input type="hidden" name="lCurrencyID" value="<%= sessionMng.m_lCurrencyID %>">
<input type="hidden" name="lUserID" value="<%= sessionMng.m_lUserID %>">
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">

<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">取消复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>

      <table width="100%" border="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="7" height="1"></td>
        </tr>
        
        <tr class="MsoNormal"> 
          <td width="5" height="29"></td>
          <td width="110" height="25">
            <p><span class="MsoNormal">&nbsp;&nbsp;交易类型：</span></p>
          </td>
          <td width="50" height="25" class="MsoNormal">&nbsp;</td>
          <td width="160" height="25" class="MsoNormal">
<%
		//OBHtmlCom.showQueryTypeListControl1(out,"SelectType",(rsForm == null)?-1:rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" ",false);
		OBHtmlCom.showQueryCheckTypeListControl(out,"SelectType",(rsForm == null)?-1:rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,true);
%>       
		<input type=hidden name="SelectStatus"  value="<%=  OBConstant.SettInstrStatus.CHECK %>">     
        </td>
        <td colspan="3">
        </td>
      </tr>
		<tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;提交日期：</span></p>
          </td>
          <td height="25" class="MsoNormal" width="100">
            <div align="right" >由：&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td  height="25" class="MsoNormal">
          	<%  Timestamp ts=Env.getSystemDateTime(); %>
          	<fs_c:calendar 
			          	    name="txtConfirmA"
				          	value="" 
				          	properties="nextfield ='txtConfirmB'" 
				          	size="18"/>
			<script>
		          		$('#txtConfirmA').val('<%=request.getAttribute("txtConfirmA")!=null?request.getAttribute("txtConfirmA"):ts.toString().substring(0,10)%>');
		    </script>
	    </td>
		<td  width="200" align="right"> <div align="right" class="MsoNormal">至：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
		<td>
            	<fs_c:calendar 
	          	    name="txtConfirmB"
		          	value="" 
		          	properties="nextfield ='txtMinAmount'" 
		          	size="18"/>
		       <script>
	          		$('#txtConfirmB').val('<%=request.getAttribute("txtConfirmB")!=null?request.getAttribute("txtConfirmB"):ts.toString().substring(0,10)%>');
	    		</script>
		 </td>
          <td height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;金额：</span></p>
          </td>
          <td height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">最小值：</div>
          </td> 
          <td width="" height="25" class="MsoNormal">
	        <fs:amount 
	       		form="form1"
       			name="txtMinAmount"
       			value="<%=txtMinAmount %>"
       			
       			nextFocus="txtMaxAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
  		</td>
   		<td>
	   		<div align="right" width="20" class="MsoNormal">最大值：</div>
     	</td>
     	<td>
        	<fs:amount 
	       		form="form1"
       			name="txtMaxAmount"
       			value="<%=txtMaxAmount %>"
       			
       			nextFocus="txtExecuteA"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
     	</td>
     	<td height="25" class="MsoNormal"></td>
       </tr>
     
        <tr class="MsoNormal">
          <td colspan="7" height="1" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;执行日期：</span></p>
          </td>
          <td height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">由：&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td  height="25" class="MsoNormal">
          <%  Timestamp tss=Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); %>
          	<fs_c:calendar 
			          	    name="txtExecuteA"
				          	value="" 
				          	properties="nextfield ='txtExecuteB'" 
				          	size="18"/>
			 <script>
	          		$('#txtExecuteA').val('<%=request.getAttribute("txtExecuteA")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	   		 </script>
   		 </td>
   		 <td > <div align="right" class="MsoNormal">至：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
   		 <td>
            <fs_c:calendar 
			          	    name="txtExecuteB"
				          	value="" 
				          	properties="nextfield =''" 
				          	size="18"/>
			<script>
	          		$('#txtExecuteB').val('<%=request.getAttribute("txtExecuteB")!=null?rsForm.getEndExe():tss.toString().substring(0,10)%>');
	    	</script>
		  </td>
		  <td height="25" class="MsoNormal"></td>
        </tr>
        
        <tr>
          <td colspan="6"></td> 	
          <td>
            <div align="right">
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Submitv00204" value=" 查 找 " class="button1" onClick="javascript:doQuery();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
        <tr height="5"><td></td></tr>
      </table>
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
	
      <table width="100%" border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <div align="right">
			<input type=hidden name="txtisCheck"  value="0"><!--表示要做取消复核的操作　1表示复核 0 表示取消复核-->
			<input type=hidden name="isBatchCheck"  value="1"><!--表示批量复核-->
			<!--img src="" name="Check1"   border="0" style="cursor:hand" onClick="javascript:doCheck();"-->
			<input type="button" name="Check1" class="button1" align="right" onClick="javascript:doCheck();">&nbsp;&nbsp;</div>
		  	<script language="javascript"> 
                    if (form1.SelectStatus.value == "<%= OBConstant.SettInstrStatus.SAVE %>")
                    {
                          /*复核*/
                          //form1.Check1.src = "/webob/graphics/button_fuhe.gif";
						  form1.Check1.value = " 复核 ";
                          form1.txtisCheck.value= "1";
                    }else{
                          /*取消复核*/
                          //form1.Check1.src = "/webob/graphics/button_QuXiaoFuHe.gif";
						  form1.Check1.value = " 取消复核 ";
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
<%--查看详情的提交Form--%>
<form name="form3" method="post" sytle="display:none">
   <input type="text" class="box" name="txtID" size="24" value="" style="display:none">
   <input type="text" class="box" name="txtTransType" size="24" value="" style="display:none">
   <input type=hidden name="txtisCheck"  value="0"><!--表示要做取消复核的操作　1表示复核 0 表示取消复核-->
   <input type="hidden" name="SelectType" value="<%= lTransType %>">

</form>
 <script language="javascript">
 $(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'txtID', name : 'txtID', width : 50, sortable : true, align: 'center'},
			{display: '指令序号', name: 'sbatchno', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?")', width: 120, sortable: false, align: 'center'},
			{display: '交易类型',  name : 'ncurrencyid', width : 120, sortable : false, align: 'center'},
			{display: '账号',  name : 'SPAYEEACCTNO', width : 120, sortable : false, align: 'center'},
			{display: '借/贷',  name : 'payername', width : 100, sortable : false, align: 'center'},
			{display: '金额',  name : 'ntranstype', width : 100, sortable : false, align: 'center'},
			{display: '对方资料名称',  name : 'mamount', width : 100, sortable : false, align: 'center'},
			{display: '对方资料账号',  name : 'payeeacctno', width : 100, sortable : false, align: 'center'},
			{display: '执行日期',  name : 'payeename', width : 100, sortable : false, align: 'center'},
			{display: '汇款用途',  name : 'spayeeprov', width : 100, sortable : false, align: 'center'}
		],//列参数
		title:'取消复核',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckInfo',//要调用的方法
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

function doQuery()
{	
	if(doCheckForm()){
		$.gridReload("flexlist");
	}
}
function toDisabled(){
	$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
		if(n.value == '0'){
			n.disabled = true;
			n.style.display = "none";
		}
	});
	showAlarm();
}

 function doCheckForm()
 {
       var fTop,fLov;

	   //add by sun start 2003-02-19
		/* 提交日期校验 */
		var starSubmit = form1.txtConfirmA.value;
		var endSubmit = form1.txtConfirmB.value;
		if (starSubmit != "")
		{
			if(chkdate(starSubmit) == 0)
			{
				alert("请输入正确的申请开始日期");
				form1.txtConfirmA.focus();
				return false;
			}
		}
		if (endSubmit != "")
		{
			if(chkdate(endSubmit) == 0)
			{
				alert("请输入正确的申请结束日期");
				form1.txtConfirmB.focus();
				return false;
			}
		}
		if ((starSubmit != "") && (endSubmit != ""))
		{	if (!CompareDate(form1.txtConfirmA, form1.txtConfirmA, "提交日期：起始日期不能大于结束日期"))
			{
				return false;
			}
		}
		/* 执行日期校验 */
		var startExe = form1.txtExecuteA.value;
		var endExe = form1.txtExecuteB.value;
		if (startExe != "")
		{
			if(chkdate(startExe) == 0)
			{
				alert("请输入正确的执行开始日期");
				form1.txtExecuteA.focus();
				return false;
			}
		}
		if (endExe != "")
		{
			if(chkdate(endExe) == 0)
			{
				alert("请输入正确的执行结束日期");
				form1.txtExecuteB.focus();
				return false;
			}
		}
		if ((startExe != "") && (endExe != ""))
		{	if (!CompareDate(form1.txtExecuteA, form1.txtExecuteB, "执行日期：起始日期不能大于结束日期"))
			{
				return false;
			}
		}
		//add by sun end 2003-02-19

       /*校验金额*/
       if (!checkAmount(form1.txtMinAmount,0,"金额 最小值"))
             return false;
       if (!checkAmount(form1.txtMaxAmount,0,"金额 最大值"))
             return false;

       fLov =  parseFloat(reverseFormatAmount1(form1.txtMinAmount.value));
       fTop = parseFloat(reverseFormatAmount1(form1.txtMaxAmount.value));
       if (fLov > fTop)
       {
             alert("金额 最小值不能大于最大值");
             return false;
       }
       return true;
 }
 var flexlist = "flexlist";
 function doCheck()/*复核---取消复核*/
 {
		var isCheck = false;
		$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
			if(n.checked){
				isCheck = true;
			} 
		});
	
       if (!isCheck)
       {
             alert("请选择记录");
             return false;
       }
	   var checkOrUncheck ;
	   if( form1.txtisCheck.value == "1" )
	   {
	   		checkOrUncheck = "复核，是否决定？"
	   }
	   else
	   {
	   		checkOrUncheck = "是否取消复核？"
	   }
	   
	   if(!confirm(checkOrUncheck))
	   {
	   		return false;
	   }
       form1.action = "C415.jsp";
       showSending(); 
	   form1.submit();
	   
 }
 function doLink(blnNotBeFalsified,id,name)
 {
        form3.txtID.value = name;
        form3.txtTransType.value = id;
        form3.action = "C414.jsp?menu=hidden&blnNotBeFalsified="+blnNotBeFalsified;
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");	
        form3.target = "_formwin";
        form3.submit();
        form3.target = "";
 }

 form1.txtMinAmount.value = "<%= rsForm.getFormatMinAmount() %>";
 form1.txtMaxAmount.value = "<%= rsForm.getFormatMaxAmount() %>";
 firstFocus(form1.SelectType);
 //setSubmitFunction("doQuery()");
 setFormName("form1");
 
 function checkAll(obj){
	var checkboxes = document.getElementsByName("txtCheckbox");
	if(checkboxes.length==0)
		return;
	for(var i=0;i<checkboxes.length;i++){
		checkboxes[i].checked=obj.checked;
	}
}

function isCheckedAll()
{
	var isCheck = true;
	for(var i=0;i<document.form1.txtCheckbox.length;i++)
	{
		if(document.form1.txtCheckbox[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.all.checked = true;
	else
		document.form1.all.checked = false;
    if(document.form1.txtCheckbox.length == undefined){
		document.form1.all.checked = document.form1.txtCheckbox.checked;
	}		
}
 
 </script>


<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>