<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.ebank.obdiscountapply.dao.*,
				com.iss.itreasury.loan.util.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>	
<%
/////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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
           
		//定义变量，获取请求参数
		
		String strTmp = "";
		String strControl = "";
		String strBackURL = "S125";
		String strDisabled = " disabled";

		long lBillID = -1;
		long[] lBillIDArray = new long[1000];
		String strBillID = "";
		Vector v = null;
			
		long txtContract = -1;			//贴现标示
		String txtContractCode = "";	//贴现申请编号
		long txtClient = -1;			//单位标示
		String txtClientCtrl = "";		//单位名称

		long lContractID = -1;			//贴现标示
		long lCredenceID = -1;			//贴现凭证标示
		Timestamp tsFillDate = null;
		long lDraftTypeID = -1;
		String strDraftTypeName = "";
		String strDraftCode = "";
		Timestamp tsPublicDate = null;
		Timestamp tsAtTerm = null;
		String strApplyClient = "";
		String strApplyAccount = "";
		String strApplyBank = "";
		String strAcceptClient = "";
		String strAcceptAccount = "";
		String strAcceptBank = "";
		double dAmount = 0;
		double dRate = 0;
		double dInterest = 0;
		double dCheckAmount = 0;
		double dBillAmount = 0;
		double dBillInterest = 0;
		double dBillCheckAmount = 0;
		double[] dResult = new double[3];

		long lGrantTypeID = -1;				//放款方式
		long lAccountBankID = -1;			//开户银行
		String strAccountBankName = "";		//开户银行
		String strAccountBankCode = "";		//开户银行账号
		String strReceiveClientCode = "";	//收款单位账号
		String strReceiveClientName = "";	//收款单位名称
		String strRemitBank = "";			//汇入行
		String strRemitInProvince = "";   	//汇入地（省）
		String strRemitInCity = "";       	//汇入地（市）
		long lGrantCurrentAccountID = -1;	//发放至活期账户
		String strGrantCurrentAccountCode = "";//发放至活期账户
		
		//////////////////////////////
		String strContractCode = "";
		long lInputUserID = -1;
		String strInputUserName = "";
		long lStatusID = -1;
		long lCount = 0;

		//////////////////////////////
		
		//贴现EJB
		OBDiscountApplyHome  obDiscountApplyHome = null;
		OBDiscountApply      obDiscountApply = null;
		//DiscountLoanInfo  dli = new DiscountLoanInfo ();
		obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
		obDiscountApply = obDiscountApplyHome.create();
		
		//DiscountCredenceQueryInfo qinfo = new DiscountCredenceQueryInfo();
		DiscountCredenceInfo qinfo = new DiscountCredenceInfo();

		
///////control///////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

	
		strTmp = (String)request.getAttribute("backurl");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strBackURL = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("txtContract");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContract = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("txtClient");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClient = Long.parseLong(strTmp.trim());
		}	 

		strTmp = (String)request.getAttribute("txtClientCtrl");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClientCtrl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("strBillID");
System.out.println("strBillID===================================================================================="+strTmp);

        if(strTmp != null && strTmp.length() > 0 && !strTmp.equals("null"))
        {
			strBillID = strTmp.trim();
			/**
			 * 将一个用","分开的串分解为一个Vector的数组
			 * @param strParam 需要拆分的参数
			 * @return 返回一个Vector，里面是Long型
			 */
			 
			v = DataFormat.changeStringGroup(strBillID);

			if( (v != null) && (v.size() > 0) )
			{
				Iterator it = v.iterator();
                while (it.hasNext() )
                {
					Long lTmp = (Long) it.next();
					lBillIDArray[(int)lCount] = lTmp.longValue();
					lCount++;
				}
			}
		}
		
System.out.println("here");
		//我的代码
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
System.out.println("lCredenceID===================================================================================="+lCredenceID);
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}
		
		//新增
		if(lCredenceID <=0 )
		{
			strTmp = (String)request.getAttribute("lCredenceID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lCredenceID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsFillDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				tsFillDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("lDraftTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lDraftTypeID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("strDraftTypeName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftTypeName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strDraftCode");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftCode = strTmp.trim();
			}
			
			strTmp = (String)request.getAttribute("tsPublicDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsPublicDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsAtTerm");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsAtTerm = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("strApplyClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyClient =strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyBank =strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("dBillAmount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dBillAmount = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dRate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dRate = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dBillInterest");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dBillInterest = Double.parseDouble(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("lGrantTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantTypeID = Long.parseLong(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("naccountbankid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lAccountBankID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("sreceiveclientname");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("txtAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientCode = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitbank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitinprovince");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInProvince = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitincity");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInCity = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("ngrantcurrentaccountid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantCurrentAccountID = Long.parseLong(strTmp.trim());
			}

			//贴现凭证
			qinfo.setID(-1); //贴现凭证标识
			qinfo.setDiscountContractID(lContractID); //贴现合同标识
			//qinfo.setCode(rs.getString("sCode")); //贴现凭证编号
			qinfo.setFillDate(tsFillDate);
			qinfo.setDraftTypeID(lDraftTypeID); //贴现汇票种类标示
			qinfo.setDraftCode(strDraftCode); //贴现汇票号码
			qinfo.setPublicDate(tsPublicDate); //发票日
			qinfo.setAtTerm(tsAtTerm); //到期日
			qinfo.setApplyClientName(strApplyClient); //申请单位名称
			qinfo.setApplyAccount(strApplyAccount); //申请单位账号
			qinfo.setApplyBank(strApplyBank); //申请单位银行
			qinfo.setAcceptClientName(strAcceptClient); //承兑单位名称
			qinfo.setAcceptAccount(strAcceptAccount); //承兑单位账号
			qinfo.setAcceptBank(strAcceptBank); //承兑单位银行
			qinfo.setStatusID(LOANConstant.DiscountCredenceStatus.SUBMIT); //贴现凭证是否删除

			qinfo.setBillAmount(dBillAmount); //贴现凭证金额
			qinfo.setBillInterest(dBillInterest); //贴现凭证利息
			qinfo.setBillCheckAmount(dBillAmount - dBillInterest); //贴现凭证实付贴现额

			qinfo.setInputUserID(sessionMng.m_lUserID);
			qinfo.setInputDate(Env.getSystemDate());
			qinfo.setNextCheckUserID(sessionMng.m_lUserID); //下一个审核人标示
				
			qinfo.setGrantTypeID(lGrantTypeID);
			qinfo.setAccountBankID(lAccountBankID);
			qinfo.setReceiveClientCode(strReceiveClientCode);
			qinfo.setReceiveClientName(strReceiveClientName);
			qinfo.setRemitBank(strRemitBank);
			qinfo.setRemitInProvince(strRemitInProvince);
			qinfo.setRemitInCity(strRemitInCity);
			qinfo.setGrantCurrentAccountID(lGrantCurrentAccountID);
			//rate
			qinfo.setDiscountRate(dRate);
	
			//进行新增
			//lCredenceID = obDiscountApply.saveDiscountCredence(qinfo);
			if (lCredenceID > 0)
			{				
			%>
				<script language="JavaScript">
					alert("请不要重复提交！")
					//eval("self.location='S118.jsp?control=view'");
				</script>
			<%
				response.sendRedirect("d036-v.jsp?control=view&lCredenceID="+lCredenceID+"&backurl=S118");
			}
			else
			{
				lCredenceID = obDiscountApply.saveDiscountCredence(qinfo);
			}
			if (lCredenceID > 0)
			{
for (int i = 0; i < lBillIDArray.length; i++)
{
	if(lBillIDArray[i]>0)
	{
		System.out.println(lBillIDArray[i]);
	}
}
				OBDiscountApplyDao dao = new OBDiscountApplyDao();
				dao.saveDiscountCredenceBill(lCredenceID, lContractID, lBillIDArray);
			}
			
		    request.setAttribute("lCredenceID",lCredenceID+"");
		    request.setAttribute("lContractID",lContractID+"");
			request.setAttribute("strBillID",strBillID);
			request.setAttribute("control","view");
			//下一页
			request.setAttribute("nextPage","d036-v.jsp");
		    /* 获取上下文环境 */
	        ServletContext sc = getServletContext();
	        /* 设置返回地址 */
	        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d033-c.jsp")));
	        /* forward到结果页面 */
	        rd.forward(request, response);
			
		}
		else//进行更新
		{
			strTmp = (String)request.getAttribute("tsFillDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				tsFillDate = DataFormat.getDateTime(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("strDraftCode");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strDraftCode = strTmp.trim();
			}
			
			strTmp = (String)request.getAttribute("tsPublicDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsPublicDate = DataFormat.getDateTime(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("tsAtTerm");
			if( strTmp != null && strTmp.length() > 0 )
			{
				if (!strTmp.trim().equals("null"))
					tsAtTerm = DataFormat.getDateTime(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("strApplyClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strApplyBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strApplyBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptClient");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptClient = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptAccount = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("strAcceptBank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strAcceptBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("dBillAmount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dAmount = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dRate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dRate = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("dBillInterest");
			if( strTmp != null && strTmp.length() > 0 )
			{
				dInterest = Double.parseDouble(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("lGrantTypeID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantTypeID = Long.parseLong(strTmp.trim());
			}
			
			strTmp = (String)request.getAttribute("naccountbankid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lAccountBankID = Long.parseLong(strTmp.trim());
			}

			strTmp = (String)request.getAttribute("sreceiveclientname");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientName = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("txtAccount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strReceiveClientCode = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitbank");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitBank = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitinprovince");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInProvince = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("sremitincity");
			if( strTmp != null && strTmp.length() > 0 )
			{
				strRemitInCity = strTmp.trim();
			}

			strTmp = (String)request.getAttribute("ngrantcurrentaccountid");
			if( strTmp != null && strTmp.length() > 0 )
			{
				lGrantCurrentAccountID = Long.parseLong(strTmp.trim());
			}

			//Discount.saveDiscountCredence(lCredenceID, lContractID, sessionMng.m_lUserID, tsFillDate, lDraftTypeID, strDraftCode, tsPublicDate, tsAtTerm, strApplyClient, strApplyAccount, strApplyBank, strAcceptClient, strAcceptAccount, strAcceptBank, dAmount, dRate, dInterest, sessionMng.m_lOfficeID);

			//贴现凭证
			qinfo.setID(lCredenceID); //贴现凭证标识
			qinfo.setDiscountContractID(lContractID); //贴现凭证标识
			//info.setCode(rs.getString("sCode")); //贴现凭证编号
			qinfo.setFillDate(tsFillDate);
			qinfo.setDraftTypeID(lDraftTypeID); //贴现汇票种类标示
			qinfo.setDraftCode(strDraftCode); //贴现汇票号码
			qinfo.setPublicDate(tsPublicDate); //发票日
			qinfo.setAtTerm(tsAtTerm); //到期日
			qinfo.setApplyClientName(strApplyClient); //申请单位名称
			qinfo.setApplyAccount(strApplyAccount); //申请单位账号
			qinfo.setApplyBank(strApplyBank); //申请单位银行
			qinfo.setAcceptClientName(strAcceptClient); //承兑单位名称
			qinfo.setAcceptAccount(strAcceptAccount); //承兑单位账号
			qinfo.setAcceptBank(strAcceptBank); //承兑单位银行
			qinfo.setStatusID(LOANConstant.DiscountCredenceStatus.SUBMIT); //贴现凭证是否删除

			qinfo.setBillAmount(dBillAmount); //贴现凭证金额
			qinfo.setBillInterest(dBillInterest); //贴现凭证利息
			qinfo.setBillCheckAmount(dBillAmount - dBillInterest); //贴现凭证实付贴现额

			qinfo.setInputUserID(sessionMng.m_lUserID);
			qinfo.setInputDate(Env.getSystemDate());
			qinfo.setNextCheckUserID(sessionMng.m_lUserID); //下一个审核人标示
				
			qinfo.setGrantTypeID(lGrantTypeID);
			qinfo.setAccountBankID(lAccountBankID);
			qinfo.setReceiveClientCode(strReceiveClientCode);
			qinfo.setReceiveClientName(strReceiveClientName);
			qinfo.setRemitBank(strRemitBank);
			qinfo.setRemitInProvince(strRemitInProvince);
			qinfo.setRemitInCity(strRemitInCity);
			qinfo.setGrantCurrentAccountID(lGrantCurrentAccountID);

			Log.print("lGrantTypeID="+lGrantTypeID);
			Log.print("lAccountBankID="+lAccountBankID);
			Log.print("strReceiveClientCode="+strReceiveClientCode);
			Log.print("strReceiveClientName="+strReceiveClientName);
			Log.print("strRemitBank="+strRemitBank);
			Log.print("strRemitInProvince="+strRemitInProvince);
			Log.print("strRemitInCity="+strRemitInCity);
			Log.print("lGrantCurrentAccountID="+lGrantCurrentAccountID);
			
			obDiscountApply.updateDiscountCredence(qinfo);
			
		    request.setAttribute("lCredenceID",lCredenceID+"");
		    request.setAttribute("lContractID",lContractID+"");
			request.setAttribute("strBillID",strBillID);
			request.setAttribute("control","view");
			//下一页
			request.setAttribute("nextPage","d036-v.jsp");
		    /* 获取上下文环境 */
	        ServletContext sc = getServletContext();
	        /* 设置返回地址 */
	        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d033-c.jsp")));
	        /* forward到结果页面 */
	        rd.forward(request, response);
			
		}
		
	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>