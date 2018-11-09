 <%--
/*
 * �������ƣ�v004.jsp
 * ����˵���������ÿ��������ҳ
 * �������ߣ�banreyliu
 * ������ڣ�2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:directive.page import="com.iss.itreasury.util.Constant"/>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.OBBudgetStatus" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ÿ��������]";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	if((String)request.getParameter("ID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("ID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("�����ɹ���");
			</script>
	 <%
		}
	}
%>
<%
	
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
         // �û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v004.jsp">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/v004.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">			<!--��������-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="modifyDate" value="">
		<%
		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjj"+sessionMng.m_lClientID);
		%>

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�ÿ�ƻ�����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>		
    </td>
  </tr>
    </table>	
    <br/>
       <table width="80%" border="0" cellspacing="0" cellpadding="0" class=normal align=""> 
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
            long sPeriod=-1;
              String strMagnifierNamePeriod = URLEncoder.encode("�ÿ�����");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetdtstart","budgetdtend","budgetid","modifyDate"};
              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","startdate","enddate","id","modifydate"};
              String[] strReturnNamesPeriod = {"budgetID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {"-1"};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("�ÿ�����"),URLEncoder.encode("�ÿ��˻�"),URLEncoder.encode("�ÿ�ʱ������")};
              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              long type = Constant.FALSE;
              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname,"+OBConstant.OBBudgetStatus.APPROVE+","+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+","+type+")";
              String strMatchValuePeriod = "sname";
              String strNextControlsPeriod = "adjustdate";
              String strTitlePeriod = "<font color=red>*</font>�ÿ�����";
              String strFirstTDPeriod = "15%";
              String strSecondTDPeriod= "25%";

              OBMagnifier.showZoomCtrl(out
              ,strMagnifierNamePeriod
              ,strFormNamePeriod
              ,strPrefixPeriod
              ,strMainNamesPeriod
              ,strMainFieldsPeriod
              ,strReturnNamesPeriod
              ,strReturnFieldsPeriod
              ,strReturnInitValuesPeriod
              ,strReturnValuesPeriod
              ,strDisplayNamesPeriod
              ,strDisplayFieldsPeriod
              ,nIndexPeriod
              ,strMainPropertyPeriod
              ,strSQLPeriod
              ,strMatchValuePeriod
              ,strNextControlsPeriod
              ,strTitlePeriod
              ,strFirstTDPeriod
              ,strSecondTDPeriod);
           
%>          
 		 
 		<input name="accountID"   type="hidden">
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
        <tr>
        <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal">�ÿ��˺ţ�</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" type="text" readonly></td>
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ����䣺��</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" size="12" readonly >
          </td>
		  <td width="15%" height="25" class="MsoNormal">����</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  readonly size="12">
		  </td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ����˵����</td>
          <td width="25%" colspan="3"><input class="box"   type="text" name="adjustNote" size="50" maxlength="50"></td>
		  
        </tr> 
 
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >

        <tr>
         <td colspan="7"> </td>
            <td align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" ��һ�� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</td>
          <td >&nbsp;</td>
        </tr>
        <tr><td >&nbsp;</td></tr>
      </table>
      <br>
       
  <input name="sysdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%>>
	  <!--��ʽ��̬��ʾ�տ����-->


<%
		
%>
	 <!-- ˢ������ -->
<input type="hidden" name="clickCount" value="0">
<!-- ˢ������ --> 
</form>
 
<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(form_1.budgetname);
	////setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">
 /* �޸��ύ������ */
    function addme()
    {  	
		form_1.action = "../view/v010.jsp";
		if (validate() == true)
        {	  
        	 //alert(form_1.budgetid.value);
			showSending();
            form_1.submit();
            
        }
    }
    /* ȡ�������� */
    function cancelme()
    {
		 	
			if (confirm("�Ƿ�ȡ����"))
			{
				form_1.action="../view/v004.jsp";
				form_1.submit();
			}
		 
    }
function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        **/
        if (form_1.budgetID.value < 0)
		{
			alert("�ÿ�������ӷŴ���ѡ��");
			form_1.budgetID.focus();
			return false;
		}

		if (!InputValid(form_1.adjustNote, 0, "", 1, 0, 100,"�ÿ����˵��"))
		{
			return false;
		}
		 

    	return true;
		
    }
</script>
<%

	  }
	catch (IException ie)
	{
		
		
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
	/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);	
%>
 
<%@ include file="/common/SignValidate.inc" %>