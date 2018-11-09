<%
/**
 * 页面名称 ：q037-v.jsp
 * 页面功能 : 贴现合同审核
 * 作    者 ：曾海燕
 * 日    期 ：2003-10-14
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@page import="java.util.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
java.net.URLEncoder,
com.iss.itreasury.ebank.util.*,			
com.iss.itreasury.ebank.obdataentity.*,	
com.iss.itreasury.ebank.obquery.bizlogic.*,
com.iss.itreasury.ebank.obquery.dataentity.*,
com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo,
com.iss.itreasury.system.approval.bizlogic.*,
com.iss.itreasury.loan.contract.dataentity.*,
com.iss.itreasury.loan.contractcontent.dataentity.*
" 
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	  try
	  {
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
		if ( info.getInputUserID() == sessionMng.m_lUserID )
		{
			bIsEdit = true;
		}
				
		String strTitle = "";
		strTitle = LOANConstant.LoanType.getName(qInfo.getTypeID()) + "合同" + "——" + "审核";
		
		//显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,strTitle,Constant.YesOrNo.NO);
		
%>



<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
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
	
	function findDiscountBill(lContractID) //贴现票据  action on the Form : frmPage
	{
		var url = "q026-v.jsp?lContractID="+lContractID+"&control=view&isSM=<%=Constant.YesOrNo.NO%>";
		window.open(url, "贴现票据", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	}
	
	function findDiscountRate(lContractID) //贴现利息  action on the Form : frmPage
	{
		var url = "q027-v.jsp?lContractID="+lContractID+"&control=view&isSM=<%=Constant.YesOrNo.NO%>";
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
	<form name="form2"  action="../loan/l102-c.jsp"  method="post">
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
	<form name="theform"   method="post" action="../loan/l106-c.jsp">
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
			<input type="hidden" name="bIsEdit"  <% if (bIsEdit) { out.println("value='1'");}else{out.println("value='0'");}%> >
      <TABLE align=center border=0 width=730>
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
				  <A href="<%=Env.EBANK_URL%>loan/query/q006-c.jsp?lLoanID=<%=info.getLoanID()%>&lClientID=<%=info.getBorrowClientID()%>&lLoanType=<%=info.getLoanTypeID()%>&txtAction=modify&statusID=<%=info.getStatusID()%>&mType=client" target="_blank">
				  <%=info.getBorrowClientName()%>
				  </A> 
                  </TD>
                  <TD height=24 width="10%">客户编号：</TD>
                  <TD height=24 width="22%"> 
                    <INPUT class=box disabled name=txtCode size=12 value="<%=info.getBorrowClientCode()%>">
                  </TD>
                </TR>
                <TR> 
                  <TD height=23 width="12%"> 贴现利率： </TD>
                  <TD height=23 width="4%"> 
                    <DIV align=right></DIV>
                  </TD>
                  <TD height=23 width="24%" colspan="3"> 
                    <INPUT class=tar disabled name=txtAmount size=18 value="<%=info.getFormatDiscountRate()%>"> %
                  </TD>
                </TR>
                <TR> 
                  <TD height=23 colspan="2">汇总贴现金额：</TD>
                  <TD height=23 width="24%">
                    <%=sessionMng.m_strCurrencySymbol%><input class=tar disabled name=txtAmount2 size=18 value="<%=info.getFormatExamineAmount()%>">
                    </TD>
                  <TD height=23 width="10%">汇总贴现利息：</TD>
                  <TD height=23 width="22%">
				  <%=sessionMng.m_strCurrencySymbol%><input class=tar disabled name=txtAmount2 size=18 value="<%=info.getFormatDiscountInterest()%>"></TD>
                </TR>
				<TR> 
                  <TD height=23 colspan="2">汇总贴现核定金额：</TD>
                  <TD height=23 width="24%" colspan="3">
                    <%=sessionMng.m_strCurrencySymbol%><input class=tar disabled name=txtAmount2 size=18 value="<%=info.getFormatCheckAmount()%>">
                    </TD>
				</TR>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
				<TR> 
                  <TD height=23 colspan="5">
				  <INPUT class=button name=btnModifyExPlan onclick="findDiscountBill('<%=info.getContractID()%>')" type="button" value=" 贴现票据明细表 ">
				 <INPUT class=button name=Submit42242 onclick="findDiscountRate('<%=info.getContractID()%>')" type=button value="贴现票据计息明细表">
				  					</TD>
				</TR>
                <tr> 
                  <td colspan=5 height=32> 
                    <hr>
                  </td>
                </tr>
                </TBODY> 
              </TABLE>
            <HR>	
        <TR>
          <TD height=36 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=36>
            <DIV align=center><U><B>填写贷款合同文本</B></U></DIV></TD></TR>
        <TR>
          <TD height=233 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=233>
            <TABLE width="100%">
              <TBODY>
              <TR>
                  <TD colSpan=4> 
                    <TABLE align=center border=0 class=ItemList  width="100%">
                      <TBODY> 
                      <TR align=center class="tableHeader"> 
                        <TD class=ItemTitle height=24 width="14%"></TD>
                        <TD class=ItemTitle width="16%"> 
                          <div align="center">序列号</div>
                        </TD>
                        <TD class=ItemTitle  width="33%"> 
                          <div align="center">合同名称</div>
                        </TD>
                        <TD class=ItemTitle width="37%">单位名称</TD>
                      </TR>
<%								int lSemicolonIndex = 0;
									int lStringLength  = 0;
									String PageName = "";
									String sTemplate = "";

								if (info.getContractContent() != null)
								{
									Iterator it = info.getContractContent().iterator();
									ContractContentInfo cInfo = new ContractContentInfo();
									if ( it != null )
									{
										for (int n = 0 ;it.hasNext();n++)
										{
											cInfo = (ContractContentInfo)it.next();
%>
                      <TR align=center> 
                        <TD class=ItemBody height=24 width="14%"> 
                          <INPUT name="ContentID" type="radio" value="<%=cInfo.getID()%>" <%  if (n==0){out.println("checked");} %>>
                        </TD>
                        <TD class=ItemBody height=24 width="16%"> 
                          <div align="center"><%=n+1%></div>
                        </TD>
                        <TD class=ItemBody height=23 width="33%"> 
                          <div align="center">
						  <!--<A href="../../content/c001a-c.jsp?lContentID=<%=cInfo.getID()%>&lAction=<%=qInfo.getActionID()%>&PageName=<%=cInfo.getPageName()%>&PageNo=1"  target="_blank">-->
						  <%=cInfo.getContractType()%>
						  <!--</A>-->
						  </div>
                        </TD>
                        <TD class=ItemBody height=24 width="37%"><%=cInfo.getClientName()%></TD>
                      </TR>
<%									}
									}
								}
								else
								{
%>
					<TR align=center> 
                        <TD class=ItemBody height=20 width="14%"> 
                        </TD>
                        <TD class=ItemBody width="16%"> 
                        </TD>
                        <TD class=ItemBody  width="33%"> 
                        </TD>
                        <TD class=ItemBody  width="37%"></TD>
                      </TR>
<%
								}
%>
                      </TBODY> 
                    </TABLE>
					  <BR>
					  <INPUT class=button name=btnExportContract onclick="Javascript: frmExport();" type="button" value="导出并打印">
                  </TD>
				   </TR>
				   
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
				<iframe src="../../attach/AttachFramea.jsp?lID=<%=info.getContractID()%>&lTypeID=<%=LOANConstant.AttachParentType.CONTRACT%>&sCaption=<%=URLEncoder.encode("链接正式合同文本")%>&showOnly=true"  width=730 height="100" scrolling="Auto" frameborder="0" name="iFrame" >
				</iframe>
           </TR>
		  </TABLE>
		</TD>
		<TR>
	</TBODY>
	</TABLE>
</TD>

        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=2>
            <TABLE cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD height=42 width="78%"><U>申请书历史审核意见</U></TD></TR>
              <TR>
                <TD width="78%">

							<INPUT class=button name=btnPreApplyAudit onclick="frmPreApplyAudit( '<%=info.getLoanID()%>', '<%=info.getLoanTypeID()%>')" type="button" value=" 查看历史审核意见 ">

                </TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left colSpan=7 height=2>
            <HR>
          </TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
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
                  <td width="20%">历史审核意见:</td>
                  <td colspan="4"> <br>
                    <table width="550" border="0" align="left" height="50%" class="ItemList">
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
                              <td class="ItemBody" width="12%"   height="20" align="center"><%=ATInfo.getSerialID()%></td>
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