<%--
/*
 * �������ƣ�
 * ����˵�������ҳ��
 * �������ߣ�baihuili
 * ���ڣ�2006��09��15��
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"


%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[���л��]";
%>

<%
System.out.println("**************************Enter v002.jsp");
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    
	long lSourceType = 0;//ͷ��Ϣ��ʾ
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
	//��"�ҵĹ���"���ݵĿ��Ʊ���
	String strTempAction = "";
	if (request.getParameter("strTempAction") != null)
	{
		strTempAction = (String)request.getParameter("strTempAction");
		request.setAttribute("strTempAction", strTempAction);
	}

%>

<%
	/* ʵ������Ϣ�� */
	

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
        
     OBHtml.validateRequest(out,request,response);

		/* �������л�ȡ��Ϣ */
		//OBBankPayInfo  info=(OBBankPayInfo)request.getAttribute("info");
		
		OBFinanceInstrEJB ejb = new OBFinanceInstrEJB();
		
		OBBankPayInfo info = new OBBankPayInfo();
		String lId = request.getParameter("txtID");
		info = ejb.findByID(Long.parseLong(lId));
		System.out.println("**************************"+info);
		
      
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
	
	

	//SEFC����
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />

<form action="" method="post" name="form_1">
<input type="hidden" name="lID" value="<%= info.getId() %>">
     <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">���л��ȷ��</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        ���л�� ���ڸ��˺���ύ��<%=com.iss.itreasury.settlement.util.NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<br>
              <!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <br>
              �ñʽ����д������˸��ˣ�
			  <br>
              <br>
              <b>ָ�����Ϊ��<%= info.getId() %></b><br>
              <br>
            </p>
            </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>

	   <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> �������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"> <%=info.getName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
         <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> �տ����</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            ��ʽ��
          </td>
          <td width="430" height="25" class="MsoNormal">���л��</td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ�˺ţ�
          </td>
          <td width="430" height="25" class="MsoNormal"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
     
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ���ƣ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getSpayeeacctname()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
         <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
             ������CNAPS�ţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankCNAPSNo() == null?"":info.getBankCNAPSNo()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �������кţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankconnectnumber() == null?"":info.getBankconnectnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
                 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �����ţ�
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
		</table>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> ��������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">        
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>

          <td width="430" height="25" class="MsoNormal" id="ss"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <script>
          		var a = <%=info.getMamount()%>+"";
          		document.getElementById("ss").innerText = formatAmount1(a);
          </script>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�ύ���ڣ�</td>
          <td width="430" height="25" class="MsoNormal"><%=info.getDtexecute().toString().substring(0,10)%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�����;��</td>
          <td width="430" height="25" class="MsoNormal"><%= (info.getSnote()==null)?"":info.getSnote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      

      </table>
	  <br>

      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> �������봦������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal">
		<thead>     	        
        <tr>
          <td height="19" width="10%"  align="center" >
            <div align="center"><font size="2" >���к�</font></div>
          </td>
          
          <td height="19"  width="30%" align="center">
           �û�
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">��������</div>
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
       </thead>
        <tr valign="middle">
          <td width="10%" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td   width="30%" height="25">
            <div align="center"><%=NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td   width="30%" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>

 </table>
 <br/>

<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">��ʷ�������</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
 	  <table border="0" width="100%" cellspacing="0" cellpadding="0" align="" class=normal>
	  <TR>
		  <TD colspan="3">
			 <iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=info.getId()+ ""%>&&transType=<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
		  </TD>
	  </TR>
	  <tr>
		     <%
		     if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
		     {
				//�������������񣬲�����ʾ�������¼���
				if(info.getNstatus() != OBConstant.OBBudgetStatus.APPROVE){
						String strMagnifierNameRemark = "�������";
						String strFormNameRemark = "form_1";
						String strMainPropertyRemark = "";
						String strPrefixRemark = "";
						String[] strMainNamesRemark = {com.iss.inut.workflow.constants.Constants.WF_ADVISE};
						String[] strMainFieldsRemark = {"Description"};
						String strReturnInitValuesRemark="";
						String[] strReturnNamesRemark = {"txtsOptionID"};
						String[] strReturnFieldsRemark = {"id"};
						String[] strReturnValuesRemark = {""};
						String[] strDisplayNamesRemark = {"����������","�����������"};
						String[] strDisplayFieldsRemark = {"Code","Description"};
						int nIndexRemark = 1;
						String strSQLRemark = "select * from sys_approvalopinion where officeid="+sessionMng.m_lOfficeID+" and currencyid="+sessionMng.m_lCurrencyID+" and moduleid="+sessionMng.m_lModuleID+" and statusid="+Constant.RecordStatus.VALID;
						String strMatchValueRemark = "Description";
						//String[] strNextControlsRemark = {"strGeneratorCapacity","isShareHolder"};
						String strNextControlsRemark = "checkNextUser";
						String strTitleRemark = "�������";
						String strFirstTDRemark="align='center'";
						String strSecondTDRemark="colspan='2'";	
						Magnifier.showTextAreaCtrlForEbank(out,strMagnifierNameRemark,strFormNameRemark,strPrefixRemark,strMainNamesRemark,strMainFieldsRemark,strReturnNamesRemark,strReturnFieldsRemark,strReturnInitValuesRemark,strReturnValuesRemark,strDisplayNamesRemark,strDisplayFieldsRemark,nIndexRemark,strMainPropertyRemark,strSQLRemark,strMatchValueRemark,strNextControlsRemark,strTitleRemark,strFirstTDRemark,strSecondTDRemark);
				}
		    }		
		%>   
		</tr> 
		<tr>
		<td colspan=3><br></td>
		</tr>
 	</table>
 	<br/>		
   	<table width="100%"><tr><td align="right">
	<%
   	//����ȡ�������б����ʱ,��ʾȡ��������ť
   	
   		int operation = -1;
	String strTemp = request.getParameter("operation");	
	
	
	if(strTemp!=null && !strTemp.equals("")){
		operation = Integer.parseInt(strTemp);
	}
   	
   	if(operation==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
   		{
   	%>
		<input class="button1" name="ca" type="button" value=" ȡ������ " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">

	     <isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />

	<%	}
	else
		{
	%>
	     <isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />

	<%	}
	        if(strTempAction.equals("finished") || strTempAction.equals("cancelApproval"))
			{
			%>
			<input class=button1 name=add type=button value=" �� �� " onClick="javascript:history.back();" onKeyDown="javascript:history.back();"/>
			<%  }
			else
			{
			 %>
			<input class=button1 name=add type=button value=" �� �� " onClick="javascript:doReturn();" onKeyDown="javascript:doReturn();"/>
			<%} %>
	
	<input type="hidden" name="strSuccessPageURL" value="">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">	
			</td>
		</tr>
	</table>
			</td>
		</tr>
	</table>

	</form>
<!--presentation end-->

<script language="javascript">
//��ӡί�и���ƾ֤
function PrintConsignVoucher()
{
}


	/* �˵����ƴ����� */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frmzjhb.menu.value="hidden";
		<%  }   %>
	}
	/*���ش����� */
	function returnme()
	{	if (!confirm("�Ƿ񷵻أ�"))
			{
				return false;
			}
		frmzjhb.action="../view/v101.jsp";
		frmzjhb.submit();
	}

    function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}

	/* ȷ�ϴ����� */
	function checkme()
	{
		//showMenu();
		if (!confirm("�Ƿ񸴺ˣ�"))
			{
				return false;
			}
		frmzjhb.act.value="check";
		frmzjhb.action="../control/c101.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* �޸Ĵ����� */
	function updateme()
	{
		//showMenu();
		frmzjhb.action="../control/c002.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* ɾ�������� */
	function deleteme()
	{
		if (!confirm("�Ƿ�ɾ����"))
		{
			return false;
		}
		//showMenu();
		frmzjhb.action="../control/c003.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* ����ƥ�亯�� */
	function checkmatchme()
	{
		//showMenu();
		frmzjhb.action="../check/ck006-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}	

	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			frmzjhb.action="../check/C415.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* ǩ�ϴ����� */
	function signme()
	{
		//showMenu();
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* ȡ��ǩ�ϴ����� */
	function cancelsignme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			frmzjhb.action="../sign/s004-c.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* ��ӡ������ */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
	}

</script>

<script language="Javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//����������
	function doApproval()
	{
		var frm=form_1;
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("�������������");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	    frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
		if (confirm("�Ƿ�����?")) 
		{					
			frm.strSuccessPageURL.value='/approval/view/v033.jsp';
			frm.strFailPageURL.value='../view/c004_v.jsp';
			frm.action= "../control/c901.jsp";
			showSending();			
			frm.submit();
		}
	}	

//ȡ������
	function cancelApproval()
	{
		var frm=form_1;
	 	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
		if (confirm("�Ƿ�ȡ������?")) 
		{						
			frm.strSuccessPageURL.value='/approval/view/v036.jsp';
			frm.strFailPageURL.value='../view/c004_v.jsp';
			frm.action= "../control/c901.jsp";
			showSending();			
			frm.submit();
		}
	}	
    
</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>