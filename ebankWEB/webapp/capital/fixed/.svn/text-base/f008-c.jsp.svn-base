<%--
/*
 * 程序名称：f008-c.jsp
 * 功能说明：定期转存提交控制页面
 * 作　　者：葛亮
 * 完成日期：2007年04月18日
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
	/* 标题固定变量 */
	String strTitle = "[定期转存]";
	
%>
<%


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
    String sDepositBillNo = "";//定期存单号
    Timestamp sDepositBillStartDate = null;//新定期子账户开始日
    Timestamp sDepositBillEndDate = null;//新定期子账户结束日
    long sDepositBillPeriod = -1;//新定期子账户期限
    long sDepositInterestDeal = -1;//新定期存款处理方式
    long lInstructionID = -1;//指令ID
    long sDepositInterestToAccountID  = -1;//账户ID
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
%>

<!--Get Data start-->
<%
		/* 从FORM表单中获取相应数据 */
		
		/* 定期子账号 */
		sDepositBillNo = GetParam(request,"sPayerAccountNoZoomCtrl");
	    Log.print("定期存款子账号=" + sDepositBillNo);
        
		/* 定期子账户存款起始日 */
		sDepositBillStartDate = DataFormat.getDateTime(GetParam(request,"dPayerStartDate"));
	    Log.print("新定期存款起始日=" + sDepositBillStartDate);
	    
	    /* 定期子账户存款到期日 */
	    sDepositBillEndDate = DataFormat.getDateTime(GetParam(request,"dPayerEndDate"));
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
	    sDepositInterestToAccountID = GetNumParam(request,"lInterestToAccountID");
		Log.print("利息转至活期账户ID=" + sDepositInterestToAccountID);
		
		
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
			
			String _blnIsNeedApproval = request.getParameter("isNeedApproval");
			long lActionStatus =  Long.parseLong(request.getParameter("actionStatus"));
			
			String strIsRefused = request.getParameter("isRefused");
			strIsRefused = strIsRefused == null ? "" : strIsRefused;
			
			//特殊处理
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
		/* 初始化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();

		/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		/* 付款方资料 */
		/*financeInfo.setPayerName( strPayerName );// 付款方名称
		financeInfo.setPayerAcctID ( lPayerAcctID );//付款方账户ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// 银行账号
		financeInfo.setPayerAcctNo( strPayerAcctNo );// 付款方账号
		*/
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
		financeInfo.setSDepositBillNo(sDepositBillNo);
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
		financeInfo.setTransType( OBConstant.SettInstrType.DRIVEFIXDEPOSIT );//定期续存类型
		/*确定指令状态*/
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

			//add by mingfang
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			
		lInstructionID = financeInstr.addTrans(financeInfo);
		financeInfo.setID(lInstructionID);
		session.setAttribute("lInstructionID", String.valueOf(lInstructionID));
		/*调用EJB方法查询结果*/
	//	financeInfo = financeInstr.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	   
	    
	    //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/fixed/f006-c.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward到结果页面 */
	    rd.forward(request, response);
	    sessionMng.getActionMessages().addMessage("保存成功！");

	} 
	catch(IException ie) 
	{
		Log.print("f008-c.jsp:EJB调用异常或者跳转有错");
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }

%>
<!--Forward end-->