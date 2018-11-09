<!--
/*
 * 程序名称：f009-c.jsp
 * 功能说明：定期转存提交控制页面
 * 作　　者：葛亮
 * 完成日期：2007年04月18日
 */
-->

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
                  com.iss.itreasury.ebank.approval.dataentity.*,
                 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.common.attach.bizlogic.*,
                 com.iss.itreasury.safety.util.*,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期转存]";
%>
<%
	/* 实现菜单控制 */
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
	/* 定义对表单的相应变量 */
	long strAction=-1;
    String sDepositBillNo = "";//定期存单号
    Timestamp sDepositBillStartDate = null;//新定期子账户开始日
    Timestamp sDepositBillEndDate = null;//新定期子账户结束日
    long sDepositBillPeriod = -1;//新定期子账户期限
    long sDepositInterestDeal = -1;//新定期存款处理方式
    long lInstructionID = -1;//指令ID
    long sDepositInterestToAccountID  = -1;//账户ID
    String sPayeeAccountNo = "";
    String sPayeeAccountName ="";
    double dAmount = 0.0;
    String strPayerAcctNo = "";// 付款方账号
    String strPayerName = "";//  付款方名称
    long lPayerAcctID = -1;//付款方账户ID
    Timestamp tsExecute = null;// 执行日
    Timestamp tsFixedDepositStart = null;//定期存款起始日
	long lFixedDepositTime = -1;//定期存款期限
	double dDepositAmount = 0.0;//定期存单金额
	double dDepositRate = 0.0;//定期利率
	
	String tempTransCode = ""; //临时交易号
	String strTemp = "";
	
	//Modify by leiyang date 2007/07/25
	boolean isSommitTimes = false;
	String msg = "";
	String operate = "";
    
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
        
     //判断是否需要审批流 by ypxu 2007-05-10
	InutParameterInfo inutParameterInfo = new InutParameterInfo();
	inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
	inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
	inutParameterInfo.setClientID(sessionMng.m_lClientID);
	inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
	boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
%>

<!--Get Data start-->
<%
		/* 从FORM表单中获取相应数据 */
		
		
		strAction=GetNumParam(request,"strAction");
		
		
		/* 定期子账号 */
		sDepositBillNo = GetParam(request,"sPayerAccountNoZoomCtrl");
	    Log.print("定期存款子账号=" + sDepositBillNo);
      
        
        
        lPayerAcctID = GetNumParam(request,"nPayeeAccountID");
	    Log.print("定期账号=" + sPayeeAccountNo);
	    
	    strPayerAcctNo = GetParam(request,"sPayeeAccountNo");// 付款方账号
	    
	    strPayerName = GetParam(request,"sPayeeAccountName");// 付款方名称
        
		if(request.getParameter("dAmount") != null)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dAmount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
	
		/* 定期子账户存款起始日 */
		sDepositBillStartDate = DataFormat.getDateTime(GetParam(request,"dPayerStartDate"));
	    Log.print("新定期存款起始日=" + sDepositBillStartDate);
	    
	    /* 定期子账户存款到期日 */
	    sDepositBillEndDate = DataFormat.getDateTime(GetParam(request,"dPayerCurrEndDate"));
	    Log.print("新定期存款到期日=" + sDepositBillEndDate);
	    
	    /* 定期子账户存款期限 */
	    sDepositBillPeriod = GetNumParam(request,"nFixedDepositTime1");
	    Log.print("新定期存款期限=" + sDepositBillPeriod);
	    
		/* 指令序号 */
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		
		/* 定期处理方式 */
	    sDepositInterestDeal = GetNumParam(request,"interesttype");
	    Log.print("新定期存款到期日=" + sDepositInterestDeal);
	    
	    /* 利息转至活期账户ID */
	    if(request.getParameter("lInterestToAccountID") != null)
	    {
	    	sDepositInterestToAccountID = GetNumParam(request,"lInterestToAccountID");
			Log.print("利息转至活期账户ID=" + sDepositInterestToAccountID);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute = DataFormat.getDateTime((String)request.getParameter("tsExecute"));// 执行日
			Log.print("执行日=" + tsExecute);
		}
		if(request.getParameter("dPayerStartDate") != null)
		{
			tsFixedDepositStart = DataFormat.getDateTime((String)request.getParameter("dPayerStartDate"));//定期存款起始日
			Log.print("定期存款起始日=" + tsFixedDepositStart);
		}
		if(request.getParameter("nFixedDepositTime") != null)
		{
			lFixedDepositTime = GetNumParam(request,"nFixedDepositTime");//定期存款期限
			Log.print("定期存款期限=" + lFixedDepositTime);
		}
		
		if(request.getParameter("dPayerCurrBalance") != null)
		{
			dDepositAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerCurrBalance"))).doubleValue();// 定期存单金额
			Log.print("定期存单金额=" + dDepositAmount);
		}
		
		if(request.getParameter("dPayerCurrInterest") != null)
			dDepositRate = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerCurrInterest"))).doubleValue();// 定期存单利率
			Log.print("定期存单利率=" + dDepositRate);
			
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
		
		//Modify by leiyang date 2007/07/25
		if(!operate.equals("cancelApproval")){
			Timestamp timeNow = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		
			//当执行日等于开机日
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
					
					//当前小时和分钟
					int lTNHours =  timeNow.getHours();
					int lTNMinutes = timeNow.getMinutes();
					
					String commitTimes[] = strCommitTime.split(":");
					//停止接收的小时和分钟
					int lCTHours = Integer.parseInt(commitTimes[0]);
					int lCTMinutes = Integer.parseInt(commitTimes[1]);
					
					if(lCTHours < lTNHours){
						if(operate.equals("doApproval")){
							throw new IException("审批时间已超过结算最迟接收时间");
						}
						else{
							if(isControl == SETTConstant.OBCommitTimeControlType.RIGID){
								throw new IException("提交时间已超过结算最迟接收时间");
							}
							else{
								tsExecute.setDate(tsExecute.getDate() + 1);
								isSommitTimes = true;
							}
						}
					}
					else if(lCTHours == lTNHours) {
						if(lCTMinutes < lTNMinutes){
							if(operate.equals("doApproval")){
								throw new IException("审批时间已超过结算最迟接收时间");
							}
							else {
								if(isControl == SETTConstant.OBCommitTimeControlType.RIGID){
									throw new IException("提交时间已超过结算最迟接收时间");
								}
								else{
									tsExecute.setDate(tsExecute.getDate() + 1);
									isSommitTimes = true;
								}
							}
						}
					}
				}
			}
		}
		
		
		
		//added by mingfang 2007/05/24 数字签名		
		//安全认证机构
		String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		//得到签名值
		String signatureValue = request.getParameter(SignatureConstant.SIGNATUREVALUE);
		String signatureOriginalValue = "";
	
		if(strTroyName.equals(Constant.GlobalTroyName.ITrus) && 
			(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL
				|| strAction==OBConstant.SettInstrStatus.SAVE
				|| strAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL
		)){
			String[] nameArray = OBSignatureConstant.ChangeFixdeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.ChangeFixdeposit.getSignValueArrayFromReq(request);
			
			String strIsRefused = request.getParameter("isRefused");
			strIsRefused = strIsRefused == null ? "" : strIsRefused;
			
			//特殊处理
			if(!(strAction == OBConstant.SettInstrStatus.DOAPPRVOAL) && !strIsRefused.equals("true")){
				valueArray[OBSignatureConstant.ChangeFixdeposit.iArrayLength -1] = "-1";
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
		/* 初始化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();
	
		/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		/* 付款方资料 */
		financeInfo.setPayerName( strPayerName );// 付款方名称
		financeInfo.setPayerBankNo( strPayerAcctNo );// 银行账号
		financeInfo.setPayerAcctNo( strPayerAcctNo );// 付款方账号
		financeInfo.setPayerAcctID (lPayerAcctID );//付款方账户ID
		/* 收款方资料 */
		/*
		financeInfo.setPayeeName(strPayeeName) ;//收款放名称
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); // 收款方账号
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//收款方账户ID
		financeInfo.setFixedDepositTime ( lFixedDepositTime );//定期存款期限
		*/
		/* 划款资料 */
		/*
		financeInfo.setAmount( dAmount );// 金额
		financeInfo.setExecuteDate( tsExecute );// 执行日
		financeInfo.setConfirmDate( tsExecute );//确认日期
		financeInfo.setNote( strNote );// 汇款用途
		*/
		/* 定期续存子账户信息 */
		financeInfo.setDepositStart( tsFixedDepositStart );// 定期存款起始日
		financeInfo.setFixedDepositTime( lFixedDepositTime );// 定期存款期限
		financeInfo.setDepositAmount( dDepositAmount );// 定期存单金额
		financeInfo.setDepositRate( dDepositRate );// 定期存单利率
		financeInfo.setAmount(dAmount);// 金额
		financeInfo.setSDepositBillNo(sDepositBillNo);
		financeInfo.setDepositNo( sDepositBillNo );// 定期存款单据号
		financeInfo.setExecuteDate(tsExecute);// 执行日
		financeInfo.setConfirmDate( tsExecute );//确认日期
		financeInfo.setSDepositBillStartDate(sDepositBillStartDate);
		financeInfo.setSDepositBillEndDate(sDepositBillEndDate);
		financeInfo.setSDepositBillPeriod(sDepositBillPeriod);
		financeInfo.setSDepositInterestDeal(sDepositInterestDeal);
		financeInfo.setSDepositInterestToAccountID(sDepositInterestToAccountID);
		/* 从session中获取相应数据 */
		financeInfo.setConfirmUserID( sessionMng.m_lUserID );
		financeInfo.setClientID( sessionMng.m_lClientID );
		financeInfo.setCurrencyID( sessionMng.m_lCurrencyID );
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//默认办事处ID
		/* 确定网上交易类型 */
		financeInfo.setTransType( OBConstant.SettInstrType.CHANGEFIXDEPOSIT );//定期转存类型
		
		/*确定指令状态*/
		 strTemp = (String)request.getAttribute("rsStatus");
	  	 long statusid = -1;	 	
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    statusid = Long.valueOf(strTemp).longValue();
		    if(statusid>=0)
		    {
		    	financeInfo.setStatus(statusid);
		    }
		}
		if(financeInfo.getStatus() == -1)
		{
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
		/* 修改返回结果 */
		long lUpdateResult = -1;
	
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();

		/* 调用EJB方法保存(或修改)和提取信息对象 */
		//刷新问题
		if (session.getAttribute("clickCount") == null)
		{
			session.setAttribute("clickCount", String.valueOf(0));
		}

		String strNextPage = "/capital/fixed/f011-v.jsp?isNeedApproval="+ isNeedApproval;
		long iTransType = OBConstant.SettInstrType.NOTIFYDEPOSITDRAW;
		
		if(operate != null && operate.equals("saveAndApproval"))
		{
			String strSuccessPageURL1="/capital/fixed/vAppF009.jsp";
			String strFailPageURL1="/capital/fixed/f009-c.jsp";
			//String strAction1 = "FixedQuery";
			strNextPage ="/capital/fixed/f011-v.jsp?isNeedApproval="+ isNeedApproval;
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=";
				
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
						
			financeInfo.setInutParameterInfo(pInfo);
					
					
			//add by mingfang
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			
				
			lInstructionID = financeInstr.addTrans(financeInfo);
			financeInfo.setID(lInstructionID);
			session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
			//Modify by leiyang date 2007/07/25
			if(isSommitTimes == true){
				msg =  "提交审批成功, 时间已超过结算最迟接收时间, 执行日延迟一日!";
			}
			else {
				msg =  "提交审批成功";
			}
			request.setAttribute("msg",msg);
		}	
		else if(operate != null && operate.equals("doApproval"))
		{
			
		    String strSuccessPageURL1="/capital/fixed/vAppF009.jsp";
			String strFailPageURL1="/capital/fixed/f009-c.jsp";
			
			
			

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
					//如果审批流的待办任务链接修改为v033.jsp则使用下面地址    mingfang
					strNextPage ="/approval/view/v033.jsp";
			
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
			
			financeInfo.setInutParameterInfo(pInfo);
			//审批
				try
				{
				
							//add by mingfang
					financeInfo.setSignatureValue(signatureValue);	
					financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				
					financeInstr.doApproval(financeInfo);
					//Modify by leiyang date 2007/07/25
					msg =  "审批成功";
					sessionMng.getActionMessages().addMessage(msg);
				}
				catch(Exception e) 
				{
					Log.print("EJB异常抛到前台处理");	
					e.printStackTrace();
				}
			
			//sessionMng.getActionMessages().addMessage("审批成功");
		}
		else if(operate != null && operate.equals("cancelApproval"))
		{
			
		    String strSuccessPageURL1="/capital/fixed/vAppF009.jsp";
			String strFailPageURL1="/capital/fixed/f009-c.jsp";
			
			
			

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
			strNextPage ="/approval/view/v036.jsp";
			
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
			
			financeInfo.setInutParameterInfo(pInfo);
			//审批
				try
				{
				
							//add by mingfang
					financeInfo.setSignatureValue(signatureValue);	
					financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				
					financeInstr.cancelApproval(financeInfo);
					sessionMng.getActionMessages().addMessage("取消审批成功");
				}
				catch(Exception e) 
				{
					Log.print("EJB异常抛到前台处理");	
					e.printStackTrace();
				}
			
			//sessionMng.getActionMessages().addMessage("审批成功");
		}
		else
		{
					//将签名值与原始数据保存
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			
			
			lInstructionID = financeInstr.addTrans(financeInfo);
			if(lInstructionID>0){
				  if(tempTransCode != null && !tempTransCode.equals(""))
					{
						//数据访问对象
						AttachOperation attachOperation = new AttachOperation();
						attachOperation.updateOBTransCode(tempTransCode,String.valueOf(lInstructionID));
					}
				}
			financeInfo.setID(lInstructionID);
			session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
		}
		/*调用EJB方法查询结果*/
		financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
%>
 <!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */	    
	   
	   
	   
	   //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPage);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward到结果页面 */
	    
		//Modify by leiyang date 2007/07/25
		if(isSommitTimes == true){
			msg =  "保存成功, 时间已超过结算最迟接收时间, 执行日延迟一日!";
		}
		else {
			msg =  "保存成功";
		}
		sessionMng.getActionMessages().addMessage(msg);
	    rd.forward(request, response);

	} 
	catch(IException ie) 
	{
		Log.print("f008-c.jsp:EJB调用异常或者跳转有错");
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }

%>
<!--Forward end-->