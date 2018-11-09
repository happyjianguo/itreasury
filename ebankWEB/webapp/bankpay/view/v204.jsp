<%--
/*
 * 程序名称：
 * 功能说明：业务审核－取消审核
 * 作　　者：baihuili
 * 日期：2006年09月15日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->
<%
	//标题变量
	String strTitle = "[银行汇款]";
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.NO;
	
	
	try{ 
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
       
        	/* 显示文件头 */
			OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
			
		String [] sId=(String [])request.getAttribute("id");
		System.out.print("++++++++++++++++++++++++++++++++"+sId.length);
%>
<safety:resources />   
      <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">业务审核确认</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	  <br/>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <%       
   for(int i=0;i<sId.length;i++)
   {
   		OBBankPayInfo info  =  new OBBankPayInfo();
   		OBFinanceInstrEJB  dao = new OBFinanceInstrEJB();
   		info = dao.findByID(Long.parseLong(sId[i]));
   %>
        <tr class="MsoNormal">
          <td width="40" height="25" class="MsoNormal"></td>
   
    <td height="" class="MsoNormal" >
            <font size="3"><br>
           <%
				if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) out.print("这笔业务现为<b>已复核状态</b>,");	
				if(info.getNiscanaccept()!=1 ) out.print("需要签认后才能发送银行指令");
				else out.print("已经发送银行指令"); 
				%> 
			<br>
				指令序列号为<b><%=sId[i]%></b>
              </font> 
			</td>
			
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>      
     <%}%>
        <tr class="MsoNormal">
          
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">

            <div align="right"><!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit()"-->
			<input type="button" name="Submitv00204" value=" 关  闭 " class="button" onClick="javascript:doback();"></div>

		  </td>
        </tr>
      </table>
      </td>
      </tr>
      </table>
	 
	  <script language="JavaScript">
	  
	  function doback()
	  {
	  	window.opener.location.reload();
	  	window.close();
	  	//window.location="../view/v201.jsp";
	  }
	  </script>
<%
   }
   catch(IException ie)
   {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
%>

<%
	/* 显示文件尾*/
	OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>