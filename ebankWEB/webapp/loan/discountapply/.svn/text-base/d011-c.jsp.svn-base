<%--
 页面名称 ：d011-c.jsp
 页面功能 : 新增贴现申请-保存贴现申请 贴现票据明细保存 控制页面
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
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d011-c.jsp*******");
	
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
		int nArrayNumber = 20;
		int nNum = 0;
		
		String strTmp = "";
		
		long lDiscountBillID = -1; //贴现票据标识,
		long lDiscountApplyID = -1; //贴现标识,
		long lDiscountCredenceID = -1; //贴现凭证标识,
		String strUser = ""; //原始出票人,
		String strBank = ""; //承兑银行,
		long lSerialNo = -1; //序列号,
		long lIsLocal = -1; //是否在本地,
		Timestamp tsCreate = null; //出票日,
		Timestamp tsEnd = null; // 到期日,
		String strCode = ""; //汇票号码,
		double dAmount = 0.0; //汇票金额,
		long lAddDay = 0; //节假日增加天数,
		long lAcceptPOTypeID = -1;//汇票类型
		String strFormerOwner = ""; //贴现单位直接前手
		String frompage="";
		
		 strTmp = (String)request.getAttribute("lID");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 lDiscountApplyID = Long.parseLong(strTmp.trim());
		 }
		
		for (nNum = 1; nNum <= nArrayNumber; nNum++)
		{
				strUser = ""; //原始出票人,
				strBank = ""; //承兑银行,
				lIsLocal = -1; //是否在本地,
				tsCreate = null; //出票日,
				tsEnd = null; // 到期日,
				strCode = ""; //汇票号码,
				dAmount = 0.0; //汇票金额,
				lAddDay = 0; //节假日增加天数,
				lAcceptPOTypeID = -1;//汇票类型
				strFormerOwner = ""; //贴现单位直接前手
				
				strTmp = (String)request.getAttribute("strUser"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					strUser = strTmp.trim();
				}

				strTmp = (String)request.getAttribute("strBank"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					strBank = strTmp.trim();
				}

				strTmp = (String)request.getAttribute("lIsLocal"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					lIsLocal = Integer.parseInt(strTmp.trim());
				}

				strTmp = (String)request.getAttribute("tsCreate"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					tsCreate = DataFormat.getDateTime(strTmp.trim());
				}

				strTmp = (String)request.getAttribute("tsEnd"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					tsEnd = DataFormat.getDateTime(strTmp.trim());
				}

				strTmp = (String)request.getAttribute("strCode"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					strCode = strTmp.trim();
				}
			
				strTmp = (String)request.getAttribute("dAmount"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					dAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
				}

				strTmp = (String)request.getAttribute("lAddDay"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					lAddDay = Integer.parseInt(strTmp.trim());
				}
				
				strTmp = (String)request.getAttribute("lAcceptPOTypeID"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					lAcceptPOTypeID = Integer.parseInt(strTmp.trim());
				}
				
				strTmp = (String)request.getAttribute("strFormerOwner"+nNum);
				if( strTmp != null && strTmp.length() > 0 )
				{
					strFormerOwner = strTmp.trim();
				}
				strTmp = (String)request.getAttribute("frompage");
				if( strTmp != null && strTmp.length() > 0 )
				{
					frompage = strTmp;
				}
				
				DiscountBillInfo discountBillInfo = new DiscountBillInfo();
				
				discountBillInfo.setDiscountBillID(lDiscountBillID); //贴现票据标识,
		        discountBillInfo.setDiscountApplyID(lDiscountApplyID); //贴现标识,
		        discountBillInfo.setDiscountCredenceID(lDiscountCredenceID); //贴现凭证标识,
		        discountBillInfo.setUser(strUser); //原始出票人,
		        discountBillInfo.setBank(strBank); //承兑银行,
		        discountBillInfo.setSerialNo(lSerialNo); //序列号,
		        discountBillInfo.setIsLocal(lIsLocal); //是否在本地,
		        discountBillInfo.setCreate(tsCreate); //出票日,
		        discountBillInfo.setEnd(tsEnd); // 到期日,
		        discountBillInfo.setCode(strCode); //汇票号码,
		        discountBillInfo.setAmount(dAmount); //汇票金额,
		        discountBillInfo.setAddDay(lAddDay); //节假日增加天数,
				discountBillInfo.setAcceptPOTypeID(lAcceptPOTypeID); //汇票类型
				discountBillInfo.setFormerOwner(strFormerOwner); //单位直接前出手
				
				OBDiscountApplyHome  obDiscountApplyHome = null;
	            OBDiscountApply      obDiscountApply = null;
	            obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
	            obDiscountApply = obDiscountApplyHome.create();
		
						
				if (strUser != null && strUser.length() > 0)
				{
					obDiscountApply.saveDiscountBill(discountBillInfo);
				}
				
				
			}
			  /* 获取上下文环境 */
               ServletContext sc = getServletContext();
     		   /* 设置返回地址 */
    		   RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d008-c.jsp?frompage="+frompage+"")));
    		   /* forward到结果页面 */
     		   rd.forward(request, response); 

%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


