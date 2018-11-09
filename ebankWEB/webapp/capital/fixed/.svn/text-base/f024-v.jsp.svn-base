<%--
/*
 * 程序名称：f024-v.jsp
 * 功能说明：定期开立修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
	<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[换开定期存单]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	String strTemp = (String)request.getAttribute("lID");
	long lID = 0;
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lID = Long.valueOf(strTemp).longValue();
	}
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;
	PayerOrPayeeInfo payeeInfo = null;
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();

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

		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		String strAction = request.getParameter("strAction");
		
		/* 从信息类中获取格式化的当前金额和可用金额 */
        sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
        sPayerUsableBalance = financeInfo.getFormatUsableBalance();
        if (sPayerUsableBalance==null || sPayerUsableBalance.trim().length()==0) 
		{	
			sPayerUsableBalance="0.00";
		}
		/*获取账户起存金额信息*/
		accountID = (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID();
		//accinfo = accdel.findAccountByID(accountID);
		accinfo = dao.findByID(accountID);
		dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frm">
     <table width="730" border="0" cellspacing="0" cellpadding="0" class =top>
        <tr class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          
			<td width="720"height="25" class=FormTitle colspan="4"><font size="3" >活期账户</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
		  <td width="5" height="25" class="MsoNormal"></td>
          
      <td width="130" height="25" class="MsoNormal">活期账户名称：</td>
          <td width="590" height="25"  class="MsoNormal">
            <input type="text" class="rebox" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? sessionMng.m_strClientName : (financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()) %>" disabled>
          </td>
          <td width="5" height="25"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1"  class="MsoNormal"></td>
        </tr>

		<!--a try for glass start-->
		<tr  class="MsoNormal">
		<td width="5" height="25"  class="MsoNormal"></td>
		
<%
		//String [] strNextCtrl = {"nFixedDepositTime"};
		//付款方账号放大镜
		//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",strNextCtrl);	
%>		
		
		<td>付款方账号:</td>
		<td><input type="text" class="amountbox" name="nPayerAccountID" size="16" value="<%= financeInfo.getPayerAcctNo() %>" disabled></td>
		</tr>
		<tr class="MsoNormal">
        <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<!--
        <tr class="MsoNormal">
          <td width="5" height="25"  class="MsoNormal"></td>

          <td width="130" height="25"  class="MsoNormal">当前余额：</td>
          <td width="590" height="25"  class="MsoNormal">
		<input type="text" class="amountbox" name="dPayerCurrBalance" size="16" value="<%= sPayerCurrentBalance %>" disabled>
		<font class="graytext" >
		可用余额：
		</font>
		<input type="text" class="amountbox" name="dPayerUsableBalance" size="16" value="<%= sPayerUsableBalance %>" disabled>
		<input type="hidden" name="nPayerAccountID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
		  </td>
          <td width="5" colspan="4"  class="MsoNormal"></td>
        </tr>
     -->    
        <tr  class="MsoNormal">
          <td colspan="4" height="1"  class="MsoNormal"></td>
        </tr>
  </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class =top>
        
          <!--td  class="MsoNormal"width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <tr>
      <td width="720" height="25" class=FormTitle colspan="2"><font size="3" >定期账户</font></td>
       </tr>
      </table>
      
      <table width="730" border="0" cellspacing="0" cellpadding="0" class =top>
              <tr  class="MsoNormal">
          <td width="5" height="25"  class="MsoNormal"></td>
          
      <td width="130" height="25"  class="MsoNormal"> <span class="MsoNormal">定期账号：</span></td>

      	<td width="590" height="25"  class="MsoNormal">
			<input type="text" name="sPayeeAccountNo" size="24" value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountNo():financeInfo.getPayeeAcctNo() %>" disabled  class=rebox>
	  		<input type="hidden" name="nPayeeAccountID"  value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID() %>" >	  	</td>
          <td width="5" height="25"  class="MsoNormal"></td>
        </tr>
      
        <tr  class="MsoNormal">
          <td colspan="4" height="1"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="5" height="25" rowspan="2"  class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
          <p><span  class="MsoNormal">定期存款期限：</span></p>          </td>
          <td width="590" height="12"  class="MsoNormal">
		  <%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime",SETTConstant.TransQueryType.FIXED,financeInfo.getFixedDepositTime(),false," disabled=\"disabled\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>          </td>
          <td width="5" height="25" rowspan="2" class="MsoNormal"></td>
        </tr>
        <!-- 
        <tr  class="MsoNormal">
          <td width="130" height="25" class="MsoNormal">定期存单号：</td>
          <td height="13"  class="MsoNormal"><input type="text" name="SDEPOSITBILLNO" size="24"  class="rebox" value="<%=financeInfo.getSDepositBillNo() == null?"":financeInfo.getSDepositBillNo() %>"/></td>
        </tr>
         -->
      </table>
	  <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class =top>
        <tr  class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td height="25" class=FormTitle colspan="5"><font size="3" >划款资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="4" height="25"></td>
          <td width="111" height="25"  class="MsoNormal">金额：</td>
          <td width="19" height="25" class="MsoNormal">
          <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>          </td>
          <td height="25" width="542"  class="MsoNormal">
          <!-- script  language="JavaScript">
				createAmountCtrl("frm","dAmount","<%= financeInfo.getFormatAmount() %>","tsExecute","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
			</script-->   
			<input name="Amount" style="text-align:right" value="<%= financeInfo.getFormatAmount() %>" class="rebox" disabled/>       </td>
          <td width="54" height="25"  class="MsoNormal"></td>
        </tr>
		<tr>
          <td colspan="5" height="1"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="4" height="25"  class="MsoNormal"></td>
          <td height="25"   class="MsoNormal" colspan="2">大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25"   class="MsoNormal">
			<input type="text" class="rebox" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(String.valueOf(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" disabled>		  </td>		  
          <td width="54" height="25"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="4" height="25" class="MsoNormal"></td>
          <td height="25"   class="MsoNormal" colspan="2">执行日：</td>
          <td height="25"  class="MsoNormal">
			<input type="text" name="tsExecute" class="box" value="<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(1,1)) :financeInfo.getFormatExecuteDate() %>" onfocus="nextfield ='sNote';" size="12" disabled> 
			<a href="#">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>			</a>		  </td>

          <td width="54" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="4" height="25"></td>
          <td height="25" colspan="2"  class="MsoNormal">汇款用途：</td>
          <td height="25" nowrap  class="MsoNormal">
		  	<textarea name="sNote" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" disabled onFocus="nextfield ='';"><%=(financeInfo.getNote()==null)?"":financeInfo.getNote()%></textarea>
			<font color="red">&nbsp;</font>          </td>
          <td width="54" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="4"></td>
          <td colspan="2"  class="MsoNormal">&nbsp;</td>
          <td nowrap  class="MsoNormal">&nbsp;</td>
          <td class="MsoNormal"></td>
        </tr>
        
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br />
	  <table width="730" height="25" border="0" cellpadding="0" cellspacing="0" class=top>
  <tr>
    <td height="25" colspan="2" class=FormTitle ><font size="3" >换开定期存单资料</font></td>
    </tr>
  <tr>
    <td width="134" height="25"><span class="MsoNormal">摘要：</span></td>
    <td width="596"><span class="MsoNormal">
      <textarea name="lAbstractID" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;"  onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield ='';"><%=financeInfo.getSDepositBillAbstract() == null?"":financeInfo.getSDepositBillAbstract() %></textarea>
    </span></td>
  </tr>
  <tr>
    <td height="25" colspan="2"><span class="MsoNormal">录入人：<%if(financeInfo.getNDepositBillInputuserId()==0 || financeInfo.getNDepositBillInputuserId()==-1){%>&nbsp;<%}else{%><%=financeInfo.getNDepositBillInputuserId()%><%} %>
    </span>&nbsp;&nbsp;&nbsp;
    <span class="MsoNormal">录入日期：<%if(financeInfo.getDtDepositBillInputdate()==null){%>&nbsp;<%}else{%><%=financeInfo.getDtDepositBillInputdate().toString().substring(0,10)%><%} %>&nbsp;&nbsp;&nbsp;
    复核人：<%if(financeInfo.getNDepositBillCheckuserId()==0 || financeInfo.getNDepositBillCheckuserId()==-1){%>&nbsp;<%}else{%><%=financeInfo.getNDepositBillCheckuserId() %><%} %> &nbsp;&nbsp;&nbsp;
    复核日期：<%if(financeInfo.getDtDepositBillCheckdate()==null){%>&nbsp;<%}else{%><%=financeInfo.getDtDepositBillCheckdate().toString().substring(0,10) %><%} %> &nbsp;&nbsp;&nbsp;
    状态：<%if(financeInfo.getNDepositBillStatusId()==0){%>&nbsp;<%}else{ %><%=SETTConstant.Actions.getName(financeInfo.getNDepositBillStatusId()) %><%} %>&nbsp;&nbsp;&nbsp;</span></td>
    </tr>
</table>

      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <div align="right"></div>
          
			<%
				if(strAction.equals("toCreate")){
			%>
			<DIV align="right">
				<!-- 
				<INPUT class="button" name="tempSave" type="button" value=" 暂 存 " onClick="doTempSave();">
				 -->
				<input class="button" name="save" type="button" value=" 保 存 " onClick="doSave();">
				<INPUT class="button" name="Submit333" type="button" value=" 返 回 " onClick="doBack1();">
				<input class="button" name="Submit32" type="button" value=" 链接查找 " onClick="doLinkSearch();">
			</DIV>
			<%
			}else if(strAction.equals("toModify")){
			%>
			<DIV align="right">
				<input class="button" name="save" type="button" value=" 保 存 " onClick="doSave();">
				<INPUT class="button" name="delete" type="button" value=" 删 除 " onClick="doDelete();">
				<!-- 
				<INPUT class="button" name="print" type="button" value=" 打 印 " onClick="doPrint();" disabled>
				 -->
				<input class="button" name="Submit32" type="button" value=" 返 回 " onClick="doBack2();">								
			</DIV>
			<%
			}else{
				System.out.println("没有此操作");
			}
			%>          </td>
        </tr>
      </table>
<%
		String strClickCount = (String) session.getAttribute("clickCount");
		Log.print("strClickCount=" + strClickCount);
		if (strClickCount == null) 
		{
			strClickCount = "0";
		}
%>
	    <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
		<input type="hidden" name="dMinSinglePayAmount" value="<%=dMinSinglePayAmount%>">
		<input name="strAction" type="hidden" value="">
		<input name="lID" type="hidden" value="<%=lID %>">
	  
</form>
<!--presentation end-->



<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frm.lAbstractID);
	//setSubmitFunction("doSave()");
	setFormName("frm");
	
	
	//控制页面的域的可见性
for(var i=0;i<frm.length;i++)
{
	if(frm[i].type!="button"&&frm[i].type!="hidden")
		frm[i].disabled=true;
		
	//显示定期存单号和摘要		
	//frm.SDEPOSITBILLNO.disabled=false;
	frm.lAbstractID.disabled=false;
}

		var isSubmited = false;
		
		//保存成功是否打印数据
		function saveSuccess()
		{
		    if (confirm("保存成功，是否打印?"))
		    {
		        window.open();
		    }
		}
		//打印换开定期存单数据
		function doPrint()
		{
			//confirm("执行了打印功能,有待开发!");
			window.open("/settlement/print/view/v023.jsp?lID=<%=financeInfo.getID()%>&strSuccessPageURL=/settlement/tran/current/view/v051.jsp&strFailPageURL=/settlement/tran/current/view/v051.jsp");
		}
		//删除功能
		function doDelete()
		{
		    if(isSubmited == true)
		    {
		        alert("请求已提交，请等候！");
		        return;
		    }
			if (confirm("是否删除?")) 
			{		
			    isSubmited = true;
				document.frm.action="f025-c.jsp?lID=<%=financeInfo.getID()%>";
			    document.frm.strAction.value="<%=SETTConstant.Actions.DELETE%>";
			    document.frm.submit();
			}
		}	
		//暂存换开定期存单数据	
		function doTempSave()
		{
		     if(isSubmited == true)
		    {
		        alert("请求已提交，请等候！");
		        return;
		    }
			
			if (confirm("是否暂存?")) 
			{
				isSubmited = true;
				document.frm.action="f025-c.jsp?lID=<%=financeInfo.getID()%>";
				document.frm.strAction.value="<%=SETTConstant.Actions.MODIFYTEMPSAVE%>";
				showSending();
				document.frm.submit();
			}
		}
		//保存换开定期存单数据
		function doSave()
		{		
		    if(isSubmited == true)
		    {
		        alert("请求已提交，请等候！");
		        return;
		    }
			//if(!validateFields(frm)) return;
			if(!validate()) return;
			if (confirm("是否保存?")) 
			{
				isSubmited = true;
				document.frm.action="f025-c.jsp?lID=<%=financeInfo.getID()%>";
				document.frm.strAction.value="<%=SETTConstant.Actions.MODIFYSAVE%>";				
				showSending();
				document.frm.submit();
			}		    
		}
		//连接查找
		function doLinkSearch()
		{
			document.frm.strAction.value='linkSearch';
			document.frm.action='f025-c.jsp';
			showSending();//显示正在执行
			frm.submit();
			
		}
		//返回
		function doBack1()
		{
			//回去了后可以执行回显示功能
			document.frm.action='f021-c.jsp';
			showSending();//显示正在执行
			frm.submit();
		}
		//返回
		function doBack2()
		{
			//回去了后可以执行回显示功能  返回到连接查找页面
			document.frm.strAction.value='linkSearch';
			document.frm.action='f025-c.jsp';
			showSending();//显示正在执行
			frm.submit();
		}
		//验证	
		function validate()
		{
			var bResult = true;
		
		if(bResult)
		{	
		
			//confirm("aa"+Trim(document.frm.SDEPOSITBILLNO.value)+"bb");
			//confirm("aa"+document.frm.lAbstractID.value+"bb");
			//看里面是否要设置成相应的字符串的要求
			//if(document.frm.SDEPOSITBILLNO.value=="")
			//{
			//	alert("定期存单号不能为空，请输入！");
			//	document.frm.SDEPOSITBILLNO.focus();
			//	bResult = false;
			//	return bResult;
			//}
			if(document.frm.lAbstractID.value=="")
			{
				alert("摘要不能为空，请输入！");
				document.frm.lAbstractID.focus();
				bResult = false;
			}
		}
		
		return bResult;
	}
</script>

<%
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);	
    }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>