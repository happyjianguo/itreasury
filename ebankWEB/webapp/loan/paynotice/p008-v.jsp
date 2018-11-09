<%@ page contentType="text/html;charset=gbk" %> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 
<%@page import="java.util.*,
                java.sql.*,
                java.rmi.RemoteException,
                java.net.URLEncoder,
                com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.system.approval.bizlogic.*,
				com.iss.itreasury.system.approval.dataentity.*,
                com.iss.itreasury.ebank.obpaynotice.dataentity.*,
                com.iss.itreasury.ebank.obpaynotice.dao.*,
                com.iss.itreasury.ebank.obpaynotice.bizlogic.*,
                com.iss.itreasury.loan.contract.bizlogic.*,
                com.iss.itreasury.loan.contract.dataentity.*,
                java.util.*"%>      
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />
<%!
	public static String getAmountByOrder(String strAmount, int iOrderID) throws Exception
	{
		String strReturn = "&nbsp;";
		int nAmountLength = 12;
		try
		{
			if (iOrderID < strAmount.length())
			{
				if (iOrderID <= 2)
				{
					return (strAmount.substring(strAmount.length() - iOrderID, strAmount.length() - iOrderID + 1));
				}
				else
				{
					return (strAmount.substring(strAmount.length() - iOrderID - 1, strAmount.length() - iOrderID));
				}
			}
			if((iOrderID == strAmount.length()) && (iOrderID <= nAmountLength))
			{
					return "￥";//sessionMng.m_strCurrencySymbol;
			}
		}
		catch (Exception e)
		{
		}
		return strReturn;
	}
%>

<%
	String strTitle = "打印放款通知单";
    try
    {
        //判断是否登录
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
                

        //定义变量
        long lOfficeID = sessionMng.m_lOfficeID;
				
        //定义变量
        String strTmp = "";
        long lContractID = -1; //合同号
        long lPayID = -1;      //放款通知单ID
                

        //合同ID
        strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
        {
        	lContractID = Long.parseLong(strTmp.trim());
        }
        System.out.println("lContractID = "+lContractID);
        
        //放款通知单ID
        strTmp = (String)request.getAttribute("lPayID");
        if( strTmp != null && strTmp.length() > 0 )
        {
        	lPayID = Long.parseLong(strTmp.trim());
        }
        System.out.println("lPayID = "+lPayID);
                
        //合同的EJB
        /*
		ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
        Contract c_ejb = contractHome.create();
        ContractInfo c_info = new ContractInfo();
          */      
        //得到合同内容
        //c_info = c_ejb.findByID(lContractID);
                
        //贷款申请单的EJB
        OBPayNoticeHome PayNoticeHome = (OBPayNoticeHome)EJBObject.getEJBHome("OBPayNoticeHome");
        OBPayNotice PayNotice = PayNoticeHome.create();
        PayNoticeInfo info = new PayNoticeInfo();
                
        //得到放款通知单内容
       	info = PayNotice.findPayNoticeByID(lPayID,lContractID);

		if(info != null)
		{
			
			
			try
			{
			String strAmount = "";
				strAmount = "<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
          	  +"<tr>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">十</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">亿</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">千</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">百</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">十</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">万</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">千</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">百</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">十</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">元</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">角</span></td>"
          		+"<td width=\"8\" align=\"center\" class=\"In1-td-bottom\"><span class=\"f12\">分</span></td>"
       		+"</tr>"
          	+"<tr>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),12)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),11)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),10)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),9)+"</span></td>"
				+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),8)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),7)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),6)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),5)+"</span></td>"
				+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),4)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),3)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-rightbottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),2)+"</span></td>"
          		+"<td align=\"center\" class=\"In1-td-bottom\"><span class=\"f12\">"+getAmountByOrder(DataFormat.formatAmount(info.getAmount()),1)+"</span></td>"
       		+"</tr>"
          	+"</table>";//本金金额
			ShowGrantLoanInfo sinfo = new ShowGrantLoanInfo();
			String strExecuteDate = DataFormat.getDateString(Env.getSystemDate(1,1));
			if (strExecuteDate.length() < 10)
			{
				strExecuteDate = "0000000000";
			}
			sinfo.setYear(strExecuteDate.substring(0, 4));
			sinfo.setMonth(strExecuteDate.substring(5, 7));
			sinfo.setDay(strExecuteDate.substring(8, 10));
			sinfo.setAbstract(DataFormat.formatString(info.getLoanPurpose()));//摘要
			sinfo.setNote("");//备注
			sinfo.setBillCode("");//放款单号
			sinfo.setTransNo("");//交易号
			sinfo.setAmount(strAmount);//本金金额
			sinfo.setChineseAmount(ChineseCurrency.showChinese(DataFormat.formatAmount(info.getAmount())));//本金金额（大写）
			sinfo.setContractCode(DataFormat.formatString(info.getContractCode()));//合同编号
			sinfo.setConsignUnit(DataFormat.formatString(info.getConsignClientName()));//委托单位
			sinfo.setCurrencyName("金 额");//币种
			//info.setCurrencyName(Constant.CurrencyType.getName(c_info.getCurrencyID()));//币种
			sinfo.setLoanInterval(DataFormat.getChineseDateString(info.getStart())
					+ " 至 "+ DataFormat.getChineseDateString(info.getEnd()));//贷款期限
			sinfo.setLoanType(LOANConstant.LoanType.getName(info.getLoanTypeID()));//贷款种类
			sinfo.setLoanAccountNo(DataFormat.formatString(info.getLoanAccount()));//贷款账号

			sinfo.setLoanUnit(DataFormat.formatString(info.getLoanClientName()));//借款单位
			
			//如果放款方式是 活期账户
			if( info.getGrantTypeID() == LOANConstant.TransType.CurrentAccount )
			{
				sinfo.setAccountNo(DataFormat.formatString(info.getGrantCurrentAccount()));//账号
				sinfo.setOpenBankName("");//开户银行名称
			}
			else
			{
				sinfo.setAccountNo(DataFormat.formatString(info.getAccountBankCode()));//账号
				sinfo.setOpenBankName(DataFormat.formatString(info.getAccountBankName()));//开户银行名称
			}

			if(info.getWTInterestRate()*(1+info.getAdjustRate()/100) <= 0)
			{
				sinfo.setContractRate("");
			}
			else
			{
					sinfo.setContractRate(DataFormat.formatRate(DataFormat.formatRate(info.getWTInterestRate()*(1+info.getAdjustRate()/100))));//合同利率
			}
			if(info.getCommissionRate() <= 0)
			{
				sinfo.setChargeRate("");
			}
			else
			{
				sinfo.setChargeRate(DataFormat.formatRate(DataFormat.formatRate(info.getCommissionRate())));//手续费率
			}
			//sinfo.setContractRate(DataFormat.formatRate(DataFormat.formatRate(info.getWTInterestRate()*(1+info.getAdjustRate()/100))));//合同利率
			//sinfo.setChargeRate(DataFormat.formatRate(DataFormat.formatRate(info.getCommissionRate())));//手续费率
			
			long lModuleID = Constant.ModuleType.LOAN ;
        	long lLoanTypeID = Constant.ApprovalLoanType.OTHER ;
        	long lActionID = Constant.ApprovalAction.LOANPAY_NOTICE ;
			
			Collection c = null;
			ApprovalBiz appBiz = new ApprovalBiz();
			ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
			c = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lPayID,1);
			if (c != null) 
			{
				String [] sName = {"","","",""};
				Iterator iter = c.iterator();
				int i = 0;
				while (iter.hasNext()&&i<4) 
				{
     		            tracinginfo = (ApprovalTracingInfo)iter.next();
						sName[i++] = tracinginfo.getUserName();
				}
				sinfo.setInputUserName(DataFormat.formatString(i>0?sName[--i]:""));//录入人
				sinfo.setCheckUserName(DataFormat.formatString(i>0?sName[--i]:""));//复核人
				sinfo.setManagerName(DataFormat.formatString(i>0?sName[--i]:""));//部门经理
				sinfo.setManagerLeaderName(DataFormat.formatString(i>0?sName[--i]:""));//主管总经理
			}
			
			OBHtml.showGrantLoan(out,sinfo, 1);
			out.println("<br clear=all style='page-break-before:always'>");
			OBHtml.showGrantLoan(out,sinfo, 2);
			out.println("<br clear=all style='page-break-before:always'>");
			OBHtml.showGrantLoan(out,sinfo, 3);
			out.println("<br clear=all style='page-break-before:always'>");
			OBHtml.showGrantLoan(out,sinfo, 4);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
		}

			
			OBHtml.noShowPrintHeadAndFooterPayNotice(out,0,lOfficeID);
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
	}
%>
<%@ include file="/common/SignValidate.inc" %>