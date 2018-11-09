<%
/**
 * 页面名称 ：1119-v.jsp
 * 页面功能 : 银团合同审核取消
 * 作    者 ：曾海燕
 * 日    期 ：2003-10-14
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="java.util.*,
com.iss.itreasury.loan.util.*,
java.net.URLEncoder,
com.iss.itreasury.ebank.util.*,			
com.iss.itreasury.ebank.obdataentity.*,	
com.iss.itreasury.ebank.obquery.bizlogic.*,
com.iss.itreasury.ebank.obquery.dataentity.*,
com.iss.itreasury.util.*,
com.iss.itreasury.system.bizlogic.*,
com.iss.itreasury.loan.contract.dataentity.*,
com.iss.itreasury.loan.contractcontent.dataentity.*,
com.iss.itreasury.ebank.approval.bizlogic.*,
com.iss.itreasury.ebank.approval.dataentity.ApprovalTracingInfo
" 
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	  try
	  {
 		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		ContractQueryInfo qInfo = new ContractQueryInfo();
		qInfo = (ContractQueryInfo)request.getAttribute("qInfo");
		ContractInfo info = new ContractInfo();
		info = (ContractInfo)request.getAttribute("ContractInfo");
		
		boolean bIsEdit = false;//是否第一级审核
				
		String strTitle = "";
		strTitle = LOANConstant.LoanType.getName(qInfo.getTypeID()) + "合同" + "——" + "查看";
		
		//显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,strTitle,Constant.YesOrNo.NO);
%>

<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<script language="JavaScript">
	function frmExport() //导出合同 action on the Form : frmPage
	{
		if (theform.ContentID.value != null)
		{
			lContentID = theform.ContentID.value;
		}
		else
		{
			for(var i=0;i<theform.ContentID.length;i++)
			{
				if (theform.ContentID[i].checked == true)
				{
					lContentID = theform.ContentID[i].value;
				}
			}
		}

		if (confirm("是否导出合同？"))
		{
			window.open('../../content/c106a-c.jsp?lContentID='+lContentID, "", "width=800,height=600,status=yes,toolbar=yes,menubar=yes,location=no,resizable=yes,scrollbars=yes");
		}
		
	} //function frmExport() 导出合同
	
	function frmModifyExPlan(planID) //执行计划  action on the Form : frmPage
	{
		var url = "q094-v.jsp?control=view&isYU=2&nTmpID=" + planID;
		window.open(url, "执行计划", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	}
	
	function frmPreApplyAudit( loanID, loanTypeID) //查看历史审核意见  
	{
		var url = "q039-v.jsp?loanID="+loanID+"&loanType="+loanTypeID ;
		window.open(url, "查看历史审核意见", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	}
	
	function turnPage(clicked)
	{
		var iPage;
		var sSubmit;
		var iAllPage;
		iAllPage = parseInt(frmPage.lPageCount.value);
		if (!InputValid(frmPage.txtPageNo, 1, "int", 0, 1, 10, "页数"))
			return false;
		iPage = parseInt(frmPage.txtPageNo.value);
	
		if (iAllPage < 1)
		{
			return false;
		}
		if ((iPage < 1) || (iPage > iAllPage))
		{
			return false;
		}
	
		switch (clicked)
		{
			case 1 :
				if ((iPage == "") || (iPage < 0) || (iPage > iAllPage))
				{
					return false;
				}
				else
				{
					frmPage.txtPageNo.value = iPage;
				}
				break;
			case 2 :
				if (iPage < 1)
				{
					return false;
				}
				else
				{
					frmPage.txtPageNo.value = 1;
				}
				break;
			case 3 :
				if (iPage > 1)
				{
					frmPage.txtPageNo.value = iPage - 1;
				}
				else
				{
					return false;
				}
				break;
			case 4 :
				if (iPage < iAllPage)
				{
					frmPage.txtPageNo.value = iPage + 1;
				}
				else
				{
					return false;
				}
				break;
			case 5 :
				if (iPage >= iAllPage)
				{
					return false }
				else
				{
					frmPage.txtPageNo.value = iAllPage;
				}
				break;
		}
		return true;
	}
	
	function writed()
	{
		if (theform.txtDtStart.value != "" && theform.txtInterval.value != ""  && theform.txtDtEnd.value =="")
		{
			if (!checkDate(theform.txtDtStart, 1, "合同起始日期"))
			{
				return false;
			}
			theform.txtDtEnd.value = addDate(theform.txtDtStart.value, theform.txtInterval.value);
		}
	}
	
	function addDate(strInputDate, lMonth)
	{
		var temp, s;
		temp = new String(strInputDate);
		s = new String("");
		for (var i = 0; i <= temp.length - 1; i++)
		{
			if (temp.charAt(i) == "-" || temp.charAt(i) == "/")
			{
				s = s + "/";
			}
			else
			{
				if (isNaN(Number(temp.charAt(i))))
				{
					alert("请输入正确的开始日期");
					document.theform.txtDtStart.focus();
					return false;
				}
				else
				{
					s = s + temp.charAt(i);
				}
			}
		} //for(var i=0;i<=temp.length-1;i++)
		var dtDate;
		dtDate = new Date(s);
		var strDate;
		var yy, mm, temp;
	
		var dtDay = dtDate.getDate();
		temp = parseInt(lMonth) + dtDate.getMonth() + 1;
		var dtMonth = temp % 12;
		var dtYear = dtDate.getYear() + parseInt(temp / 12);
	
		if (parseInt(dtMonth) == 0)
		{
			dtMonth = '12';
			dtYear = parseInt(dtYear) - 1;
		}
		strDate = dtYear + "-" + dtMonth + "-" + dtDay;
		return strDate;
	}
	
	function frmSubmit(frm)
	{
		if (frm.name == "theform")
		{
			showSending();
			frm.submit();
			return true;
		}
		else
		{
			var lMax = parseInt(frm.lPageCount.value);
			if (!InputValid(frm.txtPageNo, 1, "int", 1, 1, lMax, "页数"))
				return (false);
			showSending();
			frmPage.submit();
			return true;
		}
	}
	
	function frmSave() //提交合同 action on the Form : theform
	{
		if (!checkDate(document.theform.txtDtStart, 1, "合同起始日期"))
		{
			return (false);
		}
		if (!checkDate(document.theform.txtDtEnd, 1, "合同结束日期"))
		{
			return (false);
		}
		if (!checkContractStartEndDate(document.theform.txtDtStart, document.theform.txtDtEnd))
			return (false);
			
		if (theform.AreaType.value == -1)
		{
			alert("请选择地区分类");
			theform.AreaType.focus();
			return (false);
		}
		
		if (theform.IndustryType1.value == -1)
		{
			alert("请选择行业分类1");
			theform.IndustryType1.focus();
			return (false);
		}
		if (theform.IndustryType2.value == -1)
		{
			alert("请选择行业分类2");
			theform.IndustryType2.focus();
			return (false);
		}
				
		for( var i=1;i<=6;i++ )
		{
			eval("sValue=theform.LoanAmount"+i);
			if(!InputValid(sValue,0,"float",0,1,10,"承贷金额"))
			 {
					 return false;
			 }
			 
			 eval("sValue=theform.LoanRate"+i);
			if(!InputValid(sValue,0,"float",0,1,10,"承贷比例"))
			 {
					 return false;
			 }
			 		 
			 eval("sValue=theform.AssureAmount"+i);
			if(!InputValid(sValue,0,"float",0,1,10,"担保金额"))
			 {
					 return false;
			 }
			 
			 eval("sValue=theform.CreditAmount"+i);
			if(!InputValid(sValue,0,"float",0,1,10,"信用金额"))
			 {
					 return false;
			 }
			 			 
		}
	
		if (confirm("是否提交合同？"))
		{
			document.theform.control.value = "save";
			frmSubmit(document.theform);
		}
	}
	
	function frmCancel() //取消合同 action on the Form : theform
	{
		if (confirm("是否取消合同？"))
		{
			document.theform.control.value = "cancel";
			frmSubmit(document.theform);
		}
	}
	
	function frmReturn() //返回
	{
			form2.submit();
	}
	
	function frmAction(action) 
	{
		 var IsSubmit = 0;
		 
		if (confirm("确定取消合同吗？"))
		{
			IsSubmit = 1;
		}
		  
		if ( IsSubmit == 1 )
		{
			theform.control.value = action;
			theform.submit();
		}	
	}	
</script>

<TABLE border=0 class=top height=700 width=730>
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B><%=strTitle%></B>
	<form name="form2"  action="l102-c.jsp"  method="post">
			<input type="hidden" name="ctrlContractIDFrom" value="<%=qInfo==null?-1:qInfo.getContractIDFrom()%>">
			<input type="hidden" name="ctrlContractIDTo" value="<%=qInfo==null?-1:qInfo.getContractIDTo()%>">
			<input type="hidden" name="ctrlConsignClientID" value="<%=qInfo==null?-1:qInfo.getConsignClientID()%>">
			<input type="hidden" name="ctrlClientID" value="<%=qInfo==null?-1:qInfo.getClientID()%>">
			<input type="hidden" name="txtAmountFrom" value="<%=qInfo==null?0:qInfo.getAmountFrom()%>">
			<input type="hidden" name="txtAmountTo" value="<%=qInfo==null?0:qInfo.getAmountTo()%>">
			<input type="hidden" name="txtLoanStart" value="<%=qInfo==null?"":qInfo.getFormatLoanStart()%>">
			<input type="hidden" name="txtLoanEnd" value="<%=qInfo==null?"":qInfo.getFormatLoanEnd()%>">
			<input type="hidden" name="txtIntervalNum" value="<%=qInfo==null?"":qInfo.getFormatIntervalNum()%>">
			<input type="hidden" name="txtInputStart" value="<%=qInfo==null?"":qInfo.getFormatInputStart()%>">
			<input type="hidden" name="txtInputEnd" value="<%=qInfo==null?"":qInfo.getFormatInputEnd()%>">
			<input type="hidden" name="selStatusID" value="<%=qInfo==null?-1:qInfo.getStatusID()%>">
			<input type="hidden" name="lOrderParam" value="<%=qInfo==null?-1:qInfo.getOrderParam()%>">
			<input type="hidden" name="ctrlContractIDFromCtrl" value="<%=qInfo==null?"":qInfo.getContractNoFrom()%>">
			<input type="hidden" name="ctrlContractIDToCtrl" value="<%=qInfo==null?"":qInfo.getContractNoTo() %>">
			<input type="hidden" name="ctrlConsignClientIDCtrl" value="<%=qInfo==null?"":qInfo.getConsignClientName()%>">
			<input type="hidden" name="ctrlClientIDCtrl" value="<%=qInfo==null?"":qInfo.getClientName()%>">
			<input type="hidden" name="lDesc" value="<%=qInfo==null?1:qInfo.getDesc()%>">
			<input type="hidden" name="type" value="<%=qInfo==null?-1:qInfo.getTypeID()%>">
			<input type="hidden" name="action" value="<%=qInfo==null?-1:qInfo.getActionID()%>">
			</form>
	</TD>
  </TR>
  <TR>
    <TD height=726 vAlign=top>
		<form name="theform"  action="l106-c.jsp"  method="post">
			<input type="hidden" name="ctrlContractIDFrom" value="<%=qInfo==null?-1:qInfo.getContractIDFrom()%>">
			<input type="hidden" name="ctrlContractIDTo" value="<%=qInfo==null?-1:qInfo.getContractIDTo()%>">
			<input type="hidden" name="ctrlConsignClientID" value="<%=qInfo==null?-1:qInfo.getConsignClientID()%>">
			<input type="hidden" name="ctrlClientID" value="<%=qInfo==null?-1:qInfo.getClientID()%>">
			<input type="hidden" name="txtAmountFrom" value="<%=qInfo==null?0:qInfo.getAmountFrom()%>">
			<input type="hidden" name="txtAmountTo" value="<%=qInfo==null?0:qInfo.getAmountTo()%>">
			<input type="hidden" name="txtLoanStart" value="<%=qInfo==null?"":qInfo.getFormatLoanStart()%>">
			<input type="hidden" name="txtLoanEnd" value="<%=qInfo==null?"":qInfo.getFormatLoanEnd()%>">
			<input type="hidden" name="txtIntervalNum" value="<%=qInfo==null?"":qInfo.getFormatIntervalNum()%>">
			<input type="hidden" name="txtInputStart" value="<%=qInfo==null?"":qInfo.getFormatInputStart()%>">
			<input type="hidden" name="txtInputEnd" value="<%=qInfo==null?"":qInfo.getFormatInputEnd()%>">
			<input type="hidden" name="selStatusID" value="<%=qInfo==null?-1:qInfo.getStatusID()%>">
			<input type="hidden" name="lOrderParam" value="<%=qInfo==null?-1:qInfo.getOrderParam()%>">
			<input type="hidden" name="ctrlContractIDFromCtrl" value="<%=qInfo==null?"":qInfo.getContractNoFrom()%>">
			<input type="hidden" name="ctrlContractIDToCtrl" value="<%=qInfo==null?"":qInfo.getContractNoTo() %>">
			<input type="hidden" name="ctrlConsignClientIDCtrl" value="<%=qInfo==null?"":qInfo.getConsignClientName()%>">
			<input type="hidden" name="ctrlClientIDCtrl" value="<%=qInfo==null?"":qInfo.getClientName()%>">
			<input type="hidden" name="lDesc" value="<%=qInfo==null?1:qInfo.getDesc()%>">
			<input type="hidden" name="type" value="<%=qInfo==null?-1:qInfo.getTypeID()%>">
			<input type="hidden" name="action" value="<%=qInfo==null?-1:qInfo.getActionID()%>">
			<input type="hidden" name="contractID"  value="<%=info.getContractID()%>">
			<input type="hidden" name="control" >
      <TABLE align=center border=0 width=100%>
        <TBODY>
        <TR>
          <TD height=32 width=1>&nbsp;</TD>
          <TD colSpan=4 height=32 vAlign=middle>合同编号：<%=info.getContractCode()%></TD>
          <TD colSpan=3 height=32 vAlign=middle>
            <DIV align=left>申请书编号：<%=info.getApplyCode()%></DIV></TD></TR>
			
        <TR>
          <TD height=17 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=17 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR>
          <TD height=190 width=1>&nbsp;</TD>
            <TD align=left colSpan=7 vAlign=top> 
              <TABLE height=36 width="100%">
                <TBODY> 
                <TR> 
                  <TD colSpan=2 height=34><U>贷款资料</U></TD>
                  <TD height=34 width="24%">&nbsp;</TD>
                  <TD height=34 width="10%">&nbsp;</TD>
                  <TD height=34 width="22%">&nbsp;</TD>
                </TR>
                <TR> 
                  <TD colSpan=2 height=24>借款单位：</TD>
                  <TD height=24 width="24%"> 			  
				  <%=info.getBorrowClientName()%>
                  </TD>
                  <TD height=24 width="10%">客户编号：</TD>
                  <TD height=24 width="22%"> 
                    <INPUT class=box disabled name=txtCode size=12 value="<%=info.getBorrowClientCode()%>">
                  </TD>
                </TR>
                <TR> 
                  <TD height=23 width="12%"> 借款金额： </TD>
                  <TD height=23 width="4%"> 
                    <DIV align=right>￥</DIV>
                  </TD>
                  <TD height=23 width="24%"> 
                    <INPUT class=tar disabled name=txtAmount size=18 value="<%=info.getFormatLoanAmount()%>">
                  </TD>
                  <TD height=23 width="10%"> 期限： </TD>
                  <TD height=23 width="22%"> 
                    <INPUT class=box disabled name=txtInterval size=2 value="<%=info.getIntervalNum()%>">
                    月 </TD>
                </TR>
                <TR> 
                  <TD height=23 colspan="2">合同执行利率：</TD>
                  <TD height=23 width="24%">
                    <input class=tar disabled name=txtAmount2 size=18 value="<%=info.getFormatInterestRate()%>">
                    % </TD>
                  <TD height=23 width="10%">代理费率：</TD>
                  <TD height=23 width="22%"><input class=tar disabled name=txtAmount2 size=12 value="<%=info.getFormatChargeRate()%>">
				  ‰</TD>
                </TR>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
                <TR> 
                  <TD colSpan=2 height=32><U>合同资料</U></TD>
                  <TD height=32 width="24%">&nbsp;</TD>
                  <TD height=32 width="10%">&nbsp;</TD>
                  <TD height=32 width="22%">&nbsp;</TD>
                </TR>
                <TR> 
                  <TD colSpan=1 height=32><font color='#FF0000'>*&nbsp;</font>合同起始日期:</TD>
                  <TD colSpan=2 height=32 width="24%"> 
                  <% if (bIsEdit) { %>
                  <fs_c:calendar 
			         	    name="txtDtStart"
				          	value="" 
				          	properties="nextfield ='document.theform.txtDtEnd'" 
				          	size="20"/>
				  			          	  <script>
	          		$('#txtDtStart').val('<%=info.getFormatLoanStart()%>');
	          	</script>
				  <script>
	          		$('#txtDtStart').blur(
	          			function (){
	          				writed();
	          			}
	          		);
	          	</script>
				          	<%}else{ %>
	          	 <fs_c:calendar 
	         	    name="txtDtStart"
		          	value="" 
		          	size="20"/>
		          	  			          	  <script>
	          		$('#txtDtStart').val('<%=info.getFormatLoanStart()%>');
	          	</script>
		         <script>
	          		$('#txtDtStart').attr('disabled','true');
	          	 </script>
				          	<%} %>
	                  <!-- 
	                    <INPUT class=box name="txtDtStart"  
						<% //if (bIsEdit) { %>onblur="writed()" onfocus="nextfield='document.theform.txtDtEnd';" <%//}else { out.println("disabled");}%> 
						value="<%=//info.getFormatLoanStart()%>" >
						<A href="javascript:show_calendar('theform.txtDtStart');"
				            onmouseout="window.status='选择日期';return true;"
				            onmouseover="window.status='选择日期';return true;"> 
							<IMG BORDER=0 HEIGHT=16 SRC="/webob/graphics/calendar.gif" WIDTH=17>
							</A> 
						 -->	
                  </TD>
                  <TD colSpan=1 height=32><font color='#FF0000'>*&nbsp;</font>合同结束日期:</TD>
                  <TD  height=32 width="22%"> 
                   <% if (bIsEdit) { %>
                  <fs_c:calendar 
			         	    name="txtDtEnd"
				          	value="" 
				          	properties="nextfield ='document.theform.AreaType'" 
				          	size="20"/>
				<script>
	          		$('#txtDtEnd').val('<%=info.getFormatLoanEnd()%>');
	          	</script>
				          	<%}else{ %>
	          	 <fs_c:calendar 
	         	    name="txtDtEnd"
		          	value="<%=info.getFormatLoanEnd()%>" 
		          	size="20"/>
		          	<script>
	          		$('#txtDtEnd').val('<%=info.getFormatLoanEnd()%>');
	          	</script>
  					<script>
	          			$('#txtDtEnd').attr('disabled','true');
	          		</script>		          	
				          	<%} %>
				    <!--
					<INPUT class=box name="txtDtEnd"   value="<%=//info.getFormatLoanEnd()%>" 
					<% //if (bIsEdit) { %> onfocus="nextfield='document.theform.AreaType';"  <%//}else { out.println("disabled");}%> >
                    <A href="javascript:show_calendar('theform.txtDtEnd');"
			            onmouseout="window.status='选择日期';return true;"
			            onmouseover="window.status='选择日期';return true;">
						 <IMG BORDER=0 HEIGHT=16 SRC="/webob/graphics/calendar.gif" WIDTH=17>
						 </A> 
						   -->
                  </TD>
                </TR>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
                <tr> 
                  <td colspan=2 height=32><u>借款用途:</u></td>
                  <td height=32 colspan="3"> 
                    <textarea name="textfield" cols="50" disabled><%=info.getLoanPurpose()==null?"":info.getLoanPurpose()%></textarea>
                  </td>
                </tr>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
                <tr> 
                  <td colspan=5 height=2> 
                    <table border=0 cellpadding=0 cellspacing=0 width="100%">
                      <tr> 
                        <td colspan=4 height=33><u><span lang=ZH-CN 
                        style="FONT-SIZE: 10pt"><font size=2> 
						贷款担保资料明细</font></span></u></td>
                      </tr>
                      <tr> 
                        <td colspan=4> 
                          <table align=center border=0 class=ItemList width="100%">
                            <tbody> 
                            <tr align=center class="tableHeader"> 
                              <td  height=24 class=ItemTitle  width="17%" > 
							  <font size=2>客户编号</font></td>
                              <td class=ItemTitle width="16%"> 
                                <div align=center><font size=2>单位名称</font></div>
                              </td>
                              <td class=ItemTitle  width="15%">
							  <font  size=2>担保方式</font></td>
                              <td class=ItemTitle  width="14%"> 
                                <div align=center>
								<font class=ItemTitle size=2>联系人</font>
								</div>
                              </td>
                              <td class=ItemTitle  width="14%"> 
                                <div align=center><font size=2>电话</font></div>
                              </td>
                              <td class=ItemTitle width="13%"> 
                                <div align=center>
								<font size=2>担保金额</font>
								</div>
                              </td>
                              <td class=ItemTitle width="11%" align="center">
							  <font size=2>担保比例(%)</font></td>
                            </tr>
							<%
								if (info.getAssure() != null)
								{
									Iterator it = info.getAssure().iterator();
									AssureInfo aInfo = new AssureInfo();
									if ( it != null )
									{
										while (it.hasNext())
										{
											aInfo = (AssureInfo)it.next();
							%>
                            <tr > 
                              <td class=ItemBody height=20 width="17%" align="center">
							  <font size=2><%=aInfo.getClientCode()%></font></td>
                              <td class=ItemBody  width="16%" align="center">
							  <font size=2><%=aInfo.getClientName()%></font></td>
                              <td class=ItemBody width="15%" align="center">
							  <font size=2><%=aInfo.getAssureTypeName()%></font></td>
                              <td class=ItemBody  width="14%" align="center">
							  <font size=2><%=aInfo.getContact()==null?"":aInfo.getContact()%></font></td>
                              <td class=ItemBody  width="14%" align="center">
							  <font size=2><%=aInfo.getPhone()==null?"":aInfo.getPhone()%></font></td>
                              <td class=ItemBody  width="13%" align="right">
							  <font size=2><%=aInfo.getFormatAmount()%></font></td>
                              <td class=ItemBody  width="11%" align="right"><%=aInfo.getFormatRate()%></td>
                            </tr>
							<%		}
									}
								}
								else
								{
							%>
							<tr > 
                              <td class=ItemBody height=20 width="17%" align="center">
							  </td>
                              <td class=ItemBody  width="16%" align="center">
							  </td>
                              <td class=ItemBody width="15%" align="center">
							  </td>
                              <td class=ItemBody  width="14%" align="center">
							  </td>
                              <td class=ItemBody  width="14%" align="center">
							  </td>
                              <td class=ItemBody  width="13%" align="right">
							  </td>
                              <td class=ItemBody  width="11%" align="right"></td>
                            </tr>
                            <%}%>                           
                            </tbody> 
                          </table>
                        </td>
                      </tr>
					  <tr>
					  <td colspan="8">&nbsp;</td>
					  </tr>
                      <tr> 
                        <td width="18%"><font size=2>贷款金额：</font></td>
                        <td width="4%"> 
                          <div align=right><font size=2>￥</font></div>
                        </td>
                        <td width="31%"><font size=2> 
                          <input class=tar disabled name=textfield33 size=18 value="<%=info.getFormatExamineAmount()%>">
                          </font></td>
                        <td width="47%">&nbsp;</td>
                      </tr>
                      <tr> 
                        <td height=33 width="18%"><font size=2><u><span 
                        lang=ZH-CN 
                        style="FONT-SIZE: 10pt">其中</span></u></font></td>
                        <td height=33 width="4%">&nbsp;</td>
                        <td height=33 width="31%">&nbsp;</td>
                        <td height=33 width="47%">&nbsp;</td>
                      </tr>
                      <tr> 
                        <td width="18%"><font size=2>有担保贷款总额： </font></td>
                        <td width="4%"> 
                          <div align=right><font size=2>￥</font></div>
                        </td>
                        <td width="31%"><font size=2> 
                          <input class=tar disabled name=textfield224 size=18 value="<%=info.getFormatAssureAmount()%>">
                          </font></td>
                        <td width="47%"><font size="2">所占比例： 
                          <input class=tar disabled name=textfield2242 size=10 value="<%=info.getFormatAssureRate()%>">
                          %</font></td>
                      </tr>
                      <tr> 
                        <td width="18%"><font size=2>信用贷款总额：</font></td>
                        <td width="4%"> 
                          <div align=right><font size=2>￥</font></div>
                        </td>
                        <td width="31%"><font size=2> 
                          <input class=tar disabled name=textfield4 size=18 value="<%=info.getFormatCreditAmount()%>">
                          </font></td>
                        <td width="47%"><font size="2">所占比例： 
                          <input class=tar disabled name=textfield22422 size=10 value="<%=info.getFormatCreditRate()%>">
                          %</font></td>
                      </tr>
                      <tbody> </tbody> 
                    </table>
                  </td>
                </tr>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
                <TR> 
                  <TD colSpan=2 height=32><font color="#FF0000" size="2">* </font><font size="2"><U>贷款分类</U></font></TD>
                  <TD height=32 width="24%"><font size="2">&nbsp;</font></TD>
                  <TD height=32 width="10%"><font size="2">&nbsp;</font></TD>
                  <TD height=32 width="22%"><font size="2">&nbsp;</font></TD>
                </TR>
                <TR> 
                  <TD colSpan=5 height=2> 
                    <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
                      <TBODY> 
                      <TR> 
                        <TD height=33><font size="2">按地区分类： 
                          <%
							String sTemp = "";
							if ( bIsEdit )
							{
								sTemp = "onfocus=nextfield='document.theform.IndustryType1';";
							}
							else
							{
								sTemp = " disabled ";
							}
							
							LOANHTML.showAreaTypeListControl(out,"AreaType", info.getAreaType(), sTemp );
							
							%>
						  </font></TD>
                        <TD height=33 width="46%">&nbsp;</TD>
                        <TD height=33 width="4%">&nbsp; </TD>
                      </TR>
                      <TR> 
                        <TD height=33><font size="2">按行业分类1:
                          <%
							if ( bIsEdit )
							{
								sTemp = "onfocus=nextfield='document.theform.IndustryType2'; ";
							}
							else
							{
								sTemp = " disabled ";
							}
							LOANHTML.showIndustryType1ListControl(out,"IndustryType1", info.getIndustryType1(), sTemp);
							%>       
						  </font></TD>
                            <TD height=33 width="46%"><font size="2">按行业分类2： 
                              <%
							if ( bIsEdit )
							{
								sTemp = "onfocus=nextfield='BankName1';";
							}
							else
							{
								sTemp = " disabled ";
							}
						LOANHTML.showIndustryType2ListControl(out,"IndustryType2", info.getIndustryType2(), sTemp );
						%>
                              </font></TD>
                        <TD height=33 width="4%">&nbsp;</TD>
                      </TR>
                      </TBODY> 
                    </TABLE>
                  </TD>
                </TR>
                </TBODY> 
              </TABLE>
            <HR>
			<table width="100%" border="0">
                <tr> 
                  <td colspan="6" height="30"><u><font size="2">贷款人资料</font></u></td>
                </tr>
<%
			String[] sBankName = null; //银行名称
			String[] dLoanAmount = null; //承贷金额
			String[] dLoanRate = null; //承贷比例
			String[] dAssureAmount = null; //担保金额
			String[] dCreditAmount = null; //信用金额
			
			sBankName = info.getYTInfo().getBankName();
			dLoanAmount = info.getYTInfo().getLoanAmount();
			dLoanRate = info.getYTInfo().getLoanRate();
			dAssureAmount = info.getYTInfo().getAssureAmount();
			dCreditAmount = info.getYTInfo().getCreditAmount();
			
			sTemp="";
			
			for (int i=0;i<dLoanAmount.length;i++)
			{
			
					if (i==0)
					{
						sTemp = "牵头行/代理行";
					}
					else if (i>0)
					{
						sTemp = "参加行"+i;
					}
				
%>
                <tr> 
                  <td ><font size="2"><%=sTemp%>：</font></td>
                  <td   ><font size="2">&nbsp;</font></td>
                  <td ><font size="2">&nbsp;</font></td>
                  <td ><font size="2">&nbsp;</font></td>
                  <td ><font size="2">&nbsp;</font></td>
                  <td ><font size="2">&nbsp;</font></td>
                </tr>
                <tr> 
                  <td><font size="2">银行名称：</font></td>
                  <td> <font size="2"> 
                    <input   type="text" name="BankName<%=i+1%>"  size="18" class="box" 
					<% if (bIsEdit) { %>onfocus="nextfield='LoanRate<%=i+1%>';"<%}else { out.println("disabled");}%> 
					value="<%=sBankName[i]%>">
                    </font></td>
                  <td><font size="2">承贷金额：￥</font></td>
                  <td> <font size="2"> 
					<input name="LoanAmount<%=i+1%>" 
					<% if (!bIsEdit) { out.println("disabled");}%>
					value="<%=dLoanAmount[i]%>"   size="18" class="tar"  readonly>
                    </font></td>
                  <td><font size="2">承贷比例：</font></td>
                  <td> <font size="2"> 
					<input name="LoanRate<%=i+1%>" value="<%=dLoanRate[i]%>"   size="12"   maxlength="10" class="tar"  
					 onBlur="calculateLoanAmount('<%=i+1%>')" 
					 <% if (bIsEdit) { %>onfocus="nextfield='AssureAmount<%=i+1%>';" <%}else { out.println("disabled");}%> >
                    % </font></td>
                </tr>
                <tr> 
                  <td><font size="2">担保金额：￥</font></td>
                  <td> <font size="2">
				  <% if (bIsEdit) { %>
					<SCRIPT LANGUAGE="JAVASCRIPT">
	        		createAmountCtrl("theform","AssureAmount<%=i+1%>","<%=dAssureAmount[i]%>","CreditAmount<%=i+1%>","");
	          		</SCRIPT> 
					<%}else { %>
					<input type="text" class="tar"  size="18" value="<%=dAssureAmount[i]%>" disabled>
					<%}%>
                    </font></td>
                  <td><font size="2">信用金额：￥</font></td>
                  <td> <font size="2"> 
				  <%
				  	if (i<(dLoanAmount.length-1))
					{
						sTemp = "BankName"+(i+2);
					}
					else if (i==(dLoanAmount.length-1))
					{
						sTemp = "submitfunction";
					}
				  	
				  %>
				  <% if (bIsEdit) { %>
					<SCRIPT LANGUAGE="JAVASCRIPT">
	        		createAmountCtrl("theform","CreditAmount<%=i+1%>","<%=dCreditAmount[i]%>","<%=sTemp%>","");
	          		</SCRIPT> 
					<%}else { %>
					<input type="text" class="tar"  size="18" value="<%=dCreditAmount[i]%>" disabled>
					<%}%>
                    </font></td>
                  <td><font size="2">&nbsp;</font></td>
                  <td><font size="2">&nbsp;</font></td>
                </tr>
                <tr> 
                  <td colspan="6"> 
                    <hr>
                  </td>
                </tr>
<%
		}
%>
	</table>
            </TD>
          </TR>
          <TR> 
            <TD height=36 width=1>&nbsp;</TD>
            <TD align=left colSpan=2 height=36>参加行审批决议： </TD>
		</tr>
		<tr>
			<TD height=36 width=1>&nbsp;</TD>
            <TD align=left height=36 colspan="6">
			<iframe src="../../attach/AttachFramea.jsp?lID=<%=info.getContractID()%>&lTypeID=<%=LOANConstant.AttachParentType.YTAPPROVALRESOLUTION%>&sCaption=链接参加行审批决议&showOnly=true"  width=600 height="100" scrolling="Auto" frameborder="0" name="iFrame" >
			</iframe>
            </TD>
          </TR>
          <TR> 
            <TD height=36 width=1>&nbsp;</TD>
            <TD align=left colSpan=7 height=36> 
              <hr>
            </TD>
          </TR>
        <TR>
          <TD height=36 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=36>
            <DIV align=center><U><B>填写贷款合同文本</B></U></DIV></TD></TR>
        <TR>
          <TD height=133 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=133>
            <TABLE width="100%">
              <TBODY>
		<TR>
			<TD colSpan=4>
				<HR>
			</TD>
		</TR>
        <TR>
		  <TD colSpan=4>
		  <TABLE>
		   <TR>
			  <TD ALIGN=LEFT COLSPAN="5" HEIGHT=12>
			       正式合同文本：
			  </td>
			  <TD WIDTH="1" HEIGHT="4">&nbsp;</TD>
		   </TR>
		   <TR>
			  <TD ALIGN=LEFT COLSPAN="6" HEIGHT=48>
				<iframe src="../../attach/AttachFramea.jsp?lID=<%=info.getContractID()%>&lTypeID=<%=LOANConstant.AttachParentType.CONTRACT%>&sCaption=<%=URLEncoder.encode("链接正式合同文本")%>&showOnly=true"  width=600 height="100" scrolling="Auto" frameborder="0" name="iFrame" >
				</iframe>
				</td>
           </TR>
		  </TABLE>
		</TD>
		<TR>
	</TBODY>
	</TABLE>
</TD>
</TR>
<tr>
          <TD height=2 >&nbsp;</TD>
          <TD align=left colSpan=7 height=2>
            <HR>
          </TD></TR>
        <TR>
          <TD height=2 >&nbsp;</TD>
          <TD align=left colSpan=7  >
            <TABLE cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD height=42 ><U>执行计划详细</U></TD>
                <TD height=42 ><U>申请书历史审核意见</U></TD></TR>
              <TR>
                <TD >
							<INPUT class=button name=btnModifyExPlan onclick="Javascript: frmModifyExPlan('<%=info.getPlanVersionID()%>');" type="button" value=" 查看执行计划 ">
                </TD>
                <TD >

							<INPUT class=button name=btnPreApplyAudit onclick="frmPreApplyAudit( '<%=info.getLoanID()%>', '<%=info.getLoanTypeID()%>')"  type="button" value=" 查看历史审核意见 ">

                </TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=2>
            <HR>
          </TD></TR>
        <TR>
          <TD height=2 >&nbsp;</TD>
          <td colspan="8">
		  <div align="right"> 
        <!-- form1 -->
        <table width="99%" border="1" bordercolor="#999999" cellpadding="0" cellspacing="0">
          <tr> 
            <td> 
              <table width="100%" border="0">
                <tr> 
                  <td colspan="3" height="28"><u>本公司审批意见</u></td>
                  <td height="28">&nbsp;</td>
                  <td height="28">&nbsp;</td>
                </tr>
                <tr> 
                  <td >历史审核意见:</td>
                  <td colspan="4"> <br>
                    <table  border="0" align="left" height="50%" class="ItemList">
                            <tr class="tableHeader"> 
                              <td class="ItemTitle" width="12%" height="20"> <div align="center">序列号</div></td>
                              <td class="ItemTitle" width="21%" height="20"> <div align="center">意见内容</div></td>
                              <td class="ItemTitle" width="21%" height="20"> <div align="center">审核人</div></td>
                              <td class="ItemTitle" width="20%" height="20"> <div align="center">审核决定</div></td>
                              <td class="ItemTitle" width="26%" height="20"> <div align="center">日期和时间</div></td>
                            </tr>
<%
								Collection  cApproval = (Collection)request.getAttribute("cApproval");
								if (cApproval != null)
								{
									Iterator it =cApproval.iterator();
									ApprovalTracingInfo ATInfo = new ApprovalTracingInfo();
									
									if ( it != null )
									{
										for (;it.hasNext();)
										{
											ATInfo = (ApprovalTracingInfo)it.next();
%>
                            <tr> 
                              <td class="ItemBody" width="12%" height="20" align="center"><%=ATInfo.getSerialID()%></td>
                              <td class="ItemBody" width="21%"  align="center"><%=ATInfo.getOpinion()==null?"":ATInfo.getOpinion()%></td>
                              <td class="ItemBody" width="21%"  align="center"><%=ATInfo.getUserName()%></td>
                              <td class="ItemBody" width="20%"  align="center"><%=Constant.ApprovalDecision.getName(ATInfo.getResultID())%></td>
                              <td class="ItemBody" width="26%"  align="center"><%=DataFormat.getDateString(ATInfo.getApprovalDate())%></td>
                            </tr>
<%
									}
								}
							}
							else
							{
%>
<tr> 
                              <td class="ItemBody" width="12%"  height="20"  align="center"></td>
                              <td class="ItemBody" width="21%"  align="center"></td>
                              <td class="ItemBody" width="21%"  align="center"></td>
                              <td class="ItemBody" width="20%"  align="center"></td>
                              <td class="ItemBody" width="26%"  align="center"></td>
                            </tr>
<%}%>
                          </table>
                  </td>
                </tr>
        		<tr>
                    <td><% if (info.getStatusID()==LOANConstant.ContractStatus.SUBMIT){%>下一级审核人:<%}%></td>
                    <td colspan="4">
                    <%
                    	if (info.getStatusID()==LOANConstant.ContractStatus.SUBMIT){
                    		String nextCheckUserName=info.getCheckUserName();
                    %>
                    <input type="text" name="nextCheckUserName" class="box" size="10" value="<%=nextCheckUserName%>" disabled > 
                    <%		
                    	}
                    %> 
                    </td>
                  </tr>
               
              </table>
            </td>
          </tr>

        </table>
       
        <p align=right>录入人:<%=info.getInputUserName()%>&nbsp;&nbsp;&nbsp;录入日期:<%=DataFormat.getDateString(info.getInputDate())%> &nbsp;&nbsp;&nbsp;状态:<%=LOANConstant.ContractStatus.getName(info.getStatusID())%><br> </p>
       
      </div>
		  
		  </td>
		  </TR>
		  </TBODY>
		  </TABLE>
		  </form>
		  </TD>
		  </TR>
		  </TBODY>
		  </TABLE>
		  <table border=0 width=650><tr><td width=400>&nbsp;</td><td align=right >
          <input type="button" name="return"  class="button" onClick="window.close();" value=" 关 闭 ">
		</td></tr>
		
		</table>
<%
			OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"客户管理", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
<%@ include file="/common/SignValidate.inc" %>