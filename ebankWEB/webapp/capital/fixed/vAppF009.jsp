<!--
/*
 * �������ƣ�vAppF009.jsp
 * ����˵��������ת���ύ����ҳ��
 * �������ߣ��̿�
 * ������ڣ�2007��04��18��
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef"
%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[����ת��]";
%>

<%
com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
    String type = "";
    String _type = request.getParameter("type");
    if ((type != null) && (type.length()>0))
	{
    	type = _type;
	}
	long lCheckType = -1;//�����ڸ���ƥ���
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
       	lTransType = (String)request.getParameter("txtTransType");
       	
    //��"�ҵĹ���"���ݵĿ��Ʊ���
	String strTempAction = "";
	if (request.getAttribute("strTempAction") != null) 
	{
		strTempAction = (String)request.getAttribute("strTempAction");
	}
%>

<%
	/* ʵ������Ϣ�� */
	FinanceInfo financeInfo = null;

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try 
	{
        /* �û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }*/

		/* �������л�ȡ��Ϣ */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		
		//ȡ�������б�ҳ���ȡ��������
		String operation = "-1";
		if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
		{
			operation =(String)request.getParameter("operation");
		}
		
		
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
/*		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.ChangeFixdeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.ChangeFixdeposit.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//���⴦��
			 	valueArray[9] = "-1";
			}
		//	blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);			
		}*/
	
%>
<safety:resources />

<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} %>


<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<isswf:init/>
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <form name="frm" method="post">
      <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="" align="center">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="124" background="/webob/graphics/new_til_bg.gif">��<span class="txt_til2">����ת��</span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> ����ת��</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>�������潫<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>�ڸ��˺��<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>�����������˺��<%} }%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<br>
              <!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <%if(!OBFSWorkflowManager.isAutoCheck()){
			  	if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){
			  %>
			  <br>
              �ñʽ����д������˸��ˣ�
			  <br>
			  <%}
			  else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){
			  %>
			  <br>
              �ñʽ����д�������������
			  <br>
			  <%}
			  }
			  %>
              <br>
              <b>ָ�����Ϊ��<%= financeInfo.getID() %></b><br>     
              <br>
            </p>
          </td>
          <td width="5" height="25"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      
	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> ���ڴ���˻�</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
      <tr class="MsoNormal">
          <td width="5" height="25"></td>  
    <td width="130" height="25" class="MsoNormal">�����˻����ƣ�</td>
          <td width="430" height="25"class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">�����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">���ڴ�����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getDepositNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> ����ת��</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>

      
  <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>�¶��ڴ����ʼ�գ�</p></td>
      <td width="547" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;�¶��ڴ����գ�<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>���ڴ�����ޣ�</p></td>
      <td width="547" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"��":"����"%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>��</p></td>
      <td width="547" height="25" class="MsoNormal">
       <%= financeInfo.getFormatAmount() %>
      </td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
  </table>

      <br>
      	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> ��Ϣ����</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%if(financeInfo.getSDepositInterestDeal() == 1 ) {%>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��������</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%}else if(financeInfo.getSDepositInterestDeal() == 2 ){ %>
           <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">��Ϣת�������˻�:</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal">��Ϣת�������˺ţ�<%=eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;</td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%} %>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // ��ȷ��,δ����
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //�����
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //�Ѿܾ� 		   
		{
%>
		 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="160" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> �������봦������</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
      <table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal >
       <thead>
        <tr >
          <td width="63" height="19">
            <p align="center">���к�</p>
          </td>
          
          <td  height="19" valign="middle"  width="190">
            <div align="center">�û�</div>
          </td>
         
          <td  height="19" valign="middle"  width="198">
            <div align="center">��������</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td   width="198" height="25">
            <div align="center">¼��</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% }
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // �Ѹ���
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //�Ѿܾ�
        	{ 
%>
        <tr valign="middle">
          <td width="63" align="left"   height="25">
            <div align="center">2</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//�Ѿܾ�
					(financeInfo.getSignUserName() != null))
         		{  
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">2</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">����</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
       
<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // ��ǩ��
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //������
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //�����
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//�Ѿܾ�
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">ǩ��</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				}
%>
 </table>
      <br>
    <%--   <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">���Ӹ���</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>--%>
	           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "�ϴ�" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.CHANGEFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.CHANGEFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
      <%--     </td>
        </tr>
      </table>--%>
      <br>
	  <table border="0" width="774" align="center" cellspacing="0" cellpadding="0" align="center">
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>&&transType=<%=OBConstant.SettInstrType.CHANGEFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		   <fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CHANGEFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
		  </TD>
	  </TR>
	  <tr>
		         <%
		    if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
			{
				//added by mzh_fu 2007-05-03 �����������Ѹ��˵����񣬲�����ʾ�������¼���
				if(financeInfo.getStatus() != OBConstant.SettInstrStatus.APPROVALED && financeInfo.getStatus() != OBConstant.SettInstrStatus.CHECK){

						String strMagnifierNameRemark = "�������";
						String strFormNameRemark = "frm";
						String strMainPropertyRemark = "";
						String strPrefixRemark = "";
						String[] strMainNamesRemark = {com.iss.inut.workflow.constants.Constants.WF_ADVISE};
						String[] strMainFieldsRemark = {"Description"};
						String strReturnInitValuesRemark="";
						String[] strReturnNamesRemark = {"txtsOptionID"};
						String[] strReturnFieldsRemark = {"id"};
						String[] strReturnValuesRemark = {""};
						String[] strDisplayNamesRemark = {"����������","�����������"};
						String[] strDisplayFieldsRemark = {"Code","Description"};
						int nIndexRemark = 1;
						String strSQLRemark = "select * from sys_approvalopinion where officeid="+sessionMng.m_lOfficeID+" and currencyid="+sessionMng.m_lCurrencyID+" and moduleid="+sessionMng.m_lModuleID+" and statusid="+Constant.RecordStatus.VALID;
						String strMatchValueRemark = "Description";
						//String[] strNextControlsRemark = {"strGeneratorCapacity","isShareHolder"};
						String strNextControlsRemark = "checkNextUser";
						String strTitleRemark = "�������";
						String strFirstTDRemark="align='center'";
						String strSecondTDRemark="colspan='2'";	
						Magnifier.showTextAreaCtrlForEbank(out,strMagnifierNameRemark,strFormNameRemark,strPrefixRemark,strMainNamesRemark,strMainFieldsRemark,strReturnNamesRemark,strReturnFieldsRemark,strReturnInitValuesRemark,strReturnValuesRemark,strDisplayNamesRemark,strDisplayFieldsRemark,nIndexRemark,strMainPropertyRemark,strSQLRemark,strMatchValueRemark,strNextControlsRemark,strTitleRemark,strFirstTDRemark,strSecondTDRemark);
				}
			}
		
		%>   
		</tr> 
		<tr>
		<td colspan=3><br></td>
		</tr>
	   </table>
		<table width="100%"><tr><td align="right">
			<%
	    	//����ȡ�������б����ʱ,��ʾȡ��������ť
	    	if(operation!=null && operation.length()>0 && Long.parseLong(operation)==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
	    		{
	    	%>
	    		<input class="button1" name="ca" type="button" value=" ȡ������ " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">
			     <isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />
			<%	}
			else
				{
			%>
			     <isswf:submit styleClass="button1" value=" �� �� " history=" �� �� "  onclick="doApproval();" />
			<%	}
			%>
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:history.back();" onKeyDown="javascript:history.back();"/>
            </td>
        </tr>
	  <%--
        <tr>
		<td width="774" align="right">

<%
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� %>

            <input class=button1 name=add type=button value=" �޸� " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" ɾ�� " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
            
            <% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
            <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
            <%}%>

		<% }
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
			<% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
			/* �������� */
			System.out.println("sessionMng.m_lUserID="+sessionMng.m_lUserID);
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID != financeInfo.getNDepositBillInputuserId()) && (lCheckType == 1)) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// �Ѹ��ˡ���¼�ˣ������� %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
		  	/* ����ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillCheckuserId())) {// ����ɡ���¼�ˣ������� %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" ǩ���ύ " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* ����ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// ����ɡ���Ҫ����¼��ǩ��=true %>

           	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" ǩ���ύ " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%} 
			/* ����ȡ��ǩ�� */
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillSignUserID()) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) ){// ����ɡ���¼�ˣ�ǩ���� %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->

				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <input type="Button" class="button1" value=" ��ӡ " width="46" height="18"   onclick="javascript:PrintConsignVoucher()" 
		   <%  if(a!=null && !a.contains(strStatus))
               {%>
					disabled
			   <%}
			%>>&nbsp;&nbsp;

               <!--
            <img src="\webob\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" �ر� " width="46" height="18"   onclick="window.close();">&nbsp;&nbsp;
         	<%}%>
		 </td>
        </tr>
        --%>
      </table>

	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">


	  <!-- add by mingfang 2007-05-24 -->
	  <!-- ǩ�����ֶ� -->
   <input type="hidden" name="strAction" value="">
   <input type="hidden" name="nPayeeAccountID"         value="<%=financeInfo.getPayeeAcctID()%>">     
   <input type="hidden" name="sPayerAccountNoZoomCtrl" value="<%=financeInfo.getSDepositBillNo()%>">
   <input type="hidden" name="dAmount"                 value="<%=financeInfo.getAmount()%>">
   <input type="hidden" name="nFixedDepositTime1"      value="<%=financeInfo.getFixedDepositTime()%>">
   <input type="hidden" name="tsExecute"               value="<%=DataFormat.getDateString(financeInfo.getExecuteDate())%>">
   <input type="hidden" name="dPayerStartDate"         value="<%=DataFormat.getDateString(financeInfo.getSDepositBillStartDate())%>">
   <input type="hidden" name="interesttype"            value="<%=financeInfo.getSDepositInterestDeal()%>">
   <input type="hidden" name="lInterestToAccountID"    value="<%=financeInfo.getSDepositInterestToAccountID()%>">
   <input type="hidden" name="submitUserId"            value="<%=financeInfo.getConfirmUserID()%>">
   
   	 
   <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
   <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>">
	  </form>
<!--presentation end-->

<script language="javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//����������
	function doApproval()
	{
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("�������������");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
	
		if (confirm("�Ƿ�����?")) 
		{
		
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f009-c.jsp?operate=doApproval';
			frm.submit();
		}
	}

//ȡ������   add by mingfang
	function cancelApproval()
	{
	
	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
	
		if (confirm("�Ƿ�����?")) 
		{
		
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f009-c.jsp?operate=cancelApproval';
			frm.submit();
		}
	}	
	
	/*���ش����� */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f006-c.jsp";
		frm.submit();
	}

	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		if (confirm("ȷ��ȡ����"))
		{
			//showMenu();
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}

	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f007-c.jsp";
		frm.submit();
	}
	
		/* ɾ�������� */
	function deleteme()
	{
		if (!confirm("�Ƿ�ɾ����"))
		{
			return false;
		}
		//showMenu();
		frm.action="../fixed/f009-2-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* ��ʾ�ļ�β */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>