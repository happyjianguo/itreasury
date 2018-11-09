<%
/**
 * ҳ������ ��l005-c.jsp
 * ҳ�湦�� : ����һ���¿ͻ��Ļ�����Ϣ,����ɹ�������һ�ʴ���Ļ�����Ϣ�ͼƻ��汾��Ϣ
 * 			  Ȼ�󵽴��������Ϣ��ҳ�棬�˴���������ˡ�
	  
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
		
		
		/* ������ҳ�����ݱ���ͻ��Ļ�����Ϣ*/
		ClientInfo cInfo=new ClientInfo();
		String tmp="";
		
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
		
		/*
		tmp=(String)request.getParameter("cclientid");								//�ͻ����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setClientID(Long.valueOf(tmp).longValue());
		*/
		cInfo.setClientID(sessionMng.m_lClientID);	
		cInfo.setModifyUserID(sessionMng.m_lUserID);						//�޸���
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();
		
		/*
		�ϸ�ҳ����disabled�����Ը���ȡ����
		tmp=(String)request.getAttribute("clicencecode");							//Ӫҵִ�պ���
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLicenceCode(tmp);
		*/
		
		tmp=(String)request.getAttribute("sClientName");							//��λ����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setName(tmp);
		
		tmp=(String)request.getAttribute("caccount");								//����˾�˺�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAccount(tmp);

		tmp=(String)request.getAttribute("cbank1");								    //��������1
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank1(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount1");							//���������˺�1
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount1(tmp);
			
		tmp=(String)request.getAttribute("cbank2");								    //��������2
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank2(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount2");							//���������˺�2
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount2(tmp);
			
		tmp=(String)request.getAttribute("cbank3");								    //��������3
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank3(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount3");							//���������˺�3
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount3(tmp);										
		
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

		tmp=(String)request.getAttribute("isShareHolder");								//�Ƿ�ɶ�
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setIsPartner(Long.valueOf(tmp).longValue());

		tmp=(String)request.getAttribute("lClientTypeID");								//��Ӫ����ͻ�����
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanClientTypeID(Long.valueOf(tmp).longValue());			
			
		tmp=(String)request.getAttribute("lSettClientTypeID");								//�ͻ��������
		System.out.println("settLientTypeID:"+tmp);
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
        
		//long cID=lcs.saveClientInfo(cInfo);

		boolean iswt=false;
		long loanType=Long.valueOf((String)request.getAttribute("lLoanType")).longValue();
		if (loanType==OBConstant.LoanInstrType.LOAN_WT)
       		iswt=true; 

		OBLoanCreateInfo createInfo=new OBLoanCreateInfo();
		
		long lCurrencyID=sessionMng.m_lCurrencyID;
		long lUserID=sessionMng.m_lUserID;
		long lOfficeID=sessionMng.m_lOfficeID;
		
		OBSecurityInfo sInfo=new OBSecurityInfo();
		sInfo.setCurrencyID(lCurrencyID);
		sInfo.setOfficeID(lOfficeID);
		sInfo.setUserID(lUserID);
	
		createInfo.setLoanID(-1);
		createInfo.setSecurityInfo(sInfo);
		createInfo.setTypeID(Long.valueOf((String)request.getAttribute("lLoanType")).longValue());
		
		if ( iswt )
			createInfo.setConsignClientID(sessionMng.m_lClientID);
		else
			createInfo.setBorrowClientID(sessionMng.m_lClientID);
		
		OBLoanApplyHome home2 = (OBLoanApplyHome)EJBObject.getEJBHome("OBLoanApplyHome");
		OBLoanApply lla = home2.create();
		
		long lID=lla.add(createInfo);
		
		OBLoanApplyInfo lInfo=lla.findByID(lID,sInfo);	
		request.setAttribute("LoanApplyInfo",lInfo);
		request.setAttribute("lLoanID",String.valueOf(lID));	
	
		String strURL="";
		if ( iswt )
			strURL="/loan/loanapply/l001-v.jsp";
		else
			strURL="/loan/loanapply/l006-v.jsp";
		
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
     	//System.out.println("nullll");
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