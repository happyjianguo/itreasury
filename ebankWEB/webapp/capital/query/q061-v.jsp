<%--
/*
 * �������ƣ�q001-v.jsp
 * ����˵�������������ѯ����ҳ��
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-11
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<%
    //�������
    String strTitle = "�۽��������ѯ��";
    try {
        /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
		
		// ϵͳʱ��
        
        Timestamp dtSysDate = Env.getSystemDateTime();
		
        long lClientID = sessionMng.m_lClientID;    //�ͻ�ID
        long lTransType = -1;           //��������
        long lStatus = -1;              //״̬
        long lLoanContractID = -1;      //��ͬID
        long lFixTransferID = -1;       //����֧ȡ�˻�ID
        long lNotifyTransferID = -1;    //֪֧ͨȡ�˻�ID
        String sStartSubmit = DataFormat.formatDate(dtSysDate);       //�ύ����-��
        String sEndSubmit = DataFormat.formatDate(dtSysDate);         //�ύ����-��
        double dMinAmount = 0.0;        //���׽��-��Сֵ
        double dMaxAmount = 0.0;        //���׽��-���ֵ
        String sStartExe = "";          //ִ������-��
        String sEndExe = "";            //ִ������-��
        String sContractNo = "";        //��ͬ��
        String strFormCtrl = "";        //FORM����
        String sTemp = null;            //��ʱ��
        sTemp = (String) request.getAttribute("fromAccountType");
        if (sTemp != null && sTemp.trim().length() > 0) {
            strFormCtrl = sTemp;
            Log.print("FORM����=" + strFormCtrl);
        }
        sTemp = (String) request.getAttribute("lFixTransferID");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lFixTransferID = Long.parseLong(sTemp);
        }
        OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		if (lFixTransferID == -1) {
        	lFixTransferID = obFinanceInstrDao.getLoanAccountID(
            	sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
        Log.print("����֧ȡ�˻�ID=" + lFixTransferID);
        sTemp = (String) request.getAttribute("lNotifyTransferID");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lNotifyTransferID = Long.parseLong(sTemp);
        }
		if (lNotifyTransferID == -1) {
         	lNotifyTransferID = obFinanceInstrDao.getLoanAccountID(
            	sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
		}
        Log.print("֪֧ͨȡ�˻�ID=" + lNotifyTransferID);
        //�������л�ȡ��ѯ�����Ϣ
        Collection lstQuery = (Collection) request.getAttribute("cltQcf");
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
		long lChild = GetNumParam(request,"child");
        QueryCapForm queryCapForm = (QueryCapForm) request.getAttribute("queryCapForm");
        if (queryCapForm != null) {
            lTransType = queryCapForm.getTransType();       //�������н�������
            Log.print("��������=" + lTransType);
            sStartSubmit = queryCapForm.getStartSubmit();   //�ύ����-��
            Log.print("�ύ����-��=" + sStartSubmit);
            sEndSubmit = queryCapForm.getEndSubmit();       //�ύ����-��
            Log.print("�ύ����-��=" + sEndSubmit);
            lStatus = queryCapForm.getStatus();             //����ָ��״̬
            Log.print("����ָ��״̬=" + lStatus);
            dMinAmount = queryCapForm.getMinAmount();       //���׽��-��Сֵ
            Log.print("���׽��-��Сֵ=" + dMinAmount);
            dMaxAmount = queryCapForm.getMaxAmount();       //���׽��-���ֵ
            Log.print("���׽��-���ֵ=" + dMaxAmount);
            sStartExe = queryCapForm.getStartExe();         //ִ������-��
            Log.print("ִ������-��=" + sStartExe);
            sEndExe = queryCapForm.getEndExe();             //ִ������-��
            Log.print("ִ������-��=" + sEndExe);
        }
        //��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<form name="frmjysqcx" method="post" action="">
<input type="hidden" name="fromAccountType" value="">
<input type="hidden" name="lClientID" value="<%=lClientID%>">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
<input type="hidden" name="sNextSuccessPage" value="">
	<table width="730" border="0" cellspacing="0" cellpadding="0"  class = top>
        <tr > 
          <td colspan="4" height="25" class=FormTitle >���������ѯ</td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top >
        <tr  id="commonStatus">
          <td width="5" height="25"></td>
          <td width="130" height="25"  class="graytext" colspan="2">״̬��</td>
          <td width="430" height="25"  class="graytext" colspan="3">
<%
        //״̬
        OBHtmlCom.showQueryStatusListControl(
            out,
            "lStatus",
            lStatus,
            " onfocus=\"nextfield ='sStartSubmit';\" "
        );
%>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr  id="commonStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="submitDate">
          <td width="5" height="25"></td>
          <td width="70" height="25" class="graytext" >�ύ���ڣ�</td>
          <td width="60" height="25" class="graytext" >
            <div align="right">��</div>
          </td>
          <td width="130" height="25" class="graytext">
         		<fs_c:calendar 
	          	    name="sStartSubmit"
		          	value="" 
		          	properties="nextfield ='sEndSubmit'" 
		          	size="12"/>
		       	  <script>
	          		$('#sStartSubmit').val('<%=sStartSubmit%>');
	          	</script>
          </td>
          <td width="50" height="25" class="graytext">
            <span class="graytext">��</span>
            </td>
          <td width="250" height="25" class="graytext">
             	<fs_c:calendar 
	          	    name="sEndSubmit"
		          	value="" 
		          	properties="nextfield ='dMinAmount'" 
		          	size="12"/>
		         <script>
	          		$('#sEndSubmit').val('<%=sEndSubmit%>');
	          	</script>
          </td>
          <td width="5"></td>
        </tr>
        <tr  id="submitDateLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="70" height="25" class="graytext">��</td>
          <td width="60" height="25" class="graytext" >
            <div align="right">��Сֵ<%= sessionMng.m_strCurrencySymbol /*�����Сֵ*/%></div>
          </td>
          <td width="130" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMinAmount",
                    '<%=dMinAmount==0.0?"":DataFormat.formatDisabledAmount(dMinAmount)%>',
                    "dMaxAmount",
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
            </td>
            <td width="50" height="25" class="graytext" colspan="1">
            <span class="graytext">���ֵ<%= sessionMng.m_strCurrencySymbol /*������ֵ*/%></span>
            </td>
            <td width="250" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMaxAmount",
                    '<%=dMaxAmount==0.0?"":DataFormat.formatDisabledAmount(dMaxAmount)%>',
                    '',
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
          </td>
          <td width="5"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
      <td width="376"><%="&nbsp;"%></td>
      <td width="134"><%="&nbsp;"%></td>
          <td width="60">
            <div align="right">
			<!--img src="/webob/graphics/button_chazhao.gif" width="46" height="18" border="0" onclick="javascript:queryme();"-->
			<input type="Button" class="button"  name="querybut" onfocus="nextfield ='';"
			value="��  ��" width="46" height="18"   onclick="javascript:queryme()">
				</div>
          </td>
        </tr>
      </table>
    <br>
<%
if (strFormCtrl != null && strFormCtrl.equals("yes")) {
    String sPreConfirmDate = "";    //ǰһ��ȷ������
    String sConfirmDate = "";       //ȷ������
    String sPrePayerAcctNo = "";    //ǰһ�����ڴ��
    String sPayerAcctNo = "";       //���ڴ��

    Timestamp tsConfirmDate = null; //ȷ��ʱ��
    long lTotalCount = 0;   //���б���
    long lDeleteCount = 0;  //��ɾ������
    long lUnCheckCount = 0; //δ���˱���
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
    switch((int) lTransType) {
        case (int)-1:
        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
		case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
%>
    <table width="730"  class="ItemList">
       <tr class="tableHeader">
         <td width="70" align="center" height="18"  class="ItemTitle" nowrap>
           <div>ָ�����</div>
         </td>
        <%
            if (lTransType == -1) {
        %>               
         <td width="55" align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>��������</div>
         </td>
        <%
            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
			%>
		<td align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>������λ����</div>
         </td>
			<%
			}
			else
			{
        %>   
         <td align="center" bgcolor="#456795" height="18"  class="ItemTitle" nowrap>
           <div>��������</div>
         </td>
        <%
           	}
        %>       
    
         <td width="90" align="center" bgcolor="#456795" class="ItemTitle"  nowrap>
           <div>���</div>
         </td>
         <td width="40" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>״̬</div>
         </td>
         <td width="90" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>���ױ��</div>
         </td>
        <%
            if (lTransType == -1) {
        %>
         <td width="40" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>��ע</div>
         </td>
         <td width="80" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>�ܾ�ԭ��</div>
         </td>
        <%
            }
			else
			{
        %>
         <td width="100" align="center" bgcolor="#456795" height="18" class="ItemTitle"  nowrap>
           <div>��ע</div>
         </td>
        <%
            }			
        %>
       </tr>
<%
        if (listQuery != null) {
            //����������򣬰���ʽ��ʾ���м�¼
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
                    if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
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
         <td align="left" bgcolor="#FDF5DF" class="ItemBody" height="19">�ύ���ڣ�</td>
         <td align="left" colspan="3" bgcolor="#FDF5DF" class="ItemBody" height="19"><%=sConfirmDate%></td>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="6" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="5" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
                sPrePayerAcctNo = sPayerAcctNo;
                sPayerAcctNo = info.getPayerAcctNo();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
%>
       <tr>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF">�˺ţ�</td>
         <td colspan="3" bgcolor="#DFDFDF" class="ItemBody" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":sPayerAcctNo%></td>
         <td colspan="5" bgcolor="#DFDFDF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF"><%="&nbsp;"%></td>
         <td colspan="1" class="ItemBody" height="19" bgcolor="#DFDFDF">���ڴ�</td>
         <td colspan="3" bgcolor="#DFDFDF" class="ItemBody" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":sPayerAcctNo%></td>
         <td colspan="4" bgcolor="#DFDFDF" class="ItemBody" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
%>
       <tr>
         <td width="70" align="center"  class="ItemBody" nowrap>
            <u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name; 
                form3.txtTransType.value = this.id;doSee();" 
            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u>
           <input type="text" name="txtID" size="24" value="<%=info.getID()%>" style="display:none">
           <input type="text" name="txtTransType" size="24" value="<%=info.getTransType()%>" style="display:none">
         </td>
<%
            if (lTransType == -1) {
%>		 
         <td width="55" align="center"  height="18" class="ItemBody" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
<%
            }else if (lTransType==OBConstant.QueryInstrType.CHILDCAPTRANSFER)
			{
			%>
			<td  align="center"  height="18" class="ItemBody" nowrap>
            <%=info.getChildClientName()%></td>
			<%
			}
			else
			{
%>
         <td  align="center"  height="18" class="ItemBody" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
<%
            }
%>

         <td width="90" align="right"  class="ItemBody" nowrap>
             <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%>
         </td>
         <td  width="40" align="center"  class="ItemBody" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
         <td width="90" align="center"  class="ItemBody" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <%
            if (lTransType == -1) {
        %>
         <td  width="40" align="center"  class="ItemBody" nowrap>
             <%=info.getNote()==null || info.getNote()==""?"&nbsp;":info.getNote()%></td>
                 <td width="80" align="center"  class="ItemBody" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
		<%
            }
			else
			{
        %>
         <td width="100" align="center"  class="ItemBody" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
		<%
            }			
        %>
       </tr>
<%
                switch ((int) info.getStatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//��ɾ��
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//δ���˱���
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
        } else {
%>
      <tr>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
%>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr >
        <td colspan="4" height="1"></td>
      </tr>
      <tr class=FormTitle >
        <td  width="5" valign="top" align="left" height="22"></td>
        <td colspan="2"height="22" ><font size="3"><b >�����ʽ�����˺�</b></font></td>
        <td width="1" height="22" ></td>
      </tr>
      <tr >
        <td colspan="4" height="1"></td>
      </tr>
    </table>
    <table class="ItemList" width="730">
      <tr bgcolor="#456795" align="center" class="tableHeader">
        <td class="ItemTitle" width="70" height="13" nowrap>ָ�����</td>
        <td class="ItemTitle" width="60" height="13" nowrap>��������</td>
        <td class="ItemTitle" width="50" height="13" nowrap>��ͬ��</td>
        <td class="ItemTitle" width="40" height="13" nowrap>����ſ�����</td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
        %>
        <td class="ItemTitle" width="100" height="13" nowrap>��Ϣ���û���</td>
        <%
            } else {
        %>
        <td class="ItemTitle" width="100" height="13" nowrap>���</td>
        <%
            }
        %>
        <td class="ItemTitle" width="40" height="13" nowrap>״̬</td>
        <td class="ItemTitle" width="80" height="13" nowrap>���ױ��</td>
        <td class="ItemTitle" width="160" height="13" nowrap>��ע</td>
      </tr>
<%
        if (listQuery != null) {
            //����������򣬰���ʽ��ʾ���м�¼
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
                    if (!"".equals(sPreConfirmDate)) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setOnGoingCount(lOnGoingCount);   //�����б���
                        obCSI.setFinishedCount(lFinishedCount); //����ɱ���
                        obCSI.setRefusedCount(lRefusedCount);   //�Ѿܾ�����
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        lTotalCount = 0;    //���б���
                        lDeleteCount = 0;   //��ɾ������
                        lUnCheckCount = 0;  //δ���˱���
                        lCheckCount = 0;    //�Ѹ��˱���
                        lSignCount = 0;     //��ǩ�ϱ���
                        lOnGoingCount = 0;  //�����б���
                        lFinishedCount = 0; //����ɱ���
                        lRefusedCount = 0;  //�Ѿܾ�����
                        dTotalAmount = 0;   //�ܽ��׽��
                        vctCapSummarize.addElement(obCSI);
                    }
%>
      <tr>
         <td align="left" bgcolor="#FDF5DF" class="ItemBody" height="19">�ύ���ڣ�</td>
         <td align="left" colspan="3" bgcolor="#FDF5DF" class="ItemBody" height="19">
            <%=sConfirmDate==null || sConfirmDate==""?"&nbsp;":sConfirmDate%></td>
         <td colspan="4" bgcolor="#FDF5DF" class="ItemBody" height="19"><%="&nbsp;"%></td>
      </tr>
<%
                }
%>
      <tr  align="center">
        <td  align="center" width="70"  class="ItemBody" nowrap>
            <u style="cursor:hand" onClick="javascript:form3.txtID.value = this.name; 
                form3.txtTransType.value = this.id;doSee();" 
            id="<%=info.getTransType()%>" name="<%=info.getID()%>"><%=info.getID()%></u>
           <input type="text" name="txtID" size="24" value="<%=info.getID()%>" style="display:none">
           <input type="text" name="txtTransType" size="24" value="<%=info.getTransType()%>" style="display:none">
        </td>
        <td class="ItemBody"  height="13" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
        <td class="ItemBody" height="13" nowrap>
            <%=info.getLoanContractNo()==null || info.getLoanContractNo()==""?"&nbsp;":info.getLoanContractNo()%></td>
        <td class="ItemBody"  height="13" nowrap>
            <%=info.getPayDate()==null?"&nbsp;":DataFormat.getDateString(info.getPayDate())%></td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
			double interest = info.getRealInterest() + info.getRealCompoundInterest() + 
				info.getRealOverdueInterest() + info.getRealSuretyFee() + info.getRealCommission();
        %>
        <td class="ItemBody" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%>
			<%=interest==0.0?"&nbsp;&nbsp;":DataFormat.formatDisabledAmount(interest)%></td>
        <%
            } else {
        %>
        <td class="ItemBody" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
        <%
            }
        %>
        <td class="ItemBody"  height="13" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
        <td class="ItemBody"  height="13" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <td width="160" class="ItemBody" height="13" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
      </tr>
<%
                switch ((int) info.getStatus()) {
                    case (int) OBConstant.SettInstrStatus.DELETE:
                        lDeleteCount++;//��ɾ��
                    break;
                    case (int) OBConstant.SettInstrStatus.SAVE:
                        lUnCheckCount++;//δ���˱���
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
                dTotalAmount += info.getAmount();//�����ڷ��������ĵ��ܽ��׽��
                lTotalCount++;//���б���
                tsConfirmDate = info.getConfirmDate();
                if (listQuery != null && !listQuery.hasNext()) {
                        obCSI = new OBCapSummarizeInfo();
                        obCSI.setConfirmDate(tsConfirmDate);    //ȷ��ʱ��
                        obCSI.setTotalCount(lTotalCount);       //���б���
                        //obCSI.setDeleteCount(lDeleteCount);     //��ɾ������
                        obCSI.setUnCheckCount(lUnCheckCount);   //δ���˱���
                        obCSI.setCheckCount(lCheckCount);       //�Ѹ��˱���
                        obCSI.setSignCount(lSignCount);         //��ǩ�ϱ���
                        obCSI.setOnGoingCount(lOnGoingCount);   //�����б���
                        obCSI.setFinishedCount(lFinishedCount); //����ɱ���
                        obCSI.setRefusedCount(lRefusedCount);   //�Ѿܾ�����
                        obCSI.setTotalAmount(dTotalAmount);     //�ܽ��׽��
                        vctCapSummarize.addElement(obCSI);
                }
            }
        } else {
%>
      <tr>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
         <td  align="center" class="ItemBody"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
    }
    session.setAttribute("vctCap", vctCapSummarize);
    //session.setAttribute("queryCapForm",queryCapForm);
%>
    <br>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="450">
          <div align="left"><span class="graytext">��ѯʱ�䣺<%=dtSysDate%></span></div>
        </td>
      </tr>
    </table>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
      <td width="535"> 
        <div align="right">
		<!--img src="\webob\graphics\button_jiaoyizongjie.gif" width="83" height="18" border="0" onclick="javascript:summarize();"-->
		<input type="Button" class="button" value="�����ܽ�" width="46" height="18"   onclick="javascript:summarize()">
		</div>
        </td>
        
      <td width="137"> 
        <div align="right">
		<!--img src="\webob\graphics\button_xiazaichazhao.gif" width="119" height="18" border="0" onclick="javascript:downLoadMe();"-->
		<!--input type="Button" class="button" value="���ز��ҽ��" width="46" height="18"   onclick="javascript:downLoadMe()"-->
		</div>
        </td>
        
      <td width="58"> 
        <div align="right">
		<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="javascript:printMe();"-->
		<!--input type="Button" class="button" value="��  ӡ" width="46" height="18"   onclick="javascript:printMe()"-->
		</div>
        </td>
      </tr>
    </table>
<%
}
%>
</form>

<script language="javascript">
    /* ҳ�潹�㼰�س����� */
    window.name = "Check_Window";
    firstFocus(frmjysqcx.lStatus);
    //setSubmitFunction("queryme()");
    setFormName("frmjysqcx");
</script>

<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="strReturn" size="24" value="" style="display:none">
</form>

<script language="javascript">
    
     /* ��ѯ������ */
    function queryme() {
        if (validate() == true) {
            frmjysqcx.action = "q062-c.jsp";
            frmjysqcx.sNextSuccessPage.value = "";
            frmjysqcx.target = "";
            frmjysqcx.fromAccountType.value = "yes";
			showSending();
            frmjysqcx.submit();
        }
    }

    function doSee() {
        form3.action = "q063-c.jsp?menu=hidden";
        frmjysqcx.sNextSuccessPage.value = "";
        form3.target = "NewWindow_S";
        form3.strReturn.value = "/capital/query/q001-v.jsp";
        form3.submit();
    }

    /* У�麯�� */
    function validate() {
            var starSubmit = frmjysqcx.sStartSubmit.value;
            var endSubmit = frmjysqcx.sEndSubmit.value;
            if (starSubmit != "") {
                if(chkdate(starSubmit) == 0) {
                    alert("��������ȷ�����뿪ʼ����");
                    frmjysqcx.sStartSubmit.focus();
                    return false;
                }
            }
            if (endSubmit != "") {
                if(chkdate(endSubmit) == 0) {
                    alert("��������ȷ�������������");
                    frmjysqcx.sEndSubmit.focus();
                    return false;
                }
            }
            if ((starSubmit != "") && (endSubmit != "")) {
                if (!CompareDate(frmjysqcx.sStartSubmit, frmjysqcx.sEndSubmit, 
                    "�ύ���ڣ���ʼ���ڲ��ܴ��ڽ�������")) {
                    return false;
                }
            }

            if ((parseFloat(reverseFormatAmount(frmjysqcx.dMinAmount.value))) > (parseFloat(reverseFormatAmount(frmjysqcx.dMaxAmount.value)))) {
                alert("��С���ܴ��������");
                return false;
            }
            
            return true;
    }

    /* ��ӡ������ */
    function printMe() {
        frmjysqcx.action = "q002-c.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "q004-v.jsp";
            frmjysqcx.submit();
        }
    }
    /* ���ش����� */
    function downLoadMe() {
        frmjysqcx.action = "q002-c.jsp";
        if (validate() == true) {
            frmjysqcx.target = "NewWindow_S";
            frmjysqcx.sNextSuccessPage.value = "q005-v.jsp";
            frmjysqcx.submit();
        }
    }
    /* �����ܽᴦ���� */
    function  summarize() {
        frmjysqcx.action = "q006-v.jsp";
        frmjysqcx.sNextSuccessPage.value = "";
        frmjysqcx.target = "";
        if (validate() == true) {
            showSending();
            frmjysqcx.submit();
        }
    }
</script>

<%
        /* ��ʾ�ļ�β */
        OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
<%@ include file="/common/SignValidate.inc" %>