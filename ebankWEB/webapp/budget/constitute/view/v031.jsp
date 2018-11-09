<%--
 ҳ������ ��v031.jsp
 ҳ�湦�� : �¼���λԤ����ܽ���
 ��    �� ��weilu
 ��    �� ��2005-8-1
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--����js�ļ�-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />
<!-- ����ҳ����Ʋ��� -->
<%
try
{
	String strTitle = "�¼���λԤ�����";
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

	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");

	/**
	 * ��������
	 */
	long operatorId				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long currencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long officeId				= sessionMng.m_lOfficeID;			//���´�ID
	long clientId				= sessionMng.m_lClientID;			//��λID

	%>
	<form name="frmV021" method="post" action="../control/c031.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="officeID" value="<%=officeId%>">
		<input type="hidden" name="currencyID" value="<%=currencyId%>">
		<input type="hidden" name="nextCheckUserID" value="<%=operatorId%>">
		<input type="hidden" name="budgetSystemID" value="">
		<input type="hidden" name="budgetPeriodID" value="">
		<input type="hidden" name="startDate" value="">
		<input type="hidden" name="endDate" value="">
		<input type="hidden" name="remark" value="">
		<input type="hidden" name="budgetFlag" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="versionNo" value="">
		<input type="hidden" name="clientID" value="<%=clientId%>">

	<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>Ԥ����Ƽ����� - �¼���λԤ�����</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                 <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">Ԥ����ϵ</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">Ԥ������</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">Ԥ�����俪ʼ����</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">Ԥ�������������</div>
                   </td>
				    <td height="20" nowrap class="ItemTitle">
                     <div align="center">�汾��</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">״̬</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">��������</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">������</div>
                   </td>
					<td height="20" nowrap class="ItemTitle">
                     <div align="center"></div>
                   </td>
				</tr>
				<%
		Collection coll =(Collection)request.getAttribute("searchResult");
			if(coll!=null && coll.size() > 0){
				for(Iterator iter =coll.iterator();iter.hasNext();)
				{
					BudgetPlanInfo planInfo=(BudgetPlanInfo)iter.next();
						%>
						<TR class=ItemBody>
						   <td height="20">
							 <div align="center"><%=planInfo.getBudgetSystemName()%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=planInfo.getBudgetPeriodName()%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=DataFormat.getDateString(planInfo.getStartDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=DataFormat.getDateString(planInfo.getEndDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=planInfo.getVersionNo()%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=BUDGETConstant.ConstituteStatus.getName(planInfo.getStatusID())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=DataFormat.getDateString(planInfo.getConstituteDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=NameRef.getUserNameByID(planInfo.getInputUserID())%></div>
						   </td>
						    <td height="20">
							 <div align="center"><a href="#" onclick="doReceive(<%=planInfo.getBudgetSystemID()%>,<%=planInfo.getBudgetPeriodID()%>,'<%=planInfo.getStartDate()%>','<%=planInfo.getVersionNo()%>','<%=planInfo.getEndDate()%>','<%=planInfo.getBudgetFlag()%>','<%=planInfo.getId()%>');">����</a></div>
						   </td>
						</tr>
<%
				}
			}else{
%>
			<TR class=ItemBody>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			    <td height="20">
				 <div align="center"></div>
			   </td>
			</tr>
<%}%>
  </TBODY></TABLE></TD></TR></TBODY></TABLE></form>
<!-- Javascript���� -->
<script language="JavaScript">
function doReceive(systemID,periodID,startDate,versionNo,endDate,flag,id){
	if (confirm("ȷ�Ͻ���?"))
	{
		 showSending();//��ʾ����ִ��
		 frmV021.budgetSystemID.value = systemID;
		 frmV021.budgetPeriodID.value = periodID;
		 frmV021.startDate.value = startDate;
		 frmV021.endDate.value = endDate;
		 frmV021.versionNo.value = versionNo;
		 frmV021.budgetFlag.value = flag;
		 frmV021.id.value = id;
		
		frmV021.strSuccessPageURL.value="../control/c021.jsp?strSuccessPageURL=../view/v021.jsp&strFailPageURL=../view/v021.jsp&strAction=7&statusID=2";	//��������ɹ���������ҳ��
		frmV021.strFailPageURL.value="../view/v031.jsp";		//����ʧ�ܺ�������ҳ��
		frmV021.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//�����������
		frmV021.submit();
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
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>