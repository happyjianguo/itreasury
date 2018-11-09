<!--for modify-->
<%--
/*
 * 程序名称：
 * 功能说明：银行汇款－－信息修改
 * 作　　者：baihuili
 * 日期：2006年09月15日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
					java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                   com.iss.itreasury.budget.util.*,
				   com.iss.itreasury.budget.setting.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[银行汇款]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	strMenu="hidden";
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
%>
<%


	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
     OBHtml.validateRequest(out,request,response);

		OBBankPayInfo info=(OBBankPayInfo)request.getAttribute("info");
		System.out.println("##################"+info);
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">
     <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr bgcolor="#456795" class="tableHeader">
          <!--td bgcolor="#456795" width="4" valign="top" align="left" height="25">
		  <img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td height="25" bgcolor="#456795" colspan="4" class=FormTitle><font size="3" color="#FFFFFF" >付款方资料</font></td>
          <!--td width="129" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="MsoNormal">
		  <td width="4" height="25" class="MsoNormal"></td>
          <td width="140" height="25" class="MsoNormal">付款方名称：</td>
          <td width="457" height="25" class="MsoNormal">
            <input type="text" class="box" name="name" size="30" value="<%=info.getName()%>" readonly >
            <INPUT type="hidden" name="npayeracctid" value="<%=info.getNpayeracctid()%>">
            <input type="hidden" name="id" value="<%=info.getId()%>">
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
       <tr  class="MsoNormal">
		<td width="4" height="25" class="MsoNormal"></td>
	<%OBMagnifier.createPayerBankAccountNoCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID,"npayeracctid","dPayerCurrBalance","dPayerUsableBalance","name","frmzjhb","","sPayerAccountNoZoom","付款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
		%>
		<script>
          document.frmzjhb.sPayerAccountNoZoomCtrl.value="<%=NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %>";
          </script>
		<td width="140" class="MsoNormal" ></td>
		 </tr>
	
        <tr  class="MsoNormal">
          <td width="4" height="25" class="MsoNormal"></td>

          <td width="160" height="25" class="MsoNormal">当前余额：</td>
          <td width="457" height="25" class="MsoNormal">
		<input type="text" class="amountbox" name="dPayerCurrBalance" size="16" value="<%=DataFormat.formatDisabledAmount(OBOperation.GetCurBalance(info.getNpayeracctid(), sessionMng.m_lCurrencyID),2)%>" readonly>
		&nbsp;&nbsp;&nbsp;&nbsp;付款方银行名称：
		<input type="text"   name="bankname" size="20" value="<%= NameRef.getBankNameByAcctID(info.getNpayeracctid()) %>" readonly> 
		<input type="hidden" class="amountbox" name="dPayerUsableBalance" size="16" value="" readonly>
		<input type="hidden" name="nPayerAccountID" size="16" value="" >
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="FormTitle">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="720"height="25" colspan=3><font size="3" >收款方资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
	   <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            <p><span class="MsoNormal">汇款方式：</span></p>
          </td>
          <td width="590" height="25" class="MsoNormal">
           &nbsp;&nbsp;
			银行汇款
			<INPUT type="hidden" name="ntranstype" value="<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>">
		  </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      </table>
	   <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>

		
		 <tr id="payeeAcctNoZoom" class="MsoNormal">
          <td height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <input type="hidden" name="npayeeacctid" value="<%=info.getNpayeeacctid()%>">
          <input type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
          
		<%
		//收款方账号放大镜（汇）
		OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"npayeeacctid","spayeeacctname","spayeeprov","spayeecity","spayeebankname","frmzjhb",info.getSpayeeacctno(),"sPayeeAcctNoZoom","收款方账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\"");	
	
%>
		
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeNameZoomAcct" class="MsoNormal">
          <td height="25" width="2" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
		  <td height="25" width="130" class="MsoNormal">收款方名称：</td>
		  <td height="25" colspan="3" class="MsoNormal">
		  	<textarea name="spayeeacctname" value="<%=info.getSpayeeacctname()%>" cols="65"  onfocus="nextfield ='spayeeprov';" rows="2" readonly="readonly"></textarea>
		  </td>
		  <script>
          document.frmzjhb.spayeeacctname.value="<%=info.getSpayeeacctname()%>";
          </script>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>
		
		<tr id="payeePlace" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">汇入地：</td>
          <td height="25"  class="MsoNormal">
            <input type="text" name="spayeeprov" value="<%=info.getSpayeeprov()%>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15">
            省
            <input type="text" name="spayeecity" value="<%=info.getSpayeecity()%>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15">
        市（县）</td>
          <td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal">
          <td height="1" colspan="6" class="MsoNormal"></td>
        </tr>

		<tr id="payeeBankNameRead" class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">汇入行名称：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="rebox" name="spayeebankname" value="<%=info.getSpayeebankname()%>" size="30" readonly>
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
                <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">银行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="rebox" name="bankconnectnumber"  size="30" value="<%=info.getBankconnectnumber()==null?"":info.getBankconnectnumber()%>">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td  height="25" width="1" class="MsoNormal"></td>
          <td height="25" width="5" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal">机构号：</td>
          <td height="25" colspan="2" class="MsoNormal">
			<input type="text" class="rebox" name="departmentnumber"  size="30" value="<%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%>">
          </td>
		<td height="25" width="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="720 "height="25" bgcolor="#456795" colspan="5"class="FormTitle"><font size="3" color="#FFFFFF" >划款资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
       <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="110" height="25"class="MsoNormal">金额：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td height="25" width="590" class="MsoNormal">
            <script  language="JavaScript">
            var a = <%=info.getMamount()%>+"";
				createAmountCtrl("frmzjhb","mamount","0.0","snote","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
				document.frmzjhb.mamount.value=formatAmount1(a);
			</script>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="rebox" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(DataFormat.formatEAmount(info.getMamount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25"class="MsoNormal" colspan="2">提交日期：</td>
          <td height="25" class="MsoNormal">
          <%=info.getDtexecute().toString().substring(0,10)%>
			<input type="hidden" name="dtexecute" value="<%=info.getDtexecute().toString().substring(0,10)%>" onfocus="nextfield ='snote';" size="12">
			<!--<a href="javascript:show_calendar('frmzjhb.dtexecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">-->
			<!--	<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>-->
			<!--</a>-->
		  </td>
		  
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">汇款用途：</td>
          <td height="25" class="MsoNormal" nowrap>
		 
            <textarea name="snote"  cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onFocus="nextfield ='';"><%=info.getSnote()%></textarea>
			
			
        <font color="red">&nbsp;</font>
          </td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button name=add type=button value=" 提  交 " onClick="Javascript:addme();"  > 
			<input type="hidden" name="act">
			</div>
          </td>
		  <td width="9" height="17"></td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button name=add type=button value=" 关  闭 " onClick="Javascript:window.close();" > 
			</div>
          </td>
        </tr>
      </table>
	  
      
</form>
<script language="Javascript">
	
	

	
	function getNextField()
	{
            
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
             
    }

    /* 修改提交处理函数 */
    function addme()
    {
        frmzjhb.act.value="querymodify";	
		frmzjhb.action = "../control/c001.jsp";
		frmzjhb.spayeeacctno.value=frmzjhb.sPayeeAcctNoZoomCtrl.value;
		if (validate() == true)
        {
        		
			/* 确认提交 */
			if (!confirm("是否提交？"))
			{
				return false;
			}
			showSending();
            frmzjhb.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (confirm("确定重置吗？"))
			{
				frmzjhb.reset();
			}
		
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		

		/* 付款方资料非空校验 */
		if (frmzjhb.name.value == "")
		{
			alert("付款方名称不能为空");
			frmzjhb.name.focus();
			return false;
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		if (frmzjhb.name.value < 0)
		{
			alert("付款方账号请从放大镜里取出！");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
					
			if (frmzjhb.spayeeacctname.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.spayeeacctname.focus();
				return false;
			}
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
			//if (!IsAccountN0Int(frmzjhb.sPayeeAcctNoZoomCtrl.value))
			//{
			//	alert("收款方帐户编号只能是数字");
			//	frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			//	return false;
			//}
			if(frmzjhb.spayeecity.value != "")
			{
				var str = frmzjhb.spayeecity.value;
				str = str.substring(str.length-1,str.length);
				if(str=="市")
				{
					alert("请去掉汇入城市后的 '市' ");
					frmzjhb.spayeecity.focus();
					return false;
				}
			}
			
		
	
		
      
		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.mamount, 1, "交易金额"))
		{
			return false;
		}

		/* 执行日校验 
		if (!checkInterestExecuteDate(frmzjhb.dtexecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		}*/
		/* 汇款用途 */
		if (!InputValid(frmzjhb.snote, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}
		/* 业务校验 */
		/* 可用余额－交易金额 */
		
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.mamount.value)) ;
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		/* add 2006-11-23 不校验可用余额
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入划拨金额");
			frmzjhb.mamount.focus();
			return false;
		}
		*/
		
		//校验划拨金额 如果划拨金额大于当前金额，重新划拨
		var currBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerCurrBalance.value));
		var transferMoney = parseFloat(reverseFormatAmount(frmzjhb.mamount.value));
		if(transferMoney > currBalance){
		   alert("划拨金额大于当前金额，请重新划拨金额");
		   frmzjhb.mamount.focus();
			return false;
		}
		
		
		if(frmzjhb.sPayerAccountNoZoomCtrl.value == frmzjhb.sPayeeAcctNoZoomCtrl.value)
		{
			alert("付款方和收款方不能相同");
			frmzjhb.sPayeeAcctNoZoomCtrl.focus();
			return false;
		}

		
			/*
			 * 如果收款方名称需要校验取消该注释
			 *
			if (!InputValid(frmzjhb.spayeeacctname, 0, "textarea", 1, 0, 100,"收款方名称"))
			{
				return false;
			}
			*/
			if (!InputValid(frmzjhb.spayeeprov, 1, "string", 0, 0, 0,"汇款地 省"))
			{
				return false;
			}
			if (!InputValid(frmzjhb.spayeecity, 1, "string",0, 0, 0,"汇入地 市(县)"))
			{
				return false;
			}
		

    	return true;
		
    }
	
function IsAccountN0Int( d_int)
{
		var checkOK = "0123456789";
		var checkStr = d_int;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		if (ch != ",")
			allNum += ch;
		}
		return (allValid)
 }	
 

 
</script>
<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
	
	//setSubmitFunction("addme(frmzjhb)");
	setFormName("frmzjhb");
</script>

<%
	}
	catch(IException ie){
		 OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
	}
	OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>