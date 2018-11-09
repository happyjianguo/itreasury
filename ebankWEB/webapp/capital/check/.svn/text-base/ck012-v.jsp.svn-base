<%--
/*
 * 程序名称：ck002-v.jsp
 * 功能说明：资金划拨复核匹配输入页面
 * 作　　者：刘琰
 * 完成日期：2004年02月06日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	long lTransType = -1;
	
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
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}

        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">
     
  <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
    <tr class="tableHeader"> 
      <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
      <td width="720"height="25"  colspan="5" class=FormTitle><font size="3" color="#FFFFFF" class="whitetext">资金申领指令复核</font></td>
      <!--td width="5" valign="top" align="right" height="25"> 
        <div align="right"></div>
      </td0-->
    </tr>
	<tr class="MsoNormal"> 
      <td colspan="5" height="1"></td>
    </tr>
	<tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>       
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal">
		  <input  type="hidden" name="SelectType" value="<%=OBConstant.QueryInstrType.APPLYCAPITAL%>">
		  </td>
          <td width="220" height="25" class="MsoNormal">&nbsp;&nbsp;金额：&nbsp;&nbsp;<%= sessionMng.m_strCurrencySymbol %></td>
         
          <td height="25" width="500" class="MsoNormal">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%= financeInfo.getFormatAmount() %>","","",1);
			</script>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr>
			<td height = "5" colspan="5"></td>
		</tr>
        <tr class="MsoNormal">
          <td class="MsoNormal" colspan="4">
            <div align="right" ></div>
          </td>          
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
			<input type="button" name="Submitv00204" value=" 匹  配 " class="button" onClick="Javascript:matchme();">
			</div>
          </td>
        </tr>
   </table>
   
        <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
</form>

<script language="Javascript">
	
	/* 汇款方式 */
	//var iRemitType = frmzjhb.nRemitType.value;
	
	
	function getNextField()
	{
              //>>>>add by shiny 20030403
      	      var iRemitType = frmzjhb.nRemitType.value;
			  if (iRemitType == -1)
			  {//没有选择
			  	  alert("请选择汇款方式");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frmzjhb.sPayeeAccountInternalCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//本转
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//其他
                  frmzjhb.dAmount.focus();
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* 修改提交处理函数 */
    function matchme()
    {
        
		frmzjhb.action = "ck017-c.jsp";
		if (validate() == true)
        {
			/* 确认提交 */
			if (!confirm("是否匹配？"))
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
		if (frmzjhb.lInstructionID.value == -1)
		{	
			if (confirm("确定取消吗？"))
			{
				frmzjhb.action="";
				frmzjhb.submit();
			}
		}
		else
		{
			if (confirm("确定取消吗？"))
			{
        		history.go(-1);
			}
		}
    }

    /* 校验函数 */
    function validate()
    {
       /* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.dAmount, 1, "交易金额"))
		{
			return false;
		}
		
    	return true;
    }
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	firstFocus(frmzjhb.dAmount);
	//setSubmitFunction("matchme(frmzjhb)");
	setFormName("frmzjhb");
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