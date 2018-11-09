<%--
 ҳ������ ��v014.jsp
 ҳ�湦�� : Ԥ����Ʋ��ҷ��ؽ����
 ��    �� ��weilu
 ��    �� ��2005-7-14
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
	
	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");

	/**
	 * ��������
	 */
	long operatorId				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long currencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long officeId				= sessionMng.m_lOfficeID;			//���´�ID
	long clientId				= sessionMng.m_lClientID;			//��λID

	%>
	<form name="frmv014" method="post" action="../control/c011.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="startDate" value="<%=info.getStartDate()%>">
		<input type="hidden" name="endDate" value="<%=info.getEndDate()%>">
		<input type="hidden" name="remark" value="<%=info.getRemark()%>">
		<input type="hidden" name="versionNo" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="clientID" value="<%=clientId%>">
	<TABLE width="80%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>Ԥ����Ƽ����� - Ԥ�����</B></TD>
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
                     <div align="center">������</div>
                   </td>

				</tr>
				<%
		Collection coll =(Collection)request.getAttribute("searchResult");
			if(coll!=null){
				for(Iterator iter =coll.iterator();iter.hasNext();)
				{
					BudgetPlanInfo planInfo=(BudgetPlanInfo)iter.next();
						%>
						<TR class=ItemBody>
						   <td height="20">
							 <div align="center"><a href="#" onclick="find(<%=planInfo.getBudgetSystemID()%>,<%=planInfo.getBudgetPeriodID()%>,'<%=planInfo.getStartDate()%>','<%=planInfo.getVersionNo()%>',<%=planInfo.getId()%>,'<%=planInfo.getEndDate()%>');"><%=planInfo.getBudgetSystemName()%></a></div>
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
							 <div align="center"><%=NameRef.getUserNameByID(planInfo.getInputUserID())%></div>
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
			</tr>
<%}%>
               </TBODY>
             </TABLE></TD></TR></TBODY></TABLE></form>
			 <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
					<input class="button" name="next" type="button" value=" �� �� " onClick="javascript:location.href='../view/v011.jsp'">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
        

<!-- Javascript���� -->
<script language="JavaScript">
function find(systemID,periodID,startDate,versionNo,planId,endDate){
	showSending();//��ʾ����ִ��
	 frmv014.budgetSystemID.value = systemID;
	 frmv014.budgetPeriodID.value = periodID;
	 frmv014.startDate.value = startDate;
	  frmv014.endDate.value = endDate;
	 frmv014.versionNo.value = versionNo;
	  frmv014.id.value = planId;
	frmv014.strSuccessPageURL.value="../view/v013.jsp";	//��������ɹ���������ҳ��
	frmv014.strFailPageURL.value="../view/v011.jsp";		//����ʧ�ܺ�������ҳ��
	frmv014.strAction.value="<%=Constant.Actions.LINKSEARCH%>";	//�����������
	frmv014.submit();
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