
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dao.BillPrintBiz"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransParam"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AccountInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dao.AccountHisTransDAO"/><%
/**页面功能说明
 * 页面名称 ：c091-gd.jsp
 * 页面功能 : 对账单中单账户的信息查询
 * 作    者 ：zcwang
 * 日    期 ：2008-4-29
 * 简单实现说明：
 *				1、单账户的交易明细
 *				2、用于显示和打印				
 * 特殊说明 ：
 * 修改历史 ：
 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<!--类导入部分开始-->
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>

<!--类导入部分结束-->

<%
String strTitle = null;
	/**页面控制Info**/
	PageControllerInfo pageCtrl = new PageControllerInfo();
	
    try
	{
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		/**页面校验开始（用户登录校验、用户权限校验、重复请求校验）*/
		JSPLogger.info("*******进入页面--bankportal\\c091-account_ebank.jsp*******");
		
		//获取页面控制信息
		pageCtrl.convertRequestToDataEntity(request);
		if((request.getAttribute("p_action")!=null))
		{
		String temp=(String)request.getAttribute("p_action");
		pageCtrl.setP_Action(temp);
		}
		else if(request.getParameter("p_action")!=null)
		{
		 pageCtrl.setP_Action(request.getParameter("p_action"));
		}
		/**页面校验结束*/
		
		/**业务逻辑开始*/
		//获取显示页面的业务信息
		QueryBillPrintParam param = new QueryBillPrintParam();		
		param.convertRequestToDataEntity(request);
		param.setOfficeID(sessionMng.m_lOfficeID);		
		//String clientID = (String)session.getAttribute("eclientid");
		//if(!"".equals(clientID)&&null!=clientID){
		//	long clientid=Long.parseLong(clientID);
		//	param.setClientIdFrom(clientid);
		//}
		long currencyID=sessionMng.m_lCurrencyID;
		param.setCurrencyType(currencyID);
		param.setClientId(sessionMng.m_lClientID);
		param.setClientIdFrom(sessionMng.m_lClientID);
		if(null != param.getBankType()&& !"".equals(param.getBankType())){
			//long bankid=NameRef.getBankIdByRefCode(param.getBankType());
			//param.setBankId(bankid);
			
			String bankid=NameRef.getBankIdByRefCode(param.getBankType());
			param.setAllbankId(bankid);
		}
		/**账户基本信息Biz**/
		AccountInfo acctInfo = null;
		
		/**账户历史余额Biz**/
		BillPrintBiz billPrintBiz = new BillPrintBiz();
		
		/**账户交易Biz**/
		AcctTransParam transParam = new AcctTransParam();
		
		
		/**定义返回的结果对象**/
		double beginBalance   = Double.NaN;
		AcctTransInfo[] transInfos = null;
		
		if(pageCtrl.getP_Action().equals("findById"))
		{
            //取得查询区间
			String strTemp = "";
			request.setAttribute("p_action",pageCtrl.getP_Action());
			beginBalance = billPrintBiz.findAcctBalance( param );
			
			//返回结果对象之二:账户历史交易
			if( param != null )
			{
				transParam.setAccountId( param.getAccountId() );
				transParam.setTransactionEndDate(param.getTransactionEndDate());
				transParam.setTransactionStartDate(param.getTransactionStartDate());
				transInfos = billPrintBiz.findSingleAcctHisBalance( transParam );
			}			
			if(transInfos!=null)
			{
				JSPLogger.debug("C transInfos:"+transInfos.length);
			}else{JSPLogger.debug("C transInfos:-1");}
			//返回结果对象之三:账户基本信息
			//acctInfo  = billPrintBiz.findAccountInfoByID( param.getAccountId(),AccountHisTransDAO.strTableName );			
			//保存查询结果
		  request.setAttribute("balanceCol",new Double(beginBalance));
			request.setAttribute("transInfos",transInfos);
			//request.setAttribute("acctInfo",acctInfo);	
			request.setAttribute("param",param);
		}
		pageCtrl.success();
		/**业务逻辑结束*/
	}
	catch( Exception exp )
	{
		sessionMng.getActionMessages().addMessage(exp);
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
		pageCtrl.fail();
	}
	
	String nextURL = pageCtrl.getP_NextPageURL();
	JSPLogger.info("进入下一页面："+nextURL);	
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	rd.forward( request,response );
%>