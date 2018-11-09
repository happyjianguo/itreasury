<%--
/*
 * �������ƣ�c004-v.jsp
 * ����˵�����ʽ𻮲��ύ,�޸����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��06��
 */
--%>
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
				 com.iss.itreasury.safety.util.*,
				  com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				  com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				  com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.ExtSystemSettingBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = null;
%>

<%
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

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
	
	boolean blnNotBeFalsified = true;
	String temp = null;	
	
    //String strID = (String)request.getAttribute("lID");
    //long lID = -1;
    //if((strID != null) && (strID.length()>0)){
    //	lID = Long.parseLong(strID);
    //}
    
	//�Ƿ���Ҫ������ by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
   //modify by xwhe 2008-07-23
    String flagCommit ="";
	temp = (String)request.getParameter("flagCommit");
	if(temp!=null && temp.equals("success"))
	{
	 flagCommit = temp;
	}
    String lTransType = "";
    lTransType= (String)request.getAttribute("lTransType");
    if(lTransType==null)
       	{
       	 	lTransType = (String)request.getParameter("txtTransType");
       	}
    //�Ƿ�Ϊ����ָ���ѯ
    String lOperate = null;
    String operate="";
	lOperate = (String)request.getParameter("operate");
	if (lOperate != null)
	{
	    operate = lOperate;
	}
       
%>

<%
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = null;

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
        /* �û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }*/

		/* �������л�ȡ��Ϣ */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		long lID = financeInfo.getID();

		
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
		
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
	
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
	int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
    String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   	boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	//boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
	//boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //�Ƿ�ʹ���°�����֤����ǩ
	if(isUseCertification)
	{
		temp = (String)request.getParameter("blnNotBeFalsified");
		if(temp!=null&&temp.trim().length()>0)
		{
			blnNotBeFalsified = new Boolean(temp).booleanValue();
		}
	}
%>

<safety:resources />

<%
System.out.println("blnUseITrusCert"+blnUseITrusCert);
System.out.println("blnNotBeFalsified"+blnNotBeFalsified);
if(isUseCertification &&!blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt(); 
	//-->
	</script>
<%
	}
 %>
 
 
<form name="frmzjhb" method="post">
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title ><span class="txt_til2">��ʸ���ȷ��</span></td>
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

<table border="0" cellspacing="0" cellpadding="0" >
     <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">������ƣ�</td>
          <%--
          <td width="430" height="25" class="MsoNormal"> <%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":sessionMng.m_strClientName%></td>
          --%>
          <td width="430" height="25" class="MsoNormal"> <%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
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
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �տ����</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
<table width=100% border="0" cellspacing="0" cellpadding="0"class=normal id="table1">
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
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            �տ�˺ţ�
          </td>
          <td width="430" height="25" class="MsoNormal"><%=(strPayeeAccountNo==null)?"":NameRef.getNoLineAccountNo(strPayeeAccountNo)%></td>
          <td width="1" height="25" class="MsoNormal"></td>
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
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ��������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��</td>
          <td width="20" height="25" class="MsoNormal">
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
       
            <td width="130" height="25" class="MsoNormal" colspan="2" >�ⲿϵͳָ�����</td>
			<td width="430" height="25" class="MsoNormal"><%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %></td> 	
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
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
<br/>
      <table   border="0" cellspacing="0" cellpadding="0">
      <tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> �������봦������</td>
			<td width="17"><a class=lab_title3></td>
	  </tr>
    </table>
      <table width=100% border="0" cellpadding="0" cellspacing="1" class=list_table id="table1">
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
          
          <td width="30%" height="25" >
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
          
          <td width="30%" height="25" align="center">
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
<br/>
<% if(isNeedApproval.equals("true")){ %>
<table  border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ��ʷ�������</td>
	<td width="17"><a class=lab_title3></td>
</tr>

</table>
	 
	  <!-- ��ʷ������� -->
	  <TR>
		  <TD colspan="3">
			<%--  <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&lID=<%=lID %>&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<%String tempTransNo = financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";
			if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
          		 { %>
					<fs:HistoryOpinionFrame
					  strTransNo='<%=tempTransNo%>'
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
					  strTransNo='<%=tempTransNo%>'
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
		  </TD>
	  </TR>
	  <!-- ��ʷ������� -->
<%} %>
<br />

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
		if(financeInfo.getStatus()==OBConstant.SettInstrStatus.REFUSE)
		{
			if(financeInfo.getConfirmUserID()==sessionMng.m_lUserID&&biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
		%>

		<input class="button1" name=add type=button value=" �����ύ " onClick="Javascript:submitagain();" onKeyDown="Javascript:submitagain();">&nbsp;&nbsp;
		<% 
				}
		}
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� 
        	if(biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
        		{
        %>
        <!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
        <!--��ӡί�и���ƾ֤-->
        <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();"> 
        <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"--> 
        	
		<input class="button1" name=add type=button value=" �� �� " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;
		<input class="button1" name=add type=button value=" ɾ �� " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
        <% 
        	}
        	if ( lSourceType != 1 &&  lShowMenu != OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class="button1" name=add type=button value=" �� �� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
        <%}%>
        <% }
        if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// δ����ȷ�ϡ���¼�ˣ�ȷ���� 
			if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {
		%>
			<input class=button1 name=add type=button value=" �� �� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			
		<%}
		}
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE ) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1)&&!search.equals("1") ) {// ��ȷ�ϡ���¼��<>ȷ���� %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"--> 
		<input class="button1" name=add type=button value=" �� �� " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
        <% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ) {// ��ȷ�ϡ���¼��<>ȷ���� %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"--> 
		<input class="button1" name=add type=button value=" �� �� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		        <%
		        if(signForCheck>0){
		        %>
		         <input class=button1 name=add type=button value=" �� �� " onClick="javascript:returnCheck()" onKeyDown="javascript:returnCheck()">&nbsp;&nbsp;
		        <% 
		        }else{
		        %>	 
		        <input class=button1 name=add type=button value=" �� �� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
		        <% 
		        }
		   	}
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&&!search.equals("1") && !isSign.equals("1")) {// �Ѹ��ˡ���¼�ˣ������� %>
        <!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onClick="Javascript:cancelcheckme();"-->
		<input class="button1" name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
        <%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&&!search.equals("1") && isSign.equals("1")){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>
        <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"--> 
		<input class="button1" name=add type=button value=" ǩ �� " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;
        <%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&&!search.equals("1") && isSign.equals("1")){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>
        <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"--> 
		<input class="button1" name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;
        <%
	       }
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus(); 
        %>
        <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"--> 
		<%
		//add by zcwang 2007-10-19  ����ҵ���Ƿ���Ҫ��ӡ����ʾ
		if(Config.getBoolean(ConfigConstant.EBANK_ISPRINT,false))
		{
		 %>
		<input type="Button" class="button1" value=" �� ӡ " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		<%if(a!=null && !a.contains(strStatus))
               {
		%>
			   disabled
		<%
			   }
		%>
		>
		<% 
		}
		//
		%>	
		
        <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
        <% if ( lShowMenu == OBConstant.ShowMenu.NO) 
        	{
        		
        		if(operate.equals("query"))
        		{
        	%>
        			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
					<input type="Button" class="button1" value=" �� �� " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        	<%
        		}
        		else
        		{
        	%>
        			<input type="Button" class="button1" value=" �� �� " width="46" height="18"   onclick="window.close();window.opener.doQuery();"> &nbsp;&nbsp;
        	<% 		
        		}
        		
        	}
        	%>
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
	  
	  <!-- ����ָ���ѯ��־�ֶ� -->
	  <input type="hidden" name="newOpen" value="">
	  <!-- �����ύ��־�ֶ� -->
	  <input type="hidden" name="submitAgain" value="">

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
//��ӡί�и���ƾ֤
function PrintConsignVoucher()
{
	window.open("../../common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
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
	{
		frmzjhb.lInstructionID.value = "";
		frmzjhb.action="../captransfer/c001-c.jsp";
	    showSending();
		frmzjhb.submit();
	}
	/*���ش����� */
	function returnCheck()
	{
		frmzjhb.lInstructionID.value = "";
		frmzjhb.action="../check/ck006-c.jsp?SelectType=<%=OBConstant.QueryInstrType.CAPTRANSFER%>";
	    showSending();
		frmzjhb.submit();
	}
	/* ȷ�ϴ����� */
	function confirmme()
	{
		//showMenu();
		frmzjhb.action="../captransfer/C13.jsp";
		frmzjhb.submit();
	}
	/* �޸Ĵ����� */
	function updateme()
	{
		var msg;
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�������ѱ��۸ģ��Ƿ��޸ģ�"
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
			}
		%>
		frmzjhb.newOpen.value = "newOpen";
		frmzjhb.action="../financeinstr/control/fi_c006.jsp";
		showSending();
		frmzjhb.submit();
	}
		function submitagain()
	{
		//showMenu();
		frmzjhb.submitAgain.value = "submitAgain";
		frmzjhb.newOpen.value = "newOpen";
		frmzjhb.action="../financeinstr/control/fi_c006.jsp";
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
		frmzjhb.action="../captransfer/c005-c.jsp?flag=delete";
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
	/* ���˴����� */
	function checkme()
	{
		//showMenu();
		
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��	
		<%}%>
		
		//-------------------���ָ����֤---��ʼ----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //ָ����֤
		$.ajax(
		{
			  type:'post',
			  url:"<%=strContext%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
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
		
		frmzjhb.action="../check/C415.jsp?flag=checked";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		var msg = "�Ƿ�ȡ�����ˣ�"
		if(!confirm(msg))
		{
			return false;
		}				
		<%

			if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��	
		<%}%>
		frmzjhb.action="../check/C415.jsp";
		frmzjhb.txtisCheck.value = "0";
		showSending();
		frmzjhb.submit();
		
	}
	/* ǩ�ϴ����� */
	function signme()
	{
		//showMenu();
		
		//-------------------���ָ����֤---��ʼ----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //ָ����֤
		$.ajax(
		{
			  type:'post',
			  url:"<%=strContext%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(frmzjhb,1);
					if($("#Ver").val()!=""){
				  	    signme();// �ٴ��ύ
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("ָ����֤���������²ɼ�");	
					$("#Ver").val("");
				  	getFingerPrint(frmzjhb,1);//���ؿؼ�
					if($("#Ver").val()!=""){
				  	    signme();// �ٴ��ύ
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
		
		var msg = "�Ƿ�ǩ�ϣ�"
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�������ѱ��۸ģ��Ƿ�ǩ�ϣ�"
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
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>
		//-------------------���ָ����֤---����----------------
		
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* ȡ��ǩ�ϴ����� */
	function cancelsignme()
	{
		var msg = "�Ƿ�ȡ��ǩ�ϣ�";
		if(!confirm(msg))
		{
			return false;
		}				
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "0";
		showSending();
		frmzjhb.submit();
		
	}
	/* ��ӡ������ */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
	}
$(document).ready(function() {
 	$(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    $("#flexlist").flexigridenc({
		colModel : [
			{display: '���к�',  name : 'ncurrencyid', width : 300, sortable : false, align: 'center'},
			{display: '�û�',  name : 'payeracctno', width : 300, sortable : false, align: 'center'},
			{display: '��������',  name : 'payername', width : 340, sortable : false, align: 'center'},
			{display: 'ʱ�������',  name : 'ntranstype', width : 350, sortable : false, align: 'center'}
		],//�в���
		//title:'�������봦������',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		usepager : false,
		userFunc : getFormData,
		height : 100,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmbizapp","flexlist");
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
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/SignValidate.inc" %>