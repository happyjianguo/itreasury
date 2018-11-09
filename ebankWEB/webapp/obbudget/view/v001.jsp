<%--
/*
 * �������ƣ�v001.jsp
 * ����˵��������Ԥ������ҳ
 * �������ߣ�banreyliu
 * ������ڣ�2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.*" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ÿ�����]";
%>
<%
    OBHtml.validateRequest(out,request,response);
    
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	   // transTypeID = Constant.ModuleType.BUDGET
	}
	
	if((String)request.getParameter("ID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("ID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("�����ɹ�");
			</script>
	 <%
		}else if(Long.parseLong((String)request.getParameter("ID")) == Constant.FALSE){
	 %>
	 		<script language="JavaScript">
			alert("���˻��´����ص����ÿ����䣬������ѡ��");
			</script>
	 <%		
		}
	}
	long period = -1;
	SettPeriodSetBiz biz = new SettPeriodSetBiz();
	PeriodSetInfo info = new PeriodSetInfo();
	info.setCurrencyId(sessionMng.m_lCurrencyID);
	info.setOfficeId(sessionMng.m_lOfficeID);
	info.setStatusId(1);
	info = biz.findByCondition(info);
	if(info.getId()>-1){
		period = info.getPeriod();
	}
	Timestamp endDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
	long endTime = endDate.getTime()+period*24*3600*1000-1;
	endDate.setTime(endTime);
	String strEndDate = DataFormat.formatDate(endDate,1);
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
<input type="hidden" name="strSuccessPageURL" value="../view/v009.jsp">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/v001.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">			<!--��������-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
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
          <td width="15%" class="MsoNormal" height=25>�ÿλ��</td>
          <td width="25%"><input readonly class="box"   type="text" name="txtClientName" value=<%=sessionMng.m_strClientName%> size="30"></td>
		  <td width="15%" height="25" class="MsoNormal"><font color=red>*</font>�ÿ����ƣ�</td>
		  <td width="25%" height="25" class="MsoNormal"><input class="box"  type="text" name="sname"  onfocus="nextfield ='budgetaccount';" size="20" maxlength="15"></td>
        </tr> 
        
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
            long sPeriod=-1;
              String strMagnifierNamePeriod = URLEncoder.encode("�ÿ��˻�");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetaccount"};
              String[] strMainFieldsPeriod= {"accountno"};
              String[] strReturnNamesPeriod = {"accountID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {"-1"};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("�˻�����"),URLEncoder.encode("�˺�")};
              String[] strDisplayFieldsPeriod = {"name","accountno"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getBudgetPayerAccountNoSQL(form_1.budgetaccount.value,"+sessionMng.m_lUserID+","+sessionMng.m_lClientID+","+sessionMng.m_lCurrencyID+",1,"+sessionMng.m_lOfficeID+")";
              String strMatchValuePeriod = "accountno";
              String strNextControlsPeriod = "startdate";
              String strTitlePeriod = "<font color=red>*</font>�ÿ��˻�";
              String strFirstTDPeriod = "";
              String strSecondTDPeriod= "";

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
 
          <td  height="25"></td>
          <td  height="25"></td>
          <td  height="25"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>�ÿ����䣺��</td>
          <td width="25%">
         	 <fs_c:calendar 
	         	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="12"/>
		      <script>
	          		$('#startdate').val('<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>');
	          </script>
          </td>
		  <td width="15%" height="25" class="MsoNormal">����</td>
		  <td width="25%" height="25" class="MsoNormal">
			 <fs_c:calendar 
	         	    name="enddate"
		          	value="" 
		          	properties="nextfield ='note'" 
		          	size="12"/>
		      <script>
	          		$('#enddate').val('<%=strEndDate%>');
	          </script>
		  </td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>�ÿ�˵����</td>
          <td width="25%" colspan="3"><input class="box"   type="text" name="note" size="50" onfocus="nextfield ='';" maxlength="50"></td>
		  
        </tr> 
        <tr>
        <td> </td>

</TR>
		<tr>
          <td colspan="7"> </td>
          
            <td align="right" >
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" ��һ�� " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</td>
          <td >&nbsp;</td>
        </tr>
        <tr><td >&nbsp;</td></tr>
      </table>
      <br>
       
  <input name="sysdate" type="hidden" value=<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>>
	  <!--��ʽ��̬��ʾ�տ����-->


<%
		
%>
	 <!-- ˢ������ -->
<input type="hidden" name="clickCount" value="0">
<!-- ˢ������ --> 
</form>
 
<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(form_1.sname);
	//setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">
    //����������ĺ�����ͨ��  
   function  DateDiff(sDate1,  sDate2){    //sDate1��sDate2��2006-12-18��ʽ  
       var  aDate,  oDate1,  oDate2,  iDays  
       aDate  =  sDate1.split("-")  
       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //ת��Ϊ12-18-2006��ʽ  
       aDate  =  sDate2.split("-")  
       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //�����ĺ�����ת��Ϊ����  
       return  iDays  
   } 

 /* �޸��ύ������ */
    function addme()
    {  	
    	if(<%=period%> == -1){
    		alert("�ÿ�����δ���ã���ҵ�񻹲����ã�����ϵϵͳ����Ա");
    		return false;
    	}
    	var periodDate = DateDiff(form_1.startdate.value,form_1.enddate.value)+1;	 
		form_1.action = "../control/c003.jsp?period="+periodDate;
		if (validate() == true)
        { 
        	if((!CompareDateString(form_1.sysdate.value,form_1.startdate.value)))
        	{
        		alert("��ʼ����С�ڵ�ǰ���ڣ�������ѡ��");
        		return false;
        	}else if(periodDate > <%=period%>){
        		alert("ѡ������ڴ������õ�������<%=period%>�죬������ѡ��");
        		return false;
        	} 
			form_1.startdate.value = formatedate(form_1.startdate.value);
			form_1.enddate.value = formatedate(form_1.enddate.value);
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
				form_1.action="../view/v001.jsp";
				form_1.submit();
			}
		 
    }
function validate()
    {
       /**
        * ��������������ϣ�return ture
        * ���У�����return false
        **/
        
		if (form_1.sname.value == "")
		{
			alert("�ÿ����Ʋ���Ϊ��");
			form_1.sname.focus();
			return false;
		}
		if (form_1.accountID.value < 0)
		{
			alert("�ÿ��˺���ӷŴ���ѡ��");
			form_1.accountID.focus();
			return false;
		}
		/* �������Ϸǿ�У�� */
		/* Ԥ��˵�� */
		if (!InputValid(form_1.note, 0, "", 1, 0, 100,"�ÿ�˵��"))
		{
			return false;
		}
		if(!CompareDate(form_1.startdate,form_1.enddate,"�ÿ�������ֹʱ��Ӧ��������ʼʱ��"))
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
