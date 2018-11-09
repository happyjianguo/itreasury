<%--
/*
 * 程序名称：v013.jsp
 * 功能说明：查找更改审批显示页面
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
                com.iss.itreasury.ebank.approval.dataentity.*,               com.iss.itreasury.ebank.util.*"%>
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
		
        ApprovalSettingInfo info = null;
		if(request.getAttribute("relustInfo") != null)
		{
			info = (ApprovalSettingInfo)request.getAttribute("relustInfo");
			strApproveName = info.getName();//审批流名称
			strApproveDesc = info.getDesc();//审批流描述
			lApprovalID = info.getID();	//审批设置标示
	    	lTotalLevel = info.getTotalLevel();	//审批总级别
			lLowLevel = info.getLowLevel();		//最少审批级别
	    	lIsPass = info.getIsPass();		//是否允许越级
			lStatusID = info.getStatusID();		//当前审批状态：1、设置中；2、设置完成
			strStatusName = Constant.ApprovalStatus.getName(lStatusID);
		}
%>
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/websys/js/Control.js"></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<form name="frm" method=post action="../control/c011.jsp">
<input type="hidden" name="lType" value=0>
<input name="strAction" type="hidden">
<input name="strSuccessPageURL" type="hidden" value="../view/v012.jsp">
<input name="strFailPageURL" type="hidden" value="../view/v012.jsp">
<input type="hidden" name="lApprovalID" value="<%=lApprovalID%>">
<table width="66%" class="top">
  <tr> 
    <td class="FormTitle" height="29" colspan="3"> 
      <p><b><font size="3">审批流设置</font></b></p>
    </td>
  </tr> 
  		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>审批流名称：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <input type="text" size="50" class=box name="strApproveName" value="<%out.print(strApproveName);%>" onFocus="nextfield='strApproveDesc';" >
          </td>          
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>审批流描述：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <textarea  class=box name="strApproveDesc" onFocus="nextfield='lTotalLevel';" ><%out.print(strApproveDesc);%></textarea>
          </td>          
        </tr>
		
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>最大审批级别：</td>          
		  <td align=left borderColor=#999999 height=32 vAlign=middle colspan="1">
               <input type="text" class=box name="lTotalLevel" value="<%if(lTotalLevel>0) out.println(lTotalLevel);else out.println("");%>" onFocus="nextfield='lLowLevel';" >
          </td>          
        </tr>	
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>最小审批级别：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle colspan="2">
               <input type="text" class=box name="lLowLevel" value="<%if(lLowLevel>0) out.println(lLowLevel);else out.println("");%>" onFocus="nextfield='lIsPass[0]';" >
          </td>          
        </tr>
		<tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>是否允许越级审批：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle>
               <input type="radio" name="lIsPass" <%if(lIsPass==1) out.println("checked");%> value='1' onFocus="nextfield='lIsPass[1]';" >是
               &nbsp;&nbsp;&nbsp;
				<input type="radio"  name="lIsPass" <%if(lIsPass==2) out.println("checked");%> value='2' onFocus="nextfield='submitfunction';" >否
          </td>
        </tr>
        <tr bordercolor="#FFFFFF"> 
          <td width="30" height="29">&nbsp;</td>
          <td borderColor=#999999 height=32 vAlign=middle>当前状态：</td>
          <td align=left borderColor=#999999 height=32 vAlign=middle>
               <input type="text" class=box name="txtStatus" value='<%=strStatusName%>'  disabled>
          </td>          
        </tr>
        <tr bordercolor="#FFFFFF">
          <td height=30 align="right" colspan=3><input type=hidden name="type">
		  <input type="button" name="btnUpdate" value=" 保 存 " class = button onClick="toUpdate();" onKeydown="if(event.keyCode==13) toUpdate();">		
		  <input type="button" name="btnDispatch" value=" 分配人员 " class = button onClick="toDispatch();" onKeydown="if(event.keyCode==13) toDispatch();">			   
		  <%if(lStatusID==Constant.ApprovalStatus.SAVE || lStatusID==Constant.ApprovalStatus.SUBMIT){%>
		  <input type="button" name="btnDelete" value=" 删除 " class = button onClick="toDelete();" onKeydown="if(event.keyCode==13) toDelete();">
		  <%}else if(lStatusID == Constant.ApprovalStatus.INVALID){%>
		  <input type="button" name="btnValid" value=" 恢复 " class = button onClick="toValid();" onKeydown="if(event.keyCode==13) toValid();">
		  <%}%>
               <input type="button" name="btnClose" value=" 返回 " class = button onClick="toBack();" onKeydown="if(event.keyCode==13) toBack();">			   
          </td>
        </tr>
      </table>             
</form>
<script language="javascript"> 
function toUpdate()
{
	if(!validateFields(frm)) return;
	if (frm.lLowLevel.value > frm.lTotalLevel.value)
	{
		alert("最小审批级别不应大于最大审批级别");
		frm.lLowLevel.focus();
        return false;
	}
	if(confirm("是否保存修改?"))
	{
		frm.strAction.value = "toUpdate";
		frm.strSuccessPageURL.value = "../view/v013.jsp";
		frm.strFailPageURL.value = "../view/v013.jsp";
		frm.submit();
	}
}  
function toDispatch()
{
	if(confirm("已保存修改并重新分配人员?"))
	{
		frm.strAction.value = "toDispatch";
		frm.submit();
	}
}  
function toBack()
{
	frm.strAction.value = "toBack";
	frm.submit();
	
}  
function toDelete()
{
	if(confirm("是否删除?"))
	{
		frm.strAction.value = "toDelete";
		frm.submit();
	}
}  
function toValid()
{
	if(confirm("是否恢复该审批流设置?"))
	{
		frm.strAction.value = "toValid";
		frm.submit();
	}
} 
function allFields()
{			
		this.aa = new Array("strApproveName","审批流名称","string",1);
		this.ab = new Array("strApproveDesc","审批流描述","string",1);
		this.ac = new Array("lTotalLevel","最大审批级别","int",1);
		this.ad = new Array("lLowLevel","最小审批级别","int",1);		
}  
//setSubmitFunction("toBack()");
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