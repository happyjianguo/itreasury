<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/**ҳ�湦��˵��
	 * ҳ������ ��v090.jsp
	 * ҳ�湦�� : �˻����˵�
	 * ��    �� ��zcwang
	 * ��    �� ��2008-4-29
	 * ��ʵ��˵����
	 *				1����ѯ����¼��
	 * ����˵�� ��
	 * �޸���ʷ ��
	 */
%>
<!--�ർ�벿�ֿ�ʼ-->
<jsp:directive.page import="com.iss.itreasury.util.DataFormat" />
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:directive.page import="com.iss.itreasury.util.Env" />
<jsp:directive.page import="com.iss.itreasury.ebank.system.util.HtmlKit" />
<%@ page contentType="text/html;charset=GBK"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<jsp:directive.page
	import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam" />
<%
	String strContext = request.getContextPath();
%>
<!--�ർ�벿�ֽ���-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
	//String clientID = request.getParameter("clientid");
	//String officeid = request.getParameter("officeid");
	//String moduleid = request.getParameter("moduleid");
	//session.setAttribute("eofficeID",String.valueOf(officeid).toString());
	//session.setAttribute("emoduleid",String.valueOf(moduleid).toString());
	//session.setAttribute("eclientid",String.valueOf(clientID).toString());
	try {
		//emoduleid����6��������ģ��
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
		// 		out.flush();
		//		return;
		//}
		String strTitle = null;
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E003");
			out.flush();
			return;
		}
		/**ҳ��У�鿪ʼ(�û���¼У�顢�û�Ȩ��У�顢�ظ�����У��)**/
		JSPLogger
				.info("****************����ҳ��--bankportal\\v090-subaccount_ebank.jsp");

		/**ҳ��У�����**/

		/**ҵ���߼���ʼ**/

		long clientId = -1;
		long subclientId = -1;
		/*
		try{
			String strTemp = (String)session.getAttribute("eclientid");
			if(strTemp!=null && strTemp.length()>0)
			{
		  	 	clientId = Long.parseLong(strTemp);
		    }
		   
		}catch(Exception ex){
		}
		 */
		clientId = sessionMng.m_lClientID;
		QueryBillPrintParam queryInfo = new QueryBillPrintParam();
		queryInfo.convertRequestToDataEntity(request);
		if (clientId == -1)
			clientId = queryInfo.getClientIdFrom();

		/**ҵ���߼�����**/

		/**ҳ����ʾ��ʼ*/
		// HTMLHelper.showHomeHead(out,sessionMng,"�˻����˵�",BooleanValue.TRUE);//��ʾҳ��ͷ
%>

<jsp:include page="/ShowMessage.jsp" />

<!--����js�ļ�-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webbp/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script type="text/javascript" src="/webob/js/jquery-1.4.2.min.js"></script>
	  <script type=text/javascript src=/webob/js/wpCalendar.js></script>
	  
	  <script type="text/javascript" src="/webob/js/flexigrid.js"></script>
<script type="text/javascript" src="/webob/js/flexigridEncapsulation.js"></script>
<script type="text/javascript" src="/webob/js/suggest.js"></script>
<script type="text/javascript" src="/webob/js/jquery-ui-1.7.2.custom.min.js"></script>
<link rel="stylesheet" href="/webob/css/jquery-ui-1.7.2.custom.css" type="text/css">
<link rel="stylesheet" href="/webob/css/suggest.css" type="text/css">
<link rel="stylesheet" href="/webob/css/flexigrid.css" type="text/css">
	  

<!--����js�ļ�-->

<!--ҳ�����ʼ-->
<form name="frmV090" method="post" action="">
	<!--ҳ�����Ԫ�ؿ�ʼ-->
	<input name="ActionID" type="hidden"
		value="<%=new java.util.Date().getTime()%>">
	<input name="p_Action" type="hidden" value="findFirst">
	<input name="p_SuccessPageURL" type="hidden"
		value="/bankportal/v091-subaccount_ebank.jsp">
	<input name="p_FailPageURL" type="hidden"
		value="/bankportal/v090-subaccount_ebank.jsp">
	<input name="systemDate" type="hidden"
		value="<%=DataFormat.formatDate(Env.getSystemDateTime(), 1)%>">
	<!----�˺ŷŴ�Ĭ�ϵ�ƥ��ֵ---->
	<input name="clientId" type="hidden" value="<%=clientId%>">
	<input name="countryId" type="hidden" value="-1">
	<input name="isCheck" type="hidden" value="1">
	<input name="accounttype" type="hidden" value="1">
	<input name="isDirectLink" type="hidden" value="1">
	<input name="accountStatus" type="hidden" value="1">
	<input name="officeID" type="hidden"
		value="<%=(String) session.getAttribute("eofficeID")%>">
	<!--ҳ�����Ԫ�ؽ���-->
	<input type="hidden" name="m_lOfficeID" id="m_lOfficeID" value="<%=sessionMng.m_lOfficeID %>"/>
		<input type="hidden" name="m_lCurrencyID" id="m_lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>" />
		<input type="hidden" name="m_lClientID" value="<%=sessionMng.m_lClientID%>" />
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title  nowrap ><span class="txt_til2">������λ�����˻�</span></td>
			       <td class=title_right   width="17"><a class=img_title></td>
				</TR>
			</TABLE>
			
		<br/>
		
		<table width=100% border="0 cellpadding="0" cellspacing="1" class=normal id="table1">	
  		<TBODY>
  		<TR>
    		<TD height=63>      
      			<TABLE align=center border=0 height=80 width="90%" >
        			<TBODY> 
        				<TR>
        					  		
								<%
										long officeId = sessionMng.m_lOfficeID;
										long currencyId = sessionMng.m_lCurrencyID;
										String strFormName = "frmV090";
										String strCtrlName = "subclientId";
										strTitle = "������λ���";
										String strRtnAccountNameCtrlName = "";
										String strClientCode = "";
									//	long lParentClientID = clientId;
										String strFirstTD = "";// ��һ��TD������
										String strSecondTD = "";// �ڶ���TD������
										String[] strNextControls = new String[] { "subaccountIdCtrl" };

									/*	OBMagnifier
												.createChildClientCtrl(out, 
														officeId, 
														currencyId,
														strFormName, 
														strCtrlName, 
														strTitle,
														subclientId, 
														strClientCode, 
														clientId,
														strFirstTD, 
														strSecondTD, 
														strNextControls,
														strRtnAccountNameCtrlName);*/
								%>
								 <td width="15%" height="25" align="left">&nbsp;&nbsp;������λ��ţ�</td>
							  	 <td >
							
									<fs_c:dic id="subclientIdCtrl" size="22" form="frmV090" title="������λ���" sqlFunction="getChildClientSQL"  sqlParams='frmV090.m_lOfficeID.value,frmV090.m_lClientID.value,frmV090.subclientIdCtrl.value' value="<%=strClientCode%>" nextFocus="subaccountIdCtrl" width="500">
										<fs_c:columns> 
											<fs_c:column display="�ͻ����" name="clientNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="�ͻ�����" name="clientName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="subclientIdCtrl" name="clientNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subclientId" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subclientIdFromClient" name="FromClient" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
									<input type="hidden" value="" name="subclientId" value="<%=subclientId %>"/>
									<input type="hidden" value="" name="subclientIdFromClient" value="<%=subclientId > 0?1:0 %>"/>
							     </td>			
						</TR>
			    
						
					  <TR>						  
							<%
	  								strFormName = "frmV090";
	  									strCtrlName = "subaccountId";
	  									strRtnAccountNameCtrlName = "";
	  									long lAccountId = -1;
	  									String strAccountCode = "";
	  									strNextControls = new String[] { "transactionStartDateString" };

	  									//�Ŵ󾵹����ֶΣ�
	  									//�ͻ���� cong���ͻ���� ��������id������id������id������������id����֧����id���Ƿ�ֱ�����˻�����һ��������
	  									//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,
	  									//accountPropertyOne,Two,Three,isCheck,accountStatus
	  									String[] sConditions = new String[] { "subclientId",
	  											"countryId", null, null, null, null, "isDirectLink",
	  											null, null, null, "isCheck", "accountStatus" };

	  							/*		OBMagnifier.createSubAccountCtrlReturnCode(out, 
	  											strFormName,
	  											strCtrlName, 
	  											lAccountId, 
	  											strAccountCode,
	  											strRtnAccountNameCtrlName, 
	  											strNextControls,
	  											sConditions, 
	  											officeId, 
	  											clientId);*/
	  							%>
	  							 <td width="130" height="25" align="left">&nbsp;&nbsp;�����˺ţ�</td>
							  	 <td>
									<fs_c:dic id="subaccountIdCtrl" size="22" form="frmV090" title="�����˺�" sqlFunction="getSubAccountSQLForEbank"  sqlParams='frmV090.subaccountIdCtrl.value,frmV090.m_lClientID.value,frmV090.m_lOfficeID.value,frmV090.subclientId.value,frmV090.countryId.value,-1,-1,-1,-1,frmV090.isDirectLink.value,-1,-1,-1,frmV090.isCheck.value,frmV090.accountStatus.value' value="<%=strAccountCode%>" nextFocus="transactionStartDateString" width="650">
										<fs_c:columns> 
											<fs_c:column display="�˺�" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="�˻�����" name="AccountName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="��������" name="BankName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="subaccountIdCtrl" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subaccountId" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>	
									</fs_c:dic> 
									<input type="hidden" name="subaccountId" value="<%=String.valueOf(lAccountId) %>">									
							     </td>		
         				  </TR>						  
	  				  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
           </TR> 
		   <TR>
    		<TD height=20>      
      			<TABLE align=center border=0 height=20 width="95%">
        			<TBODY> 
					  <TR>
						<TD colspan="4"><hr></TD>
					  </TR>
					  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
          </TR>
		  
		  <TR>
    		<TD height=20>      
      			<TABLE align=center border=0 height=20 width="90%">
        			<TBODY> 
					  <TR>
						<TD width="10%"><font class=txt_red >* </font>��ѯ���ڣ�</TD>
						<TD width="5%" align="right">��</td>
						<td width="40%">
						 <fs_c:calendar 
			          	    name="transactionStartDateString"
				          	value="" 
				          	properties="nextfield ='transactionEndDateString'" 
				          	size="20"/>
							<script>
				          		$('#transactionStartDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo,
											"transactionStartDate", -1)%>');
				          	</script>
						</TD>
						<TD width="5%" align="right">��</td>
						<td width="40%">
							 <fs_c:calendar 
				          	    name="transactionEndDateString"
					          	value="" 
					          	properties="nextfield ='butSearch'" 
					          	size="20"/>
						    <script>
				          		$('#transactionEndDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo,"transactionEndDate", -1)%>');
				          	</script>
						</TD>
						<TD></TD>
					  </TR>
					  
					  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
          </TR>
		
		
        <TR>
            <TD colspan="3" align=right> 
              <input class=button1 type=button name="butSearch" value=" �� �� " onClick="doSearch();" onfocus="nextfield='submitfunction';">
            </TD>
			<TD align=right>&nbsp;</TD>
        </TR>
		<TR>
            <TD colspan="4" align=right>&nbsp;</TD>
        </TR>
		
	</TBODY>
</TABLE>
</td></tr></table>
</form>
<!--ҳ�������-->

<!--ҳ��ű���ʼ-->
<script language="JavaScript">
	firstFocus(document.frmV090.subclientIdCtrl);
	//setSubmitFunction("doSearch(frmV090)");
	setFormName("frmV090");
</script>

<script language="javascript">
function doSearch()
{
	if (!validateFields(frmV090)) return;
	if( !(compareDate(frmV090.transactionStartDateString.value,frmV090.transactionEndDateString.value)) )
	{
		alert("��ѯ��ʼ���ڲ��ܴ��ڲ�ѯ�������ڣ�");
		return false;
	}
	if( !(compareDate(frmV090.transactionEndDateString.value,frmV090.systemDate.value)) )
	{
		alert("�������ڲ��ܴ���ϵͳ���ڣ�");
		return false;
	}
	
	frmV090.action = "<%=strContext%>/bankportal/c090-subaccount_ebank.jsp"
	frmV090.submit();
}    
/**
 * ������ҪУ�����λ
 */
function allFields()
{
	this.ad = new Array("subclientId","�¼��ͻ����","magnifier",0);
	this.ah = new Array("subaccountId","�¼��˺�","magnifier",0);
	this.ai = new Array("transactionStartDateString","��ѯ��ʼ����","date",1);
	this.aj = new Array("transactionEndDateString","��ѯ��������","date",1);
}
</script>
<!--ҳ��ű�Ԫ�ؽ���-->
<%
	} catch (Exception exp) {
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//��ʾҳ��β
	OBHtml.showOBHomeEnd(out);
	/**ҳ����ʾ����*/
%>