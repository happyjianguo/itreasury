<%--
 ҳ������ ��v002.jsp
 ҳ�湦�� : Ԥ��ִ�����������ѯ�����ҳ��
 ��    �� ��liuyang
 ��    �� ��
 ����˵�� ��
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
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.BudgetItemPrivilegeInfo"%>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>


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

<!-- ����ҳ����Ʋ��� -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<!-- ����ҵ���߼����� -->

<%
try
{
	//������
	/** Ȩ�޼�� **/
	String strTableTitle = "ϵͳ���� - Ԥ��ִ���������";
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
	OBHtml.showOBHomeHead(out,sessionMng,"[Ԥ��ִ���������]",Constant.YesOrNo.YES);

	/**
	 * ��������
	 */
	long operatorId				= sessionMng.m_lUserID;				//��ǰ�����û�ID
	long currencyId				= sessionMng.m_lCurrencyID;			//��ǰϵͳʹ�ñ���ID
	long officeId				= sessionMng.m_lOfficeID;			//���´�ID	
	/**
	* ����ҵ�����
	*/
	%>

	<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		�������� - Ԥ��ִ���������</B></TD>
         </TR>
         <TR>
           <TR>

           <TD height=10 vAlign=top width="100%">
             <table width="100%" border="0" cellspacing="1" class="ItemList">
<tr class="ItemTitle">
<td height="20" width="20%"> <div align="center">Ԥ����Ŀ��� </div></td>
<td width="20%"> <div align="center">Ԥ����Ŀ����</div></td>
<td width="10%"> <div align="center">ִ������</div></td>
<td width="10%"> <div align="center">���׺�</div></td>
<td width="10%"> <div align="center">ִ�н��</div></td>
<td width="10%"> <div align="center">Ʊ�ݺ���</div></td>
<td width="20%"><div align="center">ժҪ</div></td>


</tr>
<%
	/*
resuInfo.setItemNo(rs.getString("ItemNo"));
				resuInfo.setItemName(rs.getString("ItemName"));
				resuInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				resuInfo.setTransNo(rs.getString("TransNo"));
				resuInfo.setExecuteAmout(rs.getDouble("ExcuteAmount"));
				resuInfo.setContractCode(rs.getString("ContractCode"));
				resuInfo.setRemark(rs.getString("remark"));
*/
	Collection detailColl=null;
	detailColl=(Collection)request.getAttribute("detailColl");
	for(Iterator iter =detailColl.iterator();iter.hasNext();){
		QBudgetResultInfo info=(QBudgetResultInfo)iter.next();
		out.println("<tr class=ItemBody><td>"+info.getItemNo()+"</td>");
		out.println("<td>"+info.getItemName()+"</td>");
		out.println("<td align=center>"+info.getExecuteDate().toString().substring(0,10)+"</td>");
		out.println("<td align=left>"+info.getTransNo()+"</td>");	
		out.println("<td align=right>"+info.getExecuteAmout()+"</td>");
		out.println("<td align=left>"+info.getContractCode()+"</td>");
		if(info.getRemark()!=null){
			out.println("<td align=left>"+info.getRemark()+"</td></tr>");
		}else{
			out.println("<td align=right>&nbsp;</td></tr>");
		}
		
	}
	request.removeAttribute("detailColl");
%>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom>           <TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right><input name="Submit32" type="button" class="button" onClick="location.href='../control/c321.jsp';" value=" �� �� ">&nbsp;&nbsp;</DIV></td>
               </TR>
             </TBODY>
           </TABLE></TD>
         </TR>          
       </TBODY>
    </TABLE>
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