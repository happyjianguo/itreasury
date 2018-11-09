<%--
 页面名称 ：as_c003.jsp
 页面功能 : 保存账户关系体系
 作    者 ：jeff
 日    期 ：2008-02-29
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.bizlogic.AccountSystem"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath() + "/iTreasury-ebank";%>

<%
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
	AccountSystem accountSystem = new AccountSystem();
	String strTitle = "查看下级账户";
	String strTemp = "";

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
		assInfo.convertRequestToDataEntity(request);
		long nAccountId = -1;
		
		strTemp = request.getParameter("nUpAccountId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setNUpAccountId(Long.parseLong(strTemp));
		}
		strTemp = request.getParameter("nId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setId(Long.parseLong(strTemp));
		}
		strTemp = request.getParameter("nStatusId");
		if(strTemp != null && !strTemp.equals("")){
			assInfo.setNStatusId(Long.parseLong(strTemp));
		}
		
		strTemp = request.getParameter("nAccountId");
		if(strTemp != null && !strTemp.equals("")){
			nAccountId = Long.parseLong(strTemp);
		}
		
		//删除时使用
		long nRelationId[] = null;
		String strCheckBox[] = request.getParameterValues("cbIndexId");
		if(strCheckBox != null && strCheckBox.length > 0){
			nRelationId = new long[strCheckBox.length];
			for(int i=0; i<strCheckBox.length; i++){
				nRelationId[i] = Long.parseLong(strCheckBox[i]);
			}
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.NEXT))){
			//父结果
			AccountSystemSettingInfo assInfoTemp = accountSystem.findAccountSystem(assInfo.getId());
			//子结果
			Collection coll = accountSystem.findCollByAccountSystemId(assInfo.getId());
			
			pageInfo.success();
			request.setAttribute("findResultInfo",assInfoTemp);
			request.setAttribute("findResultColl",coll);
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.SAVE))){
			AccountSystemSettingInfo assInfoTemp = new AccountSystemSettingInfo();
			assInfoTemp.setId(assInfo.getId());
			if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.SAVE){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.SAVE);
			}else if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.DELETING){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETING);
			}else{
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.MODIFING);
			}
			
			AccountRelationSettingInfo arsInfo = new AccountRelationSettingInfo();
			arsInfo.setNAccountSystemId(assInfo.getId());
			arsInfo.setNAccountId(nAccountId);
			arsInfo.setNStatusId(AccountSystemConstant.RelationStatus.SAVE);
			arsInfo.setNOfficeId(sessionMng.m_lOfficeID);
			arsInfo.setNCurrencyId(sessionMng.m_lCurrencyID);
			
			//校验
			boolean checkAccountRelation = accountSystem.checkAccountRelationByAccountId(nAccountId);
			if(checkAccountRelation == true){
				accountSystem.addAccountRelation(arsInfo);
				accountSystem.updateAccountSystem(assInfoTemp);
				sessionMng.getActionMessages().addMessage("新增成功!");
				pageInfo.success();
			}
			else {
				sessionMng.getActionMessages().addMessage("新增失败，此帐户已被纳入帐户体系!");
				pageInfo.fail();
			}
			
			//父结果
			assInfoTemp = accountSystem.findAccountSystem(assInfo.getId());
			//子结果
			Collection coll = accountSystem.findCollByAccountSystemId(assInfo.getId());
			
			request.setAttribute("findResultInfo",assInfoTemp);
			request.setAttribute("findResultColl",coll);
		}
		
		if(pageInfo.getStrAction().equals(String.valueOf(AccountSystemConstant.Actions.DELETE))){
			//修改父
			AccountSystemSettingInfo assInfoTemp = new AccountSystemSettingInfo();
			assInfoTemp.setId(assInfo.getId());
			if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.SAVE){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.SAVE);
			}else if(assInfo.getNStatusId() == AccountSystemConstant.SettingStatus.DELETING){
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.DELETING);
			}else{
				assInfoTemp.setNStatusId(AccountSystemConstant.SettingStatus.MODIFING);
			}
			
			//修改子
			AccountRelationSettingInfo arsInfoTemp = null;
			boolean isDelete = true;
			
			for(int i=0; i<nRelationId.length; i++){
				AccountRelationSettingInfo arsInfo = accountSystem.findAccountRelation(nRelationId[i]);
				arsInfoTemp = new AccountRelationSettingInfo();
				
				arsInfoTemp.setId(nRelationId[i]);
				if(arsInfo.getNStatusId() == AccountSystemConstant.RelationStatus.SAVE){
					arsInfoTemp.setNStatusId(AccountSystemConstant.RelationStatus.DELETED);
				}
				else if(arsInfo.getNStatusId() == AccountSystemConstant.RelationStatus.DELETING){
					sessionMng.getActionMessages().addMessage("帐户ID:" + arsInfo.getNAccountId() + " 已是删除状态，等待审核");
					pageInfo.fail();
					isDelete = false;
					break;
				}
				else{
			    	OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			    	AccountBalanceInfo accountinfo = obFinanceInstrDao.getCurrBalanceByAccountID(arsInfo.getNAccountId(), sessionMng.m_lCurrencyID, -1);
			    	if(accountinfo.getCurrentBalance() < 0){
						sessionMng.getActionMessages().addMessage("删除失败，帐户ID:" + arsInfo.getNAccountId() + " 占用体系金额");
						pageInfo.fail();
						isDelete = false;
						break;
			    	}

					arsInfoTemp.setNStatusId(AccountSystemConstant.RelationStatus.DELETING);
				}
				accountSystem.updateAccountRelation(arsInfoTemp);
				accountSystem.updateAccountSystem(assInfoTemp);
			}
			
			if(isDelete == true){
				sessionMng.getActionMessages().addMessage("删除成功");
				pageInfo.success();
			}
			
			//父结果
			assInfoTemp = accountSystem.findAccountSystem(assInfo.getId());
			//子结果
			Collection coll = accountSystem.findCollByAccountSystemId(assInfo.getId());
			
			request.setAttribute("findResultInfo",assInfoTemp);
			request.setAttribute("findResultColl",coll);
		}
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
}
catch (IException ie){	
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
%>
