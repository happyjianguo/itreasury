<!--
/**
 * ҳ������ ��v007.jsp
 * ҳ�湦�� : �ʽ�ƻ�-����-����-�����Ӻ�õ����걨�����ҳ�棻 v006.jsp ��������> ��ǰҳ��
 * ��    �� ��ylguo
 * ��    �� ��2008-10-27
 * 		
 * �޸���ʷ ��
 */
-->
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection,
				 java.util.Date,
				 com.iss.itreasury.util.*,
				 java.text.DateFormat,
				 java.text.SimpleDateFormat,				 
                 com.iss.itreasury.ebank.util.SessionOB,
                 com.iss.itreasury.ebank.util.OBHtml,
                 com.iss.itreasury.ebank.util.OBConstant,
                 com.iss.itreasury.util.Constant,
                 com.iss.itreasury.system.util.SYSHTML,
                 java.sql.Timestamp           
                 " 
                 %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.ListType" %>
<%@ page import="com.iss.itreasury.util.Constant.ModuleType" %>
<%@ page import="com.iss.itreasury.util.SessionMng" %>
		
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTableTitle = null;
String strContext = request.getContextPath();
try
{       //�������Ƿ��¼
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}
		/* �ж��û��Ƿ���Ȩ�� */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

		//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
%>

<jsp:include page="/ShowMessage.jsp"/>



<!-- ҳ��ʹ��js���� -->
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<%@ taglib uri="/WEB-INF/tlds/iss-fundplan-tags.tld" prefix="fundplan"%>
<safety:resources />

<!-- ҳ��ʹ��js�������� -->



<form method="post" name="frm001">
<table  width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">�ʽ�ƻ�����</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<!-- �����걨��� -->
<%
	String strTemp = "";
    //�ͻ�����
    String clientName = "";
    strTemp = request.getParameter("clientName") ;
    if(strTemp != "" && strTemp.length()>0){
       //clientName = new String(strTemp.getBytes("ISO-8859-1"),"GB2312");
       clientName = strTemp;
    }
    //�걨���
    String cpCode = "";
    strTemp = request.getParameter("cpCode");
    if(strTemp != "" && strTemp.length()>0){
       cpCode = strTemp;
    }
    
    Timestamp startTs = null;
    Timestamp endTs = null;
    //�걨��ʼʱ��
    String startDate = "";
    strTemp = request.getParameter("startDate");
    if(strTemp != "" && strTemp.length()>0){
       startTs = DataFormat.getDateTime(strTemp);
       startDate = DataFormat.getDateString(startTs);
    }
    //�걨����ʱ��
    String endDate = "";
    strTemp = request.getParameter("endDate");
    if(strTemp != "" && strTemp.length()>0){
       endTs = DataFormat.getDateTime(strTemp);
       endDate = DataFormat.getDateString(endTs);
    }
    
    //�õ��걨��״̬
    long statusId = -1;
    strTemp = request.getParameter("statusId");
    if(strTemp != "" && strTemp.length()>0){
       statusId = Long.parseLong(strTemp);
    }
    
    //�ӱ��е��ֶ�CapitalplanId
    long CapitalplanId = 0;
    strTemp = ""+request.getParameter("id");
    if(strTemp != "" && strTemp.length()>0){
      CapitalplanId = Long.parseLong(strTemp);
    }
    
    //��õ�ǰ���������
    long checkP = sessionMng.m_lUserID;
    //���¼�������
    long inputerP = 0;
    strTemp = request.getParameter("inputerId");
    if(strTemp != "" && strTemp.length()>0){
      inputerP = Long.parseLong(strTemp);
    }
    
    //����Ѿ����ˡ����ø��������
    long isCheckedP = 0;
    strTemp = request.getParameter("isCheckedP");
    if(strTemp != "" && strTemp.length()>0){
      isCheckedP = Long.parseLong(strTemp);
    }
    
    long modelId = -1;
    strTemp = request.getParameter("modelId");
    if(strTemp != "" && strTemp.length()>0){
      modelId = Long.parseLong(strTemp);
    }    
    //����ʱ��
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    Date _date = new Date();
    String checkdate = df.format(_date);
    
 %>
 <input name="p_SuccessPageURL" type="hidden" value="../view/v005.jsp">
<input name="p_FailPageURL" type="hidden" value="../view/v005.jsp">

<br/>

     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr>
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">�걨��λ</td>
          <td width="586">
		  	<input type="text" class="box" name="clientName" size="32" value="<%=clientName%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">�걨��Χ</td>
          <td width="586">
		  		<input type="text" class="box" name="dateFrom" size="16" value="<%=startDate %>" disabled="disabled">
				&nbsp;��&nbsp;
				<input type="text" class="box" name="dateTo" size="16" value="<%=endDate %>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>  
		  		<td width="4" height="25" ></td>
          <td width="100" align="left">�걨���</td>
          <td width="586">
		  		<input type="text" class="box" name="cpCode" size="20" value="<%=cpCode%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>        
        
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">&nbsp;</td>
          <td width="686" colspan="2" align="right">
		  		��λ����Ԫ
          </td>
        </tr>
		
        <tr>
		  <td width="4" height="25"></td>
          <td width="100%" colspan="3">
          	<!-- ��Ŀ��ʾ��� -->
          	<fundplan:FundPlanWidget capitalplanId="<%=CapitalplanId %>" 
          							 disabled="true"
          							 type="submit"
          							 modelId="<%=modelId %>"
          							 office="<%=sessionMng.m_lOfficeID%>"
          							 currency="<%=sessionMng.m_lCurrencyID%>"
          							 clientId="<%=sessionMng.m_lClientID%>"
          							 dateFrom="<%=startDate%>"
          							 dateTo="<%=endDate%>" />
		  </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
        <tr>
        <td colspan=3></td>
        	<td>
          <div align="right">
          <%
          	if(statusId == 1 && inputerP != sessionMng.m_lUserID)
          	{
           %>
          	<input class="button1" name="strcheck" type="button" value=" �� �� " onclick="toCheck()">&nbsp;&nbsp;
          <% } %>
		  <%
          	if(statusId == 2 && inputerP != sessionMng.m_lUserID)
          	{
           %>
          	<input class="button1" name="discheck" type="button" value=" ȡ������ " onclick="javascript:toDisCheck()">&nbsp;&nbsp;
		  <% } %>
		  	<input class="button1" name="back" type="button" value=" �� �� " onclick="javascript:toBack()">&nbsp;&nbsp;
          </div>
          </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
</table> 


          
</td>
</tr>
</table>
</form>


<script language="javascript">
   window.onload = function ss(){
   		var statusId = <%=statusId%>;
   		var inputerP = <%=inputerP%>;
   		var uid = <%=sessionMng.m_lUserID%>;
   		setFormName("frm001");
   		if(statusId == 1 && inputerP != uid){
   			//setSubmitFunction("toCheck()");
   		}
   		if(statusId == 2 && inputerP != uid){
   			//setSubmitFunction("toDisCheck()");
   		}
   }
   
	function toCheck(){
		var checker =<%=checkP %>;
		var inputer =<%=inputerP%>;
		if(checker!=inputer)
	    {  
	    	if(confirm("ȷ��Ҫ������"))
	    	{
	    		showSending();
				frm001.action ="../control/c006.jsp?&method=toCheck&capitalplanId=<%=CapitalplanId%>&checker=<%=checkP%>&checkDate=<%=checkdate%>";
				frm001.submit();
			}
		}
		else{
			alert("�Բ������Ǹ��ʽ�ƻ����걨�ˣ����ܸ��������ݣ�");
		}
	}
	
	function toDisCheck(){
		var checker =<%=checkP %>;
		var isCheckedP =<%=isCheckedP%>;
	    if(checker==isCheckedP){ 
			if(confirm("ȷ��ȡ��������"))
			{
				showSending();
				frm001.action ="../control/c006.jsp?&method=toDisCheck&capitalplanId=<%=CapitalplanId%>";
				frm001.submit();
		    }
	    }
	    else{
			alert("�Բ��������Ǹ��ʽ�ƻ��ĸ����ˣ�����ȡ�����ˣ�");
		}
	}
	function toBack(){
		window.history.back();
	}
	
	
</script>
<%
	/**
	* ��ʾ�ļ�β
	*/
	OBHtml.showOBHomeEnd(out);
%>

<%
}
catch(IException ie)
{
	
	ie.printStackTrace();
	out.flush();
	return; 
}
%>	

<%@ include file="/common/SignValidate.inc" %>