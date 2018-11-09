<%--
/**
 ҳ������ ��a308-v.jsp
 ҳ�湦�� : ����ҵ�񡪡�����/ȡ�����ˡ�����ʾҳ��
 ��    �� : kewen hu
 ��    �� ��2004-02-25
 */
 --%>
 
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.*" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo" %>    
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation" %>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo" %>  
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_SpecialOperationDAO"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******����ҳ��--ebank/capital/accountinfo/a308-v.jsp******\n");
    //�������
    String strTitle = "[�˻���ʷ��ϸ]";
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
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		//����ָ���ͼ��־λ--���÷�Χ������try{}��
		boolean bPayment=true;   //�����ϸ���ϱ�ʾ
		boolean bRepayment=true; //�տ��ϸ���ϱ�ʾ
		boolean bVoucher=true;	 //Ʊ������
		boolean bBank=true;	     //��������
		
		//ҳ�渨������
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;	
		
		//����״̬
	    long lNstatusid =-1;
		
		//���ø����ѡһ��Ϣ��־λ
		long lPayChecked = 1;
		//�����տ��ѡһ��Ϣ��־λ
		long lRepayChecked = 1;
		String sReturn = (String) request.getAttribute("lReturn");
		long lReturn = -1;
		if (sReturn != null && sReturn.trim().length() > 0) {
			lReturn = Long.parseLong(sReturn);
		}
		Log.print("=============lReturn="+lReturn);
        //�жϵ�ǰ����ҵ�����ͱ��
		Object o= request.getAttribute("lNoperationtypeid");
		long SELECTED=-1;
        if(o==null)
		{
        	SELECTED=-1;
        }
		else
		{
			SELECTED=Long.valueOf(request.getAttribute("lNoperationtypeid").toString()).longValue();
		}

		if( SELECTED==-1)
		{
%>	
<form name="formSave" method="post" action="" >
	<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
    <input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="">
	<input name="strFailPageURL"  type="hidden" value="">
	<input name="strContinueSave" type="hidden" value="false">
	<input name="lNtransactiontypeid" type="hidden" value="<%=SETTConstant.TransactionType.SPECIALOPERATION%>">

<TABLE class="top" height="80" width="89%">
	<TBODY>

			<TR class="tableHeader">
				<TD class="FormTitle"><B>����ҵ��</B></TD>
			</TR>			                        
			<TR>
				<TD valign="middle">					
					<TABLE align="center" width="97%">
						<TR>
							<TD nowrap>	����ҵ�����ͣ�
<%   
 		String strSQL="SELECT id,sname FROM sett_specialoperation where nstatusid=1 and nOfficeID="+sessionMng.m_lOfficeID;
 		SETTHTML.showCommonListControl(out,"lNoperationtypeid",strSQL,"sname","id",SELECTED,false,"disabled",false);
 %>
 									</TD>
                                </TR>
								<TR>
	
								   	<TD height="38" align="right">
										<INPUT class=button name=linksearch onclick="chgOpType()" type=button value=" ���Ӳ��� "> 
									</TD>
								</TR>
					      </TABLE>
                	   </TD>
          			 </TR> 		 		
		</tbody>
	</table>
</form>	
<%
		}
		else
		{
%>
<form name="formSave" method="post" action="" >
	<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
    <input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" value="">
	<input name="strFailPageURL"  type="hidden" value="">
	<input name="strContinueSave" type="hidden" value="false">
	<input name="lNtransactiontypeid" type="hidden" value="<%=SETTConstant.TransactionType.SPECIALOPERATION%>">

<TABLE class="top" height="80" width="89%">
	<TBODY>

			<TR class="tableHeader">
				<TD class="FormTitle"><B>ҵ�񸴺� ���� ����ҵ��</B></TD>
			</TR>			                        
			<TR>
				<TD valign="middle">					
					<TABLE align="center" width="97%">
						<TR>
							<TD nowrap>	����ҵ�����ͣ�
<%   
		String strSQL="SELECT id,sname FROM sett_specialoperation where nstatusid=1 and nOfficeID="+sessionMng.m_lOfficeID;
 		SETTHTML.showCommonListControl(out,"lNoperationtypeid",strSQL,"sname","id",SELECTED,false,"",false);
 %>
 									</TD>
                                </TR>
							 </TABLE>
                   </TD>
           </TR> 				
<%			
		//��ѡ��һ��ҵ������
		//¼����
		long lNinputuserid = -1;
		String strNinputuserid = "";
		//������
		long lNcheckuserid = -1;
		String strNcheckuserid = "";
		//¼��ʱ�䣺������ʱ����
		Timestamp tsDtinput = null;
		String strDtinput = "";
		//�޸�ʱ�䣺������ʱ����
		Timestamp tsDtmodify = null;
		String strDtmodify = "";
		//ȷ��ժҪ
		String strSconfirmabstract = "";
		//ȡ������ժҪ
		String strScanclecheckabstract = "";
		//ͨ��ͨ�ҶԷ����´����
		//ȷ����
		long lNconfirmuserid = -1;
		//ǩ����
		long lNsignuserid = -1;
		//����ͻ�ID
		long lNPayClientID = -1;
		//����˻�ID
		long lNpayaccountid = -1;
		//������˻�ID
		long lNpaysubaccountid = -1;
		//����浥��
		String strSpayfixeddepositno = "";
		//�����ͬID
		long lNpaycontractid = -1;
		//strNpaycontractno
		String strNpaycontractno = "";
		//����ſ�֪ͨ��ID
		long lNpayloannoteid = -1;
		//strNpayloannoteid
		String strNpayloannoteid = "";
		//���������ID
		long lNpaybankid = -1;
		//strNpaybankid
		String strNpaybankid = "";
		//���������
		String strSpayextbankno = "";
		//������е���������ID
		long lNpaysinglebankaccounttypeid = -1;
		String strNpaysinglebankaccounttypeid = "";
		//���������ID
		long lNpaygeneralledgertypeid = -1;
		//strPaygeneralledgertypeid
		String strPaygeneralledgertypeid = "";
		//������
		double dMpayamount = 0;
		String strMpayamount = "0.0";
		//�����
		long lNpaydirection = -1;
		String strNpaydirection = "��";
		//�տ��
		long lNreceivedirection = -1;
		String strNreceivedirection = "��";
		//�տ���
		double dMreceiveamount = 0;
		String strMreceiveamount = "0.0";
		//�տ������ID
		long lNreceivegeneralledgertypeid = -1;
		//strReceivegeneralledgertypeid
		String strReceivegeneralledgertypeid = "";
		//�տ�ͻ����
		long lNreceiveclientid = -1;
		//�տ���е���������ID
		long lNreceivesinglebankaccounttypei = -1;
		String strNreceivesinglebankaccounttypei = "";
		//������
		String strSreceiveextbankno = "";
		//�տ������ID
		long lNreceivebankid = -1;
		//strReceivebankid
		String strReceivebankid = "";
		//�տ�ſ�֪ͨ��ID
		long lNreceiveloannoteid = -1;
		//strReceiveloannoteid
		String strReceiveloannoteid = "";
		//�տ��ͬID
		long lNreceivecontractid = -1;
		//strReceivecontractid
		String strReceivecontractid = "";
		//�տ�浥��
		String strSreceivefixeddepositno = "";
		//�տ�˻�ID
		long lNreceiveaccountid = -1;
		//�տ���˻�ID
		long lNreceivesubaccountid = -1;
		//Ʊ������ID
		long lNbilltypeid = -1;
		String strNbilltypeid = "���";
		//Ʊ�ݺ�
		String strSbillno = "";
		//Ʊ�ݷ�������ID
		long lNbillbankid = -1;
		//�ǲ���˾�˺ţ������˺ţ�
		String strSextaccountno = "";
		//����֧Ʊ��
		String strSbankchequeno = "";
		//�ǲ���˾�˻����ƣ������˻����ƣ�
		String strSextclientname = "";
		//������
		String strSdeclarationno = "";
		//����ʡ
		String strSremitinprovince = "";
		//������
		String strSremitincity = "";
		//����������
		String strSremitinbank = "";
		//ִ����
		Timestamp tsDtexecute = null;
		//String  tsDtexecute =null;
		String strDtexecute = "";
		//��Ϣ��
		Timestamp tsDtintereststart = null;
		String strDtintereststart = "";
		//ժҪ
		String strSabstract = "";
		//ժҪID
		long lNabstractid = -1;
		//���׺�
		String strStransno = "";
		//����
		long lNcurrencyid = -1;
		//���´�
		long lNofficeid = -1;
		//�ڲ����
		long lId = -1;

		//��������lNtransactiontypeid
		long lNtransactiontypeid = -1;
		//���⽻������
		long lNoperationtypeid = -1;

		//����ͻ�code
		String strPayClientCode = "";
		//����ͻ�����
		String strPayClientName = "";
		//����˺�
		String strPayAccountNo = "";
		//lPayAccountClientID
		//long lPayAccountClientID=-1;

		//�տ�ͻ�code
		String strReceiveClientCode = "";
		//�տ�ͻ�����
		String strReceiveClientName = "";
		//strReceiveAccountNo
		String strReceiveAccountNo = "";

		String strId = "-1";
		strId = (String) request.getAttribute("lId");
		
		if (strId != null && strId.compareTo("-1") != 0)
		{
			lId = Long.parseLong(strId);
		}
		Log.print("***************lId=*********************"+lId);

		//TransSpecialOperationDelegation tsod = new TransSpecialOperationDelegation();
		Sett_TransSpecialOperationDAO transdao = new Sett_TransSpecialOperationDAO();
		Sett_SpecialOperationDAO dao = new Sett_SpecialOperationDAO();

		//TransSpecialOperationInfo tsoi = (TransSpecialOperationInfo) tsod.findDetailByID(lId);
		TransSpecialOperationInfo tsoi = (TransSpecialOperationInfo) transdao.findByID(lId);

		//default tsoi value is from c004.jsp when the c004.jsp operation failured.
		if (request.getAttribute("cuurentResult") != null)
		{
			tsoi = (TransSpecialOperationInfo) request.getAttribute("cuurentResult");
		}

		if (tsoi != null)
		{
			lNcheckuserid = tsoi.getNcheckuserid();
			strNcheckuserid = DataFormat.formatString(NameRef.getUserNameByID(lNcheckuserid));

			//����ʱ��
			tsDtmodify = tsoi.getDtmodify();
			strDtmodify = DataFormat.formatDate(tsDtmodify);
			if (strNcheckuserid == null || (strNcheckuserid.compareTo("") == 0))
			{
				strDtmodify = "";
			}
			else
			{
				strDtmodify = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			}

			//¼����
			lNinputuserid = tsoi.getNinputuserid();
			strNinputuserid = DataFormat.formatString(NameRef.getUserNameByID(lNinputuserid));
			//¼��ʱ��	
			tsDtinput = tsoi.getDtinput();
			strDtinput = DataFormat.formatDate(tsDtinput);

			//����ͻ�ID
			lNPayClientID = tsoi.getNpayclientid();
			if (lNPayClientID > 0)
			{
				lPayChecked = 1;
			}
			Log.print("tsoi.getNpayclientid(): " + lNPayClientID);
			strPayClientCode = NameRef.getClientCodeByID(lNPayClientID);
			Log.print("NameRef.getClientCodeByID(lNPayClientID): " + strPayClientCode);
			strPayClientName = NameRef.getClientNameByID(lNPayClientID);
			Log.print("NameRef.getClientNameByID(lNpayClientID): " + strPayClientName);
			//����˻�ID
			lNpayaccountid = tsoi.getNpayaccountid();
			strPayAccountNo = NameRef.getAccountNoByID(lNpayaccountid);
			Log.print("NameRef.getAccountNoByID(lNpayaccountid): " + strPayAccountNo);
			//������˻�ID
			lNpaysubaccountid = tsoi.getNpaysubaccountid();
			//����浥��
			strSpayfixeddepositno = tsoi.getSpayfixeddepositno();
			//�����ͬID
			lNpaycontractid = tsoi.getNpaycontractid();
			//strNpaycontractno
			strNpaycontractno = NameRef.getContractNoByID(lNpaycontractid);
			//����ſ�֪ͨ��ID
			lNpayloannoteid = tsoi.getNpayloannoteid();
			if (lNpayloannoteid != -1)
			{
				strNpayloannoteid = NameRef.getPayFormNoByID(lNpayloannoteid);
			}
			//���������ID
			lNpaybankid = tsoi.getNpaybankid();
			if (lNpaybankid > 0)
			{
				lPayChecked = 2;
			}
			//strNpaybankid
			strNpaybankid = NameRef.getBankNameByID(lNpaybankid);
			//������е���������ID
			lNpaysinglebankaccounttypeid = tsoi.getNpaysinglebankaccounttypeid();
			if (lNpaysinglebankaccounttypeid == SETTConstant.SingleBankAccountType.TRANSFER)
			{
				strNpaysinglebankaccounttypeid = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.TRANSFER);
			}
			else if (lNpaysinglebankaccounttypeid == SETTConstant.SingleBankAccountType.CASH)
			{
				strNpaysinglebankaccounttypeid = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.CASH);
			}

			//���������ID
			lNpaygeneralledgertypeid = tsoi.getNpaygeneralledgertypeid();
			if (lNpaygeneralledgertypeid > 0)
			{
				lPayChecked = 3;
			}
			strPaygeneralledgertypeid = NameRef.getGLTypeDescByID(lNpaygeneralledgertypeid);
			//������
			dMpayamount = tsoi.getMpayamount();
			strMpayamount = DataFormat.formatAmount(dMpayamount);
			//�����
			lNpaydirection = tsoi.getNpaydirection();
			strNpaydirection = SETTConstant.DebitOrCredit.getName(lNpaydirection);
			//�տ��
			lNreceivedirection = tsoi.getNreceivedirection();
			strNreceivedirection = SETTConstant.DebitOrCredit.getName(lNreceivedirection);
			//�տ���
			dMreceiveamount = tsoi.getMreceiveamount();
			strMreceiveamount = DataFormat.formatAmount(dMreceiveamount);

			//�տ������ID
			lNreceivegeneralledgertypeid = tsoi.getNreceivegeneralledgertypeid();
			if (lNreceivegeneralledgertypeid > 0)
			{
				lRepayChecked = 3;
				Log.print("lRepayChecked=3");
			}
			strReceivegeneralledgertypeid = NameRef.getGLTypeDescByID(lNreceivegeneralledgertypeid);
			//�տ�ͻ����
			lNreceiveclientid = tsoi.getNreceiveclientid();
			if (lNreceiveclientid > 0)
			{
				lRepayChecked = 1;
				Log.print("lRepayChecked=1");
			}
			strReceiveClientCode = NameRef.getClientCodeByID(lNreceiveclientid);
			strReceiveClientName = NameRef.getClientNameByID(lNreceiveclientid);
			//�տ���е���������ID
			lNreceivesinglebankaccounttypei = tsoi.getNreceivesinglebankaccounttypei();

			if (lNreceivesinglebankaccounttypei == SETTConstant.SingleBankAccountType.TRANSFER)
			{
				strNreceivesinglebankaccounttypei = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.TRANSFER);
			}
			else if (lNreceivesinglebankaccounttypei == SETTConstant.SingleBankAccountType.CASH)
			{
				strNreceivesinglebankaccounttypei = SETTConstant.SingleBankAccountType.getName(SETTConstant.SingleBankAccountType.CASH);
			}
			//�տ������ID
			lNreceivebankid = tsoi.getNreceivebankid();
			if (lNreceivebankid > 0)
			{
				lRepayChecked = 2;
				Log.print("lRepayChecked=2");
			}
			strReceivebankid = NameRef.getBankNameByID(lNreceivebankid);
			//�տ�ſ�֪ͨ��ID
			lNreceiveloannoteid = tsoi.getNreceiveloannoteid();
			if (lNreceiveloannoteid != -1)
			{
				strReceiveloannoteid = NameRef.getPayFormNoByID(lNreceiveloannoteid);
			}
			//�տ��ͬID
			lNreceivecontractid = tsoi.getNreceivecontractid();
			strReceivecontractid = NameRef.getContractNoByID(lNreceivecontractid);
			//�տ�浥��
			strSreceivefixeddepositno = tsoi.getSreceivefixeddepositno();
			//�տ�˻�ID
			lNreceiveaccountid = tsoi.getNreceiveaccountid();
			strReceiveAccountNo = NameRef.getAccountNoByID(lNreceiveaccountid);
			//�տ���˻�ID
			lNreceivesubaccountid = tsoi.getNreceivesubaccountid();
			//�ǲ���˾�˺ţ������˺ţ�
			strSextaccountno = tsoi.getSextaccountno();
			//����֧Ʊ��
			strSbankchequeno = tsoi.getSbankchequeno();
			//�ǲ���˾�˻����ƣ������˻����ƣ�
			strSextclientname = tsoi.getSextclientname();
			//������
			strSdeclarationno = tsoi.getSdeclarationno();
			//����ʡ
			strSremitinprovince = tsoi.getSremitinprovince();
			//������
			strSremitincity = tsoi.getSremitincity();
			//����������
			strSremitinbank = tsoi.getSremitinbank();
			//ִ����
			tsDtexecute = tsoi.getDtexecute();
			strDtexecute = DataFormat.formatDate(tsDtexecute);
			//��Ϣ��
			tsDtintereststart = tsoi.getDtintereststart();
			//����/ȡ������ժҪ
			strDtintereststart = DataFormat.formatDate(tsDtintereststart);
			//ժҪ
			strSabstract = tsoi.getSabstract();
			if (tsoi.getScanclecheckabstract() != null)
			{
				strScanclecheckabstract = tsoi.getScanclecheckabstract();
			}
			//ժҪID
			lNabstractid = tsoi.getNabstractid();
			//���׺�
			strStransno = tsoi.getStransno();
			//����
			lNcurrencyid = tsoi.getNcurrencyid();
			//���´�
			lNofficeid = tsoi.getNofficeid();
			//�ڲ����
			lId = tsoi.getId();
			Log.print("v006 lId:" + lId);
			//��������lNtransactiontypeid
			lNtransactiontypeid = tsoi.getNtransactiontypeid();
			//���⽻������
			lNoperationtypeid = tsoi.getNoperationtypeid();
			//����״̬
			lNstatusid = tsoi.getNstatusid();
		}

		//��Request�л�ò���
		//ҳ����Ʋ���
		if (request.getAttribute("strActionResult") != null)
		{
			strActionResult = (String) request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
			strAction = (String) request.getAttribute("strAction");
		}
		if (request.getAttribute("strPreSaveResult") != null)
		{
			strPreSaveResult = (String) request.getAttribute("strPreSaveResult");
		}
		//��ʽ������
		strSabstract = DataFormat.formatString(strSabstract);
		strSdeclarationno = DataFormat.formatString(strSdeclarationno);
		strSbankchequeno = DataFormat.formatString(strSbankchequeno);
		strSremitincity = DataFormat.formatString(strSremitincity);
		strSremitinprovince = DataFormat.formatString(strSremitinprovince);
		strSremitinbank = DataFormat.formatString(strSremitinbank);
		strSextclientname = DataFormat.formatString(strSextclientname);
		strSextaccountno = DataFormat.formatString(strSextaccountno);
		strSbillno = DataFormat.formatString(strSbillno);
		strSreceiveextbankno = DataFormat.formatString(strSreceiveextbankno);
		strSreceivefixeddepositno = DataFormat.formatString(strSreceivefixeddepositno);
		strSpayextbankno = DataFormat.formatString(strSpayextbankno);
		strSpayfixeddepositno = DataFormat.formatString(strSpayfixeddepositno);
		strStransno = DataFormat.formatString(strStransno);

		strPayClientCode = (strPayClientCode == null) ? "" : strPayClientCode;

		if (request.getAttribute("lNoperationtypeid") != null)
		{
			if (lNoperationtypeid != -1)
			{
				//com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo soi = (com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo) tsod.findSettingDetailByID(lNoperationtypeid);
				SpecialOperationInfo soi = (SpecialOperationInfo) dao.findByID(lNoperationtypeid);
				String strContent = "";
				if (soi != null)
				{
					strContent = soi.getScontent();
					Log.print("v006 strContent: " + strContent);
				}
				else
				{
					bPayment = false;
					bRepayment = false;
					bVoucher = false;
					bBank = false;
				}
				//������ʾ�ָ���ͼ��־λ
				if (strContent.indexOf("�����ϸ����") == -1)
				{
					bPayment = false;
				}
				else
				{
					bPayment = true;
				}
				if (strContent.indexOf("�տ��ϸ����") == -1)
				{
					bRepayment = false;
				}
				else
				{
					bRepayment = true;
				}
				if (strContent.indexOf("Ʊ������") == -1)
				{
					bVoucher = false;
				}
				else
				{
					bVoucher = true;
				}
				if (strContent.indexOf("��������") == -1)
				{
					bBank = false;
				}
				else
				{
					bBank = true;
				}
			}
		}
%>   
				          
						            
                       <TR>
                                <TD><input name="lId" type="hidden" value="<%=lId%>">
						  <input name="tsDtmodify" type="hidden" value="<%=tsDtmodify.toString()%>">
		    	   <TABLE align=center width="97%">
                    <TBODY>
                     <TR>					  
   		 <%
                if (bPayment )
                {
                //��ʾ�����ϸ��Ϣ
          %>
                         <TD width="50%">
                             <TABLE align=left border=1 borderColor=#999999 width="99%">
                                 <TBODY>
                                    <TR borderColor=#E8E8E8> 
									        <td>&nbsp;</td>                                       
                                            <TD nowrap width="150" ><U>�����ϸ����</U></TD>
                                            <TD>&nbsp;</TD>
                                    </TR>

									<!--����ͻ���ŷŴ�-->
									<TR borderColor=#E8E8E8>
									  <TD align="center"><INPUT name="lPay" type="radio" value="1"  disabled   <%=(lPayChecked==1?"checked":"")%>></TD> 	
										<td nowrap>����ͻ����:</td>
										   <TD><input name="lNPayClientID" class="box" type="Text"  disabled  value="<%=strPayClientCode%>" >
										</TD>																		
									</TR>
									
                                    <TR borderColor=#E8E8E8>
                                           <td>&nbsp;</td>
                                            <TD nowrap>����ͻ����ƣ�</TD>
                                            <TD>
											<%Log.print("v006 strPayClientName: "+strPayClientName);%>
											<textarea name="strPayClientName" disabled class="box" rows=2 cols=30><%=strPayClientName%></textarea>
                                            </TD>
                                    </TR>

									<!--//����˺ŷŴ�-->
                                  	<TR borderColor=#E8E8E8>
									    <td>&nbsp;</td>
										<TD nowrap>����˺�:</TD>									
										<td><input name="strPayAccountNo" class="box" type="Text"  disabled value="<%=strPayAccountNo%>" ></td>
									</TR>
									
									<!--���ݺŷŴ�-->
                                    <TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
									  <TD nowrap>���ݺ�:</TD>
									  <td><input name="strSpayfixeddepositno" type="text" class="box" disabled value="<%=strSpayfixeddepositno%>">  </td>
									</TR>
									
									<!--��ͬ�ŷŴ�-->
                                	<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										<TD nowrap>��ͬ��:</TD>
									    <td><input name="strNpaycontractno" type="text" class="box" disabled value="<%=strNpaycontractno%>">  </td>
									</TR>
									
									
                                    <!--�ſ�֪ͨ���ݺŷŴ�-->       
         							<TR borderColor=#E8E8E8>
									  <td>&nbsp;</td>
									  <TD nowrap>�ſ�֪ͨ���ݺ�:</TD>
									  <td><input name="strNpayloannoteid" type="text" class="box" disabled value="<%=strNpayloannoteid%>">  </td>
									</TR>
									
									<!--�����зŴ�-->
                             		<TR borderColor=#E8E8E8>
											<TD align="center"><INPUT name="lPay" type="radio" value="2"  disabled   <%=(lPayChecked==2?"checked":"")%>></TD> 
                                            <TD nowrap>������:</td>
											<td>
											<textarea name="strNpaybankid" disabled class="box"  rows=2 cols=30><%=strNpaybankid%></textarea>
											</td>
								</TR>		
									
									<!--���������ͷŴ�-->
                                       <TR borderColor=#E8E8E8>
									     <TD align="center"><INPUT name="lPay" type="radio" value="3" disabled   <%=(lPayChecked==3?"checked":"")%>></TD> 
										<TD nowrap>����������:</TD> 	
										<td ><INPUT type="Text" class=box name="strPaygeneralledgertypeid" disabled value="<%=strPaygeneralledgertypeid%>" ></td>																			
									</TR>
									
									<TR borderColor=#E8E8E8>
                      <TD nowrap>&nbsp;</TD>										
                      <TD nowrap>�� </TD>
                      <td nowrap > 
					  <%=sessionMng.m_strCurrencySymbol%>
<input type="Text"  maxlength="20"class=tar name="strMpayamount" disabled value="<%=strMpayamount%>" >
<input type="Text" class=box name="strNpaydirection" size="3" disabled value="<%=strNpaydirection%>" >
                      </td>
									</TR>
                                    			                                       
			                    </TBODY>
			                </TABLE>
							 <!--���ϸ�ڱ����-->
						</TD>
							<%
									}
									if (bRepayment )
									{
									//��ʾ�տ��ϸ��Ϣ
							%>
							<TD width="50%">
							 <!--�տϸ�ڱ�ʼ-->
							     <TABLE align=left border=1 borderColor=#999999 width="99%">
									<TBODY>
									              
									<TR borderColor=#E8E8E8>
									
                      <TD>&nbsp;</TD>
                      <TD width="150" nowrap><u>�տ��ϸ����</u></TD>
					   <TD>&nbsp;</TD>									
									</TR>
									<!--�տ�ͻ���ŷŴ�-->
									<TR borderColor=#E8E8E8>
										<TD align="center"><INPUT name="lRepay" type="radio" value="1" disabled  <%=(lRepayChecked==1?"checked":"")%>  ></TD>									
										<TD nowrap>�տ�ͻ����:</TD>
										<td ><INPUT type="Text" class=box name="strReceiveClientCode" disabled value="<%=strReceiveClientCode%>" ></td>																			
									</TR>
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>									
										<TD nowrap>�տ�ͻ����ƣ�</TD>
										<TD>
										<textarea name="strReceiveClientName" disabled class="box"  rows=2 cols=30><%=strReceiveClientName%></textarea>
										</TD>
									</TR>
									<!--�տ�˺ŷŴ�-->
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>									
										<TD nowrap>�տ�˺�:</TD>
										<TD>
										   <input name="strReceiveAccountNo" class=box disabled   value="<%=strReceiveAccountNo%>">
										</TD>
									</TR>
									<!--���ݺŷŴ�-->
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
									  <TD nowrap>���ݺ�:</TD>
									  <TD>
										   <input name="strSreceivefixeddepositno" class=box disabled   value="<%=strSreceivefixeddepositno%>">
										</TD>
								</TR>
									<!--��ͬ�ŷŴ�-->  									            
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										<TD nowrap>��ͬ��:</TD>
										 <TD>
										   <input name="strReceivecontractid" class=box disabled   value="<%=strReceivecontractid%>">
										</TD>
									</TR>
									<!--�ſ�֪ͨ���ݺŷŴ�-->
									<TR borderColor=#E8E8E8>
									<td>&nbsp;</td>
										 <TD nowrap>�ſ�֪ͨ���ݺ�:</TD>
										  <TD>
										   <input name="strReceiveloannoteid" class=box disabled   value="<%=strReceiveloannoteid%>">
										</TD>
									</TR>
									<!--�����зŴ�-->
									<TR borderColor=#E8E8E8>
									    <TD align="center"><INPUT name="lRepay" type="radio"  disabled value="2"  <%=(lRepayChecked==2?"checked":"")%>    ></TD>
										<TD nowrap>������:</td>
										 <TD>
										   <textarea name="strReceivebankid" disabled class="box"  rows=2 cols=30><%=strReceivebankid%></textarea>
										</TD>
									</TR>
									<!--�������ͷŴ�-->
									<TR borderColor=#E8E8E8>
									 <TD align="center"><INPUT name="lRepay" type="radio" value="3"  disabled   <%=(lRepayChecked==3?"checked":"")%>></TD> 
									<TD nowrap>����������:</TD> 
									<td><INPUT type="Text" class=box disabled name="strReceivegeneralledgertypeid" value="<%=strReceivegeneralledgertypeid%>" ></td>
									</TR>
									<TR borderColor=#E8E8E8>
										<td>&nbsp;</td>
                      <TD  nowrap>��</TD>
                      <TD nowrap> 
					    <%=sessionMng.m_strCurrencySymbol%>
                        <INPUT type="Text" class=tar disabled name="strMreceiveamount" value="<%=strMreceiveamount%>" >
										<INPUT type="Text" class=box disabled name="strNreceivedirection" size="3" value="<%=strNreceivedirection%>" >
										</TD>										
										</TR>
									</TBODY>
								</TABLE>
							 <!--�տϸ�ڱ����-->
							</TD>
							<%
								}
							%>
					  </TR>
					</TBODY>
				  </TABLE>
				  <!--  ������-->	
				  <TR>
				  <TD>
				  <TABLE align=center height="30" width="97%">
					 <TBODY>
				  <%
							if(bVoucher)//(bVoucher)
							{
							//��ʾƱ������
				  %>
				  <!--���� Ʊ������  ��ʼ-->
					   <TR borderColor=#ffffff>
							<TD colSpan=4><HR></TD>
						</TR>
						<TR borderColor=#ffffff>
							<TD colSpan=2 height=14 nowrap><u>Ʊ������</u></TD>
							<TD height=14 width="18%">&nbsp;</TD>
							<TD height=14 width="33%">&nbsp;</TD>
						</TR>
						<TR borderColor=#ffffff>
							<TD width="17%" nowrap>Ʊ�����ͣ�</TD>		
						<!--Ʊ��������һ��listbox-->
						<td><INPUT type="Text" class=box disabled name="strNbilltypeid" value="<%=strNbilltypeid%>" ></td>
							<!--Ʊ�ݺŷŴ�-->
							<TD width="17%" nowrap>Ʊ�ݺ�:</TD>	
							<td><INPUT type="Text" class=box disabled name="strSbillno" value="<%=strSbillno%>" ></td>	
							</TR>					
				  <!--���� Ʊ������  ����-->
				    <%
							}
							if(bBank)
							{
							//��ʾ��������
					%>
					<!--�������� ��ʼ-->
						<TR borderColor=#ffffff>
							<TD colSpan=4><HR></TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=14 nowrap><u> ��������</u></TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
						</TR>
						<TR borderColor=#ffffff>
								<!--�����˺ŷŴ�-->
								<td nowrap>�����˺�:</td>
								<td><INPUT type="Text" class=box disabled name="strSextaccountno" value="<%=strSextaccountno%>" ></td>	
								<TD width="18%" nowrap>�����˻��ͻ����ƣ�</TD>
									<TD width="33%">
									<INPUT class=box disabled name="strSextclientname" value="<%=strSextclientname%>" >
									</TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=18 width="17%" nowrap>����֧Ʊ�ţ�</TD>
									<TD height=18>
									<INPUT class=box disabled name="strSbankchequeno"  value="<%=strSbankchequeno%>"  size="20">
									</TD>
									<TD height=18 width="18%" nowrap>���б����ţ�</TD>
									<TD height=18 width="33%">
									<INPUT class=box disabled name="strSdeclarationno" value="<%=strSdeclarationno%>" >
									</TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD width="17%" nowrap>�����(ʡ)��</TD>
									<TD><INPUT class=box name="strSremitinprovince" disabled value="<%=strSremitinprovince%>" ></TD>
									<TD width="18%" nowrap>�����(��)��</TD>
									<TD width="33%"><INPUT class=box name="strSremitincity" disabled value="<%=strSremitincity%>" ></TD>
						</TR>
						<TR borderColor=#ffffff>
									<TD height=18 width="17%" nowrap>���������ƣ�</TD>
									<TD height=18><INPUT class=box name="strSremitinbank" disabled value="<%=strSremitinbank%>" ></TD>
									<TD height=18 width="18%">&nbsp;</TD>
									<TD height=18 width="33%">&nbsp;</TD>
						</TR>
								<!--�������� ����-->
	  <%
						}
	%>	
							  <!--������Ϣ ��ʼ-->
						<TR borderColor=#ffffff>
								<TD colSpan=4><HR></TD>
						</TR>
						
						<TR borderColor=#ffffff>
								<!--ժҪ�Ŵ�-->
							<td nowrap>ժҪ:</td>
							<td><INPUT type="Text" class=box name="strSabstract" disabled value="<%=strSabstract%>" ></td>	
						</TR>
						<TR borderColor=#ffffff>
									<TD width="17%" nowrap>��Ϣ�գ�</TD>
									<TD nowrap>
									<INPUT class=box name="strDtintereststart" disabled value="<%=strDtintereststart%>" >
									</TD>
									<TD align=left width="18%" nowrap>ִ���գ�</TD>
									<TD align=left width="33%" nowrap>
			                          <INPUT class=box name="strDtexecute" disabled value="<%=strDtexecute%>" >
									</TD>
						</TR>		
								<!--������Ϣ ����-->	
			 		</TBODY>
			</TABLE> 	
			</TD>
			</TR>
			<TR>
				<TD height="38"  align="center">
				 <TABLE align=center border=0 cellPadding=2 cellSpacing=2 height=15 
      width="90%">
        <TBODY>
        <TR>
          <TD colSpan=3 height=19>
           	<div align="right">
					<input type="button" name="print" value=" �� ӡ " readonly class="button" onClick="doPrint();">
					<input type="button" name="close" value=" �� �� " class="button" onClick="window.close();">
				</div>
			</TD>
			</TR>
			</TBODY>
			</TABLE>
				
				</TD>
			</TR>
			
			<TR borderColor=#ffffff>
				<TD colSpan=4><HR></TD>
			</TR>
					
			<TR>
				<TD>					
				      <table height="30" width="97%" border="0" align="center">
				        <tr> 
						  <td nowrap>���˱�ע��
						    <input type="text" class=box name="strScanclecheckabstract" value="<%=strScanclecheckabstract%>">
						  </td>
				          <td nowrap>¼���ˣ�<%=strNinputuserid%> </td>
				          <td nowrap>¼�����ڣ�<%=strDtinput%></td>		
						  <td nowrap>������:  <%=strNcheckuserid%> </td>		         
						  <td nowrap>�������ڣ�<%=strDtmodify%></td>
				          <td nowrap>״̬��<%=SETTConstant.TransactionStatus.getName(lNstatusid)%></td>
				        </tr>
				      </table>
				</TD>
			</TR> 
<script language="JavaScript">
function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a409-v.jsp?lID=<%=lId%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a409-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a308-v.jsp");
}
</script>			
		 <%
		  }
		  %>	
		 
	</TBODY>
</TABLE>
</form>
<!--ҳ��Ĭ������ �ϴ���-->

<script language="JavaScript">
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
firstFocus(formSave.strScanclecheckabstract);
<% 
	if(lNstatusid == SETTConstant.TransactionStatus.SAVE)
	{   
%>
//setSubmitFunction("doCheck()");
<%
   	}
	else if(lNstatusid == SETTConstant.TransactionStatus.CHECK)
	{
%>	
//setSubmitFunction("doUndoCheck()");	
function allFields()
{
	this.chkstrScanclecheckabstract=new Array("strScanclecheckabstract","���˱�ע","string",1);
}
<%
	}
%>
setFormName("formSave"); 
<%
	}
%>
//��ʶ�Ƿ����ύ����
var isSubmited = false;

function checkSuccess()
{
    if (confirm("���˳ɹ����Ƿ��ӡ?"))
    {
        //code
    }
	else
	{
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	}
}

function doCheck()
{
    if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }

	if (confirm("�Ƿ񸴺˴˱�ҵ������?")) 
	{
		isSubmited = true;
		document.formSave.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.formSave.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }

	if(!validateFields(formSave)) return;
	
	if (confirm("�Ƿ�ȡ�����˴˱�ҵ������?")) 
	{
		isSubmited = true;
		document.formSave.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.formSave.submit();
	}
}
function doReturn()
{
	<%
	if(lNstatusid == SETTConstant.TransactionStatus.SAVE)
	{%>
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	<%
	}else if(lNstatusid == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/special/control/c003.jsp?lNstatusids=<%=SETTConstant.TransactionStatus.CHECK%>&lNtransactiontypeid=<%=SETTConstant.TransactionType.SPECIALOPERATION%>&strSuccessPageURL=../view/v005.jsp&strFailPageURL=../view/v005.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%}else{%>
		document.location.href="<%=strContext%>/settlement/tran/special/view/v004.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strScanclecheckabstract","ȡ�����˱�ע","string",1);
} 

function chgOpType()
{
	window.location="../view/v006.jsp?lNoperationtypeid="+ formSave.lNoperationtypeid.value;
}
</script>

<%
    //�ɹ��������
			if(Constant.ActionResult.SUCCESS.equals(strActionResult) && String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction))
			{
				%>
					<script language="JavaScript">
						checkSuccess();
					</script>
				<%
			}
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
