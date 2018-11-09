<%--
/*
 * 程序名称：v003.jsp
 * 功能说明：审批权限转移第一页
 */
--%>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifiersys" scope="request" class="com.iss.itreasury.system.util.SYSMagnifier"/>
<!--Header start-->
<%
/* 设置页面属性、session、引入的程序包 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
                java.sql.*,
                java.rmi.RemoteException,
                java.rmi.*,java.net.URLEncoder,
                com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.approval.dataentity.*,
                com.iss.itreasury.ebank.util.*"%>
                
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
    response.setHeader("Cache-Control","no-stored");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>
<%

    Log.print("---------进入 v003.jsp 审批权限转移第一页---------");
    try
    {
		//请求检测
		/** 权限检查 **/
		String strTableTitle = "审批权限转移（第一页）";
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTableTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		/* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);

        String strOfficeName = sessionMng.m_strOfficeName;
        String strUserName = sessionMng.m_strUserName;
        String strClientName = "";
        String strClientNo = "";
        long   lID = 0;
        String strTmp = "";
		long lApprovalID = -1;	//审批设置标示
		//翻页参数
	  	long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;
	  	long lPageNo = 1;
	 	long lOrderParam = 1;
	 	long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
        
        if (request.getAttribute("Error") != null)
        {
		
            strTmp = (String)request.getAttribute("Error");
%>
            <script language="javascript">alert("处理出错:"+'<%=strTmp%>');</script>
<%
		}
%>
<jsp:include page="/ShowMessage.jsp"/>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form name="frm" method=post>
<!--input type="hidden" name="lType" value=0-->
<input type="hidden" name="lPageLineCount" value=<%=lPageLineCount%> >
<input type="hidden" name="lPageNo" value=<%=lPageNo%> >
<input type="hidden" name="lOrderParam" value=<%=lOrderParam%> >
<input type="hidden" name="lDesc" value=<%=lDesc%> >
<table width="66%" class="top">
  <tr> 
    <td class="FormTitle" height="29" colspan="2"> 
      <p><b><font size="3">审批权限转移（第一页）</font></b></p>
    </td>
  </tr>
  <tr> 
    <td height="102" colspan="2"> 
      <table width="500">
        <tr bordercolor="#FFFFFF">
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
          <td height="29">&nbsp;</td>
        </tr>        
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
<%
       String strMagnifierNameApprovalSetting = "";
       String strFormNameApprovalSetting = "frm";
       String strMainPropertyApprovalSetting = "";
       String strPrefixApprovalSetting = "";       
       String[] strMainNamesApprovalSetting = {"txtApprovalName"};
       String[] strMainFieldsApprovalSetting = {"sName"};
	   	   
	   String strReturnInitValuesApprovalSetting = "";
	   String strMatchValueApprovalSetting = "0";
	   String strTitleApprovalSetting = "<font color=#FF0000>* </font>审批流名称";
	   
       String[] strReturnNamesApprovalSetting = {"lApprovalID"};
       String[] strReturnFieldsApprovalSetting = {"ID"};
       String[] strReturnValuesApprovalSetting = {"-1"};
       String[] strDisplayNamesApprovalSetting = {"序号","审批流名称"};
       String[] strDisplayFieldsApprovalSetting = {"rownum","sName"};
       int nIndexApprovalSetting = 1;       
       String strSQLApprovalSetting = "getApprovalSettingSQL("+sessionMng.m_lOfficeID+",-1,"+ sessionMng.m_lClientID +")";
       String strNextControlsApprovalSetting = "sUserName";
       Magnifier.showZoomCtrl(
		   out,
		   strMagnifierNameApprovalSetting,
		   strFormNameApprovalSetting,
			strPrefixApprovalSetting,
			strMainNamesApprovalSetting,
			strMainFieldsApprovalSetting,
			strReturnNamesApprovalSetting,
			strReturnFieldsApprovalSetting,
			strReturnInitValuesApprovalSetting,
			strReturnValuesApprovalSetting,
			strDisplayNamesApprovalSetting,
			strDisplayFieldsApprovalSetting,
			nIndexApprovalSetting,
			strMainPropertyApprovalSetting,
			strSQLApprovalSetting,
			strMatchValueApprovalSetting,
		    strNextControlsApprovalSetting,
		    strTitleApprovalSetting,"","");
%>   
          <td height="29" width="1">&nbsp;</td>
        </tr>        
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
		 
		 <%//放大镜选出所有不是虚拟用户的用户！
		        String strMagnifierNameUser = "";
		       String strFormNameUser = "frm";
		       String strMainPropertyUser = "";
		       String strPrefixUser = "";       
		       String[] strMainNamesUser = {"sUserName"};
		       String[] strMainFieldsUser = {"sUserName"};
		       
			   String strReturnInitValuesUser = "";
			   String strMatchValueUser = "";
			   String strTitleUser = "<font color=#FF0000>* </font>审批人员";
			   
			   String[] strReturnNamesUser = {"lUserID"};
		       String[] strReturnFieldsUser = {"lUserID"};
		       String[] strReturnValuesUser = {"-1"};
		       String[] strDisplayNamesUser = {"用户名称"};
		       String[] strDisplayFieldsUser = {"sUserName"};
		       int nIndexUser = 0;   
			   String strSQLUser = "getNewUserSQL("+ sessionMng.m_lClientID +")";
		       String strNextControlsUser = "NextSubmit";
		       Magnifiersys.showZoomCtrl(out,strMagnifierNameUser,strFormNameUser,strPrefixUser,strMainNamesUser,strMainFieldsUser,strReturnNamesUser,strReturnFieldsUser,strReturnInitValuesUser,strReturnValuesUser,strDisplayNamesUser,strDisplayFieldsUser,nIndexUser,strMainPropertyUser,strSQLUser,strMatchValueUser,strNextControlsUser,strTitleUser,"","");
		   %>
        </tr>
        <tr bordercolor="#FFFFFF">
          <td height=30 align="right" colspan=5>
               <input type="button" name="NextSubmit" value=" 下一页 " class = button 
                      onClick="frmSubmits(frm)" onKeydown="if(event.keyCode==13) frmSubmits(frm);">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
              <input type=hidden name="type">
</form>

<script language="javascript">
	function getNewUserSQL(lClientID)
{
    var strSQL ="  select id lUserID,sname sUserName from ob_user ";
        //strSQL +=" where nstatusid = 1 and nisVirtualUser = 1 ";
		//需求变更,可以转给真实用户
		strSQL +=" where nstatus = 1 and (nisVirtualUser is null or nisVirtualUser=0)";
		strSQL +=" and nClientID = " + lClientID+"";
    return strSQL;
}
function frmSubmits(frm)
{
	if(frm.lApprovalID.value < 0)
	{
		alert('请输入审批流名称！');
		frm.txtApprovalName.focus();
		return false;
	}
	if(frm.lUserID.value < 0)
	{
		alert('请输入审批人员！');
		frm.sUserName.focus();
		return false;
	}
    else
    {
		//frm.lType.value = 0;        //保存标识
        frm.action="../control/c004.jsp";
        frm.submit();
        return true;
    }
}


function alertError(err)
{
    alert("发生错误："+err);
}
</script>
<script language="javascript">
    setFormName("frm");
    firstFocus(document.frm.txtApprovalName);
    ////setSubmitFunction("frmSubmits(frm)");
</script>
<%
        OBHtml.showOBHomeEnd(out);
    }
    catch(Exception e)
    {
		//SYSHTML.showExceptionMessage(out,sessionMng,e,request,response,"审批流设置（第一页）",Constant.RecordStatus.VALID);
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>