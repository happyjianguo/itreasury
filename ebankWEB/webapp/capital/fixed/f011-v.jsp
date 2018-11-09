<!--
/*
 * �������ƣ�f011-v.jsp
 * ����˵��������ת���ύ����ҳ��
 * �������ߣ��̿�
 * ������ڣ�2007��04��18��
 */
-->

<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:directive.page import="java.text.DateFormat" />
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[��������]";
%>

<%
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();	
	com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
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
       	
       	   	
	//�Ƿ���Ҫ������ by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";
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
		
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
		
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		
		/*if(blnUseITrusCert){
			String[] nameArray = OBSignatureConstant.ChangeFixdeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.ChangeFixdeposit.getSignValueArrayFromInfo(financeInfo);	
			if(OBSignatureUtil.isIdHaveNotRealValue(financeInfo,blnIsNeedApproval)){
				//���⴦��			
			 	valueArray[OBSignatureConstant.ChangeFixdeposit -1 ] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}*/
		String strID = (String)request.getAttribute("lID");
	    long lID = -1;
	    if((strID != null) && (strID.length()>0)){
	    	lID = Long.parseLong(strID);
	    }
%>

<safety:resources />

<link rel="stylesheet" href="/webloan/css/approve.css" type="text/css"> 

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
	<% 
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) 
		{
%>


	<table cellpadding="0" cellspacing="0" class="title_top">
		<tr>
			<td height="24">
				<table style="width:150px" cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
						<td class=title>
							<span class="txt_til2">��������</span>
						</td>
						<td class=title_right>
							<a class=img_title>
						</td>
					</TR>
				</TABLE>
			</td>
		</tr>


	</table>
	<br />
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							��������
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr>
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal">

				<p>
					<br>
					�������潫<%
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
	        	%>�����������˺���ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
	        else
	        {
	        	%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
        %>
					<br>
				
					<%if(isNeedApproval.equals("true"))
			  	{
			  		%><br>
					�ñʽ����д�������������
					<br>
					<%
			  	}else if(!OBFSWorkflowManager.isAutoCheck())
			  	{
			  		%><br>
					�ñʽ����д������˸��ˣ�
					<br>
					<%
			 	}%>
					<br>
					<b>ָ�����Ϊ��<%= financeInfo.getID() %></b>
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
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td style="width: 90px" class="lab_title2">
							���ڴ���˻�
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>

				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				�����˻����ƣ�
			</td>
			<td width="430" height="25" class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
			<td width="5" height="25"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				�����˺ţ�
			</td>
			<td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
			<td width="5"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				���ڴ�����˺ţ�
			</td>
			<td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getSDepositBillNo().toString()) %></td>
			<td width="5"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
	</table>
	<br>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">


					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							��������
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>


	<table width=100% border="0" cellspacing="0" cellpadding="0"
		class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					��
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal">
				<%= financeInfo.getFormatAmount() %>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					���ڴ�����ޣ�
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"��":"����"%></td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					�¶��ڴ����ʼ�գ�
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;�¶��ڴ����գ�<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
	</table>

	<br>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							��Ϣ����
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0"
		class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<%if(financeInfo.getSDepositInterestDeal() == 1 ) {%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				��������
			</td>
			<td width="20" height="25" class="MsoNormal">
				<div align="right"></div>
			</td>
			<td width="430" height="25" class="MsoNormal"></td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<%}else if(financeInfo.getSDepositInterestDeal() == 2 ){ %>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				��Ϣת�������˻�:
			</td>
			<td width="20" height="25" class="MsoNormal">
				<div align="right"></div>
			</td>
			<td width="430" height="25" class="MsoNormal">
				��Ϣת�������˺ţ�<%=eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<%} %>
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
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//�Ѿܾ�   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//������		   
		{ 
%>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="180" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="160" class="lab_title2">
							�������봦������
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=774 border="1" align="" cellpadding="0" cellspacing="0" class=list_table>
			<tr class="itemtitle">
				<td width="63" height="19">
					<p align="center">
						���к�
					</p>
				</td>

				<td height="19" valign="middle" width="190">
					<div align="center">
						�û�
					</div>
				</td>

				<td height="19" valign="middle" width="198">
					<div align="center">
						��������
					</div>
				</td>

				<td height="19" valign="middle" width="269">
					<div align="center">
						ʱ�������
					</div>
				</td>
			</tr>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					1
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getConfirmUserName() %></div>
			</td>

			<td width="198" height="25">
				<div align="center">
					¼��
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
			</td>
		</tr>

		<% }
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //�Ѿܾ�
        	{ 
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getCheckUserName() %>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					����
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatCheckDate() %></div>
			</td>
		</tr>
		<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//�Ѿܾ�
					(financeInfo.getSignUserName() != null))
         		{  
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getCheckUserName() %>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					����
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatCheckDate() %></div>
			</td>
		</tr>

		<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//�Ѿܾ�
					(financeInfo.getSignUserName() != null))
         		{ 
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getSignUserName() %></div>
			</td>

			<td width="198" height="25">
				<div align="center">
					ǩ��
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatSignDate() %></div>
			</td>
		</tr>
		<% 
				}
%>
	</table>
	
	<br />
	<table border="0" width=100% cellspacing="0" cellpadding="0"
		align="center">
		<!-- ��ʷ������� -->
		<TR>
			<TD colspan="3">
				<iframe
					src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>"
					width="100%" height="100" scrolling="Auto" frameborder="0"
					name="currentSignID"></iframe>

			</TD>
		</TR>
		<!-- ��ʷ������� -->
		<tr>
			<td width="774" align="right">

				<%
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� %>

				<input class=button1 name=add type=button value=" �� �� "
					onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">
				&nbsp;&nbsp;

				<input class=button1 name=add type=button value=" ɾ �� "
					onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
				&nbsp;&nbsp;

				<!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->

				<% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
				<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
				<input class=button1 name=add type=button value=" �� �� "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<%}%>

				<% }
		
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// δ����ȷ�ϡ���¼�ˣ�ȷ���� 
		if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) 
		{%>
				<input class=button1 name=add type=button value=" �� �� "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;

				<%}
		}
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" �� �� "
					onClick="Javascript:checkmatchme();"
					onKeyDown="Javascript:checkmatchme();">
				<% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE|| financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" �� �� "
					onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
				<input class=button1 name=add type=button value=" �� �� "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<% }
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// �Ѹ��ˡ���¼�ˣ������� %>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ "
					onClick="Javascript:cancelcheckme();"
					onKeyDown="Javascript:cancelcheckme();">

				<%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>

				<!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				<input class=button1 name=add type=button value=" ǩ �� "
					onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

				<%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>

				<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				<input class=button1 name=add type=button value=" ȡ��ǩ�� "
					onClick="Javascript:cancelsignme();"
					onKeyDown="Javascript:cancelsignme();">

				<%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>

				<!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
				<%
						///add by liuguang 2007-10-19  ����ҵ���Ƿ���Ҫ��ӡ����ʾ
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
				<input type="Button" class="button1" value=" �� ӡ " width="46"
					height="18" onclick="javascript:PrintConsignVoucher()"
					<%if(a!=null && !a.contains(strStatus))
               { 
			%>
					disabled <%
				}
			%>>
				<%
				}
				%>
				<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
				<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
				<input type="Button" class="button1" value=" �� �� " width="46"
					height="18" onclick="window.close();">
				<%}%>
			</td>
		</tr>
	</table>

	<input type="hidden" name="RemitType"
		value="<%= financeInfo.getRemitType() %>">
	<input type="hidden" name="Amount"
		value="<%= financeInfo.getAmount() %>">
	<input type="hidden" name="ExecuteDate"
		value="<%= financeInfo.getExecuteDate() %>">
	<input type="hidden" name="ConfirmUserID"
		value="<%= financeInfo.getConfirmUserID() %>">
	<input type="hidden" name="ConfirmDate"
		value="<%= financeInfo.getConfirmDate() %>">
	<input type="hidden" name="PayerAcctID"
		value="<%= financeInfo.getPayerAcctID() %>">
	<input type="hidden" name="PayeeAcctID"
		value="<%= financeInfo.getPayeeAcctID() %>">
	<input type="hidden" name="lInstructionID"
		value="<%= financeInfo.getID() %>">
	<input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	<input type="hidden" name="billstatusid"
		value="<%=financeInfo.getNDepositBillStatusId()%>">
	<input type="hidden" name="txtisCheck" value="">
	<input type="hidden" name="txtTransType" value="<%=lTransType %>">
	<input type="hidden" name="menu"
		value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">


	<!-- add by mingfang 2007-05-24 -->
	<!-- ǩ�����ֶ� -->

	<input type="hidden" name="nPayeeAccountID"
		value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="sPayerAccountNoZoomCtrl"
		value="<%=financeInfo.getSDepositBillNo()%>">
	<input type="hidden" name="dAmount"
		value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="nFixedDepositTime1"
		value="<%=financeInfo.getFixedDepositTime()%>">
	<input type="hidden" name="tsExecute"
		value="<%=financeInfo.getExecuteDate()%>">
	<input type="hidden" name="dPayerStartDate"
		value="<%=financeInfo.getSDepositBillStartDate()%>">
	<input type="hidden" name="interesttype"
		value="<%=financeInfo.getSDepositInterestDeal()%>">
	<input type="hidden" name="lInterestToAccountID"
		value="<%=financeInfo.getSDepositInterestToAccountID()%>">
	<input type="hidden" name="submitUserId"
		value="<%=financeInfo.getConfirmUserID()%>">

	<!-- ��c415.jsp�ж�ҵ��������-->
	<input type="hidden" name="SelectType"
		value="<%=financeInfo.getTransType() %>">
</form>
<!--presentation end-->

<script language="javascript">

	/*���ش����� */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f006-c.jsp";
		frm.submit();
	}

	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}

	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f007-c.jsp";
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
		frm.action="../fixed/f009-2-c.jsp?operate=delete";
		showSending();
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
   
   function checkme()
	{
		//showMenu();
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
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
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	$(document).ready(function() {
	 	$(".FormTitle").click(function(){
	      	$("#iTable").toggle();
	    });
	    $("#flexlist").flexigridenc({
			colModel : [
				{display: '���к�',  name : 'ncurrencyid', width : 200, sortable : false, align: 'center'},
				{display: '�û�',  name : 'payeracctno', width : 230, sortable : false, align: 'center'},
				{display: '��������',  name : 'payername', width : 230, sortable : false, align: 'center'},
				{display: 'ʱ�������',  name : 'ntranstype', width : 230, sortable : false, align: 'center'}
			],//�в���
			//title:'�������봦������',
			classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//Ҫ���õķ���
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
			usepager : false,
			userFunc : getFormData,
			height : 100
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
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/common.jsp"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc"%>