<%
/**
 * 页面名称 ：l017-c.jsp
 * 页面功能 : 保存一个保证资料
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
	
		long lLoanType=Long.valueOf((String)request.getAttribute("lLoanType")).longValue();
		long lAssureTypeID=Long.valueOf((String)request.getAttribute("lAssureTypeID")).longValue();
		long lLoanID=Long.valueOf((String)request.getAttribute("lLoanID")).longValue();
		long lClientID=Long.valueOf((String)request.getAttribute("lClientID")).longValue();

		/* UNDO:根据上页的内容保存客户的基本信息*/
		ClientInfo cInfo=new ClientInfo();
		String tmp="";
		String action="";

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
		
		System.out.println("^^^^^^^^^^^^^^)))))))))))");
		tmp=(String)request.getAttribute("cclientid");								//客户编号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setClientID(Long.valueOf(tmp).longValue());
		
		cInfo.setModifyUserID(sessionMng.m_lUserID);						//修改人
		System.out.println("^^^^^^^^^^^^^^0000000000000");
		/*tmp=(String)request.getAttribute("clicencecode");							//营业执照号码
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLicenceCode(tmp);
		
		tmp=(String)request.getAttribute("caccount");								//财务公司账号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setAccount(tmp);

		tmp=(String)request.getAttribute("cbank1");								    //开户银行
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBank1(tmp);				
		
		tmp=(String)request.getAttribute("cbankaccount1");							//开户银行账号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setBankAccount1(tmp);				
		
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

		tmp=(String)request.getAttribute("IsShareHolder");								//是否股东
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setIsPartner(Long.valueOf(tmp).longValue());

		tmp=(String)request.getAttribute("lClientTypeID");								//自营贷款客户分类
		if ( (tmp!=null)&&(tmp.length()>0) )		
			cInfo.setLoanClientTypeID(Long.valueOf(tmp).longValue());			
			
		tmp=(String)request.getAttribute("lSettClientTypeID");								//客户结算分类
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
    	tmp=(String)request.getAttribute("lAssureID");								//保证编号
		if ( (tmp!=null)&&(tmp.length()>0) )		
			ID=Long.valueOf(tmp).longValue();
	
    	tmp=(String)request.getAttribute("dbje");		//担保金额
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			amount=Double.valueOf(tmp).doubleValue();	
		}
		
		tmp=(String)request.getAttribute("dcb");		//调查表
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			fillQuestionary=1;
		}
		
		tmp=(String)request.getAttribute("zydcmc");		//质押动产名称
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawName=tmp;
		}
		
		tmp=(String)request.getAttribute("sl");		//质押动产数量
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			impawAmount=Double.valueOf(tmp).doubleValue();
		}	
		
		
		tmp=(String)request.getAttribute("zl");		//质押动产质量
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawQuality=tmp;
		}	
		
		tmp=(String)request.getAttribute("zk");		//质押动产状况
		if ( (tmp!=null)&&(tmp.length()>0) )
		{
			impawStatus=tmp;
		}
		
		tmp=(String)request.getAttribute("dycczj");
		if ( (tmp!=null)&&(tmp.length()>0) )  //抵押财产总价
		{
			tmp=DataFormat.reverseFormatAmount(tmp);
			pledgeAmount=Double.valueOf(tmp).doubleValue();	
		}
		
		tmp=(String)request.getAttribute("dyl");
		if ( (tmp!=null)&&(tmp.length()>0) )  //抵押财产率
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
		
		/* 如果成功转到贷款基本信息，并传递相应的参数 */
		String strURL="/loan/loanapply/l016-c.jsp?lLoanType="+(String)request.getAttribute("lLoanType")
			+"&txtAction="+action;
		
		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	    
		/* 设置返回地址 */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward到结果页面 */
	    rd.forward(request, response);
		return;
		
     }catch (IException ie) {
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