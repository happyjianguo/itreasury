<%--
 页面名称 ：c023.jsp
 页面功能 : 查询 - 贷款通知书查询 - 控制页面
 作    者 ：xrli
 日    期 ：2003-12-22
 特殊说明 ：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QLoanNoticeBook"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obinterest.bizlogic.*"%>	
<%@ page import="com.iss.system.dao.PageLoader"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	String strContinueSave = "";

    try
	{
		/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "贷款通知书查询", "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "贷款通知书查询", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;


       //定义变量
		long lNoticeTypeID=-1;   
		String strContractNoFrom = "";
		String strContractNoTo = "";
		String strPayFormNoFrom = "";
		String strPayFormNoTo = "";	
		long lOrderField = -1;

		strSuccessPageURL = "s002-v.jsp";
		strFailPageURL = "s002-v.jsp";

		String key = (String)request.getAttribute("_pageLoaderKey");
		
		//读取数据
		String strTemp = null;
		strTemp = (String)request.getAttribute("lNoticeTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNoticeTypeID = Long.valueOf(strTemp).longValue();
		}	
		strTemp = (String)request.getAttribute("lContractIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strContractNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lContractIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strContractNoTo = strTemp;
		}
		strTemp = (String)request.getAttribute("lPayFormIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayFormNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lPayFormIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayFormNoTo = strTemp;
		}
			
		
		long  lContractID = -1;
		long  lLoanNoteID = -1;
		
		strTemp = (String)request.getAttribute("lContractID");
		if (strTemp != null && strTemp.length() > 0)
		{
		    lContractID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("lLoanNoteID");
		if (strTemp != null && strTemp.length() > 0)
		{
		    lLoanNoteID = Long.valueOf(strTemp).longValue();
		}
		
		LoanNoticeInfo info_l = new LoanNoticeInfo();
		info_l.setContractID(lContractID);
		info_l.setLoanNoteID(lLoanNoteID);
		info_l.setFormTypeID(lNoticeTypeID);
		
		OBInterestOperation oBInterest = new OBInterestOperation();
		oBInterest.dispatchLoanNotice(info_l);			

        QueryLoanNoticeInfo info = null;
		QLoanNoticeBook qobj = new QLoanNoticeBook();
		PageLoader pageLoader = null;
		
		info = new QueryLoanNoticeInfo();
		
		info.setOfficeID(sessionMng.m_lOfficeID);
		info.setCurrencyID(sessionMng.m_lCurrencyID);
	
		info.setContractNoFrom(strContractNoFrom);
		info.setContractNoTo(strContractNoTo);
		
		info.setPayFormNoFrom(strPayFormNoFrom);
		info.setPayFormNoTo(strPayFormNoTo);			
		
		info.setNoticeTypeID(lNoticeTypeID);
		
		info.setDesc(lDesc);			
		info.setOrderField(lOrderField);
		
		pageLoader = qobj.queryLoanNoticeInfo(info);
		
	    
		key=sessionMng.setPageLoader(pageLoader);
		sessionMng.setQueryCondition(key,info);
		request.setAttribute("_pageLoaderKey",key);
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "贷款通知书查询","",1);
		return;
    }
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = "pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL
	+"&strFailPageURL="+strFailPageURL
	+"&strAction="+Constant.PageControl.LISTALL;
	Log.print("strNextPageURL :  " + strNextPageURL);
	

	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>