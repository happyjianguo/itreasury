<%--
 ҳ������ ��remindAutoTaskFail.jsp
 ҳ�湦�� : �Զ�����-�Զ�����ʧ�ܲ�ѯ-���Ѵ���
 ��    �� ��
 ��    �� ��
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
				com.iss.itreasury.ebank.obbudget.bizlogic.*,
				com.iss.itreasury.ebank.obbudget.dataentity.*,
				com.iss.itreasury.util.*" 
%>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<script language="JavaScript" src="/webob/js/Control.js" ></script>
	<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
	<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
	<script language="JavaScript" src="/webob/js/Check.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">


<% 
try
{
	//������
    OBHtml.validateRequest(out,request,response);
%>

<%
	
%>

<!-- #EndLibraryItem -->
<table width="100%" border="0" cellpadding="15" cellspacing="0">
 <tr>
	<td><form name="frm" method="post" action="">

     <TABLE width="100%" border="1" cellspacing="2" cellpadding="2" align="center">
       <TBODY>
         <TR>
            <TD class=ItemTitle height=2 width="100%"><B> �Զ�����ʧ��</B></TD>
         </TR>
         <TR>
         <TD width="100%" vAlign=bottom>          
          <TABLE width="100%" border="0" cellspacing="2" cellpadding="2" class=normal align=center>
                               <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">�ÿ�����</div>
                   </td>
                   <TD nowrap height=20 align="center"  class=ItemTitle>�ÿ��˻�</TD>
                   <TD nowrap height=20 align="center"  class=ItemTitle>�ÿ��˺�</TD>
                   <td nowrap class="ItemTitle" align="center" >�ÿ�����</td>
                   <td nowrap class="ItemTitle" align="center" >�ÿ��������</td>
                   <td nowrap class="ItemTitle" align="center" >�ÿ���</td>
                   <td nowrap height="20" class="ItemTitle" align="center" >������</td>
                   <td nowrap class="ItemTitle" align="center">����ʱ��</td>
                   <td nowrap class="ItemTitle" align="center">״̬</td>
                   <td nowrap class="ItemTitle" align="center">�������</td>
                   <td nowrap class="ItemTitle" align="center">��������</td>
                   </TR>
                <%
                Collection con=null;
            	OBBudgetBiz biz = new OBBudgetBiz();
            	OBBudgetInfo info=new OBBudgetInfo();
            	info.setOfficeId(sessionMng.m_lOfficeID);
            	info.setCurrencyId(sessionMng.m_lCurrencyID);
				info.setClientID(sessionMng.m_lClientID);
				info.setStatus(OBConstant.OBBudgetStatus.FAILEDDEAL);
            	con=biz.findByCondition(info);
                if(con==null){
                	%>
                	<tr class="ItemBody">
         				<td colspan="11">�޼�¼</td>
                	</tr>
                	<%
                }
                else{
                	Iterator it=con.iterator();
                	while(it.hasNext()){
                		OBBudgetInfo info2=(OBBudgetInfo)it.next();
                		%>
                		<TR>
                   <td class="ItemBody" height="20" align="center" >
				   <%=DataFormat.formatString(info2.getSname())%> </td>
				   <td class="ItemBody" height="20" align="center" nowrap><%= !(info2.getSname().trim().length()>0)?"":com.iss.itreasury.settlement.util.NameRef.getAccountNameByID(info2.getAccountID())%></td>
                   <td class="ItemBody" height="20" align="center" nowrap><%= !(info2.getSname().trim().length()>0)?"":NameRef.getBankAcctNameByAcctID(info2.getAccountID())%></td>
                   <td class="ItemBody" height="20" align="center"><%
                   if(info2.getSname().trim().length()>0)
                   {
                   		out.print(info2.getStartdate().toString().substring(0,10));
                   }
                   %></td>
                   <td class="ItemBody" height="20" align="center"><%=DataFormat.formatString(info2.getAdjustdate()==null?"":info2.getAdjustdate().toString().substring(0,10))%></td>
                   <td class="ItemBody" height="20" align="center"><%=DataFormat.formatAmount(info2.getAmount())%></td>                   
                   <td class="ItemBody" align="center" height="20"><%=DataFormat.formatString( NameRef.getUserNameByID(info2.getInputuser()))%></td>
                   <td class="ItemBody" align="center" height="20"><%=DataFormat.formatString(info2.getInputdate().toString().substring(0,10))%></td>
                   <td class="ItemBody" align="center" height="20"><%=DataFormat.formatString(OBConstant.OBBudgetStatus.getName(info2.getStatus()))%></td>
                    <td class="ItemBody" align="right" height="20"><%=DataFormat.formatString(info2.getRefusereason())%></td>
				   <td class="ItemBody"  align="left" height="20"><%
				   if(info2.getCheckuser() > 0)
				   {
				   		out.print(NameRef.getUserNameByID(info2.getCheckuser()));
				   }
				   %></td>
                 </TR>
                		<% 
                	}
                }
                %>
          </TABLE>
          </TD>
          </TR>
          <tr >
			  <td align="right">
			    <input type="button" class="button" name="dd" value=" �� �� " onclick="javascript:window.close();">
			  </td>
			</tr>
          
         </TBODY>
        </TABLE>
        <input type="hidden" name="BsID" value=""> 
        </form>
     </td>
   </tr>
  
</table>


<%	
}
catch( IException ie )
{
	ie.printStackTrace();
	Log.print(ie.getMessage());
}
	OBHtml.showOBHomeEnd(out);
%>