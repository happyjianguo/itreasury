<%--
/*
 * �������ƣ�c003-c.jsp
 * ����˵�����ʽ𻮲��ύ����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��06�� 
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
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[�ʽ𻮲�]";  
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
	long lInstructionID = -1;//ָ�����
	double dPayerCurrentBalance = 0.0;//��ǰ���
	double dPayerUsableBalance = 0.0;//�������
	long lChildClientID = -1;//������λID
    String strPayerName = "";//  �������
	String strPayerBankNo = "";// �����˺�
	String strPayerAcctNo = "";// ����˺�
	long lPayerAcctID = -1;//����˻�ID
	long lRemitType = 0;// ��ʽ
	long lPayeeAcctID = -1;//�տ�˻�ID
	String strPayeeName = "";// �տ����
	String strPayeeBankNo = "";// �տ�����˺�
	String strPayeeAcctNo = "";// �տ�˺�
	String strPayeeBankName = "";// ����������
	String strPayeeProv = "";// ����ʡ
	String strPayeeCity = "";// ������
	double dAmount = 0.0;// ���
	Timestamp tsExecute = null;// ִ����
	String strNote = "";// �����;
	
	/* ��ʼ����Ϣ�� */
	FinanceInfo financeInfo = new FinanceInfo();
	ClientCapInfo clientCapInfo = new ClientCapInfo();
%>

<%
	/* �û���¼�����Ȩ��У�� */
	try 
	{
	
        // �û���¼���
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
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
		/* ָ����� */
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("ָ�����=" + lInstructionID);
		
		/* ������� */
		lChildClientID = GetNumParam(request,"lClientID");
		Log.print("������λID=" + lChildClientID);
		
		strPayerName = GetParam(request,"sPayerName");// �������
		Log.print("�������=" + strPayerName);
		
		//System.out.println(request.getParameter("nPayerAccountID"));
		lPayerAcctID = GetNumParam(request,"nPayerAccountID");//����˻�ID
		Log.print("����˻�ID=" + lPayerAcctID);
		
		strPayerBankNo = GetParam(request,"sBankAccountNO");// �����˺�
		Log.print("�����˺�=" + strPayerBankNo);

		strPayerAcctNo = GetParam(request,"sPayerAccountNoZoomCtrl");// ����˺�
		Log.print("����˺�=" + strPayerAcctNo);
		
		if(request.getParameter("dPayerCurrBalance") != null)
		{
			dPayerCurrentBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerCurrBalance"))).doubleValue();// ��ǰ���
			Log.print("��ǰ���=" + dPayerCurrentBalance);
		}
		
		if(request.getParameter("dPayerUsableBalance") != null)
		{
			dPayerUsableBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerUsableBalance"))).doubleValue();// �������
			Log.print("�������=" + dPayerUsableBalance);
		}
		
		/* �տ���� */	
		lRemitType = OBConstant.SettRemitType.BANKPAY;
		Log.print("��ʽ=" + lRemitType);			
			
		lPayeeAcctID = GetNumParam(request,"nPayeeAccountID");//�տ�˻�ID
		Log.print("�տ�˻�ID=" + lPayeeAcctID);
		
		switch ((int)lRemitType)
		{
			/* ��ʽ��ת */
			case (int)OBConstant.SettRemitType.SELF :
				
				strPayeeName = GetParam(request,"sPayeeNameZoomBankCtrl");//�տ����
				Log.print("�տ����=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeBankNoZoomCtrl");// �տ�˺�
				Log.print("�տ�˺�=" + strPayeeAcctNo);
				
				strPayeeBankName = GetParam(request,"sPayeeBankNameRead");// ����������
				Log.print("����������=" + strPayeeBankName);
				
				break;
				
			/* ��ʽ��㡢�Ż㡢Ʊ�� */
			case (int)OBConstant.SettRemitType.BANKPAY :
			
				strPayeeName = GetParam(request,"sPayeeName");// �տ����
				Log.print("�տ����=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// �տ�˺�
				Log.print("�տ�˺�=" + strPayeeAcctNo);
				
				strPayeeProv = GetParam(request,"sPayeeProv");// ����ʡ
				Log.print("����ʡ=" + strPayeeProv);
				
				strPayeeCity = GetParam(request,"sPayeeCity");// ������
				Log.print("������=" + strPayeeCity);
				
				strPayeeBankName = GetParam(request,"sPayeeBankName");// ����������
				Log.print("����������=" + strPayeeBankName);
				
				break;
				
			/* ��ʽ�ڲ�ת�� */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strPayeeName = GetParam(request,"sPayeeName");// �տ����
				Log.print("�տ����=" + strPayeeName);
				
				strPayeeBankNo = GetParam(request,"sPayeeAccountInternalInternal");// �տ�����˺�
				Log.print("�տ�����˺�(Iternal)=" + strPayeeBankNo);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// �տ�˺�
				Log.print("�տ�˺�=" + strPayeeAcctNo);
				
				break;
			/* ��ʽ�ڲ�ת�� */
			case (int)OBConstant.SettRemitType.OTHER :
			
				strPayeeName = GetParam(request,"sPayeeNameOther");// �տ����
				Log.print("�տ����=" + strPayeeName);
								
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* �������� */
		if(request.getParameter("dAmount") != null)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dAmount"))).doubleValue();// ���
			Log.print("���=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute = DataFormat.getDateTime((String)request.getParameter("tsExecute"));// ִ����
			Log.print("ִ����=" + tsExecute);
		}
		
		strNote = GetParam(request,"sNote");// �����;
		Log.print("�����;=" + strNote);
		
	
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
		/* ������� */
		financeInfo.setChildClientID(lChildClientID);//������λ
		financeInfo.setPayerName( strPayerName );// �������
		financeInfo.setPayerAcctID ( lPayerAcctID );//����˻�ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// �����˺�
		financeInfo.setPayerAcctNo( strPayerAcctNo );// ����˺�
		financeInfo.setCurrentBalance( dPayerCurrentBalance );// �����ǰ���
		financeInfo.setUsableBalance( dPayerUsableBalance );// ����������
		/* �տ���� */
		financeInfo.setRemitType ( lRemitType );// ��ʽ
		financeInfo.setPayeeName(strPayeeName) ;
		clientCapInfo.setPayeeName(strPayeeName);// �տ����
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); 
		clientCapInfo.setPayeeAccoutNO( strPayeeAcctNo );// �տ�˺�
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//�տ�˻�ID
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.SELF:
				financeInfo.setPayeeBankNo( "" );// �տ�����˺�
				financeInfo.setPayeeBankName( strPayeeBankName );// ����������
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//�����Ͳ����ڲ��˻�
				break;
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setPayeeProv( strPayeeProv );
				clientCapInfo.setPayeeProv( strPayeeProv );// ����ʡ
				financeInfo.setPayeeCity(strPayeeCity );
				clientCapInfo.setCity( strPayeeCity );// ������
				financeInfo.setPayeeBankName( strPayeeBankName );
				clientCapInfo.setPayeeBankName( strPayeeBankName );// ����������
				financeInfo.setIsCpfAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );//�����Ͳ����ڲ��˻�
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setPayeeBankNo( "" );// �տ�����˺�
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//�����Ͳ����ڲ��˻�
				break;
			case (int)OBConstant.SettRemitType.OTHER :
				financeInfo.setPayeeBankNo( "" );// �տ�����˺�
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
		financeInfo.setCurrencyID( 1 );
		clientCapInfo.setCurrencyID( sessionMng.m_lCurrencyID );// ���ױ���
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//Ĭ�ϰ��´�ID
		/* ȷ�����Ͻ������ͺͻ�ʽ */
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.SELF:
				financeInfo.setTransType( OBConstant.SettInstrType.CAPTRANSFER_SELF );
				break;
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setTransType( OBConstant.SettInstrType.CAPTRANSFER_BANKPAY );
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setTransType( OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT );
				break;
			case (int)OBConstant.SettRemitType.OTHER :
				financeInfo.setTransType( OBConstant.SettInstrType.CAPTRANSFER_OTHER );
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		
		//���Ͻ�������
		financeInfo.setTransType( OBConstant.SettInstrType.APPLYCAPITAL );

		financeInfo.setRemitType( lRemitType );//��ʽ
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
		Log.print("--------------------lInstructionID="+lInstructionID);

		/* ����EJB��������(���޸�)����ȡ��Ϣ���� */
		//ˢ������
		if (session.getAttribute("clickCount") == null)
		{
			session.setAttribute("clickCount", String.valueOf(0));
		}
		String strClickCount = request.getParameter("clickCount");
		Log.print("clickcount from request parameter:="+strClickCount);
		boolean blnClickCheck = OBOperation.checkClick(strClickCount, session);
		if (blnClickCheck)
		{
			lInstructionID = financeInstr.addCapitalTrans(financeInfo);
			financeInfo.setID(lInstructionID);
			session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
			System.out.println("-----------------"+lInstructionID);
		}
		else 
		{
			if(lInstructionID<0)
			{
				String strInstructionID = (String) session.getAttribute("lInstructionID");
				lInstructionID = Long.parseLong(strInstructionID);
			}
		}
		/*����EJB������ѯ���*/
		financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		
		
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		ClientAccountInfo accountInfo=null;
		long accountID=-1;
	
		if (financeInfo!=null)	accountID=financeInfo.getPayerAcctID();
			
		accountInfo=dao.findAccountInfoByClient(sessionMng.m_lUserID, sessionMng.m_lClientID, accountID, sessionMng.m_lCurrencyID);
		request.setAttribute("accountInfo",accountInfo);		
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
	    /* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	    RequestDispatcher rd = null;
		

	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/captransfer/c014-v.jsp");
	//�ַ�
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

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
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }

%>
<!--Forward end-->