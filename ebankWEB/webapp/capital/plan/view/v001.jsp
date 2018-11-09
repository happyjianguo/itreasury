<%--
 ҳ������ ��v001.jsp
 ҳ�湦�� : �ʽ�ƻ�ά��
 ��    �� ��jiamiao
 ��    �� ��2006-3-23
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="java.util.*"%>
<%@ page import="java.net.URI"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
try
{
	String type = request.getParameter("type");
	String strTitle = "";
	if(type == null)
		strTitle = "�ÿ�ƻ�ά��";
	else if(type.equals("1"))
		strTitle = "�ÿ�ƻ�����";
		
	Log.print("*******����ҳ��--ias_hotdeploy_iTreasury-ebank/iTreasury-ebank/capital/plan/view/v001.jsp*******");
    /*
     * У��ͻ����������Ч��
     */
     //�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,strTitle,null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,strTitle,null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	OBHtml.showOBHomeHead(out, sessionMng,strTitle, Constant.YesOrNo.YES);
	Collection col = (Collection)request.getAttribute("col");
%>

<safety:resources />

<form name="formV001" method="post"  onsubmit="return checkSubmit();">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
  <table width="99%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr bgcolor="#FFFFFF"> 
		<td colspan="4" height="1"></td>
	</tr>
	<tr class="tableHeader">                
		<td colspan="4"height="22" class="FormTitle">
			<font size="3" color="#FFFFFF" ><b><%=strTitle %></b></font>
		</td>                
	</tr>
	<tr bgcolor="#FFFFFF"> 
		<td colspan="4" height="1"></td>
	</tr>
  </table>
  <table width="100%" border="0" class="ItemList" bordercolor="#999999">
    <tr align=center class="tableHeader">
      <td class=ItemTitle width="10%" height="23">���</td>
      <td class=ItemTitle width="18%" height="23">֧�����</td>
      <td class=ItemTitle width="18%" height="23">������</td>
      <td class=ItemTitle width="18%" height="23">��ʼ����</td>
      <td class=ItemTitle width="18%" height="23">��������</td>
      <td class=ItemTitle width="18%" height="23">״̬</td>
    </tr>
   <% if(col != null && !col.isEmpty())
   {
	   Iterator iterator = col.iterator();
	   while(iterator.hasNext())
   		{
   			OBCapitalPlanInfo info = new OBCapitalPlanInfo();
   			info = (OBCapitalPlanInfo)iterator.next();
   			URI uri = new URI("");
   			if(type == null)
   	   			uri = new URI("../control/c003.jsp?id="+info.getId());
   			else if(type.equals("1"))
   	   			uri = new URI("../control/c003.jsp?type=1&id="+info.getId());
   %>   
    <tr borderColor=#999999>
      <td class=ItemBody width="10%" height="18" align="center" ><a href=<%=uri %>><%=info.getId() %></a></td>
      <td class=ItemBody width="18%" height="18" align="right" ><%=DataFormat.formatDisabledAmount(info.getPayAmount())%></td>
      <td class=ItemBody width="18%" height="18" align="right" ><%=DataFormat.formatDisabledAmount(info.getReceivAmount()) %></td>
      <td class=ItemBody width="18%" height="18" align="center" ><%=info.getStartDate().toString().substring(0,10) %></td>
      <td class=ItemBody width="18%" height="18" align="center" ><%=info.getEndDate().toString().substring(0,10)%></td>
      <td class=ItemBody width="18%" height="18" align="center" ><%=OBConstant.OBCapitalPlan.getName(info.getStatusID())%></td>
    </tr>
    <%   }
   	}else{ %>
    <tr align="center"  borderColor=#999999>
      <td class=ItemBody width="10%" height="18">&nbsp;</td>
      <td class=ItemBody width="18%" height="18">&nbsp;</td>
      <td class=ItemBody width="18%" height="18">&nbsp;</td>
      <td class=ItemBody width="18%" height="18">&nbsp;</td>
      <td class=ItemBody width="18%" height="18">&nbsp;</td>
      <td class=ItemBody width="18%" height="18">&nbsp;</td>
    </tr>
    <%} %>
  </table>  <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="right" bgcolor="#FFFFFF">
      <td width="20%">&nbsp;</td>
      <td></td>
      <td width="20%"><%if(type == null){ %><input name="add" type="submit" class="button" id="add" value="�� ��" onClick="doAdd();"><%} %>&nbsp;&nbsp;</td>
    </tr>
  </table><br></td>
    </tr>
  </table>
  
</form>

<script language="JavaScript">
	function doAdd()
	{	
		formV001.action="/NASApp/iTreasury-ebank/capital/plan/view/v002.jsp?value=add";
	}
	    
	var checkSubmitFlg = false;
    function checkSubmit()
	{
      if (checkSubmitFlg == true)
	  {
         return false;
      }
      checkSubmitFlg = true;
      return true;
   }
   document.ondblclick = 
   function docondblclick()
   {
    window.event.returnValue = false;
   }
   document.onclick =
   function doconclick()
   {
       if (checkSubmitFlg)
	   {
         window.event.returnValue = false;
       }
   }
	
</script>
<%				
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}
		OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>