<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[提前还款通知单]";
%>
<%String strContext = request.getContextPath();%>
<%
	long ContractID = -1; //合同的ID
	long PayID = -1;//放款通知单ID
	long lID = -1; //提前还款通知单ID
	long lIsChange = -1; //判断是否用来更新,>0则表示更新

	try
	{
		/**
		* isLogin start
		*/
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
		//从指令查询过来的参数
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
			
		//配合指令查询过来的参数,如果为"yes",则表示查询后并修改过
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

		//取参数变量
		String strTemp = "";
		strTemp = (String)request.getAttribute("ctrlContractIDFrom");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ContractID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ContractID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("ctrlPayID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PayID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				PayID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("lIsChange");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lIsChange = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lIsChange = -1;
			}
		}

		/*ContractInfo cInfo = new ContractInfo();
		if ( ContractID > 0 )
		{
			//调用EJB的方法
			ContractHome home = (ContractHome) EJBObject.getEJBHome("ContractHome");
			Contract contract = home.create();
			cInfo = contract.findByID(ContractID);
		}
		
		//得到放款通知单信息
		LoanPayNoticeInfo lInfo = new LoanPayNoticeInfo();
		if ( PayID > 0 )
		{
			LoanPayNoticeHome loanPayNoticeHome = (LoanPayNoticeHome)EJBObject.getEJBHome("LoanPayNoticeHome");
            LoanPayNotice loanPayNotice = loanPayNoticeHome.create();
			lInfo = loanPayNotice.findLoanPayNoticeByID(PayID);
		}*/
System.out.println("@@@@@@@@@@@@@@@@="+ContractID+"$$$$$$$$$$$$$$$$$$$$+"+PayID+"&&&&&&&&&&&&&&&"+lID);
		OBAheadRepayNoticeHome aheadRepayNoticeHome = (OBAheadRepayNoticeHome)EJBObject.getEJBHome("OBAheadRepayNoticeHome");
		OBAheadRepayNotice OBAheadRepayNotice = aheadRepayNoticeHome.create();
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		info = OBAheadRepayNotice.findAheadRepayNotice(ContractID,PayID,lID);
System.out.println("$$$$$$$$$$$$$$$"+info);
		/*info.setClientName(cInfo.getBorrowClientName());//借款单位
		info.setContractID(cInfo.getContractID());//合同ID
		info.setContractCode(cInfo.getContractCode());//合同编号
		info.setIntervalNum(cInfo.getIntervalNum());//贷款期限
		info.setContractAmount(cInfo.getExamineAmount());//合同金额
		info.setContractBalance(cInfo.getAInfo().getBalanceAmount());//合同余额
		
		info.setLetoutNoticeID(lInfo.getID());//放款通知单ID
		info.setLetoutNoticeCode(lInfo.getCode());//放款通知单编号
		info.setLetoutNoticeRate(lInfo.getInterestRate());//放款通知单利率
		info.setLetoutNoticeAmount(lInfo.getAmount());//放款通知单金额
		info.setLetoutNoticeBalance(lInfo.getBalance());//放款通知单余额
		
		info.setPlanID(cInfo.getPlanVersionID());//合同计划ID*/
	
		request.setAttribute("info", info);  		
		request.setAttribute("txtAction",txtAction);

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		RequestDispatcher rd = null;
		
		if (info.getLetoutNoticeBalance()==0)
		{
%>
<script >
if (confirm("此放款余额为零，无法申请贷款还款！是否返回新增？"))
{
	history.back(-1);
}
</script>
<%		
		}
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		if(lID>0)
		{
			//如果是指令提交过来的页面,并且登录人不是撰写人,则只能看(并且状态不能是已接收)
			if(txtAction.equals("modify")&&info.getInputUserID()==sessionMng.m_lUserID&&!txtChanged.equals("yes")&&info.getStatusID()!=OBConstant.LoanInstrStatus.ACCEPT&&info.getStatusID()!=OBConstant.LoanInstrStatus.REFUSE&&info.getStatusID()!=OBConstant.LoanInstrStatus.CANCEL&&info.getStatusID()!=OBConstant.LoanInstrStatus.APPROVE)
			{
				System.out.println("inininiininini="+txtChanged);
				
				//构建页面分发时需要用到的实体
				pageControllerInfo.setUrl(strContext + "/loan/aheadrepaynotice/a003-v.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				
				request.setAttribute("lID", lID+"");
				request.setAttribute("txtChanged","yes");
			}else
			{
				//保存结束
				//构建页面分发时需要用到的实体
				pageControllerInfo.setUrl(strContext + "/loan/aheadrepaynotice/a005-v.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
		}else
		{
			//更新
			//构建页面分发时需要用到的实体
			pageControllerInfo.setUrl(strContext + "/loan/aheadrepaynotice/a003-v.jsp");
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			request.setAttribute("lID", lID+"");
		}

		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

			