<%--
/*
 * �������ƣ�
 * ����˵����ҵ�񸴺�
 * �������ߣ�baihuili
 * ���ڣ�2006��09��15��
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
                 com.iss.itreasury.settlement.util.NameRef,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->
<%
	//�������
	String strTitle = "[���л��]";
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
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
		
			
		String [] sId=(String [])request.getAttribute("id");
		System.out.print("++++++++++++++++++++++++++++++++"+sId.length);
		OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
%>
<safety:resources />

	  <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ҵ��ȡ������ȷ��</span></td>
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
   		OBBankPayInfo info=financeInstr.findByID(Long.parseLong(sId[i]));
   %>
        <tr class="MsoNormal">
          <td width="40" height="25" class="MsoNormal"></td>
   
    <td height="" class="MsoNormal" >
            <br>
            ���ҵ����Ϊ���ύ״̬,��Ҫ���˺�����ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>
			<br>
			<br>
				<b>ָ�����к�Ϊ<%=sId[i]%></b>
             
              <br><br>
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
			<input type="button" name="Submitv00204" value=" ��  �� " class="button1" onClick="window.close();"></div>

		  </td>
        </tr>
      </table>
      </td>
      </tr>
      </table>
	 
	  <script language="JavaScript">
	  
	  function doback()
	  {
	  	window.location="../view/v104.jsp";
	  }
	  function window.onunload()
	  {   
          window.opener.document.form1.ActionID.value="<%=new java.util.Date().getTime()%>";
          window.opener.doQuery();
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
	/* ��ʾ�ļ�β*/
	OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>