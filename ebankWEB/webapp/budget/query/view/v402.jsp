<%--
 ҳ������ ��v402.jsp
 ҳ�湦�� : Ԥ��������ҷ��ؽ����
 ��    �� ��xrli
 ��    �� ��2005-8-23
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

<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>

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
	if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	
	/** ��ʾ�ļ�ͷ **/
	OBHtml.showOBHomeHead(out,sessionMng,"[Ԥ�������ѯ]",Constant.YesOrNo.YES);

	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");
	/**
	 * ��������
	 */
	long operatorId				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long currencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long officeId				= sessionMng.m_lOfficeID;			//���´�ID
	long clientId				= sessionMng.m_lClientID;			//��λID

	
	

	%>
	<form name="frmV003" method="post" action="../control/c401.jsp">
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
		<input type="hidden" name="planID" value="">
		<input type="hidden" name="clientID" value="<%=clientId%>">
	<TABLE width="80%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>�������� - Ԥ�������ѯ</B></TD>
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
							 <div align="center"><a href="#" onclick="find(<%=planInfo.getBudgetSystemID()%>,<%=planInfo.getBudgetPeriodID()%>,'<%=planInfo.getStartDate()%>','<%=planInfo.getVersionNo()%>',<%=planInfo.getId()%>,'<%=planInfo.getEndDate()%>');"><%=planInfo.getVersionNo()%></a></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=BUDGETConstant.ConstituteStatus.getName(planInfo.getStatusID())%></div>
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
			 
			</tr>
<%}%>
               </TBODY>
             </TABLE>
			 <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
					<input class="button" name="next" type="button" value=" �� �� " onClick="javascript:location.href='../view/v401.jsp'">
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
function find(systemID,periodID,startDate,versionNo,planId,endDate){
	showSending();//��ʾ����ִ��
	 frmV003.budgetSystemID.value = systemID;
	 frmV003.budgetPeriodID.value = periodID;
	 frmV003.startDate.value = startDate;
	  frmV003.endDate.value = endDate;
	  frmV003.planID.value = planId;

	frmV003.strSuccessPageURL.value="../view/v403.jsp";	//��������ɹ���������ҳ��
	frmV003.strFailPageURL.value="../view/v401.jsp";		//����ʧ�ܺ�������ҳ��
	frmV003.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//�����������
	frmV003.submit();
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