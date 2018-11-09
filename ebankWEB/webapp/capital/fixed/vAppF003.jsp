<!--
/*
 * �������ƣ�vAppF003.jsp
 * ����˵�������ڿ����ύ����ҳ��
 * �������ߣ�ypxu
 * ������ڣ�07-04-25
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				  com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[���ڿ���]";
%>

<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
    String type = "";
    String _type = request.getParameter("type");
    if ((type != null) && (type.length()>0))
	{
    	type = _type;
	}
	long lCheckType = -1;//�����ڸ���ƥ���
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
       	lTransType = (String)request.getParameter("txtTransType");
       	
    //ȡ�������б�ҳ���ȡ��������
	String operation = "-1";
	if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
	{
		operation =(String)request.getParameter("operation");
	}
		
	//��"�ҵĹ���"���ݵĿ��Ʊ���
	String strTempAction = "";
	if (request.getAttribute("strTempAction") != null) 
	{
		strTempAction = (String)request.getAttribute("strTempAction");
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
        System.out.println("financeInfo.getNDepositBillStatusId()��ֵ��================"+financeInfo.getNDepositBillStatusId());
        boolean isbill = false;
        if(financeInfo.getNDepositBillInputuserId() > 0)
        	isbill = true;
        if(isbill)
        	strTitle = "[�������ڴ浥]";
        else
        	strTitle = "[���ڿ���]";
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		long doApproval = OBConstant.SettInstrStatus.DOAPPRVOAL;
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.OpenFixDeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.OpenFixDeposit.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//���⴦��
			 	valueArray[5] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		
		blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}
%>
<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<isswf:init/>

<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} %>

<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <form name="frm" method="post">
      <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">���ڿ���</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
</table>
<br/>
	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"><%if(isbill) {%>�������ڴ浥ȷ��<%}else{ %>���ڿ���ȷ��<%} %></td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br><%if(isbill) {%>�������ڴ浥ȷ��<%}else{ %>���ڿ���ȷ��<%} %>��<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>�ڸ��˺��<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>�����������˺��<%} }%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<br>
              <!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <%if(!OBFSWorkflowManager.isAutoCheck()){
			  	if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){
			  %>
			  <br>
              �ñʽ����д������˸��ˣ�
			  <br>
			  <%}
			  else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){
			  %>
			  <br>
              �ñʽ����д�������������
			  <br>
			  <%}
			  }
			  %>
              <br>
              <b>ָ�����Ϊ��<%= financeInfo.getID() %></b><br>
              <br>
            </p>
          </td>
          <td width="5" height="25"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
      </table>
      <br>
	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �����˻�</td>
	<td width="17"><a class=lab_title3></td>
</tr>  
        </table></td>
      </tr>
    </table>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          
		  <td width="130" height="25" class="MsoNormal">�����˻����ƣ�</td>
          <td width="430" height="25" class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<!--
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">�����˺ţ�</td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayerBankNo() %></td>
          <td width="5"></td>
        </tr>
		-->
        <tr class="MsoNormal">
          <td colspan="4" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          
    <td width="130" height="25" class="MsoNormal">�����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> �����˻�</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>

      
  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>�����˺ţ�</p></td>
      <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo().toString()) %></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>
		<%
			if(financeInfo.getIsAutoContinue() == 1)
			{
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					�Ƿ��Զ����棺
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">��
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<%
				if(financeInfo.getAutocontinuetype() == 1)
				{
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							�Զ��������ͣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">��Ϣ����
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>		
		<%			
				}else if(financeInfo.getAutocontinuetype() == 2) {
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							�Զ��������ͣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">��������
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							��Ϣת�������˻���
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid()) %>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							���ڿͻ����ƣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid())%>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>										
		<%			
				}		
			}else if(financeInfo.getIsAutoContinue() == 2) {
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					�Ƿ��Զ����棺
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">��
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>			
		<%		
			} 
		%> 
<%} %>   
    <!-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal" width="141">���������</td>
      <td class="MsoNormal" width="26">
      <div align="right"><%//= sessionMng.m_strCurrencySymbol %>&nbsp;</div>
      </td>
      <td width="547" height="25" class="MsoNormal"><%//= financeInfo.getMamOuntForTrans() %></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
     -->
    <tr class="MsoNormal"> 
      <td colspan="6" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>���ڴ�����ޣ�</p></td>
      <td width="430" height="25" class="MsoNormal"><%=financeInfo.getFixedDepositTime() > 10000?financeInfo.getFixedDepositTime()-10000:financeInfo.getFixedDepositTime() %><%=(financeInfo.getFixedDepositTime() > 10000)?"��":"����"%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <%-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>�����Ƿ����棺</p></td>
      <td width="547" height="25" class="MsoNormal">
        <% long isFixContinue =financeInfo.getIsFixContinue();System.out.print("MMMMMMMMMMMMMM"+financeInfo.getIsFixContinue()); if(isFixContinue > 1){%>
        ���ڲ�����
        <%}else{  %>
        ��������
        <% }%>
      </td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal" >��ע��</td>
      <td width="547" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getFixEdremark()) %></td>
      <td width="10" class="MsoNormal"></td>
    </tr>
    --%>
  </table>

      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ��������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
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
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
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
          <td width="430" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getNote()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // ��ȷ��,δ����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //�����
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) || //�Ѿܾ� 
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE) || // ��ȷ��,δ����(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��(����)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //������(����)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH ) || //�����(����)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ�(����)  		   
		{
%>
		 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td style="width:160px" class="lab_title2"> �������봦������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width=80% border="1" align="" cellpadding="0" cellspacing="0" class=normal >
       <thead>
        <tr >
          <td width="63" height="19" class="ItemTitle">
            <p align="center">���к�</p>
          </td>
          
          <td  height="19" valign="middle"  width="190" >
            <div align="center">�û�</div>
          </td>
         
          <td  height="19" valign="middle"  width="198" >
            <div align="center">��������</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"  height="25" >
            <div align="center">1</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%if(isbill){%><%= NameRef.getUserNameByID(financeInfo.getNDepositBillInputuserId()) %><%}else{%><%= financeInfo.getConfirmUserName() %><%} %></div>
          </td>
          
          <td   width="198" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if(isbill){%><%=financeInfo.getDtDepositBillInputdate().toString().substring(0,19)%><%}else{ %><%= financeInfo.getFormatConfirmDate() %><%} %></div>
          </td>
        </tr>
        
<% }
			if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ� 
        		&&(financeInfo.getNDepositBillInputuserId() == 0))
        	{
%>
        <tr valign="middle">
          <td width="63" align="left"   height="25">
            <div align="center">2</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <% }
			if (
        		((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��(����)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //������(����)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)|| //�����(����)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))&& //�Ѿܾ�(����)
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && 
        		(financeInfo.getDtDepositBillCheckdate() != null)) 
        	{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">2</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%if(isbill){%><%= NameRef.getUserNameByID(financeInfo.getNDepositBillCheckuserId()) %><%}%>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if(isbill){%><%=financeInfo.getDtDepositBillCheckdate().toString().substring(0,19)%><%}%></div>
          </td>
        </tr>
       
<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE))&&//�Ѿܾ�
        			(financeInfo.getSignUserName() != null)&&
        			(financeInfo.getNDepositBillInputuserId() == 0))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				}
%>
<% 
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��(����)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //������(����)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)|| //�����(����)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))&&//�Ѿܾ�(����)
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)&& 
            		(financeInfo.getDtDepositBillSignDate() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%if((isbill)){%><%=NameRef.getUserNameByID(financeInfo.getNDepositBillSignUserID()) %><%}%></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if((isbill)){%><%=financeInfo.getDtDepositBillSignDate().toString().substring(0,19)%><%}%></div>
          </td>
        </tr>
<% 
			}
%>
 </table>

<%
	if(isbill){
%>     
	<table width="80%" border="0" align="" cellspacing="0" cellpadding="0" class=normal>
        <tr class="tableHeader">
          <!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <td colspan="2" height="22" class=FormTitle><font size="3" ><b>�������ڴ浥����</b></font></td>
          <!--td width="5" height="22" bgcolor="#0C3869"></td-->
        </tr>

        <tr>
          <td width="23%" height="25" class="MsoNormal">&nbsp;�������ڴ浥¼��ժҪ��</td>
          <td width="77%"><%=financeInfo.getSDepositBillAbstract() == null?"":financeInfo.getSDepositBillAbstract() %></td>
        </tr>
        <%if(financeInfo.getNDepositBillStatusId() != OBConstant.SettInstrStatus.SAVE || lCheckType > -1) {%>
        <tr>
          <td width="23%" height="25" class="MsoNormal">&nbsp;�������ڴ浥����ժҪ��</td>
          <td width="77%"><span class="MsoNormal">
            <%if(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK || financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN ) {%>
         <%=financeInfo.getSDepositBillCheckAbstract() == null?"":financeInfo.getSDepositBillCheckAbstract() %>
         <%} else{%><textarea name="lAbstractID" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield ='';" ><%=financeInfo.getSDepositBillCheckAbstract() == null?"":financeInfo.getSDepositBillCheckAbstract() %></textarea>
         <%} %>
          </span></td>
        </tr>
        <%} %>
      </table>
      <br/>
      <%} %>
	
	  <%--
	  <table align="center" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">���Ӹ���</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="80%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
           
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " �� �� " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
      <%--     </td>
        </tr>
      </table>
      --%>
<br/>
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">��ʷ�������</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
	  <table border="0" width="80%" cellspacing="0" cellpadding="0" align="" class=normal>
	  <TR>
		  <TD colspan="3">
		<%-- 	 <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>&&transType=<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
		 --%>
		  <fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
		  </TD>
	  </TR>
	  <tr>
		         <%
		    if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
			{
				//added by mzh_fu 2007-05-03 �����������Ѹ��˵����񣬲�����ʾ�������¼���
				if(financeInfo.getStatus() != OBConstant.SettInstrStatus.APPROVALED && financeInfo.getStatus() != OBConstant.SettInstrStatus.CHECK){

						String strMagnifierNameRemark = "�������";
						String strFormNameRemark = "frm";
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
						String strFirstTDRemark="align='left'";
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
		<table width="80%">
		<tr>
		<td colspan="7"></td>
		<td align="right">
			<%
	    	//����ȡ�������б����ʱ,��ʾȡ��������ť
	    	if(operation!=null && operation.length()>0 && Long.parseLong(operation)==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
	    		{
	    	%>
	    	<input class="button1" name="ca" type="button" value=" ȡ������ " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">
			<isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />
			<%	}
			else
				{
			%>
			<isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />
			<%	
			}
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
			</td>
        </tr>
       <%-- 
        <tr>
		<td width="80%" align="right">

<%
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� %>

            <input class=button1 name=add type=button value=" �޸� " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" ɾ�� " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
            
            <% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
            <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
            <%}%>

		<% }
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
			<% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
			/* �������� */
			System.out.println("sessionMng.m_lUserID="+sessionMng.m_lUserID);
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID != financeInfo.getNDepositBillInputuserId()) && (lCheckType == 1) && isbill ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// �Ѹ��ˡ���¼�ˣ������� %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
		  	/* ����ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillCheckuserId()) && isbill ) {// ����ɡ���¼�ˣ������� %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" ǩ�� " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* ����ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) && isbill  && (financeInfo.getIsNeedSign() == true)){// ����ɡ���Ҫ����¼��ǩ��=true %>

           	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" ǩ�� " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%} 
			/* ����ȡ��ǩ�� */
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillSignUserID()) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) && isbill ){// ����ɡ���¼�ˣ�ǩ���� %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->

				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <input type="Button" class="button1" value=" ��ӡ " width="46" height="18"   onclick="javascript:PrintConsignVoucher()" 
		   <%  if(a!=null && !a.contains(strStatus))
               {%>
					disabled
			   <%}
			%>>&nbsp;&nbsp;

               <!--
            <img src="\webob\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" �ر� " width="46" height="18"   onclick="window.close();">&nbsp;&nbsp;
         	<%}%>
		 </td>
        </tr>
        --%>
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
	  <input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="strAction" value="">

	  <!-- add by mingfang 2007-05-24 -->
	  <!-- ǩ�����ֶ� -->
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>">
	   <input type="hidden" name="nFixedDepositTime" value="<%=financeInfo.getFixedDepositTime() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  

	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	   
	  <safety:certFilterHidden />
	  </form>
<!--presentation end-->

<script language="javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//����������
	function doApproval()
	{
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("�������������");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	    frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
		if (confirm("�Ƿ�����?")) 
		{
		
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f003-c.jsp?operate=doApproval';
			frm.submit();
		}
	}	

//ȡ������   add by mingfang
	function cancelApproval()
	{
	
	 	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
		if (confirm("�Ƿ�ȡ������?")) 
		{
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f003-c.jsp?operate=cancelApproval';
			frm.submit();
		}
	}	
	
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
		        frm.menu.value="hidden";
		<%  }   %>
	}
	/*���ش����� */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f001-c.jsp";
		frm.submit();
	}
	
	function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}
	
	/* �޸Ĵ����� */
	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f001-c.jsp?isupdate=isupdate";
		frm.submit();
	}
	/* ɾ�������� */
	function deleteme()
	{
		if (!confirm("�Ƿ�ɾ����"))
		{
			return false;
		}
		//showMenu();
		frm.action="../fixed/f005-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	/* ���˴����� */
	function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp?fuhe=fuhe";
		frm.txtisCheck.value = "1";
		frm.txtTransType.value = "2";
		frm.submit();
	}
	
	/* ���˴����� */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* ǩ�ϴ����� */
	function signme()
	{
		//showMenu();
		frm.action="../sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* ȡ��ǩ�ϴ����� */
	function cancelsignme()
	{
		if (confirm("�Ƿ�ȡ����"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* ��ӡ������ */
	function printme()
	{
		frm.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frm.target="new_window";
		frm.submit();
		frm.target="";
	}

</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(IException e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>