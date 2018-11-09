<%--
/*
 * �������ƣ�check_v007.jsp
 * ����˵�������ڿ������ҳ��
 * �������ߣ�
 * ������ڣ�2010��10��12��
 */
--%>

<!--Header start-->
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="java.util.ArrayList,com.iss.itreasury.util.ConfigConstant,com.iss.itreasury.ebank.util.*,com.iss.itreasury.util.*,com.iss.itreasury.ebank.obquery.dataentity.*,com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,com.iss.itreasury.util.OBFSWorkflowManager,com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
String strContext = request.getContextPath();
%>
<%!/* ����̶����� */
	String strTitle = "[���ڿ���]";%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
	long lSourceType = 0;
	String strSource = request.getParameter("src");
	if ((strSource != null) && (strSource.length() > 0)) {
		lSourceType = Long.parseLong(strSource);
	}
	String type = "";
	String _type = request.getParameter("type");
	if ((type != null) && (type.length() > 0)) {
		type = _type;
	}
	long lCheckType = -1;//�����ڸ���ƥ���
	String strCheckType = request.getParameter("checktype");
	if ((strCheckType != null) && (strCheckType.length() > 0)) {
		lCheckType = Long.parseLong(strCheckType);
	}
	String lTransType = "";
	lTransType = (String) request.getParameter("lTransType");
	if (lTransType == null)
		lTransType = (String) request.getParameter("txtTransType");

	//�Ƿ���Ҫ������ 
	String isNeedApproval = request.getParameter("isNeedApproval");
	if (isNeedApproval == null)
		isNeedApproval = "";
%>
<%
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = null;

	/* �ļ�ͷ��ʾ */
	try {
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		/* �������л�ȡ��Ϣ */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(
		sessionMng, financeInfo);
		
		boolean isbill = false;
		if (financeInfo.getNDepositBillInputuserId() > 0)
			isbill = true;
		if (isbill)
			strTitle = "[�������ڴ浥]";
		else
		strTitle = "[���ڿ���]";
		/* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);

		String strTransNo = financeInfo.getID() + "";

		boolean blnUseITrusCert = Config.getProperty(
		ConfigConstant.GLOBAL_TROY_NAME,
		Constant.GlobalTroyName.NotUseCertificate).equals(
		Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;


		//CFCA֤����ǩ
		//boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);		
    	String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);		
		if(isUseCertification)
		{
			
			String temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}
		}		
%>
<%
		double dMinSinglePayAmount = 0.0;
		long lIsSoft = 0;
		String strTemp = (String) request
		.getAttribute("dMinSinglePayAmount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dMinSinglePayAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("lIsSoft");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lIsSoft = Long.valueOf(strTemp).longValue();
		}
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%
if(isUseCertification &&!blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt(); 
	//-->
	</script>
<%
}
 %>
<form name="frm" method="post">
<table cellpadding="0" cellspacing="0" class="title_top" width="100%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">���ڿ���</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			
	<br/>
	<%
			if (lSourceType != 1
			&& financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) {
	%>



		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"><%
							if (isbill) {
							%>
							�������ڴ浥ȷ��
							<%
							} else {
							%>
							���ڿ���ȷ��
							<%
							}
							%></td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
		 
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr>
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal">

				<p>
					<br>
					<%
					if (isbill) {
					%>�������ڴ浥ȷ��<%
					} else {
					%>���ڿ���ȷ��<%
					}
					%>��<%
					if (isNeedApproval.equals("true")) {
					%>��������<%
					if (!OBFSWorkflowManager.isAutoCheck()) {
					%>�ȴ������˸��ˣ�<%
					} else {
					%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
								}
								} else if (!OBFSWorkflowManager.isAutoCheck()) {
					%>�ڸ��˺���ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
					} else {
					%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
					}
					%>
					<br>
					<!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
					<!--update by hyzeng 2003-4-10-->
					<%
					if (isNeedApproval.equals("true")) {
					%>
					<br>�ñʽ����д�������������<br>
					<%
					} else if (!OBFSWorkflowManager.isAutoCheck()) {
					%>
					<br>�ñʽ����д������˸��ˣ�<br>
					<%
					}
					%>
					<br>
					<b>ָ�����Ϊ��<%=financeInfo.getID()%>
					</b>
					<br>
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="98%" border="0" cellspacing="0" cellpadding="0">
					<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2">&nbsp; �����˻�</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>

			<td width="130" height="25" class="MsoNormal">
				�����˻����ƣ�
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getPayerName() == null
						|| financeInfo.getPayerName() == "" ? "&nbsp;"
						: financeInfo.getPayerName()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<!--
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">�����˺ţ�</td>
          <td width="430" height="25" class="graytext"><%=financeInfo.getPayerBankNo()%></td>
          <td width="5"></td>
        </tr>
		-->
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>

			<td width="130" height="25" class="MsoNormal">
				�����˺ţ�
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayerAcctNo())%>
			</td>
			<td width="5" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
										<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2">&nbsp; �����˻�</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>


	<table width="98%" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					�����˺ţ�
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayeeAcctNo())%>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
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
			<td height="25" width="130" class="MsoNormal">
				<p>
					���ڴ�����ޣ�
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getFixedDepositTime()>10000?financeInfo.getFixedDepositTime()-10000:financeInfo.getFixedDepositTime()%>
				<%=(financeInfo.getFixedDepositTime() > 10000) ? "��"
								: "����"%>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
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
<%}%> 	
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp; ��������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				��
			</td>
			<td width="3%" height="25" class="MsoNormal">
				<div align="right">
					<%=sessionMng.m_strCurrencySymbol%>
				</div>
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getFormatAmount()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td width="130" height="25" class="MsoNormal" colspan="2">
				ִ���գ�
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getFormatExecuteDate()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td width="130" height="25" class="MsoNormal" colspan="2">
				�����;��
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=DataFormat.formatString(financeInfo.getNote())%>
			</td>
			<td width="5" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<%
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)
			|| // ��ȷ��,δ����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
			|| // �Ѹ���
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
			|| // ��ǩ��
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
			|| //������
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
			|| //�����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)
			|| //�Ѿܾ� 
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE)
			|| // ��ȷ��,δ����(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
			|| // �Ѹ���(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
			|| // ��ǩ��(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
			|| //������(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)
			|| //�����(����)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ�(����)  		   
			{
	%>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="180" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3"><a class=lab_title1></td>
						<td class="lab_title2" style="width:140px"> &nbsp; �������봦������</td>
						<td width="17"><a class=lab_title3></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=98% border="0" align="center" cellpadding="0" cellspacing="1" class=list_table>
			<tr class=itemtitle>
				<td width="20%" height="26">
					<p align="center">
						���к�
					</p>
				</td>

				<td>
					<div align="center">
						�û�
					</div>
				</td>

				<td>
					<div align="center">
						��������
					</div>
				</td>

				<td>
					<div align="center">
						ʱ�������
					</div>
				</td>
			</tr>
		<tr valign="middle">
			<td width="20%" align="center" height="25">
				<div align="center">
					1
				</div>
			</td>

			<td width="20%" height="25">

				<div align="center">
					<%
					if (isbill) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillInputuserId())%>
					<%
					} else {
					%>
					<%=financeInfo.getConfirmUserName()%>
					<%
					}
					%>
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					¼��
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=financeInfo.getDtDepositBillInputdate()
										.toString().substring(0, 19)%>
					<%
					} else {
					%>
					<%=financeInfo.getFormatConfirmDate()%>
					<%
					}
					%>
				</div>
			</td>
		</tr>

		<%
				}
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
				|| // �Ѹ���
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
				|| //������
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) || //�����
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ� 
				&& (financeInfo.getNDepositBillInputuserId() == 0)) {
		%>
		<tr valign="middle">
			<td width="20%" align="center" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%=financeInfo.getCheckUserName() == null ? "����"
									: financeInfo.getCheckUserName()%>
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					����
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%=financeInfo.getFormatCheckDate()%>
				</div>
			</td>
		</tr>
		<%
				}
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
				|| // �Ѹ���(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
				|| //������(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //�����(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
				&& //�Ѿܾ�(����)
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
				&& (financeInfo.getDtDepositBillCheckdate() != null)) {
		%>
		<tr valign="middle">
			<td width="20%" align="center" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillCheckuserId())%>
					<%
					}
					%>
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					����
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=financeInfo.getDtDepositBillCheckdate()
										.toString().substring(0, 19)%>
					<%
					}
					%>
				</div>
			</td>
		</tr>

		<%
				}
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
				|| //������
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) || //�����
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE))
				&& //�Ѿܾ�
				(financeInfo.getSignUserName() != null && !"".equals(financeInfo.getSignUserName()))
				&& (financeInfo.getNDepositBillInputuserId() == 0)) {
		%>
		<tr valign="middle">
			<td width="20%" align="center" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%=financeInfo.getSignUserName()%>
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					ǩ��
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%=financeInfo.getFormatSignDate()%>
				</div>
			</td>
		</tr>
		<%
		}
		%>
		<%
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
				|| // ��ǩ��(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
				|| //������(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //�����(����)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
				&& //�Ѿܾ�(����)
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
				&& (financeInfo.getDtDepositBillSignDate() != null)) {
		%>
		<tr valign="middle">
			<td width="20%" align="center" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%
					if ((isbill)) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillSignUserID())%>
					<%
					}
					%>
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					ǩ��
				</div>
			</td>

			<td width="20%" height="25">
				<div align="center">
					<%
					if ((isbill)) {
					%>
					<%=financeInfo.getDtDepositBillSignDate()
										.toString().substring(0, 19)%>
					<%
					}
					%>
				</div>
			</td>
		</tr>
		
		<%
		}
		%>
	</table>
	<br/>
	<%
	if (isbill) {
	%>
	<table width="100%" border="0" align="" cellspacing="0" cellpadding="0" class=normal>
		<tr class="tableHeader">
			<!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
			<td colspan="2" height="22" class=FormTitle>
				<font size="3"><b>�������ڴ浥����</b>
				</font>
			</td>
			<!--td width="5" height="22" bgcolor="#0C3869"></td-->
		</tr>

		<tr>
			<td width="23%" height="25" class="MsoNormal">
				&nbsp;�������ڴ浥¼��ժҪ��
			</td>
			<td width="77%">
				<%=financeInfo.getSDepositBillAbstract() == null ? ""
									: financeInfo.getSDepositBillAbstract()%>
			</td>
		</tr>
		<%
					if (financeInfo.getNDepositBillStatusId() != OBConstant.SettInstrStatus.SAVE
					|| lCheckType > -1) {
		%>
		<tr>
			<td width="23%" height="25" class="MsoNormal">
				&nbsp;�������ڴ浥����ժҪ��
			</td>
			<td width="77%">
				<span class="MsoNormal"> <%
 				if (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK
 				|| financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) {
 %>
					<%=financeInfo
													.getSDepositBillCheckAbstract() == null ? ""
											: financeInfo
													.getSDepositBillCheckAbstract()%> <%
 } else {
 %>
					<textarea name="lAbstractID" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;"
						onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield ='';" type="_moz">
						<%=financeInfo
													.getSDepositBillCheckAbstract() == null ? ""
											: financeInfo
													.getSDepositBillCheckAbstract()%>
					</textarea> <%
 }
 %> </span>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<br/>
	<%
	}
	%>
	<%--
    <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
	<% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	<fs:uploadFile strContext='<%=strContext%>' showOnly='true'
		transCode='<%=strtransNo%>' caption="�ϴ�" lid='-1'
		moduleID='<%=Constant.ModuleType.SETTLEMENT%>' transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
		transSubTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' currencyID='<%=sessionMng.m_lCurrencyID%>'
		officeID='<%=sessionMng.m_lOfficeID%>' clientID='<%=sessionMng.m_lClientID%>'
		islowerunit = '<%=OBConstant.IsLowerun.ISNO%>' />
	<%--     </td>
        </tr>
    </table>
 --%>
 <br/>
 

<% if(isNeedApproval.equals("true")) {%>
	   <table width="100%" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">��ʷ�������</td>
				<td width="800"><a class=lab_title3></td>
			</tr>
		</table>
	<table border="0" width="98%" cellspacing="0" cellpadding="0" align="center" class=list_table>
		<!-- ��ʷ������� -->
		<TR>
			<TD colspan="3"  class="itemtitle">
				<iframe
					src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>"
					width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
			<%--<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>--%>
			</TD>
		</TR>
		<!-- ��ʷ������� -->
		</table>
		<%} %>
		<br />
		<table border="0" width=100% align="" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right">

				<%
						/* ��ָ���ѯҳ������� */
						String search = "";
						if (request.getAttribute("search") != null) {
							search = (String) request.getAttribute("search");
						}
						String isSign="";
						if(request.getParameter("sign")!=null){
						isSign=request.getParameter("sign");
						}
				%>		

				
				<%
						/* ���� */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
						.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
						&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
						&& (lCheckType == 1)&& !search.equals("1")) {// ��ȷ�ϡ���¼��<>ȷ����
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" �� �� " onClick="Javascript:checkme();"
					onKeyDown="Javascript:checkme();">
				
				<% } %>
				<%
				if (lShowMenu == OBConstant.ShowMenu.NO) {
				%>
				<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
				<input type="Button" class="button1" value=" �� �� " width="46" height="18" onclick="window.close();">
				&nbsp;&nbsp;
				<%
				}
				%>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<input type="hidden" name="RemitType" value="<%=financeInfo.getRemitType()%>">
	<input type="hidden" name="Amount" value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="ExecuteDate" value="<%=financeInfo.getExecuteDate()%>">
	<input type="hidden" name="ConfirmUserID" value="<%=financeInfo.getConfirmUserID()%>">
	<input type="hidden" name="ConfirmDate" value="<%=financeInfo.getConfirmDate()%>">
	<input type="hidden" name="PayerAcctID" value="<%=financeInfo.getPayerAcctID()%>">
	<input type="hidden" name="PayeeAcctID" value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
	<input type="hidden" name="txtID" value="<%=financeInfo.getID()%>">
	<input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	<input type="hidden" name="txtisCheck" value="">
	<input type="hidden" name="txtTransType" value="<%=lTransType%>">
	<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO ? "hidden"
						: ""%>">
	<input type="hidden" name="dtmodify" value="<%=(financeInfo.getDtModify()==null?"":financeInfo.getDtModify().toString()) %>">
	<!-- ǩ�����ֶ�  add by mingfang -->
	<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>">
	<input type="hidden" name="nFixedDepositTime" value="<%=financeInfo.getFixedDepositTime()%>">
	<input type="hidden" name="dAmount" value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate()%>">

	<input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID()%>">
	<!-- ��c415.jsp�ж�ҵ��������-->
	<input type="hidden" name="SelectType" value="<%=financeInfo.getTransType()%>">
	</td>
		</tr>
	</table>
</form>
<script language="javascript">
	/* ���˴����� */
	function checkme()
	{
		//showMenu();
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��	
		<%}%>
		
	    if(!<%=dMinSinglePayAmount%> == 0.0 ){
			if(parseFloat(<%=dMinSinglePayAmount%>)>reverseFormatAmount(frm.dAmount.value))
			{
				alert("�����С�ڴ�����������Ϊ��<%=sessionMng.m_strCurrencySymbol%>" + <%=dMinSinglePayAmount%>);
				if(parseInt(<%=lIsSoft%>) == 1) {
					return false;
				}
			}
		} 
		var msg = "�Ƿ񸴺ˣ�";
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
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>
		frm.action="../control/check_c006_1.jsp?fuhe=fuhe";
		frm.txtisCheck.value = "1";
		frm.txtTransType.value = "2";
		frm.submit();
	}
</script>
<%
			if (lShowMenu == OBConstant.ShowMenu.YES) { /* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	} catch (Exception e) {
		e.printStackTrace();
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e,
		strTitle, "", 1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>	