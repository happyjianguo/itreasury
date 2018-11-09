<%--
 页面名称 ：c034.jsp
页面功能 : （银团）贷款收回处理控制页面
 作    者 ：xirenli
 日    期 ：2004年05月24日
 特殊说明 ：实现操作说明：
				1、复核
				2、取消复核
 修改历史 ：
--%>
	<%@ page contentType = "text/html;charset=gbk" %>
	<%@ page import="java.sql.Timestamp"%>
	<%@ page import="java.util.Collection"%>
	<%@ page import="java.util.ArrayList"%>
	<%@ page import="com.iss.itreasury.util.Log"%>
	<%@ page import="com.iss.itreasury.util.Env"%>
	<%@ page import="com.iss.itreasury.util.Constant"%>
	<%@ page import="com.iss.itreasury.util.DataFormat"%>
	<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
	<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
	<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
	<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
	<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
	<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_SyndicationLoanInterestDAO"%>
	<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

try
{
	//判断是否登录
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
	long lCheckUserID = sessionMng.m_lUserID;
	long lID = -1;
	String strCheckAbstract = "";
	java.sql.Timestamp tsModify = null;
	
	String strModify = null;
	String strTransNo = "";
	
	//读取数据
	strAction = (String)request.getAttribute("strAction");
	System.out.println("**********************strAction="+strAction);
	strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		TransRepaymentLoanInfo transRepaymentLoanInfo = new TransRepaymentLoanInfo();
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();
		
	String strTemp = null;
	
	strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("tsModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		
		if("Query".equals(strAction))//修改
		{
			if ("Query".equals(strAction) && strTransNo != null && !strTransNo.equals(""))
			{
				lID = transLoanDelegation.getIDByTransNo(strTransNo);
			}
			Log.print("lID = " + lID);
		}
	
			transRepaymentLoanInfo = transLoanDelegation.findByID(lID);
			if(transRepaymentLoanInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				//list = findSyndicationByReceiveLoanID(lID);
				Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
				list = (ArrayList)dao.findBySyndicationLoanReceiveID(lID);
				transRepaymentLoanInfo.setSyndicationLoanInterest(list);
			}
		
		//transRepaymentLoanInfo = transLoanDelegation.repaymentFindDetailByID(lID);
		Log.print("复核记录ID："+lID);

		if(transRepaymentLoanInfo == null)
		{
			throw new Exception("Gen_E001");
		} 		
	
		//将数据填写到request中，后续代码执行发生异常时跳转到view页面可以完成数据回显
		request.setAttribute("searchResults",transRepaymentLoanInfo);
		session.setAttribute("searchBankLoanMemberResults",transRepaymentLoanInfo.getSyndicationLoanInterest());		
        
		if("toCancelCheck".equals(strAction) || "Query".equals(strAction))
		{
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
        {
            Log.print("无效操作");
        }
	
}
catch( Exception exp )
{
	exp.printStackTrace();
	//sessionMng.getActionMessages().addMessage(exp);
	strActionResult = Constant.ActionResult.FAIL;	
}
	
	request.setAttribute("strActionResult",strActionResult);

	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//转向下一页面
	Log.print("Next Page URL:"+strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>