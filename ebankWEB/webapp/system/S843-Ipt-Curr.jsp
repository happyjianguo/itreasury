<%--
/*
 * 程序名称：S843-Ipt-Curr.jsp
 * 功能说明：签认金额设置输入、输出页面（新奥--活期）
 * 作　　者：周翔
 * 完成日期：2011年4月15日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "活期签认金额设置";
%>

<%
	/* 初始化用户信息传递类 */
	SignAmountInfo signAmountInfo = null;
	/* 获取相应信息 */
	String sAmount1 = "0.00"; // 金额一
    String sAmount2 = "0.00"; // 金额二
    String sAmount3 = "0.00"; // 金额三
    long lSignUserID1 = -1; // 指定签认人一
    long lSignUserID2 = -1; // 指定签认人二
    long lSignUserID3 = -1; // 指定签认人三

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
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
		signAmountInfo = (SignAmountInfo) request.getAttribute("signAmountInfo");
		if (signAmountInfo != null)
		{
			sAmount1 = signAmountInfo.getFormatAmount1(); // 金额一
			Log.print("sAmount1=========" + sAmount1);
    		sAmount2 = signAmountInfo.getFormatAmount2(); // 金额二
		    sAmount3 = signAmountInfo.getFormatAmount3(); // 金额三
		    lSignUserID1 = signAmountInfo.getSignUserID1(); // 指定签认人一
		    lSignUserID2 = signAmountInfo.getSignUserID2(); // 指定签认人二
		    lSignUserID3 = signAmountInfo.getSignUserID3(); // 指定签认人三
		}

        /**
         * presentation start
         */
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
    } 
	catch (IException ie) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<safety:resources />

<form method="post" name="frmqrjesz">
	
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">活期签认金额设置</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
 
</table>
<br/>
	 <table width=80% border="0" cellspacing="0" cellpadding="0" align="">
      
      
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 签认金额一</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext" ><span class="graytext"><font color="red">*&nbsp;</font>金额：(大于等于)</span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172">  <span class="graytext">
           	<script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount1","<%= sAmount1 %>","nSignUserID1","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>元</td>
            
          <td height="58" class="graytext"><font color="red">*&nbsp;</font>指定签认人： <span class="graytext">
<% 			
			OBHtmlCom.showSignUserControl(out, "nSignUserID1",lSignUserID1,"onfocus=\"nextfield ='sAmount2';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span></td>
        </tr>
      </table>
	<br>
	 <table width=80% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

       <!--     <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 签认金额二</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr> -->
                  <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 签认金额二</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext"><span class="graytext">
            <font color="red">*&nbsp;</font>金额：(大于等于) </span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172">  <span class="graytext">
            <script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount2","<%= sAmount2 %>","nSignUserID2","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>元</td>
          <td height="58" class="graytext"><font color="red">*&nbsp;</font>指定签认人： <span class="graytext">
<% 		
			OBHtmlCom.showSignUserControl(out, "nSignUserID2",lSignUserID2,"onfocus=\"nextfield ='sAmount3';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span></td>
        </tr>
      </table>
	 <table width=80% border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>&nbsp;</td>
      </tr>
      
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

      <!--   <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 签认金额三</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>  --> 
          
                          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 签认金额三</td>
	<td width="17"><a class=lab_title3></td>
</tr>
          
        </table></td>
      </tr>
    </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0" class = "normal" align="">
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="2" height="58"></td>
          <td width="120" height="58" class="graytext"><span class="graytext"><font color="red">*&nbsp;</font>金额：(大于等于)</span></td>
          <td width="20" height="58">
            <div align="right" class="graytext"><span class="graytext"><%= sessionMng.m_strCurrencySymbol %></span></div>
          </td>
          <td height="58" width="172">  <span class="graytext">
            <script  language="JavaScript">
				createAmountCtrl("frmqrjesz","sAmount3","<%= sAmount3 %>","nSignUserID3","",<%= sessionMng.m_lCurrencyID%>);
			</script>
            </span>元</td>
          <td height="58" class="graytext"><font color="red">*&nbsp;</font>指定签认人： <span class="graytext">
<% 
			OBHtmlCom.showSignUserControl(out, "nSignUserID3",lSignUserID3,"onfocus=\"nextfield ='';\"", sessionMng.m_lClientID, sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID); 
%>
            </span> </td>
        </tr>
      </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
                  <table width=80% border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input type="button" name="Submitv00204" value=" 保 存 " class="button1" onClick="javascript:submitme();">
			</div>
          </td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input type="button" name="Submitv00204" value=" 重 置 " class="button1" onClick="javascript:cancelme();">
			</div>
          </td>
        </tr>
      </table>
	 <br>
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * 数据校验及FORM提交
	 * javascript
	 */

    /* 查询处理函数 */
    function submitme()
    {
		frmqrjesz.action = "<%=strContext%>/system/S845-Ctr-Curr.jsp";
		if (validate() == true)
		{
			frmqrjesz.submit();
		}
	
    }

	/* 取消处理函数 */
    function cancelme()
    {
    	frmqrjesz.reset();
		//document.all.frmqrjesz.sAmount3.value="";
		//document.all.frmqrjesz.sAmount2.value="";
		//document.all.frmqrjesz.sAmount1.value="";
		//document.all.frmqrjesz.nSignUserID3.value="";
		//document.all.frmqrjesz.nSignUserID2.value="";
		//document.all.frmqrjesz.nSignUserID1.value="";
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */

		/* 金额校验 */
		var amount1 = reverseFormatAmount(frmqrjesz.sAmount1.value);
		var amount2 = reverseFormatAmount(frmqrjesz.sAmount2.value);
		var amount3 = reverseFormatAmount(frmqrjesz.sAmount3.value);
		/* 金额一：
		 * 是数值
		 * 如果为空或值为0则提交，否则进行如下校验
		 * 大于等于0而小于1000000000000
		 * 指定签认人不能为空
		 */
		if (frmqrjesz.sAmount1.value =="-") 
		{
			alert("金额一只能是数值" );
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.sAmount1.value ==",") 
		{
			alert("金额一只能是数值" );
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.sAmount2.value =="-") 
		{
			alert("金额二只能是数值" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.sAmount2.value ==",") 
		{
			alert("金额二只能是数值" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.sAmount3.value =="-") 
		{
			alert("金额三只能是数值" );
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (frmqrjesz.sAmount3.value ==",") 
		{
			alert("金额三只能是数值" );
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (!isFloat(amount1))
	    {
			alert("金额一只能是数值" );
			frmqrjesz.sAmount1.focus();
			return false;
	    }
	/*	if (((amount1 == "") || (parseFloat(amount1) == 0.00))&&(frmqrjesz.nSignUserID1.value == -1))
		{
			return true;
		}	
	*/	
		if (((amount1 == "") || (parseFloat(amount1) == 0.00))&&(frmqrjesz.nSignUserID1.value == -1))
		{
			if(((amount2 == "") || (parseFloat(amount2) == 0.00))&&(frmqrjesz.nSignUserID2.value == -1))
			{
				if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
				{
					return true;
				}
				alert ("签认金额二内容为空");
				return false;
			}
			alert ("签认金额一内容为空");
			return false;
		}
		
		if (((amount1 == "")||parseFloat(amount1) < 0.01 )&& !(frmqrjesz.nSignUserID1.value == -1))
		{
			alert("金额一不能小于0.01");
			frmqrjesz.sAmount1.focus();
			return false;
		}
		
		if (amount1.length >= 16)
		{
			alert("金额一只能小于1,000,000,000,000.00");
			frmqrjesz.sAmount1.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID1.value == -1)
		{
			alert("指定签认人一不能为空");
			frmqrjesz.nSignUserID1.focus();
			return false;
		}
		/* 金额二：
		 * 是数值
		 * 如果为空或值为0则提交，否则进行如下校验
		 * 大于等于0而小于1000000000000
		 * 金额二大于金额一
		 * 指定签认人不能为空
		 */
		 
		if (!isFloat(amount2))
	    {
			alert("金额二只能是数值" );
			frmqrjesz.sAmount2.focus();
			return (false);
	    }
	    
		if (((amount2 == "")||parseFloat(amount1) < 0.01 )&& !(frmqrjesz.nSignUserID2.value == -1))
		{
			alert("金额二不能小于0.01");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		
		if (((amount2 == "") || (parseFloat(amount2) == 0.00))&&(frmqrjesz.nSignUserID2.value == -1))
		{
			if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
				{
					return true;
				}
				alert ("签认金额二内容为空");
				return false;
		}
	
	/*	if (parseFloat(amount2) < 0.00)
		{
			alert("金额二不能小于0.00");
			frmqrjesz.sAmount2.focus();
			return false;
		}
	*/
		if (amount2.length >= 16)
		{
			alert("金额二只能小于1,000,000,000,000.00");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (parseFloat(amount1) >= parseFloat(amount2))
		{
			alert ("金额二必须大于金额一");
			frmqrjesz.sAmount2.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID2.value == -1)
		{
			alert("指定签认人二不能为空");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID2.value)
		{
			alert("金额一和金额二的指定签认人不能相同");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
		
		/* 金额三：
		 * 是数值
		 * 如果为空或值为0则提交，否则进行如下校验
		 * 大于等于0而小于1000000000000
		 * 金额三大于金额二
		 * 指定签认人不能为空
		 */
		if (!isFloat(amount3))
	    {
			alert("金额三只能是数值" );
			frmqrjesz.sAmount3.focus();
			return (false);
	    }
	/*	if ((amount3 == "") || (parseFloat(amount3) == 0.00))
		{
			return true;
		}
	*/
		if (((amount3 == "") || (parseFloat(amount3) == 0.00))&&(frmqrjesz.nSignUserID3.value == -1))
		{
			return true;
		}
		if (((amount3 == "")||parseFloat(amount3) < 0.01 )&& !(frmqrjesz.nSignUserID3.value == -1))
		{
			alert("金额三不能小于0.01");
			frmqrjesz.sAmount1.focus();
			return false;
		}
	
		if (amount3.length >= 16)
		{
			alert("金额三只能小于1,000,000,000,000.00");
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (parseFloat(amount2) >= parseFloat(amount3))
		{
			alert ("金额三必须大于金额二");
			frmqrjesz.sAmount3.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID3.value == -1)
		{
			alert("指定签认人三不能为空");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
	/*	if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID2.value)
		{
			alert("金额一和金额二的指定签认人不能相同");
			frmqrjesz.nSignUserID2.focus();
			return false;
		}
	*/
		if (frmqrjesz.nSignUserID1.value == frmqrjesz.nSignUserID3.value)
		{
			alert("金额一和金额三的指定签认人不能相同");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
		if (frmqrjesz.nSignUserID2.value == frmqrjesz.nSignUserID3.value)
		{
			alert("金额二和金额三的指定签认人不能相同");
			frmqrjesz.nSignUserID3.focus();
			return false;
		}
    	return true;
    }

</script>


<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frmqrjesz.sAmount1);
	setSubmitFunction("submitme(frmqrjesz)");
	setFormName("frmqrjesz");
</script>

<%
	/* 显示文件尾 */
	OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>