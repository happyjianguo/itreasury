<%--
/*
 * �������ƣ�v006.jsp
 * ����˵���������ÿ��������ҳ
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
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "";
	String strLine = "";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	OBBudgetAdjustInfo info = new OBBudgetAdjustInfo();
	if(request.getAttribute("rInfo")!=null)
	{
		info = (OBBudgetAdjustInfo)request.getAttribute("rInfo");
	}
	OBBudgetInfo budgetinfo = new OBBudgetInfo();
	if(request.getAttribute("rBudgetInfo")!=null)
	{
		budgetinfo = (OBBudgetInfo)request.getAttribute("rBudgetInfo");
	}
	String type="readonly";
	String adtype="true";
	String act = "";
	String check = null;
 if(request.getParameter("check")!=null)
	{
		check = (String)request.getParameter("check");
	}
	if(sessionMng.m_lUserID==info.getInputuser() && info.getStatus()==OBConstant.OBBudgetStatus.SAVE)//�޸�ɾ������
	{
		type="";
		adtype="false";
		lShowMenu = OBConstant.ShowMenu.NO;
		act = "modify";
		strTitle = "[�ÿ�����޸�]";
		strLine = "�ÿ����� - �ÿ�����޸�";
	}else if(sessionMng.m_lUserID==info.getCheckuser() && info.getStatus()==OBConstant.OBBudgetStatus.CHECK)//ȡ������
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act = "canclecheck";
		strTitle = "[�ÿ����ȡ������]";
		strLine = "�ÿ����� - �ÿ����ȡ������";
	}
	else if(check!=null  && check.equals("true") 
			&& sessionMng.m_lUserID!=info.getInputuser() && info.getStatus()==OBConstant.OBBudgetStatus.SAVE)//����
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.YES;
		act="check";
		strTitle = "[�ÿ��������]";
		strLine = "�ÿ����� - �ÿ��������";
	}
	else
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act="query";
		strTitle = "[�ÿ������ϸ]";
		strLine = "�ÿ����� - �ÿ������ϸ";
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
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v005.jsp">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/v005.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">			<!--��������-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="250" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"><%=strLine%></td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align=center>
	<!-- 	
     <table width="98%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr bgcolor="#456795" class="tableHeader">
          <td height="25" bgcolor="#456795" colspan="5" class=FormTitle><font size="3" color="#FFFFFF" ><%=strLine%></font></td>   
        </tr>
        -->
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <% 
			if(type.equals("no"))
              {
           		  long sPeriod=-1;
	              String strMagnifierNamePeriod = URLEncoder.encode("�ÿ�����");
	              String strFormNamePeriod = "form_1";
	              String strPrefixPeriod = "";
	              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetamount","budgetdtstart","budgetdtend","budgetid"};
	              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","amount","startdate","enddate","id"};
	              String[] strReturnNamesPeriod = {"budgetID"};
	              String[] strReturnFieldsPeriod = {"id"};
	              String strReturnInitValuesPeriod= budgetinfo.getSname();
	              if(strReturnInitValuesPeriod.equals("-1"))
	            	  strReturnInitValuesPeriod = new String("");
	              String[] strReturnValuesPeriod = {String.valueOf(info.getBudgetID())};
	              String[] strDisplayNamesPeriod = {URLEncoder.encode("�ÿ�����"),URLEncoder.encode("�ÿ��˻�"),URLEncoder.encode("�ÿ�ʱ������")};
	              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
	              int nIndexPeriod = 0;
	              String strMainPropertyPeriod = type;
	              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname)";
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
           }
              else
              {
              	%>
              		<td width="15%" class="MsoNormal" height=25>�ÿ����ƣ�</td>
         			<td width="25%"class="MsoNormal"><input class="box" type="text" name="budgetname"   readonly value=<%=budgetinfo.getSname()%>  ></td>
		  			<input type=hidden name=budgetID value=<%=info.getBudgetID()%>>
              	<%
              }
       
%>          
 		 
 		<input name="accountID"   type="hidden">
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
        <tr>
        <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal">�ÿ��˺ţ�</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" value=<%=NameRef.getBankAcctNameByAcctID(budgetinfo.getAccountID())%> type="text" readonly></td>
          <td  height="25" class="MsoNormal">�ÿ��</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="budgetamount" value=<%=DataFormat.formatDisabledAmount(budgetinfo.getAmount(),2)%> type="text" readonly></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ����䣺��</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" value=<%=budgetinfo.getStartdate().toString().substring(0,10)%> size="12" readonly >
          </td>
		  <td width="15%" height="25" class="MsoNormal">����</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  value=<%=budgetinfo.getEnddate().toString().substring(0,10)%> readonly size="12">
		  </td>
        </tr> 
         <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>�ÿ������Ч���ڣ�</td>
          <td width="25%" class="MsoNormal" >
            <%
          if(type.equalsIgnoreCase("no"))
          {  
          %>
          <fs_c:calendar 
			         	    name="adjustdate"
				          	value="" 
				          	properties="nextfield ='amount'" 
				          	size="12"/>
				          	 <script>
	          		$('#adjustdate').val('<%=info.getAdjustdate().toString().substring(0,10)%>');
	          	</script>
				<script>
	          		$('#adjustdate').attr('readonly','true');
	          	</script>
				          	<!-- 
          	<a href="javascript:show_calendar('form_1.adjustdate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>
			</a>
			 -->
          <%
          }else{
          %>
                    <input type="text" name="adjustdate" onfocus="nextfield ='amount';" size="12" readonly value=<%=info.getAdjustdate().toString().substring(0,10)%>   >
          <%} %>
			</td>
          <td  height="25" class="MsoNormal"><font color=red>*</font>�ÿ������</td>
          <td  height="25" class="MsoNormal"><%=sessionMng.m_strCurrencySymbol%>
           <%
          	if(act.equalsIgnoreCase("modify"))
          	{
          	%>
          		<script  language="JavaScript">
				createAmountCtrl("form_1","amount","<%=DataFormat.formatDisabledAmount(info.getAmount(),2)%>","note","","<%=sessionMng.m_lCurrencyID%>");
				</script>
          		<%
          	}
          	else 
          	{  
          		%>
          			<input type=text name=amount readonly value=<%=DataFormat.formatDisabledAmount(info.getAmount(),2)%> onfocus="nextfield ='note';"  >
          		<%
          	}
          %>
                   <%
	 
%>
			</td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ�˵����</td>
          <td width="25%" colspan="3"><input class="box" onfocus="nextfield =' ';"  type="text" name="note" size="50"  maxlength="50"   <%=type%> value=<%= info.getNote()!=null?info.getNote():""%>></td>  
        </tr> 
 
<input name="checkuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="checkdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
      </table>
      <br>

	  <!--��ʽ��̬��ʾ�տ����-->

      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
           <%
          	if(act.equals("modify"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" ��  �� " onClick="Javascript:modifyme();" onKeyDown="Javascript:modifyme();"> 
				</div>
	         	 </td>
	         	 <td width="9" height="17"></td>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" ɾ  �� " onClick="Javascript:delme();" onKeyDown="Javascript:delme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" ��  �� " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}else if(act.equals("canclecheck"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" ȡ �� �� �� " onClick="Javascript:canchekme();" onKeyDown="Javascript:canchekme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" ��  �� " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}
          	else if(act.equals("check"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" ��  �� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" ȡ  �� " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
				</div>
	          </td>
          	<%
          	}
          	else
          	{
          	%>
          		<td width="63">
	            <div align="right">
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" ��  �� " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}
          %>
        </tr>
      </table>
<%
		 System.out.println("banrey@@@@@@@@@@@77777777777");
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
		form_1.action = "../control/c005.jsp?action=check";
		if (validate() == true)
        {	  
        	 //alert(form_1.budgetid.value);
			/* ȷ���ύ */
			 if (!confirm("�Ƿ��ύ��"))
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
			form_1.budgetID.focus();
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
	 
		if (!InputValid(form_1.note, 0, "", 1, 0, 100,"�ÿ�˵��"))
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
      function closeme()
    {
    	window.close();
    }
    
    function modifyme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustmodify";
		if (validate() == true)
        {		 
			/* ȷ���ύ */
			if (!confirm("�Ƿ��޸ģ�"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
    
    function delme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustdelete&id=<%=info.getId()%>";
		 		 
			/* ȷ���ύ */
			if (!confirm("�Ƿ�ɾ����"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
 
    }
    
    function canchekme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustcancle&id=<%=info.getId()%>";
		 		 
			/* ȷ���ύ */
			if (!confirm("�Ƿ�ȡ�����ˣ�"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
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
