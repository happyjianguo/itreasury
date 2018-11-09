<%
/**
 * ҳ������ ��l017-c.jsp
 * ҳ�湦�� : ����һ����֤����
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,
			com.iss.itreasury.ebank.util.*,
			com.iss.itreasury.ebank.obloanapply.dataentity.*,
			com.iss.itreasury.ebank.obloanapply.bizlogic.*,
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.ebank.obdataentity.*,
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>


<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    try{
    	
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
	
		long lLoanType=Long.valueOf((String)request.getAttribute("lLoanType")).longValue();
		long lAssureTypeID=Long.valueOf((String)request.getAttribute("lAssureTypeID")).longValue();
		long lLoanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();
		long lClientID=Long.valueOf((String)request.getAttribute("lClientID")).longValue();

		/* UNDO:������ҳ�����ݱ���ͻ��Ļ�����Ϣ*/
		ClientInfo cInfo=new ClientInfo();
		String tmp="";
		String action="";

		/*------------------------�ж��ظ��ύ------------------------------*/
		long commandKey=-1;
		boolean isRepeat=false;
		tmp=(String)request.getParameter("commandKey");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			commandKey=Long.valueOf(tmp).longValue();
			isRepeat=sessionMng.checkInterval(commandKey);
		}
		/*-------------------------�жϽ���---------------------------------*/
		
		System.out.println("^^^^^^^^^^^^^^)))))))))))");
		tmp=(String)request.getAttribute("cclientid");								//�ͻ����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setClientID(Long.valueOf(tmp).longValue());
		
		cInfo.setModifyUserID(sessionMng.m_lUserID);						//�޸���
		System.out.println("^^^^^^^^^^^^^^0000000000000");
		/*tmp=(String)request.getAttribute("clicencecode");							//Ӫҵִ�պ���
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLicenceCode(tmp);
		
		tmp=(String)request.getAttribute("caccount");								//����˾�˺�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAccount(tmp);

		tmp=(String)request.getAttribute("cbank1");								    //��������
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank1(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount1");							//���������˺�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount1(tmp);				
		
		tmp=(String)request.getAttribute("loancardno");							//�����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanCardNo(tmp);				
			
		tmp=(String)request.getAttribute("loancardpwd");							//�������
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanCardPwd(tmp);				
				
		tmp=(String)request.getAttribute("cprovince");								//ʡ
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setProvince(tmp);				

		tmp=(String)request.getAttribute("ccity");								//��
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setCity(tmp);			
							
		tmp=(String)request.getAttribute("caddress");								//��ַ
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAddress(tmp);			
			
		tmp=(String)request.getAttribute("czipcode");								//�ʱ�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setZipCode(tmp);			

		tmp=(String)request.getAttribute("cphone");								//�绰
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setPhone(tmp);			
			
		tmp=(String)request.getAttribute("cfax");								//����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setFax(tmp);			

		tmp=(String)request.getAttribute("cmail");								//����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setEmail(tmp);			

		tmp=(String)request.getAttribute("clegalperson");								//����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLegalPerson(tmp);			

		tmp=(String)request.getAttribute("ccontacter");								//��ϵ��
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setContacter(tmp);

		tmp=(String)request.getAttribute("cgenerator");								//����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setGeneratorCapacity(tmp);
						
		tmp=(String)request.getAttribute("cdealscope");								//Ӫҵ��Χ
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setDealScope(tmp);
			
		tmp=(String)request.getAttribute("crisklevel");								//��������
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setRiskLevel(tmp);

		tmp=(String)request.getAttribute("cjudgelevel");								//������λ
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setJudGelevelClient(tmp);

		tmp=(String)request.getAttribute("IsShareHolder");								//�Ƿ�ɶ�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setIsPartner(Long.valueOf(tmp).longValue());

		tmp=(String)request.getAttribute("lClientTypeID");								//��Ӫ����ͻ�����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanClientTypeID(Long.valueOf(tmp).longValue());			
			
		tmp=(String)request.getAttribute("lSettClientTypeID");								//�ͻ��������
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setSettClientTypeID(Long.valueOf(tmp).longValue());				
			
		tmp=(String)request.getAttribute("lCreditLevel");								//���õȼ�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setCreditLevelID(Long.valueOf(tmp).longValue());			

		tmp=(String)request.getAttribute("ccaptial");								//ע���ʱ���
		if ( (tmp!=null)&&(tmp.length()>0) )	
		{
			cInfo.setCaptial(tmp);			
		}	
			
        tmp=(String)request.getAttribute("ccorpnatureid");					//��������
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setCorpNatureID(Long.valueOf(tmp).longValue() );
        
        tmp=(String)request.getAttribute("cparentcorpid");					//���ܲ���
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setParentCorpID(Long.valueOf(tmp).longValue() );
        
		tmp=(String)request.getAttribute("ckgclientid");					//�عɵ�λID
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGClientID(Long.valueOf(tmp).longValue() );
        	
		tmp=(String)request.getAttribute("ckgscale");						//�عɱ���
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setFKGScale(Float.valueOf(tmp).floatValue() );

		tmp=(String)request.getAttribute("ckgclientname");						//�عɵ�λ����
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGClientName(tmp );
        	
		tmp=(String)request.getAttribute("ckgcardno");						//�عɵ�λ�����
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGCardNo(tmp );

		tmp=(String)request.getAttribute("ckgpwd");						//�عɵ�λ�������
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGPwd(tmp );
        	
	

    	String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];	

		tmp=(String)request.getAttribute("cqtclientname0");						//�عɵ�λ1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[0]=tmp;
		tmp=(String)request.getAttribute("cqtcardno0");						    //�عɵ�λ���1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[0]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd0");						    //�عɵ�λ�������1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[0]=tmp;		
		tmp=(String)request.getAttribute("cqtscale0");						    //�عɵ�λ����1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTScale[0]=Float.valueOf(tmp).floatValue();
		
		tmp=(String)request.getAttribute("cqtclientname1");						//�عɵ�λ1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[1]=tmp;
		tmp=(String)request.getAttribute("cqtcardno1");						    //�عɵ�λ���1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[1]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd1");						    //�عɵ�λ�������1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[1]=tmp;		
		tmp=(String)request.getAttribute("cqtscale1");						    //�عɵ�λ����1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTScale[1]=Float.valueOf(tmp).floatValue();
			
		tmp=(String)request.getAttribute("cqtclientname2");						//�عɵ�λ1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[2]=tmp;
		tmp=(String)request.getAttribute("cqtcardno2");						    //�عɵ�λ���1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[2]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd2");						    //�عɵ�λ�������1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[2]=tmp;		
		tmp=(String)request.getAttribute("cqtscale2");						    //�عɵ�λ����1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTScale[2]=Float.valueOf(tmp).floatValue();
					
		cInfo.setQTClientName(QTClientName);
		cInfo.setFQTScale(QTScale);
		cInfo.setQTCardNo(QTCardNo);
		cInfo.setQTPwd(QTPwd);		      
		cInfo.setModifyUserID(sessionMng.m_lUserID);  
		

		tmp=(String)request.getAttribute("txtAction");
		if ( (tmp!=null) && ( tmp.length()>0 ) )
			action=tmp;	
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();*/
		
		//if ( !isRepeat )
		//	long cID=lcs.saveClientInfo(cInfo);
		OBAssureInfo aInfo=new OBAssureInfo();
		
		long    ID=-1;
    	long    loanID=-1;
    	long    assureTypeID=-1;
    	long    fillQuestionary=-1;
    	long    clientID=Long.valueOf((String)request.getAttribute("assClientID")).longValue();
    	double  amount=0;
    	String  impawName="";
    	String  impawQuality="";
    	String  impawStatus="";
    	double  pledgeAmount=0;
    	double  pledgeRate=0;
    	long    statusID=-1;
    	String  assureCode="";
    	double impawAmount=0;
    	tmp=(String)request.getAttribute("lAssureID");								//��֤���
		if ( (tmp!=null)&&(tmp.length()>0) )		
			ID=Long.valueOf(tmp).longValue();
	
    	tmp=(String)request.getAttribute("dbje");		//�������
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			amount=Double.valueOf(tmp).doubleValue();	
		}
		
		tmp=(String)request.getAttribute("dcb");		//�����
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			fillQuestionary=1;
		}
		
		tmp=(String)request.getAttribute("zydcmc");		//��Ѻ��������
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawName=tmp;
		}
		
		tmp=(String)request.getAttribute("sl");		//��Ѻ��������
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			impawAmount=Double.valueOf(tmp).doubleValue();
		}	
		
		
		tmp=(String)request.getAttribute("zl");		//��Ѻ��������
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawQuality=tmp;
		}	
		
		tmp=(String)request.getAttribute("zk");		//��Ѻ����״��
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawStatus=tmp;
		}
		
		tmp=(String)request.getAttribute("dycczj");
		if ( (tmp!=null)&&(tmp.length()>0) )  //��Ѻ�Ʋ��ܼ�
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			pledgeAmount=Double.valueOf(tmp).doubleValue();	
		}
		
		tmp=(String)request.getAttribute("dyl");
		if ( (tmp!=null)&&(tmp.length()>0) )  //��Ѻ�Ʋ���
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			pledgeRate=Float.valueOf(tmp).floatValue();	
			System.out.println(pledgeRate);
		}
		
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
				
		aInfo.setID(ID);
		System.out.println("assureid:"+ID);
		aInfo.setLoanID(lLoanID);
		aInfo.setAssureTypeID(lAssureTypeID);
		aInfo.setFillQuestionary(fillQuestionary);
		aInfo.setClientID(clientID);
		aInfo.setAmount(amount);
		aInfo.setImpawName(impawName);
		aInfo.setImpawQuality(impawQuality);
		aInfo.setImpawStatus(impawStatus);
		aInfo.setPledgeAmount(pledgeAmount);
		aInfo.setPledgeRate(pledgeRate);
		aInfo.setStatusID(Constant.RecordStatus.VALID);
		aInfo.setSecurityInfo(sInfo);
		
	   if (lAssureTypeID==LOANConstant.AssureType.IMPAWN)
	   		aInfo.setAmount(impawAmount);
	   		
	   if (lAssureTypeID==LOANConstant.AssureType.PLEDGE)
	   		aInfo.setAmount(pledgeAmount*pledgeRate/100);		

		OBLoanApplyHome home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home2.create();		
		
		if ( ! isRepeat )
			lla.saveAssure(aInfo);	

		System.out.println(lLoanType+"^^^^^^^^^^^^^^^^^^^^");
		
		/* ����ɹ�ת�����������Ϣ����������Ӧ�Ĳ��� */
		String strURL="/loan/loanapply/l016-c.jsp?lLoanType="+(String)request.getAttribute("lLoanType")
			+"&txtAction="+action;
		
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
		OBHtml.showExceptionMessage(out,sessionMng,ie,"�ͻ�ѡ��","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
		return; 
	  
     }
%>