<!-- 
 页面名称 ：q101-c.jsp
 页面功能 : 子账户查询——业务逻辑
 作    者 ：leiyang
 日    期 ：2007-07-04
 特殊说明 ：
 修改历史 ：
 -->

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>	
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QSubAccountDao" %>
<%@ page import="com.iss.itreasury.settlement.account.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.*" %>
<%@ page import="java.util.Collection,com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>

<% String strContext = request.getContextPath();%>

<%
	Log.print("\n\n*******进入页面--capital/query/q101-c.jsp*******\n\n");
	String strTitle = "[子账户查询]";
	
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	Timestamp  tsQueryDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
	UtilOperation utilOperation = new UtilOperation();
%>

<%
    try
	{
	     /* 用户登录检测 */
	    if (sessionMng.isLogin() == false)
	    {
	    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
	    	out.flush();
	    	return;
	    }
	
	    //判断用户是否有权限 
	    if (sessionMng.hasRight(request) == false)
	    {
	        out.println(sessionMng.hasRight(request));
	    	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
	    	out.flush();
	    	return;
		}
	
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

		//取得页面控制变量
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		

        //定义变量
        long ID=-1;
        //取得ID的值
		String strTemp = null;
		strTemp = (String)request.getAttribute("id");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    ID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("QueryDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsQueryDate = DataFormat.getDateTime(strTemp);
		}
		
        //通过ID在账户主表中查得账户类型
		QSubAccountDao dao = new QSubAccountDao();	
		long nAccountTypeID = dao.findAccountTypeBySubAccountID(ID);	
	    long lnAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(nAccountTypeID);

		switch((int)lnAccountGroupID)
		{
			//定期
			case (int)SETTConstant.AccountGroupType.FIXED:		

				QuerySubFixedInfo fixedInfo = dao.findFixedInfoBySubaccountID(ID);
				Log.print("\n\n c031 fixedInfo="+fixedInfo+" \n\n");
			    strActionResult = Constant.ActionResult.SUCCESS;
			    request.setAttribute("searchResults",fixedInfo);
				strSuccessPageURL="q101-v.jsp";
				break;
			// 保证金
			case (int)SETTConstant.AccountGroupType.MARGIN:		

				QuerySubFixedInfo marginInfo=dao.findFixedInfoBySubaccountID(ID);
				Log.print("\n\n c031 fixedInfo="+marginInfo+" \n\n");
			    strActionResult = Constant.ActionResult.SUCCESS;
			    request.setAttribute("searchResults",marginInfo);
				strSuccessPageURL="../view/v037.jsp";
				break;
			//通知
			case (int)SETTConstant.AccountGroupType.NOTIFY:		
				QuerySubNoteInfo noteInfo=dao.findNoteInfoBySubaccountID(ID);
				Log.print("\n\n c031 noteInfo="+noteInfo+" \n\n");
			    strActionResult = Constant.ActionResult.SUCCESS;
			    request.setAttribute("searchResults",noteInfo);
				strSuccessPageURL="../view/v032.jsp";	
				break;

			//信托
			case (int)SETTConstant.AccountGroupType.TRUST:			
				QuerySubAffianceInfo affianceInfo = dao.findAffianceInfoBySubaccountID(ID);
				Log.print("\n\n c031 affianceInfo="+affianceInfo+" \n\n");
				strActionResult = Constant.ActionResult.SUCCESS;
				affianceInfo.setLoanInterestRate(utilOperation.getLatelyRate(affianceInfo.getAL_nLoanNoteID(),tsQueryDate));
				//
			    request.setAttribute("searchResults",affianceInfo);
				if(affianceInfo.getLoanType()==LOANConstant.LoanType.YT)
				{
					strSuccessPageURL="../view/v036.jsp";
				}
				else
				{
					strSuccessPageURL="../view/v033.jsp";
				}

				break;

			//委托
			case (int)SETTConstant.AccountGroupType.CONSIGN:					
				QuerySubConsignInfo consignInfo=dao.findConsignInfoBySubaccountID(ID);
				Log.print("\n\n c031 consignInfo="+consignInfo+" \n\n");
				strActionResult = Constant.ActionResult.SUCCESS;
				consignInfo.setLoanInterestRate(utilOperation.getLatelyRate(consignInfo.getAL_nLoanNoteID(),tsQueryDate));
			    request.setAttribute("searchResults",consignInfo);
				strSuccessPageURL="../view/v034.jsp";
				break;

			//贴现
			case (int)SETTConstant.AccountGroupType.DISCOUNT:		
				QuerySubDiscountInfo discountInfo=dao.findDiscountInfoBySubaccountID(ID);
				Log.print("\n\n c031 discountInfo="+discountInfo+" \n\n");
				strActionResult = Constant.ActionResult.SUCCESS;
			    request.setAttribute("searchResults",discountInfo);
				strSuccessPageURL="../view/v035.jsp";		
				break;
			default:
				break;
		}
        
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
	Log.print("strNextPageURL :  " + strNextPageURL);

	//转向下一页面
	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	
	
	
	Log.print("\n\n end strNextPageURL="+strNextPageURL+" \n\n");
	rd.forward( request,response );
%>