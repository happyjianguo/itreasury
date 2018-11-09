<%--
/*
 * 程序名称：C11.jsp
 * 功能说明：资金划拨提交控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月18日
 */
--%>

<!--Header start-->


<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";
%>

<%	
	/* 定义对表单的相应变量 */
	long lInstructionID = -1;//指令序号
	
    String strPayerName = "";//  付款方名称
	String strPayerBankNo = "";// 银行账号
	String strPayerAcctNo = "";// 付款方账号
	long lRemitType = 0;// 汇款方式
	String strPayeeName = "";// 收款方名称
	String strPayeeBankNo = "";// 收款方银行账号
	String strPayeeAcctNo = "";// 收款方账号
	String strPayeeBankName = "";// 汇入行名称
	String strPayeeProv = "";// 汇入省
	String strPayeeCity = "";// 汇入市
	double dAmount = 0.0;// 金额
	Timestamp tsExecute = null;// 执行日
	String strNote = "";// 汇款用途
   long  acctid=6;
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
       
%>

<!--Get Data start-->
<%
		/* 从FORM表单中获取相应数据 */
		/* 指令序号 */
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		
		/* 付款方资料 */
		strPayerName = GetParam(request,"sPayerName");// 付款方名称
		Log.print("付款方名称=" + strPayerName);
		
		strPayerBankNo = GetParam(request,"sBankAccountNO");// 银行账号
		Log.print("银行账号=" + strPayerBankNo);

		strPayerAcctNo = GetParam(request,"sPayerAccountNoZoomCtrl");// 付款方账号
		Log.print("付款方账号=" + strPayerAcctNo);
		
		/* 收款方资料 */
		lRemitType = GetNumParam(request,"nRemitType");// 汇款方式
		Log.print("汇款方式=" + lRemitType);
		
		switch ((int)lRemitType)
		{
			/* 汇款方式本转 */
			case (int)OBConstant.SettRemitType.SELF :
				
				strPayeeName = GetParam(request,"sPayeeNameZoomBankCtrl");//收款方名称
				Log.print("收款方名称=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeBankNoZoomCtrl");// 收款方账号
				Log.print("收款方账号=" + strPayeeAcctNo);
				
				strPayeeBankName = GetParam(request,"sPayeeBankNameRead");// 汇入行名称
				Log.print("汇入行名称=" + strPayeeBankName);
				
				break;
				
			/* 汇款方式电汇、信汇、票汇 */
			case (int)OBConstant.SettRemitType.BANKPAY :
			case (int)OBConstant.SettRemitType.FINCOMPANYPAY :
			case (int)OBConstant.SettRemitType.PAYSUBACCOUNT :

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
				
				break;
				
			/* 汇款方式内部转账 */
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
			
				strPayeeName = GetParam(request,"sPayeeName");// 收款方名称
				Log.print("收款方名称=" + strPayeeName);
				
				strPayeeBankNo = GetParam(request,"sPayeeAccountInternalInternal");// 收款方银行账号
				Log.print("收款方银行账号(Iternal)=" + strPayeeBankNo);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// 收款方账号
				Log.print("收款方账号=" + strPayeeAcctNo);
				
				break;
				
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;
		}
		/* 划款资料 */
		if(request.getParameter("dAmount") != null)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dAmount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute = DataFormat.getDateTime((String)request.getParameter("tsExecute"));// 执行日
			Log.print("执行日=" + tsExecute);
		}
		
		strNote = GetParam(request,"sNote");// 汇款用途
		Log.print("汇款用途=" + strNote);
		
	
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
		/* 初始化信息类 */
		FinanceInfo financeInfo = new FinanceInfo();
		ClientCapInfo clientCapInfo = new ClientCapInfo();

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
		/* 收款方资料 */
		financeInfo.setRemitType ( lRemitType );// 汇款方式
		financeInfo.setPayeeName(strPayeeName) ;
		clientCapInfo.setPayeeName(strPayeeName);// 收款方名称
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); 
		clientCapInfo.setPayeeAccoutNO( strPayeeAcctNo );// 收款方账号
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.SELF:
				financeInfo.setPayeeBankNo( "" );// 收款方银行账号
				financeInfo.setPayeeBankName( strPayeeBankName );// 汇入行名称
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.BANKPAY:
			case (int)OBConstant.SettRemitType.FINCOMPANYPAY:
			case (int)OBConstant.SettRemitType.PAYSUBACCOUNT :
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
		    	Log.print("C11.jsp:Wrong Remit Type:line220 - line235");
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
		financeInfo.setPayerAcctID(acctid);
		/* 确定网上交易类型和汇款方式 */
		financeInfo.setTransType( OBConstant.QueryInstrType.CAPTRANSFER );//网上交易类型 
		financeInfo.setRemitType( lRemitType );//汇款方式
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
		Log.print("--------------------lInstructionID="+lInstructionID+"----------payerid="+financeInfo.getClientID());

		/* 调用EJB方法保存(或修改)和提取信息对象 */
		lInstructionID = financeInstr.addCapitalTrans(financeInfo);
		financeInfo.setID(lInstructionID);
		Log.print("-------------------------------------test");
		
		/*初始化信息查询类*/
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		/*调用类方法查询结果集*/
		financeInfo = obFinanceInstrDao.findByID( lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */

	    
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/finance/V12.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	    
	    /* forward到结果页面 */
	    rd.forward(request, response);

	} 
	catch(IException ie) 
	{
		Log.print(ie.getMessage());
    }

%>
<!--Forward end-->