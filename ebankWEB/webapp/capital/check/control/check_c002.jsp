<%--
 页面名称 ：check_c002.jsp
 页面功能 : 信息查询 － 取消复核查询 
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="java.util.Collection"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	QueryCapForm queryCapForm = new QueryCapForm();
	String strTitle = "取消复核查询 ";
	String strTemp = "";
	
	long lTransType = -1;
try {
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
       	out.flush();
       	return;
   	}
 
 	//将request中的参数转换到Bean中
	pageInfo.convertRequestToDataEntity(request);
 	
	strTemp = request.getParameter("lTransType");
	if(strTemp != null && strTemp.length()>0){
		lTransType = Long.parseLong(strTemp);
		queryCapForm.setTransType(lTransType);
    }
	
	strTemp = request.getParameter("lDepositID");
	if(strTemp != null && strTemp.length()>0){
		queryCapForm.setDepositID(Long.parseLong(strTemp));
	}
	
	strTemp = request.getParameter("strDepositNo");
	if(strTemp != null && strTemp.length()>0){
		queryCapForm.setDepositNo(strTemp);
	}
    
    strTemp = request.getParameter("sStartExe");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setStartExe(strTemp);
    }
    
    strTemp = request.getParameter("sEndExe");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setEndExe(strTemp);
    }
    
    strTemp = request.getParameter("dMinAmount");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setMinAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
    }
    
    strTemp = request.getParameter("dMaxAmount");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setMaxAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
    }
    
    strTemp = request.getParameter("sStartSubmit");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setStartSubmit(strTemp);
    }
    
    strTemp = request.getParameter("sEndSubmit");
    if(strTemp != null && strTemp.length()>0){
    	queryCapForm.setEndSubmit(strTemp);
    }
    		
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	
	if(pageInfo.getStrAction().equals(String.valueOf(OBConstant.QueryOperationType.QUERY))){
		/* 从session中获取相应数据 */
		queryCapForm.setStatus(OBConstant.SettInstrStatus.CHECK);
	    queryCapForm.setClientID(sessionMng.m_lClientID);
	    queryCapForm.setCurrencyID(sessionMng.m_lCurrencyID);
	    queryCapForm.setUserID(sessionMng.m_lUserID);
	    /* 根据页面菜单确定查询类型 */
	    queryCapForm.setOperationTypeID(OBConstant.QueryOperationType.QUERY);
	    queryCapForm.setOrderBy(true);
	    Collection coll = obFinanceInstrBiz.queryCheckCollectionByQuery(queryCapForm);
	    
		request.setAttribute("queryCapForm",queryCapForm);
		request.setAttribute("queryCapColl",coll);
		pageInfo.success();
	}
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request,response);
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
