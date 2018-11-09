<%--
/*
 * 程序名称：s008-v.jsp
 * 功能说明：新奥定期签认查询显示页面
 * 作　　者：wangzhen
 * 完成日期：2011-04-06
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
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
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<%
    Log.print("\n111*******进入页面--ebank/capital/sign/s008-v.jsp******\n");
    //标题变量
    String strTitle = "";
    try {
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
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

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
      	
      	if(request.getAttribute("txtMinAmount")!=null&&request.getParameter("txtMinAmount").trim().length() > 0)
      	{
      		String min = (String)request.getAttribute("txtMinAmount");
      		double minAmount = Double.parseDouble(DataFormat.reverseFormatAmount(min));
      		rsForm.setMinAmount(minAmount);
      		
      	}
      	
      	if(request.getAttribute("txtMaxAmount")!=null&&request.getParameter("txtMaxAmount").trim().length() > 0)
      	{
      		String max = (String)request.getAttribute("txtMaxAmount");
      		double maxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(max));
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
%>

<%--下是查询结果--%>
<form name="form1" method="post">
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="sign" type="hidden" value="">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务签认-定期</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

    </td>
  </tr>

</table>
<br/>

      <table width=80% border=0 align="" cellpadding="0" cellspacing="0" class=normal id="table1">
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
			OBHtmlCom.showQueryTypeListControl3(out,"SelectType",(rsForm == null)?-1:
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
            <div align="right" class="graytext">由</div>
          </td>
          <td  height="25" colspan="3">
		<%  Timestamp ts=Env.getSystemDateTime(); %>
		   		<fs_c:calendar 
		          	    name="txtConfirmA"
			          	value="" 
			          	properties="nextfield ='txtConfirmB'" 
			          	size="20"/>
			   	<script>
	          		$('#txtConfirmA').val('<%=request.getAttribute("txtConfirmA")!=null?rsForm.getStartSubmit():ts.toString().substring(0,10)%>');
	          	</script>
            <span class="graytext">至
            </span>
          		  <fs_c:calendar 
		          	    name="txtConfirmB"
			          	value="" 
			          	properties="nextfield ='SelectStatus'" 
			          	size="20"/>
			      <script>
	          		$('#txtConfirmB').val('<%=request.getAttribute("txtConfirmB")!=null?rsForm.getStartSubmit():ts.toString().substring(0,10)%>');
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
        OBHtmlCom.showSignTransStatusListControl(out,"SelectStatus",(rsForm == null)?-1:
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
            <div align="right" class="graytext">由<%=sessionMng.m_strCurrencySymbol%></div>
          </td>
          <td width="93" height="25">
            <script  language="JavaScript">
                createAmountCtrl('form1','txtMinAmount','','txtMaxAmount','',1);
            </script>
          </td>
          
      <td width="5%" height="25" align="left"> <div align="right" class="graytext">至<%=sessionMng.m_strCurrencySymbol%></div>
          </td>
          <td width="423" height="25">
            <script  language="JavaScript">
                createAmountCtrl('form1','txtMaxAmount','','txtExecuteA','',1);
            </script>
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
            <div align="right" class="graytext">由</div>
          </td>
          <td height="25" colspan="4">
          	<%  Timestamp tss=Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); %>
            <fs_c:calendar 
						          	    name="txtExecuteA"
							          	value="" 
							          	properties="nextfield =''"
							          	size="20"/>
				  <script>
	          		$('#txtExecuteA').val('<%=request.getAttribute("txtExecuteA")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	          	</script>
             <span class="graytext">至
            </span>
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
          <td width="60" align=right>
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Query" value=" 查 找 " class="button1" onClick="javascript:doQuery();">
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr><td></td></tr>
      </table>
      <br>
      <table width="80%" border="0" cellspacing="0" cellpadding="0">
       
      </table>
      <br>
     <table width=80% border="1" align="" cellpadding="0" cellspacing="0" class=normal id="table1">
	    <thead>
        <tr >
          <td width="70"  height="18" rowspan="2">
            <p align="center">
            全选
              <input type="checkbox" name="txtCheckAll" value="1" onclick="doCheckAll()">
				</p>
          </td>
          
          <td width="124"  valign="middle" height="18" rowspan="2">
            <p align="center"> 指令序号</p>
          </td>
          
          <td width="75"  valign="middle" height="18" rowspan="2">
            <p align="center"> 汇款用途</p>
          </td>
          
          <td  height="28" valign="middle" rowspan="2"  width="67">
            <div align="center">账号</div>
          </td>
         
          <td  height="28" valign="middle" rowspan="2"  width="44"><div align="center">借/贷</div></td>
          
          <td  height="28" valign="middle" rowspan="2"  width="73"><div align="center">金额</div></td>
          
          <td  height="0" valign="middle" colspan="2" >
            <div align="center">对方资料</div>
          </td>
          
          
          <td  height="18" valign="middle" rowspan="2"  width="88">
            <div align="center">执行日期</div>
          </td>
        </tr>
        
        <tr >
          <td  height="18" valign="middle" width="133" nowrap>
            <div align="center">名称</div>
          </td>
          
          <td  height="18" valign="middle"  width="133" nowrap>
            <div align="center" nowrap>账号</div>
          </td>
        </tr>
        </thead>
        
<%
        int iCount = 0;         //计数器
        String strDataLast = "";//前一个指针
        String strData = "";    //当前指针
        String strNote = "";
        while (rs != null && rs.hasNext()) {
            FinanceInfo info=(FinanceInfo)rs.next();
            iCount++;
            strNote = info.getNote()==null?"":info.getNote();
           // strData = info.getFormatConfirmDate();
            if (iCount == 1) {  //第一次
                strDataLast = strData;
%>
        <tr  >
          <td width="26" align="left" height="20"></td>
          
          <td colspan="17" align="left"  height="20">
            <p>提交日期<span >：<%=(DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"" %></span></p>
          </td>
        </tr>
<%
            //如果两个指针不同
            }
            if (!strDataLast.equalsIgnoreCase(strData)) {
%>
        <tr valign="middle">
          <td width="26" align="left"  height="20"></td>
          
          <td colspan="17" align="left"  height="20">
            <p>提交日期<span >：<%=(DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"" %></span></p>
          </td>
        </tr>
<%
                strDataLast = strData;
            }
%>
        
        <tr>
          <td width="70" valign="top" align="center" >
            <div align="center">
              <input type="checkbox" name="txtCheckbox" value="<%=iCount%>" onclick="changeCheckAll();">
            </div>
          </td>
          
          <td width="124" valign="top" align="left"  >
            <div align="center"><u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name; form3.txtTransType.value = this.id;doSee();" 
            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u></div>
           <input type="text" name="txtID" size="24" value="<%=info.getID()%>" style="display:none">
           <input type="text" name="txtTransType" size="24" value="<%=info.getTransType()%>" style="display:none">
           <input type="hidden" name='<%=info.getID()+"_dtmodify"%>' size="24" value="<%=info.getDtModify()==null?"":info.getDtModify()+""%>">
          </td>
            
			<td height="20" nowrap align="center" title = "<%=strNote%>" >
				<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
			</td>
            
          <td  width="100"  valign="top" nowrap><%=NameRef.getNoLineAccountNo(info.getPayerAcctNo().toString())%></td>
          
          <td  width="44"  valign="top">
<%
            
          
                out.print("借");
           
%>
          </td>
          
          <td  valign="top">
            <div align="right"><%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()==""?"0.00":info.getFormatAmount()%></div>
          </td>
          
          <td width="133" valign="top" nowrap><%=info.getPayeeName()%></td>
          
     <td   width="133" valign="top" nowrap><%=NameRef.getNoLineAccountNo(info.getPayeeAcctNo())%></td>
          
         
          <td width="80" valign="top" nowrap>
            <div align="center"><span ><%=info.getFormatExecuteDate()%></span></div>
          </td>
        </tr>
<%
        }
        if (iCount == 0) {//表示没记录，显示一空行
%>
        
        <tr >
          <td width="26" valign="top" align="left" height="14"  >
          </td>
          
          <td width="168" valign="top" align="left"  >
          </td>
          
          <td width="75" valign="top" align="left" >
          </td>
          
          <td width="67"  valign="top"></td>
          
          <td width="44" valign="top"></td>
          
          <td width="73"  valign="top"></td>
          
          <td width="133" valign="top"></td>
          
          <td width="80" valign="top"></td>
          
        
          <td width="88" valign="top">
          </td>
        </tr>
<%
        }
%>
        
      </table>
      <br>
<%  
        if (rs != null) {//返回，有数据，才显示下面的按钮
%>
      <table width="80%" border="0" cellspacing="0" cellpadding="0">
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
			 <input type="button" name="Check1" class="button1" onClick="doSign();">
			</div>
          <script language="javascript">
            form1.SelectStatus.value="<%=rsForm.getStatus()%>";
            if (form1.SelectStatus.value == "<%=OBConstant.SettInstrStatus.CHECK%>") {
                /*签认*/
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
<%
        }
%>
</form>

<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
   <input type="hidden" name="txtisCheck" value="0"><!--表示要做取消复核的操作　1表示复核 0 表示取消复核-->
</form>

<script language="javascript">
 form1.txtMinAmount.value = "<%=rsForm.getFormatMinAmount()%>";
 form1.txtMaxAmount.value = "<%=rsForm.getFormatMaxAmount()%>";

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
        for(var i=0; i<document.form1.elements.length; i++) {
            if(document.form1.elements[i].type=="checkbox") {
                if (document.form1.elements[i].checked == true) {
                    isCheck = true;
                    break;
                }
            }
        }
        if (!isCheck) {
            alert("请选择记录");
            return false;
        }
        
        var btName = document.form1.Check1.value;
        btName = IgnoreSpaces(btName);
        
        if(!confirm("是否"+btName+"？")){
        	return false;
        }
        form1.sign.value = "fixd";
        form1.action = "s004-c.jsp?";
        //form1.target="NewWindow_S";
        //showSending();

        form1.submit();
    }
    
    //取消签认功能
    function doCancelSign() {/*取消选择*/
        var isCheck = false;
        for(var i=0; i<document.form1.elements.length; i++) {
            if(document.form1.elements[i].type=="checkbox") {
                if (document.form1.elements[i].checked == true) {
                    isCheck = true;
                    break;
                }
            }
        }
        if (!isCheck) {
            alert("请选择记录");
            return false;
        }
        
        var btName = document.form1.Check1.value;
        btName = IgnoreSpaces(btName);
        
        if(!confirm("是否"+btName+"？")){
        	return false;
        }
        form1.sign.value = "fixd";
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
    function doSee() {
        form3.action = "s003-c.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");	
        form3.target = "_formwin";
        form3.submit();
        form3.target = "";
    }
    function doQuery() {
       if (doCheckForm()) {
            form1.sign.value="fixd";
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