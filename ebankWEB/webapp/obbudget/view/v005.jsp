<%--
/*
 * �������ƣ�v004.jsp
 * ����˵���������ÿ����ƥ��ҳ
 * �������ߣ�banreyliu
 * ������ڣ�2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ÿ��������]";
	OBBudgetAdjustInfo cinfo = new OBBudgetAdjustInfo();
	OBBudgetInfo rBudgetInfo = new OBBudgetInfo();
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	if((String)request.getParameter("RID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("RID"))==-1)
		{
	 %>
	 		<script language="JavaScript">
			alert("û����ƥ����ÿ������¼�룡");
			</script>
	 <%
		 if(request.getAttribute("CInfo")!=null)
			{
				cinfo = (OBBudgetAdjustInfo)request.getAttribute("CInfo");	
				rBudgetInfo = (OBBudgetInfo)request.getAttribute("rBudgetInfo");
			}
		}
		if(Long.parseLong((String)request.getParameter("RID"))==-2)
		{
	 %>
	 		<script language="JavaScript">
			alert("����ʧ�ܣ�");
			</script>
	 <%
		}
		if(Long.parseLong((String)request.getParameter("RID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("���˳ɹ���");
			</script>
	 <%
		}
	}else{
		cinfo = new OBBudgetAdjustInfo();
		rBudgetInfo = new OBBudgetInfo();
	}
 
	
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

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v006.jsp?check=true">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/v005.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">			<!--��������-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="250" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �ÿ����� - �ÿ�������� - ����</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>	
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align=center>
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
          	  long sPeriod=cinfo.getBudgetID()>0?cinfo.getBudgetID():-1;
              String strAccname = cinfo.getBudgetID()>0?rBudgetInfo.getSname():"";
              
              String strMagnifierNamePeriod = URLEncoder.encode("�ÿ�����");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetamount","budgetdtstart","budgetdtend","budgetid"};
              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","amount","startdate","enddate","id"};
              String[] strReturnNamesPeriod = {"budgetID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= strAccname;
              String[] strReturnValuesPeriod = {String.valueOf(sPeriod)};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("�ÿ�����"),URLEncoder.encode("�ÿ��˻�"),URLEncoder.encode("�ÿ�ʱ������")};
              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname,"+OBConstant.OBBudgetStatus.AUTHED+")";
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
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" type="text" readonly value="<%=rBudgetInfo.getAccountID()>0?NameRef.getBankAcctNameByAcctID(rBudgetInfo.getAccountID()):""%>"></td>
          <td  height="25" class="MsoNormal">�ÿ��</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="budgetamount" type="text" readonly value="<%=rBudgetInfo.getAmount()>0.00?DataFormat.formatDisabledAmount(rBudgetInfo.getAmount(),2):""%>"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ����䣺��</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" size="12" readonly value="<%=rBudgetInfo.getStartdate()!=null?rBudgetInfo.getStartdate().toString().substring(0,10):""%>">
          </td>
		  <td width="15%" height="25" class="MsoNormal">����</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  readonly size="12" value="<%=rBudgetInfo.getEnddate()!=null?rBudgetInfo.getEnddate().toString().substring(0,10):""%>">
		  </td>
        </tr> 
         <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>   
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>�ÿ������Ч���ڣ�</td>
          <td width="25%" class="MsoNormal" >
          		<fs_c:calendar 
	         	    name="adjustdate"
		          	value="" 
		          	properties="nextfield ='amount'" 
		          	size="12"/>
		    	<script>
	          		$('#adjustdate').val('<%=cinfo.getAdjustdate()!=null?cinfo.getAdjustdate().toString().substring(0,10):""%>');
	          	</script>
		</td>
          <td  height="25" class="MsoNormal"><font color=red>*</font>�ÿ������</td>
          <td  height="25" class="MsoNormal"><%=sessionMng.m_strCurrencySymbol%>
            <script  language="JavaScript">
				createAmountCtrl("form_1","amount","<%=cinfo.getAmount()>0.00?DataFormat.formatDisabledAmount(cinfo.getAmount(),2):""%>","","","<%=sessionMng.m_lCurrencyID%>");
			</script>
			</td>
		  
        </tr> 
        
 
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
      </table>
      <br>
       
  <input name="sysdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%>>
	  <!--��ʽ��̬��ʾ�տ����-->


      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" ƥ  �� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</div>
          </td>
		  <td width="9" height="17"></td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name=add type=button value=" ȡ  �� " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
			</div>
          </td>
        </tr>
      </table>
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
		form_1.action = "../control/c005.jsp?action=match";
		if (validate() == true)
        {	  
 
			/* ȷ���ύ */
			 if (!confirm("�Ƿ�ƥ�䣿"))
			{
				return false;
			}
			form_1.adjustdate.value = formatedate(form_1.adjustdate.value);
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
    /* ȡ�������� */
    function cancelme()
    {
		 	
			if (confirm("�Ƿ�ȡ����"))
			{
				form_1.action="../view/v005.jsp";
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
			form_1.budgetname.focus();
			return false;
		}
 
		if (!checkDate(form_1.adjustdate,1,"�ÿ������Ч����"))
		{
			return false;
		}
 
		if(!checkAmount(form_1.amount, 1, "���׽��"))
		{
			return false;
		}
 
		if(( !CompareDate(form_1.budgetdtstart,form_1.adjustdate,"�ÿ������Ч���ڳ����ÿ�����")
		  ||!CompareDate(form_1.adjustdate,form_1.budgetdtend,"�ÿ������Ч���ڳ����ÿ�����")))
		{
			form_1.adjustdate.focus();
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