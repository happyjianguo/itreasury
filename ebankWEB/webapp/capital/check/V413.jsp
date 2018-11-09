<%--
/*
 * �������ƣ�V413.jsp
 * ����˵����ҵ�񸴺˲�ѯ��ʾҳ��
 * �������ߣ�����
 * ������ڣ�2003��09��23��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.safety.util.*,
                 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%
	//�������
	String strTitle = null;
%>

<%	  
	/* �û���¼�����Ȩ��У�� */
	
	String strStartDate = null;//��һ��ҳ�洫���Ŀ�ʼ����
	
	String strEndDate = null;//�ϸ�ҳ�洫���Ľ�������
	
	double txtMinAmount = 0.0;
	double txtMaxAmount = 0.0;
	
	try{ 
		
		//��ҳ��Ϣ
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
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
   		
		Iterator rs = (Iterator)request.getAttribute("return");
		
		//��ѯ��Ϣ����
		QueryCapForm rsForm = new QueryCapForm();
		if(request.getAttribute("FormValue") != null)
		{
       		rsForm = (QueryCapForm)request.getAttribute("FormValue");
        }
		long lTransType = -1;
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}

        	if(request.getAttribute("txtConfirmA")!=null)
		{
			
			strStartDate =(String)request.getAttribute("txtConfirmA");
			System.out.println("txtConfirmA============="+strStartDate);
		}
		
		
		if(request.getAttribute("txtConfirmB")!=null)
		{
			
			strEndDate = (String)request.getAttribute("txtConfirmB");
			System.out.println("txtConfirmB============="+strEndDate);
		}
		
		if(request.getParameter("txtMinAmount") != null && request.getParameter("txtMinAmount").trim().length() > 0) {
			txtMinAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMinAmount")));
       	}
		if(request.getParameter("txtMaxAmount") != null && request.getParameter("txtMaxAmount").trim().length() > 0) {
			txtMaxAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("txtMaxAmount")));
      	}
		 
 		String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
 		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
 		boolean blnNotBeFalsified = true;
		String[] nameArray = null;
		String[] valueArray = null;
		
%>
<script language="javascript" src="/webob/js/glass.js"></script>

<safety:resources />

<%--���ǲ�ѯ���--%>
<form name="form1" method="post">
<input type="hidden" name="lClientID" value="<%= sessionMng.m_lClientID %>">
<input type="hidden" name="lCurrencyID" value="<%= sessionMng.m_lCurrencyID %>">
<input type="hidden" name="lUserID" value="<%= sessionMng.m_lUserID %>">
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">

<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ȡ������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>

      <table width="100%" border="0" class=normal>
        <tr class="MsoNormal">
          <td colspan="7" height="1"></td>
        </tr>
        
        <tr class="MsoNormal"> 
          <td width="5" height="29"></td>
          <td width="110" height="25">
            <p><span class="MsoNormal">&nbsp;&nbsp;�������ͣ�</span></p>
          </td>
          <td width="50" height="25" class="MsoNormal">&nbsp;</td>
          <td width="160" height="25" class="MsoNormal">
<%
		//OBHtmlCom.showQueryTypeListControl1(out,"SelectType",(rsForm == null)?-1:rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" ",false);
		OBHtmlCom.showQueryCheckTypeListControl(out,"SelectType",(rsForm == null)?-1:rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,true);
%>       
		<input type=hidden name="SelectStatus"  value="<%=  OBConstant.SettInstrStatus.CHECK %>">     
        </td>
        <td colspan="3">
        </td>
      </tr>
		<tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;�ύ���ڣ�</span></p>
          </td>
          <td height="25" class="MsoNormal" width="100">
            <div align="right" >�ɣ�&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td  height="25" class="MsoNormal">
          	<%  Timestamp ts=Env.getSystemDateTime(); %>
          	<fs_c:calendar 
			          	    name="txtConfirmA"
				          	value="" 
				          	properties="nextfield ='txtConfirmB'" 
				          	size="18"/>
			<script>
		          		$('#txtConfirmA').val('<%=request.getAttribute("txtConfirmA")!=null?request.getAttribute("txtConfirmA"):ts.toString().substring(0,10)%>');
		    </script>
	    </td>
		<td  width="200" align="right"> <div align="right" class="MsoNormal">����&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
		<td>
            	<fs_c:calendar 
	          	    name="txtConfirmB"
		          	value="" 
		          	properties="nextfield ='txtMinAmount'" 
		          	size="18"/>
		       <script>
	          		$('#txtConfirmB').val('<%=request.getAttribute("txtConfirmB")!=null?request.getAttribute("txtConfirmB"):ts.toString().substring(0,10)%>');
	    		</script>
		 </td>
          <td height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;��</span></p>
          </td>
          <td height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��Сֵ��</div>
          </td> 
          <td width="" height="25" class="MsoNormal">
	        <fs:amount 
	       		form="form1"
       			name="txtMinAmount"
       			value="<%=txtMinAmount %>"
       			
       			nextFocus="txtMaxAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
  		</td>
   		<td>
	   		<div align="right" width="20" class="MsoNormal">���ֵ��</div>
     	</td>
     	<td>
        	<fs:amount 
	       		form="form1"
       			name="txtMaxAmount"
       			value="<%=txtMaxAmount %>"
       			
       			nextFocus="txtExecuteA"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
     	</td>
     	<td height="25" class="MsoNormal"></td>
       </tr>
     
        <tr class="MsoNormal">
          <td colspan="7" height="1" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;ִ�����ڣ�</span></p>
          </td>
          <td height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">�ɣ�&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td  height="25" class="MsoNormal">
          <%  Timestamp tss=Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); %>
          	<fs_c:calendar 
			          	    name="txtExecuteA"
				          	value="" 
				          	properties="nextfield ='txtExecuteB'" 
				          	size="18"/>
			 <script>
	          		$('#txtExecuteA').val('<%=request.getAttribute("txtExecuteA")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	   		 </script>
   		 </td>
   		 <td > <div align="right" class="MsoNormal">����&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
   		 <td>
            <fs_c:calendar 
			          	    name="txtExecuteB"
				          	value="" 
				          	properties="nextfield =''" 
				          	size="18"/>
			<script>
	          		$('#txtExecuteB').val('<%=request.getAttribute("txtExecuteB")!=null?rsForm.getEndExe():tss.toString().substring(0,10)%>');
	    	</script>
		  </td>
		  <td height="25" class="MsoNormal"></td>
        </tr>
        
        <tr>
          <td colspan="6"></td> 	
          <td>
            <div align="right">
			<!--img name="Query" src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doQuery();"-->
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="javascript:doQuery();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
        <tr height="5"><td></td></tr>
      </table>
      <br>
		<TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
				   <td width="1%">&nbsp;</td>
					<TD width="*%">
						<br><TABLE width="100%" id="flexlist"></TABLE><br>
					</TD>
					<td width="1%">&nbsp;</td>
				</tr>
			</TBODY>
		</TABLE>

      <br>
	
      <table width="100%" border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <div align="right">
			<input type=hidden name="txtisCheck"  value="0"><!--��ʾҪ��ȡ�����˵Ĳ�����1��ʾ���� 0 ��ʾȡ������-->
			<input type=hidden name="isBatchCheck"  value="1"><!--��ʾ��������-->
			<!--img src="" name="Check1"   border="0" style="cursor:hand" onClick="javascript:doCheck();"-->
			<input type="button" name="Check1" class="button1" align="right" onClick="javascript:doCheck();">&nbsp;&nbsp;</div>
		  	<script language="javascript"> 
                    if (form1.SelectStatus.value == "<%= OBConstant.SettInstrStatus.SAVE %>")
                    {
                          /*����*/
                          //form1.Check1.src = "/webob/graphics/button_fuhe.gif";
						  form1.Check1.value = " ���� ";
                          form1.txtisCheck.value= "1";
                    }else{
                          /*ȡ������*/
                          //form1.Check1.src = "/webob/graphics/button_QuXiaoFuHe.gif";
						  form1.Check1.value = " ȡ������ ";
                          form1.txtisCheck.value = "0";
                    }
            </script>
		  </td>
        </tr>
      </table>
	 </td>
  </tr>
</table>
</form>
<%--�鿴������ύForm--%>
<form name="form3" method="post" sytle="display:none">
   <input type="text" class="box" name="txtID" size="24" value="" style="display:none">
   <input type="text" class="box" name="txtTransType" size="24" value="" style="display:none">
   <input type=hidden name="txtisCheck"  value="0"><!--��ʾҪ��ȡ�����˵Ĳ�����1��ʾ���� 0 ��ʾȡ������-->
   <input type="hidden" name="SelectType" value="<%= lTransType %>">

</form>
 <script language="javascript">
 $(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    
	$("#flexlist").flexigridenc({
		colModel : [
			{elType : 'checkbox', elName : 'txtID', name : 'txtID', width : 50, sortable : true, align: 'center'},
			{display: 'ָ�����', name: 'sbatchno', elType : 'link', elName : 'batchno', methodName : 'doLink("?","?","?")', width: 120, sortable: false, align: 'center'},
			{display: '��������',  name : 'ncurrencyid', width : 120, sortable : false, align: 'center'},
			{display: '�˺�',  name : 'SPAYEEACCTNO', width : 120, sortable : false, align: 'center'},
			{display: '��/��',  name : 'payername', width : 100, sortable : false, align: 'center'},
			{display: '���',  name : 'ntranstype', width : 100, sortable : false, align: 'center'},
			{display: '�Է���������',  name : 'mamount', width : 100, sortable : false, align: 'center'},
			{display: '�Է������˺�',  name : 'payeeacctno', width : 100, sortable : false, align: 'center'},
			{display: 'ִ������',  name : 'payeename', width : 100, sortable : false, align: 'center'},
			{display: '�����;',  name : 'spayeeprov', width : 100, sortable : false, align: 'center'}
		],//�в���
		title:'ȡ������',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckInfo',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData,
		callback: 'toDisabled()'
	});
	
});

function getFormData() 
{
	return $.addFormData("form1","flexlist");
}

function doQuery()
{	
	if(doCheckForm()){
		$.gridReload("flexlist");
	}
}
function toDisabled(){
	$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
		if(n.value == '0'){
			n.disabled = true;
			n.style.display = "none";
		}
	});
	showAlarm();
}

 function doCheckForm()
 {
       var fTop,fLov;

	   //add by sun start 2003-02-19
		/* �ύ����У�� */
		var starSubmit = form1.txtConfirmA.value;
		var endSubmit = form1.txtConfirmB.value;
		if (starSubmit != "")
		{
			if(chkdate(starSubmit) == 0)
			{
				alert("��������ȷ�����뿪ʼ����");
				form1.txtConfirmA.focus();
				return false;
			}
		}
		if (endSubmit != "")
		{
			if(chkdate(endSubmit) == 0)
			{
				alert("��������ȷ�������������");
				form1.txtConfirmB.focus();
				return false;
			}
		}
		if ((starSubmit != "") && (endSubmit != ""))
		{	if (!CompareDate(form1.txtConfirmA, form1.txtConfirmA, "�ύ���ڣ���ʼ���ڲ��ܴ��ڽ�������"))
			{
				return false;
			}
		}
		/* ִ������У�� */
		var startExe = form1.txtExecuteA.value;
		var endExe = form1.txtExecuteB.value;
		if (startExe != "")
		{
			if(chkdate(startExe) == 0)
			{
				alert("��������ȷ��ִ�п�ʼ����");
				form1.txtExecuteA.focus();
				return false;
			}
		}
		if (endExe != "")
		{
			if(chkdate(endExe) == 0)
			{
				alert("��������ȷ��ִ�н�������");
				form1.txtExecuteB.focus();
				return false;
			}
		}
		if ((startExe != "") && (endExe != ""))
		{	if (!CompareDate(form1.txtExecuteA, form1.txtExecuteB, "ִ�����ڣ���ʼ���ڲ��ܴ��ڽ�������"))
			{
				return false;
			}
		}
		//add by sun end 2003-02-19

       /*У����*/
       if (!checkAmount(form1.txtMinAmount,0,"��� ��Сֵ"))
             return false;
       if (!checkAmount(form1.txtMaxAmount,0,"��� ���ֵ"))
             return false;

       fLov =  parseFloat(reverseFormatAmount1(form1.txtMinAmount.value));
       fTop = parseFloat(reverseFormatAmount1(form1.txtMaxAmount.value));
       if (fLov > fTop)
       {
             alert("��� ��Сֵ���ܴ������ֵ");
             return false;
       }
       return true;
 }
 var flexlist = "flexlist";
 function doCheck()/*����---ȡ������*/
 {
		var isCheck = false;
		$.each($("#" + flexlist + " input[type='checkbox'][name='txtID']"),function(i,n){
			if(n.checked){
				isCheck = true;
			} 
		});
	
       if (!isCheck)
       {
             alert("��ѡ���¼");
             return false;
       }
	   var checkOrUncheck ;
	   if( form1.txtisCheck.value == "1" )
	   {
	   		checkOrUncheck = "���ˣ��Ƿ������"
	   }
	   else
	   {
	   		checkOrUncheck = "�Ƿ�ȡ�����ˣ�"
	   }
	   
	   if(!confirm(checkOrUncheck))
	   {
	   		return false;
	   }
       form1.action = "C415.jsp";
       showSending(); 
	   form1.submit();
	   
 }
 function doLink(blnNotBeFalsified,id,name)
 {
        form3.txtID.value = name;
        form3.txtTransType.value = id;
        form3.action = "C414.jsp?menu=hidden&blnNotBeFalsified="+blnNotBeFalsified;
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");	
        form3.target = "_formwin";
        form3.submit();
        form3.target = "";
 }

 form1.txtMinAmount.value = "<%= rsForm.getFormatMinAmount() %>";
 form1.txtMaxAmount.value = "<%= rsForm.getFormatMaxAmount() %>";
 firstFocus(form1.SelectType);
 //setSubmitFunction("doQuery()");
 setFormName("form1");
 
 function checkAll(obj){
	var checkboxes = document.getElementsByName("txtCheckbox");
	if(checkboxes.length==0)
		return;
	for(var i=0;i<checkboxes.length;i++){
		checkboxes[i].checked=obj.checked;
	}
}

function isCheckedAll()
{
	var isCheck = true;
	for(var i=0;i<document.form1.txtCheckbox.length;i++)
	{
		if(document.form1.txtCheckbox[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.form1.all.checked = true;
	else
		document.form1.all.checked = false;
    if(document.form1.txtCheckbox.length == undefined){
		document.form1.all.checked = document.form1.txtCheckbox.checked;
	}		
}
 
 </script>


<%
   }
   catch(IException ie)
   {
         OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>