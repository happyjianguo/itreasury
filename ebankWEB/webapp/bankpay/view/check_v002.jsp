<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<% String strContext = request.getContextPath();%>
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
        
         long lTransType = -1;           //��������
         long NSTATUS = -1;              //״̬
         String sStartSubmit = "";       //�ύ����-��
         String sEndSubmit = "";         //�ύ����-��
         double dMinAmount = 0.0;        //���׽��-��Сֵ
         double dMaxAmount = 0.0;        //���׽��-���ֵ
         String strTemp = "";            //��ʱ����
         //��ѯ������ȡ
         strTemp = (String) request.getParameter("lTransType");
         if(strTemp != null && strTemp.trim().length() > 0) {
            lTransType = Long.parseLong(strTemp);                                    // ��������
         }
         strTemp = (String) request.getParameter("NSTATUS");
         if(strTemp != null && strTemp.trim().length() > 0) {
            NSTATUS = Long.parseLong(strTemp);                                        // ����ָ��״̬
         }
         strTemp = (String) request.getParameter("sStartSubmit");
         if(strTemp != null && strTemp.trim().length() > 0) {
            sStartSubmit = strTemp;                                                   // �ύ����-��
         }
         strTemp = (String) request.getParameter("sEndSubmit");
         if(strTemp != null && strTemp.trim().length() > 0) {
           sEndSubmit = strTemp;                                                     // �ύ����-��
         }
         strTemp = (String) request.getParameter("dMinAmount");
         if(strTemp != null && strTemp.trim().length() > 0) {
            dMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // �����Сֵ
        }
        strTemp = (String) request.getParameter("dMaxAmount");
        if(strTemp != null && strTemp.trim().length() > 0) {
            dMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // ������ֵ
        }
       
       	/* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
			
			
			
		
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%out.print("ҵ�񸴺�ȷ��");%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	  <br/>
	  <form method="post" name="form1">
      <input type="hidden" name="dMinAmount" value="<%=dMinAmount %>">
      <input type="hidden" name="dMaxAmount" value="<%=dMaxAmount %>">
      <input type="hidden" name="sStartSubmit" value="<%=sStartSubmit%>">
      <input type="hidden" name="sEndSubmit" value="<%=sEndSubmit%>">
      <input type="hidden" name="NSTATUS" value="<%=NSTATUS%>">
      <input type="hidden" name="lTransType" value="<%=lTransType%>">
      <input type="hidden" name="strAction" value="search">
      <input name="strSuccessPageURL"  type="hidden" >
	  <input name="strFailPageURL"  type="hidden" >
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        
    <%   
    OBBankPayInfo  info = null;   
    Vector rf = null;//��ʾ��Ϣ
		rf = (Vector)request.getAttribute("info");
    if (rf != null && rf.size() > 0) 
				{
					info  = new OBBankPayInfo();
					for( int i=0; rf.size() != -1 && i < rf.size(); i++ )
					{
						info = (OBBankPayInfo)rf.elementAt(i);
	%> 
        	<tr class="MsoNormal">
            <td width="5" height="25" class="MsoNormal"></td>

   		    <td height="" class="MsoNormal" >
            <br>
<%			
			if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK) 
			out.print("���ҵ����Ϊ<b>�Ѹ���״̬</b>,");	
			if(info.getNiscanaccept()==3 )
			out.print("��Ҫǩ�Ϻ���ܷ�������ָ��");
			else if(info.getNiscanaccept()==2 )
			out.print("��Ҫ��˺���ܷ�������ָ��");
			else out.print("�Ѿ���������ָ��"); 
			%>
			<br>
			<br>
				ָ�����к�Ϊ<b><%=info.getId()%></b>
			</td>
           <td width="1" height="25" class="MsoNormal"></td>
          </tr>      
     <%}
     } %>
        <tr class="MsoNormal">
          
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">

            <div align="right">
            <input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:doExit();">&nbsp;&nbsp;
		  </td>
		  <td>&nbsp;</td>
        </tr>
        <tr><td height=5>&nbsp;</td></tr>
      </table>
      </form>
		</td>
		</tr>
		</table>

	 
	  <script language="JavaScript">
	   function doExit()
	  {
			
			form1.action = "<%=strContext%>/bankpay/control/check_c001.jsp";
			form1.strSuccessPageURL.value="<%=strContext%>/bankpay/view/check_v001.jsp";
			form1.strFailPageURL.value="<%=strContext%>/bankpay/view/check_v001.jsp";
	        showSending();
		    document.form1.submit();
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