<!--
/*
 * �������ƣ�check_v006.jsp
 * ����˵�����ʽ𻮲����ҳ��
 * �������ߣ�
 * ������ڣ�20010��10��12��
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*,
				 com.iss.itreasury.safety.util.*,
				  com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.ExtSystemSettingBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = null;
%>

<%
	/* ʵ�ֲ˵����� */

	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
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
	
	long lCheckType = -1;//�����ڸ���ƥ���
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    
	long signForCheck = -1;//���˱�ʶ
	String strCheckSign = request.getParameter("signForCheck");
    if ((strCheckSign != null) && (strCheckSign.length()>0))
	{
    	signForCheck = Long.parseLong(strCheckSign);
	}
    
	//�Ƿ���Ҫ������ 
	String isNeedApproval = request.getParameter("isNeedApproval");
   //modify by xwhe 2008-07-23
    String flagCommit ="";
    String temp = "";
	temp = (String)request.getParameter("flagCommit");
	if(temp!=null && temp.equals("success"))
	{
	 flagCommit = temp;
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
   	{
   	 	lTransType = (String)request.getParameter("txtTransType");
   	}
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = null;

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
		/* �������л�ȡ��Ϣ */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		
		String strSourceType = "";

		ExtSystemSettingInfo extSystemSettingInfo = new ExtSystemSettingInfo();
		ExtSystemSettingBiz extSystemSettingBiz = new ExtSystemSettingBiz();
		if(financeInfo.getSource()!=-1)
		{
			extSystemSettingInfo = extSystemSettingBiz.findExtSystemSettingByID(financeInfo.getSource());
			strSourceType = extSystemSettingInfo.getSName();
		}
		
		
		long l = financeInfo.getRemitType();
				
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
		//�տ�˻�ID
		long payeeAcctIDTemp = financeInfo.getPayeeAcctID();
		//�տ�˻�����
		String payeeAcctName = "";
		com.iss.itreasury.ebank.util.NameRef nf = new com.iss.itreasury.ebank.util.NameRef();
		if(payeeAcctIDTemp>0)
		{
			payeeAcctName = nf.getRecAccountNameByID(payeeAcctIDTemp);
		}
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		ClientAccountInfo accountInfo=null;	
		accountInfo=(ClientAccountInfo)request.getAttribute("accountInfo");		
			
		if (accountInfo==null) accountInfo=new ClientAccountInfo();
		
		String strPayerAccountNo = 	financeInfo.getPayerAcctNo();
		String strPayeeAccountNo = 	financeInfo.getPayeeAcctNo();
		//SEFC����
		
		String strTransNo = financeInfo.getID() + "";
		if(isNeedApproval == null) isNeedApproval = "";
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		

		//CFCA֤����ǩ
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
		//boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
    	String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);		
		if(isUseCertification)
		{
			temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}
		}
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<%if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){%>
<safety:resources />
<%}else if (Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA)){ %>
<safety:resources cabName="CFCACertKitAx.cab" cabVersion="3,0,0,20" cabClassId="A018D0EB-121B-4CD8-B712-78D09B207BBC"/>
<%
}
System.out.println("blnUseITrusCert"+blnUseITrusCert);
System.out.println("blnNotBeFalsified"+blnNotBeFalsified);
if(isUseCertification &&!blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt(); 
	//-->
	</script>
<%}  %>
<form name="frmzjhb" method="post">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">��ʸ���ȷ��</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    
<br/>
<% 
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) 
		{
%>

	  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        ��ʸ���-<%= financeInfo.getFormatRemitType() %>��<%
	        if(isNeedApproval.equals("true"))
	        {
	        	%>��������<%
	        	if(!OBFSWorkflowManager.isAutoCheck())
	        	{
	        		%>�ȴ������˸��ˣ�<%
	        	}
	        	else
	        	{
	        		%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        	}
	        }
	        else if(!OBFSWorkflowManager.isAutoCheck())
	        {
	        	%>�ڸ��˺���ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
	        else
	        {
	        	%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
        %>
        <br>
              <!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			    <%
			    if(isNeedApproval.equals("true")&& flagCommit.equals("success"))
			  	{
			  	%>
			  	    <br>�ñʽ����ѱ��滹δ�ύ��<br>
			  	<%
			  	}
			    else if(isNeedApproval.equals("true"))
			  	{
			  		%><br>�ñʽ����д�������������<br><%
			  	}else if(!OBFSWorkflowManager.isAutoCheck())
			  	{
			  		%><br>�ñʽ����д������˸��ˣ�<br><%
			 	}%>
              <br>
              <b>ָ�����Ϊ��<%= financeInfo.getID() %></b><br>
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
<%
		}
%>

<table  border="0" cellspacing="0" cellpadding="0" >
     <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2">&nbsp; �������</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
	  <table width=98% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="center">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">������ƣ�</td>
          <td width="430" height="25" class="MsoNormal"> <%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":sessionMng.m_strClientName%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<!--
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">�����˺ţ�</td>
          <td width="430" height="25" class="graytext"><%//= financeInfo.getPayerBankNo() %></td>
          <td width="5"></td>
        </tr>
		-->
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(strPayerAccountNo) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0">
      <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2">&nbsp; �տ����</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
<table width=98% border="0" cellspacing="0" cellpadding="0"class=normal id="table1" align="center">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            ��ʽ��
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatRemitType() %></td>
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
          <td width="430" height="25" class="MsoNormal"><%=(strPayeeAccountNo==null)?"":NameRef.getNoLineAccountNo(strPayeeAccountNo)%></td>
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
		   <%		  
		  if (financeInfo.getRemitType()==OBConstant.SettRemitType.OTHER)
		  {
					out.println(Env.getClientName());
			}
			else
			{
					   	out.println((financeInfo.getPayeeName()==null)?payeeAcctName:financeInfo.getPayeeName());
			} 
		   %></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        
        <% 
        if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
        {%>
        <tr id="payeePlace" class="MsoNormal">
        <td width="5" height="25" class="MsoNormal"></td>
        <td height="25" width="130" class="MsoNormal" align="left">����أ�</td>
        <td width="430" height="25"  class="MsoNormal">
         <%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>
            ʡ
         <%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>
        �У��أ�</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
         <tr id="payeeBankName">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">���������ƣ�</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
           <tr id="payeeBankCNAPSNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">������CNAPS�ţ�</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankCNAPSNO()==null)?"":financeInfo.getSPayeeBankCNAPSNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
           <tr id="payeeBankOrgNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">�����л����ţ�</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankOrgNO()==null)?"":financeInfo.getSPayeeBankOrgNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
           <tr id="payeeBankExchangeNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">���������кţ�</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankExchangeNO()==null)?"":financeInfo.getSPayeeBankExchangeNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
    
      <% }
        %>
  
		</table>
      <br>
	  <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp;&nbsp;��������</td>
	<td width="683"><a class=lab_title3></td>
</tr>
</table>
      <table width=98% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��</td>
          <td width="3%" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr>
            <td width="5" height="25" class="MsoNormal"></td>
            <!--modify by xwhe 2008-11-10-->
            <td width="130" height="25" class="MsoNormal" colspan="2" >��д���(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)��</td>
			<td width="430" height="25" class="MsoNormal"><%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%></td> 	
		  
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
         <tr>
            <td width="5" height="25" class="MsoNormal"></td>
            <!--modify by xwhe 2008-11-10-->
            <td width="130" height="25" class="MsoNormal" colspan="2" >������Դ</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getSource()==-1?"":strSourceType %>
			</td> 	
		  
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%
        	if(financeInfo.getSource()!=SETTConstant.ExtSystemSource.EBANK)
        	{
         %>
         <tr>
            <td width="5" height="25" class="MsoNormal"></td>
            <!--modify by xwhe 2008-11-10-->
            <td width="130" height="25" class="MsoNormal" colspan="2" >�ⲿϵͳָ�����</td>
			<td width="430" height="25" class="MsoNormal"><%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %></td> 	
		  
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%
        	}
         %>
        
        <!-- Boxu Add 2010-12-01 ����"����/���"��"�Ƿ�Ӽ�"��ʹ�������ѵ������ļ� -->
        <%
        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)
        	  && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY )
        	{
        %>
        	<tr>
				<td width="5" height="25" class="MsoNormal"></td>
	          	<td width="130" height="25" class="MsoNormal" colspan="2">�������/�ٶȣ�</td>
	          	<td width="430" height="25" class="MsoNormal">
	            	<%=Constant.remitAreaType.getName(financeInfo.getRemitArea()) %>&nbsp;
	            	<%=Constant.remitSpeedType.getName(financeInfo.getRemitSpeed()) %>
	          	</td>
	          	<td width="5" height="25" class="MsoNormal"></td>
        	</tr>
        <%
        	}
        %>
        
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">ִ���գ�</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�����;��</td>
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getNote()==null)?"":financeInfo.getNote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <% if (financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE){ %>
 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">ʧ��ԭ��</td>
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getReject() ==null)?"":financeInfo.getReject() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%}%>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // ��ȷ��,δ����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //�����
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//�Ѿܾ�   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//������		   
		{ 
%>
      <table   border="0" cellspacing="0" cellpadding="0">
      <tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">&nbsp; �������봦������</td>
			<td width="17"><a class=lab_title3></td>
	  </tr>
</table>
      <table width=98% border="0" cellpadding="0" cellspacing="1" class=list_table id="table1" align="center">
		<tr class="itemtitle">
          <td height="26" width="10%"  align="center" >
            ���к�
          </td>
          
          <td height="19" width="30%" align="center">
           �û�
          </td>
          
          <td  height="19"  width="30%" align="center">
            <div align="center">��������</div>
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="10%" align="center"  height="25">
            <div align="center">1</div>
          </td>
          
          <td width="30%" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>          
          <td  width="30%" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% 
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //�Ѿܾ�
        	{ 
%>
        <tr valign="middle">
          <td width="10%" align="center"  height="25">
            <div align="center" >2</div>
          </td>
          
          <td width="30%" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        
<% 
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//�Ѿܾ�
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="10%" align="center"  height="25">
            <div align="center" >3</div>
          </td>
         
          <td  width="30%" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td width="30%" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				} 
%>
<%
 			} 
%>
 </table>
<%
 		} 
%>
 <br>
<% if(isNeedApproval.equals("true")){ %>
<table  border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp;��ʷ�������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
			 <%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<%--  <%if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
          		 { %>
          		 <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
					<%
		        	}
		        	else if(financeInfo.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
		        	{
		        	 %>
		        	 <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
			  		<%
		        	 }
		        	  %>--%>
			 <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
			<%if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
          		 { %>
					<fs:HistoryOpinionFrame
					  strTransNo='<%=strtransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					<%
		        	}
		        	else if(financeInfo.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
		        	{
		        	 %>
		        	 <fs:HistoryOpinionFrame
					  strTransNo='<%=strtransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					 <%
		        	 }
		        	  %>
		        	  <%
	}
%>
<br />
<table border="0" width=100% align="" cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="100%" align="right"> 
        <%
        String search="";
		if(request.getAttribute("search")!=null){
		search=(String)request.getAttribute("search");
		}
		String isSign="";
		if(request.getParameter("sign")!=null){
		isSign=request.getParameter("sign");
		}
		%>

                
		<%	/* ���� */
		
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ){ // ��ȷ�ϡ���¼��<>ȷ���� %>
		<input class="button1" name=add type=button value=" �� �� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;		        
		  <% } %>
		<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
		<input type="Button" class="button1" value=" �� �� " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        <%}%>
      </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
<br/>

	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
	  <!-- add by mingfang ��c415.jsp�ж�ҵ��������-->
	  <input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">
	  	  
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">

 	  <!-- ǩ�����ֶ� -->
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">	  
	  
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  
	  
	
	  <!--start  ָ����֤���html -->
  	  <input name="Ver" id="Ver" type="hidden" value="">
      <!--end  ָ����֤���html -->
	  </td>
  </tr>

</table>
	  </form>
<!--presentation end-->

	<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	<!--  ָ�ƿؼ�-->
	<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
			 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
	<% } %>	

<script language="javascript">
	/* ���˴����� */
	function checkme()
	{
		//showMenu();
		//-------------------���ָ����֤---��ʼ----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //ָ����֤
		$.ajax(
		{
			  type:'post',
			  url:"<%=request.getContextPath()%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+$("#Ver").val(),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(frmzjhb,1);
					if($("#Ver").val()!=""){
				  	    checkme();// �ٴ��ύ
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("ָ����֤���������²ɼ�");	
					$("#Ver").val("");
				  	getFingerPrint(frmzjhb,1);//���ؿؼ�
					if($("#Ver").val()!=""){
				  	    checkme();// �ٴ��ύ
					}
					fpFlag = false;
				}
				else{
					if(result != null && result != "null" && result != "" ){
						alert(result);	
						$("#Ver").val("");
						fpFlag = false;
					}else{
						fpFlag = true;
					}
				}
			  }
		}
		);
		if(!fpFlag){return;}
		<%}%>
		//-------------------���ָ����֤---����----------------

		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�������ѱ��۸ģ��Ƿ񸴺ˣ�"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //����
				{
		%>
					msg = "�������ѱ��۸�!"
					alert(msg);
					return false;
		<%
				}
			}else
			{
		%>		
					msg = "�Ƿ񸴺ˣ�"
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>	
		

		<%
			if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��	
		<%}%>
		
		frmzjhb.action="../control/check_c006_1.jsp?flag=checked";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
		$("#Ver").val("");
		
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
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng,e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
