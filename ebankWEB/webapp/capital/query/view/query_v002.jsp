<%--
 ҳ������ ��query_v002.jsp
 ҳ�湦�� : ��Ϣ��ѯ �� ����ָ���ѯ 
 ��    �� ��leiyang
 ��    �� ��2008-12-05
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBCapSummarizeInfo" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "����ָ���ѯ";
String strTemp = "";

long lFixTransferID = -1;       //����֧ȡ�˻�ID
long lNotifyTransferID = -1;    //֪֧ͨȡ�˻�ID
long lTransType = -1;           //��������
long nEbankStatus = -1;         //����ָ��״̬
try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//��¼���
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//���Ȩ��
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	  String strNote = "";			//�����;
   	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
   	if (queryCapForm != null) {
   	 lTransType = queryCapForm.getTransType();       //�������н�������
   	 nEbankStatus = queryCapForm.getNEbankStatus();  //����ָ��״̬
   	}
   	
	if(queryCapForm == null){
		response.sendRedirect(strContext + "/capital/query/view/query_v001.jsp");
	}
	
	Collection coll = (Collection)request.getAttribute("queryCapColl");
	
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	lFixTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
	lNotifyTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
%>

<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
</script>

<form name="form1" method="post" action="../control/query_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/query_v002.jsp">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="../view/query_v001.jsp">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">  <!--��������-->
<input type="hidden" name="lDepositID" value="">
<input type="hidden" name="strDepositNo" value="">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
<input type="hidden" name="lInstructionID" value="">
<input type="hidden" name="sNextSuccessPage" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="22">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2"><%=strTitle%></span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">�������ͣ�</td>
		          	<td width="500" height="25" colspan="2">&nbsp;&nbsp;
	            	<%
	            		OBHtmlCom.showQueryTypeListControl(out,"lTransType",lTransType," onChange=\"disTransType(form1);\"  onfocus=\"nextfield ='lStatus';\" ",true);
	            	%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr id="trFixedDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //���ڴ��ݺ�
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lFixedDepositID",
			                "���ڴ��ݺ�",
			                sessionMng.m_lUserID,
			                lFixTransferID,
			                -1,
			                "",
			                1,
			                21,
			                "lFixTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
				<tr id="trNotifyDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //֪ͨ���ݺ�
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lNotifyDepositID",
			                "֪ͨ���ݺ�",
			                sessionMng.m_lUserID,
			                lNotifyTransferID,
			                -1,
			                "",
			                2,
			                21,
			                "lNotifyTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">״̬��</td>
          			<td width="500" height="25" colspan="2">&nbsp;&nbsp;
					<%
						OBHtmlCom.showQueryStatusListControl(out,"lStatus",queryCapForm.getStatus()," onfocus=\"nextfield ='sStartExe';\" ");
					%>
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
        		  <tr  id="nEbankStatus">
			           <td width="5" height="25"></td>
			          <td height="25"  class="graytext" >����ָ��״̬��</td>
			          <td height="25"  class="graytext" >&nbsp;&nbsp;
			<%
			        //״̬
			        OBConstant.BankInstructionStatus.showList(out, "nEbankStatus", 1, nEbankStatus, true, " onfocus=\"nextfield ='sStartSubmit';\" ");
			
			%>
			          </td>
			          <td width="8" height="25"></td>
			        </tr>
        		      <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">�ύ���ڣ�</td>
		          	<td width="220" height="25">
		          		��&nbsp;
		          		<fs_c:calendar 
						          	    name="sStartSubmit"
							          	value="" 
							          	properties="nextfield ='sEndSubmit'" 
							          	size="20"/>
						<script>
	          		$('#sStartSubmit').val('<%=queryCapForm.getStartSubmit()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						��&nbsp;
						<fs_c:calendar 
						          	    name="sEndSubmit"
							          	value="" 
							          	properties="nextfield ='butSearch'" 
							          	size="20"/>
						<script>
	          		$('#sEndSubmit').val('<%=queryCapForm.getEndSubmit()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		       
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">��<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="220" height="25">
		          		��<script language="JavaScript">
							createAmountCtrl_standard("form1","dMinAmount",false,"<%=queryCapForm.getMinAmount()==0.0?"":DataFormat.formatListAmount(queryCapForm.getMinAmount())%>","dMaxAmount","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> Ԫ                                           
		          	</td>
		          	<td width="280" height="25">
						��<script language="JavaScript">
							createAmountCtrl_standard("form1","dMaxAmount",false,"<%=queryCapForm.getMaxAmount()==0.0?"":DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>","sStartSubmit","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> Ԫ
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		         <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">ִ�����ڣ�</td>
		          	<td width="220" height="25">
		          		��&nbsp;
		          		<fs_c:calendar 
						          	    name="sStartExe"
							          	value="" 
							          	properties="nextfield ='sEndExe'" 
							          	size="20"/>
						<script>
	          		$('#sStartExe').val('<%=queryCapForm.getStartExe()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						��&nbsp;
						<fs_c:calendar 
						          	    name="sEndExe"
							          	value="" 
							          	properties="nextfield ='dMinAmount'" 
							          	size="20"/>
						<script>
	          		$('#sEndExe').val('<%=queryCapForm.getEndExe()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr id="trSaveInternalvirement">
						 <td  colspan="6">
                     <div align="right">
						<input type="button" value=" �� �� " class="button1" name="butSearch" onfocus="nextfield ='submitfunction';" onclick="doQuery()"/>&nbsp;
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				  <tr>
                 <td colspan="6" height="5"></td>
                 </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
  <%
    String sPreConfirmDate = "";    //ǰһ��ȷ������
    String sConfirmDate = "";       //ȷ������
    String sPrePayerAcctNo = "";    //ǰһ�����ڴ��
    String sPayerAcctNo = "";       //���ڴ��

    Timestamp tsConfirmDate = null; //ȷ��ʱ��
    long lTotalCount = 0;   //���б���
    long lDeleteCount = 0;  //��ɾ������
    long lUnCheckCount = 0; //δ���˱���
    long lApprovalingCount = 0; //�����б���
	long lApprovaledCount = 0; //������ɱ���
    long lCheckCount = 0;   //�Ѹ��˱���
    long lSignCount = 0;    //��ǩ�ϱ���
    long lOnGoingCount = 0; //�����б���
    long lFinishedCount = 0;//����ɱ���
    long lRefusedCount = 0; //�Ѿܾ�����
    double dTotalAmount = 0;//�ܽ��׽��
    double dLoanAmount = 0; //���д����
    double dDebitAmount = 0;//���н���

    Vector vctCapSummarize = new Vector(); //���OBCapSummarizeInfo����ļ���
    OBCapSummarizeInfo obCSI=null; //��Ž����ܽ���Ϣ
   
%>
	<tr>
		<td valign="top">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal">
				<thead>
			        <tr>
			        	<td  height="18" align="center" rowspan="2" nowrap width="15%">ָ�����</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">��������</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">��/��</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">�� ��</td>
					    <td  height="18" align="center" colspan="2" nowrap width="30%">�Է�����</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">״ ̬</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">����ָ��״̬</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">���ױ��</td>
						<td  height="18" align="center" rowspan="2" nowrap width="10%">�����;</td>
						<td  height="18" align="center" rowspan="2" nowrap width="5%">�� ע</td>
			        </tr>
			          <tr> 
				        <%
				            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT 
				            		|| lTransType == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) {
				        %>
				        <td  align="center"  nowrap width="20%"> <div>���ڴ������</div></td>
				        <%
				            } else {
				        %>
				        <td  align="center"  nowrap width="20%"> <div>����</div></td>
				        <%
				            }
				        %>
				        <td  align="center"  nowrap width="10%"> <div>�˺�</div></td>
				      </tr>
			    </thead>
				<tbody>
				<%
				if(coll != null){
					Iterator listQuery = coll.iterator();
					String strFormatConfirmDate = "";
					long nPayerAcctID = -1;
		            while(listQuery.hasNext()) {
		                FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
		                if(info.getTransNo() == null){
		                	info.setTransNo("");
		                }
		                if(info.getNote() == null){
		                	info.setNote("");
		                }
		                if(info.getReject() == null){
		                	info.setReject("");
		                }
		                strNote = info.getNote()== null?"":info.getNote().trim();
		                sPreConfirmDate = sConfirmDate;
               			 sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
		                if(strFormatConfirmDate.equals("") || !strFormatConfirmDate.equals(DataFormat.getDateString(info.getConfirmDate()))){
		                	strFormatConfirmDate = DataFormat.getDateString(info.getConfirmDate());
			                nPayerAcctID = info.getPayerAcctID();
			                 if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setLApprovalingCount(lApprovalingCount); //�����б���
                        obCSI.setLApprovaledCount(lApprovaledCount);  //������ɱ���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setOnGoingCount(lOnGoingCount);   //�����б���
                        obCSI.setFinishedCount(lFinishedCount); //����ɱ���
                        obCSI.setRefusedCount(lRefusedCount);   //�Ѿܾ�����
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        obCSI.setLoanAmount(dLoanAmount);       //���д����
                        obCSI.setDebitAmount(dDebitAmount);     //���н���
                        lTotalCount = 0;    //���б���
                        lDeleteCount = 0;   //��ɾ������
                        lUnCheckCount = 0;  //δ���˱���
                        lApprovalingCount = 0; //�����б���
						lApprovaledCount = 0; //������ɱ���
                        lCheckCount = 0;    //�Ѹ��˱���
                        lSignCount = 0;     //��ǩ�ϱ���
                        lOnGoingCount = 0;  //�����б���
                        lFinishedCount = 0; //����ɱ���
                        lRefusedCount = 0;  //�Ѿܾ�����
                        dTotalAmount = 0;   //�ܽ��׽��
                        dLoanAmount = 0;    //���д����
                        dDebitAmount = 0;   //���н���
                        vctCapSummarize.addElement(obCSI);
                    }
		        %>
			        <tr>
						<td height="19" align="left">�ύ���ڣ�</td>
						<td height="19" align="left" colspan="9"><%=strFormatConfirmDate%></td>
			        </tr>
			        <tr>
			            <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
						<td height="19" align="left" bgcolor="#DFDFDF">�ʺţ�</td>
						<td height="19" align="left" colspan="9"  bgcolor="#DFDFDF"><%=info.getPayerAcctNo()%></td>
			        </tr>
			    <%
		                }
		                else if(nPayerAcctID != info.getPayerAcctID()) {
		                	nPayerAcctID = info.getPayerAcctID();
		        %>
			        <tr>
			            <td colspan="1"   height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
						<td height="19" align="left" bgcolor="#DFDFDF">�ʺţ�</td>
						<td height="19" align="left" colspan="9" bgcolor="#DFDFDF"><%=info.getPayerAcctNo()%></td>
			        </tr>
		        <%
		                }
			    %>
			        <tr>
			        	 <td align="center" nowrap> <u style="cursor:hand" onclick="javascript:form3.txtID.value = this.name; 
               			 form3.txtTransType.value = this.id;doSee();" 
			            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u> 
			        <input type="text" name="txtID2" size="20" value="<%=info.getID()%>" style="display:none" class="box"> 
			        <input type="text" name="txtTransType2" size="20" value="<%=info.getTransType()%>" class="box" style="display:none"> 
			     	 </td>
						<td height="25" align="center">
						<%
							if(info.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY){
								 if(info.getSBatchNo()!=null && info.getSBatchNo().length()>0) {
									 out.println("��������-���л��");
								 }
								 else {
									 out.println(OBConstant.SettInstrType.getName(info.getTransType()));
								 }
							}
							else{
								out.println(OBConstant.SettInstrType.getName(info.getTransType()));
							}
						%>
						</td>
						<td height="25" align="center"><%="��"%></td>
						<td height="25" align="right"><%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
						 <%
            			if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT
            				||lTransType==OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT) {
				        %>
				      <td align="center"    nowrap> <%=info.getFixedDepositTime()>10000?(info.getFixedDepositTime()-10000)+"��":info.getFixedDepositTime()+"����"%></td>
				      <%
				            } else {
				        %>
				      <td  align="center"  style="word-break:break-all"> <%=info.getPayeeName()==null || info.getPayeeName()==""?"&nbsp;":info.getPayeeName()%></td>
				      <%
				            }
				        %>
						<td height="25" align="center"><%=info.getPayeeAcctNo()==null?"":info.getPayeeAcctNo()%></td>
						<td height="25" align="center" nowrap><%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
						<td align="center"><%=info.getEbankStatus()==-1?"&nbsp;":OBConstant.BankInstructionStatus.getName(info.getEbankStatus()) %> </td>
						<td height="25" align="center"><%=info.getTransNo()%></td>
						  <%
							if(strNote.length()<=6){
						%>
								<td height="20" nowrap align="center"><%=strNote%></td>
						<%
							}else{
								%>
								<td  height="20" nowrap align="center" id="<%=info.getID()%>"
								 	onmouseover="showHelper('<%="#"+info.getID()%>', '�����;', '<%=strNote %>',50)" onmouseout="$('#_Popup_help').remove()" >
									<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
								</td>
								<%
							}
						%>
						<td height="25" align="left"><%=info.getReject()%></td>
			        </tr>
		        <% 
                switch ((int) info.getStatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//��ɾ��
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//δ���˱���
                    break;
                    case (int) OBConstant.SettInstrStatus.APPROVALING:
                        lApprovalingCount++;//�����б���
                    break;
                    case (int) OBConstant.SettInstrStatus.APPROVALED:
                        lApprovaledCount++;//������ɱ���
                    break;
                    case (int) OBConstant.SettInstrStatus.CHECK:
                        lCheckCount++;//�Ѹ��˱���
                    break;
                    case (int) OBConstant.SettInstrStatus.SIGN:
                        lSignCount++;//��ǩ�ϱ���
                    break;
                    case (int) OBConstant.SettInstrStatus.DEAL:
                        lOnGoingCount++;//�����б���
                    break;
                    case (int) OBConstant.SettInstrStatus.FINISH:
                        lFinishedCount++;//����ɱ���
                    break;
                    case (int) OBConstant.SettInstrStatus.REFUSE:
                        lRefusedCount++;//�Ѿܾ�����
                    break;
                    default :
                    break;
                }
                if (info.getTransType() == -1000) {
                    dLoanAmount += info.getAmount(); //���д����
                } else {
                    dDebitAmount += info.getAmount();//���н���
                }
                dTotalAmount += info.getAmount();//�����ڷ��������ĵ��ܽ��׽��
                lTotalCount++;//���б���
                tsConfirmDate = info.getConfirmDate();
                if (listQuery != null && !listQuery.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setLApprovalingCount(lApprovalingCount); //�����б���
                        obCSI.setLApprovaledCount(lApprovaledCount);  //������ɱ���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setOnGoingCount(lOnGoingCount);   //�����б���
                        obCSI.setFinishedCount(lFinishedCount); //����ɱ���
                        obCSI.setRefusedCount(lRefusedCount);   //�Ѿܾ�����
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        obCSI.setLoanAmount(dLoanAmount);       //���д����
                        obCSI.setDebitAmount(dDebitAmount);     //���н���
                        vctCapSummarize.addElement(obCSI);
		       
		            }
				}
				}
				else{
				%>
			        <tr>
			        	<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
			        </tr>
			    <%
			    }
			    session.setAttribute("vctCap", vctCapSummarize);
			    %>
			    </tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height="25">��ѯʱ�䣺<%=DataFormat.getDateString(sessionMng.dLastLoginTime)%></td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
						<input type="button" value=" �����ܽ� " class="button1" name="butQuery01" onclick="javascript:summarize()"/>&nbsp;
						<input type="button" value=" ���ز��ҽ�� " class="button1" name="butQuery02" onclick="javascript:downLoadMe()"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</tbody>
</table>
</form>
	
	
<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
   <input type="hidden" name="search" value="1">
   
</form>
<script language="javascript">
/* ҳ�潹�㼰�س����� */
firstFocus(form1.lTransType);
//setSubmitFunction("doQuery()");
setFormName("form1");
disTransType(form1);
function document.onkeydown(){ var e = window.event; if(e.keyCode==13 && e.srcElement.type != 'button'){ e.keyCode = 9; return; } } 
//���Ʋ�ͬ���ʽ����ʾ
function disTransType(form){
	var lTransType = form.lTransType.value;
	var oTrFixedDeposit = document.getElementById("trFixedDeposit");
	var oTrNotifyDeposit = document.getElementById("trNotifyDeposit");
	
	form.lFixedDepositIDCtrl.value = "";
	oTrFixedDeposit.style.display = "none";
	form.lNotifyDepositIDCtrl.value = "";
	oTrNotifyDeposit.style.display = "none";

	if(lTransType == "<%=OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER%>"){
		oTrFixedDeposit.style.display = "block";
		oTrNotifyDeposit.style.display = "none";
	}
	
	if(lTransType == "<%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW%>"){
		oTrFixedDeposit.style.display = "none";
		oTrNotifyDeposit.style.display = "block";
	}
}
function toFinanceCountQuery(form){
	form.target = "";
	form.action = "../control/query_c003.jsp";
	form.strSuccessPageURL.value = "../view/query_v003.jsp";
	form.strFailPageURL.value = "../view/query_v001.jsp";
	form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	showSending();
    form.submit();
}


function doQuery()
{
	form1.action = "../control/query_c001.jsp";
	form1.strSuccessPageURL.value = "../view/query_v002.jsp";
	form1.strFailPageURL.value = "../view/query_v001.jsp";
	form1.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	if (!validate(form1)) return;
	
	if(form1.lFixedDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lFixedDepositID.value;
		form1.strDepositNo.value = form1.lFixedDepositIDCtrl.value;
	}
	if(form1.lNotifyDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lNotifyDepositID.value;
		form1.strDepositNo.value = form1.lNotifyDepositIDCtrl.value;
	}

	showSending();
    form1.submit();
}

  /* �����ܽᴦ���� */
    function  summarize() {
    	form1.method="post";
        form1.action = "../q006-v.jsp";
        form1.sNextSuccessPage.value = "";
        form1.target = "";
        if (validate(form1) == true) {
            showSending();
            form1.submit();
        }
    }
 /* ���ش����� */
    function downLoadMe() {
    	form1.method="post";
        form1.action = "../q002-c.jsp";
        if (validate(form1) == true) {
            form1.target = "NewWindow_S";
            form1.sNextSuccessPage.value = "q005-v.jsp";
            form1.submit();
        }
    }
   function doSee() {
        form3.action = "../q003-c.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
        form3.target = "_formwin";    
        form3.submit(); 
        form3.target = "";         
    }
function validate(form){

	if(!checkDate(form.sStartExe,0,"ִ������"))
	{
		form.sStartExe.value="";
		form.sStartExe.focus();
		return false;
	}
	
	if(!checkDate(form.sEndExe,0,"ִ������"))
	{
		form.sEndExe.value="";
		form.sEndExe.focus();
		return false;
	}
	
	if(form.sStartExe.value!="" && form.sEndExe.value!="")
	{
		if(CompareDateString(form.sEndExe.value,form.sStartExe.value))
		{
			alert("ִ��������������ִ������");
			return false;
		}
	}
	
	if(!checkAmount(form.dMinAmount, 2, "�����")){
		return false;
	}
	
	if(!checkAmount(form.dMaxAmount, 2, "�����")){
		return false;
	}
	
	if(form.dMinAmount.value!="" && form.dMaxAmount.value!="")
	{
		if((parseFloat(reverseFormatAmount(form.dMinAmount.value))) > (parseFloat(reverseFormatAmount(form.dMaxAmount.value)))) {
        	alert("��С���ܴ��������");
            return false;
        }
	}
	
	if(!checkDate(form.sStartSubmit,1,"�ύ������"))
	{
		form.sStartSubmit.value="";
		form.sStartSubmit.focus();
		return false;
	}
	
	if(!checkDate(form.sEndSubmit,1,"�ύ������"))
	{
		form.sEndSubmit.value="";
		form.sEndSubmit.focus();
		return false;
	}
	
	if(form.sStartSubmit.value!="" && form.sEndSubmit.value!="")
	{
		if(!CompareDateString(form.sStartSubmit.value,form.sEndSubmit.value))
		{
			alert("�ύ���������������ύ������");
			return false;
		}
	}

	return true;
}
</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}

%>
<%@ include file="/common/SignValidate.inc"%>