<%--
 页面名称 ：c001.jsp
 页面功能 :  挂失业务处理控制页面
 作    者 ：jinchen
 日    期 ：2004-11-23
 特殊说明 ：
 实现操作说明：
 修改历史 ：
           
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@page import="
				java.util.*,
				java.sql.*,
				com.iss.itreasury.util.*,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.*,
				com.iss.itreasury.settlement.reportlossorfreeze.dao.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * 页面控制类
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

try {
		/** 权限检查 **/
		/* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
            out.flush();
            return;
        }
	   String strTableTitle = "业务处理 —— 挂失";
	   Log.print("=================进入页面reportlossorfreeze/control/c001.jsp=========");
	   //请求检测
       //if(!SETTHTML.validateRequest(out, request,response)) return;

	/** 
	 * 从request中获得页面控制信息 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/** 
	 * 定义业务变量 
	 */
	
	
	 String strTemp = null;
	 long lOrder = -1;	
	 long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

	 ReportLossOrFreezeInfo info=new ReportLossOrFreezeInfo();	
	 ReportLossOrFreezeInfo upInfo=new ReportLossOrFreezeInfo();
	 ReportLossOrFreezeInfo tpInfo=new ReportLossOrFreezeInfo();
	 ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
	/**
	* 获得上页参数
	*/
	info.convertRequestToDataEntity(request);
	
	info.setOfficeId(sessionMng.m_lOfficeID);
	info.setCurrencyId(sessionMng.m_lCurrencyID);
	String strTmp="";
	strTmp = (String)request.getAttribute("subAccountIdCtrl");
	if( strTmp != null && strTmp.length() > 0 )
	{
		info.setOldDepositNo(strTmp.trim());
	}
	
	ReportLossOrFreezeBean delegation = new ReportLossOrFreezeBean();
	long ltemp = -1;
	/**
	 * 根据操作代码进行操作
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.CREATESAVE)))	//创建保存
	{			
		info.setStatus(SETTConstant.TransactionStatus.SAVE);
		info.setSubAccountOldStatus(SETTConstant.SubAccountStatus.NORMAL);
		info.setSubAccountNewStatus(SETTConstant.SubAccountStatus.REPORTLOSS);
		info.setTransActionType(SETTConstant.TransactionType.REPORTLOSS);
		info.setModifyDate(Env.getSystemDateTime()) ;
		ltemp =delegation.save(info);
		upInfo = delegation.findByID(ltemp);
		upInfo.setCheckUserId(sessionMng.m_lUserID);
		upInfo.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
		ltemp = delegation.check(upInfo);
		
		
		if(ltemp>0)
		{
			request.setAttribute("reportLossOrFreezeInfo",info);
			/**
			* 操作成功
			*/
			request.setAttribute("SucInfo","保存成功！");
			pageInfo.success();
		}
		else 
		{
			request.setAttribute("SucInfo","挂失业务新增操作失败,请重试！");
			pageInfo.fail();
		}
	}
	/**
	 * 根据操作代码进行操作
	 */
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.LINKSEARCH)))	//链接查找
	{			
		
		Collection c = null;
		strTemp = (String)request.getAttribute("Order");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lOrder = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("Desc");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lDesc = Long.parseLong(strTemp.trim());
		}
		qInfo.setDesc(lDesc);
		qInfo.setOrderField(lOrder);
		qInfo.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
		qInfo.setCheckUserId(sessionMng.m_lUserID);
		qInfo.setStatus(SETTConstant.TransactionStatus.CHECK);
		qInfo.setTransActionType(SETTConstant.TransactionType.REPORTLOSS);
		c = delegation.findByConditions(qInfo);
		request.setAttribute("SearchResult",c);
		request.setAttribute("Order",String.valueOf(lOrder));
		request.setAttribute("Desc",String.valueOf(lDesc));
		/**
		* 操作成功
		*/	
		pageInfo.success();	
	}
	/**
	 * 根据操作代码进行操作
	 */
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.TODETAIL)))	//点交易号到详细页面
	{	
		long lId = -1;
		strTmp = (String)request.getAttribute("hdnId");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lId = Long.parseLong(strTmp.trim());
		}
		ReportLossOrFreezeInfo rInfo = new ReportLossOrFreezeInfo();
		rInfo = delegation.findByID(lId);
		request.setAttribute("ResultInfo",rInfo);
		/**
		* 操作成功
		*/	
		pageInfo.success();	
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.DELETE)))	// 删除
	{	
		long lId = -1;
		String strModifyDate = "";
		strTmp = (String)request.getAttribute("hdnId");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lId = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("hdnModifyDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strModifyDate = strTmp;
		}
		
		ReportLossOrFreezeInfo rInfo = new ReportLossOrFreezeInfo();
		ReportLossOrFreezeInfo cInfo = new ReportLossOrFreezeInfo();
		rInfo = delegation.findByID(lId);
		rInfo.setModifyDate(new Timestamp(Long.parseLong(strModifyDate)));
		ltemp = delegation.cancelCheck(rInfo);
		cInfo = delegation.findByID(lId);
		ltemp = delegation.delete(cInfo);
		

		if(ltemp>0)
		{
			
			/**
			* 操作成功
			*/
			request.setAttribute("SucInfo","删除成功！");
			pageInfo.success();
		}
		else 
		{
			request.setAttribute("SucInfo","挂失业务删除操作失败,请重试！");
			pageInfo.fail();
		}
		/**
		* 操作成功
		*/	
		pageInfo.success();	
	}


	
} catch ( Exception exp ) {
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	//sessionMng.getActionMessages().addMessage(exp);
	/**
	 * 出现异常,操作结果置为失败
	 */
	pageInfo.fail();
}
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	/** 
	 * 转向下一页面 
	 **/
	
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
	
	
	
	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>