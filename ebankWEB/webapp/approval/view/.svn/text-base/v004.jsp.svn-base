<%--
/*
 * 程序名称：v004.jsp
 * 功能说明：查找更改审批显示页面
 */
--%>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"></jsp:useBean>
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
    Log.print("---------进入 v004.jsp 审批权限转移（第二页）---------");
    try
    {
		String strTableTitle = "审批权限转移（第二页）";
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
        	if (request.getAttribute("vResult") != null)
        	{
            	vResult = (Vector)request.getAttribute("vResult");
        	}
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<FORM name="frm" method=post>
<TABLE class=top width="87%" border=0>
  <TBODY>
  <TR>
    <TD class=FormTitle height=29><B>审批权限转移（第二页）</B></TD></TR>
  <TR>
    <TD height=50><!-- form1 -->
    
	<input type="hidden" name="lApprovalID" value="<%=lApprovalID%>">	
	<input type="hidden" name="lUserID" value=<%=lUserID%>>
      <TABLE  border=0>
        <TBODY>
		<td> <div >
          <input type="checkbox" name="checkNewUser">
        </div></td>
		<%//查询所有虚拟用户
		       String strMagnifierNameUser = "";
		       String strFormNameUser = "frm";
		       String strMainPropertyUser = "";
		       String strPrefixUser = "";       
		       String[] strMainNamesUser = {"sNewUserName"};
		       String[] strMainFieldsUser = {"sNewUserName"};
		       
			   String strReturnInitValuesUser = "";
			   String strMatchValueUser = "";
			   String strTitleUser = "批量转移至";
			   
			   String[] strReturnNamesUser = {"lNewUserID"};
		       String[] strReturnFieldsUser = {"lNewUserID"};
		       String[] strReturnValuesUser = {"-1"};
		       String[] strDisplayNamesUser = {"用户名称"};
		       String[] strDisplayFieldsUser = {"sNewUserName"};
		       int nIndexUser = 0;       
		       String strSQLUser = "getNewUserSQL("+lUserID+","+ sessionMng.m_lClientID +")";
		       String strNextControlsUser = "NextSubmit";
		       Magnifier.showZoomCtrl(out,strMagnifierNameUser,strFormNameUser,strPrefixUser,strMainNamesUser,strMainFieldsUser,strReturnNamesUser,strReturnFieldsUser,strReturnInitValuesUser,strReturnValuesUser,strDisplayNamesUser,strDisplayFieldsUser,nIndexUser,strMainPropertyUser,strSQLUser,strMatchValueUser,strNextControlsUser,strTitleUser,"","");
		%>
      </TABLE>
	</TD>
  <TR>
    <TD align=center height=75>
      <HR>

      <TABLE class=ItemList borderColor=#999999 width=823 border=0>
        <TBODY>
        <TR align=center borderColor=#999999 bgColor=#cccccc>
          <TD class=ItemTitle width=150>序号</TD>
          <TD class=ItemTitle width=173><A href="javascript:go('1');">审批流名称</A></TD>          
          <TD class=ItemTitle width=100><A href="javascript:go('4');">所属级别</A></TD>
          <TD class=ItemTitle width=100><A href="javascript:go('5');">审批人员</A></TD>
          <TD class=ItemTitle width=96><A href="javascript:go('6');">权限转移至</A></TD>
		  <TD class=ItemTitle width=96><A href="javascript:go('7');">转移日期</A></TD>
		</TR>
	<%
	int k=((Vector)vResult).size();   //记录数
	for(int i=0;i<k;i++)
	{
		info = (ApprovalChangeInfo)((Vector)vResult).elementAt(i);
	   	lPageCount = info.getRecordCount() / Constant.PageControl.CODE_PAGELINECOUNT;
	   	if ((info.getRecordCount() % Constant.PageControl.CODE_PAGELINECOUNT) != 0)
	   	{
            lPageCount ++;
        }
	%>
		<TR align=center borderColor=#999999>
          <TD class=ItemBody width=150><A HREF="../control/c005.jsp?lApprovalID=<%=info.getApprovalID()%>&lUserID=<%=lUserID%>&lLevel=<%=info.getLevel()%>&lPageLingCount=<%=lPageLineCount%>&lPageNo=<%=lPageNo%>&lOrderParam=<%=lOrderParam%>&lDesc=<%=lDesc%>"><%=info.getSerialID()%></A></TD>
          <TD class=ItemBody width=173><%=info.getName()%></TD>          
          <TD class=ItemBody width=100><%=info.getLevel()%></TD>
          <TD class=ItemBody width=100><%=info.getUserName()%></TD>
          <TD class=ItemBody width=96><%=info.getNewUserName()==null?"":info.getNewUserName()%></TD>
		  <TD class=ItemBody width=96><%=DataFormat.getDateString(info.getDate())%></TD>
		</TR>
	<%}%>
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=8 height=2>
            <TABLE class=SearchBar height=50 cellSpacing=3 cellPadding=0 
            width="188%" border=0>
              <TBODY>
              <TR>
                <TD width=859 height=2>
                  <DIV align=right>
                  <P><B>
<INPUT class=SearchBar_Page maxLength=3  name="cz" size=3 onkeydown="key_down()" value="<%=lPageNo%>"> of <%=lPageCount%> 
<INPUT class=SearchBar_Btn name="aaaaa" type=button onclick="goto()" value=go> 

<%if(lPageNo > 1) {%>
<input type="button" name="Submit4222" value="|&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value='1';gohere();">
<%}%>
<%if(lPageNo > 1) {%>
<input type="button" name="Submit5222" value="&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo-1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit6222" value="&gt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo+1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit7222" value="&gt;|" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageCount%>;gohere();">
<%}%></B></P></DIV></TD></TR></TBODY></TABLE>
		</TD></TR></TBODY></TABLE>
		<BR>
		<DIV align="right">
		<input class="button" type="button" name="button1" value="批量转移" onClick="frmMove(frm)">
        <input class="button" type="button" name="button2" value="批量取消" onClick="frmCancel(frm)">
		<input class="button" type="button" name="button3" value="返回" onClick=backto()>
		</DIV>
		<BR></TD></TR></TBODY></TABLE>
		<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
		<input type="hidden" name="lPageLineCount" value=<%=lPageLineCount%>>
		<input type="hidden" name="lPageNo" value=<%=lPageNo%>>
		<input type="hidden" name="lOrderParam" value=<%=lOrderParam%>>
		<input type="hidden" name="lDesc" value=<%=lDesc%>>
		<input type="hidden" name="sType" >

</form>

<script language="JavaScript">

function backto()
{
	eval("self.location='../view/v003.jsp?control=view'");
}

function key_down()
{
	if (window.event.keyCode == 13)
	{
		var lMax = parseInt(frm.lPageCount.value);
		if (NumValid(frm.cz,lMax,"页数"))
		{
			frm.lPageNo.value=frm.cz.value;
			confirmSave(frm);
		}
		else
		{
			return(false);
		}
	}
}

function go(para)
{
	
	if (frm.lOrderParam.value==para)
	{
	   if (frm.lDesc.value=="<%=Constant.PageControl.CODE_ASCORDESC_DESC%>")
	       frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_ASC%>";
	   else
	   	   frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_DESC%>"; 
	}
	frm.lOrderParam.value=para;
	//frm.lPageNo.value="1";
	confirmSave(frm); 
}

function goto()
{
	var lMax=parseInt(frm.lPageCount.value);
	if (NumValid(frm.cz,lMax,"页数"))
	{
		frm.lPageNo.value=frm.cz.value;
		confirmSave(frm);
	}
	else
	{
		return(false);
	}
}

function gohere()
{
	var lMax=parseInt(frm.lPageCount.value);
	if (NumValid(frm.cz,lMax,"页数"))
	{
		//frm.lPageNo.value=frm.cz.value;
		confirmSave(frm);
	}
	else
	{
		return(false);
	}
	//confirmSave(frm); 
}

/*function search()
{
	frm.lPageNo.value="";
	confirmSave(frm); 
}*/

function NumValid(d_input,d_max,d_str)
{
	if (d_input.value.length == 0)
	{
		alert("请输入" +d_str);
		d_input.focus();
		return (false);
	}
	else if ( d_input.value.length > 0 ) 
	{
		if ( !isInt(d_input.value))
		{
			alert( d_str+ "只能是数字");
			d_input.focus();
			return (false);
		}

		if  (!(1<=parseInt(d_input.value) && parseInt(d_input.value) <= d_max))
		{
			alert(d_str+ "的值必须在 1 到 " +d_max+ " 之间。");
			d_input.focus();
			return (false);
		}
	}
	return true;
}

//编号比较
function CodeCompare(d_input1,d_input2,d_str)
{
	if (d_input1.value.length>0 && d_input2.value.length>0)
	{
		if (d_input1.value>d_input2.value)
		{
			alert(d_str+"不能由大至小。");
			d_input1.focus();
			return (false);
		}
	}
	return true;
}

function  confirmSave(frm)
{	
	frm.action="../control/c004.jsp";
	//showSending();
	frm.submit();
		
		 
}

function getNewUserSQL(lUserID,lClientID)
{
    var strSQL ="  select id lNewUserID,sname sNewUserName from ob_user ";
        //strSQL +=" where nstatusid = 1 and nisVirtualUser = 1 ";
		//需求变更,可以转给真实用户
		strSQL +=" where nstatus = 1 and nisVirtualUser = 1";
		strSQL +=" and id != " + lUserID;
		strSQL +=" and nClientID = " + lClientID;
    return strSQL;
}

function frmMove(frm)
{
	if(frm.checkNewUser.status!=true)
	{
		alert('请输入审批权限转移给的用户，并在前面的复选框打勾！');
		frm.checkNewUser.focus();
		return false;
	}
	if(frm.lNewUserID.value < 0)
	{
		alert('请输入审批权限转移给的用户！');
		frm.sNewUserName.focus();
		return false;
	}
	frm.sType.value="move";
	frm.action = "../control/c006.jsp";
	frm.submit();
}

function frmCancel(frm)
{
	frm.sType.value="cancel";
	frm.action = "../control/c006.jsp";
	frm.submit();
}
//firstFocus(frm.rq1);
////setSubmitFunction("confirmSave(frm)");
//setFormName("frm");	 

</script>	
<%
        OBHtml.showOBHomeEnd(out);
    }
    catch(Exception e)
    {
        Log.print(e.getMessage());
    }
%>
<%@ include file="/common/SignValidate.inc" %>