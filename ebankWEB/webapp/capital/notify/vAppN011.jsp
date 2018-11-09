<%--
/*
 * �������ƣ�vAppN011-v.jsp
 * ����˵����֪֧ͨȡ�ύҳ��
 * �������ߣ�ypxu
 * ������ڣ�07-04-25
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"
%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[֪֧ͨȡ]";
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
		
		
		//ȡ�������б�ҳ���ȡ��������
		String operation = "-1";
		if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
		{
			operation =(String)request.getParameter("operation");
		}
		
		
		
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} 
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
/*		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.NotifyDepositDraw.getSignNameArray();
			String[] valueArray = OBSignatureConstant.NotifyDepositDraw.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//���⴦��
			 	valueArray[7] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
				 
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}*/
%>
<safety:resources />

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
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
<table width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">֪ͨ���֧ȡ</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	    </td>
	  </tr>
	</table>
	<br/>
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> ֪֧ͨȡȷ��</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
    </table> 
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->          
		 <!-- td width="560" class=FormTitle height="25" colspan="3"><font size="3" >֪֧ͨȡȷ��</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
        -->
        
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>֪֧ͨȡ��<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>�ڸ��˺��<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>�����������˺��<%} }%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<br>
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
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
      
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">֪ͨ�˻�����</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table> 
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->          
		<!-- td width="560"height="25" class=FormTitle colspan="4"><font size="3" >֪ͨ�˻�����</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        -->
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25"  class="MsoNormal">֪ͨ�˻����ƣ�</td>
          <td width="430" height="25"  class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
       
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25" class="MsoNormal">֪ͨ�˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getPayerAcctNo() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25" class="MsoNormal">֪ͨ���ݺţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getDepositNo() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
		
      </table>
      <br>
      <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">֪ͨ�˻�����</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
     <!-- 
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <!-- td width="560"height="25" colspan="3" class=FormTitle><font size="3" >�տ����</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      -->
     <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr class="MsoNormal">
          <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">����</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">��ʽ��</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatRemitType() %></td>
        </tr>
        <tr id="payeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNo" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">�տ�˺ţ�</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getPayeeAcctNo() %></td>
        </tr>
        <tr id="payeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNoName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">�տ���ƣ�</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getPayeeName() %></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeePlace" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">����أ�</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getPayeeProv() == null) ? "��" : financeInfo.getPayeeProv()) + "ʡ" + ((financeInfo.getPayeeCity() == null) ? "��" : financeInfo.getPayeeCity()) + "�У��أ�" %> 
          </td>
        </tr>
        <tr id="payeeBankNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">���������ƣ�</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getPayeeBankName() == null) ? "" : financeInfo.getPayeeBankName() %> 
          </td>
        </tr>
        <tr id="line1" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        
      </table>  
		  </td>
		  <td width="1" class="MsoNormal">
		  </td>
		  <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">��Ϣ��</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">��ʽ��</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatInterestRemitType() %></td>
        </tr>
        
        <tr id="InterestPayeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNO" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">�տ�˺ţ�</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getInterestPayeeAcctNo() %></td>
        </tr>
        <tr id="InterestPayeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNoName" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">�տ���ƣ�</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getInterestPayeeName() %></td>
        </tr>
        <tr id="InterestPayeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeePlace" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">����أ�</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getInterestPayeeProv() == null) ? "��" : financeInfo.getInterestPayeeProv()) + "ʡ" + ((financeInfo.getInterestPayeeCity() == null) ? "��" : financeInfo.getInterestPayeeCity()) + "�У��أ�" %> 
          </td>
        </tr>
        <tr id="InterestPayeeBankLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBank" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">���������ƣ�</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getInterestPayeeBankName() == null) ? "" : financeInfo.getInterestPayeeBankName() %>	
          </td>
        </tr>
        
      </table>  
		  </td>
        </tr>
      </table>
	   
	  <br>
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> ��������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
      <table width="80%" border="0" cellspacing="0" cellpadding="0"class ="normal" align="">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <!-- td width="560"height="25"  class=FormTitle colspan="5"><font size="3" >��������</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        -->
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25"  class="MsoNormal" width="110">��</td>
          <td width="20" height="25"  class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25"  class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="60" height="25"  class="MsoNormal" colspan="2">ִ���գ�</td>
          <td width="430" height="25" align="left"  class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">�����;��</td>
          <td width="430" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getNote()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // ��ȷ��,δ����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //�����
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ�      		   
		{ 
%>
      <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	  	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">�������봦������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
     <!-- 
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="tableHeader">
          <!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <!-- td colspan="5" height="22" class=FormTitle bgcolor="#0C3869"><font size="3" ><b>�������봦������</b></font></td>
          <!--td width="5" height="22" bgcolor="#0C3869"></td>
        </tr>        
      </table>
      -->
      <table align="" width="80%" border="0" class="normal">
        <tr class="tableHeader">
          <td width="50" bgcolor="#456795" height="19" class="ItemTitle">
            <p align="center"><font size="2" >���к�</font></p>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="150">
            <div align="center">�û�</div>
          </td>
         
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="150">
            <div align="center">��������</div>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="217">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
        
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center">1</div>
          </td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="215" height="25">
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
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="ItemBody">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">����</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="217" height="25">
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
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="ItemBody">3</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">ǩ��</div>
          </td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="217" height="25">
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
 <br>

<%
 		} 
%>
<%-- <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">���Ӹ���</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>--%>
	            <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	            <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " �� �� " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
         <%--  </td>
        </tr>
      </table>--%>
	  <br>
      <form name="frm" method="post">
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">��ʷ�������</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	  </table>
	  <table border="0" align="" width="80%" cellspacing="0" cellpadding="0" class="normal">
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		 	<fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>'
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
		<table width="80%"><tr><td align="right">
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
            </td>
        </tr>
	  <%--
        <tr>
		<td width="730" align="right">

<%
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� %>

<!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
		    <input class=button1 name=add type=button value=" �޸� " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">

			<input class=button1 name=add type=button value=" ɾ�� " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->

            
            <% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO ) {%>            
			<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
            <%}%>

		<% }
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
		  <% }
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// �Ѹ��ˡ���¼�ˣ������� %>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">
		<%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>

			 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
			 <input class=button1 name=add type=button value=" ǩ�� " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

		<%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>

				 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		  
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <input type="Button" class="button1" value=" ��ӡ " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%
			   if(a!=null && !a.contains(strStatus))
               {
			   // ���Ŵ�ӡȨ�� 2007/3/16 modify by wjliu ��disabledȥ��
		   %>
				
		   <%
			   }
			%>
		   >
               <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" �ر� " width="46" height="18"   onclick="window.close();">
         	<%}%>
		 </td>
        </tr>
        --%>
      </table>
      <br/>

	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="txtTransType" value="<%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW %>">

	 <!-- ǩ�����ֶ� -->
	  <input type="hidden" name="sNotifyDepositNoCtrl" value="<%=financeInfo.getDepositNo() %>">
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=financeInfo.getInterestPayeeAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">	  
	  <input type="hidden" name="nRemitTypeHidden" value="<%=financeInfo.getRemitType() %>">	  
	  <input type="hidden" name="nInterestRemitTypeHidden" value="<%=financeInfo.getInterestRemitType() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="strAction" value="">
	  
	  	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>">  
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
	document.frm.strAction.value = <%=OBConstant.SettInstrStatus.DOAPPRVOAL%>;
		if (confirm("�Ƿ�����?")) 
		{
		
			//add by mingfang ����ǩ�� 2007-05-25 
			<%if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			frm.action="<%=strContext%>" + '/capital/notify/n011-c.jsp?operate=doApproval';
			frm.submit();
		}
	}


//ȡ������   add by mingfang
	function cancelApproval()
	{
	
		document.frm.strAction.value = <%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>;
		if (confirm("�Ƿ�ȡ������?")) 
		{
			<%if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>		
			frm.action="<%=strContext%>" + '/capital/notify/n011-c.jsp?operate=cancelApproval';
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
		frm.lInstructionID.value = "";
		frm.action="../notify/n011-c.jsp";
		frm.submit();
	}

    function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}

	/* ȷ�ϴ����� */
	function confirmme()
	{
		//showMenu();
		frm.action="../notify/C13.jsp";
		frm.submit();
	}
	/* �޸Ĵ����� */
	function updateme()
	{
		//showMenu();
		frm.action="../notify/n011-c.jsp";
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
		frm.action="../notify/n015-c.jsp";
		showSending();
		frm.submit();
	}
	/* ����ƥ�亯�� */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* ���˴����� */
	function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		if (confirm("�Ƿ�ȡ�����ˣ�"))
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
		if (confirm("�Ƿ�ȡ��ǩ�ϣ�"))
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
		OBHtml.showExceptionMessage(out,sessionMng, e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>