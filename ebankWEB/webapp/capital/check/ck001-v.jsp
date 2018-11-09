<%--
/*
 * 程序名称：ck002-1.jsp
 * 功能说明：业务复核交易类型选择页面
 * 作　　者：刘琰
 * 完成日期：2004年02月06日
 */
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>


<%!
	/* 标题固定变量 */
	String strTitle = null;
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
	FinanceInfo financeInfo = null;
	
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";

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
		
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle,lShowMenu);
%>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frm">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22" colspan="10">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>

     	<tr><td><br></td></tr>                
        <tr class=MsoNormal align="left">
		  <td width="5" height="25"></td>
          <td width="110" height="25" class="MsoNormal" align="left">&nbsp;&nbsp;交易类型：</td>
          <td width="90" height="25"  class="MsoNormal">
<%
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		long lIsCtrl = dao.getIsControlChild(sessionMng.m_strClientCode);


		if (lIsCtrl == 1)
		{
			//OBHtmlCom.showQueryTypeListControl1(out,"SelectType",-1," onfocus=\"nextfield ='';\" ",false);
			OBHtmlCom.showQueryCheckTypeListControl(out,"SelectType",-1," onfocus=\"nextfield ='';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,false);
		}
		else
		{		
			//OBHtmlCom.showQueryTypeListControl(out,"SelectType",-1," onfocus=\"nextfield ='';\" ",false);
			OBHtmlCom.showQueryCheckTypeListControl(out,"SelectType",-1," onfocus=\"nextfield ='';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,false);
			
		}

%> 		
			</td>
			<td width="100" height="25" align="right"><input type="button" name="Submitv00204" value=" 确 定 " class="button1" onClick="Javascript:continueme();"></td>
          <td width="400" height="25" align="right">&nbsp;</td>
        </tr>       
	 	<tr><td></td></tr>
     </table>
     <!-- <table width=80% align="center">
     <tr><td height="10"></td><td></td></tr>
        <tr class=MsoNormal align="right">
          <td  class=MsoNormal  colspan=3>
            <div align="right"></div>
          </td>
          
          <td width="62" class=MsoNormal>
            <div align="right">
			<img src="\webob\graphics\button_jixu.gif" width="46" height="18" onclick="Javascript:continueme();">
			
			</div>
          </td>
        </tr>
     </table> -->
		<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
</form>

<script language="Javascript">
	var queryType = frm.SelectType.value;
	function continueme()
	{		
		frm.action="ck006-c.jsp";
		showSending();
		frm.submit();		
	}

</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frm.SelectType);
	//setSubmitFunction("continueme(frm)");
	setFormName("frm");
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