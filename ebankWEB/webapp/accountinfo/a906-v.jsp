<%--
 页面名称 ：a906-v.jsp
 页面功能 : 复核/取消复核界面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>	
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO" %>
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

         //页面控制变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		
		strAction = (String)request.getAttribute("strAction");
		
		//通过贴现凭证编号查询得到信息
		long lID = -1; // 贴现发放业务ID
		long OfficeID = sessionMng.m_lOfficeID; //	办事处		
		long CurrencyID	= sessionMng.m_lCurrencyID;  // 币种			
		String TransNo	= ""; //交易号			
		long TransactionTypeID	= -1; //交易类型
		long DiscountAccountID = -1; //	贴现账户		
		long DiscountContractID = -1; //	贴现合同号		
		long DiscountNoteID = -1; //	贴现凭证		
		double DiscountBillAmount = 0.0; //	汇票金额		
		double DiscountAmount = 0.0 ; //	贴现金额		
		long DepositAccountID = -1;//	贴现转至活期账户ID	
		long PayTypeID = -1; //	贴现付款方式
		long BankID = -1; //	汇出行ID		
		String ExtAcctNo = "";//	外部账号				
		String ExtAcctName = ""; //	外部客户名称			
		String BankName = ""; //	外部汇入行名称			
		String Province	= ""; //汇入行省			
		String City = ""; //	汇入行市			
		long CashFlowID = -1; //	现金流向		
		double Interest = 0.0; //	利息		
		Timestamp InterestStartDate = null; //	起息日			
		Timestamp ExecuteDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); //	执行日			
		Timestamp ModifyDate = null;//	修改时间：年月日时分秒			
		Timestamp InputDate = null; //	录入日期			
		long InputUserID = -1; //	录入人		
		long CheckUserID = -1; //	复核人		
		String Abstract	= ""; // 摘要			
		String CheckAbstract = ""; //	取消复核摘要			
		long StatusID = -1; //	交易状态
		double mBankAcceptanceAmount=0.0;
		double mTradeAcceptanceAmount=0.0;

	    //增加
		long nClientID=-1;   //贴现客户编号
		String strClientName=""; //贴现客户名称
		
		TransGrantDiscountInfo info = (TransGrantDiscountInfo)request.getAttribute("matchResult");

		if(info != null )
		{
System.out.println("\n\n v008 中 info is not null !\n\n");
			lID = info.getID();
System.out.println("\n\n ID="+lID+"\n\n");
			OfficeID = info.getOfficeID();
			CurrencyID = info.getCurrencyID();
			TransNo = info.getTransNo();		
			TransactionTypeID = info.getTransactionTypeID();
			DiscountAccountID = info.getDiscountAccountID();		
			DiscountContractID = info.getDiscountContractID();
			DiscountNoteID = info.getDiscountNoteID();		
			DiscountBillAmount = info.getDiscountBillAmount();		
			DiscountAmount = info.getDiscountAmount();		
			DepositAccountID = info.getDepositAccountID();	
			PayTypeID = info.getPayTypeID();
			BankID = info.getBankID();		
			ExtAcctNo = info.getExtAcctNo();				
			ExtAcctName = info.getExtAcctName();			
			BankName = info.getBankName();
			Province = info.getProvince();			
			City = info.getCity();		
			CashFlowID = info.getCashFlowID();		
			Interest = info.getInterest();
			InterestStartDate = info.getInterestStartDate();	
			ExecuteDate = info.getExecuteDate();			
			ModifyDate = info.getModifyDate();	
System.out.println("\n\n v008.jsp 中,ModifyDate="+ModifyDate+"\n\n");
			InputDate = info.getInputDate();			
			InputUserID = info.getInputUserID();	
			CheckUserID = info.getCheckUserID();	
			Abstract = info.getAbstract();	
System.out.println("\n\n v008.jsp 中,Abstract="+Abstract+"\n\n");
			CheckAbstract = info.getCheckAbstract();			
			StatusID = info.getStatusID();
			mBankAcceptanceAmount = info.getMBankAcceptanceAmount();
			mTradeAcceptanceAmount = info.getMTradeAcceptanceAmount();

			nClientID=NameRef.getClientIDByAccountID(DiscountAccountID);
			strClientName=NameRef.getClientNameByID(nClientID);
		}    
		else
			System.out.println("\n\n v008 中 info is  null !\n\n");
	%>
	
<safety:resources />
<form action="../control/c008.jsp" name="fm" method="post">
		<input type="hidden" name="strSuccessPageURL" value="../view/v005.jsp">
		<input type="hidden" name="strFailPageURL" value="../view/v008.jsp">	
		<input name="strAction" type="hidden" value="">
		<input name="ModifyDate" type="hidden" value="<%=ModifyDate%>">	
		<input name="lID" type="hidden" value="<%=lID%>">	
		

		<!--页面控制变量-->
        <input name="lID" type="hidden" value="<%=lID%>">
			<%
			    boolean isCheck=false;
				if(StatusID==3)
					isCheck=false;
				else if(StatusID==2)
					isCheck=true;
			%>
		<input type="Hidden" name="isCheck" value="<%=isCheck%>">

<TABLE border=0 class=top height=290 width="99%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>贴现发放</B></TD></TR>
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center borderColor=#999999 height=40 width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%">贴现客户编号：</TD>
          <TD height=20 vAlign=top width="33%">
					<input type="Hidden" name="nClientID" value="">
					<INPUT class=box disabled maxLength=6 name="nClientID" size=6 value="<%=NameRef.getClientCodeByID(NameRef.getClientIDByAccountID(DiscountAccountID))%>"> </TD>
          <TD height=20 width="15%">贴现客户名称 ：</TD>
          <TD height=20 width="35%"><textarea class=box disabled  name="strClientName" rows=2 cols=30 ><%=NameRef.getClientNameByID(NameRef.getClientIDByAccountID(DiscountAccountID))%></textarea> </TD>
  </TR>	

<TR borderColor=#E8E8E8>
          
					<script language="JavaScript">
						showDisableAccountCtrl("DiscountAccountIDNo","<%=NameRef.getAccountNoByID(DiscountAccountID )%>","贴现账号","width='17%'","width='33%'");
					</script> 
          <TD height=20 vAlign=middle width="15%"><input type="Hidden" name="DiscountAccountID" value="20124">&nbsp;</TD>
          <TD height=20 vAlign=top width="35%"></TD>
				</TR>

        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%">合同号：</TD>
          <TD height=20 vAlign=top width="33%">
					<INPUT class=box disabled name="DiscountContractID" value="<%=NameRef.getContractNoByID(DiscountContractID)%>"> </TD>
          <TD height=20 vAlign=top width="15%">
					<input type="Hidden" name="DiscountContractID" value="1477">&nbsp;</TD>
          <TD height=20 vAlign=top width="35%">&nbsp;</TD>
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
          <TD height=20 vAlign=middle width="17%"><U>贴现贷款资料</U></TD>
          <TD height=20 vAlign=middle width="33%">&nbsp;</TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="35%">&nbsp;</TD>
				</TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%">贴现凭证编号：</TD>
          <TD height=20 vAlign=middle width="33%">
					<INPUT type="hidden" class=box  name="DiscountNoteID" value="">
					<INPUT type="Text" class=box disabled name="strDueBillID" value="<%=NameRef.getDiscountCredenceNoByID(DiscountNoteID)%>"> </TD>
                    
					
					<TD><INPUT class=button name=TranStati_button disabled type=button value="贴现票据计息明细表"></TD>
					
				</TR>
        <TR borderColor=#E8E8E8>
          <TD height=2 vAlign=middle width="17%">起始日期：</TD>
          <TD height=2 vAlign=middle width="33%">
					<INPUT type="Text" class=box disabled  name="InterestStartDate" size=18 value="<%=DataFormat.getDateString(InterestStartDate)%>"></TD>
          <TD height=2 width="15%">汇票金额：</TD>
          <TD height=2 width="35%">￥
					<script language="JavaScript">
						createAmountCtrl("fm","DiscountBillAmount","<%=DataFormat.formatDisabledAmount(DiscountBillAmount)%>","DiscountAmount");
					    fm.DiscountBillAmount.disabled=true;
	                </script>	
					<!--
					<input type="Text" disabled name="DiscountBillAmount" value="<%=DiscountBillAmount%>">
					-->
		  </TD>
		</TR>
				</TBODY>
			</TABLE>
		</TD>
	</TR>
  <TR>
    <TD height=221 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>贴现发放详细资料</U> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>贴现金额：</TD>
          <TD height=20 width="27%">￥ 
			        <script language="JavaScript">
						createAmountCtrl("fm","DiscountAmount","<%=DataFormat.formatDisabledAmount(DiscountAmount)%>","DiscountAmount");
					    fm.DiscountAmount.disabled=true;
	                </script>
					<!--
					<INPUT type="Text" class=box disabled name="DiscountAmount" size=17 value="<%=DiscountAmount%>"> 
					-->
		  </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
				</TR>


        <TR borderColor=#E8E8E8>
		<TD borderColor=#E8E8E8 height=20 width="3%">
	
			    <INPUT name="radType" type="Radio" disabled value="Current" <%=(DepositAccountID > 0 ? "checked" : "")%> <%=((DepositAccountID < 0 && PayTypeID < 0) ? "checked" : "")%> onfocus="nextfield='lCurrentAccountID';">
	          <!--     

				<INPUT CHECKED name="radType" type="Radio" value="Current" disabled onfocus="nextfield='DepositAccountIDCtrlCtrl3';">
			  -->
		 
				<input type="Hidden" name="lClientID" value="<%=NameRef.getClientIDByAccountID(DepositAccountID)%>"> 
		</TD>
	   
        <script language="JavaScript">						showDisableAccountCtrl("DepositAccountID","<%=NameRef.getAccountNoByID(DepositAccountID)%>","贴现转至活期账号","width='17%'","width='33%'");
						
		</script> 		

        <TD height=20 vAlign=middle width="15%">活期客户名称 ：</TD>	
		
		<%
			String strName=NameRef.getClientNameByAccountID(DepositAccountID);
		    if(strName==null)
				strName="";
		%>
		
		<TD height=20 width="38%"><textarea class=box name="sClientName" disabled	
				 rows=2 cols=30 ><%=strName%></textarea>	
	    </TD>
	  </TR> 

        <TR borderColor=#E8E8E8>
          <TD height=19 width="3%">
					<INPUT name="radType"  disabled type="Radio"  value="Bank" <%=(PayTypeID > 0 ? "checked" : "")%>  onfocus="nextfield='BankIDCtrl';">
		  </TD>

          <TD borderColor=#E8E8E8 height=20 width="20%">贴现付款方式：</TD>
		  <TD height=20 width="24%">
			  <%
				SETTConstant.PayType.showList(out,"PayTypeID",0,PayTypeID,false,true,"onfocus=\"nextfield='BankIDCtrl';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			  %>
			  <!--
			  <select class=box name="PayTypeID" onfocus="nextfield='BankIDCtrl';" value="<%=PayTypeID%>">				
				<option value="1">电汇</option>
				<option value="2">票汇</option>
				<option value="3">信汇</option>
				<option value="4">支票</option>
			  </select>
			  -->
			  <script language="JavaScript">
				fm.PayTypeID.disabled=true;
			  </script>
		  </TD>		  
          
          <TD height=19 width="15%">&nbsp;</TD>
          <TD height=19 width="37%">&nbsp;</TD>
		</TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">汇出行名称：</TD>
          <TD height=20 width="27%"><textarea class=box disabled  name="BankID" rows=2 cols=30 ><%=NameRef.getBankNameByID(BankID)%></textarea> </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		</TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入收款单位账号：</TD>
          <TD height=20 width="27%">
		  <%
						String strExtAcctNo="";
						if(ExtAcctNo==null || ExtAcctNo.equals(""))
							strExtAcctNo="";
						else
							strExtAcctNo=ExtAcctNo;
		  %>
					<INPUT class=box type="Text" disabled  name="ExtAcctNo" value="<%=strExtAcctNo%>" size=18> </TD>
          <TD height=20 width="15%">汇入收款单位户名:</TD>
          <TD height=20 width="37%">
					<textarea class=box disabled name="ExtAcctName" rows=2 cols=30 ><%=strExtAcctNo%></textarea>
		  </TD>
		</TR>

        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">汇入地(省)：</TD>
          <TD height=20 width="27%">
					<INPUT class=box disabled  name="Province" value="<%=(Province==null?"":Province)%>" size=24> 
		  </TD>
          <TD height=20 width="15%">汇入地(市)：</TD>
          <TD height=20 width="37%">
					<INPUT class=box disabled  name="City" value="<%=(City==null?"":City)%>" size=24> 
		  </TD>
		</TR>
	
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入行名称：</TD>
          <TD height=20 width="27%">
					<textarea class=box disabled  name="BankName" rows=2 cols=30><%=(BankName==null?"":BankName)%></textarea> </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		</TR>
       
				</TBODY>
			</TABLE>
		</TD>
	</TR>
  <TR>
    <TD height=43 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height="100%" width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%"><U>利息详细资料</U> </TD>
          <TD height=20 vAlign=middle width="33%">&nbsp;</TD>
          <TD height=20 vAlign=middle width="15%">&nbsp;</TD>
          <TD height=20 vAlign=middle width="35%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8 vAlign=middle>
          <TD height=20 width="17%">利 息：</TD>
          <TD height=20 width="33%">￥ 
					<INPUT type="Text" class=tar disabled name="Interest" size=17 value="<%=DataFormat.formatDisabledAmount(Interest)%>"> </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="35%">&nbsp;</TD>
				</TR>
				</TBODY>
			</TABLE>
		</TD>
	</TR>
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center height=2 width="97%">
        <TBODY>
        <TR>
          <TD align=left height=20 vAlign=middle width="10%">执行日：</TD>
          <TD align=left height=20 vAlign=middle width="25%">
					<INPUT  type="Text" class=box disabled name="ExecuteDate" value="<%=DataFormat.getDateString(ExecuteDate)%>"> </TD>
          <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="25%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="20%">&nbsp;</TD></TR>
        <TR>
          <TD align=left height=20 vAlign=middle width="10%">交易号： </TD>
          <TD align=left height=20 vAlign=middle width="25%">	    
			<INPUT class=box disabled name="TransNo" value="<%=(TransNo==null ? "" : TransNo)%>">
		  </TD>
          <TD align=left height=20 vAlign=middle width="10%">摘 要：</TD>
          <TD align=left height=20 vAlign=middle width="25%">		
			<INPUT class=box disabled maxLength=2000 name="Abstract"  size=25 value="<%=(Abstract==null?"":Abstract)%>">
		  </TD>
          <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="20%">&nbsp;</TD>
				</TR>
        <TR>
			 <%
			            String strButtonName="复  核";	               

						if(isCheck)
						{
							strButtonName="复  核";							
						}
						else 
						{
							strButtonName="取消复核";							
						}
			 %>		  
          <TD align=left colSpan=6 height=20 vAlign=top>	
<%
	if("Query".equalsIgnoreCase(strAction))
	{
%>
			<div align="right">
		        <INPUT class=button name="cmdTempSave" type=button value=" 打 印 " onClick="doPrint();"> 
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
			</div>
<%
	}
	else
	{
%>		 
            <DIV align=right>
						<INPUT class=button name="checkOrNot" onclick="doCheackOrNot();" type=button
			        value="<%=strButtonName%>"> 
				        <INPUT class=button name="cmdTempSave" type=button value=" 打 印 " onClick="doPrint();"> 
						<INPUT class=button name="cmdBack" onClick="doBack();" type=button value=" 返 回 ">  
            </DIV>
<%
	}
%>
			</TD>
				</TR>
				</TBODY>
			</TABLE>
		</TD>
	</TR>
  <TR>
    <TD height=2 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TBODY>
        <TR vAlign=middle>
          <TD height=2 width="8%">复核备注:</TD>
          <TD height=2 vAlign=top width="19%">
					<INPUT type="Text" class=box  name="CheckAbstract" value="<%=(CheckAbstract==null?"":CheckAbstract)%>" onfocus="nextfield='submitfunction';"> </TD>
          <TD height=2 vAlign=middle width="6%">录入人:</TD>
          <TD height=2 vAlign=middle width="8%"><%=NameRef.getUserNameByID(InputUserID)%></TD>
          <TD height=2 width="8%">录入日期:</TD>
          <TD height=2 width="11%"><%=DataFormat.formatDate(InputDate)%></TD>
          <TD height=2 width="6%">复核人:</TD>
          <TD height=2 width="7%"><%=NameRef.getUserNameByID(CheckUserID)%></TD>
          <TD height=2 width="8%">复核日期:</TD>
		      <%
				String checkDate="";
				String userID=NameRef.getUserNameByID(CheckUserID);
				if( (userID!=null) && (!userID.equals("")) )
					checkDate=DataFormat.formatDate(ExecuteDate);
			  %>
          <TD height=2 width="9%"><%=checkDate%></TD>
          <TD height=2 width="5%">状态:</TD>
          <TD height=2 width="5%"><%=SETTConstant.TransactionStatus.getName(StatusID)%></TD>
				</TR>
				</TBODY>
			</TABLE>
		</TD>
	</TR>
	</TBODY>
</TABLE>
</Form>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>

<script language="JavaScript">
	firstFocus(document.fm.CheckAbstract);
	//setSubmitFunction("doCheackOrNot()");
	setFormName("fm"); 
</script>
<%
	}
%>
<SCRIPT language=JavaScript>
	//复核 or 取消复核
	function doCheackOrNot()
	{
		fm.isCheck.value="<%=isCheck%>";	

		if(fm.isCheck.value == 'true') //复核
		{
			if (confirm("是否复核?"))
			{				
				showSending();
				document.fm.strAction.value="<%=String.valueOf(SETTConstant.Actions.CHECK)%>";
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
				if(document.fm.CheckAbstract=="")
				{
					alert("复核备注必须录入!");
					return ;
				}		
				showSending();
				document.fm.strAction.value="<%=String.valueOf(SETTConstant.Actions.CANCELCHECK)%>";
				fm.submit();
			}
		}
    }

	function doBack()
	{
		if(fm.isCheck.value == 'true') //复核
			document.location.href="../view/v005.jsp";
		else if(fm.isCheck.value == 'false')
			//document.location.href="../view/v007.jsp";
		    document.location.href="<%=strContext%>/settlement/tran/discount/control/c003.jsp?StatusID=<%=SETTConstant.TransactionStatus.CHECK%>&TransactionTypeID=<%=SETTConstant.TransactionType.DISCOUNTGRANT%>&strSuccessPageURL=../view/v007.jsp&strFailPageURL=../view/v005.jsp&QueryTypeID=1";
	}

	function allFields()
	{
		this.aa = new Array("CheckAbstract","复核备注","string",1);
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
			window.open( "../accountinfo/a907-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=TransactionTypeID%>&strSuccessPageURL='../accountinfo/a906-v.jsp'&strFailPageURL='../accountinfo/a906-v.jsp'&lReturn=<%=lOBReturn%>");
		}
	}

</SCRIPT>

<%	    
	OBHtml.showOBHomeEnd(out);	
%>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>