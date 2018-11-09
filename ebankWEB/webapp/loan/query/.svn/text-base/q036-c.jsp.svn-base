<%
/**
 * 页面名称 ：q036-c.jsp
 * 页面功能 : 合同修改
 * 作    者 ：曾海燕
 * 日    期 ：2003-10-14
 * 特殊说明 ：
 *			  
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import = "java.util.*,
com.iss.itreasury.util.*, com.iss.itreasury.loan.util.*, 
com.iss.itreasury.system.approval.bizlogic.ApprovalBiz,
com.iss.itreasury.loan.contract.dao.*,
com.iss.itreasury.ebank.util.*,			
com.iss.itreasury.ebank.obdataentity.*,	
com.iss.itreasury.ebank.obquery.bizlogic.*,
com.iss.itreasury.ebank.obquery.dataentity.*,
com.iss.itreasury.loan.contract.dataentity.*, 
com.iss.itreasury.loan.contract.bizlogic.* "%>

<%
	try
	{
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
		
		//定义变量
		long lContractID = -1;//合同ID
		long lContractIDFrom = -1; //合同号“由”
		long lContractIDTo = -1; //合同号“到”
		String sContractNoFrom = ""; //合同编号起始
		String sContractNoTo = ""; //合同编号结束
		long lConsignClientID = -1; //委托单位
		long lClientID = -1; //借款单位
		String sConsignClientName = ""; //委托单位名称
		String sClientName = ""; //借款单位名称
		double dAmountFrom = 0.0; //金额“由”
		double dAmountTo = 0.0; //金额“到”
		String sLoanStart = ""; //贷款日期"由"
		String sLoanEnd = ""; //贷款日期"到"
		long lIntervalNum = -1; //贷款期限
		String sInputStart = ""; //合同录入日期"由"
		String sInputEnd = ""; //合同录入日期"到"
		long lStatusID = -1; //合同状态
		long lType = -1; //贷款类型
		long lAction = -1; //操作状态

		//分页变量
		long lOrderParam = -1;
		long lDesc = -1;
		long lPageNo = -1;

		//取参数变量
		String strTemp = "";
		strTemp = request.getParameter("contractID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lContractID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContractID = -1;
			}
		}

		strTemp = request.getParameter("ctrlContractIDFrom");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lContractIDFrom = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContractIDFrom = -1;
			}
		}

		strTemp = request.getParameter("ctrlContractIDTo");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lContractIDTo = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContractIDTo = -1;
			}
		}

		strTemp = request.getParameter("ctrlContractIDFromCtrl");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sContractNoFrom = strTemp;
			}
			catch (Exception e)
			{
				sContractNoFrom = "";
			}
		}

		strTemp = request.getParameter("ctrlContractIDToCtrl");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sContractNoTo = strTemp;
			}
			catch (Exception e)
			{
				sContractNoTo = "";
			}
		}

		strTemp = request.getParameter("ctrlConsignClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lConsignClientID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lConsignClientID = -1;
			}
		}

		strTemp = request.getParameter("ctrlClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lClientID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lClientID = -1;
			}
		}

		strTemp = request.getParameter("ctrlConsignClientIDCtrl");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sConsignClientName = strTemp;
			}
			catch (Exception e)
			{
				sConsignClientName = "";
			}
		}

		strTemp = request.getParameter("ctrlClientIDCtrl");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sClientName = strTemp;
			}
			catch (Exception e)
			{
				sClientName = "";
			}
		}

		strTemp = request.getParameter("txtAmountFrom");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				dAmountFrom = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp));
			}
			catch (Exception e)
			{
				dAmountFrom = -1;
			}
		}

		strTemp = request.getParameter("txtAmountTo");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				dAmountTo = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp));
			}
			catch (Exception e)
			{
				dAmountTo = -1;
			}
		}

		strTemp = request.getParameter("txtLoanStart");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sLoanStart = strTemp;
			}
			catch (Exception e)
			{
				sLoanStart = "";
			}
		}

		strTemp = request.getParameter("txtLoanEnd");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sLoanEnd = strTemp;
			}
			catch (Exception e)
			{
				sLoanEnd = "";
			}
		}

		strTemp = request.getParameter("txtIntervalNum");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lIntervalNum = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lIntervalNum = -1;
			}
		}

		strTemp = request.getParameter("txtInputStart");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sInputStart = strTemp;
			}
			catch (Exception e)
			{
				sInputStart = "";
			}
		}

		strTemp = request.getParameter("txtInputEnd");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sInputEnd = strTemp;
			}
			catch (Exception e)
			{
				sInputEnd = "";
			}
		}

		strTemp = request.getParameter("selStatusID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lStatusID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lStatusID = -1;
			}
		}

		strTemp = request.getParameter("type");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lType = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lType = -1;
			}
		}

		strTemp = request.getParameter("action");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lAction = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lAction = -1;
			}
		}

		strTemp = request.getParameter("lOrderParam");
		System.out.println("lOrderParam="+strTemp);
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lOrderParam = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lOrderParam = 1;
			}
		}

		strTemp = request.getParameter("lDesc");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lDesc = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lDesc = -1;
			}
		}

		strTemp = request.getParameter("txtPageNo");
		System.out.println("txtPageNo="+strTemp);
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lPageNo = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lPageNo = 1;
			}
		}
		else
		{
			lPageNo = 1;
		}

		ContractQueryInfo qInfo = new ContractQueryInfo();
		qInfo.setTypeID(lType);
		qInfo.setActionID(lAction);
		qInfo.setUserID(sessionMng.m_lUserID);
		qInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		qInfo.setContractIDFrom(lContractIDFrom);
		qInfo.setContractIDTo(lContractIDTo);
		qInfo.setContractNoFrom(sContractNoFrom);
		qInfo.setContractNoTo(sContractNoTo);
		qInfo.setConsignClientName(sConsignClientName);
		qInfo.setClientName(sClientName);
		qInfo.setConsignClientID(lConsignClientID);
		qInfo.setClientID(lClientID);
		qInfo.setAmountFrom(dAmountFrom);
		qInfo.setAmountTo(dAmountTo);
		qInfo.setLoanStart(DataFormat.getDateTime(sLoanStart));
		qInfo.setLoanEnd(DataFormat.getDateTime(sLoanEnd));
		qInfo.setIntervalNum(lIntervalNum);
		qInfo.setInputStart(DataFormat.getDateTime(sInputStart));
		qInfo.setInputEnd(DataFormat.getDateTime(sInputEnd));
		qInfo.setStatusID(lStatusID);
		qInfo.setPageLineCount(10);
		qInfo.setPageNo(lPageNo);
		qInfo.setOrderParam(lOrderParam);
		qInfo.setDesc(lDesc);

		request.setAttribute("qInfo", qInfo);
	
		ContractInfo info = null;
		if ( lContractID > 0 )
		{
			//调用EJB的方法
			//ContractHome home = (ContractHome) EJBObject.getEJBHome("ContractHome");
			OBContractQuery contract = new OBContractQuery();
			info = contract.findByID(lContractID);
			String strURL = "";
			Collection cApproval = null;
			
			if ( qInfo.getActionID() == 3 )//合同查看
			{
				long lModuleID = Constant.ModuleType.LOAN; //模块类型
				long lLoanTypeID = -1; //贷款类型
				long lActionID = Constant.ApprovalAction.LOAN_CONTRACT; //合同审核
				ApprovalBiz appbiz = new ApprovalBiz();
				ContractDao cDao = new ContractDao();
				
				//取得审核的贷款类型
				lLoanTypeID = cDao.getApprovalLoanType(info.getLoanTypeID()); 
		
				//取得审批意见
				cApproval = appbiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lContractID,-1);
				request.setAttribute("cApproval", cApproval);
				
				if ( lType ==LOANConstant.LoanType.YT )
				{
					strURL = "/loan/query/q040-v.jsp";
				}
				else  if ( lType ==LOANConstant.LoanType.TX )
				{
					strURL = "/loan/query/q053-v.jsp";
				}
				else
				{
					strURL = "/loan/query/q037-v.jsp";
				}
			}
	
			request.setAttribute("ContractInfo", info);
			/* 获取上下文环境 */
			ServletContext sc = getServletContext();

			/* 设置返回地址 */
			RequestDispatcher rd = null;
			rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));

			/* forward到结果页面 */
			rd.forward(request, response);
			return;
		}
	}
	catch (IException ie)
	{
		//LOANHTML.showExceptionMessage(out, sessionMng, ie, request, response, "客户管理", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return;

	}
%>
		