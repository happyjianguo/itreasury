<%--
/*
 * 程序名称：v005.jsp
 * 功能说明：单个权限转移、取消页面
 */
--%>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.system.util.SYSMagnifier"></jsp:useBean>
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
                
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    response.setHeader("Cache-Control","no-stored");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>
<%
    Log.print("---------进入 v005.jsp 审批权限转移（第三页）---------");
    try
    {
		String strTableTitle = "审批权限转移（第三页）";
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

        //String strOfficeName = sessionMng.m_strOfficeName;
        //String strUserName = sessionMng.m_strUserName;
        String strClientName = "";
        String strClientNo = "";
        long   lID = 0;
        String strTmp = "";	
		long lApprovalID = -1;	//审批设置标示
		long lLevel = -1;       //level
		long lUserID = -1;      //用户标识
		String strUserName = "";//用户名称
		Vector vResult = new Vector();
		//获得翻页参数
		long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;
  		long lPageNo = 1;
 		long lOrderParam = 1;
 		long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
		long lPageCount = 0;//页数
		String strStatusName = "";
		ApprovalChangeInfo info = new ApprovalChangeInfo();
        
        if (request.getAttribute("Error") != null)
        {
		
            strTmp = (String)request.getAttribute("Error");
        %>
            <script language="javascript">alert("处理出错:"+'<%=strTmp%>');</script>
      <%}
			// 获取lApprovalID
			strTmp = (String)request.getAttribute("lApprovalID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lApprovalID = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lApprovalID = -1;
				}
			}
			
			// 获取lLevel
			strTmp = (String)request.getAttribute("lLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lLevel = -1;
				}
			}

			// 获取lUserID
			strTmp = (String)request.getAttribute("lUserID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lUserID = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lUserID = -1;
				}
			}
	
			// 获取strUserName
			strTmp = (String)request.getAttribute("sUserName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strUserName = strTmp;
				}
				catch(Exception e)
				{
					strUserName= "";
				}
			}
	
			// 获取lUserID
			strTmp = (String)request.getAttribute("lPageLineCount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lPageLineCount = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lPageLineCount = -1;
				}
			}
	
			// 获取lUserID
			strTmp = (String)request.getAttribute("lPageNo");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lPageNo = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lPageNo = -1;
				}
			}
			
			// 获取lUserID
			strTmp = (String)request.getAttribute("lOrderParam");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lOrderParam = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lOrderParam = -1;
				}
			}
			
			// 获取lUserID
			strTmp = (String)request.getAttribute("lDesc");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lDesc = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lDesc = -1;
				}
			}
			
			// 获取vResult
        	if (request.getAttribute("info") != null)
        	{
            	info = (ApprovalChangeInfo)request.getAttribute("info");
        	}

%>
<script language="JavaScript" src="/websys/js/Control.js"></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>

<safety:resources />

<form name="frm" method=post>
<input type="hidden" name="lPageLineCount" value=<%=lPageLineCount%>>
<input type="hidden" name="lPageNo" value=<%=lPageNo%>>
<input type="hidden" name="lOrderParam" value=<%=lOrderParam%>>
<input type="hidden" name="lDesc" value=<%=lDesc%>>
<input type="hidden" name="lUserID" value=<%=lUserID%>>
<input type="hidden" name="lApprovalID" value=<%=lApprovalID%>>
<table width="66%" class="top">
  <tr> 
    <td class="FormTitle" height="29" colspan="2"> 
      <p><b><font size="3">审批权限转移（第三页）</font></b></p>
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
		  <td borderColor=#999999 height=32 vAlign=middle>审批流名称</td>	
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=info.getName()%>' size="10"></td> 
		  <td height="29" >当前状态</td>
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=Constant.ApprovalStatus.getName(info.getStatusID())%>' size="10"></td>
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
		  <td borderColor=#999999 height=32 vAlign=middle>审批人员</td>	
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=info.getUserName()%>' size="10"></td> 
		  <td height="29" >所属级别</td>
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=info.getLevel()%>' size="10"></td>
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
		  <td borderColor=#999999 height=32 vAlign=middle>权限转移至</td>	
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=info.getNewUserName()==null?"":info.getNewUserName()%>' size="10"></td> 
		  <td height="29" >转移日期</td>
		  <td height="29" align="left"><input type="text" class=box readonly value='<%=DataFormat.getDateString(info.getDate())%>' size="10"></td>   
        </tr>
      </table>
    </td>
  </tr>
  
  <tr> 
    <td height="102" colspan="2"> 
      <table width="500">
        <TR><td colspan = 7>
         <HR>
		 </td>
        </TR>	
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
		<%//查询所有虚拟用户
		       String strMagnifierNameUser = "";
		       String strFormNameUser = "frm";
		       String strMainPropertyUser = "";
		       String strPrefixUser = "";       
		       String[] strMainNamesUser = {"sNewUserName"};
		       String[] strMainFieldsUser = {"sNewUserName"};
		       
			   String strReturnInitValuesUser = "";
			   String strMatchValueUser = "";
			   String strTitleUser = "<font color=#FF0000>* </font>审批权限转移给";
			   
			   String[] strReturnNamesUser = {"lNewUserID"};
		       String[] strReturnFieldsUser = {"lNewUserID"};
		       String[] strReturnValuesUser = {"-1"};
		       String[] strDisplayNamesUser = {"用户名称"};
		       String[] strDisplayFieldsUser = {"sNewUserName"};
		       int nIndexUser = 0;       
		       String strSQLUser = "getNewUserSQL("+lApprovalID+","+lLevel+","+lUserID+")";
		       String strNextControlsUser = "move";
		       Magnifier.showZoomCtrl(out,strMagnifierNameUser,strFormNameUser,strPrefixUser,strMainNamesUser,strMainFieldsUser,strReturnNamesUser,strReturnFieldsUser,strReturnInitValuesUser,strReturnValuesUser,strDisplayNamesUser,strDisplayFieldsUser,nIndexUser,strMainPropertyUser,strSQLUser,strMatchValueUser,strNextControlsUser,strTitleUser,"","");
		%>
		 
        </tr>
      </table>
    </td>
  </tr>

  <tr> 
    <td height="40" colspan="2"> 
      <table width="500">
        <tr bordercolor="#FFFFFF">      
		  <td width="50%">&nbsp;</td>  
          <td height=30 align="right" >            
            <input type="button" name="move" value=" 转移 " class = button onclick="frmMove(frm)">
           </td>
		   <td height=30 align="right" >            
            <input type="button" name="cancel" value=" 取消 " class = button onclick="frmCancel(frm)">
           </td>
		   <td height=30 align="right" >            
            <input type="button" name="back" value=" 返回 " class = button onclick="frmBack(frm)">
           <input type=hidden name="sType"></td>     
        </tr>

    </table>
              
<script language="javascript">
    setFormName("frm");
    firstFocus(document.frm.sNewUserName);
    ////setSubmitFunction("frmSubmits(frm)");
</script>
<script language="javascript">
function frmMove(frm)
{
	if(frm.lNewUserID.value < 0)
	{
		alert('请输入审批权限转移给的用户！');
		frm.sNewUserName.focus();
		return false;
	}
	frm.sType.value="move";
	frm.action = "../control/c007.jsp";
	frm.submit();
}
function frmCancel(frm)
{
	frm.sType.value="cancel";
	frm.action = "../control/c007.jsp";
	frm.submit();
}
function frmBack(frm)
{
	frm.action = "../control/c004.jsp";
	frm.submit();
}
//得到在该审批设置下，该level下的虚拟用户
function getNewUserSQL(lApprovalID,lLevel,lUserID)
{
	 var strSQL ="  select id lNewUserID,sname sNewUserName from ob_user ";
        //strSQL +=" where nstatusid = 1 and nisVirtualUser = 1 ";
		//需求变更,可以转给真实用户
		strSQL +=" where nstatus = 1 and nisVirtualUser=1";
		strSQL +=" and id != "+lUserID;
		strSQL +=" and id in (";
		strSQL +=" select nuserid from ob_approvalitem ";
		strSQL +=" where napprovalID="+lApprovalID;
		strSQL +=" and nlevel = "+lLevel;
		strSQL +=" )";
    return strSQL;
}

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