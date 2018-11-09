<%--
/**ҳ�湦��˵��
 * ҳ������ ��v091.jsp
 * ҳ�湦�� : ���˵���ӡ
 * ��    �� ��gqfang
 * ��    �� ��2005-06-05
 * ��ʵ��˵����
 *				1���˻���Ϣ�б�
 * ����˵�� ��
 * �޸���ʷ ��
 */
--%>

<%@ page contentType = "text/html;charset=GBK" %>
<jsp:directive.page import="com.iss.itreasury.util.Constant.PageControl"/>
<jsp:directive.page import="com.iss.itreasury.util.Env"/>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    try
	{
		//emoduleid����6��������ģ��
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
       // 		out.flush();
		//		return;
		//}
		
		//��ҳ��Ϣ
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
		String strTitle = null;
		//�û���¼��� 
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
        }
        
		/**ҳ��У�鿪ʼ���û���¼У�顢�û�Ȩ��У�顢�ظ�����У�飩*/
		JSPLogger.info("*******����ҳ��--bankportal\\v091-account_ebank.jsp*******");
		/**ҳ��У�����*/
		
		/**ҵ���߼���ʼ*/
		
		String strContext = request.getContextPath();
		//��ҳ���Ʋ���
		String strTemp            = "";
		long   orderField         = -1;
		long   desc               = PageControl.CODE_ASCORDESC_ASC;
		
		//���PageLoader
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		JSPLogger.info("Result Page ::��strPageLoaderKey : " + strPageLoaderKey);
		
		
		//ǿ��ת��
		Object[] queryResults = null;
		queryResults = (QueryBillPrintInfo[])request.getAttribute(PageControl.SearchResults);
		JSPLogger.info("�˻�������ϸ    queryResults :  " + queryResults + " length:" + ((queryResults != null)?queryResults.length:-1));
		
		long clientId = -1;
		clientId = sessionMng.m_lClientID;
		/**ҵ���߼�����*/
		
		//�ж�����ͷ���
		strTemp = (String)request.getParameter("desc");
		if( strTemp != null && strTemp.length() > 0 )
		{
		     desc = Long.parseLong(strTemp.trim());
		}
		
		//�õ���ѯ����
		String bankType = "";
		String queryStartDate = "";
		String queryEndDate   = "";
		strTemp = (String)request.getParameter("transactionStartDateString");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryStartDate = strTemp;
		}
		strTemp = (String)request.getParameter("transactionEndDateString");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryEndDate = strTemp;
		}
		strTemp = (String)request.getAttribute("bankType");
		if( strTemp != null && strTemp.length() > 0 )
		{
		     bankType = strTemp;
		}
			
		//����paramInfo����
		QueryBillPrintParam paramInfo  =(QueryBillPrintParam)request.getAttribute("queryInfo");
		request.setAttribute("queryInfo", paramInfo);

		/**ҳ����ʾ��ʼ*/
       // HTMLHelper.showHomeHead(out,sessionMng,"���˵���ӡ",BooleanValue.TRUE);//��ʾҳ��ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>


<!--��ѯ��������ʼ-->
<!--ҳ�����ʼ-->
<form name="frmV091" method="post" action="">
<!--ҳ�����Ԫ�ؿ�ʼ-->
	<input name="p_Action" type="hidden" value="find">
	<input name="strAction" type="hidden" value="">
	<input type="hidden" name="pageLoaderKey" value="<%=strPageLoaderKey%>">
	<input type="hidden" name="orderField" value="<%=orderField%>">
	<input type="hidden" name="pageLoaderInfo_rowPerPage" value="">	
	<input type="hidden" name="pageLoaderInfo_pageNo" value="">
	<input type="hidden" name="strOrderBy" value="">
	<input type="hidden" name="transactionStartDateString" value="<%=queryStartDate%>">
	<input type="hidden" name="transactionEndDateString"  value="<%=queryEndDate%>">	
	<input type="hidden" name="desc" value="<%=desc%>">
	<input name="systemDate" type="hidden" value="<%=Env.getSystemDateString()%>">
	<input name="isCheck" type="hidden" value="1"/>
	<input name="isDirectLink" type="hidden" value="1"/>		
	<input name="accountStatus" type="hidden" value="1"/>	
	<input name="clientId" type="hidden" value="<%=clientId%>"/>	
	<input name="lUserID" type="hidden" value="<%=sessionMng.m_lUserID %>"/>
	<input name="lCurrencyID" type="hidden" value="<%=sessionMng.m_lCurrencyID %>"/>
	<input name="lOfficeID" type="hidden" value="<%=sessionMng.m_lOfficeID %>"/>
	<input name="bankType" type="hidden" value="<%=paramInfo.getBankType() %>"/>
	<input name="accountId" type="hidden" value="<%=paramInfo.getAccountId() %>"/>
	
	<!--ҳ�����Ԫ�ؽ���-->
	<table width="98%" cellpadding="0" cellspacing="0" class="title_top">
		<tr>
			<td height="24">
				<table cellspacing="0" cellpadding="0" >
					<TR>
				       <td class=title><span class="txt_til2">�˻���Ϣ</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
				<br/>
				<table width=100% border="0"  cellpadding="0" cellspacing="0" class="top">
					<tbody>
					<TR>
					    <td width="2%">&nbsp;</td>
						<TD>
							<br><TABLE width="100%" id="flexlist"></TABLE><br>
						</TD>
					    <td width="2%">&nbsp;</td>
					</TR>
					</tbody>
					</table>
					<TR>
						<TD>
							<TABLE width="97%" align="center">
								<TBODY>
							        <TR borderColor=#ffffff>
							            <TD colspan="9" align=right> 
										  <input class=button1 type=button name="butBack" value=" �� �� " onClick="doBack()" >
							            </TD>
							        </TR>
								</TBODY>
					        </TABLE>
						</TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
</form>

<script language="JavaScript">
	setFormName("frmV091");
</script>

<script language="javascript">
$(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '�ͻ����',  name : 'clientId', width : 120, sortable : true, align: 'center'},
			{display: '�ͻ�����',  name : 'clientId', width : 120, sortable : true, align: 'center'},
			{display: '����',  name : 'bankId', width : 100, sortable : true, align: 'center'},
			{display: '����',  name : 'countryId', width : 100, sortable : true, align: 'center'},
			{display: '����',  name : 'currencyType', width : 100, sortable : true, align: 'center'},
			{display: '�˺�', name: 'accountNo', elType : 'link', elName : 'sTransNoLink', methodName : 'toDetail("?","?","?","?")', width: 135, sortable: true, align: 'center'},
			{display: '�˻�����',  name : 'accountName', width : 100, sortable : true, align: 'center'},
			{display: '���ڷ�Χ',  name : 'balanceStartDate', width : 120, sortable : true, align: 'center'},
			{display: '���',  name : 'balance', width : 120, sortable : true, align: 'center'}
		],//�в���
		title:'�˻����˵�',
		classMethod : 'com.iss.itreasury.ebank.system.action.BillPrintAction.queryBillPrintInfo',//Ҫ���õķ���
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
		userFunc : getFormData
	});
});
function getFormData() 
{
	return $.addFormData("frmV091","flexlist");
}
function toDetail(acctId,queryStartDate,queryEndDate,bankType)
{	
	window.open('<%=strContext%>/bankportal/c091-account_ebank.jsp?p_SuccessPageURL=/bankportal/v092-account_ebank.jsp&p_FailPageURL=/bankportal/v091-account_ebank.jsp&p_Action=findById&accountId='+acctId+'&transactionStartDateString='+queryStartDate+'&transactionEndDateString='+queryEndDate+'&bankType='+bankType);
}

function doBack()
{
	history.back();
}
</script>
<!--ҳ��ű�Ԫ�ؽ���-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//��ʾҳ��β
	OBHtml.showOBHomeEnd(out);
	/**ҳ����ʾ����*/
%>
<jsp:include page="/ShowMessage.jsp"/>