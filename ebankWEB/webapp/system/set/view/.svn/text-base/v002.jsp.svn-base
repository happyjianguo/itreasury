<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：基础设置-汇款用途摘要设置
 * 作　　者：
 * 完成日期：2010年9月19日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import="java.text.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<!--Header end-->

<%
	//固定变量
	String strTitle = null;
	String datetime=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); //获取系统时间
	Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
%>

<%
     /* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        // 用户登录检测 
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

        
        //显示文件头   
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String strContext = request.getContextPath();//http://xxxx/../cpob
        Collection collection = (Collection)request.getAttribute("collection");
		Iterator iterator = null;
		if (collection != null)
		{
            iterator = collection.iterator();
		}
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />

     <% /***********************StandardAbstract Information 标准摘要********************************/ %>
	
   <% /* 如果上一页面传过来的lID为空 
       * 就把页面所有的字段 
	   * 字符串型 的 设为  "" 空串
	   * long型   的 设为 0 然后 转化为空串 --因为 不能在页面显示0
       */ 
   %>  
        <%
		OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
        OBAbstractSettingInfo info = new OBAbstractSettingInfo();
        
        long lFlag = 1;
        String sTemp = null ;
    	long lResult;
    	long lID = 0;
    	String strCode = null;
    	String strDesc = null;
        
    	String strAction = "";        
    	sTemp = (String)request.getAttribute("lID");
    	lID = Long.parseLong(sTemp);

    /* IF getAttribute 为 Type 运行下面的程序 否则 不运行
     * 根据lID 判定 saveBranch 方法 是save 还是update
     */

    strAction = (String)request.getAttribute("type");

    sTemp = (String)request.getAttribute("lID");
    if (sTemp != null && sTemp.length() > 0)
        lID = Long.parseLong(sTemp);
    sTemp = (String)request.getAttribute("strCode");
    if (sTemp != null && sTemp.length() > 0 )
        strCode = sTemp;
    sTemp = (String)request.getAttribute("strCode1");
    if (sTemp != null && sTemp.length() > 0 )
        strCode = sTemp;
    sTemp = (String)request.getAttribute("strDesc");
    if (sTemp != null && sTemp.length() > 0)
        strDesc = sTemp;
        
		
	info.setId(lID);
	info.setScode(strCode);
	info.setSdesc(strDesc);
	info.setNofficeid(sessionMng.m_lOfficeID);
	info.setNclientid(sessionMng.m_lUserID);
	info.setInputtime(now);
	info.setModifytime(now);

    if (strAction != null && strAction.equals("save"))
    {
        lResult = OBAbstractSetting.saveStandardAbstract(info);
        if(lResult == -1){
        	%>
			 <script language="javascript" type="text/javascript">
	           alert("摘要内容已存在");
			 </script>
			<%
        }else{
       		sessionMng.getActionMessages().addMessage("保存成功");
			%>
			 <script language="javascript" type="text/javascript">
	           window.location.href="v001.jsp?control=view"; 
			 </script>
			<%
        }
    }
    if (strAction != null && strAction.equals("delete"))
    {
        OBAbstractSetting.deleteStandardAbstract(String.valueOf(lID));
        sessionMng.getActionMessages().addMessage("删除成功");
        %>
		 <script language="javascript" type="text/javascript">
           window.location.href="v001.jsp?control=view"; 
		 </script>
		<%        
    }

    
    OBAbstractSettingInfo tmpInfo = new OBAbstractSettingInfo();    
    if(lID!= 0 && lID!=-1)
	{
		tmpInfo = OBAbstractSetting.findStandardAbstractByID( lID );
		lFlag = 2;
	}
		
		
	else
	    {
	        sTemp= (String)request.getAttribute("lID");
	        lID  = Long.parseLong(sTemp);
			long lMaxCode = OBAbstractSetting.getMaxCode(sessionMng.m_lOfficeID,sessionMng.m_lUserID);
			tmpInfo.setScode(DataFormat.formatInt(lMaxCode,5,true,0));
	       }
        %> 
<form name="form1" method="post" action="v002.jsp">
<table cellpadding="0" cellspacing="0" class="title_top">
		<tr>
		    <td height="22">
				<br>
				<table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
					<TR>
						<td class=title><span class="txt_til2">标准摘要定义设置</span></td>
					    <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
<br/>
      <TABLE align=center border=0 height=71 width="98%" class=normal>
        <TBODY>
        
        
        <input type="hidden" name="type" value="" >
        <input type="hidden" name="lID"    value=<%=lID%> >
		<input type="hidden" name="control" value="view">
		<input type="hidden" name="Flag" value="<%=lFlag%>">
        <TR><td height=5>&nbsp;</td></TR>
        <TR>
          <TD borderColor=#B1D0EC height=22 width="20%">&nbsp;&nbsp;摘要代号：</TD>
          <TD borderColor=#B1D0EC colSpan=3 height=22 width="80%">
		  
		   <%
		   if( lID > 0 )
		   {
		   %>
		  <INPUT class=box name=strCode value="<%=tmpInfo.getScode()%>"  onFocus="nextfield ='strDesc';" disabled> 
		   <input type="hidden" name="strCode1" value="<%=tmpInfo.getScode() %>">
		   <%
		   }
		   else
		   {
		   %>
		  <!-- 
		  <INPUT class=box name=strCode value="<%=tmpInfo.getScode()%>" onBlur="formatSCode();" onFocus="nextfield ='strDesc';" maxlength="<%=Constant.LengthControl.Code%>"> 
		   -->
		   <%=tmpInfo.getScode() %>
		   <input type="hidden" name="strCode" value="<%=tmpInfo.getScode() %>">
		   <%
		   }
		   %>
		  
		  </TD>
		</TR>
       <tr>
			<%com.iss.itreasury.util.Constant.CommonTextarea.show(out,"摘要描述：","height=12 width='20%'","strDesc","",true,3,65,tmpInfo.getSdesc()!=null?tmpInfo.getSdesc():strDesc!=null?strDesc:"",100,"submitfunction","" ); %>
	   </tr>
	   
        <TR>
          <TD borderColor=#B1D0EC colSpan=4 height=17>
            <DIV align=right>
			<INPUT type=button class=button1 name=Submit322  value=" 保 存 " onclick="ConfirmBack1(form1,'保存标准摘要定义？');"> 
<%
	if(lID>0)
	{
%>
			<INPUT class=button1 name=Submit1  type="button" value=" 删 除 " onClick="javascript:deleteData(form1)"> 
<%
	}
%>
			<INPUT class=button1 name=Submit332 onclick="ConfirmBack('v001.jsp?control=view', '放弃保存标准摘要定义？');" type=button value=" 返 回 "> 
            </DIV></TD>
            <td>&nbsp;</td>
            </TR>
            <tr><td heigth=3>&nbsp;</td></tr>
            
            </TBODY></TABLE>
            <br>
     </TD>
   </TR>
</TBODY>
</TABLE>
</form>		
<% /*************************HTML 的结束标志 ********************************/ %>

<%
	//显示文件尾
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
	
%>
<SCRIPT language=JavaScript>

function trimString(stringValue)
{
	var s = stringValue;
	while (s.substring(0,1) == ' ') 
	{
    	s = s.substring(1,s.length);
 	}
    while (s.substring(s.length-1,s.length) == ' ') 
	{
    	s = s.substring(0,s.length-1);
    }
    return s;
}

 function  ConfirmBack(URL, MSG)
{
	if (confirm(MSG)) 
	{
		eval("self.location='"+URL+"'");
	}   
}
function formatSCode()
{
	var length = form1.strCode.value.length ;
	if(length < 5)
	{
		for(i = 0; i < (5-length); i ++)
		{
			form1.strCode.value = "0" + form1.strCode.value;
		}
	}else if(length > 5)
	{
		form1.strCode.value = form1.strCode.value.substr(0,5);
	}
}
 function  ConfirmBack1(frm,MSG)
{
	if (! InputValid(frm.strCode,1, "int",0, 0, 0,"摘要代号")) return false;
	if( frm.strCode.value.length != 5 )
	{
        alert("标准摘要代号必须是五位.");
        return false;
	}
	if (! InputValid(frm.strDesc,1, "string",0, 0, 0,"摘要描述")) return false;
	
	if( trimString(frm.strDesc.value).length == 0 )
	{
		alert("摘要描述栏位不能输入空格，请返回录入！");
		frm.strDesc.value = "";
		frm.strDesc.focus();
		return false;
	}
	
	if (confirm(MSG)) 
	{
		//eval("parent.location='"+URL+"'");
        frm.type.value="save";
        frm.control.value="save";
			showSending();//显示正在执行
        frm.submit();
		return true;
	}   else
	{
	 	return false;
	}
}
function deleteData(form)
{
	if (confirm("删除标准摘要定义？"))
	{
		form.type.value="delete";
		form.control.value="delete";
			showSending();//显示正在执行
		form.submit();
	}
}
    if( parseInt(document.form1.Flag.value) == 1 )
	{
		firstFocus(document.form1.strDesc);
	}
	else
	{
		firstFocus(document.form1.strDesc);
	}
	setSubmitFunction("ConfirmBack1(form1,'保存标准摘要定义？')");
    setFormName("form1");
//-->
</SCRIPT>
<%@ include file="/common/SignValidate.inc" %>
