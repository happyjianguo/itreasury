<%--
 ҳ������ ��v001.jsp
 ҳ�湦�� : Ԥ�����
 ��    �� ��weilu
 ��    �� ��2005-7-13
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

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

	
	/**
	* ����ҵ�����
	*/
	long budgetSystemID = -1;
	long budgetPeriodID = -1;
	Timestamp startDate = null;
	Timestamp endDate = null;
	String remark="";
	
	/** ȡ��dataentity,��������ֵ */
	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");
	if (info != null)
	{
		budgetSystemID = info.getBudgetSystemID();
		budgetPeriodID = info.getBudgetPeriodID();
		startDate = info.getStartDate();
		endDate = info.getEndDate();
		remark = info.getRemark();
	}
%>
<!--������Ϣ����ҳ��,��ҳ����Ե������ڵ���ʽ�����Ѿ���׽�����쳣-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--����js�ļ�-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<!--form ��ʼ-->
<form name="form_1" method=post action="../control/c001.jsp"  >
<!-- ����ҳ����Ʋ��� -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<input type="hidden" name="officeID" value="<%=officeId%>">
<input type="hidden" name="currencyID" value="<%=currencyId%>">
<input type="hidden" name="budgetFlag" value="<%=BUDGETConstant.BudgetFlag.CONSTITUTE%>">
<!-- ����ҵ���߼����� -->
<input type=hidden name=clientID value=<%=clientId%>>

<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>Ԥ����Ƽ����� - Ԥ�����</B></TD>
         </TR>
         <TR>
         <TD width="100%" vAlign=bottom>           <TABLE width="100%" border=0 align=center>
<TR>
<%
		String strFormNamePCS = "form_1";
		String strCtrlNamePCS = "budgetSystemID";
		String strTitlePCS = "<font color=red>*</font> Ԥ����ϵ";
		long systemIDPCS = -1;
		String strSystemNoPCS = "";
		String strFirstTDPCS = "";
		String SecondTDPCS = "";
		String[] sNextControlsPCS = {"budgetPeriodIDCtrl"};
		String strRtnClientNameCtrlPCS = "";
		
	BUDGETMagnifier.createBudgetSystemCtrl(
		out,
		officeId,
		currencyId,
		strFormNamePCS,
		strCtrlNamePCS,
		strTitlePCS,
		systemIDPCS,
		strSystemNoPCS,
		strFirstTDPCS,
		SecondTDPCS,
		sNextControlsPCS,
		strRtnClientNameCtrlPCS);
 %>

<TD width="20%" height=23> Ԥ�㵥λ��</TD>
<TD width="30%" height=23><input type="text" name="lClientIDCtrl2" class="box" maxlength='30' disabled value="<%=BUDGETNameRef.getClientNameByID(clientId)%>"></TD>
</TR>
<TR>
<%
		String strFormNamePCS2 = "form_1";
		String strCtrlNamePCS2 = "budgetPeriodID";
		String strTitlePCS2 = "<font color=red>*</font> Ԥ������";
		long lClientIDPCS2 = -1;
		String strClientNoPCS2 = "";
		String strFirstTDPCS2 = "";
		String SecondTDPCS2 = "";
		String[] sNextControlsPCS2 = {"startDate"};
		String strRtnClientNameCtrlPCS2 = "";
		
	BUDGETMagnifier.createBudgetPeriodCtrl(
		out,
		officeId,
		currencyId,
		strFormNamePCS2,
		strCtrlNamePCS2,
		strTitlePCS2,
		lClientIDPCS2,
		strClientNoPCS2,
		strFirstTDPCS2,
		SecondTDPCS2,
		sNextControlsPCS2,
		strRtnClientNameCtrlPCS2);
 %>
 <td ></td>
<td>
</td>
</TR>
<tr>
<td ><font color=red>*</font> Ԥ�����䣺��</td>
<td>
 <fs_c:calendar 
  	name="startDate"
   	value="" 
   	properties="javascript:getEndDate();nextfield='endDate';" 
   	size="20"/>
				          	<!-- 
				<input type="text" name="startDate" size="20" maxlength="30" class="box" onFocus="javascript:getEndDate();nextfield='endDate';" onblur="javascript:getEndDate();">&nbsp;
				<A href="javascript:show_calendar('form_1.startDate');" onMouseOut="window.status='';return true;"onMouseOver="window.status='Date Picker';return true;"><IMG border="0" height="16"src="/websett/image/calendar.gif" width="17"></A>
				 -->
 </td>
<td >����</td>
<td><input type="text" name="endDate" size="20" maxlength="30" class="box" onFocus="nextfield='remark';" readonly>&nbsp;
</td>
</TR>

<TR>
<td >Ԥ��˵����</td>
<td colspan="3"><input name="remark" type="text" class="box" value='<%=remark%>' size="50"  maxlength='30' onFocus="nextfield='submitfunction';"></td>
</TR>
             <TBODY>
             </TBODY>
           </TABLE></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
				   <input class="button" name="next" type="button" value=" ��һ�� " onClick="javascript:doNext();">
				   <input class="button" name="search" type="button" value=" �� �� " onClick="javascript:doSearch();">
                     <!--input class="button" name="Submit322" type="button" value=" �� �� " onClick="location.href='2Ԥ�����1.htm';"-->
                     					 
                   </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
         </TD>
         </TR>
       </TBODY>
    </TABLE>
</form>

<!-- Javascript���� -->
<script language="JavaScript">
$('#startDate').blur(function (){
	getEndDate();
});
//�趨ҳ�潹��
firstFocus(document.form_1.budgetSystemIDCtrl);
//�趨form����
setFormName("form_1"); 
//�趨�س��Զ�ִ�ж���
//setSubmitFunction("doNext()");

//ҵ�����
function doSearch()
{
	showSending();//��ʾ����ִ��
	form_1.strSuccessPageURL.value="../view/v003.jsp";	//��������ɹ���������ҳ��
	form_1.strFailPageURL.value="../view/v001.jsp";		//����ʧ�ܺ�������ҳ��
	form_1.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//�����������
	form_1.submit();
}
//ҵ�����
function doNext()
{
	//�������������
	if (!validateFields(form_1)) return;
	//�ύ����
	if (confirm("ȷ����һ��?"))
	{
		showSending();//��ʾ����ִ��
		form_1.strSuccessPageURL.value="../view/v002.jsp";	//��������ɹ���������ҳ��
		form_1.strFailPageURL.value="../view/v001.jsp";		//����ʧ�ܺ�������ҳ��
		form_1.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//�����������
		form_1.submit();
	}
}
//Form����Ҫ����������Ŵ�
function allFields()
{			
		this.aa = new Array("budgetSystemID","Ԥ����ϵ","magnifier",1);	
		this.bb = new Array("budgetPeriodID","Ԥ������","magnifier",1);
		this.cc = new Array("startDate","Ԥ�����俪ʼ����","date",1);
		this.dd = new Array("endDate","Ԥ�������������","date",1);
}
function getEndDate()
{
var strStartDate = form_1.startDate.value;
if (strStartDate != "" && form_1.BudgetPeriodType.value != "-1")
	{
		var startDate = new Date(strStartDate.substring(0,4),new Number(strStartDate.substring(5,7)),new Number(strStartDate.substring(8,10)));
		var newDate = startDate;
		var periodType = form_1.BudgetPeriodType.value;
		if (periodType == <%=BUDGETConstant.BudgetPeriod.Y%>)
		{
			newDate.setYear(startDate.getYear()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.H%>)
		{
			newDate.setMonth(startDate.getMonth()+6); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.Q%>)
		{
			newDate.setMonth(startDate.getMonth()+3); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.M%>)
		{
			newDate.setMonth(startDate.getMonth()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.T%>)
		{
			newDate.setDate(startDate.getDate()+10); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.W%>)
		{
			newDate.setDate(startDate.getDate()+7); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.D%>)
		{
			newDate.setDate(startDate.getDate()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.X1%>)
		{
			newDate.setDate(startDate.getDate()+14); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.X2%>)
		{
			newDate.setDate(startDate.getDate()+ new Number(form_1.periodDays.value)); 
		}
		var year = newDate.getYear();
		var month = newDate.getMonth();
		var day = newDate.getDate();
		if (month==0){month=12;year--;}
		if (month<10)month="0"+month;
		if (day<10)day="0"+day;
		form_1.endDate.value = year + "-" + month + "-" + day;
	}
}
</script>
<%	
	/**
	* ��ʵ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);	
}
//�쳣����
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>