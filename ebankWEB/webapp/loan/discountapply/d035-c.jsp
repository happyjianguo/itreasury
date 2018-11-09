<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.ebank.obdiscountapply.dao.*,
				com.iss.itreasury.loan.util.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
	/* ����̶����� */
	String strTitle = "[����ƾ֤]";
%>	
<%
/////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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
           
		//�����������ȡ�������
		
		String strTmp = "";
		String strControl = "";
		String strBackURL = "S125";
		String strDisabled = " disabled";

		long lBillID = -1;
		long[] lBillIDArray = new long[1000];
		String strBillID = "";
		Vector v = null;
			
		long txtContract = -1;			//���ֱ�ʾ
		String txtContractCode = "";	//����������
		long txtClient = -1;			//��λ��ʾ
		String txtClientCtrl = "";		//��λ����

		long lContractID = -1;			//���ֱ�ʾ
		long lCredenceID = -1;			//����ƾ֤��ʾ
		Timestamp tsFillDate = null;
		long lDraftTypeID = -1;
		String strDraftTypeName = "";
		String strDraftCode = "";
		Timestamp tsPublicDate = null;
		Timestamp tsAtTerm = null;
		String strApplyClient = "";
		String strApplyAccount = "";
		String strApplyBank = "";
		String strAcceptClient = "";
		String strAcceptAccount = "";
		String strAcceptBank = "";
		double dAmount = 0;
		double dRate = 0;
		double dInterest = 0;
		double dCheckAmount = 0;
		double dBillAmount = 0;
		double dBillInterest = 0;
		double dBillCheckAmount = 0;
		double[] dResult = new double[3];

		long lGrantTypeID = -1;				//�ſʽ
		long lAccountBankID = -1;			//��������
		String strAccountBankName = "";		//��������
		String strAccountBankCode = "";		//���������˺�
		String strReceiveClientCode = "";	//�տλ�˺�
		String strReceiveClientName = "";	//�տλ����
		String strRemitBank = "";			//������
		String strRemitInProvince = "";   	//����أ�ʡ��
		String strRemitInCity = "";       	//����أ��У�
		long lGrantCurrentAccountID = -1;	//�����������˻�
		String strGrantCurrentAccountCode = "";//�����������˻�
		
		//////////////////////////////
		String strContractCode = "";
		long lInputUserID = -1;
		String strInputUserName = "";
		long lStatusID = -1;
		long lCount = 0;

		//////////////////////////////
		
		//����EJB
		OBDiscountApplyHome  obDiscountApplyHome = null;
		OBDiscountApply      obDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
		obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		obDiscountApply = obDiscountApplyHome.create();
		
		//DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		DiscountCredenceInfo qinfo = new DiscountCredenceInfo();

		
///////control///////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

	
		strTmp = (String)request.getAttribute("backurl");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strBackURL = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("txtContract");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContract = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("txtClient");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClient = Long.parseLong(strTmp.trim());
		}	 

		strTmp = (String)request.getAttribute("txtClientCtrl");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClientCtrl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("strBillID");
System.out.println("strBillID===================================================================================="+strTmp);

        if(strTmp != null && strTmp.length() > 0 && !strTmp.equals("null"))
        {
			strBillID = strTmp.trim();
			/**
			 * ��һ����","�ֿ��Ĵ��ֽ�Ϊһ��Vector������
			 * @param strParam ��Ҫ��ֵĲ���
			 * @return ����һ��Vector��������Long��
			 */
			 
			v = DataFormat.changeStringGroup(strBillID);

			if( (v != null) && (v.size() > 0) )
			{
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					Long lTmp = (Long) it.next();
					lBillIDArray[(int)lCount] = lTmp.longValue();
					lCount++;
				}
			}
		}
		
System.out.println("here");
		//�ҵĴ���
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
System.out.println("lCredenceID===================================================================================="+lCredenceID);
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}
		
		//����
		if(lCredenceID <=0 )
		{
			strTmp = (String)request.getAttribute("lCredenceID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lCredenceID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsFillDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				tsFillDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("lDraftTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lDraftTypeID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("strDraftTypeName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftTypeName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strDraftCode");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftCode = strTmp.trim();
			}
			
			strTmp = (String)request.getAttribute("tsPublicDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsPublicDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsAtTerm");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsAtTerm = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("strApplyClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyClient =strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyBank =strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("dBillAmount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dBillAmount = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dRate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dRate = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dBillInterest");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dBillInterest = Double.parseDouble(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("lGrantTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantTypeID = Long.parseLong(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("naccountbankid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lAccountBankID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("sreceiveclientname");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("txtAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientCode = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitbank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitinprovince");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInProvince = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitincity");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInCity = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("ngrantcurrentaccountid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantCurrentAccountID = Long.parseLong(strTmp.trim());
			}

			//����ƾ֤
			qinfo.setID(-1); //����ƾ֤��ʶ
			qinfo.setDiscountContractID(lContractID); //���ֺ�ͬ��ʶ
			//qinfo.setCode(rs.getString("sCode")); //����ƾ֤���
			qinfo.setFillDate(tsFillDate);
			qinfo.setDraftTypeID(lDraftTypeID); //���ֻ�Ʊ�����ʾ
			qinfo.setDraftCode(strDraftCode); //���ֻ�Ʊ����
			qinfo.setPublicDate(tsPublicDate); //��Ʊ��
			qinfo.setAtTerm(tsAtTerm); //������
			qinfo.setApplyClientName(strApplyClient); //���뵥λ����
			qinfo.setApplyAccount(strApplyAccount); //���뵥λ�˺�
			qinfo.setApplyBank(strApplyBank); //���뵥λ����
			qinfo.setAcceptClientName(strAcceptClient); //�жҵ�λ����
			qinfo.setAcceptAccount(strAcceptAccount); //�жҵ�λ�˺�
			qinfo.setAcceptBank(strAcceptBank); //�жҵ�λ����
			qinfo.setStatusID(LOANConstant.DiscountCredenceStatus.SUBMIT); //����ƾ֤�Ƿ�ɾ��

			qinfo.setBillAmount(dBillAmount); //����ƾ֤���
			qinfo.setBillInterest(dBillInterest); //����ƾ֤��Ϣ
			qinfo.setBillCheckAmount(dBillAmount - dBillInterest); //����ƾ֤ʵ�����ֶ�

			qinfo.setInputUserID(sessionMng.m_lUserID);
			qinfo.setInputDate(Env.getSystemDate());
			qinfo.setNextCheckUserID(sessionMng.m_lUserID); //��һ������˱�ʾ
				
			qinfo.setGrantTypeID(lGrantTypeID);
			qinfo.setAccountBankID(lAccountBankID);
			qinfo.setReceiveClientCode(strReceiveClientCode);
			qinfo.setReceiveClientName(strReceiveClientName);
			qinfo.setRemitBank(strRemitBank);
			qinfo.setRemitInProvince(strRemitInProvince);
			qinfo.setRemitInCity(strRemitInCity);
			qinfo.setGrantCurrentAccountID(lGrantCurrentAccountID);
			//rate
			qinfo.setDiscountRate(dRate);
	
			//��������
			//lCredenceID = obDiscountApply.saveDiscountCredence(qinfo);
			if (lCredenceID > 0)
			{				
			%>
				<script language="JavaScript">
					alert("�벻Ҫ�ظ��ύ��")
					//eval("self.location='S118.jsp?control=view'");
				</script>
			<%
				response.sendRedirect("d036-v.jsp?control=view&lCredenceID="+lCredenceID+"&backurl=S118");
			}
			else
			{
				lCredenceID = obDiscountApply.saveDiscountCredence(qinfo);
			}
			if (lCredenceID > 0)
			{
for (int i = 0; i < lBillIDArray.length; i++)
{
	if(lBillIDArray[i]>0)
	{
		System.out.println(lBillIDArray[i]);
	}
}
				OBDiscountApplyDao dao = new OBDiscountApplyDao();
				dao.saveDiscountCredenceBill(lCredenceID, lContractID, lBillIDArray);
			}
			
		    request.setAttribute("lCredenceID",lCredenceID+"");
		    request.setAttribute("lContractID",lContractID+"");
			request.setAttribute("strBillID",strBillID);
			request.setAttribute("control","view");
			//��һҳ
			request.setAttribute("nextPage","d036-v.jsp");
		    /* ��ȡ�����Ļ��� */
	        ServletContext sc = getServletContext();
	        /* ���÷��ص�ַ */
	        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d033-c.jsp")));
	        /* forward�����ҳ�� */
	        rd.forward(request, response);
			
		}
		else//���и���
		{
			strTmp = (String)request.getAttribute("tsFillDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				tsFillDate = DataFormat.getDateTime(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("strDraftCode");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftCode = strTmp.trim();
			}
			
			strTmp = (String)request.getAttribute("tsPublicDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsPublicDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsAtTerm");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsAtTerm = DataFormat.getDateTime(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("strApplyClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("dBillAmount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dAmount = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dRate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dRate = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dBillInterest");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dInterest = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("lGrantTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantTypeID = Long.parseLong(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("naccountbankid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lAccountBankID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("sreceiveclientname");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("txtAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientCode = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitbank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitinprovince");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInProvince = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitincity");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInCity = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("ngrantcurrentaccountid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantCurrentAccountID = Long.parseLong(strTmp.trim());
			}

			//Discount.saveDiscountCredence(lCredenceID, lContractID, sessionMng.m_lUserID, tsFillDate, lDraftTypeID, strDraftCode, tsPublicDate, tsAtTerm, strApplyClient, strApplyAccount, strApplyBank, strAcceptClient, strAcceptAccount, strAcceptBank, dAmount, dRate, dInterest, sessionMng.m_lOfficeID);

			//����ƾ֤
			qinfo.setID(lCredenceID); //����ƾ֤��ʶ
			qinfo.setDiscountContractID(lContractID); //����ƾ֤��ʶ
			//info.setCode(rs.getString("sCode")); //����ƾ֤���
			qinfo.setFillDate(tsFillDate);
			qinfo.setDraftTypeID(lDraftTypeID); //���ֻ�Ʊ�����ʾ
			qinfo.setDraftCode(strDraftCode); //���ֻ�Ʊ����
			qinfo.setPublicDate(tsPublicDate); //��Ʊ��
			qinfo.setAtTerm(tsAtTerm); //������
			qinfo.setApplyClientName(strApplyClient); //���뵥λ����
			qinfo.setApplyAccount(strApplyAccount); //���뵥λ�˺�
			qinfo.setApplyBank(strApplyBank); //���뵥λ����
			qinfo.setAcceptClientName(strAcceptClient); //�жҵ�λ����
			qinfo.setAcceptAccount(strAcceptAccount); //�жҵ�λ�˺�
			qinfo.setAcceptBank(strAcceptBank); //�жҵ�λ����
			qinfo.setStatusID(LOANConstant.DiscountCredenceStatus.SUBMIT); //����ƾ֤�Ƿ�ɾ��

			qinfo.setBillAmount(dBillAmount); //����ƾ֤���
			qinfo.setBillInterest(dBillInterest); //����ƾ֤��Ϣ
			qinfo.setBillCheckAmount(dBillAmount - dBillInterest); //����ƾ֤ʵ�����ֶ�

			qinfo.setInputUserID(sessionMng.m_lUserID);
			qinfo.setInputDate(Env.getSystemDate());
			qinfo.setNextCheckUserID(sessionMng.m_lUserID); //��һ������˱�ʾ
				
			qinfo.setGrantTypeID(lGrantTypeID);
			qinfo.setAccountBankID(lAccountBankID);
			qinfo.setReceiveClientCode(strReceiveClientCode);
			qinfo.setReceiveClientName(strReceiveClientName);
			qinfo.setRemitBank(strRemitBank);
			qinfo.setRemitInProvince(strRemitInProvince);
			qinfo.setRemitInCity(strRemitInCity);
			qinfo.setGrantCurrentAccountID(lGrantCurrentAccountID);

			Log.print("lGrantTypeID="+lGrantTypeID);
			Log.print("lAccountBankID="+lAccountBankID);
			Log.print("strReceiveClientCode="+strReceiveClientCode);
			Log.print("strReceiveClientName="+strReceiveClientName);
			Log.print("strRemitBank="+strRemitBank);
			Log.print("strRemitInProvince="+strRemitInProvince);
			Log.print("strRemitInCity="+strRemitInCity);
			Log.print("lGrantCurrentAccountID="+lGrantCurrentAccountID);
			
			obDiscountApply.updateDiscountCredence(qinfo);
			
		    request.setAttribute("lCredenceID",lCredenceID+"");
		    request.setAttribute("lContractID",lContractID+"");
			request.setAttribute("strBillID",strBillID);
			request.setAttribute("control","view");
			//��һҳ
			request.setAttribute("nextPage","d036-v.jsp");
		    /* ��ȡ�����Ļ��� */
	        ServletContext sc = getServletContext();
	        /* ���÷��ص�ַ */
	        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d033-c.jsp")));
	        /* forward�����ҳ�� */
	        rd.forward(request, response);
			
		}
		
	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"����ƾ֤", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"����ƾ֤",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>