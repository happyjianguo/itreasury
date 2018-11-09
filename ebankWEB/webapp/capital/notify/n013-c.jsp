<%--
/*
 * �������ƣ�n013-c.jsp
 * ����˵����֪֧ͨȡ�ύ����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��13��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.approval.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.common.attach.bizlogic.*,
                 com.iss.itreasury.safety.util.*"
%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.facade.factory.*"%>
<%@ page import="com.iss.itreasury.safety.facade.imp.*"%>
<%@ page import="com.iss.itreasury.safety.facade.*"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[֪֧ͨȡ]";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	Timestamp now = new Timestamp(System.currentTimeMillis());//��ȡϵͳ��ǰʱ��
%>

<%	
	/* ����Ա�����Ӧ���� */
	long lInstructionID = -1;//ָ�����
	long templInstructionID = -1;//��ʱָ����ţ�ҵ����־ʹ��
	long strAction=-1;
    String strPayerName = "";//  �������
	String strPayerBankNo = "";// �����˺�
	String strPayerAcctNo = "";// ����˺�
	long lPayerAcctID = -1;//����˻�ID
	String strDepositNo = "";//֪ͨ���ݺ�
	long lSubAccountID = -1; //���˻�ID
	Timestamp tsNotifyDepositStart = null;//֪ͨ�����ʼ��
	long lNoticeDay = -1;//֪ͨ���Ʒ��
	double dDepositAmount = 0.0;//֪ͨ�浥���
	double dDepositBalance = 0.0;//֪ͨ�浥���
	
	long lRemitType = 0;// �����ʽ
	long lPayeeAcctID = -1;//�����տ�˻�ID
	String strPayeeName = "";// �����տ����
	String strPayeeBankNo = "";// �����տ�����˺�
	String strPayeeAcctNo = "";// �����տ�˺�
	String strPayeeBankName = "";// �������������
	String strPayeeProv = "";// �������ʡ
	String strPayeeCity = "";// ���������
	
	long lInterestPayeeAcctID = -1;//��Ϣ�տ�˻�ID
	long lInterestRemitType = 0;// ��Ϣ��ʽ
	String strInterestPayeeName = "";// ��Ϣ�տ����
	String strInterestPayeeBankNo = "";// ��Ϣ�տ�����˺�
	String strInterestPayeeAcctNo = "";// ��Ϣ�տ�˺�
	String strInterestPayeeBankName = "";// ��Ϣ����������
	String strInterestPayeeProv = "";// ��Ϣ����ʡ
	String strInterestPayeeCity = "";// ��Ϣ������
		
	double dAmount = 0.0;// ֧ȡ������
	Timestamp tsExecute = null;// ִ����
	String strNote = "";// �����;
	
	String tempTransCode = ""; //��ʱ���׺�
	String strTemp = "";
	long AbstractID = -1;//�����;ID
	String AbstractCode = "";//�����;���	
	
	//Modify by leiyang date 2007/07/25
	boolean isSommitTimes = false;
	String msg = "";
	String operate = "";
	Timestamp dtmodify = null;
	
	String signatureValue = "";
	String signatureOriginalValue = "";	
	long timestamp = -1;
	
	/* ��ʼ����Ϣ�� */
	FinanceInfo financeInfo = new FinanceInfo();
	ClientCapInfo clientCapInfo = new ClientCapInfo();
	TransInfo transinfo = new TransInfo();
	
	OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
    OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();  
    
    String lsign = null;
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}
		
    	
%>

<%
	/* �û���¼�����Ȩ��У�� */
	try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
        
                     
    //�ж��Ƿ���Ҫ������ by ypxu 2007-05-10
	InutParameterInfo inutParameterInfo = new InutParameterInfo();
	inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
	inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
	inutParameterInfo.setClientID(sessionMng.m_lClientID);
	inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
	boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
%>

<!--Get Data start-->
<%
		/* ��FORM���л�ȡ��Ӧ���� */
		/* ָ����� */
		
		
		strAction=GetNumParam(request,"strAction");
		Log.print("��������=" + strAction);
						
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("ָ�����=" + lInstructionID);
		templInstructionID = lInstructionID;
		
		/* ������� */
		strPayerName = GetParam(request,"sPayerName");// �������
		Log.print("�������=" + strPayerName);
		
		lPayerAcctID = GetNumParam(request,"nPayerAccountID");//����˻�ID
		Log.print("����˻�ID=" + lInstructionID);
		
		strPayerBankNo = GetParam(request,"sBankAccountNO");// �����˺�
		Log.print("�����˺�=" + lPayerAcctID);

		strPayerAcctNo = GetParam(request,"sPayerAccountNoZoomCtrl");// ����˺�
		Log.print("����˺�=" + strPayerAcctNo);
		
		strDepositNo = GetParam(request,"depositNo");//֪ͨ���ݺ�
		Log.print("֪ͨ���ݺ�=" + strDepositNo);
		
		lSubAccountID =  GetNumParam(request,"sNotifyDepositNo"); //���˻�ID
		Log.print("���˻�=" + lSubAccountID);
		
		tsNotifyDepositStart = DataFormat.getDateTime((String)request.getParameter("tsNotifyDepositStart"));//֪ͨ�����ʼ��
		Log.print("֪ͨ�����ʼ��=" + tsNotifyDepositStart);
		
		lNoticeDay = GetNumParam(request,"nNoticeDay");//֪ͨ���Ʒ��
		Log.print("֪ͨ�������=" + lNoticeDay);
		
		dDepositAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dDepositAmount"))).doubleValue();// ���ڴ浥���
		Log.print("֪ͨ�浥���=" + dDepositAmount);
		
		dDepositBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dDepositBalance"))).doubleValue();// ���ڴ浥����
		Log.print("֪ͨ�浥���=" + dDepositBalance);
		
		
		/* �����տ���� */
		 lRemitType = GetNumParam(request,"nRemitTypeHid");// ��ʽ
		 //lRemitType =OBConstant.SettRemitType.INTERNALVIREMENT;
		Log.print("��ʽ=" + lRemitType);
		/*
		lPayeeAcctID = GetNumParam(request,"payeeAcctID");//�����տ�˻�ID
		Log.print("�տ�˻�ID=" + lPayeeAcctID);
		*/
		lInterestRemitType = GetNumParam(request,"nInterestRemitTypeHid");// ��Ϣ��ʽ
		//lInterestRemitType = OBConstant.SettRemitType.INTERNALVIREMENT;
		Log.print("��Ϣ��ʽ=" + lInterestRemitType);
		/*
		lInterestPayeeAcctID = GetNumParam(request,"interestPayeeAcctID");//��Ϣ�տ�˻�ID
		Log.print("��Ϣ�տ�˻�ID=" + lInterestPayeeAcctID);
		*/
		strTemp = (String)request.getAttribute("strTransTypeNo");	 	
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tempTransCode = strTemp;
		}
		strTemp = (String)request.getAttribute("operate");	 	
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    operate = strTemp;
		}
		strTemp = (String)request.getAttribute("dtmodify");	 	
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dtmodify = DataFormat.getDateTime(strTemp);
		}
		
		strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureValue = strTemp;
		}
		
		strTemp = request.getParameter(SignatureConstant.ORIGINALVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureOriginalValue = strTemp;
		}	
		
		strTemp = request.getParameter("timestamp");
		if(strTemp != null && !strTemp.equals("")){
			timestamp = Long.valueOf(strTemp).longValue();
			
		}				
	
		int ier=0;
		ier =(int)lRemitType;
		switch ((int)lRemitType)
		{		
			/* ��ʽ���и��� */
			case (int)OBConstant.SettRemitType.BANKPAY :
			
				strPayeeName = GetParam(request,"sPayeeNameZoomAcctCtrl");// �տ����
				Log.print("�տ����=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAcctNoZoomCtrl");// �տ�˺�
				Log.print("�տ�˺�=" + strPayeeAcctNo);
				
				strPayeeProv = GetParam(request,"sPayeeProv");// ����ʡ
				Log.print("����ʡ=" + strPayeeProv);
				
				strPayeeCity = GetParam(request,"sPayeeCity");// ������
				Log.print("������=" + strPayeeCity);
				
				strPayeeBankName = GetParam(request,"sPayeeBankName");// ����������
				Log.print("����������=" + strPayeeBankName);
				
				lPayeeAcctID = GetNumParam(request,"nPayeeAccountID2");//�����տ�˻�ID
				Log.print("�տ�˻�ID=" + lPayeeAcctID);
		
				break;
				
			/* ��ʽ�ڲ�ת�� */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strPayeeName = GetParam(request,"sPayeeName");// �տ����
				Log.print("�տ����=" + strPayeeName);
				
				strPayeeBankNo = GetParam(request,"sPayeeAccountInternalInternal");// �տ�����˺�
				Log.print("�տ�����˺�(Iternal)=" + strPayeeBankNo);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// �տ�˺�
				Log.print("�տ�˺�=" + strPayeeAcctNo);
				
				lPayeeAcctID = GetNumParam(request,"nPayeeAccountID1");//�����տ�˻�ID
				Log.print("�տ�˻�ID=" + lPayeeAcctID);
		
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		
		switch ((int)lInterestRemitType)
		{		
			/* ��ʽ���и��� */
			case (int)OBConstant.SettRemitType.BANKPAY :
			
				strInterestPayeeName = GetParam(request,"sInterestPayeeNameZoomAcctCtrl");// �տ����
				Log.print("��Ϣ�տ����=" + strInterestPayeeName);
				
				strInterestPayeeAcctNo = GetParam(request,"sInterestPayeeAcctNoZoomCtrl");// �տ�˺�
				Log.print("��Ϣ�տ�˺�=" + strInterestPayeeAcctNo);
				
				strInterestPayeeProv = GetParam(request,"sInterestPayeeProv");// ����ʡ
				Log.print("��Ϣ����ʡ=" + strInterestPayeeProv);
				
				strInterestPayeeCity = GetParam(request,"sInterestPayeeCity");// ������
				Log.print("��Ϣ������=" + strInterestPayeeCity);
				
				strInterestPayeeBankName = GetParam(request,"sInterestPayeeBankName");// ����������
				Log.print("��Ϣ����������=" + strInterestPayeeBankName);
				
				lInterestPayeeAcctID = GetNumParam(request,"nInterestPayeeAccountID2");//��Ϣ�տ�˻�ID
				Log.print("��Ϣ�տ�˻�ID=" + lInterestPayeeAcctID);
		
				break;
				
			/* ��ʽ�ڲ�ת�� */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strInterestPayeeName = GetParam(request,"sInterestPayeeName");// �տ����
				Log.print("��Ϣ�տ����=" + strInterestPayeeName);
				
				strInterestPayeeBankNo = GetParam(request,"sInterestPayeeAccountInternalInternal");// �տ�����˺�
				Log.print("��Ϣ�տ�����˺�(Iternal)=" + strInterestPayeeBankNo);
				
				strInterestPayeeAcctNo = GetParam(request,"sInterestPayeeAccountInternalCtrl");// �տ�˺�
				Log.print("��Ϣ�տ�˺�=" + strInterestPayeeAcctNo);
				
				lInterestPayeeAcctID = GetNumParam(request,"nInterestPayeeAccountID1");//��Ϣ�տ�˻�ID
				Log.print("��Ϣ�տ�˻�ID=" + lInterestPayeeAcctID);
		
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* �������� */
		if(request.getParameter("amount") != null)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("amount"))).doubleValue();// ���
			Log.print("���=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute = DataFormat.getDateTime((String)request.getParameter("tsExecute"));// ִ����
			Log.print("ִ����=" + tsExecute);
		}
		
		strNote = GetParam(request,"sNoteCtrl").trim();// �����;
		Log.print("�����;=" + strNote);
	
		//Modify by leiyang date 2007/07/25
		Timestamp timeNow = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		
		//��ִ���յ��ڿ�����
		if(tsExecute.compareTo(timeNow) == 0){
			String strCommitTime = "";
			long isControl = -1;
	
			OBCommitTime cTime = new OBCommitTime();
			cTime.setOfficeId(sessionMng.m_lOfficeID);
			cTime.setCurrencyId(sessionMng.m_lCurrencyID);
			OBCommitTime result = OBCommitTimeBiz.findOBCommitTime(cTime);
			
			if(result != null){
				strCommitTime = result.getCommitTime();
				isControl = result.getIsControl();
				
				timeNow = Env.getSystemDateTime();
				
				//��ǰСʱ�ͷ���
				int lTNHours =  timeNow.getHours();
				int lTNMinutes = timeNow.getMinutes();
				
				String commitTimes[] = strCommitTime.split(":");
				//ֹͣ���յ�Сʱ�ͷ���
				int lCTHours = Integer.parseInt(commitTimes[0]);
				int lCTMinutes = Integer.parseInt(commitTimes[1]);
				
				if(lCTHours < lTNHours){
					if(isControl == SETTConstant.OBCommitTimeControlType.RIGID){
						throw new IException("�ύʱ���ѳ���������ٽ���ʱ��");
					}
					else{
						tsExecute.setDate(tsExecute.getDate() + 1);
						isSommitTimes = true;
					}
				}
				else if(lCTHours == lTNHours) {
					if(lCTMinutes < lTNMinutes){
						if(isControl == SETTConstant.OBCommitTimeControlType.RIGID){
							throw new IException("�ύʱ���ѳ���������ٽ���ʱ��");
						}
						else{
							tsExecute.setDate(tsExecute.getDate() + 1);
							isSommitTimes = true;
						}
					}
				}
			}
		}
	
	// ����ǩ��
	
		if(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL
				|| strAction==OBConstant.SettInstrStatus.SAVE
				|| strAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL) 
		{
			SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
			SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
			boolean validate = facade.validateSignFromClient(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW,request);
			if(!validate)
			{
				throw new Exception("��֤�ͻ���ǩ����Ϣʧ��!");
			}			
		}
    

		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
		/* ����FORM���е���Ӧ���� ������Ϣ��*/
		/* ָ����� */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		
		financeInfo.setTimestamp(timestamp);
		financeInfo.setSignatureOriginalValue(signatureOriginalValue);
		financeInfo.setSignatureValue(signatureValue);
				
		/* ������� */
		financeInfo.setPayerName( strPayerName );// �������
		financeInfo.setPayerAcctID ( lPayerAcctID );//����˻�ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// �����˺�
		financeInfo.setPayerAcctNo( strPayerAcctNo );// ����˺�
		financeInfo.setDepositNo( strDepositNo );// ֪ͨ���ݺ�
		financeInfo.setSubAccountID (lSubAccountID);//���˻�ID
		financeInfo.setDepositStart( tsNotifyDepositStart );// ֪ͨ�����ʼ��
		financeInfo.setNoticeDay( lNoticeDay );// ֪ͨ���Ʒ��
		financeInfo.setDepositAmount( dDepositAmount );// ֪ͨ�浥���
		financeInfo.setDepositBalance( dDepositBalance );// ֪ͨ�浥���
		/* �տ���� */
		financeInfo.setRemitType ( lRemitType );// ��ʽ
		financeInfo.setInterestRemitType( lInterestRemitType );//��Ϣ��ʽ
		financeInfo.setPayeeName(strPayeeName) ;
		clientCapInfo.setPayeeName(strPayeeName);// �տ����
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); 
		clientCapInfo.setPayeeAccoutNO( strPayeeAcctNo );// �տ�˺�
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//�տ�˻�ID
		financeInfo.setInterestPayeeAcctID ( lInterestPayeeAcctID );//�տ�˻�ID
		financeInfo.setInterestPayeeName(strInterestPayeeName) ;
		financeInfo.setInterestPayeeAcctNo (strInterestPayeeAcctNo); 
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setPayeeProv( strPayeeProv );
				clientCapInfo.setPayeeProv( strPayeeProv );// ����ʡ
				financeInfo.setPayeeCity(strPayeeCity );
				clientCapInfo.setCity( strPayeeCity );// ������
				financeInfo.setPayeeBankName( strPayeeBankName );
				clientCapInfo.setPayeeBankName( strPayeeBankName );// ����������
				financeInfo.setIsCpfAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//�����Ͳ����ڲ��˻�
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setPayeeBankNo( "" );// �տ�����˺�
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//�����Ͳ����ڲ��˻�
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		switch ((int)lInterestRemitType)
		{
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setInterestPayeeProv( strInterestPayeeProv );
				clientCapInfo.setPayeeProv( strInterestPayeeProv );// ����ʡ
				financeInfo.setInterestPayeeCity(strInterestPayeeCity );
				clientCapInfo.setCity( strInterestPayeeCity );// ������
				financeInfo.setInterestPayeeBankName( strInterestPayeeBankName );
				clientCapInfo.setPayeeBankName( strInterestPayeeBankName );// ����������
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );//�����Ͳ����ڲ��˻�
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setInterestPayeeBankNo( "" );// �տ�����˺�
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//�����Ͳ����ڲ��˻�
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* �������� */
		financeInfo.setAmount( dAmount );// ���
		financeInfo.setExecuteDate( tsExecute );// ִ����
		financeInfo.setConfirmDate( tsExecute );//ȷ������
		financeInfo.setNote( strNote );// �����;
		/* ��session�л�ȡ��Ӧ���� */
		financeInfo.setConfirmUserID( sessionMng.m_lUserID );
		clientCapInfo.setInputUserID( sessionMng.m_lUserID );// ȷ����ID
		financeInfo.setClientID( sessionMng.m_lClientID );
		clientCapInfo.setClientID( sessionMng.m_lClientID );// �����ύ��λ
		financeInfo.setCurrencyID( sessionMng.m_lCurrencyID );
		clientCapInfo.setCurrencyID( sessionMng.m_lCurrencyID );// ���ױ���
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//Ĭ�ϰ��´�ID
		/* ȷ�����Ͻ������ͺͻ�ʽ */
		financeInfo.setTransType( OBConstant.SettInstrType.NOTIFYDEPOSITDRAW );			
		/*ȷ��ָ��״̬*/
		if(financeInfo.getStatus() == -1)
		{
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}
		financeInfo.setDtModify(dtmodify);
			
		if(lInterestPayeeAcctID!=-1&&NameRef.getAccountOfficeByID(lInterestPayeeAcctID)!=NameRef.getAccountOfficeByID(lPayerAcctID))
		{
			throw new IException("����ʺź���Ϣ�տ�˺Ų���ͬ�������˻�");
		}
		if(lInterestPayeeAcctID!=-1&&NameRef.getAccountOfficeByID(lInterestPayeeAcctID)!=NameRef.getAccountOfficeByID(lPayeeAcctID)){
			throw new IException("����ʺźͱ����տ�˺Ų���ͬ�������˻�");
		}
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
		/* �޸ķ��ؽ�� */
		long lUpdateResult = -1;
	
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		Log.print("--------------------lInstructionID="+lInstructionID+"----------payerid="+financeInfo.getClientID());

		/* ����EJB��������(���޸�)����ȡ��Ϣ���� */
		//ˢ������
		if (session.getAttribute("clickCount") == null)
		{
			session.setAttribute("clickCount", String.valueOf(0));
		}
		String strClickCount = request.getParameter("clickCount");
		Log.print("clickcount from request parameter:="+strClickCount);
		boolean blnClickCheck = OBOperation.checkClick(strClickCount, session);
		String strNextPage = "/capital/notify/n014-v.jsp?isNeedApproval="+isNeedApproval;
		long iTransType = OBConstant.SettInstrType.NOTIFYDEPOSITDRAW;
		
		if(operate.equals("saveAndApproval"))
		{
			String strSuccessPageURL1="/capital/notify/vAppN011.jsp";
			String strFailPageURL1="/capital/notify/n013-c.jsp";
			//String strAction1 = "FixedQuery";
			strNextPage ="/capital/notify/n014-v.jsp?isNeedApproval="+isNeedApproval;
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=";
				
			//���������
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
				
			financeInfo.setInutParameterInfo(pInfo);
			
						//��ǩ��ֵ��ԭʼ���ݱ���
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			//Boxu Add 2009��5��22�� ������־��¼
				try
				{
					if(sign.equals("again"))
					{
						financeInfo.setID(-1);
					}
					lInstructionID = financeInstr.addCapitalTrans(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
				}
				catch(Exception ex)
				{
					transinfo.setStatus(Constant.FAIL);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}
			
			if(lInstructionID>0){
				  if(tempTransCode != null && !tempTransCode.equals(""))
					{
						//���ݷ��ʶ���
						AttachOperation attachOperation = new AttachOperation();
						attachOperation.updateOBTransCode(tempTransCode,String.valueOf(lInstructionID));
					}
			}
			financeInfo.setID(lInstructionID);
			session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
			//Modify by leiyang date 2007/07/25
			if(isSommitTimes == true){
				msg =  "�ύ�����ɹ�, ʱ���ѳ���������ٽ���ʱ��, ִ�����ӳ�һ��!";
			}
			else {
				msg =  "�ύ�����ɹ�";
			}
			request.setAttribute("msg",msg);
		}
		else
		{
			if (blnClickCheck)
			{
			
				//��ǩ��ֵ��ԭʼ���ݱ���
				financeInfo.setSignatureValue(signatureValue);	
				financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				
				//Boxu Add 2009��5��22�� ������־��¼
				try
				{
					if(sign.equals("again"))
					{
						financeInfo.setID(-1);
					}
					lInstructionID = financeInstr.addCapitalTrans(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
				}
				catch(Exception ex)
				{
					transinfo.setStatus(Constant.FAIL);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}
				
				financeInfo.setID(lInstructionID);
				if(lInstructionID>0){
					  if(tempTransCode != null && !tempTransCode.equals(""))
						{
							//���ݷ��ʶ���
							AttachOperation attachOperation = new AttachOperation();
							attachOperation.updateOBTransCode(tempTransCode,String.valueOf(lInstructionID));
						}
				}
				session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
			}
			else 
			{
				if(lInstructionID<0)
				{
					String strInstructionID = (String) session.getAttribute("lInstructionID");
					lInstructionID = Long.parseLong(strInstructionID);
				}
			}
			if(operate.equals("submit"))
			{
				//Modify by leiyang date 2007/07/25
				if(isSommitTimes == true){
					msg =  "����ɹ�, ʱ���ѳ���������ٽ���ʱ��, ִ�����ӳ�һ��!";
				}
				else {
					msg =  "����ɹ�";
				}
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		
				/*��������;ժҪ��Ϣ*/
				
				
				strTemp = (String)request.getAttribute("sNote");
				if(strTemp!=null&&!strTemp.equals("")){
					AbstractID = Long.parseLong(strTemp);//�����;ID
					
					AbstractCode = (String)request.getAttribute("sCode");//�����;���
				}else{
					AbstractID=-1;
				}
				if( AbstractID < 0)
				{
					long lMaxCode = OBAbstractSetting.getMaxCode(sessionMng.m_lOfficeID,sessionMng.m_lUserID);
					OBinfo.setScode(DataFormat.formatInt(lMaxCode,5,true,0));
				}else if(AbstractID > 0){
					OBinfo.setScode(AbstractCode);
				}
				
				OBinfo.setId(AbstractID);
				OBinfo.setSdesc(strNote);
				OBinfo.setNofficeid(sessionMng.m_lOfficeID);
				OBinfo.setNclientid(sessionMng.m_lUserID);
				OBinfo.setInputtime(now);
				OBinfo.setModifytime(now);
				if(strNote.trim().length() != 0 )
				{
					OBAbstractSetting.saveStandardAbstract(OBinfo);
				}		
		
		
		/*����EJB������ѯ���*/
		financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
	    /* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    
	   //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPage);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	   
	    /* forward�����ҳ�� */
	    rd.forward(request, response);

	} 
	catch(IException ie) 
	{
		Log.print("S11-Ctr.jsp:EJB�����쳣������ת�д�");
		session.setAttribute("financeInfo", financeInfo);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	finally
	{
		//Boxu Add 2009��5��22�� ������־��¼
		if(operate.equals("saveAndApproval"))
		{
			transinfo.setActionType(Constant.TransLogActionType.initApproval);
		}
		else if(templInstructionID > 0)
		{
			transinfo.setActionType(Constant.TransLogActionType.modify);
		}
		else
		{
			transinfo.setActionType(Constant.TransLogActionType.insert);
		}
		
		if(transinfo.getStatus() != -1)
		{
			TranslogBiz translofbiz= new TranslogBiz();
			transinfo.setHostip(request.getRemoteAddr());
			transinfo.setHostname(request.getRemoteHost());
			transinfo.setTransType(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
			translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
		}
	}
%>
<!--Forward end-->