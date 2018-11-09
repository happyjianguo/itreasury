<%
/**
 * 页面名称 ：l005-c.jsp
 * 页面功能 : 保存一个新客户的基本信息,如果成功就增加一笔贷款的基本信息和计划版本信息
 * 			  然后到贷款基本信息的页面，此处不处理回退。
	  
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
    	
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		
		/* 根据上页的内容保存客户的基本信息*/
		ClientInfo cInfo=new ClientInfo();
		String tmp="";
		
		/*------------------------判断重复提交------------------------------*/
		long commandKey=-1;
		boolean isRepeat=false;
		tmp=(String)request.getParameter("commandKey");
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			commandKey=Long.valueOf(tmp).longValue();
			isRepeat=sessionMng.checkInterval(commandKey);
		}
		/*-------------------------判断结束---------------------------------*/
		
		/*
		tmp=(String)request.getParameter("cclientid");								//客户编号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setClientID(Long.valueOf(tmp).longValue());
		*/
		cInfo.setClientID(sessionMng.m_lClientID);	
		cInfo.setModifyUserID(sessionMng.m_lUserID);						//修改人
		
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();
		
		/*
		上个页面是disabled，所以根本取不到
		tmp=(String)request.getAttribute("clicencecode");							//营业执照号码
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLicenceCode(tmp);
		*/
		
		tmp=(String)request.getAttribute("sClientName");							//单位名称
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setName(tmp);
		
		tmp=(String)request.getAttribute("caccount");								//财务公司账号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAccount(tmp);

		tmp=(String)request.getAttribute("cbank1");								    //开户银行1
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank1(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount1");							//开户银行账号1
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount1(tmp);
			
		tmp=(String)request.getAttribute("cbank2");								    //开户银行2
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank2(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount2");							//开户银行账号2
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount2(tmp);
			
		tmp=(String)request.getAttribute("cbank3");								    //开户银行3
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank3(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount3");							//开户银行账号3
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount3(tmp);										
		
		tmp=(String)request.getAttribute("loancardno");							//贷款卡号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanCardNo(tmp);				
			
		tmp=(String)request.getAttribute("loancardpwd");							//贷款卡密码
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanCardPwd(tmp);				
				
		tmp=(String)request.getAttribute("cprovince");								//省
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setProvince(tmp);				

		tmp=(String)request.getAttribute("ccity");								//市
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setCity(tmp);			
							
		tmp=(String)request.getAttribute("caddress");								//地址
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAddress(tmp);			
			
		tmp=(String)request.getAttribute("czipcode");								//邮编
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setZipCode(tmp);			

		tmp=(String)request.getAttribute("cphone");								//电话
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setPhone(tmp);			
			
		tmp=(String)request.getAttribute("cfax");								//传真
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setFax(tmp);			

		tmp=(String)request.getAttribute("cmail");								//电邮
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setEmail(tmp);			

		tmp=(String)request.getAttribute("clegalperson");								//法人
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLegalPerson(tmp);			

		tmp=(String)request.getAttribute("ccontacter");								//联系人
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setContacter(tmp);

		tmp=(String)request.getAttribute("cgenerator");								//容量
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setGeneratorCapacity(tmp);
						
		tmp=(String)request.getAttribute("cdealscope");								//营业范围
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setDealScope(tmp);
			
		tmp=(String)request.getAttribute("crisklevel");								//风险评级
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setRiskLevel(tmp);

		tmp=(String)request.getAttribute("cjudgelevel");								//评级单位
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setJudGelevelClient(tmp);

		tmp=(String)request.getAttribute("isShareHolder");								//是否股东
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setIsPartner(Long.valueOf(tmp).longValue());

		tmp=(String)request.getAttribute("lClientTypeID");								//自营贷款客户分类
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanClientTypeID(Long.valueOf(tmp).longValue());			
			
		tmp=(String)request.getAttribute("lSettClientTypeID");								//客户结算分类
		System.out.println("settLientTypeID:"+tmp);
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setSettClientTypeID(Long.valueOf(tmp).longValue());				
			
		tmp=(String)request.getAttribute("lCreditLevel");								//信用等级
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setCreditLevelID(Long.valueOf(tmp).longValue());			

		tmp=(String)request.getAttribute("ccaptial");								//注册资本金
		if ( (tmp!=null)&&(tmp.length()>0) )	
		{
			cInfo.setCaptial(tmp);			
		}	
			
        tmp=(String)request.getAttribute("ccorpnatureid");					//经济性质
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setCorpNatureID(Long.valueOf(tmp).longValue() );
        
        tmp=(String)request.getAttribute("cparentcorpid");					//主管部门
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setParentCorpID(Long.valueOf(tmp).longValue() );
        
		tmp=(String)request.getAttribute("ckgclientid");					//控股单位ID
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGClientID(Long.valueOf(tmp).longValue() );
        	
		tmp=(String)request.getAttribute("ckgscale");						//控股比例
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setFKGScale(Float.valueOf(tmp).floatValue() );

		tmp=(String)request.getAttribute("ckgclientname");						//控股单位名称
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGClientName(tmp );
        	
		tmp=(String)request.getAttribute("ckgcardno");						//控股单位贷款卡号
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGCardNo(tmp );

		tmp=(String)request.getAttribute("ckgpwd");						//控股单位贷款卡密码
        if ( (tmp!=null)&&(tmp.length()>0) )
        	cInfo.setKGPwd(tmp );
        	
	

    	String[] QTClientName=new String[3];
    	float[] QTScale =new float[3];
    	String[] QTCardNo=new String[3];
    	String[] QTPwd=new String[3];	

		tmp=(String)request.getAttribute("cqtclientname0");						//控股单位1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[0]=tmp;
		tmp=(String)request.getAttribute("cqtcardno0");						    //控股单位贷款卡1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[0]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd0");						    //控股单位贷款卡密码1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[0]=tmp;		
		tmp=(String)request.getAttribute("cqtscale0");						    //控股单位比例1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTScale[0]=Float.valueOf(tmp).floatValue();
		
		tmp=(String)request.getAttribute("cqtclientname1");						//控股单位1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[1]=tmp;
		tmp=(String)request.getAttribute("cqtcardno1");						    //控股单位贷款卡1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[1]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd1");						    //控股单位贷款卡密码1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[1]=tmp;		
		tmp=(String)request.getAttribute("cqtscale1");						    //控股单位比例1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTScale[1]=Float.valueOf(tmp).floatValue();
			
		tmp=(String)request.getAttribute("cqtclientname2");						//控股单位1
        if ( (tmp!=null)&&(tmp.length()>0) )
			QTClientName[2]=tmp;
		tmp=(String)request.getAttribute("cqtcardno2");						    //控股单位贷款卡1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTCardNo[2]=tmp;	
		tmp=(String)request.getAttribute("cqtpwd2");						    //控股单位贷款卡密码1
		if ( (tmp!=null)&&(tmp.length()>0) )
			QTPwd[2]=tmp;		
		tmp=(String)request.getAttribute("cqtscale2");						    //控股单位比例1
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
		
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
     	//System.out.println("nullll");
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	  
     }catch (RemoteException e) {
		
		e.printStackTrace();
		out.flush();
		return; 
	  
     }
%>