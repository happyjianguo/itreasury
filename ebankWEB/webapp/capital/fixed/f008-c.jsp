<%--
/*
 * �������ƣ�f008-c.jsp
 * ����˵��������ת���ύ����ҳ��
 * �������ߣ�����
 * ������ڣ�2007��04��18��
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
                 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.safety.util.*,
                 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[����ת��]";
	
%>
<%


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
%>

<%	
	/* ����Ա�����Ӧ���� */
    String sDepositBillNo = "";//���ڴ浥��
    Timestamp sDepositBillStartDate = null;//�¶������˻���ʼ��
    Timestamp sDepositBillEndDate = null;//�¶������˻�������
    long sDepositBillPeriod = -1;//�¶������˻�����
    long sDepositInterestDeal = -1;//�¶��ڴ���ʽ
    long lInstructionID = -1;//ָ��ID
    long sDepositInterestToAccountID  = -1;//�˻�ID
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
%>

<!--Get Data start-->
<%
		/* ��FORM���л�ȡ��Ӧ���� */
		
		/* �������˺� */
		sDepositBillNo = GetParam(request,"sPayerAccountNoZoomCtrl");
	    Log.print("���ڴ�����˺�=" + sDepositBillNo);
        
		/* �������˻������ʼ�� */
		sDepositBillStartDate = DataFormat.getDateTime(GetParam(request,"dPayerStartDate"));
	    Log.print("�¶��ڴ����ʼ��=" + sDepositBillStartDate);
	    
	    /* �������˻������� */
	    sDepositBillEndDate = DataFormat.getDateTime(GetParam(request,"dPayerEndDate"));
	    Log.print("�¶��ڴ�����=" + sDepositBillEndDate);
	    
	    /* �������˻�������� */
	    sDepositBillPeriod = GetNumParam(request,"nFixedDepositTime1");
	    Log.print("�¶��ڴ������=" + sDepositBillPeriod);
	    
		/* ָ����� */
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("ָ�����=" + lInstructionID);
		
		/* ���ڴ���ʽ */
	    sDepositInterestDeal = GetNumParam(request,"interesttype");
	    Log.print("�¶��ڴ�����=" + sDepositInterestDeal);
	    
	    /* ��Ϣת�������˻�ID */
	    sDepositInterestToAccountID = GetNumParam(request,"lInterestToAccountID");
		Log.print("��Ϣת�������˻�ID=" + sDepositInterestToAccountID);
		
		
		//added by mingfang 2007/05/24 ����ǩ��		
		//��ȫ��֤����
		String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		//�õ�ǩ��ֵ
		String signatureValue = request.getParameter(SignatureConstant.SIGNATUREVALUE);
		String signatureOriginalValue = "";
	
		if(strTroyName.equals(Constant.GlobalTroyName.ITrus) && 
			(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL
				|| strAction==OBConstant.SettInstrStatus.SAVE
				|| strAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL
		)){
			String[] nameArray = OBSignatureConstant.ChangeFixdeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.ChangeFixdeposit.getSignValueArrayFromReq(request);
			
			String _blnIsNeedApproval = request.getParameter("isNeedApproval");
			long lActionStatus =  Long.parseLong(request.getParameter("actionStatus"));
			
			String strIsRefused = request.getParameter("isRefused");
			strIsRefused = strIsRefused == null ? "" : strIsRefused;
			
			//���⴦��
			if(!(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL)){
				if(_blnIsNeedApproval.equals("true")){
				  	if(!strIsRefused.equals("true")
						&& lActionStatus != OBConstant.SettActionStatus.CANCELAPPROVALED
						&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED){											
							valueArray[9] = "-1";
					}		
				}else{
					long lRsStatus = Long.parseLong(request.getParameter("rsStatus"));
					
					if( lRsStatus != OBConstant.SettInstrStatus.CHECK
						&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED){
						
						valueArray[9] = "-1";
					}		
				}
			}
			SignatureInfo signatureInfo = new SignatureInfo();			
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureOriginalValue = SignatureUtil.formatData(signatureInfo);			
			//signatureOriginalValue = SignatureUtil.formatData(nameArray,valueArray);

			try{
				//SignatureAuthentication.validateFromReq(signatureOriginalValue,signatureValue);
		
				signatureInfo.setOriginalData(signatureOriginalValue);
				signatureInfo.setSignatureValue(signatureValue);
				
				SignatureAuthentication.validateFromReq(signatureInfo);										
			}catch(Exception e){
				throw new IException(e.getMessage());
			}			
		}		
		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
		/* ��ʼ����Ϣ�� */
		FinanceInfo financeInfo = new FinanceInfo();

		/* ����FORM���е���Ӧ���� ������Ϣ��*/
		/* ָ����� */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		/* ������� */
		/*financeInfo.setPayerName( strPayerName );// �������
		financeInfo.setPayerAcctID ( lPayerAcctID );//����˻�ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// �����˺�
		financeInfo.setPayerAcctNo( strPayerAcctNo );// ����˺�
		*/
		/* �տ���� */
		/*
		financeInfo.setPayeeName(strPayeeName) ;//�տ������
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); // �տ�˺�
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//�տ�˻�ID
		financeInfo.setFixedDepositTime ( lFixedDepositTime );//���ڴ������
		*/
		/* �������� */
		/*
		financeInfo.setAmount( dAmount );// ���
		financeInfo.setExecuteDate( tsExecute );// ִ����
		financeInfo.setConfirmDate( tsExecute );//ȷ������
		financeInfo.setNote( strNote );// �����;
		*/
		/* �����������˻���Ϣ */
		financeInfo.setSDepositBillNo(sDepositBillNo);
		financeInfo.setSDepositBillStartDate(sDepositBillStartDate);
		financeInfo.setSDepositBillEndDate(sDepositBillEndDate);
		financeInfo.setSDepositBillPeriod(sDepositBillPeriod);
		financeInfo.setSDepositInterestDeal(sDepositInterestDeal);
		financeInfo.setSDepositInterestToAccountID(sDepositInterestToAccountID);
		/* ��session�л�ȡ��Ӧ���� */
		financeInfo.setConfirmUserID( sessionMng.m_lUserID );
		financeInfo.setClientID( sessionMng.m_lClientID );
		financeInfo.setCurrencyID( sessionMng.m_lCurrencyID );
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//Ĭ�ϰ��´�ID
		/* ȷ�����Ͻ������� */
		financeInfo.setTransType( OBConstant.SettInstrType.DRIVEFIXDEPOSIT );//������������
		/*ȷ��ָ��״̬*/
		if(financeInfo.getStatus() == -1)
		{
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
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

		/* ����EJB��������(���޸�)����ȡ��Ϣ���� */
		//ˢ������
		if (session.getAttribute("clickCount") == null)
		{
			session.setAttribute("clickCount", String.valueOf(0));
		}

			//add by mingfang
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			
		lInstructionID = financeInstr.addTrans(financeInfo);
		financeInfo.setID(lInstructionID);
		session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
		/*����EJB������ѯ���*/
	//	financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
	    /* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	   
	    
	    //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/fixed/f006-c.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
	    sessionMng.getActionMessages().addMessage("����ɹ���");

	} 
	catch(IException ie) 
	{
		Log.print("f008-c.jsp:EJB�����쳣������ת�д�");
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }

%>
<!--Forward end-->