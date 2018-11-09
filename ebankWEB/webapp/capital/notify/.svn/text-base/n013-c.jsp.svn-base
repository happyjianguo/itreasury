<%--
/*
 * 程序名称：n013-c.jsp
 * 功能说明：通知支取提交控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月13日
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
	/* 标题固定变量 */
	String strTitle = "[通知支取]";
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
	Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
%>

<%	
	/* 定义对表单的相应变量 */
	long lInstructionID = -1;//指令序号
	long templInstructionID = -1;//临时指令序号，业务日志使用
	long strAction=-1;
    String strPayerName = "";//  付款方名称
	String strPayerBankNo = "";// 银行账号
	String strPayerAcctNo = "";// 付款方账号
	long lPayerAcctID = -1;//付款方账户ID
	String strDepositNo = "";//通知存款单据号
	long lSubAccountID = -1; //子账户ID
	Timestamp tsNotifyDepositStart = null;//通知存款起始日
	long lNoticeDay = -1;//通知存款品种
	double dDepositAmount = 0.0;//通知存单金额
	double dDepositBalance = 0.0;//通知存单余额
	
	long lRemitType = 0;// 本金汇款方式
	long lPayeeAcctID = -1;//本金收款方账户ID
	String strPayeeName = "";// 本金收款方名称
	String strPayeeBankNo = "";// 本金收款方银行账号
	String strPayeeAcctNo = "";// 本金收款方账号
	String strPayeeBankName = "";// 本金汇入行名称
	String strPayeeProv = "";// 本金汇入省
	String strPayeeCity = "";// 本金汇入市
	
	long lInterestPayeeAcctID = -1;//利息收款方账户ID
	long lInterestRemitType = 0;// 利息汇款方式
	String strInterestPayeeName = "";// 利息收款方名称
	String strInterestPayeeBankNo = "";// 利息收款方银行账号
	String strInterestPayeeAcctNo = "";// 利息收款方账号
	String strInterestPayeeBankName = "";// 利息汇入行名称
	String strInterestPayeeProv = "";// 利息汇入省
	String strInterestPayeeCity = "";// 利息汇入市
		
	double dAmount = 0.0;// 支取本金金额
	Timestamp tsExecute = null;// 执行日
	String strNote = "";// 汇款用途
	
	String tempTransCode = ""; //临时交易号
	String strTemp = "";
	long AbstractID = -1;//汇款用途ID
	String AbstractCode = "";//汇款用途编号	
	
	//Modify by leiyang date 2007/07/25
	boolean isSommitTimes = false;
	String msg = "";
	String operate = "";
	Timestamp dtmodify = null;
	
	String signatureValue = "";
	String signatureOriginalValue = "";	
	long timestamp = -1;
	
	/* 初始化信息类 */
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
	inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
	boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
%>

<!--Get Data start-->
<%
		/* 从FORM表单中获取相应数据 */
		/* 指令序号 */
		
		
		strAction=GetNumParam(request,"strAction");
		Log.print("操作类型=" + strAction);
						
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		templInstructionID = lInstructionID;
		
		/* 付款方资料 */
		strPayerName = GetParam(request,"sPayerName");// 付款方名称
		Log.print("付款方名称=" + strPayerName);
		
		lPayerAcctID = GetNumParam(request,"nPayerAccountID");//付款方账户ID
		Log.print("付款方账户ID=" + lInstructionID);
		
		strPayerBankNo = GetParam(request,"sBankAccountNO");// 银行账号
		Log.print("银行账号=" + lPayerAcctID);

		strPayerAcctNo = GetParam(request,"sPayerAccountNoZoomCtrl");// 付款方账号
		Log.print("付款方账号=" + strPayerAcctNo);
		
		strDepositNo = GetParam(request,"depositNo");//通知存款单据号
		Log.print("通知存款单据号=" + strDepositNo);
		
		lSubAccountID =  GetNumParam(request,"sNotifyDepositNo"); //子账户ID
		Log.print("子账户=" + lSubAccountID);
		
		tsNotifyDepositStart = DataFormat.getDateTime((String)request.getParameter("tsNotifyDepositStart"));//通知存款起始日
		Log.print("通知存款起始日=" + tsNotifyDepositStart);
		
		lNoticeDay = GetNumParam(request,"nNoticeDay");//通知存款品种
		Log.print("通知存款期限=" + lNoticeDay);
		
		dDepositAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dDepositAmount"))).doubleValue();// 定期存单金额
		Log.print("通知存单金额=" + dDepositAmount);
		
		dDepositBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dDepositBalance"))).doubleValue();// 定期存单利率
		Log.print("通知存单余额=" + dDepositBalance);
		
		
		/* 本金收款方资料 */
		 lRemitType = GetNumParam(request,"nRemitTypeHid");// 汇款方式
		 //lRemitType =OBConstant.SettRemitType.INTERNALVIREMENT;
		Log.print("汇款方式=" + lRemitType);
		/*
		lPayeeAcctID = GetNumParam(request,"payeeAcctID");//本金收款方账户ID
		Log.print("收款方账户ID=" + lPayeeAcctID);
		*/
		lInterestRemitType = GetNumParam(request,"nInterestRemitTypeHid");// 利息汇款方式
		//lInterestRemitType = OBConstant.SettRemitType.INTERNALVIREMENT;
		Log.print("利息汇款方式=" + lInterestRemitType);
		/*
		lInterestPayeeAcctID = GetNumParam(request,"interestPayeeAcctID");//利息收款方账户ID
		Log.print("利息收款方账户ID=" + lInterestPayeeAcctID);
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
			/* 汇款方式银行付款 */
			case (int)OBConstant.SettRemitType.BANKPAY :
			
				strPayeeName = GetParam(request,"sPayeeNameZoomAcctCtrl");// 收款方名称
				Log.print("收款方名称=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAcctNoZoomCtrl");// 收款方账号
				Log.print("收款方账号=" + strPayeeAcctNo);
				
				strPayeeProv = GetParam(request,"sPayeeProv");// 汇入省
				Log.print("汇入省=" + strPayeeProv);
				
				strPayeeCity = GetParam(request,"sPayeeCity");// 汇入市
				Log.print("汇入市=" + strPayeeCity);
				
				strPayeeBankName = GetParam(request,"sPayeeBankName");// 汇入行名称
				Log.print("汇入行名称=" + strPayeeBankName);
				
				lPayeeAcctID = GetNumParam(request,"nPayeeAccountID2");//本金收款方账户ID
				Log.print("收款方账户ID=" + lPayeeAcctID);
		
				break;
				
			/* 汇款方式内部转账 */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strPayeeName = GetParam(request,"sPayeeName");// 收款方名称
				Log.print("收款方名称=" + strPayeeName);
				
				strPayeeBankNo = GetParam(request,"sPayeeAccountInternalInternal");// 收款方银行账号
				Log.print("收款方银行账号(Iternal)=" + strPayeeBankNo);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// 收款方账号
				Log.print("收款方账号=" + strPayeeAcctNo);
				
				lPayeeAcctID = GetNumParam(request,"nPayeeAccountID1");//本金收款方账户ID
				Log.print("收款方账户ID=" + lPayeeAcctID);
		
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		
		switch ((int)lInterestRemitType)
		{		
			/* 汇款方式银行付款 */
			case (int)OBConstant.SettRemitType.BANKPAY :
			
				strInterestPayeeName = GetParam(request,"sInterestPayeeNameZoomAcctCtrl");// 收款方名称
				Log.print("利息收款方名称=" + strInterestPayeeName);
				
				strInterestPayeeAcctNo = GetParam(request,"sInterestPayeeAcctNoZoomCtrl");// 收款方账号
				Log.print("利息收款方账号=" + strInterestPayeeAcctNo);
				
				strInterestPayeeProv = GetParam(request,"sInterestPayeeProv");// 汇入省
				Log.print("利息汇入省=" + strInterestPayeeProv);
				
				strInterestPayeeCity = GetParam(request,"sInterestPayeeCity");// 汇入市
				Log.print("利息汇入市=" + strInterestPayeeCity);
				
				strInterestPayeeBankName = GetParam(request,"sInterestPayeeBankName");// 汇入行名称
				Log.print("利息汇入行名称=" + strInterestPayeeBankName);
				
				lInterestPayeeAcctID = GetNumParam(request,"nInterestPayeeAccountID2");//利息收款方账户ID
				Log.print("利息收款方账户ID=" + lInterestPayeeAcctID);
		
				break;
				
			/* 汇款方式内部转账 */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strInterestPayeeName = GetParam(request,"sInterestPayeeName");// 收款方名称
				Log.print("利息收款方名称=" + strInterestPayeeName);
				
				strInterestPayeeBankNo = GetParam(request,"sInterestPayeeAccountInternalInternal");// 收款方银行账号
				Log.print("利息收款方银行账号(Iternal)=" + strInterestPayeeBankNo);
				
				strInterestPayeeAcctNo = GetParam(request,"sInterestPayeeAccountInternalCtrl");// 收款方账号
				Log.print("利息收款方账号=" + strInterestPayeeAcctNo);
				
				lInterestPayeeAcctID = GetNumParam(request,"nInterestPayeeAccountID1");//利息收款方账户ID
				Log.print("利息收款方账户ID=" + lInterestPayeeAcctID);
		
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* 划款资料 */
		if(request.getParameter("amount") != null)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("amount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute = DataFormat.getDateTime((String)request.getParameter("tsExecute"));// 执行日
			Log.print("执行日=" + tsExecute);
		}
		
		strNote = GetParam(request,"sNoteCtrl").trim();// 汇款用途
		Log.print("汇款用途=" + strNote);
	
		//Modify by leiyang date 2007/07/25
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
					if(isControl == SETTConstant.OBCommitTimeControlType.RIGID){
						throw new IException("提交时间已超过结算最迟接收时间");
					}
					else{
						tsExecute.setDate(tsExecute.getDate() + 1);
						isSommitTimes = true;
					}
				}
				else if(lCTHours == lTNHours) {
					if(lCTMinutes < lTNMinutes){
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
	
	// 数字签名
	
		if(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL
				|| strAction==OBConstant.SettInstrStatus.SAVE
				|| strAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL) 
		{
			SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
			SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
			boolean validate = facade.validateSignFromClient(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW,request);
			if(!validate)
			{
				throw new Exception("验证客户端签名信息失败!");
			}			
		}
    

		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
		/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		
		financeInfo.setTimestamp(timestamp);
		financeInfo.setSignatureOriginalValue(signatureOriginalValue);
		financeInfo.setSignatureValue(signatureValue);
				
		/* 付款方资料 */
		financeInfo.setPayerName( strPayerName );// 付款方名称
		financeInfo.setPayerAcctID ( lPayerAcctID );//付款方账户ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// 银行账号
		financeInfo.setPayerAcctNo( strPayerAcctNo );// 付款方账号
		financeInfo.setDepositNo( strDepositNo );// 通知存款单据号
		financeInfo.setSubAccountID (lSubAccountID);//子账户ID
		financeInfo.setDepositStart( tsNotifyDepositStart );// 通知存款起始日
		financeInfo.setNoticeDay( lNoticeDay );// 通知存款品种
		financeInfo.setDepositAmount( dDepositAmount );// 通知存单金额
		financeInfo.setDepositBalance( dDepositBalance );// 通知存单余额
		/* 收款方资料 */
		financeInfo.setRemitType ( lRemitType );// 汇款方式
		financeInfo.setInterestRemitType( lInterestRemitType );//利息汇款方式
		financeInfo.setPayeeName(strPayeeName) ;
		clientCapInfo.setPayeeName(strPayeeName);// 收款方名称
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); 
		clientCapInfo.setPayeeAccoutNO( strPayeeAcctNo );// 收款方账号
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//收款方账户ID
		financeInfo.setInterestPayeeAcctID ( lInterestPayeeAcctID );//收款方账户ID
		financeInfo.setInterestPayeeName(strInterestPayeeName) ;
		financeInfo.setInterestPayeeAcctNo (strInterestPayeeAcctNo); 
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setPayeeProv( strPayeeProv );
				clientCapInfo.setPayeeProv( strPayeeProv );// 汇入省
				financeInfo.setPayeeCity(strPayeeCity );
				clientCapInfo.setCity( strPayeeCity );// 汇入市
				financeInfo.setPayeeBankName( strPayeeBankName );
				clientCapInfo.setPayeeBankName( strPayeeBankName );// 汇入行名称
				financeInfo.setIsCpfAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setPayeeBankNo( "" );// 收款方银行账号
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//是中油财务内部账户
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		switch ((int)lInterestRemitType)
		{
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setInterestPayeeProv( strInterestPayeeProv );
				clientCapInfo.setPayeeProv( strInterestPayeeProv );// 汇入省
				financeInfo.setInterestPayeeCity(strInterestPayeeCity );
				clientCapInfo.setCity( strInterestPayeeCity );// 汇入市
				financeInfo.setInterestPayeeBankName( strInterestPayeeBankName );
				clientCapInfo.setPayeeBankName( strInterestPayeeBankName );// 汇入行名称
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setInterestPayeeBankNo( "" );// 收款方银行账号
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//是中油财务内部账户
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* 划款资料 */
		financeInfo.setAmount( dAmount );// 金额
		financeInfo.setExecuteDate( tsExecute );// 执行日
		financeInfo.setConfirmDate( tsExecute );//确认日期
		financeInfo.setNote( strNote );// 汇款用途
		/* 从session中获取相应数据 */
		financeInfo.setConfirmUserID( sessionMng.m_lUserID );
		clientCapInfo.setInputUserID( sessionMng.m_lUserID );// 确认人ID
		financeInfo.setClientID( sessionMng.m_lClientID );
		clientCapInfo.setClientID( sessionMng.m_lClientID );// 交易提交单位
		financeInfo.setCurrencyID( sessionMng.m_lCurrencyID );
		clientCapInfo.setCurrencyID( sessionMng.m_lCurrencyID );// 交易币种
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//默认办事处ID
		/* 确定网上交易类型和汇款方式 */
		financeInfo.setTransType( OBConstant.SettInstrType.NOTIFYDEPOSITDRAW );			
		/*确定指令状态*/
		if(financeInfo.getStatus() == -1)
		{
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}
		financeInfo.setDtModify(dtmodify);
			
		if(lInterestPayeeAcctID!=-1&&NameRef.getAccountOfficeByID(lInterestPayeeAcctID)!=NameRef.getAccountOfficeByID(lPayerAcctID))
		{
			throw new IException("付款方帐号和利息收款方账号不是同机构的账户");
		}
		if(lInterestPayeeAcctID!=-1&&NameRef.getAccountOfficeByID(lInterestPayeeAcctID)!=NameRef.getAccountOfficeByID(lPayeeAcctID)){
			throw new IException("付款方帐号和本金收款方账号不是同机构的账户");
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
		Log.print("--------------------lInstructionID="+lInstructionID+"----------payerid="+financeInfo.getClientID());

		/* 调用EJB方法保存(或修改)和提取信息对象 */
		//刷新问题
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
				
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
				
			financeInfo.setInutParameterInfo(pInfo);
			
						//将签名值与原始数据保存
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			//Boxu Add 2009年5月22日 增加日志记录
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
						//数据访问对象
						AttachOperation attachOperation = new AttachOperation();
						attachOperation.updateOBTransCode(tempTransCode,String.valueOf(lInstructionID));
					}
			}
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
		else
		{
			if (blnClickCheck)
			{
			
				//将签名值与原始数据保存
				financeInfo.setSignatureValue(signatureValue);	
				financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				
				//Boxu Add 2009年5月22日 增加日志记录
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
							//数据访问对象
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
					msg =  "保存成功, 时间已超过结算最迟接收时间, 执行日延迟一日!";
				}
				else {
					msg =  "保存成功";
				}
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		
				/*保存汇款用途摘要信息*/
				
				
				strTemp = (String)request.getAttribute("sNote");
				if(strTemp!=null&&!strTemp.equals("")){
					AbstractID = Long.parseLong(strTemp);//汇款用途ID
					
					AbstractCode = (String)request.getAttribute("sCode");//汇款用途编号
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
	    rd.forward(request, response);

	} 
	catch(IException ie) 
	{
		Log.print("S11-Ctr.jsp:EJB调用异常或者跳转有错");
		session.setAttribute("financeInfo", financeInfo);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	finally
	{
		//Boxu Add 2009年5月22日 增加日志记录
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