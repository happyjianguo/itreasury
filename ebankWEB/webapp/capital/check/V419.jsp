<%--
/*
 * �������ƣ�V419.jsp
 * ����˵����ȡ������-���� ��ѯ��ʾҳ�棨�°���Ŀ������
 * �������ߣ�wangzhen
 * ������ڣ�2011��04��07��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->


<%
	//�������
	String strTitle = null;
%>

<%	  
	/* �û���¼�����Ȩ��У�� */
	
	String strStartDate = null;//��һ��ҳ�洫���Ŀ�ʼ����
	
	String strEndDate = null;//�ϸ�ҳ�洫���Ľ�������
	
	
	
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
		
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>

<safety:resources />

<%--���ǲ�ѯ���--%>
<form name="form1" method="post">

<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="sign" type="hidden" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">ȡ������-����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>

</table>
<br/>

      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0" class = normal>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29"></td>
          <td width="110" height="25">
            <p><span class="MsoNormal">&nbsp;&nbsp;�������ͣ�</span></p>
          </td>
          <td width="20" height="25" class="MsoNormal">&nbsp;</td>
          <td width="430" height="25" class="MsoNormal">
<%
		OBHtmlCom.showQueryCheckTypeListControl2(out,"SelectType",(rsForm == null)?-1:rsForm.getTransType()," onfocus=\"nextfield ='txtConfirmA';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,true);
%>       
		<input type=hidden name="SelectStatus"  value="<%=  OBConstant.SettInstrStatus.CHECK %>">     
          </td>
          <td width="1" height="25"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;�ύ���ڣ�</span></p>
          </td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��</div>
          </td>
          <td width="430" height="25" class="MsoNormal">
          	<%  Timestamp ts=Env.getSystemDateTime(); %>
          	<fs_c:calendar 
			          	    name="txtConfirmA"
				          	value="" 
				          	properties="nextfield ='txtConfirmB'" 
				          	size="12"/>
			  	  <script>
	          		$('#txtConfirmA').val('<%=request.getAttribute("txtConfirmA")!=null?request.getAttribute("txtConfirmA"):ts.toString().substring(0,10)%>');
	          	</script>
			<span class="MsoNormal">&nbsp;&nbsp;&nbsp;&nbsp;��
            </span>
            <fs_c:calendar 
			          	    name="txtConfirmB"
				          	value="" 
				          	properties="nextfield ='txtMinAmount'" 
				          	size="12"/>
				 <script>
	          		$('#txtConfirmB').val('<%=request.getAttribute("txtConfirmB")!=null?request.getAttribute("txtConfirmB"):ts.toString().substring(0,10)%>');
	          	</script>
			</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="70" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;��</span></p>
          </td>
          <td width="60" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��Сֵ<%= sessionMng.m_strCurrencySymbol%></div>
          </td>
          <td width="440" height="25" class="MsoNormal">
	        <script  language="JavaScript">
				createAmountCtrl('form1','txtMinAmount','','txtMaxAmount','',1);
			</script>
        <span class="MsoNormal">&nbsp;&nbsp;&nbsp;&nbsp; ���ֵ<%= sessionMng.m_strCurrencySymbol%></span>
        <script  language="JavaScript">
				createAmountCtrl('form1','txtMaxAmount','','txtExecuteA','',1);
			</script>
          </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="5" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="29" class="MsoNormal"></td>
          <td width="110" height="25" class="MsoNormal">
            <p><span class="MsoNormal">&nbsp;&nbsp;ִ�����ڣ�</span></p>
          </td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right" class="MsoNormal">��</div>
          </td>
          <td width="430" height="25" class="MsoNormal">
          <%  Timestamp tss=Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID); %>
          		<fs_c:calendar 
		          	    name="txtExecuteA"
			          	value="" 
			          	properties="nextfield ='txtExecuteB'" 
			          	size="12"/>
			    <script>
	          		$('#txtExecuteA').val('<%=request.getAttribute("txtExecuteA")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	          	</script>
             <span class="graytext">&nbsp;&nbsp;&nbsp;&nbsp;��
            </span>
            	<fs_c:calendar 
		          	    name="txtExecuteB"
			          	value="" 
			          	properties="nextfield =''" 
			          	size="12"/>
			    <script>
	          		$('#txtExecuteB').val('<%=request.getAttribute("txtExecuteB")!=null?rsForm.getStartExe():tss.toString().substring(0,10)%>');
	          	</script>
     	  </td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr>
          <td colspan="7"></td> 	
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

      <table width="80%" border="1" align="" cellpadding="0" cellspacing="0" class=normal id="table1">
      <thead>
        <tr>
          <td width="3%"   height="18" rowspan="2"  >
            <p align="center">
            <%
            if (rs != null){
            	%>
            	<input type="checkbox" name="all" value="" onclick="checkAll(this);">
            	<%
            }else{
            	%>
            	<input type="checkbox" name="all" value="" disabled="disabled">
            	<%
            }
            %>
            </p>
          </td>
          
          <td width="11%"   valign="middle" height="18" rowspan="2"  >
            <p align="center">ָ�����</p>
          </td>
          
          <td width="10%"   valign="middle" height="18" rowspan="2"  >
            <p align="center">��������</p>
          </td>
          
          <td width="11%" height="28" valign="middle" rowspan="2"   >
            <div align="center">�˺�</div>
          </td>
          
          <td width="5%" height="28" valign="middle" rowspan="2" >��/��</td>
          
          <td width="10%" height="28" valign="middle" rowspan="2" >
            <div align="center">���</div>
          </td>
          
          <td width="16%"  height="18" valign="middle" colspan="2"  >
            <div align="center">�Է�����</div>
          </td>
          <td width="9%"  height="18" valign="middle" rowspan="2" >
            <div align="center">ִ������</div>
          </td>
          <td width="8%" height="18" valign="middle" rowspan="2" >
            <div align="center">�����;</div>
          </td>
        </tr>
        
        <tr>
          <td   height="18" valign="middle"   width="8%">
            <div align="center">����</div>
          </td>
       
          <td   height="18" valign="middle"   width="8%">
            <div align="center">�˺�</div>
          </td>
        </tr>
       </thead>

<%
      	int iCount = 0;//������
      	String strDataLast = "";//ǰһ��ָ��
      	String strData = "";//��ǰָ��
      	String strNote = "";
	  	//ѭ����������ʾ����
      	while ((rs != null) && rs.hasNext())
      	{
            FinanceInfo info=(FinanceInfo)rs.next();//�ʽ������Ϣ����
            iCount++;
            
  		  	long billstatusid = info.getNDepositBillStatusId();
		  	System.out.println("billstatusid��ֵ�ǣ�"+billstatusid);
		  	strData = DataFormat.getDateString(info.getConfirmDate());
		  	strNote = info.getNote()== null?"":info.getNote().trim(); //add by zhouxiang
            if (iCount == 1)
            {
                  strDataLast = strData;
%>
        <tr valign="middle">
          <td width="25" align="left"  bgcolor="#DFDFDF" height="20"></td>
          
          <td colspan="17" align="left"  bgcolor="#DFDFDF"   height="20">
            <p>�ύ����<span class="graytext">��<%=(DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"" %></span></p>
          </td>
        </tr>
<%
            }
            if (!strDataLast.equals(strData))
            {
%>
        <tr  valign="middle">
          <td width="25" align="left" bgcolor="#DFDFDF"   height="20"></td>
          
          <td colspan="17" align="left"  bgcolor="#DFDFDF"    height="20">
            <p>�ύ����<span class="graytext">��<%=(DataFormat.getDateString(info.getConfirmDate())!=null)?DataFormat.getDateString(info.getConfirmDate()):"" %></span></p>
          </td>
        </tr>
<%
                  strDataLast = strData;
            }
%>
		
        <tr>
          <td  valign="center" align="left" >
            <div align="center">
              <input type="checkbox" name="txtCheckbox" value="<%= iCount %>" onclick="isCheckedAll();">

            </div>
          </td>
          
          <td  valign="center" align="left" >          
   			<input type=hidden name="billstatusidList"  value="<%=billstatusid %>">
   			<input type="hidden" name="<%=info.getID()+"_dtmodify" %>"  value="<%=info.getDtModify()==null?"":info.getDtModify()+"" %>"> 
            <div align="center"><u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name;form3.txtTransType.value = this.id; doSee();" id="<%= info.getTransType()%>" name="<%= info.getID() %>" ><%= info.getID()%></u></div>
           <input type="text" class="box" name="txtID" size="24" value="<%= info.getID() %>" style="display:none">
           <input type="text" class="box" name="txtTransType" size="24" value="<%= info.getTransType() %>" style="display:none">
		  </td>
          
          <td  valign="center" align="center" nowrap="nowrap">
            <%=OBConstant.SettInstrType.getName(info.getTransType()) %>
          </td>
          
          <td align="center"   valign="center"><%= NameRef.getNoLineAccountNo(info.getPayerAcctNo())%></td>
          
          <td  align="center"    valign="center"><%out.print("��"); %></td>
          
          <td  align="right"   valign="center">
            <%= sessionMng.m_strCurrencySymbol%><%= info.getFormatAmount() %>
          </td>
          
          <td     valign="center"><%= DataFormat.formatString(info.getPayeeName())%></td>
          
          <td  align="center"  valign="center"><%= DataFormat.formatString(NameRef.getNoLineAccountNo(info.getPayeeAcctNo()))%></td>
         
          <td    valign="center">
            <div align="center"><span  ><%= info.getFormatExecuteDate() %></span></div>
          </td>
          
		<td nowrap valign="center" title ="<%=strNote%>" >
			<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
		</td>
				
        </tr>
<%
		}
		if (iCount == 0)//��ʾû��¼����ʾһ����
		{
%>
        <tr>
          <td width="25" height="14" valign="top" align="left"></td>
          
          <td width="48" valign="top" align="left"></td>
          
          <td width="48" valign="top" align="left"></td>
          
          <td  width="75"   valign="top"></td>
          
          <td  width="30"   valign="top"></td>
          
          <td  width="72"   valign="top"></td>
          
          <td   width="132" valign="top"></td>
          
          <td   width="75" valign="top"></td>
          
          <td   width="51" valign="top"></td>

          <td   width="51" valign="top"></td>
        </tr>
<%	
		}
%>
       
      </table>

      <br>
	<%  if (rs != null)
	    {
		%>
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <div align="right">
			<input type=hidden name="txtisCheck"  value="0"><!--��ʾҪ��ȡ�����˵Ĳ�����1��ʾ���� 0 ��ʾȡ������-->
			<!--img src="" name="Check1"   border="0" style="cursor:hand" onClick="javascript:doCheck();"-->
			<input type="button" name="Check1" class="button1" onClick="javascript:doCheck();">&nbsp;&nbsp;</div>
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
	  <%}%>
</form>
<%--�鿴������ύForm--%>
<form name="form3" method="post" sytle="display:none">
   <input type="text" class="box" name="txtID" size="24" value="" style="display:none">
   <input type="text" class="box" name="txtTransType" size="24" value="" style="display:none">
   <input type=hidden name="txtisCheck"  value="0"><!--��ʾҪ��ȡ�����˵Ĳ�����1��ʾ���� 0 ��ʾȡ������-->
   <input type="hidden" name="SelectType" value="<%= lTransType %>">

</form>
 <script language="javascript">
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
 function doCheck()/*����---ȡ������*/
 {
       var isCheck = false;
       for(i=0; i<document.form1.elements.length; i++)
       {
             if(document.form1.elements[i].type=="checkbox")
             {
                   if (document.form1.elements[i].checked == true)
                   {
                          isCheck = true;
                          break;
                   }
              }
       }
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
	   if(true)
	   {
	       form1.sign.value = "fixd";
	       form1.action = "C415.jsp";
	       showSending(); 
		   form1.submit();
	   }
 }
 function doSee()
 {
        form3.action = "C414.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");	
        form3.target = "_formwin";
        form3.submit();
        form3.target = "";

 }
 function doQuery()
 {
       if (doCheckForm())
       {
             form1.sign.value = "fixd";
             form1.action="C412.jsp";
             showSending(); form1.submit();
       }
 }
 //window.name = "Check_Window";
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