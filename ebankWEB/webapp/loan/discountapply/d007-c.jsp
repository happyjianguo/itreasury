<%--
 页面名称 ：d007-c.jsp
 页面功能 : 新增贴现申请-保存贴现票据统计信息 控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d007-c.jsp*******");
	
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
	
		//定义变量
	   long lID = -1; //贴现ID标识
	   long lBankAccepPO =  -1; //银行承兑汇票（张数）(页面)
	   long lBizAcceptPO =  -1; //商业承兑汇票（张数）(页面)
	   double dApplyDiscountAmount = 0.0; //申请贴现金额
	   String strDiscountPurpose = ""; //贴现用途
	   String strDiscountReason = ""; //贴现原因
	   long lInputUserID =  -1; //录入人标示
	   Timestamp tsInputDate =  null; //录入时间
	   Timestamp tsDiscountStartDate = null; //贴现开始时间
	   long isPurchaserInterest=-1;//是否买方付息
	   long discountClientID=-1;//出票人ID
	   String discountClientName="";//出票人名称
	   long subLoanType=-1;//贷款子类型
	   
	   //获取贴现票据统计信息
       String strTemp = "";
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		Log.print("==========lID:"+lID);
		
		strTemp = (String)request.getAttribute("lBankAccepPO");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBankAccepPO = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("lBizAcceptPO");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBizAcceptPO = Long.valueOf(strTemp).longValue();
		}
		
		
		strTemp = (String)request.getAttribute("dApplyDiscountAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dApplyDiscountAmount = DataFormat.parseNumber(strTemp);
		}
			
		strTemp = (String)request.getAttribute("strDiscountPurpose");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strDiscountPurpose = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("strDiscountReason");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strDiscountReason = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("lInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lInputUserID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("tsInputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsInputDate =  DataFormat.getDateTime(strTemp);
		}
		
		strTemp = (String)request.getAttribute("tsDiscountStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsDiscountStartDate =  DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("isPurchaserInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			isPurchaserInterest = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("discountClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			discountClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("discountClientName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			discountClientName = strTemp.trim();
		}
		strTemp=(String)request.getAttribute("subLoanType");
		if ( (strTemp!=null)&&(strTemp.length()>0) )
			subLoanType=Long.valueOf(strTemp).longValue();

        DiscountBillStatInfo  discountBillStatInfo = new DiscountBillStatInfo();
     
	 	discountBillStatInfo.setID(lID); 
		discountBillStatInfo.setBankAcceptPO(lBankAccepPO); 
		discountBillStatInfo.setBizAcceptPO(lBizAcceptPO); 
		discountBillStatInfo.setAmount(dApplyDiscountAmount); 
		discountBillStatInfo.setReason(strDiscountReason); 
		discountBillStatInfo.setPurpose(strDiscountPurpose);
		discountBillStatInfo.setInputUserID(lInputUserID); 
		discountBillStatInfo.setDate(tsInputDate); 
		discountBillStatInfo.setDiscountStartDate(tsDiscountStartDate); 
		discountBillStatInfo.setIsPurchaserInterest(isPurchaserInterest);
		discountBillStatInfo.setDiscountClientID(discountClientID);
        discountBillStatInfo.setDiscountClientName(discountClientName);
		discountBillStatInfo.setSubTypeId(subLoanType);

		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		
		
		if(obDiscountApply.saveDiscount2(discountBillStatInfo) > 0)
		{
		    /* 获取上下文环境 */
	       ServletContext sc = getServletContext();
	       /* 设置返回地址 */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d008-c.jsp")));
	       /* forward到结果页面 */
	       rd.forward(request, response);
		}
		else
		{ 
		   OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		   return;
		}

    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


