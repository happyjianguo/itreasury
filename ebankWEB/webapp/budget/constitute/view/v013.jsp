<%--
 ҳ������ ��v001.jsp
 ҳ�湦�� : ģ�����ҳ��
 ��    �� ��xirenli
 ��    �� ��2004-07-15
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��

--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.budget.templet.dataentity.*" %>
<%@ page import="com.iss.itreasury.budget.util.*" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.setting.bizlogic.BudgetParameterOperation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--������Ϣ����ҳ��,��ҳ����Ե������ڵ���ʽ�����Ѿ���׽�����쳣-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--����js�ļ�-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />
<%
try
{
	String strTitle = "Ԥ�����";
	//�û���¼��� 
	if (sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
		out.flush();
		return;
	}

	// �ж��û��Ƿ���Ȩ�� 
	if (sessionMng.hasRight(request) == false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
		out.flush();
		return;
	}
	/* ��ʾ�ļ�ͷ */
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.YES);

	/**
	 * ��������
	 */
	long operatorId				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long currencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long officeId				= sessionMng.m_lOfficeID;			//���´�ID	
	long clientId				= sessionMng.m_lClientID;			//��λID

	
	String[] aryHeadDateString = null;
	Vector vTemplate = null;
	long lLevelCount = -1;
	String versionNo = "";
	Vector v = new Vector();
	String strTmp = null;
	
	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");
	
	vTemplate = (Vector)request.getAttribute("searchResult");
	strTmp = (String)request.getAttribute("versionNo");
	if (strTmp != null && strTmp.length() > 0)
		versionNo = strTmp;
	strTmp = (String)request.getAttribute("maxLevel");
	lLevelCount = Long.parseLong(strTmp);

	long[] tmp = info.getShowColumn();
	aryHeadDateString = new String[tmp.length];
	for (int i=0;i<tmp.length;i++)
	{
		aryHeadDateString[i] = BUDGETConstant.BudgetColumnList.getName(tmp[i]);
	}
	
	long[] aryLevelStatus = {1};//��һ��Ĭ����ʾ

%>
	<jsp:include page="/ShowMessage.jsp"/>
	
	<form name="frmV013" method="post" action="../control/c001.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="officeID" value="<%=officeId%>">
		<input type="hidden" name="currencyID" value="<%=currencyId%>">
		<input type="hidden" name="inputUserID" value="<%=operatorId%>">
		<input type="hidden" name="id" value="<%=info.getId()%>">
		<input type="hidden" name="hdnLevelCount" value="<%=lLevelCount%>">
		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="clientID" value="<%=clientId%>">
		<input type="hidden" name="showColumns" value="<%=tmp.length%>">
			
		
		<table width="80%" border="0" class="top" height="100">
			<tr>
				<td height="2" class="FormTitle" width="100%" colspan="2"><b>Ԥ����Ƽ����� - Ԥ�����</b>
				</td>
			</tr>
			
			<tr>
				<td height="5" width="100%" valign="bottom" colspan="2">
				<hr>
<%
	//��ʾģ����
	BUDGETHTML.showBudgetTemplate(out,aryHeadDateString,vTemplate,lLevelCount,aryLevelStatus);
%>
				</td>
			</tr>
			<tr>
				<td height="5" width="100%" valign="bottom" colspan="2">
					<table width="97%" border="0" cellspacing="2" cellpadding="2" height="15" align="center">
						<tr>
							<td height="19" colspan="3">
								<div align="right">		
								<%System.out.println("info.getStatusID()="+info.getStatusID());%>
								<%if (info.getStatusID()<0 
											||info.getStatusID() == BUDGETConstant.ConstituteStatus.SAVE
											||info.getStatusID() == BUDGETConstant.ConstituteStatus.RETURN){%>
									<input type="button" name="commit" value=" �� �� " class="button" onClick="doCommit(frmV013);">
									<%}%>
									<input type="button" name="back" value=" �� �� " class="button" onClick="doBack(frmV013);">
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>


<script language="javascript">

function compute(form)
{
	if (<%=tmp.length%>==1)
	{
		alert("����ѡ��ϵ�������ڵ���");
		return false;
	}
	if (form.quotiety.value == "")
	{
		alert("����д����ϵ��");
		form.quotiety.focus();
		return false;
	}
	else
	{
		if (isNaN(form.quotiety.value))
		{
			alert("����ϵ������Ϊ����");
			form.quotiety.focus();
			return false;
		}
		else
		{
			if (form.quotiety.value <= 0)
			{
				alert("����ϵ���������0");
				form.quotiety.focus();
				return false;
			}
		}
	}
	for (var i=0;i<form.hdnItemNo.length;i++)
	{
		eval("form.txtAmount1_"+form.hdnItemNo[i].value+".value = (form.txtAmount0_"+form.hdnItemNo[i].value+".value * form.quotiety.value)");
	}

}
function doBack(form)
{	
	showSending();
	document.location.href="../view/v011.jsp";		
}

function doCommit(form)
{	
	if (confirm("ȷ���ύ?"))
	{
		showSending();
		form.action="../control/c011.jsp";
		form.strSuccessPageURL.value="../view/v011.jsp";	//��������ɹ���������ҳ��
		form.strFailPageURL.value="../view/v011.jsp";		//����ʧ�ܺ�������ҳ��
		form.strAction.value="<%=Constant.Actions.COMMIT%>";	//�����������
		form.submit();
	}
}

function showColumn()
{
	if (frmV013.show.checked==true)
	{
		for (var i=0;i<frmV013.showcolumn.length;i++)
		{
			if (frmV013.showcolumn[i].checked==true)
			{
				dosubmit(frmV013);
				break;
			}
		}
	}
}
function dosubmit(form_1)
{
	showSending();
	form_1.action="../control/c011.jsp";
	form_1.strSuccessPageURL.value="../view/v002.jsp";	//��������ɹ���������ҳ��
	form_1.strFailPageURL.value="../view/v001.jsp";		//����ʧ�ܺ�������ҳ��
	form_1.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//�����������
	form_1.submit();
}

</script>
<% 
	/**
	* ��ʵ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);	
}
catch (Exception ex)
{
	System.out.println(ex.toString());
	ex.printStackTrace();
}
%>
<%@ include file="/common/SignValidate.inc" %>