<%--
 页面名称 ：a910-v.jsp
 页面功能 : 复核/取消复核显示页面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.bizlogic.TransDiscountHome" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script> 

  
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
		OBHtml.showOBHomeHead(out,sessionMng,Env.getClientName(),Constant.YesOrNo.NO);
		
		//定义变量  对应后台的DataEntity
        java.sql.Timestamp tsDtExecute = null;
		java.sql.Timestamp tsDtInputDate = null;
		java.sql.Timestamp tsDtInterestStart = null;
		java.sql.Timestamp tsDtModify = null;
		long lID = -1;
		double dMAmount = 0.0;
		double dMOverDueInterest = 0.0;
		double dMReturnedAmount = 0.0;
		long lNBankID = -1;
		long lNCashFlowID = -1;
		long lNCheckUserID = -1;
		long lNClientID = -1;
		long lNCurrencyID = -1;
		long lNDepositAccountID = -1;
		long lNDiscountAccountID = -1;
		long lNDiscountBillID = -1;
		long lNDiscountContractID = -1;
		long lNDiscountNoteID = -1;
		long lNInputUserID = -1;
		long lNIsReturned = SETTConstant.BooleanValue.ISFALSE;
		long lNOfficeID = -1;
		long lNStatusID = -1;
		long lNTransactionTypeID = -1;
		String strSAbstract = "";
		String strSCheckAbstract = "";
		String strSTransNo = "";
		long lNAbstractID = -1;

		//设置回显数据
		String strClientNo="";

		//页面辅助变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;
		
		strAction = (String)request.getAttribute("strAction");

		String strTemp = null;
		strTemp = (String)request.getAttribute("tsDtExecute");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDtExecute = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsDtInputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDtInputDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsDtInterestStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDtInterestStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsDtModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDtModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("dMAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dMAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("dMOverDueInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dMOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("dMReturnedAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dMReturnedAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("lNBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNCashFlowID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNCashFlowID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNCheckUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNCurrencyID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNCurrencyID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNDepositAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNDepositAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNDiscountAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNDiscountAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNDiscountBillID");
Log.print("\n\n v016 承兑票据号 lNDiscountBillID ＝"+strTemp+"\n\n");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNDiscountBillID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNDiscountContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNDiscountContractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNDiscountNoteID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNDiscountNoteID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNInputUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNIsReturned");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNIsReturned = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strSAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strSCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strSTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strSTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lNAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNAbstractID = Long.valueOf(strTemp).longValue();
		}	
		
		//回显数据
		strClientNo=NameRef.getClientCodeByID(lNClientID);
		if(strClientNo==null)
			strClientNo="";

		//从传过来的对象中取值
		TransRepaymentDiscountInfo info = (TransRepaymentDiscountInfo)request.getAttribute("matchResult");

		if(info != null )
		{
Log.print("\n\n v016 中， info is not null !\n\n");
			Log.print("\n\n v016 info is not null \n\n");
			tsDtExecute = info.getDtExecute();
			tsDtInputDate = info.getDtInputDate();
			tsDtInterestStart = info.getDtInterestStart();
			tsDtModify = info.getDtModify();
Log.print("\n\n v016 tsDtModify="+tsDtModify+"\n\n");
			lID = info.getID();
			Log.print("V016  --lID1:"+lID);
			dMAmount = info.getMAmount();
			dMOverDueInterest = info.getMOverDueInterest();
			dMReturnedAmount = info.getMReturnedAmount();
			lNBankID = info.getNBankID();
			lNCashFlowID = info.getNCashFlowID();
			lNCheckUserID = info.getNCheckUserID();
			lNClientID = info.getNClientID();
Log.print("\n\n v016 lNClientID="+lNClientID+"\n\n");
			lNCurrencyID = info.getNCurrencyID();
			lNDepositAccountID = info.getNDepositAccountID();
			lNDiscountAccountID = info.getNDiscountAccountID();
			lNDiscountBillID = info.getNDiscountBillID();
Log.print("\n\n v016 承兑票据号 lNDiscountBillID ＝"+lNDiscountBillID+"\n\n");
			lNDiscountContractID = info.getNDiscountContractID();
			lNDiscountNoteID = info.getNDiscountNoteID();
			lNInputUserID = info.getNInputUserID();
			lNIsReturned = info.getNIsReturned();
			lNOfficeID = info.getNOfficeID();
			lNStatusID = info.getNStatusID();
			lNTransactionTypeID = info.getNTransactionTypeID();
			strSAbstract = info.getSAbstract();
			strSCheckAbstract = info.getSCheckAbstract();
			strSTransNo = info.getSTransNo();
			lNAbstractID = info.getNAbstractID();
		}
		else
		{
Log.print("\n\n v016 中， info is null !\n\n");
		}
		 
	%>
<%Log.print("V016  --lID2:"+lID);%>


<form name="fm" action="../control/c016.jsp" method="post">
		<input type="hidden" name="strSuccessPageURL" value="../view/v015.jsp">
		<input type="hidden" name="strFailPageURL" value="../view/v016.jsp">	
		<input name="strAction" type="hidden" value="">	  
		<!--页面控制变量-->
        <input name="lID" type="hidden" value="<%=lID%>">
			<%Log.print("V016  --lID3:"+lID);%>
			<%
			    boolean isCheck=false;
				if(lNStatusID==3)
					isCheck=false;
				else if(lNStatusID==2)
					isCheck=true;
			%>
<safety:resources />
		<input type="Hidden" name="isCheck" value="<%=isCheck%>">
		<input name="tsDtModify" type="hidden" value="<%=tsDtModify%>">

<TABLE border=0 class=top height=290 width="760">
  <TBODY>
		<TR class="tableHeader">
			<TD class=FormTitle height=2 width="100%"><B>贴现收回</B></TD>
		</TR>
		<TR>
			<TD height=20 vAlign=top width="100%">
				<TABLE align=center border=1 borderColor=#999999 height="100%" width="97%">
        	<TBODY>
					<TR borderColor=#E8E8E8>
						<TD height=20 vAlign=middle width="17%" bordercolor="#E8E8E8" nowrap><U>贴现账户详细资料</U></TD>
						<TD height=20 vAlign=middle width="33%">&nbsp;</TD>
						<TD height=20 vAlign=middle width="15%">&nbsp;</TD>
						<TD height=20 vAlign=middle width="35%">&nbsp;</TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=31 width="17%" bordercolor="#E8E8E8" nowrap>客户编号：</TD>
						<TD height=31 width="33%">
						<input type="Hidden" name="lNClientID" value="<%=lNClientID%>">
						<INPUT type="Text" class=box disabled maxLength=6 name="strClientNo" size=6 value="<%=NameRef.getClientCodeByID(lNClientID)%>"></TD>
						<TD height=31 width="15%" nowrap>客户名称 ： </TD>
						<TD height=31 width="35%"><textarea type="Text" class=box disabled  name="strClientName" rpws=2 cols=30><%=NameRef.getClientNameByID(lNClientID)%></textarea>						
					</TR>
					<TR borderColor=#E8E8E8>
						<script language="JavaScript">
							showDisableAccountCtrl("DiscountAccountIDNo","<%=NameRef.getAccountNoByID(lNDiscountAccountID)%>","贴现账号","width='17%'","width='33%'");							
						</script> 
						<TD height=31 width="15%"><input type="Hidden" name="lNDiscountAccountID" value="<%=lNDiscountAccountID%>">&nbsp;</TD>
						<TD height=31 width="35%">&nbsp;</TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=20 width="17%" bordercolor="#E8E8E8" nowrap>合同号：</TD>
						<TD height=20 width="33%"><INPUT type="Text" class=box disabled name="strContractNo" value="<%=NameRef.getContractNoByID(lNDiscountContractID)%>"></TD>
						<TD height=20 width="15%"><input type="Hidden" name="lNDiscountContractID" value="<%=lNDiscountContractID%>">&nbsp;</TD>
						<TD height=20 width="35%">&nbsp;</TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=31 width="17%" bordercolor="#E8E8E8" nowrap>贴现凭证编号：</TD>
						<TD height=31 width="33%"><input type="hidden" name="lNDiscountNoteID" value="<%=lNDiscountNoteID%>"><INPUT type="Text" class=box disabled name="strtDiscountCredenceCode"   value="<%=NameRef.getDiscountCredenceNoByID(lNDiscountNoteID)%>"></TD>
									
						
						<TD height=20 width="15%">贴现票据贴现日:</TD>
						  <TD height=20 width="35%"><INPUT class=box name="tsStart" disabled value="<%=NameRef.getDiscountDateByDiscountBillID1(lNDiscountBillID)%>">
						  </TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=31 width="17%" bordercolor="#E8E8E8" nowrap>承兑汇票号：</TD>
						<TD height=31 width="33%"><INPUT type="Text" class=box disabled name="strDraftNo"    value="<%=NameRef.getDiscountBillNoByID(lNDiscountBillID)%>" ></TD>
										
						
						<TD height=20 width="15%">贴现票据到期日期:<INPUT type="hidden" class=box  name="lNDiscountBillID" value="<%=lNDiscountBillID%>">	</TD>
						<TD height=20 width="35%"><INPUT class=box name="tsEnd" disabled value="<%=NameRef.getEndDateByDiscountBillID(lNDiscountBillID)%>"></TD>
					</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD height=20 vAlign=top width="100%">
				<TABLE align=center border=1 borderColor=#999999 height="100%" width="97%">
        	<TBODY>
					<TR borderColor=#E8E8E8>
						<TD height=20 width="17%" nowrap><U>银行存款详细资料</U> </TD>
						<TD height=20 width="33%">&nbsp;</TD>
						<TD height=20 width="15%">&nbsp;</TD>
						<TD height=20 width="35%">&nbsp;</TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=20 width="17%" nowrap>银行存款开户行:&nbsp;</TD>
						<TD height=20 width="33%"><textarea  class=box disabled name="strBankName" rows=2 cols=30><%=NameRef.getBankNameByID(lNBankID)%></textarea></TD>
						<TD height=20 width="15%"><INPUT type="hidden" class=box name="lNBankID" value="<%=lNBankID%>">&nbsp;</TD>
						<TD height=20 width="35%">&nbsp;</TD>
					</TR>					
					</TBODY>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD height=20 vAlign=top width="100%">
				<TABLE align=center border=1 borderColor=#999999 height="2%" width="97%">
					<TBODY>
					<TR borderColor=#E8E8E8>
						<TD colSpan=4 height=10 vAlign=middle><U><input type="Hidden" name="lNIsReturned"
						value="<%=lNIsReturned%>"><input type="hidden" name="lNDepositAccountID" value="<%=lNDepositAccountID%>">
						<INPUT type="Checkbox" disabled  name="chkDetail"  value="<%=lNIsReturned%>" <%=(lNIsReturned==SETTConstant.BooleanValue.ISTRUE ? "checked" : "")%> >退票处理详细资料</U> </TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<script language="JavaScript">
							showDisableAccountCtrl("lNDepositAccountID","<%=NameRef.getAccountNoByID(lNDepositAccountID)%>","活期账号","width='17%'","width='33%'");
						</script> 
						
						<TD height=10 width="15%" nowrap>客户名称 ： </TD>
						<TD height=10 width="35%">
						<textarea class=box disabled name="strCurrentClient"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(lNDepositAccountID)%></textarea>
						</TD>
					</TR>
					<TR borderColor=#E8E8E8>
						<TD height=2 vAlign=middle width="17%" nowrap>退票金额： </TD>
						<TD height=2 vAlign=top width="33%" nowrap>￥ 
						<script language="JavaScript">
							createAmountCtrl("fm","dMReturnedAmount","<%=DataFormat.formatDisabledAmount(dMReturnedAmount)%>","dMOverDueInterest");		
						</script>
						<script language="JavaScript">
							document.fm.dMReturnedAmount.disabled=true;
						</script>
						<INPUT class=box type="hidden" name="dMReturnedAmount" size=17 value="<%=dMReturnedAmount%>">
						</TD>
						<TD height=2 width="15%" nowrap>罚息：</TD>
						<TD height=2 width="35%" nowrap>￥ 
						<script language="JavaScript">
							createAmountCtrl("fm","dMOverDueInterest","<%=DataFormat.formatDisabledAmount(dMOverDueInterest)%>","dMOverDueInterest");		
						</script>
						<script language="JavaScript">
							document.fm.dMOverDueInterest.disabled=true;
						</script>
						<INPUT type="Hidden" class=box name="dMOverDueInterest" value="<%=dMOverDueInterest%>" size=17>
						</TD>
					</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD height=81 vAlign=top width="100%">
				<TABLE align=center height=2 width="97%">
        	<TBODY>
					<TR>
						<TD align=left height=20 vAlign=middle width="10%" nowrap>金额：</TD>
						<TD align=left height=20 vAlign=middle width="25%" nowrap>￥ 
						<script language="JavaScript">
							createAmountCtrl("fm","dMAmount","<%=DataFormat.formatDisabledAmount(dMAmount)%>","dMAmount");		
						</script>
						<script language="JavaScript">
							document.fm.dMAmount.disabled=true;
						</script>
						<INPUT type="hidden" class=box name="dMAmount" size=17 value="<%=dMAmount%>">
						</TD>
						<TD align=left height=20 vAlign=middle width="10%" nowrap>起息日：</TD>
						<TD align=left height=20 vAlign=middle width="25%">
					<%
						Timestamp tmp=Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
						String strDtInterestStart=tmp.toString().substring(0,10);
						if(tsDtInterestStart!=null )
							strDtInterestStart=DataFormat.formatDate(tsDtInterestStart);
					%>
						<INPUT type="Text" class=box disabled name="strStart" value="<%=strDtInterestStart%>">
						<INPUT type="Hidden" class=box name="tsDtInterestStart" value="<%=tsDtInterestStart%>">
						</TD>
						<TD align=left height=20 vAlign=middle width="10%" nowrap>执行日：</TD>
						<TD align=left height=20 vAlign=middle width="20%">
					<%
						String strDtDtExecute=tmp.toString().substring(0,10);
						if(tsDtExecute!=null)
							strDtDtExecute=DataFormat.formatDate(tsDtExecute);
					%>
						<INPUT class=box disabled name="strExecute" value="<%=strDtDtExecute%>">
						<INPUT type="Hidden"  class=box name="tsDtExecute" value="<%=tsDtExecute%>">
						</TD>
					</TR>
					<TR>
						<TD align=left height=20 vAlign=middle width="10%" nowrap>交易号： </TD>
						<TD align=left height=20 vAlign=middle width="25%">
						<INPUT class=box disabled name="strTranstionNo" value="<%=(strSTransNo==null?"":strSTransNo)%>">
						<INPUT type="Hidden" class=box name="strSTransNo" value="<%=strSTransNo%>">
						</TD>
						<TD align=left height=20 vAlign=middle width="10%" nowrap>摘要：</TD>
						<TD align=left height=20 vAlign=middle width="25%">
						<INPUT class=box disabled maxLength=2000 name="strA" size=25 value="<%=(strSAbstract==null?"":strSAbstract)%>">
						<INPUT class=box type="Hidden" maxLength=2000 name="lNAbstractID" size=25 value="<%=lNAbstractID%>">
						</TD>
						<TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
						<TD align=left height=20 vAlign=middle width="20%">&nbsp;</TD>
					</TR>
					<TR>
						<TD align=left colSpan=6 height=20 vAlign=top>
						<DIV align=right>
				 <%
			            String strButtonName="复  核";	               

						if(lNStatusID==2)
						{
							strButtonName="复  核";							
						}
						else 
						{
							strButtonName="取消复核";							
						}
			     %>		  
				 
<%
	if("Query".equalsIgnoreCase(strAction))
	{
%>
			  	<INPUT class=button name="cmdTempSave" type=button value=" 打 印 " onClick="doPrint();">
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
<%
	}
	else
	{
%>	
						<INPUT class=button name="cmdComfirm" onclick="doCheackOrNot();" type=button value="<%=strButtonName%>"> 
					   <INPUT class=button name="cmdTempSave" type=button value=" 打 印 " onClick="doPrint();"> 
					    <INPUT class=button name="cmdBack" onClick="doBack();" type=button value=" 返 回 ">  
<%
	}
%>
            </DIV></TD>
					</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD height=20 vAlign=top width="100%">
				<HR>
				<TABLE align=center border=0 height=22 width="97%">
        	<TBODY>
					<TR vAlign=middle>
          	<TD height=25 width="8%" nowrap>复核备注:</TD>
						<TD height=25 vAlign=top width="19%" nowrap><INPUT class=box name="strSCheckAbstract" value="<%=(strSCheckAbstract==null?"":strSCheckAbstract)%>"  onfocus="nextfield='submitfunction';"></TD>
						<TD height=25 width="6%" nowrap>录入人:</TD>
						<TD height=25 width="8%" nowrap><%=NameRef.getUserNameByID(lNInputUserID)%></TD>
						<TD height=25 width="8%" nowrap>录入日期:</TD>
						<TD height=25 width="11%" nowrap><%=DataFormat.formatDate(tsDtInputDate)%></TD>
						<TD height=25 width="6%" nowrap>复核人:</TD>
						<TD height=25 width="7%" nowrap><%=NameRef.getUserNameByID(lNCheckUserID)%></TD>
					 <%
						String checkDate="";
						String userID=NameRef.getUserNameByID(lNCheckUserID);
						if( (userID!=null) && (!userID.equals("")) )
							checkDate=DataFormat.formatDate(tsDtExecute);
					  %>
						<TD height=25 width="8%" nowrap>复核日期:</TD>
						<TD height=25 width="9%" nowrap><%=checkDate%></TD>
						<TD height=25 width="5%" nowrap>状态:</TD>
						<TD height=25 width="5%" nowrap><%=SETTConstant.TransactionStatus.getName(lNStatusID)%></TD>
					</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
		</TBODY>
</TABLE>
</form>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<script language="JavaScript">
	firstFocus(document.fm.strSCheckAbstract);
	//setSubmitFunction("doCheackOrNot()");
	setFormName("fm"); 
</script>
<%
	}
%>
<SCRIPT language=JavaScript>  
/***************/
	//复核 or 取消复核
	function doCheackOrNot()
	{
		fm.isCheck.value="<%=isCheck%>";	

		if(fm.isCheck.value == 'true') //复核
		{
			if (confirm("是否复核?"))
			{				
				showSending();
				fm.submit();
			}
		}
		else  //取消复核
		{			
			if (!validateFields(fm))
			{
				return false;
			}
			if (confirm("是否取消复核?"))
			{
				//复核备注为必录项
				if(document.fm.strSCheckAbstract=="")
				{
					alert("复核备注必须录入!");
					return ;
				}		
				showSending();
				fm.submit();
			}
		}
    }

	function doBack()
	{
		if(fm.isCheck.value == 'true') //复核
			document.location.href="../view/v015.jsp";
		else if(fm.isCheck.value == 'false')			
		    document.location.href="<%=strContext%>/settlement/tran/discount/control/c012.jsp?lNStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lNTransactionTypeID=<%=SETTConstant.TransactionType.DISCOUNTRECEIVE%>&strSuccessPageURL=../view/v017.jsp&strFailPageURL=../view/v016.jsp&QueryTypeID=1";
	}

	function allFields()
	{
		this.aa = new Array("strSCheckAbstract","复核备注","string",1);
	}

	//打印
	function doPrint()
	{
<%	
	long lOBReturn=-1;
	String strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>		
		if (confirm("是否打印?")) 
		{
			isSubmited = true;			
			window.open( "../accountinfo/a911-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lNTransactionTypeID%>&strSuccessPageURL='../accountinfo/a912-v.jsp'&strFailPageURL='../accountinfo/a912-v.jsp'&lReturn=<%=lOBReturn%>");
		}
	}
//-->
</SCRIPT>

<%
	OBHtml.showOBHomeEnd(out);	
%>
<%
}
catch(Exception exp)
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>