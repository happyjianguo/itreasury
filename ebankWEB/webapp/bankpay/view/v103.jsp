<%--
/*
 * �������ƣ�
 * ����˵����ҵ�񸴺������ʾҳ��
 * �������ߣ�baihuili
 * ���ڣ�2006��09��15��
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.rmi.*,java.sql.*,java.util.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

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
	String strIsCheck = (String)request.getAttribute("isCheck");//1 �Ǹ���
	if(strIsCheck==null) strIsCheck="";
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
        if (strIsCheck.equalsIgnoreCase("1"))
		{
        	/* ��ʾ�ļ�ͷ */
			OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		}
		else
		{
			OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		}		
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%if (strIsCheck.equalsIgnoreCase("1")) {out.print("ҵ�񸴺�ȷ��");}else{out.print("ҵ��ȡ������ȷ��");}%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	  <br/>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal" >
            <br>
<%
			OBBankPayInfo info=(OBBankPayInfo)request.getAttribute("info");
			System.out.println("********info in v103.jsp************"+info);
			
%>
				
				<%
				if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) out.print("���ҵ����Ϊ<b>�Ѹ���״̬</b>,");	
				if(info.getNiscanaccept()==3 ) out.print("��Ҫǩ�Ϻ���ܷ�������ָ��");
				else if(info.getNiscanaccept()==2 ) out.print("��Ҫ��˺���ܷ�������ָ��");
				else out.print("�Ѿ���������ָ��"); 
				%>
				<br><br>
				ָ�����к�Ϊ<b><%=info.getId()%></b>
              
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>      
     
        <tr class="MsoNormal">
          
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">

            <div align="right"><!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit()"-->
			<input type="button" name="Submitv00204" value=" ��  �� " class="button1" onClick="javascript:doback();"></div>

		  </td>
		  <td>&nbsp;</td>
        </tr>
        <tr><td height=5>&nbsp;</td></tr>
      </table>
</td>
</tr>
</table>
	 
	  <script language="JavaScript">
	  
	  function doback()
	  {
	  	window.location="../view/v101.jsp";
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