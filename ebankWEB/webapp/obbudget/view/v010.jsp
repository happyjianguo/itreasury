<%--
/*
 * �������ƣ�v010.jsp
 * ����˵��������Ԥ�����ҳ
 * �������ߣ�liangpan
 * ������ڣ�2007-07-18
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:directive.page import="java.text.DateFormat"/>
<!-- ������ Added by zwsun, 2007/7/18 -->
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.*,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.*" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%String strContext = request.getContextPath();%>

<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ÿ�����]";
%>
<%
  
    OBHtml.validateRequest(out,request,response);
    
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	   // transTypeID = Constant.ModuleType.BUDGET
	}
	
	long id = -1;
	String strTemp = "";
	String adjustNote = "";
	Timestamp modifyDate = null;
	
	strTemp = (String)request.getParameter("budgetID");
	if(strTemp != null && !strTemp.equals("")){
		id = Long.parseLong(strTemp);
	}
	
	strTemp = (String)request.getParameter("adjustNote");
	if(strTemp != null && !strTemp.equals("")){
		adjustNote = strTemp;
	}
	
	strTemp = (String)request.getParameter("modifyDate");
	if(strTemp != null && !strTemp.equals("")){
		modifyDate = DataFormat.getDateTime(strTemp);
	}

	OBBudgetInfo info = new OBBudgetInfo();
	OBBudgetBiz biz = new OBBudgetBiz();
	if(id > 0){
		info = biz.findByID(id);
	}
	List list = biz.findAllSubRecords(id);
	Timestamp currentDate = DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)));
%>
<%
	
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try 
	{
        //   OBHtml.validateRequest(out,request,response);
         // �û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v004.jsp">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/v004.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">			<!--��������-->
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<input type="hidden" name="adjustNote" value=<%=adjustNote%>>
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="officeId" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="currencyId" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
		<input type="hidden" name="sname" value="<%=info.getSname()%>">
		<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
		<input type="hidden" name="note" value="<%=info.getNote()%>">
		<input type="hidden" name="modifyDate" value="<%=modifyDate%>">
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="220" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �ÿ����� - �ÿ��������</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
</table>
<table width="774" border="1" cellspacing="0" cellpadding="0" class=normal align=center>
<%
		long isReadonly = Constant.TRUE;
		for(int i = 0 ; i < list.size() ; i ++){
			OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
%>
		<tr align="center">
		<td height="25" class="MsoNormal" width="25%"><%=DataFormat.getDateString(subInfo.getStartdate())%></td>
		<td  height="25" class="MsoNormal" width="25%"><font color=red>*</font>�ÿ��</td>
        <td  height="25" class="MsoNormal" width="25%"><%=sessionMng.m_strCurrencySymbol%> 
<%   
		if(subInfo.getStartdate().before(currentDate) || (subInfo.getStartdate().equals(currentDate) && (subInfo.getStatus() != OBConstant.OBBudgetStatus.NOTDEAL && subInfo.getStatus() != OBConstant.OBBudgetStatus.FAILEDDEAL))){
%>		    
        <script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","readonly","");
		</script>
<%
		}else {
%>
		<script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","","");
		</script>
<%
		isReadonly = Constant.FALSE;
		}
 %>
		</td>
		<td height="25" class="MsoNormal" width="25%"><%=OBConstant.OBBudgetStatus.getName(subInfo.getStatus())%></td>
		</tr>
<%		
		}
 %>
         <tr>
           <td colspan="4">
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=String.valueOf(info.getAdjunctID())%>' 
		        	caption = "�ϴ�" 
		        	lid = '-1'
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
           </td>
        </tr>
    </table>	

      <br>
       
  <input name="sysdate" type="hidden" value=<%=currentDate%>>
	  <!--��ʽ��̬��ʾ�տ����-->

      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name="add" type=button value=" �� �� " onClick="Javascript:addme(<%=isReadonly%>);" onKeyDown="Javascript:addme();"> 
			</div>
          </td>
		  <td width="9" height="17"></td>
			<!--  Added by zwsun ,2007/7/18, ������ -->
			<td>
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="���沢�ύ����" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.BUDGETADJUST%>"/>
		  	</td>
		  <td width="9" height="17"></td>			  
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name="cancel" type=button value=" ȡ  �� " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
			</div>
          </td>
        </tr>
      </table>
<%
		
%>
	 <!-- ˢ������ -->
<input type="hidden" name="clickCount" value="0">
<!-- ˢ������ --> 
</form>
 
<script language="javascript">
	/* ҳ�潹�㼰�س����� */
	firstFocus(form_1.amount0);
	//setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">

 /* �޸��ύ������ */
    function add(isReadonly)
    {  	
    	if(isReadonly == <%=Constant.TRUE%>){
    		alert("û�п��Ե����ļ�¼");
    		return false;
    	}
    	var txtColl = document.getElementsByTagName("INPUT");
    	for(var i=0;i<txtColl.length;i++){
    		var subInput = txtColl[i];
    		if(subInput.type == "text") {
    			if(subInput.value == 0.0 || subInput.value == ""){
    				alert("�����������ÿ���");
    				return false;
    			}
    		}
    	}    	    	
		form_1.action = "../control/c004.jsp";
		if(confirm("ȷ���ύ��")){
			showSending();
        	form_1.submit();
        }
    }
     //���沢�ύ����, Added by zwsun, 2007/7/18/
    function doSaveAndApprovalInit()
	{
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		add();
	} 
	function addme(isReadonly){
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		add(isReadonly);		
	}   

    
    /* ȡ�������� */
    function cancelme()
    {
		 	
			if (confirm("�Ƿ�ȡ����"))
			{
				form_1.action="../view/v004.jsp";
				form_1.submit();
			}
		 
    }
    
</script>
<%

	  }
	catch (IException ie)
	{
		
		
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
	/* ��ʾ�ļ�β */
		OBHtml.showOBHomeEnd(out);	
%>
 
<%@ include file="/common/SignValidate.inc" %>
