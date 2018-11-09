<%--
 ҳ������ ��a913-c.jsp
 ҳ�湦�� : ������/ί�У�����Ŵ������ҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
				1�������ݴ�
				2���޸��ݴ�
				3����������
				4���޸ı���
				5��ɾ��
				6������
				7������ǰ���
 �޸���ʷ ��
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
    String strNextPageURL 			= "";								//��ת��URL
	String strSuccessPageURL 		= "";								//�����ɹ�����ת��URL
	String strFailPageURL 			= "";								//����ʧ�ܺ���ת��URL
	String strAction 				= null;								//��������
	String strActionResult 			= Constant.ActionResult.FAIL;		//�����������
	String strToPrint 				= "";								//�Ƿ��ӡ��ʾ��Ϣ
	
	//TransLoanDelegation loanDelegation = new TransLoanDelegation();		//����delegation
	Sett_TransGrantLoanDAO loanDelegation=new Sett_TransGrantLoanDAO();
	
	long lID 						= -1;								//ID
    try
	{
		
		//������
				//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		
		//ҳ�渨������
		String strContinueSave 				= null;
		String strExecuteDate 				= null;
		String strInterestStartDate 		= null;
		String strModifyTime 				= null;
		
        //�������
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		long lInputUserID = sessionMng.m_lUserID;
		String strTransNo = "";
		long lTransactionTypeID = -1;//��������
		
		//---------------------����/ί�й���
		long lLoanClientID = -1;//����ͻ�ID
		long lLoanAccountID = -1;//�����˻�ID
		long lLoanContractID = -1;//�����ͬID
		long lLoanPayFormID = -1;//�ſ�֪ͨ��ID
		long lPayInterestAccountID = -1;//����/ί��-��Ϣ�˻�		

		//---------------------����/ί�й���
		
		//------------------����ר��
		
		boolean blnIsKeepAccount	= false;		//�Ƿ�󲹼��˴���
		long lPaySuretyFeeAccountID = -1;			//����-����˺�
		long lReceiveSuretyFeeAccountID = -1;		//����-�������˺�
		
		//------------------����ר��
		
		
		
		//------------------ί��ר��
		
		long lReceiveInterestAccountID 	= -1; 		//ί�з���Ϣ�˻�
		long lCommisionPayMode			= -1;		//��������ȡ��ʽ
		long lCommissionAccountID 		= -1;    	//֧���������˺�
		double dInterestRateID			= -1;		//����
		Timestamp tsValidDate			= null;		//��Ч����
		
		long lCashFlow					= -1;  		//�ֽ�����
		long lConsignDepositClientAccountID		= -1;		//ί�д���˺�
		
		//------------------ί��ר��
		
		
		//---------------------����/ί�й���
		
		long lDepositAccountID = -1;//������������˻�
		
		long lPayTypeID = -1;//���ʽ
		long lBankID = -1;//�����
		String strExtAcctNo = "";//�����տλ�˺�
		String strExtAcctName = "";//�����տλ����
		String strProvince = "";//�����(ʡ)
		String strCity = "";//�����(��)
		String strBankName = "";//������
		String strExtBankNo = null;//������
		
		long lAbstractID = -1;//ժҪID
		String strAbstract = "";//ժҪ����
		
		double dAmount = 0.0;//���
		java.sql.Timestamp tsExecute = null;//ִ����
		java.sql.Timestamp tsInputDate = null;//¼������
		java.sql.Timestamp tsInterestStart = null;//��Ϣ��
		java.sql.Timestamp tsModify = null;//��������
		long lStatusID = -1;//����״̬
		
		//---------------------����/ί�й���
		
        //--ҳ����Ʋ���
        strAction = (String)request.getAttribute("strAction");					
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");	
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");
		//--ҳ����Ʋ���
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("lLoanClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanContractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lLoanPayFormID");		//�ſ�֪ͨ��
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lLoanPayFormID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)request.getAttribute("blnIsKeepAccount");		//�Ƿ�󲹼��˴���
		if (strTemp !=null && strTemp.trim().equals("true")){
			blnIsKeepAccount = true;
		}
		
		strTemp = (String)request.getAttribute("lAbstractIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("lAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAbstractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dAmount = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("tsExecute");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecute = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("tsInputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInputDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsInterestStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strTransNo = strTemp;
		}
		
		strTemp = (String)request.getAttribute("strExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strExtBankNo = strTemp.trim();
		}
		
		double dRate = 0.0;//����
		strTemp = (String)request.getAttribute("txtRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dRate = Double.valueOf(strTemp).doubleValue();
		}
		if (dRate > 0.0)
		{
			//���ʴ�����
			if (lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT){		//�����ί��
				strTemp=(String)request.getAttribute("chkPayInterestAccount");
				if (strTemp!=null && strTemp.equals("1")){
					strTemp = (String)request.getAttribute("lPayInterestAccountID");
					if (strTemp != null && strTemp.trim().length() > 0)
					{
					    lPayInterestAccountID = Long.valueOf(strTemp).longValue();
					}
				}
				
			}
			else if (lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT){	//���������
				strTemp = (String)request.getAttribute("lPayInterestAccountID");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
				    lPayInterestAccountID = Long.valueOf(strTemp).longValue();
				}
			}
			
			
			strTemp=(String)request.getAttribute("chkReceiveInterestAccount");				//ί��
			if (strTemp!=null && strTemp.equals("1")){		//ѡ����ί�з���Ϣ�˻�
				strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
				}
			}
			
		}
		
		double dSuretyRate = 0.0;//������
		strTemp = (String)request.getAttribute("txtSuretyRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dSuretyRate = Double.valueOf(strTemp).doubleValue();
		}
		if (dSuretyRate > 0.0)
		{
			//�����Ѵ�����
			strTemp = (String)request.getAttribute("lDebtorAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lPaySuretyFeeAccountID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lSuretyAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lReceiveSuretyFeeAccountID = Long.valueOf(strTemp).longValue();
			}
		}
		
		String strRadioType = "";
		strTemp = (String)request.getAttribute("radio1");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strRadioType = strTemp;
		}
		if (strRadioType.equals("1"))
		{
			//�����˻���ʽ
			strTemp = (String)request.getAttribute("lDepositAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lDepositAccountID = Long.valueOf(strTemp).longValue();
			}
		}
		else if (strRadioType.equals("2"))
		{
			//���и��ʽ
			strTemp = (String)request.getAttribute("lPayTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lPayTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lRemitOutBranchID");

			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lBankID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lExtReceiveAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strExtAcctNo = strTemp;
			}
			strTemp = (String)request.getAttribute("strExtAcctName");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strExtAcctName = strTemp;
			}
			strTemp = (String)request.getAttribute("strProvince");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strProvince = strTemp;
			}
			strTemp = (String)request.getAttribute("strCity");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strCity = strTemp;
			}
			strTemp = (String)request.getAttribute("lRemitInBankIDCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strBankName = strTemp;
			}
		}
		
		
		//-----------------ί��ר��

		
		strTemp=(String)request.getAttribute("chkCommissionAccountID");
		if (strTemp!=null && strTemp.equals("1")){		//ѡ����֧�������ѷ��˻�
			strTemp = (String)request.getAttribute("lCommissionAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lCommissionAccountID = Long.valueOf(strTemp).longValue();   
			}
		}
		
		
		strTemp=(String)request.getAttribute("chkInterestRateID");
		if (strTemp!=null && strTemp.equals("1")){		//ѡ������Ϣ˰����
			strTemp = (String)request.getAttribute("lInterestRateIDCtrl");		//ȡ����Ϣ˰����
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dInterestRateID = Double.valueOf(strTemp).doubleValue();
			}
			strTemp = (String)request.getAttribute("tsValidDate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    tsValidDate = DataFormat.getDateTime(strTemp);
			}
		}
		
		
		strTemp = (String)request.getAttribute("lCashFlow");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCashFlow = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConsignDepositClientAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lConsignDepositClientAccountID = Long.valueOf(strTemp).longValue();
		}
		//-----------------ί��ר��
		
		
		/*
	
		strTemp = (String)request.getAttribute("dInterestTaxRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dInterestTaxRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("tsInterestTaxRateVauleDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestTaxRateVauleDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("KeepAccount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    KeepAccount = 
		}
		strTemp = (String)request.getAttribute("lPayCommisionAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayCommisionAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
		}

		strTemp = (String)request.getAttribute("lConsignDepositAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lConsignDepositAccountID = Long.valueOf(strTemp).longValue();
		}
		*/

		//��ʵ��������������
        TransGrantLoanInfo info = new TransGrantLoanInfo();
        
		info.setLoanAccountID(lLoanAccountID);				//�����˻�
		info.setLoanContractID(lLoanContractID);			//��ͬ��
		info.setLoanNoteID(lLoanPayFormID);					//�ſ�֪ͨ����
		info.setKeepAccount(blnIsKeepAccount);				//�Ƿ�󲹼��˴���
		info.setID(lID);									//ID
		info.setTransactionTypeID(lTransactionTypeID);		//��������
		info.setTransNo(strTransNo);						//���׺�
		info.setAbstract(strAbstract);						//ժҪ
		info.setAbstractID(lAbstractID);					//ժҪID
		info.setAmount(dAmount);							//���
		info.setBankID(lBankID);							//���д���
		info.setCity(strCity);								//���д���
		info.setCurrencyID(lCurrencyID);					//����
		info.setExecute(tsExecute);							//ִ����
		info.setExtAcctName(strExtAcctName);				//�ⲿ�ͻ�����
		info.setExtAcctNo(strExtAcctNo);					//�ⲿ�˺�
		info.setBankName(strBankName);						//�ⲿ����������
		info.setInputDate(tsInputDate);						//¼��ʱ��
		info.setInputUserID(lInputUserID);					//¼����ID
		info.setInterestStart(tsInterestStart);				//��Ϣ��
		info.setOfficeID(lOfficeID);						//���´�ID
		info.setPayTypeID(lPayTypeID);						//���ʽ
		info.setProvince(strProvince);						//ʡ��
		info.setDepositAccountID(lDepositAccountID);		//�����˺�
		info.setPayInterestAccountID(lPayInterestAccountID);//��Ϣ�˺�
		info.setPaySuretyFeeAccountID(lPaySuretyFeeAccountID);//���������˺�
		info.setReceiveSuretyFeeAccountID(lReceiveSuretyFeeAccountID);//�յ������˺�
		info.setModify(tsModify);							//��������
		info.setStatusID(lStatusID);						//����״̬
		info.setExtBankNo(strExtBankNo);					//������

		info.setReceiveInterestAccountID(lReceiveInterestAccountID);			//��Ϣ�˺�
		info.setPayCommisionAccountID(lCommissionAccountID);					//���������˺�
		info.setInterestTaxRate(dInterestRateID);								//��Ϣ˰����
		Log.print("��Ϣ˰����:"+dInterestRateID);
		info.setInterestTaxRateVauleDate(tsValidDate);							//��Ϣ˰������Чʱ��
		info.setConsignDepositAccountID(lConsignDepositClientAccountID);		//ί�д���˺�
		
		/*
		info.setInterestTaxRate(dInterestTaxRate);
		info.setInterestTaxRateVauleDate(tsInterestTaxRateVauleDate);
		info.setKeepAccount(KeepAccount);
		info.setPayCommisionAccountID(lPayCommisionAccountID);
		info.setReceiveInterestAccountID(lReceiveInterestAccountID);
		info.setConsignDepositAccountID(lConsignDepositAccountID);
		*/
		
		/*----------------Ϊ���ж��ո������-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/		
        
        //����������������ҵ����ĵ���
        if("Query".equals(strAction))
        {
			//strTransNo
			
			if (strTransNo != null && !strTransNo.equals(""))
			{
				//lID = loanDelegation.grantGetIDByTransNo(strTransNo);
				lID=loanDelegation.getIDByTransNo(strTransNo);
			}
			Log.print("��ϸstrTransNo == "+strTransNo);
			Log.print("��ϸID == "+lID);
			
			TransGrantLoanInfo resultinfo = null;
			//resultinfo = loanDelegation.grantFindDetailByID(lID);  //Sett_TransGrantLoanDAO dao.findByID(id);
			resultinfo=loanDelegation.findByID(lID);
			if (resultinfo != null)
			{
	            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
				
				payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
				Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
				
				//payforminfo = loanDelegation.grantNext(payforminfo);//Sett_TransGrantLoanDAO dao.findPayFormDetailByCondition(info);
				payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
				
				if(payforminfo != null )
	            {
	                strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("LoanPayFormDetailInfo",payforminfo);
					request.setAttribute("TransGrantLoanInfo",resultinfo);
				}
			}
		}
        else if(String.valueOf(SETTConstant.Actions.NEXTSTEP).equals(strAction))
        {
			Log.print("������������");
            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
			payforminfo.setLoadNoteID(lLoanPayFormID);
			Log.print("lLoanPayFormID = "+lLoanPayFormID);
			Log.print("lLoanLoadNoteID = "+payforminfo.getLoadNoteID());
			//payforminfo = loanDelegation.grantNext(payforminfo);
			payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
			if(payforminfo != null )
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				request.setAttribute("LoanPayFormDetailInfo",payforminfo);
				request.setAttribute("TransGrantLoanInfo",info);
            }
			else
			{
				strActionResult = Constant.ActionResult.FAIL;
				//sessionMng.getActionMessages().addMessage("δ�ҵ��÷ſ�֪ͨ����Ӧ�ļ�¼");
			}
        }
        else if(String.valueOf(SETTConstant.Actions.CREATETEMPSAVE).equals(strAction))
        {
			Log.print("�������������ݴ�");
			long lTempsaveResult = 1;	//loanDelegation.grantTempSave(info);
			if(lTempsaveResult > 0 )
            {
            	request.setAttribute("lID",""+lTempsaveResult);
                strActionResult = Constant.ActionResult.SUCCESS;
                //strToPrint = "�ݴ�ɹ�,�Ƿ��ӡ?";
                //sessionMng.getActionMessages().addMessage("�ݴ�ɹ�");
            }
        }
        else if(String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction))
        {
			Log.print("����������������");
			long lSaveResult = 1;	//loanDelegation.grantSave(info);
			if(lSaveResult > 0)
			{
				request.setAttribute("lID",""+lSaveResult);
				strActionResult = Constant.ActionResult.SUCCESS;
				//sessionMng.getActionMessages().addMessage("����ɹ�");
				//request.setAttribute("CreateSave","Success");
				//strToPrint = "����ɹ�,�Ƿ��ӡ?";
			}
        }
		else if(String.valueOf(SETTConstant.Actions.MODIFYTEMPSAVE).equals(strAction))
        {
			Log.print("���������޸��ݴ�");
			long lTempsaveResult =1;	// loanDelegation.grantTempSave(info);
            if(lTempsaveResult > 0 )
            {
            	request.setAttribute("lID",""+lTempsaveResult);
            	//strToPrint = "�޸��ݴ�ɹ�,�Ƿ��ӡ?";
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("�޸��ݴ�ɹ�");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
		else if(String.valueOf(SETTConstant.Actions.MODIFYSAVE).equals(strAction))
        {
			Log.print("���������޸ı���");
			long lSaveResult = 1;	//loanDelegation.grantSave(info);
            if(lSaveResult > 0 )
            {
            	request.setAttribute("lID",""+lSaveResult);
            	//strToPrint = "�޸ı���ɹ�,�Ƿ��ӡ?";
                strActionResult = Constant.ActionResult.SUCCESS;
				//sessionMng.getActionMessages().addMessage("�޸ı���ɹ�");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
        else if(String.valueOf(SETTConstant.Actions.DELETE).equals(strAction))
        {
			Log.print("��������ɾ��");
			
			long lDeleteResult = 1;	//loanDelegation.grantDelete(info);
            if(lDeleteResult > 0)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("ɾ���ɹ�");
				strSuccessPageURL = "../control/c002.jsp";
            }
        }
		else if(String.valueOf(SETTConstant.Actions.TODETAIL).equals(strAction))
		{
			Log.print("�������������޸�ҳ����ϸ");
			
			TransGrantLoanInfo resultinfo = null;
			Log.print("��ϸID == "+lID);
			//resultinfo = loanDelegation.grantFindDetailByID(lID);
			resultinfo=loanDelegation.findByID(lID);
			if (resultinfo != null)
			{
	            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
				
				payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
				Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
				
				//payforminfo = loanDelegation.grantNext(payforminfo);
				payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
				if(payforminfo != null )
	            {
	                strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("LoanPayFormDetailInfo",payforminfo);
					request.setAttribute("TransGrantLoanInfo",resultinfo);
				}
			}
		}
        else
        {
            Log.print("��Ч����");
        }
	}
	catch( Exception exp )
	{
		Log.print("�����쳣");
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
		
		try
		{
			if (String.valueOf(SETTConstant.Actions.CREATESAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.CREATETEMPSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.MODIFYTEMPSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.MODIFYSAVE).equals(strAction) ||
				String.valueOf(SETTConstant.Actions.DELETE).equals(strAction) )
			{
			Log.print("������˻���Ϣ");
				TransGrantLoanInfo resultinfo = null;
				Log.print("lID == "+lID);
				//resultinfo = loanDelegation.grantFindDetailByID(lID);
				resultinfo=loanDelegation.findByID(lID);
				if (resultinfo != null)
				{
		            LoanPayFormDetailInfo payforminfo = new LoanPayFormDetailInfo();
					
					payforminfo.setLoadNoteID(resultinfo.getLoanNoteID());
					Log.print("lLoanPayFormID = "+payforminfo.getLoadNoteID());
					
					//payforminfo = loanDelegation.grantNext(payforminfo);
					payforminfo=loanDelegation.findPayFormDetailByCondition(payforminfo);
					if(payforminfo != null )
		            {
						request.setAttribute("LoanPayFormDetailInfo",payforminfo);
						request.setAttribute("TransGrantLoanInfo",resultinfo);
					}
				}
			}
		}
		catch (Exception e)
		{
			Log.print("�쳣�У������쳣");
			e.printStackTrace();
			//sessionMng.getActionMessages().addMessage(e);
			strActionResult = Constant.ActionResult.FAIL;
		}
	}

	request.setAttribute("strActionResult",strActionResult);
	request.setAttribute("CreateSave",strToPrint);				//�����ӡ��Ϣ
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//ת����һҳ��
	Log.print("Next Page URL:"+strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>