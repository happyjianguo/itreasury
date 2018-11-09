<%--
/*
 * �������ƣ�f008-v.jsp
 * ����˵�������������ύ����ҳ��
 * �������ߣ��̿�
 * ������ڣ�2007��04��18��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[��������]";
%>

<%
	//��ҳ��Ϣ
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();	
	com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
		String strTemp=null;
		long rdoInterest = -1;
		strTemp = (String) request.getAttribute("rdoInterest");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			rdoInterest = Long.valueOf(strTemp).longValue();
		}

	/* ʵ�ֲ˵����� */
	String dPayerEndDate="";
	if(request.getAttribute("dPayerEndDate")!=null)
	{
		dPayerEndDate =(String) request.getAttribute("dPayerEndDate");   //���ڴ�������
	}
	
	
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

      	
	//�Ƿ���Ҫ������ by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";
	
	//�Ƿ�Ϊ����ָ���ѯ
    String lOperate = null;
    String operate="";
	lOperate = (String)request.getParameter("operate");
	if (lOperate != null)
	{
	    operate = lOperate;
	}
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
        //��Ϣ���´����ݿ��ȡ����Ҫ���ڸ���dtmodify�ֶ�   add by zhanglei  2010.06.01
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		long lID = financeInfo.getID();
		financeInfo=financeInstr.findByID(financeInfo.getID(), sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		
		
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
        /**
         * presentation start
         */
        /* ��ʾ�ļ�ͷ */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		//boolean isUseCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
		//boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //�Ƿ�ʹ���°�����֤����ǩ
   	 	String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
		
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);		
		if(isUseCertification)
		{
			String temp = null;	
			temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}			
		}		
%>


<%if(isUseCertification && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%

	}
 %>
<form name="frm" method="post">
<table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">��������</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

<br/>
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <input type="hidden" name="rdoInterest" value="<%=rdoInterest %>">
     <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
     <input type="hidden" name="dPayerEndDate" value="<%=dPayerEndDate %>">
     
	 <input type="hidden" name="endDate" value="">
     
      <%
		if (lSourceType != 1) 
		{
%>


	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>
        <table width="110" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2">  ��������ȷ��</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        </table>
        </td>
      </tr>
    </table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>�������潫<%
	        if(isNeedApproval.equals("true"))
	        {
	        	%>��������<%
	        	if(!OBFSWorkflowManager.isAutoCheck())
	        	{
	        		%>�ȴ������˸��ˣ�<%
	        	}
	        	else
	        	{
	        		%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        	}
	        }
	        else if(!OBFSWorkflowManager.isAutoCheck())
	        {
	        	%>�ڸ��˺���ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
	        else
	        {
	        	%>�ύ��<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>��<%
	        }
        %>
        <br>
              <!--br>
              ��֪ͨ�����˸��ˣ�
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			 <%if(isNeedApproval.equals("true"))
			  	{
			  		%><br>�ñʽ����д�������������<br><%
			  	}else if(!OBFSWorkflowManager.isAutoCheck())
			  	{
			  		%><br>�ñʽ����д������˸��ˣ�<br><%
			 	}%>
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
<%
		}
%>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ���ڴ���˻�</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
      <tr class="MsoNormal">
          <td width="1%" height="25"></td>  
    <td width="130" height="25" class="MsoNormal">�����˻����ƣ�</td>
          <td width="430" height="25"class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">�����˺ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="130" height="25"class="MsoNormal">���ڴ浥�ţ�</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getSDepositBillNo().toString()) %></td>
          <td width="5"></td>
        </tr>
<!-- �����Զ�������� -->
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>
		<%
			if(financeInfo.getIsAutoContinue() == 1)
			{
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					�Ƿ��Զ����棺
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">��
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<%
				if(financeInfo.getAutocontinuetype() == 1)
				{
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							�Զ��������ͣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">��������
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>		
		<%			
				}else if(financeInfo.getAutocontinuetype() == 2) {
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							�Զ��������ͣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">��������
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							��Ϣת�������˻���
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid()) %>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							���ڿͻ����ƣ�
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid())%>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>										
		<%			
				}		
			}else if(financeInfo.getIsAutoContinue() == 2) {
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					�Ƿ��Զ����棺
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">��
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>			
		<%		
			} 
		%>
<%} %>
<!-- End -->		        
        
      </table>
      <br>
      	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ��������</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>

      
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="23%" height="25" class="MsoNormal"><p>��</p></td>
      <td width="430" height="25" class="MsoNormal">
       <%=sessionMng.m_strCurrencySymbol%> <%= financeInfo.getFormatAmount() %>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td height="25" width="130" class="MsoNormal"> <p>���ڴ�����ޣ�</p></td>
      <td width="430" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod()>10000?financeInfo.getSDepositBillPeriod()-10000:financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"��":"����"%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td height="25" width="130" class="MsoNormal"> <p>�¶��ڴ����ʼ�գ�</p></td>
      <td width="430" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;�¶��ڴ����գ�<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
  </table>

      <br>
      	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> ��Ϣ����</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
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
          <td width="1%" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="100">��Ϣת�������˺ţ�</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;</td>
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
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//�Ѿܾ�   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//������
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//������		   
		{ 
%>
		<table width="180" border="0">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:140px"> �������봦������</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        </table>
      <table width="100%" border="0" align="" cellspacing="1" class=list_table >
        <tr class=itemtitle>
          <td width="63" height="19">
            <p align="center">���к�</p>
          </td>
          
          <td  height="24" valign="middle"  width="190">
            <div align="center">�û�</div>
          </td>
         
          <td  height="19" valign="middle"  width="198">
            <div align="center">��������</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">ʱ�������</div>
          </td>
        </tr>
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

 <br/>
 <%--
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	          <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>'  
		        	caption = " �� �� " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       <%--    </td>
        </tr>
      </table>
      --%>
      <br>
      <% if(isNeedApproval.equals("true")){ %>
      	   <table width="170" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">��ʷ�������</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
		</table>
		<%-- <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&lID=<%=lID %>&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			 <fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					<%} %>
		<br />
		<table border="0" width=100% align="" cellspacing="0" cellpadding="0">
        <tr>
		<td width="100%" align="right">
			<%
				if(financeInfo.getStatus()==OBConstant.SettInstrStatus.REFUSE)
				{
					if(financeInfo.getConfirmUserID()==sessionMng.m_lUserID&&biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
						{
						
			 %>
			 				<input class="button1" name=add type=button value=" �����ύ " onClick="Javascript:submitagain();" onKeyDown="Javascript:submitagain();">&nbsp;&nbsp;
			 				
			 <%
			 			}
			 	}		
			  %>

<%
	/* ��ָ���ѯҳ������� */
String search="";
if(request.getAttribute("search")!=null){
search=(String)request.getAttribute("search");
}
String isSign="";
if(request.getParameter("sign")!=null){
isSign=request.getParameter("sign");
}
		/* ȷ�ϡ��޸ġ�ɾ�� */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// δ����ȷ�ϡ���¼�ˣ�ȷ���� 
			if(biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
		%>

            <input class=button1 name=add type=button value=" �޸� " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" ɾ�� " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
            
            <% 
            	}
            if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
            <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
            <%}%>

		<% }
		
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// δ����ȷ�ϡ���¼�ˣ�ȷ���� 
		if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) 
		{%>
			<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			
		<%}
		}
		
			/* ����ƥ��*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) &&!search.equals("1")) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* ���� */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE|| financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) &&!search.equals("1")) {// ��ȷ�ϡ���¼��<>ȷ���� %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" ���� " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
				<input class=button1 name=add type=button value=" ���� " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			<% }
		  	/* ȡ������ */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&&!search.equals("1") && !isSign.equals("1")) {// �Ѹ��ˡ���¼�ˣ������� %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" ȡ������ " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">

		<%}
			/* ǩ�ϼ��ύ */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&&!search.equals("1") && isSign.equals("1")){// �Ѹ��ˡ���Ҫ����¼��ǩ��=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" ǩ�� " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

		<%}
			/* ȡ��ǩ�� */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&&!search.equals("1") && isSign.equals("1")){// ��ǩ�ϡ���¼�ˣ�ǩ���� %>

            	<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" ȡ��ǩ�� " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		  
		    <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <%
						///add by liuguang 2007-10-19  ����ҵ���Ƿ���Ҫ��ӡ����ʾ
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
		   <input type="Button" class="button1" value=" ��ӡ " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%if(a!=null && !a.contains(strStatus))
               { 
			%>
			   disabled
			<%
				}
			%>
		   >
		   <%
				}
				%>
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) 
        	{
        		
        		if(operate.equals("query"))
        		{
        	%>
        			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
					<input type="Button" class="button1" value=" �� �� " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        	<%
        		}
        		else
        		{
        	%>
        			<input type="Button" class="button1" value=" �� �� " width="46" height="18"   onclick="window.close();window.opener.queryme();"> &nbsp;&nbsp;
        	<% 		
        		}
        		
        	}
        	%>
		 </td>
        </tr>
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
	  
   <input type="hidden" name="nPayeeAccountID"         value="<%=financeInfo.getPayeeAcctID()%>">     
   <input type="hidden" name="sPayerAccountNoZoomCtrl" value="<%=financeInfo.getSDepositBillNo()%>">
   <input type="hidden" name="dAmount"                 value="<%=financeInfo.getAmount()%>">
   <input type="hidden" name="nFixedDepositTime1"      value="<%=financeInfo.getSDepositBillPeriod()%>">
   <input type="hidden" name="tsExecute"               value="<%=financeInfo.getExecuteDate()%>">
   <input type="hidden" name="dPayerStartDate"         value="<%=financeInfo.getDepositStart().toString().substring(0,10)%>">
   <input type="hidden" name="interesttype"            value="<%=financeInfo.getSDepositInterestDeal()%>">
   <input type="hidden" name="lInterestToAccountID"    value="<%=financeInfo.getSDepositInterestToAccountID()%>">
   <input type="hidden" name="submitUserId"            value="<%=financeInfo.getConfirmUserID()%>">
   <input type="hidden" name="nFixedDepositTime"  value="<%=String.valueOf(financeInfo.getFixedDepositTime()) %>">
   <input type="hidden" name="dPayerCurrStartDate"     value="<%=financeInfo.getSDepositBillStartDate().toString().substring(0,10)%>">
	<!-- ��c415.jsp�ж�ҵ��������-->
	<input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">  
	
	   </td>
  </tr>
  </table>
	  </form>
<!--presentation end-->

<script language="javascript">

	$(document).ready(function() {
	 	$(".FormTitle").click(function(){
	      	$("#iTable").toggle();
	    });
	    $("#flexlist").flexigridenc({
			colModel : [
				{display: '���к�',  name : 'ncurrencyid', width : 220, sortable : false, align: 'center'},
				{display: '�û�',  name : 'payeracctno', width : 220, sortable : false, align: 'center'},
				{display: '��������',  name : 'payername', width : 220, sortable : false, align: 'center'},
				{display: 'ʱ�������',  name : 'ntranstype', width : 220, sortable : false, align: 'center'}
			],//�в���
			//title:'�������봦������',
			classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//Ҫ���õķ���
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// Ԥ��ָ�����н�������
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// Ԥ������ķ�ʽ
			usepager : false,
			userFunc : getFormData,
			height : 100
		});
		
	});
	
	function getFormData() 
	{
		return $.addFormData("frm","flexlist");
	}
	
	/*���ش����� */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
	<%
		/* ���� */
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
		.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
		&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
		&& (lCheckType == 1)&& !search.equals("1")) {// ��ȷ�ϡ���¼��<>ȷ����
	%>
		frm.action="<%=strContext%>/capital/check/ck001-v.jsp";
		
	<% }else{%>	
		frm.action="<%=strContext%>/capital/fixed/f006-c.jsp";
	<% } %>
		frm.submit();
	}

	/* ȡ�����˴����� */
	function cancelcheckme()
	{
		var msg = "�Ƿ�ȡ�����ˣ�";	
		
		if(!confirm(msg))
		{
			return false;
		}				
		<%

		if(blnUseITrusCert){
			OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
			OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
		%>
			var signatureValue = DoSign(frm,nameArray,valueArray);
			if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
							
		<%}
		%>
			
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "0";
		showSending();
		frm.submit();
		
	}

	function updateme()
	{
		//showMenu();
		calculateEndDate();
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�������ѱ��۸ģ��Ƿ��޸ģ�"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //����
				{
		%>
					msg = "�������ѱ��۸�!"
					alert(msg);
					return false;
		<%
				}
			}
		%>		
		frm.action="<%=strContext%>/capital/fixed/f006-c.jsp";
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
		frm.action="<%=strContext%>/capital/fixed/f009-1-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	
/* ���˴����� */
	function checkmatchme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
		function checkme()
	{
		//showMenu();
		
							//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//ǩ�����ɹ����������ύ��		
								
			<%}%>
			
		if (!confirm("�Ƿ񸴺ˣ�"))
		{
			return false;
		}	
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	
		/* ǩ�ϴ����� */
	function signme()
	{
		var msg = "�Ƿ�ǩ�ϣ�";
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //����
				{
		%>
					msg = "�������ѱ��۸ģ��Ƿ�ǩ�ϣ�"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //����
				{
		%>
					msg = "�������ѱ��۸�!"
					alert(msg);
					return false;
		<%
				}
			}else
			{
		%>		
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>	
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* ȡ��ǩ�ϴ����� */
	function cancelsignme()
	{
		var msg = "�Ƿ�ȡ��ǩ�ϣ�";
	
		if(!confirm(msg))
		{
			return false;
		}				
	

		//showMenu();
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "0";
		showSending();
		frm.submit();
		
	}
	function submitagain()
	{
		frm.action="<%=strContext%>/capital/fixed/f006-c.jsp?sign=again";
		frm.submit();
	}
	//���㶨�ڴ�������
	function calculateEndDate()
	{
		var d = ft_dateConvertDate(frm.dPayerStartDate.value) ;
		var arra = frm.dPayerStartDate.value.split("-");
		var yy = arra[0]; 
		var mm = arra[1]-1 ;
		var n = 1000000 ;
			
		n = parseInt(frm.nFixedDepositTime.value);
		d.setMonth(d.getMonth()+n);
		if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
		d=new Date(d.getYear(),d.getMonth(),0);
		}
		    
		frm.endDate.value = ft_dateConvertStr(d);
	
		    
	}
	/* �����ַ���ת��Ϊ���ڸ�ʽ */
	function ft_dateConvertDate(dateStr){
		var arra = dateStr.split("-");
		return new Date(arra[0],arra[1]-1,arra[2]) ;
	}
	
	/*���ڸ�ʽת��Ϊ�����ַ���*/
	function ft_dateConvertStr(d){
		var mon = d.getMonth()+1;
		var date = d.getDate();
		
		if(mon<=9&&mon>0)
		{
			mon = "0"+mon;
		}
		if(date<=9&&date>0)
		{
			date = "0"+date;
		}
		
		
		
		return d.getFullYear()+"-"+mon+"-"+date;
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
<%@ include file="/common/common.jsp"%>
