<%--
/*
 *�������ƣ�S857-Opt.jsp
 *����˵�����˻��������������ύ���ҳ��
 *��    ��: ����
 *�������: 2003��8��28��
 */
--%>
<!--Header start-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
 	response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
	<!--Header end-->
<% String strContext = request.getContextPath();%>
<%
    //�̶��������
    String strTitle = "�˻�������������";

    try
	{
         /**
          * isLogin start
          */
        //��¼���
        if( sessionMng.isLogin()==false)
        {
              OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
              out.flush();
              return;
        }

        //�ж��Ƿ���Ȩ��
        if(sessionMng.hasRight(request)==false)
        {
              out.println(sessionMng.hasRight(request));
              OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
              out.flush();
              return;
        }
        /**
         * isLogin end
         */
		long lAccountID = Long.parseLong( (String)request.getAttribute("lAccountID") ); 
		
        /**
         * presentation start
         */
       //��ʾ�ļ�ͷ
         OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />


<form name="form1" action="<%=strContext%>/system/S841-Ctr.jsp" method="post">

    
    <table  class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�˻�����Ȩ������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>
      <table width=100% border="0"   align=""> 
        <tr bgcolor="">
          <td colspan="3" height="1"></td>
        </tr>
        <tr bgcolor="">
          <td height="25">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������Ч</td>
          <td width="1" height="25"></td>
        </tr>
      </table>
      <br>
      <table width=100% border="0">
        <tr  colspan="3">
          <td width="445">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right"></div>
          </td>
          <td width="62">
            <div align="right">
			<!--a href="#" onClick="form1.submit() ;"><img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0"></a-->
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="form1.submit() ;">
			</div>&nbsp;&nbsp;
          </td>
        </tr>
      </table>
       </td>
  </tr>
</table>
	  </form>
<!--presentation end-->
<%
      }
      catch (IException ie)
      {
           OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
      }

      OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>