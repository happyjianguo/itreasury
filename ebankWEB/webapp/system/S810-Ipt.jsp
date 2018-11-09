<%--
/**
 * 程序名称：S810-Ipt.jsp
 * 功能说明：票据查询输入、输出页面
 * 作　　者：刘琰
 * 完成日期：2003年8月22日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.sql.*,
				 java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.dao.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<% String strContext = request.getContextPath();%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%!
	/* 标题固定变量 */
	String strTitle = "[票据查询]";
%>

<%
	/* 初始化查询结果集 */
	List lstQuery = null;
	ListIterator listIterator = null;
	/* 获取用户输入信息的相应变量 */
	QueryVoucherInfo queryVoucherInfo = null; // 用户信息传递类
	//String strPayerBankNo = ""; // 银行账号
	long lType = -1; // 票据类型
	String strStartSubmit = ""; // 申请日期-从
	String strEndSubmit = ""; // 申请日期-到
	String strStartVoucherNo = "-1"; // 票据号-从
	String strEndVoucherNo = "-1"; // 票据号-到

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
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
		lstQuery = (List) request.getAttribute("cltQvf");
		if (lstQuery != null)
		{
			listIterator = lstQuery.listIterator();
		}
		queryVoucherInfo = (QueryVoucherInfo) request.getAttribute("queryVoucherInfo");
		if (queryVoucherInfo != null)
		{
			//strPayerBankNo = (String)request.getAttribute("sPayerBankNo");
			lType = queryVoucherInfo.getTypeID();
			strStartSubmit = queryVoucherInfo.getStartDate();
			strEndSubmit = queryVoucherInfo.getEndDate();
			strStartVoucherNo = String.valueOf(queryVoucherInfo.getStartVoucherNo() );
			strEndVoucherNo = String.valueOf(queryVoucherInfo.getEndVoucherNo() );
		}
		
		//日期默认为系统时间
		if ((strStartSubmit != null) && strStartSubmit.equals(""))
		{
			strStartSubmit = DataFormat.getDateString();
		}
		if ((strEndSubmit != null) && strEndSubmit.equals(""))
		{
			strEndSubmit = DataFormat.getDateString();
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

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<safety:resources />

<form method="post" name="frmpzcx">
	  <br>
	  <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">客户资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">客户名称：</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" class="rebox" name="sPayerName" size="30" value="<%= sessionMng.m_strClientName %>" >
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
		  <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">票据类型：</td>
		  <td width="430" height="25" bgcolor="#C8D7EC" class="box">
<% 			
			OBHtmlCom.showBillTypeControl(out,"lType",lType," onfocus=\"nextfield ='sStartSubmit';\""); //创建票据类型选择框
%>
		  </td>	 
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" class="graytext" bgcolor="#C8D7EC">申请日期：</td>
          <td width="20" height="25" class="graytext" bgcolor="#C8D7EC">
            <div align="right">由</div>
          </td>
          <td width="130" height="25" class="box">
          <fs_c:calendar 
	        	name="sStartSubmit"
	          	value="" 
	          	properties="nextfield ='sEndSubmit'" 
	          	size="12"/>
	          	 <script>
	          		$('#sStartSubmit').val('<%=((strStartSubmit != null) && !strStartSubmit.equals("")) ? strStartSubmit : "" %>');
	          	</script>
		  </td>
		  <td width="300" height="25" class="box">
			<span class="graytext">至</span>
			  <fs_c:calendar 
	         	    name="sEndSubmit"
		          	value="" 
		          	properties="nextfield ='sStartVoucherNo'" 
		          	size="12"/>
		          	 <script>
	          		$('#sEndSubmit').val('<%=((strEndSubmit != null) && !strEndSubmit.equals("")) ? strEndSubmit : "" %>');
	          	</script>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="6" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          
      <td width="110" height="25" class="graytext" bgcolor="#C8D7EC">票据号：</td>
          <td width="20" height="25" class="graytext" bgcolor="#C8D7EC">
            <div align="right">由</div>
          </td>
          <td width="130" height="25" class="box">
            <input type="text" name="sStartVoucherNo" value="<%= strStartVoucherNo.equals("-1") ? "" : strStartVoucherNo %>" size="30" onfocus="nextfield ='sEndVoucherNo';">
		  </td>
		  <td width="300" height="25" class="box">
            	<span class="graytext"> 至 </span>
            <input type="text" name="sEndVoucherNo" value="<%= strEndVoucherNo.equals("-1") ? "" : strEndVoucherNo %>" size="30" onfocus="nextfield ='';">
          </td>
          <td width="5"></td>
        </tr>
      </table>
      <br>
      <table width="730" align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
            <div align="right"></div>
          </td>
          <td width="60">
            <div align="right"><img src="\webob\graphics\button_chazhao.gif" width="46" height="18" border="0" onclick="javascript:queryme();"></div>
          </td>
        </tr>
      </table>
	  <br>

<% 
	if (lstQuery == null) { // 查询为空，显示一条空记录
%>
	<table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
      <tr bgcolor="#C8D7EC">
        <td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
        
      <td colspan="2"height="22" bgcolor="#0C3869"><b><font size="3" color="#FFFFFF" class="whitetext">票据库存资料</font></b></td>
        <td width="5" height="22" bgcolor="#0C3869"></td>
      </tr>
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table width="570" border="0" cellspacing="0" cellpadding="0">
      <tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table width="730" border="0" align="center" height="51" class="ItemList">
      <tr>
        <td class="whitetext" width="120" height="14" bgcolor="#456795">
          
        <div align="center">票据号</div>
        </td>
        <td class="whitetext" width="80" height="14" bgcolor="#456795">
          <div align="center">状态</div>
        </td>
        <td class="whitetext" width="100" height="14" bgcolor="#456795">
          <div align="center">生成日期</div>
        </td>
      </tr>
      <tr bgcolor="#C8D7EC">
        <td class="graytext" width="120" height="14">
          <div align="center"></div>
        </td>
        <td class="graytext" width="80" height="14">
          <div align="center"></div>
        </td>
        <td class="graytext" width="100" height="14">
          <div align="center"></div>
        </td>
      </tr>
    </table>
<% 
	}
%>

<%
	/**
	 * 查询结果不为空
	 */
	if (lstQuery != null)
	{
%>

      <table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          
      <td colspan="2"height="22" bgcolor="#0C3869"><b><font size="3" color="#FFFFFF" class="whitetext">票据库存资料</font></b></td>
          <td width="5" height="22" bgcolor="#0C3869"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" border="0" align="center" height="51" class="ItemList">
        <tr>
          <td class="whitetext" width="120" height="14" bgcolor="#456795">
            
        <div align="center">票据号</div>
          </td>
          <td class="whitetext" width="80" height="14" bgcolor="#456795">
            <div align="center">状态</div>
          </td>
          <td class="whitetext" width="100" height="14" bgcolor="#456795">
            <div align="center">生成日期</div>
          </td>
        </tr>

<%
			VoucherInfo voucherInfo = null; // 初始化信息类

			/* 按格式显示所有记录 */
	   		while(listIterator.hasNext())
	   		{
				voucherInfo = (VoucherInfo)listIterator.next(); // 获取下一条记录信息
%>
        <tr bgcolor="#C8D7EC">
          <td class="graytext" width="120" height="14">
            <div align="center"><%=voucherInfo.getVoucherNo() /*票据号*/%></div>
          </td>
          <td class="graytext" width="80" height="14">
            <div align="center"><%= voucherInfo.getStatus() /*状态*/%></div>
          </td>
          <td class="graytext" width="100" height="14">
            <div align="center"><%= voucherInfo.getDate() /*日期*/%></div>
          </td>
        </tr>
<% 
			} 
%>
      </table>
<% 
	} 
%>
	 <br>
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * 数据校验及FORM提交
	 * javascript
	 */

    /* 查询处理函数 */
    function queryme()
    {
        frmpzcx.action = "<%=strContext%>/system/S811-Ctr.jsp";
		if (validate() == true)
        {
            frmpzcx.submit();
        }
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */

		/* 票据类型非空校验 */
		

		/*  申请日期校验 */
		var starSubmit = frmpzcx.sStartSubmit.value;
		var endSubmit = frmpzcx.sEndSubmit.value;

		if((starSubmit != "") && ( chkdate(starSubmit) == 0 ))
		{
			alert("请输入正确的申请开始日期");
			frmpzcx.sStartSubmit.focus();
			return false;
		}
		if((endSubmit != "") && ( chkdate(endSubmit) == 0 ))
		{
			alert("请输入正确的申请结束日期");
			frmpzcx.sEndSubmit.focus();
			return false;
		}
		if ((starSubmit != "") && (endSubmit != ""))
		{	if (!CompareDate(frmpzcx.sStartSubmit, frmpzcx.sEndSubmit, "起始日期不能大于结束日期"))
			{
				return false;
			}
		}
		/* 票据号校验 */
		if (isNaN(frmpzcx.sStartVoucherNo.value))
	    {
			alert("票据号只能是数值");
			frmpzcx.sStartVoucherNo.focus();
			return (false);
	    }
		if (isNaN(frmpzcx.sEndVoucherNo.value))
	    {
			alert("票据号只能是数值");
			frmpzcx.sEndVoucherNo.focus();
			return (false);
	    }
		if ((frmpzcx.sStartVoucherNo.value != "") && (frmpzcx.sEndVoucherNo.value != ""))
		{
			if (parseFloat(frmpzcx.sStartVoucherNo.value) > parseFloat(frmpzcx.sEndVoucherNo.value))
			{
				alert("起始票据号不能大于结束票据号");
				return false;
			}
		}

    	return true;
    }

</script>


<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frmpzcx.lType);
	setSubmitFunction("queryme(frmpzcx)");
	setFormName("frmpzcx");
</script>

<%
	/* 显示文件尾 */
	OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>