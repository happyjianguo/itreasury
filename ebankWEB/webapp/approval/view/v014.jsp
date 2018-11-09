<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：查找更改审批显示页面
 */
--%>
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
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
    response.setHeader("Cache-Control","no-stored");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
%>
<%
    Log.print("---------进入 v001.jsp 查找更改审批显示页面---------");
    try
    {
		String strTableTitle = "审批流设置";
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
		
		String strApproveName = "";//审批流名称
		String strApproveDesc = "";//审批流描述
		
		long lApprovalID = -1;	//审批设置标示
	    long lTotalLevel = 0;	//审批总级别
		long lLowLevel = 0;		//最少审批级别
	    long lIsPass = 0;		//是否允许越级
		long lStatusID = 1;		//当前审批状态：1、设置中；2、设置完成
		String strStatusName = "";
        
        if (request.getAttribute("Error") != null)
        {
		
            strTmp = (String)request.getAttribute("Error");
        %>
            <script language="javascript">alert("处理出错:"+'<%=strTmp%>');</script>
      <%}
			//如果保存不成功，则页面返回以前设置，如保存成功，则返回成功后的值
			
			// 获取strApproveName
			strTmp = (String)request.getAttribute("strApproveName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveName = strTmp;
				}
				catch(Exception e)
				{
					strApproveName = "";
				}
			}
			// 获取strApproveDesc
			strTmp = (String)request.getAttribute("strApproveDesc");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveDesc = strTmp;
				}
				catch(Exception e)
				{
					strApproveDesc = "";
				}
			}
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
	
			// 获取lTotalLevel
			strTmp = (String)request.getAttribute("lTotalLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lTotalLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lTotalLevel = -1;
				}
			}

						// 获取lLowLevel
			strTmp = (String)request.getAttribute("lLowLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lLowLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lLowLevel = -1;
				}
			}
	
			// 获取lIsPass
			strTmp = (String)request.getAttribute("lIsPass");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lIsPass = Long.parseLong(strTmp);
					System.out.println("lIsPass="+lIsPass);
				}
				catch(Exception e)
				{
					lIsPass = -1;
				}
			}
			
			// 获取lStatusID
			strTmp = (String)request.getAttribute("lStatusID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lStatusID = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lStatusID = -1;
				}
			}
			// 获取strStatusName
			strTmp = (String)request.getAttribute("strStatusName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strStatusName = strTmp;
				}
				catch(Exception e)
				{
					strStatusName = "";
				}
			}

%>
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/websys/js/Control.js"></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<form name="frm" method=post>
<input type="hidden" name="lType" value=0>
<input type="hidden" name="lApprovalID" value="<%=lApprovalID%>">
<table width="66%" class="top">
  <tr> 
    <td class="FormTitle" height="29" colspan="3"> 
      <p><b><font size="3">审批流设置（第一页）</font></b></p>
    </td>
  </tr> 
  		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle><font color="red">*</font> 审批流名称：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <input type="text" size="50" class=box name="strApproveName" maxlength="40" value="<%out.print(strApproveName);%>" onFocus="nextfield='strApproveDesc';">
          </td>          
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle><font color="red">*</font> 审批流描述：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <textarea  class=box name="strApproveDesc" onKeyDown="textCounter(document.frm.strApproveDesc);" onKeyUp="textCounter(document.frm.strApproveDesc);" onFocus="nextfield='lTotalLevel';"><%out.print(strApproveDesc);%></textarea>
          </td>          
        </tr>
		
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle><font color="red">*</font> 最大审批级别：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <input type="text" class=box name="lTotalLevel" maxlength="1"  onKeyUp="onup(frm.lTotalLevel)" value="<%if(lTotalLevel>0)  out.println(lTotalLevel);else out.println("");%>" onFocus="nextfield='lLowLevel';">
          </td>          
        </tr>	
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle><font color="red">*</font> 最小审批级别：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle colspan="2">
               <input type="text" class=box name="lLowLevel" maxlength="1"  onKeyUp="onup(frm.lLowLevel)" value="<%if(lLowLevel>0) out.println(lLowLevel);else out.println("");%>" onFocus="nextfield='lIsPass[0]';">
          </td>          
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle><font color="red">*</font> 是否允许越级审批：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle>
               <input type="radio" name="lIsPass" <%if(lIsPass==1) out.println("checked");%> value='1' onFocus="nextfield='lIsPass[1]';">是
               &nbsp;&nbsp;&nbsp;
				<input type="radio"  name="lIsPass" <%if(lIsPass==2) out.println("checked");%> value='2' onFocus="nextfield='submitfunction';">否
          </td>
        </tr>
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>  当前状态：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle>
               <input type="text" class=box name="txtStatus" value='<%=strStatusName%>' readonly>
          </td>          
        </tr>
        <tr bordercolor="#FFFFFF">
          <td height=30 align="right" colspan=3>
		  <%if(lApprovalID<0){%>
               <input type="button" name="NextSubmit" value=" 保存 " class = button 
                      onClick="frmSubmits(frm)" onKeydown="if(event.keyCode==13) frmSubmits(frm);" <%//if(lApprovalID>0)out.print("disabled");%>>
		  <%}else{%>
			   <input type="button" name="NextPage" value="分配人员" class = button 
			          onClick="frmNextPage(frm)" onKeydown="if(event.keyCode==13) frmNextPage(frm);">
		  <%}%>
          		 <input type="button" name="NextPage" value=" 返回 " class = button 
			          onClick="frmBack(frm)" onKeydown="if(event.keyCode==13) frmBack(frm);">
		  </td>
        </tr>
      </table>
              <input type=hidden name="type">
</form>
<script language="javascript">
function frmSubmits(frm)
{	
	if(!validateFields(frm)) return;

	if(frm.lLowLevel.value<=0 || frm.lTotalLevel.value<=0)
	{
	   alert("审批级别不应为负数或零");
	   return false;
	}

	if (frm.lLowLevel.value > frm.lTotalLevel.value)
	{
		alert("最小审批级别不应大于最大审批级别");
		frm.lLowLevel.focus();
        return false;
	}
	
    if (frm.lIsPass[0].status==false && frm.lIsPass[1].status==false)
    {
        alert('请输入是否允许越级审批');
        frm.lIsPass[0].focus();
        return false;
    }
	if(confirm("是否保存?"))
	{
		frm.lType.value = 0;        //保存标识
		frm.action = "../control/c001.jsp";
		document.frm.lTotalLevel.value=fntrimspace(document.frm.lTotalLevel.value);
		document.frm.lLowLevel.value=fntrimspace(document.frm.lLowLevel.value);
		frm.submit();
		return true;
    }
}

function frmNextPage(frm)
{    
    if(!validateFields(frm)) return;
	if (frm.lLowLevel.value > frm.lTotalLevel.value)
	{
		alert("最小审批级别不应大于最大审批级别");
		frm.lLowLevel.focus();
        return false;
	}
	
    if (frm.lIsPass[0].status==false && frm.lIsPass[1].status==false)
    {
        alert('请输入是否允许越级审批');
        frm.lIsPass[0].focus();
        return false;
    }
    frm.lType.value = 1;        //翻页标识
    frm.action="../control/c001.jsp";
    frm.submit();
    return true;
}
function frmBack(frm)
{
    frm.action = "../view/v001.jsp";
    frm.submit();
}
function allFields()
{			
		this.aa = new Array("strApproveName","审批流名称","string",1);
		this.ab = new Array("strApproveDesc","审批流描述","string",1);
		this.ac = new Array("lTotalLevel","最大审批级别","int",1);
		this.ad = new Array("lLowLevel","最小审批级别","int",1);		
		this.af = new Array("txtStatus","当前状态","string",0);		
} 
function alertError(err)
{
    alert("发生错误："+err);
}
function fntrimspace(str)//去除首尾空格函数
{
	nospace=1;
	var str1=str;
	if(str1.charAt(0)==" ")
	{
		str1=str1.substring(1);
		nospace=0;
	}
	var len=str1.length;
    if(str1.charAt(len-1)==" ")
	{
		str1=str1.substring(0,len-1);
		nospace=0;
	}
	if(nospace==0)
	{
       str1=fntrimspace(str1);
	}

 return str1;
}
function onup(obj){
	var namevalue=obj.value;
	var dd=fntrimspace(namevalue);
	obj.value=dd;
}
function textCounter(field) {
               var maxlimit=200;
            // 定义函数，传入3个参数，分别为表单区的名字，表单域元素名，字符限制；
           if (field.value.length > maxlimit)
           //如果元素区字符数大于最大字符数，按照最大字符数截断；
           field.value = field.value.substring(0, maxlimit);
    }
</script>
<script language="javascript">
    setFormName("frm");
    firstFocus(document.frm.strApproveName);
    //setSubmitFunction("frmSubmits(frm)");
	
</script>

<%
        /**
		* 现实文件尾
		*/
		OBHtml.showOBHomeEnd(out);	
    }
    catch(Exception e)
    {
        Log.print(e.getMessage());
    }
%>
<%@ include file="/common/SignValidate.inc" %>