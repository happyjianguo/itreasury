<%--
 页面名称 ：c201.jsp
 页面功能 : 设置密码规则
 作    者 ：liangpan
 日    期 ：2007-6-25
 特殊说明 ：
 修改历史 ：
 修改说明 :
--%>
<%@ page contentType="text/html;charset=gbk" %>
<jsp:directive.page import="java.sql.Timestamp"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*,
				 com.iss.itreasury.ebank.pwconfig.bizlogic.*,
				 com.iss.itreasury.ebank.pwconfig.dao.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 java.util.*,
				 javax.servlet.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<%	
	Log.print("用户密码设置----iTreasury-ebank/pwconfig/control/c201.jsp");

	//页面控制类
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strTitle = "";
	try{
	//登录检测
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}


	//从request中获取页面控制参数
	pageInfo.convertRequestToDataEntity(request);

	/**
	* 定义业务dataentity
	*/
	PasswordInfo passwordInfo = new PasswordInfo();
	
	PWConfigBean biz = new PWConfigBean();

	/**
	* 从request中取出页面相关的变量
	*/
	passwordInfo = biz.getPasswordConfigInfo(sessionMng.m_lOfficeID);
	String strTemp = null;
	strTemp = String.valueOf(request.getAttribute("isCancelConfig"));
	if(strTemp.equals("1")){
		passwordInfo.setStatus(0);
		passwordInfo.setModifyDate(Env.getSystemDateTime());
		biz.updatePasswordInfo(passwordInfo);
	}else{	
		strTemp = null;	
		passwordInfo.setMinPassword(Long.parseLong(String.valueOf(request.getAttribute("passwordLength"))));
		long id = passwordInfo.getId();
		
		strTemp = String.valueOf(request.getAttribute("termDate"));
		if(strTemp.equals("")){
			passwordInfo.setTermDate(-1);
		}else{
			passwordInfo.setTermDate(Long.parseLong(strTemp));
		}
		strTemp = String.valueOf(request.getAttribute("remindDate"));
		if(strTemp.equals("")){
			passwordInfo.setRemindDate(-1);
		}else{
			passwordInfo.setRemindDate(Long.parseLong(strTemp));
		}
		passwordInfo.setCompriseCapital(Long.parseLong(String.valueOf(request.getAttribute("compriseCapital"))));
		passwordInfo.setCompriseLowercase(Long.parseLong(String.valueOf(request.getAttribute("compriseLowercase"))));
		passwordInfo.setCompriseNumber(Long.parseLong(String.valueOf(request.getAttribute("compriseNumber"))));
		passwordInfo.setCompriseSpecial(Long.parseLong(String.valueOf(request.getAttribute("compriseSpecial"))));
		passwordInfo.setCompriseTerm(Long.parseLong(String.valueOf(request.getAttribute("compriseTerm"))));
		passwordInfo.setForcePerfect(Long.parseLong(String.valueOf(request.getAttribute("compriseForce"))));
		passwordInfo.setInputuserId(sessionMng.m_lUserID);
		passwordInfo.setOfficeId(sessionMng.m_lOfficeID);
		passwordInfo.setClientId(sessionMng.m_lClientID);
		passwordInfo.setType(1);
		if(id == -1){
			passwordInfo.setInputDate(Env.getSystemDate());
			passwordInfo.setStatus(1);
			biz.addPasswordInfo(passwordInfo);
		}else{			
			passwordInfo.setModifyDate(Env.getSystemDate());
			biz.updatePasswordInfo(passwordInfo);
		}
	}		
	//成功完成设置
	pageInfo.setStrActionResult(Constant.ActionResult.SUCCESS);
	sessionMng.getActionMessages().addMessage("设置成功");
	}catch(IException e){
		e.printStackTrace();
		/**
		* 出现异常,添加报错信息
		*/
		sessionMng.getActionMessages().addMessage(e.getMessage()); 
		pageInfo.setStrActionResult(Constant.ActionResult.FAIL);
	}
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult", pageInfo.getStrActionResult());

	/**
	 * 将打印信息置入request中
	 */
	if (!pageInfo.getStrPrintMsg().equals(""))request.setAttribute("strPrintMsg",pageInfo.getStrPrintMsg());
	
	/**
	 * 将结果置入request中
	 */
	

	/** 
	 * 转向下一页面
	 */
	 
	String nextURL = "../view/v201.jsp";
	pageInfo.setStrNextPageURL(nextURL);
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>