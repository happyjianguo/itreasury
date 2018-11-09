<%--
/*
 * �������ƣ�C416.jsp
 * ����˵��������ָ���������˻�ȡ���������ҳ��
 * �������ߣ�����
 * ������ڣ�2003��09��25��
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
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<!--Header end-->

<%
	//�������
	String strTitle = "��ʸ���";
%>

<%
	/* �û���¼�����Ȩ��У�� */
	try{ 
         /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
           
        /* ��ʾ�ļ�ͷ */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		String temp = "";
		String strMessage1 = "";
		String strMessage2 = "";
		temp = (String)request.getAttribute("strMessage1");
		
		if(temp!=null&&temp.trim().length()>0)
		{
			strMessage1 = temp;
		}
		
		temp = (String)request.getAttribute("strMessage2");
		if(temp!=null&&temp.trim().length()>0)
		{
			strMessage2 = temp;
		}
		String strContext = request.getContextPath();

	
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>

<safety:resources />
 
    
    <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ҵ�񸴺�</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>
<table width=100% border="0" cellspacing="0" cellpadding="0" align="" class = normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal">
           
<%
		
			
			out.print("<br>"+strMessage1);
			out.print("<br>"+strMessage2);
		
%>
           
			</td>
          <td width="1" height="25"></td>
        </tr>
       <tr >
         
          <td colspan="2">
            <div align="right">
			<!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit();"-->
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:doExit();">&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr><td height="5"></td></tr>
      </table>
     
       </td>
  </tr>
</table>

	  <script language="JavaScript">
	  function doExit()
	  {
			window.location.href="<%=strContext%>/capital/check/view/check_v001.jsp";
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
		OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>