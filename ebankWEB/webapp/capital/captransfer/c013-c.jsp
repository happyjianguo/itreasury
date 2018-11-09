<%--
/*
 * 程序名称：c003-c.jsp
 * 功能说明：资金划拨提交控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日 
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
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";  
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
	long lInstructionID = -1;//指令序号
	double dPayerCurrentBalance = 0.0;//当前余额
	double dPayerUsableBalance = 0.0;//可用余额
	long lChildClientID = -1;//下属单位ID
    String strPayerName = "";//  付款方名称
	String strPayerBankNo = "";// 银行账号
	String strPayerAcctNo = "";// 付款方账号
	long lPayerAcctID = -1;//付款方账户ID
	long lRemitType = 0;// 汇款方式
	long lPayeeAcctID = -1;//收款方账户ID
	String strPayeeName = "";// 收款方名称
	String strPayeeBankNo = "";// 收款方银行账号
	String strPayeeAcctNo = "";// 收款方账号
	String strPayeeBankName = "";// 汇入行名称
	String strPayeeProv = "";// 汇入省
	String strPayeeCity = "";// 汇入市
	double dAmount = 0.0;// 金额
	Timestamp tsExecute = null;// 执行日
	String strNote = "";// 汇款用途
	
	/* 初始化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	ClientCapInfo clientCapInfo = new ClientCapInfo();
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
	
        // 用户登录检测
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
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
		/* 指令序号 */
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		
		/* 付款方资料 */
		lChildClientID = GetNumParam(request,"lClientID");
		Log.print("下属单位ID=" + lChildClientID);
		
		strPayerName = GetParam(request,"sPayerName");// 付款方名称
		Log.print("付款方名称=" + strPayerName);
		
		//System.out.println(request.getParameter("nPayerAccountID"));
		lPayerAcctID = GetNumParam(request,"nPayerAccountID");//付款方账户ID
		Log.print("付款方账户ID=" + lPayerAcctID);
		
		strPayerBankNo = GetParam(request,"sBankAccountNO");// 银行账号
		Log.print("银行账号=" + strPayerBankNo);

		strPayerAcctNo = GetParam(request,"sPayerAccountNoZoomCtrl");// 付款方账号
		Log.print("付款方账号=" + strPayerAcctNo);
		
		if(request.getParameter("dPayerCurrBalance") != null)
		{
			dPayerCurrentBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerCurrBalance"))).doubleValue();// 当前余额
			Log.print("当前余额=" + dPayerCurrentBalance);
		}
		
		if(request.getParameter("dPayerUsableBalance") != null)
		{
			dPayerUsableBalance = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dPayerUsableBalance"))).doubleValue();// 可用余额
			Log.print("可用余额=" + dPayerUsableBalance);
		}
		
		/* 收款方资料 */	
		lRemitType = OBConstant.SettRemitType.BANKPAY;
		Log.print("汇款方式=" + lRemitType);			
			
		lPayeeAcctID = GetNumParam(request,"nPayeeAccountID");//收款方账户ID
		Log.print("收款方账户ID=" + lPayeeAcctID);
		
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
			
				strPayeeName = GetParam(request,"sPayeeName");// 收款方名称
				Log.print("收款方名称=" + strPayeeName);
				
				strPayeeAcctNo = GetParam(request,"sPayeeAccountInternalCtrl");// 收款方账号
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
			/* 汇款方式内部转账 */
			case (int)OBConstant.SettRemitType.OTHER :
			
				strPayeeName = GetParam(request,"sPayeeNameOther");// 收款方名称
				Log.print("收款方名称=" + strPayeeName);
								
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
		/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if(lInstructionID>0)
		{
			financeInfo.setID(lInstructionID);		
		}
		/* 付款方资料 */
		financeInfo.setChildClientID(lChildClientID);//下属单位
		financeInfo.setPayerName( strPayerName );// 付款方名称
		financeInfo.setPayerAcctID ( lPayerAcctID );//付款方账户ID
		financeInfo.setPayerBankNo( strPayerAcctNo );// 银行账号
		financeInfo.setPayerAcctNo( strPayerAcctNo );// 付款方账号
		financeInfo.setCurrentBalance( dPayerCurrentBalance );// 付款方当前余额
		financeInfo.setUsableBalance( dPayerUsableBalance );// 付款方可用余额
		/* 收款方资料 */
		financeInfo.setRemitType ( lRemitType );// 汇款方式
		financeInfo.setPayeeName(strPayeeName) ;
		clientCapInfo.setPayeeName(strPayeeName);// 收款方名称
		financeInfo.setPayeeAcctNo (strPayeeAcctNo); 
		clientCapInfo.setPayeeAccoutNO( strPayeeAcctNo );// 收款方账号
		financeInfo.setPayeeAcctID ( lPayeeAcctID );//收款方账户ID
		switch ((int)lRemitType)
		{
			case (int)OBConstant.SettRemitType.SELF:
				financeInfo.setPayeeBankNo( "" );// 收款方银行账号
				financeInfo.setPayeeBankName( strPayeeBankName );// 汇入行名称
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.BANKPAY:
				financeInfo.setPayeeProv( strPayeeProv );
				clientCapInfo.setPayeeProv( strPayeeProv );// 汇入省
				financeInfo.setPayeeCity(strPayeeCity );
				clientCapInfo.setCity( strPayeeCity );// 汇入市
				financeInfo.setPayeeBankName( strPayeeBankName );
				clientCapInfo.setPayeeBankName( strPayeeBankName );// 汇入行名称
				financeInfo.setIsCpfAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );
				clientCapInfo.setIsCPFAcct( OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO );//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.INTERNALVIREMENT :
				financeInfo.setPayeeBankNo( "" );// 收款方银行账号
				financeInfo.setIsCpfAcct(  OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES );//是中油财务内部账户
				break;
			case (int)OBConstant.SettRemitType.OTHER :
				financeInfo.setPayeeBankNo( "" );// 收款方银行账号
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
		financeInfo.setCurrencyID( 1 );
		clientCapInfo.setCurrencyID( sessionMng.m_lCurrencyID );// 交易币种
		financeInfo.setOfficeID( sessionMng.m_lOfficeID );//默认办事处ID
		/* 确定网上交易类型和汇款方式 */
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
		
		//网上交易类型
		financeInfo.setTransType( OBConstant.SettInstrType.APPLYCAPITAL );

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
		Log.print("--------------------lInstructionID="+lInstructionID);

		/* 调用EJB方法保存(或修改)和提取信息对象 */
		//刷新问题
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
		/*调用EJB方法查询结果*/
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
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = null;
		

	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/captransfer/c014-v.jsp");
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

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
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }

%>
<!--Forward end-->